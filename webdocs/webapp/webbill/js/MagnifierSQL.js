/**
 * 程序名称：MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：ninh
 * 完成日期：2005年02月07日
 * 注意：模糊匹配时，第一个"%"一定要用"%25",否则有问题。
 * 所有的放大镜SQL方法：
 * 1, getOfficeSQL 办事处放大镜
 * 2, getClientSQL 客户放大镜
 * 3, getAccountSQL 帐户放大镜的SQL语句
 * 4, getBranchSQL 开户行放大镜的SQL语句
 * 5, getAbstractSQL 摘要放大镜
 * 6, getUserSQL 录入人放大镜
 * 7, getExtAcctSQL 外部帐户放大镜
 * 8, getGLSubjectSQL 总帐科目放大镜
 * 9, getGLEntrySQL 总帐分录放大镜
 * 10, getContractSQL 合同放大镜
 * 11, getPayFormSQL 放款通知单放大镜
 * 12, getDiscountCredenceSQL 贴现凭证放大镜
 * 13, getGLTypeSQL 总帐类类型放大镜
 * 14, getDiscountBillSQL 贴现票据放大镜（银行承兑汇票号）
 * 15, getUserSQL 用户放大镜
 * 16,getBlackListSQL 黑名单票据放大镜
 * 17, getBlankSQL 空白凭证票据放大镜
 * 18, getExchangeBlankSQL 空白凭证票据编号(换发时用到)
 */

//////////注意//////////
// % 用%25 替代
// + 用%2B 替代




/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getClientSQL(nOfficeID,sClientNoOrName)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNoOrName != null && sClientNoOrName != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNoOrName + "%25' or a.sName like '%25"+sClientNoOrName+"%25')";
	}
	sql += " order by a.sCode";
	
	return sql;
}

/**
 * 用户放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sUserName 用户名称
 */
function getUserSQL(nOfficeID,nCurrencyID,sUserName)
{
	var sql = "select a.ID UserID,a.sName UserName ";
	sql += "  from userInfo a ";
	sql += " where a.nStatusID>0 ";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID = " + nCurrencyID; 
	}
	if (sUserName != null && sUserName != "") 
	{
		sql += " and a.sName like '%25" + sUserName + "%25'";
	}
	sql += " order by a.sName";
	
	return sql;
}

/**
 * 帐户放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ,(ss.mbalance - ss.muncheckpaymentamount) mBalance";
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(%2B)";
	
	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
		sql += " and a.nCheckStatusID=4 ";
	}
	else
	{
		sql += " and a.nStatusID in (1,3) and a.nCheckStatusID=4 ";
	}
	
	if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID = " + nCurrencyID;
	}
	if (nAccountGroupType > 0)
	{
		sql += " and at.nAccountGroupID = " + nAccountGroupType;
	}
	else if(nAccountGroupType==-12)
	{
		sql += " and at.nAccountGroupID in (1,2) ";
	}
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and a.nAccountTypeID in (8,9) ";
	}
	else if(nAccountTypeID == 0)
	{
		sql += " and a.nAccountTypeID in (select id from sett_accounttype sett where sett.officeid = " + nOfficeID + " and sett.currencyid = " + nCurrencyID + " and sett.naccountgroupid = " + nAccountGroupType + ") ";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}
	if (nClientID > 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '" + sAccountNo + "%25'";
	}
	sql += " order by a.sAccountNo";
	return sql;
}

/**
 * 摘要放大镜
 * nOfficeID 办事处ID
 * sCodeOrDesc 摘要代码或描述
 */
function getAbstractSQL(nOfficeID,sCodeOrDesc)
{
	var sql = "select ID AbstractID,sCode AbstractCode,sDesc AbstractDesc from sett_StandardAbstract ";
	sql += " where nStatusID > 0";
	if (nOfficeID > 0)
	{
		sql += " and nOfficeid = " + nOfficeID;
	}
	if (sCodeOrDesc != null && sCodeOrDesc.length > 0)
	{
		sql += " and (sCode like '%25" + sCodeOrDesc + "%25' or sDesc like '%25" + sCodeOrDesc + "%25')";
	}
	sql += " order by sCode";

	return sql;
}

/**
 * 科目放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGLSubjectCode 科目代码
 * nIsleaf 是否末级:0,否；1，是。
 */
function getSubjectSQL(nOfficeID,nCurrencyID,subjectCode,nIsleaf)
{
	var sql = "select id subjectID, nSubjectType subjectType, sSubjectCode as subjectCode, sSubjectName as subjectName ";
	sql += " from sett_vglsubjectdefinition where nStatus=1 ";
	if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (nIsleaf == 1)
	{
		sql += " and nIsleaf = 1";
	}
	else
	{
		sql += " and nIsleaf = 0";
	}
	if (subjectCode != null && subjectCode.length > 0)
	{
		sql += " and sSubjectCode  like '" + subjectCode + "%25'";
	}
	sql += " order by sSubjectCode ";
	
	return sql;
}

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
 * 外部帐户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部帐户编号
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

/**
 * 汇入行（外部银行）放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sRemitInBankName 汇入行名称
 */
function getRemitInBankSQL(nOfficeID,nCurrencyID,sRemitInBankName)
{
	var sql = " select ID RemitInBankID,sName RemitInBankName from sett_ExternalBank where nStatusID=1 ";
	if (sRemitInBankName != null && sRemitInBankName.length > 0)
	{
		sql += " and sName like '%25" + sRemitInBankName + "%25'";
	}
	sql += " order by sName";
	return sql;
}

/**
 * 合同放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
function getContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
{
	var sql = "select contract.ID ContractID,contract.sContractCode ContractCode,contract.nBorrowClientID ClientID, client.sname ClientName,contract.nTypeID ContractType,nChargeRateTypeID CommisionDealType ";
	sql += " from loan_contractform contract, client ";
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
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200' || sStatusIDArray == '-500' || sStatusIDArray == '-600'))
	{//信托/委托收回，从子帐户取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID,(sa.mBalance-sa.mUncheckPaymentAmount) Balance, ";
		sql += " (select decode(count(*),0,0,1) from loan_FreeForm where nPayFormID=a.ID and nStatusID=2) IsHasFree ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa,sett_Account account,loan_FreeForm ff ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID and a.ID=ff.nPayFormID(%2B) ";
		sql += " and account.nAccountTypeID in (8,9) ";

		if (sStatusIDArray == '-100')
		{
			//贷款收回——业务处理
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) > 0 ";		
		}
		else if (sStatusIDArray == '-500')
		{
			//利息费用/特殊业务——业务处理
			sql += " and sa.nstatusid=1  ";
		}
		else if (sStatusIDArray == '-200')
		{
			//贷款收回——业务复核
			sql += " and sa.nstatusid=1 and (sa.mUncheckPaymentAmount>0) ";
		}
		else if (sStatusIDArray == '-600')
		{
			//利息费用/特殊业务——业务复核
			sql += " and sa.nstatusid=1 ";
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
			sql += " and a.scode like '%25" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID,0.0 Balance,-1 IsHasFree ";
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
			sql += " and a.scode like '%25" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}



/**
 * 贴现票据放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同号
 * nDiscountCredenceID 贴现凭证ID
 * sDiscountBillNo 贴现票据号
 */ 
 function getDiscountBillSQL(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO,to_char(b.dtDiscountDate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2 ";
    sql += " from loan_discountcontractbill a,loan_ContractForm b ";
	sql += " where b.id = a.nContractID and a.nStatusID=1 ";
	//已收回不再显示
	sql += " and a.id not in (select NDISCOUNTBILLID from sett_transrepaymentdiscount where NTRANSACTIONTYPEID = 22 and NSTATUSID = 3 ) ";

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
		sql += " and b.id = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%25" + sDiscountBillNo + "%25'";
	}
	sql += " order by a.scode";
	
    return sql;
}

/**
 * 票据放大镜 niuyuanguang
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同号
 * nDiscountCredenceID 贴现凭证ID
 * sDiscountBillNo 贴现票据号
 */ 
function getBillSQL(OfficeID,CurrencyID,StatusID,MatchStr)
{
	var sql="select * from LOAN_DISCOUNTCONTRACTBILL where NOFFICEID = "+OfficeID+" and NCURRENCYID = "+CurrencyID+" and NSTATUSID = "+StatusID;
	if(MatchStr!=null&&MatchStr.length>0)
	{
		sql+=" and SCODE like '"+MatchStr+"'";
	}
	return sql;
}

function getBillSQL2(OfficeID,CurrencyID,billType,StatusID,MatchStr)
{
	var sql="select * from LOAN_DISCOUNTCONTRACTBILL ";
		sql += " where 1=1 and NSTATUSID > 0 ";
	if (OfficeID > 0)
	{
		sql += "  and NOFFICEID = "+OfficeID+"";
	}
	if (CurrencyID > 0)
	{
		sql += "  and NCURRENCYID = "+CurrencyID+"";
	}
	if (billType > 0)
	{
		sql += "  and nDraftTypeID = "+billType;
	}
	if (StatusID > 0)
	{
		sql += "  and NSTATUSID = "+StatusID;
	}
	if(MatchStr!=null&&MatchStr.length>0)
	{
		//sql+=" and SCODE like '"+MatchStr+"'";
		sql += " and SCODE like '%25" + MatchStr + "%25'";
	}
	return sql;
}
/**
 * 总帐科目放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGLSubjectCode 科目代码
 * nIsleaf 是否末级:0,否；1，是。
 * nSubjectType 科目类型
 */
function getGLSubjectSQL(nOfficeID,nCurrencyID,sGLSubjectCode,nIsleaf,nSubjectType)
{      
	var sql = "select id GLSubjectID, nSubjectType GLSubjectType, sSubjectCode as GLSubjectCode, sSubjectName as GLSubjectName ";
    sql += " from sett_vglsubjectdefinition where nStatus=1";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (nIsleaf == 1)
	{
		sql += " and nIsleaf = 1";
	}
	else
	{
		sql += " and nIsleaf = 0";
	}
	if(nSubjectType >0)
	{
		sql += " and nSubjectType = " + nSubjectType;
	}
	if (sGLSubjectCode != null && sGLSubjectCode.length > 0)
	{
		sql += " and sSubjectCode  like '%25" + sGLSubjectCode + "%25'";
	}
	sql += " order by GLSubjectCode ";
	
    return sql;
}

/**
 * 总帐分录放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sSubjectCode 科目代码
 */
function getGLEntrySQL(nOfficeID,nCurrencyID,sSubjectCode)
{      
	var sql = "select id GLEntryID,ntransactiontype GLEntryTransactionType,ncapitaltype GLEntryCapitalType,nentrytype GLEntryType,ndirection GLEntryDirection,nsubjecttype GLEntrySubjectType,ssubjectcode GLEntrySubjectCode, namountdirection GLEntryAmountDirection,namounttype GLEntryAmountType ";
    sql += " from sett_glentrydefinition where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (sSubjectCode != null && sSubjectCode.length > 0)
	{
		sql += " and sSubjectCode like '%25" + sSubjectCode + "%25'";
	}
	sql += " order by sSubjectCode";
	
    return sql;
}

/**
 * 总帐类类型放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGeneralLedgerCode 总帐类代码
 */
function getGLTypeSQL(nOfficeID,nCurrencyID,sGeneralLedgerCode)
{      
	var sql = "select ID GLID,sGeneralLedgerCode GLCode,sName GLName,sSubjectCode SubjectCode ";
    sql += " from SETT_GENERALLEDGER ";
	sql += " where nStatusID=1 ";
	
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
    if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID;
	}

	if (sGeneralLedgerCode != null && sGeneralLedgerCode.length > 0)
	{
		sql += " and (sGeneralLedgerCode like '%25" + sGeneralLedgerCode + "%25' or sName like '%25" + sGeneralLedgerCode + "%25')";
	}
	sql += " order by GLCode";
	
    return sql;
}

/**
 * 转贴现类类型放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGeneralLedgerCode 转贴现类代码
 */
function getZTXTypeSQL(nOfficeID,nCurrencyID,sGeneralLedgerCode)
{      
	var sql = "SELECT ID GLID,sSubjectCode GLCode,sSubjectName GLName,sSubjectCode sCode, sSubjectName sName";
//	sSubjectCode sCode, sSubjectName sName ";
    sql += " from Sett_VGLSubjectDefinition ";
	sql += " where nStatusID=1 and nIsLeaf = 1";
	
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
    if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID;
	}
	sql += " order by sCode";
	
    return sql;
}

/**
 * 黑名单票据放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sBillCode 代码
 */
function getBlackListSQL(billTypeId,sBillCode)
{
	var sql  = " select * from (";
		sql += " select  blacklist.id,blacklist.billtypeid, blacklist.BILLCODE, billtype.name";
		sql += " from Bill_BlackList blacklist,BILL_BILLTYPE billtype";
		sql += " where blacklist.STATUSID >0 ";
		sql += " and billtype.STATUSID >0";
		sql += " and blacklist.billtypeid =  billtype.id ";
		sql += " )";
		sql += " where 1=1 ";
	if (billTypeId > 0)
	{
		sql += " and billtypeid = " + billTypeId;
	}
	if (sBillCode != null && sBillCode.length > 0)
	{
		sql += " and BILLCODE like '%25" + sBillCode +  "%25'";
	}
	sql += " order by BILLCODE";
	return sql;
}

/**
 * 票据类型放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sBillTypeCode 代码
 */
function getBillTypeSQL(nOfficeID,nCurrencyID,sBillTypeCode)
{
	var sql  = " select * ";
		sql += " from Bill_BillType ";
		sql += " where STATUSID >0 ";

	if (nOfficeID > 0)
	{
		sql += " and officeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and currencyID = " + nCurrencyID;
	}
	if (sBillCode != null && sBillCode.length > 0)
	{
		sql += " and code like '%25" + sBillTypeCode +  "%25'";
	}
	sql += " order by code ";
	return sql;
}


/**
 * BlankTrans 放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sBillTypeCode 代码
 */
function getBlankTransSQL(nOfficeID,nCurrencyID,nTransTypeID,sTransCode)
{
	var sql  = " select * ";
		sql += " from Bill_BlankTransaction ";
		sql += " where STATUSID >0 ";

	if (nOfficeID > 0)
	{
		sql += " and officeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and currencyID = " + nCurrencyID;
	}
	if (sTransCode != null && sTransCode.length > 0)
	{
		sql += " and transCode like '%25" + sTransCode +  "%25'";
	}
	sql += " order by transCode ";
	return sql;
}

/** 空白凭证票据编号
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * StatusID 票据状态
 * typeId 票据类型
*/
function getBlankSQL(OfficeID,CurrencyID,StatusID,typeID,MatchStr)
{
	var sql="select * from Bill_BlankVoucher where 1=1";
	if (OfficeID >0)
	{
		sql+=" and OFFICEID = "+OfficeID;
	}
	if (CurrencyID >0)
	{
		sql+=" and CURRENCYID = "+CurrencyID;
	}
	if (typeID >0)
	{
		sql+=" and billTypeId="+typeID;
	}
	if(StatusID != null && StatusID.length>0)
	{
		sql+=" and StatusID in ("+StatusID+")";
	}
	if(MatchStr!=null&&MatchStr.length>0)
	{
		sql+=" and CODE like '%25"+MatchStr+"%25'";
	}
	sql+=" order by code";
	return sql;
}

/** 空白凭证票据编号,返回领用此票据的客户名称以及帐户号
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * StatusID 票据状态
 * typeId 票据类型
*/
function getBlankBillSQL(OfficeID,CurrencyID,StatusID,typeID,MatchStr)
{
	var sql="select a.*,b.sname as clientName,c.sAccountNo as accountNo from Bill_BlankVoucher a,client b,sett_Account c where a.useClientID=b.id and a.useAccountID=c.id";
	if (OfficeID >0)
	{
		sql+=" and a.OFFICEID = "+OfficeID;
	}
	if (CurrencyID >0)
	{
		sql+=" and a.CURRENCYID = "+CurrencyID;
	}
	if (typeID >0)
	{
		sql+=" and a.billTypeId="+typeID;
	}
	if(StatusID != null && StatusID.length>0)
	{
		sql+=" and a.StatusID in ("+StatusID+")";
	}
	if(MatchStr!=null&&MatchStr.length>0)
	{
		sql+=" and a.CODE like '%25"+MatchStr+"%25'";
	}
	sql+=" order by a.code";
	return sql;
}

/** 空白凭证票据编号(换发时用到)
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * StatusID 票据状态
 * AbstractTypeID 票据属性
 * typeId 票据类型
 * useUserID 领用人
 * useClientID 领用客户
 * useAccountID 领用帐户
*/
function getExchangeBlankSQL(OfficeID,CurrencyID,StatusID,AbstractTypeID,useUserID,typeID,useClientID,useAccountID,MatchStr)
{
	var sql="select bill.*,c.sName as ClientName,c.sCode as ClientCode,";
	    sql +=" a.sAccountNo as accountNo,a.sName as AccountName ";
		sql +=" from Bill_BlankVoucher bill,Client c,sett_Account a,bill_billType type ";
		sql +=" where 1=1 and bill.billTypeId = type.id ";
		sql +=" and bill.useClientId = c.id and bill.useAccountId = a.id ";
	if (OfficeID >0)
	{
		sql+=" and bill.OFFICEID = "+OfficeID;
	}
	if (CurrencyID >0)
	{
		sql+=" and bill.CURRENCYID = "+CurrencyID;
	}
	if (typeID >0)
	{
		sql+=" and bill.billTypeId="+typeID;
	}
	if(StatusID != null && StatusID.length>0)
	{
		sql+=" and bill.StatusID in ("+StatusID+")";
	}
	if (AbstractTypeID != null && AbstractTypeID.length>0)
	{
		sql+=" and type.abstractTypeId in ("+AbstractTypeID+")";
	}
	/* 由于数据库变更去掉这一条件
	if (useUserID >0)
	{
		sql +=" and bill.useUserID="+useUserID;
	}
	*/
	if (useClientID >0)
	{
		sql +=" and bill.useClientID="+useClientID;
	}
	if (useAccountID >0)
	{
		sql+=" and bill.useAccountID="+useAccountID;
	}
	if(MatchStr!=null&&MatchStr.length>0)
	{
		sql+=" and bill.CODE like '%25"+MatchStr+"%25'";
	}
	return sql;
}