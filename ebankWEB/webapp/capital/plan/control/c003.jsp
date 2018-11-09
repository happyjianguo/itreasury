<%--
 页面名称 ：c003.jsp
 页面功能 : 资金计划维护
 作    者 ：jiamiao
 日    期 ：2005-7-14
 特殊说明 ：
 实现操作说明：
 修改历史 ：
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->

<%@ page import="java.util.*"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.bizlogic.OBCapitalPlanBiz"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
OBCapitalPlanInfo info = null;

try {
	 /** 权限检查 **/
    Log.print("=================进入页面/capital/plan/control/c003.jsp=========");
	String type = request.getParameter("type");
    String strTitle = ""; 
	if(type == null)
		strTitle = "资金计划维护";
	else if(type.equals("1"))
		strTitle = "资金计划复核";
    long id = new Long(request.getParameter("id")).longValue();
    /*
     * 校验客户端请求的有效性
     */
    OBHtml.validateRequest(out,request,response);

	/**
	* 初始化业务实体类 
	*/
	info = new OBCapitalPlanInfo();
	info.setClientID(sessionMng.m_lClientID);

	/**
	* 实例化逻辑类
	*/
	OBCapitalPlanBiz biz = new OBCapitalPlanBiz();

	/**
	* 调用方法
	*/
	info = biz.findById(id,sessionMng.m_lClientID);
	
	System.out.println("这里是c003"+info);
	
	request.setAttribute("info",info);

	if(type == null)
		pageInfo.setStrNextPageURL("../view/v002.jsp?value=update");
	else if(type.equals("1"))
		pageInfo.setStrNextPageURL("../view/v002.jsp?type=1&userid="+info.getInputUserID()+"&CheckuserID="+info.getCheckUserID());
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
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	
	
	rd.forward( request,response );
%>