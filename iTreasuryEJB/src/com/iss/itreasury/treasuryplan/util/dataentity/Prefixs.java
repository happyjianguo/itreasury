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
public class Prefixs {

	/**
	 * 
	 */
	public Prefixs() {

	}

	private Hashtable hash = new Hashtable();
	
	public void add(ClientType clientType){
		hash.put(String.valueOf(clientType.getClientTypeId()),clientType);
	}
	
	public Hashtable getAll(){
		return hash;
	}
	
	public ClientType getClientTypePrefix(long clientTypeId){
		ClientType clientType = (ClientType)hash.get(String.valueOf(clientTypeId));
		return clientType;
	}
	public static void main(String[] args) {
	}
}
