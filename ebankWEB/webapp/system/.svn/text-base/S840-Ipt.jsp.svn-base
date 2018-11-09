<%--
/*
 * 程序名称：S840-Ipt.jsp
 * 功能说明：交易类型设置输入、输出页面
 * 作　　者：刘琰	
 * 完成日期：2003年8月27日
 */
--%>
<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="java.sql.*,
				 java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "账户交易类型设置";
%>

<%
	/* 初始化查询结果集 */
	Collection cltApf = null;
	Iterator cltIterator = null;
	Iterator cltIterator1 = null;
	long groupID = -1;
	AccountPrvgInfo accountPrvgInfo = null;
	long lAccountID = -1; // 银行账号

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        /* 用户登录检测 */
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

		/* 从请求中获取信息 */
		cltApf = (Collection)request.getAttribute("cltApf");  //查询结果集
		if (cltApf!= null)
		{
			cltIterator = cltApf.iterator();
			//cltIterator1 = cltApf.iterator();
		}
		if (request.getAttribute("lAccountID")!=null)
		{
			try
			{
				lAccountID = Long.parseLong((String)request.getAttribute("lAccountID")); // 银行账户ID
			}
			catch(Exception e){
				lAccountID = -1;
			}
		}	
		
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		long lIsCtrl = dao.getIsControlChild(sessionMng.m_strClientCode);
		
        /**
         * presentation start
         */
        /* 显示文件头 */
         OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>
<jsp:include page="/ShowMessage.jsp" />

<form method="post" name="frmjylxsz">
	  <table cellpadding="0" cellspacing="0" class="title_top">
 <tr>
	    <td height="24" >
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">账户交易类型设置</span></td>
			       <td class=title_right ><a class=img_title></td>
				</TR>
			</table>
			<br/>
 			<table width=100% border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td>
		        	<table border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="3"><a class=lab_title1></td>
						<td class="lab_title2">账户信息</td>
						<td width="17"><a class=lab_title3></td>
			          </tr>
		        	</table>
	        	</td>
		      </tr>
    		</table>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal>
				<tr>
				  <td colspan="5" height="1"></td>
				</tr>
		        <tr>
		          <td width="4" height="25"></td>
		          <td width="120" height="25">
		            <p><span class="graytext"><font color="red">*&nbsp;</font>账号：</span></p>
		          </td>
		          <td height="25" colspan="3">
		<% 			
					OBHtmlCom.showPayerBankCodeControl1(out, "lAccountID",lAccountID,"onchange=\"changeme();\" ",sessionMng.m_lClientID, sessionMng.m_lCurrencyID,sessionMng.m_lUserID,-1,sessionMng.m_lSAID); 
		%>
		          </td>
		        </tr>
		        <tr>
		          <td  height="1" colspan="5"></td>
		        </tr>
 		     </table>
			<br>
		 <table width=100% border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td><table border="0" cellspacing="0" cellpadding="0">
		          <tr>
	            	<td width="3"><a class=lab_title1></td>
					<td class="lab_title2">交易类型</td>
					<td width="17"><a class=lab_title3></td>
		          </tr>
		        </table></td>
		      </tr>
		</table>
		<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal  >
	        <tr>
	          <td  height="1" colspan="5"></td>
	        </tr>
	
<%
			/* 按格式显示所有记录 */
	   		while(cltIterator.hasNext())
	   		{
				accountPrvgInfo = (AccountPrvgInfo)cltIterator.next(); // 获取下一条记录信息
				if(accountPrvgInfo.getGroupID() != groupID)
					{
						groupID = accountPrvgInfo.getGroupID();
%>
			<tr>
			  <td width="4" height="25"></td>	  
	      	  <td width="120" height="25" class="graytext"> <%=accountPrvgInfo.getFormatGroup()/*显示所有的交易类型组名*/%>：</td>
	      	  <td>  	
<%						int i = 0;						cltIterator1 = cltApf.iterator();
						while(cltIterator1.hasNext())
							{
								i++;
								accountPrvgInfo = (AccountPrvgInfo)cltIterator1.next();
								if(accountPrvgInfo.getGroupID() == groupID) 
									{
										if ((lIsCtrl != 1 && accountPrvgInfo.getTypeID() !=OBConstant.SettInstrType.CHILDCAPTRANSFER && accountPrvgInfo.getTypeID() !=OBConstant.SettInstrType.YTLOANRECEIVE) || lIsCtrl ==1)
										{
										  if(accountPrvgInfo.getTypeID()!=OBConstant.SettInstrType.CHANGEFIXDEPOSIT && accountPrvgInfo.getTypeID()!=OBConstant.SettInstrType.CHILDCAPTRANSFER)
										  {
											//修改 by kenny(2007-05-05)(处理数组越界问题)[accountPrvgInfo.getFormatType().substring(5,9)]
											String formatType = accountPrvgInfo.getFormatType();
											if (accountPrvgInfo.getGroupID()==11) {
												if (formatType != null && formatType.length()>0) {
													if (formatType.indexOf("-")>0) {
														formatType = formatType.split("-")[1];
													} else if (formatType.indexOf("－")>0) {
														formatType = formatType.split("－")[1];
													}
												}
											}
%>
				  <input type="checkbox" name="chktype" value="<%=accountPrvgInfo.getTypeID()%>" <%= (accountPrvgInfo.getValue() == true) ? "checked" : ""%> ><%=formatType%><%if(i % 7 == 0){ %><br><%} %>
<%										}	
									}			
								}				
							}
%>
				</td>
			</tr>	
			<tr>
	          <td  height="1" colspan="7"></td>
	        </tr>		
<%					}
			}
%>
      	</table>

      	<br>
		<table width=100% border="0" cellspacing="0" cellpadding="0" align="center">
        	<tr>
          		<td width="605"><div align="right"></div></td>
          		<td align="right">
					<input type="button" name="Submitv00204" value=" 保 存 " class="button1" onClick="javascript:submitme();">&nbsp;&nbsp;
					<input type="button" name="Submitv00204" value=" 重 置 " class="button1" onClick="javascript:cancelme();">&nbsp;&nbsp; 
          		</td>
        	</tr>
      	</table>
	  	<br>
  	</td>
  </tr>
</table>
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * 数据校验及FORM提交
	 * javascript
	 */

	/*  查找处理函数 */
	function changeme()
	{
		frmjylxsz.action = "<%=strContext%>/system/S841-Ctr.jsp";

		if (frmjylxsz.lAccountID.value == "")
		{
			return false;
		}

        frmjylxsz.submit();

	}

    /* 提交处理函数 */
    function submitme()
    {
        frmjylxsz.action = "<%=strContext%>/system/S842-Ctr.jsp";
		if (validate() == true)
        {
            frmjylxsz.submit();
        }
    }

	/* 取消处理函数 */
    function cancelme()
    {
        frmjylxsz.action = "";
        frmjylxsz.submit();
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */

		/*  交易类型账号设置非空校验 */
		if (frmjylxsz.lAccountID.value == "")
		{
			alert("请选择账号");
			frmjylxsz.lAccountID.focus();
			return false;
		}

    	return true;
    }

</script>


<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frmjylxsz.lAccountID);
	setSubmitFunction("submitme(frmjylxsz)");
	setFormName("frmjylxsz");
</script>

<%
	} catch (Exception exp) {
		exp.printStackTrace();
		//JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	OBHtml.showOBHomeEnd(out);
	/**页面显示结束*/
%>

