/*
 * Created on 2004-1-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obextendapply.dao;

import java.util.*;
import com.iss.itreasury.ebank.obrepayplan.dataentity.*;
import com.iss.itreasury.ebank.obextendapply.dataentity.*;
import com.iss.itreasury.ebank.obrepayplan.dao.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.util.PagedStatement;
import com.iss.itreasury.loan.contract.dataentity.*; 
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.obrepayplan.dataentity.OBRepayPlanInfo;
import com.iss.itreasury.settlement.util.SETTConstant ;
import java.sql.*;
import java.rmi.RemoteException ;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBExtendApplyDao
{
    private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this);
	/**
	 * 展期申请合同查找
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param OBQueryContractInfo  查询条件
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findContractByMultiOption(OBQueryContractInfo qinfo) throws Exception
	{
		ArrayList alist = new ArrayList() ;
		Connection conn = null ;
		//PreparedStatement ps = null ;
		PagedStatement ps=null;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;
		String sCondition = "" ;
		OBExtendApplyInfo e_info = new OBExtendApplyInfo() ;

		long lRecordCount = -1 ;
		long lPageCount = -1 ;
		long lRowNumStart = -1 ;
		long lRowNumEnd = -1 ;

		long lIndex = 1;

		try
		{
			conn = Database.getConnection() ;
			sCondition =
				  "from loan_ContractForm aa,CLIENT cc "
				  + " where aa.NSTATUSID in ("
				  + LOANConstant.ContractStatus.ACTIVE + ","
				  + LOANConstant.ContractStatus.EXTEND
				  + ") and cc.ID = aa.NBORROWCLIENTID " ; 
			//新增限制合同条件
			System.out.println("herehere======"+qinfo.getClientID());
			if ( qinfo.getClientID() > 0)
			{
				sCondition += " and aa.NBORROWCLIENTID= " + qinfo.getClientID();
			}
			if ( qinfo.getUserID() > 0 )
			{
				//sCondition += " and aa.NINPUTUSERID = " + qinfo.getUserID() ;
			}
			if ( qinfo.getCurrencyID() > 0 )
			{
				sCondition += " and aa.NCURRENCYID = " + qinfo.getCurrencyID() ;
			}
			if ( qinfo.getTypeID() > 0 )
			{
				sCondition += " and aa.NTYPEID = " + qinfo.getTypeID() ;
			}
			if ( qinfo.getContractIDFrom() > 0 )
			{
				sCondition += " and aa.ID >= " + qinfo.getContractIDFrom() ;
			}
			if ( qinfo.getContractIDTo() > 0 )
			{
				sCondition += " and aa.ID <= " + qinfo.getContractIDTo() ;
			}
			if ( qinfo.getClientID() > 0 )
			{
				//sCondition += " and aa.NBORROWCLIENTID = " + qinfo.getClientID() ;
			}
			if ( qinfo.getOfficeID() > 0 )
			{
				sCondition += " and aa.NOFFICEID = " + qinfo.getOfficeID() ;
			}
			switch ( ( int ) qinfo.getOrderParam() )
			{
				case 1:
					sCondition += " order by SCONTRACTCODE" ;
					break ;
				case 2:
					sCondition += " order by SNAME" ;
					break ;
				case 3:
					sCondition += " order by aa.MEXAMINEAMOUNT" ;
					break ;
				case 4:
					sCondition += " order by aa.DTSTARTDATE" ;
					break ;
				case 5:
					sCondition += " order by aa.NINTERVALNUM" ;
					break ;
				case 6:
					sCondition += " order by ISEXTEND" ;
					break ;
				default:
					sCondition += " order by SCONTRACTCODE" ;
			}
			if(qinfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				sCondition += " desc ";
			}
			else
			{
				sCondition += " asc ";
			}
//因为被封装,所以下面代码去掉!
			/*sb.append( "select count(*)" + sCondition ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			// ...
			rs = ps.executeQuery() ;
			while ( rs.next() )
			{
				lRecordCount = rs.getLong( 1 ) ;
				log4j.info( "RecordCount: " + lRecordCount ) ;
			}

			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			lPageCount = lRecordCount / qinfo.getPageLineCount() ;
			if ( lRecordCount % qinfo.getPageLineCount() != 0 )
			{
				lPageCount++ ;
			}
			lRowNumStart = ( qinfo.getPageNo() - 1 ) * qinfo.getPageLineCount() + 1 ;
			lRowNumEnd = lRowNumStart + qinfo.getPageLineCount() - 1 ;*/
			sb.append( "select aa.*,cc.SNAME " +sCondition ) ;
			System.out.println( sb.toString() ) ;
			//初始翻页类
			ps = new PagedStatement(conn,sb.toString(), qinfo.getPageNo(), qinfo.getPageLineCount());
			//ps = new PagedStatement(sb.toString(), qinfo.getPageNo(), qinfo.getPageLineCount());
			lIndex = 1;
			//ps = conn.prepareStatement( sb.toString() ) ;
			// ...
			rs = ps.executeQuery() ;
			while ( rs.next() )
			{
				e_info = new OBExtendApplyInfo() ;
				e_info.setContractID(rs.getLong( "ID" )) ;
				e_info.setStatusID(getExtendStatus( e_info.getContractID() )) ; //如果合同有展期并状态为提交则值为2
				e_info.setContractCode(rs.getString( "SCONTRACTCODE" )) ;
				e_info.setClientName(rs.getString( "SNAME" )) ;
				e_info.setLoanAmount(rs.getDouble( "MEXAMINEAMOUNT" )) ;
				e_info.setLoanDate(rs.getTimestamp( "DTSTARTDATE" )) ;
				e_info.setLoanIntervalNum(rs.getLong( "NINTERVALNUM" )) ;
				e_info.setIsExtend(rs.getLong( "ISEXTEND" )) ;
				e_info.setPageCount(rs.getLong("ResultSize")); //记录总数
				System.out.println("the result ==================================="+e_info.getPageCount());
				alist.add( e_info ) ;
			}

		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return ( alist.size() > 0 ? alist : null ) ;

	}

	/**
	 * 查找合同的最新版本还款计划。不翻页
	 * 最新的计划版本，
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID 合同标识
	 * @return Collection  (if 合同计划正在修改返回null)
	 * @exception RemoteException
	 */
	public Collection findPlanByContract( long lID ,OBSecurityInfo sInfo) throws Exception
	{
		long lLoanID = -1 ;
		long lOfficeID = -1 ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;
		ArrayList alist = new ArrayList() ;
		String strCondition = "" ;
		long lRecordCount = -1 ; // 这里用做计数器
		long lhasMAX = -1 ;
		double dPlanBalance = 0 ;

		long lstatusid = -1 , nisused = -1 ;

		try
		{
			conn = Database.getConnection() ;
			//查最新计划是否可以做展期
			sb.append(
				  "select NSTATUSID,NISUSED,NUSERTYPE from loan_LoanContractPlan where NCONTRACTID = ? and NPLANVERSION in (select max(NPLANVERSION) from loan_LoanContractPlan where NCONTRACTID = ?)" ) ;
			//sb.append("select NSTATUSID,NISUSED,NUSERTYPE from loan_LoanContractPlan where NCONTRACTID = ? and NPLANVERSION is null");
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			ps.setLong( 1 , lID ) ;
			ps.setLong( 2 , lID ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				lstatusid = rs.getLong( "NSTATUSID" ) ;
				nisused = rs.getLong( "NISUSED" ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			log4j.info( "ejb----------------lstatusid/nisused: " + lstatusid
				  + "/" + nisused ) ;
			if ( lstatusid == 1 && nisused != Constant.YesOrNo.YES )
			{
				sb.append(
					  "select max(NPLANVERSION) from loan_loancontractplan where nContractID = ?" ) ;
				Log.print( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , lID ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lhasMAX = rs.getLong( 1 ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
				System.out.println( "------------------lhasMAX:" + lhasMAX ) ;

				if ( lhasMAX > 0 )
				{
					strCondition =
						  " from  loan_loancontractplanDetail aa,"
						  +
						  " loan_loancontractplan bb "
						  + " where bb.nContractID = ? "
						  + " and aa.nPayTypeID = "
						  + LOANConstant.PlanType.REPAY
						  +
						  " and aa.nContractPlanID = bb.ID and bb.NPLANVERSION "
						  + " in(select max(NPLANVERSION) "
						  + " from loan_loancontractplan where nContractID = "
						  + lID + ")" ;
				}
				else
				{
					strCondition = " from  loan_loancontractplanDetail aa,"
						  +
						  " loan_loancontractplan bb where bb.nContractID = ? "
						  + " and aa.nPayTypeID = "
						  + LOANConstant.PlanType.REPAY
						  + " and aa.nContractPlanID = bb.ID" ;
				}

				strCondition += " order by DTPLANDATE" ;

				sb.append( "select aa.* " + strCondition ) ;
				Log.print( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , lID ) ;
				//ps.setLong(2,lLoanID);
				rs = ps.executeQuery() ;
				ContractDao contractDao = new ContractDao() ;
				long lPageCount = 0 ;
				while ( rs.next() )
				{
					OBRepayPlanInfo rp_info = new OBRepayPlanInfo() ;
					rp_info.setID(rs.getLong( "ID" )) ;
					rp_info.setPlanDate(rs.getTimestamp( "DTPLANDATE" )) ;
					rp_info.setExecuteInterestRateName(getPlanRate( lID ,rp_info.getPlanDate() )) ;
					System.out.println( "he..........."+ rp_info.getExecuteInterestRateName() ) ;
					rp_info.setExecuteInterestRate(contractDao.getLatelyRate( 0 ,lID , rp_info.getPlanDate() ).getLateRate() );
					rp_info.setLoanOrRepay(rs.getInt( "NPAYTYPEID" )) ;
					rp_info.setAmount(rs.getDouble( "MAMOUNT" )) ;
					rp_info.setType(rs.getString( "STYPE" )) ;
					rp_info.setInputDate(rs.getTimestamp( "DTMODIFYDATE" )) ;
					rp_info.setContractPayPlanVersionID(rs.getLong("nContractPlanID" )) ;
					rp_info.setCount(++lPageCount) ;
					rp_info.setLastExtendID(rs.getLong( "NLASTEXTENDID" )) ;
					rp_info.setLastOverDueID(rs.getLong( "NLASTOVERDUEID" )) ;
					rp_info.setLastVersionPlanID(rs.getLong("nLastVersionPlanID" )) ;
					rp_info.setPlanBalance(getPlanBalance( lID,rp_info.getContractPayPlanVersionID() , rp_info.getID() ));
					System.out.println( "rp_info.dPlanBalance =" + rp_info.getPlanBalance() ) ;
					alist.add( rp_info ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				conn.close() ;
				conn = null ;

			}
		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return ( alist.size() > 0 ? alist : null ) ;

	}

	/**
	 * 查找延期申请
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param     long        lID              延期申请标示
	 * @param     OBSecurityInfo        sInfo              安全信息
	 * @return    OBExtendApplyInfo
	 * @exception Exception
	 * @if lContractID>0 go the contractdao.findbyid()
	 **/
	public OBExtendApplyInfo findExtendByID( long lID,long lContractID,OBSecurityInfo sInfo ) throws Exception
	{
		System.out.println("here="+lContractID);
		OBExtendApplyInfo e_info = new OBExtendApplyInfo() ;
		OBRepayPlanInfo r_info = new OBRepayPlanInfo() ;
		OBExtendDetailInfo ec_info = new OBExtendDetailInfo() ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;
		String sql = "" ;
		ArrayList alist = new ArrayList() ;
		ArrayList alist2 = new ArrayList();
		double dTemp = 0 ;

		try
		{
			if(lID > 0)
			{
				System.out.println("hehehrhehhrehrhehrhehrhehrhehrhehrhe"+lID);
				conn = Database.getConnection();
				sb.append(
					"select aa.*,bb.SCONTRACTCODE,bb.nTypeID,bb.NLOANID"
					+ " from ob_Extend aa,loan_ContractForm bb"
					+ " where aa.ID = ? "
					+ " and aa.NCONTRACTID = bb.ID ");
				System.out.println(sb.toString());
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, lID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					e_info.setContractID(rs.getLong("NCONTRACTID")); //贷款合同标示
					e_info.setPlanVersionID(rs.getLong("NPLANVERSIONID")); //贷款ID
					e_info.setLoanID(rs.getLong("NLOANID")); //贷款ID
					e_info.setSerialNo(rs.getLong("NSERIALNO")); //展期序号
					e_info.setLoanTypeID(rs.getLong("NTYPEID"));
					e_info.setExtendReason(rs.getString("SEXTENDREASON")); //展期原因
					e_info.setReturnPostPend(rs.getString("SRETURNPOSTPEND")); //归还延期还款本息资金
					e_info.setOtherContent( (rs.getString("SOTHERCONTENT") == null)
										   ? "" : rs.getString("SOTHERCONTENT")); //其他事项
                    if(rs.getLong("NBANKINTERESTID") > 0)  
                    {           
                        e_info.setInterestRate(Double.parseDouble(getTheRate(rs.
                            getLong("NBANKINTERESTID"), rs.getLong("NCONTRACTID")))); //利率          
                    }
                    else
                    {
                        e_info.setInterestRate(rs.getDouble("minterestadjust"));
                    }
					e_info.setStatusID(rs.getLong("NSTATUSID")); //状态
					e_info.setInputUserID(rs.getLong("NINPUTUSERID")); // 展期录入人标识
					e_info.setInputUserName(getInputUserName(e_info.
						getInputUserID()));
					e_info.setInputDate(rs.getTimestamp("DTINPUTDATE")); // 录入日期
					e_info.setContractCode(rs.getString("SCONTRACTCODE"));
					e_info.setBankRateTypeID(rs.getLong("NBANKINTERESTID")); //利率ID
					e_info.setInstructionNo(rs.getString("sInstructionNo")); //指令号
                    if(rs.getLong("nbankInterestId") > 0)
                    {
                        e_info.setBasicInterest(getTheBasicRate(rs.getLong("nbankInterestId")));
                    }
                    else
                    {
                        e_info.setBasicInterest(""+rs.getDouble("minterestadjust"));
                    }

				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);

				// 查展期明细 -> RapayPlanInfo
				sql =
					"select id,nPlanID,mPlanBalance,mExtendAmount,"
					+ " dtExtendBeginDate,dtExtendEndDate,NEXTENDINTERVALNUM,"
					+
					" DTEXTENDBEGINDATE dtPlanEndDate from ob_ExtendDetail "
					+ " where NEXTENDapplyID = " + lID;
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next())
				{
					r_info = new OBRepayPlanInfo();
					r_info.setExtendListID(rs.getLong("ID"));
					r_info.setID(rs.getLong("nPlanID"));
					r_info.setPlanBalance(rs.getDouble("MPLANBALANCE"));
					r_info.setAmount(rs.getDouble("MEXTENDAMOUNT"));
					r_info.setExtendStartDate(rs.getTimestamp(
						"DTEXTENDBEGINDATE"));
					r_info.setExtendEndDate(rs.getTimestamp("DTEXTENDENDDATE"));
					r_info.setExtendPeriod(rs.getLong("NEXTENDINTERVALNUM"));
					r_info.setPlanDate(rs.getTimestamp("dtPlanEndDate"));
					alist.add(r_info);
				}
				e_info.setExtendList(alist);
				System.out.println("beanbenbeanbenaebanbnanbananannbnenan"+e_info.getExtendList());
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				// 查展期合同 -> ExtendContractInfo
				//alist.clear();
				sql =
					"select a.*,b.ID as ContractContentID,b.SDOCNAME "
					+ " from loan_extendcontract a,loan_contractcontent b"
					+ " where a.nextendid = " + lID + " and b.nparentid = " +
					lID
					+ " and b.ncontractid =" + e_info.getContractID()
					+ " and b.ncontracttypeid = "
					+ LOANConstant.ContractType.EXTEND
					+ " and a.nstatusid > " + Constant.RecordStatus.INVALID;
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					ec_info = new OBExtendDetailInfo();
					ec_info.setExtendID(rs.getLong("ID")); //展期合同ID
					ec_info.setClientID(rs.getLong("ContractContentID")); //展期合同文本ID
					ec_info.setExtendApplyID(lID); //展期申请的ID
					ec_info.setExtendCode(rs.getString("SCODE")); //展期合同编号
					ec_info.setStatusID(rs.getLong("NSTATUSID")); //展期合同状态
					ec_info.setInputUserID(rs.getLong("NINPUTUSERID")); //录入人姓名
					ec_info.setInputUserName(getInputUserName(ec_info.
						getInputUserID())); //录入人姓名
					ec_info.setCheckUserID(rs.getLong("NNEXTCHECKUSERID")); ////复核人姓名
					ec_info.setCheckUserName(getInputUserName(ec_info.
						getCheckUserID())); //复核人姓名
					ec_info.setExtendStart(rs.getTimestamp("DTINPUTDATE")); //录入时间
					ec_info.setDocName(rs.getString("SDOCNAME"));
					alist2.add(ec_info);
				}
				e_info.setExtendContractList(alist2);
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				conn.close();
				conn = null;
			}
			if(lContractID >0)
			{
			    ContractDao dao = new ContractDao();
			    System.out.println("here="+lContractID);
			    e_info.setC_Info(dao.findByID(lContractID));
			    System.out.println("here="+lContractID);
			}

		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		System.out.println("beanbenbeanbenaebanbnanbananannbnenan"+e_info.getExtendList());
		System.out.println("beanbenbeanbenaebanbnanbananannbnenan"+e_info);
		return e_info ;

	}


	/**
	 * 新增/修改展期申请
	 * @Copyright (c) Jan. 2004, by iSoftStone Inc. All Rights Reserved
	 * @param     OBExtendApplyInfo       appInfo   展期申请信息
	 * @return    long     操作成功，返回值 == 1，失败，返回值 == 0。
	 * @exception Exception
	 **/
	public long saveExtendApply( OBExtendApplyInfo appInfo)  throws Exception
	{
		long lResult = -1 ;
		long lStatusID = -1 ;
		long lMaxID = -1 ;
		long lMAXEXID = -1 ;
		long lPlanID = -1 ;
		long lserialID = -1 ;
		long lCounter = 0 ;
		long lNewVer = -1 ;
		long lLastVerID = -1 ;
		double dBalance = 0 ;
		double dAmountTotal = 0 ;
		double dOldPlanAmount = 0 ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;
		String sql = "" ;
		Timestamp tsStartDate = null ;
		Timestamp tsEndDate = null ;
		OBRepayPlanInfo rp_info = new OBRepayPlanInfo() ;
		ArrayList alist = new ArrayList() ;
		ArrayList alist1 = new ArrayList() ;
		ArrayList alist2 = new ArrayList() ;

		try
		{
			conn = Database.getConnection() ;
			long lLoanID = 0 ;

			// 新增
			if ( appInfo.getID() == 0 )
			{

				System.out.println(
					  "-----------------------EJB_save:" + appInfo.getID() + ","
					  + appInfo.getContractID() + "," + appInfo.getPlanVersionID() + ","
					  + appInfo.getExtendList() + "," + appInfo.getExtendTypeID() + ","
					  + appInfo.getInterestTypeID() + "," + appInfo.getExtendRate() + ","
					  + appInfo.getLiborRateID() + "," + appInfo.getExtendReason() + ","
					  + appInfo.getReturnPostPend() + "," + appInfo.getOtherContent() + ","
					  + appInfo.getUserID() ) ;

				if ( appInfo.getBankRateTypeID() <= 0 )
				{
					appInfo.setBankRateTypeID(getInterestRateID( appInfo.getContractID() , null )) ;

				}

				// 得到最大ID（contractpayplanversion)Loan
				sb.append(
					  "select nvl(max(ID)+1,1) from ob_ContractPlan" ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lMaxID = rs.getLong( 1 ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
				// 新增版本取值
				sb.append( "select nvl(max(NPLANVERSION)+1,1) from loan_loanContractPlan where NCONTRACTID = ?" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , appInfo.getContractID() ) ;

				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lNewVer = rs.getLong( 1 ) ;
				}
				System.out.println( "ejb-------------.lNewVer: " + lNewVer ) ;
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
				// 展期版本来原(最大旧版本对应的ID)
				sb.append(
					  "select ID from LOAN_LOANCONTRACTPLAN where NCONTRACTID = ? and NPLANVERSION in (select max(NPLANVERSION) from loan_LoanContractPlan where NCONTRACTID = ?)" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , appInfo.getContractID() ) ;
				ps.setLong( 2 , appInfo.getContractID() ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lLastVerID = rs.getLong( 1 ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 得到最大ID   loan_ExtendForm
				sb.append( "select nvl(max(ID)+1,1) from ob_extend" ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lMAXEXID = rs.getLong( 1 ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
				System.out.println( "ejb-----------lMaxID(version.ID): "
					  + lMaxID ) ;
				System.out.println( "ejb-----------lMAXEXID(extapp.ID): "
					  + lMAXEXID ) ;

				// 取nserialID值，
				sb.append( "select nvl(max(NSERIALNO)+1,1) from ob_extend where NCONTRACTID = ? and NSTATUSID > ?" ) ;
				log4j.info( "LoanExamineEJB: " + sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , appInfo.getContractID() ) ;
				ps.setLong( 2 , Constant.RecordStatus.INVALID ) ;
				rs = ps.executeQuery() ;

				while ( rs.next() )
				{
					if ( ( lserialID = rs.getLong( 1 ) ) != 1 )
					{
						System.out.println( "合同已做过展期了" ) ;
					}
					else
					{
						System.out.println( "合同第一次做展期" ) ;
					}
					System.out.println( "ejb-----------lserialID: " + lserialID ) ;
				}

				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 取得申请书编号
				sb.append(
					  "select distinct nLoanID from loan_ContractForm  where id = ?" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				//ps.setLong(1,Notes.CODE_PAYREPAY_TYPE_REPAY);
				ps.setLong( 1 , appInfo.getContractID() ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lLoanID = rs.getLong( "nLoanID" ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 新增一条版本
				sb.append(
					  "insert into ob_ContractPlan (ID, NLOANID, NCONTRACTID, NSTATUSID,NISUSED,NUSERTYPE,NPLANVERSION) values (?,?,?,?,?,?,?)" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , lMaxID ) ;
				ps.setLong( 2 , lLoanID ) ;
				ps.setLong( 3 , appInfo.getContractID() ) ;
				//ps.setLong( 4 , lUserID ) ;
				ps.setLong( 4 , Constant.RecordStatus.INVALID ) ;
				ps.setLong( 5 , Constant.YesOrNo.YES ) ;
				ps.setLong( 6 , LOANConstant.PlanModifyType.EXTEND ) ;
				ps.setLong( 7 , lNewVer ) ;
				if ( ( lResult = ps.executeUpdate() ) < 1 )
				{
					System.out.println( "error.update.CONTRACTPAYPLANVERSION" ) ;
				}
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 复制明细
				sb.append("select * from loan_loancontractplanDetail where NContractPLANID in (select ID from loan_loanContractPlan where NPLANVERSION = ? and NCONTRACTID = ?) order by ID" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				//ps.setLong(1,Notes.CODE_PAYREPAY_TYPE_REPAY);
				ps.setLong( 1 , lNewVer - 1 ) ;
				ps.setLong( 2 , appInfo.getContractID() ) ;
				System.out.println("@@@@@@@@@@@@@@@@lNEWVER=="+lNewVer);
				System.out.println("@@@@@@@@@@@@@appInfo.getContractID()=="+appInfo.getContractID());
				rs = ps.executeQuery() ;
				while ( rs.next() )
				{
					rp_info = new OBRepayPlanInfo() ;
					rp_info.setID(rs.getLong( "ID" )) ;
					rp_info.setPlanDate(rs.getTimestamp( "DTPLANDATE" )) ;
					//rp_info.fExecuteInterestRate = rs.getDouble(
					//      "FEXECUTEINTERESTRATE" ) ;
					rp_info.setLoanOrRepay(rs.getInt( "NPAYTYPEID" )) ;
					rp_info.setAmount(rs.getDouble( "MAMOUNT" )) ;
					rp_info.setType(rs.getString( "STYPE" )) ;
					rp_info.setLastExtendID(rs.getLong( "NLASTEXTENDID" )) ;
					rp_info.setLastOverDueID(rs.getLong( "NLASTOVERDUEID" )) ;
					//rp_info.lisOverDue = rs.getLong( "NISOVERDUE" ) ;
					//4.10加
					rp_info.setInputDate(rs.getTimestamp( "DTMODIFYDATE" )) ;
					System.out.println("in111111");
					alist.add( rp_info ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
System.out.println("hehrhehrhehrehrhehre"+alist.size());
System.out.println("hehrhehrhehrehrhehre"+alist);
				// Copy
				if ( alist.size() != 0 )
				{
					System.out.println("in");
					sb.append(
						  "insert into ob_contractplanDetail (ID, NPLANID, DTPLANDATE, NPAYTYPEID, MAMOUNT, STYPE, DTMODIFYDATE, NLASTEXTENDID, NLASTOVERDUEID,NLASTVERSIONPLANID) values (nvl((select max(id) from ob_contractplanDetail),0)+1,?,?,?,?,?,?,?,?,?)" ) ;
					log4j.info( sb.toString() ) ;
					Iterator iter = alist.iterator() ;
					while ( iter.hasNext() )
					{
						rp_info = ( OBRepayPlanInfo ) iter.next() ;
						ps = conn.prepareStatement( sb.toString() ) ;
						ps.setLong( 1 , lMaxID ) ;
						ps.setTimestamp( 2 , rp_info.getPlanDate() ) ;
						//ps.setDouble( 3 , rp_info.fExecuteInterestRate ) ;
						ps.setLong( 3 , ( long ) rp_info.getLoanOrRepay() ) ;
						ps.setDouble( 4 , rp_info.getAmount() ) ;
						ps.setString( 5 , rp_info.getType() ) ;
						//4.10加
						System.out.println( "1111-----------------="
							  + rp_info.getInputDate() ) ;
						ps.setTimestamp( 6 , rp_info.getInputDate() ) ;

						ps.setLong( 7 , rp_info.getLastExtendID() ) ;
						ps.setLong( 8 , rp_info.getLastOverDueID() ) ;
						//ps.setLong( 10 , rp_info.lisOverDue ) ;
						ps.setLong( 9 , rp_info.getID() ) ;
						if ( ps.executeUpdate() < 1 )
						{
							System.out.println(
								  "error.copy.ob_contractplanDetail" ) ;
						}
						log4j.info( "success.copy.ob_contractplanDetail" ) ;
						ps.close() ;
						ps = null ;
					}
					sb.setLength( 0 ) ;
				} // copy.end
				//sinstructionID
				long lModuleID = OBConstant.SubModuleType.LOAN;
				String strInstructionNo = OBOperation.createInstrCode(lModuleID);
				// 展期表
				sb.append(
					  "insert into ob_Extend (ID,NCONTRACTID,NPLANVERSIONID,NSERIALNO,SEXTENDREASON,SRETURNPOSTPEND,SOTHERCONTENT,MINTERESTADJUST,NSTATUSID,sinstructionno,NINPUTUSERID,NINTERESTTYPEID,DTINPUTDATE,NEXTENDTYPEID,NLASTPLANVERSIONID,NBANKINTERESTID) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)" ) ;
				System.out.println( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , lMAXEXID ) ;
				ps.setLong( 2 , appInfo.getContractID() ) ;
				ps.setLong( 3 , lMaxID ) ;
				ps.setLong( 4 , lserialID ) ;
				ps.setString( 5 , appInfo.getExtendReason() ) ;
				ps.setString( 6 , appInfo.getReturnPostPend() ) ;
				ps.setString( 7 , appInfo.getOtherContent() ) ;
				ps.setDouble( 8 , appInfo.getExtendRate() ) ;
				//网银是先增为撰写状态
				ps.setLong( 9 , LOANConstant.ExtendStatus.SAVE ) ;
				ps.setString( 10 , strInstructionNo ) ;
				ps.setLong( 11 , appInfo.getUserID() ) ;
				ps.setLong( 12 , appInfo.getInterestTypeID() ) ;
				//ps.setLong( 13 , lLiborRateID ) ;
				ps.setLong( 13 , appInfo.getExtendTypeID() ) ;
				//ps.setDouble( 15 , dAdjustValue ) ;
				ps.setLong( 14 , lLastVerID ) ;
				ps.setLong( 15 , appInfo.getBankRateTypeID() ) ;
				//ps.setString(16,strInstructionNo);
				if ( ( lResult = ps.executeUpdate() ) < 1 )
				{
					System.out.println( "error.insert.ob_Extend" ) ;
				}
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 计划明细表
				if ( appInfo.getExtendList().size() > 0 )
				{
					/*   // 取明细ID
						   sb.append("select nvl(max(ID)+1,1) from loan_loancontractplanDetail");
						   ps = conn.prepareStatement(sb.toString());
											 rs = ps.executeQuery();
											 if (rs.next()) {
							lPlanID = rs.getLong(1);
											 }
											 rs.close();	rs = null;
											 ps.close(); ps = null;
											 sb.setLength(0); */
					/*****
					 * 需求变更：展期时，计划金额不随展期金额而变化
					 *****/
					//sb.append( "update loan_loancontractplanDetail set DTPLANDATE = ?, NLASTEXTENDID = ?,dtmodifydate = sysdate,mAmount = ? where nLastVersionPlanID = ? and nContractPlanID = ?" ) ;
					sb.append( "update ob_contractplanDetail set DTPLANDATE = ?, NLASTEXTENDID = ?,dtmodifydate = sysdate,mAmount = ? where nLastVersionPlanID = ? and nPlanID = ?" ) ;
					log4j.info( sb.toString() ) ;
					//long lMAXCounter = cExtendList.size();
					Iterator iter = appInfo.getExtendList().iterator() ;
					alist1 = new ArrayList() ;
					alist2 = new ArrayList() ;

					while ( iter.hasNext() )
					{
						rp_info = ( OBRepayPlanInfo ) iter.next() ;

						//取出对应计划明细的原金额，确定是否要新增一条纪录
						sql = "select mAmount from ob_contractplanDetail where nLastVersionPlanID = ? and nPlanID = ?" ;
						ps = conn.prepareStatement( sql ) ;
						ps.setLong( 1 , rp_info.getID() ) ;
						ps.setLong( 2 , lMaxID ) ;
						System.out.println( "rp_info.lID = " + rp_info.getID() ) ;
						System.out.println( "lMaxID = " + lMaxID ) ;
						rs = ps.executeQuery() ;
						if ( rs != null && rs.next() )
						{
							dOldPlanAmount = rs.getDouble( "mAmount" ) ;
						}
						rs.close() ;
						rs = null ;
						ps.close() ;
						ps = null ;
						System.out.println( "dOldPlanAmount = "
							  + dOldPlanAmount ) ;
						System.out.println( "rp_info.dAmount = "
							  + rp_info.getAmount() ) ;

						if ( dOldPlanAmount > rp_info.getAmount() )
						{
							//如果展期金额不足计划余额，新增一条纪录
							sql = "insert into ob_contractplanDetail (ID, nPlanID, DTPLANDATE, NPAYTYPEID, STYPE,DTMODIFYDATE,MAMOUNT ) " +
								  " values (nvl((select max(id) from ob_contractplanDetail),0)+1,?,?,?,?,sysdate,?)" ;
							System.out.println( "1---" ) ;
							ps = conn.prepareStatement( sql ) ;
							ps.setLong( 1 , lMaxID ) ;
							ps.setTimestamp( 2 , rp_info.getExtendStartDate() ) ;
							//ps.setLong( 3 , rp_info.getID() ) ;
							//ps.setLong( 4 , lMaxID ) ;
							ps.setLong( 3 , LOANConstant.PlanType.REPAY ) ;
							ps.setString( 4 , "本金" ) ;
							//ps.setLong( 7 , rp_info.lisOverDue ) ;
							ps.setDouble( 5 , dOldPlanAmount - rp_info.getAmount() ) ;
							ps.executeUpdate() ;
							System.out.println(
								  "success.copy.ob_contractplanDetail" ) ;
							ps.close() ;
							ps = null ;

						}

						ps = conn.prepareStatement( sb.toString() ) ;
						ps.setTimestamp( 1 , rp_info.getExtendEndDate() ) ;
						//ps.setDouble( 2 , rp_info.fExecuteInterestRate ) ;
						ps.setLong( 2 , lMAXEXID ) ;
						ps.setDouble( 3 , rp_info.getAmount() ) ;
						ps.setLong( 4 , rp_info.getID() ) ;
						ps.setLong( 5 , lMaxID ) ;

						if ( ( lResult = ps.executeUpdate() ) < 1 )
						{
							System.out.println(
								  "error.update.ob_contractplanDetail" ) ;
						}
						System.out.println( "ejb--------------------lPlanID: "
							  + lPlanID ) ;
						ps.close() ;
						ps = null ;
						// 展期明细表
						lCounter++ ;
						sql =
							  "insert into ob_ExtendDetail (ID, NEXTENDAPPLYID, NPLANID, MPLANBALANCE, MEXTENDAMOUNT, DTEXTENDBEGINDATE, DTEXTENDENDDATE,NEXTENDINTERVALNUM) values (nvl((select max(id) from ob_ExtendDetail),0)+1,?,?,?,?,?,?,?)" ;
						System.out.println( sql ) ;
						ps = conn.prepareStatement( sql ) ;
						ps.setLong( 1 , lMAXEXID ) ;
						ps.setLong( 2 , rp_info.getID() ) ;
						//ps.setLong( 3 , lCounter ) ;

						// 收集日期
						//alist1.add( rp_info.tsExtendStartDate ) ;
						//alist2.add( rp_info.tsExtendEndDate ) ;
						/*
														 if (lCounter == 1) {
								//dBalance = rp_info.dPlanBalance;
								tsStartDate = rp_info.tsExtendStartDate;
														 }
							   if (lCounter == cExtendList.size()) {
								tsEndDate = rp_info.tsExtendEndDate;
														 }*/

						//dBalance += rp_info.dPlanBalance ;
						//dAmountTotal += rp_info.dAmount ;
						ps.setDouble( 3 , rp_info.getPlanBalance() ) ;
						ps.setDouble( 4 , rp_info.getAmount() ) ;
						ps.setTimestamp( 5 , rp_info.getExtendStartDate() ) ;
						ps.setTimestamp( 6 , rp_info.getExtendEndDate() ) ;
						ps.setLong( 7 , rp_info.getExtendPeriod() ) ;
						System.out.println( "ejb-----------------ext1" ) ;
						if ( ( lResult = ps.executeUpdate() ) < 1 )
						{
							System.out.println(
								  "error.insert.loan_ExtendDetail" ) ;
						}
						System.out.println( "ejb-----------------ext2" ) ;
						ps.close() ;
						ps = null ;

					}
					sb.setLength( 0 ) ;
				}

				/*/ 取展期日期
				tsStartDate = ( Timestamp ) Collections.min( alist1 ) ;
				tsEndDate = ( Timestamp ) Collections.max( alist2 ) ;
						  // 展期余额与总额
						  sb.append( "update loan_ExtendForm set MLOANBALANCE = ?,MEXTENDAMOUNT = ?,DTEXTENDSTART = ?,DTEXTENDEND = ? where ID = ?" ) ;
						  System.out.println( sb.toString() ) ;
						  ps = conn.prepareStatement( sb.toString() ) ;
						  ps.setDouble( 1 , dBalance ) ;
						  ps.setDouble( 2 , dAmountTotal ) ;
						  ps.setTimestamp( 3 , tsStartDate ) ;
						  ps.setTimestamp( 4 , tsEndDate ) ;
						  ps.setLong( 5 , lMAXEXID ) ;
						  lResult = ps.executeUpdate() ;
						  ps.close() ;
						  ps = null ;
						  sb.setLength( 0 ) ;
				*/
			   //if (lResult == 1) { lResult = lMAXEXID; }
			   lResult = ( lResult == 1 ) ? lMAXEXID : lResult ;

			}

			// 修改
			if ( appInfo.getID() > 0 )
			{
				sb.append( "select NSTATUSID,NPLANVERSIONID,NLASTPLANVERSIONID from ob_Extend where ID = "
					  + appInfo.getID() ) ;
				System.out.println( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					lStatusID = rs.getLong( 1 ) ;
					lMaxID = rs.getLong( 2 ) ;
					lLastVerID = rs.getLong( 3 ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;
				System.out.println( "修改展期的ID:" + appInfo.getID() + "状态：" + lStatusID ) ;

				/*/ REVIEWOPINION
					  if ( lStatusID == LOANConstant.ExtendStatus.CHECK )
						 {
							 sb.append(
					  "delete from REVIEWOPINION where NREVIEWTYPEID = 2 and NREVIEWCONTENTID = "
				  + lID ) ;
							 System.out.println( sb.toString() ) ;
							 ps = conn.prepareStatement( sb.toString() ) ;
							 if ( ( lResult = ps.executeUpdate() ) < 1 )
							 {
				System.out.println( "error.delete.REVIEWOPINION" ) ;
							 }
							 ps.close() ;
							 ps = null ;
							 sb.setLength( 0 ) ;
						 }
						 //更新网银记录
						 sb.append( " update OB_Extend set nStatusID=? where ID=(select ID from OB_Extend where nInID = ?) " ) ;
						 ps = conn.prepareStatement( sb.toString() ) ;
					  ps.setLong( 1 , OBConstant.LoanInstrStatus.ACCEPT ) ;
						 ps.setLong( 2 , lID ) ;
						 ps.executeUpdate() ;
						 ps.close() ;
						 ps = null ;
						 sb.setLength( 0 ) ;*/

			   // del 明细
				sb.append("delete from ob_contractplanDetail where NPLANID = ?" ) ;
				System.out.println( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				ps.setLong( 1 , lMaxID ) ;
				if ( ( lResult = ps.executeUpdate() ) < 1 )
				{
					System.out.println(
						  "error.delete.ob_contractplanDetail" ) ;
				}
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 复制明细
				sb.append( "select * from loan_loancontractplanDetail where NcontractPLANID = ? order by ID" ) ;
				log4j.info( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				//ps.setLong(1,Notes.CODE_PAYREPAY_TYPE_REPAY);
				ps.setLong( 1 , lLastVerID ) ;
				rs = ps.executeQuery() ;
				while ( rs.next() )
				{
					rp_info = new OBRepayPlanInfo() ;
					rp_info.setID(rs.getLong( "ID" )) ;
					rp_info.setPlanDate(rs.getTimestamp( "DTPLANDATE" )) ;
					//rp_info.fExecuteInterestRate = rs.getDouble(
					//      "FEXECUTEINTERESTRATE" ) ;
					rp_info.setLoanOrRepay(rs.getInt( "NPAYTYPEID" )) ;
					rp_info.setAmount(rs.getDouble( "MAMOUNT" )) ;
					rp_info.setType(rs.getString( "STYPE" )) ;
					rp_info.setLastExtendID(rs.getLong( "NLASTEXTENDID" )) ;
					rp_info.setLastOverDueID(rs.getLong( "NLASTOVERDUEID" )) ;
					//rp_info.lisOverDue = rs.getLong( "NISOVERDUE" ) ;
					//4.10加
					rp_info.setInputDate(rs.getTimestamp( "DTMODIFYDATE" )) ;

					alist.add( rp_info ) ;
				}
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// Copy
				if ( alist.size() != 0 )
				{
					sb.append(
						  "insert into ob_contractplanDetail (ID, NPLANID, DTPLANDATE, NPAYTYPEID, MAMOUNT, STYPE, DTMODIFYDATE, NLASTEXTENDID, NLASTOVERDUEID,NLASTVERSIONPLANID) values (nvl((select max(id) from ob_contractplanDetail),0)+1,?,?,?,?,?,?,?,?,?)" ) ;
					log4j.info( sb.toString() ) ;
					Iterator iter = alist.iterator() ;
					while ( iter.hasNext() )
					{
						rp_info = ( OBRepayPlanInfo ) iter.next() ;
						ps = conn.prepareStatement( sb.toString() ) ;
						ps.setLong( 1 , lMaxID ) ;
						ps.setTimestamp( 2 , rp_info.getPlanDate() ) ;
						//ps.setDouble( 3 , rp_info.fExecuteInterestRate ) ;
						ps.setLong( 3 , ( long ) rp_info.getLoanOrRepay() ) ;
						ps.setDouble( 4 , rp_info.getAmount() ) ;
						ps.setString( 5 , rp_info.getType() ) ;
						//4.10加
						System.out.println( "1111-----------------="
							  + rp_info.getInputDate() ) ;
						ps.setTimestamp( 6 , rp_info.getInputDate() ) ;

						ps.setLong( 7 , rp_info.getLastExtendID() ) ;
						ps.setLong( 8 , rp_info.getLastOverDueID() ) ;
						//ps.setLong( 10 , rp_info.lisOverDue ) ;
						ps.setLong( 9 , rp_info.getID() ) ;
						if ( ps.executeUpdate() < 1 )
						{
							System.out.println(
								  "error.copy.ob_contractplanDetail" ) ;
						}
						log4j.info( "success.copy.loan_contractplanDetail" ) ;
						ps.close() ;
						ps = null ;
					}
					sb.setLength( 0 ) ;
				} // copy.end

				sb.append(
					  "update ob_Extend set SEXTENDREASON = ?,SRETURNPOSTPEND = ?,SOTHERCONTENT = ?,MINTERESTADJUST = ?,NINTERESTTYPEID = ?,NBANKINTERESTID = ? where ID = ?" ) ;
				System.out.println( sb.toString() ) ;
				ps = conn.prepareStatement( sb.toString() ) ;
				//ps.setLong(1,lMAXEXID);
				//ps.setLong(2,lContractID);
				//ps.setLong(3,lMaxID);
				//ps.setLong(4,lserialID);
				ps.setString( 1 , appInfo.getExtendReason() ) ;
				ps.setString( 2 , appInfo.getReturnPostPend() ) ;
				ps.setString( 3 , appInfo.getOtherContent() ) ;
				ps.setDouble( 4 , appInfo.getExtendRate() ) ;
				//temporary do so
				//ps.setLong( 5 , LOANConstant.ExtendStatus.SAVE ) ;
				//ps.setLong( 6 , appInfo.getUserID() ) ;
				//ps.setLong(11,lUserID);
				ps.setLong( 5 , appInfo.getInterestTypeID() ) ;
				//ps.setLong( 8 , lLiborRateID ) ;
				//ps.setDouble( 9 , dAdjustValue ) ;
				ps.setLong(6 , appInfo.getBankRateTypeID() ) ;
				ps.setLong( 7 , appInfo.getID() ) ;
				//ps.setLong(14,lExtendTypeID);
				if ( ps.executeUpdate() == 1 )
				{
					lResult = appInfo.getID() ;
					System.out.println( "success.update.ob_Extend: "
						  + appInfo.getID() ) ;
				}
				else
				{
					System.out.println( "error.update.ob_Extend" ) ;
				}
				ps.close() ;
				ps = null ;
				sb.setLength( 0 ) ;

				// 计划明细表
				alist1 = new ArrayList() ;
				alist2 = new ArrayList() ;
				if ( appInfo.getExtendList().size() > 0 )
				{

					/*****
					 * 需求变更：展期时，计划金额不随展期金额而变化
					 *****/
					sb.append(
						  "update ob_contractplanDetail set DTPLANDATE = ?,"
						  + " DTMODIFYDATE = sysdate, "
						  + " NLASTEXTENDID = ?,mAmount = ? where "
						  + " nLastVersionPlanID = (select nPlanID "
						  + " from ob_ExtendDetail where id = ?) "
						  + " and NPLANID = ?" ) ;
					log4j.info( sb.toString() ) ;
					Iterator iter = appInfo.getExtendList().iterator() ;
					while ( iter.hasNext() )
					{
						rp_info = ( OBRepayPlanInfo ) iter.next() ;

						//取出对应计划明细的原金额，确定是否要新增一条纪录
						sql =
							  "select mAmount from ob_contractplanDetail "
							  + " where nLastVersionPlanID = ? "
							  + " and NPLANID = ?" ;
						ps = conn.prepareStatement( sql ) ;
						ps.setLong( 1 , rp_info.getID() ) ;
						ps.setLong( 2 , lMaxID ) ;
						rs = ps.executeQuery() ;
						System.out.println( "rp_info.lLastVersionPlanID = "
							  + rp_info.getID() ) ;
						System.out.println( "lMaxID = " + lMaxID ) ;
						if ( rs != null && rs.next() )
						{
							dOldPlanAmount = rs.getDouble( "mAmount" ) ;
						}
						rs.close() ;
						rs = null ;
						ps.close() ;
						ps = null ;

						System.out.println( "dOldPlanAmount = "
							  + dOldPlanAmount ) ;
						System.out.println( "rp_info.dAmount = "
							  + rp_info.getAmount() ) ;
						if ( dOldPlanAmount > rp_info.getAmount() )
						{
							//如果展期金额不足计划余额，新增一条纪录
							sql = "insert into ob_contractplanDetail (ID, NPLANID, DTPLANDATE, NPAYTYPEID, STYPE, DTMODIFYDATE,MAMOUNT ) " +
								  " values (nvl((select max(id) from ob_contractplanDetail),0)+1,?,?,?,?,sysdate,?)" ;
							System.out.println( sql ) ;
							ps = conn.prepareStatement( sql ) ;
							ps.setLong( 1 , lMaxID ) ;
							ps.setTimestamp( 2 , rp_info.getExtendStartDate() ) ;
							//ps.setLong( 3 , rp_info.lID ) ;
							//ps.setLong( 4 , lMaxID ) ;
							ps.setLong( 3 , LOANConstant.PlanType.REPAY ) ;
							ps.setString( 4 , "本金" ) ;
							//ps.setLong( 7 , rp_info.lisOverDue ) ;
							ps.setDouble( 5 , dOldPlanAmount - rp_info.getAmount() ) ;
							ps.executeUpdate() ;
							System.out.println(
								  "success.copy.ob_contractplanDetail" ) ;
							ps.close() ;
							ps = null ;

						}

						ps = conn.prepareStatement( sb.toString() ) ;
						ps.setTimestamp( 1 , rp_info.getExtendEndDate() ) ;
						//ps.setDouble( 2 , rp_info.fExecuteInterestRate ) ;
						ps.setLong( 2 , appInfo.getID() ) ;
						ps.setDouble( 3 , rp_info.getAmount() ) ;
						ps.setLong( 4 , rp_info.getExtendListID() ) ;
						ps.setLong( 5 , lMaxID ) ;

						if ( ( lResult = ps.executeUpdate() ) < 1 )
						{
							System.out.println( "error.update.CONTRACTPAYPLAN" ) ;
						}
						ps.close() ;
						ps = null ;
						lCounter++ ;
						// 收集终止日期
						alist2.add( rp_info.getExtendEndDate() ) ;
						//		    				if (lCounter == cExtendList.size()) {
						//		    					tsEndDate = rp_info.tsExtendEndDate;
						//		    				}

						// 展期明细表
						sql = "update ob_ExtendDetail set MEXTENDAMOUNT = ?, DTEXTENDENDDATE = ?,NEXTENDINTERVALNUM = ? where ID = ?" ;
						System.out.println( sql ) ;
						ps = conn.prepareStatement( sql ) ;
						ps.setDouble( 1 , rp_info.getAmount() ) ;
						dAmountTotal += rp_info.getAmount() ;
						ps.setTimestamp( 2 , rp_info.getExtendEndDate() ) ;
						ps.setLong( 3 , rp_info.getExtendPeriod() ) ;
						ps.setLong( 4 , rp_info.getExtendListID() ) ;
						if ( ( lResult = ps.executeUpdate() ) < 1 )
						{
							System.out.println(
								  "error.update.loan_ExtendDetail" ) ;
						}
						ps.close() ;
						ps = null ;

					}
					sb.setLength( 0 ) ;
				}
				sb.setLength( 0 ) ;
				// 展期终止日期
				/* tsEndDate = ( Timestamp ) Collections.max( alist2 ) ;
				 sb.append(
					   "update loan_ExtendForm set DTEXTENDEND = ?,MEXTENDAMOUNT = ? where ID = ?" ) ;
				 System.out.println( sb.toString() ) ;
				 ps = conn.prepareStatement( sb.toString() ) ;
				 ps.setTimestamp( 1 , tsEndDate ) ;
				 ps.setDouble( 2 , dAmountTotal ) ;
				 ps.setLong( 3 , lID ) ;
				 lResult = ps.executeUpdate() ;
				 ps.close() ;
				 ps = null ;
				 sb.setLength( 0 ) ;*/

				lResult = ( lResult == 1 ) ? appInfo.getID() : lResult ;
			}

		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return lResult ;

	}

	/**
	 * 取消展期申请或提交。
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lExtendApplyID 展期申请标识
	 * @param OBSecurityInfo sInfo 安全信息
	 * @return 1-成功，
	 * 0-操作失败
	 * －1 状态不对
	 * @exception RemoteException
	 */
	public long updateStatus( long lExtendApplyID,long lStatusID,OBSecurityInfo sInfo ) throws Exception
	{
		{
			long lResult = -1;
			Connection conn = null;
			PreparedStatement ps = null;
			StringBuffer sbSQL = null;
			StringBuffer sb = new StringBuffer();
			try
			{
				Log.print("~~~~~~~~~~~进入方法updateStatus~~~~~~~~~~");
				conn = Database.getConnection() ;
				sbSQL = new StringBuffer();
				sbSQL.append(" update OB_extend set nStatusID=? where ID=? ");
				Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
				Log.print(sbSQL.toString());
				Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
				ps = conn.prepareStatement(sbSQL.toString());
				Log.print("hehehehehehehehehhehehe");
				ps.setLong(1, lStatusID);
				ps.setLong(2, lExtendApplyID);
				if (ps.executeUpdate() > 0)
				{
					lResult = 1;
				}
				else
				{
					lResult = 0;
				}
				Log.print("hehehehehehehehehhehehe");
				ps.close();
				ps = null;
				Log.print("hehehehehehehehehhehehe");
				//如果是取消,需要做的动作
				if(lStatusID==OBConstant.LoanInstrStatus.CANCEL)
				{
					Log.print("hehehehehehehehehhehehe");
					sb.append("delete from ob_contractplanDetail where NPLANID in (select NPLANVERSIONID from ob_Extend where ID = ").append(lExtendApplyID).append(")" );
			   Log.print("hehehehehehehehehhehehe"+sb);
					System.out.println( sb.toString() ) ;
					ps = conn.prepareStatement( sb.toString() ) ;
					ps.executeUpdate() ;
					ps.close() ;
					ps = null ;
					sb.setLength( 0 ) ;

					// loan_LoanContractPlan
					sb.append( "delete from ob_ContractPlan where ID in (select NPLANVERSIONID from ob_Extend where ID = "
						  + lExtendApplyID + ")" ) ;
					System.out.println( sb.toString() ) ;
					ps = conn.prepareStatement( sb.toString() ) ;
					ps.executeUpdate() ;
					ps.close() ;
					ps = null ;
					sb.setLength( 0 ) ;

					/*/ OB_PlanVersion
					   sb.append( "update OB_PlanVersion set nIsUsed = 2 where ID in (select NPLANVERSIONID from OB_Extend where ID = (select ID from OB_Extend where nInID = "
							 + lExtendApplyID + "))" ) ;
					   System.out.println( sb.toString() ) ;
					   ps = conn.prepareStatement( sb.toString() ) ;
					   ps.executeUpdate() ;
					   ps.close() ;
					   ps = null ;
					   sb.setLength( 0 ) ;*/

				   // loan_ExtendDetail
					sb.append( "delete from ob_ExtendDetail where NEXTENDapplyID = "
						  + lExtendApplyID ) ;
					System.out.println( sb.toString() ) ;
					ps = conn.prepareStatement( sb.toString() ) ;
					lResult = ps.executeUpdate() ;
					ps.close() ;
					ps = null ;
					sb.setLength( 0 ) ;

					// loan_ExtendForm
					/*sb.append( "update loan_ExtendForm set NSTATUSID = "
						  + Constant.RecordStatus.INVALID + " where ID = "
						  + lExtendApplyID ) ;
					System.out.println( sb.toString() ) ;
					ps = conn.prepareStatement( sb.toString() ) ;
					lResult = ps.executeUpdate() ;
					ps.close() ;
					ps = null ;
					sb.setLength( 0 ) ;*/

					// 当合同下没有展期时，nIsExtend为否;
					/*sb.append(
						  "update loan_ContractForm set ISEXTEND = "
						  + Constant.YesOrNo.NO
						  + ",NSTATUSID = "
						  + LOANConstant.ContractStatus.ACTIVE
						  +
						  " where not exists (select ID from loan_ExtendForm where  (NSTATUSID = "
						  + LOANConstant.ExtendStatus.CHECK
						  + " or NSTATUSID = "
						  + LOANConstant.ExtendStatus.SUBMIT
						  + ") and ID <> "
						  + lExtendApplyID
						  + ")"
						  +
						  " and ID = (select distinct NCONTRACTID from loan_ExtendForm where ID = "
						  + lExtendApplyID
						  + ") " ) ;
					System.out.println( sb.toString() ) ;
					ps = conn.prepareStatement( sb.toString() ) ;
					lResult = ps.executeUpdate() ;
					ps.close() ;
					ps = null ;
					sb.setLength( 0 ) ;*/

				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}

			}
			return lResult;
		}

	}
	public static void main(String[] args)
	{
	}

	//如果合同有展期并状态为提交则值为2
	private long getExtendStatus( long lContractID ) throws RemoteException
	{
		long lResult = -1 ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;

		try
		{
			conn = Database.getConnection() ;
			sb.append( "select ID from loan_ExtendForm where ncontractid = "
				  + lContractID + " and nstatusid = 2" ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				if ( rs.getLong( 1 ) > 0 )
				{
					lResult = 2 ;
				}
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;
			System.out.println("hehrherhehrhehrhehrhhehrhehrhehrheout"+lResult);
			//对网银得判断
			if(lResult!=2)
			{
				System.out.println("hehrherhehrhehrhehrhhehrhehrhehrhe");
				sb.append("select ID from ob_Extend where ncontractid = "
						  + lContractID + " and nstatusid = 2");
				System.out.println("yyyyyyyyyyy"+sb.toString());
				ps = conn.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					if (rs.getLong(1) > 0)
					{
						lResult = 2;
					}
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength( 0 ) ;
			}
			System.out.println("hehrherhehrhehrhehrhhehrhehrhehrheout"+lResult);
		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return lResult ;
	}

	// 计算计划余额：lID：合同标识  lVersionID: 版本标识  lPlanListID  计划明细标示
	private static double getPlanBalance( long lContractID , long lVersionID ,
		  long lPlanListID ) throws RemoteException
	{
		double dResult = 0 ;
		double dRepayTotal = -1 ;
		double dPlanTotal = -1 ;
		double dTmp = 0 ;
		double dListAmount = 0 ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;

		try
		{
			conn = Database.getConnection() ;
			sb.append(
				  " select sum(mOpenAmount-mBalance) MREPAYTOTAL from sett_subAccount where AL_nLoanNoteID in (select id from loan_payform where nContractID = "
				  + lContractID
				  + " ) and nStatusID = "
				  + SETTConstant.SubAccountStatus.NORMAL ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				dRepayTotal = rs.getDouble( "MREPAYTOTAL" ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			sb.append(
				  "select nContractPlanID,sum(MAMOUNT) MPLANTOTAL from loan_loanContractPlanDetail where NPAYTYPEID = "
				  + LOANConstant.PlanType.REPAY
				  + " and nContractPlanID = "
				  + lVersionID
				  + " group by nContractPlanID" ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				dPlanTotal = rs.getDouble( "MPLANTOTAL" ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			sb.append(
				  "select sum(mAmount) from loan_loanContractPlanDetail where nPayTypeID = "
				  + LOANConstant.PlanType.REPAY
				  + " and nContractPlanID = "
				  + lVersionID
				  + " and dtPlanDate < (select dtplandate from loan_loanContractPlanDetail where id ="
				  + lPlanListID
				  + " )" ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				dTmp = rs.getDouble( 1 ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			sb.append(
				  "select mAmount from loan_loanContractPlanDetail where id ="
				  + lPlanListID ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				dListAmount = rs.getDouble( 1 ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			System.out.println( "dResult is :" + dResult ) ;
			System.out.println( "dRepayTotal is :" + dRepayTotal ) ;
			System.out.println( "dPlanTotal is :" + dPlanTotal ) ;
			System.out.println( "dTmp is :" + dTmp ) ;
			System.out.println( "dListAmount is :" + dListAmount ) ;

			if ( ( dRepayTotal - dTmp ) > 0 )
			{
				if ( dListAmount > ( dRepayTotal - dTmp ) )
				{
					dResult = dListAmount - ( dRepayTotal - dTmp ) ;
				}
				else
				{
					dResult = 0 ;
				}

			}
			else
			{
				dResult = dListAmount ;
			}

		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return dResult ;
	}

	private String getPlanRate( long lContractID ,
		  Timestamp tsDate ) throws RemoteException
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
				  "select NINTERESTTYPEID from loan_contractform where ID = "
				  + lContractID ) ;
			System.out.println( sb.toString() ) ;
			System.out.println( "1-------------" ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				lRateType = rs.getLong( 1 ) ;
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			if ( lRateType == LOANConstant.InterestRateType.LIBOR )
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
				ContractDao contractDao = new ContractDao() ;
				dInterestRate = contractDao.getLatelyRate( 0 , lContractID , tsDate ).getLateRate() ;
				//dInterestRate = getDefaultBankRate(lLoanID,lContractID,lRateType,dRate);

				String strSQLInterestRate =
					  "select mAdjustRate from loan_contractform "
					  + " where ID = " + lContractID ;
				ps = conn.prepareStatement( strSQLInterestRate ) ;
				System.out.println( "3-------------" + strSQLInterestRate ) ;
				rs = ps.executeQuery() ;
				if ( rs.next() )
				{
					if ( lRateType == LOANConstant.InterestRateType.BANK )
					{ // is FX
						sInterestRate = String.valueOf( DataFormat.formatDouble(
							  dInterestRate + rs.getDouble( "mAdjustRate" ) , 6 ) ) ;
					}
					else
					{
						sInterestRate = DataFormat.formatRate( ( float )
							  dInterestRate ) ;
					}
				}
				System.out.println( "3-------------" ) ;
				rs.close() ;
				rs = null ;
				ps.close() ;
				ps = null ;
			}

			// -----------------------------------------------------------end
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

	private static String getInputUserName( long lID ) throws RemoteException
	{
		String sResult = "" ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;

		try
		{
			conn = Database.getConnection() ;
			sb.append( "select * from ob_user where ID = " + lID ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			rs = ps.executeQuery() ;
			//log4j.info("came here =--------------");
			if ( rs.next() )
			{
				sResult = rs.getString( "SNAME" ) ;
			}
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;
			//log4j.info("came here =--------------");
		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw new RemoteException( ex.getMessage() ) ;
		}
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs.close() ;
				}
				if ( ps != null )
				{
					ps.close() ;
				}
				if ( conn != null )
				{
					conn.close() ;
				}
			}
			catch ( Exception ex )
			{
				throw new RemoteException( ex.getMessage() ) ;
			}
		}
		return sResult ;
	}

	private long getInterestRateID( long lContractID ,
		  Timestamp tsDate ) throws RemoteException
	{
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		Connection conn = null ;
		StringBuffer sbSQL = new StringBuffer() ;

		//查找银行利率ID
		long lID = -1 ;

		try
		{
			conn = Database.getConnection() ;
			if ( tsDate == null || tsDate.equals( "" ) )
			{
				tsDate = DataFormat.getDateTime( conn ) ;
			}

			sbSQL.append(
				  " SELECT b.id FROM loan_rateAdjustContractDetail a ,loan_interestRate b  " ) ;
			sbSQL.append( " WHERE a.nContractID = ?  " ) ;
			sbSQL.append(
				  " AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')" ) ;
			sbSQL.append( " and a.nBankInterestID = b.id(+) " ) ;
			sbSQL.append( " ORDER BY  a.dtStartDate DESC " ) ;
			ps = conn.prepareStatement( sbSQL.toString() ) ;
			ps.setLong( 1 , lContractID ) ;
			ps.setTimestamp( 2 , tsDate ) ;
			rs = ps.executeQuery() ;

			if ( rs.next() )
			{
				lID = rs.getLong( 1 ) ;
			}

			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sbSQL.setLength( 0 ) ;

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
		return lID ;

	}


}
