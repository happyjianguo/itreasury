/** 
 * 程序名称：MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：Forest Ming
 * 完成日期：2003年08月07日
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
 
 */

//////////注意//////////
// % 用%25 替代
// + 用%2B 替代
   
 
 
//////////以下SQL，请勿改动/////////////

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
		sql += " and a.sCode like '%25" + sOfficeNo + "%25'";
	}
	sql += " order by a.sCode";
	
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
		sql += " and c2.sCode like '%25" + sParentCorpNo + "%25'";
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
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}
	//added by ryping on 07-08-18
	sql += " and a.clientbasetype = 1 " ;
	//end
	sql += " order by a.sCode";
	
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
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}
	sql += " and a.nCorpNatureID = e.id(%2B)";
	sql += " order by a.sCode";
	
	return sql;
}

/**
 * 评级方案放大镜
 * nOfficeID 办事处ID
 * planID 评级方案ID
 * planName 评级方案名称
 */
function getPlanSQL(nOfficeID,planID,planName)
{
	var sql = "select t1.id   planID, t1.name planName, t2.name standratingName, t3.name targetsetupName from crert_ratingproject  t1,crert_standardrating t2,  crert_targetsetup    t3 ";
	sql += " where t1.standardratingid = t2.id ";
	sql += "  and t1.targetsetupid = t3.id ";
	sql += " and t1.state = 1 and t2.state = 1 and t3.state = 1";
 
    if (nOfficeID > 0)
	{
		sql += " and t1.OFFICEID = " + nOfficeID; 
	}
	 if (planID > 0)
	{
		sql += " and t1.ID = " + planID; 
	}
	if (planName != null && planName != "") 
	{
		sql += " and t1.name like '%25" + planName + "%25' ";
	}
	sql += " order by t1.ID";
	
	return sql;
}

/**
 * 标准等级放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * name 搜索字段
 */
function getStandardRatingSQL(nOfficeID, nCurrencyID, name)
{
	var sql = "";
	sql += " select t.id standardRatingId, t.name standardRatingName, t.description standardRatingDescription";
	sql += " from CRERT_STANDARDRATING t";
	sql += " where t.state = 1";
	sql += " and t.officeid = " + nOfficeID;
	sql += " and t.currencyid = " + nCurrencyID;
	if(name != ""){
		sql += " and t.name like '%25" + name + "%25' ";
	}
	sql += " order by t.inputdate desc";
	return sql;
}

/**
 * 指标体系放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * name 搜索字段
 */
function getTargetSetupSQL(nOfficeID, nCurrencyID, name)
{
	var sql = "";
	sql += " select t.id targetSetupId, t.name targetSetupName, t.description targetSetupDescription";
	sql += " from CRERT_TARGETSETUP t";
	sql += " where t.state = 1";
	sql += " and t.paterid = -1";
	sql += " and t.officeid = " + nOfficeID;
	sql += " and t.currencyid = " + nCurrencyID;
	if(name != ""){
		sql += " and t.name like '%25" + name + "%25' ";
	}
	sql += " order by t.inputdate desc";
	return sql;
}