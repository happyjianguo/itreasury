<%--
 ҳ������ ��c054.jsp
 ҳ�湦�� : ��ʴ����ջش������ҳ��
 ��    �� ��Barry
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
				1��ҵ�񸴺�
				2��ҵ��ȡ������
 �޸���ʷ ��
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
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


		/**
		 * ȡ��ҳ����Ʋ���
		 */
		strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

        /**
         * �������		
         */
		long lCheckUserID			= sessionMng.m_lUserID;					//������
		String strCheckAbstract 	= "";									//���˱�ע
		java.sql.Timestamp tsModify = null;									//�޸�ʱ��
		long lID 					= -1;									//��¼ID
		long lStatusID 				= -1;									//����״̬
		String strTransNo           = "";                                   //���׺�
		long lSerialNo				= -1;									//���к�

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
		strTemp = (String)request.getAttribute("lSerialNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSerialNo = Long.valueOf(strTemp.trim()).longValue();
		}
		
		//TransLoanDelegation loanDelegation = new TransLoanDelegation();				//delegation
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();

		//System.out.println(strAction);
		if("Query".equals(strAction))//�޸�
		{
			if ("Query".equals(strAction) && strTransNo != null && !strTransNo.equals("") && lSerialNo>0)
			{
				//lID = loanDelegation.repaymentGetIDByTransNoAndSerialNo(strTransNo,lSerialNo);
				lID=transLoanDelegation.getIDByTransNoAndSerialNo(strTransNo,lSerialNo);
				//System.out.println("strTransNo"+strTransNo+"-----lSerialNo"+lSerialNo);
				//lID=transLoanDelegation.getIDByTransNo(strTransNo);
			}
			lID=transLoanDelegation.getIDByTransNo(strTransNo);
		}
		
		/*----------------Ϊ���ж��ո������-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, ""+lSerialNo, sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/

		
		Log.print("��ǰ��ID:"+lID);
		TransRepaymentLoanInfo loanInfo = null;
		//loanInfo = loanDelegation.repaymentFindDetailByID(lID);						//��û���׼�¼		
		loanInfo = transLoanDelegation.findByID(lID);
		Log.print("��û���׳ɹ�");
		if(loanInfo == null)
		{
			throw new Exception("ϵͳ����");
		}
		else
		{
			loanInfo.setModify(tsModify);
		} 

		
		/**
		 * ��������д��request�У���������ִ�з����쳣ʱ��ת��viewҳ�����������ݻ���
		 */
		UtilOperation.setDataentityToRequest(request,loanInfo);
		request.setAttribute("lRecordStatusID",""+lStatusID);			//����ԭ���ļ�¼״̬
        
        if(String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
        {/*
			Log.print("���� -- ����");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			
			Log.print("ί�д���ID:"+loanInfo.getID());
			Log.print("ί�д������:"+loanInfo.getCheckUserID());
			Log.print("ί�д�������:"+loanInfo.getCheckAbstract());
			
			long lReturn = loanDelegation.repaymentCheck(loanInfo);
			
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                sessionMng.getActionMessages().addMessage("���˳ɹ�");
            }
            */
        }
		else if(String.valueOf(SETTConstant.Actions.CANCELCHECK).equals(strAction))
        {/*
			Log.print("���� -- ȡ������");
			loanInfo.setID(lID);
			loanInfo.setCheckAbstract(strCheckAbstract);
			loanInfo.setCheckUserID(lCheckUserID);
			Log.print("ID = "+loanInfo.getID());
			
			long lReturn = loanDelegation.repaymentCancelCheck(loanInfo);
			
            if(lReturn > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                sessionMng.getActionMessages().addMessage("ȡ�����˳ɹ�");
				strSuccessPageURL = "../control/c052.jsp";
				request.setAttribute("strAction",String.valueOf(SETTConstant.Actions.LINKSEARCH));
            }
           */ 
        }
		else if ("Query".equals(strAction))
		{
			switch((int)loanInfo.getTransDirectionID()){
				case (int)SETTConstant.MultiLoanType.PAYMENT:{
					strSuccessPageURL = "../accountinfo/a921-v.jsp";
					break;
				}
				case (int)SETTConstant.MultiLoanType.TRUSTLOAN:{
					strSuccessPageURL = "../accountinfo/a922-v.jsp";
					break;
				}
				case (int)SETTConstant.MultiLoanType.CONSIGNLOAN:{
					strSuccessPageURL = "../accountinfo/a923-v.jsp";
					break;
				}
			}
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

	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

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
