/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.util;

import org.w3c.dom.Document;

import com.iss.itreasury.ebank.obintegration.xmlmsg.RequestXMLInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author xintan
 * 
 *  财企接口客户请求解析器
 */
public class RequestAssember {
    
    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

    public RequestAssember() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 将xml格式的请求字符串解析成RequestXMLInfo对象
     * 
     * @param strRequestData xml格式的字符串
     * @return
     * @throws IException
     */
    public RequestXMLInfo convertToXMLInfo(String strRequestData) throws IException
    {
        Document xmlDoc = null;         
        RequestXMLInfo requestXMLInfo =new RequestXMLInfo();
        try 
        {
            xmlDoc = XMLHelper.parse(strRequestData);            
            if(xmlDoc!=null)
            {
                requestXMLInfo.marshal(xmlDoc);
            }               
        }
        catch (Exception e) 
        {           
            log.error("网银财企接口解析xml报文时发生异常：" + e.toString());
            throw new IException(e.getMessage());
        }
        return requestXMLInfo;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
