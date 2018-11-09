<%
/**
 * 页面名称 ：S613.jsp
 * 页面功能 : 贷款展期申请处理-新增
 * 作    者 ：方远明
 * 日    期 ：2003-10-23
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S614.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>		

<%
	try
	{
		
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

    	// 定义变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		long lLoanType = -1;
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

		long lInterestType = -1;
		long lLiborRateID = -1;
		String sLiborName = "";
		boolean libor = false;
		double dRateAdjust = 0;
		String sAdjust1 = "";
		String sAdjust2 = "";
		long lBankRateTypeID = -1;

		long nLength = 0;
		long lExtendListID = -1;
		long lPlanID = -1;

		long lStatusID = -1;

		String strLiborName = "";
		float fLiborAdjustRate = 0;
		long lLiborID = 0;
        
        
		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
		{
			strcontrol = (String)request.getAttribute("control");
		}
		
		if( (String)request.getAttribute("first") != null)
		{
			strFirst = (String)request.getAttribute("first");
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) 
		{
        	sAction = strTmp;
		}

		//OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		//OBExtendApply e_ejb = extendApplyHome.create();

		// 判断(展期:1转期:2－新增:1审核:2修改:3)--------------------begin (lEType-lAType)
		lInterestType = LOANConstant.InterestRateType.BANK;
		
		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) 
		{
			lAType = Long.valueOf(strTmp).longValue();
		}
		if (lAType == 1) 
		{
				strTmp = (String)request.getAttribute("lContractID");
				if ( strTmp != null && strTmp.length() > 0 ) 
				{
					lContractID = Long.valueOf(strTmp).longValue();
				} 
				else 
				{
					out.println("出错：没传合同标识.");
					strcontrol = "";
				}
		}
		if (lAType == 2 || lAType == 3) 
		{
				strTmp = (String)request.getAttribute("lExtendID");
				if ( strTmp != null && strTmp.length() > 0 ) 
				{
					lExtendID = Long.valueOf(strTmp).longValue();
				} 
				else 
				{
					out.println("出错：没传展期标识.");
					strcontrol = "";
				}
		}

		// ---------------------------------------------------------------------------------判断.end

     	// 初始化EJB


		//ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
		//Contract c_ejb = contractHome.create();
		

//      动作处理 -------------------------------------------------------------------------action.begin


//      动作处理 -------------------------------------------------------------------------action.end

		if ( strcontrol.equals("save") )
		{
			OBHtml.showOBHomeHead(out,sessionMng,"展期新增与修改",Constant.ShowMenu.YES); %>


<!--*************************************************************************************-->
<SCRIPT language="javascript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script> 
<safety:resources />
<!--*************************************************************************************-->
<p>
<!-- -------------------------------------------------------------------------------------------------------- 新增.begin -->
<%
			//if (lAType == 1) // 新增
			//{
				String[] slID = request.getParameterValues("checkbox");
				if (slID != null || slID.length > 0) 
				{
					nLength = slID.length;
				}

				double dbalance = 0;  // 用来判断是否为计划余额
				strTmp = (String)request.getAttribute("balance"); 
				if (strTmp != null && strTmp.length() >0) 
				{
					dbalance = Double.valueOf(strTmp).doubleValue();
				}

				// 2
				strTmp = ((String)request.getAttribute("extype")).trim();
				if (strTmp != null && strTmp.length() > 0) 
				{
					lEType = Long.valueOf(strTmp).longValue();
				}  
				else 
				{
					out.println("出错：没有指定是展期或转期");
					strcontrol = "";
				}
				
				//ContractInfo c_info = c_ejb.findByID(lContractID);
				ContractInfo c_info = new ContractInfo();
		       	if (request.getAttribute("c_info") != null)
		       	{
		           	c_info = (ContractInfo)request.getAttribute("c_info");
		       	}
				System.out.println("ininininininiiniini"+c_info);
				OBRepayPlanInfo r_info[] = null;
		       	if (request.getAttribute("r_info") != null)
		       	{
		           	r_info = (OBRepayPlanInfo[])request.getAttribute("r_info");
		       	}
				dRate = c_info.getBasicInterestRate();
				lLoanType = c_info.getLoanTypeID();
%>
<form name="form1"  action="../extendapply/e008-c.jsp?control=save&lContractID=<%= lContractID %>"  method="post">
<input type="hidden" name="txtAction" value="save">
<input type="hidden" name="attribtype" value="1">
<input type="hidden" name="extype" value="<%= lEType %>">
<input type="hidden" name="nlength">

<table width=740 border="0" class="top" height="113">


  <tr class="tableHeader"> 
    <td class="FormTitle" height="13"><b> <%= sEType %>新增</b></td>
  </tr>
  <tr> 
    <td height="51" valign="bottom"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td>&nbsp;</td>
		  <td width="26%">合同编号：</td>
          <td width="21%"><u><%= c_info.getContractCode() %></u></td>
		  <td width="13%">&nbsp;</td>
          <td width="40%" colspan=2 align="right">&nbsp; 
            
          </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>借款单位：</td>
          <td colspan=4> <input type="text" class="box" size=80 name="txt1" value="<%= c_info.getBorrowClientName() %>" disabled></td>        
        </tr>
		<tr> 
          <td>&nbsp;</td>
          <td>贷款日期：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= DataFormat.formatDate(c_info.getLoanStart()) %>" disabled></td>
          <td align=right> 到：</td>
          <td> <input type="text" name="txt1" class="box" value="<%= DataFormat.formatDate(c_info.getLoanEnd()) %>" disabled></td>
          <td >&nbsp; </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>期限：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= c_info.getIntervalNum() %>" disabled></td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>贷款金额：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= DataFormat.formatListAmount(c_info.getExamineAmount()) %>" disabled></td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
       <tr> 
          <td>&nbsp;</td>
          <td>执行利率：</td>
          <td><input type="text" name="txt1" class="box" value="<%=DataFormat.formatRate((float)c_info.getInterestRate())%> %" disabled></td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
		<tr> 
          <td>&nbsp;</td>
          <td>委托单位：</td>
          <td colspan=4>  <input type="text" size=80 name="txt1" class="box" value="<%= c_info.getClientName()==null?"":c_info.getClientName() %> " disabled></td>
        </tr>
      </table>
      <table width="97%" height="401" border="1" align="center" bordercolor="999999">
        <tr  bordercolor="#E8E8E8"> 
          <td width="16%" height="25"> <u><%= sEType %>申请资料</u></td>
          <td height="20" colspan="3">&nbsp;</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
<%
	            // String[] -> long[]
                long l = -1;
				long[] lID = new long[slID.length];
				for (int i=0; i<slID.length; i++) 
				{
					l = Long.valueOf(slID[i]).longValue();
					lID[i] = l;
				}

				for (int i=0; i<nLength; i++) 
				{
%>
<!-- -------------------------------------------------------------------------------------- Loop -->
		  <input type="hidden" name="checkbox" value="<%= lID[i] %>">
<%      
                	//System.out.println("jsp-----------------------lID[i]: " + lID[i]);
                	//RepayPlanHome repayPlanHome = (RepayPlanHome)EJBObject.getEJBHome("RepayPlanHome");
                	//RepayPlan r_ejb = repayPlanHome.create();
                	//RepayPlanInfo r_info = new RepayPlanInfo();  //  页面显示用
                	//r_info = r_ejb.findPlanByID(lID[i],lContractID,sessionMng.m_lOfficeID);
                	if(r_info[i]!=null)
                	{
%>
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20">计划余额：</td>
          <td height="20" colspan="3"> 
            <input name="txtdBalance<%= i %>" type="text" class="tar" size="20" value="<%if (r_info[i].getPlanBalance() == 0) { out.print("0.00");} else { out.print(DataFormat.formatListAmount(r_info[i].getPlanBalance()));} %>" readonly><!-- 用来判断是否为计划余额 -->
<% 
                		if (r_info[i].getPlanBalance() == 0) 
                		{
                			sAmount = DataFormat.formatListAmount(r_info[i].getAmount());
                		}
                		else
                		{
                			sAmount =  DataFormat.formatListAmount(r_info[i].getPlanBalance()); 
                		}
%>
          </td>
          <td width="16%" height="20"> <font color="#FF0000">*</font><%= sEType %>金额： </td>
          <td width="32%" height="20"> 
<!--*************************************************************************************-->
		 <script language="javascript">
        	createAmountCtrl("form1","dAmount<%=i %>","<%=sAmount%>","txtdtInputDate<%=i %>");
          </script>
<!--*************************************************************************************-->		
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20"><%= sEType %>日：</td>
          <td height="20" colspan="3"> 
            <input name="txtExtendStartDate<%= i %>" type="text" class="box" size="20" value="<%= DataFormat.formatDate(r_info[i].getPlanDate()) %>" readonly>
          </td>
          <td width="16%" height="20"><font color="#FF0000">*</font><%= sEType %>到期日：</td>
          <td width="32%" height="20"> 
          	<fs_c:calendar 
          	    name="txtdtInputDate<%=i %>"
	          	value="<%= (dtInputDate==null?"":DataFormat.formatDate(dtInputDate)) %>" 
	          	properties="nextfield ='txtNum<%=i %>'" 
	          	size="20"/>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20"><font color="#FF0000">*</font><%= sEType %>期限：</td>
          <td height="20" colspan="3"> 
<% 
                		if (i == lID.length-1) 
						{ 
%>
           <input name="txtNum<%=i %>" type="text" class="box" size="10" value="" onblur="calculateDate(txtExtendStartDate<%=i %>,form1.txtNum<%= i %>,'<%=i %>')" onfocus="nextfield='txtlRateValue'" >
<% 
                		} 
						else 
						{ 
%>
           <input name="txtNum<%=i %>" type="text" class="box" size="10" value="" onblur="calculateDate(txtExtendStartDate<%=i %>,form1.txtNum<%= i %>,'<%=i %>')" onfocus="nextfield='dAmount<%=i+1 %>'">
<% 
                		} 
%>
            月</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6">
            <hr>
          </td>
        </tr>
		<!-- ------------------------------------------------------------------------------------------ Loop -->
<%
                	}
                } 
%>


	<tr bordercolor="#E8E8E8"> 
	  <td height="20" colspan="6"> 
<!-- ------------------------------------------------------------------------------------------rate.begin -->
<table>
    <tr bordercolor="#E8E8E8"><td><font color="#FF0000">*</font></td>
<%
		String strMagnifierName = URLEncoder.encode(sEType+"基准利率");						//放大镜的名称
		String strFormName = "form1";									//主页面表单名称
		String strMainNames1[] = {"txtlRateValue"};					//放大镜回显栏位值列表
		String strMainFields1[] = { "mRate"};							//放大镜回显栏位对应的表格字段
		String strReturnNames1[] = {"txtlRate"};						//放大镜返回值列表(隐含值)
		String strReturnFields1[] = {"RateID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String strReturnInitValues3 = ""+DataFormat.formatRate(dRate);						//放大镜回显栏位对应的初始值		String strReturnValues3[] = {(""+lBorrowClientID)};								//放大镜返回值(隐含值)对应的初始值
		String strReturnValues1[] = {""+c_info.getBankInterestID()};								//放大镜返回值(隐含值)对应的初始值
		String strDisplayNames1[]= {URLEncoder.encode("利率编号"),URLEncoder.encode("利率名称"),URLEncoder.encode("利率(%)")};				//放大镜小窗口显示的栏位名称
		String strDisplayFields1[] = {"RateCode","RateName","RateValue"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty = " size = '8'";				//放大镜的对应控件栏位属性
		String strMatchValue3 = "RateCode";										//（放大镜要模糊匹配的字段，如传入null或""则匹配第一个显栏对应的数据库字段）
    	String strNextControls3 = "txtExtReason";						//设置下一个焦点
    	String strTitle3 = "基准利率";									//栏位标题
    	String strFirstTD3 = "";											//第一个TD的属性
    	String strSecondTD3 = "";										//第2个TD的属性
		
		//调用产生放大镜的方法
		LOANMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames1,strMainFields1,strReturnNames1,strReturnFields1,
		strReturnInitValues3,strReturnValues1,strDisplayNames1,strDisplayFields1,
		intIndex,strMainProperty,"getRateSQL()",strMatchValue3,strNextControls3,
    	strTitle3,strFirstTD3,strSecondTD3);
%>  		  
            <td nowrap>% 
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
              <input type="text" name="ExtendValue" size="8" class="box" value="<%=DataFormat.formatRate(dRate*(1+c_info.getAdjustRate()/100))%>"   readonly>
              % </td>
        </tr></table>
<!-- ------------------------------------------------------------------------------------------rate.end -->
	  </td>
	</tr>

        <tr bordercolor="#E8E8E8"> 
          <td height="21" colspan="4"><font color="#FF0000">*</font><%= sEType %>原因：</td>
          <td height="21">&nbsp;</td>
          <td height="21">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtReason" cols="90" rows="2" class="box"  onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onfocus="nextfield='txtExtSource';setExtendValue(<%=c_info.getAdjustRate()%>)"></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="4"><font color="#FF0000">*</font>归还延期还款本息资金来源：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtSource" cols="90" rows="2" class="box" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onfocus="nextfield='txtExtOther';"></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="4"> 其他事项：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtOther" cols="90" rows="2" class="box" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onfocus="nextfield='submitfunction';"></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <hr>
          </td>
        </tr>
      </table>
	 <tr> 
		<td height="30" valign="top"> 
		 <table width="100%" border="0" align="center" height="25">
         <tr> 
          <td>&nbsp;</td>
		  <td width="26%">&nbsp;</td>
          <td width="21%">&nbsp;</td>
		  <td width="13%">&nbsp;</td>
          <td width="40%" colspan=2 align="right"> 
            <input type="button" name="Submit11" value="保存<%= sEType %>申请" class="button" onClick="formSubmit1(form1,'确认保存<%= sEType %>申请?')">
            <input type="button" name="Submit12" value="返回" class="button" onClick="location.href='../extendapply/e004-c.jsp?control=view&lContractID=<%= lContractID %>' ">
          </td>
		 </tr>
      </table>
      </td>
    </tr>

    <tr>
    <td height="30" valign="top"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td width="13%">&nbsp;</td>
          <td width="79%" align="right">状态：</td>
          <td width="8%">撰写 </td>
       </tr></table></td></tr></table></form>
<% 
               } 
%>
<!-- -------------------------------------------------------------------------------------------------------- 新增.end -->

<script language="javascript">
 firstFocus(form1.Submit11);
 //setSubmitFunction("formSubmit1(form1,'确认保存<%= sEType %>申请?')");
 setFormName("form1");
</script>


<script language="JavaScript">
<!--
    function formSubmit1(form,msg)
	{  
	<% if (lStatusID == LOANConstant.ContractStatus.CHECK) { %>
		if(!confirm("<%= sEType %>申请已经审核完毕，确认修改?"))
		{
		     return(false);
		}
	<% } %>


		for (var i = 0 ; i < <%= nLength %>; i++)
		{
				if (!checkDate(eval("form.txtdtInputDate"+i),1,"日期"))
					return(false);
				if (!checkAmount(eval("form.dAmount"+i),1,"录入金额"))
					return(false);
				if (!CodeCompare(eval("form.txtExtendStartDate"+i),eval("form.txtdtInputDate"+i),"日期"))
					return(false);
				if (!InputValid(eval("form.txtNum"+i),1,"int",1,1,90000,"贷款期限(单位：月）")) 
					return(false);
				//if (!checkAmountMAX(eval("form.dAmount"+i),1,"录入金额 "))  // from 21-M
				//		return(false);
				if(eval("form.dAmount"+i+".value.length>form.txtdBalance"+i+".value.length"))
				{
					alert("展期金额不能大于计划余额！");
					eval("form.dAmount"+i+".focus()");
					return(false);
				}
				else if(eval("form.dAmount"+i+".value.length==form.txtdBalance"+i+".value.length")&&eval("form.dAmount"+i+".value>form.txtdBalance"+i+".value"))
				{
					alert("展期金额不能大于计划余额！");
					eval("form.dAmount"+i+".focus()");
					return(false);
				}

		}
<%
	if( lLoanType == LOANConstant.LoanType.WT )
	{
%>
        //基准利率
        if (!checkMagnifier("form1","txtlRateValue","txtlRateValue","基准利率"))
        {
             return false;
        }
<%
	}
	else
	{
%>
        //基准利率
        if (!checkMagnifier("form1","txtlRate","txtlRateValue","基准利率"))
        {
             return false;
        }
<%
	}
%>
		if (!InputValid(form.txtExtReason,1,"string",1,0,99,"申请原因")) 
			return(false);
		if (!InputValid(form.txtExtSource,1,"string",1,0,99,"资金来源")) 
			return(false);
		if (!InputValid(form.txtExtOther,0,"string",1,0,99,"其它事项")) 
			return(false);

			if (confirm(msg))
			{
				<% if (lAType == 1) { %>
				form.txtAction.value = "save";
				<% } %>
				<% if (lAType == 2 || lAType == 3) { %>
				form.txtAction.value = "modi";
				<% } %>
				form.nlength.value = "<%= nLength %>";
				showSending();//显示正在执行
	            form.submit();
			}
	}

	// 取消展期
function formCancel(form,msg)
{
	if(confirm(msg))
	{
		form.txtAction.value = "cancel";
        showSending();//显示正在执行
		form.submit();
	}
}

//编号比较
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str+"不能由大至小。");
			form1.Submit1.focus();
			return (false);
		}
	}
	return true;
}
 function calculateDate(d_input1,d_input2,s)
 {
	 var i=0;

	 if (d_input2.value.length>0 && d_input1.value.length>0)
	 {
		if (!InputValid(eval("form1.txtNum"+s),1,"int",1,1,90000,"期限(单位：月）")) 
			return(false);
		 i = parseInt(d_input2.value);

   		 eval("form1.txtdtInputDate"+s).value = addDate(d_input1.value,i);  // shout
	 }
 }

 // 没有9.31错误的方法 by michele yu
function addDate(strInputDate,lMonth)
{
	var s = new String();
//	var strInputDate = "2002-06-01";
//	var lMonth = 12;
	var date1 = DateProcess1(strInputDate);
//	alert(date1.toLocaleString());
	var date2 = new Date(date1.getFullYear(), date1.getMonth() + lMonth-1,date1.getDate());
//	alert(date2.toLocaleString());

	s = date2.toLocaleString();
	s = DateProcess2(s);
	return s;
//	alert(s);
}

// "2002-08-02" -> new Date
function DateProcess1(d1)
{
	var s1 = new String();
	var s2 = new String();
	var s3 = new String();

	var p1 = d1.indexOf("-");
	s1 = d1.substring(0,p1);

    d1 = d1.substring(p1+1,d1.length);
	var p2 = d1.indexOf("-");
	s2 = d1.substring(0,p2);

	s3 = d1.substring(p2+1,d1.length);

	return new Date(s1,s2,s3);
}

// new Date -> "2002-08-02"
function DateProcess2(d1)
{
	var s1 = new String();
	var s2 = new String();
	var s3 = new String();

	var p1 = d1.indexOf("年");
	s1 = d1.substring(0,p1);

	var p2 = d1.indexOf("月");
	s2 = d1.substring(p1+1,p2);
	if (s2.length == 1)
		s2 = '0' + s2;

	var p3 = d1.indexOf("日");
	s3 = d1.substring(p2+1,p3);
	if (s3.length == 1)
		s3 = '0' + s3;

    s1 = s1 + "-" +s2 + "-" + s3;
    
    if ( s1.length == 2 )
    {
	p1 = d1.indexOf("/");
	s1 = d1.substring(0,p1);

	p2 = d1.indexOf("/",p1+1);
	s2 = d1.substring(p1+1,p2);
	if (s2.length == 1)
		s2 = '0' + s2;

	s3 = d1.substring(p2+1,p2+5);
	if (s3.length == 1)
		s3 = '0' + s3;
    s1 = s3 + "-" +s1 + "-" + s2;
    }
    
	return (s1);
}

// error addDate
//function addDate(strInputDate,lMonth)
//{
//		var temp,s;
//		temp=new String(strInputDate);
//		s=new String("");
//		for(var i=0;i<=temp.length-1;i++)
//		{
//				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
//				{
//					    s=s+"/";
//				}else
//				{
//					    if(isNaN(Number(temp.charAt(i))))
//					    {
//						      alert("请输入正确的开始日期");
//						      document.theform.txtDtStart.focus();
//						      return false;
//					     }else
//						 {
//					  		 s=s+temp.charAt(i);
//						  }
//		         } 
//		}//for(var i=0;i<=temp.length-1;i++)
//		var dtDate;
//		dtDate = new Date(s);
//		var strDate;
//		var yy,mm,temp;
// 
//		 var dtDay = dtDate.getDate();
//		 temp = parseInt(lMonth) + dtDate.getMonth()+1 ;
//		 var dtMonth = temp % 12;
//		 var dtYear = dtDate.getYear() + parseInt(temp / 12);
// 
//		 if(parseInt(dtMonth)==0)
//		 {
//			  dtMonth='12';
//			  dtYear=parseInt(dtYear)-1;
//		 }
//		 strDate=dtYear + "-" +dtMonth + "-" + dtDay;
//		 return strDate;
//}


 function clearRate(obj)
{
	if (eval("document.form1." + obj + ".value") == 0)
	{
		eval("document.form1." + obj + ".value = ''");
	}
}
 function setExtendValue(floatRateValue)
{
	eval("document.form1.ExtendValue.value = document.form1.txtlRateValue.value*(1 + floatRateValue*1/100)");
	eval("document.form1.ExtendValue.value = Math.round(document.form1.ExtendValue.value*1000000)/1000000");
}

	function getRateSQL()
	{
	 var strSQL  = "  select b.id RateID,a.SINTERESTRATENO RateCode,b.mRate, a.sinterestratename as RateName,";
	    strSQL  += " b.mRate RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate ";
	    strSQL  += " from LOAN_INTERESTRATETYPEINFO a,LOAN_INTERESTRATE  b ";
	    strSQL  += " where a.id = b.nBankInterestTypeID and b.dtValiDate < sysdate ";
	    strSQL+=" and (a.id,b.dtvalidate) in ";
	    strSQL+=" ( select b.id,max(a.dtvalidate) from loan_INTERESTRATETYPEINFO b,loan_InterestRate a ";
		strSQL+=" WHERE ";
		strSQL+=" b.ID=a.NBANKINTERESTTYPEID ";
		strSQL+=" and  to_char(a.DTVALIDATE,'yyyymmdd')<to_char(sysdate,'yyyymmdd') ";
		strSQL+=" group by b.id ) ";
	  strSQL = strSQL + " order by a.SINTERESTRATENO ";
	  return strSQL;
	}
</script>

<%		
	OBHtml.showOBHomeEnd(out);
		//}
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"展期查找(计划)",1);
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>