/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obfinanceinstr.exp;

import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 *  业务申请编号重复异常。
 *  当外部系统提交指令申请时，提交的指令业务申请编号（唯一标识）和现有系统中的重复，则抛出此异常
 */
public class RepeatedApplyCodeException extends IException {

    /**
     * 
     */
    public RepeatedApplyCodeException() {
        super();
    }
}
