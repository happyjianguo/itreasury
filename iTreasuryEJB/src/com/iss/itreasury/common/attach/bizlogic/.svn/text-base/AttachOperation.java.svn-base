/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.common.attach.bizlogic;

import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.common.attach.dao.*;
import com.iss.itreasury.common.attach.dataentity.*;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachOperation
{
	private static Log4j log4j = null;

	public AttachOperation()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
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
	* 得到客户中心文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID在这里对应center_docinfo里的clientid
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection queryclientcenter(long lAttachTypeID, long lParentID) throws Exception
	{
		Collection cResult = null;
		AttachDao attachDao = new AttachDao();

		try
		{
			cResult = attachDao.queryclientcenter(lAttachTypeID,lParentID);
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
	* 逻辑删除文件信息
	* Create Date: 2003-10-15
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteDoc(long lID) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.deleteDoc(lID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 逻辑删除文件信息
	* Create Date: 2003-10-15
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteOBDoc(long lID) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.deleteOBDoc(lID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 逻辑删除客户中心文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteclientcenter(long lID) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.deleteclientcenter(lID);
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
	/**
	* 增加文件信息
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insertDoc(AttachInfo info) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.insertDoc(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 增加文件信息(网银)
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insertOBDoc(AttachInfo info) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.insertOBDoc(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 增加客户中心文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long addclientcenter(AttachInfo info) throws Exception
	{
		long lResult = -1;
		AttachDao attachDao = new AttachDao();

		try
		{
			lResult = attachDao.insertclientcenter(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lResult;
	}
	/**
	* 得到文件信息
	* Create Date: 2007-04-24
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection findByCondition(AttachInfo info) throws Exception
	{
		Collection cResult = null;
		AttachDao attachDao = new AttachDao();

		try
		{
			cResult = attachDao.findByCondition(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return cResult;
	}
	/**
	* 得到文件信息(网银)
	* Create Date: 2007-04-24
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection findByOBCondition(AttachInfo info) throws Exception
	{
		Collection cResult = null;
		AttachDao attachDao = new AttachDao();

		try
		{
			cResult = attachDao.findByOBCondition(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return cResult;
	}
	/**通过临时交易号,把正常的交易号与上传的文件做关联
	 * 
	 * @param tempTransCode 临时交易号
	 * @param transCode交易号
	 * @throws Exception
	 */
	public void updateTransCode(String tempTransCode,String transCode) throws Exception
	{
		
		AttachDao attachDao = new AttachDao();

		try
		{
			 attachDao.updateTransCode(tempTransCode,transCode);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		
	}
	
	/**通过临时交易号,把正常的交易号与上传的文件做关联(网银)
	 * 
	 * @param tempTransCode 临时交易号
	 * @param transCode交易号
	 * @throws Exception
	 */
	public void updateOBTransCode(String tempTransCode,String transCode) throws Exception
	{
		
		AttachDao attachDao = new AttachDao();

		try
		{
			 attachDao.updateOBTransCode(tempTransCode,transCode);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		
	}
	
}
