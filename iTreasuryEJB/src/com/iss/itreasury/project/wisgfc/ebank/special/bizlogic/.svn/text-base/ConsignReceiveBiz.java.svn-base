package com.iss.itreasury.project.wisgfc.ebank.special.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OpenDateInfo;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.project.wisgfc.ebank.special.dao.ConsignReceiveDao;
import com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo;
import com.iss.itreasury.settlement.bizdelegation.AccountSystemDelegation;
import com.iss.itreasury.settlement.dataentity.AccountSystemInfo;
import com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xlchang 2010-11-29
 * 委托收款操作类
 *
 */
public class ConsignReceiveBiz {
	private static Log4j log4j = new Log4j(Constant.ModuleType.EBANK);
	private ConsignReceiveDao dao = new ConsignReceiveDao();
		
	/**
	 * 新增委托收款
	 * @param info
	 * @throws IException
	 */
	public void add(ConsignReceiveInfo info) throws IException {
		try {
			dao.add(info);
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 更新委托收款
	 * @param info
	 * @throws IException
	 */
	public void update(ConsignReceiveInfo info) throws IException {
		try {				
			dao.update(info);					
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 删除委托收款
	 * @param info
	 * @throws IException
	 */
	public void del(ConsignReceiveInfo info) throws IException {
		try {
			dao.update(info);			
		}catch (ITreasuryDAOException e) {
			throw new IException("删除失败！");
		} 		
	}	
		
	/**
	 * 查询委托收款信息（委托收款发起默认条件）
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection startDefFindByCondition(ConsignReceiveInfo info) throws IException{
		Collection c = null;
		try {
		ConsignReceiveInfo qInfo = new ConsignReceiveInfo();
    	qInfo.setNOfficeID(info.getNOfficeID());
    	qInfo.setNCurrencyID(info.getNCurrencyID());
    	qInfo.setNPayeeClientID(info.getNPayerClientID());
    	if (info.getQ_NStatus() > 0) {	
    		qInfo.setNStatus(info.getQ_NStatus());
    	}      
     	String strStatus =  OBConstant.SettInstrStatus.SAVE + "," + OBConstant.SettInstrStatus.SUBMIT + ","
     			+ OBConstant.SettInstrStatus.CONFIRM + "," + OBConstant.SettInstrStatus.REFUSE;
    	c = dao.findByCondition(qInfo,strStatus);    	
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("查询委托收款信息失败！");
		}
    	return c;			
	}
	
	/**
	 * 查询委托收款信息（委托收款确认默认条件）
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection confirmDefFindByCondition(ConsignReceiveInfo info, long nClientID) throws IException{	
		try {
			ConsignReceiveInfo qInfo = new ConsignReceiveInfo();
			qInfo = new ConsignReceiveInfo();
			qInfo.setNOfficeID(info.getNOfficeID());
	    	qInfo.setNCurrencyID(info.getNCurrencyID());
	    	//qInfo.setNStatus(OBConstant.SettInstrStatus.SUBMIT);
	    	qInfo.setNPayerClientID(nClientID);    		
	    	String strStatus =  OBConstant.SettInstrStatus.SUBMIT + "," + OBConstant.SettInstrStatus.CONFIRM + ","
	    			+ OBConstant.SettInstrStatus.REFUSE;
	    	return dao.findByCondition(qInfo,strStatus);		
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("查询委托收款信息失败！");
		}
		
    		
	}
		
		
	/**
	 * 根据id查询委托收款信息
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public ConsignReceiveInfo findByID(long id) throws IException {
		ConsignReceiveInfo result = null;
		try {
			result = (ConsignReceiveInfo)dao.findByID(id, ConsignReceiveInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new IException("查询委托收款信息失败！");
		} 
		return result;
	}
	
	/**
	 * 委托收款确认 新增一条内转业务，更新委托收款相关信息
	 * @param info
	 * @throws IException 
	 */
	public FinanceInfo createFinanceInfo(ConsignReceiveInfo info) throws IException {
		long instrID = -1;
		FinanceInfo result = null;
		try {
			
			//封装内转业务信息
			FinanceInfo fInfo = new FinanceInfo();
			ConsignReceiveInfo dbInfo = findByID(info.getId());
			fInfo.setOfficeID(dbInfo.getNOfficeID());// 交易办事处
			fInfo.setCurrencyID(dbInfo.getNCurrencyID());// 交易币种		
			fInfo.setClientID(dbInfo.getNPayerClientID());// 交易提交单位
			fInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);  // 网上交易类型
			fInfo.setRemitType(OBConstant.SettRemitType.INTERNALVIREMENT);// 汇款方式
			fInfo.setAmount(dbInfo.getMAmount());  // 交易金额
			fInfo.setExecuteDate(info.getDTExecute()); // 执行日
			
			//网银最晚提交时间效验
			OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
			boolean isCommitTimes = commitTime.validateOBCommitTime(info.getDTExecute(),dbInfo.getNOfficeID(),dbInfo.getNCurrencyID());
			if(isCommitTimes == false){
				fInfo.setExecuteDate(DataFormat.getNextDate(fInfo.getExecuteDate(),1));
			}
			
			fInfo.setNote(NameRef.getAbstractNameByID(info.getNAbstractID())); // 汇款用途/摘要
			fInfo.setStatus(OBConstant.SettInstrStatus.SAVE); // 指令状态
			fInfo.setConfirmDate(fInfo.getExecuteDate()); //确认日期
			fInfo.setConfirmUserID(info.getNConfirmUserID()); //确认人ID	
			fInfo.setPayerAcctID(dbInfo.getNPayerAcctID()); // 付款方账户ID				
			fInfo.setPayeeAcctID(dbInfo.getNPayeeAcctID()); //收款方账户ID	
			
			addCapitalTrans(fInfo);			
			
			//封装委托收款业务信息
			ConsignReceiveInfo cInfo = new ConsignReceiveInfo();
			cInfo.setId(info.getId());
			cInfo.setNStatus(OBConstant.SettInstrStatus.CONFIRM);
			cInfo.setNConfirmUserID(info.getNConfirmUserID());
			cInfo.setDTConfirm(fInfo.getConfirmDate());
			cInfo.setDTExecute(info.getDTExecute());
			cInfo.setSMemo(info.getSMemo());
			try{
				instrID = dao.createFinaneInfo(fInfo, cInfo);	
			} catch (Exception e) {
				throw new IException("委托收款确认失败！",e);
			}
			
			OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			
			try{
				result = obFinanceInstrDao.findByInstructionID(instrID,info.getNCurrencyID());	
			} catch (Exception e) {
				throw new IException("委托收款确认成功，查询生成的内转交易失败，请通过申请指令查询！",e);
			}
		} catch (IException ie) {
			throw ie;
		} catch (Exception e) {
			throw new IException("委托收款确认失败！",e);
		}
		return result;
	}

	
	/**
	 * 通过指令id判断是否由委托收款生成的业务
	 * @param instrID  指令id
	 * @return
	 */
	public boolean isConsignReceive (long instrID) throws IException {
		boolean flag = false;
		try {
	        ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
	        tempInfo.setNInstrID(instrID);        
	        Collection c = dao.findByCondition(tempInfo);
	        if (c != null && c.size() > 0) {
	        	flag = true;
	        }	     
		} catch (Exception e) {
			throw new IException("委托收款查询失败！",e);
		}
        return flag;        
	}
	
	/**
	 * 删除由委托收款生成的内转时，更新相应的委托收款业务
	 * @param instrID 指令id
	 * @return
	 * @throws IException
	 */
	public boolean delInstr (long instrID, long nUserID) throws IException {
		boolean flag = false;
		try {
	        ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
	        ConsignReceiveInfo info = new ConsignReceiveInfo();
	        tempInfo.setNInstrID(instrID);        
	        Collection c = dao.findByCondition(tempInfo);	        
	        if (c != null && c.size() > 0) {
	        	Iterator t = c.iterator();
	        	info = (ConsignReceiveInfo)t.next();	 
	        	if (info != null && info.getId() > 0) {
	        		 tempInfo.setId(info.getId());
	        		 tempInfo.setNInstrID(-1);   
	        		 tempInfo.setNStatus(OBConstant.SettInstrStatus.REFUSE);
	        		 tempInfo.setNConfirmUserID(nUserID);
	        		 tempInfo.setDTConfirm(Env.getSystemDate());
	        		 update(tempInfo);
	        	}
	        }	     
		} catch (Exception e) {
			throw new IException("更新委托收款业务失败！",e);
		}
        return flag;        
	}
		
	/**
	 * 分页查询
	 * @param info 查询条件
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryConsignReceiveInfo(ConsignReceiveInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		
		try {	
			pageLoader = dao.queryConsignReceiveInfo(info);
		} catch (Exception e) {
			throw new IException("查询委托收款业务失败！",e);
		}
		
		return pageLoader;
	}
	
	  
    /**
     * 保存资金划拨信息
     * 拷贝原OBFinanceInstrEJB.java中addCapitalTrans方法
     * @param FinanceInfo
     * @return long 财务交易指令流水号,如小于0表示新增或修改失败
     * @exception Exception
     */
    public long addCapitalTrans(FinanceInfo info) throws RemoteException, Exception
    {
        long lReturn = -1;
        AccountBalanceInfo accountBalanceInfo = null;
        OpenDateInfo openDateInfo = null;
       
        try
        {        	
            if (info != null)
            {
                OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
                if (info.getTransType() != OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER && info.getTransType() != OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
                {
                    accountBalanceInfo = obFinanceInstrDao.getCurrBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
                    
                    if (info.getTransType()==OBConstant.SettInstrType.APPLYCAPITAL)
                    {	
                    	//校验可用余额是否大于划拨金额
	                    log4j.info("可用余额=" + (accountBalanceInfo.getOverdraftAmount()+accountBalanceInfo.getUsableBalance()));
	                    log4j.info("划拨金额=" + info.getAmount());
	                    if ((accountBalanceInfo.getOverdraftAmount()+accountBalanceInfo.getUsableBalance())
	                    		< info.getAmount())
	                    {
	                        throw new IException("OB_EC18");
	                    }
                    }
                    else
                    {
	                    //校验可用余额是否大于划拨金额
	                    log4j.info("可用余额=" + accountBalanceInfo.getUsableBalance());
	                    log4j.info("划拨金额=" + info.getAmount());
	                    	                  
	                    if ((accountBalanceInfo.getOverdraftAmount() + accountBalanceInfo.getUsableBalance())
	                    		< info.getAmount())
	                    {
	                        throw new IException("OB_EC18");
	                    }
                    }
                   
    	        	AccountSystemInfo accountSystemInfo = new AccountSystemInfo();
    	        	accountSystemInfo.setNAccountId(info.getPayerAcctID());
    	        	double dCPF2Amount = obFinanceInstrDao.getUsableBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), info.getID());
    	        	accountSystemInfo.setDPayAmount(info.getAmount()+dCPF2Amount);
    	        	if(!new AccountSystemDelegation().isPassAccountSystemVerify(accountSystemInfo)){
    	        		throw new IException("OB_EC18");
    	        	}
                }
                
                if (info.getTransType()!=OBConstant.SettInstrType.APPLYCAPITAL)
	            {
	                //日期不能在结算开机日之前
	                openDateInfo = obFinanceInstrDao.getOpenDate(info.getOfficeID(), info.getCurrencyID());
	
	                if (openDateInfo == null)//不能取到开关机时间
	                {	                    
	                	throw new IException("OB_EC30");
	                } 
	                if (openDateInfo.getSystemStatusID() == 1)//开机
	                {
	                    if (info.getExecuteDate().getTime() - openDateInfo.getOpenDate().getTime() < 0)
	                    {
	                        log4j.info("ExecuteDate=" + info.getExecuteDate().toString());
	                        log4j.info("openDate=" + openDateInfo.getOpenDate().toString());
	                        throw new IException("OB_EC31");
	                    }
	                }
	                else
	                //关机
	                {
	                    if (info.getExecuteDate().getTime() - openDateInfo.getOpenDate().getTime() <= 0)
	                    {
	                        throw new IException("OB_EC17");
	                    }
	                }	               
	            }
               
            }
		
			if(null != info.getSignatureValue() && info.getSignatureValue().length() > 0){
				//保存签名值
				new OBFinanceInstrDao().updateSignatureInfo(info);
			}
			
        } 
        catch (Exception e)
        {        	
            log4j.error(e.toString());
            throw new IException(e.getMessage(), e);
        }

        return lReturn;

    }
	
    
	
}
