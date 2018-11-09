<%--
/**
 * 程序名称：MagnifierSQL.jsp
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：
 * 完成日期：2003年08月07日
 */
--%> 
<%@ page import=" java.util.*,java.rmi.*,java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.Constant,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*"
%>

<%
try
{
%>
 
<Script language="JavaScript">
//本部分待修改
	/**==========付款方银行账户编号放大镜====================*/
	function getBankAccountCodeSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID+" as InstructionID, a.sbankaccountno ,b.saccountno ,a.nAccountID "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(<%=URLEncoder.encode("+")%>)=b.id and b.nAccountTypeID=c.id(<%=URLEncoder.encode("+")%>) and c.nAccountGroupID=<%=SETTConstant.AccountGroupType.CURRENT%>"
                 + " and b.nStatusID=<%=SETTConstant.AccountStatus.NORMAL%>"
                 + " and b.nCheckStatusID =<%=SETTConstant.AccountCheckStatus.CHECK%>" ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '<%=URLEncoder.encode("%")%>" + sBankAccountCode + "<%=URLEncoder.encode("%")%>' ";
		}
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql += " order by b.saccountno  ";
		
		return sql;
	}
	
	/**==========收款方银行账户编号放大镜（本转）====================*/
	function getPayeeBankNOSQL(lClientID,lCurrencyID,sPayeeBankNo,sPayeeName)
	{
		var sql = " select id, sPayeeName,sPayeeBankName,SPAYEEPROV,SPAYEECITY, spayeeacctno  from OB_PAYEEINFO where sPayeeName is not null "
				+ " and niscpfacct=<%=OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES%>"
				+ " and nclientid ="+lClientID+" and ncurrencyid ="+lCurrencyID;
		if (sPayeeName!=null && sPayeeName!="")
		{
			sql = sql + " and sPayeeName like '<%=URLEncoder.encode("%")%>" + sPayeeName + "<%=URLEncoder.encode("%")%>' ";
		}
		if (sPayeeBankNo!=null && sPayeeBankNo!="")
		{
			sql = sql + " and spayeeacctno like '<%=URLEncoder.encode("%")%>" + sPayeeBankNo +"<%=URLEncoder.encode("%")%>' ";
		}
		sql += " order by spayeeacctno ";
		
		return sql;
	}
	
	/**==========收款方银行账户编号放大镜（汇）====================*/
	function getPayeeAccountNOSQL(lClientID,lCurrencyID,sPayeeAccountNo,sPayeeName)
	{
		var sql = " select id, sPayeeName,spayeeacctno,sPayeeBankName,SPAYEEPROV,SPAYEECITY from OB_PAYEEINFO  where sPayeeName is not null "
				+ " and nclientid ="+lClientID+" and ncurrencyid ="+lCurrencyID ;
		if (sPayeeName!=null && sPayeeName!="")
		{
			sql = sql + " and sPayeeName like '<%=URLEncoder.encode("%")%>" + sPayeeName +"<%=URLEncoder.encode("%")%>'";
		}
		if (sPayeeAccountNo!=null && sPayeeAccountNo!="")
		{
			sql = sql + " and spayeeacctno like '<%=URLEncoder.encode("%")%>" + sPayeeAccountNo +"<%=URLEncoder.encode("%")%>'";
		}
		sql += " order by spayeeacctno";
		
		return sql;
	}
	
/**
 * 定期(通知)存款单据号
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 * lDepositTypeID 存单类型：1，定期；2，通知。
 * nAccountID 主帐户ID
 * nUserID 当前用户
 * sDepositNo 存单号
 * nTypeID 类型
 * 	1，定期(通知)开立--复核匹配时使用
 * 	21，定期（通知）支取--业务处理时使用
 * 	22,定期（通知）支取--业务复核时使用
 * 	3、定期续期转存--业务处理时使用（仅显示已到期的存单）
 * sSystemDate 开机日期
 */
function getFixedDepositNoSQL(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{
	//定期存单
	if (nTypeID == 1)
	{
		//定期（通知）开立--复核匹配时使用
		var sql = "select -1 SubAccountID,a.sDepositNo DepositNo,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,'' OpenDate,0 Capital,0 Balance,nAccountID AccountID,0 Rate,0 Interval,'' StartDate ";
		sql += " from sett_TransOpenFixedDeposit a ";
		sql += " where a.nStatusID=2 ";
 		
		if (lDepositTypeID == 1)
		{
			//定期开立
			sql += " and a.nTransactionTypeID=12"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知开立
			sql += " and a.nTransactionTypeID=15"; 			
		}
		if (nOfficeID > 0)
		{
			sql += " and a.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and a.nCurrencyID = " + nCurrencyID; 
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (nUserID > 0)
		{
			sql += " and a.nInputUserID <> " + nUserID; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.sDepositNo";
		
		return sql;
	}
	else if (nTypeID == 22 || nTypeID == 21)
	{
		//定期（通知）支取--业务处理 或 复核时使用
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 ";
 		
		if (nTypeID == 21)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
		}
		else if (nTypeID == 22)
		{
			//复核只显示已保存过的。
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>0 ";
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and ma.nAccountTypeID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and ma.nAccountTypeID=3"; 			
		}
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (nUserID > 0)
		{
			sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else if (nTypeID == 3)
	{
		//定期续期转存--业务处理时使用
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 and ma.nAccountTypeID=2 ";
 		
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (nUserID > 0)
		{
			sql += " and a.nInputUserID <> " + nUserID; 
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
}	
/**
 * 合同放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
/*function getContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
{      
	var sql = "select contract.ID ContractID,contract.sContractCode ContractCode,contract.nBorrowClientID ClientID, client.sname ClientName from loan_contractform contract, client ";
    sql += " where contract.nborrowclientid=client.id and contract.nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and contract.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and contract.ncurrencyid = " + nCurrencyID;
	}
	if (sTypeIDArray != null && sTypeIDArray.length > 0)
	{
		sql += " and contract.nTypeID in ("+sTypeIDArray+")";
	}
	if (sStatusIDArray != null && sStatusIDArray.length > 0)
	{
		sql += " and contract.nStatusID in ("+sStatusIDArray+")";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and contract.sContractCode like '" + sContractCode + "%25'";
	}
	if (nClientID > 0)
	{
		sql += " and contract.nborrowclientid = " + nClientID;
	}
	sql += " order by contract.sContractCode";

    return sql;
}*/

/**==========合同放大镜SQL语句===========
* lType  查找类型  1：委托免还；  2：逾期  3：放款通知单
* lLoanType 贷款种类
* lClientID 客户标识
* lInputUserID 用户标识
*=====================================*/
function getContractSQL(lType,lLoanType,lClientID,lInputUserID)
{
	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(<%=URLEncoder.encode("+")%>) ";
		
	if(lType==1)//查找委托免还的合同
	{
		strSQL += " and a.nTypeID in (<%=LOANConstant.LoanType.WT%>,<%=LOANConstant.LoanType.WT%>)";//委托免还
	}
	else if(lType==2)//查找逾期的合同
	{
		strSQL += " and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";//逾期没有 “贴现“ 贷款类型
	}
	else if(lType==3)//查找放款通知单的合同，没有 “贴现“ 贷款类型
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";
	}
	else if(lType==4)//查找贴现凭证的合同
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID = <%=LOANConstant.LoanType.TX%>";
	}
	else if(lType==5)//查找贷款合同风险状态变更合同
	{
		strSQL += " and a.nStatusID = <%=LOANConstant.ContractStatus.DELAYDEBT%> ";
	}
	//如果有其他的查找类型，可以用lType来区分
	///////////////////////////////////////
	if(lLoanType > 0)//查找该贷款类型下的合同
	{
		strSQL += " and a.nTypeID = " + lLoanType;
	}
	if(lClientID > 0)//查找该客户下的合同
	{
		strSQL += " and a.nBorrowClientID = " + lClientID;
	}
	if(lInputUserID > 0)//查出录入人为该用户的合同
	{
		strSQL += " and a.nInputUserID = " + lInputUserID;
	}
		strSQL += " order by a.sContractCode ";//按合同编号排序
	//System.out.println("sql==="+strSQL);
	return strSQL;
}


/**
 * 放款通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 放款通知单号
 */ 
function getPayFormSQL(nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200'))
	{//信托/委托收回，从子帐户取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID ";
		
		if (sStatusIDArray == '-100')
		{
			//收回——业务处理
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) > 0 ";		
		}
		else
		{
			//收回——业务复核
			//sql += " and sa.nstatusid=1 and sa.al_nLoanNoteID in (select distinct nLoanNoteID from sett_TransRepaymentLoan where nStatusID=2) ";
			//sql += " and sa.nstatusid=1 and sa.mUncheckPaymentAmount>0 ";
		}
		
	    if (nOfficeID > 0)
		{
			sql += " and b.nOfficeID = " + nOfficeID;
		}
		if (nCurrencyID > 0)
		{
			sql += " and b.nCurrencyID = " + nCurrencyID;
		}
		if (nContractID > 0)
		{
			sql += " and a.nContractID = " + nContractID;
		}
		if (sTypeIDArray != null && sTypeIDArray.length > 0)
		{
			sql += " and b.nTypeID in ("+sTypeIDArray+")";
		}	
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and a.scode like '" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID ";
		
	    if (nOfficeID > 0)
		{
			sql += " and b.nOfficeID = " + nOfficeID;
		}
		if (nCurrencyID > 0)
		{
			sql += " and b.nCurrencyID = " + nCurrencyID;
		}
		if (nContractID > 0)
		{
			sql += " and a.nContractID = " + nContractID;
		}
		if (sTypeIDArray != null && sTypeIDArray.length > 0)
		{
			sql += " and b.nTypeID in ("+sTypeIDArray+")";
		}	
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			sql += " and a.nStatusID in ("+sStatusIDArray+")";
		}
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and a.scode like '" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}

//adding
/**
 * 开户行放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边账银行（1，是；其它，不是）
 * nAccountID 账户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQL(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,ab.sBankAccountNo BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab ";
	    sql += " where b.nStatusID=1 and ab.nBankID(%2B)=b.ID";
		
		sql += " and ab.nAccountID = " + nAccountID;
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,'' BranchAccountNo";
		sql += " from sett_Branch b ";
	    sql += " where b.nStatusID=1 ";
	}
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
    if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}		
	if (nIsSingleBank = 1)
	{
		//目前还没有用
	}	
	if (sBranchNoOrName != null && sBranchNoOrName.length > 0)
	{
		sql += " and (b.sCode like '%25" + sBranchNoOrName + "%25' or b.sName like '%25"+sBranchNoOrName+"%25')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

/**
 * 外部账户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部账户编号
 */
function getExtAcctSQL(nOfficeID,sExtAcctNo)
{      
	var sql = "select ID ExtAcctID,sExtAcctNo ExtAcctNo,sExtAcctName ExtAcctName,sProvince ExtProvince,sCity ExtCity,sBankName ExtBankName ";
    sql += " from sett_ExternalAccount where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sExtAcctNo like '%25" + sExtAcctNo + "%25'";
	}
	sql += " order by sExtAcctNo";
	
    return sql;
}

/**===========账户放大镜===============
* lType  查找类型
* lOfficeID 办事处标识
* lCurrencyID 货币标识
* lClientID 客户标识
* lAccountType 账户类型
*=====================================*/
function getAccountSQL(lOfficeID,lCurrencyID,lClientID,lType,lAccountGroupType,sAccountNo)
{
	var strSQL ="";
	strSQL = " select a.SACCOUNTNO,c.sName,a.id from SETT_Account a,sett_accounttype b,client c ";
	strSQL += " where a.nClientID=c.ID and a.naccounttypeid = b.id(<%=URLEncoder.encode("+")%>) ";
	strSQL += " and a.nCheckStatusID = 4 ";
	if(lOfficeID > 0)
	{
		strSQL += " and a.nOfficeID = " + lOfficeID;
	}
	if(lCurrencyID > 0)
	{
		strSQL += " and a.nCurrencyID = " + lCurrencyID;
	}
	if(lClientID > 0)
	{
		strSQL += " and a.nClientID = " + lClientID;
	}
	//放款通知单中所使用的活期账户只能是正常(1)、封存(3)以及部分冻结(8)状态并且是已复核完的
	if(lType == 100)
	{
	        strSQL += " and a.NSTATUSID in(<%=SETTConstant.AccountStatus.NORMAL%>,<%=SETTConstant.AccountStatus.FREEZE%>,<%=SETTConstant.AccountStatus.SEALUP%>,<%=SETTConstant.AccountStatus.PARTFREEZE%>) ";
			strSQL += " and ( b.naccountGroupid = <%=SETTConstant.AccountGroupType.CURRENT%> or b.naccountGroupid = <%=SETTConstant.AccountGroupType.OTHERDEPOSIT%> )";
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
	        strSQL += " and a.sAccountNo like '%25" + sAccountNo + "%25'";
	}
	if(lAccountGroupType > 0)
	{
	        strSQL += " and b.naccountGroupid = "+ lAccountGroupType;
	}
	strSQL += " order by a.sAccountNo ";
	return strSQL;
}
</Script>
<Script language="JavaScript">
function getClientTypeSQL()
{
	var strSQL = "select sCode,sName,nInputUserID,dtInput,id ";
	strSQL += " from LOAN_ClientType where 1=1 and nStatusID= "+<%=Constant.RecordStatus.VALID%>;
	strSQL += " order by sCode ";
	return strSQL;
}
	
</SCRIPT> 

<%
}
catch (Exception ex)
{
	out.println(ex.toString());
}
%>
