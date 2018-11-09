<%--
 ҳ������ ��a901-c.jsp
 ҳ�湦�� : ��Ϣ�ջؿ���ҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
				1������
				2��ȡ������
 �޸���ʷ ��
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>


<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//---------ҳ����Ʊ���
    String strNextPageURL					 = "";
	String strSuccessPageURL				 = "";
	String strFailPageURL					 = "";
	String strAction						 = null;
	String strActionResult					 = Constant.ActionResult.FAIL;
	String strToPrint						 = null;
	long lID 								 = -1;									//��¼ID
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
		long lCheckUserID			= sessionMng.m_lUserID;					//������
		String strCheckAbstract 	= "";									//���˱�ע
		java.sql.Timestamp tsModify = null;									//�޸�ʱ��
		long lStatusID 				= -1;									//����״̬
		String strTransNo           = "";                                   //���׺�

		//---ȡ��ҳ����Ʋ���
		strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		
		
		String strTemp = null;												//��ò�����ת����
		strTemp = (String)request.getAttribute("lID");						//��¼��ID
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");			//���˱�ע
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstract = strTemp;
		}

		strTemp = (String)request.getAttribute("tsModify");					//�޸�ʱ��
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lStatusID");				//״̬
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		
		//TransLoanDelegation loanDelegation = new TransLoanDelegation();				//delegation
		Sett_TransRepaymentLoanDAO loanDelegation = new Sett_TransRepaymentLoanDAO();
		
		/*----------------Ϊ���ж��ո������-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.getTransDirect(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/

		if("Query".equals(strAction))//�޸�
		{
			if ("Query".equals(strAction) && strTransNo != null && !strTransNo.equals(""))
			{
				//lID = loanDelegation.repaymentGetIDByTransNo(strTransNo);			//transLoanFacade.repaymentGetIDByTransNo(strTransNo)
				lID=loanDelegation.getIDByTransNo(strTransNo);
			}
			Log.print("lID = " + lID);
		}
		
		Log.print("��ǰ��ID:"+lID);
		TransRepaymentLoanInfo loanInfo = null;
		//loanInfo = loanDelegation.repaymentFindDetailByID(lID);						//��û���׼�¼this.transLoanFacade.FindRepaymentDetailByID(lID);		
		loanInfo=loanDelegation.findByID(lID);
		Log.print("��û���׳ɹ�");
		if(loanInfo == null)
		{
			throw new Exception("ϵͳ����");
		}
		else
		{
			loanInfo.setModify(tsModify);
		} 

		
		//��������д��request�У���������ִ�з����쳣ʱ��ת��viewҳ�����������ݻ���
		request.setAttribute("RepaymentInfo",loanInfo);

        //����������������ҵ����ĵ���
        if(String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
        {
			Log.print("���� -- ����");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			
			Log.print("ί�д���ID:"+loanInfo.getID());
			Log.print("ί�д������:"+loanInfo.getCheckUserID());
			Log.print("ί�д�������:"+loanInfo.getCheckAbstract());
			
			//long lReturn = loanDelegation.repaymentCheck(loanInfo); //transLoanFacade.check(info,true);
			long lReturn=1;
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                strToPrint		= "���˳ɹ�,�Ƿ��ӡ?";
                //sessionMng.getActionMessages().addMessage("���˳ɹ�");
            }
        }
		else if(String.valueOf(SETTConstant.Actions.CANCELCHECK).equals(strAction))
        {
			Log.print("���� -- ȡ������");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			Log.print("ID = "+loanInfo.getID());
			
			//long lReturn = loanDelegation.repaymentCancelCheck(loanInfo); //transLoanFacade.check(info,false);
			long lReturn=1;
			
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("ȡ�����˳ɹ�");
				strSuccessPageURL = "../control/c042.jsp";
				request.setAttribute("strAction",String.valueOf(SETTConstant.Actions.LINKSEARCH));
            }
        }
		else if ("Query".equals(strAction))
		{
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
        {
            Log.print("��Ч����");
        }
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;	
	}

	request.setAttribute("strActionResult",strActionResult);
	request.setAttribute("CreateSave",strToPrint);
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//ת����һҳ��
	Log.print("Next Page URL:"+strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
