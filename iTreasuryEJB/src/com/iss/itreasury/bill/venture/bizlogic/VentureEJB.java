/* Generated by Together */
package com.iss.itreasury.bill.venture.bizlogic;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.CreateException;
import com.iss.itreasury.bill.venture.dao.BlackBillDao;
import com.iss.itreasury.bill.venture.dataentity.BlackConditionInfo;
import com.iss.itreasury.bill.venture.dataentity.BlackQueryCondition;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import java.util.Vector;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.venture.dataentity.BlackBillInfo;
/**
 * @ejbHome com.iss.itreasury.bill.venture.bizlogic.VentureHome
 * @ejbRemote com.iss.itreasury.bill.venture.bizlogic.Venture
 * @ejbDontSynchronizeNames 
 */
public class VentureEJB implements SessionBean
{
	private SessionContext ctx = null;
	private BlackBillDao billDao = new BlackBillDao();
	public void setSessionContext(SessionContext context) throws RemoteException, EJBException
	{
		ctx = context;
	}
	public void ejbActivate() throws EJBException
	{
	}
	public void ejbPassivate() throws EJBException
	{
	}
	public void ejbRemove() throws EJBException
	{
	}
	public void ejbCreate() throws CreateException, EJBException
	{
		// Write your code here
	}
	protected Log4j log = new Log4j(Constant.ModuleType.BILL, this);
	/**
	 * Method add.
	 * @param bci
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	/**
	 * ����¼������������һ���������¼ 
	 */
	public long add(BlackConditionInfo bci) throws BillException, RemoteException
	{
		
		
		long lResult = -1;
		BlackBillInfo billInfo = null;
		log.print("=======Enter method VentureEJB.add(BlackConditionInfo bci)=======");
		try
		{
			//ת�����ݣ����б���		
			Vector vctBillInfo = ConvertBlackConditionInfoToBillInfos(bci);
			if (vctBillInfo != null)
			{
				for (int i = 0; i < vctBillInfo.size(); i++)
				{
					billInfo = (BlackBillInfo) vctBillInfo.elementAt(i);
					if (billInfo != null)
					{
						//�����Ƿ�����ͬ���͵���ͬƱ�ݺŵĻ�Ʊ�Ѿ����������
						if (billDao.isBillInBlackList(billInfo))
						{
							throw new BillException("Bill_E020", billInfo.getBillCode(), null);
						}
						else
						{
							lResult = billDao.add(billInfo);
						}
					}
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new BillException(e.getMessage(), e, ctx); //�����쳣��Ϣ��Ҫת��Ϊ�ͻ�����������쳣���ԣ����޸�
		}
		log.print("=======End method VentureEJB.add(BlackConditionInfo bci)=======");
		return lResult;
	}
	/**
	 * Method findByCondition.
	 * @param bci
	 * @return PageLoader
	 * @throws BillException
	 * @throws RemoteException
	 */
	/**
	 * ����ָ����������ѯ���������ļ�¼ 
	 */
	public Vector findByCondition(BlackQueryCondition blackQueryCondition) throws BillException, RemoteException
	{
		Vector vctResult = null;
		log.print("=======Enter method VentureEJB.findByCondition(BlackConditionInfo bci)=======");
		vctResult = billDao.findByQueryCondition(blackQueryCondition);
		log.print("=======End method VentureEJB.findByCondition(BlackConditionInfo bci)=======");
		return vctResult;
	}
	/**
	 * Method releaseFromBlackList.
	 * @param ID
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	public long releaseFromBlackList(long[] IDs) throws BillException, RemoteException
	{
		long lResult = -1;
		log.print("=======Enter method VentureEJB.releaseFromBlackList(ArrayList IDs)=======");
		if (IDs != null && IDs.length > 0)
		{
			for (int i = 0; i < IDs.length; i++)
			{
				lResult = billDao.updateStatusByID(IDs[i], BILLConstant.BlackBillStatus.REALEASEGUARD);
			}
		}
		log.print("=======End method VentureEJB.releaseFromBlackList(ArrayList IDs)=======");
		return lResult;
	}
	/**
	 * Method findByID.
	 * @param ID
	 * @return BlackBillInfo
	 * @throws BillException
	 * @throws RemoteException
	 */
	/**
	 * ����ָ��������ID������ȡ�ü�¼����ϸ��Ϣ 
	 */
	public BlackBillInfo findByID(long ID) throws BillException, RemoteException
	{
		BlackBillInfo resultInfo = null;
		log.print("=======Enter method VentureEJB.findByID(long ID)=======");
		try
		{
			resultInfo = (BlackBillInfo) billDao.findByID(ID, BlackBillInfo.class);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e, ctx);
		}
		log.print("=======End method VentureEJB.findByID(long ID)=======");
		return resultInfo;
	}
	/**
	 * Method delete.
	 * @param ID
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	/**
	 * ����ָ�����������飩��ɾ����ؼ�¼ 
	 */
	public long delete(long[] IDs) throws BillException, RemoteException
	{
		long lResult = -1;
		log.print("=======Enter method  VentureEJB.delete(ArrayList IDs)=======");
		if (IDs != null && IDs.length > 0)
		{
			for (int i = 0; i < IDs.length; i++)
			{
				lResult = billDao.updateStatusByID(IDs[i], Constant.RecordStatus.INVALID);
			}
		}
		log.print("=======End method  VentureEJB.delete(ArrayList IDs)=======");
		return lResult;
	}
	/**
	 * Method update.
	 * @param bbi
	 * @return long
	 * @throws BillException
	 * @throws RemoteException
	 */
	/**
	 * �޸�ָ��Ʊ����Ϣ 
	 */
	public long update(BlackBillInfo blackBillInfo) throws BillException, RemoteException
	{
		long lResult = -1;
		BlackBillInfo tempBlackBillInfo = null;
		log.print("=======Enter method  VentureEJB.update(BlackBillInfo bbi) =======");
		try
		{
			//�ж��Ƿ��޸�
			tempBlackBillInfo = (BlackBillInfo) billDao.findByID(blackBillInfo.getId(), BlackBillInfo.class);
			if(tempBlackBillInfo == null ||(tempBlackBillInfo!=null && tempBlackBillInfo.getStatusID() == Constant.RecordStatus.INVALID))
			{
				throw new BillException("Bill_E021", blackBillInfo.getBillCode(), null);			
			}
			billDao.update(blackBillInfo);
		}
		catch (ITreasuryDAOException e)
		{
			throw new BillException(e.getMessage(), e, ctx);
		}
		log.print("=======End method  VentureEJB.update(BlackBillInfo bbi) =======");
		return lResult;
	}
	private Vector ConvertBlackConditionInfoToBillInfos(BlackConditionInfo conditionInfo) throws BillException, RemoteException
	{
		Vector vctResult = null;
		BlackBillInfo blackBillInfo = null;
		long lBillNum = -1;
		String strTmpCode = "";
		if (conditionInfo != null)
		{
			lBillNum = conditionInfo.getBillCodeEnd() - conditionInfo.getBillCodeStart() + 1;
			log.print("======Ʊ����Ŀ:" + lBillNum);
			log.print("======Ʊ�����ֲ��ֳ���:" + conditionInfo.getBillNumberLength());
			log.print("======Ʊ����ʼ����:" + conditionInfo.getBillCodeStart());
			if (lBillNum > 0)
			{
				vctResult = new Vector();
				for (int i = 0; i < lBillNum; i++)
				{
					blackBillInfo = new BlackBillInfo();
					blackBillInfo.setBillTypeID(conditionInfo.getBillTypeID());
					blackBillInfo.setOfficeID(conditionInfo.getOfficeID());
					blackBillInfo.setCurrencyID(conditionInfo.getCurrencyID());
					blackBillInfo.setStatusID(conditionInfo.getStatusID());
					blackBillInfo.setInputUserID(conditionInfo.getInputUserID());
					blackBillInfo.setInputDate(conditionInfo.getInputDate());
					blackBillInfo.setCreateDate(conditionInfo.getCreateDate());
					blackBillInfo.setMaturityDate(conditionInfo.getMaturityDate());
					blackBillInfo.setAcceptor(conditionInfo.getAcceptor());
					blackBillInfo.setAcceptorAccount(conditionInfo.getAcceptorAccount());
					blackBillInfo.setAcceptorBank(conditionInfo.getAcceptorBank());
					blackBillInfo.setRemark(conditionInfo.getRemark());
					blackBillInfo.setBillAmount(conditionInfo.getBillAmount());
					blackBillInfo.setBillCode(
						UtilOperation.getFormatBillCode(conditionInfo.getBillCodeString(), conditionInfo.getBillCodeStart() + i, conditionInfo.getBillNumberLength()));
					log.print("======blackBillInfo.getBillCode:" + blackBillInfo.getBillCode());
					vctResult.add(blackBillInfo);
				}
				log.print("======size:" + vctResult.size());
			}
		}
		return vctResult;
	}
}