package com.iss.itreasury.system.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.system.privilege.dataentity.Sys_PrivilegeInfo;
import com.iss.itreasury.system.setting.dataentity.SysCalendarInfo;
import com.iss.itreasury.util.Database;

public class SysCalendarDAO {
	
	public static final String strTableName = "SYS_CALENDAR";
	
	/**当前DAO的使用的数据库连接*/
	protected Connection transConn = null;
	
	/**当前DAO的使用的结果集*/
	protected ResultSet transRS = null;
	/**当前DAO的使用的PreparedStatement*/
	protected PreparedStatement transPS = null;
	/**是否数据库连接是自维护的（即不是容器维护的）*/
	private boolean isSelfManagedConn = false;
	
	/**
	 * DAO初使化类，在使用具体的DAO操作前，必须先调用此方法!!!!　
	 * @param 
	 * @param 　
	 * @return
	 * @throws SecuritiesDAOException
	 */			
	protected void initDAO() throws ITreasuryDAOException{
		try {
			if(transConn == null)
				transConn = Database.getConnection();
		} catch (Exception e) {
			throw new ITreasuryDAOException("数据库初使化异常发生",e);
		}
	}

	/**功能：查找所有有效的设置记录*/
	public SysCalendarInfo findById(String strIdDate){
		
		return null;
	}
	
	public Collection findCurrentMonth(String strIdDate)throws SQLException, ITreasuryDAOException{
		Collection coll = new ArrayList();
		SysCalendarInfo sysCalendarInfo = null;
		String arg = strIdDate.substring(0, 7);
		try
        {
			
			initDAO();
        StringBuffer sb = new StringBuffer();
        sb.append("select a.* from SYS_CALENDAR a where a.statusid=1 and a.dayid like '"+arg+"%'");
        
        
        transPS = transConn.prepareStatement(sb.toString());
        transRS = transPS.executeQuery();
        while (transRS.next())
        {
        	sysCalendarInfo = new SysCalendarInfo();
        	sysCalendarInfo.setDayId(transRS.getString("DAYID"));
        	sysCalendarInfo.setBhTransType(transRS.getLong("BHTRANSTYPE"));
        	sysCalendarInfo.setBhTransDesc(transRS.getString("BHTRANSDESC"));
        	sysCalendarInfo.setStatusId(transRS.getLong("STATUSID"));
        	sysCalendarInfo.setInputUserId(transRS.getLong("INPUTUSERID"));
        	sysCalendarInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
        	coll.add(sysCalendarInfo);
        }
        finalizeDAO();
        System.out.println(coll.size()+"  :  "+arg+"  : "+sb.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return coll;
        }


	  /**功能：查找所有有效的设置记录*/
	public Collection getAll()throws SQLException, ITreasuryDAOException{
		Collection coll = new ArrayList();
		SysCalendarInfo sysCalendarInfo = null;
		try
        {
			initDAO();
			StringBuffer sb = new StringBuffer();
	        sb.append("select a.* from SYS_CALENDAR a where a.statusid=1");
	        transPS = transConn.prepareStatement(sb.toString());
	        transRS = transPS.executeQuery();
	        while (transRS.next())
	        {
	        	sysCalendarInfo = new SysCalendarInfo();
	        	sysCalendarInfo.setDayId(transRS.getString("DAYID"));
	        	sysCalendarInfo.setBhTransType(transRS.getLong("BHTRANSTYPE"));
	        	sysCalendarInfo.setBhTransDesc(transRS.getString("BHTRANSDESC"));
	        	sysCalendarInfo.setStatusId(transRS.getLong("STATUSID"));
	        	sysCalendarInfo.setInputUserId(transRS.getLong("INPUTUSERID"));
	        	sysCalendarInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
	        	coll.add(sysCalendarInfo);
	        }
	        System.out.println("System.out.println(coll.size()):"+coll.size());
			finalizeDAO();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            finalizeDAO();
	        }
	        finally
	        {
	            finalizeDAO();
	        }
	        return coll;
	        }
	/**功能：新增一条假日表设置记录*/
	public String add(){
		return null;
	}
	/**功能：根据ID更新假日表设置记录*/
	public String updateOrAdd(SysCalendarInfo info)throws SQLException, ITreasuryDAOException{
		Collection coll = new ArrayList();
		SysCalendarInfo sysCalendarInfo = null;
		try
        {
			initDAO();
			StringBuffer sb = new StringBuffer();
			String arg = info.getDayId();
	        sb.append("select a.* from SYS_CALENDAR a where a.dayid = '"+arg+"'");
	        transPS = transConn.prepareStatement(sb.toString());
	        transRS = transPS.executeQuery();
	        if (transRS.next())
	        {
	        	String sqlstr = "update sys_calendar a set a.bhtranstype=?,a.statusid=?,a.inputdate=? where a.dayid=? ";
				System.out.println(sqlstr);
				transPS = transConn.prepareStatement(sqlstr.toString());
				int index = 1;
				transPS.setLong(index++, info.getBhTransType());
				transPS.setLong(index++, info.getStatusId());
				transPS.setTimestamp(index++, info.getInputDate());
				transPS.setString(index++, info.getDayId());
				int i = transPS.executeUpdate();
				if (i > 0) {
					System.out.println("----------------------u sss--------------------------");
				}
	        }else{
	        	String sqlstr = "insert into sys_calendar a (a.dayid,a.bhtranstype,a.statusid,a.inputuserid,a.inputdate) values ('" + info.getDayId() + "'," + info.getBhTransType()+ "," + info.getStatusId() + ",'" + info.getInputUserId() + "',?)";
	        	transPS = transConn.prepareStatement(sqlstr.toString());
				int index = 1;
				transPS.setTimestamp(index++, info.getInputDate());
	            System.out.println(sqlstr);
	            int i = transPS.executeUpdate();
	            if (i > 0) {
	        	System.out.println("----------------------a sss--------------------------");
	            }
	        }
	    
			finalizeDAO();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            finalizeDAO();
	        }
	        finally
	        {
	            finalizeDAO();
	        }
	        return "";
	}
	/**功能：删除指定ID假日表设置记录*/
	public String delete(String strDtID){
		return null;
	}
	
	/**
	 * DAO结束操作，在DAO操作的结尾必须调用此方法!!!!!!!!　
	 * @param 
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 */				
	protected void finalizeDAO() throws ITreasuryDAOException{
		try {
			if (transRS != null) {
				transRS.close();
				transRS = null;
			}

			if (transPS != null) {
				transPS.close();
				transPS = null;
			}

			if (transConn != null && !isSelfManagedConn) {
				transConn.close();
				transConn = null;
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库关闭异常发生",e);			
		}
	}
	
	
	public static void main(String[] args)
    {
		SysCalendarDAO s = new SysCalendarDAO();
		try {
			s.findCurrentMonth("2010-08-16");
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
