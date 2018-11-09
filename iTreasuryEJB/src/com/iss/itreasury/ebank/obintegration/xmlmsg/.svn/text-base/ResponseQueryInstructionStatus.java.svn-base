/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Iterator;
import java.util.Vector;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BatchImportAssemble;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obintegration.constant.InstructionExecuteStatus;
import com.iss.itreasury.ebank.obintegration.judgement.InstructionExecuteStatusJudgement;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.ebank.util.OBConstant;

/**
 * @author xintan
 *
 */
public class ResponseQueryInstructionStatus extends ResponseXMLInfo 
{
	/**
	 * 
	 */
	public ResponseQueryInstructionStatus() 
	{
		super();
	}
	
	private Vector vRenQuery = new Vector(); //申请指令返回结果集集	
	
	public void addAssemble(BatchImportAssemble assemble)throws Exception
	{
		FinanceInfo info = assemble.getFinanceInfo();
		RenQuery renQuery = new RenQuery();
	
		renQuery.setApplyCode(info.getApplyCode());
		renQuery.setTransNo(info.getTransNo());
        renQuery.setStatusNo(InstructionExecuteStatusJudgement.judge(info.getStatus()));
        renQuery.setStatusDec(InstructionExecuteStatusJudgement.getInstructionExecuteDesc(info));
        
		if(InstructionExecuteStatus.FAILED.equals(renQuery.getStatusNo()))
		{
			renQuery.setStatusDec(assemble.getExceptionInfo());
		}
        
		if(info.getDealDate()!=null)
		{
			renQuery.setDealTime(info.getDealDate().toString());
		}
	
	    this.vRenQuery.add(renQuery);	
	}
	
	/**
	 * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
	 *<?xml version="1.0" encoding = "GBK"?>
	<Iss_Itreasury>
		<QueryRen>
			<OperationType>操作类型</OperationType>			
			<RenContent>
				<ApplyCode>业务申请编号</ApplyCode>		
				<TransNo>交易号</TransNo>		
				<StatusNo>状态编码</StatusNo>		
				<StatusDec>状态描述</StatusDec>	
				<DealTime>处理时间</DealTime>
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
    	//<ApplyCode>业务申请编号</ApplyCode>	
    	private String ApplyCode = null;
    	//<TransNo>交易号</TransNo>
    	private String TransNo = null;
    	//<StatusNo>状态编码</StatusNo>		
    	private String StatusNo = null;
    	//<StatusDec>状态描述</ StatusDec>	
    	private String StatusDec = null;  
    	//<DealTime>处理时间</DealTime>
    	private String DealTime = null;
		

        /**
         * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
         */
        public Node unmarshal(Document doc) throws Exception
        {

            Element element = doc.createElement("RenContent");

            XMLHelper.createNode(doc, element, "ApplyCode", this);
            XMLHelper.createNode(doc, element, "TransNo", this);
            XMLHelper.createNode(doc, element, "StatusNo", this);
            XMLHelper.createNode(doc, element, "StatusDec", this);
            XMLHelper.createNode(doc, element, "DealTime", this);
            return element;
        }
		/**
		 * @return Returns the applyCode.
		 */
		public String getApplyCode() {
			return ApplyCode;
		}
		/**
		 * @param applyCode The applyCode to set.
		 */
		public void setApplyCode(String applyCode) {
			ApplyCode = applyCode;
		}
		/**
		 * @return Returns the statusDec.
		 */
		public String getStatusDec() {
			return StatusDec;
		}
		/**
		 * @param statusDec The statusDec to set.
		 */
		public void setStatusDec(String statusDec) {
			StatusDec = statusDec;
		}
		/**
		 * @return Returns the statusNo.
		 */
		public String getStatusNo() {
			return StatusNo;
		}
		/**
		 * @param statusNo The statusNo to set.
		 */
		public void setStatusNo(String statusNo) {
			StatusNo = statusNo;
		}
		/**
		 * @return Returns the dealTime.
		 */
		public String getDealTime() {
			return DealTime;
		}
		/**
		 * @param dealTime The dealTime to set.
		 */
		public void setDealTime(String dealTime) {
			DealTime = dealTime;
		}
		/**
		 * @return Returns the transNo.
		 */
		public String getTransNo() {
			return TransNo;
		}
		/**
		 * @param transNo The transNo to set.
		 */
		public void setTransNo(String transNo) {
			TransNo = transNo;
		}
    }


}
