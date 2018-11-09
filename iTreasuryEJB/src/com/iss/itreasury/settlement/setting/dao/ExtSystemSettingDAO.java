
package com.iss.itreasury.settlement.setting.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;



public class ExtSystemSettingDAO extends ITreasuryDAO  
{
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public ExtSystemSettingDAO()
	{
		super("ep_ExtSystem");
		super.setUseMaxID();
	}

	public ExtSystemSettingDAO(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
	}

	public ExtSystemSettingDAO(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
	}
	
	public ArrayList findExtSystemInformation() throws Exception
	{
		ArrayList list = null;
		StringBuffer sql = null;
		ExtSystemSettingInfo info = null;
		try
		{
			list = new ArrayList();
			sql = new StringBuffer();
			conn=Database.getConnection();
			sql.append(" select * from ep_ExtSystem "); 
			sql.append(" where lstatus=1 ");
			sql.append(" order by id ");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				info = new ExtSystemSettingInfo();
				info.setId(rs.getLong("id"));
				info.setSCode(rs.getString("scode"));
				info.setSName(rs.getString("sname"));
				list.add(info);
			}
			
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			
				
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		return list.size()>0?list:null;
	}
	
	public ArrayList findExtSystemOrginalInformation() throws Exception
	{
		ArrayList list = null;
		StringBuffer sql = null;
		ExtSystemSettingInfo info = null;
		try
		{
			list = new ArrayList();
			sql = new StringBuffer();
			conn=Database.getConnection();
			sql.append(" select * from ep_ExtSystem "); 
			sql.append(" where lstatus=-1 ");
			System.out.println("sql="+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				info = new ExtSystemSettingInfo();
				info.setId(rs.getLong("id"));
				info.setSCode(rs.getString("scode"));
				info.setSName(rs.getString("sname"));
				info.setSNote(rs.getString("snote"));
				list.add(info);
			}
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		
		return list.size()>0?list:null;
	}
	
	public ArrayList findExtSystemInformationExcept(long id) throws Exception
	{
		ArrayList list = null;
		StringBuffer sql = null;
		ExtSystemSettingInfo info = null;
		try
		{
			list = new ArrayList();
			sql = new StringBuffer();
			conn=Database.getConnection();
			sql.append(" select * from ep_ExtSystem "); 
			sql.append(" where lstatus=1 ");
			sql.append(" and id!="+id);
			sql.append(" order by id ");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				info = new ExtSystemSettingInfo();
				info.setId(rs.getLong("id"));
				info.setSCode(rs.getString("scode"));
				info.setSName(rs.getString("sname"));
				list.add(info);
			}
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			
			
				
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		return list.size()>0?list:null;
	}
	
	public Collection getSApplyCodeSource()throws Exception
	{
		Vector v = null;
		StringBuffer sql = null;
		ExtSystemSettingInfo info = null;
		try{
			v = new Vector();
			sql = new StringBuffer();
			info = new ExtSystemSettingInfo();
			conn=Database.getConnection();
			sql.append(" select * from ep_extsystem ");
			sql.append(" where id="+SETTConstant.ExtSystemSource.EBANK);
			sql.append(" union all ");
			sql.append(" select * from ep_extsystem ");
			sql.append(" where id > 3 ");
			sql.append(" and lstatus = "+Constant.RecordStatus.VALID);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			System.out.println("sql="+sql.toString());
			while (rs.next()&&rs!=null)
			{
				info = new ExtSystemSettingInfo();
				info.setId(rs.getLong("id"));
				info.setSCode(rs.getString("scode"));
				info.setSName(rs.getString("sname"));
				info.setSNote(rs.getString("snote"));
				v.addElement(info);
			}
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		return v.size()>0?v:null;
	}
	
	/**
	*@author <a href="mailto:xfma3@isoftstone.com">马现福</a>
	*@version 创建时间：Mar 24, 2011 1:33:09 PM
	*@功能：取数据库中的数据来源ID
	*@param type 0-全部
	*@return
	*@throws Exception
	 */
	public long[] getSourceCode(long type)throws Exception
	{
		long[] larray = null;
		Vector v = null;
		StringBuffer sql = null;
		try{
			v = new Vector();
			this.initDAO();
			sql = new StringBuffer();
			if(type==0){
				sql.append(" select id from ep_extsystem ");
				sql.append(" where lstatus != ").append(Constant.RecordStatus.INVALID);
			}else{
				sql.append(" select id from ep_extsystem ");
				sql.append(" where id=").append(SETTConstant.ExtSystemSource.EBANK);
				sql.append(" union all ");
				sql.append(" select id from ep_extsystem ");
				sql.append(" where id > ").append(SETTConstant.ExtSystemSource.BANK);
				sql.append(" and lstatus = ").append(Constant.RecordStatus.VALID);
			}
			System.out.println("sql="+sql.toString());
			transPS = transConn.prepareStatement(sql.toString());
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				v.addElement(String.valueOf(transRS.getLong("id")));
			}
			if(v.size()>0){
				larray = new long[v.size()];
				for(int i=0;i<v.size();i++){
					larray[i] = Long.valueOf(v.get(i).toString()).longValue();
				}
			}
			this.finalizeDAO();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.finalizeDAO();
		}
		return larray;
	}

	public boolean isAllowDelete(ExtSystemSettingInfo info) throws Exception
	{
		boolean isAllowDelete = true;
		StringBuffer sql = null;
		
		try
		{
			sql = new StringBuffer();
			conn=Database.getConnection();
			sql.append(" select * from ob_FinanceInStr ");
			sql.append(" where lsource="+info.getId());
			sql.append(" and NSTATUS="+Constant.RecordStatus.VALID);
			
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs!=null&&rs.next())
			{
				isAllowDelete = false;
			}
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		return isAllowDelete;
	}
	
	/**
	*@author <a href="mailto:xfma3@isoftstone.com">马现福</a>
	*@version 创建时间：Mar 24, 2011 1:33:09 PM
	*@功能：取数据库中的数据来源ID
	*@param type 0-全部
	*@return
	*@throws Exception
	 */
	public Map getSourceName()throws Exception
	{
		Map sourceMap = new HashMap();
		StringBuffer sql = null;
		try{
			this.initDAO();
			sql = new StringBuffer();
			sql.append(" select id, sName from ep_extsystem ");
			sql.append(" where lstatus != ").append(Constant.RecordStatus.INVALID);
			System.out.println("sql="+sql.toString());
			transPS = transConn.prepareStatement(sql.toString());
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				sourceMap.put(String.valueOf(transRS.getLong("id")), transRS.getString("sName"));
			}
			this.finalizeDAO();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.finalizeDAO();
		}
		return sourceMap;
	}
	
	
	
	
	
}
