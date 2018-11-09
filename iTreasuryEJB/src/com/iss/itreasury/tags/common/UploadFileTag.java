package com.iss.itreasury.tags.common;

import java.net.URLEncoder;
import java.sql.ResultSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.FSWorkflowManager;
/**
 * ʹ�ô˱�ǩʱҪ��ʹ��ҳ�涨��һ����ʱ�Ľ��׺�,
 * �ڱ����Ҫ�����ɵĽ��׺��滻�����ݿ����
 * @author wjliu
 *
 */
public class UploadFileTag extends BaseTagSupport {

	private String caption = "���Ӹ���";// ������ť����

	// private long type = -1; // �ļ�����

	// private long parentType = -1; // ����ָ������

	// private long parentID = -1; // ����ָ��ID

	private long lid; // ��ʾ

	private long actionID = -1; // ����

	private long moduleID = -1;// ģ��id

	private long transTypeID = -1;// ��������ID

	private long transSubTypeID = -1;// ����������id������ǽ���ҵ�񣬽�������=����������

	private long currencyID = -1;

	private long officeID = -1;

	private String transCode = ""; // ���׺�

	private String showOnly = "false"; // �Ƿ�ֻ��

	private String strContext = "";// ������������

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
			pInfo.setActionID(this.actionID);

			if (FSWorkflowManager.isNeedApproval(pInfo) == true) {
				
                
				out.write("\t<TABLE width=\"100%\"><TR>\r\n");
				out.write("\t<TD borderColor=#E8E8E8><hr>\r\n");
				out.write("\t<U>��������</U> \r\n");

				out.write("\t<input type = \"hidden\" name = \"strTransTypeNo\" value = '");
				out.print(transCode);
				out.write("'></TD>\r\n");
				out.write("\t<TR><TD><iframe src=\"");
				out.print(strContext);
				out.write("/AttachFrame.jsp?lID=");
				out.print(lid);
				out.write("&lTypeID=2&sCaption=");
				out.print(URLEncoder.encode(caption));
				out.write("&control=modify&showOnly=" + showOnly+ "&ModuleID=");
				out.print(moduleID);
				out.write("&TransTypeID=");
				out.print(transTypeID);
				out.write("&transCode=");
				out.print(transCode);
				out.write("&TransSubTypeID=");
				out.print(transSubTypeID);
				out.write("&CurrencyID=");
				out.print(currencyID);
				out.write("&OfficeID=");
				out.print(officeID);
				out.write("\" width=99% height=\"100\" scrolling=\"Auto\" frameborder=\"0\" name=\"currentSignID\">\r\n");
				out.write("\t</iframe>\r\n");
				out.write("\t</TD></TR>\r\n");
				out.write("</TABLE>");
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

	public long getActionID() {
		return actionID;
	}

	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

}
