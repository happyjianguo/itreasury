<%@ page import="java.sql.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<%!
public static String GetParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = rqt.getParameter(strParamName);
	return sTemp==null?"":sTemp;
}
public static long GetNumParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = rqt.getParameter(strParamName);
	return (sTemp==null||sTemp.equals(""))?-1:Long.parseLong(sTemp);
}
%>
<%
	String strPayerAcctNo = "";
	String strPayeeAcctNo = "";
	double dAmount = 0.00;
	long lRemitType = 0;
	Timestamp tsExecute = null;
	long lInstructionID = -1;
	String sPayeeBankName = "";
	String sPayeeName = "";
	String sPayeeProv = "";
	String sPayeeCity = "";
	String sPayeeAccountInternalInternal = "";
	String sPayeeAcctNoZoom = "";
	String sPayeeNameZoomAcct = "";
	
	strPayerAcctNo = GetParam(request, "PayerAcctNo");
	sPayeeProv = GetParam(request, "sPayeeProv");
	sPayeeCity = GetParam(request, "sPayeeCity");
	lInstructionID = GetNumParam(request, "lInstructionID");
	strPayeeAcctNo = GetParam(request, "PayeeAcctNo");
	sPayeeName = GetParam(request, "sPayeeName");
	sPayeeBankName = GetParam(request, "sPayeeBankName");
	sPayeeAcctNoZoom = GetParam(request, "sPayeeAcctNoZoom");
	sPayeeNameZoomAcct = GetParam(request, "sPayeeNameZoomAcct");
	
	if (GetNumParam(request, "nRemitType") > 0) {
			lRemitType = GetNumParam(request, "nRemitType");
	} else {
			lRemitType = GetNumParam(request, "nRemitTypeHidden");
	}
	if (request.getParameter("dAmount") != null) {
			dAmount = Double.valueOf(
					DataFormat.reverseFormatAmount((String) request.getParameter("dAmount"))).doubleValue();
	}
	if (request.getParameter("tsExecute") != null) {
			tsExecute = DataFormat.getDateTime((String) request.getParameter("tsExecute"));
	}
	
	OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
	
	boolean commonflag = oBFinanceInstrDao.findByFinance(strPayerAcctNo,lRemitType, dAmount,tsExecute,strPayeeAcctNo,sPayeeProv,sPayeeCity,sPayeeBankName,sPayeeAcctNoZoom,sPayeeNameZoomAcct,lInstructionID);
	if(commonflag){
		out.write("1");
	}else{
		out.write("0");
	}
	
 %>
