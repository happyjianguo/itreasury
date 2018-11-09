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
    
    private Properties properties = null; //�����ļ�����Ӧ�����Զ������    
    private static Properties DEFAULT_PROPERTIES = null; //��¼�������Ĭ������ֵ
    public static final String PROP_FILE_NAME = "commercialdraft.properties";//�����ļ���
    private File propfile = null; //��Ӧ�������ļ����ļ��������
    private long propfile_LastModifiedTime = 0; //�����ļ�������޸�����
    private static ClassLoader staticClassLoader = null;

    //��Ȩ��ʾ
    public static final String COPYRIGHT = "copyRight";
    
    //���ݿ�����
    public static final String DB_CONN_TYPE = "db.conn.type";
    public static final String DB_TYPE = "db.type";
    public static final String DB_TYPE4_NAME = "db.type4.name";
    public static final String DB_IP = "db.ip";
    public static final String DB_PORT = "db.port";
    public static final String DB_SID = "db.sid";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";    
    
    //ģ���·������
	public static final String DRAFT_SYSTEMTYPE = "draft.systemtype";
	public static final String DRAFT_CONTEXT_URL = "draft.context.url";
	public static final String DRAFT_WEBSERVICE_DRAFTMANAGESERVICE_URL = "draft.webservice.draftmanageservice.url";
	public static final String DRAFT_LOGIN_URL = "draft.login.url";
	public static final String DRAFT_FLOWDESIGNER_URL = "draft.flowdesigner.url";
	
	//�ϴ�/���ر���·������
    public static final String DRAFT_UPLOAD_URL = "draft.upload";
    public static final String DRAFT_UPLOAD_SIZE = "draft.upload.size";
    public static final String DRAFT_DRAFTCONTRACT_SIZE = "draft.upload.draftcontract.size";
    public static final String DRAFT_DOWNLOAD_URL = "draft.download";
    public static final String DRAFT_TEMPLATE_URL = "draft.template";
    
    //Ʊ��ϵͳ��Ӧ�Ŀ������к�������Ϣ
    public static final String DRAFT_SYSTEM_PRATICIPANT_BANKNO = "draft.system.praticipant.bankno";  //ϵͳ�����߿������к�
    public static final String DRAFT_SYSTEM_FINANCE_ORGANIZATIONCODE = "draft.system.finance.organizationcode";  //����˾��֯��������
    
    //Ʊ��ϵͳ֤�������������
    public static final String SYSTME_CERTIFICATE_CRLPATH = "systme.certificate_crlpath"; //֤��CRL·��
    public static final String SYSTME_CERTIFICATE_LDAPIP = "systme.certificate_ldapip"; //֤��CRL_LDAP������IP
    public static final String SYSTME_CERTIFICATE_LDAPPORT = "systme.certificate_ldapport"; //֤��CRL_LDAP������PORT
    public static final String SYSTME_CERTIFICATE_PUBLICKEYFILE = "systme.certificate_publickeyfile"; //����֤���ļ�
    public static final String SYSTME_CERTIFICATE_FILE = "systme.certificate_file"; //֤���ļ�
    public static final String SYSTME_CERTIFICATE_FILEPWD = "systme.certificate_filepwd"; //֤���ļ�����
    
    //Ʊ��ϵͳҵ��֤�������������
    public static final String SYSTEM_BUSINESS_CERTIFICATE_TYPE = "system.business.certificate.type"; //����ҵ��֤������
    public static final String SYSTEM_BUSINESS_CERTIFICATE_ITRUS_CVMCONFIG = "system.business.certificate.itrus.cvmconfig"; //������������CVMConfig.xml�ļ�λ��
    public static final String SYSTEM_BUSINESS_CERTIFICATE_NOINPUTUSER = "system.business.certificate.noinputuser"; //��������֤����Ƿ����ֻѡ֤��Ϳ�������¼
    public static final String SYSTEM_BUSINESS_CERTIFICATE_CHECKSN = "system.business.certificate.checksn"; //��������֤����Ƿ��¼ʱ�Ƿ�Ч��֤��SN
    
    //�����ļ�·��   
    public static final String SYSTME_FONTS_PATH = "system.fonts_path";
    
    //ϵͳ��������
    public static final String SYS_SEQUENCEID_GENERATETYPE = "ecds.sequenceid.generatetype";
    public static final String SYS_MESSAGE_CHARACTER = "ecds.message.character";

    //��Ϣ�м�����ӳ�����������
    public static final String SYSTME_MQPROGRAM_TYPE = "systme.mqprogram.type";  //MQ��������
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
    
    //��������������
    public static final String APPROVAL_IMPLEMENT = "approval.implement";
    
    //�Զ�����ʱ������
    public static final String AUTOTASK_QUEUEINCEPT_REALTIME = "autotask.queueincept.realtime"; //�Զ�����ʵʱ���ն���
    public static final String AUTOTASK_QUEUEINCEPT_COMMON = "autotask.queueincept.common"; //�Զ�������ͨ���ն���
    public static final String AUTOTASK_MESSAGEDISPOSE = "autotask.messagedispose"; //�Զ����񣺽��ձ��Ĵ�����ʱ����
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_SKINS = "autotask.draftwork.runtime.skins"; //�Զ�������ʱ�䡪�������񡪴�Ӧ��ҵ��
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_APPROVAL = "autotask.draftwork.runtime.approval"; //�Զ�������ʱ�䡪�������񡪴�����ҵ��
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_DRAFTSTATUSVARY = "autotask.draftwork.runtime.draftstatusvary"; //�Զ�������ʱ�䡪ҵ�����Ѽ�Ԥ����Ʊ��״̬���ҵ��
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_INFORESULTNOTICE = "autotask.draftwork.runtime.inforesultnotice"; //�Զ�������ʱ�䡪ҵ�����Ѽ�Ԥ������Ϣ�鸴���֪ͨ
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_OPERATIONREMIND = "autotask.draftwork.runtime.operationremind"; //�Զ�������ʱ�䡪ҵ�����Ѽ�Ԥ����ҵ������
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_COMPLETEWORK = "autotask.draftwork.runtime.completework"; //�Զ�������ʱ�䡪�Ѱ�����

    //������������
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_DRAFTSTATUSVARY_DATENUM = "autotask.draftwork.runtime.draftstatusvary.datenum"; //Ʊ��״̬���ҵ��-��������
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_INFORESULTNOTICE_DATENUM = "autotask.draftwork.runtime.inforesultnotice.datenum"; //��Ϣ�鸴���֪ͨ-��������
    public static final String AUTOTASK_DRAFTWORK_RUNTIME_COMPLETEWORK_DATENUM = "autotask.draftwork.runtime.completework.datenum"; //�Ѱ�����-��������
    
    //������������
    public static final String SYSTEM_SETTING_SETTLEMENTONLINE = "system.setting.SettlementOnLine";
    
    //�Ƿ���ʾ��֤��
    public static final String DRAFT_SYSTEM_VERIFICATIONCODE = "draft.system.verificationcode";

    //�Ƿ���������ҵ�� 
    public static final String DRAFT_OPERATION_BATCHBUSINESS = "draft.operation.batchBusiness";
    
    //�ⲿ�˻������Ƿ�Ĭ������ 
    public static final String DRAFT_OUTBANKACCOUNT_RELATION = "draft.outbankaccount.relation";
    
    //����ҵ���Ƿ����븶Ϣ��ʽ����;��Ϣ 
    public static final String DRAFT_OPERATION_DISCOUNT_EXTRA = "draft.operation.discount.extra";
    
    //ϵͳ�ͻ�����
    public static final String DRAFT_SYSTEMCLIENTTYPE = "draft.systemclienttype";
    
    //�Ϻ�����������Ӧ���ѯ����ҵ���������Ƚӿ�url
    public static final String DRAFT_SYSTEMCLIENTTYPE_SHDQ_DISCOUNT_URL = "draft.systemclienttype.shdq.discount.url";
    
    //���磺 ��Ʊ/�������Ŷ�Ȼָ��ӿ�url
    public static final String DRAFT_SYSTEMCLIENTTYPE_XD_REGISTRATION_URL = "draft.systemclienttype.xd.registration.url";
    
    //������������Ʊϵͳ��Session�����ϵͳ����һ��url
    public static final String DRAFT_SYSTEMCLIENTTYPE_DFDQ_SESSIONSYCN_URL = "draft.systemclienttype.dfdq.sessionsycn.url";
    
    //������������Ʊϵͳ��Session�����ϵͳ����һ��url��ҳ��JS�ã�
    public static final String DRAFT_SYSTEMCLIENTTYPE_DFDQ_SESSIONSYCN_CLIENT_URL = "draft.systemclienttype.dfdq.sessionsycn.client.url";
    
    //����ҵ�� �Ƿ����Ҫ�� �����ڼ�������
    public static final String DRAFT_DISCOUNT_LEGALHOLIDAYSETTING = "draft.discount.legalholidaysetting";

    //���磺 ��Ʊ�������Ŷ�ȼ��ӿ�url
    public static final String DRAFT_SYSTEMCLIENTTYPE_HD_CREDITCONTROL_URL = "draft.systemclienttype.hd.creditcontrol.url";
    
    static {
    	
        //�����������Ĭ������
        DEFAULT_PROPERTIES = new Properties();
        
        DEFAULT_PROPERTIES.put(COPYRIGHT, "��ͨ������Ϣ���������ţ����޹�˾");
        DEFAULT_PROPERTIES.put(SYS_SEQUENCEID_GENERATETYPE, "db");
        DEFAULT_PROPERTIES.put(SYS_MESSAGE_CHARACTER, "UTF-8");
    }

    private Env(){
    	
        properties = new Properties();     
        try
        {
            File configFile = new File(PROP_FILE_NAME);
            if(!configFile.exists()){
                throw new Exception(PROP_FILE_NAME + "�ļ�������");
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
                    throw new Exception(PROP_FILE_NAME + "�ļ�������");
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
        //���δ���ã����ȡĬ��ֵ
        if (val == null)
        {
            val = DEFAULT_PROPERTIES.getProperty(name);
        }       
        
        return val;
    }

    private static void reloadProperties() {
    	
        //��������ļ��Ƿ��޸Ĺ�,�������
        synchronized (_instance)
        {
            try
            {
                if(_instance.propfile==null || !_instance.propfile.exists()){
                    throw new Exception(PROP_FILE_NAME + "�ļ�������");
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
     * ����ϵͳ����Ŀ�ʱ�� ����ITreasuryDAO�޷�֧��ͨ����DataEntity��Setter������ͨ��set
     * null�����ݿ��е�Date���͸���Ϊ��ֵ�����ͨ�� ����һ��ϵͳ�ڵĿ�ʱ��(1970-01-01
     * 08:00:00.0)��Ϊ��ʱ��ı�־ʱ�䣬��Ҫ�����ݿ���Date�������ݸ���Ϊnull��
     * ��������ִ��setXXX����ʱ�����ô˲�����ȡϵͳ����Ŀ�ʱ���־��DAO����setTimeStampʱ�ж�ʱ���Ƿ�Ϊ
     * ��ʱ�䣬����ǣ�����¸��ֶ�Ϊnull
     */    
    public static Timestamp getNullTimeStamp() {
        return new Timestamp(0);
    }
    
    /**
     * ����ϵͳ����Ŀ�ʱ�� ����ITreasuryDAO�޷�֧��ͨ����DataEntity��Setter������ͨ��set
     * null�����ݿ��е�Date���͸���Ϊ��ֵ�����ͨ�� ����һ��ϵͳ�ڵĿ�ʱ��(1970-01-01
     * 08:00:00.0)��Ϊ��ʱ��ı�־ʱ�䣬��Ҫ�����ݿ���Date�������ݸ���Ϊnull��
     * ��������ִ��setXXX����ʱ�����ô˲�����ȡϵͳ����Ŀ�ʱ���־��DAO����setTimeStampʱ�ж�ʱ���Ƿ�Ϊ
     * ��ʱ�䣬����ǣ�����¸��ֶ�Ϊnull
     */    
    public static Date getNullDate() {
        return new Date(0);
    }
    
	
	/**
	 * ��������ļ�·��
	 * @return
	 */
	public static String getDownLoadFilePath()
	{
		String sFilePath = Env.getConfigItem(Env.DRAFT_DOWNLOAD_URL);
		try
		{
			//�����ꡢ�¡���������·��
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
	 * ��ȡָ�����Ƶ�������Ϣ
	 * @param name
	 * @return
	 */
	public static String getEnvConfigItem(String name)
	{
		String val = _instance.properties.getProperty(name);
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
}
