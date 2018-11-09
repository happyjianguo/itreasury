/*
 * Created on 2005-1-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.StockProjectNameSettingInfo;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_StockProjectNameSettingDAO extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_StockProjectNameSettingDAO()
	{
		super();
		this.strTableName = "sett_stockProjectNameSetting";
		setUseMaxID();
	}
	/**
	 * Constructor for Sett_BranchDAO.
	 * @param conn
	 */
	public Sett_StockProjectNameSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_stockProjectNameSetting";
		setUseMaxID();
	}
	
	/**
	 * 判断是否被修改过
	 * @param info 
	 * @return 已经修改过 true；未被修改过 false
	 * @exception
	 */
	public boolean isTouch(StockProjectNameSettingInfo info) throws Exception
    {
        boolean bReturn = false;
        try
        {
            StockProjectNameSettingInfo tmpInfo = (StockProjectNameSettingInfo)findByID(info.getId(),(new StockProjectNameSettingInfo()).getClass());
            if (tmpInfo == null || tmpInfo.getModifyDate().compareTo(info.getModifyDate())!=0)
            {
            	if (tmpInfo != null && info != null){
            		log.print("数据库修改时间:"+tmpInfo.getModifyDate().toString());
            		log.print("页面传入时间"+info.getModifyDate().toString());
            	}else{
            		log.print("null");
            	}
            	bReturn = true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return bReturn;
    }
	public static void main(String[] args) throws ITreasuryDAOException
	{
		Sett_StockProjectNameSettingDAO dao = new Sett_StockProjectNameSettingDAO();
		StockProjectNameSettingInfo tmpInfo = (StockProjectNameSettingInfo)dao.findByID(1,(new StockProjectNameSettingInfo()).getClass());
		if (tmpInfo!=null)
		{
			System.out.println(tmpInfo.getProjectName());
			System.out.println(tmpInfo.getModifyDate());
		}
	}
}
