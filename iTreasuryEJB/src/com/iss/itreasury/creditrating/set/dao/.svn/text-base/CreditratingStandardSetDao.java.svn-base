package com.iss.itreasury.creditrating.set.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iss.itreasury.bill.attach.dataentity.AttachInfo;
import com.iss.itreasury.creditrating.set.dataentity.CreditratingStandardInfo;
import com.iss.itreasury.creditrating.set.dataentity.CreditratingSubStandardInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.creditrating.util.*;

public class CreditratingStandardSetDao extends ITreasuryDAO 
{
	
	private static Log4j log4j  = null;
	
	public CreditratingStandardSetDao(String TableName)
	{
		super(TableName);
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
		
		
		
	}
	public CreditratingStandardSetDao()
	{
		
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
		
	}

	public PageLoader searchstandardinfo(CreditratingStandardInfo info) throws Exception
	{

		try
		{
            StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id,name,description,remark ");
			sbSQL.append(" FROM CRERT_STANDARDRATING a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL.append("  and a.OFFICEID="+info.getOfficeid());
			if(info.getName()!=null )
			{
			sbSQL.append(" AND a.name like '%"+info.getName()+"%'" );
			}
			
			  
			log4j.info(sbSQL.toString());
		

			try{
				PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
				pageLoader.initPageLoader(new AppContext(), "("+sbSQL.toString()+")","*", "1=1", (int) Constant.PageControl.LISTALL,
						"com.iss.itreasury.creditrating.set.dataentity.CreditratingStandardInfo", null);
	
				return pageLoader;
			}catch(Exception ex)
			{
				throw new ITreasuryDAOException(ex.getMessage(), ex);
			}			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
	}
	public PageLoader searchsubstandardinfo(CreditratingSubStandardInfo info) throws Exception
	{

		try
		{
            StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id,standardratingid,name,description,mixvalue,maxvalue,remark ");
			sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			
			if(info.getStandardratingid()!=-1 )
			{
			sbSQL.append(" AND a.standardratingid = "+info.getStandardratingid());
			}
			
			  
			log4j.info(sbSQL.toString());
		

			try{
				PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
				pageLoader.initPageLoader(new AppContext(), "("+sbSQL.toString()+")","*", "1=1", (int) Constant.PageControl.LISTALL,
						"com.iss.itreasury.creditrating.set.dataentity.CreditratingSubStandardInfo", null);
	
				return pageLoader;
			}catch(Exception ex)
			{
				throw new ITreasuryDAOException(ex.getMessage(), ex);
			}			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	public long saverecord(CreditratingStandardInfo info) throws Exception
	{
		long countrecord=-1;
		
		
		try {
			countrecord=add(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
		
		return countrecord;
		
	}
	public long savedetailrecord(CreditratingSubStandardInfo info) throws Exception
	{
		long countrecord=-1;
		
		
		try {
			countrecord=add(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
		
		return countrecord;
		
	}
	
	public CreditratingStandardInfo searchstandardinfobyid(CreditratingSubStandardInfo info) throws Exception
	{


			CreditratingStandardInfo queryinfo=new CreditratingStandardInfo();
			//ArrayList list=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;

			try
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" select id,name,description,remark ");
				sbSQL.append(" FROM CRERT_STANDARDRATING a ");
				sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
				sbSQL.append(" and a.id= "+info.getStandardratingid() );
				
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					//CreditratingStandardInfo queryinfo=new CreditratingStandardInfo();
					queryinfo.setId(rs.getLong("id")); //文件ID(表BILL_DOCINFO的ID)
					queryinfo.setName(rs.getString("name")); //文件ID（表fileinfo的ID）
					queryinfo.setDescription(rs.getString("description")); //在客户端的名称
					queryinfo.setRemark(rs.getString("remark")); //在客户端的路径


					
				}

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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return queryinfo;
			
		
	}
	
	
	public CreditratingSubStandardInfo searchsubstandardinfobyid(CreditratingSubStandardInfo info) throws Exception
	{


			CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
			//ArrayList list=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;

			try
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" select id,standardratingid,name,description,mixvalue,maxvalue,remark ");
				sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
				sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
				sbSQL.append(" and a.id= "+info.getId() );
				
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					//CreditratingStandardInfo queryinfo=new CreditratingStandardInfo();
					queryinfo.setId(rs.getLong("id")); 
					queryinfo.setStandardratingid(rs.getLong("standardratingid"));
					queryinfo.setName(rs.getString("name")); 
					queryinfo.setDescription(rs.getString("description")); 
					queryinfo.setMixvalue(rs.getDouble("mixvalue"));
					queryinfo.setMaxvalue(rs.getDouble("maxvalue"));
					queryinfo.setRemark(rs.getString("remark")); 
					
				}

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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return queryinfo;
			
		
	}
	
	public void updaterecord(CreditratingStandardInfo info) throws Exception
	{
		
		
		
		try {
			update(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
		
		
		
	}
	public void updatedetailrecord(CreditratingSubStandardInfo info) throws Exception
	{
		
		
		
		try {
			System.out.println(info.getName());
			update(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
		
	
		
	}
	public void delrecord(CreditratingStandardInfo info) throws Exception
	{
		
		
		
		try {
			update(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
		
		
		
	}
	public void deldetailrecord(CreditratingSubStandardInfo info) throws Exception
	{
		
		
		
		try {
			update(info);
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
			throw new ITreasuryDAOException(e.getMessage(), e);
		
		}
	}
	
	public String checkmixvalue(CreditratingSubStandardInfo info,long updateid)  throws Exception
	{
		//CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
		//ArrayList list=new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String checkstate="true";

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id");
			sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL.append("and mixvalue<>-99999 and maxvalue=-99999");
			sbSQL.append(" and a.standardratingid= "+info.getStandardratingid());
			if(updateid!=-1)
			{
			sbSQL.append(" and a.id!= "+updateid);
			}
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				checkstate="false";
			}
//			sbSQL=null;
			rs=null;
			ps=null;
			StringBuffer sbSQL1 = new StringBuffer();
			sbSQL1.append(" select id");
			sbSQL1.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL1.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL1.append("and maxvalue<>-99999 and maxvalue >"+info.getMixvalue());
			sbSQL1.append(" and a.standardratingid= "+info.getStandardratingid());
			
			if(updateid!=-1)
			{
			sbSQL1.append(" and a.id!= "+updateid);
			}
			
			
			log4j.info(sbSQL1.toString());
			ps = con.prepareStatement(sbSQL1.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}

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
			log4j.error(e.toString());
			throw new IException("Gen_E001");
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return checkstate;
	}
	
	public String checkmaxvalue(CreditratingSubStandardInfo info,long updateid)  throws Exception
	{
		//CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
		//ArrayList list=new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String checkstate="true";

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id");
			sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL.append("and mixvalue=-99999 and maxvalue<>-99999");
			sbSQL.append(" and a.standardratingid= "+info.getStandardratingid());
			if(updateid!=-1)
			{
			sbSQL.append(" and a.id!= "+updateid);
			}
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{

				checkstate="false";

			}
//			sbSQL=null;
			rs=null;
			ps=null;
			StringBuffer sbSQL1 = new StringBuffer();
			sbSQL1.append(" select id");
			sbSQL1.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL1.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			//sbSQL1.append("and mixvalue<>-99999 and maxvalue>"+info.getMaxvalue());// 此处有bug需要修改2009.3.7
			sbSQL1.append("and mixvalue<>-99999 and mixvalue<"+info.getMaxvalue());//修改为这样
			sbSQL1.append(" and a.standardratingid= "+info.getStandardratingid());
			
			if(updateid!=-1)
			{
			sbSQL1.append(" and a.id!= "+updateid);
			}
			
			
			log4j.info(sbSQL1.toString());
			ps = con.prepareStatement(sbSQL1.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}

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
			log4j.error(e.toString());
			throw new IException("Gen_E001");
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return checkstate;
	}
	
	public String checkall(CreditratingSubStandardInfo info,long updateid)  throws Exception
	{
		//CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
		//ArrayList list=new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String checkstate="true";

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id");
			sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL.append("and mixvalue<>-99999 and maxvalue=-99999 and  mixvalue< "+info.getMaxvalue());
			sbSQL.append(" and a.standardratingid= "+info.getStandardratingid());
			
			if(updateid!=-1)
			{
			sbSQL.append(" and a.id!= "+updateid);
			}
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{

				checkstate="false";

				
			}
//			sbSQL=null;
			rs=null;
			ps=null;
			StringBuffer sbSQL1 = new StringBuffer();
			sbSQL1.append(" select id");
			sbSQL1.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL1.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL1.append("and mixvalue=-99999 and maxvalue<>-99999 and maxvalue> "+info.getMixvalue());
			sbSQL1.append(" and a.standardratingid= "+info.getStandardratingid());
			
			if(updateid!=-1)
			{
			sbSQL1.append(" and a.id!= "+updateid);
			}
			
			
			log4j.info(sbSQL1.toString());
			ps = con.prepareStatement(sbSQL1.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}
//			sbSQL=null;
			rs=null;
			ps=null;
			StringBuffer sbSQL2 = new StringBuffer();
			sbSQL2.append(" select id");
			sbSQL2.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL2.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL2.append("and mixvalue<>-99999 and maxvalue<>-99999 and mixvalue>="+info.getMixvalue()+" and maxvalue<= "+info.getMaxvalue());
			sbSQL2.append(" and a.standardratingid= "+info.getStandardratingid());
			if(updateid!=-1)
			{
			sbSQL2.append(" and a.id!= "+updateid);
			}
			
			log4j.info(sbSQL2.toString());
			ps = con.prepareStatement(sbSQL2.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}
//			sbSQL=null;
			rs=null;
			ps=null;
			StringBuffer sbSQL3 = new StringBuffer();
			sbSQL3.append(" select id ");
			sbSQL3.append(" from (select *");
			sbSQL3.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL3.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL3.append(" and mixvalue<>-99999 and maxvalue<>-99999 ");
			sbSQL3.append(" and a.standardratingid= "+info.getStandardratingid()+") b ");
			sbSQL3.append(" where  "+info.getMixvalue()+" > b.mixvalue and "+info.getMixvalue()+" < b.maxvalue ");
			
			if(updateid!=-1)
			{
			sbSQL3.append(" and b.id!= "+updateid);
			}
			
			
			log4j.info(sbSQL3.toString());
			ps = con.prepareStatement(sbSQL3.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}
			
			rs=null;
			ps=null;
			StringBuffer sbSQL4 = new StringBuffer();
			sbSQL4.append(" select id ");
			sbSQL4.append(" from (select *");
			sbSQL4.append(" FROM CRERT_SUBSTANDARDRATING a ");
			sbSQL4.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL4.append(" and mixvalue<>-99999 and maxvalue<>-99999 ");
			sbSQL4.append(" and a.standardratingid= "+info.getStandardratingid()+") b ");
			sbSQL4.append(" where  "+info.getMaxvalue()+" > b.mixvalue and "+info.getMaxvalue()+" < b.maxvalue ");
			
			if(updateid!=-1)
			{
			sbSQL4.append(" and b.id!= "+updateid);
			}
			
			
			log4j.info(sbSQL4.toString());
			ps = con.prepareStatement(sbSQL4.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				checkstate="false";

			}

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
			log4j.error(e.toString());
			throw new IException("Gen_E001");
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return checkstate;
	}
	
	public String checknamainuse(String usename,String tablename,long updateid,long standardratingid)  throws Exception
	{

		String checkstate="true";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select id");
			sbSQL.append(" FROM "+tablename+" a ");
			sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
			sbSQL.append(" and a.name= '"+usename+"'" );
			
			if(tablename=="CRERT_SUBSTANDARDRATING")
			{
				sbSQL.append(" and a.standardratingid= "+standardratingid );
			}
			
			if(updateid!=-1)
			{
				sbSQL.append(" and a.id!= "+updateid );
			}
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				checkstate="false";
				
			}

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
			log4j.error(e.toString());
			throw new IException("Gen_E001");
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return checkstate;

	}
	
	public String  checkstanddel(long id,String checkname) throws Exception
	{

			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String checkstate="true";

			try
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				if(checkname=="standardrating")
				{
					sbSQL.append(" select id ");
					sbSQL.append(" FROM CRERT_RATINGPROJECT a ");
					sbSQL.append(" WHERE a.state!= "+Constant.RecordStatus.INVALID );
					sbSQL.append(" and a.standardratingid= "+id );
				}
				else
				{
					sbSQL.append(" select id ");
					sbSQL.append(" FROM CRERT_RATINGPROJECT a ");
					sbSQL.append(" WHERE a.state!= "+Constant.RecordStatus.INVALID );
					sbSQL.append(" and a.standardratingid= " );
					sbSQL.append(" (select standardratingid" );
					sbSQL.append(" FROM CRERT_SUBSTANDARDRATING b ");
					sbSQL.append(" where b.id= "+id+" )" );
				}
				
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					checkstate="false";

				}

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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return checkstate;
			
		
	}
	
	public ArrayList searchsubstandardinfobyid(CreditratingStandardInfo info) throws Exception
	{


			//CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
			ArrayList list=new ArrayList();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;

			try
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" select id,standardratingid,name,description,mixvalue,maxvalue,remark ");
				sbSQL.append(" FROM CRERT_SUBSTANDARDRATING a ");
				sbSQL.append(" WHERE a.state= "+Constant.RecordStatus.VALID );
				sbSQL.append(" and a.standardratingid= "+info.getId() );
				
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();

				while (rs.next())
				{
					CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
					queryinfo.setId(rs.getLong("id")); 
					queryinfo.setStandardratingid(rs.getLong("standardratingid"));
					queryinfo.setName(rs.getString("name")); 
					queryinfo.setDescription(rs.getString("description")); 
					queryinfo.setMixvalue(rs.getDouble("mixvalue"));
					queryinfo.setMaxvalue(rs.getDouble("maxvalue"));
					queryinfo.setRemark(rs.getString("remark")); 
					list.add(queryinfo);
				}

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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return list;
			
		
	}
	
}
