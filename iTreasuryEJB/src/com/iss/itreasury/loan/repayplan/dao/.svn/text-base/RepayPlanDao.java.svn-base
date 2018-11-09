/*
 * Created on 2004-3-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.repayplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.repayplan.dataentity.PlanModifyInfo;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RepayPlanDao
{

	/**
		 * 根据贷款合同号查找计划信息
		 * <p>    
		 * <b>&nbsp;</b>
		 * <ol><b>查找贷款信息</b>
		 * <ul>
		 * <li>操作数据库表ContractPayPlanVersion，ContractPayPlan
		 * <li>得到版本号最高的信息
		 * </ul>
		 * </ol>
		 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
		 *
		 * @param     long     lContractID                 贷款合同标示
		 *
		 * @param     long     lPageLineCount         每页页行数条件
		 * @param     long     lPageNo                第几页条件
		 * @param     long     lOrderParam            排序条件，根据此参数决定结果集排序条件
		 * @param     long     lDesc                  升序或降序
	
		 * 	 * @return    Collection     
		 *
		 * @exception Exception
		**/
	public Collection findPlanByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws Exception,IException
	{
		/*
		*/
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList alist = new ArrayList();
		String strCondition = "";
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long lhasMAX = -1;

		try
		{
			conn = Database.getConnection();

			sb.append("select max(NPLANVERSION) from loan_loancontractplan where nContractID = ? and NISUSED = 2");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lhasMAX = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			System.out.println("------------------lhasMAX:" + lhasMAX);

			if (lhasMAX > 0)
			{
				strCondition =
					" from  loan_loancontractplanDetail aa,loan_loancontractplan bb, (select max(NPLANVERSION) maxid from loan_loancontractplan where nContractID = "
						+ lContractID
						+ "  and NISUSED = 2) cc where bb.nContractID = ? and aa.nContractPlanID = bb.ID and bb.NPLANVERSION in cc.maxid";
			}
			else
			{
				strCondition = " from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = ? and aa.nContractPlanID = bb.ID";
			}

			switch ((int) lOrderParam)
			{
				case 1 :
					strCondition += " order by DTPLANDATE";
					break;
				case 2 :
					strCondition += " order by NPAYTYPEID";
					break;
				case 3 :
					strCondition += " order by MAMOUNT";
					break;
				case 4 :
					strCondition += " order by STYPE";
					break;
				case 5 :
					//strCondition +=  " order by FEXECUTEINTERESTRATE"; 
					break;
				case 6 :
					strCondition += " order by DTMODIFYDATE";
					break;
				default :
					strCondition += " order by DTPLANDATE";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strCondition += " desc";
			}
			sb.append("select count(*)" + strCondition);
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			//ps.setLong(2,lLoanID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("RecordCount: " + lRecordCount);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
            
            if(lRecordCount > 0)
            {
                lPageCount = lRecordCount / lPageLineCount;

                if (lRecordCount % lPageLineCount != 0)
                {
                    lPageCount++;
                }

                lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
                lRowNumEnd = lRowNumStart + lPageLineCount - 1;

                sb.append("select * from ( select a.*, rownum num from (");
                sb.append("select aa.* " + strCondition);
                sb.append(" )  a) where num between  " + lRowNumStart + " and " + lRowNumEnd);
                Log.print(sb.toString());
                ps = conn.prepareStatement(sb.toString());
                ps.setLong(1, lContractID);
                //ps.setLong(2,lLoanID);
                rs = ps.executeQuery();
                ContractDao contractDao = new ContractDao();
                while (rs.next())
                {
                    RepayPlanInfo rp_info = new RepayPlanInfo();
                    rp_info.lID = rs.getLong("ID");
                    rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
                    rp_info.sExecuteInterestRate = getPlanRate(lContractID, rp_info.tsPlanDate);
                    System.out.println("he..........." + rp_info.sExecuteInterestRate);
                    rp_info.fExecuteInterestRate = contractDao.getLatelyRate(0, lContractID, rp_info.tsPlanDate).getLateRate();
                    rp_info.lateRateString = contractDao.getLatelyRate(0, lContractID, rp_info.tsPlanDate).getLateRateString();
                    rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
                    rp_info.dAmount = rs.getDouble("MAMOUNT");
                    rp_info.sType = rs.getString("STYPE");
                    rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
                    rp_info.lContractPayPlanVersionID = rs.getLong("nContractPlanID");
                    rp_info.lCount = lPageCount;
                    rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
                    rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
                    rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");
                    rp_info.mINTERESTAMOUNT=rs.getDouble("MINTERESTAMOUNT");
                    rp_info.mRECOGNIZANCEAMOUNT=rs.getDouble("MRECOGNIZANCEAMOUNT");
                    alist.add(rp_info);
                }
                rs.close();
                rs = null;
                ps.close();
                ps = null;
            }
            else
            {
                //System.out.print(" apply no have plan ");
                throw new IException("Loan_E020");
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
            if (conn != null)
            {
                conn.close();
                conn = null;
            }

		}
        catch(IException ie)
        {
            System.out.print(" apply no have plan ");
            ie.printStackTrace();
            throw new IException("Loan_E020");
        }
		catch (Exception ex)
		{
			ex.printStackTrace();
            throw new IException("Loan_E021");
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return (alist.size() > 0 ? alist : null);
	}

	public String getPlanRate(long lContractID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		String sInterestRate = ""; // for Libor  因为Libor没有值
		//查找银行利率
		long lRateType = LOANConstant.InterestRateType.BANK; // bank rate
		double dInterestRate = 0;

		try
		{
			conn = Database.getConnection();

			sb.append("select NINTERESTTYPEID from loan_contractform where ID = " + lContractID);
			System.out.println(sb.toString());
			System.out.println("1-------------");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lRateType = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			if (lRateType == LOANConstant.InterestRateType.LIBOR)
			{ // is Libor Rate
				/*
							sb.append("select cc.SNAME,aa.FLIBORADJUST from LOANINFO aa,LIBORINTERESTRATE cc where aa.ID = ? and cc.ID = aa.NLIBORRATEID");
							System.out.println(sb.toString());
							ps = conn.prepareStatement(sb.toString());
							ps.setLong(1,lLoanID);
							rs = ps.executeQuery();
							if (rs.next()) {
								sInterestRate = rs.getString(1) + " ";
								if (rs.getDouble(2)>=0)
									sInterestRate = sInterestRate + "+" + rs.getDouble(2);
								else
									sInterestRate = sInterestRate + rs.getDouble(2);
							}
							System.out.println ("ejb---------------libor1.sInterestRate" + sInterestRate);
							rs.close(); rs = null;
							ps.close(); ps = null;
							sb.setLength(0);
				*/
			}
			else
			{ // is Bank Rate
				ContractDao contractDao = new ContractDao();
				dInterestRate = contractDao.getLatelyRate(0, lContractID, tsDate).getLateRate();
				sInterestRate = DataFormat.formatRate(dInterestRate);
			}

			// -----------------------------------------------------------end
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw e;
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
			catch (Exception e)
			{
				throw e;
			}
		}
		return sInterestRate;

	}
	
	public long updatePlanModifyStatus(PlanModifyInfo info) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_planmodifyform");
			sbSQL.append(" SET nStatusID = ? where ncontractid = ? "); // 合同状态
			
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getStatusID());
			ps.setLong(2, info.getContractID());
			
			lResult = ps.executeUpdate();
			
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(PlanModifyInfo info) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_planmodifyform");
			sbSQL.append(" SET nStatusID = ? where ncontractid = ? and nStatusID = ?"); // 合同状态
			
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getStatusID());
			ps.setLong(2, info.getContractID());
			ps.setLong(3, LOANConstant.LoanStatus.CHECK);
			
			lResult = ps.executeUpdate();
			
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	
	/**
	 * added by xiong fei 2010-07-16
	 * 获取该合同下还款计划的信息
	 * @param contractID
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findRepayPlan(long contractID) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		Collection coll = new ArrayList();
		ResultSet rs = null;
		
		try {
			con = Database.getConnection();
			if(contractID == -1){
				throw new Exception("合同ID为空");
			}
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*, ");
			sbSQL.append(" RANK() OVER(PARTITION BY NCONTRACTPLANID ORDER BY DTPLANDATE) as issue "); 
			sbSQL.append(" FROM loan_loancontractplandetail a ");
			sbSQL.append(" where ncontractplanid = ( ");
			sbSQL.append("     select max(id) from  loan_loancontractplan  ");
			sbSQL.append("     where  NSTATUSID = 1 and NISUSED = 2 and NCONTRACTID = '"+contractID+"'");
			sbSQL.append("     ) ");
			
			ps = con.prepareStatement(sbSQL.toString());			
			rs = ps.executeQuery();
			while(rs.next()){
				RepayPlanInfo info = new RepayPlanInfo();
				info.setLID(rs.getLong("id"));
				info.setLContractPayPlanVersionID(rs.getLong("ncontractplanid"));
				info.setTsPlanDate(rs.getTimestamp("dtplandate"));
				info.setNLoanOrRepay(rs.getInt("npaytypeid"));
				info.setDPlanPayAmount(rs.getDouble("MAMOUNT"));
				info.setSType(rs.getString("stype"));
				info.setTsInputDate(rs.getTimestamp("dtmodifydate"));
				info.setLExtendListID(rs.getLong("nlastextendid"));
				info.setLLastOverDueID(rs.getLong("nlastoverdueid"));
				info.setLLastVersionPlanID(rs.getLong("nlastversionplanid"));
				info.setMINTERESTAMOUNT(rs.getDouble("MINTERESTAMOUNT"));
				info.setMRECOGNIZANCEAMOUNT(rs.getDouble("mrecognizanceamount"));
				info.setIssue(rs.getLong("issue"));
				coll.add(info);
			}
			
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return coll;
	}

	/**
	 * added by xiong fei 2010-07-16
	 * 提前还款成功后重新安排计划，这里是更新计划
	 * @param coll
	 * @return long -1表示没有要更新的记录，1表示更新成功，其他数则表示失败
	 * @throws Exception
	 * @modify by yunchang
	 * @date 2010-12-01 13:15
	 * @function 修改提前还款期间如何排列还款计划表
	 */
	public long savePlan(Collection coll,ContractInfo contractInfo) throws Exception 
	{
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		long lResult = -1;
		
		try {
				if(coll==null)
				{
					throw new Exception("要添加的集合为空");
				}
				con = Database.getConnection();
				Iterator iterator = coll.iterator();
				long i = coll.size();//要插入的记录数
				long planId = 0;
				int s = 1;
				String sql1 = "select nvl(max(id),0)+1 from loan_loancontractplan";
				System.out.println("===============================================================");
				System.out.println("查询合同计划表最大ID="+sql1.toString());
				System.out.println("===============================================================");				
				ps = con.prepareStatement(sql1);
				rs = ps.executeQuery();
				while(rs.next())
				{
					planId = rs.getLong(1);
				}
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				/*
				 * 遍历合同计划表的实体内容
				 */
				while(iterator.hasNext())
				{
					RepayPlanInfo repayPlanInfo = (RepayPlanInfo)iterator.next();	
					//planId = repayPlanInfo.getLContractPayPlanVersionID();//计划版本号加1
					/*
					 * 第一笔合同计划时
					 */
					if(s==1)
					{
						StringBuffer sql = new StringBuffer();
						sql.append(" insert into loan_loancontractplan ");
						sql.append(" values(?,?,?,(select nvl(max(nplanversion),0)+1 from loan_loancontractplan where ncontractid = ?),?,'',?,?,?) ");
						System.out.println("============================================================================");
						System.out.println("重新排版还款计划信息："+sql.toString());
						System.out.println("============================================================================");
						ps = con.prepareStatement(sql.toString());
						ps.setLong(1, planId);
						ps.setLong(2, contractInfo.getLoanID());
						ps.setLong(3, contractInfo.getContractID());
						ps.setLong(4, contractInfo.getContractID());
						//ps.setLong(4, repayPlanInfo.getLContractPayPlanVersionID()+1);
						ps.setLong(5, 1);//状态为1
						ps.setLong(6, 2);//是否使用为2
						ps.setLong(7, 1);
						ps.setTimestamp(8, Env.getSystemDate(contractInfo.getOfficeID(), contractInfo.getCurrencyID()));
						int success = ps.executeUpdate();
						if(success == 1)
						{
							s++;
						}
						if(ps!=null)
						{
							ps.close();
						}
						StringBuffer sSQL = new StringBuffer();
						sSQL.append(" insert into loan_planmodifyform(id,ncontractid,nplanid,ninputuserid,dtinput,nnextcheckuserid,nstatusid,nnextchecklevel,nofficeid,ncurrencyid) ");
						sSQL.append(" values((select nvl(max(id),0)+1 from loan_planmodifyform),?,?,?,?,?,?,?,?,?) "); 
						System.out.println("=============================================================================");
						System.out.println("合同执行计划申请表="+sSQL.toString());
						System.out.println("=============================================================================");
						ps = con.prepareStatement(sSQL.toString());
						ps.setLong(1, contractInfo.getContractID());
						ps.setLong(2, planId);
						ps.setLong(3, -1);
						ps.setTimestamp(4, Env.getSystemDate());
						ps.setLong(5, -1);
						ps.setLong(6, 3);
						ps.setLong(7, 1);
						ps.setLong(8, contractInfo.getOfficeID());
						ps.setLong(9, contractInfo.getCurrencyID());
						ps.executeUpdate();
						if(ps!=null)
						{
							ps.close();
						}
					}
					
					StringBuffer sbSQL = new StringBuffer();
					sbSQL.append(" INSERT INTO loan_loancontractplandetail ");
					sbSQL.append(" VALUES((select nvl(max(id),0)+1 from loan_loancontractplandetail),?,?,?,?,?,?,?,?,?,?,?) "); 
					System.out.println("=============================================================================");
					System.out.println("合同计划明细表="+sbSQL.toString());
					System.out.println("=============================================================================");					
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, planId);
					ps.setTimestamp(2, repayPlanInfo.getTsPlanDate());
					ps.setLong(3, repayPlanInfo.getNLoanOrRepay());
					ps.setDouble(4, repayPlanInfo.getDPlanPayAmount());
					ps.setString(5, repayPlanInfo.getSType());
					ps.setTimestamp(6, repayPlanInfo.getTsInputDate());
					ps.setLong(7, repayPlanInfo.getLExtendListID());
					ps.setLong(8, repayPlanInfo.getLOVERDUEINFONEWID());
					ps.setLong(9,repayPlanInfo.getLID());
					ps.setDouble(10, repayPlanInfo.getMINTERESTAMOUNT());
					ps.setDouble(11, repayPlanInfo.getMRECOGNIZANCEAMOUNT());
					lResult = ps.executeUpdate();//往计划明细表里添加计划
					if(lResult == 0)
					{
						lResult = repayPlanInfo.getIssue();//如果更新失败，记录是第几期
						throw new Exception("系统忙，请联系管理员！");
					}
					if(ps!=null)
					{
						ps.close();
					}
				}	
			} 
			catch (Exception e) 
			{
				Log.print(e.toString());
			} 
			finally 
			{
				try 
				{
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
					Log.print(e.toString());
					throw new IException("Gen_E001");
				}
			}
		return lResult;
	}
	
	/**
	 * @author yunchang
	 * @date 2010-08-06
	 * @function 融资租赁--》结算--》匡算--》租金支付通知汇总表--》本期应付租金，本期应付利息
	 * @param long contractid(合同ID),String kuangSuanDate（租赁偿付表日期）
	 * @return RepayPlanInfo 返回匡算租金偿付表的离匡算日期最近的一期租赁偿付信息
	 */
	public RepayPlanInfo getLastPayMount(long contractid,String kuangSuanDate) throws Exception
	{
		Log.print("==========================================================================================");
		Log.print("进入方法：RepayPlanDao.getLastPayMount(long contractid,String kuangSuanDate):");
		Log.print("==========================================================================================");		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer sqlStringBuffer = new StringBuffer();
		RepayPlanInfo repayPlanInfo = null;
		
		try
		{
			/*
			 * 获取数据库连接
			 */
			connection = Database.getConnection();
			sqlStringBuffer.append(" select * from (select id, ncontractplanid, dtplandate, npaytypeid, mamount, stype, dtmodifydate, ");
			sqlStringBuffer.append(" nlastextendid, nlastoverdueid, nlastversionplanid, minterestamount, mrecognizanceamount, ");
			sqlStringBuffer.append(" rank() over(partition by ncontractplanid order by dtplandate) as issue ");
			sqlStringBuffer.append(" from loan_loancontractplanDetail ld where ld.dtplandate <= to_date('" + kuangSuanDate);
			sqlStringBuffer.append("', 'yyyy-mm-dd') and ld.ncontractplanid in ");
			sqlStringBuffer.append(" (select id from loan_loancontractplan lp where lp.ncontractid = " + contractid + " and lp.nisused = 2 and lp.nplanversion = ");
			sqlStringBuffer.append(" (select max(lp2.nplanversion) from loan_loancontractplan lp2 where lp2.nContractID = " + contractid + " and lp2.nisused = 2)) ");
			sqlStringBuffer.append(" order by dtplandate desc) where rownum = 1 ");
			/*
			 * 打印数据库执行SQL
			 */
			Log.print("strSQL = "+ sqlStringBuffer.toString());
			/*
			 * 执行数据库查询
			 */
			preparedStatement = connection.prepareStatement(sqlStringBuffer.toString());
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * 将查询信息插入到Info之中
			 */
			while(resultSet.next())
			{
				repayPlanInfo = new RepayPlanInfo();
				repayPlanInfo.setLID(resultSet.getLong("id"));//合同ID
				repayPlanInfo.setLContractPayPlanVersionID(resultSet.getLong("ncontractplanid"));//合同计划版本ID(FK_reference_loan_LoanFormPlan_ID)
				repayPlanInfo.setTsPlanDate(resultSet.getTimestamp("dtplandate"));//原始计划日期
				repayPlanInfo.setNLoanOrRepay(resultSet.getInt("npaytypeid"));//放/还款
				repayPlanInfo.setDPlanPayAmount(resultSet.getDouble("mamount"));//金额
				repayPlanInfo.setSType(resultSet.getString("stype"));//类型
				repayPlanInfo.setTsInputDate(resultSet.getTimestamp("dtmodifydate"));//更改日期
				repayPlanInfo.setLExtendListID(resultSet.getLong("nlastextendid"));//对应的展期ID
				repayPlanInfo.setLLastOverDueID(resultSet.getLong("nlastoverdueid"));//对应逾期ID
				repayPlanInfo.setLLastVersionPlanID(resultSet.getLong("nlastversionplanid"));//对应的上一版本的计划明细ID
				repayPlanInfo.setMINTERESTAMOUNT(resultSet.getDouble("minterestamount"));//利息金额
				repayPlanInfo.setMRECOGNIZANCEAMOUNT(resultSet.getDouble("mrecognizanceamount"));//保证金抵押额
				repayPlanInfo.setIssue(resultSet.getLong("issue"));//对应租金偿付表期数
			}
		}
		catch(Exception e)
		{
			/*
			 * 捕获并且抛出异常 
			 */
			Log.print("方法：ReplayPlanDao.getLastPayMount(long contractid,String kuangSuanDate)抛出异常！");
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally 
		{
			try 
			{
				if(sqlStringBuffer != null)
				{
					sqlStringBuffer = null;
				}				
				if(resultSet != null)
				{
					resultSet.close();
					resultSet = null;
				}
				if (preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if (connection != null) 
				{
					connection.close();
					connection = null;
				}
			} 
			catch (Exception e) 
			{
				Log.print("方法：ReplayPlanDao.getLastPayMount(long contractid,String kuangSuanDate)关闭数据库连接错误。");
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}		
		return repayPlanInfo;
	}
	
	/**
	 * @author yunchang
	 * @date 2010-08-07
	 * @function 融资租赁--》结算--》匡算--》租金支付通知汇总表--》逾期未付租金
	 * @param long contractid(合同ID),String kuangSuanDate（租赁偿付表日期）
	 * @return double 返回逾期未付租金值
	 */
	public double getAllNoPayMount(long contractid,String kuangSuanDate) throws Exception
	{
		Log.print("==========================================================================================");
		Log.print("进入方法：RepayPlanDao.getAllNoPayMount(long contractid,String kuangSuanDate):");
		Log.print("==========================================================================================");		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer sqlStringBuffer = new StringBuffer();
		double allNoPayMount = 0.0d;
		
		try
		{
			/*
			 * 获取数据库连接
			 */
			connection = Database.getConnection();
			sqlStringBuffer.append(" select nvl(aaa.surepayall - bbb.truepayall,0) as allNoPayMount from ");
			sqlStringBuffer.append(" (select nvl(sum(round(nvl(mamount, 0), 2)) + sum(round(nvl(minterestamount, 0), 2)),0) as surepayall from ");
			sqlStringBuffer.append(" (select id, ncontractplanid, dtplandate, npaytypeid, mamount, stype, ");
			sqlStringBuffer.append(" dtmodifydate, nlastextendid, nlastoverdueid, nlastversionplanid, minterestamount, mrecognizanceamount, ");
			sqlStringBuffer.append(" rank() over(partition by ncontractplanid order by dtplandate) as issue ");
			sqlStringBuffer.append(" from loan_loancontractplanDetail ld where ld.dtplandate <= to_date('" + kuangSuanDate + "', 'yyyy-mm-dd') ");
			sqlStringBuffer.append(" and ld.ncontractplanid in (select id from loan_loancontractplan lp  where lp.ncontractid = " + contractid );
			sqlStringBuffer.append(" and lp.nisused = 2 and lp.nplanversion = (select max(lp2.nplanversion) from loan_loancontractplan lp2 ");
			sqlStringBuffer.append(" where lp2.nContractID = " + contractid + " and lp2.nisused = 2)) order by dtplandate desc)) aaa, ");
			sqlStringBuffer.append(" (select nvl(sum(round(nvl(mcorpusamount, 0), 2)) + sum(round(nvl(minterestamount, 0), 2)),0) as truepayall from sett_transreturnfinance ");
			sqlStringBuffer.append(" where ncontractid = " + contractid + " and nstatusid = " + SETTConstant.TransactionStatus.CHECK + ") bbb");
			/*
			 * 打印数据库执行SQL
			 */
			Log.print("strSQL = "+ sqlStringBuffer.toString());
			/*
			 * 执行数据库查询
			 */
			preparedStatement = connection.prepareStatement(sqlStringBuffer.toString());
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * 将查询信息插入到Info之中
			 */
			while(resultSet.next())
			{
				/*
				 * 获取逾期未付租金
				 */
				allNoPayMount = resultSet.getDouble("allNoPayMount");
			}
		}
		catch(Exception e)
		{
			/*
			 * 捕获并且抛出异常 
			 */
			Log.print("方法：ReplayPlanDao.getLastPayMount(long contractid,String kuangSuanDate)抛出异常！");
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally 
		{
			try 
			{
				if(sqlStringBuffer != null)
				{
					sqlStringBuffer = null;
				}
				if(resultSet != null)
				{
					resultSet.close();
					resultSet = null;
				}				
				if (preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if (connection != null) 
				{
					connection.close();
					connection = null;
				}
			} 
			catch (Exception e) 
			{
				Log.print("方法：ReplayPlanDao.getLastPayMount(long contractid,String kuangSuanDate)关闭数据库连接错误。");
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}	
		
		return allNoPayMount;
	}	
	
	/**
	 * @author yunchang
	 * @date 2010-08-07
	 * @function 融资租赁--》结算--》匡算--》租金支付通知汇总表--》逾期未付期数列表集合
	 * @param long contractid(合同ID),String kuangSuanDate（租赁偿付表日期）
	 * @return String 返回逾期未付期数
	 */
	public String getIssueList(long contractid,String kuangSuanDate) throws Exception
	{
		Log.print("==========================================================================================");
		Log.print("进入方法：RepayPlanDao.getIssueList(long contractid,String kuangSuanDate):");
		Log.print("==========================================================================================");		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer sqlStringBuffer = new StringBuffer();
		StringBuffer issueList = new StringBuffer();
		String returnResult = "";
		try
		{
			/*
			 * 获取数据库连接
			 */
			connection = Database.getConnection();
			sqlStringBuffer.append(" select issue from (select rank() over(partition by ncontractplanid order by dtplandate) as issue ");
			sqlStringBuffer.append(" from loan_loancontractplanDetail ld where ld.dtplandate <= to_date('" + kuangSuanDate + "', 'yyyy-mm-dd') ");
			sqlStringBuffer.append(" and ld.ncontractplanid in (select id from loan_loancontractplan lp where lp.ncontractid = " + contractid );
			sqlStringBuffer.append(" and lp.nisused = 2 and lp.nplanversion = (select max(lp2.nplanversion) from loan_loancontractplan lp2 ");
			sqlStringBuffer.append(" where lp2.nContractID = " + contractid + " and lp2.nisused = 2)) order by dtplandate) ");
			sqlStringBuffer.append(" where issue not in (select issue from sett_transreturnfinance where ncontractid = " + contractid + " and nstatusid = " + SETTConstant.TransactionStatus.CHECK + ")");
			/*
			 * 打印数据库执行SQL
			 */
			Log.print("strSQL = "+ sqlStringBuffer.toString());
			/*
			 * 执行数据库查询
			 */
			preparedStatement = connection.prepareStatement(sqlStringBuffer.toString());
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * 将查询信息插入到Info之中
			 */
			while(resultSet.next())
			{
				/*
				 * 获取逾期未付期数
				 */
				issueList.append(String.valueOf(resultSet.getLong("issue"))+",");
			}
			/*
			 * 当结果为空的时候
			 */
			if(issueList.length()-1 >= 0)
			{
				returnResult = issueList.substring(0,issueList.length()-1);
			}
			else
			{
				returnResult = issueList.toString();
			}
		}
		catch(Exception e)
		{
			/*
			 * 捕获并且抛出异常 
			 */
			Log.print("方法：ReplayPlanDao.getIssueList(long contractid,String kuangSuanDate)抛出异常！");
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally 
		{
			try 
			{
				if(sqlStringBuffer != null)
				{
					sqlStringBuffer = null;
				}				
				if(resultSet != null)
				{
					resultSet.close();
					resultSet = null;
				}				
				if (preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if (connection != null) 
				{
					connection.close();
					connection = null;
				}
			} 
			catch (Exception e) 
			{
				Log.print("方法：ReplayPlanDao.getIssueList(long contractid,String kuangSuanDate)关闭数据库连接错误。");
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}	
		
		return returnResult;
	}	
	
}
