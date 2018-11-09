/*
 * Created on 2004-4-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.cache;

/**
 * 缓存的单位对象,缓存是由缓存单位对象组成的链构成的
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CacheObj{

	private CacheObj previousObj= null;         	//上一个环节缓存单元

	private CacheObj nextObj 	= null;             //下一个环节缓存单元

	private Object key 			= null;             //缓存单元存储Key

	private Object value 		= null;				//缓存单元存储的Value

	/**
	 * 构建器
	 */
	public CacheObj(CacheObj previousObj,CacheObj nextObj,Object key,Object value){
		this.previousObj 		= previousObj;
		this.nextObj 			= nextObj;
		this.key 				= key;
		this.value 				= value;
	}
	/**
	 * 默认构建器
	 */
	public CacheObj(){
		this(null,null,null,null);
	}
	
	/**
	 * 给缓存单元置入上一个环节
	 * @param previousObj
	 */
	public void setPrevious(CacheObj previousObj){
		this.previousObj = previousObj;
	}

	/**
	 * 置入下一个环节缓存单元
	 */
	public void setNext(CacheObj nextObj){
		this.nextObj 			= nextObj;
	}

	/**
	 * 判断当前的对象中存的Key和传入的Key是否一致,一致则返回当前对象的Value
	 */
	public Object get(Object key){
		if (this.key.equals(key)) return value;
		else return null;
	}

	/**
	 * 返回当前对象的Value
	 */
	public Object get(){
		return value;
	}

	/**
	 * 给对象置入Key和Value
	 */
	public void set(Object key,Object value){
		this.key 				= key;
		this.value 				= value;
	}

	/**
	 * 得到当前缓存单元的下一个环节缓存单元
	 */
	public CacheObj next(){
		return nextObj;
	}

	/**
	 * 得到当前缓存单元的下一个环节缓存单元
	 */
	public CacheObj previous(){
		return previousObj;
	}
}