/* Generated by Together */

package com.iss.itreasury.clientmanage.bizdelegation;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.clientmanage.client.bizlogic.CorporationCmd;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;

public class CorportionDelegation {
	CorporationCmd CM = new CorporationCmd();

	public void update(CorporationInfo corDataEntity, ClientInfo cliDataEntity) {
		try {
			CM.update(corDataEntity, cliDataEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection findByCondition(CorporationInfo dataEntity) {
		Collection c = null;
		try {
			c = CM.findByContidion(dataEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (c);
	}

	public Collection findById(long lId) throws Exception {
		Collection c = null;
		try {
			c = CM.findById(lId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (c);
	}

	public Collection findByClientId(long lClientId) throws Exception {
		Collection c = null;
		try {
			c = CM.findByClientId(lClientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (c);
	}

	/**
	 * 查信用等级
	 * 
	 * @return
	 * @throws Exception
	 */
	public CorporationInfo findAppraise(long ClientID) throws Exception {
		CorporationInfo Info = new CorporationInfo();

		try {
			Info = CM.findAppraise(ClientID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Info);
	}

	public static void main(String[] args) {
		long id = 2;
		CorporationInfo dataEntity = new CorporationInfo();
		dataEntity.setId(1);
		CorportionDelegation cd = new CorportionDelegation();
		// Collection c = null;
		try {
			dataEntity.setId(2);
			Collection c = cd.findByCondition(dataEntity);
			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					CorporationInfo Info = (CorporationInfo) it.next();
					System.out.println(Info.getLegalPersonCodeCert());
					System.out.println(Info.getClientid());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
