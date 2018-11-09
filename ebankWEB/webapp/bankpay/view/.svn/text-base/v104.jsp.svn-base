<%--
/*
 * 程序名称：
 * 功能说明：业务复核－取消复核查询显示页面
 * 作　　者：baihuili
 * 日期：2006年09月14日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 com.iss.itreasury.bankportal.integration.constant.InstructionStatus,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->


<%
	//标题变量
	String strTitle = "[银行汇款]";
	String strdisable = "";
%>

<%	  
	/* 用户登录检测与权限校验 */
	try{ 
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
       
        double txtMinAmount=0.0;        //交易金额-最小值
        double txtMaxAmount=0.0;         //交易金额-最大值
        String txtExecuteA=null;        //执行日期从
        String txtExecuteB=null;        //执行日期到
         txtExecuteA = DataFormat.getDateString(Env.getSystemDateTime());//执行日期-从
        txtExecuteB = DataFormat.getDateString(Env.getSystemDateTime());//执行日期-从
         if(request.getParameter("txtMinAmount") != null && request.getParameter("txtMinAmount").trim().length() > 0) {
       txtMinAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMinAmount")));
       }
       if(request.getParameter("txtMaxAmount") != null && request.getParameter("txtMaxAmount").trim().length() > 0) {
       txtMaxAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMaxAmount")));
       }
       if(request.getParameter("txtExecuteA") != null && request.getParameter("txtExecuteA").trim().length() > 0) {
       txtExecuteA= request.getParameter("txtExecuteA");
       }
       if(request.getParameter("txtExecuteB") != null && request.getParameter("txtExecuteB").trim().length() > 0) {
       txtExecuteB= request.getParameter("txtExecuteB");
       }
      
           
        /* 显示文件头 */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
   		Collection coll = null;
		if(request.getAttribute("rcoll")!=null)
		{
			coll = (Collection)request.getAttribute("rcoll");
		}
		Iterator rs = null;
	 	if(coll != null)
		{
			rs = coll.iterator();
		}
		//Iterator rs = (Iterator)request.getAttribute("return");
		
		//查询信息对象
		QueryCapForm rsForm = new QueryCapForm();
		if(request.getAttribute("FormValue") != null)
		{
       		rsForm = (QueryCapForm)request.getAttribute("FormValue");
        }
		long lTransType = -1;
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
         
        
%>
<!--jsp:include page="/ShowMessage.jsp"/-->

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<!--引入js文件-->
<%--下是查询结果--%>
<form name="form1" method="post">

<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">取消复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
        </td>
      </tr>
    </table>
    <br/>
    <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal align="">
    	<tr><td></td></tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="1%" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;金额：</span></p>
          </td>
          <td width="1%" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal"></div>
          </td>
          <td  height="25"  class="MsoNormal">由：</td><td><%= sessionMng.m_strCurrencySymbol%>
	        <script  language="JavaScript">
				createAmountCtrl(
				'form1',
				'txtMinAmount',
				'<%=txtMinAmount==0.0?"":DataFormat.formatDisabledAmount(txtMinAmount)%>',
				'txtMaxAmount',
				'',
				<%=sessionMng.m_lCurrencyID%>);
			</script>
			</td><td>
	   至：</td><td>&nbsp;<%= sessionMng.m_strCurrencySymbol%>
	        <script  language="JavaScript">
				createAmountCtrl(
				'form1',
				'txtMaxAmount',
				'<%=DataFormat.formatDisabledAmount(txtMaxAmount)%>',
				'txtExecuteA',
				'',
				<%=sessionMng.m_lCurrencyID%>);
			</script>
            
          </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="5" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;提交日期：</span></p>
          </td>
          <td width="1%" height="25" class="MsoNormal" align="right">
            <div align="right" class="MsoNormal"></div>
          </td>
          <td  height="25">由：</td><td>
        	  <fs_c:calendar 
	   	       	    name="txtExecuteA"
		          	value="" 
		          	properties="nextfield ='txtExecuteB'" 
		          	size="20"/>
		        <script>
	          		$('#txtExecuteA').val('<%=txtExecuteA%>');
	          	</script>
	          	</td>
           	 <td>至：
          </td><td>
              <fs_c:calendar 
	         	    name="txtExecuteB"
		          	value="" 
		          	properties="nextfield ='find'" 
		          	size="20"/>
		       <script>
	          		$('#txtExecuteB').val('<%=txtExecuteB%>');
	          	</script>
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr >
          <td width=90% colspan="7"></td>
          <td width="60" align="right">
            <div align="right">
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="find" value=" 查 找 " class="button1" onClick="javascript:doQuery();" onkeydown="javascript:doQuery();">&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td></td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
      <br>
</form>
<form name="formcancel">
      <table width=80% border="1" cellspacing="0" cellpadding="0" class=normal> 
       <thead>
        <tr >
          <td width="25" height="18" rowspan="2"  nowrap>
            <p align="center"></p>
          </td>
          
          <td width="48" valign="middle" height="18" rowspan="2"  nowrap>
            <div align="center">指令序号</div>
          </td>
          
          <td width="48" valign="middle" height="18" rowspan="2"  nowrap>
            <div align="center">汇款用途</div>
          </td>
          
          <td  height="18" valign="middle" rowspan="2"  width="75" nowrap>
            <div align="center">账号</div>
          </td>
          
          <td  height="18" valign="middle" rowspan="2"  width="30" align="center" nowrap>借/贷</td>
          
          <td  height="18" valign="middle" rowspan="2"  width="72" align="center" nowrap>
            <div align="center">金额</div>
          </td>
          <td  height="18" valign="middle" rowspan="2" align="center"  width="48" nowrap>
            <div align="center">是否提交</div>
          </td>
          <td  height="18" valign="middle" rowspan="2" align="center" width="78" nowrap>
            <div align="center">状态</div>
          </td>
          
          <td  height="18" valign="middle" colspan="2" align="center" nowrap>
            <div align="center">对方资料</div>
          </td>
          
          
          <td  height="18" valign="middle" rowspan="2"  width="65" align="center" nowrap>
            <div align="center">执行日期</div>
          </td>
        </tr>
       
        <tr >
          <td  height="18" valign="middle"  width="80" align="center" nowrap>
            <div >名称</div>
          </td>
       
          <td height="18" valign="middle"  width="75" align="center" nowrap>
            <div >账号</div>
          </td>
        </tr>
      </thead> 
<%
      	int iCount = 0;//计数器
      	String strDataLast = "";//前一个指针
      	String strData = "";//当前指针
	  	//循环将数据显示出来
      	while ((rs != null) && rs.hasNext())
      	{
            OBBankPayInfo info=(OBBankPayInfo)rs.next();//资金管理信息对象
            iCount++;
            strData = info.getDtconfirm().toString();
  		  	//long billstatusid = info.getNDepositBillStatusId();
		  	//System.out.println("billstatusid的值是："+billstatusid);
            if (iCount == 1)
            {
                  strDataLast = strData;
%>
        <tr >
        <td width="25"> </td>
          <td width="48" align="left"  height="19" nowrap><div nowrap>提交日期：</div></td>   
          <td align="left" colspan="10" height="19" nowrap>
            <%= strData.toString().substring(0,10) %>
          </td>
          
        </tr>
<%
            }
            if (!strDataLast.equalsIgnoreCase(strData))
            {
%>
        <tr >
        <td width="25"> </td>
          <td width="48" align="left"   height="19" nowrap><div nowrap>提交日期：</div></td>         
          <td align="left" colspan="10" height="19" nowrap>
            <%= strData.toString().substring(0,10) %>
          </td>
        
        </tr>
<%
                  strDataLast = strData;
            }
        
                  	long pStatus=info.getBankPortalStatus();
                  	if(pStatus!=-1 && pStatus!=OBConstant.SettInstrStatus.DELETE  && pStatus!=OBConstant.SettInstrStatus.APPROVALING && pStatus !=1)
                  	{
                  		strdisable="disabled";
                  	}
                  	else
                  	{
                  		strdisable="";
                  	} 
                  	
        
%>
		
        <tr>
          <td width="25" valign="top" align="left" nowrap>
            <div align="center">
              <input type="checkbox" name="txtCheckbox" <%=strdisable%> value="<%= info.getId() %>">

            </div>
          </td>
          
          <td width="48" valign="top" align="left"  nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v106.jsp?id=<%= info.getId()%>&isable=<%=strdisable%>" target="_blank"><%= info.getId()%></a></div>
           
          
		  </td>
          
          <td width="48" valign="top" align="left" nowrap>
            <div align="center"><%=DataFormat.formatString(info.getSnote()) %></div>
          </td>
          
          <td  width="75"  valign="top" nowrap><%= com.iss.itreasury.ebank.util.NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
          
          <td  width="30"  valign="top" align="center" nowrap><%out.print("借"); %></td>
          
          <td  width="72"  valign="top"align="center"  nowrap>
            <div align="center"><%= sessionMng.m_strCurrencySymbol%><%=String.valueOf(info.getMamount()) %></div>
          </td>
          <td  width="48" valign="top" align="center" nowrap>
          	<div align="center"><%if(info.getNstatus()==1) out.print("是");
          	 	else out.print("否");
          	 %></div>
          </td>
          <td  width="78" valign="top" align="center" nowrap><%=OBConstant.SettInstrStatus.getName(info.getNstatus())%></td>
          <td  width="80" valign="top" align="center" nowrap><%=info.getSpayeeacctname()%></td>
          
          <td  width="75" valign="top" align="center" nowrap><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
         
          <td  width="65" valign="top" align="center" nowrap>
            <div align="center"><span nowrap><%= info.getDtexecute().toString().substring(0,10) %></span></div>
          </td>
        </tr>
<%
		}
		if (iCount == 0)//表示没记录，显示一空行
		{
%>
		
        <tr>
          <td width="25" height="14" valign="top"  >
          </td>
          
          <td width="48" valign="top" >
		  </td>
          
          <td width="48" valign="top" >
          </td>
          
          <td  width="75"  valign="top"></td>
          
          <td width="30"  valign="top">
      </td>
          
          <td  width="72"  valign="top">
          </td>
          <td  width="48"  valign="top">
          </td>
          
          <td width="78"  valign="top">
          </td>
          
          <td  width="80" valign="top"></td>
          
          <td  width="75" valign="top"></td>
        
          
          <td  width="65" valign="top">
            <div align="center"><span ></span></div>
          </td>
        </tr>

<%	
		}
%>
       
      </table>

      <br>
	<%  if (rs != null)
	    {
		%>
      <table width=80% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="400">
            <div align="right"></div>
          </td>
          <td width="160">
          </td>
          <td width="60">
            <div align="right">
			<input type="hidden" name="doact">
			<!--img src="" name="Check1"   border="0" style="cursor:hand" onClick="javascript:doCheck();"-->
			<input type="button" name="Check1" class="button1" value = " 取消复核 " onClick="javascript:doCheck();"></div>
		  </td>
        </tr>
      </table>
	  <%}%>
</form>

 <script language="javascript">
 function doCheckForm()
 {
       var fTop,fLov;

	   //add by sun start 2003-02-19
		
		/* 执行日期校验 */
		var startExe = form1.txtExecuteA.value;
		var endExe = form1.txtExecuteB.value;
		if (startExe != "")
		{
			if(chkdate(startExe) == 0)
			{
				alert("请输入正确的执行开始日期");
				form1.txtExecuteA.focus();
				return false;
			}
		}
		if (endExe != "")
		{
			if(chkdate(endExe) == 0)
			{
				alert("请输入正确的执行结束日期");
				form1.txtExecuteB.focus();
				return false;
			}
		}
		if ((startExe != "") && (endExe != ""))
		{	if (!CompareDate(form1.txtExecuteA, form1.txtExecuteB, "执行日期：起始日期不能大于结束日期"))
			{
				return false;
			}
		}
		//add by sun end 2003-02-19

       /*校验金额*/
       if (!checkAmount(form1.txtMinAmount,0,"金额 最小值"))
             return false;
       if (!checkAmount(form1.txtMaxAmount,0,"金额 最大值"))
             return false;

       fLov =  parseFloat(reverseFormatAmount1(form1.txtMinAmount.value));
       fTop = parseFloat(reverseFormatAmount1(form1.txtMaxAmount.value));
       if (fLov > fTop)
       {
             alert("金额 最小值不能大于最大值");
             return false;
       }
       return true;
 }
 function doCheck()/*复核---取消复核*/
 {
       var isCheck = false;
       for(i=0; i<document.formcancel.elements.length; i++)
       {
             if(document.formcancel.elements[i].type=="checkbox")
             {
                   if (document.formcancel.elements[i].checked == true)
                   {
                          isCheck = true;
                          break;
                   }
              }
       }
       if (!isCheck)
       {
             alert("请选择要操作的交易！");
             return false;
       }
	   var checkOrUncheck ;
	 
	   		checkOrUncheck = "取消复核？"
	   
	   if(confirm(checkOrUncheck))
	   {
	       formcancel.action = "../control/c103.jsp";
	       formcancel.doact.value="many";
	       showSending(); 
		   formcancel.submit();
	   }
 }

 function doQuery()
 {
       if (doCheckForm())
       {
             form1.action="../control/c102.jsp";
             showSending(); form1.submit();
       }
 }
 
 setFormName("form1");
 </script>


<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>