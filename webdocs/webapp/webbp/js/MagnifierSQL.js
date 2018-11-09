/**
 * �������ƣ�MagnifierSQL.js
 * ����˵�������Ŵ���ص����ݿ��ѯ��SQL���
 * �������ߣ�Forest Ming
 * ������ڣ�2003��08��07��
 * ע�⣺ģ��ƥ��ʱ����һ��"%"һ��Ҫ��"%25",���������⡣
 * ���еķŴ�SQL������
 * 1, getSubjectSQL ��Ŀ�Ŵ�

 
 */

//////////ע��//////////
// % ��%25 ���
// + ��%2B ���
 
 
 
//////////����SQL������Ķ�/////////////
/**
*������Ŵ�
*
* 
* 
*/
function getAreaCodeSQL(sAreaCode,sProvince,sCity)
{
	var sql = " select n_id areaCodeId,s_areacode as areaCode,s_areaprovince provinceName,s_areaname cityName,s_areacity countyName,s_arealevel  status from bankareacode where 1=1 ";
	if(sAreaCode !=null && sAreaCode.length > 0 )
	{
		sql += " and  s_areacode like '%25" + sAreaCode +"%25' " ;
	}
	if(sProvince !=null && sProvince.length > 0 )
	{
		sql += " and s_areaprovince ='"+sProvince+"' " ;
		}
  if(sCity !=null && sCity.length > 0 )
  {
  	sql += " and s_areaname = '"+sCity+"' ";
  	
  }
  sql += " order by areaCode "
	return sql;
}
/**
*���зŴ�
*sbankCode
* officeID �û��İ��´�
* flag �ж��Ƿ�Ϊ����
*/
function getBankSQL(sBankCode,officeID,flag)
{
	var sql = " select n_id bankId,s_code as bankCode ,s_name bankName,N_GRADE grade, S_GRADECODE gradecode from BS_BANKSETTING where n_rdstatus=1";
	if ( sBankCode !=null && sBankCode.length > 0 )
	{
		sql += " and ( s_code like '%25" + sBankCode + "%25'" + " or s_name like '%25" + sBankCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code";
	return sql;
}

/**
*���ʷŴ�
*sbankCode
* officeID �û��İ��´�
* 
*/
function getRateSQL(sRateCode)
{
	var sql = " select n_id rateId,s_code as rateCode ,s_name rateName from BS_INTERESTRATESETTING where n_rdstatus=1";
	if ( sRateCode !=null && sRateCode.length > 0 )
	{
		sql += " and ( s_code like '%25" + sRateCode + "%25'" + " or s_name like '%25" + sRateCode + "%25'"+" )";
	}
	
	sql += " order by s_code";
	return sql;
}

/**
*���зŴ�1
*sbankCode
* officeID �û��İ��´�
* flag �ж��Ƿ�Ϊ����
*/
function getBankSQL1(sBankCode,officeID,flag)
{
	var sql = " select n_id bankId,s_code as bankCode ,s_name bankName,N_GRADE grade, S_GRADECODE gradecode,S_REFERENCECODE referencecode from BS_BANKSETTING where n_rdstatus=1";
	if ( sBankCode !=null && sBankCode.length > 0 )
	{
		sql += " and ( s_code like '%25" + sBankCode + "%25'" + " or s_name like '%25" + sBankCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code";
	return sql;
}
/**
*���зŴ�
*����һ���ַ����������е����б�ż��������ƽ���ģ��ƥ��,�������кźͻ�����
*��������ָ���ʱ��ָ�����кźͻ�����
*sbankCode
*/
function getBocBankSQL2(sAreaName,province,city)
{	    
    //alert(sAreaName+","+province+","+city);
	var sql = "select s_branchName,s_bankExchageCode,s_branchCode,s_upBranchName from bs_bocbranchcode where 1=1";
	var flag=false;
 	if (sAreaName != null && sAreaName.length > 0)
	{
	    sql += " and s_branchName like '%25"+sAreaName+"%25'";	
		flag=true;		
	}	
	if(province != null && province.length > 0)
	{
		 sql += " and s_branchName like '%25"+province+"%25'";	
		 flag=true;		
	}
	if(city != null && city.length > 0)
	{
		 sql += " and s_branchName like '%25"+city+"%25'";	
		 flag=true;		
	}
	if(flag==true)
	{
	 	sql += " order by s_branchName";	
	}
	else
	{
	 	sql = "select '����������' s_branchName, '' s_bankExchageCode,'' s_branchCode,'' s_upBranchName from dual";
	}		
	//alert(flag);
  	return sql;
}
/**
*��������Ŵ�
**/
function getBankNetSQL(sBankNetCode,sBankId)
{
 var sql=" select n_id bankNetId,s_code bankNetCode,n_bankid bankId,s_unitecode unitecode,s_swiftcode swiftCode,s_branchname branchName,s_branchareaseg1 branchareaseg1,s_branchareaseg2 branchareaseg2,s_branchareaseg3 branchareaseg3 from BS_BANKNETSTATIONSETTING where 1=1";
 if(sBankNetCode!=null && sBankNetCode.length>0)
  {
   sql += " and ( s_code like '%25" + sBankNetCode + "%25'" + " or s_name like '%25" + sBankNetCode + "%25'"+" )";
  }
 if(sBankId!=null && sBankId.length>0 )
  {
   sql+=" and n_bankid ="+sBankId;
  } 
  sql+=" order by s_code";
  return sql;
}
/**
 * ��Ŀ�Ŵ�
 * sGLSubjectCode ��Ŀ����
 * nIsleaf �Ƿ�ĩ��:0,��1���ǡ�
 */
function getSubjectSQL(sSubjectCode,nIsleaf,officeID,flag)
{      
	var sql = "select n_id SubjectID,s_subjectcode as SubjectCode, s_subjectname as SubjectName ";
    sql += " from BS_SUBJECTSETTING where n_rdstatus=1";
  
	if(nIsleaf > 0)
	{
		if (nIsleaf == 1)
		{
			sql += " and n_isleaf = 1";
		}
		else
		{
			sql += " and n_isleaf = 0";
		}
	}

	if (sSubjectCode != null && sSubjectCode.length > 0)
	{
		sql += " and ( s_subjectcode like '%25" + sSubjectCode + "%25'" + " or s_subjectname like '%25" + sSubjectCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_subjectcode ";
	
    return sql;
}

/**
 * ���ҷŴ�
 * sCountryCode ���Ҵ���
 */
function getCountrySQL(sCountryCode)
{      
	var sql = "select n_id CountryID,s_code as CountryCode, s_name as CountryName ";
    sql += " from BS_COUNTRYSETTING where n_rdstatus=1";
  
	if (sCountryCode != null && sCountryCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sCountryCode + "%25'" + " or s_name like '%25" + sCountryCode + "%25'"+" )";
	}
	sql += " order by s_code ";
	
    return sql;
}

/**
 * �ͻ��Ŵ�
 * sClientCode �ͻ�����
 * officeID �û��İ��´�
 * flag �ж��Ƿ�Ϊ����
 */
function getClientSQL(sClientCode,officeID,flag)
{      
	var sql = "select n_id ClientID,s_code as ClientCode, s_name as ClientName  , S_GRADECODE as upgradecode, N_GRADE as upgrade ";
    sql += " from BS_CLIENTSETTING where n_rdstatus=1";
  
	if (sClientCode != null && sClientCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sClientCode + "%25'" + " or s_name like '%25" + sClientCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code ";
	
    return sql;
}

/**
 * �����Ŵ�
 * sSumGroupCode ��������
 * officeID �û��İ��´�
 * flag �ж��Ƿ�Ϊ�ܲ�
 */
function getSumGroupSQL(sSumGroupCode)
{      
	var sql = "select n_id as sumGroupId,s_code as sumGroupCode, s_name as sumGroupName , n_minamount as minamount,n_maxamount as maxamount ";
    sql += " from BS_SUMGROUP where 1=1";
  
	if (sSumGroupCode != null && sSumGroupCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sSumGroupCode + "%25'" +" )";
	}
	sql += " order by n_id ";
	
    return sql;
}

function getGatherClientSQL(sClientCode,officeID,bankType,transType,flag)
{      
	var sql = "select distinct a.n_id ClientID,a.s_code as ClientCode, a.s_name as ClientName  , a.S_GRADECODE as upgradecode, a.N_GRADE as upgrade ";
    sql += " from BS_CLIENTSETTING a,BS_ACCTGATHERTACTIC b,BS_ACCTGATHERTACTICCONTENT c,bs_bankaccountinfo d where a.n_id=d.n_clientid ";
    sql += " and d.n_id=c.n_subacctid and b.n_id=c.n_tacticid and a.n_rdstatus=1 ";
    sql += " and (b.n_tacticproperty=-1 or n_tacticproperty="+transType+") ";
    if(bankType!=null && bankType.length>0 && bankType!="-1")
    {
    	sql += " and c.s_bankType='"+bankType+"'";
    }
  
		if (sClientCode != null && sClientCode.length > 0)
		{
			sql += " and ( a.s_code like '%25" + sClientCode + "%25'" + " or a.s_name like '%25" + sClientCode + "%25'"+" )";
		}
		if(flag == 1)
		{
			sql += " and a.n_officeid = " + officeID;
		}
		sql += " order by a.s_code ";
	
    return sql;
}
/**
* ��طŴ�
*/
function getMonitorSQL(sMonitorCode,officeID,flag)
{
    var sql = "select n_id MonitorID,s_code MonitorCode, s_name MonitorName ";
    sql += " from BS_MONITORITEMINFO where n_rdstatus=1";
  
	if (sMonitorCode != null && sMonitorCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sMonitorCode + "%25'" + " or s_name like '%25" + sMonitorCode + "%25'"+" )";
	}
	
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	
	sql += " order by s_code ";
	
    return sql;
}
/**
 * ���ַŴ�
 * sCurrencyCode �ͻ�����
 */
function getCurrencySQL(sCurrencyCode)
{      
	var sql = "select n_id CurrencyID,s_code as CurrencyCode, s_name_zh as CurrencyName ";
    sql += " from bs_currencysetting where n_rdstatus=1";
  
	if (sCurrencyCode != null && sCurrencyCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sCurrencyCode + "%25'" + " or s_name_zh like '%25" + sCurrencyCode + "%25'"+" )";
	}
	sql += " order by s_code ";
	
    return sql;
}
/**
 * �˻��Ŵ�
 * sAccountNO �ͻ�����
 * officeID �û��İ��´�
 * flag �ж��Ƿ�Ϊ����
 */
function getAccountSQL1(sAccountNO,isCheck,currencyType,officeID,flag)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName ,b.s_name BankName,c.s_name_zh CurrencyName";
   	    sql += " from BS_BANKACCOUNTINFO a ,bs_banksetting b,bs_currencysetting c where b.n_id=a.n_bankid and a.n_currencytype=c.n_id and a.n_rdstatus=1 and a.n_accountStatus = 1 ";
	
	if (isCheck != null && isCheck >= 0)
	{
		sql += " and a.n_ischeck = " + isCheck;
	}
	if (currencyType != null && currencyType >0)
	{
		sql += " and a.n_currencytype= " + currencyType;
	}
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and a.n_officeid = " + officeID;
	}

	sql += " order by a.s_accountno";
	
    return sql;
}

/**
 * �˻��Ŵ�
 * �����˻�����,����ָ��userID�������˻�
 */
function getAccountSQL4(sAccountNO,isCheck,userID,currencyType,officeID,flag)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName ,b.s_name BankName,c.s_name_zh CurrencyName";
  sql += " from BS_BANKACCOUNTINFO a ,bs_banksetting b,bs_currencysetting c where b.n_id=a.n_bankid and a.n_currencytype=c.n_id and a.n_rdstatus=1 and (a.n_accountStatus = 3 or a.n_accountStatus = 1) ";
	
	if (isCheck != null && isCheck >= 0)
	{
		sql += " and a.n_ischeck = " + isCheck;
	}
	if (currencyType != null && currencyType >0)
	{
		sql += " and a.n_currencytype= " + currencyType;
	}
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and a.n_officeid = " + officeID;
	}
	if(userID!=null && userID!=-1)
	{
		sql += " and exists (select * from bs_bankacctmaintaininfo b ";
		sql += " where b.n_accountid=a.n_id and b.n_operatetype=1 and b.n_operaterid=" + userID + ")";
	}

	sql += " order by a.s_accountno";
	
    return sql;
}

//�˻��Ŵ󾵹����ֶΣ�
//�ͻ�id������id����������id������id������������id����֧����id���Ƿ�ֱ�����ʻ�����һ�������������״̬���ʻ�״̬
//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,Two,Three,isCheck,accountStatus
//officeID �û��İ��´�
//flag �ж��Ƿ�Ϊ����
function getAccountSQL(sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
		       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName,a.n_inputoroutput as Inputoroutput,b.s_name as BankName";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid=b.n_id ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
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
	}	else{
		sql += " and ( a.n_accountstatus=1 or a.n_accountstatus=3 ) ";
	}
	if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != '-1')
	{
		sql += " and b.s_referencecode = '" + dirBankRefcode + "' ";
	}	
	sql += " order by a.s_accountno ";
	
    return sql;
}

//�ϼ��˻��Ŵ󾵹����ֶΣ�
//�ͻ�id������id����������id������id������������id����֧����id���Ƿ�ֱ�����ʻ�����һ�������������״̬���ʻ�״̬
//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,Two,Three,isCheck,accountStatus
//officeID �û��İ��´�
//flag �ж��Ƿ�Ϊ����
function getAccountSQL3(sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
		       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName,a.n_inputoroutput as Inputoroutput,b.s_name as BankName";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid=b.n_id ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
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
	if (dirBankRefcode == null)
	{
		sql += " and b.s_referencecode <> '-1' ";
	}	
	else if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != '-1')
	{
		sql += " and b.s_referencecode = '" + dirBankRefcode + "' ";
	}	
	sql += " order by a.s_accountno ";
	
    return sql;
}

function getGatherAccountSQL(sAccountNO,officeID,clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,
		       accountPropertyTwo,accountPropertyThree,isCheck,accountStatus,dirBankRefcode,transType)
{      
	var sql = "select distinct a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName,a.n_inputoroutput as Inputoroutput,b.s_name as BankName";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b, BS_ACCTGATHERTACTICCONTENT c,BS_ACCTGATHERTACTIC d where a.n_rdstatus=1 and a.n_bankid=b.n_id and a.n_id=c.n_subacctid and d.n_id=c.n_tacticid and (d.n_tacticproperty=-1 or d.n_tacticproperty="+transType+") ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
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

/**
 *  �˻��Ŵ󾵣������˻�������ˣ�ֻ��ʾ���������е��˻�
*/
function getAccountSQLForClose(sAccountNO,officeID,accountStatus)
{      
	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName, ";
			sql += " b.s_name as BankName,b.s_referencecode referenceCode ";
    	sql += " from BS_BANKACCOUNTINFO a,BS_BANKSETTING b ";
    	sql += " where  1=1 and a.n_ischeck=1 and a.n_bankid=b.n_id ";
    	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}    	
  	
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and a.n_accountStatus = " + accountStatus + " ";
	}	else{
		sql += " and ( a.n_accountstatus=0 or a.n_accountstatus=2 ) ";
	}
	
	sql += " order by b.s_referencecode, b.s_name, a.s_accountno ";
	
    return sql;
}


//�˻��Ŵ󾵹����ֶΣ�
//�ͻ���Ŵӡ��ͻ���ŵ�������id����������id������id������������id����֧����id���Ƿ�ֱ�����ʻ�����һ�������������״̬���ʻ�״̬
//clientIdFromCtrl,clientIdToCtrl,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,accountPropertyOne,Two,Three,isCheck,accountStatus
//officeID �û��İ��´�
//flag �ж��Ƿ�Ϊ����
function getAccountSQL2(sAccountNO,
	clientIdFromCtrl,clientIdToCtrl,countryId,bankId,currencyType,ownerType,inputOrOutput,
	isDirectLink,accountPropertyOne,accountPropertyTwo,accountPropertyThree,isCheck,
	accountStatus,officeID,flag)
{   
	   
	var sql = "select a.n_id AccountID,s_accountno as AccountNO, s_accountname as AccountName, b.s_name BankName";
    sql += " from BS_BANKACCOUNTINFO a,BS_Banksetting b where a.n_rdstatus=1 and b.n_id=a.n_bankid ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( s_accountno like '%25" + sAccountNO + "%25'" + " or s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}
	
	if (clientIdFromCtrl != null && clientIdFromCtrl.length > 0 && clientIdFromCtrl != '-1')
	{
		sql += " and n_clientId in ( select n_Id from bs_clientsetting where s_Code >= '" + clientIdFromCtrl+ "') ";
	}
	if (clientIdToCtrl != null && clientIdToCtrl.length > 0 && clientIdToCtrl != '-1')
	{
		sql += " and n_clientId in ( select n_Id from bs_clientsetting where s_Code <= '" + clientIdToCtrl+ "') ";
	}
	if (countryId != null && countryId.length > 0 && countryId != '-1')
	{
		sql += " and n_countryId = " + countryId + " ";
	}
	if (bankId != null && bankId.length > 0 && bankId != '-1')
	{
		sql += " and n_bankId = " + bankId + " ";
	}
	if (currencyType != null && currencyType.length > 0 && currencyType != '-1')
	{
		sql += " and n_currencyType = " + currencyType + " ";
	}
	if (ownerType != null && ownerType.length > 0 && ownerType != '-1' )
	{
		sql += " and n_ownerType = " + ownerType + " ";
	}
	if (inputOrOutput != null && inputOrOutput.length > 0 && inputOrOutput != '-1')
	{
		sql += " and n_inputOrOutput = " + inputOrOutput + " ";
	}
	if (isDirectLink != null && isDirectLink.length > 0 && isDirectLink != '-1')
	{
		sql += " and n_isDirectLink = " + isDirectLink + " ";
	}
	if (accountPropertyOne != null && accountPropertyOne.length > 0 && accountPropertyOne != '-1')
	{
		sql += " and n_accountPropertyOne = " + accountPropertyOne + " ";
	}
	if (accountPropertyTwo != null && accountPropertyTwo.length > 0 && accountPropertyTwo != '-1')
	{
		sql += " and n_accountPropertyTwo = " + accountPropertyTwo + " ";
	}
	if (accountPropertyThree != null && accountPropertyThree.length > 0 && accountPropertyThree != '-1')
	{
		sql += " and n_accountPropertyThree = " + accountPropertyThree + " ";
	}
	if (isCheck != null && isCheck.length > 0 && isCheck != '-1')
	{
		sql += " and n_isCheck = " + isCheck + " ";
	}
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and n_accountStatus = " + accountStatus + " ";
	}
	if(flag == 1)
	{
		sql += " and a.n_officeid = " + officeID;
	}
	sql += " order by s_accountno ";
	
    return sql;
}


/**
 * �ʻ�����һ�Ŵ�
 * sAccountPropertyOneCode �ʻ�����һ����
 */
function getAccountPropertyOneSQL(sAccountPropertyOneCode,officeID,flag)
{      
	var sql = "select n_id AccountPropertyOneID,s_code as AccountPropertyOneCode,s_name as AccountPropertyOneName ";
    sql += "from BS_ACCOUNTPROPERTYONESETTING where n_rdstatus=1";
  
	if (sAccountPropertyOneCode != null && sAccountPropertyOneCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sAccountPropertyOneCode + "%25'" + " or s_name like '%25" + sAccountPropertyOneCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code ";
	
    return sql;
}


/**
 * �ʻ����Զ��Ŵ�
 * sAccountPropertyTwoCode �ʻ����Զ�����
 */
function getAccountPropertyTwoSQL(sAccountPropertyTwoCode,officeID,flag)
{      
	var sql = "select n_id AccountPropertyTwoID,s_code as AccountPropertyTwoCode,s_name as AccountPropertyTwoName ";
    sql += "from BS_ACCOUNTPROPERTYTWOSETTING where n_rdstatus=1";
  
	if (sAccountPropertyTwoCode != null && sAccountPropertyTwoCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sAccountPropertyTwoCode + "%25'" + " or s_name like '%25" + sAccountPropertyTwoCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code ";
	
    return sql;
}


/**
 * �ʻ��������Ŵ�
 * sAccountPropertyThreeCode �ʻ�����������
 */
function getAccountPropertyThreeSQL(sAccountPropertyThreeCode,officeID,flag)
{      
	var sql = "select n_id AccountPropertyThreeID,s_code as AccountPropertyThreeCode,s_name as AccountPropertyThreeName ";
    sql += "from BS_ACCOUNTPROPERTYTHREESETTING where n_rdstatus=1";
  
	if (sAccountPropertyThreeCode != null && sAccountPropertyThreeCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sAccountPropertyThreeCode + "%25'" + " or s_name like '%25" + sAccountPropertyThreeCode + "%25'"+" )";
	}
	if(flag == 1)
	{
		sql += " and n_officeid = " + officeID;
	}
	sql += " order by s_code ";
	
    return sql;
}


/**
 *�˻��Ŵ󾵣������ֹ��˶Ժ͵�������
 *�����ֶΣ���������id�� �Ƿ�ֱ�������״̬���ʻ�״̬
 *		     bankId,isDirectLink,isCheck,accountStatus
 * officeID �û��İ��´�
 * flag �ж��Ƿ�Ϊ����
 */
 function getAccountByReferenceBankcode (sAccountNO,referenceBank,isDirectLink,isCheck,accountStatus,officeID,flag)
{
	var sql = "select a.n_id AccountID ,a.s_accountno as AccountNO,a.s_accountname as AccountName , a.n_bankid as BankID,b.s_name BankName ";
		sql += " from BS_BANKACCOUNTINFO a , BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid = b.n_id ";  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}
	
	if (referenceBank != null && referenceBank.length > 0 && referenceBank != '-1')
	{
		sql += " and b.s_referencecode = \'"+referenceBank+"\' ";
	}
	
	if (isDirectLink != null && isDirectLink.length > 0 && isDirectLink != '-1')
	{
		sql += " and a.n_isDirectLink = " + isDirectLink + " ";
	}
	if (isCheck != null && isCheck.length > 0 && isCheck != '-1')
	{
		sql += " and a.n_isCheck = " + isCheck + " ";
	}
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and a.n_accountStatus = " + accountStatus + " ";
	}
	if(flag == 1)
	{
		sql += " and a.n_officeid = " + officeID;
	}
	sql += " order by a.s_accountno ";
	
    return sql;
}

/**
 * �����˻��Ŵ󾵣���������ֱ������
 *
 */
 function getFilialeAccountSQL (sAccountNO,isCheck,currencyType)
 {
 	var sql = "select a.n_id AccountID,a.s_accountno as AccountNO, a.s_accountname as AccountName ,b.s_name BankName,c.s_name_zh CurrencyName";
  sql += " from BS_BANKACCOUNTINFO a ,bs_banksetting b,bs_currencysetting c where b.n_id=a.n_bankid and a.n_currencytype=c.n_id and a.n_rdstatus=1 and a.n_accountStatus=1";
  sql += " and a.n_upbankacctid !='-1' ";
  
 
	if (isCheck != null && isCheck >= 0)
	{
		sql += " and a.n_ischeck = " + isCheck;
	}
	
	if (currencyType != null && currencyType >0)
	{
		sql += " and a.n_currencytype= " + currencyType;
	}
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}

	sql += " order by a.s_accountno";
	
    return sql;
 }
 
 /**
 * �����˻��Ŵ󾵣���������ֱ������
 *�����ֶ� �������͹�����š� �Ƿ�ֱ�������״̬���ʻ�״̬,�˻�����
 *referenceBankID,isDirectLink,isCheck,accountStatus
 */
			
 function getFilialeAccountSQLByReferenceBankcode(sAccountNO,referenceBankID,isDirectLink,isCheck,accountStatus,inputoroutput)
 {
 	var sql = "select a.n_id AccountID ,a.s_accountno as AccountNO,a.s_accountname as AccountName , a.n_bankid as BankID,b.s_name BankName ";
		sql += " from BS_BANKACCOUNTINFO a , BS_BANKSETTING b where a.n_rdstatus=1 and a.n_bankid = b.n_id ";  	
		sql += " and a.n_upbankacctid !='-1' ";
		
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( a.s_accountno like '%25" + sAccountNO + "%25'" + " or a.s_accountname like '%25" + sAccountNO + "%25'"+" )";
	}
	
	if (referenceBankID != null && referenceBankID.length > 0 && referenceBankID != '-1')
	{
		sql += " and b.s_referencecode = \'"+referenceBankID+"\' ";
	}
	
	if (isDirectLink != null && isDirectLink.length > 0 && isDirectLink != '-1')
	{
		sql += " and a.n_isDirectLink = " + isDirectLink + " ";
	}
	if (isCheck != null && isCheck.length > 0 && isCheck != '-1')
	{
		sql += " and a.n_isCheck = " + isCheck + " ";
	}
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and a.n_accountStatus = " + accountStatus + " ";
	}
	if(inputoroutput!='-1')
	{
		if(inputoroutput=='1')
			sql+= " and a.n_inputoroutput=1 ";
		else if(inputoroutput=='2')	
		  sql+=" and a.n_inputoroutput=2 ";		
		else if(inputoroutput=='3')			  
		  sql+=" and a.n_inputoroutput=3 ";		
		else if(inputoroutput=='12')			  
		  sql+=" and (a.n_inputoroutput=1 or a.n_inputoroutput=2) ";		
		else if(inputoroutput=='13')	
		 sql+=" and (a.n_inputoroutput=1 or a.n_inputoroutput=3) ";
		else if(inputoroutput=='23')	
			sql+=" and (a.n_inputoroutput=2 or a.n_inputoroutput=3) "; 		
	}
	sql += " order by a.s_accountno ";
	
    return sql;
 }
function getAcctGatherTacticSQL (sTacticCode,officeId, flag)
 {
 	var sql = "select t.n_id TacticID,t.s_tacticcode TacticCode,t.s_tacticname TacticName from bs_acctgathertactic t where 1=1 and t.n_rdstatus=1 ";
  
	if (sTacticCode != null && sTacticCode.length > 0)
	{
		sql += " and ( t.s_tacticcode like '%25" + sTacticCode + "%25'" + " or t.s_tacticname like '%25" + sTacticCode + "%25'"+" )";
	}
	
	if(flag == 1)
	{
		sql += " and t.n_officeid = " + officeId;
	}

	sql += " order by t.s_tacticcode";
	
    return sql;
 }
 /**�˻��鼯���ԷŴ�
  * 1.transType Ϊ4,�ϻ�ʱ���鼯����Ϊ��ȫ���������ϻ���
	* 2.transType Ϊ5,�²�ʱ���鼯����Ϊ��ȫ���������²���  
	* 2.transType Ϊ-1,ȫ��ʱ���鼯����Ϊ��ȫ����  
 */
function getAcctGatherTacticSQLByTransType (sTacticCode,lTransType, officeId, flag)
 {
 	var sql = "select t.n_id TacticID,t.s_tacticcode TacticCode,t.s_tacticname TacticName from bs_acctgathertactic t where 1=1 and t.n_rdstatus=1 ";
  
	if (sTacticCode != null && sTacticCode.length > 0)
	{
		sql += " and ( t.s_tacticcode like '%25" + sTacticCode + "%25'" + " or t.s_tacticname like '%25" + sTacticCode + "%25'"+" )";
	}

	if (lTransType != -1)
	{
			sql += " and( t.n_tacticproperty=" + lTransType + " or t.n_tacticproperty= -1 )" ;
	}
	
	if(flag == 1)
	{
		sql += " and t.n_officeid = " + officeId;
	}

	sql += " order by t.s_tacticcode";
	
    return sql;
 }
  
  /**
 * �¼��ͻ��Ŵ�
 * lUpClientID,�ϼ��ͻ�id,sClientCode �ͻ�����
 */
function getSubClientSQL(lUpAccountId, sClientCode,officeId, flag)
{      
	var sql = "select n_id ClientID,s_code as ClientCode, s_name as ClientName  , S_GRADECODE as upgradecode, N_GRADE as upgrade ";
    	sql += " from BS_CLIENTSETTING c,";
    	sql+="(select a.n_clientid n_upclientid,c0.n_grade upgrade,c0.s_gradecode upgradecode from bs_bankaccountinfo a,bs_clientsetting c0 where a.n_id="+lUpAccountId+" and a.n_clientid=c0.n_id) c1 where n_rdstatus=1 ";

  	sql+=" and substr(c.s_gradecode,1,length(c1.upgradecode)) like c1.upgradecode and c.n_grade>=c1.upgrade ";	

	if (sClientCode != null && sClientCode.length > 0)
	{
		sql += " and ( s_code like '%25" + sClientCode + "%25'" + " or s_name like '%25" + sClientCode + "%25'"+" )";
	}
	
	if(flag == 1)
	{
		sql += " and c.n_officeid = " + officeId;
	}

	sql += " order by s_code ";
	
    return sql;
}


 /**
 * �û��Ŵ�
 */

function getUserSQL(sLoginNo,officeId, flag)
{      
	var sql = "select  id UserID, sloginno LoginNo, sname UserName ";
    	sql += " from userinfo ";
  	  sql +=" where 1=1 ";	

	if (sLoginNo != null && sLoginNo.length > 0)
	{
		sql += " and ( sloginno like '%25" + sLoginNo + "%25'" + " or sname like '%25" + sLoginNo + "%25'"+" )";
	}
	
	if(flag == 1)
	{
		sql += " and nofficeid = " + officeId;
	}

	sql += " order by sloginno,sname ";
	
    return sql;
}

 /**
 * �û���Ŵ�
 */
 /*
function getUserGroupSQL(sGroupName,officeId, flag)
{      
	var sql = "select id GroupID, name GroupName ";
    	sql += " from sys_group ";
  	  sql +=" where 1=1 ";	

	if (sGroupName != null && sGroupName.length > 0)
	{
		sql += " and ( name like '%25" + sGroupName + "%25' )";
	}
	
	if(flag == 1)
	{
		sql += " and officeid = " + officeId;
	}

	sql += " order by name ";
	
    return sql;
}
*/

 /**
 * �ڲ����Ŵ󾵣����ڴ����ҽ��ײ�ѯ
 */

function getSubAccountSQL(sSubjectCode,officeId, flag)
{      
	var sql = "select distinct n_subjectid SubjectID, s_subjectcode SubjectCode, s_subjectname SubjectName ";
    	sql += " from bs_agenttransactioninfo ";
  	  sql +=" where 1=1 and n_rdstatus=1 ";	

	if (sSubjectCode != null && sSubjectCode.length > 0)
	{
		sql += " and ( SubjectCode like '%25" + sSubjectCode + "%25' or SubjectName like '%25" + sSubjectCode + "%25' )";
	}
	
	if(flag == 1)
	{
		sql += " and officeid = " + officeId;
	}

	sql += " order by SubjectCode,SubjectName ";
	
    return sql;
}


 /**
 * �ʽ�Ԥ��Ŵ�
 */
function getForecastsAccountSQL(sAccountNO,officeID,clientId,isDirectLink,
                                 isCheck,accountStatus,dirBankRefcode)
{      
	var sql = "select distinct  bgc.n_acctid as accountId,ba.s_accountno as AccountNo,ba.s_accountname as AccountName ";
    	sql += " from bs_acctgathertacticcontent bgc ,bs_bankaccountinfo ba where bgc.n_acctid=ba.n_id and bgc.n_acctid not in (select bgc2.n_subacctid from bs_acctgathertacticcontent bgc2) ";
  	
	if (sAccountNO != null && sAccountNO.length > 0)
	{
		sql += " and ( ba.s_accountno like '%25" + sAccountNO + "%25'" +" )";
	}
	if(officeID != null && officeID != '-1')
	{
		sql += " and ba.n_officeid = " + officeID;
	}
	if (clientId != null && clientId.length > 0 && clientId != '-1')
	{
		sql += " and ba.n_clientId = " + clientId + " ";
	}
	if (isDirectLink != null && isDirectLink.length > 0 && isDirectLink != '-1')
	{
		sql += " and ba.n_isDirectLink = " + isDirectLink + " ";
	}
	if (isCheck != null && isCheck.length > 0 && isCheck != '-1')
	{
		sql += " and ba.n_isCheck = " + isCheck + " ";
	}
	if (accountStatus != null && accountStatus.length > 0 && accountStatus != '-1')
	{
		sql += " and ba.n_accountStatus = " + accountStatus + " ";
	}	
	if (dirBankRefcode != null && dirBankRefcode.length > 0 && dirBankRefcode != '-1')
	{
		sql += " and bgc.s_banktype = '" + dirBankRefcode + "' ";
	}	
	sql += " order by bgc.n_acctid ";
	
    return sql;
}