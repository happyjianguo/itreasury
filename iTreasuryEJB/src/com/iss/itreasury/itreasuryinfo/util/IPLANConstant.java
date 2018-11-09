package com.iss.itreasury.itreasuryinfo.util;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * 资金计划部常量
 * 
 * @author gmqiu
 * 
 */
public class IPLANConstant extends Constant {

	// 一般状态常量
	public static final long INVALID = 0; // 无效
	public static final long VALID = 1; // 有效

	/**
	 * 利率状态常量
	 */
	public static class RateStatus {

		public static final long DELETEED = 0; // 已删除
		public static final long SAVED = 1; // 已保存
		public static final long CHECKED = 3; // 已复核

		public static final String getName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) DELETEED:
				s = "已删除";
				break;
			case (int) SAVED:
				s = "已保存";
				break;
			case (int) CHECKED:
				s = "已复核";
				break;
			}
			return s;
		}

		// 所有状态
		public static final long[] getAllStatus() {
			long[] lTemp = { DELETEED, SAVED, CHECKED };
			return lTemp;
		}

		/**
		 * 下拉列表
		 * 
		 * @param out
		 * @param strControlName
		 *            <select> 标签的name属性
		 * @param nType
		 *            下拉列表的类型(控制列数)
		 * @param lSelectValue
		 *            是否需要在列表中放置"全部"这一选项
		 * @param isNeedAll
		 *            是否需要全部选项
		 * @param isNeedBlank
		 *            是否需要空白列
		 * @param strProperty
		 *            <select> 标签的property属性
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllStatus();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = SAVED;
					lArrayID[1] = CHECKED;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 拆入信息状态
	 */
	public static class LendStatus {

		public static final long DELETEED = 0; // 已删除
		public static final long LEND_SAVE = 1; // 拆入保存
		public static final long LENDED = 3; // 已拆入
		public static final long REPAY_SAVE = 4; // 还款保存
		public static final long REPAYED = 5; // 已还款

		public static final String getName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) DELETEED:
				s = "已删除";
				break;
			case (int) LEND_SAVE:
				s = "拆入保存";
				break;
			case (int) LENDED:
				s = "已拆入";
				break;
			case (int) REPAY_SAVE:
				s = "还款保存";
				break;
			case (int) REPAYED:
				s = "已还款";
				break;
			}
			return s;
		}

		// 所有状态
		public static final long[] getAllStatus() {
			long[] lTemp = { DELETEED, LEND_SAVE, LENDED, REPAY_SAVE, REPAYED };
			return lTemp;
		}

		/**
		 * 下拉列表
		 * 
		 * @param out
		 * @param strControlName
		 *            <select> 标签的name属性l
		 * @param nType
		 *            下拉列表的类型(控制列数)
		 * @param lSelectValue
		 *            是否需要在列表中放置"全部"这一选项
		 * @param isNeedAll
		 *            是否需要全部选项
		 * @param isNeedBlank
		 *            是否需要空白列
		 * @param strProperty
		 *            <select> 标签的property属性
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllStatus();
					break;
				case 1:
					lArrayID = new long[4];
					lArrayID[0] = LEND_SAVE;
					lArrayID[1] = LENDED;
					lArrayID[2] = REPAY_SAVE;
					lArrayID[3] = REPAYED;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 资金项目日余额常量
	 * 
	 * @author gmqiu
	 * 
	 */
	public static class SubjectInfo {

		//资金项目常量
		public static final long S_ZYZJ = 1; // 自有资金
		public static final long S_XDZCZR = 2; // 信贷资产转让
		public static final long U_ZBJ = 3; // 准备金
		public static final long U_JJZQ = 4; // 基金、债券等

		//项目性质常量
		public static final long SOURCE = 1; // 资金来源
		public static final long USED = 2;   // 资金占用

		public static final String getName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) S_ZYZJ:
				s = "自有资金";
				break;
			case (int) S_XDZCZR:
				s = "信贷资产转让";
				break;
			case (int) U_ZBJ:
				s = "准备金";
				break;
			case (int) U_JJZQ:
				s = "基金、债券等";
				break;
			}
			return s;
		}
		
		public static final String getKindName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) SOURCE:
				s = "资金来源";
				break;
			case (int) USED:
				s = "资金占用";
				break;
			}
			return s;
		}
		
		
		//根据项目ID获取项目性质
		public static final long getSubjectKind(long subjectId){
			long l = -1;
			switch ((int) subjectId) {
			case (int) S_ZYZJ:
				l = SOURCE;
				break;
			case (int) S_XDZCZR:
				l = SOURCE;
				break;
			case (int) U_ZBJ:
				l = USED;
				break;
			case (int) U_JJZQ:
				l = SOURCE;
				break;
			}
			return l;
		}
		
		// 所有资金项目常量
		public static final long[] getAllStatus() {
			long[] lTemp = { S_ZYZJ, S_XDZCZR, U_ZBJ, U_JJZQ };
			return lTemp;
		}
		
		// 所有资金项目性质
		public static final long[] getAllKind() {
			long[] lTemp = { SOURCE, USED };
			return lTemp;
		}
		
		//资金项目下拉框
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllStatus();
					break;
				case 1:
					lArrayID = new long[4];
					lArrayID[0] = S_ZYZJ;
					lArrayID[1] = S_XDZCZR;
					lArrayID[2] = U_ZBJ;
					lArrayID[3] = U_JJZQ;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
		//资金项目性质下拉框
		public static final void showKindList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllKind();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = SOURCE;
					lArrayID[1] = USED;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getKindName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
	}
	
	//报表常量
	public static class Report {
		
		public static final long YUAN = 1; // 元
		public static final long WANYUAN = 10000; // 万元
		public static final long YIYUAN = 100000000; // 亿元
		
		public static final String getName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) YUAN:
				s = "元";
				break;
			case (int) WANYUAN:
				s = "万元";
				break;
			case (int) YIYUAN:
				s = "亿元";
				break;
			}
			return s;
		}
		
		// 所有常量
		public static final long[] getAllStatus() {
			long[] lTemp = { YUAN, WANYUAN, YIYUAN };
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllStatus();
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = YUAN;
					lArrayID[1] = WANYUAN;
					lArrayID[2] = YIYUAN;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
		public static void showCommonList(JspWriter out, String strControlName,
				long[] lArrayID, String[] strArrayName, long lSelectValue,
				boolean isNeedAll, String strProperty, boolean isNeedBlank) {
			try {
				SelectSorter selecSorter = new SelectSorter(lArrayID, strArrayName);
				selecSorter.sort();
				lArrayID = selecSorter.getLArrayID();
				strArrayName = selecSorter.getStrArrayName();

				out.println("<select name=\"" + strControlName + "\" "
						+ strProperty + " style='width: 80px;' >");
				for (int i = 0; i < lArrayID.length; i++) {
					Log.print("lArrayID[i] = " + lArrayID[i]);
					Log.print("lSelectValue = " + lSelectValue);
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
				out.println("</select>");
			} catch (Exception ex) {
				Log.print("显示下拉列表出现异常：" + ex.toString());
			}
		}
		
	}
	
	public static class Bank {
		
		public static final long ZGYH = 1;//中国银行
		public static final long ZGGSYH = 2;//中国工商银行
		public static final long ZGJSYH = 3;//中国建设银行
		public static final long ZGNYYH = 4;//中国农业银行
		public static final long ZGMSYH = 5;//中国民生银行
		public static final long ZGZSYH = 6;//中国招商银行
		public static final long ZGJTYH = 7;//中国交通银行
		public static final long SZFZYH = 8;//深圳发展银行
		public static final long ZXSYYH = 9;//中信实业银行
		public static final long ZGGDYH = 10;//中国光大银行
		public static final long SHPDFZYH = 11;//上海浦东发展银行
		public static final long XYYH = 12;//兴业银行
		public static final long GDFZYH = 13;//广东发展银行
		public static final long BJYH = 14;//北京银行
		public static final long HFYH = 15;//汇丰银行
		public static final long HXYH = 16;//华夏银行
		public static final long HLYH = 17;//荷兰银行
		public static final long ZDYH = 18;//渣打银行
		public static final long HQYH = 19;//花旗银行
		public static final long ZYXG = 20;//中银香港
		public static final long JSYH = 21;//江苏银行
		public static final long YZCXYH = 22;//邮政储蓄银行
		public static final long HENGFYH = 23;//恒丰银行
		public static final long HKYH = 24;//汉口银行
		public static final long YCSSY = 25;//宜昌市商业银行
		public static final long BJNSYH = 26;//北京农商银行
		public static final long QT = 27;//其他
		
		public static final String getBankName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) ZGYH:
				s = "中国银行";
				break;
			case (int) ZGGSYH:
				s = "中国工商银行";
				break;
			case (int) ZGJSYH:
				s = "中国建设银行";
				break;
			case (int) ZGNYYH:
				s = "中国农业银行";
				break;
			case (int) ZGMSYH:
				s = "中国民生银行";
				break;
			case (int) ZGZSYH:
				s = "中国招商银行";
				break;
			case (int) ZGJTYH:
				s = "中国交通银行";
				break;
			case (int) SZFZYH:
				s = "深圳发展银行";
				break;
			case (int) ZXSYYH:
				s = "中信实业银行";
				break;
			case (int) ZGGDYH:
				s = "中国光大银行";
				break;
			case (int) SHPDFZYH:
				s = "上海浦东发展银行";
				break;
			case (int) XYYH:
				s = "兴业银行";
				break;
			case (int) GDFZYH:
				s = "广东发展银行";
				break;
			case (int) BJYH:
				s = "北京银行";
				break;
			case (int) HFYH:
				s = "汇丰银行";
				break;
			case (int) HXYH:
				s = "华夏银行";
				break;
			case (int) HLYH:
				s = "荷兰银行";
				break;
			case (int) ZDYH:
				s = "渣打银行";
				break;
			case (int) HQYH:
				s = "花旗银行";
				break;
			case (int) ZYXG:
				s = "中银香港";
				break;
			case (int) JSYH:
				s = "江苏银行";
				break;
			case (int) YZCXYH:
				s = "邮政储蓄银行";
				break;
			case (int) HENGFYH:
				s = "恒丰银行";
				break;
			case (int) HKYH:
				s = "汉口银行";
				break;
			case (int) YCSSY:
				s = "宜昌市商业银行";
				break;
			case (int) BJNSYH:
				s = "北京农商银行";
				break;
			case (int) QT:
				s = "其他";
				break;
			}
			return s;
		}
		
		public static final String getSimpleBankName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) ZGYH:
				s = "中国银行,中行,中行";
				break;
			case (int) ZGGSYH:
				s = "工商银行,工商,工行";
				break;
			case (int) ZGJSYH:
				s = "建设银行,建设,建行";
				break;
			case (int) ZGNYYH:
				s = "农业银行,农业,农行";
				break;
			case (int) ZGMSYH:
				s = "民生银行,民生,民行";
				break;
			case (int) ZGZSYH:
				s = "招商银行,招商,招行";
				break;
			case (int) ZGJTYH:
				s = "交通银行,交通,交行";
				break;
			case (int) SZFZYH:
				s = "深圳发展,深发展,深圳银行";
				break;
			case (int) ZXSYYH:
				s = "中信实业,中实业,中信银行";
				break;
			case (int) ZGGDYH:
				s = "中国光大,光大,光大银行";
				break;
			case (int) SHPDFZYH:
				s = "浦东发展,上海浦发,浦发";
				break;
			case (int) XYYH:
				s = "兴业银行,兴业,兴行";
				break;
			case (int) GDFZYH:
				s = "广东发展银行,广东发展,广发";
				break;
			case (int) BJYH:
				s = "北京银行,北京,京行";
				break;
			case (int) HFYH:
				s = "汇丰银行,汇丰,汇丰";
				break;
			case (int) HXYH:
				s = "华夏银行,华夏,华夏";
				break;
			case (int) HLYH:
				s = "荷兰银行,荷兰,荷兰";
				break;
			case (int) ZDYH:
				s = "渣打银行,渣打,渣打";
				break;
			case (int) HQYH:
				s = "花旗银行,花旗,花旗";
				break;
			case (int) ZYXG:
				s = "中银香港,中银,香港";
				break;
			case (int) JSYH:
				s = "江苏银行,江苏,江行";
				break;
			case (int) YZCXYH:
				s = "邮政储蓄银行,邮政,邮储";
				break;
			case (int) HENGFYH:
				s = "恒丰银行,恒丰,恒丰";
				break;
			case (int) HKYH:
				s = "汉口银行,汉口,汉口";
				break;
			case (int) YCSSY:
				s = "宜昌市商业银行,宜昌商业,宜昌商行";
				break;
			case (int) BJNSYH:
				s = "北京农商银行,北京农商,北农商";
				break;
			case (int) QT:
				s = " , , ";
				break;
			}
			return s;
		}
		
		// 所有常量
		public static final long[] getAllStatus() {
			long[] lTemp = { ZGYH, ZGGSYH, ZGJSYH, ZGNYYH, ZGMSYH, ZGZSYH, ZGJTYH, SZFZYH, ZXSYYH, ZGGDYH, SHPDFZYH, XYYH, GDFZYH, BJYH, HFYH, HXYH, HLYH, ZDYH, HQYH, ZYXG, JSYH, YZCXYH, HENGFYH, HKYH, YCSSY, BJNSYH, QT };
			return lTemp;
		}
		
		//下拉列表
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllStatus();
					break;
				case 1:
					lArrayID = new long[27];
					lArrayID[0] = ZGYH;
					lArrayID[1] = ZGGSYH;
					lArrayID[2] = ZGJSYH;
					lArrayID[3] = ZGNYYH;
					lArrayID[4] = ZGMSYH;
					lArrayID[5] = ZGZSYH;
					lArrayID[6] = ZGJTYH;
					lArrayID[7] = SZFZYH;
					lArrayID[8] = ZXSYYH;
					lArrayID[9] = ZGGDYH;
					lArrayID[10] = SHPDFZYH;
					lArrayID[11] = XYYH;
					lArrayID[12] = GDFZYH;
					lArrayID[13] = BJYH;
					lArrayID[14] = HFYH;
					lArrayID[15] = HXYH;
					lArrayID[16] = HLYH;
					lArrayID[17] = ZDYH;
					lArrayID[18] = HQYH;
					lArrayID[19] = ZYXG;
					lArrayID[20] = JSYH;
					lArrayID[21] = YZCXYH;
					lArrayID[22] = HENGFYH;
					lArrayID[23] = HKYH;
					lArrayID[24] = YCSSY;
					lArrayID[25] = BJNSYH;
					lArrayID[26] = QT;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getBankName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
	} 
	
	public static class FixedDiposit {
		
		//期限形式
		public static final long MONTH = 1; // 月
		public static final long DAY = 2;   // 天
		public static final long YEAR = 3;  // 年
		
		public static final String getPeriodName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) MONTH:
				s = "月";
				break;
			case (int) DAY:
				s = "天";
				break;
			case (int) YEAR:
				s = "年";
				break;
			}
			return s;
		}
		
		public static final String getSimplePeriodName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) MONTH:
				s = "个月";
				break;
			case (int) DAY:
				s = "天";
				break;
			case (int) YEAR:
				s = "年";
				break;
			}
			return s;
		}
		
		// 所有常量
		public static final long[] getAllPeriods() {
			long[] lTemp = { MONTH, DAY, YEAR };
			return lTemp;
		}
		
		//期限形式下拉列表
		public static final void showPeriodList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllPeriods();
					break;
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = MONTH;
					lArrayID[1] = DAY;
					lArrayID[2] = YEAR;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getPeriodName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
		//存款方式
		public static final long NOTICE = 1; // 通知存款
		public static final long FIXED = 2; // 定期存款
		
		public static final String getDepositName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) NOTICE:
				s = "通知存款";
				break;
			case (int) FIXED:
				s = "定期存款";
				break;
			}
			return s;
		}
		
		// 所有常量
		public static final long[] getAllDeposits() {
			long[] lTemp = { NOTICE, FIXED };
			return lTemp;
		}
		
		//存款方式下拉列表
		public static final void showDeposiList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllDeposits();
					break;
				case 1:
					lArrayID = new long[2];
					lArrayID[0] = NOTICE;
					lArrayID[1] = FIXED;
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getDepositName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				Log.print(ex.toString());
			}
		}
		
		//存单状态
		public static final long INVALID = 0;     // 无效
		public static final long OUTSTANDING = 1; // 未结清
		public static final long STANDED = 2;     // 已结清
		
		public static final String getDepositStatusName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) INVALID:
				s = "无效";
				break;
			case (int) OUTSTANDING:
				s = "未结清";
				break;
			case (int) STANDED:
				s = "已结清";
				break;
			}
			return s;
		}
		
		public static void showCommonList(JspWriter out, String strControlName,
				long[] lArrayID, String[] strArrayName, long lSelectValue,
				boolean isNeedAll, String strProperty, boolean isNeedBlank) {
			try {
				out.println("<select name=\"" + strControlName + "\" "
						+ strProperty + " style='width: 80px;' >");
				for (int i = 0; i < lArrayID.length; i++) {
					Log.print("lArrayID[i] = " + lArrayID[i]);
					Log.print("lSelectValue = " + lSelectValue);
					if (lArrayID[i] == lSelectValue) {
						out.println("<option value='" + lArrayID[i] + "' selected>"
								+ strArrayName[i] + "</option>");
					} else {
						out.println("<option value='" + lArrayID[i] + "'>"
								+ strArrayName[i] + "</option>");
					}
				}
				out.println("</select>");
			} catch (Exception ex) {
				Log.print("显示下拉列表出现异常：" + ex.toString());
			}
		}
		
	}
	
	/**
	 * 控制操作常量标识
	 * 
	 * @author gmqiu
	 * 
	 */
	public static class Actions {

		// 录入复核Actions控制
		public static final long ENTRY_SEARCH = 1; // 录入_查询
		public static final long ENTRY_SAVE = 2; // 录入_保存
		public static final long ENTRY_UPDATE = 3; // 录入_修改
		public static final long ENTRY_DELETE = 4; // 录入_删除
		public static final long ENTRY_DETAIL = 5; // 录入_明细
		public static final long ENTRY_ADD_TODO = 6; // 录入_新增_过度
		public static final long ENTRY_RETURN = 7; // 录入_返回
		public static final long CHECK_SEARCH = 8; // 复核_查询
		public static final long CHECK_CHECK = 9; // 复核_复核
		public static final long CHECK_UNCHECK = 10; // 复核_取消复核
		public static final long CHECK_DETAIL = 11; // 复核_明细
		public static final long CHECK_RETURN = 12; // 复核_返回
		public static final long ENTRY_LEND = 13; // 录入_还款
		public static final long CHECK_LEND = 14; // 复核_还款
		public static final long CHECK_REVIEW_SEARCH = 15; // 复核_待复核_查询
		public static final long ENTRY_BETCH_UPDATE = 16;//批量录入修改
		public static final long SEARCH = 17;//查找
		public static final long RETURN = 18;//返回
		public static final long SEARCH_SINGLE = 19;//查找单个开户行利率
		public static final long BEFORE_DAY = 20;//前一天
		public static final long AFTER_DAY = 21;//后一天
		public static final long PRINT = 22; //打印
		public static final long PRINT_SINGLE = 23;//打印单个
		public static final long SEARCH_CONDITION = 24;//查询条件

		public static final String getName(long code) {
			String s = "";
			switch ((int) code) {
			case (int) ENTRY_SEARCH:
				s = "录入_查询";
				break;
			case (int) ENTRY_SAVE:
				s = "录入_保存";
				break;
			case (int) ENTRY_UPDATE:
				s = "录入_修改";
				break;
			case (int) ENTRY_DELETE:
				s = "录入_删除";
				break;
			case (int) ENTRY_DETAIL:
				s = "录入_明细";
				break;
			case (int) ENTRY_ADD_TODO:
				s = "录入_新增_过度";
				break;
			case (int) ENTRY_RETURN:
				s = "录入_返回";
				break;
			case (int) CHECK_SEARCH:
				s = "复核_查询";
				break;
			case (int) CHECK_CHECK:
				s = "复核_复核";
				break;
			case (int) CHECK_UNCHECK:
				s = "复核_取消复核";
				break;
			case (int) CHECK_DETAIL:
				s = "复核_明细";
				break;
			case (int) CHECK_RETURN:
				s = "复核_返回";
				break;
			case (int) ENTRY_LEND:
				s = "录入_还款";
				break;
			case (int) CHECK_LEND:
				s = "复核_还款";
				break;
			case (int) CHECK_REVIEW_SEARCH:
				s = "复核_待复核_查询";
				break;
			case (int) ENTRY_BETCH_UPDATE:
				s = "批量录入修改";
				break;
			case (int) SEARCH:
				s = "查找";
				break;
			case (int) RETURN:
				s = "返回";
				break;
			case (int) SEARCH_SINGLE:
				s = "查找单个开户行利率";
				break;
			case (int) BEFORE_DAY:
				s = "前一天";
				break;
			case (int) AFTER_DAY:
				s = "后一天";
				break;
			case (int) PRINT:
				s = "打印";
				break;
			case (int) PRINT_SINGLE:
				s = "打印单个";
				break;
			case (int) SEARCH_CONDITION:
				s = "查询条件";
				break;
			}
			return s;
		}

	}

	/**
	 * 下拉列表模版
	 * 
	 * @param out
	 * @param strControlName
	 * @param lArrayID
	 * @param strArrayName
	 * @param lSelectValue
	 * @param isNeedAll
	 * @param strProperty
	 * @param isNeedBlank
	 */
	public static void showCommonList(JspWriter out, String strControlName,
			long[] lArrayID, String[] strArrayName, long lSelectValue,
			boolean isNeedAll, String strProperty, boolean isNeedBlank) {
		try {
//			SelectSorter selecSorter = new SelectSorter(lArrayID, strArrayName);
//			selecSorter.sort();
//			lArrayID = selecSorter.getLArrayID();
//			strArrayName = selecSorter.getStrArrayName();

			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + " style='width: 128px;' >");
			if (isNeedBlank == true) {
				if (lSelectValue == -1) {
					out.println("<option value='-1' selected>&nbsp;</option>");
				} else {
					out.println("<option value='-1'>&nbsp;</option>");
				}
			}
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value='' selected>全部</option>");
				} else {
					out.println("<option value=''>全部</option>");
				}
			}
			for (int i = 0; i < lArrayID.length; i++) {
				Log.print("lArrayID[i] = " + lArrayID[i]);
				Log.print("lSelectValue = " + lSelectValue);
				if (lArrayID[i] == lSelectValue) {
					out.println("<option value='" + lArrayID[i] + "' selected>"
							+ strArrayName[i] + "</option>");
				} else {
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			Log.print("显示下拉列表出现异常：" + ex.toString());
		}
	}

}
