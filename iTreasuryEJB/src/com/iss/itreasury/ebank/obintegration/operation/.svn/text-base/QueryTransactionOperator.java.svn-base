/**
 * created on Mar 12, 2008
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
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryTransaction;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseQueryTransaction;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseSubmitInstruction;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;

/**
 * @author xintan
 * 
 * 交易流水查询器。
 * 一次只能查询一个账户的交易流水
 * 查询的起止日期不能超过31天
 *
 */
public class QueryTransactionOperator implements IOperator {

    private static final long OPERATIONTYPE = OperationType.QUERY_TRANSACTION;
    
    public QueryTransactionOperator() {
        super();
    }

    public String handle(RequestXMLInfo requestXMLInfo) {
        RequestQueryTransaction requestInfo = (RequestQueryTransaction) requestXMLInfo;
        String strRenXml = "";
        Collection colReqQueryAccounts = null;//获得到的请求指令集
        Collection cResQueryResult = null;//返回的结果集
        Document doc = null;    //doc 转化
        ResponseQueryTransaction responseInfo = new ResponseQueryTransaction();
        
        OBXmlInstrDao dao = new OBXmlInstrDao();
        AccountInfo aInfo = null;
        Iterator it = null;
        long MaxIntervalDays =31;//查询的起止日期不能超过31天
        colReqQueryAccounts = requestInfo.getReqQuery();
        try
        {       
            responseInfo.setOperationType("" + OPERATIONTYPE); //3 为交易流水查询
            
            if(colReqQueryAccounts!=null&&colReqQueryAccounts.size()==1) //只允许有一条指令集
            {
                it=colReqQueryAccounts.iterator();
                while(it!=null && it.hasNext())//实际集合中只有一条指令,如果有多条结果即为异常
                {
                    long IntervalDays =0;
                    QueryTransAccountDetailWhereInfo qInfo = new  QueryTransAccountDetailWhereInfo();
                    RequestQueryTransaction.ReqTransFlowQuery  reqQuery = null;
                    reqQuery = (RequestQueryTransaction.ReqTransFlowQuery)it.next();//获取到内部类的对象
                    qInfo = reqQuery.toQueryTransForm();
                    IntervalDays=DataFormat.getTime(qInfo.getStartDate(),qInfo.getEndDate());
                    
                    if(IntervalDays>MaxIntervalDays)
                    {
                        throw new IException("查询起止日期不能超过31天");
                    }
                    Log.print("--查询账户信息 start ---");
                    aInfo = dao.getAccountInfobyAcctNo(qInfo.getAccountNo());//获取账户相关信息
                    Log.print("--end Account Info --");
                    this.formatAccountDetailInfo(qInfo,aInfo);//将该帐户号的相关信息置入qInfo
                    QTransAccountDetail qTransDetail = new QTransAccountDetail();
                    Log.print("开始对帐单查询---SAP-EBANK 调用");
                    cResQueryResult= qTransDetail.queryTransAccountDetail(qInfo);
                }
                
                responseInfo.addResult(cResQueryResult);
                //将doc转换为string
                strRenXml = XMLHelper.getXMLString((Document) responseInfo.unmarshal(null),"GBK");
            }
            else
            {
                throw new IException("只允许有一条查询指令");
            }
             
        }catch(Exception e )
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
            responseInstrXMLInfo.setOperationType(String.valueOf(3)); //操作类型3
            //定义实体类
            FinanceInfo info = new FinanceInfo();
            info.setStatus(-1);
            info.setApplyCode("-1");
            assemble.setFinanceInfo(info);          
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
    
    /**
     * 将从SAP过来的只有帐户号现将帐户信息补充完整
     * @param qInfo
     * @param aInfo
     * @throws Exception
     */
    private void formatAccountDetailInfo(QueryTransAccountDetailWhereInfo qInfo, AccountInfo aInfo) throws Exception
    {
       try
       {
           if(aInfo==null)
               throw new IException("没有匹配的账户信息");
           else
           {
               qInfo.setAccountID(aInfo.getAccountID());
               qInfo.setOfficeID(aInfo.getOfficeID());
               qInfo.setCurrencyID(aInfo.getCurrencyID());
           }
               
               
       }catch(Exception e)
       {
           throw e ;
       }
    }       

}
