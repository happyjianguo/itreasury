package com.iss.itreasury.fcinterface.util;

import com.iss.itreasury.ebank.util.OBConstant.BankInstructionStatus;

/**
 * 常量类
 * @author xiangzhou
 * 2011-3-24
 */
public class EPConstant {
	
	/**
	 * 指令状态
	 * @author issuser
	 *
	 */
	public static class EPInstructionStatus{
		
		public static final long FAIL = 1; 	// 失败；接收或处理失败
		
		public static final long SENDSUCCESS = 3; 	// 待处理；发送成功，等待财务公司处理
		
		public static final long SETTHANDLE = 4; 	// 处理中；财务公司已接收，正在处理中
		
		public static final long SETTSUCCESS = 5; 	// 已完成；财务公司处理指令成功
		
		public static final long SETTFAIL = 6; 	// 已拒绝；财务公司拒绝指令
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) FAIL:
				strReturn = "失败；接收或处理失败";
				break;	
			case (int) SENDSUCCESS:
				strReturn = "待处理；发送成功，等待财务公司处理";
				break;	
			case (int) SETTHANDLE:
				strReturn = "处理中；财务公司已接收，正在处理中";
				break;	
			case (int) SETTSUCCESS:
				strReturn = "已完成；财务公司处理指令成功";
				break;	
			case (int) SETTFAIL:
				strReturn = "已拒绝；财务公司拒绝指令";
				break;	
			default:
				strReturn = BankInstructionStatus.getName(lCode);
			break;
			}
			return strReturn;
		
		}
	
	}
	
	/**
	 * 外部系统特殊客户
	 * @author xiangzhou
	 *
	 */
	public static class MachineUser{
		
		public static final long OBInputUser = 999999999;
		public static final long OBCheckUser = 999999998;
		public static final long SETTInputUser = 0;
		public static final long SETTCheckUser = -1;
		
		 public static final String getName(long lCode)
	        {
	            String strReturn = ""; //初始化返回值
	            switch ((int) lCode)
	            {
	                case (int) OBInputUser:
	                    strReturn = "网银机制";
	                    break;
	                case (int) OBCheckUser:
	                    strReturn = "网银机核";
	                    break;
	                case (int) SETTInputUser:
	                	strReturn = "结算机制";
	                break;
	                case (int) SETTCheckUser:
	                	strReturn = "结算机核";
	                break;
	            }
	            return strReturn;
	        }
		
	}
	
}
