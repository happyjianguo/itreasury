<%--
/**
 * �������ƣ�V854.jsp
 * ����˵����ϵͳ����-�û�����
 * �������ߣ�����
 * ������ڣ�2003��9��8��
 */
--%>

<!--Header start-->
<%@ page contentType="text/html;charset=gbk"%>
<jsp:useBean id="getData"
	class="com.iss.itreasury.system.privilege.util.GetData" scope="page" />
<%@ page
	import="java.util.*,com.iss.itreasury.util.*,com.iss.itreasury.system.bizlogic.EBankbean,com.iss.itreasury.ebank.privilege.dataentity.*,com.iss.itreasury.ebank.privilege.bizlogic.*,com.iss.itreasury.ebank.privilege.dao.*,com.iss.itreasury.ebank.privilege.util.*,com.iss.itreasury.ebank.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	response.setHeader("Cache-Control", "no-stored");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!--Header end-->
<%
	//�̶�����
	String strTitle = "[�����û�]";
	String strMethod = "Add";
	String strContext = request.getContextPath();
	try {
		/**
		 * isLogin start
		 */
		//��¼���
		/*if( sessionMng.isLogin() == false)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
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

		//������
		HttpServletRequest req = getData.setValue(request);

		////�������Ϊ�ı���Ҷ�ˢ�£���ס������Ϣ
		long lUserIDForCur = -1;
		if (request.getParameter("UserID") != null
				&& request.getParameter("UserID").length() > 0) {
			lUserIDForCur = Long.parseLong(request
					.getParameter("UserID"));
		}
		Log.print("lUserIDForCur ID " + lUserIDForCur);

		EBankbean userAdmin = new EBankbean();
		if (lUserIDForCur != -1) {
			OB_UserInfo pi = userAdmin.findUserInfoByID(lUserIDForCur);
			/* �������б��������� */
			request.setAttribute("UserInfo", pi);
		}
		//
		Collection cForCur = userAdmin
				.findGroupsByClient(sessionMng.m_lClientID);
		/* �������б��������� */
		request.setAttribute("GroupsOfClient", cForCur);

		String strUserName = "";
		String strLogin = "";
		String strPassword = "";
		String strRePassword = "";
		long lCurrencyID = -1;
		long lUserID = -1;
		long nIsVirtualUser = 0;
		long nSaid = -1;

		Collection c = (Collection) request
				.getAttribute("GroupsOfClient");

		String isRepeat = (String) (request.getAttribute("isRepeat"));

		System.out.println("=-==========in view page  isRepeat is   : "
				+ isRepeat);

		if (isRepeat != null && isRepeat.equals("1")) {
%>
<script language="JavaScript">
		alert("������ĵ�¼�����Ѿ����ڣ�����������!");
		</script>
<%
	}

		OB_UserInfo pi = (OB_UserInfo) request.getAttribute("UserInfo");
		HashMap hm = new HashMap();
		if (pi != null) {
			strMethod = "Modify";
			strTitle = "�޸��û���Ϣ";
			strUserName = pi.getSName();
			strLogin = pi.getSLoginNo();
			nIsVirtualUser = pi.getNIsVirtualUser();
			strPassword = pi.getSPassword();
			nSaid = pi.getNSaid();

			Log.print(nSaid + "Password : " + pi.getSPassword());
			lCurrencyID = pi.getNCurrencyId();
			lUserID = pi.getId();
			
			Vector vGroupID = new Vector();
			vGroupID = (Vector) request.getAttribute("vGroupID");

			if (vGroupID != null && vGroupID.size() > 0)
				for (int i = 0; i < vGroupID.size(); i++) {
					hm.put(vGroupID.elementAt(i), pi.getSName());
				}
		}

		////�������Ϊ�ı���Ҷ�ˢ�£���ס������Ϣ
		String strTemp = "";
		strTemp = (String) req.getAttribute("UserName");
		if (strTemp != null && strTemp.length() > 0) {
			strUserName = strTemp;
		}
		strTemp = (String) req.getAttribute("LoginNoHidden");
		if (strTemp != null && strTemp.length() > 0) {
			strLogin = strTemp;
		}
		strTemp = (String) req.getAttribute("Password");
		if (strTemp != null && strTemp.length() > 0) {
			strPassword = strTemp;
		}
		strTemp = (String) req.getAttribute("Repassword");
		if (strTemp != null && strTemp.length() > 0) {
			strRePassword = strTemp;
		}
		strTemp = (String) req.getAttribute("UserID");
		if (strTemp != null && strTemp.length() > 0) {
			lUserID = Long.parseLong(strTemp);
		}
		//
		strTemp = (String) req.getAttribute("Currency");
		if (strTemp != null && strTemp.length() > 0) {
			lCurrencyID = Long.parseLong(strTemp);
		}
		lCurrencyID = Constant.CurrencyType.RMB;
		OBHtml.showOBHomeHead(out, sessionMng, strTitle,
				OBConstant.ShowMenu.YES);
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />

<form name="form1" method=post>
	<input type="hidden" name="methodValue" value="<%=strMethod%>">
	<input type="hidden" name="UserID" value="<%=lUserID%>">
	<input type="hidden" name="LoginNoHidden" value="">
	
				<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�û�����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
				
			</td>
		</tr>
		
	</table>
	<br/>
	<table width=100% border="0" cellspacing="0" cellpadding="0"
		align="">
	
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					
					
					<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �û�</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=80% border="0" cellspacing="0" cellpadding="0"
		class=normal align="">
		<tr>
			<td>&nbsp;&nbsp;�û����ƣ�</td>
			<td>
				<input type="text" class="box" name="UserName" size="19" maxlength="50"
					value="<%=strUserName%>" onkeyup="halfTurnFull(this,'UserName')">
			</td>
			<td>
				<%
					if (strMethod.equals("Add")) {
				%>
				&nbsp;&nbsp;��¼���ƣ�
			</td>
			<td>
				<input type="text" class="box" name="LoginNo" size="19" maxlength="20"
					value="<%=strLogin%>" onkeyup="halfTurnFull(this,'LoginNo')">
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;��¼���룺
			</td>
			<td>
				<input type="password" class="box" name="Password" size="20" maxlength="20" onpaste="return false" value="<%=strPassword%>">
			</td>
			<td>�ٴ��������룺</td>
			<td>
				<input type="password" class="box"  name="Repassword" size="20" maxlength="20" onpaste="return false"
					value="<%=strRePassword%>">
				<%
					} else {
				%>
				��¼���ƣ�
				<input type="text" class="box" name="LoginNo" size="20" maxlength="20"
					value="<%=strLogin%>" disabled>
				<%
					}
				%>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;���֣� &nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<%
					OBHtmlCom.showCurrencyList(out, "Currency1",
								"class='box' onchange=javascript:changeSAOwnedAccount(); disabled",
								lCurrencyID);
				%>
				<input type="hidden"  name="Currency" value="<%=lCurrencyID%>" />
				<div style="display: none;">
				<input name="nIsVirtualUser"  type="checkbox" value="1"
					<%if (nIsVirtualUser == 1){%> checked <%;}%>>
				�����û�
				</div>
			</td>
		</tr>
		<tr height=15>
			<td></td>
		</tr>
	</table>
	<br>
<table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td style="width:150px"class="lab_title2">�˺�Ȩ�޹���</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	
	<table width=80% border="0" cellspacing="0" cellpadding="0"
		class="normal" align="">
		<tr>
			<td colspan="3" height="1"></td>
		</tr>
		<%
			
			Iterator it = null;
				AccountInfo ai = new AccountInfo();
				EBankbean ua = new EBankbean();
				UserBean bean = new UserBean();
				Collection cAccountOfSA = ua
						.findAccountBySA(sessionMng.m_lClientID, lCurrencyID,
								sessionMng.m_lSAID);
				Collection cAccountOfUser = bean.findAccountByUser(lUserID,
						lCurrencyID);
				
				Collection cAccountOfUserQuery = bean.findAccountByUserQuery(lUserID,lCurrencyID);
				
				HashMap hAccountOfUser = new HashMap();
				HashMap hAccountOfUserQuery = new HashMap();
				Log.print("User ID : " + lUserID);
				Log.print("cAccountOfUser : " + cAccountOfUser);
				Log.print("cAccountOfUserQuery : " + cAccountOfUserQuery);
				if (cAccountOfUser != null) {
					it = cAccountOfUser.iterator();
					while (it.hasNext()) {
						ai = (AccountInfo) it.next();
						hAccountOfUser
								.put(ai.m_strAccountNo, ai.m_strAccountNo);
					}
				}
				if(cAccountOfUserQuery!=null)
				{
					it = cAccountOfUserQuery.iterator();
					while(it.hasNext())
					{
						ai = (AccountInfo)it.next();
						hAccountOfUserQuery.put(ai.m_strAccountNo,ai.m_strAccountNo);
					}
				}
				
				if (cAccountOfSA != null) {
					it = cAccountOfSA.iterator();
					
					out.println("<tr class='ItemBody'>");
					out.println("<td class='ItemBody' align='center'>�˺�</td>");
					out.println("<td class='ItemBody' align='center'>����Ȩ��");
					out.println("<INPUT name='checkalloperate'  type='checkbox' value='checkboxoperate' class='ItemBody' onclick='SelectAllOperate()'>");
					out.println("</td>");
					out.println("<td class='ItemBody' align='center'>�鿴Ȩ��");
					out.println("<INPUT name='checkallquery'  type='checkbox' value='checkboxquery' class='ItemBody' onclick='SelectAllQuery()'>");
					out.println("</td>");
					out.println("</tr>");
					while (it.hasNext()) {
						ai = (AccountInfo) it.next();
						for(int i=0;i<=2;i++)
						{
							if(i==0)
							{
								out.println("<tr class='ItemBody'>");
								out.println("<td class='ItemBody' align='center'>"+ai.m_strAccountNo+"</td>");
							}
							if(i==1)
							{
								out.println("<td class='ItemBody' align='center'>");
								if(hAccountOfUser.get(ai.m_strAccountNo) != null)
								{
									out.println("<input type='checkbox' class='ItemBody' name='CheckboxAccount' onclick='isCheckedAll3();' checked value='"
											+ ai.m_strAccountNo + "'>");
								}
								else
								{
									out.println("<input type='checkbox' class='ItemBody' name='CheckboxAccount'onclick='isCheckedAll3();' value='"
											+ ai.m_strAccountNo + "'>");
								}
								out.println("</td>");
							}
							if(i==2)
							{
								out.println("<td class='ItemBody' align='center'>");
								if(hAccountOfUserQuery.get(ai.m_strAccountNo) != null)
								{
									out.println("<input type='checkbox' class='ItemBody' name='CheckboxAccountQuery' onclick='isCheckedAll4();' checked value='"
											+ ai.m_strAccountNo + "'>");
								}
								else
								{
									out.println("<input type='checkbox' class='ItemBody' name='CheckboxAccountQuery' onclick='isCheckedAll4();' value='"
											+ ai.m_strAccountNo + "'>");
								}
								out.println("</td>");
								out.println("</tr>");
							}
						}
					}
					
				}
		%>
		
	</table>
	<br>
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:200px;">�����˺�Ȩ�޹���</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	</table>
	<table width=80% border="0" cellspacing="0" cellpadding="0"
		class="normal" align="">
		<tr>
			<td colspan="3" height="1"></td>
		</tr>
		<%
				Iterator iterator = null;
				AccountInfo eBankInfo = new AccountInfo();
				EBankbean eBankBean = new EBankbean();
				UserBean userBean = new UserBean();
				Collection eBankAccountOfSA = eBankBean.findEbankAccountBySA(sessionMng.m_lClientID,lCurrencyID);
				Collection eBankAccountOfUserOperate = userBean.findEbankAccountByUserOperate(lUserID,lCurrencyID);
				Collection eBankAccountOfUserQuery = userBean.findEbankAccountByUserQuery(lUserID,lCurrencyID);
				Collection eBankAccountOfSelect = eBankBean.findEbankAccountBySelect(sessionMng.m_lClientID,lCurrencyID);
				HashMap hEbankAccountOfUserOperate = new HashMap();
				HashMap hEbankAccountOfUserQuery = new HashMap();
				
				if(eBankAccountOfUserOperate!=null)
				{
					iterator=eBankAccountOfUserOperate.iterator();
					while(iterator.hasNext())
					{
						eBankInfo=(AccountInfo)iterator.next();
						hEbankAccountOfUserOperate.put(eBankInfo.m_strAccountNo,eBankInfo.m_strAccountNo);
						
						
					}
				}
				if(eBankAccountOfUserQuery!=null)
				{
					iterator=eBankAccountOfUserQuery.iterator();
					while(iterator.hasNext())
					{
						eBankInfo=(AccountInfo)iterator.next();
						hEbankAccountOfUserQuery.put(eBankInfo.m_strAccountNo,eBankInfo.m_strAccountNo);
					}
				}
				if(eBankAccountOfSA!=null)
				{
					iterator=eBankAccountOfSA.iterator();
					out.println("<tr class='ItemBody'>");
					out.println("<td class='ItemBody' align='center'>�˺�</td>");
					out.println("<td class='ItemBody' align='center'>����Ȩ��");
					out.println("<INPUT name='checkAllEbankOperate'  type='checkbox' value='checkboxEbankOperate' class='ItemBody' onclick='SelectAllEbankOperate()'>");
					out.println("</td>");
					out.println("<td class='ItemBody' align='center'>�鿴Ȩ��");
					out.println("<INPUT name='checkAllEbankQuery'  type='checkbox' value='checkboxEbankQuery' class='ItemBody' onclick='SelectAllEbankQuery()'>");
					out.println("</td>");
					out.println("</tr>");
					while(iterator.hasNext())
					{
						eBankInfo =(AccountInfo) iterator.next();
						
						for(int i=0;i<=2;i++)
						{
							if(i==0)
							{
								out.println("<tr class='ItemBody'>");
								out.println("<td class='ItemBody' align='center'>"+eBankInfo.m_strAccountNo+"</td>");
							
								
							}
							if(i==1)
							{
								
								out.println("<td class='ItemBody' align='center'>");
								if(eBankAccountOfSelect!=null)
								{
	
										if(hEbankAccountOfUserOperate.get(eBankInfo.m_strAccountNo)!=null)
										{
											out.println("<input type='checkbox' class='ItemBody' name='checkEbankAccountOperate'onclick='isCheckedAll();' checked value='"+ eBankInfo.m_strAccountNo + "' >");
									
										}
										else
										{
											out.println("<input type='checkbox' class='ItemBody' name='checkEbankAccountOperate' onclick='isCheckedAll();' value='"+ eBankInfo.m_strAccountNo + "'>");
										}							
								}
								else
								{
									out.println("<input type='checkbox' class='ItemBody' name='check'  disabled='disabled'>");
								}
								out.println("</td>");
							}
							if(i==2)
							{
								out.println("<td class='ItemBody' align='center'>");
								
									if(hEbankAccountOfUserQuery.get(eBankInfo.m_strAccountNo) != null)
									{
										out.println("<input type='checkbox' class='ItemBody' name='checkEbankAccountQuery' onclick='isCheckedAll2();' checked value='"+ eBankInfo.m_strAccountNo + "'>");
									
									}
									else
									{
										out.println("<input type='checkbox' class='ItemBody' name='checkEbankAccountQuery' onclick='isCheckedAll2();' value='"+ eBankInfo.m_strAccountNo + "'>");
									}
								
								
								out.println("</td>");
								out.println("</tr>");
								
							}
						}
					}
					
					
					
				}
				
				
		
		 %>
	</table>
	<br>
	
	<table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td style="width:150px"class="lab_title2"> �û���</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	<table width=80% border="0" cellspacing="0" cellpadding="0"
		class="normal" align="">
	
		
	
		<tr class='ItemBody'>
			<td colspan="3" height="1"></td>
		</tr>
		<%
			if (c != null) {
					it = c.iterator();
					int i = 0;
					while (it.hasNext()) {
						OB_GroupInfo gi = (OB_GroupInfo) it.next();
						if (i % 3 == 0) {
							out.println("<tr class='ItemBody'>");
						}
						out.println("<td class='ItemBody'>");
						if (hm.get(String.valueOf(gi.getId())) != null)
							out
									.println("<input type='checkbox' class='ItemBody' name='Checkbox' checked value='"
											+ gi.getId() + "'>");
						else
							out
									.println("<input type='checkbox' class='ItemBody' name='Checkbox' value='"
											+ gi.getId() + "'>");

						out.println(gi.getName());
						out.println("</td>");
						if (i % 3 == 2) {
							out.println("</tr>");
						}
						i++;
					}
					if (i % 3 == 1) {
						out.println("<td></td>");
						out.println("<td></td>");
						out.println("</tr>");
					}
					if (i % 3 == 2) {

						out.println("<td></td>");
						out.println("</tr>");
					}

				}
		%>
	</table>
	<br>
	<br>
	<br>
	<table width="774" border="0" cellspacing="0" cellpadding="0">
		<tr>
		</tr>
		<tr>
		</tr>
	</table>
	<table width=80% border="0" cellspacing="0" cellpadding="0">
		<tr height="25">
			<td width="300"></td>
			<td>

				<div align="right">
					<!--img src="/webob/graphics/button_tijiao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSubmit();"-->
					<input type="button" name="Submitv00204" value=" �� �� "
						class="button1" onClick="javascript:doSubmit();">
					<%
						if (strMethod.equals("Modify")) {
					%>
					<!--img src="/webob/graphics/button_shanchu.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:deleteUser();"-->
					<input type="button" name="Submitv00204" value=" ɾ �� "
						class="button1" <%if(nSaid<0) out.println("disabled");%>
						onClick="javascript:deleteUser();">
					<%
						}
					%>
					<!--a href="C853.jsp?method=view"> <img src="/webob/graphics/button_fanhui.gif" border="0" ></a-->
					<input type="button" name="Submitv00204" value=" �� �� "
						class="button1"
						onclick="window.location.href='C853.jsp?method=view'">&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" name="hnstrLogin" value="<%=strLogin%>">
</form>
<%
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
				1);
	}
	OBHtml.showOBHomeEnd(out);
%>
<script language="JavaScript">
function doSubmit()
{
	if( form1.UserName.value == null || form1.UserName.value.length == "" )
	{
		alert("�������û�����");
		return false;
	}
	if( "<%=strMethod%>" == "Add" )
	{
		if( form1.LoginNo.value == null || form1.LoginNo.value.length == "" )
		{
			alert("�������¼����");
			return false;
		}
		if( !isEnglish( form1.LoginNo.value ) )
		{
			alert("��¼���ư������Ļ�Ƿ��ַ�");
			return false;
		}
		if( form1.Password.value == null || form1.Password.value.length == ""  )
		{
			alert("�������¼����");
			return false;
		}
		if( form1.Repassword.value == null || form1.Repassword.value.length == "" )
		{
			alert("���ٴ������¼����");
			return false;
		}
		 
		if( Trim(form1.Password.value)=="")
		{
			alert("��¼���벻��Ϊ�գ�����������");
			return false;
		}
		if( Trim(form1.Repassword.value)=="")
		{
			alert(" �ٴ��������벻��Ϊ�գ�����������");
			return false;
		}
		
		var whitespace = new String(" \t\n\r");
		 var s = new String(form1.Password.value);
		 var s1 = new String(form1.Repassword.value);
		 if (whitespace.indexOf(s.charAt(0)) != -1 || whitespace.indexOf(s.charAt(s.length-1)) != -1)
		 {
		 	alert("��¼���벻���Կո�ͷ���β��������¼��");
		 	return ;
		 }
		 if (whitespace.indexOf(s1.charAt(0)) != -1 || whitespace.indexOf(s1.charAt(s.length-1)) != -1)
		 {
		 	alert("�ٴ��������벻���Կո�ͷ���β��������¼��");
		 	return ;
		 }
		//ȫ�� 2010-5-14
		if(form1.Password.value !=  form1.Repassword.value)
		{
			alert("���һ�κ͵ڶ�����������������ȫһ��");
			return false;
		}
		
		if(form1.Password.value.length < <%=Constant.PASSWORD_MIN_LENGTH%> || form1.Password.value.length > <%=Constant.PASSWORD_MAX_LENGTH%>)
		{
            alert("��������볤�ȱ�����6��20֮��");
            return false;
         }
	}
	if( form1.Currency.value == -1 )
	{
		alert("��ѡ�����");
		return false;
	}
	
		/*********************2006��10��10�����***************************************/	
	/**  ���Ϊ����
	 * ��Ѻ����滻Ϊ���ַ�	aa 
	 * �޸����ڣ�2006��10��10��
    */		
   
    var userName = document.form1.UserName.value;
    var rule=/[\u4e00-\u9fa5]/g;
    var s=userName.replace(rule,'aa');

    //�û������Ƶĳ��ȼ���
    if (!(s.length>=0 && s.length <= 50))
    {
    	alert("�û������Ƶĳ��ȱ���С��50���ַ���");
        document.form1.UserName.focus();
		return false;
    }
	/*********************�޸���� ***************************************/
	var isCheck = 0;
	if(document.form1.CheckboxAccount!=undefined)
	{
		
		for (var i = 0; i < document.form1.CheckboxAccount.length; i++) {
			if(document.form1.CheckboxAccount[i].type=="checkbox") {
				if (document.form1.CheckboxAccount[i].checked==true)
				{
					 isCheck = 1;
				}
			}
		}
	}
	if(document.form1.Checkbox!=undefined)
	{
		for (var i = 0; i < document.form1.Checkbox.length; i++) {
			if(document.form1.Checkbox[i].type=="checkbox") {
				if (document.form1.Checkbox[i].checked==true)
				{
					 isCheck = 1;
				}
			}
		}
	}
	if (isCheck ==0)
	{
		alert("�빴ѡ�����˺Ż��û��飡");
		return false;
	}
	
	form1.action = "<%=strContext%>/system/C853.jsp?method="+form1.methodValue.value;
	form1.submit();
}
function deleteUser()
{
	if(confirm("�Ƿ�ɾ��?"))
	{
	  form1.action = "<%=strContext%>/system/C853.jsp?method=Delete";
	  form1.submit();
	}
}

function isEnglish( d_english )
{
	var allValid = true;
	var len = d_english.length;
	for (i = 0;  i < len;  i++)
	{
		ch = d_english.charCodeAt(i);
		if ((ch >= 48 && ch <=57 )||(ch >=65 && ch <=90)||(ch >=97 && ch <= 122)||(ch == 45) || (ch==46))
		//if (ch <= 255)
			allValid = true;
		else
		{
			allValid = false;
			break;
		}
	}
	return allValid;
}


function changeSAOwnedAccount()
{
	/*
	if( form1.Password.value !=  form1.Repassword.value )
	{
		alert("���һ�κ͵ڶ�����������������ȫһ��");
		return false;
	}
	*/
	form1.LoginNoHidden.value=form1.LoginNo.value;
	form1.action = "<%=strContext%>/system/V854.jsp?method="+form1.methodValue.value;
	form1.submit();
}
/*

==================================================================

LTrim(string):ȥ����ߵĿո�

==================================================================

*/

function LTrim(str)

{

    var whitespace = new String(" \t\n\r");

    var s = new String(str);

    

    if (whitespace.indexOf(s.charAt(0)) != -1)

    {

        var j=0, i = s.length;

        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)

        {

            j++;

        }

        s = s.substring(j, i);

    }

    return s;

}

 

/*

==================================================================

RTrim(string):ȥ���ұߵĿո�

==================================================================

*/

function RTrim(str)

{

    var whitespace = new String(" \t\n\r");

    var s = new String(str);

 

    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)

    {

        var i = s.length - 1;

        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)

        {

            i--;

        }

        s = s.substring(0, i+1);

    }

    return s;

}

 

/*

==================================================================

Trim(string):ȥ��ǰ��ո�

==================================================================

*/

function Trim(str)

{

    return RTrim(LTrim(str));

}
function SelectAllEbankOperate()
{
	var f = document.form1;
	var c;
	if(f.checkAllEbankOperate.checked)
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "checkEbankAccountOperate")
			{
				f.elements[c].checked=true;
			}
		}
	}
	else
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "checkEbankAccountOperate")
			{
				f.elements[c].checked=false;
			}
		}
	}
}
function isCheckedAll()
{
	var isCheck = true;
	for(var i=0;i<document.form1.checkEbankAccountOperate.length;i++)
	{
		if(document.form1.checkEbankAccountOperate[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.checkAllEbankOperate.checked = true;
	else
		document.form1.checkAllEbankOperate.checked = false;
		
	if(document.form1.checkEbankAccountOperate.length == undefined){
		document.form1.checkAllEbankOperate.checked = document.form1.checkEbankAccountOperate.checked;
	}
}

function isCheckedAll2()
{
	var isCheck = true;
	for(var i=0;i<document.form1.checkEbankAccountQuery.length;i++)
	{
		if(document.form1.checkEbankAccountQuery[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.checkAllEbankQuery.checked = true;
	else
		document.form1.checkAllEbankQuery.checked = false;
	
	if(document.form1.checkEbankAccountQuery.length == undefined){
		document.form1.checkAllEbankQuery.checked = document.form1.checkEbankAccountQuery.checked;
	}	
		
		
}




function SelectAllEbankQuery()
{
	var f = document.form1;
	var c;
	if(f.checkAllEbankQuery.checked)
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "checkEbankAccountQuery")
			{
				f.elements[c].checked=true;
			}
		}
	}
	else
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "checkEbankAccountQuery")
			{
				f.elements[c].checked=false;
			}
		}
	}
}
function SelectAllOperate()
{
	var f = document.form1;
	var c;
	if(f.checkalloperate.checked)
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "CheckboxAccount")
			{
				f.elements[c].checked=true;
			}
		}
	}
	else
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "CheckboxAccount")
			{
				f.elements[c].checked=false;
			}
		}
	}
}

function isCheckedAll3()
{
	var isCheck = true;
	for(var i=0;i<document.form1.CheckboxAccount.length;i++)
	{
		if(document.form1.CheckboxAccount[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.checkalloperate.checked = true;
	else
		document.form1.checkalloperate.checked = false;
    if(document.form1.CheckboxAccount.length == undefined){
		document.form1.checkalloperate.checked = document.form1.CheckboxAccount.checked;
	}	
		
}





function SelectAllQuery()
{
	var f = document.form1;
	var c;
	if(f.checkallquery.checked)
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "CheckboxAccountQuery")
			{
				f.elements[c].checked=true;
			}
		}
	}
	else
	{
		for(c=0; c < f.elements.length; c++)
		{
			if(f.elements[c].name == "CheckboxAccountQuery")
			{
				f.elements[c].checked=false;
			}
		}
	}
}

function isCheckedAll4()
{
	var isCheck = true;
	for(var i=0;i<document.form1.CheckboxAccountQuery.length;i++)
	{
		if(document.form1.CheckboxAccountQuery[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.checkallquery.checked = true;
	else
		document.form1.checkallquery.checked = false;
	 if(document.form1.CheckboxAccountQuery.length == undefined){
		document.form1.checkallquery.checked = document.form1.CheckboxAccountQuery.checked;
	}	
		
}

</script>

<%@ include file="/common/SignValidate.inc"%>