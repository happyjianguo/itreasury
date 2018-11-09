/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration;

import org.w3c.dom.Document;

import com.iss.itreasury.ebank.obintegration.constant.OperationType;
import com.iss.itreasury.ebank.obintegration.operation.IOperator;
import com.iss.itreasury.ebank.obintegration.operation.SubmitInstructionOperator;
import com.iss.itreasury.ebank.obintegration.util.RequestAssember;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryAccountCurrentBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryAccountDailyBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryInstructionStatus;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryTransaction;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestSubmitInstruction;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseSubmitInstruction;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 * 
 * 网银财企接口处理器。用于处理企业应用系统传来的请求，并给出响应
 *
 */
public class OBServerFacade {

    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
    
    /**
     * 
     */
    public OBServerFacade() {
        super();
    }
    
    /**
     * 该函数不抛出异常
     * 
     * @param strRequestData 客户端请求
     * @return
     */
    public String handleRequest(String strRequestData)
    {
        String strXmlRen = "";
        RequestXMLInfo requestXMLInfo = null;
        long lOperatorType = -1;
        try
        {
            requestXMLInfo = new RequestAssember().convertToXMLInfo(strRequestData);
            if(requestXMLInfo != null)
            {           
                lOperatorType = Long.valueOf(requestXMLInfo.getOperationType()).longValue();            
            }
            
            validateSystemID(requestXMLInfo);    

            if(lOperatorType == OperationType.SUBMIT_INSTRUCTION)
            {

                RequestSubmitInstruction requestSubmitInstruction
                    = (RequestSubmitInstruction) convertToXMLInfo(strRequestData, OperationType.SUBMIT_INSTRUCTION);   
                //新增记录
                strXmlRen = this.doSave(requestSubmitInstruction);
            }
            //查找
            else if(lOperatorType == OperationType.QUERY_INSTRUCTIONSTATUS)
            {
                Document xmlDoc = null;         
                RequestQueryInstructionStatus requestQueryXMLInfo 
                    = (RequestQueryInstructionStatus) convertToXMLInfo(strRequestData, OperationType.QUERY_INSTRUCTIONSTATUS);   
                //查询记录
                strXmlRen = this.doQuery(requestQueryXMLInfo);
            }
            //操作类型3 BFDLSAP 交易流水查询
            else if(lOperatorType == OperationType.QUERY_TRANSACTION)
            {
                Document xmlDoc =null;
                RequestQueryTransaction requestQueryTransaction 
                    = (RequestQueryTransaction) convertToXMLInfo(strRequestData, OperationType.QUERY_TRANSACTION);
                strXmlRen = this.doTransFlowQuery(requestQueryTransaction);
            }
            //类型4 BFDLSAP 帐户余额查询，查询账户的历史每日余额
            else if(lOperatorType == OperationType.QUERY_ACCOUNTDAILYBALANCE)
            {
                RequestQueryAccountDailyBalance requestQueryAcctBalanceInfo
                    = (RequestQueryAccountDailyBalance) convertToXMLInfo(strRequestData, OperationType.QUERY_ACCOUNTDAILYBALANCE);
                strXmlRen = this.doAccountBalanceQuery(requestQueryAcctBalanceInfo);
            }
            //类型5 帐户余额查询,查询当前余额及可用余额
            else if(lOperatorType == OperationType.QUERY_ACCOUNTCURBALANCE)
            {
                RequestQueryAccountCurrentBalance requestQueryAccountCurrentBalance
                    = (RequestQueryAccountCurrentBalance) convertToXMLInfo(strRequestData, OperationType.QUERY_ACCOUNTCURBALANCE);
                strXmlRen =this.doCurrentAccountBalanceQuery(requestQueryAccountCurrentBalance);
            }
            //错误操作类型
            else
            {
                throw new IException("无效的操作类型");
            }
        }
        catch (Exception e) 
        {           
            log.error("财企接口，处理客户请求错误，" + e.toString());
            ResponseSubmitInstruction responseInfo = new ResponseSubmitInstruction();
            responseInfo.setOperationType(String.valueOf(lOperatorType));
            //定义实体类
//            BatchImportAssemble assemble = new BatchImportAssemble();
//            FinanceInfo info = new FinanceInfo();
//            info.setStatus(-1);
//            assemble.setFinanceInfo(info);          
//            assemble.setExceptionInfo(e.getErrorCode());
//            responseInfo.addAssemble(assemble);
            //响应类生成响应包Doc
            Document doc ;
//            doc = (Document) responseInfo.unmarshal(null);
            //将doc转换为string
//            strXmlRen = XMLHelper.getXMLString(doc,"GBK");          
        }
        
        return strXmlRen;
    }

    private String doCurrentAccountBalanceQuery(RequestQueryAccountCurrentBalance requestQueryAccountCurrentBalance) {
        // TODO Auto-generated method stub
        return null;
    }

    private String doAccountBalanceQuery(RequestQueryAccountDailyBalance requestQueryAcctBalanceInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    private String doTransFlowQuery(RequestQueryTransaction requestQueryTransaction) {
        // TODO Auto-generated method stub
        return null;
    }

    private String doQuery(RequestQueryInstructionStatus requestQueryXMLInfo) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private String doSave(RequestSubmitInstruction requestInstrXMLInfo) 
    {
        IOperator operator = new SubmitInstructionOperator();
        return operator.handle(requestInstrXMLInfo);
    }
    
    private RequestXMLInfo convertToXMLInfo(String strRequestData, long operationType) throws Exception {
        Document xmlDoc;
        
        RequestXMLInfo requestXMLInfo = null;
        switch((int)operationType)
        {
        case (int)OperationType.SUBMIT_INSTRUCTION:
            requestXMLInfo = new RequestSubmitInstruction();
            break;
        case (int) OperationType.QUERY_INSTRUCTIONSTATUS:
            requestXMLInfo = new RequestQueryInstructionStatus();
            break;
        }
        //字符串转换为doc
        xmlDoc = XMLHelper.parse(strRequestData);
        //doc转换为请求包实体类
        if(xmlDoc!=null)
        {
            requestXMLInfo.marshal(xmlDoc);
        }
        return requestXMLInfo;
    }

    /**
     * 校验请求的来源是否有效。请求来源由SystemID字段标识。
     * 
     * @param requestXMLInfo
     * @throws IException
     */
    private void validateSystemID(RequestXMLInfo requestXMLInfo) throws IException {
        if(requestXMLInfo.getSystemID()==null)
        {
            throw new IException("SystemID为空");
        }        
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
