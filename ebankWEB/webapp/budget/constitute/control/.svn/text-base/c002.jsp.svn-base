<%--
 页面名称 ：c001.jsp
 页面功能 : 预算编制控制层
 作    者 ：weilu
 日    期 ：2005-7-14
 特殊说明 ：
 实现操作说明：
 修改历史 ：
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@page import="java.util.*,
				com.iss.itreasury.util.Env,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.IException,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanDetailInfo"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
BudgetPlanInfo info = null;

try {
	 /** 权限检查 **/
    Log.print("=================进入页面budget/constitute/control/c002.jsp=========");
    String strTitle = "预算编制"; 
    //请求检测
     //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

	/** 
	 * 从request中获得页面控制信息 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	* 初始化业务实体类 
	*/
	info = new BudgetPlanInfo();

	/**
	* 获得上页参数
	*/
	info.convertRequestToDataEntity(request);

	/** 
	 * [定义业务变量]
	 */
	 ArrayList list = new ArrayList();
	 BudgetPlanDetailInfo detailInfo = null;
	 String strTmp = null;
	 String[] strArrTmp = null;
	
	String[] itemNo = request.getParameterValues("hdnItemNo");
	long itemID = -1;
	double amount = 0.0;
	long showColumn = 0;	//显示的列，要根据列来判断预算金额所取的字段名
	strTmp = (String)request.getAttribute("showColumns");
	showColumn = Long.parseLong(strTmp)-1;
	System.out.println("itemNo.length="+itemNo.length);
	for (int i=0;i<itemNo.length;i++)
	{
		strTmp = (String)request.getAttribute("hdnItemID"+itemNo[i]);
		itemID = Long.parseLong(strTmp);
		strTmp = (String)request.getAttribute("txtAmount"+ showColumn +"_"+itemNo[i]);
		strTmp = strTmp.replaceAll(",","");
		amount = Double.parseDouble(strTmp);
		
		detailInfo = new BudgetPlanDetailInfo();
		detailInfo.setItemID(itemID);
		detailInfo.setAmount(amount);
		detailInfo.setBudgetAmount(amount);
		detailInfo.setOriginalAmount(amount);
		detailInfo.setStatusID(Constant.RecordStatus.VALID);
		list.add(detailInfo);
	}
	info.setPlanDetail(list);
	info.setConstituteDate(Env.getSystemDate());
	info.setBudgetFlag(BUDGETConstant.BudgetFlag.CONSTITUTE);
	info.setStatusID(BUDGETConstant.ConstituteStatus.SAVE);
	info.setInputUserID(sessionMng.m_lUserID);
	info.setInputDate(Env.getSystemDateTime());
	/**
	* 初始化业务逻辑层
	*/
	ConstituteDelegation delegation = new ConstituteDelegation();
	
	/**
	 * 根据操作代码进行业务操作
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.CREATESAVE)))	//创建保存
	{
		long l = delegation.save(info);
		sessionMng.getActionMessages().addMessage("编制成功");
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.COMMIT)))	//提交
	{
		System.out.println("提交");
		long l = delegation.commitBudget(info);
		sessionMng.getActionMessages().addMessage("提交成功");
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.DELETE)))	//删除
	{
		System.out.println("删除");
		delegation.deleteBudget(info);
		sessionMng.getActionMessages().addMessage("删除成功");
		pageInfo.success();
	}

} 
/**
* 异常处理
*/
catch ( Exception exp ) {
	/**
	* 用户提交信息置入request中
	*/
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	sessionMng.getActionMessages().addMessage(exp);
	/**
	 * 出现异常,操作结果置为失败
	 */
	pageInfo.fail();
}
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	
	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
				//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>