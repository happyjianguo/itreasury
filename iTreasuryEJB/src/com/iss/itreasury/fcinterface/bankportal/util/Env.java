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
 * ϵͳ��������<br>
 * ���浱ǰϵͳ���е�ϵͳ�����Ͳ���
 * @author mxzhou
 */
public class Env
{
	/**��ǰϵͳ��������ľ�̬ʵ��*/
	private static Env INSTANCE = new Env(); //��̬ʵ��
	/**�������ö��������*/
	//private File propfile = null; //��Ӧ�������ļ����ļ��������
	//private File officeFile = null; //��Ӧ�ڰ��´������ļ����ļ��������
	//private long propfile_LastModifiedTime = 0; //�����ļ�������޸�����
	private Properties properties = null; //�����ļ�����Ӧ�����Զ������
	//private Properties offices = null; //���´�������Ϣ
	private static Properties DEFAULT_PROPERTIES = null; //��¼�������Ĭ������ֵ
	/**********************************/
	/**�������õ�ϵͳ���ĳ���        **/
	/**********************************/
	public static final String PROP_FILE_NAME = "bankportal.properties";//�����ļ���
	//public static final String OFFICE_FILE_NAME = "office.properties";//�����ļ���
	/**�ϴ��ĵ��Ĵ洢Ŀ¼����**/
	public static final String UPLOAD_DIR_NAME = "bp_upload";
	public static final String UPLOAD_ACCOUNTDOC_DIR_NAME = "accountdoc";
	public static final String UPLOAD_TEMP_DIR_NAME="temp";
	public static final String UPLOAD_DATADOC_DIR_NAME = "datadoc";	
	/**ָ����ļ�·����Ŀ¼����**/
	public static final String EXP_INSTRUCTION_PATH = "exp.instruction.path";
	//����
	//�����ļ���ģ���ļ�������
	public static final String EXP_MODEL_DIR_NAME = "model";
	//����ָ��ĸ����˺�ӳ���ļ�������
	public static final String EXP_MAPPING_DIR_NAME = "acctnomapping";
	//�����ļ��������ļ�������
	public static final String EXP_DATA_DIR_NAME = "data";	
	/**����ID�����ļ����������**/
	public static final String TRANSID_FILE_NAME = "transid.txt";	
	/**��ؽ������洢�ļ����������**/
	public static final String MONITORRESULT_FILE_NAME = "resultobject.tmp";
	/**���ڲ�ѯ�������ʱ�����ļ�**/
	public static final String REPORTOBJECT_FILE_NAME1 = "reportobject1.tmp";
	public static final String REPORTOBJECT_FILE_NAME2 = "reportobject2.tmp";
	/**��¼����ָ���ļ�����ź͵���ʱ��**/
	public static final String EXPINSTRUCTION_FILE_NAME = "expinstrobject.tmp";
	/**********************************/
	/**�������õĶ������������������**/
	/**********************************/
	/**����������*/
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
	/**���ݿ⻷��������*/
	public static final String DB_TYPE = "db.type";
	public static final String DB_CONNECTION_TYPE = "db.conn.type";
	public static final String DB_IP = "db.ip";
	public static final String DB_SID = "db.sid";
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_PORT = "db.port";	
	public static final String DB_CONNECTION_NUM = "db.conn.num";//���ӳ����������
	//���ݿ���û����������Ƿ����,true,���ܣ�false��������,Ĭ��Ϊ������
	public static final String DB_ISENCRYPTED = "db.isencrypted"; 
	/**LDAP����������**/
	public static final String LDAP_IP = "ldap.ip";	
	public static final String LDAP_PORT = "ldap.port";	
	/**���ɷ�������**/
	public static final String INTEGRATION_SERVICE_TYPE = "integration.service.type";
	/**rmi����˿�**/
	public static final String INTEGRATION_RMI_PORT = "integration.rmi.port";
	/**�Զ�֪ͨ��������**/
	public static final String NOTICE_TRANSNOTICE = "notice.transnotice";
	public static final String NOTICE_TRANSNOTICE_FLAG = "notice.transnotice.flag";
	public static final String NOTICE_TRANSNOTICE_IMPL = "notice.transnotice.impl";
	public static final String NOTICE_TRANSNOTICE_IMPL_PARAM = "notice.transnotice.impl.param";
    /**������֪ͨ��������*/
    public static final String NOTICE_AGENTTRANSNOTICE_IMPL = "notice.agenttransnotice.impl";
    /**ָ�ָ��״̬֪ͨ��������*/
    public static final String NOTICE_VIREMENTRESULTNOTICE_IMPL = "notice.virementresultnotice.impl";
	/**ũ���Զ�֪ͨ��Ϣƥ�������ʶ��**/
    public static final String ABC_NOTICE_TRANSFLAG="abc.notice.transflag";
	/**���ݿ�type4������Դ��JNDI����**/
	public static final String DB_TYPE4_NAME = "db.type4.name";	
	/**�˻�����������*/
	public static final String FIELDNAME_ACCTPROPERTYONE = "fieldname.acctpropertyone";
	public static final String FIELDNAME_ACCTPROPERTYTWO = "fieldname.acctpropertytwo";
	public static final String FIELDNAME_ACCTPROPERTYTHREE = "fieldname.acctpropertythree";
	/**�ϴ��洢�ļ����������*/
	public static final String UPLOAD_FILE_PATH = "upload.file.path";
	/** log4j�����ļ����� **/
	public static final String LOG4J_CONFIGFILE_NAME = "log4j.configfile.name"; //Log4j��־�����ļ�����	
	/**ϵͳ�ӿ�������*/
	public static final String INTERFACE_USERIMPL = "interface.userimpl";//�û��ӿ�
	public static final String INTERFACE_EXCHANGERATEIMPL = "interface.exchangerateimpl";//���ʽӿ�
	public static final String INTERFACE_CAPITALPLAYIMPL = "interface.capitalplay.impl";//�ʽ�ƻ�
	public static final String INTERFACE_BPLOG = "interface.bplogimpl";//ϵͳ��־ʵ����
    public static final String BANKSERVICE_CONFIGFILE_NAME="bankservice.configfile.name"; 	
    /**�Ƿ���Ҫϵͳ��־����**/
    public static final String ISNEEDBPLOG = "isNeedBpLog";
    /**�ʼ����ѷ�ʽ�������**/
    public static final String REMIND_EMAIL_REMINDNUM = "remind.email.remindnum";//���Ѵ���
	public static final String REMIND_EMAIL_SERVER = "remind.email.server";//�ʼ�������
    public static final String REMIND_EMAIL_USER = "remind.email.user";//�û���
    public static final String REMIND_EMAIL_PASS = "remind.email.pass";//����
    public static final String REMIND_EMAIL_FROM = "remind.email.from";//������
    /**�������ִ�е���Сʱ����    ��λΪ����**/
    public static final String MONITORTASK_INTERVAL = "monitortask.interval";    
    /**֧��ָ������Ƿ��Զ�����**/
    public static final String ISAUTOSEND = "isautosend";    
    /**��������**/
    public static final String CURRENCY="currency";
    /**�ϴ������ļ���С����**/
    public static final String UPLOAD_FILE_SIZE="upload.file.size";
    /**���ϴ��ļ���С����**/
    public static final String UPL0AD_TOTAL_FILE_SIZE="upload.total.file.size";
    /**��ӡ�������ã�ÿҳ��**/
    public static final String NPAGELINE="nPageLine";
    /**�Ƿ����οͻ����ã��ͻ����ã� */
    public static final String ISCLIENT = "isclient";
    /**�Ƿ����λ������õ��������޸ģ�ɾ������**/
    public static final String ISRATE = "israte";
    /**�Ƿ����ι���������õ��������޸ģ�ɾ������**/
    public static final String ISRELATIONNO = "isrelationno";
    /**�Ƿ����α������õ��������޸ģ�ɾ������**/
    public static final String ISCURRENCY = "iscurrency";
    /**�Ƿ�����������ַŴ�*/
    public static final String ISCURRENCYMAPPING = "iscurrencymapping";
    /**�Ŵ������Ʊ�������**/
    public static final String APPMAGNIFIER_CODE="appmagnifier_code";
    /**����ӿڳ�ʼ��ͨѶģ�飬��ʶ�ֶ�**/
    public static final String BSCOMM_FLAG="bscomm_flag";
    /**ֱ������������**/
    public static final String DIRECTBANKTYPE="directBankType";
    /**Excel��������������**/
    public static final String IMPORTBANKTYPE="importBankType";
    /**�Ƿ���Ҫ֧�ֶ��**/
    public static final String ISNEEDCOUNTRY="isNeedCountry";
    /**��Ȩ��ʾ**/
    public static final String COPYRIGHT="copyRight";
    /**�Ƿ���Ҫ��¼�û���¼��־**/
    public static final String isNeedUserLog = "isNeedUserLog";
    /**�Ƿ���Ҫϵͳ��֤����**/
    public static final String isNeedCertificate="isNeedCertificate";
    /**�Ƿ��ڽ���ָ���ʱ��ʹ��CFCA��֤*/
    public static final String isCertWhenSendInstruction="isCertWhenSendInstruction";
    /**�Զ��ϻ���ժҪ*/
    public static final String GATHER_ABSTRACTINFO="gather.abstractinfo";
    /**�Զ�����ʱ����ʾ���ϼ��˻������ͣ����뻧��1��֧������2����֧����3��*/
    public static final String GATHER_UPACCT_INPUTOROUTPUT="gather.upacct.inputoroutput";
    /**�Զ�����ʱ����ʾ�Ķ����˻������ͣ����뻧��1��֧������2����֧����3��*/
    public static final String GATHER_INPUTOROUTPUT="gather.inputoroutput";
    /**�Զ�����ʱ�����ɵ�ָ���Ƿ��Զ����͡���1���Զ����ͣ�2���ֶ����ͣ�*/
    public static final String GATHER_AUTOGATHER_ISAUTOSEND = "gather.autogather.isautosend";
    /**�ʽ𻮲���������ʱ���ǰ��ͻ������¼���ϵ���ǰ����˻������¼���ϵ�����ˡ�1�����ͻ��ȼ�������2�����˻��ȼ�����;3,�����ͻ����˺����¼�����ƽ���仮��*/
    public static final String GATHER_AUTOGATHER_SOURCEOFGATHERACCOUNT = "gather.autogather.sourceofgatheraccount";
    /**�Զ��ϻ���ָ���Ƿ�Ӽ�*/
    public static final String GATHER_INSTRUCTION_ISPRIOR="gatherInstructionIsPrior";
    /**�Զ��²���ָ���Ƿ�Ӽ�*/
    public static final String ASSIGN_INSTRUCTION_ISPRIOR="assignInstructionIsPrior";    
    /**�Զ��²���ժҪ*/
    public static final String AUTOTASK_ASSIGN_ABSTRACTINFO="autotask.assign.abstractinfo";
    
    /**����ָ�����ftp���Ӳ����ĺ�׺���������������ƶ��壺ֱ����(��д)+ ��Ӧ��׺**/
    public static final String EXPINSTRFTPIP = "ExpInstrFtpIP";//ftp ip
    public static final String EXPINSTRFTPPORT = "ExpInstrFtpPort";//ftp port
    public static final String EXPINSTRFTPUSER = "ExpInstrFtpUser";//ftp user
    public static final String EXPINSTRFTPPASS = "ExpInstrFtpPass";//ftp password
    public static final String EXPINSTRFTPPATH = "ExpInstrFtpPath";//ftp path
    public static final String EXPINSTRFTPPATHFX = "ExpInstrFtpPathFx";//ftp path fx
    /**��־�쳣ջ����ļ�·��**/
    public static final String EXCEPTION_FILE_PATH = "exception.file.path";
    public static final String AUTOTASK_EXECFREQUENCY = "autotask.execfrequency";
    /**
     * �ܲ����´�ID
     * �����-1�������ܲ�
     * **/
    public static final String HQOFFICEID = "HQOfficeID";
    /**�����Ƿ���Ҫ���ǰ��´�**/
    public static final String HASBANKOFFICE = "hasBankOffice";
    /**�ͻ��Ƿ���Ҫ���ǰ��´�**/
    public static final String HASCLIENTOFFICE = "hasClientOffice";
    /**�˻�����һ,��,���Ƿ���Ҫ���ǰ��´�**/
    public static final String HASACCOUNTPROPERTYOFFICE = "hasAccountPropertyOffice";
    /**�Զ����뼰�Զ��˶������Ƿ����ְ��´�**/
    public static final String HASAUTOTASKOFFICE = "hasAutoTaskOffice";
    /**����ָ���ʱ���Ƿ���Ҫ����ͬ������**/
    public static final String ISNEEDSAMEBANK = "isNeedSameBank";
    
    /**ָ���ʱ�Ƿ������޸�ָ����Ϣ�������Ƿ�Ӽ���**/
    public static final String ISMODIFYINSTRINFO = "isModifyInstrInfo";
    /**�������ã�ֱ������Ƿ����,Ĭ��Ϊ������*/
    public static final String ISNEEDREFERENCECODE= "isNeedReferenceCode";
    /**���д������տ����**/
    public static final String RECNETSTATION="recNetStation";
    
    /**ָ������ҳ���Ƿ���ʾ�ɾ������Ƿ�Ϊ���⣬�����ַ�ھ�����ʡ�У������ǵ�ַһ��ַ��**/
    public static final String isForeignInstr="isForeignInstr";

    /**����ID��ȡֵ���ͣ��Ƿ�ȡsequence**/
    public static final String transID_isSequence="transID.isSequence";
        
    /**�����ʽ���֧�ֵ��ⲿϵͳid��������������","����**/
    public static final String outerSystemID="outerSystemID";
    
    /**����ϵͳ�Ƿ�Ի�����Ϣʹ�û���,Ĭ��Ϊ:false**/
    public static final String isUseCache="isUseCache";    
    
    /**��ѯͬһ�˻�����ʷ������ʷ������Ϣ�ļ��ʱ�䣬��λ�Ƿ���*/
    public static final String AUTOTASK_CHECKINTERVAL = "autotask.checkinterval";
    /**�Զ�������Իָ�ʱ�䣬��λ�Ƿ���*/
    public static final String AUTOTASK_RESUMETIME = "autotask.resumetime";
    /**�˶ԣ�ֻ�˶������˶Խ��׵�����*/
    public static final String AUTOTASK_ONLYCHECKBALANCE = "autotask.onlycheckbalance";
    /**�Ƿ��ϵͳ�ķ���������м�¼*/
    public static final String ISNEEDACCESSLOG = "isNeedAccessLog";
    /**���ϵͳ�ķ�����־�Ƿ������ϵͳ����**/
    public static final String ISACCESSLOGSHARE = "isaccesslogshare";
    /**�к�ר�ã�Ĭ���ڲ�����֪ͨ����ʱ��*/
    public static final String DEFAULTSUBJECTCODE = "defaultSubjectcode";    
    /**�ֹ��˶�ʱ���Ƿ�ֻ�����˻��˶�,Ĭ��Ϊtrue*/
    public static final String isCheckOnlyOneAcct = "isCheckOnlyOneAcct";
    /**�ֹ��˶�ʱ���Ƿ����������ƣ�Ĭ��Ϊtrue*/
    public static final String isCheckDateLimit = "isCheckDateLimit";
    /**�Ƿ���ҪУ������Ȩ��*/
    public static final String isNeedDataPrivilege = "isNeedDataPrivilege";
    /**�û���¼ʱ�Ƿ���Ҫ�Զ�����Ȩ����Ȩ*/
    public static final String isNeedAutoAuthorize = "isNeedAutoAuthorize";
    /**����ѯ�б����ӿ���ʾ����λ*/
    public static final String balanceListDisplayRows = "balanceListDisplayRows";
    /**�˻�����ʱ�Ƿ���Ҫ���*/
    public static final String isNeedCheckWhenClosingAccount = "isNeedCheckWhenClosingAccount";
    /**�˻��޸ģ��û��Ƿ�ֻ���޸��Լ�¼����˻�*/
    public static final String AccountSetting_WhoInputWhoModify = "AccountSetting.WhoInputWhoModify";   
    /**���˻�����ˣ�Ҳ��ȡ�����*/
    public static final String AccountUncheck_Anyone= "AccountUncheck.Anyone";
    /**�Ƿ��������Ϣ,���;�����*/
    public static final String ISBASEINFOSHARE = "isbaseinfoshare";
    /**�ʽ��²��Ƿ���Ҫ����ڲ������*/
    public static final String ASSIGNFUND_ISCHECK_SETTACCTBALCNE = "Assignfund.Ischeck.Settacctbalance";
    /**������Ŀ��־��¼����¼��login����*/
    public static final String ISCPFLOG = "isCpfLog";
    /**���ɽӿ�ʵ���У���ĳ���в�֧�ִ�����ʱ����ҵ�����͸�Ϊָ��������
     * ��ʽΪ��CIBICIB,8;CMBCHINA,3;
     * */
    public static final String INTEGRATION_CHANGETOOTHERVIREMENT = "integration.changeToOtherVirement";
    /**����ʱ����跽���׻��������������
     * ��ʽΪ��true ����,false �跽, null Ĭ��
     * */
    public static final String NOTICE_TRANSNOTICE_ORDERBYDEBITFIRST = "notice.transnotice.orderbyDebitFirst";
    /**������Ϣ��¼ʱ���Ƿ���Ҫ���
	 *��ʽΪ��true ��Ҫ������ˣ�false ����Ҫ��ˣ�	
     */
    public static final String MODIFYTRANSACTION_ISNEEDCHECK = "modifytransaction.isneedcheck";
    /**
     * �ʽ��ϻ�ǰ���Ƿ����Ƿ����[�ѱ���]��[������]��[δ֪]ָ��(��ֹ͸֧)
     * */
    public static final String GATHER_ISCHECKPROCESSINGINSTRUCTION = "gather.ischeckprocessinginstruction";
    
	static {
		/**�����������Ĭ������*/
		DEFAULT_PROPERTIES = new Properties();
		/**����������*/
		DEFAULT_PROPERTIES.put(G_PROJECT_NAME, "bankportal");
		DEFAULT_PROPERTIES.put(G_STYLE_NAME, "bankportal");
		DEFAULT_PROPERTIES.put(G_BANKPORTAL_URL, "/iTreasury-bankportal");
		DEFAULT_PROPERTIES.put(G_LANGUAGE, "zh");
		DEFAULT_PROPERTIES.put(G_CLIENT_NAME, "��ͨ����");
		DEFAULT_PROPERTIES.put(G_CLIENT_ENGNAME, "isoftstone");
		DEFAULT_PROPERTIES.put(G_RUN_ENVIRONMENT, "windows2000");
		
		/**���ɷ�������**/
		DEFAULT_PROPERTIES.put(INTEGRATION_RMI_PORT, "2005");
		DEFAULT_PROPERTIES.put(INTEGRATION_SERVICE_TYPE, "rmi");	
		
		/**�Զ�֪ͨ��������**/
		DEFAULT_PROPERTIES.put(NOTICE_TRANSNOTICE, "false");
		DEFAULT_PROPERTIES.put(NOTICE_TRANSNOTICE_FLAG, "**");
		
		/** ���ݿ�type4������Դ��JNDI����**/
		DEFAULT_PROPERTIES.put(DB_TYPE4_NAME, "jdbc/jdbc-bp");
		DEFAULT_PROPERTIES.put(DB_SID,"dbk3");
		DEFAULT_PROPERTIES.put(DB_USERNAME,"xinaoadmin");
		DEFAULT_PROPERTIES.put(DB_PASSWORD, "");
		DEFAULT_PROPERTIES.put(DB_ISENCRYPTED,"false");
		
		/**�˻�����������*/
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYONE, "�˻�����һ");
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYTWO, "�˻����Զ�");
		DEFAULT_PROPERTIES.put(FIELDNAME_ACCTPROPERTYTHREE, "�˻�������");

		/**�ϴ�Ŀ¼ **/
		DEFAULT_PROPERTIES.put(UPLOAD_FILE_PATH, File.separator); //jreĬ�Ϲ���Ŀ¼
		
		/**log4j�����ļ����� **/
		DEFAULT_PROPERTIES.put(LOG4J_CONFIGFILE_NAME, "commons-logging.properties"); 
		
		DEFAULT_PROPERTIES.put(BANKSERVICE_CONFIGFILE_NAME, "bank_service_config.xml");
		/**��������**/
		DEFAULT_PROPERTIES.put(CURRENCY,"USD");
		/**�ϴ��ļ���С����**/
		DEFAULT_PROPERTIES.put(UPLOAD_FILE_SIZE,"2100000");
		/**�ϴ��ܵ��ļ���С����**/
		DEFAULT_PROPERTIES.put(UPL0AD_TOTAL_FILE_SIZE,"11000000");
		/**��ӡҳ����������**/
		DEFAULT_PROPERTIES.put(NPAGELINE,"20");
		DEFAULT_PROPERTIES.put(ISCLIENT,"false");
		DEFAULT_PROPERTIES.put(ISRATE,"false");
		DEFAULT_PROPERTIES.put(ISRELATIONNO,"false");
		DEFAULT_PROPERTIES.put(ISCURRENCY,"false");
		DEFAULT_PROPERTIES.put(ISCURRENCYMAPPING,"true");
		DEFAULT_PROPERTIES.put(DB_CONNECTION_NUM,"50");
		/**Ĭ�ϵķŴ������Ʊ�������**/
		DEFAULT_PROPERTIES.put(APPMAGNIFIER_CODE,"GBK");
		/**Ĭ�ϼ������ִ�е���Сʱ����**/
		DEFAULT_PROPERTIES.put(MONITORTASK_INTERVAL,"10");
		/**Ĭ������ӿ�ͨѶģ���ʼ������**/
		DEFAULT_PROPERTIES.put(BSCOMM_FLAG,"false");
		DEFAULT_PROPERTIES.put(ISNEEDCOUNTRY,"true");
		/**��Ȩ��ʾ**/
		DEFAULT_PROPERTIES.put(COPYRIGHT, "��ͨ����");
		
		// �Ƿ���Ҫ��¼�û���¼��־
		//DEFAULT_PROPERTIES.put(isNeedUserLog, "false");		
		
		/**�Ƿ���Ҫ��¼�û���¼��־**/
		DEFAULT_PROPERTIES.put(isNeedUserLog, "false");	
		/**�Ƿ���Ҫ֤����֤**/
		DEFAULT_PROPERTIES.put(isNeedCertificate,"true");
		/**�Ƿ���Ҫ�˻�����**/
		//DEFAULT_PROPERTIES.put(isNeedAccountType,"true");
		/**��Ӧ������**/
		//DEFAULT_PROPERTIES.put(oppNeedBankType,"ABC");
		/**�Ƿ��ڷ���ָ��ʱʹ��cfca��֤*/
		DEFAULT_PROPERTIES.put(isCertWhenSendInstruction,"false");
		/**��׼ʱ��Ĭ��ֵ8*60**/
		DEFAULT_PROPERTIES.put(G_UTC,"480");
		/**����֧��ָ����Ƿ��Զ�����*/
		DEFAULT_PROPERTIES.put(ISAUTOSEND,"false");
		/**�Զ��ϻ���ժҪ��Ϣ*/
		DEFAULT_PROPERTIES.put(GATHER_ABSTRACTINFO,"�ʽ��ϻ�");
		DEFAULT_PROPERTIES.put(GATHER_INPUTOROUTPUT,"-1");
		DEFAULT_PROPERTIES.put(GATHER_AUTOGATHER_ISAUTOSEND,"1");
		DEFAULT_PROPERTIES.put(GATHER_AUTOGATHER_SOURCEOFGATHERACCOUNT,"1");
		DEFAULT_PROPERTIES.put(GATHER_INSTRUCTION_ISPRIOR,"false");
        DEFAULT_PROPERTIES.put(ASSIGN_INSTRUCTION_ISPRIOR,"false");        

		/**�µ��Զ������ִ��Ƶ��*/
		DEFAULT_PROPERTIES.put(AUTOTASK_EXECFREQUENCY,"60");
		/**���´�ID��Ĭ��Ϊ-1���������ǰ��´�**/
		DEFAULT_PROPERTIES.put(HQOFFICEID,"-1");
		/**�����Ƿ���Ҫ���ǰ��´�**/
		DEFAULT_PROPERTIES.put(HASBANKOFFICE,"false");
	    /**�ͻ��Ƿ���Ҫ���ǰ��´�**/
		DEFAULT_PROPERTIES.put(HASCLIENTOFFICE,"false");
		/**�˻�����һ,��,���Ƿ���Ҫ���ǰ��´�**/
		DEFAULT_PROPERTIES.put(HASACCOUNTPROPERTYOFFICE,"false");
        /**�Զ����뼰�Զ��˶������Ƿ����ְ��´�**/
        DEFAULT_PROPERTIES.put(HASAUTOTASKOFFICE, "false");
		/**����ָ���ʱ���Ƿ���Ҫ����ͬ�����У�Ŀǰ�������л�������ͬ������**/	    
	    DEFAULT_PROPERTIES.put(ISNEEDSAMEBANK,"true");
	    /**ָ���ʱ�Ƿ������޸�ָ����Ϣ�������Ƿ�Ӽ��ȣ�Ĭ���ǿ����޸ĵ�**/
	    DEFAULT_PROPERTIES.put(ISMODIFYINSTRINFO,"true");
	    DEFAULT_PROPERTIES.put(ISNEEDREFERENCECODE,"false");	     
	    DEFAULT_PROPERTIES.put(INTERFACE_EXCHANGERATEIMPL,"com.iss.itreasury.fcinterface.bankportal.setting.exchangerate.ExchangeRateImpl");
	    DEFAULT_PROPERTIES.put(INTERFACE_USERIMPL,"com.iss.itreasury.fcinterface.bankportal.usermgt.userimpl.SysModuleUserImpl");
	    DEFAULT_PROPERTIES.put(RECNETSTATION,"");
	    /**Ĭ��Ϊ����**/
	    DEFAULT_PROPERTIES.put(isForeignInstr,"false");	 
	    /**Ĭ����0, 1, 2,�ֱ�Ϊ�ʽ���ϵͳ�����������**/
	    DEFAULT_PROPERTIES.put(outerSystemID,"0,1,2");
	    DEFAULT_PROPERTIES.put(AUTOTASK_RESUMETIME, "60");
	    /**Ĭ��Ϊ��ʹ��sequence**/
	    DEFAULT_PROPERTIES.put(transID_isSequence,"false");	
	    /**�ʽ��ػ�����Ϣ�Ƿ�ʹ�û��棬Ĭ��Ϊʹ��**/
	    DEFAULT_PROPERTIES.put(isUseCache,"true");	  
	    DEFAULT_PROPERTIES.put(ISNEEDACCESSLOG, "false");
	    DEFAULT_PROPERTIES.put(ISACCESSLOGSHARE, "false");
	    /**��������Ĭ��ֵ*/
	    DEFAULT_PROPERTIES.put(REMIND_EMAIL_REMINDNUM, "3");
	    /**�к�ר�ã�Ĭ�ϵ�֪ͨ�������ڲ���*/
	    DEFAULT_PROPERTIES.put(DEFAULTSUBJECTCODE, "");
	    /**�ֹ��˶�ʱ���Ƿ�ֻ�����˻��˶�,Ĭ��Ϊtrue*/
	    DEFAULT_PROPERTIES.put(isCheckOnlyOneAcct, "true");
	    /**�ֹ��˶�ʱ���Ƿ����������ƣ�Ĭ��Ϊtrue*/
	    DEFAULT_PROPERTIES.put(isCheckDateLimit, "true");
	    /**�Ƿ���ҪУ������Ȩ�ޣ�Ĭ��Ϊfalse*/
	    DEFAULT_PROPERTIES.put(isNeedDataPrivilege, "false");	   
	    /**�û���¼ʱ�Ƿ���Ҫ�Զ�����Ȩ����Ȩ*/
	    DEFAULT_PROPERTIES.put(isNeedAutoAuthorize, "false");
	    DEFAULT_PROPERTIES.put(balanceListDisplayRows, "");
        DEFAULT_PROPERTIES.put(isNeedCheckWhenClosingAccount, "false");
        /**�����˻����Ƿ���˭¼��˭�޸ģ�Ĭ��Ϊtrue,��ԭ�ȵĳ��򱣴�һ��*/
        DEFAULT_PROPERTIES.put(AccountSetting_WhoInputWhoModify, "true");
        /**�Ƿ��������Ϣ,���;�����*/
        DEFAULT_PROPERTIES.put(ISBASEINFOSHARE, "false");
        /**�ʽ��²��Ƿ���Ҫ����ڲ������*/
        DEFAULT_PROPERTIES.put(ASSIGNFUND_ISCHECK_SETTACCTBALCNE, "false");
        DEFAULT_PROPERTIES.put(AUTOTASK_ASSIGN_ABSTRACTINFO, "�ʽ��²�");
        /**������Ŀ��־��¼*/
        DEFAULT_PROPERTIES.put(ISCPFLOG,"false");
        /**������Ϣ��¼ʱ�Ƿ���Ҫ���*/
        DEFAULT_PROPERTIES.put(MODIFYTRANSACTION_ISNEEDCHECK, "false");
        /**�Զ��ϻ�ʱ���Ƿ�����ڴ����е�ָ��*/
        DEFAULT_PROPERTIES.put(GATHER_ISCHECKPROCESSINGINSTRUCTION, "false");
	}

	/**
	 * ˽�еĹ��췽�������Ա�֤����޷�ֱ��ʵ����
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
//					//ת���ַ���
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
	 * ��ȡ��̬ʵ��
	 * @return
	 */
	public static Env getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �����������������ʾ��Ϣ
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
				//ת���ַ���
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
	 * ��ȡָ�����Ƶ�������Ϣ
	 * @param name
	 * @return
	 */
	public static String getEnvConfigItem(String name)
	{
		String val = null;

		// ��������ļ��Ƿ��޸Ĺ�,�������
//		synchronized (INSTANCE.propfile)
//		{
//			long newTime = INSTANCE.propfile.lastModified();
			// ����ǣ���ȡ�����ļ�
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
////							//ת���ַ���
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
		//�������ļ������ݽ���ת��
		//֮���Բ���load���ļ���ʱ��ͳһ��ת�룬
		//����Ϊ����Properties��set֮��֮ǰ����ת�빤����Ч��
		//������Hashtable��put�������ٶ�ֵ������ת��
		//���ǲ��Ժ�ó��Ľ���
        if(val != null)
        {
        	try
			{
				val = new String(val.getBytes("ISO8859_1"),"GBK");
			} catch (UnsupportedEncodingException e)
			{				
			}
        }
		//���δ���ã����ȡĬ��ֵ
		if (val == null)
		{
			val = DEFAULT_PROPERTIES.getProperty(name);
		}		
        
		return val;
	}

	/**
	 * �ṩϵͳ�Ļ�׼ʱ�䣬��ǰ����ʵ�������ݿ����������ʱ��Ϊ׼��
	 */
	public static Date getSystemDateTime() throws SystemException
	{
//		try
//		{
			/**
			 * ��ΪЧ�����⣬��ǰʱ��ֱ��ȡӦ�÷�����ʱ��--by jsxie
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
//				"��ȡ���ݿ�ϵͳʱ��ʱ�����쳣������ԭ��" + e.getMessage(),
//				e);
//		}
	}
	
	/**
	 * ���ݵ�ǰʱ���ȡ�����׼ʱ��
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
			throw new SystemException("utcʱ�����÷Ƿ�", e);
		}
		Date systemTime = getSystemDateTime();
		Date utcDateTime = new Date(systemTime.getTime() - utcMin*60*1000);
		return utcDateTime;
	}

	/**
	 * ����ϵͳ����Ŀ�ʱ�� ����ITreasuryDAO�޷�֧��ͨ����DataEntity��Setter������ͨ��set
	 * null�����ݿ��е�Date���͸���Ϊ��ֵ�����ͨ�� ����һ��ϵͳ�ڵĿ�ʱ��(1970-01-01
	 * 08:00:00.0)��Ϊ��ʱ��ı�־ʱ�䣬��Ҫ�����ݿ���Date�������ݸ���Ϊnull��
	 * ��������ִ��setXXX����ʱ�����ô˲�����ȡϵͳ����Ŀ�ʱ���־��DAO����setTimeStampʱ�ж�ʱ���Ƿ�Ϊ
	 * ��ʱ�䣬����ǣ�����¸��ֶ�Ϊnull
	 */
	public static Date getNullTimeStamp()
	{
		return new Date(0);
	}
    
	/**
     * �ж�ĳһ���´��Ƿ����ܲ�
     * �ǣ�����true�����򣬷���false
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
	
	//�����Զ�������Իָ�ʱ��
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
     * ����configItem��Ӧ��ֵ��true����false��
     * configItem��ֵֻ��Ϊtrue��false
     * 
     * @param configItem
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isTrue(String configItem) {
        
        String strTemp = getEnvConfigItem(configItem);
        
        if(strTemp==null){
            throw new IllegalArgumentException("������["+configItem +"]ֵΪ��");
        }
        strTemp = strTemp.trim();
        if("true".compareToIgnoreCase(strTemp)==0)
        {
            return true;
        }else if("false".compareToIgnoreCase(strTemp)==0)
        {
            return false;
        }else{
            throw new IllegalArgumentException("������["+ configItem +"]ֵ��Ч");
        }
    }
    
    
    /**
     * �ж�ָ�����ַ����Ƿ�������ַ���������
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
     * �����˻�����ѯʱ����ȡ����ʾ�������
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
        //���˵���ֺ��ַ��������е�null��""�ַ���
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
//     * ��ǰ����ѯ����ʷ����ѯ��ֱ���˻�����ѯ���ж����Ƿ���ҳ���Ͽɼ���
//     * @param fieldName 
//     * @return
//     */
//    public static boolean isVisableOnBlanceQueryList(String fieldName)
//    {
//        boolean result = false;
//        return result;
//    }
    
   
	
	/**
     * ��ʾ���´��б�
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
					out.println("<option value='0' selected>ȫ��</option>");
				} else {
					out.println("<option value='0'>ȫ��</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}		    
    }	**/
	
}
