package com.iss.itreasury.loan.integrationcredit.bizlogic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.clientmanage.client.dao.CorporationDAO;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitDetailSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditProductDAO;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.query.dao.QueryDao;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class CreditSimpleBiz extends AbstractCreditBiz{
	
	/**
	 * ���ݴ������Ͳ�ѯ��������
	 * 
	 * @param loanTypeID
	 * @return
	 */
	public long getCreditTypeByLoanType(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}

	/**
	 * ��������Ʒ�����Ͳ�ѯ��Ӧ�Ĵ�������
	 * 
	 * @param creditProductType
	 * @return
	 * @throws Exception
	 */
	public long typeTrans(long creditProductType) throws IException {
		long loanType = -1;
		try {
			switch ((int) creditProductType) {
			case (int) LOANConstant.CreditProduct.ZY:
				loanType = LOANConstant.LoanType.ZY;
				break;
			case (int) LOANConstant.CreditProduct.SP:
				loanType = LOANConstant.LoanType.TX;
				break;
			case (int) LOANConstant.CreditProduct.BH:
				loanType = LOANConstant.LoanType.DB;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("ת������",e);
		}
		return loanType;
	}

	
	/***************************************************************************/
	
	
	/**
	 * ������������ID ��ѯ���ŷ�������
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public CreditProductInfo findCreditProductInfo(CreditProductInfo qInfo)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByCreditTypeID(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}
	

	/**
	 * �������Ų�Ʒ����������Ϣ
	 * 
	 * @param CreditProductInfo
	 * @return
	 * @throws Exception
	 */
	public long saveProductInfo(CreditProductInfo info) throws IException {
		long ret = -1;
		CreditProductDAO dao = new CreditProductDAO();
		CreditProductInfo qInfo = null;

		try {
			qInfo = dao.findByCreditTypeID(info);
			
			if(qInfo == null){
				info.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				ret = dao.add(info);
			}
			else {
				qInfo.setOfficeID(info.getOfficeID());    
				qInfo.setCurrencyID(info.getCurrencyID());
				qInfo.setUpdateTime(info.getUpdateTime());
				qInfo.setUpdateUserID(info.getUpdateUserID());
				qInfo.setCreditTypeID(info.getCreditTypeID());
				qInfo.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				qInfo.setIsControl(info.getIsControl());
				qInfo.setControlTypeID(info.getControlTypeID());
				qInfo.setEngrossRate(info.getEngrossRate()); 
				qInfo.setStatusID(info.getStatusID()); 
				dao.update(qInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("�������Ų�Ʒ�������ô���",e);
		}
		return ret;
	}
	
	
	/***************************************************************************/
	
	/**
	 * 
	 * ���ͻ��������ϼ��ͻ��Ƿ���������������
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isParentCredit(CorporationInfo corporationInfo, CreditLimitDetailInfo detailInfo) throws IException
	{    
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
			 CreditLimitDetailInfo  queryinfo = new CreditLimitDetailInfo();
			 queryinfo.setCreditLimitID(detailInfo.getCreditLimitID());
			 queryinfo.setCreditTypeID(detailInfo.getCreditTypeID());
			 queryinfo.setOfficeID(detailInfo.getOfficeID());
			 queryinfo.setCurrencyID(detailInfo.getCurrencyID());
			 queryinfo.setStartDate(detailInfo.getStartDate());
			 queryinfo.setEndDate(detailInfo.getEndDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 Collection c = dao.findByDateOption(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, true);
			 
//			 if(corporationInfo.getParentCorpID1() == -1){
//				 if(c == null){
//					 isCredit = true;
//				 }
//				 else {
//					 isCredit = false;
//				 }
//			 }
//			 else {
//				if(c == null){
////					CorporationDAO corporationDAO = new CorporationDAO();
////					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
////					isCredit = isParentCredit(parentCInfo, detailInfo);
//					isCredit = true;
//				}
//				 else {
//					isCredit = false;
//				 }
//			 }
			 if(c == null){
				 isCredit = true;
			 }
			 else{
				 isCredit = false;
			 }
		 }
		 catch (Exception e) {
			 throw new IException("��ѯ�ϼ��ͻ����ų���");
		 }
		 return isCredit;
    }
	
	/**
	 * 
	 * ���ͻ�������������ʱ�ͻ��������¼��ͻ��Ƿ�����������
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isChildCredit(CreditLimitDetailInfo detailInfo) throws IException{
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
			 
			 Collection c = dao.findByDateOptionAndChildClient(detailInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
			 if(c == null){
				 isCredit = true;
			 }
			 else{
				 isCredit = false;
			 }
		 }
		 catch (Exception e) {
			 throw new IException("��ѯ�¼��ͻ����ų���");
		 }
		 return isCredit;
	}
	
	/***************************************************************************/
	
	/**
	 * ����������Ϣ
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long saveCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			// �ж��Ƿ��������Ŷ������
			Collection c = dao.findByDateOption(info, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, false);
			if (c != null) {
				throw new IException("Loan_E121");
			}
			
			//���ͻ��������ϼ��ͻ��Ƿ���������������
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("�ͻ��ϼ������ù���������");
			}
			
			//���ͻ�������������ʱ�ͻ��������¼��ͻ��Ƿ�����������
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("�ͻ��¼������ù�����");
				}
			}

			lID = dao.add(info);
			
			if(info.getInutParameterInfo()!=null){
				
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(lID));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// �ύ����
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// ����״̬��"������"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
				info.setId(lID);
				dao.update(info);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("�������Ŷ������ʧ�� ��" + e.getMessage(),e);
		}
		return lID;
	}
	
	/**
	 * �޸�������Ϣ
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long updateCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			//���ͻ��������ϼ��ͻ��Ƿ���������������
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("�ͻ��ϼ������ù���������");
			}
			
			//���ͻ�������������ʱ�ͻ��������¼��ͻ��Ƿ�����������
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("�ͻ��¼������ù�����");
				}
			}
			
			if(info.getInutParameterInfo() != null){
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(info.getId()));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// �ύ����
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// ����״̬��"������"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
			}
			dao.update(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("�������Ŷ����Ϣ����",e);
		}
		return lID;
	}
	
	/**
	 * ɾ��������Ϣ
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long deleteCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			dao.update(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("ɾ�����Ŷ����Ϣ����",e);
		}
		return lID;
	}
	
	/**
	 * �������Ŷ��ID��ѯ���������Ϣ��������ȵ�ʹ�������
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitInfo findCreditLimitInfoByID(long id) throws IException {
		
		CreditLimitSimpleDAO dao = new CreditLimitSimpleDAO();
		CreditLimitInfo info = new CreditLimitInfo();
		try{
			
			info = (CreditLimitInfo) dao.findByID(id, CreditLimitInfo.class);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("��ѯ���������Ϣ����",e);
		}
		return info;
	}
	
	/**
	 * ͨ��ID���������Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitDetailInfo findCreditLimitDetailInfoByID(long id) throws IException {
		
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		CreditLimitDetailInfo cinfo = new CreditLimitDetailInfo();

		try{
			cinfo = (CreditLimitDetailInfo) dao.findByID(id, CreditLimitDetailInfo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("���������Ϣ����",e);
		}
		return cinfo;
	}
	
	public void doApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo returnInfo = new InutParameterInfo();
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		
		try {
			returnInfo = FSWorkflowManager.doApproval(info.getInutParameterInfo());

			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.CHECK);
				dao.updateStatusID(info);
				
			}
			
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.SAVE);
				dao.updateStatusID(info);
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException("������������ʧ��",ex);
		}
	}
	
	public void cancelApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		
		try {
			if(info.getActiveStatusID() == LOANConstant.CreditStatus.ACTIVE){
				throw new IException("���ż�¼�ѱ������ȡ������");
			}
			
			info.setStatusID(LOANConstant.LoanStatus.SAVE);
			dao.updateStatusID(info);
			
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			else {
				throw new Exception();
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException(ex.getMessage(),ex);
		}
	}
	
	
	
	/*******************************************************************/
	/*************        ��������������       *****************************/
	
	//ת��һ������IDΪһ������ID
	public long switchLoanTypeId(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}
	
	//ת��һ������IDΪһ������ID
	public long switchCreditTypeId(long creditTypeID) {

		long ret = -1;
		switch ((int) creditTypeID) {
		case (int) LOANConstant.CreditProduct.ZY:
			ret = LOANConstant.LoanType.ZY;
			break;
		case (int) LOANConstant.CreditProduct.SP:
			ret = LOANConstant.LoanType.TX;
			break;
		case (int) LOANConstant.CreditProduct.BH:
			ret = LOANConstant.LoanType.DB;
			break;
		}
		return ret;
	}
	
	/**
	 * �ۺ�����ʱ��������Ų�Ʒ�ķ�������
	 */
	public boolean checkCreditProduct(long officeId, long currencyId, long creditTypeId) throws IException {
		boolean isCheck = false;
		CreditProductInfo creditProductInfo = null; 
		try {
			if(creditTypeId == LOANConstant.CreditProduct.ZHSX){
				long lArrayID[] = new long[3];
				lArrayID[0] = LOANConstant.CreditProduct.ZY;
				lArrayID[1] = LOANConstant.CreditProduct.SP;
				lArrayID[2] = LOANConstant.CreditProduct.BH;
				
				for(int i=0; i<lArrayID.length; i++){
					CreditProductInfo qInfo = new CreditProductInfo();
					qInfo.setOfficeID(officeId);
					qInfo.setCurrencyID(currencyId);
					qInfo.setCreditTypeID(lArrayID[i]);
					
					creditProductInfo = this.findCreditProductInfo(qInfo);
					
					if (creditProductInfo != null && creditProductInfo.getId() > 0) {
						if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.NO) {
							isCheck = false;
							throw new IException("�������Ų�Ʒ���������ж�ȿ���");
						}
					} else {
						isCheck = false;
						throw new IException("�������������Ų�Ʒ����");
					}
				}
				
				isCheck = true;
			}
			else {
				isCheck = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	
	/**
	 * ���Ž����ڿ���������
	 * 
	 */
	public boolean checkCreditLimit(LoanApplyInfo applyInfo) throws IException {
		boolean isCheck = false; 
		try {
			// ��ȡ�����ļ��ж��Ƿ��д�������ҵ��
			boolean integrationcredit = Config.getBoolean(ConfigConstant.LOAN_CREDIT_INTEGRATIONCREDIT, false);
			
			if(integrationcredit){
				long creditTypeId = this.switchLoanTypeId(applyInfo.getTypeID());
				
				//Ȼ��Ѵ�������Ŀ�ʼ���ڷ��뵽CreditLimitInfo������
				CreditLimitInfo queryCreditInfo = new CreditLimitInfo();
				queryCreditInfo.setOfficeID(applyInfo.getOfficeID());
				queryCreditInfo.setCurrencyID(applyInfo.getCurrencyID());
				queryCreditInfo.setStartDate(applyInfo.getStartDate());
				queryCreditInfo.setCreditTypeID(creditTypeId);
				queryCreditInfo.setClientID(applyInfo.getBorrowClientID());
				
				// �Ƿ���й���Ʒ�ķ�������
				// ���Ҷ�Ӧ�Ĵ�������
				CreditProductInfo qInfo = new CreditProductInfo();
				qInfo.setOfficeID(applyInfo.getOfficeID());
				qInfo.setCurrencyID(applyInfo.getCurrencyID());
				qInfo.setCreditTypeID(creditTypeId);
				
				CreditProductInfo creditProductInfo = this.findCreditProductInfo(qInfo);
				
				if (creditProductInfo != null && creditProductInfo.getId() > 0) {
					if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.YES) {
						return this.checkCreditAmountAndDate(queryCreditInfo, creditProductInfo,applyInfo);
					}
					else {
						isCheck = true;
					}
				} else {
					isCheck = false;
					throw new IException("���������Ų�Ʒ����");
				}
			}

			isCheck = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * �жϴ�������������Ƿ������ŵ�����֮��
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkCreditAmountAndDate(CreditLimitInfo queryCreditInfo, CreditProductInfo creditProductInfo, LoanApplyInfo applyInfo)
			throws IException {
		boolean isCheck = false;
		CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		CreditLimitInfo creditInfo = null;
		
		//������׼���
		double examineAmount = 0;
		//���Ž��
	  	double creditAmount = 0;
	  	//�ͻ���ռ�õ��ܽ��
	  	double countAmount = 0;
	  	//�ͻ���ʷռ�õ��ܽ��
	  	double historyCountAmount = 0;
	  	//ռ�ý���ܺ�
	  	double sumAmount = 0;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			// �жϴ�������Ŀ�ʼ�����Ƿ������ŵ�����֮��
			creditInfo = creditLimitSimpleDAO.findDateByCreditLimit(queryCreditInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			
			if (creditInfo != null) {
				  //�����ͻ��Լ������ż�¼
				  //������׼���
				  examineAmount = applyInfo.getExamineAmount();
				  //���Ž��
		    	  creditAmount = creditInfo.getAmount();
		    	  //�ͻ���ռ�õ��ܽ��
		    	  countAmount = this.getClientCountAmount(creditInfo);
		    	  //�ͻ���ʷռ�õ��ܽ��
		    	  historyCountAmount = this.getHistoryClientCountAmount(creditInfo);
		    	  
		    	  sumAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
		    	  sumAmount = UtilOperation.Arith.add(sumAmount, historyCountAmount);
		    	  
		    	  System.out.println("��ѯ���ͣ�" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
		    	  System.out.println("������׼��� examineAmount��" + examineAmount);
		    	  System.out.println("ռ�ö�ȱ��� engrossRate��" + creditProductInfo.getEngrossRate());
		    	  System.out.println("�ͻ���ռ�õ��ܽ�� countAmount��" + countAmount);
		    	  System.out.println("�ͻ���ʷռ�õ��ܽ�� historyCountAmount��" + historyCountAmount);
		    	  System.out.println("ռ�ý���ܺ� sumAmount��" + sumAmount);
		    	  System.out.println("���Ž�� creditAmount��" + creditAmount);
	
		    	  if(Double.compare(sumAmount, creditAmount) > 0){
		    		  // ����Ǹ��Կ���,����һ����ʶλΪtrue,��������Կ���,����һ����ʶλΪfalse
		    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
		    			  isCheck = false;
		    			  throw new IException("������Ľ����������");
		    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
		    			  isCheck = false;
		    			  throw new IException("CREDIT_1");
		    		  }
		    	  }
		    	  else {
		    		  isCheck = true;
		    	  }
			}
			else {
				CorporationDAO corporationDAO = new CorporationDAO();
				CorporationInfo corporationInfo = corporationDAO.findByclietID(queryCreditInfo.getClientID());
				creditInfo = this.getParentCreditLimitInfo(corporationInfo, queryCreditInfo);

				if(creditInfo != null){
					  //�����ͻ��Լ������ż�¼
					  //������׼���
					  examineAmount = applyInfo.getExamineAmount();
					  //���Ž��
			    	  creditAmount = creditInfo.getAmount();
			    	  //�ͻ���ռ�õ��ܽ��
			    	  countAmount = this.getClientCountAmount(creditInfo);
			    	  //�ͻ���ʷռ�õ��ܽ��
			    	  historyCountAmount = this.getHistoryClientCountAmount(creditInfo);
			    	  
			    	  sumAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
			    	  sumAmount = UtilOperation.Arith.add(sumAmount, historyCountAmount);
			    	  
			    	  System.out.println("��ѯ���ͣ�" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
			    	  System.out.println("������׼��� examineAmount��" + examineAmount);
			    	  System.out.println("ռ�ö�ȱ��� engrossRate��" + creditProductInfo.getEngrossRate());
			    	  System.out.println("�ͻ���ռ�õ��ܽ�� countAmount��" + countAmount);
			    	  System.out.println("�ͻ���ʷռ�õ��ܽ�� historyCountAmount��" + historyCountAmount);
			    	  System.out.println("ռ�ý���ܺ� sumAmount��" + sumAmount);
			    	  System.out.println("���Ž�� creditAmount��" + creditAmount);
		
			    	  if(Double.compare(sumAmount, creditAmount) > 0){
			    		  // ����Ǹ��Կ���,����һ����ʶλΪtrue,��������Կ���,����һ����ʶλΪfalse
			    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
			    			  isCheck = false;
			    			  throw new IException("������Ľ����������");
			    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
			    			  isCheck = false;
			    			  throw new IException("CREDIT_1");
			    		  }
			    	  }
			    	  else {
			    		  isCheck = true;
			    	  }
				}
				else {
					isCheck = false;
					//throw new IException("���뿪ʼ���������������û�����ż�¼");
					throw new IException("CREDIT_2");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * ȡ���ͻ���ռ�õĽ��
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//�ͻ���ռ�õ�������
	  	double applyAmount = 0;
	  	//�ͻ���ռ�õĺ�ͬ���
	  	double contractAmount = 0;
	  	//��ѭ������ʱ������
	  	double circleAmount = 0;
	  	//�ͻ���ռ�õ��ܽ��
	  	double countAmount = 0;
	  	
	  	CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			if (creditInfo != null) {
				  // �ж��Ƿ����ۺ����ţ���������������Ƿ���й���Ʒ�ķ�������
//				  if(this.checkCreditProduct(creditInfo.getOfficeID(), creditInfo.getCurrencyID(), creditInfo.getCreditTypeID()) == false){
//	    			  throw new IException("���Ų�Ʒ�������ò�����");
//				  }
	
		    	  //���ݿͻ���Ϣ��ѯ����������
		    	  applyAmount = creditLimitSimpleDAO.getClientLoanApplyAmount(creditInfo, false);
	
		    	  //���ݿͻ���Ϣ��ѯ�����ͬ���
		    	  contractAmount = creditLimitSimpleDAO.getClientLoanContractAmount(creditInfo, false);
		    	  
		    	  //��Ӫ�����ѭ������ʱ������
		    	  circleAmount = creditLimitSimpleDAO.getClientCircleLoanAmount(creditInfo, false);
		    	  
		    	  //�ܼƽ��
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("�ͻ���ռ�õ������� applyAmount��" + applyAmount);
		    	  System.out.println("�ͻ���ռ�õĺ�ͬ��� contractAmount��" + contractAmount);
		    	  System.out.println("��ѭ������ʱ������ circleAmount��" + circleAmount);
		    	  System.out.println("�ͻ�ռ���ܽ�� countAmount��" + countAmount);
			}
			else {
				throw new IException("���ż�¼����");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * ȡ����ʷ�ͻ���ռ�õĽ��
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getHistoryClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//�ͻ���ʷ��ռ�õ�������
	  	double applyAmount = 0;
	  	//�ͻ���ʷ��ռ�õĺ�ͬ���
	  	double contractAmount = 0;
	  	//��ʷ��ѭ������ʱ������
	  	double circleAmount = 0;
	  	//�ͻ���ռ�õ��ܽ��
	  	double countAmount = 0;
	  	
	  	CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			if (creditInfo != null) {
	
		    	  //���ݿͻ���Ϣ��ѯ��ʷ����������
		    	  applyAmount = creditLimitSimpleDAO.getClientLoanApplyAmount(creditInfo, true);
	
		    	  //���ݿͻ���Ϣ��ѯ��ʷ�����ͬ���
		    	  contractAmount = creditLimitSimpleDAO.getClientLoanContractAmount(creditInfo, true);
		    	  
		    	  //��ʷ��Ӫ�����ѭ������ʱ������
		    	  circleAmount = creditLimitSimpleDAO.getClientCircleLoanAmount(creditInfo, true);
		    	  
		    	  //�ܼƽ��
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("�ͻ���ʷ��ռ�õ������� applyAmount��" + applyAmount);
		    	  System.out.println("�ͻ���ʷ��ռ�õĺ�ͬ��� contractAmount��" + contractAmount);
		    	  System.out.println("��ʷ��ѭ������ʱ������ circleAmount��" + circleAmount);
		    	  System.out.println("�ͻ�ռ���ܽ�� countAmount��" + countAmount);
			}
			else {
				throw new IException("���ż�¼����");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * 
	 * ���ҿͻ����ϼ��ͻ��Ƿ��������������ţ���ȡ���ϼ��ͻ��ļ�������
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public CreditLimitInfo getParentCreditLimitInfo(CorporationInfo corporationInfo, CreditLimitInfo limitInfo) throws IException
	{    
		CreditLimitInfo returnCInfo = null;
		 try {
			 CreditLimitSimpleDAO CreditLimitSimpleDAO = new CreditLimitSimpleDAO();
			 CreditLimitInfo  queryinfo = new CreditLimitInfo();
			 queryinfo.setOfficeID(limitInfo.getOfficeID());
			 queryinfo.setCurrencyID(limitInfo.getCurrencyID());
			 queryinfo.setCreditTypeID(limitInfo.getCreditTypeID());
			 queryinfo.setStartDate(limitInfo.getStartDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 returnCInfo = CreditLimitSimpleDAO.findDateByCreditLimit(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
//			 if(corporationInfo.getParentCorpID1() == -1){
//				 return returnCInfo;
//			 }
//			 else {
//				if(returnCInfo == null){
//					CorporationDAO corporationDAO = new CorporationDAO();
//					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
//					returnCInfo = getParentCreditLimitInfo(parentCInfo, limitInfo);
//				}
//				 else {
//					return returnCInfo;
//				 }
//			 }
		 }
		 catch (Exception e) {
			 throw new IException("�����ϼ��ͻ����ų���");
		 }
		 return returnCInfo;
    }
	
	
	/**
	 * ��ѯ���Ŷ����Ϣ(����/ȡ������)
	 * 
	 */
	public Collection findLimitDetailInfoByCondition(CreditLimitDetailInfo qInfo)
			throws IException {
		Collection c = null;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ѯ������Ϣ����",e);
		}
		return c;
	}
	
	/**
	 * ��ѯ���ż�¼��Ϣ
	 * 
	 */
	public PageLoader findLimitDetailHistoryInfoByCondition(CreditLimitDetailInfo qInfo)
			throws IException {
		PageLoader pageLoader = null;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		try {
			 pageLoader = dao.findByHistoryMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ѯ������Ϣ����",e);
		}
		return pageLoader;
	}
	
	
	/**
	 * ��ѯ���Ŷ����Ϣ
	 * 
	 */
	public Collection findLimitInfoByCondition(CreditLimitInfo qInfo)
			throws Exception {
		Collection c = null;
		CreditLimitSimpleDAO dao = new CreditLimitSimpleDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ѯ���Ŷ����Ϣ����",e);
		}
		return c;
	}
	
	/**
	 * ����/ȡ�����Ŷ�ȱ����Ϣ
	 * 
	 * @param info
	 * @throws IException
	 * @throws Exception
	 */
	public void activeCreditLimitDetail(long creditId, long activeUserId)
		throws IException {
		
		CreditLimitSimpleDAO creditDao = null;
		CreditLimitDetailSimpleDAO creditDetailDao = null;
		CreditLimitInfo creditInfo = null;
		CreditLimitDetailInfo creditDetailInfo = null;
		Timestamp tsTime = Env.getSystemDateTime();
		long lReturn = -1;
		
		try {
			creditDao = new CreditLimitSimpleDAO();
			creditDetailDao = new CreditLimitDetailSimpleDAO();

			creditDetailInfo = (CreditLimitDetailInfo)creditDetailDao.findByID(creditId, CreditLimitDetailInfo.class);
			if(creditDetailInfo == null) {
				throw new IException("�����ҵ�������ϸ��Ϣ������ʧ��");
			}
			
			if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.XINZENG){
				//�������������Ϣ
				creditInfo = new CreditLimitInfo();
				creditInfo.setOfficeID(creditDetailInfo.getOfficeID());
				creditInfo.setCurrencyID(creditDetailInfo.getCurrencyID());
				creditInfo.setClientID(creditDetailInfo.getClientID());
				creditInfo.setAmount(creditDetailInfo.getChangeAmount());
				creditInfo.setCreditTypeID(creditDetailInfo.getCreditTypeID());
				creditInfo.setCreditModeID(creditDetailInfo.getCreditModeID());
				creditInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditInfo.setStartDate(creditDetailInfo.getStartDate());
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setInputUserID(creditDetailInfo.getInputUserID());
				creditInfo.setInputDate(tsTime);
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				lReturn = creditDao.add(creditInfo);
				
				//�޸�������ϸ��Ϣ
				creditDetailInfo.setCreditLimitID(lReturn);
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}
			else {
				creditInfo = (CreditLimitInfo)creditDao.findByID(creditDetailInfo.getCreditLimitID(), CreditLimitInfo.class);
				if(creditInfo == null) {
					throw new IException("�����ҵ�������Ϣ������ʧ��");
				}
				
				double initAmount = creditInfo.getAmount();
				
				if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.JIA){
					initAmount = initAmount + creditDetailInfo.getChangeAmount();
				}
				else {
					initAmount = initAmount - creditDetailInfo.getChangeAmount();
				}
				
				//�޸�����������Ϣ
				creditInfo.setAmount(initAmount);
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				creditDao.update(creditInfo);
				
				//�޸�������ϸ��Ϣ
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}
	
	public long getCreditSubLoanTypeId(long loanTypeId,long lOfficeID,long lCurrencyID)
		throws IException {
		long subLoanTypeId = -1;
		try {
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			Collection coll = biz.findByLoanTypeID(loanTypeId,lOfficeID,lCurrencyID);
			if(coll != null){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					LoanTypeSettingInfo info = (LoanTypeSettingInfo)it.next();
					subLoanTypeId = info.getId();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return subLoanTypeId;
	}
	//���ż�¼��Ϣ����
	public String getCreditLimitDetailOrderSQL(long lOrder, long lDesc)
	{
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		return dao.getCreditLimitDetailOrderSQL(lOrder, lDesc);
	}
}	
	
