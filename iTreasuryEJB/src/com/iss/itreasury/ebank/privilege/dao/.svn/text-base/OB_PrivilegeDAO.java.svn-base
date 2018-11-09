/**
 * 权限数据库访问层类实体
 * 
 * @author jinchen
 */

package com.iss.itreasury.ebank.privilege.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dataentity.OB_PrivilegeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class OB_PrivilegeDAO extends ITreasuryDAO
{

    public OB_PrivilegeDAO()
    {
        super("ob_privilege");
    }

    public OB_PrivilegeDAO(String tableName)
    {
        super(tableName);
    }

    public OB_PrivilegeDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public OB_PrivilegeDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public OB_PrivilegeDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public OB_PrivilegeDAO(String tableName, boolean isNeedPrefix,
            Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 根据模块ID查询所有权限
     * 
     * @param moduleId
     * @return @throws
     *         SQLException
     * @throws IException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesbyModuleId(long moduleId) throws IException
            
    {
        Vector co = new Vector();
        try
        {
        initDAO();
        
            StringBuffer sb = new StringBuffer();

            sb.append("select * from OB_privilege where moduleId=");
            sb.append(moduleId);
            sb.append(" and plevel<>999 and statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" order by privilegeNo");
            transPS = transConn.prepareStatement(sb.toString());
            OB_PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
 
            while (transRS.next())
            {
                privilege = new OB_PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
        
        finalizeDAO();
        
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据模块ID查找权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据模块ID查找权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        return co.size() > 0 ? co : null;
    }

    /**
     * 根据模块查询用户具有的所有权限取并集
     * 
     * @param userId
     * @param moduleID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     * @throws IException
     */
    public Collection getPrivilegeOfUser(long userId, long moduleID,
            long clientId) throws IException
    {
        Vector v = new Vector();
        try
        {
        initDAO();
        StringBuffer sb = new StringBuffer();
        sb.append(" select distinct sp.* \n");
        sb
                .append(" from OB_privilege sp,OB_group_of_user sgou, OB_privilege_of_group spou \n");
        sb
                .append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid  \n");
        sb.append("   and sgou.userid= ");
        sb.append(userId);
        sb.append("   and sp.moduleId= ");
        sb.append(moduleID);
        sb.append("  and sp.statusid = ");
        sb.append(Constant.RecordStatus.VALID);
        sb.append(" order by privilegeNo ");
        System.out.println("查询用户权限的sql为            " + sb);
        transPS = transConn.prepareStatement(sb.toString());
        transRS = transPS.executeQuery();
       
        OB_PrivilegeInfo OB_privilegeEntity;

        while (transRS.next())
        {
            OB_privilegeEntity = new OB_PrivilegeInfo();
            OB_privilegeEntity.setId(transRS.getLong("id"));
            OB_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
            OB_privilegeEntity.setName(transRS.getString("name"));
            OB_privilegeEntity.setPlevel(transRS.getLong("plevel"));
            OB_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
            OB_privilegeEntity.setModuleID(moduleID);
            v.add(OB_privilegeEntity);
        }

        finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        return v.size() > 0 ? v : null;

    }
    
    /**
     * 根据模块查询用户具有的所有权限取并集
     * 
     * @param userId
     * @param moduleID
     * @param 权限ID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     * @throws IException
     */
    public Collection getPrivilegeOfUserForYQ(long userId, long moduleID,String privilegeNo)
            throws IException
    {

        Vector v = new Vector();
        try
        {
            
        
        initDAO();
        StringBuffer sb = new StringBuffer();
        sb.append(" select distinct sp.* \n");
        sb.append(" from OB_privilege sp,OB_group_of_user sgou, OB_privilege_of_group spou \n");
        sb.append(" where sp.id=spou.privilegeId and spou.groupid=sgou.groupid  \n");
        sb.append("   and sgou.userid= ");
        sb.append(userId);
        sb.append("   and sp.moduleId= ");
        sb.append(moduleID);
        sb.append("  and sp.statusid = ");
        sb.append(Constant.RecordStatus.VALID);
        sb.append("  and sp.privilegeno like '");
        sb.append(privilegeNo);
        sb.append("%'");
        sb.append("   and sp.plevel != 1");
        sb.append(" order by privilegeNo ");
        System.out.println("查询用户权限的sql为            " + sb);
        transPS = transConn.prepareStatement(sb.toString());
        transRS = transPS.executeQuery();
        
        OB_PrivilegeInfo OB_privilegeEntity;

        while (transRS.next())
        {
            OB_privilegeEntity = new OB_PrivilegeInfo();
            OB_privilegeEntity.setId(transRS.getLong("id"));
            OB_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
            OB_privilegeEntity.setName(transRS.getString("name"));
            OB_privilegeEntity.setPlevel(transRS.getLong("plevel"));
            OB_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
            OB_privilegeEntity.setModuleID(moduleID);
            v.add(OB_privilegeEntity);
        }

        finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
        return v.size() > 0 ? v : null;

    }

    /**
     * 根据模块查询用户具有的所有权限取并集
     * modify by leiyang 20081117
     * 
     * @param userId
     * @param moduleID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     * @throws IException
     */
    public Collection getPrivilegeOfUser(long userId, long moduleID) throws Exception
    {
        Collection coll = null;
        try
        {
 	       /*-----------------init DAO --------------------*/
 	       try {
 	         initDAO();
 	       }
 	       catch (ITreasuryDAOException e) {
 	          throw new ITreasuryDAOException("创建连接时异常",e);
 	       }
 	       
 	       try {
 	    	   StringBuffer strSQL = new StringBuffer();
 	    	   strSQL.append(" select distinct sp.id id, sp.name name, sp.officeid officeid, sp.moduleid moduleid, nvl(sp.menuurl,'null') menuurl, sp.statusid statusid, sp.privilegeno privilegeno, sp.plevel  plevel");
 	    	   strSQL.append(" from OB_privilege sp, OB_group_of_user sgou, OB_privilege_of_group spou");
 	    	   strSQL.append(" where sp.id = spou.privilegeId");
 	    	   strSQL.append(" and spou.groupid = sgou.groupid");
 	    	   strSQL.append(" and sgou.userid = ?");
 	    	   strSQL.append(" and sp.moduleId = ?");
 	    	   strSQL.append(" and sp.statusid = ?");
 	    	   //strSQL.append(" and sp.plevel != 1");
 	    	   strSQL.append(" order by privilegeNo ");

		       prepareStatement(strSQL.toString());
		       transPS.setLong(1, userId);
		       transPS.setLong(2, moduleID);
		       transPS.setLong(3, Constant.RecordStatus.VALID);
		       executeQuery();
		        
		       OB_PrivilegeInfo obpEntity = null;
		       coll = new Vector();
		       while (transRS.next()) {
		    	   obpEntity = new OB_PrivilegeInfo();
		    	   obpEntity.setId(transRS.getLong("id"));
		    	   obpEntity.setMenuUrl(transRS.getString("menuurl"));
		    	   obpEntity.setName(transRS.getString("name"));
		    	   obpEntity.setPlevel(transRS.getLong("plevel"));
		    	   obpEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
		    	   obpEntity.setModuleID(moduleID);
		    	   coll.add(obpEntity);
		       }
 	       }
	       catch (Exception e) {
	    	   throw new ITreasuryDAOException("查询用户菜单出错", e);
		   }
	       /*----------------finalize Dao-----------------*/
	       try {
	         finalizeDAO();
	       }
	       catch (ITreasuryDAOException e) {
	         throw new ITreasuryDAOException("关闭连接时异常",e);
	       }
	       /*----------------end of finalize---------------*/
        }
        catch(Exception e)
        {
             e.printStackTrace();
             throw new IException("查询异常",e);
        }
        finally
        {
        	finalizeDAO();
        }
        return (coll.size() > 0 ? coll : null);
    }
    
    
    /**
     * 根据模块和相应URL查询用户是否具权限
     * modify by leiyang 20081117
     * 
     * @param userId
     * @param moduleID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     * @throws IException
     */
    public OB_PrivilegeInfo getPrivilegeInfo(long userId, long moduleID, String strMenuurl) throws Exception
    {
    	OB_PrivilegeInfo privilegeInfo = null;
        try
        {
 	       /*-----------------init DAO --------------------*/
 	       try {
 	         initDAO();
 	       }
 	       catch (ITreasuryDAOException e) {
 	          throw new ITreasuryDAOException("创建连接时异常",e);
 	       }
 	       
 	       try {
 	    	   StringBuffer strSQL = new StringBuffer();
 	    	   strSQL.append(" select distinct sp.id id, sp.name name, sp.officeid officeid, sp.moduleid moduleid, sp.menuurl menuurl, sp.statusid statusid, sp.privilegeno privilegeno, sp.plevel  plevel");
 	    	   strSQL.append(" from OB_privilege sp, OB_group_of_user sgou, OB_privilege_of_group spou");
 	    	   strSQL.append(" where sp.id = spou.privilegeId");
 	    	   strSQL.append(" and spou.groupid = sgou.groupid");
 	    	   strSQL.append(" and sgou.userid = ?");
 	    	   strSQL.append(" and sp.moduleId = ?");
 	    	   strSQL.append(" and sp.statusid = ?");
 	    	   strSQL.append(" and sp.menuurl = ?");
 	    	   //strSQL.append(" and sp.plevel != 1");
 	    	   strSQL.append(" order by privilegeNo ");

		       prepareStatement(strSQL.toString());
		       transPS.setLong(1, userId);
		       transPS.setLong(2, moduleID);
		       transPS.setLong(3, Constant.RecordStatus.VALID);
		       transPS.setString(4, strMenuurl);
		       executeQuery();
		        
		       while (transRS.next()) {
		    	   privilegeInfo = new OB_PrivilegeInfo();
		    	   privilegeInfo.setId(transRS.getLong("id"));
		    	   privilegeInfo.setMenuUrl(transRS.getString("menuurl"));
		    	   privilegeInfo.setName(transRS.getString("name"));
		    	   privilegeInfo.setPlevel(transRS.getLong("plevel"));
		    	   privilegeInfo.setPrivilegeNo(transRS.getString("privilegeNo"));
		    	   privilegeInfo.setModuleID(moduleID);
		       }
 	       }
	       catch (Exception e) {
	    	   throw new ITreasuryDAOException("查询用户菜单出错", e);
		   }
	       /*----------------finalize Dao-----------------*/
	       try {
	         finalizeDAO();
	       }
	       catch (ITreasuryDAOException e) {
	         throw new ITreasuryDAOException("关闭连接时异常",e);
	       }
	       /*----------------end of finalize---------------*/
        }
        catch(Exception e)
        {
             e.printStackTrace();
             throw new IException("查询异常",e);
        }
        finally
        {
        	finalizeDAO();
        }
        return privilegeInfo;
    }
    

    public Collection findPrivilegesByGroupId(long groupId)
            throws  IException
    {
        Vector v = new Vector();
        try
        {
        initDAO();
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from OB_privilege ");
        sb
                .append(" where id  in (select  privilegeId from OB_privilege_of_group ");
        sb.append(" where groupId  =   ");
        sb.append(groupId);
        sb.append(" )  ");

        sb.append(" order by privilegeNo ");
        System.out.println("查询用户组权限的sql为            " + sb);
        transPS = transConn.prepareStatement(sb.toString());
        transRS = transPS.executeQuery();
        
        OB_PrivilegeInfo OB_privilegeEntity;

        while (transRS.next())
        {
            OB_privilegeEntity = new OB_PrivilegeInfo();
            OB_privilegeEntity.setId(transRS.getLong("id"));
            OB_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
            OB_privilegeEntity.setName(transRS.getString("name"));
            OB_privilegeEntity.setPlevel(transRS.getLong("plevel"));
            OB_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
            //OB_privilegeEntity.setModuleID(moduleID);
            v.add(OB_privilegeEntity);
        }

        finalizeDAO();
        
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户组权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户组权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
        return v.size() > 0 ? v : null;

    }

    /**
     * 初始化权限信息
     * 
     * @param privilege
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void addPrivilege(OB_PrivilegeInfo privilege) throws IException
            
    {
        try
        {
        initDAO();
        
        long lMaxID = 1;
        ResultSet rs = null;
        String sqlstr = "select nvl(max(id),0)+1 maxid from OB_privilege ";
        Statement stmt = transConn.createStatement();
        System.out.println(sqlstr);
        rs = stmt.executeQuery(sqlstr);
        if(rs!=null && rs.next())
        {
        	lMaxID = rs.getLong("maxid");
        }
        rs.close();
       
        sqlstr = "INSERT INTO OB_privilege (plevel,privilegeNo,officeID,moduleID,name,Id,menuUrl,statusID) VALUES ("
                + privilege.getPlevel()
                + ",'"
                + privilege.getPrivilegeNo()
                + "',"
                + privilege.getOfficeID()
                + ","
                + privilege.getModuleID()
                + ",'"
                + privilege.getName()
                + "'," + lMaxID + ",'"
                + privilege.getMenuUrl()
                + "',"
				+ privilege.getStatusID() + " ) ";
        stmt = transConn.createStatement();
        System.out.println(sqlstr);
        stmt.executeUpdate(sqlstr);
       
        finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("新增权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("新增权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
    }
    
    public long findPrivilegeIdByNo(String privilege) throws IException
             
    {
        long lReturn = -1;
        try
        {
            initDAO();
       
        String strSQL = "select id from OB_privilege where PRIVILEGENO = '"
                + privilege + "' and statusid =1";
        transPS = transConn.prepareStatement(strSQL);
        System.out.println(strSQL);
        transRS = transPS.executeQuery();
        if (transRS != null && transRS.next())
        {
            lReturn = transRS.getLong("id");
        }
        this.finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据权限编号查找权限ID发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据权限编号查找权限ID发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
        return lReturn;
    }

    public Collection getAllPrivilege() throws IException 
    {
        Vector v = new Vector();
        try
        {
            initDAO();
      //      String strSQL = "select * from OB_privilege where statusid =1";

            String strSQL = "select * from OB_privilege where statusid =1 order by privilegeNo";
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                OB_PrivilegeInfo info = new OB_PrivilegeInfo();
                info.setId(transRS.getLong("id"));
                info.setMenuUrl(transRS.getString("menuurl"));
                info.setName(transRS.getString("name"));
                info.setPlevel(transRS.getLong("plevel"));
                info.setPrivilegeNo(transRS.getString("privilegeNo"));
                //OB_privilegeEntity.setModuleID(moduleID);
                v.add(info);
            }
            finalizeDAO();
            
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
       
        return v.size() > 0 ? v : null;
    }
    
    /**
     * 查询closed_operate = 1
     * 并且statusid = 1的权限
     * @param privilege
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findPrivilegesbyPrivilege()throws  IException
    {
		Vector v = new Vector();
		try
		{
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from OB_privilege ");
			sb.append(" where  statusid = 1 and privilege_isable = 1");
			sb.append(" order by privilegeNo ");
			System.out.println("查询用户组权限的sql为************" + sb.toString());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			OB_PrivilegeInfo OB_privilegeEntity;
			while (transRS.next())
			{
			    OB_privilegeEntity = new OB_PrivilegeInfo();
			    OB_privilegeEntity.setId(transRS.getLong("id"));
			    OB_privilegeEntity.setMenuUrl(transRS.getString("menuurl"));
			    OB_privilegeEntity.setName(transRS.getString("name"));
			    OB_privilegeEntity.setPlevel(transRS.getLong("plevel"));
			    OB_privilegeEntity.setPrivilegeNo(transRS.getString("privilegeNo"));
			    OB_privilegeEntity.setPrivilegeisable(transRS.getLong("privilege_isable"));
			    //OB_privilegeEntity.setModuleID(moduleID);
			    v.add(OB_privilegeEntity);
			}
			
			finalizeDAO();
		
		} catch (ITreasuryDAOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    throw new IException("查找用户组权限发生错误",e);
		    //throw new IException(null,e);
		} catch (SQLException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    throw new IException("查找用户组权限发生数据库操作异常",e);
		}
		catch(Exception e)
		 {
		     e.printStackTrace();
		     throw new IException("其他异常",e);
		 }
		finally
		{
		    this.finalizeDAO();  
		}		
		return v.size() > 0 ? v : null;
    }    
    
    public Collection getLastPrivilege() throws IException 
    {
        Vector v = new Vector();
        try
        {
            initDAO();
            String strSQL = "select * from OB_privilege where statusid =1 and privilegeno like '6-10%'";

            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                OB_PrivilegeInfo info = new OB_PrivilegeInfo();
                info.setId(transRS.getLong("id"));
                info.setMenuUrl(transRS.getString("menuurl"));
                info.setName(transRS.getString("name"));
                info.setPlevel(transRS.getLong("plevel"));
                info.setPrivilegeNo(transRS.getString("privilegeNo"));
                //OB_privilegeEntity.setModuleID(moduleID);
                v.add(info);
            }
            finalizeDAO();
            
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找所有权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
       
        return v.size() > 0 ? v : null;
    }    

    /**
     * 更新Privilege数据
     * @param privilege
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void initPrivilege() throws IException
    
    {
        try
        {
	        initDAO();     
	        String sqlstr = "update OB_privilege set privilege_isable  = '0'";
	        Statement stmt = transConn.createStatement();
	        stmt.executeUpdate(sqlstr);
	        finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("跟新权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("新增权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
            this.finalizeDAO();
        }
        
    }
    
    /**
     * 更新Privilege数据
     * @param privilege
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void updatePrivilege(String strPvgNo) throws IException
            
    {
        try
        {
	        initDAO();     
	        String sqlstr = "update OB_privilege set privilege_isable = 1 where privilegeno = '"+strPvgNo+"'";
	        Statement stmt = transConn.createStatement();
	        System.out.println(sqlstr);
	        stmt.executeUpdate(sqlstr);
	        finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("跟新权限发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("新增权限发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
            this.finalizeDAO();
        }
        
    }
    

    public static void main(String args1[])
    {

    }
}