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
 *  判断指令的执行情况。根据指令的判断来判断
 */
public class InstructionExecuteStatusJudgement {

    /**
     * 根据指令状态，判断指令的执行情况
     * 
     * @param instructionStatus
     * @return
     */
    public static String judge(long instructionStatus)
    {
        /*01：失败；接收或处理失败
        03：待处理；发送成功，等待财务公司处理
        04：处理中；财务公司已接收，正在处理中
        05：已完成；财务公司处理指令成功
        06：已拒绝；财务公司拒绝指令。*/
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
     * 根据指令状态，返回指令执行情况的描述信息
     * 
     * @param financeInfo
     * @return
     */
    public static String getInstructionExecuteDesc(FinanceInfo financeInfo)
    {
        /*01：失败；接收或处理失败
        03：待处理；发送成功，等待财务公司处理
        04：处理中；财务公司已接收，正在处理中
        05：已完成；财务公司处理指令成功
        06：已拒绝；财务公司拒绝指令。*/
        String strExecuteDesc = null;

        strExecuteDesc = OBConstant.SettInstrStatus.getName(financeInfo.getStatus());
        //如果被拒绝，返回拒绝原因
        if(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)
        {
            strExecuteDesc = financeInfo.getReject();
        }
        
        return strExecuteDesc;
    }
}
