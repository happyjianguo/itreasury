<%--
 页面名称 ：query_v002.jsp
 页面功能 : 信息查询 － 申请指令查询 
 作    者 ：leiyang
 日    期 ：2008-12-05
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBCapSummarizeInfo" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "申请指令查询";
String strTemp = "";

long lFixTransferID = -1;       //定期支取账户ID
long lNotifyTransferID = -1;    //通知支取账户ID
long lTransType = -1;           //交易类型
long nEbankStatus = -1;         //银行指令状态
try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	  String strNote = "";			//汇款用途
   	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
   	if (queryCapForm != null) {
   	 lTransType = queryCapForm.getTransType();       //网上银行交易类型
   	 nEbankStatus = queryCapForm.getNEbankStatus();  //银行指令状态
   	}
   	
	if(queryCapForm == null){
		response.sendRedirect(strContext + "/capital/query/view/query_v001.jsp");
	}
	
	Collection coll = (Collection)request.getAttribute("queryCapColl");
	
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	lFixTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
	lNotifyTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
%>

<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
</script>

<form name="form1" method="post" action="../control/query_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/query_v002.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/query_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">  <!--操作代码-->
<input type="hidden" name="lDepositID" value="">
<input type="hidden" name="strDepositNo" value="">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
<input type="hidden" name="lInstructionID" value="">
<input type="hidden" name="sNextSuccessPage" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="22">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2"><%=strTitle%></span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">交易类型：</td>
		          	<td width="500" height="25" colspan="2">&nbsp;&nbsp;
	            	<%
	            		OBHtmlCom.showQueryTypeListControl(out,"lTransType",lTransType," onChange=\"disTransType(form1);\"  onfocus=\"nextfield ='lStatus';\" ",true);
	            	%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr id="trFixedDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //定期存款单据号
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lFixedDepositID",
			                "定期存款单据号",
			                sessionMng.m_lUserID,
			                lFixTransferID,
			                -1,
			                "",
			                1,
			                21,
			                "lFixTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
				<tr id="trNotifyDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //通知存款单据号
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lNotifyDepositID",
			                "通知存款单据号",
			                sessionMng.m_lUserID,
			                lNotifyTransferID,
			                -1,
			                "",
			                2,
			                21,
			                "lNotifyTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">状态：</td>
          			<td width="500" height="25" colspan="2">&nbsp;&nbsp;
					<%
						OBHtmlCom.showQueryStatusListControl(out,"lStatus",queryCapForm.getStatus()," onfocus=\"nextfield ='sStartExe';\" ");
					%>
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
        		  <tr  id="nEbankStatus">
			           <td width="5" height="25"></td>
			          <td height="25"  class="graytext" >银行指令状态：</td>
			          <td height="25"  class="graytext" >&nbsp;&nbsp;
			<%
			        //状态
			        OBConstant.BankInstructionStatus.showList(out, "nEbankStatus", 1, nEbankStatus, true, " onfocus=\"nextfield ='sStartSubmit';\" ");
			
			%>
			          </td>
			          <td width="8" height="25"></td>
			        </tr>
        		      <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">提交日期：</td>
		          	<td width="220" height="25">
		          		由&nbsp;
		          		<fs_c:calendar 
						          	    name="sStartSubmit"
							          	value="" 
							          	properties="nextfield ='sEndSubmit'" 
							          	size="20"/>
						<script>
	          		$('#sStartSubmit').val('<%=queryCapForm.getStartSubmit()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						至&nbsp;
						<fs_c:calendar 
						          	    name="sEndSubmit"
							          	value="" 
							          	properties="nextfield ='butSearch'" 
							          	size="20"/>
						<script>
	          		$('#sEndSubmit').val('<%=queryCapForm.getEndSubmit()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		       
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">金额：<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="220" height="25">
		          		由<script language="JavaScript">
							createAmountCtrl_standard("form1","dMinAmount",false,"<%=queryCapForm.getMinAmount()==0.0?"":DataFormat.formatListAmount(queryCapForm.getMinAmount())%>","dMaxAmount","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元                                           
		          	</td>
		          	<td width="280" height="25">
						至<script language="JavaScript">
							createAmountCtrl_standard("form1","dMaxAmount",false,"<%=queryCapForm.getMaxAmount()==0.0?"":DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>","sStartSubmit","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		         <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">执行日期：</td>
		          	<td width="220" height="25">
		          		由&nbsp;
		          		<fs_c:calendar 
						          	    name="sStartExe"
							          	value="" 
							          	properties="nextfield ='sEndExe'" 
							          	size="20"/>
						<script>
	          		$('#sStartExe').val('<%=queryCapForm.getStartExe()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						至&nbsp;
						<fs_c:calendar 
						          	    name="sEndExe"
							          	value="" 
							          	properties="nextfield ='dMinAmount'" 
							          	size="20"/>
						<script>
	          		$('#sEndExe').val('<%=queryCapForm.getEndExe()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr id="trSaveInternalvirement">
						 <td  colspan="6">
                     <div align="right">
						<input type="button" value=" 查 找 " class="button1" name="butSearch" onfocus="nextfield ='submitfunction';" onclick="doQuery()"/>&nbsp;
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				  <tr>
                 <td colspan="6" height="5"></td>
                 </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
  <%
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
	<tr>
		<td valign="top">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal">
				<thead>
			        <tr>
			        	<td  height="18" align="center" rowspan="2" nowrap width="15%">指令序号</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">交易类型</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">借/贷</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">金 额</td>
					    <td  height="18" align="center" colspan="2" nowrap width="30%">对方资料</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">状 态</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">银行指令状态</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">交易编号</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">汇款用途</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">备 注</td>
			        </tr>
			          <tr> 
				        <%
				            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT 
				            		|| lTransType == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) {
				        %>
				        <td  align="center"  nowrap width="20%"> <div>定期存款期限</div></td>
				        <%
				            } else {
				        %>
				        <td  align="center"  nowrap width="20%"> <div>名称</div></td>
				        <%
				            }
				        %>
				        <td  align="center"  nowrap width="10%"> <div>账号</div></td>
				      </tr>
			    </thead>
				<tbody>
				<%
				if(coll != null){
					Iterator listQuery = coll.iterator();
					String strFormatConfirmDate = "";
					long nPayerAcctID = -1;
		            while(listQuery.hasNext()) {
		                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
		                if(info.getTransNo() == null){
		                	info.setTransNo("");
		                }
		                if(info.getNote() == null){
		                	info.setNote("");
		                }
		                if(info.getReject() == null){
		                	info.setReject("");
		                }
		                strNote = info.getNote()== null?"":info.getNote().trim();
		                sPreConfirmDate = sConfirmDate;
               			 sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
		                if(strFormatConfirmDate.equals("") || !strFormatConfirmDate.equals(DataFormat.getDateString(info.getConfirmDate()))){
		                	strFormatConfirmDate = DataFormat.getDateString(info.getConfirmDate());
			                nPayerAcctID = info.getPayerAcctID();
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
						<td height="19" align="left">提交日期：</td>
						<td height="19" align="left" colspan="9"><%=strFormatConfirmDate%></td>
			        </tr>
			        <tr>
			            <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
						<td height="19" align="left" bgcolor="#DFDFDF">帐号：</td>
						<td height="19" align="left" colspan="9"  bgcolor="#DFDFDF"><%=info.getPayerAcctNo()%></td>
			        </tr>
			    <%
		                }
		                else if(nPayerAcctID != info.getPayerAcctID()) {
		                	nPayerAcctID = info.getPayerAcctID();
		        %>
			        <tr>
			            <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
						<td height="19" align="left" bgcolor="#DFDFDF">帐号：</td>
						<td height="19" align="left" colspan="9" bgcolor="#DFDFDF"><%=info.getPayerAcctNo()%></td>
			        </tr>
		        <%
		                }
			    %>
			        <tr>
			        	 <td align="center" nowrap> <u style="cursor:hand" onclick="javascript:form3.txtID.value = this.name; 
               			 form3.txtTransType.value = this.id;doSee();" 
			            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u> 
			        <input type="text" name="txtID2" size="20" value="<%=info.getID()%>" style="display:none" class="box"> 
			        <input type="text" name="txtTransType2" size="20" value="<%=info.getTransType()%>" class="box" style="display:none"> 
			     	 </td>
						<td height="25" align="center">
						<%
							if(info.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY){
								 if(info.getSBatchNo()!=null && info.getSBatchNo().length()>0) {
									 out.println("批量付款-银行汇款");
								 }
								 else {
									 out.println(OBConstant.SettInstrType.getName(info.getTransType()));
								 }
							}
							else{
								out.println(OBConstant.SettInstrType.getName(info.getTransType()));
							}
						%>
						</td>
						<td height="25" align="center"><%="借"%></td>
						<td height="25" align="right"><%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
						 <%
            			if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT
            				||lTransType==OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) {
				        %>
				      <td align="center"    nowrap> <%=info.getFixedDepositTime()>10000?(info.getFixedDepositTime()-10000)+"天":info.getFixedDepositTime()+"个月"%></td>
				      <%
				            } else {
				        %>
				      <td  align="center"  style="word-break:break-all"> <%=info.getPayeeName()==null || info.getPayeeName()==""?"&nbsp;":info.getPayeeName()%></td>
				      <%
				            }
				        %>
						<td height="25" align="center"><%=info.getPayeeAcctNo()==null?"":info.getPayeeAcctNo()%></td>
						<td height="25" align="center" nowrap><%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
						<td align="center"><%=info.getEbankStatus()==-1?"&nbsp;":OBConstant.BankInstructionStatus.getName(info.getEbankStatus()) %> </td>
						<td height="25" align="center"><%=info.getTransNo()%></td>
						  <%
							if(strNote.length()<=6){
						%>
								<td height="20" nowrap align="center"><%=strNote%></td>
						<%
							}else{
								%>
								<td  height="20" nowrap align="center" id="<%=info.getID()%>"
								 	onmouseover="showHelper('<%="#"+info.getID()%>', '汇款用途', '<%=strNote %>',50)" onmouseout="$('#_Popup_help').remove()" >
									<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
								</td>
								<%
							}
						%>
						<td height="25" align="left"><%=info.getReject()%></td>
			        </tr>
		        <% 
                switch ((int) info.getStatus()) {
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
                if (info.getTransType() == -1000) {
                    dLoanAmount += info.getAmount(); //其中贷金额
                } else {
                    dDebitAmount += info.getAmount();//其中借金额
                }
                dTotalAmount += info.getAmount();//按日期分类计算出的的总交易金额
                lTotalCount++;//共有笔数
                tsConfirmDate = info.getConfirmDate();
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
				}
				else{
				%>
			        <tr>
			        	<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
			        </tr>
			    <%
			    }
			    session.setAttribute("vctCap", vctCapSummarize);
			    %>
			    </tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height="25">查询时间：<%=DataFormat.getDateString(sessionMng.dLastLoginTime)%></td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
						<input type="button" value=" 交易总结 " class="button1" name="butQuery01" onclick="javascript:summarize()"/>&nbsp;
						<input type="button" value=" 下载查找结果 " class="button1" name="butQuery02" onclick="javascript:downLoadMe()"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</tbody>
</table>
</form>
	
	
<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
   <input type="hidden" name="search" value="1">
   
</form>
<script language="javascript">
/* 页面焦点及回车控制 */
firstFocus(form1.lTransType);
//setSubmitFunction("doQuery()");
setFormName("form1");
disTransType(form1);
function document.onkeydown(){ var e = window.event; if(e.keyCode==13 && e.srcElement.type != 'button'){ e.keyCode = 9; return; } } 
//控制不同付款方式的显示
function disTransType(form){
	var lTransType = form.lTransType.value;
	var oTrFixedDeposit = document.getElementById("trFixedDeposit");
	var oTrNotifyDeposit = document.getElementById("trNotifyDeposit");
	
	form.lFixedDepositIDCtrl.value = "";
	oTrFixedDeposit.style.display = "none";
	form.lNotifyDepositIDCtrl.value = "";
	oTrNotifyDeposit.style.display = "none";

	if(lTransType == "<%=OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER%>"){
		oTrFixedDeposit.style.display = "block";
		oTrNotifyDeposit.style.display = "none";
	}
	
	if(lTransType == "<%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW%>"){
		oTrFixedDeposit.style.display = "none";
		oTrNotifyDeposit.style.display = "block";
	}
}
function toFinanceCountQuery(form){
	form.target = "";
	form.action = "../control/query_c003.jsp";
	form.strSuccessPageURL.value = "../view/query_v003.jsp";
	form.strFailPageURL.value = "../view/query_v001.jsp";
	form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	showSending();
    form.submit();
}


function doQuery()
{
	form1.action = "../control/query_c001.jsp";
	form1.strSuccessPageURL.value = "../view/query_v002.jsp";
	form1.strFailPageURL.value = "../view/query_v001.jsp";
	form1.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	if (!validate(form1)) return;
	
	if(form1.lFixedDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lFixedDepositID.value;
		form1.strDepositNo.value = form1.lFixedDepositIDCtrl.value;
	}
	if(form1.lNotifyDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lNotifyDepositID.value;
		form1.strDepositNo.value = form1.lNotifyDepositIDCtrl.value;
	}

	showSending();
    form1.submit();
}

  /* 交易总结处理函数 */
    function  summarize() {
    	form1.method="post";
        form1.action = "../q006-v.jsp";
        form1.sNextSuccessPage.value = "";
        form1.target = "";
        if (validate(form1) == true) {
            showSending();
            form1.submit();
        }
    }
 /* 下载处理函数 */
    function downLoadMe() {
    	form1.method="post";
        form1.action = "../q002-c.jsp";
        if (validate(form1) == true) {
            form1.target = "NewWindow_S";
            form1.sNextSuccessPage.value = "q005-v.jsp";
            form1.submit();
        }
    }
   function doSee() {
        form3.action = "../q003-c.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
        form3.target = "_formwin";    
        form3.submit(); 
        form3.target = "";         
    }
function validate(form){

	if(!checkDate(form.sStartExe,0,"执行日由"))
	{
		form.sStartExe.value="";
		form.sStartExe.focus();
		return false;
	}
	
	if(!checkDate(form.sEndExe,0,"执行日至"))
	{
		form.sEndExe.value="";
		form.sEndExe.focus();
		return false;
	}
	
	if(form.sStartExe.value!="" && form.sEndExe.value!="")
	{
		if(CompareDateString(form.sEndExe.value,form.sStartExe.value))
		{
			alert("执行日至不能晚于执行日由");
			return false;
		}
	}
	
	if(!checkAmount(form.dMinAmount, 2, "金额由")){
		return false;
	}
	
	if(!checkAmount(form.dMaxAmount, 2, "金额至")){
		return false;
	}
	
	if(form.dMinAmount.value!="" && form.dMaxAmount.value!="")
	{
		if((parseFloat(reverseFormatAmount(form.dMinAmount.value))) > (parseFloat(reverseFormatAmount(form.dMaxAmount.value)))) {
        	alert("最小金额不能大于最大金额");
            return false;
        }
	}
	
	if(!checkDate(form.sStartSubmit,1,"提交日期由"))
	{
		form.sStartSubmit.value="";
		form.sStartSubmit.focus();
		return false;
	}
	
	if(!checkDate(form.sEndSubmit,1,"提交日期至"))
	{
		form.sEndSubmit.value="";
		form.sEndSubmit.focus();
		return false;
	}
	
	if(form.sStartSubmit.value!="" && form.sEndSubmit.value!="")
	{
		if(!CompareDateString(form.sStartSubmit.value,form.sEndSubmit.value))
		{
			alert("提交日期至不能晚于提交日期由");
			return false;
		}
	}

	return true;
}
</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}

%>
<%@ include file="/common/SignValidate.inc"%>