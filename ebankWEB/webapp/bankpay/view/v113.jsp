<%--
/*
 * 程序名称：v113.jsp
 * 功能说明：银行直联交易申请查询输入页面下载
 * 作　　者：niweinan
 * 完成日期：2010-10-20
 */
--%>

<%@ page contentType="application/msexcel;charset=gbk"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    response.setHeader("Content-Disposition",";filename=\treport.xls");
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />


<%
    //标题变量
    String strTitle = "［交易申请查询］";
    try {
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

       
        //从请求中获取查询结果信息
        Collection lstQuery = (Collection) request.getAttribute("cltQcf");
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
        // 系统时间
        
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<HTML>
<HEAD><TITLE></TITLE>
<style type="text/css">
<!--
.table1 {  border: 1px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
-->
</style>
</HEAD>
<BODY text="#000000">
<%
    String sPreConfirmDate = "";    //前一个确认日期
    String sConfirmDate = "";       //确认日期
    String sPrePayerAcctNo = "";    //前一个活期存款
    String sPayerAcctNo = "";       //活期存款

    
%>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
       <tr>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>指令序号</td>
       
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>交易类型</td>
        
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>借/贷</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>金额</td>
         <td align="center" colspan="2" class="td-rightbottom" nowrap>对方资料</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>状态</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>交易编号</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>汇款用途</td>
    	 <td align="center" rowspan="2" class="td-rightbottom" nowrap>备注</td>
       </tr>
       <tr>
      
         <td align="center" class="td-rightbottom" nowrap>名称</td>
      
         <td align="center" class="td-rightbottom" nowrap>账号</td>
       </tr>
<%
        if (listQuery != null) {
            //根据排序规则，按格式显示所有记录
            while(listQuery != null && listQuery.hasNext()) {
                OBBankPayInfo info = (OBBankPayInfo)listQuery.next(); // 获取下一条记录信息
                sPreConfirmDate = sConfirmDate;
              
                sConfirmDate = DataFormat.getDateString(info.getDtconfirm());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
%>
       <tr>
         <td align="left" class="td-topright">提交日期：</td>
         <td align="left" colspan="3" class="td-topright"><%=sConfirmDate%></td>
       
         <td colspan="6" class="td-topright"><%="&nbsp;&nbsp;"%></td>
       
       </tr>
<%
                }
                sPrePayerAcctNo = sPayerAcctNo;
                sPayerAcctNo = info.getS_accountno();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
%>
       <tr>
        
         <td colspan="1" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td colspan="1" class="td-topright">账号：</td>
         <td colspan="3" class="td-topright"><%=sPayerAcctNo==""?"&nbsp;&nbsp;":sPayerAcctNo+"&nbsp;"%></td>
         <td colspan="5" class="td-topright"><%="&nbsp;&nbsp;"%></td>
     
       </tr>
<%
                }
%>
       <tr>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;<%=String.valueOf(info.getId())%></td>
         <td align="center" class="td-topright" nowrap>
		 	
            	     <%=OBConstant.SettInstrType.getName(23)%></td>
            	   
         <td align="center" class="td-topright" nowrap>
<%
                out.print("借");
           
%>
         </td>
         <td align="right" class="td-topright" nowrap>
             <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%>
         </td>
        
         <td align="center" class="td-topright" nowrap><%=info.getSpayeeacctname()%></td>
        
         <td align="center" class="td-topright" nowrap><%=info.getSpayeeacctno()+"&nbsp;"%></td>
         <td align="center" class="td-topright" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getNstatus())%>
         </td>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
            
         </td>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
             <%=info.getSnote()== null||info.getSnote()==""?"&nbsp;&nbsp;":info.getSnote()%>
         </td>
      	<td align="center" class="td-topright" nowrap>
            <%=info.getCancelNote()==null || info.getCancelNote()==""?"&nbsp;&nbsp;":info.getCancelNote()%></td>
       </tr>
<%
            }
        } else {
%>
      <tr>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
      
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>


    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
      <tr>
        <td align="left" class="td-topright">查询时间：<%=dtSysDate%></td>
      </tr>
    </table>
</BODY>
</HTML>
<%
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
