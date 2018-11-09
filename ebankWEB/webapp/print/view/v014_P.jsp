<%--
 ҳ������ ��v013_P.jsp
 ҳ�湦�� : ���ӻص���ӡ��������
 ��    �� ��zyyao
 ��    �� ��2007-8-3
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 java.net.URLEncoder,
				 java.util.ArrayList,
                 javax.servlet.*,
                 java.util.Iterator,
                 com.iss.itreasury.ebank.print.dataentity.QueryPrintInfo,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.settlement.ebankPrint.bizlogic.PrintEbankApplyBiz"%>
<%@ page import="com.iss.itreasury.settlement.ebankPrint.dataentity.PrintEbankApply"%>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common"%>

<%
	/* ����̶����� */
	String strTitle = "[��ӡ����鿴]";
%>	
<% String strContext = request.getContextPath();%>
<%
  try
  {
	   /* �û���¼��� */
	   if (sessionMng.isLogin() == false)
	   {
	       OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
	   	   out.flush();
	   	   return;
	   }
	
	   /* �ж��û��Ƿ���Ȩ�� */
	   if (sessionMng.hasRight(request) == false)
	   {
	   	   out.println(sessionMng.hasRight(request));
	   	   OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
	   	   out.flush();
	   	   return;
	   }

	  //��ʾ�ļ�ͷ
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
  		    
	  QueryTransactionInfo queryinfo = new QueryTransactionInfo();
	  queryinfo = (QueryTransactionInfo)request.getAttribute("QueryTransaction");
	  
	  Collection arrResult = (Collection)request.getAttribute("printresult");
	  
	  //��ҳ����
	  String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	  
	  int i = 0;
	  int countAll = 0;
%>		
<jsp:include flush="true" page="/ShowMessage.jsp" />

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />

<form name="frm" method="post">
<input name="strSuccessPageURL" type="hidden" >
<input name="strFailPageURL" type="hidden" >
<input type="hidden" name="strAction" >
<input type="hidden" name="_pageLoaderKey" value="<%= strPageLoaderKey %>">
<input type="hidden" name="lID" value="<%= queryinfo.getID() %>">
<input type="hidden" name="transno" value="<%= queryinfo.getTransNo() %>">
<input type="hidden" name="typeid" value="<%= queryinfo.getTransactionTypeID() %>">
<table width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">��ӡ����鿴</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
        </td>
      </tr>
    </table>
<br>
<table width="80%">
	 
	 <TR>
		<TD vAlign=bottom>
	  		<TABLE align="" border=0 width="100%" class=normal>
				<TR>
			  		<TD height=28>���ױ�ţ�</TD>
			  		<TD height=28><INPUT disabled class=box name="" size="20" value="<%= queryinfo.getTransNo().trim().length() > 0 ? queryinfo.getTransNo() : "" %>"></TD>
			  		<TD width="60%">&nbsp;</TD>
				</TR>
				<TR>
			  		<TD height=28>�������ͣ�</TD>
			  		<TD height=28><INPUT disabled class=box name="" size="20" value="<%= queryinfo.getTransactionTypeID() > 0 ? VOUConstant.TransType.getName(queryinfo.getTransactionTypeID()) : "" %>"></TD>
			  		<TD width="60%">&nbsp;</TD>
				</TR>
	  		</TABLE>
		</TD>
  	 </TR>
	 <TR>
		<TD>
	  		<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
				<tr>
					<TD class=ItemTitle height=17 ><FONT size=2><input type="checkbox" name="seleAll1"  disabled value="0" onclick="javascript:selectAll()"  ></FONT></TD>
          			<TD class=ItemTitle align="center" height=17  width="50%" ><FONT size=2>�������ӡ��������</FONT></TD>
          			<TD class=ItemTitle align="center" height=17 ><FONT size=2>�Ѵ�ӡ����</FONT></TD>
				</tr>
				
				<%

					if ( arrResult != null && arrResult.size() > 0 )
					{
						PrintApplyInfo info = null;
						
						Iterator it = (Iterator)arrResult.iterator();

						
						long Setid = -1;
						
						while( it.hasNext() )
						{
							info = (PrintApplyInfo)it.next();
							PrintEbankApplyBiz pbz = new PrintEbankApplyBiz();
                           Collection arrResult2 = pbz.findeBankPrintchoose(queryinfo.getID());
                           String check = "";
                           Iterator it2 = (Iterator)arrResult2.iterator();
                           while( it2.hasNext() )
                           {
                           PrintEbankApply info2 = (PrintEbankApply)it2.next();
                           if(info2.getStempname().equals(info.getStrTempName()))
                           {
                           check = "checked";
                           break;
                           }
                           }
                           
							%>
								<tr>
									<td width="10%" class=ItemBody align="center" >
										<input disabled  type="checkbox" name="seleAll" <%=check %> value="<%=info.getStrTempName()%>">
									</td>
									<td width="20%" class=ItemBody align="center">
									<%=info.getStrBillName()%>
									</td>
									<td class=ItemBody height=17 align="center">
										<%=info.getLPrintNum()%>
									</td>
								</tr>
								
								<tr width="100%" height="1"><td colspan="3" bgcolor="#C5E7F8" height="1"></td></tr>
							<%
						}
					}
					else {
				%>
				
				<tr>
					<TD class=ItemBody height=17>&nbsp;</TD>
          			<TD class=ItemBody height=17>&nbsp;</TD>
          			<TD class=ItemBody height=17>&nbsp;</TD>
				</tr>
				<% } %>
			</table>
		</TD>
  	 </TR>
	 
</table>

	  <!--  br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td nowrap>
            <div align="right">
			<input <% if ( arrResult != null && arrResult.size() > 0 ) { out.print(""); } else { out.print("disabled"); } %> class=button1 name="11" type=button value="�����ٴδ�ӡ" onClick="doSave(frm);">&nbsp;<input class="button1" name="Submit23" value=" �� �� "  type=button onClick="javascript:history.back();">
			</div>
          </td>
        </tr>
      </table--> 

</form>

<script language="javascript">

String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function doSave(form)
{	
	if ( validate(form) == true )
	{
		if ( confirm("�ύ���룡") == true )
		{
			form.strSuccessPageURL.value = "<%=strContext%>/print/control/c001_P.jsp";
			form.strFailPageURL.value = "<%=strContext%>/print/control/c001_P.jsp";
			
			form.action = "<%=strContext%>/print/control/c014_P.jsp";
			form.strAction.value = "save";
			showSending();
			form.submit();
		}
	}
}

function doBank(form)
{
	form.strSuccessPageURL.value = "<%=strContext%>/print/view/v012_P.jsp";
	form.strFailPageURL.value = "<%=strContext%>/print/view/v011_P.jsp";
	
	form.action = "<%=strContext%>/print/control/c001_P.jsp";
	showSending();
	form.submit();
}

function selectAll()
{
	//checkBox�����ִ���1.checkBox����Ϊ����  2.checkBox��Ϊ����
	if(frm.seleAll1.checked==true)
	{
		if ( isNaN(frm.seleAll.length) == true )
		{
			frm.seleAll.checked = true;
		}
		else
		{
			for(var k=0;k<frm.seleAll.length;k++)
			{
				frm.seleAll[k].checked = true;
			}
		}
		
		var ka = <%= i %>;
		for ( var m=1;m<=ka;m++ )
		{
			var name1 = "selectbSet"+m;
			var nlength = eval("frm."+name1+".length");
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=true");
			}
			else
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=true");
				}
			}
		}
		
	}
	else
	{
		if ( isNaN(frm.seleAll.length) == true )
		{
			frm.seleAll.checked = false;
		}
		else
		{
			for(var k=0;k<frm.seleAll.length;k++)
			{
				frm.seleAll[k].checked = false;
			}
		}
		
		var ka = <%= i %>;
		for ( var m=1;m<=ka;m++ )
		{
			var name1 = "selectbSet"+m;
			var nlength = eval("frm."+name1+".length");
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=false");
			}
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=false");
				}
			}
		}
		
	}
}

function selectSet()
{
	//checkBox�����ִ���1.checkBox����Ϊ����  2.checkBox��Ϊ����
	
	var ka = <%= i %>;
	for ( var m=0;m<ka;m++ )
	{
		var ss = m+1;
		var name1 = "selectbSet"+ss;
		var nlength = eval("frm."+name1+".length");
		
		if ( isNaN(nlength) == true )
		{
			var lcheck = eval("frm."+name1+".checked");
			if (lcheck == true)
			{
				//frm.seleAll1.checked = true;
				if ( isNaN(frm.seleAll.length) == true )
				{
					frm.seleAll.checked = true;
				}
				else
				{
					frm.seleAll[m].checked = true;
				}
			}
			else
			{
				if ( isNaN(frm.seleAll.length) == true )
				{
					frm.seleAll.checked = false;
				}
				else
				{
					frm.seleAll[m].checked = false;
				}
			}
		}
		else
		{
			for ( var s=0;s<nlength;s++ )
			{
				var lcheck = eval("frm."+name1+"[s].checked");
				if ( lcheck == true )
				{
					//frm.seleAll1.checked = true;
					if ( isNaN(frm.seleAll.length) == true )
					{
						frm.seleAll.checked = true;
						break;
					}
					else
					{
						frm.seleAll[m].checked = true;
						break;
					}
				}
				else
				{
					if ( isNaN(frm.seleAll.length) == true )
					{
						frm.seleAll.checked = false;
					}
					else
					{
						frm.seleAll[m].checked = false;
					}
				}
			}
		}
	}
	
	//Ч�鵥�����Ͱ�Ťȫ��
	var countsele = 1;
	if ( isNaN(frm.seleAll.length) == true )
	{
		if ( frm.seleAll.checked == true )
		{
			//frm.seleAll1.checked = true;
			countsele = countsele + 1;
		}
		//else
		//{
			//frm.seleAll1.checked = false;
		//}
	}
	else
	{
		for ( var m=0;m<ka;m++ )
		{
			if ( frm.seleAll[m].checked == true )
			{
				//frm.seleAll1.checked = true;
				//break;
				countsele = countsele + 1;
			}
			//else
			//{
				//frm.seleAll1.checked = false;
			//}
		}
	}

	if ( eval(countsele == 1) )
	{
		frm.seleAll1.checked = false;
	}
}

function seleAllTemp(ssTempvalue)
{
	//checkBox�����ִ���1.checkBox����Ϊ����  2.checkBox��Ϊ����

	//Ч�鵥�����͵�����Ť
	var ss = ssTempvalue-1;
	var name1 = "selectbSet"+ssTempvalue;
	var nlength = eval("frm."+name1+".length");
	if ( isNaN(frm.seleAll.length) == true )
	{
		if ( frm.seleAll.checked == true )
		{
			//frm.seleAll1.checked = true;
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=true");
			}
			else
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=true");
				}
			}
		}
		else
		{
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=false");
			}
			else
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=false");
				}
			}
		}
	}
	else
	{
		var name2 = eval("frm.seleAll["+ss+"].checked");
		if ( name2 == true )
		{
			//frm.seleAll1.checked = true;
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=true");
			}
			else
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=true");
				}
			}
		}
		else
		{
			if ( isNaN(nlength) == true )
			{
				eval("frm."+name1+".checked=false");
			}
			else
			{
				for ( var s=0;s<nlength;s++ )
				{
					eval("frm."+name1+"[s].checked=false");
				}
			}
		}
	}
	
	//Ч�鵥�����Ͱ�Ťȫ��
	var countsele = 1;
	
	if ( isNaN(frm.seleAll.length) == true )
	{
		if ( frm.seleAll.checked == true )
		{
			countsele = countsele + 1;
		}
	}
	else
	{
		for ( var i=0;i<frm.seleAll.length;i++ )
		{
			if ( frm.seleAll[i].checked == true )
			{
				countsele = countsele + 1;
			}
		}
	}
	if ( eval(countsele == 1) )
	{
		frm.seleAll1.checked = false;
	}
}

//firstFocus(frm.strTransactionType);
////setSubmitFunction("doSearch(frm)");
setFormName("frm");

function validate(frm)
{
	var all = 0;
	if ( isNaN(frm.seleAll.length) == true )
	{
		if ( frm.seleAll.checked == true )
		{
			all = all + 1;
		} 
	}
	else
	{
		for ( var i=0;i<frm.seleAll.length;i++ )
		{
			if ( frm.seleAll[i].checked == true )
			{
				all = all + 1;
			}
		}
	}
	if ( all == 0 )
	{
		alert("��ѡ�񵥾����ͣ�");
		return false;
	}

	return true;
}

</script>			

<%	
    //��ʾ�ļ�β
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

<%@ include file="/common/SignValidate.inc" %>