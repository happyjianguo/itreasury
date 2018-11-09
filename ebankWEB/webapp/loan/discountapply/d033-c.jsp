<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
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
<%!
public int IsInVector (Vector v,long lID)
{
    int i = 0;
    long lNum = -1;
	boolean flag = false;
	
	try{  	
		if (v != null && v.size() > 0)
		{
	 		while (i < v.size()) 
 	 		{
	      		Long lTmp = (Long) v.get(i);
				lNum = lTmp.longValue();
				if (lNum == lID)
				{					
					flag = true;
					break;
				}
				i++;
			}
		}
	}
	catch(Exception e) 
	{
    	System.out.println(e.toString());
    }
    
    return ((flag == true) ? i : -1);    
}
%>
<%
//////////////////////////////////////////////////////////////////////////////////////
	
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
		String strControl = "";
		
		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;			//���ֺ�ͬ
		long lLoanID = -1;				//��������
		//added by fanyang
		long lCredenceID = -1;          //����ƾ֤ID
		
		double llv = 0;					//��������
		Timestamp rq = null;			//��������
		Timestamp tsEnd = null;			//��������
		String strEnd = "";				//��������
		int nDays = 0;					//ʵ����������
		double dAccrual = 0;			//������Ϣ
		double dRealAmount = 0;			//ʵ�����ֽ��
		long lCount = 0;				//Ʊ���ܱ���
		double dTotalAccrual = 0;		//����������Ϣ
		double dTotalRealAmount = 0;	//����ʵ�����ֽ��

		Collection temp = null;

		// ��ҳ����
		long lPageCount = 1;                   //��ҳ
		long lPageNo = 1;                      //�ڼ�ҳ
		long lOrderParam = 1;                  //����ʲô����
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //�����Ƿ���
		
		String strNextPage = "d034-v.jsp";  //Ĭ��
		
		//Ϊ��ѯ����Ʊ����ϸ�����
		String BillInfo = "";
	    strTmp = (String)request.getAttribute("BillInfo");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     BillInfo = strTmp;
		}
		
		//��ָ���ѯ�����Ĳ���
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
		
		//���ָ���ѯ�����Ĳ���,���Ϊ"yes",���ʾ��ѯ���޸Ĺ�
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");
       	/*if (request.getAttribute("temp") != null)
       	{
           	temp = (Collection)request.getAttribute("temp");
       	}*/


///////control////////////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}
				
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("lCredenceID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lCredenceID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("nextPage");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strNextPage = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("strBillID");
        if(strTmp != null && strTmp.length() > 0 && !strTmp.equals("modify"))
        {
             strBillID = strTmp.trim();
			 /**
			  * ��һ����","�ֿ��Ĵ��ֽ�Ϊһ��Vector������
			  * @param strParam ��Ҫ��ֵĲ���
			  * @return ����һ��Vector��������Long��
			  */
			 v = DataFormat.changeStringGroup(strBillID);

		}
		
System.out.println("herehre3");
////////view//////////////////////////////////////////////////////////////////////////
		/*if (strControl.equals("view"))
		{
			if (lContractID > 0)		
            {
				temp = Discount.findBillInterestByID(lContractID,-1,1000,1,lOrderParam,lDesc);
			}
		}*/

////////save//////////////////////////////////////////////////////////////////////////
System.out.println("herehre3.5"+strControl);
		if (strControl.equals("save"))
		{			
System.out.println("herehre3.5");
			//��ѡ�������Ʊ��IDƴ���ַ���
			String[] strTmp1 = request.getParameterValues("checkbox");
			
			lBillID = Long.parseLong(strTmp1[0].trim());
			strBillID += lBillID;
			for(int i=1;i<strTmp1.length;i++)
			{				
				lBillID = Long.parseLong(strTmp1[i].trim());
				strBillID += ","+lBillID;
			}
			//response.sendRedirect("S119.jsp?control=view&lContractID="+lContractID+"&strBillID=" + strBillID);
            //return;
		}
System.out.println("herehre4");
		OBDiscountApplyHome  OBDiscountApplyHome = null;
		OBDiscountApply      OBDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
System.out.println("herehre5"+strBillID);
		OBDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		OBDiscountApply = OBDiscountApplyHome.create();
System.out.println("herehre6"+lCredenceID);
System.out.println("herehre6.1"+BillInfo);
		DiscountCredenceInfo info = new DiscountCredenceInfo();
		DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		if(!BillInfo.equals("yes"))
		{
			qinfo.setDiscountCredenceID(lCredenceID);
		}
		qinfo.setContractID(lContractID);
		qinfo.setClientID(sessionMng.m_lClientID);
		qinfo.setBillsID(strBillID);
		//��ѯ
System.out.println("herehre7");
		info = OBDiscountApply.findDiscountCredence(qinfo);
System.out.println("herehre8"+txtAction);
System.out.println("herehre8"+info);
	    //temp = OBDiscountApply.findDiscountBillByContractID(qinfo);
	    request.setAttribute("info",info);
	    request.setAttribute("lContractID",lContractID+"");
		request.setAttribute("lCredenceID",lCredenceID+"");
		request.setAttribute("strBillID",strBillID);
		request.setAttribute("txtAction",txtAction); 
		request.setAttribute("txtChanged",txtChanged);
		//Ϊ�Ӳ�ѯ�������
		if(txtAction.equals("modify"))
		{
			if(info.getInputUserID()==sessionMng.m_lUserID&&!txtChanged.equals("yes")
			&&(info.getStatusID()==OBConstant.LoanInstrStatus.SAVE||info.getStatusID()==OBConstant.LoanInstrStatus.SUBMIT))
			{
				strNextPage = "d037-v.jsp";
				request.setAttribute("txtChanged","yes");
			}else
			{
				strNextPage = "d036-v.jsp";
			}
		}
System.out.println("strNextpage==="+strNextPage);
	    /* ��ȡ�����Ļ��� */
        ServletContext sc = getServletContext();
        /* ���÷��ص�ַ */
        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/"+strNextPage)));
        /* forward�����ҳ�� */
        rd.forward(request, response);
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