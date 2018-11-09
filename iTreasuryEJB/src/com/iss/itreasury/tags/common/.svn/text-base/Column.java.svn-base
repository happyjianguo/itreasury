package com.iss.itreasury.tags.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.iss.itreasury.ebank.util.PagerTypeConstant;

public class Column extends SimpleTagSupport{
	private String display;
	private String name;
	private long type;
	private long width;
	private boolean sort;
	private String align;
	private String clazz;
	private String method;
	private String paramType;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(long type) {
		this.type = type;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public void setSort(boolean sort) {
		this.sort = sort;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	
	public void doTag() throws JspException, IOException {
		StringBuffer strColumn = new StringBuffer();
		strColumn.append(" { ");
		strColumn.append(" display:\""+display+"\", ");
		strColumn.append(" name:\""+name+"\", ");
		strColumn.append(" type:"+PagerTypeConstant.getType(type));
		if(width>0)
		{
			strColumn.append(", width:"+width);
		}

		if(sort)
		{
			strColumn.append(", sortable:"+sort);
		}
		if(align!=null&&!align.equals(""))
		{
			strColumn.append(", align:\""+align+"\"");
		}
		if(PagerTypeConstant.FUNCTION==type){
			strColumn.append(", clazz:\""+clazz+"\"");
			strColumn.append(", method:\""+method+"\"\n");
			strColumn.append(", paramType:\""+paramType+"\"\n");
		}
		strColumn.append(" }");
		//System.out.println(strColumn);
		
		Columns columns = (Columns)findAncestorWithClass(this,Columns.class);
		if(columns==null)
		{
			throw new JspTagException("获取父标签失败!");
		}
		Dictionary dic = (Dictionary)findAncestorWithClass(this, Dictionary.class);
		if(dic==null)
		{
			throw new JspTagException("获取父标签失败!");
		}
		dic.AddColumnList(strColumn.toString());
	}	
	

}
