<%
/**
 * ҳ������ ��q013-c.jsp
 * ҳ�湦�� : ��ѯ����������Ϣ�ͼƻ���Ϣ��ת��ִ�мƻ���ά������
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    try{
    	
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
		
		long  loanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();          //��ˮ��
    	LoanApplyInfo applyInfo=null;	
    	String tmp="";
    	long pageNo=1;
    	long orderParam=99;
    	long lDesc=Constant.PageControl.CODE_ASCORDESC_ASC;
    	String action="";
    

		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			pageNo=Long.valueOf(tmp).longValue();
						
		OBLoanQuery lla = new OBLoanQuery(); 
				
		/*��ѯ������Ϣ*/
		LoanApplyInfo laInfo=lla.findByID(loanID);		
		
		long  pageLine=Constant.PageControl.CODE_PAGELINECOUNT;
    	long  planCount=laInfo.getPlanDetailCount();
    	long  pageCount=planCount / pageLine;
    	if ( (planCount%pageLine)!=0 )	pageCount++;
    	
		/*��ѯ�ƻ���Ϣ*/
		if ( pageNo<=0 ) pageNo=1;
		if ( orderParam<=0 ) orderParam=99;
		if ( lDesc<=0 ) lDesc=Constant.PageControl.CODE_ASCORDESC_ASC;
		if ( pageNo>pageCount) pageNo=1;
		
		Vector c=(Vector)lla.findPlanByLoanID(loanID,pageNo,Constant.PageControl.CODE_PAGELINECOUNT,orderParam,lDesc);
		
		request.setAttribute("LoanApplyInfo",laInfo);
		request.setAttribute("Collection",c);
		request.setAttribute("nOrderParam",String.valueOf(orderParam));
		request.setAttribute("nDesc",String.valueOf(lDesc));
		request.setAttribute("nPageNo",String.valueOf(pageNo));
	
		String strURL="/loan/query/q014-v.jsp";
		
		
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