package org.wxm.summary.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxm.core.constant.CstCommon;
import org.wxm.summary.security.domain.SecurityDomain;
import org.wxm.summary.security.view.UserDetail;
import org.wxm.summary.system.view.VSysMenu;

/**
 * <b>标题: </b>自定义Shiro用户权限信息封装类 <br/>
 * <b>描述: </b> <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-21 下午2:45:14 <br/>
 * <b>修改记录: </b>
 * 
 */
public class ShiroSecurityRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass()); // 日志记录

    @Autowired
    private SecurityDomain securityDomain; // 用户权限DOMAIN接口

    /**
     * 
     * <b>标题: </b>验证当前登录的Subject <br/>
     * <b>描述: </b>方法的调用时机为SecurityController.login()方法中执行Subject.login()时 <br/>
     * <b>版本: </b>1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-15 下午7:12:36 <br/>
     * <b>修改记录: </b>
     * 
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        // 两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 用户名
        String username = String.valueOf(token.getPrincipal());
        // 密码
        String password = new String((char[]) token.getCredentials());

        // 根据用户名查询用户信息，包括角色、权限信息
        UserDetail userDetail = securityDomain.findUserByUsername(username);

        // 用户不存在，抛出UnknownAccountException异常
        if (null == userDetail) {
            throw new UnknownAccountException();
        }
        // 密码不匹配，抛出IncorrectCredentialsException异常
        else if (!password.equals(userDetail.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        // 账户被锁定，抛出AuthenticationException异常
        else if (CstCommon.VAR_STR_YES.equals(userDetail.getIsLocked())) {
            throw new LockedAccountException();
        }

        // 可扩展异常，如下：
        // <!-- 身份认证异常 -->
        // <!-- 身份令牌异常，不支持的身份令牌 -->
        // org.apache.shiro.authc.pam.UnsupportedTokenException
        // <!-- 未知账户/没找到帐号,登录失败 -->
        // org.apache.shiro.authc.UnknownAccountException
        // <!-- 帐号锁定 -->
        // org.apache.shiro.authc.LockedAccountException
        // <!-- 用户禁用 -->
        // org.apache.shiro.authc.DisabledAccountException
        // <!-- 登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试 -->
        // org.apache.shiro.authc.ExcessiveAttemptsException
        // <!-- 一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录-->
        // org.apache.shiro.authc.ConcurrentAccessException
        // <!-- 账户异常 -->
        // org.apache.shiro.authc.AccountException
        // <!-- 过期的凭据异常 -->
        // org.apache.shiro.authc.ExpiredCredentialsException
        // <!-- 错误的凭据异常 -->
        // org.apache.shiro.authc.IncorrectCredentialsException
        // <!-- 凭据异常 -->
        // org.apache.shiro.authc.CredentialsException
        // org.apache.shiro.authc.AuthenticationException
        // <!-- 权限异常 -->
        // <!-- 没有访问权限，访问异常 -->
        // org.apache.shiro.authz.HostUnauthorizedException
        // org.apache.shiro.authz.UnauthorizedException
        // <!-- 授权异常 -->
        // org.apache.shiro.authz.UnauthenticatedException
        // org.apache.shiro.authz.AuthorizationException
        // <!-- shiro全局异常 -->
        // org.apache.shiro.ShiroException

        logger.info("用户登录。[用户名：{}][中文姓名：{}]", userDetail.getUsername(), userDetail.getCnName());
        // 封装返回对象
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userDetail, password, ByteSource.Util.bytes(username), this.getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 
     * <b>标题: </b>为当前登录的Subject授予角色和权限 <br/>
     * <b>描述: </b>授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用. <br/>
     * <b>版本: </b>1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-21 下午9:12:07 <br/>
     * <b>修改记录: </b>
     * 
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 从 principals获取主身份信息
        // 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
        UserDetail userDetail = (UserDetail) principals.getPrimaryPrincipal();

        // 用户不存在，抛出UnknownAccountException异常
        if (null == userDetail) {
            throw new AuthenticationException("会话过期！");
        }

        // 菜单-操作
        Set<String> menuOperates = null;
        // 获取菜单操作权限信息
        List<VSysMenu> menus = securityDomain.findMenuOperatorByUserId(userDetail.getId());

        if (null == menus || menus.isEmpty()) {
            throw new AuthenticationException("当前用户无任何权限！");
        }

        menuOperates = new HashSet<String>();
        String menuOperateFrag = "%s:%s";
        for (VSysMenu menu : menus) {
            // 将数据库中的权限标签 符放入集合
            menuOperates.add(String.format(menuOperateFrag, menu.getOpCode(), menu.getMenuUrl()));
        }

        // 查到权限数据，返回授权信息(要包括 上边的permissions)
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(menuOperates);

        return simpleAuthorizationInfo;

    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        this.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
