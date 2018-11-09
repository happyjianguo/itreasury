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
//			throw new ITreasuryDAOException("���ݲ����OB_SUBCAPITALPLANʧ�ܣ�" + e.getMessage(),e);
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
	 * ���Ҳ���,ʱ�����ֻ�ȽϿ�ʼʱ�䣬��Ϊ�ͻ��п���������ʱ�������ʱ��ʱ�����䲢û��һ�����ڵ�ʱ�䡣
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
			if(conditionInfo.getStartdate() != null ){//����ʼ�����в���ʱ
		    sbSQL.append("              and oc.startdate >= to_date( '");
			      sbSQL.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' )\n");
			}
			if(conditionInfo.getEnddate() != null ){  //�����������в���ʱ
		          sbSQL.append("              and oc.startdate <= to_date( '");
		          sbSQL.append(DataFormat.getDateString(conditionInfo.getEnddate())+"','yyyy-mm-dd' )\n");
			}
			//����һ��cpcode����������"�ʽ�ƻ�����ѯ"�˵��µ�
			if(conditionInfo.getCpCode() != null && conditionInfo.getCpCode()!="" && conditionInfo.getCpCode().length()>0){ 
		        sbSQL.append("              and oc.cpcode = "+conditionInfo.getCpCode()+"\n");
		    }
		    if(conditionInfo.getStatusId() != -1){
		    	sbSQL.append("              and oc.statusid = "+conditionInfo.getStatusId()+"\n");
		    }else{
		    	sbSQL.append("              and oc.statusid !=0 \n");//��v001.jspҳ��״̬ѡȫ��ʱ��ɾ��״̬�µĲ���ʾ
		    }
		    if(conditionInfo.getClientId()!=-1)
		    {
		    	sbSQL.append("              and oc.clientid ="+conditionInfo.getClientId()+"\n");//����ͻ�ID���ڵ�ǰ
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
			throw new ITreasuryDAOException("�����ʽ�ƻ��걨ʧ�ܣ�" + ex.getMessage(), ex);
			
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * ɾ�����������������ɾ��
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
	 * �ʽ�ƻ��ĸ��ˣ�����������Ӧ�ļ�¼��״̬��Ϊ2
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
			throw new ITreasuryDAOException("�����ʽ�ƻ�ʱ���޸�OB_CAPITALPLAN��IDΪ"+capitalplanId+"״̬ʧ��!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * �ʽ�ƻ���ȡ�����ˣ�����������Ӧ�ļ�¼��״̬��Ϊ1
	 * @param long capitalplanId,long modifyuserid,String datetime
	 * @throws ITreasuryDAOException
	 */
	public void discheckCapitalplan(long capitalplanId) throws ITreasuryDAOException{
		StringBuffer sql = new StringBuffer();
		sql.append("update ob_capitalplan oc  set oc.statusid=1");
		sql.append(", \n oc.modifyuserid= -1");//���������
		sql.append(", \n oc.modifydate= null");//�����������
		sql.append(" \n where oc.id =");
		sql.append(capitalplanId);
		try{
			initDAO();
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("ȡ�������ʽ�ƻ�ʱ���޸�OB_CAPITALPLAN��IDΪ"+capitalplanId+"״̬ʧ��!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo �ʽ�ƻ����ܲ�ѯ
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
	    sql.append("(--��ʱ����鲢��ͣ�ͳ�� \n");
	    sql.append("        select moneyIn.stdate datefrom, \n");
	    sql.append("               moneyIn.eddate dateto, \n");
	    sql.append("               count(moneyIn.eddate) capitalTotle, \n");
	    sql.append("               sum(moneyIn.totle) AllmoneyIn, \n");
	    sql.append("               modelid \n");
	    sql.append("        from \n");
	    sql.append("            (--ʱ������пͻ��������ʽ� \n");
	    sql.append("             select  os.total totle, \n");
	    sql.append("                     oc.startdate stdate,  \n");
	    sql.append("                     oc.enddate eddate, \n");
	    sql.append("                     obf.modelid modelid \n");
	    sql.append("              from   ob_subcapitalplan os , \n");
	    sql.append("                     ob_capitalplanconfig obf,\n");
	    sql.append("                     ob_capitalplan oc \n");
	    sql.append("             where   os.capitalplanid = oc.id\n");
	    sql.append("               and   oc.statusid = 20 \n");
	    sql.append("               and   oc.startdate in --Ŀ���ǽ�����ʱ�䰴�����ֶΣ�Ȼ�����Ӧ���ʽ�ƻ���Ӧ���� \n");
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
	    sql.append("               and obf.name='�ʽ�����' \n");
	    sql.append("               --�����´��ͱ��ַֿ���ѯ \n");
	    sql.append("               and os.officeid = obf.officeid  \n");
	    sql.append("               and obf.officeid = oc.officeid  \n");
	    sql.append("               and oc.officeid = "+conditionInfo.getOfficeId()+" \n");
	    sql.append("               and os.currencyid = obf.currencyid  \n");
	    sql.append("               and obf.currencyid = oc.currencyid  \n");
	    sql.append("               and oc.currencyid = "+conditionInfo.getCurrencyId()+" \n");
	    sql.append("        )moneyIn   \n");
	    sql.append("        group by moneyIn.stdate,moneyIn.eddate ,moneyIn.modelid\n");
	    sql.append(" )a, \n");
	    sql.append("(--��ʱ����鲢��ͣ�ͳ�� \n");
	    sql.append("        select moneyOut.stdate datefrom, \n");
	    sql.append("               moneyOut.eddate dateto, \n");
	    sql.append("               count(moneyOut.eddate) capitalTotle, \n");
	    sql.append("               sum(moneyOut.totle) AllmoneyOut, \n");
	    sql.append("               modelid \n");
	    sql.append("        from \n");
	    sql.append("            (--ʱ������пͻ��������ʽ� \n");
	    sql.append("             select  os.total totle, \n");
	    sql.append("                     oc.startdate stdate,  \n");
	    sql.append("                     oc.enddate eddate, \n");
	    sql.append("                     obf.modelid modelid \n");
	    sql.append("              from   ob_subcapitalplan os , \n");
	    sql.append("                     ob_capitalplanconfig obf,\n");
	    sql.append("                     ob_capitalplan oc \n");
	    sql.append("             where   os.capitalplanid = oc.id\n");
	    sql.append("               and   oc.statusid = 20 \n");
	    sql.append("               and   oc.startdate in --Ŀ���ǽ�����ʱ�䰴�����ֶΣ�Ȼ�����Ӧ���ʽ�ƻ���Ӧ���� \n");
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
	    sql.append("               and obf.name='�ʽ�����' \n");
	    sql.append("               --�����´��ͱ��ַֿ���ѯ \n");
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
	 * added by ylguo,���ʽ�ƻ���ǩ����Ҫ��2����������ĳ��ڴ��Ͷ��ڴ��
	 * @param FundPlanParamInfo info
	 * @throws Exception
	 * @return double[]
	 */
	public double[] findWeekPlanInfo(FundPlanParamInfo info)throws Exception {
		//����������
		
		String lastFriday = "";
	    lastFriday = DataFormat.getDateString(DataFormat.getFridayDateOfLastWeek(Env.getSystemDateTime()));
	    //lastFriday = "2008-09-03";
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( \n"+
				   "--��������ڴ��� \n" +
				   "select nvl(sum(mbalance),0)/10000 mbalanceA from \n" +
				   "(   --���ڵĶ��ڴ�� \n" +
				   "    select dab.mbalance,\n" +
				   "           dab.naccountid,\n" +
				   "           dab.nsubaccountid,\n" +
				   "           dab.dtdate\n" +
				   "    from ( \n" +
				   "           --���ڶ��ڴ�����˻� \n" +
				   "           select ac.id ID,            --���˻�ID, \n" +
				   "                  ac.saccountno,       --���˻���� \n" +
				   "                  ac.sname,            --�˻���, \n" +
				   "                  subaccount.id SUBID  --���˻�ID \n" +
				   "           from   sett_account ac, \n" +
				   "                  sett_subaccount subaccount \n" +
				   "           where  ac.id=subaccount.naccountid \n" +
				   "           and    subaccount.af_ndepositterm<=3   --���ڵĶ��ڴ�� \n" +
				   "           and    ac.naccounttypeid in (select id \n" +
				   "                                        from   sett_accounttype \n" +
				   "                                        where  naccountgroupid=2 --�������ʹ���Ϊ2 \n" +
				   "                                        and    officeid="+info.getOffice()+"\n" +
				   "                                        and    nstatusId=1          --�����˻� \n" +
				   "                                        and    currencyId="+info.getCurrency()+"\n" +
				   "                                        ) \n" +
				   "           and   ac.nofficeid="+info.getOffice()+"\n" +
				   "           and   ac.ncurrencyid ="+info.getCurrency()+"\n" +
				   "           and   ac.nstatusid =1 \n" +
				   "     )subac, \n" +
				   "     sett_dailyaccountbalance dab \n" +
				   "     where subac.SUBID = dab.nsubaccountid \n" +
				   "     and   dab.dtdate = to_date('" +lastFriday+"','yyyy-mm-dd')   --����5  \n"+
				   "\n" +
				   "union \n" +
				   "\n" +
				   "     --���ڴ�� \n" +
				   "     select dab.mbalance, \n" +
				   "            dab.naccountid,\n" +
				   "            dab.nsubaccountid,\n" +
				   "            dab.dtdate \n" +
				   "     from (" +
				   "            --���ڴ�����˻� \n" +
				   "            select ac.id ID,            --���˻�ID,\n" +
				   "                   ac.saccountno,       --���˻����,\n" +
				   "                   ac.sname,            --�˻���,\n" +
				   "                   subaccount.id SUBID  --���˻�ID \n" +
				   "            from   sett_account ac, \n" +
				   "                   sett_subaccount subaccount \n" +
				   "            where  ac.id=subaccount.naccountid \n" +
				   "            and    ac.naccounttypeid in (select id  \n" +
				   "                                         from   sett_accounttype act\n" +
				   "                                         where  naccountgroupid=1 --�������ʹ���Ϊ1 \n" +
				   "                                         and    act.saccounttypecode !=" +Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"--����ί�д��\n"+
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
				   "       and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --����5  \n"+
				   "\n"+
				   "union \n" +
				   "\n" +
				   "     --֪ͨ���\n" +
				   "     select dab.mbalance,\n" +
				   "            dab.naccountid,\n" +
				   "            dab.nsubaccountid,\n" +
				   "            dab.dtdate \n" +
				   "     from ( \n" +
				   "            --֪ͨ������˻� \n" +
				   "            select ac.id ID,            --���˻�ID,\n" +
				   "                   ac.saccountno,       --���˻����,\n" +
				   "                   ac.sname,            --�˻���,\n" +
				   "                   subaccount.id SUBID  --���˻�ID \n" +
				   "            from   sett_account ac, \n" +
				   "                   sett_subaccount subaccount \n" +
				   "            where  ac.id=subaccount.naccountid \n" +
				   "            and    ac.naccounttypeid in (select id \n" +
				   "                                         from   sett_accounttype \n" +
				   "                                         where  naccountgroupid=3 --֪ͨ������ʹ���Ϊ3 \n" +
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
				   "     and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --����5  \n"+
				   "  ) \n )a, \n" +
				   "(--�����峤�ڴ������3���µĶ��ڴ�� \n" +
				   " select nvl(sum(mbalance),0)/10000 mbalanceB from \n" +
				   " (       --���ڵĶ��ڴ�� \n" +
				   "         select dab.mbalance, \n" +
				   "                dab.naccountid, \n" +
				   "                dab.nsubaccountid,\n" +
				   "                dab.dtdate \n" +
				   "         from \n" +
				   "         (       --���ڶ��ڴ�����˻� \n" +
				   "                 select ac.id ID,            --���˻�ID,\n" +
				   "                        ac.saccountno,       --���˻����,\n" +
				   "                        ac.sname,            --�˻���,\n" +
				   "                        subaccount.id SUBID  --���˻�ID \n" +
				   "                 from   sett_account ac, \n" +
				   "                        sett_subaccount subaccount \n" +
				   "                 where  ac.id=subaccount.naccountid \n" +
				   "                 and    subaccount.af_ndepositterm>3 --���ڵĶ��ڴ�� \n" +
				   "                 and    ac.naccounttypeid in (select id \n" +
				   "                                              from   sett_accounttype \n" +
				   "                                              where  naccountgroupid=2    --�������ʹ���Ϊ2 \n" +
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
				   "          and   dab.dtdate = to_date('"+lastFriday+"','yyyy-mm-dd')   --����5  \n"+
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
			throw new ITreasuryDAOException("���ʽ�ƻ��ĳ��ںͶ��ڴ�������ʧ��!",e);
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
		if(conditionInfo.getStartdate() != null){//����ʼ�����в���ʱ
		      sbSQL2.append(" and oc.startdate >= to_date( '");
		      sbSQL2.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' )");
		}
		if(conditionInfo.getEnddate() != null){  //�����������в���ʱ
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
				     "               oc.inputdate,--�����¼���˺͸�������Ϣ \n" +
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
		if(conditionInfo.getStartdate() != null){//����ʼ�����в���ʱ
		      sbSQL.append("               and oc.startdate >= to_date( '");
		      sbSQL.append(DataFormat.getDateString(conditionInfo.getStartdate())+" ', 'yyyy-mm-dd' ) \n");
		}
		if(conditionInfo.getEnddate() != null){  //�����������в���ʱ
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
			throw new ITreasuryDAOException("���������ʽ�ƻ�ʱ�������޸�OB_CAPITALPLAN״̬ʧ��!",e);
		}
	}
	/**
	 * ��ѯ�Ƿ����״̬�Ѿ����޸ĵ�����
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
				//ȡ������
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
			throw new ITreasuryDAOException("�ʽ�ƻ�������ѯOB_CAPITALPLAN���ݴ���!",e);
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
			throw new ITreasuryDAOException("�ʽ�ƻ�������ѯOB_CAPITALPLAN���ݴ���!",e);
		}
		return istrue;
	}

	
}
