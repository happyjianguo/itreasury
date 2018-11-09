package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dataentity.PeriodSetInfo;

public class Sett_PeriodSetDao extends ITreasuryDAO
{

    public Sett_PeriodSetDao()
    {
        super("SETT_PERIODSETTING");
    }

    public Sett_PeriodSetDao(String tableName)
    {
        super(tableName);
    }

    public Sett_PeriodSetDao(String tableName, Connection conn)
    {
        super(tableName, conn);
    }
    
    public void clearDAO() throws ITreasuryDAOException
    {
        super.finalizeDAO();
    }
  
    public List findByCondition(PeriodSetInfo info)throws ITreasuryDAOException{
    	List list = new ArrayList();
    	PeriodSetInfo periodSetInfo = new PeriodSetInfo();
    	String strSql = "select * from sett_periodsetting s where s.statusid = 1 and s.officeid ="+info.getOfficeId()+" and s.currencyid ="+info.getCurrencyId();
    	initDAO();
    	try{
    		transPS = transConn.prepareStatement(strSql);
    		transRS = transPS.executeQuery();
    		while(transRS.next()){
    			periodSetInfo.setId(transRS.getLong(1));
    			periodSetInfo.setCurrencyId(transRS.getLong(2));
    			periodSetInfo.setOfficeId(transRS.getLong(3));
    			periodSetInfo.setPeriod(transRS.getLong(4));
    			periodSetInfo.setPeriodName(transRS.getString(5));
    			periodSetInfo.setStartDate(transRS.getTimestamp(6));
    			periodSetInfo.setOldPeriod(transRS.getLong(7));
    			periodSetInfo.setOldStartDate(transRS.getTimestamp(8));
    			periodSetInfo.setInputUserId(transRS.getLong(9));
    			periodSetInfo.setInputDate(transRS.getTimestamp(10));
    			periodSetInfo.setModifyUserId(transRS.getLong(11));
    			periodSetInfo.setModifyDate(transRS.getTimestamp(12));
    			periodSetInfo.setStatusId(transRS.getLong(13));
    			list.add(periodSetInfo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		finalizeDAO();
    	}
    	return list;
    }
    
    public long addPeropdSet(PeriodSetInfo info)throws ITreasuryDAOException{
    	setUseMaxID();
    	long id = -1;
    	try{
    		id = add(info);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return id;
    }
}