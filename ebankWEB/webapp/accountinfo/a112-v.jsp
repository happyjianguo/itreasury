<%--
/**
 ҳ������ ��a112-v.jsp
 ҳ�湦�� : ������λ�˻�����ӡ
 ��    �� �� kewen hu
 ��    �� �� 2004-01-13
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.ebank.util.eBankPrint"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<script language="javascript" src="/webob/js/Check.js"></script>
<!--<script language="javascript" src="/webob/js/glass.js"></script>-->
<script language="javascript" src="/webob/js/date-picker.js"></script>
<!--<script language="javascript" src="/webob/js/Zoom.js"></script>-->

<% String strContext = request.getContextPath();%>
<% System.out.println("����ҳ��:"+strContext);%>
<%
//�������
String strTitle = "[������λ�˻����]";

		//ǩ���������//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//������������·��
		String nowDate = Env.getSystemDateTimeString();//��ǰ����
		String officeName = Env.getClientName();//���´�����
		//�鿴�Ƿ���ǩ��Ȩ��--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		//String nbilltypeId = EBankDocRiht.ebankDocType[3][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.XSDWZHCX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//�鿴�Ƿ���ǩ��Ȩ��--end----
		double px=300;//������
		double py = 200;//������
		//////////////////////////////////////////////////////////////


try {
    Log.print("\n*******����ҳ��--ebank/accountinfo/a112-v.jsp******\n");

         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* ��ʾ�ļ�ͷ */
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    // ҳ����Ʊ���
    String sAction = null;
    String sActionResult = Constant.ActionResult.FAIL;
    String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
    String strFailPageURL = (String)request.getAttribute("strFailPageURL");

    long lClientID = -1;                                                //�ͻ���ʶ
    long lAccountGroupID = -1;                                          //�˻����ͱ�ʶ
    long lAccountID = -1;                                               //�˺ű�ʶ
    long lCurrencyID = sessionMng.m_lCurrencyID;                        //���ֱ�ʶ
    long lParentCorpID = sessionMng.m_lClientID;                        //ĸ��˾ID
    Timestamp tsEnd = Env.getSystemDate(
        sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);               //����ʱ��
	boolean isNotnull = false;
    String sTemp = null;
    sTemp = (String)request.getParameter("lAccountGroupID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lAccountGroupID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lClientID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lClientID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lAccountID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lAccountID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("tsEnd");
    if (sTemp != null && sTemp.trim().length() > 0) {
        tsEnd = DataFormat.getDateTime(sTemp);
    }
    String fromStr = null;
    sTemp = (String)request.getParameter("fromAccountType");
    if (sTemp != null && sTemp.trim().length() > 0) {
        fromStr = sTemp;
    }

    Collection listType = null;
    Collection listAccount = null;
    Iterator iterator = null;
    OBAccountQueryAmountInfo obaqai = null;
    Connection conn = null;
    OBAccountQueryAmountDao obaqad = new OBAccountQueryAmountDao(conn);
    OBAccountQueryWhere obaqw = new OBAccountQueryWhere();
    obaqw.setCurrencyID(lCurrencyID);
    obaqw.setOfficeID(sessionMng.m_lOfficeID);
    obaqw.setDateTo(tsEnd);
%>
<%
	//IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	eBankPrint.showPrintReport(out,sessionMng,"A4",2,false);
%>

<safety:resources />
<form name="frmPrint" method="post" action="a112-v.jsp">
      <table width="600px" align="center" class="top" border=0 >
        <tr>
          <td height="25">
            <table width="600px" align="center"  border="0" cellspacing="0" cellpadding="3" class="table1">
                <tr class="ItemBody">
                    <td align="center" colspan="5">
                        <b><font style="font-size:22px"><%=sessionMng.m_strClientName%>������λ������˻�����</font></b>
                    </td>
                </tr>
                <tr class="ItemBody">
                    <td align="left">
                    ��ѯ���ڣ�<%=DataFormat.getDateString(tsEnd)%></td>
                    <td width="200"></td>
                    <td align="right">��λ��Ԫ</td>
                </tr>
            </table>
            <table width="600px" align="center" border="0" cellspacing="0" cellpadding="3" class="table1" >
              <tr align="center">
                <td class="td-rightbottom" nowrap>�˻�����</td>
                <td class="td-rightbottom" nowrap>��������</td>
                <td class="td-rightbottom" nowrap>�ʽ����</td>
                <td class="td-rightbottom" nowrap>������</td>
                <td class="td-rightbottom" nowrap>�˺�</td>
              </tr>
<%
//*******�˴����a110-v.jsp��copy����***begin**/
   int iItem = 0;
    double Balance = 0.0;
    double SubSumBalance = 0.0;
    double SumBalance = 0.0;
    double Total = 0.0;
    Collection clientList = null;
    Iterator iterList = null;
    Iterator iterClient = null;
    Iterator iterAccount = null;
    boolean bLoop = false;
    if(lAccountGroupID == -1 && lClientID == -1 && lAccountID == -1)
        bLoop = true;
        //modified by fxzhang 2006-12-28  
        int co=(bLoop?SETTConstant.AccountGroupType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).length:1);
    for(int i=1; i<=co; i++) 
    //for(int i=1; i<=(bLoop?SETTConstant.AccountGroupType.getCodeForZJ().length:1); i++) 
    
    {
        Collection list = new ArrayList();
        if(bLoop) lAccountGroupID = (long) i;
        if (lClientID == -1) {
            if (lAccountID == -1) {
                clientList = obaqad.getChildClientID(obaqw,lParentCorpID,sessionMng.m_lOfficeID,true);
                if (clientList != null) {
                    iterClient = clientList.iterator();
                }
                boolean self=false;
                int k=0;
                while (iterClient != null && iterClient.hasNext()) {
                k++;
                    OBAccountQueryAmountInfo infoClient = (OBAccountQueryAmountInfo) iterClient.next();
                    if(sessionMng.m_lClientID==infoClient.getClientID()) self=true;
                    Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, infoClient.getClientID(),lParentCorpID,self);
                    if (accountList != null) {
                        iterAccount = accountList.iterator();
                        while (iterAccount != null && iterAccount.hasNext()) {
                            OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
                            list.add(infoAccount);
                        }
                    }
                     self=false;
                }
                list = obaqad.compositByAccountTypeID(list);
                list = obaqad.compositByLoanClientTypeID(list);
            } else {
                Collection accountList = obaqad.getAccountInfoByAccountID(obaqw, lAccountGroupID, lAccountID);
                if (accountList != null) {
                    iterAccount = accountList.iterator();
                    while (iterAccount != null && iterAccount.hasNext()) {
                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
                        list.add(infoAccount);
                    }
                }
            }
        } else {
       			  boolean self=false;
             	if(sessionMng.m_lClientID==lClientID) self=true;
            if (lAccountID == -1) {
                Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, lClientID,lParentCorpID,self);
                if (accountList != null) {
                    iterAccount = accountList.iterator();
                    while (iterAccount != null && iterAccount.hasNext()) {
                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
                        list.add(infoAccount);
                          self=false;
                    }
                }
                list = obaqad.compositByAccountTypeID(list);
                list = obaqad.compositByLoanClientTypeID(list);
            } else {
                Collection accountList = obaqad.getAccountInfoByAccountID(obaqw, lAccountGroupID, lAccountID);
                if (accountList != null) {
                    iterAccount = accountList.iterator();
                    while (iterAccount != null && iterAccount.hasNext()) {
                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
                        list.add(infoAccount);
                    }
                }
            }
        }
//*******�˴����a110-v.jsp��copy����***end**/        
        
     if(list!=null && list.size()>0)
	 {
	    isNotnull = true;
	    
        long lLoanClientTypeID = 0;
        long lAccountTypeID = 0;
        String strLoanClientTypeName = "";
        Balance = 0.0;
        SubSumBalance = 0.0;
        //SumBalance = 0.0;
        Total = 0.0;
        if (list != null) {
            iterList = list.iterator();
        }
        while (iterList != null && iterList.hasNext()) {
            OBAccountQueryAmountInfo info = (OBAccountQueryAmountInfo) iterList.next();
            Balance = obaqad.getBalanceByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID) + obaqad.getAmountByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID);
            if (lAccountTypeID == 0 || lAccountTypeID != info.getAccountTypeID()) {
                lAccountTypeID = info.getAccountTypeID();
                if(lLoanClientTypeID != 0) {
%>
              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="right"><%=strLoanClientTypeName==null||strLoanClientTypeName.equals("")?"&nbsp;":strLoanClientTypeName%></div>
                </td>
                <td align="right" class="td-topright" nowrap>
                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-top">&nbsp;</td>
              </tr>
<%
                    SubSumBalance = 0.0;
                }
%>
              <tr>
                <td align="left" class="td-topright" nowrap>
                    <span><%=SETTConstant.AccountType.getName(lAccountTypeID)%></span>
                </td>
                <td align="right" class="td-topright" nowrap> 
                  <div align="right">�ܼ�</div>
                </td>
                <td align="right" class="td-topright" nowrap>
                    <span id="sum<%=++iItem%>"></span>
                    <%
                 if (iItem > 1) {
                   %>
                    <script language="JavaScript">
                        sum<%=iItem-1%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
                    </script>
                    <%
                }
                    %>
                </td>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-top">&nbsp;</td>
              </tr>

              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="left"><%=info.getOpenAccountName()==null||info.getOpenAccountName().equals("")?"&nbsp;":info.getOpenAccountName()%></div>
                </td>
                <td align="right" class="td-topright" nowrap>
                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="left"><%="����˾"%></div>
                </td>
                <td align="center" class="td-top" nowrap>
                <%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%>
                </td>
              </tr>
<%
                lLoanClientTypeID = info.getLoanClientTypeID();
                strLoanClientTypeName = info.getLoanClientTypeName();
                SumBalance = 0.0;
            } else {
                if(lLoanClientTypeID != 0 && lLoanClientTypeID != info.getLoanClientTypeID()) {
%>
              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="right"><%=strLoanClientTypeName==null||strLoanClientTypeName.equals("")?"&nbsp;":strLoanClientTypeName%></div>
                </td>
                <td align="right" class="td-topright" nowrap>
                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-top">&nbsp;</td>
              </tr>
<%
                    SubSumBalance = 0.0;
                }
%>
              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="left"><%=info.getOpenAccountName()==null||info.getOpenAccountName().equals("")?"&nbsp;":info.getOpenAccountName()%></div>
                </td>
                <td align="right" class="td-topright" nowrap>
                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="left"><%="����˾"%></div>
                </td>
                <td align="center" class="td-top" nowrap>
                <%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%>
                </td>
              </tr>
<%
                lLoanClientTypeID = info.getLoanClientTypeID();
                strLoanClientTypeName = info.getLoanClientTypeName();
            }
            SubSumBalance += Balance;
            SumBalance += Balance;
            Total += Balance;

          
        }
	if(i==co-2){
%>
              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="right"><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>�ܼƣ�</div>
                </td>
                <td align="right" class="td-topright" nowrap>
                  <%=Total==0.0?"0.00":DataFormat.formatDisabledAmount(Total,2)%>
                 </td>
                <td align="left" class="td-topright"   id="signaturePosition_4">&nbsp;</td>
                <td align="left" class="td-top">&nbsp;</td>
              </tr>

<%
	}else{
 %>
              <tr>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-topright" nowrap> 
                  <div align="right"><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>�ܼƣ�</div>
                </td>
                <td align="right" class="td-topright" nowrap>
                <%=Total==0.0?"0.00":DataFormat.formatDisabledAmount(Total,2)%></td>
                <td align="left" class="td-topright">&nbsp;</td>
                <td align="left" class="td-top">&nbsp;</td>
              </tr>
<%
}
        Total = 0.0;
    }
   }
    // �Ͽ�����
    obaqad.closeConn();
    //end of FOR
    if(!isNotnull)
    {
    	
%>
		  <tr align="center">
                <td class="td-top" nowrap></td>
                <td class="td-top" nowrap></td>
                <td class="td-top" nowrap></td>
                <td class="td-top" nowrap></td>
                <td class="td-top" nowrap></td>
           </tr>
		
<%
    }
%>
<script language="JavaScript">
try{
    	sum<%=iItem%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
    }catch(e){
    
    }
</script>
            </table>
        </td>
    </tr>
</table>
<br>
<table width="600px" align="center" >	

	<tr>
	 <td height="25" align="left">
	 �����ˣ�<%=sessionMng.m_strUserName%>
	 </td>
	 <td height="25" align="right" id="printDate">
	 ��ӡʱ�䣺<%=Env.getSystemDateString()%>
	 </td>
	</tr>
</table>
</form>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>


<%

	if(hasRight){

 %>
<BODY language="javascript" onResize="ReSetSignaturePosition()"  style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl"  codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196" classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
<param name="ServiceUrl" value="<%=basePath%>/NASApp/SignatureServlet">                       <!--��ȥ���ݿ������Ϣ-->
<param name="WebAutoSign" value="0">                     <!--�Ƿ��Զ�����ǩ��(0:�����ã�1:����)-->
<param name="PrintControlType" value=2>                  <!--��ӡ���Ʒ�ʽ��0:������  1��ǩ�·���������  2�������̿��ƣ�-->
<param name="MenuDocVerify" value=false>                 <!--�˵��ĵ���֤�ĵ�-->
<param name="MenuServerVerify" value=false>              <!--�˵�������֤,����汾ר��-->
<param name="MenuDigitalCert" value=false>               <!--�˵�����֤��-->
<param name="MenuDocLocked" value=false>                 <!--�˵��ĵ�����-->
<param name="MenuDeleteSign" value=false>                <!--�˵�����ǩ��-->
<param name="MenuMoveSetting" value=true>                <!--�˵���ֹ�ƶ�-->
<param name="PrintWater" value=false>                    <!--�Ƿ��ӡˮӡ-->
</OBJECT>
</BODY>
<script language="javascript">
/**
	��ӡʱǩ��λ�õĵ���    add by zhanglei   2010.06.25
**/
window.onbeforeprint=function(){
	setPirntPosition();
}
window.onafterprint=function(){
	ReSetSignaturePosition();
}
		try{
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-120);
		    document.all.SignatureControl.EnableMove = "false";          //����ǩ���Ƿ�����ƶ�
		    document.all.SignatureControl.PassWord = "123456";           //ǩ������,ϵͳĬ��Ϊ"",�����øĲ������ǩ�º󵯳���ѡ��ǩ�´����е����뽫Ĭ��Ϊ������      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //��ȡ������ѡ��ǩ�µĴ����Ƿ�ɼ�
		    document.all.SignatureControl.FieldsList = "strTransNos=���ҵ����";          //��ȡ������ǩ�±�����Ϣ�б�
		    document.all.SignatureControl.SaveHistory = "false";         //��ȡ�������Ƿ񱣴���ʷ��¼true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //��ȡ������ǩ�µ��û�����
		    //document.all.SignatureControl.DivId = oPageSet.showSignatureName;          //��ȡ������ǩ�����ڵĲ�
		    document.all.SignatureControl.PositionByTagType = 0;
		    document.all.SignatureControl.Position(sx,sy);      //����ǩ��ʲôλ����Position��(0:���Ͻǡ�1:�м䡢2:���Ͻ�)
		    document.all.SignatureControl.ValidateCertTime = false;      //�����Ƿ���֤֤����ڻ�δ����
		    document.all.SignatureControl.ExtParam = "11111111|11";//transNo
		    document.all.SignatureControl.ExtParam1 = "<%=nowDate%>";          //����ǩ�¸�����Ϣ
		    //document.all.SignatureControl.SetWaterInfo("����ר��","����",0X0000FF,0);//����ǩ������ˮӡ��Ϣ
		    document.all.SignatureControl.WebSetFontOther(true,"","0","����",7,"$0000FF",false);//����ǩ��ͼ��������Ϣ(����ʱ�䡢ǩ����Ա�������)��ʾģʽ
		    document.all.SignatureControl.DefaultSignTimeFormat = 8;    //����ǩ�¸���ʱ��Ĭ�ϸ�ʽ
		    document.all.SignatureControl.SetSignTextOffset(0,30);      //���ø����ǵĸ�����Ϣ(����ʱ��)��ƫ����
		  }catch(e){
		    alert(e);
		  }
		    try{
		    	document.all.SignatureControl.RunSignature();               //ִ��ǩ��  
		    }catch(e){
		    	alert("���ǩ�´�������ϵ������Ա");
		    }
	//������ڴ�С�仯�ˣ�ǩ�µ�λ��ҲҪ�ı䡣  add by zhanglei  2010.06.11	    
	function ReSetSignaturePosition(){
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-120);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}	    
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = 570;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
	function getElementLeft(element){
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		
		while (current !== null){
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		
	����return actualLeft;
	}
</script>
<%
	}
%>

