<%--
 页面名称 ： batchpay_c.jsp
 页面功能 :  批量导入数据验证页面
 作    者 ： niweinan
 日    期 ： 2011-2-25
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao" %>
<%@ page import="com.iss.itreasury.ebank.obbatchpayment.dao.OBBatchPaymentDAO" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%

	/* 标题固定变量 */
	String strTitle = "批量付款";


	
try{
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
	
	String temp = null;
	long lRemitType = -1;
	String sPayerAcctNo = "";
	String sPayeeName = "";
	String sPayeeAcctNo = "";
	long payeeID = -1;
	OBFinanceInstrDao obfinanceInstrDao=new OBFinanceInstrDao();
	OBBatchPaymentDAO obBatchPaymentDAO = new OBBatchPaymentDAO();
	

	
	temp = request.getParameter("lRemitType");
	if(temp!=null)
	{
		lRemitType = Long.parseLong(temp);
	}
	
	temp = request.getParameter("sPayerAcctNo");
	if(temp!=null)
	{
		sPayerAcctNo = temp;
	}
	
	temp = request.getParameter("sPayeeName");
	temp = java.net.URLDecoder.decode(temp,"UTF-8"); 
	if(temp!=null)
	{
		sPayeeName = temp;
	}
	
	temp = request.getParameter("sPayeeAcctNo");
	if(temp!=null)
	{
		sPayeeAcctNo = temp;
	}

	//开始校验
	//先校验付款方帐号
    
	if(NameRef.getAccountIdByNo(sPayerAcctNo)>0)
	{
		if(!obfinanceInstrDao.checkAccountIsOwnedByUser(NameRef.getAccountIdByNo(sPayerAcctNo),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
		{
			out.println("<div id='result'>PayerAccountHaveNoAuthority</div>");  //付款方帐户没有权限
		}
		else  //付款方帐号校验通过
		{
			if(lRemitType==OBConstant.SettRemitType.INTERNALVIREMENT)  //如果类型为内部转账，开始校验收款方帐号
			{
				payeeID=obBatchPaymentDAO.findPayeeAccountID(sPayeeAcctNo,sPayeeName);
				if(payeeID<0)
				{
					out.println("<div id='result'>PayeeAccountNotExist</div>");  //收款方帐户不存在
				}
				else
				{
					if(sPayerAcctNo.equals(sPayeeAcctNo))
					{
						out.println("<div id='result'>SameNo</div>");  //收款方帐户和付款方编号相同
					}
					else
					{
						out.println("<div id='result'>Success</div>");  //校验通过
					}
				}
			}
			else
			{
				out.println("<div id='result'>Success</div>");  //校验全部通过
			}
			
		}
	}
	else
	{
		out.println("<div id='result'>PayerAccountNotExist</div>");  //付款方帐户不存在
	}
	
	

	
}
catch(Exception exp)
{
	exp.printStackTrace();
}
%>