
/**===========账户放大镜===============
* lType  查找类型
* lOfficeID 办事处标识
* lCurrencyID 货币标识
* lClientID 客户标识
* lAccountType 账户类型
*=====================================*/
function getAccountSQL(lOfficeID,lCurrencyID,lClientID,lType,lAccountGroupType)
{
	var strSQL ="";
	var strAccount="";
	strSQL = " select a.SACCOUNTNO,c.sName,a.id from SETT_Account a,client c,sett_AccountType sat where a.nClientID=c.ID and 1=1 and a.nAccountTypeID=sat.id";
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
	if(lType = 100)//放款通知单中所使用的活期账户只能是正常(1)和封存(3)状态
	{
	    strSQL += " and a.NSTATUSID in(1,3) ";
	}
	if(lAccountGroupType > 0)
	{
	        strSQL += " and sat.naccountGroupid = "+ lAccountGroupType;
	}
	strSQL += " order by a.sAccountNo ";
	return strSQL;
}

/**
 * 下属单位客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getChildClientSQL(nOfficeID,clientId,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
   /**
	if (lParentClientID > 0)
	{
		sql += " and a.id in (select CHILDCLIENTID from childclientprivilege where CLIENTID= "
		+ lParentClientID; 
		sql += " and decode(PRIVILEGETYPE,1,PRIVILEGEVALUE,-1)=1";
		sql += " union select "+lParentClientID+" from dual )"
	}
	*/
	if (clientId > 0)
	{
		sql += " and a.id in (select distinct bs.n_clientid from bs_bankaccountinfo bs where n_id in(select accountid from Ob_bankacctprvgbyclient where clientid ="+clientId+" ))"; 		
	}
    if (nOfficeID > 0)
	{
		sql += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}
	sql += " order by a.sCode";
	
	return sql;
}

//下级账户放大镜过滤字段： add by xfma 2008-8-27
//客户id、国家id、银行类型id、币种id、所有者类型id、收支属性id、是否直连、帐户属性一、二、三、审核状态、帐户状态
//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,Two,Three,isCheck,accountStatus
//officeID 用户的办事处
//flag 判断是否为总行
function getSubAccountSQLForEbank(sAccountNO,clientID,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
		       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName,a.n_inputoroutput as Inputoroutput,b.s_name as BankName";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b,ob_bankacctprvgbyclient bpg where a.n_rdstatus=1 and a.n_bankid=b.n_id and bpg.ACCOUNTID=a.N_ID ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%" + sAccountNO + "%'" + " or a.s_accountname like '%" + sAccountNO + "%'"+" )";
	}
	if (clientID != null && clientID> 0)
	{
		sql += " and bpg.clientid = "+ clientID;
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
	if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != '-1')
	{
		sql += " and b.s_referencecode = '" + dirBankRefcode + "' ";
	}	
	sql += " order by a.s_accountno ";
	
    return sql;
}

	/**==========付款方银行账号放大镜（关联操作权限）====================*/
	function getPayerAccountNoSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or b.sname like '%" + sBankAccountCode + "%' )";
		}
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql = sql+" and b.saccountno in ";
		sql = sql+" (select a.saccountno ";
		sql = sql+" from OB_AccountOwnedByUser a, Sett_Account ai  ";
		sql = sql+" where ai.nStatusID=1  ";
		sql = sql+" and a.saccountno=ai.saccountno ";
		sql = sql+" and a.nUserID="+lUserID;
		sql = sql+" and ai.ncurrencyid="+lCurrencyID+") ";
		
		sql += " order by saccountno  ";
		return sql;
	}
	
	function getPayerAccountNoSQLByDateDic(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname,to_char(s.dtopendate,'yyyy-mm-dd') as dtopendate,nvl(ab.mAmount,0) as dPayerCurrBalance,nvl(ab.mAmount,0)-nvl(cc.UsableBalance,0) as dPayerUsableBalance"
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf,sett_officetime s,"
                 + " (SELECT SUM(nvl(a.mBalance, 0) - nvl(a.muncheckpaymentamount, 0)) AS mAmount,"
          		 + "  b.id as id"
        		 + " FROM SETT_SubAccount a, SETT_Account b"
    		     + " WHERE a.nAccountID = b.id"
        		 + "   AND b.nStatusID = 1";
        		if (lCurrencyID>0)
        			sql += "   AND b.ncurrencyid = "+lCurrencyID;
      		 	sql += "  group by b.id) ab,(SELECT (SUM(mAmount) + SUM(MREALINTEREST) +"
             	+ " SUM(MREALCOMPOUNDINTEREST) + SUM(MREALOVERDUEINTEREST) +"
           		+ " SUM(MREALSURETYFEE) + SUM(MREALCOMMISSION)) UsableBalance,"
           		+ " npayeracctID"
	          	+ "    FROM ob_FinanceInStr"
	       		+ "     WHERE 1 = 1";
	       		if (lCurrencyID>0)	
				 	sql +=" AND ncurrencyid = " + lCurrencyID;
        		sql += "      AND (nStatus = 1 OR nStatus = 10 OR nStatus = 20 OR nStatus = 2 OR"
       			+ "           (nStatus = 3) OR (nStatus = 4 and cpf_stransno is null))";
       			if (lInstructionID != -1)
					sql += " AND ID != " + lInstructionID;
       			sql += "     group by (npayeracctID)) cc,"
       			+ "   SETT_SubAccount sa"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1  and  b.id=cc.npayeracctID(+)"
   				 + " and sa.nAccountID = b.id"
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or b.sname like '%" + sBankAccountCode + "%' )";
		}
		sql = sql + " and s.nofficeid = b.nofficeid ";
		sql = sql + " and s.ncurrencyid = 1 ";
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql = sql+" and b.saccountno in ";
		sql = sql+" (select a.saccountno ";
		sql = sql+" from OB_AccountOwnedByUser a, Sett_Account ai  ";
		sql = sql+" where ai.nStatusID=1  ";
		sql = sql+" and a.saccountno=ai.saccountno  and ab.id=b.id ";
		sql = sql+" and a.nUserID="+lUserID;
		sql = sql+" and ai.ncurrencyid="+lCurrencyID+") ";
		
		sql += " order by saccountno  ";
		return sql;
	}	
	
	
	
	function getPayerAccountNoSQLByDate(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname,to_char(s.dtopendate,'yyyy-mm-dd') as dtopendate"
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf,sett_officetime s"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or b.sname like '%" + sBankAccountCode + "%' )";
		}
		sql = sql + " and s.nofficeid = b.nofficeid ";
		sql = sql + " and s.ncurrencyid = 1 ";
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql = sql+" and b.saccountno in ";
		sql = sql+" (select a.saccountno ";
		sql = sql+" from OB_AccountOwnedByUser a, Sett_Account ai  ";
		sql = sql+" where ai.nStatusID=1  ";
		sql = sql+" and a.saccountno=ai.saccountno ";
		sql = sql+" and a.nUserID="+lUserID;
		sql = sql+" and ai.ncurrencyid="+lCurrencyID+") ";
		
		sql += " order by saccountno  ";
		return sql;
	}	
	/**==========付款方银行账号放大镜（没有关联操作权限）====================*/
	function getPayerAccountSQLWithout(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,Sett_bankAccountOfFiliale baf"
                 + " where a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or b.sname like '%" + sBankAccountCode + "%' )";
		}
		sql = sql+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql += " order by saccountno  ";
		
		return sql;
	}
	
		/**==========付款方银行账号放大镜(取是否是柔性控制)====================*/
		function getPayerAccountNoSQL2(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname,s.issoft "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf ,sett_accountextend s"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 "
                 + " and b.id = s.AccountID(" + encodeURIComponent("+") + ")  " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '%" + sBankAccountCode + "%' ";
		}
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql += " order by saccountno  ";
		
		return sql;
	}
	
	
	/*中交，下划成员单位 付款方放大镜，带出关联的收款方相关资料*/
		function getPayerAccountNoSQL1(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{ 
	    var sql = " select distinct "+ lCurrencyID +" CurrencyID,"+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno dANo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) sactno ,b.ID nActID,b.sname"
				 + ",bs.outAcctNo o1,bs.outAcctName o2,bs.province p,bs.city c,bs.openBankName o3"                
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf,"
                 /*为带出收款方信息添加*/
                 + " (select ba.N_SUBJECTID,ba.S_ACCOUNTNO outAcctNo,"
                 + " ba.S_ACCOUNTNAME outAcctName,bb.S_BRANCHAREASEG1 province,bb.S_BRANCHAREASEG2 city,bb.S_NAME openBankName"
                 + " from bs_bankaccountinfo ba,bs_banksetting bb"
                 + " where bb.n_id(+)=ba.N_BANKID "
                 + " and ba.n_inputoroutput=2"                 
                 + ") bs "                                  
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '%" + sBankAccountCode + "%' ";
		}
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql += " and bs.N_SUBJECTID(+)=b.id";
		sql += " order by sactno  ";
		
		return sql;
	}
	

	/**==========收款方银行账号放大镜（内部转账）====================*/
	function getInternalPayeeAccountNoSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
		
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID,a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno ,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,obacct.ID as nAccountID,sPayeeName as sname "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf,OB_PAYEEINFO obacct"
                 + " where  a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 and c.ID!=97"
                 + " and b.id = baf.NWITHINACCOUNTID(+)"
				 + " and b.nStatusID=1 "
				 + " and obacct.niscpfacct= 1 and obacct.NSTATUSID = 1 and obacct.spayeeacctno=b.saccountno  " //add by zcwang 2007-7-6
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or sPayeeName like '%" + sBankAccountCode + "%')";
		}
		sql = sql+"  and obacct.nclientid="+lClientID+" and b.ncurrencyid ="+lCurrencyID;
		
		sql += " order by saccountno  ";
		return sql;
	}
	
	/**==========收款方银行账号放大镜（国电专用（内部转账））====================*/
	function getInterPayeeAccountNoSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID,a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno ,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno, b.sname, b.id nAccountID  "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf "
                 + " where  a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 and c.ID!=97"
                 + " and b.id = baf.NWITHINACCOUNTID(+)"
				 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and (b.saccountno like '%" + sBankAccountCode + "%' or  b.sname like '%" + sBankAccountCode + "%')";
		}
		sql = sql+" and b.ncurrencyid ="+lCurrencyID;
		sql += " and b.nclientid ="+lClientID;
		sql += " order by saccountno  ";
		
		return sql;
	}
	/**==========收款方银行账号放大镜（本转）====================*/
	function getPayeeBankNOSQL(bisBlurQuery,lClientID,lCurrencyID,sPayeeBankNo,sPayeeName)
	{
		var sql = " select obacct.id id,seacct.id lInterestToAccountID, sPayeeName,sPayeeBankName,SPAYEEPROV,SPAYEECITY,sab.sbankaccountno accountBankNo,spayeeacctno displayAccountNo,"
				+"spayeeacctno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as spayeeacctno  "//modify by zcwang 2007-3-23 本转时限制帐户类型只能是活期帐户
				+"from OB_PAYEEINFO obacct,SETT_ACCOUNT seacct,SETT_ACCOUNTBANK sab ,SETT_accounttype c "  
//
				+",(select aa.SBANKACCOUNTNO,aa.NWITHINACCOUNTID id from Sett_bankAccountOfFiliale aa,SETT_accounttype bb where aa.naccounttype=bb.id(+) ) baf"
				+" where sPayeeName is not null "
				+ " and seacct.id = baf.id(+)"
				//modify by zcwang 2007-3-23
				+ " and seacct.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				//
				+ "  and sab.naccountid(+)=seacct.id "
				+ " and obacct.niscpfacct= 1 and obacct.NSTATUSID = 1 and obacct.spayeeacctno=seacct.saccountno "
				+ " and obacct.nclientid ="+lClientID+" and obacct.ncurrencyid ="+lCurrencyID;
		if (bisBlurQuery == true)
		{
			if (sPayeeName!=null && sPayeeName!="")
			{
				sql = sql + " and obacct.sPayeeName like '%" + sPayeeName + "%' ";
			}
		}
		if (sPayeeBankNo!=null && sPayeeBankNo!="")
		{
			sql = sql + " and obacct.spayeeacctno like '%" + sPayeeBankNo +"%' ";
		}
		sql += " order by obacct.spayeeacctno ";
		//document.write(sql);
		return sql;
	}
	/**==========收款方银行账号放大镜（国电专用）====================*/
	function getInterPayeeBankNOSQL(bisBlurQuery,lClientID,lCurrencyID,sPayeeBankNo,sPayeeName)
	{
		var sql = " select  seacct.id  id,seacct.id lInterestToAccountID,seacct.sname sPayeeName,seacct.saccountno spayeeacctno, sab.sbankaccountno accountBankNo,seacct.saccountno displayAccountNo "
				+" from SETT_ACCOUNT seacct,SETT_ACCOUNTBANK sab ,SETT_accounttype c "  				
				+" where seacct.sname is not null "
				//modify by zcwang 2007-3-23
				+ " and seacct.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				+ " and sab.naccountid(+) = seacct.id "
				//
				+ "  and seacct.nstatusid = 1 "
				+ "  and seacct.nclientid ="+lClientID;
				+ "  and seacct.ncheckstatusid = 4 "
		if (bisBlurQuery == true)
		{
			if (sPayeeName!=null && sPayeeName!="")
			{
				sql = sql + " and seacct.sname like '%" + sPayeeName + "%' ";
			}
		}
		if (sPayeeBankNo!=null && sPayeeBankNo!="")
		{
			sql = sql + " and (seacct.saccountno like '%" + sPayeeBankNo +"%' or seacct.sname like '%" + sPayeeBankNo +"%') ";
		}
		sql += " order by seacct.saccountno ";
		
		return sql;
	}
	
	
	/**==========收款方银行账号放大镜（内转）====================*/
	function getPayeeBankNOSQL1(bisBlurQuery,m_lOfficeID,lCurrencyID,sPayeeBankNo,sPayeeName)
	{
		var sql = "select a.ID as ID, a.sAccountNo displayAccountNo,a.sAccountNo spayeeacctno,  a.sName sPayeeName,  c.ID as ClientID,"
				+" at.naccountgroupid as accountgroupid,c.sCode as ClientNo,c.sName as ClientName,sab.sbankaccountno accountBankNo  "
				+" from sett_Account a,Client c, SETT_ACCOUNTBANK sab, sett_AccountType at "  				
				+" where a.nClientID = c.ID "
				+ " and a.nAccountTypeID = at.ID "	
				+ " and a.nStatusID in (1) "
				+ " and a.nCheckStatusID = 4 "
				+ " and sab.naccountid(+) = a.id "
				//+ " and a.nOfficeID = "+m_lOfficeID //update by dwj 20111104 BUG #9447
				+ " and a.nCurrencyID = "+lCurrencyID
				+ " and at.nAccountGroupID in (1)"
		//add by dwj 20111104 BUG #9447
		if(m_lOfficeID>0)
		{
			sql = sql + " and a.nOfficeID = "+m_lOfficeID
		}
		//update by dwj 20111104 
		if (bisBlurQuery == true)
		{
			if (sPayeeName!=null && sPayeeName!="")
			{
				sql = sql + " and a.sName like '%" + sPayeeName + "%' ";
			}
		}
		if (sPayeeBankNo!=null && sPayeeBankNo!="")
		{
			sql = sql + " and (a.sAccountNo like '%" + sPayeeBankNo +"%' or a.sName like '%" + sPayeeBankNo +"%' )";
		}
		sql += " order by a.sAccountNo ";
		//document.write(sql);
		return sql;
	}
	
	
	
	/**==========收款方银行账号放大镜（汇）====================*/
	function getPayeeAccountNOSQL(bisBlurQuery,lClientID,lCurrencyID,sPayeeAccountNo,sPayeeName)
	{
		var sql = " select id, sPayeeName,spayeeacctno,sPayeeBankName,SPAYEEPROV,SPAYEECITY,SPAYEEBANKEXCHANGENO,SPAYEEBANKCNAPSNO,SPAYEEBANKORGNO from OB_PAYEEINFO  where sPayeeName is not null "
				+ " and niscpfacct= 2 and NSTATUSID = 1 and ncurrencyid ="+lCurrencyID ;
		if(lClientID > 0)
		{
			sql += " and nclientid = "+lClientID;
		}
		
		if (bisBlurQuery == true)
		{
			if (sPayeeName!=null && sPayeeName!="")
			{
				sql = sql + " and sPayeeName like '%" + sPayeeName +"%'";
			}
		}
		
		if (sPayeeAccountNo!=null && sPayeeAccountNo!="")
		{
			sql = sql + " and (spayeeacctno like '%" + sPayeeAccountNo +"%' or sPayeeName like '%" + sPayeeAccountNo +"%')";
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
		var sql ="";
		if(lDepositTypeID == 1)
	    {
	      sql += "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance-a.mUncheckPaymentAmount-nvl(summamount,0) Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";  
	      sql += " from sett_SubAccount a,sett_Account ma,(select nsubaccountid,sum(mamount) summamount from ob_financeinstr where ob_financeinstr.nstatus not in (0,6) group by nsubaccountid) o ";
	      sql += " where 1=1 and a.nAccountID=ma.ID and a.id = o.nsubaccountid(+) and a.nStatusID=1 ";
	    }
		else if (lDepositTypeID == 2)
		{
            sql += "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance-a.mUncheckPaymentAmount Balance,ma.ID AccountID,AF_mRate Rate,AF_NNOTICEDAY Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";	
			sql += " from sett_SubAccount a,sett_Account ma ";
			sql += " where 7=7 and a.nAccountID=ma.ID and a.nStatusID=1 ";
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
			sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) "; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 3 ) "; 			
		}
		else
		{
			sql += " and ma.nAccountTypeID in ( select id from sett_accounttype where naccountgroupid in(2,3) )";
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
        var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		sql += " where 1=1 and a.nAccountID=ma.ID and a.nStatusID=1 and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) ";
 		

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
        var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		sql += " where 2=2 and a.nAccountID=ma.ID and a.nStatusID>0 ";
 		

		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (lDepositTypeID == 1)
		{
			//定期存款
			sql += " and ma.nAccountTypeID  in ( select id from sett_accounttype where naccountgroupid = 2 )"; 
		}
		else if (lDepositTypeID == 2)
		{
			//通知存款
			sql += " and ma.nAccountTypeID in ( select id from sett_accounttype where naccountgroupid = 3 )"; 			
		}
		else
		{
			sql += " and ma.nAccountTypeID in ( select id from sett_accounttype where naccountgroupid in (2,3) )";
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
 * 合同放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nTypeIDArray 合同类型（信托，委托，贴现）
 * nStatusIDArray 合同状态
 * sContractCode 合同编号
 */
 
function getContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
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
		sql += " and contract.sContractCode like '" + sContractCode + "%'";
	}
	if (nClientID > 0)
	{
		sql += " and contract.nborrowclientid = " + nClientID;
	}
	sql += " order by contract.sContractCode";
    return sql;
	
}

/**
 * 放款日期放大镜
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nContractID 合同ID
 * sTypeIDArray 通知单类型
 * sStatusIDArray 通知单状态
 * sPayFormNo 放款通知单号
 */ 
function getPayFormSQL(nOfficeID,nCurrencyID,nClientID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200'))
	{//信托/委托收回，从子帐户取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID,(sa.MBalance-sa.MUNCHECKPAYMENTAMOUNT) LoanBalance,sa.NAccountId LoanAccountID,sa.AL_nLoanNoteID LoanNoteID ";
	    sql += " ,a.mInterestrate*(1 + b.mAdjustrate/100) as rate ";
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
		if (nClientID > 0)
		{
			sql += " and b.nBorrowClientID = " + nClientID;
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
			sql += " and a.scode like '" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID ";
	    sql += " ,a.mInterestrate*(1 + b.mAdjustrate/100) as rate ";
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
		if (nClientID > 0)
		{
			sql += " and b.nBorrowClientID = " + nClientID;
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
			sql += " and a.scode like '" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	
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
function getPayFormNOSQL(nClientID,nOfficeID,nCurrencyID,nContractID,sTypeIDArray,sStatusIDArray,sPayFormNo)
{   
	var sql = "";   
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200' || sStatusIDArray == '-500' || sStatusIDArray == '-600'))
	{//信托/委托收回，从子帐户取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID,(sa.mBalance-sa.mUncheckPaymentAmount) Balance, ";
		sql += " (select decode(count(*),0,0,1) from loan_FreeForm where nPayFormID=a.ID and nStatusID=2) IsHasFree ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa,sett_Account account,loan_FreeForm ff ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID and sa.nAccountID=account.ID and a.ID=ff.nPayFormID(+) ";
		sql += " and account.nAccountTypeID in ( select id from sett_accounttype where naccountgroupid in(4,5) ) ";

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
		if (nClientID > 0)
		{
			sql += "and c.ID = " + nClientID;	
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
			sql += " and a.scode like '" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	else
	{//信托/委托发放，从信贷取值
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID,0.0 Balance,-1 IsHasFree ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID ";
		
		if (nClientID > 0)
		{
			sql += "and c.ID = " + nClientID;	
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
		if (sStatusIDArray != null && sStatusIDArray.length > 0)
		{
			sql += " and a.nStatusID in ("+sStatusIDArray+")";
		}
		if (sPayFormNo != null && sPayFormNo.length > 0)
		{
			sql += " and a.scode like '" + sPayFormNo + "%'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}


/**===========利率放大镜（带出改动后的利率）===============*/
function getRateSQL(lCurrencyID,changeRate)
{
	var lTmpRate = 1+changeRate/100;
 var strSQL = " select RateID,RateCode,mRate,RateName,RateValue,dtValiDate,adjustRate from ("  
 	strSQL += "  select b.id RateID,a.SINTERESTRATENO RateCode,round(b.mRate,6) mRate, a.sinterestratename as RateName,";
    strSQL  += " round(b.mRate,6) RateValue,to_char(b.dtValiDate,'yyyy-mm-dd') as dtValiDate ";
	if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate <%=URLEncoder.encode("*")%>"+lTmpRate+") as adjustRate ";
	}else
	{
		strSQL += ",0 as adjustRate ";
	}
    strSQL  += " from LOAN_INTERESTRATETYPEINFO a,LOAN_INTERESTRATE  b ";
    strSQL  += " where a.id = b.nBankInterestTypeID and b.dtValiDate <= sysdate ";
  /*if (strCode != null && strCode != "") 
  {
   	strSQL += " and a.sInterestRateNo like '<%=URLEncoder.encode("%")%>"+strCode+"<%=URLEncoder.encode("%")%>' ";
  }*/
  if (lCurrencyID > 0)
  {
   	strSQL=strSQL+ " and a.nCurrencyID = "+lCurrencyID;
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
	if(changeRate!=null&&changeRate!="")
	{
		strSQL += ",(b.mRate <%=URLEncoder.encode("*")%>"+lTmpRate+") as adjustRate ";
	}else
	{
		strSQL += ",0 as adjustRate ";
	}
	strSQL  += " from loan_INTERESTRATETYPEINFO a,loan_InterestRate  b ";
    strSQL = strSQL + " where a.id = b.nBankInterestTypeID and b.dtvalidate > sysdate ";
 	/*if (strCode != null && strCode != "") 
  	{
   		strSQL += " and a.sInterestRateNo like '<%=URLEncoder.encode("%")%>"+strCode+"<%=URLEncoder.encode("%")%>' ";
  	}*/
  	if (lCurrencyID > 0)
  	{
   		strSQL=strSQL+ " and a.nCurrencyID = "+lCurrencyID;
  	}
		//strSQL = strSQL + " order by sCode,dtValidate";
  	strSQL = strSQL + ") order by mRate ";
  	return strSQL;
}

/**
 * 外部账户放大镜
 * nOfficeID 办事处ID
 * sExtAcctNo 外部账号
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
	    sql += " where b.nStatusID=1 and ab.nBankID(+)=b.ID";
		
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
		sql += " and (b.sCode like '%" + sBranchNoOrName + "%' or b.sName like '%"+sBranchNoOrName+"%')";
	}	
	sql += " order by b.sCode";
	
	return sql;
}
/**==========合同放大镜SQL语句===========
* nconsignclientid 登陆单位
*=====================================*/
function getContractSQL1(clientid,currencyid)
{

	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(+) ";
		strSQL += " and a.nTypeID in (2)";//委托免还,委托统借统还
		strSQL += " and a.NSTATUSID in (5,6)"//状态为执行中，一展期
		if(currencyid>0)
		{
			strSQL += " and a.ncurrencyid = "+currencyid; 
		}
		if(clientid>0)
		{
			strSQL += " and a.nconsignclientid = "+clientid; //委托单位是登陆单位
		}

		strSQL += " order by a.sContractCode ";//按合同编号排序
	return strSQL;
}

function getContractSQL2(clientid,currency)
{
	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(+) ";
		strSQL += " and a.nTypeID in (8)";//银团
		strSQL += " and a.NSTATUSID in (4,5,6)"//状态为未执行，执行中，以展期
		strSQL += " and a.NCURRENCYID = "+currency
		if(clientid>0)
		{
		strSQL += " and a.NBORROWCLIENTID = "+clientid ; //委托单位是登陆单位
		}
		
		strSQL += " order by a.sContractCode ";//按合同编号排序

	return strSQL;
}

function getContractSQL3(clientid,currency)
{
	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(+) ";
		strSQL += " and a.NSTATUSID in (4,5,6)"//状态为未执行，执行中，以展期
		strSQL += " and a.nTypeID !=3";//不是贴现
		//strSQL += " and a.nTypeID !=8";
		strSQL += " and a.NCURRENCYID = "+currency;
		if(clientid>0)
		{
		strSQL += " and a.NBORROWCLIENTID = "+clientid ; //委托单位是登陆单位
		}
		
		strSQL += " order by a.sContractCode ";//按合同编号排序

	return strSQL;
}
/**==================客户放大镜==================
* lType  查找类型
* lOfficeID 办事处标识
*=====================================*/
/*
function getClientSQL()
{
	var strSQL  = " select distinct a.sCode ClientCode, a.ID, a.sName ClientName, ";
		strSQL += " a.sLicenceCode LicenceCode, b.ID OfficeID, b.sCode OfficeCode, b.sName OfficeName ";
		strSQL += " ,a.sLoanCardNo as CardNo,a.sLoanCardPwd as PassWord ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由与其关联的控件ID和txt
		strSQL += " from Client a, Office b ";
		strSQL += " where a.nOfficeID =b.ID and a.nStatusID= 1"//有效
		strSQL += " order by a.sCode ";
		return strSQL;
}*/	
/**  modify by wjliu --2007/3/14 原放大镜不能区分办事处
 * 客户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getClientSQL(nOfficeID,sClientNo)
{
	var strSQL  = " select distinct a.sCode ClientCode, a.ID, a.sName ClientName, ";
		strSQL += " a.sLicenceCode LicenceCode, b.ID OfficeID, b.sCode OfficeCode, b.sName OfficeName ";
		strSQL += " ,a.sLoanCardNo as CardNo,a.sLoanCardPwd as PassWord ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由与其关联的控件ID和txt
	//var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient ";
		strSQL += " from client a, office b ";
		strSQL += " where a.nofficeid = b.id and a.nStatusID > 0";
 
    if (nOfficeID > 0)
	{
		strSQL += " and a.nofficeid = " + nOfficeID; 
	}
	if (sClientNo != null && sClientNo != "") 
	{
		strSQL += " and (a.sCode like '%" + sClientNo + "%' or a.sName like '%" + sClientNo + "%')";
	}
	strSQL += " order by a.sCode";
	
	return strSQL;
}

function getClient()
{
	var sql = "SELECT id,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";
	return sql ;
}

/**===========放款通知单放大镜===============/
* lType  查找类型  1：委托免还；  2：逾期  3：
* lLoanType 贷款种类
* lContractID 合同id
* lInputUserID 用户标识
* lContractInputUserID 合同输入人
*=====================================*/
function getLoanPaySQL(lContractID,lCurrencyID,lClientID)
{
	var strSQL ="";
	strSQL = " select a.sCode as LoanPayCode,a.id as id,b.sContractCode as ContractCode,b.ID as ContractID ";
	strSQL +=" ,c.ID as ClientID,c.sName as ClientName ";
	strSQL +=" ,SA.mBalance as Balance  ";
	strSQL +=" from LOAN_PayForm a,loan_contractform b,Client c ";
	strSQL +=" ,sett_subaccount SA ";
	strSQL +=" where 1=1 ";
	strSQL +=" and a.ID = SA.Al_NloanNoteid(+) ";
	strSQL +=" and a.nContractID=b.ID and c.ID=b.NBORROWCLIENTID ";
	strSQL += " and b.nTypeID in (2)";//委托免还,委托统借统还
	strSQL += " and a.nstatusid in (4)";//使用used
	//var  = 0;
	if(lCurrencyID > 0)
	{
		strSQL += " and b.ncurrencyid = " + lCurrencyID ;
	}
	if(lContractID > 0)
	{
		strSQL += " and b.ID = " + lContractID ;
	}
	if(lClientID > 0)
	{
		strSQL += " and b.nConsignClientId = " + lClientID ;
	}
	strSQL += " order by b.sContractCode,a.SCODE ";//按照合同编号大排序， 再按照放款单编号排序
	return strSQL;
}

function getClient()
{
	var strSQL  = " select distinct a.sCode ClientCode, a.ID, a.sName ClientName";
		strSQL += "  from Client a where  a.nStatusID= 1 order by a.sCode ";
		return strSQL;
}	

/**==========用户放大镜===============*/
function getUserSQL(nOfficeID,nCurrencyID,nClinetID,sUserName)
{
    var sql = "select u.ID UserID,u.sName UserName ,c.sname ClinetName";
	sql += " from ob_user u ,client c";
	sql += " where u.nStatus=1 and u.nClientID = c.id";
    if (nOfficeID > 0)
	{
		sql += " and u.nOfficeID = " + nOfficeID;
	}
    if (nClinetID > 0)
	{
		sql += " and c.id = " + nClinetID;
	}
	if(sUserName != null && sUserName.length > 0)
	{
	  sql += " and u.sName like '%" + sUserName + "%'";
	}
	sql += " order by u.sName";
	
	return sql;
}

function getApprovalSettingSQL(OfficeID,CurrencyID)
{
    var strSQL  =" select rownum,ID,sName ";        
        strSQL +=" from ob_approvalsetting where 1=1 ";
		strSQL +=" and nStatusID = 2 ";//激活状态
		strSQL +=" and nOfficeID = " + OfficeID;
		strSQL +=" and nCurrencyID = " + CurrencyID;   
    //strSQL += "  order by ID ";
    return strSQL;
}

function getApprovalSettingSQL(nOfficeID,lApprovalID,nClientID)
{
	var sql = "select a.ID, a.sName,rownum ";
	sql += " from ob_approvalSetting a,ob_user b ";
	sql += " where a.nInputUserID=b.id and a.nStatusID = 2 ";
 
   if (nOfficeID > 0)
	{
		sql += " and a.nOfficeID = " + nOfficeID; 
	}
   if (nClientID > 0)
	{
		sql += " and b.nClientID = " + nClientID; 
	}
   if (lApprovalID > 0)
	{
		sql += " and a.ID = " + lApprovalID; 
	}
	return sql;
}

function getPeriodSetting()
{
	var sql = "select id,PERIOD,PERIODNAME,STARTDATE from SETT_PERIODSETTING";
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
function getAccountSQL1(nOfficeID,nCurrencyID,nAccountGroupType,nAccountTypeID,lReceiveOrPay,nClientID,sAccountNo)
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
	sql += " where a.nClientID=c.ID and a.nAccountTypeID=at.ID and    a.id = ss.naccountid(+)";

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

/**
**??????
*/
function getApprovalRelationSettingSQL(lOfficeID,lClientID)
{
    var strSQL  =" select a.ID,a.author,a.Name ";        
        strSQL +=" from os_ob_wfdefine a where 1=1 ";
		strSQL +=" and a.officeID = " + lOfficeID;  
		strSQL +=" and a.clientID = " + lClientID;  
		strSQL +=" order by a.createDate desc"; 
    return strSQL;
}

/**===========???????===============
* lCurrencyID 币种ID
* lClientID 客户ID
* sAccountNo 账号
*=====================================*/
function getBankAccount(nCurrencyID,nClientID,sAccountNo)
{ 
	var strSQL ="";
	strSQL = " select t.n_id id,t.s_accountno accountno,t.s_accountname name from bs_bankaccountinfo t where t.n_accountstatus=1 and t.n_rdstatus=1 and t.n_ischeck=1";
 
	if(nCurrencyID > 0)
	{
		strSQL += " and t.n_currencytype=" + nCurrencyID;
	}
	if(nClientID > 0)
	{
		strSQL += "  and t.n_clientid=" + nClientID;
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
		strSQL += "  and t.s_accountno like '%" + sAccountNo + "%'";
	}
	strSQL += " order by t.s_accountno ";
	return strSQL;
}
	
function getBudgetPayerAccountNoSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID,officeId)
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
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID+" and b.nofficeid = "+officeId;
		sql += " order by saccountno  ";
		return sql;
}
/**===========?????===============
* lCurrencyID 币种ID
* lClientID 客户ID
* sAccountNo 账号
*=====================================*/
function getOBBudget(nClientID,sBudgetname,status,officeid,currencyid,type)
{ 
	var strSQL ="";
	if(type == 1){
		strSQL = "select t.id,t.sname,t.accountid,b.saccountno s_accountno,to_char(t.modifydate,'yyyy-mm-dd hh24:mi:ss') modifydate,to_char(t.startdate,'yyyy-mm-dd') startdate,to_char(t.enddate,'yyyy-mm-dd') enddate,rPAD(LPAD(to_char(t.startdate,'yyyy-mm-dd'),10,'?'),24,LPAD(to_char(t.enddate,'yyyy-mm-dd'),14,' to ')) area,"
 									+"(case t.status when 1 then '已保存'" 
 									+" when 2 then '已提交' "
              					  	+" when 3 then '已审批' "
              					  	+" when 4 then '未拨付' "
              					  	+" when 5 then '已拨付' "
              					  	+" when 6 then '拨付失败' "
              					  	+" when 7 then '被调整' "
              					  	+" end) as status "
              					  	+" from ob_budget t,sett_account b where status<>0 and t.accountid=b.id and t.parentbudgetid = -1 and t.clientid="+nClientID+" and t.officeid = "+officeid+" and t.currencyid = "+currencyid;
	 }else if(type == 0){
	 	strSQL = "select t.id,t.sname,t.accountid,b.saccountno s_accountno,to_char(t.modifydate,'yyyy-mm-dd hh24:mi:ss') modifydate,to_char(t.startdate,'yyyy-mm-dd') startdate,to_char(t.enddate,'yyyy-mm-dd') enddate,rPAD(LPAD(to_char(t.startdate,'yyyy-mm-dd'),10,'?'),24,LPAD(to_char(t.enddate,'yyyy-mm-dd'),14,' to ')) area "
	 			+" from ob_budget t,sett_account b where status<>0 and t.accountid=b.id and t.parentbudgetid = -1 and t.clientid="+nClientID+" and t.officeid = "+officeid+" and t.currencyid = "+currencyid;
	 }
	 if(status > 0)
	{
		strSQL += " and t.status="+status;
	}
	if(sBudgetname != null && sBudgetname.length > 0)
	{
		strSQL += "  and t.sname like '%" + sBudgetname + "%'";
	}
	strSQL += " order by t.startdate desc ";
	return strSQL;
}

/**===========银行直联 付款方放大镜sql===============
* lCurrencyID ????????
* lClientID ????????
* sAccountNo ????????
*=====================================*/
function getBankAccount(sAccountNo,nOfficeID,nClientID,CurrencyID,lUserID)
{ 
	var strSQL ="";
	strSQL = " select t.n_id id,t.s_accountno accountno,t.s_accountname name,t.s_branchname bankname,NVL(to_char(a.n_usablebalance,'999,999,999,990.99'),TO_CHAR(0.00,'0.00')) currentbalance from bs_bankaccountinfo t,bs_acctcurbalance a,client_clientinfo cc  where a.n_accountid(%2B)=t.n_id and t.n_clientid=cc.id  and t.n_accountstatus=1 and t.n_rdstatus=1 and t.n_ischeck=1 ";
 
	if(CurrencyID > 0)
	{
		strSQL += " and t.n_currencytype=" + CurrencyID;
	}
	if(nClientID > 0)//????????
	{
		strSQL += "  and t.n_clientid=" + nClientID;
	}
	else{//????????
		strSQL+="  AND t.n_officeid ="+nOfficeID+" ";
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
		strSQL += "  and t.s_accountno like '%" + sAccountNo + "%'";
	}
	strSQL += " and t.s_accountno in ";
	strSQL += " (select e.saccountno ";
	strSQL += " from OB_EbankAccountByUserOperation e,bs_bankaccountinfo t ";
	strSQL += " where e.saccountno=t.s_accountno ";
	strSQL += " and t.n_accountstatus=1 ";
	strSQL += " and t.n_rdstatus=1 ";
	strSQL += " and t.n_ischeck=1 ";
	strSQL += " and e.nuserid="+lUserID;
	strSQL += " and t.n_currencytype="+CurrencyID+") ";
	
	strSQL += " order by t.s_accountno ";
	return strSQL;
}
function getBankAccountforbankpay(sAccountNo,nOfficeID,nClientID,CurrencyID)
{ 
	var strSQL ="";
	strSQL = " select t.n_id id,t.s_accountno accountno,t.s_accountname name,t.s_branchname bankname,NVL(to_char(a.n_usablebalance,'999,999,999,990.99'),TO_CHAR(0.00,'0.00')) currentbalance from bs_bankaccountinfo t,bs_acctcurbalance a,client_clientinfo cc  where a.n_accountid(+)=t.n_id and t.n_clientid=cc.id  and t.n_accountstatus=1 and t.n_rdstatus=1 and t.n_ischeck=1 ";
 
	if(CurrencyID > 0)
	{
		strSQL += " and t.n_currencytype=" + CurrencyID;
	}
	if(nClientID > 0)//????????
	{
		strSQL += " and t.n_clientid=" + nClientID;
	}
	else{//????????
		strSQL+="  AND t.n_officeid ="+nOfficeID+" ";
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
		strSQL += "  and t.s_accountno like '%" + sAccountNo + "%'";
	}
	
	
	strSQL += " order by t.s_accountno ";
	return strSQL;
}
/**===========Edited by ylguo===============
* lCurrencyID 币种ID
* lClientID 客户ID
* lOfficeID 办事处ID
*=====================================*/
function getFundPlanSQL(someCpCode,lCurrencyID,lClientID,lOfficeID)
{ 

	var strSQL ="";
	strSQL = " select ob.id,ob.cpcode from ob_capitalplan ob where ob.statusid != 0 ";
 	if(lCurrencyID > 0)
	{
		strSQL += " and ob.currencyid = " + lCurrencyID;
	}
	if(lClientID > 0)
	{
		strSQL += " and ob.clientid = " + lClientID;
	}
	if(lOfficeID > 0)
	{
		strSQL+=" and ob.officeid = "+lOfficeID;
	}
	 if(someCpCode != null && someCpCode !="")
	{
		strSQL+=" and ob.cpcode like '%"+someCpCode+"%'";
	}
	strSQL += " order by ob.id ";
	
	return strSQL;
}

/**
*网上银行 定期续存专用放大镜！！
*/
function getFixedDepositNoSQLOB(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
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
		
        var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
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
	else if (nTypeID == 3 || nTypeID == 4)
	{
		//(3)定期续存--业务处理时使用
		//(4)定期续存--业务复核
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.AF_DTSTART,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate, c.sCode  ClientNo,c.sname  ClientName,ma.sAccountNo  AccountNo, SUBSTR(ma.sAccountNo,1,2) AccountNo1,SUBSTR(ma.sAccountNo,4,2) AccountNo2,SUBSTR(ma.sAccountNo,7,4) AccountNo3,1 as FromClient,c.ID as ClientID,sat.naccountgroupid as accountgroupid";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat,client c";
		sql += " where a.nAccountID=ma.ID and a.nStatusID in (1,7) and ma.nAccountTypeID=sat.id and sat.nStatusID=1 and sat.nAccountGroupID = 2 and ma.nClientID=c.ID";
		//sql +=" and a.af_sDepositNo not in ";
		//sql +=" (select distinct o.sDepositBillNo ";		
		//sql +=" from ob_financeinstr o ";		
		//sql +=" where o.ntranstype = 19 ";		
		if(nTypeID == 3)
		{
		//	sql +=" and o.nstatus not in (0, 6)) ";	
		}else if (nTypeID == 4)
		{
			//sql +=" and o.nstatus not in (0,1,6)) ";		
		}
		if (nCurrencyID > 0)
		{
			sql += " and ma.nCurrencyID = " + nCurrencyID; 
		}
		if (nAccountID > 0)
		{
			sql += " and a.nAccountID = " + nAccountID; 
		}
		sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
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
        var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,a.mBalance Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ,sett_accountType sat";
		sql += " where a.nAccountID=ma.ID and a.nStatusID>0 ";
		sql += " and ma.nAccountTypeID = sat.id and sat.nStatusID=1";
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
 *=============== Added by leiyang ===============
 * sUrl 要打开的窗口
 * strHiddenName 隐藏域名称
 * strHiddenValue 隐藏域值
 *================================================
 */
function openMagnifier(sUrl, strHiddenName, strHiddenValue)
{
	var form = document.createElement("form");
	var strInputHiddens = "";
	var dis = 0;
	
	form.action = sUrl;
	form.method = "post";
	form.target = "formMagnifier";
	var strName = strHiddenName.split("|:|:");
	var strValue = strHiddenValue.split("|:|:");
	
	for(var i=0; i<strName.length; i++){
		if(strName[i] == "strDisplayNames"){
			dis++;
		}
		strInputHiddens = strInputHiddens + "<input type=\"hidden\" name=\""+ strName[i] +"\" value=\""+ strValue[i] +"\" >";
	}
	form.innerHTML = strInputHiddens;
	
	//alert(form.outerHTML);
	document.appendChild(form);
	
	if(dis < 3){
		window.open("","formMagnifier","toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes", false);
	}
	else {
		window.open("","formMagnifier","toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes", false);
	}
	form.submit();
}
function getOperationUser(officeid,currencyid,status,userid)
{
	var sql = "";
	sql = "select o.id, o.sname, o.sloginno, c.sname as name from ob_user o, client c where o.nclientid = c.id and o.nofficeid = "+officeid+" and o.ncurrencyid = "+currencyid+" and o.nstatus = "+status+" and o.nclientid = (select t.id from client t, ob_user ob where t.id = ob.nclientid and ob.id = "+userid+" ) order by o.id";
	return sql;
	
}
/**
 * 摘要放大镜
 * nOfficeID 办事处ID
 * sCodeOrDesc 摘要代码或描述
 */
function getAbstractSettingSQL(nOfficeID,nClientID,sCodeOrDesc)
{      
	var sql = "select ID AbstractID,sCode AbstractCode,sDesc AbstractDesc from ob_standardabstract ";
    sql += " where nStatusID > 0";
    if (nOfficeID > 0)
	{
		sql += " and nOfficeid = " + nOfficeID;
	}
	if (nClientID > 0)
	{
		sql += " and nClientid = " + nClientID;
	}
	if (sCodeOrDesc != null && sCodeOrDesc.length > 0)
	{
		sql += " and (sCode like '%" + sCodeOrDesc + "%' or sDesc like '%" + sCodeOrDesc + "%')";
	}
	sql += " order by sCode";
	
    return sql;
}
/**
 * 定期(通知)存款单据号(定期支取-开立新存单时调用)
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

function getFixedDepositNoSQL_CREATE(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{
	//定期（通知）支取--业务处理 或 复核时使用
	var sql ="";
	if(lDepositTypeID == 1)
    {
      sql += " select (case when nvl(summamount, -1) = -1 then (a.mBalance - a.mUncheckPaymentAmount) else 0 end) as Balance,a.* from ( ";  
      sql += " select summamount,mBalance,mUncheckPaymentAmount,a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd, 'yyyy-mm-dd') EndDate,to_char(a.dtOpen, 'yyyy-mm-dd') OpenDate, a.mOpenAmount Capital,ma.ID AccountID, AF_mRate  Rate,AF_nDepositTerm Interval,to_char(AF_dtStart, 'yyyy-mm-dd') StartDate ";
      sql += " from sett_SubAccount a,sett_Account ma,(select nsubaccountid, sum(mamount) summamount from ob_financeinstr where ob_financeinstr.nstatus not in (0, 6) group by nsubaccountid) o ";
    }
	  sql += " where 1 = 1 and a.nAccountID = ma.ID and a.id = o.nsubaccountid(+) and a.nStatusID = 1 ";
	if (nTypeID == 21)
	{
		sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
	}
	else if (nTypeID == 22)
	{
		//复核只显示已保存过的。
		sql += " and a.mUncheckPaymentAmount>0 ";
	}
	if (lDepositTypeID == 1)
	{
		//定期存款
		sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) "; 
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
	sql += " ) a order by DepositNo ";
	return sql;			
	
}


/**
 * 定期(通知)存款单据号(定期支取-开立新存单时调用)
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

function getFixedDepositNoSQL_CREATE_OLD(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{
	//定期（通知）支取--业务处理 或 复核时使用
	var sql ="";
	if(lDepositTypeID == 1)
    {
      sql += " select (case when nvl(summamount, -1) = -1 then (a.mBalance - a.mUncheckPaymentAmount) else 0 end) as Balance,a.* from ( ";  
      sql += " select summamount,mBalance,mUncheckPaymentAmount,a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd, 'yyyy-mm-dd') EndDate,to_char(a.dtOpen, 'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,ma.ID AccountID,to_char(AF_mRate, '99.999999') Rate,AF_nDepositTerm Interval,to_char(AF_dtStart, 'yyyy-mm-dd') StartDate ";
      sql += " from sett_SubAccount a,sett_Account ma,(select nsubaccountid, sum(mamount) summamount from ob_financeinstr where ob_financeinstr.nstatus not in (0, 6) group by nsubaccountid) o ";
    }
	  sql += " where 1 = 1 and a.nAccountID = ma.ID and a.id = o.nsubaccountid and a.nStatusID = 1 ";
	if (nTypeID == 21)
	{
		sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
	}
	else if (nTypeID == 22)
	{
		//复核只显示已保存过的。
		sql += " and a.mUncheckPaymentAmount>0 ";
	}
	if (lDepositTypeID == 1)
	{
		//定期存款
		sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) "; 
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
	sql += " ) a order by DepositNo ";

	return sql;			
	
}

/**
 * 定期(通知)存款单据号(定期支取-开立新存单时调用-修改时用)
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

function getFixedDepositNoSQL_CREATE_MODIFY(code,nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{
	//定期（通知）支取--业务处理 或 复核时使用
	var sql ="";
    if(lDepositTypeID == 1)
    {
    sql += " select (case when subaccountid ="+code+" then e when subaccountid <> "+code+" then p end) as Balance,t.* from ( ";
    sql += " select (case when nvl(m, -1) = -1 then e else 0 end) as p,a.* from ( ";  
    sql += " select m,mBalance-mUncheckPaymentAmount e,a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd, 'yyyy-mm-dd') EndDate,to_char(a.dtOpen, 'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,ma.ID AccountID,round(AF_mRate, 6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart, 'yyyy-mm-dd') StartDate ";
    sql += " from sett_SubAccount a,sett_Account ma,(select nsubaccountid, sum(mamount) m from ob_financeinstr where nstatus not in (0, 6) group by nsubaccountid) o ";
  	sql += " where 1 = 1 and a.nAccountID = ma.ID and a.id = o.nsubaccountid(+) and a.nStatusID = 1 ";
	}
	if (nTypeID == 21)
	{
		sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
	}
	else if (nTypeID == 22)
	{
		//复核只显示已保存过的。
		sql += " and a.mUncheckPaymentAmount>0 ";
	}
	if (lDepositTypeID == 1)
	{
		//定期存款
		sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) "; 
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
	sql += " ) a ) t order by DepositNo ";
	
	return sql;			
	
}

/**
 * 定期(通知)存款单据号(修改专用)
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

function getFixedDepositNoSQL_MODIFY(lID,nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{

	var sql ="";
	if(lDepositTypeID == 1)
    {
      sql += "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount-nvl(summamount,0)) Balance,ma.ID AccountID,AF_mRate Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";  
      sql += " from sett_SubAccount a,sett_Account ma,(select nsubaccountid,sum(mamount) summamount from ob_financeinstr where ob_financeinstr.nstatus not in (0,6) and id not in('"+lID+"') group by nsubaccountid) o ";
      sql += " where 1=1 and a.nAccountID=ma.ID and a.id = o.nsubaccountid(+) and a.nStatusID=1 ";
    }
	if (nTypeID == 21)
	{
		sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
	}
	else if (nTypeID == 22)
	{
		//复核只显示已保存过的。
		sql += " and a.mUncheckPaymentAmount>0 ";
	}
	if (lDepositTypeID == 1)
	{
		//定期存款
		sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 2 ) "; 
	}
	else if (lDepositTypeID == 2)
	{
		//通知存款
		sql += " and ma.nAccountTypeID in( select id from sett_accounttype where naccountgroupid = 3 ) "; 			
	}
	else
	{
		sql += " and ma.nAccountTypeID in ( select id from sett_accounttype where naccountgroupid in(2,3) )";
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

/**
 * 委托收款页面账户放大镜 add by xlchang 2010-11-26
 * nOfficeID 办事处ID
 * nCurrencyID 币种ID
 * nClientID 客户ID
 * sAccountNo 账户号
 */
function getConsignReceiveAccountSQL(nOfficeID,nCurrencyID,sAccountNo,equalClientID,unEqualClientID){
	var sql="select a.id sid,a.saccountno sno,a.sname,c.id cid,c.name cname";
	sql += " from sett_account a,client_clientinfo c,sett_accounttype at";
	sql += " where a.nclientid=c.id and a.naccounttypeid=at.id and at.naccountgroupid=1 and a.nstatusid!=4";
	sql += " and a.nofficeid=" + nOfficeID + " and a.ncurrencyid=" + nCurrencyID;	
	if (equalClientID >0) {
		sql += " and a.nclientid=" + equalClientID;
	}
	if (unEqualClientID >0) {
		sql += " and a.nclientid!=" + unEqualClientID;
	}
	if (sAccountNo != null && sAccountNo.length > 0) {
		sql += " and a.saccountno like '%"+sAccountNo+"%'";
	}	
	return sql;
}

/** 
 * 委托收款页面客户放大镜  add by xlchang 2010-11-26
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getConsignReceiveClientSQL(nOfficeID,sClientName,unEqualClientID)
{
	var sql="select c.id,c.code,c.name from client_clientinfo c where c.statusid > 0";
	sql += " and c.officeid = " + nOfficeID;
	if (sClientName != null && sClientName.length > 0) {
		sql += " and (c.code like '%"+sClientName+"%' or c.name like '%"+sClientName+"%')";
	}	
	if (unEqualClientID >0) {
		sql += " and c.id!=" + unEqualClientID;
	}
	return sql;
}


	/**==========业务复核new （没有关联操作权限）====================*/
	function getPayerAccountCheckSQLWithout(sBankAccountCode,lSAID,lClientID,lCurrencyID,lInstructionID)
	{
	
	    var sql = "select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID
				 +" as InstructionID, a.sbankaccountno accountBankNo,b.saccountno displayAccountNo, a.sbankaccountno,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno ,b.ID as nAccountID,b.sname "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedBySA ob,Sett_bankAccountOfFiliale baf"
                 + " where a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 "
				 + " and b.id = baf.NWITHINACCOUNTID(+)"
                 + " and b.nStatusID=1 "
                 + " and b.nCheckStatusID =4 " ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and ( b.saccountno like '%" + sBankAccountCode + "%' or b.sname like '%" + sBankAccountCode + "%' )";
		}
		
		sql = sql+" and ob.sAccountNo = b.saccountno and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID+"and ob.nSAID ="+lSAID;
		sql += " order by saccountno  ";
		return sql;
	}
	
	//关联权限的放大镜查询(帐户对账单查询)
	
	function getAccountSQLByAuthority(sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
			       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode,lUserID,lCurrencyID)
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
		if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != '-1')
		{
			sql += " and b.s_referencecode = '" + dirBankRefcode + "' ";
		}	
		sql +=" and a.s_accountno in(  ";
		sql +=" select e.saccountno ";
		sql +=" from OB_EbankAccountByUserQuery e,bs_bankaccountinfo t ";
		sql +=" where e.saccountno=t.s_accountno ";
		sql +=" and t.n_accountstatus=1 ";
		sql +=" and t.n_rdstatus=1 ";
		sql +=" and t.n_ischeck=1 ";
		sql +=" and e.nuserid="+lUserID;
		sql +=" and t.n_currencytype="+lCurrencyID;
		sql +=" ) ";
		sql += " order by a.s_accountno ";
		return sql;
	}
		
	

