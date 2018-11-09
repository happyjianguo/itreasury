<%--
 页面名称 ：d003-v.jsp
 页面功能 : 新增贴现申请-保存贴现申请 借款单位详细信息显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "java.sql.*,
                   com.iss.itreasury.ebank.util.*,
                   java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.loan.util.*,
				   com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
				   com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				   com.iss.itreasury.ebank.obsystem.dataentity.*,
				   com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>	
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d003-v.jsp*******");
	
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
		
		//定义变量
		String strTemp = "";
		String strAction = (String)request.getAttribute("txtAction");
		
		
	    //ClientID标识
		 long lClientID = sessionMng.m_lClientID;
		//办事处标识 也是财务公司标识
		 long lOfficeID = sessionMng.m_lOfficeID;
		 //币种
		 long lCurrencyID = sessionMng.m_lCurrencyID;
		//办事处名称 也是财务公司名称
		 String strOfficeName = sessionMng.m_strOfficeName;
		//客户编号
		 String strCode = "";
		//客户名称
		 String strName = "";
		//法人代码或营业执照
		 String strLicenceCode = "";
		//企业类型
		 long lCorpNatureID = -1;
		//父公司标识
		 long lParentCorpID = -1;
		//父公司名称
		 String strParentCorpName = "";
		//父公司2标识
		 long lParentCorpID2 = -1;
		//父公司2名称
		 String strParentCorpName2 = "";
		//电子邮件
		 String strEmail = "";
		//地址
		 String strAddress = "";
		//邮编
		 String strZipCode = "";
		//电话
		 String strPhone = "";
		//传真
		 String strFax = "";
		//录入人ID
		 long lInputUserID = -1;
		//录入人姓名
		 String strInputUserName = "";
		//录入时间
		 Timestamp dtInput = null;
		//更新用户ID
		 long lModifyUserID = -1;
		//更新用户
		 String strModifyUserName = "";
		//更新时间
		 Timestamp dtModify  =  null;
		//是否是股东
		 long lIsPartner = -1;
		//财务公司账号
		 String strAccount = "";
		 long lAccountID = -1;
		 
		//开户银行1
		 String strBank1 = "";
		//开户银行2
		 String strBank2 = "";
		//开户银行3
		 String strBank3 = "";
		//省
		 String strProvince = "";
		//市
		 String strCity = "";
		//银行账户1
		 String strBankAccount1 = "";
		//银行账户2
		 String strBankAccount2 = "";
		//银行账户3
		 String strBankAccount3 = "";
		//经济性质
		 String strEconomyType = "";
		//信用等级
		 long lCreditLevelID = -1;
		//联系人
		 String strContacter = "";
		//自营贷款客户分类
		 long lLoanClientTypeID = -1;
		//自营贷款客户分类
		 String strLoanClientType = "";
		//结算客户分类
		 long lSettClientTypeID = -1;
		//结算客户分类
		 String strSettClientType = "";
		//风险级别
		 long lRiskLevelID = -1;
		//手工输入 风险评级
		 String strRiskLevel = ""; //注：华能专用 ***-大桥也是
	     //状态  是否有效
		 long lStatusID = -1;
		//法人代表
		 String strLegalPerson = "";
		//注册资本
		 String strCaptial = "";
		//机组容量
		 String strGeneratorCapacity = "";
		//评级单位
		 String strJudGelevelClient = "";
		//经营范围
		 String strDealScope = "";
		//贷款卡号
		 String strLoanCardNo = "";
		//贷款卡密码
		 String strLoanCardPwd = "";
		//法人代码证号
		 String strLegalPersonCode = "";
		//资质等级
		 String strIntelligenceLevel = "";
		//控股单位
		String strMust="";
		
		 long lKGClientID = -1;
		 String strKGClientName = "";
		 float fKGScale  = 0;
		 String strKGCardNo = "";
		 String strKGPwd = "";
		//其他股东1-3
		 long[] lQTClientID = {-1,-1,-1};
		 String[] strQTClientName = new String[3];
		 float[] fQTScale  = new float[3];
		 String[] strQTCardNo = new String[3];
		 String[] strQTPwd = new String[3];
		//分页显示总页数记录
		 long lPageCount = 0;
		//财务情况统计表ID  最近三年
		 long[] lFINANCETJBID = {-1,-1,-1};
		//财务情况统计表名称
		 String[] strFINANCETJBName = {"","",""};
		//财务情况统计表——年份
		 String[] strFINANCETJBYear = {"","",""};		
		//获取查询结果集
		ClientInfo resultInfo = null;
		
        resultInfo = (ClientInfo)request.getAttribute("resultInfo");
		
		if(resultInfo != null)
		{ 
		  lClientID = resultInfo.getClientID(); 
		  Log.print("\n\n~~~~~~~~~~~lClientID:"+lClientID);
		  lOfficeID = resultInfo.getOfficeID();
		  strOfficeName = resultInfo.getOfficeName();
		  strCode = DataFormat.formatNullString(resultInfo.getCode());
		  strName = DataFormat.formatNullString(resultInfo.getName());
		  strLicenceCode = DataFormat.formatNullString(resultInfo.getLicenceCode());
		  lCorpNatureID = resultInfo.getCorpNatureID();
		  lParentCorpID = resultInfo.getParentCorpID();
		  strParentCorpName = DataFormat.formatNullString(resultInfo.getParentCorpName());
		  lParentCorpID2 = resultInfo.getParentCorpID2();
		  strParentCorpName2 = DataFormat.formatNullString(resultInfo.getParentCorpName2());
		  strEmail = DataFormat.formatNullString(resultInfo.getEmail());
		  strAddress = DataFormat.formatNullString(resultInfo.getAddress());
		  strZipCode = DataFormat.formatNullString(resultInfo.getZipCode());
		  strPhone = DataFormat.formatNullString(resultInfo.getPhone());
		  strFax = DataFormat.formatNullString(resultInfo.getFax());
		  lInputUserID = resultInfo.getInputUserID();
		  strInputUserName = DataFormat.formatNullString(resultInfo.getInputUserName());
		  dtInput = resultInfo.getInput();
		  lModifyUserID = resultInfo.getModifyUserID();
		  strModifyUserName = DataFormat.formatNullString(resultInfo.getModifyUserName());
		  dtModify  = resultInfo.getModify();
		  lIsPartner = resultInfo.getIsPartner();
		  strAccount = DataFormat.formatNullString(resultInfo.getAccount());
		  if(strAccount !=null && strAccount !="")
			{
				lAccountID = 1;
			}
		  strBank1 = DataFormat.formatNullString(resultInfo.getBank1());
		  strBank2 = DataFormat.formatNullString(resultInfo.getBank2());
		  strBank3 = DataFormat.formatNullString(resultInfo.getBank3());
		  strProvince = DataFormat.formatNullString(resultInfo.getProvince());
		  strCity = DataFormat.formatNullString(resultInfo.getCity());
		  strBankAccount1 = DataFormat.formatNullString(resultInfo.getBankAccount1());
		  strBankAccount2 = DataFormat.formatNullString(resultInfo.getBankAccount2());
		  strBankAccount3 = DataFormat.formatNullString(resultInfo.getBankAccount3());
		  strEconomyType = DataFormat.formatNullString(resultInfo.getEconomyType());
		  lCreditLevelID = resultInfo.getCreditLevelID();
		  strContacter = DataFormat.formatNullString(resultInfo.getContacter());
		  lLoanClientTypeID = resultInfo.getLoanClientTypeID();
		  strLoanClientType = DataFormat.formatNullString(resultInfo.getLoanClientType());
		  lSettClientTypeID = resultInfo.getSettClientTypeID();
		  strSettClientType = DataFormat.formatNullString(resultInfo.getSettClientType());
		  lRiskLevelID = resultInfo.getRiskLevelID();
		  strRiskLevel = DataFormat.formatNullString(resultInfo.getRiskLevel()); //注：华能专用 ***-大桥也是
		  lStatusID = resultInfo.getStatusID();
		  strLegalPerson = DataFormat.formatNullString(resultInfo.getLegalPerson());
		  strCaptial = DataFormat.formatNullString(resultInfo.getCaptial());
		  strGeneratorCapacity = DataFormat.formatNullString(resultInfo.getGeneratorCapacity());
		  strJudGelevelClient = DataFormat.formatNullString(resultInfo.getJudGelevelClient());
		  strDealScope = DataFormat.formatNullString(resultInfo.getDealScope());
		  strLoanCardNo = DataFormat.formatNullString(resultInfo.getLoanCardNo());
		  strLoanCardPwd = DataFormat.formatNullString(resultInfo.getLoanCardPwd());
		  strLegalPersonCode = DataFormat.formatNullString(resultInfo.getLegalPersonCode());
		  strIntelligenceLevel = DataFormat.formatNullString(resultInfo.getIntelligenceLevel());	
		  lKGClientID = resultInfo.getKGClientID();
		  strKGClientName = DataFormat.formatNullString(resultInfo.getKGClientName());
		  fKGScale  = resultInfo.getKGScale();
		  strKGCardNo = DataFormat.formatNullString(resultInfo.getKGCardNo());
		  strKGPwd = DataFormat.formatNullString(resultInfo.getKGPwd());
		  lQTClientID = resultInfo.getQTClientID();
		  strQTClientName = resultInfo.getQTClientName();
		  fQTScale  = resultInfo.getQTScale();
		  strQTCardNo = resultInfo.getQTCardNo();
		  strQTPwd = resultInfo.getQTPwd();
		  lPageCount = resultInfo.getPageCount();
		  lFINANCETJBID = resultInfo.getFINANCETJBID();
		  strFINANCETJBName = resultInfo.getFINANCETJBName();
		  strFINANCETJBYear = resultInfo.getFINANCETJBYear();				
		}
		
		//如是修改“下一步”
		String strBackPage = null;
		long lID = -1;//贴现申请id
		
		strTemp = (String)request.getAttribute("strBackPage");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strBackPage = DataFormat.toChinese(strTemp.trim());
		}
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
	   //显示文件头
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	  	    Log.print("=======================1");
%>		
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post" action="d004-c.jsp">
<input type="Hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>">
<input type="Hidden" name="lInputUserID" value="<%=lInputUserID%>">
<input type="Hidden" name="strInputUserName" value="<%=strInputUserName%>">
<input type="Hidden" name="dtInput" value="<%=dtInput%>">
<input type="Hidden" name="lModifyUserID" value="<%=sessionMng.m_lClientID%>">
<input type="Hidden" name="strModifyUserName" value="<%=sessionMng.m_strClientName%>">
<input type="Hidden" name="dtModify" value="<%=DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID))%>">
<input type="Hidden" name="lStatusID" value="<%=Constant.RecordStatus.VALID%>">
<!--用于返回的参数!-->
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="strBackPage" value="<%=strBackPage%>">
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<table  class="top"  border=0 width="730">
	<tr class="tableHeader"> 
		<TD class=FormTitle colspan="2"><B>贴现申请－新增</B></TD>
	</tr>
	<tr>
		<td align="center" height="344" colspan="2">
     <%  
    	String CustomUrl = "/common/ShowClientInfo.jsp?ClientID="+String.valueOf(sessionMng.m_lClientID);
    	System.out.println("------@@@@@@@@@@@@###########-------------->"+CustomUrl);
    %>
   	 <jsp:include page='<%=CustomUrl%>' />
		

	<%if ( Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,false) ){%>		
			<tr>
				<td colspan=5 height="32">
					
			<input class=button name="financialDCB" type=button value="资产负债表"  onclick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=lClientID%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=lClientID%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				</td>
				<td>&nbsp;</td>
			</tr>
	<%}%>
		
	<%if ( Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,false) ){%>		
		<tr>
			<td align="center" height="2" colspan="2"> 
				<hr>
			</td>
		</tr>
	<%}%>
	
		<tr>
			<td align="center" height="2" width="94%"> 
			<div align="right">	
				<input type="button" name="SaveSubmit" value=" 下一步 " class = button onfocus="nextfield='submitfunction';" onClick="frmSubmit(frm);">
			</div>
			</td>
			<td align="right" width="6%" height="2">&nbsp;</td>
		</tr>
	</table>
</form>

<script language="JavaScript">
//firstFocus(frm.strLicenceCode);
//setSubmitFunction("frmSubmit(frm)");
setFormName("frm");

function frmSubmit(frm)
{
	if (checkInput(frm) )
	{
		//if (confirm("是否保存客户资料?"))
		//{	
            frm.action="d004-c.jsp"
			showSending();
		    frm.submit();
		//}
	}
}

function checkInput(frm)
{
/*
	if (!InputValid(frm.strLicenceCode,0,"string",1,0,100,"营业执照号码"))		return false;
	if (!InputValid(frm.strBank1,0,"string",1,0,100,"开户银行"))			return false;
	if (!InputValid(frm.strAccount,0,"string",1,0,100,"开户银行账号"))	return false;
	if (!InputValid(frm.strLoanCardNo,1,"int",0,0,0,"贷款卡号"))			return false;
	if (!InputValid(frm.strLoanCardPwd,1,"int",0,0,0,"贷款卡密码"))			return false;
	if (!InputValid(frm.strProvince,0,"string",1,0,100,"省"))				return false;
	if (!InputValid(frm.strCity,1,"string",1,0,100,"市"))					return false;
	if (!InputValid(frm.strAddress,1,"string",1,0,100,"地址"))				return false;
	if (!InputValid(frm.strZipCode,0,"zip",1,6,6,"邮编"))					return false;

	if (!InputValid(frm.strLegalPerson,0,"string",1,0,100,"法人代表"))		return false;
	if (!InputValid(frm.strPhone,0,"fax",1,0,100,"电话"))						return false;
	if (!InputValid(frm.strFax,0,"fax",1,0,100,"传真"))						return false;
	if (!InputValid(frm.strEmail,0,"email",1,0,100,"电邮地址"))			return false;
	if (!InputValid(frm.strContacter,0,"string",1,0,100,"联系人"))			return false;

	if (!checkMagnifier("frm","lLoanClientTypeID","lLoanClientTypeID","自营贷款客户分类"))	return false;
	
	if (!checkAmount(frm.strCaptial,0,"注册资本金")) return false;
*/
	return true ;
}
</script>

<script language="javascript">
function getAllScale(frm,value)
{
	var f1=0,f2=0,f3=0,f4=0 ;
	if((value>=0)&&(frm.fKGScale.value > 0)) f1= frm.fKGScale.value;
	if((value>=1)&&(frm.strQTClientName1.value !="")&&(frm.fQTScale1.value > 0)) f2 = frm.fQTScale1.value; 
	if((value>=2)&&(frm.strQTClientName2.value !="")&&(frm.fQTScale2.value > 0)) f3 = frm.fQTScale2.value; 
	if((value>=3)&&(frm.strQTClientName3.value !="")&&(frm.fQTScale3.value > 0)) f4 = frm.fQTScale3.value; 
	if((eval(f1) + eval(f2) + eval(f3) + eval(f4))>100)
	{
		if((value==0)&&( eval(f1)>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fKGScale.focus();
			return false;
		}
		if((value==1)&&( (eval(f1) + eval(f2))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fQTScale1.focus();
			return false;
		}
		if((value==2)&&( (eval(f1) + eval(f2) + eval(f3))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fQTScale2.focus();
			return false;
		}
		if((value==3)&&( (eval(f1) + eval(f2) + eval(f3) + eval(f4))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fQTScale3.focus();
			return false;
		}
	}
	return true;
}
</script>
<%	
   //显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>

