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
 * ��<br>
 * ��ʵ�������ڵ�ӿڵĽڵ��������ͽṹ
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Tree implements java.io.Serializable
{
	/**
	 * ����
	 */
	private String name = null;
	/**
	 * �ڵ�Ԫ�ػ��棬�洢��ǰ�������еĽڵ�Ԫ��
	 */
	private HashMap nodeElementMap = new HashMap();
	/**
	 * �ڵ㻺�棬�洢��ǰ�������еĽڵ�
	 */
	private HashMap nodeAddr = new HashMap();
	private boolean valid = false;
	private Node[] treeNodes = new Node[0];
	
	/**
	 * ���췽��
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
	 * ���ڵ���<br>
	 * ��װ�ڵ�Ԫ��
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
	 * ������ڵ�
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
	 * ���ݽڵ�Id��ȡ�ڵ�
	 * @param nodeId
	 * @return
	 */
	public NodeElement getNodeElement(long nodeElementId)
	{
		return (NodeElement)nodeElementMap.get(new Long(nodeElementId));
	}
	
	/**
	 * ���ݽڵ�Id�Ƴ��ڵ�
	 * @param nodeId
	 */
	public void removeNodeElement(long nodeElementId)
	{
		nodeElementMap.remove(new Long(nodeElementId));
		valid = false;
	}
	
	/**
	 * ������нڵ�
	 *
	 */
	public void clear()
	{
		nodeElementMap.clear();
		valid = false;
	}
	
	/**
	 * ����ArrayList�ṹ����<br>
	 * ���ṹ���£�<br>
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
				//����Ҳ������ڵ㣬˵����ǰ�ڵ����һ����
				root.add(node);
			}
			else
			{
				//�����ڸ��ڵ��н���������ϵ
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
	 * ���ݽڵ�Id��ȡ�ڵ�
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
	 * ����toString������ʵ����״�ṹ�����
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
	 * ������״�˵��������˵�ҳ���������ҳ�涯̬�˵�
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
