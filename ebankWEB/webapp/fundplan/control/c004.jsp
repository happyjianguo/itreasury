<%--
 ҳ������ ��c005.jsp
 ҳ�湦�� : �ʽ�ƻ��걨���޸ģ�ֻ������״̬Ϊ1�Ĳſ��Խ��в��� ����ҳ��  v004.jsp  ��������> c004.jsp ��������> v001.jsp
 ��    �� ��ylguo
 ��    �� ��2008-10-24
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.fundplan.dataentity.SubCapitalPlanInfo,
java.util.Vector,
com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,
com.iss.itreasury.ebank.fundplan.dao.*,
com.iss.itreasury.ebank.system.util.JSPLogger,
java.sql.Timestamp,
com.iss.itreasury.ebank.fundplan.bizlogic.AllCapitalPlanBiz,
java.text.DateFormat,
java.text.SimpleDateFormat,
java.util.Date
"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[�ʽ�ƻ�]";
%>
<% String strContext = request.getContextPath(); %>
<%
	PageControllerInfo pageCtrlInfo = new PageControllerInfo();
	request.setCharacterEncoding("ISO8859_1");
	try
	{
		JSPLogger.info("*******����ҳ��--\\iTreasury-ebank\\fundplan\\control\\c004.jsp*******");	
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
        
        pageCtrlInfo.convertRequestToDataEntity(request);
        
		String sCode = "";
		String temp = "";
		Timestamp startDate = null;
		Timestamp endDate = null;        
        
        //��v004.jspҳ����startDate��endDate��cpCode
        temp = (String)request.getAttribute("startDate");
        if(temp != null && temp.length() > 0)
        {
        	startDate = DataFormat.getDateTime(temp);
        }
        temp = (String)request.getAttribute("endDate");
        if(temp != null && temp.length() > 0)
        {
        	endDate = DataFormat.getDateTime(temp);
        }
        temp = (String)request.getAttribute("cpCode");
        if(temp != null && temp.length() > 0)
        {
        	sCode = temp;
        }
        //����ID
        long capitalplanId = -1;
        temp = request.getParameter("capitalplanId");
        if(temp != null && temp.length() > 0)
        {
        	capitalplanId = Long.parseLong(temp);
        }
        long modleId = -1;
        temp = (String)request.getAttribute("modelId");
        if(temp != null && temp.length() > 0)
        {
        	modleId = Long.parseLong(temp);
        }
        //������Ϣ
        CapitalPlanInfo capitalplan = new CapitalPlanInfo();
        capitalplan.setCpCode(sCode);
        capitalplan.setId(capitalplanId);
        capitalplan.setModelId(modleId);
        capitalplan.setClientId(sessionMng.m_lClientID);
        capitalplan.setOfficeId(sessionMng.m_lOfficeID);
        capitalplan.setCurrencyId(sessionMng.m_lCurrencyID);
        capitalplan.setStartdate(startDate);
        capitalplan.setEnddate(endDate);
        capitalplan.setInputuserId(sessionMng.m_lUserID);
      //  Date inputtime = new Date();
       // DateFormat df= new SimpleDateFormat("yyyy-MM-dd"); 
 		
      //  capitalplan.setInputdate(DataFormat.getDateTime(df.format(inputtime)));
        capitalplan.setInputdate(Env.getSystemDate());
        capitalplan.setStatusId(1);
                
		// ��ò�������
		String formAction = "";
		formAction = request.getParameter("strAction");
     	//�ӱ���Ϣ
     	SubCapitalPlanInfo subPlanInfo = null;
        //��ȡҳ��Ԫ��
		String[] strConfigIds = request.getParameterValues("configId");
		String[] strParentIds = request.getParameterValues("parentId");
		String[] strItemIds = request.getParameterValues("itemId");
		
		if((strConfigIds == null || strParentIds == null || strItemIds == null)
			||(strConfigIds.length!=strParentIds.length || strConfigIds.length!=strItemIds.length))
		{
			throw new IException("ҳ��ؼ������쳣");
		}
		
		long curConfigId = -1;
		long curItemId = -1;
		long curParentId = -1;
		String[] strEverdayCapitals = null;
		Vector v = new Vector();
		
		for(int i=0; i<strConfigIds.length; i++)
		{
			curConfigId = Long.parseLong(strConfigIds[i]);
			curItemId = Long.parseLong(strItemIds[i]);		
			curParentId = Long.parseLong(strParentIds[i]);	
			//getParameterValues�õ���ֵ���������,
			//��com.iss.itreasury.servlet.FilterEncoding�����Ѿ���ISO-8859-1ת������GBK��
			//��������ֻҪ��ҳ�������ת����ISO-8859-1������
			strEverdayCapitals = request.getParameterValues("P_" + curConfigId);

			subPlanInfo = new SubCapitalPlanInfo();
			subPlanInfo.setCapitalplanconfigId(curConfigId);
			subPlanInfo.setId(curItemId);
			subPlanInfo.setPareTldId(curParentId);
			subPlanInfo.setOfficeId(sessionMng.m_lOfficeID);
			subPlanInfo.setCurrencyId(sessionMng.m_lCurrencyID);
			subPlanInfo.setTotal(strEverdayCapitals[0]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[0]));
			subPlanInfo.setMondayCapital(strEverdayCapitals[1]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[1]));
			subPlanInfo.setTuesdayCapital(strEverdayCapitals[2]==null?0:DataFormat.parseNumber(strEverdayCapitals[2]));
			subPlanInfo.setWednesdaycapital(strEverdayCapitals[3]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[3]));
			subPlanInfo.setThursdayCapital(strEverdayCapitals[4]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[4]));
			subPlanInfo.setFridayCapital(strEverdayCapitals[5]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[5]));
			subPlanInfo.setNextweekCapital(strEverdayCapitals[6]==null?0.0:DataFormat.parseNumber(strEverdayCapitals[6]));
			if(strEverdayCapitals.length==8)
			{
				//subPlanInfo.setRemark(strEverdayCapitals[7]==null?null:DataFormat.toChinese(strEverdayCapitals[7]) );
				if(strEverdayCapitals[7] != null){
					System.out.println("[]����[] strEverdayCapitals[7] :" + strEverdayCapitals[7]);
					subPlanInfo.setRemark(strEverdayCapitals[7]);
				}
				//System.out.println(DataFormat.toChinese(strEverdayCapitals[7]));
			}else{
				subPlanInfo.setRemark(null);
			}
			
			subPlanInfo.setStatusId(1);
			
			v.add(subPlanInfo);
		}
				
		if("save".equals(formAction))
		{
		    
            //�������
	     	AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();
	     	if(capitalplan.getId()>0)
	     	{	
		     	allCapitalPlanBiz.updateSubCapitalPlan(capitalplan,v);
		     	
		    } 
		    else 
		    {
	     		allCapitalPlanBiz.addCapitalPlan(capitalplan, v);
	     	}

	     	sessionMng.getActionMessages().addMessage("����ɹ�");
	     	
	    }
	    else if("toAutoCheck".equals(formAction))
	    {
	        AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();//�Ƚ��б������
	     	if(capitalplan.getId()>0)
	     	{	
		     	allCapitalPlanBiz.updateSubCapitalPlan(capitalplan,v);
		    } 
		    else 
		    {
	     		capitalplanId = allCapitalPlanBiz.addCapitalPlan(capitalplan, v);
	     	}
            if(allCapitalPlanBiz.checkAllCapitalPlan(capitalplanId,sessionMng.m_lUserID,Env.getSystemDateString()) > 0)//Ȼ����и��˲���
            {
                sessionMng.getActionMessages().addMessage("�ύ�ɹ���");
            }
            else
            {
               sessionMng.getActionMessages().addMessage("�ύʧ�ܣ�");
            }
	     	
	    }
	    else if("del".equals(formAction))
	    {
	     	 AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();
	     	 allCapitalPlanBiz.delAllCapitalPlan(capitalplanId);
	     	 sessionMng.getActionMessages().addMessage("ɾ���ɹ�");
	    }
	     
	     pageCtrlInfo.success();
	}
	catch(IException exp)
	{
    	sessionMng.getActionMessages().addMessage(exp.getMessage());
    	exp.printStackTrace();
 		pageCtrlInfo.fail();
    }

   /* ���÷��ص�ַ */
	String nextURL = pageCtrlInfo.getP_NextPageURL();
	JSPLogger.info("������һҳ�棺"+nextURL);		
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward�����ҳ�� */
	rd.forward(request, response);
%>
