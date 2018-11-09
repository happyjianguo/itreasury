package com.iss.itreasury.settlement.integration.client;

import com.iss.itreasury.settlement.integration.serviceinterface.ISETTBankService;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResult;
import com.iss.itreasury.settlement.integration.client.info.BSAccountInfo;
import com.iss.itreasury.settlement.integration.client.info.SettResultInfo;
import com.iss.itreasury.settlement.integration.client.info.SettTransInfo;
import com.iss.itreasury.settlement.integration.client.info.SystemStatusInfo;
import java.rmi.Naming;
/**
 * 
 * @author zpli
 *
 */
public class RMIBankServiceClient {	
	
	private ISETTBankService service=null;	
	private static String IP="";
	private static long port=-1;
	private static RMIBankServiceClient client=new RMIBankServiceClient();	
	
	public static RMIBankServiceClient getInstance() throws Exception{
		if (client==null)
			client=new RMIBankServiceClient();
		return client;
	}	
	/**
	 * 
	 * @param IP
	 * @param port
	 * @throws Exception
	 */
	public static void init(String IP,int port) throws Exception {
		
		RMIBankServiceClient.IP=IP;
		RMIBankServiceClient.port=port;
		System.out.println("结算服务初始化");
	}
	
	public ImpAccountResult impAccount(BSAccountInfo info) throws Exception{
		
		System.out.println("结算入账开始");
		ImpAccountResult rtn=null;		
		beforeTrans();
		rtn=service.impAccount(info);		
		afterTrans();
		System.out.println("结算入账结束");
		System.out.println(rtn.getImpAccountResultItem());
		System.out.println(rtn.getReferenceCode());

		return rtn;

	}
	/**
	 * 
	 * @param refferenceCode
	 * @return
	 * @throws Exception
	 */
	public SystemStatusInfo querySystemStatus(String refferenceCode)
			throws Exception {
		System.out.println("开始查询客户开关机状态");
		SystemStatusInfo rtn = null;
		beforeTrans();
		rtn = service.querySystemStatus(refferenceCode);
		afterTrans();
		System.out.println("结束查询客户开关机状态");
		System.out.println(rtn.getStatus());
		System.out.println(rtn.getOpenSystemDate());
		return rtn;
	}

	/**
	 * 调用服务前的准备工作
	 * 
	 * @throws Exception
	 */
	private void beforeTrans() throws Exception{		
		checkParam();		
		try{
			connect();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("RMI连接失败");
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void connect() throws Exception{	
		System.out.println("RMI连接");		
		service = (ISETTBankService)Naming.lookup("rmi://"+IP+":"+port+"/bsserver");		
	}
	/**
	 * 检验RMI连接是否可用
	 * @return
	 */
	private void checkParam() throws Exception{
		System.out.println("参数检查");
		if(IP==null || IP=="")
			throw new Exception("资金监控服务IP未设置");
		if(port<=0)
			throw new Exception("资金监控服务端口未设置");		
		return;
	}	
	private void afterTrans() throws Exception{
		System.out.println("断开连接");
		//TODO: 断开RMI连接
	}
	/**
	 * 
	 * @param refferenceCode
	 * @return
	 * @throws Exception
	 */
	public SettResultInfo checkSettTransStatus(SettTransInfo info)
			throws Exception {
		System.out.println("开始检查业务数据状态");
		SettResultInfo rtn = null;
		beforeTrans();
		rtn = service.checkSettTransStatus(info);
		afterTrans();
		System.out.println("结束查询业务数据状态");
		System.out.println(rtn.getStatus());
		System.out.println(rtn.getTransNo());
		return rtn;
	}
	

}
