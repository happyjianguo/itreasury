
package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Timestamp;

/**
 * ��ʷ���ݵ�����������
 * 
 * @author pepsi To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ImportBankAccountHisDataSettingInfo
{
	private long id = -1; // ��־ID
	private long bankType = -1; // ����ָ������
	private Timestamp executeTime = null; // ָ��ִ��ʱ��
	private long taskStatus = -1; // ����״̬
	private long status = -1; // ��¼״̬
	private Timestamp modifyTime = null; // ��¼�޸�ʱ��
	

	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return
	 */
	public long getBankType()
	{
		return bankType;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteTime()
	{
		return executeTime;
	}

	/**
	 * @return
	 */
	public Timestamp getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * @return
	 */
	public long getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public long getTaskStatus()
	{
		return taskStatus;
	}

	/**
	 * @param l
	 */
	public void setBankType(long l)
	{
		bankType = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteTime(Timestamp timestamp)
	{
		executeTime = timestamp;
	}

	/**
	 * @param l
	 */
	public void setModifyTime(Timestamp l)
	{
		modifyTime = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l)
	{
		status = l;
	}

	/**
	 * @param l
	 */
	public void setTaskStatus(long l)
	{
		taskStatus = l;
	}

}
