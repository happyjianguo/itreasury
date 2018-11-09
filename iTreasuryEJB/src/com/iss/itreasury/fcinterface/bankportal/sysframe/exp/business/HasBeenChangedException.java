/**
 * created on Apr 18, 2008
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business;

/**
 * @author xintan
 * 
 * 记录已被修改异常
 * 
 * 更新数据库时发现待更新记录发生改变，抛出此异常
 *
 */
public class HasBeenChangedException extends BusinessException {

    /**
     * @param string
     */
    public HasBeenChangedException() {
        super("记录已被修改");
    }
}
