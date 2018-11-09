<%--
/*
 * �������ƣ�batsign004-v.jsp
 * ����˵��������ǩ�ϲ�ѯҳ��
 * �������ߣ�����ξ
 * ������ڣ�2008��03��31��
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo,
				com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao,
				java.util.*,
				com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>


<%!/* ����̶����� */
	String strTitle = "[����ǩ��]";%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

	/* ʵ������Ϣ�� */
	//ʵ��
	FinanceInfo info = new FinanceInfo();
	List infoList = null;
	//���ӽ���ҳ��
	String strURL = null;
	//�ɹ�����ҳ��
	String strSuccessURL = "";
	//ʧ�ܷ���ҳ��
	String strFaileURL = "";
	String sbatchno = "";//���κ�
	String status = null;//�洢״̬
	String strStartDate = null;//��һ��ҳ�洫���Ŀ�ʼ����	
	String strEndDate = null;//�ϸ�ҳ�洫���Ľ�������
	//��ѯ��
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try {
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}
		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}		
		if(request.getAttribute("strStartDate")!=null)
		{			
			strStartDate =(String)request.getParameter("strStartDate");
			System.out.println("strStartDate============="+strStartDate);
		}				
		if(request.getAttribute("strEndDate")!=null)
		{			
			strEndDate = (String)request.getParameter("strEndDate");	
			System.out.println("strEndDate============="+strEndDate);
		}
        //����ָ��״̬
	    status = (String)request.getParameter("sStatus");
		System.out.println("============================��"+status);	
		//�õ���ѯ��Ľ������
		if(request.getAttribute("infoList")!=null)
		{
			//�õ��������ʾ�������
			infoList = (List)request.getAttribute("infoList");
		}
		if(request.getParameter("sbatchno")!=null)
		{
			sbatchno = DataFormat.formatString(request.getParameter("sbatchno"));
		}
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        //boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);  //�Ƿ�ʹ��CFCA֤����ǩ
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);     
        boolean blnNotBeFalsified = true;	
        int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);	
        String[] nameArray = null;
		String[] valueArray = null; 		
%>

<html>
<script language="javascript">
var flexlist = "flexlist";

$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'strID', name : 'strID', width : 50, sortable : true, align: 'center'},
			{display: '���',  name : 'no', width : 50, sortable : false, align: 'center'},
			{display: '����',  name : 'ncurrencyid', width : 60, sortable : true, align: 'center'},
			{display: '�����ʺ�',  name : 'payeracctno', width : 100, sortable : true, align: 'center'},
			{display: '������',  name : 'payername', width : 60, sortable : true, align: 'center'},
			{display: 'ҵ������',  name : 'ntranstype', width : 100, sortable : true, align: 'center'},
			{display: '���',  name : 'mamount', width : 100, sortable : true, align: 'center'},
			{display: '�տ�ʺ�',  name : 'payeeacctno', width : 100, sortable : true, align: 'center'},
			{display: '�տ���',  name : 'payeename', width : 60, sortable : true, align: 'center'},
			{display: '����ʡ',  name : 'spayeeprov', width : 60, sortable : true, align: 'center'},
			{display: '������',  name : 'spayeecity', width : 60, sortable : true, align: 'center'},
			{display: '������',  name : 'spayeebankname', width : 130, sortable : true, align: 'center'},
			{display: '������CNAPS��',  name : 'spayeebankcnapsno', width : 100, sortable : true, align: 'center'},
			{display: '�����л�����',  name : 'spayeebankorgno', width : 100, sortable : true, align: 'center'},
			{display: '���������к�',  name : 'spayeebankexchangeno', width : 100, sortable : true, align: 'center'},
			{display: '��ע',  name : 'snote', width : 100, sortable : true, align: 'center'},
			{display: '״̬',  name : 'nstatus', width : 60, sortable : true, align: 'center'}
		],//�в���
		title:'��������ǩ��',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryBatchSignInfoDetail',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData,
		callback : 'addOnClickFun()'
	});
	
});

function getFormData() 
{
	return $.addFormData("frm","flexlist");
}


 
 
function addme()
{
	var msg = "�Ƿ�ǩ�ϣ�";
	var isFalsified = false;
	if(!validate()) 
	  {return;}

	<%
	if(isUseCertification)
	{
	%>
		$.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
			if(n.checked&&n.isFalsified){
				isFalsified = true;
			} 
		});

	<%
	}
	%>

	if(isFalsified)
	{
		<%
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�����к��зǷ��޸ĵ����ݣ��Ƿ����ǩ�ϣ���"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //����
				{
		%>
					msg = "�����к��зǷ��޸ĵ����ݣ��޷�ǩ��!"
					alert(msg);
					return false;
		<%
				}	
		%>	
	}else
	{
 	   	   msg = "�Ƿ�ǩ�ϣ�";
	       if (!confirm(msg))
		   {
			   return false;
		   } 		
	}  
	/*$.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
		if(n.checked){
			var isFalsifiedArr = n.value.split("####");
			n.value = isFalsifiedArr[0];
		}
	});*/
	frm.action = "batsign005-c.jsp?Straction=sign&sbatchno=<%=sbatchno%>";
	showSending();
	document.frm.submit();
	
}

function addOnClickFun(){

	showAlarm();
	
}

function cancelsign()
{  if(!validate()) 
	  {return;}
	if(!confirm("�Ƿ�ȡ��ǩ�ϣ�"))
	{
		return false;
	}
	frm.action = "batsign005-c.jsp?Straction=cancelsign&sbatchno=<%=sbatchno%>";
	showSending();
	document.frm.submit();
}
function cancelme()
{
	frm.action = "batsign002-c.jsp?strStartDate=<%=strStartDate%>&strEndDate=<%=strEndDate%>&SelectStatus=<%=status%>&clientID=<%=sessionMng.m_lClientID%>&UserID=<%=sessionMng.m_lUserID%>&CurrencyID=<%=sessionMng.m_lCurrencyID%>";
	
	showSending();
		document.frm.submit();
}
function doCheckAll() {  
    if(frm.txtCheckAll.checked == true){
        if(frm.strID.length==undefined){
            frm.strID.checked= true;  
        }else{
	        for(i=0;i<frm.strID.length;i++){
	           frm.strID[i].checked= true;
	        }
        }
    }else if(frm.txtCheckAll.checked == false){
       if(frm.strID.length==undefined){
            frm.strID.checked= false;  
       }else{
	      for(i=0;i<frm.strID.length;i++){
	        frm.strID[i].checked = false;
	      }
      }
   }
}



function validate()
{
   var isCheck = false;    
 	 $.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
		if(n.checked){
			isCheck = true;
		} 
	});
    if (!isCheck) {
        alert("��ѡ���¼");
        return false;
    }	
	return  isCheck;
}

</script>

<body>
<form method="post" name="frm">
<!--�ж��Ǹ��˻���ȡ�������ֶ�,�˴�Ϊδ���� -->
<input type="hidden" name="sbatchno" value="<%=sbatchno %>"/>
<input type="hidden" name="clientID" value="<%=sessionMng.m_lClientID  %>">
<input type="hidden" name="UserID"    value="<%=sessionMng.m_lUserID %>">
<input type="hidden" name="CurrencyID" value="<%=sessionMng.m_lCurrencyID %>">	
<input type="hidden" name="status" value="<%=status %>">	
<input type="hidden" name="strStartDate" value="<%=strStartDate %>">	
<input type="hidden" name="strEndDate" value="<%=strEndDate %>">	
		
		<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><% if(status.equals("2")){%>
		����ǩ��			
			<% 
		}else if(status.equals("3")){
			%>
			����ȡ��ǩ��
			<%
			}
			%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	
<br/>

<div id='message' style=display:none>
<table width=100% border="0" cellspacing="0" cellpadding="0" align="" class = normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal">
           
<%
		Vector rf = null;//��ʾ��Ϣ
		int iCount = -1;
		if(request.getAttribute("return")!=null)
		{
			rf = (Vector)request.getAttribute("return");
			iCount = rf.size();//����
			for (int i=0;i<iCount;i++)//��ʾ
			{
				String strTmp = (String)rf.elementAt(i);
				out.print("<br>"+strTmp);
			}
		}
%>
           
			</td>
          <td width="1" height="25"></td>
        </tr>
      
</table>
</div>

<TABLE border="0" width="100%">
		<TBODY>
			<tr>
				<TD width="*%">
					<TABLE width="100%" id="flexlist"></TABLE>
				</TD>
			</tr>
		</TBODY>
	</TABLE>
	
	
	 <br>
 <table width=100% border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <%
          if(status.equals("2")){
        	  %>
        	<td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name=add type=button value=" ǩ �� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();">&nbsp;&nbsp;
			</div>
            </td>
        	  <% 
          }else if(status.equals("3")){
        	  %>       	  
        	 	<td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name=add type=button value="ȡ��ǩ��" onClick="Javascript:cancelsign();" onKeyDown="Javascript:cancelsign();">&nbsp;&nbsp;
			</div>
            </td>       	  
        	  <% 
          }
  		
          %>
        
        
		 
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class="button1" name=add type=button value=" ���� " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">&nbsp;&nbsp; 
			</div>
          </td>
        </tr>
      </table>
      	</td>
	</tr>
	
</table>
</form>
<% 
	if(request.getAttribute("return")!=null)
	{
		out.print("<script language = 'javascript'>");
		out.print("document.all('message').style.display='block';");
		out.print("</script>");
	}
	
	if(request.getAttribute("infoList")!=null)
		{
			//�õ��������ʾ�������
			out.print("<script language = 'javascript'>");
			//out.print("document.all('result').style.display='block';");
			out.print("</script>");
		}
%>
</body>
</html>

<%
		/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		Log.print("batchck004-v�쳣��Ϣ��"+ie.toString());
		return;
	}
%>
<safety:resources />
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/SignValidate.inc" %>