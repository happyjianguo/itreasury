/*
 * Created on 2005-1-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.StockProjectSettingInfo;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_StockProjectSettingDAO extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_StockProjectSettingDAO()
	{
		super();
		this.strTableName = "sett_stockProjectSetting";
		setUseMaxID();
	}
	/**
	 * Constructor for Sett_BranchDAO.
	 * @param conn
	 */
	public Sett_StockProjectSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_stockProjectSetting";
		setUseMaxID();
	}
	/**
	 * 查询已经设置的项目
	 * @return
	 * @throws Exception
	 */
	public Collection findByProjectCondition(StockProjectSettingInfo info) throws Exception 
	{
		ArrayList list = new ArrayList();
		
		this.initDAO();
		StringBuffer SQLstr = new StringBuffer();
		SQLstr.append("select a.*,b.projectName from sett_stockProjectSetting a,sett_stockProjectNameSetting b ");
		SQLstr.append("where a.statusid=1 and a.projectID = b.id and a.projectType=? and a.officeid=? and a.currencyid=?");
		if (info.getProjectID() > 0)
			SQLstr.append(" and a.projectID = " + info.getProjectID());
		SQLstr.append(" order by b.projectName,a.id");
		log.print(SQLstr.toString());
		try
		{
			transPS = transConn.prepareStatement(SQLstr.toString());
			transPS.setLong(1,info.getProjectType());
			transPS.setLong(2,info.getOfficeID());
			transPS.setLong(3,info.getCurrencyID());
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				StockProjectSettingInfo tempInfo = new StockProjectSettingInfo();
				tempInfo.setId(transRS.getLong("ID"));
				tempInfo.setProjectID(transRS.getLong("projectID"));
				tempInfo.setProjectName(transRS.getString("projectName"));
				tempInfo.setProjectType(transRS.getLong("projectType"));
				tempInfo.setInputUserID(transRS.getLong("InputUserID"));
				tempInfo.setInputDate(transRS.getTimestamp("inputDate"));
				tempInfo.setAccountType(transRS.getLong("AccountType"));
				tempInfo.setContractType(transRS.getLong("ContractType"));
				tempInfo.setRelateClientType(transRS.getLong("RelateClientType"));
				tempInfo.setProfitAndLossType(transRS.getString("ProfitAndLossType"));
				list.add(tempInfo);
			}
		}catch(SQLException e)
		{
			throw e;
		}
		finally
		{
			this.finalizeDAO();
		}
		return list;
	}
	

	/**
	 * 查询某项目是否已经设置过
	 * @param projectID
	 * @return
	 * @throws Exception
	 */
	public Collection findByProjectID(long projectID) throws Exception
	{
		Collection c = null;
		StockProjectSettingInfo info = new StockProjectSettingInfo();
		info.setProjectID(projectID);
		info.setStatusID(com.iss.itreasury.util.Constant.RecordStatus.VALID);
		try
		{
			c = this.findByCondition(info);
		}catch(Exception e)
		{
			throw e;
		}
		return c;
	}
	
	/**
	 * 判断是否被修改过
	 * @param info 
	 * @return 已经修改过 true；未被修改过 false
	 * @exception
	 */
	public boolean isTouch(StockProjectSettingInfo info) throws Exception
    {
        boolean bReturn = false;
        try
        {
        	StockProjectSettingInfo tmpInfo = (StockProjectSettingInfo)findByID(info.getId(),(new StockProjectSettingInfo()).getClass());
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
	
	public static void main(String[] args) throws Exception
	{
		Sett_StockProjectSettingDAO dao = new Sett_StockProjectSettingDAO();
	}
}
