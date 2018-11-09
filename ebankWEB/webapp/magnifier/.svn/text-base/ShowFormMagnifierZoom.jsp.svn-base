<%--
/**
 * 程序名称：ShowFormMagnifierZoom.jsp
 * 功能说明：放大镜的显示小窗口
 * 作　　者：Jeff yang
 * 完成日期：2009.02.19
*/
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@page import="com.iss.itreasury.util.RequestAlteredValidator"%>
<%@page import="com.iss.itreasury.util.IException"%>
<%@page import="com.iss.itreasury.util.Constant"%>

<%
try
{
	RequestAlteredValidator.validate(request);
	//定义变量
	String strTemp = "";
	CommonSelectInfo info = null;
	Collection ctResult = null;
    Iterator it = null;
    
  	//放大镜的名称
    String strMagnifierName = "";
  	//主页面表单名称
    String strFormName = "";
  	//控件名
    String strControl = "";
  	//查询语句，用来从数据库中查询指定的结果集
    String strSQL = "";
  	//返回主页面后，获得焦点的控件名
    String strNextControls = "";
  	//指定有下划线的栏位
    int nIndex = 0;

	//获取参数
	strTemp = request.getParameter("strMagnifierName");
	if(strTemp!=null && strTemp.length()>0){
		strMagnifierName = strTemp;
	}
	strTemp = request.getParameter("strFormName");
	if(strTemp!=null && strTemp.length()>0){
		strFormName = strTemp;
	}
	strTemp = request.getParameter("strControl");
	if(strTemp!=null && strTemp.length()>0){
		strControl = strTemp;
	}
	String[] strMainNames = request.getParameterValues("strMainNames");//主页面显示名称的文本框名称
	String[] strMainFields = request.getParameterValues("strMainFields");//结果集中表示名称的字段名
	String[] strReturnNames = request.getParameterValues("strReturnNames");//主页面保存ID的文本框名称
	String[] strReturnFields = request.getParameterValues("strReturnFields");//结果集中表示ID的字段名
	String[] strDisplayNames = request.getParameterValues("strDisplayNames");//要显示的列名称
	String[] strDisplayFields = request.getParameterValues("strDisplayFields");//要显示的列的字段名称
	strTemp = request.getParameter("strSQL");
	if(strTemp!=null && strTemp.length()>0){
		strSQL = java.net.URLDecoder.decode(strTemp);
	}
	strTemp = request.getParameter("strNextControls");
	if(strTemp!=null && strTemp.length()>0){
		strNextControls = strTemp;
	}
	strTemp = request.getParameter("nIndex");
	if(strTemp!=null && strTemp.length()>0){
		nIndex = Integer.parseInt(strTemp);
	}
	
	System.out.println("ShowFormMagnifierZoom.jsp   strSQL = \n" + strSQL);
	
	//Log.print(strSQL);
	//转中文
	//strMagnifierName = DataFormat.toChinese(strMagnifierName);
	//strFormName = DataFormat.toChinese(strFormName);
	//strControl = DataFormat.toChinese(strControl);
	//strSQL = DataFormat.toChinese(strSQL);
	//strSQL = java.net.URLDecoder.decode(strSQL);
	//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&strSQL="+strSQL);
	//for (int i=0; i<strDisplayNames.length; i++)
	//{
	//	strDisplayNames[i] = DataFormat.toChinese(strDisplayNames[i]);		
	//}
	
	//Log.print(strSQL);
	/*
	//读取数据
	boolean bOne = true; //是否只有一个记录
	//获取记录集
	ctResult = OBMagnifier.getCommonSelectList(strMainFields, strReturnFields, strDisplayFields, strSQL);
	if(ctResult != null)
	{
		bOne = ctResult.size() == 1 ? true : false;
	}
	*/
%>
<script language="JavaScript">
window.focus();
</script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<html>
<head>
<title><%=strMagnifierName%>放大镜</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<script language=javascript>
//返回选择结果
function selectProject(sMainValues, sArrReturnValue)
{
	try
	{
<%
		//返回hidden数据
		if(strReturnNames != null)
		{
			for(int i=0; i<strReturnNames.length; i++)
			{
%>
			try
			{
				parent.opener.document.all.<%=strFormName%>.<%=strReturnNames[i]%>.value = sArrReturnValue[<%=i%>];
			}
			catch (e)
			{}
<%
			}
		}
%>

<%
		//返回名称
		for(int i=0; i<strMainNames.length; i++)
		{
			//修改 by kenny (胡志强) (2007-03-22) 处理账户放大镜分段的问题
			if (i==0) {
%>
			try
			{//对于账户放大竟的特殊处理
				var strAccountNo;
				strAccountNo = sMainValues[0];

				if (strAccountNo!="")
				{
					var nNum,nStart
					nStart=0;
					nNum=1;

					for (j=0; j<strAccountNo.length; j++)
					{
						var ch;
						ch = strAccountNo.charAt(j);
						if ((ch=="-"))
						{
							eval("parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl"%>"+nNum+".value = strAccountNo.substring(nStart,j)");
							nNum++;
							nStart=j+1;
						}
						if(j==strAccountNo.length-1)
						{
							eval("parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl"%>"+nNum+".value = strAccountNo.substring(nStart,strAccountNo.length)");
						}
					}
				}
			}
			catch (e)
			{}
<%
			}
%>
			//一般处理			
			try
			{
				parent.opener.document.all.<%=strFormName%>.<%=strMainNames[i]%>.value = sMainValues[<%=i%>];
			}
			catch (e)
			{}
			try
			{
				if(sMainValues[<%=i%>]==1)
				{
					parent.opener.document.all.<%=strFormName%>.<%=strMainNames[i]+"[0]"%>.checked = true;
					parent.opener.document.all.<%=strFormName%>.<%=strMainNames[i]+"[1]"%>.checked = false;
				}
				else
				{
					parent.opener.document.all.<%=strFormName%>.<%=strMainNames[i]+"[0]"%>.checked = false;
					parent.opener.document.all.<%=strFormName%>.<%=strMainNames[i]+"[1]"%>.checked = true;
				}
			}
			catch (e)
			{}
<%
		}
%>

<%
		if (strNextControls != null && !strNextControls.equals(""))
		{
%>
			if (parent.opener.document.all.<%=strFormName%>.<%=strNextControls%>.disabled == false)
			{
				parent.opener.document.all.<%=strFormName%>.<%=strNextControls%>.focus(); //指定返回主页面后获得焦点的控件
			}
<%
		}
%>
	}
	catch(e)
	{}
	finally
	{
		self.close();	//关闭当前窗口
	}
}
</script>

<script language="JavaScript">
function keyDown(DnEvents) 
{ 
	k =  window.event.keyCode;
	if (k == 27)
	{  
		parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]%>.focus();
		self.close();	
	}
}
document.onkeydown = keyDown;
</script>	

<!--引用数据校验方法集-->
</head>
<body bgcolor="#FFFFFF" leftmargin="5" topmargin="5" marginwidth="5" marginheight="5">
<%
	//读取数据
	boolean bOne = true; //是否只有一个记录
	//获取记录集
	ctResult = OBMagnifier.getCommonSelectList(strMainFields, strReturnFields, strDisplayFields, strSQL);
	if(ctResult != null)
	{
		bOne = ctResult.size() == 1 ? true : false;
	}
%>
<table width="100%" border="0" name="table" class="ItemList" cellspacing="1">
<%
	if(ctResult == null)
	{
%>
				<tr bordercolor="#999999">
					<td width="460" class="ItemBody" align="center">无匹配记录</td>
				</tr>
<script language="JavaScript">
function keyDown(DnEvents) 
{ 
	k =  window.event.keyCode;
	if (k == 13)
	{  
		parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]%>.focus();
		self.close();
	}
	else if (k == 27)
	{
		parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]%>.focus();
		self.close();	
	}
}
document.onkeydown = keyDown;
</script>		
<%
		return;
	}
%>
    <tr bordercolor="#999999">
      <td width="3%" height="26" class="ItemTitle">&nbsp;</td>
<%
	for(int i=0; i<strDisplayNames.length; i++)
	{
%>
		<td class="ItemTitle">
			<div align="center"><b><%=strDisplayNames[i]%></b></div>
		</td>
<%	}%>
    </tr>
<%
	if (ctResult!=null)
	{
		//记录集不为空，分页显示所有记录
		it = ctResult.iterator();

		Object[] oMainCols = null;
		Object[] oReturnCols = null;
		Object[] oDisplayCols = null;

	    for(int i=1; it.hasNext(); i++)
		{
		    info = (CommonSelectInfo) it.next();

		    oMainCols = info.getMainCols();
		    oReturnCols = info.getReturnCols();
		    oDisplayCols = info.getDisplayCols();

			//生成当前行记录的选定函数
			String sSelectFunction = "selectProject(new Array(";
			for(int j=0; j<strMainNames.length; j++)
			{
				if(j>0) sSelectFunction += ",";
				sSelectFunction += "\""+oMainCols[j]+"\"";
			}
			sSelectFunction += "),new Array(";

			if(strReturnNames != null)
			{
				for(int j=0; j<strReturnNames.length; j++)
				{
					if(j>0) sSelectFunction += ",";
					sSelectFunction += "\""+oReturnCols[j]+"\"";
				}
			}
			sSelectFunction += "));";

			//如果只有一条记录,直接选中此记录,并退出窗口
			if(bOne == true)
			{

%>
		<script>
			<%=sSelectFunction%>
		</script>
<%
			}//if(bOne == true)

			//显示列表
%>
    <tr bordercolor="#999999">
	  <td class="ItemBody"><input type="radio" name="rdo" id="rdo<%=i%>" onkeydown='if(event.keyCode==13) <%=sSelectFunction%>'></td>
<%
			for(int j=0; j<strDisplayNames.length; j++)
			{
				if(j == nIndex || nIndex < 0)
				{
%>
      	<td class="ItemBody"><A href="#" onClick='<%=sSelectFunction%>'><div align="center"><%=NameRef.getNoLineAccountNo(oDisplayCols[j].toString())%></div></A></td>
<%
				}else{
%>
		<td class="ItemBody"><div align="left"><%=oDisplayCols[j]%></div></td>
<%
				}
			}
%>
    </tr>
<%
		}
	}
%>
</table>
</body>
</html>

<script language=javascript>
//初始化
function window.onload()
{
	try
	{
	    rdo1.focus();
	    rdo1.checked = true;
	}
	catch(e)
	{}
}
</script>
<%
}
catch(IException e1)
{
 	OBHtml.showExceptionMessage(out, null, e1, "放大镜", "", Constant.RecordStatus.INVALID);
	e1.printStackTrace();
}
catch(Exception e)
{
	System.out.println(e.getMessage());
	e.printStackTrace();
}
%>
