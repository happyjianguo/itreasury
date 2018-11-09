/* Generated by Together */

package com.iss.itreasury.clientmanage.bizdelegation;

import com.iss.itreasury.clientmanage.enterprisememo.bizlogic.EnterpriseMemoCmd;
import com.iss.itreasury.clientmanage.enterprisememo.dataentity.EnterpriseMemoInfo;

public class EnterpriseMemoDelegation {
	
	EnterpriseMemoCmd enterprisememocmd = new EnterpriseMemoCmd();
	public EnterpriseMemoInfo findEnterpriseLaw(long ClientID)
	{
		EnterpriseMemoInfo Info = new EnterpriseMemoInfo();
		try
		{
			Info = enterprisememocmd.findEnterpriseMemo(ClientID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (Info);
	}
	
//	public static void main(String[] args)
//	{
//		EnterpriseMemoDelegation id = new EnterpriseMemoDelegation();
//		String Code = "02-0003";
//		EnterpriseMemoInfo Info = new EnterpriseMemoInfo();
//		try
//		{
//			Info = id.findEnterpriseLaw(Code);
//			System.out.println(Info.getCount());
////			System.out.println(Info.getSum());		
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}