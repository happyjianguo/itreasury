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
 * ������ˮ��ѯ����
 * һ��ֻ�ܲ�ѯһ���˻��Ľ�����ˮ
 * ��ѯ����ֹ���ڲ��ܳ���31��
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
        Collection colReqQueryAccounts = null;//��õ�������ָ�
        Collection cResQueryResult = null;//���صĽ����
        Document doc = null;    //doc ת��
        ResponseQueryTransaction responseInfo = new ResponseQueryTransaction();
        
        OBXmlInstrDao dao = new OBXmlInstrDao();
        AccountInfo aInfo = null;
        Iterator it = null;
        long MaxIntervalDays =31;//��ѯ����ֹ���ڲ��ܳ���31��
        colReqQueryAccounts = requestInfo.getReqQuery();
        try
        {       
            responseInfo.setOperationType("" + OPERATIONTYPE); //3 Ϊ������ˮ��ѯ
            
            if(colReqQueryAccounts!=null&&colReqQueryAccounts.size()==1) //ֻ������һ��ָ�
            {
                it=colReqQueryAccounts.iterator();
                while(it!=null && it.hasNext())//ʵ�ʼ�����ֻ��һ��ָ��,����ж��������Ϊ�쳣
                {
                    long IntervalDays =0;
                    QueryTransAccountDetailWhereInfo qInfo = new  QueryTransAccountDetailWhereInfo();
                    RequestQueryTransaction.ReqTransFlowQuery  reqQuery = null;
                    reqQuery = (RequestQueryTransaction.ReqTransFlowQuery)it.next();//��ȡ���ڲ���Ķ���
                    qInfo = reqQuery.toQueryTransForm();
                    IntervalDays=DataFormat.getTime(qInfo.getStartDate(),qInfo.getEndDate());
                    
                    if(IntervalDays>MaxIntervalDays)
                    {
                        throw new IException("��ѯ��ֹ���ڲ��ܳ���31��");
                    }
                    Log.print("--��ѯ�˻���Ϣ start ---");
                    aInfo = dao.getAccountInfobyAcctNo(qInfo.getAccountNo());//��ȡ�˻������Ϣ
                    Log.print("--end Account Info --");
                    this.formatAccountDetailInfo(qInfo,aInfo);//�����ʻ��ŵ������Ϣ����qInfo
                    QTransAccountDetail qTransDetail = new QTransAccountDetail();
                    Log.print("��ʼ���ʵ���ѯ---SAP-EBANK ����");
                    cResQueryResult= qTransDetail.queryTransAccountDetail(qInfo);
                }
                
                responseInfo.addResult(cResQueryResult);
                //��docת��Ϊstring
                strRenXml = XMLHelper.getXMLString((Document) responseInfo.unmarshal(null),"GBK");
            }
            else
            {
                throw new IException("ֻ������һ����ѯָ��");
            }
             
        }catch(Exception e )
        {
            //���������Ϣ
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
            responseInstrXMLInfo.setOperationType(String.valueOf(3)); //��������3
            //����ʵ����
            FinanceInfo info = new FinanceInfo();
            info.setStatus(-1);
            info.setApplyCode("-1");
            assemble.setFinanceInfo(info);          
            try {
                responseInstrXMLInfo.addAssemble(assemble);
                //��Ӧ��������Ӧ��Doc
                doc = (Document) responseInstrXMLInfo.unmarshal(null);
               //��docת��Ϊstring
                strRenXml = XMLHelper.getXMLString(doc,"GBK");         

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }                   
        return strRenXml;
    }
    
    /**
     * ����SAP������ֻ���ʻ����ֽ��ʻ���Ϣ��������
     * @param qInfo
     * @param aInfo
     * @throws Exception
     */
    private void formatAccountDetailInfo(QueryTransAccountDetailWhereInfo qInfo, AccountInfo aInfo) throws Exception
    {
       try
       {
           if(aInfo==null)
               throw new IException("û��ƥ����˻���Ϣ");
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
