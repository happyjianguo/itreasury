<%
/**
 * 页面名称 ：q075-v.jsp
 * 页面功能 : 贷款展期申请处理-新增
 * 作    者 ：方远明
 * 日    期 ：2003-10-23
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S611.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.extendapply.bizlogic.*,
	com.iss.itreasury.loan.extendapply.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.loan.repayplan.bizlogic.*,
	com.iss.itreasury.loan.repayplan.dataentity.*,
	com.iss.itreasury.system.bizlogic.*,
	com.iss.itreasury.system.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.ebank.approval.dataentity.*,
	com.iss.itreasury.ebank.approval.bizlogic.*,
	
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	try
	{
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		long lModuleID = Constant.ModuleType.LOAN;
		//模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER ;
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		
    	// 定义变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		String strTmp = "";
		String sTitle = "一般贷款";
		String sAction = "";
		String sEType = "展期";

        String sAmount = "";
		double dAmount = 0;
		double dBalance = 0;
		Timestamp dtExtendStartDate = null;
		Timestamp dtInputDate = null;
		double dRate = 0;
		String sReason = "",sSource = "",sOther = "";
		long nNum = -1;
		String sLiborName = "";
		boolean libor = false;


		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lExtendID = Long.valueOf(strTmp).longValue();
		} else {
			out.println("出错：没传展期标识.");
			strcontrol = "";
		}

		OBExtendQuery e_ejb = new OBExtendQuery();
		ExtendApplyInfo e_info = e_ejb.findExtendByID(lExtendID);
		
		lLoanTypeID = e_info.lLoanTypeID;
		
		
		OBContractQuery c_ejb = new OBContractQuery();
		
        ContractInfo c_info = c_ejb.findByID(e_info.m_lContractID);

		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) {
			lAType = Long.valueOf(strTmp).longValue();
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  sAction = strTmp;
		}

		RepayPlanInfo r_info = new RepayPlanInfo();  //  页面显示用
		
		if (strcontrol.equals("view")) 
		{
			/**************  haoning 控制是否显示菜单  *************************************/
			long lisSM = Constant.YesOrNo.YES;
			strTmp = (String)request.getAttribute("isSM");
			if ( strTmp != null && strTmp.length() > 0 )
			{
				lisSM = Long.valueOf(strTmp).longValue();
				if(lisSM == Constant.YesOrNo.NO){}
				else
				{
					lisSM = Constant.YesOrNo.YES;
				}
			}
			OBHtml.showOBHomeHead(out,sessionMng,"内容显示",Constant.YesOrNo.NO);
%>


<!--*************************************************************************************-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<!--*************************************************************************************-->
<p>

<table width="730" border="0" class="top" height="113">

  <tr class="tableHeader"> 
    <td class="FormTitle" height="13"><b>  以下<%= sEType %>申请已完成！</b></td>
  </tr>
  <tr> 
    <td height="51" valign="bottom"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td width="20%">合同编号：</td>
          <td width="25%"><u><%= e_info.m_strContractCode %></u></td>
          <td width="55%">&nbsp;</td>
        </tr>
		<tr> 
          <td width="20%"><%=sEType%>申请编号：</td>
          <td width="25%"><u><%= e_info.m_lSerialNo>9?(""+e_info.m_lSerialNo):("0"+e_info.m_lSerialNo) %></u></td>
          <td width="55%" align="right">
 <!-- ------------------------------------------------------------------------------------------------------显示1  -->
		  <% if (lAType == 1) { %>
            <input type="button" name="Submit11" value="新增<%= sEType %>" class="button" onClick="location.href='S611.jsp?control=view' ">
            <INPUT class=button name=btnExportContract onclick="window.open('S85-p.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.m_lContractID%>');" type="button" value="导出并打印">
			<input type="button" name="Submit12" value="审核<%= sEType %>" class="button" onClick="location.href='S615.jsp?control=save&lExtendID=<%= lExtendID %>' ">
		<% } %>
		<% if (lAType == 3) { %>
            <input type="button" name="Submit11" value="新增<%= sEType %>" class="button" onClick="location.href='S611.jsp?control=view' ">
            <INPUT class=button name=btnExportContract onclick="window.open('S85-p.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.m_lContractID%>');" type="button" value="导出并打印">
			<input type="button" name="Submit12" value="审核<%= sEType %>" class="button" onClick="location.href='S615.jsp?control=save&lExtendID=<%= lExtendID %>' ">
		<input type="button" name="Submit13" value="返 回" class="button" onClick="location.href='S620.jsp?control=view' ">
		<% } %>
		<% if (lAType == 4) { %>
            <INPUT class=button name=btnExportContract onclick="window.open('q077-p.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.m_lContractID%>');" type="button" value="导出并打印">
		<%
		if(lisSM == Constant.YesOrNo.NO)
		{
%>
			<input type="button" name="Submit13" value=" 关 闭 " class="button" onClick="window.close()">
<%
		}
		else
		{
	%>
			<input type="button" name="Submit13" value="返 回" class="button" 	onClick="window.location.href='<%=(((String)request.getAttribute("sRetunrn") == null)?"S621":"S620")%>.jsp?control=view'">
	<%
		}
	%>
		<% } %>
		&nbsp;<br>
 <!-- ------------------------------------------------------------------------------------------------------显示2  -->
        </td>
        </tr>
		<% if (lAType == 4 ) { %>
        <tr> 
          <td width="20%"><%=sEType%>合同编号：</td>
          <td width="25%">
	<% if (e_info.m_lInputUserID == sessionMng.m_lUserID && e_info.m_lContractID >0 && e_info.m_lExContractID > 0) { %>
<a href="S85.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.m_lContractID%>&control=view&ContractType=<%=e_info.lLoanTypeID%>">
<% } %><%= (e_info.m_sExCode == null ? "" : e_info.m_sExCode)  %></a>
		</td>
          <td width="55%">&nbsp;</td>
        </tr>
		<% } %>
      </table>
      <table width="97%" height="401" border="1" align="center" bordercolor="999999">
        <tr bordercolor="#D7DFE5"> 
          <td width="16%" height="25"> <u><%= sEType %>申请资料</u></td>
          <td height="20" colspan="3">&nbsp;</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
<%

     Collection c2 = e_info.cExtendList;
	 Iterator iter2 = c2.iterator();
	 while (iter2.hasNext()) {
		 r_info = (RepayPlanInfo)iter2.next();
%>
<!-- -------------------------------------------------------------------------------------- Loop -->
        <tr bordercolor="#D7DFE5"> 
          <td width="16%" height="20">计划余额：</td>
          <td height="20" colspan="3"> 
		  <input name="txtdBalance" type="text" class="tar" size="20" value="<%= DataFormat.formatListAmount(r_info.dPlanBalance) %>" disabled>
          </td>
          <td width="16%" height="20"> <%= sEType %>金额： </td>
          <td width="32%" height="20"> 
		  <input name="txtdBalance" type="text" class="tar" size="20" value="<%= DataFormat.formatListAmount(r_info.dAmount) %>" disabled>
          </td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td width="16%" height="20"><%= sEType %>日：</td>
          <td height="20" colspan="3"> 
            <input name="txtExtendStartDate" type="text" class="box" size="20" value="<%= DataFormat.formatDate(r_info.tsExtendStartDate) %>" disabled>
          </td>
          <td width="16%" height="20"><%= sEType %>到期日：</td>
          <td width="32%" height="20"> 
          <input name="txtExtendStartDate" type="text" class="box" size="20" value="<%= DataFormat.formatDate(r_info.tsExtendEndDate) %>" disabled>
          </td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td width="16%" height="20"><%= sEType %>期限：</td>
          <td height="20" colspan="3"> 
            <input name="txtNum" type="text" class="box" size="10" value="<%= r_info.lExtendPeriod %>" disabled>
            月</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="6"> 
            <hr>
          </td>
        </tr>
		<!-- ------------------------------------------------------------------------------------------ Loop -->
		<% } %>

	<tr bordercolor="#D7DFE5"> 
	  <td height="20" colspan="6"> 
		<table>
        <tr bordercolor="#D7DFE5"> 
            <td height="20"><%= sEType %>基准利率：</td>
          	  <td nowrap><input type="text" name="textfield324" size="8" class="box" value="<%=(e_info.m_dBasicInterestRate)%>" disabled>% 
              <select class='box' name="select1" disabled>
                <option>　</option>
                <option value=1 <%if(c_info.getAdjustRate()>=0) out.println("selected");%>>+</option>
                <option value=2 <%if(c_info.getAdjustRate()<0) out.println("selected");%>>-</option>
              </select>
              浮动比例 
              <input type="text" name="textfield3222" size="5" class="box" value="<%=c_info.getAdjustRate()>=0?c_info.getAdjustRate():-c_info.getAdjustRate()%>" disabled>
              % </td>
            <td width="23%" nowrap align="right"><%= sEType %>执行利率 ： </td>
            <td width="20%"> 
              <input type="text" name="ExtendValue" size="8" class="box" value="<%out.println(e_info.m_dInterestRate);%>"   readonly>
              % </td>
        </tr></table>
	  </td>
	</tr>


        <tr bordercolor="#D7DFE5"> 
          <td height="21" colspan="4"><%= sEType %>原因：</td>
          <td height="21">&nbsp;</td>
          <td height="21">&nbsp;</td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtReason" cols="90" rows="2" class="box" disabled><%= e_info.m_strExtendReason %></textarea>
          </td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="4">归还延期还款本息资金来源：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtSource" cols="90" rows="2" class="box" disabled><%= e_info.m_strReturnPostPend %></textarea>
          </td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="4"> 其他事项：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtOther" cols="90" rows="2" class="box" disabled><%= e_info.m_strOtherContent %></textarea>
          </td>
        </tr>
        <tr bordercolor="#D7DFE5"> 
          <td height="20" colspan="6"> 
<hr>

<!-- ------------------------------------------------------------------------------------------------------------Examine1 -->
<% 
if (lAType == 4) 
{ 
%>
        <table width="100%" border="0">
                <tr> 
                  <td height="127" width="103">历史审核意见:</td>
                  <td colspan="4"> <br>
                    <table width="550" border="0" align="left" height="50%" class="ItemList">
                      <tr class="tableHeader"> 
                        <td class="ItemTitle" width="8%" height="20"> 
                          <div align="center">序列号</div>
                        </td>
                        <td class="ItemTitle" width="21%" height="20"> 
                          <div align="center">意见内容</div>
                        </td>
                        <td class="ItemTitle" width="21%" height="20"> 
                          <div align="center">审核人</div>
                        </td>
                        <td class="ItemTitle" width="20%" height="20"> 
                          <div align="center">审核决定</div>
                        </td>
                        <td class="ItemTitle" width="30%" height="20"> 
                          <div align="center">日期和时间</div>
                        </td>
					 </tr>
<%
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
		
		Collection c = null;
		c = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lExtendID,1);
		if (c != null) 
		{
			Iterator iter = c.iterator();
			while (iter.hasNext()) 
			{
     			tracinginfo = (ApprovalTracingInfo)iter.next();
			
%>
			          <tr> 
                        <td class="ItemBody" width="8%" height="24">&nbsp;<%= tracinginfo.getSerialID()%></td>
                        <td class="ItemBody" width="21%" height="24">&nbsp;<%= (tracinginfo.getOpinion()==null?"":tracinginfo.getOpinion()) %></td>
                        <td class="ItemBody" width="21%" height="24">&nbsp;<%= tracinginfo.getUserName() %></td>
                        <td class="ItemBody" width="20%" height="24">&nbsp;<%= Constant.ApprovalDecision.getName(tracinginfo.getResultID())%></td>
                        <td class="ItemBody" width="30%" height="24">&nbsp;<%= DataFormat.getDateTimeString(tracinginfo.getApprovalDate()) %></td>
                      </tr>
<%
 			}
		} 
		else 
		{  
%>
                    <tr> 
                        <td class="ItemBody" width="8%" height="24">&nbsp;</td>
                        <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                        <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                        <td class="ItemBody" width="20%" height="24">&nbsp;</td>
                        <td class="ItemBody" width="30%" height="24">&nbsp;</td>
                      </tr>
<% 
		} 
%>
                    <tr> 
                        <td colspan="5"> 
                          <div align="center"> 
                            <table width="550" border="0" cellspacing="3" cellpadding="0" class="SearchBar" height="18">
                              <tr> 
                                <td width="545" height="100%"> 
                                  <div align="right"> 
                                    <p><b> &nbsp;
                                       </b> </p>
                                  </div>
                                </td>
                              </tr> 
                            </table>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
        		<tr>
                    <td><% if (c_info.getStatusID()==LOANConstant.ContractStatus.SUBMIT){%>下一级审核人:<%}%></td>
                    <td colspan="4">
                    <%
                    	if (c_info.getStatusID()==LOANConstant.ContractStatus.SUBMIT){
                    		String nextCheckUserName=c_info.getCheckUserName();
                    %>
                    <input type="text" name="nextCheckUserName" class="box" size="10" value="<%=nextCheckUserName%>" disabled > 
                    <%		
                    	}
                    %> 
                    </td>
                  </tr>
                
</table>


<hr>
<% 
} 
%>
<!-- ------------------------------------------------------------------------------------------------------------Examine2 -->




            
          </td>
        </tr>
      </table>
    <tr>
    <td height="30" valign="top"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td width="13%">
		  <p align=right>录入人:<%=c_info.getInputUserName()%>&nbsp;&nbsp;&nbsp;录入日期:<%=DataFormat.getDateString(c_info.getInputDate())%> &nbsp;&nbsp;&nbsp;状态:<%=LOANConstant.ContractStatus.getName(c_info.getStatusID())%><br> </p>
		  </td>
        </tr>
      </table>
    </td>
  </tr>
</table>




<%		
	     OBHtml.showOBHomeEnd(out);
		}
	} 
	catch (IException e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng,e,"展期内容显示","", Constant.RecordStatus.VALID); 
		e.printStackTrace();
		out.flush();
		return; 
	}

%>
<%@ include file="/common/SignValidate.inc" %>