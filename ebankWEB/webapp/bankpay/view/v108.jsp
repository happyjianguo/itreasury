<%--
/*
 * 程序名称：v108.jsp
 * 功能说明：银行申请指令查询页面
 * 作　　者：niweinan
 * 完成日期：2010-10-20
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<safety:resources />

<%
String strContext = request.getContextPath();
strContext+="1";
%>
<%
    //标题变量
    String strTitle = null;
    try {
        /* 用户登录检测 */
       if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        long lClientID = sessionMng.m_lClientID;    //客户ID
        long lTransType = -1;           //交易类型
        long lStatus = -1;              //状态
        long lLoanContractID = -1;      //合同ID
        long nEbankStatus = -1;         //银行指令状态
        String sStartSubmit = "";       //提交日期-从
        String sEndSubmit = "";         //提交日期-到
        double dMinAmount = 0.0;        //交易金额-最小值
        double dMaxAmount = 0.0;        //交易金额-最大值
        
        //modify by xwhe 2008-11-24
        sStartSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-从
        sEndSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-到
        String sContractNo = "";        //合同号
        String strFormCtrl = "";        //FORM控制
        String sTemp = null;            //临时量
        String cancelNote = "";
        sTemp = (String) request.getAttribute("fromAccountType");
        if (sTemp != null && sTemp.trim().length() > 0) {
            strFormCtrl = sTemp;
            Log.print("FORM控制=" + strFormCtrl);
        }
       
        //从请求中获取查询结果信息
        Collection lstQuery = (Collection) request.getAttribute("cltQcf");
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
		long lChild = GetNumParam(request,"child");
        QueryCapForm queryCapForm = (QueryCapForm) request.getAttribute("queryCapForm");
        if (queryCapForm != null) {
            
            sStartSubmit = queryCapForm.getStartSubmit();   //提交日期-从
            Log.print("提交日期-从=" + sStartSubmit);
            sEndSubmit = queryCapForm.getEndSubmit();       //提交日期-到
            Log.print("提交日期-到=" + sEndSubmit);
            lStatus = queryCapForm.getStatus();             //交易指令状态
            Log.print("交易指令状态=" + lStatus);
            dMinAmount = queryCapForm.getMinAmount();       //交易金额-最小值
            Log.print("交易金额-最小值=" + dMinAmount);
            dMaxAmount = queryCapForm.getMaxAmount();       //交易金额-最大值
            Log.print("交易金额-最大值=" + dMaxAmount);
            nEbankStatus = queryCapForm.getNEbankStatus();  //银行指令状态
            Log.print("银行指令状态=" + nEbankStatus);
            
        }
        //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        // 系统时间
  
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<form name="frmjysqcx" method="post" action="">
<input type="hidden" name="fromAccountType" value="">
<input type="hidden" name="lClientID" value="<%=lClientID%>">
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">

<input type="hidden" name="sNextSuccessPage" value="">

<table cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">申请指令查询</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
         <tr>
          <td colspan="6" height="5"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext" colspan="2">交易类型：</td>
          <td height="25" class="graytext" colspan="3">
<%
		
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		OBConstant.SettInstrType.bankpay_showList(out,"lTransType",1,lTransType,true,false," onchange=\"jump();\"  onfocus=\"nextfield ='lStatus';\" ",null,false);
		 
		
            //业务
            
%>
          </td>
          
         
          <td height="25" class="graytext"></td>
        </tr>
        <tr  id="contractCLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="commonStatus">
          <td width="5" height="25"></td>
          <td height="25"  class="graytext" colspan="2">状态：</td>
          <td height="25"  class="graytext" colspan="3">
<%
        //状态
        OBHtmlCom.showQueryEbankStatusListControl(
            out,
            "lStatus",
            lStatus,
            " onfocus=\"nextfield ='nEbankStatus';\" "
        );
%>
          </td>
          <td width="8" height="25"></td>
        </tr>
         <tr  id="commonStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="nEbankStatus">
           <td width="5" height="25"></td>
          <td height="25"  class="graytext" colspan="2">银行指令状态：</td>
          <td height="25"  class="graytext" colspan="3">
<%
        //状态
        OBConstant.BankInstructionStatus.showList(out, "nEbankStatus", 1, nEbankStatus, true, " onfocus=\"nextfield ='dMinAmount';\" ");

%>
          </td>
          <td width="8" height="25"></td>
        </tr>
        <tr  id="nEbankStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">金额：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">由<%= sessionMng.m_strCurrencySymbol /*金额最小值*/%></div>
          </td>
          <td width="188" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMinAmount",
                    '<%=dMinAmount==0.0?"":DataFormat.formatDisabledAmount(dMinAmount)%>',
                    "dMaxAmount",
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
            </td>
            <td width="98" height="25" class="graytext" colspan="1" align="right">
            <span class="graytext">至<%= sessionMng.m_strCurrencySymbol /*金额最大值*/%></span>
            </td>
            <td width="330" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMaxAmount",
                    '<%=dMaxAmount==0.0?"":DataFormat.formatDisabledAmount(dMaxAmount)%>',
                    "sStartSubmit",
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
         <tr  id="submitDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext" >提交日期：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext">
          <fs_c:calendar 
          	    name="sStartSubmit"
	          	value="" 
	          	properties="nextfield ='sEndSubmit'" 
	          	size="18"/>
	         	  <script>
	          		$('#sStartSubmit').val('<%=sStartSubmit%>');
	          	</script>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
            </td>
          <td width="330" height="25" class="graytext">
             <fs_c:calendar 
	          	    name="sEndSubmit"
		          	value="" 
			        properties="nextfield =''" 
		          	size="18"/>
		    <script>
	          		$('#sEndSubmit').val('<%=sEndSubmit%>');
	          	</script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="submitDateLine">
          <td colspan="6" height="1"></td>
        </tr>
      <tr>
          <td  colspan="6">
            <div align="right">
			<!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" onclick="javascript:queryme();"-->
			<input type="Button" class="button1" value=" 查 找 " width="46" height="18"   onclick="javascript:queryme()">
				&nbsp;&nbsp;</div>
          </td>
        </tr>
        <tr>
          <td colspan="6" height="5"></td>
        </tr>
      </table>
    <br>
  <%
  
if (strFormCtrl != null && strFormCtrl.equals("yes")) {
    String sPreConfirmDate = "";    //前一个确认日期
    String sConfirmDate = "";       //确认日期
    String sPrePayerAcctNo = "";    //前一个活期存款
    String sPayerAcctNo = "";       //活期存款

    Timestamp tsConfirmDate = null; //确认时间
    long lTotalCount = 0;   //共有笔数
    long lDeleteCount = 0;  //已删除笔数
    long lUnCheckCount = 0; //未复核笔数
    long lApprovalingCount = 0; //审批中笔数
	long lApprovaledCount = 0; //审批完成笔数
    long lCheckCount = 0;   //已复核笔数
    long lSignCount = 0;    //已签认笔数
    long lOnGoingCount = 0; //处理中笔数
    long lFinishedCount = 0;//已完成笔数
    long lRefusedCount = 0; //已拒绝笔数
    double dTotalAmount = 0;//总交易金额
    double dLoanAmount = 0; //其中贷金额
    double dDebitAmount = 0;//其中借金额

    Vector vctCapSummarize = new Vector(); //存放OBCapSummarizeInfo对象的集合
    OBCapSummarizeInfo obCSI=null; //存放交易总结信息
 
%>
  <table width=100% border="1"  cellpadding="0" cellspacing="0" class=normal >
    <thead>
      <tr> 
        <td width="60" align="center" height="18" rowspan="2"   nowrap> <div>指令序号</div></td>
       
        <td align="center"     height="18" rowspan="2"   nowrap> 
          <div>交易类型</div></td>
       
        <td width="25" align="center"     height="18" rowspan="2"   nowrap> 
          <div>借/贷</div></td>
        <td width="85" align="center"       rowspan="2" nowrap> 
          <div>金额</div></td>
        <td width="135" align="center"       colspan="2" nowrap> 
          <div>对方资料</div></td>
        <td width="35" align="center"     height="18"   rowspan="2" nowrap> 
          <div>状态</div></td>
        <td width="85" align="center"     height="18"   rowspan="2" nowrap> 
          <div>银行指令状态</div></td>
        <td width="75" align="center"     height="18"   rowspan="2" nowrap> 
        <!--modify by xwhe 2008-11-10 将合同号替换为汇款用途  
          <div>合同号</div></td>-->
         <div>汇款用途</div></td> 
      
        <td width="75" align="center"     height="18"   rowspan="2" nowrap> 
          <div>备注</div></td>
       
      </tr>
      <tr> 
        
        <td width="60" align="right"       nowrap> <div>名称</div></td>
       
        <td width="80" align="center"       nowrap> <div>账号</div></td>
      </tr>
    </thead>
    <%
        if (listQuery != null) {
            //根据排序规则，按格式显示所有记录
            while(listQuery != null && listQuery.hasNext()) {
                OBBankPayInfo info = (OBBankPayInfo)listQuery.next(); // 获取下一条记录信息
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getDtconfirm());
                cancelNote = info.getCancelNote()== null?"":info.getCancelNote().trim();	//add by zhouxiang
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
                    if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
                        obCSI.setDebitAmount(dDebitAmount);     //其中借金额
                        lTotalCount = 0;    //共有笔数
                        lDeleteCount = 0;   //已删除笔数
                        lUnCheckCount = 0;  //未复核笔数
                        lApprovalingCount = 0; //审批中笔数
						lApprovaledCount = 0; //审批完成笔数
                        lCheckCount = 0;    //已复核笔数
                        lSignCount = 0;     //已签认笔数
                        lOnGoingCount = 0;  //处理中笔数
                        lFinishedCount = 0; //已完成笔数
                        lRefusedCount = 0;  //已拒绝笔数
                        dTotalAmount = 0;   //总交易金额
                        dLoanAmount = 0;    //其中贷金额
                        dDebitAmount = 0;   //其中借金额
                        vctCapSummarize.addElement(obCSI);
                    }
%>
    <tr> 
      <td align="left"       height="19">提交日期：</td>
      <td align="left" colspan="3"       height="19"><%=sConfirmDate%></td>
  
      <td colspan="6"       height="19"><%="&nbsp;"%></td>
     
    </tr>
    <%
                }
                sPrePayerAcctNo = sPayerAcctNo;
               
                sPayerAcctNo = info.getS_accountno();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
%>
    <tr>
 <%-- 
      <%
            if (lTransType == -1) {
        %>
      <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
      <td colspan="1"   height="19" bgcolor="#DFDFDF">账号：</td>
      <td colspan="3" bgcolor="#DFDFDF"   height="19"><%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":NameRef.getNoLineAccountNo(sPayerAcctNo)%></td>
      <td colspan="5" bgcolor="#DFDFDF"   height="19"><%="&nbsp;"%></td>
      <%
            } else {
        %>
        
        
 --%>
 
      <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
      <td colspan="1"   height="19" bgcolor="#DFDFDF" nowrap>账号：</td>
      <td colspan="7" bgcolor="#DFDFDF"   height="19"> <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":NameRef.getNoLineAccountNo(sPayerAcctNo)%></td>
	  <td colspan="1" bgcolor="#DFDFDF"  height="19" ><%="&nbsp;"%></td>
      
<%--
      <%
            }
        %>
--%>

    </tr>
    <%
                }
%>
    <tr> 
      <td width="65" align="center"    nowrap> <u style="cursor:hand" onclick="javascript:form3.txtID.value = this.name; 
                form3.txtTransType.value = this.id;doSee();" 
            id="23" name="<%=info.getId()%>"><%=info.getId()%></u> 
        <input type="text" name="txtID2" size="20" value="<%=info.getId()%>" style="display:none" class="box"> 
        <input type="text" name="txtTransType2" size="20" value="23" class="box" style="display:none"> 
      </td>
      
            
      <td  align="center"  height="18"   nowrap> <%=OBConstant.SettInstrType.getName(23)%></td>
            	      
            	
     
   		<td width="25" align="center"  height="18"   nowrap> 
        <%
            
                out.print("借");
           
%>
      </td>
      <td width="85" align="right"    nowrap> <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%> 
      </td>
    
      <td  width="100" align="center"    nowrap> <%=info.getSpayeeacctname()==null || info.getSpayeeacctname()==""?"&nbsp;":info.getSpayeeacctname()%></td>
     
      <td  width="100" align="center"    nowrap> <%=info.getSpayeeacctno()==null || info.getSpayeeacctno()==""?"&nbsp;":NameRef.getNoLineAccountNo(info.getSpayeeacctno())%></td>
      <td  width="45" align="center"    nowrap> 
        
        <%=OBConstant.SettInstrStatus.getName(info.getNstatus())%> 
        
      </td>
      <!-- 交易编号  暂设置成空 -->
      <td width="85" align="center"    nowrap><%=OBConstant.BankInstructionStatus.getName(info.getNEbankStatus())%> </td>
       
       <td  width="75" align="center"    nowrap> <%=info.getSnote()==null || info.getSnote()==""?"&nbsp;":info.getSnote()%></td>
     
     <%
		if(cancelNote.length()<=6){
	%>
			<td width="75" height="20" nowrap align="center"><%=cancelNote%></td>
	<%
		}else{
			%>
			<td width="75" height="20" nowrap align="center" id="<%=info.getId()%>"
			 	onmouseover="showHelper('<%="#"+info.getId()%>', '备注信息', '<%=cancelNote %>',50)" onmouseout="$('#_Popup_help').remove()" >
				<%=cancelNote.length()>6?cancelNote.substring(0,6)+"<font color='red'>...</font>":cancelNote %>
			</td>
			<%
		}
	%>
	
    </tr>
    <%
                switch ((int) info.getNstatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//已删除
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//未复核笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.APPROVALING:
                        lApprovalingCount++;//审批中笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.APPROVALED:
                        lApprovaledCount++;//审批完成笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.CHECK:
                        lCheckCount++;//已复核笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.SIGN:
                        lSignCount++;//已签认笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.DEAL:
                        lOnGoingCount++;//处理中笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.FINISH:
                        lFinishedCount++;//已完成笔数
                    break;
                    case (int) OBConstant.SettInstrStatus.REFUSE:
                        lRefusedCount++;//已拒绝笔数
                    break;
                    default :
                    break;
                }
             
                dDebitAmount += info.getMamount();//其中借金额
                
                dTotalAmount += info.getMamount();//按日期分类计算出的的总交易金额
                lTotalCount++;//共有笔数
                tsConfirmDate = info.getDtconfirm();
                if (listQuery != null && !listQuery.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
                        obCSI.setDebitAmount(dDebitAmount);     //其中借金额
                        vctCapSummarize.addElement(obCSI);
                }
            }
        } else {
%>
    <tr> 
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
      <td  align="center"  ><%="&nbsp;"%></td>
   
      <td  align="center"  ><%="&nbsp;"%></td>
    </tr>
    <%
        }
%>
  </table>
 <% 
    session.setAttribute("vctCap", vctCapSummarize);
    //session.setAttribute("queryCapForm",queryCapForm);
%>
    <br>
    <table width=100% border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">查询时间：<%=DataFormat.getDateString(dtSysDate)%></span></div>
        </td>
      </tr>
    </table>
	
    <table width=100% border="0" align="center" cellspacing="0" cellpadding="0">
      <tr>
      <td align="right"> 
		<!--img src="\webob\graphics\button_jiaoyizongjie.gif" width="83" height="18" border="0" onclick="javascript:summarize();"-->
		<input type="Button" class="button1" value=" 交易总结 " width="46" height="18"   onclick="javascript:summarize()">
		&nbsp;&nbsp;
		<!--img src="\webob\graphics\button_xiazaichazhao.gif" width="119" height="18" border="0" onclick="javascript:downLoadMe();"-->
		<input type="Button" class="button1" value=" 下载查找结果 " width="46" height="18"   onclick="javascript:downLoadMe()">
		&nbsp;&nbsp;
		<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="javascript:printMe();"-->
		<!--liuguang 屏蔽打印按钮
		<input type="Button" class="button1" value=" 打印 " width="46" height="18"   onclick="javascript:printMe()">
		&nbsp;&nbsp;
		-->
		</td>
      </tr>
    </table>
<%
}
%>
</form>

<script language="javascript">
    /* 页面焦点及回车控制 */
    //window.name = "Check_Window";
    firstFocus(frmjysqcx.lTransType);
    //setSubmitFunction("queryme()");
    setFormName("frmjysqcx");
</script>

<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
   <input type="hidden" name="search" value="1">
   
</form>

<script language="javascript">


     /* 查询处理函数 */
    function queryme() {
        if (validate() == true) {
        	
        	
            frmjysqcx.action = "c109.jsp";
            frmjysqcx.sNextSuccessPage.value = "";
            frmjysqcx.target = "";
            frmjysqcx.fromAccountType.value = "yes";
			showSending();
            frmjysqcx.submit();
        }
    }

    function doSee() {
        form3.action = "c110.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
        form3.target = "_formwin";    
<%--        frmjysqcx.sNextSuccessPage.value = "";      --%>
<%--        form3.strReturn.value = "/capital/query/q001-v.jsp";--%>
        form3.submit(); 
        form3.target = "";    
	   
	
<%--     window.open("<%=strContext%>/accountinfo/q003-c.jsp?menu=hidden&strReturn="/capital/query/q001-v.jsp"&txtID="+txtID+"&txtTransType="+txtTransType+"&strReturn="+strReturn+"&search="1" ");--%>
         
    }

    /* 校验函数 */
    function validate() {
            var starSubmit = frmjysqcx.sStartSubmit.value;
            var endSubmit = frmjysqcx.sEndSubmit.value;
            if (starSubmit != "") {
                if(chkdate(starSubmit) == 0) {
                    alert("请输入正确的申请开始日期");
                    frmjysqcx.sStartSubmit.focus();
                    return false;
                }
            }
            if (endSubmit != "") {
                if(chkdate(endSubmit) == 0) {
                    alert("请输入正确的申请结束日期");
                    frmjysqcx.sEndSubmit.focus();
                    return false;
                }
            }
            if ((starSubmit != "") && (endSubmit != "")) {
                if (!CompareDate(frmjysqcx.sStartSubmit, frmjysqcx.sEndSubmit, 
                    "提交日期：起始日期不能大于结束日期")) {
                    return false;
                }
            }
			if(!checkAmount(frmjysqcx.dMinAmount,"0","最小金额")){
				return false;
			}
			if(!checkAmount(frmjysqcx.dMaxAmount,"0","最大金额")){
				return false;
			}

            if ((parseFloat(reverseFormatAmount(frmjysqcx.dMinAmount.value))) > (parseFloat(reverseFormatAmount(frmjysqcx.dMaxAmount.value)))) {
                alert("最小金额不能大于最大金额");
                return false;
            }
			return true;

          
    }

    /* 打印处理函数 */
    function printMe() {
        frmjysqcx.action = "q002-c.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "q004-v.jsp";
            frmjysqcx.submit();
<%--			window.open('/NASApp/iTreasury-ebank/capital/query/q002-c.jsp?lClientID=<%=lClientID%>&dMinAmount=<%=dMinAmount%>&dMaxAmount=<%=dMaxAmount%>&lStatus=<%=queryCapForm.getStatus()%>&sStartSubmit=<%=sStartSubmit%>&sStartExe=<%=sStartExe%>&sEndExe=<%=sEndExe%>&sEndSubmit=<%=sEndSubmit%>&sNextSuccessPage=q004-v.jsp&lFixTransferID=<%=lFixTransferID%>&lNotifyTransferID=<%=lNotifyTransferID%>&lTransType=<%=queryCapForm.getTransType()%>','popup', 'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;')--%>
        }
    }
    /* 下载处理函数 */
    function downLoadMe() {
    	frmjysqcx.method="post";
        frmjysqcx.action = "c109.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "v113.jsp";
            frmjysqcx.submit();
        }
    }
    /* 交易总结处理函数 */
    function  summarize() {
    	frmjysqcx.method="post";
        frmjysqcx.action = "v111.jsp";
        frmjysqcx.sNextSuccessPage.value = "";
        frmjysqcx.target = "";
        if (validate() == true) {
            showSending();
            frmjysqcx.submit();
        }
    }
</script>

<%
        /* 显示文件尾 */
        OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
<%@ include file="/common/SignValidate.inc" %>