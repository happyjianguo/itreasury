package com.iss.itreasury.settlement.integration.service.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

import com.iss.itreasury.settlement.integration.serviceinterface.ISETTBankService;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class IRMIAgent extends UnicastRemoteObject{
	
	private String IP="";
	private int port=-1;
	private static Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	public IRMIAgent() throws RemoteException
    {
   	   super();
    }
	public void startBankService(String IP,int port) throws RemoteException{		
				 
			this.IP=IP;
			this.port=port;
			log.debug("IP = "+IP);
			log.debug("port = "+port);
			
			ISETTBankService stub = (ISETTBankService) UnicastRemoteObject.toStub(this);			
            try{
            	LocateRegistry.createRegistry(port);            
            }
            catch(Exception e){
            	log.info("RMI服务已经启动");
            }
            
	        Registry registry = LocateRegistry.getRegistry(port);
	        log.debug("绑定RMI名称服务");
	        registry.rebind("bsserver", stub);  
		
	}
	
}
