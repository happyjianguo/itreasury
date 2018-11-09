/*
 * Created on 2003-9-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

//import java.util.ArrayList;
//import java.util.Vector;
//import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
//import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
//import com.iss.itreasury.util.Constant;
//import com.iss.itreasury.util.Env;
//import com.iss.itreasury.util.Log4j;
//import junit.framework.TestCase;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/*
public class Sett_TransFixedWithDrawDAOTest extends TestCase 
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * Constructor for Sett_TransFixedWithDrawDAOTest.
	 * @param arg0
	 
	public Sett_TransFixedWithDrawDAOTest(String arg0) 
	{
		super(arg0);
	}
	public void test() throws Exception
	{
		try
		{			
			/*testAdd();	
					
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.getMessage());			  
		}
	}
	public void testAdd() throws Exception
	{
		System.out.println("****Begin method testAdd");
		try
		{
			long lTotalPages = 0;
			TransFixedDrawInfo info = new TransFixedDrawInfo();				
			info.setTransNo("1111");
			info.setOfficeID(1);
			info.setCurrencyID(1);
			info.setTransactionTypeID(1);
			info.setDepositNo("2003");
			info.setAmount(100);
			info.setTransactionTypeID(13);
			info.setAbstract("111111");
			info.setInputUserID(11);
			info.setModifyDate(Env.getSystemDate(1, 1));				
			info.setStartDate(Env.getSystemDate(1, 1));
			info.setExecuteDate(Env.getSystemDate(1, 1));
			info.setClientID(111);
			info.setBankID(11);
			info.setCashFlowID(1);
			
			TransFixedDrawInfo info1 = new TransFixedDrawInfo();				
			info1.setTransNo("2222");
			info1.setOfficeID(2);
			info1.setCurrencyID(2);
			info1.setStatusID(2);
			info1.setTransactionTypeID(13);
			info1.setInputUserID(222222);
			info1.setAccountID(2);
			info1.setClientID(222);
			info1.setDepositNo("2");
			info1.setAmount(200);
			info1.setInterestStartDate(Env.getSystemDate(1, 1));	
			info1.setCurrencyID(22);
			info1.setPayTypeID(2);
			info1.setBankID(2);
			info1.setReceiveInterestAccountID(222);
			info1.setInterestPayTypeID(2);
			info1.setInterestBankID(22);		
			info1.setModifyDate(Env.getSystemDate(1, 1));			
			info1.setStartDate(Env.getSystemDate(1, 1));
			info1.setExecuteDate(Env.getSystemDate(1, 1));
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			
			long c = dao.add(info);				
			System.out.println("****info"+c);
			if (c != 0)
			{				
				System.out.println("result size: " + c);
				long c1 = dao.add(info1);					
			}
			else
			{				
				System.out.println("result == null ");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}
	
	//修改测试
	public void testUpdate() throws Exception
	{
		System.out.println("****Begin method testUpdate");
		try
		{
			long lTotalPages = 0;
			TransFixedDrawInfo info = new TransFixedDrawInfo();
			info.setID(25);
			info.setTransNo("1111");
			info.setOfficeID(1);
			info.setCurrencyID(1);
			info.setTransactionTypeID(1);
			info.setAbstract("111111");
			info.setInputUserID(11);
			info.setModifyDate(Env.getSystemDate(1, 1));
			info.setAmount(500);
			info.setStartDate(Env.getSystemDate(1, 1));
			info.setExecuteDate(Env.getSystemDate(1, 1));
			info.setClientID(111);
			info.setBankID(11);
			info.setCashFlowID(1);
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
				
			long c = dao.update(info);
			System.out.println("****info"+c);
			if (c != 0)
			{				
				System.out.println("result size: " + c);					
			}
			else
			{				
				System.out.println("result == null ");
			}				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}
	//修改状态的测试	
	public void testUpdateStatus() throws Exception
	{
		System.out.println("****Begin method testUpdateStatus");
		try
		{
			long lTotalPages = 0;
			
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
				
			long c = dao.updateStatus(25,5);
			System.out.println("****info"+c);
			if (c != 0)
			{				
				System.out.println("result size: " + c);					
			}
			else
			{				
				System.out.println("result == null ");
			}
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}		
	//根据Id查找的测试
	public void testFindById() throws Exception
	{
		System.out.println("****Begin method testFindById");
		try
		{
			long lTotalPages = 0;			
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
				
			TransFixedDrawInfo info = dao.findByID(25);
			System.out.println("****info"+info.getTransNo());				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}
	//查找测试
	public void testFindByStatus() throws Exception
	{
		System.out.println("****Begin method testFindByStatus");
		try
		{
			long lTotalPages = 0;
			QueryByStatusConditionInfo info = new QueryByStatusConditionInfo();
			//处理还是复核
			info.setTypeID(0);			
			info.setOfficeID(1);
			info.setCurrencyID(1);
			info.setTransactionTypeID(1);
			long[] lStatusIDs = new long[2];
			lStatusIDs[0]=5;
			lStatusIDs[1]=2;
			info.setStatus(lStatusIDs);
			info.setStatusID(5);
			info.setUserID(11);
			info.setDate(Env.getSystemDate(1, 1));			
			
			
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
				
			ArrayList vec = (ArrayList)dao.findByStatus(info);			
			if (vec.size()== 0)
			{				
				System.out.println("result == null ");					
			}
			else
			{				
				System.out.println("result size: " + vec.size());
			}
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}
	//匹配测试
	public void testMatch() throws Exception
	{
		System.out.println("****Begin method testMatch");
		try
		{
			long lTotalPages = 0;
			TransFixedDrawInfo info = new TransFixedDrawInfo();			
			
			//交易类型						
			info.setTransNo("2222");
			info.setTransactionTypeID(13);
			info.setOfficeID(2);
			info.setCurrencyID(22);
			info.setStatusID(2);			
			info.setInputUserID(2222);
			info.setAccountID(2);
			info.setClientID(222);
			info.setDepositNo("2");
			info.setAmount(200);
			info.setInterestStartDate(Env.getSystemDate(1, 1));	
			info.setCurrentAccountID(-1);
			info.setPayTypeID(2);
			info.setBankID(2);
			info.setReceiveInterestAccountID(222);
			info.setInterestPayTypeID(2);
			info.setInterestBankID(22);		
			info.setModifyDate(Env.getSystemDate(1, 1));			
			info.setStartDate(Env.getSystemDate(1, 1));
			info.setExecuteDate(Env.getSystemDate(1, 1));						
			
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
				
			ArrayList vec = (ArrayList)dao.match(info);			
			if (vec.size()== 0)
			{				
				System.out.println("result == null ");					
			}
			else
			{				
				System.out.println("result size: " + vec.size());
			}				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}

}
*/
public class Sett_TransFixedWithDrawDAOTest {}