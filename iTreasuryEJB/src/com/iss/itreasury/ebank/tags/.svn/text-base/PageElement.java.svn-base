package com.iss.itreasury.ebank.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.iss.itreasury.ebank.util.PagerTypeConstant;

public class PageElement extends SimpleTagSupport{
	private String elName = "";
	private String name = "";
	private long type;
	private String elType = "";;
	private String value = "";
	public void setElName(String elName) {
		this.elName = elName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(long type) {
		this.type = type;
	}
	public void setElType(String elType) {
		this.elType = elType;
	}
	public void setValue(String value) {
		this.value = value;
	}		
	public void doTag() throws JspException, IOException {
		
		StringBuffer strElement = new StringBuffer();
		strElement.append(" { ");
		strElement.append(" elName:\""+elName+"\",");
		strElement.append(" name:\""+name+"\", ");
		strElement.append(" type:"+PagerTypeConstant.getType(type)+",");
		if(!value.equals("")&&elType.equals("hidden"))
		{
			strElement.append(" value:\""+value+"\", ");
		}
		strElement.append(" elType:\""+elType+"\" ");
		strElement.append(" } ");
		System.out.println(strElement);
		PageElements elements = (PageElements)findAncestorWithClass(this, PageElements.class);
		if(elements==null)
		{
			throw new JspTagException("获取父标签失败!");
		}
		Dictionary dic = (Dictionary)findAncestorWithClass(this, Dictionary.class);
		if(dic==null)
		{
			throw new JspTagException("获取父标签失败!");
		}		
		dic.AddElementList(strElement.toString());
	}

}
