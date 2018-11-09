/*
 * Created on 2004-4-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.cache;

/**
 * 整体缓存控制类
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Cache{

	private CacheObj firstObj 	= null;				//第一个缓存单元

	private CacheObj lastObj 	= null;             //最后一个缓存单元
	
	static final int MAXIMUM_CAPACITY = 1 << 30;
	
	private int MAX_CACHE_SIZE	= 100;				//最大的缓存数量
	
	private int cacheNum 		= 0;                //当前缓存对象个数
	
	
	/**
	 * 构建器,默认设置缓存大小为100
	 *
	 */
	public Cache(){
		firstObj = new CacheObj();
	}
	
	/**
	 * 限制缓存大小的构建器
	 * @param maxSize
	 */
	public Cache(int maxSize){
		this();
		
		if (maxSize<0 || maxSize > MAXIMUM_CAPACITY){
			throw new IllegalArgumentException("Illegal initial capacity: " +
														maxSize);
		}
		MAX_CACHE_SIZE = maxSize;
	}

	/**
	 * 添加链方法,如果添加了相同的Key的值,那么删除原来有的值
	 */
	public synchronized void add(Object key,Object value){
		/*  first element  */
		if (cacheNum == 0){					//如果缓存数量为零，则把新添加的数据加到第一个缓存对象里
			firstObj.set(key,value);
			lastObj = firstObj;
			cacheNum = 1;
		}
		else{								//如果缓存数量不为零，则查找是否已经存在此Key的缓存，有更新，无添加
			CacheObj obj = getObj(key);		//检查是否存在当前Key的缓存
			
			if (obj != null){		//有,更新缓存
				obj.set(key,value);
				append(obj);		//将对象置到最后
			}
			else{					//没有，新增一条缓存,新增如果已经超过最大限度，则把最前边的一条清除
				obj = new CacheObj(lastObj,null,key,value);
				lastObj.setNext(obj);
				lastObj = obj;
				cacheNum++;
				
				if (cacheNum > MAX_CACHE_SIZE) remove(0);
			}
			
			
		}
	}

	/**
	 * 获得缓存大小
	 */
	public synchronized int size(){
		return cacheNum;
	}

	//---------------------------  移除缓存  ----------------------------
	/**
	 * 移除指定key的缓存链
	 */
	public synchronized void remove(Object key){
		CacheObj obj = getObj(key);
		removeObj(obj);
	}

	/**
	 * 移除指定位置缓存链
	 */
	public synchronized void remove(int cacheNum){
		CacheObj obj = getObj(cacheNum);
		removeObj(obj);
	}

	/**
	 * 重新搭建缓存链
	 */
	private void removeObj(CacheObj obj){
		if (obj!=null){
			if (obj.equals(firstObj)){              	//如果是第一个对象
				firstObj = firstObj.next();
				firstObj.setPrevious(null);				//置空前面的对象
			}
			else if (obj.equals(lastObj)){				//如果是最后一个对象
				obj.previous().setNext(null);
				lastObj = obj.previous();
			}
			else{										//是中间的对象
				obj.previous().setNext(obj.next());
				obj.next().setPrevious(obj.previous());
			}
			cacheNum--;
		}
	}
	//---------------------------  把刚查的缓存移动到最后  ----------------------------

	/**
	 * 把某一个环节添加到最后
	 */
	private void append(CacheObj obj){
		if (!obj.equals(lastObj)){
			removeObj(obj);						//从链上删除此结
			lastObj.setNext(obj);
			obj.setPrevious(lastObj);			//链接到最后一个对象后面
			obj.setNext(null);					//下一个对象清空
			lastObj = obj;
			cacheNum++;
		}
	}

	//---------------------------  清空缓存  ----------------------------
	/**
	 * 清空缓存
	 */
	public synchronized void clear(){
		firstObj = new CacheObj();
		cacheNum = 0;
	}

	//---------------------------  获得缓存  ----------------------------

	/**
	 * 用Key获得缓存对象,使用倒序
	 */
	public CacheObj getObj(Object key){

		CacheObj obj = lastObj;
		Object value = null;
		while(obj!=null){
			value = obj.get(key);
			if (value != null) break;
			obj = obj.previous();
		}
		return obj;
	}
	/**
	 * 用数字获得缓存对象
	 */
	private CacheObj getObj(int cacheNum){
		CacheObj obj = firstObj;

		if (cacheNum<0) return null;		//防止负数

		for(int n=0;n<cacheNum;n++){
			if (obj != null)
				obj = obj.next();
			else
				break;
		}
		return obj;
	}

	/**
	 * 从缓存中获得缓存的对象 *** 主方法
	 */
	public synchronized Object get(int cacheNum){
		CacheObj obj = getObj(cacheNum);

		if (obj != null){
			return obj.get();
		}

		else
			return null;
	}

	 /**
	 * 获得指定key的value
	 */
	public synchronized Object get(Object key){
		CacheObj obj = getObj(key);

		if (obj != null){
			append(obj);						//把访问过的缓存对象置到最后
			return obj.get();
		}
		else
			return null;
	}

	
//---------------------------------测试代码
	public static void main(String[] args){
		Cache c = new Cache(2000);
		
		for(int n=0;n<5000;n++){
			c.add(""+n,"test"+n);
		}
		
		System.out.println("size:"+c.size()+"//content:"+c.get("1"));
		
	}
}