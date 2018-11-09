package com.iss.itreasury.credit.setting.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountSetupInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.util.LOANConstant;

public class SubAmountSetupDao extends ITreasuryDAO {

	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	public SubAmountSetupDao() {
		super("CREDIT_SUBAMOUNTSETUP");
	}

	public SubAmountSetupDao(Connection conn) {
		super("CREDIT_SUBAMOUNTSETUP", conn);
	}
	
	/**
	 * �������ݿ���������£�
	 * @param amountSetupId
	 * @throws ITreasuryDAOException
	 */
	public void updateSubAmountSetup(SubAmountSetupInfo sasInfo)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.CREDITSHARE = ?,");
	        	strSQL.append(" t.CREDITAMOUNT = ?,");
	        	strSQL.append(" t.EXCESSPERCENT = ?,");
	        	strSQL.append(" t.STATE = ?");
	        	strSQL.append(" where t.ID = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("SubAmountSetupDao.updateSubAmountSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, sasInfo.getCreditShare());
	        	transPS.setDouble(nIndex++, sasInfo.getCreditAmount());
	        	transPS.setDouble(nIndex++, sasInfo.getExcessPercent());
	        	transPS.setLong(nIndex++, sasInfo.getState());
	        	transPS.setLong(nIndex++, sasInfo.getId());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("ִ��UPDATE���ʧ��", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("���ݿ�����쳣",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * �������ݿ����������ɾ����
	 * @param amountSetupId
	 * @throws ITreasuryDAOException
	 */
	public void deleteBatch(long amountSetupId)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.STATE = ?");
	        	strSQL.append(" where t.AMOUNTSETUPID = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("SubAmountSetupDao.deleteBatch()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.DELETE);
	        	transPS.setLong(nIndex++, amountSetupId);
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("ִ��UPDATE���ʧ��", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("���ݿ�����쳣",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * ��ѯ��һ�����ż�¼��Ʒ��������Ϣ
	 * 
	 * @param info ���ż�¼
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getSubCreditAmountSetupColl(AmountSetupInfo asInfo)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.* from " + this.strTableName + " t1");
	        	strSQL.append(" where t1.officeId = " + asInfo.getOfficeId());
	        	strSQL.append(" and t1.currencyId = " + asInfo.getCurrencyId());
	        	strSQL.append(" and t1.state = " + LOANConstant.CreditFlowStatus.SAVE);
	        	strSQL.append(" and t1.amountsetupid = ?");

		        prepareStatement(strSQL.toString());
				log4j.info("SubAmountSetupDao.getSubCreditAmountSetupColl()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, asInfo.getId());
		        
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(SubAmountSetupInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("���ݿ��ѯ�쳣", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("���ݿ��ѯ�쳣",e);
		}
		finally {
			finalizeDAO();
		}
		if(coll == null || coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}
}
