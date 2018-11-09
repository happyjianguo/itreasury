/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * 系统环境对象<br>
 * 保存当前系统所有的系统环境和参数
 * @author mxzhou
 */
public class Env
{
	/**当前系统环境对象的静态实例*/
	private static Env INSTANCE = new Env(); //静态实例
	/**环境配置对象的属性*/
	//private File propfile = null; //对应于属性文件的文件对象变量
	//private File officeFile = null; //对应于办事处属性文件的文件对象变量
	//private long propfile_LastModifiedTime = 0; //属性文件的最后修改日期
	private Properties properties = null; //属性文件所对应的属性对象变量
	//private Properties offices = null; //办事处属性信息
	private static Properties DEFAULT_PROPERTIES = null; //记录配置项的默认配置值
	/**********************************/
	/**以下配置的系统级的常量        **/
	/**********************************/
	public static final String PROP_FILE_NAME = "bankportal.properties";//属性文件名
	//public static final String OFFICE_FILE_NAME = "office.properties";//属性文件名
	/**上传文档的存储目录名称**/
	public static final String UPLOAD_DIR_NAME = "bp_upload";
	public static final String UPLOAD_ACCOUNTDOC_DIR_NAME = "accountdoc";
	public static final String UPLOAD_TEMP_DIR_NAME="temp";
	public static final String UPLOAD_DATADOC_DIR_NAME = "datadoc";	
	/**指令导出文件路径及目录名称**/
	public static final String EXP_INSTRUCTION_PATH = "exp.instruction.path";
	//常量
	//导出文件的模板文件夹名称
	public static final String EXP_MODEL_DIR_NAME = "model";
	//导出指令的付款账号映射文件夹名称
	public static final String EXP_MAPPING_DIR_NAME = "acctnomapping";
	//导出文件的数据文件夹名称
	public static final String EXP_DATA_DIR_NAME = "data";	
	/**交易ID管理文件相关配置项**/
	public static final String TRANSID_FILE_NAME = "transid.txt";	
	/**监控结果对象存储文件的相关配置**/
	public static final String MONITORRESULT_FILE_NAME = "resultobject.tmp";
	/**用于查询报表的临时对象文件**/
	public static final String REPORTOBJECT_FILE_NAME1 = "reportobject1.tmp";
	public static final String REPORTOBJECT_FILE_NAME2 = "reportobject2.tmp";
	/**记录导出指令文件的序号和导出时间**/
	public static final String EXPINSTRUCTION_FILE_NAME = "expinstrobject.tmp";
	/**********************************/
	/**以下配置的都是配置项的配置名称**/
	/**********************************/
	/**基础配置项*/
	public static final String G_PROJECT_NAME = "project.name";
	public static final String G_STYLE_NAME = "style.name";
	public static final String G_BANKPORTAL_URL = "bankportal.url";
	public static final String G_LANGUAGE = "language";
	public static final String G_CLIENT_NAME = "client.name";
	public static final String G_CLIENT_ENGNAME = "client.engname";
	public static final String G_RUN_ENVIRONMENT = "run.environment";
	public static final String G_APPSERVER_VERSION = "appserver.version";
	public static final String G_UTC = "utc";
	public static final String G_LOGIN_URL="login.url";
	/**数据库环境配置项*/
	public static final String DB_TYPE = "db.type";
	public static final String DB_CONNECTION_TYPE = "db.conn.type";
	public static final String DB_IP = "db.ip";
	public static final String DB_SID = "db.sid";
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_PORT = "db.port";	
	public static final String DB_CONNECTION_NUM = "db.conn.num";//连接池最大连接数
	//数据库的用户名和密码是否加密,true,加密，false，不加密,默认为不加密
	public static final String DB_ISENCRYPTED = "db.isencrypted"; 
	/**LDAP环境配置项**/
	public static final String LDAP_IP = "ldap.ip";	
	public static final String LDAP_PORT = "ldap.port";	
	/**集成服务配置**/
	public static final String INTEGRATION_SERVICE_TYPE = "integration.service.type";
	/**rmi服务端口**/
	public static final String INTEGRATION_RMI_PORT = "integration.rmi.port";
	/**自动通知服务配置**/
	public static final String NOTICE_TRANSNOTICE = "notice.transnotice";
	public static final String NOTICE_TRANSNOTICE_FLAG = "notice.transnotice.flag";
	public static final String NOTICE_TRANSNOTICE_IMPL = "notice.transnotice.impl";
	public static final String NOTICE_TRANSNOTICE_IMPL_PARAM = "notice.transnotice.impl.param";
    /**代理交易通知服务配置*/
    public static final String NOTICE_AGENTTRANSNOTICE_IMPL = "notice.agenttransnotice.impl";
    /**指令及指令状态通知服务配置*/
    public static final String NOTICE_VIREMENTRESULTNOTICE_IMPL = "notice.virementresultnotice.impl";
	/**农行自动通知信息匹配特殊标识符**/
    public static final String ABC_NOTICE_TRANSFLAG="abc.notice.transflag";
	/**数据库type4连接资源的JNDI名称**/
	public static final String DB_TYPE4_NAME = "db.type4.name";	
	/**账户的三个属性*/
	public static final String FIELDNAME_ACCTPROPERTYONE = "fieldname.acctpropertyone";
	public static final String FIELDNAME_ACCTPROPERTYTWO = "fieldname.acctpropertytwo";
	public static final String FIELDNAME_ACCTPROPERTYTHREE = "fieldname.acctpropertythree";
	/**上传存储文件相关配置项*/
	public static final String UPLOAD_FILE_PATH = "upload.file.path";
	/** log4j配置文件名称 **/
	public static final String LOG4J_CONFIGFILE_NAME = "log4j.configfile.name"; //Log4j日志配置文件名称	
	/**系统接口配置项*/
	public static final String INTERFACE_USERIMPL = "interface.userimpl";//用户接口
	public static final String INTERFACE_EXCHANGERATEIMPL = "interface.exchangerateimpl";//汇率接口
	public static final String INTERFACE_CAPITALPLAYIMPL = "interface.capitalplay.impl";//资金计划
	public static final String INTERFACE_BPLOG = "interface.bplogimpl";//系统日志实现类
    public static final String BANKSERVICE_CONFIGFILE_NAME="bankservice.configfile.name"; 	
    /**是否需要系统日志功能**/
    public static final String ISNEEDBPLOG = "isNeedBpLog";
    /**邮件提醒方式相关配置**/
    public static final String REMIND_EMAIL_REMINDNUM = "remind.email.remindnum";//提醒次数
	public static final String REMIND_EMAIL_SERVER = "remind.email.server";//邮件服务器
    public static final String REMIND_EMAIL_USER = "remind.email.user";//用户名
    public static final String REMIND_EMAIL_PASS = "remind.email.pass";//密码
    public static final String REMIND_EMAIL_FROM = "remind.email.from";//发件人
    /**监控任务执行的最小时间间隔    单位为分钟**/
    public static final String MONITORTASK_INTERVAL = "monitortask.interval";    
    /**支付指令创建后是否自动发送**/
    public static final String ISAUTOSEND = "isautosend";    
    /**基础币种**/
    public static final String CURRENCY="currency";
    /**上传单个文件大小限制**/
    public static final String UPLOAD_FILE_SIZE="upload.file.size";
    /**总上传文件大小限制**/
    public static final String UPL0AD_TOTAL_FILE_SIZE="upload.total.file.size";
    /**打印行数设置（每页）**/
    public static final String NPAGELINE="nPageLine";
    /**是否屏蔽客户设置，客户设置， */
    public static final String ISCLIENT = "isclient";
    /**是否屏蔽汇率设置的新增，修改，删除功能**/
    public static final String ISRATE = "israte";
    /**是否屏蔽关联编号设置的新增，修改，删除功能**/
    public static final String ISRELATIONNO = "isrelationno";
    /**是否屏蔽币种设置的新增，修改，删除功能**/
    public static final String ISCURRENCY = "iscurrency";
    /**是否屏蔽折算币种放大镜*/
    public static final String ISCURRENCYMAPPING = "iscurrencymapping";
    /**放大镜列名称编码设置**/
    public static final String APPMAGNIFIER_CODE="appmagnifier_code";
    /**银企接口初始化通讯模块，标识字段**/
    public static final String BSCOMM_FLAG="bscomm_flag";
    /**直连银行配置项**/
    public static final String DIRECTBANKTYPE="directBankType";
    /**Excel导入银行配置项**/
    public static final String IMPORTBANKTYPE="importBankType";
    /**是否需要支持多国**/
    public static final String ISNEEDCOUNTRY="isNeedCountry";
    /**版权标示**/
    public static final String COPYRIGHT="copyRight";
    /**是否需要记录用户登录日志**/
    public static final String isNeedUserLog = "isNeedUserLog";
    /**是否需要系统认证处理**/
    public static final String isNeedCertificate="isNeedCertificate";
    /**是否在进行指令发送时，使用CFCA认证*/
    public static final String isCertWhenSendInstruction="isCertWhenSendInstruction";
    /**自动上划的摘要*/
    public static final String GATHER_ABSTRACTINFO="gather.abstractinfo";
    /**自动划拨时，显示的上级账户的类型（收入户：1，支出户：2，收支户：3）*/
    public static final String GATHER_UPACCT_INPUTOROUTPUT="gather.upacct.inputoroutput";
    /**自动划拨时，显示的二级账户的类型（收入户：1，支出户：2，收支户：3）*/
    public static final String GATHER_INPUTOROUTPUT="gather.inputoroutput";
    /**自动划拨时，生成的指令是否自动发送。（1：自动发送，2：手动发送）*/
    public static final String GATHER_AUTOGATHER_ISAUTOSEND = "gather.autogather.isautosend";
    /**资金划拨策略设置时，是按客户的上下级关系还是按照账户的上下级关系来过滤。1，按客户等级划拨；2，按账户等级划拨;3,不安客户，账号上下级允许平级间划拨*/
    public static final String GATHER_AUTOGATHER_SOURCEOFGATHERACCOUNT = "gather.autogather.sourceofgatheraccount";
    /**自动上划，指令是否加急*/
    public static final String GATHER_INSTRUCTION_ISPRIOR="gatherInstructionIsPrior";
    /**自动下拨，指令是否加急*/
    public static final String ASSIGN_INSTRUCTION_ISPRIOR="assignInstructionIsPrior";    
    /**自动下拨的摘要*/
    public static final String AUTOTASK_ASSIGN_ABSTRACTINFO="autotask.assign.abstractinfo";
    
    /**各家指令导出的ftp连接参数的后缀，各个参数的名称定义：直联号(大写)+ 对应后缀**/
    public static final String EXPINSTRFTPIP = "ExpInstrFtpIP";//ftp ip
    public static final String EXPINSTRFTPPORT = "ExpInstrFtpPort";//ftp port
    public static final String EXPINSTRFTPUSER = "ExpInstrFtpUser";//ftp user
    public static final String EXPINSTRFTPPASS = "ExpInstrFtpPass";//ftp password
    public static final String EXPINSTRFTPPATH = "ExpInstrFtpPath";//ftp path
    public static final String EXPINSTRFTPPATHFX = "ExpInstrFtpPathFx";//ftp path fx
    /**日志异常栈输出文件路径**/
    public static final String EXCEPTION_FILE_PATH = "exception.file.path";
    public static final String AUTOTASK_EXECFREQUENCY = "autotask.execfrequency";
    /**
     * 总部办事处ID
     * 如果是-1，则都是总部
     * **/
    public static final String HQOFFICEID = "HQOfficeID";
    /**银行是否需要考虑办事处**/
    public static final String HASBANKOFFICE = "hasBankOffice";
    /**客户是否需要考虑办事处**/
    public static final String HASCLIENTOFFICE = "hasClientOffice";
    /**账户属性一,二,三是否需要考虑办事处**/
    public static final String HASACCOUNTPROPERTYOFFICE = "hasAccountPropertyOffice";
    /**自动导入及自动核对任务是否区分办事处**/
    public static final String HASAUTOTASKOFFICE = "hasAutoTaskOffice";
    /**创建指令的时候，是否需要考虑同行他行**/
    public static final String ISNEEDSAMEBANK = "isNeedSameBank";
    
    /**指令发送时是否允许修改指令信息，比如是否加急等**/
    public static final String ISMODIFYINSTRINFO = "isModifyInstrInfo";
    /**银行设置，直联编号是否必输,默认为不必输*/
    public static final String ISNEEDREFERENCECODE= "isNeedReferenceCode";
    /**工行代理汇兑收款方网点**/
    public static final String RECNETSTATION="recNetStation";
    
    /**指令明晰页面是否显示成境外风格，是否为境外，比如地址在境内是省市，境外是地址一地址二**/
    public static final String isForeignInstr="isForeignInstr";

    /**交易ID的取值类型，是否取sequence**/
    public static final String transID_isSequence="transID.isSequence";
        
    /**配置资金监控支持的外部系统id，可以配多个，用","隔开**/
    public static final String outerSystemID="outerSystemID";
    
    /**配置系统是否对基础信息使用缓存,默认为:false**/
    public static final String isUseCache="isUseCache";    
    
    /**查询同一账户的历史余额和历史交易信息的间隔时间，单位是分钟*/
    public static final String AUTOTASK_CHECKINTERVAL = "autotask.checkinterval";
    /**自动任务的自恢复时间，单位是分钟*/
    public static final String AUTOTASK_RESUMETIME = "autotask.resumetime";
    /**核对，只核对余额，不核对交易的银行*/
    public static final String AUTOTASK_ONLYCHECKBALANCE = "autotask.onlycheckbalance";
    /**是否对系统的访问情况进行记录*/
    public static final String ISNEEDACCESSLOG = "isNeedAccessLog";
    /**监控系统的访问日志是否和其它系统共用**/
    public static final String ISACCESSLOGSHARE = "isaccesslogshare";
    /**中核专用，默认内部户，通知入账时用*/
    public static final String DEFAULTSUBJECTCODE = "defaultSubjectcode";    
    /**手工核对时，是否只允许单账户核对,默认为true*/
    public static final String isCheckOnlyOneAcct = "isCheckOnlyOneAcct";
    /**手工核对时，是否有日期限制，默认为true*/
    public static final String isCheckDateLimit = "isCheckDateLimit";
    /**是否需要校验数据权限*/
    public static final String isNeedDataPrivilege = "isNeedDataPrivilege";
    /**用户登录时是否需要自动数据权限授权*/
    public static final String isNeedAutoAuthorize = "isNeedAutoAuthorize";
    /**余额查询列表，增加可显示的栏位*/
    public static final String balanceListDisplayRows = "balanceListDisplayRows";
    /**账户销户时是否需要审核*/
    public static final String isNeedCheckWhenClosingAccount = "isNeedCheckWhenClosingAccount";
    /**账户修改，用户是否只能修改自己录入的账户*/
    public static final String AccountSetting_WhoInputWhoModify = "AccountSetting.WhoInputWhoModify";   
    /**非账户审核人，也能取消审核*/
    public static final String AccountUncheck_Anyone= "AccountUncheck.Anyone";
    /**是否共享基础信息,中油境外用*/
    public static final String ISBASEINFOSHARE = "isbaseinfoshare";
    /**资金下拨是否需要检查内部户余额*/
    public static final String ASSIGNFUND_ISCHECK_SETTACCTBALCNE = "Assignfund.Ischeck.Settacctbalance";
    /**中油项目日志记录，记录在login表中*/
    public static final String ISCPFLOG = "isCpfLog";
    /**集成接口实现中，当某银行不支持代理汇兑时，将业务类型改为指定的类型
     * 格式为：CIBICIB,8;CMBCHINA,3;
     * */
    public static final String INTEGRATION_CHANGETOOTHERVIREMENT = "integration.changeToOtherVirement";
    /**入账时先入借方交易还是先入贷方交易
     * 格式为：true 贷方,false 借方, null 默认
     * */
    public static final String NOTICE_TRANSNOTICE_ORDERBYDEBITFIRST = "notice.transnotice.orderbyDebitFirst";
    /**交易信息补录时，是否需要审核
	 *格式为：true 需要进行审核，false 不需要审核；	
     */
    public static final String MODIFYTRANSACTION_ISNEEDCHECK = "modifytransaction.isneedcheck";
    /**
     * 资金上划前，是否检查是否存在[已保存]，[处理中]，[未知]指令(防止透支)
     * */
    public static final String GATHER_ISCHECKPROCESSINGINSTRUCTION = "gather.ischeckprocessinginstruction";
    
	static {
		/**设置配置项的默认配置*/
		DEFAULT_PROPERTIES = new Properties();
		/**基础配置项*/
		DEFAULT_PROPERTIES.put(G_PROJECT_NAME, "bankportal");
		DEFAULT_PROPERTIES.put(G_STYLE_NAME, "bankportal");
		DEFAULT_PROPERTIES.put(G_BANKPORTAL_URL, "/iTreasury-bankportal");
		DEFAULT_PROPERTIES.put(G_LANGUAGE, "zh");
		DEFAULT_PROPERTIES.put(G_CLIENT_NAME, "软通动力");
		DEFAULT_PROPERTIES.put(G_CLIENT_ENGNAME, "isoftstone");
		DEFAULT_PROPERTIES.put(G_RUN_ENVIRONMENT, "windows2000");
		
		/**集成服务配置**/
		DEFAULT_PROPERTIES.put(INTEGRATION_RMI_PORT, "2005");
		DEFAULT_PROPERTIES.put(INTEGRATION_SERVICE_TYPE, "rmi");	
		
		/**自动通知服务配置**/
		DEFAULT_PROPERTIES.put(NOTICE_TRANSNOTICE, "false");
		DEFAULT_PROPERTIES.put(NOTICE_TRANSNOTICE_FLAG, "**");
		
		/** 数据库type4连接资源的JNDI名称**/
		DEFAULT_PROPERTIES.put(DB_TYPE4_NAME, "jdbc/jdbc-bp");
		DEFAULT_PROPERTIES.put(DB_SID,"dbk3");
		DEFAULT_PROPERTIES.put(DB_USERNAME,"xinaoadmin");
		DEFAULT_PROPERTIES.put(DB_PASSWORD, "");
		DEFAULT_PROPERTIES.put(DB_ISENCRYPTED,"false");
		
		/**账户的三个属性*/
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYONE, "账户属性一");
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYTWO, "账户属性二");
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYTHREE, "账户属性三");

		/**上传目录 **/
		DEFAULT_PROPERTIES.put(UPLOAD_FILE_PATH, File.separator); //jre默认工作目录
		
		/**log4j配置文件名称 **/
		DEFAULT_PROPERTIES.put(LOG4J_CONFIGFILE_NAME, "commons-logging.properties"); 
		
		DEFAULT_PROPERTIES.put(BANKSERVICE_CONFIGFILE_NAME, "bank_service_config.xml");
		/**币种配置**/
		DEFAULT_PROPERTIES.put(CURRENCY,"USD");
		/**上传文件大小配置**/
		DEFAULT_PROPERTIES.put(UPLOAD_FILE_SIZE,"2100000");
		/**上传总的文件大小配置**/
		DEFAULT_PROPERTIES.put(UPL0AD_TOTAL_FILE_SIZE,"11000000");
		/**打印页面行数配置**/
		DEFAULT_PROPERTIES.put(NPAGELINE,"20");
		DEFAULT_PROPERTIES.put(ISCLIENT,"false");
		DEFAULT_PROPERTIES.put(ISRATE,"false");
		DEFAULT_PROPERTIES.put(ISRELATIONNO,"false");
		DEFAULT_PROPERTIES.put(ISCURRENCY,"false");
		DEFAULT_PROPERTIES.put(ISCURRENCYMAPPING,"true");
		DEFAULT_PROPERTIES.put(DB_CONNECTION_NUM,"50");
		/**默认的放大镜列名称编码设置**/
		DEFAULT_PROPERTIES.put(APPMAGNIFIER_CODE,"GBK");
		/**默认监控任务执行的最小时间间隔**/
		DEFAULT_PROPERTIES.put(MONITORTASK_INTERVAL,"10");
		/**默认银企接口通讯模块初始化配置**/
		DEFAULT_PROPERTIES.put(BSCOMM_FLAG,"false");
		DEFAULT_PROPERTIES.put(ISNEEDCOUNTRY,"true");
		/**版权标示**/
		DEFAULT_PROPERTIES.put(COPYRIGHT, "软通动力");
		
		// 是否需要记录用户登录日志
		//DEFAULT_PROPERTIES.put(isNeedUserLog, "false");		
		
		/**是否需要记录用户登录日志**/
		DEFAULT_PROPERTIES.put(isNeedUserLog, "false");	
		/**是否需要证书认证**/
		DEFAULT_PROPERTIES.put(isNeedCertificate,"true");
		/**是否需要账户类型**/
		//DEFAULT_PROPERTIES.put(isNeedAccountType,"true");
		/**对应的银行**/
		//DEFAULT_PROPERTIES.put(oppNeedBankType,"ABC");
		/**是否在发送指令时使用cfca认证*/
		DEFAULT_PROPERTIES.put(isCertWhenSendInstruction,"false");
		/**标准时差默认值8*60**/
		DEFAULT_PROPERTIES.put(G_UTC,"480");
		/**创建支付指令后是否自动发送*/
		DEFAULT_PROPERTIES.put(ISAUTOSEND,"false");
		/**自动上划的摘要信息*/
		DEFAULT_PROPERTIES.put(GATHER_ABSTRACTINFO,"资金上划");
		DEFAULT_PROPERTIES.put(GATHER_INPUTOROUTPUT,"-1");
		DEFAULT_PROPERTIES.put(GATHER_AUTOGATHER_ISAUTOSEND,"1");
		DEFAULT_PROPERTIES.put(GATHER_AUTOGATHER_SOURCEOFGATHERACCOUNT,"1");
		DEFAULT_PROPERTIES.put(GATHER_INSTRUCTION_ISPRIOR,"false");
        DEFAULT_PROPERTIES.put(ASSIGN_INSTRUCTION_ISPRIOR,"false");        

		/**新的自动任务的执行频率*/
		DEFAULT_PROPERTIES.put(AUTOTASK_EXECFREQUENCY,"60");
		/**办事处ID，默认为-1，即不考虑办事处**/
		DEFAULT_PROPERTIES.put(HQOFFICEID,"-1");
		/**银行是否需要考虑办事处**/
		DEFAULT_PROPERTIES.put(HASBANKOFFICE,"false");
	    /**客户是否需要考虑办事处**/
		DEFAULT_PROPERTIES.put(HASCLIENTOFFICE,"false");
		/**账户属性一,二,三是否需要考虑办事处**/
		DEFAULT_PROPERTIES.put(HASACCOUNTPROPERTYOFFICE,"false");
        /**自动导入及自动核对任务是否区分办事处**/
        DEFAULT_PROPERTIES.put(HASAUTOTASKOFFICE, "false");
		/**创建指令的时候，是否需要考虑同行他行，目前境外银行还不考虑同行他行**/	    
	    DEFAULT_PROPERTIES.put(ISNEEDSAMEBANK,"true");
	    /**指令发送时是否允许修改指令信息，比如是否加急等，默认是可以修改的**/
	    DEFAULT_PROPERTIES.put(ISMODIFYINSTRINFO,"true");
	    DEFAULT_PROPERTIES.put(ISNEEDREFERENCECODE,"false");	     
	    DEFAULT_PROPERTIES.put(INTERFACE_EXCHANGERATEIMPL,"com.iss.itreasury.fcinterface.bankportal.setting.exchangerate.ExchangeRateImpl");
	    DEFAULT_PROPERTIES.put(INTERFACE_USERIMPL,"com.iss.itreasury.fcinterface.bankportal.usermgt.userimpl.SysModuleUserImpl");
	    DEFAULT_PROPERTIES.put(RECNETSTATION,"");
	    /**默认为境内**/
	    DEFAULT_PROPERTIES.put(isForeignInstr,"false");	 
	    /**默认是0, 1, 2,分别为资金监控系统，结算和网银**/
	    DEFAULT_PROPERTIES.put(outerSystemID,"0,1,2");
	    DEFAULT_PROPERTIES.put(AUTOTASK_RESUMETIME, "60");
	    /**默认为不使用sequence**/
	    DEFAULT_PROPERTIES.put(transID_isSequence,"false");	
	    /**资金监控基础信息是否使用缓存，默认为使用**/
	    DEFAULT_PROPERTIES.put(isUseCache,"true");	  
	    DEFAULT_PROPERTIES.put(ISNEEDACCESSLOG, "false");
	    DEFAULT_PROPERTIES.put(ISACCESSLOGSHARE, "false");
	    /**邮箱提醒默认值*/
	    DEFAULT_PROPERTIES.put(REMIND_EMAIL_REMINDNUM, "3");
	    /**中核专用，默认的通知入账用内部户*/
	    DEFAULT_PROPERTIES.put(DEFAULTSUBJECTCODE, "");
	    /**手工核对时，是否只允许单账户核对,默认为true*/
	    DEFAULT_PROPERTIES.put(isCheckOnlyOneAcct, "true");
	    /**手工核对时，是否有日期限制，默认为true*/
	    DEFAULT_PROPERTIES.put(isCheckDateLimit, "true");
	    /**是否需要校验数据权限，默认为false*/
	    DEFAULT_PROPERTIES.put(isNeedDataPrivilege, "false");	   
	    /**用户登录时是否需要自动数据权限授权*/
	    DEFAULT_PROPERTIES.put(isNeedAutoAuthorize, "false");
	    DEFAULT_PROPERTIES.put(balanceListDisplayRows, "");
        DEFAULT_PROPERTIES.put(isNeedCheckWhenClosingAccount, "false");
        /**银行账户，是否是谁录入谁修改，默认为true,和原先的程序保存一致*/
        DEFAULT_PROPERTIES.put(AccountSetting_WhoInputWhoModify, "true");
        /**是否共享基础信息,中油境外用*/
        DEFAULT_PROPERTIES.put(ISBASEINFOSHARE, "false");
        /**资金下拨是否需要检查内部户余额*/
        DEFAULT_PROPERTIES.put(ASSIGNFUND_ISCHECK_SETTACCTBALCNE, "false");
        DEFAULT_PROPERTIES.put(AUTOTASK_ASSIGN_ABSTRACTINFO, "资金下拨");
        /**中油项目日志记录*/
        DEFAULT_PROPERTIES.put(ISCPFLOG,"false");
        /**交易信息补录时是否需要审核*/
        DEFAULT_PROPERTIES.put(MODIFYTRANSACTION_ISNEEDCHECK, "false");
        /**自动上划时，是否检查存在处理中的指令*/
        DEFAULT_PROPERTIES.put(GATHER_ISCHECKPROCESSINGINSTRUCTION, "false");
	}

	/**
	 * 私有的构造方法，用以保证外界无法直接实例化
	 */
	private Env()
	{
		super();

//		this.propfile = new File(PROP_FILE_NAME);
		
//		this.propfile_LastModifiedTime = propfile.lastModified();
//
//		if (this.propfile_LastModifiedTime == 0)
//		{
//			System.err.println(PROP_FILE_NAME + " file does exist !");
//		}

		try
		{
			properties = new Properties();			
			FileInputStream is = new FileInputStream(PROP_FILE_NAME);			
			properties.load(is);			
			is.close();			
			
//			Enumeration enu = properties.keys();
//			if (enu != null)
//			{
//				while (enu.hasMoreElements())
//				{
//					String key = (String) enu.nextElement();
//					//转换字符集
//					try
//					{
//						String strValue = INSTANCE.properties.getProperty(key);
//						if(strValue != null)
//						{
//							strValue = new String(strValue.getBytes("ISO8859_1"),"GBK");							
//						}						
//						INSTANCE.properties.setProperty(key,strValue);
//					}
//					catch(Exception e)
//					{								
//					}							
//				}
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取静态实例
	 * @return
	 */
	public static Env getInstance()
	{
		return INSTANCE;
	}

	/**
	 * 构造所有配置项的显示信息
	 * @return String
	 */
	public static String showAllEnvConfigItem()
	{
		StringBuffer sbTemp = new StringBuffer(128);

		Enumeration enu = INSTANCE.properties.keys();

		if (enu != null)
		{
			String key = null;

			while (enu.hasMoreElements())
			{
				key = (String) enu.nextElement();
				//转换字符集
//				try
//				{
//					key = new String(key.getBytes("ISO8859_1"),"GBK");
//				}
//				catch(Exception e)
//				{
//					
//				}
				sbTemp.append(
					"EnvItem  "
						+ key
						+ " : "
						+ INSTANCE.properties.getProperty(key) + "\n");
			}
		}

		return sbTemp.toString();
	}
	/**
	 * 获取指定名称的配置信息
	 * @param name
	 * @return
	 */
	public static String getEnvConfigItem(String name)
	{
		String val = null;

		// 检查属性文件是否被修改过,必须加锁
//		synchronized (INSTANCE.propfile)
//		{
//			long newTime = INSTANCE.propfile.lastModified();
			// 如果是，读取属性文件
//			if (newTime > INSTANCE.propfile_LastModifiedTime)
//			{
//				INSTANCE.properties.clear();
//
//				try
//				{
//					FileInputStream is = new FileInputStream(PROP_FILE_NAME);
//					INSTANCE.properties.load(is);
//					is.close();
//					
////					Enumeration enu = INSTANCE.properties.keys();
////					if (enu != null)
////					{
////						while (enu.hasMoreElements())
////						{
////							String key = (String) enu.nextElement();
////							//转换字符集
////							try
////							{
////								String strValue = INSTANCE.properties.getProperty(key);
////								if(strValue != null)
////								{
////									strValue = new String(strValue.getBytes("ISO8859_1"),"GBK");
////								}								
////								INSTANCE.properties.setProperty(key,strValue);
////							}
////							catch(Exception e)
////							{								
////							}							
////						}
////					}
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}

		val = INSTANCE.properties.getProperty(name);
		//对属性文件的内容进行转码
		//之所以不在load入文件的时候统一做转码，
		//是因为在往Properties里set之后，之前做的转码工作无效，
		//可能在Hashtable的put方法里再对值进行了转码
		//这是测试后得出的结论
        if(val != null)
        {
        	try
			{
				val = new String(val.getBytes("ISO8859_1"),"GBK");
			} catch (UnsupportedEncodingException e)
			{				
			}
        }
		//如果未配置，则读取默认值
		if (val == null)
		{
			val = DEFAULT_PROPERTIES.getProperty(name);
		}		
        
		return val;
	}

	/**
	 * 提供系统的基准时间，当前方法实现以数据库服务器日期时间为准。
	 */
	public static Date getSystemDateTime() throws SystemException
	{
//		try
//		{
			/**
			 * 因为效率问题，当前时间直接取应用服务器时间--by jsxie
			 * */
//			SystemDAO systemDAO =
//				(SystemDAO) DAOFactory.getDAOImpl(SystemDAO.class, null);
//			return systemDAO.getSystemDateTime();
			return new Date();
//		}
//		catch (SystemException e)
//		{
//			e.printStackTrace();
//			throw new SystemException(
//				"获取数据库系统时间时出现异常，出错原因：" + e.getMessage(),
//				e);
//		}
	}
	
	/**
	 * 根据当前时差，获取世界标准时间
	 */
	public static Date getUTCDateTime() throws SystemException
	{
		String utcLocal = getEnvConfigItem(G_UTC);
		long utcMin = -1;
		try
		{
			utcMin = Long.parseLong(utcLocal);
		}
		catch(Exception e)
		{
			throw new SystemException("utc时间设置非法", e);
		}
		Date systemTime = getSystemDateTime();
		Date utcDateTime = new Date(systemTime.getTime() - utcMin*60*1000);
		return utcDateTime;
	}

	/**
	 * 返回系统定义的空时间 由于ITreasuryDAO无法支持通过在DataEntity中Setter函数中通过set
	 * null将数据库中的Date类型更新为空值，因此通过 定义一个系统内的空时间(1970-01-01
	 * 08:00:00.0)作为空时间的标志时间，需要将数据库中Date类型数据更新为null的
	 * 操作，在执行setXXX操作时，调用此操作获取系统定义的空时间标志，DAO将在setTimeStamp时判断时间是否为
	 * 此时间，如果是，则更新该字段为null
	 */
	public static Date getNullTimeStamp()
	{
		return new Date(0);
	}
    
	/**
     * 判断某一办事处是否是总部
     * 是，返回true；否则，返回false
     * 
	 * @param officeID
	 * @return
	 */
	public static boolean isHQ(long officeID)
	{
		long lOfficeID = Long.parseLong(getEnvConfigItem(HQOFFICEID));
		if(lOfficeID < 0 || lOfficeID == officeID)
		{
			return true;
		}		
		return false;
	}
	
	//返回自动任务的自恢复时间
	public static long getResumeTime()
	{
		long resumeTime = 60*60*1000;
		try{
			String strTemp = Env.getEnvConfigItem(Env.AUTOTASK_RESUMETIME);
			if(strTemp!=null)
			{
				resumeTime = Long.parseLong(strTemp)*60*1000;
			}
			if(resumeTime<=0)
			{
				resumeTime=60*60*1000;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resumeTime = 60*60*1000;
		}
		return resumeTime;
	}

    /**
     * 返回configItem对应的值，true或者false。
     * configItem的值只能为true或false
     * 
     * @param configItem
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isTrue(String configItem) {
        
        String strTemp = getEnvConfigItem(configItem);
        
        if(strTemp==null){
            throw new IllegalArgumentException("配置项["+configItem +"]值为空");
        }
        strTemp = strTemp.trim();
        if("true".compareToIgnoreCase(strTemp)==0)
        {
            return true;
        }else if("false".compareToIgnoreCase(strTemp)==0)
        {
            return false;
        }else{
            throw new IllegalArgumentException("配置项["+ configItem +"]值无效");
        }
    }
    
    
    /**
     * 判断指定的字符串是否存在于字符串数组中
     * 
     * @param strItems
     * @param aItem
     * @return
     */
    public static boolean containItems(String[] strItems, String aItem)
    {
        if(strItems==null || strItems.length<=0 )
        {
            return false;
        }
        if(aItem==null || aItem.length()<=0)
        {
            return false;
        }
        for(int i=0; i<strItems.length; i++)
        {
            if(aItem.equals(strItems[i])) return true;
        }
        return false;
    }
    
    /**
     * 用于账户余额查询时，获取可显示项的配置
     * 
     * @param envItemName
     * @return
     */
    public static String[] getOptionalDisplayItems(String envItemName)
    {
        String strConfig = Env.getEnvConfigItem(envItemName);
        String[] strItems = null;
        if(strConfig!=null)
        {
            strItems = DataFormat.splitString(strConfig, ";");
        }
        //过滤掉拆分后字符串数组中的null和""字符串
        ArrayList list = new ArrayList();
        for(int i=0; i<strItems.length; i++)
        {
            if(strItems[i]!=null && strItems[i].trim().length()>0)
            {
                list.add(strItems[i]);
            }
        }
        return (String[]) list.toArray(new String[0]);
    }

//    /**
//     * 当前余额查询，历史余额查询，直联账户余额查询，判断域是否在页面上可见。
//     * @param fieldName 
//     * @return
//     */
//    public static boolean isVisableOnBlanceQueryList(String fieldName)
//    {
//        boolean result = false;
//        return result;
//    }
    
   
	
	/**
     * 显示办事处列表
     * @param out
     * @param strControlName
     * @param nType
     * @param lSelectValue
     * @param isNeedAll
     * @param isNeedBlank
     * @param strProperty
     */
	/**public static void showOfficeList(JspWriter out, String strControlName,long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
    {    	
    	try {
			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + ">");
			if (isNeedBlank == true) {
				if (lSelectValue == -1) {
					out.println("<option value='-1' selected>&nbsp;</option>");
				} else {
					out.println("<option value='-1'>&nbsp;</option>");
				}
			}		
			if(INSTANCE.offices == null)
			{
				INSTANCE.officeFile = new File(OFFICE_FILE_NAME);
				INSTANCE.offices = new Properties(); 
				FileInputStream is1 = new FileInputStream(OFFICE_FILE_NAME);
				INSTANCE.offices.load(is1);
				is1.close();				
			}
			Set keySet = INSTANCE.offices.keySet();						
			if(keySet.size()>0)
			{
			    String[] keys = (String[])keySet.toArray(new String[0]);
				for(int i=keys.length-1;i>=0;i--)
				{					
					String value = (String)INSTANCE.offices.get(keys[i]);
					value = new String(value.getBytes("ISO8859_1"),"GBK");
					if (Long.parseLong(keys[i]) == lSelectValue) 
					{
						out.println("<option value='" + keys[i]
								+ "' selected >" + value + "</option>");
					}
					else 
					{
						out.println("<option value='" + keys[i] + "'>"
								+ value + "</option>");	
					}
				}
			}			
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value='0' selected>全部</option>");
				} else {
					out.println("<option value='0'>全部</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}		    
    }	**/
	
}
