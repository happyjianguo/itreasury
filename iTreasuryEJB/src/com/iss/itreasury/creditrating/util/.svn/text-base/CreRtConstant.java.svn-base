package com.iss.itreasury.creditrating.util;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.Constant;

/**
 * 包含信用评级子系统所有的常量
 * 
 * @author zcwang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreRtConstant extends com.iss.itreasury.util.Constant {
	
	/**
	 * 评级状态
	 * @author Administrator
	 *
	 */
	public static class CreRtStatus {
	
		public static final long SAVE = 1; // 已保存

		public static final long APPROVALING = 2; //审批中	
		
		public static final long APPROVALED = 3; // 已审批
		
		public static final long CANCEL = 4; // 已作废

		public static final long DELETE = 0; // 已删除	

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "已保存";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			case (int) APPROVALED:
				strReturn = "已审批";
				break;
			case (int) CANCEL:
				strReturn = "已作废";
				break;
			case (int) DELETE:
				strReturn = "已删除";
				break;		
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, APPROVALING, APPROVALED, CANCEL, DELETE };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.creditrating.util.CreRtConstant$CreRtStatus",
					officeID, currencyID);
		}
	}
	
	/**
	 * 评级类型
	 * @author Administrator
	 *
	 */
	public static class CreRtType{
		
		public static final long INCRERT = 1; // 内部评级

		public static final long OUTCRERT = 2; //外部评级	
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INCRERT:
				strReturn = "内部评级";
				break;
			case (int) OUTCRERT:
				strReturn = "外部评级	";
				break;
			}
			return strReturn;
		}
			public static final long[] getAllCode() {
				long[] lTemp = { INCRERT, OUTCRERT };

				return lTemp;
			}

			public static final long[] getAllCode(long officeID, long currencyID) {
				return Constant.getAllCode(
						"com.iss.itreasury.creditrating.util.CreRtConstant$CreRtType",
						officeID, currencyID);
			}
	}
	

	/**
	 * @author Administrator
	 * 指标方案取值方式
	 */
	public static class DataType{
		
		public static final long HANDIN = 1; // 手工输入

		public static final long HANDCHOOSE = 2; //手工选择	
		
		//public static final long PERSONALCHOOSE = 3; //个性化取数
		//modify by bingliu 20120817 目前未实现个性化取数的逻辑，先屏蔽
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) HANDIN:
				strReturn = "手工输入";
				break;
			case (int) HANDCHOOSE:
				strReturn = "手工选择	";
				break;
//			case (int) PERSONALCHOOSE:
//				strReturn = "个性化取数";
//				break;
			}
			return strReturn;
		}
		
		public static final long[] getAllCode() {
//			long[] lTemp = { HANDIN, HANDCHOOSE,PERSONALCHOOSE };
			long[] lTemp = { HANDIN, HANDCHOOSE };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.creditrating.util.CreRtConstant$DataType",
					officeID, currencyID);
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *            是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

		//区分办事和币种的下拉框
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode(lOfficeID, lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
//	 工作类型 added by mzh_fu 2007/06/22
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//待处理业务
		
		public static final long CURRENTWORK = 2; //待办任务

		public static final long HISTORYWORK = 3;//已办任务

		public static final long FINISHEDWORK = 4;//办结任务
		
		public static final long REFUSEWORK = 5;//拒绝业务
		
		public static final long CANCELAPPROVAL = 6;//取消审批	
		
		//Added by leiyang, 2007/09/28 得到我的工作的链接地址
		public final static String getWorkUrl(String strContext, long workType){
			String workUrl = "";
			switch((int)workType){
				case (int)WAITDEALWITHWORK:
					workUrl = strContext + "/mywork/waitDealWithWorkList-main.jsp";
					break;
				case (int)CURRENTWORK:
					workUrl = strContext + "/mywork/currentWorkList-main.jsp";
					break;
				case (int)HISTORYWORK:
					workUrl = strContext + "/securities/mywork/historyWorkList-main.jsp";
					break;
				case (int)FINISHEDWORK:
					workUrl = strContext + "";
					break;
				case (int)REFUSEWORK:
					workUrl = strContext + "/mywork/refuseWorkList-main.jsp";
					break;
				case (int)CANCELAPPROVAL:
					workUrl = strContext + "/mywork/cancelApprovalList-main.jsp";
					break;					
			}
			return workUrl;
		}
		
		public final static String getWorkUrl(long workType){
			String strContext="/NASApp/iTreasury-creditrating";
			return getWorkUrl(strContext, workType);
		}
	}

}
