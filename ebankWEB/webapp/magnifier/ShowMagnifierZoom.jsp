<%
/**
 * �������ƣ�ShowMagnifierZoom.jsp
 * ����˵�����Ŵ󾵵���ʾС����
 * �������ߣ�Forest Ming
 * ������ڣ�2003.08.01
*/
%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.*,
				com.iss.itreasury.dataentity.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.settlement.util.NameRef"%>
<%
try
{
    RequestAlteredValidator.validate(request);
	//modify by leiyang 2009/02/10
	//����Filter�����Ŵ�websphere��jboss������ֲ����ɵ����
	//request.setCharacterEncoding("ISO8859_1");
	//Log.print("***����ҳ�� �� cpfsec/common/ShowMagnifierZoom.jsp ***");
	
	//�������
	CommonSelectInfo info = null;
	Collection ctResult = null;
    Iterator it = null;

	//��ȡ����
	String strMagnifierName = request.getParameter("strMagnifierName");//�Ŵ󾵵�����
	String strFormName = request.getParameter("strFormName");//��ҳ�������
	String strControl = request.getParameter("strControl");//�ؼ���
	String[] strMainNames = request.getParameterValues("strMainNames");//��ҳ����ʾ���Ƶ��ı�������
	String[] strMainFields = request.getParameterValues("strMainFields");//������б�ʾ���Ƶ��ֶ���
	String[] strReturnNames = request.getParameterValues("strReturnNames");//��ҳ�汣��ID���ı�������
	String[] strReturnFields = request.getParameterValues("strReturnFields");//������б�ʾID���ֶ���
	String[] strDisplayNames = request.getParameterValues("strDisplayNames");//Ҫ��ʾ��������
	String[] strDisplayFields = request.getParameterValues("strDisplayFields");//Ҫ��ʾ���е��ֶ�����
	String strSQL = request.getParameter("strSQL");//��ѯ��䣬���������ݿ��в�ѯָ���Ľ����
	String strNextControls = request.getParameter("strNextControls");//������ҳ��󣬻�ý���Ŀؼ���
	System.out.println("********************strNextControls="+strNextControls);
	System.out.println("********************strSQL="+strSQL);
	int nIndex = Integer.parseInt(request.getParameter("nIndex"));//ָ�����»��ߵ���λ
	
	//Log.print(strSQL);
	//ת����
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
	Log.print(strSQL);
	/*
	//��ȡ����
	boolean bOne = true; //�Ƿ�ֻ��һ����¼
	//��ȡ��¼��
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
<title><%=strMagnifierName%>�Ŵ�</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<script language=javascript>
//����ѡ����
function selectProject(sMainValues, sArrReturnValue)
{
	try
	{
<%
		//����hidden����
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
		//��������
		for(int i=0; i<strMainNames.length; i++)
		{
			//�޸� by kenny (��־ǿ) (2007-03-22) �����˻��Ŵ󾵷ֶε�����
			if (i==0) {
%>
			try
			{//�����˻��Ŵ󾹵����⴦��
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
			//һ�㴦��			
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
				parent.opener.document.all.<%=strFormName%>.<%=strNextControls%>.focus(); //ָ��������ҳ����ý���Ŀؼ�
			}
<%
		}
%>
	}
	catch(e)
	{}
	finally
	{
		self.close();	//�رյ�ǰ����
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

<!--��������У�鷽����-->
</head>
<body bgcolor="#FFFFFF" leftmargin="5" topmargin="5" marginwidth="5" marginheight="5">
<%
	//��ȡ����
	boolean bOne = true; //�Ƿ�ֻ��һ����¼
	//��ȡ��¼��
	ctResult = OBMagnifier.getCommonSelectList(strMainFields, strReturnFields, strDisplayFields, strSQL);
	if(ctResult != null)
	{
		bOne = ctResult.size() == 1 ? true : false;
	}
%>
<table width="100%" border="0" name="table" class="ItemList" cellspacing="1" style="word-break:break-all">
<%
	if(ctResult == null)
	{
%>
				<tr bordercolor="#999999">
					<td width="460" class="ItemBody" align="center">��ƥ���¼</td>
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
    <tr bordercolor="#999999" >
      <td width="3%" height="26" class="ItemTitle" nowrap>&nbsp;</td>
<%
	for(int i=0; i<strDisplayNames.length; i++)
	{
%>
		<td class="ItemTitle">
			<div align="center" nowrap><b><%=strDisplayNames[i]%></b></div>
		</td>
<%	}%>
    </tr>
<%
	if (ctResult!=null)
	{
		//��¼����Ϊ�գ���ҳ��ʾ���м�¼
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

			//���ɵ�ǰ�м�¼��ѡ������
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

			//���ֻ��һ����¼,ֱ��ѡ�д˼�¼,���˳�����
			if(bOne == true)
			{

%>
		<script>
			<%=sSelectFunction%>
		</script>
<%
			}//if(bOne == true)

			//��ʾ�б�
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
//��ʼ��
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
 	OBHtml.showExceptionMessage(out, null, e1, "�Ŵ�", "", Constant.RecordStatus.INVALID);
	e1.printStackTrace();
}
catch(Exception e)
{
	System.out.println(e.getMessage());
	e.printStackTrace();
}
%>
