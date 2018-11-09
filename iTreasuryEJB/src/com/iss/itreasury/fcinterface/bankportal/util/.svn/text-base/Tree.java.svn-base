/*
 * Created on 2005-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 树<br>
 * 将实现了树节点接口的节点生成树型结构
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Tree implements java.io.Serializable
{
	/**
	 * 树名
	 */
	private String name = null;
	/**
	 * 节点元素缓存，存储当前树中所有的节点元素
	 */
	private HashMap nodeElementMap = new HashMap();
	/**
	 * 节点缓存，存储当前树中所有的节点
	 */
	private HashMap nodeAddr = new HashMap();
	private boolean valid = false;
	private Node[] treeNodes = new Node[0];
	
	/**
	 * 构造方法
	 *
	 */
	public Tree()
	{
		this.name = "tree";
	}
	public Tree(String name)
	{
		this.name = name;
	}
	
	/**
	 * 树节点类<br>
	 * 封装节点元素
	 * @author mxzhou
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public class Node  implements java.io.Serializable
	{
		private NodeElement nodeElement = null;
		private ArrayList childNodes = new ArrayList();
		private Node parentNode = null;
		
		public NodeElement getNodeElement()
		{
			return nodeElement;
		}
		
		public Node getParentNode()
		{
			return parentNode;
		}
		
		public boolean hasChild()
		{
			if(childNodes.size() > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public Node[] getChildNodes()
		{
			Node[] childs = new Node[0];
			if(childNodes.size() > 0)
			{
				childs = (Node[])childNodes.toArray(childs);
			}
			return childs;
		}
	}
	/**
	 * 添加树节点
	 * @param node
	 */
	public void addNodeElement(NodeElement nodeElement)
	{
		if(nodeElement != null)
		{
			nodeElementMap.put(new Long(nodeElement.getId()), nodeElement);
			valid = false;
		}
	}
	
	/**
	 * 根据节点Id获取节点
	 * @param nodeId
	 * @return
	 */
	public NodeElement getNodeElement(long nodeElementId)
	{
		return (NodeElement)nodeElementMap.get(new Long(nodeElementId));
	}
	
	/**
	 * 根据节点Id移除节点
	 * @param nodeId
	 */
	public void removeNodeElement(long nodeElementId)
	{
		nodeElementMap.remove(new Long(nodeElementId));
		valid = false;
	}
	
	/**
	 * 清除所有节点
	 *
	 */
	public void clear()
	{
		nodeElementMap.clear();
		valid = false;
	}
	
	/**
	 * 创建ArrayList结构的树<br>
	 * 树结构如下：<br>
	 * (String)treeName<br>
	 * (ArrayList)root---->(TreeNodeElement)node<br>
	 *                     (ArrayList)child<br>
	 *                ---->(TreeNodeElement)node<br>
	 * 	                   (ArrayList)child---->(TreeNodeElement)node<br>
	 * 	                                        (ArrayList)child<br>
	 * @return
	 */
	public Node[] generateTree()
	{
		if(valid)
		{
			return this.treeNodes;
		}
		
		Node[] rootNodes = new Node[0];
		
		if(nodeElementMap.size() <= 0)
		{
			return rootNodes;
		}
		ArrayList root = new ArrayList();
		this.initNodeAddr();
		HashMap usedNode = new HashMap();
		
		Long nodeId = null;
		Long parentNodeId = null;
		NodeElement nodeElement = null;
		Node node = null;
		Node parentNode = null;
		Set fieldNames = nodeElementMap.keySet();
		Iterator it = fieldNames.iterator();
		while (it.hasNext())
		{
			nodeId = (Long) it.next();
			nodeElement = (NodeElement)nodeElementMap.get(nodeId);
			node = (Node)nodeAddr.get(nodeId);
			
			parentNodeId = new Long(nodeElement.getParentId());
			if(!nodeAddr.containsKey(parentNodeId))
			{
				//如果找不到父节点，说明当前节点就是一个根
				root.add(node);
			}
			else
			{
				//否则，在父节点中建立关联关系
				parentNode = (Node)nodeAddr.get(parentNodeId);
				parentNode.childNodes.add(node);
				node.parentNode = parentNode;
			}
		}
		
		if(root.size() > 0)
		{
			rootNodes = (Node[])root.toArray(rootNodes);
		}
		this.treeNodes = rootNodes;
		valid = true;
		return this.treeNodes;
	}
	
	/**
	 * 根据节点Id获取节点
	 * @param nodeId
	 * @return
	 */
	public Node getNode(long nodeElementId)
	{
		if(!valid)
		{
			generateTree();
		}
		return (Node)nodeAddr.get(new Long(nodeElementId));
	}
	
	private void initNodeAddr()
	{
		nodeAddr.clear();
		
		Set fieldNames = nodeElementMap.keySet();
		Iterator it = fieldNames.iterator();
		while (it.hasNext())
		{
			Long nodeElementId = (Long) it.next();
			Node node = new Node();
			node.nodeElement = (NodeElement)nodeElementMap.get(nodeElementId);
			node.childNodes = new ArrayList();
			nodeAddr.put(nodeElementId, node);
		}
	}
	
	
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 重载toString方法，实现树状结构的输出
	 */
	public String toString()
	{
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("===="+Tree.class.getName()+" instance:["+this.hashCode()+"] name:["+this.getName()+"]====\n");
		printNodes(this.generateTree(), 0, strBuff);
		strBuff.append("===="+Tree.class.getName()+" end====");
		return strBuff.toString();
	}
	
	private void printNodes(Node[] nodes, int indent, StringBuffer strBuff)
	{
		String strIndent = "    ";
		int indentTemp = indent;
		String strIndentTemp = "";
		while(indentTemp-- >= 0)
		{
			strIndentTemp = strIndentTemp + strIndent;
		}
		for(int i = 0; i < nodes.length; i++)
		{
			NodeElement nodeElement = nodes[i].getNodeElement();
			strBuff.append(strIndentTemp + nodeElement.getName() + "\n");
			printNodes(nodes[i].getChildNodes(), indent+1, strBuff);
		}
	}
	
	/**
	 * 生成树状菜单，与树菜单页面配合生成页面动态菜单
	 * @return
	 */
	public String generateTreeString()
	{
		//System.out.println("generateTreeString");
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("[\n");
		generateNodesString(this.generateTree(), 0, strBuff);
		strBuff.append("]");
		return strBuff.toString();
	}
	
	private void generateNodesString(Node[] nodes, int indent, StringBuffer strBuff)
	{
		String strIndent = "    ";
		int indentTemp = indent;
		String strIndentTemp = "";
		while(indentTemp-- >= 0)
		{
			strIndentTemp = strIndentTemp + strIndent;
		}
		for(int i = 0; i < nodes.length; i++)
		{
			NodeElement nodeElement = nodes[i].getNodeElement();
			String name = nodeElement.getName();
			Object property = nodeElement.getProperty();
			strBuff.append(strIndentTemp + "['");
			strBuff.append(nodeElement.getName()+"', ");
			strBuff.append(property == null?"null":"'"+property.toString()+"'");
			if(nodes[i].hasChild())
			{
				strBuff.append(",\n");
				generateNodesString(nodes[i].getChildNodes(), indent+1, strBuff);
				strBuff.append(strIndentTemp + "],\n");
			}
			else
			{
				strBuff.append("],\n");
			}
		}
	}
}
