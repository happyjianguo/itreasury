package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.FSWorkflowManager;

public class HistoryOpinionTag extends BaseTagSupport {

	private String strTransNo = null;//½»Ò×ºÅ
	
	private long lid = -1;
	
	private long officeID = -1;

	private long currencyID = -1;

	private long moduleID = -1;

	private long transTypeID = -1;

	private long actionID = -1;
	
	
	public int doStartTag() throws JspException 
	{
		
		
		
		try{
			
			JspWriter out = pageContext.getOut();
			showParams();
			String strOut = "";
			InutParameterInfo info = new InutParameterInfo();
			info.setActionID(this.actionID);
			info.setCurrencyID(this.currencyID);
			info.setModuleID(this.moduleID);
			info.setTransTypeID(this.transTypeID);
			info.setOfficeID(this.officeID);
			
			if(FSWorkflowManager.isNeedApproval(info)==true)
			{
				out.write("<TR><TD  colspan=\"6\">\r\n<hr>");
				out.write("<iframe src=\"/NASApp/iTreasury-settlement/HistoryOpinionFrame.jsp?transNo=");
				out.print(this.strTransNo);
				out.write("&lID=");
				out.print(this.lid);
				out.write("&transTypeID=");
				out.print(this.transTypeID);
				out.write("\" width=\"100%\"  scrolling=\"Auto\" frameborder=\"0\" name=\"currentSignID\">\r\n");
				out.write("</iframe>\r\n");
				out.write("</TD></TR>");
				
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



	
}
