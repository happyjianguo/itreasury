/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obintegration.operation.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.iss.itreasury.ebank.obintegration.constant.OperationType;
import com.iss.itreasury.ebank.obintegration.constant.TransType;
import com.iss.itreasury.ebank.obintegration.operation.SubmitInstructionOperator;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestSubmitInstruction;
import com.iss.itreasury.util.DataFormat;

/**
 * @author xintan
 *
 */
public class TestSubmitInstruction {

    /**
     * 
     */
    public TestSubmitInstruction() {
        super();
    }

    public String testSuccess()
    {
        SubmitInstructionOperator operator = new SubmitInstructionOperator();
        
        RequestSubmitInstruction requestInfo 
            = createSuccessRequest1();
        return operator.handle(requestInfo);
    }

    
    /**
     * @return
     */
    private RequestSubmitInstruction createSuccessRequest1() {
        RequestSubmitInstruction requestInfo = new RequestSubmitInstruction();
        
        requestInfo.setOperationType("" + OperationType.SUBMIT_INSTRUCTION);
        requestInfo.setSystemID("0001");
        Vector vector= new Vector();
        RequestSubmitInstruction.ReqInstr reqInstr = requestInfo.new ReqInstr();
        reqInstr.setAmount("2.0");
        reqInstr.setApplyCode(generateApplyCode());
        reqInstr.setClientCode("01-000004");
        reqInstr.setConfirmTime(DataFormat.formatDate(new Date(), DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));
        reqInstr.setConfirmUser("谭欣");
        reqInstr.setExcuteDate(DataFormat.formatDate(new Date(), DataFormat.FMT_DATE_YYYYMMDD));
        reqInstr.setNote("财企接口测试");
        reqInstr.setPayeeAcctName("谭欣测试用账户1");
        reqInstr.setPayeeAcctNo("01-01-000056-01");
        reqInstr.setPayerAcctNo("01-01-000004-02");
        reqInstr.setRemitBankName("工行北京支行");
        reqInstr.setRemitCity("北京");
        reqInstr.setRemitProvince("北京");
        reqInstr.setTransType(TransType.YHFK);
        
        vector.add(reqInstr);
        requestInfo.setReqInstr(vector);
        
        return requestInfo;
    }
    
    private String generateApplyCode()
    {
        SimpleDateFormat fmtDate = new SimpleDateFormat();
        
        fmtDate.applyPattern("yyyyMMddHHmmssSSS");
        
        String strApplyCode = "4" + fmtDate.format(new Date());
        
        return strApplyCode;
    }
    
//    public static void main(String args[])
//    {
//        System.out.println(new TestSubmitInstruction().generateApplyCode());
//    }
}
