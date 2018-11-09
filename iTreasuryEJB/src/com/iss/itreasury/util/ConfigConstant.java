/*
 * Created on 2005-4-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

// import
// com.iss.itreasury.settlement.remind.process.RemindBankAccountTransInfoTask;

/**
 * @author gdzhao
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ConfigConstant {
	// ***********
	// 全局属性****
	// ***********
	
	/**
	 * 是否南航项目
	 */
	public static final String GLOBAL_IS_SOURTHFLY = "global.is.sourcefly";



	
	/**
	 * 系统中的模块顺序
	 * */
	public static final String GLOBAL_MODULES_ORDER = "global.modules.order";
	

	//added by mzh_fu 2008/01/30 安全控制 begin	
	/**
	 * 结算证书ＣＮ是否必输
	 */
	public static final String SETTLEMENT_CERT_SUBJECTCOMMONNAME_ISNEEDED = "settlement.cert.subjectcommonname.isneeded";
	
	/**
	 * 网银证书ＣＮ是否必输
	 */
	public static final String EBANK_CERT_SUBJECTCOMMONNAME_ISNEEDED = "ebank.cert.subjectcommonname.isneeded";
	
	/**
	 * 结算证书序列号是否必输
	 */
	public static final String SETTLEMENT_CERT_SERIALNUMBER_ISNEEDED = "settlement.cert.serialnumber.isneeded";
	
	/**
	 * 网银证书序列号是否必输
	 */
	public static final String EBANK_CERT_SERIALNUMBER_ISNEEDED = "ebank.cert.serialnumber.isneeded";	
	
	
	/**
	 * 网银证书系统名称
	 */
	public static final String GLOBAL_TROY_NAME = "global.troy.name";
	/**
	 * 网银证书系统类型
	 */
	public static final String GLOBAL_TROY_TYPE = "global.troy.type";
	
	/**
	 * 结算证书系统名称
	 */
	public static final String GLOBAL_SETTLEMENT_TROY_NAME = "global.settlement.troy.name";
	
	
	/**
	 * 网银支持的证书颁发机构列表
	 */
	public static final String GLOBAL_TROY_ISSUERDNARRAY = "global.troy.issuerdnarray";
	
	/**
	 * 结算支持的证书颁发机构列表
	 */
	public static final String GLOBAL_SETTLEMENT_TROY_ISSUERDNARRAY = "global.settlement.troy.issuerdnarray";
	
	/**
	 * 担保功能是否需要结算模块
	 */
	
	public static final String GLOBAL_SETTLEMENT_ASSURE = "global.settlement.assure";
	/**
	 * KeyStore 文件的路径与名称
	 */
	public static final String GLOBAL_KEYSTORE_KEYSTOREFILEPATHNAME = "global.keystore.keystorefilepathname";
	
	/**
	 * KeyStore 的密码
	 */
	public static final String GLOBAL_KEYSTORE_KEYSTOREPASSWORD = "global.keystore.keystorepassword";
	
	/**
	 * Key Alias
	 */
	public static final String GLOBAL_KEYSTORE_KEYALIAS = "global.keystore.keyalias";
	
	/**
	 * 结Key 的密码
	 */
	public static final String GLOBAL_KEYSTORE_KEYPASSWORD = "global.keystore.keypassword";	

	/**
	 * 服务器端签名证书路径
	 */
	public static final String GLOBAL_CERTIFICATE_PATH = "global.certificate.path";	
	
	/**
	 * 服务器签名证书密码
	 */
	public static final String GLOBAL_CERTIFICATE_PASSWORD = "global.certificate.password";	
	// 安全控制 end
	
	/**
	 * 是否许可并行关机
	 */
	public static final String GLOBAL_CAN_MULTICLOSE = "global.can.multiclose";

	/**
	 * 是否默认人民币
	 */
	public static final String GLOBAL_IS_CURRENCY = "global.is.currency";

	/**
	 * 账户号的段数
	 */
	public static final String GLOBAL_ACCOUNTNO_FIELD = "global.accountno.field";

	/**
	 * 账户号最长段的长度
	 */
	public static final String GLOBAL_MAXACCOUNTNO_LENGTH = "global.maxaccountno.length";

	/**
	 * 账户号最长段的段位
	 */
	public static final String GLOBAL_MAXACCOUNTNO_NUMBER = "global.maxaccountno.number";

	/**
	 * 账户号的段间符号
	 */
	public static final String GLOBAL_ACCOUNTNO_TAG = "global.accountno.tag";

	/**
	 * 账户号第一段的类型
	 * 1 人民币;2 办事处[如果没有选择1则人民币不在账户编号中]
	 */
	public static final String GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE = "global.accountno.firstfieldtype";

	/**
	 * 权限设置
	 * 是否启用是否是指纹管理员功能 true为启动，即页面显示该功能，false 为不启用
	 */
	public static final String GLOBAL_ISORNOTENABLE_FINGERPRINTTYPE = "global.isornotenable.fingerprinttype";

	/**
	 * Ldap查询设置
	 * 是否启用Ldap查询 true为启动，false 为不启用，用于新奥新增用户
	 */
	public static final String GLOBAL_PRIVILEGE_LDAP = "global.privilege.ldap";
	/**
	 * 指纹验证配置
	 * 是否启用指纹验证配置 true为启动，false 为不启用，最初用于新奥业务
	 */
	public static final String GLOBAL_PRIVILEGE_FINGERPRINT = "global.privilege.fingerprint";
	/**
	 * 指纹服务器url地址
	 */
	public static final String GLOBAL_FINGERPRINT_SERVER_URL = "global.fingerprint.server.url";
	/**
	 * 指纹验证机构编号
	 */
	public static final String GLOBAL_FINGERPRINT_SYS_ID = "global.fingerprint.sys.id";
	
	public static final String GLOBAL_LOGIN_URL = "global.login.url";
	
	
	/**
	 * 2012-9-10 南航个性化需求，如果为true，则执行债券到期日、分红日提醒自动任务
	 */
	public static final String GLOBAL_NCHW_ZQZDRW = "global.nchw.zqzdrw";
	
	// ************
	// 信贷模块属性
	// ************
	public static final String LOAN_CREDIT_CHECKREPORT = "loan.credit.checkreport";
	public static final String LOAN_CONTRACT_REBUILD="loan.contract.rebuild";
	public static final String LOAN_CLIENT_REPORT = "loan.client.report";
	
	/**
	 * 信贷模块,是否启用授信功能。true or false，默认为false
	 */
	public static final String LOAN_CREDIT_INTEGRATIONCREDIT = "loan.credit.integrationcredit";
	public static final String LOAN_CREDIT_ISAPPLYENDDATE = "loan.credit.isapplyenddate";
	/**
	 * 信贷模块，启用授信功能后，如果某客户没有设置授信，贷款申请时是否提示。true or false，默认为false
	 */
	public static final String CREDIT_ISCREDITPROMPT = "credit.iscreditprompt";
	/**
	 * 信贷模块，授信校验方式（自营贷款），1.放款通知单申请时校验,2.贷款申请时校验，默认为1
	 */
	public static final String LOAN_CREDIT_CHECK = "loan.credit.check";
	
	//add by zwxiao 2010-08-02
	/**
	 * 信贷模块，国机项目组要求等额本息的合同的保证金金额为3期的租金，默认为3
	 */
	public static final String LOAN_FINANCE_MARGINAMOUNT = "loan.finance.marginAmount";
	
	public static final String LOAN_CONSIGN_HANDINGCHARGEACCOUNT = "loan.consign.handingChargeAccount";
	//add by kevin（刘连凯） 2011-05-20
	/**
	 * 信贷模块，审批成功后，是否提示打印,默认为否
	 */
	public static final String LOAN_PRINT_PROMPT="loan.print.prompt";

	// ***********
	// 银企接口属性*
	// ***********
	/**
	 * 是否有银企接口
	 */
	public static final String INTEGRATION_SERVICE_ISVALID = "integration.service.isvalid";

	/**
	 * 结算与指令接口操作类
	 */
	public static final String INTEGRATION_SERVICE_INSTRUCTIONCLASS = "integration.bankinterface_settlement.InstructionClass";

	/**
	 * 结算与入账接口操作类
	 */
	public static final String INTEGRATION_SERVICE_IMPORTACCOUNTCLASS = "integration.bankinterface_settlement.ImportAccountClass";

	/**
	 * 银企接口配置文件
	 */
	public static final String INTEGRATION_SERVICE_CONFIG_FILE = "integration.service.config.file";

	/**
	 * 自动任务间隔时间
	 */
	public static final String INTEGRATION_SERVICE_CONFIG_INTERVAL = "integration.service.config.interval";

	/**
	 * 银企接口日至保存的目录名称
	 */
	public static final String INTEGRATION_SERVICE_LOG_PATH = "integration.service.log.path";

	/**
	 * 银企接口日至级别
	 */
	public static final String INTEGRATION_SERVICE_LOG_LEVEL = "integration.service.log.level";

	/**
	 * 银行交易文件导出路径
	 */
	public static final String INTEGRATION_SERVICE_EXPORFILE_PATH = "integration.service.exportfile.path";

	/**
	 * 需要自动入账的银行类型
	 */
	public static final String INTEGRATION_SERVICE_TOTURNIN_BANKTYPE = "integration.service.toturnin.banktype";

	/**
	 * 需要发送银行指令的银行类型
	 */
	public static final String INTEGRATION_SERVICE_TOSENDBANKINSTRUCTION_BANKTYPE = "integration.service.tosendbankinstruction.banktype";

	/**
	 * 接收银行交易并且自动入账的类名
	 */
	public static final String INTEGRATION_SERVICE_RECIEVEBANKTRANSINFOTOTURNIN_CLASS = "integration.service.RecieveBankTransInfoToTurnIn.class";

	/**
	 * 网银系统与结算系统集成-网银系统业务信息属性
	 */
	public static final String INTEGRATION_ES_FINANCEINFO = "integration.ebank_settlement.FinanceInfo";

	/**
	 * 结算系统与银企接口集成-结算系统银行指令信息属性
	 */
	public static final String INTEGRATION_SB_BANKINSTRUCTIONINFO = "integration.settlement_bankinterface.BankInstructionInfo";

	/**
	 * 银企接口与结算系统集成-银企接口转账结果信息属性
	 */
	public static final String INTEGRATION_BS_VIREMENTRESULTINFO = "integration.bankinterface_settlement.VirementResultInfo";

	/**
	 * 银企接口与结算系统集成-银企接口交易信息属性
	 */
	public static final String INTEGRATION_BS_BANKACCOUNTTRANSINFO = "integration.bankinterface_settlement.BankAccountTransInfo";

	/**
	 * 发送给结算的自动入账的URL
	 */
	public static final String INTEGRATION_SERVICE_RECEIVEAUTOTURNIN_PATH = "integration.service.ReceiveAutoTurnIn.Path";

	/**
	 * 是否启用银行账户收款交易提醒任务
	 */
	public static final String INTEGRATION_SERVICE_REMINDBANKACCOUNTTRANSINFOTASK_ISVALID = "integration.service.RemindBankAccountTransInfoTask.isvalid";

	/**
	 * 是否启用银行账户余额提醒任务
	 */
	public static final String INTEGRATION_SERVICE_REMINDBANKACCOUNTBALANCETASK_ISVALID = "integration.service.RemindBankAccountBalanceTask.isvalid";

	/**
	 * 结算服务的ip
	 */
	public static final String INTEGRATION_SERVICE_SETTSERVICEIP = "integration.service.SettServiceIP";

	/**
	 * 结算服务的端口
	 */
	public static final String INTEGRATION_SERVICE_SETTSERVICEPORT = "integration.service.SettServicePort";

	/**
	 * 资金监控服务的ip
	 */
	public static final String INTEGRATION_SERVICE_BPSERVICEIP = "integration.service.BPServiceIP";

	/**
	 * 资金监控服务的端口
	 */
	public static final String INTEGRATION_SERVICE_BPSERVICEPORT = "integration.service.BPServicePort";

	
	/**
	 * 结算-机构编号设置 新增时往客户表中插入 机构客户
	 * 
	 * 航天科工客户信息 保存在 client 表中
	 * 
	 * 产品 保存在 client_clientinfo 表中
	 */
	public static final String SETTLEMENT_CLIENT_TABLENAME = "settlement.client.tablename";

	
	// ************
	// 网银模块属性
	// ************
	/**
	 * 网银可以打印的打印凭证
	 */
	public static final String EBANK_TRANS_PROCESSVOUCHERPRINT = "ebank_trans_processvoucherprint";
	
	/**
	 * 设置业务是否需要打印的提示 add by zcwang 2007-10-19
	 */
	public static final String EBANK_ISPRINT = "ebank_isprint";
	//
	public static final String EBANK_INITUSER_XMLNAME = "ebank_inituser_xmlname";

	/**
	 * 网银业务操作流程分类
	 */
	public static final String EBANK_BUSINESS_OPERATION_TYPE = "ebank_business_operation_type";
	
	/**
	 * 网银业务审批后是否自动复核
	 */
	public static final String EBANK_TRANSAPPROVAL_AUTOCHECK = "ebank_transapproval_autocheck";
	
	/**
	 * 是否去掉存单号的"-"
	 */
	public static final String EBANK_DEPOSITNO_NOLINE = "ebank_depositno_noline";
	
	/**
	 * 网银登录页面是否需要验证码
	 */
	public static final String EBANK_ISREQUIRECHECKCODE = "ebank_isRequireCheckcode";
	
	
	// ************
	// 结算模块属性
	// ************
	/**
	 * 结算开户行编号设置－是否单边账号
	 */
	public static final String SETTLEMENT_BRANCH_ISSINGLEACCOUNT = "settlement_branch_issingleaccount";

	/**
	 * 结算贷款贴现凭证新增配置项－汇票是否用票据模块数据
	 */
	public static final String SETTLEMENT_LOAN_DRAFTISFROMBILL = "settlement.settlement_loan.draftIsFromBill";

	/**
	 * 是否有自动定时提醒
	 */
	public static final String SETTLEMENT_INFORM_AUTOTIME = "settlement_inform_autotime";

	/**
	 * 是否有银行头寸和活期余额比例提醒
	 */
	public static final String SETTLEMENT_INFORM_BANKCASH = "settlement_inform_bankcash";

	/**
	 * 是否有冲账需求功能
	 */
	public static final String SETTLEMENT_SPECIAL_STRIKEBALANCE = "settlement_special_strikebalance";

	/**
	 * 是否有新旧账户对照
	 */
	public static final String SETT_ACCOUNT_ISOLDACCOUNT = "sett_account_isoldaccount";

	/**
	 * 是否可以打印定期开立、通知开立已删除凭证
	 */
	public static final String SETT_TRANSFIXED_DELVOUCHERPRINT = "sett_transfixed_delvoucherprint";

	/**
	 * 是否有结算交易手续费的收取
	 */
	public static final String SETT_TRANSCOMMISSION = "sett_transcommission";

	/**
	 * 定期存单账号是否可以修改
	 */
	public static final String SETT_FIXEDNO_CANMODIFY = "sett_fixedno_canmodify";

	/**
	 * 贷款结算合同号是否由客户手工录入
	 */
	public static final String SETT_SETTLOAN_CONTRACTNO_MANUAL = "sett_settloan_contractno_manual";

	/**
	 * 网银指令在结算模块是否自动复核
	 */
	public static final String SETT_OBINSTRUCTION_AUTOCHECK = "sett_obinstruction_autocheck";
	
	/**
	 * 结算定期业务，是否显示自动续存功能配置项  2010.11.19
	 */		
	public static final String SETT_TRAN_FIXED_ISAUTOCONTINUE = "settlement.settlement_tran.fixed.IsAutoContinue";

	/**
	 * 结算系统，是否有委托付款凭证
	 */
	public static final String SETT_HAS_CONSIGNVOUCHER = "sett_has_consignvouncher";

	/**
	 * 结算系统，是否需要选择还款通知单
	 */
	public static final String SETT_HAS_REPAYNOTE = "sett_has_repaynote";

	/**
	 * 结算系统，中油总账接口收入现金流向对应编号
	 */
	public static final String SETT_ORACLEFORCPF_MULTICODEFORINCOME = "sett_oracleforcpf_multicodeforincome";

	/**
	 * 结算系统，中油总账接口支出现金流向对应编号
	 */
	public static final String SETT_ORACLEFORCPF_MULTICODEFORPAYOUT = "sett_oracleforcpf_multicodeforpayout";

	/**
	 * 资产买入买断能否选择汇出行
	 */
	public static final String SETT_SELECT_BANK = "sett.select.bank";
	
	/**
	 * 活期利率
	 */
	public static final String CURRENCY_INTEREST_RATE = "currency_interest_rate";

	/**
	 * 结算业务操作流程分类
	 */
	public static final String SETT_BUSINESS_OPERATION_TYPE = "sett_business_operation_type";

	/**
	 * 设置业务复核后是否需要打印的提示
	 */
	public static final String SETTLEMENT_CHECKED_ISPRINT = "settlement_checked_isprint";

	/**
	 * 更新会计账科目时检查并更新科目上下级关级
	 */
	public static final String SETT_TIME_BUDGETAUTOTASK = "sett_time_budgetautotask";
	
	/**
	 * 设置科目号每一级的长度，格式如：4|6|8|11|13。在开机的时候为科目号增加上级科目ID时用到。
	 * 最终是为了开户行余额汇总查询出数。
	 */
	public static final String SETTLEMENT_GLSUBJECTDEFINITION_CONVERT = "settlement.glsubjectdefinition.convert";
	
	/**
	 * 结算登录页面是否需要验证码
	 */
	public static final String SETTLEMENT_ISREQUIRECHECKCODE = "settlement_isRequireCheckcode";
	
	/**
	 * 银行付款-拨子账户交易是否生成会计分录
	 */
	public static final String SETTLEMENT_CREATESUBJECT_PAYSUBACCOUNT = "settlement_createsubject_paysubaccount";
	
	/**
	 * 设置是否显示金额单位条件
	 */
	public static final String SETTLEMENT_ISSHOW_AMOUNTUNIT = "settlement_isshow_amountunit";
	
	/**
	 * 设置是否显示指定账户编号查询条件
	 */
	public static final String SETTLEMENT_ISSHOW_APPOINTACCOUNT = "settlement_isshow_appointAccount";
	
	// ***********
	// 系统模块*
	// ***********
	/**
	 * 是否加密(结算系统)
	 */
	public static final String SETT_CAN_ENCRYPT = "sett.can.encrypt";
	/**
	 * 用户密码加密方式(结算系统)
	 */
	public static final String SETT_ENCRYPT_TYPE = "sett.encrypt.type";
	/**
	 * 是否加密(网银系统)
	 */
	public static final String EBANK_CAN_ENCRYPT = "ebank.can.encrypt";
	/**
	 * 用户密码加密方式(网银系统)
	 */
	public static final String EBANK_ENCRYPT_TYPE = "ebank.encrypt.type";
	
	/**
	 * 网银使用证书验签时提醒方式
	 */
	
	public static final String EBANK_CERTIFICATE_REMIND = "ebank.certificate.remind";
	/**
	 * 结算 日结科目汇总查询是否滤空
	 */
	public static final String SETTMENT_DAILYGLQUERY_ISLEACHING = "settlement_dailyGLQuery_IsLeaching";

	/**
	 * 交易记录查询是否有存在有补打凭证的功能
	 */
	public static final String SETT_HAS_REPAIR_PRINTVOUCHER = "sett_has_repair_printVoucher";

	/**
	 * 计算利息,是按每月30天计算,还是按实际天数计算
	 */
	public static final String SETT_INTEREST_INTERVALDAYS_ISFACT = "settlement_interestIntervalDays_IsFact";

	/**
	 * 客户编号长度
	 */
	public static final String SYSTEM_USERINFO_SCODE_LENGTH = "system.userinfo.scode.length";
	
	/**
	 * 结算业务审批后是否自动复核
	 */
	public static final String SETT_TRANSAPPROVAL_AUTOCHECK = "sett_transapproval_autocheck";
	
	
	/**
	 * 开户行科目类型
	 */
	public static final String SETT_BRANCH_BANKSUBJECTTYPE = "sett_branch_bankSubjectType";
	
	/**
	 * 通知入账对应的特殊业务子类型编号,此编号需要在特殊业务类型设置中设置,如果没有设置此编号,在做"通知入账时将会报异常"
	 */
	public static final String SETT_SPECIALOPERATION_CONVERGE_NUMBER="sett_specialOperation_converge_number";
	/**
	 * 设置在开机时是否自动导入科目余额,默认为非自动导入
	 */
	public static final String SETT_AUTO_IMPORT_GLSUBJECT_BALANCE="sett_auto_import_glsubject_balance";
	
	/**
	 * 结算业务审批后是否自动复核
	 */
	public static final String SETT_GLENTRYDEFINITION_ISNEEDCHECK = "settlement_GlentryDefinition_IsNeedCheck";
	
	
	// ***********
	// 电子回单模块*
	// ***********
	/**
	 * 打印控件打印间隔时间
	 */
	public static final String GLOBAL_EVOUCHER_PRINTTIME = "global.evoucher.printtime";
	
	/**
	 * 电子回单柜打印财务公司所在地点
	 */
	public static final String GLOBAL_EVOUCHER_PRINTCLIENTADDRESS = "global.evoucher.printclientaddress";
	
	//设置结算导账摘要信息长度
	public static final String SETTLEMENT_U850_ABSTRACTLENGTH = "settlement_U850_Abstractlength";
	
	//设置结算导账接收编码
	public static final String SETTLEMENT_U850_ECODING = "settlement_U850_ecoding";
	
	/**
	 * 设置下属单位账户余额查询(是否查询本单位信息) add by zcwang 2008-2-25
	 */
	public static final String EBANK_ISQUERYCLIENT = "ebank_isQueryClient";
	
	/**
	 * 结算关机是否强制校验账户体系(代企业资金管理)
	 */
	public static final String SETT_CLOSESYSTEM_ISFORCEVERIFYACCOUNTSYSTEM = "sett_closesystem_isforceverifyaccountsystem";
	/**
	 * 是否只提示当天未处理的网银指令
	 */
	public static final String SETTLEMENT_REMIND_INSTRUCTION = "settlement_remind_instruction";
	
	/**
	 * 网银和结算不同服务器,网银访问结算服务器 add by zcwang 2008-4-30
	 */
	public static final String SETTLEMENT_MACHINE_INDEXURL = "settlement_machine_indexUrl";
	
	/**
	 * 多级资金管理网银审批是否判断账户体系 add by zcwang 2008-5-6
	 */
	public static final String EBANK_ISNEEDACCOUNTSYSTEM = "ebank_isneedaccountsystem";
	
	/**
	 * 关机时是否强行检查银行指令 add by leiyang 2008-05-09
	 */
	public static final String SETT_CLOSESYSTEM_ISFORCEVERIFYBANKDICTATE = "sett_closesystem_isforceverifybankdictate";
	
	
	/**
	 * 是否生成客户编号辅助核算项 2008-10-30 
	 */
	public static final String SETTLEMENT_NEEDCLIENTASSISTANT = "settlement_needclientassistant";
	
	/**
	 *  网银指令接受生成活期交易时，结算起息日是否修改为结算系统开机日
	 *  add by xwhe 2008-10-14
	 */
	public static final String SETT_OBINSTRUCTION_MODIFYINTERESTDATE = "sett_obinstruction_modifyinterestDate";
	/**
	 *  网银银行付款指令自动复核交易取消复核时自动修改网银指令状态sett_cancelcheckbankpayoption
	 *  add by xwhe 2008-11-18
	 */
	public static final String SETT_CANCELCHECKBANKPAYOPTION = "sett_cancelcheckbankpayoption";
	/**
	 * 内部转账业务，两边都是二级户，是否只生成一笔银行转账指令
	 */
	public static final String SETT_CREATEINTERNALVIRMENTINTRUCTION_ONLYONE = "settlement_createInternalvirmentIntruction_OnlyOne";
	
	/**
	 * 是否打印贷款合同文件
	 * add by jdcheng 2008-11-11
	 */
	public static final String LOAN_CONTRACT_PRINT = "loan.contract.print";
	
	public static final String LOAN_CONTRACT_FIVERISKLEVEL = "loan.contract.fiverisklevel";
	
	public static final String SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE = "settlement_WTDeposit_AccountTypeCode";
	
	//协定存款利率 2008 -12- 18
	public static final String settlement_Negotiation_Rate = "settlement_Negotiation_Rate";
	
	//贷款期限特殊配置项 add by xfma 2008-12-29
	public static final String LOAN_JNTERM = "loan.JNTerm";
	//贷款提醒合同前是否需要显示客户简称
	public static final String LOAN_AWAKE_ISSHOWSIMPLENAME = "loan.awake.isshowsimplename";
	
	
	
	/**
	 * 1104数据库主机地址
	 */
	public static final String GLOBAL_1104DB_IP = "global.1104db.ip";

	/**
	 * 1104数据库SID
	 */
	public static final String GLOBAL_1104DB_SID = "global.1104db.sid";

	/**
	 * 1104数据库段口号
	 */
	public static final String GLOBAL_1104DB_PORT = "global.1104db.port";

	/**
	 * 1104数据库服务名
	 */
	public static final String GLOBAL_1104DB_SERVICENAME = "global.1104db.servicename";

	/**
	 * 1104数据库登录用户名称
	 */
	public static final String GLOBAL_1104DB_USERNAME = "global.1104db.username";

	/**
	 * 1104数据库段登录用户密码
	 */
	public static final String GLOBAL_1104DB_PASSWORD = "global.1104db.password";

	/**
	 *信用评级模块指标体系取值方式为手工选择，在评级时定值描述后是否显示实际分数，true为显示，false为不显示
	 */
	public static final String LOAN_CREDITRATING_FIXVALUE = "loan.creditrating.fixvalue";
	
	public static final String LOAN_CREDITRATING_CHECKSAMERATINGDATE = "loan.creditrating.checkSameRatingDate";
	
	
	/**
	 *网银结算系统是否保存业务日志，true为保存，false为不保存
	 */
	
	public static final String GLOBAL_SYSTEM_TRANSLOG="global.system.translog";
	
	public static final String GLOBAL_EBANK_TRANSLOG="global.ebank.translog";
	
	/*
	 * 网银结算系统是否在登录时使用软键盘
	 */
	public static final String GLOBAL_SYSTEM_SOFTKEYBOARD="global.system.softkeyboard";
	
	public static final String GLOBAL_EBANK_SOFTKEYBOARD="global.ebank.softkeyboard";
	
	public static final String SETTLEMENT_BANKABSTRACT_SIGN="settlement.bankabstract.sign";
	
	/**
	 * 结算关机处理委托结束合同方式
	 */
	public static final String SETTLEMENT_FINISH_WT_CONTRACT = "settlement_finish_WT_contract";
	
	/**
	 * 对财务公司及成员单位授信时是否校验对财务公司授信的授信额度该等于其在相同授信区间内对所有成员单位的授信额度的总和(同业授信)
	 */
	public static final String CRAFTBROTHER_CREDIT_ISALLMEMBER = "craftbrother.credit.isallmember";	
	
	/**
	 * 是否航天科工项目
	 */
	
	public static final String SETTLEMENT_ISHTKG = "settlement_HTKG";
	/**
	 * 是否限制合同对应放款单只有合同录入人才能增加，该配置项应用于同业往来模块
	 */
	public static final String GLOBAL_ISREQUIREPRIVILEGE = "global_isRequirePrivilege";
	
	/**
	 * 是否限制合同对应放款单只有合同录入人才能增加，该配置项应用于信贷模块
	 */
	public static final String GLOBAL_IFMOREOPERATE = "global.ifmoreoperate";
	
	/**
	 * 定期提前部分支付是否生成新的存单号
	 */
	public static final String SETT_FIXEDDRAW_CREATENEWBOOK = "sett.fixeddraw.createnewbook";
	/**
	 * 定期提前部分支付是否生成新的存单号
	 */
	public static final String SETT_FIXEDDRAW_STRIKEALLPREDRAWINTEREST = "sett.fixeddraw.strikeallpredrawinterest";
	/**
	 * 放款单中选择的活期账户是否默认为借款人的活期账户,6种贷款类型分别配置
	 */
	/**
	 * 自营贷款
	 */
	public static final String GLOBAL_LOANTYPE_ZYDK = "global.loantype.zydk";
	/**
	 * 委托贷款
	 */
	public static final String GLOBAL_LOANTYPE_WTDK = "global.loantype.wtdk";
	/**
	 * 银团贷款
	 */
	public static final String GLOBAL_LOANTYPE_YTDK = "global.loantype.ytdk";
	/**
	 * 贴现
	 */
	public static final String GLOBAL_LOANTYPE_TX = "global.loantype.tx";
	/**
	 * 担保
	 */
	public static final String GLOBAL_LOANTYPE_DB = "global.loantype.db";
	/**
	 * 融资租赁
	 */
	public static final String GLOBAL_LOANTYPE_RZZL = "global.loantype.rzzl";
	
	///
	/**
	 * 收款单中选择的账户编号是否默认为合同所关联的账户,3种合同类型分别配置
	 */
	/**
	 * 付保证金账户编号
	 */
	public static final String GLOBAL_ACCOUNT_PAY_PRO = "global.account.paypro";
	/**
	 * 收保证金账户编号
	 */
	public static final String GLOBAL_ACCOUNT_REV_PRO = "global.account.revpro";
	/**
	 * 付手续费账户编号
	 */
	public static final String GLOBAL_ACCOUNT_PAY_HAN = "global.account.payhan";
	
	/**
	 * 贷款模块是否需要审批提醒
	 */
	public static final String LOAN_APPROVAL_MESSAGE = "loan.approval.message";	
	/**
	 * 自营贷款提醒方式
	 */
	public static final String LOAN_PAYOFF_TYPE = "loan.payoff.type";	
	/**
	 * 过滤已经授权的电子签章客户的交易
	 */
	public static final String FILTER_ELECTRONIC_SIGNATURE = "filter.electronic.signature";
	/**
	 * 平均余额分析天数计算方法
	 */
	public static final String AVERAGE_DATE_TYPE = "Average.date.type";
	/**
	 * 是否开启SAP服务监控(财企接口)
	 */
	public static final String GLOBAL_FCINTERFACE_SAP = "global.fcinterface.sap";
	/**
	 * 网银签认人是否区分活期与定期(新奥项目)
	 */
	public static final String EBANK_SIGN_DISTINCT = "ebank.sign.distinct";
	/**
	 * 网银上级单位是否管理下级单位（新奥）
	 */
	public static final String EBANK_MANAGE_CHILDCLIENT = "ebank.manage.childClient";
	/**
	 * 电子单据柜模板存放位置
	 */
	public static final String GLOBAL_EVOUCHER_FILEPATH = "global.evoucher.filepath";
	/**
	 * 结算关机是否自动导账
	 */
	public static final String SETT_CLOSESYSTEM_AUTOPOSTGLVOUCHER = "sett.closesystem.autopostglvoucher";
	/**
	 * 远程调用贷款模块IP
	 */
	public static final String LOAN_REMOTECALL_IP = "loan.remotecall.ip";
	/**
	 * 远程调用贷款模块端口
	 */
	public static final String LOAN_REMOTECALL_PORT = "loan.remotecall.port";
	
	/**
	 * 打印签章中是否显示日期
	 */
	public static final String GLOBAL_SIGNATURE_SHOWDATE = "global.signature.showdate";
	
	/**
	 * 打印签章的显示位置
	 */
	public static final String GLOBAL_SIGNATURE_POSITION = "global.signature.position";
	
	/**
	 * 打印签章（日期）的显示位置
	 */
	public static final String GLOBAL_SIGNATUREDATE_POSITION = "global.signaturedate.position";
	
	/**
	 * 业务操作日志是否有效
	 */
	public static final String GLOBAL_BTNLEVERLOG_ISVALID = "global.btnlevellog.isvalid";
	
	/**
	 * 是否对已上传附件的文件ID进行加密显示
	 */
	public static final String GLOBAL_DOWNLOAD_FILEID_CAN_ENCRYPT = "global.download.fileid.can.encrypt";

	/**
	 * 委托贷款发放放款单起始日期取值方式
	 */
	public static final String SETT_LOAN_PAYFORM_DTSTARTDATE = "sett.loan.payform.dtstartdate";
	
	/**
	 * 放款通知单打印是否显示固定的“经办人”、“部门负责人”
	 */
	public static final String LOAN_PAYFORM_PRINT = "loan.payform.print";
	/**
	 * 放款通知单打印固定“经办人”常量设置
	 */
	public static final String LOAN_PAYFORM_PRINT_INPUTUSER = "loan.payform.print.inputuser";
	/**
	 * 放款通知单打印固定“部门负责人”常量设置
	 */
	public static final String LOAN_PAYFORM_PRINT_MANAGER = "loan.payform.print.manager";
	/**
	 * 放款通知单打印内部活期户开户行名称常量设置
	 */
	public static final String LOAN_PAYFORM_PRINT_OPENBANKNAME = "loan.payform.print.openbankname";
	/**
	 * 使用报表类型(使用皕杰还是帆软报表)
	 */
	public static final String GLOBAL_REPORT_TYPE = "global.report.type";
	/**
	 * NC接口同步科目和科目余额的实现方式（1:报文;2:中间表）
	 */
	public static final String SETT_NC_IMPORT_MODEL = "sett_nc_import_model";
	/**
	 * 打印签章服务url地址
	 */
	public static final String GLOBAL_SIGNATURE_SERVER_URL = "global.signature.server.url";

}
