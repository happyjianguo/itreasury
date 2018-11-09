/**
 * 程序名称：webcm/MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：ninh
 * 完成日期：2005年02月07日
 * 注意：模糊匹配时，第一个"%"一定要用"%25",否则有问题。
 * 所有的放大镜SQL方法：
 * 1, getClientSQL 客户放大镜
 * 2, getUserSQL 录入人放大镜
 * 3, getBudgetSystemSQL 预算体系放大镜
 * 4, getBudgetperiodSQL 预算周期放大镜
 * 5, getAccountSQL  账户放大镜
 * 6, getBudgetItemSQL 预算项目放大镜
 * 7, getSuperCorp1SQL 上级单位1放大镜
 * 8, getSuperCorp2SQL 上级单位2放大镜
 * 9, getCustomerManagerUserID 查找客户经理放大镜
 * 10,getCorInvestSQL 查询法人客户--对外投资放大镜
 * 11,getCorMangementSQL 查询法人客户--管理层放大镜
 * 12,getCorInvestSQL 查询法人客户--股东层放大镜
 */

//////////注意//////////
// % 用%25 替代
// + 用%2B 替代




/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * clienttype 客户是自然人或法人
 */
function getClientSQL(nOfficeID,clienttype,clientcode)
{
	var sql = "select * from client_clientinfo where statusID=1 ";

    if (nOfficeID > 0)
	{
		sql += " and officeid = " + nOfficeID; 
	}
	if (clienttype != null && clienttype != "") 
	{
		//sql += " and clientbasetype like  '%25" + clienttype + "%25' ";
		sql += " and clientbasetype = '" + clienttype + "'";
	}
	if (clientcode != null && clientcode != "") 
	{
		sql += " and (code like  '%25" + clientcode + "%25' ";
		sql += " or name like '%25" + clientcode + "%25') ";
	}
	
	//modified by mzh_fu 2008/05/30 按编号升序
	//sql += " order by id";
	sql += " order by code ";

	return sql;
}
/**
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getSubClientSQL(nOfficeID,sClientNoOrName,clientid)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client a, office b,clientRelation c ";
	sql += " where a.nofficeid = b.id  and a.id = c.clientid and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNoOrName != null && sClientNoOrName != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNoOrName + "%25' or a.sName like '%25"+sClientNoOrName+"%25')";
	}
 	if (clientid > 0)
	{
		sql += " and (c.clientid = " + clientid + " or c.parentClientid = " + clientid + ") "; 
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
 * 预算体系放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sBudgetSystemNo 预算体系编号
 */
function getBudgetSystemSQL(nOfficeID,nCurrencyID,sBudgetSystemNo)
{
	var sql = "select a.ID BudgetSystemID,a.budgetSystemNo budgetSystemNo,a.budgetSystemName BudgetSystemName ";
	sql += "  from Budget_System a ";
	sql += " where a.StatusID>0 ";
 
    if (nOfficeID > 0)
	{
		sql += " and a.officeid = " + nOfficeID; 
	}
    if (nCurrencyID > 0)
	{
		sql += " and a.CurrencyID = " + nCurrencyID; 
	}
	if (sBudgetSystemNo != null && sBudgetSystemNo != "") 
	{
		sql += " and a.budgetSystemNo like '%25" + sBudgetSystemNo + "%25'";
	}
	sql += " order by a.budgetSystemNo";
	
	return sql;
}

/**
 * 预算周期放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * sPeriodNo 预算周期编号
 */
function getBudgetPeriodSQL(nOfficeID,nCurrencyID,sPeriodNo)
{
	var sql = "select a.ID BudgetPeriodID,a.periodNo BudgetPeriodNo,a.periodName BudgetPeriodName,a.periodType ,a.periodDays";
	sql += "  from Budget_Period a ";
	sql += " where a.StatusID>0 ";
 
   
	if (sPeriodNo != null && sPeriodNo != "") 
	{
		sql += " and a.periodNo like '%25" + sPeriodNo + "%25'";
	}
	sql += " order by a.periodNo";
	
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
		sql += " and a.itemNo like '" + sItemNo + "%25'";
	}
	sql += " order by a.ItemNo";
	return sql;
}

/**
 * 模板预算项目放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountGroupType 帐户组类型
 * nAccountTypeID 帐户类型
 * lReceiveOrPay 收或付
 * nClientID 客户ID
 * sAccountNo 帐户编号
 */
function getTempletBudgetItemSQL(nOfficeID,nCurrencyID,nSystemID,nIsLeaf,sItemNo)
{
	var sql = "select distinct a.id itemID,a.ItemNo itemNo,a.ItemName itemName,b.id SystemID,b.budgetSystemNo SystemNo";
	sql += " from BUDGET_TEMPLET a,Budget_System b";
	sql += " where  a.budgetSystemID = b.id and a.statusID=1";
		
	if (nOfficeID > 0)
	{
		sql += " and b.OfficeID = " + nOfficeID;
	}
	if (nCurrencyID > 0)
	{
		sql += " and b.CurrencyID = " + nCurrencyID;
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
		sql += " and a.itemNo like '" + sItemNo + "%25'";
	}
	sql += " order by a.ItemNo";
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
 * 上级单位1放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
 function getSuperCorp1SQL(id,nOfficeID,sParentCorpNo){
 	getSuperCorp1SQL(id,nOfficeID,sParentCorpNo,"");
 }
function getSuperCorp1SQL(id,nOfficeID,sParentCorpNo,sLevelcode)
{
	//新增及修改客户资料选择上级单位时需要将已被删除的客户筛选掉，因此增加“and statusid=1” 2008-11-13增加 kaishao 
	var sql = " select distinct id,code scode,name sname from client_clientinfo where clientbasetype='1' and statusid=1";
	if (nOfficeID > 0)
	{
		sql += " and OfficeID = " + nOfficeID; 
	}
	if (id>0)
	{
		sql += " and id != " + id;
	}
	if (sParentCorpNo != null &&  sParentCorpNo != "" ) 
	{
		sql += " and (name like '%" + sParentCorpNo + "%'";
		sql += " or code like '%" + sParentCorpNo + "%')";
	}
	if(sLevelcode != null && sLevelcode != "" && sLevelcode != "-1"){
		sql += " and levelcode not like '"+sLevelcode+"%'";
	}
	sql += " order by code";
	return sql;
}



function getManage(nOfficeID,name)
{
	var sql = "select id,sname from userinfo where  nstatusid = 1  ";
	if (nOfficeID > 0)
	{
		sql += " and OfficeID = " + nOfficeID; 
	}
	if (name != null &&  name != "" ) 
	{
		sql += " and sname like '%25" + name + "%25'";
	}
	return sql;
}



/**
 * 客户经理放大镜
 * nOfficeID 办事处ID
 * sParentCorpNo 母公司编号
 */
function getCustomerManagerUserID(nOfficeID,sname)
{
	var sql = " select distinct id,id code,sname name from  userinfo   where  nstatusid = 1  and (nIsAdminUser <> 1 or nIsAdminUser is null)";
	
	
	if (nOfficeID > 0)
	{
		sql += " and nofficeid= " + nOfficeID; 
	}
	if (sname != null && sname != "") 
	{
		sql += " and sname like '%25" + sname + "%25'";
	}
	
	sql += " order by id";
	return sql;
}




/**
 * 查询法人客户--管理层放大镜
 * nOfficeID 办事处ID
 * 
 */
function getCorMangementSQL(nOfficeID)
{
	var sql = " select  distinct a.id,a.code,a.name,engName,simpleName,name2,simpleEngName,engName2,sex,birthday,country,nativePlance,identityCardNo,passportNo,address,marriage,folk,polity,educationBackground,gratulateUniversity,customerManagerUserID,queryPassword,inputDate,serviceLevel,company,serviceAge,employment,position,jobCompetenceDate,jobCompetenceNo,technology,officeAddress,officeTel,officeFax,mobilePhone,mail,resume,managementStyle,health,income,debtInfo,elisorRecord,character,habit,homeAddress,homeTel,homeStatus,familyMember,abstract,extendInfo1,extendInfo2,extendInfo3,extendInfo4,extendInfo5,extendInfo6,extendInfo7,extendInfo8 from client_clientinfo a,client_natureinfo b where  b.clientid = a.id  and clientbasetype like '2'  and a.statusid = 1 ";
	
	
	if (nOfficeID > 0)
	{
		sql += " and a.officeid= " + nOfficeID; 
	}
	
	
	sql += " order by a.id";
	
	return sql;
}


/**
 * 查询法人客户--对外投资放大镜
 * nOfficeID 办事处ID
 * 
 */
function getCorInvestSQL(nOfficeID,nClientID,code)
{
	var sql = " select distinct a.id,a.code, a.name,a.engname,b.loanCardNo,b.loanCardPwd  from client_clientinfo a, client_corporationinfo b ";
	sql += "  where a.id = b.clientid  and  a.clientbasetype like '1'  and a.statusid = 1  ";
	sql += "and a.id <> "+ nClientID+ " and a.id not in (";
	sql +="select partnerid  from client_investofsubsidiary where clientid ="+nClientID;
	sql +=" and statusid=1)";
	
	if (nOfficeID > 0)
	{
		sql += " and a.officeid= " + nOfficeID; 
	}
	if(code!=null &&code!="")
	{
		sql += " and (a.code like '%25" + code + "%25' or a.name like '%25" + code + "%25')";
	}
	
	sql += " order by a.id";
	
	return sql;
}

/**
 * 查询法人客户--对外投资放大镜
 * nOfficeID 办事处ID
 * 
 */
function getCorInvestSQL1(nOfficeID,nClientID,nPartnerID,code)
{
	var sql = " select distinct a.id,a.code, a.name from client_clientinfo a ";
	sql += "  where a.clientbasetype like '1'  and a.statusid = 1  ";
	sql += "and a.id <> "+ nClientID+ " and a.id not in (";
	sql +="select partnerid  from client_investofsubsidiary where clientid ="+nClientID+" and partnerid<>"+nPartnerID;
	sql +=" and statusid=1)";
	
	if (nOfficeID > 0)
	{
		sql += " and a.officeid= " + nOfficeID; 
	}
	if(code!=null &&code!="")
	{
		sql += " and (a.code like '%25" + code + "%25' or a.name like '%25" + code + "%25')";
	}
	
	sql += " order by a.id";
	
	return sql;
}

function getCorPartnerSQL(nOfficeID,nClientID,code)
{
	var sql = " select distinct a.id,a.code, a.name,a.engname,b.loanCardNo,b.loanCardPwd  from client_clientinfo a, client_corporationinfo b ";
	sql += "  where a.id = b.clientid  and  a.clientbasetype like '1'  and a.statusid = 1  ";
	sql += "and a.id <> "+ nClientID+ " and a.id not in (";
	sql +="select partnerid  from client_partnerofclient where  clientid="+nClientID;
	sql +=" and statusid=1)";
	
	if (nOfficeID > 0)
	{
		sql += " and a.officeid= " + nOfficeID; 
	}
	if(code!=null &&code!="")
	{
		sql += " and a.code like '%25" + code + "%25'";
	}
	
	sql += " order by a.id";
	
	return sql;
}



