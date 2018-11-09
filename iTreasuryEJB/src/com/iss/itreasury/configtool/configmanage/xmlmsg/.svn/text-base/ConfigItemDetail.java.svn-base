/*
 * Created on 2004-6-9
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.configtool.configmanage.xmlmsg;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.iss.itreasury.configtool.configmanage.dataentity.ConfigItemInfo;
import com.iss.itreasury.configtool.configmanage.xmlmsg.XMLDataMarshalUtil;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ConfigItemDetail extends XMLInfo
{
	private ArrayList resultSet = new ArrayList();
	
	Node parentNode = null;
	Node currentNode = null;

	/**
	 * Constructor for ResponseMultiAccountCurrentBalance.
	 */
	public ConfigItemDetail()
	{
		super();
	}

	/**
	 * 得到查询到的余额账户个数
	 * @return
	 */
	public int getResultSetSize()
	{
		return resultSet.size();
	}

	/**
	 * 得到单一一笔交易的信息
	 * @param i
	 * @return
	 */
	public ConfigItemInfo getResult(int i)
	{
		return (ConfigItemInfo) resultSet.get(i);
	}

	/**
	 * 将Dom模型反解析成响应对象
	 */
	public void marshal(Node xmlDoc) throws Exception
	{
		if (xmlDoc == null)
		{
			Log.print("xmlDoc is null");
			throw new IException("Gen_E001");
		}

		traverse(xmlDoc, null);
	}

	/**
	 * 将响应返回的XML文件解析成对象
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	private void traverse(Node node, ConfigItemInfo info) throws Exception
	{
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					traverse(document.getDocumentElement(), info);
					break;
				}

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						if ("itreasury_configitem".equals(child.getNodeName()))
						{ //如果是一个账户信息
							info = new ConfigItemInfo(); //new 一个新的信息类
							traverse(child, info); //结息账户信息
							resultSet.add(info);
							info = null;
						}
						else
						{
							traverse(child, info);
						}

						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					/**
					 * 如果账户交易信息AccountTransactionInfo对象不为null
					 * 
					 * 则检测是否存在账户明细交易信息节点,存在则赋值
					 */

					//
					if (info != null && "name".equals(node.getParentNode().getNodeName()))
					{ //
						info.setName(node.getNodeValue());
					}
					else if (info != null && "namemap".equals(node.getParentNode().getNodeName()))
					{ //
						info.setNamemap(node.getNodeValue());
					}
					else if (info != null && "type".equals(node.getParentNode().getNodeName()))
					{ //
						info.setType(node.getNodeValue());
					}
					else if (info != null && "desc".equals(node.getParentNode().getNodeName()))
					{ //
						info.setDesc(node.getNodeValue());
					}
					else if (info != null && "value".equals(node.getParentNode().getNodeName()))
					{ //
						info.setVale(node.getNodeValue());
					}
					/*else 
					{
						XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
					}*/
					break;
				}
		}
	}
	
	/**
	 * 修改XML文件
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	public void modify(Node node, ConfigItemInfo info) throws Exception
	{
		short type = node.getNodeType();
		boolean isModify = false;
		
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					modify(document.getDocumentElement(), info);
					break;
				} 

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						if ("itreasury_configitem".equals(child.getNodeName()))
						{ 
							info.setDesc("");
							modify(child, info); 							
						}
						else
						{
							modify(child, info);
						}

						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					/**
					 * 如果账户交易信息AccountTransactionInfo对象不为null
					 * 
					 * 则检测是否存在账户明细交易信息节点,存在则赋值
					 */

				
					if (info != null && "namemap".equals(node.getParentNode().getNodeName()))
					{ 
						if (node.getNodeValue().equalsIgnoreCase(info.getNamemap()))
						{
							//isModify = true;
							info.setDesc("Modify");
							
						}
					}
					else if (info != null && "value".equals(node.getParentNode().getNodeName())
							&&info.getDesc().equalsIgnoreCase("Modify"))
					{ 						
						node.setNodeValue(info.getVale());
					}
					/*else 
					{
						XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
					}*/
					break;
				}
		}
	}
	
	/**
	 * 修改XML文件
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	public void append(Document node, ConfigItemInfo info) throws Exception
	{
		Node root = node.getFirstChild();
		
		Element xDataBody = node.createElement("itreasury_configitem");		//添加xml数据段标示	
		root.appendChild(xDataBody);
		XMLDataMarshalUtil.createNode(node, xDataBody, "name", info.getName());		
		XMLDataMarshalUtil.createNode(node, xDataBody, "namemap", info.getNamemap());	
		XMLDataMarshalUtil.createNode(node, xDataBody, "type", info.getType());		
		XMLDataMarshalUtil.createNode(node, xDataBody, "desc", info.getDesc());		
		XMLDataMarshalUtil.createNode(node, xDataBody, "value", info.getVale());	
	}
	
	public void delete(Node node, ConfigItemInfo info) throws Exception
	{
		short type = node.getNodeType();
		
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					delete(document.getDocumentElement(), info);
					break;
				} 

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						if ("itreasury_configitem".equals(child.getNodeName()))
						{ 
							info.setDesc("");
							parentNode = child.getParentNode();
							currentNode = child;
										
							delete(child, info); 	
							
							if (info.getDesc().equalsIgnoreCase("delete"))
							{
								parentNode.removeChild(currentNode);
							}
						}
						else
						{
							delete(child, info);
						}
						
						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					/**
					 * 如果账户交易信息AccountTransactionInfo对象不为null
					 * 
					 * 则检测是否存在账户明细交易信息节点,存在则赋值
					 */
					if (info != null && "namemap".equals(node.getParentNode().getNodeName()))
					{ 
						if (node.getNodeValue().equalsIgnoreCase(info.getNamemap()))
						{
							info.setDesc("delete");
						}
					}
					break;
				}
		}
	}

}
