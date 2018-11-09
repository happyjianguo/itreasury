
<%@ page contentType="text/html;charset=gbk" %>

<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>	
<%@page import="com.iss.itreasury.clientmanage.dataentity.ClientInfo"%>	
<%@ page import="com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo" %>
<%@ page import="com.iss.itreasury.clientmanage.bizdelegation.CorportionDelegation" %>
<%@ page import="com.iss.itreasury.clientmanage.bizdelegation.NatureDelegation" %>
<%@page import="com.iss.itreasury.clientmanage.bizdelegation.ClientDelegation"%>		
<%@ page import="com.iss.itreasury.clientmanage.client.dataentity.NatureInfo" %>	
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.clientmanage.util.CMConstant,
				com.iss.itreasury.ebank.util.*" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	

	try{
		String strTitle = null;
		long lShowMenu = OBConstant.ShowMenu.YES;
	    //用户登录检测 
	    if (sessionMng.isLogin() == false)
	    {
	    	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
	    	out.flush();
	    	return;
	    }

	    // 判断用户是否有权限 
	    if (sessionMng.hasRight(request) == false)
	    {
	        out.println(sessionMng.hasRight(request));
	    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
	    	out.flush();
	    	return;
	    }
	 OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		long ClientID = -1;
		String strTmp = (String)request.getParameter("ClientID");
		System.out.println("================="+strTmp);
		if(strTmp != null && !strTmp.equals("-1"))
		{
			ClientID = Long.parseLong(strTmp);
		}
		ClientID=sessionMng.m_lClientID;
		ClientDelegation cd = new ClientDelegation();
		NatureDelegation na = new NatureDelegation();
		CorportionDelegation cor = new CorportionDelegation();
		ClientInfo finfo =  new ClientInfo();
		CorporationInfo corinfo = new CorporationInfo();
		NatureInfo fnainfo = new NatureInfo();
		finfo.setId(ClientID);
		corinfo.setClientid(ClientID);
		fnainfo.setClientid(ClientID);
		Collection c = cd.load(finfo);
		Collection c1 = cor.findByCondition(corinfo);
		Collection c2 = na.findByCondition(fnainfo);
		if(c != null)
		{
			Iterator it = c.iterator();
			while(it.hasNext())
			{
				finfo = (ClientInfo)it.next();
				if(finfo.getClientBaseType().equals(CMConstant.ClientBaseType.CORPORATION))
				{
					if(c1 != null)
					{
						Iterator it1 = c1.iterator();
						while(it1.hasNext())
						{
							corinfo =(CorporationInfo)it1.next();
						}
					}
					String strbuilddate = "";
					strbuilddate = DataFormat.getDateString(corinfo.getBuildDate());
					String strinputdate = "";
					strinputdate = DataFormat.getDateString(finfo.getInputDate());
					String strcreditleveldate = "";
					strcreditleveldate = DataFormat.getDateString(corinfo.getCreditLevelDate());
					String strsignstart = "";
					strsignstart = DataFormat.getDateString(corinfo.getSignStart());
					String strevl = "";
					strevl = DataFormat.formatListLong(finfo.getServiceLevel());
%>

<html>
<body>
<form name="testfrm" enctype ="multipart/form-data"  action="" method="post">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">客户资料查询</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">基本资料</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td width="19%" height="20" class="MsoNormal">&nbsp;&nbsp;客户编号：</td>
				<td width="27%"><%=finfo.getCode()%></td>
				<TD width="18%">&nbsp;</TD>
				<TD width="36%">&nbsp;</TD>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;客户中文名称：</td>
				<td height="20" ><input name="name" type="text" disabled class="box" value='<%=finfo.getName()%>'  maxlength='30'></td>
				<td height="20" >&nbsp;</td>
				<td height="20" >&nbsp;</td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;客户外文名称：</td>
				<td><input type="text" name="engName" class="box"  maxlength='30' value='<%=finfo.getEngName()%>' disabled></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;客户中文简称：</td>
				<td><input type="text" name="simpleName" class="box"  maxlength='15' value='<%=finfo.getSimpleName()%>' disabled></td>
				<td>&nbsp;&nbsp;客户中文别名：</td>
				<td><input type="text" name="name2" class="box"  maxlength='15' value='<%=finfo.getName2()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;客户外文简称：</td>
				<td><input type="text" name="simpleEngName" class="box"  maxlength='15' value='<%=finfo.getSimpleEngName()%>' disabled></td>
				<td>&nbsp;&nbsp;客户外文别名：</td>
				<td><input type="text" name="engName2" class="box"  maxlength='15' value='<%=finfo.getEngName2()%>' disabled></td>
				</TR>
				
				<TR>
				<%
					String sql1 = "select id ,Name as value from client_ExtendAttribute where AttributeID=1 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
							
								if(!CMConstant.getFieldName(1, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
								 %>
				<td height="20" >&nbsp;&nbsp;<%=CMConstant.getFieldName(1, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID1" styleClass="select" sql="<%=sql1%>" value="<%=String.valueOf(corinfo.getClienttypeID1())%>"   disabled="true"/></td>
				<%
								}
								String sql2 = "select id ,Name as value from client_ExtendAttribute where AttributeID=2 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
								if(!CMConstant.getFieldName(2, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				
				%>
				<td>&nbsp;&nbsp;<%=CMConstant.getFieldName(2, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID2"  styleClass="select" sql="<%=sql2%>" value="<%=String.valueOf(corinfo.getClienttypeID2())%>"  disabled="true"/></td>
				<%
					}
				%>
				</TR>
				
				<TR>
				<%
					
					String sql3 = "select id ,Name as value from client_ExtendAttribute where AttributeID=3 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(3, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" >&nbsp;&nbsp;<%=CMConstant.getFieldName(3, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID3" styleClass="select" sql="<%=sql3%>" value="<%=String.valueOf(corinfo.getClienttypeID3())%>"  disabled="true"/></td>
				<%
				}
					String sql4 = "select id ,Name as value from client_ExtendAttribute where AttributeID=4 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(4, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td>&nbsp;&nbsp;<%=CMConstant.getFieldName(4, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID4" styleClass="select" sql="<%=sql4%>" value="<%=String.valueOf(corinfo.getClienttypeID4())%>"  disabled="true"/></td>
				<%
					}
				%>
				</TR>
				
				<TR>
				<%
					String sql5 = "select id ,Name as value from client_ExtendAttribute where AttributeID=5 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(5, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" >&nbsp;&nbsp;<%=CMConstant.getFieldName(5, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID5" styleClass="select" sql="<%=sql5%>" value="<%=String.valueOf(corinfo.getClienttypeID5())%>"  disabled="true"/></td>
				<%
				}
					String sql6 = "select id ,Name as value from client_ExtendAttribute where AttributeID=6 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
				
					if(!CMConstant.getFieldName(6, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td>&nbsp;&nbsp;<%=CMConstant.getFieldName(6, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="clienttypeID6" styleClass="select" sql="<%=sql6%>" value="<%=String.valueOf(corinfo.getClienttypeID6())%>"  disabled="true"/></td>
				<%
					}
				%>
				</TR>
				
				<TR>
				<%
					String s1 = "getSuperCorp1SQL("+sessionMng.m_strOfficeNo+",form_1.parentCorpID1.value)";
					String s2 = "getSuperCorp1SQL("+sessionMng.m_strOfficeNo+",form_1.parentCorpID2.value)";
					String s3 = "getSuperCorp1SQL("+sessionMng.m_strOfficeNo+",form_1.budgetParent.value)";
				%>
				<td height="20" >&nbsp;&nbsp;上级单位1：</td>
				<td ><input name="parentCorpID1" type="text" disabled class="box" value='<%=CMConstant.parentName(corinfo.getParentCorpID1())%>'  disabled></td>
				<td height="20" >&nbsp;&nbsp;上级单位2：
				<td ><input name="parentCorpID2" type="text" disabled class="box" value='<%=CMConstant.parentName(corinfo.getParentCorpID2())%>'  disabled></td>
				</TR>
				<TR>
				<td height="20" nowrap>&nbsp;&nbsp;预算管理上级单位：
				<td ><input name="budgetParent" type="text" disabled class="box" value='<%=corinfo.getBudgetParent()%>'  disabled></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">注册信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td width="19%" height="20" >&nbsp;&nbsp;注册国家：</td>
				<td width="27%"><input type="text" name="country" class="box"  maxlength='15' value='<%=finfo.getCountry()%>' disabled></td>
				<TD width="18%">&nbsp;&nbsp;注册地点：</TD>
				<TD width="36%"><input type="text" name="registerPlace" class="box"  maxlength='15' value='<%=corinfo.getRegisterPlace()%>' disabled></TD>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;组织机构代码：</td>
				<td height="20" ><input name="legalPersonCodeCert" type="text" disabled class="box" value='<%=corinfo.getLegalPersonCodeCert()%>'  maxlength='15' disabled></td>
				<td height="20" >&nbsp;&nbsp;营业执照号码：</td>
				<td height="20" ><input type="text" name="licenceCode" class="box"  maxlength='15' value='<%=corinfo.getLicenceCode()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;法人代表：</td>
				<td><input type="text" name="legalPerson" class="box"  maxlength='15' value='<%=corinfo.getLegalPerson()%>' disabled></td>
				<td>&nbsp;&nbsp;金融许可证号码：</td>
				<td><input type="text" name="financePermissionNo" class="box"  maxlength='15' value='<%=corinfo.getFinancePermissionNo()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;国税登记号：</td>
				<td><input type="text" name="countryTaxRegisterNo" class="box"  maxlength='15' value='<%=corinfo.getCountryTaxRegisterNo()%>' disabled></td>
				<td>&nbsp;&nbsp;地税登记号：</td>
				<td><input type="text" name="localTaxRegisterNo" class="box"  maxlength='15' value='<%=corinfo.getLocalTaxRegisterNo()%>' disabled></td>
				</TR>
				<!--<TR>
				 <td height="20" >&nbsp;&nbsp;客户外文简称：</td>
				<td><input type="text" name="simpleEngName" class="box"  maxlength='15' value='<%=finfo.getSimpleEngName()%>' disabled></td>
				<td>&nbsp;&nbsp;客户外文别名：</td>
				<td><input type="text" name="engName2" class="box"  maxlength='15' value='<%=finfo.getEngName2()%>' disabled></td>
				</TR> -->
				<TR>
				<td height="20" >&nbsp;&nbsp;注册币种1：</td>
				<td><input type="text" name="registerCurrencyType" class="box"  maxlength='10' value='<%=corinfo.getRegisterCurrencyType()%>' disabled></td>
				<td>&nbsp;&nbsp;注册资本1：</td>
				<td><input type="text" name="registerCapital1" class="box"  maxlength='10' value='<%=corinfo.getRegisterCapital1()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;注册币种2：</td>
				<td><input type="text" name="registerCurrencyType2" class="box"  maxlength='10' value='<%=corinfo.getRegisterCurrencyType2()%>' disabled></td>
				<td>&nbsp;&nbsp;注册资本2：</td>
				<td><input type="text" name="registerCapital2" class="box"  maxlength='10' value='<%=corinfo.getRegisterCapital2()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;注册币种3：</td>
				<td><input type="text" name="registerCurrencyType3" class="box"  maxlength='10' value='<%=corinfo.getRegisterCurrencyType3()%>' disabled></td>
				<td>&nbsp;&nbsp;注册资本3：</td>
				<td><input type="text" name="registerCapital3" class="box"  maxlength='10' value='<%=corinfo.getRegisterCapital3()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;注册币种4：</td>
				<td><input type="text" name="registerCurrencyType4" class="box"  maxlength='10' value='<%=corinfo.getRegisterCurrencyType4()%>' disabled></td>
				<td>&nbsp;&nbsp;注册资本4：</td>
				<td><input type="text" name="registerCapital4" class="box"  maxlength='10' value='<%=corinfo.getRegisterCapital4()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;注册币种5：</td>
				<td><input type="text" name="registerCurrencyType5" class="box"  maxlength='10' value='<%=corinfo.getRegisterCurrencyType5()%>' disabled></td>
				<td>&nbsp;&nbsp;注册资本5：</td>
				<td><input type="text" name="registerCapital5" class="box"  maxlength='10' value='<%=corinfo.getRegisterCapital5()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;验资机构：<a href="#"></a></td>
				<td><input type="text" name="validateOrganization" class="box"  maxlength='20' value='<%=corinfo.getValidateOrganization()%>' disabled></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				</TR>
			</table>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">上市信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td height="20" width="19%">&nbsp;&nbsp;是否上市公司：</td>
				<td width="27%"><select class='select' name="isMarkCompany" disabled>
					<option value=1 >是</option>
					<%
						if (corinfo.getIsMarkCompany()==2)
						{
					%>
						<option value=2 selected>否</option>
					<%}else{%>							
						<option value=2>否</option>
					<%}%>
					</select></td>
				<TD width="18%">&nbsp;</TD>
				<TD width="36%">&nbsp;</TD>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;上市地点1：</td>
				<td><input type="text" name="markPlace1" class="box"  maxlength='15' value='<%=corinfo.getMarkPlace1()%>' disabled></td>
				<td> &nbsp;&nbsp;股票代号1：</td>
				<td><input type="text" name="stockNo1" class="box"  maxlength='10' value='<%=corinfo.getStockNo1()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;上市地点2：</td>
				<td><input type="text" name="markPlace2" class="box"  maxlength='15' value='<%=corinfo.getMarkPlace2()%>' disabled></td>
				<td>&nbsp;&nbsp;股票代号2：</td>
				<td><input type="text" name="stockNo2" class="box"  maxlength='10' value='<%=corinfo.getStockNo2()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;上市地点3：</td>
				<td><input type="text" name="markPlace3" class="box"  maxlength='15' value='<%=corinfo.getMarkPlace3()%>' disabled></td>
				<td>&nbsp;&nbsp;股票代号3：</td>
				<td><input type="text" name="stockNo3" class="box"  maxlength='10' value='<%=corinfo.getStockNo3()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;上市地点4：</td>
				<td><input type="text" name="markPlace4" class="box"  maxlength='15' value='<%=corinfo.getMarkPlace4()%>' disabled></td>
				<td>&nbsp;&nbsp;股票代号4：</td>
				<td><input type="text" name="stockNo4" class="box"  maxlength='10' value='<%=corinfo.getStockNo4()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;上市地点5：</td>
				<td><input type="text" name="markPlace5" class="box"  maxlength='15' value='<%=corinfo.getMarkPlace5()%>' disabled></td>
				<td>&nbsp;&nbsp;股票代号5：</td>
				<td><input type="text" name="stockNo5" class="box"  maxlength='10' value='<%=corinfo.getStockNo5()%>' disabled></td>
				</TR>
			</table>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">经营信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td height="20" width="19%">&nbsp;&nbsp;是否财务公司：</td>
				<td width="27%"><select class='select' name="isPartner" disabled>
					<%
						if (corinfo.getIsPartner()==2)
						{
					%>
						<option value=2 selected>否</option>
					<%}else{%>							
						<option value=2>否</option>
					<%}%>
					<% if (corinfo.getIsPartner()==1) { %>
						<option value=1 selected>是</option>
					<% } else { %>							
					<option value=1>是</option>
					<%}%>
				
				</select></td>
				<TD width="18%">&nbsp;</TD>
				<TD width="36%">&nbsp;</TD>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;经营范围：</td>
				<td><input type="text" name="dealScope" class="box"  maxlength='10' value='<%=corinfo.getDealScope()%>' disabled></td>
				<td>&nbsp;&nbsp;生产规模：</td>
				<td><input type="text" name="productScope" class="box"  maxlength='10' value='<%=corinfo.getProductScope()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;资产规模：</td>
				<td><input type="text" name="capitalScope" class="box"  maxlength='10' value='<%=corinfo.getCapitalScope()%>' disabled></td>
				<td>&nbsp;&nbsp;净资产：</td>
				<td><input type="text" name="netCapital" class="box"  maxlength='10' value='<%=corinfo.getNetCapital()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;主要产品和产量：</td>
				<td><input type="text" name="products" class="box"  maxlength='10' value='<%=corinfo.getProducts()%>' disabled></td>
				<td nowrap >&nbsp;&nbsp;主营业务及销售市场：</td>
				<td><input type="text" name="operations" class="box"  maxlength='10' value='<%=corinfo.getOperations()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;成立时间：</td>
				<td><input type="text" name="buildDate" class="box" value='<%=strbuilddate%>' disabled></td>
				<td>&nbsp;&nbsp;员工总数：</td>
				<td><input type="text" name="employeeNumber" class="box"  maxlength='8' value='<%=corinfo.getEmployeeNumber()%>' disabled></td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">联络信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td colspan="4">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr>
							<td width="10%" height="20" >&nbsp;&nbsp;地址：</td>
							<td width="9%" align="right">省：</td>
							<td colspan="3" nowrap>
								<input name="province" type="text" class="box" size="10"  maxlength='10' value='<%=corinfo.getProvince()%>' disabled>
							</td>
						</tr>
						<tr>
							<td></td>
							<td align="right">市：</td>
							<td>
								<input name="city" type="text" class="box" size="10"  maxlength='10' value='<%=corinfo.getCity()%>' disabled>
							</td>
						</tr>
						<tr>
							<td></td>
							<td align="right">地址：</td>
							<td>
								<input name="address" type="text" class="box" size="10"  maxlength='10' value='<%=corinfo.getAddress()%>' disabled>
							</td>
						</tr>
					</table>
				</td>
				</TR>
				<TR>
				<td height="20" width="19%">&nbsp;&nbsp;邮编：</td>
				<td width="27%"><input type="text" name="zipCode" class="box"  maxlength='6' value='<%=corinfo.getZipCode()%>' disabled></td>
				<td width="18%">&nbsp;</td>
				<td width="36%">&nbsp;</td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;电话：</td>
				<td><input type="text" name="phone" class="box"  maxlength='15' value='<%=corinfo.getPhone()%>' disabled></td>
				<td>&nbsp;&nbsp;传真：</td>
				<td><input type="text" name="fax" class="box"  maxlength='15' value='<%=corinfo.getFax()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;网址：</td>
				<td><input type="text" name="website" class="box"  maxlength='30' value='<%=corinfo.getWebsite()%>' disabled></td>
				<td>&nbsp;&nbsp;电子邮件：</td>
				<td><input type="text" name="email" class="box"  maxlength='15' value='<%=corinfo.getEmail()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;结算业务联系人：</td>
				<td><input type="text" name="settlementContacter" class="box"  maxlength='20' value='<%=corinfo.getSettlementContacter()%>' disabled></td>
				<td>&nbsp;&nbsp;信贷业务联系人：</td>
				<td><input type="text" name="loanContacter" class="box"  maxlength='20' value='<%=corinfo.getLoanContacter()%>' disabled></td>
				</TR>
				<TR>
				<%
					String s = "getCustomerManagerUserID("+sessionMng.m_lOfficeID+")";
				%>
				<td height="20">&nbsp;&nbsp;客户经理：
				<td><input name="customerManagerUserIDname" type="text"  value="<%=CMConstant.getManageName(finfo.getCustomerManagerUserID())%>"  class="box" disabled></td>
				<td>&nbsp;&nbsp;查询密码：</td>
				<td><input type="password" name="queryPassword" class="box"  maxlength='30' value='<%=finfo.getQueryPassword()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;建立关系日期：</td>
				<td><input name="inputDate" type="text"  value="<%=strinputdate%>"  class="box" disabled></td>
				<td> &nbsp;&nbsp;服务级别：</td>
				<td><input type="text" name="serviceLevel" class="box"  maxlength='5' value='<%=strevl%>' disabled></td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">业务信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
				<td width="19%" height="20" >&nbsp;&nbsp;开户办事处：</td>
				<td width="27%"><input name="officeID" type="text" disabled class="box"  value='<%=sessionMng.m_strOfficeName%>'  maxlength='30' ></td>
				<td width="18%">&nbsp;&nbsp;财务公司账号：</td>
				<td width="36%"><input name="account" type="text" class="box"  maxlength='10' value='<%=corinfo.getAccount()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;主要开户银行1：</td>
				<td><input type="text" name="bank1" class="box"  maxlength='10' value='<%=corinfo.getBank1()%>' disabled></td>
				<td>&nbsp;&nbsp;开户银行账号1：</td>
				<td><input type="text" name="extendAccount1" class="box"  maxlength='10' value='<%=corinfo.getExtendAccount1()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;主要开户银行2：</td>
				<td><input type="text" name="bank2" class="box"  maxlength='10' value='<%=corinfo.getBank2()%>' disabled></td>
				<td>&nbsp;&nbsp;开户银行账号2：</td>
				<td><input type="text" name="extendAccount2" class="box"  maxlength='10' value='<%=corinfo.getExtendAccount2()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;主要开户银行3：</td>
				<td><input type="text" name="bank3" class="box"  maxlength='10' value='<%=corinfo.getBank3()%>' disabled></td>
				<td>&nbsp;&nbsp;开户银行账号3：</td>
				<td><input type="text" name="extendAccount3" class="box"  maxlength='10' value='<%=corinfo.getExtendAccount3()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;贷款卡号：</td>
				<td><input type="text" name="loanCardNo" class="box"  maxlength='20' value='<%=corinfo.getLoanCardNo()%>' disabled></td>
				<td>&nbsp;&nbsp;贷款卡密码：</td>
				<td><input type="text" name="loanCardPwd" class="box"  maxlength='10' value='<%=corinfo.getLoanCardPwd()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;信用等级：</td>
				<td><input type="text" name="creditLevelID" class="box"  maxlength='8' value='<%=corinfo.getCreditLevelID()%>' disabled></td>
				<td>&nbsp;&nbsp;信用等级评级机构：</td>
				<td><input type="text" name="judgeLevelClient" class="box"  maxlength='15' value='<%=corinfo.getJudgeLevelClient()%>' disabled></td>
				</TR>
				<TR>
				<td height="20"  nowrap>&nbsp;&nbsp;信用等级评级日期：</td>
				<td><input type="text" name="creditLevelDate" class="box" value='<%=strcreditleveldate%>' disabled></td>
				<td>&nbsp;&nbsp;内部信用等级：</td>
				<td><input type="text" name="insideCreditLevel" class="box"  maxlength='8' value='<%=corinfo.getInsideCreditLevel()%>' disabled></td>
				</TR>
				<TR>
				<td height="20" >&nbsp;&nbsp;风险级别：</td>
				<td><input type="text" name="riskLevel" class="box"  maxlength='8' value='<%=corinfo.getRiskLevel()%>' disabled></td>
				<td>&nbsp;&nbsp;资质等级：</td>
				<td><input type="text" name="talentLevel" class="box"  maxlength='8' value='<%=corinfo.getTalentLevel()%>' disabled></td>
				</TR>
				<TR>
				<td height="20"  nowrap>&nbsp;&nbsp;信用等级评级分数：</td>
				<td><input type="text" name="assessMark" class="box"  maxlength='8' value='<%=corinfo.getAssessMark()%>' disabled></td>
				<td></td>
				<td></td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2">补充信息</td>
					<td width="17"><a class=lab_title3></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<TR>
					<td width="19%" height="20" nowap>印鉴启用时间：</td>
					<td><input type="text"  class="box" name="signStart"  value='<%=strsignstart%>' disabled></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				<td colspan="4"><hr></td>
				</tr>
				<tr>
					<td nowrap>导入印鉴：</td>
					<td colspan="3">
					<!-- <iframe src="/NASApp/iTreasury-ebank/attach/AttachFrame.jsp?lID=<%=finfo.getId()%>&lTypeID=<%=CMConstant.AttachParentType.SEAL%>&sCaption=<%=URLEncoder.encode("链接附件")%>&control=view&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe> -->						
						<iframe src="../attach/AttachFrame.jsp?lID=<%=finfo.getId()%>&lTypeID=100101&sCaption=<%=URLEncoder.encode("链接附件")%>&control=view&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
					</td>
				</TR>
				<tr>
				<td colspan="4"><hr></td>
				</tr>
				<TR>
					<td height="20"  nowrap>营业执照图像：</td>
					<td colspan="3">						
						<iframe src="../attach/AttachFrame.jsp?lID=<%=finfo.getId()%>&lTypeID=100102&sCaption=<%=URLEncoder.encode("链接附件")%>&control=view&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="businessLicensePic"></iframe>
					</td>
				</tr>
				<tr> 
				<td colspan="4"><hr></td>
				</tr>
				<tr>
					<td>其他：</td>
					<td colspan="3">						
						<iframe src="../attach/AttachFrame.jsp?lID=<%=finfo.getId()%>&lTypeID=100103&sCaption=<%=URLEncoder.encode("链接附件")%>&control=view&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="organizationPic"></iframe>
					</td>
				</TR>
				<tr>
				<td colspan="4"><hr></td>
				</tr>
				
				<TR>
				<%
					String sql11 = "select id ,Name as value from client_ExtendAttribute where AttributeID=11 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					
					if(!CMConstant.getFieldName(11, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" ><%=CMConstant.getFieldName(11, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute1" styleClass="select" sql="<%=sql11%>" value='<%=String.valueOf(corinfo.getExtendAttribute1())%>' disabled="true"/></td>
				<%
				}
					String sql12 = "select id ,Name as value from client_ExtendAttribute where AttributeID=12 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(12, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td><%=CMConstant.getFieldName(12, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute2" styleClass="select" sql="<%=sql12%>" value='<%=String.valueOf(corinfo.getExtendAttribute2())%>' disabled="true"/></td>
				
				<%
				}
				 %>
				</TR>
				
				<TR>
				<%
					String sql13 = "select id ,Name as value from client_ExtendAttribute where AttributeID=13 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(13, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" ><%=CMConstant.getFieldName(13, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute3" styleClass="select" sql="<%=sql13%>" value='<%=String.valueOf(corinfo.getExtendAttribute3())%>' disabled="true"/></td>
				<%
				}
					String sql14 = "select id ,Name as value from client_ExtendAttribute where AttributeID=14 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(14, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td><%=CMConstant.getFieldName(14, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute4" styleClass="select" sql="<%=sql14%>"  value='<%=String.valueOf(corinfo.getExtendAttribute4())%>' disabled="true"/></td>
				
				<%
				}
				 %>
				</TR>
				
				<TR>
				<%
					String sql15 = "select id ,Name as value from client_ExtendAttribute where AttributeID=15 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(15, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" ><%=CMConstant.getFieldName(15, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute5" styleClass="select" sql="<%=sql15%>" value='<%=String.valueOf(corinfo.getExtendAttribute5())%>' disabled="true"/></td>
				<%
				}
					String sql16 = "select id ,Name as value from client_ExtendAttribute where AttributeID=16 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(16, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td><%=CMConstant.getFieldName(16, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute6" styleClass="select" sql="<%=sql16%>" value='<%=String.valueOf(corinfo.getExtendAttribute6())%>' disabled="true"/></td>
				
				<%
				}
				 %>
				</TR>
				
				<TR>
				<%
					String sql17 = "select id ,Name as value from client_ExtendAttribute where AttributeID=17 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(17, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td height="20" ><%=CMConstant.getFieldName(17, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute7" styleClass="select" sql="<%=sql17%>" value='<%=String.valueOf(corinfo.getExtendAttribute7())%>' disabled="true"/></td>
				<%
				}
					String sql18 = "select id ,Name as value from client_ExtendAttribute where AttributeID=18 and statusid=1 and officeid = " + sessionMng.m_lOfficeID;
					if(!CMConstant.getFieldName(18, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				%>
				<td><%=CMConstant.getFieldName(18, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</td>
				<td><fs:select name="extendAttribute8" styleClass="select" sql="<%=sql18%>" value='<%=String.valueOf(corinfo.getExtendAttribute8())%>' disabled="true"/></td>
				<%
				}
				 %>
				</TR>
				
				 
				 
				 
				 
				 
				<%
					if(!CMConstant.getFieldName(21, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %> 
				<TR>
				
				<td height="20" ><p><%=CMConstant.getFieldName(21, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo1" cols="60" class="box" disabled><%=corinfo.getExtendInfo1()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(22, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(22, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo2" cols="60" class="box" disabled><%=corinfo.getExtendInfo2()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(23, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(23, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo3" cols="60" class="box" disabled><%=corinfo.getExtendInfo3()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(24, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(24, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo4" cols="60" class="box" disabled><%=corinfo.getExtendInfo4()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(25, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(25, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo5" cols="60" class="box" disabled><%=corinfo.getExtendInfo5()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(26, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(26, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo6" cols="60" class="box" disabled><%=corinfo.getExtendInfo6()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(27, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(27, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo7" cols="60" class="box" disabled><%=corinfo.getExtendInfo7()%></textarea></td>
				</TR>
				<%
					}
					if(!CMConstant.getFieldName(28, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
				 %>
				<TR>
				<td height="20" ><p><%=CMConstant.getFieldName(28, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
				<td colspan="3"><textarea name="extendInfo8" cols="60" class="box" disabled><%=corinfo.getExtendInfo8()%></textarea></td>
				</TR>
				<%
					}
				 %>
			</TABLE>
<%
				}
				else if(finfo.getClientBaseType().equals(CMConstant.ClientBaseType.NATURE))
				{
					if(c2 != null)
					{
						Iterator it2 = c2.iterator();
						while(it2.hasNext())
						{
							fnainfo =(NatureInfo)it2.next();
						}
					}
					String strbirthday = "";
					strbirthday = DataFormat.getDateString(fnainfo.getBirthday());
					String strinputdate = "";
					strinputdate = DataFormat.getDateString(finfo.getInputDate());
					String strjobdate="";
					strjobdate = DataFormat.getDateString(fnainfo.getJobCompetenceDate());
					String strevl = "";
					strevl = DataFormat.formatListLong(finfo.getServiceLevel());		
%>
					<br/>
					<table width=100% border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="1"><a class=lab_title1></td>
							<td class="lab_title2">基本资料</td>
							<td width="17"><a class=lab_title3></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
						<TR>
							<TD width="19%" height="20" >&nbsp;&nbsp;客户编号：</TD>
							<TD width="27%"><%=finfo.getCode()%></TD>
							<TD width="18%">&nbsp;&nbsp;开户办事处：</TD>
							<TD width="36%"><input type="text" name="officeID" class="box"  maxlength='30' value='<%=sessionMng.m_strOfficeName%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;客户中文名称：&nbsp;<a href=#></a></TD>
							<TD height="20" ><input name="name" type="text" disabled class="box" value='<%=finfo.getName()%>'  maxlength='30'></TD>
							<TD height="20" >&nbsp;</TD>
							<TD height="20" >&nbsp;</TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;客户外文名称：</TD>
							<TD><input type="text" name="engName" class="box"  maxlength='30' value='<%=finfo.getEngName()%>' disabled></Td>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;客户中文简称：</TD>
							<TD><input type="text" name="simpleName" class="box"  maxlength='30' value='<%=finfo.getSimpleName()%>' disabled></TD>
							<TD>&nbsp;&nbsp;客户中文别名：</TD>
							<TD><input type="text" name="name2" class="box"  maxlength='30' value='<%=finfo.getName2()%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;客户外文简称：</TD>
							<TD><input type="text" name="simpleEngName" class="box"  maxlength='30' value='<%=finfo.getSimpleEngName()%>' disabled></TD>
							<TD>&nbsp;&nbsp;客户外文别名：</TD>
							<TD><input type="text" name="engName2" class="box"  maxlength='30' value='<%=finfo.getEngName2()%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;性别：</TD>
							<td><select class='select' name="sex" disabled>
								<option value=1>男</option>
							<%
								if (fnainfo.getSex()==2)
								{
							%>
								<option value=2 selected>女</option>
							<%}else{%>							
								<option value=2>女</option>
							<%}%>
								</select></td>	
							<TD>&nbsp;&nbsp;出生日期：</TD>
							<TD><input type="text" name="birthday" class="box" value='<%=strbirthday%>' disabled></td>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;国籍：</TD>
							<TD><input type="text" name="country" class="box"  maxlength='30' value='<%=finfo.getCountry()%>' disabled></TD>
							<TD>&nbsp;&nbsp;籍贯：</TD>
							<TD><input type="text" name="nativePlance" class="box"  maxlength='30' value='<%=fnainfo.getNativePlance()%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;身份证号码：</TD>
							<TD><input type="text" name="identityCardNo" class="box"  maxlength='30' value='<%=fnainfo.getIdentityCardNo()%>' disabled></TD>
							<TD>&nbsp;&nbsp;护照号码：</TD>
							<TD><input type="text" name="passportNo" class="box"  maxlength='30' value='<%=fnainfo.getPassportNo()%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;户籍地址：</TD>
							<TD><input type="text" name="address" class="box"  maxlength='30' value='<%=fnainfo.getAddress()%>' disabled></TD>
							<TD>&nbsp;&nbsp;婚姻状况：</TD>
							<TD><input type="text" name="marriage" class="box"  maxlength='30' value='<%=fnainfo.getMarriage()%>' disabled></TD>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;民族：</TD>
							<TD><input type="text" name="folk" class="box"  maxlength='30' value='<%=fnainfo.getFolk()%>' disabled></TD>
							<td><select class='select' name="marriage" disabled>							
								<option value=1>是</option>
								<%
									if (fnainfo.getMarriage()==2)
									{
								%>
								   <option value=2 selected>否</option>
								<%}else{%>							
									<option value=2>否</option>
								<%}%>
							</select></td>
						</TR>
						<TR>
							<TD height="20" >&nbsp;&nbsp;教育水平：</TD>
							<td><input type="text" name="educationBackground" class="box"  maxlength='30' value='<%=fnainfo.getEducationBackground()%>' disabled></td>
							<td>&nbsp;&nbsp;毕业院校：</td>
							<td><input type="text" name="gratulateUniversity" class="box"  maxlength='30' value='<%=fnainfo.getGratulateUniversity()%>' disabled></td>
						</TR>
						<TR>
							<%
									String s = "getCustomerManagerUserID("+sessionMng.m_lOfficeID+")";
							%>
								<td height="20" colSpan="2">&nbsp;&nbsp;客户经理：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fs:magnifier form="form_1" cssClass="box" size="20" title="客户经理"   mainName="customerManagerUsername"  mainField="name"   value="<%=CMConstant.getName(finfo.getCustomerManagerUserID())%>" displayName="经理编号,经理姓名"  displayField="code,name"  sql="<%=s%>"    returnName="customerManagerUserID"  returnField="id"   disabled="true"/></td>
							<td>&nbsp;&nbsp;查询密码：</td>
							<td><input type="password" name="queryPassword" class="box"  maxlength='30' value='<%=finfo.getQueryPassword()%>' disabled></td>
						</TR>	
						<TR>
							<td height="20" >&nbsp;&nbsp;建立关系日期：</td>
							<td><input type="text" name="inputDate" class="box" value="<%=strinputdate%>" disabled>
							<td>&nbsp;&nbsp;服务级别：</td>
							<td><input type="text" name="serviceLevel" class="box"  maxlength="30" value="<%=strevl%>" disabled></td>
						</TR>
				</TABLE>
				<br/>
					<table width=100% border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="1"><a class=lab_title1></td>
							<td class="lab_title2">职业信息</td>
							<td width="17"><a class=lab_title3></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
						<TR>
							<td width="19%" height="20" >&nbsp;&nbsp;工作单位：</td>
							<td width="27%"><input type="text" name="company" class="box"  maxlength="30" value="<%=fnainfo.getCompany()%>" disabled></td>
							<TD width="18%">&nbsp;&nbsp;工作年限：</TD>
							<TD width="36%"><input type="text" name="serviceAge" class="box"  maxlength="30" value="<%=fnainfo.getServiceAge()%>" disabled></TD>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;职业：</td>
							<td><input type="text" name="employment" class="box"  maxlength='30' value='<%=fnainfo.getEmployment()%>' disabled></td>
							<td>&nbsp;&nbsp;职位：</td>
							<td><input type="text" name="position" class="box"  maxlength='30' value='<%=fnainfo.getPosition()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;任职资格批准时间：</td>
							<td><input type="text" name="jobCompetenceDate" class="box"  value="<%=strjobdate%>" disabled>
							<td>&nbsp;&nbsp;任职资格批准文号：</td>
							<td><input type="text" name="jobCompetenceNo" class="box"  maxlength="30" value="<%=fnainfo.getJobCompetenceNo()%>" disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;技术职称：</td>
							<td><input type="text" name="technology" class="box"  maxlength="30" value="<%=fnainfo.getTechnology()%>" disabled></td>
							<td>&nbsp;&nbsp;办公地址</td>
							<td><input type="text" name="officeAddress" class="box"  maxlength='30' value='<%=fnainfo.getOfficeAddress()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;办公电话：</td>
							<td><input type="text" name="officeTel" class="box"  maxlength='30' value='<%=fnainfo.getOfficeTel()%>' disabled></td>
							<td>&nbsp;&nbsp;传真：</td>
							<td><input type="text" name="officeFax" class="box"  maxlength='30' value='<%=fnainfo.getOfficeFax()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;移动电话：</td>
							<td><input type="text" name="mobilePhone" class="box"  maxlength='30' value='<%=fnainfo.getMobilePhone()%>' disabled></td>
							<td>&nbsp;&nbsp;电子邮箱：</td>
							<td><input type="text" name="mail" class="box"  maxlength='30'  value='<%=fnainfo.getMail()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;基本履历：</td>
							<td><input type="text" name="resume" class="box"  maxlength='30' value='<%=fnainfo.getResume()%>' disabled></td>
							<td>&nbsp;&nbsp;管理风格：</td>
							<td><input type="text" name="managementStyle" class="box"  maxlength='30' value='<%=fnainfo.getManagementStyle()%>' disabled></td>
						</TR>
				</TABLE>
				<br/>
					<table width=100% border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="1"><a class=lab_title1></td>
							<td class="lab_title2">家庭状况</td>
							<td width="17"><a class=lab_title3></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
						<TR>
							<td height="20" >&nbsp;&nbsp;健康状况：</td>
							<td><input type="text" name="health" class="box"  maxlength='30' value='<%=fnainfo.getHealth()%>' disabled></td>
							<td>&nbsp;&nbsp;收入情况：</td>
							<td><input type="text" name="income" class="box"  maxlength='30' value='<%=fnainfo.getIncome()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;个人资产及债务情况：</td>
							<td><input type="text" name="debtInfo" class="box"  maxlength='30' value='<%=fnainfo.getDebtInfo()%>' disabled></td>
							<td>&nbsp;&nbsp;个人司法纪录：</td>
							<td><input type="text" name="elisorRecord" class="box"  maxlength='30' value='<%=fnainfo.getElisorRecord()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;性格品行：</td>
							<td><input type="text" name="character" class="box"  maxlength='30' value='<%=fnainfo.getCharacter()%>' disabled></td>
							<td>&nbsp;&nbsp;爱好忌讳：</td>
							<td><input type="text" name="habit" class="box"  maxlength='30' value='<%=fnainfo.getHabit()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;家庭地址：</td>
							<td><input type="text" name="homeAddress" class="box"  maxlength='30' value='<%=fnainfo.getHomeAddress()%>' disabled></td>
							<td>&nbsp;&nbsp;家庭电话：</td>
							<td><input type="text" name="homeTel" class="box"  maxlength='30' value='<%=fnainfo.getHomeTel()%>' disabled></td>
						</TR>
						<TR>
							<td height="20" >&nbsp;&nbsp;家庭情况：</td>
							<td><input type="text" name="homeStatus" class="box"  maxlength='30' value='<%=fnainfo.getHomeStatus()%>' disabled></td>
							<td>&nbsp;&nbsp;负担家庭成员数目：</td>
							<td><input type="text" name="FamilyMember" class="box"  maxlength='30' value='<%=fnainfo.getFamilyMember()%>' disabled></td>
						</TR>
			</TABLE>
			<br/>
					<table width=100% border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="1"><a class=lab_title1></td>
							<td class="lab_title2">补充信息</td>
							<td width="17"><a class=lab_title3></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
						<TR>
							<td width="19%" height="20" >备注： </td>
							<td colspan="3"><input type="text" name="abstract" class="box" value='<%=fnainfo.getAbstract()%>' disabled></td>
						</tr>
						<tr>
							<td colspan="4"><hr></td>
						</tr>
						<tr>
							<td>证件图像链接：</td>
							<td colspan="3">						
								<iframe src="/NASApp/iTreasury-ebank/attach/AttachFrame.jsp?lID=<%=finfo.getId()%>&lTypeID=<%=CMConstant.AttachParentType.SEAL%>&sCaption=<%=URLEncoder.encode("链接附件")%>&control=view&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="certificatePic" ></iframe>
		                    </td>
						</TR>
						<tr>
							<td colspan="4"><hr></td>
						</tr>
						<%if(!CMConstant.getFieldName(31, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {%>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(31, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo1" cols="60" class="box" disabled><%=fnainfo.getExtendInfo1()%></textarea></td>
						</TR>
						<%}
						if(!CMConstant.getFieldName(32, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(32, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo2" cols="60" class="box" disabled><%=fnainfo.getExtendInfo2()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(33, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(33, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo3" cols="60" class="box" disabled><%=fnainfo.getExtendInfo3()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(34, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(34, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo4" cols="60" class="box" disabled><%=fnainfo.getExtendInfo4()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(35, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(35, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo5" cols="60" class="box" disabled><%=fnainfo.getExtendInfo5()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(36, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(36, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo6" cols="60" class="box" disabled><%=fnainfo.getExtendInfo6()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(37, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(37, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo7" cols="60" class="box" disabled><%=fnainfo.getExtendInfo7()%></textarea></td>
						</TR>
						<%
						}
						if(!CMConstant.getFieldName(38, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).equals("empty")) {
						 %>
						<TR>
							<td height="20" ><p><%=CMConstant.getFieldName(38, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>：</p></td>
							<td colspan="3"><textarea name="extendInfo8" cols="60" class="box" disabled><%=fnainfo.getExtendInfo8()%></textarea></td>
						</TR>
						<%
						}
						 %>
				</TABLE>
				<br/>
				</td>
				</tr>
				</table>
				
</form>
</body>
</html>

<% 	
				}
			}
		}	
		OBHtml.showOBHomeEnd(out);	
}catch(Exception ce){ce.printStackTrace();}
%>