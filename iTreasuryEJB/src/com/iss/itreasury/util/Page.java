/*
 * Created on 2003-4-29
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.iss.itreasury.dataentity.PageInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Page
{
	public static HashMap hmQueryCondition = new HashMap();

	public static Object getQueryInfo(HttpServletRequest req, int index, Object Info)
	{
		Object resultInfo = null;
		String sInitKey = "turnpagecontrol";
		String sKey = sInitKey + index;

		boolean bIsInit = false;
		long lPageNo = 1; //页码
		long lPageSize = Constant.PageControl.CODE_PAGELINECOUNT; //页面记录数
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;//升序或降序
		String sOrderBy = ""; //排序字段名称

		String sTemp = "";

		sTemp = req.getParameter("txtPageNo");
		if (sTemp != null && !sTemp.equals(""))
		{
			lPageNo = Long.parseLong(sTemp);
		}
		else
		{
			bIsInit = true;
		}

		sTemp = req.getParameter("lPageSize");
		if (sTemp != null && !sTemp.equals(""))
		{
			lPageSize = Long.parseLong(sTemp);
		}

		sTemp = req.getParameter("lOrderParam");
		if (sTemp != null && !sTemp.equals(""))
		{
			sOrderBy = sTemp;
		}

		sTemp = req.getParameter("lDesc");
		if (sTemp != null && !sTemp.equals(""))
		{
			lDesc = Long.parseLong(sTemp);
		}

		PageInfo pInfo = new PageInfo();
		pInfo.setPageNo(lPageNo);
		pInfo.setPageSize(lPageSize);
		pInfo.setOrderBy(sOrderBy);
		pInfo.setChange(bIsInit);
		pInfo.setDesc(lDesc);
		
		long inPageCount = 0;
		long inResultSize = 1;
		
		Object oTemp = PageStatement.hmCount.get(PageStatement.sKey);
		if (oTemp != null && !oTemp.equals(""))
		{
			inResultSize = Long.parseLong((String) oTemp);
		}		
		
		//计算总页数
		if (inResultSize > lPageSize)
		{
			inPageCount = inResultSize / lPageSize;
			if ((inResultSize % lPageSize) != 0)
			{
				inPageCount++;
			}
		}
		else if (inResultSize > 0 && inResultSize <= lPageSize)
		{
			inPageCount = 1;
		}
		else
		{
			inPageCount = 0;
		}
		
		req.setAttribute("txtPageNo",String.valueOf(lPageNo));
		req.setAttribute("lPageSize",String.valueOf(lPageSize));
		req.setAttribute("lOrderParam",String.valueOf(sOrderBy));
		req.setAttribute("lDesc",String.valueOf(lDesc));
		req.setAttribute("inPageCount",String.valueOf(inPageCount));
		
		
		PageStatement.sKey = sKey;
		PageStatement.hmPageInfo.put(sKey, pInfo);
		if (bIsInit)
		{
			resultInfo = Info;
			hmQueryCondition.put(sKey, Info);
		}
		else
		{
			resultInfo = hmQueryCondition.get(sKey);
		}

		return resultInfo;
	}

	//	/**
	//	* 返回key值
	//	* @return String 返回的key
	//	*/
	//	public String getKey()
	//	{
	//		String key = null;
	//
	//		Random random1 = new Random();
	//		Random random2 = new Random(random1.nextLong());
	//
	//		key = String.valueOf(random2.nextLong());
	//
	//		if (Page.hmQueryCondition.containsKey(key))
	//		{
	//			key = null;hmQueryCondition.put("","");
	//		}
	//		
	//		return key;
	//	}

	/**
		* 输出翻页控件
		* add by hyzeng 
		* @param out 
		* @param sFormName 翻页控件使用的表单名称
		* @param sFormAction 翻页控件使用的表单提交到的页面
		* @param inResultSize 记录总数
		* @param inInitPageNo 页号
		* @param inPageSize 每页的记录数
		* @param lOrderParam 排序的字段
		* @param lDesc 升序或降序
		*/
	/*public static void showTurnPageControl(JspWriter out, String sFormName, String sFormAction, long inPageSize) throws Exception
	{
		long inPageCount = 0;
		long inResultSize = 1;
		
		Object oTemp = PageStatement.hmCount.get(PageStatement.sKey);
		if (oTemp != null && !oTemp.equals(""))
		{
			inResultSize = Long.parseLong((String) oTemp);
		}

		PageInfo pInfo = new PageInfo();
		
		oTemp = PageStatement.hmPageInfo.get(PageStatement.sKey);
		if (oTemp != null && !oTemp.equals(""))
		{
			pInfo = (PageInfo) oTemp;
		}
		
		//计算总页数
		if (inResultSize > inPageSize)
		{
			inPageCount = inResultSize / inPageSize;
			if ((inResultSize % inPageSize) != 0)
			{
				inPageCount++;
			}
		}
		else if (inResultSize > 0 && inResultSize <= inPageSize)
		{
			inPageCount = 1;
		}
		else
		{
			inPageCount = 0;
		}
		
		StringBuffer sbPage = new StringBuffer();
		sbPage.append("<form name=" + sFormName + " action=" + sFormAction + " method=post> \n");
		sbPage.append("<TABLE width=100% border=0 cellspacing=3 cellpadding=0 class=SearchBar height=15> \n");
		sbPage.append("<TR> \n");
		sbPage.append("<TD width=952 height=33> \n");
		sbPage.append("<DIV align=right> \n");
		sbPage.append("<p> \n");
		sbPage.append("<b> 第 \n");
		sbPage.append("<input type=text name=txtPageNo maxlength=3 size=3 class=SearchBar_Page value=" + pInfo.getPageNo());
		sbPage.append(" onkeydown='Javascript:k = window.event.keyCode;if (k == 13){return turnPage(1);}'> \n");
		sbPage.append("页 / 共 " + inPageCount + " 页 \n");
		sbPage.append("<input type=submit name=SUBMIT1 value=go class=SearchBar_Btn onclick='Javascript:return turnPage(1);'> \n");
		sbPage.append("<input type=submit name=SUBMIT2 value='|&lt;' class=SearchBar_Btn onclick='Javascript:return turnPage(2);'> \n");
		sbPage.append("<input type=submit name=SUBMIT3 value='&lt;' class=SearchBar_Btn onclick='Javascript:return turnPage(3);'> \n");
		sbPage.append("<input type=submit name=SUBMIT4 value='&gt;' class=SearchBar_Btn onclick='Javascript:return turnPage(4);'> \n");
		sbPage.append("<input type=submit name=SUBMIT5 value='&gt;|' class=SearchBar_Btn onclick='Javascript:return turnPage(5);'> \n");
		sbPage.append("<input type=hidden name=lPageCount value=" + inPageCount + "> \n");
		sbPage.append("<input type=hidden name=lOrderParam value=" + pInfo.getOrderBy() + "> \n");
		sbPage.append("<input type=hidden name=lDesc value=" + pInfo.getDesc() + "> \n");
		sbPage.append("</b> \n");
		sbPage.append("</p> \n");
		sbPage.append("</DIV> \n");
		sbPage.append("</TD> \n");
		sbPage.append("</TR> \n");
		sbPage.append("</TABLE> \n");
		sbPage.append("</form> \n");
		sbPage.append("<script language=javascript>\n");
		sbPage.append("function turnPage(clicked){ \n");
		sbPage.append("var iPage;var sSubmit;var iAllPage; \n");
		sbPage.append("iAllPage = parseInt(" + sFormName + ".lPageCount.value); \n");
		sbPage.append("if (!InputValid(" + sFormName + ".txtPageNo, 1, 'int', 0, 1, 10, '页数'))return false; \n");
		sbPage.append("iPage = parseInt(" + sFormName + ".txtPageNo.value); \n");
		sbPage.append("if (iAllPage < 1){ return false;} \n");
		sbPage.append("if ((iPage < 1) || (iPage > iAllPage)){return false;} \n");
		sbPage.append("switch (clicked){ \n");
		sbPage.append("case 1 : \n");
		sbPage.append("if ((iPage == '') || (iPage < 0) || (iPage > iAllPage)){return false;} \n");
		sbPage.append("else{" + sFormName + ".txtPageNo.value = iPage;}break; \n");
		sbPage.append("case 2 : \n");
		sbPage.append("if (iPage <= 1){return false;} \n");
		sbPage.append("else{" + sFormName + ".txtPageNo.value = 1;}break; \n");
		sbPage.append("case 3 : \n");
		sbPage.append("if (iPage > 1){" + sFormName + ".txtPageNo.value = iPage - 1;} \n");
		sbPage.append("else{return false;}break; \n");
		sbPage.append("case 4 : \n");
		sbPage.append("if (iPage < iAllPage){" + sFormName + ".txtPageNo.value = iPage + 1;} \n");
		sbPage.append("else{return false;}break; \n");
		sbPage.append("case 5 : \n");
		sbPage.append("if (iPage >= iAllPage){return false;} \n");
		sbPage.append("else{" + sFormName + ".txtPageNo.value = iAllPage;}break; \n");
		sbPage.append("}showSending(); \n");
		sbPage.append("return true;} \n");
		sbPage.append(" \n");
		sbPage.append("function go(frm, lDescPara){ \n");
		sbPage.append("var iAllPage = parseInt(" + sFormName + ".lPageCount.value); \n");
		sbPage.append("if (iAllPage < 1){return;} \n");
		sbPage.append("if (frm.lOrderParam.value == lDescPara){ \n");
		sbPage.append("if (frm.lDesc.value == " + Constant.PageControl.CODE_ASCORDESC_ASC + "){ \n");
		sbPage.append("frm.lDesc.value = " + Constant.PageControl.CODE_ASCORDESC_DESC + "; \n");
		sbPage.append("}else{ \n");
		sbPage.append("frm.lDesc.value = " + Constant.PageControl.CODE_ASCORDESC_ASC + ";}} \n");
		sbPage.append("frm.lOrderParam.value = lDescPara; \n");
		sbPage.append("frmSubmit(frm); \n");
		sbPage.append("} \n");
		sbPage.append(" \n");
		sbPage.append("function frmSubmit(frm) \n");
		sbPage.append("{ showSending(); \n");
		sbPage.append("frm.submit(); \n");
		sbPage.append("} \n");
		sbPage.append(" \n");
		sbPage.append("</script>");
		out.println(sbPage.toString());
	}*/
}
