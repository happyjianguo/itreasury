/** 
 * 注意：模糊匹配时，第一个"%"一定要用"%25",否则有问题。
 * 所有的放大镜SQL方法：
 * 1, getFixedDepositNoSQL 定期(通知)存款单据号
 * 1-1,getFixedDepositNoSQLSpecial 资金结算-账户类型编码设置专用定期(通知)存款单据号 2008-11-12增加 kaishao
 * 2, getOfficeSQL 办事处放大镜
 * 3, getInterestRateSQL 利率放大镜
 * 4, getClientSQL 客户放大镜
 * 5, getAccountSQL 帐户放大镜的SQL语句
 * 6, getBranchSQL 开户行放大镜的SQL语句
 * 7, getCashFlowSQL 现金流向放大镜
 */

//added by liliang
function getContractCodeSQL(nOfficeID,nCurrencyID,nStatusID,nUserID,nCounterPartID)
{
	var sql = " select rownum, tt.* from ";
	    sql+= " ( select A.id, A.contractcode, B.counterpartname, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate, ";
	    sql+= "   decode(A.transtypeid,";
	    sql+= "        1001,";
	    sql+= "        '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "        1003,";
	    sql+= "        '买入回购',";
	    sql+= "        1004,";
	    sql+= "        '买入买断',";
	    sql+= "        '') TransType,";
	    sql+= " A.transtypeid ";
	    sql+= " from CRA_TRANSFERCONTRACTFORM A, CRA_COUNTERPART B ";
	    sql+= " where A.counterpartid = B.id ";
	    sql+= " and A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    sql+= " and A.statusid <> "+nStatusID+" ";
	    if (nUserID > 0)
	    {
	       sql+= " and A.inputuserid = "+nUserID+" ";
	    }
	    if (nCounterPartID > 0)
	    {
	       sql+= " and A.counterpartid = "+nCounterPartID+" ";
	    }
	    sql+= " order by A.contractcode ) tt ";
	return sql;
}
//added by zcwang 2009-07-31 通知单新增选择转让合同专用
function getContractCodeForNoticesSQL(nOfficeID,nCurrencyID,nStatusID,nUserID)
{
	var sql = " select rownum, tt.* from ";
	    sql+= " ( select A.id, A.contractcode, B.counterpartname, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate, ";
	    sql+= "   decode(A.transtypeid,";
	    sql+= "        1001,";
	    sql+= "        '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "        1003,";
	    sql+= "        '买入回购',";
	    sql+= "        1004,";
	    sql+= "        '买入买断',";
	    sql+= "        '') TransType,";
	    sql+= " A.transtypeid ";
	    sql+= " from CRA_TRANSFERCONTRACTFORM A, CRA_COUNTERPART B ";
	    sql+= " where A.counterpartid = B.id ";
	    sql+= " and A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    sql+= " and A.statusid = "+nStatusID+" ";
	    if (nUserID > 0)
	    {
	       sql+= " and A.inputuserid = "+nUserID+" ";
	    }
	    sql+= " order by A.contractcode ) tt ";
	return sql;
}

//added by xintan
//查询信贷资产转让合同放大镜
function getTransferContractCodeSQL(nOfficeID,nCurrencyID,nStatusID,nUserID,nTransType)
{
	var sql = " select rownum, tt.* from ";
	    sql+= " ( select A.id, A.contractcode, B.counterpartname, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate, ";
	    sql+= "   decode(A.transtypeid,";
	    sql+= "        1001,";
	    sql+= "        '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "        1003,";
	    sql+= "        '买入回购',";
	    sql+= "        1004,";
	    sql+= "        '买入买断',";
	    sql+= "        '') TransType,";
	    sql+= " A.transtypeid ";
	    sql+= " from CRA_TRANSFERCONTRACTFORM A, CRA_COUNTERPART B ";
	    sql+= " where A.counterpartid = B.id ";
	    sql+= " and A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    sql+= " and A.statusid = "+nStatusID+" ";
	    if(nTransType>0)
	    {
		    sql+=" and A.transtypeid = "+nTransType+" ";
		}
	    
	    if (nUserID > 0)
	    {
	       sql+= " and A.inputuserid = "+nUserID+" ";
	    }
	    sql+= " order by A.contractcode asc,A.startdate,A.transtypeid) tt ";
	return sql;
}


//added by xwhe 
function getInterestCraContractCodeSQL(nOfficeID,nCurrencyID,nStatusID,nUserID,transTypeID)
{
    var sql = " select A.id, A.transtypeid, A.contractcode, B.counterpartname, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate, ";
        sql+= "   decode(A.transtypeid,";
	    sql+= "        1001,";
	    sql+= "        '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "        1003,";
	    sql+= "        '买入回购',";
	    sql+= "        1004,";
	    sql+= "        '买入买断',";
	    sql+= "        '') TransType ";
        sql+= " from cra_transfercontractform A, cra_counterpart B";
        sql+= " where A.counterpartid = B.id";
        sql+= " and A.officeid = "+nOfficeID;
        sql+= " and A.currencyid = "+nCurrencyID;
        sql+= " and A.statusid = "+nStatusID;
        sql+= " and A.transtypeid = "+transTypeID;
        if (nUserID > 0)
	    {
	       sql+= " and A.inputuserid = "+nUserID+" ";
	    }
        sql+= " order by A.contractcode";
    return sql;
} 
//added by wangjunying
function getCraContractCodeSQL(nOfficeID,nCurrencyID,nStatusID,transTypeID)
{
    var sql = " select A.id, A.transtypeid, A.contractcode, B.counterpartname, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate";
        sql+= " from cra_transfercontractform A, cra_counterpart B";
        sql+= " where A.counterpartid = B.id";
        sql+= " and A.officeid = "+nOfficeID;
        sql+= " and A.currencyid = "+nCurrencyID;
        sql+= " and A.statusid <> "+nStatusID;
        sql+= " and A.transtypeid = "+transTypeID;
        sql+= " order by A.contractcode";
    return sql;
} 

function getApplyCodeSQL(nOfficeID,nCurrencyID,nStatusID)
{
	var sql = " select rownum, tt.* from ( select A.id , A.sapplycode , A.counterpartname,A.sapplyamount,A.zstartdate,A.zenddate from CRA_TRANSFERAPPLYFORM A where A.officeid = "+nOfficeID+" and A.currencyid = "+nCurrencyID+" and A.statusid <> "+nStatusID+" ) tt ";
	return sql;
}


//added by zhaomin
function getcounterpart(nOfficeID,nCurrencyID,nStatusID)
{
	var sql = "select id ,counterpartcode ,counterpartname from cra_counterpart where OFFICEID ="+ nOfficeID+" and CURRENCYID= "+nCurrencyID+" and statusid= "+nStatusID;
	return sql ;
}

function  getcounterpartbank(nOfficeID,nCurrencyID,nStatusID)
{
	var sql="select id,counterpartbankno,counterpartbankname,counterparname from cra_counterpartbank where  OFFICEID ="+ nOfficeID+" and CURRENCYID= "+nCurrencyID+" and statusid= "+nStatusID;
	return sql;
}

function  getcounterpartbank1(nOfficeID,nCurrencyID,nStatusID,id)
{
	var sql="select id,counterpartbankno,counterpartbankname,counterparname,counterpartid from cra_counterpartbank where  OFFICEID ="+ nOfficeID+" and CURRENCYID= "+nCurrencyID+" and statusid= "+nStatusID;
	if(id!=-1)
	{
		sql+=" and counterpartid= "+ id;
	}
	
	return sql;
}

function getApplyCodeSQL3(nOfficeID,nCurrencyID,nStatusID,nUserID)
{
	var sql = " select rownum, tt.* from ( select A.id , A.sapplycode , A.counterpartname,to_char(A.sapplyamount,'999,999,999,999,999,999,999,999.00') sapplyamount,A.zstartdate,A.zenddate, ";
	sql +="   decode(A.transtypeid,";
    sql+= "        1001,";
    sql+= "        '卖出回购',";
    sql+= "        1002,";
    sql+= "        '卖出卖断',";
    sql+= "        1003,";
    sql+= "        '买入回购',";
    sql+= "        1004,";
    sql+= "        '买入买断',";
    sql+= "        '') transtype ";
	
	sql += "from CRA_TRANSFERAPPLYFORM A where A.officeid = "+nOfficeID+" and A.currencyid = "+nCurrencyID+" ";
	
	if(nUserID>0)
	{
		sql+=" and a.inputuserid= "+nUserID ;
	}
	
	if(nStatusID>0)
	{
		sql+= " and A.statusid = "+nStatusID;
	}else{
		sql+= " and A.statusid>0 ";
	}
	
	sql+= " order by  A.sapplycode asc) tt ";
	
	return sql;
}

function getApplyCodeSQL4(nOfficeID,nCurrencyID,nStatusID,nUserID,nTransTypeID)
{
	var sql = " select rownum, tt.* from ( select A.id , A.sapplycode , A.counterpartname,to_char(A.sapplyamount,'999,999,999,999,999,999,999,999.00') sapplyamount,A.zstartdate,A.zenddate, ";
	sql +="   decode(A.transtypeid,";
    sql+= "        1001,";
    sql+= "        '卖出回购',";
    sql+= "        1002,";
    sql+= "        '卖出卖断',";
    sql+= "        1003,";
    sql+= "        '买入回购',";
    sql+= "        1004,";
    sql+= "        '买入买断',";
    sql+= "        '') transtype ";
	
	sql += "from CRA_TRANSFERAPPLYFORM A where A.officeid = "+nOfficeID+" and A.currencyid = "+nCurrencyID+" ";
	
	if(nUserID>0)
	{
		sql+=" and a.inputuserid= "+nUserID ;
	}
	
	if(nStatusID>0)
	{
		sql+= " and A.statusid = "+nStatusID;
	}else{
		sql+= " and A.statusid>0 ";
	}
	
	if(nTransTypeID>0)
	{
		sql+= " and A.transtypeid = "+nTransTypeID;
	}
	
	sql+= " order by  A.sapplycode asc) tt ";
	
	return sql;
}

function  getLoanContractCode(nTypeID,nOfficeID,nCurrencyID,nStatusID)
{
	var sql="select id, sContractCode from loan_ContractForm where nTypeID in ("+nTypeID+")  and nOfficeID = "+nOfficeID+" and nCurrencyID =  "+nCurrencyID+" and nstatusid in ("+nStatusID[0]+","+nStatusID[1]+") order by sContractCode";
	return sql;	
}

function  getClient(statusid,officeid)
{
	var sql="select id,scode,sName from client where nStatusID="+ statusid+" and nOfficeID = "+officeid+" order by sCode";

	return sql;
}
function getContractCodeSQL1(nOfficeID,nCurrencyID,nStatusID,nUserID)
{
	var sql = " select rownum, tt.* from ";
	    sql+= " ( select A.id, A.contractcode, to_char(A.amount,'FM999,999,999,999,999,999,999,999.00') amount, A.startdate, A.enddate ";
	    sql+= " from CRA_TRANSFERCONTRACTFORM A ";
	    sql+= " where A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    if(nUserID>0)
	    {
	    	sql+= " and A.inputuserid = "+nUserID+" ";
	    }
	    sql+= " and A.statusid = "+nStatusID+" order by A.contractcode ) tt ";
	return sql;
}
function getloanapplyformsql(nOfficeID,nCurrencyID,nStatusID,userid)
{
    var sql = "select a.id,";
    	sql+= "  a.sloanapplycode,";
    	sql+= "  decode(a.TRANSTYPEID,";
	    sql+= "        1001,";
	    sql+= "       '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "       1003,";
	    sql+= "       '买入回购',";
	    sql+= "       1004,";
	    sql+= "       '买入买断',";
	    sql+= "       '') TRANSTYPE,";
	    sql+= "        a.TRANSTYPEID,";
	    sql+= "   to_char((select sum(TRANSFERAMOUNT) from CRA_LOANCONTRACTDETAIL where SAPPLYID=a.id and STATUSID>0),'999,999,999,999,999,999,999.99') TRANSFERAMOUNT";
	    sql+= "  from CRA_LOANAPPLYFORM a";
	    sql+= " where A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    if(userid>0)
	    {
	    	sql+= " and A.inputuserid = "+userid+" ";
	    }
	    if(nStatusID>0)
	    {
	    sql+= " and A.statusid = "+nStatusID+" ";
	    }
	    sql+= " order by A.SLOANAPPLYCODE asc";
return sql;
}
function getloanapplyformsql1(nOfficeID,nCurrencyID,nStatusID,userid,matchvalue)
{
    var sql = "select a.id,";
    	sql+= "  a.sloanapplycode,";
    	sql+= "  b.sapplycode,";
    	sql+= "  b.zstartdate,";
    	sql+= "  b.zenddate,";
    	sql+= "  decode(a.TRANSTYPEID,";
	    sql+= "        1001,";
	    sql+= "       '卖出回购',";
	    sql+= "        1002,";
	    sql+= "        '卖出卖断',";
	    sql+= "       1003,";
	    sql+= "       '买入回购',";
	    sql+= "       1004,";
	    sql+= "       '买入买断',";
	    sql+= "       '') TRANSTYPE,";
	    sql+= "        a.TRANSTYPEID,";
	    sql+= "   to_char((select sum(TRANSFERAMOUNT) from CRA_LOANCONTRACTDETAIL where SAPPLYID=a.id and STATUSID>0),'999,999,999,999,999,999,999.99') TRANSFERAMOUNT";
	    sql+= "  from CRA_LOANAPPLYFORM a, cra_transferapplyform b";
	    sql+= " where A.officeid = "+nOfficeID+" ";
	    sql+= " and A.currencyid = "+nCurrencyID+" ";
	    sql+= " and A.sapplyformid = b.id ";
	    if(userid>0)
	    {
	    	sql+= " and A.inputuserid = "+userid+" ";
	    }
	    if(nStatusID>0)
	    {
	    sql+= " and A.statusid = "+nStatusID+" ";
	    }
	    if(matchvalue>0)
	    {
	    sql+="and a.TRANSTYPEID = '"+matchvalue+"'";
	    }
	    sql+= " order by A.SLOANAPPLYCODE asc";
return sql;
}
//added by wangjunying
function getBankNumber(nOfficeID,nCurrencyID,nStatusID,counterpartId)
{ 
    var sql = " select id, counterpartbankno, counterpartbankname, counterparname,counterpartid ";
        sql+= " from cra_counterpartbank ";
        sql+= " where OFFICEID = "+nOfficeID;
        sql+= " and CURRENCYID = "+nCurrencyID;
        sql+= " and STATUSID <> "+nStatusID;
        sql+= " and COUNTERPARTID = "+counterpartId;
        sql+= " order by counterpartbankno ";
    return sql;
}

/**
 * 帐户放大镜的SQL语句(查询字段较少一些)
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 *
 *银行付款使用
 */

function getAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,accountID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(%2B)";

	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4 ";
	}
	else if (lReceiveOrPay == -10000)
	{
		//不显示只收不付冻结账户 Boxu Add 2008年2月18日
		sql += " and a.nStatusID in (1,8) and a.nCheckStatusID=4 ";
	}
	else
	{
	    sql += " and a.nStatusID in (1,2,3,4,7,8) and a.nCheckStatusID=4 ";
	}
	
	if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID = " + nCurrencyID;
	}
	if (nAccountGroupType == 1)
	{
		sql += " and at.nAccountGroupID in (1,7)";
	}
	else if (nAccountGroupType > 1)
	{
		sql += " and at.nAccountGroupID = " + nAccountGroupType;
	}
	else if(nAccountGroupType==-12)//所有存款帐户
	{
		sql += " and at.nAccountGroupID in (1,2,3,7)";
	}
	else if(nAccountGroupType==-1000)//所有贷款帐户
	{
		sql += " and at.nAccountGroupID in (4,5,6,8)";
	}
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and at.nAccountGroupID in (4,5) ";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}	
    if (nClientID > 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	 if (accountID > 0)
	{
		sql += " and a.id = " + accountID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%25" + sAccountNo + "%25'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

