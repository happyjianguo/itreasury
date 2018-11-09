package com.iss.itreasury.fcinterface.bankportal.privilegemgt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.fcinterface.bankportal.constant.BooleanValue;

public class OperationConfig
{
	//操作类型对应的入口页面URL
	public static final HashMap hmPageURL = new HashMap();
	//操作类型集合
	public static HashMap hmOperation = new HashMap();
	public static Operation ACCOUNTQUERY = new Operation(1);//账户数据查询操作类型	
	public static Operation VIREMENT = new Operation(2);//指令维护
	public static Operation GATHER = new Operation(3);//上收业务
	public static Operation BANKSETTING = new Operation(4);//银行设置
	public static Operation CLIENTSETTING = new Operation(5);//客户设置
	public static Operation ACCOUNTMNG = new Operation(6);//账户设置
	public static Operation DATAMAINTAIN = new Operation(7);//数据维护
	public static Operation ACCTPROPERTY1SETTING = new Operation(8);//账户属性一设置	
	public static Operation ACCTPROPERTY2SETTING = new Operation(9);//账户属性二设置
	public static Operation ACCTPROPERTY3SETTING = new Operation(10);//账户属性三设置
	
	static
	{
		/**
		 * ACCOUNTQUERY 账户数据查询操作类型定义
		 */
		hmOperation.put(String.valueOf(ACCOUNTQUERY.getType()),ACCOUNTQUERY);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		ACCOUNTQUERY.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 默认下是否包含下级办事处**/
		ACCOUNTQUERY.setIsContainSub(BooleanValue.TRUE);
		
		/**3 需要建立视图的表**/
		Collection colTables = new ArrayList();
		ACCOUNTQUERY.setTables(colTables);
		//客户表
		Table tbClient = new Table("bs_clientsetting");
		//要和账户视图建立关联
		tbClient.setIsJoinAccountView(BooleanValue.TRUE);
		//客户表和账户表的关联条件，定义规则是：本表字段(键)——>账户表字段(值)，其他表也一样
		HashMap hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_clientid");	
		tbClient.setAndCondition(hmAndCondition);			
		colTables.add(tbClient);
		//银行表
		Table tbBank = new Table("bs_banksetting");
		//要和账户视图建立关联
		tbBank.setIsJoinAccountView(BooleanValue.TRUE);
		//银行表和账户表的关联条件，定义规则是：本表字段(键)——>账户表字段(值)，其他表也一样
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_bankid");	
		tbBank.setAndCondition(hmAndCondition);			
		colTables.add(tbBank);
		//国家表
		Table tbCountry = new Table("bs_countrysetting");		
		tbCountry.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_countryid");	
		tbCountry.setAndCondition(hmAndCondition);			
		colTables.add(tbCountry);
		//币种表
		Table tbCurrency = new Table("bs_currencysetting");		
		tbCurrency.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_currencytype");	
		tbCurrency.setAndCondition(hmAndCondition);			
		colTables.add(tbCurrency);		
		//账户属性一表
		Table tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");		
		tbAcctPropertyOne.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertyone");	
		tbAcctPropertyOne.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyOne);
		//账户属性二表
		Table tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");		
		tbAcctPropertyTwo.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertytwo");	
		tbAcctPropertyTwo.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyTwo);
		//账户属性三表
		Table tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");		
		tbAcctPropertyThree.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertythree");	
		tbAcctPropertyThree.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyThree);
		/**4 对应的入口页面**/
		hmPageURL.put("/account/view/v030.jsp",ACCOUNTQUERY);//当前余额查询
		hmPageURL.put("/account/view/v040.jsp",ACCOUNTQUERY);//历史余额查询
		hmPageURL.put("/account/view/v050.jsp",ACCOUNTQUERY);//当日交易查询
		hmPageURL.put("/account/view/v060.jsp",ACCOUNTQUERY);//历史交易查询	
		hmPageURL.put("/account/view/v090.jsp",ACCOUNTQUERY);//对账单功能
		hmPageURL.put("/account/view/v111.jsp",ACCOUNTQUERY);//交易汇总清单
		hmPageURL.put("/account/view/v211.jsp",ACCOUNTQUERY);//入账交易清单
		hmPageURL.put("/account/view/v070.jsp",ACCOUNTQUERY);//账户基础信息查询
		hmPageURL.put("/account/view/v080.jsp",ACCOUNTQUERY);//账户归集关系查询
		hmPageURL.put("/account/view/v230.jsp",ACCOUNTQUERY);//交易信息补录
		hmPageURL.put("/query/view/v001.jsp",ACCOUNTQUERY);//账户每日余额表
		hmPageURL.put("/query/view/v010.jsp",ACCOUNTQUERY);//账户日均余额表
		hmPageURL.put("/query/view/v040.jsp",ACCOUNTQUERY);//账户收支统计表
		hmPageURL.put("/query/view/v020.jsp",ACCOUNTQUERY);//账户日均余额变动表
		hmPageURL.put("/query/view/v050.jsp",ACCOUNTQUERY);//客户每日余额汇总表
		hmPageURL.put("/query/view/v060.jsp",ACCOUNTQUERY);//银行每日余额汇总表
		hmPageURL.put("/query/view/v070.jsp",ACCOUNTQUERY);//客户每日余额折算汇总表
		hmPageURL.put("/query/view/v080.jsp",ACCOUNTQUERY);//银行每日余额折算汇总表
		hmPageURL.put("/datamaintain/view/v201.jsp",ACCOUNTQUERY);//直联账户当日余额查询
		hmPageURL.put("/datamaintain/view/v210.jsp",ACCOUNTQUERY);//直联账户当日交易明细查询
		hmPageURL.put("/account/view/v070.jsp",ACCOUNTQUERY);//账户基础信息查询
		hmPageURL.put("/account/view/v080.jsp",ACCOUNTQUERY);//账户归集关系查询	
		
//*********************************************************************************************************************//	
		/**
		 * VIREMENT 指令维护操作类型定义
		 */
		hmOperation.put(String.valueOf(VIREMENT.getType()),VIREMENT);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		VIREMENT.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 默认下是否包含下级办事处**/
		VIREMENT.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		VIREMENT.setTables(colTables);
		//指令表
		Table tbInstruction = new Table("bs_bankinstructioninfo");
		//要和账户视图建立关联
		tbInstruction.setIsJoinAccountView(BooleanValue.TRUE);
		//指令和账户表的关联条件
		hmAndCondition = new HashMap();
		hmAndCondition.put("s_payaccountno","s_accountno");	
		tbInstruction.setAndCondition(hmAndCondition);
		HashMap hmOrCondition = new HashMap();
		hmOrCondition.put("s_agentacctnoofpay","s_accountno");	
		tbInstruction.setOrCondition(hmOrCondition);
		colTables.add(tbInstruction);		
		
		/**4 对应的入口页面**/
		hmPageURL.put("/bankinterface/view/v001.jsp",VIREMENT);//指令查询
		hmPageURL.put("/bankinterface/view/v010.jsp",VIREMENT);//指令维护
		hmPageURL.put("/bankinterface/view/v020.jsp",VIREMENT);//指令导出
		
//**********************************************************************************************************************//		
		/**
		 * GATHER 上收业务操作类型定义
		 */
		hmOperation.put(String.valueOf(GATHER.getType()),GATHER);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		GATHER.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 默认下是否包含下级办事处**/
		GATHER.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		GATHER.setTables(colTables);
		//客户表
		tbClient = new Table("bs_clientsetting");
		//要和账户视图建立关联
		tbClient.setIsJoinAccountView(BooleanValue.TRUE);
		//客户表和账户表的关联条件
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_clientid");	
		tbClient.setAndCondition(hmAndCondition);			
		colTables.add(tbClient);	
		//归集策略表
		Table tbTactic = new Table("bs_acctgathertactic");
		colTables.add(tbTactic);
		//账户归集策略内容表
		//不必使用该表,有bs_acctgathertactic就足够
//		Table tbTacticContent = new Table("bs_acctgathertacticcontent");
//		colTables.add(tbTacticContent);
		//账户自动归集条件设置表
		Table tbTacticCondition = new Table("bs_acctgathercondition");
		colTables.add(tbTacticCondition);		
		
		/**4 对应的入口页面**/
		hmPageURL.put("/gather/view/v001.jsp",GATHER);//账户归集策略定义
		hmPageURL.put("/gather/view/v020.jsp",GATHER);//银行调账
		hmPageURL.put("/gather/view/v030.jsp",GATHER);//手动归集(新)
		hmPageURL.put("/bankinterface/view/v110.jsp",GATHER);//手动归集
		hmPageURL.put("/gather/view/v010.jsp",GATHER);//资金上划设置
//**********************************************************************************************************************//		
		/**
		 * BANKSETTING 银行设置操作类型定义
		 */
		hmOperation.put(String.valueOf(BANKSETTING.getType()),BANKSETTING);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		BANKSETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 默认下是否包含下级办事处**/
		BANKSETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		BANKSETTING.setTables(colTables);
		//银行表
		tbBank = new Table("bs_banksetting");		
		colTables.add(tbBank);				
		
		/**4 对应的入口页面**/
		hmPageURL.put("/setting/view/v008.jsp",BANKSETTING);//银行设置
		
//**********************************************************************************************************************//		
		/**
		 * CLIENTSETTING 客户设置操作类型定义
		 */
		hmOperation.put(String.valueOf(CLIENTSETTING.getType()),CLIENTSETTING);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		CLIENTSETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 默认下是否包含下级办事处**/
		CLIENTSETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		CLIENTSETTING.setTables(colTables);
		//客户表
		tbClient = new Table("bs_clientsetting");		
		colTables.add(tbClient);				
		
		/**4 对应的入口页面**/
		hmPageURL.put("/setting/view/v002.jsp",CLIENTSETTING);//客户设置	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY1SETTING 账户属性一设置操作类型定义
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY1SETTING.getType()),ACCTPROPERTY1SETTING);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		ACCTPROPERTY1SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 默认下是否包含下级办事处**/
		ACCTPROPERTY1SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		ACCTPROPERTY1SETTING.setTables(colTables);
		//账户属性一表
		tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");		
		colTables.add(tbAcctPropertyOne);				
		
		/**4 对应的入口页面**/
		hmPageURL.put("/setting/view/v014.jsp",ACCTPROPERTY1SETTING);//账户属性一设置	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY2SETTING 账户属性二设置操作类型定义
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY2SETTING.getType()),ACCTPROPERTY2SETTING);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		ACCTPROPERTY2SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 默认下是否包含下级办事处**/
		ACCTPROPERTY2SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		ACCTPROPERTY2SETTING.setTables(colTables);
		//账户属性二表
		tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");		
		colTables.add(tbAcctPropertyTwo);				
		
		/**4 对应的入口页面**/
		hmPageURL.put("/setting/view/v017.jsp",ACCTPROPERTY2SETTING);//账户属性二设置	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY3SETTING 账户属性三设置操作类型定义
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY3SETTING.getType()),ACCTPROPERTY3SETTING);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		ACCTPROPERTY3SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 默认下是否包含下级办事处**/
		ACCTPROPERTY3SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		ACCTPROPERTY3SETTING.setTables(colTables);
		//账户属性三表
		tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");		
		colTables.add(tbAcctPropertyThree);				
		
		/**4 对应的入口页面**/
		hmPageURL.put("/setting/view/v020.jsp",ACCTPROPERTY3SETTING);//账户属性三设置	
//**********************************************************************************************************************//
		
		/**
		 * ACCOUNTMNG 账户设置操作类型定义
		 */
		hmOperation.put(String.valueOf(ACCOUNTMNG.getType()),ACCOUNTMNG);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		ACCOUNTMNG.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 默认下是否包含下级办事处**/
		ACCOUNTMNG.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		colTables = new ArrayList();
		ACCOUNTMNG.setTables(colTables);
		//客户表
		tbClient = new Table("bs_clientsetting");		
		colTables.add(tbClient);
		//银行表
		tbBank = new Table("bs_banksetting");
		colTables.add(tbBank);
		//关联号表
		Table tbSubject = new Table("bs_subjectsetting");
		colTables.add(tbSubject);
		//账户属性一表
		tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");				
		colTables.add(tbAcctPropertyOne);
		//账户属性二表
		tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");					
		colTables.add(tbAcctPropertyTwo);
		//账户属性三表
		tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");					
		colTables.add(tbAcctPropertyThree);
		
		/**4 对应的入口页面**/
		hmPageURL.put("/account/view/v001.jsp",ACCOUNTMNG);//账户设置
		hmPageURL.put("/account/view/v010.jsp",ACCOUNTMNG);//账户审核
		hmPageURL.put("/account/view/v020.jsp",ACCOUNTMNG);//账户销户
		hmPageURL.put("/account/view/v240.jsp",ACCOUNTMNG);//账号升级查询
//**********************************************************************************************************************//		
		/**
		 * DATAMAINTAIN 数据维护操作类型定义
		 */
		hmOperation.put(String.valueOf(DATAMAINTAIN.getType()),DATAMAINTAIN);
		/**1 是否需要限制账户,即是否需要建立账户视图**/
		DATAMAINTAIN.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 默认下是否包含下级办事处**/
		DATAMAINTAIN.setIsContainSub(BooleanValue.FALSE);
		
		/**3 需要建立视图的表**/
		//没有需要额外建立的视图
		
		/**4 对应的入口页面**/
		hmPageURL.put("/datamaintain/view/v061.jsp",DATAMAINTAIN);//手工核对数据
		hmPageURL.put("/datamaintain/view/v071.jsp",DATAMAINTAIN);//手工导入历史数据
		hmPageURL.put("/datamaintain/view/v021.jsp",DATAMAINTAIN);//核对结果查询		
	}
	
	public static Operation getOperation(long operationType)
	{
		return (Operation)hmOperation.get(String.valueOf(operationType));
	}
	public static Operation getOperationByPageURL(String pageURL)
	{
		return (Operation)hmPageURL.get(pageURL);
	}
	/**
	 * 操作类型
	 */
	public static class Operation
	{				
	    //业务类型
		private long type = -1;
		
		//默认是否包含下级办事处
		private long isContainSub = -1;
		
		//是否需要建立账户视图
		private long isNeedAccount = -1;
		
		//需要建立视图的表
		private Collection tables = null;
		
		private Operation(long type)
		{
			this.type = type;
		}		

		public long getIsContainSub()
		{
			return isContainSub;
		}

		public void setIsContainSub(long isContainSub)
		{
			this.isContainSub = isContainSub;
		}

		public long getIsNeedAccount()
		{
			return isNeedAccount;
		}

		public void setIsNeedAccount(long isNeedAccount)
		{
			this.isNeedAccount = isNeedAccount;
		}

		public Collection getTables()
		{
			return tables;
		}		

		public long getType()
		{
			return type;
		}		

		public void setTables(Collection tables)
		{
			this.tables = tables;
		}

		public void setType(long type)
		{
			this.type = type;
		}	
	}
	/**
	 * 要建立视图的表
	 */
	public static class Table
	{		
		//表名
		private String name = null;	
		private long isJoinAccountView = -1;
		//关联条件
		private HashMap andCondition = null;//and的关联条件
		private HashMap orCondition = null;//or的关联条件
		
		private Table(String name)
		{
			this.name = name;
		}
		
		public long getIsJoinAccountView()
		{
			return isJoinAccountView;
		}

		public void setIsJoinAccountView(long isJoinAccountView)
		{
			this.isJoinAccountView = isJoinAccountView;
		}

		public HashMap getAndCondition()
		{
			return andCondition;
		}
		public void setAndCondition(HashMap andCondition)
		{
			this.andCondition = andCondition;
		}		
		public void setName(String name)
		{
			this.name = name;
		}
		public void setOrCondition(HashMap orCondition)
		{
			this.orCondition = orCondition;
		}		
		public String getName()
		{
			return name;
		}
		public HashMap getOrCondition()
		{
			return orCondition;
		}	
	}
}
