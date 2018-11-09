package com.iss.itreasury.fcinterface.bankportal.privilegemgt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.iss.itreasury.fcinterface.bankportal.constant.BooleanValue;
import com.iss.itreasury.fcinterface.bankportal.constant.MonitorAcctConditionType;
import com.iss.itreasury.fcinterface.bankportal.constant.RecordStatus;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.OperationConfig;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.OperationConfig.Operation;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.OperationConfig.Table;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity.DataPrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

public class DataPrivilegeDAO_oracle  extends BaseDAO_oracle implements DataPrivilegeDAO
{
	
	public DataPrivilegeDAO_oracle(Connection conn) throws SystemException
	{
		super(tableName, isNeedPrefix, conn);
		this.setIDType(ID_TYPE_MAXID);
	}
	/**
	 * ���û�����Ӷ�Ӧ�������͵�����Ȩ��
	 */
	public void addPrivilege(DataPrivilegeInfo privilegeInfo) throws SystemException
	{
		try
		{
			if(privilegeInfo.getOperationType() > -1)
			{
			    Operation operation = OperationConfig.getOperation(privilegeInfo.getOperationType());
			    if(operation != null)
			    {			
			    	initDAO();
			    	
			    	PreparedStatement ps = null;
					ResultSet rs = null;
					String levelCode = null;
			    	//�Ǽ�������Ϣ
			    	privilegeInfo.setId(-1);
			    	privilegeInfo.setRdStatus(RecordStatus.VALID);
			    	long privilegeSettingID = this.add(privilegeInfo);
			    	//�Ǽ��˻���������Ӧ�����ݿ����
			    	  //do something
					//���´���level code
			    	StringBuffer strBufSQL = new StringBuffer();
			    	strBufSQL.append("select levelcode from OFFICE where id=");
			    	strBufSQL.append(privilegeInfo.getOfficeID());
			    	ps = transConn.prepareStatement(strBufSQL.toString());
					rs = ps.executeQuery();						
					if (rs.next())
					{			
						levelCode = rs.getString("levelcode");
					}
					else
					{
						throw new Exception("�Ҳ������´���Ϣ");
					}
					strBufSQL = null;
					rs.close();
					ps.close();
					//�������´���ͼ
					String officeViewName = null;					
					officeViewName = "office_"+privilegeInfo.getUserGroupID()+"_"+operation.getType();
		    		//������ͬ����ͼ����Ϊ��ͼ������û�������ģ���������������ϻ����ó������µ�������ͼ
		    		try
		    		{
			    		ps = transConn.prepareStatement("drop view " + officeViewName);
						ps.executeUpdate();			
						ps.close();
		    		}catch(Exception e)
		    		{
		    			//�п��ܻ�����ͼ�������쳣�����Ը��쳣
		    		}
					
		    		strBufSQL = new StringBuffer();
		    		strBufSQL.append("create view " + officeViewName);
		    		strBufSQL.append(" as select a.* from office a");
		    		if(privilegeInfo.getIsContainSub() == BooleanValue.TRUE)
		    		{
		    			strBufSQL.append(" where a.levelcode like '"+levelCode+"%'");
		    		}
		    		else
		    		{
		    			strBufSQL.append(" where a.id="+privilegeInfo.getOfficeID());
		    		}		    		
		    		ps = transConn.prepareStatement(strBufSQL.toString());
					ps.executeUpdate();			
					ps.close();
					strBufSQL = null;		    	
			    	//���Ҫ�����˻���ͼ�������˻���ͼ
					String accountViewName = null;
			    	if(operation.getIsNeedAccount() == BooleanValue.TRUE)
			    	{			
			    		accountViewName = "bs_bankaccountinfo_"+privilegeInfo.getUserGroupID()+"_"+operation.getType();
			    		//������ͬ����ͼ����Ϊ��ͼ������û�������ģ���������������ϻ����ó������µ�������ͼ
			    		try
			    		{
				    		ps = transConn.prepareStatement("drop view " + accountViewName);
							ps.executeUpdate();			
							ps.close();
			    		}catch(Exception e)
			    		{
			    			//�п��ܻ�����ͼ�������쳣�����Ը��쳣
			    		}
						
			    		strBufSQL = new StringBuffer();
			    		strBufSQL.append("create view " + accountViewName);
			    		strBufSQL.append(" as select distinct a.* from bs_bankaccountinfo a");			    		
			    		strBufSQL.append("," + officeViewName + " b where a.n_officeid=b.id");
			    					    		
			    		//��������
			    		if(privilegeInfo.getAcctConditionType() == MonitorAcctConditionType.CONDITIONS)
			    		{			    	
			    			//do something
			    		}
			    		//ָ��
			    		else if(privilegeInfo.getAcctConditionType() == MonitorAcctConditionType.ACCOUNTS)
			    		{
			    			//do something
			    		}
			    		System.out.println(strBufSQL.toString());
			    		ps = transConn.prepareStatement(strBufSQL.toString());
						ps.executeUpdate();			
						ps.close();
						strBufSQL = null;
			    	}
			    	//������ر����ͼ
			    	Collection colTables = operation.getTables();			    	
			    	if(colTables != null && colTables.size()>0)
			    	{
			    		Iterator itTables = colTables.iterator();
			    		while(itTables.hasNext())
			    		{			    			
			    			Table table = (Table)itTables.next();
			    			String tableName = table.getName();
			    			if(tableName.equalsIgnoreCase("bs_accountpropertyonesetting"))
			    			{
			    				tableName = "bs_acctpropertyone";
			    			}
			    			else
			    			{
			    				if(tableName.equalsIgnoreCase("bs_accountpropertytwosetting"))
			    				{
				    				tableName = "bs_acctpropertytwo";
				    			}
				    			else
				    			{
				    				if(tableName.equalsIgnoreCase("bs_accountpropertythreesetting"))
				    				{
				    				    tableName = "bs_acctpropertythree";
				    				}				    				
				    			}
			    			}
			    			String tableViewName = tableName + "_" + privilegeInfo.getUserGroupID() + "_" + operation.getType();
			    			//������ͬ����ͼ
			    			try
			    			{
				    			ps = transConn.prepareStatement("drop view " + tableViewName);
								ps.executeUpdate();			
								ps.close();
			    			}catch(Exception e)
				    		{
				    			//�п��ܻ�����ͼ�������쳣�����Ը��쳣
				    		}
							
			    			strBufSQL = new StringBuffer();
			    			strBufSQL.append("create view " + tableViewName);
			    			if(table.getIsJoinAccountView() == BooleanValue.TRUE)//���˻���ͼ����
			    			{
			    				if(accountViewName == null)
			    				{
			    					throw new Exception("û�ж�Ӧ���˻���ͼ");
			    				}		    				
			    				strBufSQL.append(" as select distinct a.* from " + table.getName() + " a,"+accountViewName+" b where 1=1");
			    				HashMap hmConditon = table.getAndCondition();
			    				if(hmConditon != null)
			    				{
				    				Set keySet = hmConditon.keySet();
				    	    		Iterator itKey = keySet.iterator();
				    	    		while(itKey.hasNext())
				    	    		{
				    	    			String localField = (String)itKey.next();
				    	    	        String otherField = (String)hmConditon.get(localField);
				    	    	        strBufSQL.append(" and a."+localField +"="+"b."+otherField);			    	    	        
				    	    		}
			    				}
			    	    		hmConditon = table.getOrCondition();
			    	    		if(hmConditon != null)
			    				{
			    	    			Set keySet = hmConditon.keySet();
			    	    			Iterator itKey = keySet.iterator();
				    	    		while(itKey.hasNext())
				    	    		{
				    	    			String localField = (String)itKey.next();
				    	    	        String otherField = (String)hmConditon.get(localField);
				    	    	        strBufSQL.append(" or a."+localField +"="+"b."+otherField);			    	    	        
				    	    		}
			    				}
			    			}
			    			//�Ͱ��´�����
			    			else
			    			{
			    				strBufSQL.append(" as select distinct a.* from "+table.getName()+" a");			    				
					    		strBufSQL.append("," + officeViewName + " b where a.n_officeid=b.id");					    			
			    			}			    			
			    			ps = transConn.prepareStatement(strBufSQL.toString());
							ps.executeUpdate();			
							ps.close();
							strBufSQL = null;							
			    		}
			    	}
			    }
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SystemException(e.getMessage(),e);
		}
		finally
		{
			finalizeDAO();
		}
	}	
	
	/**
	 * @return ��Ȩ����û���ID
	 */
	public long autoAuthorize(long userID, long officeID) throws SystemException
	{
		try
		{
			long groupID = -1;			
			//�ж϶�Ӧ���´����û����Ƿ��Ѿ�����
			boolean isGroupExist = false;
			
			initDAO();
			
	    	PreparedStatement ps = null;
			ResultSet rs = null;		
	    	StringBuffer strBufSQL = new StringBuffer();
	    	strBufSQL.append("select n_id from bs_group where n_rdstatus = ");
	    	strBufSQL.append(RecordStatus.VALID);
	    	strBufSQL.append(" and n_officeid=");
	    	strBufSQL.append(officeID);
	    	ps = transConn.prepareStatement(strBufSQL.toString());
			rs = ps.executeQuery();						
			if (rs.next())
			{			
				groupID = rs.getLong("n_id");
				isGroupExist = true;
			}
			strBufSQL = null;
			rs.close();
			ps.close();
			//���û�н�����
			if(!isGroupExist)
			{
			      //���û��������һ����¼,�����û��û������������һ����¼
				  String officeName = null;
				  strBufSQL = new StringBuffer();
				  strBufSQL.append("select sname from OFFICE where id=");
		    	  strBufSQL.append(officeID);
		    	  ps = transConn.prepareStatement(strBufSQL.toString());
				  rs = ps.executeQuery();						
				  if (rs.next())
				  {			
					  officeName = rs.getString("sname");
				  }
				  else
				  {
					  throw new Exception("�Ҳ������´���Ϣ");
				  }
				  strBufSQL = null;
				  rs.close();
				  ps.close();				  
				 
				  String strSQL = "select nvl(max(n_id)+1,1) n_id from bs_group";
				  ps = transConn.prepareStatement(strSQL);
				  rs = ps.executeQuery();						
				  if (rs.next())
				  {			
					  groupID = rs.getLong("n_id");
				  }	
				  rs.close();
				  ps.close();
				  String groupName = "Ĭ���û���_" + officeName;				  
				  strSQL = "insert into bs_group(n_id,s_name,n_officeid,n_rdstatus) values (?,?,?,?)";
				  ps = transConn.prepareStatement(strSQL);
				  ps.setLong(1,groupID);
				  ps.setString(2, groupName);
				  ps.setLong(3,officeID);
				  ps.setLong(4,RecordStatus.VALID);
				  ps.executeUpdate();
				  ps.close();
				  
				  strSQL = "insert into bs_user_group(n_id,n_userid,n_groupid) values ((select nvl(max(n_id)+1,1) n_id from bs_user_group),?,?)";
				  ps = transConn.prepareStatement(strSQL);
				  ps.setLong(1, userID);
				  ps.setLong(2,groupID);				  
				  ps.executeUpdate();
				  ps.close();				  
			}			
			//����Ѿ��ж�Ӧ���û��飬��
			else
			{
			    //�ж��û��Ƿ�������û��飬���û�����ڹ�����������һ����¼
				boolean isRecordExist = false;
				strBufSQL = new StringBuffer();
		    	strBufSQL.append("select n_id from bs_user_group where n_userid = ");
		    	strBufSQL.append(userID);
//		    	strBufSQL.append(" and n_groupid=");
//		    	strBufSQL.append(groupID);
		    	ps = transConn.prepareStatement(strBufSQL.toString());
				rs = ps.executeQuery();						
				if (rs.next())
				{
					isRecordExist = true;
				}
				strBufSQL = null;
				rs.close();
				ps.close();
				if(!isRecordExist)
				{
					String strSQL = "insert into bs_user_group(n_id,n_userid,n_groupid) values ((select nvl(max(n_id)+1,1) n_id from bs_user_group),?,?)";
				    ps = transConn.prepareStatement(strSQL);
				    ps.setLong(1, userID);
				    ps.setLong(2,groupID);				  
				    ps.executeUpdate();
				    ps.close();
				}			    
			}
			//��Ȩ��飬���û�������û����Ȩ�Ĳ������ͽ�����Ȩ
			HashMap hmOperationType = new HashMap();
			strBufSQL = new StringBuffer();
	    	strBufSQL.append("select n_operationtype from bs_privilegesetting where n_usergroupid = ");
	    	strBufSQL.append(groupID);    	    	
	    	strBufSQL.append(" and n_rdstatus=");
	    	strBufSQL.append(RecordStatus.VALID);
	    	ps = transConn.prepareStatement(strBufSQL.toString());
			rs = ps.executeQuery();						
			while(rs.next())
			{
				hmOperationType.put(""+rs.getLong("n_operationtype"),"value");
			}
			strBufSQL = null;
			rs.close();
			ps.close();
			Set keySet = OperationConfig.hmOperation.keySet();
    		Iterator itKey = keySet.iterator();
    		while(itKey.hasNext())
    		{    			
    			String key = (String)itKey.next();
    			OperationConfig.Operation operation = (OperationConfig.Operation)OperationConfig.hmOperation.get(key);
    			if(hmOperationType.get(operation.getType()+"") == null)//�ò���������δ��Ȩ
    			{
    				DataPrivilegeInfo privilegeInfo = new DataPrivilegeInfo();
    				privilegeInfo.setOfficeID(officeID);
    				privilegeInfo.setUserGroupID(groupID);
    				privilegeInfo.setOperationType(operation.getType());
    				privilegeInfo.setIsContainSub(operation.getIsContainSub());    				
    				addPrivilege(privilegeInfo);
    			}
    		}			
			return groupID;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SystemException(e.getMessage(),e);			
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public long getGroupIDByUserID(long userID) throws SystemException
	{

		try
		{
			long groupID = -1;
			
			initDAO();
			
	    	PreparedStatement ps = null;
			ResultSet rs = null;		
	    	StringBuffer strBufSQL = new StringBuffer();
	    	strBufSQL.append("select n_groupid from bs_user_group where n_userid = ");
	    	strBufSQL.append(userID);	    	
	    	ps = transConn.prepareStatement(strBufSQL.toString());
			rs = ps.executeQuery();						
			if (rs.next())
			{			
				groupID = rs.getLong("n_groupid");				
			}
			strBufSQL = null;
			rs.close();
			ps.close();				
			return groupID;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SystemException(e.getMessage(),e);			
		}
		finally
		{
			finalizeDAO();
		}	
	}
	
	public static void main(String[] args)
	{
		try
		{
//			Connection conn = Database.getConnection();
//			DataPrivilegeDAO_oracle dao= new DataPrivilegeDAO_oracle(conn);
//			DataPrivilegeInfo paramInfo = new DataPrivilegeInfo();
//			paramInfo.setOfficeID(3);
//			paramInfo.setUserGroupID(4);
//			paramInfo.setOperationType(1);
//			paramInfo.setIsContainSub(BooleanValue.FALSE);
//			dao.addPrivilege(paramInfo);
			DataPrivilegeDAO_oracle dao= new DataPrivilegeDAO_oracle(null);
			System.out.println(dao.getGroupIDByUserID(1));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
