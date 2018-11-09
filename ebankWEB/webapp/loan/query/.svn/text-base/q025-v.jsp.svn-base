<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.system.bizlogic.*,
				com.iss.itreasury.system.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
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
		
		//定义变量
		/////////////////////////////////////////////////////////////////////////////////
		long	lClientID = -1;//客户ID 
		String  strSYSClientName=Env.getClientName();
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
		String strCapital="";//资本金
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
		//long[]	lContentID = {1,2,3};//三年财务情况ContentID 
		long[] lContentID = {-1,-1,-1}; //客户文本的ID
		//////////////////////////////////////////////////////////////////////////////
        long lDiscountID = -1;
		String strDiscountCode = "";
		long lReturn = -1;
		String backpage = "";
		String strTmp = "";
        String strControl = "";
        String strMessage = "";
		ClientInfo Info = new ClientInfo();
        String strMust="";
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem loanCommonSetting = home.create();

////////control///////////////////////////////////////////////////////////////////////

		//取得参数
		strTmp = (String)request.getAttribute("control");
		if ( strTmp != null && strTmp.length() > 0 )
		{
			strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("backpage");
        if(strTmp != null && strTmp.length() > 0)
        {
			backpage = DataFormat.toChinese(strTmp);
        }

		strTmp = (String)request.getAttribute("lClientID");
		if ( strTmp != null && strTmp.length() > 0 )
		{
			lClientID = Long.parseLong(strTmp.trim());
		}

		//extract lDiscountID
        strTmp = (String)request.getAttribute("lDiscountID");
        if(strTmp != null && strTmp.length() > 0)
        {
            lDiscountID = Long.parseLong(strTmp);
        }

		strTmp = (String)request.getAttribute("strDiscountCode");
        if(strTmp != null && strTmp.length() > 0)
        {
            strDiscountCode = DataFormat.toChinese(strTmp);
        }

        if (strControl.equals("view"))
        {
			if (lClientID > 0)
			{				
				Info = loanCommonSetting.findClientByID(lClientID);

				strOfficeName = DataFormat.formatString(Info.getOfficeName());//办事处--------
				if(strOfficeName == "")
				{
					strOfficeName = DataFormat.formatString(strSYSClientName);
				}
				Log.print("办事处(财务公司):"+strOfficeName);
				strClientCode=DataFormat.formatString(Info.getCode());//客户编号
				strClientName=DataFormat.formatString(Info.getName());//客户名称
				strLicence=DataFormat.formatString(Info.getLicenceCode());	//营业执照
				lCorpNatureID =Info.getCorpNatureID();//企业性质（类型）
				lManageDeptID =Info.getParentCorpID();//母公司（上级主管部门）ID
				strManageDept=DataFormat.formatString(Info.getParentCorpName());//母公司（上级主管部门）
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
				strCapital=DataFormat.formatString(Info.getCaptial());//资本金
				strGeneratorCapacity =DataFormat.formatString(Info.getGeneratorCapacity());//机组容量
				strJudgeClient =DataFormat.formatString(Info.getJudGelevelClient());//评级单位
				if(strJudgeClient=="")
				{
					strJudgeClient=DataFormat.formatString(strSYSClientName);
				}
				strDealScope=DataFormat.formatString(Info.getDealScope());//经营范围
				strVentureLevel=DataFormat.formatString(Info.getRiskLevel());//手工录入风险等级（华能专用）

				strIntelligenceLevel = DataFormat.formatString(Info.getIntelligenceLevel());//资质等级
				Log.print("资质等级:"+strIntelligenceLevel);
				strLegelPersonCode = DataFormat.formatString(Info.getLegalPersonCode());//法人代码证号
				Log.print("法人代码证号:"+strLegelPersonCode);

				lKGClientID = Info.getKGClientID();//控股单位ID
				strKGClientName = DataFormat.formatString(Info.getKGClientName());//控股单位
				fScale = Info.getFKGScale();//持股比例
				strCardNo=DataFormat.formatString(Info.getKGCardNo());//贷款卡号
				strPwd=DataFormat.formatString(Info.getKGPwd());//密码
				//其它股东1-3
				strGDName = Info.getQTClientName();//其它股东
				fGDScale = Info.getFQTScale();//持股比例
				strGDCardNo = Info.getQTCardNo();//贷款卡号
				strGDPwd = Info.getQTPwd();//密码
				lContentID = Info.getFINANCETJBID();
				Log.print("-----查找客户的信息结束！-----");
			}
			else
			{
				//OBHtml.showExceptionMessage(out,sessionMng,ie,"贴现申请","", Constant.RecordStatus.VALID); 
				OBHtml.showOBHomeEnd(out);
				out.flush();
				return;
			}
         
        }

//////////////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out,sessionMng,"贴现申请",Constant.YesOrNo.NO);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm" method="post" action="S105.jsp">
<table class="top">
	<tr class="tableHeader"> 
		<TD class=FormTitle height=29 colspan=2><B>贴现申请－查看</B></TD>
	</tr>
	<tr>
		<td align="center" height="344" colspan="2"> 
		  <table cellpadding=0 width=730 height=60>
			<tr>
				<td></td>
				<td height="30">贴现申请编号：</td>
				<td colspan=4><P><%=DataFormat.formatString(strDiscountCode)%></P>
				</td>
			</tr>
			<tr> 
				<td colspan=6 height="30"><u>贴现申请单位资料</u></td>
			</tr>

			<tr>
				<td></td>
				<td>企业名称：</td>
				<td colspan=4>
					<input type="text" class="box" size="90" name="strClientName1" value="<%=strClientName%>" onfocus="nextfield='strClientCode'" DISABLED>
				</td>
			</tr>
			<tr>
				<td></td>
				<TD>客户编号：</TD>
				<td>
					<input type="text" class="box" size="20" name="strClientCode1" value="<%=strClientCode%>" onfocus="nextfield='strLicence'" DISABLED>
				</td>
				<td></td>
				<td>营业执照号码：</td>
				<td>
					<input type="text" class="box" size="20" name="strLicence" value="<%=strLicence%>"  onfocus="nextfield='txtAccountCodeCtrl1'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>

<%
				String sOfficeName = "";
%>
				<td><%=sOfficeName%>：</td>
				<td>
					<select name="strOfficeName1" class="box" onfocus="nextfield='txtAccountCodeCtrl1'">
						<option value="-1" SELECTED><%=strOfficeName%></option>
					</select>
				</td>
				<td></td>
<%
				String strMagnifierNameAccount = "";
					strMagnifierNameAccount = "账号";
%>
				<td><%=strMagnifierNameAccount%>：</td>
				<td>
					<input type="text" class="box" size="20" name="strAccount" value="<%=strAccount%>" onfocus="nextfield='strLoanCardNo'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>开户银行：</td>
				<td>
					<input type="text" class="box" size="20" name="strBank1" value="<%=strBank1%>"  onfocus="nextfield='strAccount1'" disabled>
				</td>
				<td></td>
				<td>开户银行账号：</td>
				<td>
					<input type="text" class="box" size="20" name="strAccount1" value="<%=strAccount1%>" onfocus="nextfield='strLoanCardNo'" disabled>
				</td>
			</tr>			
			<tr>
				<td align=right>&nbsp;</td>
				<td>贷款卡号：</td>
				<td>
					<input type="text" class="box" size="20" name="strLoanCardNo" value="<%=strLoanCardNo%>" onfocus="nextfield='strLoanCardPwd'" disabled>
				</td>
				<td align=right>&nbsp;</td>
				<td>贷款卡密码：</td>
				<td>
					<input type="text" class="box" size="20" name="strLoanCardPwd" value="<%=strLoanCardPwd%>" onfocus="nextfield='strProvince'" disabled>
				</td>
			</tr>
			<tr>
				<td rowspan=2 align=right>&nbsp;</td>
				<td rowspan=2>地址：</td>
				<td>
					<input type="text" class="box" size="8" name="strProvince" value="<%=strProvince%>" onfocus="nextfield='strCity'" disabled>&nbsp;省
					<input type="text" class="box" size="8" name="strCity" value="<%=strCity%>" onfocus="nextfield='strAddress'" disabled>&nbsp;市
					<br>
				</td>
				<td></td>
				<td>电话：</td>
				<td>
					<input type="text" class="box" size="17" name="strTel" value="<%=strTel%>"  onfocus="nextfield='strFax'" disabled>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" class="box" size="25" name="strAddress" value="<%=strAddress%>" onfocus="nextfield='strTel'" disabled>
				</td>
				<td></td>
				<td>传真：</td>
				<td>
					<input type="text" class="box" size="17" name="strFax" value="<%=strFax%>" onfocus="nextfield='strLegalPerson'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan=1>法人代表：</td>
				<td>
					<input type="text" class="box" size="20" name="strLegalPerson" value="<%=strLegalPerson%>" onfocus="nextfield='strContacter'" disabled>
				</td>
				<td></td>
<%
			String conTmp="";
%>

				<td colspan=1><%=conTmp%>：</td>
				<td>
					<input type="text" class="box" size="20" name="strContacter" value="<%=strContacter%>" onfocus="nextfield='strZipCode'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan=1>邮编：</td>
				<td>
					<input type="text" class="box" size="6" name="strZipCode" value="<%=strZipCode%>" onfocus="nextfield='strMailAddr'" maxlength="6" disabled>
				</td>
				<td></td>
				<td colspan=1 >电邮地址：</td>
				<td>
					<input type="text" class="box" size="20" name="strMailAddr" value="<%=strMailAddr%>" onfocus="nextfield='lCorpNatureID'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>企业类型：</td>
				<td>
				<select name="lCorpNatureID" class="box" onfocus="nextfield='txtManageDept'" disabled>
					<option value="-1" > </option>
			<%
					long[] lCorpTmp = LOANConstant.ClientCorpIndustry.getAllCode();
					for(int j =0;j< lCorpTmp.length;j++)
					{
						if (lCorpNatureID == lCorpTmp[j])
						{
			%>				<option value="<%=lCorpTmp[j]%>"SELECTED>
							<%=LOANConstant.ClientCorpIndustry.getName(lCorpTmp[j])%>
							</option>
			<%			}
						else
						{
			%>				<OPTION value="<%=lCorpTmp[j]%>">
								<%=LOANConstant.ClientCorpIndustry.getName(lCorpTmp[j])%>
							</OPTION>
			<%			}
					}
			%>
				</select>
<%
		//LOANHTML.ShowList(out,"lCorpNatureID","txtManageDept",lCorpNatureID,LOANConstant.ListType.SETTCLIENTTYPE);
%>

				</td>
				<td align=right><font color='#FF0000'>&nbsp;</font></td>
				<td colspan=1>上级主管单位:</td>
				<td>
					<input type="text" class="box" size="20" name="strManageDept" value="<%=strManageDept%>" onfocus="nextfield='strGeneratorCapacity'" disabled>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan=1>股东:</td>
				<td>
					<select name="isShareHolder" class="box" onfocus="nextfield='lLoanClientTypeID'" disabled>
			<%		if (isShareHolder==Constant.YesOrNo.YES) 
					{
			%>			<option value="<%=Constant.YesOrNo.YES%>" SELECTED>是 </option>
			<%		}
					else
					{
			%>			<option value="<%=Constant.YesOrNo.YES%>" >是 </option>
			<%		}
					if (isShareHolder==Constant.YesOrNo.NO) 
					{
			%>			<option value="<%=Constant.YesOrNo.NO%>" SELECTED>否 </option>
			<%		}
					else
					{
			%>			<option value="<%=Constant.YesOrNo.NO%>" > 否</option>
			<%		}
			%>
					</select>
				</td>
				<td align=right>&nbsp;</td>
				<td colspan=1>自营贷款客户分类：</td>
				<td>
<%
				LOANHTML.ShowList(out,"lLoanClientTypeID","lCreditLevel",lLoanClientTypeID,LOANConstant.ListType.LOANCLIENTTYPE,true,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan=1>信用等级：</td>
				<td>
				<select name="lCreditLevel" class="box" onfocus="nextfield='lVentureLevel'" disabled>
					<option value="-1"> </option>
			<%
				long[] CreditTmp = LOANConstant.CreditLevel.getAllCode();
				for(int i =0;i< CreditTmp.length;i++)
				{
					if (lCreditLevel == CreditTmp[i])
					{%>
						<option value="<%=CreditTmp[i]%>"SELECTED>
						<%=LOANConstant.CreditLevel.getName(CreditTmp[i])%>
						</option>
					<%
					}
					else
					{%>
						<OPTION value="<%=CreditTmp[i]%>">
							<%=LOANConstant.CreditLevel.getName(CreditTmp[i])%>
						</OPTION>
					<%}
				}	%>
				</select>
				</td>
				<td></td>
				<td colspan=1>风险评级：</td>
				<td>
					<input type="text" class="box" size="20" name="lVentureLevel" value="<%=strVentureLevel%>" onfocus="nextfield='strJudgeClient'" disabled>
				</td>
			</tr>
			<tr>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td>注册资本金：</td>
				<td>
					<input type="text" class="box" size="20" name="strCapital" value="<%=strCapital%>" onfocus="nextfield='strDealScope'" disabled>
				</td>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td colspan=1>经营范围：</td>
				<td>
					<input type="text" class="box" size="20" name="strDealScope" value="<%=strDealScope%>" onfocus="nextfield='txtKGClientName'" disabled>
				</td>
			</tr>
			<tr>
				<td colspan=6>
					<hr>
				</td>
			</tr>
			<tr>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td colspan=1>控股单位:</td>
				<td>
					<input type="text" class="box" size="20" name="strKGClientName" value="<%=strKGClientName%>" onfocus="nextfield='strGeneratorCapacity'" disabled>
				</td>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td colspan=1>持股比例：</td>
				<td>
					<input type="text" name="fScale" size="20" class="tar" value="<%= fScale <= 0 ? "" : DataFormat.formatRate(fScale)%>"  onfocus="nextfield='strCardNo'" disabled>&nbsp;%
				</td>
			</tr>
			<tr>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td>贷款卡号:</td>
				<td>
					<input type="text" name="strCardNo" size="20" class="box" value="<%=strCardNo%>" onfocus="nextfield='strPwd'" disabled>
				</td>
				<td align=right><font color='#FF0000'><%=strMust%>&nbsp;</font></td>
				<td colspan=1>贷款卡密码：</td>
				<td>
					<input type="text" name="strPwd" size="20" class="box" value="<%=strPwd%>" onfocus="nextfield='strGDName1'" disabled>
				</td>
			</tr>
			<tr>
				<td colspan=6>
					<hr>
				</td>
			</tr>
<%
		for(int i=1;i<=strGDName.length;i++)
		{
%>
			<tr>
				<td></td>
				<td>其他股东单位<%=i%>：
				</td>
				<td>
					<input type="text" name="strGDName<%=i%>" size="20" class="box" value="<%=DataFormat.formatString(strGDName[i-1])%>"  onfocus="nextfield='fGDScale<%=i%>'" disabled>
				</td>
				<td></td>
				<td colspan=1> 持股比例：</td>
				<td>
					<input type="text" name="fGDScale<%=i%>" size="20" class="tar" value="<%=fGDScale[i-1] <= 0? "" : DataFormat.formatRate(fGDScale[i-1])%>"  onfocus="nextfield='strGDCardNo<%=i%>'" disabled>&nbsp;%
				</td>
			</tr>
			<tr>
				<td></td>
				<td>贷款卡号:</td>
				<td>
					<input type="text" name="strGDCardNo<%=i%>" size="20" class="box" value="<%=DataFormat.formatString(strGDCardNo[i-1])%>" onfocus="nextfield='strGDPwd<%=i%>'" disabled>
				</td>
				<td></td>
				<td colspan=1>贷款卡密码：</td>
				<td>
<%
				if(i==strGDName.length)
				{
%>
					<input type="text" name="strGDPwd<%=i%>" size="20" class="box" value="<%=DataFormat.formatString(strGDPwd[i-1])%>" onfocus="nextfield='SaveSubmit'" disabled>
<%
				}
				else
				{
%>
					<input type="text" name="strGDPwd<%=i%>" size="20" class="box" value="<%=DataFormat.formatString(strGDPwd[i-1])%>" onfocus="nextfield='strGDName<%=i+1%>'" disabled>
<%
				}
%>
					</td>
			</tr>
			<tr>
				<td colspan=6>
					<hr>
				</td>
			</tr>
<%
		}
%>

			<tr>
				<td colspan=5 height="32"> 
					<input class=button name="loanDCB" type=button value="贷款调查表" onclick="Javascript:window.open('../../content/c220a-c.jsp?ClientID=<%=lClientID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/c220a-c.jsp?ClientID=<%=lClientID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
					<input class=button name="financialDCB" type=button value="财务情况统计表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=lClientID%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=yes,menubar=yes,location=yes,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=lClientID%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=yes,menubar=yes,location=yes,resizable=yes,scrollbars =yes');">
				</td>
				<td>&nbsp;</td>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center" height="2" colspan="2"> 
				<hr>
			</td>
		</tr>
		<tr>
			<td align="center" height="2" width="94%"> 
			<div align="right">
				<input type="button" name="CancelSubmit" value=" 返 回 "class = button onclick="backto()">
			</div>
			</td>
			<td align="right" width="6%" height="2">&nbsp;</td>
		</tr>
	</table>
</form>

<script language="JavaScript">
function backto()
{
	MM_goToURL('self','<%=backpage%>.jsp?control=view&lDiscountID=<%=lDiscountID%>');
}
function frmSubmit(frm)
{
	if (checkInput(frm) )
	{
		if (confirm("是否保存客户资料?"))
		{			
			frm.action="S105.jsp";
			frm.control.value="save";
			showSending();
			frm.submit();
		}
	}
}
function confirmkehu()
{		
		frm.action="../normal/S2.jsp?control=view";
		frm.control.value="view";
		showSending();
		frm.submit();
	
}
function checkInput(frm)
{
	if (!InputValid(frm.strLicence,0,"string",1,0,100,"营业执照号码"))		return false;
	if (!InputValid(frm.strBank1,0,"string",1,0,100,"开户银行"))			return false;
	if (!InputValid(frm.strAccount1,0,"string",1,0,100,"开户银行账号"))	return false;
	if (!InputValid(frm.strLoanCardNo,1,"int",0,0,0,"贷款卡号"))			return false;
	if (!InputValid(frm.strLoanCardPwd,1,"int",0,0,0,"贷款卡密码"))			return false;
	if (!InputValid(frm.strProvince,0,"string",1,0,100,"省"))				return false;
	if (!InputValid(frm.strCity,1,"string",1,0,100,"市"))					return false;
	if (!InputValid(frm.strAddress,1,"string",1,0,100,"地址"))				return false;
	if (!InputValid(frm.strZipCode,0,"zip",1,6,6,"邮编"))					return false;
	if (!InputValid(frm.strLegalPerson,0,"string",1,0,100,"法人代表"))		return false;
	if (!InputValid(frm.strTel,0,"fax",1,0,100,"电话"))						return false;
	if (!InputValid(frm.strFax,0,"fax",1,0,100,"传真"))						return false;
	if (!InputValid(frm.strMailAddr,0,"email",1,0,100,"电邮地址"))			return false;
	if (!InputValid(frm.strContacter,0,"string",1,0,100,"联系人"))			return false;
	if (!InputValid(frm.strJudgeClient,0,"string",1,0,100,"评级单位"))		return false;
	//if (!checkMagnifier("frm","lManageDeptID","txtManageDept","上级主管单位名称"))	return false;
	if (!checkMagnifier("frm","lLoanClientTypeID","lLoanClientTypeID","自营贷款客户分类"))	return false;
	//if (!InputValid(frm.lLoanClientTypeID,1,"int",1,1,10000,"自营贷款客户分类"))			return false;
	/*
	if (frm.lManageDeptID.value<0)
	{
		alert("请输入上级主管单位");
		frm.txtManageDept.focus();
		return false;
	}//*/

	if (!InputValid(frm.strGDName1,0,"string",1,0,100,"其他股东单位1"))		return false;
	if(frm.strGDName1.value !="")
	{
		if (!InputValid(frm.fGDScale1,0,"float",1,0,100,"股东1持股比例"))	return false;
		if (!InputValid(frm.strGDCardNo1,0,"int",0,0,100,"股东1贷款卡号"))	return false;
		if (!InputValid(frm.strGDPwd1,0,"int",0,0,100,"股东1贷款卡密码"))	return false;
		if (getAllScale(frm,1)==false) return false;
	}
	if (!InputValid(frm.strGDName2,0,"string",1,0,100,"其他股东单位2"))		return false;
	if(frm.strGDName2.value !="")
	{
		if (!InputValid(frm.fGDScale2,0,"float",1,0,100,"股东2持股比例"))	return false;
		if (!InputValid(frm.strGDCardNo2,0,"int",0,0,100,"股东2贷款卡号"))	return false;
		if (!InputValid(frm.strGDPwd2,0,"int",0,0,100,"股东2贷款卡密码"))	return false;
		if (getAllScale(frm,2)==false) return false;
	}
	if (!InputValid(frm.strGDName3,0,"string",1,0,100,"其他股东单位3"))		return false;
	if(frm.strGDName3.value !="")
	{
		if (!InputValid(frm.fGDScale3,0,"float",1,0,100,"股东3持股比例"))	return false;
		if (!InputValid(frm.strGDCardNo3,0,"int",0,0,100,"股东3贷款卡号"))	return false;
		if (!InputValid(frm.strGDPwd3,0,"int",0,0,100,"股东3贷款卡密码"))	return false;
		if (getAllScale(frm,3)==false) return false;
	}
	return true ;
}

</script>

<script language="javascript">
function getAllScale(frm,value)
{
	var f1=0,f2=0,f3=0,f4=0 ;
	if((value>=0)&&(frm.fScale.value > 0)) f1= frm.fScale.value;
	if((value>=1)&&(frm.strGDName1.value !="")&&(frm.fGDScale1.value > 0)) f2 = frm.fGDScale1.value; 
	if((value>=2)&&(frm.strGDName2.value !="")&&(frm.fGDScale2.value > 0)) f3 = frm.fGDScale2.value; 
	if((value>=3)&&(frm.strGDName3.value !="")&&(frm.fGDScale3.value > 0)) f4 = frm.fGDScale3.value; 
	if((eval(f1) + eval(f2) + eval(f3) + eval(f4))>100)
	{
		if((value==0)&&( eval(f1)>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fScale.focus();
			return false;
		}
		if((value==1)&&( (eval(f1) + eval(f2))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fGDScale1.focus();
			return false;
		}
		if((value==2)&&( (eval(f1) + eval(f2) + eval(f3))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fGDScale2.focus();
			return false;
		}
		if((value==3)&&( (eval(f1) + eval(f2) + eval(f3) + eval(f4))>100 ))
		{
			alert("控股比例之和不能超过100%");
			frm.fGDScale3.focus();
			return false;
		}
		
	}
	return true;
}
</script>

<%
		OBHtml.showOBHomeEnd(out);
		
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "","",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>