<%--
/*
 * 程序名称：
 * 功能说明：业务签认查询显示页面
 * 作　　者：baihuili
 * 日期：2006年09月15日
 */
--%>
<%-- 注释此页面于:2008/03/12  注释人：leiyang3 --%>

<%-------------------------------------------------------------------------------------
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.budget.util.BUDGETNameRef,
                 com.iss.itreasury.bankportal.integration.constant.InstructionStatus"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
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



<form name="form1" method="get">

	<table width="810" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="1" class=FormTitle >银行汇款--业务查询</td>
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
				//alert(form1.txtMinAmount.value);
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
            <input type="text" name="txtExecuteA" size="12" value="<%= rsForm .getStartExe() %>" onfocus="nextfield ='txtExecuteB';">
            <a href="javascript:show_calendar('form1.txtExecuteA');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
			<img src="/webob/graphics/calendar.gif"  width="15" height="18" border=0></a>
             <span class="graytext">至
            </span>
            <input type="text" name="txtExecuteB" size="12" value="<%= rsForm .getEndExe() %>" onfocus="nextfield ='';" >
            <a href="javascript:show_calendar('form1.txtExecuteB');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
			<img src="/webob/graphics/calendar.gif"  width="15" height="18" border=0></a>
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;状态：</span></p>
          </td>
          <td colspan="2" align="left">
          	<select name="lStatus">
          		<option value="-1" selected>全部          		
          		<option value="<%=OBConstant.OBBankPayStatus.SAVE%>" >未复核</option>
          		<option value="<%=OBConstant.OBBankPayStatus.CHECK%>">已复核</option>
          		<option value="<%=OBConstant.OBBankPayStatus.AUDITING%>">已审核</option>
          		<option value="<%=OBConstant.OBBankPayStatus.SIGN%>">已签认</option>
          	</select>
          	<script language="javascript">
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
            <input type="hidden" name="doact" >
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
            <div align="center">状态</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="40">
            <div align="center">银行指令状态</div>
          </td>
          
          <td bgcolor="#456795" height="0" valign="middle" colspan="2" class="ItemTitle">
            <div align="center">对方资料</div>
          </td>
          
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">预算体系</div>
          </td>
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">预算名称</div>
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
      	
      	Vector vctCapSummarize = new Vector(); //存放OBCapSummarizeInfo对象的集合
   	    OBCapSummarizeInfo obCSI=null; //存放交易总结信息
   	    Timestamp tsConfirmDate = null; //确认时间
	    long lTotalCount = 0;   //共有笔数
	    long lDeleteCount = 0;  //已删除笔数
	    long lUnCheckCount = 0; //未复核笔数
	    long lCheckCount = 0;   //已复核笔数
	    long lSignCount = 0;    //已签认笔数
	    double dTotalAmount = 0;//总交易金额
	    double dLoanAmount = 0; //其中贷金额
	    double dDebitAmount = 0;//其中借金额
    
	  	//循环将数据显示出来
      	while ((rs != null) && rs.hasNext())
      	{
            OBBankPayInfo info=(OBBankPayInfo)rs.next();//资金管理信息对象
            iCount++;
            strData = info.getDtconfirm().toString();
  		  	//long billstatusid = info.getNDepositBillStatusId();
		  	//System.out.println("billstatusid的值是："+billstatusid);
            if (iCount == 1 && strData!=null)
            {
                  strDataLast = strData;
%>
        <tr bgcolor="#FDF5DF" valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="22" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>提交日期<span class="graytext">：<%= strData==null?"":strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
            	
            	
            	
            }
            if ( strDataLast!=null && !strDataLast.equalsIgnoreCase(strData) )
            {
%>
        <tr  valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="22" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>提交日期<span class="graytext">：<%= strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
						//记录上一个时间段的信息
                 
        				obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);   //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
                        obCSI.setDebitAmount(dDebitAmount);     //其中借金额
                        lTotalCount = 0;    //共有笔数
                        lDeleteCount = 0;   //已删除笔数
                        lUnCheckCount = 0;  //未复核笔数
                        lCheckCount = 0;    //已复核笔数
                        lSignCount = 0;     //已签认笔数
                        dTotalAmount = 0;   //总交易金额
                        dLoanAmount = 0;    //其中贷金额
                        dDebitAmount = 0;   //其中借金额
                        vctCapSummarize.addElement(obCSI);
                 
                 
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
            &nbsp;
            
          </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody" nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v302.jsp?id=<%= info.getId()%>" target="_blank"><%= info.getId()%></a></div>
           
          
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
          <td bgcolor="#C8D7EC" class="ItemBody" width="40" valign="top">
          	<%=OBConstant.OBBankPayStatus.getName(info.getNstatus())%>
          	
          	 
          	 <%
          	 	//根据每条记录的状态增加条数
          	 	switch ((int) info.getNstatus()) {
                    case (int) OBConstant.OBBankPayStatus.DELETE:
                        lDeleteCount++;//已删除
                    break;
                    case (int) OBConstant.OBBankPayStatus.SAVE:
                        lUnCheckCount++;//未复核笔数
                    break;
                    case (int) OBConstant.OBBankPayStatus.CHECK:
                        lCheckCount++;//已复核笔数
                    break;
                    case (int) OBConstant.OBBankPayStatus.SIGN:
                        lSignCount++;//已签认笔数
                    break;
                    default :
                    break;
                }
                if(info.getNstatus() != OBConstant.OBBankPayStatus.DELETE){
	                if (info.getNtranstype() == -1000) {
	                    dLoanAmount += info.getMamount(); //其中贷金额
	                } else {
	                    dDebitAmount += info.getMamount();//其中借金额
	                }
	                dTotalAmount += info.getMamount();//按日期分类计算出的的总交易金额
	                lTotalCount++;//共有笔数
                }
                tsConfirmDate = info.getDtconfirm();
                
                if (rs != null && !rs.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
                        obCSI.setDebitAmount(dDebitAmount);     //其中借金额
                        vctCapSummarize.addElement(obCSI);
                }
            
          	 %>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="40" valign="top"><%=InstructionStatus.getName(info.getBankPortalStatus())%></td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="132" valign="top" nowrap><%=info.getSpayeeacctname()==null?"":info.getSpayeeacctname()%></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="75" valign="top"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= BUDGETNameRef.getSystemNoByID(info.getBudgetSystemID()) %></span></div>
          </td>	
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= BUDGETNameRef.getItemNameByID(info.getBudgetItemID()) %></span></div>
          </td>	
         		
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
          <td bgcolor="#C8D7EC" width="40" class="ItemBody" valign="top">
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
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
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
	    
	     session.setAttribute("vctCap", vctCapSummarize);
		 System.out.println("******************************"+vctCapSummarize.size()+"---------------");
		
		%>
    <br>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">查询时间：<%=DataFormat.getDateString().substring(0,10)%></span></div>
        </td>
      </tr>
    </table>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
      <td width="750"> 
        <div align="right">
		<!--img src="\webob\graphics\button_jiaoyizongjie.gif" width="83" height="18" border="0" onclick="javascript:summarize();"-->
		<input type="Button" class="button" value="交易总结" width="46" height="18"   onclick="javascript:summarize()">
		</div>
        </td>
        
        
      <td width="58"> 
        <div align="right">
		<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="javascript:printMe();"-->
		<input type="Button" class="button" value="打  印" width="46" height="18"   onclick="javascript:printMe()">
		
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
 
 function doQuery()
 {
       if (doCheckForm())
       {
             form1.doact.value="";
             form1.target = "";
             form1.action="../control/c301.jsp";
             showSending(); form1.submit();
       }
 }
  /* 交易总结处理函数 */
    function  summarize() {
   	    form1.doact.value="";
        form1.target = "";
        form1.action = "../view/v305.jsp";
        showSending();
        form1.submit();
        
    }
    //打印处理函数
    function printMe() {
        form1.action = "../control/c301.jsp";
        form1.doact.value="allprint";
        form1.target = "NewWindow_S";
        form1.submit();
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
-------------------------------------------------------------------------------------%>