/**
 * �û���Ȩ�޹�ϵ���ݷ��ʲ���ʵ��
 * @author jinchen
 */ 

package com.iss.itreasury.ebank.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dataentity.OB_Privilege_Of_GroupInfo;
import com.iss.itreasury.util.IException;

import java.sql.*;
import java.util.*;

public class OB_Privilege_Of_GroupDAO extends ITreasuryDAO
{

    public OB_Privilege_Of_GroupDAO()
    {
        super("ob_privilege_of_group");
    }

    public OB_Privilege_Of_GroupDAO(String tableName)
    {
        super(tableName);
    }

    public OB_Privilege_Of_GroupDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public OB_Privilege_Of_GroupDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public OB_Privilege_Of_GroupDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public OB_Privilege_Of_GroupDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    /**
     * ɾ��ĳ�û����Ӧ��Ȩ�޹�ϵ
     * @param groupId
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void del(long groupId) throws IException
        
    {
        try
        {
        initDAO();
        StringBuffer sqlstr = new StringBuffer();
        sqlstr.append("delete OB_privilege_of_group where groupId=" + groupId);
        transPS = transConn.prepareStatement(sqlstr.toString());
        transPS.executeUpdate();
        finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("ɾ��Ȩ���û����ϵ��������",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("ɾ��Ȩ���û����ϵ�������ݿ�����쳣",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("�����쳣",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
    }
    /**
     * ��ѯĳһ���û��������Ȩ��
     * @param groupId
     * @return
     * @throws IException
     */
    public Collection findPrivilegeByGroupId(long groupId)
        throws IException
    {
        Vector v = new Vector();
        try
        {
        this.initDAO();
        OB_Privilege_Of_GroupInfo condition = new OB_Privilege_Of_GroupInfo();
        condition.setGroupID(groupId);
        Collection c = findByCondition(condition);
        for(Iterator iterator = c.iterator(); iterator.hasNext(); v.add((new StringBuffer(String.valueOf(((OB_Privilege_Of_GroupInfo)iterator.next()).getPrivilegeID()))).toString()));
        this.finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("�����û���ID����Ȩ�޷�������",e);
            //throw new IException(null,e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("�����쳣",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        return v.size()>0 ? v : null;
    }
   
}
