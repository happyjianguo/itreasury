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
		System.out.println("��������ʼ��");
	}
	
	public ImpAccountResult impAccount(BSAccountInfo info) throws Exception{
		
		System.out.println("�������˿�ʼ");
		ImpAccountResult rtn=null;		
		beforeTrans();
		rtn=service.impAccount(info);		
		afterTrans();
		System.out.println("�������˽���");
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
		System.out.println("��ʼ��ѯ�ͻ����ػ�״̬");
		SystemStatusInfo rtn = null;
		beforeTrans();
		rtn = service.querySystemStatus(refferenceCode);
		afterTrans();
		System.out.println("������ѯ�ͻ����ػ�״̬");
		System.out.println(rtn.getStatus());
		System.out.println(rtn.getOpenSystemDate());
		return rtn;
	}

	/**
	 * ���÷���ǰ��׼������
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
			throw new Exception("RMI����ʧ��");
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void connect() throws Exception{	
		System.out.println("RMI����");		
		service = (ISETTBankService)Naming.lookup("rmi://"+IP+":"+port+"/bsserver");		
	}
	/**
	 * ����RMI�����Ƿ����
	 * @return
	 */
	private void checkParam() throws Exception{
		System.out.println("�������");
		if(IP==null || IP=="")
			throw new Exception("�ʽ��ط���IPδ����");
		if(port<=0)
			throw new Exception("�ʽ��ط���˿�δ����");		
		return;
	}	
	private void afterTrans() throws Exception{
		System.out.println("�Ͽ�����");
		//TODO: �Ͽ�RMI����
	}
	/**
	 * 
	 * @param refferenceCode
	 * @return
	 * @throws Exception
	 */
	public SettResultInfo checkSettTransStatus(SettTransInfo info)
			throws Exception {
		System.out.println("��ʼ���ҵ������״̬");
		SettResultInfo rtn = null;
		beforeTrans();
		rtn = service.checkSettTransStatus(info);
		afterTrans();
		System.out.println("������ѯҵ������״̬");
		System.out.println(rtn.getStatus());
		System.out.println(rtn.getTransNo());
		return rtn;
	}
	

}
