package org.wxm.summary.security.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/loginPage")
    public String loginPage() {
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
    @RequestMapping("/login")
    public String login(String username, String password, ModelMap modelMap) {
        try {
            if (StringUtils.isEmpty(username)) {
                modelMap.put("loginMsg", "用户名不能为空！");
            } else if (StringUtils.isEmpty(password)) {
                modelMap.put("loginMsg", "密码不能为空！");
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated()) {
                    token.setRememberMe(true);
                    currentUser.login(token);
                }
                return indexPage;
            }
        } catch (UnknownAccountException e) {
            modelMap.put("loginMsg", "用户名或密码不正确！");
        } catch (IncorrectCredentialsException e) {
            modelMap.put("loginMsg", "用户名或密码不正确！");
        } catch (LockedAccountException e) {
            modelMap.put("loginMsg", "账号已被锁定，请联系管理员！");
        } catch (Exception e) {
            logger.error("捕获异常：", e);
            modelMap.put("loginMsg", e.getMessage());
        }
        return loginPage;
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
