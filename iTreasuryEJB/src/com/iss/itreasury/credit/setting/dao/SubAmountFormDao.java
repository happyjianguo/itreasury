package com.iss.itreasury.credit.setting.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class SubAmountFormDao extends ITreasuryDAO {

	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	public SubAmountFormDao() {
		super("CREDIT_SUBAMOUNTFORM");
	}

	public SubAmountFormDao(Connection conn) {
		super("CREDIT_SUBAMOUNTFORM", conn);
	}
	
	/**
	 * 基本数据库操作（更新）
	 * @param amountSetupId
	 * @throws ITreasuryDAOException
	 */
	public void updateSubAmountForm(SubAmountFormInfo safInfo)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
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
				log4j.info("SubAmountFormDao.updateSubAmountForm()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, safInfo.getCreditShare());
	        	transPS.setDouble(nIndex++, safInfo.getCreditAmount());
	        	transPS.setDouble(nIndex++, safInfo.getExcessPercent());
	        	transPS.setLong(nIndex++, safInfo.getState());
	        	transPS.setLong(nIndex++, safInfo.getId());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库操作异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * 
	 * @param queryInfo
	 * @param lCreditType 贷款类型
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public SubAmountFormInfo getSubAmountFormInfo(AmountFormInfo queryInfo, long lCreditType)
		throws ITreasuryDAOException
	{
		SubAmountFormInfo safInfo = null;
		StringBuffer strSQL = null;
		try {
			  initDAO();
			  
        	strSQL = new StringBuffer();
        	strSQL.append(" select t1.* from " + this.strTableName + " t1");
        	strSQL.append(" where t1.officeId = ?");
        	strSQL.append(" and t1.currencyId = ?");
        	strSQL.append(" and t1.AMOUNTFORMID = ?");
        	strSQL.append(" and t1.CREDITTYPE = ?");
        	strSQL.append(" and t1.state = " + LOANConstant.CreditFlowStatus.SAVE);

	        prepareStatement(strSQL.toString());
			log4j.info("SubAmountFormDao.getSubAmountFormInfo()\n");
			log4j.info("strSQL=\n" + strSQL.toString());
	        
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, lCreditType);
			
        	executeQuery();
        	safInfo = (SubAmountFormInfo)getDataEntityFromResultSet(SubAmountFormInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		
		return safInfo;
	}
	
	/**
	 * 查询不共享的授信金额
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getCreditAmountByNotShare(AmountFormViewInfo queryInfo)
		throws ITreasuryDAOException
	{
		double dCreditAmount = 0.0;
		StringBuffer strSQL = null;
		
		try {
			
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select nvl(sum(t2.creditamount*t2.excesspercent/100), 0) creditamount from credit_amountform t1, " + this.strTableName + " t2");
	        	strSQL.append(" where t1.id = ?");
	        	strSQL.append(" and t1.officeid = ?");
	        	strSQL.append(" and t1.currencyId = ?");
	        	strSQL.append(" and t1.state = ?");
	        	strSQL.append(" and t2.state = ?");
	        	strSQL.append(" and t2.creditshare = ?");
	        	strSQL.append(" and t1.id = t2.amountformid");

	        	prepareStatement(strSQL.toString());
				log4j.info("SubAmountFormDao.getCreditAmountByNotShare()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, queryInfo.getId());
	        	transPS.setLong(nIndex++, queryInfo.getOfficeId());
	        	transPS.setLong(nIndex++, queryInfo.getCurrencyId());
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, LOANConstant.CreditShare.NO);
	        	
	        	executeQuery();
	        	while(transRS.next()){
	        		dCreditAmount = transRS.getDouble("creditamount");
	        	}
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return dCreditAmount;
	}
	
	/**
	 * 查询出一个授信额度的子设置
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getSubCreditAmountFormColl(AmountFormInfo afInfo)
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
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.* from " + this.strTableName + " t1");
	        	strSQL.append(" where t1.officeId = ?");
	        	strSQL.append(" and t1.currencyId = ?");
	        	strSQL.append(" and t1.state = ?");
	        	strSQL.append(" and t1.amountformid = ?");

		        prepareStatement(strSQL.toString());
				log4j.info("SubAmountFormDao.getSubCreditAmountFormColl()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, afInfo.getOfficeId());
	        	transPS.setLong(nIndex++, afInfo.getCurrencyId());
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, afInfo.getId());
		        
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(SubAmountFormInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
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
	public List getSubAmountFormList(long amountFormID)throws ITreasuryDAOException
	{
		StringBuffer strBufferSQL = new StringBuffer();
		SubAmountFormInfo subAmountFormInfo = null;
		List subAmountFormList = new ArrayList();
		
		try{
			strBufferSQL.append("SELECT S.ID AS SID,S.AMOUNTFORMID AS AMOUNTFORMID,S.CREDITTYPE AS CREDITTYPE,S.CREDITSHARE AS CREDITSHARE,\n");
			strBufferSQL.append("S.CREDITAMOUNT AS CREDITAMOUNT,S.EXCESSPERCENT AS EXCESSPERCENT,S.STATE AS STATE FROM CREDIT_SUBAMOUNTFORM S \n ");
			strBufferSQL.append(" WHERE S.AMOUNTFORMID = ? \n");
			initDAO();
			log4j.info("getSubAmountFormList---sql:::"+strBufferSQL.toString());
			this.prepareStatement(strBufferSQL.toString());
			this.transPS.setLong(1,amountFormID);
			this.executeQuery();
			while(this.transRS.next() && this.transRS != null)
			{
				subAmountFormInfo = new SubAmountFormInfo();
				subAmountFormInfo.setId(this.transRS.getLong("SID"));
				subAmountFormInfo.setAmountFormId(this.transRS.getLong("AMOUNTFORMID"));
				subAmountFormInfo.setCreditType(this.transRS.getLong("CREDITTYPE"));
				subAmountFormInfo.setCreditShare(this.transRS.getLong("CREDITSHARE"));
				subAmountFormInfo.setCreditAmount(this.transRS.getLong("CREDITAMOUNT"));
				subAmountFormInfo.setExcessPercent(this.transRS.getDouble("EXCESSPERCENT"));
				subAmountFormInfo.setState(this.transRS.getLong("STATE"));
				subAmountFormList.add(subAmountFormInfo);
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001",e);
			
		}finally{
			this.finalizeDAO();
		}
		return subAmountFormList;
	}

}
