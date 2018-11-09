<%--
/*
 * 程序名称：batchck004-v.jsp
 * 功能说明：批量复核查询页面
 * 作　　者：菅中尉
 * 完成日期：2007年04月20日
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo,
				com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao,
				java.util.*,
				com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%!/* 标题固定变量 */
	String strTitle = "[批量复核]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

	/* 实例化信息类 */
	//实体
	FinanceInfo info = new FinanceInfo();
	List infoList = null;
	//链接进入页面
	String strURL = null;
	//成功返回页面
	String strSuccessURL = "";
	//失败返回页面
	String strFaileURL = "";
	String sbatchno = "";//批次号
	
	String strStartDate = null;//上一个页面传来的开始日期
	
	String strEndDate = null;//上个页面传来的结束日期
	//查询类
	/* 用户登录检测与权限校验及文件头显示 */
	try {
		//用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		if(request.getAttribute("strStartDate")!=null)
		{			
			strStartDate =(String)request.getAttribute("strStartDate");
			System.out.println("strStartDate============="+strStartDate);
		}		
		
		
		if(request.getAttribute("strEndDate")!=null)
		{			
			strEndDate = (String)request.getAttribute("strEndDate");
			System.out.println("strEndDate============="+strEndDate);
		}
		
	
		//得到查询后的结果集合
		if(request.getAttribute("infoList")!=null)
		{
			//得到结果并显示结果集合
			infoList = (List)request.getAttribute("infoList");
		}
		if(request.getParameter("sbatchno")!=null)
		{
			sbatchno = DataFormat.formatString(request.getParameter("sbatchno"));
		}
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
       // boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);  //是否使用CFCA证书验签
        int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);        
        boolean blnNotBeFalsified = true;		
        String[] nameArray = null;
		String[] valueArray = null;        
%>

<script language="javascript">
var flexlist = "flexlist";

$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'strID', name : 'strID', width : 50, sortable : true, align: 'center'},
			{display: '序号',  name : 'no', width : 50, sortable : false, align: 'center'},
			{display: '币种',  name : 'ncurrencyid', width : 60, sortable : true, align: 'center'},
			{display: '付款帐号',  name : 'payeracctno', width : 100, sortable : true, align: 'center'},
			{display: '付款人',  name : 'payername', width : 60, sortable : true, align: 'center'},
			{display: '业务类型',  name : 'ntranstype', width : 100, sortable : true, align: 'center'},
			{display: '金额',  name : 'mamount', width : 100, sortable : true, align: 'center'},
			{display: '收款方帐号',  name : 'payeeacctno', width : 100, sortable : true, align: 'center'},
			{display: '收款人',  name : 'payeename', width : 60, sortable : true, align: 'center'},
			{display: '汇入省',  name : 'spayeeprov', width : 60, sortable : true, align: 'center'},
			{display: '汇入市',  name : 'spayeecity', width : 60, sortable : true, align: 'center'},
			{display: '汇入行',  name : 'spayeebankname', width : 130, sortable : true, align: 'center'},
			{display: '汇入行CNAPS号',  name : 'spayeebankcnapsno', width : 100, sortable : true, align: 'center'},
			{display: '汇入行机构号',  name : 'spayeebankorgno', width : 100, sortable : true, align: 'center'},
			{display: '汇入行联行号',  name : 'spayeebankexchangeno', width : 100, sortable : true, align: 'center'},
			{display: '备注',  name : 'snote', width : 100, sortable : true, align: 'center'},
			{display: '状态',  name : 'nstatus', width : 60, sortable : true, align: 'center'}
		],//列参数
		title:'批量支付复核',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryBatchCheckInfoDetail',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		callback : 'addOnClickFun()'
	});
	
});

function getFormData() 
{
	return $.addFormData("frm","flexlist");
}

function query()
{	
	if(validate(frm)){
		$.gridReload("flexlist");
	}
}

function addOnClickFun(){

	showAlarm();
	
}

function addme()
{
	var msg = "是否复核？";
	var isFalsified = false;

	frm.action = "batchck005-c.jsp?sbatchno=<%=sbatchno%>";
	if(!validate()) 
	  {return;}
			  
	<%
	if(isUseCertification)
	{
	%>
		$.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
			if(n.checked&&n.isFalsified){
				isFalsified = true;
			} 
		});

	<%
	}
	%>
	
	if(isFalsified)
	{
		<%
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "交易中含有非法修改的数据，是否继续复核？？"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
				{
		%>
					msg = "交易中含有非法修改的数据，无法复核!"
					alert(msg);
					return false;
		<%
				}	
		%>	
	}else
	{
 	   	   msg = "是否复核？";
	       if (!confirm(msg))
		   {
			   return false;
		   } 		
	}
	showSending();
	document.frm.submit();
	
}
function cancelme()
{
	frm.action = "batchck002-c.jsp?strStartDate=<%=strStartDate%>&strEndDate=<%=strEndDate%>&sStatus=1";
	showSending();
		document.frm.submit();
}
function isCheckedAll()
{
	var isCheck = true;
	for(var i=0;i<document.frm.strID.length;i++)
	{
		if(document.frm.strID[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.frm.txtCheckAll.checked = true;
	else
		document.frm.txtCheckAll.checked = false;
	if(document.frm.strID.length == undefined){
		document.frm.txtCheckAll.checked = document.frm.strID.checked;
	}		
} 
function validate()
{
	var isCheck = false;
    $.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
		if(n.checked){
			isCheck = true;
		} 
	});
    if (!isCheck) {
        alert("请选择记录");
        return false;
    }
	return  isCheck;
}
</script>

<safety:resources />

<body >
<form method="post" name="frm" >
<!--判断是复核还是取消复核字段,此处为未复核 -->
<input type="hidden" name="sbatchno" value="<%=sbatchno %>"/>
		
<table cellpadding="0" cellspacing="0" width="98%" class="title_top">
	<tr>
		<td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">批量支付复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	
<br/>

<div id='message' style=display:none>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="" class = normal>
		<tr class="MsoNormal">
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="" class="MsoNormal">
	<%
			Vector rf = null;//显示信息
			int iCount = -1;
			if(request.getAttribute("return")!=null)
			{
				rf = (Vector)request.getAttribute("return");
				iCount = rf.size();//数量
				for (int i=0;i<iCount;i++)//显示
				{
					String strTmp = (String)rf.elementAt(i);
					out.print("<br>"+strTmp);
				}
			}
	%>
			</td>
			<td width="1" height="25"></td>
		</tr>
	</table>
</div>

<br/>

	<TABLE border="0" width="100%">
		<TBODY>
			<tr>
				<TD width="*%">
					<TABLE width="100%" id="flexlist"></TABLE>
				</TD>
			</tr>
		</TBODY>
	</TABLE>

<br>

	<table width=100% border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name=add type=button value=" 复 核 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();">&nbsp;&nbsp;
			</div>
          </td>
		  <td width="9" height="17">&nbsp;</td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class="button1" name=add type=button value=" 返 回 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">&nbsp;&nbsp; 
			</div>
          </td>
        </tr>
      </table>
      
   	</td>
	</tr>
</table>

</form>
<% 
	if(request.getAttribute("return")!=null)
	{
		out.print("<script language = 'javascript'>");
		out.print("document.all('message').style.display='block';");
		out.print("</script>");
	}
	
	if(request.getAttribute("infoList")!=null)
		{
			//得到结果并显示结果集合
			out.print("<script language = 'javascript'>");
			out.print("document.all('result').style.display='block';");
			out.print("</script>");
		}
%>
</body>

<%
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		Log.print("batchck004-v异常消息："+ie.toString());
		return;
	}
%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/SignValidate.inc" %>