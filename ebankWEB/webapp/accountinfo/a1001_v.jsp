<%--
 页面名称 ：a002_v.jsp
 页面功能 : 账户余额显示余额
 作    者 ：
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryAmountBiz"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBBankAccountInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

	<%
        try
        {
			Log.print("*******进入页面--ias_hotdeploy_iTreasury-ebank/iTreasury-ebank/accoutinfo/a1001_v.jsp*******");
			
			long clientID=-1;
			clientID=sessionMng.m_lClientID;
			boolean isTrue = false;
			String Temp = "";
			Temp = request.getParameter("isTrue");
			if(Temp != null && !Temp.equals(""))
			{
				isTrue = new Boolean(Temp).booleanValue();
			}
			
			OBHtml.showOBHomeHead(out, sessionMng, "银行账户对比查询", 1);
			OBAccountQueryAmountBiz biz=new OBAccountQueryAmountBiz();
			Collection coll=null;
			coll=biz.getBankAcctByClientID(clientID);
			
	%>
	<form name="frmv1001" method="post" action="a1002_c.jsp">
	<input name="strSuccessPageURL" type="hidden" value="a1001_v.jsp" />
	<input name="strFailPageURL" type="hidden" value="a1001_v.jsp" />

	       <TABLE width="730" border=1 cellpadding="1" cellspacing="1" class="ItemList">
		<tr > 
              
                    <td align="center" class="ItemTitle">内部账号</td>
                    <td align="center" class="ItemTitle">银行账号</td>
                    <td align="center" class="ItemTitle">银行账户名称</td>
                    <td align="center" class="ItemTitle">银行名称</td>
                    <!-- <td align="center" class="ItemTitle">银行编码</td>-->
                    <td align="center" class="ItemTitle">所在国家</td>
                  </tr>
                  <%if(!coll.isEmpty()){
					for(Iterator iter=coll.iterator();iter.hasNext();){
						OBBankAccountInfo info=(OBBankAccountInfo)iter.next();
						String nbAcctNo=null;
						String bankAcctNo=null;
						String bankAcctName=null;
						String bankName=null;
						String bankCode=null;
						String bankCity=null;
						nbAcctNo=DataFormat.formatString(info.getNbAcctNO());
						bankAcctNo=DataFormat.formatString(info.getBankAcctNO());
						bankAcctName=DataFormat.formatString(info.getBankAcctName());
						bankName=DataFormat.formatString(info.getBankName());
						bankCode=DataFormat.formatString(info.getBankCode());
						bankCity=DataFormat.formatString(info.getBankCity());
						if(nbAcctNo==null||nbAcctNo.equals("null")){nbAcctNo="";}
						if(bankAcctNo==null||bankAcctNo.equals("null")){bankAcctNo="";}
						if(bankAcctName==null||bankAcctName.equals("null")){bankAcctName="";}
						if(bankName==null||bankName.equals("null")){bankName="";}
						if(bankCode==null||bankCode.equals("null")){bankCode="";}
						if(bankCity==null||bankCity.equals("null")){bankCity="";}
				%>
                  <tr>
                    <td align="center" class="ItemBody"><%=nbAcctNo%></td>
                    <td align="center" class="ItemBody"><%=bankAcctNo%></td>
                    <td align="center" class="ItemBody"><%=bankAcctName%></td>
                    <td align="center" class="ItemBody"><%=bankName%></td>
                    <!--<td align="center" class="ItemBody"><%=bankCode%></td>-->
                    <td align="center" class="ItemBody"><%=bankCity%></td>
                  </tr>
                  <%}}else{ %>
                  <tr>
                    <td align="center" class="ItemBody">&nbsp;</td>
                    <td align="center" class="ItemBody">&nbsp;</td>
                    <td align="center" class="ItemBody">&nbsp;</td>
                    <td align="center" class="ItemBody">&nbsp;</td>
                    <td align="center" class="ItemBody">&nbsp;</td>
                    
                  </tr>
                  <%} %>
                </table>


<%				
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}
		OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>