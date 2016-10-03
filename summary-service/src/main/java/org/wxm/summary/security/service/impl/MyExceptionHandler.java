package org.wxm.summary.security.service.impl;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * <b>标题: </b> <br/>
 * <b>描述: </b> <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016年9月24日 下午9:59:30 <br/>
 * <b>修改记录: </b>
 * 
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 根据不同错误转向不同页面
        if (ex instanceof UnauthorizedException) {
            logger.error("-----unauthorized-------");
            return new ModelAndView("/errors/unauthorized");
        } else if (ex instanceof SQLException) {
            logger.error("-----401-------");
            return new ModelAndView("/errors/401");
        } else {
            logger.error("-----500-------");
            return new ModelAndView("/errors/500");
        }
    }

}
