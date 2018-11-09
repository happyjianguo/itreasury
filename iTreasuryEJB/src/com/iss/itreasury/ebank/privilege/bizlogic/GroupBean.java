/**
 * �û������ҵ���߼���
 * @see com.iss.itreasury.system.privilege.bizlogic.UserDelegation
 */

package com.iss.itreasury.ebank.privilege.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dao.*;
import com.iss.itreasury.ebank.privilege.dataentity.*;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class GroupBean
{

    
    /**
     * Ĭ�ϳ�ʼ������
     * ����java.sql.Connection
     * �������ݷ��ʲ���ʵ��
     *
     */
    public GroupBean()
    {
       super();
    }
   
    /*
     * �������ݿ�����
     */
    private Connection initBean()
        throws ITreasuryDAOException
    {
    	Connection transConn = null;
        try
        {
            
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("���ݿ��ʹ���쳣����", e);
        }
		return transConn;
    }
    /*
     * �ͷ����ݿ�����
     */
    private void finalizeBean(Connection transConn)
        throws ITreasuryDAOException
    {
        try
        {
            if(transConn != null)
            {
                transConn.close();
                transConn = null;
            }
        }
        catch(SQLException e)
        {
            throw new ITreasuryDAOException("���ݿ�ر��쳣����", e);
        }
    }

   
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupID
     * @return
     * @throws IException
     * @throws ITreasuryDAOException
     */
    public OB_GroupInfo findGroupInfoByID(long groupID) throws IException
        
    {	
    	
        OB_GroupInfo groupinfo = null;
    	OB_GroupDAO groupDao = new OB_GroupDAO();
    	
    	try
        {
            groupinfo = (OB_GroupInfo)groupDao.findByID(groupID, (new OB_GroupInfo()).getClass());
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("�����û�����Ϣ����",e);
        }
        
        return groupinfo;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupID
     * @return
     * @throws IException
     * @throws ITreasuryDAOException
     */
    public Collection findGroupPrivilegeByID(long groupID) throws IException
        
    {
    	
    	
    	OB_Privilege_Of_GroupDAO privilege_groupDao = new OB_Privilege_Of_GroupDAO();
        Collection c = privilege_groupDao.findPrivilegeByGroupId(groupID);
        
        return c;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupCondition
     * @param pgCondition
     * @return
     * @throws IException
     * @throws SQLException
     * @throws IException
     */
    public long addGroup(OB_GroupInfo groupCondition, OB_Privilege_Of_GroupInfo pgCondition[]) throws IException
        
    {
        long groupId = -1;
    	Connection transConn = null;
    	try
        {
            transConn = this.initBean();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException("���ݿ��ʼ����������",e1);
        }
        try
        {
    	OB_GroupDAO groupDao = new OB_GroupDAO("OB_group", transConn);
    	OB_Privilege_Of_GroupDAO privilege_groupDao = new OB_Privilege_Of_GroupDAO("OB_Privilege_Of_Group", transConn);
    	
    	
    	
        
        Collection c = groupDao.findGroupByName(groupCondition.getName(),groupCondition.getClientId());
        if(c != null && c.size() > 0)
            throw new IException("��ӵ��û������Ѵ��ڣ�������¼�롣");
        
            groupId = groupDao.add(groupCondition);
            int len = pgCondition.length;
            for(int i = 0; i < len; i++)
            {
                pgCondition[i].setGroupID(groupId);
                privilege_groupDao.add(pgCondition[i]);
            }

            transConn.commit();
            this.finalizeBean(transConn);
        }
		catch(IException e)
		{
			throw new IException("��ӵ��û������Ѵ��ڣ�������¼�롣");
		}        
        catch(Exception e)
        {
            try
            {
                transConn.rollback();
            } catch (SQLException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            System.out.println("ϵͳ���󣬻ع�������ǰ״̬");
            e.printStackTrace();
            throw new IException("�����û������ʧ�ܣ�ϵͳ�Ѿ��ָ�������ǰ״̬��");
        }
        
        finally
        {
            try
            {
                this.finalizeBean(transConn);
            } catch (ITreasuryDAOException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        
        return groupId;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupId
     * @throws IException
     * @throws SQLException
     * @throws IException
     */
    public long delGroup(long groupId) throws IException
        
    {	
    	long nResult = -1;
    	Connection transConn = null;
    	try
        {
            transConn = this.initBean();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException("���ݿ��ʼ����������",e1);
        }
        try
        {
    	OB_GroupDAO groupDao = new OB_GroupDAO("OB_group", transConn);
    	OB_Privilege_Of_GroupDAO privilege_groupDao = new OB_Privilege_Of_GroupDAO("OB_Privilege_Of_Group", transConn);
    	OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", transConn);
    	
        Collection c = group_userDao.findUserByGroupId(groupId);
        if(c != null && c.size() > 0)
            throw new IException("�������ڱ�ʹ�ò��ܲ�ɾ����");
        
            privilege_groupDao.del(groupId);
            groupDao.del(groupId);
            transConn.commit();
            nResult = 1;
            this.finalizeBean(transConn);
        }
        catch(Exception e)
        {
            try
            {
                transConn.rollback();
            } catch (SQLException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            System.out.println("ϵͳ���󣬻ع�������ǰ״̬");
            e.printStackTrace();
            throw new IException("ɾ���û������ʧ�ܣ��������ڱ�ʹ�ò���ɾ��.");
        }
        
        finally
        {
            try
            {
                this.finalizeBean(transConn);
            } catch (ITreasuryDAOException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        return nResult;
    }

   /**
    * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
    * @param groupCondition
    * @param pgCondition
    * @return
 * @throws IException
    * @throws SQLException
    * @throws IException
    */

    public long updateGroup(OB_GroupInfo groupCondition, OB_Privilege_Of_GroupInfo pgCondition[]) throws IException
       
    {
        long nResult = -1;
        
        Connection transConn = null;
    	try
        {
            transConn = this.initBean();
        } catch (ITreasuryDAOException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new IException("���ݿ��ʼ����������",e2);
        }
        try
        {
    	OB_GroupDAO groupDao = new OB_GroupDAO("OB_group", transConn);
    	OB_Privilege_Of_GroupDAO privilege_groupDao = new OB_Privilege_Of_GroupDAO("OB_Privilege_Of_Group", transConn);
    	
        Collection c = groupDao.findGroupByName(groupCondition.getName(),groupCondition.getClientId());
        if(c!=null&&c.size() > 0)
        {
            long groupId = ((OB_GroupInfo)c.iterator().next()).getId();
            if(groupId != groupCondition.getId())
                {
            	 throw new IException("�û������Ѿ����ڣ����޸��û������������û���ʧ�ܡ�");
            }
            else
                try
                {
                    privilege_groupDao.del(groupId);
                    int len = pgCondition.length;
                    for(int i = 0; i < len; i++)
                    {
                        pgCondition[i].setGroupID(groupId);
                        privilege_groupDao.add(pgCondition[i]);
                    }

                    groupDao.update(groupCondition);
                    transConn.commit();
                    nResult = 1;
                    System.out.println("�û�����Ϣ���³ɹ�");
                }
                catch(Exception e)
                {
                    transConn.rollback();
                    System.out.println("ϵͳ���󣬻ع�������ǰ״̬");
                    e.printStackTrace();
                    throw new IException("�����û������ʧ�ܣ�ϵͳ�Ѿ��ָ�������ǰ״̬��");
                }
               
        } else
        {
            long groupId = groupCondition.getId();
            System.out.println("Ҫ���µ���idΪ           " + groupId);
            try
            {
                privilege_groupDao.del(groupId);
                int len = pgCondition.length;
                for(int i = 0; i < len; i++)
                {
                    pgCondition[i].setGroupID(groupId);
                    privilege_groupDao.add(pgCondition[i]);
                }

                groupDao.update(groupCondition);
                transConn.commit();
                nResult = 1;
                System.out.println("�û�����³ɹ�");
            }
            catch(Exception e1)
            {
                transConn.rollback();
                System.out.println("ϵͳ���󣬻ع�������ǰ״̬");
                e1.printStackTrace();
                throw new IException("�����û������ʧ�ܣ�ϵͳ�Ѿ��ָ�������ǰ״̬��");
            }
           
        }
        this.finalizeBean(transConn);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                this.finalizeBean(transConn);
            } catch (ITreasuryDAOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nResult;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param moduleId
     * @return
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findPrivilegesbyModuleId(long moduleId) throws IException
        
    {
    	
    	Collection c = null;
     	
     	OB_PrivilegeDAO privilegeDao = new OB_PrivilegeDAO();
        c = privilegeDao.findPrivilegesbyModuleId(moduleId);
       
        return c;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param moduleId
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findGroupByModuleId(long moduleId)
        throws ITreasuryDAOException
    {
    	Connection transConn = null;
    	Collection c = null;
    	
    	OB_GroupDAO groupDao = new OB_GroupDAO();
        OB_GroupInfo info = new OB_GroupInfo();
        info.setModuleID(moduleId);
        c = groupDao.findByCondition(info);
       
        return c;
    }
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param args
     * @throws SQLException
     * @throws IException
     */
    public static void main(String args[])
        throws SQLException, IException
    {
        GroupBean bean = new GroupBean();
        OB_GroupInfo groupinfo = new OB_GroupInfo();
        groupinfo.setName("test2");
        groupinfo.setOfficeID(1L);
        groupinfo.setModuleID(2L);
        groupinfo.setRank("h");
        groupinfo.setInputUserID(1L);
        OB_Privilege_Of_GroupInfo privilege_groupinfo = new OB_Privilege_Of_GroupInfo();
        privilege_groupinfo.setPrivilegeID(33L);
        OB_Privilege_Of_GroupInfo privilege_group[] = {
            privilege_groupinfo
        };
        try
        {
            bean.delGroup(23L);
        }
        catch(ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
    }

	/**
	 * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
	 * @param groupinfo
	 * @param orderCondition
	 * @return
	 * @throws IException
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findGroupByCondition(OB_GroupInfo groupinfo, long orderCondition) throws IException 
	{
		// TODO Auto-generated method stub
		
    	Collection c = null;
    	
    	OB_GroupDAO groupDao = new OB_GroupDAO();
		c = groupDao.findGroupbyCondition(groupinfo,orderCondition);
		
		return c;
	}
	/**
	 * 
	 * @param groupId
	 * @return
	 * @throws IException
	 * @throws SQLException
	 * @throws ITreasuryDAOException
	 */
	public Collection findPrivilegesByGroupId(long groupId) throws IException 
	{
		Connection transConn = null;
    	Collection c = null;
    	
    	OB_PrivilegeDAO privilegeDao = new OB_PrivilegeDAO();
		c = privilegeDao.findPrivilegesByGroupId(groupId);
		
		return c;
	}
}
