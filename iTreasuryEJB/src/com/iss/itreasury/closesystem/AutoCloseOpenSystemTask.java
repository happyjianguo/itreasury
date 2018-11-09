/*
 * Created on 2005-1-31
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.closesystem;

import java.sql.Timestamp;

import com.iss.itreasury.bs.util.timer.Task;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**����ϵͳ�Զ����ػ��Զ�����
 * @author mxzhou
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AutoCloseOpenSystemTask extends Task
{
	/**
	 * ��־
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ��ǰ�Զ������Ӧ�Ŀ����кͱ���
	 */
	private long officeID = -1;
	private long currencyID = -1;
	
	/**
	 * ���췽��
	 * @param officeID
	 * @param currencyID
	 */
	public AutoCloseOpenSystemTask(long officeID, long currencyID)
	{
		this.officeID = officeID;
		this.currencyID = currencyID;
	}

	/**
	 * �Զ�����ִ�з���
	 */
	public void run() throws Exception
	{
		//ִ�йػ�����
		//�ػ�ǰ���жϵ�ǰ�����պ���Ȼʱ���Ƿ�һ�£������һ�£������ػ�����
		Timestamp systemDate = null;//��Ȼʱ��
		Timestamp openDate = null;//����ϵͳ�Ŀ���ʱ��
		
		//��ȡ��ǰ����ϵͳ�Ŀ���ʱ��
		try
		{
			systemDate = Env.getSystemDateTime();
			openDate = Env.getSystemDate(this.officeID, this.currencyID);
			if(systemDate == null || openDate == null)
			{
				throw new Exception();
			}
			Log.print("��ǰϵͳʱ�䣺"+systemDate+" ����ϵͳ�Ŀ���ʱ��:"+openDate);
		}
		catch(Exception e)
		{
			Log.print("��ȡ��ǰϵͳʱ��ͽ���ϵͳ�Ŀ���ʱ��ʱ�����쳣���޷�ִ�йػ�����");
			e.printStackTrace();
			return;
		}
		//�Ƚ���ʱ���Ƿ����
		if(IDate.compareDate(systemDate, openDate) != 0)
		{
			Log.print("��ǰϵͳʱ��ͽ���ϵͳ�Ŀ���ʱ�䲻һ�£��޷�ִ�йػ�����");
			return;
		}
		//ִ�йػ�����
		DealThread dealThread = new DealThread();
		boolean closeSuccess = false;
		try
		{
			closeSuccess = dealThread.closeSystemPublic(
					this.officeID, 
					this.currencyID, 
					Constant.ModuleType.SETTLEMENT,
					openDate,
					true);
		}
		catch(Throwable e)
		{
			Log.print("ִ�йػ�����ʱ�����쳣");
			e.printStackTrace();
			return;
		}
		
		//�ػ��ɹ�ʱ����ִ�п�������
		if(!closeSuccess)
		{
			Log.print("�ػ�����ִ��ʧ�ܣ��޷�ִ�п�������");
			return;
		}
		Log.print("�ػ�����ִ�гɹ���ִ�п�������");
		
		boolean openSuccess = false;
		try
		{
			openSuccess = dealThread.openSystemPublic(
					this.officeID, 
					this.currencyID, 
					Constant.ModuleType.SETTLEMENT,
					DataFormat.getNextDate(openDate));
		}
		catch(Throwable e)
		{
			Log.print("ִ�п�������ʱ�����쳣");
			e.printStackTrace();
			return;
		}
		
		if(!openSuccess)
		{
			Log.print("��������ִ��ʧ��");
			return;
		}
		Log.print("��������ִ�гɹ�");
	}
}
