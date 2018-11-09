package com.iss.sysframe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


/**
 * 
 * @author leiyang3
 *
 */
public class Env {

    private static Env _instance =  new Env();
    
    private Properties properties = null; //属性文件所对应的属性对象变量    
    private static Properties DEFAULT_PROPERTIES = null; //记录配置项的默认配置值
    public static final String PROP_FILE_NAME = "commercialdraft.properties";//属性文件名
    private File propfile = null; //对应于属性文件的文件对象变量
    private long propfile_LastModifiedTime = 0; //属性文件的最后修改日期
    private static ClassLoader staticClassLoader = null;

    //版权标示
    public static final String COPYRIGHT = "copyRight";
    
    //数据库设置
    public static final String DB_CONN_TYPE = "db.conn.type";
    public static final String DB_TYPE = "db.type";
    public static final String DB_TYPE4_NAME = "db.type4.name";
    public static final String DB_IP = "db.ip";
    public static final String DB_PORT = "db.port";
    public static final String DB_SID = "db.sid";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";    
    
    //模块根路径设置
	public static final String DRAFT_SYSTEMTYPE = "draft.systemtype";
	public static final String DRAFT_CONTEXT_URL = "draft.context.url";
	public static final String DRAFT_WEBSERVICE_DRAFTMANAGESERVICE_URL = "draft.webservice.draftmanageservice.url";
	public static final String DRAFT_LOGIN_URL = "draft.login.url";
	public static final String DRAFT_FLOWDESIGNER_URL = "draft.flowdesigner.url";
	
	//上传/下载保存路径设置
    public static final String DRAFT_UPLOAD_URL = "draft.upload";
    public static final String DRAFT_UPLOAD_SIZE = "draft.upload.size";
    public static final String DRAFT_DRAFTCONTRACT_SIZE = "draft.upload.draftcontract.size";
    public static final String DRAFT_DOWNLOAD_URL = "draft.download";
    public static final String DRAFT_TEMPLATE_URL = "draft.template";
    
    //票据系统对应的开户行行号配置信息
    public static final String DRAFT_SYSTEM_PRATICIPANT_BANKNO = "draft.system.praticipant.bankno";  //系统参与者开户行行号
    public static final String DRAFT_SYSTEM_FINANCE_ORGANIZATIONCODE = "draft.system.finance.organizationcode";  //财务公司组织机构代码
    
    //票据系统证书相关配置设置
    public static final String SYSTME_CERTIFICATE_CRLPATH = "systme.certificate_crlpath"; //证书CRL路径
    public static final String SYSTME_CERTIFICATE_LDAPIP = "systme.certificate_ldapip"; //证书CRL_LDAP服务器IP
    public static final String SYSTME_CERTIFICATE_LDAPPORT = "systme.certificate_ldapport"; //证书CRL_LDAP服务器PORT
    public static final String SYSTME_CERTIFICATE_PUBLICKEYFILE = "systme.certificate_publickeyfile"; //公匙证书文件
    public static final String SYSTME_CERTIFICATE_FILE = "systme.certificate_file"; //证书文件
    public static final String SYSTME_CERTIFICATE_FILEPWD = "systme.certificate_filepwd"; //证书文件密码
    
    //票据系统业务证书相关配置设置
    public static final String SYSTEM_BUSINESS_CERTIFICATE_TYPE = "system.business.certificate.type"; //配置业务证书类型
    public static final String SYSTEM_BUSINESS_CERTIFICATE_ITRUS_CVMCONFIG = "system.business.certificate.itrus.cvmconfig"; //配置天威诚信CVMConfig.xml文件位置
    public static final String SYSTEM_BUSINESS_CERTIFICATE_NOINPUTUSER = "system.business.certificate.noinputuser"; //配置启用证书后是否可以只选证书就可以做登录
    public static final String SYSTEM_BUSINESS_CERTIFICATE_CHECKSN = "system.business.certificate.checksn"; //配置启用证书后是否登录时是否效验证书SN
    
    //字体文件路径   
    public static final String SYSTME_FONTS_PATH = "system.fonts_path";
    
    //系统基础设置
    public static final String SYS_SEQUENCEID_GENERATETYPE = "ecds.sequenceid.generatetype";
    public static final String SYS_MESSAGE_CHARACTER = "ecds.message.character";

    //消息中间件连接程序类型设置
    public static final String SYSTME_MQPROGRAM_TYPE = "systme.mqprogram.type";  //MQ程序类型
    //JMS common
    public static final String JMS_MBFE_INREALTIMEQUEUENAME = "jms.mbfe.inrealtimequeuename";
    public static final String JMS_MBFE_INQUEUENAME = "jms.mbfe.inqueuename";
    public static final String JMS_MBFE_OUTREALTIMEQUEUENAME = "jms.mbfe.outrealtimequeuename";
    public static final String JMS_MBFE_OUTQUEUENAME = "jms.mbfe.outqueuename";
    //jms MQ
    public static final String JMS_MQ_QUEUEMANAGERNAME = "jms.mq.queuemanagername";
    public static final String JMS_MQ_HOSTNAME = "jms.mq.hostname";
    public static final String JMS_MQ_PORT = "jms.mq.port";
    public static final String JMS_MQ_CCSID = "jms.mq.ccsid";
    public static final String JMS_MQ_CHANNEL = "jms.mq.channel";
    public static final String JMS_MQ_USERNAME = "jms.mq.username";
    public static final String JMS_MQ_PASSWORD = "jms.mq.password";
    //jms TLQ
    public static final String JMS_TLQ_HOSTNAME = "jms.tlq.hostname";
    public static final String JMS_TLQ_HOSTPORT = "jms.tlq.hostport";
    
    //审批流基础设置
    public static final String APPROVAL_IMPLEMENT = "approval.implement";
    
    //自动任务时间设置
    public static final String AUTOTASK_QUEUEINCEPT_REALTIME = "autotask.queueincept.realtime"; //自动任务实时接收队列
    public static final String AUTOTASK_QUEUEINCEPT_COMMON = "autotask.queueincept.common"; //自动任务普通接收队列
    public static final String AUTOTASK_MESSAGEDISPOSE = "autotask.messagedispose"; //自动任务：接收报文处理器时间间隔
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_SKINS = "autotask.draftwork.runtime.skins"; //自动任务间隔时间—待办任务—待应答业务
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_APPROVAL = "autotask.draftwork.runtime.approval"; //自动任务间隔时间—待办任务—待审批业务
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_DRAFTSTATUSVARY = "autotask.draftwork.runtime.draftstatusvary"; //自动任务间隔时间—业务提醒及预警—票据状态变更业务
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_INFORESULTNOTICE = "autotask.draftwork.runtime.inforesultnotice"; //自动任务间隔时间—业务提醒及预警—信息查复结果通知
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_OPERATIONREMIND = "autotask.draftwork.runtime.operationremind"; //自动任务间隔时间—业务提醒及预警—业务提醒
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_COMPLETEWORK = "autotask.draftwork.runtime.completework"; //自动任务间隔时间—已办任务

    //提醒天数设置
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_DRAFTSTATUSVARY_DATENUM = "autotask.draftwork.runtime.draftstatusvary.datenum"; //票据状态变更业务-提醒天数
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_INFORESULTNOTICE_DATENUM = "autotask.draftwork.runtime.inforesultnotice.datenum"; //信息查复结果通知-提醒天数
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_COMPLETEWORK_DATENUM = "autotask.draftwork.runtime.completework.datenum"; //已办任务-提醒天数
    
    //线上清算设置
    public static final String SYSTEM_SETTING_SETTLEMENTONLINE = "system.setting.SettlementOnLine";
    
    //是否显示验证码
    public static final String DRAFT_SYSTEM_VERIFICATIONCODE = "draft.system.verificationcode";

    //是否启用批量业务 
    public static final String DRAFT_OPERATION_BATCHBUSINESS = "draft.operation.batchBusiness";
    
    //外部账户资料是否默认引用 
    public static final String DRAFT_OUTBANKACCOUNT_RELATION = "draft.outbankaccount.relation";
    
    //贴现业务是否引入付息方式与在途利息 
    public static final String DRAFT_OPERATION_DISCOUNT_EXTRA = "draft.operation.discount.extra";
    
    //系统客户类型
    public static final String DRAFT_SYSTEMCLIENTTYPE = "draft.systemclienttype";
    
    //上海电气：贴现应答查询贷款业务审批进度接口url
    public static final String DRAFT_SYSTEMCLIENTTYPE_SHDQ_DISCOUNT_URL = "draft.systemclienttype.shdq.discount.url";
    
    //西电： 撤票/作废授信额度恢复接口url
    public static final String DRAFT_SYSTEMCLIENTTYPE_XD_REGISTRATION_URL = "draft.systemclienttype.xd.registration.url";
    
    //东方电气：电票系统的Session与核心系统保持一致url
    public static final String DRAFT_SYSTEMCLIENTTYPE_DFDQ_SESSIONSYCN_URL = "draft.systemclienttype.dfdq.sessionsycn.url";
    
    //东方电气：电票系统的Session与核心系统保持一致url（页面JS用）
    public static final String DRAFT_SYSTEMCLIENTTYPE_DFDQ_SESSIONSYCN_CLIENT_URL = "draft.systemclienttype.dfdq.sessionsycn.client.url";
    
    //贴现业务 是否刚性要求 法定节假日设置
    public static final String DRAFT_DISCOUNT_LEGALHOLIDAYSETTING = "draft.discount.legalholidaysetting";

    //哈电： 出票控制授信额度检查接口url
    public static final String DRAFT_SYSTEMCLIENTTYPE_HD_CREDITCONTROL_URL = "draft.systemclienttype.hd.creditcontrol.url";
    
    static {
    	
        //设置配置项的默认配置
        DEFAULT_PROPERTIES = new Properties();
        
        DEFAULT_PROPERTIES.put(COPYRIGHT, "软通动力信息技术（集团）有限公司");
        DEFAULT_PROPERTIES.put(SYS_SEQUENCEID_GENERATETYPE, "db");
        DEFAULT_PROPERTIES.put(SYS_MESSAGE_CHARACTER, "UTF-8");
    }

    private Env(){
    	
        properties = new Properties();     
        try
        {
            File configFile = new File(PROP_FILE_NAME);
            if(!configFile.exists()){
                throw new Exception(PROP_FILE_NAME + "文件不存在");
            }    		
            propfile = configFile;
            FileInputStream is = new FileInputStream(configFile);           
            properties.load(is);  
            propfile_LastModifiedTime = propfile.lastModified();

            is.close();         
        }
        catch (Exception fnfe)
        {
        	ClassLoader classLoader = Env.class.getClassLoader();
    		InputStream ins = classLoader.getResourceAsStream(PROP_FILE_NAME);    
    		URL url = classLoader.getResource(PROP_FILE_NAME);
    		
            try
			{
            	File configFile = null;
            	
            	configFile = new File(url.getFile());
            	if(configFile == null || !configFile.exists()){
            		 configFile = new File(new URI(url.toString()));
            	}
                if(!configFile.exists()){
                    throw new Exception(PROP_FILE_NAME + "文件不存在");
                }    		
                propfile = configFile;
				properties.load(ins);
	            propfile_LastModifiedTime = propfile.lastModified();
				ins.close();         
			} catch (IOException e1)
			{
				e1.printStackTrace();
			} catch (URISyntaxException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
        }
    }
    
    public static String getConfigItem(String name){       
        String val = null;

        reloadProperties();

        val = _instance.properties.getProperty(name);

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

    private static void reloadProperties() {
    	
        //检查属性文件是否被修改过,必须加锁
        synchronized (_instance)
        {
            try
            {
                if(_instance.propfile==null || !_instance.propfile.exists()){
                    throw new Exception(PROP_FILE_NAME + "文件不存在");
                }
                long newTime = _instance.propfile.lastModified();
                if (newTime > _instance.propfile_LastModifiedTime)
                {
                    _instance.properties.clear();
    
                    try{
                    	FileInputStream is = new FileInputStream(PROP_FILE_NAME);
						_instance.properties.load(is);
						is.close();
                    }
                    catch(Exception ex)
                    {
                    	ClassLoader classLoader = Env.class.getClassLoader();
                		URL url = classLoader.getResource(PROP_FILE_NAME);
                    	File file = new File(new URI(url.toString()));
                    	FileInputStream is = new FileInputStream(file);
						_instance.properties.load(is);
						is.close();
                    }
                }
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
        }
    }

    /**
     * 返回系统定义的空时间 由于ITreasuryDAO无法支持通过在DataEntity中Setter函数中通过set
     * null将数据库中的Date类型更新为空值，因此通过 定义一个系统内的空时间(1970-01-01
     * 08:00:00.0)作为空时间的标志时间，需要将数据库中Date类型数据更新为null的
     * 操作，在执行setXXX操作时，调用此操作获取系统定义的空时间标志，DAO将在setTimeStamp时判断时间是否为
     * 此时间，如果是，则更新该字段为null
     */    
    public static Timestamp getNullTimeStamp() {
        return new Timestamp(0);
    }
    
    /**
     * 返回系统定义的空时间 由于ITreasuryDAO无法支持通过在DataEntity中Setter函数中通过set
     * null将数据库中的Date类型更新为空值，因此通过 定义一个系统内的空时间(1970-01-01
     * 08:00:00.0)作为空时间的标志时间，需要将数据库中Date类型数据更新为null的
     * 操作，在执行setXXX操作时，调用此操作获取系统定义的空时间标志，DAO将在setTimeStamp时判断时间是否为
     * 此时间，如果是，则更新该字段为null
     */    
    public static Date getNullDate() {
        return new Date(0);
    }
    
	
	/**
	 * 获得下载文件路径
	 * @return
	 */
	public static String getDownLoadFilePath()
	{
		String sFilePath = Env.getConfigItem(Env.DRAFT_DOWNLOAD_URL);
		try
		{
			//根据年、月、日生成子路径
			java.util.Calendar calendar = Calendar.getInstance ( ) ;
			String strYear = String.valueOf(calendar.get(Calendar.YEAR));
			String strMonth = String.valueOf ( calendar.get ( Calendar.MONTH ) + 1 ) ;
			String strDay = String.valueOf(calendar.get(Calendar.DATE));
	
			sFilePath += strYear + "/" + strMonth + "/" + strDay + "/";
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return sFilePath;
	}
	
	/**
	 * 获取指定名称的配置信息
	 * @param name
	 * @return
	 */
	public static String getEnvConfigItem(String name)
	{
		String val = _instance.properties.getProperty(name);
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
}
