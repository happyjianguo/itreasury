package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
import javax.servlet.jsp.JspWriter;
import java.io.*;

public class ApprovalInitButton extends BaseTagSupport {
	private String controlName = "ApprovalInitButton"; // 按钮名称

	private String classType = ""; // 按钮样式

	private String onClickMethod = ""; // 按钮的onclick方法

	private String onfocusMethod = ""; // 按钮的onfocus方法

	private String value = "提交审批"; // 按钮的value(显示文字)

	private boolean disabled = false; // 是否disabled

	private String extProperty = ""; // 其他扩展属性

	private long officeID = -1;

	private long currencyID = -1;

	private long moduleID = -1;

	private long transTypeID = -1;

	private long actionID = -1;

	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();

			showParams();
			String strOutPrint = "";

			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(this.moduleID);
			pInfo.setOfficeID(this.officeID);
			pInfo.setCurrencyID(this.currencyID);
			pInfo.setTransTypeID(this.transTypeID);
			pInfo.setActionID(this.actionID);

			if (FSWorkflowManager.isNeedApproval(pInfo) == true) {
				strOutPrint = "<INPUT class=\"" + classType + "\" name=\""
						+ controlName + "\" type=\"button\" value=\"" + value
						+ "\" " + extProperty;
				if (onClickMethod != null && onClickMethod.length() > 0) {
					strOutPrint += " onClick=\"" + onClickMethod + "\"";
				}
				if (onfocusMethod != null && onfocusMethod.length() > 0) {
					strOutPrint += " onfocus=\"" + onfocusMethod + "\"";
				}
				if (disabled) {
					
					strOutPrint += " disabled";
				}
				strOutPrint += " >";

				out.println(strOutPrint);
			}

		} catch (IOException ex) {
		}

		catch (Exception ex) {
		}

		return EVAL_PAGE;
	}

	private void showParams() {
		System.out.println("controlName:" + controlName);
		System.out.println("classType:" + classType);
		System.out.println("onClickMethod:" + onClickMethod);
		System.out.println("onfocusMethod:" + onfocusMethod);
		System.out.println("value:" + value);
		System.out.println("isDisabled:" + disabled);
		System.out.println("extProperty:" + extProperty);
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

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public String getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(String extProperty) {
		this.extProperty = extProperty;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
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

	public String getOnClickMethod() {
		return onClickMethod;
	}

	public void setOnClickMethod(String onClickMethod) {
		this.onClickMethod = onClickMethod;
	}

	public String getOnfocusMethod() {
		return onfocusMethod;
	}

	public void setOnfocusMethod(String onfocusMethod) {
		this.onfocusMethod = onfocusMethod;
	}

	public long getTransTypeID() {
		return transTypeID;
	}

	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
