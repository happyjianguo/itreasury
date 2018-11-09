/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obintegration.operation;

import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BatchImportAssemble;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obintegration.constant.OperationType;
import com.iss.itreasury.ebank.obintegration.dao.OBXmlInstrDao;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestQueryInstructionStatus;
import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.ebank.obintegration.xmlmsg.ResponseQueryInstructionStatus;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 *
 *  财企接口指令执行情况操作类。返回客户请求的指令申请的执行信息
 */
public class QueryInstructionStatusOperator implements IOperator {

    private static final long OPERATIONTYPE = OperationType.QUERY_INSTRUCTIONSTATUS; 
    
    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

    public String handle(RequestXMLInfo requestXMLInfo) {
        
        RequestQueryInstructionStatus requestInfo = (RequestQueryInstructionStatus)requestXMLInfo;
        
        ResponseQueryInstructionStatus responseInfo = new ResponseQueryInstructionStatus();
        String strRenXml = "";
        Collection cReqQuery = null;
        Document doc = null;    

        Iterator it = null;
        try
        {
            responseInfo.setOperationType("" + OPERATIONTYPE);
            
            cReqQuery = requestInfo.getReqQuery();
            if(cReqQuery != null)       
            {
                it = cReqQuery.iterator();
            }
            
            OBXmlInstrDao dao = new OBXmlInstrDao();
            
            while(it != null && it.hasNext())
            {
                RequestQueryInstructionStatus.ReqQuery reqQuery = null;
                //定义实体类
                BatchImportAssemble assemble = new BatchImportAssemble();
                FinanceInfo info = new FinanceInfo();
                try
                {
                    //取下一条条件记录
                    reqQuery = (RequestQueryInstructionStatus.ReqQuery) it.next();
                    //将xml条件实体类转化为查询实体类
                    QueryCapForm qcf = reqQuery.toQueryCapForm();
                    //查询记录
                    info = dao.QueryXmlInstr(qcf);                  
                    //add info  
                    assemble.setFinanceInfo(info);
                    if(info.getID()<0)
                    {
                        assemble.setExceptionInfo("没有匹配记录");
                        assemble.getFinanceInfo().setApplyCode(qcf.getApplyCodeFrom());
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
//            throw new Exception(e.getMessage());        
        }
        return strRenXml;
    }

}
