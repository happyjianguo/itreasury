<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.ebank.obdiscountapply.dao.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* ����̶����� */
	String strTitle = "[����ƾ֤]";
%>		
<%
/////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		//�����������ȡ�������

		String strTmp = "";
		long lCredenceID = -1;			//����ƾ֤��ʾ
		long lStatusID = -1;            //״̬

		//////////////////////////////
		
		//����EJB
		OBDiscountApplyHome  obDiscountApplyHome = null;
		OBDiscountApply      obDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
		obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		obDiscountApply = obDiscountApplyHome.create();
        
///////control///////////////////////////////////////////////////////////////////
//
		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("lStatusID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lStatusID = Long.parseLong(strTmp.trim());
		}
		//��ָ���ѯ�����Ĳ���
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
			
		//���ָ���ѯ�����Ĳ���,���Ϊ"yes",���ʾ��ѯ���޸Ĺ�
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");
//
//ȡ������
		long lResult = -1;
		//OBDiscountApplyDao dao = new OBDiscountApplyDao();
		System.out.println("qqq!!!!!!!!!!!!!===(*******)"+lStatusID);
		lResult = obDiscountApply.updateCredenceStatus(lCredenceID,lStatusID);
		System.out.println("www!!!!!!!!!!!!!===(*******)"+lResult);
		DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		qinfo.setDiscountCredenceID(lCredenceID);
		DiscountCredenceInfo info = obDiscountApply.findDiscountCredence(qinfo);
		RequestDispatcher rd;
		if (lResult > 0)		
        {
		   //temp = obDiscountApply.findDiscountBillByContractID(qinfo);
		   /* ��ȡ�����Ļ��� */
	       ServletContext sc = getServletContext();
	       /* ���÷��ص�ַ */
		   //������ύ
		   if(lStatusID==OBConstant.LoanInstrStatus.SUBMIT)
		   {
		   		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d045-v.jsp?lID="+lCredenceID+"&strCode="+info.getCode())));
		   }else 
		   {
		   		if(txtAction.equals("modify"))
				{
		   			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/query/q003-c.jsp")));
		   		}
		  		 else
		   		{
	       			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d030-v.jsp")));
	       		}
			}
		   /* forward�����ҳ�� */
	       rd.forward(request, response);
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }
	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"����ƾ֤", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"����ƾ֤",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>