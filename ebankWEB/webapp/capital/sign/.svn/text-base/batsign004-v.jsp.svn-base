<%--
/*
 * 程序名称：batsign004-v.jsp
 * 功能说明：批量签认查询页面
 * 作　　者：菅中尉
 * 完成日期：2008年03月31日
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
	String strTitle = "[批量签认]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
	
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
	String status = null;//存储状态
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
			strStartDate =(String)request.getParameter("strStartDate");
			System.out.println("strStartDate============="+strStartDate);
		}				
		if(request.getAttribute("strEndDate")!=null)
		{			
			strEndDate = (String)request.getParameter("strEndDate");	
			System.out.println("strEndDate============="+strEndDate);
		}
        //交易指令状态
	    status = (String)request.getParameter("sStatus");
		System.out.println("============================："+status);	
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
        //boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);  //是否使用CFCA证书验签
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);     
        boolean blnNotBeFalsified = true;	
        int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);	
        String[] nameArray = null;
		String[] valueArray = null; 		
%>

<html>
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
		title:'批量付款签认',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryBatchSignInfoDetail',//要调用的方法
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


 
 
function addme()
{
	var msg = "是否签认？";
	var isFalsified = false;
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
					msg = "交易中含有非法修改的数据，是否继续签认？？"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
				{
		%>
					msg = "交易中含有非法修改的数据，无法签认!"
					alert(msg);
					return false;
		<%
				}	
		%>	
	}else
	{
 	   	   msg = "是否签认？";
	       if (!confirm(msg))
		   {
			   return false;
		   } 		
	}  
	/*$.each($("#" + flexlist + " input[type='checkbox'][name='strID']"),function(i,n){
		if(n.checked){
			var isFalsifiedArr = n.value.split("####");
			n.value = isFalsifiedArr[0];
		}
	});*/
	frm.action = "batsign005-c.jsp?Straction=sign&sbatchno=<%=sbatchno%>";
	showSending();
	document.frm.submit();
	
}

function addOnClickFun(){

	showAlarm();
	
}

function cancelsign()
{  if(!validate()) 
	  {return;}
	if(!confirm("是否取消签认？"))
	{
		return false;
	}
	frm.action = "batsign005-c.jsp?Straction=cancelsign&sbatchno=<%=sbatchno%>";
	showSending();
	document.frm.submit();
}
function cancelme()
{
	frm.action = "batsign002-c.jsp?strStartDate=<%=strStartDate%>&strEndDate=<%=strEndDate%>&SelectStatus=<%=status%>&clientID=<%=sessionMng.m_lClientID%>&UserID=<%=sessionMng.m_lUserID%>&CurrencyID=<%=sessionMng.m_lCurrencyID%>";
	
	showSending();
		document.frm.submit();
}
function doCheckAll() {  
    if(frm.txtCheckAll.checked == true){
        if(frm.strID.length==undefined){
            frm.strID.checked= true;  
        }else{
	        for(i=0;i<frm.strID.length;i++){
	           frm.strID[i].checked= true;
	        }
        }
    }else if(frm.txtCheckAll.checked == false){
       if(frm.strID.length==undefined){
            frm.strID.checked= false;  
       }else{
	      for(i=0;i<frm.strID.length;i++){
	        frm.strID[i].checked = false;
	      }
      }
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

<body>
<form method="post" name="frm">
<!--判断是复核还是取消复核字段,此处为未复核 -->
<input type="hidden" name="sbatchno" value="<%=sbatchno %>"/>
<input type="hidden" name="clientID" value="<%=sessionMng.m_lClientID  %>">
<input type="hidden" name="UserID"    value="<%=sessionMng.m_lUserID %>">
<input type="hidden" name="CurrencyID" value="<%=sessionMng.m_lCurrencyID %>">	
<input type="hidden" name="status" value="<%=status %>">	
<input type="hidden" name="strStartDate" value="<%=strStartDate %>">	
<input type="hidden" name="strEndDate" value="<%=strEndDate %>">	
		
		<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><% if(status.equals("2")){%>
		批量签认			
			<% 
		}else if(status.equals("3")){
			%>
			批量取消签认
			<%
			}
			%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	
<br/>

<div id='message' style=display:none>
<table width=100% border="0" cellspacing="0" cellpadding="0" align="" class = normal>
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
          <%
          if(status.equals("2")){
        	  %>
        	<td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name=add type=button value=" 签 认 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();">&nbsp;&nbsp;
			</div>
            </td>
        	  <% 
          }else if(status.equals("3")){
        	  %>       	  
        	 	<td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name=add type=button value="取消签认" onClick="Javascript:cancelsign();" onKeyDown="Javascript:cancelsign();">&nbsp;&nbsp;
			</div>
            </td>       	  
        	  <% 
          }
  		
          %>
        
        
		 
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class="button1" name=add type=button value=" 返回 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">&nbsp;&nbsp; 
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
			//out.print("document.all('result').style.display='block';");
			out.print("</script>");
		}
%>
</body>
</html>

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
<safety:resources />
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/SignValidate.inc" %>