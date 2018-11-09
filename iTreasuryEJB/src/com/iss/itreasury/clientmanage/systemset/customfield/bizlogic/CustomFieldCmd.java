/* Generated by Together */

package com.iss.itreasury.clientmanage.systemset.customfield.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.clientmanage.systemset.customfield.dataentity.CustomFieldInfo;
import com.iss.itreasury.command.Command;
import com.iss.itreasury.clientmanage.systemset.customfield.dao.CustomFieldDAO;
import com.iss.itreasury.util.Database;

public class CustomFieldCmd extends Command{
    /**
	 * 增加一条字段名称设置信息，增加以前检查相同fieldID 的记录是否已经存在，如果存在不允许增加
     * @throws Exception
	 */
    public long save(Collection c) throws Exception{
    	Connection con = null;
		CustomFieldDAO dao = null;
		CustomFieldInfo info=null;

		try
		{
			con = Database.getConnection();
			long size=c.size();
			if (c!=null && size>0)
			{
				dao = new CustomFieldDAO(con);
				dao.setUseMaxID();
				for(int i = 0; i < size; i++)
				{
					ArrayList l=(ArrayList)c;
					info=(CustomFieldInfo)l.get(i);
					if (info.getId()<=0)
					{
						dao.add(info);
					}else{
						dao.update(info);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			throw e;
   		}finally{
			clearConn(con);
		}		
		return 1;
    }

    /**
	 * 修改自定义字段名称设置信息，fieldID不允许修改
     * @throws Exception
	 */
    /*
    public long update(String[] FieldID,String[] name,Timestamp InputDate,long InputUserID,long StatusID) throws Exception
    {
    	Connection con = null;
    	CustomFieldDAO customfielddao = null;
    	CustomFieldInfo CFInfo = new CustomFieldInfo();
    	long lReturn = -1;
    	
    	try
		{
    		con = Database.getConnection();
    		customfielddao = new CustomFieldDAO(con);
    		
    		CFInfo.setInputDate(InputDate);
    		CFInfo.setInputUserID(InputUserID);
    		CFInfo.setStatusID(StatusID);
    		
    		for (int i = 0;name != null && i<name.length;i++)
    		{
    			
    			CFInfo.setName(name[i]);
    			CFInfo.setFieldID(Long.parseLong(FieldID[i]));
    			if(customfielddao.checkByFieldInfo(CFInfo)>0)
				{
					lReturn = -2;//存在相同名称的FieldID，不能保存
				}
				else
				{
					lReturn = customfielddao.update(CFInfo);
				} 			
    		}
		}
    	catch(Exception e)
		{
    		if(con != null)
    		{
    			con.close();
    			con = null;
    		}
		}
    	finally
		{
    		try
			{
    			if(con != null)
        		{
        			con.close();
        			con = null;
        		}
			}
    		catch(Exception ex)
			{
    			throw new Exception(ex.getMessage());
			}
		}
    	return (lReturn);
    }
*/
    /**
	 * 删除记录
	 */
    public void delete(long id){}

    /**
	 * 自定义字段名称设置库中是否已经存在fieldID相同的记录
	 */
    public boolean exists(String fieldID){
    	
    	
    	return false;
    }

    /**
	 * 根据条件查询结果集，通过ITreasuryDAO的相关方法执行查询
     * @throws Exception
	 */
    public Vector findByCondition(long StatusID, long officeID) throws Exception {
    	{
    		Connection con = null;
    		CustomFieldDAO customfielddao = null;
    		boolean bSucceed = false;
    		Vector v = new Vector();
    		try
    		{
    			//con = Database.get_jdbc_connection();
    			con = Database.getConnection();
    			customfielddao = new CustomFieldDAO(con);
    			
    			//查询审批设置信息
    			v = customfielddao.findAttributeInfo(StatusID, officeID);
    			
    			con.close();
    			con = null;
    		}
    		catch (Exception e)
    		{
    		    if (con != null)
    			{
    				con.close();
    				con = null;
    			}
       		}
    		finally
    		{
    			try
    			{
    				if (con != null)
    				{
    					con.close();
    					con = null;
    				}
    			}
    			catch (Exception ex)
    			{
    				throw new Exception(ex.getMessage());
    			}
    		}
    		return (v);
    	}
    }
    
    /**
	 * 根据条件查询结果集，通过ITreasuryDAO的相关方法执行查询
     * @throws Exception
	 */
    public Vector findByCondition(long StatusID, long officeID,long currencyID) throws Exception {
    	{
    		Connection con = null;
    		CustomFieldDAO customfielddao = null;
    		boolean bSucceed = false;
    		Vector v = new Vector();
    		try
    		{
    			//con = Database.get_jdbc_connection();
    			con = Database.getConnection();
    			customfielddao = new CustomFieldDAO(con);
    			
    			//查询审批设置信息
    			v = customfielddao.findAttributeInfo(StatusID, officeID, currencyID);
    			
    			con.close();
    			con = null;
    		}
    		catch (Exception e)
    		{
    		    if (con != null)
    			{
    				con.close();
    				con = null;
    			}
       		}
    		finally
    		{
    			try
    			{
    				if (con != null)
    				{
    					con.close();
    					con = null;
    				}
    			}
    			catch (Exception ex)
    			{
    				throw new Exception(ex.getMessage());
    			}
    		}
    		return (v);
    	}
    }
    
    public  String  getFieldname(int FieldID) throws Exception
    {	
    	String str="";
    	Connection con = null;
    	try{
    	con = Database.getConnection();
    	str = new CustomFieldDAO(con).getFieldname(FieldID);
    	return str;
    	}catch (Exception ex){
    		ex.printStackTrace();
    		throw ex;
    	}finally{
    		clearConn(con);
    	}
    }
    
    public  String  getFieldname(long FieldID, long officeID,long currencyID) throws Exception
    {	
    	String str="";
    	Connection con = null;
    	try{
	    	con = Database.getConnection();
	    	str = new CustomFieldDAO(con).getFieldname(FieldID, officeID,currencyID);
	    	return str;
    	}catch (Exception ex){
    		ex.printStackTrace();
    		throw ex;
    	}finally{
    		clearConn(con);
    	}
    }
}

