package com.forum.service.security.realm

import com.forum.mapper.RoleAuthorityMapper
import com.forum.mapper.RoleMapper
import com.forum.mapper.UserMapper
import com.forum.model.entity.AuthorityEntity
import com.forum.model.entity.RoleEntity
import com.forum.model.entity.UserEntity
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired

class PasswordRealm extends AuthorizingRealm{
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity user = (UserEntity) principalCollection.getPrimaryPrincipal();
        System.out.println(user.getUsername() + "进行授权操作");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Integer roleId = user.getRoleId();
        RoleEntity role = roleMapper.findRoleById(roleId);
        info.addRole(role.getRoleName());
        List<AuthorityEntity> authorities = roleAuthorityMapper.findAuthoritiesByRoleId(roleId);
        if (authorities.size() == 0) {
            return null;
        }else{
            authorities?.each {
                info.addStringPermission(it?.getUri())
            }
        }

        return info;
    }

    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //toke强转
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        //根据用户名查询密码，由安全管理器负责对比查询出的数据库中的密码和页面输入的密码是否一致
        UserEntity user = userMapper.findUserByUserName(username);
        if (user == null) {
            return null;
        }

        //单用户登录
        //处理session
/*
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        //获取当前已登录的用户session列表
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        def obj = sessionManager.getSessionDAO()
        UserEntity temp;
        for(Session session : sessions){
            //清除该用户以前登录时保存的session，强制退出
            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }

            temp = (UserEntity) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if(username.equals(temp.getUsername())) {
                sessionManager.getSessionDAO().delete(session);
            }
        }
*/
        String password = user.getPassword();
        //最后的比对需要交给安全管理器,三个参数进行初步的简单认证信息对象的包装,由安全管理器进行包装运行
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
