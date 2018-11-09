/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.assistant.dataentity.AssistantInfo;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLEntryDefinitionTempDAO;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GLSubjectDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.mergedVoucher.bizlogic.Sett_mergedVouchersBiz;
import com.iss.itreasury.settlement.mergedVoucher.dao.Sett_mergedVouchersDAO;
import com.iss.itreasury.settlement.mergedVoucher.dataentity.MergedVoucherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GeneralLedgerEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	final static long serialVersionUID = 3206093459760846163L;
	
	private final static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。 
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}
	/**
	 * 新增总账定义记录
	 * @param info GLSubjectDefinitionInfo
	 * @return 总账定义记录id
	 * @throws java.rmi.RemoteException
	 * @throws IRollbackException
	 */
	public long addGLSubjectDefinition(GLSubjectDefinitionInfo info) throws java.rmi.RemoteException, IRollbackException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			/**
			 * 合法性检查
			 */
			this.validatateSubjectDefinitionInfo(dao, info, true);
			long id = dao.add(info);
			return id;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 修改科目总表记录
	 * @param info 科目总表记录
	 * @return 科目总表记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long updateGLSubjectDefinition(GLSubjectDefinitionInfo info) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			/**
			 * 合法性检查
			 */
			this.validatateSubjectDefinitionInfo(dao, info, false);
			long id = dao.update(info);
			return id;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 通过标识查询
	 * @param id long
	 * @return 科目总表记录
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			GLSubjectDefinitionInfo info = dao.findByID(id);
			return info;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 查询所有科目总表记录
	 * @param includeDisable true-包括无效，false-不包括无效
	 * @param officeID 机构ID 如果是-1忽略此条件
	 * @param CurrencyID 币种 如果是-1忽略此条件
	 * @param subjectType 科目属性 如果是-1忽略此条件
	 * @return 所有科目总表记录集合
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection findAllGLSubjectDefinition(boolean includeDisable, long officeID, long CurrencyID, long subjectType) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			Collection collection = dao.findAll(includeDisable, officeID, CurrencyID, subjectType);
			return collection;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByCode(String subjectCode)
        throws IRollbackException, RemoteException
    {
        GLSubjectDefinitionInfo  obj = null;
        return obj;
        
    }
        
	/**
	 * 修改科目总表记录状态
	 * @param id 科目总表记录id
	 * @param status 科目总表记录状态
	 * @return 科目总表记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long updateGLSubjectDefinitionStatus(long id, long status) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			if (dao.hasSonSubject(id))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E108");
			}
			long returnId;
			try
			{
				returnId = dao.updateStatus(id, status);
			}
			catch (SQLException e)
			{
				throw new IRollbackException(mySessionCtx, "SQLException in GeneralLedgerEJB", e);
			}
			return returnId;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 新增分录设置定义记录
	 * @param info GLEntryDefinitionInfo
	 * @return分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long addGLEntryDefinition(GLEntryDefinitionInfo info) throws IRollbackException, RemoteException
	{
		try
		{
			validateEntryDefinitionInfo(info);
			Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
			long returnId = dao.add(info);
			return returnId;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 更新分录设置定义记录
	 * @param info GLEntryDefinitionInfo
	 * @return 分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long updateGLEntryDefinition(GLEntryDefinitionInfo info) throws IRollbackException, RemoteException
	{
		try
		{
			validateEntryDefinitionInfo(info);
			Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
			long returnId = dao.update(info);
			return returnId;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 更新分录设置定义记录
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return 分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long updateGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException
	{
		synchronized(lockObj){
			long returnId = -1;
			GLEntryDefinitionTempInfo uncheckTempInfo = null;
			try
			{
				validateEntryDefinitionTempInfo(tempInfo);
				
				
				Sett_GLEntryDefinitionTempDAO dao = new Sett_GLEntryDefinitionTempDAO();
				
				if(tempInfo.getID()>0){
					
					uncheckTempInfo = dao.findByTempID(tempInfo.getID());
					if(uncheckTempInfo == null){
						
						throw new RemoteException("该业务类型的会计分录设置已经被撤销");
					}
					else if(uncheckTempInfo!=null && uncheckTempInfo.getNStatusID() == SETTConstant.GeneralLedgerStatus.CHECK){
						
						throw new RemoteException("该业务类型的会计分录设置已经被复核");
					}
					
					if(tempInfo.getNID()>0){
						tempInfo.setNOperateType(SETTConstant.GeneralLedgerOperationType.UPDATE);
					}else{
						tempInfo.setNOperateType(SETTConstant.GeneralLedgerOperationType.ADD);
					}
					
					returnId = dao.updateTemp(tempInfo);
				}else{
					
					if(tempInfo.getNID()>0){
						
						tempInfo.setNOperateType(SETTConstant.GeneralLedgerOperationType.UPDATE);
						
						uncheckTempInfo = dao.findUncheckGLEntryDefinitionByTransType(tempInfo.getTransactionType(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
						
						if(uncheckTempInfo == null ){
							
							dao.delUncheckTempInfoByNID(tempInfo.getNID(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
							returnId = dao.addTemp(tempInfo);
							
						}else if(uncheckTempInfo !=null && uncheckTempInfo.getInputUserID() == tempInfo.getInputUserID()){
							
							dao.delUncheckTempInfoByNID(tempInfo.getNID(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
							returnId = dao.addTemp(tempInfo);
							
						}else{
							
							throw new RemoteException("该业务类型的会计分录设置已经被其他人新增或修改");
						}
						
					}else{
						
						throw new RemoteException("修改会计分录设置异常");
					}
				
				}
				
				return returnId;
				
			}
			catch (Exception ex)
			{
				throw new IRollbackException(mySessionCtx, ex.getMessage());
			}
		}
	}
	/**
	 * 通过标识查询
	 * @param id long
	 * @return GLEntryDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
			GLEntryDefinitionInfo info = dao.findByID(id);
			return info;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 通过标识查询
	 * @param id long
	 * @return GLEntryDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public GLEntryDefinitionTempInfo findGLEntryDefinitionTempInfoByID(long id) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
			GLEntryDefinitionTempInfo tempInfo = tempDao.findByTempID(id);
			return tempInfo;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 查询所有分录设置定义表记录
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有分录设置定义表记录集合
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection findAllGLEntryDefinition(long officeID, long currencyID, long orderType) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
			Collection collection = dao.findAll(officeID, currencyID, orderType);
			return collection;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 查询所有未复核和已生效分录设置定义表记录
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有未复核和已生效分录设置定义表记录
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection findAllUncheckAndUsedGLEntryDefinition(long officeID, long currencyID, long orderType) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
			Collection collection = tempDao.findAllUncheckAndUsedGLEntryDefinition(officeID, currencyID, orderType);
			return collection;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 查询所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection findAllUnUseAndUsedGLEntryDefinition(long officeID, long currencyID, long orderType) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
			Collection collection = tempDao.findAllUnUseAndUsedGLEntryDefinition(officeID, currencyID, orderType);
			return collection;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 物理删除会计分录定义
	 * @param id 会计分录定义id
	 * @return 会计分录定义id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long deleteGLEntryDefinition(long id) throws IRollbackException, RemoteException
	{
		try
		{
			long returnValue = -1;
			Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
			dao.deletePhysically(id);
			return returnValue;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 物理删除会计分录定义
	 * @param id 会计分录定义id
	 * @return 会计分录定义id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long deleteGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException
	{
		synchronized(lockObj){
			GLEntryDefinitionTempInfo uncheckTempInfo = null;
			try
			{
				long returnValue = -1;
				Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
				Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
				
				if(tempInfo.getID()>0){
					
					uncheckTempInfo = tempDao.findByTempID(tempInfo.getID());
					
					if(uncheckTempInfo.getNStatusID()== SETTConstant.GeneralLedgerStatus.CHECK){
						
						throw new RemoteException("该会计分录设置已复核，不能撤销");
					}
					
					tempDao.deletePhysically(tempInfo.getID());
					returnValue = tempInfo.getID();
					
				}else{
					
					if(tempInfo.getNID()>0){
						uncheckTempInfo = tempDao.findUncheckGLEntryDefinitionByTransType(tempInfo.getTransactionType(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
						
						if(uncheckTempInfo == null ){
							
							tempDao.delUncheckTempInfoByNID(tempInfo.getNID(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
							
						}else if(uncheckTempInfo !=null && uncheckTempInfo.getInputUserID() == tempInfo.getInputUserID()){
							
							tempDao.delUncheckTempInfoByNID(tempInfo.getNID(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
							
						}else{
							
							throw new RemoteException("该业务类型的会计分录设置已经被其他人新增或修改");
						}
						
						GLEntryDefinitionInfo info = dao.findByID(tempInfo.getNID());
						
						tempInfo = switchInfoToGLEntryDefinitionTempInfo(info,tempInfo);
						tempInfo.setNOperateType(SETTConstant.GeneralLedgerOperationType.DELETE);
						
						returnValue = tempDao.addTemp(tempInfo);
					}else{
						
						throw new RemoteException("删除会计分录设置异常");
					}
				}
				return returnValue;
			}
			catch (Exception ex)
			{
				throw new IRollbackException(mySessionCtx, ex.getMessage());
			}
		}
	}
	/**
	 * 合法性检查
	 * @param dao Sett_GLSubjectDefinitionDAO
	 * @param info GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 */
	private void validatateSubjectDefinitionInfo(Sett_GLSubjectDefinitionDAO dao, GLSubjectDefinitionInfo info, boolean isNew) throws IRollbackException
	{
		try
		{
			this.validateSubjectDefinitionInternal(info);
			this.validateSubjectDefinitionExternal(dao, info, isNew);
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 自身内部校验：<br>
	 * 科目代码不能为空；科目名称不能为空；“上级科目”为空，则“是否总账科目”必须为是；
	 * @param GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 */
	private void validateSubjectDefinitionInternal(GLSubjectDefinitionInfo info) throws IRollbackException
	{
		//科目代码不能为空
		if (info.getSegmentCode2() == null || info.getSegmentCode2().length() == 0)
		{
			throw new IRollbackException(mySessionCtx, "Sett_E102");
		}
		//科目名称不能为空
		if (info.getSegmentName2() == null || info.getSegmentName2().length() == 0)
		{
			throw new IRollbackException(mySessionCtx, "Sett_E103");
		}
		//“上级科目”为空，则“是否总账科目”必须为是
		if (info.getParentSubjectID() == -1 && !info.isRoot())
		{
			throw new IRollbackException(mySessionCtx, "Sett_E104");
		}
		//科目不能既是总账科目，又有上级科目
		if (info.getParentSubjectID() != -1 && info.isRoot())
		{
			throw new IRollbackException(mySessionCtx, "Sett_E109");
		}
		//科目属性不能为空
		if (info.getSubjectType() == -1)
		{
			throw new IRollbackException(mySessionCtx, "Sett_E110");
		}
		if (info.getParentSubjectID() != -1 && info.getParentSubjectID() == info.getID())
		{
			throw new IRollbackException(mySessionCtx, "Sett_E114"); //科目的上级科目不能是自身
		}
	}
	/**
	 * 关联校验 <br>
	 * 校验上级科目是否末级科目，上级科目和本科目属性是否一致<br>
	 * 校验科目代码是否存在<br>
	 * 校验科目名称是否存在<br>
	 * @param dao Sett_GLSubjectDefinitionDAO
	 * @param info GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 */
	private void validateSubjectDefinitionExternal(Sett_GLSubjectDefinitionDAO dao, GLSubjectDefinitionInfo info, boolean isNew) throws IRollbackException
	{
		try
		{
			//校验上级科目是否末级科目，上级科目和本科目属性是否一致
			aboutParentSubjectValidate(dao, info);
			//判断科目代码是否存在	
			if (dao.SegmentCode2IsExist(info))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E100");
			}
			//判断科目名称是否存在
			if (dao.subjectNameIsExist(info))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E101");
			}
			if (!isNew && dao.hasSonSubject(info.getID()))
			{
				if (info.isLeaf())
				{
					throw new IRollbackException(mySessionCtx, "Sett_E107");
				}
				GLSubjectDefinitionInfo oldInfo = dao.findByID(info.getID());
				if (oldInfo.getSubjectType() != info.getSubjectType())
				{
					throw new IRollbackException(mySessionCtx, "Sett_E111");
				}
				//科目含有下级科目，不能更改该科目的上级科目
				if (!oldInfo.getSegmentCode2().equals(info.getSegmentCode2()))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E151");
				}
			}
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 对会计分录设置信息的校验 
	 * @param info GLEntryDefinitionInfo
	 * @throws IRollbackException
	 */
	private void validateEntryDefinitionInfo(GLEntryDefinitionInfo info) throws IRollbackException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			if (info.getSubjectType() == SETTConstant.EntrySubjectType.SUBJECT_TYPE_99)
			{
				if (dao.findByCode(info.getSubjectCode()) == null)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E112");
				}
			}
			else
			{
				if (info.getSubjectCode() != null && info.getSubjectCode().length() > 0)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E113");
				}
			}
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 对会计分录设置信息的校验 
	 * @param info GLEntryDefinitionInfo
	 * @throws IRollbackException
	 */
	private void validateEntryDefinitionTempInfo(GLEntryDefinitionTempInfo info) throws IRollbackException
	{
		try
		{
			Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
			if (info.getSubjectType() == SETTConstant.EntrySubjectType.SUBJECT_TYPE_99)
			{
				if (dao.findByCode(info.getSubjectCode()) == null)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E112");
				}
			}
			else
			{
				if (info.getSubjectCode() != null && info.getSubjectCode().length() > 0)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E113");
				}
			}
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	/**
	 * 校验科目的上级科目合法性
	 * @param dao Sett_GLSubjectDefinitionDAO
	 * @param info GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 */
	private void aboutParentSubjectValidate(Sett_GLSubjectDefinitionDAO dao, GLSubjectDefinitionInfo info) throws IRollbackException
	{
		long parentId = info.getParentSubjectID();
		if (parentId != -1)
		{
			GLSubjectDefinitionInfo parentInfo = null;
			try
			{
				parentInfo = dao.findByID(parentId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(this.mySessionCtx, e.getMessage());
			}
			//上级科目是否末级科目
			if (parentInfo.isLeaf())
			{
				throw new IRollbackException(this.mySessionCtx, "Sett_E105");
			}
			//上级科目和本科目属性是否一致
			if (parentInfo.getSubjectType() != info.getSubjectType())
			{
				throw new IRollbackException(this.mySessionCtx, "Sett_E106");
			}
			//科目的上级科目不能是自身
			if (parentInfo.getSegmentCode2().equals(info.getSegmentCode2()))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E114"); //科目的上级科目不能是自身
			}
		}
	}
	/**
	 * 交易产生会计分录
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param) throws IRollbackException, RemoteException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean();
		try {
			return glBean.generateGLEntry(param);
		} catch (IException e) {
			throw new IRollbackException(mySessionCtx,e);
		} 
//		try
//		{
//			log.info(param.toString());
//			//获取参数
//			//TransInfo transInfo = param.getTransInfo();
//			long lPrincipalType = param.getPrincipalType();
//			long lInterestType = param.getInterestType();
//			long lCommisionType = param.getCommisionType();
//			long lEntryType = param.getEntryType();
//			String sMultiCode = param.getMultiCode();
//			long receiveAccountID = param.getReceiveAccountID(); //收款方账户ID，可空
//			long payAccountID = param.getPayAccountID(); //付款方账户ID，可空  
//			long receiveInterestAccountID = param.getReceiveInterestAccountID();
//			//收息账户ID，可空
//			long payInterestAccountID = param.getPayInterestAccountID();
//			//付息账户ID，可空   
//			long vouchReceiveAccountID = param.getVouchReceiveAccountID();
//			//委托收款方账户ID，可空
//			long vouchPayAccountID = param.getVouchPayAccountID();
//			//委托付款方账户ID，可空
//			long receieveSuertyFeeAccountID = param.getReceieveSuertyFeeAccountID();
//			//收担保费账户ID，可空
//			long paySuertyFeeAccountID = param.getPaySuertyFeeAccountID();
//			//付担保费账户ID，可空
//			long payCommissionAccountID = param.getPayCommissionAccountID();
//			//付手续费账户ID，可空
//			long principalBankID = param.getPrincipalBankID(); //本金开户行ID，可空
//			long interestBankID = param.getInterestBankID(); //本金开户行ID，可空
//			long feeBankID = param.getFeeBankID(); //费用开户行ID，可空
//			double principalOrTransAmount = param.getPrincipalOrTransAmount();
//			//本金/交易金额
//			double totalInterest = param.getTotalInterest(); //利息合计，可空
//			double preDrawInterest = param.getPreDrawInterest(); //计提利息，可空
//			double unPreDrawInterest = param.getUnPreDrawInterest(); //未计提利息，可空
//			double overTimeInterest = param.getOverTimeInterest(); //逾期利息，可空
//			double overFee = param.getOverFee(); //罚息，可空	
//			double compoundInterest = param.getCompoundInterest(); //复利，可空
//			double suretyFee = param.getSuretyFee(); //担保费，可空
//			double commissionFee = param.getCommissionFee(); //手续费，可空
//			double interestTaxFee = param.getInterestTaxFee(); //利息税费，可空
//			double totalPrincipalAndInterest = param.getTotalPrincipalAndInterest();
//			//本息合计，可空	
//			double remissionInterest = param.getRemissionInterest(); //豁免利息，可空
//			double reallyReceiveInterest = param.getReallyReceiveInterest();
//			//实收利息，可空     	 	
//			log.debug("-----------开始产生会计分录-----------");
//			//生成会计分录定义DAO
//			Sett_GLEntryDefinitionDAO gLEntryDefinitionDAO = new Sett_GLEntryDefinitionDAO();
//			log.debug("----------根据交易类型，分录类型查询会计分录定义--------------");
//			ArrayList gLEntryDefinitions = null;
//			try
//			{
//				gLEntryDefinitions = (ArrayList) gLEntryDefinitionDAO.findAllByTransactionTypeIDAndEntryType(param.getTransactionTypeID(), lEntryType);
//			}
//			catch (SQLException e)
//			{
//				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
//			}
//			log.debug("-----查询到" + gLEntryDefinitions.size() + "个会计分录定义----------");
//			if (gLEntryDefinitions != null && gLEntryDefinitions.size() > 0)
//			{
//				for (int i = 0; i < gLEntryDefinitions.size(); i++)
//				{
//					GLEntryDefinitionInfo tmp = (GLEntryDefinitionInfo) gLEntryDefinitions.get(i);
//					log.debug("--------会计分录定义" + i + "是:------------");
//					log.debug(UtilOperation.dataentityToString(tmp));
//					//资金流向是"无关",不做以下匹配 
//					if (tmp.getCapitalType() != SETTConstant.CapitalType.IRRESPECTIVE)
//					{
//						log.debug("--------资金流向不是无关--------");
//						//如果金额类型是"本金/交易金额" 比较"资金流向"是否相同			
//						if ((tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11) && lPrincipalType != tmp.getCapitalType())
//						{
//							log.debug("金额类型是本金/交易金额  本息合计 资金流向不同，匹配下一个会计分录定义");
//							continue;
//						}
//						//如果金额类型是"利息合计" "计提利息" "未计提利息" "逾期利息" "罚息" "复利" ，比较利息类型是否相同
//						if ((tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_13) && lInterestType != tmp.getCapitalType())
//						{
//							log.debug("金额类型是利息合计 计提利息 未计提利息 逾期利息　罚息 复利, 实收利息 利息类型不同，匹配下一个会计分录定义");
//							continue;
//						}
//						//如果金额类型是"担保费" "手续费"，比较"费用流向"是否相同
//						if ((tmp.getAmountType() == 8 || tmp.getAmountType() == 9) && lCommisionType != tmp.getCapitalType())
//						{
//							log.debug("金额类型是担保费 手续费, 费用流向，匹配下一个会计分录定义");
//							continue;
//						}
//					}
/////////////////////
//					double dAmount = 0.0;
//					log.debug("---------金额类型是: " + tmp.getAmountType() + "------------");
//					switch ((int) tmp.getAmountType())
//					{
//						case SETTConstant.AmountType.AmountType_1 :
//							{
//								dAmount = principalOrTransAmount;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_2 :
//							{
//								dAmount = totalInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_3 :
//							{
//								dAmount = preDrawInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_4 :
//							{
//								dAmount = unPreDrawInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_5 :
//							{
//								dAmount = overTimeInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_6 :
//							{
//								dAmount = overFee;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_7 :
//							{
//								dAmount = compoundInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_8 :
//							{
//								dAmount = suretyFee;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_9 :
//							{
//								dAmount = commissionFee;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_10 :
//							{
//								dAmount = interestTaxFee;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_11 :
//							{
//								dAmount = totalPrincipalAndInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_12 :
//							{
//								dAmount = remissionInterest;
//							}
//							break;
//						case SETTConstant.AmountType.AmountType_13 :
//							{
//								dAmount = reallyReceiveInterest;
//							}
//							break;
//						default :
//							return false;
//					}
//					log.debug("---------金额是: " + dAmount + "------------");
//					if (dAmount == 0.0)
//						continue;
//						
//					int subjectType = (int) tmp.getSubjectType();
//					log.debug("-----会计分录定义的科目号是：" + subjectType + "--------");
//					//根据科目类型、账户ID获得科目号
//					String strSubject = null;
//					//生成账户子系统接口类
//					log.debug("--------根据子账户ID查询对应科目号------------");
//					log.debug("--------科目类型是：" + subjectType + "-----------");
//					AccountOperation accountOperation = new AccountOperation();
//					switch (subjectType)
//					{
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_1 :
//							{
//								log.debug("科目类型1账户接口:"+accountOperation);
//								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_2 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//								log.info("-------付款方科目名称是：" + strSubject + "-------");
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_3 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(receiveInterestAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_4 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payInterestAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_5 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(vouchReceiveAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_6 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(vouchPayAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_7 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(receieveSuertyFeeAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_8 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(paySuertyFeeAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_9 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payCommissionAccountID,AccountOperation.SUBJECT_TYPE_ACCOUNT);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_10 :
//							{
//								if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_1 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_11)
//								{
//									strSubject = accountOperation.getSubjectByBankID(principalBankID);
//								}
//								else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_2 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_3 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_4 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_5 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_6 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_7|| tmp.getAmountType() == SETTConstant.AmountType.AmountType_13)
//									strSubject = accountOperation.getSubjectByBankID(interestBankID);
//								else if (tmp.getAmountType() == SETTConstant.AmountType.AmountType_8 || tmp.getAmountType() == SETTConstant.AmountType.AmountType_9)
//									strSubject = accountOperation.getSubjectByBankID(feeBankID);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_11 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_12 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_13 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_14 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_INTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_15 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(receiveAccountID,AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_16 :
//							{
//								strSubject = accountOperation.getSubjectBySubAccountID(payAccountID,AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
//							}
//							break;
//						case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_99 :
//							{
//								strSubject = tmp.getSubjectCode();
//							}
//							break;
//						default :
//							return false;
//					}
//					//无法确定合适的会计科目
//					if (strSubject == null)
//					{
//						log.debug("---------无法获得合适的会计科目-------------");
//						//continue;
//						throw new IRollbackException(mySessionCtx, "无法获得合适的会计科目,交易失败");
//					}
//					log.debug("-----------科目号是" + strSubject + "-----------");
//					/////////////////////////////						
//					////////////////////////////
//					//借贷方向 －－金额方向 2是红字
//					if (tmp.getAmountDirection() == SETTConstant.AmountDirection.RED)
//						dAmount = (-1) * dAmount;
//					//获取借贷方向
//					long lDirection = tmp.getDirection();
//					GLEntryInfo gLEntryInfo = new GLEntryInfo();
//					// set info fields
//					//保留
//					long Group;
//					long Type;
//					gLEntryInfo.setOfficeID(param.getOfficeID());
//					gLEntryInfo.setCurrencyID(param.getCurrencyID());
//					gLEntryInfo.setSubjectCode(strSubject);
//					gLEntryInfo.setTransNo(param.getTransNo());
//					gLEntryInfo.setTransactionTypeID(param.getTransactionTypeID());
//					gLEntryInfo.setTransDirection(tmp.getDirection());
//					gLEntryInfo.setAmount(dAmount);
//					gLEntryInfo.setExecute(param.getExecuteDate());
//					gLEntryInfo.setInterestStart(param.getInterestStartDate());
//					gLEntryInfo.setAbstract(param.getAbstractStr());
//					gLEntryInfo.setMultiCode(sMultiCode);
//					gLEntryInfo.setInputUserID(param.getInputUserID());
//					gLEntryInfo.setCheckUserID(param.getCheckUserID());
//					//设为已复核
//					gLEntryInfo.setStatusID(3);
//					sett_GLEntryDAO gLEntryDAO = new sett_GLEntryDAO();
//					log.debug("---------产生会计分录到会计分录表------------");
//					try
//					{
//						gLEntryDAO.add(gLEntryInfo);
//					}
//					catch (SQLException e)
//					{
//						throw new IRollbackException(mySessionCtx, e.getMessage(), e);
//					}
//					log.debug("第" + i + "笔会计分录产生完成");
//				} //for end
//				//检查本交易号产生的分录是否借贷平衡
//				log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
//				boolean checkRes = this.checkTransDCBalance(param.getTransNo());
//				if (!checkRes)
//				{
//					log.debug("-----借贷平衡不平衡，分录产生失败-------");
//					throw new IRollbackException(mySessionCtx, "借贷平衡不平衡，分录产生失败");
//				}
//			}
//			else
//			{
//				throw new IRollbackException(mySessionCtx, "无法找到对应的会计分录定义，交易失败");
//			}
//			return true;
//		}
//		catch (Exception ex)
//		{
//			throw new IRollbackException(mySessionCtx, ex.getMessage());
//		}
	}
	/**
	 * 增加新的会计分录
	 * @param stransNo 交易号
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo) throws IRollbackException, RemoteException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean();
		try {
			glBean.deleteGLEntry(stransNo);
		} catch (IException e) {
			throw new IRollbackException(mySessionCtx,e);
		} 
//		sett_GLEntryDAO dao = new sett_GLEntryDAO();
//		try
//		{
//			dao.deleteByTransNo(stransNo);
//		}
//		catch (Exception ex)
//		{
//			throw new IRollbackException(mySessionCtx, ex.getMessage());
//		}
	}
	
	/**
	 * 增加会计分录(for　特殊业务及一付多少)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException
	{
		sett_GLEntryDAO dao = new sett_GLEntryDAO();
		try
		{
			for(int i= 0;i<infos.length; i++){
				GLEntryInfo info = infos[i];
				
				//2008年5月4日 Boxu Add 总账业务存在内部账户交易需要生成辅助核算信息 辅助核算值
				//dao.add(info);
				long returnid = dao.add(info);
				//增加辅助核算信息
				sett_GLEntryDAO gldao = new sett_GLEntryDAO();
				AssistantInfo assistantInfo = new AssistantInfo();
				assistantInfo.setAssitantName("客商辅助核算");
				assistantInfo.setAssitantValue(info.getAssitantValue());
				assistantInfo.setGlentryID(returnid);
				assistantInfo.setModifyUserID(info.getInputUserID());
				assistantInfo.setStatusId(1);
				gldao.addAssitant(assistantInfo);
			}
			
			if(infos.length > 1){
				log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
				boolean checkRes = checkTransDCBalance(infos[0].getTransNo());
				if (!checkRes)
				{
					log.debug("-----借贷平衡不平衡，分录产生失败-------");
					throw new IRollbackException(mySessionCtx, "借贷平衡不平衡，分录产生失败");
				}			
			}
//			if (infos[0].getTransactionTypeID() != SETTConstant.TransactionType.ONETOMULTI)
//			{
//				//判断是否启用银企接口
//				if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)) {
//					
//					//判断是否有形成指令的配置银行
//					ArrayList list = new ArrayList(8);
//					list = Config.getArray(Config.INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE, new ArrayList(8));					
//					if(list.size() > 0) { 
//
//						log.info("交易类型ID ============== "+infos[0].getTransactionTypeID());						
//						//构造参数
//						CreateInstructionParam instructionParam = new CreateInstructionParam();
//						instructionParam.setTransactionTypeID(infos[0].getTransactionTypeID());
//						instructionParam.setTransNo(infos[0].getTransNo());
//						instructionParam.setOfficeID(infos[0].getOfficeID());
//						instructionParam.setCurrencyID(infos[0].getCurrencyID());
//						instructionParam.setCheckUserID(infos[0].getCheckUserID());
//						instructionParam.setInputUserID(infos[0].getInputUserID());
//						
//						//生成银行指令并保存
//						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
//						
//						bankInstruction.createBankInstructionFromTransDetail(instructionParam);	
////						bankInstruction.createBankInstruction(instructionParam);
//					}
//				}
//				else {
//					log.info("当前系统没有提供银企接口服务！");
//				}
//			}
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}	
	/**
	 * 按交易检查借贷平衡
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IRollbackException, RemoteException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean();
		try {
			return glBean.checkTransDCBalance(transNo);
		} catch (IException e) {
			throw new IRollbackException(mySessionCtx,e);
		} 		
//		try
//		{
//			//		1.	调用sett_GLEntryDAO.searchByTransNo(strTransNo)，在会计分录表sett_GLEntry中，选取所有sett_GLEntry.sTransNo = strTransNo 且sett_GLEntry.nStatusID = 3的记录。
//			sett_GLEntryDAO gLEntryDAO = new sett_GLEntryDAO();
//			long status = SETTConstant.TransactionStatus.CHECK;
//			Collection c;
//			try
//			{
//				c = gLEntryDAO.findByTransNoAndStatusID(transNo, status);
//			}
//			catch (SQLException e)
//			{
//				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
//			}
//			if (c == null || c.size() == 0)
//				return false;
//			Iterator i = c.iterator();
//			double lendAmount = 0.0;
//			double loanAmount = 0.0;
//			while (i.hasNext())
//			{
//				GLEntryInfo gLEntryInfo = (GLEntryInfo) i.next();
//				long transDirection = gLEntryInfo.getTransDirection();
//				if ((int) transDirection == 1)					//			2.	累计借方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 1；
//					lendAmount += gLEntryInfo.getAmount();
//				else if ((int) transDirection == 2)					//			累计贷方金额 = Sum(sett_GLEntry.mAmount) where  sett_GLEntry.nTransDirection = 2；  
//					loanAmount += gLEntryInfo.getAmount();
//			}
//			//		3.	如果累计借方金额<>累计贷方金额，则返回0，否则返回1。
//			log.debug("----借方累计金额" + lendAmount + "---------");
//			log.debug("----贷方累计金额" + loanAmount + "---------");
//			if (lendAmount != loanAmount)
//				return false;
//			else
//				return true;
//		}
//		catch (Exception ex)
//		{
//			throw new IRollbackException(mySessionCtx, ex.getMessage());
//		}
	}
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public GLSubjectDefinitionInfo findBySubjectCode(String subjectCode) throws IRollbackException, RemoteException
	{
		Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
		try
		{
			return dao.findByCode(subjectCode);
		}
		catch (SQLException e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage());
		}
	}
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public GLSubjectDefinitionInfo findBySubjectOldCode(String subjectCode) throws IRollbackException, RemoteException
	{
		Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
		try
		{
			return dao.findByOldCode(subjectCode);
		}
		catch (SQLException e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage());
		}
	}
	
	public boolean isExistSubeject(String subjectCode) throws IRollbackException, RemoteException{
//		Sett_GLSubjectDefinitionDAO dao = new Sett_GLSubjectDefinitionDAO();
//		try
//		{
//			return dao.isExistSubeject(subjectCode);
//		}
//		catch (SQLException e)
//		{
//			throw new IRollbackException(mySessionCtx, e.getMessage());
//		}		
		GeneralLedgerBean glBean = new GeneralLedgerBean();
		try {
			return glBean.isExistSubeject(subjectCode);
		} catch (IException e) {
			throw new IRollbackException(mySessionCtx,e);
		} 
	}
	
	
	/**
	 * 判断传入的银行类型ID对应的银行是否需要生成银行指令
	 * @param bankTypeID 银行类型ID
	 * @return 需要返回true, 否则返回false
	 * @throws Exception
	 */
	private boolean checkBankType(long bankTypeID) throws Exception{
		
		boolean bCreateInstruction = false;
		
		try {
			ArrayList list = new ArrayList(8);
			list = Config.getArray(Config.INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE, new ArrayList(8));
			long[] bankType = new long[list.size()];
			
			log.debug("交易传入的银行类型：" + bankTypeID);	
			for(int i=0;i<list.size();i++) {
				bankType[i] = Long.parseLong((String)list.get(i));
				if(bankTypeID == bankType[i]) {
					bCreateInstruction = true;
					log.debug("配置文件中需要生成指令的银行："+bankType[i]);
					break;
				}
			}
			
		} catch (Exception e) {
			log.error("判断账户所属银行类型时出错！");
			e.printStackTrace();
		}
		
		return bCreateInstruction;
	}
	/**
	 * 增加会计分录(for　转贴现业务)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addDiscountGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException
	{
		sett_GLEntryDAO dao = new sett_GLEntryDAO();
		try
		{
			for(int i= 0;i<infos.length; i++){
				GLEntryInfo info = infos[i];
				dao.add(info);
			}
			
			if(infos.length > 1){
				log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
				boolean checkRes = checkTransDCBalance(infos[0].getTransNo());
				if (!checkRes)
				{
					log.debug("-----借贷平衡不平衡，分录产生失败-------");
					throw new IRollbackException(mySessionCtx, "借贷平衡不平衡，分录产生失败");
				}			
			}
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}	
	
	/**
	 * 合并会计分录
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String mergeVoucher(GLEntryInfo[] infos) throws IRollbackException, RemoteException
	{
	    String sbatchno = "";//批次号
	    GLEntryInfo entryInfo = null;
	    MergedVoucherInfo voucherInfo = null;
	    MergedVoucherInfo mergedVoucherInfo = null;
	    Vector vector = new Vector();
	    String scheckedTransNo = "";
		   // 通过编号规则生成批次号
		    System.out.println("------开始获取新批次号--------");
		    UtilOperation utilOperation = new UtilOperation();
		    try {
				sbatchno = utilOperation.getNewTransactionNo(
						infos[0].getOfficeID(),
						infos[0].getCurrencyID(),
						infos[0].getTransactionTypeID());
				System.out.println("------生成的新批次号：--------"+sbatchno);
				//通过循环来合并成新的凭证Info
			    for(int i = 0;i<infos.length;i++){
			    	scheckedTransNo += infos[i].getTransNo()+",";
			    	 //vector作用是存已经被合并的信息
			    	if(vector!=null&&vector.size()>0)
			    	{
			    		//判断是不是已经被合并过了,如果是合并过的就跳出循环
			    		if(vector.contains(infos[i])==true){
			    			continue;
			    		}
			    	}
			    	double mergedamount =0.0;
			    	mergedamount = infos[i].getAmount();
			    	for(int j = i+1;j<infos.length;j++){
			    		//通过判断科目编号和交易方向来合并
			    		if(infos[i].getSubjectCode().equals(infos[j].getSubjectCode())&&infos[i].getTransDirection()==infos[j].getTransDirection()){
			    			mergedamount+=infos[j].getAmount();
			    			vector.add(infos[j]);
			    		}
			    	}
			    	voucherInfo = new MergedVoucherInfo();
					voucherInfo.setSSubjectCode((infos[i].getSubjectCode()));
					voucherInfo.setMAmount(mergedamount);
					voucherInfo.setSBatchNo(sbatchno);
					voucherInfo.setDtExecute(infos[i].getExecute());
					voucherInfo.setDtIntereststStart(null);//起息日目前设置为空
					voucherInfo.setNOfficeId(infos[i].getOfficeID());
					voucherInfo.setNCurrencyId(infos[i].getCurrencyID());
					voucherInfo.setNTransDirection(infos[i].getTransDirection());
					voucherInfo.setNTransactionTypeId(infos[i].getTransactionTypeID());
					voucherInfo.setNStatusId(SETTConstant.TransactionStatus.CHECK);
					voucherInfo.setSAbstract("合并凭证");
					//添加到数据库中
					Sett_mergedVouchersDAO vouchersDAO = new Sett_mergedVouchersDAO();
					//将合并后的凭证信息存入表sett_mergedVouchers
					vouchersDAO.add(voucherInfo);
			    }
			    //通过交易号来更新状态和加入对应的批次号
			    String[] sTransNo = scheckedTransNo.split(",");
			    
			    for(int i = 0;i<sTransNo.length;i++)
			    {
			    	sett_GLEntryDAO dao = new sett_GLEntryDAO();
			    	//根据交易号将会计分录的状态变为“已合并”为8并且加入批次号
		    		dao.updateByTransNo(sTransNo[i],SETTConstant.EntryStatus.MERGED,sbatchno);
			    }
			    //判断是否合并凭证是否成功,小于零为不正常
			    Sett_mergedVouchersDAO vouchersDAO = new Sett_mergedVouchersDAO();
			    if(vouchersDAO.isMergedSuccess(sbatchno)<=0){
			    	throw new IRollbackException(mySessionCtx,"合并凭证失败");
			    }
		    	}catch (Exception ex) {
					throw new IRollbackException(mySessionCtx, ex.getMessage());
				} 
	    //返回合并后的批次号
	    return sbatchno;
	}
	
	/**
	 * 取消合并会计分录
	 * @param String[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return 交易号字符串
	 */
	public String mergeCancel(String[] sbatchno) throws IRollbackException, RemoteException
	{
	    //根据合并批次号查出交易号
	    //sett_GLEntryDAO dao = new sett_GLEntryDAO();
	    //dao.findByBatchNo();
		String scheckedTransNo = "";
		Sett_mergedVouchersBiz vouchersBiz = new Sett_mergedVouchersBiz();
		sett_GLEntryDAO entryDAO = new sett_GLEntryDAO();
		try {
			for(int i = 0;i<sbatchno.length;i++){
				//根据合并批次号查出交易号
				Collection collection = entryDAO.findTransNoBySbatchNo(sbatchno[i]);
				//把交易号拼装成一个字符串
				if(collection!=null&&collection.size()>0){
					Iterator iterator = collection.iterator();
					while(iterator.hasNext()){
						String transNo = (String)iterator.next();
						scheckedTransNo += transNo+",";
					}
				}
				System.out.println("======scheckedTransNo====:"+scheckedTransNo);
				//在sett_glentry修改取消合并的状态1.将状态已经合并修改为已复核
				//2.去掉批次号字段的值
				entryDAO.updateBySbatchNo(sbatchno[i],SETTConstant.EntryStatus.CHECKED);
				//根据批次号删除sett_mergedVouchers表中的数据
				vouchersBiz.delBySbatchno(sbatchno[i]);
				 //判断是否合并凭证是否成功,小于零为不正常
			    Sett_mergedVouchersDAO vouchersDAO = new Sett_mergedVouchersDAO();
			    if(vouchersDAO.isMergedSuccess(sbatchno[i])>0){
			    	throw new IRollbackException(mySessionCtx, "取消合并凭证失败");
			    }
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	    return scheckedTransNo;
	}
	
	
	/**
	 * 查询所有未复核 和 已复核 分录设置定义表记录 
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有分录设置定义表记录集合
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection findAllGLEntryDefinitionTemp(String strState,long officeID, long currencyID, long orderType) throws IRollbackException, RemoteException
	{
		try
		{
			Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
			Collection collection = tempDao.findAllTemp(strState,officeID, currencyID, orderType);
			return collection;
		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
	}
	
	/**
	 * 按照交易类型 复核 会计分录设置
	 * @return 成功返回 >= 0 , 失败返回 -1
	 * @throws IRollbackException
	 */
	public long checkGLEntryDefinitionTemp(String strTransactionType,long checkUserID,long officeID, long currencyID) throws IRollbackException, RemoteException{
		synchronized(lockObj){
			Collection coll = null;
			long lReturn = -1;
			GLEntryDefinitionTempInfo checkInfo = null;
			try
			{
				Sett_GLEntryDefinitionDAO dao = new Sett_GLEntryDefinitionDAO();
				
				Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
				
				String[]  strTransTypes =  strTransactionType.split(",");
				String transTypeIDs = "";
				for (int i = 0 ; i< strTransTypes.length; i++){
					
					transTypeIDs = transTypeIDs + "," + strTransTypes[i].split("@")[0];
					
					String[] tempIDs = strTransTypes[i].split("@")[1].split("::");
					String[] inputDates = strTransTypes[i].split("@")[2].split("&&");
					
					if(tempIDs.length == inputDates.length){
						for (int j =  0 ; j < tempIDs.length; j++){
							
							checkInfo = tempDao.findByTempID(Long.parseLong(tempIDs[j]));
							
							 if(checkInfo==null){
								 throw new RemoteException("有已被撤销的会计分录设置,请刷新页面重新复核");
							 }else if( checkInfo != null && checkInfo.getNStatusID()== SETTConstant.GeneralLedgerStatus.CHECK){
								 throw new RemoteException("有已被复核的会计分录设置,请刷新页面重新复核");
							 }else if( checkInfo != null && !DataFormat.formatDate(checkInfo.getInputDate(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS).equals(inputDates[j]))
							 {
								 throw new RemoteException("有已被修改的会计分录设置,请刷新页面重新复核");
							 }
						}
					}else{
						
						throw new RemoteException("从页面获取的参数有误：" + strTransactionType);
					}

					
				}

				if(transTypeIDs.length()>=1){
					transTypeIDs = transTypeIDs.substring(1);
				}
				
				coll = tempDao.findUncheckGLEntryDefinitionTemp(transTypeIDs,officeID,currencyID);
				
				lReturn = tempDao.checkGLEntryDefinitionTemp(transTypeIDs, checkUserID,officeID,currencyID);
				
				if( coll !=null && coll.size()>0){
					Iterator it = coll.iterator();
					while(it.hasNext()){
						
						GLEntryDefinitionTempInfo tempInfo = (GLEntryDefinitionTempInfo)it.next();
						
						GLEntryDefinitionInfo info = switchTempInfoToGLEntryDefinitionInfo(tempInfo);
						
						if(info.getID()>0){
							
							if(tempInfo.getNOperateType() == SETTConstant.GeneralLedgerOperationType.DELETE){
								dao.deletePhysically(info.getID());
							}else{
								dao.update(info);
							}
							
						}else{
							dao.add(info);
						}
						
					}
					
				}else{
						
						throw new RemoteException("该业务类型的会计分录设置已被复核或撤销");
				
				}
				
			}
			catch (Exception ex)
			{
				throw new IRollbackException(mySessionCtx, ex.getMessage());
			}
			
			return lReturn;
		}
		
	}
	
	/**
	 * 由会计分录未生效设置INfo类 转换 会计分录设置已生效设置类
	 * @return  info GLEntryDefinitionInfo
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionInfo switchTempInfoToGLEntryDefinitionInfo(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException{
		GLEntryDefinitionInfo info = null ;
		try
		{

				info = new GLEntryDefinitionInfo();
				
				info.setID(tempInfo.getNID());
                info.setOfficeID(tempInfo.getOfficeID());
                info.setCurrencyID(tempInfo.getCurrencyID());
                info.setTransactionType(tempInfo.getTransactionType());
				info.setSubTransactionType(tempInfo.getSubTransactionType());                
                info.setCapitalType(tempInfo.getCapitalType());
                info.setEntryType(tempInfo.getEntryType());
                info.setDirection(tempInfo.getDirection());
                info.setSubjectType(tempInfo.getSubjectType());
				info.setSubjectCode(tempInfo.getSubjectCode());
                info.setAmountDirection(tempInfo.getAmountDirection());
                info.setAmountType(tempInfo.getAmountType());
                info.setOfficeType(tempInfo.getOfficeType());

		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
		
		return info;
		
	}
	
	
	/**
	 * 由会计分录设置已生效设置类 转换 会计分录未生效设置INfo类  
	 * @return tempInfo GLEntryDefinitionTempInfo
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionTempInfo switchInfoToGLEntryDefinitionTempInfo(GLEntryDefinitionInfo info,GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException{
		try
		{

				tempInfo.setNID(info.getID());
				tempInfo.setOfficeID(info.getOfficeID());
				tempInfo.setCurrencyID(info.getCurrencyID());
				tempInfo.setTransactionType(info.getTransactionType());
				tempInfo.setSubTransactionType(info.getSubTransactionType());                
				tempInfo.setCapitalType(info.getCapitalType());
				tempInfo.setEntryType(info.getEntryType());
				tempInfo.setDirection(info.getDirection());
				tempInfo.setSubjectType(info.getSubjectType());
				tempInfo.setSubjectCode(info.getSubjectCode());
				tempInfo.setAmountDirection(info.getAmountDirection());
				tempInfo.setAmountType(info.getAmountType());
				tempInfo.setOfficeType(info.getOfficeType());

		}
		catch (Exception ex)
		{
			throw new IRollbackException(mySessionCtx, ex.getMessage());
		}
		
		return tempInfo;
		
	}
	/**
	 * 新增分录设置定义记录
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long addGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException
	{
		synchronized(lockObj){
			long returnId = -1;
			GLEntryDefinitionTempInfo uncheckTempInfo = null;
			try
			{
				validateEntryDefinitionTempInfo(tempInfo);
				tempInfo.setNOperateType(SETTConstant.GeneralLedgerOperationType.ADD);
				
				Sett_GLEntryDefinitionTempDAO dao = new Sett_GLEntryDefinitionTempDAO();
	
				uncheckTempInfo = dao.findUncheckGLEntryDefinitionByTransType(tempInfo.getTransactionType(),tempInfo.getOfficeID(),tempInfo.getCurrencyID());
				
				if(uncheckTempInfo ==null ){
					
					returnId = dao.addTemp(tempInfo);
					
				}else if(uncheckTempInfo !=null && uncheckTempInfo.getInputUserID() == tempInfo.getInputUserID()){
					
					returnId = dao.addTemp(tempInfo);
					
				}else{
					
					throw new RemoteException("该业务类型的会计分录设置已经被其他人新增或修改");
				}
				
				return returnId;
			}
			catch (Exception ex)
			{
				throw new IRollbackException(mySessionCtx, ex.getMessage());
			}
		}
	}
	
	
	/**
	 *  查询所有未复核 和 已复核 分录设置定义表记录 分页查询
	 * @return 未复核 已复核 的 会计 分录的 分页查询
	 * @throws IRollbackException
	 */
	
	public PageLoader findAllGLEntryDefinitionPagerLoaderTemp(long nStatusID,long officeID, long currencyID,long orderType,String sort) throws RemoteException
	{

			Sett_GLEntryDefinitionTempDAO tempDao = new Sett_GLEntryDefinitionTempDAO();
			return tempDao.findAllPagerLoaderTemp(nStatusID,officeID, currencyID, orderType,sort);
	}
}
