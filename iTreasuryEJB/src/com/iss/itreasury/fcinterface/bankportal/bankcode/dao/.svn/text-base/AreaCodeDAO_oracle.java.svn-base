package com.iss.itreasury.fcinterface.bankportal.bankcode.dao;

/**
 * fszhu
 * 2008-11-27
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.AreaCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOUtil;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

public class AreaCodeDAO_oracle extends BaseDAO_oracle implements AreaCodeDAO 
{
	private static Logger log = new Logger(AreaCodeDAO_oracle.class);

	public AreaCodeDAO_oracle(Connection conn) throws SystemException {
		super(tableName, isNeedPrefix, conn);
		this.setIDType(ID_TYPE_MAXID);
	}

	/**
	 * 查找省名称 return Collection throws SystemException
	 * 
	 */
	public Collection findProvince() throws Exception
	{
		Collection res = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Database.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct s_areaprovince from ");
			sql.append(tableName);
			sql.append(" order by s_areaprovince ");
			log.info(sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				String province = rs.getString("s_areaprovince");
				res.add(province);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw (e);
		} catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SystemException(e);
		} 
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
		}
		return res;
	}

	/**
	 * 通过省名称查找市名称 param String provinceName return Collection throws
	 * SystemException
	 * @throws Exception 
	 */
	public Collection findCityByProvinceName(String provinceName) throws Exception 
	{
		Collection res = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			con = Database.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct s_areaname from ");
			sql.append(tableName);
			sql.append(" where  ");
			sql.append(" s_areaprovince like '%");
			sql.append(provinceName.trim());
			sql.append("%'");
			sql.append(" order by s_areaname ");
			log.info(sql.toString());
			ps=con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			while (rs.next()) 
			{
				String city = rs.getString("s_areaname");
				res.add(city);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			
		} catch (SystemException e) 
		{
			e.printStackTrace();
			throw (e);
		} catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SystemException(e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException();
			}
		}
		return res;
	}

	/**
	 * 查找与参数相同的数据库信息
	 */
	public Collection findEquelAreaCodeByCondition(AreaCodeInfo paramInfo)
			throws SystemException 
			{
		log.info("Enter AreaCodeDAO_oracle.findEquelAreaCodeByCondition(AreaCodeInfo paramInfo)....");
		StringBuffer sql = new StringBuffer();
		try 
		{
			initDAO();
			sql.append(" select * from  ");
			sql.append(tableName);
			sql.append(" where 1=1 ");
			if (paramInfo.getAreaCode() != null && paramInfo.getAreaCode().length() > 0) 
			{
				sql.append(" and s_areacode = '" + paramInfo.getAreaCode() + "' ");
			} 
			if (paramInfo.getAreaProvince() != null && paramInfo.getAreaProvince().length() > 0) 
			{
				sql.append(" and s_areaprovince = '" + paramInfo.getAreaProvince() + "' ");
			}
			if (paramInfo.getAreaName() != null && paramInfo.getAreaName().length() > 0) 
			{
				sql.append(" and s_areaname = '" + paramInfo.getAreaName() + "' ");
			}
			if (paramInfo.getAreaCity() != null && paramInfo.getAreaCity().length() > 0) 
			{
				sql.append(" and s_areacity = '" + paramInfo.getAreaCity() + "' ");
			} 
			if (paramInfo.getAreaLevel() != null && paramInfo.getAreaLevel().length() > 0) 
			{
				sql.append(" and s_arealevel= '" + paramInfo.getAreaLevel() + "' ");
			} 
			sql.append(" order by s_areacode ");
			log.info(sql.toString());
			prepareStatement(sql.toString());
			executeQuery();
			Collection res = DAOUtil.getDataEntitiesFromResultSet(transRS,
					AreaCodeInfo.class, isNeedPrefix());
			return res;
		} catch (Exception e) 
		{
			throw new SystemException(e);
		} finally 
		{
			finalizeDAO();
		}

	}

	/**
	 * 查找地区编码号
	 * @throws IException 
	 * 
	 */
	public String[] findAreaCodeByCondition(AreaCodeInfo paramInfo)
			throws SystemException, IException 
	{
		log.info("Enter AreaCodeDAO_oracle.findAreaCodeByCondition(AreaCodeInfo paramInfo)....");
		StringBuffer sql = new StringBuffer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList res = new ArrayList();
		try 
		{
			con = Database.getConnection();
			sql.append("select  distinct s_areacode from ");
			sql.append(tableName + " areacode");
			sql.append(" where 1=1 ");
			if (paramInfo.getAreaCode() != null
					&& paramInfo.getAreaCode().length() > 0) 
			{
				sql.append(" and s_areacode = '" + paramInfo.getAreaCode() + "' ");
			}
			if (paramInfo.getAreaProvince() != null
					&& paramInfo.getAreaProvince().length() > 0) 
			{
				sql.append(" and s_areaprovince like '%"+paramInfo.getAreaProvince()+"%'"); 
			}
			if (paramInfo.getAreaName() != null
					&& paramInfo.getAreaName().length() > 0) 
			{   
				sql.append(" and s_areaname like '%" + paramInfo.getAreaName() + "%' ");
			}
			if (paramInfo.getAreaCity() != null
					&& paramInfo.getAreaCity().length() > 0) 
			{
				sql.append(" and s_areacity like '%" + paramInfo.getAreaCity() + "%' ");
			}
			if (paramInfo.getAreaLevel() != null) 
			{
				sql.append(" and s_arealevel= '" + paramInfo.getAreaLevel() + "' ");
			}
			sql.append(" order by areacode.s_areacode ");
			log.info(sql.toString());
			ps=con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			
			while (rs.next()) 
			{
				String city = rs.getString("s_areacode");
				res.add(city);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (Exception e) 
		{
			throw new SystemException(e);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException();
			}
		}
		return (String[]) res.toArray(new String[0]);
	}

	/**
	 * 查找地区编码信息
	 * 
	 */
	public AreaCodeInfo[] findByCondition(AreaCodeInfo paramInfo)
			throws SystemException 
	{
		log.info("Enter AreaCodeDAO_oracle.findByCondition(AreaCodeInfo paramInfo)");
		StringBuffer sql = new StringBuffer();
		try 
		{
			initDAO();
			sql.append("select areacode.*,info1.s_name as s_inputusername,info2.s_name as s_modifyusername from ");
			sql.append(tableName + " areacode,");
			sql.append(" bs_user info1,bs_user info2 ");
			sql.append(" where 1=1 ");
			sql.append(" and areacode.n_inputuserid = info1.n_id(+)  ");
			sql.append(" and areacode.n_modifyuserid = info2.n_id(+) ");
			if (paramInfo.getAreaCode() != null
					&& paramInfo.getAreaCode().length() > 0) 
			{
				sql.append(" and s_areacode = '" + paramInfo.getAreaCode() + "' ");
			}
			if (paramInfo.getAreaProvince() != null
					&& paramInfo.getAreaProvince().length() > 0) 
			{
				sql.append(" and s_areaprovince like '%" + paramInfo.getAreaProvince() + "%'");
			}
			if (paramInfo.getAreaName() != null
					&& paramInfo.getAreaName().length() > 0) 
			{
				sql.append(" and s_areaname like '%" + paramInfo.getAreaName() + "%' ");
			}
			if (paramInfo.getAreaCity() != null
					&& paramInfo.getAreaCity().length() > 0) 
			{
				sql.append(" and s_areacity like '%" + paramInfo.getAreaCity() + "%' ");
			}
			if (paramInfo.getAreaLevel() != null)
			{
				sql.append(" and s_arealevel= '" + paramInfo.getAreaLevel() + "' ");
			}
			sql.append(" order by areacode.s_areacode ");
			log.info(sql.toString());
			prepareStatement(sql.toString());
			executeQuery();
			Collection res = DAOUtil.getDataEntitiesFromResultSet(transRS,
					AreaCodeInfo.class, isNeedPrefix());
			return (AreaCodeInfo[]) res.toArray(new AreaCodeInfo[0]);
		} catch (Exception e) 
		{
			throw new SystemException(e);
		} finally 
		{
			finalizeDAO();
		}

	}
}
