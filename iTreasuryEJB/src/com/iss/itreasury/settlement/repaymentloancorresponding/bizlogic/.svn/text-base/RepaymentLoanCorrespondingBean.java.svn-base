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
import java.util.*;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.repaymentloancorresponding.dao.*;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.*;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author yychen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepaymentLoanCorrespondingBean
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    public RepaymentLoanCorrespondingBean()
	{
    	super();
	}
    
    /**
     * 
     * @throws SettlementException
     */
    public void cancelCorresponding(TransCurrentDepositInfo transInfo) throws SettlementException
    {
		Connection conn = null;
		try 
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);//为了事物处理
		} catch (Exception e) 
		{
			throw new SettlementException("无法取得数据库连结产生错误", e);
		}
    	GrantLoanDao gDao = new GrantLoanDao(conn);
    	RepaymentLoanDao rDao = new RepaymentLoanDao(conn);
    	Collection c = null;
    	LoanDetailInfo lInfo = new LoanDetailInfo();
    	RepaymentDetailInfo rInfo = new RepaymentDetailInfo();
    	try
		{
    		c = rDao.findRepaymentInfosByTransId(transInfo.getId());
    		if(c!=null&&c.size()>0)
    		{
    			Iterator iter = c.iterator();
    			while (iter.hasNext()) 
    			{
    				rInfo = (RepaymentDetailInfo)iter.next();
    				lInfo = gDao.findLoanDetailById(rInfo.getLoanDetailId());
    				//更新收款的余额
    				gDao.addBalance(lInfo,rInfo.getAmount());
    				//将对应的付款明细删除
    				rInfo.setStatus(SETTConstant.RecordStatus.INVALID);
    				rDao.update(rInfo);
    			}
    		}
    		conn.commit();
		}
    	catch(Exception e)
		{
			try 
			{
				conn.rollback();
			} catch (SQLException e2) 
			{
				throw new SettlementException("无法回滚", e);
			}
    		throw new SettlementException("上缴业务取消复核错误",e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					throw new SettlementException("关闭数据库错误",e);
				}
			}
		}
    }
    
    /**
     * 付款复核后对以前的收款进行抵消,如果抵消后有多的返回多余金额
     * @param transInfo
     * @throws SettlementException
     */
    public void corresponding(TransCurrentDepositInfo transInfo) throws SettlementException
    {
		Connection conn = null;
		try 
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);//为了事物处理
		} catch (Exception e) 
		{
			throw new SettlementException("无法取得数据库连结产生错误", e);
		}
    	GrantLoanDao gDao = new GrantLoanDao(conn);
    	RepaymentLoanDao rDao = new RepaymentLoanDao(conn);
    	Collection c = null;
    	LoanDetailInfo lInfo = new LoanDetailInfo();
    	RepaymentDetailInfo rInfo = new RepaymentDetailInfo();
    	double amount = transInfo.getAmount();//本次付款额
    	double min = 0;
    	//先按照日期排序查找收款明细
    	try
		{
    		c = gDao.findLoanDetail();
    		if(c!=null&&c.size()>0)
    		{
    			Iterator iter = c.iterator();
    			while (iter.hasNext()) 
    			{
    				lInfo = (LoanDetailInfo)iter.next();
    				rInfo.setTransId(transInfo.getId());
    				rInfo.setLoanDetailId(lInfo.getId());
    				rInfo.setStatus(SETTConstant.RecordStatus.VALID);
    				rInfo.setRepaymentDate(Env.getSystemDateTime());
    				//金额=min（贷款余额，上缴业务金额）
    				min = amount>lInfo.getBalance()?lInfo.getBalance():amount;
    				rInfo.setAmount(min);
    				//减少对应余额
    				gDao.reduceBalance(lInfo,min);
    				//新增付款记录
    				rDao.add(rInfo);
    				amount -= min;
    				if(amount<=0)
    				{
    					conn.commit();
    					return;
    				}
    			}
    		}
    		else
    		{
    			return;
    		}
		}
    	catch(Exception e)
		{
			try 
			{
				conn.rollback();
			} catch (SQLException e2) 
			{
				throw new SettlementException("无法回滚", e);
			}
    		throw new SettlementException("上缴业务复核错误",e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					throw new SettlementException("关闭数据库错误",e);
				}
			}
		}
    }
    
    public static void main(String args[])throws Exception
    {
    	TransCurrentDepositInfo transInfo = new TransCurrentDepositInfo();
    	transInfo.setId(1);
    	transInfo.setAmount(2000);
    	RepaymentLoanCorrespondingBean bean = new RepaymentLoanCorrespondingBean();
    	//bean.corresponding(transInfo);
    	bean.cancelCorresponding(transInfo);
    }
}
