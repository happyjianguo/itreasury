/*
 * Created on 2005-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

/**
 * 树节点接口<br>
 * 所有欲实现树结构的节点，均应实现该接口
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface NodeElement extends java.io.Serializable
{
	/**
	 * 获取当前节点ID
	 * @return
	 */
	public long getId();
	/**
	 * 获取父节点ID
	 * @return
	 */
	public long getParentId();
	/**
	 * 获取节点名称
	 * @return
	 */
	public String getName();
	/**
	 * 获取节点属性
	 * @return
	 */
	public Object getProperty();
}
