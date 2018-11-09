<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contractcontent.dataentity.*,
				com.iss.itreasury.loan.contractcontent.dao.ContractContentDao"
%>

<%

	//log4j //log4j = new //log4j(Constant.ModuleType.LOAN);

	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "�ֽ���������ѯ";
	try
	{
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�������
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lUserID = sessionMng.m_lUserID;
		long lIsSM = Constant.YesOrNo.NO;
		long lIsReadOnly = Constant.YesOrNo.YES;

	
		//log4j.info("c211-v----------5-------------------");
		//��ʾ�ļ�ͷ
		OBHtml.showOBHomeHead(out,sessionMng,"[����ƻ�ά��]",Constant.YesOrNo.NO);
		
%>


<%
		/**
		 * ҳ����Ʋ���
		 */
		String strSourcePage 		= "";	//��Դҳ��
		String strSuccessPageURL 	= "";	//�����ɹ���ת����ҳ��
		String strFailPageURL 		= "";	//����ʧ����ת����ҳ��
		String strAction 			= "";	//��������
		String strActionResult		= "";	//�������
		
		/**
		 * ��request�л�ȡҳ����Ʋ���
		 */
		String strTemp = "";
		
		strTemp = (String)request.getAttribute("strSourcePage");
		if (strTemp != null && strTemp.trim().length()>0){				//��Դҳ��
			strSourcePage = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strSuccessPageURL");
		if (strTemp != null && strTemp.trim().length()>0){				//�ɹ���ת����ҳ��URL
			strSuccessPageURL = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strFailPageURL");
		if (strTemp != null && strTemp.trim().length()>0){				//ʧ����ת����ҳ��URL
			strFailPageURL = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length()>0){				//��������
			strAction = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strActionResult");
		if (strTemp != null && strTemp.trim().length()>0){				//�������
			strActionResult = strTemp.trim();
		}
		
		
		/**
		* ��ý��
		*/

		long lTypeId = -1;
		long lClientID = -1;
		long lContentID = -1;
		String strClientName="";
		strTemp = (String)request.getAttribute("lTypeId");
		if (strTemp != null && strTemp.trim().length()>0){				//�������
			lTypeId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length()>0){				//�������
			lClientID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("lContentID");
		if (strTemp != null && strTemp.trim().length()>0){				//�������
			lContentID = Long.parseLong(strTemp.trim());
		}

		strTemp = (String)request.getAttribute("strClientName");
		if (strTemp != null && strTemp.trim().length()>0){				//�ͻ�����
			strClientName = strTemp.trim();
		}

		String strControl ="";
		strTemp = (String)request.getAttribute("control");
		if (strTemp != null && strTemp.trim().length()>0){				//
			strControl = strTemp.trim();
		}

		Collection c =(Collection)request.getAttribute("ContractContents");	
	
%>


<SCRIPT language="javascript" src="/webloan/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webloan/js/Check.js"></SCRIPT>

<form name="frm">
	<!--ҳ����Ʊ���-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	
	<!--�ظ��������-->
	<!--ҳ����Ʊ���-->
	
	<!--����ҵ������-->
	<input type=hidden name="hdnTypeId" value="<%=lTypeId%>">
	<input type=hidden name="hdnlClientID" value="<%=lClientID%>">
	<input type=hidden name="hdnlContentID" value="<%=lContentID%>">
	<input type=hidden name="hdnstrYear" value="">
	<input type=hidden name="hdnstrSeason" value="">
	<input type=hidden name="hdnClientName" value="<%=strClientName%>">
	<input type=hidden name="hdnControl" value="<%=strControl%>">
	
	<!--����ҵ������-->


<TABLE border=0 class=top width="80%">
  <tr> 
		<td class="FormTitle" height=2  width="100%"><b><%=strTableTitle%></b></td>
  </tr>
  <tr>
	<td height="39" width="583">
		���: <INPUT TYPE="text" NAME="strYear">  
		

	</td>
  </tr>
	<tr>
	<td>
	<table align=center border=0 width=80%>

	<tr > 
            
			<td width="60"></td>
			<td align="right">

              <input class=button name='searchlow' onClick="query()" type="button" value=" �� �� "  onFocus="nextfield ='submitfunction';" >


<%
	if(!("view".equals(strControl)))
	{
%>
              <input class=button name='addnew' onClick="add()" type="button" value=" �� �� "   >
<%
	}
%>
			   <input type="button" name="close" value=" �� �� " class="button" onClick="return closepage();">
            </td>
	</tr>
	</table>
	</td>
	</tr>

  <tr>
    <td width="80%" >
		
    <table width="80%" border="0"  class="ItemList">
      <tr>
        <td class=ItemTitle width="40%" align="center">���</td>
        <td class=ItemTitle width="40%" align="center">���</td>
       
      </tr>
<%
			Iterator it = null;
			int count=1;
	        if (c != null&&c.size()>0 )
            {
				it = c.iterator();
               
                while (it.hasNext())
                {	
					ContractContentInfo info = new ContractContentInfo();
                    info = (ContractContentInfo)it.next();
					//long tt=0;
					// tt = inf.getId();
%>
      <tr>
        <td class=ItemBody align="center">
		<A href="javascript:queryDetail(<%=info.getID()%>,<%=info.getCode()%>);">
		<FONT size=2><%=count++%></FONT></A>
		</td>
       
      
        <td class=ItemBody align="center">
		
		<%=info.getCode()%>
		</td>
      </tr>
<%
				}
			}
%>




    </table>
		
	</td>
  </tr>







</table>
	
</form>
<SCRIPT LANGUAGE="JavaScript">
<!--
		//firstFocus(frm.sInterestRateName);
		//setSubmitFunction("add()");
		setFormName("frm");	 

function query()
{
		if (!InputValid(frm.strYear,0,"int",0,0,0,"���"))			
		{
			  return false;
		}
		if (!InputValid(frm.strYear,0,"string",1,0,4,"���"))		
		{
			return false;
		}
		
		frm.action = "../control/c001.jsp";
		frm.strSuccessPageURL.value = "../view/v004.jsp";
		frm.strAction.value = "query";	//����--��ѯ
		frm.strFailPageURL.value = "../view/v004.jsp";		
		showSending();				
		frm.submit();

		
}
function add()
{ 

	

	if (!validate() )
	{
		return false;
	}

	
		
		frm.action = "../control/c003.jsp";
		frm.strSuccessPageURL.value = "../view/v202.jsp";
		frm.strAction.value = "add";	//����--����
		frm.strFailPageURL.value = "../view/202.jsp";		
		showSending();
		frm.submit();
	

}

function queryDetail(id,year)
{
	
	
	frm.hdnlContentID.value = id;
	frm.hdnstrYear.value = year;

	frm.action = "../control/c003.jsp";
	frm.strSuccessPageURL.value = "../view/v202.jsp";
	frm.strSourcePage.value = "../view/v001.jsp";
	frm.strAction.value = "modify";//����--���Ӳ�ѯ
	frm.strFailPageURL.value = "../view/v202.jsp";

	frm.submit();
}




function validate()
{
    if (!InputValid(frm.strYear,1,"int",0,0,0,"���"))			
	{
		  return false;
	}
	if (frm.strYear.value.length != 4)
	{
		alert("��ݱ���Ϊ��λ����");
		frm.strYear.focus();
		return false;
	}
	return true;
}  

function closepage()
{
	
	
	
	if (confirm("�Ƿ�رյ�ǰ���ڣ�"))
	{
			window.close();
			return false;
	}
	else
	{
		return false;
	}
	
}

//-->
</SCRIPT>







<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
