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
	
	//翻页查询
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
	 * 产生查询SQL  
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
			//业务类型
			strTemp = String.valueOf(transferApplyInfo.getTransTypeId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.transtypeid = '" + strTemp + "'");
			}
			//合同编号
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
			//交易对手编号
			strTemp = String.valueOf(transferApplyInfo.getCounterPartId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.counterpartid = '" + strTemp + "'");
			}
			//金额
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
			//录入日期	
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
			
				// 查询条件
				strSQL_con = " WHERE 1=1 ";
				strSQL_con += " AND a.nStatusID >="
						+ LOANConstant.ContractStatus.ACTIVE;
				strSQL_con += " and a.nStatusID <="
						+ LOANConstant.ContractStatus.EXTEND;
				strSQL_con += " AND a.nTypeID =" + LOANConstant.LoanType.ZY;
				strSQL_con += " AND a.mExamineAmount <> 0";
			
				// lContractIDFrom 合同编号起
				if (qInfo.getQueryloancontractcodestart()!="") {
					strSQL_con += " AND a.scontractcode >= '" + qInfo.getQueryloancontractcodestart()+"'";
				}
			
				// lContractIDTo 合同编号止
				if (qInfo.getQueryloancontractcodeend() != "") {
					strSQL_con += " AND a.scontractcode <= '" + qInfo.getQueryloancontractcodeend()+"'";
				}
			
				// lClientID借款单位ID
				if (qInfo.getClientid() > 0) {
					strSQL_con += " AND a.nBorrowClientID = " + qInfo.getClientid();
				}
			
				// dAmountFrom金额起
				if (qInfo.getQuerycontractamountstart() > 0) {
					strSQL_con += " AND b.mamount >= "
							+ qInfo.getQuerycontractamountstart();
				}
			
				// dAmountTo金额止
				if (qInfo.getQuerycontractamountend() > 0) {
					strSQL_con += " AND b.mamount <= " + qInfo.getQuerycontractamountend();
				}
			
				// lStatusID 借款合同状态
				if (qInfo.getQueryloancontractstatus() > 0) {
					strSQL_con += " AND a.nStatusID = " + qInfo.getQueryloancontractstatus();
				}
			
				// 贷款类型
				if (qInfo.getQueryloancontracttype() > 0) {
					strSQL_con += " and a.nsubtypeid = " + qInfo.getQueryloancontracttype();
				}
				//办事处
				if (qInfo.getOfficeid() > 0) {
					strSQL_con += " and  a.nofficeid = " + qInfo.getOfficeid();
				}
				//币种
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
					info.setContractID(rs.getLong("ID")); // 合同的ID
					info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
					info.setLoanTypeID(rs.getLong("nTypeID"));
					info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
					info.setContractCode(rs.getString("sContractCode")); // 合同编号
					info.setBorrowClientName(rs.getString("sName")); // 借款单位
					info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
					
					//info.setExamineAmount(rs.getDouble("mExamineAmount")); // 批准金额
					info.setExamineAmount(rs.getDouble("payamount")); // 批准金额
					
					info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
					info.setInputUserName(rs.getString("InputUserName")); // 录入人
					info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
					RateInfo rInfo = new RateInfo();
					rInfo = getLatelyRate(-1, info.getContractID(), null);
					info.setInterestRate(rInfo.getLateRate()); // 执行利率
					
					//info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款起始日期
					//info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款到期日期
					info.setLoanStart(rs.getTimestamp("startdate")); // 贷款起始日期
					info.setLoanEnd(rs.getTimestamp("enddate")); // 贷款到期日期
					
					info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
					info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
					info.setStatusID(rs.getLong("nStatusID")); // 合同状态
					info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID()));
					
					ContractAmountInfo aInfo = new ContractAmountInfo();
					long payID = rs.getLong("payid");
					//aInfo = getLateAmount(info.getContractID());
					
					//当前合同余额
					aInfo = getGuoDianLateAmount(info.getContractID(), payID);
					info.setBalance(aInfo.getBalanceAmount());  //子账户余额
					
					//合同放款单已经转让总金额
					//信贷资产转让，已转让金额取结算交易收款明细金额
					info.setLastAttornmentAmount(detaildao.searchSellAmount(payID));
					//info.setLastAttornmentAmount(rs.getDouble("lastAttornmentAmount"));
					
					//合同本金减去提前还款申请后的余额
					//info.setBalanceForAttornment(aInfo.getBalanceAmount() - aInfo.getAheadAmount());
					//直接从子账户获得账户余额,这里的余额是在结算还款后的金额,不再减去贷款还款单金额
					info.setBalanceForAttornment(aInfo.getBalanceAmount());
					
					info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount")); // 债权余额
					
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
	 * 得到执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。 Create Date: 2003-10-15
	 * 
	 * @param lLoanID
	 *            贷款ID
	 * @param lContractID
	 *            合同ID
	 * @param tsDate
	 *            时间，如传入为NULL值或空串则默认为当前日期。
	 * @return double 执行利率
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

			// 取得利率类型
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
					// 利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// 贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					// 未调整的基准利率
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT || lLoanTypeID == LOANConstant.LoanType.RZZL) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// 未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// 未调整的利率
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					// Libor利率期限
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
					// 利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					// 贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					// 未调整的基准利率
					// if ((lLoanTypeID == LOANConstant.LoanType.WT) ||
					// (lLoanTypeID == LOANConstant.LoanType.WTTJTH))
					if (lLoanTypeID == LOANConstant.LoanType.WT || lLoanTypeID ==LOANConstant.LoanType.RZZL ) {
						info.setBasicRate(rs.getDouble("mInterestRate"));
					} else {
						info.setBasicRate(rs.getDouble("mRate"));
					}
					// 未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					// 未调整的利率
					info.setRate(info.getBasicRate()
							* (1 + rs.getDouble("mAdjustRate") / 100)
							+ rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					// Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					// Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					// Libor利率期限
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
				// 通过合同ID取利率
				if (lContractID > 0) {
					sbSQL.setLength(0);
					sbSQL
							.append(" SELECT a.dtStartDate,b.mRate, b.ID,a.mAdjustRate,a.mStaidAdjustRate ");
					sbSQL
							.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b ");
					
					////modified by mzh_fu 2007/07/19 增加条件 status != Constant.RecordStatus.INVALID
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
						info.setLateBasicRate(rs.getDouble("mRate")); // 调整后的基准利率
						// ======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
						info.setLateRate(info.getLateBasicRate()
								* (1 + rs.getDouble("mAdjustRate") / 100)
								+ rs.getDouble("mStaidAdjustRate"));
						info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
						// 调整后的利率
						info.setLateStaidAdjustRate(rs
								.getDouble("mStaidAdjustRate"));
						// 调整后的基准利率ID
						info.setLateBankInterestID(rs.getLong("ID"));
						// 调整生效时间
						info.setAdjustDate(rs.getTimestamp("dtStartDate"));
						bIsHaveLate = true;
						//调整后的利率格式化
						info.setLateRateString(DataFormat.formatRate(info.getLateRate(),6));
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				// 如果利率未调整过，取未调整的原始利率值
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
			// 针对委托贷款
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
	 * 得到合同当前金额 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
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
						info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
						info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
						info.setRepayAmount(rs.getDouble("OpenAmount") - rs.getDouble("Balance")); // 合同已还金额
						info.setInterestAmount(rs.getDouble("Interest")); // 合同应收利息
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
						info.setUnPayAmount(dTmp); // 未发放金额
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
	 * 得到银团合同当前金额 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
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
				info.setBalanceAmount(rs.getDouble("Balance")); // 合同余额
				info.setOpenAmount(rs.getDouble("OpenAmount")); // 合同已发放金额
				info.setRepayAmount(rs.getDouble("OpenAmount")
						- rs.getDouble("Balance")); // 合同已还金额
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
				info.setUnPayAmount(dTmp); // 未发放金额
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
	 * 得到已经转让的贷款合同放款单总金额
	 * Boxu Add 2008年11月6日
	 * @param lID
	 * 放款单ID
	 * @return double 已经转让总金额
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
	 * 得到合同详细信息 Create Date: 2003-10-15
	 * @param lID
	 * 合同ID
	 * 放款单lPayID
	 * @return ContractInfo 合同详细信息
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
				//info.setLastExecDate(lastExecDate);// 合同最后执行日期
				info.setLoanID(rs.getLong("nLoanID")); // 贷款ID
				info.setContractID(rs.getLong("id")); // 合同ID
				info.setOfficeID(rs.getLong("NOFFICEID")); // 办事处id
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 币种
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入日期
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setSubTypeID(rs.getLong("nSubTypeID")); // 贷款zi类型
				info.setIsCircle(rs.getLong("niscircle"));//是否循环贷款
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				info.setApplyCode(rs.getString("sApplyCode")); // 申请书编号
				info.setBorrowClientID(rs.getLong("nBorrowClientID")); // 借款单位ID
				info.setBorrowClientName(rs.getString("sClientName")); // 借款单位
				info.setBorrowClientCode(rs.getString("sClientCode")); // 客户编号
				info.setLoanAmount(rs.getDouble("mLoanAmount")); // 借款金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 期限
				info.setLoanReason(rs.getString("SLOANREASON"));//借款原因
				info.setOther(rs.getString("SOTHER")); // 其他
				info.setRiskLevel(rs.getLong("nRiskLevel")); // 合同风险等级
				info.setInterestTypeID(rs.getLong("nInterestTypeID")); // 利率类型
				info.setLiborRateID(rs.getLong("nLiborRateID")); // Libor利率ID
				info.setDiscountDate(rs.getTimestamp("dtDiscountDate"));//贴现日期
				RateInfo rInfo = new RateInfo();
				rInfo = getLatelyRate(-1, info.getContractID(), null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setLateRateString(rInfo.getLateRateString());// 利率，字符串格式
				info.setRate(rInfo.getRate()); // 调整前利率
				info.setBasicInterestRate(rInfo.getLateBasicRate()); // 基准利率
				info.setBankInterestID(rInfo.getLateBankInterestID());
				info.setLastAttornmentAmount(rs.getDouble("lastAttornmentAmount"));

				info.setPayID(rs.getLong("payid"));
				info.setPayCode(rs.getString("paycode"));
				info.setLoanacountid(rs.getLong("loanaccountid"));
				
				// ======ninh 2004-06-22 需求变更 增加固定浮动利率======//
				// info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
				info.setStaidAdjustRate(rInfo.getLateStaidAdjustRate());
				// 银行利率ID
				// info.setAdjustRate(rs.getDouble("mAdjustRate")); //利率调整
				info.setAdjustRate(rInfo.getLateAdjustRate());

				// =========gqfang，Libor利率需求的需要
				if (rs.getLong("nInterestTypeID") == LOANConstant.InterestRateType.LIBOR) {
					info.setStaidAdjustRate(rInfo.getStaidAdjustRate());
					info.setAdjustRate(rInfo.getAdjustRate());
				}

				//info.setLoanStart(rs.getTimestamp("dtStartDate")); // 合同起始日期
				//info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 合同结束日期
				info.setLoanStart(rs.getTimestamp("startdate")); // 合同起始日期
				info.setLoanEnd(rs.getTimestamp("enddate")); // 合同结束日期
				
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("payamount")); // 审批金额
				info.setExamineSelfAmount(rs.getDouble("mExamineSelfAmount")); // 批准财务公司承贷金额
				info.setAssureAmount(rs.getDouble("AssureAmount")); // 有担保贷款总额
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID"));

				// 手续费率类型
				info.setClientID(rs.getLong("nConsignClientID")); // 委托单位

				if (info.getClientID() > 0) {
					info.setClientName(getClientName(info.getClientID()));
				}

				if (info.getExamineAmount() != 0) // 担保金额占审批金额的比例
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

				// 信用贷款总额（审批金额 - 担保金额）

				if (info.getExamineAmount() != 0) // 信用贷款金额占审批金额的比例
				{
					info.setCreditRate(info.getCreditAmount() / info.getExamineAmount());
				} else {
					info.setCreditRate(0);
				}

				info.setAreaType(rs.getLong("nTypeID1")); // 按地区分类
				info.setIndustryType1(rs.getLong("nTypeID2")); // 按行业分类1
				info.setIndustryType2(rs.getLong("nTypeID3")); // 按行业分类2
				info.setIndustryType3(rs.getLong("nTypeID4")); // 按行业分类3

				info.setAssure(getAssure(info.getContractID())); // 保证信息
				info.setContractContent(getContractContent(info.getContractID())); // 合同文本信息
				info.setYTInfo(getFormatYT(getYT(info.getContractID()))); // 银团信息
				// info.setScale(rs.getDouble("mSelfAmount"));

				info.setLoanAccount(rs.getString("saccountno")); // 贷款单位账号
				info.setInputUserID(rs.getLong("nInputUserID")); // 录入人id
				info.setInputUserName(rs.getString("InputUser")); // 录入人
				info.setCheckUserName(rs.getString("CheckUser")); // 审核人
				info.setStatusID(rs.getLong("nStatusID")); // 合同状态
				info.setStatus(LOANConstant.ContractStatus.getName(info.getStatusID())); // 合同状态名称

				info.setPlanVersionID(rs.getLong("PlanID")); // 合同执行计划版本号ID
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setIsPurchaserInterest(rs.getLong("IsPurchaserInterest")); // 是否买方付息
				info.setDiscountClientID(rs.getLong("discountClientID")); // 出票人
				info.setDiscountClientName(rs.getString("discountClientName")); // 出票人名称
				info.setPurchaserInterestRate(rs.getDouble("purchaserInterestRate")); // 买方付息比例

				// 担保业务相关
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate")); // 担保费率
				info.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID")); // 担保费收取方式
				info.setBeneficiary(rs.getString("Beneficiary")); // 受益人
				info.setAssureTypeID1(rs.getLong("AssureTypeID1")); // 担保类型1
				info.setAssureTypeID2(rs.getLong("AssureTypeID2")); // 担保类型2
				
				//融资租赁新增
				info.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));
				info.setInterestCountTypeID(rs.getLong("NINTERESTCOUNTTYPEID"));
				info.setMatureNominalAmount(rs.getDouble("MMATURENOMINALAMOUNT"));
				//融资租赁新增结束

				ContractAmountInfo aInfo = new ContractAmountInfo();
				aInfo = getGuoDianLateAmount(info.getContractID(), info.getPayID());
				info.setAInfo(aInfo); // 合同金额
				info.setBalance(aInfo.getBalanceAmount()); // 合同当前余额
				
				//info.setBalanceForAttornment(aInfo.getBalanceAmount() - aInfo.getAheadAmount());
				info.setBalanceForAttornment(aInfo.getBalanceAmount());

				info.setIsCredit(rs.getLong("niscredit")); // 信用
				info.setIsAssure(rs.getLong("nisassure")); // 保证
				info.setIsImpawn(rs.getLong("nisimpawn")); // 抵押
				info.setIsPledge(rs.getLong("nispledge")); // 质押
				info.setIsRecognizance(rs.getLong("IsRecognizance"));// 保证金

				// 以下是为打印取出的字段
				// 委托相关信息
				info.setConsignName(rs.getString("consignname"));
				info.setConsignCode(rs.getString("consigncode"));
				info.setConsignAddress((rs.getString("cSPROVINCE")==null?"":rs.getString("cSPROVINCE")+" 省 ")
						+ (rs.getString("cSCITY")==null?"":rs.getString("cSCITY")+" 市 ")
						+ (rs.getString("consignaddress")==null?"":rs.getString("consignaddress")));
				info.setConsignBank(rs.getString("consignbank"));
				info.setConsignAccount(rs.getString("consignaccount"));
				info.setConsignZip(rs.getString("consignzip"));

				// 借款相关信息
				info.setBorrowName(rs.getString("borrowname"));
				info.setBorrowCode(rs.getString("borrowcode"));
				info.setBorrowAddress((rs.getString("SPROVINCE")==null ? "":(rs.getString("SPROVINCE")+" 省 "))
								+ (rs.getString("SCITY")==null ? "":(rs.getString("SCITY")+" 市 "))
								+ (rs.getString("borrowaddress")==null?"":rs.getString("borrowaddress")));
				info.setBorrowBank(rs.getString("borrowbank"));
				info.setBorrowAccount(rs.getString("borrowaccount"));
				info.setBorrowZip(rs.getString("borrowzip"));

				// 担保相关信息
				info.setAssureName(rs.getString("assurename"));
				info.setAssureCode(rs.getString("assurecode"));
				info.setAssureAddress((rs.getString("assureSPROVINCE")==null?"":rs.getString("assureSPROVINCE")+" 省 ")
						+ (rs.getString("assureSCITY")==null?"":rs.getString("assureSCITY")+" 市 ")
						+ (rs.getString("assureaddress")==null?"":rs.getString("assureaddress")));
				info.setAssureBank(rs.getString("assurebank"));
				info.setAssureAccount(rs.getString("assureaccount"));
				info.setAssureZip(rs.getString("assurezip"));
				
				//info.setLeftoversAttornmentAmount(rs.getDouble("leftoversAttornmentAmount")); // 债权余额
				
				//放款单可用金额
				//double balance = getPayformBalanceForAttornment(lPayID);
				//已经转让的金额
				double useBalance = sumLastAttornmentAmount(lPayID);
				//可转让金额
				info.setLeftoversAttornmentAmount(info.getBalanceForAttornment() - useBalance);
				
				// 下一个审核级别
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));

				// 取展期数目
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

				// 取免还数目
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
	 * 得到合同文本信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 合同文本信息
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
	 * 得到担保信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 担保信息
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
				info.setClientCode(rs.getString("sCode")); // 客户编号
				info.setClientName(rs.getString("sName")); // 单位名称
				info.setAssureType(rs.getLong("nAssureTypeID")); // 担保方式ID
				info.setAssureTypeName(LOANConstant.AssureType.getName(info
						.getAssureType()));
				// 担保方式名称
				info.setContact(rs.getString("sContacter")); // 联系人
				info.setPhone(rs.getString("sPhone")); // 电话
				info.setAmount(rs.getDouble("mAmount")); // 担保金额
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 审批金额
				if (info.getExamineAmount() != 0) // 担保比例
				{
					info.setRate(info.getAmount() / info.getExamineAmount());
				} else {
					info.setRate(0);
				}
				info.setIsTopAssure(rs.getLong("nIsTopAssure")); // 是否最高额担保

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
	 * 得到合同文本信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return Collection 合同文本信息
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
				// info.setSerialNo(rs.getLong("nSerialNo")); //序列号
				info.setContractTypeID(rs.getLong("nContractTypeID")); // 合同类型ID
				info.setContractType(LOANConstant.ContractType.getName(info
						.getContractTypeID()));
				// 合同类型
				System.out.println("合同类型:"+info.getContractType());
				System.out.println("合同类型:"+info.getContractType());
				System.out.println("合同类型:"+info.getContractType());
				info.setClientName(rs.getString("sName")); // 单位名称
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); // 保证类型
				info.setCode(rs.getString("sAssureCode")); // 保证合同编号
				String sPageName = dao.getContractJspName(info.getID(), info
						.getContractTypeID());
				System.out.println("JSP文件名:"+sPageName);
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
	 * 得到银团信息 Create Date: 2003-10-15
	 * 
	 * @param lContractID
	 *            合同ID
	 * @return YTInfo 银团信息
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
			/** ***********不用降序**** end **** */

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();

			while (rs.next()) {
				YTInfo info = new YTInfo();
				info.setAttendBankID(rs.getLong("nAttendBankID")); // 银行名称在参与行设置的ID
				info.setBankName(rs.getString("sBankName")); // 银行名称
				info.setAssureAmount(rs.getDouble("mAssureAmount")); // 担保金额
				info.setCreditAmount(rs.getDouble("mCreditAmount")); // 信用金额
				info.setLoanAmount(info.getAssureAmount()
						+ info.getCreditAmount());
				// 承贷金额
				info.setLoanRate(rs.getBigDecimal("mRate")); // 承贷比例
				info.setIsHead(rs.getLong("nIsHead")); // 是否牵头行
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
	 * 得到银团信息 Create Date: 2003-10-15
	 * 
	 * @param Collection
	 *            银团信息
	 * @return YTFormatInfo 银团信息
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
					String[] sBankName = new String[len]; // 银行名称
					String[] dLoanAmount = new String[len]; // 承贷金额
					String[] dLoanRate = new String[len]; // 承贷比例
					BigDecimal[] dLoanRate1 = new BigDecimal[len]; // 承贷比例
					String[] dAssureAmount = new String[len]; // 担保金额
					String[] dCreditAmount = new String[len]; // 信用金额
					String[] lIsAhead = new String[len]; // 是否牵头行

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
				
				info.setContractID(rs.getLong("lcid")); // 合同ID
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
