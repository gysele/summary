package org.wxm.summary.security.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wxm.summary.security.dao.SecurityDao;
import org.wxm.summary.security.view.UserDetail;
import org.wxm.summary.system.view.VSysMenu;

/**
 * <b>标题: </b>用户权限DOMAIN实现类 <br/>
 * <b>描述: </b> <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-28 下午6:22:36 <br/>
 * <b>修改记录: </b>
 * 
 */
@Service
public class SecurityDomainImpl implements SecurityDomain {
    @Autowired
    private SecurityDao securityDao; // 用户权限DAO接口

    /**
     * 
     * <b>标题: </b>根据用户名查找用户 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-19 下午9:11:55 <br/>
     * <b>修改记录: </b>
     * 
     * @param username
     *            用户名
     * @return
     */
    @Override
    public UserDetail findUserByUsername(String username) {
        return securityDao.findUserByUsername(username);
    }

    /**
     * 
     * <b>标题: </b>根据用户ID查找菜单操作权限 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-20 上午12:24:57 <br/>
     * <b>修改记录: </b>
     * 
     * @param userId
     *            用户ID
     * @return
     */
    @Override
    public List<VSysMenu> findMenuOperatorByUserId(String userId) {
        return securityDao.findMenuOperatorByUserId(userId);
    }

}
