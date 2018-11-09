<%--
/*
 * 程序名称：q001-v.jsp
 * 功能说明：交易申请查询输入页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-11
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
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
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

<safety:resources />

<%
    //标题变量
    String strTitle = "［交易申请查询］";
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
		
		// 系统时间
        
        Timestamp dtSysDate = Env.getSystemDateTime();
		
        long lClientID = sessionMng.m_lClientID;    //客户ID
        long lTransType = -1;           //交易类型
        long lStatus = -1;              //状态
        long lLoanContractID = -1;      //合同ID
        long lFixTransferID = -1;       //定期支取账户ID
        long lNotifyTransferID = -1;    //通知支取账户ID
        String sStartSubmit = DataFormat.formatDate(dtSysDate);       //提交日期-从
        String sEndSubmit = DataFormat.formatDate(dtSysDate);         //提交日期-到
        double dMinAmount = 0.0;        //交易金额-最小值
        double dMaxAmount = 0.0;        //交易金额-最大值
        String sStartExe = "";          //执行日期-从
        String sEndExe = "";            //执行日期-到
        String sContractNo = "";        //合同号
        String strFormCtrl = "";        //FORM控制
        String sTemp = null;            //临时量
        sTemp = (String) request.getAttribute("fromAccountType");
        if (sTemp != null && sTemp.trim().length() > 0) {
            strFormCtrl = sTemp;
            Log.print("FORM控制=" + strFormCtrl);
        }
        sTemp = (String) request.getAttribute("lFixTransferID");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lFixTransferID = Long.parseLong(sTemp);
        }
        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		if (lFixTransferID == -1) {
        	lFixTransferID = obFinanceInstrDao.getLoanAccountID(
            	sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
        Log.print("定期支取账户ID=" + lFixTransferID);
        sTemp = (String) request.getAttribute("lNotifyTransferID");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lNotifyTransferID = Long.parseLong(sTemp);
        }
		if (lNotifyTransferID == -1) {
         	lNotifyTransferID = obFinanceInstrDao.getLoanAccountID(
            	sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
		}
        Log.print("通知支取账户ID=" + lNotifyTransferID);
        //从请求中获取查询结果信息
        Collection lstQuery = (Collection) request.getAttribute("cltQcf");
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
		long lChild = GetNumParam(request,"child");
        QueryCapForm queryCapForm = (QueryCapForm) request.getAttribute("queryCapForm");
        if (queryCapForm != null) {
            lTransType = queryCapForm.getTransType();       //网上银行交易类型
            Log.print("交易类型=" + lTransType);
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
            sStartExe = queryCapForm.getStartExe();         //执行日期-从
            Log.print("执行日期-从=" + sStartExe);
            sEndExe = queryCapForm.getEndExe();             //执行日期-到
            Log.print("执行日期-到=" + sEndExe);
        }
        //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<form name="frmjysqcx" method="post" action="">
<input type="hidden" name="fromAccountType" value="">
<input type="hidden" name="lClientID" value="<%=lClientID%>">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
<input type="hidden" name="sNextSuccessPage" value="">
	<table width="730" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="25" class=FormTitle >交易申请查询</td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top >
        <tr  id="commonStatus">
          <td width="5" height="25"></td>
          <td width="130" height="25"  class="graytext" colspan="2">状态：</td>
          <td width="430" height="25"  class="graytext" colspan="3">
<%
        //状态
        OBHtmlCom.showQueryStatusListControl(
            out,
            "lStatus",
            lStatus,
            " onfocus=\"nextfield ='sStartSubmit';\" "
        );
%>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr  id="commonStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="submitDate">
          <td width="5" height="25"></td>
          <td width="70" height="25" class="graytext" >提交日期：</td>
          <td width="60" height="25" class="graytext" >
            <div align="right">由</div>
          </td>
          <td width="130" height="25" class="graytext">
         		<fs_c:calendar 
	          	    name="sStartSubmit"
		          	value="" 
		          	properties="nextfield ='sEndSubmit'" 
		          	size="12"/>
		       	  <script>
	          		$('#sStartSubmit').val('<%=sStartSubmit%>');
	          	</script>
          </td>
          <td width="50" height="25" class="graytext">
            <span class="graytext">至</span>
            </td>
          <td width="250" height="25" class="graytext">
             	<fs_c:calendar 
	          	    name="sEndSubmit"
		          	value="" 
		          	properties="nextfield ='dMinAmount'" 
		          	size="12"/>
		         <script>
	          		$('#sEndSubmit').val('<%=sEndSubmit%>');
	          	</script>
          </td>
          <td width="5"></td>
        </tr>
        <tr  id="submitDateLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="70" height="25" class="graytext">金额：</td>
          <td width="60" height="25" class="graytext" >
            <div align="right">最小值<%= sessionMng.m_strCurrencySymbol /*金额最小值*/%></div>
          </td>
          <td width="130" height="25" class="graytext" colspan="1">
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
            <td width="50" height="25" class="graytext" colspan="1">
            <span class="graytext">最大值<%= sessionMng.m_strCurrencySymbol /*金额最大值*/%></span>
            </td>
            <td width="250" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMaxAmount",
                    '<%=dMaxAmount==0.0?"":DataFormat.formatDisabledAmount(dMaxAmount)%>',
                    '',
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
          </td>
          <td width="5"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
      <td width="376"><%="&nbsp;"%></td>
      <td width="134"><%="&nbsp;"%></td>
          <td width="60">
            <div align="right">
			<!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" onclick="javascript:queryme();"-->
			<input type="Button" class="button"  name="querybut" onfocus="nextfield ='';"
			value="查  找" width="46" height="18"   onclick="javascript:queryme()">
				</div>
          </td>
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
    switch((int) lTransType) {
        case (int)-1:
        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
		case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
%>
    <table width="730"  class="ItemList">
       <tr class="tableHeader">
         <td width="70" align="center" height="18"  class="ItemTitle" nowrap>
           <div>指令序号</div>
         </td>
        <%
            if (lTransType == -1) {
        %>               
         <td width="55" align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>交易类型</div>
         </td>
        <%
            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
			%>
		<td align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>下属单位名称</div>
         </td>
			<%
			}
			else
			{
        %>   
         <td align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>交易类型</div>
         </td>
        <%
           	}
        %>       
    
         <td width="90" align="center" bgcolor="#456795" class="ItemTitle"  nowrap>
           <div>金额</div>
         </td>
         <td width="40" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>状态</div>
         </td>
         <td width="90" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>交易编号</div>
         </td>
        <%
            if (lTransType == -1) {
        %>
         <td width="40" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>备注</div>
         </td>
         <td width="80" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>拒绝原因</div>
         </td>
        <%
            }
			else
			{
        %>
         <td width="100" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>备注</div>
         </td>
        <%
            }			
        %>
       </tr>
<%
        if (listQuery != null) {
            //根据排序规则，按格式显示所有记录
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
                    if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
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
         <td align="left" bgcolor="#FDF5DF" class="ItemBody" height="19">提交日期：</td>
         <td align="left" colspan="3" bgcolor="#FDF5DF" class="ItemBody" height="19"><%=sConfirmDate%></td>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="6" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="5" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
                sPrePayerAcctNo = sPayerAcctNo;
                sPayerAcctNo = info.getPayerAcctNo();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
%>
       <tr>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF">账号：</td>
         <td colspan="3" bgcolor="#DFDFDF" class="ItemBody" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":sPayerAcctNo%></td>
         <td colspan="5" bgcolor="#DFDFDF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF">活期存款：</td>
         <td colspan="3" bgcolor="#DFDFDF" class="ItemBody" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":sPayerAcctNo%></td>
         <td colspan="4" bgcolor="#DFDFDF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
%>
       <tr>
         <td width="70" align="center"  class="ItemBody" nowrap>
            <u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name; 
                form3.txtTransType.value = this.id;doSee();" 
            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u>
           <input type="text" name="txtID" size="24" value="<%=info.getID()%>" style="display:none">
           <input type="text" name="txtTransType" size="24" value="<%=info.getTransType()%>" style="display:none">
         </td>
<%
            if (lTransType == -1) {
%>		 
         <td width="55" align="center"  height="18" class="ItemBody" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
<%
            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
			%>
			<td  align="center"  height="18" class="ItemBody" nowrap>
            <%=info.getChildClientName()%></td>
			<%
			}
			else
			{
%>
         <td  align="center"  height="18" class="ItemBody" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
<%
            }
%>

         <td width="90" align="right"  class="ItemBody" nowrap>
             <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%>
         </td>
         <td  width="40" align="center"  class="ItemBody" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
         <td width="90" align="center"  class="ItemBody" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <%
            if (lTransType == -1) {
        %>
         <td  width="40" align="center"  class="ItemBody" nowrap>
             <%=info.getNote()==null || info.getNote()==""?"&nbsp;":info.getNote()%></td>
                 <td width="80" align="center"  class="ItemBody" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
		<%
            }
			else
			{
        %>
         <td width="100" align="center"  class="ItemBody" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
		<%
            }			
        %>
       </tr>
<%
                switch ((int) info.getStatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//已删除
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//未复核笔数
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
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
%>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr >
        <td colspan="4" height="1"></td>
      </tr>
      <tr class=FormTitle >
        <td  width="5" valign="top" align="left" height="22"></td>
        <td colspan="2"height="22" ><font size="3"><b >流动资金贷款账号</b></font></td>
        <td width="1" height="22" ></td>
      </tr>
      <tr >
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table class="ItemList" width="730">
      <tr bgcolor="#456795" align="center" class="tableHeader">
        <td class="ItemTitle" width="70" height="13" nowrap>指令序号</td>
        <td class="ItemTitle" width="60" height="13" nowrap>贷款种类</td>
        <td class="ItemTitle" width="50" height="13" nowrap>合同号</td>
        <td class="ItemTitle" width="40" height="13" nowrap>贷款放款日期</td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
        %>
        <td class="ItemTitle" width="100" height="13" nowrap>利息费用汇总</td>
        <%
            } else {
        %>
        <td class="ItemTitle" width="100" height="13" nowrap>金额</td>
        <%
            }
        %>
        <td class="ItemTitle" width="40" height="13" nowrap>状态</td>
        <td class="ItemTitle" width="80" height="13" nowrap>交易编号</td>
        <td class="ItemTitle" width="160" height="13" nowrap>备注</td>
      </tr>
<%
        if (listQuery != null) {
            //根据排序规则，按格式显示所有记录
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
                    if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        lTotalCount = 0;    //共有笔数
                        lDeleteCount = 0;   //已删除笔数
                        lUnCheckCount = 0;  //未复核笔数
                        lCheckCount = 0;    //已复核笔数
                        lSignCount = 0;     //已签认笔数
                        lOnGoingCount = 0;  //处理中笔数
                        lFinishedCount = 0; //已完成笔数
                        lRefusedCount = 0;  //已拒绝笔数
                        dTotalAmount = 0;   //总交易金额
                        vctCapSummarize.addElement(obCSI);
                    }
%>
      <tr>
         <td align="left" bgcolor="#FDF5DF" class="ItemBody" height="19">提交日期：</td>
         <td align="left" colspan="3" bgcolor="#FDF5DF" class="ItemBody" height="19">
            <%=sConfirmDate==null || sConfirmDate==""?"&nbsp;":sConfirmDate%></td>
         <td colspan="4" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
      </tr>
<%
                }
%>
      <tr  align="center">
        <td  align="center" width="70"  class="ItemBody" nowrap>
            <u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name; 
                form3.txtTransType.value = this.id;doSee();" 
            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u>
           <input type="text" name="txtID" size="24" value="<%=info.getID()%>" style="display:none">
           <input type="text" name="txtTransType" size="24" value="<%=info.getTransType()%>" style="display:none">
        </td>
        <td class="ItemBody"  height="13" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
        <td class="ItemBody" height="13" nowrap>
            <%=info.getLoanContractNo()==null || info.getLoanContractNo()==""?"&nbsp;":info.getLoanContractNo()%></td>
        <td class="ItemBody"  height="13" nowrap>
            <%=info.getPayDate()==null?"&nbsp;":DataFormat.getDateString(info.getPayDate())%></td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
			double interest = info.getRealInterest() + info.getRealCompoundInterest() + 
				info.getRealOverdueInterest() + info.getRealSuretyFee() + info.getRealCommission();
        %>
        <td class="ItemBody" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%>
			<%=interest==0.0?"&nbsp;&nbsp;":DataFormat.formatDisabledAmount(interest)%></td>
        <%
            } else {
        %>
        <td class="ItemBody" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
        <%
            }
        %>
        <td class="ItemBody"  height="13" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
        <td class="ItemBody"  height="13" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <td width="160" class="ItemBody" height="13" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
      </tr>
<%
                switch ((int) info.getStatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//已删除
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//未复核笔数
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
                dTotalAmount += info.getAmount();//按日期分类计算出的的总交易金额
                lTotalCount++;//共有笔数
                tsConfirmDate = info.getConfirmDate();
                if (listQuery != null && !listQuery.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
                        obCSI.setTotalCount(lTotalCount);       //共有笔数
                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
                        vctCapSummarize.addElement(obCSI);
                }
            }
        } else {
%>
      <tr>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
    }
    session.setAttribute("vctCap", vctCapSummarize);
    //session.setAttribute("queryCapForm",queryCapForm);
%>
    <br>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">查询时间：<%=dtSysDate%></span></div>
        </td>
      </tr>
    </table>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
      <td width="535"> 
        <div align="right">
		<!--img src="\webob\graphics\button_jiaoyizongjie.gif" width="83" height="18" border="0" onclick="javascript:summarize();"-->
		<input type="Button" class="button" value="交易总结" width="46" height="18"   onclick="javascript:summarize()">
		</div>
        </td>
        
      <td width="137"> 
        <div align="right">
		<!--img src="\webob\graphics\button_xiazaichazhao.gif" width="119" height="18" border="0" onclick="javascript:downLoadMe();"-->
		<!--input type="Button" class="button" value="下载查找结果" width="46" height="18"   onclick="javascript:downLoadMe()"-->
		</div>
        </td>
        
      <td width="58"> 
        <div align="right">
		<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="javascript:printMe();"-->
		<!--input type="Button" class="button" value="打  印" width="46" height="18"   onclick="javascript:printMe()"-->
		</div>
        </td>
      </tr>
    </table>
<%
}
%>
</form>

<script language="javascript">
    /* 页面焦点及回车控制 */
    window.name = "Check_Window";
    firstFocus(frmjysqcx.lStatus);
    //setSubmitFunction("queryme()");
    setFormName("frmjysqcx");
</script>

<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
</form>

<script language="javascript">
    
     /* 查询处理函数 */
    function queryme() {
        if (validate() == true) {
            frmjysqcx.action = "q062-c.jsp";
            frmjysqcx.sNextSuccessPage.value = "";
            frmjysqcx.target = "";
            frmjysqcx.fromAccountType.value = "yes";
			showSending();
            frmjysqcx.submit();
        }
    }

    function doSee() {
        form3.action = "q063-c.jsp?menu=hidden";
        frmjysqcx.sNextSuccessPage.value = "";
        form3.target = "NewWindow_S";
        form3.strReturn.value = "/capital/query/q001-v.jsp";
        form3.submit();
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
        }
    }
    /* 下载处理函数 */
    function downLoadMe() {
        frmjysqcx.action = "q002-c.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "q005-v.jsp";
            frmjysqcx.submit();
        }
    }
    /* 交易总结处理函数 */
    function  summarize() {
        frmjysqcx.action = "q006-v.jsp";
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