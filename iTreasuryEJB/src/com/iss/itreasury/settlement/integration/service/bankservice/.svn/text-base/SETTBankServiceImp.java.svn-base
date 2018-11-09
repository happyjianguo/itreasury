package com.iss.itreasury.settlement.integration.service.bankservice;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResult;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResultItem;
import com.iss.itreasury.settlement.integration.client.info.SettResultInfo;
import com.iss.itreasury.settlement.integration.client.info.SettTransInfo;
import com.iss.itreasury.settlement.integration.client.info.SystemStatusInfo;
import com.iss.itreasury.settlement.integration.service.rmi.IRMIAgent;
import com.iss.itreasury.settlement.integration.serviceinterface.ISETTBankService;
import com.iss.itreasury.settlement.integration.client.constant.ResultStatus;
import com.iss.itreasury.settlement.integration.client.info.BSAccountInfo;
import com.iss.itreasury.settlement.bankinstruction.instructionbean.ImportAccountBean_Product;
import com.iss.itreasury.settlement.integration.client.info.AccountTransInfo;
import com.iss.itreasury.settlement.integration.client.constant.ResultStatus;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * 
 * @author zpli
 *
 */
public class SETTBankServiceImp extends IRMIAgent implements ISETTBankService {
	private static Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	public SETTBankServiceImp() throws RemoteException
    {
   	   super();
    }
	/**
	 * 入账处理
	 */
	public ImpAccountResult impAccount(BSAccountInfo accountInfo) throws Exception{
		log.info("进入业务方法 impAccount ");
		
		if (accountInfo ==null){
			log.info("入账对象为空");
			throw new Exception("入账对象为空");
		}
		
		ImportAccountBean_Product impBean=new ImportAccountBean_Product();
		if (accountInfo.getTransInfos()==null){
			log.info("入账信息为空");
			throw new Exception("入账信息为空");
		}
		AccountTransInfo[] arrTransInfo=accountInfo.getTransInfos();
		
		ImpAccountResultItem[] arrItems=new ImpAccountResultItem[arrTransInfo.length];
		log.info("收到信息："+arrTransInfo.length+ "条");
		for (int i=0;i<arrTransInfo.length;i++){
			log.info("正在处理第 "+i+"条");
			ImpAccountResultItem item=impBean.save(accountInfo.getReferenceCode(),accountInfo.getBranchCode(),arrTransInfo[i]);
			arrItems[i]=item;
		}
				
		ImpAccountResult rtn=new ImpAccountResult(accountInfo.getReferenceCode(),arrItems);
		log.info("退出业务方法 impAccount ");
		return rtn;		
	}
	/**
	 * 查询下属单位开关机状态
	 */
	public SystemStatusInfo querySystemStatus(String refferenceCode) throws Exception{
		try{
			SystemStatusInfo rtn=new SystemStatusInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			AccountInfo accountInfo=dao.findByAccountNO(refferenceCode);
			if (accountInfo==null){
				//无此账户
				rtn.setStatus(0);	
				return rtn;
			}
			
			
			//判断是否已经关机
			if (EndDayProcess.getSystemStatusID(accountInfo.getOfficeID(), accountInfo.getCurrencyID()) != Constant.SystemStatus.OPEN)
			{
				log.info("系统关机，办事处编号:" + accountInfo.getOfficeID() + "币种：" + accountInfo.getCurrencyID());
				rtn.setStatus(2);
				return rtn;
				
			}
			//判断是否正在关机
			if (CloseSystemMain.getDealStatusID(accountInfo.getOfficeID(),accountInfo.getCurrencyID(),Constant.ModuleType.SETTLEMENT) == Constant.ShutDownStatus.DOING)
			{
				log.debug("系统正在关机，办事处编号:" + accountInfo.getOfficeID() + "币种：" + accountInfo.getCurrencyID());
				rtn.setStatus(2);
				return rtn;
			}
			Timestamp openDate=Env.getSystemDate(accountInfo.getOfficeID(),accountInfo.getCurrencyID());
			rtn.setStatus(1);
			rtn.setOpenSystemDate(openDate);
			return rtn;
		}
		catch (Exception e){
			e.printStackTrace();
			SystemStatusInfo rtn=new SystemStatusInfo();
			return rtn;
		}
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.settlement.integration.serviceinterface.ISETTBankService#checkSettTransStatus(com.iss.itreasury.settlement.integration.client.info.SettTransInfo)
	 */
	public SettResultInfo checkSettTransStatus(SettTransInfo info)
			throws Exception {
		System.out.println("进入方法checkSettTransStatus");
		SettResultInfo rtnInfo = null;
		try{
			QTransaction qObj = new QTransaction();
			rtnInfo = qObj.getTransactionInfo(info);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return rtnInfo;
	}
}
