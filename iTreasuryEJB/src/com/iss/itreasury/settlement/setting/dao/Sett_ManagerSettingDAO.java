
package com.iss.itreasury.settlement.setting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.MaganerSettingInfo;

/**
 * Sett_ManagerSetting 数据库访问对象
 * @author gqfang
 * 
 */
public class Sett_ManagerSettingDAO extends SettlementDAO
{

	/**
	 * Constructor of class
	 */
	public Sett_ManagerSettingDAO()
	{

		super("Sett_ManagerSetting", false);
		setUseMaxID();
	}
	
	/**
	 * 查询符合条件的设置项
	 * 
	 * @return Collection 
	 * info.getStatusId() = -1 ：查询全部设置 
	 * info.getStatusId() =  0 ：查询无效设置 
	 * info.getStatusId() =  1 ：查询有效设置
	 */
	public Collection findAllSettings(MaganerSettingInfo info) throws SettlementException
	{

		Vector v = new Vector();

		try
		{
			// Initialize DAO
			initDAO();

			// return Collection
			StringBuffer sb = new StringBuffer();
			MaganerSettingInfo maganerSettingInfo = null;

			sb.append(" select * from Sett_ManagerSetting \n");
			if (info.getStatusId() != -1) 
			{
				sb.append(" where statusId = " + info.getStatusId());
			}
			sb.append("  order by moduleId ,itemName ");

			transPS = prepareStatement(sb.toString());

			System.out.println(" sql is :  \n" + sb.toString());
			
			transRS = transPS.executeQuery();
			
			while ( transRS.next()) 
			{
				maganerSettingInfo = new MaganerSettingInfo();

				maganerSettingInfo.setId(transRS.getLong("Id"));
				maganerSettingInfo.setItemUrl(transRS.getString("ItemUrl"));
				maganerSettingInfo.setItemName(transRS.getString("ItemName"));
				maganerSettingInfo.setModuleId(transRS.getLong("ModuleId"));
				maganerSettingInfo.setStatusId(transRS.getLong("StatusId"));
				
				System.out.println(" maganerSettingInfo.getItemUrl() = " +maganerSettingInfo.getItemUrl());
				System.out.println(" maganerSettingInfo.getItemName() = " +maganerSettingInfo.getItemName());
				
				v.add(maganerSettingInfo);
			}
			// 关闭资源
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally 
		{
			try 
			{
				finalizeDAO();
			}
			catch (Exception ex) 
			{
				throw new SettlementException();
			}
		}
		return (v.size() > 0 ? v : null);
	}


	/**
	 * 删除一条记录，物理删除
	 * @param id
	 * @throws ITreasuryDAOException
	 */
	public void deleteItem(long id) throws ITreasuryDAOException
	{

		try 
		{
			initDAO();

			StringBuffer buffer = new StringBuffer();

			buffer.append(" DELETE FROM  Sett_ManagerSetting  WHERE ID = " + id);

			String strSQL = buffer.toString();

			log.debug(strSQL);

			prepareStatement(strSQL);

			executeUpdate();
			
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new ITreasuryDAOException("删除操作异常", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
	}


	/**
	 * 判断是否存在相同名称的记录
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public boolean isExitRecord(MaganerSettingInfo info) throws SettlementException
	{

		boolean tureOrFalse = true;

		try 
		{
			initDAO();

			ResultSet rs = null;

			StringBuffer sb = new StringBuffer();

			sb.append(" select * from Sett_ManagerSetting ");
			sb.append(" where  ItemName = '" + info.getItemName() + "'");

			String strSQL = sb.toString();

			log.info(" SQL : \n" + sb.toString());

			prepareStatement(sb.toString());

			rs = executeQuery();

			if (rs != null && rs.next()) 
			{
				tureOrFalse = true;
			}
			else
			{
				tureOrFalse = false;
			}

			finalizeDAO();
		}
		catch (ITreasuryDAOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				finalizeDAO();
			}
			catch (Exception ex) 
			{
				throw new SettlementException();
			}
		}

		return tureOrFalse;
	}
	
	/**
	 * 置Sett_Managersetting所有记录的状态为 INVALID
	 * @throws ITreasuryDAOException
	 */
	public void unCheckedAll() throws ITreasuryDAOException
	{

		try 
		{
			initDAO();

			ResultSet rs = null;

			StringBuffer sb = new StringBuffer();

			sb.append(" update Sett_Managersetting set statusid =  " + Constant.RecordStatus.INVALID);

			String strSQL = sb.toString();

			log.info(" SQL : \n" + sb.toString());

			prepareStatement(strSQL);

			executeUpdate();

			finalizeDAO();
		}
		catch (ITreasuryDAOException e) 
		{
			throw new ITreasuryDAOException("更新 Sett_Managersetting 异常", e);
		}
		finally
		{
			try 
			{
				finalizeDAO();
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}

	}
}