/*
 * Created on 2003-9-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.IException;

/**
 * 对其它子系统的接口实现
 * @author yqwu
 * 
 */
public class GeneralLedgerOperation
{
	GeneralLedger generalLedger;

	public GeneralLedgerOperation() throws RemoteException, IRollbackException
	{
		GeneralLedgerHome home;
		try
		{
			home = (GeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(GeneralLedgerHome.class);
			generalLedger = (GeneralLedger) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException();
		}
	}


	public GeneralLedgerOperation(Connection conn) 
	{

	}


	/**
	 * 通过标识查询
	 * @param id long
	 * @return 科目总表记录
	 * @throws IRollbackException
	 */
	public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id) throws RemoteException, IRollbackException
	{
		GLSubjectDefinitionInfo info = null;
		info = this.generalLedger.findGLSubjectDefinitionByID(id);
		return info;
	}

	/**
	 * 通过代码查询
	 * @param subjectCode String
	 * @return 科目总表记录
	 * @throws IRollbackException
	 */
	public GLSubjectDefinitionInfo findGLSubjectDefinitionByCode(String subjectCode) throws RemoteException, IRollbackException
	{
		GLSubjectDefinitionInfo info = null;
		info = this.generalLedger.findGLSubjectDefinitionByCode(subjectCode);
		return info;
	}

	/**
	 * 查询所有科目总表记录
	 * @param includeDisable true-包括无效，false-不包括无效
	 * @param officeID 机构ID 如果是-1忽略此条件
	 * @param CurrencyID 币种 如果是-1忽略此条件
	 * @param subjectType 科目属性 如果是-1忽略此条件
	 * @return 所有科目总表记录集合
	 * @throws IRollbackException
	 */
	public Collection findAllGLSubjectDefinition(boolean includeDisable, long officeID, long CurrencyID, long subjectType) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		collection = this.generalLedger.findAllGLSubjectDefinition(includeDisable, officeID, CurrencyID, subjectType);
		return collection;
	}

	/**
	 * 通过标识查询
	 * @param id long
	 * @return GLEntryDefinitionInfo
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id) throws RemoteException, IRollbackException
	{
		GLEntryDefinitionInfo info = null;
		info = this.generalLedger.findGLEntryDefinitionByID(id);
		return info;
	}

	/**
	 *  查询所有分录设置定义表记录
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType
	 * @return 所有分录设置定义表记录集合
	 * @throws IRollbackException
	 */
	public Collection findAllGLEntryDefinition(long officeID, long currencyID, long orderType) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		collection = this.generalLedger.findAllGLEntryDefinition(officeID, currencyID, orderType);
		return collection;
	}

	/**
	 * 删除会计分录
	 * @param stransNo 交易号
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo) throws IRollbackException, RemoteException
	{
		generalLedger.deleteGLEntry(stransNo);
	}
	
	
	/**
	 * 删除会计分录
	 * @param stransNo 交易号
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void deleteGLEntry(String stransNo, Connection conn) throws IException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		glBean.deleteGLEntry(stransNo);
	}	

	/**
	 * 交易产生会计分录
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param) throws IRollbackException, RemoteException
	{
		return generalLedger.generateGLEntry(param);
	}
	
	/**
	 * 交易产生会计分录
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public boolean generateGLEntry(GenerateGLEntryParam param, Connection conn) throws IException
	{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		return glBean.generateGLEntry(param);
	}	
	

	
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectCode(String subjectCode)  throws IRollbackException, RemoteException{
		return generalLedger.findBySubjectCode(subjectCode);
	}
	
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectOldCode(String subjectCode)  throws IRollbackException, RemoteException{
		return generalLedger.findBySubjectOldCode(subjectCode);
	}
	
	/**
	 * 判断科目号是否存在
	 * @return　boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode) throws IRollbackException, RemoteException{
		return generalLedger.isExistSubeject(subjectCode);		
	}
	
	/**
	 * 判断科目号是否存在
	 * @return　boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode, Connection conn) throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);				
		return glBean.isExistSubeject(subjectCode);		
	}	
	
	/**
	 * 增加会计分录(for　特殊业务)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		generalLedger.addGLEntries(infos);
	}
	/**
	 * 增加会计分录(for　转贴现业务)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addDiscountGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		generalLedger.addDiscountGLEntries(infos);
	}
	
	/**
	 * 按交易检查借贷平衡
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IRollbackException, RemoteException{
		return generalLedger.checkTransDCBalance(transNo);
	}
	
//	/**
//	 * 	单条分录过总账
//	 *  将 一条分录入到总账中，一般是关机后，系统将调用本方法，逐条将所有分录过到总账中。
//	*/
//	public void postGLEntry(long officeID, long currencyID, String subjectCode, long transDirection,double amount,Timestamp execDate, Connection conn) throws IException{
//		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);
//		glBean.postGLEntry(officeID, currencyID, subjectCode, transDirection, amount, execDate);		
//	}
	
	/**
	 * 建立每日初始总账
	*/
	public void createSODGLBalance(long officeID, long currencyID, Timestamp today, Connection conn)  throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);		
		glBean.createSODGLBalance(officeID, currencyID,today);
	}	
	
	/**	日终分录过总账*/
	public void postEODGLEntries(long officeID, long currencyID, Connection conn)  throws IException{
		GeneralLedgerBean glBean = new GeneralLedgerBean(conn);
		glBean.postEODGLEntries(officeID, currencyID);		
	}
	
	/**
	 * 合并会计分录
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String mergeVoucher(GLEntryInfo[] infos) throws IRollbackException, RemoteException{
		return generalLedger.mergeVoucher(infos);
	}

	/**
	 * 取消会计分录
	 * @param sbatchno批次号
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return 交易号连接字符串
	 */
	public String mergeCancel(String[] sbatchno) throws IRollbackException, RemoteException{
		return generalLedger.mergeCancel(sbatchno);
	}
}
