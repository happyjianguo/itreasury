<%--
/*
 * 程序名称：S844-Ctr.jsp
 * 功能说明：文件下载
 * 完成日期：2011年06月24日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.system.uploadmodel.bizlogic.*,
			     com.iss.itreasury.system.uploadmodel.dataentity.*,
			     com.iss.itreasury.util.AutoFileBean,
			     com.iss.itreasury.dataentity.AutoFileInfo,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%
      //固定变量
      String strTitle = null;
%>
<%
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
        DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />
<form name="form1" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="txtIsCPF" value="true">
<table  cellpadding="0" cellspacing="0" class="title_top">
	      <TR>
			<td height="22">
			    <table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
				       <td class=title><span class="txt_til2">文件下载</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
     	        <br/>
	  
      <table width=100% border="1" cellpadding="0" cellspacing="0" class=normal id="table1">
        <thead>
    <tr>
	  <td width="5%">序号</td>
      <td width="25%">文件标题</td>
      <td width="35%">文件内容</td>
      <td width="25%">发布时间</td>
      <td width="10%">文件下载</td>
      
    </tr>
      </thead>
      <%
		UpModelBiz upModelBiz =new UpModelBiz();
		UpModelInfo upModelInfo = new UpModelInfo();
		upModelInfo.setClients(String.valueOf(sessionMng.m_lClientID));
		upModelInfo.setOfficeId(sessionMng.m_lOfficeID);
		upModelInfo.setStatusID(1);
	    Collection downLoadCollects = upModelBiz.findByUpModel(upModelInfo);
	    Iterator iters = null;
	    int i =0;
		if (downLoadCollects != null)
		{
            iters = downLoadCollects.iterator();
		
        while(iters.hasNext()){
       UpModelInfo downModelInfo = new UpModelInfo();
       downModelInfo =(UpModelInfo)iters.next();
       i=i+1;
     %> 
   <tr bgcolor="FFFFFF">
   <td align="center">
   <%=i %>
   </td>
   <td  align="center">
   <%=downModelInfo.getHeader() %></td>
     <%if(downModelInfo.getContent()==null) {%>
    <td align="center">
    </td>
    <% }else{%>
    <td align="center">
    <%=downModelInfo.getContent() %>
    </td>
    <%} %>
    <td align="center">
    <%=DataFormat.getDateString(downModelInfo.getReleaseDate()) %>
    </td>
   <td align="center">
    <A href="<%=Env.EBANK_URL%>DownLoadServlet?FileID=<%=en.getEncString(String.valueOf(downModelInfo.getFileId()))%>" target="_blank">下载</A>
    </td>
   </tr>
 <%}
 }else{%>
 <tr>
 <td align="center" colspan="5">
 </td>
 </tr>
 <%}%>
	</table>
	</td>
	</TR>
	</table>
</form>


 <script language="JavaScript">
 
 </script>
<%
	//OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		 return;
	}
	
%>

<%@ include file="/common/SignValidate.inc" %>
