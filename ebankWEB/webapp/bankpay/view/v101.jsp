<%--
/*
 * 程序名称：
 * 功能说明：业务复核匹配
 * 作　　者：baihuili
 * 日期：2006年09月15日
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[银行汇款]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strTemp="";
	String sPayerAccountNoZoom="";
	String sPayeeAcctNoZoom="";
	String npayeracctid="";
	String npayeeacctid="";
	String report="";
	String sChineseAmount="";
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	long lTransType = -1;
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
		System.out.println("***************");
        //用户登录检测 
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
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
		strTemp = (String)request.getParameter("sPayerAccountNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //付款方账号
		{				
			sPayerAccountNoZoom = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sPayeeAcctNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //收款方账号
		{				
			sPayeeAcctNoZoom = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeracctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //付款方ID
		{				
			npayeracctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeeacctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //收款方ID
		{				
			npayeeacctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("report");
		if (strTemp != null && strTemp.trim().length() > 0)				
		{				
			report = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sChineseAmount");
		if (strTemp != null && strTemp.trim().length() > 0)					 // 大写金额
		{				
			sChineseAmount = strTemp.trim();
		}
		System.out.print(sChineseAmount);
        OBBankPayInfo  info=new OBBankPayInfo();
        
        if(request.getAttribute("info") != null)
		{
			info = ( OBBankPayInfo)request.getAttribute("info");
		}
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>
<safety:resources />

<form method="post" name="frmzjhb">   
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  	    </td>
  </tr>
</table>
<br/>     
	<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
	<tr><td></td></tr>
    <tr  class="MsoNormal">
		  <td width="40" height="25" class="MsoNormal"></td>
		  <INPUT type="hidden" name="npayeracctid" value=<%=npayeracctid%> >
			<%OBMagnifier.createPayerBankAccount(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,
	sessionMng.m_lOfficeID,sessionMng.m_lClientID,
	"npayeracctid","dPayerCurrBalance","dPayerUsableBalance","name","frmzjhb",
	sPayerAccountNoZoom,
	"sPayerAccountNoZoom","<font color='#FF0000'>* </font>付款方账号"," nowrap width=\"130\" height=\"25\" class=\"MsoNormal\"",
	" width=\"430\" height=\"25\" ");
		%>
          <td width="129" height="25" class="MsoNormal"></td>
          
        </tr>
        
        <tr class="MsoNormal">
          <td  height="25" class="MsoNormal"></td>
          <td width="260" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;汇款方式：</span></p>
          </td>
          <td width="590" height="25" class="MsoNormal">
           &nbsp;&nbsp;
			银行汇款
			<INPUT type="hidden" name="ntranstype" value="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>">
		  </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
    	<tr id="payeeAcctNoZoom" class="MsoNormal">
          <td height="25"  class="MsoNormal"></td>
          <input type="hidden" name="npayeeacctid" value=<%=npayeeacctid%> >
          <input type="hidden" name="spayeeacctno">
          
		<%
		//收款方账号放大镜（汇）
		String[] sNextControlsEbank = {};
		OBMagnifier.createPayeeAccountNOCtrl2(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"npayeeacctid","spayeeacctname","spayeeprov","spayeecity","spayeebankname","txtPayeeBankCNAPSNO","bankconnectnumber","departmentnumber","frmzjhb",sPayeeAcctNoZoom,"sPayeeAcctNoZoom","<font color='#FF0000'>* </font>收款方账号"," nowrap  width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"",sNextControlsEbank,false);	
	
%>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25"  class="MsoNormal"></td>
		  <td height="25" width="220" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25"  class="MsoNormal">
		  	<input type="text" class="box" name="spayeeacctname" cols="65"  onfocus="nextfield ='spayeebankname';" rows="2" readonly><%=info.getSpayeeacctname() %> </textarea>
		  </td>
          
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td height="25"  class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="spayeebankname" value="<%=info.getSpayeebankname()==null?"":info.getSpayeebankname() %>" onfocus="nextfield ='bankconnectnumber';" size="30" readonly>
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td  height="25" class="MsoNormal"></td>
          <td width="220" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>金额：&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td height="25" width="590" class="MsoNormal">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","mamount","<%=info.getMamount()%>","dtexecute","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
			</script>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td  height="25" nowrap class="MsoNormal" >&nbsp;&nbsp;大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="box" name="sChineseAmount" size="30" value="<%=sChineseAmount %>" readonly>		
		  </td>		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td  height="25" class="MsoNormal"></td>
          <td width="220" height="25"class="MsoNormal"  align="left"><font color="#FF0000">* </font>提交日期：</td>
          <td height="25" class="MsoNormal">
	          <fs_c:calendar 
	          	    name="dtexecute"
		          	value="" 
		          	properties="nextfield ='domatch'" 
		          	size="12"/>
		    <script type="text/javascript">
		    	$('#txtExecuteA').val('<%=(info.getDtexecute() == null)?DataFormat.getStringDateTime().substring(0,10):DataFormat.getDateString(info.getDtexecute())%>');
		    	$('#dtexecute').attr('readonly','true');
		    </script>
			<!-- 
			<input class="box" type="text" name="dtexecute" readonly value="<%=(info.getDtexecute() == null)?DataFormat.getStringDateTime().substring(0,10):DataFormat.getDateString(info.getDtexecute())%>" onfocus="nextfield ='domatch';" size="12">
			<a class=calendar href="javascript:show_calendar('frmzjhb.dtexecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				
			</a>
			 -->
		  </td>
		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
        
        <tr>
          <td colspan="2">
            <div align="right"></div>
          </td>
          <td >
            
          </td>
		  
          <td width="62">
            <div align="right">
            <INPUT type="hidden" name="act" value="match">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name="domatch" type=button value=" 匹  配 " onClick="Javascript:match();" onfocus="nextfield='submitfunction'" > 
			</div>
          </td>
        </tr>
        <tr><td></td></tr>
      </table>
     </form>  
      
 <script language="javascript">
	$('#dtexecute').attr("readonly","true");
	/* 页面焦点及回车控制 */
	
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	frmzjhb.sPayerAccountNoZoomCtrl.focus;
	
	setFormName("frmzjhb");
	//setSubmitFunction("match(frmzjhb)");
	
	<%
		if(!sPayerAccountNoZoom.equals("")){
			%>
			frmzjhb.npayeracctid.value = <%=npayeracctid%>;
			<%
		}
	%>
	<%
		if(report.equals("report")){
			%>
		alert("没有匹配记录");
			<%
		}
	%>

   function match()
      {
      if (validate() == true)
        {
      	if (!confirm("是否匹配？"))
			{
				return false;
			}
       	frmzjhb.action="../control/c101.jsp";
       	
       	frmzjhb.submit();
      }
      
      }
      
      function validate()
    {
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}			
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.spayeeacctname.value == "")
			{
				alert("收款方名称不能为空");
				frmzjhb.spayeeacctname.focus();
				return false;
			}	
             
             if(Trim(frmzjhb.spayeebankname.value) == "")
			{
			   alert("汇入行名称不能为空");
			   frmzjhb.spayeebankname.focus();
			   return false;
			
			}
		if(!checkAmount(frmzjhb.mamount, 1, "交易金额"))
			{
				return false;
			}
		
		/* 执行日校验 */
		var tsExecute = frmzjhb.dtexecute.value;
		if(tsExecute=="")
		{
			alert("提交日期不能为空，请录入");
			frmzjhb.tsExecute.focus();
			return false;
		}
		if(chkdate(tsExecute) == 0)
		{
			alert("提交日期格式不正确，请重新录入");
			frmzjhb.tsExecute.focus();
			return false;
		}
    	return true;
    }
      </SCRIPT>
     
  <%
  OBHtml.showOBHomeEnd(out);
  }
  catch(IException ie)
  {
     OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
  }
  %>    
<%@ include file="/common/SignValidate.inc" %>