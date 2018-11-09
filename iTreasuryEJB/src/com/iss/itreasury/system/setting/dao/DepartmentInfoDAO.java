/*
 * Created on 2004-7-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.setting.dao ;
import java.sql.Connection;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Database;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class DepartmentInfoDAO extends ITreasuryDAO
{
	/**
	 *  
	 */
	public DepartmentInfoDAO ( )
	{
		super ( "Department" ) ;
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 */
	public DepartmentInfoDAO ( String tableName )
	{
		super ( tableName ) ;
		 super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public DepartmentInfoDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
		 super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param isNeedPrefix
	 */
	public DepartmentInfoDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
		 super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public DepartmentInfoDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
		 super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public DepartmentInfoDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
		 super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	public long  deleteByID (long id) throws Exception
	{
		long ret=-1;
		String sql="";
		String sql1="";
		try {
		initDAO();
		sql="select * from userinfo where ndepartmentid="+id+"";
        transPS = transConn.prepareStatement(sql);
        transRS=transPS.executeQuery();
        if(transRS.next()){
        	ret=-2;
        }else{
        	sql1="update department set statusid=0 where id="+id+"";
        	transPS = transConn.prepareStatement(sql1);
	        ret=transPS.executeUpdate();	        
        }
        System.out.println(sql+"^^^^^^^^^^^^"+sql1);
		   finalizeDAO();
	    }catch (Exception e){
	        e.printStackTrace();
	        finalizeDAO();
	    }finally{
	        finalizeDAO();
	    }
		 return ret;
	}
}