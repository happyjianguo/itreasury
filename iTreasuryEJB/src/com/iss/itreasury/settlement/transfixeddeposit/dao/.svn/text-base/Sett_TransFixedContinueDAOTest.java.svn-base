/*
 * Created on 2003-9-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

//import java.util.ArrayList;
//import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
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
public class Sett_TransFixedContinueDAOTest extends TestCase 
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * Constructor for Sett_TransFixedContinueDAOTest.
	 * @param arg0
	 
	public Sett_TransFixedContinueDAOTest(String arg0) 
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
			TransFixedContinueInfo info = new TransFixedContinueInfo();				
			info.setTransNo("1111");
			info.setOfficeID(1);
			info.setCurrencyID(1);
			info.setTransactionTypeID(1);
			info.setDepositNo("1111");
			info.setAmount(100);
			info.setTransactionTypeID(13);
			info.setAbstract("111111");
			info.setInputUserID(11);
			info.setModifyDate(Env.getSystemDate(1, 1));				
			info.setStartDate(Env.getSystemDate(1, 1));
			info.setExecuteDate(Env.getSystemDate(1, 1));
			info.setClientID(111);			
			
			TransFixedContinueInfo info1 = new TransFixedContinueInfo();				
			info1.setTransNo("2222");
			info1.setOfficeID(2);
			info1.setCurrencyID(2);
			info1.setStatusID(2);
			info1.setTransactionTypeID(13);
			info1.setInputUserID(222222);
			info1.setAccountID(2);
			info1.setClientID(222);
			info1.setDepositNo("2");
			info1.setNewDepositNo("22");
			info1.setNewStartDate(Env.getSystemDate(1, 1));
			info1.setNewRate(2.2);
			info1.setNewDepositTerm(22);
			info1.setNewAmount(200);
			info1.setExecuteDate(Env.getSystemDate(1, 1));
			info1.setIsCapitalAndInterestTransfer(1);
			info1.setReceiveInterestAccountID(222);
			info1.setInterestPayTypeID(2);
			info1.setInterestBankID(22);					
			info1.setModifyDate(Env.getSystemDate(1, 1));			
			
			
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
			
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
			TransFixedContinueInfo info = new TransFixedContinueInfo();
			info.setID(23);
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
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
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
			
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
			long c = dao.updateStatus(23,5);
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
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
			TransFixedContinueInfo info = dao.findByID(23);
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
			info.setUserID(11);
			info.setDate(Env.getSystemDate(1, 1));			
			info.setOfficeID(1);
			long[] lStatusIDs = new long[2];
			lStatusIDs[0]=5;
			lStatusIDs[1]=2;
			info.setStatus(lStatusIDs);
			info.setStatusID(5);
			info.setCurrencyID(1);	
			info.setTransactionTypeID(1);		
			
			//处理还是复核
			info.setTypeID(0);			
			
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
			ArrayList arr = (ArrayList)dao.findByStatus(info);			
			if (arr.size()== 0)
			{				
				System.out.println("result == null ");					
			}
			else
			{				
				System.out.println("result size: " + arr.size());
			}
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("****"+e.getMessage());
		}
	}
	public void testCheckDepositNo() throws Exception
		{	
			System.out.println("****Begin method testCheckDepositNo");
			try
			{
				long lTotalPages = 0;
				TransFixedContinueInfo info = new TransFixedContinueInfo();			
				//交易类型			
				info.setTransNo("2222");
				info.setOfficeID(2);
				info.setCurrencyID(2);
				info.setStatusID(2);			
				info.setInputUserID(23332);
				info.setAccountID(-1);
				info.setClientID(222);
				info.setNewDepositNo("2222");
				info.setDepositTerm(2);
				info.setSealNo("2");
				info.setRate(2.2);				
				//info.setExtBankNo("2");				
				info.setAmount(200);								
				info.setStartDate(Env.getSystemDate(1, 1));			
				info.setExecuteDate(Env.getSystemDate(1, 1));			
				info.setTransactionTypeID(12);			
			
				Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
				boolean flag = dao.checkDepositNo(info);			
				if (flag)
				{				
					System.out.println("不重复");					
				}
				else
				{				
					System.out.println("存款单据号重复");
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
			TransFixedContinueInfo info = new TransFixedContinueInfo();			
			
			//交易类型			
			info.setTransNo("2222");
			info.setOfficeID(2);
			info.setCurrencyID(2);
			info.setStatusID(2);
			info.setTransactionTypeID(13);
			info.setInputUserID(333);
			info.setAccountID(2);
			info.setClientID(222);
			info.setDepositNo("2");
			info.setNewDepositNo("22");
			info.setNewStartDate(Env.getSystemDate(1, 1));
			info.setNewRate(2.2);
			info.setNewDepositTerm(22);
			info.setNewAmount(200);
			info.setExecuteDate(Env.getSystemDate(1, 1));
			info.setIsCapitalAndInterestTransfer(1);
			info.setReceiveInterestAccountID(222);
			info.setInterestPayTypeID(2);
			info.setInterestBankID(22);					
			//查找交易类型						
			
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
				
			ArrayList arr = (ArrayList)dao.match(info);			
			if (arr.size()== 0)
			{				
				System.out.println("result == null ");					
			}
			else
			{				
				System.out.println("result size: " + arr.size());
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
public class Sett_TransFixedContinueDAOTest {}