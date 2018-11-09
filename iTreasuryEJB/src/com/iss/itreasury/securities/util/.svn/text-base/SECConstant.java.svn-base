/*
 * Created on 2003-8-7
 *  
 */
package com.iss.itreasury.securities.util;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dao.SEC_TransactionTypeDAO;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.util.Log;
/** 
 * 包含结算子系统所有的常量
 * 
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class SECConstant extends com.iss.itreasury.util.Constant
{

	/*业务性质
	 * 业务性质比较特殊，在业务性质表中只定义了名称，没有定义ID，因此通过名称返回常量中定义的ID
	 * */
	public static class BusinessAttribute
	{
		public static final long INTER_BANK = 1; //1,银行间业务
		public static final long INTER_EXCHANGECENTER = 2; //2，交易所业务
		public static final long OPENFUND = 3; //3，开放式基金业务
		public static final long CONTRACTS = 4; //4，合同业务
		public static final long OTHERS = 5; //5，其他业务
		
		public static final long getIDByName(String name)
		{
			if (name.compareToIgnoreCase("银行间业务") == 0)
				return INTER_BANK;
			else if (name.compareToIgnoreCase("交易所业务") == 0)
				return INTER_EXCHANGECENTER;
			else if (name.compareToIgnoreCase("开放式基金业务") == 0)
				return OPENFUND;
			else if (name.compareToIgnoreCase("合同业务") == 0)
				return CONTRACTS;
			else if (name.compareToIgnoreCase("其他业务") == 0)
				return OTHERS;
			return -1;
		}
		public static final long[] getCodeForApprovalSetting(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER};
		}
		public static final long[] getAllCode(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER,OPENFUND,CONTRACTS,OTHERS};
		}
		public static final long[] getCodeForApproval(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER,CONTRACTS};
		}
		
		public static final String getName(long ID){
			if (ID==INTER_BANK)
				return "银行间业务";
			else if (ID==INTER_EXCHANGECENTER)
				return "交易所业务";
			else if (ID==OPENFUND)
				return "开放式基金业务";
			else if (ID==CONTRACTS)
				return "合同业务";
			else if (ID==OTHERS)
				return "其他业务";
			
			return "-1";
		}
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long[] lArrayID){
			
			String[] strArrayName = null;
			try
			{
				//lArrayID = getAllCode(lOfficeID,lCurrencyID);
				//lArrayID = getAllSetLoanTypeID(lOfficeID,lCurrencyID);
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
	/*业务性质状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class BusinessAttributeStatus
	{
		public static final long DELETED = 0; //0,已删除；
		public static final long SAVED = 2; //2，已保存
		public static final long CHECKED = 3; //3，已复核
	}
	/*备注状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class RemarkStatus extends BusinessAttributeStatus
	{
	}
	/*科目设置状态
	 0=已删除
	 3=已保存 */
	public static class EntrySubjectStatus extends BusinessAttributeStatus
	{
	}
	/*帐务科目设置状态
	 0=已删除
	 3=已保存 */
	public static class AccountTypeStatus extends BusinessAttributeStatus
	{
	}
	/*证交所状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class ExchangeCenterStatus extends BusinessAttributeStatus
	{
	}
	/*业务类型状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class BusinessTypeStatus extends BusinessAttributeStatus
	{
	}
	/*设置状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class SettingStatus extends BusinessAttributeStatus
	{
	}
	/*证券状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class SecuritiesStatus extends BusinessAttributeStatus
	{
	}
	/*交易类型状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class TransactionTypeStatus extends BusinessAttributeStatus
	{
	}
	/*证券类别状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class SecuritiesTypeStatus extends BusinessAttributeStatus
	{
	}
	/*交易对手状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class CounterpartStatus extends BusinessAttributeStatus
	{
	}
	/*股东帐户状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class StockHolderAccountStatus extends BusinessAttributeStatus
	{
	}
	/*资金帐户状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class AccountStatus extends BusinessAttributeStatus
	{
	}
	/*证券行情状态
	  0=已删除
	  2=已保存
	  3=已复核 */
	public static class SecuritiesMarketStatus extends BusinessAttributeStatus
	{
	}
	/*收付方向
	  1=收
	  2=付 */
	public static class Direction
	{
		public static final long RECEIVE = 1; //资金帐户收
		public static final long PAY = 2; //资金帐户付
		public static final long PAY_AND_RECEIVE = 3; //一收一付
		public static final long FINANCE_RECEIVE = 4; //财务公司收款
		public static final long FINANCE_PAY = 5; //财务公司付款
		public static final long RECEIVE_AND_FINANCE_PAY = 6; //资金帐户收，银行付
		public static final long PAY_AND_FINANCE_RECEIVE = 7; //资金帐户付，银行收
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) RECEIVE :
					strReturn = "收";
					break;
				case (int) PAY :
					strReturn = "付";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { RECEIVE, PAY };
			return lTemp;
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 * （0，显示全部；）
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
				Log.print(ex.toString());
			}
		}
	}
	public static class StockDirection
	{
		public static final long IN = 1; //入
		public static final long OUT = 2; //出		
	}
	/*交割单性质
	  1=实际
	  2=虚拟*/
	public static class DeliveryOrderAttribute
	{
		public static final long REAL = 1; //实际
		public static final long DUMMY = 2; //虚拟
	}
	/*价格指标
	  0=不适用
	  1=不大于/小于等于
	  2=不小于/大于等于
	  3=固定值
	  4=固定范围*/
	public static class PriceTarget
	{
		public static final long INVALID = 0; //不适用
		public static final long LESSTHAN = 1; //不大于/小于等于
		public static final long MORETHAN = 2; //不小于/大于等于
		public static final long EQUAL = 3; //固定值
		public static final long RANGE = 4; //固定范围
	}
	/**
	 * 
	 * 页面操作定义
	 * 
	 * @author rongyang
	 */
	public static class Actions
	{
		public static final int CREATETEMPSAVE = 1; //新增临时保存
		public static final int MODIFYTEMPSAVE = 2; //修改临时保存
		public static final int CREATESAVE = 3; //创建保存
		public static final int MODIFYSAVE = 4; //修改保存
		public static final int DELETE = 5; //删除
		public static final int LINKSEARCH = 6; //链接查找
		public static final int MATCHSEARCH = 7; //匹配查找
		public static final int CHECK = 8; //复核/审核
		public static final int CANCELCHECK = 9; //取消复核
		public static final int NEXTSTEP = 10; //下一步
		public static final int TODETAIL = 11; //点交易号到详细页面
		public static final int MODIFYNEXTSTEP = 12; //更改下一步
		public static final int REJECT = 13; //拒绝
		public static final int RETURN = 14; //返回修改
		public static final int CHECKOVER = 15; //审核完成
		public static final int MASSCHECK = 16; //批量复核
		public static final int MASSCANCELCHECK = 17; //批量取消复核
		public static final int UPDATESEARCH = 18; //修改查找
		public static final int CHECKSEARCH = 19; //审核查找
		public static final int COMMIT = 20; //提交
		public static final int INITAPPROVAL = 25;// 提交审批
		public static final int SAVEANDINITAPPROVAL = 26; // 保存并提交审批
		public static final int DOAPPRVOAL = 27; // 审批
		public static final int APPMATCHSEARCH = 28; // 审批查找
		public static final int CANCELAPPROVAL = 29; //取消审批	
		public static final int MODIFYANDINITAPPROVAL = 30; // 修改并提交审批

		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case CREATETEMPSAVE :
					strReturn = "临时保存";
					break;
				case MODIFYTEMPSAVE :
					strReturn = "临时保存";
					break;
				case CREATESAVE :
					strReturn = "创建保存";
					break;
				case MODIFYSAVE :
					strReturn = "修改保存";
					break;
				case DELETE :
					strReturn = "删除";
					break;
				case LINKSEARCH :
					strReturn = "链接查找";
					break;
				case MATCHSEARCH :
					strReturn = "匹配查找";
					break;
				case CHECK :
					strReturn = "复核";
					break;
				case CANCELCHECK :
					strReturn = "取消复核";
					break;
				case NEXTSTEP :
					strReturn = "下一步";
					break;
				case TODETAIL :
					strReturn = "察看明细";
					break;
				case REJECT :
					strReturn = "拒绝";
					break;
				case RETURN :
					strReturn = "返回修改";
					break;
				case CHECKOVER :
					strReturn = "审核完成";
					break;
				case MASSCHECK :
					strReturn = "批量复核";
					break;
				case MASSCANCELCHECK :
					strReturn = "批量取消复核";
					break;
				case UPDATESEARCH :
					strReturn = "修改查找";
					break;
				case CHECKSEARCH :
					strReturn = "审核查找";
					break;
				case COMMIT :
					strReturn = "提交";
				case INITAPPROVAL:
					strReturn = "提交审批";
					break;
				case SAVEANDINITAPPROVAL:
					strReturn = "保存并提交审批";
					break;				
				case DOAPPRVOAL:
					strReturn = "审批";
					break;
				case APPMATCHSEARCH:
					strReturn = "审批查找";
					break;
				case CANCELAPPROVAL:
					strReturn = "取消审批";
					break;

			}
			return strReturn;
		}
	}
	/**
	 * 交易状态
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class TransactionStatus
	{
		public static final long DELETED = 0; //已删除
		public static final long TEMPSAVE = 1; //暂存
		public static final long SAVE = 2; //已保存（未复核）
		public static final long CHECK = 3; //已复核
		public static final long NOTSIGN = 4; //未签认
		public static final long SIGN = 5; //已签认
		public static final long CONFIRM = 6; //已确定
		public static final long CIRCLE = 7; //已勾帐
		public static final long APPROVALING = 10; //审批中		
		public static final long APPROVALED = 20;  //已审批		
		public static final long REFUSE = 30;  //已拒绝
		public static final long WAITAPPROVAL=40;//待审批		
		
		public static final long[] getAllCode()
		{
			long[] lTemp = { DELETED, TEMPSAVE, SAVE, CHECK, NOTSIGN, SIGN, CONFIRM, CIRCLE, APPROVALING, APPROVALED, REFUSE, WAITAPPROVAL };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DELETED :
					strReturn = "删除";
					break;
				case (int) TEMPSAVE :
					strReturn = "暂存";
					break;
				case (int) SAVE :
					strReturn = "已保存";
					break;
				case (int) CHECK :
					strReturn = "已复核";
					break;
				case (int) NOTSIGN :
					strReturn = "未签认";
					break;
				case (int) SIGN :
					strReturn = "已签认";
					break;
				case (int) CONFIRM :
					strReturn = "已确定";
					break;
				case (int) CIRCLE :
					strReturn = "已勾帐";
					break;
				case (int) APPROVALING:
					strReturn = "审批中";
					break;
				case (int) APPROVALED:
					strReturn = "已审批";
					break;
				case (int) REFUSE:
					strReturn = "已拒绝";
					break;					
				case (int) WAITAPPROVAL:
					strReturn = "待审批";
					break;					
				default :
					strReturn = "错误状态";
			}
			return strReturn;
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *            控件名称
		 * @param nType，控件类型
		 * （0，显示全部；1，业务处理；2，业务复核;3,显示3项（暂存、未复核、已复核））4,两项(保存,已复核)
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
						lArrayID = new long[2];
						lArrayID[0] = TEMPSAVE;
						lArrayID[1] = SAVE;
						break;
					case 2 :
						lArrayID = new long[1];
						lArrayID[0] = CHECK;
						break;
					case 3 :
						lArrayID = new long[] { TEMPSAVE, SAVE, CHECK };
						break;
					case 4 :
						lArrayID = new long[] { SAVE, CHECK,NOTSIGN };
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
	/*数量指标
	  0=不适用
	  1=不大于/小于等于
	  2=不小于/大于等于
	  3=固定值
	  4=固定范围*/
	public static class AmountTarget extends PriceTarget
	{
	}
	/*日期指标
	  0=不适用
	  1=不大于/小于等于
	  2=不小于/大于等于
	  3=固定值
	  4=固定范围*/
	public static class DateTarget extends PriceTarget
	{
	}
	/*利率指标
	  0=不适用
	  1=不大于/小于等于
	  2=不小于/大于等于
	  3=固定值
	  4=固定范围*/
	public static class InterestRateTarget extends PriceTarget
	{
	}
	
	
	//证券类别
	//Modify by leiyang 2007/09/06
	public static class SecuritiesType {
		public static final long STOCK_A = 1; //"股票"
		public static final long STOCK_B = 2; //"B股"
		public static final long ENTERPRISE_BOND = 3; //"企业债现券"
		public static final long TRANSFORMABLE_BOND = 4; //"可转债现券"
		public static final long ENCLOSED_FUND = 5; //"封闭式基金"
		public static final long MUTUAL_FUND = 6; //"开放式基金"
		public static final long EXCHANGECENTER_NATIONAL_BOND = 7; //"交易所国债现券"
		public static final long EXCHANGECENTER_BOND_REPURCHASE = 8; //"交易所国债回购"
		public static final long EXCHANGECENTER_ENTERPRISE_BOND = 9; //"交易所企业债回购"
		public static final long BANK_NATIONAL_BOND = 10; //"银行间国债现券"
		public static final long BANK_BOND_REPURCHASE = 11; //"银行间国债回购"
		public static final long CENTRAL_BANK_NOTE = 12; //"央行票据现券"
		public static final long POLICY_RELATED_FINANCIAL_BOND = 13; //"政策性金融债现券"
		public static final long FINANCIAL_BOND = 14; //"金融债现券"
		public static final long OTHERS = 15; //"其它"
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) STOCK_A :
					strReturn = "股票";
					break;
				case (int) CENTRAL_BANK_NOTE :
					strReturn = "央行票据";
					break;
				case (int) BANK_BOND_REPURCHASE :
					strReturn = "银行间国债回购";
					break;
				case (int) EXCHANGECENTER_ENTERPRISE_BOND :
					strReturn = "交易所企业债回购";
					break;
				case (int) BANK_NATIONAL_BOND :
					strReturn = "银行间国债";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND :
					strReturn = "交易所国债";
					break;
				case (int) FINANCIAL_BOND :
					strReturn = "金融债";
					break;
				case (int) ENTERPRISE_BOND :
					strReturn = "企业债";
					break;
				case (int) TRANSFORMABLE_BOND :
					strReturn = "可转债";
					break;
				case (int) ENCLOSED_FUND :
					strReturn = "封闭式基金";
					break;
				case (int) MUTUAL_FUND :
					strReturn = "开放式基金";
					break;
				case (int) OTHERS :
					strReturn = "其它";
					break;
					
				case (int) STOCK_B :
					strReturn = "B股";
					break;
				case (int) EXCHANGECENTER_BOND_REPURCHASE :
					strReturn = "交易所国债回购";
					break;
				case (int) POLICY_RELATED_FINANCIAL_BOND :
					strReturn = "政策性金融债";
					break;
			}
			return strReturn;
		}
		
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = {
					STOCK_A,
					STOCK_B,
					ENTERPRISE_BOND,
					TRANSFORMABLE_BOND,
					ENCLOSED_FUND,
					MUTUAL_FUND,
					EXCHANGECENTER_NATIONAL_BOND,
					EXCHANGECENTER_BOND_REPURCHASE,
					EXCHANGECENTER_ENTERPRISE_BOND,
					BANK_NATIONAL_BOND,
					BANK_BOND_REPURCHASE,
					CENTRAL_BANK_NOTE,
					POLICY_RELATED_FINANCIAL_BOND,
					FINANCIAL_BOND,
					OTHERS
			};
			return lTemp;
		}
        
        //交易所现券
        public static final long[] getExchangeCode()
        {
            long[] lTemp =
                {
                    STOCK_A,
                    ENTERPRISE_BOND,
                    TRANSFORMABLE_BOND,
                    ENCLOSED_FUND,
                    //MUTUAL_FUND,
                    EXCHANGECENTER_NATIONAL_BOND,
                    //EXCHANGECENTER_BOND_REPURCHASE,
                    //EXCHANGECENTER_ENTERPRISE_BOND,
                    BANK_NATIONAL_BOND,
                    //BANK_BOND_REPURCHASE,
                    CENTRAL_BANK_NOTE,
                    POLICY_RELATED_FINANCIAL_BOND,
                    FINANCIAL_BOND,
                    OTHERS };
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
						lArrayID = new long[11];
						lArrayID[0] = STOCK_A;
						lArrayID[1] = ENTERPRISE_BOND;
						lArrayID[2] = TRANSFORMABLE_BOND;
						lArrayID[3] = ENCLOSED_FUND;
						lArrayID[4] = MUTUAL_FUND;
						lArrayID[5] = EXCHANGECENTER_NATIONAL_BOND;
						lArrayID[6] = EXCHANGECENTER_ENTERPRISE_BOND;
						lArrayID[7] = BANK_NATIONAL_BOND;
						//目前没有银行间国债回购业务，暂时屏蔽  add 2007-11-07
						//lArrayID[8] = BANK_BOND_REPURCHASE;
						lArrayID[8] = CENTRAL_BANK_NOTE;
						lArrayID[9] = FINANCIAL_BOND;
                        //目前没有其他业务，暂时屏蔽  add 2007-11-07
						//lArrayID[11] = OTHERS;
						lArrayID[10] = EXCHANGECENTER_BOND_REPURCHASE;
						break;
					case 5 :
						lArrayID = new long[10];
						lArrayID[0] = STOCK_A;
						lArrayID[1] = ENTERPRISE_BOND;
						lArrayID[2] = TRANSFORMABLE_BOND;
						lArrayID[3] = ENCLOSED_FUND;
						lArrayID[4] = MUTUAL_FUND;
						lArrayID[5] = EXCHANGECENTER_NATIONAL_BOND;
						lArrayID[6] = EXCHANGECENTER_BOND_REPURCHASE;
						lArrayID[7] = EXCHANGECENTER_ENTERPRISE_BOND;
						lArrayID[8] = BANK_NATIONAL_BOND;

						lArrayID[9] = FINANCIAL_BOND;
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
	//证券子类别
	public static class SecuritiesSubType
	{
		//public static final long TOSTOCK = 1; //"针对股票";
		public static final long PREFERREDSTOCK = 2; //"优先股";
		public static final long ORDINARYSTOCK = 3; //"普通股";
		public static final long OTHERS = 4; //"其它";
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				//case (int) TOSTOCK :
				//strReturn = "针对股票";
				//break;
				case (int) PREFERREDSTOCK :
					strReturn = "优先股";
					break;
				case (int) ORDINARYSTOCK :
					strReturn = "普通股";
					break;
				case (int) OTHERS :
					strReturn = "其它";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { PREFERREDSTOCK, ORDINARYSTOCK, OTHERS };
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
						//lArrayID[0] = TOSTOCK;
						lArrayID[0] = PREFERREDSTOCK;
						lArrayID[1] = ORDINARYSTOCK;
						lArrayID[2] = OTHERS;
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
	//期限类型
	public static class TermType
	{
		public static final String YEAR = "Y";
		public static final String MONTH = "M";
		public static final String DAY = "D";
	}
	//交易对手类型
	public static class CounterpartType
	{
		public static final long STOCK_JOBBER = 1; //证券公司
		public static final long FUND_MGMT_COMPANY = 2; //基金管理公司
		public static final long CENTRAL_BANK = 3; //中央银行
		public static final long POLICY_RELATED_BANK = 4; //政策性银行
		public static final long STATE_BANK = 5; //国有商业银行
		public static final long COMMERCIAL_BANK = 6; //其它商业银行
		public static final long INSURER = 7; //保险公司
		public static final long NONBANK_INSTITUTION = 8; //其它非银行金融机构
		public static final long OTHERS = 9; //其它单位
		public static final long[] getAllCode()
		{
			long[] lTemp = { STOCK_JOBBER, FUND_MGMT_COMPANY, CENTRAL_BANK, POLICY_RELATED_BANK, STATE_BANK, COMMERCIAL_BANK, INSURER, NONBANK_INSTITUTION, OTHERS };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) STOCK_JOBBER :
					strReturn = "证券公司";
					break;
				case (int) FUND_MGMT_COMPANY :
					strReturn = "基金管理公司";
					break;
				case (int) CENTRAL_BANK :
					strReturn = "中央银行";
					break;
				case (int) POLICY_RELATED_BANK :
					strReturn = "政策性银行";
					break;
				case (int) STATE_BANK :
					strReturn = "国有商业银行";
					break;
				case (int) COMMERCIAL_BANK :
					strReturn = "其它商业银行";
					break;
				case (int) INSURER :
					strReturn = "保险公司";
					break;
				case (int) NONBANK_INSTITUTION :
					strReturn = "其它非银行金融机构";
					break;
				case (int) OTHERS :
					strReturn = "其它单位";
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
		  * @param nType，控件类型（0，显示全部）
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
				Log.print(ex.toString());
			}
		}
	}
	//交易对手类型
	public static class CounterpartObjectType
	{
		public static final long FUND_MANAGEMENT_CO = 1; //开户基金管理公司
		public static final long INTER_BANK_COUNTERPART = 2; //银行间交易对手
		public static final long SECURITIES_UNDERWRITER = 3; //债券分销商
		public static final long SECURITIES_CO = 4; //委托理财券商
		public static final long INVESTED_CORPORATION = 5; //被投资企业
		public static final long OWNERSHIP_TRANSFER = 6; //股权受让方
		public static final long FLOATERS = 7; //债券发行人
		public static final long CONSIGNER = 8; //受托理财委托方
		public static final long POLICY_HOLDER = 9; //投保人
	}
	//行业类别：对应于交易对手类型是被投资企业
	public static class IndustrySort
	{
		public static final long MANUFACTURING = 1; //制造业
		public static final long FINANCE = 2; //金融业
		public static final long REALTY = 3; //房地产业
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { MANUFACTURING, FINANCE, REALTY };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) MANUFACTURING :
					strReturn = "制造业";
					break;
				case (int) FINANCE :
					strReturn = "金融业";
					break;
				case (int) REALTY :
					strReturn = "房地产业";
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
						lArrayID[0] = MANUFACTURING;
						lArrayID[1] = FINANCE;
						lArrayID[2] = REALTY;
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
	//行业类型：对应于交易对手类型是投保人
	public static class IndustryType
	{
		public static final long INDUSTRYTYPE_ENTERPRISE = 1; //企业
		
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { INDUSTRYTYPE_ENTERPRISE };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) INDUSTRYTYPE_ENTERPRISE :
					strReturn = "企业";
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
		  * @param nType，控件类型
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
				Log.print(ex.toString());
			}
		}
	}
	//资金帐户类型
	public static class AccountType
	{
		public static final long SECURITIES_ACCOUNT = 1; //证券帐户
		public static final long OPENFUND_ACCOUNT = 2; //开放式基金帐户
		public static final long BANK_ACCOUNT = 3; //银行帐户
	}
	/*帐户状态
	  1=正常
	  2=冻结
	  3=封存
	  4=清户*/
	public static class AccountStatusID
	{
		public static final long NORMAL = 1; //正常
		public static final long FREEZE = 2; //冻结
		public static final long SEALUP = 3; //封存
		public static final long CLOSE = 4; //清户
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) NORMAL :
					strReturn = "正常";
					break;
				case (int) FREEZE :
					strReturn = "冻结";
					break;
				case (int) SEALUP :
					strReturn = "封存";
					break;
				case (int) CLOSE :
					strReturn = "清户";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { NORMAL, FREEZE, SEALUP, CLOSE };
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
						lArrayID[0] = NORMAL;
						lArrayID[1] = FREEZE;
						lArrayID[2] = SEALUP;
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
	
	//业务类型
	public static class BusinessType
	{
		//Modify by leiyang date 2007/09/07
		//用于取得业务的交易类型编号
		public static final long[] getBusinessTransType(long bussinessType){
        	long[] lTemp = null;
        	switch ((int) bussinessType)
        	{
        		//16.股票一级网上申购
        		case (int) STOCK_BID_ONLINE.ID :
        			lTemp = STOCK_BID_ONLINE.getAllCode();
        			break;
        		//17.股票一级网下申购
        		case (int) STOCK_BID.ID :
        			lTemp = STOCK_BID.getAllCode();
        			break;
        		//18.股票二级
        		case (int) STOCK_TRANSACTION.ID :
        			lTemp = STOCK_TRANSACTION.getAllCode();
        			break;
        		//21.央行票据一级 
        		case (int) CENTRAL_BANK_NOTE_BID.ID :
        			lTemp = CENTRAL_BANK_NOTE_BID.getAllCode();
        			break;
        		//22.央行票据二级 
        		case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
        			lTemp = CENTRAL_BANK_NOTE_TRANSACTION.getAllCode();
        			break;
        		//26.银行间债券回购
        		case (int) BANK_BOND_REPURCHASE.ID :
        			lTemp = BANK_BOND_REPURCHASE.getAllCode();
        			break;
        		//27.交易所债券回购 
        		case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
        			lTemp = EXCHANGECENTER_BOND_REPURCHASE.getAllCode();
        			break;
        		//31.银行间国债一级 
        		case (int) BANK_NATIONAL_BOND_BID.ID :
        			lTemp = BANK_NATIONAL_BOND_BID.getAllCode();
        			break;
        		//32.银行间国债二级 
        		case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
        			lTemp = BANK_NATIONAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//33.交易所国债一级 
        		case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
        			lTemp = EXCHANGECENTER_NATIONAL_BOND_BID.getAllCode();
        			break;
        		//34.交易所国债二级
        		case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
        			lTemp = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//36.金融债一级
        		case (int) FINANCIAL_BOND_BID.ID :
        			lTemp = FINANCIAL_BOND_BID.getAllCode();
        			break;
        		//37.金融债二级
        		case (int) FINANCIAL_BOND_TRANSACTION.ID :
        			lTemp = FINANCIAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//41.政策性金融债一级 
        		case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
        			lTemp = POLICY_RELATED_FINANCIAL_BOND_BID.getAllCode();
        			break;
        		//42.政策性金融债二级
        		case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
        			lTemp = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//46.企业债一级
        		case (int) ENTERPRISE_BOND_BID.ID :
        			lTemp = ENTERPRISE_BOND_BID.getAllCode();
        			break;
        		//47.企业债二级
        		case (int) ENTERPRISE_BOND_TRANSACTION.ID :
        			lTemp = ENTERPRISE_BOND_TRANSACTION.getAllCode();
        			break;
        		//51.可转债一级网上申购 
        		case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
        			lTemp = TRANSFORMABLE_BOND_BID_ONLINE.getAllCode();
        			break;
        		//52.可转债一级网下申购
        		case (int) TRANSFORMABLE_BOND_BID.ID :
        			lTemp = TRANSFORMABLE_BOND_BID.getAllCode();
        			break;
        		//53.可转债二级
        		case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
        			lTemp = TRANSFORMABLE_BOND_TRANSACTION.getAllCode();
        			break;
        		//54.债转股 
        		case (int) DEBT_TO_EQUITY.ID :
        			lTemp = DEBT_TO_EQUITY.getAllCode();
        			break;
        		//56.封闭式基金一级网上申购
        		case (int) ENCLOSED_FUND_BID_ONLINE.ID :
        			lTemp = ENCLOSED_FUND_BID_ONLINE.getAllCode();
        			break;
        		//57.封闭式基金一级网下申购
        		case (int) ENCLOSED_FUND_BID.ID :
        			lTemp = ENCLOSED_FUND_BID.getAllCode();
        			break;
        		//58.封闭式基金二级
        		case (int) ENCLOSED_FUND_TRANSACTION.ID :
        			lTemp = ENCLOSED_FUND_TRANSACTION.getAllCode();
        			break;
        		//61.开放式基金一级认购 
        		case (int) MUTUAL_FUND_SUBSCRIBE.ID :
        			lTemp = MUTUAL_FUND_SUBSCRIBE.getAllCode();
        			break;
        		//62.开放式基金二级申购
        		case (int) MUTUAL_FUND_BID.ID :
        			lTemp = MUTUAL_FUND_BID.getAllCode();
        			break;
        		//63.开放式基金二级赎回
        		case (int) MUTUAL_FUND_REDEEM.ID :
        			lTemp = MUTUAL_FUND_REDEEM.getAllCode();
        			break;
        		//64.开放式基金分红
        		case (int) MUTUAL_FUND_MELON_CUTTING.ID :
        			lTemp = MUTUAL_FUND_MELON_CUTTING.getAllCode();
        			break;
        		//73.委托理财
        		case (int) ENTRUST_FINANCING.ID :
        			lTemp = ENTRUST_FINANCING.getAllCode();
        			break;
        		//77.结构性投资 
        		case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
        			lTemp = FOREIGN_CURRENCY_INVESTMENT.getAllCode();
        			break;
        		//81.债券承销
        		case (int) BOND_UNDERWRITING.ID :
        			lTemp = BOND_UNDERWRITING.getAllCode();
        			break;
        		//85.资金划拨
           		case (int) CAPITAL_TRANSFER.ID :
        			lTemp = CAPITAL_TRANSFER.getAllCode();
           			break;
           		default:
           			lTemp = null;
        	}
        	return lTemp;
		}
		
		//Modify by leiyang date 2007/09/07
		//用于取得业务的交易类型名称
		public static final String getBusinessTransName(long bussinessType, long transId){
        	String strTemp = null;
        	switch ((int) bussinessType)
        	{
        		//16.股票一级网上申购
        		case (int) STOCK_BID_ONLINE.ID :
        			strTemp = STOCK_BID_ONLINE.getName(transId);
        			break;
        		//17.股票一级网下申购
        		case (int) STOCK_BID.ID :
        			strTemp = STOCK_BID.getName(transId);
        			break;
        		//18.股票二级
        		case (int) STOCK_TRANSACTION.ID :
        			strTemp = STOCK_TRANSACTION.getName(transId);
        			break;
        		//21.央行票据一级 
        		case (int) CENTRAL_BANK_NOTE_BID.ID :
        			strTemp = CENTRAL_BANK_NOTE_BID.getName(transId);
        			break;
        		//22.央行票据二级 
        		case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
        			strTemp = CENTRAL_BANK_NOTE_TRANSACTION.getName(transId);
        			break;
        		//26.银行间债券回购
        		case (int) BANK_BOND_REPURCHASE.ID :
        			strTemp = BANK_BOND_REPURCHASE.getName(transId);
        			break;
        		//27.交易所债券回购 
        		case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
        			strTemp = EXCHANGECENTER_BOND_REPURCHASE.getName(transId);
        			break;
        		//31.银行间国债一级 
        		case (int) BANK_NATIONAL_BOND_BID.ID :
        			strTemp = BANK_NATIONAL_BOND_BID.getName(transId);
        			break;
        		//32.银行间国债二级 
        		case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
        			strTemp = BANK_NATIONAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//33.交易所国债一级 
        		case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
        			strTemp = EXCHANGECENTER_NATIONAL_BOND_BID.getName(transId);
        			break;
        		//34.交易所国债二级
        		case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
        			strTemp = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//36.金融债一级
        		case (int) FINANCIAL_BOND_BID.ID :
        			strTemp = FINANCIAL_BOND_BID.getName(transId);
        			break;
        		//37.金融债二级
        		case (int) FINANCIAL_BOND_TRANSACTION.ID :
        			strTemp = FINANCIAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//41.政策性金融债一级 
        		case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
        			strTemp = POLICY_RELATED_FINANCIAL_BOND_BID.getName(transId);
        			break;
        		//42.政策性金融债二级
        		case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
        			strTemp = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//46.企业债一级
        		case (int) ENTERPRISE_BOND_BID.ID :
        			strTemp = ENTERPRISE_BOND_BID.getName(transId);
        			break;
        		//47.企业债二级
        		case (int) ENTERPRISE_BOND_TRANSACTION.ID :
        			strTemp = ENTERPRISE_BOND_TRANSACTION.getName(transId);
        			break;
        		//51.可转债一级网上申购 
        		case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
        			strTemp = TRANSFORMABLE_BOND_BID_ONLINE.getName(transId);
        			break;
        		//52.可转债一级网下申购
        		case (int) TRANSFORMABLE_BOND_BID.ID :
        			strTemp = TRANSFORMABLE_BOND_BID.getName(transId);
        			break;
        		//53.可转债二级
        		case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
        			strTemp = TRANSFORMABLE_BOND_TRANSACTION.getName(transId);
        			break;
        		//54.债转股 
        		case (int) DEBT_TO_EQUITY.ID :
        			strTemp = DEBT_TO_EQUITY.getName(transId);
        			break;
        		//56.封闭式基金一级网上申购
        		case (int) ENCLOSED_FUND_BID_ONLINE.ID :
        			strTemp = ENCLOSED_FUND_BID_ONLINE.getName(transId);
        			break;
        		//57.封闭式基金一级网下申购
        		case (int) ENCLOSED_FUND_BID.ID :
        			strTemp = ENCLOSED_FUND_BID.getName(transId);
        			break;
        		//58.封闭式基金二级
        		case (int) ENCLOSED_FUND_TRANSACTION.ID :
        			strTemp = ENCLOSED_FUND_TRANSACTION.getName(transId);
        			break;
        		//61.开放式基金一级认购 
        		case (int) MUTUAL_FUND_SUBSCRIBE.ID :
        			strTemp = MUTUAL_FUND_SUBSCRIBE.getName(transId);
        			break;
        		//62.开放式基金二级申购
        		case (int) MUTUAL_FUND_BID.ID :
        			strTemp = MUTUAL_FUND_BID.getName(transId);
        			break;
        		//63.开放式基金二级赎回
        		case (int) MUTUAL_FUND_REDEEM.ID :
        			strTemp = MUTUAL_FUND_REDEEM.getName(transId);
        			break;
        		//64.开放式基金分红
        		case (int) MUTUAL_FUND_MELON_CUTTING.ID :
        			strTemp = MUTUAL_FUND_MELON_CUTTING.getName(transId);
        			break;
        		//73.委托理财
        		case (int) ENTRUST_FINANCING.ID :
        			strTemp = ENTRUST_FINANCING.getName(transId);
        			break;
        		//77.结构性投资 
        		case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
        			strTemp = FOREIGN_CURRENCY_INVESTMENT.getName(transId);
        			break;
        		//81.债券承销
        		case (int) BOND_UNDERWRITING.ID :
        			strTemp = BOND_UNDERWRITING.getName(transId);
        			break;
            	//85.资金划拨
        		case (int) CAPITAL_TRANSFER.ID :
        			strTemp = CAPITAL_TRANSFER.getName(transId);
        			break;
        		default:
        			strTemp = "";
        	}
        	return strTemp;
		}
		
		/*是否需要登记回购登记薄*/
		static public boolean isNeedRegiesterRepurchaseRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		/*是否需要登记申购登记薄*/
		static public boolean isNeedRegiesterPurchaseRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		/*是否需要登记长期投资登记薄*/
		static public boolean isNeedRegiesterInvestmentRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		//获取业务类型的详细信息
		public static TransactionTypeInfo getTransactionTypeInfoByID(long id) throws ITreasuryDAOException
		{
			SEC_TransactionTypeDAO dao = new SEC_TransactionTypeDAO();
			TransactionTypeInfo resInfo = (TransactionTypeInfo) dao.findByID(id, TransactionTypeInfo.class);
			return resInfo;
		}
		//11.资金拆入授信
		public static class CAPITAL_IN_CREDIT_EXTENSION
		{
			public static final long ID = 11;
			public static final long CAPITAL_IN_CREDIT_EXTENSION = 1101; //资金拆入授信
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN_CREDIT_EXTENSION :
						strReturn = "资金拆入授信";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN_CREDIT_EXTENSION };
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
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
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
		//12.资金拆出授信
		public static class CAPITAL_OUT_CREDIT_EXTENSION
		{
			public static final long ID = 12;
			public static final long CAPITAL_OUT_CREDIT_EXTENSION = 1201; //资金拆出授信
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) CAPITAL_OUT_CREDIT_EXTENSION :
						strReturn = "资金拆出授信";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_OUT_CREDIT_EXTENSION };
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
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
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
		//13.资金拆借业务
		public static class CAPITAL_LANDING
		{
			public static final long ID = 13;
			public static final long CAPITAL_IN = 1301; //资金拆入
			public static final long CAPITAL_IN_REPAY = 1302; //资金拆入返款
			public static final long CAPITAL_OUT = 1303; //资金拆出 
			public static final long CAPITAL_OUT_REPAY = 1304; //资金拆出返款
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "资金拆入";
						break;
					case (int) CAPITAL_IN_REPAY :
						strReturn = "资金拆入返款";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "资金拆出";
						break;
					case (int) CAPITAL_OUT_REPAY :
						strReturn = "资金拆出返款";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_IN_REPAY, CAPITAL_OUT, CAPITAL_OUT_REPAY };
				return lTemp;
			}
			/**
			  * 显示下拉列表
			  * 
			  * @param out
			  * @param strControlName,
			  *            控件名称
			  * @param nType，控件类型（0，显示全部；1,只显示正向交易）
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
							//华能申请书用
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//华能交割单用
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//华能通知单用
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//国机交割单用
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
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
		//16.股票一级网上申购
		public static class STOCK_BID_ONLINE
		{
			public static final long ID = 16;
			public static final long INITIAL_OFFERING_BID_ONLINE = 1601; //股票首发网上申购
			public static final long INITIAL_OFFERING_BID_WIN = 1602; //股票首发申购中签
			public static final long INITIAL_OFFERING_BID_REPAY = 1603; //股票首发申购返款
			public static final long PROMOTION_BID_ONLINE = 1604; //股票增发网上申购
			public static final long PROMOTION_BID_WIN = 1605; //股票增发申购中签
			public static final long PROMOTION_BID_REPAY = 1606; //股票增发申购返款
			//以下国机使用
			public static final long INITIAL_OFFERING_SELL = 1607; //股票首发卖出
			public static final long INITIAL_OFFERING_QUOTA_CONFIRM = 1608; //股票首发配售中签
			public static final long PROMOTION_SELL = 1609; //股票增发卖出
			public static final long PROMOTION_QUOTA_CONFIRM = 1610; //股票增发配售中签

			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) INITIAL_OFFERING_BID_ONLINE :
						strReturn = "股票首发网上申购";
						break;
					case (int) INITIAL_OFFERING_BID_WIN :
						strReturn = "股票首发申购中签";
						break;
					case (int) INITIAL_OFFERING_BID_REPAY :
						strReturn = "股票首发申购返款";
						break;
					case (int) PROMOTION_BID_ONLINE :
						strReturn = "股票增发网上申购";
						break;
					case (int) PROMOTION_BID_WIN :
						strReturn = "股票增发申购中签";
						break;
					case (int) PROMOTION_BID_REPAY :
						strReturn = "股票增发申购返款";
						break;
						//					以下国机使用
					case (int) INITIAL_OFFERING_SELL :
						strReturn = "股票首发卖出";
						break;
					case (int) INITIAL_OFFERING_QUOTA_CONFIRM :
						strReturn = "股票首发配售中签";
						break;
					case (int) PROMOTION_SELL :
						strReturn = "股票增发卖出";
						break;
					case (int) PROMOTION_QUOTA_CONFIRM :
						strReturn = "股票增发配售中签";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp =
					{
						INITIAL_OFFERING_BID_ONLINE,
						INITIAL_OFFERING_BID_WIN,
						INITIAL_OFFERING_BID_REPAY,
						PROMOTION_BID_ONLINE,
						PROMOTION_BID_WIN,
						PROMOTION_BID_REPAY,
						INITIAL_OFFERING_SELL,
						INITIAL_OFFERING_QUOTA_CONFIRM,
						PROMOTION_SELL,
						PROMOTION_QUOTA_CONFIRM };
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
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[10];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							//						以下国机使用
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = INITIAL_OFFERING_SELL;
							lArrayID[5] = PROMOTION_BID_ONLINE;
							lArrayID[6] = PROMOTION_BID_WIN;
							lArrayID[7] = PROMOTION_BID_REPAY;
							//						以下国机使用
							lArrayID[8] = PROMOTION_QUOTA_CONFIRM;
							lArrayID[9] = PROMOTION_SELL;

							break;
							/**以下用于国机--业务通知单**/
						case 6 :
							lArrayID = new long[4];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[2] = PROMOTION_BID_ONLINE;
							lArrayID[3] = PROMOTION_BID_REPAY;
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
		//17.股票一级网下申购
		public static class STOCK_BID
		{
			public static final long ID = 17;
			public static final long INITIAL_OFFERING_BID = 1701; //股票首发网下申购
			public static final long INITIAL_OFFERING_MARGIN_SUPPLY = 1702; //股票首发差额补款
			public static final long INITIAL_OFFERING_MARGIN_REPAY = 1703; //股票首发差额返款
			public static final long INITIAL_OFFERING_QUOTA_CONFIRM = 1704; //股票首发获配确认
			public static final long PROMOTION_BID = 1705; //股票增发网下申购
			public static final long PROMOTION_BID_MARGIN_SUPPLY = 1706; //股票增发差额补款
			public static final long PROMOTION_BID_MARGIN_REPAY = 1707; //股票增发差额返款
			public static final long PROMOTION_BID_QUOTA_CONFIRM = 1708; //股票增发获配确认
			//			以下国机使用
			public static final long INITIAL_OFFERING_SELL = 1709; //股票首发卖出
			public static final long PROMOTION_SELL = 1710; //股票增发卖出

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) INITIAL_OFFERING_BID :
						strReturn = "股票首发网下申购";
						break;
					case (int) INITIAL_OFFERING_MARGIN_SUPPLY :
						strReturn = "股票首发差额补款";
						break;
					case (int) INITIAL_OFFERING_MARGIN_REPAY :
						strReturn = "股票首发差额返款";
						break;
					case (int) INITIAL_OFFERING_QUOTA_CONFIRM :
						strReturn = "股票首发获配确认";
						break;
					case (int) PROMOTION_BID :
						strReturn = "股票增发网下申购";
						break;
					case (int) PROMOTION_BID_MARGIN_SUPPLY :
						strReturn = "股票增发差额补款";
						break;
					case (int) PROMOTION_BID_MARGIN_REPAY :
						strReturn = "股票增发差额返款";
						break;
					case (int) PROMOTION_BID_QUOTA_CONFIRM :
						strReturn = "股票增发获配确认";
						break;
						//					以下国机使用
					case (int) INITIAL_OFFERING_SELL :
						strReturn = "股票首发卖出";
						break;
					case (int) PROMOTION_SELL :
						strReturn = "股票增发卖出";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp =
					{
						INITIAL_OFFERING_BID,
						INITIAL_OFFERING_MARGIN_SUPPLY,
						INITIAL_OFFERING_MARGIN_REPAY,
						INITIAL_OFFERING_QUOTA_CONFIRM,
						PROMOTION_BID,
						PROMOTION_BID_MARGIN_SUPPLY,
						PROMOTION_BID_MARGIN_REPAY,
						PROMOTION_BID_QUOTA_CONFIRM,
						INITIAL_OFFERING_SELL,
						PROMOTION_SELL };
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
							lArrayID = new long[8];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = PROMOTION_BID;
							lArrayID[5] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[6] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[7] = PROMOTION_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[8];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = PROMOTION_BID;
							lArrayID[5] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[6] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[7] = PROMOTION_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = PROMOTION_BID;
							lArrayID[4] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[5] = PROMOTION_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[10];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							//						以下国机使用
							lArrayID[4] = INITIAL_OFFERING_SELL;

							lArrayID[5] = PROMOTION_BID;
							lArrayID[6] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[7] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[8] = PROMOTION_BID_QUOTA_CONFIRM;
							//						以下国机使用
							lArrayID[9] = PROMOTION_SELL;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//18.股票二级
		public static class STOCK_TRANSACTION
		{
			public static final long ID = 18;
			public static final long STOCK_BUYIN = 1801; //股票买入
			public static final long STOCK_SELL = 1802; //股票卖出
			public static final long STOCK_RATION_ONLINE = 1803; //网上配股
			public static final long STOCK_RATION = 1804; //网下配股
			public static final long STOCK_TAKEBACK = 1805; //送股
			public static final long STOCK_MELON_CUTTING = 1806; //分红
			public static final long STOCK_MELON_CUTTING_CNMEF = 1807; //派息

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) STOCK_BUYIN :
						strReturn = "股票买入";
						break;
					case (int) STOCK_SELL :
						strReturn = "股票卖出";
						break;
					case (int) STOCK_RATION_ONLINE :
						strReturn = "网上配股";
						break;
					case (int) STOCK_RATION :
						strReturn = "网下配股";
						break;
					case (int) STOCK_TAKEBACK :
						strReturn = "送股";
						break;
					case (int) STOCK_MELON_CUTTING :
						strReturn = "分红";
						break;
					case (int) STOCK_MELON_CUTTING_CNMEF :
						strReturn = "派息";
						break;

				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { STOCK_BUYIN, STOCK_SELL, STOCK_RATION_ONLINE, STOCK_RATION, STOCK_TAKEBACK, STOCK_MELON_CUTTING, STOCK_MELON_CUTTING_CNMEF };
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
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING;
							break;
						case 2 :
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_RATION;
							break;
							//国机
						case 5 :
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING_CNMEF;
							break;
							/**以下用于国机---业务通知单**/
						case 6 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_BUYIN;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//21.央行票据一级 
		public static class CENTRAL_BANK_NOTE_BID
		{
			public static final long ID = 21;
			public static final long NOTE_BID = 2101; //央行票据申购
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) NOTE_BID :
						strReturn = "央行票据申购";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { NOTE_BID };
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
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
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
		//22.央行票据二级 
		public static class CENTRAL_BANK_NOTE_TRANSACTION
		{
			public static final long ID = 22;
			public static final long NOTE_BUYIN = 2201; //央行票据买入
			public static final long NOTE_SELL = 2202; //央行票据卖出
			public static final long NOTE_ACCRUAL_IN = 2203; //兑息
			public static final long NOTE_MATURITY_REPAY = 2204; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) NOTE_BUYIN :
						strReturn = "央行票据买入";
						break;
					case (int) NOTE_SELL :
						strReturn = "央行票据卖出";
						break;
					case (int) NOTE_ACCRUAL_IN :
						strReturn = "兑息";
						break;
					case (int) NOTE_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { NOTE_BUYIN, NOTE_SELL, NOTE_ACCRUAL_IN, NOTE_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
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
		//26.银行间债券回购
		public static class BANK_BOND_REPURCHASE
		{
			public static final long ID = 26;
			public static final long FUND_REPURCHASE = 2601; //正回购
			public static final long FUND_REPAY = 2602; //正回购到期
			public static final long BOND_REPURCHASE = 2603; //逆回购
			public static final long BOND_REPAY = 2604; //逆回购到期
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_REPURCHASE :
						strReturn = "正回购";
						break;
					case (int) FUND_REPAY :
						strReturn = "正回购到期";
						break;
					case (int) BOND_REPURCHASE :
						strReturn = "逆回购";
						break;
					case (int) BOND_REPAY :
						strReturn = "逆回购到期";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REPURCHASE, FUND_REPAY, BOND_REPURCHASE, BOND_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
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
		//27.交易所债券回购 
		public static class EXCHANGECENTER_BOND_REPURCHASE
		{
			public static final long ID = 27;
			public static final long FUND_REPURCHASE = 2701; //正回购
			public static final long FUND_REPAY = 2702; //正购回
			public static final long BOND_REPURCHASE = 2703; //逆回购
			public static final long BOND_REPAY = 2704; //逆购回
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_REPURCHASE :
						strReturn = "正回购";
						break;
					case (int) FUND_REPAY :
						strReturn = "正购回";
						break;
					case (int) BOND_REPURCHASE :
						strReturn = "逆回购";
						break;
					case (int) BOND_REPAY :
						strReturn = "逆购回";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REPURCHASE, FUND_REPAY, BOND_REPURCHASE, BOND_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
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
		//31.银行间国债一级 
		public static class BANK_NATIONAL_BOND_BID
		{
			public static final long ID = 31;
			public static final long BOND_BID = 3101; //银行间国债申购
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "银行间国债申购";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
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
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
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
		//32.银行间国债二级 
		public static class BANK_NATIONAL_BOND_TRANSACTION
		{
			public static final long ID = 32;
			public static final long BOND_BUYIN = 3201; //银行间国债买入
			public static final long BOND_SELL = 3202; //银行间国债卖出
			public static final long BOND_ACCRUAL_IN = 3203; //兑息
			public static final long BOND_MATURITY_REPAY = 3204; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "银行间国债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "银行间国债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "兑息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
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
		//33.交易所国债一级 
		public static class EXCHANGECENTER_NATIONAL_BOND_BID
		{
			public static final long ID = 33;
			public static final long BOND_BID = 3301; //交易所国债申购
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "交易所国债申购";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
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
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
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
		//34.交易所国债二级
		public static class EXCHANGECENTER_NATIONAL_BOND_TRANSACTION
		{
			public static final long ID = 34;
			public static final long BOND_BUYIN = 3401; //交易所国债买入
			public static final long BOND_SELL = 3402; //交易所国债卖出
			public static final long BOND_ACCRUAL_IN = 3403; //兑息
			public static final long BOND_MATURITY_REPAY = 3404; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "交易所国债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "交易所国债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "兑息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
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
		//36.金融债一级
		public static class FINANCIAL_BOND_BID
		{
			public static final long ID = 36;
			public static final long BOND_BID = 3601; //金融债申购
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "金融债申购";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
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
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
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
		//37.金融债二级
		public static class FINANCIAL_BOND_TRANSACTION
		{
			public static final long ID = 37;
			public static final long BOND_BUYIN = 3701; //金融债买入
			public static final long BOND_SELL = 3702; //金融债卖出
			public static final long BOND_ACCRUAL_IN = 3703; //兑息
			public static final long BOND_MATURITY_REPAY = 3704; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "金融债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "金融债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "兑息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
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
		//41.政策性金融债一级 
		public static class POLICY_RELATED_FINANCIAL_BOND_BID
		{
			public static final long ID = 41;
			public static final long BOND_BID = 4101; //政策性金融债申购
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "政策性金融债申购";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
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
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
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
		//42.政策性金融债二级
		public static class POLICY_RELATED_FINANCIAL_BOND_TRANSACTION
		{
			public static final long ID = 42;
			public static final long BOND_BUYIN = 4201; //政策性金融债买入
			public static final long BOND_SELL = 4202; //政策性金融债卖出
			public static final long BOND_ACCRUAL_IN = 4203; //兑息
			public static final long BOND_MATURITY_REPAY = 4204; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "政策性金融债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "政策性金融债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "兑息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
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
		//46.企业债一级
		public static class ENTERPRISE_BOND_BID
		{
			public static final long ID = 46;
			public static final long BOND_BID = 4601; //企业债申购
			public static final long BOND_BID_MARGIN_SUPPLY = 4602; //企业债申购差额补款
			public static final long BOND_BID_MARGIN_REPAY = 4603; //企业债申购差额返款
			public static final long BOND_BID_QUOTA_CONFIRM = 4604; //企业债申购获配确认
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "企业债申购";
						break;
					case (int) BOND_BID_MARGIN_SUPPLY :
						strReturn = "企业债申购差额补款";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "企业债申购差额返款";
						break;
					case (int) BOND_BID_QUOTA_CONFIRM :
						strReturn = "企业债申购获配确认";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID, BOND_BID_MARGIN_SUPPLY, BOND_BID_MARGIN_REPAY, BOND_BID_QUOTA_CONFIRM };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//47.企业债二级
		public static class ENTERPRISE_BOND_TRANSACTION
		{
			public static final long ID = 47;
			public static final long BOND_BUYIN = 4701; //企业债买入
			public static final long BOND_SELL = 4702; //企业债卖出
			public static final long BOND_ACCRUAL_IN = 4703; //派息
			public static final long BOND_MATURITY_REPAY = 4704; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "企业债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "企业债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "派息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
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
		//51.可转债一级网上申购 
		public static class TRANSFORMABLE_BOND_BID_ONLINE
		{
			public static final long ID = 51;
			public static final long BOND_BID_ONLINE = 5101; //可转债网上申购
			public static final long BOND_BID_WIN = 5102; //可转债申购中签
			public static final long BOND_BID_REPAY = 5103; //可转债申购返款
			//国机使用
			public static final long BOND_SELL = 5104; //可转债网上申购卖出
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID_ONLINE :
						strReturn = "可转债网上申购";
						break;
					case (int) BOND_BID_WIN :
						strReturn = "可转债申购中签";
						break;
					case (int) BOND_BID_REPAY :
						strReturn = "可转债申购返款";
						break;
						//					国机使用
					case (int) BOND_SELL :
						strReturn = "可转债网上申购卖出";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID_ONLINE, BOND_BID_WIN, BOND_BID_REPAY, BOND_SELL };
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
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							//							国机使用
							lArrayID[3] = BOND_SELL;
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
		//52.可转债一级网下申购
		public static class TRANSFORMABLE_BOND_BID
		{
			public static final long ID = 52;
			public static final long BOND_BID = 5201; //可转债网下申购
			public static final long BOND_BID_MARGIN_SUPPAY = 5202; //可转债申购差额补款
			public static final long BOND_BID_MARGIN_REPAY = 5203; //可转债申购差额返款
			public static final long BOND_BID_QUOTA_CONFIRM = 5204; //可转债申购获配确认
			//			国机使用
			public static final long BOND_SELL = 5205; //可转债网下申购卖出
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "可转债网下申购";
						break;
					case (int) BOND_BID_MARGIN_SUPPAY :
						strReturn = "可转债申购差额补款";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "可转债申购差额返款";
						break;
					case (int) BOND_BID_QUOTA_CONFIRM :
						strReturn = "可转债申购获配确认";
						break;
						//					国机使用
					case (int) BOND_SELL :
						strReturn = "可转债网下申购卖出";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID, BOND_BID_MARGIN_SUPPAY, BOND_BID_MARGIN_REPAY, BOND_BID_QUOTA_CONFIRM, BOND_SELL };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[5];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							//							国机使用
							lArrayID[4] = BOND_SELL;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//53.可转债二级
		public static class TRANSFORMABLE_BOND_TRANSACTION
		{
			public static final long ID = 53;
			public static final long BOND_BUYIN = 5301; //可转债买入
			public static final long BOND_SELL = 5302; //可转债卖出
			public static final long BOND_ACCRUAL_IN = 5303; //派息
			public static final long BOND_MATURITY_REPAY = 5304; //到期还本
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "可转债买入";
						break;
					case (int) BOND_SELL :
						strReturn = "可转债卖出";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "派息";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "到期还本";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
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
		//54.债转股 
		public static class DEBT_TO_EQUITY
		{
			public static final long ID = 54;
			public static final long DEBT_TO_EQUITY = 5401; //债转股
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) DEBT_TO_EQUITY :
						strReturn = "债转股";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { DEBT_TO_EQUITY };
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
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
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
		//56.封闭式基金一级网上申购
		public static class ENCLOSED_FUND_BID_ONLINE
		{
			public static final long ID = 56;
			public static final long FUND_BID_ONLINE = 5601; //封闭式基金网上申购
			public static final long FUND_BID_WIN = 5602; //封闭式基金申购中签
			public static final long FUND_BID_REPAY = 5603; //封闭式基金申购返款
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_BID_ONLINE :
						strReturn = "封闭式基金网上申购";
						break;
					case (int) FUND_BID_WIN :
						strReturn = "封闭式基金申购中签";
						break;
					case (int) FUND_BID_REPAY :
						strReturn = "封闭式基金申购返款";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID_ONLINE, FUND_BID_WIN, FUND_BID_REPAY };
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
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
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
		//57.封闭式基金一级网下申购
		public static class ENCLOSED_FUND_BID
		{
			public static final long ID = 57;
			public static final long FUND_BID = 5701; //封闭式基金网下申购
			public static final long FUND_BID_MARGIN_SUPPLY = 5702; //封闭式基金申购差额补款
			public static final long FUND_BID_MARGIN_REPAY = 5703; //封闭式基金申购差额返款
			public static final long FUND_BID_QUOTA_CONFIRM = 5704; //封闭式基金申购获配确认
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_BID :
						strReturn = "封闭式基金网下申购";
						break;
					case (int) FUND_BID_MARGIN_SUPPLY :
						strReturn = "封闭式基金申购差额补款";
						break;
					case (int) FUND_BID_MARGIN_REPAY :
						strReturn = "封闭式基金申购差额返款";
						break;
					case (int) FUND_BID_QUOTA_CONFIRM :
						strReturn = "封闭式基金申购获配确认";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID, FUND_BID_MARGIN_SUPPLY, FUND_BID_MARGIN_REPAY, FUND_BID_QUOTA_CONFIRM };
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
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;

							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//58.封闭式基金二级
		public static class ENCLOSED_FUND_TRANSACTION
		{
			public static final long ID = 58;
			public static final long FUND_BUYIN = 5801; //封闭式基金买入
			public static final long FUND_SELL = 5802; //封闭式基金卖出
			public static final long FUND_MELON_CUTTING = 5803; //封闭式基金分红
			public static final long FUND_MATURITY_SETTLE = 5804; //封闭式基金到期清算
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_BUYIN :
						strReturn = "封闭式基金买入";
						break;
					case (int) FUND_SELL :
						strReturn = "封闭式基金卖出";
						break;
					case (int) FUND_MELON_CUTTING :
						strReturn = "封闭式基金分红";
						break;
					case (int) FUND_MATURITY_SETTLE :
						strReturn = "封闭式基金到期清算";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BUYIN, FUND_SELL, FUND_MELON_CUTTING, FUND_MATURITY_SETTLE };
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
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
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
		//61.开放式基金一级认购 
		public static class MUTUAL_FUND_SUBSCRIBE
		{
			public static final long ID = 61;
			public static final long FUND_SUBSCRIBE = 6101; //开放式基金一级认购
			public static final long FUND_SUBSCRIBE_CONFIRM = 6102; //开放式基金一级认购确认
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_SUBSCRIBE :
						strReturn = "开放式基金一级认购";
						break;
					case (int) FUND_SUBSCRIBE_CONFIRM :
						strReturn = "开放式基金一级认购确认";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_SUBSCRIBE, FUND_SUBSCRIBE_CONFIRM };
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
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_SUBSCRIBE;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//62.开放式基金二级申购
		public static class MUTUAL_FUND_BID
		{
			public static final long ID = 62;
			public static final long FUND_BID = 6201; //开放式基金二级申购
			public static final long FUND_BID_CONFIRM = 6202; //开放式基金二级申购确认
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_BID :
						strReturn = "开放式基金二级申购";
						break;
					case (int) FUND_BID_CONFIRM :
						strReturn = "开放式基金二级申购确认";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID, FUND_BID_CONFIRM };
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
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_BID;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//63.开放式基金二级赎回
		public static class MUTUAL_FUND_REDEEM
		{
			public static final long ID = 63;
			public static final long FUND_REDEEM = 6301; //开放式基金二级赎回
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_REDEEM :
						strReturn = "开放式基金二级赎回";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REDEEM };
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
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
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
		//64.开放式基金分红
		public static class MUTUAL_FUND_MELON_CUTTING
		{
			public static final long ID = 64;
			public static final long FUND_CASH_MELON_CUTTING = 6401; //开放式基金现金分红
			public static final long FUND_SHARE_MELON_CUTTING = 6402; //开放式基金份额分红
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_CASH_MELON_CUTTING :
						strReturn = "开放式基金现金分红";
						break;
					case (int) FUND_SHARE_MELON_CUTTING :
						strReturn = "开放式基金份额分红";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_CASH_MELON_CUTTING, FUND_SHARE_MELON_CUTTING };
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
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
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
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//71.资产回购
		public static class CAPITAL_REPURCHASE
		{
            public static final long ID = 71;
            public static final long CAPITAL_REPURCHASE = 7101; //资产回购
            public static final long REPURCHASE_NOTIFY = 7102; //合同卖出（回购）
            public static final long PAYBACK_NOTIFY = 7103; //合同卖出购回（回购）
            public static final long INTEREST_PAYMENT = 7104; //利息支付通知单
            public static final long INTEREST_PLAN = 7105; //计提利息
            public static final long AVERAGE_NOTIFY = 7106; //合同买入（回购）
            public static final long ACCEPT_NOTIFY = 7107; //合同买入购回（回购）
            public static final long BREAK_NOTIFY = 7108; //合同卖出（买断）
            public static final long INVEST_BREAK = 7109; //合同买入（买断）
            
            
            public static final long CAPITAL_PAYMENT = 7110; //支付转让款项
            public static final long INTEREST_PAYBACK = 7111; //利息收回通知单
            public static final long REPURCHASE_CAPITAL = 7112; //买入（回购）购回通知单
            public static final long ACCEPT_CAPITAL = 7113; //卖出（回购）购回通知单
            public static final long CAPITAL_PAYBACK = 7114; //收到转让款项
           
            
            //
            public static final String getName(long lCode)
            {
                String strReturn = ""; //初始化返回值
                switch ((int) lCode)
                {
                    case (int) CAPITAL_REPURCHASE :
                        strReturn = "资产回购";
                        break;
                    case (int) REPURCHASE_NOTIFY :
                        strReturn = "卖出（回购）";
                        break;
                    case (int) PAYBACK_NOTIFY :
                        strReturn = "卖出购回（回购）";
                        break;
                    case (int) INTEREST_PAYMENT :
                        strReturn = "利息支付通知单";
                        break;
                    case (int) INTEREST_PLAN :
                        strReturn = "计提利息";
                        break;
                    case (int) AVERAGE_NOTIFY :
                        strReturn = "买入（回购）";
                        break;
                    case (int) ACCEPT_NOTIFY :
                        strReturn = "买入购回（回购）";
                        break;
                    case (int) BREAK_NOTIFY :
                        strReturn = "卖出（买断）";
                        break;
                    case (int) INVEST_BREAK :
                        strReturn = "买入（买断）";
                        break;
                    case (int) CAPITAL_PAYMENT :
                        strReturn = "支付转让款项";
                        break;
                    case (int) INTEREST_PAYBACK :
                        strReturn = "利息收回通知单";
                        break;
                    case (int) REPURCHASE_CAPITAL :
                        strReturn = "买入（回购）购回通知单";
                        break;
                    case (int) ACCEPT_CAPITAL :
                        strReturn = "卖出（回购）购回通知单";
                        break;
                    case (int) CAPITAL_PAYBACK :
                        strReturn = "收到转让款项";
                        break;
                }
                return strReturn;
            }
            //
            public static final long[] getAllCode()
            {
                long[] lTemp = { CAPITAL_REPURCHASE, REPURCHASE_NOTIFY, PAYBACK_NOTIFY, INTEREST_PAYMENT };
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
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = PAYBACK_NOTIFY;
                            lArrayID[2] = INTEREST_PAYMENT;
                            break;
                        case 2 :
                            lArrayID = new long[6];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = PAYBACK_NOTIFY;
                            lArrayID[2] = INTEREST_PAYMENT;
                            lArrayID[3] = AVERAGE_NOTIFY;
                            lArrayID[4] = ACCEPT_NOTIFY;
                            lArrayID[5] = BREAK_NOTIFY;
                            break; 
                        case 3 :
                            lArrayID = new long[4];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = AVERAGE_NOTIFY;
                            lArrayID[2] = BREAK_NOTIFY;
                            lArrayID[3] = INVEST_BREAK;
                            break;
                        case 4 :
                            lArrayID = new long[2];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = BREAK_NOTIFY;
                            break;
                        case 5 :
                            lArrayID = new long[3];
                            lArrayID[0] = CAPITAL_PAYMENT;
                            lArrayID[1] = INTEREST_PAYBACK;
                            lArrayID[2] = REPURCHASE_CAPITAL;
                            break;
                        case 6 :
                            lArrayID = new long[1];
                            lArrayID[0] = CAPITAL_PAYMENT;
                            break; 
                        case 7 :
                        	 lArrayID = new long[3];
                             lArrayID[0] = CAPITAL_PAYBACK;
                             lArrayID[1] = INTEREST_PAYMENT;
                             lArrayID[2] = ACCEPT_CAPITAL;
                            break;
                        case 8 :
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
		//73.委托理财
		public static class ENTRUST_FINANCING
		{
			public static final long ID = 73;
			public static final long ENTRUST_FINANCING = 7301; //委托理财
			public static final long CORPORACITY_PAYMENT_NOTIFY = 7302; //本金支付通知单
			public static final long CORPORACITY_DRAWBACK_NOTIFY = 7303; //本金收回通知单
			public static final long INCOME_DRAWBACK_NOTIFY = 7304; //收益收回通知单
			//新加需求
			public static final long SECURITIES_PAYMENT_NOTIFY = 7305; //证券转出业务通知单
			public static final long SECURITIES_DRAWBACK_NOTIFY = 7306; //证券收回业务通知单
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) ENTRUST_FINANCING :
						strReturn = "委托理财";
						break;
					case (int) CORPORACITY_PAYMENT_NOTIFY :
						strReturn = "本金支付通知单";
						break;
					case (int) CORPORACITY_DRAWBACK_NOTIFY :
						strReturn = "本金收回通知单";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "收益收回通知单";
						break;
					case (int) SECURITIES_PAYMENT_NOTIFY :
						strReturn = "证券转出通知单";
						break;
					case (int) SECURITIES_DRAWBACK_NOTIFY :
						strReturn = "证券收回通知单";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { ENTRUST_FINANCING, CORPORACITY_PAYMENT_NOTIFY, CORPORACITY_DRAWBACK_NOTIFY, INCOME_DRAWBACK_NOTIFY, SECURITIES_PAYMENT_NOTIFY, SECURITIES_DRAWBACK_NOTIFY };
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
							lArrayID = new long[5];
							lArrayID[0] = CORPORACITY_PAYMENT_NOTIFY;
							lArrayID[1] = CORPORACITY_DRAWBACK_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
							lArrayID[3] = SECURITIES_PAYMENT_NOTIFY;
							lArrayID[4] = SECURITIES_DRAWBACK_NOTIFY;
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
		//75.受托理财 
		public static class ENTRUSTED_FINANCING
		{
			public static final long ID = 75;
			public static final long ENTRUSTED_FINANCING = 7501; //受托理财
			public static final long CORPORACITY_DRAWBACK_NOTIFY = 7502; //本金收回通知单
			public static final long CORPORACITY_PAYMENT_NOTIFY = 7503; //本金支付通知单
			public static final long INCOME_PAYMENT_NOTIFY = 7504; //收益支付通知单
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) ENTRUSTED_FINANCING :
						strReturn = "委托理财";
						break;
					case (int) CORPORACITY_PAYMENT_NOTIFY :
						strReturn = "本金支付通知单";
						break;
					case (int) CORPORACITY_DRAWBACK_NOTIFY :
						strReturn = "本金收回通知单";
						break;
					case (int) INCOME_PAYMENT_NOTIFY :
						strReturn = "收益支付通知单";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { ENTRUSTED_FINANCING, CORPORACITY_PAYMENT_NOTIFY, CORPORACITY_DRAWBACK_NOTIFY, INCOME_PAYMENT_NOTIFY };
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
							lArrayID = new long[4];
							lArrayID[0] = ENTRUSTED_FINANCING;
							lArrayID[1] = CORPORACITY_DRAWBACK_NOTIFY;
							lArrayID[2] = CORPORACITY_PAYMENT_NOTIFY;
							lArrayID[3] = INCOME_PAYMENT_NOTIFY;
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
		//77.结构性投资 
		public static class FOREIGN_CURRENCY_INVESTMENT
		{
			public static final long ID = 77;
			public static final long FOREIGN_CURRENCY_INVESTMENT = 7701; //结构性投资
			public static final long INVESTMENT_PAYMENT_NOTIFY = 7702; //支付投资款通知单
			public static final long INVESTMENT_DRAWBACK_NOTIFY = 7703; //投资款收回通知单
			public static final long INCOME_DRAWBACK_NOTIFY = 7704; //投资收益收回通知单
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FOREIGN_CURRENCY_INVESTMENT :
						strReturn = "结构性投资";
						break;
					case (int) INVESTMENT_PAYMENT_NOTIFY :
						strReturn = "支付投资款通知单";
						break;
					case (int) INVESTMENT_DRAWBACK_NOTIFY :
						strReturn = "投资款收回通知单";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "投资收益收回通知单";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FOREIGN_CURRENCY_INVESTMENT, INVESTMENT_PAYMENT_NOTIFY, INVESTMENT_DRAWBACK_NOTIFY, INCOME_DRAWBACK_NOTIFY };
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
						case 1 : //结构性投资业务通知单使用
							lArrayID = new long[3];
							lArrayID[0] = INVESTMENT_PAYMENT_NOTIFY;
							lArrayID[1] = INVESTMENT_DRAWBACK_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
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
		//79.股权投资 
		public static class STOCK_INVESTMENT
		{
			public static final long ID = 79;
		}
		//81.债券承销
		public static class BOND_UNDERWRITING
		{
			public static final long ID = 81;
			public static final long BOND_UNDERWRITING = 8101; //债券承销
			public static final long UNDERWRITING_DRAWBACK_NOTIFY = 8102; //债券承销收款通知单
			public static final long UNDERWRITING_PAYMENT_NOTIFY = 8103; //支付债券承销款通知单
			public static final long INCOME_DRAWBACK_NOTIFY = 8104; //承销费收款通知单
			public static final long BOND_DRAWBACK_NOTIFY = 8105; //债券收取通知单
			public static final long CONTRACT_END = 8106; //合同结束
			
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) BOND_UNDERWRITING :
						strReturn = "债券承销";
						break;
					case (int) UNDERWRITING_DRAWBACK_NOTIFY :
						strReturn = "债券承销收款通知单";
						break;
					case (int) UNDERWRITING_PAYMENT_NOTIFY :
						strReturn = "支付债券承销款通知单";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "承销费收款通知单";
						break;
					case (int) BOND_DRAWBACK_NOTIFY :
						strReturn = "债券收取通知单";
						break;
					case (int) CONTRACT_END :
						strReturn = "合同结束";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_UNDERWRITING, BOND_UNDERWRITING, UNDERWRITING_PAYMENT_NOTIFY, INCOME_DRAWBACK_NOTIFY, UNDERWRITING_DRAWBACK_NOTIFY };
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
							lArrayID = new long[4];
							lArrayID[0] = BOND_DRAWBACK_NOTIFY;
							lArrayID[1] = UNDERWRITING_PAYMENT_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
							lArrayID[3] = UNDERWRITING_DRAWBACK_NOTIFY;
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
		//83.保险代理
		public static class INSURANCE
		{
			public static final long ID = 83;
		}
		//85.资金划拨  
		public static class CAPITAL_TRANSFER
		{
			public static final long ID = 85;
			public static final long CAPITAL_IN = 8501; //资金存入
			public static final long CAPITAL_OUT = 8502; //资金取出
			//股票网上
			public static final long INITIAL_OFFERING_BID_ONLINE = 8503; //股票首发网上申购资金划拨
			public static final long INITIAL_OFFERING_BID_REPAY = 8504; //股票首发申购返款资金划拨
			public static final long PROMOTION_BID_ONLINE = 8505; //股票增发网上申购资金划拨
			public static final long PROMOTION_BID_REPAY = 8506; //股票增发申购返款资金划拨
			//股票二级
			public static final long STOCK_BUYIN = 8507; //股票买入资金划拨
			//可转债网上
			public static final long BOND_BID_ONLINE = 8508; //可转债网上申购资金划拨
			public static final long BOND_BID_REPAY = 8509; //可转债申购返款资金划拨
			//			可转债二级
			public static final long BOND_BUYIN = 8510; //可转债买入资金划拨
			//			债转股
			public static final long DEBT_TO_EQUITY = 8511; //债转股资金划拨
			//			开放式基金
			public static final long FUND_SUBSCRIBE = 8512; //开放式基金一级认购资金划拨
			public static final long FUND_BID = 8513; //开放式基金二级申购资金划拨
			public static final long FUND_TRANSFER = 8514; //基金转换资金划拨

			//股票网下
			public static final long INITIAL_OFFERING_BID = 8515; //股票首发网下申购资金划拨
			public static final long INITIAL_OFFERING_MARGIN_SUPPLY = 8516; //股票首发差额补款资金划拨
			public static final long INITIAL_OFFERING_MARGIN_REPAY = 8517; //股票首发差额返款资金划拨
			public static final long PROMOTION_BID = 8518; //股票增发网下申购资金划拨
			public static final long PROMOTION_BID_MARGIN_SUPPLY = 8519; //股票增发差额补款资金划拨
			public static final long PROMOTION_BID_MARGIN_REPAY = 8520; //股票增发差额返款资金划拨

			//			可转债网下
			public static final long BOND_BID = 8521; //可转债网下申购资金划拨
			public static final long BOND_BID_MARGIN_SUPPAY = 8522; //可转债申购差额补款资金划拨
			public static final long BOND_BID_MARGIN_REPAY = 8523; //可转债申购差额返款资金划拨

			//			开放式基金
			public static final long FUND_REDEEM = 8524; //开放式基金二级赎回资金划拨
			public static final long FUND_CASH_MELON_CUTTING = 8525; //开放式基金分红资金划拨

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "资金存入";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "资金取出";
						break;

						//股票一级网上	
					case (int) INITIAL_OFFERING_BID_ONLINE :
						strReturn = "股票首发网上申购资金划拨";
						break;
					case (int) INITIAL_OFFERING_BID_REPAY :
						strReturn = "股票首发申购返款资金划拨";
						break;
					case (int) PROMOTION_BID_ONLINE :
						strReturn = "股票增发网上申购资金划拨";
						break;
					case (int) PROMOTION_BID_REPAY :
						strReturn = "股票增发申购返款资金划拨";
						break;

						//股票一级网下	
					case (int) INITIAL_OFFERING_BID :
						strReturn = "股票首发网下申购资金划拨";
						break;
					case (int) INITIAL_OFFERING_MARGIN_SUPPLY :
						strReturn = "股票首发差额补款资金划拨";
						break;
					case (int) INITIAL_OFFERING_MARGIN_REPAY :
						strReturn = "股票首发差额返款资金划拨";
						break;
					case (int) PROMOTION_BID :
						strReturn = "股票增发网下申购资金划拨";
						break;
					case (int) PROMOTION_BID_MARGIN_SUPPLY :
						strReturn = "股票增发差额补款资金划拨";
						break;
					case (int) PROMOTION_BID_MARGIN_REPAY :
						strReturn = "股票增发差额返款资金划拨";
						break;

						//股票二级
					case (int) STOCK_BUYIN :
						strReturn = "股票买入资金划拨";
						break;

						//可转债一级网上
					case (int) BOND_BID_ONLINE :
						strReturn = "可转债网上申购资金划拨";
						break;
					case (int) BOND_BID_REPAY :
						strReturn = "可转债申购返款资金划拨";
						break;
						//可转债一级网下
					case (int) BOND_BID :
						strReturn = "可转债网下申购资金划拨";
						break;
					case (int) BOND_BID_MARGIN_SUPPAY :
						strReturn = "可转债申购差额补款资金划拨";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "可转债申购差额返款资金划拨";
						break;

						//可转债二级
					case (int) BOND_BUYIN :
						strReturn = "可转债买入资金划拨";
						break;
						//债转股
					case (int) DEBT_TO_EQUITY :
						strReturn = "债转股资金划拨";
						break;

						//开放式基金
					case (int) FUND_SUBSCRIBE :
						strReturn = "开放式基金一级认购资金划拨";
						break;
					case (int) FUND_BID :
						strReturn = "开放式基金二级申购资金划拨";
						break;
					case (int) FUND_REDEEM :
						strReturn = "开放式基金二级赎回资金划拨";
						break;
					case (int) FUND_CASH_MELON_CUTTING :
						strReturn = "开放式基金分红资金划拨";
						break;

					case (int) FUND_TRANSFER :
						strReturn = "基金转换资金划拨";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_OUT };
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
						case 3 :
							lArrayID = new long[2];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_OUT;
							break;
							//						股票一级网上	
						case 7 :
							lArrayID = new long[4];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[2] = PROMOTION_BID_ONLINE;
							lArrayID[3] = PROMOTION_BID_REPAY;
							break;
							//						股票二级
						case 8 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_BUYIN;
							break;
							//						可转债一级网上
						case 9 :
							lArrayID = new long[2];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_REPAY;
							break;
							//						债转股
						case 10 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
							//						开放式基金   
						case 11 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_BID;
							lArrayID[2] = FUND_TRANSFER;
							break;
							//						可转债二级
						case 12 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BUYIN;
							break;
							//						股票网下
						case 13 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = PROMOTION_BID;
							lArrayID[4] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[5] = PROMOTION_BID_MARGIN_REPAY;
							break;
							//						可转债网下
						case 14 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
							//						开放式基金   
						case 15 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_REDEEM;
							lArrayID[1] = FUND_CASH_MELON_CUTTING;
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
		//87.资金利息结算 
		public static class INTEREST_SETTLEMENT
		{
			public static final long ID = 87;
			public static final long INTEREST_SETTLEMENT = 8701; //资金利息结算
			public static final long OTHER_CAPITAL_IN = 8702; //其他资金划入
			public static final long OTHER_CAPITAL_OUT = 8703; //其他资金划出

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) INTEREST_SETTLEMENT :
						strReturn = "资金利息结算";
						break;
					case (int) OTHER_CAPITAL_IN :
						strReturn = "其他资金划入";
						break;
					case (int) OTHER_CAPITAL_OUT :
						strReturn = "其他资金划出";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { INTEREST_SETTLEMENT, OTHER_CAPITAL_IN, OTHER_CAPITAL_OUT };
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
							lArrayID[0] = INTEREST_SETTLEMENT;
							lArrayID[1] = OTHER_CAPITAL_IN;
							lArrayID[2] = OTHER_CAPITAL_OUT;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = INTEREST_SETTLEMENT;
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
		//89.其它资金往来  
		public static class OTHER_CAPITAL_SETTLEMENT
		{
			public static final long ID = 89;
			public static final long CAPITAL_IN = 8901; //资金往来收入
			public static final long CAPITAL_OUT = 8902; //资金往来支出
			public static final long VENTURE_FUND_SETTLE = 8903; //缴纳结算风险基金
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "资金存入";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "资金取出";
						break;
					case (int) VENTURE_FUND_SETTLE :
						strReturn = "资金取出";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_OUT, VENTURE_FUND_SETTLE };
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
							lArrayID[0] = CAPITAL_IN;
							lArrayID[0] = CAPITAL_OUT;
							lArrayID[0] = VENTURE_FUND_SETTLE;
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
		//91.证券划转
		public static class SECURITIES_TRANSFER
		{
			public static final long ID = 91;
			public static final long STOCK_IN = 9101; //证券划转

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) STOCK_IN :
						strReturn = "证券划转";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { STOCK_IN };
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
							lArrayID = new long[1];
							lArrayID[0] = STOCK_IN;
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
		//92.基金转换
		public static class FUND_TRANSFER
		{
			public static final long ID = 92;
			public static final long FUND_TRANSFER = 9201; //基金转换

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //初始化返回值
				switch ((int) lCode)
				{
					case (int) FUND_TRANSFER :
						strReturn = "基金转换";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_TRANSFER };
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
							lArrayID = new long[1];
							lArrayID[0] = FUND_TRANSFER;
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
		//93.结转成本 
		public static class CARRY_COST
		{
			public static final long ID = 93;
			public static final long CARRY_COST = 9301; //结转成本 
		}

		//801.审批备注  
		public static class REMARK_APPROVAL
		{
			public static final long ID = 801;
		}
		//802.交割单备注  
		public static class REMARK_TRANSACTION
		{
			public static final long ID = 802;
		}
		//得到业务类型的名字
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				//资金拆借
				case (int) CAPITAL_IN_CREDIT_EXTENSION.ID :
					strReturn = "资金拆入授信";
					break;
				case (int) CAPITAL_OUT_CREDIT_EXTENSION.ID :
					strReturn = "资金拆出授信";
					break;
				case (int) CAPITAL_LANDING.ID :
					strReturn = "资金拆借业务";
					break;
					//股票	
				case (int) STOCK_BID_ONLINE.ID :
					strReturn = "股票一级网上申购";
					break;
				case (int) STOCK_BID.ID :
					strReturn = "股票一级网下申购";
					break;
				case (int) STOCK_TRANSACTION.ID :
					strReturn = "股票二级";
					break;
					//央行票据	
				case (int) CENTRAL_BANK_NOTE_BID.ID :
					strReturn = "央行票据一级";
					break;
				case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
					strReturn = "央行票据二级";
					break;
					//债券回购	
				case (int) BANK_BOND_REPURCHASE.ID :
					strReturn = "银行间债券回购";
					break;
				case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
					strReturn = "交易所债券回购";
					break;
					//国债	
				case (int) BANK_NATIONAL_BOND_BID.ID :
					strReturn = "银行间国债一级";
					break;
				case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
					strReturn = "银行间国债二级";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
					strReturn = "交易所国债一级";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
					strReturn = "交易所国债二级";
					break;
					//金融债	
				case (int) FINANCIAL_BOND_BID.ID :
					strReturn = "金融债一级";
					break;
				case (int) FINANCIAL_BOND_TRANSACTION.ID :
					strReturn = "金融债二级";
					break;
					//政策性金融债	
				case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
					strReturn = "政策性金融债一级";
					break;
				case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
					strReturn = "政策性金融债二级";
					break;
					//企业债	
				case (int) ENTERPRISE_BOND_BID.ID :
					strReturn = "企业债一级";
					break;
				case (int) ENTERPRISE_BOND_TRANSACTION.ID :
					strReturn = "企业债二级";
					break;
					//可转债	
				case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
					strReturn = "可转债一级网上申购";
					break;
				case (int) TRANSFORMABLE_BOND_BID.ID :
					strReturn = "可转债一级网下申购";
					break;
				case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
					strReturn = "可转债二级";
					break;
				case (int) DEBT_TO_EQUITY.ID :
					strReturn = "债转股";
					break;
					//封闭式基金	
				case (int) ENCLOSED_FUND_BID_ONLINE.ID :
					strReturn = "封闭式基金一级网上申购";
					break;
				case (int) ENCLOSED_FUND_BID.ID :
					strReturn = "封闭式基金一级网下申购";
					break;
				case (int) ENCLOSED_FUND_TRANSACTION.ID :
					strReturn = "封闭式基金二级";
					break;
					//开放式基金	
				case (int) MUTUAL_FUND_SUBSCRIBE.ID :
					strReturn = "开放式基金一级认购";
					break;
				case (int) MUTUAL_FUND_BID.ID :
					strReturn = "开放式基金二级申购";
					break;
				case (int) MUTUAL_FUND_REDEEM.ID :
					strReturn = "开放式基金二级赎回";
					break;
				case (int) MUTUAL_FUND_MELON_CUTTING.ID :
					strReturn = "开放式基金分红";
					break;
				case (int) CAPITAL_REPURCHASE.ID :
					strReturn = "资产回购";
					break;
				case (int) ENTRUST_FINANCING.ID :
					strReturn = "委托理财";
					break;
				case (int) ENTRUSTED_FINANCING.ID :
					strReturn = "受托理财";
					break;
				case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
					strReturn = "结构性投资";
					break;
				case (int) STOCK_INVESTMENT.ID :
					strReturn = "股权投资";
					break;
				case (int) BOND_UNDERWRITING.ID :
					strReturn = "债券承销";
					break;
				case (int) INSURANCE.ID :
					strReturn = "保险代理";
					break;
				case (int) CAPITAL_TRANSFER.ID :
					strReturn = "资金划拨";
					break;
				case (int) INTEREST_SETTLEMENT.ID :
					strReturn = "资金利息结算";
					break;
				case (int) OTHER_CAPITAL_SETTLEMENT.ID :
					strReturn = "其它资金往来";
					break;
				case (int) SECURITIES_TRANSFER.ID :
					strReturn = "证券划转";
					break;
				case (int) FUND_TRANSFER.ID :
					strReturn = "基金划转";
					break;
				case (int) CARRY_COST.ID :
					strReturn = "结转成本";
					break;
				case (int) REMARK_APPROVAL.ID :
					strReturn = "审批备注";
					break;
				case (int) REMARK_TRANSACTION.ID :
					strReturn = "交割单备注";
					break;
			}
			return strReturn;
		}

		//返款
		public final static int TRANS_GROUP_REPAY = 0;
		//补款
		public final static int TRANSTYPE_GROUP_SUPPLY = 1;
		/**
		  * 返回返款还是补款
		  * -1 ：不返款也不补款或者...
		  * 0 ：返款
		  * 1 ：补款
		*/
		public static final long isRepayOrSupply(long transactionTypeId)
		{
			long lReturn = -1; //初始化返回值

			switch ((int) transactionTypeId)
			{
				//资金拆借业务	
				case (int) CAPITAL_LANDING.CAPITAL_IN_REPAY :
				case (int) CAPITAL_LANDING.CAPITAL_OUT_REPAY :
					lReturn = TRANS_GROUP_REPAY; //返款
					break;

					//股票一级网上申购	
				case (int) STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
				case (int) STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//返款
					lReturn = -1; //不需要进行判断
					break;

					//股票一级网下申购
				case (int) STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
				case (int) STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
					lReturn = TRANS_GROUP_REPAY; //返款
					break;

				case (int) STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
				case (int) STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //补款
					break;

					//企业债	
				case (int) ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//返款
					lReturn = -1; //不需要进行判断
					break;
				case (int) ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
					//lReturn = TRANSTYPE_GROUP_SUPPLY;//补款
					lReturn = -1; //不需要进行判断
					break;

					//可转债	
				case (int) TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
				case (int) TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//返款
					lReturn = -1; //不需要进行判断
					break;
				case (int) TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //补款
					break;

					//封闭式基金	
				case (int) ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
					lReturn = -1; //不需要进行判断
				case (int) ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
					lReturn = TRANS_GROUP_REPAY; //返款
					break;
				case (int) ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //补款
					break;
				default :
					lReturn = -1; //不返款也不补款

			}
			return lReturn;

		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					CAPITAL_IN_CREDIT_EXTENSION.ID,
					CAPITAL_OUT_CREDIT_EXTENSION.ID,
					CAPITAL_LANDING.ID,
					STOCK_BID_ONLINE.ID,
					STOCK_BID.ID,
					STOCK_TRANSACTION.ID,
					CENTRAL_BANK_NOTE_BID.ID,
					CENTRAL_BANK_NOTE_TRANSACTION.ID,
					BANK_BOND_REPURCHASE.ID,
					EXCHANGECENTER_BOND_REPURCHASE.ID,
					BANK_NATIONAL_BOND_BID.ID,
					BANK_NATIONAL_BOND_TRANSACTION.ID,
					EXCHANGECENTER_NATIONAL_BOND_BID.ID,
					EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID,
					FINANCIAL_BOND_BID.ID,
					FINANCIAL_BOND_TRANSACTION.ID,
					POLICY_RELATED_FINANCIAL_BOND_BID.ID,
					POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID,
					ENTERPRISE_BOND_BID.ID,
					ENTERPRISE_BOND_TRANSACTION.ID,
					TRANSFORMABLE_BOND_BID_ONLINE.ID,
					TRANSFORMABLE_BOND_BID.ID,
					TRANSFORMABLE_BOND_TRANSACTION.ID,
					DEBT_TO_EQUITY.ID,
					ENCLOSED_FUND_BID_ONLINE.ID,
					ENCLOSED_FUND_BID.ID,
					ENCLOSED_FUND_TRANSACTION.ID,
					MUTUAL_FUND_SUBSCRIBE.ID,
					MUTUAL_FUND_BID.ID,
					MUTUAL_FUND_REDEEM.ID,
					MUTUAL_FUND_MELON_CUTTING.ID,
					CAPITAL_REPURCHASE.ID,
					ENTRUST_FINANCING.ID,
					ENTRUSTED_FINANCING.ID,
					FOREIGN_CURRENCY_INVESTMENT.ID,
					STOCK_INVESTMENT.ID,
					BOND_UNDERWRITING.ID,
					INSURANCE.ID,
					CAPITAL_TRANSFER.ID,
					INTEREST_SETTLEMENT.ID,
					OTHER_CAPITAL_SETTLEMENT.ID,
					SECURITIES_TRANSFER.ID,
					FUND_TRANSFER.ID,
					CARRY_COST.ID,
					REMARK_APPROVAL.ID,
					REMARK_TRANSACTION.ID };
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
						lArrayID = new long[42];
						lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION.ID;
						lArrayID[1] = CAPITAL_OUT_CREDIT_EXTENSION.ID;
						lArrayID[2] = CAPITAL_LANDING.ID;
						lArrayID[3] = STOCK_BID_ONLINE.ID;
						lArrayID[4] = STOCK_BID.ID;
						lArrayID[5] = STOCK_TRANSACTION.ID;
						lArrayID[6] = CENTRAL_BANK_NOTE_BID.ID;
						lArrayID[7] = CENTRAL_BANK_NOTE_TRANSACTION.ID;
						lArrayID[8] = BANK_BOND_REPURCHASE.ID;
						lArrayID[9] = EXCHANGECENTER_BOND_REPURCHASE.ID;
						lArrayID[10] = BANK_NATIONAL_BOND_BID.ID;
						lArrayID[11] = BANK_NATIONAL_BOND_TRANSACTION.ID;
						lArrayID[12] = EXCHANGECENTER_NATIONAL_BOND_BID.ID;
						lArrayID[13] = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID;
						lArrayID[14] = FINANCIAL_BOND_BID.ID;
						lArrayID[15] = FINANCIAL_BOND_TRANSACTION.ID;
						lArrayID[16] = POLICY_RELATED_FINANCIAL_BOND_BID.ID;
						lArrayID[17] = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID;
						lArrayID[18] = ENTERPRISE_BOND_BID.ID;
						lArrayID[19] = ENTERPRISE_BOND_TRANSACTION.ID;
						lArrayID[20] = TRANSFORMABLE_BOND_BID_ONLINE.ID;
						lArrayID[21] = TRANSFORMABLE_BOND_BID.ID;
						lArrayID[22] = TRANSFORMABLE_BOND_TRANSACTION.ID;
						lArrayID[23] = DEBT_TO_EQUITY.ID;
						lArrayID[24] = ENCLOSED_FUND_BID_ONLINE.ID;
						lArrayID[25] = ENCLOSED_FUND_BID.ID;
						lArrayID[26] = ENCLOSED_FUND_TRANSACTION.ID;
						lArrayID[27] = MUTUAL_FUND_SUBSCRIBE.ID;
						lArrayID[28] = MUTUAL_FUND_BID.ID;
						lArrayID[29] = MUTUAL_FUND_REDEEM.ID;
						lArrayID[30] = MUTUAL_FUND_MELON_CUTTING.ID;
						lArrayID[31] = CAPITAL_REPURCHASE.ID;
						lArrayID[32] = ENTRUST_FINANCING.ID;
						lArrayID[33] = ENTRUSTED_FINANCING.ID;
						lArrayID[34] = FOREIGN_CURRENCY_INVESTMENT.ID;
						lArrayID[35] = STOCK_INVESTMENT.ID;
						lArrayID[36] = BOND_UNDERWRITING.ID;
						lArrayID[37] = INSURANCE.ID;
						lArrayID[38] = CAPITAL_TRANSFER.ID;
						lArrayID[39] = INTEREST_SETTLEMENT.ID;
						lArrayID[40] = OTHER_CAPITAL_SETTLEMENT.ID;
						lArrayID[41] = SECURITIES_TRANSFER.ID;
						lArrayID[42] = CARRY_COST.ID;
						lArrayID[43] = REMARK_APPROVAL.ID;
						lArrayID[44] = REMARK_TRANSACTION.ID;
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
	//申请书状态
	public static class ApplyFormStatus
	{
		public static final long CANCELED = 0; //已取消
		public static final long SUBMITED = 1; //已保存
		public static final long CHECKED = 2; //已审批
		public static final long USED = 3; //已使用
		public static final long COMPLETED = 4; //已结束
		public static final long REJECTED = 5; //已拒绝
		public static final long APPROVALING = 6;//审核中
		/**
		 * 已审批，统一用已审核
		 * @Deprecated
		 */
		public static final long APPROVALED = 7;  		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SUBMITED :
					strReturn = "已保存";
					break;
				case (int) CHECKED :
					strReturn = "已审批";
					break;
				case (int) USED :
					strReturn = "已使用";
					break;
				case (int) COMPLETED :
					strReturn = "已结束";
					break;
				case (int) CANCELED :
					strReturn = "已删除";
					break;
				case (int) REJECTED :
					strReturn = "已拒绝";
					break;
				case (int) APPROVALING :	
					strReturn = "审批中";
				    break;
				case (int) APPROVALED :	
					strReturn = "已审批";
				    break;				
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SUBMITED, CHECKED, USED, COMPLETED, CANCELED, REJECTED,APPROVALING };
			return lTemp;
		}
		/**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型（0，显示全部；1,没有清户选项,2,显示已符合和已使用两项）
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
						lArrayID = new long[7];
						lArrayID[0] = SUBMITED;
						lArrayID[1] = CHECKED;
						lArrayID[2] = USED;
						lArrayID[3] = COMPLETED;
						lArrayID[4] = CANCELED;
						lArrayID[5] = REJECTED;
						lArrayID[6] = APPROVALING;
						break;
					case 2 :
						lArrayID = new long[2];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
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
		 * 交割单录入查找申请书专用
		 * @param out
		 * @param strControlName
		 * @param nType
		 * @param strSelectValue
		 * @param isNeedAll
		 * @param isNeedBlank
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, String strSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			String[] strArrayID = new String[3];
			String[] strArrayName = new String[3];
			strArrayID = new String[3];
			strArrayName = new String[3];
			strArrayID[0] = CHECKED + "," + USED;
			strArrayID[1] = "" + CHECKED;
			strArrayID[2] = "" + USED;
			strArrayName[0] = "";
			strArrayName[1] = getName(CHECKED);
			strArrayName[2] = getName(USED);
			showCommonList(out, strControlName, strArrayID, strArrayName, strSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}
	//交割单状态
	public static class DeliveryOrderStatus
	{
		public static final long DELETED = 0; //已删除
		public static final long TEMPSAVED = 1; //已暂存
		public static final long SAVED = 2; //已保存
		public static final long CHECKED = 3; //已复核
		public static final long USED = 4; //已使用
		public static final long POSTED = 5; //已过账
		public static final long APPROVALING = 10; //审批中
		public static final long APPROVALED = 20;  //已审批
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) TEMPSAVED :
					strReturn = "已暂存";
					break;
				case (int) SAVED :
					strReturn = "已保存";
					break;
				case (int) CHECKED :
					strReturn = "已复核";
					break;
				case (int) USED :
					strReturn = "已使用";
					break;
				case (int) DELETED :
					strReturn = "已删除";
					break;
				case (int) POSTED :
					strReturn = "已过账";
					break;
				case (int) APPROVALING :
					strReturn = "审批中";
					break;
				case (int) APPROVALED :
					strReturn = "已审批";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { TEMPSAVED, SAVED, CHECKED, USED, DELETED, POSTED, APPROVALING, APPROVALED };
			return lTemp;
		}
		/**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型
		  * 	（0，显示全部；1,没有清户选项 2,显示暂存和保存两项,3,显示已保存和已复核两项,4，显示已符合和已使用两项）
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
						lArrayID = new long[8];
						lArrayID[0] = TEMPSAVED;
						lArrayID[1] = SAVED;
						lArrayID[2] = CHECKED;
						lArrayID[3] = USED;
						lArrayID[4] = DELETED;
						lArrayID[5] = POSTED;
						lArrayID[6] = APPROVALING;
						lArrayID[7] = APPROVALED;
						break;
					case 2 :
						lArrayID = new long[2];
						lArrayID[0] = TEMPSAVED;
						lArrayID[1] = SAVED;
						break;
					case 3 :
						lArrayID = new long[2];
						lArrayID[0] = SAVED;
						lArrayID[1] = CHECKED;
						break;
					case 4 :
						lArrayID = new long[2];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
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

	/**
	 * 账户属性
	 * 
	 * @author yqwu
	 * 
	 *  
	 */
	public static class SubjectAttribute
	{
		public static final int ASSET = 1; //资产类科目
		public static final int DEBT = 2; //负债类科目
		public static final int RIGHT = 3; //权益类科目
		public static final int INCOME = 4; //收入类科目
		public static final int PAYOUT = 5; //支出类科目
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case ASSET :
					strReturn = "资产类科目";
					break;
				case DEBT :
					strReturn = "负债类科目";
					break;
				case RIGHT :
					strReturn = "权益类科目";
					break;
				case INCOME :
					strReturn = "收入类科目";
					break;
				case PAYOUT :
					strReturn = "支出类科目";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ASSET, DEBT, RIGHT, INCOME, PAYOUT };
			return lTemp;
		}
		public static final long getDirection(long lTypeID)
		{
			long lDiretionID = DebitOrCredit.CREDIT;
			if (lTypeID == ASSET || lTypeID == PAYOUT)
			{
				lDiretionID = DebitOrCredit.DEBIT;
			}
			return lDiretionID;
		}
	}
	/**
	 * 借贷方向
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
	 * Comments
	 */
	public static class DebitOrCredit
	{
		public static final long DEBIT = 1; //借
		public static final long CREDIT = 2; //贷
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DEBIT :
					strReturn = "借";
					break;
				case (int) CREDIT :
					strReturn = "贷";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { DEBIT, CREDIT };
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
				Log.print(ex.toString());
			}
		}
	}
	//通知单状态
	public static class NoticeFormStatus
	{
		public static final long CANCELED = 0; //已删除
		public static final long SUBMITED = 1; //已保存
		public static final long CHECKED = 2; //已审批,已审核
		public static final long USED = 3; //已使用
		public static final long COMPLETED = 4; //已完成
		public static final long POSTED = 5; //已过账
		public static final long REJECTED = 6; //已拒绝
		public static final long APPROVALING = 7;//审批中
		public static final long APPROVED = 8; //已审批
		
		//public static final long GRANTED = 9;//已发放
		//public static final long RECEIVED = 10;//已收回
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SUBMITED :
					strReturn = "已保存";
					break;
				case (int) CHECKED :
					strReturn = "已审批";
					break;
				case (int) USED :
					strReturn = "已使用";
					break;
				case (int) COMPLETED :
					strReturn = "已完成";
					break;
				case (int) POSTED :
					strReturn = "已过账";
					break;
				case (int) CANCELED :
					strReturn = "已删除";
					break;
				case (int) REJECTED :
					strReturn = "已拒绝";
					break;
				case (int) APPROVALING :
					strReturn = "审批中";
					break;
				case (int)	APPROVED	:
					strReturn = "已审批";
					break;
//				case (int) GRANTED:
//					strReturn = "已发放";
//					break;
//				case (int) RECEIVED:
//					strReturn = "已收回";
//					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SUBMITED, CHECKED, USED, COMPLETED, POSTED, CANCELED, REJECTED, APPROVALING };
			return lTemp;
		}
		/**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型（0，显示全部；1,没有清户选项,2,传递到资金结算的选项）
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
						lArrayID = new long[8];
						lArrayID[0] = SUBMITED;
						lArrayID[1] = CHECKED;
						lArrayID[2] = USED;
						lArrayID[3] = COMPLETED;
						lArrayID[4] = POSTED;
						lArrayID[5] = CANCELED;
						lArrayID[6] = REJECTED;
						lArrayID[7] = APPROVALING;
						break;
					case 2 :
						lArrayID = new long[4];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
						lArrayID[2] = COMPLETED;
						lArrayID[3] = POSTED;
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
	/**
	 * 登记薄类型
	 * */
	public static class RegisterType
	{
		public static final long REPURCHASE = 1; //回购登记薄
		public static final long PURCHASE = 2; //申购登记薄
		public static final long INVESTMENT = 3; //长期投资登记薄		
	}
	/**
	 * 金额方向
	 *
	 */
	public static class AmountDirection
	{
		public static final int BLUEFONT = 1; //蓝字  
		public static final int REDFONT = 2; //红字
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) BLUEFONT :
					strReturn = "蓝字";
					break;
				case (int) REDFONT :
					strReturn = "红字";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { BLUEFONT, REDFONT };
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
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * 资金流向
	 *
	 */
	public static class CapitalType
	{
		public static final int IRRESPECTIVE = 0; //无关
		public static final int INTERNAL = 1; //内部转帐
		public static final int BANK = 2; //银行
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case IRRESPECTIVE :
					strReturn = "无关";
					break;
				case INTERNAL :
					strReturn = "内部转帐";
					break;
				case BANK :
					strReturn = "银行";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { IRRESPECTIVE, INTERNAL, BANK };
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
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * 金额类型
	 * */
	public static class AmountType
	{
		public static final int AmountType_1 = 1; //实际收付
		public static final int AmountType_2 = 2; //本金/成本/面值(全价)
		public static final int AmountType_3 = 3; //利息/收益/支出
		public static final int AmountType_4 = 4; //证券销售/差价收入/损失
		public static final int AmountType_5 = 5; //应计利息/损益调整
		public static final int AmountType_6 = 6; //未实现利得
		public static final int AmountType_7 = 7; //手续费/佣金
		public static final int AmountType_8 = 8; //折价
		public static final int AmountType_9 = 9; //溢价
		public static final int AmountType_10 = 10; // 印花税
		public static final int AmountType_11 = 11; //营业税金及附加
		public static final int AmountType_12 = 12; //罚息
		public static final int AmountType_13 = 13; //券商实收付
		public static final int AmountType_14 = 14; //证交所实收付
		public static final int AmountType_15 = 15; //结算风险基金
		public static final int AmountType_16 = 16; //本金/成本/面值(净价)
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case AmountType_1 :
					strReturn = "实际收付";
					break;
				case AmountType_2 :
					strReturn = "本金/成本/面值(全价)";
					break;
				case AmountType_3 :
					strReturn = "利息/收益/支出";
					break;
				case AmountType_4 :
					strReturn = "证券销售/差价收入/损失";
					break;
				case AmountType_5 :
					strReturn = "应计利息/损益调整";
					break;
				case AmountType_6 :
					strReturn = "未实现利得";
					break;
				case AmountType_7 :
					strReturn = "手续费/佣金";
					break;
				case AmountType_8 :
					strReturn = "折价";
					break;
				case AmountType_9 :
					strReturn = "溢价";
					break;
				case AmountType_10 :
					strReturn = "印花税";
					break;
				case AmountType_11 :
					strReturn = "营业税金及附加";
					break;
				case AmountType_12 :
					strReturn = "罚息";
					break;
				case AmountType_13 :
					strReturn = "券商实收付";
					break;
				case AmountType_14 :
					strReturn = "证交所实收付";
					break;
				case AmountType_15 :
					strReturn = "结算风险基金";
					break;
				case AmountType_16 :
					strReturn = "本金/成本/面值(净价)";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					AmountType_1,
					AmountType_2,
					AmountType_3,
					AmountType_4,
					AmountType_5,
					AmountType_6,
					AmountType_7,
					AmountType_8,
					AmountType_9,
					AmountType_10,
					AmountType_11,
					AmountType_12,
					AmountType_13,
					AmountType_14,
					AmountType_15,
					AmountType_16 };
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
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * 科目类型
	 *   
	 */
	public static class EntrySubjectType
	{
		public static final int SUBJECT_TYPE_1 = 1; //付方资金帐户
		public static final int SUBJECT_TYPE_2 = 2; //收方资金帐户
		public static final int SUBJECT_TYPE_3 = 3; //付方本金/成本/面值科目
		public static final int SUBJECT_TYPE_4 = 4; //收方本金/成本/面值科目
		public static final int SUBJECT_TYPE_5 = 5; //付方利息/收益/支出科目
		public static final int SUBJECT_TYPE_6 = 6; //收方利息/收益/支出科目
		public static final int SUBJECT_TYPE_7 = 7; //付方证券销售/差价收入/损失科目
		public static final int SUBJECT_TYPE_8 = 8; //收方证券销售/差价收入/损失科目
		public static final int SUBJECT_TYPE_9 = 9; //付方应计利息/损益调整科目
		public static final int SUBJECT_TYPE_10 = 10; //收方应计利息/损益调整科目
		public static final int SUBJECT_TYPE_11 = 11; //付方未实现利得科目
		public static final int SUBJECT_TYPE_12 = 12; //收方未实现利得科目
		public static final int SUBJECT_TYPE_13 = 13; //付方手续费/佣金科目
		public static final int SUBJECT_TYPE_14 = 14; //收方手续费/佣金科目
		public static final int SUBJECT_TYPE_15 = 15; //付方折价科目
		public static final int SUBJECT_TYPE_16 = 16; //收方折价科目
		public static final int SUBJECT_TYPE_17 = 17; //付方溢价科目
		public static final int SUBJECT_TYPE_18 = 18; //收方溢价科目		
		public static final int SUBJECT_TYPE_19 = 19; //付方跌价/减值准备科目
		public static final int SUBJECT_TYPE_20 = 20; //收方跌价/减值准备科目
		public static final int SUBJECT_TYPE_21 = 21; //付方印花税科目
		public static final int SUBJECT_TYPE_22 = 22; //收方印花税科目
		public static final int SUBJECT_TYPE_23 = 23; //付方营业税科目		
		public static final int SUBJECT_TYPE_24 = 24; //收方营业税科目
		public static final int SUBJECT_TYPE_25 = 25; //付方罚息科目
		public static final int SUBJECT_TYPE_26 = 26; //收方罚息科目
		public static final int SUBJECT_TYPE_27 = 27; //付方资本公积科目
		public static final int SUBJECT_TYPE_28 = 28; //收方资本公积科目
		public static final int SUBJECT_TYPE_29 = 29; //付方申购/挂帐科目
		public static final int SUBJECT_TYPE_30 = 30; //收方申购/挂帐科目
		public static final int SUBJECT_TYPE_31 = 31; //付方银行帐户
		public static final int SUBJECT_TYPE_32 = 32; //收方银行帐户
		public static final int SUBJECT_TYPE_33 = 33; //付方其它应收款科目
		public static final int SUBJECT_TYPE_34 = 34; //收方其它应收款科目
		public static final int SUBJECT_TYPE_35 = 35; //付方其它应付款科目
		public static final int SUBJECT_TYPE_36 = 36; //收方其它应付款科目
		public static final int SUBJECT_TYPE_99 = 99; //自设
		public static long getSubjectTypeDirection(int subjectType)
		{
			if (subjectType == SUBJECT_TYPE_1
				|| subjectType == SUBJECT_TYPE_3
				|| subjectType == SUBJECT_TYPE_5
				|| subjectType == SUBJECT_TYPE_7
				|| subjectType == SUBJECT_TYPE_9
				|| subjectType == SUBJECT_TYPE_11
				|| subjectType == SUBJECT_TYPE_13
				|| subjectType == SUBJECT_TYPE_15
				|| subjectType == SUBJECT_TYPE_17
				|| subjectType == SUBJECT_TYPE_19
				|| subjectType == SUBJECT_TYPE_21
				|| subjectType == SUBJECT_TYPE_23
				|| subjectType == SUBJECT_TYPE_25
				|| subjectType == SUBJECT_TYPE_27
				|| subjectType == SUBJECT_TYPE_1
				|| subjectType == SUBJECT_TYPE_29)
			{
				return Direction.PAY;
			}
			else if (subjectType == SUBJECT_TYPE_99)
			{
				return -1;
			}
			else
			{
				return Direction.RECEIVE;
			}
		}
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					SUBJECT_TYPE_1,
					SUBJECT_TYPE_2,
					SUBJECT_TYPE_3,
					SUBJECT_TYPE_4,
					SUBJECT_TYPE_5,
					SUBJECT_TYPE_6,
					SUBJECT_TYPE_7,
					SUBJECT_TYPE_8,
					SUBJECT_TYPE_9,
					SUBJECT_TYPE_10,
					SUBJECT_TYPE_11,
					SUBJECT_TYPE_12,
					SUBJECT_TYPE_13,
					SUBJECT_TYPE_14,
					SUBJECT_TYPE_15,
					SUBJECT_TYPE_16,
					SUBJECT_TYPE_17,
					SUBJECT_TYPE_18,
					SUBJECT_TYPE_19,
					SUBJECT_TYPE_20,
					SUBJECT_TYPE_21,
					SUBJECT_TYPE_22,
					SUBJECT_TYPE_23,
					SUBJECT_TYPE_24,
					SUBJECT_TYPE_25,
					SUBJECT_TYPE_26,
					SUBJECT_TYPE_27,
					SUBJECT_TYPE_28,
					SUBJECT_TYPE_29,
					SUBJECT_TYPE_30,
					SUBJECT_TYPE_31,
					SUBJECT_TYPE_32,
					SUBJECT_TYPE_33,
					SUBJECT_TYPE_34,
					SUBJECT_TYPE_35,
					SUBJECT_TYPE_36,
					SUBJECT_TYPE_99 };
			return lTemp;
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *        控件名称
		 * @param nType，控件类型（0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *        是否需要空白行
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
				Log.print(ex.toString());
			}
		}
		//TBD....
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case SUBJECT_TYPE_1 :
					strReturn = "付方资金帐户";
					break;
				case SUBJECT_TYPE_2 :
					strReturn = "收方资金帐户";
					break;
				case SUBJECT_TYPE_3 :
					strReturn = "付方本金/成本/面值科目";
					break;
				case SUBJECT_TYPE_4 :
					strReturn = "收方本金/成本/面值科目";
					break;
				case SUBJECT_TYPE_5 :
					strReturn = "付方利息/收益/支出科目";
					break;
				case SUBJECT_TYPE_6 :
					strReturn = "收方利息/收益/支出科目";
					break;
				case SUBJECT_TYPE_7 :
					strReturn = "付方证券销售/差价收入/损失科目";
					break;
				case SUBJECT_TYPE_8 :
					strReturn = "收方证券销售/差价收入/损失科目";
					break;
				case SUBJECT_TYPE_9 :
					strReturn = "付方应计利息/损益调整科目";
					break;
				case SUBJECT_TYPE_10 :
					strReturn = "收方应计利息/损益调整科目";
					break;
				case SUBJECT_TYPE_11 :
					strReturn = "付方未实现利得科目";
					break;
				case SUBJECT_TYPE_12 :
					strReturn = "收方未实现利得科目";
					break;
				case SUBJECT_TYPE_13 :
					strReturn = "付方手续费/佣金科目";
					break;
				case SUBJECT_TYPE_14 :
					strReturn = "收方手续费/佣金科目";
					break;
				case SUBJECT_TYPE_15 :
					strReturn = "付方折价科目";
					break;
				case SUBJECT_TYPE_16 :
					strReturn = "收方折价科目";
					break;
				case SUBJECT_TYPE_17 :
					strReturn = "付方溢价科目";
					break;
				case SUBJECT_TYPE_18 :
					strReturn = "收方溢价科目";
					break;
				case SUBJECT_TYPE_19 :
					strReturn = "付方跌价/减值准备科目";
					break;
				case SUBJECT_TYPE_20 :
					strReturn = "收方跌价/减值准备科目";
					break;
				case SUBJECT_TYPE_21 :
					strReturn = "付方印花税科目";
					break;
				case SUBJECT_TYPE_22 :
					strReturn = "收方印花税科目";
					break;
				case SUBJECT_TYPE_23 :
					strReturn = "付方营业税科目";
					break;
				case SUBJECT_TYPE_24 :
					strReturn = "收方营业税科目";
					break;
				case SUBJECT_TYPE_25 :
					strReturn = "付方罚息科目";
					break;
				case SUBJECT_TYPE_26 :
					strReturn = "收方罚息科目";
					break;
				case SUBJECT_TYPE_27 :
					strReturn = "付方资本公积科目";
					break;
				case SUBJECT_TYPE_28 :
					strReturn = "收方资本公积科目";
					break;
				case SUBJECT_TYPE_29 :
					strReturn = "付方申购/挂帐科目";
					break;
				case SUBJECT_TYPE_30 :
					strReturn = "收方申购/挂帐科目";
					break;
				case SUBJECT_TYPE_31 :
					strReturn = "付方银行帐户";
					break;
				case SUBJECT_TYPE_32 :
					strReturn = "收方银行帐户";
					break;
				case SUBJECT_TYPE_33 :
					strReturn = "付方其它应收款科目";
					break;
				case SUBJECT_TYPE_34 :
					strReturn = "收方其它应收款科目";
					break;
				case SUBJECT_TYPE_35 :
					strReturn = "付方其它应付款科目";
					break;
				case SUBJECT_TYPE_36 :
					strReturn = "收方其它应付款科目";
					break;
				case SUBJECT_TYPE_99 :
					strReturn = "自设";
					break;
			}
			return strReturn;
		}
	}
	/**
	 * 凭证导出状态
	 *   
	 */
	public static class PostEntryStatus
	{
		public static final long NotPost = 0; //未过帐		
		public static final long Posted = 1; //已过帐		
	}
	/**
	 * 帐务类型
	 * */
	public static class EntryAccountType
	{
		public static final int AccountType_0 = 0; //无关
		public static final int AccountType_1 = 1; //拆入资金
		public static final int AccountType_2 = 2; //拆出资金
		public static final int AccountType_3 = 3; //正回购
		public static final int AccountType_4 = 4; //逆回购
		public static final int AccountType_5 = 5; //银行间证券现券
		public static final int AccountType_6 = 6; //交易所证券现券
		public static final int AccountType_7 = 7; //逆回购
		public static final int AccountType_8 = 8; //正回购
		public static final int AccountType_9 = 9; //开放式基金
		public static final int AccountType_10 = 10; //资金划拨
		public static final int AccountType_11 = 11; //资产回购
		public static final int AccountType_12 = 12; //结构性投资
		public static final int AccountType_13 = 13; //债券承销
		public static final int AccountType_14 = 14; //委托理财
		public static final int AccountType_15 = 15; //受托理财
		public static final int AccountType_16 = 16; //股权投资（自营/委托）
		public static final int AccountType_17 = 17; //代理保险
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) AccountType_0 :
					strReturn = "无关";
					break;
				case (int) AccountType_1 :
					strReturn = "拆入资金";
					break;
				case (int) AccountType_2 :
					strReturn = "拆出资金";
					break;
				case (int) AccountType_3 :
					strReturn = "正回购";
					break;
				case (int) AccountType_4 :
					strReturn = "逆回购";
					break;
				case (int) AccountType_5 :
					strReturn = "银行间证券现券";
					break;
				case (int) AccountType_6 :
					strReturn = "交易所证券现券";
					break;
				case (int) AccountType_7 :
					strReturn = "逆回购";
					break;
				case (int) AccountType_8 :
					strReturn = "正回购";
					break;
				case (int) AccountType_9 :
					strReturn = "开放式基金";
					break;
				case (int) AccountType_10 :
					strReturn = "资金划拨";
					break;					
				case (int) AccountType_11 :
					strReturn = "资产回购";
					break;
				case (int) AccountType_12 :
					strReturn = "结构性投资";
					break;
				case (int) AccountType_13 :
					strReturn = "债券承销";
					break;
				case (int) AccountType_14 :
					strReturn = "委托理财";
					break;
				case (int) AccountType_15 :
					strReturn = "受托理财";
					break;
				case (int) AccountType_16 :
					strReturn = "股权投资（自营/委托）";
					break;
				case (int) AccountType_17 :
					strReturn = "代理保险";
					break;
			}
			return strReturn;
		}
		/**
		 * 业务性质为银行间的帐务类型
		 */
		public static final long[] getAllCode_BANK()
		{
			long[] lTemp = { AccountType_0, AccountType_1, AccountType_2, AccountType_3, AccountType_4, AccountType_5, AccountType_10};
			return lTemp;
		}
		/**
		 * 业务性质为交易所的帐务类型
		 */
		public static final long[] getAllCode_EXCHANGECENTER()
		{
			long[] lTemp = { AccountType_0, AccountType_6, AccountType_7, AccountType_8 };
			return lTemp;
		}
		/**
		* 业务性质为开放式基金的帐务类型
		*/
		public static final long[] getAllCode_OPENFUND()
		{
			long[] lTemp = { AccountType_0, AccountType_9 };
			return lTemp;
		}
		/**
		* 业务性质为合同业务的帐务类型
		*/
		public static final long[] getAllCode_CONTRACT()
		{
			long[] lTemp = { AccountType_0, AccountType_11, AccountType_12, AccountType_13, AccountType_14, AccountType_15, AccountType_16, AccountType_17 };
			return lTemp;
		}
        /**
        * 业务性质为其他业务的帐务类型
        */
        public static final long[] getAllCode_OTHERS()
        {
            long[] lTemp = { AccountType_0 };
            return lTemp;
        }
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					AccountType_0,
					AccountType_1,
					AccountType_2,
					AccountType_3,
					AccountType_4,
					AccountType_5,
					AccountType_6,
					AccountType_7,
					AccountType_8,
					AccountType_9,
					AccountType_10,
					AccountType_11,
					AccountType_12,
					AccountType_13,
					AccountType_14,
					AccountType_15,
					AccountType_16,
					AccountType_17 };
			return lTemp;
		}
		/**
		 * 显示下拉列表
		 * 
		 * @param out
		 * @param strControlName,
		 *        控件名称
		 * @param nType，控件类型
		 * （0，显示全部；）
		 * @param lSelectValue
		 * @param isNeedAll，是否需要”全部项“
		 * @param isNeedBlank
		 *        是否需要空白行
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
						lArrayID = getAllCode_BANK();
						break;
					case 2 :
						lArrayID = getAllCode_EXCHANGECENTER();
						break;
					case 3 :
						lArrayID = getAllCode_OPENFUND();
						break;
					case 4 :
						lArrayID = getAllCode_CONTRACT();
						break;
                    case 5 :
                        lArrayID = getAllCode_OTHERS();
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
	//文件类型
	public static class FileType
	{
		//文件类型
		public static final long TXT = 1; //文本文件
		public static final long DOC = 2; //WORD文档
		public static final long XLS = 3; //EXCEL文档
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) TXT :
					strReturn = "文本文件";
					break;
				case (int) DOC :
					strReturn = "WORD文档";
					break;
				case (int) XLS :
					strReturn = "EXCEL文档";
					break;
			}
			return strReturn;
		}
	}
	
	//Modify by leiyang date 2007/09/14
	//上传附件所依附的主体类型
	//审批关联设置的操作类型
	public static class AttachParentType
	{
		public static final long APPLY = 1; //申请
		public static final long CONTRACT = 2; //合同
		public static final long NOTICE = 3; //业务通知书
		public static final long CLIENT = 4; //客户
		public static final long DELIVERY = 5; //交割单
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) APPLY :
					strReturn = "申请";
					break;
				case (int) CONTRACT :
					strReturn = "合同";
					break;
				case (int) NOTICE :
					strReturn = "业务通知书";
					break;
				case (int) CLIENT :
					strReturn = "客户";
					break;
				case (int) DELIVERY :
					strReturn = "交割单";
					break;	
			}
			return strReturn;
		}
		
		public static final long[] getAllCode()
		{
			long[] lTemp = { APPLY, CONTRACT, NOTICE};
			return lTemp;
		}
	}
	
	//资金来源
	public static class CapitalSource
	{
		public static final long SELFSUPPORT = 1; //自营
		public static final long AGENT = 2; //代理
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SELFSUPPORT :
					strReturn = "自营";
					break;
				case (int) AGENT :
					strReturn = "代理";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SELFSUPPORT, AGENT };
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
						lArrayID = new long[2];
						lArrayID[0] = SELFSUPPORT;
						lArrayID[1] = AGENT;
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
	//冻结处理方式
	public static class FrozenProcess
	{
		public final static long NOT_DEAL = 0; //不处理
		public final static long FREEZE = 1; //库存冻结
		public final static long CANCEL_FREEZE = 2; //库存解冻
	}
	public static class RegisterProcess
	{
		public final static long CAPITAL = 11; //拆借/回购
		public final static long CAPITAL_MATURE = 12; //拆借/回购到期
		public final static long APPLICATION_APPLY = 21; //申购申请
		public final static long APPLICATION_CONFIRM = 22; //申购确认
		public final static long APPLICATION_MATURE = 23; //申购返款
		public final static long APPLICATION_SELLOUT = 24; //申购卖出
		public final static long LONGTERM_BUYIN = 31; //长期投资买入
		public final static long LONGTERM_BUYOUT = 32; //长期投资买出
		public final static long REPURCHASE_REGISTER = 1; //回购登记薄
		public final static long PURCHASE_REGISTER = 2; //买卖登记薄　
		public final static long LONGTERMINVESTMENT_REGISTER = 3; //长期投资登记薄
		public final static long getBelongRegister(long registerProcess)
		{
			if (registerProcess == CAPITAL || registerProcess == CAPITAL_MATURE)
			{
				return REPURCHASE_REGISTER;
			}
			else if (registerProcess == APPLICATION_APPLY || registerProcess == APPLICATION_CONFIRM || registerProcess == APPLICATION_MATURE || registerProcess == APPLICATION_SELLOUT)
			{
				return PURCHASE_REGISTER;
			}
			else if (registerProcess == LONGTERM_BUYIN || registerProcess == LONGTERM_BUYOUT)
			{
				return LONGTERMINVESTMENT_REGISTER;
			}
			return -1;
		}
	}
	/************************************************************/
	/**
	 * 投标方式
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class BidType
	{
		public static final long US = 1; //美国式
		public static final long HOLLAND = 2; //荷兰式
		public static final long OTHERS = 3; //其它
		public static final long[] getAllCode()
		{
			long[] lTemp = { US, HOLLAND, OTHERS };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) US :
					strReturn = "美国式";
					break;
				case (int) HOLLAND :
					strReturn = "荷兰式";
					break;
				case (int) OTHERS :
					strReturn = "其它";
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
						lArrayID[0] = US;
						lArrayID[1] = HOLLAND;
						lArrayID[2] = OTHERS;
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
	/**
	 * 投标方式
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class TaxType
	{
		public static final long BUSINESS_TAX = 1; //营业税
		public static final long CONSTRUCTION_TAX = 2; //城市维护建设税
		public static final long EDUCATION_TAX = 3; //教育费附加税
		public static final long[] getAllCode()
		{
			long[] lTemp = { BUSINESS_TAX, CONSTRUCTION_TAX, EDUCATION_TAX };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) BUSINESS_TAX :
					strReturn = "营业税";
					break;
				case (int) CONSTRUCTION_TAX :
					strReturn = "城市维护建设税";
					break;
				case (int) EDUCATION_TAX :
					strReturn = "教育费附加税";
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
						lArrayID[0] = BUSINESS_TAX;
						lArrayID[1] = CONSTRUCTION_TAX;
						lArrayID[2] = EDUCATION_TAX;
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

	//合同状态
	public static class ContractStatus
	{
		//合同状态
		public static final long SAVE = 1; //撰写
		public static final long SUBMIT = 2; //已提交
		public static final long CHECK = 3; //已审核
		public static final long NOTACTIVE = 4; //未执行
		public static final long ACTIVE = 5; //执行中
		public static final long EXTEND = 6; //已展期
		public static final long FINISH = 10; //已结束
		public static final long CANCEL = 11; //已取消
		public static final long REFUSE = 12; //已拒绝
		public static final long APPROVALING = 13;//审核中

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "已保存";
					break;
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) CHECK :
					strReturn = "已审核";
					break;
				case (int) NOTACTIVE :
					strReturn = "未执行";
					break;
				case (int) ACTIVE :
					strReturn = "执行中";
					break;
				case (int) EXTEND :
					strReturn = "已展期";
					break;
				case (int) FINISH :
					strReturn = "已结束";
					break;
				case (int) CANCEL :
					strReturn = "已取消";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) APPROVALING :
					strReturn = "审核中";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[9];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = EXTEND;
			lTemp[6] = FINISH;
			lTemp[7] = CANCEL;
			lTemp[8] = REFUSE;
			lTemp[8] = APPROVALING;

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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
		public static final void showList1(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{

				lArrayID = new long[] { SAVE, SUBMIT, CHECK, NOTACTIVE, ACTIVE, FINISH };
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

	/*
	 * 委托资产形式
	 * @author gdzhao
	 *
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class ConsignAssetType
	{
		public static final long CASH = 1; //现金
		public static final long STOCK = 2; //股票
		public static final long DEBT = 3; //国债
		public static final long ALL = 4;  //全部
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) CASH :
					strReturn = "现金";
					break;
				case (int) STOCK :
					strReturn = "股票";
					break;
				case (int) DEBT :
					strReturn = "国债";
					break;
				case (int) ALL :
					strReturn = "全部";					
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = CASH;
			lTemp[1] = STOCK;
			lTemp[2] = DEBT;
			lTemp[3] = ALL;
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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * 结息方式
	 * @author gdzhao
	 *
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class SettlementType
	{
		public static final long ONEMONTH = 1; //1 month
		public static final long THREEMONTH = 2; //3 month
		public static final long SIXMONTH = 3; //5 month
		public static final long ONEYEAR = 4; //1 year
		public static final long ONTIME = 5; //on time
		public static final long PRETIME = 6; //PRE TIME

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1个月";
					break;
				case (int) THREEMONTH :
					strReturn = "3个月";
					break;
				case (int) SIXMONTH :
					strReturn = "6个月";
					break;
				case (int) ONEYEAR :
					strReturn = "1年";
					break;
				case (int) ONTIME :
					strReturn = "到期支付";
					break;
				case (int) PRETIME :
					strReturn = "提前支付";
					break;

			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = ONEMONTH;
			lTemp[1] = THREEMONTH;
			lTemp[2] = SIXMONTH;
			lTemp[3] = ONEYEAR;
			lTemp[4] = ONTIME;
			lTemp[5] = PRETIME;
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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	public static class BoldSaleType
	{
		public static final long ALLSALE = 1;
		public static final long BALANCESALE = 2;
		public static final long CONSIGNSALE = 3;
		public static final long OTHER = 4;
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ALLSALE :
					strReturn = "全额包销";
					break;
				case (int) BALANCESALE :
					strReturn = "余额包销";
					break;
				case (int) CONSIGNSALE :
					strReturn = "代销";
					break;
				case (int) OTHER :
					strReturn = "其它";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = ALLSALE;
			lTemp[1] = BALANCESALE;
			lTemp[2] = CONSIGNSALE;
			lTemp[3] = OTHER;
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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	/*帐户类型－银行类型（杨帆加）
	  1=银行帐户
	  2=华能财务帐户
	  3=活期帐户 */
	public static class NoticeAccountType
	{
		public static final long COUNTERPARTACCOUNT = 1; //1,银行帐户；
		public static final long COMPANYACCOUNT = 2; //2，华能财务帐户
		public static final long CURRENTACCOUNT = 3; //3，活期帐户
	}

	/*收付方向（杨帆加）
	 * 8=收款
	 * 9=付款
	 * @author fanyang
	 */
	/*public static class ReceiveOrPay
	{
		public static final long RECEIVE = 8; //收
		public static final long PAY = 9; //付
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) RECEIVE :
					strReturn = "收";
					break;
				case (int) PAY :
					strReturn = "付";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { RECEIVE, PAY };
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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}*/
	/*	法人类型
	 * 1=一般法人
	 * 2=战略法人
	 * 3=一般投资者
	 * @author xuyu
	 */

	public static class OwnerType
	{
		public static final long NORMAL_OWNER = 1; //一般法人
		public static final long STRATERGY_OWNER = 2; //战略法人

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) NORMAL_OWNER :
					strReturn = "一般法人";
					break;
				case (int) STRATERGY_OWNER :
					strReturn = "战略法人";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = NORMAL_OWNER;
			lTemp[1] = STRATERGY_OWNER;

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
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	//合同执行计划变更状态
	public static class PlanModifyStatus
	{
		//免还申请状态
		public static final long SUBMIT = 1; //已提交
		public static final long CHECK = 2; //已审核
		public static final long REFUSE = -2; //已拒绝
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) SUBMIT :
					strReturn = "已提交";
					break;
				case (int) CHECK :
					strReturn = "已审核";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
			}
			return strReturn;
		}
	}
	//贷款放款方式
	public static class PayType
	{
		public static final long ONEMONTH = 1; //1个月
		public static final long THREEMONTH = 2; //3个月
		public static final long SIXMONTH = 3; //6个月
		public static final long ONEYEAR = 4; //按年放款
		public static final long ONETIME = 5; //一次放款
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1个月";
					break;
				case (int) THREEMONTH :
					strReturn = "3个月";
					break;
				case (int) SIXMONTH :
					strReturn = "6个月";
					break;
				case (int) ONEYEAR :
					strReturn = "一年";
					break;
				case (int) ONETIME :
					strReturn = "一次性";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ONEMONTH, THREEMONTH, SIXMONTH, ONEYEAR, ONETIME };
			return lTemp;
		}
		public static final long getCount()
		{
			return 5;
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
	//贷款还款方式
	public static class RepayType
	{
		public static final long ONEMONTH = 1; //1个月
		public static final long THREEMONTH = 2; //3个月
		public static final long SIXMONTH = 3; //6个月
		public static final long ONEYEAR = 4; //按年放款
		public static final long ONETIME = 5; //一次放款
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1个月";
					break;
				case (int) THREEMONTH :
					strReturn = "3个月";
					break;
				case (int) SIXMONTH :
					strReturn = "6个月";
					break;
				case (int) ONEYEAR :
					strReturn = "一年";
					break;
				case (int) ONETIME :
					strReturn = "一次性";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ONEMONTH, THREEMONTH, SIXMONTH, ONEYEAR, ONETIME };
			return lTemp;
		}
		public static final long getCount()
		{
			return 5;
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
	//计划类型
	public static class PlanType
	{
		//手续费率类型
		public static final long PAY = 1; //放款
		public static final long REPAY = 2; //还款
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) PAY :
					strReturn = "放款";
					break;
				case (int) REPAY :
					strReturn = "还款";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { PAY, REPAY };
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
	
	//银行类型
	public static class SiteType
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
		public static final long[] getAllCode()
		{
			long[] lTemp = { STATE_OWNED_BANK, COMMERCIAL_BANK,NONBANK_ORGANIZATION,OTHER_ORGANIZATION };
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
	
	//科目号（余额表）
	public static class SubjectCode
	{
		public static final long SUBJECTTYPE_ZYZQ = 1;//自营证券
		public static final long SUBJECTTYPE_DQTZ = 2;//短期投资
		public static final long SUBJECTTYPE_MRHGZC = 3;//买入回购资产
		public static final long SUBJECTTYPE_MCHGZCK = 4;//卖出回购资产款
		public static final long SUBJECTTYPE_JGXTZ = 5; //结构性投资
		
		
		/**
		 * 一级科目
		 * code1:subjectTypeID
		**/
		public static final String getName( long code1 )
		{
			String strReturn = ""; //初始化返回值
			
			switch ( (int) code1 )
			{
				case (int) SUBJECTTYPE_ZYZQ:
					strReturn = "121";
					break;
				case (int) SUBJECTTYPE_DQTZ:
					strReturn = "118";
					break;
				case (int) SUBJECTTYPE_MRHGZC:
					strReturn = "1260";
					break;
				case (int) SUBJECTTYPE_MCHGZCK:
					strReturn = "2250";
					break;
				case (int) SUBJECTTYPE_JGXTZ:
					strReturn = "1020";
					break;
			}
			return strReturn;
		}
		
		/**
		 * 二级科目
		 * code1:subjectTypeID
		 * code2:businessTypeID
		**/
		public static final String getName( long code1,long code2 )
		{
			String strReturn = ""; //初始化返回值
			
			strReturn = getName( code1 );
			
			switch ( (int) code2 )
			{
				case (int) 16://股票
					strReturn += "001";
					break;
				case (int) 31://国债
					strReturn += "-100";
					break;
				case (int) 73://委托理财
					strReturn += "-200";
					break;
				case (int) 46://债券
					strReturn += "002";
					break;
				case (int) 56://基金
					strReturn += "003";
					break;
				case (int) 26://债券回购、卖出回购证券款
					strReturn += "01";
					break;
				case (int) 71://信贷资产回购、卖出回购信贷资产款
					strReturn += "02";
					break;
			}
			
			return strReturn;
		}
		
		/**
		 * 三级科目
		 * code1:subjectTypeID
		 * code2:businessTypeID
		 * code3:siteTypeID
		**/
		public static final String getName( long code1,long code2,long code3 )
		{
			String strReturn = ""; //初始化返回值
			
			strReturn = getName( code1,code2 );
			
			switch ( (int) code3 )
			{
				case (int) SiteType.COMMERCIAL_BANK://其他商业银行
					strReturn += "0200";
					break;
				case (int) SiteType.NONBANK_ORGANIZATION://非银行金融机构
					strReturn += "0300";
					break;
				case (int) SiteType.OTHER_ORGANIZATION://其他单位
					strReturn += "0400";
					break;
				case (int) SiteType.STATE_OWNED_BANK://国有商业银行
					strReturn += "0100";
					break;
			}
			
			return strReturn;
		}
	}
	
	// 工作类型 add by leiyang,kevin 2007/09/05
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//待处理业务
		
		public static final long CURRENTWORK = 2; //待办任务

		public static final long HISTORYWORK = 3;//已办任务

		public static final long FINISHEDWORK = 4;//办结任务
		
		public static final long REFUSEWORK = 5;//拒绝业务
		
		public static final long CANCELAPPROVAL = 6;//取消审批		
		
		//Added by zwsun, 2007/09/06 得到我的工作的链接地址
		public final static String getWorkUrl(String strContext, long workType){
			String workUrl = "";
			switch((int)workType){
				case (int)WAITDEALWITHWORK:
					workUrl = strContext + "/securities/mywork/waitDealWithWorkList-main.jsp";
					break;
				case (int)CURRENTWORK:
					workUrl = strContext + "/securities/mywork/currentWorkList-main.jsp";
					break;
				case (int)HISTORYWORK:
					workUrl = strContext + "/securities/mywork/historyWorkList-main.jsp";
					break;
				case (int)FINISHEDWORK:
					workUrl = strContext + "";
					break;
				case (int)REFUSEWORK:
					workUrl = strContext + "/securities/mywork/refuseWorkList-main.jsp";
					break;
				case (int)CANCELAPPROVAL:
					workUrl = strContext + "/securities/mywork/cancelApprovalList-main.jsp";
					break;					
			}
			return workUrl;
		}
		public final static String getWorkUrl(long workType){
			String strContext="/NASApp/iTreasury-securities";
			return getWorkUrl(strContext, workType);
		}
	}
	
	//资产转让通知单类型
	public static class NoticeFormType
	{
        public static final long CAPITAL_PAYMENT = 7111; //支付转让款项
        public static final long INTEREST_PAYBACK = 7112; //利息收回通知单
        public static final long CAPITAL_REPURCHASE = 7113; //购回通知单
        public static final long INTEREST_PAYMENT = 7114; //利息支付通知单
        public static final long CAPITAL_PAYBACK = 7115; //收到转让款项
        
        
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
                         lArrayID[1] = INTEREST_PAYMENT;
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