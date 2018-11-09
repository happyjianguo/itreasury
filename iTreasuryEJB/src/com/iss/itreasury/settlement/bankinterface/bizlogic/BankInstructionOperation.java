package com.iss.itreasury.settlement.bankinterface.bizlogic;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.bankportal.integration.info.ReqInstructionInfo;
import com.iss.itreasury.bankportal.integration.info.RespInstructionInfo;
import com.iss.itreasury.settlement.account.bizlogic.Account;
import com.iss.itreasury.settlement.account.bizlogic.AccountHome;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.TransBankDetailInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Config; 
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.bankportal.integration.constant.RemitPriority;
import com.iss.itreasury.bankportal.integration.client.BPClientAgent;
import com.iss.itreasury.bankportal.integration.constant.InstructionStatus;
import com.iss.itreasury.bankportal.integration.info.ReqGetGeneralBankAcctInfo;
import com.iss.itreasury.bankportal.integration.info.RespQueryCurTransInfo;
import com.iss.itreasury.bankportal.integration.info.ReqQueryCurTransInfo;
import com.iss.itreasury.bankportal.integration.info.RespQueryHisTransInfo;
import com.iss.itreasury.bankportal.integration.info.ReqQueryHisTransInfo;
/**
 * @author rongyang
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of
 * type comments go to Window>Preferences>Java>Code Generation.
 */
public class BankInstructionOperation
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private String strAgentIP="10.10.19.164";
	private int iAgentPort=2005;
	private static ArrayList instructionList = new ArrayList(16);
	
	/**
	 * Constructor for BankService.
	 */
	public BankInstructionOperation()
	{
		super();
	}
	public void addBankInstruction(TransBankDetailInfo transInfo)
		throws Exception
	{//log.debug("----------当前系统设置可以使用银企直连-----------");
		long bankID = transInfo.getPayBranchID();
		Sett_BranchDAO branchDAO = new Sett_BranchDAO();
		BranchInfo bankInfo = null;
		bankInfo = branchDAO.findByID(bankID);
		log.info("bankid = "+bankID);
		AccountInfo  accountInfo = new AccountInfo();
		if(transInfo.getPayAccountID() > 0)
		{
			  long accountPayId = -1;
			  accountPayId = transInfo.getPayAccountID();
			  AccountHome home =
				(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
			  Account accounthome = (Account) home.create();
			  accountInfo = accounthome.findAccountByID(accountPayId);
		}
		
		
		
		//当指定开户行不存在、指定开户行无银行类型、无银行账户号，交易中无收款方的账户信息则不生成指令
		if (bankInfo != null
			&& bankInfo.getBankTypeID() != -1
			&& bankInfo.getBankAccountCode() != null
			&& !("".equals(bankInfo.getBankAccountCode()))
			&& transInfo.getReceiveAccountNo() != null
			&& !("".equals(transInfo.getReceiveAccountNo())))
		{			
			log.info("银行信息："+UtilOperation.dataentityToString(bankInfo));
			long bankTypeID = bankInfo.getBankTypeID();			
			
			BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
			
			if (transInfo.getPayReferenceCode()!=null && !transInfo.getPayReferenceCode().equals("")){
				bankInstructionInfo.setPayAccountNo(transInfo.getPayReferenceCode());//付款方的内部关联号	
			}
			else{			
				bankInstructionInfo.setPayBankAccountNO(bankInfo.getBankAccountCode());				
				bankInstructionInfo.setPayAcctBankCode(bankInfo.getBranchCode());				
				
			}
			bankInstructionInfo.setReceiveReferenceCode(transInfo.getReceiveAccountReferenceCode());
			if(transInfo.getReceiveAccountReferenceCode()==null || transInfo.getReceiveAccountReferenceCode()==""){
				log.info("对外");
				bankInstructionInfo.setReceiveAccountNo(transInfo.getReceiveAccountNo());

				bankInstructionInfo.setReceiveAccountNo(
						transInfo.getReceiveAccountNo());
				bankInstructionInfo.setReceiveAccountName(
						transInfo.getReceiveAccountName());
				bankInstructionInfo.setReceiveBranchName(
						transInfo.getReceiveBranchName());
				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(
						transInfo.getReceiveBranchAreaNameOfProvince());
				bankInstructionInfo.setReceiveBranchAreaNameOfCity(
					transInfo.getReceiveBranchAreaNameOfCity());			
			}
			else
			{
				log.info("对内");				
				bankInstructionInfo.setReceiveReferenceCode(transInfo.getReceiveAccountReferenceCode());				
			}
			
			bankInstructionInfo.setAmount(transInfo.getAmount());			
			bankInstructionInfo.setReceiveDepartmentName(
				transInfo.getReceiveDepartmentName());				
			bankInstructionInfo.setAbstract(transInfo.getAbstract());
			bankInstructionInfo.setTransactionNo(transInfo.getTransactionNo());
			bankInstructionInfo.setTransType(transInfo.getTransType());
			bankInstructionInfo.setCreateTime(
					Env.getSystemDateTime());
			bankInstructionInfo.setReceiveBank(bankTypeID);
			//新增字段的设置
			bankInstructionInfo.setPayAreaNameOfProvince(
				bankInfo.getBranchProvince());
			bankInstructionInfo.setPayAreaNameOfCity(bankInfo.getBranchCity());
			bankInstructionInfo.setCurrencyID(bankInfo.getCurrencyID()); //交易币种
			bankInstructionInfo.setRemitPriority(
					RemitPriority.NORMAL);
			bankInstructionInfo.setSenderNo(
				String.valueOf(transInfo.getCreateUserID()).toString());			
			
			//新增中行需要的联行号，机构号
			bankInstructionInfo.setPayBankExchangeCode(bankInfo.getBankExchangeCode());
        	bankInstructionInfo.setPayBranchCodeOfBank(bankInfo.getBranchCodeOfBank());
        	bankInstructionInfo.setReceiveBankExchangeCode(transInfo.getReceiveBankExchangeCode());
        	bankInstructionInfo.setReceiveBranchCodeOfBank(transInfo.getReceiveBranchCodeOfBank());
        	bankInstructionInfo.setPayChargeAccountNo(transInfo.getPayChargeAccountNo());
        	bankInstructionInfo.setPayChargeBankExchangeCode(transInfo.getPayChargeBankExchangeCode());
        	bankInstructionInfo.setPayChargeBranchCodeOfBank(transInfo.getPayChargeBranchCodeOfBank());
        	
			if (bankInfo.getIsAutoVirementByBank() == Constant.TRUE)
			{
				log.debug("----------当前开户行设置允许通过接口发送指令-----------");
				//自动发送的指令发送人设置为交易复核人
				bankInstructionInfo.setSenderNo(
					String.valueOf(transInfo.getCreateUserID()));
				//			try
				//			{
				//				BankInstruction biz = new BankInstruction();
				//				
				//				biz.postBankInstruction(biz.findBankInstructionByID(id));
				//			}
				//			catch(Exception e)
				//			{
				//				log.error("发送银行指令失败。");
				//				e.printStackTrace();
				//			}
				//将当前要自动发送的指令的id保存在当前类的静态变量中。由系统后
				//台的自动任务定时读取执行发送
			}
			addBankInstruction(bankInstructionInfo);
		}
		//当为总账发送指令时
		else if (bankInfo == null  && transInfo != null)
		{
			log.info("当为总账发送指令时");
			//long bankTypeID = bankInfo.getBankTypeID();			
			
			BankInstructionInfo bankInstructionInfo = new BankInstructionInfo();
			bankInstructionInfo.setPayAccountNo(transInfo.getPayReferenceCode());
			bankInstructionInfo.setCurrencyID(transInfo.getCurrencyID());
			bankInstructionInfo.setPayAcctBankCode(String.valueOf(transInfo.getPayAccountID()));
			
			//bankInstructionInfo.setPayBranchName(bankInfo.getBranchName());
			if(transInfo.getReceiveAccountReferenceCode()==null || transInfo.getReceiveAccountReferenceCode()==""){
				//对外
				bankInstructionInfo.setReceiveAccountNo(transInfo.getReceiveAccountNo());

				bankInstructionInfo.setReceiveAccountNo(
						transInfo.getReceiveAccountNo());
				bankInstructionInfo.setReceiveAccountName(
						transInfo.getReceiveAccountName());
				bankInstructionInfo.setReceiveBranchName(
						transInfo.getReceiveBranchName());
				bankInstructionInfo.setReceiveBranchAreaNameOfProvince(
						transInfo.getReceiveBranchAreaNameOfProvince());
				bankInstructionInfo.setReceiveBranchAreaNameOfCity(
					transInfo.getReceiveBranchAreaNameOfCity());			
			}
			else
			{
				//对内
				bankInstructionInfo.setReceiveAccountNo(transInfo.getReceiveAccountReferenceCode());
			}
			
			bankInstructionInfo.setAmount(transInfo.getAmount());			
			bankInstructionInfo.setReceiveDepartmentName(
				transInfo.getReceiveDepartmentName());				
			bankInstructionInfo.setAbstract(transInfo.getAbstract());
			bankInstructionInfo.setTransactionNo(transInfo.getTransactionNo());
			bankInstructionInfo.setTransType(transInfo.getTransType());
			bankInstructionInfo.setCreateTime(
					Env.getSystemDateTime());
			//bankInstructionInfo.setReceiveBank(transInfo);
			//新增字段的设置
			//bankInstructionInfo.setPayAreaNameOfProvince(bankInfo.getBranchProvince());
			//bankInstructionInfo.setPayAreaNameOfCity(bankInfo.getBranchCity());
			//bankInstructionInfo.setCurrencyID(bankInfo.getCurrencyID()); //交易币种
			bankInstructionInfo.setRemitPriority(RemitPriority.NORMAL);
			bankInstructionInfo.setSenderNo(
				String.valueOf(transInfo.getCreateUserID()).toString());			
			
			//新增中行需要的联行号，机构号
			//bankInstructionInfo.setPayBankExchangeCode(bankInfo.getBankExchangeCode());
        	//bankInstructionInfo.setPayBranchCodeOfBank(bankInfo.getBranchCodeOfBank());
        	bankInstructionInfo.setReceiveBankExchangeCode(transInfo.getReceiveBankExchangeCode());
        	bankInstructionInfo.setReceiveBranchCodeOfBank(transInfo.getReceiveBranchCodeOfBank());
        	bankInstructionInfo.setPayChargeAccountNo(transInfo.getPayChargeAccountNo());
        	bankInstructionInfo.setPayChargeBankExchangeCode(transInfo.getPayChargeBankExchangeCode());
        	bankInstructionInfo.setPayChargeBranchCodeOfBank(transInfo.getPayChargeBranchCodeOfBank());
        	
			//if (bankInfo.getIsAutoVirementByBank() == Constant.TRUE)
			//{
				log.debug("----------当前开户行设置允许通过接口发送指令-----------");
				//自动发送的指令发送人设置为交易复核人
				bankInstructionInfo.setSenderNo(String.valueOf(transInfo.getCreateUserID()));
				//			try
				//			{
				//				BankInstruction biz = new BankInstruction();
				//				
				//				biz.postBankInstruction(biz.findBankInstructionByID(id));
				//			}
				//			catch(Exception e)
				//			{
				//				log.error("发送银行指令失败。");
				//				e.printStackTrace();
				//			}
				//将当前要自动发送的指令的id保存在当前类的静态变量中。由系统后
				//台的自动任务定时读取执行发送
			//}
			addBankInstruction(bankInstructionInfo);
		}
		else
		{
			log.debug("-------对应付款方开会行信息不满足生成指令的条件-------");
		}
	}
	/**
	 * 接收结算系统发送的发送指令，对指令信息进行校验。 将指令保存到表Sett_BankInstruction中 将指令信息以消息的形式进行发送。
	 * 
	 * @param BankInstructionInfo
	 *            银行转账指令数据类
	 * @return long 指令记录的数据库id
	 */
	public void addBankInstruction(BankInstructionInfo bankInstructionInfo)
		throws Exception
	{
		if (bankInstructionInfo == null)
		{
			return;
		}		
		long bankID = bankInstructionInfo.getReceiveBank();
				
		//保存银行指令
		
		bankInstructionInfo.setCreateTime(
				Env.getSystemDateTime());
		bankInstructionInfo.setStatusID(
			SETTConstant.BankInstructionStatus.SAVED);
		//调用DAO
		log.debug(UtilOperation.dataentityToString(bankInstructionInfo));
		this.postInstruction(this.converTrasInfoToInstruction(bankInstructionInfo));
		//log.debug("-------保存银行转账指令成功-------");
	}
	
	/**
	 * 接收结算系统发送的发送指令，对指令信息进行校验。 将指令保存到表Sett_BankInstruction中 将指令信息以消息的形式进行发送。(批量发指令)
	 * @param colInstructionParams
	 * @throws Exception
	 */
	public void addBankInstructions(Collection colInstructionParams)
	throws Exception
{
	if (colInstructionParams == null)
	{
		return;
	}		
	//调用DAO
	this.postInstructions(this.converTrasInfoToInstructions(colInstructionParams));
	//log.debug("-------保存银行转账指令成功-------");
}
	/**
	 * 接收结算系统发送的发送指令，对指令信息进行校验。 将指令保存到表Sett_BankInstruction中 将指令信息以消息的形式进行发送。
	 * 
	 * @param BankInstructionInfo
	 *            银行转账指令数据类
	 * @param Connection
	 *            事务连接
	 * @return long 指令记录的数据库id
	 */
	public void addBankInstruction(
		BankInstructionInfo bankInstructionInfo,
		Connection conn)
		throws Exception
	{
		if (bankInstructionInfo == null)
		{
			return;
		}
		if (!Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false))
			return;
		long bankID = bankInstructionInfo.getReceiveBank();
		
		
		//保存银行指令		
		bankInstructionInfo.setCreateTime(
				Env.getSystemDateTime());
		bankInstructionInfo.setStatusID(
			SETTConstant.BankInstructionStatus.SAVED);
		//调用DAO
		log.debug(UtilOperation.dataentityToString(bankInstructionInfo));
		this.postInstruction(this.converTrasInfoToInstruction(bankInstructionInfo));
		log.debug("-------保存银行转账指令成功-------");
	}
	
   
	public RespInstructionInfo postInstruction(ReqInstructionInfo instruction) throws Exception{
		log.debug("开始发送银行指令");
		log.debug("指令信息开始 "+instruction.toString());
		try{
			/*System.out.println("SystemId = "+instruction.getSystemId());
			System.out.println("PayReferenceCode = "+instruction.getPayReferenceCode());
			System.out.println("RecReferenceCode = "+instruction.getRecReferenceCode());
			System.out.println("RecAccountNO = "+instruction.getRecAccountNO());
			System.out.println("RecAccountName = "+instruction.getRecAccountName());
			System.out.println("RecBranchCode = "+instruction.getRecBranchCode());
			System.out.println("RecBranchName = "+instruction.getRecBranchName());
			System.out.println("getRecBranchUniteCode = "+instruction.getRecBranchUniteCode());
			System.out.println("RecBranchProvince = "+instruction.getRecBranchProvince());
			System.out.println("RecBranchCity = "+instruction.getRecBranchCity());
			System.out.println("Amount = "+instruction.getAmount());
			System.out.println("ChargesBorneType = "+instruction.getChargesBorneType());
			System.out.println("RemitPriority = "+instruction.getRemitPriority());
			System.out.println("AbstractInfo = "+instruction.getAbstractInfo());
			System.out.println("RemarkInfo = "+instruction.getRemarkInfo());
			System.out.println("SubmitUserName = "+instruction.getSubmitUserName());
			System.out.println("PayRateReferenceCode = "+instruction.getPayRateReferenceCode());
			System.out.println("RecNetStationName = "+instruction.getRecNetStationName());
			System.out.println("TransNO = "+instruction.getTransNO());
			System.out.println("TransType = "+instruction.getTransType());
			System.out.println("TransactionTime = "+instruction.getTransactionTime());
			*/
			log.info("即将发送的银行指令信息："+UtilOperation.dataentityToString(instruction));
			log.debug("指令信息结束");
			RespInstructionInfo rtn=null;
			//throw new Exception("测试");
			this.agentIni();
			rtn=BPClientAgent.postInstruction(instruction);
			log.debug("指令发送完毕，返回信息 "+rtn.getMessage());
			if (rtn.getStatus()!=InstructionStatus.SUCCEED && rtn.getStatus()!=InstructionStatus.SAVEDNOTSEND && rtn.getStatus()!=InstructionStatus.SENTPROCESSING && rtn.getStatus()!=InstructionStatus.UNKNOWENED)
				throw new Exception("银行指令发送失败:"+rtn.getMessage());
			return rtn;
			
				
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	/**
	 * 批量发送银行指令
	 * @param instruction
	 * @throws Exception
	 */
	public void postInstructions(ReqInstructionInfo[] instruction) throws Exception{
		log.debug("开始发送银行指令");
		log.debug("指令信息开始 ");
		try{
			int i = 0;
			//add by zcwang 2009-03-24
			if(instruction==null)
			{
				return;
			}
			while(i<instruction.length)
			{
				log.info("即将发送的银行指令信息："+UtilOperation.dataentityToString(instruction[i]));
				i++;
			}
			log.debug("指令信息结束");
			RespInstructionInfo rtn=null;
			//throw new Exception("测试");
			this.agentIni();
			rtn=BPClientAgent.postInstructions(instruction);
			log.debug("指令发送完毕，返回信息 "+rtn.getMessage());
			if (rtn.getStatus()!=InstructionStatus.SUCCEED && rtn.getStatus()!=InstructionStatus.SAVEDNOTSEND && rtn.getStatus()!=InstructionStatus.SENTPROCESSING && rtn.getStatus()!=InstructionStatus.UNKNOWENED)
				throw new Exception("银行指令发送失败:"+rtn.getMessage());
			
				
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	public void agentIni() throws Exception{
		
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		if (!bIsValid){
			log.info("没有配置资金监控服务接口");
			return;
		}			
		
		String IP=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEIP ,"");
		String port=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEPORT ,"");
		
		log.info("资金监控服务 IP = "+IP);
		log.info("资金监控服务 Port = " +port);
		
		if (IP==null || IP=="" || port==null || port==""){
			log.info("IP或Port为空，无法启动结算服务");
			throw new Exception("IP或Port为空，无法连接资金监控接口");
		}
		
		strAgentIP=IP;
		iAgentPort=Integer.valueOf(port).intValue();
		
		BPClientAgent.init(strAgentIP,iAgentPort);
	}
	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ReqInstructionInfo converTrasInfoToInstruction(BankInstructionInfo info) throws Exception {
		if(info==null)
			throw new Exception("param is null");
		
		ReqInstructionInfo instruction=new ReqInstructionInfo();
		instruction.setSystemId(1);//系统id
		instruction.setPayReferenceCode(info.getPayAccountNo());//付款方内部账号:01-01-1001-2 
		instruction.setPayAccountNO(info.getPayBankAccountNO());//付款银行账户号(开户行账号):如无关联号，则必填
		instruction.setPayAccountName(info.getPayAccountName());//付款银行账户名称
		instruction.setPayAcctCurrencyCode(Constant.CurrencyType.getCode(info.getCurrencyID()));//付款账户币种编号
		instruction.setPayAcctBankCode(info.getPayAcctBankCode());//付款账户银行编号
		instruction.setRecReferenceCode(info.getReceiveReferenceCode());//内转交易则为收款方内部账号
		instruction.setRecAccountNO(info.getReceiveAccountNo());//收款方外部账户号
		instruction.setRecAccountName(info.getReceiveAccountName());//收款方外部账户名称
		instruction.setRecBranchCode(info.getReceiveBranchCodeOfBank());//收款方外部开户行机构号
		instruction.setRecBranchName(info.getReceiveBranchName());//收款方外部开户行
		instruction.setRecBranchUniteCode(info.getReceiveBankExchangeCode());//收款方外部开户行联行号
		instruction.setRecBranchProvince(info.getReceiveBranchAreaNameOfProvince());//收款外部账户所在地省名 
		instruction.setRecBranchCity(info.getReceiveBranchAreaNameOfCity());//收款外部账户所在地市名
		instruction.setAmount(info.getAmount());//金额
		instruction.setChargesBorneType(info.getChargesBorneType());//费用承担方
		instruction.setRemitPriority(info.getRemitPriority());//汇款速度
		instruction.setAbstractInfo(info.getAbstract());//摘要
		instruction.setRemarkInfo(info.getComment());//备注
		instruction.setSubmitUserName(info.getSenderNo());//提交人名称
		instruction.setPayRateReferenceCode(info.getPayChargeAccountNo());//付款手续费关联号
		instruction.setRecNetStationName(info.getRecNetStationName());//收款方网点名称
		instruction.setTransNO(info.getTransactionNo());//对应业务系统的统一标示
		instruction.setTransType(info.getTransType());//业务类型
		instruction.setTransactionTime(info.getCreateTime());//交易日期
		
		instruction.setAgentAcctNoOfPay(info.getAgentAcctNoOfPay());
		instruction.setAgentBankNameOfPay(info.getAgentBankNameOfPay());
		instruction.setOfficeID(info.getOfficeId());//办事处id
		return instruction;
		
	}
	
	/**
	 * 批量转换发指令INFO
	 * @param colInstructionParams
	 * @return
	 * @throws Exception
	 */
	public ReqInstructionInfo[] converTrasInfoToInstructions(Collection colInstructionParams) throws Exception {
		if(colInstructionParams==null)
			throw new Exception("param is null");
		//modify by zcwang 2009-03-24
		//ReqInstructionInfo[] instructions = new ReqInstructionInfo[colInstructionParams.size()];
		ReqInstructionInfo[] instructions =null;
		Collection tempColl = new ArrayList();
		Timestamp systemdatetime =Env.getSystemDateTime();
		int i=0;
		while(i<colInstructionParams.size())
		{
			BankInstructionInfo info =(BankInstructionInfo)colInstructionParams.toArray()[i];
			ReqInstructionInfo instruction = new ReqInstructionInfo();
			instruction.setSystemId(1);//系统id
			instruction.setPayReferenceCode(info.getPayAccountNo());//付款方内部账号:01-01-1001-2 
			instruction.setPayAccountNO(info.getPayBankAccountNO());//付款银行账户号(开户行账号):如无关联号，则必填
			instruction.setPayAccountName(info.getPayAccountName());//付款银行账户名称
			instruction.setPayAcctCurrencyCode(Constant.CurrencyType.getCode(info.getCurrencyID()));//付款账户币种编号
			instruction.setPayAcctBankCode(info.getPayAcctBankCode());//付款账户银行编号
			instruction.setRecReferenceCode(info.getReceiveReferenceCode());//内转交易则为收款方内部账号
			instruction.setRecAccountNO(info.getReceiveAccountNo());//收款方外部账户号
			instruction.setRecAccountName(info.getReceiveAccountName());//收款方外部账户名称
			instruction.setRecBranchCode(info.getReceiveBranchCodeOfBank());//收款方外部开户行机构号
			instruction.setRecBranchName(info.getReceiveBranchName());//收款方外部开户行
			instruction.setRecBranchUniteCode(info.getReceiveBankExchangeCode());//收款方外部开户行联行号
			instruction.setRecBranchProvince(info.getReceiveBranchAreaNameOfProvince());//收款外部账户所在地省名 
			instruction.setRecBranchCity(info.getReceiveBranchAreaNameOfCity());//收款外部账户所在地市名
			//财企接口新增
			instruction.setRecBankCode(info.getRecBankCode());  //CNAPS序号
			instruction.setRecBranchCode(info.getReceiveBranchCodeOfBank()); //收款方机构号
			instruction.setRecBranchUniteCode(info.getReceiveBankExchangeCode()); //收款方联行号
			
			
			//当保留两位的金额为0时，不发送此笔指令 modify by zcwang 2009-03-24
			if(UtilOperation.Arith.round(info.getAmount(),2)==0.0)
			{
				i++;
				continue;
			}
			instruction.setAmount(UtilOperation.Arith.round(info.getAmount(),2));//金额
			instruction.setChargesBorneType(info.getChargesBorneType());//费用承担方
			instruction.setRemitPriority(info.getRemitPriority());//汇款速度
			instruction.setAbstractInfo(info.getAbstract());//摘要
			instruction.setRemarkInfo(info.getComment());//备注
			instruction.setSubmitUserName(info.getSenderNo());//提交人名称
			instruction.setPayRateReferenceCode(info.getPayChargeAccountNo());//付款手续费关联号
			instruction.setRecNetStationName(info.getRecNetStationName());//收款方网点名称
			instruction.setTransNO(info.getTransactionNo());//对应业务系统的统一标示
			instruction.setTransType(info.getTransType());//业务类型
			instruction.setTransactionTime(systemdatetime);//交易日期
			
			instruction.setAgentAcctNoOfPay(info.getAgentAcctNoOfPay());
			instruction.setAgentBankNameOfPay(info.getAgentBankNameOfPay());
			instruction.setOfficeID(info.getOfficeId());//办事处id
			//modify by xwhe 2008-12-12 
			instruction.setRemitPriority(info.getRemitPriority());//加急
			//modify by zcwang 2009-03-24
			//instructions[i] = instruction;
			tempColl.add(instruction);
			i++;
		}
		//add by zcwang 2009-03-24
		if(tempColl.size()>0)
		{
			instructions = (ReqInstructionInfo[])tempColl.toArray(new ReqInstructionInfo[0]);
		}
		return instructions;
		
	}
	/**
	 * 
	 * @总账info 转成 ReqGetGeneralBankAcctInfo 
	 * @return
	 * @throws Exception
	 */
	public ReqGetGeneralBankAcctInfo converGeneralInfoToInstruction(TransGeneralLedgerInfo info) throws Exception {
		if(info==null)
			throw new Exception("param is null");
		
		ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
		//Account account = null;
		AccountHome home =
			(AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
		Account account = (Account) home.create();
		instruction.setSystemId(1);//系统id
		instruction.setReferenceCode(account.findAccountByID(info.getAccountID()).getAccountNo());
		
		return instruction;
		
	}
	/**
	 * 检索当日交易信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public RespQueryCurTransInfo queryCurrentTransInfo(ReqQueryCurTransInfo info) throws Exception{
		RespQueryCurTransInfo rtnInfo=null;
		this.agentIni();
		rtnInfo=BPClientAgent.queryCurTransaction(info);
		return rtnInfo;
	}
	/**
	 * 检索历史交易信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public RespQueryHisTransInfo queryHisTransInfo(ReqQueryHisTransInfo info) throws Exception{
		RespQueryHisTransInfo rtnInfo=new RespQueryHisTransInfo();
		this.agentIni();
		rtnInfo=BPClientAgent.queryHisTransaction(info);
		return rtnInfo;
	}
}
