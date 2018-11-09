package com.iss.itreasury.fcinterface.bizlogic;

import com.iss.itreasury.bankportal.integration.constant.InstructionStatus;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obsystem.dao.OBSystemDao;
import com.iss.itreasury.ebank.obsystem.dataentity.ClientCapInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.fcinterface.dao.FcinterfaceDao;
import com.iss.itreasury.fcinterface.dataentity.ExtSystemLogInfo;
import com.iss.itreasury.fcinterface.util.EPConstant;
import com.iss.itreasury.fcinterface.util.EPConstant.EPInstructionStatus;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.sap.mw.jco.*;

public class SapInstructionHandle implements JCO.ServerExceptionListener, JCO.ServerErrorListener {
	 
		static IRepository repository;
		static IRepository repository_600;
		
	    /**
	     *  This is the actual Server (Listener) object
	     */
    	static public class Server extends JCO.Server {

        public Server(String gwhost, String gwserv, String program_id, IRepository repos)
        {
            super(gwhost, gwserv, program_id, repos);
        }
				
        public Server(String args[][], IRepository repos)
        {
            super(args, repos);
        }				

        protected boolean onCheckTID(String tid)
        {
            return true;
        }
        protected void onConfirmTID(String tid)
        {
        }
        protected void onCommit(String tid)
        {
        }
        protected void onRollback(String tid)
        {
        }

        /**
         *  Called upon an incoming requests
         */
        protected void handleRequest(JCO.Function function){
        	
        	FinanceInfo financeInfo = null;
        	ClientCapInfo clientCapInfo = null;
        	ExtSystemLogInfo qInfo = null;
        	OBSystemDao OBSystemDao = new OBSystemDao();
        	FcinterfaceDao fcinterfaceDao = new FcinterfaceDao();
        	
        	try{
	            System.out.println(function.getName());
	
	            // Process incoming requests
	            if (function.getName().equals("YFI_SEND_TRANSACTIONIDN")) {
	                System.out.println(function.getName());
	                
	                //JCO.ParameterList list = function.getExportParameterList();//输出信息                
	                //取付款指令后往资金监控进行发送
	                //IFunctionTemplate ftemplate = repository.getFunctionTemplate("YFI_SEND_TRANSACTIONIDN"); 
	                
	                JCO.Table s = function.getTableParameterList().getTable("YTRAN_DAVANCE"); //SAP表名
	                int len = s.getNumRows(); 
	                System.out.println("========接收到指令行数==========="+len);
	                int j = 0 ;
	                fcinterfaceDao = new FcinterfaceDao();
	                for(int i = 0 ; i <len ; i++){	
	                	System.out.println("============================财企接口指令解析===================================");
	                	qInfo = new ExtSystemLogInfo();
	            		financeInfo = new FinanceInfo();
	            		clientCapInfo = new ClientCapInfo();
	            		
	            		//从SAP取出信息后,进行单条信息解析
	            		for (JCO.FieldIterator e = s.fields(); e.hasMoreElements(); ) {
	    	                JCO.Field field = e.nextField();
	    	                if(field.getName().equals("TRANID")){
	    	                	qInfo.setApplycode(field.getString());
	    	                }else if(field.getName().equals("SYSID")){
	    	                	qInfo.setExtSystemCode(field.getString());
	    	                }
	    	                getTransCurrentDepositInfoSap(financeInfo,clientCapInfo,field);
	    	                System.out.println(field.getName() + ":\t" + field.getString());
	    	            }
	            		
	            		//保存外部账户
	            		if(financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY){
		            		long payeeid = OBSystemDao.addPayee(clientCapInfo);
		            		if(payeeid > 0){
		            			financeInfo.setPayeeAcctID(payeeid);
		            		}else{
		            			financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"外部账号保存错误");
		            		}
	            		}
	            		
	            		//日志
	            		boolean bool = true;
	            		ExtSystemLogInfo log = new ExtSystemLogInfo();
	            		if(!financeInfo.getReturnMsg().equals(""))
	            		{
	            			bool = false;
	            			log.setApplycode(financeInfo.getApplyCode());
	            			log.setSource(financeInfo.getSource());
	            			log.setRemark(financeInfo.getReturnMsg());
	            			log.setNstatus(EPConstant.EPInstructionStatus.FAIL);
	            			fcinterfaceDao.addLog(log);
	            		}
	            		
	    	            System.out.println("-----取出单条交易号为：----" + qInfo.getApplycode());
	    	            
	    	            //判断返回错误信息是否为空，如果为空就发送信息到资金监控
	    	            if(financeInfo != null){
		    	            FcinterfaceHandle fcinterfaceHandle = new FcinterfaceHandle();
		    	            if(bool) fcinterfaceHandle.handle(financeInfo);
	    	            }
	    	            qInfo = fcinterfaceDao.queryInstruction(qInfo);
	    	            //-------------------------------------------------------------------------------------------------    	      
	    	            JCO.Table inputTable = function.getTableParameterList().getTable("YTRAN_DAVANCE_INFO");//SAP表名	
    		        	//把从资金监控传过来的信息解析出来传到SAP   		        	
	        			inputTable.insertRow(j++);
						inputTable.setRow(j-1);	
						inputTable.setValue("1","OPER");				
						inputTable.setValue(qInfo.getApplycode(),"TRANID");//交易号
						inputTable.setValue(fcinterfaceDao.getStatus(qInfo.getNstatus()),"ENNSTAS");//指令状态
						inputTable.setValue(qInfo.getRemark(),"ENNDES");//指令描述
						inputTable.setValue(qInfo.getBankStatus(),"BAKSTAS");//银企指令状态
						inputTable.setValue(getStatusName(qInfo.getBankStatus()),"BAKDES");//银企指令描述
   					    String modifytime = DataFormat.formatDate(qInfo.getCreatetime(),2);
					    System.out.println("SayPaymodifytime"+modifytime);
						inputTable.setValue(modifytime,"MODIFY_TIME");
						
						System.out.println("交易号:"+qInfo.getApplycode());
						System.out.println("指令状态:"+EPInstructionStatus.getName(qInfo.getNstatus()));
						System.out.println("指令描述:"+qInfo.getRemark());
						System.out.println("银企指令状态:"+qInfo.getBankStatus());
						System.out.println("银企指令描述:"+getStatusName(qInfo.getBankStatus()));
						//-------------------------------------------------------------------------------------------------	    	            
	    	            if(i<len-1){
	    		            s.nextRow();
	    		            }
	            	}            
	            	} else if (function.getName().equals("YFI_GET_TRANID_INFO")) {
		                System.out.println(function.getName());
		                
		                //JCO.ParameterList list = function.getExportParameterList();//输出信息    
		                
		                //查询从SAP发送到资金监控的指令状态
		                //IFunctionTemplate ftemplate = repository.getFunctionTemplate("YFI_GET_TRANID_INFO"); 
		                JCO.Table s = function.getTableParameterList().getTable("ITID"); //SAP表名	
		                
		                int len = s.getNumRows(); 
		                System.out.println("========接收到指令行数==========="+len);
		                int j = 0 ;
		                String code = "";
		                for(int i = 0 ; i <len ; i++){	
		                	System.out.println("============================财企接口指令解析===================================");
		                	qInfo = new ExtSystemLogInfo();
	    	            	//从SAP取出信息后,进行单条信息解析(解析的是查询条件)
	    	    			for (JCO.FieldIterator e = s.fields(); e.hasMoreElements(); ) {           	
		    	                JCO.Field field = e.nextField();
		    	                if(field.getName().equals("OPER")){
		    	    				
		    	    			}else if(field.getName().equals("TRANID")){
		    	    				qInfo.setApplycode(field.getString());//外部系统申请指令编号;
		    	    				code = qInfo.getApplycode();
		    	    			}else if(field.getName().equals("SYSID")){
		    	    				qInfo.setExtSystemCode(field.getString());//外部系统编号
		    	    			}
		    	                System.out.println(field.getName() + ":\t" + field.getString());
		    	            }
		    	            //查询指令信息(查询一条返回一条状态); 
	    	    			qInfo = fcinterfaceDao.queryInstruction(qInfo);
	    		           	JCO.Table inputTable = function.getTableParameterList().getTable("ETINFO");//SAP表名
		        			inputTable.insertRow(j++);
							inputTable.setRow(j-1);	
							if(qInfo == null){
								inputTable.setValue("2","OPER");//操作类型
								inputTable.setValue(code,"TRANID");//交易号
							    inputTable.setValue(fcinterfaceDao.getStatus(EPConstant.EPInstructionStatus.FAIL),"ENNSTAS");//执行情况：查询失败
							    inputTable.setValue("查询失败,没有返回结果","ENNDES"); //指令描述
							    inputTable.setValue("","BAKSTAS");//银企指令状态
								inputTable.setValue("","BAKDES");//银企指令描述
								inputTable.setValue("","MODIFY_TIME"); //改变时间
							}else{
								inputTable.setValue("2","OPER");//操作类型
								inputTable.setValue(qInfo.getApplycode(),"TRANID");//交易号 
								inputTable.setValue(fcinterfaceDao.getStatus(qInfo.getNstatus()),"ENNSTAS");//指令状态
								inputTable.setValue(qInfo.getRemark(),"ENNDES");//指令描述
								inputTable.setValue(qInfo.getBankStatus(),"BAKSTAS");//银企指令状态
								inputTable.setValue(getStatusName(qInfo.getBankStatus()),"BAKDES");//银企指令描述
		 				        String modifytime = DataFormat.formatDate(qInfo.getCreatetime(),2);
								System.out.println("Querymodifytime"+modifytime);
								inputTable.setValue(modifytime,"MODIFY_TIME"); //改变时间
								
								System.out.println("交易号:"+qInfo.getApplycode());
								System.out.println("指令状态:"+EPInstructionStatus.getName(qInfo.getNstatus()));
								System.out.println("指令描述:"+qInfo.getRemark());
								System.out.println("银企指令状态:"+qInfo.getBankStatus());
								System.out.println("银企指令描述:"+getStatusName(qInfo.getBankStatus()));
								//-------------------------------------------------------------------------------------------------	    	            
	 	    	              }
							if(i<len-1){
								s.nextRow();
							}
	 	    	        }
		                } else {
		                // Otherwise
		                throw new JCO.AbapException("NOT_SUPPORTED","This service is not implemented by the external server");
	            	}        		
	        }catch(Exception e){
	        	e.printStackTrace();
	        	}
	        }
        
        /*
         * 对SAP的指令进行转换
         */
		private void getTransCurrentDepositInfoSap(FinanceInfo financeInfo,ClientCapInfo clientCapInfo,JCO.Field field) throws Exception {
			
			FcinterfaceDao fcinterfaceDao = new FcinterfaceDao();
			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
			if(field.getName().equals("SYSID")){
				long lSource = fcinterfaceDao.getExtSystemIDByCode(field.getString());
				if(lSource > 0 ){
					financeInfo.setSource(lSource);
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"外部系统不存在 ");
				}
				System.out.println("外部系统:"+field.getString());
				
			}else if(field.getName().equals("TRANID")){
				if(field.getString()!=null && field.getString().length()>0){
					financeInfo.setApplyCode(field.getString());
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"申请指令号为空 ");
				}
 				System.out.println("申请指令号:"+field.getString());
 				
			}else if(field.getName().equals("BUKRS")){
				long clientID = fcinterfaceDao.getOBClientIDByCode(field.getString());//获得组织机构编号
				if(clientID > 0){
					clientCapInfo.setClientID(clientID);
					financeInfo.setClientID(clientID);
					financeInfo.setOfficeID(1);
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"获得组织机构编号无效 ");
				}
				System.out.println("公司代码:"+field.getString());
				
			}else if(field.getName().equals("TRANTYP")){
				if(field.getString()!=null && field.getString().length()>0){
					if(field.getString().equals("YHFK"))//银行付款
					{
						financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
						financeInfo.setRemitType(OBConstant.SettRemitType.BANKPAY);
					}
					else if(field.getString().equals("NZ"))//内部转账
					{
						financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
						financeInfo.setRemitType(OBConstant.SettRemitType.INTERNALVIREMENT);
					}
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"交易类型为空 ");
				}
				System.out.println("交易类型:"+field.getString());
				
			}else if(field.getName().equals("WAERS")){//币种
				if(field.getString()!=null && field.getString().length()>0){
					if(field.getString().equals("RMB")){
						financeInfo.setCurrencyID(Constant.CurrencyType.RMB);
						clientCapInfo.setCurrencyID(Constant.CurrencyType.RMB);
					}
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"币种为空 ");
				}
				System.out.println("币种:"+field.getString());
				
			}else if(field.getName().equals("WRBTR")){//金额
				if(field.getString()!=null && field.getString().length()>0){
					financeInfo.setAmount(Double.parseDouble(field.getString()));
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"金额为空 ");
				}
				System.out.println("金额:"+field.getString());
				
			}else if(field.getName().equals("SEDDAY")){//执行日
				financeInfo.setExecuteDate(DataFormat.getDateTime(field.getString()));
				System.out.println("执行日:"+field.getString());
				
			}else if(field.getName().equals("FBANKA")){//付款方银行名称
				
			}else if(field.getName().equals("BKNPAY")){//付款方账号
				if(sett_AccountDAO.findByAccountNO(field.getString())==null){
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"付款方账号不存在 ");
				}else{
					financeInfo.setPayerAcctID(sett_AccountDAO.findByAccountNO(field.getString()).getAccountID());
				}
				System.out.println("付款方账号:"+field.getString());
				
			}else if(field.getName().equals("NAME1")){//收款方名称
				if(field.getString()!=null && field.getString().length()>0){
					clientCapInfo.setPayeeName(field.getString());
					clientCapInfo.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//非中油内部账户
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"收款方名称为空 ");
				}
				System.out.println("收款方名称:"+field.getString());
				
			}else if(field.getName().equals("BRNCH")){//收款方账号
				if(field.getString()!=null && field.getString().length()>0){
					
					if(financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY){//交易类型
						clientCapInfo.setPayeeAccoutNO(field.getString());
					}else{
						if(sett_AccountDAO.findByAccountNO(field.getString())==null){
							financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"内部收款方账号不存在 ");
						}else{
							financeInfo.setPayeeAcctID(sett_AccountDAO.findByAccountNO(field.getString()).getAccountID());
						}
					}
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"收款方账号为空 ");
				}
				System.out.println("收款方账号:"+field.getString());
				
			}else if(field.getName().equals("BANKA")){//收款方开户行名称
				if(field.getString()!=null && field.getString().length()>0){
					clientCapInfo.setPayeeBankName(field.getString());
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"收款方开户行名称为空 ");
				}
				System.out.println("收款方开户行名称:"+field.getString());
				
			}else if(field.getName().equals("BEZEI")){//收款方所在省
				if(field.getString()!=null && field.getString().length()>0){
					clientCapInfo.setPayeeProv(field.getString());
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"收款方所在省为空 ");
				}
				System.out.println("收款方所在省:"+field.getString());
				
			}else if(field.getName().equals("ORT01")){//收款方所在市
				if(field.getString()!=null && field.getString().length()>0){
					clientCapInfo.setCity(field.getString());
				}else{
					financeInfo.setReturnMsg(financeInfo.getReturnMsg()+"收款方所在市为空 ");
				}
				System.out.println("收款方所在市:"+field.getString());
				
			}else if(field.getName().equals("CNAPS")){//收款方的CNAPS
				clientCapInfo.setSPayeeBankCNAPSNO(field.getString());
				System.out.println("收款方的CNAPS:"+field.getString());
				
			}else if(field.getName().equals("UNIT_S")){//收款方联行号
				clientCapInfo.setSPayeeBankExchangeNO(field.getString());
				System.out.println("收款方联行号:"+field.getString());
				
			}else if(field.getName().equals("ORGANIZATION_S")){//收款方机构号
				clientCapInfo.setSPayeeBankOrgNO(field.getString());
				System.out.println("收款方机构号:"+field.getString());
				
			}else if(field.getName().equals("USNAM")){//提交人
				financeInfo.setConfirmUserID(EPConstant.MachineUser.OBInputUser);//机制
				clientCapInfo.setInputUserID(EPConstant.MachineUser.OBInputUser);//机制
				System.out.println("提交人:"+field.getString());
				
			}else if(field.getName().equals("MODIFY_TIME")){//发送时间
				financeInfo.setConfirmDate(DataFormat.getDateTime(field.getString()));
				System.out.println("发送时间："+field.getString());
				
			}else if(field.getName().equals("MESS")){//摘要
				financeInfo.setNote(field.getString());
				System.out.println("摘要："+field.getString());
			}
			
		}
		
		/*
		 * 取指令状态
		 */
		private String getStatusName(long statusID) {
			String strStatus = "";
			if(statusID==InstructionStatus.CANCELLED){
				strStatus = "撤销";
			}else if(statusID==InstructionStatus.SAVEDNOTSEND){
				strStatus = "已保存未发送";
			}else if(statusID==InstructionStatus.SENTPROCESSING){
				strStatus = "已发送待处理";
			}else if(statusID==InstructionStatus.SUCCEED){
				strStatus = "成功";
			}else if(statusID==InstructionStatus.FAILED){
				strStatus = "失败";
			}else if(statusID==InstructionStatus.UNKNOWENED){
				strStatus = "未知";
			}else if(statusID==InstructionStatus.CONSTRUCTFAILED){
				strStatus = "指令创建失败";
			}else if(statusID==InstructionStatus.EXISTED){
				strStatus = "指令已存在";
			}else if(statusID==InstructionStatus.SYSTEMNOTREG){
				strStatus = "系统未注册";
			}else if(statusID==InstructionStatus.NOTEXIST){
				strStatus = "指令不存在";
			}
			return strStatus;
		}
    }
    
    /** 
     *  Called if an exception was thrown anywhere in our server
     */
    public void serverExceptionOccurred(JCO.Server srv, Exception ex)
    {
        System.out.println("Exception in Server " + srv.getProgID() + ":\n" + ex);
        ex.printStackTrace();
    }

    /** 
     *  Called if an error was thrown anywhere in our server
     */
    public void serverErrorOccurred(JCO.Server srv, Error err)
    {
        System.out.println("Error in Server " + srv.getProgID() + ":\n" + err);
        err.printStackTrace();
    }

    // System IDs of the system that we gonna using be for dictionary calls
    String POOL_A = "SYSTEM_A";
    String POOL_B = "SYSTEM_B";

    // The server objects that actually handles the request
    int MAX_SERVERS = 4;
    Server servers[] = new Server[MAX_SERVERS];

    /**
     *  Constructor. Creates a client pool, the repository and a server.
     */
    public SapInstructionHandle()
    {    
    	String sapclient = "";
    	String userid = "";
    	String password = "";
    	String language = "";
    	String hostname = "";
    	String systemnumber = "";
    	String systemcode = "";
    	String programid = "";
    	String unicode   = "";
    	
    	//800机器
    	sapclient = Env.getSAP_CLIENT();
    	userid = Env.getSAP_USERID();
    	password = Env.getSAP_PASSWORD();
    	language = Env.getSAP_LANGUAGE();
    	hostname = Env.getSAP_HOSTNAME();
    	systemnumber = Env.getSAP_SYSTEMNUMBER();
    	systemcode = Env.getSAP_SYSTEMCODE();
    	programid = Env.getSAP_PROGRAMID();
    	unicode = Env.getSAP_UNICODE(); 
    	
    	JCO.addClientPool(POOL_A,3,sapclient,userid,password,language,hostname,systemnumber);

        repository = JCO.createRepository("SYSTEM_A", POOL_A );
        String[][] login_params  = 
		{
	        {"gwhost", hostname},			//host name
	        {"gwserv", systemcode},		//服务器标识
	        {"progid", programid },			
	        {"unicode", unicode},			//编码
	    };
        
		 servers[0] = new Server((String[][])login_params, repository);
		 servers[1] = new Server((String[][])login_params, repository);
		
		//600机器
		sapclient = Env.getSAP_CLIENT_1();
    	userid = Env.getSAP_USERID_1();
    	password = Env.getSAP_PASSWORD_1();
    	language = Env.getSAP_LANGUAGE_1();
    	hostname = Env.getSAP_HOSTNAME_1();
    	systemnumber = Env.getSAP_SYSTEMNUMBER_1();
    	systemcode = Env.getSAP_SYSTEMCODE_1();
    	programid = Env.getSAP_PROGRAMID_1();
    	unicode = Env.getSAP_UNICODE_1(); 
    	
    	JCO.addClientPool(POOL_B,3,sapclient,userid,password,language,hostname,systemnumber);

    	repository_600 = JCO.createRepository("SYSTEM_B", POOL_B );
        String[][] login_params_600  = 
		{
	        {"gwhost", hostname},			//host name
	        {"gwserv", systemcode},		//服务器标识
	        {"progid", programid },			
	        {"unicode", unicode},			//编码
	    };
        
		 servers[2] = new Server((String[][])login_params_600, repository_600);
		 servers[3] = new Server((String[][])login_params_600, repository_600);

        JCO.addServerExceptionListener(this);
        JCO.addServerErrorListener(this);
    }

    /**
     *  Start the server
     */
    public void startServers()
    {
        try {
            for (int i = 0; i < MAX_SERVERS; i++) servers[i].start();
            System.out.println("=============SAP服务监控已启动==============");
        }
        catch (Exception ex) {
            System.out.println("Could not start servers !\n" + ex);
        }//try
    }

    /**
     *  Simple main program driver
     */
    public static void main(String[] argv)
    {
    	SapInstructionHandle obj = new SapInstructionHandle();
        obj.startServers();
    }
    
    /**
     *启动服务的时候进行初始
     */
    public static void init()
    {
    	SapInstructionHandle obj = new SapInstructionHandle();
        obj.startServers();
    }
     
    
	/**
	 * 截取字符串（按字节截取）
	 * @param str
	 * @return
	 */
	public static long getCharLength(String str){
	     int charLen=0;
		  for(int i=0,len=str.length();i<len;i++){
		       if(str.charAt(i)>255){
		        charLen+=2;
		    }else{
		        charLen+=1;
		    }  
		  }
	  return charLen;
	}

}
