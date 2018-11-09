<%
/**
 * ҳ������ ��l024-c.jsp
 * ҳ�湦�� : ����ƻ���ϸ�������������޸�
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 *			  
 * ת��ҳ�� : 1020-c.jsp
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
	long        loanID=-1;          //����������ˮ��
	long		rsCount=-1;
	long 		payType=-1;
	long 		planVersion=-1;
	Timestamp	payDate=null;
	double 		payAmount=0;
	String 		type="����";
	String tmp="";
	Vector		v=new Vector();
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

	
		tmp=(String)request.getAttribute("lRsCount");		//��ҳ�ļ�¼��
		if ( (tmp!=null)&&(tmp.length()>0) )
			rsCount=Long.valueOf(tmp).longValue();	
		
		tmp=(String)request.getAttribute("lPlanVersion");		//�ƻ��汾��
		if ( (tmp!=null)&&(tmp.length()>0) )
			planVersion=Long.valueOf(tmp).longValue();	
	
		for ( int i=0;i<rsCount;i++)
		{
			tmp=(String)request.getAttribute("dAmount"+i);		//���
			if ( (tmp!=null)&&(tmp.length()>0) )
			{
				tmp=DataFormat.reverseFormatAmount(tmp);	
				payAmount=Double.valueOf(tmp).doubleValue();
			}	
			if ( payAmount<=0 )
				continue;
			tmp=(String)request.getAttribute("txtdtInputDate"+i);		//����
			if ( (tmp!=null)&&(tmp.length()>0) )
				payDate=DataFormat.getDateTime(tmp);
				
			tmp=(String)request.getAttribute("txtlPayRepayType"+i);		//�Ż���
			if ( (tmp!=null)&&(tmp.length()>0) )
				payType=Long.valueOf(tmp).longValue();	
			
			OBLoanPlanDetailInfo planDetail=new OBLoanPlanDetailInfo();
			tmp=(String)request.getAttribute("lDetailID"+i);		//�Ż���
			if ( (tmp!=null)&&(tmp.length()>0) )
				planDetail.setID(Long.valueOf(tmp).longValue());
			else
				planDetail.setID(-1);
			planDetail.setPlanID(planVersion);
			planDetail.setPlanDate(payDate);
			planDetail.setPayTypeID(payType);
			planDetail.setAmount(payAmount);
			planDetail.setType(type);
			payAmount=0;
			
			v.add(planDetail);    

		}
					
		String strURL="/loan/loanapply/l020-c.jsp";
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		
		lla.savePlan(v);
		

	    //request.setAttribute("LoanApplyInfo",laInfo);
		
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
		
     }catch (Exception ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>