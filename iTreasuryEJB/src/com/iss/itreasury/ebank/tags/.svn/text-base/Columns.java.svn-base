package com.iss.itreasury.ebank.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.iss.itreasury.ebank.util.PagerTypeConstant;

public class Columns extends SimpleTagSupport{
	public void doTag() throws JspException, IOException {
		this.getJspBody().invoke(null);
		Dictionary dic = (Dictionary)findAncestorWithClass(this, Dictionary.class);
		if(dic==null)
		{
			throw new JspTagException("ªÒ»°∏∏±Í«© ß∞‹!");
		}		
	}	
	

}
