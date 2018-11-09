/*
 * Copyright (c) 1999-2001 AsiaEC.com. All Rights Reserved.
 *
 * 总体功能说明：用来存放项目中用到的常量
 *
 * 使用注意事项：
 * 1
 * 2

 * 3
 *
 * 作者：yfan
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.loan.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.credit.dao.CreditLimitDAO;
import com.iss.itreasury.loan.setting.bizlogic.LoanContractAssortSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanAssortSettingInfo;
import com.iss.itreasury.loan.bizdelegation.LoanAssortSettingDelegation;
import com.iss.itreasury.loan.bizdelegation.LoanTypeRelationDelegation;
import com.iss.itreasury.util.Constant;

public class LOANConstant extends com.iss.itreasury.util.Constant {
	// 代码类型
	//
	// 企业性质
	public static class ClientCorpNature {
		// 企业性质
		public static final long MARKET = 1; // 上市

		public static final long OUTOFMARKET = 2; // 非上市

		public static final long OTHER = 3; // 其他

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) MARKET:
				strReturn = "上市";
				break;
			case (int) OUTOFMARKET:
				strReturn = "非上市";
				break;
			case (int) OTHER:
				strReturn = "其他";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { MARKET, OUTOFMARKET, OTHER };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ClientCorpNature",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

//	承兑汇票
	public static class AcceptBillStatus {
		// 承兑汇票状态
		public static final long OPEN = 1;// 开立

		public static final long CLOSE = 2;// 已承兑

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "开立";
				break;
			case (int) CLOSE:
				strReturn = "已承兑";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}
	
	// 信贷证明
	public static class CreditProveStatus {
		// 信贷证明状态
		public static final long OPEN = 1;// 开立

		public static final long CLOSE = 2;// 作废

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "开立";
				break;
			case (int) CLOSE:
				strReturn = "作废";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}
	
	// 保函
	public static class LetterGuaranteeStatus {
		// 信贷证明状态
		public static final long OPEN = 1;// 执行中

		public static final long CLOSE = 2;// 失效

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) OPEN:
				strReturn = "执行中";
				break;
			case (int) CLOSE:
				strReturn = "失效";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = OPEN;
			lTemp[1] = CLOSE;
			return lTemp;
		}

	}

	//信用证
	public static class LetterCreditStatus{
		//信用证状态
		public static final long OPEN = 1;//开立
		
		public static final long DDTZ = 2;//到单通知
		
		public static final long YHZ = 3;//压汇中
		
		public static final long YHJS = 4;//压汇结束
		
		public static final long YSD = 5;//已赎单
		
		public static final String getName(long lCode){
			String strReturn = "";
			switch((int)lCode){
			case (int)OPEN:
				strReturn = "开立";
				break;
			case (int)DDTZ:
				strReturn = "到单通知";
				break;
			case (int)YHZ:
				strReturn = "压汇中";
				break;
			case (int)YHJS:
				strReturn = "压汇结束";
				break;
			case (int)YSD:
				strReturn = "已赎单";
				break;
			}
			return strReturn;
		}
		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = OPEN;
			lTemp[1] = DDTZ;
			lTemp[2] = YHZ;
			lTemp[3] = YHJS;
			lTemp[4] = YSD;
			return lTemp;
		}
		
	}


	// 货币类型
	public static class CurrencyType {
		public static final long CNY = 1; // 人民币

		public static final long USD = 2; // 美元

		public static final long EUR = 3; // 欧元

		public static final long JPY = 4; // 日元

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CNY:
				strReturn = "￥";
				break;
			case (int) USD:
				strReturn = "$";
				break;
			case (int) EUR:
				strReturn = "&euro;";
				break;
			case (int) JPY:
				strReturn = "&yen;";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = CNY;
			lTemp[1] = USD;
			lTemp[2] = EUR;
			lTemp[3] = JPY;
			return lTemp;
		}
	
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// 企业类型
	public static class ClientCorpIndustry {
		// 企业类型
		public static final long YXZRGS = 1; // 有限责任公司

		public static final long GYDZGS = 2; // 国有独资企业

		public static final long WSDZGS = 3; // 外商独资企业

		public static final long GFYXGSSS = 4; // 股份有限（上市）

		public static final long GFYXGSWSS = 5; // 股份有限（未上市）

		public static final long QT = 6; // 其他

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) YXZRGS:
				strReturn = "有限责任公司";
				break;
			case (int) GYDZGS:
				strReturn = "国有独资企业";
				break;
			case (int) WSDZGS:
				strReturn = "外商独资企业";
				break;
			case (int) GFYXGSSS:
				strReturn = "股份有限公司（上市）";
				break;
			case (int) GFYXGSWSS:
				strReturn = "股份有限公司（未上市）";
				break;
			case (int) QT:
				strReturn = "其他";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = YXZRGS;
			lTemp[1] = GYDZGS;
			lTemp[2] = WSDZGS;
			lTemp[3] = GFYXGSSS;
			lTemp[4] = GFYXGSWSS;
			lTemp[5] = QT;

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ClientCorpIndustry",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 票据状态
	public static class BillStatus {
		// 票据状态
		public static final long KC = 1; // 库存

		public static final long ZT = 2; // 在途

		public static final long YHG = 3; // 已回够

	}

	// 贷款类型
	public static class LoanType {
		// 贷款类型
		public static final long ZY = 1; // 自营贷款

		public static final long WT = 2; // 委托贷款

		public static final long TX = 3; // 贴现

		public static final long ZGXE = 4; // 最高限额

		public static final long YT = 5; // 银团贷款

		public static final long ZTX = 6; // 转贴现

		public static final long MFXD = 7; // 买方信贷

		public static final long DB = 8; // 担保

		public static final long RZZL = 10;// 融资租赁

		public static final long OTHER = 9; // 其他
		
		public static final long CREDIT = 11;//授信
		
		public static final long XDZR = 12; //信贷资产转让
		
		public static final long SHYCHDHP = 80;//商业承兑汇票

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "自营贷款";
				break;
			case (int) WT:
				strReturn = "委托贷款";
				break;
			case (int) TX:
				strReturn = "贴现";
				break;
			case (int) ZGXE:
				strReturn = "最高限额贷款";
				break;
			case (int) YT:
				strReturn = "银团贷款";
				break;
			//case (int) ZTX:
			//	strReturn = "转贴现";
			//	break;
			case (int) MFXD:
				strReturn = "买方信贷";
				break;
			case (int) DB:
				strReturn = "担保";
				break;
			case (int) RZZL:
				strReturn = "融资租赁";
				break;
			case (int) OTHER:
				strReturn = "其他";
				break;
			case (int) CREDIT:
				strReturn = "授信设置";
			    break;
			case (int) XDZR:
				strReturn = "信贷资产转让";
			    break;
			case (int) SHYCHDHP :
				strReturn = "商业承兑汇票";
				break;        
			}
			return strReturn;
		}

		// 原先已经有此方法
		public static final long[] getAllCode() {
			long[] loantype = { ZY, WT, TX, ZGXE, YT,MFXD, DB, RZZL,
					OTHER,CREDIT,XDZR };
			return loantype;
		}
		//贷款业务
		
		// 新增方法 by liuguang 增加贷款类型用ConfigTool进行设置（对利息费用结算处理应用） 
		public static final long[] getAllCode1(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$LoanType",officeID,currencyID);
        }
		// 中交加入，资金结算－查询－贷款查询－贷款类型中只显示内部资金占用和其他
		public static final long[] getAllCodeForZj()
		{
			long[] loantype = 
			{ ZY, OTHER };
			return loantype;
		}

		// 取贷款类型关系设置中选择的大类
		public static final long[] getAllCode(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			return delegation.getAllSetLoanTypeID(lOfficeID, lCurrencyID);
		}
		
		// 取贷款类型关系设置中选择的大类(注：去掉授信设置 by liaiyi 2013-01-15)
		public static final long[] getAllCode2(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			long[] getAllCode = delegation.getAllSetLoanTypeID(lOfficeID, lCurrencyID);
			
			int count = 0;
			long[] _getAllCode = null;
			
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=CREDIT){
					count++;
				}
			}
			_getAllCode = new long[count];
			int j = 0;
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=CREDIT){
					_getAllCode[j] = getAllCode[i];
					j++;
				}
			}
			
			return _getAllCode;
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID) {
			// long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// lArrayID = getAllCode(lOfficeID,lCurrencyID);
				// lArrayID = getAllSetLoanTypeID(lOfficeID,lCurrencyID);
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

	// 设置的贷款类型
	public static class SubLoanType {
		public static final String getName(long lCode) {
			return LOANNameRef.getSubLoanTypeNameByID(lCode);
		}

		public static final String getCode(long lCode) {
			return LOANNameRef.getSubLoanTypeCodeByID(lCode);
		}

		// 取贷款类型关系设置中选择的子类
		public static final long[] getAllCode(long lOfficeID, long lCurrencyID) {
			LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
			return delegation.getAllSetSubLoanTypeID(lOfficeID, lCurrencyID);
		}

		public static final void showList(JspWriter out, String strControlName,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long lOfficeID, long lCurrencyID,
				long[] lLoanTypeID, long[] lSubLoanTypeID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				// lLoanTypeID,long[] lSubLoanTypeID
				// lArrayID = getAllCode(lOfficeID,lCurrencyID);
				if (lLoanTypeID == null) {
					lArrayID = lSubLoanTypeID;
				} else {
					LoanTypeRelationDelegation delegation = new LoanTypeRelationDelegation();
					lArrayID = delegation.getAllSetSubLoanTypeIDByLoanTypeID(
							lOfficeID, lCurrencyID, lLoanTypeID);
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

		public static final void showListByType(JspWriter out,
				String strControlName, long type, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				lArrayID = getAllCode(lOfficeID, lCurrencyID);

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

	// 贷款状态
	public static class LoanStatus {
		// 贷款状态
		public static final long SAVE = 1; // 已保存

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝

		public static final long OTHER = 4; // 其他 TODO 值待定

		public static final long CANCEL = 5; // 已经取消
		
		public static final long APPROVALING = 20; //审批中		

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/06/11,贷款未提交审批前只有一个状态"已保存"
//				strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) OTHER:
				strReturn = "其它";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, CANCEL };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanStatus",
					officeID, currencyID);
		}
	}
    
	
	/* 融资租赁 保证金处理 中 保证金的处理方式
     * 1 为 到期/提前撤销
     * 2 为 回购
     * quanshao 添加 2010-5-31
     */
	public static class DecognizanceDeal{
		public static final long MATURITY = 1;  // 到期/提前撤销
		
		public static final long BUY_BAKE = 2;  // 回购
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) MATURITY:
				strReturn = "到期/提前撤销";
				break;
			case (int) BUY_BAKE:
				strReturn = "回购";
				break;
			}
			return strReturn;
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = { MATURITY, BUY_BAKE };
			return lTemp;
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName  控件名称
		 * @param lSelectValue
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				   lArrayID     = getAllCode();
				   strArrayName = new String[lArrayID.length];
				   for (int i = 0; i < lArrayID.length; i++) {
					  strArrayName[i] = getName(lArrayID[i]);
				   }
				   showCommonList(out, strControlName, lArrayID, strArrayName,lSelectValue, false, strProperty, false);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
	}
	
	
	// 汇票种类
	public static class DraftType {
		// 汇票种类
		public static final long BANK = 1; // 银行承兑汇票

		public static final long BIZ = 2; // 商业承兑汇票

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BANK:
				strReturn = "银行承兑汇票";
				break;
			case (int) BIZ:
				strReturn = "商业承兑汇票";
				break;
			default:
				strReturn = "";
				break;			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BANK, BIZ };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$DraftType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 展期状态
	public static class ExtendStatus {
		// 展期状态
		public static final long SAVE = 1; //已保存

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = 4; // 已拒绝

		public static final long USED = 5; // 已使用
		
		public static final long APPROVALING = 20; //审批中
		
		public static final long CANCEL = 0; //已取消
		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
//				strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[7];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = REFUSE;
			lTemp[4] = USED;
			lTemp[5] = APPROVALING;
			lTemp[6] =CANCEL;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ExtendStatus",
					officeID, currencyID);
		}
	}

	// 合同状态
	public static class ContractStatus 
	{
		// 合同状态
		public static final long SAVE = 1; // 已保存

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审批

		public static final long NOTACTIVE = 4; // 未执行

		public static final long ACTIVE = 5; // 执行中

		public static final long EXTEND = 6; // 已展期

		public static final long OVERDUE = 7; // 已逾期

		public static final long DELAYDEBT = 8; // 呆滞

		public static final long BADDEBT = 9; // 呆账

		public static final long FINISH = 10; // 已结束

		public static final long CANCEL = 11; // 已取消

		public static final long REFUSE = 12; // 已拒绝
		
		public static final long APPROVALING = 20; //审批中
		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
//				strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) NOTACTIVE:
				strReturn = "未执行";
				break;
			case (int) ACTIVE:
				strReturn = "执行中";
				break;
			case (int) EXTEND:
				strReturn = "已展期";
				break;
			case (int) OVERDUE:
				strReturn = "已逾期";
				break;
			case (int) DELAYDEBT:
				strReturn = "呆滞";
				break;
			case (int) BADDEBT:
				strReturn = "呆账";
				break;
			case (int) FINISH:
				strReturn = "已结束";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;

			lTemp = new long[12];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = EXTEND;
			lTemp[6] = OVERDUE;
			lTemp[7] = DELAYDEBT;
			lTemp[8] = BADDEBT;
			lTemp[9] = FINISH;
			lTemp[10] = CANCEL;
			lTemp[11] = REFUSE;

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ContractStatus",
					officeID, currencyID);
		}

		// (注：去掉已提交、已结束、已取消  by liaiyi 2013-01-15)
		public static final long[] getAllCode3(long officeID, long currencyID) {
			long[] getAllCode = getAllCode(officeID, currencyID);
			
			int count = 0;
			long[] _getAllCode = null;
			
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=SUBMIT && tmp!=FINISH && tmp!=CANCEL){
				
					count++;
				}
			}
			_getAllCode = new long[count];
			int j = 0;
			for(int i=0;i<getAllCode.length;i++){
				long tmp = getAllCode[i];
				if(tmp!=SUBMIT && tmp!=FINISH && tmp!=CANCEL){
					_getAllCode[j] = getAllCode[i];
					j++;
				}
			}
			
			return _getAllCode;
		}

		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型（0，显示全部；1,结算使用（不显示撰写，已提交，已审核，已取消，已拒绝）2,信贷资产转让使用）
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
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[7];
					lArrayID[0] = NOTACTIVE;
					lArrayID[1] = ACTIVE;
					lArrayID[2] = EXTEND;
					lArrayID[3] = OVERDUE;
					lArrayID[4] = DELAYDEBT;
					lArrayID[5] = BADDEBT;
					lArrayID[6] = FINISH;
					break;
				case 2:
					lArrayID = new long[2];
					lArrayID[0] = ACTIVE;
					lArrayID[1] = EXTEND;
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
	
	
//	 贴现汇票种类 银行授信状
	public static class TsDiscountType 
	{
		// 贴现汇票种类 
		public static final long BUSINESSPO = 2; // 商业汇票 

		public static final long BANKPO = 1; // 银行汇票

		
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BUSINESSPO:
				//modified by mzh_fu 2007/07/05
//				strReturn = "撰写";
				strReturn = "商业承兑汇票";
				break;
			case (int) BANKPO:
				strReturn = "银行承兑汇票";
				break;
			}
			return strReturn;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$TsDiscountType",
					officeID, currencyID);
		}

	}

	/*public static class CreditType 
	{
		// 授信种类 
		public static final long ZH = 1; // 综合授信
		public static final long ZY = 2; // 自营贷款 
		public static final long BUSPO= 3; // 商业承兑汇票 
			
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ZH:
				//modified by mzh_fu 2007/07/05
//				strReturn = "撰写";
				strReturn = "综合授信";
				break;
			case (int) ZY:
				strReturn = "自营贷款";
				break;
			case (int) BUSPO:
				strReturn = "商业承兑汇票";
				break;
			}
			return strReturn;
		}
		
		public static final long getRefID(String lName)
		{
			long longReturn =-1; // 初始化返回值
			if(lName.equals("综合授信"))
			{
				longReturn=1;
			}
			else if (lName.equals("自营贷款"))
			{
				longReturn=2;
			}
			else 
			{
				longReturn=3;
			}
			return longReturn;
		}
		
		
		
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditType",
					officeID, currencyID);
		}	
	}*/
	
//	 授信品种 
	public static class changeType 
	{
		// 授信种类 
		public static final long XINZENG = 3 ; //提交
		public static final long JIA = 1; // 加法
		public static final long JIAN= 2; // 减法
		public static final long BIANGENG = 4; //变更

		public static final String getOperator(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) JIA:
				strReturn = "+";
				break;
			case (int) JIAN:
				strReturn = "-";
				break;

			}
			return strReturn;
		}
		
		public static final String getQuery(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) XINZENG:
				strReturn = "授信新增";
				break;
			case (int) BIANGENG:
				strReturn = "授信变更";
				break;

			}
			return strReturn;
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
				case (int) JIA:
					strReturn = "授信变更";
					break;
				case (int) JIAN:
					strReturn = "授信变更";
					break;
				case (int) XINZENG:
					strReturn = "授信新增";
					break;
			}
			return strReturn;
		}
		
		public static final long getRefID(String lName)
		{
			long longReturn =-1; // 初始化返回值
			if(lName.equals("+"))
			{
				longReturn=1;
			}
			 if (lName.equals("-"))
			{
				longReturn=2;
			}
			return longReturn;
		}
		
		
		
		
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[3];
			lTemp[0] = JIA;
			lTemp[1] = JIAN;
			lTemp[2] = XINZENG;
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 * @param lArrayID 自定义下拉框现实的内容
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[3];
					lArrayID[0] = JIA;
					lArrayID[1] = JIAN;
					lArrayID[2] = XINZENG;
					break;
				}			
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 * @param lArrayID 自定义下拉框现实的内容
		 */
		public static final void showOperatorList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[2];
					lArrayID[0] = JIA;
					lArrayID[1] = JIAN;
					break;
				}			
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getOperator(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	
	
	/**
	 * 显示下拉列表
	 * 
	 * @param out
	 * @param strControlName     控件名称
	 * @param nType，控件类型（0，显示全部；）
	 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
	 * @param isNeedAll，是否需要”全部项“
	 * @param isNeedBlank 是否需要空白行
	 * @param strProperty 设置下拉框的其它属性（可为空字符串）
	 * @param lArrayID 自定义下拉框现实的内容
	 */
	public static final void showQueryList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
			boolean isNeedBlank, String strProperty)
	{
		long[] lArrayID = null;
		String[] strArrayName = null;
		
		try
		{
			switch (nType) {
			case 0:
				lArrayID = new long[2];
				lArrayID[0] = XINZENG;
				lArrayID[1] = BIANGENG;
				break;
			}			
			strArrayName = new String[lArrayID.length];
			
			for (int i = 0; i < lArrayID.length; i++)
			{
				strArrayName[i] = getQuery(lArrayID[i]);
			}
			
			showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
}
	
	
	
	
	
	
	

	// 银行授信状态
	public static class BankCreditStatus
	{
		public static final long ACTIVE = 1;	//执行中
		public static final long FINISHED = 2;	//已结束
		public static final long CANCELED = 3;	//已取消
		
		// 通过代码匹配名称
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			
			switch ((int)lCode)
			{
				case (int)ACTIVE :
					strReturn = "执行中";
					break;
				case (int)FINISHED :
					strReturn = "已结束";
					break;
				case (int)CANCELED :
					strReturn = "已取消";
					break;
			}
			
			return strReturn;
		}
		
		// 得到所有的代码
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[3];
			lTemp[0] = ACTIVE;
			lTemp[1] = FINISHED;
			lTemp[2] = CANCELED;
			
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	//银行授信品种
	public static class BankCreditVariety 
	{
		public static final long MIXEDBANKCREDIT = 0;		//混用额度
		public static final long SHORTTERMLOAN = 1;			//短期贷款
		public static final long LONGTERMLOAN = 2;			//中长期贷款
		public static final long LETTEROFCREDIT = 3;		//信用证
		public static final long LETTEROFIGUARANTEE = 4;	//保函
		public static final long PROVEOFCREDIT = 5;			//信贷证明
		public static final long ACCEPTBILL = 6;			//承兑汇票
		
		//通过代码匹配名称
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			
			switch ((int)lCode)
			{
				case (int)MIXEDBANKCREDIT :
					strReturn = "混用额度";
					break;
				case (int)SHORTTERMLOAN :
					strReturn = "短期贷款";
					break;
				case (int)LONGTERMLOAN :
					strReturn = "中长期贷款";
					break;
				case (int)LETTEROFCREDIT :
					strReturn = "信用证";
					break;
				case (int)LETTEROFIGUARANTEE :
					strReturn = "保函";
					break;
				case (int)PROVEOFCREDIT :
					strReturn = "信贷证明";
					break;
				case (int)ACCEPTBILL :
					strReturn = "承兑汇票";
					break;
			}
			
			return strReturn;
		}
		
		// 得到所有的代码
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[7];
			lTemp[0] = MIXEDBANKCREDIT;
			lTemp[1] = SHORTTERMLOAN;
			lTemp[2] = LONGTERMLOAN;
			lTemp[3] = LETTEROFCREDIT;
			lTemp[4] = LETTEROFIGUARANTEE;
			lTemp[5] = PROVEOFCREDIT;
			lTemp[6] = ACCEPTBILL;
			
			return lTemp;
		}
		
		// 得到综合授信品种
		public static final long[] getBankCreditList() {
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = SHORTTERMLOAN;
			lTemp[1] = LONGTERMLOAN;
			lTemp[2] = LETTEROFCREDIT;
			lTemp[3] = LETTEROFIGUARANTEE;
			lTemp[4] = PROVEOFCREDIT;
			lTemp[5] = ACCEPTBILL;
			
			return lTemp;
		}
		
		//得到货币资金的授信品种
		public static final long[] getMonetaryFundCode() {
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = SHORTTERMLOAN;
			lTemp[1] = LONGTERMLOAN;
			
			return lTemp;
		}
		
		//得到中间业务的授信品种
		public static final long[] getMiddleOperCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[1] = LETTEROFCREDIT;
			lTemp[2] = LETTEROFIGUARANTEE;
			lTemp[3] = PROVEOFCREDIT;
			lTemp[4] = ACCEPTBILL;
			
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；1，显示综合授信品种；2，显示货币资金授信品种；3，显示中间业务授信品种）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1:
						lArrayID = getBankCreditList();
						break;
					case 2 :
						lArrayID = getMonetaryFundCode();
						break;
					case 3 :
						lArrayID = getMiddleOperCode();
						break;
				}
				
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	//	银行授信是否可循环使用
	public static class BankCreditExtReuse
	{
		public static final long YES = 0;	// 是
		public static final long NO  = 1;	// 否
		
		//通过代码匹配名称
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			
			switch ((int)lCode)
			{
				case (int)YES :
					strReturn = "是";
					break;
				case (int)NO :
					strReturn = "否";
					break;
			}
			
			return strReturn;
		}
		
		// 得到所有的代码
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = YES;
			lTemp[1] = NO;
			
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// 银行授信期限
	public static class BankCreditExtTerm 
	{
		public static final long TERMINABLE = 0;	// 有期限
		public static final long DATELESS = 1;		// 无期限
		
		//通过代码匹配名称
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			
			switch ((int) lCode)
			{
				case (int)TERMINABLE :
					strReturn = "有期限";
					break;
				case (int)DATELESS :
					strReturn = "无期限";
					break;
			}
			
			return strReturn;
		}
		
		// 得到所有的代码
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = TERMINABLE;
			lTemp[1] = DATELESS;
			
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}

	// 质押类型
	public static class ImpawnType {
		// 担保类型1
		public static final long ENTITY = 1; // 实物质押

		public static final long DROIT = 2; // 权利质押

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ENTITY:
				strReturn = "实物质押";
				break;
			case (int) DROIT:
				strReturn = "权利质押";
				break;
			}
			return strReturn;
		}
	}

	// 担保种类
	public static class AssureKind {
		public static final long INTERNAL = 1; // 对内担保

		public static final long EXTERNAL = 2; // 对外担保

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) INTERNAL:
				strReturn = "对内担保";
				break;
			case (int) EXTERNAL:
				strReturn = "对外担保";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { INTERNAL, EXTERNAL };
			return longTemp;
		}
	}

	// 担保方式
	public static class AssureType {
		// 担保方式assureMode
		public static final long CREDIT = 1; // 信用

		public static final long ASSURE = 2; // 保证  将原来的“担保”改为“保证”

		public static final long PLEDGE = 3; // 抵押

		public static final long IMPAWN = 4; // 质押

		public static final long RECOGNIZANCE = 5; // 保证金
		//added by xiongfei 2010/05/17
		public static final long REPURCHASE = 6;//回购

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CREDIT:
				strReturn = "信用";
				break;
			case (int) ASSURE:
				strReturn = "保证";
				break;
			case (int) PLEDGE:
				strReturn = "抵押";
				break;
			case (int) IMPAWN:
				strReturn = "质押";
				break;
			case (int) RECOGNIZANCE:
				strReturn = "保证金";
				break;
			//added by xiongfei 2010/05/17
			case (int) REPURCHASE:
				strReturn = "回购";
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { CREDIT, ASSURE, PLEDGE, IMPAWN, RECOGNIZANCE ,REPURCHASE };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType",
					officeID, currencyID);
		}
	}

	// 担保类型1
	public static class AssureType1 {
		// 担保类型1
		public static final long FINANCING = 1; // 融资担保

		public static final long NONFINANCING = 2; // 非融资担保

		public static final long OTHER = 3; // 其他

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) FINANCING:
				strReturn = "融资担保";
				break;
			case (int) NONFINANCING:
				strReturn = "非融资担保";
				break;
			case (int) OTHER:
				strReturn = "其他";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { FINANCING, NONFINANCING, OTHER };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType1",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 担保类型2
	public static class AssureType2 {
		// 担保类型2
		public static final long LOAN = 1; // 贷款担保

		public static final long HOMELAND = 2; // 贸易项下的国内担保

		public static final long OVERSEAS = 3; // 贸易项下的国外担保

		public static final long TENDER = 4; // 招投标担保

		public static final long PERFORM = 5; // 履约担保

		public static final long IMPAWN = 6; // 质保

		public static final long PREPAYMENT = 7; // 预付款担保

		public static final long BORROW = 8; //借款担保
		
		public static final long CREDIT = 9; //授信额度担保
		
		public static final long SECURITIES = 10; //有价证券担保
		
		public static final long FINANCING = 11; //融资租赁担保
		
		public static final long DEFERRED = 12; //延期付款担保
		
		public static final long INSURANCE = 13; //投保担保
		
		public static final long TARIFF = 14; //关税担保
		
		public static final long SIGHTPAYMENT = 15; //即期付款担保
		
		public static final long EXPERIENCE = 16; //经验租赁担保
		
		public static final long OTHER = 17; // 其他		
		

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "贷款担保";
				break;
			case (int) HOMELAND:
				strReturn = "贸易项下的国内担保";
				break;
			case (int) OVERSEAS:
				strReturn = "贸易项下的国外担保";
				break;
			case (int) TENDER:
				strReturn = "招投标担保";
				break;
			case (int) PERFORM:
				strReturn = "履约担保";
				break;
			case (int) IMPAWN:
				strReturn = "质保";
				break;
			case (int) PREPAYMENT:
				strReturn = "预付款担保";
				break;
			case (int) OTHER:
				strReturn = "其他";
				break;
			case (int) BORROW:
				strReturn = "借款担保";
			break;
			case (int) CREDIT:
				strReturn = "授信额度担保";
			break;
			case (int) SECURITIES:
				strReturn = "有价证券担保";
			break;
			case (int) FINANCING:
				strReturn = "融资租赁担保";
			break;
			case (int) DEFERRED:
				strReturn = "延期付款担保";
			break;
			case (int) INSURANCE:
				strReturn = "投保担保";
			break;
			case (int) TARIFF:
				strReturn = "关税担保";
			break;
			case (int) SIGHTPAYMENT:
				strReturn = "即期付款担保";
			break;
			case (int) EXPERIENCE:
				strReturn = "经验租赁担保";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] longTemp = { LOAN, HOMELAND, OVERSEAS, TENDER, PERFORM,
					IMPAWN, PREPAYMENT, OTHER, BORROW, CREDIT, SECURITIES,
					FINANCING , DEFERRED, INSURANCE, TARIFF, SIGHTPAYMENT, EXPERIENCE };
			return longTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AssureType2",
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
				case 1:
					lArrayID = new long[] { BORROW, CREDIT, SECURITIES,
							FINANCING, DEFERRED };
					break;
				case 2:
					lArrayID = new long[] { INSURANCE, PREPAYMENT, PERFORM, TARIFF,
							SIGHTPAYMENT, EXPERIENCE, OTHER };
					break;
				case 3:
					lArrayID = new long[] { OTHER };
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
				case 0:
					lArrayID = getAllCode(lOfficeID, lCurrencyID);
					break;
				case 1:
					lArrayID = new long[] { BORROW, CREDIT, SECURITIES,
							FINANCING, DEFERRED };
					break;
				case 2:
					lArrayID = new long[] { INSURANCE, PREPAYMENT, PERFORM, TARIFF,
							SIGHTPAYMENT, EXPERIENCE, OTHER };
					break;
				case 3:
					lArrayID = new long[] { OTHER };
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

	// 买卖方贷款
	public static class BuyOrSaleType {
		// 买卖方贷款
		public static final long BUY = 1; // 买

		public static final long SALE = 2; // 卖

		public static final long NOTUSE = 3; // 不适用

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BUY:
				strReturn = "买";
				break;
			case (int) SALE:
				strReturn = "卖";
				break;
			case (int) NOTUSE:
				strReturn = "不适用";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BUY, SALE, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$BuyOrSaleType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 文件类型
	public static class FileType {
		// 文件类型
		public static final long TXT = 1; // 文本文件

		public static final long DOC = 2; // WORD文档

		public static final long XSL = 3; // EXCEL文档

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) TXT:
				strReturn = "买";
				break;
			case (int) DOC:
				strReturn = "卖";
				break;
			case (int) XSL:
				strReturn = "不适用";
				break;
			}
			return strReturn;
		}
	}

	// 上传附件（合同正式文本）所依附的主体类型
	public static class AttachParentType {
		// 文档父类型
		public static final long CONTRACT = 1; // 合同

		public static final long CLIENT = 2; // 客户

		public static final long LOAN = 3; // 申请

		public static final long CONSIGNCONTRACT = 4; // 委托合同

		public static final long EXTENDAPPLY = 5; // 展期申请

		public static final long CONSIGNUPSAVESETTING = 6; // 上存资金账户设置

		public static final long YTAPPROVALRESOLUTION = 7; // 参加行审批决议(银团贷款)
	}

	// 可以导出的合同类型
	public static class ContractType {
		// 可以导出的合同类型
		public static final long LOAN = 1; // 借款合同

		public static final long ASSURE = 2; // 担保合同

		public static final long PLEDGE = 3; // 抵押合同

		public static final long IMPAWN = 4; // 质押合同

		public static final long EXTEND = 5; // 展期合同

		public static final long DKDCB = 6; // 贷款调查表

		public static final long ASSUREDCB = 7; // 担保调查表

		public static final long FINANCETJB = 8; // 财务统计表

		public static final long TX = 9; // 贴现合同

		public static final long ZTX = 10; // 转贴现合同

		public static final long ZCFZB = 11;// 资产负债表

		public static final long SYB = 12;// 损益表

		public static final long XJLLB = 13;// 现金流量表

		// （中电子的三种质押合同及委托合同） modified by zntan 2004-11-8
		public static final long GQIMPAWN = 14;// 股权质押合同

		public static final long GFIMPAWN = 15;// 股份质押合同

		public static final long CDIMPAWN = 16;// 存单质押合同

		public static final long CONSIGN = 17;// 委托合同（委托贷款委托合同）

		// 上海电气增加 modified by zntan 2004-11-25
		public static final long ZGEASSURE = 18;// 最高额保证合同

		public static final long ZGEPLEDGE = 19;// 最高额抵押合同

		public static final long DYWQD = 20;// 抵押物清单

		public static final long DBXY = 21;// 担保协议

		// 上海浦发增加 modified by liwang 2006-03-21
		public static final long SHPF_KLGNBHXY = 22;// 开立国内保函协议

		public static final long SHPF_RZTenancy = 23;// 融资租赁合同

		public static final long SHPF_ZGXE = 24;// 最高限额借款合同

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "借款合同";
				break;
			case (int) ASSURE:
				strReturn = "担保合同";
				break;
			case (int) PLEDGE:
				strReturn = "抵押合同";
				break;
			case (int) IMPAWN:
				strReturn = "质押合同";
				break;
			case (int) EXTEND:
				strReturn = "展期合同";
				break;
			case (int) DKDCB:
				strReturn = "贷款调查表";
				break;
			case (int) ASSUREDCB:
				strReturn = "担保调查表";
				break;
			case (int) FINANCETJB:
				strReturn = "财务统计表";
				break;
			case (int) TX:
				strReturn = "贴现合同";
				break;
			//case (int) ZTX:
		//		strReturn = "转贴现合同";
		//		break;
			case (int) ZCFZB:
				strReturn = "资产负债表";
				break;
			case (int) SYB:
				strReturn = "损益表";
				break;
			case (int) XJLLB:
				strReturn = "现金流量表";
				break;
			case (int) GQIMPAWN:
				strReturn = "股权质押合同";
				break;
			case (int) GFIMPAWN:
				strReturn = "股份质押合同";
				break;
			case (int) CDIMPAWN:
				strReturn = "存单质押合同";
				break;
			case (int) CONSIGN:
				strReturn = "委托合同";
				break;
			case (int) ZGEASSURE:
				strReturn = "最高额保证合同";
				break;
			case (int) ZGEPLEDGE:
				strReturn = "最高额抵押合同";
				break;
			case (int) DYWQD:
				strReturn = "抵押物清单";
				break;
			case (int) DBXY:
				strReturn = "担保协议";
				break;
			case (int) SHPF_KLGNBHXY:
				strReturn = "开立国内保函协议";
				break;
			case (int) SHPF_RZTenancy:
				strReturn = "融资租赁合同";
				break;
			case (int) SHPF_ZGXE:
				strReturn = "最高限额借款合同";
				break;
			}
			return strReturn;
		}
	}

	// 利率调整状态
	public static class InterestRateSettingStatus {
		// 利率调整状态
		public static final long SUBMIT = 1; // 已保存

		//modified by mzh_fu 2007/07/05
		
		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝
        
		public static final long ADJUST = 4;//已调整
		
		public static final long CANCELAPPLY = 0; //取消申请
		
		public static final long APPROVALING = 20; //审批中
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) ADJUST:
				strReturn = "已调整";
				break;
			case (int) CANCELAPPLY:
				strReturn = "已取消";
				break;
			
			case (int) APPROVALING:
			strReturn = "审批中";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = new long[6];
			lTemp[0]= SUBMIT;
			lTemp[1]= CHECK;
			lTemp[2]= REFUSE;
			lTemp[3]=ADJUST;
			lTemp[4]=CANCELAPPLY;
			lTemp[5]=APPROVALING;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$InterestRateSettingStatus",
							officeID, currencyID);
		}

	}

	// 信用等级
	public static class CreditLevel {
		// 信用等级
		public static final long AAA = 1; // AAA级

		public static final long AA = 2; // AA级

		public static final long A = 3; // A级

		public static final long BBB = 4; // BBB级

		public static final long BB = 5; // BB级

		public static final long B = 6; // B级

		public static final long CCC = 7; // CCC级

		public static final long CC = 8; // CC级

		public static final long C = 9; // C级

		public static final long Aa = 10; // Aa级

		public static final long Ab = 11; // Ab级

		public static final long Ac = 12; // Ac级

		public static final long Ba = 13; // Ba级

		public static final long Bb = 14; // Bb级

		public static final long Bc = 15; // Bc级

		public static final long Ca = 16; // Ca级

		public static final long Cb = 17; // Cb级

		public static final long Cc = 18; // Cc级

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) AAA:
				strReturn = "AAA";
				break;
			case (int) AA:
				strReturn = "AA";
				break;
			case (int) A:
				strReturn = "A";
				break;
			case (int) BBB:
				strReturn = "BBB";
				break;
			case (int) BB:
				strReturn = "BB";
				break;
			case (int) B:
				strReturn = "B";
				break;
			case (int) CCC:
				strReturn = "CCC";
				break;
			case (int) CC:
				strReturn = "CC";
				break;
			case (int) C:
				strReturn = "C";
				break;
			case (int) Aa:
				strReturn = "Aa";
				break;
			case (int) Ab:
				strReturn = "Ab";
				break;
			case (int) Ac:
				strReturn = "Ac";
				break;
			case (int) Ba:
				strReturn = "Ba";
				break;
			case (int) Bb:
				strReturn = "Bb";
				break;
			case (int) Bc:
				strReturn = "Bc";
				break;
			case (int) Ca:
				strReturn = "Ca";
				break;
			case (int) Cb:
				strReturn = "Cb";
				break;
			case (int) Cc:
				strReturn = "Cc";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = null;
			lTemp = new long[5];
			lTemp[0] = AAA;
			lTemp[1] = AA;
			lTemp[2] = A;
			lTemp[3] = BBB;
			lTemp[4] = BB;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditLevel",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 客户分类
	public static class ClientType {
		// 客户分类
		public static final long GFGS = 1; // 股份公司

		public static final long JTGS = 2; // 集团公司

		public static final long QT = 3; // 其他

		public static final long HCQY = 4; // 划出企业

		public static final long MFXD = 5; // 卖方信贷

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) GFGS:
				strReturn = "股份公司";
				break;
			case (int) JTGS:
				strReturn = "集团公司";
				break;
			case (int) QT:
				strReturn = "其他";
				break;
			case (int) HCQY:
				strReturn = "划出企业";
				break;
			case (int) MFXD:
				strReturn = "卖方信贷";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { GFGS, JTGS, QT, HCQY, MFXD };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$ClientType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 利率类型
	public static class InterestRateType {
		// 利率类型
		public static final long BANK = 1; // 银行利率

		public static final long LIBOR = 2; // LIBOR利率

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BANK:
				strReturn = "银行利率";
				break;
			case (int) LIBOR:
				strReturn = "LIBOR利率";
				break;
			}
			return strReturn;
		}
	}

	// 下拉列表类型
	public static class ListType {
		public static final long LOANCLIENTTYPE = 1; // 自营贷款客户分类

		public static final long SETTCLIENTTYPE = 2; // 结算客户分类
	}

	// 免还申请状态
	public static class FreeApplyStatus {
		// 免还申请状态
		public static final long SUBMIT = 1; // 已保存

		public static final long CHECK = 3; // 已审批

		public static final long USED = 4; // 已使用

		public static final long REFUSE = -2; // 已拒绝
		
		public static final long APPROVALING = 20; //审批中
		
		//added by ryping on 07-08-08
		public static final long CANCEL = 0;//已取消
		//end
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			//added by ryping on 07-08-08
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			//end
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
		
		//added by ryping on 2007/06/25
		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, CANCEL ,APPROVALING};
			return lTemp;
		}

		//added by ryping on 2007/06/25
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
	}

	// 合同执行计划变更状态
	public static class PlanModifyStatus {
		// 免还申请状态
		public static final long SUBMIT = 1; // 已保存
		
		//modified by mzh_fu 2007/07/05
		//public static final long CHECK = 2; // 已审核
		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝
		
		public static final long APPROVALING = 20; //审批中

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "已提交";
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;	
			}
			return strReturn;
		}
	}

	// 逾期状态
	public static class OverDueStatus {
		// 逾期状态
		public static final long SUBMIT = 1; // "已保存";

		public static final long CHECK = 3; // "已审批";

		public static final long NOTYET = 4; // "没有逾期";

		public static final long YES = 5; // "已逾期";
		
		public static final long APPROVALING = 20; //审批中

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "已提交";
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) NOTYET:
				strReturn = "没有逾期";
				break;
			case (int) YES:
				strReturn = "已逾期";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;	
			}
			return strReturn;
		}
	}

	// 放款通知单状态
	public static class LoanPayNoticeStatus {
		// 放款通知单状态
		public static final long SUBMIT = 1; // 已保存

		public static final long CHECK = 3; // 已审批

		public static final long USED = 4; // 已使用

		public static final long REFUSE = -2; // 已拒绝
		
		public static final long APPROVALING = 20; //审批中	
		//modify by fulihe 因为数据库存入的都是0 ，所以改为0,此状态以前为5
		public static final long CANCEL = 0; // 已取消

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, REFUSE,APPROVALING,CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$LoanPayNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			}
			return strReturn;
		}
	}

	// 提前还款通知单状态
	public static class AheadRepayStatus {
		// 提前还款通知单状态
		public static final long SUBMIT = 1; // 已保存

		public static final long CHECK = 3; // 已审核

		public static final long USED = 4; // 已使用

		public static final long REFUSE = 5; // "已拒绝";

		public static final long CANCEL = 6; // 已取消
		
		public static final long APPROVALING = 20; //审批中	
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$AheadRepayStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
		
		//Modify by leiyang date 2007/06/25
		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, CANCEL ,APPROVALING};
			return lTemp;
		}

		//Modify by leiyang date 2007/06/25
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
	}

	// 贴现凭证状态
	public static class DiscountCredenceStatus {
		// 贴现凭证状态
		public static final long SAVE = 1; // "已保存";

		public static final long SUBMIT = 2; // "已提交";

		public static final long CHECK = 3; // "已审批";

		public static final long USED = 4; // "已使用";

		public static final long FINISH = 5; // "已结束";
		
		public static final long APPROVALING = 20; //审批中	

		public static final long REFUSE = -2; // "已拒绝";
		
		//added by mzh_fu 2007/03/26 为解决贴现凭证取消后,在贴现凭证查询中查询出来的凭证状态不显示的问题
		public static final long CANCEL = 0; // "已取消";
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) FINISH:
				strReturn = "已结束";
				break;
				
			//added by mzh_fu 2007/03/26 为解决贴现凭证取消后,在贴现凭证查询中查询出来的凭证状态不显示的问题
			case (int) CANCEL:
				strReturn = "已取消";
				break;			
			}
			return strReturn;
		}
		public static final long[] getAllCode() {
			long[] lTemp = null;

			lTemp = new long[8];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = APPROVALING;
			lTemp[3] = CHECK;
			lTemp[4] = USED;
			lTemp[5] = FINISH;
			lTemp[6] = CANCEL;
			lTemp[7] = REFUSE;
			
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$DiscountCredenceStatus",
					officeID, currencyID);
		}
	}
	
	//商业承兑汇票通知 wjdu
	public static class Inform
	{
		public static final long SHQSHXF = 1;//收取手续费
		public static final long DQCHD = 2;//到期承兑
		public static final long DFBXSHH = 3;//垫付本息收回
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int)   SHQSHXF :strReturn = "收取手续费";break;
				case (int)   DQCHD  :strReturn = "到期承兑";break;
				case (int)   DFBXSHH  :strReturn = "垫付本息收回";break;
			}
			return strReturn;
		}
	}
	

	// Libor利率通知状态
	public static class LiborInformStatus {
		// Libor利率通知状态
		public static final long SAVE = 1; // "已保存";

		public static final long SUBMIT = 2; // "已提交";

		public static final long CHECK = 3; // "已审批";

		public static final long REFUSE = -2; // "已拒绝";

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			}
			return strReturn;
		}
	}

	// 提款通知单状态
	public static class LoanDrawNoticeStatus {
		// 提款通知单状态
		//modified by mzh_fu 2007/07/05
		//public static final long SUBMIT = 2; // 已提交
		public static final long SUBMIT = 1; // 已保存
		
		public static final long CHECK = 3; // 已审批

		public static final long USED = 4; // 已使用

		public static final long REFUSE = -2; // 已拒绝
		
		public static final long APPLOVING=20;//审批中
		
		//added by ryping on 07-08-09
		public static final long CANCEL = 0;//已取消
		//end

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, CHECK, USED, REFUSE,APPLOVING };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$LoanDrawNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) SUBMIT:
				// modified by mzh_fu 2007/07/05
				//strReturn = "已提交";
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPLOVING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
	}

	// 计划修改来源类型
	public static class PlanModifyType {
		// 计划修改来源类型
		public static final long LOAN = 1; // 贷款申请

		public static final long MENU = 2; // 计划修改

		public static final long EXTEND = 3; // 展期申请

		public static final long OVERDUE = 4; // 逾期申请

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) LOAN:
				strReturn = "贷款申请";
				break;
			case (int) MENU:
				strReturn = "计划修改";
				break;
			case (int) EXTEND:
				strReturn = "展期申请";
				break;
			case (int) OVERDUE:
				strReturn = "逾期申请";
				break;
			}
			return strReturn;
		}
	}

	// 放款通知单修改来源
	public static class LoanPayNoticeModifySourceType {
		// 放款通知单修改来源
		public static final long JS = 1; // 结算

		public static final long XD = 2; // 信贷

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) JS:
				strReturn = "结算";
				break;
			case (int) XD:
				strReturn = "信贷";
				break;
			}
			return strReturn;
		}
	}

	// 合同(风险)状态变更状态
	public static class RiskModifyStatus {
		// 状态变更状态
		public static final long SUBMIT = 1; // 已保存
		
		//modified by mzh_fu 2007/07/05
//		public static final long CHECK = 2; // 已审核
		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝
		
		public static final long APPROVALING = 20; //审批中
		public static final long cancelApproval = -1;//取消审批 贷后调查报告取消审批时更新loan_risklevel表中status用
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				//modified by mzh_fu 2007/07/05
				//strReturn = "已提交";
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
	}

	// 风险评级
	public static class VentureLevel {
		// 风险评级
		public static final long A = 1; // "正常";

		public static final long B = 2; // "关注";

		public static final long C = 3; // "次级";

		public static final long D = 4; // "可疑";

		public static final long E = 5; // "损失";

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) A:
				strReturn = "正常";
				break;
			case (int) B:
				strReturn = "关注";
				break;
			case (int) C:
				strReturn = "次级";
				break;
			case (int) D:
				strReturn = "可疑";
				break;
			case (int) E:
				strReturn = "损失";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { A, B, C, D, E };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$VentureLevel",
					officeID, currencyID);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	/*
	 * 贷后报告检查区间类型
	 */
	public static class AfterCheckType {
		// 检查区间类型
		public static final long JIDU = 1; // "按季度";

		public static final long MONTH = 2; // "按月";

		public static final long TEMP = 3; // "临时";

		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) JIDU:
				strReturn = "按季度";
				break;
			case (int) MONTH:
				strReturn = "按月";
				break;
			case (int) TEMP:
				strReturn = "临时";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { JIDU, MONTH, TEMP };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AfterCheckType",
					officeID, currencyID);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	
	
	
	
	

	// 可以导出的合同的合同模板文件名
	public static class Template {
		// 模板文件名
		public static final long HN_ZYDK = 1; // 自营贷款

		public static final long HN_WTDK = 2; // 委托贷款

		public static final long HN_WTTJTH = 3; // 统借统还

		public static final long HN_ZGXE = 4; // 最高限额

		public static final long HN_YTDK = 5; // 银团贷款

		public static final long HN_TX = 6; // 贴现-银行汇票

		public static final long HN_ASSURE = 7; // 担保

		public static final long HN_PLEDGE = 8; // 抵押

		public static final long HN_IMPAWN = 9; // 质押

		public static final long HN_EXTENDZY = 10; // 自营展期

		public static final long HN_DKDCB = 11; // 贷款调查表

		public static final long HN_EXTENDWT = 12; // 委托展期

		public static final long HN_TXBIZ = 13; // 贴现-商业汇票

		// （中电子的三种质押合同） by zntan 2004-11-8
		public static final long GQIMPAWN = 14;// 股权质押

		public static final long GFIMPAWN = 15;// 股份质押

		public static final long CDIMPAWN = 16;// 存单质押

		public static final long WTWT = 17;// 委托合同（委托贷款委托合同）

		// 上海电气增加 modified by zntan 2004-11-25
		public static final long ZGEASSURE = 18;// 最高额保证合同

		public static final long ZGEPLEDGE = 19;// 最高额抵押合同

		public static final long DYWQD = 20;// 抵押物清单

		public static final long DBXY = 21;// 担保协议

		// 上海浦发增加 modified by liwang 2006-03-21
		public static final long SHPF_KLGNBHXY = 22;// 开立国内保函协议

		public static final long SHPF_RZTenancy = 23;// 融资租赁合同

		public static final long SHPF_ASSURE = 24;// 保证合同

		public static final long SHPF_IMPAWN = 25;// 抵押合同

		public static final long SHPF_ZYDK = 26;// 自营贷款合同

		public static final long SHPF_WTDK = 27;// 委托贷款合同

		public static final long SHPF_ZGEASSURE = 28;// 最高额保证合同

		public static final long SHPF_ZGEPLEDGE = 29;// 最高额抵押合同

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值

			/* TOCONFIG—TODELETE */
			/*
			 * 产品化不再区分项目,以中电子为参考; ninh 2005-03-24
			 */

			/*
			 * //if (Env.getProjectName().equals(Constant.ProjectName.HN))
			 * //把项目判断改为属性文件控制 haoning 2004-11-03
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN) {
			 * switch ((int) lCode) { case (int) HN_ZYDK : strReturn =
			 * "rmbjkht.txt;c010-v.jsp"; break; case (int) HN_WTDK : strReturn =
			 * "wtdkht.txt;c070-v.jsp"; break; case (int) HN_WTTJTH : strReturn =
			 * "tjthht.txt;c050-v.jsp"; break; case (int) HN_ZGXE : strReturn =
			 * "zgxeht.txt;c080-v.jsp"; break; case (int) HN_YTDK : strReturn =
			 * ""; break; case (int) HN_TX : strReturn =
			 * "yhchdhptxxy.txt;c110-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "bzht.txt;c030-v.jsp"; break; case (int) HN_PLEDGE :
			 * strReturn = "dyht.txt;c040-v.jsp"; break; case (int) HN_IMPAWN :
			 * strReturn = ""; break; case (int) HN_EXTENDZY : strReturn =
			 * "jkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT :
			 * strReturn = "wtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_DKDCB :
			 * strReturn = "dkdcb.txt;c101-v.jsp"; break; case (int) HN_TXBIZ :
			 * strReturn = "shychdhptxxy.txt;c125-v.jsp"; break; } } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.MBEC))
			 * //把项目判断改为属性文件控制 haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.MBEC) {
			 * strReturn = "dqxtdkht.txt;c120-v.jsp"; } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.CNMEF))
			 * //把项目判断改为属性文件控制 haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.CNMEF) {
			 * switch ((int) lCode) { case (int) HN_ZYDK : strReturn =
			 * "rmbjkht.txt;c010-v.jsp"; break; case (int) HN_WTDK : strReturn =
			 * "wtdkht.txt;c070-v.jsp"; break; case (int) HN_WTTJTH : strReturn =
			 * "tjthht.txt;c050-v.jsp"; break; case (int) HN_ZGXE : strReturn =
			 * "zgxeht.txt;c080-v.jsp"; break; case (int) HN_YTDK : strReturn =
			 * ""; break; case (int) HN_TX : strReturn =
			 * "yhchdhptxxy.txt;c110-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "bzht.txt;c030-v.jsp"; break; case (int) HN_PLEDGE :
			 * strReturn = "dyht.txt;c040-v.jsp"; break; case (int) HN_IMPAWN :
			 * strReturn = ""; break; case (int) HN_EXTENDZY : strReturn =
			 * "jkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT :
			 * strReturn = "wtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_DKDCB :
			 * strReturn = "dkdcb.txt;c101-v.jsp"; break; case (int) HN_TXBIZ :
			 * strReturn = "shychdhptxxy.txt;c125-v.jsp"; break; } } //else if
			 * (Env.getProjectName().equals(Constant.ProjectName.HAIER))
			 * //把项目判断改为属性文件控制 haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HAIER) {
			 * switch ((int) lCode) { case (int) HN_DKDCB : strReturn =
			 * "haierdkdcb.txt;c401-v.jsp"; break; case (int) HN_ZYDK :
			 * strReturn = "haierrmbjkht.txt;c410-v.jsp"; break; case (int)
			 * HN_TX : strReturn = "haieryhchdhptxxy.txt;c460-v.jsp"; break;
			 * case (int) HN_TXBIZ : strReturn =
			 * "haiershychdhptxxy.txt;c470-v.jsp"; break; case (int) HN_ASSURE :
			 * strReturn = "haierbzht.txt;c430-v.jsp"; break; case (int)
			 * HN_PLEDGE : strReturn = "haierdyht.txt;c440-v.jsp"; break; case
			 * (int) HN_IMPAWN : strReturn = "haierzyht.txt;c450-v.jsp"; break; } }
			 * //把项目判断改为属性文件控制 haoning 2004-11-03 else
			 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.CEC) {
			 * switch ((int) lCode) { case (int) HN_ZYDK ://自营贷款借款合同 strReturn =
			 * "cecjkht.txt;c500-v.jsp"; break; case (int) HN_TX ://商业汇票贴现合同
			 * strReturn = "cecshyhptxht.txt;c537-v.jsp"; break; case (int)
			 * HN_ASSURE ://保证合同 strReturn = "cecbzht.txt;c506-v.jsp"; break;
			 * case (int) HN_PLEDGE ://抵押合同 strReturn =
			 * "cecdyht.txt;c525-v.jsp"; break; case (int) GQIMPAWN ://股权质押合同
			 * strReturn = "cecgqzhyht.txt;c510-v.jsp"; break; case (int)
			 * GFIMPAWN ://股份质押合同 strReturn = "cecgfzhyht.txt;c515-v.jsp";
			 * break; case (int) CDIMPAWN ://存单质押合同 strReturn =
			 * "ceccdzhyht.txt;c520-v.jsp"; break; case (int) HN_WTDK
			 * ://委托贷款借款合同 strReturn = "cecwtjkht.txt;c534-v.jsp"; break; case
			 * (int) WTWT ://委托贷款委托合同 strReturn = "cecwtdkwtht.txt;c531-v.jsp";
			 * break; case (int) HN_ZGXE ://最高限额借款合同共用自营贷款的借款合同 strReturn =
			 * "cecjkht.txt;c500-v.jsp"; break; case (int) HN_DKDCB ://贷款调查表
			 * strReturn = "cecdkdcb.txt;c101-v.jsp"; break; case (int)
			 * HN_EXTENDZY ://自营展期合同沿用华能 strReturn = "cecjkzqxy.txt;c310-v.jsp";
			 * break; case (int) HN_EXTENDWT ://委托展期合同沿用华能 strReturn =
			 * "cecwtdkzqxy.txt;c320-v.jsp"; break; case (int) HN_WTTJTH
			 * ://委托贷款统借统还借款合同同委托贷款短期 strReturn = "cecwtjkht.txt;c534-v.jsp";
			 * break; } } else if(Config.GLOBAL.getProjectType() ==
			 * Constant.ProjectType.SEFC) { switch ((int) lCode) { case (int)
			 * HN_ZYDK ://自营贷款借款合同 strReturn = "SEFCjkht.txt;c550-v.jsp"; break;
			 * case (int) HN_ASSURE ://保证合同 strReturn =
			 * "SEFCbzht.txt;c555-v.jsp"; break; case (int) HN_PLEDGE ://抵押合同
			 * strReturn = "SEFCdyht.txt;c566-v.jsp"; break; case (int)
			 * HN_IMPAWN : strReturn = ""; break; case (int) HN_WTDK ://委托贷款借款合同
			 * strReturn = "SEFCwtdkxy.txt;c579-v.jsp"; break; case (int)
			 * HN_WTTJTH ://委托贷款统借统还借款合同同委托贷款短期 strReturn =
			 * "SEFCwtdkxy.txt;c579-v.jsp"; break; case (int) ZGEASSURE
			 * ://最高额保证合同 strReturn = "SEFCzgebzht.txt;c560-v.jsp"; break; case
			 * (int) ZGEPLEDGE ://最高额抵押合同 strReturn =
			 * "SEFCzgedyht.txt;c572-v.jsp"; break; case (int) DYWQD ://抵押物清单
			 * strReturn = "SEFCdywqd.txt;c581-v.jsp"; break; case (int)
			 * HN_DKDCB ://贷款调查表沿用华能 strReturn = "SEFCdkdcb.txt;c101-v.jsp";
			 * break; case (int) HN_EXTENDZY ://自营展期合同沿用华能 strReturn =
			 * "SEFCjkzqxy.txt;c310-v.jsp"; break; case (int) HN_EXTENDWT
			 * ://委托展期合同沿用华能 strReturn = "SEFCwtdkzqxy.txt;c320-v.jsp"; break;
			 * case (int) HN_ZGXE ://最高限额贷款同自营贷款 strReturn =
			 * "SEFCjkht.txt;c550-v.jsp"; break; case (int) DBXY://担保协议
			 * strReturn = "SEFCdbxy.txt;c582-v.jsp"; break; } } //
			 */

			switch ((int) lCode) {
			case (int) HN_ZYDK:
				strReturn = "casicrmbjkht.txt;c600-v.jsp";
				break;
			case (int) HN_WTDK:
				strReturn = "casicwtjkht.txt;c534-v.jsp";
				break;
			case (int) HN_WTTJTH:
				strReturn = "tjthht.txt;c050-v.jsp";
				break;
			case (int) HN_ZGXE:
				strReturn = "casicrmbjkht.txt;c600-v.jsp";
				break;
			case (int) HN_YTDK:
				strReturn = "";
				break;
			case (int) HN_TX:
				strReturn = "casicyhchdhptxxy.txt;c460-v.jsp";// 和Haier的模板一样
				break;
			case (int) HN_ASSURE:
				strReturn = "casicbzht.txt;c615-v.jsp";
				break;
			case (int) HN_PLEDGE:
				strReturn = "casicdyht.txt;c605-v.jsp";
				break;
			case (int) HN_IMPAWN:
				strReturn = "casiczyht.txt;c610-v.jsp";
				break;
			case (int) HN_EXTENDZY:
				strReturn = "casicjkzqxy.txt;c310-v.jsp";
				break;
			case (int) HN_EXTENDWT:
				strReturn = "wtdkzqxy.txt;c320-v.jsp";
				break;
			case (int) HN_DKDCB:
				strReturn = "dkdcb.txt;c101-v.jsp";
				break;
			case (int) HN_TXBIZ:
				strReturn = "casicshychdhptxxy.txt;c470-v.jsp";// 和Haier的模板一样
				break;
			case (int) GQIMPAWN:// 股权质押合同
				strReturn = "cecgqzhyht.txt;c510-v.jsp";
				break;
			case (int) GFIMPAWN:// 股份质押合同
				strReturn = "cecgfzhyht.txt;c515-v.jsp";
				break;
			case (int) CDIMPAWN:// 存单质押合同
				strReturn = "ceccdzhyht.txt;c520-v.jsp";
				break;
			case (int) WTWT:// 委托贷款委托合同
				strReturn = "casicwtdkwtht.txt;c531-v.jsp";
				break;
			case (int) ZGEASSURE:// 最高额保证合同
				strReturn = "SEFCzgebzht.txt;c560-v.jsp";
				break;
			case (int) ZGEPLEDGE:// 最高额抵押合同
				strReturn = "SEFCzgedyht.txt;c572-v.jsp";
				break;
			case (int) DYWQD:// 抵押物清单
				strReturn = "SEFCdywqd.txt;c581-v.jsp";
				break;
			case (int) DBXY:// 担保协议
				strReturn = "casicdbxy.txt;c582-v.jsp";
				break;
			// 上海浦发
			case (int) SHPF_ZYDK:// 自营贷款合同
				strReturn = "shpfrmbjkht.txt;c620-v.jsp";
				break;
			case (int) SHPF_WTDK:// 委托贷款合同
				strReturn = "shpfwtdkwt.txt;c626-v.jsp";
				break;
			case (int) SHPF_ASSURE:// 保证合同
				strReturn = "shpfbzht.txt;c630-v.jsp";
				break;
			case (int) SHPF_IMPAWN:// 抵押合同
				strReturn = "shpfdyht.txt;c634-v.jsp";
				break;
			case (int) SHPF_ZGEASSURE:// 最高额保证合同
				strReturn = "shpfzgebzht.txt;c638-v.jsp";
				break;
			case (int) SHPF_ZGEPLEDGE:// 最高额抵押合同
				strReturn = "shpfzgedyht.txt;c642-v.jsp";
				break;
			case (int) SHPF_KLGNBHXY:// 开立国内保函协议
				strReturn = "shpfklgnbhxy.txt;c646-v.jsp";
				break;
			case (int) SHPF_RZTenancy:// 融资租赁合同
				strReturn = "shpfrzzlht.txt;c650-v.jsp";
				break;
			}

			/* TOCONFIG—END */

			return strReturn;
		}

	}

	// 到期业务提醒类型
	public static class AtTermAwakeType {
		// 到期业务提醒类型
		public static final long ZYDQ = 1; // 自营短期贷款

		public static final long ZYZCQ = 2; // 自营中长期贷款

		public static final long WT = 3; // 委托贷款

		public static final long WTTJTH = 4; // 委托贷款—统借统还

		// public static final long TX = 5; //贴现
		public static final long ZGXEDQ = 6; // 最高限额短期

		public static final long ZGXEZCQ = 7; // 最高限额中长期

		public static final long YTDQ = 8; // 银团短期贷款

		public static final long YTZCQ = 10; // 银团中长期贷款

		// public static final long ZTX = 11; //转贴现
		public static final long DKLVTZ = 20; // 贷款利率调整

		public static final long EXCEED = 21; // 逾期提醒
		
		public static final long WTCOMMISSION = 30; //委托贷款手续费收取提醒

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ZYDQ:
				strReturn = "自营短期贷款";
				break;
			case (int) ZYZCQ:
				strReturn = "自营中长期贷款";
				break;
			case (int) WT:
				strReturn = "委托贷款";
				break;
			case (int) WTTJTH:
				strReturn = "委托贷款-统借统还";
				break;
			case (int) ZGXEDQ:
				strReturn = "最高限额短期";
				break;
			case (int) ZGXEZCQ:
				strReturn = "最高限额中长期";
				break;
			case (int) YTDQ:
				strReturn = "银团短期贷款";
			case (int) YTZCQ:
				strReturn = "银团中长期贷款";
				break;
			case (int) DKLVTZ:
				strReturn = "贷款利率调整";
				break;
			case (int) EXCEED:
				strReturn = "逾期提醒";
				break;
			case (int) WTCOMMISSION:
				strReturn = "委托贷款手续费收取提醒";
			}
			return strReturn;
		}

		public static final long getCount() {
			return 10;
		}
	}

	// 修改计划的几个原因
	public static class WhoChangePlan {
		public static final long LOANAPPLY = 1; // 贷款申请

		public static final long PLANMODIFY = 2; // 执行计划修改

		public static final long EXTENDAPPLY = 3; // 展期申请

		public static final long OVERDUEAPPLY = 4; // 逾期申请
	}

	// 贷款放款方式
	public static class PayType {
		public static final long DAY = 1; // 按日放款

		public static final long WEEK = 2; // 按周放款

		public static final long MONTH = 3; // 按月放款

		public static final long QUARTOR = 4; // 按季放款

		public static final long HALFYEAR = 5; // 按半年放款

		public static final long YEAR = 6; // 按年放款

		public static final long ONETIME = 7; // 一次放款

		public static final long NOTUSE = 8; // 不适用

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DAY:
				strReturn = "按日放款";
				break;
			case (int) WEEK:
				strReturn = "按周放款";
				break;
			case (int) MONTH:
				strReturn = "按月放款";
				break;
			case (int) QUARTOR:
				strReturn = "按季放款";
				break;
			case (int) HALFYEAR:
				strReturn = "按半年放款";
				break;
			case (int) YEAR:
				strReturn = "按年放款";
				break;
			case (int) ONETIME:
				strReturn = "一次放款";
				break;
			case (int) NOTUSE:
				strReturn = "不适用";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DAY, WEEK, MONTH, QUARTOR, HALFYEAR, YEAR,
					ONETIME, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$PayType",
					officeID, currencyID);
		}

		public static final long getCount() {
			return 8;
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 贷款还款方式
	public static class RepayType {
		public static final long DAY = 1; // 按日还款

		public static final long WEEK = 2; // 按周还款

		public static final long MONTH = 3; // 按月还款

		public static final long QUARTOR = 4; // 按季还款

		public static final long HALFYEAR = 5; // 按半年还款

		public static final long YEAR = 6; // 按年还款

		public static final long ONETIME = 7; // 一次还款

		public static final long NOTUSE = 8; // 不适用

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DAY:
				strReturn = "按日还款";
				break;
			case (int) WEEK:
				strReturn = "按周还款";
				break;
			case (int) MONTH:
				strReturn = "按月还款";
				break;
			case (int) QUARTOR:
				strReturn = "按季还款";
				break;
			case (int) HALFYEAR:
				strReturn = "按半年还款";
				break;
			case (int) YEAR:
				strReturn = "按年还款";
				break;
			case (int) ONETIME:
				strReturn = "一次清还";
				break;
			case (int) NOTUSE:
				strReturn = "不适用";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { DAY, WEEK, MONTH, QUARTOR, HALFYEAR, YEAR,
					ONETIME, NOTUSE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$RepayType",
					officeID, currencyID);
		}

		public static final long getCount() {
			return 8;
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 计划类型
	public static class PlanType {
		// 计划类型
		public static final long PAY = 1; // 放款

		public static final long REPAY = 2; // 还款

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) PAY:
				strReturn = "放款";
				break;
			case (int) REPAY:
				strReturn = "还款";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { PAY, REPAY };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$PlanType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 按地区分类
	public static class AreaType {
//		public static final long CLOSE = 1; // 封闭贷款
//
//		public static final long POLICY = 2; // 政策性贷款
//
//		public static final long SPECIAL = 3; // 专项贷款
//
//		public static final long GENERAL = 4; // 一般商业贷款
//
//		public static final long CONSIGN = 5;// 委托贷款
//
//		public static final long DISCOUNT = 6;// 贴现
//
//		public static final long LIMITLOAN = 7;// 最高限额贷款
//
//		public static final long ASSURE = 8;// 担保
//
//		public static final long BANK_ZXSY_NJFH = 9;// 中信实业银行南京分行
//
//		public static final long BANK_ZGJS_HZFH = 10;// 中国建设银行杭州市支江分行
//
//		public static final long BANK_ZSGF = 11;// 浙商银行股份有限公司
//
//		public static final long BANK_SZFZ_HZQT = 12;// 深圳发展银行杭州清泰支行
//
//		public static final long[] getAllCode() {
//
//			long[] lTemp = { CLOSE, POLICY, SPECIAL, GENERAL, CONSIGN,
//					DISCOUNT, LIMITLOAN, ASSURE, BANK_ZXSY_NJFH,
//					BANK_ZGJS_HZFH, BANK_ZSGF, BANK_SZFZ_HZQT };
//			return lTemp;
//		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.AREA;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
		public static final long[] getOfficeIdAndCurrencyIdAllCode(long officeId, long currencyId, long typeId) {
			Collection coll = new ArrayList();
			//long assortId = LOANConstant.AssortSetType.AREA;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssort(officeId, currencyId, typeId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$AreaType",
					officeID, currencyID);
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}
		
//		public static final String getName(long lCode) {
//			String strReturn = ""; // 初始化返回值
//			switch ((int) lCode) {
//			case (int) CLOSE:
//				strReturn = "封闭贷款";
//				break;
//			case (int) POLICY:
//				strReturn = "政策性贷款";
//				break;
//			case (int) SPECIAL:
//				strReturn = "专项贷款";
//				break;
//			case (int) GENERAL:
//				strReturn = "一般商业贷款";
//				break;
//			case (int) CONSIGN:
//				strReturn = "委托贷款";
//				break;
//			case (int) DISCOUNT:
//				strReturn = "贴现";
//				break;
//			case (int) LIMITLOAN:
//				strReturn = "最高限额贷款";
//				break;
//			case (int) ASSURE:
//				strReturn = "担保";
//				break;
//			case (int) BANK_ZXSY_NJFH:
//				strReturn = "中信实业银行南京分行";
//				break;
//			case (int) BANK_ZGJS_HZFH:
//				strReturn = "中国建设银行杭州市支江分行";
//				break;
//			case (int) BANK_ZSGF:
//				strReturn = "浙商银行股份有限公司";
//				break;
//			case (int) BANK_SZFZ_HZQT:
//				strReturn = "深圳发展银行杭州清泰支行";
//				break;
//			}
//			return strReturn;
//		}
	}

	// 按行业分类1
	public static class IndustryType1 {
		// 军品MILITARY/房地产ESTATE/特种车SPECIALCAR/汽车零配件CARSPARE
		// 通信COMMUNICATION/特种材料SPECIALMATERIAL/其他OTHER
//		public static final long MILITARY = 1; // 军品
//
//		public static final long ESTATE = 2; // 房地产
//
//		public static final long SPECIALCAR = 3; // 特种车
//
//		public static final long CARSPARE = 4; // 汽车零配件
//
//		public static final long COMMUNICATION = 5; // 通信
//
//		public static final long SPECIALMATERIAL = 6; // 特种材料
//
//		public static final long OTHER = 7; // 其他
//
//		// nhcw(2005-11-21) add by kenny
//		public static final long CONTENTFLOW = 8;// 物流
//
//		public static final long PLANESERVICE = 9;// 飞机维修
//
//		public static final long NAVIGATETRANSPORT = 10;// 航空运输、航空货运
//
//		public static final long JUNKETING = 11;// 旅游

		// public static final long ESTATE = 2; //房地产
		// public static final long OTHER = 7; //其他
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}
//		public static final String getName(long lCode) {
//			String strReturn = ""; // 初始化返回值
//			switch ((int) lCode) {
//			case (int) MILITARY:
//				strReturn = "军品";
//				break;
//			case (int) ESTATE:
//				strReturn = "房地产";
//				break;
//			case (int) SPECIALCAR:
//				strReturn = "特种车";
//				break;
//			case (int) CARSPARE:
//				strReturn = "汽车零配件";
//				break;
//			case (int) COMMUNICATION:
//				strReturn = "通信";
//				break;
//			case (int) SPECIALMATERIAL:
//				strReturn = "特种材料";
//				break;
//			case (int) OTHER:
//				strReturn = "其他";
//				break;
//			case (int) CONTENTFLOW:
//				strReturn = "物流";
//				break;
//			case (int) PLANESERVICE:
//				strReturn = "飞机维修";
//				break;
//			case (int) NAVIGATETRANSPORT:
//				strReturn = "航空运输";
//				break;
//			case (int) JUNKETING:
//				strReturn = "旅游";
//				break;
//			}
//			return strReturn;
//		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY1;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
//		public static final long[] getAllCode() {
//			long[] lReturn = null;
//			long[] lTemp = { MILITARY, ESTATE, SPECIALCAR, CARSPARE,
//					COMMUNICATION, SPECIALMATERIAL, OTHER, CONTENTFLOW,
//					PLANESERVICE, NAVIGATETRANSPORT, JUNKETING };
//			lReturn = lTemp;
//			return lReturn;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType1",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 按行业分类2
	public static class IndustryType2 {
		// 平台PLATFORM/本部LOCAL
		// 上线单位ONLINE/非上线单位UNONLINE
//		public static final long PLATFORM = 1; // 平台
//
//		public static final long LOCAL = 2; // 本部
//
//		//public static final long ONLINE = 3; //上线单位
//		//public static final long UNONLINE = 4; //非上线单位
//
//		// nhcw(2005-11-21) add by kenny
//		public static final long STOCKCOMPANY = 5; // 股份控股公司
//
//		public static final long GROUPCOMPANY = 6; // 集团控股公司
//
//		public static final long HOLDINGCOMPANY = 7; // 控股公司
//
//		public static final long ASSISTANTTRADE = 8; // 辅业板块
//
//		public static final long SUBSIDIARY = 9; // 子公司

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY2;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
		
//		public static final long[] getAllCode() {
//			long[] lReturn = null;
//			long[] lTemp = { PLATFORM, LOCAL, STOCKCOMPANY, GROUPCOMPANY,
//					HOLDINGCOMPANY, ASSISTANTTRADE, SUBSIDIARY };
//			lReturn = lTemp;
//
//			return lReturn;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType2",
					officeID, currencyID);
		}

//		public static final String getName(long lCode) {
//			String strReturn = ""; // 初始化返回值
//			switch ((int) lCode) {
//			case (int) PLATFORM:
//				strReturn = "平台";
//				break;
//			case (int) LOCAL:
//				strReturn = "本部";
//				break;
//			case (int) STOCKCOMPANY:
//				strReturn = "股份控股公司";
//				break;
//			case (int) GROUPCOMPANY:
//				strReturn = "集团控股公司";
//				break;
//			case (int) HOLDINGCOMPANY:
//				strReturn = "控股公司";
//				break;
//			case (int) ASSISTANTTRADE:
//				strReturn = "辅业板块";
//				break;
//			case (int) SUBSIDIARY:
//				strReturn = "子公司";
//				break;
//			}
//			return strReturn;
//		}

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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 按行业分类3
	public static class IndustryType3 {
//		public static final long CONTROLSTOCK = 1; // 控股
//
//		public static final long SHARESTOCK = 2; // 参股
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			strReturn = delegation.getAssortNameByID(lCode);
			return strReturn;
		}

		public static final long[] getAllCode() {
			Collection coll = new ArrayList();
			long assortId = LOANConstant.AssortSetType.INDUSTRY3;
			LoanAssortSettingDelegation delegation = new LoanAssortSettingDelegation();
			coll = delegation.getAssortNameAndIDByAssortId(assortId);
			Iterator iterator = coll.iterator();
			long[] lTemp = new long[coll.size()];
			int i = 0;
			while(iterator.hasNext()){
				LoanAssortSettingInfo info = (LoanAssortSettingInfo)iterator.next();
				lTemp[i] = info.getId();
				i++;
			}
			return lTemp;
		}
//		public static final long[] getAllCode() {
//			long[] lTemp = { CONTROLSTOCK, SHARESTOCK };
//			return lTemp;
//		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$IndustryType3",
					officeID, currencyID);
		}

//		public static final String getName(long lCode) {
//			String strReturn = ""; // 初始化返回值
//			switch ((int) lCode) {
//			case (int) CONTROLSTOCK:
//				strReturn = "控股";
//				break;
//			case (int) SHARESTOCK:
//				strReturn = "参股";
//				break;
//			}
//			return strReturn;
//		}

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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 是否牵头行
	public static class IsHead {
		// 是否牵头行
		public static final long YES = 1; // 是

		public static final long NO = 0; // 不是
	}

	/* 手续费收取方方式 */
	public static class ChargeRatePayType {
		public static final long ONETIME = 1;

		public static final long YEAR = 2;

		public static final long QUARTER = 3;

		public static final long HALFYEAR = 4;

		public static final long MONTH = 5;

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ONETIME:
				strReturn = "一次性";
				break;
			case (int) YEAR:
				strReturn = "按年";
				break;
			case (int) HALFYEAR:
				strReturn = "按半年";
				break;
			case (int) QUARTER:
				strReturn = "按季";
				break;
			case (int) MONTH:
				strReturn = "按月";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ONETIME, YEAR, HALFYEAR, QUARTER, MONTH };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$ChargeRatePayType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 放款通知单放款方式
	public static class TransType {
		// 放款通知单放款方式
		public static final long Bank = 1; // 银行付款

		public static final long CurrentAccount = 2; // 活期账户
	}

	public static class AttornmentStatus {
		// 贷款转让申请状态
		public static final long SAVE = 1; // 已保存

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审核

		public static final long REFUSE = 4; // 已拒绝

		public static final long USED = 5; // 已使用

		public static final long CANCEL = 6; // 已经取消
		
		public static final long REJECTED = 7; //已拒绝
		
		public static final long APPROVALING = 8;//审核中		

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/07/05
				//strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REJECTED :
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING :	
				strReturn = "审批中";
			    break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, USED, CANCEL, REJECTED, APPROVALING };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AttornmentStatus",
							officeID, currencyID);
		}

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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	public static class CheckDiscountBillStatus {
		// 企业类型
		public static final long WSC = 1; // 未审查

		public static final long ZZSC = 2; // 正在审查

		public static final long SCYX = 3; // 审查有效

		public static final long SCWX = 4; // 审查无效

	}

	/**
	 * 
	 * 页面操作定义
	 * 
	 * @author rongyang
	 */
	public static class Actions {
		public static final int CREATETEMPSAVE = 1; // 新增临时保存

		public static final int MODIFYTEMPSAVE = 2; // 修改临时保存

		public static final int CREATESAVE = 3; // 创建保存

		public static final int MODIFYSAVE = 4; // 修改保存

		public static final int DELETE = 5; // 删除

		public static final int LINKSEARCH = 6; // 链接查找

		public static final int MATCHSEARCH = 7; // 匹配查找

		public static final int CHECK = 8; // 复核/审核

		public static final int CANCELCHECK = 9; // 取消复核

		public static final int NEXTSTEP = 10; // 下一步

		public static final int TODETAIL = 11; // 点交易号到详细页面

		public static final int MODIFYNEXTSTEP = 12; // 更改下一步

		public static final int REJECT = 13; // 拒绝

		public static final int RETURN = 14; // 返回修改

		public static final int CHECKOVER = 15; // 审核完成

		public static final int MASSCHECK = 16; // 批量复核

		public static final int MASSCANCELCHECK = 17; // 批量取消复核

		public static final int UPDATESEARCH = 18; // 修改查找

		public static final int CHECKSEARCH = 19; // 审核查找

		public static final int COMMIT = 20; // 提交
		public static final int QUERY = 24;

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case CREATETEMPSAVE:
				strReturn = "临时保存";
				break;
			case MODIFYTEMPSAVE:
				strReturn = "临时保存";
				break;
			case CREATESAVE:
				strReturn = "创建保存";
				break;
			case MODIFYSAVE:
				strReturn = "修改保存";
				break;
			case DELETE:
				strReturn = "删除";
				break;
			case LINKSEARCH:
				strReturn = "链接查找";
				break;
			case MATCHSEARCH:
				strReturn = "匹配查找";
				break;
			case CHECK:
				strReturn = "复核";
				break;
			case CANCELCHECK:
				strReturn = "取消复核";
				break;
			case NEXTSTEP:
				strReturn = "下一步";
				break;
			case TODETAIL:
				strReturn = "察看明细";
				break;
			case REJECT:
				strReturn = "拒绝";
				break;
			case RETURN:
				strReturn = "返回修改";
				break;
			case CHECKOVER:
				strReturn = "审核完成";
				break;
			case MASSCHECK:
				strReturn = "批量复核";
				break;
			case MASSCANCELCHECK:
				strReturn = "批量取消复核";
				break;
			case UPDATESEARCH:
				strReturn = "修改查找";
				break;
			case CHECKSEARCH:
				strReturn = "审核查找";
				break;
			case COMMIT:
				strReturn = "提交";
			case QUERY:
				strReturn = "查询";

			}
			return strReturn;
		}
	}

	/**
	 * 转贴现类型 买入、卖出
	 * 
	 * @author haoning
	 */
	public static class TransDiscountInOrOut {
		public static final long IN = 1; // 买入

		public static final long OUT = 2; // 卖出

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) IN:
				strReturn = "买入";
				break;
			case (int) OUT:
				strReturn = "卖出";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { IN, OUT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransDiscountInOrOut",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	/**
	 * 转贴现种类 买断式、回购式
	 * 
	 * @author haoning
	 */
	public static class TransDiscountType {
		public static final long BUYBREAK = 1; // 买断式

		public static final long REPURCHASE = 2; // 回购式

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) BUYBREAK:
				strReturn = "买断式";
				break;
			case (int) REPURCHASE:
				strReturn = "回购式";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { BUYBREAK, REPURCHASE };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransDiscountType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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
	
	/**
	 * 变更操作
	 * 
	 * @author haoning
	 */
	public static class ChangeOper {
		public static final long AAMOUNT = 1; // 激活额度信息
		public static final long ACHANGE = 2; // 激活变更信息

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) AAMOUNT:
				strReturn = "授信额度信息";
				break;
			case (int) ACHANGE:
				strReturn = "授信变更信息";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AAMOUNT, ACHANGE };
			return lTemp;
		}
	}

	/**
	 * 回购方式 一次性回购、分次回购
	 * 
	 * @author haoning
	 */
	public static class TransRepurchaseType {
		public static final long ONCE = 1; // 一次性回购

		public static final long MULTI_TIME = 2; // 分次回购

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ONCE:
				strReturn = "一次性回购";
				break;
			case (int) MULTI_TIME:
				strReturn = "分次回购";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { -1, ONCE, MULTI_TIME };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$TransRepurchaseType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 凭证类型
	public static class CredenceType {
		// 凭证类型
		public static final long DISCOUNTCREDENCE = 1; // "贴现凭证";

		public static final long TRANSDISCOUNTCREDENCE = 2; // "转贴现凭证";

		public static final long REPURCHASECREDENCE = 3; // "回购凭证";

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DISCOUNTCREDENCE:
				strReturn = "贴现凭证";
				break;
			case (int) TRANSDISCOUNTCREDENCE:
				strReturn = "转贴现凭证";
				break;
			case (int) REPURCHASECREDENCE:
				strReturn = "回购凭证";
				break;
			}
			return strReturn;
		}
	}

	// 票据来源
	public static class BillSourceType {
		// 凭证类型
		public static final long DISCOUN = 1; // "贴现";

		public static final long PACHASETRANSDISCOUNT = 2; // "转贴现买入";

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DISCOUN:
				strReturn = "贴现";
				break;
			case (int) PACHASETRANSDISCOUNT:
				strReturn = "转贴现买入";
				break;
			}
			return strReturn;
		}
	}
	
	public static class CreditRelationType {
		
		public static final long SIMPLE = 1;  //两级授信关系
		
		public static final long MULTILEVEL = 2; //多级授信关系
		
	}

	
	/**
	 * 授信额度状态
	 * @author Administrator
	 *
	 */
	public static class CreditStatus {
		
		public static final long SAVE = 1;     //已保存
		
 		public static final long CHECK = 3;    //已审批
		
		public static final long APPROVALING = 20; //审批中
		
		
		
		public static final long DUFAULT = -1;  //默认值
		
		public static final long ACTIVE = 6;   //已激活

		public static final long FREEZE = 7;   //已冻结
		
		public static final long DELETE = 0;  //已删除
		
		
		
		public static final long OVERTIME = -20;   //已过期（不用）
		
		public static final long SUBMIT = -10; //已提交（不用）
		
		//public static final long REFUSE = 4;   //已拒绝
		
		//public static final long CANCEL = 5;   //已取消
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
			    strReturn = "已保存";
			    break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) ACTIVE:
				strReturn = "已激活";
				break;
			case (int) FREEZE:
				strReturn = "已冻结";
				break;
			//case (int) CANCEL:
			//	strReturn = "已取消";
			//	break;
			case (int) DELETE:
				strReturn = "已删除";
				break;
			//case (int) OVERTIME:
			//	strReturn = "已过期";
			//	break;
			//case (int) SUBMIT:
			//    strReturn = "已提交";
			//    break;
			//case (int) REFUSE:
			//    strReturn = "已拒绝";
			//    break;
			case (int) APPROVALING:
			    strReturn = "审批中";
			    break;
			}
			return strReturn;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 * @param lArrayID 自定义下拉框现实的内容
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, 
				boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				switch (nType) {
				case 0:
					lArrayID = new long[2];
					lArrayID[0] = CHECK;
					lArrayID[1] = ACTIVE;
					break;
				case 1:
					lArrayID = new long[4];
					lArrayID[0] = SAVE;
					lArrayID[1] = APPROVALING;
					lArrayID[2] = CHECK;
					lArrayID[3] = ACTIVE;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	

	
	//授信种类查询方式
	public static class queryCreditProduct
	{
		public static final long SELF_AND_ZHSX = 1; // 自己和综合授信
		public static final long ALL = 2; // 全部类型
		public static final long SELF= 3; // 自己
		
		public static final String getQueryString(long lCode, long lCreditProduct){
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SELF_AND_ZHSX:
				if(lCreditProduct == CreditProduct.ZHSX){
					strReturn = CreditProduct.ZY + ", " + CreditProduct.SP + ", " + CreditProduct.BH + ", " + CreditProduct.ZHSX;
				}
				else {
					strReturn = lCreditProduct + ", " + CreditProduct.ZHSX;
				}
				break;
			case (int) ALL:
				strReturn = CreditProduct.ZY + ", " + CreditProduct.SP + ", " + CreditProduct.BH + ", " + CreditProduct.ZHSX;
				break;
			case (int) SELF:
				strReturn = String.valueOf(lCreditProduct);
				break;
			}
			return strReturn;
		}
	}
	/**
	 * 授信品种
	 * @author Administrator
	 *
	 */
	public static class CreditProduct {
		
		public static final long ZY = 1;  //自营贷款授信

		public static final long SP = 2;  //商业承兑汇票授信

		public static final long BH = 3;  //保函
		
		public static final long ZHSX = 4;  //综合授信

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "自营贷款";
				break;
			case (int) SP:
				strReturn = "承兑汇票";
				break;
			case (int) BH:
				strReturn = "保函";
				break;
			case (int) ZHSX:
				strReturn = "综合授信";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ZY, SP, BH, ZHSX };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditProduct",
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
				
				case 1:
					lArrayID = new long[3];
					lArrayID[0] = ZY;
					lArrayID[1] = SP;
					lArrayID[2] = BH;
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
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = getAllCode(lOfficeID, lCurrencyID);
						break;
						
					case 1:
						lArrayID = new long[3];
						lArrayID[0] = ZY;
						lArrayID[1] = SP;
						lArrayID[2] = BH;
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

		// 只显示有额度限制的产品
		public static final void showListValid(JspWriter out,
				String strControlName, int nType, long lSelectValue,
				boolean isNeedAll, boolean isNeedBlank, String strProperty,
				long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
				CreditLimitDAO dao = new CreditLimitDAO();
				switch (nType) {
				case 0:
					lArrayID = dao.findIsControlProduct();
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
	
	
	/**
	 * 授信模式
	 * @author leiyang
	 *
	 */
	public static class CreditMode {
		
		public static final long SINGLECORP  = 1;  //单一法人授信

		public static final long GROUP  = 2;  ///集团授信

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SINGLECORP:
				strReturn = "单一法人授信";
				break;
			case (int) GROUP:
				strReturn = "集团授信";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = {SINGLECORP, GROUP};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$CreditMode",
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
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	public static class ActionTrace {
		public static class CreditAction {
			public static final long ADDNEW = 1;

			public static final long ACTIVE = 2;

			public static final long CANCELACTIVE = 3;

			public static final long FREEZE = 4;

			public static final long CANCELFREEZE = 5;

			public static final long MODIFY = 6;

			public static final long DELETE = 7;

			public static final long OVERTIME = 8;

			public static final String getName(long lCode) {
				String strReturn = ""; // 初始化返回值
				switch ((int) lCode) {
				case (int) ADDNEW:
					strReturn = "新增";
					break;
				case (int) ACTIVE:
					strReturn = "激活";
					break;
				case (int) CANCELACTIVE:
					strReturn = "取消激活";
					break;
				case (int) FREEZE:
					strReturn = "冻结";
					break;
				case (int) MODIFY:
					strReturn = "修改";
					break;
				case (int) DELETE:
					strReturn = "删除";
					break;
				case (int) CANCELFREEZE:
					strReturn = "取消冻结";
					break;
				case (int) OVERTIME:
					strReturn = "过期";
					break;

				}
				return strReturn;
			}

		}
	}

	public static class CreditReportType {
		public static final long ABOUTUSE = 1;

		public static final long STATISTICS = 2;

		public static final long DETAIL = 3;

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ABOUTUSE:
				strReturn = "使用情况表";
				break;
			case (int) STATISTICS:
				strReturn = "统计表";
				break;
			case (int) DETAIL:
				strReturn = "明细表";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ABOUTUSE, STATISTICS, DETAIL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$CreditReportType",
							officeID, currencyID);
		}

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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	/*
	 * 需要跟踪得业务类别
	 */
	public static class TraceType {
		public static long CREDIT = 1;
	}

	// 四舍五入方式
	public static class CountInterestType {
		// 四舍五入方式
		public static final long CalAfterRounding = 1; // 先舍入后累加

		public static final long CalBeforRounding = 2; // 先累加后舍入

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CalAfterRounding:
				strReturn = "先舍入后累加";
				break;
			case (int) CalBeforRounding:
				strReturn = "先累加后舍入";
				break;
			}
			return strReturn;
		}
	}

	// 担保收费通知单状态
	public static class AssureChargeNoticeStatus {
		// 担保收费通知单状态
		//public static final long SAVE = 1; // 已保存
		
		public static final long SUBMIT = 1; // 已保存
		
		public static final long CHECK = 3; // 已审核

		public static final long USED = 4; // 已使用

		public static final long CANCEL = 5; // 已取消

		public static final long REFUSE = 6; // 已拒绝
		
		public static final long APPROVALING = 20; // 审批中

		public static final long[] getAllCode() {
			long[] lTemp = {SUBMIT, CHECK, USED, CANCEL, REFUSE ,APPROVALING};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureChargeNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			//case (int) SAVE:
			//	strReturn = "已保存";
			//	break;
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
	}
    
	//融资租赁保证金收取通知单状态
	public static class RecognizanceNoticeStatus {
		public static final long DEL = 0; // 已删除
		
		public static final long SUBMIT = 1; // 已保存
		
		public static final long CHECK = 3; // 已审批

		public static final long USED = 4; // 已使用

		public static final long CANCEL = 5; // 已取消

		public static final long REFUSE = 6; // 已拒绝
		
		public static final long APPROVALING = 20; // 审批中

		public static final long[] getAllCode() {
			long[] lTemp = {DEL,SUBMIT, CHECK, USED, CANCEL, REFUSE ,APPROVALING};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$RecognizanceNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) DEL:
				strReturn = "已删除";
				break;
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
	}
	// 保后处理通知单状态
	public static class AssureManagementNoticeStatus {
		// 保后处理通知单状态
		//public static final long SAVE = 1; // 已保存
		
		public static final long SUBMIT = 1; // 已保存

		public static final long CHECK = 3; // 已审核

		public static final long USED = 4; // 已使用

		public static final long CANCEL = 5; // 已取消

		public static final long REFUSE = 6; // 已拒绝
		
		public static final long APPROVALING = 20; // 审批中

		public static final long[] getAllCode() {
			long[] lTemp = {SUBMIT, CHECK, USED, CANCEL, REFUSE, APPROVALING };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureManagementNoticeStatus",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			//case (int) SAVE:
			//	strReturn = "已保存";
			//	break;
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) USED:
				strReturn = "已使用";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;
			}
			return strReturn;
		}
	}

	// 保后处理方式
	public static class AssureManagementType {
		// 保后处理方式
		public static final long REPEAL = 1; // 到期/提前撤销

		public static final long COUNTERCLAIM = 2; // 索偿处理

		public static final long CANCEL = 3; // 取消担保

		public static final long[] getAllCode() {
			long[] lTemp = { REPEAL, COUNTERCLAIM, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AssureManagementType",
							officeID, currencyID);
		}

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) REPEAL:
				strReturn = "到期/提前撤销";
				break;
			case (int) COUNTERCLAIM:
				strReturn = "索偿处理";
				break;
			case (int) CANCEL:
				strReturn = "取消担保";
				break;
			}
			return strReturn;
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	// 资产回购合同状态
	public static class RepurchaseStatus {
		// 资产回购合同状态
		public static final long SUBMIT = 1; // 可购回

		public static final long FINISH = 2; // 已购回

		public static final long CANCEL = 3; // 已取消

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "可购回";
				break;
			case (int) FINISH:
				strReturn = "已购回";
				break;
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SUBMIT, FINISH, CANCEL };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$RepurchaseStatus",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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

	/**
	 * 将网银的贷款类型转换成信贷的贷款类型。例如网银委托贷款为3，信贷中为2
	 * 
	 * @param longTypID
	 *            网银的类型
	 * @return 信贷的类型
	 * @throws Exception
	 */
	public static long ConvertLoanTypeFromOBToLoan(long longTypeID)
			throws Exception {
		long rtn = -1;
		switch ((int) longTypeID) {
		case 1:
		case 2:
			rtn = 1;
			break;
		case 3:
			rtn = 2;
			break;
		case 4:
			// rtn=1;
			break;
		case 5:
			rtn = 3;
			break;
		case 6:
		case 7:
			rtn = 4;
			break;
		case 8:
			// rtn=1;
			break;
		case 9:
			// rtn=1;
			break;
		case 10:
			// rtn=1;
			break;
		case 11:
			// rtn=1;
			break;
		case 12:
			// rtn=1;
			break;
		case 14:
			// rtn=1;
			break;
		default:
			throw new Exception("无法识别的贷款类型");
		}
		return rtn;

	}

	// 利息计算方式（融资租赁）
	public static class InterestCountType {
		// 资产回购合同状态
		//modified by xiongfei 2010/05/17 将等本和等额改成了等额本金和等额本息
		public static final long AVERAGEPRINCIPAL = 1; // 等额本金

		public static final long AVERAGEAMOUNT = 2; // 等额本息

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) AVERAGEPRINCIPAL:
				strReturn = "等额本金";
				break;
			case (int) AVERAGEAMOUNT:
				strReturn = "等额本息";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AVERAGEPRINCIPAL, AVERAGEAMOUNT };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$InterestCountType",
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

		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty, long lOfficeID,
				long lCurrencyID) {
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
	// 工作类型 added by mzh_fu 2007/06/22
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
			String strContext="/NASApp/iTreasury-loan";
			return getWorkUrl(strContext, workType);
		}
	}
	
	/**
	 * 贷款分类类型 added by mzh_fu 2007/08/31
	 * 
	 */
	public static class AssortSetType {
		public static final long AREA = 1; // 地区分类

		public static final long INDUSTRY1 = 2;// 行业分类1

		public static final long INDUSTRY2 = 3;// 行业分类2
        //modify by xwhe 2008-06-10 
		public static final long INDUSTRY3 = 4;// 是否进行账户监管

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) AREA:
				strReturn = "地区分类";
				break;
			case (int) INDUSTRY1:
				strReturn = "行业分类1";
				break;
			case (int) INDUSTRY2:
				strReturn = "行业分类2";
				break;
			case (int) INDUSTRY3:
				strReturn = "是否进行账户监管";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AREA, INDUSTRY1, INDUSTRY2, INDUSTRY3 };
			return lTemp;
		}
		
		/**
		 * 根据Vector的ID获取贷款合同类型<p>
		 * 
		 * @param FieldID
		 * @param officeID
		 * @param currencyID
		 * @return
		 * @throws Exception
		 * 
		 * @author kaishao
		 */
		public static final String getContractName(long FieldID,long officeID,
				long currencyID) throws Exception{
			String str = "";
			str = new LoanContractAssortSettingBiz().getFieldname(FieldID,officeID,currencyID);
			if(str==null||"".equals(str)){
				str=LOANConstant.AssortSetType.getName((int)FieldID);
			}
			return (str);
		}

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
	}
	
	//Modify by leiyang 2007/09/04 同来往来MagnifierSQL报错所加
	//信用评级状态
	public static class ObjectiveSetting
	{
		public static final long RSAVE = 1;  //已保存
		public static final long RDELETE = -1; //已删除
		public static final long APPROVAL = 2; //审批中
		public static final long CHECK = 3; //已审批
		public static final long REFUSE = 4; //已拒绝
		
		public static final String getName(long creditId){
			String strReturn = ""; //初始化返回值
			
			switch ((int) creditId)
			{
				case (int) RSAVE :
					strReturn = "已保存";
					break;
				case (int) RDELETE :
					strReturn = "已删除";
					break;
				case (int) APPROVAL :
					strReturn = "审批中";
					break;
				case (int) CHECK :
					strReturn = "已审批";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
			}
			return strReturn;
		}
		
		public static long[] getAllCode(){
			long lTemp[] = null;
			lTemp = new long[4];
			lTemp[0] = RSAVE;
			lTemp[1] = APPROVAL;
			lTemp[2] = CHECK;
			lTemp[3] = REFUSE;
			return lTemp;
		}
		
		public static long[] getAllCodes(){
			long lTemp[] = null;
			lTemp = new long[2];
			lTemp[0] = RSAVE;
			lTemp[1] = APPROVAL;
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getAllCodes();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	 /**
     * 贷款业务是否进行额度控制
     * @author Administrator
     *
     */
	public static class ISRatingControl {
		
		public static final long YES = 1; //是

		public static final long NO = 0; // 否

		public static final String getName(long lCode) {
			
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) YES:
				strReturn = "是";
				break;
			case (int) NO:
				strReturn = "否";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { YES, NO };
			return lTemp;
		}
	
		/**
		 * 显示下拉列表(授信品种是否进行额度控制)
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	 /**
     * 控制方式
     * @author Administrator
     *
     */
	public static class ControlMode {
		
		public static final long RIGIDITY = 1; //刚性控制

		public static final long FLEXIBLE = 2; //柔性控制

		public static final String getName(long lCode) {
			
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) RIGIDITY:
				strReturn = "刚性控制";
				break;
			case (int) FLEXIBLE:
				strReturn = "柔性控制";
				break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { RIGIDITY, FLEXIBLE };
			return lTemp;
		}
	
		/**
		 * 显示下拉列表(授信品种是否进行额度控制)
		 * 
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();				
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	 /**
     * 贷款期限
     * @author Administrator
     */
	public static class LoanTerm {
		public static final long SIXMOUTH_BELOW			= 1;  //六个月以内（含）
		public static final long SIXMOUTH_ONEYEAR		= 2;  //六个月至一年
		public static final long ONEYEAR_THREEYEAR		= 3;  //一年至三年
		public static final long THREEYEAR_FIVEYEAR		= 4;  //三年至五年
		public static final long FIVEYEAR_ABOVE			= 5;  //五年以上
		
		public static final String getName(long lCode) {
			
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
				case (int) SIXMOUTH_BELOW:
					strReturn = "六个月以内（含）";
					break;
				case (int) SIXMOUTH_ONEYEAR:
					strReturn = "六个月至一年";
					break;
				case (int) ONEYEAR_THREEYEAR:
					strReturn = "一年至三年";
					break;
				case (int) THREEYEAR_FIVEYEAR:
					strReturn = "三年至五年";
					break;
				case (int) FIVEYEAR_ABOVE:
					strReturn = "五年以上";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SIXMOUTH_BELOW, SIXMOUTH_ONEYEAR, ONEYEAR_THREEYEAR, THREEYEAR_FIVEYEAR, FIVEYEAR_ABOVE };
			return lTemp;
		}
	
		/**
		 * 显示下拉列表(贷款期限)
		 * @param out
		 * @param strControlName     控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue 是否选中空白选项（值为“-1”就是选中）
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank 是否需要空白行
		 * @param strProperty 设置下拉框的其它属性（可为空字符串）
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			
			try
			{
				lArrayID = getAllCode();
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	
	/******************************************************************************************************/
	/*
	 * 授信常量，暂时放在LOANConstant中将来移植到CREDITConstant中
	 */
	
	public static class CreditType {
		
		public static final long ZY = 1; // 自营贷款

		//public static final long WT = 2; // 委托贷款

		public static final long TX = 3; // 贴现

		//public static final long ZGXE = 4; // 最高限额

		//public static final long YT = 5; // 银团贷款

		//public static final long ZTX = 6; // 转贴现

		//public static final long MFXD = 7; // 买方信贷

		public static final long DB = 8; // 担保

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ZY:
				strReturn = "自营贷款";
				break;
			case (int) TX:
				strReturn = "贴现";
				break;
			case (int) DB:
				strReturn = "担保";
				break;
			}
			return strReturn;
		}
		
		public static final long[] getAllCode(long officeID, long currencyID) {
			long[] creditType =Constant.getAllCode("com.iss.itreasury.loan.util.LOANConstant$CreditType", officeID, currencyID);
			return creditType != null ? creditType : (new long[0]);
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long officeID, long currencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(officeID, currencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	/*
	 * 授信模式
	 */
	public static class CreditModel {
	
		public static final long SIMPLE = 1; //单一法人授信

		public static final long GROUP = 2; //集团授信

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SIMPLE:
				strReturn = "单一法人授信";
				break;
			case (int) GROUP:
				strReturn = "集团授信";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SIMPLE, GROUP };
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要"全部项"
		 * @param isNeedBlank，是否需要空白行
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
	}
	
	/*
	 * 授信操作类型
	 */
	public static class OperationType {
		
		public static final long NEW = 1; //授信新增

		public static final long CHANGE = 2; //接信变更
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) NEW:
				strReturn = "授信新增";
				break;
			case (int) CHANGE:
				strReturn = "授信变更";
				break;	
			}
			return strReturn;
		}
		
		public static final long[] getAllCode() {
			long[] lTemp = { NEW, CHANGE };
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName,int nType, long lSelectValue, boolean isNeedAll,boolean isNeedBlank, String strProperty) 
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try{
				switch (nType) 
				{
					case 0:lArrayID = getAllCode();
						   break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
	}
	
	/*
	 * 授信是否共享标识
	 */
	public static class CreditShare {
		
		public static final long YES = 0; //共享，使用综合授信

		public static final long NO = 1; //不共享，占用品种授信
		
	}
	
	/*
	 * 授信变更操作符
	 */
	public static class OperationSign {
		
		public static final long ADDITION = 1; //加法

		public static final long SUBTRACTION = 2; //减法
		
		public static final String getSignName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ADDITION:
				strReturn = "+";
				break;
			case (int) SUBTRACTION:
				strReturn = "-";
				break;	
			}
			return strReturn;
		}
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) ADDITION:
				strReturn = "增加";
				break;
			case (int) SUBTRACTION:
				strReturn = "减少";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { ADDITION, SUBTRACTION };
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要"全部项"
		 * @param isNeedBlank，是否需要空白行
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
					strArrayName[i] = getSignName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	/*
	 * 授信控制方式
	 */
	public static class ControlType {
		
		public static final long RIGIDITY = 1; //刚性

		public static final long FLEXIBILITY = 2; //柔性

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) RIGIDITY:
				strReturn = "刚性";
				break;
			case (int) FLEXIBILITY:
				strReturn = "柔性";
				break;	
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { RIGIDITY, FLEXIBILITY };
			return lTemp;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要"全部项"
		 * @param isNeedBlank，是否需要空白行
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
	}
	
	/**
	 * @author afzhu
	 * 融资租金批量还款 ---结算方式
	 */
	public static class BalanceType
	{
		public static final long CURRENTACCOUNT = 1;//活期账户
		public static final long RECOGNIZANCEACCOUNT = 2;//保证金账户
		public static final long FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT = 3;//先活期后保证金
		public static final long CURRENTACCOUNTRECOGNIZANCEACCOUNTSCALE = 4;//活期与保证金按比例
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) CURRENTACCOUNT:
			    strReturn = "活期账户";
			    break;
			case (int) RECOGNIZANCEACCOUNT:
				strReturn = "保证金账户";
				break;
			case (int) FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT:
				strReturn = "先活期后保证金";
				break;
			case (int) CURRENTACCOUNTRECOGNIZANCEACCOUNTSCALE:
				strReturn = "活期与保证金按比例";
				break;
			}
			return strReturn;
		}
	}
	
	/**
	 * 授信额度状态
	 * @author Administrator
	 *
	 */
	public static class CreditFlowStatus {
		
		public static final long SAVE = 1;     //已保存
		
 		public static final long CHECK = 3;    //已审批
		
		public static final long APPROVALING = 20; //审批中
		
		public static final long ACTIVE = 6;   //已激活
		
		public static final long DELETE = 0;  //已删除

		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
			    strReturn = "已保存";
			    break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) ACTIVE:
				strReturn = "已激活";
				break;
			case (int) DELETE:
				strReturn = "已删除";
				break;
			case (int) APPROVALING:
			    strReturn = "审批中";
			    break;
			}
			return strReturn;
		}
		
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要"全部项"
		 * @param isNeedBlank，是否需要空白行
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName,
				int nType, long lSelectValue, boolean isNeedAll,
				boolean isNeedBlank, String strProperty) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			String[] strArrayValue = null;
			try {
				switch (nType) {
					case 0:
						lArrayID = new long[]{SAVE, APPROVALING, CHECK, ACTIVE};
						break;
				}
				strArrayName = new String[lArrayID.length];
				strArrayValue = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
					strArrayValue[i] = String.valueOf(lArrayID[i]);
				}
				showCommonList(out, strControlName, strArrayValue, strArrayName,
						String.valueOf(lSelectValue), isNeedAll, strProperty, isNeedBlank);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}
	
	
	// 利率调整状态
	public static class LoanRateAdjustStatus {
		// 贷款状态
		public static final long SAVE = 1; // 已保存

		public static final long SUBMIT = 2; // 已提交

		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝

		

		public static final long CANCEL = 0; // 已经取消
		
		public static final long APPROVALING = 20; //审批中		

		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SAVE:
				//modified by mzh_fu 2007/06/11,贷款未提交审批前只有一个状态"已保存"
//				strReturn = "撰写";
				strReturn = "已保存";
				break;
			case (int) SUBMIT:
				strReturn = "已提交";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			
			case (int) CANCEL:
				strReturn = "已取消";
				break;
			case (int) APPROVALING:
				strReturn = "审批中";
				break;				
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { SAVE, SUBMIT, CHECK, REFUSE, CANCEL };

			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanRateAdjustStatus",
					officeID, currencyID);
		}
	}

	//	 --状态
	public static class reportState
	{
		//状态
		public static final long SAVE = 1;//保存
		public static final long SUBMIT = 2;//提交
		public static final long DELETE = 3;//删除
		public static final long CHECKING = 4;//审核中
		public static final long CHECK = 5;//审核通过
		public static final long REFUSE = -2;//已拒绝
		public static final long INVALIDATION = 7;//失效
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "撰写";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) DELETE :
					strReturn = "删除";
					break;
				case (int) CHECKING :
					strReturn = "审核中";
					break;
				case (int) CHECK :
					strReturn = "已审核";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) INVALIDATION :
					strReturn = "失效";
					break;
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { SAVE, SUBMIT ,DELETE ,CHECKING,CHECK,REFUSE,INVALIDATION };
			return lTemp;
		}
		
	}


	public static class GageType
	{
		public static final long GYTDSYQ = 1; //国有土地使用权
		public static final long FWSYQ = 2; //房屋所有权
		public static final long JQSB = 3; //机器设备
		public static final long QTCC = 4; //其他财产
		public static final long XYBZ = 5; //信用保证
		public static final long HP = 6; //汇票
		public static final long BP = 7; //本票
		public static final long ZP = 8; //支票
		public static final long ZJ = 9; //债卷
		public static final long CD = 10; //存单
		public static final long TD = 11; //提单
		public static final long GPGQ = 12; //股票、股权
		public static final long YSK = 13; //应收款
		public static final long QCHGZ = 14; //汽车合格证
		public static final long QTQL = 15; //其它权利
		public static final long BZJ = 16; //保证金
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) GYTDSYQ :strReturn = "国有土地使用权"; break;
				case (int) FWSYQ   :strReturn = "房屋所有权";   break;
				case (int) JQSB    :strReturn = "机器设备";   break;
				case (int) QTCC    :strReturn = "其他财产";   break;
				case (int) XYBZ    :strReturn = "保证";  break;//原为信用保证
				case (int) HP      :strReturn = "汇票";   break;
				case (int) BP      :strReturn = "本票";   break;
				case (int) ZP      :strReturn = "支票";   break;
				case (int) ZJ      :strReturn = "债卷";  break;
				case (int) CD      :strReturn = "存单";   break;
				case (int) TD      :strReturn = "提单";   break;
				case (int) GPGQ    :strReturn = "股票";   break;
				case (int) YSK     :strReturn = "应收款";  break;
				case (int) QCHGZ   :strReturn = "汽车合格证";   break;
				case (int) QTQL    :strReturn = "其它权利";   break;
				case (int) BZJ     :strReturn = "保证金";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()//权证入库申请－查询担保品信息--抵押
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,HP,BP,ZP,CD,GPGQ,QCHGZ};
			return longTemp;
		}
		public static final long[] getDiYa()//抵押品属性
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,JQSB,QTCC};
			return longTemp;
		}
		public static final long[] getZhiYa()//质押品属性
		{
			long[] longTemp = {HP,BP,ZP,ZJ,CD,TD,GPGQ,YSK,QCHGZ,QTQL};
			return longTemp;
		}
		public static final long[] getXinYong()//信用保证属性
		{
			long[] longTemp = {XYBZ};
			return longTemp;
		}
		public static final long[] getBaoZhengJin()//保证金属性
		{
			long[] longTemp = {BZJ};
			return longTemp;
		}
		public static final long[] getNull()
		{
			long[] longTemp = {};
			return longTemp;
		}
		public static final long[] getAllCode()
		{
			long[] longTemp = {GYTDSYQ,FWSYQ,JQSB,QTCC,XYBZ,HP,BP,ZP,ZJ,CD,TD,GPGQ,YSK,QCHGZ,QTQL,BZJ};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1:
						lArrayID = getDiYa();
						break;
					case 2:
						lArrayID = getZhiYa();
						break;
					case 3:
						lArrayID = getXinYong();
						break;
					case 4:
						lArrayID = getBaoZhengJin();
						break;
					case 5:
						lArrayID = getNull();
						break;
					case 6:
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class MainTenanceAction
	{
		
		public static final long MCHECKIN = 1; //信息登记
		public static final long MAINTENANCE = 2; //信息维护
		public static final long MDELETE = 3; //信息删除

		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) MCHECKIN :strReturn = "信息登记"; break;
				case (int) MAINTENANCE   :strReturn = "信息维护";   break;
				case (int) MDELETE   :strReturn = "信息删除";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()//权证入库申请－查询担保品信息担保品状态
		{
			long[] longTemp = {MCHECKIN,MAINTENANCE,MDELETE};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class GageStatus
	{
		
		public static final long INVALID = 0; //无效(删除)
		public static final long SAVE = 1; //已登记
		public static final long SUBMIT = 2; //已提交
		public static final long CHECKING = 3; //审核中
		public static final long BECOME_EFFECTIVE = 4;	//已使用
		public static final long BECOME_INVALID = 5;	//已失效
		public static final long REFUSE = -2; //已拒绝
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
			    case (int) REFUSE :strReturn = "已拒绝"; break;
				case (int) SAVE :strReturn = "已登记"; break;
				case (int) SUBMIT   :strReturn = "已提交";   break;
				case (int) CHECKING   :strReturn = "审核中";   break;
				case (int) BECOME_EFFECTIVE   :strReturn = "已使用";   break;
				case (int) BECOME_INVALID   :strReturn = "已失效";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()//权证入库申请－查询担保品信息担保品状态
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,BECOME_INVALID};
			return longTemp;
		}
		public static final long[] getAllCodeI()//权证入库申请－查询担保品信息担保品状态
		{
			long[] longTemp = {SAVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()//担保品信息维护－查询担保品信息担保品状态
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE};
			return longTemp;
		}
		public static final long[] getAllCodeIII()//担保品－查询
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,REFUSE,SUBMIT,CHECKING,BECOME_INVALID};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;	
					case 2 :
						lArrayID = getAllCode();
						break;
					case 3 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }

	public static class WarrantStatus
	{
		public static final long REFUSE = -2; //已拒绝
		public static final long SAVE = 1;//已登记
		public static final long SUBMIT = 2; //已提交
		public static final long CHECKING = 3; //审核中
		public static final long BECOME_EFFECTIVE = 4; //已入库
		public static final long BECOME_INVALID = 5;   //已出库
		public static final long LEND = 6;	           //已出借
		public static final long RESTORE = 7;	       //已归还
		public static final long INVALID = 0; //无效(删除)
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
			    case (int) REFUSE   :strReturn = "已拒绝";   break;
			    case (int) SAVE   :strReturn = "已登记";   break;
			    case (int) SUBMIT   :strReturn = "已提交";   break;
			    case (int) CHECKING   :strReturn = "审核中";   break;
				case (int) BECOME_EFFECTIVE   :strReturn = "已入库";   break;
				case (int) BECOME_INVALID   :strReturn = "已出库";   break;
				case (int) LEND   :strReturn = "已出借";   break;
				case (int) RESTORE   :strReturn = "已归还";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()
		{
			long[] longTemp = {BECOME_EFFECTIVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()
		{
			long[] longTemp = {LEND};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {SAVE,BECOME_EFFECTIVE,BECOME_INVALID,LEND};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;
					case 2 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }

	public static class GageProperty
	{
		public static final long CREDIT = 1; //信用保证
		public static final long PLEDGE = 2; //抵押
		public static final long IMPAWN = 3; //质押
		public static final long RECOGNIZANCE = 4; //保证金
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) CREDIT :
					strReturn = "保证";//原为信用保证
					break;
				case (int) PLEDGE :
					strReturn = "抵押品";
					break;
				case (int) IMPAWN :
					strReturn = "质押品";
					break;
				case (int) RECOGNIZANCE :
					strReturn = "保证金";
					break;
			}
			return strReturn;
		}
		
		
		
		public static final long[] getAllCodeI()
		{
			long[] longTemp = {CREDIT, PLEDGE, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeII()
		{
			long[] longTemp = {PLEDGE, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {PLEDGE, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		//add by wwcheng 10-01-11
		public static final long[] getAllCodeIV()
		{
			long[] longTemp = {PLEDGE};
			return longTemp;
		}
		public static final long[] getAllCodeV()
		{
			long[] longTemp = {IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVI()
		{
			long[] longTemp = {RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVII()
		{
			long[] longTemp = {PLEDGE, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVIII()
		{
			long[] longTemp = {IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		
		public static final long[] getAllCodeIVV()
		{
			long[] longTemp = {CREDIT};
			return longTemp;
		}
		public static final long[] getAllCodeIIVV()
		{
			long[] longTemp = {CREDIT, PLEDGE};
			return longTemp;
		}
		public static final long[] getAllCodeIIIVV()
		{
			long[] longTemp = {CREDIT, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVVV()
		{
			long[] longTemp = {CREDIT, RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVVVI()
		{
			long[] longTemp = {CREDIT, PLEDGE, IMPAWN};
			return longTemp;
		}
		public static final long[] getAllCodeVVVII()
		{
			long[] longTemp = {CREDIT, PLEDGE,  RECOGNIZANCE };
			return longTemp;
		}
		public static final long[] getAllCodeVVVIII()
		{
			long[] longTemp = {CREDIT, IMPAWN, RECOGNIZANCE };
			return longTemp;
		}
		
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;
					case 2 :
						lArrayID = getAllCodeIII();
						break;
					//add  by wwcheng 10-01-11
					case 3 :
						lArrayID = getAllCodeIV();
						break;
					case 4 :
						lArrayID = getAllCodeV();
						break;
					case 5 :
						lArrayID = getAllCodeVI();
						break;
					case 6 :
						lArrayID = getAllCodeVII();
						break;
					case 7 :
						lArrayID = getAllCodeVIII();
						break;
					case 8 :
						lArrayID = getAllCodeIVV();
						break;
					case 9 :
						lArrayID = getAllCodeIIVV();
						break;
					case 10 :
						lArrayID = getAllCodeIIIVV();
						break;
					case 11 :
						lArrayID = getAllCodeVVV();
						break;
					case 12 :
						lArrayID = getAllCodeVVVI();
						break;
					case 13 :
						lArrayID = getAllCodeVVVII();
						break;
					case 14 :
						lArrayID = getAllCodeVVVIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);//不需排序
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		
	}

	public static class StatusType
	{
		//
		public static final long UNCHECK = 1; //未审核
		public static final long CHECKED = 2; //已审核
		
		//public static final long C = 5; //已审核
		//public static final long D = -2; //已拒绝
		//增加删除状态
		public static final long E = 0; //已删除
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) UNCHECK :
					strReturn = "未审核";
					break;
				case (int) CHECKED :
					strReturn = "已审核";
					break;
//				case (int) C :
//					strReturn = "已审核";
//					break;
//				case (int) D :
//					strReturn = "已拒绝";
//					break;
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { UNCHECK, CHECKED };
			return lTemp;
		}
		public static final long[] getAllApproveCode()
		{
			long[] lTemp = { CHECKED };
			return lTemp;
		}
		public static final long[] getAllSelectCode()
		{
			long[] lTemp = { UNCHECK };
			return lTemp;
		}
//		public static final long[] getAllSelectCode2()
//		{
//			long[] lTemp = { A, B, C ,D};
//			return lTemp;
//		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getAllApproveCode();
						break;
					case 2 :
						lArrayID = getAllSelectCode();
						break;
//					case 3 :
//						lArrayID = getAllSelectCode2();
//						break;
						

				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}


	public static class WarrantApplyStatus
	{
		public static final long REFUSE = -2; //已拒绝
		public static final long INVALID = 0; //无效(删除)
		public static final long SAVE = 1; //已登记
		public static final long SUBMIT = 2; //已提交
		public static final long CHECKING = 3; //审核中
		public static final long CHECK = 4; //已审核
		public static final long BECOME_INVALID = 5;//已失效
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :strReturn = "已保存"; break;
				case (int) SUBMIT   :strReturn = "已提交";   break;
				case (int) CHECK   :strReturn = "已审核";   break;
				case (int) REFUSE   :strReturn = "已拒绝";   break;
				case (int) BECOME_INVALID   :strReturn = "已失效";   break;
				case (int) CHECKING   :strReturn = "审核中";   break;
			}
			return strReturn;
		}
		public static final long[] getAllCodeI()//权证入库申请－查询担保品信息担保品状态
		{
			long[] longTemp = {SAVE};
			return longTemp;
		}
		public static final long[] getAllCodeII()//担保品信息维护－查询担保品信息担保品状态
		{
			long[] longTemp = {SUBMIT,CHECKING};
			return longTemp;
		}
		public static final long[] getAllCodeIII()
		{
			long[] longTemp = {SAVE,SUBMIT,CHECK,CHECKING,REFUSE};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCodeI();
						break;
					case 1 :
						lArrayID = getAllCodeII();
						break;	
					case 2 :
						lArrayID = getAllCodeIII();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


	public static class WarrantApplyType
	{
		
		public static final long  RUKU_APPLY     = Constant.ApprovalAction.DBPGL_1; //入库
		public static final long  CHUKU_APPLY    = Constant.ApprovalAction.DBPGL_2;	//出库
		public static final long  CHUJIE_APPLY   = Constant.ApprovalAction.DBPGL_3;	//出借
		public static final long  GUIHUAN_APPLY  = Constant.ApprovalAction.DBPGL_4;	//归还
		
		public static final String getName(long lCode)throws Exception
		{
			return Constant.ApprovalAction.getName(lCode)+"申请";
		}
		public static final long[] getAllCode()
		{
			long[] longTemp = {RUKU_APPLY,CHUKU_APPLY,CHUJIE_APPLY,GUIHUAN_APPLY};
			return longTemp;
		}
		public static final long[] getRUKU_APPLYCode()
		{
			long[] longTemp = {RUKU_APPLY};
			return longTemp;
		}
		public static final long[] getCHUKU_APPLYCode()
		{
			long[] longTemp = {CHUKU_APPLY};
			return longTemp;
		}
		public static final long[] getCHUJIE_APPLYCode()
		{
			long[] longTemp = {CHUJIE_APPLY};
			return longTemp;
		}
		public static final long[] getGUIHUAN_APPLYCode()
		{
			long[] longTemp = {GUIHUAN_APPLY};
			return longTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case (int) RUKU_APPLY:
						lArrayID = getRUKU_APPLYCode();
						break;
					case (int) CHUKU_APPLY :
						lArrayID = getCHUKU_APPLYCode();
						break;
					case (int) CHUJIE_APPLY :
						lArrayID = getCHUJIE_APPLYCode();
						break;
					case (int) GUIHUAN_APPLY :
						lArrayID = getGUIHUAN_APPLYCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
    }


		//授信客户评分状态
	public static class LoanCreditgrade
	{

		public static final long SAVE = 1; //已制单
		public static final long YISHIYONG = 2; //已保存
		public static final long YIGUOQI = 3; //已失效
		public static final long SHENPIZHONG = 4; //审批中
		public static final long YISHENPI = 5; //已审批
		public static final long YIFUHE = 7; //已生效
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "已保存";
					break;
				case (int) YISHIYONG :
					strReturn = "已保存";
					break;
				case (int) YIGUOQI :
					strReturn = "已失效";
				    break;
				case (int) YIFUHE :
					strReturn = "已生效";
				    break;
				case (int) SHENPIZHONG :
					strReturn = "审批中";
				    break;
				case (int) YISHENPI :
					strReturn = "已生效";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { SAVE, YISHIYONG, YIGUOQI,YIFUHE,SHENPIZHONG,YISHENPI };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$LoanCreditgrade", officeID,
					currencyID);
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
		

	//贷后调查报告：显示年、季度
	public static class YearList
	{
		//
		public static final long A = 2009; //2009
		public static final long B = 2010; //2010
		public static final long C = 2011; //2011
		public static final long D = 2012; //2012
		public static final long E = 2013; //2013
		public static final long F = 2014; //2014
		public static final long G = 2015; //2015
		public static final long H = 2016; //2016
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) A :
					strReturn = "2009";
					break;
				case (int) B :
					strReturn = "2010";
					break;
				case (int) C :
					strReturn = "2011";
					break;
				case (int) D :
					strReturn = "2012";
					break;
				case (int) E :
					strReturn = "2013";
					break;
				case (int) F :
					strReturn = "2014";
					break;
				case (int) G :
					strReturn = "2015";
					break;
				case (int) H :
					strReturn = "2016";
					break;
					
		
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { A, B ,C ,D ,E,F,G,H };
			return lTemp;
		}
		
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)


				{
					case 0 :
						lArrayID = getAllCode();
						break;

				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	/**
	 * 检查范围 季度 月
	 * @author lllliu
	 *
	 */
	public static class CheckSpace
	{
		public static final long JANUARY = 1; 
		public static final long FEBRUARY = 2;
		public static final long MARCH = 3; 
		public static final long APRIL = 4; 
		public static final long MAY = 5; 
		public static final long JUNE = 6; 
		public static final long JULY = 7; 
		public static final long AUGUST = 8; 
		public static final long SEPTEMBER = 9; 
		public static final long OCTOBER = 10;
		public static final long NOVEMBER = 11; 
		public static final long DECEMBER = 12; 
		
		public static final long ONEQUARTER = 14;
		public static final long TWOQUARTER = 15;
		public static final long THREEQUARTER = 16; 
		public static final long FOURQUARTER = 17;
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)

			{
				case (int) ONEQUARTER :
					strReturn = "第一季度";
					break;
				case (int) TWOQUARTER :
					strReturn = "第二季度";
					break;
				case (int) THREEQUARTER :
					strReturn = "第三季度";
					break;
				case (int) FOURQUARTER :
					strReturn = "第四季度";
					break;
					
				case (int) JANUARY :
					strReturn = "1";
					break;
				case (int) FEBRUARY :
					strReturn = "2";
					break;
				case (int) MARCH :
					strReturn = "3";
					break;
				case (int) APRIL :
					strReturn = "4";
					break;
				case (int) MAY :
					strReturn = "5";
					break;
				case (int) JUNE :
					strReturn = "6";
					break;
				case (int) JULY :
					strReturn = "7";
					break;
				case (int) AUGUST :
					strReturn = "8";
					break;
				case (int) SEPTEMBER :
					strReturn = "9";
					break;
				case (int) OCTOBER :
					strReturn = "10";
					break;
				case (int) NOVEMBER :
					strReturn = "11";
					break;
				case (int) DECEMBER :
					strReturn = "12";
					break;
				
			}
			return strReturn;
		}

		public static final long[] getAllQuArterCode()
		{
			long[] lTemp = { ONEQUARTER, TWOQUARTER, THREEQUARTER,FOURQUARTER};
			return lTemp;
		}
		public static final long[] getAllMonthCode()
		{
			long[] lTemp = {JANUARY,FEBRUARY,MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER,DECEMBER};
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)


				{
					case 1 :
						lArrayID = getAllQuArterCode();
						break;
					case 2 :
						lArrayID = getAllMonthCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	  
	}

	/**
	 * 贷款检查频率
	 * @author lllliu
	 *
	 */
	public static class CheckFrequency
	{
		//资产回购合同状态
		public static final long QUARTER = 1; //每季度
		public static final long MONTH = 2; //每月
		public static final long TEMPORARY = 3; //临时
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) QUARTER :
					strReturn = "每季度";
					break;
				case (int) MONTH :
					strReturn = "每月";
					break;
				case (int) TEMPORARY :
					strReturn = "临时";
					break;



			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { QUARTER, MONTH, TEMPORARY };
			return lTemp;
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;



				}
				strArrayName = new String[lArrayID.length];
				
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
//	财务分析(年)
	public static class FinancialAnalysisYear
	{

		public static final long q = 2001; //2001
		public static final long w = 2002; //2002
		public static final long e = 2003; //2003
		public static final long r = 2004; //2004
		public static final long t = 2005; //2005
		public static final long y = 2006; //2006
		public static final long u = 2007; //2007
		public static final long i = 2008; //2008
		public static final long o = 2009; //2009
		public static final long a = 2010; //2010
		public static final long s = 2011; //2011
		public static final long d = 2012; //2012
		public static final long f = 2013; //2013
		public static final long g = 2014; //2014
		public static final long h = 2015; //2015
		public static final long j = 2016; //2016
		public static final long k = 2017; //2017
		public static final long l = 2018; //2018
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) q :
					strReturn = "2001";
					break;
				case (int) w :
					strReturn = "2002";
					break;
				case (int) e :
					strReturn = "2003";
					break;
				case (int) r :
					strReturn = "2004";
					break;
				case (int) t :
					strReturn = "2005";
					break;
				case (int) y :
					strReturn = "2006";
					break;
				case (int) u :
					strReturn = "2007";
					break;
				case (int) i :
					strReturn = "2008";
					break;
				case (int) o :
					strReturn = "2009";
					break;
				case (int) a :
					strReturn = "2010";
					break;
				case (int) s :
					strReturn = "2011";
					break;
				case (int) d :
					strReturn = "2012";
					break;
				case (int) f :
					strReturn = "2013";
					break;
				case (int) g :
					strReturn = "2014";
					break;
				case (int) h :
					strReturn = "2015";
					break;
				case (int) j :
					strReturn = "2016";
					break;
				case (int) k :
					strReturn = "2017";
					break;
				case (int) l :
					strReturn = "2018";
					break;
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { q, w, e,r,t,y,u,i,o,a,s,d,f,g,h,j,k,l };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$FinancialAnalysisYear", officeID,
					currencyID);
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	
//	财务分析(年)  
	public static class FinancialAnalysisYue
	{

		public static final long q = 1; //2001
		public static final long w = 2; //2002
		public static final long e = 3; //2003
		public static final long r = 4; //2004
		public static final long t = 5; //2005
		public static final long y = 6; //2006
		public static final long u = 7; //2007
		public static final long i = 8; //2008
		public static final long o = 9; //2009
		public static final long a = 10; //2010
		public static final long s = 11; //2011
		public static final long d = 12; //2012
		
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) q :
					strReturn = "1";
					break;
				case (int) w :
					strReturn = "2";
					break;
				case (int) e :
					strReturn = "3";
					break;
				case (int) r :
					strReturn = "4";
					break;
				case (int) t :
					strReturn = "5";
					break;
				case (int) y :
					strReturn = "6";
					break;
				case (int) u :
					strReturn = "7";
					break;
				case (int) i :
					strReturn = "8";
					break;
				case (int) o :
					strReturn = "9";
					break;
				case (int) a :
					strReturn = "10";
					break;
				case (int) s :
					strReturn = "11";
					break;
				case (int) d :
					strReturn = "12";
					break;
				
			}
			return strReturn;
		}

		public static final long[] getAllCode()
		{
			long[] lTemp = { q, w, e,r,t,y,u,i,o,a,s,d };
			return lTemp;
		}
		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.loan.util.LOANConstant$FinancialAnalysisYue", officeID,
					currencyID);
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
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode(lOfficeID,lCurrencyID);
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	/**
	 * 贷后调查报告审批状态
	 * @author fulihe
	 *
	 */
	public static class AfterCreditStatus {
		
		public static final long SUBMIT = 1; // 已保存

		
		
		public static final long CHECK = 3; // 已审批

		public static final long REFUSE = -2; // 已拒绝
        
		public static final long INVALID = -1;//取消申请
		public static final long CANCELAPPLY = 0; //取消审批
		
		public static final long APPROVALING = 20; //审批中
		
		public static final String getName(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) SUBMIT:
				strReturn = "已保存";
				break;
			case (int) CHECK:
				strReturn = "已审批";
				break;
			case (int) REFUSE:
				strReturn = "已拒绝";
				break;
			
			case (int) CANCELAPPLY:
				strReturn = "取消审批";
				break;
			
			case (int) APPROVALING:
			strReturn = "审批中";
			break;
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = new long[5];
			lTemp[0]= SUBMIT;
			lTemp[1]= CHECK;
			lTemp[2]= REFUSE;
			lTemp[3]=CANCELAPPLY;
			lTemp[4]=APPROVALING;
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.loan.util.LOANConstant$AfterCreditStatus",
							officeID, currencyID);
		}

	}
	
	//贷款申请帐户类型
	public static class LoanClientType{
		public static final long INTERIOR = 1;  //内部客户
		public static final long EXTERIOR = 2;  //外部客户
	}
	
	//商业承兑汇票通知状态 wjdu
	public static class InformStatus
	{
		public static final long DELETE = 0;//删除0
		public static final long SUBMIT = 2;//提交1
		public static final long SAVE = 1;//保存2
		public static final long CHECKING = 5;//待审核(审核中)3(贷款通知单中没有)
		public static final long CHECK = 3;//已审核(审核通过)4
		public static final long REFUSE = -2;//拒绝5
		public static final long USED = 4; //"已使用";
		public static final long FINISH = 6; //"已结束";7(贷款通知单中没有)
		public static final long RETURN = 7;//退回修改
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int)	 DELETE :strReturn = "删除"; break;
				case (int)   SUBMIT :strReturn = "已提交";   break;
				case (int)   SAVE  :strReturn = "撰写";   break;
				case (int)   CHECKING  :strReturn = "已提交";      break;
				case (int)   CHECK :strReturn = "已审核";      break;
				case (int)   REFUSE :strReturn = "拒绝";      break;
				//case (int)   UPDATE :strReturn = "退回修改";      break;
				case (int)   USED :strReturn = "已使用";      break;
				case (int)   FINISH :strReturn = "已结束";      break;
			}
			return strReturn;
		}
		
	}
	
	public static class RemindType
	{
		public static final String PLAN = "plan";//根据还款计划提醒
		public static final String NOTICE = "notice";//根据还款通知单提醒
		
	}
	

}