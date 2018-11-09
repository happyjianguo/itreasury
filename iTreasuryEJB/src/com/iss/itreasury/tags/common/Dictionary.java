package com.iss.itreasury.tags.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Dictionary extends SimpleTagSupport{

	private String id="";
	private String form="";
	private String title="";
	private String sqlFunction="";
	private String sqlParams="";
	private String nextFocus="";
	private long width;
	private long size;
	ArrayList columnList = null;
	ArrayList elementList = null;
	private String value="";
	private String type="";
	private long row;
	private long col;
	private boolean needRemind;
	private String properties = "";
	private long maxlength; 
	private String position = "";
	
	
	

	public void setPosition(String position) {
		this.position = position;
	}
	public void setNeedRemind(boolean needRemind) {
		this.needRemind = needRemind;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public void setMaxlength(long maxlength) {
		this.maxlength = maxlength;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setRow(long row) {
		this.row = row;
	}
	public void setCol(long col) {
		this.col = col;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSqlFunction(String sqlFunction) {
		this.sqlFunction = sqlFunction;
	}
	public void setSqlParams(String sqlParams) {
		this.sqlParams = sqlParams;
	}
	public void setNextFocus(String nextFocus) {
		this.nextFocus = nextFocus;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public void doTag() throws JspException, IOException {
		try
		{
			this.getJspBody().invoke(null);
			String sql = sqlFunction + "("+sqlParams+")";
			StringBuffer html = new StringBuffer();
			if(size<=0)
			{
				size = 22;
			}
			//type = (type==null?"":type);
			if(type.equals("textarea"))
			{
				String remindText = "";
				if(needRemind)
				{
					long length = this.getLength(value);
					properties +=" onpropertychange='checkStr("+maxlength+",\""+id+"\")' onblur='checkStrMessage("+maxlength+",\""+id+"\")' ";
					remindText = " <span id='"+id+"textAreaShow'>最多<b>"+maxlength+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+String.valueOf(maxlength-length)+"</b>个字符</span> ";
				}
				html.append(" <textarea id=\""+id+"\" name=\""+id+"\" class=\"searchtextarea\" rows=\""+row+"\" cols=\""+col+"\" "+properties+" >"+(value==null?"":value)+"</textarea> ");
				html.append(remindText);
			}else
			{
				html.append(" <input type=\"text\" id=\""+id+"\" name=\""+id+"\" class=\"searchinput\" size=\""+size+"\" value=\""+(value==null?"":value)+"\" "+properties+" /> \n");
			}
			html.append(" <script type=\"text/javascript\"> \n");
			html.append(" $(function(){ \n ");
			html.append(" 	$(\"#"+id+"\").suggest( \n ");
			html.append("  	  {  \n ");
			html.append("  		id:\""+id+"\",	\n ");
			html.append("  		form:\""+form+"\",	\n ");
			html.append("       title:\""+title+"\",   \n ");
			if(!position.equals(""))
			{
				html.append("       offset:\""+position+"\",   \n ");
			}
			if(width>0)
			{
				if(width<550)
					width=550;
				html.append("   	width:"+width+",	\n ");
			}
			html.append("  		sql:\""+sql+"\", \n ");
			html.append("  		nextFocus:\""+nextFocus+"\", \n");
			html.append("  		type:\""+type+"\"");
			String strColumnList = getListString(columnList);
			if(!strColumnList.equals(""))
			{
				html.append(", \n");
				html.append(" 		colModel:"+strColumnList);
			}
			String strElementList = getListString(elementList);
			if(!strElementList.equals(""))
			{
				html.append(", \n");
				html.append("		elements:"+strElementList);
			}
			html.append("  	});\n ");
			html.append(" }); \n ");
			html.append(" </script> \n");
			this.getJspContext().getOut().println(html.toString());
		}catch(JspException e)
		{
			e.printStackTrace();
			throw new JspTagException("获取放大镜控件出错!",e);
		}
	}	
	
	public void AddColumnList(String content)
	{
		if(columnList==null)
		{
			columnList = new ArrayList();
		}
		columnList.add(content);
	}
	
	public void AddElementList(String content)
	{
		if(elementList==null)
		{
			elementList = new ArrayList();
		}
		elementList.add(content);
	}
	
	public String getListString(ArrayList list)
	{
		String result = "";
		if(list!=null&&list.size()>0)
		{
			result = list.toString();
		}
		return result;
	}
	
	public long getLength(String value)
	{
		long length = 0;
		value = value.replaceAll("[^\\x00-\\xff]", "**");
		length = value.length();
		return length;
	}

}
