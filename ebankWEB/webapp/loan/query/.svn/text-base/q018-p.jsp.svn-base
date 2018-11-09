<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,	
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.ebank.obcontent.bizlogic.*,
	com.iss.itreasury.ebank.obcontent.dao.*,	
	com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
	/* 用户登录检测与权限校验及文件头显示 */
     try 
    {
		//判断是否登录
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
		
		String[] sOpinion = new String[8];
		sOpinion=(String[])request.getAttribute("sOpinion");
		String[] sCheckUser = new String[8];
		sCheckUser=(String[])request.getAttribute("sCheckUser");
		String[] sCheckDate = new String[8];
		//for (int i=0;i<5;i++)
		//{
		//	sCheckDate[i] = DataFormat.getDateString(Env.getSystemDate());
		//}
		sCheckDate=(String[])request.getAttribute("sCheckDate");
		
		
		LoanApplyInfo laInfo=(LoanApplyInfo)request.getAttribute("LoanApplyInfo");
		ClientInfo	clientInfo=(ClientInfo)request.getAttribute("ClientInfo");
		ClientInfo  wtClientInfo=(ClientInfo)request.getAttribute("wtClientInfo");
		Vector  planVector=(Vector)request.getAttribute("planInfo");
		Vector  assVector=(Vector)laInfo.getAssureInfo();
		AssureInfo assInfo=null;
		if ( (assVector!=null)&&(assVector.size()>0) )	assInfo=(AssureInfo)assVector.get(0);
		double paySumAmount=laInfo.getPlanPayAmount();
		double repaySumAmount=laInfo.getPlanRepayAmount();
		
		String clientName=laInfo.getBorrowClientName();
		double loanAmount=laInfo.getLoanAmount();
		long   intervalNum=laInfo.getIntervalNum();
		double interestRate=laInfo.getInterestRate()*(1+laInfo.getAdjustRate()/100);
		String loanPurpose=laInfo.getLoanPurpose();
		String loanType="";							//贷款方式
		Timestamp startDate=laInfo.getStartDate();
		Timestamp endDate=laInfo.getEndDate();
		long isCredit=laInfo.getIsCredit();
	    long isAssure=laInfo.getIsAssure();
	    long isPledge=laInfo.getIsPledge();
	    long isImpawn=laInfo.getIsImpawn();
		String sType = LOANConstant.LoanType.getName(laInfo.getTypeID());
		double ChargeRate=laInfo.getChargeRate();
		String WTclientName=wtClientInfo.getName();
		String applyCode = laInfo.getApplyCode();


		String zipCode="";
		if(wtClientInfo.getZipCode()!=null){
			zipCode="("+DataFormat.formatString(wtClientInfo.getZipCode())+")";
		}
		
		if (intervalNum<0) intervalNum=0;
		if (interestRate<0) interestRate=0;
		if (loanPurpose==null) loanPurpose="";
		
		Vector payPlan=new Vector();
		Vector repayPlan=new Vector();
		
		if ( (planVector!=null)&&(planVector.size()>0) )
		{
			int count=planVector.size();
			for ( int i=0;i<count;i++ )
			{
				LoanPlanDetailInfo tmpPlan=(LoanPlanDetailInfo)planVector.get(i);
				if ( tmpPlan.getPayTypeID()==LOANConstant.PlanType.PAY )
					payPlan.add(tmpPlan);
				if ( tmpPlan.getPayTypeID()==LOANConstant.PlanType.REPAY )
					repayPlan.add(tmpPlan);	 
			}
		}
		
		String tmp="";
%>	
<html>
<head>
<title>委托贷款申请审批书</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
.table1 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.lh12 {
	line-height: 25px;
}
-->
</style>
</head>

<body>
<div align="center">
<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000">
<tr>
<td valign="top" align="center">
<table width="100%" border="0">
<tr>
<td colspan="2" align="center" height="22" valign="top"><font size="4"><b><%=Env.getClientName()%></b></font></td>
</tr>
<tr>
<td colspan="2" align="center"><font size="4"><b><%=LOANConstant.LoanType.getName(laInfo.getTypeID())%>申请书</b></font></td>
</tr>
<tr>
<td colspan="1" height="20">&nbsp;申请书编号:&nbsp;<%=DataFormat.formatString(laInfo.getApplyCode())%></td>
<td align=right>年　月　日</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="table1" height="600">
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">委托单位</td>
<td  class="td-bottom" colspan="3" width="32%" ><%=wtClientInfo.getName()%>&nbsp;</td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">委托单位电话</td>
<td  class="td-rightbottom" width="32%"><%=DataFormat.formatString(wtClientInfo.getPhone())%>&nbsp;</td>
<td  class="td-rightbottom" width="18%" align="center">委托单位地址</td>
<td class="td-bottom" width="32%"><%=zipCode+DataFormat.formatString(wtClientInfo.getAddress())%>&nbsp;</td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20" width="25%">委托单位开户行</td>
<td  class="td-rightbottom" width="32%"><%=wtClientInfo.getAccount()==null?" &nbsp;":Env.getClientName()%></td>
<td  class="td-rightbottom" width="18%" align="center">委托单位账号</td>
<td class="td-bottom" width="32%"><%=wtClientInfo.getAccount()==null?" &nbsp;":wtClientInfo.getAccount()%></td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">借款单位</td>
<td  class="td-rightbottom" width="32%"><%=clientInfo.getName()%></td>
<td   class="td-rightbottom" width="18%" align="center">营业执照号码</td>
<td class="td-bottom"  width="32%"><%=clientInfo.getLicenceCode()==null?" &nbsp;":clientInfo.getLicenceCode()%></td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">企业性质</td>
<td  class="td-rightbottom" width="32%"><%=clientInfo.getCorpNatureName()==null?"&nbsp;":clientInfo.getCorpNatureName()%></td>
<td  class="td-rightbottom" width="18%" align="center">电话</td>
<td class="td-bottom" width="32%"><%=clientInfo.getPhone()==null?" &nbsp;":clientInfo.getPhone()%></td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">邮　　编</td>
<td  class="td-rightbottom" width="32%"><%=DataFormat.formatString(clientInfo.getZipCode())%>&nbsp;</td>
<td  class="td-rightbottom" width="18%" align="center">地址</td>
<td class="td-bottom" width="32%"><%=DataFormat.formatString(clientInfo.getAddress())%>&nbsp;</td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">开户银行</td>
<td  class="td-rightbottom" width="32%">
<%
	if(clientInfo.getAccount()!=null)
	{
		out.print(Env.getClientName());
	}
	else if(clientInfo.getBankAccount1()!=null)
	{
		out.print(clientInfo.getBank1());
	}
	else if(clientInfo.getBankAccount2()!=null)
	{
		out.print(clientInfo.getBank2());
	}
	else if(clientInfo.getBankAccount3()!=null)
	{
		out.print(clientInfo.getBank3());
	}
%> &nbsp;
</td>
<td  class="td-rightbottom" width="18%" align="center">上级主管单位</td>
<td class="td-bottom" width="32%"><%=clientInfo.getParentCorpName()==null?" &nbsp;":clientInfo.getParentCorpName()%></td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">账　　号</td>
<td  class="td-rightbottom" width="32%">
<%
	if(clientInfo.getAccount()!=null)
	{
		out.print(clientInfo.getAccount());
	}
	else if(clientInfo.getBankAccount1()!=null)
	{
		out.print(clientInfo.getBankAccount1());
	}
	else if(clientInfo.getBankAccount2()!=null)
	{
		out.print(clientInfo.getBankAccount2());
	}
	else if(clientInfo.getBankAccount3()!=null)
	{
		out.print(clientInfo.getBankAccount3());
	}
%> &nbsp;
</td>
<td  class="td-rightbottom" width="18%" align="center">担保方式</td>
<td class="td-bottom" width="32%">
<%
String sTempAssureMode = "";
if (laInfo.getIsCredit()>0)
{
	sTempAssureMode += "信用、";
} 
if (laInfo.getIsAssure()>0)
{
	sTempAssureMode += "保证、";
} 
if (laInfo.getIsPledge()>0)
{
	sTempAssureMode += "抵押、";
} 
if (laInfo.getIsImpawn()>0)
{
	sTempAssureMode += "质押、";
} 

if (!sTempAssureMode.equals(""))
{
	sTempAssureMode = sTempAssureMode.substring(0,sTempAssureMode.length()-1);
}
else
{
	sTempAssureMode="&nbsp;";
}
out.println(sTempAssureMode);
%>
</td>
</tr>
<tr>
	<td  class="td-rightbottom" colspan="2" align="center" height="20">贷款卡号</td>
	<td  class="td-rightbottom" width="32%">
		<%=DataFormat.formatString(clientInfo.getLoanCardNo())%>&nbsp;
	</td>
	<td  class="td-rightbottom" width="18%" align="center">贷款卡密码</td>
	<td class="td-bottom" width="32%">
		<%=DataFormat.formatString(clientInfo.getLoanCardPwd())%>&nbsp;
	</td>
</tr>
<tr>
	<td  class="td-rightbottom" colspan="2" align="center" height="20">法人代表</td>
	<td class="td-rightbottom" width="32%">
		<%=DataFormat.formatString(clientInfo.getLegalPerson())%>&nbsp;
	</td>
	<td  class="td-rightbottom" width="18%" align="center">法人代码证号</td>
	<td  class="td-bottom" width="32%">
		<%=DataFormat.formatString(clientInfo.getLegalPersonCode())%>&nbsp;
	</td>
</tr>
<tr>
	<td  class="td-rightbottom" colspan="2" align="center" height="20">财务主管</td>
	<td  class="td-rightbottom" width="32%"> &nbsp;</td>
	<td  class="td-rightbottom" width="18%" align="center">经营范围</td>
	<td class="td-bottom" width="32%">
		<%=DataFormat.formatString(clientInfo.getDealScope())%>&nbsp;
	</td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="25">申请金额</td>
<td  class="td-rightbottom" width="32%">￥<%=DataFormat.formatDisabledAmount(laInfo.getLoanAmount())%></td>
<td  class="td-rightbottom" width="18%" align="center">有担保贷款总额</td>
<td class="td-bottom" width="32%">￥
<%
	double dTempAssure = 0;
	String sTempClient = "";
	if (laInfo.getAssureInfo() != null)
	{
		Collection cAssure = laInfo.getAssureInfo();
		Iterator itAssure = cAssure.iterator();
		
		while (itAssure.hasNext())
		{
			AssureInfo asInfo = new AssureInfo();
			asInfo = (AssureInfo)itAssure.next();
			dTempAssure = dTempAssure + asInfo.getAmount();
			sTempClient = sTempClient + asInfo.getClientName() + "、";
		}
		if (!sTempClient.equals(""))
		{
			sTempClient = sTempClient.substring(0,sTempClient.length()-1);
		}
	}
	out.println(DataFormat.formatNumber(dTempAssure,2));
%>
</td>
</tr>
<tr>
<td  class="td-rightbottom" colspan="2" align="center" height="20">申请期限</td>
<td  class="td-rightbottom" width="32%"><%=laInfo.getIntervalNum()%>个月</td>
<td  class="td-rightbottom" width="18%" align="center">担保单位名称</td>
<td class="td-bottom" width="32%"><%=sTempClient.equals("")?"&nbsp;":sTempClient%></td>
</tr>
<tr>
<td  class="td-rightbottom" width="5%" align="center">
借<br>
款<br>
原<br>
因</td>
<td class="td-bottom" colspan="4"><%=laInfo.getLoanReason()==null?" &nbsp;":laInfo.getLoanReason()%></td>
</tr>
<tr>
<td  class="td-rightbottom" width="5%" align="center">借<br>
款<br>
用<br>
途</td>
<td class="td-bottom" colspan="4"><%=laInfo.getLoanPurpose()==null?" &nbsp;":laInfo.getLoanPurpose()%></td>
</tr>
<tr>
<td class="td-rightbottom"width="5%" align="center">还<br>
款<br>
来<br>
源<br>
及<br>
措<br>
施</td>
<td  class="td-rightbottom" colspan="4"><%=laInfo.getOther()==null?" &nbsp;":laInfo.getOther()%></td>
</tr>
<tr>
<td class="td-right"width="5%" align="center">借<br>
款<br>
单<br>
位<br>
情<br>
况<br>
简<br>
介</td>
<td  class="td-right" colspan="4"><%=laInfo.getClientInfo()==null?" &nbsp;":laInfo.getClientInfo()%></td>
</tr>
</table>
</td>
</tr>
</table>
<br clear=all style='page-break-before:always'>
<div align=center>
<p align="center"><strong><font size="+2"><%=Env.getClientName()%><br>委托贷款审批表</font></strong></p>
 <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
  <tr> 
    <td>
	<table width="100%"   cellpadding="0" cellspacing="0">
        <tr> 
          <td colspan="2"><br>委托单位：<%=WTclientName%></td>
          <td colspan="2"><br>&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="2"><br>申请借款单位：<%=clientName%></td>
          <td colspan="2"><br>申请书编号：<%=DataFormat.formatString(applyCode)%></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">贷款金额：￥<%=DataFormat.formatNumber(loanAmount,2)%></td>
          <td colspan="1">贷款期限：<%=DataFormat.formatString(intervalNum+"&nbsp;个月")%></td>
          <td colspan="2">贷款利率(年息)：<%=interestRate <=0?"0": DataFormat.formatRate(DataFormat.formatRate(interestRate))+"%"%></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">贷款方式：<%=DataFormat.formatString(loanType)%></td>
          <td colspan="2">贷款种类：<%=DataFormat.formatString(sType)%></td>
          <td colspan="1">手续费率：<%=ChargeRate <=0?"0": DataFormat.formatRate(DataFormat.formatRate(ChargeRate))+"%"%></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">贷款用途：<%=DataFormat.formatString(loanPurpose)%></td>
          <td colspan="1">&nbsp;</td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">担保贷款总额：￥<%=DataFormat.formatNumber(dTempAssure,2)%></td>
          <td colspan="2">信用贷款总额：￥<%=DataFormat.formatNumber((loanAmount-dTempAssure)<=0?0:(loanAmount-dTempAssure),2)%></td>
          <td colspan="1"></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
    <tr> 
      <td height="114" colspan="7"><div align="center"> 
          <table width="100%">
            <tr> 
              <td colspan="2"><br>
&nbsp;&nbsp;&nbsp;&nbsp; 以上委托资金请贷款人按本申请约定的方式向借款人发放。 
                <p><br>
                  <br>
                  <br>
                </p>
                <p>&nbsp;</p></td>
            </tr>
            <tr> 
              <td width="65%" align="right">&nbsp;</td>
              <td width="35%" align="right"><div align="left">委托人（公章）<br>
                  法定代表人（签字）<br>
                  &nbsp;&nbsp;&nbsp;&nbsp; 年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</div></td>
            </tr>
          </table>
        </div></td>
    </tr>
  <tr> 
    <td><table width="100%" height=200>
        <tr> 
          <td colspan="2"><p><br>
              调查岗意见：<br><%=sOpinion[0]%><br>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">调查岗签字：<%=sCheckUser[0]%><br>
			<%=sCheckDate[0].substring(0,4)%>年 <%=sCheckDate[0].substring(5,7)%>月 <%=sCheckDate[0].substring(8,10)%>日</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=200>
        <tr> 
          <td colspan="2"><p><br>
              审查岗意见：<br><%=sOpinion[1]%><br>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">审查岗签字：<%=sCheckUser[1]%><br>
			<%=sCheckDate[1].substring(0,4)%>年 <%=sCheckDate[1].substring(5,7)%>月 <%=sCheckDate[1].substring(8,10)%>日</td>
        </tr>
      </table></td>
  </tr>
</table>
</div>
<br clear=all style='page-break-before:always'>
 <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              部门经理意见：<br><%=sOpinion[2]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">部门经理签字：<%=sCheckUser[2]%><br>
			<%=sCheckDate[2].substring(0,4)%>年 <%=sCheckDate[2].substring(5,7)%>月 <%=sCheckDate[2].substring(8,10)%>日</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              本公司资金头寸是否可以安排此笔贷款：<br><%=sOpinion[3]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">计划部经理签字：<%=sCheckUser[3]%><br>
			<%=sCheckDate[3].substring(0,4)%>年 <%=sCheckDate[3].substring(5,7)%>月 <%=sCheckDate[3].substring(8,10)%>日</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              主管总/副经理意见：<br><%=sOpinion[4]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">主管总/副总经理签字：<%=sCheckUser[4]%><br>
			<%=sCheckDate[4].substring(0,4)%>年 <%=sCheckDate[4].substring(5,7)%>月 <%=sCheckDate[4].substring(8,10)%>日</td>
        </tr>
      </table></td>
  </tr>
</table>
<% if ( (payPlan.size()>1)||(repayPlan.size()>1) ) { %>
<br clear=all style='page-break-before:always'>
<p align="center"></p>
<font size="4"><b>附件： </b></font>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
<td valign="top" align="center">
<font size="4"><b>提　款、还　款　计　划</b></font>
<table width="100%" border="1" cellspacing="0" class="table1" >
<tr>
<td colspan='2' ALIGN="center" width="50%"  class="td-rightbottom">提款计划</td>
<td colspan='2' ALIGN="center" width="50%" class="td-rightbottom">还款计划</td>
</tr>
<tr align="center">
<td width="25%"  class="td-rightbottom">提款时间</td>
<td width="25%"  class="td-rightbottom">提款金额</td>
<td width="25%"  class="td-rightbottom">还款时间</td>
<td width="25%"  class="td-rightbottom">还款金额</td>
</tr>
<%
	long n=(payPlan.size()>repayPlan.size())?payPlan.size():repayPlan.size();
	
	Timestamp t1=null;
	Timestamp t2=null;
	double d1=0;
	double d2=0;
	LoanPlanDetailInfo p1=null;
	LoanPlanDetailInfo p2=null;
	
	for ( int i=0;i<n;i++ )
	{
	 	if (i<payPlan.size())
	 	{
	 		p1=(LoanPlanDetailInfo)payPlan.get(i);
	 		t1=p1.getPlanDate();
	 		d1=p1.getAmount();
	 	}
	 	if ( i<repayPlan.size())
	 	{
	 		p2=(LoanPlanDetailInfo)repayPlan.get(i);
	 		t2=p2.getPlanDate();
	 		d2=p2.getAmount();
	 	}
%>
<TR>
	<TD class="td-rightbottom" align=center ><%=(t1==null)?"":DataFormat.getChineseDateString(t1)%>&nbsp;</TD>
	<TD class="td-rightbottom" align=center><%=(d1==0)?"":DataFormat.formatNumber(d1,2)%>&nbsp;</TD>
	<TD class="td-rightbottom" align=center><%=(t2==null)?"":DataFormat.getChineseDateString(t2)%>&nbsp;</TD>
	<TD class="td-rightbottom" align=center><%=(d2==0)?"":DataFormat.formatNumber(d2,2)%>&nbsp;</TD>
</TR>

<%	
		p1=null;
		p2=null;
		t1=null;
		t2=null;
		d1=0;
		d2=0;
	}  
%>
<tr align="center">
<td width="25%"  class="td-rightbottom">总计:</td>
<td width="25%"  class="td-rightbottom"><%=DataFormat.formatNumber(paySumAmount,2)%></td>
<td width="25%"  class="td-rightbottom">总计:</td>
<td width="25%"  class="td-rightbottom"><%=DataFormat.formatNumber(repaySumAmount,2)%></td>
</tr>

</table>
<p>&nbsp;</p>
</td>
</tr>
<tr>
<td align="center" height="40">&nbsp;</td>
</tr>
</table>

<%	} %>
<Script Language="JavaScript">
				window.print();
</SCRIPT>
</div>
</body>
</html>

<%
 	}catch (Exception ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>     