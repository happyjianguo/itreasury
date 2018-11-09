<%--
/**
 页面名称 ：a201-v.jsp
 页面功能 : 人民币存款利率表
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
    Log.print("\n*******进入页面--ebank/interestinfo/a201-v.jsp******\n");
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#1A70A3">
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="9"><span class="style6"><%=Env.getClientName()%>人民币存贷款利率表</span></td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="9">（2007年07月21日起执行）</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">存款项目</td>
              <td height="25">月利率‰</td>
              <td height="25">年利率%</td>
              <td height="25" colspan="2">贷款项目</td>
              <td height="25">基准月<br>
                利率‰</td>
              <td height="25">基准年<br>
                利率%</td>
              <td height="25">下浮10%以<br>
                后年利率</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">活期</td>
              <td height="25">0.675</td>
              <td height="25">0.81</td>
              <td height="25" rowspan="2">短期</td>
              <td height="25">六个月以内<br>
                （含六个月）</td>
              <td height="25">5.025</td>
              <td height="25">6.03</td>
              <td height="25">5.427<br></td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" rowspan="6">定期</td>
              <td height="25">三个月</td>
              <td height="25">1.95</td>
              <td height="25">2.34</td>
              <td height="25">六个月至一年<br>
                （含一年）</td>
              <td height="25">5.7<br></td>
              <td height="25">6.84</td>
              <td height="25">6.156<br></td>
            </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">六个月</td>
              <td height="25">2.4<br></td>
              <td height="25">2.88</td>
              <td height="25" rowspan="3">中长期</td>
              <td height="25">一至三年<br>
                （含三年）</td>
              <td height="25">5.85<br></td>
              <td height="25">7.02</td>
              <td height="25">6.318<br></td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">一年</td>
              <td height="25">2.775<br></td>
              <td height="25">3.33</td>
              <td height="25">三至五年<br>
                （含五年）</td>
              <td height="25">6<br></td>
              <td height="25">7.20</td>
              <td height="25">6.48<br></td>
            </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">二年</td>
              <td height="25">3.3<br></td>
              <td height="25">3.96</td>
              <td height="25">五年以上</td>
              <td height="25">6.15<br></td>
              <td height="25">7.38</td>
              <td height="25">6.642<br></td>
            </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">三年</td>
              <td height="25">3.9<br></td>
              <td height="25">4.68</td>
              <td height="25" colspan="2" rowspan="4">逾期贷款罚息利率</td>
              <td height="25" colspan="3" rowspan="4">借款合同载明的贷款利率<br>
              <!--
              <td height="25" colspan="2" rowspan="2">融资租赁<br>
                买方信贷</td>
              <td height="25" colspan="3" rowspan="2">按照同期同档次法定<br>
                贷款利率（含浮动）执行</td>   -->
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">五年</td>
              <td height="25">4.35<br></td>
              <td height="25">5.22</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">协定存款</td>
              <td height="25">&#19981;&#36229;&#36807;1.275</td>
              <td height="25">&#19981;&#36229;&#36807;1.53</td>
              <!-- 
              <td height="25" colspan="2">委托贷款</td>
              <td height="25" colspan="3">不超过同期同档次法定<br>
                贷款利率（含浮动）</td>-->
              </tr>
              <!-- 
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" rowspan="2">存放央行准备金</td>
              <td height="25">法定</td>
              <td height="25">1.575</td>
              <td height="25">1.89</td>
              <td height="25" colspan="2" rowspan="2">逾期贷款罚息利率</td>
              <td height="25" colspan="3" rowspan="2">借款合同载明的贷款利率<br>
                水平上加收30%-50%</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25">超额</td>
              <td height="25">0.825</td>
              <td height="25">0.99</td>
              </tr>-->
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">同业往来</td>
              <td height="25" colspan="2">双方协商</td>
              <!-- 
              <td height="25" colspan="2" rowspan="3">未按合同约定用途<br>
                使用借款罚息利率</td>
              <td height="25" colspan="3" rowspan="3">借款合同载明的贷款利率<br>
                水平上加收50%-100%</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">委托业务月手续费率</td>
              <td height="25" colspan="2">不超过3‰</td>
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="2">贴现<br>
                （再贴现利率3.24%）</td>
              <td height="25" colspan="2">在再贴现利率基础<br>
                上，
                按不超过同期<br>
                贷款利率
                <br>
                （含浮动）加点</td>-->
              </tr>
            <tr align="center" bgcolor="#FFFFFF" class="zw1">
              <td height="25" colspan="9">担保、信用签证、财务顾问及其他咨询代理等业务收费按中国人民银行有关规定执行</td>
              </tr>
          </table>
<!--
<table width="583" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" height="319"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" height="332" bgcolor="#FFFFFF">
        <tr> 
          <td width="537" valign="top" height="318"> 
            <table width="484" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td height="42" class="FormTitle"> 
                  <div align="center" class="unnamed3"><b>人民币存款利率表</b></div>
                </td>
              </tr>
              <tr> 
                <td valign="top" height="482"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="ItemList">
                    <tr > 
                      <td class="ItemTitle" width="55%" height="32"> 
                        <div align="center" ><b>项目</b></div>
                      </td>
                      <td class="ItemTitle" width="45%" height="32"> 
                        <div align="center"><b>年利率（％） </b></div>
                      </td>
                    </tr>
                    <tr > 
                      <td class="unnamed2" colspan="2" > 
                        <table width="100%" border="0" cellspacing="1" cellpadding="0" >
                          <tr > 
                            <td class="ItemBody" width="55%"height="25" > 
                              一、活期存款</td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">0.72 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              二、定期存款 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center"></div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;整存整取 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center"></div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　三个月 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.71 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　半年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">2.07 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　一年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">2.25 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　二年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">2.70 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　三年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">3.24 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　五年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">3.60 </div>
                            </td>
                          </tr>
						  <!--
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　（二）零存整取、整存零取、存本取息</td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center"></div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　一年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.71 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　三年 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.89 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　　　　五年</td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.98 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　（三）定活两便 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">按一年以内定期整存整取同档次利率打六折执行 </div>
                            </td>
                          </tr>
						  
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              三、协定存款 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.44 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25" > 
                              四、通知存款 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center"></div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　一天 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.08 </div>
                            </td>
                          </tr>
                          <tr bgcolor="#EEFFFF"> 
                            <td class="ItemBody" width="55%" height="25"> 
                              　　七天 </td>
                            <td class="ItemBody" width="45%" height="25"> 
                              <div align="center">1.62 </div>
                            </td>
                          </tr>
                        </table>
                        <div align="center" class="ItemTitle">注：本表从2004年10月29日起实行并按照人民银行公布的利率随时调整。</div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
-->
</body>
<%
        OBHtml.showOBHomeEnd(out);
    } catch(Exception e) {
        System.out.println(e.getMessage());
    }
%>

<%@ include file="/common/SignValidate.inc" %>