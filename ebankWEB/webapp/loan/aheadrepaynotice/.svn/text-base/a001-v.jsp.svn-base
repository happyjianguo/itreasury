<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
java.net.URLEncoder,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.loan.contract.dataentity.*
" 
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 
<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款通知单]";
%>	
<%
	  try
	  {
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
		
		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frmFind" action="../aheadrepaynotice/a002-c.jsp" method="post">

<TABLE border=0 class=top width=740>
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B>贷款还款通知单——新增</B></TD>
    </TR>
  <TR>
    <TD align=center>
        <TABLE align=center border=0 width=691>
          <TBODY> 
          <TR> 
            <td width="1" height="2"><font color=#FF0000><strong>*</strong></font></td>
<%
		String strMagnifierName = URLEncoder.encode("合同编号");							//放大镜的名称
		String strFormName = "frmFind";										//主页面表单名称
		String[] strMainNames1 = {"ctrlContractIDFromCtrl"};	//放大镜回显栏位值列表
		String[] strMainFields1 = {"sContractCode"};		//放大镜回显栏位对应的表格字段
		String[] strReturnNames1 = {"ctrlContractIDFrom"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields1 = {"ID"}; //放大镜返回值(隐含值)对应的表格字段列表	
		String sTempValue = "-1";
		String[] strReturnValues1 = {sTempValue};				//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames1 = {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields1 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strReturnInitValues = "";									//放大镜的对应控件栏位属性
		
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames1,strMainFields1,strReturnNames1,strReturnFields1,strReturnInitValues,
		strReturnValues1,strDisplayNames1,strDisplayFields1,
		intIndex,"","getContractCode()","","ctrlPayCtrl", "合同编号","","");
%>
            <td colspan="3" height="2">&nbsp;</td>
          </TR>
          <TR> 
            <td width="1" height="2"><font color=#FF0000><strong>*</strong></font></td>
<%
		 strMagnifierName = URLEncoder.encode("放款通知单编号");							//放大镜的名称
		 strFormName = "frmFind";										//主页面表单名称
		 String[] strMainNames = {"ctrlPayCtrl"};	//放大镜回显栏位值列表
		 String[] strMainFields = {"sCode"};		//放大镜回显栏位对应的表格字段
		 String[] strReturnNames = {"ctrlPayID"};							//放大镜返回值列表(隐含值)
		 String[] strReturnFields = {"ID"}; //放大镜返回值(隐含值)对应的表格字段列表	
		 String[] strReturnValues = {sTempValue};				//放大镜返回值(隐含值)对应的初始值
		 String[] strDisplayNames = {URLEncoder.encode("放款通知单编号")};				//放大镜小窗口显示的栏位名称
		 String[] strDisplayFields = {"sCode"};					//放大镜小窗口显示栏位对应的表格字段
		intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		strReturnInitValues = "";									//放大镜的对应控件栏位属性
		
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnInitValues,
		strReturnValues,strDisplayNames,strDisplayFields,
		intIndex,"","getPayCode()","","next", "放款通知单编号","","");
%>
            <td colspan="1" height="2">&nbsp;</td>
            <td colspan="2" height="2">&nbsp; </td>
          </TR>
          <TR> 
            <td width="1" height="2">&nbsp;</td>
            <td colspan="5" height="2"> 
              <hr>
            </td>
          </TR>
          <TR> 
            <TD width="1" height=2>&nbsp;</TD>
            <TD colSpan="5" height=2> 
              <DIV align="right"><FONT size=2> 
                <INPUT class=button name="next" onFocus="nextfield='submitfunction';" onclick="confirmSave()" type=button value=" 继 续 ">
                <!--INPUT class=button name="link"  onclick="goPage()" type=button value=" 链接查找 "-->
                </FONT></DIV>
            </TD>
          </TR>
          </TBODY>
        </TABLE>
      </TD></TR></TBODY></TABLE>
<P><BR></P>
</form>

<script language="javascript">
function goPage()
{
	window.location = "../aheadrepaynotice/a009-v.jsp?action=1";
}

function getContractCode()
{
	var sql =" SELECT id,sContractCode FROM loan_contractForm "  ;
	
	sql += "   WHERE  (nStatusID="	 + <%=LOANConstant.ContractStatus.ACTIVE%>;
	sql += "   OR nStatusID="	 + <%=LOANConstant.ContractStatus.EXTEND%> + ")";
	//sql += "   AND  nInputUserID="	+ <%=sessionMng.m_lUserID%>;	
	/*if  (frmFind.ctrlClientID.value > 0)
	{
		sql += "   AND  nBorrowClientID="	+ frmFind.ctrlClientID.value;
	}*/
	sql += " and ncurrencyid=<%=sessionMng.m_lCurrencyID%>  ";
	sql += " and NBORROWCLIENTID=<%=sessionMng.m_lClientID%>  ";
	sql += " ORDER BY sContractCode";
	return sql;
}

function getClient()
{
	var sql = "SELECT id,sCode,sName FROM client  ORDER BY  sCode";
	return sql ;
}

function getPayCode()
{
	var sql =" SELECT id,sCode FROM loan_payform "  ;
	
	sql += "   WHERE  nStatusID="	+ <%=LOANConstant.LoanPayNoticeStatus.USED%>;
		
	if  (frmFind.ctrlContractIDFrom.value > 0)
	{
		sql += "   AND  nContractID="	+ frmFind.ctrlContractIDFrom.value;
	}
	sql += " and ncurrencyid=<%=sessionMng.m_lCurrencyID%>  ";
	sql += " ORDER BY sCode";
	return sql;
}

function confirmSave()
{
	if (frmFind.ctrlContractIDFrom.value < 0 )
	{
		alert("请输入合同编号！");
		return;
	}
	
	if (frmFind.ctrlPayID.value < 0 )
	{
		alert("请输入放款通知单编号！");
		return;
	}
	
	showSending();
	frmFind.submit();
}

firstFocus(frmFind.ctrlContractIDFromCtrl);
//setSubmitFunction("confirmSave()");
setFormName("frmFind");	 
</script>			
<%
   		//显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
