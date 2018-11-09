/** 
 * 程序名称：MagnifierSQL.js
 * 功能说明：跟放大镜相关的数据库查询的SQL语句
 * 作　　者：xiangzhou 2012-12-19
 */
 
//自营贷款基准利率放大镜
function getBaseRateSQL(lOfficeID,lCurrencyID,changeRate,baseRate){

	var sql = "";
	var lTmpRate = 1+changeRate/100;
 		sql = " select RateID,RateCode,mRate,RateName,trim(RateValue) RateValue,dtValiDate,adjustRate,bankinteresttypeid,dtinput from ("  
 		sql += " select b.id RateID,a.SINTERESTRATENO RateCode,to_char(b.mRate, '990.999999') mRate, a.sinterestratename as RateName,";
    	sql  += " to_char(b.mRate, '990.999999') RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate ";
		if(changeRate != null && changeRate != ""){
			sql += " ,(b.mRate *"+lTmpRate+") as adjustRate,b.nbankinteresttypeid as bankinteresttypeid,b.dtinput as dtinput ";
		}else{
			sql += " ,0 as adjustRate,b.nbankinteresttypeid as bankinteresttypeid ,b.dtinput as dtinput";
		}
    	sql  += " from LOAN_INTERESTRATETYPEINFO a,LOAN_INTERESTRATE  b ";
   	 	sql  += " where a.id = b.nBankInterestTypeID ";
	  	if (lCurrencyID > 0){
	   		sql += " and a.nCurrencyID = "+lCurrencyID+" and a.nOfficeID ="+lOfficeID;
	  	}
	    sql += " and (a.id,b.dtvalidate) in ";
	    sql += " (   select a.id,max(b.dtvalidate) from loan_INTERESTRATETYPEINFO a,loan_InterestRate b ";
		sql += " WHERE ";
		sql += " a.ID=b.NBANKINTERESTTYPEID ";
		sql += " group by a.id )) where 1=1";
		if (baseRate != null && baseRate != "") 
		{
			sql +=  " and mRate like '%"+baseRate+"%'"; 
		}
  		sql += "  order by dtinput desc ";
	
		return sql;
		
}
//客户编号放大镜
function getClientInfoSQL(lOfficeID,nStatusID,str,flag){

	var sql = "";
	var sql = "SELECT id,sCode,sName,sLicenceCode FROM client where 1 = 1 ";
	if(flag == null){
		sql += " and clientbasetype = 1 ";
	}
	if(lOfficeID > 0){
	   	sql += " and nOfficeID = "+lOfficeID;
	}
	if(nStatusID > 0){
	   	sql += " and nStatusID = "+nStatusID;
	}
	if (str != null && str != "") 
	{
		sql +=  " and (sCode like '%"+str+"%' or sName like '%"+str+"%')"; 
	}
	sql += "order by sCode"
	
	return sql ;
		
}
/**==================客户放大镜==================
* lType  查找类型
* lOfficeID 办事处标识
*=====================================*/
function getClientSQL(lOfficeID,nStatusID,strName){

	var strSQL  = " select distinct a.sCode ClientCode, a.ID, a.sName ClientName, ";
	strSQL += " a.sLicenceCode LicenceCode, b.ID OfficeID, b.sCode OfficeCode, b.sName OfficeName ";
	strSQL += " ,a.sLoanCardNo as CardNo,a.sLoanCardPwd as PassWord ";
	strSQL += " ,-1 as NullID,'' as NullName ";
	strSQL += " from Client a, Office b ";
	strSQL += " where a.nOfficeID = b.ID(+)";
	if(lOfficeID > 0)
	{
		strSQL += " and a.nOfficeID = " + lOfficeID;
	}
	if(nStatusID > 0)
	{
		strSQL += " and a.nStatusID = " + nStatusID;
	}
	if (strName != null && strName != "") 
	{
		strSQL +=  " and (a.sName like '%"+strName+"%' or a.sCode like '%"+strName+"%')"; 
	}
	strSQL += " and clientbasetype = 1 ";
	strSQL += " order by a.sCode ";
	
	return strSQL;
}
function getClientSQL_Old(lOfficeID,nStatusID,strName){

	var strSQL  = " select distinct a.sCode ClientCode, a.ID, a.sName ClientName, ";
	strSQL += " a.sLicenceCode LicenceCode, b.ID OfficeID, b.sCode OfficeCode, b.sName OfficeName ";
	strSQL += " ,a.sLoanCardNo as CardNo,a.sLoanCardPwd as PassWord ";
	strSQL += " ,-1 as NullID,'' as NullName ";
	strSQL += " from Client a, Office b ";
	strSQL += " where a.nOfficeID = b.ID(%2B)";
	if(lOfficeID > 0)
	{
		strSQL += " and a.nOfficeID = " + lOfficeID;
	}
	if(nStatusID > 0)
	{
		strSQL += " and a.nStatusID = " + nStatusID;
	}
	if (strName != null && strName != "") 
	{
		strSQL +=  " and (a.sName like '%25"+strName+"%25' or a.sCode like '%25"+strName+"%25')"; 
	}
	strSQL += " and clientbasetype = 1 ";
	strSQL += " order by a.sCode ";
	
	return strSQL;
}
//免还申请处理 合同编号放大镜
function getContractSQLWD(lOfficeID,lCurrencyID,lType,lLoanType,lClientID,lInputUserID,nTypeID,cancelStatus){

	var strSQL = " select a.sContractCode as ContractCode, a.id as ID,nvl(a.NINOROUT,0) InOrOut, nvl(a.NDISCOUNTTYPEID ,0) DiscountType, nvl(a.NREPURCHASETYPEID,0) RepurchaseType ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b,Loan_LoanForm c where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(+) ";
		strSQL +=" and a.NLOANID=c.ID(+) ";
		strSQL += " and a.nOfficeID ="+ lOfficeID;
		strSQL += " and a.nCurrencyID ="+ lCurrencyID;
		
	if(lType==1)//查找委托免还的合同
	{
		strSQL += " and a.nTypeID in ("+nTypeID+") and a.nStatusID!="+cancelStatus;//委托免还
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
	return strSQL;
}
/**===========放款通知单放大镜===============/
 * lType  查找类型  1：委托免还；  2：逾期  3：
 * lLoanType 贷款种类
 * lContractID 合同id
 * lInputUserID 用户标识
 * lContractInputUserID 合同输入人
 *=====================================*/
function getLoanPaySQL(typeForLoanPay,officeID,currencyID,lType,nTypeID,used,normal,lLoanType,lContractID,lInputUserID,lContractInputUserID,str){

	var strSQL = "";
	strSQL = " select distinct a.sCode as LoanPayCode,a.id as id,b.sContractCode as ContractCode,b.ID as ContractID ";
	strSQL += " ,c.ID as ClientID,a.nstatusid  as StatusID,c.sName as ClientName ";
	strSQL += " ,SA.mBalance as Balance  ";
	strSQL += " from LOAN_PayForm a,loan_contractform b,Client c ";
	strSQL += " ,sett_subaccount SA ";
	strSQL += " where 1=1 ";
	strSQL += " and a.ID = SA.Al_NloanNoteid(+) ";
	strSQL += " and a.nContractID=b.ID and c.ID=b.NBORROWCLIENTID ";
	strSQL += " and b.nTypeID in ("+typeForLoanPay+")";//由项目名称决定其贷款类型（所有）
    strSQL += " and a.nstatusid<>0";
	strSQL += " and a.nOfficeID = "+officeID;
	strSQL += " and a.nCurrencyID = "+currencyID;
	strSQL += " and  SA.NSTATUSID >0 "; //如果不加此判断，会查出重复的放款通知单
	if(lType==1)//查找委托免还的合同
	{
		strSQL += " and b.nTypeID in ("+nTypeID+")";//委托免还
		strSQL += " and a.nstatusid not in(0,1,3,20)";
		strSQL += " and SA.Mbalance>0";
	}
	if(lType==2)
	{
	    strSQL += " and a.nstatusid = "+used;
	    strSQL += " and SA.Mbalance>0";
	    strSQL += " and SA.NSTATUSID = "+normal
	}
	//如果有其他的查找类型，可以用lType来区分
	/////////////////////////////////////
	if(lLoanType > 0)
	{
		strSQL += " and b.nTypeID = "+lLoanType ;
	}
	if(lContractID > 0)
	{
		strSQL += " and b.ID = "+lContractID ;
	}
	if(lInputUserID > 0)
	{
		strSQL += " and a.nInputUserID = "+lInputUserID ;
	}
	if(StatusID = 0)
	{
		strSQL += " and a.nstatusid<>"+StatusID;
	}
	if(lContractInputUserID > 0)
	{
		strSQL += " and b.nInputUserID = "+lContractInputUserID ;
	}
	if(str != null && str != ""){
		strSQL += " and a.sCode like '%"+str+"%'"; 
	}
	strSQL += " order by b.sContractCode,a.SCODE ";//按照合同编号大排序， 再按照放款单编号排序
	
	return strSQL;
}

function getLoanPaySQL_Old(typeForLoanPay,officeID,currencyID,lType,nTypeID,used,normal,lLoanType,lContractID,lInputUserID,lContractInputUserID,isBuyInto,str){

	var strSQL = "";
	strSQL = " select distinct a.sCode as LoanPayCode,a.id as id,b.sContractCode as ContractCode,b.ID as ContractID ";
	strSQL += " ,c.ID as ClientID,a.nstatusid  as StatusID,c.sName as ClientName ";
	strSQL += " ,SA.mBalance as Balance  ";
	strSQL += " from LOAN_PayForm a,loan_contractform b,Client c ";
	strSQL += " ,sett_subaccount SA ";
	strSQL += " where 1=1 ";
	strSQL += " and a.ID = SA.Al_NloanNoteid(%2B) ";
	strSQL += " and a.nContractID=b.ID and c.ID=b.NBORROWCLIENTID ";
	strSQL += " and b.nTypeID in ("+typeForLoanPay+")";//由项目名称决定其贷款类型（所有）
    strSQL += " and a.nstatusid<>0";
	strSQL += " and a.nOfficeID = "+officeID;
	strSQL += " and a.nCurrencyID = "+currencyID;
	strSQL += " and  SA.NSTATUSID >0 "; //如果不加此判断，会查出重复的放款通知单
	if(lType==1)//查找委托免还的合同
	{
		strSQL += " and b.nTypeID in ("+nTypeID+")";//委托免还
		strSQL += " and a.nstatusid not in(0,1,3,20)";
		strSQL += " and SA.Mbalance>0";
	}
	if(lType==2)
	{
	    strSQL += " and a.nstatusid = "+used;
	    strSQL += " and SA.Mbalance>0";
	    strSQL += " and SA.NSTATUSID = "+normal
	}
	//如果有其他的查找类型，可以用lType来区分
	/////////////////////////////////////
	if(lLoanType > 0)
	{
		strSQL += " and b.nTypeID = "+lLoanType ;
	}
	if(lContractID > 0)
	{
		strSQL += " and b.ID = "+lContractID ;
	}
	if(lInputUserID > 0)
	{
		strSQL += " and a.nInputUserID = "+lInputUserID ;
	}
	if(StatusID = 0)
	{
		strSQL += " and a.nstatusid<>"+StatusID;
	}
	if(lContractInputUserID > 0)
	{
		strSQL += " and b.nInputUserID = "+lContractInputUserID ;
	}
	if(isBuyInto)
	{
		if(isBuyInto==1)
		{
			strSQL +=" and b.isBuyInto ="+isBuyInto;
		}
		else
		{
			strSQL +=" and (b.isBuyInto ="+isBuyInto+" or b.isBuyInto is null) ";
		}
	}
	if(str != null && str != ""){
		strSQL += " and a.sCode like '%"+str+"%'"; 
	}
	strSQL += " order by b.sContractCode,a.SCODE ";//按照合同编号大排序， 再按照放款单编号排序
	
	return strSQL;
}
/**==========合同放大镜SQL语句===========
* 放款通知单
* lType  查找类型  1：委托免还；  2：逾期  3：放款通知单
* lLoanType 贷款种类
* lClientID 客户标识
* lInputUserID 用户标识
* lCurrencyID 币种标识
* lOfficeID 办事处标识
*
* 区分办事处,币种
*=====================================*/
function getContractSQL(lType,lLoanType,lClientID,lInputUserID,lCurrencyID,lOfficeID,isBuyInto,loanType,nStatusID,nTypeID,strCode){

	var strSQL = " select a.sContractCode as ContractCode, a.id as ID,nvl(a.NINOROUT,0) InOrOut, nvl(a.NDISCOUNTTYPEID ,0) DiscountType, nvl(a.NREPURCHASETYPEID,0) RepurchaseType ";
		strSQL += " ,b.ID as ClientID,b.sName as ClientName ,a.mExamineAmount as mExamineAmount,b.saddress,to_char(a.dtEndDate,'yyyy-mm-dd') as dtEndDate,decode(a.nTypeID,1,'自营贷款',2,'委托贷款',3,'贴现',4,'最高限额',5,'银团贷款'"; //jzw 2010-04-28
		strSQL += " ,6,'转贴现',7,'买方信贷',8,'担保',9,'其他',10,'融资租赁',11,'授信',12,'信贷资产转让') as nTypeID";
		strSQL += " ,-1 as NullID,'' as NullName, ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		
		strSQL += "sum(f.mamount) as paidamount"
		strSQL += " from LOAN_ContractForm a,Client b,Loan_LoanForm c ,loan_PayForm f where 1=1 ";
		
		strSQL += " and  f.ncontractid(+) = a.id ";
		strSQL += " and a.nCurrencyID = " +lCurrencyID;
		strSQL += " and a.nOfficeID = " +lOfficeID;
		
		strSQL += " and a.NBORROWCLIENTID=b.ID(+) ";
		strSQL += " and a.NLOANID=c.ID(+) ";
		
	if(lType==2)//查找逾期的合同
	{
		strSQL += " and a.nTypeID not in ("+nTypeID+")";//逾期没有 “贴现“ 贷款类型
		strSQL += " and a.nStatusID not in ("+nStatusID+")";//去掉已结束的合同  全哨2010-5-14添加 去掉已取消的合同。 
	}else{
		strSQL += " and a.nTypeID in ("+loanType+")";//由项目名称决定其贷款类型（所有）
	}
	
	if(lType==5)//查找贷款合同风险状态变更合同
	{
		strSQL += " and a.nStatusID in ("+nStatusID+") and a.nTypeID not in ("+nTypeID+") ";
	}
	else if(lType==10)//贷款发放通知单新增 add by zyyao 2007-8-4
	{
		strSQL += " and a.nStatusID in ("+nStatusID+") and a.nTypeID in ("+nTypeID+") ";
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
	if(isBuyInto)
	{
		if(isBuyInto==1)
		{
			strSQL += " and a.isBuyInto ="+isBuyInto;
		}
		else
		{
			strSQL += " and (a.isBuyInto ="+isBuyInto+" or a.isBuyInto is null) ";
		}
	}
	if(strCode != null && strCode != ""){
	
		strSQL +=  " and a.sContractCode like '%"+strCode+"%'"; 
	}
	strSQL += " group by a.sContractCode,"
     strSQL += "  a.id ,"
     strSQL += "   nvl(a.NINOROUT, 0) ,"
     strSQL += "   nvl(a.NDISCOUNTTYPEID, 0),"
     strSQL += "   nvl(a.NREPURCHASETYPEID, 0) ,"
     strSQL += "   b.ID,"
     strSQL += "   b.sName,"
    strSQL += "    a.mExamineAmount,"
     strSQL += "   b.saddress,"
     strSQL += "   to_char(a.dtEndDate, 'yyyy-mm-dd'),"
     strSQL += "   decode(a.nTypeID,"
      strSQL += "         1,"
     strSQL += "          '自营贷款',"
     strSQL += "          2,"
      strSQL += "         '委托贷款',"
      strSQL += "         3,"
      strSQL += "         '贴现',"
       strSQL += "        4,"
       strSQL += "        '最高限额',"
       strSQL += "        5,"
      strSQL += "         '银团贷款',"
      strSQL += "         6,"
       strSQL += "        '转贴现',"
      strSQL += "         7,"
       strSQL += "        '买方信贷',"
       strSQL += "        8,"
        strSQL += "       '担保',"
        strSQL += "       9,"
        strSQL += "       '其他',"
        strSQL += "       10,"
        strSQL += "       '融资租赁',"
         strSQL += "      11,"
         strSQL += "      '授信',"
        strSQL += "       12,"
        strSQL += "       '信贷资产转让') "
	strSQL += " order by a.sContractCode ";//按合同编号排序
		
	return strSQL;
	
}
/**
 * 开户行放大镜的SQL语句
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nIsSingleBank 是否单边账银行（1，是；其它，不是）
 * nAccountID 账户ID
 * sBranchNoOrName 开户行编号或名称
 */
function getBranchSQL(nOfficeID, nCurrencyID,nIsSingleBank,nAccountID,sBranchNoOrName){

	var sql = "";
	if (nAccountID > 0)
	{
		sql = "select distinct b.ID BranchID,b.sCode BranchNo,b.sName BranchName,ab.sBankAccountNo BranchAccountNo";
		sql += " from sett_Branch b,sett_AccountBank ab ";
	    sql += " where b.nStatusID=1 and ab.nBankID(+)=b.ID";
		sql += " and ab.nAccountID = " + nAccountID;
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
 * 外部帐户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部帐户编号
 */
function getExtAcctCurrencySQL(nOfficeID,nCurrencyID,sExtAcctNo){      

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
//以下为20100927，wangwei注释,增加账户id参数和boolean型的"是否只能选出借款人的活期户"
//将/iTreasuryWEB/webapp/iTreasury-loan/magnifier/MagnifierSQL.jsp中的getAccountSQL方法增加参数,并修改名称为getAccountSQLnew
function getAccountSQLnew(lOfficeID,lCurrencyID,lClientID,loantype_new,lType,lAccountGroupType,sAccountNo,nStatusID,current,otherDeposit){

	var strSQL ="";
	strSQL = " select a.SACCOUNTNO,c.sName,a.id,a.sName as accountName from SETT_Account a,sett_accounttype b,client c ";
	strSQL += " where a.nClientID=c.ID and a.naccounttypeid = b.id(+) ";
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
		if(loantype_new)
		{
			strSQL += " and a.nClientID = " + lClientID;
		}
	}
	//放款通知单中所使用的活期账户只能是正常(1)、封存(3)以及部分冻结(8)状态并且是已复核完的
	if(lType == 100)
	{
	    strSQL += " and a.NSTATUSID in("+nStatusID+") ";
		strSQL += " and ( b.naccountGroupid = "+current+" or b.naccountGroupid = "+otherDeposit+" )";
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
		strSQL += " and a.sAccountNo like '%" + sAccountNo + "%'";
	}
	if(lAccountGroupType > 0)
	{
		strSQL += " and b.naccountGroupid = "+ lAccountGroupType;
	}
	strSQL += " order by a.sAccountNo ";
	
	return strSQL;
}
/**===========利率放大镜（带出改动后的利率）===============*/
function getRateSQL(lOfficeID,lCurrencyID,changeRate){

	//modify wjliu --begin 改变SQL语句,使页面上显示为保留6位小数的利率 20007/2/6
	var lTmpRate = 1+changeRate/100;
 	var strSQL = " select RateID,RateCode,trim(mRate) mRate,RateName,RateValue,dtValiDate,adjustRate,bankinteresttypeid,dtinput from ("  
 		strSQL += "  select b.id RateID,a.SINTERESTRATENO RateCode,to_char(b.mRate, '990.999999') mRate, a.sinterestratename as RateName,";
    	strSQL  += " to_char(b.mRate, '990.999999') RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate ";
    	
	if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate *"+lTmpRate+") as adjustRate,b.nbankinteresttypeid as bankinteresttypeid,b.dtinput as dtinput ";
	}
	else
	{
		strSQL += ",0 as adjustRate,b.nbankinteresttypeid as bankinteresttypeid ,b.dtinput as dtinput";
	}
    	strSQL  += " from LOAN_INTERESTRATETYPEINFO a,LOAN_INTERESTRATE  b ";
    	strSQL  += " where a.id = b.nBankInterestTypeID and b.dtValiDate <= sysdate ";
	if (lCurrencyID > 0)
	{
		strSQL += " and a.nCurrencyID = "+lCurrencyID+" and a.nOfficeID ="+lOfficeID;
	}
	    strSQL += " and (a.id,b.dtvalidate) in ";
	    strSQL += " (select a.id,max(b.dtvalidate) from loan_INTERESTRATETYPEINFO a,loan_InterestRate b ";
		strSQL += " WHERE ";
		strSQL += " a.ID=b.NBANKINTERESTTYPEID ";
		strSQL += " and  to_char(b.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') ";
		strSQL += " group by a.id ) ";
	    strSQL += " union ";
	    strSQL += " select b.id RateID,a.sInterestRateNo RateCode,to_char(b.mRate, '990.999999') mRate,a.sInterestRateName as RateName,";
		strSQL += " to_char(b.mRate, '990.999999') RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate "
	if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate *"+lTmpRate+") as adjustRate,b.nbankinteresttypeid as bankinteresttypeid,b.dtinput as dtinput ";
	}
	else
	{
		strSQL += ",0 as adjustRate,b.nbankinteresttypeid as bankinteresttypeid ,b.dtinput as dtinput";
	}
		strSQL  += " from loan_INTERESTRATETYPEINFO a,loan_InterestRate  b ";
    	strSQL = strSQL + " where a.id = b.nBankInterestTypeID and b.dtvalidate > sysdate ";
  	if(lCurrencyID > 0)
  	{
   		strSQL += " and a.nCurrencyID = "+lCurrencyID+" and a.nOfficeID ="+lOfficeID;
  	}
	//strSQL = strSQL + " order by sCode,dtValidate";
  	strSQL = strSQL + ") order by DTINPUT desc ";
  	return strSQL;
	//modify by wjliu --end
}
//贷款业务 合同编号放大镜 new
function getContractCodeSql_new(lClientID,strCode,nStatusID,nTypeID,nOfficeID,nCurrencyID,nInputuserID){

	var sql = "";
		sql += "select t.id, t.sContractCode,to_char(t.mloanamount, '9,999,999,999,999,999.00') as mExamineAmount,e.sname as clientName,decode(t.dtfactenddate,null,to_char(t.DTENDDATE,'yyyy-mm-dd'),to_char(t.dtfactenddate,'yyyy-mm-dd')) as dtEndDate,";
 		sql += " decode(t.nTypeID,1,'自营贷款',2,'委托贷款',3,'贴现',4,'最高限额',5,'银团贷款',6,'转贴现',7,'买方信贷',8,'担保',9,'其他',10,'融资租赁',11,'授信',12,'信贷资产转让') as nTypeID";
 		sql += " from loan_ContractForm t,client e WHERE e.id=t.nborrowclientid and t.NSTATUSID in ("+nStatusID+") and t.nTypeID not in ("+nTypeID+")"
 		sql += " and t.nOfficeID = "+nOfficeID+" and t.nCurrencyID = "+nCurrencyID;
 		
	if(lClientID > 0){
		sql += " and t.nBorrowClientID="+lClientID;
	}
	if(nInputuserID > -1){
		sql += " and t.NINPUTUSERID = "+nInputuserID;
	}
	if(strCode != null && strCode != ""){
	 	sql += " and t.sContractCode like '%"+strCode+"%'";
	}
	sql += " order by t.sContractCode";
 		
 	return sql;
}

/****************************贷款信息查询放大镜**********************************/

function getContractCode(nCurrencyID,nOfficeID,nStatusID,nTypeID,strCode)
{
	var sql = "select a.id as id, a.sContractCode as sContractCode, lt.name as loanTypeName, b.name as loanOrganization, ";
		sql += "  a.mexamineamount as mExamineAmount,to_char(a.dtenddate,'yyyy-mm-dd') as endDate ";
		sql += "  from loan_ContractForm a,client_clientinfo b,loan_loantypesetting lt ";
		sql += " where lt.id = a.nSubTypeid and a.nborrowclientid = b.id and a.nTypeID = lt.loantypeid ";
		sql += "   and nOfficeID = "+nOfficeID+" and nCurrencyID = "+nCurrencyID+" and a.nTypeID in ("+nTypeID+") ";
		
	if(nStatusID !=null && nStatusID > 0){
		sql += " and nStatusID != "+nStatusID;
	}
	if(strCode != null && strCode != ""){
	 	sql += " and a.sContractCode like '%"+strCode+"%'";
	}
	sql +=" order by sContractCode";
	
	return sql;	
}
function getContractUser(nOfficeID,nCurrencyID,strUser)
{
	var sql = "select distinct a.nInputUserID as ID,b.sName ";
		sql += " from Loan_ContractForm a,userInfo b "
		sql += " where b.id = a.nInputUserID and a.nOfficeID = "+nOfficeID+" and a.nCurrencyID = "+nCurrencyID;
		
	if(strUser != null && strUser != ""){
	 	sql += " and b.sName like '%"+strUser+"%'";
	}
	return sql;
}