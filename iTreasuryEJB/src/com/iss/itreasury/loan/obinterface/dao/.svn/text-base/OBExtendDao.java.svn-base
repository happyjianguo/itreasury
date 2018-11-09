package com.iss.itreasury.loan.obinterface.dao;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;
import com.iss.itreasury.loan.obinterface.dataentity.ContractPlanDetailInfo;
import com.iss.itreasury.loan.obinterface.dataentity.ContractPlanInfo;
import com.iss.itreasury.loan.obinterface.dataentity.OBBackInfo;
import com.iss.itreasury.loan.obinterface.dataentity.OBExtendInfo;
import com.iss.itreasury.loan.obinterface.dataentity.ExtendDetailInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.InterestRateInfo;
import com.iss.itreasury.loan.util.*;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBExtendDao
{
	private static Log4j log4j = null;
	public OBExtendDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * Method findByID.
	 * 根据ID查询一条展期指令的信息
	   查询表：ob_extend,ob_extenddetail,ob_contractplan,ob_contractplandetail
	   返回：OBExtendInfo
	   注：查询ob_extend，ob_contractplan返回一条记录
	   查询ob_extenddetail，ob_contractplandetail返回多条记录，将其组成Collection,set到
	   OBExtendInfo中
	 * @param lID
	 * @return OBExtendInfo
	 * @throws Exception
	 */
	public OBExtendInfo findByID(long lID) throws Exception
	{
		OBExtendInfo resultInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ExtendDetailInfo extendDetailInfo = null;
		ContractPlanInfo contractPlanInfo = null;
		ContractPlanDetailInfo contractPlanDetailInfo = null;
		Vector vctExtendDetailInfo = new Vector();
		Vector vctContractPlanDetailInfo = new Vector();
		try
		{
			log4j.info("\n=======findByID start====");
			conn = Database.getConnection();
			//查询表ob_extend
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_extend ");
			sbSQL.append(" where ID=? ");
			log4j.info("=========sbSQL start operate table:ob_extend===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_extend===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				resultInfo = new OBExtendInfo();
				resultInfo.setID(lID);
				resultInfo.setContractID(rs.getLong("NCONTRACTID"));
				resultInfo.setPlanVersionID(rs.getLong("NPLANVERSIONID"));
				resultInfo.setLastPlanVersionID(rs.getLong("NLASTPLANVERSIONID"));
				resultInfo.setSerialNO(rs.getLong("NSERIALNO"));
				resultInfo.setInstructionNO(rs.getString("SINSTRUCTIONNO"));
				resultInfo.setExtendReason(rs.getString("SEXTENDREASON"));
				resultInfo.setReturnPostEnd(rs.getString("SRETURNPOSTPEND"));
				resultInfo.setOtherContent(rs.getString("SOTHERCONTENT"));
				resultInfo.setBankInterestID(rs.getLong("NBANKINTERESTID"));
				resultInfo.setStatusID(rs.getLong("NSTATUSID"));
				resultInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				resultInfo.setInterestTypeID(rs.getLong("NINTERESTTYPEID"));
				resultInfo.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				resultInfo.setExtendTypeID(rs.getLong("NEXTENDTYPEID"));
				resultInfo.setIsCanAccept(rs.getLong("ISCANACCEPT"));
				resultInfo.setInID(rs.getLong("NINID"));
				resultInfo.setHandleUserID(rs.getLong("NHANDLEUSERID"));
				//resultInfo.setInterestAdjust(rs.getDouble("MINTERESTADJUST")); //调整利率
				//resultInfo.setInterestRate(rs.getDouble("MINTERESTRATE")); //基准利率
                
                /*============2004-03-24 ninghao 基准利率、调整利率没有携带======*/
                Log.print("基准利率"+rs.getDouble("MINTERESTRATE"));
                //Log.print("调整利率"+rs.getDouble("MINTERESTADJUST"));
                if(rs.getLong("NBANKINTERESTID") > 0)  
                {
                    //Log.print("1");           
                    //resultInfo.setInterestAdjust(
                    //    Double.parseDouble(
                    //        getTheRate( rs.getLong("NBANKINTERESTID"), 
                    //                    rs.getLong("NCONTRACTID")))); //利率  
                    resultInfo.setInterestRate(
                        Double.parseDouble(
                            getTheBasicRate(rs.getLong("nbankInterestId"))));        
                }
                else
                {
                    //Log.print("2"); 
                    resultInfo.setInterestRate(rs.getDouble("minterestadjust"));
                    //resultInfo.setInterestAdjust(rs.getDouble("minterestadjust"));
                }
                Log.print("基准利率"+resultInfo.getInterestRate());
                //Log.print("调整利率"+resultInfo.getInterestAdjust());
                //================end ninghao=============//
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
			//查询基准利率，调整利率
			/*/根据NBANKINTERESTID，查找基准利率
			if (resultInfo != null)
			{
				InterestRateInfo interestRateInfo = findInterestRateByID(resultInfo.getBankInterestID());
				if (interestRateInfo != null)
				{
					resultInfo.setInterestRate(interestRateInfo.getInterestRate());
					Log.print("==============基准利率=====：" + interestRateInfo.getInterestRate());
				}
			}
			///根据合同id，查找浮动利率
			if (resultInfo != null)
			{
				ContractDao contractDao = new ContractDao();
				ContractInfo contractInfo = contractDao.findByID(resultInfo.getContractID());
				if (contractInfo != null)
				{
					resultInfo.setInterestAdjust(contractInfo.getAdjustRate());
				}
			}
			*/
			//查询表ob_extenddetail
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_extenddetail ");
			sbSQL.append(" where NEXTENDAPPLYID=? ");
			log4j.info("=========sbSQL start operate table:ob_extenddetail===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_extenddetail===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Log.print("====result not null=========");
				extendDetailInfo = new ExtendDetailInfo();
				extendDetailInfo.setID(rs.getLong("ID"));
				extendDetailInfo.setPlanID(rs.getLong("NPLANID"));
				extendDetailInfo.setPlanBalance(rs.getDouble("MPLANBALANCE"));
				extendDetailInfo.setExtendAmount(rs.getDouble("MEXTENDAMOUNT"));
				extendDetailInfo.setExtendBeginDate(rs.getTimestamp("DTEXTENDBEGINDATE"));
				extendDetailInfo.setExtendEndDate(rs.getTimestamp("DTEXTENDENDDATE"));
				extendDetailInfo.setExtendIntervalNum(rs.getLong("NEXTENDINTERVALNUM"));
				extendDetailInfo.setExtendApplyID(rs.getLong("NEXTENDAPPLYID"));
				vctExtendDetailInfo.add(extendDetailInfo);
			}
			if (resultInfo != null && vctExtendDetailInfo != null && vctExtendDetailInfo.size() > 0)
			{
				resultInfo.setExtendDetail(vctExtendDetailInfo);
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
			//查询表ob_contractplan
			//查询合同下的最新计划
			long lMaxPlanVersion = -1;
			log4j.info("=========find the max(NPLANVERSION) MaxVersion ob_contractplan start=========\n");
			sbSQL = new StringBuffer();
			sbSQL.append(" select Max(NPLANVERSION) MaxVersion from ob_contractplan ");
			sbSQL.append(" where NCONTRACTID=?  ");
			log4j.info("=========SQL====");
			log4j.info(sbSQL.toString());
			log4j.info("=========SQL====");
			ps = conn.prepareStatement(sbSQL.toString());
			long lContractID = -1;
			if (resultInfo != null)
			{
				lContractID = resultInfo.getContractID();
				Log.print("合同号：" + lContractID);
			}
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxPlanVersion = rs.getLong("MaxVersion");
				Log.print("最大版本号：" + lMaxPlanVersion);
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
			log4j.info("=========find the max(NPLANVERSION) ob_contractplan end=========\n");
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_contractplan ");
			sbSQL.append(" where NCONTRACTID=? and NPLANVERSION=? ");
			log4j.info("=========sbSQL start operate table:ob_contractplan===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_contractplan===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lMaxPlanVersion);
			rs = ps.executeQuery();
			if (rs.next())
			{
				contractPlanInfo = new ContractPlanInfo();
				contractPlanInfo.setID(rs.getLong("ID"));
				contractPlanInfo.setLoanID(rs.getLong("NLOANID"));
				contractPlanInfo.setPlanVersion(rs.getLong("NPLANVERSION"));
				contractPlanInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				contractPlanInfo.setInput(rs.getTimestamp("DTINPUT"));
				contractPlanInfo.setStatusID(rs.getLong("NSTATUSID"));
				contractPlanInfo.setIsUsed(rs.getLong("NISUSED"));
				contractPlanInfo.setUserType(rs.getLong("NUSERTYPE"));
				contractPlanInfo.setIsCanAccept(rs.getLong("ISCANACCEPT"));
				contractPlanInfo.setInID(rs.getLong("NINID"));
				contractPlanInfo.setInPlanVersion(rs.getLong("NINPLANVERSION"));
				contractPlanInfo.setHandleUserID(rs.getLong("NHANDLEUSERID"));
				contractPlanInfo.setContractID(rs.getLong("NCONTRACTID"));
			}
			if (resultInfo != null && contractPlanInfo != null)
			{
				resultInfo.setContractPlanInfo(contractPlanInfo);
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
			//查询表ob_contractplandetail
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_contractplandetail ");
			sbSQL.append(" where NPLANID=? ");
			log4j.info("=========sbSQL start operate table:ob_contractplandetail===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_contractplandetail===========");
			ps = conn.prepareStatement(sbSQL.toString());
			long lPlanID = -1;
			if (contractPlanInfo != null)
			{
				lPlanID = contractPlanInfo.getID();
				Log.print("计划id：" + lPlanID);
			}
			ps.setLong(1, lPlanID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				contractPlanDetailInfo = new ContractPlanDetailInfo();
				contractPlanDetailInfo.setID(rs.getLong("ID"));
				contractPlanDetailInfo.setPlanID(rs.getLong("NPLANID"));
				contractPlanDetailInfo.setPlanDate(rs.getTimestamp("DTPLANDATE"));
				contractPlanDetailInfo.setPayTypeID(rs.getLong("NPAYTYPEID"));
				contractPlanDetailInfo.setAmount(rs.getDouble("MAMOUNT"));
				contractPlanDetailInfo.setType(rs.getString("STYPE"));
				contractPlanDetailInfo.setModifyDate(rs.getTimestamp("DTMODIFYDATE"));
				contractPlanDetailInfo.setLastExtendID(rs.getLong("NLASTEXTENDID"));
				contractPlanDetailInfo.setLastOverDueID(rs.getLong("NLASTOVERDUEID"));
				contractPlanDetailInfo.setLastVersionPlanID(rs.getLong("NLASTVERSIONPLANID"));
				vctContractPlanDetailInfo.add(contractPlanDetailInfo);
			}
			if (resultInfo != null && vctContractPlanDetailInfo != null && vctContractPlanDetailInfo.size() > 0)
			{
				resultInfo.setContractPlanDetail(vctContractPlanDetailInfo);
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
			log4j.info("\n=======findByID end======");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
		return resultInfo;
	}
	/**
	 * Method updateOB.
	 * 修改一条展期申请指令的信息
	   操作表：ob_extend
	   返回：1成功，0失败
	 * @param LID
	 * @param lStatusID
	 * @return long
	 * @throws Exception
	 */
	public long updateOB(OBBackInfo info) throws Exception
	{
		long lRtnResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		int nIndex = 1;
		try
		{
			log4j.info("\n=======updateOB start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_extend set ");
			if (info.getInID() > 0)
			{
				sbSQL.append(" NINID=?, ");
			}
			sbSQL.append(" NSTATUSID=?, ");
			if (info.getUserID() > 0)
			{
				sbSQL.append(" NHANDLEUSERID=?, ");
			}
			sbSQL.append(" NINPUTUSERID=NINPUTUSERID ");
			sbSQL.append(" where ID=? ");
			log4j.info("=========sbSQL start operate table:ob_extend===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_extend===========");
			ps = conn.prepareStatement(sbSQL.toString());
			if (info.getInID() > 0)
			{
				ps.setLong(nIndex++, info.getInID());
			}
			ps.setLong(nIndex++, info.getStatusID());
			if (info.getUserID() > 0)
			{
				ps.setLong(nIndex++, info.getUserID());
			}
			ps.setLong(nIndex++, info.getID());
			if (ps.executeUpdate() > 0)
			{
				lRtnResult = 1;
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
			log4j.info("\n=======updateOB end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
		return lRtnResult;
	}
	/**
	 * Method updateOBStatus.
	 * 根据指令在内部系统的ID修改一条展期申请指令的状态
	   操作表：ob_extend
	   返回：1成功，0失败
	 * @param LID
	 * @param lStatusID
	 * @return long
	 * @throws Exception
	 */
	public long updateOBStatus(long lID, long lStatusID) throws Exception
	{
		long lRtnResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			log4j.info("\n=======updateOBStatus start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_extend set ");
			sbSQL.append(" NSTATUSID=? ");
			sbSQL.append(" where NINID=? ");
			log4j.info("=========sbSQL start operate table:ob_extend===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_extend===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lID);
			if (ps.executeUpdate() > 0)
			{
				lRtnResult = 1;
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
			log4j.info("\n=======updateOBStatus end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
		return lRtnResult;
	}
	/**
	 * Method updateOBContractPlanStatus.
	 * 在进行展期申请网银指令接受的时候对网银合同计划表中的ISUSED字段进行回填,
	 * 置为LOANConstant.PlanModifyType.EXTEND
	 * 操作表：ob_contractplan
	   返回：1成功，0失败
	 * @param lContractPlanID
	 * @param lStatusID
	 * @return long
	 * @throws Exception
	 */
	public long updateOBContractPlanStatus(long lContractPlanID, long lStatusID ,long lIsUsed) throws Exception
	{
		long lRtnResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			log4j.info("\n=======updateOBContractPlanStatus start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_contractplan set ");
			sbSQL.append(" NSTATUSID=? ,NISUSED=? ");
			sbSQL.append(" where ID=? ");
			log4j.info("=========sbSQL start operate table:ob_contractplan===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_contractplan===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lIsUsed);
			ps.setLong(3, lContractPlanID);
			if (ps.executeUpdate() > 0)
			{
				lRtnResult = 1;
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
			log4j.info("\n=======updateOBContractPlanStatus end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
		return lRtnResult;
	}
	/**
	 * Method findOBExtendByInID.
	 * 根据接受之后的内部系统展期指令id查找网银展期指令信息
	 * @param lInID
	 * @return long
	 * @throws Exception
	 */
	public OBExtendInfo findOBExtendByInID(long lInID) throws Exception
	{
		OBExtendInfo resultInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lID = -1; //网银展期申请id
		try
		{
			log4j.info("\n=======findOBExtendIDByInID start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select id from ob_extend where ");
			sbSQL.append(" ninid=? ");
			log4j.info("=========sbSQL start select  table:ob_extend===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end select table:ob_extend===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lID = rs.getLong("ID");
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
			if (lID > 0)
			{
				log4j.info("\n=======find a record====");
				resultInfo = findByID(lID);
			}
			log4j.info("\n=======findOBExtendIDByInID end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
		return resultInfo;
	}
	/**findInterestRate  银行利率设置查找
	 * 根据银行利率标示查询银行利率设置 操作InterestRateHistory数据表 查询相应记录
	 * @param lID 银行利率标示
	 * @return InterestRateInfo 银行利率信息
	 * @throws RemoteException
	*/
	public InterestRateInfo findInterestRateByID(long lID) throws java.rmi.RemoteException
	{
		//可能还会需要别的信息
		long lOfficeID = 0;
		long lInputUserID = 0;
		long lUpdateUserID = 0;
		InterestRateInfo ii = new InterestRateInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,b.id as parentid ");
			sb.append(" FROM loan_InterestRate a,loan_INTERESTRATETYPEINFO b ");
			sb.append(" WHERE a.id=? and b.ID=a.NBANKINTERESTTYPEID ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				ii.setID(rs.getLong("ID"));
				ii.setBankInterestTypeID(rs.getLong("nBankInterestTypeID"));
				ii.setInterestRate(rs.getDouble("mRate"));
				ii.setValiDate(rs.getTimestamp("dtValidate"));
				ii.setInputUserID(rs.getLong("nInputUserID"));
				ii.setInputDate(rs.getTimestamp("dtInput"));
				ii.setInterestRateCode(rs.getString("SINTERESTRATENO"));
				ii.setInterestRateName(rs.getString("SINTERESTRATENAME"));
				ii.setCheckUserID(rs.getLong("nModifyUserID"));
				ii.setCheckDate(rs.getTimestamp("dtModify"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			sb.append(" SELECT *  ");
			sb.append(" FROM userinfo ");
			sb.append(" WHERE id=? ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, ii.getCheckUserID());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				ii.setCheckUserName(rs.getString("sName"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			sb.append(" SELECT sName ");
			sb.append(" FROM userinfo ");
			sb.append(" WHERE id=? ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, ii.getInputUserID());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				ii.setInputUserName(rs.getString("sName"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return ii;
	}
    
    //获得更新后的基准利率
    private String getTheBasicRate( long lBankRateTypeID) throws RemoteException
    {
        PreparedStatement ps = null ;
        ResultSet rs = null ;
        Connection conn = null ;
        StringBuffer sb = new StringBuffer() ;
        String sInterestRate = "" ; // for Libor  因为Libor没有值
        //查找银行利率
        long lRateType = LOANConstant.InterestRateType.BANK ; // bank rate
        double dInterestRate = 0 ;

        try
        {
            conn = Database.getConnection() ;

            sb.append(
                  "select mrate from loan_interestRate where ID = "
                  + lBankRateTypeID ) ;
            ps = conn.prepareStatement( sb.toString() ) ;
            rs = ps.executeQuery() ;
            if ( rs.next() )
            {
                dInterestRate = rs.getDouble( 1 ) ;
            }
            rs.close() ;
            rs = null ;
            ps.close() ;
            ps = null ;
            sb.setLength( 0 ) ;

            sInterestRate = String.valueOf( DataFormat.formatDouble(dInterestRate  , 6 ) ) ;

        }
        catch ( Exception e )
        {
            Log.print( e.toString() ) ;
            throw new RemoteException( "remote exception : " + e.toString() ) ;
        }
        finally
        {
            try
            {
                if ( rs != null )
                {
                    rs.close() ;
                    rs = null ;
                }
                if ( ps != null )
                {
                    ps.close() ;
                    ps = null ;
                }
                if ( conn != null )
                {
                    conn.close() ;
                    conn = null ;
                }
            }
            catch ( Exception e )
            {
                throw new RemoteException( "remote exception : " + e.toString() ) ;
            }
        }
        return sInterestRate ;

    }


    private String getTheRate( long lBankRateTypeID ,long lContractID ) throws RemoteException
    {
        PreparedStatement ps = null ;
        ResultSet rs = null ;
        Connection conn = null ;
        StringBuffer sb = new StringBuffer() ;
        String sInterestRate = "" ; // for Libor  因为Libor没有值
        //查找银行利率
        long lRateType = LOANConstant.InterestRateType.BANK ; // bank rate
        double dInterestRate = 0 ;

        try
        {
            conn = Database.getConnection() ;

            sb.append(
                  "select mrate from loan_interestRate where ID = "
                  + lBankRateTypeID ) ;
            ps = conn.prepareStatement( sb.toString() ) ;
            rs = ps.executeQuery() ;
            if ( rs.next() )
            {
                dInterestRate = rs.getDouble( 1 ) ;
            }
            rs.close() ;
            rs = null ;
            ps.close() ;
            ps = null ;
            sb.setLength( 0 ) ;

            String strSQLInterestRate =
                  "select nvl(mAdjustRate,0) from loan_contractform "
                  + " where ID = " + lContractID ;
            ps = conn.prepareStatement( strSQLInterestRate ) ;
            rs = ps.executeQuery() ;
            if ( rs.next() )
            {

                sInterestRate = String.valueOf( DataFormat.formatDouble(
                      dInterestRate *(1+ rs.getDouble( 1 )/100) , 6  )) ;
            }
            rs.close() ;
            rs = null ;
            ps.close() ;
            ps = null ;

        }
        catch ( Exception e )
        {
            Log.print( e.toString() ) ;
            throw new RemoteException( "remote exception : " + e.toString() ) ;
        }
        finally
        {
            try
            {
                if ( rs != null )
                {
                    rs.close() ;
                    rs = null ;
                }
                if ( ps != null )
                {
                    ps.close() ;
                    ps = null ;
                }
                if ( conn != null )
                {
                    conn.close() ;
                    conn = null ;
                }
            }
            catch ( Exception e )
            {
                throw new RemoteException( "remote exception : " + e.toString() ) ;
            }
        }
        return sInterestRate ;

    }



    
	public static void main(String args[])
	{
		try
		{
			OBExtendDao obExtendDao = new OBExtendDao();
			//OBExtendInfo obExtendInfo = null;
			//obExtendInfo = obExtendDao.findOBExtendByInID(44);
		    obExtendDao.updateOBContractPlanStatus(28,3,1);
			/*OBExtendInfo obExtendInfo = null;
			obExtendInfo = obExtendDao.findByID(1);
			if (obExtendInfo != null)
			{
				Log.print("=====not null =========");
				Log.print("strExtendReason：" + obExtendInfo.getExtendReason());
				Log.print("contractPlanInfo：" + obExtendInfo.getContractPlanInfo());
				Log.print("vctExtendDetail size：" + obExtendInfo.getExtendDetail().size());
				
				Log.print("vctContractPlanDetail size：" + obExtendInfo.getContractPlanDetail().size());
				ContractPlanDetailInfo contractPlanDetailInfo = (ContractPlanDetailInfo) (obExtendInfo.getContractPlanDetail().elementAt(0));
				if (contractPlanDetailInfo != null)
				{
					Log.print("detail info:"+contractPlanDetailInfo.getAmount());
				} 
				
			}
			*/
			/*OBBackInfo obBackInfo = new OBBackInfo();
			obBackInfo.setID(1);
			obBackInfo.setInID(1);
			obBackInfo.setUserID(3);
			if (obExtendDao.updateOB(obBackInfo) > 0)
			{
				Log.print("success!");
			}
			*/
			/*if(obExtendDao.updateOBStatus(2,1) > 0)
			{
				Log.print("success!");
			}
			*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}