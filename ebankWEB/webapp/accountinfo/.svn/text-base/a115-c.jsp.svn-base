<%--
 页面名称 ：a115-c.jsp
 页面功能 : 下属单位历史明细 控制层
 作    者 ：xgzhang
 日    期 ：2005-5-25
 特殊说明 ：
 实现操作说明：
 修改历史 ：
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%> 
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%> 
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.itreasury.loan.contract.dao.ContractDao"%>
<%@ page import="com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.securities.util.PageCtrlInfo"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
//标题变量
String strTitle = "[下属单位账户历史明细]"; 
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();
 
try
{
	//请求检测
	/** 判断是否登录系统 **/
	if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            return;
        }
    /** 权限检查 **/
    if (sessionMng.hasRight(request) == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            return;
        }
        if(request.getParameter("fromAccountType")!=null && !request.getParameter("fromAccountType").equals("YES"))  
      {
  
      //定义变量
		String strClientCode = "";//客户号
		String strAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		long lAccountTypeID = -1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID
		String strContractCode = "";//合同号
		long lContractID = -1;//合同ID
		String strLoanNoteNo = "";//放款单号
		long lLoanNoteID = -1;//放款单ID
		String strFixedDepositNo = "";//定期存款单据号
		long lSubAccountID = -1;//定期存款单据对应的子账户ID
		Timestamp tsStartDate = null;//开始日期
		String strStartDate = "";
		String strEndDate = "";
		Timestamp tsEndDate = null;//结束日期
		double dBalance = 0.0;//期初余额
		long lOrder = -1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		//读取数据
		if(request.getParameter("lAccountID")!=null)
		{
			 lAccountID = Long.parseLong(request.getParameter("lAccountID")); //账户ID
		} 
		if(request.getParameter("lAccountTypeID")!=null)
		{
			lAccountTypeID = Long.parseLong(request.getParameter("lAccountTypeID")); //账户类型ID 
		}
		if(request.getParameter("lAccountGroupID")!=null)
		{
			lAccountGroupID = Long.parseLong(request.getParameter("lAccountGroupID")); //账户组ID 
		}
		if(request.getParameter("tsStart")!=null)
		{
			strStartDate = request.getParameter("tsStart");
			tsStartDate = DataFormat.getDateTime(strStartDate);  //起始日期
		}
		if(request.getParameter("tsEnd")!=null)
		{
			strEndDate = request.getParameter("tsEnd");
			tsEndDate = DataFormat.getDateTime(strEndDate); //终止日期
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strContractCodeCtrl")!=null) )
		{
			strContractCode = request.getParameter("strContractCodeCtrl"); //合同号
			Log.print("strContractCode="+strContractCode);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strContractCode")!=null) )
		{
			lContractID = GetNumParam(request,"strContractCode"); //合同ID
			Log.print("lContractID="+lContractID);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strLoanNoteNoCtrl")!=null) )
		{
			strLoanNoteNo = request.getParameter("strLoanNoteNoCtrl"); //放款通知单号
			Log.print("strLoanNoteNo="+strLoanNoteNo);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strLoanNoteNo")!=null) )
		{
			lLoanNoteID = GetNumParam(request,"strLoanNoteNo");  //放款通知单ID
			Log.print("lLoanNoteID="+lLoanNoteID);
		}
		
		if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED || lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) && (request.getParameter("strFixedDepositNoCtrl")!=null) )
		{
			if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
			{
				strFixedDepositNo = request.getParameter("strFixedDepositNoCtrl"); //定期存款单据号
				Log.print("strFixedDepositNo="+strFixedDepositNo);
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				strFixedDepositNo = request.getParameter("strNotifyDepositNoCtrl"); //通知存款单据号
				Log.print("strFixedDepositNo="+strFixedDepositNo);
			}
		}

		if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED || 
			lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) && (request.getParameter("strFixedDepositNoCtrl")!=null) )
		{
			if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
			{		
				lSubAccountID = GetNumParam(request,"strFixedDepositNo");//子账户ID
				Log.print("定期子账户=" + lSubAccountID);
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				lSubAccountID = GetNumParam(request,"strNotifyDepositNo");  //子账户ID
				Log.print("子账户=" + lSubAccountID);
			}
		}
		
		//初始化并设置查询条件类
        QueryTransAccountDetailWhereInfo qtai = new QueryTransAccountDetailWhereInfo();
		QTransAccountDetail qobj = new QTransAccountDetail();
				
        qtai.setOfficeID(sessionMng.m_lOfficeID);
		qtai.setCurrencyID(sessionMng.m_lCurrencyID);
		qtai.setClientCode(sessionMng.m_strClientCode);//客户号
		qtai.setAccountNo(strAccountNo);//账号
		qtai.setAccountID(lAccountID);//账户ID
		qtai.setAccountTypeID(lAccountTypeID);//账户类型ID
		qtai.setContractCode(strContractCode);//合同号
		qtai.setContractID(lContractID);//合同ID
		qtai.setLoanNoteNo(strLoanNoteNo);//放款单号
		qtai.setLoanNoteID(lLoanNoteID);//放款单ID
		qtai.setFixedDepositNo(strFixedDepositNo);//定期存款单据号
		qtai.setSubAccountID(lSubAccountID);//定期存款号对应的子账户ID
		qtai.setStartDate(tsStartDate);
		qtai.setEndDate(tsEndDate);
		//qtai.setDesc(1);
		qtai.setOrderField(1);
		
        //根据请求操作，完成业务处理的调用
		Collection coll = null;
		
        coll = qobj.queryTransAccountDetail(qtai);
		dBalance = qobj.queryTransAccountBalance(qtai);//账户的期初余额
		System.out.println("!!!!!!!!!!账户的期初余额0="+dBalance);
		
		OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
		if (lContractID > 0)
		{
			dBalance = bqDao.getLoanHisBalanceByContractID(qtai);//合同的期初余额
		}
		if (lLoanNoteID > 0)
		{
			dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qtai);//放款单的期初余额
		}
		/* 在请求中保存结果对象 */
		request.setAttribute("coll_resultInfo",coll);
		request.setAttribute("whereInfo",qtai);
		System.out.println("!!!!!!!!!!账户的期初余额1="+dBalance);
		request.setAttribute("balance",DataFormat.formatAmount(dBalance));//账户的期初余额
		System.out.println("!!!!!!!!!!账户的期初余额2="+DataFormat.formatAmount(dBalance));
		}
		String  sTemp = null;
		   sTemp = (String)request.getParameter("lAccountGroupID");
		if (sTemp != null && sTemp.trim().length() > 0) {
         	request.setAttribute("lAccountGroupID",sTemp);
		}
	  sTemp = (String)request.getParameter("lClientID");
		if (sTemp != null && sTemp.trim().length() > 0) {
         	request.setAttribute("lClientID",sTemp);
		}
		Log.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+sTemp);
    	
		
    /**
	 * 从request中获得页面控制信息 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	 * 操作结果置为成功
	 */
	pageInfo.success(); 
} 
/**
* 异常处理
*/
catch ( Exception exp ) {
	pageInfo.fail(); 
}

//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
rd.forward( request,response );
%>