package com.iss.itreasury.tags.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 日历控件标签类
 * by zhouxiang 2012-05-08
 */
public class CalendarTag extends TagSupport {
	
	private static final long serialVersionUID = 2417134258022304506L;
	
	private String name = null;
	private String value = "";
	private String properties = null;
	private String nextFocus = null;
	private String size = null;
	
	private String disabled = "";
	private String readonly = "";
	private String onclick = "";
	private String onblur = "";
	private String onchange = "";
	private String onfocus = "";
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			
			JspWriter out = pageContext.getOut();
		
			out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"calendar_tab\">");
	    	out.println("<tr>");
	    	out.println("<td>");
	    	out.println("<input type=\"text\" name=\""+name+"\"  id=\""+name+"\" class=\"calendar_input\"");
	    	if(value != null){
	    		out.println(" value=\""+value+"\"");
	    	}
	    	if(nextFocus != null){
	    		out.println(" onfocus=\"nextfield='"+nextFocus+"'\"");
	    	}
	    	if(onblur != null){
	    		out.println(" onblur=\""+onblur+"\"");
	    	}
	    	if(onchange != null){
	    		out.println(" onchange=\""+onchange+"\"");
	    	}
	    	if(onfocus != null){
	    		out.println(" onfocus=\""+onfocus+"\"");
	    	}
	    	if(properties != null){
	    		out.println(" properties");
	    	}
	    	if(disabled.equalsIgnoreCase("true")){
	    		out.println(" disabled ");
	    	}
	    	if(readonly.equalsIgnoreCase("true")){
	    		out.println(" readonly ");
	    	}
	    	if(size != null){
	    		out.println(" size=\""+size+"\">");
	    	}else{
	    		out.println(" >");
	    	}
	    	out.println("</td>");
	    	out.println("<td>");
	    	out.println("<a class=\"calendar_img\" onclick=\"showCalendar(this,document.getElementById('"+name+"'));"+onclick+";\"/>");
	    	out.println("</td>");
	    	out.println("</tr>");
	    	out.println("</table>");
		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
	    	ex.printStackTrace() ;
	    	System.out.println(ex.getMessage());
	    }
		
		return super.doStartTag();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getNextFocus() {
		return nextFocus;
	}
	public void setNextFocus(String nextFocus) {
		this.nextFocus = nextFocus;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	
}
