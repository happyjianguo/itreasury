package com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity;

import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

public class DataPrivilegeInfo extends BaseDataEntity 
{	
	private long id = -1;    //唯一标识
	private long userGroupID = -1;//用户组ID	
	private long operationType = -1;//操作类型	
	private long isContainSub = -1;//是否包含下级办事处
	private long acctConditionType = -1;//账户条件类型：全部；条件；指定
	private long[] accountIDs = null;//账户条件类型为指定时的账户ID
	private long rdStatus = -1;
	private Collection acctFieldInfos = null;//账户条件类型为条件时的账户条件集合 内容是AcctFieldInfo
	
	public long getOperationType()
	{
		return operationType;
	}

	public void setOperationType(long operationType)
	{
		this.operationType = operationType;
		putUsedField("operationtype", this.operationType);
	}

	public long getUserGroupID()
	{
		return userGroupID;
	}

	public void setUserGroupID(long userGroupID)
	{
		this.userGroupID = userGroupID;
		putUsedField("usergroupid", this.userGroupID);
	}

	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
		putUsedField("id", this.id);
	}

	public long[] getAccountIDs()
	{
		return accountIDs;
	}

	public void setAccountIDs(long[] accountIDs)
	{
		this.accountIDs = accountIDs;
	}

	public long getAcctConditionType()
	{
		return acctConditionType;
	}

	public void setAcctConditionType(long acctConditionType)
	{
		this.acctConditionType = acctConditionType;
		putUsedField("acctconditiontype", this.acctConditionType);
	}	

	public long getIsContainSub()
	{
		return isContainSub;
	}

	public void setIsContainSub(long isContainSub)
	{
		this.isContainSub = isContainSub;
		putUsedField("iscontainsub", this.isContainSub);
	}

	public Collection getAcctFieldInfos()
	{
		return acctFieldInfos;
	}

	public void setAcctFieldInfos(Collection acctFieldInfos)
	{
		this.acctFieldInfos = acctFieldInfos;
	}

	public long getRdStatus()
	{
		return rdStatus;
	}

	public void setRdStatus(long rdStatus)
	{
		this.rdStatus = rdStatus;
		putUsedField("rdstatus", this.rdStatus);
	}	
}
