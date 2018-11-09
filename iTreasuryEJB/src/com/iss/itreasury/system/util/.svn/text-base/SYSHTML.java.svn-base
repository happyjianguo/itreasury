/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 * 
 * 总体功能说明：项目里需要的页面显示函数
 * 
 * 使用注意事项： 1 2
 * 
 * 作者：陈熙
 * 
 * 更改人员：yfan
 *  
 */
package com.iss.itreasury.system.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.dataentity.OfficeInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.setting.bizlogic.OfficeBiz;
import com.iss.itreasury.system.bizdelegation.DepartmentSettingDelegation;
import com.iss.itreasury.system.setting.dataentity.DepartmentInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;

public class SYSHTML
{
  
    /**
     * 显示HTML开始的部分
     * 
     * @param out
     * @param lUserID
     *            用户标识
     *  
     */
    public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle) throws IException,IOException
    {
        String strStatus = "";
        String strRemindURL = "";
        EndDayProcess process = new EndDayProcess();
       if (sessionMng.isLogin())
        {
            try
            {
                if (sessionMng.m_lModuleID == Constant.ModuleType.SYSTEM ){
//                    strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID) + "  "
//                            + sessionMng.m_strOfficeName + "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
                	strStatus = sessionMng.m_strUserName + " " + DataFormat.getChineseTimeString(Env.getSystemDateTime()) + "  " + Env.getOfficeName(sessionMng.m_lOfficeID) + " " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + " " + Constant.SystemStatus.getChineseName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID))+"<br>";
                	if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                		strStatus += "您的上次登录时间 "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                	}
                }else{
//	                	strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString() + "  " + Env.getOfficeName(sessionMng.m_lOfficeID) + "  "
//	                	+ Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
	                	strStatus = sessionMng.m_strUserName + " " + DataFormat.getChineseTimeString(Env.getSystemDateTime()) + "  " + Env.getOfficeName(sessionMng.m_lOfficeID) + " " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + " " + Constant.SystemStatus.getChineseName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID))+"<br>";
	                	if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
	                		strStatus += "您的上次登录时间 "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
	                	}
               }
            
            }catch (Exception exp)
            {
                ;
            }
        }
        String strProject="iTreasuryPro";
        HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
        htmlHeader.setJspWriter(out);
        htmlHeader.setSessionMng(sessionMng);
        htmlHeader.setTitle(strTitle);
        htmlHeader.setShowMenu(1);
        htmlHeader.setRemindURL(strRemindURL);
        htmlHeader.setStatus(strStatus);
        htmlHeader.setProjectName(strProject);
        htmlHeader.setTitle(strProject);
        displyHtmlHeader(htmlHeader);
 
    }

    /**
     * 显示HTML开始的部分 不显示菜单
     * 
     * @param out
     * @param lUserID
     *            用户标识
     *  
     */
    public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu) throws IException, IOException
    {
        String strStatus = "";
        EndDayProcess process = new EndDayProcess();
        String strRemindURL = "";

        if (sessionMng.isLogin())
        {
            try
            {
                if (sessionMng.m_lModuleID == Constant.ModuleType.SYSTEM){
                    strStatus = sessionMng.m_strUserName + "  " + DataFormat.getChineseTimeString(Env.getSystemDateTime(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "  "
                            + sessionMng.m_strOfficeName + "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + " " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + " " + Constant.SystemStatus.getChineseName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "<br>";
                	if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                		strStatus += "您的上次登录时间 "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                	}
                }else{
                		strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateTimeString() + "  " + Env.getClientName() + "  "
                        + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)+ " " + Constant.SystemStatus.getChineseName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "<br>";
                    	if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                    		strStatus += "您的上次登录时间 "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                    	}
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
                
            }
        }
        String strProject="iTreasuryPro";
        HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
        htmlHeader.setJspWriter(out);
        htmlHeader.setSessionMng(sessionMng);
        htmlHeader.setTitle(strTitle);
        htmlHeader.setShowMenu(lShowMenu);
        htmlHeader.setRemindURL(strRemindURL);
        htmlHeader.setStatus(strStatus);
        htmlHeader.setProjectName(strProject);
        htmlHeader.setTitle(strProject);
        displyHtmlHeader(htmlHeader);

    }
    public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, String status) throws IException, IOException
    {
        String strRemindURL = "";
        String strStatus = "";       
        strStatus = status == null ? "" : status;        
        String strProject = "iTreasuryPro";        
        HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
        htmlHeader.setJspWriter(out);
        htmlHeader.setSessionMng(sessionMng);
        htmlHeader.setTitle(strTitle);
        htmlHeader.setShowMenu(1);
        htmlHeader.setRemindURL(strRemindURL);
        htmlHeader.setStatus(strStatus);
        htmlHeader.setProjectName(strProject);
        htmlHeader.setTitle(strProject);        
        displyHtmlHeader(htmlHeader);        
        

    }

	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY胡志强(kewen) 2004-12-21
	 * @param out 输出
	 * @param lTypeID 操作类型
	 */
	public static void showHomeEnd(JspWriter out) throws IOException
	{
		showHomeEnd(out, -1);
	}
	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY胡志强(kewen) 2006-04-13
	 * @param out 输出
	 * @param lTypeID 操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID) throws IOException
	{
		long lOfficeID = 1;//默认值
		long lCurrencyID = 1;//默认值
		showHomeEnd(out, lTypeID, lOfficeID, lCurrencyID);
	}
	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY kenny 2006-04-08
	 * @param out 输出
	 * @param lTypeID 操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID, long lCurrencyID) throws IOException
	{
		//String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
		//打印操作
		/*if (lTypeID == 1)
		{
			out.println("</body>\n");
			out.println("</html>");
		}*/
		//else
		//{
			//out.println("    <br></td>\n");
			//out.println("  </tr>\n");
			//out.println("</table>\n");
		//	out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		//	out.println("  <tr>\n");
		//	out.println("    <td class=\"pagefootTl\"><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
		//	out.println("  </tr>\n");
		//	out.println("  <tr>\n");
		//	out.println(
		//		"    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" 软通动力 版权所有 "
		//			
		//			+ "</td>\n");
		//	out.println("  </tr>\n");
		//	out.println("</table>\n");
			//out.println("</body>\n");
			//out.println("</html>");
		//}
		
		out.println("<br></td></tr></tbody></table></body></html>");
        //Modify by jiangqi 2011-03-30
		//out.println("<script language=javascript>try{ document.all.sending.style.visibility=\"hidden\"; }catch(e){}</script>"); 
	}

    /**
     * 通用查询数据的方法
     * 
     * @param strSQL
     *            查询语句
     * @param strField
     *            返回值对应的字段
     * @return Vector
     * @throws Exception
     */
    public static Vector getCommonSelectList(String strSQL, String strField) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        Vector v = new Vector();
        try
        {
            conn = Database.getConnection();
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Object oResult = new Object();
                oResult = rs.getObject(strField);
                v.add(oResult);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception("发生数据库错误！");
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception _ex)
            {
                System.out.println("关闭数据库连接时发生数据库错误！");
            }
        }
        return v.size() > 0 ? v : null;
    }
    
    /**
     * 通用下拉列表显示控件
     * 
     * @param out
     *            输出
     * @param strFieldName
     *            域的名称
     * @param strID
     *            ID的名称
     * @param strName
     *            标中字段的名称
     * @param strTable
     *            表名
     * @param strCondition
     *            条件
     * @param lData
     *            数据 Created by Hally Zhang,2002-01-31
     *  
     */
    public static void showCommonListControl(JspWriter out, String strFieldName, String strID, String strName, String strTable, String strCondition, long lData)
            throws Exception
    {
        long lResult = -1;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        long lTemplateID = 0;
        long lLobID = -1;
        Vector v = new Vector();
        /*判断是否下拉菜单当中要查两个值*/
        String strNames[] = strName.split(",");
        try
        {
            out.println("<select class=\"select\" name=\"" + strFieldName + "\">");
            out.println("<option value=\"-1\"></option>");
            con = Database.getConnection();
            String strTmp = new String();
          
       /**     
        *原查询，每个下拉列表都只显示一个字段的值
        * 
        */
            
       /*    strTmp = " select " + strID + " id, " + strName + " name from " + strTable + " " + strCondition;       
           //System.out.println(strTmp);
                     
            ps = con.prepareStatement(strTmp);
            rs = ps.executeQuery();
            String strSelected = "";
            while (rs.next())
            {
                long lSupplierID = rs.getLong("ID");
                if (lSupplierID == lData)
                {
                    strSelected = "selected";
                }
                out.println("<option value=\"" + rs.getLong("id") + "\"" + strSelected + ">" + rs.getString("name") + "</option>");
                strSelected = "";
            }
      */
            /**
             * 新的查询，判断需要查询几个字段
             */

            	strTmp = " select " + strID + " id, " + strName + " from " + strTable + " " + strCondition; 
            	
            	ps = con.prepareStatement(strTmp);
                rs = ps.executeQuery();
                String strSelected = "";
                while (rs.next())
                {
                    long lSupplierID = rs.getLong("ID");
                    if (lSupplierID == lData)
                    {
                        strSelected = "selected";
                    }
                    out.println("<option value=\"" + rs.getLong("id") + "\"" + strSelected + ">" );
                    for ( int i = 0 ;i < strNames.length; i++ )
                    {
                    	out.println( rs.getString( strNames[i] ));
                    }
                    
                    out.println ( "</option>" );
                    strSelected = "";          
                }            
            /*********************新查询结束*******************************/
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            con.close();
            con = null;
            out.println("</select>");
        }
        catch (Exception e)
        {
            System.out.println(" can not select OFFICE, because " + e);
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
    }


    public static void showCommonListControl(JspWriter out, String strFieldName, String strID, String strName, String strTable, String strCondition,String strProperties, long lData)	    throws Exception
	{
	long lResult = -1;
	ResultSet rs = null;
	PreparedStatement ps = null;
	Connection con = null;
	long lTemplateID = 0;
	long lLobID = -1;
	Vector v = new Vector();
	try
	{
	    out.println("<select class=\"select\" name=\"" + strFieldName + "\" "+strProperties+" >");
	    out.println("<option value=\"-1\"></option>");
	    con = Database.getConnection();
	    String strTmp = new String();
	    strTmp = " select " + strID + " id, " + strName + " name from " + strTable + " " + strCondition;
	    ps = con.prepareStatement(strTmp);
	    rs = ps.executeQuery();
	    String strSelected = "";
	    while (rs.next())
	    {
	        long lSupplierID = rs.getLong("ID");
	        if (lSupplierID == lData)
	        {
	            strSelected = "selected";
	        }
	        out.println("<option value=\"" + rs.getLong("id") + "\"" + strSelected + ">" + rs.getString("name") + "</option>");
	        strSelected = "";
	    }
	    rs.close();
	    rs = null;
	    ps.close();
	    ps = null;
	    con.close();
	    con = null;
	    out.println("</select>");
	}
	catch (Exception e)
	{
	    System.out.println(" can not select OFFICE, because " + e);
	    throw e;
	}
	finally
	{
	    if (rs != null)
	    {
	        rs.close();
	        rs = null;
	    }
	    if (ps != null)
	    {
	        ps.close();
	        ps = null;
	    }
	    if (con != null)
	    {
	        con.close();
	        con = null;
	    }
	}
	}
    
    public static void showOfficeListControl(JspWriter out,String controlName,String properties,long isSelectValue,boolean isNeedAll,boolean isNeedBlank)
    {
    	OfficeBiz biz = new OfficeBiz();
    	OfficeInfo info = new OfficeInfo();
    	ArrayList list = new ArrayList();
    	long[] lArrayID = null;
    	String[] strArrayName = null;
    	Iterator it = null;
    	int i = 0;
    	try
    	{
    		list = biz.findOfficeInformation();
    		if(list!=null)
    		{
	    		lArrayID = new long[list.size()];
	    		strArrayName = new String[list.size()];
	    		it = list.iterator();
	    		while(it.hasNext())
	    		{
	    			info = (OfficeInfo)it.next();
	    			lArrayID[i] = info.getM_lID();
	    			strArrayName[i] = info.getM_strName();
	    			i++;
	    		}
    		}
    		else
    		{
    			lArrayID = new long[]{-1};
    			strArrayName = new String[]{""};
    		}
    		out.println("<select class=\"select\" name=\""+controlName+"\" "+properties+">");
    		if(isNeedAll==true)
    		{
    			if(isSelectValue==0)
    			{
    				out.println("<option value='0' selected>全部</option>");
    			}
    			else
    			{
    				out.println("<option value='0'>全部</option>");
    			}
    		}
    		if(isNeedBlank==true)
    		{
    			if(isSelectValue == -1)
    			{
    				out.println("<option value='-1' selected></option>");
    			}
    			else
    			{
    				out.println("<option value='-1'></option>");
    			}
    			
    		}
    		for(int j = 0;j<lArrayID.length;j++)
    		{
    			
    			if(lArrayID[j]==isSelectValue)
    			{
    				out.println("<option value='"+lArrayID[j]+"' selected>"+strArrayName[j]+"</option>");
    			}
    			else
    			{
    				out.println("<option value='"+lArrayID[j]+"'>"+strArrayName[j]+"</option>");
    			}
    		}
    		out.println("</select>");
    		

    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader) throws IException, IOException
    {
    	htmlHeader.getJspWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
    	htmlHeader.getJspWriter().println("<html>");
    	htmlHeader.getJspWriter().println("<head>");
    	htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
        htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
        htmlHeader.getJspWriter().println("<!--\n");
        htmlHeader.getJspWriter().println("function sendandquit()\n");
        htmlHeader.getJspWriter().println("{\n");
        htmlHeader.getJspWriter().println("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
        htmlHeader.getJspWriter().println("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
        htmlHeader.getJspWriter().println("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("\n");
        htmlHeader.getJspWriter().println("function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
        htmlHeader.getJspWriter().println("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
        htmlHeader.getJspWriter().println("  if (restore) selObj.selectedIndex=0;\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("//-->\n");
        htmlHeader.getJspWriter().println("</Script>\n");
        htmlHeader.getJspWriter().println("\n");
        htmlHeader.getJspWriter().println("\n");
        //Modify by leiyang date 2007/07/24
        //htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
        //htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/cm_addins.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");
        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
        htmlHeader.getJspWriter().println("</head>\n");
        htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
        //Modify by jiangqi 2011-03-30
        //htmlHeader.getJspWriter().println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");         
        //htmlHeader.getJspWriter().println("<script language=javascript> function showSending(){ gnIsShowSending=1;        sending.style.visibility=\"visible\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
	        htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
	        htmlHeader.getJspWriter().println("<tr><td height=\"5\"></td></tr>");
	        htmlHeader.getJspWriter().println("<tr><td>");

    }

    /**
     * 通用下拉列表显示控件(带nextfield)
     * 
     * @param out
     *            输出
     * @param strFieldName
     *            域的名称
     * @param strID
     *            ID的名称
     * @param strName
     *            标中字段的名称
     * @param strTable
     *            表名
     * @param strCondition
     *            条件
     * @param lData
     *            数据
     * @param strDesc
     *            其他属性 Created by Hally Zhang,2002-01-31
     *  
     */
    public static void showCommonListControl(JspWriter out, String strFieldName, String strID, String strName, String strTable, String strCondition,
            long lData, String strDesc) throws Exception
    {
        long lResult = -1;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        long lTemplateID = 0;
        long lLobID = -1;
        Vector v = new Vector();
        try
        {
            out.println("<select " + strDesc + " class=\"select\" name=\"" + strFieldName + "\">");
            out.println("<option value=\"-1\"></option>");
            con = Database.getConnection();
            String strTmp = new String();
            strTmp = " select " + strID + " id, " + strName + " name from " + strTable + " " + strCondition;
            //System.out.println(strTmp);
            ps = con.prepareStatement(strTmp);
            rs = ps.executeQuery();
            String strSelected = "";
            while (rs.next())
            {
                long lSupplierID = rs.getLong("ID");
                if (lSupplierID == lData)
                {
                    strSelected = "selected";
                }
                out.println("<option value=\"" + rs.getLong("id") + "\"" + strSelected + ">" + rs.getString("name") + "</option>");
                strSelected = "";
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            con.close();
            con = null;
            out.println("</select>");
        }
        catch (Exception e)
        {
            System.out.println(" can not select OFFICE, because " + e);
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
    }

    /**
     * 通用下拉列表显示控件
     * 
     * @param out
     *            输出
     * @param collection
     *            查询结果集
     * @param strControlName
     *            控件名称
     * @param strDisplayField
     *            显示字段
     * @param strID
     *            显示字段对应标识
     * @param lSelectValue
     *            被选择项对应值
     * @param isNeedAll
     *            是否需要”全部“项
     * @param strProperty
     *            下拉列表属性
     * @param isNeedBlank
     *            是否需要空白行
     * @throws Exception
     */
    public static void showCommonListControl(JspWriter out, Collection collection, String strControlName, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank) throws Exception
    {
        long lResult = -1;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        long lTemplateID = 0;
        long lLobID = -1;
        Vector v = new Vector();
        try
        {
            out.println("<select class=\"select\" name=\"" + strControlName + "\"" + strProperty + ">");
            if (isNeedBlank == true)
            {
                out.println("<option value=\"-1\">&nbsp;</option>");
            }
            if (collection != null && collection.size() > 0)
            {
                Iterator it = collection.iterator();
                while (it.hasNext())
                {
                    DepartmentInfo obj = (DepartmentInfo) it.next();
                    if (obj.getId() == lSelectValue)
                    {
                        Log.print("有selected");
                        out.println("<option value=\"" + obj.getId() + "\" selected>" + obj.getDepartmentName() + "</option>");
                    }
                    else
                    {
                        out.println("<option value=\"" + obj.getId() + "\">" + obj.getDepartmentName() + "</option>");
                    }
                }
                if (isNeedAll == true)
                {
                    if (lSelectValue == 0)
                    {
                        out.println("<option value=\"0\" selected>全部</option>");
                    }
                    else
                    {
                        out.println("<option value=\"0\" >全部</option>");
                    }
                }
                out.println("</select>");
            }
            else
            {
                out.println("</select>");
            }
        }
        catch (Exception e)
        {
            //System.out.println(" can not select OFFICE, because " + e);
            //throw e;
            Log.print("显示下拉列表异常：" + e.toString());
        }
    }

    /**
     * 显示一般信息(错误信息，消息等)
     * 
     * @param out
     *            输出
     * @param sessionMng
     *            Session
     * @param exception
     *            异常
     * @param request
     * @param response
     * @param strTitle
     *            页面的Title
     * @param lTypeID
     *            是否带头
     */
    public static void showExceptionMessage(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request,
            HttpServletResponse response, String strTitle, long lTypeID) throws Exception
    {
        showException(out, sessionMng, exception, request, response, strTitle, lTypeID, "");
    }

    public static void showMessage(JspWriter out, SessionMng sessionMng, HttpServletRequest request, HttpServletResponse response, String strTitle,
            long lTypeID, String strErroCode) throws Exception
    {
        showException(out, sessionMng, null, request, response, strTitle, lTypeID, strErroCode);
    }

    public static void showException(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request, HttpServletResponse response,
            String strTitle, long lTypeID, String strErroCode) throws Exception
    {
        String strErroMessage = "";
        if (strErroCode != null && strErroCode.length() > 0)
        {
            strErroMessage = IExceptionMessage.getExceptionMessage(strErroCode);
        }
        if (exception != null)
        {
            strErroMessage = IExceptionMessage.getExceptionMSG(exception);
        }
        System.out.println("**************IExceptionMessage:" + strErroMessage);
        if (lTypeID == Constant.RecordStatus.VALID)
        {
            showHomeHead(out, sessionMng, strTitle);
        }else
        {
        	showHomeHead(out, sessionMng, strTitle,Constant.RecordStatus.getName(lTypeID));
        }
        out.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
        out.println("  <TBODY>\n");
        out.println("  <TR>\n");
        //out.println(" <TD class=FormTitle height=2 width=\"100%\"><B>" +
        // Env.getClientName() + "</B></TD></TR>\n");
        out.println("    <TD class=FormTitle height=2 width=\"100%\"><B></B></TD></TR>\n");
        out.println("  <TR>\n");
        out.println("      <TD height=190 vAlign=bottom width=\"100%\"> \n");
        out.println("        <TABLE align=center height=187 width=\"97%\">\n");
        out.println("        <TBODY>\n");
        out.println("        <TR>\n");
        out.println("          <TD height=40 vAlign=top width=\"96%\">\n");
        out.println("              <TABLE align=center border=1 borderColor=#999999 height=177 \n");
        out.println("            width=\"99%\">\n");
        out.println("                <TBODY> \n");
        out.println("                <TR borderColor=#D7DFE5 vAlign=center> \n");
        out.println("                  <TD height=162 colspan=\"3\" align=\"center\">" + strErroMessage + "</TD>\n");
        out.println("                </TR>\n");
        out.println("                </TBODY> \n");
        out.println("              </TABLE>\n");
        out.println("            </TD></TR></TBODY></TABLE></TD></TR>\n");
        out.println("  <TR>\n");
        out.println("    <TD height=11 vAlign=top width=\"100%\">\n");
        out.println("      <TABLE align=center height=17 width=\"97%\">\n");
        out.println("        <TBODY>\n");
        out.println("        <TR vAlign=center>\n");
        out.println("          <TD colSpan=6 height=40>\n");
        out.println("            <DIV align=center>\n");
	        String URL=Constant.SDCURL;
	        if("#".equals(URL)){
	        	URL=request.getContextPath()+"/Index.jsp";
	        }
	        if (strErroMessage == null || strErroMessage.length() <= 0)
	        {
	        		out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\" 返 回 \"> \n");
	        }
	        else if(strErroMessage.equals("登录成功。"))
	        {
	        	out.println("<br>");
	        }
	        else
	        {
	        	//modified by qhzhou 2008-03-06-16
				if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
					out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+URL+"';\"  value=\" 确 认 \"> \n");
				}else{
					out.println("			 <INPUT type=\"button\" class=button name=\"goback\"  onclick=\"javascript:history.back(-1)\" value=\" 返 回 \"> \n");
	        	}
	        }
        out.println("            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
	        out.println("<SCRIPT language=JavaScript>");
	       // out.println("   goback.focus();");
	        out.println("</SCRIPT>");
        if (lTypeID == Constant.RecordStatus.VALID)
        {
            showHomeEnd(out);
        }
    }

    public static void showDepartmentCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
    {
        DepartmentSettingDelegation delegation = new DepartmentSettingDelegation();
        Collection collection = delegation.findAllDepartment();
        showCommonListControl(out, collection, strControlName, lSelectValue, isNeedAll, strProperty, true);
    }
}