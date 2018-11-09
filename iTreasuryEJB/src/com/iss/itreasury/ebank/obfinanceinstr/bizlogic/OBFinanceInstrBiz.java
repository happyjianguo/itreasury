package com.iss.itreasury.ebank.obfinanceinstr.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBBankPayInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBBankPayDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BranchbankInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.PayerOrPayeeInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.OBFSWorkflowManager;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBBankPayDao;
import com.iss.itreasury.ebank.util.OBConstant;



public class OBFinanceInstrBiz {
	
    private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
	
	public FinanceInfo findByInstructionID(long lInstructionID, long lCurrencyID)
		throws Exception
	{
        FinanceInfo info = null;
        try
        {
            OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
            info = obFinanceInstrDao.findByInstructionID(lInstructionID, lCurrencyID);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return info;
	}
	public PayerOrPayeeInfo getLoanAccountSelectInfo(long lclientID, long lcurrencyID, long laccountGroupType,long lUserID) throws RemoteException, Exception
    {
        PayerOrPayeeInfo info = null;
        try
        {
            OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
            info = obFinanceInstrDao.getLoanAccountSelectInfo(lclientID, lcurrencyID, laccountGroupType,lUserID);
        }
        catch (RemoteException e)
        {
            
            throw new IException("Gen_E001");
        }
        return info;
    }
	public void deleteEbankByID(long lInstructionID) throws Exception
	{
		try
		{
		OBBankPayDao dao = new OBBankPayDao();
		dao.deleteEbankByID(lInstructionID);
		}
		catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
		
	}
	public void updateFinanceInfo(FinanceInfo financeInfo)
		throws Exception
	{
        try
        {
            OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
            obFinanceInstrDao.update(financeInfo);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
	}
	
	/**
	 * 根据账户种类得到账户信息
	 * 
	 * @param  ClientID,CurrencyID,AccountTypeID
	 *            
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long getLoanAccountID(long lClientID, long lCurrencyID, long lAccountTypeID)
		throws Exception
	{
        long lAccountId = -1;
        try
        {
        	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        	lAccountId = obFinanceInstrDao.getLoanAccountID(lClientID, lCurrencyID, lAccountTypeID);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return lAccountId;
	}
	
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息, 用于交易指令查询
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryCollectionByQuery(QueryCapForm queryCapForm)
		throws Exception
	{
		Collection coll = null;
        try
        {
        	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        	coll = obFinanceInstrDao.queryCollectionByQuery(queryCapForm);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return coll;
	}
	/**
	 * 取消复核查询，如果存在业务挂审批流并且是自动复核，这里不查询到此业务信息
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryCheckCollectionByQuery(QueryCapForm queryCapForm)
		throws Exception
	{
		Collection coll = null;
        try
        {
        	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        	coll = obFinanceInstrDao.queryCheckCollectionByQuery(queryCapForm);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return coll;
	}
	
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息状态, 用于交易指令汇总查询
	 * @param QueryCapForm 查询条件表单类
	 * @return Map  所有符合条件交易指令汇总信息
	 * @exception Exception
	 */
	public Map queryOBFinanceInstrStatus(QueryCapForm queryCapForm)
		throws Exception
	{
		Map map = null;
        try
        {
        	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        	map = obFinanceInstrDao.queryMapByQueryStatus(queryCapForm);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return map;
	}
	
	/**
	 * 根据查询条件类，查询出符合查询条件的指令信息, 用于交易指令复核查询
	 * @param FinanceInfo 查询条件
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryCollectionByCheck(FinanceInfo financeInfo)
		throws Exception
	{
		Collection coll = null;
        try
        {
        	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        	coll = obFinanceInstrDao.queryCollectionByCheck(financeInfo);
        }
        catch (Exception e)
        {
            log.error(e.toString());
            throw new IException("Gen_E001");
        }
        return coll;
	}
//	网上银行批量复核翻页查询
	public PageLoader queryCheckInfo(Query_FinanceInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		OBFinanceInstrDao obfDao = new OBFinanceInstrDao();
		pageLoader = obfDao.queryInfo_Check(info);
		return pageLoader;
	
}
//	银行直联批量复核翻页查询
	public PageLoader queryCheckInfo_bankpay(OBBankPayInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		OBBankPayDao obfDao = new OBBankPayDao();
		pageLoader = obfDao.queryBankpayInfo(info);
		return pageLoader;
	
}
	public Collection queryEbank(QueryCapForm qcf) throws Exception
	{
		
		Collection coll = new ArrayList();
		OBBankPayDao obfDao = new OBBankPayDao();
		coll=obfDao.queryEbank(qcf);
		return coll;
	}
	public OBBankPayInfo findEbankById(long lInstructionID) throws Exception
	{
		OBBankPayInfo info = new OBBankPayInfo();
		OBBankPayDao obfDao = new OBBankPayDao();
		info = obfDao.findEbankById(lInstructionID);
		return info;
		
		
	}
	public void updateEbank(OBBankPayInfo info) throws Exception
	{
		OBBankPayDao obfDao = new OBBankPayDao();
		obfDao.updateEbank(info);
		
	}
	public boolean hasAuthority(String payerAccountNo,long lUserID,long currencyType) throws Exception
	{
		boolean hasAuthority = false;
		OBBankPayDao obfDao = new OBBankPayDao();
		ArrayList list = new ArrayList();
		list = obfDao.selectAuthority(lUserID, currencyType);
		if(list!=null)
		{
			hasAuthority=list.contains(payerAccountNo);
		}
		return hasAuthority;
	}
	public void submitExamine(OBBankPayInfo info) throws Exception
	{
		long lID = info.getId();
		InutParameterInfo tempInfo = info.getInutParameterInfo();
		Connection con = null;
		con = Database.getConnection();
        con.setAutoCommit(false);
		OBBankPayDao dao = new OBBankPayDao(con);
		tempInfo.setUrl(tempInfo.getUrl()+lID);
		tempInfo.setTransID(String.valueOf(lID));
		tempInfo.setDataEntity(info);
//		本单位保存并提交审批
		tempInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		//提交审批
		OBFSWorkflowManager.initApproval(tempInfo);
		//更新状态到审批中
		dao.updateStatus(lID,OBConstant.SettInstrStatus.APPROVALING);
		
		
	}
	//银行直联--业务处理--初始放大镜查找
	public OBBankPayInfo query_OBMagnifier(OBBankPayInfo info_bankpay) throws Exception
	{
		OBBankPayDao obfDao = new OBBankPayDao();
		OBBankPayInfo info=null;
		info=obfDao.query_OBMagnifier(info_bankpay);
		return info;
	}
	public boolean hasUserAuthority(String payerAccountNo,long lUserID,long currencyType) throws Exception
	{
		boolean hasUserAuthority = false;
		OBFinanceInstrDao obfDao = new OBFinanceInstrDao();
		ArrayList list = new ArrayList();
		list = obfDao.selectUserAuthority(lUserID, currencyType);
		if(list!=null)
		{
			hasUserAuthority=list.contains(payerAccountNo);
		}
		return hasUserAuthority;
	}
	
	public Collection query_uncheck(QueryCapForm qcf) throws Exception
	{
		OBBankPayDao obb =new OBBankPayDao();
		Collection resuilt=obb.query(qcf);
		return resuilt;
	}
	//逐笔付款--付款方放大镜初始查询
	public FinanceInfo query_OBMagnifier1(FinanceInfo financeInfo) throws Exception
	{
		OBBankPayDao obfDao = new OBBankPayDao();
		FinanceInfo info=null;
		info=obfDao.query_OBMagnifier1(financeInfo);
		return info;
		
	}
	/**
	 * 逐笔付款 SAP指令号是否重复
	 * @param sApplyCode
	 * @param lSource
	 * @return
	 * @throws Exception
	 */
	public boolean isSameApplyCode(long lID,String sApplyCode,long lSource) throws Exception
	{
		boolean isSameApplyCode = false;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		isSameApplyCode=dao.isSameApplyCode(lID,sApplyCode, lSource);
		return isSameApplyCode;
	}
	
	public BranchbankInfo findBranchBankByBankName(BranchbankInfo queryInfo) throws Exception
	{
		BranchbankInfo info = null;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		long bankID = -1;
		if(!queryInfo.getBranchName().equals("其他银行"))
		{
			bankID = this.getBankTypeIDByBankName(queryInfo.getBranchName());
			queryInfo.setBranchID(bankID);
			info = dao.findBranchBankByBankCode(queryInfo);
		}
		if(info==null)
		{
			bankID = this.getBankTypeIDByBankName("其他银行");
			queryInfo.setBranchID(bankID);
			info = dao.findBranchBankByBankCode(queryInfo);
		}
		return info;
	}
	
	public long getBankTypeIDByBankName(String bankName) throws Exception
	{
		long bankID = -1;
		long[] lArrayID = SETTConstant.BankType.getAllCodeWithEp();
		String tempName = "";
		for(int i=0;i<lArrayID.length;i++)
		{
			tempName = SETTConstant.BankType.getName_EP(lArrayID[i]);
			if(tempName.equals(bankName))
			{
				bankID = lArrayID[i];
				break;
			}
			
		}
		return bankID;
	}
	
	public ArrayList findOfficeInformation(long clientId) throws Exception
	{
		ArrayList list = new ArrayList();
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		list = dao.findOfficeInformation(clientId);
		return list;
	}
	
}

