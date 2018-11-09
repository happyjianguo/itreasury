<%--
 页面名称 ：d008-c.jsp
 页面功能 : 新增贴现申请-查询贴现票据列表 控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 java.util.Collection,
				 java.util.Vector,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
                 com.iss.itreasury.ebank.obdiscountapply.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>
<%
  try
  {
	Log.print("********进入页面--EBANK/LOAN/DISCOUNTAPPLY/D008-C.JSP******");
	
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
		
		//定义变量
		String strTemp = "";
		 long lID = -1; //贴现申请id
         long lContractID = -1;//实际中没有应用?
         long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;
         long lPageNo = 1;
         long lOrderParam = -1;
         long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序;
         double dRate = 0.0;//实际中没有应用
         Timestamp tsDate = null;//贴现日
		 String frompage="";
		
		  //贴现申请id  
		  strTemp = (String)request.getAttribute("lID");
		  if (strTemp != null && strTemp.trim().length() > 0)
		  {
			 lID = Long.valueOf(strTemp).longValue();
		  }
		  
		  //合同号
		  strTemp = (String)request.getAttribute("lContractID");
		  if (strTemp != null && strTemp.trim().length() > 0)
		  {
			 lContractID = Long.valueOf(strTemp).longValue();
		  }
		  
		   //第几页
		   strTemp = (String)request.getAttribute("lPageNo");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lPageNo = Long.valueOf(strTemp).longValue();
		   }
		   
		   //排序参数
		   strTemp = (String)request.getAttribute("lOrderParam");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lOrderParam = Long.valueOf(strTemp).longValue();
		   }
		   
		   //正序/反序
		   strTemp = (String)request.getAttribute("lDesc");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			  lDesc = Long.valueOf(strTemp).longValue();
		   }
		   
		    //贴现率
			strTemp = (String)request.getAttribute("dRate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dRate = DataFormat.parseNumber(strTemp);
			}
		   
		    //贴现日
		    strTemp = (String)request.getAttribute("tsDiscountStartDate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDate =  DataFormat.getDateTime(strTemp);
			}
			 strTemp = (String)request.getAttribute("frompage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			frompage = strTemp;
		}
			
			 
			 OBDiscountApplyHome  obDiscountApplyHome = null;
	         OBDiscountApply      obDiscountApply = null;
	         obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
	         obDiscountApply = obDiscountApplyHome.create();
			 //为避免传值繁琐，在此根据贴现申请id查出贴现日期
			 DiscountInfo discountInfo = null;
		
		    discountInfo = obDiscountApply.findDiscountByID(lID);
		    if(discountInfo != null)
		    {
		      tsDate = discountInfo.getDiscountStartDate();
		    }
			 
			 DiscountBillQueryInfo discountBillQueryInfo = new DiscountBillQueryInfo();
			
			 discountBillQueryInfo.setDiscountID(lID);
			 Log.print("==============lID:"+lID);
			 discountBillQueryInfo.setContractID(lContractID);
	         discountBillQueryInfo.setPageLineCount(lPageLineCount);
	         discountBillQueryInfo.setPageNo(lPageNo);
			 discountBillQueryInfo.setOrderParam(lOrderParam);
			 discountBillQueryInfo.setDesc(lDesc);
			 discountBillQueryInfo.setRate(dRate);
	         discountBillQueryInfo.setDate(tsDate);
			
		 
			 //Vector vctResult = obDiscountApply.findDiscountBillByDiscountID(discountBillQueryInfo);
			 
			 //查询票据信息
			 OBDiscountApplyDao obDiscountApplyDAO = new OBDiscountApplyDao();//d
			 Vector vctResult = obDiscountApplyDAO.findDiscountBillByDiscountID(discountBillQueryInfo);//d
			 
			 if( vctResult != null)
			 {
			   request.setAttribute("resultInfo",vctResult);
			 }
			 
			 //查询统计申请信息
		     if(discountInfo != null)
		     {
		        request.setAttribute("resultDiscountInfo",discountInfo);
		     }
			 
			     /* 获取上下文环境 */
		       ServletContext sc = getServletContext();
		       /* 设置返回地址 */
		       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d009-v.jsp?frompage="+frompage+"")));
		       /* forward到结果页面 */
		       rd.forward(request, response);
		
    }
	catch(IException ie) 
	{
		//ie.printStackTrace();
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>