/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.bizlogic;
import java.rmi.RemoteException;
import java.util.*;

//import com.iss.itreasury.settlement.dataentity.TransInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.*;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface GeneralLedger extends javax.ejb.EJBObject
{
    static int TRANSACTION_TYPE = 1; //交易类型
    static int CAPITAL_TYPE = 2; //资金流向 
    static int ENTRY_TYPE = 3; //分录类型
    static int DIRECTION = 4; //借贷
    static int SUBJECT_TYPE = 5; //科目类型
    static int SUBJECT_CODE = 6; //科目号
    static int AMOUNT_DIRECTION = 7; //金额方向
    static int AMOUNT_TYPE = 8; //类型
    static int OPERATE_TYPE = 9; //操作类型 1:新增;2:修改;3:删除
    static int INPUTDATE_TYPE = 10; // 录入时间
    static int CHECKDATE_TYPE = 11; // 复核时间
    /**
     * 新增总账定义记录
     * @param info GLSubjectDefinitionInfo
     * @return 总账定义记录id
     * @throws java.rmi.RemoteException
     * @throws IRollbackException
     */
    public long addGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws RemoteException, IRollbackException;

    /**
     * 修改科目总表记录
     * @param info 科目总表记录
     * @return 科目总表记录id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long updateGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws IRollbackException, RemoteException;

    /**
     * 通过标识查询
     * @param id long
     * @return 科目总表记录
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id)
        throws IRollbackException, RemoteException;

    /**
     * 通过代码查询
     * @param subjectCode String
     * @return 科目总表记录
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByCode(String subjectCode)
        throws IRollbackException, RemoteException;

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
    public Collection findAllGLSubjectDefinition(
        boolean includeDisable,
        long officeID,
        long CurrencyID,
        long subjectType)
        throws IRollbackException, RemoteException;

    /**
     * 修改科目总表记录状态
     * @param id 科目总表记录id
     * @param status 科目总表记录状态
     * @return 科目总表记录id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long updateGLSubjectDefinitionStatus(long id, long status)
        throws IRollbackException, RemoteException;

    /**
     * 新增分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long addGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IRollbackException, RemoteException;

	/**
	 * 新增分录设置定义记录
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public long addGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
        throws IRollbackException, RemoteException;
    
    /**
    	 * 更新分录设置定义记录
    	 * @param info GLEntryDefinitionInfo
    	 * @return 分录设置定义记录id
    	 * @throws IRollbackException
    	 * @throws RemoteException
    	 */
    public long updateGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IRollbackException, RemoteException;

	/**
	 * 更新分录设置定义记录
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return 分录设置定义记录id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
public long updateGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
    throws IRollbackException, RemoteException;
    /**
     * 通过标识查询
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id)
        throws IRollbackException, RemoteException;

    /**
     * 通过标识查询
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IRollbackException
     * @throws RemoteException
     */
    public GLEntryDefinitionTempInfo findGLEntryDefinitionTempInfoByID(long id)
        throws IRollbackException, RemoteException;
    /**
     * 查询所有分录设置定义表记录
     * @param officeID 机构ID
     * @param currencyID 币种
     * @param orderType 排序方式
     * @return 所有分录设置定义表记录集合
     * @throws IRollbackException
     * @throws RemoteException
     */
    public Collection findAllGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;
    
	/**
	 * 查询所有未复核和已生效分录设置定义表记录
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有未复核和已生效分录设置定义表记录
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUncheckAndUsedGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;

	/**
	 * 查询所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUnUseAndUsedGLEntryDefinition(
        long officeID,
        long currencyID,
        long orderType)
        throws IRollbackException, RemoteException;
    /**
     * 物理删除会计分录定义
     * @param id 会计分录定义id
     * @return 会计分录定义id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long deleteGLEntryDefinition(long id)
        throws IRollbackException, RemoteException;
    
    /**
     * 物理删除会计分录定义
     * @param id 会计分录定义id
     * @return 会计分录定义id
     * @throws IRollbackException
     * @throws RemoteException
     */
    public long deleteGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
        throws IRollbackException, RemoteException;

    /**
     * 交易产生会计分录
     * @return 成功返回true, 失败返回false
     * @throws IRollbackException
     * @throws RemoteException
     */
    public boolean generateGLEntry(GenerateGLEntryParam param)
        throws IRollbackException, RemoteException;

    /**
     * 删除会计分录
     * @param stransNo 交易号
     * @throws IRollbackException
     * @throws RemoteException
     */
    public void deleteGLEntry(String stransNo)
        throws IRollbackException, RemoteException;
        
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectCode(String subjectCode)  throws IRollbackException, RemoteException;
	
	/**
	 * 根据科目号查询科目定义信息
	 * @return GLSubjectDefinitionInfo
	 * @throws IRollbackException
	 * @throws RemoteException
	 */	
	public GLSubjectDefinitionInfo findBySubjectOldCode(String subjectCode)  throws IRollbackException, RemoteException;
	
	
	/**
	 * 判断科目号是否存在
	 * @return　boolean
	 * @throws IRollbackException
	 * @throws RemoteException
	 */		
	public boolean isExistSubeject(String subjectCode) throws IRollbackException, RemoteException;
	
	/**
	 * 增加会计分录(for　特殊业务)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * 增加会计分录(for　转贴现业务)
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public void addDiscountGLEntries(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * 按交易检查借贷平衡
	 * @return 成功返回true, 失败返回false
	 * @throws IRollbackException
	 */
	public boolean checkTransDCBalance(String transNo) throws IRollbackException, RemoteException;	
	
	/**
	 * 合并会计分录
	 * @param GLEntryInfo[]
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public String mergeVoucher(GLEntryInfo[] infos) throws IRollbackException, RemoteException;
	
	/**
	 * 取消会计分录
	 * @param sbatchno批次号
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return 交易号连接字符串
	 */
	public String mergeCancel(String[] sbatchno) throws IRollbackException, RemoteException;
	
	/**
	 * 查询所有未复核 和 已复核 分录设置定义表记录 
	 * @param officeID  CurrencyID
	 * @throws IRollbackException
	 * @throws RemoteException
	 * @return 记录 Collection
	 */
	public Collection findAllGLEntryDefinitionTemp(String strState,long officeID, long currencyID,long orderType) throws  IRollbackException, RemoteException;

	/**
	 * 按照交易类型 复核 会计分录设置
	 * @return 成功返回 >= 0 , 失败返回 -1
	 * @throws IRollbackException
	 */
	public long checkGLEntryDefinitionTemp(String strTransactionType,long checkUserID,long officeID, long currencyID) throws IRollbackException, RemoteException;	
	
	/**
	 * 按照交易类型 复核 会计分录设置
	 * @return 成功返回 >= 0 , 失败返回 -1
	 * @throws IRollbackException
	 */
	public GLEntryDefinitionInfo switchTempInfoToGLEntryDefinitionInfo(GLEntryDefinitionTempInfo tempInfo) throws IRollbackException, RemoteException;	
	
	/**
	 *  查询所有未复核 和 已复核 分录设置定义表记录 分页查询
	 * @return 未复核 已复核 的 会计 分录的 分页查询
	 * @throws IRollbackException
	 */
	
	public PageLoader findAllGLEntryDefinitionPagerLoaderTemp(long nStatusID,long officeID, long currencyID,long orderType,String sort) throws RemoteException;
}
