/*
 * Created on 2004-5-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.mywork.dataentity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ColumnNode implements Cloneable{
	private long nodeId 					= -1;				//节点代码
	private String nodeName					= "";				//节点名称
	
	private long columnTypeId				= -1;				//栏位类型
	private long actionTypeId				= -1;				//操作类型
	
	private long nodeStatusId				= -1;				//节点状态
	private long dataCount					= 0;				//下属数据总数				
	private String nodeURL					= "";				//节点链接URL
	
	private long nodeLevel					= 0;				//节点层次,0 表示根节点
	
	private ColumnNode parentNode			= null;				//父节点
	private ArrayList childNodes			= null;				//子节点
	
	/**
	 * 默认构建器传入父节点对象,如果为null则表示当前节点为一级节点
	 * @param parentNode
	 */
	public ColumnNode(ColumnNode parentNode){
		this.parentNode = parentNode;
		if (parentNode == null){
			this.nodeLevel = 0;
		}
		else{
			this.nodeLevel = parentNode.getNodeLevel() + 1;
		}
	}
	
	/**
	 * 添加子节点
	 * @param childNode
	 */
	public void add(ColumnNode childNode){
		if (childNodes == null) childNodes = new ArrayList();		//如果子节点容器为null,初始化容器
		this.childNodes.add(childNode);
		this.dataCount += childNode.getDataCount();					//本节点的数据数量是子节点数据数量的总和
		ColumnNode node = this;
		
		while(node.parentNode!=null){								//更新父节点数据数量
			this.parentNode.setDataCount(this.parentNode.getChildNodeNum()+ childNode.getDataCount());
			node = node.getParentNode();
		}
	}
	
	/**
	 * 判断是否有子节点
	 * @return
	 */
	public boolean hasChildren(){
		return this.childNodes!=null && this.childNodes.size()>0 ? true : false;
	}
	
	/**
	 * 得到直属子节点数量
	 * @return
	 */
	public long getChildNodeNum(){
		return this.childNodes!=null ? this.childNodes.size() : 0;
	}
	
	/**
	 * 深度科隆所需方法,保留
	 *
	public Object clone(){
		ColumnNode newNode = null;
		try{
			newNode = (ColumnNode)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		newNode.setNodeName(new String(this.getNodeName()));
		newNode.setNodeURL(new String(this.getNodeURL()));
		
		if (this.getParentNode()!=null)
			newNode.setParentNode((ColumnNode)this.getParentNode().clone());
		if (this.getChildNodes()!=null)
			newNode.setChildNodes((ArrayList)this.getChildNodes().clone());
		return newNode;
	}*/
	
	/**
	 * @return Returns the nodeLevel.
	 */
	public long getNodeLevel() {
		return nodeLevel;
	}

	/**
	 * @return Returns the dataCount.
	 */
	public long getDataCount() {
		return dataCount;
	}

	/**
	 * @param dataCount The dataCount to set.
	 */
	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
	}

	/**
	 * @return Returns the nodeId.
	 */
	public long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId The nodeId to set.
	 */
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return Returns the nodeName.
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName The nodeName to set.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return Returns the nodeStatusId.
	 */
	public long getNodeStatusId() {
		return nodeStatusId;
	}

	/**
	 * @param nodeStatusId The nodeStatusId to set.
	 */
	public void setNodeStatusId(long nodeStatusId) {
		this.nodeStatusId = nodeStatusId;
	}

	/**
	 * @return Returns the parentNode.
	 */
	public ColumnNode getParentNode() {
		return parentNode;
	}

	/**
	 * @param parentNode The parentNode to set.
	 */
	public void setParentNode(ColumnNode parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @return Returns the nodeURL.
	 */
	public String getNodeURL() {
		return nodeURL;
	}

	/**
	 * @param nodeURL The nodeURL to set.
	 */
	public void setNodeURL(String nodeURL) {
		this.nodeURL = nodeURL;
	}

	/**
	 * @return Returns the childNodes.
	 */
	public ArrayList getChildNodes() {
		return childNodes;
	}


	/**
	 * @param nodeLevel The nodeLevel to set.
	 */
	public void setNodeLevel(long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	/**
	 * @param childNodes The childNodes to set.
	 */
	public void setChildNodes(ArrayList childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * @return Returns the columnTypeId.
	 */
	public long getColumnTypeId() {
		return columnTypeId;
	}

	/**
	 * @param columnTypeId The columnTypeId to set.
	 */
	public void setColumnTypeId(long columnTypeId) {
		this.columnTypeId = columnTypeId;
	}

	/**
	 * @return Returns the actionTypeId.
	 */
	public long getActionTypeId() {
		return actionTypeId;
	}

	/**
	 * @param actionTypeId The actionTypeId to set.
	 */
	public void setActionTypeId(long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

}
