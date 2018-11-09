package com.iss.itreasury.ebank.fundplan.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanCondition;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanGatherInfo;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo;
import com.iss.itreasury.ebank.fundplan.model.FundPlanParamInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;

public class CapitalPlanDao extends ITreasuryDAO {
	
	public CapitalPlanDao()
	{
		super("ob_capitalplan");
		setUseMaxID();
	}
	
	public CapitalPlanDao(Connection conn)
	{
		super("ob_capitalplan", conn);
		setUseMaxID();
	}
	
//	public long AddtoCapital(CapitalPlanInfo info) throws Exception 
//	{
//		long returnId = -1;
//	    
//		try {
//			if(info.getId() > 0)
//			{
//				update(info);
//				returnId = info.getId();
//			}
//			else
//			{
//				setUseMaxID();
//				returnId = add(info);
//			}
//		} 
//		catch (Exception e) {
//			throw new ITreasuryDAOException("数据插入表OB_SUBCAPITALPLAN失败，" + e.getMessage(),e);
//		}
//		finally
//		{
//			this.finalizeDAO();
//		}
//		
//		return returnId;
//	}
	
	/**
	 * added by ylguo
	 * 查找操作,时间参数只比较开始时间，因为客户有可能在输入时间参数的时候，时间区间并没有一个星期的时间。
	 * @param CapitalPlanInfo conditionInfo
	 * @param long clientId
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findCapitalPlan(CapitalPlanInfo conditionInfo) throws Exception
	{
		try{
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select a.*, \n"+
					     "       b.sname inputerName,\n"+
					     "       c.sname modifyerName,\n"+
					     "       u.sname auditerName \n"+
					     "from   ( \n");

			sbSQL.append("       select oc.*, \n" +
						 "              ct.scode clientcode, \n" +
					     "              ct.sname clientname \n" +
					     "         from OB_CAPITALPLAN oc,\n " +
					     "              client ct \n" +
					     "       where \n");
			sbSQL.append("              ct.id = oc.clientid \n");
			if(conditionInfo.getStartdate() != null ){//当起始日期有参数时
		    sbSQL.append("              and oc.startdate >= to_date( '");
			      sbSQL.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' )\n");
			}
			if(conditionInfo.getEnddate() != null ){  //当结束日期有参数时
		          sbSQL.append("              and oc.startdate <= to_date( '");
		          sbSQL.append(DataFormat.getDateString(conditionInfo.getEnddate())+"','yyyy-mm-dd' )\n");
			}
			//以下一个cpcode条件是用在"资金计划－查询"菜单下的
			if(conditionInfo.getCpCode() != null && conditionInfo.getCpCode()!="" && conditionInfo.getCpCode().length()>0){ 
		        sbSQL.append("              and oc.cpcode = "+conditionInfo.getCpCode()+"\n");
		    }
		    if(conditionInfo.getStatusId() != -1){
		    	sbSQL.append("              and oc.statusid = "+conditionInfo.getStatusId()+"\n");
		    }else{
		    	sbSQL.append("              and oc.statusid !=0 \n");//当v001.jsp页面状态选全部时，删除状态下的不显示
		    }
		    if(conditionInfo.getClientId()!=-1)
		    {
		    	sbSQL.append("              and oc.clientid ="+conditionInfo.getClientId()+"\n");//主表客户ID等于当前
		    }
		    sbSQL.append("        )a,\n"+
					     "        ob_user b,\n"+
					     "        ob_user c,\n"+
					     "        userinfo u \n"+
					     "where   a.inputuserid = b.id(+) \n"+
					     "and     a.modifyuserid = c.id(+) \n"+
					     "and     a.auditinguserid = u.id(+) \n" +
					     "order by cpcode desc");
			initDAO();
		    
			prepareStatement(sbSQL.toString());
			
			executeQuery();
			return this.getDataEntitiesFromResultSet(CapitalPlanInfo.class);
			
		}catch(Exception ex)
		{
			throw new ITreasuryDAOException("查找资金计划申报失败，" + ex.getMessage(), ex);
			
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * 删除主表操作，非物理删除
	 * @param long capitalplanId
	 * @throws ITreasuryDAOException
	 */
	public void delCapitalplan(long capitalplanId) throws ITreasuryDAOException {
		try{
			CapitalPlanInfo planInfo = new CapitalPlanInfo();
			planInfo.setId(capitalplanId);
			planInfo.setStatusId(0);	
			initDAO();
			this.update(planInfo);
		}catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		}finally{
			finalizeDAO();
		}
	}

	/**
	 * added by ylguo
	 * 资金计划的复核，将主表中相应的记录的状态置为2
	 * @param long capitalplanId,long modifyuserid,String datetime
	 * @throws ITreasuryDAOException
	 */
	public void checkCapitalplan(long capitalplanId,long modifyuserid,String datetime) throws ITreasuryDAOException{
		StringBuffer sql = new StringBuffer();
		sql.append("update ob_capitalplan oc  set oc.statusid=2");
		sql.append(", \n oc.modifyuserid="+modifyuserid);
		sql.append(", \n oc.modifydate=to_date('"+datetime+"','yyyy-mm-dd')");
		sql.append(" \n where oc.id =");
		sql.append(capitalplanId);
		
		try{
			initDAO();
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("复核资金计划时，修改OB_CAPITALPLAN中ID为"+capitalplanId+"状态失败!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * 资金计划的取消复核，将主表中相应的记录的状态置为1
	 * @param long capitalplanId,long modifyuserid,String datetime
	 * @throws ITreasuryDAOException
	 */
	public void discheckCapitalplan(long capitalplanId) throws ITreasuryDAOException{
		StringBuffer sql = new StringBuffer();
		sql.append("update ob_capitalplan oc  set oc.statusid=1");
		sql.append(", \n oc.modifyuserid= -1");//复核人清空
		sql.append(", \n oc.modifydate= null");//复核日期清空
		sql.append(" \n where oc.id =");
		sql.append(capitalplanId);
		try{
			initDAO();
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("取消复核资金计划时，修改OB_CAPITALPLAN中ID为"+capitalplanId+"状态失败!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo 资金计划汇总查询
	 * @param CapitalPlanGatherInfo conditionInfo
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryByConditionGather(CapitalPlanGatherInfo conditionInfo)throws Exception {
        StringBuffer sql = new StringBuffer();
	    sql.append("select ROWNUM ID,\n" +
	    		   "       a.datefrom,\n");
	    sql.append("       a.dateto,\n");
	    sql.append("       a.capitalTotle,\n");
	    sql.append("       a.AllmoneyIn,\n");
	    sql.append("       b.AllmoneyOut, \n");
	    sql.append("       a.modelid \n");
	    sql.append("from \n");
	    sql.append("(--按时间分组并求和，统计 \n");
	    sql.append("        select moneyIn.stdate datefrom, \n");
	    sql.append("               moneyIn.eddate dateto, \n");
	    sql.append("               count(moneyIn.eddate) capitalTotle, \n");
	    sql.append("               sum(moneyIn.totle) AllmoneyIn, \n");
	    sql.append("               modelid \n");
	    sql.append("        from \n");
	    sql.append("            (--时间段所有客户的流入资金 \n");
	    sql.append("             select  os.total totle, \n");
	    sql.append("                     oc.startdate stdate,  \n");
	    sql.append("                     oc.enddate eddate, \n");
	    sql.append("                     obf.modelid modelid \n");
	    sql.append("              from   ob_subcapitalplan os , \n");
	    sql.append("                     ob_capitalplanconfig obf,\n");
	    sql.append("                     ob_capitalplan oc \n");
	    sql.append("             where   os.capitalplanid = oc.id\n");
	    sql.append("               and   oc.statusid = 20 \n");
	    sql.append("               and   oc.startdate in --目的是将参数时间按周来分段，然后跟相应的资金计划对应起来 \n");
	    sql.append("                                   ( select distinct oc.startdate \n");
	    sql.append("                                     from   ob_capitalplan oc \n");
	    sql.append("                                     where  oc.statusid = 20 \n");
	    if(conditionInfo.getDatefrom() != null){
	    	sql.append("                                 and    ");
	    	sql.append("oc.startdate >= to_date('"+DataFormat.getDateString(conditionInfo.getDatefrom())+"','yyyy-mm-dd') \n");
	    }
	    if(conditionInfo.getDateto() !=null){
	    	sql.append("                                 and    oc.startdate <= to_date('");
	    	sql.append(DataFormat.getDateString(conditionInfo.getDateto())+"','yyyy-mm-dd') \n");
	    }
	    sql.append("                                    ) \n");
	    sql.append("              and os.capitalplanconfigid = obf.id \n");
	    sql.append("               and obf.name='资金流入' \n");
	    sql.append("               --按办事处和币种分开查询 \n");
	    sql.append("               and os.officeid = obf.officeid  \n");
	    sql.append("               and obf.officeid = oc.officeid  \n");
	    sql.append("               and oc.officeid = "+conditionInfo.getOfficeId()+" \n");
	    sql.append("               and os.currencyid = obf.currencyid  \n");
	    sql.append("               and obf.currencyid = oc.currencyid  \n");
	    sql.append("               and oc.currencyid = "+conditionInfo.getCurrencyId()+" \n");
	    sql.append("        )moneyIn   \n");
	    sql.append("        group by moneyIn.stdate,moneyIn.eddate ,moneyIn.modelid\n");
	    sql.append(" )a, \n");
	    sql.append("(--按时间分组并求和，统计 \n");
	    sql.append("        select moneyOut.stdate datefrom, \n");
	    sql.append("               moneyOut.eddate dateto, \n");
	    sql.append("               count(moneyOut.eddate) capitalTotle, \n");
	    sql.append("               sum(moneyOut.totle) AllmoneyOut, \n");
	    sql.append("               modelid \n");
	    sql.append("        from \n");
	    sql.append("            (--时间段所有客户的流入资金 \n");
	    sql.append("             select  os.total totle, \n");
	    sql.append("                     oc.startdate stdate,  \n");
	    sql.append("                     oc.enddate eddate, \n");
	    sql.append("                     obf.modelid modelid \n");
	    sql.append("              from   ob_subcapitalplan os , \n");
	    sql.append("                     ob_capitalplanconfig obf,\n");
	    sql.append("                     ob_capitalplan oc \n");
	    sql.append("             where   os.capitalplanid = oc.id\n");
	    sql.append("               and   oc.statusid = 20 \n");
	    sql.append("               and   oc.startdate in --目的是将参数时间按周来分段，然后跟相应的资金计划对应起来 \n");
	    sql.append("                                   ( select distinct oc.startdate \n");
	    sql.append("                                     from   ob_capitalplan oc \n");
	    sql.append("                                     where  oc.statusid = 20 \n");
	    if(conditionInfo.getDatefrom() != null){
	    	sql.append("                                 and    ");
	    	sql.append("oc.startdate >= to_date('"+DataFormat.getDateString(conditionInfo.getDatefrom())+"','yyyy-mm-dd') \n");
	    }
	    if(conditionInfo.getDateto() !=null){
	    	sql.append("                                 and    oc.startdate <= to_date('");
	    	sql.append(DataFormat.getDateString(conditionInfo.getDateto())+"','yyyy-mm-dd') \n");
	    }
	    sql.append("                                    ) \n");
	    sql.append("              and os.capitalplanconfigid = obf.id \n");
	    sql.append("               and obf.name='资金流出' \n");
	    sql.append("               --按办事处和币种分开查询 \n");
	    sql.append("               and os.officeid = obf.officeid  \n");
	    sql.append("               and obf.officeid = oc.officeid  \n");
	    sql.append("               and oc.officeid = "+conditionInfo.getOfficeId()+" \n");
	    sql.append("               and os.currencyid = obf.currencyid  \n");
	    sql.append("               and obf.currencyid = oc.currencyid  \n");
	    sql.append("               and oc.currencyid = "+conditionInfo.getCurrencyId()+" \n");
	    sql.append("        )moneyOut   \n");
	    sql.append("        group by moneyOut.stdate,moneyOut.eddate ,moneyOut.modelid \n");
	    sql.append(" )b \n");
	    sql.append("where a.datefrom = b.datefrom  \n");
	    sql.append("order by a.datefrom");
	    try{
			
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), "("+sql.toString()+")","*", "1=1", (int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanGatherInfo", null);
			
			return pageLoader;
		}catch(Exception ex)
		{
			throw new ITreasuryDAOException(ex.getMessage(), ex);
		}
	    
	}
	
	/**
	 * added by ylguo,周资金计划标签中需要的2个金额：上周五的长期存款和定期存款
	 * @param FundPlanParamInfo info
	 * @throws Exception
	 * @return double[]
	 */
	public double[] findWeekPlanInfo(FundPlanParamInfo info)throws Exception {
		//上周五日期
		
		String lastFriday = "";
	    lastFriday = DataFormat.getDateString(DataFormat.getFridayDateOfLastWeek(Env.getSystemDateTime()));
	    //lastFriday = "2008-09-03";
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( \n"+
				   "--上周五短期存款额 \n" +
				   "select nvl(sum(mbalance),0)/10000 mbalanceA from \n" +
				   "(   --短期的定期存款 \n" +
				   "    select dab.mbalance,\n" +
				   "           dab.naccountid,\n" +
				   "           dab.nsubaccountid,\n" +
				   "           dab.dtdate\n" +
				   "    from ( \n" +
				   "           --短期定期存款子账户 \n" +
				   "           select ac.id ID,            --主账户ID, \n" +
				   "                  ac.saccountno,       --主账户编号 \n" +
				   "                  ac.sname,            --账户名, \n" +
				   "                  subaccount.id SUBID  --子账户ID \n" +
				   "           from   sett_account ac, \n" +
				   "                  sett_subaccount subaccount \n" +
				   "           where  ac.id=subaccount.naccountid \n" +
				   "           and    subaccount.af_ndepositterm<=3   --短期的定期存款 \n" +
				   "           and    ac.naccounttypeid in (select id \n" +
				   "                                        from   sett_accounttype \n" +
				   "                                        where  naccountgroupid=2 --定期类型代码为2 \n" +
				   "                                        and    officeid="+info.getOffice()+"\n" +
				   "                                        and    nstatusId=1          --正常账户 \n" +
				   "                                        and    currencyId="+info.getCurrency()+"\n" +
				   "                                        ) \n" +
				   "           and   ac.nofficeid="+info.getOffice()+"\n" +
				   "           and   ac.ncurrencyid ="+info.getCurrency()+"\n" +
				   "           and   ac.nstatusid =1 \n" +
				   "     )subac, \n" +
				   "     sett_dailyaccountbalance dab \n" +
				   "     where subac.SUBID = dab.nsubaccountid \n" +
				   "     and   dab.dtdate = to_date('" +lastFriday+"','yyyy-mm-dd')   --上周5  \n"+
				   "\n" +
				   "union \n" +
				   "\n" +
				   "     --活期存款 \n" +
				   "     select dab.mbalance, \n" +
				   "            dab.naccountid,\n" +
				   "            dab.nsubaccountid,\n" +
				   "            dab.dtdate \n" +
				   "     from (" +
				   "            --活期存款子账户 \n" +
				   "            select ac.id ID,            --主账户ID,\n" +
				   "                   ac.saccountno,       --主账户编号,\n" +
				   "                   ac.sname,            --账户名,\n" +
				   "                   subaccount.id SUBID  --子账户ID \n" +
				   "            from   sett_account ac, \n" +
				   "                   sett_subaccount subaccount \n" +
				   "            where  ac.id=subaccount.naccountid \n" +
				   "            and    ac.naccounttypeid in (select id  \n" +
				   "                                         from   sett_accounttype act\n" +
				   "                                         where  naccountgroupid=1 --活期类型代码为1 \n" +
				   "                                         and    act.saccounttypecode !=" +Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"--过滤委托存款\n"+
				   "                                         and    officeid="+info.getOffice()+"\n" +
				   "                                         and    nstatusId=1 \n" +
				   "                                         and    currencyId="+info.getCurrency()+"\n" +
				   "                                         ) \n" +
				   "             and   ac.nofficeid="+info.getOffice()+"\n" +
				   "             and   ac.ncurrencyid ="+info.getCurrency()+"\n" +
				   "             and   ac.nstatusid =1 \n" +
				   "       )subac,\n" +
				   "        sett_dailyaccountbalance dab \n" +
				   "       where subac.SUBID = dab.nsubaccountid \n" +
				   "       and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --上周5  \n"+
				   "\n"+
				   "union \n" +
				   "\n" +
				   "     --通知存款\n" +
				   "     select dab.mbalance,\n" +
				   "            dab.naccountid,\n" +
				   "            dab.nsubaccountid,\n" +
				   "            dab.dtdate \n" +
				   "     from ( \n" +
				   "            --通知存款子账户 \n" +
				   "            select ac.id ID,            --主账户ID,\n" +
				   "                   ac.saccountno,       --主账户编号,\n" +
				   "                   ac.sname,            --账户名,\n" +
				   "                   subaccount.id SUBID  --子账户ID \n" +
				   "            from   sett_account ac, \n" +
				   "                   sett_subaccount subaccount \n" +
				   "            where  ac.id=subaccount.naccountid \n" +
				   "            and    ac.naccounttypeid in (select id \n" +
				   "                                         from   sett_accounttype \n" +
				   "                                         where  naccountgroupid=3 --通知存款类型代码为3 \n" +
				   "                                         and    officeid="+info.getOffice()+"\n" +
				   "                                         and    nstatusId=1 \n" +
				   "                                         and    currencyId="+info.getCurrency()+"\n" +
				   "                                         ) \n" +
				   "            and   ac.nofficeid="+info.getOffice()+"\n" +
				   "            and   ac.nstatusid =1 \n" +
				   "            and   ac.ncurrencyid ="+info.getCurrency()+"\n" +
				   "     )subac,\n" +
				   "      sett_dailyaccountbalance dab \n" +
				   "     where subac.SUBID = dab.nsubaccountid \n" +
				   "     and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --上周5  \n"+
				   "  ) \n )a, \n" +
				   "(--上周五长期存款额，大于3个月的定期存款 \n" +
				   " select nvl(sum(mbalance),0)/10000 mbalanceB from \n" +
				   " (       --短期的定期存款 \n" +
				   "         select dab.mbalance, \n" +
				   "                dab.naccountid, \n" +
				   "                dab.nsubaccountid,\n" +
				   "                dab.dtdate \n" +
				   "         from \n" +
				   "         (       --短期定期存款子账户 \n" +
				   "                 select ac.id ID,            --主账户ID,\n" +
				   "                        ac.saccountno,       --主账户编号,\n" +
				   "                        ac.sname,            --账户名,\n" +
				   "                        subaccount.id SUBID  --子账户ID \n" +
				   "                 from   sett_account ac, \n" +
				   "                        sett_subaccount subaccount \n" +
				   "                 where  ac.id=subaccount.naccountid \n" +
				   "                 and    subaccount.af_ndepositterm>3 --长期的定期存款 \n" +
				   "                 and    ac.naccounttypeid in (select id \n" +
				   "                                              from   sett_accounttype \n" +
				   "                                              where  naccountgroupid=2    --定期类型代码为2 \n" +
				   "                                              and    officeid="+info.getOffice()+"\n" +
				   "                                              and    nstatusId=1 \n" +
				   "                                              and    currencyId="+info.getCurrency()+"\n" +
				   "                                              ) \n" +
				   "                 and   ac.nofficeid="+info.getOffice()+"\n" +
				   "                 and   ac.nstatusid =1 \n" +
				   "                 and   ac.ncurrencyid ="+info.getCurrency()+"\n" +
				   "          )subac,\n" +
				   "          sett_dailyaccountbalance dab \n" +
				   "          where subac.SUBID = dab.nsubaccountid \n" +
				   "          and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --上周5  \n"+
				   "  )\n" +
				   ")b" );
		try{
			initDAO();
			prepareStatement(sql.toString());
			transRS = executeQuery();
			double[] balances = new double[2];
			if(transRS.next()){
				balances[0] =  DataFormat.formatDouble(transRS.getDouble("mbalanceA"),2);
				balances[1]	=  DataFormat.formatDouble(transRS.getDouble("mbalanceB"),2);
			}
			return balances;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("周资金计划的长期和短期存款金额查找失败!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	
	
	/**
	public PageLoader queryCapitalPlans(CapitalPlanCondition conditionInfo) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		StringBuffer sbSQL1 = new StringBuffer();
		StringBuffer sbSQL2 = new StringBuffer();
		StringBuffer sbSQL3 = new StringBuffer();
		sbSQL.append(" oc.ID,oc.CPCODE cpCode,oc.MODELID modelId,oc.CURRENCYID currencyId,oc.OFFICEID officeId,oc.CLIENTID clientId,oc.startdate startdate,oc.enddate enddate,oc.statusId statusId,ct.scode clientcode, ct.sname clientname ,oc.AUDITINGUSERID,oc.AUDITINGDATE");
		sbSQL1.append(" OB_CAPITALPLAN oc, client ct ");
		sbSQL2.append(" ct.id = oc.clientid ");
		if(conditionInfo.getClientIDStart()>0)
		{
			 sbSQL2.append(" and oc.clientid >="+conditionInfo.getClientIDStart());
		}
		if(conditionInfo.getClientIDEnd()>0)
		{
			 sbSQL2.append(" and oc.clientid <="+conditionInfo.getClientIDEnd());
		}
		if(conditionInfo.getStartdate() != null){//当起始日期有参数时
		      sbSQL2.append(" and oc.startdate >= to_date( '");
		      sbSQL2.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' )");
		}
		if(conditionInfo.getEnddate() != null){  //当结束日期有参数时
	          sbSQL2.append(" and oc.startdate <= to_date( '");
	          sbSQL2.append(DataFormat.getDateString(conditionInfo.getEnddate())+"','yyyy-mm-dd' )");
		}
	    if(conditionInfo.getStatusId() != -1){
		      sbSQL2.append(" and oc.statusid = "+conditionInfo.getStatusId());
	    }
	    sbSQL3.append(" order by oc.clientid,oc.startdate ");
		try{
			
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), sbSQL1.toString(), sbSQL.toString(), sbSQL2.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo", null);
			pageLoader.setOrderBy(" " + sbSQL3.toString() + " ");
			
			return pageLoader;
		}catch(Exception ex)
		{
			throw new ITreasuryDAOException(ex.getMessage(), ex);
		}
	}
	*/
	public PageLoader queryCapitalPlans(CapitalPlanCondition conditionInfo) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		//modified by ylguo
		sbSQL.append("select a.*,\n" +
				     "       b.sname inputerName,\n" +
				     "       c.sname modifyerName,\n" +
				     "       u.sname auditerName \n" +
				     "from( \n" +
				     "       SELECT  oc.ID,\n" +
				     "               oc.CPCODE cpCode,\n" +
				     "       		 oc.MODELID modelId,\n" +
				     "               oc.CURRENCYID currencyId,\n" +
				     "               oc.OFFICEID officeId,\n" +
				     "               oc.CLIENTID clientId,\n" +
				     "               oc.startdate startdate,\n" +
				     "               oc.enddate enddate,\n" +
				     "               oc.statusId statusId,\n" +
				     "               ct.scode clientcode,\n" +
				     "               ct.sname clientname ,\n" +
				     "               oc.AUDITINGUSERID,\n" +
				     "               oc.AUDITINGDATE,\n" +
				     "               oc.inputdate,--添加了录入人和复核人信息 \n" +
					 "               oc.inputuserid,\n" +
					 "               oc.modifydate,\n" +
					 "               oc.modifyuserid \n" +
					 "       FROM    OB_CAPITALPLAN oc,\n" +
					 "               client ct \n" +
					 "       WHERE   ct.id = oc.clientid \n" +
					 "				and oc.statusid !=");
		sbSQL.append(OBConstant.SettInstrStatus.DELETE);
		
		if(conditionInfo.getClientIDStart()>0)
		{
			 sbSQL.append("               and oc.clientid >="+conditionInfo.getClientIDStart()+"\n");
		}
		if(conditionInfo.getClientIDEnd()>0)
		{
			 sbSQL.append("               and oc.clientid <="+conditionInfo.getClientIDEnd()+"\n");
		}
		if(conditionInfo.getCPIDStartCtrl()!=null && conditionInfo.getCPIDStartCtrl().length()>0)
		{
			 sbSQL.append("               and oc.CPCODE >="+conditionInfo.getCPIDStartCtrl()+"\n");
		}
		if(conditionInfo.getCPIDEndCtrl()!=null && conditionInfo.getCPIDEndCtrl().length()>0)
		{
			 sbSQL.append("               and oc.CPCODE <="+conditionInfo.getCPIDEndCtrl()+"\n");
		}
		if(conditionInfo.getStartdate() != null){//当起始日期有参数时
		      sbSQL.append("               and oc.startdate >= to_date( '");
		      sbSQL.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' ) \n");
		}
		if(conditionInfo.getEnddate() != null){  //当结束日期有参数时
	          sbSQL.append("               and oc.startdate <= to_date( '");
	          sbSQL.append(DataFormat.getDateString(conditionInfo.getEnddate())+"','yyyy-mm-dd' ) \n");
		}
	    if(conditionInfo.getStatusId() != -1){
		      sbSQL.append("               and oc.statusid = "+conditionInfo.getStatusId()+"\n");
	    }
	    sbSQL.append("               order by oc.clientid,oc.startdate \n");
	    sbSQL.append(")a,\n" +
	    			"ob_user b,\n" +
	    			"ob_user c,\n" +
	    			"userinfo u \n" +
	    			"where a.inputuserid = b.id(+) \n" +
	    			"and   a.modifyuserid = c.id(+) \n" +
	    			"and   a.auditinguserid = u.id(+) \n");
	    System.out.println("-----------------sbSQL---------="+sbSQL.toString());
		try{
			
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), "("+sbSQL.toString()+")","*", "1=1", (int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo", null);
			return pageLoader;
		}catch(Exception ex)
		{
			throw new ITreasuryDAOException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param strCpID
	 * @param strAction
	 * @param auditingUserid
	 * @throws ITreasuryDAOException
	 */
	public void auditingAllCapitalplans(String strCpID[],String strAction,long auditingUserid) throws ITreasuryDAOException{
		StringBuffer sql = new StringBuffer();
		String temp = "";
		if(strAction.equals("toCommit") || strAction.equals("toAuditing"))
		{
			sql.append("update ob_capitalplan oc  set oc.statusid="+OBConstant.SettInstrStatus.APPROVALED);
			sql.append(", \n oc.auditinguserid="+auditingUserid);
			sql.append(", \n oc.auditingdate=sysdate");
		}
		else if(strAction.equals("toRefuse") || strAction.equals("toDisAuditing"))
		{
			sql.append("update ob_capitalplan oc  set oc.statusid="+OBConstant.SettInstrStatus.SAVE+",MODIFYUSERID=null,MODIFYDATE=null,AUDITINGUSERID=null,AUDITINGDATE=null ");
		}
		
		sql.append(" \n where oc.id in");
		sql.append(" ( ");
		for(int i =0;i<strCpID.length;i++)
		{
			temp+=strCpID[i]+",";
		}
		sql.append(temp.substring(0, temp.length()-1));
		sql.append(" ) ");
		
		try{
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("批量审批资金计划时，批量修改OB_CAPITALPLAN状态失败!",e);
		}
	}
	/**
	 * 查询是否存在状态已经被修改得数据
	 * @param strCpID
	 * @param strAction
	 * @param auditingUserid
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public boolean checkAuditingAllCapitalplansStatus(String strCpID[],String strAction) throws ITreasuryDAOException{
		boolean istrue = false;
		StringBuffer sql = new StringBuffer();
		String temp = "";
		
		sql.append("select oc.statusid from  ob_capitalplan oc  where ");
		sql.append("    oc.id in");
		sql.append(" ( ");
		for(int i =0;i<strCpID.length;i++)
		{
			temp+=strCpID[i]+",";
		}
		sql.append(temp.substring(0, temp.length()-1));
		sql.append(" ) ");
		
		try{
			prepareStatement(sql.toString());
			transRS = executeQuery();
			while(transRS.next())
			{
				if(strAction.equals("toCommit") || strAction.equals("toRefuse") || strAction.equals("toAuditing"))
				{
					if(transRS.getLong("statusid")!=OBConstant.SettInstrStatus.CHECK)
					{
						istrue = true;
					}
				}
				//取消审批
				else if( strAction.equals("toDisAuditing"))
				{
					if(transRS.getLong("statusid")!=OBConstant.SettInstrStatus.APPROVALED)
					{
						istrue = true;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("资金计划：，查询OB_CAPITALPLAN数据错误!",e);
		}
		return istrue;
	}
	
	public boolean checkCapitalplanStatus(long capitalplanId,long status) throws ITreasuryDAOException{
		boolean istrue = false;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select oc.id from  ob_capitalplan oc  where ");
		sql.append("  oc.id ="+ capitalplanId);
		sql.append("  and oc.STATUSID="+status);
		try{
			prepareStatement(sql.toString());
			transRS = executeQuery();
			if(transRS.next())
			{
				istrue = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("资金计划：，查询OB_CAPITALPLAN数据错误!",e);
		}
		return istrue;
	}

	
}
