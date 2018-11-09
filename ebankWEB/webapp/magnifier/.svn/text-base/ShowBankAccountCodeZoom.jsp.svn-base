<%
/**
 * 程序名称：ShowBankAccountCodeZoom.jsp
 * 功能说明：放大镜的显示小窗口
 * 作　　者：Forest Ming
 * 完成日期：2003.08.01
*/
%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.*,
				com.iss.itreasury.dataentity.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.settlement.util.NameRef"
%>

<%
try
{
    RequestAlteredValidator.validate(request);
	//modify by leiyang 2009/02/10
	//加了Filter后解决放大镜websphere与jboss乱码出现不规律的情况
	//request.setCharacterEncoding("ISO8859_1");
	Log.print("***进入页面 ： /zoom/ShowBankAccountCodeZoom.jsp ***");
	
	//定义变量
	CommonSelectInfo info = null;
	Collection ctResult = null;
    Iterator it = null;

	//获取参数
	String strMagnifierName = request.getParameter("strMagnifierName");//放大镜的名称
	String strFormName = request.getParameter("strFormName");//主页面表单名称
	String strControl = request.getParameter("strControl");//控件名
	String[] strMainNames = request.getParameterValues("strMainNames");//主页面显示名称的文本框名称
	String[] strMainFields = request.getParameterValues("strMainFields");//结果集中表示名称的字段名
	String[] strReturnNames = request.getParameterValues("strReturnNames");//主页面保存ID的文本框名称
	String[] strReturnFields = request.getParameterValues("strReturnFields");//结果集中表示ID的字段名
	String[] strDisplayNames = request.getParameterValues("strDisplayNames");//要显示的列名称
	String[] strDisplayFields = request.getParameterValues("strDisplayFields");//要显示的列的字段名称
	String strSQL = request.getParameter("strSQL");//查询语句，用来从数据库中查询指定的结果集
	String[] strNextControls = request.getParameterValues("strNextControls");//返回主页面后，获得焦点的控件名
	int nIndex = Integer.parseInt(request.getParameter("nIndex"));//指定有下划线的栏位
	
	Log.print(strSQL);
	//转中文
	/*
	strMagnifierName = DataFormat.toChinese(strMagnifierName);
	strFormName = DataFormat.toChinese(strFormName);
	strControl = DataFormat.toChinese(strControl);
	strSQL = DataFormat.toChinese(strSQL);
	for (int i=0; i<strDisplayNames.length; i++)
	{
		strDisplayNames[i] = DataFormat.toChinese(strDisplayNames[i]);		
	}
	*/
%>
<script language="JavaScript">
window.focus();
</script>

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
%>
			try
			{//对于账户放大竟的特殊处理
				var strAccountNo,strAccountNo1,strAccountNo2,strAccountNo3,strAccountNo4;
				strAccountNo = sMainValues[0];
				strAccountNo1="";
				strAccountNo2="";
				strAccountNo3="";
				strAccountNo4="";
				if (strAccountNo!="")
				{
					var nNum,nStart;
					nStart=0;
					nNum=1;
					for (i = 0;  i < strAccountNo.length;  i++)
					{
						var ch;
						ch = strAccountNo.charAt(i);
						if ((ch=="-") )
						{
							eval("strAccountNo" +nNum +"=strAccountNo.substring(nStart,i)");
							nNum++;
							nStart=i+1;
						}
						if(nStart<strAccountNo.length)
						{
							eval("strAccountNo" + nNum + "=" + "strAccountNo.substring(nStart,strAccountNo.length)");
						}
					}
				}
				parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl1"%>.value = strAccountNo1;
				parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl2"%>.value = strAccountNo2;
				parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl3"%>.value = strAccountNo3;
				parent.opener.document.all.<%=strFormName%>.<%=strMainNames[0]+"Ctrl4"%>.value = strAccountNo4;
			}
			catch (e)
			{}			
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
		for(int i=strNextControls.length-1; i >= 0; i--)
		{
			if (strNextControls[i] != null && !strNextControls[i].equals(""))
			{
%>
				if ('<%=strNextControls[i]%>'=='submitfunction')
				{
					opener.executeSubmitFunction();
				}
				else
				{			
					var strTemp1 = "<%=strNextControls[i]%>";
					if((strTemp1.indexOf("(") >= 0 || strTemp1.indexOf("[") >= 0) && parent.opener.document.all.<%=strFormName%>.<%=strNextControls[i]%>.disabled == false)
					{
						parent.opener.document.all.<%=strFormName%>.<%=strNextControls[i]%>.focus(); //指定返回主页面后获得焦点的控件
					}
					else if (parent.opener.document.all.<%=strFormName%>.elements('<%=strNextControls[i]%>') != null && parent.opener.document.all.<%=strFormName%>.<%=strNextControls[i]%>.disabled == false)
					{
						parent.opener.document.all.<%=strFormName%>.<%=strNextControls[i]%>.focus(); //指定返回主页面后获得焦点的控件
					}
				}
<%
			}
		}%>
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
	ctResult = OBMagnifier.getOBAccountCommonSelectList(strMainFields, strReturnFields, strDisplayFields, strSQL);
	if(ctResult != null)
	{
		bOne = ctResult.size() == 1 ? true : false;
	}
%>
<table width="100%" border="0"   class="ItemList" cellspacing="1">
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
      	<td class="ItemBody" align="center"><A href="#" onClick='<%=sSelectFunction%>'><%=NameRef.getNoLineAccountNo(oDisplayCols[j].toString())%></A></td>
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
