<%--
/*
 * 程序名称：
 * 功能说明：业务查询打印
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
       	//OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
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
		
		eBankPrint.showPrintReport(out,sessionMng,"A4",2,true);
       
		

        
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>




<form name="formcancel">
		
		<table width="920" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px">银行汇款业务详单</font></b></td>
      </tr>
    </table>
      <table width="900" border="0" class="table1">
        <tr class="tableHeader">
          <td width="25"  height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"></p>
          </td>
          
          <td width="58"  valign="middle" height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"><font size="2" class="td-rightbottom" nowrap> 指令序号</font></p>
          </td>
          
          <td width="48"  valign="middle" height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"><font size="2" class="td-rightbottom" nowrap> 汇款用途</font></p>
          </td>
          
          <td  height="48" nowrap valign="middle" rowspan="2" class="td-rightbottom" nowrap width="75">
            <div align="center">账号</div>
          </td>
          
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="30">借/贷</td>
          
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="72">
            <div align="center">金额</div>
          </td>
          <td  height="38" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="30">
            <div align="center">是否提交</div>
          </td>
          <td  height="38" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="40">
            <div align="center">状态</div>
          </td>
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="40">
            <div align="center">银行指令状态</div>
          </td>
          
          <td  height="0" valign="middle" colspan="2" class="td-rightbottom" nowrap>
            <div align="center">对方资料</div>
          </td>
          
          
          <td  height="18" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="51">
            <div align="center">执行日期</div>
          </td>
        </tr>
       
        <tr class="tableHeader">
          <td  height="18" valign="middle" class="td-rightbottom" nowrap width="132">
            <div align="center">名称</div>
          </td>
       
          <td  height="18" valign="middle" class="td-rightbottom" nowrap width="85">
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
        <tr  valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="20" align="left"  class="ItemBody" height="20">
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
          
          <td colspan="20" align="left"  class="ItemBody" height="20">
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
          <td width="25" valign="top" align="left"  class="ItemBody">
            &nbsp;
            
          </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody" nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v302.jsp?id=<%= info.getId()%>" target="_blank"><%= info.getId()%></a></div>
           
          
		  </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
            <div align="center"><%=DataFormat.formatString(info.getSnote()) %></div>
          </td>
          
          <td  width="75" class="ItemBody" valign="top" nowrap><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
          
          <td  width="30" class="ItemBody" valign="top"><%out.print("借"); %></td>
          
          <td  width="72" class="ItemBody" valign="top">
            <div align="center"><%= sessionMng.m_strCurrencySymbol%><%= DataFormat.formatEAmount(info.getMamount()) %></div>
          </td>
          <td  class="ItemBody" width="30" valign="top">
          	<%if(info.getNiscanaccept()==1) out.print("是");
          	 	else out.print("");
          	 %>
          </td>
          <td  class="ItemBody" width="40" valign="top">
          	<%if(info.getNstatus()==OBConstant.OBBankPayStatus.SAVE) out.print("未复核");
          	 	if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) out.print("已复核");
          	 	if(info.getNstatus()==OBConstant.OBBankPayStatus.SIGN) out.print("已签认");
          	 %>
          	 
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
                if (info.getNtranstype() == -1000) {
                    dLoanAmount += info.getMamount(); //其中贷金额
                } else {
                    dDebitAmount += info.getMamount();//其中借金额
                }
                dTotalAmount += info.getMamount();//按日期分类计算出的的总交易金额
                lTotalCount++;//共有笔数
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
          <td  class="ItemBody" width="40" valign="top"><%=InstructionStatus.getName(info.getBankPortalStatus())%></td>
          <td  class="ItemBody" width="132" valign="top" nowrap><%=info.getSpayeeacctname()%></td>
          
          <td  class="ItemBody" width="75" valign="top"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
         
          <td  class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= info.getDtexecute().toString().substring(0,10) %></span></div>
          </td>
        </tr>
<%
		 }
		if (iCount == 0)//表示没记录，显示一空行
		{
%>
		
        <tr>
          <td width="25" height="14" valign="top" align="left"  class="ItemBody">
          </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
		  </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
          </td>
          
          <td  width="75" class="ItemBody" valign="top"></td>
          
          <td  width="30" class="ItemBody" valign="top">
      </td>
          
          <td  width="72" class="ItemBody" valign="top">
          </td>
          <td  width="40" class="ItemBody" valign="top">
          </td>
           <td  width="30" class="ItemBody" valign="top">
          </td>
          
          <td  width="40" class="ItemBody" valign="top">
          </td>
          
          <td  class="ItemBody" width="132" valign="top"></td>
          
          <td  class="ItemBody" width="75" valign="top"></td>
        
          
          <td  class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
        </tr>

<%	
		}
%>
       
      </table>

      <br>
	
    <br>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">查询时间：<%=DataFormat.getDateString().substring(0,10)%></span></div>
        </td>
      </tr>
    </table>
   
</form>



<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
   
%>
-------------------------------------------------------------------------------------%>