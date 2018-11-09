package com.iss.itreasury.tags.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.iss.itreasury.ebank.util.PagerTypeConstant;

public class PageElements extends SimpleTagSupport{

	
	public void doTag() throws JspException, IOException {
		this.getJspBody().invoke(null);
		Dictionary dic = (Dictionary)findAncestorWithClass(this, Dictionary.class);
		if(dic==null)
		{
			throw new JspTagException("ªÒ»°∏∏±Í«© ß∞‹!");
		}		

	}	
}
