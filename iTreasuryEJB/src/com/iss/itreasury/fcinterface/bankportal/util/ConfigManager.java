package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.IllegalParameterException;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * 配置管理对象<br>
 * 单例类，负责维护配置信息，配置信息以一个别名配置为最小管理单元
 * 注：只有成功执行了loadConfig(String name)后才可以使用其他获取配置信息的方法
 * @author mxzhou
 */
public class ConfigManager
{
	/**配置文件xml标签常量定义*/
	public static final String CONFIG_SET_NAME = "bankportal_config_set";
	public static final String CONFIG_NAME = "bankportal_config";
	public static final String PARAMETER_SET_NAME = "parameter_set";
	public static final String PARAMETER_NAME = "parameter";
	
	/**日志对象*/
	private static Logger log = new Logger(ConfigManager.class);
	/**静态实例*/
	private static ConfigManager m_instance = null;
	/**配置参数集*/
	private Hashtable config_set = null;
	
	/**
	 * 配置文件名
	 */
	public static final String BANKNAME_FILE_NAME = "bankportal.xml";

	/**
	 * Constructor for ConfigManager.
	 */
	private ConfigManager()
	{
		super();
	}

	public static ConfigManager getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new ConfigManager();
			m_instance.config_set = new Hashtable(16);
		}

		return m_instance;
	}

	/**
	 * 加载指定配置文件的配置信息
	 * @param configFileName
	 * @throws IllegalParameterException
	 * @throws ServiceSystemException
	 */
	public void loadConfig(String configFileName) throws SystemException
	{
		log.trace("enter ConfigManager.loadConfig(String configFileName).");
		
		//检测指定文件名的有效性
		if (configFileName == null || configFileName.trim().length() <= 0)
		{
			throw new IllegalParameterException("config file name is not valid.");
		}

		File configFile = null;
		FileInputStream input = null;
		try
		{
			configFile = new File(configFileName);
			log.info("load bs config file:"+ configFile.getAbsolutePath());
			input = new FileInputStream(configFile);
		}
		catch (Exception e)
		{
			throw new IllegalParameterException("The file of the config file name is not exist.", e);
		}

		try
		{
			log.debug("start parse bs config file.");
			Document node = XMLHelper.parse(input);
			this.marshal(node);
		}
		catch (Exception e)
		{
			throw new SystemException("Parser xml config file information errors.", e);
		}
	}

	/**
	 * 获取指定别名的配置对象
	 * @param aliasName 别名
	 * @return ConfigInfo
	 * @throws IllegalParameterException
	 */
	public ConfigInfo getConfigInfo(String aliasName) throws IllegalParameterException
	{
		if (aliasName == null || aliasName.trim().length() <= 0)
		{
			throw new IllegalParameterException("null config information alias name.");
		}
		ConfigInfo config = (ConfigInfo) this.config_set.get(aliasName);
		return config;
	}

	/**
	 * 获取所有配置对象的名称
	 * @return Enumeration
	 */
	public Enumeration getAllConfigInfoName()
	{
		return this.config_set.keys();
	}

	/**
	 * 获取所有配置对象
	 * @return Enumeration
	 */
	public Enumeration getAllConfigInfo()
	{
		return this.config_set.elements();
	}

	/**
	 * 解析xml配置数据
	 * @param xmlDoc
	 * @throws Exception
	 */
	private void marshal(Node xmlDoc) throws Exception
	{
		traverse(xmlDoc);
	}

	private void traverse(Node node) throws Exception
	{
		short type = node.getNodeType();
		String strName = node.getNodeName();
		//log.debug("Node name:" + strName);
		//log.debug("Node type:" + type);
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					traverse(document.getDocumentElement());
					break;
				}
			case Node.ELEMENT_NODE :
				{
					if (CONFIG_NAME.equals(strName)
						&& CONFIG_SET_NAME.equals(node.getParentNode().getNodeName()))
					{
						ConfigInfo config = new ConfigInfo();
						config.marshal(node);
						if(this.config_set.containsKey(config.getAliasName()))
						{
							throw new SystemException("配置信息中不应出现重复的别名.");
						}
						this.config_set.put(config.getAliasName(), config);
						break;
					}

					Node child = node.getFirstChild();
					while (child != null)
					{
						traverse(child);
						child = child.getNextSibling();
					}
					break;
				}
		}
	}
}
