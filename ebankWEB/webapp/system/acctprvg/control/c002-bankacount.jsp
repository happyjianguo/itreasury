<%--
 ҳ������ ��c002.jsp
 ҳ�湦�� : �˻���Ȩ�������ҳ��
 ��    �� ��ruiwang
 ��    �� ��2006-11-8
 ת��ҳ�� ��c001.jsp
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
				com.iss.itreasury.ebank.obsystem.dao.OB_AcctPrvgByClientDaoBankAcount,
				com.iss.itreasury.ebank.obsystem.bizlogic.OBAcctPrvgBizBankAcount,
				com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo,
				com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo,
				com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo,
				java.util.Iterator,
				java.util.Vector,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
				
				
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>  
<% 
try {
	 /** Ȩ�޼�� **/
   Log.print("=================����ҳ��budget/constitute/control/c002-bankacount.jsp=========");
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
	Vector vAdd=new Vector();
	Vector vDel=new Vector();	
	int num=0;
	String strNextPageURL="../control/c001-bankacount.jsp";
	Collection con;
	long clientId=sessionMng.m_lClientID;
	Log.print("********************��¼��ID��"+clientId);
	
	//�õ�v001.jspҳ���е�number
	
	String tmp1=(String)request.getAttribute("number");
    	if( (tmp1!=null)&&(tmp1.length()>0) )
    		num=Integer.valueOf(tmp1).intValue();
    	Log.print("number:"+num);
    long currencyid=sessionMng.m_lCurrencyID; 	
	
	//ɾ��ԭ�ȵļ�¼
	OBAcctPrvgBizBankAcount bz=new OBAcctPrvgBizBankAcount();
	OB_AcctPrvgByClientDaoBankAcount obABCD=new OB_AcctPrvgByClientDaoBankAcount();
	con=obABCD.getAcctIDs(clientId,currencyid);
	if(con==null){
		
		Log.print("********************conΪ��");
	}
	else{
		Log.print("************con��Ϊ��");
	}
	Iterator it=con.iterator();
	while(it.hasNext()){
		AccountInfo accountInfo=(AccountInfo)it.next();
		vDel.add(accountInfo);
	}
	Log.print("&&&&&&&&&&&&&&&&&&&&&&&&&&����delAcctPrvgs(vDel)");
	bz.delAcctPrvgs(vDel);
	Log.print("cҳ��ɾ�� ���");
	//�����µ�״̬��¼��
	String myCheck="checkbox";
	int temp=0;

	long myClientId=-1;
	long myAccountId=-1;
	Log.print("####################ֵ"+request.getAttribute("checkbox0"));
	for(int k=0;k<num;k++){
		Log.print("~~~~~~~~~~~~~~~~~~~~~~~����"+k+"��kѭ��");
		Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~myCheck+temp:"+myCheck+temp);
		if(request.getParameterValues(myCheck+temp)!=null){
			String[] varieties = request.getParameterValues(myCheck+temp);
			Log.print("�õ�varieties[]");
			for(int i=0;i<varieties.length;i++){
				if(varieties[i].trim().length()>0){
					int tmp=0;
					Log.print("varieties["+i+"]ֵ:"+varieties[i]);
					for(int j=0;j<varieties[i].length();j++){
						char c=varieties[i].charAt(j);
						if(c==','){
							Log.print("myClientIdֵ:"+varieties[i].substring(0,j));
							myClientId=Long.parseLong(varieties[i].substring(0,j));
							tmp=j+1;
							break;
						}
					}
					Log.print("111111111111");
					Log.print("myAccountId:"+varieties[i].substring(tmp,(varieties[i].length())));
					myAccountId=Long.parseLong(varieties[i].substring(tmp,(varieties[i].length())));
					AcctPrvgByClientInfo acctPrvgByClientInfo=new AcctPrvgByClientInfo();
					acctPrvgByClientInfo.setClientID(myClientId);
					acctPrvgByClientInfo.setAccountID(myAccountId);
					//bz.saveAcctPrvgs(acctPrvgByClientInfo);
					vAdd.add(acctPrvgByClientInfo);
				}
				
			}
		}
		temp++;
	}
	bz.saveAcctPrvgs(vAdd);
	
	request.setAttribute("clientInfo",con);
%>

<%
	sessionMng.getActionMessages().addMessage("����ɹ�");
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
	String strNextPageURL="../control/c001-gd.jsp";
	exp.printStackTrace();
	sessionMng.getActionMessages().addMessage("����ʧ��");
	Log.print("Next Page URL:" + strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );

}

%>