<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：基础设置-汇款用途摘要设置
 * 作　　者：
 * 完成日期：2010年9月19日
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@page import="com.iss.itreasury.util.Constant"%>
<%@page import="com.iss.itreasury.util.IException"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	//固定变量
	String strTitle = null;
	long Isdelete=-1;
     /* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        // 用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }

        
        //显示文件头   
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String strContext = request.getContextPath();//http://xxxx/../cpob
        Collection collection = (Collection)request.getAttribute("collection");
		Iterator iterator = null;
		if (collection != null)
		{
            iterator = collection.iterator();
		}
%>

<% /*************************Use  OBAbstractSetting EJB Model********************************/ %>
<% 
		OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz(); 
		OBAbstractSettingInfo queryInfo = new OBAbstractSettingInfo();
		queryInfo.setNofficeid(sessionMng.m_lOfficeID);
		queryInfo.setNclientid(sessionMng.m_lUserID);
		queryInfo.setNstatusid(Constant.RecordStatus.VALID);

		String strAction = null;
		String strTMP = "";
		 strAction = (String)request.getAttribute("strAction");	
		if(strAction != null && strAction.equals("deleteALL"))
        {
            String  checkIdArr =  (String)request.getAttribute("checkIdArr"); //接收保存checkbox信息的数组
			request.setAttribute("strAction","deleteALL");
			checkIdArr = checkIdArr.substring(0,checkIdArr.length()-1);
			Isdelete = OBAbstractSetting.deleteStandardAbstract(checkIdArr);
				
		  	if(Isdelete > 0){
				//sessionMng.getActionMessages().addMessage("删除成功");
				 %>
				 <script language="javascript" type="text/javascript">
		           window.location.href="v001.jsp?control=view&strAction=deleteALLok"; 
				 </script>
				<%    
			}
		}
		if(strAction != null && strAction.equals("deleteALLok"))
        {
			 sessionMng.getActionMessages().addMessage("删除成功");  
		}
%>

<% /*************************Page Information ********************************/ %>

<form name="form1" method="post" action="v001.jsp">
<input type="hidden" name="checkIdArr" value="" >
<input type="hidden" name="strAction" value="" >
<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>" >
<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>" >
<table width="98%" cellpadding="0" cellspacing="0" class="title_top">
		<tr>
		    <td height="22">
				<table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
						<td style="width:100%" class=title nowrap><span class="txt_til2">标准摘要定义设置</span></td>
					    <td class=title_right width="17"><a class=img_title></td>
					</TR>
				</TABLE>
				<br/>
				
				 <tr>
			     	<td colspan="4">
			     		<TABLE border="0" width="100%" class="top">
						<TBODY>
							<tr>
								<TD width="5">&nbsp;</TD>
								<TD width="*%" >
									<br><TABLE width="100%" id="flexlist"></TABLE><br>
								</TD>
								<TD width="5">&nbsp;</TD>
							</tr>
						</TBODY>
						</TABLE>
			     
		      <TABLE  border=0 width="98%" >
		        <TBODY>
			        <TR>
			        	<td width=90% colspan=7></td>
			          	<TD height=5 width=6%>
				           		<INPUT class=button1 name=Submit type="button" value=" 新 增 " onclick="add()"> 
			            </TD>&nbsp;
			          	<TD height=5 >
				           	<INPUT class=button1 name=Submit2312 type="button" value=" 删 除 " onClick="doDelet()"> 
			            </TD>
			        </TR>
		        </TBODY>
		      </TABLE>
		 </TD>
	   </TR>
</TABLE>

</form>		
<% /*************************HTML 的结束标志 ********************************/ %>	




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
<script language="JavaScript">
$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
        	{elType : 'checkbox', elName : 'lID1', name : 'ID', width : 85, sortable : true, align: 'center'},
			{display: '摘要代号', name: 'sbatchno', elType : 'link', elName : 'batchno', methodName : 'doLink("?")', width: 490, sortable: false, align: 'center'},
        	{display: '摘要描述',  name : 'CPF_SREJECT', width : 490, sortable : false, align: 'center'}
		],//列参数
		title:'标准摘要定义设置',
		classMethod : 'com.iss.itreasury.ebank.obsystem.action.OBAbstractSettingAction.query',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false,
		height : 300
		//callback : 'addOnClickFun()'
	});
	
});
function getFormData() 
{
	return $.addFormData("form1","flexlist");
}
function add(){
	window.location.href = "v002.jsp?control=view&lID=-1";
}
function doLink(id){
	window.location.href = "v002.jsp?control=view&lID="+id;
}
var flexlist = "flexlist";
function addOnClickFun(){
	//单选框	
	var lisCheckBox = $("input[type=checkbox][name='lID1']");
	$(lisCheckBox).each(function () {
		$(this).attr("onclick", "");
  	});
   	$(lisCheckBox).each(function () {
  		$(this).click(function () {
  			isCheckedAll();
    	});
	});

}
/**
*	实现全选功能
*
**/
function checkAll(o){
	var checkboxes = document.getElementsByName("lID1");
	if(checkboxes.length==0)
		return;
	for(var i=0;i<checkboxes.length;i++){
		checkboxes[i].checked=o.checked;
	}
}

function isCheckedAll()
{
	//全选
	var lisCheckBox = $("input[type=checkbox][name='lID1_all']");
	var isCheck = true;
	$.each($("#" + flexlist + " input[type='checkbox'][name='lID1']"),function(i,n){
		if(n.checked == false){
			isCheck = false;
		} 
	});
	
	$.each($("#" + flexlist + " input[type='checkbox'][name='lID1_all']"),function(i,n){
		if(isCheck == false){
			alert('isCheck == false');
			n.checked = false;
		}else{
			alert('isCheck == false');
			n.checked = true;
		}
	});
}

function doCheck()
 {
 	  	var isCheck = false;
 	  	$.each($("#" + flexlist + " input[type='checkbox'][name='lID1']"),function(i,n){
			if(n.checked == true){
				isCheck = true;
			} 
		});
		if (!isCheck)
		{
			alert("请选择要删除的记录");
			return false;
		}
		return isCheck;
 }
 function doDelet()
 {
 	//剔除全选按钮的value
 	var checkIdArr = "";
 	$.each($("#" + flexlist + " input[type='checkbox'][name='lID1']"),function(i,n){
		if(n.checked == true){
			checkIdArr += n.value+",";
		} 
	});
 	if (doCheck())
	{
		if (confirm("是否删除摘要？")){
			form1.checkIdArr.value=checkIdArr;
			form1.strAction.value="deleteALL";
			showSending(); 
			form1.submit();
		}
	}

 }

</script> 
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />