<%
/**
 * 页面名称 ：l009-c.jsp
 * 页面功能 : 保存客户的基本属性资料，然后转到贷款保证资料界面
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.ebank.obloanapply.bizlogic.*,
	com.iss.itreasury.util.*"
%>


<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
	
	long        loanID=-1;          //流水号
    long        loanType=-1;        //贷款种类
    long        isCircle=0;         //是否循环
    long        isSaleBuy=-1;       //是否卖方贷款
    long        isTechnical=-1;      //是否技改贷款
    long        isCredit=-1;         //是否信用保证
    long        isAssure=-1;         //是否担保
    long        isImpawn=-1;         //是否质押
    long        isPledge=-1;         //是否抵押
	long        isRecognizance=-1;         //是否保证金

    
    String action="";
    long statusID=-1;
    
    
 		
	/* 用户登录检测与权限校验及文件头显示 */
    try 
    {
      	
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		
		String tmp="";
		
		tmp=(String)request.getAttribute("lLoanID");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
			
		tmp=(String)request.getAttribute("lLoanType");
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanType=Long.valueOf(tmp).longValue();		
			
    	tmp=(String)request.getAttribute("iscircle");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isCircle=Long.valueOf(tmp).longValue();
    
    	tmp=(String)request.getAttribute("issalebuy");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isSaleBuy=Long.valueOf(tmp).longValue();
    
    	tmp=(String)request.getAttribute("istechnical");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isTechnical=Long.valueOf(tmp).longValue();
    	
    	
    
    	tmp=(String)request.getAttribute("iscredit");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isCredit=Long.valueOf(tmp).longValue();
    
    	tmp=(String)request.getAttribute("isassure");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isAssure=Long.valueOf(tmp).longValue();
    
    	tmp=(String)request.getAttribute("isimpawn");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isImpawn=Long.valueOf(tmp).longValue();
    

		

    	tmp=(String)request.getAttribute("ispledge");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isPledge=Long.valueOf(tmp).longValue();	
    		
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue(); 
        
		tmp=(String)request.getAttribute("isRecognizance");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		isRecognizance=Long.valueOf(tmp).longValue(); 
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
		 
		OBLoanPropertyInfo pInfo=new OBLoanPropertyInfo();
		pInfo.setLoanID(loanID);
		pInfo.setLoanType(loanType);
		pInfo.setIsCircle(isCircle);
		pInfo.setIsSaleBuy(isSaleBuy);
		pInfo.setIsTechnical(isTechnical);
		pInfo.setIsCredit(isCredit);
		pInfo.setIsAssure(isAssure);
		pInfo.setIsImpawn(isImpawn);
		pInfo.setIsPledge(isPledge);
		pInfo.setIsRecognizance(isRecognizance);
		pInfo.setSecurityInfo(sInfo);


				

		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		lla.updateProperty(pInfo);
		
		if ( (action.equals("modify"))||(action.equals("check")) )
		{
%>
<html>
<body>
	<script language="javascript">
		parent.opener.location.reload();
		window.close();
	</script>
</body>
</html>	
<%				
		}
		else
		{		

			System.out.println("^^^^^^^^^^^^^^^^^^^");
			OBLoanApplyInfo laInfo=lla.findByID(loanID,sInfo);		
			request.setAttribute("LoanApplyInfo",laInfo);
	
			String strURL="";
			
			
			if ( (isCredit>0)&&(isRecognizance<0)&&(isAssure<0)&&(isImpawn<0)&&(isPledge<0) ){
				if(loanType==OBConstant.LoanInstrType.ASSURE){
					strURL="/loan/loanapply/l028-c.jsp";
				}else{
					strURL="/loan/loanapply/l021-v.jsp";
				}
			}else{
				strURL="/loan/loanapply/l010-v.jsp";
			}
			
			
			/* 获取上下文环境 */
			ServletContext sc = getServletContext();
	    
			/* 设置返回地址 */
			RequestDispatcher rd=null;
			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
			/* forward到结果页面 */
	    	rd.forward(request, response);
	    }
		return;
		
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>