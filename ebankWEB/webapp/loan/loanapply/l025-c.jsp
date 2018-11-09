<%
/**
 * ҳ������ ��l025-c.jsp
 * ҳ�湦�� : ɾ���ƻ���Ϣ
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 *			  
 * ������� ��type			��������  
 *			  
 *
 * �������� ��type			��������
 * 			  LoanApplyInfo �������������Ϣ
 * ת��ҳ�� : ../view/v1008.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.ebank.obloanapply.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	long        loanID=-1;          //��ˮ��
	String action="";
		
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
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
		
		
		String tmp="";
		long rsCount=-1;
		long todel[]=null;
		
		tmp=(String)request.getAttribute("lRsCount");		//��ˮ��
		if ( (tmp!=null)&&(tmp.length()>0) )
			rsCount=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lLoanID");		//��ˮ��
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
			
		tmp=(String)request.getAttribute("txtAction");		//��ˮ��
		if ( (tmp!=null)&&(tmp.length()>0) )
			action=tmp;	
			
		todel=new long[(int)rsCount];
			
		for ( int i=0;i<rsCount;i++ )
		{
			tmp=(String)request.getAttribute("txtlID"+i);		//��ˮ��
			if ( (tmp!=null)&&(tmp.length()>0) )
				todel[i]=Long.valueOf(tmp).longValue();
			else
				todel[i]=-1;
			System.out.println("todel"+i+"=="+todel[i]);		
		}
			
		String strURL="/loan/loanapply/l020-c.jsp";
		
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();

		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
				
		lla.deletePlan(todel,sInfo);
		
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"�ͻ�ѡ��","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>