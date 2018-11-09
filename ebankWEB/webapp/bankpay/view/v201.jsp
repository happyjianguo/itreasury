<%--
/*
 * 程序名称：
 * 功能说明：业务审核查询显示页面
 * 作　　者：baihuili
 * 日期：2006年09月15日
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
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.bankportal.integration.constant.InstructionStatus"
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
		if(request.getAttribute("info") != null)
		{
       		rsForm = (QueryCapForm)request.getAttribute("info");
       		
       		
        }
       
		

        
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<%--下是查询结果--%>
<form name="form1" method="get">

	<table width="810" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="1" class=FormTitle >银行汇款--业务审核</td>
        </tr>
      </table>
      <table width="810" border="0" cellspacing="0" cellpadding="0" class = top>
        
      
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;金额：</span></p>
          </td>
          <td width="60" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">最小值<%= sessionMng.m_strCurrencySymbol%></div>
          </td>
          <td width="440" height="25" class="MsoNormal">
	        <script  language="JavaScript">
				createAmountCtrl('form1','txtMinAmount','<%=rsForm.getMinAmount()%>','txtMaxAmount','',1);
				form1.txtMinAmount.focus();
				if(form1.txtMinAmount.value==0.00)
				{
					form1.txtMinAmount.value="";
				}
			</script>
        <span class="MsoNormal"> 最大值<%= sessionMng.m_strCurrencySymbol%></span>
        <script  language="JavaScript">
				createAmountCtrl('form1','txtMaxAmount','<%=rsForm.getMaxAmount()%>','txtExecuteA','',1);
				if(form1.txtMaxAmount.value==0.00)
				{
					form1.txtMaxAmount.value="";
				}
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
            <p><span class="MsoNormal">&nbsp;&nbsp;执行日期：</span></p>
          </td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">由</div>
          </td>
          <td width="430" height="25" class="MsoNormal">
          <fs_c:calendar 
						          	    name="txtExecuteA"
							          	value="" 
							          	properties="nextfield ='txtExecuteB'" 
							          	size="12"/>
			  	  <script>
	          		$('#txtExecuteA').val('<%=rsForm .getStartExe() %>');
	          	</script>
            <span class="graytext">至
            </span>
            <fs_c:calendar 
          	    name="txtExecuteB"
	          	value="" 
	          	properties="nextfield =''" 
	          	size="12"/>
			<script>
	          		$('#txtExecuteB').val('<%=rsForm.getEndExe() %>');
	        </script>
		  </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;状态：</span></p>
          </td>
          <td colspan="2" align="left">
          	<select class='box' name="lStatus">
          		<option value="<%=OBConstant.OBBankPayStatus.CHECK%>" selected>已复核
          		<option value="<%=OBConstant.OBBankPayStatus.AUDITING%>">已审核
          	</select>
          	<script language="javascript">
          		if(<%=rsForm.getStatus()%>!=-1)
             	form1.lStatus.value="<%=rsForm.getStatus()%>";
            </script>
          </td>
          <td width="3" height="25"  class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="810" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
            <div align="right"></div>
          </td>
          <td width="60">
            <div align="right">
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Submitv00204" value=" 查  找 " class="button" onClick="javascript:doQuery();">
			</div>
          </td>
        </tr>
      </table>
	  <br>
</form>
<form name="formcancel">
      <table width="810" border="0" class="ItemList">
        <tr class="tableHeader">
          <td width="25" bgcolor="#456795" height="18" rowspan="2" class="ItemTitle">
            <p align="center"></p>
          </td>
          
          <td width="48" bgcolor="#456795" valign="middle" height="18" rowspan="2" class="ItemTitle">
            <p align="center"><font size="2" class="ItemTitle"> 指令序号</font></p>
          </td>
          
          <td width="48" bgcolor="#456795" valign="middle" height="18" rowspan="2" class="ItemTitle">
            <p align="center"><font size="2" class="ItemTitle"> 汇款用途</font></p>
          </td>
          
          <td bgcolor="#456795" height="28" nowrap valign="middle" rowspan="2" class="ItemTitle" width="75">
            <div align="center">账号</div>
          </td>
          
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="30">借/贷</td>
          
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="72">
            <div align="center">金额</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="30">
            <div align="center">是否提交</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="40">
            <div align="center">银行指令状态</div>
          </td>
          
          <td bgcolor="#456795" height="0" valign="middle" colspan="2" class="ItemTitle">
            <div align="center">对方资料</div>
          </td>
          
          
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">执行日期</div>
          </td>
        </tr>
       
        <tr class="tableHeader">
          <td bgcolor="#456795" height="18" valign="middle" class="ItemTitle" width="132">
            <div align="center">名称</div>
          </td>
       
          <td bgcolor="#456795" height="18" valign="middle" class="ItemTitle" width="75">
            <div align="center">账号</div>
          </td>
        </tr>

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
        <tr bgcolor="#FDF5DF" valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="19" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>提交日期<span class="graytext">：<%= strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
            }
            if (!strDataLast.equalsIgnoreCase(strData))
            {
%>
        <tr  valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="19" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>提交日期<span class="graytext">：<%= strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
                  strDataLast = strData;
            }
        
                  	long pStatus=info.getBankPortalStatus();
                  	if(pStatus!=-1 && pStatus!=0 && pStatus!=10)
                  	{
                  		strdisable="disabled";
                  	}
                  	else
                  	{
                  		strdisable="";
                  	} 
                  	
        
%>
		
        <tr>
          <td width="25" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
            <div align="center">
              <input type="checkbox" name="txtCheckbox" <%=strdisable%> value="<%= info.getId() %>">

            </div>
            
          </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody" nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v203.jsp?id=<%= info.getId()%>&strdisable=<%=strdisable%>" target="_blank"><%= info.getId()%></a></div>
           
          
		  </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
            <div align="center"><%=DataFormat.formatString(info.getSnote()) %></div>
          </td>
          
          <td bgcolor="#C8D7EC" width="75" class="ItemBody" valign="top" nowrap><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
          
          <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top"><%out.print("借"); %></td>
          
          <td bgcolor="#C8D7EC" width="72" class="ItemBody" valign="top">
            <div align="center"><%= sessionMng.m_strCurrencySymbol%><%= DataFormat.formatEAmount(info.getMamount()) %></div>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="30" valign="top">
          	<%if(info.getNiscanaccept()==1) out.print("是");
          	 	else out.print("");
          	 %>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="40" valign="top"><%=InstructionStatus.getName(info.getBankPortalStatus())%></td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="132" valign="top" nowrap><%=info.getSpayeeacctname()%></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="75" valign="top"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= info.getDtexecute().toString().substring(0,10) %></span></div>
          </td>
        </tr>
<%
		}
		if (iCount == 0)//表示没记录，显示一空行
		{
%>
		
        <tr>
          <td width="25" height="14" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
          </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
		  </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
          </td>
          
          <td bgcolor="#C8D7EC" width="75" class="ItemBody" valign="top"></td>
          
          <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top">
      </td>
          
          <td bgcolor="#C8D7EC" width="72" class="ItemBody" valign="top">
          </td>
          <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top">
          </td>
          
          <td bgcolor="#C8D7EC" width="40" class="ItemBody" valign="top">
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="132" valign="top"></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="75" valign="top"></td>
        
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
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
      <table width="810" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
          </td>
          <td width="60">
            <div align="right">
			
			<INPUT type="hidden" name="doact">
			<!--img src="" name="Check1"   border="0" style="cursor:hand" onClick="javascript:doCheck();"-->
			<%
				long doact=Long.parseLong((String)request.getAttribute("doact"));
				if(doact==OBConstant.OBBankPayStatus.CHECK)
				{
			%>
			
			<input type="button" name="Check1" class="button" value = " 审  核 " onClick="javascript:doSign();">
			<%
				}
				if(doact==OBConstant.OBBankPayStatus.AUDITING)
				{
			%>
			<input type="button" name="Check1" class="button" value = " 取消审核 " onClick="javascript:doCancelSign();">
			<%}%>
			</div>
		  	
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
 function doSign()/**/
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
	 
	   		checkOrUncheck = "开始审核？"
	   
	   if(confirm(checkOrUncheck))
	   {
	       formcancel.action = "../control/c103.jsp";
	       formcancel.doact.value="manyauditing";
	       formcancel.target="_blank";
	       //showSending(); 
		   formcancel.submit();
	   }
 }
  function doCancelSign()/**/
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
	 
	   		checkOrUncheck = "取消审核？"
	   
	   if(confirm(checkOrUncheck))
	   {
	       formcancel.action = "../control/c103.jsp";
	       formcancel.doact.value="cancelmanyauditing";
	       //showSending(); 
		   formcancel.submit();
	   }
 }

 function doQuery()
 {
       if (doCheckForm())
       {
             form1.action="../control/c201.jsp";
             showSending(); form1.submit();
       }
 }
 firstFocus(form1.txtMinAmount);
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