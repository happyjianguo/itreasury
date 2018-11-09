<%--
/**
 * 程序名称：a113-v.jsp
 * 功能说明：账户历史明细查询页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-25
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.query.queryobj.*,
				 com.iss.itreasury.settlement.query.paraminfo.*,
				 com.iss.itreasury.settlement.query.resultinfo.*,
				 java.rmi.RemoteException,
				 java.sql.*,
				 java.util.*,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<safety:resources />

<%
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a113-v.jsp******\n");
    //标题变量
	String strTitle = "[历史明细]";
    try {
    	//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
    	
    	/* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		//定义变量
		Collection coll = null;
		Iterator it = null;
		long lCurrencyID = -1;
		long lClientID = -1;//客户ID
		String sAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		long lAccountTypeID =-1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID

		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;

		double dBalance = 0.0;//期初余额
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态

		String sTemp = null;	// 临时量
		sTemp = (String) request.getAttribute("lCurrencyID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lCurrencyID = Long.parseLong(sTemp);
		}
		Log.print("========lCurrencyID="+lCurrencyID);
		sTemp = (String) request.getAttribute("lAccountID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountID="+lAccountID);
		sTemp = (String) request.getAttribute("sAccountNo");
		if (sTemp != null && sTemp.trim().length() > 0) {
			sAccountNo = sTemp;
		}
		Log.print("========sAccountNo="+sAccountNo);
		sTemp = (String) request.getAttribute("tsStart");
		if (sTemp != null && sTemp.trim().length() > 0) {
			tsStartDate = DataFormat.getDateTime(sTemp);
		}
		Log.print("========tsStartDate="+tsStartDate);
		sTemp = (String) request.getAttribute("tsEnd");
		if (sTemp != null && sTemp.trim().length() > 0) {
			tsEndDate = DataFormat.getDateTime(sTemp);
		}
		Log.print("========tsEndDate="+tsEndDate);
		sTemp = (String) request.getAttribute("lAccountGroupID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountGroupID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountGroupID="+lAccountGroupID);
		sTemp = (String) request.getAttribute("lAccountTypeID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountTypeID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountTypeID="+lAccountTypeID);

		QueryTransAccountDetailWhereInfo qtwi = new QueryTransAccountDetailWhereInfo();
		QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
		QTransAccountDetail qobj = new QTransAccountDetail();

		if( request.getAttribute("whereInfo")!=null )
	 	{
        	qtwi = (QueryTransAccountDetailWhereInfo)request.getAttribute("whereInfo");
			lAccountID = qtwi.getAccountID();
			sAccountNo = qtwi.getAccountNo();
			tsStartDate = qtwi.getStartDate();
			tsEndDate = qtwi.getEndDate();
			lCurrencyID = qtwi.getCurrencyID();
			lAccountTypeID = qtwi.getAccountTypeID();
	 	}

		if( request.getAttribute("coll_resultInfo")!=null )
	 	{
        	coll = (Collection)request.getAttribute("coll_resultInfo");
	 	}

		String strBalance = (String)request.getAttribute("balance");
		if( request.getAttribute("balance")!=null)
		{
			if (strBalance != null && strBalance.trim().length() > 0)
			{
				dBalance = Double.valueOf(strBalance).doubleValue();
			}
		}
%>

<script language="JavaScript">
/* 详细信息查看处理函数 */
    function detailme(strTransNo,lID,lPutAccountID,lGetAccountID)
    {
        window.open("ShowTrans/S330.jsp?strTransNo=" + strTransNo + "&lID=" + lID + "&lPutAccountID=" + lPutAccountID + "&lGetAccountID=" + lGetAccountID);
    }
</script>

<form name="frmQry" method="get" action="a114-c" >
<input type="hidden" name="lAccountID" value="<%=lAccountID%>">
<input type="hidden" name="lAccountTypeID" value="<%=lAccountTypeID%>"> 
<input type="hidden" name="lAccountGroupID" value="<%=lAccountGroupID%>"> 
<input type="hidden" name="sAccountNo" value="<%=sAccountNo%>">
<input type="hidden" name="lCurrencyID" value="<%=lCurrencyID%>">
<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID%>">
<input type="hidden" name="sClientCode" value="<%=sessionMng.m_strClientCode%>">

<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
  <tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title nowrap="nowrap"><span class="txt_til2"><%=SETTConstant.AccountType.getName(lAccountTypeID)%></span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
    
       <table width=100% border="0"  cellpadding="0" cellspacing="0" class=normal id="table1">
	 
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td width="5" height="25" ></td>
          <td height="25" >账号：</td>
          <td  class="graytext" width=60> 
            <div align="right"></div>
          </td>
          <td height="20"  size=20><%=NameRef.getNoLineAccountNo(sAccountNo)%></td>
          <td></td><td></td>
          <td width="5"></td>
        </tr>

        <tr > 
          <td width="5" height="25" ></td>
          <td height="25" >执行日期：</td>
          <td align="right">由</td>
          <td width="60" height="25" nowrap>
            <fs_c:calendar 
          	    name="tsStart"
	          	value="" 
	          	properties="nextfield ='tsEnd'" 
	          	size="20"/>
	          <script>
	          		$('#tsStart').val('<%=DataFormat.getDateString(tsStartDate)%>');
	          </script>
          </td>
          <td align="right">至</td>
          <td height="25" nowrap> 
			<fs_c:calendar 
			          	    name="tsEnd"
				          	value="" 
				          	properties="nextfield =''" 
				          	size=""/>
				          	<script>
	          		$('#tsEnd').val('<%=DataFormat.getDateString(tsEndDate)%>');
	          </script>
		  </td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <TR>
        	<td colspan="7">
	            <div align="right">
				<input class=button1 name=add type=button value=" 查 找 " onClick="ConfirmSearch(frmQry)" onKeyDown="ConfirmSearch(frmQry)">&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
        	</td>
        </TR>
        <tr > 
          <td colspan="5" height="5"></td>
        </tr>
      </table>
      <hr>
      <br>
      <TABLE border="0" width="100%" class="top">
	       	<tr>
	       	   <td width="1%">&nbsp;</td>
				<TD width="*%">
					<br><TABLE width="100%" id="flexlist"></TABLE><br>
				</TD>
	       	   <td width="1%">&nbsp;</td>
			</tr>
		</TABLE>
      <br>
      <table width=100% border="0" cellspacing="0" cellpadding="0" >
		  <tr>
		    <td width="582" align="left" valign="top" height="16"><br>
		      查询日期：<%=Env.getSystemDateString()%>
		    </td>
		  </tr>
      </table>
    
        <table width=100% border="0" cellspacing="0" cellpadding="0" >
          <tr> 
            <td width="490" height="17"> 
              <div align="left"></div>
            </td>
          <td width="46"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right">
			<!--img src="/webob/graphics/button_xiazaichazhao.gif" width="119" height="18" onclick="javascript:downloadresult();"-->
			<input class=button1 name=add type=button value=" 下载查找结果 " onClick="javascript:downloadresult();" onKeyDown="javascript:downloadresult();">
			</div>
          </td>
            <td width="60" height="17"> 
            <div align="right">
			<!--img src="/webob/graphics/button_dayin.gif" width="46" height="18"   onclick="javascript:printme();"-->
			<input class=button1 name=add type=button value=" 打印 " onClick="javascript:printme();" onKeyDown="javascript:printme();">  </div>
            </td>
            <td width="60" height="17"> 
            <div align="right">
			<input type="Button" class="button1" value=" 返回 " width="46" height="18"   onclick="javascript:doBack();">
			</div>
            </td>
          </tr>
        </table>
 </form>
 
<SCRIPT language=JavaScript>
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });

	$("#flexlist").flexigridenc({
		colModel : [
			{display: '日期',  name : 'ExecuteDate', width : 127, sortable : true, align: 'center'},
			{display: '摘要',  name : 'Abstract', width : 127, sortable : true, align: 'center'},
			{display: '交易编号',  name : 'TransNo', width : 127, sortable : true, align: 'center'},
			{display: '票据号',  name : 'BillNo', width : 127, sortable : true, align: 'center'},
			{display: '银行支票号',  name : 'BankChequeNo', width : 127, sortable : true, align: 'center'},
			{display: '付款金额',  name : 'PayAmount', width : 127, sortable : true, align: 'center'},
			{display: '收款金额',  name : 'ReceiveAmount', width : 127, sortable : true, align: 'center'},
			{display: '余额',  name : 'dtinput', width : 127, sortable : false, align: 'center'}
		],//列参数
		title:'<%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>',
		classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryOBAccountQueryAmountDetailInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		//usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmQry","flexlist");
}

function ConfirmSearch(form)
{
	if (checkDate(form.tsStart,1,"开始日期") == false)
		return false; 
	if (checkDate(form.tsEnd,1,"结束日期") == false)
		return false; 
	form.lAccountID.value = <%=lAccountID%>;
	form.lAccountTypeID.value = <%=lAccountTypeID%>;
	form.lAccountGroupID.value = <%=lAccountGroupID%>;
	form.lCurrencyID.value = <%=lCurrencyID%>;
	form.action = "a114-c.jsp";
	form.target = "";
	showSending();
	form.submit();
}

function downloadresult()
{
	frmQry.action="Khdzd_d.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}

function doFindTransDetail(lTransTypeID,strTransNo,lGroupID)
{
	if(lTransTypeID == <%=SETTConstant.TransactionType.UPGATHERING%>)
	{
		window.open("../accountinfo/v726-d.jsp?&TransNo="+strTransNo+"&SerialNo="+lGroupID,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
	
	}else{
		window.open("../accountinfo/querycontrol.jsp?TransactionTypeID="+lTransTypeID+"&TransNo="+strTransNo+"&SerialNo="+lGroupID);
	}
}

function printme()
{
	frmQry.action="Khdzd.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}
function doBack()
{
	window.location.href = "a110-v.jsp";
}
 </SCRIPT>
 
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />