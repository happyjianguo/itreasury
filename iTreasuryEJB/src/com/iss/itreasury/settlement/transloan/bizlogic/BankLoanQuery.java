package com.iss.itreasury.settlement.transloan.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;


import com.iss.itreasury.dao.SettlementDAO;

import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;


/**
 * @author xrli
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BankLoanQuery extends SettlementDAO
{
	
	Log4j log = null;
	/**
	 * Constructor for Sett_TransInfoOfBankAccontDAO.
	 */
	public BankLoanQuery()
	{
		super();
		log = new Log4j(Constant.ModuleType.SETTLEMENT, this);		
	}

	/**
	 * 根据放款单查询提款记录
	 * 逻辑说明：
	 * 
	 * @param lId  放款单ID 
	 * @return Collection ,查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByFormID(long lId)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			
			
		
				buffer.append("select distinct payform.nContractID as contractID, \n");
				buffer.append("yt.nIsHead as isHead, \n");
				buffer.append("yt.SBANKNAME as bankName, \n");
				buffer.append("yt.nAttendBankID as bankID, \n");			
				buffer.append("yt.MCREDITAMOUNT+yt.MASSUREAMOUNT as loanAmount, \n");
				//buffer.append("yt.MRATE as Rate,draw.mAmount*yt.MRATE/100 as drawAmount, \n");
				buffer.append("yt.MRATE as Rate,bankDraw.mDrawAmount as    drawAmount, \n");
				buffer.append("bankDraw.MCHARGEAMOUNT as commission \n");
				//buffer.append("draw.mAmount*payform.MCOMMISSIONRATE/1000*yt.MRATE/100 as commission \n");
				//buffer.append("from LOAN_YT_DRAWFORM draw, LOAN_YT_LOANCONTRACTBANKASSIGN yt,LOAN_PAYFORM payform \n");
				buffer.append("from LOAN_YT_DRAWFORM draw, LOAN_YT_LOANCONTRACTBANKASSIGN yt,LOAN_PAYFORM payform,LOAN_YT_DRAWFORMBANKASSIGN bankDraw \n");
				buffer.append("where payform.nContractID=yt.nContractID \n");
				buffer.append("and  payform.NDRAWNOTICEID = draw.id \n");
				//后增加
				buffer.append("and draw.id = bankDraw.nDrawID \n");
				buffer.append("and yt.nIshead =   bankDraw.nIshead \n");
				buffer.append("and  payform.nContractID  =draw.nContractID \n");
				//过滤垃圾数据
				//buffer.append("and yt.MRATE >0 \n");
								
				buffer.append("and payform.id=  ? \n");
				buffer.append("order by contractID,isHead \n");
				

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, lId);											

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					BankLoanDrawInfo resultInfo = new BankLoanDrawInfo();
					
					resultInfo = getDrawInfo(resultInfo, rs);					
					arrResult.add(resultInfo);

				}			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;

	}
	

	/**
	 * 根据银行贷款放款单ID，查询本次放款中，牵头行(本系统指财务公司)所占放款金额的比重。
	 * 计算公式为：(牵头行的本次提款金额/本次放款总金额)*100。
	 * 
	 * @param lId 放款单ID
	 * @return
	 * @throws Exception
	 */
	public double findRateByFormID(long lId)	throws Exception 
	{
		double dRate = 0.0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			
			
		
				buffer.append("select distinct  \n");				
				buffer.append("bankDraw.mDrawAmount/payform.mamount*100 as Rate \n");				
				buffer.append("from LOAN_YT_DRAWFORM draw, LOAN_YT_LOANCONTRACTBANKASSIGN yt,LOAN_PAYFORM payform,LOAN_YT_DRAWFORMBANKASSIGN bankDraw  \n");
				buffer.append("where payform.nContractID=yt.nContractID \n");
				buffer.append("and  payform.NDRAWNOTICEID = draw.id \n");
				//	后增加
				buffer.append("and draw.id = bankDraw.nDrawID \n");
				buffer.append("and yt.nIshead =   bankDraw.nIshead \n");
				buffer.append("and  payform.nContractID  =draw.nContractID \n");
				//过滤垃圾数据
				buffer.append("and yt.MRATE >0 \n");
				buffer.append("and yt.nIsHead =1 \n");				
				buffer.append("and payform.id=  ? \n");				
				

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, lId);											

				rs = ps.executeQuery();
				if (rs.next()) 
				{					
					dRate=rs.getDouble("Rate");
				}			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return dRate;

	}
	
	/**
	 * 根据放款单查询财务公司承贷金额
	 * 逻辑说明：
	 * 
	 * @param lId  放款单ID 
	 * @return Collection ,查询结果实体类的记录集
	 * @throws Exception
	 */
	public double findAmountByFormID(long lId)	throws Exception 
	{
		double dAmount = 0.0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";			
		
				buffer.append("select distinct  \n");				
				buffer.append("draw.MDRAWAMOUNT as Amount \n");				
				buffer.append("from LOAN_YT_DRAWFORMBANKASSIGN draw, LOAN_PAYFORM payform \n");
				buffer.append("where  \n");
				buffer.append("  payform.NDRAWNOTICEID = draw.NDRAWID \n");				
				buffer.append("and  payform.nContractID  =draw.nContractID \n");					
				//过滤垃圾数据				
				buffer.append("and draw.nIsHead =1 \n");				
				buffer.append("and payform.id=  ? \n");				
				

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, lId);											

				rs = ps.executeQuery();
				if (rs.next()) 
				{					
					dAmount=rs.getDouble("Amount");
				}			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return dAmount;

	}
	
	/**
	 * 根据放款单查询银团贷款余额
	 * 逻辑说明：
	 * 
	 * @param lId  放款单ID 
	 * @return Collection ,查询结果实体类的记录集
	 * @throws Exception
	 */
	public double findBalanceByFormID(long lId)	throws Exception 
	{
		double dAmount = 0.0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";			
		
				buffer.append("select (d.mAmount - nvl(aa.mAmount,0)) Amount \n");				
				buffer.append("from \n");				
				buffer.append("(select nvl(sum(a.mAmount),0) mAmount,a.nFormID nFormID from \n");
				buffer.append("SETT_SYNDICATIONLOANINTEREST a, SETT_TRANSREPAYMENTLOAN b where \n");
				buffer.append(" b.id = a.nsyndicationLoanReceiveID   and  b.nStatusID in (2,3)   group by a.nFormID ) aa,  \n");
				buffer.append("LOAN_PAYFORM c,SETT_TRANSGRANTLOAN d  \n");
				
				buffer.append("where  \n");
				buffer.append("  c.id =aa.nFormID(+) \n");
				buffer.append("  and c.id =d.NLOANNOTEID \n");
				buffer.append("  and d.nStatusID in (" + SETTConstant.TransactionStatus.CHECK + "," + SETTConstant.TransactionStatus.SAVE + ") \n");
				buffer.append("and c.id=  ? \n");				
				

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, lId);											

				rs = ps.executeQuery();
				if (rs.next()) 
				{					
					dAmount=rs.getDouble("Amount");
				}			
				if(dAmount!=0)
				{
					return dAmount;
				}
				else
				{
					cleanup(rs);
					cleanup(ps);
					buffer = new StringBuffer();
					//状态查询条件							
				
					buffer.append("select mBalance/bankDraw.mDrawAmount*payform.mamount Balance \n");				
					buffer.append("from \n");					
					buffer.append("sett_subaccount sa,LOAN_YT_DRAWFORM draw, LOAN_YT_LOANCONTRACTBANKASSIGN yt,LOAN_PAYFORM payform,LOAN_YT_DRAWFORMBANKASSIGN bankDraw  \n");
					
					buffer.append("where  \n");	
					buffer.append("payform.nContractID=yt.nContractID \n");
					buffer.append("and  payform.NDRAWNOTICEID = draw.id \n");
					//	后增加
					buffer.append("and draw.id = bankDraw.nDrawID \n");
					buffer.append("and yt.nIshead =   bankDraw.nIshead \n");
					buffer.append("and  payform.nContractID  =draw.nContractID \n");
					//过滤垃圾数据
					buffer.append("and yt.MRATE >0 \n");
					buffer.append("and yt.nIsHead =1 \n");
					buffer.append("and sa.al_nLoanNoteId =payform.id \n");
					buffer.append("and sa.al_nLoanNoteId=  ? \n");	
					
					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());				
					index = 1;
					ps.setLong(index++, lId);											
						rs = ps.executeQuery();
					if (rs.next()) 
					{					
						dAmount=UtilOperation.Arith.round(rs.getDouble("Balance"),2);
					}	
				}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return dAmount;

	}
	
	/**
	 * 根据放款单ID查询银团贷款信息
	 * 逻辑说明：
	 * 
	 * @param lId  放款单ID 
	 * @return Collection ,查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByPayFormID(long lId)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			
			
		
				buffer.append("select distinct  yt.nContractID nContractID, \n");				
				buffer.append("yt.sbankName sBankName, \n");
				buffer.append("yt.nAttendBankID nAttendBankID, \n");
				buffer.append("yt.nIsHead  nIsHead, \n");
				buffer.append("yt.mrate  scale, \n");				
				buffer.append("bankDraw.mDrawAmount/payform.mamount*100 as mRate \n");
				
				buffer.append("from LOAN_YT_DRAWFORM draw, LOAN_YT_LOANCONTRACTBANKASSIGN yt,LOAN_PAYFORM payform,LOAN_YT_DRAWFORMBANKASSIGN bankDraw \n");
				buffer.append("where  \n");								
				buffer.append("payform.nContractID=yt.nContractID \n");
				buffer.append("and  payform.NDRAWNOTICEID = draw.id \n");
				buffer.append("and draw.id = bankDraw.nDrawID \n");
				buffer.append("and yt.nIshead =   bankDraw.nIshead \n");
				buffer.append("and  payform.nContractID  =draw.nContractID \n");
				buffer.append("and yt.MRATE >0 \n");
				buffer.append("and payform.id=  ? \n");
				buffer.append("order by      yt.nIsHead \n");
				

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, lId);											

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					SyndicationLoanInterestInfo resultInfo = new SyndicationLoanInterestInfo();
					resultInfo = getMemberInfo(resultInfo, rs);					
					//resultInfo.setRate(resultInfo.getScale());
					arrResult.add(resultInfo);

				}			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;

	}
	
	/**
	 * 设置结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private BankLoanDrawInfo getDrawInfo(
			BankLoanDrawInfo info,
		ResultSet rs)
		throws Exception {
		info = new BankLoanDrawInfo();
		try 
		{
			info.setContractID(rs.getLong("ContractID"));
			info.setBankName(rs.getString("BankName"));		
			info.setBankID(rs.getLong("bankID"));
			info.setIsHead(rs.getLong("IsHead"));				
			info.setLoanAmount(rs.getDouble("LoanAmount"));
			info.setRate(rs.getDouble("Rate"));			
			info.setDrawAmount(rs.getDouble("DrawAmount"));
			info.setCommission(rs.getDouble("Commission"));			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	/**
	 * 设置成员行
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	/**
	 * 设置结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private SyndicationLoanInterestInfo getMemberInfo(
			SyndicationLoanInterestInfo info,
		ResultSet rs)
		throws Exception {
		info = new SyndicationLoanInterestInfo();
		try 
		{
			info.setContractID(rs.getLong("nContractID"));
			info.setBankName(rs.getString("sBankName"));		
			info.setBankID(rs.getLong("nAttendBankID"));		
			info.setRate(rs.getDouble("mRate"));			
			info.setIsHead(rs.getLong("nIsHead"));
			info.setScale(rs.getDouble("scale"));
						
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}
}
