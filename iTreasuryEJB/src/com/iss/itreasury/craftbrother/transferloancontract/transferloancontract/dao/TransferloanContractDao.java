package com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dao;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.ContractDetailDao;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoanapplyformInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.contract.dataentity.AssureInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.contract.dataentity.YTFormatInfo;
import com.iss.itreasury.loan.contract.dataentity.YTInfo;
import com.iss.itreasury.loan.contractcontent.dao.ContractContentDao;
import com.iss.itreasury.loan.contractcontent.dataentity.ContractContentInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transferloancontract.dao.TransferLoanAmountDetailDao;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferloanContractDao extends ITreasuryDAO 
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	Log4j  log4j=new Log4j(Constant.ModuleType.CRAFTBROTHER);
	
	public TransferloanContractDao()
	{
		super("CRA_LOANAPPLYFORM");
	}
	
	
	public TransferloanContractDao(Connection  conn)
	{
		super("CRA_LOANAPPLYFORM",null, conn);
	}
	
	//��ҳ��ѯ
	public PageLoader queryTransferApplyInfo(TransferApplyInfo transferApplyInfo) throws Exception
	{
		getTransferApplySQL(transferApplyInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.LISTALL,
			"com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +" "+m_sbOrderBy.toString());
		return pageLoader;
	}
	
	/**
	 * ������ѯSQL  
	 * @param info
	 *
	 */
	public void getTransferApplySQL(TransferApplyInfo transferApplyInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append("c.sname inputUserName, A.id,b.counterpartcode,a.counterpartname,A.sapplycode ,A.transtypeid,A.counterpartid,A.sapplyamount," +
					"A.zstartdate  ,A.zenddate ,A.inputuserid,A.inputdate,A.statusid \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" cra_transferapplyform A ,cra_counterpart b  ,userinfo c\n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and A.officeid= "+transferApplyInfo.getOfficeId()+" \n");
			m_sbWhere.append(" and A.currencyid= "+transferApplyInfo.getCurrencyId()+" \n");
			m_sbWhere.append(" and A.statusid<> "+CRAconstant.TransactionStatus.DELETED+" \n");
			m_sbWhere.append(" and a.counterpartid=b.id");
			m_sbWhere.append(" and a.inputuserid=c.id");
			m_sbWhere.append(" and a.id not in (");
			m_sbWhere.append(" select distinct SAPPLYFORMID  from CRA_LOANAPPLYFORM a where a.statusid<>"+CRAconstant.TransactionType.counterpartBank.INVALID+" )  \n");
			String strTemp = "";
			//ҵ������
			strTemp = String.valueOf(transferApplyInfo.getTransTypeId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.transtypeid = '" + strTemp + "'");
			}
			//��ͬ���
			strTemp = String.valueOf(transferApplyInfo.getContractIdFrom());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.id >= '"+strTemp+"'");
			}
			strTemp = String.valueOf(transferApplyInfo.getContractIdTo());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.id <= '"+strTemp+"'");
			}
			//���׶��ֱ��
			strTemp = String.valueOf(transferApplyInfo.getCounterPartId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.counterpartid = '" + strTemp + "'");
			}
			//���
			double dbTemp = 0.0;
			dbTemp = transferApplyInfo.getApplyAmountFrom();
			if (dbTemp > 0.0)
			{	
				m_sbWhere.append(" and A.sapplyamount >= " + dbTemp);
			}
			dbTemp = transferApplyInfo.getApplyAmountTo();
			if (dbTemp > 0.0)
			{	
				m_sbWhere.append(" and A.sapplyamount <= " + dbTemp );
			}
			//¼������	
			strTemp = transferApplyInfo.getInputDateFrom();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate >= to_date('"+strTemp+"','yyyy-mm-dd')");
			}
			strTemp = transferApplyInfo.getInputDateTo();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate <= to_date('"+strTemp+"','yyyy-mm-dd')+1");
			}
			strTemp = transferApplyInfo.getSapplycodestart();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.sapplycode >= '" + transferApplyInfo.getSapplycodestart()+"'");
			}
			strTemp = transferApplyInfo.getSapplycodeend();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.sapplycode <= '" + transferApplyInfo.getSapplycodeend()+"'");
			}
			strTemp = String.valueOf(transferApplyInfo.getStatusId());
			if (strTemp!=null && !strTemp.equals("-1"))
			{	
				m_sbWhere.append(" and A.statusid = " + transferApplyInfo.getStatusId());
			}
			strTemp = String.valueOf(transferApplyInfo.getTransTypeId());
			if (strTemp!=null && !strTemp.equals("-1"))
			{	
				m_sbWhere.append(" and A.transtypeid = " + transferApplyInfo.getTransTypeId());
			}
			strTemp = String.valueOf(transferApplyInfo.getInputUserId());
			if (strTemp!=null && !strTemp.equals("-1"))
			{	
				m_sbWhere.append(" and A.inputuserid = " + transferApplyInfo.getInputUserId());
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by A.inputdate desc");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	public Collection findContractForAttornment(LoanapplyformInfo qInfo)
	throws Exception {
			Vector v = new Vector();
			
			int lIndex = 1;
			
			String strSQL = "";
			String strSQL_con = "";
			String strSQL_pre = "";
			String strSQL_order = "";
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ContractDetailDao detaildao=new ContractDetailDao();
			try {
				con = Database.getConnection();
			
				// ��ѯ����
				strSQL_con = " WHERE 1=1 ";
				strSQL_con += " AND a.nStatusID >="
						+ LOANConstant.ContractStatus.ACTIVE;
				strSQL_con += " and a.nStatusID <="
						+ LOANConstant.ContractStatus.EXTEND;
				strSQL_con += " AND a.nTypeID =" + LOANConstant.LoanType.ZY;
				strSQL_con += " AND a.mExamineAmount <> 0";
			
				// lContractIDFrom ��ͬ�����
				if (qInfo.getQueryloancontractcodestart()!="") {
					strSQL_con += " AND a.scontractcode >= '" + qInfo.getQueryloancontractcodestart()+"'";
				}
			
				// lContractIDTo ��ͬ���ֹ
				if (qInfo.getQueryloancontractcodeend() != "") {
					strSQL_con += " AND a.scontractcode <= '" + qInfo.getQueryloancontractcodeend()+"'";
				}
			
				// lClientID��λID
				if (qInfo.getClientid() > 0) {
					strSQL_con += " AND a.nBorrowClientID = " + qInfo.getClientid();
				}
			
				// dAmountFrom�����
				if (qInfo.getQuerycontractamountstart() > 0) {
					strSQL_con += " AND b.mamount >= "
							+ qInfo.getQuerycontractamountstart();
				}
			
				// dAmountTo���ֹ
				if (qInfo.getQuerycontractamountend() > 0) {
					strSQL_con += " AND b.mamount <= " + qInfo.getQuerycontractamountend();
				}
			
				// lStatusID ����ͬ״̬
				if (qInfo.getQueryloancontractstatus() > 0) {
					strSQL_con += " AND a.nStatusID = " + qInfo.getQueryloancontractstatus();
				}
			
				// ��������
				if (qInfo.getQueryloancontracttype() > 0) {
					strSQL_con += " and a.nsubtypeid = " + qInfo.getQueryloancontracttype();
				}
				//���´�
				if (qInfo.getOfficeid() > 0) {
					strSQL_con += " and  a.nofficeid = " + qInfo.getOfficeid();
				}
				//����
				if (qInfo.getCurrencyid() > 0) {
					strSQL_con += " and a.ncurrencyid = " + qInfo.getCurrencyid();
				}
				if(qInfo.getLoancontractpayformid()>0)
				{
					strSQL_con += " and b.id = " + qInfo.getLoancontractpayformid();
				}
				strSQL_order += " ORDER BY a.sContractCode, b.scode asc";
			
				
			
				// got the sql sentence
				strSQL_pre = " SELECT all_record.*,ROWNUM num FROM";
				strSQL_pre += " ( SELECT a.*,";
				strSQL_pre += " c.sName,";
				strSQL_pre += " u.sName as InputUserName, ";
				strSQL_pre += " b.id payid, b.scode paycode, b.mamount payamount, b.dtstart startdate, b.dtend enddate ";
				strSQL_pre += " FROM loan_ContractForm a, loan_payform b, ";
				strSQL_pre += " client c,userinfo u";
			
				strSQL_con += " and a.id = b.ncontractid";
				strSQL_con += " AND a.nInputUserID=u.ID(+)";
				strSQL_con += " AND a.nBorrowClientID=c.ID(+)";
				strSQL_con += strSQL_order;
				strSQL_con += ")all_record ";
			
				strSQL = strSQL_pre + strSQL_con;
				log4j.info(strSQL);
				ps = con.prepareStatement(strSQL);
			
				rs = ps.executeQuery();
			
				while (rs != null && rs.next()) {
					ContractInfo info = new ContractInfo();
					info.setContractID(rs.getLong("ID")); // ��ͬ��ID
					info.setLoanID(rs.getLong("nLoanID")); // ����ID
					info.setLoanTypeID(rs.getLong("nTypeID"));
					info.setApplyCode(rs.getString("sApplyCode")); // ��������
					info.setContractCode(rs.getString("sContractCode")); // ��ͬ���
					info.setBorrowClientName(rs.getString("sName")); // ��λ
					info.setLoanAmount(rs.getDouble("mLoanAmount")); // �����
					
					//info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��׼���
					info.setExamineAmount(rs.getDouble("payamount")); // ��׼���
					
					info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
					info.setInputUserName(rs.getString("InputUserName")); // ¼����
					info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
					RateInfo rInfo = new RateInfo();
					rInfo = getLatelyRate(-1, info.getContractID(), null);
					info.setInterestRate(rInfo.getLateRate()); // ִ������
					
					//info.setLoanStart(rs.getTimestamp("dtStartDate")); // ������ʼ����
					//info.setLoanEnd(rs.getTimestamp("dtEndDate")); // ���������
					info.setLoanStart(rs.getTimestamp("startdate")); // ������ʼ����
					info.setLoanEnd(rs.getTimestamp("enddate")); // ���������
					
					info.setIntervalNum(rs.getLong("nIntervalNum")); // ����
					info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼������
					info.setStatusID(rs.getLong("nStatusID")); // ��ͬ״̬
					info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID()));
					
					ContractAmountInfo aInfo = new ContractAmountInfo();
					long payID = rs.getLong("payid");
					//aInfo = getLateAmount(info.getContractID());
					
					//��ǰ��ͬ���
					aInfo = getGuoDianLateAmount(info.getContractID(), payID);
					info.setBalance(aInfo.getBalanceAmount());  //���˻����
					
					//��ͬ�ſ�Ѿ�ת���ܽ��
					//�Ŵ��ʲ�ת�ã���ת�ý��ȡ���㽻���տ���ϸ���
					info.setLastAttornmentAmount(detaildao.searchSellAmount(payID));
					//info.setLastAttornmentAmount(rs.getDouble("lastAttornmentAmount"));
					
					//��ͬ�����ȥ��ǰ�������������
					//info.setBalanceForAttornment(aInfo.getBalanceAmount() - aInfo.getAheadAmount());
					//ֱ�Ӵ����˻�����˻����,�����������ڽ��㻹���Ľ��,���ټ�ȥ�������
					info.setBalanceForAttornment(aInfo.getBalanceAmount());
					
					info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount")); // ծȨ���
					
					info.setPayID(payID);
					info.setPayCode(rs.getString("paycode"));
					if(info.getBalanceForAttornment()!=0)
					{
						v.addElement(info);
					}
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			}
			
			catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (Exception e) {
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return (v.size() > 0 ? v : null);

}
	
	/**
	 * �õ�ִ�����ʣ�����lLoanID��lContractID�ش���һ����������Ļ�������Ϊ-1�� Create Date: 2003-10-15
	 * 
	 * @param lLoanID
	 *            ����ID
	 * @param lContractID
	 *            ��ͬID
	 * @param tsDate
	 *            ʱ�䣬�紫��ΪNULLֵ��մ���Ĭ��Ϊ��ǰ���ڡ�
	 * @return double ִ������
	 * @exception Exception
	 */
	public RateInfo getLatelyRate(long lLoanID, long lContractID,
			Timestamp tsDate) throws Exception {
		RateInfo info = new RateInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsHaveLate = false;
		long lInterestTypeID = -1;
		long lLoanTypeID = -1;
		String strRate = "";

		try {
			con = Database.getConnection();

			if (tsDate == null || tsDate.equals("")) {
				tsDate = DataFormat.getDateTime(con);
			}

			// ȡ����������
			if (lContractID > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" SELECT b.nTypeID,b.nInterestTypeID,b.nLiborRateID,nvl(b.mInterestRate,0) mInterestRate,b.nBankInterestID, ");
				sbSQL
						.append(" nvl(b.mAdjustRate,0) mAdjustRate,nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,nvl(c.mRate,0) mRate,d.LiborName,d.IntervalNum ");
				sbSQL
						.append(" FROM loan_contractForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				// log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next()) {
					// ��������
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// ��������
					lLoanTypeID = rs.getLong("nTypeID");
					// δ�����Ļ�׼����
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT || lLoanTypeID == LOANConstant.LoanType.RZZL) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// δ����������ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// δ����������
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor����ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor��������
					info.setLiborName(rs.getString("LiborName"));
					// Libor��������
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			} else if (lLoanID > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" SELECT b.nInterestTypeID, b.nTypeID,b.mInterestRate,b.nBankInterestID,b.mAdjustRate,b.mStaidAdjustRate,b.nLiborRateID,c.mRate,d.LiborName,d.IntervalNum ");
				sbSQL
						.append(" FROM loan_loanForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				// log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanID);
				rs = ps.executeQuery();

				if (rs.next()) {
					// ��������
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// ��������
					lLoanTypeID = rs.getLong("nTypeID");
					// δ�����Ļ�׼����
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT || lLoanTypeID ==LOANConstant.LoanType.RZZL ) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// δ����������ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// δ����������
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor����ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor��������
					info.setLiborName(rs.getString("LiborName"));
					// Libor��������
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			} else {
				return null;
			}

			if (lInterestTypeID == LOANConstant.InterestRateType.BANK) {
				// ͨ����ͬIDȡ����
				if (lContractID > 0) {
					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT a.dtStartDate,b.mRate, b.ID,a.mAdjustRate,a.mStaidAdjustRate ");
					sbSQL
							.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b ");
					
					////modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
					//sbSQL.append(" WHERE 1 = 1 ");
					sbSQL.append(" WHERE a.status != " + Constant.RecordStatus.INVALID);
					
					sbSQL
							.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd') ");
					sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
					sbSQL.append(" AND a.nContractID = ? ");
					sbSQL.append(" ORDER BY a.dtStartDate DESC ");
					// log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setTimestamp(1, tsDate);
					ps.setLong(2, lContractID);
					rs = ps.executeQuery();

					if (rs.next()) {
						info.setLateBasicRate(rs.getDouble("mRate")); // ������Ļ�׼����
						// ======ninh 2004-06-22 ������ ���ӹ̶���������===ִ�������㷨�ı�===//
						info.setLateRate(info.getLateBasicRate()
								* (1 + rs.getDouble("mAdjustRate") / 100)
								+ rs.getDouble("mStaidAdjustRate"));
						info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
						// �����������
						info.setLateStaidAdjustRate(rs
								.getDouble("mStaidAdjustRate"));
						// ������Ļ�׼����ID
						info.setLateBankInterestID(rs.getLong("ID"));
						// ������Чʱ��
						info.setAdjustDate(rs.getTimestamp("dtStartDate"));
						bIsHaveLate = true;
						//����������ʸ�ʽ��
						info.setLateRateString(DataFormat.formatRate(info.getLateRate(),6));
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				// �������δ��������ȡδ������ԭʼ����ֵ
				if (!bIsHaveLate) {
					info.setLateBankInterestID(info.getBankInterestID());
					info.setLateBasicRate(info.getBasicRate());
					info.setLateAdjustRate(info.getAdjustRate());
					info.setLateStaidAdjustRate(info.getStaidAdjustRate());
					info.setLateRate(info.getRate());
					info.setLateRateString(info.getFormatRate());
				}
			} else if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR) {
				strRate = info.getLiborName();
				if (info.getAdjustRate() < 0) {
					strRate = strRate
							+ " - "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getAdjustRate()));
				} else if (info.getAdjustRate() > 0) {
					strRate = strRate
							+ " + "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getAdjustRate()));
				}
				if (info.getStaidAdjustRate() < 0) {
					strRate = strRate
							+ " - "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getStaidAdjustRate()));
				} else if (info.getStaidAdjustRate() > 0) {
					strRate = strRate
							+ " + "
							+ DataFormat.formatRate(java.lang.Math.abs(info
									.getStaidAdjustRate()));
				}
				info.setLateRateString(strRate);

				info.setAdjustRate(info.getAdjustRate());
				info.setStaidAdjustRate(info.getStaidAdjustRate());
			}
			// ���ί�д���
			else {
				info.setLateBankInterestID(info.getBankInterestID());
				info.setLateBasicRate(info.getBasicRate());
				info.setLateAdjustRate(info.getAdjustRate());
				info.setLateStaidAdjustRate(info.getStaidAdjustRate());
				info.setLateRate(info.getRate());
				info.setLateRateString(info.getFormatRate());
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	/**
	 * �õ���ͬ��ǰ��� Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getGuoDianLateAmount(long lContractID, long lPayID) throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			long type = -1;
			if (lContractID > 0) {
				sbSQL.append(" SELECT nTypeid");
				sbSQL.append(" FROM loan_contractform ");
				sbSQL.append(" WHERE id = ?");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next()) {
					type = rs.getLong(1);
				}
				ps.close();
				ps = null;

				// if (type == LOANConstant.LoanType.YTDQ || type ==
				// LOANConstant.LoanType.YTZCQ)
				if (type == LOANConstant.LoanType.YT) {
					info = getYTLateAmount(lContractID);
				} else {
					sbSQL.setLength(0);
					sbSQL.append(" SELECT SUM(nvl(a.mOpenAmount,0)) OpenAmount,SUM(nvl(a.mBalance,0)) Balance,SUM(nvl(a.mInterest,0)) Interest");
					sbSQL.append(" FROM sett_subAccount a,(select ID,nContractID,nstatusid from loan_PayForm union select ID,nContractID,nstatusid from loan_DiscountCredence) b");
					sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
					sbSQL.append(" AND b.nContractID = ? and a.al_nloannoteid = ? and a.nstatusid <> 0 ");
					sbSQL.append(" AND b.nstatusid <> 0 ");
					
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					ps.setLong(2, lPayID);
					rs = ps.executeQuery();

					if (rs.next()) {
						info.setBalanceAmount(rs.getDouble("Balance")); // ��ͬ���
						info.setOpenAmount(rs.getDouble("OpenAmount")); // ��ͬ�ѷ��Ž��
						info.setRepayAmount(rs.getDouble("OpenAmount") - rs.getDouble("Balance")); // ��ͬ�ѻ����
						info.setInterestAmount(rs.getDouble("Interest")); // ��ͬӦ����Ϣ
					}
					ps.close();
					ps = null;

					sbSQL.setLength(0);
					sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
					sbSQL.append(" FROM loan_contractform c");
					sbSQL.append(" WHERE c.id = ? ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double dTmp = 0;
					if (rs.next()) {
						// if (rs.getLong("nTypeID") ==
						// LOANConstant.LoanType.ZGXEDQ || rs.getLong("nTypeID")
						// == LOANConstant.LoanType.ZGXEZCQ)
						if (type == LOANConstant.LoanType.ZGXE) {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getBalanceAmount();
						} else {
							dTmp = rs.getDouble("mExamineAmount")
									- info.getOpenAmount();
						}
						info.setUnPayAmount(dTmp); // δ���Ž��
					}
					ps.close();
					ps = null;

					sbSQL.setLength(0);
					sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
					sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
					sbSQL.append(" WHERE nContractID = ? ");
					sbSQL.append(" and nStatusID in (2,3) ");

					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lContractID);
					rs = ps.executeQuery();

					double aheadAmount = 0;
					if (rs.next()) {
						aheadAmount = rs.getDouble("aheadAmount");
						info.setAheadAmount(aheadAmount);
					}
					ps.close();
					ps = null;

				}
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return info;
	}
	
	/**
	 * �õ����ź�ͬ��ǰ��� Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return ContractAmountInfo
	 * @exception Exception
	 */
	public ContractAmountInfo getYTLateAmount(long lContractID)
			throws Exception {
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.setLength(0);
			sbSQL.append(" SELECT SUM(b.mAmount) OpenAmount,");
			sbSQL
					.append(" SUM(ROUND((b.mAmount-nvl(dd.mAmount,0)),2)) Balance");
			sbSQL.append(" FROM sett_subAccount a,loan_payform b");

			sbSQL
					.append(" ,(SELECT NVL(SUM(aa.mAmount),0) mAmount,aa.nFormid nFormid");
			sbSQL
					.append(" FROM SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
			sbSQL.append(" WHERE bb.id = aa.nsyndicationLoanReceiveID");
			sbSQL.append(" AND bb.nStatusID=3");
			sbSQL.append(" GROUP BY aa.nFormid) dd");

			sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
			sbSQL.append(" AND b.id = dd.nFormid(+)");
			sbSQL.append(" AND b.nContractID = ? ");
			//added by mzh_fu 2007/08/17 
			sbSQL.append(" AND b.nstatusid <> 0 ");
			sbSQL.append(" AND a.nstatusid <> 0 ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			if (rs.next()) {
				info.setBalanceAmount(rs.getDouble("Balance")); // ��ͬ���
				info.setOpenAmount(rs.getDouble("OpenAmount")); // ��ͬ�ѷ��Ž��
				info.setRepayAmount(rs.getDouble("OpenAmount")
						- rs.getDouble("Balance")); // ��ͬ�ѻ����
			}
			ps.close();
			ps = null;

			sbSQL.setLength(0);
			sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
			sbSQL.append(" FROM loan_contractform c");
			sbSQL.append(" WHERE c.id = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			double dTmp = 0;
			if (rs.next()) {
				dTmp = rs.getDouble("mExamineAmount") - info.getOpenAmount();
				info.setUnPayAmount(dTmp); // δ���Ž��
			}
			ps.close();
			ps = null;

			sbSQL.setLength(0);
			sbSQL.append(" SELECT sum(mAmount) as aheadAmount ");
			sbSQL.append(" FROM LOAN_AHEADREPAYFORM");
			sbSQL.append(" WHERE nContractID = ? ");
			sbSQL.append(" and nStatusID in (2,3) ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			double aheadAmount = 0;
			if (rs.next()) {
				aheadAmount = rs.getDouble("aheadAmount");
				info.setAheadAmount(aheadAmount);
			}
			ps.close();
			ps = null;

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	/**
	 * �õ��Ѿ�ת�õĴ����ͬ�ſ�ܽ��
	 * Boxu Add 2008��11��6��
	 * @param lID
	 * �ſID
	 * @return double �Ѿ�ת���ܽ��
	 * @exception Exception
	 */
	public double sumLastAttornmentAmount(long lPayID) throws Exception {
		double lastAttornmentAmount = 0.0;
		StringBuffer sbSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select sum(sa.attornmentamount) attornmentamount from sec_attornmentcontract sa ");
		    sbSQL.append(" where 1 = 1 ");
		    sbSQL.append(" and sa.statusid in ( ");
		    sbSQL.append(" "+ LOANConstant.AttornmentStatus.SAVE +", ");
		    sbSQL.append(" "+ LOANConstant.AttornmentStatus.SUBMIT +", ");
		    sbSQL.append(" "+ LOANConstant.AttornmentStatus.CHECK +", ");
		    sbSQL.append(" "+ LOANConstant.AttornmentStatus.USED +", ");
		    sbSQL.append(" "+ LOANConstant.AttornmentStatus.APPROVALING +" ");
		    sbSQL.append(" ) and sa.payid = ? ");
		    
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lPayID);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				lastAttornmentAmount = rs.getDouble("attornmentamount");
			}
		} catch (Exception e) {
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				throw new IException("Gen_E001");
			}
		}
		
		return lastAttornmentAmount;
	}
	/**
	 * �õ���ͬ��ϸ��Ϣ Create Date: 2003-10-15
	 * @param lID
	 * ��ͬID
	 * �ſlPayID
	 * @return ContractInfo ��ͬ��ϸ��Ϣ
	 * @exception Exception
	 */
	public ContractInfo findGuoDianByID(long lID, long lPayID) throws Exception {
		ContractInfo info = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			//Timestamp lastExecDate = findfinshDate(lID);
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,g.saccountno,g.id loanaccountid,");
			sbSQL.append(" b.sCode as sClientCode,b.sName as sClientName,");
			sbSQL.append(" b.sAccount as ClientAccount,");
			sbSQL.append(" iu.sName as InputUser,cu.sName as CheckUser,");
			sbSQL.append(" m.AssureAmount,");
			sbSQL.append(" l.id as PlanID,");
			sbSQL.append(" c.sname as consignname , c.scode as consigncode , ");
			sbSQL.append(" c.saddress as consignaddress ,c.sbank1 as consignbank,");
			sbSQL.append(" c.SPROVINCE as cSPROVINCE ,c.SCITY as cSCITY,");
			sbSQL.append(" c.sAccount as consignaccount , c.sZipCode as consignzip,");
			sbSQL.append(" b.sname as borrowname,b.scode as borrowcode,");
			sbSQL.append(" b.saddress as borrowaddress,b.sbank1 as borrowbank,");
			sbSQL.append(" b.sAccount as borrowaccount , b.sZipCode as borrowzip ,");
			sbSQL.append(" b.SPROVINCE as SPROVINCE , b.SCITY as SCITY ,");
			sbSQL.append(" e.sname as assurename,e.scode as assurecode,");
			sbSQL.append(" e.saddress as assureaddress,e.sbank1 as assurebank,");
			sbSQL.append(" e.SPROVINCE as assureSPROVINCE,e.SCITY as assureSCITY,");
			sbSQL.append(" e.sAccount as assureaccount , e.sZipCode as assurezip, ");
			sbSQL.append(" lp.id payid, lp.scode paycode, lp.mamount payamount, lp.dtstart startdate, lp.dtend enddate ");
			sbSQL.append(" FROM loan_contractForm a,client b, loan_payform lp, sett_account g,");
			sbSQL.append(" userInfo iu,userInfo cu,loan_loanContractPlan l,");
			sbSQL.append(" (SELECT SUM(mAmount)as AssureAmount,nContractID ");
			sbSQL.append(" FROM loan_loanContractAssure");
			sbSQL.append(" WHERE nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" GROUP BY nContractID ) m");
			sbSQL.append(" ,client c, client e, loan_loancontractassure f");
			sbSQL.append(" WHERE a.id = m.nContractID(+)");
			sbSQL.append(" AND a.id = lp.ncontractid ");
			sbSQL.append(" AND a.id = l.nContractID(+)");
			sbSQL.append(" AND l.nStatusID(+)=" + Constant.RecordStatus.VALID); 
			sbSQL.append(" AND a.nInputUserID = iu.id(+)");
			sbSQL.append(" AND a.nNextCheckUserID = cu.id(+)");
			sbSQL.append(" AND a.nBorrowClientID = b.id(+)");
			sbSQL.append(" AND lp.id = ? ");
			sbSQL.append(" AND a.nconsignclientid = c.id(+) ");
			sbSQL.append(" AND a.id = f.ncontractid(+) and e.id(+) = f.id ");
			sbSQL.append(" AND g.nclientid=a.nborrowclientid ");
			sbSQL.append(" AND g.naccounttypeid=6 ");
			  
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lPayID);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				info = new ContractInfo();
				//info.setLastExecDate(lastExecDate);// ��ͬ���ִ������
				info.setLoanID(rs.getLong("nLoanID")); // ����ID
				info.setContractID(rs.getLong("id")); // ��ͬID
				info.setOfficeID(rs.getLong("NOFFICEID")); // ���´�id
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // ����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼������
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
				info.setSubTypeID(rs.getLong("nSubTypeID")); // ����zi����
				info.setIsCircle(rs.getLong("niscircle"));//�Ƿ�ѭ������
				info.setContractCode(rs.getString("sContractCode")); // ��ͬ���
				info.setApplyCode(rs.getString("sApplyCode")); // ��������
				info.setBorrowClientID(rs.getLong("nBorrowClientID")); // ��λID
				info.setBorrowClientName(rs.getString("sClientName")); // ��λ
				info.setBorrowClientCode(rs.getString("sClientCode")); // �ͻ����
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // �����
				info.setIntervalNum(rs.getLong("nIntervalNum")); // ����
				info.setLoanReason(rs.getString("SLOANREASON"));//���ԭ��
				info.setOther(rs.getString("SOTHER")); // ����
				info.setRiskLevel(rs.getLong("nRiskLevel")); // ��ͬ���յȼ�
				info.setInterestTypeID(rs.getLong("nInterestTypeID")); // ��������
				info.setLiborRateID(rs.getLong("nLiborRateID")); // Libor����ID
				info.setDiscountDate(rs.getTimestamp("dtDiscountDate"));//��������
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setLateRateString(rInfo.getLateRateString());// ���ʣ��ַ�����ʽ
				info.setRate(rInfo.getRate()); // ����ǰ����
				info.setBasicInterestRate(rInfo.getLateBasicRate()); // ��׼����
				info.setBankInterestID(rInfo.getLateBankInterestID());
				info.setLastAttornmentAmount(rs.getDouble("lastAttornmentAmount"));

				info.setPayID(rs.getLong("payid"));
				info.setPayCode(rs.getString("paycode"));
				info.setLoanacountid(rs.getLong("loanaccountid"));
				
				// ======ninh 2004-06-22 ������ ���ӹ̶���������======//
				// info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				info.setStaidAdjustRate(rInfo.getLateStaidAdjustRate());
				// ��������ID
				// info.setAdjustRate(rs.getDouble("mAdjustRate")); //���ʵ���
				info.setAdjustRate(rInfo.getLateAdjustRate());

				// =========gqfang��Libor�����������Ҫ
				if (rs.getLong("nInterestTypeID") == LOANConstant.InterestRateType.LIBOR) {
					info.setStaidAdjustRate(rInfo.getStaidAdjustRate());
					info.setAdjustRate(rInfo.getAdjustRate());
				}

				//info.setLoanStart(rs.getTimestamp("dtStartDate")); // ��ͬ��ʼ����
				//info.setLoanEnd(rs.getTimestamp("dtEndDate")); // ��ͬ��������
				info.setLoanStart(rs.getTimestamp("startdate")); // ��ͬ��ʼ����
				info.setLoanEnd(rs.getTimestamp("enddate")); // ��ͬ��������
				
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("payamount")); // �������
				info.setExamineSelfAmount(rs.getDouble("mExamineSelfAmount")); // ��׼����˾�д����
				info.setAssureAmount(rs.getDouble("AssureAmount")); // �е��������ܶ�
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID"));

				// ������������
				info.setClientID(rs.getLong("nConsignClientID")); // ί�е�λ

				if (info.getClientID() > 0) {
					info.setClientName(getClientName(info.getClientID()));
				}

				if (info.getExamineAmount() != 0) // �������ռ�������ı���
				{
					info.setAssureRate(info.getAssureAmount() / info.getExamineAmount());
				} else {
					info.setAssureRate(0);
				}

				if ((info.getExamineAmount() - info.getAssureAmount()) > 0) {
					info.setCreditAmount(info.getExamineAmount() - info.getAssureAmount());
				} else {
					info.setCreditAmount(0);
				}

				// ���ô����ܶ������� - ������

				if (info.getExamineAmount() != 0) // ���ô�����ռ�������ı���
				{
					info.setCreditRate(info.getCreditAmount() / info.getExamineAmount());
				} else {
					info.setCreditRate(0);
				}

				info.setAreaType(rs.getLong("nTypeID1")); // ����������
				info.setIndustryType1(rs.getLong("nTypeID2")); // ����ҵ����1
				info.setIndustryType2(rs.getLong("nTypeID3")); // ����ҵ����2
				info.setIndustryType3(rs.getLong("nTypeID4")); // ����ҵ����3

				info.setAssure(getAssure(info.getContractID())); // ��֤��Ϣ
				info.setContractContent(getContractContent(info.getContractID())); // ��ͬ�ı���Ϣ
				info.setYTInfo(getFormatYT(getYT(info.getContractID()))); // ������Ϣ
				// info.setScale(rs.getDouble("mSelfAmount"));

				info.setLoanAccount(rs.getString("saccountno")); // ���λ�˺�
				info.setInputUserID(rs.getLong("nInputUserID")); // ¼����id
				info.setInputUserName(rs.getString("InputUser")); // ¼����
				info.setCheckUserName(rs.getString("CheckUser")); // �����
				info.setStatusID(rs.getLong("nStatusID")); // ��ͬ״̬
				info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID())); // ��ͬ״̬����

				info.setPlanVersionID(rs.getLong("PlanID")); // ��ִͬ�мƻ��汾��ID
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // �Ƿ��򷽸�Ϣ
				info.setDiscountClientID(rs.getLong("discountClientID")); // ��Ʊ��
				info.setDiscountClientName(rs.getString("discountClientName")); // ��Ʊ������
				info.setPurchaserInterestRate(rs.getDouble("purchaserInterestRate")); // �򷽸�Ϣ����

				// ����ҵ�����
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // ��������
				info.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID")); // ��������ȡ��ʽ
				info.setBeneficiary(rs.getString("Beneficiary")); // ������
				info.setAssureTypeID1(rs.getLong("AssureTypeID1")); // ��������1
				info.setAssureTypeID2(rs.getLong("AssureTypeID2")); // ��������2
				
				//������������
				info.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));
				info.setInterestCountTypeID(rs.getLong("NINTERESTCOUNTTYPEID"));
				info.setMatureNominalAmount(rs.getDouble("MMATURENOMINALAMOUNT"));
				//����������������

				ContractAmountInfo aInfo = new ContractAmountInfo();
				aInfo = getGuoDianLateAmount(info.getContractID(), info.getPayID());
				info.setAInfo(aInfo); // ��ͬ���
				info.setBalance(aInfo.getBalanceAmount()); // ��ͬ��ǰ���
				
				//info.setBalanceForAttornment(aInfo.getBalanceAmount() - aInfo.getAheadAmount());
				info.setBalanceForAttornment(aInfo.getBalanceAmount());

				info.setIsCredit(rs.getLong("niscredit")); // ����
				info.setIsAssure(rs.getLong("nisassure")); // ��֤
				info.setIsImpawn(rs.getLong("nisimpawn")); // ��Ѻ
				info.setIsPledge(rs.getLong("nispledge")); // ��Ѻ
				info.setIsRecognizance(rs.getLong("IsRecognizance"));// ��֤��

				// ������Ϊ��ӡȡ�����ֶ�
				// ί�������Ϣ
				info.setConsignName(rs.getString("consignname"));
				info.setConsignCode(rs.getString("consigncode"));
				info.setConsignAddress((rs.getString("cSPROVINCE")==null?"":rs.getString("cSPROVINCE")+" ʡ ")
						+ (rs.getString("cSCITY")==null?"":rs.getString("cSCITY")+" �� ")
						+ (rs.getString("consignaddress")==null?"":rs.getString("consignaddress")));
				info.setConsignBank(rs.getString("consignbank"));
				info.setConsignAccount(rs.getString("consignaccount"));
				info.setConsignZip(rs.getString("consignzip"));

				// ��������Ϣ
				info.setBorrowName(rs.getString("borrowname"));
				info.setBorrowCode(rs.getString("borrowcode"));
				info.setBorrowAddress((rs.getString("SPROVINCE")==null ? "":(rs.getString("SPROVINCE")+" ʡ "))
								+ (rs.getString("SCITY")==null ? "":(rs.getString("SCITY")+" �� "))
								+ (rs.getString("borrowaddress")==null?"":rs.getString("borrowaddress")));
				info.setBorrowBank(rs.getString("borrowbank"));
				info.setBorrowAccount(rs.getString("borrowaccount"));
				info.setBorrowZip(rs.getString("borrowzip"));

				// ���������Ϣ
				info.setAssureName(rs.getString("assurename"));
				info.setAssureCode(rs.getString("assurecode"));
				info.setAssureAddress((rs.getString("assureSPROVINCE")==null?"":rs.getString("assureSPROVINCE")+" ʡ ")
						+ (rs.getString("assureSCITY")==null?"":rs.getString("assureSCITY")+" �� ")
						+ (rs.getString("assureaddress")==null?"":rs.getString("assureaddress")));
				info.setAssureBank(rs.getString("assurebank"));
				info.setAssureAccount(rs.getString("assureaccount"));
				info.setAssureZip(rs.getString("assurezip"));
				
				//info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount")); // ծȨ���
				
				//�ſ���ý��
				//double balance = getPayformBalanceForAttornment(lPayID);
				//�Ѿ�ת�õĽ��
				double useBalance = sumLastAttornmentAmount(lPayID);
				//��ת�ý��
				info.setLeftoversAttornmentAmount(info.getBalanceForAttornment() - useBalance);
				
				// ��һ����˼���
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));

				// ȡչ����Ŀ
				strSQL = "select count(id) from LOAN_EXTENDFORM where"
						+ " NCONTRACTID=" + info.getContractID()
						+ " and nStatusID<>" + Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setExtendCount(rs2.getLong(1));
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}

				// ȡ�⻹��Ŀ
				strSQL = " select count(id) from LOAN_FREEFORM where NCONTRACTID="
						+ info.getContractID()
						+ " and nStatusID<>"
						+ Constant.RecordStatus.INVALID;
				ps2 = con.prepareStatement(strSQL);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					info.setFreeCount(rs2.getLong(1));
					System.out.println("" + info.getFreeCount());
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (ps2 != null) {
					ps2.close();
					ps2 = null;
				}

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return info;
	}
	
	/**
	 * �õ���ͬ�ı���Ϣ Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return Collection ��ͬ�ı���Ϣ
	 * @exception Exception
	 */
	public String getClientName(long lClientID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String sClientName = "";

		try {
			if (lClientID > 0) {
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" SELECT sName from Client where ID = "
						+ lClientID);

				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				if (rs.next()) {
					sClientName = rs.getString(1);
				}

				rs.close();
				rs = null;

				ps.close();
				ps = null;

				con.close();
				con = null;

			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sClientName;
	}
	
	/**
	 * �õ�������Ϣ Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return Collection ������Ϣ
	 * @exception Exception
	 */
	public Collection getAssure(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT a.*,c.sCode,c.sName,c.sContacter,c.sPhone,d.mExamineAmount");
			sbSQL
					.append(" FROM loan_loanContractAssure a,client c,loan_contractForm d");
			sbSQL.append(" WHERE a.nContractID = d.id  ");
			sbSQL.append(" AND a.nClientID = c.id  ");
			sbSQL.append(" AND a.nContractID   = ?  ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				AssureInfo info = new AssureInfo();
				info.setClientCode(rs.getString("sCode")); // �ͻ����
				info.setClientName(rs.getString("sName")); // ��λ����
				info.setAssureType(rs.getLong("nAssureTypeID")); // ������ʽID
				info.setAssureTypeName(LOANConstant.AssureType.getName(info
						.getAssureType()));
				// ������ʽ����
				info.setContact(rs.getString("sContacter")); // ��ϵ��
				info.setPhone(rs.getString("sPhone")); // �绰
				info.setAmount(rs.getDouble("mAmount")); // �������
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �������
				if (info.getExamineAmount() != 0) // ��������
				{
					info.setRate(info.getAmount() / info.getExamineAmount());
				} else {
					info.setRate(0);
				}
				info.setIsTopAssure(rs.getLong("nIsTopAssure")); // �Ƿ���߶��

				vResult.add(info);
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}
	
	/**
	 * �õ���ͬ�ı���Ϣ Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return Collection ��ͬ�ı���Ϣ
	 * @exception Exception
	 */
	public Collection getContractContent(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lContractType = -1;
		long lLoanType = -1;

		try {
			ContractContentDao dao = new ContractContentDao();

			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" SELECT a.*,c.sName,'' as sAssureCode, -1 as nAssureTypeID ");
			sbSQL
					.append(" FROM loan_ContractContent a,loan_ContractForm b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.ID");
			sbSQL.append(" AND b.nBorrowClientID = c.ID");
			sbSQL.append(" AND (a.nContractTypeID = "
					+ LOANConstant.ContractType.LOAN);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.CONSIGN);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.SHPF_KLGNBHXY);
			sbSQL.append(" OR a.nContractTypeID = "
                    + LOANConstant.ContractType.SHPF_RZTenancy);
			sbSQL.append(" OR a.nContractTypeID = "
					+ LOANConstant.ContractType.TX + ")");
			sbSQL.append(" AND a.nContractID = ?");
			sbSQL.append(" UNION ");
			sbSQL.append(" SELECT a.*,c.sName,b.sAssureCode,b.nAssureTypeID ");
			sbSQL
					.append(" FROM loan_ContractContent a,loan_loanContractAssure b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.nContractID");
			sbSQL.append(" AND a.nParentID = b.ID");
			sbSQL.append(" AND b.nClientID = c.ID");
			sbSQL.append(" AND b.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nContractTypeID != "
					+ LOANConstant.ContractType.LOAN);
			sbSQL.append(" AND a.nContractTypeID != "
					+ LOANConstant.ContractType.TX);
			sbSQL.append(" AND a.nContractID = ?");
			log4j.info("sql:"+sbSQL.toString());
			log4j.info("lContractID:"+lContractID);
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID")); // ContentID
				// info.setSerialNo(rs.getLong("nSerialNo")); //���к�
				info.setContractTypeID(rs.getLong("nContractTypeID")); // ��ͬ����ID
				info.setContractType(LOANConstant.ContractType.getName(info
						.getContractTypeID()));
				// ��ͬ����
				System.out.println("��ͬ����:"+info.getContractType());
				System.out.println("��ͬ����:"+info.getContractType());
				System.out.println("��ͬ����:"+info.getContractType());
				info.setClientName(rs.getString("sName")); // ��λ����
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); // ��֤����
				info.setCode(rs.getString("sAssureCode")); // ��֤��ͬ���
				String sPageName = dao.getContractJspName(info.getID(), info
						.getContractTypeID());
				System.out.println("JSP�ļ���:"+sPageName);
				info.setPageName(sPageName);
				info.setDocName(rs.getString("sDocName"));

				vResult.add(info);
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}
	
	/**
	 * �õ�������Ϣ Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            ��ͬID
	 * @return YTInfo ������Ϣ
	 * @exception Exception
	 */
	public Collection getYT(long lContractID) throws Exception {
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");
			sbSQL.append(" FROM loan_YT_LoanContractBankAssign a");
			sbSQL.append(" WHERE a.nContractID = ? ");
			/** ** ninh 2003-12-02 begind *** */
			sbSQL.append(" ORDER BY nIsHead ");
			// sbSQL.append(" ORDER BY nIsHead DESC");
			/** ***********���ý���**** end **** */

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				YTInfo info = new YTInfo();
				info.setAttendBankID(rs.getLong("nAttendBankID")); // ���������ڲ��������õ�ID
				info.setBankName(rs.getString("sBankName")); // ��������
				info.setAssureAmount(rs.getDouble("mAssureAmount")); // �������
				info.setCreditAmount(rs.getDouble("mCreditAmount")); // ���ý��
				info.setLoanAmount(info.getAssureAmount()
						+ info.getCreditAmount());
				// �д����
				info.setLoanRate(rs.getBigDecimal("mRate")); // �д�����
				info.setIsHead(rs.getLong("nIsHead")); // �Ƿ�ǣͷ��
				vResult.add(info);
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}
	
	/**
	 * �õ�������Ϣ Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            ������Ϣ
	 * @return YTFormatInfo ������Ϣ
	 * @exception Exception
	 */
	public YTFormatInfo getFormatYT(Collection c) throws Exception {
		YTFormatInfo info = null;

		try {
			int len = 0;

			if (c != null) {
				len = c.size();
				if (len > 0) {
					String[] lAttendBankID = new String[len]; //
					String[] sBankName = new String[len]; // ��������
					String[] dLoanAmount = new String[len]; // �д����
					String[] dLoanRate = new String[len]; // �д�����
					BigDecimal[] dLoanRate1 = new BigDecimal[len]; // �д�����
					String[] dAssureAmount = new String[len]; // �������
					String[] dCreditAmount = new String[len]; // ���ý��
					String[] lIsAhead = new String[len]; // �Ƿ�ǣͷ��

					Iterator it = c.iterator();
					if (it != null) {

						for (int i = 0; it.hasNext(); i++) {
							YTInfo yInfo1 = new YTInfo();
							yInfo1 = (YTInfo) it.next();
							lAttendBankID[i] = DataFormat.formatString(""
									+ yInfo1.getAttendBankID());
							sBankName[i] = yInfo1.getBankName() == null ? ""
									: yInfo1.getBankName();
							dLoanAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getLoanAmount());

							if (yInfo1.getLoanRate() != null) {
								dLoanRate[i] = DataFormat.format(yInfo1
										.getLoanRate(), 6);
							} else {
								dLoanRate[i] = "";
							}

							dAssureAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getAssureAmount());
							dCreditAmount[i] = DataFormat
									.formatDisabledAmount(yInfo1
											.getCreditAmount());
							lIsAhead[i] = String.valueOf(yInfo1.getIsHead());
							dLoanRate1[i] = yInfo1.getLoanRate();
						}
					}

					info = new YTFormatInfo();
					info.setAttendBankID(lAttendBankID);
					info.setBankName(sBankName);
					info.setLoanAmount(dLoanAmount);
					info.setLoanRate(dLoanRate);
					info.setAssureAmount(dAssureAmount);
					info.setCreditAmount(dCreditAmount);
					info.setIsAhead(lIsAhead);
					info.setLoanRate1(dLoanRate1);
				}
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}

		return info;
	}
	
	public ContractInfo findbyid(long id,long selfid) throws IException
	{
		ContractInfo info = null;
		PreparedStatement ps = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;
		ContractDetailDao detaildao=new ContractDetailDao();
		
		
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select  lc.nintervalnum, lc.scontractcode lcscode,");
			sbSQL.append(" lp.scode lpscode,lp.id lpid,");
			sbSQL.append(" lc.id lcid,");
			sbSQL.append(" lc.ntypeid,");
			sbSQL.append(" lp.dtstart,");
			sbSQL.append(" lp.dtend,");
			sbSQL.append(" sett_s.mbalance,");
			sbSQL.append(" cl.sName ,cl.id clid");
			sbSQL.append(" from loan_contractform lc,");
			sbSQL.append(" loan_payform      lp,");
			sbSQL.append("  client            cl,");
			sbSQL.append(" sett_subaccount   sett_s");
		       
			sbSQL.append(" where 1 = 1 \n");
			sbSQL.append("and lc.id = lp.ncontractid \n");
			sbSQL.append("and sett_s.al_nloannoteid = lp.id \n");
			sbSQL.append("and lc.nborrowclientid = cl.id \n");
			sbSQL.append("and lp.id = ? \n");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, id);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				info = new ContractInfo();
				
				info.setContractID(rs.getLong("lcid")); // ��ͬID
				info.setContractCode(rs.getString("lcscode"));
				info.setPayCode(rs.getString("lpscode"));
				info.setPayID(rs.getLong("lpid"));
				info.setLoanTypeID(rs.getLong("ntypeid"));
				info.setClientID(rs.getLong("clid"));
				info.setBorrowClientName(rs.getString("sName"));
				info.setIntervalNum(rs.getLong("nintervalnum"));
				info.setLoanStart(rs.getTimestamp("dtstart"));
				info.setLoanEnd(rs.getTimestamp("dtend"));
				info.setExamineAmount(rs.getDouble("mbalance"));
				info.setInterestRate(getLatelyRate(-1, info.getContractID(), null).getLateRate());
				info.setLeftoversAttornmentAmount(detaildao.searchSellAmount(id));
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} 
		finally 
		{
			try {

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	public long checkdatause(long sapplyformid,long loanapplyformid)
	{
		long returnid=-1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT count(a.id) num");
			sbSQL.append(" FROM CRA_LOANAPPLYFORM a");
			sbSQL.append(" WHERE a.statusid <> ? ");
			sbSQL.append(" and a.sapplyformid = ? ");
			
			if(loanapplyformid>0)
			{
			sbSQL.append(" and a.id <>  " + loanapplyformid);
			}
			

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, CRAconstant.TransactionType.counterpartBank.INVALID);
			ps.setLong(2, sapplyformid);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				returnid=rs.getLong("num");
			}
			try
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				if (con != null) {
					con.close();
					con = null;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnid;
	}
	
	public long checkloancontract(long sapplyformid )
	{
		long returnid=-1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT count(a.id) num");
			sbSQL.append(" FROM CRA_TRANSFERCONTRACTFORM a");
			sbSQL.append(" WHERE a.statusid <> ? ");
			sbSQL.append(" and a.loanapplyid = ? ");
			
			

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, CRAconstant.TransactionType.counterpartBank.INVALID);
			ps.setLong(2, sapplyformid);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				returnid=rs.getLong("num");
			}
			try
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				if (con != null) {
					con.close();
					con = null;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnid;
	}
	public PageLoader queryLoanApplyform(LoanapplyformInfo loanapplyforminfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		m_sbOrderBy = new StringBuffer();
		
		m_sbSelect.append("a.id,");
		m_sbSelect.append("e.counterpartname,");
		m_sbSelect.append("b.transferamount, ");
		m_sbSelect.append("a.sloanapplycode,");
		m_sbSelect.append("a.transtypeid,");
		m_sbSelect.append("e.zstartdate applystartdate,");
		m_sbSelect.append("e.zenddate applyenddate,");
		m_sbSelect.append("a.statusid,");
		m_sbSelect.append("a.inputuserid,");
		m_sbSelect.append("a.inputdate,");
		m_sbSelect.append("u.sname inputusername");
		
		m_sbFrom.append("CRA_LOANAPPLYFORM a,");
		m_sbFrom.append("(select sum(c.transferamount) transferamount ,c.sapplyid");
		m_sbFrom.append("   from CRA_LOANCONTRACTDETAIL c");
		m_sbFrom.append("   where c.statusid <> "+CRAconstant.TransactionStatus.DELETED);
		m_sbFrom.append("  group by c.sapplyid) b,");
		m_sbFrom.append("  CRA_TRANSFERAPPLYFORM e,");
		m_sbFrom.append("  USERINFO u");
		
		m_sbWhere.append(" b.sapplyid = a.id ");
		m_sbWhere.append(" and a.SAPPLYFORMID=e.id ");
		m_sbWhere.append(" and a.inputuserid=u.id ");
		
		if(loanapplyforminfo.getTranstypeid()>0)
		{
			m_sbWhere.append(" and a.transtypeid=" + loanapplyforminfo.getTranstypeid());
		}
		if(loanapplyforminfo.getStatusid()>0)
		{
			m_sbWhere.append(" and a.statusid= "+loanapplyforminfo.getStatusid());
		}
		if(loanapplyforminfo.getQueryloancontractcodestart()!="")
		{
			m_sbWhere.append("and a.sloanapplycode>= '"+loanapplyforminfo.getQueryloancontractcodestart()+"'");
		}
		if(loanapplyforminfo.getQueryloancontractcodeend()!="")
		{
			m_sbWhere.append("and a.sloanapplycode<= '"+loanapplyforminfo.getQueryloancontractcodeend()+"'");
		}
		if(loanapplyforminfo.getQuerycontractamountstart()>0)
		{
			m_sbWhere.append("and b.transferamount>= "+loanapplyforminfo.getQuerycontractamountstart());
		}
		if(loanapplyforminfo.getQuerycontractamountend()>0)
		{
			m_sbWhere.append("and b.transferamount<= "+loanapplyforminfo.getQuerycontractamountend());
		}
		if(loanapplyforminfo.getQuerycounterpartid()>0)
		{
			m_sbWhere.append("and e.counterpartid= "+loanapplyforminfo.getQuerycounterpartid());
		}
		
		m_sbOrderBy.append(" order by  a.sloanapplycode asc");
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoanapplyformInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +" "+m_sbOrderBy.toString());
		return pageLoader;
	}
}
