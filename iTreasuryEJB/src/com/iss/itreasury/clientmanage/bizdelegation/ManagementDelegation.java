/* Generated by Together */

package com.iss.itreasury.clientmanage.bizdelegation;

import com.iss.itreasury.clientmanage.management.bizlogic.ManagementCmd;
import com.iss.itreasury.clientmanage.management.dataentity.ManagementInfo;

public class ManagementDelegation {
	
	ManagementCmd managementcmd = new ManagementCmd();
	public ManagementInfo findManagement(long ClientID)
	{
		ManagementInfo Info = new ManagementInfo();
		try
		{
			Info = managementcmd.findManagement(ClientID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (Info);
	}
	
//	public static void main(String[] args)
//	{
//		ManagementDelegation id = new ManagementDelegation();
//		String Code = "02-0003";
//		ManagementInfo Info = new ManagementInfo();
//		try
//		{
//			Info = id.findManagement(Code);
//			System.out.println(Info.getCount());
////			System.out.println(Info.getSum());		
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}