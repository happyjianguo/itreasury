/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintNotPurchaseDebtParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author wangyi
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PrintNotPurchaseDebtBean {

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(PrintNotPurchaseDebtParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		
		

		//条件1：日期开始日 公共条件 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//条件2：日期结束日 公共条件 
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//条件3：业务单位
		long clientId = queryParam.getClientId();
		//条件4：股东帐户
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//条件5：证券类别id
		long securitiesTypeId = queryParam.getSecuritiesType();


				sbFrom.append("  select   \n");
								
				sbFrom.append("   end_table.clientId ,--业务单位  \n");
				sbFrom.append("   end_table.stockHolderAccountId ,--股东帐户名称  \n");
				sbFrom.append("   end_table.counterPartId,  --指定开户营业部  \n");
				sbFrom.append("   end_table.accountId ,--资金账号  \n");
				sbFrom.append("   end_table.securitiesId, --证券id  \n");
				sbFrom.append("   sum(end_table.realAddNumber) as realAddNumber,--实际增加  \n");
				sbFrom.append("   sum(end_table.realReduceNumber) as realReduceNumber, --实际减少 \n");
				sbFrom.append("   sum(end_table.startQuantity) as startQuantity,--期初库存余额(正常) \n");
				sbFrom.append("   sum(end_table.startQuantityConvert) as startQuantityConvert,--期初库存余额(已折合)  \n");
				sbFrom.append("   sum(end_table.startQuantityFreeze) as startQuantityFreeze,--期初库存余额(已冻结)  \n");
				sbFrom.append("   sum(end_table.endQuantity) as endQuantity,--期末库存余额(正常)  \n");
				sbFrom.append("   sum(end_table.endQuantityConvert) as endQuantityConvert,--期末库存余额(已折合)  \n");
				sbFrom.append("   sum(end_table.endQuantityFreeze) as endQuantityFreeze--期末库存余额(已冻结)  \n");
			
				sbFrom.append("  from \n");
				sbFrom.append("  ( \n");
				sbFrom.append("  ( \n");
				sbFrom.append("  select --为了得到 期初库存余额\n");
				
				sbFrom.append("  my_DailyStock.ClientID ,--业务单位\n");
				sbFrom.append("  my_DailyStock.stockHolderAccountId ,--股东帐户名称\n");
				sbFrom.append("  my_DailyStock.counterPartId,  --指定开户营业部\n");
				sbFrom.append("  my_DailyStock.AccountID ,--资金账号\n");
				sbFrom.append("  my_DailyStock.SecuritiesID, --证券id\n");
				sbFrom.append("  0 as realAddNumber,--实际增加\n");
				sbFrom.append("  0 as realReduceNumber, --实际减少\n");
				sbFrom.append("  my_DailyStock.Quantity as startQuantity,--期初库存余额(正常) \n");//搞了半天，就是为了这个字段
				sbFrom.append("  my_DailyStock.Quantity*my_DailyStock.PledgeRate as startQuantityConvert,--期初库存余额(已折合) \n");//搞了半天，就是为了这个字段
				sbFrom.append("  my_DailyStock.FrozenQuantity as startQuantityFreeze,--期初库存余额(已冻结) \n");//搞了半天，就是为了这个字段
				sbFrom.append("  0 as endQuantity,--期末库存余额(正常) \n");
				sbFrom.append("  0 as endQuantityConvert,--期末库存余额(已折合) \n");
				sbFrom.append("  0 as endQuantityFreeze--期末库存余额(已冻结) \n");
				
				sbFrom.append("  from \n");
				
				getMyTable(
					strNoticeInputDateStart,
					strNoticeInputDateEnd,
					clientId,
					stockHolderAccountIds,
					securitiesTypeId);
					
				sbFrom.append("     where to_char(my_DailyStock.stockDate , 'yyyy-mm-dd') = " + "'" + strNoticeInputDateStart + "'" + "\n");
				
		sbFrom.append("  ) \n");
		sbFrom.append("  union \n");
		sbFrom.append("  ( \n");
		sbFrom.append("  select  --为了得到 期末库存余额 \n");
		
		sbFrom.append("  my_DailyStock.ClientID ,--业务单位\n");
		sbFrom.append("  my_DailyStock.stockHolderAccountId ,--股东帐户名称\n");
		sbFrom.append("  my_DailyStock.counterPartId,  --指定开户营业部\n");
		sbFrom.append("  my_DailyStock.AccountID ,--资金账号\n");
		sbFrom.append("  my_DailyStock.SecuritiesID, --证券id\n");
		sbFrom.append("  0 as realAddNumber,--实际增加\n");
		sbFrom.append("  0 as realReduceNumber, --实际减少\n");
		sbFrom.append("  0 as startQuantity,--期初库存余额(正常) \n");
		sbFrom.append("  0 as startQuantityConvert,--期初库存余额(已折合) \n");
		sbFrom.append("  0 as startQuantityFreeze,--期初库存余额(已冻结) \n");
		sbFrom.append("  my_DailyStock.Quantity as endQuantity,--期末库存余额(正常) \n");//搞了半天，就是为了这个字段
		sbFrom.append("  my_DailyStock.Quantity*my_DailyStock.PledgeRate as endQuantityConvert,--期末库存余额(已折合) \n");//搞了半天，就是为了这个字段
		sbFrom.append("  my_DailyStock.FrozenQuantity as endQuantityFreeze --期末库存余额(已冻结) \n");//搞了半天，就是为了这个字段
				
		sbFrom.append("  from \n");
		
		getMyTable(
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientId,
			stockHolderAccountIds,
			securitiesTypeId);
					
		sbFrom.append("     where to_char(my_DailyStock.stockDate , 'yyyy-mm-dd') = " + "'" + strNoticeInputDateEnd + "'" + "\n");
		
		sbFrom.append("  ) \n");
		sbFrom.append("  union \n");
		sbFrom.append("  ( \n");
		sbFrom.append("  select  --为了得到 实际增加 实际减少\n");
		
		sbFrom.append("  my_DailyStock.ClientID ,--业务单位\n");
		sbFrom.append("  my_DailyStock.stockHolderAccountId ,--股东帐户名称\n");
		sbFrom.append("  my_DailyStock.counterPartId,  --指定开户营业部\n");
		sbFrom.append("  my_DailyStock.AccountID ,--资金账号\n");
		sbFrom.append("  my_DailyStock.SecuritiesID, --证券id\n");
		sbFrom.append("  sum(my_DailyStock.InQuantity) as realAddNumber,--实际增加 \n");//搞了半天，就是为了这个字段
		sbFrom.append("  sum(my_DailyStock.OutQuantity) as realReduceNumber, --实际减少 \n");//搞了半天，就是为了这个字段
		sbFrom.append("  0 as startQuantity,--期初库存余额(正常) \n");
		sbFrom.append("  0 as startQuantityConvert,--期初库存余额(已折合) \n");
		sbFrom.append("  0 as startQuantityFreeze,--期初库存余额(已冻结) \n");
		sbFrom.append("  0 as endQuantity,--期末库存余额(正常) \n");
		sbFrom.append("  0 as endQuantityConvert,--期末库存余额(已折合) \n");
		sbFrom.append("  0 as endQuantityFreeze --期末库存余额(已冻结) \n");
				
		sbFrom.append("  from \n");
		
		getMyTable(
			strNoticeInputDateStart,
			strNoticeInputDateEnd,
			clientId,
			stockHolderAccountIds,
			securitiesTypeId);
				
		sbFrom.append("     group by ClientID, stockHolderAccountId, counterPartId ,AccountID ,SecuritiesID  \n");
		sbFrom.append("     ) \n");
		sbFrom.append("     )end_table  \n");
		sbFrom.append("     group by ClientID, stockHolderAccountId, counterPartId ,AccountID ,SecuritiesID  \n");
				
				

		
		
		//sbFrom拼写结束！！！！！！！！！！
		
		
		
		
		sbFrom.append("   )  \n");
		
		

	}

	//到这里，得到自己所需到所有数据的表 自定义的my_DailyStock
	private void getMyTable(
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		long clientId,
		String[] stockHolderAccountIds,
		long securitiesTypeId) {
		sbFrom.append("   (select sec_DailyStock.clientId ,--业务单位 \n");
		sbFrom.append("      SEC_STOCKHOLDERACCOUNT.id as stockHolderAccountId ,--股东帐户id  \n");
		sbFrom.append("      Sec_CounterPArt.Id as counterPartId,  --指定开户营业部 \n");
		sbFrom.append("      sec_DailyStock.AccountId ,--资金账号 \n");
		sbFrom.append("      sec_DailyStock.SecuritiesId ,--证券id \n");
		sbFrom.append("      sec_dailyStock.stockDate, --成交日期 \n");
		sbFrom.append("      sec_dailyStock.InQuantity, --本日入库数量 \n");
		sbFrom.append("      sec_dailyStock.OutQuantity, --本日出库数量 \n");
		sbFrom.append("      sec_dailyStock.Quantity ,--库存余额 \n");
		sbFrom.append("      SEC_Securities.PledgeRate ,--折成标准券比例 \n");
		//库存冻结量 取自SEC_RepurchaseRegister中的 抵押券数量 抵押就是冻结的 如果Amount为零 则冻结为零 如果Amount不为零 则取冻结量
		sbFrom.append("      decode(SEC_RepurchaseRegister.Amount,0,0,SEC_RepurchaseRegister.PledgeQuantity)  as FrozenQuantity --库存冻结量 \n");	
		sbFrom.append("from sec_DailyStock ,SEC_Securities ,sec_account,SEC_STOCKHOLDERACCOUNT,Sec_CounterPArt,SEC_RepurchaseRegister  \n");
		sbFrom.append("where sec_DailyStock.SecuritiesID = SEC_Securities.ID  \n");
		sbFrom.append(" and sec_DailyStock.accountid = sec_account.id \n");
		sbFrom.append(" and SEC_RepurchaseRegister.AccountID = sec_DailyStock.accountid \n");
		sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.ID(+) = sec_Account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append(" and sec_account.counterpartid = Sec_CounterPArt.id \n");
		//证券应该是在 交割单中已经做了回购，但是还没有做购回的
		sbFrom.append(" and sec_DailyStock.SecuritiesID in (select distinct SEC_DELIVERYORDER.SECURITIESID \n");
		sbFrom.append(" 	from SEC_DELIVERYORDER,sec_repurchaseregister \n");
		sbFrom.append(" 	where SEC_DELIVERYORDER.id =  sec_repurchaseregister.FIRSTDELIVERYORDERID --做过回购\n");
		sbFrom.append(" 	and  sec_repurchaseregister.LASTDELIVERYORDERID is null ) --没有做购回 \n");

		
		//条件一：业务通知单录入日期开始日
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(sec_dailyStock.stockDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件二：业务通知单录入日期开始日
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(sec_dailyStock.stockDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//业务单位ID
		if(clientId!=-1)
		{
			sbFrom.append( " and \n");
			sbFrom.append( " sec_DailyStock.ClientID = "+ clientId);
		}
		//股东帐户
		if(stockHolderAccountIds !=null&&stockHolderAccountIds.length >0)
		{
			sbFrom.append( " and sec_Account.STOCKHOLDERACCOUNTID1 in (");
			for(int i=0;i<stockHolderAccountIds.length -1;i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+" , ");
						
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+" ) \n ");
		}
		if(securitiesTypeId != -1)
		{
			sbFrom.append( " and \n ");
			sbFrom.append( " SEC_Securities.TypeID = "+ securitiesTypeId );
		}
		sbFrom.append("     ) my_DailyStock  \n");
	}

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printNotPurchaseDebt(PrintNotPurchaseDebtParam printParam)
		throws SecuritiesException {

		getSQL(printParam);
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.PrintNotPurchaseDebtInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}

}