/*
 * Created on 2004-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.util.dataentity;

import java.util.Hashtable;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClientType {

	/**
	 * 
	 */
	public ClientType() {
	}
	private long clientTypeId ;
	
	private Hashtable hash = new Hashtable();
	/**
	 * @return Returns the clientTypeId.
	 */
	public long getClientTypeId() {
		return clientTypeId;
	}
	/**
	 * @param clientTypeId The clientTypeId to set.
	 */
	public void setClientTypeId(long clientTypeId) {
		this.clientTypeId = clientTypeId;
	}
	
	public void addPrefix(Prefix prefix){
		if(prefix!=null && prefix.getPrefixString()!=null)
		hash.put(prefix.getPrefixString(),prefix);
	}
	
	public Hashtable getAllPrefixes(){
		return hash;
	}
	
	
	public Prefix getPrefix(String str){
		Prefix rnt = null;
		if(hash!=null){
			Prefix a = (Prefix)hash.get(str);
		}
		return rnt;
	}
	
	public static void main(String[] args) {
	}
}
