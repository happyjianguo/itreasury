/**
 * �������ƣ�MagnifierSQL.js
 * ����˵�������Ŵ���ص����ݿ��ѯ��SQL���
 * �������ߣ�ninh
 * ������ڣ�2005��02��07��
 * ע�⣺ģ��ƥ��ʱ����һ��"%"һ��Ҫ��"%25",���������⡣
 * ���еķŴ�SQL������
 * 1, getOfficeSQL ���´��Ŵ�
 * 2, getClientSQL �ͻ��Ŵ�
 * 3, getAccountSQL �ʻ��Ŵ󾵵�SQL���
 * 4, getBranchSQL �����зŴ󾵵�SQL���
 * 5, getAbstractSQL ժҪ�Ŵ�
 * 6, getUserSQL ¼���˷Ŵ�
 * 7, getExtAcctSQL �ⲿ�ʻ��Ŵ�
 * 8, getGLSubjectSQL ���ʿ�Ŀ�Ŵ�
 * 9, getGLEntrySQL ���ʷ�¼�Ŵ�
 * 10, getContractSQL ��ͬ�Ŵ�
 * 11, getPayFormSQL �ſ�֪ͨ���Ŵ�
 * 12, getDiscountCredenceSQL ����ƾ֤�Ŵ�
 * 13, getGLTypeSQL ���������ͷŴ�
 * 14, getDiscountBillSQL ����Ʊ�ݷŴ󾵣����гжһ�Ʊ�ţ�
 * 15, getUserSQL �û��Ŵ�
 * 16,getBlackListSQL ������Ʊ�ݷŴ�
 * 17, getBlankSQL �հ�ƾ֤Ʊ�ݷŴ�
 * 18, getExchangeBlankSQL �հ�ƾ֤Ʊ�ݱ��(����ʱ�õ�)
 */

//////////ע��//////////
// % ��%25 ���
// + ��%2B ���




/**
 * �ͻ��Ŵ�
 * nOfficeID ���´�ID
 * sClientNo �ͻ����
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
 * �û��Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sUserName �û�����
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
 * �ʻ��Ŵ󾵵�SQL���
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nAccountGroupType �ʻ�������
 * nAccountTypeID �ʻ�����
 * lReceiveOrPay �ջ�
 * nClientID �ͻ�ID
 * sAccountNo �ʻ����
 */
function getAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ,(ss.mbalance - ss.muncheckpaymentamount) mBalance";
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(%2B)";
	
	if (lReceiveOrPay == -1000)
	{
		//�ʻ���Ϣ��ѯר�ã����Բ������״̬���ʻ�
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
		//�ջ�(Ŀǰû����)
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
 * ժҪ�Ŵ�
 * nOfficeID ���´�ID
 * sCodeOrDesc ժҪ���������
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
 * ��Ŀ�Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sGLSubjectCode ��Ŀ����
 * nIsleaf �Ƿ�ĩ��:0,��1���ǡ�
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
 * �����зŴ󾵵�SQL���
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nIsSingleBank �Ƿ񵥱������У�1���ǣ����������ǣ�
 * nAccountID �˻�ID
 * sBranchNoOrName �����б�Ż�����
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
		//Ŀǰ��û����
	}	
	if (sBranchNoOrName != null && sBranchNoOrName.length > 0)
	{
		sql += " and (b.sCode like '%25" + sBranchNoOrName + "%25' or b.sName like '%25"+sBranchNoOrName+"%25')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

/**
 * �ⲿ�ʻ��Ŵ�
 * nOfficeID ���´�ID
 * sExtAcctNo �ⲿ�ʻ����
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
 * �����У��ⲿ���У��Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sRemitInBankName ����������
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
 * ��ͬ�Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nTypeIDArray ��ͬ���ͣ����У�ί�У����֣�
 * nStatusIDArray ��ͬ״̬
 * sContractCode ��ͬ���
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
 * �ſ�֪ͨ���Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nContractID ��ͬID
 * sTypeIDArray ֪ͨ������
 * sStatusIDArray ֪ͨ��״̬
 * sPayFormNo �ſ�֪ͨ����
 */ 
function getPayFormSQL(nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200' || sStatusIDArray == '-500' || sStatusIDArray == '-600'))
	{//����/ί���ջأ������ʻ�ȡֵ
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID,(sa.mBalance-sa.mUncheckPaymentAmount) Balance, ";
		sql += " (select decode(count(*),0,0,1) from loan_FreeForm where nPayFormID=a.ID and nStatusID=2) IsHasFree ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa,sett_Account account,loan_FreeForm ff ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID and a.ID=ff.nPayFormID(%2B) ";
		sql += " and account.nAccountTypeID in (8,9) ";

		if (sStatusIDArray == '-100')
		{
			//�����ջء���ҵ����
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) > 0 ";		
		}
		else if (sStatusIDArray == '-500')
		{
			//��Ϣ����/����ҵ�񡪡�ҵ����
			sql += " and sa.nstatusid=1  ";
		}
		else if (sStatusIDArray == '-200')
		{
			//�����ջء���ҵ�񸴺�
			sql += " and sa.nstatusid=1 and (sa.mUncheckPaymentAmount>0) ";
		}
		else if (sStatusIDArray == '-600')
		{
			//��Ϣ����/����ҵ�񡪡�ҵ�񸴺�
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
	{//����/ί�з��ţ����Ŵ�ȡֵ
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
 * ����Ʊ�ݷŴ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nContractID ��ͬ��
 * nDiscountCredenceID ����ƾ֤ID
 * sDiscountBillNo ����Ʊ�ݺ�
 */ 
 function getDiscountBillSQL(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO,to_char(b.dtDiscountDate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2 ";
    sql += " from loan_discountcontractbill a,loan_ContractForm b ";
	sql += " where b.id = a.nContractID and a.nStatusID=1 ";
	//���ջز�����ʾ
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
 * Ʊ�ݷŴ� niuyuanguang
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nContractID ��ͬ��
 * nDiscountCredenceID ����ƾ֤ID
 * sDiscountBillNo ����Ʊ�ݺ�
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
 * ���ʿ�Ŀ�Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sGLSubjectCode ��Ŀ����
 * nIsleaf �Ƿ�ĩ��:0,��1���ǡ�
 * nSubjectType ��Ŀ����
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
 * ���ʷ�¼�Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sSubjectCode ��Ŀ����
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
 * ���������ͷŴ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sGeneralLedgerCode ���������
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
 * ת���������ͷŴ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sGeneralLedgerCode ת���������
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
 * ������Ʊ�ݷŴ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sBillCode ����
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
 * Ʊ�����ͷŴ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sBillTypeCode ����
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
 * BlankTrans �Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * sBillTypeCode ����
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

/** �հ�ƾ֤Ʊ�ݱ��
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * StatusID Ʊ��״̬
 * typeId Ʊ������
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

/** �հ�ƾ֤Ʊ�ݱ��,�������ô�Ʊ�ݵĿͻ������Լ��ʻ���
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * StatusID Ʊ��״̬
 * typeId Ʊ������
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

/** �հ�ƾ֤Ʊ�ݱ��(����ʱ�õ�)
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * StatusID Ʊ��״̬
 * AbstractTypeID Ʊ������
 * typeId Ʊ������
 * useUserID ������
 * useClientID ���ÿͻ�
 * useAccountID �����ʻ�
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
	/* �������ݿ���ȥ����һ����
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