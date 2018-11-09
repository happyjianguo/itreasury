/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.clientcenter.attach.bizlogic;

import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.clientcenter.attach.dao.*;
import com.iss.itreasury.clientcenter.attach.dataentity.*;

/**
 * @author rxie
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachOperation
{
	private static Log4j log4j = null;

	public AttachOperation()
	{
		log4j = new Log4j(Constant.ModuleType.CLIENTCENTER, this);
	}

	/**
	* 得到文件信息
	* Create Date: 2003-10-15
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection query(long lAttachTypeID, long lParentID) throws Exception
	{
		Collection cResult = null;
		AttachDao attachDao = new AttachDao();

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
	* 逻辑删除文件信息
	* Create Date: 2003-10-15
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long delete(long lID) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

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
	* 增加文件信息
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long add(AttachInfo info) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

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
