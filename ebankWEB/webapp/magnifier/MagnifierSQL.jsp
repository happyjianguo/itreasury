<%--
/**
 * �������ƣ�MagnifierSQL.jsp
 * ����˵�������Ŵ���ص����ݿ��ѯ��SQL���
 * �������ߣ�
 * ������ڣ�2003��08��07��
 */
--%> 
<%@ page import=" java.util.*,java.rmi.*,java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.Constant,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*"
%>

<%
try
{
%>
 
<Script language="JavaScript">
//�����ִ��޸�
	/**==========��������˻���ŷŴ�====================*/
	function getBankAccountCodeSQL(sBankAccountCode,lUserID,lClientID,lCurrencyID,lInstructionID)
	{
	    var sql = " select distinct "+ lCurrencyID +" as CurrencyID, "+lInstructionID+" as InstructionID, a.sbankaccountno ,b.saccountno ,a.nAccountID "
                 + " from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba"
                 + " where oba.sAccountNo=b.sAccountNo and a.naccountid(<%=URLEncoder.encode("+")%>)=b.id and b.nAccountTypeID=c.id(<%=URLEncoder.encode("+")%>) and c.nAccountGroupID=<%=SETTConstant.AccountGroupType.CURRENT%>"
                 + " and b.nStatusID=<%=SETTConstant.AccountStatus.NORMAL%>"
                 + " and b.nCheckStatusID =<%=SETTConstant.AccountCheckStatus.CHECK%>" ;
        if (sBankAccountCode!=null && sBankAccountCode!="")
		{
			sql = sql + " and b.saccountno like '<%=URLEncoder.encode("%")%>" + sBankAccountCode + "<%=URLEncoder.encode("%")%>' ";
		}
		sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID+"  and b.ncurrencyid ="+lCurrencyID;
		sql += " order by b.saccountno  ";
		
		return sql;
	}
	
	/**==========�տ�����˻���ŷŴ󾵣���ת��====================*/
	function getPayeeBankNOSQL(lClientID,lCurrencyID,sPayeeBankNo,sPayeeName)
	{
		var sql = " select id, sPayeeName,sPayeeBankName,SPAYEEPROV,SPAYEECITY, spayeeacctno  from OB_PAYEEINFO where sPayeeName is not null "
				+ " and niscpfacct=<%=OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES%>"
				+ " and nclientid ="+lClientID+" and ncurrencyid ="+lCurrencyID;
		if (sPayeeName!=null && sPayeeName!="")
		{
			sql = sql + " and sPayeeName like '<%=URLEncoder.encode("%")%>" + sPayeeName + "<%=URLEncoder.encode("%")%>' ";
		}
		if (sPayeeBankNo!=null && sPayeeBankNo!="")
		{
			sql = sql + " and spayeeacctno like '<%=URLEncoder.encode("%")%>" + sPayeeBankNo +"<%=URLEncoder.encode("%")%>' ";
		}
		sql += " order by spayeeacctno ";
		
		return sql;
	}
	
	/**==========�տ�����˻���ŷŴ󾵣��㣩====================*/
	function getPayeeAccountNOSQL(lClientID,lCurrencyID,sPayeeAccountNo,sPayeeName)
	{
		var sql = " select id, sPayeeName,spayeeacctno,sPayeeBankName,SPAYEEPROV,SPAYEECITY from OB_PAYEEINFO  where sPayeeName is not null "
				+ " and nclientid ="+lClientID+" and ncurrencyid ="+lCurrencyID ;
		if (sPayeeName!=null && sPayeeName!="")
		{
			sql = sql + " and sPayeeName like '<%=URLEncoder.encode("%")%>" + sPayeeName +"<%=URLEncoder.encode("%")%>'";
		}
		if (sPayeeAccountNo!=null && sPayeeAccountNo!="")
		{
			sql = sql + " and spayeeacctno like '<%=URLEncoder.encode("%")%>" + sPayeeAccountNo +"<%=URLEncoder.encode("%")%>'";
		}
		sql += " order by spayeeacctno";
		
		return sql;
	}
	
/**
 * ����(֪ͨ)���ݺ�
 * nOfficeID ���´�ID
 * sClientNo �ͻ����
 * lDepositTypeID �浥���ͣ�1�����ڣ�2��֪ͨ��
 * nAccountID ���ʻ�ID
 * nUserID ��ǰ�û�
 * sDepositNo �浥��
 * nTypeID ����
 * 	1������(֪ͨ)����--����ƥ��ʱʹ��
 * 	21�����ڣ�֪ͨ��֧ȡ--ҵ����ʱʹ��
 * 	22,���ڣ�֪ͨ��֧ȡ--ҵ�񸴺�ʱʹ��
 * 	3����������ת��--ҵ����ʱʹ�ã�����ʾ�ѵ��ڵĴ浥��
 * sSystemDate ��������
 */
function getFixedDepositNoSQL(nOfficeID,nCurrencyID,lDepositTypeID,nAccountID,nUserID,sDepositNo,nTypeID,sSystemDate)
{
	//���ڴ浥
	if (nTypeID == 1)
	{
		//���ڣ�֪ͨ������--����ƥ��ʱʹ��
		var sql = "select -1 SubAccountID,a.sDepositNo DepositNo,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,'' OpenDate,0 Capital,0 Balance,nAccountID AccountID,0 Rate,0 Interval,'' StartDate ";
		sql += " from sett_TransOpenFixedDeposit a ";
		sql += " where a.nStatusID=2 ";
 		
		if (lDepositTypeID == 1)
		{
			//���ڿ���
			sql += " and a.nTransactionTypeID=12"; 
		}
		else if (lDepositTypeID == 2)
		{
			//֪ͨ����
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
			sql += " and a.sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.sDepositNo";
		
		return sql;
	}
	else if (nTypeID == 22 || nTypeID == 21)
	{
		//���ڣ�֪ͨ��֧ȡ--ҵ���� �� ����ʱʹ��
		var sql = "select a.ID SubAccountID,a.af_sDepositNo DepositNo,to_char(a.af_dtEnd,'yyyy-mm-dd') EndDate,to_char(a.dtOpen,'yyyy-mm-dd') OpenDate,a.mOpenAmount Capital,(a.mBalance-a.mUncheckPaymentAmount) Balance,ma.ID AccountID,round(AF_mRate,6) Rate,AF_nDepositTerm Interval,to_char(AF_dtStart,'yyyy-mm-dd') StartDate ";
		sql += " from sett_SubAccount a,sett_Account ma ";
		sql += " where a.nAccountID=ma.ID and a.nStatusID=1 ";
 		
		if (nTypeID == 21)
		{
			sql += " and (a.mBalance - a.mUncheckPaymentAmount) > 0";
		}
		else if (nTypeID == 22)
		{
			//����ֻ��ʾ�ѱ�����ġ�
			//sql += " and a.ID in (select distinct nSubAccountID from SETT_TRANSFIXEDWITHDRAW where nStatusID=2)";
			sql += " and a.mUncheckPaymentAmount>0 ";
		}
		if (lDepositTypeID == 1)
		{
			//���ڴ��
			sql += " and ma.nAccountTypeID=2"; 
		}
		else if (lDepositTypeID == 2)
		{
			//֪ͨ���
			sql += " and ma.nAccountTypeID=3"; 			
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
			sql += " and a.nInputUserID <> " + nUserID; 
		}
		
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
	else if (nTypeID == 3)
	{
		//��������ת��--ҵ����ʱʹ��
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
			sql += " and a.nInputUserID <> " + nUserID; 
		}
		if(sSystemDate != null && sSystemDate != "")
		{
			sql += " and a.af_dtEnd <= to_date('" + sSystemDate +"','yyyy-mm-dd')"; 
		}
		if (sDepositNo != null && sDepositNo != "") 
		{
			sql += " and a.af_sDepositNo like '%25" + sDepositNo + "%25'";
		}
		sql += " order by a.af_sDepositNo ";
		
		return sql;			
	}
}	
/**
 * ��ͬ�Ŵ�
 * nOfficeID ���´�ID
 * nCurrencyID ����ID
 * nTypeIDArray ��ͬ���ͣ����У�ί�У����֣�
 * nStatusIDArray ��ͬ״̬
 * sContractCode ��ͬ���
 */
/*function getContractSQL(nOfficeID,nCurrencyID,sTypeIDArray,sStatusIDArray,sContractCode,nClientID)
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
		sql += " and contract.sContractCode like '" + sContractCode + "%25'";
	}
	if (nClientID > 0)
	{
		sql += " and contract.nborrowclientid = " + nClientID;
	}
	sql += " order by contract.sContractCode";

    return sql;
}*/

/**==========��ͬ�Ŵ�SQL���===========
* lType  ��������  1��ί���⻹��  2������  3���ſ�֪ͨ��
* lLoanType ��������
* lClientID �ͻ���ʶ
* lInputUserID �û���ʶ
*=====================================*/
function getContractSQL(lType,lLoanType,lClientID,lInputUserID)
{
	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName ����������ɺ�ͬID�����ķſ��ŵȵ�
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(<%=URLEncoder.encode("+")%>) ";
		
	if(lType==1)//����ί���⻹�ĺ�ͬ
	{
		strSQL += " and a.nTypeID in (<%=LOANConstant.LoanType.WT%>,<%=LOANConstant.LoanType.WT%>)";//ί���⻹
	}
	else if(lType==2)//�������ڵĺ�ͬ
	{
		strSQL += " and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";//����û�� �����֡� ��������
	}
	else if(lType==3)//���ҷſ�֪ͨ���ĺ�ͬ��û�� �����֡� ��������
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";
	}
	else if(lType==4)//��������ƾ֤�ĺ�ͬ
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID = <%=LOANConstant.LoanType.TX%>";
	}
	else if(lType==5)//���Ҵ����ͬ����״̬�����ͬ
	{
		strSQL += " and a.nStatusID = <%=LOANConstant.ContractStatus.DELAYDEBT%> ";
	}
	//����������Ĳ������ͣ�������lType������
	///////////////////////////////////////
	if(lLoanType > 0)//���Ҹô��������µĺ�ͬ
	{
		strSQL += " and a.nTypeID = " + lLoanType;
	}
	if(lClientID > 0)//���Ҹÿͻ��µĺ�ͬ
	{
		strSQL += " and a.nBorrowClientID = " + lClientID;
	}
	if(lInputUserID > 0)//���¼����Ϊ���û��ĺ�ͬ
	{
		strSQL += " and a.nInputUserID = " + lInputUserID;
	}
		strSQL += " order by a.sContractCode ";//����ͬ�������
	//System.out.println("sql==="+strSQL);
	return strSQL;
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
	if (sStatusIDArray != null && (sStatusIDArray == '-100' || sStatusIDArray == '-200'))
	{//����/ί���ջأ������ʻ�ȡֵ
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,sa.ID SubAccountID ";
	    sql += " from LOAN_PAYFORM a,loan_contractform b,Client c,sett_SubAccount sa ";
		sql += " where a.nContractID = b.id and b.nBorrowClientID=c.ID and a.ID=sa.al_nLoanNoteID ";
		
		if (sStatusIDArray == '-100')
		{
			//�ջء���ҵ����
			sql += " and sa.nstatusid=1 and (sa.mbalance-sa.mUncheckPaymentAmount) > 0 ";		
		}
		else
		{
			//�ջء���ҵ�񸴺�
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
			sql += " and a.scode like '" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	else
	{//����/ί�з��ţ����Ŵ�ȡֵ
		sql = "select a.id PayFormID,a.scode PayFormCode,b.ID ContractID,to_char(a.dtOutDate,'yyyy-mm-dd') PayDate,to_char(a.dtOutDate,'yyyy-mm-dd') InterestStartDate,to_char(a.dtStart,'yyyy-mm-dd') StartDate,to_char(a.dtEnd,'yyyy-mm-dd') EndDate,-1 SubAccountID ";
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
			sql += " and a.scode like '" + sPayFormNo + "%25'";
		}
		sql += " order by a.scode";
	}
	
    return sql;
}

//adding
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
 * �ⲿ�˻��Ŵ�
 * nOfficeID ���´�ID
 * sExtAcctNo �ⲿ�˻����
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

/**===========�˻��Ŵ�===============
* lType  ��������
* lOfficeID ���´���ʶ
* lCurrencyID ���ұ�ʶ
* lClientID �ͻ���ʶ
* lAccountType �˻�����
*=====================================*/
function getAccountSQL(lOfficeID,lCurrencyID,lClientID,lType,lAccountGroupType,sAccountNo)
{
	var strSQL ="";
	strSQL = " select a.SACCOUNTNO,c.sName,a.id from SETT_Account a,sett_accounttype b,client c ";
	strSQL += " where a.nClientID=c.ID and a.naccounttypeid = b.id(<%=URLEncoder.encode("+")%>) ";
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
		strSQL += " and a.nClientID = " + lClientID;
	}
	//�ſ�֪ͨ������ʹ�õĻ����˻�ֻ��������(1)�����(3)�Լ����ֶ���(8)״̬�������Ѹ������
	if(lType == 100)
	{
	        strSQL += " and a.NSTATUSID in(<%=SETTConstant.AccountStatus.NORMAL%>,<%=SETTConstant.AccountStatus.FREEZE%>,<%=SETTConstant.AccountStatus.SEALUP%>,<%=SETTConstant.AccountStatus.PARTFREEZE%>) ";
			strSQL += " and ( b.naccountGroupid = <%=SETTConstant.AccountGroupType.CURRENT%> or b.naccountGroupid = <%=SETTConstant.AccountGroupType.OTHERDEPOSIT%> )";
	}
	if(sAccountNo != null && sAccountNo.length > 0)
	{
	        strSQL += " and a.sAccountNo like '%25" + sAccountNo + "%25'";
	}
	if(lAccountGroupType > 0)
	{
	        strSQL += " and b.naccountGroupid = "+ lAccountGroupType;
	}
	strSQL += " order by a.sAccountNo ";
	return strSQL;
}
</Script>
<Script language="JavaScript">
function getClientTypeSQL()
{
	var strSQL = "select sCode,sName,nInputUserID,dtInput,id ";
	strSQL += " from LOAN_ClientType where 1=1 and nStatusID= "+<%=Constant.RecordStatus.VALID%>;
	strSQL += " order by sCode ";
	return strSQL;
}
	
</SCRIPT> 

<%
}
catch (Exception ex)
{
	out.println(ex.toString());
}
%>
