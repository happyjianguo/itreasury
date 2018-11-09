package com.iss.itreasury.craftbrother.interest.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.craftbrother.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.craftbrother.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * 资产转让合同利息匡算处理业务对象
 *
 */
public class InterestEstimate {

	 /**
     * 取得数据库连接。
     * @return 数据库连接。
     */
	 private Connection getConnection() throws Exception
	 {
	        Connection con = null;
	        con = Database.getConnection();
	        return con;
	 }
	 
	 public Vector findEstimateRecords(Connection con, InterestEstimateQueryInfo queryInfo) throws Exception
	 {
		 Vector resultVec = new Vector(); //返回值

			Connection conInternal = null;
			if (con == null)
			{
				try
				{
					conInternal = getConnection();
					conInternal.setAutoCommit(false);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("无法取得数据库连接");
				}
			}
			else
			{
				conInternal = con;
			}
			
			try
			{
				Vector rstVec = new Vector();
				//获取需要匡算的记录
				rstVec = this.selectEstimateRecords(conInternal, queryInfo);
				if (rstVec.size() == 0)
				{
					return resultVec;
				}
				//算息
				getEstimateInterestResult(queryInfo, resultVec, conInternal, rstVec);
				if (con == null)
				{
					try
					{
						conInternal.commit();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eCommit)
					{
						throw new IException("事务提交异常");
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (con == null)
				{
					try
					{
						conInternal.rollback();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eRollback)
					{
						throw new IException("事务回退异常");
					}
				}
				throw e;
			}
			finally
			{
				if (con == null)
				{
					try
					{
						conInternal.close();
					}
					catch (Exception eClose)
					{
						;
					}
				}
			}

			return resultVec;
	 }
	 /**
	  * 获取要匡算利息的资产转让合同
	  * @param con
	  * @param qInfo
	  * @return
	  * @throws Exception
	  */
	public Vector selectEstimateRecords(Connection con, InterestEstimateQueryInfo qInfo) throws Exception
	{
		StringBuffer buffer = new StringBuffer();
		String strSQL = "";
		
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("无法取得数据库连接");
			}
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = null; //返回值
		
		buffer.append(" SELECT \n");
		buffer.append(" transactionTypeID, counterpartId, code, transactionStartDate, transactionEndDate,\n");
		buffer.append(" amount, incomeRate, receivedInterest, paidInterest \n");
		buffer.append(" from SEC_APPLYCONTRACT \n");
		buffer.append(" WHERE statusid = " + SECConstant.ContractStatus.ACTIVE + "\n");
		buffer.append(" and transactionTypeId in ("+CRAconstant.TransactionType.AVERAGE_NOTIFY+", "+CRAconstant.TransactionType.REPURCHASE_NOTIFY+") \n");
		
		if(qInfo.getTransactionTypeId() > -1)
		{
			buffer.append("and transactionTypeId = " + qInfo.getTransactionTypeId() + " \n");	
		}
		if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
		{                                    
			buffer.append("and code>='" + qInfo.getContractNoFrom() + "' \n");				
		}
		if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
		{                                    
			buffer.append("and code<='" + qInfo.getContractNoTo() + "' \n");				
		}		
		buffer.append("order by transactionTypeId");
		strSQL = buffer.toString();
		
		try
		{
			//设置连接参数
			ps = con.prepareStatement(strSQL);

			//执行查询
			rs = ps.executeQuery();

			//取得结果
			result = fetchDataFromRS(rs);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{ //释放资源
			rs.close();
			ps.close();
			if(con==null)
			{
				conInternal.close();
			}
		}

		return result;
		
	}
	
	/**
	 * 处理查询数据算息
	 * @param queryInfo     		查询条件 
	 * @param estimateResultVec     返回结果
	 * @param conInternal   		数据库联接
	 * @param estimateVec        	需要匡算的数据集合
	 * @throws IException
	 * @throws Exception
	 */
	private void getEstimateInterestResult(
			InterestEstimateQueryInfo queryInfo,
			Vector estimateResultVec,
			Connection conInternal,
			Vector estimateVec)
			throws  Exception
			{
				for (int i = 0; i < estimateVec.size(); i++)
				{
					int interestDays = 0;
					double interest = 0;
					InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
					resultInfo = (InterestEstimateQueryResultInfo) estimateVec.elementAt(i);	
					//转让日期与回购日期之间间隔天数
					interestDays = DataFormat.getIntervalDays(resultInfo.getContractStartDate(), queryInfo.getEstimateDate());
					//转让日期到回购日期之间的利息
					if(interestDays >= 2){
						interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(
								UtilOperation.Arith
										.mul(resultInfo.getAmount(), resultInfo.getRate()),
								interestDays-1), 36000);
						resultInfo.setInterestDays(interestDays-1);
						if(resultInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY)
						{
							resultInfo.setInterestReceived(interest - resultInfo.getInterestReceived());
						}
						if(resultInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY)
						{
							resultInfo.setInterestPayment(interest - resultInfo.getInterestPayment());
						}
						estimateResultVec.addElement(resultInfo);
					}else{
						continue;
					}
				}
			}
	
	/**
	 * 从结果集向数据实体赋值
	 */
	private Vector fetchDataFromRS(ResultSet rs) throws Exception
	{
		Vector rstVec = new Vector();		
		while (rs.next())
		{
			InterestEstimateQueryResultInfo info = new InterestEstimateQueryResultInfo();
			info.setTransactionTypeId(rs.getLong("transactionTypeID"));
			info.setCounterpartId(rs.getLong("counterpartId"));
			info.setContractNo(rs.getString("code"));
			info.setContractStartDate(rs.getTimestamp("transactionStartDate"));
			info.setContractEndDate(rs.getTimestamp("transactionEndDate"));
			info.setAmount(rs.getDouble("amount"));
			info.setRate(rs.getDouble("incomeRate"));	
			info.setInterestReceived(rs.getDouble("receivedInterest"));
			info.setInterestPayment(rs.getDouble("paidInterest"));
			
			rstVec.addElement(info);			
		}
		return rstVec;
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
