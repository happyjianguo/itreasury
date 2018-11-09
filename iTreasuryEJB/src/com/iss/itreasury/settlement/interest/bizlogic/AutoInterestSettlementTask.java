package com.iss.itreasury.settlement.interest.bizlogic;

import com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Task;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AutoInterestSettlementTask extends Task
{
	//对应的设置记录的id
	private long m_lSettingRecordID = -1;

	/**
	 * Constructor for AutoInterestSettlementTask.
	 */
	public AutoInterestSettlementTask(long lID)
	{
		super();
		super.m_strName = "AutoInterestSettlementTask[SettingID:"+lID+"]";
		this.m_lSettingRecordID = lID;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		logger.info("开始执行自动结息任务" + this.m_strName);

		boolean bFlag = false;

		InterestFeeSetting interestFeeSetting = new InterestFeeSetting();

		//按照当前对象的id查找对应设置记录
		InterestFeeSettingInfo settingInfo = null;
		try
		{
			settingInfo = interestFeeSetting.findByID(this.m_lSettingRecordID);
		}
		catch (Exception e)
		{
			logger.error("没有找到自动结息的设置记录！");
			e.printStackTrace();
		}

		//校验设置是否可执行
		if (settingInfo != null)
		{
			if (settingInfo.getStatusID() == SETTConstant.TransactionStatus.SAVE)
			{
				bFlag = true;
			}
			else
			{
				bFlag = false;
				logger.error("自动结息的设置记录（ID:" + this.m_lSettingRecordID + "）已经无效！");
			}
			if (settingInfo.getIsExecute() == SETTConstant.BooleanValue.ISFALSE)
			{
				bFlag &= true;
			}
			else
			{
				bFlag = false;
				logger.error("自动结息的设置记录（ID:" + this.m_lSettingRecordID + "）已执行过");
			}
			if (this.getDate().compareTo(settingInfo.getCalculateDate()) == 0)
			{
				bFlag &= true;
			}
			else
			{
				bFlag = false;
				logger.error("自动结息的设置记录（ID:" + this.m_lSettingRecordID + "）的计算时间已修改！此任务不再执行");
			}

		}

		//执行自动结息操作
		if (bFlag)
		{
			try
			{
				//另起一个事务
				interestFeeSetting = new InterestFeeSetting();
				
				interestFeeSetting.execute(settingInfo);
			}
			catch (IException e)
			{
				logger.error("执行失败");
				e.printStackTrace();
			}
		}

		logger.info("结束自动结息任务");
	}

	/**
	 * Returns the lSettingRecordID.
	 * @return long
	 */
	public long getSettingRecordID()
	{
		return m_lSettingRecordID;
	}

}
