package org.wxm.summary.system.view;

import org.wxm.summary.system.bean.SysMenu;

/**
 * <b>标题: </b>系统菜单视图类 <br/>
 * <b>描述: </b> <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-16 下午9:57:22 <br/>
 * <b>修改记录: </b>
 * 
 */
public class VSysMenu extends SysMenu {
    private String opCode; // 操作代码

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

}
