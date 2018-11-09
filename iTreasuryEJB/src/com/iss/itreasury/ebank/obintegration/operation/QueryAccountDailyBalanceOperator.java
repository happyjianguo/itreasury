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
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryAccountDailyBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseQueryAccountDailyBalance;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseSubmitInstruction;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;

/**
 * @author xintan
 *
 *  账户每日余额查询器。
 *  不允许批量查询
 */
public class QueryAccountDailyBalanceOperator implements IOperator {

    private static final long OPERATIONTYPE = OperationType.QUERY_ACCOUNTDAILYBALANCE;
    /**
     * 
     */
    public QueryAccountDailyBalanceOperator() {
        super();
    }

    public String handle(RequestXMLInfo requestXMLInfo) {      
        RequestQueryAccountDailyBalance requestInfo = (RequestQueryAccountDailyBalance) requestXMLInfo;
        String strRenXml = "";
        Collection colReqQueryAccounts = null;
        Collection cResQueryResult = null;
        Document doc = null;    
        ResponseQueryAccountDailyBalance responseQueryAccBalanceXMLInfo = new ResponseQueryAccountDailyBalance();
        OBXmlInstrDao dao = new OBXmlInstrDao();
        Iterator it = null;
        RequestQueryAccountDailyBalance.ReqAccBalanceQuery  renQuery = null;
        QueryAccountWhereInfo qInfo = null;
        try
        {
            requestInfo.setOperationType("" + OPERATIONTYPE); //查询帐户余额
            
            colReqQueryAccounts = requestInfo.getReqQuery();
            if(colReqQueryAccounts==null)        
            {
                throw new IException("查询账户信息不能为空");
            }
            else if(colReqQueryAccounts!=null&&colReqQueryAccounts.size()>1)
            {
                throw new IException("只允许有一条账户信息");
            }
            else
            {
                it = colReqQueryAccounts.iterator() ;
                while(it!=null&&it.hasNext())
                {
                    renQuery = (RequestQueryAccountDailyBalance.ReqAccBalanceQuery)it.next();
                    qInfo = renQuery.toQueryBalanceForm();
                    cResQueryResult= dao.queryAccountBalance(qInfo);
                }
                if(cResQueryResult==null)
                {
                    throw new IException("没有查到匹配的账户余额");
                }
                else
                {
                    responseQueryAccBalanceXMLInfo.addQueryResult(cResQueryResult);
                    //响应类生成响应包Doc
                    doc = (Document) responseQueryAccBalanceXMLInfo.unmarshal(null);
                    //将doc转换为string
                    strRenXml = XMLHelper.getXMLString(doc,"GBK");                  
                }
            }
        }catch(Exception e)
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
            Log.print("resultAss.setExceptionInfo = "+assemble.getExceptionInfo());
            ResponseSubmitInstruction responseInstrXMLInfo = new ResponseSubmitInstruction();
            responseInstrXMLInfo.setOperationType("" + OPERATIONTYPE);//操作类型4
            //定义实体类
            FinanceInfo info = new FinanceInfo();
            info.setStatus(-1);
            assemble.setFinanceInfo(info);  
            assemble.getFinanceInfo().setApplyCode("-1");
            try {
                responseInstrXMLInfo.addAssemble(assemble);
                //响应类生成响应包Doc
                doc = (Document) responseInstrXMLInfo.unmarshal(null);
               //将doc转换为string
                strRenXml = XMLHelper.getXMLString(doc,"GBK");                     
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
            return strRenXml;
    }

}
