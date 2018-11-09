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
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
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
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量，获取请求参数

		String strTmp = "";
		String strControl = "";
		
		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;			//贴现合同
		long lLoanID = -1;				//贴现申请
		//added by fanyang
		long lCredenceID = -1;          //贴现凭证ID
		
		double llv = 0;					//贴现利率
		Timestamp rq = null;			//贴现日期
		Timestamp tsEnd = null;			//贴现日期
		String strEnd = "";				//贴现日期
		int nDays = 0;					//实际贴现天数
		double dAccrual = 0;			//贴现利息
		double dRealAmount = 0;			//实付贴现金额
		long lCount = 0;				//票据总笔数
		double dTotalAccrual = 0;		//汇总贴现利息
		double dTotalRealAmount = 0;	//汇总实付贴现金额

		Collection temp = null;

		// 分页参数
		long lPageCount = 1;                   //几页
		long lPageNo = 1;                      //第几页
		long lOrderParam = 1;                  //根据什么排序
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序

       	if (request.getAttribute("temp") != null)
       	{
           	temp = (Collection)request.getAttribute("temp");
       	}


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

		strTmp = (String)request.getAttribute("strBillID");
        if(strTmp != null && strTmp.length() > 0)
        {
             strBillID = strTmp.trim();
			 /**
			  * 将一个用","分开的串分解为一个Vector的数组
			  * @param strParam 需要拆分的参数
			  * @return 返回一个Vector，里面是Long型
			  */
			 v = DataFormat.changeStringGroup(strBillID);

		}

////////view//////////////////////////////////////////////////////////////////////////
		/*if (strControl.equals("view"))
		{
			if (lContractID > 0)		
            {
				temp = Discount.findBillInterestByID(lContractID,-1,1000,1,lOrderParam,lDesc);
			}
		}*/

////////save//////////////////////////////////////////////////////////////////////////
		if (strControl.equals("save"))
		{			
			//将选择的贴现票据ID拼成字符串
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
		
		OBDiscountApplyHome  OBDiscountApplyHome = null;
		OBDiscountApply      OBDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
System.out.println("here!9");
		OBDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		OBDiscountApply = OBDiscountApplyHome.create();
		System.out.println("here!8");
		DiscountCredenceInfo info = new DiscountCredenceInfo();
		DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		System.out.println("lCredenceID====="+lCredenceID);
		qinfo.setDiscountCredenceID(lCredenceID);
		qinfo.setContractID(lContractID);
		qinfo.setClientID(sessionMng.m_lClientID);
		qinfo.setBillsID(strBillID);
		//查询
		System.out.println("here!");
		info = OBDiscountApply.findDiscountCredence(qinfo);
		System.out.println("here!2");
	    //temp = OBDiscountApply.findDiscountBillByContractID(qinfo);
	    request.setAttribute("info",info);
		System.out.println("here!33");
	    request.setAttribute("lContractID",lContractID+"");
		request.setAttribute("lCredenceID",lCredenceID+"");
	    /* 获取上下文环境 */
        ServletContext sc = getServletContext();
        /* 设置返回地址 */
        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d039-v.jsp")));
        /* forward到结果页面 */
        rd.forward(request, response);
	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>