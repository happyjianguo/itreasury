<%--
/**
 * 程序名称：S861-Ipt.jsp
 * 功能说明：客户信息查看输出页面
 * 作　　者：刘琰
 * 完成日期：2003年11月27日
 */
--%>
<!--Header start-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,				
				 com.iss.itreasury.ebank.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<%
response.setHeader("Cache-Control","no-stored");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "[客户资料]";
%>

<%
	String strOffice = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "客户资料维护";
	String strSYSClientName=Env.getClientName();

try
{
	/**
	* isLogin start
	*/
	//登录检测
	/*if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}*/

	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	////////////////////////////////////////////////////////////////////*/
		//定义变量
		long	lID = -1;//客户ID 
		long	lOfficeID=sessionMng.m_lOfficeID;//办事处
		long	lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
		long	lUserID=sessionMng.m_lUserID;
		String	strOfficeName = null;//办事处
		String	strClientCode="";//客户编号
		String	strClientName="";//客户名称
		String	strLicence="";	//营业执照
		long	lCorpNatureID = -1;//企业类型
		long	lManageDeptID = -1;//母公司（上级主管部门）ID
		String strManageDept="";//母公司（上级主管部门）
		long	lManageDeptID2 = -1;//母公司（上级主管部门2）ID
		String strManageDept2="";//母公司（上级主管部门2）
		String strMailAddr="";//电子邮件
		String strAddress="";//地址
		String strZipCode="";//邮编
		String strTel="";//电话
		String strFax="";//传真
		//印鉴启用时间
		//当前印鉴iD
		//录入人
		//录入时间
		//修改人
		//修改时间
		long isShareHolder=-1;//是否股东
		long lAccountID = -1;//财务公司账号ID
		String strAccount="";//财务公司账号
		String strBank1="";//银行1
		String strBank2="";//银行2
		String strBank3="";//银行3
		String strAccount1="";//开户银行账号1
		String strAccount2="";//开户银行账号2
		String strAccount3="";//开户银行账号3
		String strLoanCardNo = "";//贷款卡号
		String strLoanCardPwd = "";//贷款卡密码
		String strProvince="";//省
		String strCity="";//市
		//String strEconomic="";//经济性质(录入栏位)，和企业性质不同
		long lCreditLevel=-1;//信用等级
		String strContacter = "";//联系人
		long lLoanClientTypeID=-1;//自营贷款客户分类
		long lSettClientTypeID=-1;//结算客户分类
		long lVentureLevel=-1;//风险级别
		long lStatusID = -1;//状态
		String strLegalPerson = "";//法人
		String strCapital="*";//资本金
		//double dCapital = 0.0 ;
		long lModelID = -1;//由哪个模块新增
		String strGeneratorCapacity = "";//机组容量
		String strJudgeClient=null;//评级单位
		String strDealScope="";//经营范围
		String strVentureLevel="";//手工录入风险等级
		////////////////////////
		String strIntelligenceLevel="";//资质等级
		String strLegelPersonCode="";//法人代码证号
		
		/////////////////////////////
		long lKGClientID = -1;//控股单位ID
		String strKGClientName = "";//控股单位
		float fScale=0;//持股比例
		String strCardNo="";//贷款卡号
		String strPwd="";//密码

		String[] strGDName = new String[3];//其它股东1-3
		float[] fGDScale={0,0,0};//持股比例1-3
		String[] strGDCardNo=new String[3];//贷款卡号1-3
		String[] strGDPwd=new String[3];//密码1-3

		///////////////////////////////////
		String strDeputy="";
		String strContact="";
		long lEconomic = -1;
		String strDKDCB = "";//贷款调查表
		String strDBDCB = "";//
		long lClientID=-1;
		//long[]	lContentID = {1,2,3};//三年财务情况ContentID 

		String	strControl="view";
		long lReturn =-1;
		long[] lContentID = {-1,-1,-1}; //客户文本的ID
		
		//从请求中获取参数
		OBClientInfo Info = new OBClientInfo();
		if(request.getAttribute("OBClientInfo")!=null)
		{
			Info = (OBClientInfo)request.getAttribute("OBClientInfo");
		}
		
		//获取详细信息
		lID=Info.getClientID();//客户ID
		strOfficeName=DataFormat.formatString(Info.getOfficeName());//办事处--------
		if(strOfficeName=="")
		{
			strOfficeName=DataFormat.formatString(strSYSClientName);
		}
		//log4j.info("办事处(财务公司):"+strOfficeName);
		strClientCode=DataFormat.formatString(Info.getCode());//客户编号
		strClientName=DataFormat.formatString(Info.getName());//客户名称
		strLicence=DataFormat.formatString(Info.getLicenceCode());	//营业执照
		lCorpNatureID =Info.getCorpNatureID();//企业性质（类型）
		lManageDeptID =Info.getParentCorpID();//母公司（上级主管部门）ID
		strManageDept=DataFormat.formatString(Info.getParentCorpName());//母公司（上级主管部门）
		lManageDeptID2 =Info.getParentCorpID2();//母公司（上级主管部门2）ID
		strManageDept2=DataFormat.formatString(Info.getParentCorpName2());//母公司（上级主管部门2）
		//if(strManageDept==null) strManageDept="";
		strMailAddr=DataFormat.formatString(Info.getEmail());//电子邮件
		strProvince=DataFormat.formatString(Info.getProvince());//省
		strCity=DataFormat.formatString(Info.getCity());//市
		strAddress=DataFormat.formatString(Info.getAddress());//地址
		strZipCode=DataFormat.formatString(Info.getZipCode());//邮编
		strTel=DataFormat.formatString(Info.getPhone());//电话
		strFax=DataFormat.formatString(Info.getFax());//传真
		isShareHolder=Info.getIsPartner();//是否股东
		strAccount=DataFormat.formatString(Info.getAccount());//财务公司账号
		if(strAccount !=null && strAccount !="")
		{
			lAccountID = 1;
		}
		strBank1=DataFormat.formatString(Info.getBank1());//银行1
		strBank2=DataFormat.formatString(Info.getBank2());//银行2
		strBank3=DataFormat.formatString(Info.getBank3());//银行3
		strAccount1=DataFormat.formatString(Info.getBankAccount1());//开户银行账号1
		strAccount2=DataFormat.formatString(Info.getBankAccount2());//开户银行账号2
		strAccount3=DataFormat.formatString(Info.getBankAccount3());//开户银行账号3
		strLoanCardNo=DataFormat.formatString(Info.getLoanCardNo());//贷款卡号
		strLoanCardPwd=DataFormat.formatString(Info.getLoanCardPwd());//贷款卡密码
		lCreditLevel=Info.getCreditLevelID();//信用等级
		strContacter =DataFormat.formatString(Info.getContacter());//联系人
		lLoanClientTypeID=Info.getLoanClientTypeID();//自营贷款客户分类
		lSettClientTypeID=Info.getSettClientTypeID();//结算客户分类--就是企业类型

		lVentureLevel=Info.getRiskLevelID();//风险级别
		//lStatusID = Info.getStatusID();//状态
		strLegalPerson = DataFormat.formatString(Info.getLegalPerson());//法人代表
		strCapital= DataFormat.formatString(Info.getCaptial());//资本金
		strGeneratorCapacity =DataFormat.formatString(Info.getGeneratorCapacity());//机组容量
		strJudgeClient =DataFormat.formatString(Info.getJudGelevelClient());//评级单位
		if(strJudgeClient=="")
		{
			strJudgeClient=DataFormat.formatString(strSYSClientName);
		}
		strDealScope=DataFormat.formatString(Info.getDealScope());//经营范围
		strVentureLevel=DataFormat.formatString(Info.getRiskLevel());//手工录入风险等级（华能专用）

		strIntelligenceLevel=DataFormat.formatString(Info.getIntelligenceLevel());//资质等级
		//log4j.info("资质等级:"+strIntelligenceLevel);
		strLegelPersonCode=DataFormat.formatString(Info.getLegalPersonCode());//法人代码证号
		//log4j.info("法人代码证号:"+strLegelPersonCode);

		lKGClientID = Info.getKGClientID();//控股单位ID
		strKGClientName = DataFormat.formatString(Info.getKGClientName());//控股单位
		fScale = Info.getFKGScale();//持股比例
		strCardNo=DataFormat.formatString(Info.getKGCardNo());//贷款卡号
		strPwd=DataFormat.formatString(Info.getKGPwd());//密码
		//其它股东1-3
		strGDName =Info.getQTClientName();//其它股东
		fGDScale = Info.getFQTScale();//持股比例
		strGDCardNo=Info.getQTCardNo();//贷款卡号
		strGDPwd=Info.getQTPwd();//密码
		lContentID = Info.getFINANCETJBID();
		
		String strMust="";
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<safety:resources />

<form name="frmS502" method="POST">
<table width="730" class="top">
    <tr class="tableHeader"> 
		<td class="FormTitle" height="29" colspan="2"> 
		<b>客户资料</b>
		</td>
	</tr>
	<tr>
		<td align="center" height="344" colspan="2"> 
    <%  
    	String CustomUrl = strContext + "/common/ShowClientInfo.jsp?ClientID="+String.valueOf(sessionMng.m_lClientID);
    	System.out.println("------@@@@@@@@@@@@###########-------------->"+CustomUrl);
    %>
   	 <jsp:include page='<%=CustomUrl%>' />
			</td>
		</tr>
	</table>
</form>


<!--presentation end-->
<!--check start-->
<script language="javascript">
	/*本页面刷新和定义初始焦点位子*/
	setFormName("frmS502");
</script>
<%
	//显示文件尾
	OBHtml.showOBHomeEnd(out);
	}
	catch (Exception e)
    {
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>

<%@ include file="/common/SignValidate.inc" %>
