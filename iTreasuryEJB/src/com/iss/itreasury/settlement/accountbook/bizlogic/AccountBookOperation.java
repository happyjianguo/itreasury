/*
 * Created on 2003-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.accountbook.bizlogic;

import java.rmi.RemoteException;

import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AccountBookOperation
{
	private AccountBook accountBook;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);	
	public AccountBookOperation() throws RemoteException, IRollbackException
	{
		try
		{
			AccountBookHome home = (AccountBookHome) EJBHomeFactory.getFactory().lookUpHome(AccountBookHome.class);
			accountBook = (AccountBook) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException();
		}
	}
	
	/**
	 * ֤ȯҵ�񱣴�Ĳ�����
	 * @author Forest,20040531
	 */
	public void saveSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.saveSecuritiesDetails(transInfo);
	}
	/**
	 * ֤ȯҵ��ɾ���Ĳ�����
	 * @author Forest,20040531
	 */
	public void deleteSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.deleteSecuritiesDetails(transInfo);
	}
	/**
	 * ֤ȯҵ�񸴺˵Ĳ�����
	 * @author Forest,20040531
	 */
	public void checkSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.checkSecuritiesDetails(transInfo);
	}
	/**
	 * ֤ȯҵ��ȡ�����˵Ĳ�����
	 * @author Forest,20040531
	 */
	public void cancelCheckSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckSecuritiesDetails(transInfo);
	}
	
	
	/**
	 * ����(֪ͨ)���������׸���(���ڿ������׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedOpenInfo ����(֪ͨ)��������ʵ����
	 * @throws IRollbackException
	 */
	public void checkOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkOpenFixedDeposit start");
		accountBook.checkOpenFixedDeposit(transInfo);
		log.debug("AccoutBookOperation::checkOpenFixedDeposit end");			
	}	
	
	/**
	 * ���������տ�����׸���(���������տ�׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReceiveFinanceInfo ���������տ��ʵ����
	 * @throws IRollbackException
	 */
	public void checkReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkReceiveFinance start");
		accountBook.checkReceiveFinance(transInfo);
		log.debug("AccoutBookOperation::checkReceiveFinance end");			
	}
	
	/**
	 * �������޻���׸���(�������޻���׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReturnFinanceInfo �������޻����ʵ����
	 * @throws IRollbackException
	 */
	public void checkReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkReturnFinance start");
		accountBook.checkReturnFinance(transInfo);
		log.debug("AccoutBookOperation::checkReturnsFinance end");			
	}
	
	/**
	 * ����(֪ͨ)����������ȡ������(���ڿ�������ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedOpenInfo ����(֪ͨ)��������ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckOpenFixedDeposit(transInfo);
	}
	
	/**
	 * ���������տ������ȡ������(���������տ��ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReceiveFinanceInfo ���������տ��ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckReceiveFinance(transInfo);
	}
	
	/**
	 * �������޻��������ȡ������(�������޻����ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReturnFinanceInfo �������޻����ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckReturnFinance(transInfo);
	}
	
	/**
	 * ����֧ȡ�����ױ���(����֧ȡ���ױ���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedDrawInfo ����֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void saveWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithdrawFixedDeposit(transInfo);	
	}	
	/**
	 * ����(֪ͨ)֧ȡ������ɾ��(����֧ȡ����ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedDrawInfo ����(֪ͨ)֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void deleteWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteWithdrawFixedDeposit(transInfo);
	}	
	/**
	 * ����(֪ͨ)֧ȡ֧ȡ�����׸���(����֧ȡ���׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedDrawInfo ����(֪ͨ)֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void checkWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithdrawFixedDeposit(transInfo);		
	}	
	/**
	 * ����(֪ͨ)֧ȡ������ȡ������(����֧ȡ����ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedDrawInfo ����(֪ͨ)֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckWithdrawFixedDeposit(TransFixedDrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithdrawFixedDeposit(transInfo);
	}	
	/**
	 * ��������ת������ױ���(���ڿ������ױ���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedContinueInfo ��������ת�潻��ʵ����
	 * @throws IRollbackException
	 */
	public void saveContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveContinueFixedDeposit(transInfo);
	}	
	/**
	 * ��������ת�������ɾ��(���ڿ�������ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedContinueInfo ��������ת�潻��ʵ����
	 * @throws IRollbackException
	 */
	public void deleteContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteContinueFixedDeposit(transInfo);
	}	
	/**
	 * ��������ת������׸���(���ڿ������׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedContinueInfo ��������ת�潻��ʵ����
	 * @throws IRollbackException
	 */
	public void checkContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkContinueFixedDeposit(transInfo);
	}	
	/**
	 * ��������ת�������ȡ������(���ڿ�������ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedContinueInfo ��������ת�潻��ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckContinueFixedDeposit(TransFixedContinueInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckContinueFixedDeposit(transInfo);
	}	
	
	
	
	/**
	 * ��֤���������׸���(��֤�������׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginOpenInfo ��֤��������ʵ����
	 * @throws IRollbackException
	 */
	public void checkOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		log.debug("AccoutBookOperation::checkOpenMarginDeposit start");
		accountBook.checkOpenMarginDeposit(transInfo);
		log.debug("AccoutBookOperation::checkOpenMarginDeposit end");			
	}	
	/**
	 * ��֤����������ȡ������(��֤��������ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginOpenInfo ��֤��������ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckOpenMarginDeposit(transInfo);
	}
	/**
	 * ��֤��֧ȡ�����ױ���(��֤��֧ȡ���ױ���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginDrawInfo ��֤��֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void saveWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithdrawMarginDeposit(transInfo);	
	}
	/**
	 * �������� ��֤�� ������ �����ױ���ʱ�Ĳ�����
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.saveWithFinancialMargin(transInfo);	
	}
	/**
	 * �������� ��֤�� ������ �������޸ı���ʱ�Ĳ����� ɾ����
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public void deleteWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{	
		accountBook.deleteWithFinancialMargin(transInfo);	
	}
	
	public void checkWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithFinancialMargin(transInfo);		
	}	
	/**
	 * ��֤��֧ȡ������ɾ��(��֤��֧ȡ����ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginDrawInfo ��֤��֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void deleteWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteWithdrawMarginDeposit(transInfo);
	}	
	/**
	 * ��֤��֧ȡ֧ȡ�����׸���(��֤��֧ȡ���׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginDrawInfo��֤��֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void checkWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkWithdrawMarginDeposit(transInfo);		
	}	
	/**
	 * ��֤��֧ȡ������ȡ������(��֤��֧ȡ����ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginDrawInfo ��֤��֧ȡ����ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckWithdrawMarginDeposit(TransMarginWithdrawInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithdrawMarginDeposit(transInfo);
	}
	
	public void cancelCheckWithFinancialMargin(TransFinancialMarginInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckWithFinancialMargin(transInfo);
	}
	
	/**
	 * ���ڲ����ױ���(���ڽ��ױ���ʱ�Ĳ���������)
	 * �߼�������
	 * 1.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0��Ϊ�ڲ�ת�ˣ������ۼ�δ���˽������˻��������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����Ʊ�ݺ�>0����Ʊ��ʹ�ã�Ʊ�ݷ������У�Ʊ�����ͣ�Ʊ�ݺţ�����ͻ�ID��ִ���գ�¼���ˣ�
	 * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws IRollbackException
	 */
	public void saveCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		
			accountBook.saveCurrentAccountDetails(transInfo);
	
	}
    /**
     * ��������ȡ�����ױ���(�����ѽ��ױ���ʱ�Ĳ���������)
     * �߼�������
     * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
     * @throws IRollbackException
     */
    public void saveCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException,IRollbackException
    {
        
            accountBook.saveCommissionAccountDetails(transInfo);
    
    }
	/**
	 * ���ڲ����ױ���(���ڽ��ױ���ʱ�Ĳ���������)
	 * �߼�������
	 * 1.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0��Ϊ�ڲ�ת�ˣ������ۼ�δ���˽������˻��������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����Ʊ�ݺ�>0����Ʊ��ʹ�ã�Ʊ�ݷ������У�Ʊ�����ͣ�Ʊ�ݺţ�����ͻ�ID��ִ���գ�¼���ˣ�
	 * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws Exception
	 */
	public void deleteCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteCurrentAccountDetails(transInfo);
	}
	/**
	 * ���ڲ����׸���(���ڽ��׸���ʱ�Ĳ���������)
	 * �߼�������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0���ڲ�ת�ˣ����򣺻���֧ȡ������˻���������Է��˻�=�տ��
	 * 3.����������Ϊ�����տ�ֽ��տ�ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ����տ��˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻��ڴ��루�տ�˻���������,�Է��˻�=�����
	 * 4.ͨ��ͨ�Ҵ���AccountBookBizlogic.InterbranchSettlement()
	 * 5.������Ʒ�¼GeneralLedgerBizlogic.GenerateGLEntry(��)�������/���������˻�ID>0���򱾽�����lPrincipalType =2���У����򱾽�����lPrincipalType =1�ڲ�ת�ˣ���¼����lEntryType =0�޹أ�lAccountID1=�տ�˻���lAccountID2=����˻���dAmount1=����������ԡ�
	 * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws Exception
	 */
	public void checkCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkCurrentAccountDetails(transInfo);
	}
    /**
     * ��������ȡ�����׸���(�����ѽ��׸���ʱ�Ĳ���������)
     * ������Ʒ�¼GeneralLedgerBizlogic.GenerateGLEntry(��)�������/���������˻�ID>0���򱾽�����lPrincipalType =2���У����򱾽�����lPrincipalType =1�ڲ�ת�ˣ���¼����lEntryType =0�޹أ�lAccountID1=�տ�˻���lAccountID2=����˻���dAmount1=����������ԡ�
     * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
     * @throws Exception
     */
    public void checkCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException,IRollbackException
    {
    	accountBook.checkCommissionAccountDetails(transInfo);
    }
	/**
	 * ���ڲ�����ȡ������(���ڽ���ȡ������ʱ�Ĳ���������)
	 * �߼�������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ���˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻���֧ȡ�����ף�����˻���������Է��˻�=�տ��
	 * 3.����������Ϊ�����տ�ֽ��տ�ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ����տ�˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻��ڴ��뷴���ף��տ�˻���������Է��˻�=�����
	 * 4.ɾ����ϸ��AccountBookBizlogic.DeleteAccountDetail
	 * 5.ͨ��ͨ�ҷ����״���AccountBookBizlogic.InterbranchSettlementReverse()
	 * 6.ɾ����Ʒ�¼GeneralLedgerBizlogic.DeleteGLEntry(��)
	 * @param transInfo TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws Exception
	 */
	public void unCheckCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException
	{
		log.debug("AccountBookOperation::unCheckCurrentAccountDetails");
		accountBook.unCheckCurrentAccountDetails(transInfo);
	}
	
	/*
	 * ���ڿ�����֪ͨ��������
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	 
		public void saveOpenFixedDeposit(TransFixedOpenInfo info)
		throws RemoteException, IRollbackException{
			accountBook.saveOpenFixedDeposit(info);
		}
		
		/*
		 * ��֤���������
		 * @param TransMarginOpenInfo
		 * @Exception RemoteException, IRollbackException
		 */
		 
			public void saveOpenMarginDeposit(TransMarginOpenInfo info)
			throws RemoteException, IRollbackException{
				accountBook.saveOpenMarginDeposit(info);
			}
			
		/*
		 * ���������տ��
		 * @param TransReceiveFinanceInfo
		 * @Exception RemoteException, IRollbackException
		 */
			 
		public void saveReceiveFinance(TransReceiveFinanceInfo info)
			throws RemoteException, IRollbackException
		{
					accountBook.saveReceiveFinance(info);
		}		
	
		/*
		 * �������޻����
		 * @param TransReturnFinanceInfo
		 * @Exception RemoteException, IRollbackException
		 */
			 
		public void saveReturnFinance(TransReturnFinanceInfo info)
			throws RemoteException, IRollbackException
		{
					accountBook.saveReturnFinance(info);
		}
		
	/**
	 * ����(֪ͨ)����������ɾ��(��֤��������ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransFixedOpenInfo ����(֪ͨ)��������ʵ����
	 * @throws IRollbackException
	 */
	public void deleteOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteOpenFixedDeposit(transInfo);
	}
	
	/**
	 * ���������տ������ɾ��(���������տ��ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReceiveFinanceInfo ����(֪ͨ)��������ʵ����
	 * @throws IRollbackException
	 */
	public void deleteReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteReceiveFinance(transInfo);
	}
	
	/**
	 * �������޻��������ɾ��(���������տ��ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransReturnFinanceInfo ����(֪ͨ)��������ʵ����
	 * @throws IRollbackException
	 */
	public void deleteReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteReturnFinance(transInfo);
	}
	
	/**
	 * ��֤����������ɾ��(��֤��������ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransMarginOpenInfo ��֤��������ʵ����
	 * @throws IRollbackException
	 */
	public void deleteOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteOpenMarginDeposit(transInfo);
	}
	
	/**
	 * ����ҵ������ױ���(����ҵ�񱣴�ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ��ʵ����
	 * @throws IRollbackException
	 */
	public void saveSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveSpecialOperation(transInfo);
	}	
	/**
	 * ����ҵ�������ɾ��(����ҵ����ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void deleteSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteSpecialOperation(transInfo);
	}	
	/**
	 * ����ҵ������׸���(����ҵ���׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void checkSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkSpecialOperation(transInfo);
	}	
	/**
	 * ����ҵ�������ȡ������(����ҵ����ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckSpecialOperation(transInfo);
	}
	
	
	/**
	 * ��Ӫ����š�ί�д���ŵȽ��ױ���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
	
		accountBook.saveGrantLoan(info);
	
	}
	
	/**
	 * ��Ӫ����š�ί�д���ŵȽ���ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public void deleteGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
	
		accountBook.deleteGrantLoan(info);
	
	}
	
	/**
	 * ��Ӫ����š�ί�д���ŵȽ��׸���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
		accountBook.checkGrantLoan(info);
	}
	
	/**
	 * ��Ӫ����š�ί�д���ŵ�ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public void cancelCheckGrantLoan(TransGrantLoanInfo info) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckGrantLoan(info);
	}		
	
	/**
	 *���ַ��Ž��ױ���
	*/
	public void saveGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.saveGrantDiscount(transInfo);
	}
	/**
	 *���ַ��Ž���ɾ��
	*/	
	public void deleteGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.deleteGrantDiscount(transInfo);
	}
	
	/**
	 *���ַ��Ž��׸���
	*/
	public void checkGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkGrantDiscount(transInfo);
	}
	/**
	 *���ַ��Ž���ȡ������
	*/	
	public void cancelCheckGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckGrantDiscount(transInfo);
	}		
	
	/**
	 * �����ջز����ױ���(�����ջر���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransRepaymentDiscountInfo �����ջ�ʵ����
	 * @throws IRollbackException
	 */
	public void saveRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveRepaymentDiscount(transInfo);
	
	}
	/**
	 * �����ջز�����ɾ��(�����ջؽ���ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransRepaymentDiscountInfo �����ջؽ���ʵ����
	 * @throws IRollbackException
	 */
	public void deleteRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteRepaymentDiscount(transInfo);
	}
	/**
	 * �����ջز����׸���(�����ջؽ��׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransRepaymentDiscountInfo �����ջؽ���ʵ����
	 * @throws IRollbackException
	 */
	public void checkRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkRepaymentDiscount(transInfo);
	}	

	/**
	 * �����ջز�����ȡ������(�����ջؽ���ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransRepaymentDiscountInfo �����ջؽ���ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckRepaymentDiscount(transInfo);
	}	
	
	public void saveRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.saveRepaymentLoan(transInfo);
	}

	public void deleteRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.deleteRepaymentLoan(transInfo);
	}
	
	public void checkRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.checkRepaymentLoan(transInfo);
	}
	
	public void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException,IRollbackException
	{
		accountBook.cancelCheckRepaymentLoan(transInfo);
	}			
	/**
	 * һ�����ս��ױ���
	 *  �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void saveOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.saveOnePayMultiReceive(transInfo);
	}
	
	/**
	 * һ�����ս���ɾ��
	 *  �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.deleteOnePayMultiReceive(transInfo);
	}
	
	/**
	 * һ�����ս��׸���
	 *   ��  ��������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void checkOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkOnePayMultiReceive(transInfo);
	}
	
	/**
	 * һ�����ս���ȡ��
	 *   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void cancelCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckOnePayMultiReceive(transInfo);		
	}
	
	/**
	 * һ�����ս��׹���
	 *   ��  ��������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void squareOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.squareOnePayMultiReceive(transInfo);
	}
	
	/**
	 * һ�����ս���ȡ������
	 *   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void cancelsquareCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelsquareCheckOnePayMultiReceive(transInfo);		
	}
	
	/**
	 * �����ౣ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.saveGeneralLedgerOperation(transInfo);
	}
	
	/**
	 * ������ɾ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.deleteGeneralLedgerOperation(transInfo);
	}
	
	
	/**
	 * �����ิ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */		
	public void checkGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.checkGeneralLedgerOperation(transInfo);
	}
	
	/**
	 * ������ȡ������   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */			
	public void cancelCheckGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException
	{
		accountBook.cancelCheckGeneralLedgerOperation(transInfo);
	}
	
	/**
	 *��ʴ����ջر���
	*/
	public void saveMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.saveMultiLoanReceive(transInfo);
	}
	/**
	 *��ʴ����ջ�ɾ��
	*/
	public void deleteMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.deleteMultiLoanReceive(transInfo);
	}
	/**
	 *��ʴ����ջظ���
	*/
	public void checkMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.checkMultiLoanReceive(transInfo);
	}
	/**
	 *��ʴ����ջ�ȡ������
	*/
	public void cancelCheckMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelCheckMultiLoanReceive(transInfo);
	}		
	/**
	 *��ʴ����ջع���
	*/	
	public void squareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.squareMultiLoanReceive(transInfo); 
	}
	/**
	 *��ʴ����ջ�ȡ������
	*/
	public void cancelSquareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelSquareMultiLoanReceive(transInfo);
	}	

	/**���׷��ô����ױ���*/
	public void saveTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.saveTransFee(transInfo);
	}
	
	/**���׷��ô�����ɾ��*/
	public void deleteTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.deleteTransFee(transInfo);
	}
	
	/**���׷��ô����׸���*/
	public void checkTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.checkTransFee(transInfo);
	}
	
	/**���׷��ô�����ȡ������*/
	public void cancelCheckTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.cancelCheckTransFee(transInfo);
	}	
	/**
	 *ת���ַ��Ž��׸���
	*/
	public void checkTransDiscount(TransDiscountDetailInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkTransDiscount(transInfo);
	}
	public boolean isCreateInstruction(long bankID) throws Exception
	{
		return accountBook.isCreateInstruction(bankID);
	}
	/**
	 *�Ŵ��ʲ�ת�ø���׸���
	*/
	public void checkTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.checkTransferContract(transInfo);
	}
	/**
	 *�Ŵ��ʲ�ת�ø����ȡ������
	*/
	public void cancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.cancelCheckTransferContract(transInfo);
	}
	/**
	 *�Ŵ��ʲ�ת���տ�׸���
	*/
	public void repaycheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repaycheckTransferContract(transInfo);
	}
	/**
	 *�Ŵ��ʲ�ת���տ��ȡ������
	*/
	public void repaycancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repaycancelCheckTransferContract(transInfo);
	}
	
	/**
	 *�Ŵ��ʲ�ת���տ�׸���(����)
	*/
	public void repayClientcheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repayClientcheckTransferContract(transInfo);
	}
	/**
	 *�Ŵ��ʲ�ת���տ��ȡ������(����)
	*/
	public void repayClientcancelCheckTransferContract(TransferLoanContractInfo transInfo) throws RemoteException,IRollbackException{
		accountBook.repayClientcancelCheckTransferContract(transInfo);
	}
	/**
	 * add by kevin(������)2011-07-15
	 * �ڲ����-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.saveInternalLend(info);
	}
	/**
	 * add by kevin(������)2011-07-15
	 * �ڲ����-���˲���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.checkInternalLend(info);
	}
	/**
	 * add by kevin(������)
	 * 2011-07-15
	 * �ڲ����-ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLend(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.cancelCheckInternalLend(info);
	}
	/**
	 * add by kevin(������)2011-07-19
	 * �ڲ�����ջ�-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public void saveInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.saveInternalLendRepayment(info);
	}
	/**
	 * add by kevin(������)2011-07-19
	 * �ڲ�����ջ�-ɾ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.deleteInternalLendRepayment(info);
	}
	/**
	 * add by kevin(������)2011-07-19
	 * �ڲ�����ջ�-���˲���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.checkInternalLendRepayment(info);
	}
	/**
	 * add by kevin(������)
	 * 2011-07-19
	 * �ڲ�����ջ�-ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLendRepayment(TransInternalLendInfo info) throws RemoteException,IRollbackException{
		accountBook.cancelCheckInternalLendRepayment(info);
	}
	/**
	 * add by ����2011-07-19
	 * ����������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.saveBakReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.saveBakReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
			
	
	}
	/**
	 * add by ����2011-07-19
	 * ����������-ɾ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.deleteBakReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.deleteBakReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	
	public void checkBakReserveDetails(TransBakReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.checkBakReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.checkBakReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	public void unCheckBakReserveDetails(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKUPRECEIVE)
        {
        	accountBook.unCheckBakReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
        {
        	accountBook.unCheckBakReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	

	/**
	 * add by ���� 2011-07-20
	 * ׼��������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.saveReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.saveReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
			
	}
	/**
	 * add by ���� 2011-07-20
	 * ׼��������-ɾ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.deleteReserveAccountDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.deleteReserveAccountDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	public void checkReserveDetails(TransReserveInfo transInfo) throws RemoteException,IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.checkReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.checkReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	public void unCheckReserveDetails(TransReserveInfo transInfo) throws RemoteException, IRollbackException
	{
        if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVEUPRECEIVE)
        {
        	accountBook.unCheckReserveDetailsUpReceive(transInfo);
        }
        else if(transInfo.getNtransactiontypeid()==SETTConstant.TransactionType.RESERVERETURN)
        {
        	accountBook.unCheckReserveDetailsDownReturn(transInfo);
        }
        else
        {
                
                throw new RemoteException("��ȡ�Ľ������Ͳ��ԣ������˲�����ʧ��,����ϵ����Ա.");
                
        }
		
	}
	
	/**��ҵƱ�ݳж�-���ڳжҽ��ױ���*/
	public void saveAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.saveAcceptanceNoteAcceptance(transInfo);
	}
	
	/**��ҵƱ�ݳж�-���ڳжҽ���ɾ��*/
	public void deleteAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.deleteAcceptanceNoteAcceptance(transInfo);
	}
	
	/**��ҵƱ�ݳж�-���ڳжҽ��׸���*/
	public void checkAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.checkAcceptanceNoteAcceptance(transInfo);
	}
	
	/**��ҵƱ�ݳж�-���ڳжҽ���ȡ������*/
	public void cancelCheckAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException{
		accountBook.cancelCheckAcceptanceNoteAcceptance(transInfo);
	}
	
	/**
	 * ������Ŀ : added by qhzhou 2011-03-28
	 * ͬҵ�������� ,�˻��˲�����
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.checkTransCraftbrother(transInfo);
	}
	/**
	 * ͬҵҵ����㴦��ȡ������
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException{
		accountBook.cancelCheckTransCraftbrother(transInfo);
	}
}
