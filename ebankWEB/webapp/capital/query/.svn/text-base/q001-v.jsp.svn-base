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
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBSignatureConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBSignatureUtil"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>



<%
String strContext = request.getContextPath();
%>
<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
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
        long lFixTransferID = -1;       //定期支取账户ID
        long lNotifyTransferID = -1;    //通知支取账户ID
        String sStartSubmit = "";       //提交日期-从
        String sEndSubmit = "";         //提交日期-到
        double dMinAmount = 0.0;        //交易金额-最小值
        double dMaxAmount = 0.0;        //交易金额-最大值
        String sStartExe = "";          //执行日期-从
        String sEndExe = "";            //执行日期-到
        String strNote = "";			//add by zhouxiang
        String strReject = "";
        long nEbankStatus = -1;         //银行指令状态
        String[] nameArray = null;
		String[] valueArray = null;
        //modify by xwhe 2008-11-24
        sStartSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-从
        sEndSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-到
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
            	sessionMng.m_lClientID,sessionMng.m_lCurrencyID,3);
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
            nEbankStatus = queryCapForm.getNEbankStatus();  //银行指令状态
            Log.print("银行指令状态=" + nEbankStatus);
        }
        //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        // 系统时间
  
        Timestamp dtSysDate = Env.getSystemDateTime();
        String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
        boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
    //    boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);  //是否使用CFCA证书验签
	//	boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //是否使用新安世纪证书验签
        boolean blnNotBeFalsified = true;
        String sysDateStr = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
%>
<form name="frmjysqcx" method="get" action="">
<input type="hidden" name="fromAccountType" value="">
<input type="hidden" name="lClientID" value="<%=lClientID%>">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
<input type="hidden" name="sNextSuccessPage" value="">
<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">
<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>">
<input type="hidden" name="sysDateStr" value="<%=sysDateStr %>">
<input type="hidden" name="currencySymbol" value="<%=sessionMng.m_strCurrencySymbol %>">

<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
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
		long lIsCtrl = dao.getIsControlChild(sessionMng.m_strClientCode);
		if (lIsCtrl == 1)
		{
			OBHtmlCom.showQueryTypeListControl1(out,"lTransType",lTransType," onchange=\"jump();\"  onfocus=\"nextfield ='lStatus';\" ",true);
		}
		else
		{		
			OBHtmlCom.showQueryTypeListControl(out,"lTransType",lTransType," onchange=\"jump();\"  onfocus=\"nextfield ='lStatus';\" ",true);
		}
            //业务
            
%>
          </td>
          <td width="8" height="25"></td>
        </tr>
		<tr >
          <td colspan="6" height="1"></td>
        </tr>
		<tr  id="child">
		<td align="left"  ></td>
<%
		  		//下属单位放大镜
				String strMagnifierNameClientName = "下属单位";
				String strFormNameClientName = "frmjysqcx";
				String strMainPropertyClientName = "";
				String strPrefixClientName = "";
				String[] strMainNamesClientName = {"txtClientCode"};
				String[] strMainFieldsClientName = {"ClientCode"};
				String strReturnInitValuesClientName=queryCapForm==null?"":queryCapForm.getChildClientNo();
				String[] strReturnNamesClientName = {"lChildClientID"};
				String[] strReturnFieldsClientName = {"id"};
				String[] strReturnValuesClientName = {queryCapForm==null?"-1":String.valueOf(queryCapForm.getChildClientID())};
				String[] strDisplayNamesClientName = {"客户编号","客户名称"};
				String[] strDisplayFieldsClientName = {"ClientCode","ClientName"};
				int nIndexClientName = 0;
				String strSQLClientName = " getClient()";
				//String[] strNextControlsClientName = {"txtContractCode"};
				String strMatchValueClientName = "ClientCode";
				String strNextControlsClientName = "lStatus";
				String strTitleClientName = "下属单位客户编号";
				String strFirstTDClientName="colspan=\"2\" width=\"130\" height=\"25\" class=\"graytext\"";
				String strSecondTDClientName=" colspan=\"3\" width=\"430\" height=\"25\" class=\"graytext\" ";	
				OBMagnifier.showZoomCtrl(out,strMagnifierNameClientName,strFormNameClientName,strPrefixClientName,strMainNamesClientName,strMainFieldsClientName,strReturnNamesClientName,strReturnFieldsClientName,strReturnInitValuesClientName,strReturnValuesClientName,strDisplayNamesClientName,strDisplayFieldsClientName,nIndexClientName,strMainPropertyClientName,strSQLClientName,strMatchValueClientName,strNextControlsClientName,strTitleClientName,strFirstTDClientName,strSecondTDClientName);

		  %>
          <td height="25" class="graytext"></td>
		</tr>
       <tr  id="childLine">
          <td colspan="6" height="1"></td>
        </tr>
<%
            String strFixedDepositNo = "";
            String strNotifyDepositNo = "";
            String strFirstTD = " colspan=\"2\" width=\"130\" height=\"25\" class=\"graytext\" ";
            String strSecondTD = " colspan=\"3\" width=\"430\" height=\"25\" class=\"graytext\" ";
            String[] nextField= {"lStatus"};
%>
        <tr  id="fixedDepositID">
          <td align="left"  ></td>
           <td width="130" height="25" align="left">定期存款单据号：</td>
           <td></td>
		   <td>
				<fs_c:dic id="lFixedDepositIDCtrl" size="22" form="frmjysqcx" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL_CREATE_OLD"  sqlParams='frmjysqcx.lOfficeID.value,frmjysqcx.lCurrencyID.value,1,frmjysqcx.lFixTransferID.value,frmjysqcx.lUserID.value,frmjysqcx.lFixedDepositIDCtrl.value,21,frmjysqcx.sysDateStr.value' value="<%=strFixedDepositNo%>" nextFocus="lStatus" width="650">
					<fs_c:columns> 
						<fs_c:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
						<fs_c:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
					<fs_c:pageElements>
						<fs_c:pageElement elName="lFixedDepositIDCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lFixedDepositID" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lFixedDepositIDAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					</fs_c:pageElements>							
				</fs_c:dic>
				<input type="hidden" name='lFixedDepositID' value="-1">
				
		   </td>
<%
            /*
			//定期存款单据号
            OBMagnifier.createFixedDepositNoCtrl(
                out,
                sessionMng.m_lOfficeID,
                sessionMng.m_lCurrencyID,
                "frmjysqcx",
                "lFixedDepositID",
                "定期存款单据号",
                sessionMng.m_lUserID,
                lFixTransferID,
                -1,
                strFixedDepositNo,
                1,
                21,
                "lFixTransferID",
                strFirstTD,
                strSecondTD,
                nextField,
                ""
            );
			*/
%>

          <td height="25" class="graytext"></td>
        </tr>
        <tr  id="fixedDepositIDLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="notifyDepositID">
          <td align="left" ></td>
          <td width="130" height="25" align="left">通知存款单据号：</td>
           <td></td>
		   <td>
				<fs_c:dic id="lNotifyDepositIDCtrl" size="22" form="frmjysqcx" title="通知存款单据号" sqlFunction="getFixedDepositNoSQL"  sqlParams='frmjysqcx.lOfficeID.value,frmjysqcx.lCurrencyID.value,2,-1,frmjysqcx.lUserID.value,frmjysqcx.lNotifyDepositIDCtrl.value,21,frmjysqcx.sysDateStr.value' value="<%=strFixedDepositNo%>" nextFocus="lStatus" width="650">
					<fs_c:columns> 
						<fs_c:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs_c:column display="开户日" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
					<fs_c:pageElements>
						<fs_c:pageElement elName="lNotifyDepositIDCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lNotifyDepositID" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lNotifyDepositIDAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					</fs_c:pageElements>							
				</fs_c:dic>
				<input type="hidden" name='lNotifyDepositID' value="-1">
				
		   </td>
<%
            /*
            //通知存款单据号
            OBMagnifier.createFixedDepositNoCtrl(
                out,
                sessionMng.m_lOfficeID,
                sessionMng.m_lCurrencyID,
                "frmjysqcx",
                "lNotifyDepositID",
                "通知存款单据号",
                sessionMng.m_lUserID,
                lNotifyTransferID,
                -1,
                strNotifyDepositNo,
                2,
                21,
                "",
                strFirstTD,
                strSecondTD,
                nextField,
                ""
            );
			*/
%>

          <td height="25" class="graytext"></td>
        </tr>
        <tr  id="notifyDepositIDLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="contractA">
          <td align="left" ></td>
<%
            //合同号放大镜
            OBMagnifier.createContractCtrl(
                out,
                sessionMng.m_lOfficeID,
                sessionMng.m_lCurrencyID,
                "frmjysqcx",
                "lLoanContractID",
                "合同号",
                sessionMng.m_lClientID,
                lLoanContractID,
                sContractNo,
                SETTConstant.TransactionType.TRUSTLOANGRANT,
                1,
                "lClientID",
                strFirstTD,
                strSecondTD,
                nextField
            );
%>

          <td height="25" class="graytext"></td>
        </tr>
        <tr  id="contractALine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="contractB">
          <td align="left" ></td>
<%
            //合同号放大镜
            OBMagnifier.createContractCtrl(
                out,
                sessionMng.m_lOfficeID,
                sessionMng.m_lCurrencyID,
                "frmjysqcx",
                "lSettContractID",
                "合同号",
                sessionMng.m_lClientID,
                lLoanContractID,
                sContractNo,
                SETTConstant.TransactionType.CONSIGNLOANGRANT,
                1,
                "lClientID",
                strFirstTD,
                strSecondTD,
                nextField
            );
%>

          <td height="25" class="graytext"></td>
        </tr>
        <tr  id="contractBLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="contractC">
          <td align="left" ></td>
<%
            //合同号放大镜
            OBMagnifier.createContractCtrl(
                out,sessionMng.m_lOfficeID,
                sessionMng.m_lCurrencyID,
                "frmjysqcx",
                "lRateContractID",
                "合同号",
                sessionMng.m_lClientID,
                lLoanContractID,
                sContractNo,
                -1,
                1,
                "lClientID",
                strFirstTD,
                strSecondTD,
                nextField
            );
%>

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
        OBHtmlCom.showQueryStatusListControl(
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
        OBConstant.BankInstructionStatus.showList(out, "nEbankStatus", 1, nEbankStatus, true, " onfocus=\"nextfield ='sStartSubmit';\" ");

%>
          </td>
          <td width="8" height="25"></td>
        </tr>
        <tr  id="nEbankStatusLine">
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
		          	properties="nextfield ='dMinAmount'" 
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
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">金额：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext" colspan="1">
            <fs_c:amount 
	       		form="frmjysqcx"
       			name="dMinAmount"
       			value="<%=dMinAmount%>"
       			
       			nextFocus="dMaxAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
           </td>
           <td width="98" height="25" class="graytext" colspan="1" align="right">
           	<span class="graytext">至</span>
           </td>
           <td width="330" height="25" class="graytext" colspan="1">
           	<fs_c:amount 
	       		form="frmjysqcx"
       			name="dMaxAmount"
       			value="<%=dMaxAmount%>"
       			
       			nextFocus="sStartExe"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          	</td>
          <td width="8"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="executeDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">执行日期：</td>
          <td width="67" height="25" class="graytext">
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext" >
          <fs_c:calendar 
						          	    name="sStartExe"
							          	value="" 
							          	properties="nextfield ='sEndExe'" 
							          	size="18"/>
		<script>
	          		$('#sStartExe').val('<%=sStartExe%>');
	          	</script>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
            </td>
            <td width="330" height="25" class="graytext">
          		  <fs_c:calendar 
		          	    name="sEndExe"
			          	value="" 
			          	properties="nextfield =''" 
			          	size="18"/>
			      <script>
	          		$('#sEndExe').val('<%=sEndExe%>');
	          	</script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="executeDateLine">
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
    switch((int) lTransType) {
        case (int)-1:
        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
		case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
        case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT:
%>
     <table width="100%" class="top">
       <tbody>
         <tr>
           <td width="2%">&nbsp;</td>
           <td>
		      <br><TABLE width="100%" id="flexlist"></TABLE>	<br>
		   </td>
           <td width="2%">&nbsp;</td>
		  </tr>
	   </tbody>
	 </table>	
 <%
        if (listQuery != null) {
            //根据排序规则，按格式显示所有记录
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
                blnNotBeFalsified = true;
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                strNote = info.getNote()== null?"":info.getNote().trim();
                strReject = info.getReject() == null?"":info.getReject().trim();
				//if(isUseCertification)
				//{
				//	blnNotBeFalsified = OBSignatureUtil.validateSignature(info,certificationType);
				//}
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
                   
                }
                sPrePayerAcctNo = sPayerAcctNo;
                sPayerAcctNo = info.getPayerAcctNo();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
               } 
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
        break;
        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
%>

		<table width=100% border="0" cellspacing="0" cellpadding="0" >
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

		<TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
					<TD width="1%">&nbsp;</TD>
					<TD width="*%">
						<TABLE width="100%" id="flexlist"></TABLE>
					</TD>
					<TD width="1%">&nbsp;</TD>
				</tr>
			</TBODY>
		</TABLE>	
    
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
                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
                        obCSI.setSignCount(lSignCount);         //已签认笔数
                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
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
                        vctCapSummarize.addElement(obCSI);
                    }
                }
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
                        vctCapSummarize.addElement(obCSI);
                }
            }
        } 
        break;
    }
    session.setAttribute("vctCap", vctCapSummarize);
%>


    <br>
    <table width=100% border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">&nbsp;&nbsp;查询时间：<%=DataFormat.getDateString(dtSysDate)%></span></div>
        </td>
      </tr>
    </table>
	
    <table width=100% border="0" align="center" cellspacing="0" cellpadding="0">
      <tr>
      <td align="right"> 
		<input type="Button" class="button1" value=" 交易总结 " width="46" height="18"   onclick="javascript:summarize()">
		&nbsp;&nbsp;
		<input type="Button" class="button1" value=" 下载查找结果 " width="46" height="18"   onclick="javascript:downLoadMe()">
		&nbsp;&nbsp;
		</td>
      </tr>
    </table>
<%
}
%>
</td>
</tr>
</table>
</form>

<script language="javascript">
    /* 页面焦点及回车控制 */
   // window.name = "Check_Window";
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

<%
if (strFormCtrl != null && strFormCtrl.equals("yes")) {
	switch((int) lTransType) {
  		case (int)-1:
        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
		case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
        case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT:
%>
$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
			{display: '指令序号', name: 'ID', elType : 'link', elName : 'batchno', methodName : 'doSee("?","?","?")', width: 95, sortable: true, align: 'center'},
       	<%
            if (lTransType == -1) 
            {
       	%>
        		{display: '交易类型',  name : 'NTRANSTYPE', width : 100, sortable : true, align: 'center'},
       	<%
            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
		%>
				{display: '下属单位名称',  name : 'nChildClientid', width : 100, sortable : true, align: 'center'},
        <%
			}
			else
			{
        %>
        		{display: '交易类型',  name : 'NTRANSTYPE', width : 100, sortable : true, align: 'center'},
        <%
           	}
        %>
        	{display: '借/贷',  name : 'ncurrencyid', width : 105, sortable : false, align: 'center'},
        	{display: '金额',  name : 'MAMOUNT', width : 90, sortable : true, align: 'center'},
        	
        	
       	<%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT 
            		|| lTransType == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) 
            {
        %>
        		{display: '对方定期存款期限',  name : 'NFIXEDDEPOSITTIME', width : 110, sortable : true, align: 'center'},
        <%
            } else {
        %>
        		{display: '对方名称',  name : 'NPAYEEACCTID', width : 85, sortable : true, align: 'center'},
        <%
            }
        %>
        	{display: '对方账号',  name : 'NPAYEEACCTID', width : 85, sortable : true, align: 'center'},
        	{display: '状态',  name : 'NSTATUS', width : 65, sortable : true, align: 'center'},
        	{display: '银行指令状态',  name : 'n_statusid', width : 85, sortable : true, align: 'center'},
        	{display: '交易编号',  name : 'CPF_STRANSNO', width : 85, sortable : true, align: 'center'},
        	{display: '汇款用途',  name : 'SNOTE', width : 85, sortable : true, align: 'center'},
        	{display: '备注',  name : 'CPF_SREJECT', width : 85, sortable : true, align: 'center'}
		],//列参数
		title:'申请指令查询',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryCapFormInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false
	});
	
});
<%
        break;
        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
%>
$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
			{display: '指令序号', name: 'ID', elType : 'link', elName : 'batchno', methodName : 'doSee("?","?","?")', width: 120, sortable: true, align: 'center'},
       		{display: '贷款种类',  name : 'NTRANSTYPE', width : 120, sortable : true, align: 'center'},
       		{display: '合同号',  name : 'NCONTRACTID', width : 120, sortable : true, align: 'center'},
        	{display: '贷款放款日期',  name : 'DTPAY', width : 120, sortable : true, align: 'center'},
       	<%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) 
            {
        %>
        		{display: '利息费用汇总',  name : 'ncurrencyid', width : 120, sortable : false, align: 'center'},
        <%
            } else {
        %>
        		{display: '金额',  name : 'MAMOUNT', width : 120, sortable : true, align: 'center'},
        <%
            }
        %>
        	{display: '状态',  name : 'NSTATUS', width : 120, sortable : false, align: 'center'},
        	{display: '交易编号',  name : 'CPF_STRANSNO', width : 120, sortable : false, align: 'center'},
        	{display: '备注',  name : 'CPF_SREJECT', width : 120, sortable : false, align: 'center'}
		],//列参数
		title:'申请指令查询',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryCapFormInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false
	});
	
});
<%  	
		break;
	}
}
%>

function getFormData() 
{
	return $.addFormData("frmjysqcx","flexlist");
}
/*function doQuery()
{	
	if(doCheckForm()){
		$.gridReload("flexlist");
	}
}*/

	 window.onload = function(){
	 	var trArray = document.getElementsByTagName("tr");
	 	for(var i=0;i<trArray.length;i++)
	 	{
	 		var tr = trArray[i];
	 		if(tr.className=="notFalsified")
	 		{
	 			var tdArray = tr.childNodes;
	 			for(var j=0;j<tdArray.length;j++)
	 			{
	 				var td = tdArray[j];
	 				if(!td.onmouseover)
	 				{
	 					td.id = tr.id + j;
	 					td.attachEvent("onmouseover",mouseover);
	 					td.attachEvent("onmouseout",mouseout);
	 				}
	 			}
	 		}
	 	}	 	
	 }



    /* 业务 */
    var iTransType = frmjysqcx.lTransType.value;
    /* 初始显示业务为资金划拨 */
    jump();
    /* 
     * 实现功能：动态显示根据业务类型确定交易申请查询录入表单
     * 实现方法：通过对TR中的ID属性控制实现，"none"表示不显示，""表示显示
     */
    function jump() {
        iTransType = frmjysqcx.lTransType.value;
        /* 合同号 */
        contractA.style.display = "none";
        contractALine.style.display = "none";
        /* 合同号 */
        contractB.style.display = "none";
        contractBLine.style.display = "none";
        /* 合同号 */
        contractC.style.display = "none";
        contractCLine.style.display = "none";
        /* 定期存款单据号 */
        fixedDepositID.style.display = "none";
        fixedDepositIDLine.style.display = "none";
        /* 通知存款单据号 */
        notifyDepositID.style.display = "none";
        notifyDepositIDLine.style.display = "none";
        /* 状态 */
        commonStatus.style.display = "none";
        commonStatusLine.style.display = "none";
        /* 提交日期 */
        submitDate.style.display = "none";
        submitDateLine.style.display = "none";
        /* 金额 */
        transBalance.style.display = "none";
        transBalanceLine.style.display = "none";
        /* 执行日期 */
        executeDate.style.display = "none";
        executeDateLine.style.display = "none";
		//下属单位
		child.style.display = "none";
        childLine.style.display = "none";
        if (iTransType == -1 || iTransType == 0) {
            /* 状态 */
            commonStatus.style.display = "";
            commonStatusLine.style.display = "";
            /* 提交日期 */
            submitDate.style.display = "";
            submitDateLine.style.display = "";
			/* 金额 */
            transBalance.style.display = "";
            transBalanceLine.style.display = "";
            /* 执行日期 */
            executeDate.style.display = "";
            executeDateLine.style.display = "";
        }
        if (iTransType == <%=OBConstant.QueryInstrType.CAPTRANSFER%> ||               // 资金划拨
            iTransType == <%=OBConstant.QueryInstrType.OPENFIXDEPOSIT%> ||            // 定期开立
            iTransType == <%=OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT%> ||        // 换开定期存单
            iTransType == <%=OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER%> ||    // 定期支取
            iTransType == <%=OBConstant.QueryInstrType.OPENNOTIFYACCOUNT%>||          // 通知开立
            iTransType == <%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW%>||
            iTransType == <%=OBConstant.QueryInstrType.DRIVEFIXDEPOSIT%>||          // 定期续存
            iTransType == <%=OBConstant.QueryInstrType.CHANGEFIXDEPOSIT%>) {         // 定期转存	
            if (iTransType == <%=OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER%>) {
                /* 定期存款单据号 */
                fixedDepositID.style.display = "";
                fixedDepositIDLine.style.display = "";
            } else if (iTransType == <%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW%>) {
                /* 通知存款单据号 */
                notifyDepositID.style.display = "";
                notifyDepositIDLine.style.display = "";
            }
			else if (iTransType == <%=OBConstant.QueryInstrType.CHILDCAPTRANSFER%>)
			{
				child.style.display = "";
        		childLine.style.display = "";
			}
            /* 状态 */
            commonStatus.style.display = "";
            commonStatusLine.style.display = "";
            /* 提交日期 */
            submitDate.style.display = "";
            submitDateLine.style.display = "";
            /* 金额 */
            transBalance.style.display = "";
            transBalanceLine.style.display = "";
            /* 执行日期 */
            executeDate.style.display = "";
            executeDateLine.style.display = "";
        }
		
		if (iTransType == <%=OBConstant.QueryInstrType.CHILDCAPTRANSFER%>)
		{
			child.style.display = "";
        	childLine.style.display = "";
	
            /* 状态 */
            commonStatus.style.display = "";
            commonStatusLine.style.display = "";
            /* 提交日期 */
            submitDate.style.display = "";
            submitDateLine.style.display = "";
            /* 金额 */
            transBalance.style.display = "";
            transBalanceLine.style.display = "";
            /* 执行日期 */
            executeDate.style.display = "";
            executeDateLine.style.display = "";
		}
		
        // 贷款清还
        if (iTransType == <%=OBConstant.QueryInstrType.TRUSTLOANRECEIVE%> || 
            iTransType == <%=OBConstant.QueryInstrType.CONSIGNLOANRECEIVE%> || 
            iTransType == <%=OBConstant.QueryInstrType.INTERESTFEEPAYMENT%>||
		    iTransType == <%=OBConstant.QueryInstrType.YTLOANRECEIVE%>) {
            if (iTransType == <%=OBConstant.QueryInstrType.TRUSTLOANRECEIVE%>
			|| iTransType == <%=OBConstant.QueryInstrType.YTLOANRECEIVE%>) {
                /* 合同号 */
                contractA.style.display = "";
                contractALine.style.display = "";
            } else if (iTransType == <%=OBConstant.QueryInstrType.CONSIGNLOANRECEIVE%>) {
                /* 合同号 */
                contractB.style.display = "";
                contractBLine.style.display = "";
            } else if (iTransType == <%=OBConstant.QueryInstrType.INTERESTFEEPAYMENT%>) {
                /* 合同号 */
                contractC.style.display = "";
                contractCLine.style.display = "";
            }
            /* 状态 */
            commonStatus.style.display = "";
            commonStatusLine.style.display = "";
            /* 提交日期 */
            submitDate.style.display = "";
            submitDateLine.style.display = "";
            /* 金额 */
            transBalance.style.display = "";
            transBalanceLine.style.display = "";
            /* 执行日期 */
            executeDate.style.display = "";
            executeDateLine.style.display = "";
        }
    }

     /* 查询处理函数 */
    function queryme() {
    	
        if (validate() == true) {
            frmjysqcx.action = "q002-c.jsp";
            frmjysqcx.sNextSuccessPage.value = "";
            frmjysqcx.target = "";
            frmjysqcx.fromAccountType.value = "yes";
			showSending();
            frmjysqcx.submit();
        }
    }

    function doSee(name,id,blnNotBeFalsified) {
    	form3.txtID.value = name;
    	form3.txtTransType.value = id;
        form3.action = "q003-c.jsp?menu=hidden&blnNotBeFalsified="+blnNotBeFalsified;
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
        form3.target = "_formwin";    
        form3.submit(); 
        form3.target = "";    
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

            /* 执行日期校验 */
            var startExe = frmjysqcx.sStartExe.value;
            var endExe = frmjysqcx.sEndExe.value;
            if (startExe != "") {
                if(chkdate(startExe) == 0) {
                    alert("请输入正确的执行开始日期");
                    frmjysqcx.sStartExe.focus();
                    return false;
                }
            }
            if (endExe != "") {
                if(chkdate(endExe) == 0)
                {
                    alert("请输入正确的执行结束日期");
                    frmjysqcx.sEndExe.focus();
                    return false;
                }
            }
            if ((startExe != "") && (endExe != "")) {
                if (!CompareDate(frmjysqcx.sStartExe, frmjysqcx.sEndExe, 
                    "执行日期：起始日期不能大于结束日期")) {
                    return false;
                }
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
        frmjysqcx.action = "q002-c.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "q005-v.jsp";
            frmjysqcx.submit();
        }
    }
    /* 交易总结处理函数 */
    function  summarize() {
    	frmjysqcx.method="post";
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