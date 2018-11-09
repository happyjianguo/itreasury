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
			conn.setAutoCommit(false);//Ϊ�����ﴦ��
		} catch (Exception e) 
		{
			throw new SettlementException("�޷�ȡ�����ݿ������������", e);
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
    				//�����տ�����
    				gDao.addBalance(lInfo,rInfo.getAmount());
    				//����Ӧ�ĸ�����ϸɾ��
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
				throw new SettlementException("�޷��ع�", e);
			}
    		throw new SettlementException("�Ͻ�ҵ��ȡ�����˴���",e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					throw new SettlementException("�ر����ݿ����",e);
				}
			}
		}
    }
    
    /**
     * ����˺����ǰ���տ���е���,����������ж�ķ��ض�����
     * @param transInfo
     * @throws SettlementException
     */
    public void corresponding(TransCurrentDepositInfo transInfo) throws SettlementException
    {
		Connection conn = null;
		try 
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);//Ϊ�����ﴦ��
		} catch (Exception e) 
		{
			throw new SettlementException("�޷�ȡ�����ݿ������������", e);
		}
    	GrantLoanDao gDao = new GrantLoanDao(conn);
    	RepaymentLoanDao rDao = new RepaymentLoanDao(conn);
    	Collection c = null;
    	LoanDetailInfo lInfo = new LoanDetailInfo();
    	RepaymentDetailInfo rInfo = new RepaymentDetailInfo();
    	double amount = transInfo.getAmount();//���θ����
    	double min = 0;
    	//�Ȱ���������������տ���ϸ
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
    				//���=min���������Ͻ�ҵ���
    				min = amount>lInfo.getBalance()?lInfo.getBalance():amount;
    				rInfo.setAmount(min);
    				//���ٶ�Ӧ���
    				gDao.reduceBalance(lInfo,min);
    				//���������¼
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
				throw new SettlementException("�޷��ع�", e);
			}
    		throw new SettlementException("�Ͻ�ҵ�񸴺˴���",e);
		}
		finally
		{
			if (conn != null){
				try{
					conn.close();
					conn = null;
				}catch(SQLException e){
					throw new SettlementException("�ر����ݿ����",e);
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
