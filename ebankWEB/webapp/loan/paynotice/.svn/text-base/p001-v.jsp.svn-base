<%
/*
控制变量
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*"				
%>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<%


	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	long CurrencyID = sessionMng.m_lCurrencyID;
	String strTitle = "提款申请";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
	        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
		
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
        
        //定义变量
        Timestamp tsSystem = Env.getSystemDate(); 
        long lID = -1;  
        long lOfficeID = sessionMng.m_lOfficeID; 
        long lInputUserID = sessionMng.m_lUserID; 

%>
<form name="frm" method="post" >
<input type="hidden" name="control">
<TABLE border=0 class=top height=100 width="730">
    <TBODY>
  

    <TR class="tableHeader">
      <TD class=FormTitle height=29><B><%=strTitle%></B></TD></TR>
    <TR>
    <TD align=center height=80>
      <TABLE align=center border=0 width="100%">
        <TBODY>
                <tr>
                <td width="10%"></td>
                <td width="25%"></td>
                <td></td>
                </tr>
  
        <TR>
                <td align="right"><font color=#FF0000>*</font></td>
<%                              
                                //合同编号放大镜
                                String strMagnifierNameContract = URLEncoder.encode("合同编号");
                                String strFormNameContract = "frm";
                                String strPrefixContract = "";
                                String[] strMainNamesContract = {"txtContractCode"};
                                String[] strMainFieldsContract = {"ContractCode"};
                                String[] strReturnNamesContract = {"lContractID"};
                                String[] strReturnFieldsContract = {"id"};
                                String strReturnInitValuesContract = "";
                                String[] strReturnValuesContract = {"-1"};
                                String[] strDisplayNamesContract = {URLEncoder.encode("合同编号")};
                                String[] strDisplayFieldsContract = {"ContractCode"};
                                int nIndexContract = 0;
                                String strMainPropertyContract = "";
                                String strSQLContract = "getContractSQL3("+lClientID+","+CurrencyID+")";
                                String strMatchValueContract = "";
                                String strNextControlsContract = "add";
                                String strTitleContract = "合同编号";
                                String strFirstTDContract = "";
                                String strSecondTDContract = "";

                                OBMagnifier.showZoomCtrl(out
                                ,strMagnifierNameContract
                                ,strFormNameContract
                                ,strPrefixContract
                                ,strMainNamesContract
                                ,strMainFieldsContract
                                ,strReturnNamesContract
                                ,strReturnFieldsContract
                                ,strReturnInitValuesContract
                                ,strReturnValuesContract
                                ,strDisplayNamesContract
                                ,strDisplayFieldsContract
                                ,nIndexContract
                                ,strMainPropertyContract
                                ,strSQLContract
                                ,strMatchValueContract
                                ,strNextControlsContract
                                ,strTitleContract
                                ,strFirstTDContract
                                ,strSecondTDContract);
%>           </TR>

        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=6 height=2>
            <DIV align=right><FONT size=2></FONT><FONT size=2></FONT><FONT 
            size=2>
                  <input type="hidden" name="strAction" value="addnew">
				  <input class=button name=add type=button value=新增提款申请单 onClick="clickAdd(frm)" onKeyDown="clickAdd(frm)">
                	
				  </FONT></DIV></TD></TR></TBODY></TABLE></TD></TR>
                    
                        
                        </TBODY></TABLE></form>
<SCRIPT language=JavaScript>

//点击新增
function clickAdd(frm)
{
        if(frm.lContractID.value < 0)
        {
                alert('请使用放大镜输入合同号！');
                frm.txtContractCode.focus();
                return false;
        }
        else
        {
                frm.action="p002-c.jsp";
                frm.submit();
        }
}
//点击链接查找
function clickSearch(frm)
{
        frm.action="S449-1.jsp";
        frm.submit();
}
</script>
<script language=JavaScript>
//设置第一个焦点
firstFocus(frm.txtContractCode);
setFormName("frm");
</script>

<%
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>

