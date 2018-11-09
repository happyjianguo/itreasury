/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontractplan.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import java.util.*;
import java.sql.*;
import com.iss.itreasury.securities.securitiescontractplan.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dao.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractPlanDao extends SecuritiesDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecuritiesContractPlanDao(){
		super("SEC_ContractPlan");
	}
	
	/**
	 * 修改执行计划时查找可以修改执行计划的合同
	 */
	public Collection findContractByMultiOption(SecuritiesContractQueryInfo qInfo) throws Exception
	{
		Vector v = new Vector();

		String strSQL = "";
		String strSQL_con="";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			strSQL="select cc.planID,cc.inputDate as planModifyDate,bb.* from "
				+"(select * from SEC_PLANMODIFY where statusid="+SECConstant.PlanModifyStatus.SUBMIT+")cc,SEC_APPLYCONTRACT bb ";
			if (qInfo.getQueryPurpose() == 1) //修改查询
			{
				// 条件：合同状态为已复核/执行中　
				strSQL_con =" WHERE 1=1 "
						+ "and  cc.ContractID(+) = bb.ID "
						+ " AND bb.OFFICEID="
						+ qInfo.getOfficeId();

				// 是否录入人
				if (qInfo.getUserId() > 0)
					strSQL_con += " and bb.INPUTUSERID = " + qInfo.getUserId();
			}
			else if (qInfo.getQueryPurpose()==2) //审核查询
			{
				strSQL_con =" WHERE 1=1 "
						+ " and bb.OFFICEID="
						+ qInfo.getOfficeId()
						+ " and cc.statusID="+SECConstant.PlanModifyStatus.SUBMIT
						+ " and cc.ContractID(+) = bb.ID";

				// 是否录入人
				if (qInfo.getUserId() > 0)
				{
					ApprovalBiz appBiz = new ApprovalBiz();
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
					//zpli mofidy 2005-09-14
					String strAllUser =
						appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES, Constant.ApprovalLoanType.OTHER, Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN, qInfo.getOfficeId(),qInfo.getCurrencyId(),qInfo.getUserId());
					
					//String strAllUser =
					//	appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES, Constant.ApprovalLoanType.OTHER, Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN,qInfo.getUserId());
					strSQL_con += " and cc.NextCheckUserID in " + strAllUser;
				}
			}

			if (qInfo.getPlanStatusId() > 0 && qInfo.getQueryPurpose()== 1)
				strSQL_con += " and cc.STATUSID = " + qInfo.getPlanStatusId();

			//业务类型    
			if (qInfo.getTransactionTypeId () > 0)
				strSQL_con += " and bb.transactionTypeID = " + qInfo.getTransactionTypeId();

			// 合同编号起
			if (qInfo.getStartContractId() > 0)
				strSQL_con += " and bb.ID >=" + qInfo.getStartContractId() ;
				
			// 合同编号止
			if (qInfo.getEndContractId() > 0)
				strSQL_con += " and bb.ID <=" + qInfo.getEndContractId();

			//交易对手
			if (qInfo.getCounterpartId() >0)
				strSQL_con += " and bb.counterpartID=" + qInfo.getCounterpartId() ;
			
			//金额开始
			if (qInfo.getStartAmount() >0 )
				strSQL_con += " and bb.amount>=" + qInfo.getStartAmount();
				
			//金额结束
			if (qInfo.getEndAmount() >0 )
				strSQL_con += " and bb.amount<=" + qInfo.getEndAmount();

					
			//借款合同状态(已复核/执行中)
			if (qInfo.getStatusId() > 0)
				strSQL_con += " and bb.STATUSID =" + qInfo.getStatusId();
			else
				strSQL_con += " and ( bb.STATUSID = "
					+ SECConstant.ContractStatus.NOTACTIVE
					+ " or bb.STATUSID = "
					+ SECConstant.ContractStatus.ACTIVE
					+ " or bb.statusID = "
					+ SECConstant.ContractStatus.CHECK
					+ ") ";

			strSQL += strSQL_con;

			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			int nIndex = 0;
			String orderParamString=qInfo.getOrderParamString() ;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0)
			{
				if (orderParamString.substring(0,nIndex).toLowerCase().equals("sec_applycontract"))
				{
					strSQL += " order by bb." + orderParamString.substring(nIndex+1);
				}
			}
			else
			{
				strSQL += " order by bb.ID";
			}
			
			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
				strSQL += " desc";
			}
			log4j.print(strSQL);

			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				SecuritiesContractInfo info = new SecuritiesContractInfo();

				info.setId(rs.getLong("id"));
				info.setCode(rs.getString("code"));
				info.setTransactionTypeId( rs.getLong("transactionTypeID"));
				info.setCounterpartId( rs.getLong("counterPartID"));
				info.setCounterpartName(NameRef.getCounterpartNameByID(info.getCounterpartId()));	//交易对手名称
				info.setCounterpartCode(NameRef.getCounterpartCodeByID( info.getCounterpartId() )); //交易对手编号
				info.setAmount(rs.getDouble("amount"));
				info.setTerm(rs.getLong("term"));
				info.setCurrencyId( rs.getLong("currencyId"));
				info.setStatusId( rs.getLong("statusID"));
				info.setPlanModifyDate( rs.getTimestamp("planModifyDate"));
				v.addElement(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			Log.print("success!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		return (v.size() > 0 ? v : null);
	}
	
	public long  autoSavePlanDetail(SecuritiesContractAutoPlanInfo apInfo) throws Exception
	 {
		   //取得贷款的起始时间
		   int nNum = 0;  
		   double dTotal = 0;
		   double dLastTime = 0;
		   long dEach = 0;
		   long lResult = 0;
		   long lVersionID = 0;

		   long contractID=apInfo.getContractID();
		   long planID = apInfo.getPlanID() ;
		   long payType=apInfo.getPayType();
		   Timestamp payStart=apInfo.getPayStart() ;
		   Timestamp payEnd=apInfo.getPayEnd() ;
		   long repayType=apInfo.getRepayType() ;
		   Timestamp repayStart=apInfo.getRepayStart(); 
		   Timestamp repayEnd=apInfo.getRepayEnd();
		   Timestamp interestStart=apInfo.getInterestStart() ;


		   Connection con = null;
		   PreparedStatement ps = null;
		   ResultSet rs = null;
           
		   SecuritiesContractPlanDetailDao pdDao=new SecuritiesContractPlanDetailDao();
		   SecuritiesContractPlanDetailInfo pdInfo=null;

		   try
		   {
		   	   /*删除当前的计划明细*/	
		   	   pdDao.deletePlanDetailByVer( apInfo.getPlanID() );
		   	   	
			   con = Database.getConnection();
			   StringBuffer sb = new StringBuffer();
               
			   //取得申请金额
			   sb.append("SELECT amount FROM SEC_APPLYCONTRACT WHERE ID = ?");
			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1,contractID);
			   rs = ps.executeQuery();
			   if (rs.next())
			   {
				   dTotal = rs.getDouble("Amount");
				   System.out.println("dTotal is " + dTotal);
			   }
			   if (rs!=null)
			   {
			   		rs.close() ;
			   		rs=null;
			   }
			   if(ps!=null)
			   {
			   		ps.close(); 
			   		ps=null;
			   }
			   sb.setLength(0);

	   		   lVersionID=planID;
			   if ( lVersionID<=0 )
			   {
					log.error("Can not find plan version for apply"+contractID);
					return -1;
			   }
 
			   int nAdd = 0;
			   pdDao=new SecuritiesContractPlanDetailDao();
			   pdDao.setUseMaxID() ;
			   if ( payType == SECConstant.PayType.ONETIME) //一次性放款
			   {
					pdInfo=new SecuritiesContractPlanDetailInfo();
					pdInfo.setId(-1);
					pdInfo.setPlanVersion( lVersionID);
					pdInfo.setPayTypeID(SECConstant.PlanType.PAY);
					pdInfo.setPlanDate(payStart);
					pdInfo.setPlanType("本金");
					pdInfo.setModifyDate( Env.getSystemDate());
					pdInfo.setAmount(dTotal);
					
					this.showPlanDetail( pdInfo );	
					lResult=pdDao.add(pdInfo);
					pdInfo=null;
			   }
			   else
			   {
					ArrayList payDateList=new ArrayList();
					while (!payStart.after(payEnd)|| payStart.toString().substring(1,10).equals(payEnd.toString().substring(1,10)))
					{
						payDateList.add(payStart); 
						nNum++;
						switch ( (int)payType )
						{
							case  (int)SECConstant.PayType.ONEYEAR  :     //年
								payStart = DataFormat.getNextMonth(payStart,12);
								break;
							case  (int)SECConstant.PayType.ONEMONTH  :     //ONEMONTH
								payStart = DataFormat.getNextMonth(payStart,1);
								break;
							case (int)SECConstant.PayType.THREEMONTH  :     //3 month
								payStart = DataFormat.getNextMonth(payStart,3);
								break;
							case  (int)SECConstant.PayType.SIXMONTH :    //6 月
								payStart = DataFormat.getNextMonth(payStart,6);
								break;
						}
					}
					dEach = (long)dTotal/nNum;
					dLastTime = dTotal - dEach*(nNum-1);
					int n=payDateList.size();
					for ( int i=0;i<n;i++ )
					{
						pdInfo=new SecuritiesContractPlanDetailInfo();
						pdInfo.setPlanVersion(lVersionID);
						pdInfo.setPayTypeID(SECConstant.PlanType.PAY );
						pdInfo.setPlanDate((Timestamp)payDateList.get(i));
						pdInfo.setPlanType("本金");
						pdInfo.setModifyDate( Env.getSystemDate());
						
						if ( i==( n-1 ) )
						{
							pdInfo.setAmount(dLastTime);
						}
						else
						{
							pdInfo.setAmount(dEach);
						}
						this.showPlanDetail( pdInfo );
						lResult=pdDao.add(pdInfo);
						pdInfo=null;
					}
				}
			   // 放款开始 
			   nNum = 0;
			   dEach = 0;
			   dLastTime = 0;
				if (repayType == SECConstant.RepayType.ONETIME) //一次性放款
				{
				   pdInfo=new SecuritiesContractPlanDetailInfo();
				   pdInfo.setPlanVersion(lVersionID);
				   pdInfo.setPayTypeID(SECConstant.PlanType.REPAY );
				   pdInfo.setPlanDate(repayStart);
				   pdInfo.setPlanType("本金");
				   pdInfo.setAmount(dTotal);
				   pdInfo.setModifyDate( Env.getSystemDate());
				   this.showPlanDetail( pdInfo );				   
				   lResult=pdDao.add(pdInfo);
				   pdInfo=null;
				}
				else
				{
					ArrayList repayDateList=new ArrayList();
					while (!repayStart.after(repayEnd) || repayStart.toString().substring(1,10).equals(repayEnd.toString().substring(1,10)))
					{
						repayDateList.add(repayStart);
						nNum++;
						switch ((int)repayType)
						{
							case  (int)SECConstant.RepayType.ONEYEAR  :     //年
								repayStart = DataFormat.getNextMonth(repayStart,12);
								break;
							case  (int)SECConstant.RepayType.ONEMONTH  :     //ONEMONTH
								repayStart = DataFormat.getNextMonth(repayStart,1);
								break;
							case (int)SECConstant.RepayType.THREEMONTH  :     //3 month
								repayStart = DataFormat.getNextMonth(repayStart,3);
								break;
							case  (int)SECConstant.RepayType.SIXMONTH :    //6 月
								repayStart = DataFormat.getNextMonth(repayStart,6);
								break;
						}
					}
				   dEach = (long)dTotal/nNum;
				   dLastTime = dTotal - dEach*(nNum-1);
				   int n=repayDateList.size();
				   for ( int i=0;i<n;i++ )
				   {
					   pdInfo=new SecuritiesContractPlanDetailInfo();
					   pdInfo.setPlanVersion(lVersionID);
					   pdInfo.setPayTypeID(SECConstant.PlanType.REPAY  );
					   pdInfo.setPlanDate((Timestamp)repayDateList.get(i));
					   pdInfo.setPlanType("本金");
					   pdInfo.setModifyDate( Env.getSystemDate());
					   if ( i==( n-1 ) )
					   {
						   pdInfo.setAmount(dLastTime);
					   }
					   else
					   {
						   pdInfo.setAmount(dEach);
					   }
					this.showPlanDetail( pdInfo );					   
					   lResult=pdDao.add(pdInfo);
					   pdInfo=null;
				   }
			   }
			   /*插入利息计划*/
				pdInfo=new SecuritiesContractPlanDetailInfo();
				pdInfo.setPlanVersion(lVersionID);
				pdInfo.setPayTypeID(SECConstant.PlanType.REPAY  );
				pdInfo.setPlanDate(interestStart);
				pdInfo.setPlanType("利息");
				pdInfo.setModifyDate( Env.getSystemDate());
				this.showPlanDetail( pdInfo );				
				lResult=pdDao.add(pdInfo);
				pdInfo=null;
		   }
		   catch(Exception e)
		   {
				e.printStackTrace();
				if (rs!=null)
				{
					rs.close() ;
					rs=null;
				}
				if(ps!=null)
				{
					ps.close(); 
					ps=null;
				}
				if (con!=null)
				{
					con.close();
					con=null;
				}
				 throw e;
		   }finally{
				if (rs!=null)
				{
					rs.close() ;
					rs=null;
				}
				if(ps!=null)
				{
					ps.close(); 
					ps=null;
				}
				if (con!=null)
				{
					con.close();
					con=null;
				}
		   }
		   return lResult;
        
	}	
	/**
	 * 根据合同号查找合同的执行计划版本
	 */
	public Collection findPlanVerByContract(long lID) throws Exception
	{
		String strSQL="";
		Collection c=null;
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			strSQL = "select * from SEC_ContractPlan \n"
				+" where 1=1 \n"
				+" and planVersion>0\n"
				+" and contractID="+lID
				+" order by ID ";
				
			prepareStatement(strSQL);
			executeQuery();
			c=getDataEntitiesFromResultSet( SecuritiesContractPlanVersionInfo.class );
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同版本列表",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return c;					
	}
	

	/**
	 * 根据合同号查找合同的最新当前计划版本
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public SecuritiesContractPlanVersionInfo findLastPlanVersionByContract(long lID) throws Exception
	{
		String strSQL="";
		SecuritiesContractPlanVersionInfo c = null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		long verID = -1;
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			conn=Database.getConnection() ;
			strSQL = "select max(ID) as maxID from sec_contractPlan"
				+" where 1=1 \n"
				+" and contractID="+lID
				+" and planversion>0 "
				+" and statusID=1 ";
				
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if (rs.next()) 
			{
				verID=rs.getLong("maxID");
			}
			if (rs!=null)
			{
				rs.close() ;
				rs=null;			
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}
			if (verID>0)
			{
				c=(SecuritiesContractPlanVersionInfo)this.findByID( verID,SecuritiesContractPlanVersionInfo.class  );
			}
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同版本时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (c);		
	}

	/**
	 * 根据合同号查找合同的最新当前计划版本的计划明晰列表
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public Collection findPlanDetailByContract(long lID) throws Exception
	{
		String strSQL="";
		Collection c = null;
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		long verID = -1;
		
		/*-----------------init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of init------------------*/		
				
		try 
		{
			conn=Database.getConnection() ;
			
			strSQL = "select max(ID) as maxID from sec_contractPlan"
				+" where 1=1 \n"
				+" and contractID="+lID
				+" and planversion>0 "
				+" and statusID=1 ";
				
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if (rs.next()) 
			{
				verID=rs.getLong("maxID");
			}
			if (rs!=null)
			{
				rs.close() ;
				rs=null;			
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}
			if (verID>0)
			{
				SecuritiesContractPlanDetailDao dao=new SecuritiesContractPlanDetailDao();
				c=dao.findPlanDetailByVer( verID );
			}
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("查找合同时产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/		
		return (c);		
	}
	//审批完成后需要做的操作
	private void insertValues(long lPlanModifyID) throws ITreasuryDAOException
	{
		PreparedStatement ps = null;
		Connection conn = null;
		long lResult = -1;
		String strSQL = "";
		try
		{
			conn = Database.getConnection();

			strSQL = "UPDATE sec_ContractPlan SET STATUSID =?,IsUsed=? WHERE ID = ( select PlanID from SEC_planModify where id = ?)";
			System.out.println("the sql is : " + strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, 1);
			ps.setLong(2, Constant.YesOrNo.NO);
			ps.setLong(3, lPlanModifyID);

			lResult = ps.executeUpdate();
			if (lResult <= 0)
			{
				lResult = -1; //失败
			}
			else
			{
				lResult = 1; //成功
			}
			ps.close();
			ps = null;

			conn.close();
			conn = null;

		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			throw new ITreasuryDAOException("",e);
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
				throw new ITreasuryDAOException("",ex);
			}
		}
	}
	
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesDAOException {
		
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();

		PlanModifyInfo aInfo = new PlanModifyInfo();
		SecuritiesPlanModifyDao mDao = new SecuritiesPlanModifyDao();

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{			

			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(SECConstant.PlanModifyStatus.REFUSE );
			try {
				mDao.update(aInfo);
				checkRefuse(lApprovalContentID);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			} catch (Exception ex){
				ex.printStackTrace() ;
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(SECConstant.PlanModifyStatus.SUBMIT);
			aInfo.setNextCheckUserID(lNextUserID);
			aInfo.setNextCheckLevel(getNextCheckLevelByVersionID(lApprovalContentID) + 1);			
			try {
				mDao.update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}

		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(SECConstant.PlanModifyStatus.CHECK);
			aInfo.setNextCheckUserID(lNextUserID);
			try {
				mDao.update(aInfo);
				insertValues( lApprovalContentID );
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			} 
		}

		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(SECConstant.PlanModifyStatus.SUBMIT);
			aInfo.setNextCheckUserID(ATInfo.getInputUserID());
			aInfo.setNextCheckLevel( 1 );
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		
		log4j.debug("check end");
		
		return lApprovalContentID;
	}	

	private void checkRefuse(long lArrorvalContentID) throws Exception
	{
		String strSQL="";
		Connection conn=null;
		PreparedStatement ps=null;
		
		conn=Database.getConnection() ;
		strSQL ="update SEC_contractplan set PlanVersion = -1 where id = (select PlanID from SEC_planmodify where id = "
				+ lArrorvalContentID
				+ ")";

		ps = conn.prepareStatement(strSQL);
		ps.executeUpdate();
		ps.close();
		ps = null;
		conn.close();
		conn=null;		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	private long getNextCheckLevelByVersionID(long verId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from SEC_planModify where 1 = 1 ";
		strSQL += " and planId = " + verId;
		try
		{
			initDAO();
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			try
			{
				while (rs != null && rs.next()) 
				{
					nextCheckLevel = rs.getLong("nextCheckLevel");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch(ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return nextCheckLevel;
	}
	public long createPlanVersion(long lID, long lContractID, long lUserID, long lOfficeID) throws Exception
	{
		long lResult = -1;
		long lVersion = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long lStatusID = -1;
		long lUsertype = -1;
		long lID_old = -1;
		SecuritiesPlanModifyDao mDao = new SecuritiesPlanModifyDao();
		mDao.setUseMaxID() ;
		try
		{
			conn = Database.getConnection();

			/*
			 *  修改已经提交的执行计划时，实际上是新增加了一个版本号为空的记录
			 *  因此，在重新保存修改时
			 *  要删除旧的已经提交的版本记录
			 */
			sb.append(
				"select statusid,ID from sec_ContractPlan where ID = (select max(id) from sec_ContractPlan where ContractID = ? and PlanVersion > 0 )");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lStatusID = rs.getLong("statusID");
				lID_old = rs.getLong("ID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			sb.append("select nvl(max(PLANVERSION)+1,1) from sec_ContractPlan where ContractID = ?");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lVersion = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			/*
			 * 如果是在已提交的计划修改记录基础上重新提交，
			 * 删除之前的版本 并且修改loan_PlanModifyForm表中的nPlanID，
			 * 指向新的版本标示
			 */
			if (lStatusID == 0)
			{
				sb.setLength(0);
				sb.append(
					"delete SEC_ContractPlanDetail where PlanVersionID = (select max(id) from SEC_ContractPlan where ContractID = ? and PlanVersion > 0)");
				Log.print(sb.toString());
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, lContractID);
				lResult = ps.executeUpdate();
				if (lResult < 1)
				{
					System.out.println("error.update.loan_loanContractPlan");
				}
				ps.close();
				ps = null;

				sb.setLength(0);
				sb.append("delete SEC_ContractPlan where ID = (select max(id) from SEC_ContractPlan where ContractID = ? and PlanVersion > 0)");
				Log.print(sb.toString());
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, lContractID);
				lResult = ps.executeUpdate();
				if (lResult < 1)
				{
					System.out.println("error.update.loan_loanContractPlan");
				}
				else
				{
					lVersion = lVersion - 1;
				}
				ps.close();
				ps = null;

				sb.setLength(0);
				sb.append("update SEC_PlanModify set PlanID = ?  where PlanID = ? ");
				Log.print(sb.toString());
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, lID);
				ps.setLong(2, lID_old);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
			}
			/*
			 * 如果是在已复核的计划基础上提交， 
			 * 新增loan_PlanModifyForm表中记录
			 */
			else
			{
				//在计划修改表中插入一条记录
				PlanModifyInfo planmodifyinfo = new PlanModifyInfo();
				planmodifyinfo.setContractID(lContractID);
				planmodifyinfo.setInputUserID(lUserID);
				planmodifyinfo.setInputDate( Env.getSystemDate() );
				planmodifyinfo.setNextCheckUserID(lUserID);
				planmodifyinfo.setPlanID(lID);
				planmodifyinfo.setStatusID( SECConstant.PlanModifyStatus.SUBMIT );
				planmodifyinfo.setNextCheckLevel( 1 );
				mDao.add(planmodifyinfo);
			}
			sb.setLength(0);
			sb.append("update sec_ContractPlan set PLANVERSION = ?,ISUSED = ?,STATUSID = ?  where ID = ? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lVersion);
			ps.setLong(2, 1);
			ps.setLong(3, 0);
			ps.setLong(4, lID);
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			sb.setLength(0);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				throw ex;
			}
		}
		return lResult;
	}
	
	public void showPlanDetail(SecuritiesContractPlanDetailInfo info)
	{
		if (info==null) 
			System.out.println("info is null");
		else	
		{
			System.out.println("----------------------");
			System.out.println("amount:"+info.getAmount() );
			System.out.println("planversionID:"+info.getPlanVersionID()  );
			System.out.println("payTypeID:"+info.getPayTypeID()  );
			System.out.println("Type:"+info.getPlanType() );
			System.out.println("----------------------");
		}
	}
	public static void main(String args[])
	{
		SecuritiesContractPlanDao dao = new SecuritiesContractPlanDao();
		Collection c =null;
		SecuritiesContractAutoPlanInfo info = new SecuritiesContractAutoPlanInfo();
		try
		{
			info.setContractID( 181 );
			info.setPlanID( 2 );
			info.setPayType( 1 );
			info.setRepayType( 1 );
			info.setPayStart(DataFormat.getDateTime("2004-06-01"));
			info.setPayEnd(DataFormat.getDateTime("2004-09-01"));
			info.setRepayStart(DataFormat.getDateTime("2004-06-01"));
			info.setRepayEnd(DataFormat.getDateTime("2004-09-01"));
			info.setSettlementTypeID( 1 );
			info.setInterestStart( DataFormat.getDateTime("2004-06-01") );
			info.setInterestStart( DataFormat.getDateTime("2004-09-01") );
			
			dao.autoSavePlanDetail( info );
			//dao.findLastPlanVersionByContract( 141 );
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c);
	}
	public long createTempVersion(long planID,long contractID,long userID,long officeID) throws Exception
	{
		long lResult = -1;
		long lVersionID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList alist = new ArrayList();
		SecuritiesContractDao conDao = new SecuritiesContractDao();
		SecuritiesContractPlanDetailDao detailDao = new SecuritiesContractPlanDetailDao();
		SecuritiesContractInfo conInfo = new SecuritiesContractInfo();

		try
		{
			/* 读取合同信息 */
			conInfo=(SecuritiesContractInfo)conDao.findByID(contractID,conInfo.getClass() );
			
			conn = Database.getConnection();
			/*首先看该合同下是否已经有临时版本*/
			sb.append("select ID from sec_ContractPlan where ContractID = ? and PLANVERSION <=0");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, contractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lVersionID = rs.getLong(1);
			}
			if (rs!=null)
			{
				rs.close();
				rs = null;
			}
			if (ps!=null)
			{
				ps.close();
				ps = null;
			}
			sb.setLength(0);

			if (lVersionID == -1) //无临时版本
			{
				setUseMaxID();
				SecuritiesContractPlanVersionInfo vInfo = new SecuritiesContractPlanVersionInfo();
				vInfo.setApplyID( conInfo.getAccountId() );
				vInfo.setContractID( contractID );
				vInfo.setPlanVersion( 0 );
				vInfo.setInputUserID( userID );
				vInfo.setInputTime( Env.getSystemDate() );
				vInfo.setIsUsed( Constant.YesOrNo.YES );
				vInfo.setStatusID( Constant.RecordStatus.INVALID );
				lVersionID=this.add(vInfo); 
			}	
			else				// 已有一空版本号记录，先删除该版本明细
			{
				detailDao.deletePlanDetailByVer( lVersionID );
				
				//更新该版本信息为新的信息
				SecuritiesContractPlanVersionInfo vInfo = (SecuritiesContractPlanVersionInfo)findByID(lVersionID,SecuritiesContractPlanVersionInfo.class );
				vInfo.setId(lVersionID);
				vInfo.setApplyID( conInfo.getAccountId() );
				vInfo.setContractID( contractID );
				vInfo.setPlanVersion( 0 );
				vInfo.setInputUserID( userID );
				vInfo.setInputTime( Env.getSystemDate() );
				vInfo.setIsUsed( Constant.YesOrNo.YES );
				vInfo.setStatusID( Constant.RecordStatus.INVALID );
				this.update(vInfo);
			}

			// 复制指定的版本号
			alist=(ArrayList)detailDao.findPlanDetailByVer( planID );
			detailDao.setUseMaxID(); 
			for ( int i=0;i<alist.size();i++)
			{
				SecuritiesContractPlanDetailInfo detailInfo = new SecuritiesContractPlanDetailInfo();
				SecuritiesContractPlanDetailInfo oInfo=(SecuritiesContractPlanDetailInfo)alist.get(i);
				detailInfo.setAmount(oInfo.getAmount());
				detailInfo.setPayTypeID( oInfo.getPayTypeID() );
				detailInfo.setPlanDate( oInfo.getPlanDate() );
				detailInfo.setPlanType( oInfo.getPlanType() );
				detailInfo.setPlanVersion( lVersionID );
				detailInfo.setModifyDate( oInfo.getModifyDate() );
				detailDao.add(detailInfo);
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
		
		return lVersionID;
	}
	
}
