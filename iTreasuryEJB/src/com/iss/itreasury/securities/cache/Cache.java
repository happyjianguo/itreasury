/*
 * Created on 2004-4-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.cache;

/**
 * ���建�������
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Cache{

	private CacheObj firstObj 	= null;				//��һ�����浥Ԫ

	private CacheObj lastObj 	= null;             //���һ�����浥Ԫ
	
	static final int MAXIMUM_CAPACITY = 1 << 30;
	
	private int MAX_CACHE_SIZE	= 100;				//���Ļ�������
	
	private int cacheNum 		= 0;                //��ǰ����������
	
	
	/**
	 * ������,Ĭ�����û����СΪ100
	 *
	 */
	public Cache(){
		firstObj = new CacheObj();
	}
	
	/**
	 * ���ƻ����С�Ĺ�����
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
	 * ���������,����������ͬ��Key��ֵ,��ôɾ��ԭ���е�ֵ
	 */
	public synchronized void add(Object key,Object value){
		/*  first element  */
		if (cacheNum == 0){					//�����������Ϊ�㣬�������ӵ����ݼӵ���һ�����������
			firstObj.set(key,value);
			lastObj = firstObj;
			cacheNum = 1;
		}
		else{								//�������������Ϊ�㣬������Ƿ��Ѿ����ڴ�Key�Ļ��棬�и��£������
			CacheObj obj = getObj(key);		//����Ƿ���ڵ�ǰKey�Ļ���
			
			if (obj != null){		//��,���»���
				obj.set(key,value);
				append(obj);		//�������õ����
			}
			else{					//û�У�����һ������,��������Ѿ���������޶ȣ������ǰ�ߵ�һ�����
				obj = new CacheObj(lastObj,null,key,value);
				lastObj.setNext(obj);
				lastObj = obj;
				cacheNum++;
				
				if (cacheNum > MAX_CACHE_SIZE) remove(0);
			}
			
			
		}
	}

	/**
	 * ��û����С
	 */
	public synchronized int size(){
		return cacheNum;
	}

	//---------------------------  �Ƴ�����  ----------------------------
	/**
	 * �Ƴ�ָ��key�Ļ�����
	 */
	public synchronized void remove(Object key){
		CacheObj obj = getObj(key);
		removeObj(obj);
	}

	/**
	 * �Ƴ�ָ��λ�û�����
	 */
	public synchronized void remove(int cacheNum){
		CacheObj obj = getObj(cacheNum);
		removeObj(obj);
	}

	/**
	 * ���´������
	 */
	private void removeObj(CacheObj obj){
		if (obj!=null){
			if (obj.equals(firstObj)){              	//����ǵ�һ������
				firstObj = firstObj.next();
				firstObj.setPrevious(null);				//�ÿ�ǰ��Ķ���
			}
			else if (obj.equals(lastObj)){				//��������һ������
				obj.previous().setNext(null);
				lastObj = obj.previous();
			}
			else{										//���м�Ķ���
				obj.previous().setNext(obj.next());
				obj.next().setPrevious(obj.previous());
			}
			cacheNum--;
		}
	}
	//---------------------------  �Ѹղ�Ļ����ƶ������  ----------------------------

	/**
	 * ��ĳһ��������ӵ����
	 */
	private void append(CacheObj obj){
		if (!obj.equals(lastObj)){
			removeObj(obj);						//������ɾ���˽�
			lastObj.setNext(obj);
			obj.setPrevious(lastObj);			//���ӵ����һ���������
			obj.setNext(null);					//��һ���������
			lastObj = obj;
			cacheNum++;
		}
	}

	//---------------------------  ��ջ���  ----------------------------
	/**
	 * ��ջ���
	 */
	public synchronized void clear(){
		firstObj = new CacheObj();
		cacheNum = 0;
	}

	//---------------------------  ��û���  ----------------------------

	/**
	 * ��Key��û������,ʹ�õ���
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
	 * �����ֻ�û������
	 */
	private CacheObj getObj(int cacheNum){
		CacheObj obj = firstObj;

		if (cacheNum<0) return null;		//��ֹ����

		for(int n=0;n<cacheNum;n++){
			if (obj != null)
				obj = obj.next();
			else
				break;
		}
		return obj;
	}

	/**
	 * �ӻ����л�û���Ķ��� *** ������
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
	 * ���ָ��key��value
	 */
	public synchronized Object get(Object key){
		CacheObj obj = getObj(key);

		if (obj != null){
			append(obj);						//�ѷ��ʹ��Ļ�������õ����
			return obj.get();
		}
		else
			return null;
	}

	
//---------------------------------���Դ���
	public static void main(String[] args){
		Cache c = new Cache(2000);
		
		for(int n=0;n<5000;n++){
			c.add(""+n,"test"+n);
		}
		
		System.out.println("size:"+c.size()+"//content:"+c.get("1"));
		
	}
}