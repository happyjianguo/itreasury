/** 
 * 程序名称：MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：Forest Ming
 * 完成日期：2003年08月07日
 * 注意：模糊匹配时，第一个"%"一定要用"%",否则有问题。
 * 所有的放大镜SQL方法：
 * 1, getFixedDepositNoSQL 定期(通知)存款单据号
 * 1-1,getFixedDepositNoSQLSpecial 资金结算-账户类型编码设置专用定期(通知)存款单据号 2008-11-12增加 kaishao
 * 2, getOfficeSQL 办事处放大镜
 * 3, getInterestRateSQL 利率放大镜
 * 4, getClientSQL 客户放大镜
 * 5, getAccountSQL 帐户放大镜的SQL语句
 * 6, getBranchSQL 开户行放大镜的SQL语句
 * 7, getCashFlowSQL 现金流向放大镜
 * 8, getAbstractSQL 摘要放大镜
 * 9, getUserSQL 录入人放大镜
 * 10, getBankBillSQL 银行票据放大镜
 * 11, getExtAcctSQL 外部帐户放大镜
 * 12, getGLSubjectSQL 总帐科目放大镜
 * 13, getGLEntrySQL 总帐分录放大镜
 * 14, getAccountRtnDepostNoSQL 帐户放大镜的SQL语句(返回存单号，定期/通知开立专用)
 * 15, getContractSQL 合同放大镜
 * 16, getPayFormSQL 放款通知单放大镜
 * 17, getRemitInBankSQL 汇入行放大镜
 * 18, getDiscountCredenceSQL 贴现凭证放大镜
 * 19，getAheadPayFormSQL 提前还款通知单
 * 20, getGLTypeSQL 总帐类类型放大镜
 * 21, getDiscountBillSQL 贴现票据放大镜（银行承兑汇票号）
 * 22, getUserSQL 用户放大镜
 * 23, getParentCorpSQL 母公司放大镜
 * 24, getFreeFormSQL 免还申请单放大镜
 * 25, getSuperCorp1SQL 上级单位1放大镜
 * 26, getSuperCorp2SQL 上级单位2放大镜
 * 27, getBankAccountNoSQL 银企接口--银行帐户编号放大镜
 * 28, getSecNoticeFormSQL 证券投资结算--证券业务通知单放大镜
 * 29, getLossAndFreezeAccountSQL 帐户放大镜的SQL语句(挂失冻结专用)
 * 30, getContractAssureSQL 担保合同放大镜SQL语句
 * 31, getLossAndFreezeDepositNoSQL 挂失冻结业务 存款单据号放大镜SQL语句
 * 32, getConsignContractSQL 合同放大镜(贷款未收利息类表外业务用)
 * 33, getConsignPayFormSQL 放款通知单放大镜(贷款未收利息类表外业务用)
 * 34, getDiscountSubjectSQL 商业汇票贴现类表外科目放大镜
 * 35, getDiscountBillReturnSQL 银行票据放大镜(海尔)
 * 36, getStockProjectNameSQL 股份公司分析项目放大镜(sefc)
 * 37, getClientSQL1
 * 38, getUpGatheringPolicySQL 创建资金上收策略放大镜
 * 39, getTransferNoticeFormSQL 信贷资产转让通知单放大镜
 * 40, getLendingOfficeSQL ,getLendAccountSQL_a 内部拆借（收回）业务机构放大镜 
 * 41, getLendAccountSQL 内部拆借（收回）业务账户放大镜
 * 42, gethidNegociateInterestRate 获得协定存款利率放大镜
 */

//////////注意//////////
// % 用% 替代
// + 用+ 替代
   
 
 
//////////以下SQL，请勿改动/////////////
/**
 * 定期(通知)存款单据号
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 * lDepositTypeID 存单类型：1，定期；2，通知。
 * nAccountID 主帐户ID
 * nUserID 当前用户
 * sDepositNo 存单号
 * nTypeID 类型
 *  0, 显示全部
 * 	1，定期(通知)开立--复核匹配时使用
 * 	21，定期（通知）支取--业务处理时使用
 * 	22,定期（通知）支取--业务复核时使用
 *  23,通知支取--业务处理时使用
 * 	3、定期续期转存--业务处理时使用（仅显示已到期的存单）
 * sSystemDate 开机日期
 */
 
 /**
  * 结算UI升级，SQL中不再需要转义符；
  * modify by xiangzhou 2012-08-08
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
		else if (lDepositTypeID == 3)
		{
			//保证金开立
			sql += " and a.nTransactionTypeID=301"; 			
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
			sql += " and a.sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.sDepositNo";
		
		return sql;
	}
	else if (nTypeID == 22 || nTypeID == 21)
	{
		//定期（通知）支取--业务处理 或 复核时使用
		
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,to_char(a.mOpenAmount,'999,999,999,999,990.99') Capital,to_char(a.mBalance,'999,999,999,999,990.99') Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		//var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ,c.id ClientID ,c.scode ClientNO,c.sname ClientName,ma.id AccountID,ma.saccountno AccountNO, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3"; //中交修改 by ruiwang
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		//sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat ,client c ";//中交修改 by ruiwang
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 " ;
		sql += " and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		
		if (nTypeID == 21)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) >= 0";
		}
		else if (nTypeID == 22)
		{
			//复核只显示已保存过的。
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>=0 ";
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			//保证金存款
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		alter(sql);
		return sql;			
	}
	else if (nTypeID == 302 || nTypeID == 303)
	{
		//保后处理
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,to_char(a.mOpenAmount,'999,999,999,999,990.99') Capital,to_char(a.mBalance,'999,999,999,999,990.99') Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		//var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ,c.id ClientID ,c.scode ClientNO,c.sname ClientName,ma.id AccountID,ma.saccountno AccountNO, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3"; //中交修改 by ruiwang
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat, loan_assurechargeform b, loan_contractform c";
		//sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat ,client c ";//中交修改 by ruiwang
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 " ;
		sql += " and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		
		if (nTypeID == 302)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) >= 0";
		}
		else if (nTypeID == 303)
		{
			//复核只显示已保存过的。
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>=0 ";
		}


		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			//保证金存款
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";			
		}
		sql += " and a.al_nloannoteid=b.id";
		sql += " and b.contractId=c.id";
		sql += " and c.ntypeid=8";	
		sql += " order by a.af_sDepositNo ";
		
		return sql;					
	}	
	else if (nTypeID == 23)
	{
		//定期（通知）支取--业务处理 或 复核时使用
		
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,to_char(a.mOpenAmount,'999,999,999,999,990.99') Capital,to_char(a.mBalance,'999,999,999,999,990.99') Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nNoticeDay Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		//var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ,c.id ClientID ,c.scode ClientNO,c.sname ClientName,ma.id AccountID,ma.saccountno AccountNO, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3"; //中交修改 by ruiwang
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		//sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat ,client c ";//中交修改 by ruiwang
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 " ;
		sql += " and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		

			sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";

		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			//保证金存款
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else if (nTypeID == 3)
	{
		//定期续期转存--业务处理时使用
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID in (1,7) and ma.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 2 ";
 		
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else if (nTypeID == 101)
	{
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID>0 ";
		sql += " and ma.nAccountTypeID = sat.id and sat.nStatusID=1";
 		
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			sql += " and sat.nAccountGroupID=3"; 			
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3,11)";
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		//if(sSystemDate != null && sSystemDate != "")
		//{
		//	sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		//}
		sql += " order by a.af_sDepositNo ";
		
		return sql;
	}
	else
	{
		//显示全部
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID>0 ";
		sql += " and ma.nAccountTypeID = sat.id and sat.nStatusID=1";
 		
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 			
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3,11)";
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}	

}

//Boxu update 2008-11-26
function getFixedDepositNewSQL(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate,lStatusID)
{
	if (nTypeID == 1)
	{
		var sql = "select -1 SubAccountID,a.sDepositNo DepositNo,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,'' OpenDate,0 Capital,0 Balance,nAccountID AccountID,0 Rate,0 Interval,'' StartDate ";
		sql += " from sett_TransOpenFixedDeposit a ";
		sql += " where a.nStatusID=2 ";
 		
		if (lDepositTypeID == 1)
		{
			sql += " and a.nTransactionTypeID=12";
		}
		else if (lDepositTypeID == 2)
		{
			sql += " and a.nTransactionTypeID=15"; 			
		}
		else if (lDepositTypeID == 3)
		{
			sql += " and a.nTransactionTypeID=301"; 			
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
			sql += " and a.sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.sDepositNo";
		
		return sql;
	}
	else if (nTypeID == 3)
	{
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtOpen, 'yyyy-mm-dd') InterestDate";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID in (1,7) and ma.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 2 ";
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
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;
	}
	else if (nTypeID == 21 || nTypeID == 22)  //jzw 2010-04-21 sett_TransOpenFixedDeposit
	{
		var sql = "select distinct a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,decode(a.AF_nDepositTerm,-1,af_nnoticeday,a.AF_nDepositTerm) Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtOpen,'yyyy-mm-dd') InterestDate ";
		//var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ,c.id ClientID ,c.scode ClientNO,c.sname ClientName,ma.id AccountID,ma.saccountno AccountNO, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3"; //中交修改 by ruiwang
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		//sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat ,client c ";//中交修改 by ruiwang
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 " ;
		sql += " and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
		if (nTypeID == 21)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) >= 0";
		}
		else if (nTypeID == 22)
		{
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>=0 ";
		}
		if (lDepositTypeID == 1)
		{
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;
	}
	else if (nTypeID == 302 || nTypeID == 303)
	{
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		//var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ,c.id ClientID ,c.scode ClientNO,c.sname ClientName,ma.id AccountID,ma.saccountno AccountNO, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3"; //中交修改 by ruiwang
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat, loan_assurechargeform b, loan_contractform c";
		//sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat ,client c ";//中交修改 by ruiwang
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 " ;
		sql += " and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		
		if (nTypeID == 302)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) >= 0";
		}
		else if (nTypeID == 303)
		{
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>=0 ";
		}
		
		if (lDepositTypeID == 1)
		{
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";			
		}
		sql += " and a.al_nloannoteid=b.id";
		sql += " and b.contractId=c.id";
		sql += " and c.ntypeid=8";	
		sql += " order by a.af_sDepositNo ";
		
		return sql;					
	}
	else
	{
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID ";
		sql += " and ma.nAccountTypeID = sat.id and sat.nStatusID=1";
 		
 		if(lStatusID > 0)
 		{
 			sql += " and a.nStatusID = " + lStatusID;
 		}
 		else
 		{
 			sql += " and a.nStatusID>0 ";
 		}
 		
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			sql += " and sat.nAccountGroupID=2";
		}
		else if (lDepositTypeID == 2)
		{
			sql += " and sat.nAccountGroupID=3";
		}
		else if (lDepositTypeID == 6)
		{
			sql += " and sat.nAccountGroupID=11";
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3,11)";
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID;
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;
	}
}

/**
*存单查询放大镜。由于资金结算-账户类型编码设置中需要依据客户编号查询存单，因此需要单独增加方法进行查询
*2008-11-12增加 kaishao 
*/
function getFixedDepositNoSQLSpecial(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nClientID,sDepositNo,sSystemDate)
{
	//增加定期续存存单内容 2008-11-18No.172修改 kaishao
	var sql = "select SubAccountID,DepositNo,EndDate,OpenDate,Capital,Balance,AccountID,Rate,Interval,StartDate ";
		sql +="from(";
		sql +="select -1 SubAccountID,";
		sql +="		dt.sDepositNo DepositNo,";
		sql +="		dt.nStatusID nStatusID,";
		sql +="		dt.nTransactionTypeID nTransactionTypeID,";
		sql +="		dt.nofficeid nofficeid,";
		sql +="		dt.nCurrencyID nCurrencyID,";
		sql +="		dt.nAccountID nAccountID,";
		sql +="		dt.nClientID nClientID,";
		sql +="		to_char(dt.dtEnd,'yyyy-mm-dd') EndDate,";
		//修改SQL中的错误，显示存单开立日 2008-11-18No.171修改 kaishao
		sql +="		to_char(dt.dtStart,'yyyy-mm-dd') OpenDate,";
		sql +="		0 Capital,";
		sql +="		0 Balance,";
		sql +="		dt.nAccountID AccountID,";
		sql +="		0 Rate,";
		sql +="		0 Interval,";
		sql +="		'' StartDate ";
		sql +="	from sett_TransOpenFixedDeposit dt ";
		sql +="union ";
		sql +="	select -1 SubAccountID,";
		sql +="		co.snewDepositNo DepositNo,";
		sql +="		co.nStatusID nStatusID,";
		sql +="		co.nTransactionTypeID nTransactionTypeID,";
		sql +="		co.nofficeid nofficeid,";
		sql +="		co.nCurrencyID nCurrencyID,";
		sql +="		co.nAccountID nAccountID,";
		sql +="		co.nClientID nClientID,";
		sql +="		to_char(co.DTNEWEND,'yyyy-mm-dd') EndDate,";
		//修改SQL中的错误，显示存单开立日 2008-11-18No.171修改 kaishao
		sql +="		to_char(co.DTNEWSTART,'yyyy-mm-dd') OpenDate,";
		sql +="		0 Capital,";
		sql +="		0 Balance,";
		sql +="		co.nAccountID AccountID,";
		sql +="		0 Rate,";
		sql +="		0 Interval,";
		sql +="		'' StartDate "; 
		sql +="	from sett_transfixedcontinue co) ";
		sql += " where nStatusID <> 0 ";
	if (lDepositTypeID == 1)
	{
		//定期开立或定期续存
		sql += " and ((nTransactionTypeID = 12) or (nTransactionTypeID = 14))"; 
	}
	else if (lDepositTypeID == 2)
	{
		//通知开立
		sql += " and nTransactionTypeID=15"; 			
	}
	if (nOfficeID > 0)
	{
		sql += " and nofficeid = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID; 
	}
	if (nAccountID > 0)
	{
		sql += " and nAccountID = " + nAccountID; 
	}
	if (nClientID > 0)
	{
		sql += " and nClientID = " + nClientID; 
	}
	if (sDepositNo != null && sDepositNo != "") 
	{
		sql += " and DepositNo like '%" + sDepositNo + "%'";
	}
	sql += " order by DepositNo";
	//修改结束
	return sql;
}

/**
*中交项目，通知支取指令，通知存款单据号放大镜
*/
function getFixedDepositNoSQLForZJ(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate,lTransType)
{
	var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate,to_char(s.dtintereststart, 'yyyy-mm-dd') InterestDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat,Sett_TransOpenFixedDeposit s";
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 ";
		sql += "and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		sql += " and s.sdepositno = a.af_sdepositno ";
 		sql += " and s.nstatusid != 0 ";
 		if(lTransType&&lTransType>0)
 		{
 			sql += " and s.ntransactiontypeid ="+lTransType;
 		}

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
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			//保证金存款
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
}
/**
*中交项目 定期续存专用放大镜！！
*/
function getFixedDepositNoSQLZJ(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
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
		else if (lDepositTypeID == 3)
		{
			//保证金开立
			sql += " and a.nTransactionTypeID=301"; 			
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
			sql += " and a.sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.sDepositNo";
		
		return sql;
	}
	else if (nTypeID == 22 || nTypeID == 21)
	{
		//定期（通知）支取--业务处理 或 复核时使用
		
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 ";
		sql += "and sat.nStatusID=1 and ma.nAccountTypeID=sat.id";
 		
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
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 
		}
		else if (lDepositTypeID == 6)
		{
			//保证金存款
			sql += " and sat.nAccountGroupID=11"; 
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else if (nTypeID == 3)
	{
		//定期续期转存--业务处理时使用
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.AF_DTSTART,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,to_char(a.mBalance,'999,999,999,999,999,999.00') Balance,ma.ID AccountID,to_char(AF_mRate,'999,999.000000') Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate, c.sCode  ClientNo,c.sname  ClientName,ma.sAccountNo  AccountNo, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3,1 as FromClient,c.ID as ClientID,c.ID as ClientID,sat.naccountgroupid as accountgroupid";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat,client c";
		sql += " where a.nAccountID=ma.ID and a.nStatusID in (1,7) and ma.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 2 and ma.nClientID=c.ID";
 		
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;
	
	}
	else
	{
		//显示全部
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID>0 ";
		sql += " and ma.nAccountTypeID = sat.id and sat.nStatusID=1";
 		
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and sat.nAccountGroupID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and sat.nAccountGroupID=3"; 			
		}
		else
		{
			sql += " and sat.nAccountGroupID in (2,3)";
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}	

}

/**
 * 办事处放大镜
 * sOfficeNo　办事处编号
 */
function getOfficeSQL(sOfficeNo)
{
	var sql = "select distinct a.id as officeid, a.sCode as OfficeNo, a.sname as officeName ";
	sql += " from office a ";
	sql += " where a.nStatusID > 0 ";
	 
	if (sOfficeNo != null && sOfficeNo != "") 
	{
		sql += " and a.sCode like '%" + sOfficeNo + "%' or a.sname like '%"+sOfficeNo+"%'";
	}
	sql += " order by a.sCode";
	
	return sql;
}
/**
 * 利率放大镜
 * sRateOrRateName 利率值或利率名称
 */
function getInterestRateSQL(nOfficeID,nCurrencyID,sRateOrRateName)
{
	var sql = "select distinct sName InterestRateName,ID InterestRateID,fRate InterestRate ";
	sql += " from sett_InterestRate ";
	sql += " where nStatusID=1 ";

	if (nOfficeID > 0)
	{
		sql += " and nofficeid = " + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID; 
	}
	if (sRateOrRateName != null && sRateOrRateName != "") 
	{
		//alert (sRateOrRateName);
		//alert (isFloat(sRateOrRateName));
		//alert (reverseFormatAmount1(sRateOrRateName));
		//alert (isFloat(reverseFormatAmount1(sRateOrRateName)));
		
		if (isFloat(sRateOrRateName))
		{
			sql += " and (fRate=" + sRateOrRateName +" or sName like '%" + sRateOrRateName + "%')";		
		}
		else
		{
			sql += " and sName like '%" + sRateOrRateName + "%'";		
		}
		/*
		sql += " and sName like '%" + sRateOrRateName + "%'";		
		*/
	}
	sql += " order by sName";
	
	return sql;
}

/**
 * 证券投资结算--证券业务通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nPayOrReceive 收款/付款（1,收款;2,付款）
 * sSecNoticeType 证券交易（通知单）类型；如果是-100，则表明是财务公司收付款
 * nSecNoticeStatus 通知单状态
 * sSecNoticeFormNo 通知单编号
 */
function getSecNoticeFormSQL(nOfficeID,nCurrencyID,sPayOrReceive,sSecNoticeType,sSecNoticeStatus,sSecNoticeFormNo)
{
	var sql = "select NtcID,";//证券业务通知单ID
	sql += "NtcNo,";//证券业务通知单编号 
	sql += "STTDesc,";//证券业务类型描述
	sql += "CBID,";//财务公司开户行ID
	sql += "CBNm,";//财务公司开户行名称
	sql += "CBANo,";//财务公司银行账号
	sql += "CBANm,";//财务公司银行账户名称
	sql += "OBNm,";//交易对手开户行名称
	sql += "OBNo,";//交易对手银行账号
	sql += "OBANm,";//交易对手银行账户名称
	sql += "PAmt,";//应收付款金额
	sql += "RAmt,";//实际收付款金额
	sql += "PRDate,";//收付款日期
	sql += "Abstract,";//摘要
	sql += "RPType,";//收付款类型
	sql += "CAID,";//活期账户ID
	sql += "CANo,";//活期账户编号
	sql += "CCNm, ";//活期客户名称
	sql += "CPName ";//交易对手名称
	sql += "from SETT_VSECNoticeForm ";//
	sql += "where 1=1 ";//
	
    if (nOfficeID > 0)
	{
		sql += " and OfficeID=" + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and CurrencyID=" + nCurrencyID; 
	}
	if (sPayOrReceive != null && sPayOrReceive != "-1") 
	{
		sql += " and CptlDir in ("+sPayOrReceive+")"; 
	}
	if (sSecNoticeType != null && sSecNoticeType == "-100") 
	{
   		sql += " and TransactionTypeID not in (7302,7303,7304,8102,8103,8104)"; 
	}
	else if (sSecNoticeType != null && sSecNoticeType != "-1") 
	{
   		sql += " and TransactionTypeID in ("+sSecNoticeType+")"; 
	}
 
	if (sSecNoticeStatus != null && sSecNoticeStatus != "-1") 
	{
   		sql += " and StatusID in ("+sSecNoticeStatus+")"; 
	}
	if (sSecNoticeFormNo != null && sSecNoticeFormNo != "") 
	{
		sql += " and NtcNo like '%" + sSecNoticeFormNo + "%'";
	}
	sql += " order by NtcNo";
	
	return sql;
}


/**
 * 银企接口--银行帐户编号放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nBankTypeID 银行类型ID
 * nBankAccountNo 银行帐户编号
 */
function getBankAccountNoSQL(nOfficeID,nCurrencyID,nBankTypeID,nBankAccountNo)
{
	var sql = " select distinct b.ID BankAccountID,b.sBankAccountCode BankAccountNo,b.nBankType BankTypeID ";
	sql += " from sett_branch b ";
	sql += " where b.nStatusID>0 and b.sBankAccountCode is not null ";

    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID; 
	}
	if (nBankTypeID > 0) 
	{
		sql += " and b.nBankType = " + nBankTypeID; 
	}
	if (nBankAccountNo != null && nBankAccountNo != "") 
	{
		sql += " and b.sBankAccountCode like '%" + nBankAccountNo + "%'";
	}
	
	sql += " order by b.sBankAccountCode";
	
	return sql;
}

/**
 * 银企接口--电厂帐户编号放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nBankTypeID 银行类型ID
 * nClientID 所属单位ID
 * nBankAccountNo 银行帐户编号
 */
function getFilialeAccountNoSQL(nOfficeID,nCurrencyID,nBankTypeID,nClientID,nBankAccountNo)
{
	var sql = " select distinct b.ID ID,b.SBANKACCOUNTNO BANKACCOUNTNO,b.NBNKTYPE BANKTYPE,b.SBANKACCOUNTNAME BANKACCOUNTNAME,b.NCLIENTID CLIENTID,b.NACCOUNTTYPE ACCOUNTTYPE,b.SFILIALENAME FILIALENAME ";
	sql += " from SETT_BANKACCOUNTOFFILIALE b ";
	sql += " where b.SBANKACCOUNTNO is not null ";
	
	if (nCurrencyID > 0) 
	{
		sql += " and b.NCURRENCYID = " + nCurrencyID; 
	}
	if (nBankTypeID > 0) 
	{
		sql += " and b.NBNKTYPE = " + nBankTypeID; 
	}
	if (nClientID > 0) 
	{
		sql += " and b.NCLIENTID = " + nClientID; 
	}
	if (nBankAccountNo != null && nBankAccountNo != "") 
	{
		sql += " and b.SBANKACCOUNTNO like '%" + nBankAccountNo + "%'";
	}
	
	sql += " order by b.SBANKACCOUNTNO";
	
	return sql;
}


/**
 * 上级单位2放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
function getSuperCorp2SQL(nOfficeID,sParentCorpNo)
{
	var sql = " select distinct c1.nParentCorpID2 ParentCorpID,c2.sCode ParentCorpNo,c2.sName ParentCorpName ";
	sql += " from client c1,client c2 ";
	sql += " where c1.nParentCorpID2=c2.ID and c1.nStatusID > 0";

    if (nOfficeID > 0)
	{
		sql += " and c1.nOfficeID = " + nOfficeID; 
	}
	if (sParentCorpNo != null && sParentCorpNo != "") 
	{
		sql += " and c2.sCode like '%" + sParentCorpNo + "%'";
	}
	sql += " order by c2.sCode";
	
	return sql;
}
 
/**
 * 上级单位1放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
function getSuperCorp1SQL(nOfficeID,sParentCorpNo)
{
	var sql = " select distinct c1.nParentCorpID1 ParentCorpID,c2.sCode ParentCorpNo,c2.sName ParentCorpName ";
	sql += " from client c1,client c2 ";
	sql += " where c1.nParentCorpID1=c2.ID and c1.nStatusID > 0";

    if (nOfficeID > 0)
	{
		sql += " and c1.nOfficeID = " + nOfficeID; 
	}
	if (sParentCorpNo != null && sParentCorpNo != "") 
	{
		sql += " and c2.sCode like '%" + sParentCorpNo + "%'";
	}
	sql += " order by c2.sCode";
	
	return sql;
}
 
/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
function getParentCorpSQL(nOfficeID,sParentCorpNo)
{
	var sql = " select distinct c1.nParentCorpID1 ParentCorpID,c2.sCode ParentCorpNo,c2.sName ParentCorpName ";
	sql += " from client c1,client c2 ";
	sql += " where c1.nParentCorpID1=c2.ID and c1.nStatusID > 0";

    if (nOfficeID > 0)
	{
		sql += " and c1.nOfficeID = " + nOfficeID; 
	}
	if (sParentCorpNo != null && sParentCorpNo != "") 
	{
		sql += " and c2.sCode like '%" + sParentCorpNo + "%'";
	}
	sql += " order by c2.sCode";
	
	return sql;
}

/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getClientSQL(nOfficeID,sClientNo)
{
	var sql = "select * from (select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0 and a.nofficeid="+nOfficeID;
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}	
	sql += " order by a.sCode)";
	sql += " union all ";
	sql += " select * from (select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id  and a.nStatusID > 0  and a.nofficeid<>"+nOfficeID;
	sql += " and (a.ISINSTITUTIONALCLIENT <= 0 or a.ISINSTITUTIONALCLIENT is null)";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}	
	sql += "  order by OfficeID,a.sCode ) ";
	return sql;
}

function getClientSQL_OLD(nOfficeID,sClientNo,name)
{
	var sql = "select * from (select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0 and a.nofficeid="+nOfficeID;
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}	
	sql += " order by a.sCode)";
	sql += " union all ";
	sql += " select * from (select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id  and a.nStatusID > 0  and a.nofficeid<>"+nOfficeID;
	sql += " and (a.ISINSTITUTIONALCLIENT <= 0 or a.ISINSTITUTIONALCLIENT is null)";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}	
	if(name !=null && name.length > 0){
		sql += " and name like '%"+name+"%'";
	}
	sql += "  order by OfficeID,a.sCode ) ";
	return sql;
}

/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getStockClientSQL(nOfficeID,sClientNo,lclientid)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient,a.sEngname as EngName ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}
	if(lclientid!=-1)
	{
		sql += " and a.ID <>"+lclientid
	}
	//added by ryping on 07-08-18
	sql += " and a.clientbasetype = 1 " ;
	//end
	sql += " order by a.sCode";
	
	return sql;
}


/**
 * 外部单位放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getExtStockClientSQL(sClientNo,lofficeid,lcurrenctid)
{
	var sql = "select distinct  a.code as ClientNo, a.ID as ClientID,a.name as ClientName,a.engname as EngName,a.linkman as linkman,a.tel as telephone ";
	sql += " from client_extclientinfo a ";
	sql += " where a.StatusID = 1";
	if(lofficeid>0)
	{
		sql +=" and a.officeid ="+lofficeid;
	}
	if(lcurrenctid>0)
	{
		sql +=" and a.currencyid ="+lcurrenctid;
	}
	
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.code like '%" + sClientNo + "%')";
	}
	sql += " order by a.Code";
	return sql;
}

function getClient_InfoSQL(nOfficeID,sClientNo){
	var sql = "select distinct  a.Code as ClientNo, a.ID as ClientID,a.name as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client_clientinfo a, office b ";
	sql += " where a.officeid = b.id and a.StatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.officeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.Code like '%" + sClientNo + "%' or a.Name like '%" + sClientNo + "%')";
	}
	sql += " order by a.Code";
	return sql;
}
/**
 * 客户放大镜(带客户所属企业类型)
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getClientSQL2(nOfficeID,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += ",e.sName as EnterpriseTypeName";
	sql += " from client a, office b, SETT_ENTERPRICETYPE e ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}
	sql += " and a.nCorpNatureID = e.id(+)";
	sql += " order by a.sCode";
	
	return sql;
}
function getInterestRateSQLBEIHF(nOfficeID,nCurrencyID,sRateOrRateName,nfixeddepositmonthid,nAccounttypeID)
{
	var sql = "select distinct sName InterestRateName,Dteffective dteffective,ID InterestRateID,LTRIM(RTRIM(to_char(fRate,'B999990.000000'))) InterestRate,  nfixeddepositmonthid,nAccounttypeID";
	sql += " from sett_InterestRate ";
	sql += " where nStatusID=1 ";

	if (nOfficeID > 0)
	{  
		sql += " and nofficeid = " + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID; 
	}
	if (nfixeddepositmonthid != null && nfixeddepositmonthid != "-1") 
	{
		//alert (sRateOrRateName);
		//alert (isFloat(sRateOrRateName));
		//alert (reverseFormatAmount1(sRateOrRateName));
		//alert (isFloat(reverseFormatAmount1(sRateOrRateName)));
		
		if (isFloat(sRateOrRateName))
		{
			sql += " and nfixeddepositmonthid=" + nfixeddepositmonthid ;		
		}
		else
		{
			sql += " and nfixeddepositmonthid =" + nfixeddepositmonthid ;		
		}
	}
	if(sRateOrRateName!="")
	{
		var temp = parseFloat(sRateOrRateName);
		if(temp.toString().indexOf("0",0)==0)
		{
			temp = temp.toString().substring(temp.toString().indexOf(".",0),temp.toString().length);
		}
		sql += " and fRate  like '" + temp+"%'" ;	
	}
	sql += " and nAccounttypeID like '%" + nAccounttypeID + "%'";		
	sql += " order by DTEFFECTIVE desc";
	
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
function getAccountRtnDepostNoSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,a.mMinSinglePayAmount MinSinglePayAmount,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName,'FromConstant_7_AccountID' as DepositNo ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
    sql += " where a.nStatusID=1 and a.nCheckStatusID=4 and a.nClientID=c.ID and a.nAccountTypeID=at.ID";
	
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
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
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
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

//Modify by leiyang  date 2007-6-12
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
function getAccountExtendRtnDepostNoSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,a.mMinSinglePayAmount MinSinglePayAmount,ae.IsSoft,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName,'' as DepositNo ";
	sql += " from sett_Account a, sett_AccountExtend ae, Client c,sett_AccountType at ";
    sql += " where a.nStatusID=1 and a.nCheckStatusID=4 and a.ID=ae.AccountID(+) and a.nClientID=c.ID and a.nAccountTypeID=at.ID";
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
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
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
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	//document.write(sql);
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
 /*
function getAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By Gqfang 04-03-02
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID ";
	*/
	//edit by rxie for nhcw
	/*
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName ,decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance";
	sql += ",ba.S_ACCOUNTNO outAcctNo,ba.S_ACCOUNTNAME outAcctName,bb.S_BRANCHAREASEG1 province,bb.S_BRANCHAREASEG2 city,bb.S_NAME openBankName";
	sql += " from sett_Account a, Client c,sett_AccountType at ,bs_bankaccountinfo ba,bs_banksetting bb,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and ba.N_SUBJECTID(+)=a.id and bb.n_id(+)=ba.N_BANKID and    a.id = ss.naccountid(+)";

	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4 ";
	}
	else
	{
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}
*/

//账户放大镜过滤字段：
//客户id、国家id、银行类型id、币种id、所有者类型id、收支属性id、是否直连、帐户属性一、二、三、审核状态、帐户状态
//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,Two,Three,isCheck,accountStatus
//officeID 用户的办事处
//flag 判断是否为总行
//add by xfma 2008-10-25
function getAccountSQLForBP(sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
		       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName,a.n_inputoroutput as Inputoroutput,b.s_name as BankName";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid=b.n_id ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%" + sAccountNO + "%'" + " or a.s_accountname like '%" + sAccountNO + "%'"+" )";
	}
	if(officeID != null && officeID != '-1')
	{
		sql += " and a.n_officeid = " + officeID;
	}
	if (clientId != null && clientId.length > 0 && clientId != '-1')
	{
		sql += " and a.n_clientId = " + clientId + " ";
	}
	if (countryId != null && countryId.length > 0 && countryId != '-1')
	{
		sql += " and a.n_countryId = " + countryId + " ";
	}
	if (bankId != null && bankId.length > 0 && bankId != '-1')
	{
		sql += " and a.n_bankId = " + bankId + " ";
	}
	if (currencyType != null && currencyType.length > 0 && currencyType != '-1')
	{
		sql += " and a.n_currencyType = " + currencyType + " ";
	}
	if (ownerType != null && ownerType.length > 0 && ownerType != '-1' )
	{
		sql += " and a.n_ownerType = " + ownerType + " ";
	}
	if (inputOrOutput != null && inputOrOutput.length > 0 && inputOrOutput != '-1')
	{
		sql += " and a.n_inputOrOutput = " + inputOrOutput + " ";
	}
	if (isDirectLink != null && isDirectLink.length > 0 && isDirectLink != '-1')
	{
		sql += " and a.n_isDirectLink = " + isDirectLink + " ";
	}
	if (accountPropertyOne != null && accountPropertyOne.length > 0 && accountPropertyOne != '-1')
	{
		sql += " and a.n_accountPropertyOne = " + accountPropertyOne + " ";
	}
	if (accountPropertyTwo != null && accountPropertyTwo.length > 0 && accountPropertyTwo != '-1')
	{
		sql += " and a.n_accountPropertyTwo = " + accountPropertyTwo + " ";
	}
	if (accountPropertyThree != null && accountPropertyThree.length > 0 && accountPropertyThree != '-1')
	{
		sql += " and a.n_accountPropertyThree = " + accountPropertyThree + " ";
	}
	if (isCheck != null && isCheck.length > 0 && isCheck != '-1')
	{
		sql += " and a.n_isCheck = " + isCheck + " ";
	}
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and a.n_accountStatus = " + accountStatus + " ";
	}	
	if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != 'all')
	{
		sql += " and b.s_referencecode = '" + dirBankRefcode + "' ";
	}	
	sql += " order by a.s_accountno ";
	
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

function getAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " (ss.mbalance - ss.muncheckpaymentamount) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";

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
		//wcl 20120904 查询可用账户状态不应包含销户状态
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (nAccountTypeID!=null&&nAccountTypeID!=''&&nAccountTypeID!=-1&&nAccountTypeID!=-100)
	{
		sql += " and a.nAccountTypeID in (" + nAccountTypeID+") ";
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and at.nAccountGroupID in (4,5) ";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}	
    if (nClientID != null && nClientID > 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
    return sql;
}
/**
**为了兼容同业模块放大镜 add by xiangzhou 2013 
**/
function getAccountSQL_old(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " (ss.mbalance - ss.muncheckpaymentamount) mBalance"
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
		//wcl 20120904 查询可用账户状态不应包含销户状态
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (nAccountTypeID!=null&&nAccountTypeID!=''&&nAccountTypeID!=-1&&nAccountTypeID!=-100)
	{
		sql += " and a.nAccountTypeID in (" + nAccountTypeID+") ";
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and at.nAccountGroupID in (4,5) ";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}	
    if (nClientID != null && nClientID > 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%25" + sAccountNo + "%25'";
	}
	sql += " order by a.sAccountNo";
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

function getAccountSQLNew(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,nClientID1,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance";
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";

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
	if (nClientID > 0 && nClientID1 > 0)
	{	
		sql += " and (a.nClientID = "+nClientID +" or a.nClientID="+nClientID1+")";
	}else if (nClientID > 0 && nClientID1 < 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
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

function getNegotiateAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";
	sql += " and ss.ac_nisnegotiate = 1 and a.nStatusID in(1,2,3,7,8) and ss.nStatusID in (1,2,3,4,5,6,7,8)";

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
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

function getNegotiateAccountSQL_OLD(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(%2B)";
	sql += " and ss.ac_nisnegotiate = 1 and a.nStatusID in(1,2,3,7,8) and ss.nStatusID in (1,2,3,4,5,6,7,8)";

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
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%25" + sAccountNo + "%25'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}
/**
*
*中交，下划成员单位使用付款方放大镜
*
*/
function getAccountSQL1(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By Gqfang 04-03-02
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID ";
	*/
	//edit by rxie for nhcw
	var sql = "select a.ID AID,a.sAccountNo ANo,a.sName AN,c.ID ClID,at.naccountgroupid actgroupid,c.sCode ClNo,c.sName ClN,decode((ss.mbalance-ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance-ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99')) mB";
	sql += ",bs.o1 o1,bs.o2 o2,bs.p p,bs.c c,bs.o3 o3";
	sql += " from sett_Account a,Client c,sett_AccountType at,";
	sql += "(select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid=1 and sub.nStatusID>0) ss";
	sql += ",(select ba.N_SUBJECTID,ba.S_ACCOUNTNO o1,ba.S_ACCOUNTNAME o2,bb.S_BRANCHAREASEG1 p,bb.S_BRANCHAREASEG2 c,bb.S_NAME o3";
	sql += " from bs_bankaccountinfo ba,bs_banksetting bb";
	sql += " where bb.n_id(+)=ba.N_BANKID and ba.n_inputoroutput=2) bs";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and a.id=ss.naccountid(+) and bs.N_SUBJECTID(+)=a.id";
	

	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4";
	}
	else 
	{
	    sql += " and a.nStatusID in (1,2,3,4,7,8) and a.nCheckStatusID=4";
	}
	
	if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID=" + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID=" + nCurrencyID;
	}
	if (nAccountGroupType == 1)
	{
		sql += " and at.nAccountGroupID in (1,7)";
	}
	else if (nAccountGroupType > 1)
	{
		sql += " and at.nAccountGroupID=" + nAccountGroupType;
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
		sql += " and a.nAccountTypeID=" + nAccountTypeID;
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and at.nAccountGroupID in (4,5)";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}	
    if (nClientID > 0)
	{
		sql += " and a.nClientID=" + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

/**
 * 开户行放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQL(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,decode(ab.sBankAccountNo,null,b.sBankAccountCode,ab.sBankAccountNo) BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab,sett_SubAccount sa ";
	    sql += " where b.nStatusID=1 and ab.nBankID(+)=b.ID and ab.nAccountID=sa.nAccountID and nvl(sa.ac_nIsAllBranch,0)<>1 and sa.nStatusID=1";
		sql += "   and ab.nAccountID = " + nAccountID;
		sql += " union ";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
		sql += " from sett_Branch b ";
		sql += " where b.nStatusID=1 and (select ac_nIsAllBranch from sett_subAccount where nStatusID>0 and nAccountID="+ + nAccountID+")=1 ";
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName like '%"+sBranchNoOrName+"%')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

/**
*为了兼容同业模块放大镜 add by xiangzhou 2013 
**/
function getBranchSQL_old(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,decode(ab.sBankAccountNo,null,b.sBankAccountCode,ab.sBankAccountNo) BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab,sett_SubAccount sa ";
	    sql += " where b.nStatusID=1 and ab.nBankID(%2B)=b.ID and ab.nAccountID=sa.nAccountID and nvl(sa.ac_nIsAllBranch,0)<>1 and sa.nStatusID=1";
		sql += "   and ab.nAccountID = " + nAccountID;
		sql += " union ";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
		sql += " from sett_Branch b ";
		sql += " where b.nStatusID=1 and (select ac_nIsAllBranch from sett_subAccount where nStatusID>0 and nAccountID="+ + nAccountID+")=1 ";
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
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
 * 开户行放大镜的SQL语句（中交）付款方处理
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQLForZj(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (b.sCode  like '%" + sBranchNoOrName + "%' or b.sName like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	return sql;
}

/**
*为了兼容同业模块放大镜 add by xiangzhou 2013 
**/
function getBranchSQLForZj_old(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNT(%2B)B)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (b.sCode  like '%25" + sBranchNoOrName + "%25' or b.sName like '%25"+ sBranchNoOrName+"%25')";
	}	
	sql += " order by BranchNo";
	return sql;
}


/**
 * 开户行放大镜的SQL语句（中交）付款方处理
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQLForZj1(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName  like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}
/**
 * 开户行放大镜的SQL语句（中交）付款方处理
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQLForZj2(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(%2b)=a.N_ID and a.S_ACCOUNTNO(%2b)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(%2b)=a.N_ID and a.S_ACCOUNTNO(%2b)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (BranchNo like '%" + sBranchNoOrName + "%' or BranchName like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}

/**
 * 开户行放大镜的SQL语句（中交）付款方处理
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQLForZj2(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (BranchNo like '%" + sBranchNoOrName + "%' or BranchName like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}
/**

**/
function getBranchSQLForZj2_old(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (b.sCode like '%25" + sBranchNoOrName + "%25' or b.sName like '%25"+ sBranchNoOrName+"%25')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}

function getBranchSQLForZj2_New(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}

/**
 * 因修改getBranchSQLForZj，对getBranchSQLForZj的备份sql
*/
function getBranchSQLForZjBak(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,decode(ab.sBankAccountNo,null,b.sBankAccountCode,ab.sBankAccountNo) BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";		 	
		sql += " from sett_Branch b,sett_AccountBank ab,sett_SubAccount sa,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
	    	sql += " where b.nStatusID=1 and ab.nBankID(+)=b.ID and ab.nAccountID=sa.nAccountID and nvl(sa.ac_nIsAllBranch,0)<>1 and sa.nStatusID=1";
		sql += " and ab.nAccountID = " + nAccountID;
		sql += " and cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode";//hy
		sql += " union";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 and (select ac_nIsAllBranch from sett_subAccount where nStatusID>0 and nAccountID="+ + nAccountID;
		sql += " )=1 and cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(+)=a.N_ID and a.S_ACCOUNTNO(+)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (BranchNo like '%" + sBranchNoOrName + "%' or BranchName like '%"+ sBranchNoOrName+"%')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}






/**
 * 开户行和银行账户编号放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nBankType
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchAndBankAccountNoSQL(nOfficeID, nCurrencyID,nIsSingleBank,nBankType,sBranchNoOrName)
{
	var sql = "";
		
	sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
	sql += " from sett_Branch b ";
	sql += " where b.nStatusID=1 ";
	
    if (nBankType > 0)
   	{
    		sql += " and b.NBANKTYPE = " + nBankType;	
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName like '%"+sBranchNoOrName+"%')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

/**
 * 现金流向放大镜
 * sCashFlow 现金流向代码或描述
 */
function getCashFlowSQL(sCashFlow)
{
    var sql = "select ID CashFlowID,sName CashFlowDesc, sMultiCode MultiCode ";
	sql += " from Sett_CashFlow ";
	sql += " where nStatusID=1";
	if(sCashFlow != null && sCashFlow.length > 0)
	{
	  sql += " and (sName like '%" + sCashFlow + "%' or sMultiCode like '%" + sCashFlow + "%')";
	}
	sql += " order by sMultiCode";
	
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
		sql += " and (sCode like '%" + sCodeOrDesc + "%' or sDesc like '%" + sCodeOrDesc + "%')";
	}
	sql += " order by sCode";
	
    return sql;
}
/**==========录入人放大镜===============*/
function getUserSQL(nOfficeID,nCurrencyID,sUserName)
{
    var sql = "select * from (select u.ID UserID, u.sName UserName,o.sname OfficeName";
	sql += "  from UserInfo u,office o ";
	sql += " where u.nofficeid=o.id  and u.nStatusID = 1 and u.nOfficeID = " + nOfficeID;
   // if (nCurrencyID > 0)
//	{
	//	sql += " and ','||u.sCurrencyID||','  like '%," + nCurrencyID + ",%'";
//	}	
	if(sUserName != null && sUserName.length > 0)
	{
	  sql += " and u.sName like '%" + sUserName + "%'";
	}
	sql += " order by u.sName )";
	sql += "  union all ";
	sql += "  select * from (select u.ID UserID, u.sName UserName,o.sname OfficeName";
	sql += " from UserInfo u,office o ";
	sql += "  where u.nofficeid=o.id and u.nStatusID = 1 and u.nOfficeID <> " + nOfficeID;
//	if (nCurrencyID > 0)
//	{
//		sql += " and ','||u.sCurrencyID||','  like '%," + nCurrencyID + ",%'";
//	}
	if(sUserName != null && sUserName.length > 0)
	{
	  sql += " and u.sName like '%" + sUserName + "%'";
	}
	sql += " order by u.sName )";
	
	return sql;
}
/**==========‘用户管理’页面【用户名称放大镜】===============*/
function getUserSQL_sys_userMng(nOfficeID,checkSta,uncheckSta,name){
	var sql = " select * from userinfo where nOfficeID = "+nOfficeID+" and nstatusid in ("+checkSta+","+uncheckSta+") ";
	if(name != null && name != ""){
	  sql +=" and (sname like '%"+name+"%' or id like '%"+name+"%' or sloginno like'%"+name+"%')";
	}
	
	return sql;
}
/**==========支票号放大镜===============*/
function getBankChequeNOSQL(nOfficeID,nCurrencyID,sBankChequeNO)
{
    var sql = "select b.sBankChequeNO  ";
	sql += " from (select t.nOfficeID,t.nCurrencyID,t.sBankChequeNO from Sett_TransCurrentDeposit t where t.nStatusID > 0 and t.sBankChequeno is not null ";
    sql += " union ";
    sql += " select t.nOfficeID,t.nCurrencyID,t.sBankChequeNO from Sett_TransSpecialOperation t where t.nStatusID > 0 and t.sBankChequeno is not null ";
    sql += " union ";
	sql += " select t.nOfficeID,t.nCurrencyID,t.sBankChequeNO from Sett_TransSecurities t where t.nStatusID > 0 and t.sBankChequeno is not null ";
    sql += " union ";
	sql += " select t.OfficeID nOfficeID,t.CurrencyID nCurrencyID,t1.BankCheckNO sBankChequeNO from sett_transcompatibility t, sett_transcompatibilitydetail t1 where t.StatusID > 0 and t1.BankCheckNO is not null and t.id = t1.compatibilityid) b ";
    sql += " where 1=1 ";
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID ;
	}
    if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID ;
	}	
	if(sBankChequeNO != null && sBankChequeNO.length > 0)
	{
	  sql += " and b.sBankChequeNO like '%" + sBankChequeNO + "%'";
	}
	sql += " order by b.sBankChequeNO";
	
	return sql;
}

/**==========报单号放大镜===============*/
function getsDeclarationNOSQL(nOfficeID,nCurrencyID,sDeclarationNO)
{
    var sql = "select b.sDeclarationNO  ";
	sql += " from (select t.nOfficeID,t.nCurrencyID,t.sDeclarationNO from Sett_TransCurrentDeposit t where t.nStatusID > 0 and t.sDeclarationNO is not null ";
    sql += " union ";
    sql += " select t.nOfficeID,t.nCurrencyID,t.sDeclarationNO from Sett_TransSpecialOperation t where t.nStatusID > 0 and t.sDeclarationNO is not null ";
    sql += " union ";
	sql += " select t.nOfficeID,t.nCurrencyID,t.sDeclarationNO from sett_TransOnePayMultiReceive t where t.nStatusID > 0 and t.sDeclarationNO is not null ";
    sql += " union ";
	sql += " select t.nOfficeID,t.nCurrencyID,t.sFormNO sDeclarationNO from Sett_TransSecurities t where t.nStatusID > 0 and t.sFormNO is not null ";
    sql += " union ";
	sql += " select t.OfficeID nOfficeID,t.CurrencyID nCurrencyID,t1.DeclarationNO sDeclarationNO from sett_transcompatibility t, sett_transcompatibilitydetail t1 where t.StatusID > 0 and t1.DeclarationNO is not null and t.id = t1.compatibilityid) b ";
    sql += " where 1=1 ";
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID ;
	}
    if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID ;
	}	
	if(sDeclarationNO != null && sDeclarationNO.length > 0)
	{
	  sql += " and b.sDeclarationNO like '%" + sDeclarationNO + "%'";
	}
	sql += " order by b.sDeclarationNO";
	
	return sql;
}
/**==========用户放大镜(网银用户)===============*/
function getObUserSQL(nOfficeID,nCurrencyID,sUserName)
{
    var sql = "select u.ID UserID,u.sName UserName ";
	sql += " from ob_user u ";
	sql += " where 1=1";
    if (nOfficeID > 0)
	{
		sql += " and u.nOfficeID = " + nOfficeID;
	}
    if (nCurrencyID > 0)
	{
		sql += " and ','||u.sCurrencyID||','  like '%," + nCurrencyID + ",%'";
	}	
	if(sUserName != null && sUserName.length > 0)
	{
	  sql += " and u.sName like '%" + sUserName + "%'";
	}
	sql += " order by u.sName";
	
	return sql;
}
/**
 * 银行票据放大镜
 * nBankID
 * nBillTypeID
 * sBankBillNo
 * nRequireClientID
 */
function getBankBillSQL(nBankID,nBillTypeID,sBankBillNo,nStatusID,nRequireClientID)
{
    var sql = " select bb.ID BillID, bb.sBillNo BillNo,bb.nBankID BankID,b.sName BankName,bb.nTypeID BillTypeID,'FromConstant_6_BillTypeID' BillTypeDesc,bb.nRequireClientID RequireClientID ";
	sql += " from sett_BankBill bb, sett_Branch b ";
	sql += "  where bb.nBankID=b.ID and bb.nIsReportLoss <>1 ";

    if (nBankID > 0)
	{
		sql += " and bb.nBankID = " + nBankID;
	}
    if (nBillTypeID > 0)
	{
		sql += " and bb.nTypeID = " + nBillTypeID;
	}
    if (nRequireClientID > 0)
	{
		sql += " and bb.nRequireClientID = " + nRequireClientID;
	}
	if (nStatusID > 0)
	{
		sql += " and bb.nStatusID = " + nStatusID;
	}
	else if (nStatusID == -1)
	{
		sql += " and bb.nStatusID in (1,2,3)";
	}
	if(sBankBillNo != null && sBankBillNo.length > 0)
	{
	  	sql += " and bb.sBillNo like '%" + sBankBillNo + "%'";
	}
	sql += " order by bb.sBillNo";
	
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
		sql += " and sExtAcctNo like '%" + sExtAcctNo + "%'";
	}
	sql += " order by sExtAcctNo";
	
    return sql;
}

/**
 * 外部帐户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部帐户编号
 */
function getExtAcctCurrencySQL(nOfficeID,nCurrencyID,sExtAcctNo)
{     
	var sql = "select ID ExtAcctID,sExtAcctNo ExtAcctNo,sExtAcctName ExtAcctName,sProvince ExtProvince,sCity ExtCity,sBankName ExtBankName, ";
    sql +=" SPayeeBankExchangeNO ExtPayeeBankExchangeNO, SPayeeBankCNAPSNO ExtPayeeBankCNAPSNO, SPayeeBankOrgNO ExtPayeeBankOrgNO ";
	sql += " from sett_ExternalAccount where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if(nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sExtAcctNo like '%25" + sExtAcctNo + "%25'";
	}
	sql += " order by sExtAcctNo";
	
    return sql;
}

function getExtAcctCurrencySQL_New(nOfficeID,nCurrencyID,sExtAcctNo)
{     
	var sql = "select ID ExtAcctID,sExtAcctNo ExtAcctNo,sExtAcctName ExtAcctName,sProvince ExtProvince,sCity ExtCity,sBankName ExtBankName, ";
    sql +=" SPayeeBankExchangeNO ExtPayeeBankExchangeNO, SPayeeBankCNAPSNO ExtPayeeBankCNAPSNO, SPayeeBankOrgNO ExtPayeeBankOrgNO ";
	sql += " from sett_ExternalAccount where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if(nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sExtAcctNo like '%" + sExtAcctNo + "%'";
	}
	sql += " order by sExtAcctNo";
	
    return sql;
}

function getExtAcctCurrencySQL_New(nOfficeID,nCurrencyID,sExtAcctNo)
{     
	var sql = "select ID ExtAcctID,sExtAcctNo ExtAcctNo,sExtAcctName ExtAcctName,sProvince ExtProvince,sCity ExtCity,sBankName ExtBankName, ";
    sql +=" SPayeeBankExchangeNO ExtPayeeBankExchangeNO, SPayeeBankCNAPSNO ExtPayeeBankCNAPSNO, SPayeeBankOrgNO ExtPayeeBankOrgNO ";
	sql += " from sett_ExternalAccount where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if(nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sExtAcctNo like '%" + sExtAcctNo + "%'";
	}
	sql += " order by sExtAcctNo";
	
    return sql;
}
/**
 * 外部客户放大镜
 * nOfficeID 办事处ID
 * sExtClientName  外部客户名称
 * sExtAcctNo 外部帐户编号
 */
function getExtClientSQL(nOfficeID,sExtClientName,sExtAcctNo)
{      
	var sql = "select ID ExtAcctID,sExtAcctNo ExtAcctNo,sExtAcctName ExtAcctName,sProvince ExtProvince,sCity ExtCity,sBankName ExtBankName, ";
    sql +=" SPayeeBankExchangeNO ExtPayeeBankExchangeNO, SPayeeBankCNAPSNO ExtPayeeBankCNAPSNO, SPayeeBankOrgNO ExtPayeeBankOrgNO ";
    sql += " from sett_ExternalAccount where nOfficeID is not null ";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (sExtClientName != null && sExtClientName.length > 0)
	{
		sql += " and sExtAcctName like '%" + sExtClientName + "%'";
	}
	if (sExtAcctNo != null && sExtAcctNo.length > 0)
	{
		sql += " and sExtAcctNo like '%" + sExtAcctNo + "%'";
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
		sql += " and sName like '%" + sRemitInBankName + "%'";
	}
	sql += " order by sName";
	
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
		sql += " and sSubjectCode  like '%" + sGLSubjectCode + "%'";
	}
	sql += " order by GLSubjectCode ";
	
    return sql;
}
/**
 * 转贴现科目放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGLSubjectCode 科目代码
 * nIsleaf 是否末级:0,否；1，是。
 * nSubjectType 科目类型
 */
function getZTXTypeSQL(nOfficeID,nCurrencyID,nIsleaf)
{      
	var sql = "select id , sSubjectCode sCode, sSubjectName sName";
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
		sql = " and nIsleaf = 1";
	}
	else
	{
		sql += " and nIsleaf = 1";
	}

	
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
		sql += " and sSubjectCode like '%" + sSubjectCode + "%'";
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
		sql += " and (sGeneralLedgerCode like '%" + sGeneralLedgerCode + "%' or sName like '%" + sGeneralLedgerCode + "%')";
	}
	sql += " order by GLCode";
	
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
    var sql="";
    if(sTypeIDArray==80)
	{
		sql = "select contract.ID ContractID,contract.sContractCode ContractCode,contract.discountclientid ClientID, client.sname ClientName,client.scode ClientCode,contract.nTypeID ContractType,nvl(nChargeRateTypeID,-1) CommisionDealType ";
	}else{
	    sql = "select distinct contract.ID ContractID,contract.sContractCode ContractCode,contract.nBorrowClientID ClientID, client.sname ClientName,client.scode ClientCode,contract.nTypeID ContractType,nvl(nChargeRateTypeID,-1) CommisionDealType ";
   	    }
   	    sql +=" ,to_char(contract.mloanamount, '9,999,999,999,999,999.00') mExamineAmount,decode(contract.dtfactenddate,null,to_char(contract.DTENDDATE,'yyyy-mm-dd'),to_char(contract.dtfactenddate,'yyyy-mm-dd')) as dtEndDate,decode(contract.nTypeID,1,'自营贷款',2,'委托贷款',3,'贴现',4,'最高限额',5,'银团贷款'"; //jzw 2010-04-28 添加放大镜的显示列
		sql +=" ,6,'转贴现',7,'买方信贷',8,'担保',9,'其他',10,'融资租赁',11,'授信',12,'信贷资产转让') as nTypeID";
   	    
    sql += " from loan_contractform contract, client ";
    if(sTypeIDArray==80)
    {
    sql += " where contract.discountclientid=client.id and contract.nOfficeID is not null ";
    }else{
    sql += " where contract.nborrowclientid=client.id and contract.nOfficeID is not null ";
    }
    if (nOfficeID > 0)
	{
		sql += " and contract.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and contract.ncurrencyid = " + nCurrencyID;
	}
	if (sTypeIDArray != null && sTypeIDArray!="")
	{
		sql += " and contract.nTypeID in ("+sTypeIDArray+")";
	}
	if (sStatusIDArray != null && sStatusIDArray.length > 0)
	{
		sql += " and contract.nStatusID in ("+sStatusIDArray+")";
		//sql += " and contract.nStatusID >0";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and contract.sContractCode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		if(sTypeIDArray==80)
		{
			sql += " and contract.discountclientid = " + nClientID;
		}
		else
		{
			sql += " and contract.nborrowclientid = " + nClientID;
		}
	}
	sql += " order by contract.sContractCode";

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
function getContractNoCredenceSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
{      
	var sql = "select distinct contract.ID ContractID,contract.sContractCode ContractCode,contract.nBorrowClientID ClientID, client.sname ClientName,client.scode ClientCode,contract.nTypeID ContractType,nvl(nChargeRateTypeID,-1) CommisionDealType ";
   	sql +=" ,contract.mloanamount mExamineAmount,decode(contract.dtfactenddate,null,contract.DTENDDATE,contract.dtfactenddate) as dtEndDate,decode(contract.nTypeID,1,'自营贷款',2,'委托贷款',3,'贴现',4,'最高限额',5,'银团贷款'"; //jzw 2010-04-28 添加放大镜的显示列
		sql +=" ,6,'转贴现',7,'买方信贷',8,'担保',9,'其他',10,'融资租赁',11,'授信',12,'信贷资产转让') as nTypeID";
    sql += " from loan_contractform contract, client ";
    sql += " where contract.nborrowclientid=client.id and contract.nOfficeID is not null and contract.ID NOT IN (select ncontractid from loan_discountcredence where nstatusid<>0 ) ";
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
		//sql += " and contract.nStatusID >0";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and contract.sContractCode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and contract.nborrowclientid = " + nClientID;
	}
	sql += " order by contract.sContractCode";

    return sql;
}



/**
 * 担保合同放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
function getContractAssureSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
{      
    var sql = "select ";
        sql += "o.ID a,";
        sql += "o.sContractCode b,";
        sql += "r.id c,";
        sql += "r.sCode d,";
        sql += "r.sname e,";
        sql += "NVL(q.currentID,-1) f,";
        sql += "q.sAccountNo g,";
        sql += "nvl(q.id,-1) hn,";
        sql += "assureTYPEID2 hm,";
        sql += "'FromConstant_10_hm' h,";
        sql += "to_char(o.DTSTARTDATE,'YYYY-MM-DD') startDate,";
        sql += "NVL(o.NINTERVALNUM,-1)  i, ";
        sql += "to_char(o.DTENDDATE,'YYYY-MM-DD') endDate,";
        sql += "NVL(o.mLoanAmount,0.0) j,";
        sql += "NVL(q.amount1,0.0) k,";
        sql += "NVL(o.mLoanAmount,0.0) amount ";
        sql += "from loan_contractform o, ";
        sql += "("; 
        sql += "SELECT w.clientId,y.ID contractID,nvl(w.accountid,-1) currentID,w.accountid id,w.sAccountNo,sum(NVL(x.RecognizanceAmount,0.0)) amount1 ";
        sql += "FROM Loan_AssureChargeForm x, loan_contractform y, (select z.id accountid,c.id clientid,z.saccountno from sett_account z,client c where z.nclientID=c.id) w ";
        sql += "WHERE x.ContractID = y.ID and x.RECOGNIZANCEACCOUNTID=w.accountid(+) ";
        sql += "GROUP BY y.ID,w.sAccountNo,w.clientid,w.accountid";
        sql += ") q,client r ";
        sql += "where o.nBorrowClientID=r.id and q.contractID(+)=o.ID ";
    if (nOfficeID > 0)
	{
		sql += " and o.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and o.ncurrencyid = " + nCurrencyID;
	}
	if (sTypeIDArray != null && sTypeIDArray.length > 0)
	{
		sql += " and o.nTypeID in ("+sTypeIDArray+")";
	}
	if (sStatusIDArray != null && sStatusIDArray.length > 0)
	{
		sql += " and o.nStatusID in ("+sStatusIDArray+")";
		//sql += " and o.nStatusID >0";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and o.sContractCode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and o.nborrowclientid = " + nClientID;
	}
	sql += " order by o.sContractCode";
    return sql;
}

function getAheadPayFormSQL(nOfficeID,nCurrencyID,nPayFormID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{      
	var sql = "select a.id AheadRepayFormID,a.scode AheadRepayFormNo,b.sContractCode ContractNo,p.ID PayFormID,p.sCode PayFormNo,c.sCode ClientNo,a.mAmount Amount ";
    sql += " from loan_AheadRepayForm a,loan_contractform b,loan_PayForm p,Client c ";
	sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.nLoanPayNoticeID=p.ID ";
	
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}
	if (nPayFormID > 0)
	{
		sql += " and a.nLoanPayNoticeID = " + nPayFormID;
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
		sql += " and a.scode like '%" + sPayFormNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}

/**
 * 提前还款通知单
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 放款通知单号
 */ 
function getAheadPayAllSQL(nOfficeID,nCurrencyID,nPayFormID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{      
	var sql = "select a.id AheadRepayFormID,a.scode AheadRepayFormNo,b.id ContractID,b.sContractCode ContractNo,p.ID PayFormID,p.sCode PayFormNo,c.id ClientID,c.sCode ClientNo,a.mAmount Amount, (sa.mBalance-sa.mUncheckPaymentAmount) Balance, to_char(p.dtStart,'yyyy-mm-dd') dtStart ";
    	sql += " from loan_AheadRepayForm a,loan_contractform b,loan_PayForm p,Client c, sett_SubAccount sa, sett_Account account ";
	sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.nLoanPayNoticeID=p.ID and p.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID(+) and sa.nStatusID=1 ";
	
    	if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}
	if (nPayFormID > 0)
	{
		sql += " and a.nLoanPayNoticeID = " + nPayFormID;
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
		sql += " and a.scode like '%" + sPayFormNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}

/**
 * 免还通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sFreeFormNo 免还通知单号
 */ 
function getFreeFormSQL(nOfficeID,nCurrencyID,sStatusIDs,sFreeFormNo)
{      
	var sql = " select ff.ID FreeFormID,ff.sCode FreeFormNo,to_char(pf.dtStart,'YYYY-MM-DD') PFStartDate,ff.mFreeAmount FreeAmount,ff.mInterest FreeInterest,";
    sql += " c.ID ContractID,c.sContractCode ContractNo,pf.ID PayFormID,pf.sCode PayFormNo, ";
	sql += " client.ID ClientID,client.sCode ClientNo,client.sName clientName ";
	sql += " from loan_freeform ff,loan_payForm pf,loan_ContractForm c,Client client ";
	sql += " where ff.nPayFormID=pf.ID and ff.nContractID=c.ID and c.nBorrowClientID=client.ID ";
	
    if (nOfficeID > 0)
	{
		sql += " and c.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and c.nCurrencyID = " + nCurrencyID;
	}
	if (sStatusIDs != null && sStatusIDs.length > 0)
	{
		sql += " and ff.nStatusID in (" + sStatusIDs + ")";
	}
	if (sFreeFormNo != null && sFreeFormNo.length > 0)
	{
		sql += " and ff.sCode like '%" + sFreeFormNo + "%'";
	}
	sql += " order by ff.sCode";
	
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
		sql += " (select decode(count(*),0,0,1) from loan_FreeForm where nContractID="+nContractID+" and nStatusID=2) IsHasFree ";
	    	sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa,sett_Account account,sett_accountType sat ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID(+) ";
		sql += " and account.nAccountTypeID = sat.id ";
		sql += " and sat.nStatusID=1 ";
		sql += " and sat.nAccountGroupID in (4,5,12) ";

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
			sql += " and a.scode like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	else if (sStatusIDArray != null && sStatusIDArray == '-900')
	{
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID,(sa.mBalance-sa.mUncheckPaymentAmount) Balance, ";
		sql += " (select decode(count(*),0,0,1) from loan_FreeForm where nPayFormID=a.ID and nStatusID=2) IsHasFree ";
	    sql += " from (select id,scode,dtOutDate,dtStart,dtEnd,nContractID from LOAN_PAYFORM union select id,scode,null as dtOutDate,null as dtStart,null as dtEnd,nContractID from LOAN_DISCOUNTCREDENCE) a,loan_contractform b,Client c,sett_SubAccount sa,sett_Account account,sett_accountType sat,loan_FreeForm ff ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID and a.ID=ff.nPayFormID(+) ";
		sql += " and account.nAccountTypeID = sat.id ";
		sql += " and sat.nStatusID=1 ";
		sql += " and sat.nAccountGroupID in (4,5,6) ";

		//利息费用/特殊业务——业务处理
		sql += " and sa.nstatusid=1  ";
		
		
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
			sql += " and a.scode like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	else if( sStatusIDArray != null && sStatusIDArray == '-1000' )
	{
		//担保贷款收款通知单
		sql = "select a.id PayFormID,a.code PayFormCode,b.ID ContractID,to_char(a.executeDate,'yyyy-mm-dd') PayDate,to_char(a.executeDate,'yyyy-mm-dd') InterestStartDate,to_char(a.StartDate,'yyyy-mm-dd') StartDate,to_char(a.EndDate,'yyyy-mm-dd') EndDate,-1 SubAccountID,a.RECOGNIZANCEAMOUNT Balance,-1 IsHasFree ";
	    sql += " from loan_assurechargeform a,loan_ContractForm b,Client c ";
		sql += " where a.ContractID = b.id and b.nBorrowClientID=c.ID ";
		
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
			sql += " and a.ContractID = " + nContractID;
		}
		if (sTypeIDArray != null && sTypeIDArray.length > 0)
		{
			sql += " and b.nTypeID in ("+sTypeIDArray+")";
		}	
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			sql += " and a.StatusID in (3)";
		}
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and a.code like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.code";
		
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID,a.mamount Balance,-1 IsHasFree ";
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
			sql += " and a.scode like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}


/**
 * 担保放款通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 放款通知单号
 */ 
function getAssurePayFormSQL(nOfficeID,nCurrencyID,lCheckStatus,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	
		//担保贷款收款通知单
		sql = " select a.assurechargeamount Amount,a.id PayFormID,a.code PayFormCode,b.ID ContractID,to_char(a.executeDate,'yyyy-mm-dd') InterestStartDate,to_char(a.StartDate,'yyyy-mm-dd') StartDate,to_char(a.EndDate,'yyyy-mm-dd') EndDate,a.RECOGNIZANCEAMOUNT Balance,a.rate Rate,a.recognizanceaccountid PayAccountID ,d.saccountno PayAccountNo ,c.sname ClientName  ,";
		

		 sql += " decode(length(d.saccountno),12,substr(d.saccountno,0,2),0) PayAccountNo1,";
 		 sql += " decode(length(d.saccountno),12,substr(d.saccountno,4,2),0) PayAccountNo2,";
		 sql += " decode(length(d.saccountno),12,substr(d.saccountno,7,4),0) PayAccountNo3,";
		 sql += " decode(length(d.saccountno),12,substr(d.saccountno,12,1),0) PayAccountNo4 ";

	     sql += " from loan_assurechargeform a,loan_ContractForm b,Client c ,sett_account d ";
		 sql += " where a.ContractID = b.id and b.nBorrowClientID=c.ID and a.recognizanceaccountid = d.id ";
		
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
			sql += " and a.ContractID = " + nContractID;
		}
		if (sTypeIDArray != null && sTypeIDArray.length > 0)
		{
			sql += " and b.nTypeID in ("+sTypeIDArray+")";
		}	
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			//新增保存
			if (lCheckStatus == 1)
			{
				sql += " and a.StatusID = 3";
			}
			//修改保存
			if (lCheckStatus == 2)
			{
				sql += " and a.StatusID = 3";
			}
			//复核
			if (lCheckStatus == 3)
			{
				sql += " and a.StatusID = 4";
			}
		}
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and a.code like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.code";
		
    return sql;
	
}


/**
 * 保后处理通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nClientID 客户ID
 * sTypeIDArray 通知单类型
 * sPayFormNo 放款通知单号
 */ 
function getAssureManagementFormSQL(nOfficeID,nCurrencyID,lCheckStatus,nClientID,sAssureFormNo)
{   
	var sql = "";   
	
		sql = " select a.id AssureFormID,a.code AssureFormCode,b.ID ContractID,a.RECOGNIZANCEAMOUNT recognizanceAmount,a.recognizanceaccountid AssureAccountID ,d.saccountno AssureAccountNo ,c.sname ClientName,c.id ClientId,c.sCode ClientCode  ";
		

	  sql += " from loan_assuremanagementform a,loan_ContractForm b,Client c ,sett_account d ";
		sql += " where a.ContractID = b.id and b.nBorrowClientID=c.ID and a.recognizanceaccountid = d.id ";
		
		//新增保存
		if (lCheckStatus == 1)
		{
			sql += " and a.StatusID = 3 "; //3表示已复核;4表示已使用
		}
		//修改保存
		if (lCheckStatus == 2)
		{
			sql += " and a.StatusID = 3 ";
		}
		//复核
		if (lCheckStatus == 3)
		{
			sql += " and a.StatusID = 4 ";
		}
		if (nOfficeID > 0)
		{
			sql += " and b.nOfficeID = " + nOfficeID;
		}
		if (nCurrencyID > 0)
		{
			sql += " and b.nCurrencyID = " + nCurrencyID;
		}
		if (nClientID > 0)
		{
			sql += " and  b.nBorrowClientID = " + nClientID;
		}
		
		if (sAssureFormNo != null && sAssureFormNo.length > 0)
		{
			sql += " and a.code like '%" + sAssureFormNo + "%'";
		}
		sql += " order by a.code";
    return sql;
	
}

//Modify by leiyang date 2007/07/06
/**
 * 保后处理通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nClientID 客户ID
 */ 
function getAssureManagementFormSQL_01(nOfficeID,nCurrencyID,nClientID,sDepositNo)
{   
	var sql = " select a.id AssureFormID,a.code AssureFormCode,b.ID ContractID,a.RECOGNIZANCEAMOUNT recognizanceAmount,a.recognizanceaccountid AssureAccountID,d.af_sDepositNo DepositNo,e.saccountno AssureAccountNo,f.sname ClientName,f.id ClientId,f.sCode ClientCode";
	sql += " from loan_assuremanagementform a,loan_contractform b,loan_assurechargeform c,sett_subaccount d,sett_account e,Client f";
	sql += " where b.id = a.contractid";
	sql += " and b.id = c.contractid";
	sql += " and c.id = d.al_nloannoteid";
	sql += " and d.naccountid = e.id";
	sql += " and b.nBorrowClientID = f.id";
	sql += " and a.StatusID = 3"; //3表示已复核
	sql += " and d.nStatusID <> 0"; 
	
	if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}
	if (nClientID > 0)
	{
		sql += " and b.nBorrowClientID = " + nClientID;
	}
	if (sDepositNo != null && sDepositNo.length > 0)
	{
		sql += " and d.af_sDepositNo = '" + sDepositNo + "'";
	}
	else {
		sql += " and d.af_sDepositNo = '-1'";
	}
	sql += " order by a.code";
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
 function getDiscountBillExtSQL(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO,to_char(b.dtfilldate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2,c.DTDISCOUNTDATE ";
    sql += " from loan_discountcontractbill a,loan_discountcredence b, loan_loanform c,loan_contractform t ";
	sql += " where b.id = a.ndiscountcredenceid and a.ncontractid=t.id  and t.nloanid=c.id and a.nStatusID=1 ";
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
			sql += " and b.ncontractid = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}
 /* 贴现放款通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sStatusIDArray 通知单状态
 * sDiscountCredenceNo 放款通知单号
 */ 
function getDiscountPayForm(nOfficeID,nCurrencyID,nContractID,sStatusIDArray,sDiscountCredenceNo)
{    
	var sql = "";  
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200'))
	{
		// 贴现发放——从子帐户取值	
		sql = "select a.id DiscountCredenceID,a.scode DiscountCredenceNo,b.ID ContractID,a.mAmount Amount,a.mInterest Interest,(a.mAmount-a.mInterest) DiscountAmount,sa.ID SubAccountID,account.SACCOUNTNO AccountNO,account.ID AccountID ";
	    sql += " from loan_discountpayform a,loan_contractform b,sett_SubAccount sa,sett_Account account,sett_accountType sat";
		sql += " where a.nContractID = b.id and sa.al_nLoanNoteID=a.ID and sa.nAccountID=account.ID and account.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 6 ";

		if (sStatusIDArray == '-100')
		{
			//收回——业务处理
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) >= 0 ";
		}
		else
		{
			//收回——业务复核
			//sql += " and sa.nstatusid=1 and sa.al_nLoanNoteID in (select distinct nDiscountNoteID from sett_TransRepaymentDiscount where nStatusID=2) ";
			//sql += " and sa.nstatusid=1 and sa.mUncheckPaymentAmount>=0 ";
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
		if (sDiscountCredenceNo != null && sDiscountCredenceNo.length > 0)
		{
			sql += " and a.scode like '%25" + sDiscountCredenceNo + "%25'";
		}
		sql += " order by a.scode";
	}
	else
	{
		// 贴现发放——从信贷取值
		sql = "select a.id DiscountCredenceID,a.scode DiscountCredenceNo,b.ID ContractID,round(a.mAmount,2) Amount,round(a.mInterest%2Ba.PurchaserInterest,2) Interest,round((a.mAmount-a.mInterest),2) DiscountAmount,-1 SubAccountID ";
	    sql += " from loan_discountpayform a,loan_contractform b ";
		sql += " where a.nContractID = b.id  ";
	
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
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			sql += " and a.nStatusID in ("+sStatusIDArray+")";
		}	
		if (sDiscountCredenceNo != null && sDiscountCredenceNo.length > 0)
		{
			sql += " and a.scode like '%25" + sDiscountCredenceNo + "%25'";
		}
		sql += " order by a.scode";
	
	}
	
    return sql;
}

/**

/**
 * 贴现票据放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同号
 * nDiscountCredenceID 贴现凭证ID
 * sDiscountBillNo 贴现票据号
 */ 
 function getDiscountBillExtSQLNew(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,discountContractBillIDs,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO,to_char(b.dtfilldate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2,c.DTDISCOUNTDATE ";
    sql += " from loan_discountcontractbill a,loan_discountcredence b, loan_loanform c,loan_contractform t ";
	sql += " where b.id = a.ndiscountcredenceid and a.ncontractid=t.id  and t.nloanid=c.id and a.nStatusID=1 ";
	//已收回不再显示
	sql += " and a.id not in (select NDISCOUNTBILLID from sett_transrepaymentdiscount where NTRANSACTIONTYPEID = 22 and NSTATUSID = 3 ) ";
    
     //过滤掉已经做过转贴现卖出买断发放的票据
    if(discountContractBillIDs != null && discountContractBillIDs .length > 0)
    {
    	sql += " and a.id not in ("+discountContractBillIDs+") ";
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
			sql += " and b.ncontractid = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}

/**
 * 贴现票据放大镜武钢项目去掉申请表直接生成合同所以去掉申请表关联
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同号
 * nDiscountCredenceID 贴现凭证ID
 * sDiscountBillNo 贴现票据号
 */ 
 function getDiscountBillExtSQLforwg(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,t.scontractcode ContractNO,a.nDiscountCredenceId CredenceID,b.scode CredenceNO,to_char(b.dtfilldate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2,to_char(b.dtfilldate, 'yyyy-mm-dd') DTDISCOUNTDATE ";
    sql += " from loan_discountcontractbill a,loan_discountcredence b,loan_contractform t ";
	sql += " where b.id = a.ndiscountcredenceid and a.ncontractid=t.id and a.nStatusID=1 ";
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
			sql += " and b.ncontractid = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}

//贴现收回--承兑票据号（过滤掉已经卖出的票据）
function getDiscountBillExtSQLforwgNew(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,discountContractBillIDs,sDiscountBillNo)
{     
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO, to_char(a.dtcreate , 'yyyy-mm-dd') dtCreate,to_char(b.dtfilldate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2,to_char(b.dtfilldate, 'yyyy-mm-dd') DTDISCOUNTDATE ";
    sql += " from loan_discountcontractbill a,loan_discountcredence b,loan_contractform t ";
	sql += " where b.id = a.ndiscountcredenceid and a.ncontractid=t.id and a.nStatusID=1 ";
	//已收回不再显示
	sql += " and a.id not in (select NDISCOUNTBILLID from sett_transrepaymentdiscount where NTRANSACTIONTYPEID = 22 and NSTATUSID = 3 ) ";
    
    //过滤掉已经做过转贴现卖出买断发放的票据
    if(discountContractBillIDs != null && discountContractBillIDs .length > 0)
    {
    	sql += " and a.id not in ("+discountContractBillIDs+") ";
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
			sql += " and b.ncontractid = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
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
	var sql = "select a.ID BillID,a.sCode BillNo,a.nContractID ContractID,'FromConstant_11_ContractID' ContractNO,a.nDiscountCredenceId CredenceID,'FromConstant_12_CredenceID' CredenceNO,to_char(b.dtfilldate,'yyyy-mm-dd') dDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest,a.NISLOCAL IsLocalID,'FromConstant_9_IsLocalID' Desc1,a.NACCEPTPOTYPEID AcceptPOTypeID,'FromConstant_8_AcceptPOTypeID' Desc2 ";
    sql += " from loan_discountcontractbill a,loan_discountcredence b ";
	sql += " where b.id = a.ndiscountcredenceid and a.nStatusID=1 ";
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
			sql += " and b.ncontractid = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and a.nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}

/**
 * 贴现凭证放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sStatusIDArray 通知单状态
 * sDiscountCredenceNo 放款通知单号
 */ 
function getDiscountCredenceSQL(nOfficeID,nCurrencyID,nContractID,sStatusIDArray,sDiscountCredenceNo)
{    
	var sql = "";  
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200'))
	{
		// 贴现发放——从子帐户取值	
		sql = "select a.id DiscountCredenceID,a.scode DiscountCredenceNo,b.ID ContractID,a.mAmount Amount,a.mInterest Interest,(a.mAmount-a.mInterest) DiscountAmount,sa.ID SubAccountID,account.SACCOUNTNO AccountNO,account.ID AccountID ";
	    sql += " from loan_DiscountCredence a,loan_contractform b,sett_SubAccount sa,sett_Account account,sett_accountType sat";
		sql += " where a.nContractID = b.id and sa.al_nLoanNoteID=a.ID and sa.nAccountID=account.ID and account.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 6 ";

		if (sStatusIDArray == '-100')
		{
			//收回——业务处理
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) >= 0 ";
		}
		else
		{
			//收回——业务复核
			//sql += " and sa.nstatusid=1 and sa.al_nLoanNoteID in (select distinct nDiscountNoteID from sett_TransRepaymentDiscount where nStatusID=2) ";
			//sql += " and sa.nstatusid=1 and sa.mUncheckPaymentAmount>=0 ";
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
		if (sDiscountCredenceNo != null && sDiscountCredenceNo.length > 0)
		{
			sql += " and a.scode like '%" + sDiscountCredenceNo + "%'";
		}
		    sql += " and b.ntypeid!=6 ";//屏蔽掉转贴现凭证
		sql += " order by a.scode";
	}
	else
	{
		// 贴现发放——从信贷取值
		sql = "select a.id DiscountCredenceID,a.scode DiscountCredenceNo,b.ID ContractID,round(a.mAmount,2) Amount,round(a.mInterest+a.PurchaserInterest,2) Interest,round((a.mAmount-a.mInterest),2) DiscountAmount,-1 SubAccountID ";
	    sql += " from loan_DiscountCredence a,loan_contractform b ";
		sql += " where a.nContractID = b.id  ";
		sql += " and b.ntypeid!=6 ";//屏蔽掉转贴现凭证
	
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
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			sql += " and a.nStatusID in ("+sStatusIDArray+")";
		}	
		if (sDiscountCredenceNo != null && sDiscountCredenceNo.length > 0)
		{
			sql += " and a.scode like '%" + sDiscountCredenceNo + "%'";
		}
		sql += " order by a.scode";
	
	}
	
    return sql;
}

/**
 * 利率计划放大镜
 * sPlanNoOrName 利率编号或名称
 */
function getInterestRatePlanSQL(nOfficeID,nCurrencyID,sPlanNoOrName)
{
	var sql = "select ID InterestRatePlanID,sCode InterestRatePlanNo,sName InterestRatePlanDesc ";
	sql += " from sett_InterestRatePlan ";
	sql += " where nStatusID=1 ";
	
	if (nCurrencyID > 0)
	{
		sql += " and NCURRENCYID = " + nCurrencyID;
	}
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}

	if (sPlanNoOrName != null && sPlanNoOrName != "") 
	{
		sql += " and (sCode like '%" + sPlanNoOrName +"%' or sName like '%" + sPlanNoOrName + "%')";		
	}
	sql += " order by sCode";
	
	return sql;
}

/**
 * 挂失和冻结用帐户放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * lAccountStatus 子帐号状态.
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getLossAndFreezeAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,lAccountStatus,nClientID,sAccountNo)
{      
	/*-----------Modify By Gqfang 04-03-02
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID ";
	*/
	var sql = "";
	if(lAccountStatus == 10)	//解冻
	{
	    sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName,ss.FREEZEAMOUNT as FREEZEAMOUNT,at.naccountgroupid naccountgroupid";
	}
	else{
        sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName,at.naccountgroupid naccountgroupid";
	}
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	if(lAccountStatus == 1)	//挂失
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid=1  ) ) ss ";
	}
	else if(lAccountStatus == 2)	//解挂
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid=5  )  ) ss ";	
	}
	else if(lAccountStatus == 3)	//换发证书
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid=5  )  ) ss ";	
	}
	else if(lAccountStatus == 4)	//冻结
	{
		sql += " (select distinct naccountid from sett_subaccount  where nstatusid=1 ) ss ";	
	}
	else if(lAccountStatus == 5)	//解冻
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid in(6,7,8)  )  ) ss ";	
	}
	else if(lAccountStatus == 6)	//冻结匹配
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid = 1  )  ) ss ";	
	}
	else if(lAccountStatus == 10)	//解冻
	{
		//sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid in(6,7,8)  )  ) ss ";	
          sql += " (select distinct acc.naccountid  naccountid,f.FREEZEAMOUNT FREEZEAMOUNT from sett_subaccount acc, (select distinct accountid,FREEZEAMOUNT from sett_reportlossorfreeze  where transactiontype=133 and status=3 and accountid not in (select accountid from sett_reportlossorfreeze where status=2 ) )f  where ( acc.nstatusid>0 and acc.nstatusid in(6,7,8)  )  and acc.naccountid=f.accountid  ) ss ";
	}
	else if(lAccountStatus == 11)	//解冻复核
	{
		//sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid in(6,7,8)  )  ) ss ";	
          sql += " (select distinct acc.naccountid  naccountid from sett_subaccount acc, (select distinct accountid from sett_reportlossorfreeze  where transactiontype=134 and status=20  )f  where ( acc.nstatusid>0 and acc.nstatusid in(6,7,8)  )  and acc.naccountid=f.accountid  ) ss ";
	}
	else if(lAccountStatus == 12) //解冻复核
	{
          sql += " (select distinct acc.naccountid  naccountid from sett_subaccount acc, (select distinct accountid from sett_reportlossorfreeze  where transactiontype=134 and status=2  )f  where ( acc.nstatusid>0 and acc.nstatusid in(6,7,8)  )  and acc.naccountid=f.accountid  ) ss ";
	}
	else
	{
		sql += " (select distinct naccountid from sett_subaccount  where nstatusid>0 ) ss  ";
	}
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid ";
	
	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4 ";
	}
	else
	{
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
		sql += " and at.nAccountGroupID in (1,2,3,11) ";
	}
	else if(nAccountGroupType==-13)
	{
		sql += " and at.nAccountGroupID in (2,3,11) ";
	}
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and a.nAccountTypeID in (8,9) ";
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
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

/**
 * 挂失冻结专用帐户放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getLossAndFreezeAccountRtnDepostNoSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,lAccountStatus,nClientID,sAccountNo)
{      
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName,'FromConstant_7_AccountID' as DepositNo ";
	sql += " from sett_Account a, Client c,sett_AccountType at , ";
	if(lAccountStatus == 1)	//挂失
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid=1  ) ) ss ";
	}
	else if(lAccountStatus == 2)	//解挂
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid=5  )  ) ss ";	
	}
	else if(lAccountStatus == 3)	//换发证书
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 )  ) ss ";	
	}
	else if(lAccountStatus == 4)	//冻结
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid=1  or nstatusid=8 )  ) ss ";	
	}
	else if(lAccountStatus == 5)	//解冻
	{
		sql += " (select distinct naccountid from sett_subaccount  where ( nstatusid>0 and nstatusid in(6,7,8)  )  ) ss ";	
	}
	else
	{
		sql += " (select distinct naccountid from sett_subaccount  where nstatusid>0 ) ss  ";
	}
    sql += " where a.nStatusID=1 and a.nCheckStatusID=4 and a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id =ss.naccountid ";
	
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
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
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
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}

function getLossAndFreezeDepositNoSQL(lSubStatusId,nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
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
			sql += " and a.sDepositNo like '%" + sDepositNo + "%'";
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
			//sql += " and ma.nAccountTypeID=2"; 
			sql += " and ma.nAccountTypeID in ( select to_number(SACCOUNTTYPECODE) from sett_accountType where NACCOUNTGROUPID = 2) "; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			//sql += " and ma.nAccountTypeID=3"; 
			sql += " and ma.nAccountTypeID in ( select to_number(SACCOUNTTYPECODE) from sett_accountType where NACCOUNTGROUPID = 3) "; 
		}
		else
		{
			sql += " and ma.nAccountTypeID in (( select to_number(SACCOUNTTYPECODE) from sett_accountType where NACCOUNTGROUPID = 2) union ( select to_number(SACCOUNTTYPECODE) from sett_accountType where NACCOUNTGROUPID = 3))";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
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
			//sql += " and a.nInputUserID <> " + nUserID; 
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else
	{
		//显示全部
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		
 		if( lSubStatusId == 1)		//挂失
		{
			//sql += " where ma.nAccountTypeID = sat.id and a.nAccountID=ma.ID and (a.nStatusID>0 and a.nStatusID =1 )";
			sql += " where a.nAccountID=ma.ID and (a.nStatusID>0 and a.nStatusID =1 )";
		}
		else if(lSubStatusId == 2 )	//解挂
		{
			//sql += " where ma.nAccountTypeID = sat.id and a.nAccountID=ma.ID and (a.nStatusID>0 and a.nStatusID =5 )";
			sql += " where a.nAccountID=ma.ID and (a.nStatusID>0 and a.nStatusID =5 )";
		}
		else if(lSubStatusId == 3 )	//换发证书
		{
			sql += " where a.nAccountID=ma.ID and (a.nStatusID>0 and a.nStatusID =5)";
		}
		else if(lSubStatusId == 4)	//冻结
		{
			sql += " where a.nAccountID=ma.ID and ( a.nStatusID =1  or a.nStatusID =8 )";
		}
		else if(lSubStatusId == 5)	//解冻
		{
			sql += " where a.nAccountID=ma.ID and ( (a.nStatusID>0 and a.nStatusID in(6,7,8) ) ) ";
		}
		else
		{
			sql += " where a.nAccountID=ma.ID and a.nStatusID>0 ";
		}
		if (nOfficeID > 0)
		{
			sql += " and ma.nofficeid = " + nOfficeID; 
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			//modified by mzh_fu 2007/09/12
			//sql += " and ma.nAccountTypeID in  ((select id from sett_accountType where NACCOUNTGROUPID = 2) union (select id from sett_accountType where NACCOUNTGROUPID = 3)) order by a.af_sDepositNo) "; 
			sql += " and ma.nAccountTypeID in  ((select id from sett_accountType where NACCOUNTGROUPID = 2) union (select id from sett_accountType where NACCOUNTGROUPID = 3)) "; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and ma.nAccountTypeID in ( select to_number(SACCOUNTTYPECODE) from sett_accountType where NACCOUNTGROUPID = 3) "; 			
		}
		else
		{
			sql += " and ma.nAccountTypeID in (( select id from sett_accountType where NACCOUNTGROUPID = 2) union ( select id from sett_accountType where NACCOUNTGROUPID = 3) union ( select id from sett_accountType where NACCOUNTGROUPID = 11) )";
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%" + sDepositNo + "%'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}	

}
/**
 * 合同放大镜(贷款未收利息类表外业务用)
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（自营，委托）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
function getConsignContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
{      
	var sql = "select contract.ID ContractID,contract.sContractCode ContractCode,contract.nBorrowClientID ClientID, client.sname ClientName,client.scode ClientCode,contract.nTypeID ContractType,nvl(nChargeRateTypeID,-1) CommisionDealType, contract.NCONSIGNCLIENTID ConsignClientID, a.scode ConsignClientCode, a.sname ConsignClientName ";
    sql += " from loan_contractform contract, client ,client  a ";
    sql += " where contract.nborrowclientid=client.id and contract.nOfficeID is not null and contract.NCONSIGNCLIENTID = a.id(+) ";
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
		//sql += " and contract.nStatusID in ("+sStatusIDArray+")";
		sql += " and contract.nStatusID >0";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and contract.sContractCode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and contract.nborrowclientid = " + nClientID;
	}
	sql += " order by contract.sContractCode";

    return sql;
}
/**
 * 放款通知单放大镜(贷款未收利息类表外业务用)
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 放款通知单号
 */ 
function getConsignPayFormSQL(nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200' || sStatusIDArray == '-500' || sStatusIDArray == '-600'))
	{//信托/委托收回，从子帐户取值
		
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,b.NBORROWCLIENTID, c.id loanAccountID, c.saccount loanAccount,b.NCONSIGNCLIENTID, d.id consignAccountID, d.saccount consignAccount ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,Client d";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and b.NCONSIGNCLIENTID = d.id(+)";
		
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
			sql += " and a.scode like '%" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}
/**
 * 商业汇票贴现类表外科目放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sGLSubjectCode 科目代码
 * transactionTypeArray 商业汇票贴现类型
 */
function getDiscountSubjectSQL(nOfficeID,nCurrencyID,sGLSubjectCode,transactionTypeArray)
{      
	var sql = "select distinct(a.subjectCode) subjectCode, b.id GLSubjectID, b.nSubjectType GLSubjectType, b.sSubjectName subjectName ";
    sql += " from SETT_OFFBALANCEREGISTER a, sett_vglsubjectdefinition b where  a.statusid = 1 ";
	if (transactionTypeArray != null && transactionTypeArray.length > 0)
		{
			sql += " and a.transactionType in ("+transactionTypeArray+")";
		}	
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and ncurrencyid = " + nCurrencyID;
	}	
	if (sGLSubjectCode != null && sGLSubjectCode.length > 0)
	{
		sql += " and sSubjectCode  like '%" + sGLSubjectCode + "%'";
	}
	sql += "  and a.subjectCode = b.sSubjectCode ";
	sql += " order by subjectCode ";
	
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
function getDiscountBillReturnSQL(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select a.ID DiscountBillID,a.sCode DiscountBillNo,to_char(b.dtDiscountDate,'yyyy-mm-dd') DiscountDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,a.mAmount Amount,nvl(a.NCONTRACTID,-1) ContractId,nvl(a.nDiscountCredenceID,-1) DiscountCredenceID,a.mCheckAmount CheckAmount,(a.mAmount-a.mCheckAmount) DelayInterest, nvl(trim(b.SCONTRACTCODE),' ') ContractNo, nvl(trim(c.scode),' ') DiscountCredenceNo ";
    sql += " from loan_discountcontractbill a,loan_ContractForm b, loan_DiscountCredence c ";
	sql += " where b.id = a.nContractID and c.id = a.NDISCOUNTCREDENCEID and a.nStatusID=1 ";

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
		sql += " and a.scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}
/**
 * 股份公司分析项目放大镜
 * nOfficeID 办事处ID 
 * nCurrencyID 币种ID
 * lProjectType 项目类型
 * lHaveItems 是否要求该项目名称有对应的设置项 1 是，0 否 
 */
function getStockProjectNameSQL(nOfficeID,nCurrencyID,lProjectType,lHaveItems,sProjectName)
{      
	var sql = "select ID, projectName, projectType from sett_stockProjectNameSetting ";    
	sql += " where statusID = 1 ";
    if (nOfficeID > 0)
	{
		sql += " and officeID = " + nOfficeID;
	}
	 if (nCurrencyID > 0)
	{
		sql += " and currencyID = " + nCurrencyID;
	}
	 if (lProjectType > 0)
	{
		sql += " and projectType = " + lProjectType;
	}
	if (sProjectName != null && sProjectName.length > 0)
	{
		sql += " and projectName like '%" + sProjectName + "%'";
	}
	if (lHaveItems == 1 )
		sql += " and ID in ( select distinct projectID from SETT_STOCKPROJECTSETTING where statusID = 1 ) ";
    return sql;
}
/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientName 客户名称
 */
function getClientSQL1(nOfficeID,sClientName)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientName != null && sClientName != "") 
	{
		//sql += " and a.sname like '%" + sClientName + "%'";
		sql += " and (a.sCode like '%" + sClientName + "%' or a.sName like '%" + sClientName + "%')";
	}
	sql += " order by a.sCode";
	
	return sql;
}
/**
 * 利率放大镜
 * nOfficeID 办事处ID
 * sInterestRateNo 利率编号
 */
function getLoanInterestRateSQL(lOfficeID,lCurrencyID,sInterestRateNo,changeRate)
{
	var lTmpRate = 1+changeRate/100;
    var strSQL = " select RateID,RateCode,mRate,RateName,RateValue,dtValiDate,adjustRate from ("  
 	strSQL += "  select b.id RateID,a.SINTERESTRATENO RateCode,round(b.mRate,6) mRate, a.sinterestratename as RateName,";
    strSQL += " round(b.mRate,6) RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate ";
	strSQL += ",0 as adjustRate ";

   /*if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate <%=URLEncoder.encode("*")%>"+lTmpRate+") as adjustRate ";
	}else
	{
		strSQL += ",0 as adjustRate ";
	}*/
    strSQL  += " from LOAN_INTERESTRATETYPEINFO a,LOAN_INTERESTRATE  b ";
    strSQL  += " where a.id = b.nBankInterestTypeID and b.dtValiDate <= sysdate ";
 
 	if (sInterestRateNo != null && sInterestRateNo != "") 
 	 {
 	  	strSQL += " and a.sInterestRateNo like  '%" + sInterestRateNo + "%' ";
	 }
 	 if (lCurrencyID > 0)
 	 {
 	  	strSQL=strSQL+ " and a.nCurrencyID = "+lCurrencyID;
 	 }
	 if (lOfficeID > 0)
 	 {
 	  	strSQL=strSQL+ " and a.nOfficeID = "+lOfficeID;
 	 }
    strSQL+=" and (a.id,b.dtvalidate) in ";
    strSQL+=" (   select a.id,max(b.dtvalidate) from loan_INTERESTRATETYPEINFO a,loan_InterestRate b ";
	strSQL+=" WHERE ";
	strSQL+=" a.ID=b.NBANKINTERESTTYPEID ";
	strSQL+=" and  to_char(b.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') ";
	strSQL+=" group by a.id ) ";

    strSQL = strSQL + " union ";
    strSQL = strSQL + " select b.id RateID,a.sInterestRateNo RateCode,round(b.mRate,6) mRate,a.sInterestRateName as RateName,";
	strSQL  += " round(b.mRate,6) RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate "
	strSQL  += ",0 as adjustRate ";
	/*if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate <%=URLEncoder.encode("*")%>"+lTmpRate+") as adjustRate ";
	}else
	{
		strSQL += ",0 as adjustRate ";
	}*/
	strSQL  += " from loan_INTERESTRATETYPEINFO a,loan_InterestRate  b ";
    strSQL = strSQL + " where a.id = b.nBankInterestTypeID and b.dtvalidate > sysdate ";
 	if (sInterestRateNo != null && sInterestRateNo != "") 
 	 {
 	  	strSQL += " and a.sInterestRateNo like  '%" + sInterestRateNo + "%' ";
	 }
  	if (lCurrencyID > 0)
  	{
   		strSQL=strSQL+ " and a.nCurrencyID = "+lCurrencyID;
  	}
	if (lOfficeID > 0)
 	 {
 	  	strSQL=strSQL+ " and a.nOfficeID = "+lOfficeID;
 	 }
		//strSQL = strSQL + " order by sCode,dtValidate";
  	strSQL = strSQL + ") order by mRate ";
  	return strSQL;
}

/**
 * 贷款结算合同放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
function getLoanSettContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nLoanTypeID,nInOrOut,nDiscountTypeID)
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
		sql += " and contract.sContractCode like '%" + sContractCode + "%'";
	}
	if (nLoanTypeID > 0)
	{
		sql += " and contract.nTypeID = " + nLoanTypeID;
	}
	if (nInOrOut > 0)
	{
		sql += " and contract.nInOrOut = " + nInOrOut;
	}
	if (nDiscountTypeID > 0)
	{
		sql += " and contract.nDiscountTypeID = " + nDiscountTypeID;
	}

	sql += " order by contract.sContractCode";

    return sql;
}
/**
 * 票据模块录入的贴现票据放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同号
 * nDiscountCredenceID 贴现凭证ID
 * sDiscountBillNo 贴现票据号
 */ 
function getBillModuleDiscountBillSQL(nOfficeID,nCurrencyID,nContractID,nDiscountCredenceID,sDiscountBillNo)
{     
	var sql = "select id,SCODE,to_char(DTCREATE,'yyyy-mm-dd') dtCreate,to_char(DTEND,'yyyy-mm-dd') dtEnd,mAmount,STRACCEPTORNAME,SFORMEROWNER,mAmount from loan_discountcontractbill where NMODULESOURCEID=10 and NDISCOUNTCREDENCEID is null";
  
    if (nOfficeID > 0)
	{
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and nCurrencyID = " + nCurrencyID;
	}
	if (nContractID > 0)
	{
		sql += " and nContractID = " + nContractID;
	}
	if (nDiscountCredenceID > 0)
	{
		sql += " and nDiscountCredenceId = " + nDiscountCredenceID;
	}
	if (sDiscountBillNo != null && sDiscountBillNo.length > 0)
	{
		sql += " and scode like '%" + sDiscountBillNo + "%'";
	}
	sql += " order by scode";
	
    return sql;
}

/**
 * 预算项目放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getBudgetItemSQL(nOfficeID,nCurrencyID,nClientID,nSystemID,nIsLeaf,sItemNo)
{
	var sql = "select distinct a.id itemID,a.ItemNo itemNo,a.ItemName itemName,b.id SystemID,b.budgetSystemNo SystemNo,'' BudgetAmount";
	sql += " from BUDGET_TEMPLET a,Budget_System b,Budget_PlanDetail c,Budget_Plan d";
	sql += " where  a.budgetSystemID = b.id and a.id = c.itemid and c.planid = d.id and a.statusID=1";
		
	if (nOfficeID > 0)
	{
		sql += " and b.OfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.CurrencyID = " + nCurrencyID;
	}
	if (nClientID > 0)
	{
		sql += " and d.ClientID = " + nClientID;
	}
	if (nIsLeaf > 0)
	{
		sql += " and a.IsLeaf = " + nIsLeaf;
	}

	if (nSystemID  > 0)
	{
		sql += " and a.budgetSystemID = " + nSystemID;
	}

	if (sItemNo != null && sItemNo.length > 0)
	{
		sql += " and a.itemNo like '" + sItemNo + "%'";
	}
	sql += " order by a.ItemNo";
	return sql;
}
/**
 * 旧帐户放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getOldAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
{      
	/*-----------Modify By Gqfang 04-03-02
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID ";
	*/
	//edit by rxie for nhcw
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,o.ORIGINALACCOUNTNO as OldAccount,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ,(ss.mbalance - ss.muncheckpaymentamount) mBalance";
	sql += " from sett_Account a, Client c,SETT_ACCOUNTRELATIONSSETTING o,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.ID = o.AccountID(+) and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";

	if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4 ";
	}
	else
	{
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
    if (nClientID != null && nClientID > 0)
	{
		sql += " and a.nClientID = " + nClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
	
    return sql;
}
/**
 * 资金上收策略放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sPolicyCode 上收策略编号
 */ 
function getUpGatheringPolicySQL(nOfficeID,nCurrencyID,sPolicyCode)
{     
	var sql = "select Id,Code,Name,UpClientId,PayeeAccountId,UpOrder,UpType,InputUserId,ModifyUserId from Sett_UpGatheringPolicy where StatusId=1 ";
  
    if (nOfficeID > 0)
	{
		sql += " and OfficeId = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and CurrencyId = " + nCurrencyID;
	}
	
	if (sPolicyCode != null && sPolicyCode.length > 0)
	{
		sql += " and Code like '%" + sPolicyCode + "%'";
	}
	sql += " order by Code";
	
    return sql;
}


/**
 * 银企接口--下属单位帐户编号放大镜（带出下属单位名称和科目号）
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nBankTypeID 银行类型ID
 * nClientID 所属单位ID
 * nBankAccountNo 银行帐户编号
 */
function getFilialeAccountNoAndSubjectSQL(nOfficeID,nCurrencyID,nBankTypeID,nClientID,nBankAccountNo)
{
	var sql = " select distinct b.ID ID,b.SBANKACCOUNTNO BANKACCOUNTNO,b.NBNKTYPE BANKTYPE,b.SBANKACCOUNTNAME BANKACCOUNTNAME,b.NCLIENTID CLIENTID,b.NACCOUNTTYPE ACCOUNTTYPE,b.SFILIALENAME FILIALENAME,a.SSUBJECT SUBJECT ";
	sql += " from SETT_BANKACCOUNTOFFILIALE b, SETT_ACCOUNT a";
	sql += " where b.SBANKACCOUNTNO is not null and b.NWITHINACCOUNTID=a.ID";
	
	if (nCurrencyID > 0) 
	{
		sql += " and b.NCURRENCYID = " + nCurrencyID; 
	}
	if (nBankTypeID > 0) 
	{
		sql += " and b.NBNKTYPE = " + nBankTypeID; 
	}
	if (nClientID > 0) 
	{
		sql += " and b.NCLIENTID = " + nClientID; 
	}
	if (nBankAccountNo != null && nBankAccountNo != "") 
	{
		sql += " and b.SBANKACCOUNTNO like '%" + nBankAccountNo + "%'";
	}
	
	sql += " order by b.SBANKACCOUNTNO";
	
	return sql;
}

/**
 * 银企接口--成员单位上收帐户编号放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nBankTypeID 银行类型ID
 * nClientID 所属单位ID
 * nBankAccountNo 银行帐户编号
 */
function getTurnInFilialeAccountNoSQL(nOfficeID,nCurrencyID,nBankTypeID,nClientID,nBankAccountNo)
{
	var sql = " select distinct b.ID ID,b.SBANKACCOUNTNO BANKACCOUNTNO,b.NBNKTYPE BANKTYPE,b.SBANKACCOUNTNAME BANKACCOUNTNAME,b.NCLIENTID CLIENTID,b.NACCOUNTTYPE ACCOUNTTYPE,b.SFILIALENAME FILIALENAME ";
	sql += " from SETT_BANKACCOUNTOFFILIALE b ";
	sql += " where b.SBANKACCOUNTNO is not null ";
	sql += " and b.NACCOUNTTYPE = 1 ";
	
	if (nCurrencyID > 0) 
	{
		sql += " and b.NCURRENCYID = " + nCurrencyID; 
	}
	if (nBankTypeID > 0) 
	{
		sql += " and b.NBNKTYPE = " + nBankTypeID; 
	}
	if (nClientID > 0) 
	{
		sql += " and b.NCLIENTID = " + nClientID; 
	}
	if (nBankAccountNo != null && nBankAccountNo != "") 
	{
		sql += " and b.SBANKACCOUNTNO like '%" + nBankAccountNo + "%'";
	}
	
	sql += " order by b.SBANKACCOUNTNO";
	
	return sql;
}
/**
 * 利息税费率计划放大镜
 * nOfficeID 办事处ID
 */
function getTaxRatePlanSQL(nOfficeID)
{
	var sql = "select distinct id as planID,code as planCode, name as planName ";
	sql += " from sett_taxrateplansetting where StatusID > 0 ";
 
    if (nOfficeID > 0)
	{
		sql += " and officeid = " + nOfficeID; 
	}
	sql += " order by Code";
	
	return sql;
}

/**
 * 融资租赁还款通知单放大镜
 * nOfficeID 办事处ID
 * sClientName 客户名称
 */
function getReturnFinanceSQL(nOfficeID,lCurrencyID,lStatusid,nContractID,sReturnNo)
{
	var sql = "select   a.id as returnID, a.code as returnNo,c.sname as ClientName,a.contractid as contractID ";
	sql += " from LOAN_LEASEHOLDREPAYFORM a, loan_contractform b,client c ";
	sql += " where a.contractid = b.id and b.NBORROWCLIENTID=c.id ";

	if(lStatusid>0){
		sql += " and a.nstatusid =3 " ; 
	}else{
		sql += " and (a.nstatusid =3 or a.nstatusid =4) " ; 
	}
 
    if (nOfficeID > 0)
	{
		sql += " and a.officeid = " + nOfficeID; 
	}
	if (lCurrencyID > 0)
	{
		sql += " and a.currencyid = " + lCurrencyID; 
	}
	if (nContractID > 0)
	{
		sql += " and a.contractid = " + nContractID;
	}
	if (sReturnNo != null && sReturnNo != "") 
	{
		//sql += " and a.sname like '%" + sClientName + "%'";
		sql += " and (a.Code like '%" + sReturnNo + "%')";
	}
	sql += " order by a.Code";
	
	return sql;
}
/**
 * 融资租赁收款通知单放大镜
 * nOfficeID 办事处ID
 * sClientName 客户名称
 */
function getPayFinanceSQL(nOfficeID,lCurrencyID,lPayFormTypeID,lPayFormStatusIDs,nContractID,sReturnNo)
{
	var sql = "select   a.id as payID, a.code as payNo,c.sname as ClientName,a.contractid as contractID ";
	sql += " from loan_assurechargeform a, loan_contractform b,client c ";
	sql += " where a.contractid = b.id and b.NBORROWCLIENTID=c.id";
	if(lPayFormStatusIDs==1){
		sql += " and a.statusid =3 " ; 
	}else if (lPayFormStatusIDs==0){
		sql += " and (a.statusid =3 or a.statusid =4) " ; 
	}
    if (nOfficeID > 0)
	{
		sql += " and a.officeid = " + nOfficeID; 
	}
	if (lCurrencyID > 0)
	{
		sql += " and a.currencyid = " + lCurrencyID; 
	}
	if (lPayFormTypeID  > 0)
		{
			sql += " and b.nTypeID in ("+lPayFormTypeID+")";
		}	
	if (nContractID > 0)
	{
		sql += " and a.contractid = " + nContractID;
	}
	if (sReturnNo != null && sReturnNo != "") 
	{
		//sql += " and a.sname like '%" + sClientName + "%'";
		sql += " and (a.Code like '%" + sReturnNo + "%')";
	}
	sql += " order by a.Code";
	
	return sql;
}

/**===========?????===============
* lCurrencyID 币种ID
* lClientID 客户ID
* sAccountNo 账户编号
*=====================================*/
function getOBBudget(sBudgetname,status,officeid,currencyid,type){
	getOBBudget(sBudgetname,status,officeid,currencyid,type,8);
}

function getOBBudget(sBudgetname,status,officeid,currencyid,type,queryType)
{ 
	var strSQL ="";
	if(type == 1){
		strSQL = "select t.id,t.sname,t.accountid,b.saccountno s_accountno,to_char(t.startdate,'yyyy-mm-dd') startdate,to_char(t.enddate,'yyyy-mm-dd') enddate,rPAD(LPAD(to_char(t.startdate,'yyyy-mm-dd'),10,'?'),24,LPAD(to_char(t.enddate,'yyyy-mm-dd'),14,' to ')) area,"
 									+"(case t.status when 1 then '已保存'" 
 									+" when 2 then '已提交' "
              					  	+" when 3 then '已审批' "
              					  	+" when 4 then '未拨付' "
              					  	+" when 5 then '已拨付' "
              					  	+" when 6 then '拨付失败' "
              					  	+" when 7 then '被调整' "
              					  	+" end) as status "
              					  	+" from ob_budget t,sett_account b where status<>0 and t.accountid=b.id and t.parentbudgetid = -1 and t.officeid = "+officeid+" and t.currencyid = "+currencyid;
	 }else if(type == 0){
	 	strSQL = "select t.id,t.sname,t.accountid,b.saccountno s_accountno,to_char(t.startdate,'yyyy-mm-dd') startdate,to_char(t.enddate,'yyyy-mm-dd') enddate,rPAD(LPAD(to_char(t.startdate,'yyyy-mm-dd'),10,'?'),24,LPAD(to_char(t.enddate,'yyyy-mm-dd'),14,' to ')) area "
	 			+" from ob_budget t,sett_account b where status<>0 and t.accountid=b.id and t.parentbudgetid = -1 and t.officeid = "+officeid+" and t.currencyid = "+currencyid;
	 }
	 if(status > 0)
	{
		strSQL += " and t.status="+status;
	}
	if(sBudgetname != null && sBudgetname.length > 0)
	{
		strSQL += "  and t.sname like '%" + sBudgetname + "%'";
	}
	if(queryType == 9){
		strSQL += " and t.accountid in (select s.accountid from sett_autotask s where s.status = 1)";
		strSQL += " and t.status in (3,5,6)";
	}
	strSQL += " order by t.startdate desc ";
	return strSQL;
}

//自动任务设置用款账户放大镜
function getBudgetPayerAccountNoSQL(sBankAccountCode,lUserID,lCurrencyID,lInstructionID,lOfficeId){
	getBudgetPayerAccountNoSQL(sBankAccountCode,lUserID,lCurrencyID,lInstructionID,lOfficeId,8);
}

function getBudgetPayerAccountNoSQL(sBankAccountCode,lUserID,lCurrencyID,lInstructionID,lOfficeId,queryType)
{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, b.saccountno accountno, b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as id,b.sname as name"
                 + " from SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf"
                 + " where oba.sAccountNo=b.sAccountNo and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '%" + sBankAccountCode + "%' ";
		}
		sql = sql+" and b.ncurrencyid ="+lCurrencyID+" and b.nofficeid ="+lOfficeId;
		if(queryType == 9){
			sql += " and b.id in (select s.accountid from sett_autotask s where s.status = 1)";
		}
		sql += " order by saccountno  ";
		return sql;
}

//自动任务设置新增用款账户放大镜
function getBudgetPayerAccountNoWithoutAutoTaskSQL(sBankAccountCode,lUserID,lCurrencyID,lInstructionID,lOfficeId)
{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, b.saccountno accountno, b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as id,b.sname as name"
                 + " from SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf"
                 + " where oba.sAccountNo=b.sAccountNo and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '%" + sBankAccountCode + "%' ";
		}
		sql = sql+" and b.ncurrencyid ="+lCurrencyID+" and b.nofficeid ="+lOfficeId;
		sql = sql + " and b.id not in (select t.accountid from sett_autotask t where t.status = 1)";
		sql += " order by saccountno  ";
		return sql;
}
/**
 * 账户放大镜
 * nOfficeID 办事处ID
 * sAccountName 账户名称
 */
function getCurrentAccountSQL(nOfficeID,nCurrencyID,lAccountGroupID,sAccountNo)
{
	var sql = " select acc.id,acc.saccountno ,acc.sname ";
	sql += " from sett_account acc,sett_accounttype accType ";
	sql += " where accType.Naccountgroupid = " + lAccountGroupID;
    sql += " and acc.naccounttypeid = accType.Id ";
    sql += " and acc.nstatusid = 1 ";
    sql += " and acc.nCheckStatusID = 4 ";
    if (nOfficeID > 0)
	{
		sql += " and acc.nofficeid = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
	   sql += " and acc.ncurrencyid = " + nCurrencyID; 
	 }
	if (sAccountNo != null && sAccountNo != "") 
	{
		sql += " and (acc.saccountno like '%" + sAccountNo + "%' or acc.saccountno like '%" + sAccountNo + "%')";
	}
	sql += " order by acc.saccountno";
	
	return sql;
}
/**
 * 放款单放大镜
 * nOfficeID 办事处ID
 * sAccountName 账户名称
 */
function getLoanNoteSQL(nOfficeID,nCurrencyID,strLoanNotetNo)
{
	var sql = " select a.id PayFormID,a.scode PayFormCode, b.ID ContractID, b.scontractcode ";
	sql += " from LOAN_PAYFORM a,loan_contractform b ";
	sql += " where a.nContractID = b.id ";
	sql += " and b.nStatusID in (3,4) ";
    sql += " and b.ntypeid in (1,2) ";
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
	   sql += " and b.nCurrencyID = " + nCurrencyID; 
	 }
	if (strLoanNotetNo != null && strLoanNotetNo != "") 
	{
		sql += " and ( a.scode like '%" + strLoanNotetNo + "%' or a.scode like '%" + strLoanNotetNo + "%')";
	}
	sql += " order by a.scode";
	
	return sql;
}

function getFundPlanSQL(nOfficeID,nCurrencyID,strCpCode)
{
	var sql = "select a.id CPID,a.CPCODE CPCODE,b.SCODE CLIENTNO,a.STARTDATE STARTDATE,a.ENDDATE ENDDATE   from ob_capitalplan a, client b where a.CLIENTID=b.ID and a.STATUSID in(2,20)";
	if (nOfficeID > 0)
	{
		sql += " and a.OFFICEID = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
	   sql += " and a.CURRENCYID = " + nCurrencyID; 
	 }
	if (strCpCode != null && strCpCode != "") 
	{
		sql += " and  a.CPCODE like '%" + strCpCode + "%' ";
	}
	sql += " order by b.SCODE,a.STARTDATE";
	
	return sql;
}


/**
* added by ylguo
* 和getFundPlanSQL(nOfficeID,nCurrencyID,strCpCode)
* 不同的是，这个放大镜的SQL要查询出资金计划的除了删除状态的所有资金计划。
* 
*/
function getFundPlanSQL2(nOfficeID,nCurrencyID,strCpCode)
{
	var sql = "select a.id CPID,a.CPCODE CPCODE,b.SCODE CLIENTNO,a.STARTDATE STARTDATE,a.ENDDATE ENDDATE   from ob_capitalplan a, client b where a.CLIENTID=b.ID and a.STATUSID != 0 ";
	if (nOfficeID > 0)
	{
		sql += " and a.OFFICEID = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
	   sql += " and a.CURRENCYID = " + nCurrencyID; 
	 }
	if (strCpCode != null && strCpCode != "") 
	{
		sql += " and  a.CPCODE like '%" + strCpCode + "%' ";
	}
	sql += " order by b.SCODE,a.STARTDATE";
	
	return sql;
}

function getAheadPayFormSQL1(nOfficeID,nCurrencyID,nPayFormID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{      
	var sql = "select a.id AheadRepayFormID,a.scode AheadRepayFormNo,b.sContractCode ContractNo,p.ID PayFormID,p.sCode PayFormNo,c.sCode ClientNo,a.mAmount Amount,to_char(p.dtStart,'yyyy-mm-dd') StartDate,b.id ContractID, (ssa.mBalance - ssa.mUncheckPaymentAmount) Balance";
    sql += " from loan_AheadRepayForm a,loan_contractform b,loan_PayForm p,Client c,sett_subaccount ssa ";
    
	sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.nLoanPayNoticeID=p.ID and ssa.al_nloannoteid=p.ID  and ssa.nStatusID=1 ";//jzw 2010-04-21 子账户销户后不应该在还款放大镜中再显示
	
    if (nOfficeID > 0)
	{
		sql += " and b.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.nCurrencyID = " + nCurrencyID;
	}
	if (nPayFormID > 0)
	{
		sql += " and a.nLoanPayNoticeID = " + nPayFormID;
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
		sql += " and a.scode like '%" + sPayFormNo + "%'";
	}
	sql += " order by a.scode";
	
    return sql;
}
/**
* added by zcwang
* 二级户对账专用
* 
*/
function getTwoLevelAccountSQL(nOfficeID,nCurrencyID,nAccountID,sAccountNo)
{      
	/*----------- ----------------------*/
	var sql = "select acct.ID AccountID,acct.sAccountNo AccountNo,acct.sName AccountName,t.N_ID bankid,t.S_ACCOUNTNO bankAccountNO,t.S_ACCOUNTNAME bankAccountName ";
	sql += " from bs_bankaccountinfo t,sett_account acct,sett_accounttype accttype ";
	sql += " where  t.n_rdstatus = 1  and t.n_ischeck = 1 and t.n_accountstatus = 1 and t.N_ACCOUNTTYPE = 3 and t.n_subjectid=acct.id and acct.naccounttypeid=accttype.id and accttype.accountmodule=1";
	if (nOfficeID > 0)
	{
		sql += " and  t.n_officeId = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and t.n_currencyType = " + nCurrencyID;
	}	
    if (nAccountID > 0)
	{
		sql += " and acct.ID = " + nAccountID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and acct.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by acct.sAccountNo";
	
    return sql;
}

/**
 * 转让通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 转让通知单号
 */ 
function getTransferNoticeFormSQL(nOfficeID,nCurrencyID,sPayFormNo,noticeType,statusid)
{   
	var sql = "";   

		 sql = " select t.id ,t.noticecode ,t.contractid ,m.CONTRACTCODE ,t.isurrogatepay, t.noticetypeid, to_char(t.amount, '999,999,999,999,990.99') amount ,t.bankid, to_char(t.interest, '999,999,999,999,990.99') interest, ";
	     sql += " decode(t.transtypeid,";
	     sql += " 1001, '卖出回购',";
	     sql += " 1002, '卖出卖断',";
	     sql += " 1003, '买入回购',";
	     sql += " 1004, '买入买断',";
	     sql += " '') TRANSTYPE,";
	     sql += " t.transtypeid, ";
	     sql += " decode(t.isurrogatepay,";
	     sql += " 1, '代收',";
	     sql += " 2, '非代收',";
	     sql += " '') Urrogatepay ";
	     sql += " from cra_transfernoticeform t ,cra_transfercontractform m ";
		 sql += " where m.id = t.cracontractid ";
		 
		if (statusid > 0)
		{
			sql += " and t.statusid = " + statusid;
		}
	    if (nOfficeID > 0)
		{
			sql += " and t.OfficeID = " + nOfficeID;
		}
		if (nCurrencyID > 0)
		{
			sql += " and t.CurrencyID = " + nCurrencyID;
		}		
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and t.noticecode like '%" + sPayFormNo + "%'";
		}
		if (noticeType > 0)
		{
			sql += " and t.NOTICETYPEID = " + noticeType;
		}
		sql += " order by t.noticecode";
		
    return sql;
	
}

/**
 * 转让通知单放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 转让通知单号
 * systime使用时限定开机日
 */ 
function getTransferNoticeFormSQL1(nOfficeID,nCurrencyID,sPayFormNo,noticeType,statusid,systime)
{   
	var sql = "";   

		 sql = " select t.id ,t.noticecode ,t.contractid ,m.CONTRACTCODE ,t.isurrogatepay, t.noticetypeid, to_char(t.amount, '999,999,999,999,990.99') amount ,t.bankid, to_char(t.interest, '999,999,999,999,990.99') interest ,";
	     sql += " decode(t.transtypeid,";
	     sql += " 1001, '卖出回购',";
	     sql += " 1002, '卖出卖断',";
	     sql += " 1003, '买入回购',";
	     sql += " 1004, '买入买断',";
	     sql += " '') TRANSTYPE,";
	     sql += " t.transtypeid, ";
	     sql += " decode(t.isurrogatepay,";
	     sql += " 1, '代收',";
	     sql += " 2, '非代收',";
	     sql += " '') Urrogatepay ";
	     sql += " from cra_transfernoticeform t ,cra_transfercontractform m ";
		 sql += " where m.id = t.cracontractid ";
		 sql += " and t.dtclearinterest <= to_date('"+systime+"','yyyy-mm-dd')";
		 
		if (statusid > 0)
		{
			sql += " and t.statusid = " + statusid;
		}
	    if (nOfficeID > 0)
		{
			sql += " and t.OfficeID = " + nOfficeID;
		}
		if (nCurrencyID > 0)
		{
			sql += " and t.CurrencyID = " + nCurrencyID;
		}		
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and t.noticecode like '%" + sPayFormNo + "%'";
		}
		if (noticeType > 0)
		{
			sql += " and t.NOTICETYPEID = " + noticeType;
		}
		sql += " order by t.noticecode";
		
    return sql;
	
}

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

function getcounterpart(nOfficeID,nCurrencyID,nStatusID)
{
	var sql = "select id ,counterpartcode ,counterpartname from cra_counterpart where OFFICEID ="+ nOfficeID+" and CURRENCYID= "+nCurrencyID+" and statusid= "+nStatusID;
	return sql ;
}

//多借多贷子类型 add by xlchang 2010-11-03
function getOPRMSubTypeSQL(nOfficeID,nCurrencyID,txtValue) {
	var sql = "select id,scode,sname from sett_opmrsubtype s ";
	sql += " where s.nstatusid = 1 and s.nofficeid = " + nOfficeID+ "and s.ncurrencyid = " +nCurrencyID;
	if (txtValue != null && txtValue.length > 0) {
		sql += " and (s.scode like '%" + txtValue + "%' or s.sname like '%" + txtValue + "%')";
	}
	sql += " order by s.scode";
	return sql ;
}

//币种放大镜sql
function getCurrencySQL(currencyType)
{  
	
 	var sql="select * from currencyinfo where status=1 "; 
    if(currencyType!=""&&currencyType.length>0)
    {
 	sql+=" and name ='"+currencyType+"'";
 	}
 
 	sql+=" order by id";
 	return sql;
}                                                                                     


/**
 * 资金调度令-单位名称 放大镜
 * OfficeID 办事处ID
 * OrganizationName  单位名称
 */
function getOrganizationSQL(OfficeID,CurrencyID,OrganizationID,OrganizationName)
{      
        var sql = "select ID,OrganizationName ";
    sql += " from fd_organization where StatusID=1  and OfficeID is not null";
    if (OrganizationID > 0)
        {
                sql += " and ID = " + OrganizationID;
        }
        if (OfficeID > 0)
        {
                sql += " and OfficeID = " + OfficeID;
        }
        if (CurrencyID > 0)
        {
                sql += " and CurrentID = " + CurrencyID;
        }

        if (OrganizationName != null && OrganizationName.length > 0)
        {
                sql += " and OrganizationName like '%" + OrganizationName + "%'";
        }
        sql += " order by ID";
        
    return sql;
}

/**
 * 资金调度令-单位名称 放大镜（查询时）
 * OfficeID 办事处ID
 * OrganizationName  单位名称
 */
function getSearchOrganizationSQL(OfficeID,CurrencyID,OrganizationID,OrganizationName)
{      
        var sql = "select ID,OrganizationName ";
    sql += " from fd_organization where OfficeID is not null";
    if (OrganizationID > 0)
        {
                sql += " and ID = " + OrganizationID;
        }
        if (OfficeID > 0)
        {
                sql += " and OfficeID = " + OfficeID;
        }
        if (CurrencyID > 0)
        {
                sql += " and CurrentID = " + CurrencyID;
        }

        if (OrganizationName != null && OrganizationName.length > 0)
        {
                sql += " and OrganizationName like '%" + OrganizationName + "%'";
        }
        sql += " order by ID";
        
    return sql;
}


/**
 * 资金调度令-开户行账号及开户行名称 放大镜
 * OfficeID 办事处ID
 * BankAccountID  开户行账号ID
 * BankAccountNo  开户行账号
 * OrganizationID 单位ID
 */
function getFoundsDispatchBankAccountSQL(OfficeID,CurrencyID,BankAccountID,BankAccountNo)
{      
        var sql = "select ID,BankAccountCode,BankName ";
    sql += " from fd_bankaccount where StatusID=1 and OfficeID is not null";

        if (OfficeID > 0)
        {
                sql += " and OfficeID = " + OfficeID;
        }
        if (CurrencyID > 0)
        {
                sql += " and CurrentID = " + CurrencyID;
        }
        if (BankAccountID > 0)
        {
                sql += " and ID = " + BankAccountID;
        }
        if (BankAccountNo != null && BankAccountNo.length > 0)
        {
                sql += " and BankAccountCode like '%" + BankAccountNo + "%'";
        }
        sql += " order by ID";
        
    return sql;
}
/**
 * 资金调度令-资金调度令编号 放大镜
 * OfficeID 办事处ID
 * FoundsdispatchID  调度令ID
 * FoundsdispatchCode  调度令编号
 */
function getFoundsdispatchCodeSQL(OfficeID,CurrencyID,FoundsdispatchID,FoundsdispatchCode)
{      
        var sql = "select ID,FoundsdispatchCode";
    sql += " from sett_Foundsdispatch where StatusID=1 and OfficeID is not null";

        if (OfficeID > 0)
        {
                sql += " and OfficeID = " + OfficeID;
        }
        if (CurrencyID > 0)
        {
                sql += " and CurrentID = " + CurrencyID;
        }
        if (FoundsdispatchID > 0)
        {
                sql += " and ID = " + FoundsdispatchID;
        }
        if (FoundsdispatchCode != null && FoundsdispatchCode.length > 0)
        {
                sql += " and FoundsdispatchCode like '%" + FoundsdispatchCode + "%'";
        }
        sql += " order by ID";
        
    return sql;
}




function getTrustCollectionContractCtrl(nOfficeID,nCurrencyID,sContractCode,nClientID,lAcountID)
{      
	var sql = "select a.id id, a.scode scode,a.npayclientid,a.npayaccountid, b.SACCOUNTNO saccountno,c.sName sName "+
	"from sett_specialContract a,sett_account b,client c where a.Nstatusid >0  and b.nstatusid>0 and a.npayaccountid = b.id and a.npayclientid"+
      "= c.id";
    if (nOfficeID > 0)
	{
		sql += " and a.NOFFICEID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.NCURRENCYID = " + nCurrencyID;
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and a.scode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and a.npayclientid = " + nClientID;
	}
	if (lAcountID > 0)
	{
		sql += " and a.NPAYACCOUNTID = " + lAcountID;
	}
	sql += " order by a.scode";

    return sql;
}

function getTrustCollectionAccountSQL(nOfficeID,nCurrencyID,id,sAccountNo)
{     
	var sql = "select a.id id, a.scode scode,a.npayclientid,a.npayaccountid npayaccountid, b.SACCOUNTNO saccountno,  b.SNAME saccountname,c.sName sName "+
	"from sett_specialContract a,sett_account b,client c where a.Nstatusid >0  and b.nstatusid>0 and a.npayaccountid = b.id and a.npayclientid"+
      "= c.id";
	
	if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID = " + nCurrencyID;
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and b.SACCOUNTNO like '%" + sAccountNo + "%'";
	}
	if (id > 0)
	{
		sql += " and a.id = " + id;
	}
	sql += " order by a.scode";
    return sql;
}


/**
 * 支付手续费账户号
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 *

 */

function getAccountHandingChargeSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,nConsignClientID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";

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
	    sql += " and a.nStatusID in (1,2,3,7,8) and a.nCheckStatusID=4 ";
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
	if (nAccountTypeID !=-1&&nAccountTypeID!=null&&nAccountTypeID!='')
	{
		sql += " and a.nAccountTypeID in (" + nAccountTypeID+") ";
	}
	else if (nAccountTypeID == -100)
	{
		sql += " and at.nAccountGroupID in (4,5) ";
	}
	if (lReceiveOrPay > 0)
	{
		//收或付(目前没有用)
	}	

	if (nClientID > 0&& nConsignClientID > 0 )
	{
		sql += " and a.nClientID in (" + nClientID + "," + nConsignClientID + ")";
	}

	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	
	
	sql += " order by a.sAccountNo ";
    return sql;
    
    
}

/**
 * 合同放大镜  带出帐户号
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
function getContractSQLWithAccount(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID,lReceiveOrPay,lAccountGroupTypeID,lTransactionType)
{      
	
	var sql = "select distinct a.id accountid, a.saccountno accountno,a.naccountgroupid groupid,c.ID ContractID,c.sContractCode Code,c.nBorrowClientID ClientID, client.sname ClientName,client.scode ClientCode,c.nTypeID Type ";
   	sql +=" ,c.mloanamount m,decode(c.dtfactenddate,null,to_char(c.DTENDDATE,'yyyy-mm-dd'),to_char(c.dtfactenddate,'yyyy-mm-dd')) as dtEndDate,decode(c.nTypeID,1,'自营贷款',2,'委托贷款') as nTypeID"; 
    sql += " from loan_contractform c,client,"
    sql += "( select a.nclientid,a.id,a.saccountno,at.naccountgroupid from sett_Account a, sett_AccountType at"
    sql += " where a.nAccountTypeID = at.ID "
    if (lReceiveOrPay == -1000)
	{
		//帐户信息查询专用，可以查出所有状态的帐户
	    sql += " and a.nCheckStatusID=4 ";
	}
	else if (lReceiveOrPay == -10000)
	{
		//不显示只收不付冻结账户 
		sql += " and a.nStatusID in (1,8) and a.nCheckStatusID=4 ";
	}
	else
	{
	    sql += " and a.nStatusID in (1,2,3,4,7,8) and a.nCheckStatusID=4 ";
	}
	if (lAccountGroupTypeID == 1)
	{
		sql += " and at.nAccountGroupID in (1,7)";
	}
	else if (lAccountGroupTypeID > 1)
	{
		sql += " and at.nAccountGroupID = " + lAccountGroupTypeID;
	}
	else if(lAccountGroupTypeID==-12)//所有存款帐户
	{
		sql += " and at.nAccountGroupID in (1,2,3,7)";
	}
	else if(lAccountGroupTypeID==-1000)//所有贷款帐户
	{
		sql += " and at.nAccountGroupID in (4,5,6,8)";
	}
	if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID = "+nOfficeID;
	}
	if (nCurrencyID > 0)
	{

		sql += " and a.nCurrencyID = " + nCurrencyID;
	}
    sql += ") a";
    sql += " where c.nborrowclientid=client.id and c.nOfficeID is not null ";
    sql +=" and c.nborrowclientid = a.nclientid(+) "
    if (nOfficeID > 0)
	{
		sql += " and c.nOfficeID = " + nOfficeID;
		
	}
	if (nCurrencyID > 0)
	{
		sql += " and c.ncurrencyid = " + nCurrencyID;
	
	}
	if (sTypeIDArray != null && sTypeIDArray.length > 0)
	{
		sql += " and c.nTypeID in ("+sTypeIDArray+")";
	}
	if (sStatusIDArray != null && sStatusIDArray.length > 0)
	{
		sql += " and c.nStatusID in ("+sStatusIDArray+")";
		//sql += " and contract.nStatusID >0";
	}
	if (sContractCode != null && sContractCode.length > 0)
	{
		sql += " and c.sContractCode like '%" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and c.nborrowclientid = " + nClientID;
	}
	if(lTransactionType==322)//买入买断补录发放 addby wangzhen
	{
	sql += " and c.isbuyinto=1 ";
	}
	else  if(lTransactionType==17){
	sql += " and (c.isbuyinto !=1 or c.isbuyinto is null) ";
	}
	sql += " order by c.sContractCode";
    return sql;
}

/**
 * 内部拆借（收回）业务-机构放大镜
 * add by kevin(刘连凯) 2011-07-15
 * sOfficeNo　机构编号
 */
function getLendingOfficeSQL(lCurrenyID,sOfficeNo,isValidOfLendAccBalance,isValidOfReserveAccBalance)
{
	var sql = "select distinct office.id officeId, office.scode   officeCode,  office.sname   officeName,client.id officeclientid ,";
	sql += " lend.accountid lendAccId,  lend.accountno lendAccNo,  lend.mbalance  lendAccBalance, ";
	sql += " reserve.accountid reserveAccId,   reserve.accountno reserveAccNo,  reserve.mbalance  reserveAccBalance ";
	sql += " from office office, client client, ";
	sql += " (select a.id accountid,  a.saccountno accountno,   a.nofficeid officeid,   a.nclientid clientid "
	if(isValidOfLendAccBalance){
		sql += " ,decode(b.mbalance-b.muncheckpaymentamount,0, '0.00',  TO_CHAR(b.mbalance-b.muncheckpaymentamount, '999,999,999,999,999,999,999.99') "
	}else{
		sql += " ,decode(b.mbalance,  0, '0.00',  TO_CHAR(b.mbalance, '999,999,999,999,999,999,999.99') "
	}
	sql += " ) mbalance ";
	sql += " from sett_account a, sett_subaccount b, sett_accounttype c  ";
	sql += "  where a.id = b.naccountid  and a.naccounttypeid = c.id   and a.nStatusID in (1, 2, 3, 4, 7, 8) and c.nstatusid>0   and b.nstatusid>0 and c.naccountgroupid = 16 and a.ncurrencyid="+lCurrenyID+" ) lend,";
	sql += "  (select a.id accountid,  a.saccountno accountno,   a.nofficeid officeid,  a.nclientid clientid "
	if(isValidOfReserveAccBalance){
		sql +=" , decode(b.mbalance-b.muncheckpaymentamount, 0, '0.00', TO_CHAR(b.mbalance-b.muncheckpaymentamount, '999,999,999,999,999,999,999.99') "
	}else{
		sql +=" , decode(b.mbalance, 0, '0.00', TO_CHAR(b.mbalance, '999,999,999,999,999,999,999.99') "
	}
	sql +=" ) mbalance ";
	sql += " from sett_account a, sett_subaccount b, sett_accounttype c ";
	sql += "  where a.id = b.naccountid   and a.naccounttypeid = c.id   and a.nStatusID in (1, 2, 3, 4, 7, 8) and c.nstatusid>0   and b.nstatusid>0 and c.naccountgroupid = 14 and a.ncurrencyid="+lCurrenyID+" ) reserve ";
	sql += " where client.isinstitutionalclient = office.id  ";
	sql += "  and client.id = lend.clientid(+)   and client.id = reserve.clientid(+)   and client.nStatusID>0  and office.nstatusid>0 ";
	
	 
	if (sOfficeNo != null && sOfficeNo != "") 
	{
		sql += " and office.sCode like '%" + sOfficeNo + "%' ";
	}
	sql += " order by office.sCode";
	
	return sql;
}
/**
 *内部拆借（返款）业务-账户放大镜
 * add by kevin(刘连凯)2011-07-15
 * nOfficeID 机构ID
 * nCurrencyID 币种ID
 * nAccountGroupType 账户组类型
 * nAccountTypeID 账户类型
 * lReceiveOrPay 收/付款
 * nClientID 客户id
 * sAccountNo 账户编号
 * isAvaBalance 金额是否为可用金额 true：可用余额，false 当前余额
*/
function getLendAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo,isAvaBalance){      

	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName,  o.id as OfficeID,  o.scode as OfficeNo, o.sname as OfficeName, ";
	if(isAvaBalance){
	     sql += "decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00' ,TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	}else{
		sql += "decode((ss.mbalance),0,'0.00',TO_CHAR(ss.mbalance,'999,999,999,999,999,999,999.99') ) mBalance"
	}	
	sql += " from sett_Account a, Client c,sett_AccountType at ,office o, ";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id  and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";
    sql += " and a.nStatusID in (1,2,3,4,7,8) and a.nCheckStatusID=4  and c.isinstitutionalclient=o.id  and o.nstatusid>0  and c.nstatusid>0 ";	
	if (nCurrencyID > 0)
	{
		sql += " and a.nCurrencyID = " + nCurrencyID;
	}
	if (nAccountGroupType > 0)
	{
		sql += " and at.nAccountGroupID = " + nAccountGroupType;
	}	
	if (nAccountTypeID > 0)
	{
		sql += " and a.nAccountTypeID = " + nAccountTypeID;
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
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
    return sql;
}

function getLendAccountSQL_a(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,sAccountNo,isAvaBalance)
{
	return getLendAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,-1,sAccountNo,isAvaBalance);

} 

function  getInstitutionalclientSQL(sClientNo)
{
       var sql;
	     sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as ClientOfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	     sql += " from client a, office b ";
	     sql += " where a.IsInstitutionalclient = b.id and a.nStatusID > 0";
	    
	    
	     if (sClientNo != null && sClientNo != "") 
	     {
		    sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	     } 
	     sql += " order by a.sCode";

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

function getReserveAccountSQL(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,ClientBelongOfficeID,sAccountNo)
{      
	/*-----------Modify By fxzhang 06-12-26 ----------------------*/
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName,f.id as  ClientBelongOfficeID , f.scode as ClientBelongOfficeCode,f.sname as  ClientBelongOfficeName, ";
	sql += " decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance"
	sql += " from sett_Account a, Client c,sett_AccountType at ,office f,";
	sql += " (select sub.* from sett_account acc,sett_subaccount sub,sett_accounttype acctype where acc.id=sub.naccountid and acc.naccounttypeid=acctype.id and acctype.naccountgroupid = 1 and sub.nStatusID > 0) ss ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+) and f.id=c.isinstitutionalclient";

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
	if(ClientBelongOfficeID>0)
	{
	
		sql += " and f.id = " +ClientBelongOfficeID ;
	
	}
	if (sAccountNo != null && sAccountNo.length > 0)
	{
		sql += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	sql += " order by a.sAccountNo";
    return sql;
}

function getCheckedUserSQL(officeID,status)
{
	
	var sql = " select * from userinfo u where 2>1 ";
	if(officeID>0)
	{
		sql +=" and u.nofficeid ="+officeID;
	}
	if(status>-1)
	{
		sql +=" and u.nstatusid ="+status;
	}
	sql +=" order by u.id " ;
	return sql;
}

//add by feiye 20081209   ShangYePiaoJuChengDui-DaoQiChengDui
function getInFormSQL1(nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)

{   
		var sql = "";
		
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtoutdate,'yyyy-mm-dd') StartDate,to_char(a.dttodate,'yyyy-mm-dd') EndDate,-1 SubAccountID,0.0 Balance,-1 IsHasFree ";

	    sql += " from loan_inform a,loan_contractform b,Client c ";

		sql += " where a.nContractID = b.id and b.DISCOUNTCLIENTID=c.ID ";
		
		//update dwj 20090304
		var val = valueNnotetypeid();
		
		frmV001.nnotetypeids.value = val;

		sql += " and a.nnotetypeid = " + val;
		//end 
		
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

			sql += " and a.status in ("+sStatusIDArray+")";

		}

		if (sPayFormNo != null && sPayFormNo.length > 0)

		{

			sql += " and a.scode like '%" + sPayFormNo + "%'";

		}


		//sql += " and a.NNOTETYPEID = 2 ";


		sql += " order by a.scode";

    return sql;

}


//add by feiye 20081209 ShangYePiaoJuChengDui-DianFuShouHui
function getInFormSQL2(nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)

{   
	var sql = "";   
		
		
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtoutdate,'yyyy-mm-dd') StartDate,to_char(a.dttodate,'yyyy-mm-dd') EndDate,-1 SubAccountID,0.0 Balance,-1 IsHasFree ";

	    sql += " from loan_inform a,loan_contractform b,Client c ";

		sql += " where a.nContractID = b.id and b.DISCOUNTCLIENTID=c.ID ";

		

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

			sql += " and a.status in ("+sStatusIDArray+")";

		}

		if (sPayFormNo != null && sPayFormNo.length > 0)

		{

			sql += " and a.scode like '%" + sPayFormNo + "%'";

		}


		sql += " and a.NNOTETYPEID = 3 ";


		sql += " order by a.scode";

    return sql;

}

/**==========财企接收人放大镜===============*/
function getFFUserSQL(nOfficeID,nCurrencyID,sUserName)
{
    var sql = "select * from (select u.ID UserID, u.sName UserName,o.sname OfficeName";
	sql += "  from UserInfo u,office o ";
	sql += " where u.nofficeid=o.id  and u.nStatusID = 1 and u.nOfficeID = " + nOfficeID;
	sql += " and u.purviewtype <> 1";
	
	if(sUserName != null && sUserName.length > 0)
	{
	  sql += " and u.sName like '%" + sUserName + "%'";
	}
	sql += " order by u.sName )";
	
	return sql;
}



function getBankAccountSQL(sAccounName){
	var sql = "select ba.sAccountNo,ba.sAccounName from iplan_BankAccountInfo ba where ba.nStatusId=1";
	if(sAccounName != null && sAccounName.length > 0) {
	    sql += " and ba.sAccounName like '%"+sAccounName+"%' ";
	}
	sql += " order by ba.id asc ";
	return sql;
}

function gethidNegociateInterestRate(str)
   		{
   			var sql = "select t.id,t.sname,t.frate from sett_interestrate t where t.naccounttypeid = 4 and t.nstatusid = 1";
	
			if (str != null && str != "") 
				{
					sql += " and (t.frate like '%" + str + "%' or t.sname like '%" + str + "%')";
				}	
			return sql;
   			
   		}
   		
	function getSubjectSQL(lOfficeID,lCurrencyID,strSubjectNo)
   	{
	   		var strSQL=" select sSubjectCode sCode,sSubjectName  sName from Sett_VGLSubjectDefinition ";
			strSQL += " where nStatus = 1 and nOfficeID="+lOfficeID+" and nCurrencyID="+lCurrencyID;
			if(strSubjectNo!=null && strSubjectNo.length>0 )
			{
				strSQL=strSQL + " and sSubjectCode  like '"+strSubjectNo+"%' ";
			}  
			////科目设置使用！
		
		    strSQL += " and nIsLeaf= 1" ; 
			
			strSQL += " order by sSubjectCode  ";
			
			return strSQL;
   	}
   	//公告标题放大镜
	function getBulletinHeaderSQL(lOfficeID)
	{
			var sql="select * from (select id,header from bulletin where OFFICEID="+lOfficeID+" order by id) where 1=1";
			return sql;
	}
	//发布人放大镜
	function getBulletinPersonSQL(lOfficeID)
    {
       //modify by xwhe 2008-3-26 区分系统管理员
        var sql="select u.ID UserID,u.sName UserName  from UserInfo u  where u.nOfficeID = "+lOfficeID+" and id not in (select id from userinfo where nIsAdminUser =1) order by u.ID";
        return sql;
    }

function getSubjectSQL(lOfficeID,lCurrencyID,strSubjectNo)
   	{
	   		var strSQL=" select sSubjectCode sCode,sSubjectName  sName from Sett_VGLSubjectDefinition ";
			strSQL += " where nStatus = 1 and nOfficeID="+lOfficeID+" and nCurrencyID="+lCurrencyID;
			if(strSubjectNo!=null && strSubjectNo.length>0 )
			{
				strSQL=strSQL + " and sSubjectCode  like '"+strSubjectNo+"%' ";
			}  
			////科目设置使用！
		
		    strSQL += " and nIsLeaf= 1" ; 
			
			strSQL += " order by sSubjectCode  ";
			
			return strSQL;
   	}
   	
function getSubjectCode(lOfficeID,bankSubjectType)
{
  	var sql = "select b.ssubjectcode,b.sname,b.sbankaccountcode ";
	sql += " from sett_branch b,bs_bankaccountinfo a ";
	sql += " where a.s_accountno=b.sbankaccountcode and b.nstatusid>0 and a.n_rdstatus>0";
	sql += " and b.nofficeid = " + lOfficeID;
	sql += " and b.banksubjecttype = " + bankSubjectType;
	return sql;
}


function getNotifyDateSql(lOfficeID,lCurrencyID,depositNo,str)
{
	var sql = " SELECT distinct t.id id ,t.depositNo depositNo, to_char(t.notifyDate,'yyyy-mm-dd' ) notifyDate, t.amount amount,u.mrate mrate, to_char(t.notifyDate+(u.nnoticeday-10000),'yyyy-mm-dd')strTakeDate FROM sett_notifyDepositInform t, sett_transopenfixeddeposit u WHERE t.statusid = 1 AND t.depositNo = '" + depositNo+"' AND u.sdepositno = '" + depositNo + "'" + "AND u.nofficeid = "+lOfficeID + " AND u.ncurrencyid = " + lCurrencyID +" AND u.nofficeid = "+lOfficeID +" and u.nstatusid=3" ;   //sql语句		
	sql="select * from ("+ sql +" ) where 1=1 ";		
	if (str != null && str != "") 
   {
 		sql +=  " and notifyDate like '%25"+str+"%25'"; 
   }
   	//document.write(sql);
	return sql;
	
}

/**
 * 委托存款--开户行放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * clientID 客户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQL_WT(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,decode(ab.sBankAccountNo,null,b.sBankAccountCode,ab.sBankAccountNo) BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab,sett_SubAccount sa ";
	    sql += " where b.nStatusID=1 and ab.nBankID(+)=b.ID and ab.nAccountID=sa.nAccountID and nvl(sa.ac_nIsAllBranch,0)<>1 and sa.nStatusID=1";
		sql += "   and ab.nAccountID = " + nAccountID;
		sql += " union ";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
		sql += " from sett_Branch b ";
		sql += " where b.nStatusID=1 and ";
		sql += " (select nvl(sum(ac_nIsAllBranch),0) from sett_subAccount ";
		sql += " where nStatusID>0 and nAccountID in (";
  		sql += " select t1.id from sett_Account t1,sett_accounttype t2 ";
  		sql += " where t1.naccounttypeid = t2.id";
  		sql += " and t2.naccountgroupid in (1,5) ";
  		sql += " and t1.nclientid = " + nAccountID ;
		sql += " ))>1 "
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo";
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName like '%"+sBranchNoOrName+"%')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}

function getAddClientSQL(nOfficeID,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	sql += " and a.id not in ";
	sql += " (select distinct o.clientid ";
	sql += " from ob_admin_of_user o ";
	sql += " where o.isbelongtoclient = 2) ";
	if(nOfficeID>-1)
	{
		sql += " and a.nofficeid = "+nOfficeID;
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}	
	sql += " order by a.sCode";
	return sql;
}
function getAllClientSQL(nOfficeID,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	if(nOfficeID>-1)
	{
		sql += " and a.nofficeid = "+nOfficeID;
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}	
	sql += " order by a.sCode";
	return sql;
}

function getCheckedClientSQL(nOfficeID,sClientNo)
{
	var sql =" select distinct b.sname OfficeName, c.sname ClientName, c.scode ClientNo, c.id ClientID,c.nlevelcode levelcode,1 FromClient ";
	sql += " from ob_user a, office b, client c ";
	sql += " where b.id = c.nofficeid ";
	sql += " and c.id = a.NCLIENTID ";
	sql += " and a.nsaid = -1 ";
	sql += " and a.NSTATUS =1 ";
	if(nOfficeID>-1)
	{
		sql += " and b.id ="+nOfficeID;
	}
	sql += " and (c.ISINSTITUTIONALCLIENT <=0 or c.ISINSTITUTIONALCLIENT is null ) ";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (c.sCode like '%" + sClientNo + "%' or c.sName like '%" + sClientNo + "%')";
	}
	sql += " order by c.sCode ";
	return sql;

}

/**
*业务编码--编码规则放大镜的SQL语句
*
*/
function getCodingRuleSql(lOfficeID,Saved,name)
{
	var sql = " select s.id ,s.name  from SYS_CodingRule s where statusid=1";	
		
	if(name !=null && name.length > 0){
		sql += " and name like '%"+name+"%'";
	}
	sql +=" order by s.id ";	
	
	return sql;
	
}

/**
 * 业务编码--流水号放大镜的SQL语句
 * 
 */
 function getSerialnumberSql(lOfficeID,Saved,name)
{
	var sql = " select t.id ,t.name  from SYS_SERIALNUMBER t where t.OFFICEID="+lOfficeID+" and t.STATUSID="+Saved;		
	
	if(name !=null && name.length > 0){
		sql += " and name like '%"+name+"%'";
	}
	sql +=" order by t.id ";
	
	return sql;
}

function getSubLoanTypeSQL(nOfficeID,lCurrencyID,lLoanTypeID,str){
	var sql = "";
	sql = "select a.ID, a.LoanTypeID, a.Code, a.Name";
	sql += " from Loan_LoanTypeSetting a, Loan_LoanTypeRelation b";
	sql += " where a.ID = b.subLoanTypeID and b.currencyid = " + lCurrencyID + " and a.statusID = 1 ";
	if (nOfficeID > 0){
		sql += " and b.officeID = " + nOfficeID;
	}
	if (lLoanTypeID > 0){
		sql += " and a.loanTypeID = " + lLoanTypeID;
	}
	if (str != null && str != ""){
 		sql +=  " and Name like '%"+str+"%'"; 
   	}
	sql += " order by code";
	
	return sql;
}
function getBusinessTypeSQL(str){
	var sql = "";
	sql = "select distinct NAME,CODE,BUSINESSATTRIBUTEID,ID";
	sql += " from sec_businessType";
	sql += " where 1=1 and ID in (16,17,18,26,27,31,32,33,34,36,37,41,42,46,47,51,52,53,54,56,57,58,61,62,63,64,85,92) and STATUSID = '3'";
	if (str != null && str != ""){
 		sql +=  " and NAME like '%"+str+"%'"; 
   	}
	sql += "order by ID";
	
	return sql;
}
function getTransactionTypeSQL(str){
	var sql = "";
	sql = "select distinct NAME,BUSINESSTYPEID,BUSINESSTYPENAME,ID";
	sql += " from SEC_VBusiTransTypeMagnifier";
	sql += " where 1=1 and businesstypeid in (16,17,18,26,27,31,32,33,34,36,37,41,42,46,47,51,52,53,54,56,57,58,61,62,63,64,85,92) and STATUSID = '3'";
	if (str != null && str != ""){
 		sql +=  " and NAME like '%"+str+"%'"; 
   	}
	sql += " order by ID";
	
	return sql;
}
function getSubCraTypeSQL(nOfficeID,lLoanTypeID,lCraTransactionTypeID,str){
	var sql = "";
	sql = "select distinct a.ID, a.LoanTypeID, a.Code, a.Name";
	sql += " from cra_craTypeSetting a, cra_craTypeRelation b, cra_cratypeactionsetting t";
	sql += " where a.ID = b.subLoanTypeID and a.statusID = 1 ";
	if (nOfficeID > 0)
	{
		sql += " and b.officeID = " + nOfficeID; 
	}
	if (lLoanTypeID > 0) 
	{
		sql += " and a.loanTypeID = " + lLoanTypeID; 
	}
	if (lCraTransactionTypeID > 0) 
	{
		sql += " and a.id = t.loantypeid and t.id = " + lCraTransactionTypeID; 
	}
	if (str != null && str != "") 
    {
 		sql +=  " and Name like '%"+str+"%'"; 
    }
	sql += " order by a.LoanTypeID";
	
	return sql;
}
function getSubCraTransactionTypeSQL(lCraTypeID,lCraTransactionTypeID,str){
	var sql = "";
	sql = "select distinct t.id as craTransTypeId, t.name as craTransTypeName";
	sql += " from cra_cratypeactionsetting t, cra_craTypeSetting s";
	sql += " where t.loantypeid = s.id";
	if(lCraTransactionTypeID!=-1){
		sql+=' and t.id = ' + lCraTransactionTypeID;
	}
	if(lCraTypeID > -1){
		sql+=' and s.id = '+ lCraTypeID;
	}
	if (str != null && str != "") 
    {
 		sql +=  " and craTransTypeName like '%"+str+"%'"; 
    }
	sql+=' and t.statusid = 1';
	sql+=' and t.id not in (11,22,3504)';
	sql+=' order by t.id';
	
	return sql;		
}
function getApprovalSQL(nOfficeID,name){
	var sql = "";
	sql = "select * from os_wfdefine";
	sql += " where 1=1 ";
	if (nOfficeID > 0){
		sql += " and officeid = " + nOfficeID;
	}
	if(name !=null && name!= ""){
	   sql += " and (name like '%"+name+"%' or id like '%"+name+"%')";
	}
	sql+=' order by id';
	
	return sql;		
}
function getUserNameForFinaceSQL(nOfficeID,nStatusID,userName){
	var sql = "";
	sql = "select * from userinfo";
	sql += " where 1=1";
	if (nOfficeID > 0){
		sql += " and nofficeid = " + nOfficeID;
	}
	if (nStatusID > 0){
		sql += " and nstatusid = " + nStatusID;
	}
	if (userName != null && userName != ""){
		sql += " and sname like '%" + userName + "%'";
	}
	sql += " order by id";
	
	return sql;	
}
function getUserNameForEbankSQL(nHidclientID,nOfficeID,nStatusID,userName){
	var sql = "";
	sql = "select o.id,o.sname,o.sloginno,c.sname as name";
	sql += " from ob_user o,client c";
	sql += " where o.nclientid=c.id";
	if (nHidclientID > 0 && nHidclientID != ""){
		sql += " and c.id = " + nHidclientID;
	}
	if (nOfficeID > 0){
		sql += " and o.nofficeid = " + nOfficeID;
	}
	if (nStatusID > 0){
		sql += " and o.nstatus = " + nStatusID;
	}
	if (userName != null && userName != ""){
		sql += " and o.sname like '%" + userName + "%'";
	}
	sql += " order by o.id";
	
	return sql;	
}
function getClientNameSQL(nOfficeID,nStatusID,userName){
	var sql = "";
	sql = "select a.sCode,a.sName,a.id from client a";
	sql += " where 1=1";
	if (nOfficeID > 0){
		sql += " and a.nOfficeID = " + nOfficeID;
	}
	if (nStatusID > 0){
		sql += " and a.nStatusID = " + nStatusID;
	}
	if (userName != null && userName != ""){
		sql += " and a.sName like '%" + userName + "%'";
	}
	sql += " order by a.id";
	
	return sql;	
}
//贷款申请处理客户编号放大镜
function getClient(nOfficeID,nStatusID,clientCode){
	var sql = "";
	sql = "select id,sCode,sName,sLicenceCode from client";
	sql += " where 1=1";
	if (nOfficeID > 0){
		sql += " and nOfficeID = " + nOfficeID;
	}
	if (nStatusID > 0){
		sql += " and nStatusID = " + nStatusID;
	}
	if (clientCode != null && clientCode != ""){
		sql += " and sCode like '%" + clientCode + "%'";
	}
	sql += " order by sCode";
	
	return sql;	
}
/**
 * 客户经理放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
function getCustomerManagerUserID_new(nOfficeID,sname){

	var sql = " select distinct id,id code,sname name from  userinfo   where  nstatusid = 1  and (nIsAdminUser <> 1 or nIsAdminUser is null)";
	
	if (nOfficeID > 0)
	{
		sql += " and nofficeid= " + nOfficeID; 
	}
	if (sname != null && sname != "") 
	{
		sql += " and sname like '%" + sname + "%'";
	}
	
	sql += " order by id";
	return sql;
}

/**
 * 注：次sql特用于财企接口设置v982.jsp页面中放大镜，待财企接口页面统一调整后，可删除，by：zhouxiang 2013-1-4
 * 开户行放大镜的SQL语句（中交）付款方处理
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边帐银行（1，是；其它，不是）
 * nAccountID 帐户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQLForZj_tmp(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName)
{
	var sql = "";
	if (nAccountID > 0)
	{
		//因sql过长，倒致银行付款业务-开户行放大镜白页，去掉部分sql by xintan
		sql = " select * from (";
		sql += " select distinct b.nOfficeID,b.nCurrencyID,b.sCode,b.sName,b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb,bs_bankaccountinfo a";
		sql += " where b.nStatusID=1 "
		sql += " and cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and nvl(a.n_ischeck,1)=1 and nvl(a.n_rdstatus,1)=1"; //hy 
		sql += " ) b where 1=1 ";
	}
	else
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,b.sBankAccountCode BranchAccountNo,a.S_ACCOUNTNO acctcode,a.S_ACCOUNTNAME acctname,to_char(cb.N_BALANCE ,'9,999,999,999,999.99') mBalance";
		sql += " from sett_Branch b,BS_ACCTCURBALANCE cb, bs_bankaccountinfo a ";
	    sql += " where cb.N_ACCOUNTID(%2B)=a.N_ID and a.S_ACCOUNTNO(%2B)=b.sBankAccountCode and b.nStatusID=1 ";
		
		sql += " and nvl(a.n_rdstatus,1) = 1 ";
		sql += " and nvl(a.n_ischeck,1) = 1 ";
		sql += " and nvl(a.n_accountstatus,1) = 1 ";
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
		sql += " and (BranchNo like '%25" + sBranchNoOrName + "%25' or BranchName like '%25"+ sBranchNoOrName+"%25')";
	}	
	sql += " order by BranchNo";
	
	return sql;
}