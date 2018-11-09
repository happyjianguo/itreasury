package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.OBFSWorkflowManager;

public class OBHistoryOpinionTag extends BaseTagSupport {

	private String strTransNo = null;//交易号
	
	private long lid = -1;
	
	private long officeID = -1;

	private long currencyID = -1;

	private long moduleID = -1;

	private long transTypeID = -1;

	private long actionID = -1;
	
	private long clientID = -1;
	
	private long islowerunit = -1;         //是否设置关联下级单位审批
	
	public long getIslowerunit() {
		return islowerunit;
	}


	public void setIslowerunit(long islowerunit) {
		this.islowerunit = islowerunit;
	}


	public int doStartTag() throws JspException 
	{
		
		
		
		try{
			
			JspWriter out = pageContext.getOut();
			showParams();
			String strOut = "";
			InutParameterInfo info = new InutParameterInfo();
			info.setCurrencyID(this.currencyID);
			info.setModuleID(this.moduleID);
			info.setTransTypeID(this.transTypeID);
			info.setOfficeID(this.officeID);
			info.setIslowerunit(this.islowerunit);
			//info.setClientID(this.clientID);
			if(OBFSWorkflowManager.isNeedApproval(info)==true)
			{
				//out.write("<TR><TD  colspan=\"6\">\r\n<hr>");
				out.write("<iframe src=\"/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=");
				out.print(this.strTransNo);
				out.write("&lID=");
				out.print(this.lid);
				out.write("&transType=");
				out.print(this.transTypeID);
				out.write("\" width=\"100%\" align=\"center\" height=\"100\" scrolling=\"Auto\" frameborder=\"0\" name=\"currentSignID\">\r\n");
				out.write("</iframe>\r\n");
			//	out.write("</TD></TR>");
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	
	private void showParams() {
		System.out.println("strTransNO:" + this.strTransNo);
		System.out.println("lID:" + this.lid);
		System.out.println("officeID:" + officeID);
		System.out.println("currencyID:" + currencyID);
		System.out.println("moduleID:" + moduleID);
		System.out.println("clientID:" + clientID);
		System.out.println("transTypeID:" + transTypeID);
		System.out.println("actionID:" + actionID);
		
		
	}

	public long getActionID() {
		return actionID;
	}


	public void setActionID(long actionID) {
		this.actionID = actionID;
	}


	public long getCurrencyID() {
		return currencyID;
	}


	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}





	public long getModuleID() {
		return moduleID;
	}


	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}


	public long getOfficeID() {
		return officeID;
	}


	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}




	public long getTransTypeID() {
		return transTypeID;
	}


	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}





	public String getStrTransNo() {
		return strTransNo;
	}


	public void setStrTransNo(String strTransNo) {
		this.strTransNo = strTransNo;
	}


	public long getLid() {
		return lid;
	}


	public void setLid(long lid) {
		this.lid = lid;
	}


	public long getClientID() {
		return clientID;
	}


	public void setClientID(long clientID) {
		this.clientID = clientID;
	}



	
}
