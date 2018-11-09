package com.iss.itreasury.tags.common;

import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.OBFSWorkflowManager;

public class OBUploadFileTag extends BaseTagSupport {

	private String caption = "链接附件";// 附件按钮名称

	// private long type = -1; // 文件类型

	// private long parentType = -1; // 所属指令类型

	// private long parentID = -1; // 所属指令ID

	private long lid; // 标示

	private long actionID = -1; // 操作

	private long moduleID = -1;// 模块id

	private long transTypeID = -1;// 交易类型ID

	private long transSubTypeID = -1;// 交易子类型id，如果是结算业务，交易类型=交易子类型

	private long currencyID = -1; //币种ID

	private long officeID = -1;   //办事处ID
	
	private long clientID = -1;   //客户ID

	private String transCode = ""; // 交易号

	private String showOnly = "false"; // 是否只读

	private String strContext = "";// 服务器上下文
	
	private long islowerunit = -1;         //是否设置关联下级单位审批
	
	public int doStartTag() throws JspException {
		ShowHtml();
		return EVAL_PAGE;
	}

	private void ShowHtml() {
		JspWriter out = pageContext.getOut();
		try {

			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(this.moduleID);
			pInfo.setOfficeID(this.officeID);
			pInfo.setCurrencyID(this.currencyID);
			pInfo.setTransTypeID(this.transTypeID);
			pInfo.setClientID(this.clientID);
			pInfo.setIslowerunit(this.islowerunit);
			//pInfo.setActionID(this.actionID);

			if (OBFSWorkflowManager.isNeedApproval(pInfo) == true) {
				out.write("<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\r\n");
				out.write("<tr>\r\n");
				out.write("<td width=\"1\"><a class=lab_title1></td>\r\n");
				out.write("<td width=\"100\"class=\"lab_title2\">链接附件</td>\r\n");
				out.write("<td width=\"650\"><a class=lab_title3></td>\r\n");
				out.write("</tr>");
				out.write("</table>");
				out.write("<table width=\"80%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"normal\">\r\n");
				out.write("<tr> \r\n");
				out.write("<td>\r\n");
				
				out.write("<script language=\"javascript\">\r\n");
				// out.write("</script>\r\n");
				out.write("</script>\r\n");
				//out.write("链接附件");
	
				out.write("\t          <input type = \"hidden\" name = \"strTransTypeNo\" value = '");
				out.print(transCode);
				out.write("' >\r\n");
				out.write("\t          <iframe src=\"");
				out.print(strContext);
				out.write("/AttachFrame.jsp?lID=");
				out.print(lid);
				out.write("&lTypeID=2&sCaption=");
				out.print(URLEncoder.encode(caption));
				out.write("&control=modify&showOnly=" + showOnly + "&ModuleID=");
				out.print(moduleID);
				out.write("&TransTypeID=");
				out.print(transTypeID);
				out.write("&transCode=");
				out.print(transCode);
				out.write("&TransSubTypeID=");
				out.print(transSubTypeID);
				out.write("&CurrencyID=");
				out.print(currencyID);
				out.print("&ClientID=");
				out.print(clientID);
				out.write("&OfficeID=");
				out.print(officeID);
				out.write("\" width=100% scrolling=\"Auto\" height=\"80\" frameborder=\"0\" name=\"currentSignID\">\r\n");
				out.write("\t          </iframe>\r\n");
				
				out.write("</td> \r\n");
				out.write("</tr> \r\n");
				out.write("</table> \r\n");
				
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
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

	public long getLid() {
		return lid;
	}

	public void setLid(long lid) {
		this.lid = lid;
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

	public String getShowOnly() {
		return showOnly;
	}

	public void setShowOnly(String showOnly) {
		this.showOnly = showOnly;
	}

	public String getStrContext() {
		return strContext;
	}

	public void setStrContext(String strContext) {
		this.strContext = strContext;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public long getTransSubTypeID() {
		return transSubTypeID;
	}

	public void setTransSubTypeID(long transSubTypeID) {
		this.transSubTypeID = transSubTypeID;
	}

	public long getTransTypeID() {
		return transTypeID;
	}

	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public long getIslowerunit() {
		return islowerunit;
	}

	public void setIslowerunit(long islowerunit) {
		this.islowerunit = islowerunit;
	}


}
