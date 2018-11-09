/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.util.DataFormat;

public class ResponseQueryAccountDailyBalance extends ResponseXMLInfo{

	public ResponseQueryAccountDailyBalance() {
		super();
	}

	/**
	 * @param args
	 */
	
	private Vector vRenQuery = new Vector(); //申请指令返回结果集集	
		
	/**
	 * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
	 *<?xml version="1.0" encoding = "GBK"?>
		<Iss_Itreasury>
		<QueryRen>
			<OperationType>操作类型</OperationType>			
			<RenContent>
				<AccountNo>账号</AccountNo >	
				<Date>日期</Date >
				<Balance>余额</Balance>
			</RenContent>			
			<RenContent>
				……
            </RenContent >
		</QueryRen>
	</Iss_Itreasury>

	 */
	/**
	 * 将结果集转化到内部类
	 */
	public void addQueryResult(Collection res) throws Exception
	{
		RenQuery renQuery = null;
		Iterator it = null;
		QueryAccountBalanceResultInfo rInfo = null;
		try
		{
			if(res!=null && res.size()>0)
			{
				it = res.iterator(); 
				while(it.hasNext())
				{   
					renQuery = new RenQuery();
					rInfo = (QueryAccountBalanceResultInfo)it.next() ;
					if(rInfo.getAccountNo() !=null)
					{
						renQuery.setAccountNo(rInfo.getAccountNo());
					}
	
				    renQuery.setBalance(DataFormat.formatAmount(rInfo.getBalance()));
				    if(rInfo.getBalanceDate() !=null)
				    {
				    	renQuery.setDate(DataFormat.getDateString(rInfo.getBalanceDate()));
				    }  
				   this.vRenQuery.add(renQuery); 
						   
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("--Exception addQueryResult ");
			throw new Exception("Gen_001");
		}
	}
	
	public Node unmarshal(Document doc) throws Exception
	{
	    doc = null;
	    Iterator it = null;
	
	    Document newDoc = null;
	    newDoc = new DocumentImpl();
	
	    Element root = newDoc.createElement("Iss_Itreasury");
	    newDoc.appendChild(root);
	
	    Element element = newDoc.createElement("QueryRen");
	    root.appendChild(element);
	
	    XMLHelper.createNode(newDoc, element, "OperationType", this);
	
	   /* root = element;
	    element = newDoc.createElement("RenContent");
	    root.appendChild(element);*/
	
	    RenQuery renQuery = null;
	    if(vRenQuery!= null)
		{
			it = vRenQuery.iterator();
		}
		while(it != null && it.hasNext())
		{
			renQuery = (RenQuery) it.next();
			
	        element.appendChild(renQuery.unmarshal(newDoc));
	
	        renQuery = null;
		}
	   	
	    return newDoc;
	}

    public class RenQuery
    {
        //<RenContent>
    	//<AccountNo>账号</AccountNo >
		private String AccountNo = null;
		//<Date>日期</Date >
		private String Date =null;
		//<Balance>余额</Balance>
		private String Balance = null;
        
		public String getAccountNo() {
			return AccountNo;
		}
		public void setAccountNo(String accountNo) {
			AccountNo = accountNo;
		}
		public String getBalance() {
			return Balance;
		}
		public void setBalance(String balance) {
			Balance = balance;
		}
		public String getDate() {
			return Date;
		}
		public void setDate(String date) {
			Date = date;
		}
		/**
         * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
         */
        public Node unmarshal(Document doc) throws Exception
        {

            Element element = doc.createElement("RenContent");

            XMLHelper.createNode(doc, element, "AccountNo", this);
            XMLHelper.createNode(doc, element, "Date", this);
            XMLHelper.createNode(doc, element, "Balance", this);

            return element;
        }



    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
