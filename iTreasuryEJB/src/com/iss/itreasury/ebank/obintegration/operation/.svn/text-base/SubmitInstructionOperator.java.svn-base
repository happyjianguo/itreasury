/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.operation;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;

import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstr;
import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrHome;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BatchImportAssemble;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.judgement.IsRepeatedApplyCodeJudgement;
import com.iss.itreasury.ebank.obintegration.constant.OperationType;
import com.iss.itreasury.ebank.obintegration.constant.TransType;
import com.iss.itreasury.ebank.obintegration.dao.OBXmlInstrDao;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestSubmitInstruction;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseSubmitInstruction;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 *
 *  财企接口指令提交操作类。根据客户端请求生成复核的指令信息
 */
public class SubmitInstructionOperator implements IOperator {
    
    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
    
    private static final long OPERATIONTYPE = OperationType.SUBMIT_INSTRUCTION;
    
    public String handle(RequestXMLInfo requestXMLInfo) {
        RequestSubmitInstruction requestInfo = (RequestSubmitInstruction) requestXMLInfo;
        String strRenXml = "";
        Collection cReqInstr = null;
        Document doc = null;    
        ResponseSubmitInstruction responseInfo = new ResponseSubmitInstruction();

        Iterator it = null; 
        long lSource = -1; //来源
        try
        {
            //指定操作类型            
            responseInfo.setOperationType("" + OPERATIONTYPE);
            /* 初始化EJB */

            OBFinanceInstrHome financeInstrHome 
                = (OBFinanceInstrHome) EJBHomeFactory.getFactory().lookUpHome(OBFinanceInstrHome.class);
            
            OBFinanceInstr financeInstr = financeInstrHome.create();
           
            //设置来源
            lSource = getSourceBySystemID(requestInfo);
            cReqInstr = requestInfo.getReqInstr();
            if(cReqInstr != null)       
            {
                it = cReqInstr.iterator();
            }
            while(it != null && it.hasNext())
            {
                RequestSubmitInstruction.ReqInstr reqInstr = null;
                //定义实体类
                BatchImportAssemble assemble = new BatchImportAssemble();
                FinanceInfo info = new FinanceInfo();
                try
                {
                    long lInstrID = -1;
                    long lRepeat = -1;
                    long lReturn = -1;//判断复核是否成功
                    //取下一条条件记录
                    reqInstr = (RequestSubmitInstruction.ReqInstr) it.next();                
                    //设置来源
                    info.setSource(lSource);
                    //将xml条件实体类转化为查询实体类
                    this.TransInstrXmlToFinanceInfo(reqInstr,info);                             
                    assemble.setFinanceInfo(info);                      
                    //add()
                    if(IsRepeatedApplyCodeJudgement.judge(info))
                    {
                        throw new IException("业务申请编号重复");
                    }
                    
                    lInstrID = financeInstr.addCapitalTrans(info);                      
                    //新增成功
                    if(lInstrID>0)
                    {
                        assemble.getFinanceInfo().setID(lInstrID);  
                        assemble.getFinanceInfo().setCheckUserID(info.getConfirmUserID());  
                        assemble.getFinanceInfo().setStatus(OBConstant.SettInstrStatus.SAVE);
                        assemble.getFinanceInfo().setSource(lSource);
                        lReturn = financeInstr.check(assemble.getFinanceInfo());
                        //复核成功
                        if(lReturn>0)
                        {
                            assemble.getFinanceInfo().setStatus(OBConstant.SettInstrStatus.CHECK);
                        }
                    }                   
                }
                catch(Exception e)
                {
                    //保存出错信息
                    String strErroMessage = IExceptionMessage.getExceptionMSG(e);
                    if(strErroMessage!=null && strErroMessage.length()>0)
                    {
                        assemble.setExceptionInfo(strErroMessage);
                    }
                    else
                    {
                        assemble.setExceptionInfo(e.getMessage());
                    }
                    log.print("resultAss.setExceptionInfo = "+assemble.getExceptionInfo());
                    //保存状态 失败
                    FinanceInfo tmpInfo = new FinanceInfo();
                    tmpInfo.setApplyCode("-1");
                    assemble.setFinanceInfo(tmpInfo);
                    assemble.getFinanceInfo().setStatus(-1);
                    assemble.setImportStatus(-1);
                    log.print(e.toString());
                    //保存信息
                    assemble.setFinanceInfo(info);  
                }               
                //加载结果信息到响应包实体类
                responseInfo.addAssemble(assemble);
            }
            //响应类生成响应包Doc
            doc = (Document) responseInfo.unmarshal(null);
            //将doc转换为string
            strRenXml = XMLHelper.getXMLString(doc,"GBK");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error("财企接口，处理指令申请出错：" + e.toString());                    
        }
        return strRenXml;
    }

    private long getSourceBySystemID(RequestXMLInfo requestInfo) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     *  将请求申请xml实体类转换为FinanceInfo
     * @param RequestInstrXMLInfo.ReqInstr
     * @return FinanceInfo
     * @throws Exception
     */
    private void TransInstrXmlToFinanceInfo(RequestSubmitInstruction.ReqInstr reqInstr,FinanceInfo info ) throws Exception
    {       
        double dTotalAmount = 0.0;                  //合计金额
        Timestamp dtExcute = null;                  //执行日
        Timestamp dtSystem = Env.getSystemDate();   //系统日期
        OBXmlInstrDao dao = new OBXmlInstrDao();
        try
        {
            //业务申请编号 非空限制
            if(reqInstr.getApplyCode()!= null && reqInstr.getApplyCode().length()>0)
            {               
                info.setApplyCode(reqInstr.getApplyCode());
            }
            else
            {
                info.setSBatchNo("");  
                throw new IException("业务申请编号为空");
            }
            //交易类型 
            if(TransType.YHFK.equals(reqInstr.getTransType()))
            {
                //资金划拨-银行付款，汇款方式为银行付款
                info.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
                info.setTransType(OBConstant.SettInstrType.getName(info.getTransType()));
                info.setRemitType(OBConstant.SettRemitType.BANKPAY);
                info.setRemitType(OBConstant.SettRemitType.getName(info.getRemitType()));
            }
            else if(TransType.BZ.equals(reqInstr.getTransType()))
            {
                //资金划拨-本转，汇款方式为本转
                info.setTransType(OBConstant.SettInstrType.CAPTRANSFER_SELF);
                info.setTransType(OBConstant.SettInstrType.getName(info.getTransType()));
                info.setRemitType(OBConstant.SettRemitType.SELF);
                info.setRemitType(OBConstant.SettRemitType.getName(info.getRemitType()));
            }
            else
            {
                info.setTransType(-1);
                info.setTransType("错误类型");
                throw new IException("错误交易类型");
            }
            //金额 非空限制
            if(reqInstr.getAmount()!=null && reqInstr.getAmount().length()>0)
            {
                try
                {
                    dTotalAmount = Double.valueOf(reqInstr.getAmount()).doubleValue();
                    info.setAmount(dTotalAmount);   
                }
                catch(Exception e) 
                {
                    info.setAmount(0.00);   
                    throw new IException("金额格式错误"); 
                }
                //modify by yuanxue for  2007-10-25 
                if(dTotalAmount <0)
                {
                    throw new IException("金额不能为负数");
                }
                
            }
            else
            {
                info.setAmount(0.00);   
                throw new IException("金额为空");
            }
            //执行日,不能为系统时间之前
            if(reqInstr.getExcuteDate()!=null && reqInstr.getExcuteDate().length()>0)
            {
                try
                {
                    dtExcute = DataFormat.getDateTime(reqInstr.getExcuteDate());
                }
                catch(Exception e) 
                {                   
                    info.setExecuteDate(null);
                    throw new IException("执行日错误");  
                }
                //here jdk版本问题待能打包后取出
                if(DataFormat.getTime(dtSystem,dtExcute)<0)
                {
                    info.setExecuteDate(null);
                    throw new IException("执行日在系统日之前");                  
                }
                else
                {
                    info.setExecuteDate(dtExcute);
                }                               
            }
            else
            {       
                info.setExecuteDate(null);  
                throw new IException("执行日为空");
            }
            //客户编号（操作业务的客户）
            if(reqInstr.getClientCode()!=null && reqInstr.getClientCode().length()>0)
            {
                long lClientID = dao.getClientIDByClientCode(reqInstr.getClientCode());
                if (lClientID>0)
                {
                    info.setClientID(lClientID);
                }
                else
                {
                    info.setClientID(-1);
                    throw new IException("客户编号错误");
                }
            }
            else
            {
                info.setClientID(-1);
                throw new IException("客户编号为空");
            }   
            //付款方账号
            if(reqInstr.getPayerAcctNo()!=null && reqInstr.getPayerAcctNo().length()>0)
            {
                AccountInfo acctInfo = dao.getAccountInfobyAcctNo(reqInstr.getPayerAcctNo());
                if(acctInfo==null || acctInfo.getAccountID()<0)
                {
                    info.setPayerAcctID(-1);
                    throw new IException("付款方账号错误");
                }
                else
                {
                    info.setPayerAcctID(acctInfo.getAccountID());
                    info.setPayerAcctNo(acctInfo.getAccountNo());
                    info.setPayerName(acctInfo.getAccountName());
                    info.setCurrencyID(acctInfo.getCurrencyID());//币种
                    info.setOfficeID(acctInfo.getOfficeID());//办事处
                }
            }
            else
            {
                info.setPayerAcctID(-1);
                throw new IException("付款方账号为空");
            }
            //收款方信息
            //银行付款方式时为外部账户
            if(info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY)
            {
                //收款方账号
                if(reqInstr.getPayeeAcctNo()!=null && reqInstr.getPayeeAcctNo().length()>0)
                {
                    info.setPayeeAcctNo(reqInstr.getPayeeAcctNo());
                }   
                else
                {
                    info.setPayeeAcctNo("收款方账户为空"); 
                    throw new IException("收款方账户为空");        
                }
                //收款方名称
                if(reqInstr.getPayeeAcctName()!=null && reqInstr.getPayeeAcctName().length()>0)
                {
                    info.setPayeeName(reqInstr.getPayeeAcctName());
                }   
                else
                {
                    info.setPayeeName("收款方名称为空");   
                    throw new IException("收款方名称为空");        
                }
                //汇入地 省
                if(reqInstr.getRemitProvince()!=null && reqInstr.getRemitProvince().length()>0)
                {
                    info.setPayeeProv(reqInstr.getRemitProvince());
                }   
                else
                {
                    info.setPayeeProv("汇入地（省）为空");  
                    throw new IException("汇入地（省）为空");
                }           
                //汇入地 市（县）
                if(reqInstr.getRemitCity()!=null && reqInstr.getRemitCity().length()>0)
                {
                    info.setPayeeCity(reqInstr.getRemitCity());
                }   
                else
                {
                    info.setPayeeCity("汇入地（市）为空");  
                    throw new IException("汇入地（市）为空");
                }   
                //汇入行名称
                if(reqInstr.getRemitBankName()!=null && reqInstr.getRemitBankName().length()>0)
                {
                    info.setPayeeBankName(reqInstr.getRemitBankName());
                }   
                else
                {
                    info.setPayeeBankName("汇入行名称为空");   
                    throw new IException("汇入行名称为空");        
                }
                //汇入行编码
//                if(reqInstr.getRemitBankCode()!=null && reqInstr.getRemitBankCode().length()>0)
//                {
//                    if(reqInstr.getRemitBankCode().equalsIgnoreCase(String.valueOf(SETTConstant.BankType.BC))
//                    ||reqInstr.getRemitBankCode().equalsIgnoreCase(String.valueOf(SETTConstant.BankType.ICBC))
//                    ||reqInstr.getRemitBankCode().equalsIgnoreCase(String.valueOf(SETTConstant.BankType.CCB))
//                    ||reqInstr.getRemitBankCode().equalsIgnoreCase(String.valueOf(SETTConstant.BankType.ABOCN))
//                    ||reqInstr.getRemitBankCode().equalsIgnoreCase(String.valueOf(SETTConstant.BankType.CMBCHINA))
//                    ||reqInstr.getRemitBankCode().equalsIgnoreCase("99"))
//                    {
//                        info.setSRemitBankCode(reqInstr.getRemitBankCode());
//                    }
//                    else
//                    {
//                        info.setSRemitBankCode("汇入行编码不是设定的银行编码");
//                        throw new IException("汇入行编码不是设定的银行编码，请检查");
//                    }
//                }
//                else
//                {
//                    info.setSRemitBankCode("汇入行编码为空，请检查");
//                    throw new IException("汇入行编码为空，请检查");
//                }
            }
            else if(info.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_SELF)
            {
                if(reqInstr.getPayeeAcctNo()!=null && reqInstr.getPayeeAcctNo().length()>0)
                {
                    AccountInfo acctInfo1 = dao.getAccountInfobyAcctNo(reqInstr.getPayeeAcctNo());
                    if(acctInfo1==null || acctInfo1.getAccountID()<0)
                    {
                        info.setPayeeAcctID(-1);
                        throw new IException("收款方账号错误");
                    }
                    else
                    {
                        info.setPayeeAcctID(acctInfo1.getAccountID());
                        info.setPayeeAcctNo(acctInfo1.getAccountNo());
                        info.setPayeeName(acctInfo1.getAccountName());                      
                    }
                }
                else
                {
                    info.setPayeeAcctNo("收款方账户为空"); 
                    throw new IException("收款方账户为空");        
                }
            }
            //提交人
            if(reqInstr.getConfirmUser()!=null && reqInstr.getConfirmUser().length()>0)
            {
                long lUserID = dao.getComfirmUser(reqInstr.getConfirmUser(),info.getClientID(),reqInstr.getClientCode());
                if( lUserID >0)
                {
                    info.setConfirmUserID(lUserID);
                }
                else
                {
                    throw new IException("提交人错误");
                }
            }
            else
            {
                throw new IException("提交人为空");
            }
            //提交时间
            if(reqInstr.getConfirmTime()!=null && reqInstr.getConfirmTime().length()>0)
            {               
                info.setConfirmDate(Timestamp.valueOf(reqInstr.getConfirmTime()));
            }
            else
            {
                throw new IException("提交时间为空");
            }
            //汇款用途
            if(reqInstr.getNote()!=null && reqInstr.getNote().length()>0)   
            {
                info.setNote(reqInstr.getNote());
            }
        }
        catch(Exception e)
        {
            throw e;            
        }       
    }   

}
