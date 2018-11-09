<%
/**
 * 页面名称 ：1007-c.jsp
 * 页面功能 : 保存贷款的基本资料，然后调用findByID获得LoanApplyInfo对象,然后转到贷款基本属性界面
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,
			com.iss.itreasury.ebank.obdataentity.*,
			com.iss.itreasury.ebank.util.*,
			com.iss.itreasury.ebank.obloanapply.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.bizlogic.*,
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
	String tmp="";
	String action="";
	
	long    loanID=-1;             //流水号
    long    loanType=-1;           //贷款种类
    double  loanAmount=0;          //借款金额
    //long    loanIntervalNum=0;     //借款期限
    String  loanReason="";         //借款原因
    String  loanPurpose="";        //借款目的
    String  other="";              //其他，还款来源及措施,        
    long    bankInterestID=-1;     //银行利率
    double  chargeRate=0;          //手续费率
	long    checkPayType=-1;       //手续费收取方式
    long    intervalNum=-1;        //期限
    Timestamp startDate=null;           //贷款开始日期
    Timestamp endDate=null;             //贷款结束日期
    String  clientInfo="";         //借款单位介绍
    double  saleAmount=0;          //财务公司承贷额度
    double  sCale=0;          //财务公司承贷比例
    double  interestRate=0;         //委托用贷款利率	
	long subLoanType=-1;//贷款子类型
	double assureChargeRate = 0; //担保费率
	long assureChargeTypeID = -1;//担保费收取方式
	String beneficiary = "";	 //受益人
	long assureTypeID1 = -1;	 //担保类型1
	long assureTypeID2 = -1;	 //担保类型2
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
		
	    tmp=(String)request.getAttribute("txtAction");
	    //System.out.println("++++++++++++++++"+tmp);
	    System.out.println("_________________"+request.getParameter("txtAction"));
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
	    
		tmp=(String)request.getAttribute("lLoanID");		//流水号
		//System.out.println("++++++++++++++++"+tmp);
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
			
		tmp=(String)request.getAttribute("lLoanType");
		//System.out.println("++++++++++++++++"+tmp);
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanType=Long.valueOf(tmp).longValue();			
	
		tmp=(String)request.getAttribute("loanamount");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			loanAmount=Double.valueOf(tmp).doubleValue();
		}	
		
		tmp=(String)request.getAttribute("loanreason");
		//System.out.println("++++++++++++++++"+tmp);
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanReason=tmp;
		
		tmp=(String)request.getAttribute("loanpurpose");
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanPurpose=tmp;
		
		tmp=(String)request.getAttribute("wjh");
		if ( (tmp!=null)&&(tmp.length()>0) )
			other=tmp;		
		
		tmp=(String)request.getAttribute("hkly");
		if ( (tmp!=null)&&(tmp.length()>0) )
			other=tmp;
		
		tmp=(String)request.getAttribute("bankinterest");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			interestRate=Double.valueOf(tmp).doubleValue();
		}
			
		tmp=(String)request.getAttribute("chargerate");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			chargeRate=Double.valueOf(tmp).doubleValue();	
		}	
		
		tmp=(String)request.getAttribute("checkPayType");
		//System.out.println("++++++++++++++++"+tmp);
		if ( (tmp!=null)&&(tmp.length()>0) )
			checkPayType=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("intervalnum");
		if ( (tmp!=null)&&(tmp.length()>0) )
			intervalNum=Long.valueOf(tmp).longValue();	
			
		tmp=(String)request.getAttribute("clientinfo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			clientInfo=tmp;			
			
		tmp=(String)request.getAttribute("saleamount");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			saleAmount=Double.valueOf(tmp).doubleValue();
		}	

		tmp=(String)request.getAttribute("sCale");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			sCale=Double.valueOf(tmp).doubleValue();
			//saleAmount = sCale;
		}	
			
		tmp=(String)request.getAttribute("startdate");
		if ( (tmp!=null)&&(tmp.length()>0) )
			startDate=DataFormat.getDateTime(tmp);
		
		tmp=(String)request.getAttribute("enddate");
		if ( (tmp!=null)&&(tmp.length()>0) )
			endDate=DataFormat.getDateTime(tmp);
		tmp=(String)request.getAttribute("subLoanType");
		if ( (tmp!=null)&&(tmp.length()>0) )
			subLoanType=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("assureChargeRate");
		if ( (tmp!=null)&&(tmp.length()>0) ){
			tmp=DataFormat.reverseFormatAmount(tmp);
			assureChargeRate=Double.valueOf(tmp).doubleValue();
		}	
		tmp=(String)request.getAttribute("assureChargeTypeID");
		if ( (tmp!=null)&&(tmp.length()>0) )
			assureChargeTypeID=Long.valueOf(tmp).longValue();
		tmp=(String)request.getAttribute("beneficiary");
		if ( (tmp!=null)&&(tmp.length()>0) )
			beneficiary=tmp.trim();
		tmp=(String)request.getAttribute("assureTypeID1");
		if ( (tmp!=null)&&(tmp.length()>0) )
			assureTypeID1=Long.valueOf(tmp).longValue();
		tmp=(String)request.getAttribute("assureTypeID2");
		if ( (tmp!=null)&&(tmp.length()>0) )
			assureTypeID2=Long.valueOf(tmp).longValue();

		
		OBLoanBasicInfo bInfo=new OBLoanBasicInfo();
		bInfo.setLoanID(loanID); 
		bInfo.setLoanType(loanType);
		bInfo.setLoanAmount(loanAmount);
		bInfo.setLoanReason(loanReason);
		bInfo.setLoanPurpose(loanPurpose);
		bInfo.setOther(other);
		bInfo.setBankInterestID(bankInterestID);
		bInfo.setChargeRate(chargeRate);
		bInfo.setCheckPayType(checkPayType);
		bInfo.setIntervalNum(intervalNum);
		bInfo.setStartDate(startDate);
		bInfo.setEndDate(endDate);
		bInfo.setClientInfo(clientInfo);
		bInfo.setSaleAmount(saleAmount);
		bInfo.setSubTypeId(subLoanType);
		bInfo.setAssureChargeRate(assureChargeRate);
		bInfo.setAssureChargeTypeID(assureChargeTypeID);
		bInfo.setBeneficiary(beneficiary);
		bInfo.setAssureTypeID1(assureTypeID1);
		bInfo.setAssureTypeID2(assureTypeID2);

		//bInfo.setSaleScale(sCale);  //UNDO
		bInfo.setInterestRate(interestRate);
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
		bInfo.setSecurityInfo(sInfo);
				
				System.out.println(assureTypeID2+"+++++"+assureTypeID1+"^^^^"+assureChargeTypeID+"^^^^^^^^"+beneficiary);
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		long ret=lla.updateBasic(bInfo);
		System.out.println("action:"+action);
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
			String strURL="/loan/loanapply/l008-v.jsp";
		
			OBLoanApplyInfo laInfo=lla.findByID(loanID,sInfo);
		
			request.setAttribute("LoanApplyInfo",laInfo);	
		
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