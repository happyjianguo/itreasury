<%--
/*
 * �������ƣ�c002-v.jsp
 * ����˵�����ʽ𻮲��޸�����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��06��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.budget.util.*"%>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.TransTypeSet" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrStatus" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<% 
	String strContext = request.getContextPath();
	/* ����̶����� */
	String strTitle = null;
	String strTemp = null;
	long lAbstractID = -1;
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	String lsign = null;  //�ж��Ƿ�Ϊ�����ύ
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}
	
	
	String sPayerCurrentBalance="";
	String dPayerUsableBalance="";
	Timestamp systemDateTime = Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
  	     //�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
    	
		/* ָ����� */
		//long lInstructionID = -1;
		
		/* ��ʼ��EJB */
		//OBFinanceInstrHome financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		//OBFinanceInstr financeInstr = financeInstrHome.create();
		//FinanceInfo financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		
		if(financeInfo == null){
			financeInfo = new FinanceInfo();
		}
		System.out.println("���κţ�" + financeInfo.getSBatchNo());
		
		//��ʼ����������Ϣ
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		//��ҳ��ֻ�б���λ�������ܽ��룬�������ֱ���λ������������λ����
		inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		//���и���
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
		boolean bankPayIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//�ڲ�ת��
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
		boolean internalVirementIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//��ת
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_SELF);
		boolean selfIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//����
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_OTHER);
		boolean otherIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);		
		boolean isNeedApproval = true;
		/* ����Ϣ���л�ȡ��ʽ���ĵ�ǰ���Ϳ��ý�� */
        sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
        dPayerUsableBalance = financeInfo.getFormatUsableBalance();
        if (dPayerUsableBalance==null || dPayerUsableBalance.trim().length()==0) 
		{	
			dPayerUsableBalance="0.00";
		}
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        
       %>
       
        <%
        String saccountno_zhubi="";
        long nPayerAccountID=-1;
        String modify = "";
        String lmodify="";
		lmodify=(String)request.getAttribute("modify");
		if(lmodify!=null&&lmodify.trim().length()>0)
		{modify=lmodify;}     
        FinanceInfo info=new  FinanceInfo();
        OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	 	info.setClientID(sessionMng.m_lClientID);
        info.setCurrencyID(sessionMng.m_lCurrencyID);
        info.setNUserID(sessionMng.m_lUserID);
        info.setOfficeID(sessionMng.m_lOfficeID);
	 	info =obFinanceInstrBiz.query_OBMagnifier1(info);
	 	nPayerAccountID=financeInfo.getPayerAcctID();
 		  if(info!=null)
 		  {
 		nPayerAccountID=info.getNAccountID();
 		saccountno_zhubi=info.getSaccountno_zhubi();
 		 if(!modify.equals("modify"))
        {
 		dPayerUsableBalance=info.getCurrentbalance_zhubi();
 		  }
 		sPayerCurrentBalance=info.getN_balance_zhubi();
 		  }
 		  
 		  String lupdate = null;
		  String update = "";
		  lupdate = request.getParameter("update");
		  if(lupdate!=null&&lupdate.trim().length()>0)
			{
				update = lupdate;
			}
			
		//��ѯ�Ƿ�����ί���տ����ɵ���ת add by xlchang 2010-12-06 �������
		String isConsignReceive = "false";
		strTemp = (String)request.getAttribute("isConsignReceive");
		if(strTemp != null && !strTemp.equals("")){
			isConsignReceive = strTemp;
		}
			
        %>
	
       
       
       
     
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<%if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){%>
	<safety:resources/>
<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){%>
	<safety:resources cabName="NetSign20.cab" cabVersion="2,0,53,1" cabClassId="B3B938C4-4190-4F37-8CF0-A92B0A91CC77"/>
<%}%>

<form method="post" name="frmzjhb">
<!--start  ָ����֤���html -->
<input name="Ver" id="Ver" type="hidden" value="">
<!--end  ָ����֤���html -->
<input name="dtmodify" value="<%=financeInfo!=null?financeInfo.getDtModify()+"":"" %>" type="hidden"/>
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">��ʸ���</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

  	    </td>
  </tr>
  
</table>

<br/>


<table  border="0" cellspacing="0" cellpadding="0" align="">

  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> �������</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>

     <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="183" height="25"  align="left"><font color="#FF0000">* </font>������ƣ�</td>
          <td width="458" height="25" >
            <input type="text" class="box" name="sPayerName" size="32" value="<%=sessionMng.m_strClientName%>" readonly>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr  class="MsoNormal" id="payerAccountNoZoom">
			<td width="4" height="25" class="MsoNormal"></td>
			
<%     
  	 String[] ss = {"itemIDCtrl"};
		//����˺ŷŴ�
		if(sign.equals("again")||update.equals("update"))
		{
			OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","<font color='#FF0000'>* </font>����˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss);	
		}
		
		else
		{
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frmzjhb",saccountno_zhubi,"sPayerAccountNoZoom","<font color='#FF0000'>* </font>����˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss);	
		}
	%>		
		<td width="220" class="MsoNormal" ></td>
		 </tr>
		 <!--  �н�����Ŵ� -->
		 	<tr  class="MsoNormal" id="payerAccountNoZoomDown">
		<td width="4" height="25" class="MsoNormal"></td>
			
<%     
  	 String[] ss1 = {"itemIDCtrl"};
		//����˺ŷŴ�
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountIDDown","dPayerCurrBalanceDown","dPayerUsableBalanceDown","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoDownZoom","<font color='#FF0000'>* </font>����˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss1);	
	%>		
		<td width="220" class="MsoNormal" ></td>
		 </tr>

		<tr class="MsoNormal">
        <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		
        <tr  class="MsoNormal" id="Balance">
          <td width="4" height="25" class="MsoNormal"></td>
          
          <td width="183" height="25" class="MsoNormal" align="left">&nbsp;&nbsp;��ǰ��</td>
          <td width="458" height="25" class="MsoNormal">
		<input type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%= sPayerCurrentBalance %>" readonly align="right">
		  </td>
		</tr>
		<tr> 
          <td width="4" height="25" class="MsoNormal"></td>		 
		  <td width="183" height="25" class="MsoNormal" align="left">		
		<font class="MsoNormal" >
		&nbsp;&nbsp;������
		</font></td>
          <td width="458" height="25" class="MsoNormal">		
		<input type="text" class="tar" name="dPayerUsableBalance" size="20" value="<%= dPayerUsableBalance %>" readonly align="right">
		<input type="hidden" name="nPayerAccountID" size="20" value="<%=nPayerAccountID %>" >
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal" id="BalanceDown">
          <td width="4" height="25" class="MsoNormal"></td>

          <td width="183" height="25" class="MsoNormal" align="left">&nbsp;&nbsp;��ǰ��</td>
          <td width="458" height="25" class="MsoNormal" >
		<input type="text" class="tar" name="dPayerCurrBalanceDown" size="20" value="<%= sPayerCurrentBalance %>" readonly align="right">
		<font class="MsoNormal" >
		������
		</font>
		<input type="text" class="tar" name="dPayerUsableBalanceDown" size="20" value="<%= dPayerUsableBalance %>" readonly align="right">
		<input type="hidden" name="nPayerAccountIDDown" size="20" value="<%= financeInfo.getPayerAcctID() %>" >
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0" align="">

    <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> �տ����</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width=80% border="0" cellspacing="0" align="" cellpadding="0" class=normal id="table1">
        <tr >
          <td colspan="4" height="1" ></td>
        </tr>
        <tr >
          <td width="5" height="25" ></td>
          <td width="19%" height="25" align="left">
            <p><span ><font color="#FF0000">* </font>��ʽ��</span></p>
          </td>
          <td height="25"  align="left">
           <input type="hidden" name="nRemitTypeHidden" value="<%= financeInfo.getRemitType() %>">
		  <%
		  		OBHtmlCom.showRemitTypeListControlZj(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\"  ":"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
      </table>
		   
	  <!--��ʽ��̬��ʾ�տ����-->

	  <table width=80% class=normal align="">
	  
	  	<tr id="payeeNameOther" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input type="text" class="box" name="sPayeeNameOther" value="<%=Env.getClientName()%>" size="32" readonly>
              </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameOtherLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNoZoom" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
<%
		//�տ�˺ŷŴ󾵣���ת��
		
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>�տ�˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");		
%>	 
					
          <td  height="25" width="177" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNoZoomLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomBank" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
		  <td height="25" colspan="2" class="MsoNormal">
		  	<input type="Text" class="box" name="sPayeeNameZoomBankCtrl" value="<%= financeInfo.getPayeeName() %>" maxlength="50" size="32" readonly>
		  </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomBankLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeeBankNoZoomInternal" class="MsoNormal">
          <td height="25" width="6" class="MsoNormal"></td>
<%
		//�տ�˺ŷŴ󾵣��ڲ�ת�ˣ�
		//OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","<font color='#FF0000'>* </font>�տ�˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
        OBMagnifier.createPayeeBankNOCtrl1(out,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","<font color='#FF0000'>* </font>�տ�˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
%>		
<script>
	document.frmzjhb.hidsPayeeBankNoZoomCtrl.value="<%=financeInfo.getPayeeAcctNo()%>";
	document.frmzjhb.hidsPayeeName.value="<%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%>";
</script> 
          <td  height="25" width="183" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNoZoomInternalLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		 <tr id="payeeName" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
          <td height="25" colspan="3"class="MsoNormal">
            <input type="text" class="box" name="sPayeeName" value="<%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%>" size="32" >
              </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		
		 <tr id="payeeAcctNoZoom" class="MsoNormal">
          <td height="25" width="6" class="MsoNormal"></td>
<%
		//�տ�˺ŷŴ󾵣��㣩
		OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeNameZoomAcctCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","<font color='#FF0000'>* </font>�տ�˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"");	
	
%>	
          <td height="25" width="183" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<textarea name="sPayeeNameZoomAcctCtrl" class="box" cols="30" onfocus="nextfield ='sPayeeProv';" rows="2" size="32" ><%= financeInfo.getPayeeName()%></textarea>
		  </td>
          <td height="25" width="2" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeePlace" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>����أ�</td>
          <td width="546" height="25"  class="MsoNormal">
            <input type="text" class="box" name="sPayeeProv" onblur="dropSpace(this)" value="<%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15">
            ʡ
            <input type="text" class="box" name="sPayeeCity" onblur="dropSpace(this)" value="<%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15">
        �У��أ�</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;���������ƣ�</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="32" readonly>
          </td>
		<td height="25" width="7" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankName">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>���������ƣ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankName" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='sPayeeBankCNAPSNO';" maxlength="50">
			<input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNameLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankCNAPSNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;������CNAPS�ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankCNAPSNO" value="<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>" size="32" onfocus="nextfield ='sPayeeBankOrgNO';" maxlength="50">
		
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankCNAPSNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankOrgNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;�����л����ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankOrgNO" value="<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>" size="32" onfocus="nextfield ='sPayeeBankExchangeNO';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankOrgNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankExchangeNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;���������кţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankExchangeNO" value="<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankExchangeNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <!-- �н����»���Ա��λ��ʾ���տ���� -->
        <tr id="payeeAcctNoZoomDown" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
<%
		//�տ�˺ŷŴ󾵣��»���
		OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountIDDown","sPayeeNameZoomAcctDownCtrl","sPayeeProvDown","sPayeeCityDown","sPayeeBankNameDown","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoDownZoom","<font color='#FF0000'>* </font>�տ�˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"");	
	
%>	

          <td height="25" width="177" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
        <tr id="payeeNameZoomAcctDown" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<textarea name="sPayeeNameZoomAcctDownCtrl" cols="30" class="box"  onfocus="nextfield ='sPayeeProvDown';" rows="2" ><%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%></textarea>
		  </td>
          
          <td height="25" width="2" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceDown" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>����أ�</td>
          <td height="25"  class="MsoNormal">
            <input type="text" class="box" name="sPayeeProvDown" onblur="dropSpace(this)" value="<%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCityDown';" maxlength="15">
            ʡ
            <input type="text" class="box" name="sPayeeCityDown" onblur="dropSpace(this)" value="<%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankNameDown';" maxlength="15">
        �У��أ�</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankNameDown">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177"  align="left">&nbsp;&nbsp;���������ƣ�</td>
          <td height="25" colspan="2">
            <input type="text" class="box" name="sPayeeBankNameDown" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='sPayeeBankCNAPSNODown';" maxlength="50">
			<input type="hidden" name="nPayeeAccountIDDown" value="<%=  financeInfo.getPayeeAcctID() %>" >
          </td>
        <td  height="25" width="7"></td>
        </tr>
		<tr id="payeeBankNameLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
        
        <tr id="payeeBankCNAPSNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;������CNAPS�ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankCNAPSNODown" value="<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>" size="32" onfocus="nextfield ='sPayeeBankOrgNODown';" maxlength="50">
		
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankCNAPSNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankOrgNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;�����л����ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankOrgNODown" value="<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>" size="32" onfocus="nextfield ='sPayeeBankExchangeNODown';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankOrgNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankExchangeNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;���������кţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankExchangeNODown" value="<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankExchangeNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
      <!-- �н��»������� --> 
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0" align="">
   <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> ��������</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width=80% border="0" align="" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="19%" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>��&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td height="25" width="590" class="MsoNormal">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%=financeInfo.getFormatAmount()%>","tsExecute","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
			</script>
			
			</td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal"  align="right">��д���(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)��</td>
          <td height="25" class="MsoNormal">
            <!--modify by xwhe 2008-05-08-->
			<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal"  align="left">&nbsp;&nbsp;�ⲿϵͳָ����ţ�</td>
          <td height="25" class="MsoNormal">
         
			<input type="text" class="box" name="sApplyCode" size="32" value="<%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %>">		
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        
      	<!-- Boxu Add 2010-12-01 ����"����/���"��"�Ƿ�Ӽ�"��ʹ�������ѵ������ļ� -->
        <%
        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) 
        	&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
        	{
        %>
        	<tr id="trBankpayim">
				<td width="4" height="25">&nbsp;</td>
	          	<td width="130" height="25">&nbsp;&nbsp;�������/�ٶȣ�</td>
	          	<td width="500" height="25">
	            	<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.NATIVE %>" 
					<%if(financeInfo.getRemitArea() == -1){out.println("checked");}else{ if(financeInfo.getRemitArea() == Constant.remitAreaType.NATIVE){out.println("checked");} }%>> ����
					<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.DEVIATIONISM %>" 
					<%if(financeInfo.getRemitArea() == Constant.remitAreaType.DEVIATIONISM){out.println("checked");}%>> ���&nbsp;&nbsp;&nbsp;&nbsp;
	            	
			        <input type="checkbox" name="remitSpeed" <% if(financeInfo.getRemitSpeed() == Constant.remitSpeedType.RAPID) { out.print("checked"); } %> 
			        onfocus="nextfield ='tsExecute';">�Ƿ�Ӽ�
	          	</td>
	          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        	</tr>
        <%
        	}
        %>
        
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal"  align="left"><font color="#FF0000">* </font>ִ���գ�</td>
          <td height="25" class="MsoNormal">
           <fs_c:calendar 
          	    name="tsExecute"
	          	value="" 
	          	properties="nextfield ='sNoteCtrl'" 
	          	size="20"/>
	          	<script>
	          		$('#tsExecute').val('<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>');
	          	</script>
		  </td>
		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

<%
		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "frmzjhb";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " <font color=\"#FF0000\">* </font>�����;";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = financeInfo.getNote();
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 12;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"butAdd"};
		
	OBMagnifier.createAbstractSettingCtrl(
		out,
		lOfficeIDAbstract,
		lClientIDAbstract,
		lCurrencyIDAbstract,
		strFormNameAbstract,
		strCtrlNameAbstract,
		strTitleAbstract,
		lAbstractIDAbstract,
		strAbstractDescAbstract,
		strFirstTDAbstract,
		strSecondTDAbstract,
		maxLength,
		strNextControlsAbstract);
%>
		  <td width="600" class="MsoNormal"></td> 
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      
<%---------
       <br>
           <div id="bankpay">
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "�ϴ�" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		   	    	clientID = '<%=sessionMng.m_lClientID%>'
		   	    	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
		   	    	</div>
            <div id="internalvirement">
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "�ϴ�" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
		        	</div>
      <br>
-----------%>
	  <br/>
      <table width=80% border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
			<input class="button1" name="butAdd" type="button" value=" �� �� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
		  	<fs:obApprovalinitbutton controlName="approvalInit"
		 										 value="���沢�ύ����"
												 classType="button1"
												 onClickMethod="doSaveAndApprovalInit();"
												 officeID="<%=sessionMng.m_lOfficeID%>"
												 clientID="<%=sessionMng.m_lClientID%>"
												 currencyID="<%=sessionMng.m_lCurrencyID%>"
												 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
												 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" />
			 <fs:obApprovalinitbutton controlName="approvalInit1"
		 										 value="���沢�ύ����"
												 classType="button1"
												 onClickMethod="doSaveAndApprovalInit();"
												 officeID="<%=sessionMng.m_lOfficeID%>"
												 clientID="<%=sessionMng.m_lClientID%>"
												 currencyID="<%=sessionMng.m_lCurrencyID%>"
												 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
												 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>" />									  
			<input class="button1" name="butReset" type="reset" value=" �� �� "  >&nbsp;&nbsp;
          </td>
        </tr>
      </table>
      <br/>
<%
		String strClickCount = (String) session.getAttribute("clickCount");
		Log.print("strClickCount=" + strClickCount);
		if (strClickCount == null) 
		{
			strClickCount = "0";
		}
		if(sign.equals("again"))
		{
			financeInfo.setID(-1);
		}
%>
	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
		<input type="hidden" name="clickCount" value="<%=strClickCount%>">
	  	<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	    <input type="hidden" name="strAction" value="">
	    
	  
	  <input type="hidden" name="submitUserId" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>"> 
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <!--add by xwhe 2008-11-30-->
	 <input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo() %>">
	 <input type="hidden" name="systemDateTime" value="<%=DataFormat.getDateString(systemDateTime)%>">
	  
	  <safety:certFilterHidden />

</form>
<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  ָ�ƿؼ�-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
<% } %>
<script language="Javascript">
	
	/* ��ʽ */
	var iRemitType = frmzjhb.nRemitType.value;
	var isNeedApproval2= null;
	jump();

	/* ʵ�ֹ��ܣ���̬��ʾ���ݻ�ʽȷ�����տ����¼���
	 * ʵ�ַ�����ͨ����TR�е�ID���Կ���ʵ��
	 */

	/* �տ���� */
	function jump()
	{	
		//document.getElementById("bankpay").style.display="none";
		//document.getElementById("internalvirement").style.display="none";

		frmzjhb.approvalInit.style.display ="none";
		frmzjhb.approvalInit1.style.display ="none";
		iRemitType = frmzjhb.nRemitType.value;
		
		/*�»��ĸ���Ŵ�*/
		payerAccountNoZoomDown.style.display = "none";
		BalanceDown.style.display = "none";
					
		payeeName.style.display = "none";
		payeeNameLine.style.display = "none";
		payeeNameZoomBank.style.display = "none";
		payeeNameZoomBankLine.style.display = "none";
		payeeNameZoomAcct.style.display = "none";
		payeeNameZoomAcctLine.style.display = "none";
			//�»����տ����
			payeeNameZoomAcctDown.style.display="none";
			payeeNameZoomAcctLineDown.style.display="none";
		
		
		/* �տ�����˺� */
		payeeBankNoZoom.style.display = "none";
		payeeBankNoZoomLine.style.display = "none";
		payeeBankNoZoomInternal.style.display = "none";
		payeeBankNoZoomInternalLine.style.display = "none";
			//�»����տ�����˺�
			payeeAcctNoZoomDown.style.display="none";
			payeeAcctNoZoomLineDown.style.display="none";
		/* �տ�˺� */
		//payeeAcctNo.style.display = "none";
		//payeeAcctNoLine.style.display = "none";
		payeeAcctNoZoom.style.display = "none";
		payeeAcctNoZoomLine.style.display = "none";
		/*  ���������� */
		payeeBankName.style.display = "none";
		payeeBankNameRead.style.display = "none";
		payeeBankNameLine.style.display = "none";
		/* ����� */
		payeePlace.style.display = "none";
		payeePlaceLine.style.display = "none";
		//����ӿ�����
		payeeBankCNAPSNO.style.display = "none";
		payeeBankCNAPSNOLine.style.display = "none";
		payeeBankOrgNO.style.display = "none";
		payeeBankOrgNOLine.style.display = "none";
		payeeBankExchangeNO.style.display = "none";
		payeeBankExchangeNOLine.style.display = "none";
			/*�»������*/
			payeePlaceDown.style.display = "none";
			payeePlaceLineDown.style.display = "none";
			/*�»���������*/
			payeeBankNameDown.style.display = "none";
			payeeBankNameLineDown.style.display = "none";
			
			payeeBankCNAPSNODown.style.display = "none";
			payeeBankCNAPSNOLineDown.style.display = "none";
			payeeBankOrgNODown.style.display = "none";
			payeeBankOrgNOLineDown.style.display = "none";
			payeeBankExchangeNODown.style.display = "none";
			payeeBankExchangeNOLineDown.style.display = "none";
		/* ��ǰ��� */
		//payeeBalance.style.display = "none";
		//payeeBalanceLine.style.display = "none";
		/*����*/
		payeeNameOther.style.display = "none";
		payeeNameOtherLine.style.display = "none";
	
		/* ���ݻ�ʽȷ������ʾ��TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // ��ʽ��ת
		{
		    isNeedApproval2  = <%=selfIsNeedApproval%>;
		    <%isNeedApproval=selfIsNeedApproval;System.out.println("��ʽ��ת");%>
		    frmzjhb.isNeedApproval.value = <%=selfIsNeedApproval%>
		   // frmzjhb.approvalInit.style.display = <%=selfIsNeedApproval==true?"":"\"none\""%>
			/*����Ŵ�*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			
			/* ���Ŵ󾵡����տ�����˺��й������տ���� */
			payeeNameZoomBank.style.display = "";
			payeeNameZoomBankLine.style.display = "";
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoom.style.display = "";
			payeeBankNoZoomLine.style.display = "";
			/* �����Ŵ󾵵��տ�˺� */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/*  ���������� */
			payeeBankNameRead.style.display = "";
			payeeBankNameLine.style.display = "";
					
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // ���и���	
		{		
		isNeedApproval2  = <%=bankPayIsNeedApproval%>;
		 <%isNeedApproval=bankPayIsNeedApproval;System.out.println("���и���");%>
		 frmzjhb.isNeedApproval.value = <%=bankPayIsNeedApproval%>
		 <%-- modify by xwhe 2008-10-24 �ж�����������Ļ��Ͳ���������--%> 	
	    <%if(financeInfo.getSBatchNo()==null || financeInfo.getSBatchNo().trim().length()==0 ) {%>
		   frmzjhb.approvalInit.style.display = <%=bankPayIsNeedApproval==true?"":"\"none\""%>
		<%}
		  else
		  {
		%>
		   frmzjhb.approvalInit.style.display = "none";
		 <%
		 }
		 %>
			/*����Ŵ�*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			/* ���Ŵ󾵡����տ�˺��й������տ���� */
			payeeNameZoomAcct.style.display = "";
			payeeNameZoomAcctLine.style.display = "";
			/* ���Ŵ󾵵��տ�˺� */
			payeeAcctNoZoom.style.display = "";
			payeeAcctNoZoomLine.style.display = "";
			/* ����� */
			payeePlace.style.display = "";
			payeePlaceLine.style.display = "";
			/*  ���������� */
			payeeBankName.style.display = "";
			payeeBankNameLine.style.display = "";
			
			payeeBankCNAPSNO.style.display = "";
			payeeBankCNAPSNOLine.style.display = "";
			payeeBankOrgNO.style.display = "";
			payeeBankOrgNOLine.style.display = "";
			payeeBankExchangeNO.style.display = "";
			payeeBankExchangeNOLine.style.display = "";
			
			/* ���ݻ�ʽ�ı�����; */
			//frmzjhb.sNote.value="���и���";
			
		}
		/*
		**�н����룬�»���Ա��λ
		*/
		if(iRemitType==<%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)//�»���Ա��λ
		{

			/*����Ŵ�*/
			payerAccountNoZoom.style.display="none";
			Balance.style.display = "none";
			
			payerAccountNoZoomDown.style.display="";
			BalanceDown.style.display="";
			
			
				/* ���Ŵ󾵡����տ�˺��й������տ���� */
			payeeNameZoomAcctDown.style.display = "";
			payeeNameZoomAcctLineDown.style.display = "";
			/* ���Ŵ󾵵��տ�˺� */
			payeeAcctNoZoomDown.style.display = "";
		    payeeAcctNoZoomLineDown.style.display = "";
		    
			/* ����� */
			payeePlaceDown.style.display = "";
			payeePlaceLineDown.style.display = "";
			/*  ���������� */
			payeeBankNameDown.style.display = "";
			payeeBankNameLineDown.style.display = "";
			
			payeeBankCNAPSNODown.style.display = "";
			payeeBankCNAPSNOLineDown.style.display = "";
			payeeBankOrgNODown.style.display = "";
			payeeBankOrgNOLineDown.style.display = "";
			payeeBankExchangeNODown.style.display = "";
			payeeBankExchangeNOLineDown.style.display = "";
		}
		

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // ��ʽ�ڲ�ת��
		{
		isNeedApproval2  = <%=internalVirementIsNeedApproval%>;
		<%isNeedApproval=internalVirementIsNeedApproval;System.out.println("��ʽ�ڲ�ת��");%>
		 frmzjhb.isNeedApproval.value = <%=internalVirementIsNeedApproval%>
		 <%-- modify by xwhe 2008-10-24 �ж�����������Ļ��Ͳ���������--%> 	
	    <%if(financeInfo.getSBatchNo()==null || financeInfo.getSBatchNo().trim().length()==0 ) {%>
		 frmzjhb.approvalInit1.style.display = <%=internalVirementIsNeedApproval==true?"":"\"none\""%>
		 <%}
		  else
		  {
		 %>
		   frmzjhb.approvalInit1.style.display = "none";
		 <%
		 }
		 %>
		    /*����Ŵ�*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			/* �����Ŵ󾵵��տ���� */
			payeeName.style.display = "";
			payeeNameLine.style.display = "";
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoomInternal.style.display = "";
			payeeBankNoZoomInternalLine.style.display = "";
			 		
		}
		if(iRemitType == <%= OBConstant.SettRemitType.OTHER %>) // ��ʽ����
		{
		isNeedApproval2  = <%=otherIsNeedApproval%>;
		<%isNeedApproval=otherIsNeedApproval;System.out.println("��ʽ����");%>
		frmzjhb.isNeedApproval.value = <%=otherIsNeedApproval%>
		//frmzjhb.approvalInit.style.display = <%=otherIsNeedApproval==true?"":"\"none\""%>
		
			/* �����Ŵ󾵵��տ���� */
			payeeNameOther.style.display = "";
			payeeNameOtherLine.style.display = "";
			//frmzjhb.sNote.value="����";	
		}
	}
	
	function getNextField()
	{
				
              //>>>>add by shiny 20030403
      	      var iRemitType = frmzjhb.nRemitType.value;
			  if (iRemitType == -1)
			  {//û��ѡ��
			  	  alert("��ʽ����Ϊ�գ���ѡ��");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//�ڲ�ת��
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//��ת
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//����
                  frmzjhb.dAmount.focus();
              }
              else if (iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER%>)
              {//�н�,�»���Ա��λ
              	  trimXH();
              	  frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();             	                	  
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* �޸ı��洦���� */
    function addme()
    {
    	var varRemitSpeed = -1;
    	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) 
    	&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY) { %>
    	if(frmzjhb.remitSpeed.checked == true)
	 	{
	 		varRemitSpeed = "<%=Constant.remitSpeedType.RAPID %>";
	 	}
	 	else
	 	{
	 		varRemitSpeed = "<%=Constant.remitSpeedType.GENERAL %>";
	 	}
	 	<% } %>
    	
		document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		var signatureValue;
		trimXH();
		if (validate() == true)
        {
        	//ǩ��  add by mingfang 2007-05-23
			<%
			if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		           
	            if(financeInfo.getSBatchNo()!=null && financeInfo.getSBatchNo().length()>0)
	            {
	            %>
	             valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
	            <% 
	           }	            
	            else
	           {
	           %>  
				if(isNeedApproval2){	
				<%				
					if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
					&& !financeInfo.isRefused()){
			%>				
					//���⴦��
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
			<%
				}%>			
			}	
			else{
				<%if(financeInfo.getStatus() !=OBConstant.SettInstrStatus.CHECK 
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED){
			%>
					//���⴦��
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
			<%
				}%>
			}
	     <%   }%>
					
				signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				//ǩ�����ɹ������������
				if(signatureValue == ""){
					alert("ǩ�����ɹ����޷����б��棡");
					return false;
				}
			<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>	
				
				try{
					signatureValue = DoSignNetSign(frmzjhb,nameArray,valueArray);
				}catch(e){
					alert("frmzjhb"+e.description);
				}		
				//ǩ�����ɹ������������
				if(signatureValue == ""){
					alert("ǩ�����ɹ����޷����б��棡");
					return false;
				}
			<%}%>
			if(validate() == true){
				
				//-------------------���ָ����֤---��ʼ----------------
				<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
			    var fpFlag = true;
			    //ָ����֤
				$.ajax(
				{
					  type:'post',
					  url:"<%=strContext%>/fingerprintControl.jsp",
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
						  	    addme();// �ٴ��ύ
							}
							fpFlag = false;
						 }
						 else if(result=="fpiswrong"){
					  		alert("ָ����֤���������²ɼ�");	
							$("#Ver").val("");
						  	getFingerPrint(frmzjhb,1);//���ؿؼ�
							if($("#Ver").val()!=""){
						  	    addme();// �ٴ��ύ
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
				//-------------------���ָ����֤---����----------------			
				
				//ȷ�ϱ���
				if (!confirm("�Ƿ񱣴棿"))
				{
					$("#Ver").val("");
					return false;
				}
			}
			
			//��ѯ�Ƿ�����ί���տ����ɵ���ת add by xlchang 2010-12-06 �������
			invocationControl(frmzjhb);
			
			frmzjhb.<%=SignatureConstant.SIGNATUREVALUE%>.value = signatureValue;
			frmzjhb.action = "c003-c.jsp?flag=commit&sign=<%=sign%>&remitSpeed="+varRemitSpeed;
			
			var parameter = "";
			parameter += "PayerAcctNo="+document.getElementById("sPayerAccountNoZoomCtrl").value;
			parameter += "&nRemitType="+document.getElementById("nRemitType").value;
			parameter += "&dAmount="+document.getElementById("dAmount").value;
			parameter += "&tsExecute="+document.getElementById("tsExecute").value;
			parameter += "&nRemitTypeHidden="+document.getElementById("nRemitTypeHidden").value;
			parameter += "&lInstructionID="+document.getElementById("lInstructionID").value;
			parameter += "&PayeeAcctNo="+document.getElementById("sPayeeBankNoZoomCtrl").value;
			parameter += "&sPayeeProv="+document.getElementById("sPayeeProv").value;
			parameter += "&sPayeeCity="+document.getElementById("sPayeeCity").value;
			parameter += "&sPayeeBankName="+document.getElementById("sPayeeBankName").value;
			parameter += "&sPayeeAcctNoZoom="+document.getElementById("sPayeeAcctNoZoomCtrl").value;
			parameter += "&sPayeeNameZoomAcct="+document.getElementById("sPayeeNameZoomAcctCtrl").value;
			send_request("post","c003-c-1.jsp?"+parameter,parameter,"XML",populateList);
        }
    }
    function populateList() {
	    if (http_request.readyState != 4) // �ж϶���״̬
			return;
			
		if (http_request.status != 200)  // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
		{
			alert("���������ҳ�����쳣��");
			return;
		}
    
    	var flag = http_request.responseText;
    	if(flag>0){
    		if(!confirm("�������Ƶļ�¼���Ƿ�������棿")){
    			return false;
    		}else{
    			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
				for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    			showSending();
    			frmzjhb.submit();
    		}
    	}else{
    		frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
			for(var i=0;i<document.frames.length;i++)
			{
				if(document.frames[i].document.getElementById("transCode").value != "")
				{
					document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
				}
			}
    		showSending();			
            frmzjhb.submit();
    	}
    		
    }	
    
    function doSaveAndApprovalInit()
    {
    	
    	var varRemitSpeed = -1;
    	
    	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY ) { %>
    		
	    	if(frmzjhb.remitSpeed.checked == true)
		 	{
		 		varRemitSpeed = "<%=Constant.remitSpeedType.RAPID %>";
		 	}
		 	else
		 	{
		 		varRemitSpeed = "<%=Constant.remitSpeedType.GENERAL %>";
		 	}
	 	<% } %>
    	
		var signatureValue;
		document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		
		if(validate() == true){
		    //ǩ��
			<%
			if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
				
				if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
					&& !financeInfo.isRefused()){		
			%>
					//���⴦��
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
				<%}%>		
				signatureValue = DoSign(frmzjhb,nameArray,valueArray);					
				//ǩ�����ɹ������������
				if(signatureValue == ""){
					alert("ǩ�����ɹ����޷����б��棡");
					return false;
				}	
			<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>	
				
				try{
					signatureValue = DoSignNetSign(frmzjhb,nameArray,valueArray);
				}catch(e){
					alert(e.description);
				}		
				
				//ǩ�����ɹ������������
				if(signatureValue == ""){
					alert("ǩ�����ɹ����޷����б��棡");
					return false;
				}
			<%}%>
		
		if(validate() == true){
		
			//-------------------���ָ����֤---��ʼ----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //ָ����֤
			$.ajax(
			{
				  type:'post',
				  url:"<%=strContext%>/fingerprintControl.jsp",
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
					  	    doSaveAndApprovalInit();// �ٴ��ύ
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("ָ����֤���������²ɼ�");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//���ؿؼ�
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit();// �ٴ��ύ
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
			//-------------------���ָ����֤---����----------------
		
			//ȷ�ϱ��沢�ύ����
			if (!confirm("�Ƿ񱣴沢�ύ������"))
			{
				$("#Ver").val("");
				return false;
			}
		}
		
		//��ѯ�Ƿ�����ί���տ����ɵ���ת add by xlchang 2010-12-06 �������
		invocationControl(frmzjhb);
		
		frmzjhb.<%=SignatureConstant.SIGNATUREVALUE%>.value = signatureValue;
		frmzjhb.action = "c003-c.jsp?flag=commit&sign=<%=sign%>&remitSpeed="+varRemitSpeed;
		
			var parameter = "";
			parameter += "PayerAcctNo="+document.getElementById("sPayerAccountNoZoomCtrl").value;
			parameter += "&nRemitType="+document.getElementById("nRemitType").value;
			parameter += "&dAmount="+document.getElementById("dAmount").value;
			parameter += "&tsExecute="+document.getElementById("tsExecute").value;
			parameter += "&nRemitTypeHidden="+document.getElementById("nRemitTypeHidden").value;
			parameter += "&lInstructionID="+document.getElementById("lInstructionID").value;
			parameter += "&PayeeAcctNo="+document.getElementById("sPayeeBankNoZoomCtrl").value;
			parameter += "&sPayeeProv="+document.getElementById("sPayeeProv").value;
			parameter += "&sPayeeCity="+document.getElementById("sPayeeCity").value;
			parameter += "&sPayeeBankName="+document.getElementById("sPayeeBankName").value;
			parameter += "&sPayeeAcctNoZoom="+document.getElementById("sPayeeAcctNoZoomCtrl").value;
			parameter += "&sPayeeNameZoomAcct="+document.getElementById("sPayeeNameZoomAcctCtrl").value;
			send_request("post","c003-c-1.jsp?"+parameter,parameter,"XML",populateSave);
			
			
		}
    }
    
    function populateSave() {
	    if (http_request.readyState != 4) // �ж϶���״̬
			return;
			
		if (http_request.status != 200)  // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
		{
			alert("���������ҳ�����쳣��");
			return;
		}
    
    	var flag = http_request.responseText;
    	if(flag>0){
    		if(!confirm("�������Ƶļ�¼���Ƿ�������棿")){
    			return false;
    		}else{
    			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
				for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    			showSending();
    			frmzjhb.submit();
    		}
    	}else{
    		frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
			for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    		showSending();			
            frmzjhb.submit();
    	}
    		
    }
    /* ȡ�������� */
    function cancelme()
    {
		if (frmzjhb.lInstructionID.value == -1)
		{	
			frmzjhb.action="c001-c.jsp";
			frmzjhb.submit();
		}
		else
		{
			
        		history.go(-1);
			
		}
    }
	
//Added by ylguo(��Ӣ��)ȥ���ո�
function dropSpace(obj){
	var s = obj.value;
	var ss = "";
	ss = Trim(s);
	obj.value = ss;
}

//Added by ylguo at 2009-01-06,�ж��˺��Ƿ�Ϊȫ��
function quanjiao()
{
    if(frmzjhb.sPayerAccountNoZoomCtrl != null && frmzjhb.sPayerAccountNoZoomCtrl.value != "")
       {	
       		var sss1 = frmzjhb.sPayerAccountNoZoomCtrl.value;
       		if (sss1.length>0)
		    {
		       for (var i = 0; i < sss1.length; i++)
		        {
		            unicode=sss1.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					    frmzjhb.sPayerAccountNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       	}
       if(frmzjhb.sPayerAccountNoDownZoomCtrl != null && frmzjhb.sPayerAccountNoDownZoomCtrl.value != "")
       {
       		var sss2 = frmzjhb.sPayerAccountNoDownZoomCtrl.value;
       		if (sss2.length>0)
		    {
		       for (var i = 0; i < sss2.length; i++)
		        {
		            unicode=sss2.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					    frmzjhb.sPayerAccountNoDownZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeAccountInternalCtrl != null && frmzjhb.sPayeeAccountInternalCtrl.value != "")
       {
       		var sss3 = frmzjhb.sPayeeAccountInternalCtrl.value;
       		if (sss3.length>0)
		    {
		       for (var i = 0; i < sss3.length; i++)
		        {
		            unicode=sss3.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					    frmzjhb.sPayeeAccountInternalCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeBankNoZoomCtrl != null && frmzjhb.sPayeeBankNoZoomCtrl.value != "")
       {
       		var sss4 = frmzjhb.sPayeeBankNoZoomCtrl.value;
       		if (sss4.length>0)
		    {
		       for (var i = 0; i < sss4.length; i++)
		        {
		            unicode=sss4.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					    frmzjhb.sPayeeBankNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeAcctNoZoomCtrl != null && frmzjhb.sPayeeAcctNoZoomCtrl.value != "")
       {
       		var sss5 = frmzjhb.sPayeeAcctNoZoomCtrl.value;
       		if (sss5.length>0)
		    {
		       for (var i = 0; i < sss5.length; i++)
		        {
		            unicode=sss5.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					   frmzjhb.sPayeeAcctNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       	}
       if(frmzjhb.sPayeeAcctNoDownZoomCtrl != null && frmzjhb.sPayeeAcctNoDownZoomCtrl.value != "")
       {
       		var sss6 = frmzjhb.sPayeeAcctNoDownZoomCtrl.value;
       		if (sss6.length>0)
		    {
		       for (var i = 0; i < sss6.length; i++)
		        {
		            unicode=sss6.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("�˺Ų�������ȫ���ַ������������ַ�");
					    frmzjhb.sPayeeAcctNoDownZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			} 
       }
      return true;
}
//�ж��˻����,ֻ�������ֺ�-
function checkAccountNO(accountNoCtrl)
{
	var patrn=/^([0-9]|[\\-])*$/; 

	if(accountNoCtrl!=null && accountNoCtrl.value!='')
	{
		var tempaccountNoCtrl=Trim(accountNoCtrl.value);
		if(!patrn.exec(tempaccountNoCtrl)) 
		{
			return false;
		}
	}
	return true ;
}
 /**
 * ��������������ϣ�return ture
 * ���У�����return false
 */
    function validate()
    {
 /*�������У��*/
		
		// �н���ӣ��»��ĸ�����ϼ���
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>) //���и�����»���Ա��λ
		{
			if (frmzjhb.sPayerName.value == "")
			{
				alert("������Ʋ���Ϊ��");
				frmzjhb.sPayerName.focus();
				return false;
			}
			
			if (frmzjhb.sPayerAccountNoDownZoomCtrl.value == "")
			{
				alert("����˺ţ���ӷŴ���ѡ��");
				frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sName.value < 0)
			{
				alert("����˺ţ���ӷŴ���ѡ��");
				frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
				return false;
			}
		}
		else                                                                 //����
		{
			// ������Ϸǿ�У�� 
			if (frmzjhb.sPayerName.value == "")
			{
				alert("������Ʋ���Ϊ�գ���ѡ��");
				frmzjhb.sPayerName.focus();
				return false;
			}
		
			if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
			{
				alert("����˺Ų���Ϊ�գ���ѡ��");
				frmzjhb.sPayerAccountNoZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sName.value < 0)
			{
				alert("����˺ţ���ӷŴ���ѡ��");
				frmzjhb.sPayerAccountNoZoomCtrl.focus();
				return false;
			}
		}

/*�տ����У��*/

		// ��ʽ�Ƿ�Ϊ��
		if (iRemitType <= 0)
		{
			alert("��ʽ����Ϊ�գ���ѡ��");
			frmzjhb.nRemitType.focus();
			return false;
		}
		
		// ���ݻ�ʽ����ת�����տ���Ͻ��зǿ�У�� 
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // ��ʽ��ת
		{
			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankAccountNO.value < 0)
			{
				alert("�տ�˺���ӷŴ���ȡ��");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeNameZoomBankCtrl.value == "")
			{
				alert("��ѡ���տ���ƻ��˺�");
				frmzjhb.sPayeeNameZoomBankCtrl.focus();
				return false;
			}

		}
		// ���л����и���---��˾���������и���---�����˻� ���л�ʽ���˺ź��տ���Ƶ�У�� 
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %> ||iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %> || iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)   // ��ʽ���и���
		{			
					
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("�տ�˺Ų���Ϊ�գ�������");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeNameZoomAcctCtrl.value == "")
			{
				alert("�տ���Ʋ���Ϊ�գ�����д�տ���ƻ��˺�");
				frmzjhb.sPayeeNameZoomAcctCtrl.focus();
				return false;
			}
			
			if (!InputValid(frmzjhb.sPayeeNameZoomAcctCtrl, 1, "string", 1, 1,15,"�տ����"))
			{
				return false;
			}
			
			if (frmzjhb.itemID != null)
			{
				if (frmzjhb.itemID.value <= 0)
				{
					alert("Ԥ����Ŀ����Ϊ��");
					frmzjhb.itemIDCtrl.focus();
					return false;
				}
			}
		}
		
		// ���ݻ�ʽ���ڲ�ת�ˣ����տ���Ͻ��зǿ�У�� 
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// ��ʽ���ڲ�ת��
		{
			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value =="")
			{
				alert("�տ�˺Ų���Ϊ�գ���ѡ��");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.nPayeeAccountID.value <0)
			{
				alert("�տ�˺ţ���ӷŴ���ѡ��");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankNoZoomCtrl.value !=frmzjhb.hidsPayeeBankNoZoomCtrl.value)
			{
				alert("�տ�˺ţ���ӷŴ���ѡ��");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
						
			if (frmzjhb.sPayeeName.value == "")
			{
				alert("�տ���Ʋ���Ϊ�գ�������");
				frmzjhb.sPayeeName.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeName.value !=frmzjhb.hidsPayeeName.value)
			{
				alert("�տ���ƣ���ӷŴ���ѡ�����");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			// �ڲ�ת�ʵ��տ������ͨ���ı���ĳ��ȿ�����
		}
		
		//�н��»���Ա��λ�տ���ϼ�������
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>) //�н������и�����»���Ա��λ
		{
			if (frmzjhb.sPayeeAcctNoDownZoomCtrl.value == "")
			{
				alert("�տ�˺Ų���Ϊ�գ���ѡ��");
				frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeNameZoomAcctDownCtrl.value == "")
			{
				alert("��ѡ���տ����");
				frmzjhb.sPayeeNameZoomAcctDownCtrl.focus();
				return false;
			}		
			
			if (frmzjhb.itemID != null)
			{
				if (frmzjhb.itemID.value <= 0)
				{
					alert("Ԥ����Ŀ����Ϊ�գ�������");
					frmzjhb.itemIDCtrl.focus();
					return false;
				}
			}
		}
		
/* ������տ���ۺ�У��*/	
        if((frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeBankNoZoomCtrl.value ) && (iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) )
		{
			alert("������տ������ͬ");
			frmzjhb.sPayeeBankNoZoomCtrl.focus();
			return false;
		}
		if((frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeBankNoZoomCtrl.value) && (iRemitType == <%= OBConstant.SettRemitType.SELF %>))
		{
			alert("������տ������ͬ");
			frmzjhb.sPayeeBankNoZoomCtrl.focus();
			return false;
		}
		
/* ����ص�У��:���л����и���---��˾���������и���---�����˻� ���л�ʽ */
       if(frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.BANKPAY %> || frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.FINCOMPANYPAY %> || frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)   // ���и���
		{
			if (!InputValid(frmzjhb.sPayeeProv, 1, "string", 1, 1, 15,"�����(ʡ)"))
			{
				return false;
			}
			
			if (!InputValid(frmzjhb.sPayeeCity, 1, "string",1, 1, 15,"�����(��)"))
			{
				return false;
			}
			
			if(frmzjhb.sPayeeCity.value != "")
			{
				var str = frmzjhb.sPayeeCity.value;
				str = str.substring(str.length-1,str.length);
				if(str=="��")
				{
					alert("��ȥ����������е� '��' ");
					frmzjhb.sPayeeCity.focus();
					return false;
				}
			}
			
			//modify by xwhe 2008-10-13 
			if (!InputValid(frmzjhb.sPayeeBankName, 1, "string",1, 1, 15,"����������"))
			{
				return false;
			}
		}
		
/* �н������и�����»���Ա��λ */
		if(frmzjhb.nRemitType.value == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER%>)
		{
			if (!InputValid(frmzjhb.sPayeeProvDown, 1, "string", 1, 1, 15,"�����(ʡ)"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.sPayeeCityDown, 1, "string",1, 1, 15,"�����(��)"))
			{
				return false;
			}
			if(frmzjhb.sPayeeCityDown.value != "")
			{
				var str = frmzjhb.sPayeeCityDown.value;
				str = str.substring(str.length-1,str.length);
				if(str=="��")
				{
					alert("��ȥ����������е� '��' ");
					frmzjhb.sPayeeCityDown.focus();
					return false;
				}
			}
		}
      
/* ���У�� */
		if(!isFloat(frmzjhb.dAmount.value))
		{
			alert("���׽��ֻ������ֵ");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		if(frmzjhb.dAmount.value<0)
		{
			alert("���׽���Ϊ��");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		if(!checkAmount(frmzjhb.dAmount, 1, "���׽��"))
		{
			return false;
		}
		
		// ���������׽�� 
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		// �н����룬�����ʽ���»����жϿ��������׽��
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)
		{
			dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalanceDown.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		}
		// ���������׽�0������ʾ���������㣬���������뻮����
		if (dBalance < 0) 
		{
			alert("�������㣬���������뻮����");
			frmzjhb.dAmount.focus();
			return false;
		}
		
/* ִ����У�� */
		if(document.all("tsExecute").value=="")
		{
			alert("ִ���ղ���Ϊ�գ���¼��");
			document.all("tsExecute").focus();
			return false;
		}
		if(!CompareDateString(frmzjhb.systemDateTime.value,frmzjhb.tsExecute.value))
		{
			alert("ִ���ղ���С��ϵͳ�����գ�");
			frmzjhb.tsExecute.focus();
			return false;
		}
		
		
/* �����;У�� */
		// У������;������	
		if(Trim(frmzjhb.sNoteCtrl.value) == "")
		{
		   alert("�����;����Ϊ��");
		   frmzjhb.sNoteCtrl.focus();
		   return false;
		
		}
		// �����; 
		//if (!InputValid(frmzjhb.sNoteCtrl, 1, "string", 1, 1,6,"�����;"))
		//{
		//	return false;
		//}
		
		// Added by ylguo �����˺�Ϊȫ�ǵ�����ģ£գ�
		//if(!quanjiao())//����˻�����û��ȫ�ǵ��ַ�
    	//	return false;
    	 if(frmzjhb.sPayerAccountNoZoomCtrl != null && frmzjhb.sPayerAccountNoZoomCtrl.value != "")
       {
	    	if(!checkAccountNO(frmzjhb.sPayerAccountNoZoomCtrl))
	    	{
	    	  alert("����˻��Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayerAccountNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayerAccountNoDownZoomCtrl != null && frmzjhb.sPayerAccountNoDownZoomCtrl.value != "")
       {
       		if(!checkAccountNO(frmzjhb.sPayerAccountNoDownZoomCtrl))
	    	{
	    	  alert("����˻��Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
	    	  return false;
	    	}
       }	
    	 if(frmzjhb.sPayeeAccountInternalCtrl != null && frmzjhb.sPayeeAccountInternalCtrl.value != "")
       {
       		if(!checkAccountNO(frmzjhb.sPayeeAccountInternalCtrl))
	    	{
	    	  alert("�տ�˺Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayeeAccountInternalCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeBankNoZoomCtrl != null && frmzjhb.sPayeeBankNoZoomCtrl.value != "")
       {
      		 if(!checkAccountNO(frmzjhb.sPayeeBankNoZoomCtrl))
	    	{
	    	  alert("�տ�˺Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayeeBankNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeAcctNoZoomCtrl != null && frmzjhb.sPayeeAcctNoZoomCtrl.value != "")
       {
       		 if(!checkAccountNO(frmzjhb.sPayeeAcctNoZoomCtrl))
	    	{
	    	  alert("�տ�˺Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeAcctNoDownZoomCtrl != null && frmzjhb.sPayeeAcctNoDownZoomCtrl.value != "")
       {
       	 if(!checkAccountNO(frmzjhb.sPayeeAcctNoDownZoomCtrl))
	    	{
	    	  alert("�տ�˺Ų��Ϸ�������¼��");
	    	  frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();
	    	  return false;
	    	}
       }	
    return true;
}
 
//�н��»�,�ÿ�
function trimXH()
{
	if(frmzjhb.sPayeeAcctNoDownZoomCtrl.value==' ')
	{
		frmzjhb.sPayeeAcctNoDownZoomCtrl.value='';
	}
	if(frmzjhb.sPayeeNameZoomAcctDownCtrl.value==' ')
	{
		frmzjhb.sPayeeNameZoomAcctDownCtrl.value='';
	}
	if(frmzjhb.sPayeeProvDown.value==' ')
	{
		frmzjhb.sPayeeProvDown.value='';
	}
	if(frmzjhb.sPayeeCityDown.value==' ')
	{
		frmzjhb.sPayeeCityDown.value='';
	}
	if(frmzjhb.sPayeeBankNameDown.value==' ')
	{
		frmzjhb.sPayeeBankNameDown.value='';
	}

}
	
function IsAccountN0Int( d_int)
{
		var checkOK = "0123456789";
		var checkStr = d_int;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		if (ch != ",")
			allNum += ch;
		}
		return (allValid)
 }	
 
function repeatTrans()
{
	
	
	
	if (confirm(document.frmzjhb.strErrMessage.value))
    {
	
		frmzjhb.action = "c003-c.jsp";
		document.frmzjhb.BudgetPass.value="true";


		if (validate() == true)
        {
			showSending();
			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
            frmzjhb.submit();
		}

    }
	else
	{
		document.frmzjhb.BudgetPass.value="";
		document.frmzjhb.strErrMessage.value="";
		frmzjhb.action = "c001-c.jsp";
		showSending();
			
        frmzjhb.submit();
		//window.location="c001-c.jsp";
	}
	

}  
</script>

<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	<%if(financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER){%>
	firstFocus(frmzjhb.sPayerAccountNoDownZoomCtrl);
	<%}else{%>
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	<%}%>
	//setSubmitFunction("addme(frmzjhb)");
	setFormName("frmzjhb");		
	
	//��ѯ�Ƿ�����ί���տ����ɵ���ת add by xlchang 2010-12-06 �������
	initCheck(frmzjhb);
	function initCheck(frm) {
		if ("<%=isConsignReceive%>"=="true") {
			frm.sPayerName.disabled = true;
			frm.sPayerAccountNoZoomCtrl.disabled = true;
			frm.dPayerCurrBalance.disabled = true;
			frm.dPayerUsableBalance.disabled = true;
			frm.nRemitType.disabled = true;
			frm.sPayeeBankNoZoomCtrl.disabled = true;
			frm.sPayeeName.disabled = true;
			frm.dAmount.disabled = true;
			frm.sChineseAmount.disabled = true;
			frm.sNoteCtrl.disabled = true;
			firstFocus(frmzjhb.tsExecute);
			frm.tsExecute.onfocus="nextfield ='butAdd';"
			frm.butReset.disabled = true;
		}
	}
	//���ÿؼ�
	function invocationControl(frm) {
		frm.sPayerName.disabled = false;
		frm.sPayerAccountNoZoomCtrl.disabled = false;
		frm.dPayerCurrBalance.disabled = false;
		frm.dPayerUsableBalance.disabled = false;
		frm.nRemitType.disabled = false;
		frm.sPayeeBankNoZoomCtrl.disabled = false;
		frm.sPayeeName.disabled = false;
		frm.dAmount.disabled = false;
		frm.sChineseAmount.disabled = false;
		frm.sNoteCtrl.disabled = false;
	}
</script>
<%	
}
catch (IException ie)
{
       OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
	return;
}
/* ��ʾ�ļ�β */
OBHtml.showOBHomeEnd(out);	
%>
<%@ include file="/common/SignValidate.inc" %>