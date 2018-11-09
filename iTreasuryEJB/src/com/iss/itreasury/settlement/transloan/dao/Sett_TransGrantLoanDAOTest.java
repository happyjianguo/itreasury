/*
 * �������� 2003-10-9
 */
package com.iss.itreasury.settlement.transloan.dao;

//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import junit.framework.TestCase;
//import com.iss.itreasury.settlement.transloan.bizlogic.TransLoanEJB;
//import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
//import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
//import com.iss.itreasury.util.Env;
/**
 * Sett_TransGrantLoanDAO��Ԫ����
 * @author yqwu
 */
/*
public class Sett_TransGrantLoanDAOTest extends TestCase
{
	List deleteList;
	Sett_TransGrantLoanDAO dao;
	/**
	 * Constructor for Sett_TransGrantLoanDAOTest.
	 * @param arg0
	 
	public Sett_TransGrantLoanDAOTest(String arg0)
	{
		super(arg0);
	}
	/*
	 * @see TestCase#setUp()
	 
	protected void setUp() throws Exception
	{
		super.setUp();
		this.deleteList = new ArrayList();
		dao = new Sett_TransGrantLoanDAO();
	}
	/*
	 * @see TestCase#tearDown()
	 
	protected void tearDown() throws Exception
	{
		super.tearDown();
		long id;
		for (Iterator iterator = this.deleteList.iterator(); iterator.hasNext();)
		{
			id = ((Long) iterator.next()).longValue();
			this.dao.deletePhysically(id);
		}
	}
	/**
	 * ��¼������Ϣ��id
	 * @param info
	 * @throws Exception
	 
	void add(GrantTrustLoanConditionInfo info) throws Exception
	{
		this.dao.add(info.getInfo());
		//this.deleteList.add(new Long(info.getID()));
	}
	/**
	 * �൥Ԫ���Է���
	 * @throws Exception
	 
	public void test() throws Exception
	{
		/**
		 * �������д���
		 
		//����һ�����д����¼
		TransLoanEJB ejb = new TransLoanEJB();
		TransGrantLoanInfo info = new TransGrantLoanInfo();
		info.setOfficeID(1);
		info.setCurrencyID(1);
		info.setTransNo("1");
		info.setTransactionTypeID(1);
		info.setLoanAccountID(1);
		info.setLoanContractID(1);
		info.setLoanNoteID(1);
		info.setExtendFormID(-1);
		info.setPayInterestAccountID(1);
		info.setPaySuretyFeeAccountID(1);
		info.setReceiveSuretyFeeAccountID(1);
		info.setDepositAccountID(1);
		info.setPayTypeID(1);
		info.setBankID(1);
		info.setExtAcctNo("ext 1");
		info.setExtAcctName("ext 1 name");
		info.setBankName("bank name");
		info.setProvince("province");
		info.setCity("");
		info.setAmount(1.11);
		info.setCashFlowID(1);
		info.setInterestStart(Env.getSystemDateTime());
		info.setExecute(Env.getSystemDateTime());
		info.setModify(Env.getSystemDateTime());
		//info.setInputDate(Env.getSystemDateTime());
		info.setInputUserID(1);
		info.setCheckUserID(1);
		info.setAbstractID(1);
		info.setAbstract("abstract");
		info.setCheckAbstract("check abstract");
		info.setStatusID(2);
		//ejb.save(info);
		/*this.add(info);
		//ͨ��id�õ���Ϣ
		TrustLoanInfo otherInfo =
		    new TrustLoanInfo(this.dao.findByID(info.getID()));
		
		infoEquals("�õ�����Ϣ�ͼ������Ϣ��ƥ�䣺", info, otherInfo);
		
		//�޸���Ϣ
		info.setTransNo("2");
		info.setBankName("new bank name");
		info.setModify(Env.getSystemDateTime());
		
		this.dao.update(info.getInfo());
		otherInfo = new TrustLoanInfo(this.dao.findByID(info.getID()));
		
		infoEquals("�޸ĺ�õ�����Ϣ�ͼ������Ϣ��ƥ�䣺", info, otherInfo);
		
		//���Ӳ���
		TrustLoanInfo conditionInfo = new TrustLoanInfo();
		conditionInfo.setOfficeID(1);
		conditionInfo.setCurrencyID(1);
		conditionInfo.setCheckUserID(1);
		conditionInfo.setTransactionTypeID(1);
		conditionInfo.setStatusID(1);
		conditionInfo.setBankName(info.getBankName());
		conditionInfo.setAbstract(info.getAbstract());
		conditionInfo.setModify(
		    new Timestamp(info.getModify().getTime() / 1000 * 1000));
		
		DAOHelper condition = new DAOHelper();
		condition.setInfo(conditionInfo.getInfo());
		condition.setName("���д����");
		condition.setOrderType(TransLoan.ORDER_MODIFY);
		condition.setNotNull("getExtendFormID");
		
		getResult(info, condition);
		
		//ƥ�����
		conditionInfo = new TrustLoanInfo(this.dao.findByID(info.getID()));
		condition = new DAOHelper();
		condition.setInfo(conditionInfo.getInfo());
		condition.setName("���д����");
		condition.setStrict(true);
		condition.setOrderType(TransLoan.ORDER_INPUT_DATE);
		
		getResult(info, condition);
		
		//���ɲ���
		conditionInfo = new TrustLoanInfo();
		conditionInfo.setBankName("name");
		conditionInfo.setAmount(2);
		
		condition = new DAOHelper();
		condition.setInfo(conditionInfo.getInfo());
		
		//like
		condition.setLikeItem("getBankName", DAOHelper.LIKE_TYPE_ALL);
		
		//<>
		condition.setNotEquals("getID");
		
		//between
		condition.setBetweenItem(
		    "getAmount",
		    new Double(info.getAmount()),
		    true,
		    new Double(0),
		    false);
		
		getResult(info, condition);
	}
	private void getResult(GrantTrustLoanConditionInfo info, DAOHelper condition) throws Exception
	{
		GrantTrustLoanConditionInfo otherInfo;
		Collection collection = this.dao.match(condition);
		otherInfo = null;
		int index = 0;
		for (Iterator iterator = collection.iterator(); iterator.hasNext() && index < 1; index++)
		{
			otherInfo = new GrantTrustLoanConditionInfo((TransGrantLoanInfo) iterator.next());
		}
		assertNotNull("���Ӳ���δ�õ����", otherInfo);
		infoEquals("���Ӳ��ҵõ�����Ϣ�ͼ������Ϣ��ƥ�䣺", info, otherInfo);
	}
	/**
	 * �ж����������ֵ�Ƿ����
	 * @param note
	 * @param info
	 * @param otherInfo
	 * @throws Exception
	 
	private void infoEquals(String note, GrantTrustLoanConditionInfo info, GrantTrustLoanConditionInfo otherInfo) throws Exception
	{
		List errorMethods = infoEqualsCore(info, otherInfo);
		assertEquals(note + errorMethods, 0, errorMethods.size());
	}
	/**
	 * �ж����������ֵ�Ƿ���ȵĺ��ķ��� 
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 
	private static List infoEqualsCore(Object source, Object target) throws Exception
	{
		Method[] sourceMethods = source.getClass().getDeclaredMethods();
		Method[] targetMethods = source.getClass().getDeclaredMethods();
		List methodNames = new ArrayList(sourceMethods.length);
		Method sourceMethod, targetMethod;
		String methodName;
		Object sourceReturn, targetReturn;
		long sourceTime, targetTime;
		for (int i = 0; i < sourceMethods.length; i++)
		{
			sourceMethod = sourceMethods[i];
			methodName = sourceMethod.getName();
			if (methodName.startsWith("get"))
			{
				targetMethod = targetMethods[i];
				sourceReturn = sourceMethod.invoke(source, null);
				targetReturn = targetMethod.invoke(target, null);
				if (sourceReturn == null)
				{
					if (targetReturn != null)
					{
						methodNames.add(methodName);
					}
				}
				else if (!sourceReturn.equals(targetReturn))
				{
					if (sourceReturn instanceof java.sql.Timestamp)
					{
						sourceTime = ((java.sql.Timestamp) sourceReturn).getTime() / 1000;
						targetTime = ((java.sql.Timestamp) targetReturn).getTime() / 1000;
						if (sourceTime != targetTime)
						{
							methodNames.add(methodName);
						}
					}
					else
					{
						methodNames.add(methodName);
					}
				}
			}
		}
		return methodNames;
	}
}
*/
public class Sett_TransGrantLoanDAOTest {}