/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.dao.OBXmlInstrDao;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 */
public class ResponseQueryTransaction extends ResponseXMLInfo {

	public ResponseQueryTransaction() {
		super();
	}

	/**
	 * @param args
	 */
	
	private Vector vRenQuery = new Vector(); //申请指令返回结果集集	
	
	
	
	public void addResult(Collection c)throws Exception
	{
		RenQuery renQuery = null;
		Iterator it = null;
		QueryTransAccountDetailResultInfo rInfo = null;
		OBXmlInstrDao dao = new OBXmlInstrDao();
		
		if(c!=null && c.size()>0)
		{
		  it=c.iterator();
		  while(it!=null && it.hasNext())
		  {
			renQuery = new RenQuery();
			rInfo =(QueryTransAccountDetailResultInfo)it.next();
			if(rInfo.getTransTypeID()==-1000)
			{
				//该情况属于日合计跳出继续下一次循环
				continue;
			}
			if(rInfo.getTransNo()!=null)
			{	//交易编号
				renQuery.setTransNo(rInfo.getTransNo());
			}
			else 
			{
				throw new IException("交易编号为空");
			}

			//付款业务由申请编号，收款业务没有
			if(rInfo.getExecuteDate()!=null)
			{   //处理时间
				renQuery.setExcutDate(DataFormat.formatDate(rInfo.getExecuteDate()));
			}
			//本方帐户号
			renQuery.setAccountNo(rInfo.getAccountNo());
			//备注摘要
			renQuery.setNote(DataFormat.formatString(rInfo.getAbstract()));
			//业务申请编号			
			renQuery.setApplyCode(DataFormat.formatString(dao.getApplyCodeByTransNo(rInfo.getTransNo())));				
			//起昔日
			renQuery.setInterestStart(DataFormat.formatDate(rInfo.getInterestStartDate()));
			if(rInfo.getPayAmount()!=0)
			{	//交易方向 付
				renQuery.setTransDirection(String.valueOf(OBConstant.PayerOrPayee.PAYER));
				//交易金额
				renQuery.setAmount(DataFormat.formatAmount(rInfo.getPayAmount())==""?" ":DataFormat.formatAmount(rInfo.getPayAmount()));
			}else
			{	//交易方向 收
				renQuery.setTransDirection(String.valueOf(OBConstant.PayerOrPayee.PAYEE));
				//交易金额
				renQuery.setAmount(DataFormat.formatAmount(rInfo.getReceiveAmount()));			
			}
			
			this.vRenQuery.add(renQuery);	
		  }
		  
		}
		else
		{
			throw new IException("交易流水为空");
		}
		if(vRenQuery.size()==0)
		{
			throw new IException("该期间内没有任何交易");
		}
	    
	}
	
	/**
	 * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
	 *<?xml version="1.0" encoding = "GBK"?>
	<Iss_Itreasury>
		<QueryRen>
			<OperationType>操作类型</OperationType>			
			<RenContent>
				<TransNo>交易编号</TransNo>	
				<ExcutDate>日期</ ExcutDate >
				<AccountNo>本方账号</AccountNo>	
				<Note>备注（摘要）</ Note >
				<ApplyCode>业务申请编号</ApplyCode>		
				<InterestStart>起息日</ InterestStart >	
				<TransDirection>交易方向</ TransDirection>		
				<Amount>交易金额</ Amount >
			</RenContent>			
			<RenContent>
				……
			</RenContent >
		</QueryRen>
	</Iss_Itreasury>
	 */
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
    	//<TransNo>交易号</TransNo>
    	private String TransNo = null;
    	//<ExcutDate>日期</ ExcutDate >
    	private String ExcutDate =null;
    	//<AccountNo>本方账号</AccountNo>
    	private String AccountNo=null;
    	//<Note>备注（摘要）</ Note >
    	private String Note = null;
    	//<ApplyCode>业务申请编号</ApplyCode>	
    	private String ApplyCode = null;
    	//<InterestStart>起息日</ InterestStart >	
    	private String InterestStart = null;
    	//<TransDirection>交易方向</ TransDirection>
		private String TransDirection =null;
		//<Amount>交易金额</Amount >
    	private String Amount = null;
		

        /**
         * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
         */
        public Node unmarshal(Document doc) throws Exception
        {

            Element element = doc.createElement("RenContent");

            XMLHelper.createNode(doc, element, "TransNo", this);
            XMLHelper.createNode(doc, element, "ExcutDate", this);
            XMLHelper.createNode(doc, element, "AccountNo", this);
            XMLHelper.createNode(doc, element, "Note", this);
            XMLHelper.createNode(doc, element, "ApplyCode", this);
            XMLHelper.createNode(doc, element, "InterestStart", this);
            XMLHelper.createNode(doc, element, "TransDirection", this);
            XMLHelper.createNode(doc, element, "Amount", this);
            return element;
        }


		public String getAccountNo() {
			return AccountNo;
		}


		public void setAccountNo(String accountNo) {
			AccountNo = accountNo;
		}


		public String getAmount() {
			return Amount;
		}


		public void setAmount(String amount) {
			Amount = amount;
		}


		public String getApplyCode() {
			return ApplyCode;
		}


		public void setApplyCode(String applyCode) {
			ApplyCode = applyCode;
		}


		public String getExcutDate() {
			return ExcutDate;
		}


		public void setExcutDate(String excutDate) {
			ExcutDate = excutDate;
		}


		public String getInterestStart() {
			return InterestStart;
		}


		public void setInterestStart(String interestStart) {
			InterestStart = interestStart;
		}


		public String getNote() {
			return Note;
		}


		public void setNote(String note) {
			Note = note;
		}


		public String getTransDirection() {
			return TransDirection;
		}


		public void setTransDirection(String transDirection) {
			TransDirection = transDirection;
		}


		public String getTransNo() {
			return TransNo;
		}


		public void setTransNo(String transNo) {
			TransNo = transNo;
		}

    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
