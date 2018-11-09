/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.constant;

/**
 * @author xintan
 * 
 * ����ӿڣ�ָ��ִ��״̬����
 *
 */
public class InstructionExecuteStatus {

    /*01��ʧ�ܣ����ջ���ʧ��
    03�����������ͳɹ����ȴ�����˾����
    04�������У�����˾�ѽ��գ����ڴ�����
    05������ɣ�����˾����ָ��ɹ�
    06���Ѿܾ�������˾�ܾ�ָ�*/
    
    public static final String FAILED = "01";   //���ջ���ʧ��
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
