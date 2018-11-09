/*
 * Created on 2005-1-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.loan.util;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.print.bizlogic.IPrint;
import com.iss.itreasury.print.dataentity.PrintSettingInfo;
import com.iss.itreasury.util.SessionMng;


/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class loanPrint extends IPrint
{
    /**
	 * 查询报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距是贷款查询部分默认的）
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @throws Exception
	 */
	public static void showQueryPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
	    PrintSettingInfo info=new PrintSettingInfo(); 
	    info.setHeader("");
	    info.setFooter("&b第&p页/共&P页&b");
	    info.setLeftMargin(0);
	    info.setRightMargin(0);
	    info.setTopMargin(10);
	    info.setBottomMargin(0);
	    showPrintReport(out,sessionMng,strPaperSize,lPortrait,info);
	}

}


