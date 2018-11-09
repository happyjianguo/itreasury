<%@page contentType="text/html;charset=gbk"%>
<%/*
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>

    <%@page import="java.util.*,
	com.iss.itreasury.loan.util.LOANConstant,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.util.*,
	com.iss.itreasury.ebank.obattach.bizlogic.*,
	com.iss.itreasury.ebank.obattach.dataentity.*,
	com.iss.itreasury.loan.attach.bizlogic.*,
	com.iss.itreasury.loan.attach.dataentity.*
	" %>
<%
		long ID=-1;     //合同或者贷款的申请ID
		long typeID=-1; //附件类型
		String buttonCaption="";
		boolean showOnly=false;
		String control="test";
		
		//取参数变量
		String strTemp = "";
		strTemp = request.getParameter("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ID = -1;
			}
		}
		strTemp = request.getParameter("lTypeID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				typeID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				typeID = -1;
			}
		}
		
		strTemp=request.getParameter("sCaption");
		if (strTemp != null && !strTemp.equals(""))
		{
			buttonCaption=DataFormat.toChinese(strTemp);
		}
		
		strTemp=request.getParameter("showOnly");
		if (strTemp != null && !strTemp.equals(""))
		{
			if (strTemp.equals("true") ) showOnly=true;
		}
		strTemp=request.getParameter("control");
		if (strTemp != null && !strTemp.equals(""))
		{
			control=strTemp;
		}
		
%>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width=100% >
<TR >
<TD colspan="2" >
        <%
		if(control.equals("view")){
            AttachOperation attachOperationView = new AttachOperation();
            Collection cview = null;
			cview = attachOperationView.query(typeID, ID);
            if (cview != null)
            {
		    	for(Iterator itview = cview.iterator();itview.hasNext();){
				 AttachInfo	infoview = (AttachInfo)itview.next();
               
		%>
          <A href="<%=Env.EBANK_URL%>DownLoadServlet?FileID=<%=infoview.getFileID()%>"   target="_blank">
		  <%=infoview.getShowName()%>
		  </A>
		  －<%=infoview.getRealName()%>
          <br>
        <%
            	}
            }
		
		}else{

            OBAttachOperation attachOperation = new OBAttachOperation();
            Collection c = null;
            OBAttachInfo info = new OBAttachInfo();	
            c = attachOperation.query(typeID, ID);
            if (c != null)
            {
		    	Iterator it = c.iterator();
				while (it.hasNext())
				{
				 	info = (OBAttachInfo)it.next();
              
		%>
          <A href="../DownLoadServlet?FileID=<%=info.getFileID()%>"   target="_blank">
		  <%=info.getShowName()%>
		  </A>
		  －<%=info.getRealName()%>
          <br>
        <%
            	}
            }
		}
        %>
</TD>
</TR>
<TR>
	<TD align="right" colspan="2" height=24 width="20%">
	<br>
	<%
	if(!control.equals("view"))
	{
	%>
		<INPUT  class=button name=btnLink <% if (showOnly){%>disabled<%}%> onclick="Javascript:window.open('a101-c.jsp?ParentID=<%=ID%>&ParentIDType=<%=typeID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=yes,resizable=yes,scrollbars =yes')" type="button" value="<%=buttonCaption%>">
	<%}%>
		</TD>
	</TR>
</table>
</BODY>
