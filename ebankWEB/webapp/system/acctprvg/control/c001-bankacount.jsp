<%--
 ҳ������ ��c001-gd.jsp
 ҳ�湦�� : �����˻���Ȩ����ҳ��
 ��    �� ��zcwang
 ��    �� ��2008-4-29
 ת��ҳ�� ��v001-gd.jsp
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.ebank.obsystem.bizlogic.OBAcctPrvgBizBankAcount,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
				
				
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>  
<% 
try {
	 /** Ȩ�޼�� **/
   Log.print("=================����ҳ��budget/constitute/control/c001-bankacount.jsp=========");
	String strTitle = "Ԥ�����"; 
   //������
    //�û���¼��� 
       if (sessionMng.isLogin() == false)
       {
       	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
       	out.flush();
       	return;
       }

       // �ж��û��Ƿ���Ȩ�� 
       if (sessionMng.hasRight(request) == false)
       {
           out.println(sessionMng.hasRight(request));
       	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
       	out.flush();
       	return;
       }
%>

<%	
	String strNextPageURL="../view/v001-bankacount.jsp";
	Collection con;
	long clientId=sessionMng.m_lClientID;
	long currencyid=sessionMng.m_lCurrencyID;
	Log.print("********************��¼��ID��"+clientId);
	
	OBAcctPrvgBizBankAcount obap=new OBAcctPrvgBizBankAcount();
	con=obap.getAcctPrvgs(clientId,currencyid);
	Log.print("@@@@@@@@@@@@@@@@@@@@@@"+clientId);
	if(con==null){
		
		Log.print("********************conΪ��");
	}
	else{
		Log.print("************con��Ϊ��");
	}
	request.setAttribute("clientInfo",con);
%>

<%
	Log.print("Next Page URL:" + strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
	
%>
<%
}catch( Exception exp ){


	
	
	exp.printStackTrace();
	
	
}

%>