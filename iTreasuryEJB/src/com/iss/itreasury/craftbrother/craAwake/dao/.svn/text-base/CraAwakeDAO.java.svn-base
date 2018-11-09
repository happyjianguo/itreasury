package com.iss.itreasury.craftbrother.craAwake.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.craAwake.dataentity.CraAwakeCondition;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class CraAwakeDAO {
	private Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public static final int REMINT_ZTX_INVEST_REPURCHASE = 1;//转贴现买入（回购）
	public static final int REMINT_ZTX_AVERAGE_REPURCHASE = 2; //转贴现卖出（回购）
	public static final int REMINT_REPURCHASE_NOTIFY = 3;////合同卖出（回购）
	public static final int REMINT_CAPITAL_IN_REPAY = 4; //资金拆入返款
	public static final int REMINT_CAPITAL_OUT_REPAY = 5; //资金拆出返款
	public static final int REMINT_REPURCHASE_NOTIFYBUY = 6;//合同买入（回购）
	public CraAwakeDAO()
	{
	}
	/**
	 * 获取提醒信息
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void getAllAwakeContract() throws RemoteException, Exception
	{
		String sResult = " ";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
        StringBuffer sbSQL = null;
		CraAwakeCondition info = null;

		try
		{
			conn = Database.getConnection();
			
            sbSQL = new StringBuffer();
            sbSQL.append(" select distinct nOfficeID,nCurrencyID from SETT_OFFICETIME c \n");
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs.next())
            {
                long lCurrencyID = -1;
                long lOfficeID = -1;
                info = new CraAwakeCondition();
                
                lOfficeID=rs.getLong("nOfficeID");
                lCurrencyID=rs.getLong("nCurrencyID");
			
				info.setCon(conn);
				//info.setClientID(lClientID);
				info.setCurrencyID(lCurrencyID);
				//info.setUserID(lUserID);
				info.setOfficeID(lOfficeID);
				//获取提醒设置信息，并进一步向完善info中的值
				getAwakeSetting(info);
				// 对转贴现买入（回购）业务进行到期提醒
				sResult +=findZTXInvestRepurchase(info);
				// 对转贴现卖出（回购）业务进行到期提醒
				sResult +=findZTXAveragRepurchase(info);
				//sResult += getTransdiscountMsg(info);
				// 对 合同卖出（回购）进行到期提醒
				sResult += getRepurchaseNotify(info);
				//对资金拆出业务通知单进行到期提醒     
				sResult += getDeliveryOrderMsgSell(info);
				//对资金拆出业务通知单进行到期提醒
				sResult += getDeliveryOrderMsgBuy(info);
				// 对 合同买入（回购）进行到期提醒
				sResult += getRepurchaseNotifybuy(info);
				
	
				String strKey =  String.valueOf(info.getOfficeID())+String.valueOf(info.getCurrencyID());
				System.out.println(strKey);
				System.out.println(sResult);
				CraAwakeCondition.AwakeMSG.put(strKey, sResult);
				log4j.info("strKey:" + strKey);
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
		catch (RemoteException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 获取各种业务类型的提醒业务设置信息
	 * @param lCurrencyID
	 * @param lOfficeID
	 * @param con
	 * @param info
	 * @throws RemoteException
	 * @throws Exception
	 */
	 public void getAwakeSetting(CraAwakeCondition info)
     throws RemoteException, Exception
	 {
	     //Connection con = null;
	     PreparedStatement ps = null;
	     ResultSet rs = null;
	     StringBuffer sb = new StringBuffer();
	     String strSQL = "";
	     /************业务提醒设置参数*************/
	     long lAwakeDay = -1; //提醒天数
	     long lAheadAwakeDay = -1; //提前提醒天数
	     long lAwakeType = -1; //提醒类型（方式）
	     long[] lAwakeDays1 = new long[10];//
	     long[] lAheadAwakeDays1 = new long[10];//
	     try
	     {
	         //----------从业务提醒设置中取出设置---------//          
	         sb.setLength(0);
	         sb.append(" select * from cra_AttermAwakeSetting ");
	         sb.append(" where 1 = 1 ");
	         sb.append(" AND NOFFICEID = " + info.getOfficeID());
	         //sb.append (" and NCURRENCYID = "+lCurrencyID);
	         sb.append(" order by NAWAKETYPEID  ");
	         //Log.print("业务提醒设置:"+sb.toString());
	         ps = info.getCon().prepareStatement(sb.toString());
	         rs = ps.executeQuery();
	         while (rs.next() && rs != null)
	         {
	             lAwakeDay = rs.getLong("NAWAKEDAYS"); //提醒天数
	             lAheadAwakeDay = rs.getLong("NAHEADDAYS"); //提前提醒天数
	             lAwakeType = rs.getLong("NAWAKETYPEID"); //提醒类型
	             switch ((int)lAwakeType) {
					case (int)CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE:
						lAwakeDays1[REMINT_ZTX_INVEST_REPURCHASE] = lAwakeDay; //提醒天数
		             	lAheadAwakeDays1[REMINT_ZTX_INVEST_REPURCHASE] = lAheadAwakeDay; //提前提醒天数
						break;
					case (int)CRAconstant.TransactionType.ZTX_AVERAGE_REPURCHASE:
						lAwakeDays1[REMINT_ZTX_AVERAGE_REPURCHASE] = lAwakeDay; //提醒天数
	             		lAheadAwakeDays1[REMINT_ZTX_AVERAGE_REPURCHASE] = lAheadAwakeDay; //提前提醒天数
						break;
					case (int)CRAconstant.TransactionType.REPURCHASE_NOTIFY:
						lAwakeDays1[REMINT_REPURCHASE_NOTIFY] = lAwakeDay; //提醒天数
             			lAheadAwakeDays1[REMINT_REPURCHASE_NOTIFY] = lAheadAwakeDay; //提前提醒天数
						break;
					case (int)CRAconstant.TransactionType.CAPITAL_IN_REPAY:
						lAwakeDays1[REMINT_CAPITAL_IN_REPAY] = lAwakeDay; //提醒天数
             			lAheadAwakeDays1[REMINT_CAPITAL_IN_REPAY] = lAheadAwakeDay; //提前提醒天数
						break;
					case (int)CRAconstant.TransactionType.CAPITAL_OUT_REPAY:
						lAwakeDays1[REMINT_CAPITAL_OUT_REPAY] = lAwakeDay; //提醒天数
             			lAheadAwakeDays1[REMINT_CAPITAL_OUT_REPAY] = lAheadAwakeDay; //提前提醒天数
						break;
					case (int)CRAconstant.TransactionType.AVERAGE_NOTIFY:
						lAwakeDays1[REMINT_REPURCHASE_NOTIFYBUY] = lAwakeDay;//提醒天数
					    lAheadAwakeDays1[REMINT_REPURCHASE_NOTIFYBUY] = lAheadAwakeDay;//提前提醒天数
					default:
						break;
					}
	             
	             //Log.print("类型:"+lAwakeType
	             // +",提前:"+lAheadAwakeDay
	             // +"天,提醒:"+lAwakeDay+"天");
	         }
	         info.setAheadAwakeDays1(lAheadAwakeDays1);
	         info.setAwakeDays1(lAwakeDays1);
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
	         }
	         catch (Exception e)
	         {
	             e.printStackTrace();
	             throw new Exception(e.getMessage());
	         }
	     }
	 }
	 /**
		 * 仅供转贴现买入（回购）到期查询页面使用   lijunli 2010.8.27
	 * @throws Exception 
		 */
	 public Collection findZTXInvestRepurchase1(CraAwakeCondition info) throws Exception
	 {
		 	Connection conn = null;
		    PreparedStatement ps = null;
		    Vector v = new Vector();
		    
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			Timestamp tsCurrent = Env.getSystemDateTime();
			TransDiscountContractBillInfo billInfo = null;
			long lCount = 0; //到期天数
			String  sScode = "";//转贴现汇票号码
			long  lBillID=-1;//汇票ID
			double dAmount = 0; //贷款金额
			Timestamp tsEndDate = null; //到期日期
			//int lTotal =0;//笔数
			
			try
			{
				conn = Database.getConnection();
				info.setCon(conn);
				getAwakeSetting(info);
				
				sb.append("select b.*, a.dtrepurchasedate,(SYSDATE - a.dtrepurchasedate) as dtCount" );
				sb.append(" from cra_loanform a, loan_contractform b");
				sb.append(" where b.nloanid = a.id");				
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append(" and b.ninorout = " + LOANConstant.TransDiscountInOrOut.IN );
				sb.append(" and b.ndiscounttypeid  = " + LOANConstant.TransDiscountType.REPURCHASE );
				sb.append( " order by a.dtrepurchasedate desc");
				log4j.info(" 转贴现卖出（回购）到期提醒查询: \n" + sb.toString());
				System.out.println(sb.toString());
				String strSQL = sb.toString();
				//log.debug(strSQL);
				ps = conn.prepareStatement(sb.toString());
				
				int i = 0;
				rs = ps.executeQuery();
				while (rs.next())
				{
					//int lTotal =i++;//笔数
					i++;
					int nDays = 0;
					lCount = rs.getLong("dtCount"); //到期天数
					TransDiscountContractInfo tdinfo= new TransDiscountContractInfo();
				
					
					tdinfo.setRepurchasedate(rs.getTimestamp("dtrepurchasedate"));//回购日期
			        tdinfo.setInOrOut(rs.getLong("NINOROUT"));//买入卖出
			        tdinfo.setDiscountDate(rs.getTimestamp("dtdiscountdate"));//转贴现日
			        tdinfo.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//转贴现回购类型
			        tdinfo.setDiscountRate(rs.getDouble("MDISCOUNTRATE"));//贴现利率
			        tdinfo.setContractCode(rs.getString("scontractcode"));//转贴现合同编号
			        
			       
			        tdinfo.setEndDate(rs.getTimestamp("dtenddate"));//贷款结束日期
			        tdinfo.setStartDate(rs.getTimestamp("dtstartdate"));//贷款开始时间
			        tdinfo.setDiscountTypeId(rs.getLong("ndiscounttypeid"));//转贴现类型	        
			        tdinfo.setLoanAmount(rs.getDouble("mloanamount"));//贷款金额      
			        
			            
			        
					
					v.add(tdinfo);
				}

			}
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new LoanDAOException("查询失败", e);
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
			         if (conn != null) {
						conn.close();
						conn = null;
					}
		        } catch (Exception e)
		        {
		            // TODO Auto-generated catch block
		        	 e.printStackTrace();
		             throw new Exception(e.getMessage());
		        }
		    }
			return (v.size() > 0 ? v : null );
		}
	 
	 /**
		 * 仅供转贴现卖出（回购）到期查询页面使用   lijunli 2010.8.27
	 * @throws Exception 
		 */
	 public Collection findZTXAveragRepurchase1(CraAwakeCondition info) throws Exception
	 {
		 	Connection conn = null;
		    PreparedStatement ps = null;
		    Vector v = new Vector();
		    
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			Timestamp tsCurrent = Env.getSystemDateTime();
			TransDiscountContractBillInfo billInfo = null;
			long lCount = 0; //到期天数
			String  sScode = "";//转贴现汇票号码
			long  lBillID=-1;//汇票ID
			double dAmount = 0; //贷款金额
			Timestamp tsEndDate = null; //到期日期
			//int lTotal =0;//笔数
			
			try
			{
				conn = Database.getConnection();
				info.setCon(conn);
				getAwakeSetting(info);
				
			
				
				sb.append(" select b.*, a.dtrepurchasedate,(SYSDATE - a.dtrepurchasedate) as dtCount" );
				sb.append(" from cra_loanform a, loan_contractform b");
				sb.append(" where b.nloanid = a.id");
				
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append(" and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT);
				sb.append(" and b.ndiscounttypeid  = " + LOANConstant.TransDiscountType.REPURCHASE);
				sb.append( " order by a.dtrepurchasedate desc");
				
				
				/*sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append("and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT);
				sb.append("and b.ndiscounttypeid  = " + LOANConstant.TransDiscountType.REPURCHASE);
				sb.append( " order by a.dtrepurchasedate desc");
				
				sb.append("select a.*,(SYSDATE-d.REPURCHASEDATE ) as dtCount, b.scontractcode, b.dtstartdate, b.dtenddate, b.mloanamount,b.discountclientname, b.MDISCOUNTRATE DiscountRate, b.dtDiscountDate DiscountDate, b.NDISCOUNTTYPEID DiscountTypeID, b.NREPURCHASETYPEID RepurchaseTypeID, b.NINOROUT INOROUT,");
				sb.append("b.ndiscounttypeid,b.mchargerate,b.nintervalnum,b.ninorout,b.purchaserinterestrate,b.nbankacceptpo,b.nbizacceptpo, d.REPURCHASEDATE billREPURCHASEDATE,d.REPURCHASETERM billREPURCHASETERM,d.ISLOCAL billIsLocal,d.ADDDAYS billAddDays,d.CHECKAMOUNT billCheckAmount,b.mcheckamount ");
				sb.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, RTRANSDISCOUNTCREDENCEBILL c, RTRANSDISCOUNTCONTRACTBILL d, LOAN_DISCOUNTCREDENCE e ");
				sb.append(
					" where d.transdiscountcontractid = b.ID and a.ID = c.DISCOUNTCONTRACTBILLID and a.id = d.DISCOUNTCONTRACTBILLID and d.transdiscountcontractid=e.ncontractid and c.transdiscountcredenceid =e.id ");
				//buffer.append(" and c.transdiscountcredenceid = " + lTransDiscountCredenceID);
				sb.append(" and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT);
				sb.append(" and b.ndiscounttypeid= " + LOANConstant.TransDiscountType.REPURCHASE);
				sb.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append( "and ( SYSDATE - d.REPURCHASEDATE ) >="+ -info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]+"");
				sb.append( " order by d.REPURCHASEDATE desc");*/
				log4j.info(" 转贴现卖出（回购）到期提醒查询: \n" + sb.toString());
				System.out.println(sb.toString());
				String strSQL = sb.toString();
				//log.debug(strSQL);
				ps = conn.prepareStatement(sb.toString());
				
				int i = 0;
				rs = ps.executeQuery();
				while (rs.next())
				{
					//int lTotal =i++;//笔数
					i++;
					int nDays = 0;
					lCount = rs.getLong("dtCount"); //到期天数
					TransDiscountContractInfo tdinfo= new TransDiscountContractInfo();
					
					tdinfo.setRepurchasedate(rs.getTimestamp("dtrepurchasedate"));//回购日期
			        tdinfo.setInOrOut(rs.getLong("NINOROUT"));//买入卖出
			        tdinfo.setDiscountDate(rs.getTimestamp("dtdiscountdate"));//转贴现日
			        tdinfo.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//转贴现回购类型
			        tdinfo.setDiscountRate(rs.getDouble("MDISCOUNTRATE"));//贴现利率
			        tdinfo.setContractCode(rs.getString("scontractcode"));//转贴现合同编号
			        
			       
			        tdinfo.setEndDate(rs.getTimestamp("dtenddate"));//贷款结束日期
			        tdinfo.setStartDate(rs.getTimestamp("dtstartdate"));//贷款开始时间
			        tdinfo.setDiscountTypeId(rs.getLong("ndiscounttypeid"));//转贴现类型	        
			        tdinfo.setLoanAmount(rs.getDouble("mloanamount"));//贷款金额 
			       
			        
					
					v.add(tdinfo);
				}

			}
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new LoanDAOException("查询失败", e);
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
			         if (conn != null) {
						conn.close();
						conn = null;
					}
		        } catch (Exception e)
		        {
		            // TODO Auto-generated catch block
		        	 e.printStackTrace();
		             throw new Exception(e.getMessage());
		        }
		    }
			return (v.size() > 0 ? v : null );
		}
	 
	 /**
		 * 获取转贴现买入（回购）的提醒信息   lijunli 2010.8.23
	 * @throws Exception 
		 */
	
	 public String findZTXInvestRepurchase(CraAwakeCondition info) throws Exception
		{
		 String sResult = "";
		 PreparedStatement ps = null;
		 Vector v = new Vector();
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			TransDiscountContractBillInfo billInfo = null;
			long lCount = 0; //到期天数
			String  sScode = "";//转贴现汇票号码
			long  lBillID=-1;//汇票ID
			double dAmount = 0; //贷款金额
			Timestamp tsEndDate = null; //到期日期
			
			try
			{
				//initDAO();
				
				sb.append("select b.*, a.dtrepurchasedate,(SYSDATE - a.dtrepurchasedate) as dtCount" );
				sb.append(" from cra_loanform a, loan_contractform b");
				sb.append(" where b.nloanid = a.id");
				
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append(" and b.ninorout = " + LOANConstant.TransDiscountInOrOut.IN );
				sb.append(" and b.ndiscounttypeid  = " + LOANConstant.TransDiscountType.REPURCHASE );
				sb.append( " order by a.dtrepurchasedate desc");
				 
				//StringBuffer buffer = new StringBuffer();
				/*sb.append("select a.*,(SYSDATE-d.REPURCHASEDATE ) as dtCount, b.scontractcode, b.dtstartdate, b.dtenddate, b.mloanamount,b.discountclientname, b.MDISCOUNTRATE DiscountRate, b.dtDiscountDate DiscountDate, b.NDISCOUNTTYPEID DiscountTypeID, b.NREPURCHASETYPEID RepurchaseTypeID, b.NINOROUT INOROUT,");
				sb.append("b.ndiscounttypeid,b.mchargerate,b.nintervalnum,b.ninorout,b.purchaserinterestrate,b.nbankacceptpo,b.nbizacceptpo, d.REPURCHASEDATE billREPURCHASEDATE,d.REPURCHASETERM billREPURCHASETERM,d.ISLOCAL billIsLocal,d.ADDDAYS billAddDays,d.CHECKAMOUNT billCheckAmount,b.mcheckamount ");
				sb.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, RTRANSDISCOUNTCREDENCEBILL c, RTRANSDISCOUNTCONTRACTBILL d, LOAN_DISCOUNTCREDENCE e ");
				sb.append(
					" where d.transdiscountcontractid = b.ID and a.ID = c.DISCOUNTCONTRACTBILLID and a.id = d.DISCOUNTCONTRACTBILLID and d.transdiscountcontractid=e.ncontractid and c.transdiscountcredenceid =e.id ");
				//buffer.append(" and c.transdiscountcredenceid = " + lTransDiscountCredenceID);
				sb.append(" and b.ninorout=" + LOANConstant.TransDiscountInOrOut.IN);
				sb.append(" and b.ndiscounttypeid=" + LOANConstant.TransDiscountType.REPURCHASE);
				sb.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				//sb.append( "and ( SYSDATE - d.REPURCHASEDATE ) <= 1");
				sb.append( "and ( SYSDATE - d.REPURCHASEDATE ) >="+ -info.getAheadAwakeDays1()[REMINT_ZTX_INVEST_REPURCHASE]+ "");
				sb.append( " order by d.REPURCHASEDATE desc");*/
				log4j.info(" 转贴现买入（回购）到期提醒: \n" + sb.toString());
				System.out.println(sb.toString());

				String strSQL = sb.toString();
				//log.debug(strSQL);
				ps = info.getCon().prepareStatement(sb.toString());
				
				
				//ps.setLong(1, info.getCurrencyID());
				//ps.setLong(2, info.getOfficeID());
				int i = 0;
				//int lTotal = 0; //笔数
				rs = ps.executeQuery();
				while (rs.next())
				{
					
					i++;//笔数
					int nDays = 0;
					TransDiscountContractInfo tdinfo= new TransDiscountContractInfo();
					billInfo = new TransDiscountContractBillInfo();
					lCount = rs.getLong("dtCount");
					
					tdinfo.setRepurchasedate(rs.getTimestamp("dtrepurchasedate"));//回购日期
			        tdinfo.setInOrOut(rs.getLong("NINOROUT"));//买入卖出
			        tdinfo.setDiscountDate(rs.getTimestamp("dtdiscountdate"));//转贴现日
			        tdinfo.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//转贴现回购类型
			        tdinfo.setDiscountRate(rs.getDouble("MDISCOUNTRATE"));//贴现利率
			        tdinfo.setContractCode(rs.getString("scontractcode"));//转贴现合同编号
			        
			       
			        tdinfo.setEndDate(rs.getTimestamp("dtenddate"));//贷款结束日期
			        tdinfo.setStartDate(rs.getTimestamp("dtstartdate"));//贷款开始时间
			        tdinfo.setDiscountTypeId(rs.getLong("ndiscounttypeid"));//转贴现类型	        
			        tdinfo.setLoanAmount(rs.getDouble("mloanamount"));//贷款金额          
				
					
					v.add(tdinfo);
				}
				//lijunli 添加count判断   add
				
				if (lCount >= -info.getAheadAwakeDays1()[REMINT_ZTX_INVEST_REPURCHASE] && info.getAheadAwakeDays1()[REMINT_ZTX_INVEST_REPURCHASE] > 0 &&  i > 0 )
		 //&& lCount<=-(info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]-info.getAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]))
			{
			
			sResult += "共有&nbsp;<a href=remind/RemindDiscountBuy_v.jsp";//转贴现卖出回购
	        sResult += " target=_blank>&nbsp;";
	        sResult +=i;
	       // sResult += sScode;
	        sResult += "&nbsp;</a>";
	        sResult += "笔转贴现买入（回购）将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
		    //sResult += "将于" + DataFormat.formatDate(tsEndDate) + "到期&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			//已过期是否提醒
       
				rs.close();
				rs = null;

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

			}
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new LoanDAOException("查询失败", e);
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
		        } catch (Exception e)
		        {
		            // TODO Auto-generated catch block
		        	 e.printStackTrace();
		             throw new Exception(e.getMessage());
		        }
		    }
			return sResult;
			
		}
	 
	 
	 
	 /**
		 * 获取转贴现卖出（回购）的提醒信息   lijunli 2010.8.23
	 * @throws Exception 
		 */
	 public String findZTXAveragRepurchase(CraAwakeCondition info) throws Exception
		{
		 String sResult = "";
		 PreparedStatement ps = null;
		 Vector v = new Vector();
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			TransDiscountContractBillInfo billInfo = null;
			long lCount = 0; //到期天数
			String  sScode = "";//转贴现汇票号码
			long  lBillID=-1;//汇票ID
			double dAmount = 0; //贷款金额
			Timestamp tsEndDate = null; //到期日期
			
			try
			{
				//initDAO();
				sb.append("select b.*, a.dtrepurchasedate,(SYSDATE - a.dtrepurchasedate) as dtCount" );
				sb.append(" from cra_loanform a, loan_contractform b");
				sb.append(" where b.nloanid = a.id");
				
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				sb.append(" and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT );
				sb.append(" and b.ndiscounttypeid  = " + LOANConstant.TransDiscountType.REPURCHASE );
				sb.append( " order by a.dtrepurchasedate desc");
				//StringBuffer buffer = new StringBuffer();
				/*sb.append("select a.*,(SYSDATE-d.REPURCHASEDATE ) as dtCount, b.scontractcode, b.dtstartdate, b.dtenddate, b.mloanamount,b.discountclientname, b.MDISCOUNTRATE DiscountRate, b.dtDiscountDate DiscountDate, b.NDISCOUNTTYPEID DiscountTypeID, b.NREPURCHASETYPEID RepurchaseTypeID, b.NINOROUT INOROUT,");
				sb.append("b.ndiscounttypeid,b.mchargerate,b.nintervalnum,b.ninorout,b.purchaserinterestrate,b.nbankacceptpo,b.nbizacceptpo, d.REPURCHASEDATE billREPURCHASEDATE,d.REPURCHASETERM billREPURCHASETERM,d.ISLOCAL billIsLocal,d.ADDDAYS billAddDays,d.CHECKAMOUNT billCheckAmount,b.mcheckamount ");
				sb.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, RTRANSDISCOUNTCREDENCEBILL c, RTRANSDISCOUNTCONTRACTBILL d, LOAN_DISCOUNTCREDENCE e ");
				sb.append(
					" where d.transdiscountcontractid = b.ID and a.ID = c.DISCOUNTCONTRACTBILLID and a.id = d.DISCOUNTCONTRACTBILLID and d.transdiscountcontractid=e.ncontractid and c.transdiscountcredenceid =e.id ");
				//buffer.append(" and c.transdiscountcredenceid = " + lTransDiscountCredenceID);
				sb.append(" and b.ninorout=" + LOANConstant.TransDiscountInOrOut.OUT);
				sb.append(" and b.ndiscounttypeid=" + LOANConstant.TransDiscountType.REPURCHASE);
				sb.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
				sb.append(" and( b.nstatusid =" + SECConstant.ContractStatus.ACTIVE);
		        sb.append(" or b.nstatusid =" + SECConstant.ContractStatus.NOTACTIVE);
		        sb.append( "  or b.nstatusid =" + SECConstant.ContractStatus.EXTEND+")");
				//sb.append( "and ( SYSDATE - d.REPURCHASEDATE ) <= 1");
				sb.append( "and ( SYSDATE - d.REPURCHASEDATE ) >="+ -info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]+ "");
				sb.append( " order by d.REPURCHASEDATE desc");*/
				log4j.info(" 转贴现卖出（回购）到期提醒: \n" + sb.toString());
				System.out.println(sb.toString());

				String strSQL = sb.toString();
				//log.debug(strSQL);
				ps = info.getCon().prepareStatement(sb.toString());
				
				
				//ps.setLong(1, info.getCurrencyID());
				//ps.setLong(2, info.getOfficeID());
				int i = 0;
				//int lTotal = 0; //笔数
				rs = ps.executeQuery();
				while (rs.next())
				{
					
					i++;//笔数
					int nDays = 0;
					TransDiscountContractInfo tdinfo= new TransDiscountContractInfo();
					billInfo = new TransDiscountContractBillInfo();
					lCount = rs.getLong("dtCount");
					
					
					tdinfo.setRepurchasedate(rs.getTimestamp("dtrepurchasedate"));//回购日期
			        tdinfo.setInOrOut(rs.getLong("NINOROUT"));//买入卖出
			        tdinfo.setDiscountDate(rs.getTimestamp("dtdiscountdate"));//转贴现日
			        tdinfo.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//转贴现回购类型
			        tdinfo.setDiscountRate(rs.getDouble("MDISCOUNTRATE"));//贴现利率
			        tdinfo.setContractCode(rs.getString("scontractcode"));//转贴现合同编号
			        
			       
			        tdinfo.setEndDate(rs.getTimestamp("dtenddate"));//贷款结束日期
			        tdinfo.setStartDate(rs.getTimestamp("dtstartdate"));//贷款开始时间
			        tdinfo.setDiscountTypeId(rs.getLong("ndiscounttypeid"));//转贴现类型	        
			        tdinfo.setLoanAmount(rs.getDouble("mloanamount"));//贷款金额          
				
					v.add(tdinfo);
				}
				//lijunli 添加count判断   add
				
				if (lCount >= -info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE] && info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE] > 0 &&  i > 0 )
		 //&& lCount<=-(info.getAheadAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]-info.getAwakeDays1()[REMINT_ZTX_AVERAGE_REPURCHASE]))
			{
			
			sResult += "共有&nbsp;<a href=remind/RemindDiscountSell_v.jsp";//转贴现卖出回购
	        sResult += " target=_blank>&nbsp;";
	        sResult +=i;
	       // sResult += sScode;
	        sResult += "&nbsp;</a>";
	        sResult += "笔转贴现卖出（回购）将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
		    //sResult += "将于" + DataFormat.formatDate(tsEndDate) + "到期&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			//已过期是否提醒
          
				rs.close();
				rs = null;

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

			}
			catch (Exception e)
			{
				Log.print(e.toString());
				throw new LoanDAOException("查询失败", e);
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
		        } catch (Exception e)
		        {
		            // TODO Auto-generated catch block
		        	 e.printStackTrace();
		             throw new Exception(e.getMessage());
		        }
		    }
			return sResult;
			//return (v.size() > 0 ? v : null);
		}
	 
	 
	
	 
	 
	/**   获取资产转让    合同卖出（回购）     
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws Exception 
	 *     lijunli   2010.8.23 */
	
	
	public String getRepurchaseNotify (CraAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
        
		long  lContractID = -1;
		String sContractCode = ""; //合同编号
		Timestamp tsEndDate = null; //到期日期
		double dAmount = 0; //贷款金额
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //到期天数
		try
		{
			sb.append(" SELECT ID, CODE,AMOUNT,TRANSACTIONENDDATE,");
			sb.append(" (SYSDATE-TRANSACTIONENDDATE) as dtCount");
			sb.append(" FROM  SEC_APPLYCONTRACT ");
			sb.append(" WHERE (TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+")");
			sb.append(" AND ( STATUSID =" + SECConstant.ContractStatus.ACTIVE);
            sb.append(" or STATUSID =" + SECConstant.ContractStatus.NOTACTIVE);
            sb.append( "  or STATUSID =" + SECConstant.ContractStatus.EXTEND+")");
            sb.append( " AND (SYSDATE-TRANSACTIONENDDATE) >=" + -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY] + "");
            sb.append(" AND CURRENCYID = ?");
			sb.append(" AND OFFICEID = ?");
			sb.append(" ORDER BY CODE");

			log4j.info(" 资产转让合同卖出（回购）到期提醒: \n" + sb.toString());
			System.out.println(sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i = 0;
			rs = ps.executeQuery();
			while (rs.next())
			{
			    i++;//笔数
				lContractID = rs.getLong("ID");
				tsEndDate = rs.getTimestamp("TRANSACTIONENDDATE");
				sContractCode = rs.getString("CODE");
				dAmount = rs.getDouble("AMOUNT");
				lCount = rs.getLong("dtCount");

				

			}
			
			if ( lCount >= -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY]  && info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY] > 0 && i > 0)
			//&& lCount<=-(info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY]-info.getAwakeDays1()[REMINT_REPURCHASE_NOTIFY]))
			{
				//sResult += "合同&nbsp;<a href= remind/RemindContract_v.jsp?lID="+lContractID;
				sResult += "共有&nbsp;<a href= remind/RemindContract_v.jsp?lID="+lContractID;
				sResult += " target=_blank>&nbsp;";
				sResult += i;
				//sResult += sContractCode;
				sResult += "&nbsp;</a>";
				sResult += "笔合同卖出（回购）将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			/*else if (dAmount > 0 && lCount > 1 )
				//&& lCount<= info.getAwakeDays1()[REMINT_REPURCHASE_NOTIFY]-info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY])
			{
				//sResult += "合同&nbsp;<a href= remind/RemindContract_v.jsp?lID="+lContractID;
				sResult += "共有&nbsp;<a href= remind/RemindContract_v.jsp?lID="+lContractID;
				sResult += " target=_blank>&nbsp;";
				//sResult += sContractCode;
				sResult += i;
				sResult += "&nbsp;</a>";
				sResult += "笔合同卖出（回购）已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			}*/
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
	
	
	
	/**
	 * 仅供合同卖出（回购）到期查询页面使用   lijunli 2010.8.27
 * @throws Exception 
	 */
 public Collection getRepurchaseNotify1(CraAwakeCondition info) throws Exception
 {
	 	Connection conn = null;
	    PreparedStatement ps = null;
	    Vector v = new Vector();
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		Timestamp tsCurrent = Env.getSystemDateTime();
		SecuritiesContractInfo ContractInfo =null;		
		//long lCount = 0; //到期天数
		//String  sScode = "";//转贴现汇票号码
		//long  lBillID=-1;//汇票ID
		//double dAmount = 0; //贷款金额
		//Timestamp tsEndDate = null; //到期日期
		//int lTotal =0;//笔数
		long[] lAheadAwakeDays1;
		try
		{
			conn = Database.getConnection();
			info.setCon(conn);
			getAwakeSetting(info);	
			sb.append(" select t.*,(SYSDATE-TRANSACTIONENDDATE) as dtCount  from sec_applycontract t ");
            sb.append(" WHERE TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY);
		
	        sb.append(" AND ( STATUSID =" + SECConstant.ContractStatus.ACTIVE +"");
	        sb.append(" or STATUSID =" + SECConstant.ContractStatus.NOTACTIVE);
	        sb.append( "  or STATUSID =" + SECConstant.ContractStatus.EXTEND +")");
	        //sb.append( " and (SYSDATE - TRANSACTIONENDDATE) <= 1");             
	        sb.append( " AND (SYSDATE-TRANSACTIONENDDATE) >=" + -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFY] + "");       
			sb.append(" AND CURRENCYID = ?");
			sb.append(" AND OFFICEID = ?");
			sb.append(" ORDER BY t.TRANSACTIONENDDATE desc");		
			log4j.info(" 合同卖出（回购）到期提醒查询1111: \n" + sb.toString());
			System.out.println(sb.toString());
			String strSQL = sb.toString();
			//log.debug(strSQL);
			ps = conn.prepareStatement(sb.toString());	
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i = 0;
			rs = ps.executeQuery();
		       while (rs.next())
		      {
		        i++;
				int nDays = 0;
				//lCount = rs.getLong("dtCount"); //到期天数
				//dAmount =rs.getDouble("MAMOUNT");//贷款金额
				//tsEndDate = rs.getTimestamp("billREPURCHASEDATE");//到期日期
				//sScode =rs.getString("SCODE");//转贴现汇票号码
				
						
				ContractInfo =new SecuritiesContractInfo();
				//ContractInfo.setClientName(rs.getString("ClientName"));   //客户名称
				ContractInfo.setAmount(rs.getDouble("Amount"));	//回购金额		
				ContractInfo.setTransactionEndDate(rs.getTimestamp("TransactionEndDate"));//合同结束日期
				ContractInfo.setRate(rs.getDouble("Rate"));	//利率				
				ContractInfo.setTransactionTypeId(rs.getLong("TransactionTypeId"));//交易类型
				ContractInfo.setCode(rs.getString("Code"));//合同编号
				ContractInfo.setTransactionStartDate(rs.getTimestamp("TransactionStartDate"));//合同开始日期	
				ContractInfo.setInputUserId(rs.getLong("InputUserId"));//合同录入人
				ContractInfo.setInputDate(rs.getTimestamp("InputDate"));//合同录入日期
									
				
				v.add(ContractInfo);
		      }
				 
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
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
		         if (conn != null) {
					conn.close();
					conn = null;
				}
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	        	 e.printStackTrace();
	             throw new Exception(e.getMessage());
	        }
	    }
		return (v.size() > 0 ? v : null);
	}
 
	
	
	
	
	
	/**
	 * 获取资金拆出（返款）业务的提醒信息  
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String getDeliveryOrderMsgSell(CraAwakeCondition info) throws RemoteException, Exception
	{
		 String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
        
		long  lDeliveryOrder = -1;
		String sDeliveryOrderCode = ""; //交割单编号
		Timestamp tsEndDate = null; //到期日期
		double dAmount = 0; //贷款金额
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //到期天数
		try
		{
			sb.append(" SELECT t.ID, t.Code,t.Amount,t.MaturityDate,");
			sb.append(" (SYSDATE-MaturityDate) as dtCount");
			sb.append(" FROM  SEC_DeliveryOrder t ");			
			sb.append(" where TRANSACTIONTYPEID ="+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+"");
			sb.append(" AND t.statusid >=" + SECConstant.DeliveryOrderStatus.CHECKED);
            sb.append(" and (SYSDATE-MaturityDate) >= "+ -info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY]+"");
            sb.append(" AND (select count(*) from SEC_DeliveryOrder a where a.TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY);
			sb.append("	and a.statusid >=" +SECConstant.DeliveryOrderStatus.CHECKED );
			sb.append(" and a.applyformid = t.applyformid) = 0 ");
            sb.append(" AND t.CurrencyID = ?");
			sb.append(" AND t.OfficeID = ?");
			sb.append(" ORDER BY t.CODE");

			log4j.info(" 资金拆出返款到期提醒: \n" + sb.toString());
			System.out.println("资金拆出返款到期提醒:"+sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i=0;
			rs = ps.executeQuery();
			while (rs.next())
			{     
				i++;//笔数
			    lDeliveryOrder = rs.getLong("ID");
				tsEndDate = rs.getTimestamp("MaturityDate");
				sDeliveryOrderCode = rs.getString("CODE");
				dAmount = rs.getDouble("Amount");
				lCount = rs.getLong("dtCount");
				

			}
			if ( lCount >= -info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY] && info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY] > 0 && i > 0 )
			   // && lCount<=-(info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY]-info.getAwakeDays1()[REMINT_CAPITAL_OUT_REPAY]))
			   {
				sResult += "共有&nbsp;<a href=remind/RemindFundSell_v.jsp";							   
			    sResult += " target=_blank>&nbsp;";
			    sResult +=i;
			    sResult += "&nbsp;</a>";
			    sResult += "笔资金拆出返款将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			   }
			   //已过期是否提醒
			  /* else if (dAmount > 0 && lCount > 1) 
					   //&&  lCount<= info.getAwakeDays1()[REMINT_CAPITAL_OUT_REPAY]-info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY])
			   {
			    sResult += "共有&nbsp;<a href=remind/RemindFundSell_v.jsp";
			    sResult += " target=_blank>&nbsp;";
			   //sResult += sDeliveryOrderCode;
			    sResult += i;
			    sResult += "&nbsp;</a>";
			    sResult += "笔资金拆出返款已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			   }*/
			
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
	
	/**
	 * 仅供资金拆出（返款）到期查询页面使用   lijunli 2010.8.27
 * @throws Exception 
	 */
 public Collection getDeliveryOrderMsgSell1(CraAwakeCondition info) throws Exception
 {
	 	Connection conn = null;
	    PreparedStatement ps = null;
	    Vector v = new Vector();
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		Timestamp tsCurrent = Env.getSystemDateTime();
		DeliveryOrderInfo orderInfo = new DeliveryOrderInfo();	
		long lCount = 0; //到期天数
		String  sScode = "";//转贴现汇票号码
		long  lBillID=-1;//汇票ID
		double dAmount = 0; //贷款金额
		Timestamp tsEndDate = null; //到期日期
		//int lTotal =0;//笔数
		long[] lAheadAwakeDays1;
	
		try
		{
			
			conn = Database.getConnection();
			info.setCon(conn);
			getAwakeSetting(info);
							
		//sb.append(" SELECT ID, CODE,Amount,MaturityDate, ");
	    sb.append(" SELECT t.*, ");
		sb.append(" (SYSDATE-MaturityDate) as dtCount");
		sb.append(" FROM  SEC_DeliveryOrder t");		
		sb.append("  WHERE TRANSACTIONTYPEID ="+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+"");
		sb.append(" AND t.statusid >=" + SECConstant.DeliveryOrderStatus.CHECKED);
        sb.append( "  AND (SYSDATE-MaturityDate) >=" + -info.getAheadAwakeDays1()[REMINT_CAPITAL_OUT_REPAY]+ "");
        sb.append(" AND (select count(*) from SEC_DeliveryOrder a where a.TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY);
		sb.append("	and a.statusid >=" +SECConstant.DeliveryOrderStatus.CHECKED );
		sb.append(" and a.applyformid = t.applyformid) = 0 ");        
		sb.append(" AND t.CURRENCYID = ?");
		sb.append(" AND t.OFFICEID = ?");
		sb.append( " order by t.MaturityDate desc ");
		//sb.append(" ORDER BY CODE");		
		log4j.info(" 供资金拆出（返款）到期查询页面使用: \n" + sb.toString());
		System.out.println(" 供资金拆出（返款）到期查询页面使用: \n"+ sb.toString());
		String strSQL = sb.toString();
		//log.debug(strSQL);
		ps = conn.prepareStatement(sb.toString());	
		ps.setLong(1, info.getCurrencyID());
		ps.setLong(2, info.getOfficeID());
		int i = 0;
		rs = ps.executeQuery();
		       while (rs.next())
		      {
		        i++;
				int nDays = 0;
				/*lCount = rs.getLong("dtCount"); //到期天数
				dAmount =rs.getDouble("MAMOUNT");//贷款金额
				tsEndDate = rs.getTimestamp("billREPURCHASEDATE");//到期日期
				sScode =rs.getString("SCODE");//转贴现汇票号码
*/				
						
				orderInfo = new DeliveryOrderInfo();
				orderInfo.setClientId(rs.getLong("clientId"));   //客户ID
				orderInfo.setCode(rs.getString("code"));//交割单编号
				orderInfo.setId(rs.getLong("id"));//交割单编号Id
				orderInfo.setValueDate(rs.getTimestamp("valueDate"));//起息日
				orderInfo.setMaturityDate(rs.getTimestamp("maturityDate"));//还款日
				orderInfo.setRate(rs.getDouble("rate"));//利率
				orderInfo.setTerm(rs.getLong("term"));//回购期限
				orderInfo.setCounterpartId(rs.getLong("counterpartId"));//交易对手id
				orderInfo.setSuspenseInterest(rs.getDouble("suspenseInterest"));//应计利息
				orderInfo.setSystemTransactionCode(rs.getString("systemTransactionCode"));//交易系统成交编号 
				orderInfo.setTransactionTypeId(rs.getLong("transactionTypeId"));//交易类型编号Id
				orderInfo.setDeliveryDate(rs.getTimestamp("deliveryDate"));//交割日
				orderInfo.setInputUserId(rs.getLong("inputUserId"));//录入人
				orderInfo.setAmount(rs.getDouble("amount"));//拆借金额
				orderInfo.setStatusId(rs.getLong("StatusId"));//状态
				orderInfo.setTax(rs.getDouble("tax"));//交易费（税费）
				orderInfo.setInterest(rs.getDouble("Interest"));//实计利息
				orderInfo.setMaturityAmount(rs.getDouble("maturityAmount"));//到期还款金额
				orderInfo.setTransactionDate(rs.getTimestamp("TransactionDate"));//成交日
													
				

				v.add(orderInfo);
		      }
 
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
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
		         if (conn != null) {
					conn.close();
					conn = null;
				}
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	        	 e.printStackTrace();
	             throw new Exception(e.getMessage());
	        }
	    }
		return (v.size() > 0 ? v : null);
	}
	
	/**
	 * 获取资金拆入（返款）业务的提醒信息  
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String getDeliveryOrderMsgBuy(CraAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
        
		long  lDeliveryOrder = -1;
		String sDeliveryOrderCode = ""; //交割单编号
		Timestamp tsEndDate = null; //到期日期
		double dAmount = 0; //贷款金额
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //到期天数
		try
		{
			sb.append(" SELECT t.ID, t.CODE,t.Amount,t.MaturityDate,");
			sb.append(" (SYSDATE-MaturityDate) as dtCount");
			sb.append(" FROM  SEC_DeliveryOrder t");
			sb.append(" WHERE (TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+ ")");			
			sb.append(" AND t.statusid >=" + SECConstant.DeliveryOrderStatus.CHECKED);			
			sb.append(" AND (SYSDATE-MaturityDate) >="+ -info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY]+"");			
			sb.append(" AND (select count(*) from SEC_DeliveryOrder a where a.TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY);
			sb.append("	and a.statusid >=" +SECConstant.DeliveryOrderStatus.CHECKED );
			sb.append(" and a.applyformid = t.applyformid) = 0 ");
			sb.append(" AND t.CurrencyID = ?");
			sb.append(" AND t.OfficeID = ?");
			sb.append(" ORDER BY t.CODE");

			log4j.info(" 资金拆入返款到期提醒: \n" + sb.toString());
			System.out.println("资金拆入返款到期提醒:"+sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i = 0;
			rs = ps.executeQuery();
			while (rs.next())
			{      
				i++;
			    lDeliveryOrder = rs.getLong("ID");
				tsEndDate = rs.getTimestamp("MaturityDate");
				sDeliveryOrderCode = rs.getString("CODE");
				dAmount = rs.getDouble("Amount");
				lCount = rs.getLong("dtCount");
				

			}
			
			if ( lCount >= -info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY] && info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY] > 0  &&  i > 0 )
				//&& lCount<=-(info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY]-info.getAwakeDays1()[REMINT_CAPITAL_IN_REPAY]))
			 {
				sResult += "共有&nbsp;<a href=remind/RemindFundBuy_v.jsp";
				sResult += " target=_blank>&nbsp;";
				//sResult += sDeliveryOrderCode;
				sResult += i;
				sResult += "&nbsp;</a>";
				sResult += "笔资金拆入返款将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			 }
			//已过期是否提醒
			/*else if (dAmount > 0 && lCount > 1 )
					//&& lCount<= info.getAwakeDays1()[REMINT_CAPITAL_IN_REPAY]-info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY])
			{
				sResult += "共有&nbsp;<a href=remind/RemindFundBuy_v.jsp";
				sResult += " target=_blank>&nbsp;";
				//sResult += sDeliveryOrderCode;
				sResult += i; 
				sResult += "&nbsp;</a>";
				sResult += "笔资金拆入返款已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
			}*/

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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
	
	/**
	 * 仅供资金拆入（返款）到期查询页面使用   lijunli 2010.8.27
 * @throws Exception 
	 */
 public Collection getDeliveryOrderMsgBuy1(CraAwakeCondition info) throws Exception
 {
	 	Connection conn = null;
	    PreparedStatement ps = null;
	    Vector v = new Vector();
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		Timestamp tsCurrent = Env.getSystemDateTime();
		DeliveryOrderInfo orderInfo = new DeliveryOrderInfo();	
		long lCount = 0; //到期天数
		String  sScode = "";//转贴现汇票号码
		long  lBillID=-1;//汇票ID
		double dAmount = 0; //贷款金额
		Timestamp tsEndDate = null; //到期日期
		//int lTotal =0;//笔数
		long[] lAheadAwakeDays1;
		try
		{
		conn = Database.getConnection();
		info.setCon(conn);
		getAwakeSetting(info);
						
		sb.append(" SELECT t.*, ");
		sb.append(" (SYSDATE-MaturityDate) as dtCount");
		sb.append(" FROM  SEC_DeliveryOrder t");
		sb.append(" WHERE (TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+ ")");		
		sb.append(" AND t.statusid >=" + SECConstant.DeliveryOrderStatus.CHECKED);
     	sb.append(" AND (SYSDATE-MaturityDate) >="+ -info.getAheadAwakeDays1()[REMINT_CAPITAL_IN_REPAY]+"");
		sb.append(" AND (select count(*) from SEC_DeliveryOrder a where a.TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY);
		sb.append("	and a.statusid >=" +SECConstant.DeliveryOrderStatus.CHECKED );
		sb.append(" and a.applyformid = t.applyformid) = 0 ");
		sb.append(" AND t.CURRENCYID = ?");
		sb.append(" AND t.OFFICEID = ?");
		sb.append(" order by t.MaturityDate desc ");	
		log4j.info(" 供资金拆入（返款）到期查询页面使用: \n" + sb.toString());
		System.out.println(sb.toString());
		String strSQL = sb.toString();
		//log.debug(strSQL);
		ps = conn.prepareStatement(sb.toString());	
		ps = info.getCon().prepareStatement(sb.toString());
		ps.setLong(1, info.getCurrencyID());
		ps.setLong(2, info.getOfficeID());
		int i = 0;
		rs = ps.executeQuery();
		       while (rs.next())
		      {
		        i++;
				int nDays = 0;
				/*lCount = rs.getLong("dtCount"); //到期天数
				dAmount =rs.getDouble("MAMOUNT");//贷款金额
				tsEndDate = rs.getTimestamp("billREPURCHASEDATE");//到期日期
				sScode =rs.getString("SCODE");//转贴现汇票号码
*/				
			
				orderInfo = new DeliveryOrderInfo();
				orderInfo.setClientId(rs.getLong("clientId"));   //客户ID
				orderInfo.setCode(rs.getString("code"));//交割单编号
				orderInfo.setId(rs.getLong("id"));//交割单编号Id
				orderInfo.setValueDate(rs.getTimestamp("valueDate"));//起息日
				orderInfo.setMaturityDate(rs.getTimestamp("maturityDate"));//还款日
				orderInfo.setRate(rs.getDouble("rate"));//利率
				orderInfo.setTerm(rs.getLong("term"));//回购期限
				orderInfo.setCounterpartId(rs.getLong("counterpartId"));//交易对手id
				orderInfo.setSuspenseInterest(rs.getDouble("suspenseInterest"));//应计利息
				orderInfo.setSystemTransactionCode(rs.getString("systemTransactionCode"));//交易系统成交编号 
				orderInfo.setTransactionTypeId(rs.getLong("transactionTypeId"));//交易类型编号Id
				orderInfo.setDeliveryDate(rs.getTimestamp("deliveryDate"));//交割日
				orderInfo.setInputUserId(rs.getLong("inputUserId"));//录入人
				orderInfo.setAmount(rs.getDouble("amount"));//拆借金额
				orderInfo.setStatusId(rs.getLong("StatusId"));//状态
				orderInfo.setTax(rs.getDouble("tax"));//交易费（税费）
				orderInfo.setInterest(rs.getDouble("Interest"));//实计利息
				orderInfo.setMaturityAmount(rs.getDouble("maturityAmount"));//到期还款金额							
				orderInfo.setTransactionDate(rs.getTimestamp("TransactionDate"));//成交日									
			      	     
				v.add(orderInfo);
		
		      }
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
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
		         if (conn != null) {
					conn.close();
					conn = null;
				}
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	        	 e.printStackTrace();
	             throw new Exception(e.getMessage());
	        }
	    }
		return (v.size() > 0 ? v : null);
	}
 
 
 /**   获取资产转让    合同买入（回购）     
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws Exception 
	 *     lijunli   2010.8.23 */
	
	
	public String getRepurchaseNotifybuy (CraAwakeCondition info) throws RemoteException, Exception
	{
		String sResult = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
     
		long  lContractID = -1;
		String sContractCode = ""; //合同编号
		Timestamp tsEndDate = null; //到期日期
		double dAmount = 0; //贷款金额
		Timestamp tsCurrent = Env.getSystemDateTime();
		long lCount = 0; //到期天数
		try
		{
			sb.append(" SELECT ID, CODE,AMOUNT,TRANSACTIONENDDATE,");
			sb.append(" (SYSDATE-TRANSACTIONENDDATE) as dtCount");
			sb.append(" FROM  SEC_APPLYCONTRACT ");
			sb.append(" WHERE (TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+")");
			sb.append(" AND ( STATUSID =" + SECConstant.ContractStatus.ACTIVE);
         sb.append(" or STATUSID =" + SECConstant.ContractStatus.NOTACTIVE);
         sb.append( "  or STATUSID =" + SECConstant.ContractStatus.EXTEND+")");
         sb.append( " AND (SYSDATE-TRANSACTIONENDDATE) >=" + -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFYBUY] + "");
         sb.append(" AND CURRENCYID = ?");
			sb.append(" AND OFFICEID = ?");
			sb.append(" ORDER BY CODE");

			log4j.info(" 资产转让合同买入（回购）到期提醒: \n" + sb.toString());
			System.out.println(sb.toString());
			ps = info.getCon().prepareStatement(sb.toString());
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i = 0;
			rs = ps.executeQuery();
			while (rs.next())
			{
			    i++;//笔数
				lContractID = rs.getLong("ID");
				tsEndDate = rs.getTimestamp("TRANSACTIONENDDATE");
				sContractCode = rs.getString("CODE");
				dAmount = rs.getDouble("AMOUNT");
				lCount = rs.getLong("dtCount");

				

			}
			
			if ( lCount >= -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFYBUY]  && info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFYBUY] > 0 && i > 0)
			
			{
				//sResult += "合同&nbsp;<a href= remind/RemindContract_v.jsp?lID="+lContractID;
				sResult += "共有&nbsp;<a href= remind/RemindContractbuy_v.jsp?lID="+lContractID;
				sResult += " target=_blank>&nbsp;";
				sResult += i;
				//sResult += sContractCode;
				sResult += "&nbsp;</a>";
				sResult += "笔合同买入（回购）将到期/已到期！&nbsp;&nbsp;&nbsp;&nbsp;";
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return sResult;
	}
 
 
 /**
	 * 仅供合同买入（回购）到期查询页面使用   lijunli 2010.8.27
* @throws Exception 
	 */
public Collection getRepurchaseNotifybuy1(CraAwakeCondition info) throws Exception
{
	 	Connection conn = null;
	    PreparedStatement ps = null;
	    Vector v = new Vector();
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		Timestamp tsCurrent = Env.getSystemDateTime();
		SecuritiesContractInfo ContractInfo =null;		
		//long lCount = 0; //到期天数
		//String  sScode = "";//转贴现汇票号码
		//long  lBillID=-1;//汇票ID
		//double dAmount = 0; //贷款金额
		//Timestamp tsEndDate = null; //到期日期
		//int lTotal =0;//笔数
		long[] lAheadAwakeDays1;
		try
		{
			conn = Database.getConnection();
			info.setCon(conn);
			getAwakeSetting(info);	
			sb.append(" select t.*,(SYSDATE-TRANSACTIONENDDATE) as dtCount  from sec_applycontract t ");
         sb.append(" WHERE TRANSACTIONTYPEID =" +SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY);
		
	        sb.append(" AND ( STATUSID =" + SECConstant.ContractStatus.ACTIVE +"");
	        sb.append(" or STATUSID =" + SECConstant.ContractStatus.NOTACTIVE);
	        sb.append( "  or STATUSID =" + SECConstant.ContractStatus.EXTEND +")");
	        //sb.append( " and (SYSDATE - TRANSACTIONENDDATE) <= 1");             
	        sb.append( " AND (SYSDATE-TRANSACTIONENDDATE) >=" + -info.getAheadAwakeDays1()[REMINT_REPURCHASE_NOTIFYBUY] + "");       
			sb.append(" AND CURRENCYID = ?");
			sb.append(" AND OFFICEID = ?");
			sb.append(" ORDER BY t.TRANSACTIONENDDATE desc");		
			log4j.info(" 合同买入（回购）到期提醒查询1111: \n" + sb.toString());
			System.out.println(sb.toString());
			String strSQL = sb.toString();
			//log.debug(strSQL);
			ps = conn.prepareStatement(sb.toString());	
			ps.setLong(1, info.getCurrencyID());
			ps.setLong(2, info.getOfficeID());
			int i = 0;
			rs = ps.executeQuery();
		       while (rs.next())
		      {
		        i++;
				int nDays = 0;
				//lCount = rs.getLong("dtCount"); //到期天数
				//dAmount =rs.getDouble("MAMOUNT");//贷款金额
				//tsEndDate = rs.getTimestamp("billREPURCHASEDATE");//到期日期
				//sScode =rs.getString("SCODE");//转贴现汇票号码
				
						
				ContractInfo =new SecuritiesContractInfo();
				//ContractInfo.setClientName(rs.getString("ClientName"));   //客户名称
				ContractInfo.setAmount(rs.getDouble("Amount"));	//回购金额		
				ContractInfo.setTransactionEndDate(rs.getTimestamp("TransactionEndDate"));//合同结束日期
				ContractInfo.setRate(rs.getDouble("Rate"));	//利率				
				ContractInfo.setTransactionTypeId(rs.getLong("TransactionTypeId"));//交易类型
				ContractInfo.setCode(rs.getString("Code"));//合同编号
				ContractInfo.setTransactionStartDate(rs.getTimestamp("TransactionStartDate"));//合同开始日期	
				ContractInfo.setInputUserId(rs.getLong("InputUserId"));//合同录入人
				ContractInfo.setInputDate(rs.getTimestamp("InputDate"));//合同录入日期
									
				
				v.add(ContractInfo);
		      }
				 
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
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
		         if (conn != null) {
					conn.close();
					conn = null;
				}
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	        	 e.printStackTrace();
	             throw new Exception(e.getMessage());
	        }
	    }
		return (v.size() > 0 ? v : null);
	}


}
 

