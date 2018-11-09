/*
 * Created on 2004-5-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.mywork.dataentity;

import com.iss.itreasury.util.Log;


/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MyWorkColumn implements Cloneable{
	private ColumnNode rootNode = null;
	private ColumnNode currentNode = null;
	
	public MyWorkColumn(long columnTypeId,long statusId,long actionTypeId){
		this.rootNode = new ColumnNode(null);
		this.rootNode.setColumnTypeId(columnTypeId);
		this.rootNode.setNodeStatusId(statusId);
		this.rootNode.setActionTypeId(actionTypeId);
		this.currentNode = this.rootNode;
	}
	
	/**
	 * 当前节点追溯到父级节点,
	 *
	 */
	public void upToParent(){
		if (!this.currentNode.equals(this.rootNode))
			this.currentNode = this.currentNode.getParentNode();
	}
	
	/**
	 * 把根节点置为当前节点
	 *
	 */
	public void upToRoot(){
		this.currentNode = this.rootNode;
	}
	
	/**
	 * 得到当前节点的层次
	 * @return
	 */
	public long getCurrentLevel(){
		return this.currentNode.getNodeLevel();
	}
	
	/**
	 * 将当前的树上追加另一棵树
	 * @param column
	 * @return
	 */
	public void append(MyWorkColumn column){
		this.currentNode.add(column.rootNode);
		column.rootNode.setParentNode(this.currentNode);
		refresh(column.rootNode);				//刷新级别和数据记录信息
		this.upToRoot();
	}

	/**
	 * 刷新级别信息
	 * @param node
	 */
	public void refresh(ColumnNode node){
		long newLevel = 0;
		if (node.getParentNode()!=null){
			newLevel = node.getParentNode().getNodeLevel()+1;
		}
		node.setNodeLevel(newLevel);						//设置新级别
		//node.setColumnTypeId(rootNode.getColumnTypeId());	//设置所有节点为根节点类型
		//node.setActionTypeId(rootNode.getActionTypeId());	//设置所有节点为根节点操作类型
		
		long dataCount = 0;
		if (node.getChildNodeNum()>0){
			for (int n=0;n<node.getChildNodeNum();n++){
				ColumnNode subNode = (ColumnNode)node.getChildNodes().get(n);
				refresh(subNode);					//递归写入级别信息
				dataCount += subNode.getDataCount();
			}
			node.setDataCount(dataCount);			//刷新数据信息
		}
	}
	
	/**
	 * 将一个节点数组按照节点名称对应添加到一个树的当前枝上
	 * @param column
	 * @param nodes
	 */
	public void addNodesToColumn(MyWorkColumn column ,MyWorkColumn copy,ColumnNode[] nodes){
		boolean isTouched = false;
		if (copy.currentNode.getNodeId()!=-1){
			for (int n=0;n<nodes.length;n++){
				/**
				 * 如果节点数组的一个节点的ID以树的当前枝的节点ID开始,那么它时当前枝的分支
				 */
				if (String.valueOf(nodes[n].getNodeId()).startsWith("" + copy.currentNode.getNodeId())){
					column.add(nodes[n]);				//添加该节点到当前枝
					column.upToParent();				//回到父节点
					isTouched = true;					//更改标志位
				}
				else{
					if (isTouched) break;				//因为已经经过了排序,所以如果标志位为true,说明添加已经结束了
				}
			}
		}
		/**
		 * 如果当前枝下面有分支,那么递归写入分支信息
		 */
		if (copy.currentNode.getChildNodeNum()>0){
			for(int n=0;n<copy.currentNode.getChildNodeNum();n++){
				column.setCurrentNode((ColumnNode)column.currentNode.getChildNodes().get(n));
				copy.setCurrentNode((ColumnNode)copy.currentNode.getChildNodes().get(n));
				addNodesToColumn(column,copy,nodes);
				column.upToParent();
				copy.upToParent();
			}
		}
	}
	
	
	/**
	 * 直接添加一个节点
	 * @param node
	 */
	public void add(ColumnNode node){
		this.add(node.getNodeId(),
				node.getNodeName(),
				node.getNodeStatusId(),
				node.getDataCount(),
				node.getNodeURL());
		
	}
	
	/**
	 * 在当前节点下添加新节点,添加完毕后当前节点自动移动到新添加节点
	 *
	 */
	public void add(long nodeId,String nodeName,long nodeStatusId,long dataCount,String nodeURL){
		ColumnNode node = new ColumnNode(this.currentNode);
		
		node.setNodeId(nodeId);
		node.setNodeName(nodeName);
		node.setNodeStatusId(nodeStatusId);
		node.setDataCount(dataCount);
		node.setNodeURL(nodeURL);
		node.setColumnTypeId(rootNode.getColumnTypeId());
		node.setActionTypeId(rootNode.getActionTypeId());
		
		this.currentNode.add(node);
		this.currentNode = node;
	}
	
	/**
	 * 深度科隆,保留
	 *
	public Object clone(){
		MyWorkColumn column = null;
		try{
			column = (MyWorkColumn)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		column.currentNode = (ColumnNode)column.currentNode.clone();
		column.rootNode = (ColumnNode)column.rootNode.clone();
		return column;
	}*/
	
	/**
	 * @return Returns the currentNode.
	 */
	public ColumnNode getCurrentNode() {
		return currentNode;
	}

	/**
	 * @param currentNode The currentNode to set.
	 */
	public void setCurrentNode(ColumnNode currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * @return Returns the rootNode.
	 */
	public ColumnNode getRootNode() {
		return rootNode;
	}

	/**
	 * @param rootNode The rootNode to set.
	 */
	public void setRootNode(ColumnNode rootNode) {
		this.rootNode = rootNode;
	}
}
