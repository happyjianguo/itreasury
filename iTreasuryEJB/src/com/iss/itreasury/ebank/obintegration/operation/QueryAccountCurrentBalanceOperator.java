/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obintegration.operation;

import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BatchImportAssemble;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obintegration.constant.OperationType;
import com.iss.itreasury.ebank.obintegration.dao.OBXmlInstrDao;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryAccountCurrentBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseQueryAccountCurrentBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseSubmitInstruction;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 * 
 *  账户当前余额和可用余额查询器。返回账户的当前余额和可用余额。
 *  当前不支持批量查询
 */
public class QueryAccountCurrentBalanceOperator implements IOperator {

    private static final long OPERATIONTYPE = OperationType.QUERY_INSTRUCTIONSTATUS;
    
    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
    /**
     * 
     */
    public QueryAccountCurrentBalanceOperator() {
        super();
    }

    public String handle(RequestXMLInfo requestXMLInfo) {
        RequestQueryAccountCurrentBalance requestInfo 
                = (RequestQueryAccountCurrentBalance) requestXMLInfo;
        
        String strRenXml = "";
        Collection colRequestAccounts = null;
        Collection cResQueryResult = null;
        RequestQueryAccountCurrentBalance.ReqCurrentAccountBalanceQuery  renQuery = null;
        ResponseQueryAccountCurrentBalance responseInfo = new ResponseQueryAccountCurrentBalance();
        OBXmlInstrDao dao = new OBXmlInstrDao();
        QueryAccountWhereInfo qInfo = null;
        Iterator it = null;
        AccountInfo  aInfo = null;
        
        try
        {
            colRequestAccounts = requestInfo.getVReqQuery();
            if(colRequestAccounts!=null
                    && colRequestAccounts.size() >0)
            {
                responseInfo.setOperationType("" + OPERATIONTYPE); 
                it =colRequestAccounts.iterator() ;
                renQuery = (RequestQueryAccountCurrentBalance.ReqCurrentAccountBalanceQuery )it.next() ;
                qInfo = renQuery.toQueryAccountAmountForm();
                aInfo=dao.getAccountInfobyAcctNo(qInfo.getStartAccountNo());
                
                if(aInfo==null)
                {
                    throw new IException("没有查到匹配的账户信息");
                }   
                
                cResQueryResult= dao.getCurrenctBalance(qInfo,aInfo);
                
                responseInfo.addQueryResult(cResQueryResult);  
                //将doc转换为string
                strRenXml = XMLHelper.getXMLString((Document) responseInfo.unmarshal(null),"GBK");                  
            }

        }
        catch(Exception e)
        {
            //保存出错信息
            BatchImportAssemble assemble = new BatchImportAssemble();
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
            ResponseSubmitInstruction responseInstrXMLInfo = new ResponseSubmitInstruction();
            responseInstrXMLInfo.setOperationType("" + OPERATIONTYPE);//操作类型4
            //定义实体类
            FinanceInfo info = new FinanceInfo();
            info.setStatus(-1);
            assemble.setFinanceInfo(info);          
            try {
                responseInstrXMLInfo.addAssemble(assemble);
                assemble.getFinanceInfo().setApplyCode("-1");
                //将doc转换为string
                 strRenXml = XMLHelper.getXMLString((Document) responseInstrXMLInfo.unmarshal(null),"GBK");                                 
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        
        return strRenXml;
    }
}
