<%--
/**
 * �������ƣ�V852.jsp
 * ����˵����ϵͳ����-�û������
 * �������ߣ�����
 * ������ڣ�2003��9��4��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.system.bizlogic.EBankbean,
				com.iss.itreasury.ebank.privilege.dataentity.*,
				com.iss.itreasury.ebank.privilege.bizlogic.*,
				com.iss.itreasury.ebank.privilege.dao.*,
				com.iss.itreasury.ebank.privilege.util.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="getData" class="com.iss.itreasury.system.privilege.util.GetData" scope="page"/>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
//response.setHeader("Cache-Control","no-stored");
//response.setHeader("Pragma","no-cache");
//response.setDateHeader("Expires",0);
%>
<!--Header end-->
<%
//�̶�����
String strTitle = "[�û������]";
String strContext = request.getContextPath();//http://xxxx/../cpob
String strMethod = "Add";
int size=0;
try
{
	/**
	* isLogin start
	*/
	//��¼���
	if( sessionMng.isLogin() == false)
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
	}
	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	OBFinanceInstrDao dao = new OBFinanceInstrDao();
	long lIsCtrl = dao.getIsControlChild(sessionMng.m_strClientCode);
	System.out.println("sessionMng.m_lClientID="+sessionMng.m_strClientCode);

    Vector v = (Vector) request.getAttribute("PrivilegesOfTopMenu");
	Log.print(" PrivilegesOfTopMenu size is " + v.size());
	Collection c = null;
	// 
	String strGroupName = "";
	String strLevel = "h";
	long lGroupID = -1;
	size=v.size();
	
	//Vector cPrivilege = (Vector)request.getAttribute("cPrivilege");
	
	HashMap hm = new HashMap();
	Vector  cPrivilege = null;
	cPrivilege = (Vector)request.getAttribute("cPrivilege");

	OB_GroupInfo gi = null;
	Log.print("gi " + gi);
	 gi = (OB_GroupInfo)  request.getAttribute("GroupInfo");
	if( gi != null )
	{

		strGroupName = gi.getName();
		//strLevel = gi.strLevel;
		lGroupID = gi.getId();
		//Log.print("gi pvg.size is " + gi.cPrivilege.size() );
		Iterator it = cPrivilege.iterator();
		strMethod = "Modify";
		
		while( it.hasNext() )
		{
			OB_PrivilegeInfo pi = (OB_PrivilegeInfo) it.next();
			hm.put( pi.getPrivilegeNo(), pi.getMenuUrl() );
		}
		
	}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<form method="post" name="form1" >
<input type="hidden" name="GroupID" value="<%= lGroupID %>" >
<input type="hidden" name="methodValue" value="<%= strMethod %>" >
<input type="hidden" name="vsize" value="<%=v.size()%>" >

    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�û�������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  
<br/>
	 <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �û���</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="">
	<tr > 
       <td  height="10" ></td>
    </tr>
	<tr>
	<td height="25" >
     &nbsp;<font color="red">*&nbsp;</font>�û������� ��<input type="text" class="box" name="GroupName" value="<%= strGroupName %>" maxlength="100" size="20"> &nbsp;&nbsp;&nbsp;
	 </td>
	</tr>
	<tr > 
       <td  height="10" ></td>
    </tr>
</table>
		<% 	
	for(int n=0;n<v.size();n++)	
	{
		//c = (Collection)(v.elementAt(4));
		//if( c != null )
		//{
		
		c = (Collection)(v.elementAt(n));
		if(null != c)
		{
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			pvg = (OB_PrivilegeInfo) it.next();
%>
<br/>
<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"><%=pvg.getName()%></td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
    <%//} %>  
    
<%  
		
		if( c != null )
		{
%>
<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="" valign="top">
		<tr>
			<td width = "50%" align = "right" valign="top">ȫѡ&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input type = "checkbox" name = "checkall<%=n+1%>" onclick = "docheckall(<%=n+1%>)" ></td>
		</tr>
		<tr>
			<td><hr></td>
			<td><hr></td>
			<td><hr></td>
		</tr>
        
<%
			//Iterator it = c.iterator();
			//OB_PrivilegeInfo pvg = null;
			//int i = 0;
			
			while( it.hasNext() )
			{
			
			Log.print("=========sessionMng.m_lClientID="+sessionMng.m_lClientID);
				pvg = (OB_PrivilegeInfo) it.next();
				/**
				// ������ǹɷݹ�˾�ͼ��Ź�˾�����ܿ��������ѯ
				/**
				if( sessionMng.m_lClientID != 3 && sessionMng.m_lClientID != 1 && sessionMng.m_lClientID != 15 && sessionMng.m_lClientID != 2 && sessionMng.m_lClientID != 20 && sessionMng.m_lClientID != 21 && pvg.getPrivilegeNo().indexOf("6-1-102")>= 0 )
					continue;				
				// ������ǹɷݹ�˾�ͼ��Ź�˾�����ܿ���������λ�˻����
				if( sessionMng.m_lClientID != 3 && sessionMng.m_lClientID != 1 && sessionMng.m_lClientID != 15 && pvg.getPrivilegeNo().indexOf("6-1-102-100")>= 0 )
					continue;
				// ������ǹɷݹ�˾�ͼ��Ź�˾�����ܿ���ÿ���ʽ�䶯����ܱ�
				if( sessionMng.m_lClientID != 3 && sessionMng.m_lClientID != 1 && sessionMng.m_lClientID != 2 && sessionMng.m_lClientID != 20 && sessionMng.m_lClientID != 21 && pvg.getPrivilegeNo().indexOf("6-1-102-101")>= 0 )
					continue;
				*/
				
				out.println("<tr>");
				out.println("<td width='50%' height='2' >");
				
				if( pvg.getPlevel() != 3 )
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("<strong>"+pvg.getName()+"</strong>");
				}
				else
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println(pvg.getName());
				}
				out.println("</td>");
				
				out.println("<td width='50%' height='2' >");
				if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
				{
					if( hm.get(pvg.getPrivilegeNo()) != null )
					{
						Log.print(" pvg no is : " + pvg.getPrivilegeNo());
						out.println("<input type='checkbox' name='checkbox"+(n+1)+"' onclick = isCheckedAll("+(n+1)+") checked value='"+ pvg.getPrivilegeNo() + "' >");
					}
					else
					{
						out.println("<input type='checkbox' name='checkbox"+(n+1)+"' onclick = isCheckedAll("+(n+1)+") value='"+ pvg.getPrivilegeNo() + "' >");
					}
				}
				out.println("</td>");

				out.println("</tr>");
				i++;
			}
		}
		%>
     </table>
		
		<%
		}
}

%>		
		
     <%--
	 		<% 		
		c = (Collection)(v.elementAt(4));
		if( c != null )
		{
%>
<br>
<table width=80% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �ʽ����</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<%} %>
     <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal align="">
<%
		c = (Collection)(v.elementAt(1));
		if( c != null )
		{
%>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        
     	<tr>
			<td width = "50%" align = "right">ȫѡ&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input type = "checkbox" name = "checkall2" onclick = "docheckall(2)" ></td>
		</tr>
		<tr><td><hr></td><td><hr></td><td><hr></td></tr>
<%
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			while( it.hasNext() )
			{
				pvg = (OB_PrivilegeInfo) it.next();
				
				if (lIsCtrl == 1 || (lIsCtrl != 1 && !pvg.getPrivilegeNo().equals("6-2-108") ))
				{
					
					out.println("<tr>");
					out.println("<td width='50%' height='2' >");
					
					if( pvg.getPlevel() != 3 )
					{
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.println("<strong>"+pvg.getName()+"</strong>");
					}
					else
					{
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.println(pvg.getName());
					}
					out.println("</td>");
					
					out.println("<td width='50%' height='2' >");
					if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
					{
						if( hm.get(pvg.getPrivilegeNo()) != null )
						{
							Log.print(" pvg no is : " + pvg.getPrivilegeNo());
							out.println("<input type='checkbox' name='checkbox2' checked value='"+ pvg.getPrivilegeNo() + "' >");
						}
						else
						{
							out.println("<input type='checkbox' name='checkbox2' value='"+ pvg.getPrivilegeNo() + "' >");
						}
					}
					out.println("</td>");
	
					out.println("</tr>");
					i++;
				}
			}
		}
%>		
	
     </table>
     		<% 		
		c = (Collection)(v.elementAt(4));
		if( c != null )
		{
%>
<br>
<table width="774" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �������</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<%} %>
  <table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="">
<%
		c = (Collection)(v.elementAt(2));
		if( c != null )
		{
%>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
			<td width = "50%" align = "right">ȫѡ&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input type = "checkbox" name = "checkall3" onclick = "docheckall(3)" ></td>
		</tr>
		<tr><td><hr></td><td><hr></td><td><hr></td></tr>
<%
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			while( it.hasNext() )
			{
				pvg = (OB_PrivilegeInfo) it.next();
				out.println("<tr>");
				out.println("<td width='50%' height='2' >");
				
				if( pvg.getPlevel() != 3 )
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("<strong>"+pvg.getName()+"</strong>");
				}
				else
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println(pvg.getName());
				}
				out.println("</td>");
				
				out.println("<td width='50%' height='2' >");
				if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
				{
					if( hm.get(pvg.getPrivilegeNo()) != null )
					{
						Log.print(" pvg no is : " + pvg.getPrivilegeNo());
						out.println("<input type='checkbox' name='checkbox3' checked value='"+ pvg.getPrivilegeNo() + "' >");
					}
					else
					{
						out.println("<input type='checkbox' name='checkbox3' value='"+ pvg.getPrivilegeNo() + "' >");
					}
				}
				out.println("</td>");

				out.println("</tr>");
				i++;
			}
		}

%>		
     </table>
			<% 		
		c = (Collection)(v.elementAt(4));
		if( c != null )
		{
%>
<br>
<table width="774" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> ϵͳ����</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<%} %>
     <table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="">
<% 		
		c = (Collection)(v.elementAt(3));
		if( c != null )
		{
%>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
			<td width = "50%" align = "right">ȫѡ&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input type = "checkbox" name = "checkall4" onclick = "docheckall(4)" ></td>
		</tr>
		<tr><td><hr></td><td><hr></td><td><hr></td></tr>
<%
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			while( it.hasNext() )
			{
				pvg = (OB_PrivilegeInfo) it.next();
				out.println("<tr>");
				out.println("<td width='50%' height='2' >");
				
				if( pvg.getPlevel() != 3 )
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("<strong>"+pvg.getName()+"</strong>");
				}
				else
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println(pvg.getName());
				}
				out.println("</td>");
				
				out.println("<td width='50%' height='2' >");
				if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
				{
					if( hm.get(pvg.getPrivilegeNo()) != null )
					{
						Log.print(" pvg no is : " + pvg.getPrivilegeNo());
						out.println("<input type='checkbox' name='checkbox4' checked value='"+ pvg.getPrivilegeNo() + "' >");
					}
					else
					{
						out.println("<input type='checkbox' name='checkbox4' value='"+ pvg.getPrivilegeNo() + "' >");
					}
				}
				out.println("</td>");

				out.println("</tr>");
				i++;
			}
		}

 %>		
     </table>
		<% 		
		c = (Collection)(v.elementAt(4));
		if( c != null )
		{
%>
<br>
<table width="774" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �ͻ�����</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<%} %>
     <table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<% 		
		c = (Collection)(v.elementAt(4));
		if( c != null )
		{
%>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
<%
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			while( it.hasNext() )
			{
				pvg = (OB_PrivilegeInfo) it.next();
				out.println("<tr>");
				out.println("<td width='50%' height='2' >");
				
				if( pvg.getPlevel() != 3 )
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("<strong>"+pvg.getName()+"</strong>");
				}
				else
				{
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println(pvg.getName());
				}
				out.println("</td>");
				
				out.println("<td width='50%' height='2' >");
				if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
				{
					if( hm.get(pvg.getPrivilegeNo()) != null )
					{
						Log.print(" pvg no is : " + pvg.getPrivilegeNo());
						out.println("<input type='checkbox' name='checkbox' checked value='"+ pvg.getPrivilegeNo() + "' >");
					}
					else
					{
						out.println("<input type='checkbox' name='checkbox' value='"+ pvg.getPrivilegeNo() + "' >");
					}
				}
				out.println("</td>");

				out.println("</tr>");
				i++;
			}
		}

 %>		
     </table>
     <% 		
		c = (Collection)(v.elementAt(4));
		if( false )
		{
%>
<br>
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> Ԥ��</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<%} %>
	 <table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<% 		
		c = (Collection)(v.elementAt(5));
		if(false )
		{
%>
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
<%
			Iterator it = c.iterator();
			OB_PrivilegeInfo pvg = null;
			int i = 0;
			while( it.hasNext() )
			{
				pvg = (OB_PrivilegeInfo) it.next();
				if(pvg.getPrivilegeNo().substring(0,3).equals("6-6")){
					out.println("<tr>");
					out.println("<td width='50%' height='2' >");
					if( pvg.getPlevel() != 3 )
					{
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.println("<strong>"+pvg.getName()+"</strong>");
					}
					else
					{
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.println(pvg.getName());
					}
					out.println("</td>");
					
					out.println("<td width='50%' height='2' >");
					if( pvg.getMenuUrl() != null && pvg.getMenuUrl().length() > 4 )
					{
						if( hm.get(pvg.getPrivilegeNo()) != null )
						{
							Log.print(" pvg no is : " + pvg.getPrivilegeNo());
							out.println("<input type='checkbox' name='checkbox' checked value='"+ pvg.getPrivilegeNo() + "' >");
						}
						else
						{
							out.println("<input type='checkbox' name='checkbox' value='"+ pvg.getPrivilegeNo() + "' >");
						}
					}
					out.println("</td>");

					out.println("</tr>");
					i++;
				}
			}
		}

 %>		
     </table>
     --%>

<table width=100% border="0" cellspacing="0" cellpadding="0" >
<br/>

 <tr>
   <td>
        <div align="right">
			<!--img src="/webob/graphics/button_tijiao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSubmit();"-->
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:doSubmit();">
			<!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:location.href='C851.jsp?method=view';"-->
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onclick="window.location.href='C851.jsp?method=view'">&nbsp;&nbsp;&nbsp;&nbsp;
			
		</div>
   </td>
  </tr>
</table>
  </td>
  </tr>
  
</table>
</form>
<%
}
catch (Exception e)
{
	e.printStackTrace();
	OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
%>
<script language="JavaScript">

function doSubmit()
{

   /*	ԭУ���û������ƵĺϷ��� */
	if (!InputValid(form1.GroupName, 1, "textarea", 1, 0, 100,"�û�������"))    
	{
		return false;
	}
	
	/*********************2006��10��10�����***************************************/	
	/**  ���Ϊ����
	 * ��Ѻ����滻Ϊ���ַ�	aa 
	 * �޸����ڣ�2006��10��10��
    */		
   
    var groupName = document.form1.GroupName.value;
    var rule=/[\u4e00-\u9fa5]/g;
    var s=groupName.replace(rule,'aa');

    //�û������Ƶĳ��ȼ���
    if (!(s.length>=0 && s.length <= 100))
    {
    	alert("�û������Ƶĳ��ȱ���С��100���ַ���");
        document.form1.GroupName.focus();
		return false;
    }
	/*********************�޸���� ***************************************/
	var isCheck = 0;
	for (var i = 0; i < document.form1.elements.length; i++) {
		if(document.form1.elements[i].type=="checkbox") {
			if (document.form1.elements[i].checked==true)
			{
				 isCheck = 1;
			}
		}
	}
	if (isCheck ==0)
	{
		alert("�빴ѡ�û�Ȩ�ޣ�");
		return false;
	}
	
	form1.action = "<%= strContext %>/system/C851.jsp?method="+form1.methodValue.value;
	form1.submit();
}
function deleteGroup()
{
	
	form1.action = "<%= strContext %>/system/C851.jsp?method=Delete";
	form1.submit();
}

function isCheckedAll(i)
{
    var f = document.form1;
	var checkall;
	var checkbox;
	var isCheck = true;
	var sign = true;
	switch ( i )
	{
	<%
	for(int m=1;m<=size;m++)
	{
	%>
	case <%=m%> : 
			checkall = f.checkall<%=m%>;
			checkbox = "checkbox<%=m%>"; 
			break;
	<%
	}
	%>
	}
	for(var c=0;c<f.elements.length;c++)
	{   
	    if(f.elements[c].name == checkbox&&f.elements[c].checked== false )
	    {   sign=f.elements[c].checked;
			isCheck = false;	
		}
	}
	if(isCheck)
		checkall.checked = true;
	else
		checkall.checked = false;
	if(checkbox.length == undefined){
		checkall.checked = sign;
	}	
}
function docheckall(i)
{
	var f = document.form1;
	var checkall;
	var checkbox;
	switch ( i )
	{
	<%
	for(int m=1;m<=size;m++)
	{
	%>
	case <%=m%> : 
			checkall = f.checkall<%=m%>;
			checkbox = "checkbox<%=m%>"; 
			break;
	<%
	}
	%>
	/*
		case 1 : 
			checkall = f.checkall1;
			checkbox = "checkbox1"; 
			break;
		case 2 : 
			checkall = f.checkall2;
			checkbox = "checkbox2"; 
			break;
		case 3 : 
			checkall = f.checkall3;
			checkbox = "checkbox3"; 
			break;
		case 4 : 
			checkall = f.checkall4;
			checkbox = "checkbox4"; 
			break;
	*/
	}
	for( var c = 0;c < f.elements.length; c++)
	{
	    if(f.elements[c].name == checkbox )
	    {
	       f.elements[c].checked=checkall.checked;
	    }
	}
}
</script> 

<%@ include file="/common/SignValidate.inc" %>