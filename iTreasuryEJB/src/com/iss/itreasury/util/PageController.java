package com.iss.itreasury.util;

/**
 * 
 * @author mzfu
 * 
 * Modify by leiyang date 2007/07/31
 *
 */
public class PageController {
	
	/**
	 * 得到分发用的URL
	 * @param pageControllerInfo
	 * @return
	 */
	public static String getDispatcherURL(PageControllerInfo pageControllerInfo) {
		String strReturnURL = "";
		String strModuleFolder = "";

		// 实现逻辑 TODO
		// 非 直接以文件夹起始（如：settment/a.jsp）的URL和“../”起始（如 ../a.jsp）的URL需要进行处理
		
		strReturnURL = pageControllerInfo.getUrl();		

		//SessionMng sessionMng = pageControllerInfo.getSessionMng();
		//String strModuleFolder = getModuleFolder(sessionMng.m_lModuleID);
		
			
		strModuleFolder = getModuleFolder(pageControllerInfo.getModelId());
		
		if(pageControllerInfo.getModelId() == Constant.ModuleType.EBANK)
		{
			if(strReturnURL.startsWith("/NASApp/iTreasury-ebank") || strReturnURL.indexOf("NASApp/iTreasury-ebank") > 0){
				strReturnURL= strReturnURL.replaceAll("/NASApp/iTreasury-ebank", "");
			}
		}	
		else {
			if(strReturnURL.startsWith("/NASApp") || strReturnURL.indexOf("NASApp") > 0){
					strReturnURL= strReturnURL.replaceAll("/NASApp", "");
			}
		}
		
		//modified by qhzhou 2007-08-02	
	    if(strReturnURL.startsWith("/") && !strReturnURL.startsWith("/"+strModuleFolder)){// && strReturnURL.indexOf("/iTreasury-") < 0) {
			strReturnURL= "/" + strModuleFolder + strReturnURL;
		}
        
		return strReturnURL;
	}

	/**
	 * 根据不同模块得到各自的文件夹
	 * @param lModuleID 模块ID
	 * @return
	 */
	public static String getModuleFolder(long lModuleID) {
		String strReturnFolder = "";

		switch ((int) lModuleID) {
		case (int) Constant.ModuleType.SETTLEMENT:
			strReturnFolder = "iTreasury-settlement";
			break;
		case (int) Constant.ModuleType.LOAN:
			strReturnFolder = "iTreasury-loan";
			break;
		case (int) Constant.ModuleType.FOREIGN:
			strReturnFolder = "iTreasury-loan";
			break;
		case (int) Constant.ModuleType.SYSTEM:
			strReturnFolder = "iTreasury-system";
			break;
		case (int) Constant.ModuleType.SECURITIES:
			strReturnFolder = "iTreasury-securities";
			break;
		case (int) Constant.ModuleType.CLIENTCENTER:
			strReturnFolder = "iTreasury-clientcenter";
			break;
		case (int) Constant.ModuleType.PLAN:
			strReturnFolder = "iTreasury-settlement";
			break;
		case (int) Constant.ModuleType.BILL:
			strReturnFolder = "iTreasury-bill";
			break;
		case (int) Constant.ModuleType.BUDGET:
			strReturnFolder = "iTreasury-budget";
			break;
		case (int) Constant.ModuleType.MANAGER:
			strReturnFolder = "iTreasury-settlement";
			break;
		case (int) Constant.ModuleType.CLIENTMANAGE:
			strReturnFolder = "iTreasury-clientmanage";
			break;
		case (int) Constant.ModuleType.EBANK:
			strReturnFolder = "";
			break;
        case (int) Constant.ModuleType.EVOUCHER:
			strReturnFolder = "iTreasury-evoucher";
			break;
        case (int) Constant.ModuleType.CRAFTBROTHER:
			strReturnFolder = "iTreasury-craftbrother";
			break;
		}
		
		return strReturnFolder;
	}
}
