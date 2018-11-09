package com.iss.itreasury.loan.query.dao;

import java.io.UnsupportedEncodingException;

import com.iss.itreasury.loan.query.dataentity.QueryDiscountInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

public class QueryDiscountDao {
	
	/**
     * ���ֺ�ͬ��ѯ
     * @param QueryDiscountInfo discountInfo
     * @return
     * @throws Exception
     */
	public String queryDiscountContractInfo(QueryDiscountInfo discountInfo) throws Exception {
		
		String[] sql = null;
		String getQuerySql = "";
		if(discountInfo.getQueryPurpose()==QuestContractInfo.TX){
			
			sql = getContractSQL_wlx(discountInfo);	//����SQL
		}else if(discountInfo.getQueryPurpose()==QuestContractInfo.RZZL){
			
			//sql = getRZZLContractSQL(discountInfo);		//��������SQL
		}else if(discountInfo.getQueryPurpose()==QuestContractInfo.DB){
			
			//sql = getDBContractSQL(discountInfo);			//����SQL
		}else{
			
			//sql = getContractSQL(discountInfo);			//�����ͬ��ѯ
		}
		
		getQuerySql = "select " + sql[0] +" from " +sql[1] +" where " + sql[2];
		
		return getQuerySql;	
			
	}
	public String[] getContractSQL_wlx(QueryDiscountInfo qInfo){
		
		String[] sql = new String[3];
		StringBuffer buf = new StringBuffer();

		//select
		//�޸�by kenny(2007-03-06)���޸Ĵ����ͬ��ѯʱ�Զ��Ͽ����ݿ����ӵ�����
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		// modified by fxzhang 2012-6-6 ���ڳ�ʼ�����ݣ�loan_DiscountFormBill��û����ؼ�¼����ֱ��ȡ��ͬ�е�
		//sql[0] +=" distinct w.NACCEPTPOTYPEID as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"
		sql[0] += " distinct nvl(w.NACCEPTPOTYPEID, (case when(c.nbankacceptpo > 0) then 1 else  2 end)) as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"			
				+ " c1.Name as borrowClientName,c2.Name as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,c.mPurchaserAmount as discountPurchaserInterest,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,c.DTINPUTDATE as InputDate,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest1,"
				+ " d.sApplyCode as applyCode,lp.balance,c3.Name as discountClientName"
				+ ", a.overdueAmount as overdueAmount"
				+ ", b.punishInterest as punishInterest";
		//from
		//sql[1] = "loan_DiscountCredence w,Loan_DiscountContractBill m,loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		sql[1] = "(select distinct nLoanID,NACCEPTPOTYPEID from loan_DiscountFormBill) w ,loan_contractForm c,client_clientinfo c1,client_clientinfo c2,client_clientinfo c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		//�����˻�����ȡ�õ�ǰ���������֣�
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";
		//��־ǿ�޸�(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //��һ��Ϣ��90��������
		sql[1] += " GROUP BY a.id) b";
		//where
		//��־ǿ�޸�(2004-03-09)
		sql[2]  = " c.nLoanId = w.nLoanID(+)";
		//sql[2] += " and m.nAcceptpotypeID =w.ndrafttypeid(+)";
		//sql[2] += " and w.ndrafttypeid>0";
		sql[2] += " and nvl(w.NACCEPTPOTYPEID,9999) > 0 ";
		//sql[2] += " and m.ncontractid=w.ncontractid(+) ";
		if(qInfo.getQueryPurpose() == QuestContractInfo.TX){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if(qInfo.getMinRate()>0){
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0){
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			/*//���ֿͻ�����
			if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
			{
				buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
			}*/
			//���ֻ�Ʊ����
			if(qInfo.getTsDiscountTypeID()>0){		
				buf.append(" and w.NACCEPTPOTYPEID="+qInfo.getTsDiscountTypeID() );
			}
		}else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
		}else{
			
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}
		sql[2] += " and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id";
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+)";
		//���Ϻ��������������� modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose()== QuestContractInfo.DB){
			
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}else{
			
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}
		//��ͬ��ſ�ʼ
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//��ͬ��Ž���
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++){
			//ȥ�����ύ
			if(contractStatusVal[i]==LOANConstant.ContractStatus.SUBMIT){
				continue;
			}
			//ȥ������
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.DELAYDEBT){
  				continue;
  			}
  			//ȥ������
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.BADDEBT){
  				continue;
  			}
  			//ȥ���Ѿܾ�
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.REFUSE){
  				continue;
  			}
  		    //ȥ��������
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.EXTEND){
  				continue;
  			}
  			//ȥ����չ��
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.OVERDUE){
  				continue;
  			}
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ",";
		}
		if(qInfo.getStatusIDs()== null ||qInfo.getStatusIDs().equals("") || qInfo.getStatusIDs().trim().equals("-1")){
			
		    buf.append(" and c.nStatusID in (" + strStatus + ")");
		}else{
			
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
		}
		/*if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}*/
		//minzhao20090505�޸Ľ���ͬ״̬����Ϊ��״̬��ѯ
		//��Ʊ��
		if (qInfo.getBillDrawer()!=null && qInfo.getBillDrawer().length()>0 ){
			
			String test = qInfo.getBillDrawer();
			try {
				test = new String(test.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			buf.append(" and c.DISCOUNTCLIENTNAME like '%" +test+"%'");
		}
		//�������뵥λ��ʼ
		if (qInfo.getMinborrowClientID() > 0  ){
			buf.append(" and c.nborrowclientid >= "+qInfo.getMinborrowClientID());
		}
		//�������뵥λ����
		if (qInfo.getMaxborrowClientID() > 0  ){
			buf.append(" and c.nborrowclientid <= "+qInfo.getMaxborrowClientID());
		}
		//(����)���������
		if (qInfo.getMaxLoanAmount() > 0){
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));
		}
		//(����)�����ʼ
		if (qInfo.getMinLoanAmount() > 0){
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));
		}
		//����ʵ��������
		if (qInfo.getMaxCheckAmount() >0){
			buf.append(" and c.mCheckAmount<=" + DataFormat.formatAmount(qInfo.getMaxCheckAmount()));
		}
		//����ʵ����ʼ
		if (qInfo.getMinCheckAmount() > 0){
			buf.append(" and c.mCheckAmount>=" + DataFormat.formatAmount(qInfo.getMinCheckAmount()));
		}
		//�������ڿ�ʼ
		if (qInfo.getMaxStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");
		}
		//�������ڽ���
		if (qInfo.getMinStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");
		}
		//����������ڽ���
		if (qInfo.getMaxEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");
		}
		//����������ڿ�ʼ
		if (qInfo.getMinEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		}
		//�������ڽ���
		if (qInfo.getMaxDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//�������ڿ�ʼ
		if (qInfo.getMinDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//¼�����ڽ���
		if (qInfo.getMaxDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//¼�����ڿ�ʼ
		if (qInfo.getMinDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//�򷽸�Ϣ����
		if (qInfo.getMaxPayerRate() > 0){
			buf.append(" and c.mPurchaserAmount<=" + DataFormat.formatAmount(qInfo.getMaxPayerRate()));
		}
		//�򷽸�Ϣ��ʼ
		if (qInfo.getMinPayerRate() > 0){
			buf.append(" and c.mPurchaserAmount>=" + DataFormat.formatAmount(qInfo.getMinPayerRate()));
		}
		//������
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//���´�
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //����
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
		sql[2] = sql[2] + buf.toString();

		return sql;
	}
	/**
	 * ����Ʊ����ϸ���ѯ ����Ʊ�ݼ�Ϣ��ϸ���ѯ
	 * @param lContractID
	 */
	public String queryDiscountBillByContractID(long lContractID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountContractBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nContractID = "+lContractID);
		
		return sql.toString();
	}
	/**
	 * ����Ʊ�ݼ�Ϣ��ϸ���ѯ
	 * @param lCredenceID
	 */
	public String queryDiscountBillByCredenceID(long lCredenceID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountContractBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nDiscountCredenceID = "+lCredenceID);
		
		return sql.toString();
	}
	/**
	 * ����Ʊ����ϸ���ѯ ����Ʊ�ݼ�Ϣ��ϸ���ѯ
	 * @param lDiscountID
	 */
	public String queryDiscountBillByDiscountID(long lDiscountID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Loan_DiscountFormBill ");
		sql.append(" where nStatusID = "+Constant.RecordStatus.VALID+" and nLoanID = "+lDiscountID);
		
		return sql.toString();
	}
	/**
	 * ����ƾ֤��Ϣ��ѯ
	 * @param QueryDiscountInfo discountVoucherInfo
	 */
	public String queryDiscountVoucherInfo(QueryDiscountInfo discountVoucherInfo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select l.ID,l.sCode as code,c.sContractCode as DiscountContractCode,l.sApplyClient as applyClientName,l.mAmount as BillAmount, ");
		sql.append(" 	c.mDiscountRate as DiscountRate,l.mInterest as BillInterest,l.PURCHASERINTEREST as PurchaserInterest, ");
		sql.append(" 	l.dtInputDate as InputDate,c.dtDiscountDate as DiscountDate,l.nStatusID as statusID,u.sName as InputUserName ");
		sql.append("  from Loan_DiscountCredence l,loan_contractForm c,userInfo u ");
		sql.append(" where c.ID(+) = l.nContractID and u.id(+) = l.nInputUserId and c.ntypeid != 6 ");
		sql.append(" and l.nofficeid = "+discountVoucherInfo.getOfficeID()+" and l.ncurrencyid = "+discountVoucherInfo.getCurrencyID());
		
		//��ѯƾ֤ʱ�ĺ�ͬID
		if (discountVoucherInfo.getContractID() > 0  ){
			sql.append(" and l.NCONTRACTID = "+discountVoucherInfo.getContractID());
		}
		//��ͬ��ſ�ʼ
		if (discountVoucherInfo.getMinContractCode() != null && discountVoucherInfo.getMinContractCode().length() > 0){
			sql.append(" and c.sContractCode >= '"+discountVoucherInfo.getMinContractCode()+"'");
		}
		//��ͬ��Ž���
		if (discountVoucherInfo.getMaxContractCode() != null && discountVoucherInfo.getMaxContractCode().length() > 0){
			sql.append(" and c.sContractCode <= '"+discountVoucherInfo.getMaxContractCode()+"'");
		}
		//�������뵥λ��ʼ
		if (discountVoucherInfo.getMinborrowClientID() > 0  ){
			sql.append(" and c.nborrowclientid >= "+discountVoucherInfo.getMinborrowClientID());
		}
		//�������뵥λ����
		if (discountVoucherInfo.getMaxborrowClientID() > 0  ){
			sql.append(" and c.nborrowclientid <= "+discountVoucherInfo.getMaxborrowClientID());
		}
		//�������ڿ�ʼ
		if (discountVoucherInfo.getMinDiscountDate() != null){
			sql.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMinDiscountDate())+"','yyyy-mm-dd') ");
		}
		//�������ڽ���
		if (discountVoucherInfo.getMaxDiscountDate() != null){
			sql.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMaxDiscountDate())+"','yyyy-mm-dd') ");
		}
		//¼�����ڿ�ʼ
		if (discountVoucherInfo.getMinDisccountInputDate() != null){
			sql.append(" and TRUNC(l.DTINPUTDATE) >= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMinDisccountInputDate())+"','yyyy-mm-dd') ");
		}
		//¼�����ڽ���
		if (discountVoucherInfo.getMaxDisccountInputDate() != null){
			sql.append(" and TRUNC(l.DTINPUTDATE) <= To_Date('"+DataFormat.getDateString(discountVoucherInfo.getMaxDisccountInputDate())+"','yyyy-mm-dd') ");
		}
		
		long credenceStatusVal[] = LOANConstant.DiscountCredenceStatus.getAllCode(discountVoucherInfo.getOfficeID(),discountVoucherInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < credenceStatusVal.length; i++){	
			if(credenceStatusVal[i]==LOANConstant.DiscountCredenceStatus.SUBMIT){
				continue;
			}
			strStatus += credenceStatusVal[i];
			if((credenceStatusVal.length - i) > 1){
				strStatus += ", ";
			}	
		}
		if(discountVoucherInfo.getCredenceStatusIDs()==null || discountVoucherInfo.getCredenceStatusIDs().equals("") || discountVoucherInfo.getCredenceStatusIDs().equals("-1")){
			
			sql.append(" and l.nStatusID in ("+strStatus+")");
		
		}else{
			
			sql.append(" and l.nStatusID in ("+discountVoucherInfo.getCredenceStatusIDs()+")");
		}
		
		return sql.toString();
	}

}
