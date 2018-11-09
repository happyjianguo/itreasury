package com.iss.itreasury.configtool.configmanage.xmlmsg;

import java.lang.reflect.Field;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

//import com.iss.itreasury.bs.cmbc.xmlmsg.XMLInfo;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class XMLDataMarshalUtil
{
	//private static Logger logger = Logger.getLogger(XMLDataMarshalUtil.class);
		
	/**
	 * Constructor for XMLDataMarshalUtil.
	 */
	public XMLDataMarshalUtil()
	{
		super();
	}
	

	
	/**
	 * 将XML DOM对象转换为指定对象
	 * @param obj
	 * @param xmlDoc
	 * @throws Exception
	 */
	public static void marshal(Object obj, Node xmlDoc) throws Exception
	{
		if (obj == null || xmlDoc == null)
		{
			Log.print("xmlDoc||obj is null");
			throw new IException("Gen_E001");
		}

		if (obj instanceof XMLInfo)
		{
			XMLInfo info = (XMLInfo) obj;
			info.marshal(xmlDoc);
		}
		

	}

	/**
	 * 将指定对象转换为XML DOM对象
	 * @param obj
	 * @return Node
	 * @throws Exception
	 */
	public static Node unmarshal(Object obj) throws Exception
	{
		Node xmlDoc = null;

		if (obj == null)
		{
			Log.print("obj is null");
			throw new IException("Gen_E001");
		}

		if (obj instanceof XMLInfo)
		{
			XMLInfo info = (XMLInfo) obj;
			xmlDoc = info.unmarshal(null);
		}

		return xmlDoc;
	}

	/**
	 * 根据指定的结点名称(nodeName)创建一个结点加入到指定的父结点(parentNode)
	 * 中。新创建的结点的值为value对象中与nodeName同名的域值。
	 * 
	 * 注：value对象中对应的域必须为String类型
	 * 
	 * 异  常： 
	 * 当value对象不存在与nodeName同名的域()时，报异常 
	 * 当value对象中对应的域不是String类型时，报异常
	 * 当value对象中对应的域值为null时，生成空结点(工行要求)
	 * 当parentNode对象为null时，创建出的结点不执行加入父结点的操作，不报异常，供嵌套类使用
	 * 
	 * @param doc 不能为null
	 * @param parentNode 可以为null
	 * @param nodeName 不能为null
	 * @param valueObj 不能为null
	 * @return Node 放回当前创建的结点
	 * @throws Exception
	 */
	public static Node createNode(Document doc, Node parentNode, String nodeName, String value) throws Exception
	{
		//构造Node
		Element element = doc.createElement(nodeName);

		//parentNode 可以为空
		if (parentNode != null)
			parentNode.appendChild(element);

		if (value == null)
			value = "";
		Text content = doc.createTextNode(value);
		element.appendChild(content);

		return element;
	}

	public static void setValueFromNodeToObject(Node node, Object obj) throws Exception
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

					//获得当前对象指定名称的Field对象
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
							//logger.debug("try to find field in super class of " + classTemp.getName());
							classTemp = classTemp.getSuperclass();
						}
					}

					if (field == null)
					{
						//logger.info(obj.getClass().getName() + " has not field named \"" + strFieldName + "\"");

						return;
					}
					//赋值
					try
					{
						field.set(obj, strFieldValue);
					}
					catch (IllegalArgumentException exp)
					{
						throw new Exception("赋值对象类型无效！应为" + field.getType().getName());
					}
					catch (IllegalAccessException exp)
					{
						throw new Exception(classTemp.getName() + "受安全约束，无法访问！");
					}
					catch (SecurityException exp)
					{
						throw new Exception(classTemp.getName() + "受安全约束，无法访问！");
					}

					break;
				}
		}

	}

}
