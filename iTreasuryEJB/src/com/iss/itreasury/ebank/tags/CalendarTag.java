package com.iss.itreasury.ebank.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/*
 * 日历控件标签类
 * by zhouxiang 2012-05-08
 */
public class CalendarTag extends TagSupport {
	
	private static final long serialVersionUID = 2417134258022304501L;
	
	private String name = null;
	private String value = "";
	private String properties = null;
	private String size = null;
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			
			JspWriter out = pageContext.getOut();
		
			out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"calendar_tab\">");
	    	out.println("<tr class='control'>");
	    	out.println("<td class='control'>");
	    	out.println("<input type=\"text\" name=\""+name+"\"  id=\""+name+"\" class=\"calendar_input\" value=\""+value+"\"");
	    	if(properties == null){
	  //  		out.println(" onfocus=\"showCalendar(this)\"");
	    	}else{
	    		out.println(" onfocus=\""+properties+"\"");
	    	//	out.println(" onfocus=\"showCalendar(this);"+properties+"\"");
	    	}
	    	if(size == null){
	    		out.println(" >");
	    	}else{
	    		out.println(" size=\""+size+"\">");
	    	}
	    	out.println("</td>");
	    	out.println("<td class='control'>");
	    	out.println("<a class=\"calendar_img\" onclick=\"showCalendar(this,document.getElementById('"+name+"'));\"/>");
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
	
}
