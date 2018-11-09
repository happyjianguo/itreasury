<%--
系统管理－文件下载
作者:刘琰 
日期:2003-8-20 
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.servlet.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 java.rmi.*,
                 java.sql.*,
				 com.jspsmart.upload.*,
                 java.io.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
 
%>
	<!--Header end-->
<%
	//固定变量
	String strTitle = "[文件下载]";
	try
	{
		/**
		* isLogin start
		*/
		//登录检测
		
		if( sessionMng.isLogin() == false)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
			out.flush();
			return;
		}
		//判断是否有权限
		if(sessionMng.hasRight(request)==false)
		{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
		out.flush();
		return;
		}
		
		// isLogin end
		
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<html>
<body>
<form name=form1 method=post ENCTYPE = "multipart/form-data">
      <table width="70%"  align="center" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="FormTitle">
          <td  width="5" valign="top" align="left" height="25"></td>
          <td colspan="2"height="25"  class="whitetext">文件规格及下载说明</td>
          <td width="5" height="25" ></td>
        </tr>
      </table>
      
      
  <table width="70%" align="center" border="0" cellspacing="0" cellpadding="0" class=top>
    <tr > 
      <td colspan="10" height="1"></td>
    </tr>
    <tr > 
      <td  valign="middle" align="left"  height="74" width="6"></td>
      <td valign="middle" align="left"  height="74" colspan="3"> 
        <p><br>
          <font size="3">指导手册及文档:</font></p></td>
      <td width="6" valign="middle" align="left"  height="74"></td>
    </tr>
    <!--  
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>网上金融系统用户使用手册：</b></span></font></li>
        </ul></td>
      <!-- td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?DispatcherType=1&FileID=1"><font size="3">用户手册.doc(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    -->
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银用户手册：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/YHSC.rar"><font size="3">国电网银用户手册(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    
     <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银USB Key安装使用手册：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/AZSC.rar"><font size="3">网银USB Key安装使用手册(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银Q&A：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/QA.rar"><font size="3">网银Q&A(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银批量付款标准模板：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/PLFK.rar"><font size="3">网银批量付款标准模板(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    
     <tr > 
      <td  valign="middle" align="left"  height="74" width="6"></td>
      <td valign="middle" align="left"  height="74" colspan="3"> 
        <p><br>
          <font size="3">管理制度及表格:</font></p></td>
      <td width="6" valign="middle" align="left"  height="74"></td>
    </tr>
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银业务申请表：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/YWSQ.rar"><font size="3">网银业务申请表(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2"> 
        <ul>
          <li> <font size="3"><span><b>国电网银业务凭证表：</b></span></font></li>
        </ul></td>
      <td  height="50" width="355" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>ebank/upload/YWPZ.rar"><font size="3">网银业务凭证表(完整)</font></a>  
      </td 
      <td width="181" valign="middle" align="left"  height="50"></td>
      <td width="6" valign="middle" align="left"  height="25"></td>
    </tr>
    <TR>
          <TD vAlign=top align=left colSpan=3 
          height=7>注意事项:</TD>
          <TD vAlign=center align=left width=248 height=0></TD>
    </TR>
    <TR>
          <TD vAlign=top align=left colSpan=3 height=13>&nbsp;</TD>
          <TD vAlign=center align=left width=248 height=0></TD>
    </TR>
        <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>1.请用签字笔详细填写以上空缺栏位.          
            <DIV>
            </TD>       
         </TR> 
        <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>2.申请原因请在相应的选项内打√,新增系统权限请打√,删除系统权限请打×.         
            <DIV>
            </TD>       
         </TR> 
       <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>3.新开户单位可不填写客户编号、账号,新增操作员可不填写操作员编号.         
            <DIV>
            </TD>       
         </TR> 
         <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>4.账号栏,若允许操作员操作全部财务公司内部账户则在“全部”处打√,否则请填写允许操作员操作的个别账户编号.       
            <DIV>
            </TD>       
         </TR> 
         <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>5.电子邮箱用于接收PIN码或操作员密码,为确保信息安全请勿填写单位或部门公共邮箱,建议分别使用相关经办及复核人员个人邮箱.          
            <DIV>
            </TD>       
         </TR> 
         <TR> 
        <TD valign="middle" align="left"  height="40" width="6"></TD>      
          <TD vAlign=top align=left colSpan=4 height=30>　　            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>          
            <DIV>
            </TD>       
         </TR>   
    
    <!--  
     <TR>
          <TD vAlign=top align=left colSpan=3 height=13>&nbsp;</TD>
          <TD vAlign=center align=left width=248 height=0></TD></TR>
        <TR>       
          <TD vAlign=top align=left colSpan=4 
            height=30>　　各单位如有业务操作人员岗位变更请求，需要一并填写<STRONG>业务操作员变更申请表</STRONG>与<STRONG>CA证书变更申请表</STRONG>，加盖公章后传真至财务公司010－66586571，同时将申请表原件及身份证复印件等附件邮寄至<FONT 
            face=Verdana size=2>邮寄至如下地址：</FONT><FONT face=Verdana><FONT size=2> 
            <DIV><SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2>　　 </FONT></FONT></SPAN>
            
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>北京市西城区广宁伯街<SPAN 
            lang=EN-US>1</SPAN>号<SPAN 
            lang=EN-US>14</SPAN>层</STRONG></FONT></FONT></SPAN></DIV>
            
            <DIV>
            <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana 
            size=2><STRONG>　　中国国电财务有限公司（收）</STRONG></FONT></SPAN></DIV>
            <DIV><SPAN 
            style="FONT-SIZE: 16pt; COLOR: black; FONT-FAMILY: 仿宋_GB2312; mso-font-kerning: 1.0pt; mso-bidi-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA"><FONT 
            face=Verdana><FONT size=2><STRONG>　　邮政编码<SPAN lang=EN-US> 
            100001</SPAN></STRONG></FONT></FONT></SPAN></DIV></FONT></FONT>
            </TD>       
            </TR>   
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2">&nbsp;</td>
      <td  height="50" width="181" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>manual.rar"> 
        <font size="3">用户手册.zip(压缩)</font></a> </td>
      <td width="6" valign="middle" align="left"  height="50"></td>
    </tr>
    <tr > 
      <td  valign="middle" align="left"  height="40" width="6"></td>
      <td valign="top" align="left"  height="40" colspan="2">&nbsp;</td>
      <td  height="50" width="181" valign="top"> <a href="../DownLoadServlet?FileName=<%=Env.UPLOAD_PATH%>clientmanage/upload/2008/LoginDemo.rar"> 
        <font size="3">demo.zip(压缩)</font></a> </td>
      <td width="6" valign="middle" align="left"  height="50"></td>
    </tr>
 
    <TR>   
         <TD valign="middle" align="left"  height="40" width="6"></TD>
         <SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 仿宋_GB2312"><FONT 
            face=Verdana><FONT size=2><STRONG>北京市西城区广宁伯街<SPAN 
            lang=EN-US>1</SPAN>号<SPAN 
            lang=EN-US>14</SPAN>层</STRONG></FONT></FONT></SPAN>
     </TR>
      --> 
    
  </table>
	</form>
	</body>
	</html>
<%
	}
	catch (Exception ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)ie, strTitle,"",1);
	}
		OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
