/*
 * Created on 2006-12-6
 * 
 */
package com.iss.itreasury.craftbrother.util;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.craftbrother.setting.dao.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Constant.ModuleType;
public class CRAconstant  extends com.iss.itreasury.util.Constant
{
	/*业务性质
	 * 业务性质比较特殊，在业务性质表中只定义了名称，没有定义ID，因此通过名称返回常量中定义的ID
	 * */
	public static class SameBusinessAttribute
	{
		public static final long SAME_BUSINESS = 1; //1,资产转让业务
		public static final long FUND_ATTORN = 2; //2，资金拆借业务
		public static final long DISCOUNT = 6; //6，转贴现业务
		public static final long OTHER = 3; //8，授信设置
		
		public static final long getIDByName(String name)
		{
			if (name.compareToIgnoreCase("资产转让业务") == 0)
				return SAME_BUSINESS;
			else if (name.compareToIgnoreCase("资金拆借业务") == 0)
				return FUND_ATTORN;
			else if (name.compareToIgnoreCase("转贴现业务") == 0)
				return DISCOUNT;
			else if (name.compareToIgnoreCase("授信设置") == 0)
				return OTHER;
			return -1;
		}
		public static final long[] getCodeForApproval(){
			return new long[]{SAME_BUSINESS,FUND_ATTORN,DISCOUNT,OTHER};
		}
		
		public static final long[] getCodeForCodeRule(){
			return new long[]{DISCOUNT,FUND_ATTORN,SAME_BUSINESS};
		}
		
        public static final long[] getCraAllCode(long lOfficeID,long lCurrencyID)
		{
        	LoanTypeRelationDao dao = new LoanTypeRelationDao();
			return dao.getAllSetLoanTypeID(lOfficeID,lCurrencyID);
		}
        
		public static final String getName(long ID){
			if (ID==SAME_BUSINESS)
				return "信贷资产转让";
			else if (ID==FUND_ATTORN)
				return "资金拆借";
			else if (ID==DISCOUNT) 
				return "转贴现";		
			else if (ID==OTHER) 
				return "授信设置";		
			return "-1";
		}
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long[] lArrayID){
			
			String[] strArrayName = null;
			try
			{
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
	 * 交易状态
	 * @author cqzhu
	 *
	 */
	public static class TransactionStatus
	{ 
	    public static final long DELETED = 0; //已删除
        public static final long SAVE = 1; //已保存（未审批）
        public static final long APPROVALING = 20; //审批中
        public static final long APPROVALED = 3;//已审批
        public static final long REFUSED = 4; //已拒绝
        public static final long USED = 5; //已使用
        
        public static final String getName(long ID)
		{   
			String strReturn = ""; //初始化返回值
			switch ((int)ID)
			{
				case (int)DELETED:
					strReturn = "已删除"; 
					break;
				case (int)SAVE:
					strReturn = "已保存";
					break;
				case (int)APPROVALING:
					strReturn = "审批中"; 
					break;
				case (int)APPROVALED:
					strReturn = "已审批";
					break;	
				case (int)REFUSED:
					strReturn = "已拒绝";
					break;	
				case (int)USED:
					strReturn = "已使用";
					break;
			}
			return strReturn;
		}
        public static final long[] getAllCode()
        {
            long[] lTemp =
            { DELETED, SAVE,APPROVALING,APPROVALED,USED};
            return lTemp;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.craftbrother.util.CRAconstant$TransactionStatus", officeID,
					currencyID);
		}
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName,
         *            控件名称
         * @param nType，控件类型
         *            （0，显示全部）
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
                    case 0:
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
                Log.print(ex.toString());
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
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
                        break;
                    case 1:
                        lArrayID = new long[]{SAVE,APPROVALING,APPROVALED};
                        break;
                    case 2:
                        lArrayID = new long[]{SAVE,APPROVALING,APPROVALED,USED};
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
                Log.print(ex.toString());
            }
        }
	
	}
	
	//工作类型 added by xwhe 2007/09/10
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//待处理业务
		
		public static final long CURRENTWORK = 2; //待办任务

		public static final long HISTORYWORK = 3;//已办任务

		public static final long FINISHEDWORK = 4;//办结任务
		
		public static final long REFUSEWORK = 5;//拒绝业务
		
		public static final long CANCELAPPROVAL = 6;//取消审批		
	}
	
	
	/**
	 * 交易类型（在授信设置的时候使用，并作为操作类型设置审批流）
	 * @author cqzhu
	 *
	 */
	public static class TransactionType {
		public static final long LOAN_APPLY = 1;  //贷款申请
        public static final long LOAN_CONTRACT = 2; //贷款合同
        public static final long CAPITAL_IN_APPLY = 2001; //资金拆入申请
        public static final long CAPITAL_OUT_APPLY = 2002; //资金拆出申请
        public static final long CAPITAL_APPLY = 2000; //资金拆借申请
        public static final long CAPITAL_LANDING_NOTICE = 2003; //资金拆借业务通知单
        public static final long CAPITAL_EXCHANGE_LISTS = 2004; //资金拆借交割单
        public static final long TRANSDISCOUNT_CREDENCE = 17; //转贴现凭证
        public static final long TRANSDISCOUNT_REPURCHASECREDENCE = 18; //转贴现回购凭证
		public static final long ZTX_INVEST_REPURCHASE = 6; //转贴现买入（回购）
		public static final long ZTX_INVEST_BREAK = 7; //转贴现买入（买断）
		public static final long ZTX_AVERAGE_REPURCHASE = 8; //转贴现卖出（回购）
		public static final long ZTX_AVERAGE_BREAK = 9; //转贴现卖出（买断）
		public static final long CAPITAL_IN = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN ; //资金拆入
		public static final long CAPITAL_OUT = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT; //资金拆出
		public static final long AVERAGE_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY; //买入（回购）
		public static final long BREAK_INVEST = SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK; //买入（买断）
		public static final long REPURCHASE_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY ; //卖出（回购）
		public static final long BREAK_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY; //卖出（买断）
        public static final long SAME_BUSINESS_APPLY = 3501; //资产转让申请
        public static final long SAME_BUSINESS_CONTRACT = 3502; //资产转让合同
        public static final long SAME_BUSINESS_NOTICE = 3503; //资产转让业务通知单
        public static final long SAME_BUSINESS_LOANCONTRACT = 3504; //资产转让贷款合同
        public static final long CAPITAL_IN_REPAY = 1302; //资金拆入返款
        public static final long CAPITAL_OUT_REPAY = 1304; //资金拆出返款
        public static final long PAYBACK_NOTIFY = 7103; //合同卖出购回（回购）
        public static final long ACCEPT_NOTIFY = 7107; //合同买入购回（回购）
        public static final long INTEREST_PAYMENT = 7104; //利息支付通知单
        
        public static final long CAPITAL_PAYMENT = 7110; //支付转让款项
        public static final long INTEREST_PAYBACK = 7111; //利息收回通知单
        public static final long REPURCHASE_CAPITAL = 7112; //买入（回购）购回通知单
        public static final long ACCEPT_CAPITAL = 7113; //卖出（回购）购回通知单
        public static final long CAPITAL_PAYBACK = 7114; //收到转让款项
        
        public static final long INVEST_REPURCHASE = 12; //转贴现买入（回购）
		public static final long INVEST_BREAK = 11; //转贴现买入（买断）
		public static final long AVERAGE_REPURCHASE = 22; //转贴现卖出（回购）
		public static final long AVERAGE_BREAK = 21; //转贴现卖出（买断）
        
		public static final String getName(long ID)
		{   
			String strReturn = ""; //初始化返回值
			switch ((int)ID)
			{
				case (int)LOAN_APPLY:
					strReturn = "转贴现申请"; 
					break;
				case (int)LOAN_CONTRACT:
					strReturn = "转贴现合同"; 
					break;
				case (int)CAPITAL_IN_APPLY:
					strReturn = "资金拆入申请"; 
					break;
				case (int)CAPITAL_OUT_APPLY:
					strReturn = "资金拆出申请";
					break;
				case (int)CAPITAL_APPLY:
					strReturn = "资金拆借申请"; 
				    break;
				case (int)CAPITAL_EXCHANGE_LISTS:
					strReturn = "资金拆借交割单"; 
				    break;				
				case (int)CAPITAL_LANDING_NOTICE:
					strReturn = "资金拆借通知单"; 
					break;
				case (int)TRANSDISCOUNT_CREDENCE:
					strReturn = "转贴现凭证"; 
					break;
				case (int)TRANSDISCOUNT_REPURCHASECREDENCE:
					strReturn = "转贴现回购凭证";
					break;					
				case (int)ZTX_INVEST_REPURCHASE:
					strReturn = "转贴现买入（回购）";
					break;
				case (int)ZTX_INVEST_BREAK:
					strReturn = "转贴现买入（买断）";
					break;
				case (int)ZTX_AVERAGE_REPURCHASE:
					strReturn = "转贴现卖出（回购）";
					break;
				case (int)ZTX_AVERAGE_BREAK:
					strReturn = "转贴现卖出（买断）";
					break;
				case (int)CAPITAL_IN:
					strReturn = "资金拆入"; 
					break;
				case (int)CAPITAL_OUT:
					strReturn = "资金拆出";
					break;
				case (int)REPURCHASE_NOTIFY:
					strReturn = "卖出（回购）"; 
					break;					
				case (int)AVERAGE_NOTIFY:
					strReturn = "买入（回购）"; 
					break;
				case (int)BREAK_NOTIFY:
					strReturn = "卖出（买断）";
					break;
				case (int)BREAK_INVEST:
					strReturn = "买入（买断）";
					break;
				case (int)SAME_BUSINESS_APPLY:
					strReturn = "资产转让申请"; 
					break;
				case (int)SAME_BUSINESS_CONTRACT:
					strReturn = "资产转让合同";
					break;
				case (int)SAME_BUSINESS_NOTICE:
					strReturn = "资产转让通知单";
					break;
				case (int)SAME_BUSINESS_LOANCONTRACT:
					strReturn = "资产转让贷款合同";
				    break;
				case (int)CAPITAL_IN_REPAY:
					strReturn = "资金拆入返款";
					break;
				case (int)CAPITAL_OUT_REPAY:
					strReturn = "资金拆出返款";
					break;
				case (int)PAYBACK_NOTIFY:
					strReturn = "合同卖出购回（回购）";
					break;
				case (int)ACCEPT_NOTIFY:
					strReturn = "合同买入购回（回购）";
					break;
				case (int)INTEREST_PAYMENT:
					strReturn = "利息支付通知单";
					break;
				case (int)INVEST_REPURCHASE:
					strReturn ="买入（回购）";
					break;
				case (int)INVEST_BREAK:
					strReturn ="买入（买断）";
					break;
				case (int)AVERAGE_REPURCHASE:
					strReturn ="卖出（回购）";
					break;
				case (int)AVERAGE_BREAK:
					strReturn ="卖出（买断）";
					break;
				case (int)CAPITAL_PAYMENT:
					strReturn = "支付转让款项";
					break;
				case (int)INTEREST_PAYBACK:
					strReturn ="利息收回通知单";
					break;
				case (int)REPURCHASE_CAPITAL:
					strReturn ="买入（回购）购回通知单";
					break;
				case (int)ACCEPT_CAPITAL:
					strReturn ="卖出（回购）购回通知单";
					break;
				case (int)CAPITAL_PAYBACK:
					strReturn ="收到转让款项";
					break;
			}
			return strReturn;
		}
		
		//根据业务类型获取所需交易类型
		public static final long[] getTransactType(long type)
		{
			long[] lArray = null;
			switch ((int)type)
			{
				case (int)CRAconstant.SameBusinessAttribute.DISCOUNT:  //转贴现
					lArray = new long[]{LOAN_APPLY,LOAN_CONTRACT,TRANSDISCOUNT_REPURCHASECREDENCE};
					break;
				case (int)CRAconstant.SameBusinessAttribute.FUND_ATTORN: //资金拆借
					lArray = new long[]{CAPITAL_APPLY,CAPITAL_EXCHANGE_LISTS,CAPITAL_LANDING_NOTICE};
					break;
				case (int)CRAconstant.SameBusinessAttribute.SAME_BUSINESS: //资产转让
					lArray = new long[]{SAME_BUSINESS_APPLY,SAME_BUSINESS_CONTRACT,SAME_BUSINESS_NOTICE,SAME_BUSINESS_LOANCONTRACT};
					break;
				default:
					lArray = new long[]{};
					break;
			}
				
			return lArray;
		}
		//获取所有需要的交易类型
        public static final long[] getTransCode()
        {
            long[] lTemp =
            { SAME_BUSINESS_APPLY,ZTX_INVEST_REPURCHASE,ZTX_INVEST_BREAK,ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, 
              CAPITAL_IN, CAPITAL_OUT,AVERAGE_NOTIFY,REPURCHASE_NOTIFY,BREAK_NOTIFY,CAPITAL_APPLY,SAME_BUSINESS_CONTRACT,SAME_BUSINESS_NOTICE,LOAN_APPLY,
              LOAN_CONTRACT,TRANSDISCOUNT_CREDENCE,TRANSDISCOUNT_REPURCHASECREDENCE,CAPITAL_LANDING_NOTICE,SAME_BUSINESS_NOTICE,SAME_BUSINESS_LOANCONTRACT};
            return lTemp;
        }
        //增加同业往来模块业务提醒设置类型Code       lijunli  2010.8.23
        public static final long[] getAlertCode(){
        	long[] lTemp=
        	{ ZTX_INVEST_REPURCHASE,ZTX_AVERAGE_REPURCHASE,AVERAGE_NOTIFY ,REPURCHASE_NOTIFY,CAPITAL_OUT_REPAY,CAPITAL_IN_REPAY };
        	return lTemp;
        	
        }
        //增加同业往来模块业务提醒设置类型名称              lijunli  2010.8.30
        public static final String getAlterName(long ID)
		{   
			String strReturn = ""; //初始化返回值
			switch ((int)ID)
			{							
			case (int)ZTX_INVEST_REPURCHASE:
				strReturn = "转贴现买入（回购）";
				break;
			
			case (int)ZTX_AVERAGE_REPURCHASE:
				strReturn = "转贴现卖出（回购）";
				break;		
		
			case (int)REPURCHASE_NOTIFY:
				strReturn = "合同卖出（回购）"; 
				break;		
			case (int)AVERAGE_NOTIFY:
			    strReturn = "合同买入（回购）";
			    break;
					
			case (int)CAPITAL_IN_REPAY:
				strReturn = "资金拆入返款";
				break;
			case (int)CAPITAL_OUT_REPAY:
				strReturn = "资金拆出返款";
				break;					
			}
			return strReturn;
		}
        
        //获取转贴现查询时所用的交易类型
        public static final long[] getTransDiscountCode()
        {
            long[] lTemp =
            {INVEST_REPURCHASE, INVEST_BREAK, AVERAGE_REPURCHASE, AVERAGE_BREAK};
            return lTemp;
        }
        
      //  获取所有需要授信的交易类型
        public static final long[] getCreditCode()
        {
            long[] lTemp =
            { ZTX_INVEST_REPURCHASE,ZTX_INVEST_BREAK,ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, 
              CAPITAL_IN, CAPITAL_OUT,AVERAGE_NOTIFY,REPURCHASE_NOTIFY,BREAK_NOTIFY,BREAK_INVEST};
            return lTemp;
        }
        
        //财务公司对交易对手授信可能发生的交易类型
        public static final long[] getPositiveCreditCode()
        {
            long[] lTemp =
            {ZTX_INVEST_REPURCHASE, ZTX_INVEST_BREAK, CAPITAL_OUT, AVERAGE_NOTIFY, BREAK_INVEST};
            return lTemp;
        }
        
        //同行对财务公司授信可能发生的交易类型
        public static final long[] getOppositeCreditCode()
        {
            long[] lTemp =
            {ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, CAPITAL_IN, REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }
        
        //同行对内部单位授信可能发生的交易类型
        public static final long[] getAttormTransCode()
        {
            long[] lTemp =
            {REPURCHASE_NOTIFY,BREAK_NOTIFY};
            return lTemp;
        }

        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.CRAconstant$TransactionType", officeID,
					currencyID);
		}
        
        public static final long[] getCodeByTransactonType(long transactionType)
        {
        	long[] lTemp = null;
        	switch ((int) transactionType)
        	{
        		//资金拆借业务通知单
    		  	case(int)CAPITAL_LANDING_NOTICE :
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT,
    		  			CAPITAL_IN_REPAY,
    		  			CAPITAL_OUT_REPAY
    		  		};
    		  		break;
    		  	//资金拆借交割单
    		  	case (int) CAPITAL_EXCHANGE_LISTS:
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT,
    		  			CAPITAL_IN_REPAY,
    		  			CAPITAL_OUT_REPAY   		  			
    		  		};
    		  		break;
    		  	//资金拆借申请
    		  	case(int)CAPITAL_APPLY :
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT   		  			
    		  		};
    		  		break;
    		  	//转贴现
    		  	case(int)CRAconstant.SameBusinessAttribute.DISCOUNT :
    		  		lTemp = new long[]{
    		  			LOAN_APPLY,
    		  			LOAN_CONTRACT,
    		  			//TRANSDISCOUNT_CREDENCE,
    		  	        TRANSDISCOUNT_REPURCHASECREDENCE
    		  		};
    		  		break;
    		  	//资产转让申请
    		  	case(int)SAME_BUSINESS_APPLY :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			BREAK_INVEST
    		  		};
    		  		break;
//        		//资产转让合同  
//    		  	case(int)SAME_BUSINESS_CONTRACT :
//    		  		lTemp = new long[]{
//    		  			REPURCHASE_NOTIFY,
//    		  			AVERAGE_NOTIFY,
//    		  			BREAK_NOTIFY
//    		  		};
//    		  		break; 
    		  		
    		  	//资产转让合同  
    		  	case(int)SAME_BUSINESS_CONTRACT :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			BREAK_INVEST
    		  		};
    		  		break; 
    		  	/**
        		//资产转让业务通知单
    		  	case(int)SAME_BUSINESS_NOTICE :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			PAYBACK_NOTIFY,
    		  			ACCEPT_NOTIFY,
    		  			INTEREST_PAYMENT
    		  		};
    		  		break;
    		  		*/
    		  	//资产转让业务通知单
    		  	case(int)SAME_BUSINESS_NOTICE :
    		  		lTemp = new long[]{
    		  			CAPITAL_PAYMENT,
    		  			INTEREST_PAYBACK,
    		  			REPURCHASE_CAPITAL,
    		  			ACCEPT_CAPITAL,
    		  			CAPITAL_PAYBACK,
    		  			INTEREST_PAYMENT
    		  		};
    		  		break;
    		  	case(int)SAME_BUSINESS_LOANCONTRACT:
    		  		lTemp = new long[]{
    		  			AVERAGE_REPURCHASE,
    		  			AVERAGE_BREAK
    		  		};
    		  		break;
        		//授信
    		  	case(int)CRAconstant.SameBusinessAttribute.OTHER :
    		  		lTemp = new long[]{
    					REPURCHASE_NOTIFY,
    					AVERAGE_NOTIFY,
    					BREAK_NOTIFY,
    					BREAK_INVEST,
    					ZTX_INVEST_REPURCHASE,
    					ZTX_INVEST_BREAK,
    					ZTX_AVERAGE_REPURCHASE,
    					ZTX_AVERAGE_BREAK,
    					CAPITAL_IN,
    					CAPITAL_OUT
    		  		};
    		  		break;
    		  	//转贴现凭证
    		  	case(int)TRANSDISCOUNT_CREDENCE:
    		  		lTemp = new long[]{
    		  			ZTX_INVEST_REPURCHASE, 	//转贴现买入（回购）
    		  			ZTX_INVEST_BREAK, 		//转贴现买入（买断）
    		  			ZTX_AVERAGE_REPURCHASE,	//转贴现卖出（回购）
    		  			ZTX_AVERAGE_BREAK 		//转贴现卖出（买断）
    		  		};
    		  		break;
    		  	//转贴现回购凭证
    		  	case(int)TRANSDISCOUNT_REPURCHASECREDENCE:
    		  		lTemp = new long[]{
    		  			ZTX_INVEST_REPURCHASE, 	//转贴现买入（回购）
    		  			ZTX_INVEST_BREAK, 		//转贴现买入（买断）
    		  			ZTX_AVERAGE_REPURCHASE,	//转贴现卖出（回购）
    		  			ZTX_AVERAGE_BREAK 		//转贴现卖出（买断）
    		  		};
    		  		break;
    		  	default:
    		  		lTemp = new long[]{};
        	}            
            return lTemp;
        }     
        
        public static final long[] getAllCode(long lModuleType)
        {
        	long[] lTemp = null;
        	switch ((int) lModuleType)
        	{
        		  case(int)ModuleType.CRAFTBROTHER:
        		
        			  lTemp = new long[]{
        				  LOAN_APPLY,
        				  LOAN_CONTRACT,
        				// CAPITAL_IN_APPLY,
        				// CAPITAL_OUT_APPLY,
        				  CAPITAL_LANDING_NOTICE,
        				  TRANSDISCOUNT_CREDENCE,
        				  TRANSDISCOUNT_REPURCHASECREDENCE,
        				  ZTX_INVEST_REPURCHASE,
        				  ZTX_INVEST_BREAK,
        				  ZTX_AVERAGE_REPURCHASE,
        				  ZTX_AVERAGE_BREAK,
        				  CAPITAL_IN,
        				  CAPITAL_OUT,
        				  REPURCHASE_NOTIFY,
        				  AVERAGE_NOTIFY,
        				  BREAK_NOTIFY,
        				 //SAME_BUSINESS_APPLY,
        				 //SAME_BUSINESS_CONTRACT,
        				 //SAME_BUSINESS_NOTICE,
    			    };
        		  
     			break;       			
        	}            
            return lTemp;
        } 
        
        //同业往来合同查询时使用
        public static final long[] getContractQueryCode()
        {
            long[] lTemp =
            {AVERAGE_NOTIFY, REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }
        
        //资产转让利息计算
        public static final long[] getCapitalTransferInterestQueryCode()
        {
            long[] lTemp =
            {REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }  
    
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName,
         *            控件名称
         * @param nType，控件类型
         *            （0，显示全部）
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
                    case 0:
                        //lArrayID = getCreditCode();
                    	lArrayID = getContractQueryCode();
                        break;
                    case 1:
                        lArrayID = getPositiveCreditCode();
                        break;  
                    case 2:
                        lArrayID = getOppositeCreditCode();
                        break;   
                    case 3:
                        lArrayID = getAttormTransCode();
                        break; 
                    case 4:
                        lArrayID = getTransCode();
                        break;
                    case 5:
                        lArrayID = getContractQueryCode();
                        break;
                    case 6:
                    	lArrayID = getCapitalTransferInterestQueryCode();
                    	break;
                    case 7:
                    	lArrayID = getTransDiscountCode();
                    	break;
                    default:
                    	lArrayID= getCreditCode();
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
                Log.print(ex.toString());
            }
        }
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName,
         *            控件名称
         * @param nTransType，交易类型
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showTransList(JspWriter out, String strControlName, int nTransType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
            	lArrayID = getCodeByTransactonType(nTransType);
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
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
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
                        break;
                    case 1:
                        lArrayID = getPositiveCreditCode();
                        break;  
                    case 2:
                        lArrayID = getOppositeCreditCode();
                        break;   
                    case 3:
                        lArrayID = getAttormTransCode();
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
                Log.print(ex.toString());
            }
        }  	
    	public static class counterpartBank
    	{
    		public static final long VALID = 1;//有效
    		public static final long INVALID = 0;//无效
    		
    	}
	}
 /*
  * add by xwhe 2009-06-30
  * 信贷资产转让交易类型
  */       
   public static class CraTransactionType 
   {
	   public static final long REPURCHASE_NOTIFY = 1001;//卖出回购
	   
	   public static final long BREAK_NOTIFY = 1002;//卖出卖断
	   
	   public static final long AVERAGE_NOTIFY =1003;//买入回购
	   
	   public static final long BREAK_AVERAGE = 1004;//买入买断
	   
	   public static final String getName(long transactionTypeID )
	   {
		   String strReturn = ""; //初始化返回值
			switch ((int)transactionTypeID)
			{
				case (int)REPURCHASE_NOTIFY:
					strReturn = "卖出回购"; 
					break;
				case (int)BREAK_NOTIFY:
					strReturn = "卖出卖断"; 
					break;
				case (int)AVERAGE_NOTIFY:
					strReturn = "买入回购"; 
					break;
				case (int)BREAK_AVERAGE:
					strReturn = "买入买断";
					break;
			}
			return strReturn;
				
	   }
	   public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
       {
           long[] lArrayID = null;
           String[] strArrayName = null;
           try
           {
               switch (nType)
               {
                   case 0:
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
               Log.print(ex.toString());
           }
       }
       public static final long[] getAllCode(long officeID, long currencyID) 
       {
			return Constant.getAllCode(
					"com.iss.itreasury.craftbrother.util.CRAconstant$CraTransactionType", officeID,
					currencyID);
		}
	   
       }
   /*
    * add by xwhe 2009-06-30
    * 信贷资产转让业务类型
    */       
     public static class CraActionType 
     {
         public static final long SAME_BUSINESS_APPLY = 3501; //信贷资产转让申请
         
         public static final long SAME_BUSINESS_CONTRACT = 3502; //信贷资产转让合同        
         
         public static final long SAME_BUSINESS_NOTICE = 3503; //信贷资产转让业务通知单
         
         public static final long CRA_LOANCONTRACT_APPLY = 3504; //贷款合同申请
  	  
  	   
  	   public static final String getName(long transactionTypeID )
  	   {
  		   String strReturn = ""; //初始化返回值
  			switch ((int)transactionTypeID)
  			{
  				case (int)SAME_BUSINESS_APPLY:
  					strReturn = "信贷资产转让申请"; 
  					break;
  				case (int)SAME_BUSINESS_CONTRACT:
  					strReturn = "信贷资产转让合同"; 
  					break;
  				case (int)SAME_BUSINESS_NOTICE:
  					strReturn = "信贷资产转让业务通知单"; 
  					break;
  				case (int)CRA_LOANCONTRACT_APPLY:
  					strReturn = "贷款合同申请"; 
  					break;
  			}
  			return strReturn;
  				
  	   }
  	   public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
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
                 Log.print(ex.toString());
             }
         }
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$CraActionType", officeID,
  					currencyID);
  		 }
         
         public static final long[] getCodeByTransactonType(long transactionType)
         {
         	long[] lTemp = null;
         	switch ((int) transactionType)
         	{
         		
     		  	//信贷资产转让申请
     		  	case(int)SAME_BUSINESS_APPLY :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,
     		  			CRAconstant.CraTransactionType.AVERAGE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_AVERAGE
     		  		};
     		  		break;
         		//信贷资产转让合同  
     		  	case(int)SAME_BUSINESS_CONTRACT :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,
     		  			CRAconstant.CraTransactionType.AVERAGE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_AVERAGE
     		  		};
     		  		break; 
         		//信贷资产转让业务通知单
     		  	case(int)SAME_BUSINESS_NOTICE :
     		  		lTemp = new long[]{
     		  			CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
     		  			CRAconstant.CRANoticeActionType.TRANSFERPAYAMOUNT,    	
     		  		};
     		  		break;
                 //贷款合同申请处理
     		  	case(int)CRA_LOANCONTRACT_APPLY :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,    	
     		  		};
     		  		break;
         	}            
             return lTemp;
         }     
  	   
         }
 /*
  * 信贷资产转让通知单交易类型
  */
     public static class CRANoticeActionType 
     {
    	 
         public static final long TRANSFERREPAYAMOUNT = 11; //信贷资产转让收款
         
         public static final long TRANSFERPAYAMOUNT = 22; //信贷资产转让付款
         
         public static final String getName(long transactionTypeID )
    	   {
    		   String strReturn = ""; //初始化返回值
    			switch ((int)transactionTypeID)
    			{
    				case (int)TRANSFERREPAYAMOUNT:
    					strReturn = "信贷资产转让收款"; 
    					break;
    				case (int)TRANSFERPAYAMOUNT:
    					strReturn = "信贷资产转让付款"; 
    					break;
    			}
    			return strReturn;
    				
    	   }
         public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
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
                 Log.print(ex.toString());
             }
         }
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$CRANoticeActionType", officeID,
  					currencyID);
  		 }
  	  
      }
      /*
       * 信贷资产转让通知单是否代收
       */
     public static class ISURROGATEPAY
     {
    	 public static final long ISTRUE = 1; //代收
    	 
    	 public static final long ISNOT = 2; //非代收
    	 
    	 public static final String getName(long ID )
  	   {
  		   String strReturn = ""; //初始化返回值
  			switch ((int)ID)
  			{
  				case (int)ISTRUE:
  					strReturn = "是"; 
  					break;
  				case (int)ISNOT:
  					strReturn = "否"; 
  					break;
  			}
  			return strReturn;
  				
  	   }
    	 public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
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
                 Log.print(ex.toString());
             }
         }
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$ISURROGATEPAY", officeID,
  					currencyID);
  		 } 
    	 
     }
     
     /*
      * 信贷资产转让通知单是否结算户直接清算
      */
    public static class ISDIRECTSETT
    {
   	 public static final long ISTRUE = 1; //是
   	 
   	 public static final long ISNOT = 2; //否
   	 
   	 public static final String getName(long ID )
 	   {
 		   String strReturn = ""; //初始化返回值
 			switch ((int)ID)
 			{
 				case (int)ISTRUE:
 					strReturn = "是"; 
 					break;
 				case (int)ISNOT:
 					strReturn = "否"; 
 					break;
 			}
 			return strReturn;
 				
 	   }
   	 public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
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
                Log.print(ex.toString());
            }
        }
        public static final long[] getAllCode(long officeID, long currencyID) 
        {
 			return Constant.getAllCode(
 					"com.iss.itreasury.craftbrother.util.CRAconstant$ISDIRECTSETT", officeID,
 					currencyID);
 		 } 
   	 
    }
     
     public static class Counterparttype
     {
     	public static final long STATE_OWNED_BANK	 = 1; //国有商业银行
     	public static final long COMMERCIAL_BANK		 = 2; //其他商业银行
     	public static final long NONBANK_ORGANIZATION = 3; //非银行金融机构 
     	public static final long OTHER_ORGANIZATION	 = 4; //其他单位
     	
     	public static final String getName(long lCode)
     	{
     		String strReturn = ""; //初始化返回值
     		switch ((int) lCode)
     		{
     			case (int) STATE_OWNED_BANK :
     				strReturn = "国有商业银行";
     				break;
     			case (int) COMMERCIAL_BANK :
     				strReturn = "其他商业银行";
     				break;
     			case (int) NONBANK_ORGANIZATION :
     				strReturn = "非银行金融机构";
     				break;
     			case (int) OTHER_ORGANIZATION :
     				strReturn = "其他单位";
     				break;
     		}
     		return strReturn;
     	}
     	public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
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
                Log.print(ex.toString());
            }
        }
     	
     	
     	public static final long[] getAllCode(long officeID, long currencyID) 
        {
 			return Constant.getAllCode(
 					"com.iss.itreasury.craftbrother.util.CRAconstant$Counterparttype", officeID,
 					currencyID);
 		 } 
     	
     }
     
     /* 手续费计算方式 */
 	public static class ChargeCommisonPayType {
 		
 		public static final long  CHARGENONE  = 0;
 		
 		public static final long CHARGEAMOUNT = 1;

 		public static final long CHARGEINTEREST = 2;

 		public static final long CHARGEOTHER = 3;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) CHARGENONE:
 				strReturn = "不收取手续费";
 				break;
 			case (int) CHARGEAMOUNT:
 				strReturn = "贷款本金的固定百分比";
 				break;
 			case (int) CHARGEINTEREST:
 				strReturn = "贷款总收息金额的固定百分比";
 				break;
 			case (int) CHARGEOTHER:
 				strReturn = "交易对手收取固定利率利息，剩余为财务公司手续费";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CHARGENONE, CHARGEAMOUNT, CHARGEINTEREST, CHARGEOTHER };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$ChargeCommisonPayType",
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

     }
    /* 结息方式 */
 	public static class ClearInterestType {
 		
 		public static final long  CLEARMONTH  = 1;
 		
 		public static final long CLEARQUARTER = 2;

 		public static final long CLEARENDDATE = 3;

 		public static final long CLEAROTHER = 4;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) CLEARMONTH:
 				strReturn = "按月支付";
 				break;
 			case (int) CLEARQUARTER:
 				strReturn = "按季度支付";
 				break;
 			case (int) CLEARENDDATE:
 				strReturn = "到期支付";
 				break;
 			case (int) CLEAROTHER:
 				strReturn = "其它";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CLEARMONTH, CLEARQUARTER, CLEARENDDATE, CLEAROTHER };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$ClearInterestType",
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

     }
 	 /* 手续费收取方式 */
 	public static class CommisonPayType {
 		
 		public static final long  DEDUCTION  = 1;
 		
 		public static final long FANHUAN = 2;
 		
 		public static final long NOCOMMISSION = 3;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) DEDUCTION:
 				strReturn = "先扣除";
 				break;
 			case (int) FANHUAN:
 				strReturn = "后返还";
 				break;
 			case (int) NOCOMMISSION:
 				strReturn = "无";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { DEDUCTION, FANHUAN ,NOCOMMISSION};
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$CommisonPayType",
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
 					lArrayID = new long[]{NOCOMMISSION};
 					break;
 				case 2:
 					lArrayID = new long[]{DEDUCTION,FANHUAN};
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
 	 /* 子账户操作类型 */
 	public static class operationType {
 		
 		public static final long  CLIENT  = 1; //对成员单位
 		
 		public static final long CONTERPART= 2; //对交易对手

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) CLIENT:
 				strReturn = "成员单位";
 				break;
 			case (int) CONTERPART:
 				strReturn = "交易对手";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CLIENT, CONTERPART };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$operationType",
 							officeID, currencyID);
 		}
 	}
 	
	 /* 代收方式 */
 	public static class AgentType {
 		
 		public static final long  AMOUNT  = 1; //代收本金
 		
 		public static final long AMOUNTANDINTEREST= 2; //代收本息

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) AMOUNT:
 				strReturn = "代收本金";
 				break;
 			case (int) AMOUNTANDINTEREST:
 				strReturn = "代收本息";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { AMOUNT, AMOUNTANDINTEREST };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$AgentType",
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
 	}
 	
	 /* 卖出回购付款方式 */
 	public static class PayType {
 		
 		public static final long  INTEREST  = 1; //付息
 		
 		public static final long AMOUNTANDINTEREST= 2; //付本息

 		public static final String getName(long lCode) {
 			String strReturn = ""; // 初始化返回值
 			switch ((int) lCode) {
 			case (int) INTEREST:
 				strReturn = "付息";
 				break;
 			case (int) AMOUNTANDINTEREST:
 				strReturn = "还本付息";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { INTEREST, AMOUNTANDINTEREST };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$PayType",
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
 	}
 	//资产转让通知单类型
	public static class NoticeFormType
	{
        public static final long CAPITAL_PAYMENT = SECConstant.NoticeFormType.CAPITAL_PAYMENT; //支付转让款项
        public static final long INTEREST_PAYBACK = SECConstant.NoticeFormType.INTEREST_PAYBACK; //利息收回通知单
        public static final long CAPITAL_REPURCHASE = SECConstant.NoticeFormType.CAPITAL_REPURCHASE; //购回通知单
        public static final long INTEREST_PAYMENT = SECConstant.NoticeFormType.INTEREST_PAYMENT; //利息支付通知单
        public static final long CAPITAL_PAYBACK = SECConstant.NoticeFormType.CAPITAL_PAYBACK; //收到转让款项
        
        
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) CAPITAL_PAYMENT :
                    strReturn = "支付转让款项";
                    break;
                case (int) INTEREST_PAYBACK :
                    strReturn = "利息收回通知单";
                    break;
                case (int) CAPITAL_REPURCHASE :
                    strReturn = "购回通知单";
                    break;
                case (int) INTEREST_PAYMENT :
                    strReturn = "利息支付通知单";
                    break;
                case (int) CAPITAL_PAYBACK :
                    strReturn = "收到转让款项";
                    break;
            }
            return strReturn;
        }
        
        public static final long[] getAllCode()
        {
            long[] lTemp = { CAPITAL_PAYMENT, INTEREST_PAYBACK, CAPITAL_REPURCHASE, INTEREST_PAYMENT, CAPITAL_PAYBACK };
            return lTemp;
        }
        /**
          * 显示下拉列表
          * 
          * @param out
          * @param strControlName,
          *            控件名称
          * @param nType，控件类型（0，显示全部；1,没有清户选项）
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
                    case 1 :
                        lArrayID = new long[3];
                        lArrayID[0] = CAPITAL_PAYMENT;
                        lArrayID[1] = INTEREST_PAYBACK;
                        lArrayID[2] = CAPITAL_REPURCHASE;
                        break;
                    case 2 :
                        lArrayID = new long[1];
                        lArrayID[0] = CAPITAL_PAYMENT;
                        break; 
                    case 3 :
                    	 lArrayID = new long[3];
                         lArrayID[0] = CAPITAL_PAYBACK;
                         lArrayID[1] = INTEREST_PAYBACK;
                         lArrayID[2] = CAPITAL_REPURCHASE;
                        break;
                    case 4 :
                        lArrayID = new long[1];
                        lArrayID[0] = CAPITAL_PAYBACK;
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
                Log.print(ex.toString());
            }
        }
    }
}

		
	
