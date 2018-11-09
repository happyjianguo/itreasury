/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.judgement;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obintegration.constant.InstructionExecuteStatus;
import com.iss.itreasury.ebank.util.OBConstant;

/**
 * @author xintan
 *
 *  �ж�ָ���ִ�����������ָ����ж����ж�
 */
public class InstructionExecuteStatusJudgement {

    /**
     * ����ָ��״̬���ж�ָ���ִ�����
     * 
     * @param instructionStatus
     * @return
     */
    public static String judge(long instructionStatus)
    {
        /*01��ʧ�ܣ����ջ���ʧ��
        03�����������ͳɹ����ȴ�����˾����
        04�������У�����˾�ѽ��գ����ڴ�����
        05������ɣ�����˾����ָ��ɹ�
        06���Ѿܾ�������˾�ܾ�ָ�*/
        String strExecuteStatus = null;

        switch((int) instructionStatus)
        {
        case (int) OBConstant.SettInstrStatus.SAVE:
        case (int) OBConstant.SettInstrStatus.CHECK:
        case (int) OBConstant.SettInstrStatus.SIGN:
            strExecuteStatus = InstructionExecuteStatus.SUBMITED;
            break;
        case (int) OBConstant.SettInstrStatus.DEAL:      
            strExecuteStatus = InstructionExecuteStatus.PROCESSING;
            break;
        case (int) OBConstant.SettInstrStatus.FINISH:    
            strExecuteStatus = InstructionExecuteStatus.FINISHED;
            break;
        case (int) OBConstant.SettInstrStatus.REFUSE:    
            strExecuteStatus = InstructionExecuteStatus.REJECTED;
            break;            
        case (int) OBConstant.SettInstrStatus.APPROVALING:    
        case (int) OBConstant.SettInstrStatus.APPROVALED:
        case (int) OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL:
        case (int) OBConstant.SettInstrStatus.DOAPPRVOAL:
        case (int) OBConstant.SettInstrStatus.CANCELAPPRVOAL:   
        case (int) OBConstant.SettInstrStatus.DELETE:                    
            strExecuteStatus = InstructionExecuteStatus.SUBMITED;
            break;            
        default:
            strExecuteStatus = InstructionExecuteStatus.FAILED;
        }
        
        return strExecuteStatus;
    }
    
    /**
     * ����ָ��״̬������ָ��ִ�������������Ϣ
     * 
     * @param financeInfo
     * @return
     */
    public static String getInstructionExecuteDesc(FinanceInfo financeInfo)
    {
        /*01��ʧ�ܣ����ջ���ʧ��
        03�����������ͳɹ����ȴ�����˾����
        04�������У�����˾�ѽ��գ����ڴ�����
        05������ɣ�����˾����ָ��ɹ�
        06���Ѿܾ�������˾�ܾ�ָ�*/
        String strExecuteDesc = null;

        strExecuteDesc = OBConstant.SettInstrStatus.getName(financeInfo.getStatus());
        //������ܾ������ؾܾ�ԭ��
        if(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)
        {
            strExecuteDesc = financeInfo.getReject();
        }
        
        return strExecuteDesc;
    }
}
