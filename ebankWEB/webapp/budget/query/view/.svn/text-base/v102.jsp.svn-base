<%--
 页面名称 ：v102.jsp
 页面功能 : 分析考核 - 预算项目单位情况 选择项目页面
 作    者 ：liuyang
 日    期 ：
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo"%>
<%@ page import="com.iss.itreasury.budget.templet.dao.Budget_TempletDAO"%>





<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--引入js文件-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />

<form name="frm001" method="post" action ="../control/c002.jsp">
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<!-- 定义业务逻辑参数 -->

<%
try
{
	//请求检测
	/** 权限检查 **/
	String strTableTitle = "预算体系设置 - 预算项目单位情况查询";
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
	
	/** 显示文件头 **/
	OBHtml.showOBHomeHead(out,sessionMng,"[预算项目单位情况查询]",Constant.YesOrNo.YES);



	/**
	 * 公共参数
	 */
	long lClientID				= sessionMng.m_lUserID;				//当前操作用户ID
	long lCurrencyID				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long lOfficeID				= sessionMng.m_lOfficeID;			//办事处ID	
	/**
	* 定义业务变量
	*/
%>
	<br>
<TABLE width="80%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		分析考核 - 预算项目单位情况查询</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
				<tr><td width="45%">&nbsp;预算项目：</td>
                  <td width="10%" >&nbsp;</td>
                  <td width="45%" >&nbsp;&nbsp;<font color=#CC0000>*</font>已选择：</td>
                </tr>
                  <tr>

<td width="45%" height="15" valign="top">
<%QueryBudgetInfo objInfo=(QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");%>
<input type=hidden name=budgetSystemID value="<%=objInfo.getBudgetSystemID()%>">
<input type=hidden name=clientID value="<%=objInfo.getClientID()%>">
<input type=hidden name=budgetPeriodID value="<%=objInfo.getBudgetPeriodID()%>">
<input type=hidden name=startDate value="<%=objInfo.getStartDate()%>">
<input type=hidden name=endDate value="<%=objInfo.getEndDate()%>">


					<select class='box' size="14" name="lineNameAssign" style="width:230"  multiple>
<%
	Collection coll=null;
	boolean checked=false;
	coll=(Collection)request.getAttribute("ItemColl");
	if(coll!=null){
		int i=0;
		for(Iterator iter =coll.iterator();iter.hasNext();){
			QBudgetResultInfo info=(QBudgetResultInfo)iter.next();
			i = i+1;
				long id = info.getItemID();
				String itemNo = info.getItemNo();
				long itemlevel =-1;
				String tmpValue = itemlevel + "$" + itemNo + "$" + id + "$" + i;
				out.println("<option value="+tmpValue+">"+info.getItemName()+"</option>");
		}
		out.println("</select>");
	}
	request.removeAttribute("ItemColl");
%>
</select></td>
                   <TD align="center" width="10%" class=""><input type=hidden name=SelPrivilege>
				<div align="center">
				<input name="buttonToRight" type="button" class="button" onclick="stot(frm001.lineNameAssign, frm001.lineNameNoAssign)" value="&nbsp;－>>&nbsp;">
				<br>
				<br>
				<input name="buttonToLeft" type="button" class="button" onclick="stot0(frm001.lineNameNoAssign, frm001.lineNameAssign)" value="&nbsp;<<－&nbsp;">
                </div>
		</TD>
		<TD align="center" width="45%" class="" colspan="2">
				<select name="lineNameNoAssign" size="14" style="width:230" multiple>
		         </select>&nbsp;
				</TD>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom><TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right><input name="Submit32" type="button" class="button" onClick="doSet()" value=" 提 交 ">
				   <input name="Submit32" type="button" class="button" onClick="location.href='../view/v101.jsp';" value=" 返 回"></DIV></td>
               </TR>
             </TBODY>
           </TABLE>
         </TD></TR></TBODY></TABLE></form>
<iframe name="myiframe" src="../view/v002.jsp" style="display:none"></iframe>
<!-- Javascript代码 -->
<script language="JavaScript">
//设定form名称
setFormName("form_m"); 
//设定回车自动执行动作
//setSubmitFunction("doSet()");
//业务操作
function doSet()
{
	//检查数据完整性
	if(document.all.lineNameNoAssign.options.length==0){
		alert("预算项目不能为空，请选择");
		return false;
	}
	
	showSending();//显示正在执行
	setHiddenValue();	
	document.frm001.strSuccessPageURL.value="../view/v103.jsp";	//定义操作成功后跳往的页面
	document.frm001.strFailPageURL.value="../view/v103.jsp";		//定义失败后跳往的页面
	document.frm001.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//定义操作代码
	frm001.submit();
		
}
function doClose(){
	window.close(); 
}
function stot0(source, target)
{
	var len = source.options.length;
	for (var i = (len-1); i >=0; i--) {
		if (source.options[i].selected) {
			var idValue = source.options[i].value;
			var tmpLevel = getTmpLevel(idValue);			//级别
			var privilegeNo = getPrivilegeNo(idValue);		//权限编号
			var idValue2 ;
			var tmpLevel2;
			var privilegeNo2;
			var selprivilege=privilegeNo;
			var tmpInt=0;
			//当选中的为父权限,要把下面所有的子权限选中再操作
			//if (tmpLevel == "1"){
		  	for (var m = i;m < len;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//权限编号
					if (privilegeNo2.indexOf(privilegeNo)==0){
						source.options[m].selected=true;
						tmpInt +=1;
					}
				}
			//当选中的为子权限,要把所有的父权限选中再操作
				var totalUpPrivilegeNo="";
					if(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
						 var x=0;
						while(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
							privilegeNo = getUpPrivilegeNo(privilegeNo);
							 if(x<1){
							totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
							 x++;}
							 else
							{
								 break;
							}
						}
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
					 
					}else{
						privilegeNo = getUpPrivilegeNo(privilegeNo);
						totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
					}
				if(!existRreast(selprivilege,source)){
				for (var m = 0;m <i;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//权限编号
					 
					if (totalUpPrivilegeNo.indexOf("$"+privilegeNo2+"$")>-1  ){
						source.options[m].selected=true;
					}
				}
				}
				
			}
		}
	 sourceToTarget(source, target);
}
function existRreast(str,obj)
{
	 
	var len=obj.options.length;
	for(i=0;i<len;i++)
	{
		var idValue2 = obj.options[i].value;
	    var privilegeNo2 = getPrivilegeNo(idValue2);	
		if(obj.options[i].selected==false && privilegeNo2.substr(0,privilegeNo2.length-1)==str.substr(0,str.length-1))
			return true;
	}
	return false;
}

function stot(source, target)
{
	var len = source.options.length;
	for (var i = 0; i < len; i++) {
		if (source.options[i].selected) {
			var idValue = source.options[i].value;
			var tmpLevel = getTmpLevel(idValue);			//级别
			var privilegeNo = getPrivilegeNo(idValue);		//权限编号
			
			var idValue2 ;
			var tmpLevel2;
			var privilegeNo2;
			var tmpInt=0;
			//当选中的为父权限,要把下面所有的子权限选中再操作
			//if (tmpLevel == "1"){
				for (var m = i;m < len;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//权限编号
					if (privilegeNo2.indexOf(privilegeNo)==0){
						source.options[m].selected=true;
						tmpInt +=1;
					}
				}
				
			//}else{//当选中的为子权限,要把所有的父权限选中再操作*/
				var totalUpPrivilegeNo="";
				//if(panduan=="0"){
					if(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
						while(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
							privilegeNo = getUpPrivilegeNo(privilegeNo);
							totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";

						}
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo+totalUpPrivilegeNo.substring(0,3) + "$";
					//alert(totalUpPrivilegeNo+"^^");
					}else{
						privilegeNo = getUpPrivilegeNo(privilegeNo);
						totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
						
							//alert(totalUpPrivilegeNo+"^^");
					}
					 
				//}else if(panduan=="1"){
				
				
				//}


				
				
				for (var m = 0;m < i;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//权限编号
					if (totalUpPrivilegeNo.indexOf("$"+privilegeNo2+"$")>-1){
						source.options[m].selected=true;
					}
				}
			}
		}
	//}
	sourceToTarget(source, target);
}

//取出是否是子权限，1是，0不是
function getTmpLevel(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}
//取出权限编号
function getPrivilegeNo(str)
{
	var tmpArray = str.split("$");
	return tmpArray[1];
}
//取出权限ID
function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[2];
}
//取出排序
function getOrder(str)
{
	var tmpArray = str.split("$");
	return tmpArray[3];
}
//取出上级权限编号
function getUpPrivilegeNo(str)
{
	if (str.lastIndexOf("_")==1){
		return str;
	}else{
		return str.substring(0,str.lastIndexOf("_"));
	}
}

//设置隐藏hidden的值
function setHiddenValue()
{
	frm001.SelPrivilege.value="";
	for(var i=0;i<frm001.lineNameNoAssign.options.length;i++)
	{
		var tmpValue = frm001.lineNameNoAssign.options[i].value;
		var tmpLevel = getTmpLevel(tmpValue);
		frm001.SelPrivilege.value = frm001.SelPrivilege.value + getId(tmpValue) + ",";
		
	}
	//alert(frm001.SelPrivilege.value+"id==")
}

//清除无用的父菜单
function clear(source)
{
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options.length > 0)
		{
			var idValue = source.options[i].value;		//级别
			var c=source.options.length-1;
				if (c==-1)
				{
					source.options[i] = null;
					if (i>0)
					i--;
					clear(source)
				}
			}
		}
}
//两个Select互相转移的主程序
function sourceToTarget(source, target) 
{
	var checkValue = false;
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options[i].selected) 
		{
			var len = target.options.length;
			var idValue = source.options[i].value;			
			//判断权限是否存在
			for (var m = 0; m < len; m++) 
			{
				if (target.options[m].value == idValue) 
				{
					source.options[i] = null;
					break;
				}
			}
			if (m==len)
			{

				var tmpNum = new Number(getOrder(idValue));
				for (l = 0; l < target.options.length; l++) 
				{
					var tmpStr = target.options[l].value;
					var tmpNum2 = new Number(getOrder(tmpStr));
					if (tmpNum2 - tmpNum > 0)
					{
						break;
					}
				}
				l -= 1;
				
				//把要填加权限的地方后面的项依次置后
				for (var n = len; n > l+1; n--) {
					target.options[n] = new Option("", "");
					target.options[n].text = target.options[n-1].text;
					target.options[n].value = target.options[n-1].value;
				}

				//填加选中的权限
				target.options[l+1] = new Option("", "");
				target.options[l+1].text = source.options[i].text;
				target.options[l+1].value = source.options[i].value;
				//删除原权限
				//每删除一权限都要将i-1，因为会重新计算长度
				source.options[i] = null;
					checkValue = true;
					i -= 1;
			}
		}
	}

	clear(source);
	for(var q=0;q < source.length;q++)
	{
		source.options[q].selected=false;
	}
	if (!checkValue) 
	{
	   alert("请选择!");
	   return;
	}
}
</script>
<%	
	/**
	* 现实文件尾
	*/
		OBHtml.showOBHomeEnd(out);
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>