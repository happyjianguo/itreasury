<%--
/**
 页面名称 ：a202-v.jsp
 页面功能 : 外币存款利率表
 作    者 ： kewen hu
 日    期 ： 2004-01-29
 特殊说明 ：实现操作说明：
 修改历史 ：
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Zoom.js"></script>
<safety:resources />

<%
try {
    Log.print("\n*******进入页面--ebank/interestinfo/a202-v.jsp******\n");
    // 请求检测
    long lShowMenu = 1;
    if (request.getParameter("sm") != null)
        lShowMenu = Long.parseLong( request.getParameter("sm") );
    if (lShowMenu != 2) {
        lShowMenu = 1;
    }
    if (lShowMenu == 1) {
        // 用户登录检测
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, "", "中国交通建设股份有限公司资金结算中心", lShowMenu,"");
            out.flush();
            return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, "", "NO WAY", lShowMenu,"");
            out.flush();
            return;
        }
    }
    OBHtml.showOBHomeHead(out, sessionMng, "", lShowMenu);
%>
<!--jsp:include page="/ShowMessage.jsp"/-->
<style type="text/css">
<!--
.unnamed1 {  font-size: 12px; line-height: 25px; text-decoration: none}
.unnamed2 {  font-size: 13px; line-height: 25px}
.unnamed3 {  font-size: 14px}
-->
</style>

<script language="JavaScript">
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
</script>

<body topmargin=0 leftmargin=0 text="#000000" link="#000000" vlink="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="1" bgcolor="#FFFFFF">
  <tr>
    <td valign="top" height="319"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" height="332" bgcolor="#FFFFFF">
        <tr> 
          <td width="100%" valign="top" height="318"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
              
              <tr> 
                <td valign="top" height="188"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="0"  >
                  
                    <tr bgcolor="#FFFFFF" valign="top"> 
                      <td class="ItemTitle" height="121"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#1A70A3">
                          	
                          	<tr bgcolor="#FFFFFF"  class="zw1"> 
				                <td height="42" colspan="8"> 
				                  <div align="center" class="unnamed3"><b>外币存款利率表</b></div>
				                </td>
		          		    </tr>
		                    <tr bgcolor="#FFFFFF"  class="zw1"> 
		                      <td  height="35" colspan="8"> 
		                        <div align="center"><b>外币小额存款利率表 年利率：％</b></div>
		                      </td>
		                    </tr>
                          	
                          <tr bgcolor="#FFFFFF"  class="zw1"> 
                            <td  width="83" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">项目</div>
                            </td>
                            <td width="47" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">美元</div>
                            </td>
                            <td  width="55" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">日元</div>
                            </td>
                            <td  width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">欧元</div>
                            </td>
                            <td  width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">英镑</div>
                            </td>                                                       
                            <td  width="61" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">港币</div>
                            </td>
                            <td  width="66" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">加拿大元</div>
                            </td>
                            <td  width="71" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">瑞士法郎</div>
                            </td>
                          </tr>
                          <tr bgcolor="#FFFFFF" valign="top"> 
                            <td class="ItemBody" width="83" height="31" bgcolor="#FFFFFF"> 
                              <div align="center">活期</div>
                            </td>
                            <td class="ItemBody" width="47" height="31" bgcolor="#FFFFFF"> 
                              <div align="center">1.1500</div>
                            </td>
                             <td class="ItemBody" width="55" height="31" bgcolor="#FFFFFF"> 
                              <div align="center">0.0001</div>
                            </td>
                            <td class="ItemBody" width="54" height="31" bgcolor="#FFFFFF"> 
                              <div align="center">0.1000</div>
                            </td>
                            <td class="ItemBody" width="54" height="31" bgcolor="#FFFFFF"> 
                              <div align="center">0.3000</div>
                            </td>                                                   
                            <td class="ItemBody" width="61" height="31" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">1.0000</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="31" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">0.1500</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="31" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">0.0500</div></div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">七天通知存款</div>
                            </td>
                            <td class="ItemBody" width="47" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">1.3750</div>
                            </td>
                            <td class="ItemBody" width="55" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">0.0005</div>
                            </td>
                            <td class="ItemBody" width="54" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">0.3750</div>
                            </td>
                            <td class="ItemBody" width="54" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">1.0000</div>
                            </td>                           
                            <td class="ItemBody" width="61" height="45" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">1.2500</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="45" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">0.3750</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="45" bgcolor="#FFFFFF"> 
                              <div align="center">0.0625</div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="35" bgcolor="#FFFFFF"> 
                              <div align="center">一个月</div>
                            </td>
                            <td class="ItemBody" width="47" height="35" bgcolor="#FFFFFF""> 
                              <div align="center">2.2500</div>
                            </td>
                            <td class="ItemBody" width="55" height="35" bgcolor="#FFFFFF"> 
                              <div align="center">0.0100</div>
                            </td>
                            <td class="ItemBody" width="54" height="35" bgcolor="#FFFFFF"> 
                              <div align="center">0.7500</div>
                            </td>
                            <td class="ItemBody" width="54" height="35" bgcolor="#FFFFFF"> 
                              <div align="center">1.7500</div>
                            </td>                                                     
                            <td class="ItemBody" width="61" height="35" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">1.8750</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="35" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">0.6250</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="35" bgcolor="#FFFFFF"> 
                              <div align="center">0.1000</div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="34" bgcolor="#FFFFFF"> 
                              <div align="center">三个月</div>
                            </td>
                            <td class="ItemBody" width="47" height="34" bgcolor="#FFFFFF"> 
                              <div align="center">2.7500</div>
                            </td>
                            <td class="ItemBody" width="55" height="34" bgcolor="#FFFFFF"> 
                              <div align="center">0.0100</div>
                            </td>
                            <td class="ItemBody" width="54" height="34" bgcolor="#FFFFFF"> 
                              <div align="center">1.0000</div>
                            </td>
                            <td class="ItemBody" width="54" height="34" bgcolor="#FFFFFF"> 
                              <div align="center">2.3125</div>
                            </td>                                                      
                            <td class="ItemBody" width="61" height="34" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">2.3750</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="34" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">0.8750</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="34" bgcolor="#FFFFFF""> 
                              <div align="center"><div align="center">0.1250</div></div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">六个月</div>
                            </td>
                            <td class="ItemBody" width="47" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">2.8750</div>
                            </td>
                            <td class="ItemBody" width="55" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">0.0100</div>
                            </td>
                            <td class="ItemBody" width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">1.1250</div>
                            </td>
                            <td class="ItemBody" width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">2.6875</div>
                            </td>                                                      
                            <td class="ItemBody" width="61" height="36" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">2.5000</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="36" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">1.0625</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">0.2500</div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">一年</div>
                            </td>
                            <td class="ItemBody" width="47" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">3.0000</div>
                            </td>
                            <td class="ItemBody" width="55" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">0.0100</div>
                            </td>
                            <td class="ItemBody" width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">1.2500</div>
                            </td>
                            <td class="ItemBody" width="54" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">3.0625</div>
                            </td>                                                       
                            <td class="ItemBody" width="61" height="36" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">2.6250</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="36" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">1.5625</div></div>
                            </td>
                            <td class="ItemBody" width="71" height="36" bgcolor="#FFFFFF"> 
                              <div align="center">0.5000</div>
                            </td>
                          </tr>
                          <tr> 
                            <td class="ItemBody" width="83" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">二年</div>
                            </td>
                            <td class="ItemBody" width="47" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">3.2500</div>
                            </td>
                            <td class="ItemBody" width="55" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">0.0100</div>
                            </td>
                            <td class="ItemBody" width="54" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">1.3125</div>
                            </td>
                            <td class="ItemBody" width="54" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">3.1250</div>
                            </td>                                                    
                            <td class="ItemBody" width="61" height="29" bgcolor="#FFFFFF"> 
                              <div align="center"><div align="center">2.7500</div></div>
                            </td>
                            <td class="ItemBody" width="66" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">1.6250</div>
                            </td>
                            <td class="ItemBody" width="71" height="29" bgcolor="#FFFFFF"> 
                              <div align="center">0.5625</div>
                            </td>
                          </tr>
                        </table>
						<tr bgcolor="#FFFFFF" class="zw1">
							<td height="29">
								<div align="center" >&#27880;&#65306;&#26412;&#34920;&#20174;2007&#24180;5&#26376;19&#26085;&#24320;&#22987;&#25191;&#34892;&#65292;&#24180;&#21033;&#29575;%&#12290;&#26412;&#21033;&#29575;&#34920;&#20013;&#30340;&#21033;&#29575;&#20165;&#20379;&#21442;&#32771;&#65292;&#20855;&#20307;&#35814;&#24773;&#35831;&#21672;&#35810;&#32593;&#28857;&#26588;&#21488;&#12290;</div>
							</td>
						</tr>
                  </table>
                  <br>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
<%
        OBHtml.showOBHomeEnd(out);
    } catch(Exception e) {
        System.out.println(e.getMessage());
    }
%>

<%@ include file="/common/SignValidate.inc" %>