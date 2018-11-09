package com.iss.itreasury.ebank.fundplan.dao;

import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.fundplan.dataentity.Sett_CapitalPlanSettingInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class Sett_CapitalPlanSettingDAO extends ITreasuryDAO {

	
	
	private StringBuffer m_sbSelect  = null;
	
	private StringBuffer m_sbFrom    = null;
	
	private StringBuffer m_sbWhere   = null;
	
	private StringBuffer m_sbOrderBy = null;
	
	
	
	public Sett_CapitalPlanSettingDAO()
	{
		super("SETT_CAPITALPLANSETTING");
	}
	
	
	public long doInsert(Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo)throws Exception
	{
	   int result = 0;
	   long maxID = -1;
	   try{
		   
		   maxID = getMaxID();
		   initDAO();
		   this.prepareStatement("INSERT INTO SETT_CAPITALPLANSETTING(ID,CLIENTID,ISCHECKPLAN)VALUES(?,?,?)");
		   this.transPS.setLong(1,maxID);
		   this.transPS.setLong(2,sett_CapitalPlanSettingInfo.getClientID());
		   this.transPS.setLong(3,sett_CapitalPlanSettingInfo.getIsCheckPlan());
		   result = this.executeUpdate();
		   
	   }catch(Exception e){
		   
		  e.printStackTrace();
		  throw new IException("Gen_E001",e);
		   
	   }finally{
		   this.finalizeDAO();
	   }
	   
	   return (result > 0?maxID:-1);
	}
	public long doUpdate(long ID,long isCheckPlan)throws Exception
	{
	   int result = 0;
	   try{
		   
		   initDAO();
		   this.prepareStatement("UPDATE SETT_CAPITALPLANSETTING SET ISCHECKPLAN = ? WHERE ID = ?");
		   this.transPS.setLong(1,isCheckPlan);
		   this.transPS.setLong(2,ID);
		   result = this.executeUpdate();
		   
	   }catch(Exception e){
		   
		  e.printStackTrace();
		  throw new IException("Gen_E001",e);
		   
	   }finally{
		   this.finalizeDAO();
	   }
	   return (result > 0?ID:-1);
	}
    public boolean isClientExist(long nClientID)throws Exception
    {
    	boolean flag = false;
    	try{
    		initDAO();
    		this.prepareStatement(" SELECT COUNT(*) AS NCOUNT FROM SETT_CAPITALPLANSETTING S WHERE S.CLIENTID = ?");
    		this.transPS.setLong(1,nClientID);
    		this.executeQuery();
    		while(this.transRS.next() && this.transRS != null)
    		{
    			if(this.transRS.getLong("NCOUNT") > 0)
    			{
    				flag = true;
    			}
    		}
    		
    	}catch(Exception e){
            e.printStackTrace();
            throw new IException("客户已存在不能进行保存！",e);
    	}finally{
    		this.finalizeDAO();
    	}
    	return flag;
    }
	public Sett_CapitalPlanSettingInfo findByID(long ID)throws Exception
	{
		Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo = null;
		StringBuffer bufferSQL = new StringBuffer();
		try{
			bufferSQL.append(" SELECT S.ID AS ID,C.ID AS CLIENTID,C.SCODE AS CLIENTCODE,C.SNAME AS CLIENTNAME,S.ISCHECKPLAN AS ISCHECKPLAN ");
			bufferSQL.append(" FROM SETT_CAPITALPLANSETTING S,CLIENT C WHERE C.ID = S.CLIENTID AND S.ID = ?");
			initDAO();
			this.prepareStatement(bufferSQL.toString());
			this.transPS.setLong(1,ID);
			this.executeQuery();
			if(this.transRS != null && this.transRS.next())
			{
				sett_CapitalPlanSettingInfo = new Sett_CapitalPlanSettingInfo();
				sett_CapitalPlanSettingInfo.setID(transRS.getLong("ID"));
				sett_CapitalPlanSettingInfo.setClientID(transRS.getLong("CLIENTID"));
				sett_CapitalPlanSettingInfo.setClientCode(transRS.getString("CLIENTCODE"));
				sett_CapitalPlanSettingInfo.setClientName(transRS.getString("CLIENTNAME"));
				sett_CapitalPlanSettingInfo.setIsCheckPlan(transRS.getLong("ISCHECKPLAN"));
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.finalizeDAO();
		}
		
		return sett_CapitalPlanSettingInfo;
	}
	
	public boolean isAutoCheck(long nClientID)throws Exception
	{
		boolean flag = false;
		
		try{
			initDAO();
			this.prepareStatement("SELECT S.ISCHECKPLAN AS ISCHECKPLAN FROM SETT_CAPITALPLANSETTING S WHERE S.CLIENTID = ?");
			this.transPS.setLong(1,nClientID);
			this.executeQuery();
			if(this.transRS != null && this.transRS.next())
			{
				if(this.transRS.getLong("ISCHECKPLAN") == 1)
				{
					flag = true;
				}
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.finalizeDAO();
			
		}
		return flag;
	}
	
	
	public PageLoader getCapitalPlanByCondition(Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo)throws Exception
	{
		    getCapitalPlanByConditionSQL(sett_CapitalPlanSettingInfo);
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	        pageLoader.initPageLoader(new AppContext(),m_sbFrom.toString(),m_sbSelect.toString(),m_sbWhere.toString(),
					                  (int) Constant.PageControl.CODE_PAGELINECOUNT,"com.iss.itreasury.ebank.fundplan.dataentity.Sett_CapitalPlanSettingInfo",null);
	        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
	        
        return pageLoader;
	}
	
	private void getCapitalPlanByConditionSQL(Sett_CapitalPlanSettingInfo sett_CapitalPlanSettingInfo)throws Exception
	{
        m_sbSelect = new StringBuffer();
		
		m_sbSelect.append(" S.ID AS ID,C.ID AS CLIENTID,C.SCODE AS CLIENTCODE,C.SNAME AS CLIENTNAME,S.ISCHECKPLAN AS ISCHECKPLAN,");
		
		m_sbSelect.append("(SELECT E1.NAME FROM CLIENT_EXTENDATTRIBUTE E1 WHERE E1.ATTRIBUTEID = 1 AND E1.STATUSID = 1 ");
		m_sbSelect.append(" AND E1.OFFICEID = " + sett_CapitalPlanSettingInfo.getNOfficeID() + " AND E1.CURRENCYID = " + sett_CapitalPlanSettingInfo.getNCurrencyID());
		m_sbSelect.append(" AND E1.ID = C.NCLIENTTYPEID1) AS CLIENTTYPENAME1,");
		
		m_sbSelect.append("(SELECT E2.NAME FROM CLIENT_EXTENDATTRIBUTE E2 WHERE E2.ATTRIBUTEID = 2 AND E2.STATUSID=1 ");
		m_sbSelect.append(" AND E2.OFFICEID = " + sett_CapitalPlanSettingInfo.getNOfficeID() + " AND E2.CURRENCYID = " + sett_CapitalPlanSettingInfo.getNCurrencyID());
		m_sbSelect.append(" AND E2.ID = C.NCLIENTTYPEID2) AS CLIENTTYPENAME2,");
		
		m_sbSelect.append("(SELECT E3.NAME FROM CLIENT_EXTENDATTRIBUTE E3 WHERE E3.ATTRIBUTEID = 3 AND E3.STATUSID=1 ");
		m_sbSelect.append(" AND E3.OFFICEID = " + sett_CapitalPlanSettingInfo.getNOfficeID() + " AND E3.CURRENCYID = " + sett_CapitalPlanSettingInfo.getNCurrencyID());
		m_sbSelect.append(" AND E3.ID = C.NCLIENTTYPEID3) AS CLIENTTYPENAME3 ");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_CAPITALPLANSETTING S,CLIENT C");

		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" C.ID = S.CLIENTID ");
		
		
		if(!sett_CapitalPlanSettingInfo.getClientCodeFromCtrl().equals(""))
		{
		   m_sbWhere.append(" AND C.SCODE >= '" + sett_CapitalPlanSettingInfo.getClientCodeFromCtrl()+"'");
		}
		if(!sett_CapitalPlanSettingInfo.getClientCodeToCtrl().equals(""))
		{
			m_sbWhere.append(" AND C.SCODE <= '" + sett_CapitalPlanSettingInfo.getClientCodeToCtrl() +"'");
		}
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(getCapitalPlanByConditionOrderParam(sett_CapitalPlanSettingInfo.getDesc(),sett_CapitalPlanSettingInfo.getOrderParam()));
	}
	
	public  String getCapitalPlanByConditionOrderParam(long lDesc,long lOrderParam)
	{
		StringBuffer sbOrderBy = new StringBuffer();
        String strDesc = lDesc == 2 ? " DESC " : " ASC ";
		
		if(lOrderParam > 0) 
		{
			switch ((int) lOrderParam)
			{
				case 1:sbOrderBy.append(" \n ORDER BY CLIENTCODE " + strDesc);       break;
				case 2:sbOrderBy.append(" \n ORDER BY CLIENTNAME " + strDesc);       break;
				case 3:sbOrderBy.append(" \n ORDER BY CLIENTTYPENAME1 " + strDesc);    break;
				case 4:sbOrderBy.append(" \n ORDER BY CLIENTTYPENAME2 " + strDesc);    break;
				case 5:sbOrderBy.append(" \n ORDER BY CLIENTTYPENAME3 " + strDesc);    break;
				case 6:sbOrderBy.append(" \n ORDER BY ISCHECKPLAN " + strDesc);      break;
				
				default:sbOrderBy.append(" \n ORDER BY ID DESC" );           break;
		    }	
		}
		else
		{
			sbOrderBy.append(" \n ORDER BY CLIENTCODE ASC" );
		}
		return sbOrderBy.toString();
	}
	
	
	
	private long getMaxID()throws Exception
	{
		long ID = -1;
		try{
			
			initDAO();
			this.prepareStatement("SELECT NVL(MAX(ID),0)+1 AS MAXID FROM SETT_CAPITALPLANSETTING");
			this.executeQuery();
			if(this.transRS != null && this.transRS.next())
			{
				ID = this.transRS.getLong("MAXID");
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			
			this.finalizeDAO();
			
		}
		
		return ID;
	}
	
}



























