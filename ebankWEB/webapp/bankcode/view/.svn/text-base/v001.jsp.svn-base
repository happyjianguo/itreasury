<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.*"%>
<%@ page import="com.iss.itreasury.fcinterface.bankportal.bankcode.BranchNOIdentify"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	try 
	{   
		//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
		String strTitle = "行名行号检索";
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
	    
		String strContext = request.getContextPath();
		String[] provinceStr = null;
		String bankName="";
		String recBankCode="";
		if (sessionMng.getQueryCondition("provinceCol") != null) 
		{
			provinceStr = (String[]) sessionMng.getQueryCondition("provinceCol");
		}
		ArrayList bankNames = null;
		if (request.getAttribute("bankNames") != null) 
		{
			bankNames = (ArrayList) request.getAttribute("bankNames");
		} 
		else 
		{
			bankNames = BranchNOIdentify.getBankNames();
		}
		if(request.getAttribute("recBankCode") != null){
			recBankCode = (String)request.getAttribute("recBankCode");
		}
		String strPageLoaderKey = (String) request.getAttribute("_pageLoaderKey");
		BankCodeInfo[] bankCodeInfo = (BankCodeInfo[]) request.getAttribute(Constant.PageControl.SearchResults);
		BankCodeParamInfo condition = new BankCodeParamInfo();
		condition = (BankCodeParamInfo) sessionMng.getQueryCondition("queryCondition");
		String standerBankName=null;
	  	String oldReceiveBranchName="";
		if(condition.getBankTypeName()!=null&&condition.getBankTypeName().length()>0)
		{
			standerBankName = BranchNOIdentify.getStanderBankNameByBankName(condition.getBankTypeName());
		}
		if(condition.getLbankName()!=null&&condition.getLbankName().length()>0)
		{
		    bankName = condition.getLbankName();
		}
		if(condition.getOldReceiveBranchName()!=null && condition.getOldReceiveBranchName().length()>0){
			oldReceiveBranchName = condition.getOldReceiveBranchName();
		}
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<input type="hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey%>">
<form name="frmV001" method="post" action="">
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="strAction" type="hidden" value="">
	<input name="strSuccessPageURL" type="hidden" value="/bankcode/view/v001.jsp">
	<input name="strFailPageURL" type="hidden" value="/bankcode/view/v001.jsp">
	<input name="bankName" type="hidden" value=<%=bankName %>>
	<input name="recBankCode" type="hidden" value=<%=recBankCode %>>
	<TABLE class=title_top width="97%" border="0">
				<TR>
					<TD class=title height=18>
						<B>行名行号检索</B>
					</TD>
				</TR>
				<TR >
					<TD height=100 width="100%">
						<br>
						<TABLE align=center border=0 class="normal" width="100%">
							<TR >
								<TD height=20 width="100%">
									<table border=0 width="100%">
										<TR >
											<TD height=20 width="40%" nowrap>原开户行：
												<input type="text" class="box" name="oldReceiveBranchName" value="<%=oldReceiveBranchName %>" readonly>
											</TD>
										</TR>
										<tr >
											<td  width=16% align="left" nowrap>省&nbsp;&nbsp;&nbsp;&nbsp;名：
												<select name="province" class="select" onchange="startRequest(this.value)">
													<option value="">
														所有省份
													</option>
													<%
													if (provinceStr != null && provinceStr.length > 0) 
													{
														for (int i = 0; i < provinceStr.length; i++) {
													%>
													<option value="<%=provinceStr[i]%>"
													<% if(condition.getProvinceName()!=null&&provinceStr[i].indexOf(condition.getProvinceName())>-1)
													{ %>
														selected 
														<%} %>>
														<%=provinceStr[i]%>
													</option>
													<%
														}
													}
													%>
												</select>
											</td>
											<td  width=25% align="left" nowrap>市&nbsp;&nbsp;&nbsp;&nbsp;名：
												<select id="city" name="city" class="select">
												<%if(condition.getCityName()!=null) {%>
												<option value = <%=condition.getCityName() %>><%=condition.getCityName() %></option>
												<%} else{%>
												<option>&nbsp;&nbsp;&nbsp;&nbsp;</option>
												<%} %>
												</select>

											</td>
										</tr>
										<tr >
											<td  width=24% align="left" nowrap>银行类型：
												<select name="bank" class="select">
													<option value="">
														全部银行
													</option>
													<%
													if (bankNames != null) 
													{
														for (int i = 0; i < bankNames.size(); i++) 
														{
													%>
													<option
														value="<%=BranchNOIdentify.getBankTypeCodeByBankName((String) bankNames.get(i))%>"
														<%
														if(standerBankName!=null&&standerBankName.equals(bankNames.get(i)))
														{ 
														%>
															selected <%
														} %>>
														<%=bankNames.get(i)%>
													</option>
													<%
														}
													}
													%>
												</select>

											</td>
										</tr>
										<tr>
											<td height=20 width=20% align="left" nowrap>
												关&nbsp;键&nbsp;字：
												<input type="text" name="keyWord" class="box" value=<%=condition.getBankName() == null ? "" : condition.getBankName()%>>
											</td>
										</tr>
									</table>
									<br>
								</TD>
							</TR>
							<TR >
								<TD align=right height=20 nowrap>
									<INPUT class="button1" name="bSearch" type="button" value=" 查 找 " onclick="doSearch()">
									&nbsp;&nbsp;
									<INPUT class="button1" name="close" type="button" value=" 关 闭 " onclick="javascript:window.close();">
								</TD>
							</TR>
					</TABLE>
				</TD>
			</TR>
			<TR >
				<TD height=60 vAlign=top width="100%">
					<br>
					<TABLE width="100%" id="flexlist"></TABLE>
				</TD>
			</TR>
	</TABLE>
</form>

<script language="javascript">
<%
if(condition.getProvinceName()!=null&&condition.getProvinceName().length()>0)
{
%>
	startRequest(frmV001.province.value);
<% 
}
%>

function doSearch1()
{
    if(frmV001.province.value.length <= 0)
	{
		alert("请输入省份！");
		return;
	}
	frmV001.strAction.value = "find";
	frmV001.action = "<%=strContext%>/bankcode/control/c001.jsp?strAction=find";
	frmV001.submit();
}

function link(bankCode,bankName,name)
{
	window.opener.document.getElementById("txtPayeeBankCNAPSNO").value=bankCode;
	window.opener.document.getElementById(name).value=bankName;
	window.close();
}

function startRequest(querycondition)
{

    createXmlHttpRequest();   
	xmlhttp.onreadystatechange = handleStateChange;
    xmlhttp.open("get","<%=strContext%>/bankcode/control/c002.jsp?query="+encodeURIComponent(encodeURIComponent(querycondition)),true);
    xmlhttp.send(null);
    delete xmlhttp;

}
var xmlhttp;
function createXmlHttpRequest()
{
      if (window.XMLHttpRequest) 
      {
		//Mozilla
		xmlhttp = new XMLHttpRequest();
		if(xmlhttp.overrideMimeType) 
		{
			xmlhttp.overrideMimeType('text/xml');
		}
	} else 
	{
		//IE
		try 
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");			
		} catch (e) 
		{
			try 
			{
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
				xmlhttp.setRequestHeader("Content-Type", "text/html; charset=GBK"); 
			
			} catch (oc) 
			{
			 	alert("ie exceed~!three"); 
			}
		}
	}
	if (!xmlhttp) 
	{
		return false;
	} else 
	{
		return xmlhttp;
	}
}

function handleStateChange()
{
     if(xmlhttp.readyState == 4)
     {
         if(xmlhttp.status == 200 )
         {
             var str = xmlhttp.responseText;     
             var strList  = new Array();
             strList = str.replace(/(\t\r\n)/gi,"").LTrim().split("::");
             var sel = document.getElementById("city");
             sel.innerHTML = "";
             for(var i=0;i<strList.length;i++)
             {             	            
	             var oOption = document.createElement("OPTION");
	             oOption.value = strList[i];
	             oOption.text = strList[i]; 	             
	          	 sel.add(oOption);	  
         	 }
         		sel.selectedIndex = i-1;  
        	 for(var j=0;j<strList.length;j++)
        	 {
      			//js 区分大小写
      			if(strList[j].indexOf("<%=condition.getCityName()%>")>-1)
      			{
      				sel.selectedIndex=j;
      			}
	    	}
         }
       	else if (xmlhttp.status == 404)
       	{
        	alert("Request URL does not exist");
        }
        else
        {
        	alert("Error: status code is " + xmlhttp.status);
        }
     }
}

String.prototype.LTrim  =  function()  
{  
	return  this.replace(/(^\s*)/g,  "");  
}  

$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
        	{display: '银行编号', name: 'clientcode', elType : 'link', elName : 'username', methodName : 'link("?","?","?")', width: 300, sortable: true, align: 'center'},
        	{display: '银行名称',  name : 'CPF_SREJECT', width : 300, sortable : true, align: 'center'}
		],//列参数
		classMethod : 'com.iss.itreasury.fcinterface.bankportal.bankcode.action.BankCodeAction.queryBankCode',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		printbutton : false,
		exportbutton : false
	});
	
});
function getFormData() 
{
	return $.addFormData("frmV001","flexlist");
}
function doSearch(){
	if(frmV001.province.value.length <= 0)
	{
		alert("请输入省份！");
		return;
	}
	$.gridReload("flexlist");
}
</script>
<%
} catch (Exception exp) 
{
	exp.printStackTrace();
%>
	<script type="text/javascript">
		window.close();
	</script>
<%
}
%>
<jsp:include page="/ShowMessage.jsp" />