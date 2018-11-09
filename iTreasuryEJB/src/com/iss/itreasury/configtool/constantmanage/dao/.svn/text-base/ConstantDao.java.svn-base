/*
 * Created on 2005-3-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.configtool.constantmanage.dataentity.*;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.configtool.util.CONFIGConstant;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstantDao extends ITreasuryDAO{
	
	public ConstantDao()
    {
        super("constantInfo");
    }

    public ConstantDao(String tableName)
    {
        super(tableName);
    }

    public ConstantDao(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public ConstantDao(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public ConstantDao(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public ConstantDao(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    
    public Collection getConstantInfoByCondition(ConstantInfo condition) throws Exception
	{
    	String sql = "";
    	ConstantInfo info = null;
		Vector vResult = new Vector();
		
		try
		{
			initDAO();
			
			sql = " SELECT * FROM constantInfo \n";
			sql += " WHERE 1=1";
			
			if (condition.getModuleID()>0)
			{
				sql += " AND moduleID="+ condition.getModuleID();
			}
			
			if (condition.getStatusID()>0)
			{
				sql += " AND statusID="+ condition.getStatusID();
			}
			
			if (condition.getConstantDesc()!=null && condition.getConstantDesc().length()>0)
			{
				sql += " AND constantDesc like '%"+ condition.getConstantDesc()+"%'";
			}
			
			Log.print("sql=\n"+sql);
			transPS = transConn.prepareStatement(sql);
            transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new ConstantInfo();
				
				info.setId(transRS.getLong("id"));
				info.setName(transRS.getString("name"));
				info.setModuleID(transRS.getLong("moduleID"));
				info.setModuleName(CONFIGConstant.ModuleType.getName(info.getModuleID()));
				info.setStatusID(transRS.getLong("statusID"));
				info.setStatus(CONFIGConstant.ManageStatus.getName(info.getStatusID()));
				info.setConstantDesc(transRS.getString("constantDesc")==null?"":transRS.getString("constantDesc"));
				
				vResult.addElement(info);
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally {
			finalizeDAO();
		}
		
		return vResult.size()>0?vResult:null;
	}
    
    public ConstantInfo findByName(String name) throws Exception
	{
    	String sql = "";
    	ConstantInfo info = null;
		
		try
		{
			initDAO();
			
			sql = " SELECT * FROM constantInfo \n";
			sql += " WHERE name=?";
			
			Log.print("sql=\n"+sql);
			transPS = transConn.prepareStatement(sql);
			transPS.setString(1,name);
            transRS = transPS.executeQuery();
			
			if(transRS.next())
			{
				info = new ConstantInfo();
				
				info.setId(transRS.getLong("id"));
				info.setName(transRS.getString("name"));
				info.setModuleID(transRS.getLong("moduleID"));
				info.setModuleName(CONFIGConstant.ModuleType.getName(info.getModuleID()));
				info.setStatusID(transRS.getLong("statusID"));
				info.setStatus(CONFIGConstant.ManageStatus.getName(info.getStatusID()));
				info.setConstantDesc(transRS.getString("constantDesc")==null?"":transRS.getString("constantDesc"));
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally {
			finalizeDAO();
		}
		
		return info;
	}

    //Boxu update 2007-11-26 查询返回集合
    public Collection findByConstantNameID(String name, long officeID, long currencyID) throws Exception
	{
    	String sql = "";
    	ConstantManageInfo info = null;
    	Collection coll = new ArrayList();
		
		try
		{
			initDAO();
			
			sql =  " SELECT b.value FROM constantInfo a, constantManageInfo b ";
			sql += " WHERE a.id = b.constantid ";
			sql += " a.name=? and b.officeID=? and b.currencyID=? and a.statusid=? ";
			sql += " order by b.value ";
			
			Log.print("sql=\n"+sql);
			transPS = transConn.prepareStatement(sql);
			
			transPS.setString(1, name);
			transPS.setLong(2, officeID);
			transPS.setLong(3, currencyID);
			transPS.setLong(4, CONFIGConstant.ManageStatus.MANAGE);
			
            transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new ConstantManageInfo();
				info.setValue(transRS.getLong("value"));

				coll.add(info);
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally {
			finalizeDAO();
		}
		
		return coll.size()>0?coll:null;
	}
   
}
