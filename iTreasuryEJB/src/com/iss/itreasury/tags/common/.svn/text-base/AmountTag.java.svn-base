package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

/**
 * 金额控件标签类
 * by zhouxiang 2012-07-24
 */
public class AmountTag extends TagSupport {

	private static final long serialVersionUID = -7574478771268981841L;
	
	private String form = "";
	private String name = "";
	private double value = 0.00;
	private String chineseCtrlName = "";
	private String nextFocus = "";
	private String disabled = "";
	private String readonly = "";
	private String noValue = "";

	private long currencyID = 1;
	private String onChange = "";
	private String onClick = "";
	private String onblur = "";
	private String id = "";
	private String maxlength = "18";
	private String intDigit = "12";
	private String decimalFractionDigit = "2";
	private String onFocus="";
	
	private String isCanInputNegative = "false";
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			
			JspWriter out = pageContext.getOut();
			
			out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"amount_tab\">");
	    	out.println("<tr>");
	    	
	    	out.println("<td>");
	    	out.println("<label class='amount_symbol'>"+Constant.CurrencyType.getSymbol(currencyID)+"</lable>");
	    	out.println("</td>");
	    	out.println("<td align='right'>");
	    	out.println("<input type=\"text\" class=\"amount_input\" name=\""+name+"\" id=\""+(id.equals("")?name:id)+"\" size=\"22\" maxLength=\""+maxlength+"\"");
	    	if(noValue.equalsIgnoreCase("true")){
	    		out.println(" value=\"\"");
	    	}else{
	    		out.println(" value=\""+DataFormat.formatDisabledAmount(value)+"\"");
	    	}
	    	if(readonly.equalsIgnoreCase("true")){
	    		out.println(" readonly ");
	    	}
	    	if(disabled.equalsIgnoreCase("true"))
	    	{
	    		out.println(" disabled >");
	    	}else
	    	{
		    	out.println(" onkeypress=\"event.returnValue=IsDigit("+isCanInputNegative+");\"");
	    		out.println(" onfocus=\"adjustAmount('"+form+"','"+name+"',2,'"+chineseCtrlName+"',"+currencyID+");\"");
	    		out.println(" onblur=\"adjustAmount('"+form+"','"+name+"',1,'"+chineseCtrlName+"',"+currencyID+");"+onblur+";\"");
		    	out.println(" onchange='onAmountChangeEven(this,"+isCanInputNegative+","+intDigit+","+decimalFractionDigit+");"+onChange+"'");
		    	if(!onClick.equals("")){
		    		out.println(" onClick='"+onClick+"'");
		    	}
		    	if(!onFocus.equals("")){
		    		out.println(" onFocus='"+onFocus+"'");
		    	}
		    	out.println(">");
		    	out.println("<input type=\"hidden\" name=\""+name+"NextCtrlName\" value=\"" + nextFocus + "\">");
	    	}
	    	out.println("</td>");
	    	
	    	out.println("</tr>");
	    	out.println("</table>");
	    	
	    	
			
		}catch (Exception ex) {
	    	ex.printStackTrace() ;
	    	System.out.println(ex.getMessage());
	    }
		
		return super.doStartTag();
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getChineseCtrlName() {
		return chineseCtrlName;
	}

	public void setChineseCtrlName(String chineseCtrlName) {
		this.chineseCtrlName = chineseCtrlName;
	}

	public String getNextFocus() {
		return nextFocus;
	}

	public void setNextFocus(String nextFocus) {
		this.nextFocus = nextFocus;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getIntDigit() {
		return intDigit;
	}

	public void setIntDigit(String intDigit) {
		this.intDigit = intDigit;
	}

	public String getDecimalFractionDigit() {
		return decimalFractionDigit;
	}

	public void setDecimalFractionDigit(String decimalFractionDigit) {
		this.decimalFractionDigit = decimalFractionDigit;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getNoValue() {
		return noValue;
	}

	public void setNoValue(String noValue) {
		this.noValue = noValue;
	}

	public String getIsCanInputNegative() {
		return isCanInputNegative;
	}

	public void setIsCanInputNegative(String isCanInputNegative) {
		this.isCanInputNegative = isCanInputNegative;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnFocus() {
		return onFocus;
	}

	public void setOnFocus(String onFocus) {
		this.onFocus = onFocus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
