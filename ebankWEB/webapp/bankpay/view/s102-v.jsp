<%--
/*
 * �������ƣ�s102-v.jsp
 * ����˵����ҵ��ǩ��
 * �������ߣ�xfma3
 * ���ڣ�2011��04��20��
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[���л��]";
%>
<%
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
     	OBHtml.validateRequest(out,request,response);
     	
     	/* ʵ�ֲ˵����� */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
		    lShowMenu = OBConstant.ShowMenu.NO;
		}
	    
		long lSourceType = 0;//ͷ��Ϣ��ʾ
	    String strSource = request.getParameter("src");
	    if ((strSource != null) && (strSource.length()>0))
		{
		    lSourceType = Long.parseLong(strSource);
		}

		/* �������л�ȡ��Ϣ */
		OBBankPayInfo  info=(OBBankPayInfo)request.getAttribute("info");
		
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);

	//SEFC����
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ҵ��ǩ��</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  	    </td>
  </tr>
</table>
<br/>
      <% if(info.getNstatus() != OBConstant.OBBankPayStatus.SIGN){ %>  
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> ���л��ȷ��</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
      <p ><br>
        ���л�� ����ǩ�Ϻ���ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<br>
			  <br>
              �ñʽ����д�ǩ����ǩ�ϣ�
			  <br>
              <br>
              <b>ָ�����Ϊ��<%= info.getId() %></b><br>
              <br>
            </p>
            </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <%} %>
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> �������</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
	
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"> <%=info.getName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= com.iss.itreasury.ebank.util.NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
         <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"><%= com.iss.itreasury.ebank.util.NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> �տ����</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">      

        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            ��ʽ��
          </td>
          <td width="430" height="25" class="MsoNormal">���л��</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ�˺ţ�
          </td>
          <td width="430" height="25" class="MsoNormal"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ���ƣ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getSpayeeacctname()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
                 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �������кţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankconnectnumber() == null?"":info.getBankconnectnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
                 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �����ţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
		</table>
      <br>
      
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> ��������</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">       

        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>

          <td width="430" height="25" class="MsoNormal" id="ss"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <script>
          		var a = <%=info.getMamount()%>+"";
          		document.getElementById("ss").innerText = formatAmount1(a);
          </script>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�ύ���ڣ�</td>
          <td width="430" height="25" class="MsoNormal"><%=info.getDtexecute().toString().substring(0,10)%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�����;��</td>
          <td width="430" height="25" class="MsoNormal"><%= (info.getSnote()==null)?"":info.getSnote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>

      </table>
	  <br>

      <table  border="0" cellspacing="0" cellpadding="0" >
      <tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> �������봦������</td>
			<td width="17"><a class=lab_title3></td>
	  </tr>
      
      </table>
      <table width=80% border="1" class=normal>
      <thead>
        <tr >
          <td height="19" width="10%"  align="center" >
            <div align="center"><font size="2" >���к�</font></div>
          </td>
          
          <td height="19"  width="30%" align="center">
           �û�
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">��������</div>
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
       </thead>
        <tr valign="middle">
          <td width="10%" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td  width="30%" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="10%" align="left"  height="25">
            <div align="center">2</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNcheckuserid())%></div>
          </td>          
          <td  width="30%" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
		<% if(info.getNstatus() == OBConstant.OBBankPayStatus.SIGN){ %>
		<tr valign="middle">
          <td width="10%" align="left"  height="25">
            <div align="center">3</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNsignuserid())%></div>
          </td>          
          <td  width="30%" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= info.getDtsign().toString().substring(0,10) %></div>
          </td>
        </tr>
		<% } %>
 </table>
 <br>
      <form name="frmzjhb" method="post">
	  <table border="0" width=80% cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="80%" align="right"> 
      	<% if(info.getNstatus() == OBConstant.OBBankPayStatus.SIGN){ %>
		<input class=button1 name=sign type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" >
		<% }else{ %>
		<input class=button1 name=sign type=button value=" ǩ  �� " onClick="Javascript:signme();" >
		<% } %>
		<input class=button1 name=back type=button value=" ��  �� " onClick="javascript:returnme()" >
      </td>
        </tr>
        <tr><td height=5>&nbsp;</td></tr>
      </table>

      <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtID" value="<%= info.getId() %>">
	  <input type="hidden" name="txtTransType" value="<%=info.getNtranstype() %>">
	  <input type="hidden" name="SelectType" value="<%=info.getNtranstype() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="newOpen" value="">
	  <input type="hidden" name="submitAgain" value="">
	  <input type="hidden" name="dAmount" value="<%=info.getMamount() %>">
	  <input type="hidden" name="tsExecute" value="<%=info.getFormatExecuteDate() %>">	  
	  <input type="hidden" name="submitUserId" value="<%=info.getNconfirmuserid() %>">
      
	  <input type="hidden" name="Amount" value="<%= info.getMamount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= info.getDtexecute() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= info.getNconfirmuserid() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= info.getDtconfirm() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= info.getNpayeracctid() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= info.getNpayeeacctid() %>">
	  <input type="hidden" name="lInstructionID" value="<%= info.getId() %>">
	  <INPUT type="hidden" name="ncurrencyid" value="<%=info.getNcurrencyid()%>">
	  <INPUT type="hidden" name="nclientid" value="<%=info.getNclientid()%>"> 
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <INPUT type="hidden" name="spayeeprov" value="<%=info.getSpayeeprov()%>">
	  <INPUT type="hidden" name="spayeecity" value="<%=info.getSpayeecity()%>">
	  <INPUT type="hidden" name="spayeebankname" value="<%=info.getSpayeebankname()%>">
	  <INPUT type="hidden" name="spayeeacctname" value="<%=info.getSpayeeacctname()%>">
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <!--start  ָ����֤���html -->
   	  <input name="Ver" id="Ver" type="hidden" value="">
	  <!--end  ָ����֤���html -->
	  </form>
<!--presentation end-->
	<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	<!--  ָ�ƿؼ�-->
	<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
			 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
	<% } %>	
<script language="javascript">

	/* �˵����ƴ������� */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frmzjhb.menu.value="hidden";
		<%  }   %>
	}
	/*���ش������� */
	function returnme()
	{	if (!confirm("�Ƿ񷵻أ�"))
		{
			return false;
		}
		//frmzjhb.action="../view/s001-v.jsp";
		//frmzjhb.submit();
		window.close();
	}

	/* ǩ�ϴ������� */
	function signme()
	{
		//showMenu();
		//-------------------����ָ����֤---��ʼ----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //ָ����֤
		$.ajax(
		{
			  type:'post',
			  url:"<%=request.getContextPath()%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(frmzjhb,1);
					if($("#Ver").val()!=""){
				  	    signme();// �ٴ��ύ
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("ָ����֤���������²ɼ�");	
					$("#Ver").val("");
				  	getFingerPrint(frmzjhb,1);//���ؿؼ�
					if($("#Ver").val()!=""){
				  	    signme();// �ٴ��ύ
					}
					fpFlag = false;
				}
				else{
					if(result != null && result != "null" && result != "" ){
						alert(result);	
						$("#Ver").val("");
						fpFlag = false;
					}else{
						fpFlag = true;
					}
				}
			  }
		}
		);
		if(!fpFlag){return;}
		<%}%>
		//-------------------����ָ����֤---����----------------
		if (!confirm("�Ƿ�ǩ�ϣ�"))
		{
			$("#Ver").val("");
			return false;
		}
		frmzjhb.action="../view/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* ȡ��ǩ�ϴ������� */
	function cancelsignme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			frmzjhb.action="../view/s004-c.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* ��ӡ�������� */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
	}

</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	}catch(Exception e){
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>