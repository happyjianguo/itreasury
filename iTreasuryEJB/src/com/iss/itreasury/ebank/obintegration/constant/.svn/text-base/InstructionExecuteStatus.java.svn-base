/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.constant;

/**
 * @author xintan
 * 
 * 财企接口，指令执行状态定义
 *
 */
public class InstructionExecuteStatus {

    /*01：失败；接收或处理失败
    03：待处理；发送成功，等待财务公司处理
    04：处理中；财务公司已接收，正在处理中
    05：已完成；财务公司处理指令成功
    06：已拒绝；财务公司拒绝指令。*/
    
    public static final String FAILED = "01";   //接收或处理失败
    public static final String SUBMITED = "03";
    public static final String PROCESSING = "04";
    public static final String FINISHED = "05";    
    public static final String REJECTED = "06";
    
    private String code = null;
    
    private String message = null;

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
