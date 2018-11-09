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
	 * ���˴���
	 */
	public ImpAccountResult impAccount(BSAccountInfo accountInfo) throws Exception{
		log.info("����ҵ�񷽷� impAccount ");
		
		if (accountInfo ==null){
			log.info("���˶���Ϊ��");
			throw new Exception("���˶���Ϊ��");
		}
		
		ImportAccountBean_Product impBean=new ImportAccountBean_Product();
		if (accountInfo.getTransInfos()==null){
			log.info("������ϢΪ��");
			throw new Exception("������ϢΪ��");
		}
		AccountTransInfo[] arrTransInfo=accountInfo.getTransInfos();
		
		ImpAccountResultItem[] arrItems=new ImpAccountResultItem[arrTransInfo.length];
		log.info("�յ���Ϣ��"+arrTransInfo.length+ "��");
		for (int i=0;i<arrTransInfo.length;i++){
			log.info("���ڴ���� "+i+"��");
			ImpAccountResultItem item=impBean.save(accountInfo.getReferenceCode(),accountInfo.getBranchCode(),arrTransInfo[i]);
			arrItems[i]=item;
		}
				
		ImpAccountResult rtn=new ImpAccountResult(accountInfo.getReferenceCode(),arrItems);
		log.info("�˳�ҵ�񷽷� impAccount ");
		return rtn;		
	}
	/**
	 * ��ѯ������λ���ػ�״̬
	 */
	public SystemStatusInfo querySystemStatus(String refferenceCode) throws Exception{
		try{
			SystemStatusInfo rtn=new SystemStatusInfo();
			Sett_AccountDAO dao=new Sett_AccountDAO();
			AccountInfo accountInfo=dao.findByAccountNO(refferenceCode);
			if (accountInfo==null){
				//�޴��˻�
				rtn.setStatus(0);	
				return rtn;
			}
			
			
			//�ж��Ƿ��Ѿ��ػ�
			if (EndDayProcess.getSystemStatusID(accountInfo.getOfficeID(), accountInfo.getCurrencyID()) != Constant.SystemStatus.OPEN)
			{
				log.info("ϵͳ�ػ������´����:" + accountInfo.getOfficeID() + "���֣�" + accountInfo.getCurrencyID());
				rtn.setStatus(2);
				return rtn;
				
			}
			//�ж��Ƿ����ڹػ�
			if (CloseSystemMain.getDealStatusID(accountInfo.getOfficeID(),accountInfo.getCurrencyID(),Constant.ModuleType.SETTLEMENT) == Constant.ShutDownStatus.DOING)
			{
				log.debug("ϵͳ���ڹػ������´����:" + accountInfo.getOfficeID() + "���֣�" + accountInfo.getCurrencyID());
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
		System.out.println("���뷽��checkSettTransStatus");
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
