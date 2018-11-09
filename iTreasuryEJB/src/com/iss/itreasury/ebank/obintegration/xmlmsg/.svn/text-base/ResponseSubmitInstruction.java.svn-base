/**
 * created on Mar 12, 2008
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

/**
 * @author xintan
 *
 */
public class ResponseSubmitInstruction extends ResponseXMLInfo 
{
	private Vector vRenInstr = new Vector(); //ÉêÇëÖ¸Áî·µ»Ø½á¹û¼¯¼¯
	
	/**
	 * 
	 */
	public ResponseSubmitInstruction() 
	{
		super();
	}
    
	public void addAssemble(BatchImportAssemble assemble)throws Exception
	{
		FinanceInfo info = assemble.getFinanceInfo();
		RenInstr renInstr = new RenInstr();
	
		renInstr.setApplyCode(info.getApplyCode());
        
        renInstr.setStatusNo(InstructionExecuteStatusJudgement.judge(info.getStatus()));
        renInstr.setStatusDec(InstructionExecuteStatusJudgement.getInstructionExecuteDesc(info));

        if(renInstr.getStatusNo() == InstructionExecuteStatus.FAILED)
        {
            renInstr.setStatusDec(assemble.getExceptionInfo());            
        }
	
	    this.vRenInstr.add(renInstr);
	
	}
	
	/**
	 * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
	 * <?xml version="1.0" encoding = "GBK"?>
	<Iss_Itreasury>
		<InstrRen>
			<OperationType>²Ù×÷ÀàÐÍ</OperationType>			
			<RenContent>
				<ApplyCode>ÒµÎñÉêÇë±àºÅ</ApplyCode>				
				<StatusNo>×´Ì¬±àÂë</StatusNo>		
				<StatusDec>×´Ì¬ÃèÊö</ StatusDec>	
			</RenContent >
			<RenContent>
				¡­¡­
			</RenContent >
		</InstrRen>
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
	
	    Element element = newDoc.createElement("InstrRen");
	    root.appendChild(element);
	
	    XMLHelper.createNode(newDoc, element, "OperationType", this);
	
	    /*
	    root = element;
	    element = newDoc.createElement("RenContent");
	    root.appendChild(element);
	    */
	
	    RenInstr renInstr = null;
	    if(vRenInstr!= null)
		{
			it = vRenInstr.iterator();
		}
		while(it != null && it.hasNext())
		{
			renInstr = (RenInstr) it.next();
			
	        element.appendChild(renInstr.unmarshal(newDoc));
	
	        renInstr = null;
		}
	   	
	    return newDoc;
	}

    public class RenInstr
    {
        //<RenContent>
    	//<ApplyCode>ÒµÎñÉêÇë±àºÅ</ApplyCode>	
    	private String ApplyCode = null;
    	//<StatusNo>×´Ì¬±àÂë</StatusNo>		
    	private String StatusNo = null;
    	//<StatusDec>×´Ì¬ÃèÊö</ StatusDec>	
    	private String StatusDec = null;        

        /**
         * @see com.iss.itreasury.bs.icbc.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
         */
        public Node unmarshal(Document doc) throws Exception
        {

            Element element = doc.createElement("RenContent");

            XMLHelper.createNode(doc, element, "ApplyCode", this);
            XMLHelper.createNode(doc, element, "StatusNo", this);
            XMLHelper.createNode(doc, element, "StatusDec", this);
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
    }

}
