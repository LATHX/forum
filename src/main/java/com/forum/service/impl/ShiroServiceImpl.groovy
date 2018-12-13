package com.forum.service.impl

import com.forum.mapper.AuthorityMapper
import com.forum.mapper.NonAuthemticateMapper
import com.forum.model.entity.AuthorityEntity
import com.forum.model.entity.NonAuthemticateEntity
import com.forum.utils.CommonUtil
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
import org.apache.shiro.web.servlet.AbstractShiroFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("shiroService")
class ShiroServiceImpl {
    @Autowired
    private AuthorityMapper authorityMapper
    @Autowired
    private NonAuthemticateMapper authemticateMapper
    private String NON_AUTHEMTICATE

    public Map<String, String> loadFilterChainDefinitions() {
        List<AuthorityEntity> authorities = authorityMapper.findAuthorities();
        //从数据库读取不需要权限map
        List<NonAuthemticateEntity> nonAuthemticateEntityList = authemticateMapper.selectAllFromTable()
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        if (authorities.size() > 0) {
            String uris;
            String[] uriArr;
            for (AuthorityEntity authority : authorities) {
                if (CommonUtil.isEmpty(authority.getPermission())) {
                    continue;
                }
                uris = authority.getUri();
                uriArr = uris.split(",");
                for (String uri : uriArr) {
                    filterChainDefinitionMap.put(uri, authority.getPermission());
                }
            }
        }
//        NON_AUTHEMTICATE?.split(',')?.each {
//            filterChainDefinitionMap.put(it, "anon")
//        }
        nonAuthemticateEntityList?.each {
            if (it?.getEnable())
                filterChainDefinitionMap.put(it?.getUrl(), "anon")
        }
        //其他资源都需要认证  authc 表示需要认证才能进行访问 ,user表示配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/**", "user");
        return filterChainDefinitionMap;
    }

    /**
     * 在对角色进行增删改操作时，需要调用此方法进行动态刷新
     * @param shiroFilterFactoryBean
     */
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }

    String getNON_AUTHEMTICATE() {
        return NON_AUTHEMTICATE
    }

    void setNON_AUTHEMTICATE(String NON_AUTHEMTICATE) {
        this.NON_AUTHEMTICATE = NON_AUTHEMTICATE
    }
}
