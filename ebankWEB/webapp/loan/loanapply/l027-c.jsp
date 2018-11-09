<%
/**
 * ҳ������ ��l027-c.jsp
 * ҳ�湦�� : ��ѯ�����ƻ���Ϣ�����޸ļƻ���ϸʱʹ��
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
	long   planDetailID=-1;	
	String tmp="";
   
    
    long clientID=-1;
    String applyCode="";
    double loanAmount=0;
    String strDate=DataFormat.getDateString(Env.getSystemDate(1,1));
    long planVersion=-1;
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
		
		tmp=(String)request.getAttribute("lPlanDetailID");
		if ( (tmp!=null)&&(tmp.length()>0) )
			planDetailID=Long.valueOf(tmp).longValue();
			
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);		
		
		/*���ݼƻ���ϸ����Ϣ����ѯ�ƻ�*/
		OBLoanPlanDetailInfo detailInfo=lla.findPlanByID(planDetailID,sInfo);
		
		request.setAttribute("LoanPlanDetailInfo",detailInfo);
	
		String strURL="/loan/loanapply/l026-v.jsp";
		
				
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