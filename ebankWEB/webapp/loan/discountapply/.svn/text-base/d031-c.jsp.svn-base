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

		//String strTitle = "";
		String strTmp = "";
		String strControl = "";
		long lCurrencyID = -1;
		
		long lContractID = -1;
		String txtContractCode = "";
		//long lClientID = -1;
		String txtClientCode = "";
		long lCredenceID = -1;

		long lClientID = sessionMng.m_lClientID;   //客户ID
		
		//adding
		String strBillID = null;
		Vector v = new Vector();
		//为查询贴现票据明细表服务
		String BillInfo = "";

///////control////////////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("BillInfo");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     BillInfo = strTmp;
		}
		
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp;
		}

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("lClientID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lClientID = Long.parseLong(strTmp.trim());
		}	 
		
//new adding
		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}


		System.out.println("hrerererererererere!"+lContractID);
		strTmp = (String)request.getAttribute("strBillID");
		System.out.println("hrerererererererere!"+strTmp);
        if(strTmp != null && strTmp.length() > 0 && !strTmp.equals("null"))
        {
             strBillID = strTmp.trim();
			 /**
			  * 将一个用","分开的串分解为一个Vector的数组
			  * @param strParam 需要拆分的参数
			  * @return 返回一个Vector，里面是Long型
			  */
			 
			 v = DataFormat.changeStringGroup(strBillID);
			 /*
			 if( (v != null) && (v.size() > 0) )
			 {
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					lBillID = ( long ) it.next();
				}
			 }
			 */
		}
		System.out.println("hrerererererererere!"+strBillID);
//
		Collection temp = null;

		OBDiscountApplyHome  obDiscountApplyHome = null;
		OBDiscountApply      obDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
		obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		obDiscountApply = obDiscountApplyHome.create();

		// 分页参数
		long lPageCount = 1;                   //几页
		long lPageNo = 1;                      //第几页
		long lOrderParam = 1;                  //根据什么排序
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序
		
//排序
		//判断正序和反序
		strTmp = (String)request.getAttribute("lDesc");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lDesc = Long.parseLong(strTmp.trim());
		}
		
		//判断排序条件
		strTmp = (String)request.getAttribute("lOrderParam");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lOrderParam = Long.parseLong(strTmp.trim());
		}

		//显示页数
		strTmp = (String)request.getAttribute("lPageNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lPageNo = Long.parseLong(strTmp.trim());
		}

		DiscountBillQueryInfo qinfo = new DiscountBillQueryInfo();    //查询条件
		qinfo.setContractID(lContractID);
		qinfo.setPageLineCount(10000);         //每页10000行，也就是不分页
		qinfo.setOrderParam(lOrderParam);
		qinfo.setDesc(lDesc);
		qinfo.setPageNo(lPageNo);
		//new adding
		qinfo.setDiscountCredenceID(lCredenceID);

		if (lContractID > 0)		
        {

		   temp = obDiscountApply.findDiscountBillByContractID(qinfo);

		   request.setAttribute("temp",temp);
		   request.setAttribute("lContractID",lContractID+"");
		   request.setAttribute("lCredenceID",lCredenceID+"");
		   request.setAttribute("BillInfo",BillInfo);
		  /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d032-v.jsp")));
	       /* forward到结果页面 */
	       rd.forward(request, response);
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }

		/*if (strControl.equals("save"))
		{
			response.sendRedirect("S125.jsp?control=view&lContractID="+lContractID);
            return;
		}*/
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
