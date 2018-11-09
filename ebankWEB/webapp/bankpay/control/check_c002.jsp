<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* ����̶����� */
	String strTitle="[���л��]";
	
%>
<%
    Vector vcResult = new Vector(1);//������Ϣ
 	long lID = -1;//ָ��id
	String[] lID1 = new String []{};//ָ��id����
	int i = -1;//�����±����
	try{
	/* ʵ�ֲ˵����� */
	OBHtml.validateRequest(out,request,response);
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
%>
<%	
  		lID1 =  request.getParameterValues("lID1");
		System.out.println("++++++++++++���˵�id++++++++"+lID1);
		i = lID1.length;//�ύ�ĸ���
		System.out.println("�ύָ��ĸ�����"+ i);
	    OBBankPayInfo  info=null;
	
	
		for(int j=0;j<i;j++)
		{
			info=new OBBankPayInfo();
			info.convertRequestToDataEntity(request);
			info.setNclientid(sessionMng.m_lClientID);
			System.out.print("�ͻ�id"+info.getNclientid());
			System.out.print("�ͻ�id"+info.getFormatDtexecute());
			info.setNcurrencyid(sessionMng.m_lCurrencyID);
			System.out.print("����id"+info.getNcurrencyid());
			System.out.println("**************************"+info);	
			//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
			/* ��ʼ��EJB */
			OBFinanceInstrHome financeInstrHome = null;
			OBFinanceInstr financeInstr = null;
			financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
			financeInstr = financeInstrHome.create();
			
			long lInstructionID=-1;
			//check
			lID = Long.parseLong(lID1[j].split("####")[0]);
			System.out.print(lID);
		    lInstructionID=financeInstr.checkBankPay(lID,sessionMng.m_lUserID);
			info=financeInstr.findByID(lID);	
			vcResult.add(info);	
		}		
		request.setAttribute("info",vcResult);
		/* ��ȡ�����Ļ��� */
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/bankpay/view/check_v002.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		/* forward�����ҳ�� */
		rd.forward(request, response);
	} 
	catch(IException ie) 
	{
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }

%>