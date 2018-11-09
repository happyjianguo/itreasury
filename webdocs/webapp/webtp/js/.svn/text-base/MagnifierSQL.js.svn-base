/**
 * 程序名称：MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：Forest Ming
 * 完成日期：2003年08月07日
 * 注意：模糊匹配时，第一个"%"一定要用"%25",否则有问题。
 * 所有的放大镜SQL方法：
 * 1, 
 * 
 */

//////////注意//////////
// % 用%25 替代
// + 用%2B 替代
 
 
 
//////////以下SQL，请勿改动/////////////



/**
 * 资金计划户放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nDepartmentID 部门ID	
 * nDepartmentIDQx 部门ID	
 * sTreaPlanNo 资金计划版本编号
 */
function getTreaPlanSQL(nOfficeID,nCurrencyID,nDepartmentID,nDepartmentIDQx,sStatus,sTreaPlanNo)
{
	var sql = "select distinct a.id treaPlanID,a.code treaPlanNo,a.StatusID status,'FromConstant_2_status' StatusDesc,to_char(a.startDate,'yyyy-mm-dd') startDate,to_char(a.endDate,'yyyy-mm-dd') EndDate  ";
	sql += " from Trea_TreasuryPlan a,Trea_TreasuryPlanDetail b ";
	sql += " where a.StatusID in (" + sStatus + ") and a.id=b.TreasuryPlanID";
 
    if (nOfficeID > 0)
	{
		sql += " and a.officeid = " + nOfficeID; 
	}
	if (nCurrencyID > 0)
	{
		sql += " and a.CurrencyID = " + nCurrencyID;
	}
	if (nDepartmentID > 0)
	{
		sql += " and a.DEPARTMENTID = " + nDepartmentID;
	}
	if (nDepartmentIDQx > 0)
	{
		sql += " and b.AUTHORIZEDDEPARTMENT like '%25<"+nDepartmentIDQx+">%25'";
	}
	if (sTreaPlanNo != null && sTreaPlanNo != "") 
	{
		sql += " and a.Code like '%25" + sTreaPlanNo + "%25'";
	}
	sql += " order by treaPlanNo";
	return sql;
}





/**
 * 上级行项目放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nSonLineID 子行项目ID
 * sParentLineNo 上级行项目编号
 */ 
function getParentLineSQL(nOfficeID,nCurrencyID,sSonLineNo,sParentLineNo)
{   
	//根据传进来的项目编号,截去最后一段,即得到上级项目编号
	var strParentLineNo = "";
	var isRoot = false;
	
	if( sSonLineNo.length == 3 )
	{
		isRoot = true;
	}
	
	for (i = 0;  i < sSonLineNo.length - 4;  i++)
	{
		strParentLineNo += sSonLineNo.charAt(i);
	}
	
	var sql = "";   
	
		sql  = " SELECT  distinct pare.lineno ParentLineNo,pare.linename ParentLineName ,pare.id ParentLineId ";
		sql += " FROM  Trea_Tptemplate pare  ";
	    sql += " WHERE  pare.isLeaf = 0  ";
		sql += "   AND  pare.officeId   =  "+nOfficeID ;
		sql += "   AND  pare.currencyId =  "+nCurrencyID;
		sql += "   AND  pare.statusId = 1   ";
		
		if( isRoot )
		{
			//如果行项目已经是一级行项目，则它没有上级行项目
			sql += "   AND 1 = 2   ";
		}
		
		if ( strParentLineNo != null && strParentLineNo.length > 0)
		{
			sql += " AND pare.lineNo = '" + strParentLineNo + "'";
		}
		if (sParentLineNo != null && sParentLineNo.length > 0)
		{
			sql += " AND pare.lineNo like '%25" + sParentLineNo + "%25'";
		}
		sql += " ORDER by pare.lineNo ";
	
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
	sql += " order by a.sCode";
	
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
	else if (nIsleaf == 0)
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
	/*-----------Modify By Gqfang 04-03-02
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ";
	sql += " from sett_Account a, Client c,sett_AccountType at ";
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID ";
	*/
	//edit by rxie for nhcw
	var sql = "select a.ID as AccountID,a.sAccountNo AccountNo,a.sName AccountName,c.ID as ClientID,c.sCode as ClientNo,c.sName as ClientName ,decode((ss.mbalance - ss.muncheckpaymentamount),0,'0.00',TO_CHAR((ss.mbalance - ss.muncheckpaymentamount),'999,999,999,999,999,999,999.99') ) mBalance";
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
 * 证券交易对手放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nCounterPartID 交易对手Id
 * sCounterPartNO 交易对手No
 */
function getCounterPartSQL(nOfficeID,nCurrencyID,nCounterPartID,sCounterPartNO)
{      
	
	var sql = "  select id counterPartId ,code counterPartNo,name counterPartName ";
	sql    += "  from  sec_counterpart  where statusId > 0 ";
	//if( nCounterPartID > 0 )
	//{
	//	sql    += "  and id = "+nCounterPartID;
	//}
	if (sCounterPartNO != null && sCounterPartNO.length > 0)
	{
		sql += " and code like '%25" + sCounterPartNO + "%25'";
	}
	sql    += "  order by counterPartNo ";
	
    return sql;
}


/**
 * 证券交易类型放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTransactionTypeID 交易类型Id
 * sTransactionTypeName 交易类型No
 */
function getSECTransactionTypeSQL(nOfficeID,nCurrencyID,nTransactionTypeID,sTransactionTypeName)
{      
	
	var sql = "  select id secBusinessTypeId ,name secBusinessTypeName ";
	sql    += "  from  sec_businesstype  where statusId > 0 ";
	//if( nTransactionTypeID > 0 )
	//{
	//	sql    += "  and id = "+nTransactionTypeID;
	//}
	if (sTransactionTypeName != null && sTransactionTypeName.length > 0)
	{
		sql += " and code like '%25" + sTransactionTypeName + "%25'";
	}
	sql    += "  order by id ";
	
    return sql;
}


/**
 * 结算账户类型放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nAccountTypeID 账户类型Id
 * sAccountTypeNO 账户类型No
 */
function getSETTAccountTypeSQL(nOfficeID,nCurrencyID,nAccountTypeID,sAccountTypeNO)
{      
	
	var sql = "  select id accountTypeId ,sAccountType accountTypeName ";
	sql    += "  from  sett_accounttype  where nStatusId > 0 ";
	
	//if( nAccountTypeID > 0 )
	//{
		//sql    += "  and id = "+nAccountTypeID;
	//}
	if (sAccountTypeNO != null && sAccountTypeNO.length > 0)
	{
		sql += " and sAccountTypeCode like '%25" + sAccountTypeNO + "%25'";
	}
	sql    += "  order by nAccountGroupId ";
	
    return sql;
}