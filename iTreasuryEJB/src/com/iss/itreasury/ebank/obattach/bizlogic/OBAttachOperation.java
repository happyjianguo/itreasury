/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obattach.bizlogic;

import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obattach.dao.*;
import com.iss.itreasury.ebank.obattach.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBAttachOperation
{

	private static Log4j log4j = null;

	public OBAttachOperation()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	* �õ��ļ���Ϣ
	* Create Date: 2003-10-15
	* @param lAttachTypeID �ϴ����ĵ�����
	* @param lParentID �ĵ��������������ID
	* @return Collection �ļ���Ϣ
	* @exception Exception
	*/
	public Collection query(long lAttachTypeID, long lParentID) throws Exception
	{
		Collection cResult = null;
		OBAttachDao attachDao = new OBAttachDao();

		try
		{
			cResult = attachDao.query(lAttachTypeID, lParentID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}

		return cResult;
	}

	/**
	* �߼�ɾ���ļ���Ϣ
	* Create Date: 2003-10-15
	* @param lID �ĵ�ID
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long delete(long lID) throws Exception
	{
		long lResult = -1;
		OBAttachDao attachDao = new OBAttachDao();

		try
		{
			lResult = attachDao.delete(lID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	* �����ļ���Ϣ
	* Create Date: 2003-10-15
	* @param AttachInfo �ĵ���Ϣ
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long add(OBAttachInfo info) throws Exception
	{
		long lResult = -1;
		OBAttachDao attachDao = new OBAttachDao();

		try
		{
			lResult = attachDao.insert(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
}
