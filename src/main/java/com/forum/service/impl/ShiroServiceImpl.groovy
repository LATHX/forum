package com.forum.service.impl

import com.forum.mapper.AuthorityMapper
import com.forum.mapper.NonAuthemticateMapper
import com.forum.mapper.SessionMapper
import com.forum.model.entity.AuthorityEntity
import com.forum.model.entity.NonAuthemticateEntity
import com.forum.model.entity.SessionEntity
import com.forum.utils.CommonUtil
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
import org.apache.shiro.web.servlet.AbstractShiroFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("shiroService")
class ShiroServiceImpl {
    @Autowired
    private AuthorityMapper authorityMapper
    @Autowired
    private NonAuthemticateMapper authemticateMapper
    @Autowired
    private SessionMapper sessionMapper
    private static final Logger logger = LoggerFactory.getLogger(this.getClass())
    /**
     * authorities 权限控制map.从数据库获取
     * nonAuthemticateEntityList  从数据库读取不需要权限map
     * 其他资源都需要认证  authc 表示需要认证才能进行访问 ,user表示配置记住我或认证通过可以访问的地址
     * @return
     */
    public Map<String, String> loadFilterChainDefinitions() {
        List<AuthorityEntity> authorities = authorityMapper.selectAllFromTable()
        List<NonAuthemticateEntity> nonAuthemticateEntityList = authemticateMapper.selectAllFromTable()
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

        nonAuthemticateEntityList?.each {
            if (it?.getEnable())
                filterChainDefinitionMap.put(it?.getUrl(), "anon")
        }
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

    void addUserSession(SessionEntity session) {
        Integer count = sessionMapper.SelectCountByUsernameAndCookie(session.getUsername(), session.getCookie())
        if (count == 1) {
            SessionEntity exitsSession = sessionMapper.SelectByUsernameAndCookie(session.getUsername(), session.getCookie())
            exitsSession.setUpdatetime(CommonUtil.getCurrentDate())
            sessionMapper.updateByPrimaryKey(exitsSession)
        } else if (count == 0) {
            sessionMapper.insert(session)
        }else{
            logger.warn('User Session more than 1 row')
        }

    }

}
