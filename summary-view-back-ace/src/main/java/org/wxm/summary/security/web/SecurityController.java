package org.wxm.summary.security.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wxm.core.util.StringUtils;

/**
 * <b>标题: </b>权限验证控制类 <br/>
 * <b>描述: </b>集中控制登录、注销和访问首页等功能 <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-21 下午3:04:48 <br/>
 * <b>修改记录: </b>
 * 
 */
@Controller
public class SecurityController {

    private Logger logger = LoggerFactory.getLogger(this.getClass()); // 日志记录

    private final String loginPage = "login"; // 登录页面
    private final String indexPage = "index"; // 后台首页

    /**
     * 
     * <b>标题: </b>登录页面 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-28 下午11:03:20 <br/>
     * <b>修改记录: </b>
     * 
     * @return
     */
    @RequestMapping("/")
    public String login() {
        return loginPage;
    }

    /**
     * 
     * <b>标题: </b>登录操作 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-28 下午11:03:35 <br/>
     * <b>修改记录: </b>
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param modelMap
     *            页面参数映射
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String username, String password, HttpServletResponse response) {
        String loginMsg = null;
        try {
            if (StringUtils.isEmpty(username)) {
                loginMsg = "用户名不能为空！";
            } else if (StringUtils.isEmpty(password)) {
                loginMsg = "密码不能为空！";
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated()) {
                    token.setRememberMe(true);
                    currentUser.login(token);
                }
                response.sendRedirect("/index");
                //return indexPage;
            }
        } catch (UnknownAccountException e) {
            loginMsg = "用户名或密码不正确！";
        } catch (IncorrectCredentialsException e) {
            loginMsg = "用户名或密码不正确！";
        } catch (LockedAccountException e) {
            loginMsg = "账号已被锁定，请联系管理员！";
        } catch (Exception e) {
            loginMsg = e.getMessage();
            logger.error("捕获异常：", e);
        }
        return loginMsg;
    }

    /**
     * 
     * <b>标题: </b>首页 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-28 下午11:04:38 <br/>
     * <b>修改记录: </b>
     * 
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return indexPage;
    }

    /**
     * 
     * <b>标题: </b>操作管理页面 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016年8月29日 下午8:37:25 <br/>
     * <b>修改记录: </b>
     * 
     * @param modelMap
     * @return
     */
    @RequiresPermissions("REMOVE")
    @RequestMapping("/system/operate/list")
    public String operateList(ModelMap modelMap) {
        modelMap.put("page", "/system/operate/list");
        return indexPage;
    }

    /**
     * 
     * <b>标题: </b>新闻管理页面 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016年8月29日 下午8:41:51 <br/>
     * <b>修改记录: </b>
     * 
     * @param modelMap
     * @return
     */
    @RequiresRoles("articlemanage")
    @RequestMapping("/information/news/list")
    public String newsList(ModelMap modelMap) {
        modelMap.put("page", "/information/news/list");
        return indexPage;
    }

    /**
     * 
     * <b>标题: </b>注销 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-28 下午11:05:32 <br/>
     * <b>修改记录: </b>
     * 
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return loginPage;
    }
}
