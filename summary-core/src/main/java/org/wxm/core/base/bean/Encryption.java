package org.wxm.core.base.bean;

/**
 * <b>标题: </b>加密信息 <br/>
 * <b>描述: </b>用于返回加密后的密文及盐值信息 <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-25 下午11:07:52 <br/>
 * <b>修改记录: </b>
 * 
 */
public class Encryption {
    private String password; // 密码
    private String salt; // 盐值

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
