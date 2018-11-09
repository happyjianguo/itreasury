package com.iss.itreasury.fcinterface.bankportal.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.IllegalParameterException;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * xml������Ϣ����<br>
 * �ṩ�������Ӧ��������Ϣ�洢����
 * @author mxzhou
 */
public class ConfigInfo
{
	/**��־����*/
	private static Logger log = new Logger(ConfigInfo.class);
	/**������Ϣ�ı���*/
	private String alias_name = null;
	/**������Ϣ������*/
	private ArrayList parameter_set = new ArrayList(8);
	/**���ò�����Ч��־*/
	private boolean isValid = false;

	/**
	 * �����Զ���
	 */
	class ParameterInfo
	{
		private String name = null; //------���ò����Ĳ�����
		private String value = null; //------���ò����Ĳ���ֵ

		/**
		 * Constructor for ParameterInfo.
		 */
		ParameterInfo()
		{
			super();
		}

		protected void marshal(Node xmlDoc) throws Exception
		{
			if (xmlDoc == null)
			{
				throw new IllegalParameterException("null DOM object.");
			}

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
						Node child = node.getFirstChild();
						while (child != null)
						{
							traverse(child);

							child = child.getNextSibling();
						}
						break;
					}

				case Node.TEXT_NODE :
					{
						ConfigInfo.setValueFromNodeToObject(node, this);
						break;
					}
			}
		}
	}

	/**
	 * Constructor for ConfigInfo.
	 */
	public ConfigInfo()
	{
		super();
	}

	/**
	 * ����xml����
	 * @param xmlDoc
	 * @throws Exception
	 */
	protected void marshal(Node xmlDoc) throws Exception
	{
		if (xmlDoc == null)
		{
			throw new IllegalParameterException("null DOM object.");
		}

		traverse(xmlDoc);

		//У�鵱ǰ���õ�Ԫ��������Ч�ԣ�ֻУ���κη��񶼱����еĲ�������ǰ
		//����alias_name��factory��������alias_name�Ƿ��ظ����ڴ�У��
		if (this.alias_name == null)
		{
			throw new SystemException("config info must offer alias name parameter.");
		}
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
					if (ConfigManager.PARAMETER_NAME.equals(strName)
							&& ConfigManager.PARAMETER_SET_NAME.equals(node.getParentNode().getNodeName()))
					{
						ParameterInfo param = new ParameterInfo();
						param.marshal(node);
						this.parameter_set.add(param);
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

			case Node.TEXT_NODE :
				{
					ConfigInfo.setValueFromNodeToObject(node, this);
					break;
				}
		}
	}

	/**
	 * ��ConfigInfo����ת��ΪHashtable��ʽ������ֵ��
	 * ע������в�����ֵΪnull�򡰡��Ĳ���
	 * @return Hashtable
	 */
	public Hashtable getProperties()
	{
		Hashtable result = new Hashtable(6);

		ParameterInfo param = null;
		for (int i = 0; i < this.parameter_set.size(); i++)
		{
			param = (ParameterInfo) this.parameter_set.get(i);

			if (param.name != null && param.value != null)
			{
				result.put(param.name, param.value);
			}
		}

		return result;
	}

	private static void setValueFromNodeToObject(Node node, Object obj) throws Exception
	{
		short type = node.getNodeType();
		String strName = node.getNodeName();

		if (strName == null || "".equals(strName))
			return;

		switch (type)
		{
			case Node.TEXT_NODE :
				{
					Node parent = node.getParentNode();
					String strFieldName = parent.getNodeName().trim();
					String strFieldValue = node.getNodeValue();

					if (strFieldValue == null || "".equals(strFieldValue.trim()))
					{
						return;
					}
					else
					{
						strFieldValue = strFieldValue.trim();
					}

					//��õ�ǰ����ָ�����Ƶ�Field����
					Class classTemp = obj.getClass();
					Field field = null;
					while (!classTemp.getName().equals("java.lang.Object"))
					{
						try
						{
							field = classTemp.getDeclaredField(strFieldName);

							field.setAccessible(true);
							break;
						}
						catch (NoSuchFieldException e)
						{
							log.debug("try to find field in super class of " + classTemp.getName());
							classTemp = classTemp.getSuperclass();
						}
					}

					if (field == null)
					{
						log.info(obj.getClass().getName() + " has not field named \"" + strFieldName + "\"");

						return;
					}
					//��ֵ
					try
					{
						field.set(obj, strFieldValue);
					}
					catch (IllegalArgumentException exp)
					{
						throw new Exception("��ֵ����������Ч��ӦΪ" + field.getType().getName());
					}
					catch (IllegalAccessException exp)
					{
						throw new Exception(classTemp.getName() + "�ܰ�ȫԼ�����޷����ʣ�");
					}
					catch (SecurityException exp)
					{
						throw new Exception(classTemp.getName() + "�ܰ�ȫԼ�����޷����ʣ�");
					}

					break;
				}
		}

	}
	
	/**
	 * Ϊ���ڵ��ԣ�����������ӡ��������null�͡���
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sbResult = new StringBuffer(128);

		sbResult.append(
			"ConfigInfo class instance (hashCode=" + this.hashCode() + ",name=" + this.alias_name + ")\r\n");
		sbResult.append("=========================================\r\n");

//		sbResult.append(BankServiceManager.ENV_BANK_SERVICE_FACTORY_CLASS);
//		sbResult.append(" = ");
//		sbResult.append(this.factory);
//		sbResult.append(";\r\n");

		ParameterInfo param = null;
		for (int i = 0; i < this.parameter_set.size(); i++)
		{
			param = (ParameterInfo) this.parameter_set.get(i);

			sbResult.append(param.name);
			sbResult.append(" = \"");
			sbResult.append(param.value);
			sbResult.append("\";\r\n");
		}

		return sbResult.toString();
	}

	/**
	 * Returns the alias_name.
	 * @return String
	 */
	public String getAliasName()
	{
		return alias_name;
	}

	/**
	 * Returns the isChecked.
	 * @return boolean
	 */
	public boolean isValid()
	{
		return isValid;
	}

	/**
	 * Sets the isChecked.
	 * @param isChecked The isChecked to set
	 */
	public void setValid(boolean isChecked)
	{
		this.isValid = isChecked;
	}

}
