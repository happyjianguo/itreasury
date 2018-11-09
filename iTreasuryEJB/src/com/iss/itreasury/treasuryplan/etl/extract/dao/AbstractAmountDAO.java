/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-19
 */
package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;


/**
 * @author yehuang
 *
 *金额数据访问对象的抽象基类
 */
public abstract class AbstractAmountDAO extends TreasuryPlanDAO {
	public AbstractAmountDAO(String strTableName,Connection conn){
		super(strTableName,conn);		
	}
	
	public void setAutoCommit(boolean bAutoCommit) throws Exception{
		tpConnection.setAutoCommit(bAutoCommit);
	}
	
	public void commit() throws Exception{
		tpConnection.commit();
	}
	
	public AbstractAmountDAO(String strTableName){
		super(strTableName);		
		super.setUseMaxID();
	}	
	
	public String amountFieldName = "";
	
	/**
	 * 根据一条取数逻辑，去Trea_ActualAmunt / Trea_ForecastAmount中，计算一条取数逻辑某一天的金额
	 * @param officeID
	 * @param currencyID
	 * @param forcastDate
	 * @param templateItemInfo
	 * @return
	 * @throws Exception
	 */
	public double sumAmountDependOnTemplateItem(long officeID,long currencyID/*, Timestamp currentDate*/,Timestamp forcastDate,TPTemplateItemInfo templateItemInfo) throws Exception{
		double amount = 0.0;
		try {
			initDAO();
			//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
			String strForcastDate = transferTimestampToTo_DateString(forcastDate);

			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("Select sum("+amountFieldName+") "+amountFieldName+" from "+strTableName+" where \n ");
			bufferSQL.append(strTableName+".Officeid= "+officeID+" and "+strTableName+".Currencyid= " + currencyID + " \n ");
			bufferSQL.append(" and "+strTableName+".TransactionDate = "+strForcastDate+" \n");
			if(templateItemInfo.getModuleID() > 0)
			{
				bufferSQL.append(" and  "+strTableName+".ModuleID = " + templateItemInfo.getModuleID());
			}
			if(templateItemInfo.getClientCode() != null)
			{
				bufferSQL.append(" and  "+strTableName+".ClientCode = '" + templateItemInfo.getClientCode()+ "'") ;
			}
			if(templateItemInfo.getAccountCode() != null)
			{
				bufferSQL.append(" and  "+strTableName+".AccountCode = '" + templateItemInfo.getAccountCode()+ "'");
			}
			if(templateItemInfo.getContractCode() != null)
			{
				bufferSQL.append(" and  "+strTableName+".ContractCode =' " + templateItemInfo.getContractCode()+ "'");
			}
			if(templateItemInfo.getPayFormCode() != null)
			{
				bufferSQL.append(" and  "+strTableName+".PayFormCode = '" + templateItemInfo.getPayFormCode()+ "'");
			}
			if(templateItemInfo.getCounterpartName() != null)
			{
				bufferSQL.append(" and  "+strTableName+".CounterpartName = '" + templateItemInfo.getCounterpartName()+ "'");
			}
			if(templateItemInfo.getSecuritiesName() != null)
			{
				bufferSQL.append(" and  "+strTableName+".SecuritiesName = '" + templateItemInfo.getSecuritiesName()+ "'");
			}
			if(templateItemInfo.getAccountTypeId() > 0)
			{
				if( templateItemInfo.getModuleID() == Constant.ModuleType.SECURITIES )
				{
					//如果数据来源是证券模块，则匹配业务类型和交易类型
					bufferSQL.append(" and  substr("+strTableName+".AccountTypeId,0,2) = " + templateItemInfo.getAccountTypeId());
				}
				else
				{
					bufferSQL.append(" and  "+strTableName+".AccountTypeId = " + templateItemInfo.getAccountTypeId());
				}
			}
			if(templateItemInfo.getGlSubjectCode() != null)
			{
				bufferSQL.append(" and  "+strTableName+".GlSubjectCode = '" + templateItemInfo.getGlSubjectCode()+ "'");
			}
			if(templateItemInfo.getAmountDirection() > 0)
			{
				bufferSQL.append(" and  "+strTableName+".AMOUNTDIRECTION = " + templateItemInfo.getAmountDirection());
			}

			
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			
			System.out.println("-----------SQL  "+bufferSQL.toString());
			ResultSet localRS = executeQuery();
			
			if(localRS.next())
				amount = localRS.getDouble(amountFieldName);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		System.out.println("-----------amount"+amount);
		return amount;
	}	
	
	/**
	 * 根据正在执行的抽取操作的类别获取应该使用的DAO的实例
	 * */
	public static AbstractAmountDAO getAmountDAOInstance(long extractingTypeID,Connection conn){
		AbstractAmountDAO amountDAO = null;
		if(extractingTypeID == AbstractTransformer.Extracting_Type_Actual){
			amountDAO = new Trea_ActualAmountDAO(conn);
		}else if(extractingTypeID == AbstractTransformer.Extracting_Type_Forecast){
			amountDAO = new Trea_ForecastAmountDAO(conn);
		}else{
		}
		return amountDAO;
	} 
	
	public void deleteByTransactionDateAndModuleID(Timestamp startDate,Timestamp endDate,long moduleID,long officeID,long currencyID) throws Exception{
		String strStartDate =  this.transferTimestampToTo_DateString(startDate);
        String strEndDate =  this.transferTimestampToTo_DateString(endDate);
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append("delete from ");
		bufferSQL.append(strTableName);
		bufferSQL.append(" where TRANSACTIONDATE >= ");
		bufferSQL.append(strStartDate);
		bufferSQL.append(" and TRANSACTIONDATE <=");
        bufferSQL.append(strEndDate);
		bufferSQL.append(" and MODULEID = "+moduleID);
		bufferSQL.append(" and ");
		bufferSQL.append(" officeID = "+officeID);
		bufferSQL.append(" and ");
		bufferSQL.append(" currencyID = "+currencyID);				
		try {
			this.prepareStatement(bufferSQL.toString());
			this.executeQuery();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		finalizeDAO();
	}
}
