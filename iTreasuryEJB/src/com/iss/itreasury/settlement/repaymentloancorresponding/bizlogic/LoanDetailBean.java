/*
 * Created on 2004-10-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.repaymentloancorresponding.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.repaymentloancorresponding.dao.GrantLoanDao;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.LoanDetailInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**  
 * @author fanyang  
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoanDetailBean
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public LoanDetailBean()
	{
		super();    
	}
	
	public long addLoanDetail(TransCurrentDepositInfo transInfo) throws SettlementException
	{
		Connection conn = null;
		try 
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);//为了事物处理
		} catch (Exception e) 
		{
			e.printStackTrace();throw new SettlementException("无法取得数据库连结产生错误", e);
		}
		long id = -1;
		GrantLoanDao dao = new GrantLoanDao(conn);
		LoanDetailInfo info = new LoanDetailInfo();
		try
		{
			info.setTransId(transInfo.getId());
			info.setBalance(transInfo.getAmount());
			info.setLoanDate(Env.getSystemDateTime());
			info.setStatus(SETTConstant.RecordStatus.VALID);
			id = dao.add(info);
			conn.commit();
		}
		catch (Exception e)
		{
			try 
			{
				conn.rollback();
			} catch (SQLException e2) 
			{
				e.printStackTrace();throw new SettlementException("无法回滚", e);
			}
			e.printStackTrace();throw new SettlementException("保存收款明细信息产生错误", e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					e.printStackTrace();throw new SettlementException("关闭数据库错误",e);
				}
			}
		}
		return id;
	}
	
    public void deleteLoanDetail(TransCurrentDepositInfo transInfo) throws SettlementException
    {
		Connection conn = null;
		try 
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);//为了事物处理
		} catch (Exception e) 
		{
			e.printStackTrace();throw new SettlementException("无法取得数据库连结产生错误", e);
		}
    	GrantLoanDao dao = new GrantLoanDao(conn);
    	LoanDetailInfo info = new LoanDetailInfo();
    	try
		{
    		info.setId(dao.findIdByTransId(transInfo.getId()));
    		info.setStatus(SETTConstant.RecordStatus.INVALID);
    		dao.update(info);
    		conn.commit();
		}
		catch (Exception e)
		{
			try 
			{
				conn.rollback();
			} catch (SQLException e2) 
			{
				e.printStackTrace();throw new SettlementException("无法回滚", e);
			}
			e.printStackTrace();throw new SettlementException("逻辑删除收款明细信息产生错误", e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					e.printStackTrace();throw new SettlementException("关闭数据库错误",e);
				}
			}
		}
    }
    
    public static void main(String args[])throws Exception
    {
    	TransCurrentDepositInfo transInfo = new TransCurrentDepositInfo();
    	transInfo.setId(1819);
    	transInfo.setAmount(234);
    	LoanDetailBean bean = new LoanDetailBean();
    	System.out.println("id="+bean.addLoanDetail(transInfo));
    	/*bean.deleteLoanDetail(transInfo);
    	System.out.println("over");*/
    }
	
}
