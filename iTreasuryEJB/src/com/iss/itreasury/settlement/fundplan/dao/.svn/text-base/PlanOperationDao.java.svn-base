package com.iss.itreasury.settlement.fundplan.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.fundplan.dataentity.GatherPrintPlanOperationInfo;
import com.iss.itreasury.settlement.fundplan.dataentity.PlanOperationInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;

/**
 * @author Boxu
 *
 */
public class PlanOperationDao extends ITreasuryDAO {
	public PlanOperationDao(){
		super("sett_planOperation");
	}
	
	/**
	 * @param planInfo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public PlanOperationInfo findPlanOperation(PlanOperationInfo planInfo)throws RemoteException, SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		PlanOperationInfo info = null;
		try
		{
			strSQL = new StringBuffer();
			con = Database.getConnection();
			
		    strSQL.append(" select * from sett_planOperation where 1=1 ");
		    if(planInfo.getId() > 0)
		    {
		    	strSQL.append(" and id = "+ planInfo.getId() +"");
		    }
		    if(planInfo.getOfficeid() > 0)
		    {
		    	strSQL.append(" and officeid = "+ planInfo.getOfficeid() +"");
		    }
		    if(planInfo.getCurrencyid() > 0)
		    {
		    	strSQL.append(" and currencyid = "+ planInfo.getCurrencyid() +"");
		    }
			
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new PlanOperationInfo();
				
				info.setId(rs.getLong("id"));
				info.setRelationDate(rs.getTimestamp("relationDate"));
				info.setGaveDept(rs.getString("gaveDept"));
				info.setDoDept(rs.getString("doDept"));
				info.setOpinion(rs.getString("opinion"));
				info.setHandleUser(rs.getString("handleUser"));
				info.setPrincipal(rs.getString("principal"));
				info.setAccountNo(rs.getString("accountNo"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
		}
		catch(SQLException re) {
			throw new RemoteException(re.getMessage());
		}
		catch(Exception re) {
			throw new RemoteException(re.getMessage());
		}
		finally{
			if(con != null) {
				con.close();
				con = null;
			}
			if(ps != null) {
				ps.close();
				ps = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		}
		return info;
	}
	
	/**
	 * @param planInfo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public Collection GatherPrintPlanOperation(PlanOperationInfo planInfo)throws RemoteException, SQLException{
		long lRowNum = 2;
		//long lRowEvelid = -1;
		long lRowEvelNum = 0;
		long[] lRowEvelNums = new long[3];
		lRowEvelNums[1]=1;
		lRowEvelNums[2]=1;
		
		int lCurLevelNum = 1; //当前序号级别
		//long lRowEvelNum3 = 0;
		long preID = -1;
		long preParentID = -1;
		long tempExtendid = -1;
		int today = 0;
		double inAmount = 0.0;
		double outAmount = 0.0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		Collection coll = new ArrayList();
		GatherPrintPlanOperationInfo info = null;
		try
		{
			con = Database.getConnection();
			
			strSQL = GatherPrintPlanOperationSQL(planInfo);
			
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			//当前是星期几
			today = DataFormat.getWeekDay(planInfo.getRelationDate());
			
			while(rs.next())
			{
				info = new GatherPrintPlanOperationInfo();
				
				long extendid = -1;
				String typename = "";
				long parentID = -1;
				long pID = -1;
				double sunAmount = 0.0;
				String strTemp = "";
				extendid = rs.getLong("extendid");
				typename = rs.getString("typename");
				parentID = rs.getLong("nParentCorpID1");
				pID = rs.getLong("id");
				if(preID < 0)
				{
					preID = pID;
				}
				if(preParentID < 0)
				{
					preParentID = parentID;
				}
				//levelid = rs.getLong("nlevelid");
				//levelcode = rs.getString("nlevelcode");
				
				switch(today - 1) {
				case 1:
					inAmount = rs.getDouble("inmondaycapital");
					outAmount = rs.getDouble("outmondaycapital");
					break;
				case 2:
					inAmount = rs.getDouble("intuesdaycapital");
					outAmount = rs.getDouble("outtuesdaycapital");
					break;
				case 3:
					inAmount = rs.getDouble("inwednesdaycapital");
					outAmount = rs.getDouble("outwednesdaycapital");
					break;
				case 4:
					inAmount = rs.getDouble("inthursdaycapital");
					outAmount = rs.getDouble("outthursdaycapital");
					break;
				case 5:
					inAmount = rs.getDouble("infridaycapital");
					outAmount = rs.getDouble("outfridaycapital");
					break;
				default:
					inAmount = 0;
					outAmount = 0;
				}
				info.setInAmount(inAmount);
				info.setOutAmount(outAmount);
				
				//集团本部
				if(extendid == 1)
				{
					info.setSerialNum(DataFormat.getChineseNumBelowTenThousand("1", 1));
					info.setClientname(rs.getString("clientname"));
					info.setExtendid(extendid);
					info.setTypename(typename);
					
					//info.setSunamount(rs.getDouble("sunamount"));
					//格式化为万元为单位显示
					sunAmount = rs.getDouble("sunamount");
					sunAmount = DataFormat.formatDouble(UtilOperation.Arith.div(sunAmount, 10000));
					info.setSunamount(sunAmount);
					
					coll.add(info);
					preID = -1;
					preParentID = -1;
				}
				else
				{
					//企业类别显示处理
					if(extendid > 1 && extendid != tempExtendid)
					{
						GatherPrintPlanOperationInfo headInfo = new GatherPrintPlanOperationInfo();
						headInfo.setSerialNum(DataFormat.getChineseNumBelowTenThousand(String.valueOf(lRowNum), 1));
						headInfo.setClientname(typename);
						coll.add(headInfo);
						lRowNum++;
						tempExtendid = extendid;
						lRowEvelNums[1] = 1;
						lRowEvelNums[2] = 1;
					}
										
					//如果上一行是本行的上级客户，下降一级
					if(parentID == preID)
					{
						lCurLevelNum = 2;
					}else if(parentID!=preParentID && lCurLevelNum==2)
					{
						lCurLevelNum = 1;
						lRowEvelNums[2]=1;
					}
					
					strTemp = getCurrentSerialNo(lRowEvelNums[lCurLevelNum], lCurLevelNum);					
					
					lRowEvelNums[lCurLevelNum]++;
					
					info.setSerialNum(strTemp);
					info.setClientname(rs.getString("clientname"));
					info.setExtendid(extendid);
					info.setTypename(typename);
					
					//info.setSunamount(rs.getDouble("sunamount"));
					//格式化为万元为单位显示
					sunAmount = rs.getDouble("sunamount");
					sunAmount = DataFormat.formatDouble(UtilOperation.Arith.div(sunAmount, 10000));
					info.setSunamount(sunAmount);
					
					coll.add(info);
					
					preID = pID;
					preParentID = parentID;
				}
			}
		}
		catch(SQLException re) {
			throw new RemoteException(re.getMessage());
		}
		catch(Exception re) {
			throw new RemoteException(re.getMessage());
		}
		finally{
			if(con != null) {
				con.close();
				con = null;
			}
			if(ps != null) {
				ps.close();
				ps = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		}
		return coll;
	}
	
	private String getCurrentSerialNo(long curSerialNo, long curLevelNum) {
		if(curLevelNum==1)
		{
			return "&nbsp;&nbsp;&nbsp;&nbsp;" + String.valueOf(curSerialNo);
		}else{
			return "&nbsp;&nbsp;" + String.valueOf(curSerialNo) + ")";
		}
	}

	/**
	 * @param planInfo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public String GatherPrintPlanOperationSQL(PlanOperationInfo planInfo) throws RemoteException, SQLException
	{
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append(" select * from ( \n");
		strSQL.append(" select Client.sName clientname, \n");
		strSQL.append("        ce.id extendid, \n");
		strSQL.append("        ce.name typename, \n");
		strSQL.append("        sum(AccountBalance.sumAccountHistoryBalance) sunamount, \n");
		strSQL.append("        Client.nParentCorpID1, \n");
		strSQL.append("        Client.id, \n");
		strSQL.append("        capital.inMONDAYCAPITAL, \n");
		strSQL.append("        capital.outMONDAYCAPITAL, \n");
		strSQL.append("        capital.inTUESDAYCAPITAL, \n");
		strSQL.append("        capital.outTUESDAYCAPITAL, \n");
		strSQL.append("        capital.inWEDNESDAYCAPITAL, \n");
		strSQL.append("        capital.outWEDNESDAYCAPITAL, \n");
		strSQL.append("        capital.inTHURSDAYCAPITAL, \n");
		strSQL.append("        capital.outTHURSDAYCAPITAL, \n");
		strSQL.append("        capital.inFRIDAYCAPITAL, \n");
		strSQL.append("        capital.outFRIDAYCAPITAL \n");
		strSQL.append(" from Client,sett_Account, \n");
		strSQL.append("      sett_accounttype, \n");
		strSQL.append("      client_extendattribute ce, \n");
		strSQL.append("      Client_CorporationInfo cc, \n");
		strSQL.append("      ( select sett_subaccount.nAccountID AccountID,sett_dailyaccountbalance.naccountid, \n");
		strSQL.append("      sett_subaccount.ac_ninterestrateplanid,sum(round(sett_subaccount.mBalance,2)) sumAccountBalance, \n");
		strSQL.append("      sum(sett_dailyaccountbalance.mbalance) sumAccountHistoryBalance \n");
		strSQL.append("      from sett_Account,sett_accounttype,sett_subaccount,sett_dailyaccountbalance where sett_subaccount.ID = sett_dailyaccountbalance.nSubAccountid(+) \n");
		strSQL.append("      and sett_Account.Naccounttypeid = sett_accounttype.id \n");
		if(planInfo.getRelationDate() != null)
		{
			strSQL.append("      and sett_dailyaccountbalance.dtdate(+)=to_date('"+ DataFormat.formatDate(UtilOperation.getNextNDay(planInfo.getRelationDate(), -1)) +"','yyyy-mm-dd') \n");
		}
		strSQL.append("		  and sett_Account.Id = sett_subaccount.naccountid \n");
		strSQL.append("		  and sett_Account.nStatusID in (1, 2, 3, 5, 7, 8) \n");
		strSQL.append("		  and sett_accounttype.nstatusid = 1 \n");
		if(planInfo.getOfficeid() > 0)
		{
			strSQL.append("		  and sett_Account.nOfficeID = "+ planInfo.getOfficeid() +" \n");
			strSQL.append("		  and sett_accounttype.officeid = "+ planInfo.getOfficeid() +" \n");
		}
		if(planInfo.getCurrencyid() > 0)
		{
			strSQL.append("		  and sett_Account.nCurrencyID = "+ planInfo.getCurrencyid() +" \n");
			strSQL.append("		  and sett_accounttype.currencyid = "+ planInfo.getCurrencyid() +" \n");
		}
		strSQL.append("	 	  and sett_accounttype.naccountgroupid in (1, 2, 3) \n");
		strSQL.append("		  and sett_accounttype.saccounttypecode <> '"+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"' \n");
		strSQL.append("      group by sett_subaccount.nAccountID,sett_dailyaccountbalance.naccountid,sett_subaccount.ac_ninterestrateplanid \n");
		strSQL.append("      ) AccountBalance , \n");
		strSQL.append(" ( select capitalplan.capclientid, \n");
		strSQL.append("          sum(capitalplan.inMONDAYCAPITAL) inMONDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.outMONDAYCAPITAL) outMONDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.inTUESDAYCAPITAL) inTUESDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.outTUESDAYCAPITAL) outTUESDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.inWEDNESDAYCAPITAL) inWEDNESDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.outWEDNESDAYCAPITAL) outWEDNESDAYCAPITAL, \n");  
		strSQL.append("          sum(capitalplan.inTHURSDAYCAPITAL) inTHURSDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.outTHURSDAYCAPITAL) outTHURSDAYCAPITAL, \n");          
		strSQL.append("          sum(capitalplan.inFRIDAYCAPITAL) inFRIDAYCAPITAL, \n");
		strSQL.append("          sum(capitalplan.outFRIDAYCAPITAL) outFRIDAYCAPITAL \n");
		strSQL.append(" from ( \n");
		strSQL.append(" select subplansum.clientid capclientid, \n");
		strSQL.append("        decode(ocf.id, 1, 1, 7, 2, 0) amounttype,\n");
		strSQL.append("		   decode(ocf.id, 1, subplansum.MONDAYCAPITAL, 0) inMONDAYCAPITAL, decode(ocf.id, 7, subplansum.MONDAYCAPITAL, 0) outMONDAYCAPITAL, \n");
		strSQL.append("		   decode(ocf.id, 1, subplansum.TUESDAYCAPITAL, 0) inTUESDAYCAPITAL, decode(ocf.id, 7, subplansum.TUESDAYCAPITAL, 0) outTUESDAYCAPITAL, \n");
		strSQL.append("        decode(ocf.id, 1, subplansum.WEDNESDAYCAPITAL, 0) inWEDNESDAYCAPITAL, decode(ocf.id, 7, subplansum.WEDNESDAYCAPITAL, 0) outWEDNESDAYCAPITAL, \n");
		strSQL.append("        decode(ocf.id, 1, subplansum.THURSDAYCAPITAL, 0) inTHURSDAYCAPITAL, decode(ocf.id, 7, subplansum.THURSDAYCAPITAL, 0) outTHURSDAYCAPITAL, \n");
		strSQL.append("        decode(ocf.id, 1, subplansum.FRIDAYCAPITAL, 0) inFRIDAYCAPITAL, decode(ocf.id, 7, subplansum.FRIDAYCAPITAL, 0) outFRIDAYCAPITAL \n");
		strSQL.append(" from ob_capitalplanconfig config, \n");
		strSQL.append("	( select subplan.capitalplanconfigid configid, \n");
		strSQL.append(" 		 oc.clientid, \n");
		strSQL.append("			 sum(subplan.total) TOTAL, \n");
		strSQL.append("			 sum(subplan.mondaycapital) MONDAYCAPITAL, \n");
		strSQL.append("			 sum(subplan.tuesdaycapital) TUESDAYCAPITAL, \n");
		strSQL.append("			 sum(subplan.wednesdaycapital) WEDNESDAYCAPITAL, \n");
		strSQL.append("			 sum(subplan.thursdaycapital) THURSDAYCAPITAL, \n");
		strSQL.append("			 sum(subplan.fridaycapital) FRIDAYCAPITAL, \n");
		strSQL.append("			 sum(subplan.nextweekcapital) NEXTWEEKCAPITAL \n");
		strSQL.append(" from ob_subcapitalplan subplan, ob_capitalplan oc \n");
		strSQL.append(" where 1 = 1 \n");
		if(planInfo.getRelationDate() != null)
		{
			strSQL.append("		  and oc.startdate <= to_date('"+ DataFormat.formatDate(planInfo.getRelationDate()) +"','yyyy-mm-dd') \n");
			strSQL.append("		  and oc.enddate >= to_date('"+ DataFormat.formatDate(planInfo.getRelationDate()) +"','yyyy-mm-dd') \n");
		}
	    if(planInfo.getOfficeid() > 0)
	    {
	    	strSQL.append("   and oc.officeid = "+ planInfo.getOfficeid() +"");
	    	strSQL.append("   and subplan.officeid = "+ planInfo.getOfficeid() +"");
	    }
	    if(planInfo.getCurrencyid() > 0)
	    {
	    	strSQL.append("   and oc.currencyid = "+ planInfo.getCurrencyid() +"");
	    	strSQL.append("   and subplan.currencyid = "+ planInfo.getCurrencyid() +"");
	    }
		strSQL.append("		  and oc.statusid =20 \n");
		strSQL.append("		  and subplan.capitalplanid = oc.id \n");
		strSQL.append("		  group by oc.clientid, subplan.capitalplanconfigid \n");
		strSQL.append(" 	  ) subplansum, \n");
		strSQL.append(" ob_capitalplanconfig ocf \n");
		strSQL.append(" where config.id=subplansum.configid(+) \n");
		strSQL.append(" 	  and ocf.id = subplansum.configid \n");
		strSQL.append("		  and (ocf.id = 1 or ocf.id = 7) and ocf.parentid = 0 \n");
		strSQL.append("		  order by clientid \n");
		strSQL.append(" 	  ) capitalplan where 1 = 1 group by capitalplan.capclientid \n");
		strSQL.append("		  ) capital \n");
		strSQL.append(" where Client.ID = sett_Account.nClientID(+) \n");
		strSQL.append("		  and ce.id = cc.clienttypeid1 \n");
		strSQL.append("		  and Accountbalance.AccountID(+) = Sett_Account.ID \n");
		strSQL.append("		  and cc.clientid = Client.id \n");
		strSQL.append("		  and capital.capclientid(+) = Client.id \n");
		strSQL.append("		  and Client.nStatusID = 1 \n");
		strSQL.append("		  and sett_Account.Naccounttypeid = sett_accounttype.id(+) \n");
		strSQL.append(" group by ce.id, \n");
		strSQL.append(" 		 ce.name, \n");
		strSQL.append(" 		 Client.id, \n");
		strSQL.append(" 		 Client.sCode, \n");
		strSQL.append(" 		 Client.sName, \n");
		strSQL.append("          Client.nParentCorpID1, \n");
		strSQL.append("          Client.id, \n");
		strSQL.append("		  	 capital.inMONDAYCAPITAL, \n");
		strSQL.append("		  	 capital.outMONDAYCAPITAL, \n");
		strSQL.append("		  	 capital.inTUESDAYCAPITAL, \n");
		strSQL.append("		  	 capital.outTUESDAYCAPITAL, \n");
		strSQL.append("		  	 capital.inWEDNESDAYCAPITAL, \n");
		strSQL.append("		  	 capital.outWEDNESDAYCAPITAL, \n");
		strSQL.append("		     capital.inTHURSDAYCAPITAL, \n");
		strSQL.append("		  	 capital.outTHURSDAYCAPITAL, \n");
		strSQL.append("		  	 capital.inFRIDAYCAPITAL, \n");
		strSQL.append("		  	 capital.outFRIDAYCAPITAL \n");
		strSQL.append(" ) sumall where 1 = 1 order by extendid \n");
		
		System.out.println(strSQL.toString());
		return strSQL.toString();
	}
	
	/**
	 * @param planInfo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public String PrintPlanOperation(PlanOperationInfo planInfo)throws RemoteException, SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		String strOpinion = planInfo.getOpinion();
		int today = 0;
		double inAmount = 0.0;
		double outAmount = 0.0;
		try
		{
			strSQL = new StringBuffer();
			con = Database.getConnection();
			
			strSQL.append(" select \n");
			strSQL.append("       ocf.id ID, \n");
			strSQL.append("		  ocf.name name, \n");
			strSQL.append("		  ocf.parentid parentid, \n");
			strSQL.append("		  ocf.layerid layerid, \n");
			strSQL.append("		  ocf.code code, \n");
			strSQL.append("		  ocf.MODELID MODELID, \n");
			strSQL.append("		  ocf.OFFICEID OFFICEID, \n");
			strSQL.append("		  ocf.CURRENCYID CURRENCYID, \n");
			strSQL.append("		  ocf.expression expression, \n");
			strSQL.append("       TOTAL, \n");
			strSQL.append("       MONDAYCAPITAL, \n");
			strSQL.append("       TUESDAYCAPITAL, \n");
			strSQL.append("       WEDNESDAYCAPITAL, \n");
			strSQL.append("       THURSDAYCAPITAL, \n");
			strSQL.append("       FRIDAYCAPITAL, \n");
			strSQL.append("       NEXTWEEKCAPITAL \n");
			strSQL.append(" from  ob_capitalplanconfig config, \n");
			strSQL.append("       (select subplan.capitalplanconfigid configid, \n");
			strSQL.append("              sum(subplan.total) TOTAL, \n");
			strSQL.append("              sum(subplan.mondaycapital) MONDAYCAPITAL, \n");
			strSQL.append("              sum(subplan.tuesdaycapital) TUESDAYCAPITAL, \n");
			strSQL.append("              sum(subplan.wednesdaycapital) WEDNESDAYCAPITAL, \n");
			strSQL.append("              sum(subplan.thursdaycapital) THURSDAYCAPITAL, \n");
			strSQL.append("              sum(subplan.fridaycapital) FRIDAYCAPITAL, \n");
			strSQL.append("              sum(subplan.nextweekcapital) NEXTWEEKCAPITAL \n");
			strSQL.append("       from ob_subcapitalplan subplan \n");
			strSQL.append("       where subplan.capitalplanid in ( \n");
			strSQL.append("       select oc.id from ob_capitalplan oc \n");
			strSQL.append("       where 1 = 1 \n");
			if(planInfo.getRelationDate() != null)
			{
				strSQL.append("	  and oc.startdate <= to_date('"+ DataFormat.formatDate(planInfo.getRelationDate()) +"','yyyy-mm-dd') \n");
				strSQL.append("	  and oc.enddate >= to_date('"+ DataFormat.formatDate(planInfo.getRelationDate()) +"','yyyy-mm-dd') \n");
			}
		    if(planInfo.getOfficeid() > 0)
		    {
		    	strSQL.append("   and oc.officeid = "+ planInfo.getOfficeid() +"");
		    }
		    if(planInfo.getCurrencyid() > 0)
		    {
		    	strSQL.append("   and oc.currencyid = "+ planInfo.getCurrencyid() +"");
		    }
			strSQL.append("       and oc.statusid = 20 \n");
			strSQL.append("       ) \n");
			if(planInfo.getOfficeid() > 0)
		    {
		    	strSQL.append("   and subplan.officeid = "+ planInfo.getOfficeid() +"");
		    }
		    if(planInfo.getCurrencyid() > 0)
		    {
		    	strSQL.append("   and subplan.currencyid = "+ planInfo.getCurrencyid() +"");
		    }
			strSQL.append("       group by subplan.capitalplanconfigid \n");
			strSQL.append("       ) subplansum, \n");
			strSQL.append("       ob_capitalplanconfig ocf \n");
			strSQL.append(" where config.id = subplansum.configid(+) \n");
			strSQL.append(" 	  and ocf.id = subplansum.configid \n");
		    strSQL.append(" 	  order by id \n");
		    
		    System.out.println("联系单打印SQL=="+strSQL.toString());
		    
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			//当前是星期几
			today = DataFormat.getWeekDay(planInfo.getRelationDate());
			
			while(rs.next())
			{
				if(rs.getLong("id") == 1)  //流入
				{
					switch(today) {
					case 1:
						inAmount = rs.getDouble("mondaycapital");
						break;
					case 2:
						inAmount = rs.getDouble("tuesdaycapital");
						break;
					case 3:
						inAmount = rs.getDouble("wednesdaycapital");
						break;
					case 4:
						inAmount = rs.getDouble("thursdaycapital");
						break;
					case 5:
						inAmount = rs.getDouble("fridaycapital");
						break;
					default:
						inAmount = 0.0;
					}
				}
				else if(rs.getLong("id") == 7)  //流出
				{
					switch(today) {
					case 1:
						outAmount = rs.getDouble("mondaycapital");
						break;
					case 2:
						outAmount = rs.getDouble("tuesdaycapital");
						break;
					case 3:
						outAmount = rs.getDouble("wednesdaycapital");
						break;
					case 4:
						outAmount = rs.getDouble("thursdaycapital");
						break;
					case 5:
						outAmount = rs.getDouble("fridaycapital");
						break;
					default:
						outAmount = 0.0;
					}
				}
			}
			
			System.out.println("流入=="+inAmount);
			System.out.println("流出=="+outAmount);
			
			strOpinion = strOpinion.replaceAll("ZJLR", DataFormat.formatDisabledAmount(inAmount, 2));
			strOpinion = strOpinion.replaceAll("ZJLC", DataFormat.formatDisabledAmount(outAmount, 2));
		}
		catch(SQLException re) {
			throw new RemoteException(re.getMessage());
		}
		catch(Exception re) {
			throw new RemoteException(re.getMessage());
		}
		finally{
			if(con != null) {
				con.close();
				con = null;
			}
			if(ps != null) {
				ps.close();
				ps = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		}
		return strOpinion;
	}
	
}