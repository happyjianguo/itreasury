
package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Timestamp;

/**
 * 历史数据导入任务设置
 * 
 * @author pepsi To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ImportBankAccountHisDataSettingInfo
{
	private long id = -1; // 日志ID
	private long bankType = -1; // 导入指定银行
	private Timestamp executeTime = null; // 指定执行时间
	private long taskStatus = -1; // 任务状态
	private long status = -1; // 记录状态
	private Timestamp modifyTime = null; // 记录修改时间
	

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
