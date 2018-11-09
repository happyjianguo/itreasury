/*
 * Created on 2003-10-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.loanpaynotice.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ContractFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanContractPlanDetailInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustPayConditionInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeAdjustInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeRateInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.ShowGrantLoanInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * @author fanyang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanPayNoticeDao
{
	private static Log4j log4j = null;
	public LoanPayNoticeDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * 得到执行利率，参数lLoanPayNoticeID
	 * Create Date: 2003-10-15
	 * @param lLoanPayNoticeID 放款通知单ID
	 * @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	 * @return double 执行利率
	 * @exception Exception
	 */
	public double getLatelyRate(long lLoanPayNoticeID, Timestamp tsDate) throws Exception
	{
		double dResult = 100;
		double dRate = 100;
		double dAdjustRate = 100;
		double dStaidAdjustRate = 100;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = Database.getConnection();
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(con);
			}
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT b.mrate, a.MSTAIDADJUSTRATE, a.MADJUSTRATE ");
			sbSQL.append(" FROM LOAN_RATEADJUSTPAYDETAIL a,loan_interestRate b ");
			sbSQL.append(" WHERE a.NLOANPAYNOTICEID = ? ");
			sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd') <= TO_CHAR(?,'yyyymmdd') ");
			sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
			
			////modified by mzh_fu 2007/07/19 增加条件 status != Constant.RecordStatus.INVALID
			sbSQL.append(" AND a.status != " + Constant.RecordStatus.INVALID);
			
			sbSQL.append(" ORDER BY a.dtStartDate DESC ");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lLoanPayNoticeID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dRate = rs.getDouble("mRate");
				dAdjustRate = rs.getDouble("MADJUSTRATE");
				dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
			}
			else if (lLoanPayNoticeID > 0)
			{
			    if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				sbSQL.setLength(0);
				sbSQL.append(" SELECT decode(b.mRate,null,a.mInterestRate,0,a.mInterestRate,b.mRate) mRate ");
				sbSQL.append(" FROM loan_PayForm a,loan_InterestRate b ");
				sbSQL.append(" WHERE a.nBankInterestID = b.id(+) ");
				sbSQL.append(" AND a.id = ? ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dRate = rs.getDouble("mRate");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				sbSQL.setLength(0);
				sbSQL.append(" SELECT nvl(a.MADJUSTRATE,0) MADJUSTRATE, nvl(a.MSTAIDADJUSTRATE,0) MSTAIDADJUSTRATE");
				sbSQL.append(" FROM loan_payform a,loan_contractform b ");
				sbSQL.append(" WHERE a.nContractID = b.ID ");
				sbSQL.append(" AND a.id = ? ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dAdjustRate = rs.getDouble("MADJUSTRATE");
					dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}			
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dResult;
	}
	public Collection getRateAdjustInfo(long payNoticeID, Timestamp tsDate) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try
		{
			conn = Database.getConnection();
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(conn);
			}

			strSQL =
				" SELECT b.mrate, a.dtStartDate ,a.MSTAIDADJUSTRATE, a.MADJUSTRATE FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b ,LOAN_RATEADJUSTPAYCONDITION c "
					+ " WHERE a.NLOANPAYNOTICEID = ?  "
					+ " AND TO_CHAR(c.dtInputDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')"
					+ " and a.nBankInterestID = b.id(+) "
					+ " and a.NADJUSTCONDITIONID = c.id(+) "
					+ " ORDER BY  a.dtStartDate DESC ";

			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, payNoticeID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			while (rs.next())
			{
				PayNoticeAdjustInfo rateInfo = new PayNoticeAdjustInfo();
				double dRate = rs.getDouble("mRate");
				double dAdjustRate = rs.getDouble("MADJUSTRATE");
				double dStaidAdjustRate = rs.getDouble("MSTAIDADJUSTRATE");
				double dResult = (dRate * (1 + dAdjustRate / 100)) + dStaidAdjustRate;
				rateInfo.setInterestRate(dResult);
				rateInfo.setStartDate(rs.getTimestamp("dtStartDate"));
				log4j.print("--------------");
				log4j.print(rateInfo.getStartDate() + "====" + rateInfo.getInterestRate());
				log4j.print("--------------");
				v.add(rateInfo);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return v;
	}
	/**
	 * 得到放款通知单的指定费率的值
	 * Create Date: 2003-11-6
	 * @param lRateTypeID 费率类型（利率、代办费等）
	 * @param lLoanPayNoticeID 放款通知单ID
	 * @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	 * @return PayNoticeRateInfo 放款通知单利率对象
	 * @exception Exception
	 */
	public PayNoticeRateInfo getRateValue(long lRateTypeID, long lLoanPayNoticeID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsHaveLate = true;
		PayNoticeRateInfo info = new PayNoticeRateInfo();		
		long lInterestTypeID = -1;
		String strRate = "";
		
		try
		{
			con = Database.getConnection();
			
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(con);
			}
			
			switch ((int) lRateTypeID)
			{
				//利率
				case (int) Constant.RateType.INTEREST :
				    if (lLoanPayNoticeID > 0)
					{
						sbSQL.setLength(0);
						sbSQL.append(" SELECT b.nInterestTypeID,b.nLiborRateID,nvl(b.mInterestRate,0) mInterestRate,b.nBankInterestID,nvl(b.mAdjustRate,0) mAdjustRate, ");
						sbSQL.append(" nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,nvl(c.mRate,nvl(b.mInterestRate,0)) mRate,d.LiborName,d.IntervalNum, nvl(b.overDueAdjustRate,0) overDueAdjustRate, nvl(b.overDueStaidAdjustRate,0) overDueStaidAdjustRate, b.isRemitOverDueInterest ");
						sbSQL.append(" FROM loan_payForm b,loan_interestRate c,loan_liborInterestRate d ");
						sbSQL.append(" WHERE 1 = 1 ");
						sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
						sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
						sbSQL.append(" AND b.ID = ? ");
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						rs = ps.executeQuery();
						if (rs.next())
						{
						    //利率类型
							lInterestTypeID = rs.getLong("nInterestTypeID");
							//未调整的利率	
							info.setBasicRate(rs.getDouble("mRate"));
							//未调整的基准利率
							info.setBankInterestID(rs.getLong("nBankInterestID"));
							//未调整的利率ID
							info.setRate(info.getBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
							info.setAdjustRate(rs.getDouble("mAdjustRate"));
							info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
							//Libor利率ID
							info.setLiborRateID(rs.getLong("nLiborRateID"));
							//Libor利率名称
							info.setLiborName(rs.getString("LiborName"));
							//Libor利率期限
							info.setLiborIntervalNum(rs.getLong("IntervalNum"));
							
							//modify by leiyang3 罚息利率
							info.setOverDueAdjustRate(rs.getDouble("overDueAdjustRate"));
							info.setOverDueStaidAdjustRate(rs.getDouble("overDueStaidAdjustRate"));
							info.setIsRemitOverDueInterest(rs.getLong("isRemitOverDueInterest"));
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
					}
				    else
					{
					    return null;
					}
					
					if (lInterestTypeID == LOANConstant.InterestRateType.BANK)
					{
						sbSQL.setLength(0);
						sbSQL.append(" SELECT a.dtStartDate, nvl(b.mRate,0) mRate,nvl(a.mrate,0) as newrate, b.ID, nvl(a.mStaidAdjustRate,0) mStaidAdjustRate, nvl(a.mAdjustRate,0) mAdjustRate ");
						sbSQL.append(" FROM loan_RateAdjustPayDetail a,loan_InterestRate b ");
						sbSQL.append(" WHERE a.nLoanPayNoticeID = ? ");
						
						////modified by mzh_fu 2007/07/19 增加条件 status != Constant.RecordStatus.INVALID
						sbSQL.append(" AND a.status != " + Constant.RecordStatus.INVALID);
						
						sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd') <= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
						sbSQL.append(" ORDER BY a.dtStartDate DESC ");
						log4j.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						ps.setTimestamp(2, tsDate);
						rs = ps.executeQuery();
						if (rs.next())
						{
							info.setLateBasicRate(rs.getDouble("mRate")); //调整后的基准利率
							//如果是委托贷款利率调整，因为无利率ID，需要取loan_rateadjustpaydetail中的mrate
							if(info.getLateBasicRate()==0.0){
								info.setLateBasicRate(rs.getDouble("newrate"));
							}
							info.setLateRate(info.getLateBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
							info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
							//调整后的利率
							info.setLateStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
							//调整后的基准利率ID
							info.setLateBankInterestID(rs.getLong("ID"));
							//调整生效时间
							info.setAdjustDate(rs.getTimestamp("dtStartDate"));
							
							//modify by leiyang3 罚息利率
							if(info.getIsRemitOverDueInterest() == 0)
							{
								info.setOverDueInterestRate(info.getLateRate());
							}
							else
							{
								info.setOverDueInterestRate(info.getLateRate() * (1 + info.getOverDueAdjustRate() / 100) + info.getOverDueStaidAdjustRate());
							}
						}
						else
						{
							bIsHaveLate = false;
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;

						//如果利率未调整过，取未调整的原始利率值
						if (!bIsHaveLate)
						{
							info.setLateBankInterestID(info.getBankInterestID());
							info.setLateBasicRate(info.getBasicRate());
							info.setLateAdjustRate(info.getAdjustRate());
							info.setLateStaidAdjustRate(info.getStaidAdjustRate());
							info.setLateRate(info.getRate());
							info.setLateRateString(DataFormat.formatRate(info.getLateRate()));
							
							//modify by leiyang3 罚息利率
							if(info.getIsRemitOverDueInterest() == 0)
							{
								info.setOverDueInterestRate(info.getLateRate());
							}
							else
							{
								info.setOverDueInterestRate(info.getLateRate() * (1 + info.getOverDueAdjustRate() / 100) + info.getOverDueStaidAdjustRate());
							}
						}
					}
					else if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR)
					{
					    strRate = info.getLiborName();
					    if (info.getAdjustRate() < 0)
					    {
					        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
					    }
					    else if (info.getAdjustRate() > 0)
					    {
					        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
					    }
					    if (info.getStaidAdjustRate() < 0)
					    {
					        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
					    }
					    else if (info.getStaidAdjustRate() > 0)
					    {
					        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
					    }
					    info.setLateRateString(strRate);
					    //Libor利率通知
					    sbSQL.setLength(0);
						sbSQL.append(" SELECT nvl(a.LiborRate,0) LiborRate ");
						sbSQL.append(" FROM Loan_LiborInform a ");
						sbSQL.append(" WHERE a.PayNoticeID = ? ");
						sbSQL.append(" AND TO_CHAR(a.InterestStart,'yyyymmdd') <= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND TO_CHAR(a.InterestEnd,'yyyymmdd') >= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND a.StatusID = " + LOANConstant.RecordStatus.VALID);
						sbSQL.append(" ORDER BY a.ID DESC ");
						log4j.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						ps.setTimestamp(2, tsDate);
						ps.setTimestamp(3, tsDate);
						rs = ps.executeQuery();
						if (rs.next())
						{
						    info.setLateBasicRate(rs.getDouble("LiborRate"));	//Libor利率值
						    info.setLateRate(info.getLateBasicRate() * (1 + info.getAdjustRate() / 100) + info.getStaidAdjustRate());
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
					}
					//针对委托贷款
					else
					{
						info.setLateBankInterestID(info.getBankInterestID());
						info.setLateBasicRate(info.getBasicRate());
						info.setLateAdjustRate(info.getAdjustRate());
						info.setLateStaidAdjustRate(info.getStaidAdjustRate());
						info.setLateRate(info.getRate());
						info.setLateRateString(DataFormat.formatRate(info.getLateRate()));
					}
					
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					info.setInterestRate(info.getLateRate());
					info.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.INTEREST));
					info.setRateType(Constant.RateType.INTEREST);			
					break;
				case (int) Constant.RateType.AGENT :
					break;
				case (int) Constant.RateType.ASSURE :
					break;
				case (int) Constant.RateType.CHARGE :
					sbSQL.setLength(0);
					sbSQL.append(" SELECT a.mChargeRate FROM loan_contractform a,loan_payform b ");
					sbSQL.append(" where a.ID = b.nContractID and b.ID = ? ");
					log4j.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lLoanPayNoticeID);
					rs = ps.executeQuery();
					if (rs.next())
					{
					    info.setInterestRate(rs.getDouble("mChargeRate"));
					}
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					info.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.CHARGE));
					info.setRateType(Constant.RateType.CHARGE);
					break;
				case (int) Constant.RateType.FINE :
					break;
				default :
					break;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	public String[] getLevelCheckName(long lModuleID, long lLoanTypeID, long lActionID, long lApprovalContentID) throws Exception
	{
		String[] sResult = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		Vector v = new Vector();
		try
		{
			conn = Database.getConnection();
			strSQL =
				" select b.sname from loan_ApprovalTracing a,userinfo b where nApprovalID = ( "
					+ " select id from loan_ApprovalSetting where nModuleID = ? and nLoanTypeID = ? "
					+ " and nActionID = ? )"
					+ " and nApprovalContentID = ? "
					+ " and nUserID = b.id "
					+ " order by a.dtDate ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lModuleID);
			ps.setLong(2, lLoanTypeID);
			ps.setLong(3, lActionID);
			ps.setLong(4, lApprovalContentID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				v.add(rs.getString("SNAME"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (v.size() > 0)
			{
				sResult = (String[]) v.toArray();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return sResult;
	} 
	public static void showBEIHFGrantLoan(int i, ShowGrantLoanInfo info, JspWriter out) throws Exception
	{

		String headLine = "";

		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目,以中电子为参考;
		 * ninh 
		 * 2005-03-24
		 */
		 
//		if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//		{
//			headLine = "贷款出账通知单（一式三联）";
//		}
//		else
//		{
//			headLine = "贷款发放凭证（一式四联）";
//		}

		headLine = "贷款借款凭证（一式三联）";
		
		/*  TOCONFIG—END  */
		
		try
		{
			boolean bIsYT = false;
			if (info.getLoanType().indexOf("银团") >= 0)
			{
				bIsYT = true;
			}
			String strBuffer = "";
			switch (i)
			{
				case (1) :
					strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>附<BR>件</FONT> ";
					break;
				case (2) :

				/*  TOCONFIG—TODELETE  */
				/*
				 * 产品化不再区分项目,以中电子为参考;
				 * ninh 
				 * 2005-03-24
				 */

//					if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>风<BR>险<BR>留<BR>存</FONT> ";
//					}
//					else
//					{
						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>业<BR>务<BR>留<BR>存</FONT> ";
//					}

				/*  TOCONFIG—END  */
				
					break;
				case (3) :

				/*  TOCONFIG—TODELETE  */
				/*
				 * 产品化不再区分项目,以中电子为参考;
				 * ninh 
				 * 2005-03-24
				 */

//					if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>信<BR>贷<BR>留<BR>存</FONT> ";
//					}
//					else
//					{
						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> ";
//					}

				/*  TOCONFIG—END  */
					break;
				case (4) :
					strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>留<BR>存<BR>卡<BR>片</FONT> ";
					break;
			}
			out.println(
					"<Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
					+ " <style type=\"text/css\"> "
					+ " <!-- "
					+ " .In1-table1 {  border: 2px #000000 solid} "
					+ " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
					+ " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
					+ " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"470\" nowrap><strong><font size =\"+1.5\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ headLine 
					+ "</font></font></strong></strong></TD> "
					+ "			<TD width=\"90\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"630\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"244\"><B>贷款种类：</B><u><b>"
					+ info.getLoanType()
					+ " </b></u></TD> "
					+ "    <TD width=\"186\" align=\"left\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"right\" width=\"200\"><b>"
					+ info.getContractCode()
					+ " </b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"24\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>执行利率(年)</B></td> "//将“合同利率”改为“执行利率”
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getContractRate()//该处实际显示的是：执行利率
					+ (info.getContractRate().length() == 0 ? "0" : "%")
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>放款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"center\" nowrap class=\"In1-td-rightbottom\"> &nbsp;"
					+ info.getLoanInterval()
                    +"</td>"	
					+"<td nowrap class=\"In1-td-bottom\">"
					+"<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+"<tr>"
	          		+"<td width=\"67\" align=\"center\" class=\"In1-td-right\" nowrap ><B>起息日</B></td>"
	          		+"<td width=\"164\" align=\"center\" >" 
	          		+info.getYear()
					+ "年"
					+ info.getMonth()
					+ "月"
					+ info.getDay()
					+ "日"
					+"</td>"
					+"</tr>"     
					+"</table>"
					+"</td>"
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>人民币:"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"td-bottom\">"
					+ info.getAmount()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR rowspan=\"3\">  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>摘要</B></TD> "
					+ "    <td height=\"72\" colspan=\"3\" align=\"center\" class=\"In1-td-bottom\" nowrap>&nbsp;"
					+"合同编号:"
					+ info.getContractCode()
					+";&nbsp;&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR rowspan=\"4\">  "
					+ "    <TD width=\"384\" colspan=\"3\" align=\"center\" class=\"In1-td-right\">" 
					
					+"  根据与贷款方签订的借款合同，向贵公司提出上述提款请求作为支用贵公司贷款的借据。"
					+"  借款单位保证对所提款项严格遵守借款合同的各条规定。"
					+"<br>"
					+ " 借款单位（预留印章）"
					+
							"</TD> "
					+ "    <td height=\"72\"  align=\"center\" nowrap>&nbsp;"
					+ "贷款单位（公章）"
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+"<br>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ strBuffer
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<br>"
					+ " ') "
					);

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目,以中电子为参考;
			 * ninh 
			 * 2005-03-24
			 */

//			if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//			{
//			    out.println( 
//			            " document.write(' "
//		             	+ "<Table width=\"630  \" border=\"0\">"
//						+ "  <TR> "
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[计划财务]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getManagerName()
//						+ "</TD>"
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[信贷经理]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getCheckUserName()
//						+ "</TD>"
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[经办]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getInputUserName()
//						+ "</TD>"
//						+ "  </TR>"
//						+ "  </Table>"
//						+ " ') "
//						);
//			}
//			else
//			{
			    out.println( 
			            " document.write(' "
			    		+"<br>"
		             	+ "<Table width=\"630\" border=\"0\">"
						+ "  <TR> " //+ "  <TD height=\"22\" nowrap>&nbsp; </TD>"						
						+ "    <TD width=\"120\" height=\"22\"  align=\"right\"> &nbsp;</TD>"
						+ "    <TD width=\"65\" height=\"22\" nowrap align=\"left\">"
						
						+ "</TD>"
						+ "    <TD width=\"100\" height=\"22\" nowrap align=\"right\">&nbsp; </TD>"
						+ "    <TD width=\"65\" height=\"22\" nowrap align=\"left\">"
						
						+ "</TD>"
						+ "    <TD width=\"60\" height=\"22\" nowrap align=\"right\">&nbsp; </TD>"
						+ "    <TD width=\"65\" height=\"22\" nowrap align=\"left\">"
						+"[经办人]"
						+ "</TD>"
						+ "    <TD width=\"60\" height=\"22\" nowrap align=\"right\"></TD>"
						+ "    <TD width=\"65\" height=\"22\" nowrap align=\"left\">"
						+ info.getInputUserName()
						+ "</TD>"
						+ "  </TR>"
						+ "  </Table>"
						+ " ') "
						);
//			}

			/*  TOCONFIG—END  */
			
     out.println( 
             	" document.write(' "
             	+ "</BODY> "
				+ " "
				+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	
	}
	public static void showGrantLoan(int i, ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		String headLine = "";

		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目,以中电子为参考;
		 * ninh 
		 * 2005-03-24
		 */
		 
//		if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//		{
//			headLine = "贷款出账通知单（一式三联）";
//		}
//		else
//		{
//			headLine = "贷款发放凭证（一式四联）";
//		}

		headLine = "贷款放款通知单";
		
		/*  TOCONFIG—END  */
		
		try
		{
			boolean bIsYT = false;
			if (info.getLoanType().indexOf("银团") >= 0)
			{
				bIsYT = true;
			}
			String strBuffer = "";
			//国电财务项目不需要显示第一联 信贷留存”字样，“第二联 业务留存”字样 第三联、第四联等等
			//update by xfma (马现福) 2008-6-19
//			switch (i)
//			{
//				case (1) :
//					strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>附<BR>件</FONT> ";
//					break;
//				case (2) :

				/*  TOCONFIG—TODELETE  */
				/*
				 * 产品化不再区分项目,以中电子为参考;
				 * ninh 
				 * 2005-03-24
				 */

//					if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>风<BR>险<BR>留<BR>存</FONT> ";
//					}
//					else
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>业<BR>务<BR>留<BR>存</FONT> ";
//					}

				/*  TOCONFIG—END  */
				
//					break;
//				case (3) :

				/*  TOCONFIG—TODELETE  */
				/*
				 * 产品化不再区分项目,以中电子为参考;
				 * ninh 
				 * 2005-03-24
				 */

//					if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>信<BR>贷<BR>留<BR>存</FONT> ";
//					}
//					else
//					{
//						strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> ";
//					}

				/*  TOCONFIG—END  */
//					break;
//				case (4) :
//					strBuffer = "			<TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>留<BR>存<BR>卡<BR>片</FONT> ";
//					break;
//			}
			out.println(
					"<Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
					+ " <style type=\"text/css\"> "
					+ " <!-- "
					+ " .In1-table1 {  border: 2px #000000 solid} "
					+ " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
					+ " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
					+ " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"690\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"470\" nowrap><strong><font size =\"+1.5\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ headLine 
					+ "</font></font></strong></strong></TD> "
					+ "			<TD width=\"90\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"690\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"244\"><B>贷款种类：</B><u><b>"
					+ info.getLoanType()
					+ " </b></u></TD> "
					+ "    <TD width=\"446\" align=\"right\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<TABLE width=\"690\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\" > "
					+ "  <TR>  "
					+ "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"24\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>委托单位</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getConsignUnit()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"td-bottom\">"
					+ info.getAmount()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>执行利率</B></TD> "//将“合同利率”改为“执行利率”
					+ "    <td align=\"left\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getContractRate()//该处实际显示的是：执行利率 update by xfma 2008-6-19
					+ (info.getContractRate().length() == 0 ? "0" : "%")
					+ "</td> "
					+ "    <td align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>"
					+ (bIsYT ? "代理" : "手续")
					+ "费率</b>"
					+ "</td> "
					+ "    <td align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getChargeRate()
					+ ""
					+ (info.getChargeRate().length() == 0 ? "0" : (bIsYT ? "‰" : "%"))
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>逾期利率</B></TD> "//将“合同利率”改为“执行利率”
					+ "    <td align=\"left\" colspan=\"3\"  height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOverDueInterestRate()//该处实际显示的是：执行利率 update by xfma 2008-6-19
					+ (info.getOverDueInterestRate().length() == 0 ? "0" : "%")
					+ "</td> "
					+ "  </TR> "
					+ "  <TR rowspan=\"3\">  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> ：</TD> "
					+ "    <td height=\"72\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					//+ "	<TABLE width=\"30\" border=\"0\"> "
					//+ "		<TR> "
					//+ strBuffer
					//+ "			</TD> "
					//+ "		</TR> "
					//+ "	</TABLE> "
					//+ "<br>"
					+ " ') "
					);

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目,以中电子为参考;
			 * ninh 
			 * 2005-03-24
			 */

//			if (Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//			{
//			    out.println( 
//			            " document.write(' "
//		             	+ "<Table width=\"630  \" border=\"0\">"
//						+ "  <TR> "
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[计划财务]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getManagerName()
//						+ "</TD>"
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[信贷经理]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getCheckUserName()
//						+ "</TD>"
//						+ "    <TD width=\"120\" height=\"22\" nowrap align=\"right\">[经办]&nbsp; </TD>"
//						+ "    <TD width=\"90\" height=\"22\" nowrap align=\"left\">"
//						+ info.getInputUserName()
//						+ "</TD>"
//						+ "  </TR>"
//						+ "  </Table>"
//						+ " ') "
//						);
//			}
//			else
//			{
//			国电财务项目需要显示[经办人] [部门负责人] [审核人] [分管副经理][总经理] ”字样
			//update by xfma (马现福) 2008-6-19
			
			//通过配置文件配置放款通知单打印是否显示固定的【经办人】、【部门负责人】
			//update by jywang 2012-09-28
			boolean isShow = Config.getBoolean(ConfigConstant.LOAN_PAYFORM_PRINT, false);
			if(isShow){
				//显示固定的【经办人】、【部门负责人】
				out.println(
						" document.write(' "
		             	+ "<Table width=\"690\" border=\"0\">"
						+ "  <TR> " 
						+ " <TD height=\"22\" nowrap align=\"left\"><B>[经办人]</B>"
						+ DataFormat.formatStringForPrint(Config.getProperty(Config.LOAN_PAYFORM_PRINT_INPUTUSER))
						+ " <B>[部门负责人]</B>"
						+ DataFormat.formatStringForPrint(Config.getProperty(Config.LOAN_PAYFORM_PRINT_MANAGER))
						+ "</TD>"
						+ "  </TR>"
						+ "  </Table>"
						+ " ') "
						);
			}else{
			
			    out.println( 
			            " document.write(' "
		             	+ "<Table width=\"690\" border=\"0\">"
						+ "  <TR> " 
						+ " <TD height=\"22\" nowrap align=\"center\"><B>[经办人]</B>"
						+ DataFormat.formatStringForPrint(info.getInputUserName())
						//+ "</TD>"
						+ " <B>[部门负责人]</B>"
						+ DataFormat.formatStringForPrint(info.getManagerName())
						//+ "</TD>"
						+ " <B>[审核人]</B>"
						+ DataFormat.formatStringForPrint(info.getCheckUserName())
						//+ "</TD>"
						+ " <B>[分管副经理]</B>"
						+ DataFormat.formatStringForPrint(info.getManagerDepartLeader())
						//+ "</TD>"
						+ " <B>[总经理]</B>"
						+ DataFormat.formatStringForPrint(info.getManagerLeaderName())
						+ "</TD>"
						+ "  </TR>"
						+ "  </Table>"
						+ " ') "
						);
			}
//			}

			/*  TOCONFIG—END  */
			
     out.println( 
             	" document.write(' "
             	+ "</BODY> "
				+ " "
				+ " '); </SCRIPT>  ");
		}
			
		catch (Exception e)
		{
		}
	}
	public static void noShowPrintHeadAndFooter(JspWriter out, long lTypeID, long lOfficeID)
	{
		try
		{
			out.println("<object viewastext style=\"display:none\"\n");
			out.println(" classid=\"clsid:5445be81-b796-11d2-b931-002018654e2e\"\n");
			out.println(" codebase=\"/websett/script/smsx.cab#Version=6,1,429,14\">\n");
			out.println(" <param name=\"GUID\" value=\"{C4028AB9-F659-4D91-B282-05199716D8FB}\">\n");
			out.println(" <param name=\"Path\" value=\"/websett/script/sxlic.mlf\">\n");
			out.println(" <param name=\"Revision\" value=\"0\">\n");
			out.println("</object>\n");
			out.println("<!-- MeadCo ScriptX -->\n");
			out.println("<object viewastext id=\"factory\" style=\"display:none\"\n");
			out.println(" classid=\"clsid:1663ed61-23eb-11d2-b92f-008048fdd814\"\n");
			out.println(" codebase=\"/websett/script/smsx.cab#Version=6,1,429,14\">\n");
			out.println("</object>\n");
			out.println("<script defer>\n");
			out.println("  factory.printing.header = \"\";\n");
			out.println("  factory.printing.footer = \"\";\n");
			if (lTypeID == 1)
			{
				out.println("  factory.printing.leftMargin = 0;\n");
				out.println("  factory.printing.topMargin = 0;\n");
				out.println("  factory.printing.rightMargin = 0;\n");
				out.println("  factory.printing.bottomMargin = 0;\n");
			}
			else
			{
				Connection conn = null;
				conn = Database.getConnection();
				PreparedStatement ps = conn.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
				ResultSet rs = ps.executeQuery();
				int nPrintTop = 50;
				int nPrintLeft = 40;
				if (rs.next())
				{
					nPrintTop = rs.getInt("nPrintTop");
					nPrintLeft = rs.getInt("nPrintLeft");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				out.println("  factory.printing.SetMarginMeasure(1);\n");
				out.println("  factory.printing.leftMargin =" + nPrintLeft + ";\n");
				out.println("  factory.printing.topMargin = " + nPrintTop + ";\n");
				out.println("  factory.printing.rightMargin = 0;\n");
				out.println("  factory.printing.bottomMargin = 0;\n");
				out.println("  factory.printing.portrait=false;//横向\n");
				out.println("  factory.printing.paperSize=\"A5\";\n");
			}
			out.println("  factory.printing.Print(true); ");
			out.println("</script>\n");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 查找放款通知单
	 * @param lLoanPayNoticeID 放款通知单标识
	 */
	public LoanPayNoticeInfo findLoanPayNoticeByID(long lLoanPayNoticeID) throws Exception
	{
		LoanPayNoticeInfo info = new LoanPayNoticeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Timestamp tsDate = null;
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sbankaccountcode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.id = ? and a.nAccountBankID = j.id(+)");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, lLoanPayNoticeID);
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setContractID(rs.getLong("nContractID"));
				info.setContractCode(rs.getString("sContractCode"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//已发放金额info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(lLoanPayNoticeID, null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				info.setRateadjustdate(rs.getTimestamp("rateadjustdate"));
				info.setIsRemitCompoundInterest(rs.getLong("isRemitCompoundInterest"));
				info.setIsRemitOverDueInterest(rs.getLong("isRemitOverDueInterest"));
				info.setOverDueAdjustRate(rs.getDouble("overDueAdjustRate"));
				info.setOverDueStaidAdjustRate(rs.getDouble("overDueStaidAdjustRate"));
				//因没有放款单利率调整，暂不取放款单利率调整而现算
				if(info.getIsRemitOverDueInterest() == 0)
				{
					info.setOverDueInterestRate(0.0);
				}
				else
				{
					info.setOverDueInterestRate(info.getInterestRate() * (1 + info.getOverDueAdjustRate() / 100) + info.getOverDueStaidAdjustRate());
				}
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			sb.append(" SELECT b.mrate, a.MSTAIDADJUSTRATE, a.MADJUSTRATE,b.dtvalidate dtValiDate FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b  ");
			sb.append(" WHERE a.NLOANPAYNOTICEID = ?  ");
			sb.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
			sb.append(" and a.nBankInterestID = b.id(+) ");
			sb.append(" ORDER BY  a.dtStartDate DESC ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lLoanPayNoticeID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info.setBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestRateValidate(rs.getTimestamp("dtValiDate"));
				info.setAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
			}
			else if (lLoanPayNoticeID > 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);
				sb.append(" SELECT decode( b.mRate,null,a.mInterestRate,0,a.mInterestRate ,b.mRate) mRate,b.dtvalidate dtValiDate ");
				sb.append(" FROM loan_payform a,loan_interestRate b ");
				sb.append(" WHERE a.nBankInterestID = b.id(+) ");
				sb.append(" AND a.id = ?");
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setBasicInterestRate(rs.getDouble("mRate"));
					info.setInterestRateValidate(rs.getTimestamp("dtValiDate"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);
				sb.append(" SELECT nvl(a.MADJUSTRATE,0) MADJUSTRATE, nvl(a.MSTAIDADJUSTRATE,0) MSTAIDADJUSTRATE");
				sb.append(" FROM loan_payform a,loan_contractform b ");
				sb.append(" WHERE a.nContractID = b.ID ");
				sb.append(" AND a.id = ?");
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setAdjustRate(rs.getDouble("MADJUSTRATE"));
					info.setStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			if (lLoanPayNoticeID > 0)
			{
				long type = -1;
				sb.setLength(0);
				sb.append(" SELECT c.nTypeid");
				sb.append(" FROM loan_contractform c,loan_payform p ");
				sb.append(" WHERE p.nContractid = c.id");
				sb.append(" AND p.id = ? ");

				log4j.info(sb.toString());
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					type = rs.getLong(1);
				}
				ps.close();
				ps = null;

				if (type == LOANConstant.LoanType.YT)
				{
					info.setBalance(getYTLateAmount(lLoanPayNoticeID));//余额
				}
			}
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return info;
	}

	/**
	* 得到执行利率，参数lLoanPayNoticeID
	* Create Date: 2003-10-15
	* @param lLoanPayNoticeID 放款通知单ID
	* @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	* @return double 执行利率
	* @exception Exception
	*/
	public double getYTLateAmount(long lLoanPayNoticeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		double dRet = 0;

		try
		{
			con = Database.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.setLength(0);
			sb.append(" SELECT SUM(a.mAmount) OpenAmount,");
			sb.append(" SUM(ROUND((b.mAmount-nvl(dd.mAmount,0)),2)) Balance");
			sb.append(" FROM sett_transgrantloan a,loan_payform b");

			sb.append(" ,(SELECT NVL(SUM(aa.mAmount),0) mAmount,aa.nFormid nFormid");
			sb.append(" FROM SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
			sb.append(" WHERE bb.id = aa.nsyndicationLoanReceiveID");
			sb.append(" AND bb.nStatusID in (2,3)");
			sb.append(" GROUP BY aa.nFormid) dd");

			sb.append(" WHERE a.nLoanContractID(+) = b.nContractID ");
			sb.append(" AND a.nloannoteid(+) = b.id");
			sb.append(" AND b.id = dd.nFormid(+)");
			sb.append(" AND a.nStatusID in (2,3)");
			sb.append(" AND b.id = ? ");

			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lLoanPayNoticeID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				dRet = rs.getDouble("Balance"); //余额
			}
			ps.close();
			ps = null;

			//如为历史数据需做如下处理
			if (dRet == 0)
			{
				sb.setLength(0);
				sb.append(" SELECT SUM(a.mOpenAmount/c.mrate*100) OpenAmount,");
				sb.append(" SUM(ROUND((b.mAmount-nvl(dd.mAmount,0)),2)) Balance");
				sb.append(" FROM sett_subAccount a,loan_payform b,loan_yt_loancontractbankassign c");

				sb.append(" ,(SELECT NVL(SUM(aa.mAmount),0) mAmount,aa.nFormid nFormid");
				sb.append(" FROM SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
				sb.append(" WHERE bb.id = aa.nsyndicationLoanReceiveID");
				sb.append(" AND bb.nStatusID in (2,3)");
				sb.append(" GROUP BY aa.nFormid) dd");

				sb.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sb.append(" AND b.id = dd.nFormid(+)");
				sb.append(" AND c.nContractID = b.nContractID");
				sb.append(" AND c.nIsHead = 1");
				sb.append(" AND b.id = ? ");

				log4j.info(sb.toString());
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					dRet = rs.getDouble("Balance"); //余额
				}
				ps.close();
				ps = null;
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dRet;
	}

	/**
	 * 更新放款通知单状态 added by mzh_fu 2007/06/18
	 * @param loanPayNoticeInfo
	 * @return
	 * @throws IException
	 */
	public long updateStatus(long lId,long lStatusId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL="UPDATE loan_payform set nStatusID = ? where id= ?";	
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lStatusId);
			ps.setLong(2, lId );
			
			lReturn = ps.executeUpdate();

		} catch (Exception e) {
			throw new IException("更新状态失败");
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
		return lReturn;
	}
	
	/**
	 * 更新提款通知单状态 added by mzh_fu 2007/08/16
	 * @param loanPayNoticeInfo
	 * @return
	 * @throws IException
	 */
	public long updateYTDrawNoticeStatusById(long lId,long lStatusId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL="UPDATE loan_yt_drawform set nStatusID = ? where id= ?";	
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lStatusId);
			ps.setLong(2, lId );
			
			lReturn = ps.executeUpdate();

		} catch (Exception e) {
			throw new IException("更新提款通知单状态失败");
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
		return lReturn;
	}
	
	
	/**
	 * 查询贷款类型 added by mzh_fu 2007/08/16
	 * @param 
	 * @return
	 * @throws IException
	 */
	public long findLoanTypeByPayNoticeId(long lId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL="select lc.ntypeid ntypeid from loan_payform lp,loan_contractform lc where lp.id = ? and lp.ncontractid = lc.id ";	
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lId);
				
			rs = ps.executeQuery();
			if(rs.next()){
				lReturn = rs.getLong("ntypeid");
			}

		} catch (Exception e) {
			throw new IException("查询贷款类型失败");
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
		return lReturn;
	}
	
	
	/**
	 * 查询银团提款通知单ID added by mzh_fu 2007/08/17
	 * @param 
	 * @return
	 * @throws IException
	 */
	public long findYTDrawNoticeIdByPayNoticeId(long lId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL="select ndrawnoticeid from loan_payform lp where lp.id = ? ";	
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lId);
				
			rs = ps.executeQuery();
			if(rs.next()){
				lReturn = rs.getLong("ndrawnoticeid");
			}

		} catch (Exception e) {
			throw new IException("查询银团提款通知单ID失败");
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
		return lReturn;
	}
	
	
	/**
	 * 此方法通过放款通知单的ID查询该单据的利息.
	 * @param 
	 * @return
	 * @throws IException
	 */
	public double findRepayBalanceByID(long lId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		double dReturn = 0;
		try {
			con = Database.getConnection();
			String strSQL="select a.minterest from sett_dailyaccountbalance a,"
						 +"(select id from sett_subaccount where nstatusid=1 and al_nloannoteid= ?) b"
						 +" where a.nsubaccountid = b.id"
						 +" and a.dtdate=(select max(dtdate) dtdate from sett_dailyaccountbalance  where nsubaccountid=b.id)";	
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lId);
			rs = ps.executeQuery();
			if(rs.next()){
				dReturn = rs.getDouble("minterest");
			}

		} catch (Exception e) {
			throw new IException("查询银团提款通知单ID失败");
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
		return dReturn;
	}
	
	
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param lStatusId
	 * @return
	 * @throws IException
	 */
	public long updateStatusAndCheckStatus(long lId,long lStatusId) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lReturn = -1;
		try {
			con = Database.getConnection();
			String strSQL="UPDATE loan_payform set nStatusID = ? where id= ? and nStatusID= ?";	
			
			ps = con.prepareStatement(strSQL);
			
			ps.setLong(1, lStatusId);
			ps.setLong(2, lId );
			ps.setLong(3, LOANConstant.LoanPayNoticeStatus.CHECK);
			
			lReturn = ps.executeUpdate();

		} catch (Exception e) {
			throw new IException("更新状态失败");
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
		return lReturn;
	}
	
	/**
	 * 查询待办任务
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryWaitOperation(LoanPayNoticeInfo loanPayNoticeInfo)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		List lstReturn = new ArrayList();

//		strSQL = "select a.*,b.id CONTRACTID,b.scontractcode scontractcode,b.ntypeid typeid,c.name subTypeName from loan_payform a,loan_contractform b,Loan_LoanTypeSetting c "
//				+ " where a.ncurrencyid = ? and a.nofficeid = ? "
//				+ " and a.ninputuserid = ? and a.nstatusid = ? and b.id = a.ncontractid and c.id = b.nsubtypeid "
//				+ " order by a.id desc";
		strSQL = "select * from v_loan_LoanPayNotice " +
				" where currencyid = ? and officeid = ? and inputuserid = ? and statusid = ? " +
				" order by loanTypeId,INPUTDATE desc";
		
		
		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);

			ps.setLong(1, loanPayNoticeInfo.getCurrencyID());
			ps.setLong(2, loanPayNoticeInfo.getOfficeID());
			ps.setLong(3, loanPayNoticeInfo.getInputUserID());
			ps.setLong(4, LOANConstant.LoanPayNoticeStatus.SUBMIT);

			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				LoanPayNoticeInfo _loanPayNoticeInfo = new LoanPayNoticeInfo();
		        
				_loanPayNoticeInfo.setID(rs.getLong("id"));
				_loanPayNoticeInfo.setCode(rs.getString("code"));// 放款通知单代码
				_loanPayNoticeInfo.setContractID(rs.getLong("contractid"));// 合同ID
				_loanPayNoticeInfo.setLoanTypeID(rs.getLong("loanTypeId"));//贷款类型					
				_loanPayNoticeInfo.setLoanSubTypeName(rs.getString("loanSubTypeName"));//贷款子类型名称
				
				_loanPayNoticeInfo.setContractCode(rs.getString("contractcode"));//合同编号
				_loanPayNoticeInfo.setAmount(rs.getDouble("amount"));// 金额
				_loanPayNoticeInfo.setStart(rs.getTimestamp("startdate"));// 贷款起始日期
				_loanPayNoticeInfo.setEnd(rs.getTimestamp("enddate"));// 贷款结束日期
				_loanPayNoticeInfo.setInputDate(rs.getTimestamp("INPUTDATE"));// 录入日期
				
				_loanPayNoticeInfo.setBorrowClientName(rs.getString("borrowClientName"));// 借款单位
		
				lstReturn.add(_loanPayNoticeInfo);
			}

		} catch (Exception e) {
			throw e;
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lstReturn;
	}
	
	/**
	 * added by mzh_fu 2008/04/29 
	 * 根据放款通知单查找合同ID
	 * @param noticeId 放款通知单ID
	 * @return 合同ID
	 * @throws ITreasuryDAOException
	 */
	public long findContractIdByNoticeId(long noticeId) throws ITreasuryDAOException{
		long lReturn = -1;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			String strSQL="select ncontractid from loan_payform where id = ?";	
			
			ps = con.prepareStatement(strSQL);			
			ps.setLong(1, noticeId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				lReturn = rs.getLong("ncontractid");
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("根据放款通知单查询合同信息失败",e);
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
					if(!con.isClosed())
						con.close();
					con = null;
				}
			} catch (Exception e) {
				throw new ITreasuryDAOException("根据放款通知单查询合同信息失败",e);
			}
		}
		return lReturn;
		
	}
	/**
	 * added by xwhe 2008/07/3 
	 * 根据放款通知单查找利率
	 * @param noticeId 放款通知单ID
	 * @return double
	 * @throws ITreasuryDAOException
	 */
	public double findPayNoticeRateById(long noticeId) throws ITreasuryDAOException{
		long lReturn = -1;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
        double rate = 0.0;
		try {
			con = Database.getConnection();
			String strSQL=" select  nvl(cc.mInterestRate, 0) mInterestRate,nvl(cc.mAdjustRate, 0) mAdjustRate,nvl(cc.mStaidAdjustRate, 0) mStaidAdjustRate from loan_payform cc where id = ?";	
			
			ps = con.prepareStatement(strSQL);			
			ps.setLong(1, noticeId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				rate = (rs.getDouble("mInterestRate") * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
			}

		} catch (Exception e) {
			throw new ITreasuryDAOException("根据放款通知单查询利率信息失败",e);
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
					if(!con.isClosed())
						con.close();
					con = null;
				}
			} catch (Exception e) {
				throw new ITreasuryDAOException("根据放款通知单查询利率信息失败",e);
			}
		}
		return rate;
		
	}
	/**
	 * 此方法通过放款通知单的ID查询该单据的利息.
	 * add by xwhe 2008-07-18
	 * @param 
	 * @return
	 * @throws IException
	 */
	public double findRepayInterestByID(double amount ,long lLoanPayNoticeID,long lContractID,Timestamp payDate,long nOfficeID,long nCurrencyID) throws IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Timestamp tsLastInterestDate = null;
		double dReturn = 0;
		long ntypeid = -1;//贷款类型
		AdjustInterestConditionInfo adjustInfo =  new AdjustInterestConditionInfo();
		TransRepaymentLoanInfo info = new TransRepaymentLoanInfo();
		try {
			con = Database.getConnection();
			String strSQL1 = " select c.ntypeid,n.naccountid,n.id, m.dtoutdate,nvl(m.minterestrate, 0) interestrate,nvl(m.mAdjustRate, 0) mAdjustRate,nvl(m.mStaidAdjustRate, 0) mStaidAdjustRate" +
					         " from sett_transgrantloan t, sett_subaccount n,loan_payform m,loan_contractform c  " +
					         " where t.nloanaccountid = n.naccountid and c.id=m.ncontractid and t.nloannoteid = m.id and n.NSTATUSID>0 and n.al_nloannoteid = ? and m.id = ? and t.nloancontractid=? and t.nofficeid= ? and t.ncurrencyid=? and t.nstatusid= "+SETTConstant.TransactionStatus.CHECK ;
			ps = con.prepareStatement(strSQL1);
			int index = 1;
			ps.setLong(index++, lLoanPayNoticeID);
			ps.setLong(index++, lLoanPayNoticeID);
			ps.setLong(index++, lContractID);
			ps.setLong(index++, nOfficeID);
			ps.setLong(index++, nCurrencyID);			
			rs = ps.executeQuery();
			if(rs.next()){
				
				info.setInterestStart(rs.getTimestamp("dtoutdate"));//放款日期
				info.setInterestIncome(rs.getDouble("interestrate")* (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate") );//放款单利率
				info.setSubAccountID(rs.getLong("id"));
				info.setLoanAccountID(rs.getLong("naccountid"));
				ntypeid = rs.getLong("ntypeid");
				if(info.getSubAccountID()>0)
				{
				   try
					{
					Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
					SubAccountAssemblerInfo subAccountAssemblerInfo = null;
					SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
									
					subAccountAssemblerInfo = sett_SubAccountDAO.findByID(info.getSubAccountID());
					resultInfo.setID(info.getSubAccountID());
					resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
				    tsLastInterestDate = resultInfo.getClearInterestDate();//通过子账户获取上一个结息日
					}
				    catch (Exception ie)
					{
						throw new IException(true, "子账户表中没有对应记录，查询失败", null);
					}
				    Timestamp tsRepayStartDate  =  info.getInterestStart();//放款开始日期
				    Timestamp tsRepayEndDate  =   payDate;//还款日期
				    InterestCalculation interestCalculation = new InterestCalculation(con);
				    int repayDays = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
				    int interestDays = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
				    if(repayDays>=interestDays)
				    {
				    	ResultSet rs1 = null ;
						String strSQL2 = "";
						long lRecordCount = -1;
						double	interestRate =  info.getInterestIncome();
						if(ntypeid!=LOANConstant.LoanType.WT){
						strSQL2 = " SELECT nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate FROM   loan_rateadjustpaydetail  bb ,loan_InterestRate cc ";
						strSQL2+="  WHERE   bb.nContractID= "+lContractID;
						strSQL2+=" and bb.nloanpaynoticeid ="+lLoanPayNoticeID;
						strSQL2+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
						strSQL2+=" and bb.nBankInterestID = cc.id(+) ";
						strSQL2+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
						strSQL2+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						strSQL2+=" order by bb.dtstartdate ";
						}else{
							strSQL2 = " SELECT nvl(bb.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate FROM   loan_rateadjustpaydetail  bb ,loan_InterestRate cc ";
							strSQL2+="  WHERE   bb.nContractID= "+lContractID;
							strSQL2+=" and bb.nloanpaynoticeid ="+lLoanPayNoticeID;
							strSQL2+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
							strSQL2+=" and bb.nBankInterestID = cc.id(+) ";
							strSQL2+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
							strSQL2+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
							strSQL2+=" order by bb.dtstartdate ";	
						}
						ps = con.prepareStatement(strSQL2.toString());
						rs1 = ps.executeQuery();
						while(rs1 != null && rs1.next())
						{
							interestRate = rs1.getDouble("mrate") * (1 + rs1.getDouble("mAdjustRate") / 100)+ rs1.getDouble("mStaidAdjustRate");
						}
						String strSQL3 = "";
						strSQL3 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
						strSQL3+=" WHERE   bb.nContractID= "+lContractID;
						strSQL3+=" and bb.nloanpaynoticeid ="+lLoanPayNoticeID;
						strSQL3+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
						strSQL3+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
						strSQL3+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						ps = con.prepareStatement(strSQL3.toString());
						rs = ps.executeQuery();
						if (rs != null && rs.next())
		                {
		                    lRecordCount = rs.getLong(1);
		                }
						if (lRecordCount <= 0)
		                {
							int Day = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
							double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), Day), 36000);
							info.setInterest(interest);
		                }
						else
						{
							
							String strSQL4 = "";
							if(ntypeid!=LOANConstant.LoanType.WT){
							strSQL4 = " select nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
							strSQL4+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc ";
							strSQL4+= " where bb.nContractID = "+lContractID;
							strSQL4+= " and bb.nloanpaynoticeid = "+lLoanPayNoticeID;
							strSQL4+="  and bb.STATUS ="+Constant.RecordStatus.VALID;
							strSQL4+= " and bb.nBankInterestID = cc.id(+) ";
							strSQL4+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
							strSQL4+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
							strSQL4+= " order by bb.dtstartdate ";
							}else{
								strSQL4 = " select nvl(bb.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
								strSQL4+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc ";
								strSQL4+= " where bb.nContractID = "+lContractID;
								strSQL4+= " and bb.nloanpaynoticeid = "+lLoanPayNoticeID;
								strSQL4+="  and bb.STATUS ="+Constant.RecordStatus.VALID;
								strSQL4+= " and bb.nBankInterestID = cc.id(+) ";
								strSQL4+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsLastInterestDate)+"','yyyy-mm-dd')";
								strSQL4+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
								strSQL4+= " order by bb.dtstartdate ";
								
								
							}
							ps = con.prepareStatement(strSQL4.toString());
							rs = ps.executeQuery();
							double interest2 = 0.0;
							int compareDay2 = 0;	
							while (rs != null && rs.next())
							{
								adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
								adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
							    compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, adjustInfo.getTsRateValidate(), 1);
							    System.out.println("还款天数:" + compareDay2);
							    System.out.println("还款利率:" + interestRate);
							    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), compareDay2), 36000);
							    System.out.println("还款利息:" + interest2);
							    tsLastInterestDate = adjustInfo.getTsRateValidate();
							    interestRate = adjustInfo.getDAdjustRate();
							}
							compareDay2 = (int) interestCalculation.getIntervalDays(tsLastInterestDate, tsRepayEndDate, 1);
							System.out.println("还款天数:" + compareDay2);
							System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
							interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, adjustInfo.getDAdjustRate()), compareDay2), 36000);	
							System.out.println("还款利息:" + interest2);
							info.setInterest(interest2);
						}
				    }
				    else
				    {
				    	long lRecordCount = -1;
				    	String strSQL5 = "";
				    	double	interestRate =  info.getInterestIncome();
				    	strSQL5 =" SELECT count(*) FROM   loan_rateadjustpaydetail  bb ";
				    	strSQL5+=" WHERE   bb.nContractID= "+lContractID;
				    	strSQL5+=" and bb.nloanpaynoticeid ="+lLoanPayNoticeID;
				    	strSQL5+=" and bb.STATUS ="+Constant.RecordStatus.VALID;
				    	strSQL5+=" and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
				    	strSQL5+=" and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
						ps = con.prepareStatement(strSQL5.toString());
						rs = ps.executeQuery();
						if (rs != null && rs.next())
		                {
		                    lRecordCount = rs.getLong(1);
		                }
						if (lRecordCount <= 0)
		                {
							int Day = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
							double interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, info.getInterestIncome()), Day), 36000);
							info.setInterest(interest);
		                }
						else
						{
							String strSQL6 = "";
							if(ntypeid!=LOANConstant.LoanType.WT){
							strSQL6 = " select nvl(cc.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
							strSQL6+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc  ";
							strSQL6+= " where bb.nContractID = "+lContractID;
							strSQL6+= " and bb.nloanpaynoticeid = "+lLoanPayNoticeID;
							strSQL6+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
							strSQL6+= " and bb.nBankInterestID = cc.id(+) ";
							strSQL6+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
							strSQL6+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
							strSQL6+= " order by bb.dtstartdate ";
							}else{

								strSQL6 = " select nvl(bb.mRate,0) mrate , nvl(bb.mAdjustRate,0) mAdjustRate,nvl(bb.mStaidAdjustRate,0) mStaidAdjustRate,bb.dtstartdate  ";
								strSQL6+= " FROM   loan_rateadjustpaydetail bb,loan_InterestRate cc  ";
								strSQL6+= " where bb.nContractID = "+lContractID;
								strSQL6+= " and bb.nloanpaynoticeid = "+lLoanPayNoticeID;
								strSQL6+= " and bb.STATUS ="+Constant.RecordStatus.VALID;
								strSQL6+= " and bb.nBankInterestID = cc.id(+) ";
								strSQL6+= " and bb.dtstartdate>= to_date('" + DataFormat.getDateString(tsRepayStartDate)+"','yyyy-mm-dd')";
								strSQL6+= " and bb.dtstartdate< to_date('"+ DataFormat.getDateString(tsRepayEndDate)+"','yyyy-mm-dd')";
								strSQL6+= " order by bb.dtstartdate ";
								
							}
							ps = con.prepareStatement(strSQL6.toString());
							rs = ps.executeQuery();
							double interest2 = 0.0;
							int compareDay2 = 0;	
							while (rs != null && rs.next())
							{
								adjustInfo.setDAdjustRate(rs.getDouble("mrate") * (1 + rs.getDouble("mAdjustRate") / 100)+ rs.getDouble("mStaidAdjustRate"));
								adjustInfo.setTsRateValidate(rs.getTimestamp("dtstartdate"));
							    compareDay2 = (int) interestCalculation.getIntervalDays(tsRepayStartDate, adjustInfo.getTsRateValidate(), 1);
							    System.out.println("还款天数:" + compareDay2);
							    System.out.println("还款利率:" + interestRate);
							    interest2 = interest2+ UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, interestRate), compareDay2), 36000);
							    System.out.println("还款利息:" + interest2);
							    tsRepayStartDate = adjustInfo.getTsRateValidate();
							    interestRate = adjustInfo.getDAdjustRate();
							}
							compareDay2 = (int) interestCalculation.getIntervalDays(tsRepayStartDate, tsRepayEndDate, 1);
							System.out.println("还款天数:" + compareDay2);
							System.out.println("还款利率:" + adjustInfo.getDAdjustRate());
							interest2 = interest2+UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(amount, adjustInfo.getDAdjustRate()), compareDay2), 36000);	
							System.out.println("还款利息:" + interest2);
							info.setInterest(interest2);			    	
				    }
				}				
			}

		} 
		}catch (Exception e) {
			throw new IException("查询银团提款通知单ID失败");
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
		return info.getInterest();
	}
	public static void main(String args[])
	{
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		try
		{
			dao.getRateAdjustInfo(1623, DataFormat.getDateTime("2004-7-12"));

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 在利率调整审批之后修改放款单中的利率调整日期
	 * Add by Boxu 2008年12月18日
	 * @param 
	 * @return
	 * @throws IException
	 */
	public void updateRateAdjustDate(long payNoticeID, long lAction) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanPayNoticeInfo info = null;
		Timestamp rateAdjustDate = null;
		Timestamp payformStartDate = null;
		Timestamp tempRateAdjustDate = null;
		int adjustrateterm = 0;
		String sTemp = "";
		StringBuffer strSQL = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			
			info = new LoanPayNoticeInfo();
			info = findLoanPayNoticeByID(payNoticeID);
			
			rateAdjustDate = info.getRateadjustdate();  	//调整日期
			payformStartDate = info.getStart();  			//放款单开始日期
			
			strSQL.setLength(0);
			strSQL.append(" select a.adjustrateterm from loan_loanform a, loan_contractform b, loan_payform c ");
			strSQL.append(" where a.id = b.nloanid and b.id = c.ncontractid and c.id = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			ps.setLong(1, payNoticeID);
			rs = ps.executeQuery();
			if(rs.next())
			{
				adjustrateterm = rs.getInt("adjustrateterm");
			}
			
			if(adjustrateterm > 0)
			{
				//审批完成
				if(lAction == 1)
				{
					if(rateAdjustDate != null)
					{
						tempRateAdjustDate = DataFormat.getNextMonth(rateAdjustDate, adjustrateterm);
					}
					else
					{
						tempRateAdjustDate = DataFormat.getNextMonth(payformStartDate, adjustrateterm);
					}
					
					sTemp = "to_date('"+ DataFormat.formatDate(tempRateAdjustDate) +"', 'yyyy-mm-dd')";
				}
				//取消审批
				else
				{
					tempRateAdjustDate = DataFormat.getNextMonth(rateAdjustDate, -adjustrateterm);
					
					if(tempRateAdjustDate.compareTo(payformStartDate) == 0)
					{
						sTemp = "null";
					}
					else
					{
						sTemp = "to_date('"+ DataFormat.formatDate(tempRateAdjustDate) +"', 'yyyy-mm-dd')";
					}
				}
				
				strSQL.setLength(0);
				strSQL.append(" update loan_payform set rateadjustdate = "+ sTemp +" where id = ? ");
				
				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, payNoticeID);
				ps.executeUpdate();
			}
			if(conn!=null){
				conn.close();
				conn=null;
			}
		}
		catch (SQLException e)
		{
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{	
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new IException("Gen_E001");
			}
		}
		
	}
	
	public double checkPayNotice(long lContractID, double amount, long isCircle,long nLoannoteId)throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double tempamount = 0.0;
		String strSQL = "";
		StringBuffer strBuf = new StringBuffer();
		try{
			conn = Database.getConnection();
			
			if(isCircle ==2)
			{
				 strBuf.append(" select (a.mloanamount  ");
				 strBuf.append(" -( select nvl(sum(b.mamount),0) from loan_payform b  where 1=1");
				 strBuf.append(" and b.ncontractid =" + lContractID );
				 strBuf.append(" and b.nstatusid in ("+LOANConstant.LoanPayNoticeStatus.SUBMIT+"," +LOANConstant.LoanPayNoticeStatus.CHECK +"," +LOANConstant.LoanPayNoticeStatus.USED +","+LOANConstant.LoanPayNoticeStatus.APPROVALING+")");
				 if(nLoannoteId>0){
					 strBuf.append(" and b.id!="+nLoannoteId+"");
				 }
				 strBuf.append(")");
				 strBuf.append(" -" + amount + ")resultamount");
				 strBuf.append(" from loan_contractform  a where a.id = " + lContractID);
				 strBuf.append(" ");
			}
			else
			{
				 strBuf.append(" select (a.mloanamount ");
				 strBuf.append(" - ( select nvl(sum(b.mamount),0) from loan_payform b  where 1=1");
				 strBuf.append(" and b.ncontractid =" + lContractID );
				 strBuf.append(" and b.nstatusid in ("+LOANConstant.LoanPayNoticeStatus.SUBMIT+","+LOANConstant.LoanPayNoticeStatus.CHECK +","+LOANConstant.LoanPayNoticeStatus.USED  +","+LOANConstant.LoanPayNoticeStatus.APPROVALING+")");
				 if(nLoannoteId>0){
					 strBuf.append(" and b.id!="+nLoannoteId+"");
				 }
				 strBuf.append(")");
				 strBuf.append(" + (select nvl(sum(c.mamount),0)repaymount from sett_transrepaymentloan  c where 1=1");
				 strBuf.append(" and c.nloannoteid in(select b.id from loan_payform b where b.ncontractid =" + lContractID +")");
				 strBuf.append(" and c.nstatusid = "+SETTConstant.TransactionStatus.CHECK+")" );
				 strBuf.append(" -" + amount + ")resultamount");
				 strBuf.append(" from loan_contractform  a where a.id = " + lContractID);
			}
			strSQL = strBuf.toString();
			System.out.println("---------------checkPayNotice-----------\n"+strSQL);
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				tempamount = rs.getDouble("resultamount");
			}
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new IException("Gen_E001");
			}
		}
		
		return tempamount;
	}
	
	public long getRateadJustPayCondition(AdjustPayConditionInfo adjustPayConditionInfo)  throws Exception
	{
    	/*
    	 * 定义数据库参数连接
    	 */
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;		
		/*
		 * 定义返回值
		 */
    	long tempReturn = -1;	
		/*
		 * 定义查询本金StringBuffer
		 */
		StringBuffer getRatedJustPayConditionID = new StringBuffer();    	
    	try
    	{
    		/*
    		 * 拼写SQL
    		 */
    		getRatedJustPayConditionID.append(" select count(a.*) from loan_rateadjustpaycondition a where 1=1 ");
    		/*
    		 * 加入条件判断
    		 */
    		getRatedJustPayConditionID.append(" and a.ncontractid = ? ");
    		getRatedJustPayConditionID.append(" and a.nloanpaynoticeid = ? ");
    		getRatedJustPayConditionID.append(" and nstatusId in ("+LOANConstant.InterestRateSettingStatus.SUBMIT+","+LOANConstant.InterestRateSettingStatus.APPROVALING+") ");
    		/*
    		 * 得到(preparedStatement)
    		 */
    		preparedStatement = connection.prepareStatement(getRatedJustPayConditionID.toString());  
    		/*
    		 * 添加参数
    		 */
    		preparedStatement.setLong(1, Long.parseLong(adjustPayConditionInfo.getNcontractid()));
    		preparedStatement.setLong(2, adjustPayConditionInfo.getNloanpaynoticeid());
    		/*
    		 * 执行查询
    		 */
    		resultSet = preparedStatement.executeQuery();
    		/*
    		 * 取出值
    		 */
    		if (resultSet.next())
    		{
    			tempReturn = resultSet.getLong(1);
    		}
    		
    	}
    	catch(Exception exception)
    	{
    		/*
    		 * 捕获异常
    		 */
    		exception.printStackTrace();
    	}
    	finally
    	{
    		try
    		{
    			/*
    			 * 关闭preparedStatement,并且手动置空
    			 */
    			if(preparedStatement!=null)
    			{
    				preparedStatement.close();
    				preparedStatement = null;
    			}
    			/*
    			 * 关闭数据库连接,并且手动置空
    			 */
    			if(connection!=null)
    			{
    				connection.close();
    				connection = null;
    			}
    		}
    		catch(Exception exception)
    		{
    			/*
    			 * 捕获并且抛出异常
    			 */
    			exception.printStackTrace();
    		}
    	}  
    	return tempReturn;
	}
	
	/**
	 * 得到融资租赁的合同剩余本金金额(截止到生效日期)
	 * Add by zwxiao 2010-06-19
	 * @param 
	 * @return
	 * @throws IException
	 */
	public String getPrincipalsBalace(long planID,AdjustPayConditionInfo adjustPayConditionInfo) throws Exception
	{
		String strReturn = "";
		double principalsBalace = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long remainIssue = -1;
		StringBuffer strSQL = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			strSQL.append(" select count(*) as remainIssue,nvl(sum(a.MAMOUNT),0.00) as principalsBalace ");
			strSQL.append("   from loan_loancontractplandetail a ");
			strSQL.append("  where 1=1 ");
			strSQL.append("    and a.ncontractplanid = "+planID);
			strSQL.append("    and a.dtplandate >= to_date('"+DataFormat.formatDate(adjustPayConditionInfo.getDtvalidate())+"', 'yyyy-MM-dd') ");
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if(rs.next())
			{
				remainIssue = rs.getLong("remainIssue");
				principalsBalace = rs.getDouble("principalsBalace");
			}
			strReturn = String.valueOf(remainIssue)+","+String.valueOf(principalsBalace);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new IException("Gen_E001");
			}
		}
		return strReturn;
	}
}