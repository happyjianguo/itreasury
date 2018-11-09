package com.iss.itreasury.ebank.obquery.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo;
import com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo;
import com.iss.itreasury.util.IException;

/**
 * 当日余额查询：包括汇总当日余额查询和下属单位当日余额查询 写具体的查询语句，返回包含OBTodayBalanceResultInfo对象的集合
 * 
 * @author liuyang
 *  
 */
public class OBTodayBalanceDAO extends ITreasuryDAO {

	public OBTodayBalanceDAO() {
		super("");
	}

	/**
	 * 汇总账户余额查询-当日余额查询 银行接口 bs_acctcurbalance 信托贷款 transtrustgrantloan 委托贷款
	 * transconsigngrantloan 定期 transfixeddeposit
	 * 备注：得到的记录如果不符合显示的要求，可以在另外一个类里做记录处理，得到最终想要的结果集
	 * 
	 * @param info
	 * @return @throws
	 *         IException
	 */
	public OBTodayBalanceResultInfo[] QueryTodayBalance(OBQueryAccInfo info)
			throws IException {

		OBTodayBalanceResultInfo[] results = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSql = null;
		StringBuffer sbSql_u = null;
		StringBuffer sbParam = null;
		StringBuffer sband = null;
		Vector vector = new Vector();
		Timestamp today = null;

		sbSql = new StringBuffer();
		sband = new StringBuffer();
		sbSql_u = new StringBuffer();
		sbParam = new StringBuffer();

		try {
			/** ---查询条件sbParam拼写---* */
			if (info.getClientid() > 0) {
				sbParam.append(" and v.clientID=" + info.getClientid() + "\n");
			}
			if (info.getAreaCenter() != null
					&& info.getAreaCenter().length() > 0) {
				sbParam.append(" and v.areaID in(" + info.getAreaCenter()
						+ ")\n");
			}
			if (info.getBankId() != null && info.getBankId().length() > 0) {
				sbParam.append(" and v.Bankid in(" + info.getBankId() + ")\n");
			}
			if (info.getCurrencyId() != null
					&& info.getCurrencyId().length() > 0) {
				sbParam.append(" and v.currencyID in(" + info.getCurrencyId()
						+ ")\n");
			}
			/** ---查询sql_u拼写---* */
			/*
			 * sbSql_u.append(" union all \n"); sbSql_u.append(" SELECT --定期贷款查询
			 * \n");
			 * 
			 * sbSql_u.append(" a.naccountgroupid as accountgroupid, --账户组类型id为1
			 * \n"); sbSql_u.append(" v.AccountType as accounttype, --账户类型id
			 * \n"); sbSql_u.append(" v.areaID as areaid, --区域中心id \n");
			 * sbSql_u.append(" v.AreaName as AreaName, --区域中心名称 \n");
			 * sbSql_u.append(" v.currencyID as currencyID, --币种id \n");
			 * sbSql_u.append(" v.Bankid as Bankid, --银行id \n");
			 * sbSql_u.append(" v.bankName as bankName, --银行名称 \n");
			 * //sbSql_u.append(" v.countryID as countryID, --国家id \n"); //
			 * sbSql_u.append(" v.countryName as countryName, --国家名称 \n");
			 * sbSql_u.append(" v.accountID as accountID, --账户id \n");
			 * sbSql_u.append(" v.accountNo as accountNo, --账号 \n");
			 * sbSql_u.append(" v.billNO as billNO, --单据号 \n"); sbSql_u.append("
			 * v.StartDate as StartDate, --存入日期 \n"); sbSql_u.append(" v.EndDate
			 * as EndDate, --到期日期 \n"); sbSql_u.append(" v.Term as Term, --期限
			 * \n"); sbSql_u.append(" v.CurrenBalance as CurrenBalance, --当日余额
			 * \n"); sbSql_u.append(" v.Rate as Rate, --利率 \n");
			 * sbSql_u.append(" v.clientID as clientID, --客户id \n");
			 * sbSql_u.append(" c.sname as clientName --客户名称 \n");
			 * 
			 * sbSql_u.append(" FROM \n"); sbSql_u.append(" OB_CURBALANCEVIEW
			 * v,accounttype a,client c \n");
			 * 
			 * sbSql_u.append(" where \n"); sbSql_u.append(" a.id=v.AccountType
			 * \n"); sbSql_u.append(" and c.id=v.clientID \n"); sbSql_u.append("
			 * and v.AccountID in(select t.naccountid from ob_accountownedbyuser
			 * t,accounttype a ,accountinfo b where b.ncurrencyid <>1 and
			 * t.naccountid=b.id and b.naccounttypeid=a.id and a.naccountgroupid
			 * in
			 * ("+SETTConstant.AccountGroupType.FIXED+","+SETTConstant.AccountGroupType.OTHERLOAN+")
			 * and t.nuserid="+info.getUserid()+" and
			 * t.naccounttypeid="+OBConstant.AccountType.IN+" union select -1
			 * from dual)\n"); sbSql_u.append(sband.toString());
			 * sbSql_u.append(sbParam.toString());
			 *  
			 *//** ---结束sql_u拼写---* */
			/*
			 * 
			 * 
			 *  
			 *//** ---查询sql拼写---* */
			/**/
			sbSql.append(" SELECT  \n");

			sbSql.append(" 	a.naccountgroupid as accountgroupid,   --账户组类型id \n");
			sbSql.append(" 	v.AccountType as accounttype,   --账户类型id \n");
			sbSql.append(" 	v.areaID as areaid,   --区域中心id \n");
			sbSql.append("  v.AreaName as AreaName,   --区域中心名称 \n");
			sbSql.append(" 	v.currencyID as currencyID,   --币种id \n");
			sbSql.append(" 	v.Bankid as Bankid,   --银行id \n");
			sbSql.append(" 	v.bankName as bankName,  --银行名称 \n");
			//sbSql.append(" v.countryID as countryID, --国家id \n");
			// sbSql.append(" v.countryName as countryName, --国家名称 \n");
			sbSql.append(" 	v.accountID as accountID,  --账户id  \n");
			sbSql.append(" 	v.accountNo as accountNo,  --账号  \n");
			sbSql.append(" 	  '' as billNO,  		   --单据号  \n");
			sbSql.append(" 	v.CurrenBalance as CurrenBalance,  --当日余额  \n");
			sbSql.append(" 	v.clientID as clientID,  --客户id  \n");
			sbSql.append(" 	c.sname as clientName  --客户名称  \n");

			sbSql.append(" FROM  \n");
			sbSql.append(" OB_CURBALANCEVIEW v ,sett_accounttype a,client c  \n");

			sbSql
					.append(" where a.id = v.accounttype and a.nstatusId=1 and a.officeId="+info.getOfficeId()+" and a.currencyId="+info.getCurrencyId()+" and c.id = v.clientID and v.AccountID in(select t.naccountid from ob_accountownedbyuser t where t.nuserid="
							+info.getUserid()
							+" union select -1 from dual) \n");
			sbSql.append(sbParam.toString());

			sbSql
					.append("  order by accountgroupid,areaid,Bankid,accounttype,accountID,billno");//改动顺序会影响前台显示
			/** ---结束sql拼写---* */
			Log.print("sql--" + sbSql.toString());
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				OBTodayBalanceResultInfo rinfo = new OBTodayBalanceResultInfo();
				rinfo.setAccountgroupid(rs.getLong("accountgroupid")); //账户组类型id
				rinfo.setAccounttype(rs.getLong("accounttype")); //账户类型id
				rinfo.setAreaid(rs.getLong("areaid"));//区域中心id
				rinfo.setAreaName(rs.getString("AreaName"));//区域中心名称
				rinfo.setCurrencyID(rs.getLong("currencyID"));//币种id
				rinfo.setBankid(rs.getLong("Bankid")); //银行id
				rinfo.setBankName(rs.getString("bankName")); //银行名称
				rinfo.setAccountID(rs.getLong("accountID")); //账户id
				rinfo.setAccountNo(rs.getString("accountNo"));//账号
				rinfo.setCurrenBalance(rs.getDouble("CurrenBalance"));//当日余额
				rinfo.setBillNO(rs.getString("billNO"));//单据号
				//rinfo.setTerm(rs.getLong("Term"));//期限
				//rinfo.setStartDate(rs.getTimestamp("StartDate"));//存入日期
				//rinfo.setEndDate(rs.getTimestamp("EndDate"));//到期日期
				//rinfo.setRate(rs.getDouble("Rate"));// 利率
				rinfo.setClientID(rs.getLong("clientID"));//客户id
				rinfo.setClientName(rs.getString("clientName"));//客户名称
				if (rinfo.getAccountgroupid() == SETTConstant.AccountGroupType.FIXED) {
					today = OBOperation.getClientOpenDate(rinfo.getClientID(),
							rinfo.getCurrencyID());
					if (rinfo.getEndDate() != null
							&& rinfo.getEndDate().toString().trim().length() > 0) {
						if (today.before(rinfo.getEndDate())) {
							rinfo.setMark(OBConstant.Loanremark.NO);
						} else {
							rinfo.setMark(OBConstant.Loanremark.YES);
						}
					} else {
						rinfo.setMark(-1);
					}
				}
				vector.add(rinfo);
			}

			if (vector.size() > 0) {
				results = new OBTodayBalanceResultInfo[0];
				results = (OBTodayBalanceResultInfo[]) vector.toArray(results);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new IException("Gen_E001");
			}
		}
		return results;
	}

	/**
	 * 下属单位账户信息查询-汇总账户当日余额查询 银行接口 bs_acctcurbalance 信托贷款 transtrustgrantloan
	 * 委托贷款 transconsigngrantloan 定期 transfixeddeposit
	 * 备注：得到的记录如果不符合显示的要求，可以在另外一个类里做记录处理，得到最终想要的结果集
	 * 
	 * @param info
	 * @return
	 */
	/*
	 * public OBTodayBalanceResultInfo[] QueryClientTodayBal(OBQueryAccInfo
	 * info)throws IException { OBTodayBalanceResultInfo[] results = null;
	 * Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
	 * StringBuffer sbSql = null; StringBuffer sbSql_u = null; StringBuffer
	 * sbParam = null; StringBuffer sband = null; Vector vector = new Vector();
	 * Timestamp today = null;
	 * 
	 * sbSql = new StringBuffer(); sband = new StringBuffer(); sbSql_u = new
	 * StringBuffer(); sbParam = new StringBuffer(); String tmp = null;
	 * 
	 * 
	 * try {
	 *//** ---查询条件sbParam拼写---* */
	/*
	 * 
	 * 
	 * 
	 * if(info.getUnderClient() != null && info.getUnderClient().trim().length() >
	 * 0) { tmp = info.getUnderClient(); Log.print("tmp--------"+tmp); } else {
	 * tmp = OBOperation.getChildClientID(info.getClientid());
	 * System.out.println("tmp-------"+tmp); }
	 * 
	 * if(info.getIncludeSelf() == Constant.YesOrNo.YES && tmp != null &&
	 * tmp.length() > 0) { System.out.println("查询包括用户所在企业在内的整体的数据");
	 * 
	 * sbParam.append(" and ( \n"); sbParam.append(" v.clientID =
	 * "+info.getClientid()+" Or v.clientID IN \n"); sbParam.append(" ( " + tmp
	 * +") \n"); sbParam.append(" ) \n");
	 *  } else if(tmp != null && tmp.length()>0) {
	 * System.out.println("只查询下属单位的，不包含用户所在企业");
	 * 
	 * sbParam.append(" and v.clientID IN ( \n"); sbParam.append(" \n " + tmp );
	 * sbParam.append(" )\n"); }
	 * 
	 * if(info.getAreaCenter() != null && info.getAreaCenter().length()>0) {
	 * sbParam.append(" and v.areaID in("+ info.getAreaCenter() +") \n"); }
	 * if(info.getBankId() != null && info.getBankId().length()>0) {
	 * sbParam.append(" and v.Bankid in("+ info.getBankId() +") \n"); }
	 * if(info.getCurrencyId() != null && info.getCurrencyId().length()>0) {
	 * sbParam.append(" and v.currencyID in("+ info.getCurrencyId() +") \n"); }
	 *  
	 *//** ---查询sql_u拼写---* */
	/*
	 * sbSql_u.append(" union all \n"); sbSql_u.append(" SELECT --定期贷款查询 \n");
	 * 
	 * sbSql_u.append(" a.naccountgroupid as accountgroupid, --账户组类型id为1 \n");
	 * sbSql_u.append(" v.AccountType as accounttype, --账户类型id \n");
	 * sbSql_u.append(" v.areaID as areaid, --区域中心id \n"); sbSql_u.append("
	 * v.AreaName as AreaName, --区域中心名称 \n"); sbSql_u.append(" v.currencyID as
	 * currencyID, --币种id \n"); sbSql_u.append(" v.Bankid as Bankid, --银行id
	 * \n"); sbSql_u.append(" v.bankName as bankName, --银行名称 \n");
	 * //sbSql_u.append(" v.countryID as countryID, --国家id \n"); //
	 * sbSql_u.append(" v.countryName as countryName, --国家名称 \n");
	 * sbSql_u.append(" v.accountID as accountID, --账户id \n"); sbSql_u.append("
	 * v.accountNo as accountNo, --账号 \n"); sbSql_u.append(" v.billNO as
	 * billNO, --单据号 \n"); sbSql_u.append(" v.StartDate as StartDate, --存入日期
	 * \n"); sbSql_u.append(" v.EndDate as EndDate, --到期日期 \n");
	 * sbSql_u.append(" v.Term as Term, --期限 \n"); sbSql_u.append("
	 * v.CurrenBalance as CurrenBalance, --当日余额 \n"); sbSql_u.append(" v.Rate as
	 * Rate, --利率 \n"); sbSql_u.append(" v.clientID as clientID, --客户id \n");
	 * sbSql_u.append(" c.sname as clientName --客户名称 \n");
	 * 
	 * sbSql_u.append(" FROM \n"); sbSql_u.append(" ob_fixedloanbalanceview
	 * v,accounttype a,client c \n");
	 * 
	 * sbSql_u.append(" where \n"); sbSql_u.append(" a.id=v.AccountType \n");
	 * sbSql_u.append(" and c.id=v.clientID \n"); sbSql_u.append(" and
	 * v.AccountID in(select t.naccountid from ob_accountownedbyuser
	 * t,accounttype a ,accountinfo b where b.ncurrencyid <>1 and
	 * t.naccountid=b.id and b.naccounttypeid=a.id and a.naccountgroupid
	 * in("+SETTConstant.AccountGroupType.FIXED+","+Notes.CODE_ACCOUNTTYPE_ACCOUNTGROUP_LOAN+")
	 * and t.nuserid="+info.getUserid()+" and
	 * t.naccounttypeid="+OBConstant.AccountType.IN+") \n");
	 * sbSql_u.append(sbParam.toString());
	 *  
	 *//** ---结束sql_u拼写---* */
	/*
	 * 
	 * 
	 *  
	 *//** ---查询sql拼写---* */
	/*
	 * sbSql.append(" SELECT --银行和活期组 \n");
	 * 
	 * sbSql.append(" 1 as accountgroupid, --账户组类型id \n"); sbSql.append("
	 * v.AccountType as accounttype, --账户类型id \n"); sbSql.append(" v.areaID as
	 * areaid, --区域中心id \n"); sbSql.append(" v.AreaName as AreaName, --区域中心名称
	 * \n"); sbSql.append(" v.currencyID as currencyID, --币种id \n");
	 * sbSql.append(" v.Bankid as Bankid, --银行id \n"); sbSql.append(" v.bankName
	 * as bankName, --银行名称 \n"); //sbSql.append(" v.countryID as countryID,
	 * --国家id \n"); // sbSql.append(" v.countryName as countryName, --国家名称 \n");
	 * sbSql.append(" v.accountID as accountID, --账户id \n"); sbSql.append("
	 * v.accountNo as accountNo, --账号 \n"); sbSql.append(" '' as billNO, --单据号
	 * \n"); sbSql.append(" to_date('3000-01-01','yyyy-mm-dd') as StartDate,
	 * --存入日期 \n"); sbSql.append(" to_date('3000-01-01','yyyy-mm-dd') as
	 * EndDate, --到期日期 \n"); sbSql.append(" -1 as Term, --期限 \n");
	 * sbSql.append(" v.CurrenBalance as CurrenBalance, --当日余额 \n");
	 * sbSql.append(" 0.0 as Rate, --利率 \n"); sbSql.append(" v.clientID as
	 * clientID, --客户id \n"); sbSql.append(" c.sname as clientName --客户名称 \n");
	 * 
	 * sbSql.append(" FROM \n"); sbSql.append(" ob_curbalanceview v,client c
	 * \n");
	 * 
	 * sbSql.append(" where \n"); sbSql.append(" c.id=v.clientID \n");
	 * sbSql.append(" and v.AccountID in(select t.naccountid from
	 * ob_accountownedbyuser t where t.nuserid="+info.getUserid()+" and
	 * t.naccounttypeid="+OBConstant.AccountType.OUT+" union select t.naccountid
	 * from ob_accountownedbyuser t,accounttype a ,accountinfo b where
	 * b.ncurrencyid <>1 and t.naccountid=b.id and b.naccounttypeid=a.id and
	 * a.naccountgroupid="+SETTConstant.AccountGroupType.CURRENT+" and
	 * t.nuserid="+info.getUserid()+" and
	 * t.naccounttypeid="+OBConstant.AccountType.IN+" union select -1 from dual)
	 * \n");
	 * 
	 * sbSql.append(sbParam.toString());
	 * 
	 * if(info.getAreaCenter().trim().length()==0) {
	 * if(info.getBankId().indexOf("-9")!=-1 ||
	 * info.getBankId().trim().length()==0) {//根据查询条件 拼写查询定期\贷款的sql
	 * 
	 * sbSql.append(sbSql_u.toString()); } }
	 * 
	 * sbSql.append(" order by
	 * accountgroupid,clientID,areaid,Bankid,accounttype,accountID,billno");//改动顺序会影响前台显示
	 *//** ---结束sql拼写---* */
	/*
	 * Log.print("sql--"+sbSql.toString()); conn = Database.getConnection(); ps =
	 * conn.prepareStatement(sbSql.toString()); rs = ps.executeQuery();
	 * 
	 * while (rs.next()) { OBTodayBalanceResultInfo rinfo = new
	 * OBTodayBalanceResultInfo();
	 * rinfo.setAccountgroupid(rs.getLong("accountgroupid")); //账户组类型id
	 * rinfo.setAccounttype(rs.getLong("accounttype")); //账户类型id
	 * rinfo.setAreaid(rs.getLong("areaid"));//区域中心id
	 * rinfo.setAreaName(rs.getString("AreaName"));//区域中心名称
	 * rinfo.setCurrencyID(rs.getLong("currencyID"));//币种id
	 * rinfo.setBankid(rs.getLong("Bankid")); //银行id
	 * rinfo.setBankName(rs.getString("bankName")); //银行名称
	 * rinfo.setAccountID(rs.getLong("accountID")); //账户id
	 * rinfo.setAccountNo(rs.getString("accountNo"));//账号
	 * rinfo.setCurrenBalance(rs.getDouble("CurrenBalance"));//当日余额
	 * rinfo.setBillNO(rs.getString("billNO"));//单据号
	 * rinfo.setTerm(rs.getLong("Term"));//期限
	 * rinfo.setStartDate(rs.getTimestamp("StartDate"));//存入日期
	 * rinfo.setEndDate(rs.getTimestamp("EndDate"));//到期日期
	 * rinfo.setRate(rs.getDouble("Rate"));// 利率
	 * rinfo.setClientID(rs.getLong("clientID"));//客户id
	 * rinfo.setClientName(rs.getString("clientName"));//客户名称
	 * if(rinfo.getAccountgroupid()==SETTConstant.AccountGroupType.FIXED) {
	 * today =
	 * OBOperation.getClientOpenDate(rinfo.getClientID(),rinfo.getCurrencyID());
	 * if(rinfo.getEndDate()!=null &&
	 * rinfo.getEndDate().toString().trim().length()>0) {
	 * if(today.before(rinfo.getEndDate())) {
	 * rinfo.setMark(OBConstant.Loanremark.NO); } else {
	 * rinfo.setMark(OBConstant.Loanremark.YES); } } else { rinfo.setMark(-1); } }
	 * 
	 * vector.add(rinfo); }
	 * 
	 * if (vector.size() > 0) { results = new OBTodayBalanceResultInfo[0];
	 * results = (OBTodayBalanceResultInfo[]) vector.toArray( results); }
	 * 
	 * rs.close(); rs = null; ps.close(); ps = null; conn.close(); conn = null;
	 * 
	 *  } catch(Exception ex){ ex.printStackTrace(); throw new
	 * IException("Gen_E001") ; } finally { try { if (rs != null) rs.close(); if
	 * (ps != null) ps.close(); if (conn != null) conn.close(); } catch
	 * (Exception ex) { ex.printStackTrace(); throw new IException("Gen_E001") ; } }
	 * return results; }
	 */
}