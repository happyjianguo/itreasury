<%--
 页面名称 ：c002.jsp
 页面功能 : 账户授权保存控制页面
 作    者 ：ruiwang
 日    期 ：2006-11-8
 转入页面 ：c001.jsp
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.ebank.obsystem.dao.OB_AcctPrvgByClientDaoBankAcount,
				com.iss.itreasury.ebank.obsystem.bizlogic.OBAcctPrvgBizBankAcount,
				com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo,
				com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo,
				com.iss.itreasury.ebank.obsystem.dataentity.AccountInfo,
				java.util.Iterator,
				java.util.Vector,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
				
				
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>  
<% 
try {
	 /** 权限检查 **/
   Log.print("=================进入页面budget/constitute/control/c002-bankacount.jsp=========");
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
%>

<%	
	Vector vAdd=new Vector();
	Vector vDel=new Vector();	
	int num=0;
	String strNextPageURL="../control/c001-bankacount.jsp";
	Collection con;
	long clientId=sessionMng.m_lClientID;
	Log.print("********************登录人ID："+clientId);
	
	//得到v001.jsp页面中的number
	
	String tmp1=(String)request.getAttribute("number");
    	if( (tmp1!=null)&&(tmp1.length()>0) )
    		num=Integer.valueOf(tmp1).intValue();
    	Log.print("number:"+num);
    long currencyid=sessionMng.m_lCurrencyID; 	
	
	//删除原先的记录
	OBAcctPrvgBizBankAcount bz=new OBAcctPrvgBizBankAcount();
	OB_AcctPrvgByClientDaoBankAcount obABCD=new OB_AcctPrvgByClientDaoBankAcount();
	con=obABCD.getAcctIDs(clientId,currencyid);
	if(con==null){
		
		Log.print("********************con为空");
	}
	else{
		Log.print("************con不为空");
	}
	Iterator it=con.iterator();
	while(it.hasNext()){
		AccountInfo accountInfo=(AccountInfo)it.next();
		vDel.add(accountInfo);
	}
	Log.print("&&&&&&&&&&&&&&&&&&&&&&&&&&进入delAcctPrvgs(vDel)");
	bz.delAcctPrvgs(vDel);
	Log.print("c页面删除 完毕");
	//插入新的状态记录！
	String myCheck="checkbox";
	int temp=0;

	long myClientId=-1;
	long myAccountId=-1;
	Log.print("####################值"+request.getAttribute("checkbox0"));
	for(int k=0;k<num;k++){
		Log.print("~~~~~~~~~~~~~~~~~~~~~~~进入"+k+"次k循环");
		Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~myCheck+temp:"+myCheck+temp);
		if(request.getParameterValues(myCheck+temp)!=null){
			String[] varieties = request.getParameterValues(myCheck+temp);
			Log.print("得到varieties[]");
			for(int i=0;i<varieties.length;i++){
				if(varieties[i].trim().length()>0){
					int tmp=0;
					Log.print("varieties["+i+"]值:"+varieties[i]);
					for(int j=0;j<varieties[i].length();j++){
						char c=varieties[i].charAt(j);
						if(c==','){
							Log.print("myClientId值:"+varieties[i].substring(0,j));
							myClientId=Long.parseLong(varieties[i].substring(0,j));
							tmp=j+1;
							break;
						}
					}
					Log.print("111111111111");
					Log.print("myAccountId:"+varieties[i].substring(tmp,(varieties[i].length())));
					myAccountId=Long.parseLong(varieties[i].substring(tmp,(varieties[i].length())));
					AcctPrvgByClientInfo acctPrvgByClientInfo=new AcctPrvgByClientInfo();
					acctPrvgByClientInfo.setClientID(myClientId);
					acctPrvgByClientInfo.setAccountID(myAccountId);
					//bz.saveAcctPrvgs(acctPrvgByClientInfo);
					vAdd.add(acctPrvgByClientInfo);
				}
				
			}
		}
		temp++;
	}
	bz.saveAcctPrvgs(vAdd);
	
	request.setAttribute("clientInfo",con);
%>

<%
	sessionMng.getActionMessages().addMessage("保存成功");
	Log.print("Next Page URL:" + strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
	
%>
<%
}catch( Exception exp ){
	String strNextPageURL="../control/c001-gd.jsp";
	exp.printStackTrace();
	sessionMng.getActionMessages().addMessage("保存失败");
	Log.print("Next Page URL:" + strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );

}

%>