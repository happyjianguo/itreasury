/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.configtool.constantmanage.dataentity.ConstantManageInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConstantManageDao extends ITreasuryDAO {
	public ConstantManageDao() {
		super("constantManageInfo");
	}

	public ConstantManageDao(String tableName) {
		super(tableName);
	}

	public ConstantManageDao(String tableName, Connection conn) {
		super(tableName, conn);
	}

	public ConstantManageDao(boolean isNeedPrefix) {
		super(isNeedPrefix);
	}

	public ConstantManageDao(String tableName, boolean isNeedPrefix) {
		super(tableName, isNeedPrefix);
	}

	public ConstantManageDao(String tableName, boolean isNeedPrefix, Connection conn) {
		super(tableName, isNeedPrefix, conn);
	}

	public Collection getConstantManageInfoByCondition(ConstantManageInfo condition) throws Exception {
		String sql = "";

		ConstantManageInfo info = null;
		Vector vResult = new Vector();

		try {
			initDAO();

			sql = " SELECT * FROM constantManageInfo \n";
			sql += " WHERE 1=1";

			if (condition.getOfficeID() > 0) {
				sql += " AND officeID=" + condition.getOfficeID();
			}

			if (condition.getCurrencyID() > 0) {
				sql += " AND currencyID=" + condition.getCurrencyID();
			}

			if (condition.getConstantID() > 0) {
				sql += " AND constantID=" + condition.getConstantID();
			}

			transPS = transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();

			while (transRS.next()) {
				info = new ConstantManageInfo();

				info.setConstantID(transRS.getLong("constantID"));
				info.setOfficeID(transRS.getLong("officeID"));
				info.setCurrencyID(transRS.getLong("currencyID"));
				info.setValue(transRS.getLong("value"));

				vResult.addElement(info);
			}
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			finalizeDAO();
		}

		return vResult;
	}
	
	public Collection findByConstantID(long constantID,long officeID,long currencyID) throws Exception {
		String sql = "";

		ConstantManageInfo info = null;
		Vector vResult = new Vector();
		int n=1;

		try {
			initDAO();

			sql = " SELECT * FROM constantManageInfo \n";
			sql += " WHERE constantID=?";
			sql += " and officeID=?";
			sql += " and currencyid=?";
//			if(currencyID>0&&currencyID!=99)jzw 2010-04-16 修改总账增加和查询的时候默认是人民币，并且选择外币的时候下拉框没有的错误。
//				sql += " and currencyID=?";

			transPS = transConn.prepareStatement(sql);
			transPS.setLong(n++,constantID);
			transPS.setLong(n++,officeID);
			transPS.setLong(n++,currencyID);
//			if(currencyID>0&&currencyID!=99)
//				transPS.setLong(n++,currencyID);
			transRS = transPS.executeQuery();

			while (transRS.next()) {
				info = new ConstantManageInfo();

				info.setConstantID(transRS.getLong("constantID"));
				info.setOfficeID(transRS.getLong("officeID"));
				info.setCurrencyID(transRS.getLong("currencyID"));
				info.setValue(transRS.getLong("value"));

				vResult.addElement(info);
			}
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			finalizeDAO();
		}

		return vResult;
	}
	
	public void add(ConstantManageInfo info) throws Exception {
		String sql = "";
		int index = 0;

		try {
			initDAO();

			sql = " INSERT INTO constantManageInfo  \n";
			sql += "(constantID,officeID,currencyID,value)";
			sql += " VALUES (?,?,?,?)";
			
			transPS = transConn.prepareStatement(sql);
			transPS.setLong(++index,info.getConstantID());
			transPS.setLong(++index,info.getOfficeID());
			transPS.setLong(++index,info.getCurrencyID());
			transPS.setLong(++index,info.getValue());
			
			transPS.executeUpdate();
			
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			finalizeDAO();
		}
	}
	
	public void delByCondition(ConstantManageInfo condition) throws Exception {
		String sql = "";
		int index = 0;

		try {
			initDAO();

			sql = " DELETE constantManageInfo  \n";
			sql += " WHERE constantID = ?";
			sql += " AND officeID = ?";
			sql += " AND currencyID = ?";
			
			transPS = transConn.prepareStatement(sql);
			transPS.setLong(++index,condition.getConstantID());
			transPS.setLong(++index,condition.getOfficeID());
			transPS.setLong(++index,condition.getCurrencyID());
			
			transPS.executeUpdate();
			
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			finalizeDAO();
		}
	}
	
	public void saveAllConstantManageInfo(ConstantManageInfo[] info) throws IException
	{
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		long officeID = -1;
		long currencyID = -1;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			ConstantManageDao dao = new ConstantManageDao("constantManageInfo",conn);
			if (info[0].getAllOffice() > 0)
			{
				Log.print("--------------------选择了所有办事处----------------------");
				sql = "select id from office where nstatusID = 1";
				st2 = conn.createStatement();
				rs2 = st2.executeQuery(sql);
				while (rs2.next())
				{
					officeID = rs2.getLong(1);
					info[0].setOfficeID(officeID);
					if (info[0].getAllCurrency() > 0)
					{
						Log.print("--------------------选择了所有币种----------------------");
						sql = "select id from CURRENCYINFO where status=1";
						st = conn.createStatement();
						rs = st.executeQuery(sql);
						while (rs.next())
						{
							currencyID = rs.getLong(1);
							info[0].setCurrencyID(currencyID);
							//先删除掉此币种此办事处此常量的已经存在的值
							delByCondition(info[0]);
							for (int i=0;i<info.length;i++)
							{
								info[i].setOfficeID(officeID);
								info[i].setCurrencyID(currencyID);
								dao.add(info[i]);
							}
						}
						rs.close();
						st.close();
					}
					else if (info[0].getAllForeignCurrency() > 0) 
					{
						Log.print("--------------------选择了所有外币----------------------");
						sql = "select id from CURRENCYINFO where status=1 and name!='人民币元'";
						st = conn.createStatement();
						rs = st.executeQuery(sql);
						while (rs.next())
						{
							currencyID = rs.getLong(1);
							info[0].setCurrencyID(currencyID);
							//先删除掉此币种此办事处此常量的已经存在的值
							delByCondition(info[0]);
							for (int i=0;i<info.length;i++)
							{
								info[i].setOfficeID(officeID);
								info[i].setCurrencyID(currencyID);
								dao.add(info[i]);
							}
						}
						rs.close();
						st.close();
					}
					else
					{
						delByCondition(info[0]);
						for (int i=0;i<info.length;i++)
						{
							info[i].setOfficeID(officeID);
							dao.add(info[i]);
						}
					}
				}
				rs2.close();
				st2.close();
			}
			else
			{
				Log.print("--------------------没有选择所有办事处----------------------");
				if (info[0].getAllCurrency() > 0)
				{
					Log.print("--------------------选择了所有币种----------------------");
					sql = "select id from CURRENCYINFO where status=1";
					st = conn.createStatement();
					rs = st.executeQuery(sql);
					while (rs.next())
					{
						currencyID = rs.getLong(1);
						info[0].setCurrencyID(currencyID);
						//先删除掉此币种此办事处此常量的已经存在的值
						delByCondition(info[0]);
						for (int i=0;i<info.length;i++)
						{
							info[i].setCurrencyID(currencyID);
							dao.add(info[i]);
						}
					}
					rs.close();
					st.close();
				}
				else
				{
					Log.print("--------------------选择了所有外币----------------------");
					sql = "select id from CURRENCYINFO where status=1 and name!='人民币元'";
					st = conn.createStatement();
					rs = st.executeQuery(sql);
					while (rs.next())
					{
						currencyID = rs.getLong(1);
						info[0].setCurrencyID(currencyID);
						//先删除掉此币种此办事处此常量的已经存在的值
						delByCondition(info[0]);
						for (int i=0;i<info.length;i++)
						{
							info[i].setCurrencyID(currencyID);
							dao.add(info[i]);
						}
					}
					rs.close();
					st.close();
				}
			}
			conn.commit();
		} catch (Exception e)
		{
			Log.print(e.toString());
			try
			{
				if (conn != null)
					conn.rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new IException("Gen_E001");
		}
		finally {
			finalizeDAO();
		}
		
	}
}