package com.iss.itreasury.loan.creditext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotResultInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtSplitInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtSplitResultInfo;
import com.iss.itreasury.util.Database;

/**
* 银行授信分解与调整DAO
* @author mayongming
* @version 1.0
*/
public class BankCreditExtSplitDAO extends LoanDAO
{
	public BankCreditExtSplitDAO()
	{
		super("loan_bank_creditext_split");
	}	
	//通过授信年度，授信合同号查找分配表里的授信合同号id，为下一步查询提供条件
	public long getContractId (String year,String contractNo,long officeId) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" select distinct a.id id ");
			strSQL.append(" from loan_bank_creditext a , loan_bank_creditext_allot b ");
			strSQL.append(" where a.nisvalid = 1 ");
			strSQL.append(" and b.nisvalid = 1 ");
			strSQL.append(" and a.id = b.nbankcreditextid ");
			strSQL.append(" and b.nofficeid = ? ");//--结算中心id
			strSQL.append(" and a.syear = ? ");//--授信年度
			strSQL.append(" and a.scontractno = ? ");//--授信合同号
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,officeId );
			ps.setString( nIndex++ , year );
			ps.setString( nIndex++ , contractNo );			
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				lret = rs.getLong("id");
			}
		
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	//	 通过授信合同号id和结算中心代码查询该授信已经分解到各结算中心的情况
	public Vector getBankCreditAllot( long id, long officeId) throws Exception 
	{
		Vector vret = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		BankCreditExtSplitResultInfo info = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			strSQL.append(" select d.id id ,contractno,bankname,  officeid , d.companycode companycode, e.sname Companyname ,");
			strSQL.append(" sum(amount1) amount1, sum(currencytype1) currencytype1,sum(exchangerate1) exchangerate1,");
			strSQL.append(" sum(amount2) amount2,sum(currencytype2) currencytype2,sum(exchangerate2) exchangerate2,");
			strSQL.append(" sum(amount3) amount3,sum(currencytype3) currencytype3,sum(exchangerate3) exchangerate3,");
			strSQL.append(" sum(amount4) amount4,sum(currencytype4) currencytype4,sum(exchangerate4) exchangerate4,");
			strSQL.append(" sum(amount5) amount5,sum(currencytype5) currencytype5,sum(exchangerate5) exchangerate5,");
			strSQL.append(" sum(amount6) amount6,sum(currencytype6) currencytype6,sum(exchangerate6) exchangerate6,");
			strSQL.append(" sum(amount7) amount7,sum(currencytype7) currencytype7,sum(exchangerate7) exchangerate7 ");
			strSQL.append(" from (");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid,b.scompanycode companycode, ");
			strSQL.append(" b.mamount amount1, c.ncurrencytype currencytype1, c.nexchangerate exchangerate1, ");
			strSQL.append(" 0 amount2,0 currencytype2, 0 exchangerate2, 0 amount3,0 currencytype3, 0 exchangerate3,");
			strSQL.append(" 0 amount4,0 currencytype4, 0 exchangerate4, 0 amount5,0 currencytype5, 0 exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_mix c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=0");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid,b.scompanycode companycode, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, b.mamount amount2, c.ncurrencytype currencytype2, c.nexchangerate exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0 currencytype6, 0 exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=1 and c.nvariety=1 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid,b.scompanycode companycode,");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, 0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" b.mamount amount3, c.ncurrencytype currencytype3, c.nexchangerate exchangerate3,");
			strSQL.append(" 0 amount4,0 currencytype4, 0 exchangerate4, 0 amount5,0 currencytype5, 0 exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=2 and c.nvariety=2 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, b.scompanycode companycode,");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3, b.mamount amount4, c.ncurrencytype currencytype4, c.nexchangerate exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0 currencytype6, 0 exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7 ");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=3 and c.nvariety=3 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid,b.scompanycode companycode, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, 0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3, 0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" b.mamount amount5, c.ncurrencytype currencytype5, c.nexchangerate exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=4 and c.nvariety=4 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid,b.scompanycode companycode, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,b.mamount amount6, c.ncurrencytype currencytype6, c.nexchangerate exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=5 and c.nvariety=5 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, b.scompanycode companycode,");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0  currencytype6, 0 exchangerate6,");
			strSQL.append(" b.mamount amount7,c.ncurrencytype currencytype7, c.nexchangerate exchangerate7 ");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_split b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=6 and c.nvariety=6 ");
			strSQL.append(" and a.id=? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" ) d,client e");
			strSQL.append(" where d.companycode = e.scode");
			strSQL.append(" and d.officeid = ? ");
			strSQL.append(" and e.nstatusid = 1 ");
			strSQL.append(" group by d.id ,contractno,bankname,e.sname, d.officeid,d.companycode ");
			strSQL.append(" order by d.companycode ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );;
			ps.setLong( nIndex++ , id );	
			ps.setLong( nIndex++ , officeId );
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				info = new BankCreditExtSplitResultInfo();
				info.setId(rs.getLong("id"));//授信合同id号
				info.setContractNo(rs.getString("contractno"));//授信合同号
								
				info.setCompanyCode(rs.getString("companycode"));//单位编号
				info.setCompanyName(rs.getString("Companyname"));//单位名称
				info.setBankName(rs.getString("bankname"));//授信银行
				
				info.setCurrencyType1(rs.getLong("currencytype1"));//混用授信币种
				info.setAmout1(rs.getDouble("amount1"));//混用授信额度
				info.setExchangeRate1(rs.getDouble("exchangerate1"));//混用授信汇率
				
				info.setCurrencyType2(rs.getLong("currencytype2"));//短期贷款币种
				info.setAmout2(rs.getDouble("amount2"));//短期贷款金额
				info.setExchangeRate2(rs.getDouble("exchangerate2"));//短期贷款汇率
				
				info.setCurrencyType3(rs.getLong("currencytype3"));//中长期贷款币种
				info.setAmout3(rs.getDouble("amount3"));//中长期贷款金额
				info.setExchangeRate3(rs.getDouble("exchangerate3"));//中长期贷款汇率
				
				info.setCurrencyType4(rs.getLong("currencytype4"));//信用证币种
				info.setAmout4(rs.getDouble("amount4"));//信用证金额
				info.setExchangeRate4(rs.getDouble("exchangerate4"));//信用证汇率
				
				info.setCurrencyType5(rs.getLong("currencytype5"));//保函币种
				info.setAmout5(rs.getDouble("amount5"));//保函金额
				info.setExchangeRate5(rs.getDouble("exchangerate5"));//保函汇率
				
				info.setCurrencyType6(rs.getLong("currencytype6"));//信贷证明币种
				info.setAmout6(rs.getDouble("amount6"));//信贷证明金额
				info.setExchangeRate6(rs.getDouble("exchangerate6"));//信贷证明汇率
				
				info.setCurrencyType7(rs.getLong("currencytype7"));//承兑汇票币种
				info.setAmout7(rs.getDouble("amount7"));//承兑汇票金额
				info.setExchangeRate7(rs.getDouble("exchangerate7"));//承兑汇票汇率
				
				vret.add(info);
			}
			
	
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return vret.size()>0 ? vret : null;
	}
	
	//	 通过银行授信ID、授信品种和成员单位代码查询授信分配信息
	//  点击金额进入详细信息
	public BankCreditExtSplitInfo getBankCreditSplitInfo(long id, long variety, String companyCode, String companyName , long officeId) throws Exception 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		BankCreditExtSplitInfo info = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT a.id id, a.scontractno contractNo,a.sbankname bankname,a.syear year, a.dstartdate startdate,");
			strSQL.append(" a.denddate enddate,f.nvariety variety,c.currencytype currencytype, ");
			strSQL.append(" f.mamount amount, f.sremark remark  , b.mamount-e.amount1+f.mamount balance");
			strSQL.append(" FROM loan_bank_creditext a, loan_bank_creditext_allot b,loan_bank_creditext_split f,");
			strSQL.append(" ( ");
			strSQL.append(" SELECT a.nvariety variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_list a ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" union ");
			strSQL.append(" SELECT 0 variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_mix a ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" ) c, ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT sum(d.mamount) amount1 ");
			strSQL.append(" FROM loan_bank_creditext_split d ");
			strSQL.append(" WHERE d.nbankcreditextid = ? ");
			strSQL.append(" AND d.nisvalid = 1 ");
			strSQL.append(" AND d.nvariety = ? ");
			strSQL.append(" AND d.nofficeid = ? ");
			strSQL.append(" ) e");
			strSQL.append(" WHERE  f.scompanycode = ? ");// --单位编号 
			strSQL.append(" AND a.id = f.nbankcreditextid ");
			strSQL.append(" AND f.nbankcreditextid = b.nbankcreditextid ");
			strSQL.append(" AND f.nbankcreditextid = ? ");// --授信合同id 
			strSQL.append(" AND f.nofficeid = b.nofficeid ");
			strSQL.append(" AND c.variety = f.nvariety ");
			strSQL.append(" AND f.nvariety = ? ");//  --授信品种id 
			strSQL.append(" AND f.nvariety = b.nvariety");
			strSQL.append(" AND f.nisvalid = 1 ");
			strSQL.append(" AND b.nisvalid = 1 ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,variety );
			ps.setLong( nIndex++ , officeId);
			ps.setString( nIndex++ ,companyCode );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,variety );
			
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = new BankCreditExtSplitInfo();
				
				info.setId(rs.getLong("id"));//id
				info.setContractNo(rs.getString("contractNo"));//授信合同号
				info.setBankName(rs.getString("bankname"));//授信银行
				info.setYear(rs.getString("year"));//授信年度
				if (rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());//起始日期
				}
				if (rs.getDate("enddate")!=null)
				{
					info.setEndDate(rs.getDate("enddate").toString());//结束日期
				}
				info.setCompanyCode(companyCode);//单位编号
				info.setCompanyName(companyName);//单位名称
				info.setVariety(rs.getLong("variety"));//授信品种
				info.setBalance(rs.getDouble("balance"));//授信余额
				info.setCurrencyType(rs.getLong("currencytype"));//币种
				info.setAmount(rs.getDouble("amount"));//	金额
				info.setRemark(rs.getString("remark"));//备注
			}
		
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return info;
	}
	
	//	删除授信分解记录
	public boolean delete(long contractNoid, long variety, String companyCode ,long lastModifier, String lastModifyDate) throws Exception
	{
		boolean lret = false;
		long reLine = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE loan_bank_creditext_split t ");
			strSQL.append(" SET t.nisvalid = 0 ,");//--设置状态位为无效状态
			strSQL.append(" t.slastmodifier = ? ,");//--最后修改人id
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ");//--最后修改日期
			strSQL.append(" WHERE t.nbankcreditextid = ? ");//--授信合同号
			strSQL.append(" AND t.nvariety = ? ");//--授信品种
			strSQL.append(" AND t.scompanycode = ? ");//-- 单位编号
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , lastModifier);	
			ps.setString( nIndex++ , lastModifyDate );
			ps.setLong( nIndex++ , contractNoid);
			ps.setLong( nIndex++ , variety);
			ps.setString( nIndex++ , companyCode );
			
			reLine = ps.executeUpdate();
			if( reLine>0 )
			{
				lret = true;
			}

			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{			
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	
	
	//得到某具体品种的风险系数
	public double getRiskcoefficient(long contractNoid, long variety ) throws Exception
	{
		double lret = 0;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT t.nriskcoefficient  riskcoefficient ");
			strSQL.append(" FROM loan_bank_creditext_list t ");
			strSQL.append(" WHERE  t.nbankcreditextid = ? ");// --授信合同id
			strSQL.append(" AND t.nvariety = ? ");//--授信品种id
		
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,contractNoid );
			ps.setLong( nIndex++ , variety );
		
			rs = ps.executeQuery();
			if(rs.next())
			{
				lret = rs.getDouble("riskcoefficient");				
			}
		
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	
	//新增银行授信分解记录，判断是否已经分解以及是否超过余额
   //	 添加成功返回true,失败返回false
	public boolean insert(BankCreditExtSplitInfo info) throws Exception 
	{
		boolean lret = false;
		long reLine = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			strSQL.append(" INSERT INTO loan_bank_creditext_split t VALUES ( ?,?,?,?,?,1,?,to_date(?,'yyyy-mm-dd'),? ) ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , info.getId());//授信合同号id
			ps.setLong( nIndex++ , info.getOfficeId());//结算中心id
			ps.setString( nIndex++ , info.getCompanyCode());//成员单位编号
			ps.setLong( nIndex++ , info.getVariety()); //授信品种
			ps.setDouble( nIndex++ , info.getAmount());//金额
			ps.setLong( nIndex++ ,info.getLastModifier());//最后修改人
			ps.setString ( nIndex++ , info.getLastModifyDate());//最后修改时间
			ps.setString( nIndex++ ,info.getRemark());//备注
						
			reLine = ps.executeUpdate();
			if( reLine>0 )
			{
				lret = true;
			}

			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{			
			cleanup(ps);
			cleanup(con);
		}	
		
		return lret;
	}
	
	//通过授信合同号得到授信信息
	//v002，点击新增，进入新增页面，显示显示信息的查询
	public BankCreditExtSplitInfo getBankCreditInfo(long contractNoid) throws Exception 
	{
		BankCreditExtSplitInfo info = null ;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT a.scontractno contractno , ");// -- 授信合同号
			strSQL.append(" a.sbankname bankname ,");//  --授信银行
			strSQL.append(" a.syear year,");//--授信年度
			strSQL.append(" a.dstartdate startdate ,");//--起始日期
			strSQL.append(" a.denddate enddate");//-- 结束日期
			strSQL.append(" FROM loan_bank_creditext a");
			strSQL.append(" WHERE a.id = ? ");
			strSQL.append(" AND a.nisvalid = 1 ");//有效状态

			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,contractNoid );
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = new BankCreditExtSplitInfo();
				
				info.setId(contractNoid);
				info.setContractNo(rs.getString("contractno"));
				info.setBankName(rs.getString("bankname"));
				info.setYear(rs.getString("year"));
				if (rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());
				}
				if (rs.getDate("enddate")!=null)
				{
					info.setEndDate(rs.getDate("enddate").toString());
				}
			}
			
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return info;
	}
	
//	计算所有品种的授信现有余额，为新增时各个授信品种的选择提供信息
	public HashMap getAllBalance (long contractNoid ,long officeId ) throws Exception
	{

		HashMap lret = new HashMap();
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT b.nvariety variety , nvl( b.mamount-e.amount1, b.mamount ) balance ");
			strSQL.append(" FROM   loan_bank_creditext_allot b, ");
			strSQL.append("  (  ");
			strSQL.append(" SELECT sum(d.mamount) amount1 ,d.nvariety nvariety ,d.nbankcreditextid nbankcreditextid ");
			strSQL.append(" FROM loan_bank_creditext_split d ");
			strSQL.append(" WHERE d.nbankcreditextid = ? ");
			strSQL.append(" AND d.nisvalid = 1 ");
			strSQL.append(" AND d.nofficeid = ? ");
			strSQL.append(" group by d.nvariety,d.nbankcreditextid ");
			strSQL.append(" ) e ");
			strSQL.append(" WHERE b.nisvalid = 1 ");
			strSQL.append(" AND b.nvariety  =  e.nvariety(+)");
			strSQL.append(" AND b.nbankcreditextid = e.nbankcreditextid(+)");
			strSQL.append(" AND b.nofficeid = ? ");
			strSQL.append(" AND b.nbankcreditextid = ? ");
			strSQL.append(" ORDER by b.nvariety ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,contractNoid );
			ps.setLong( nIndex++ , officeId );
			ps.setLong( nIndex++ , officeId );
			ps.setLong( nIndex++ ,contractNoid );
							
			rs = ps.executeQuery();
			while(rs.next())
			{
				double ba ;
				long v;
				v = rs.getLong("variety");
				ba = rs.getDouble("balance");
				lret.put(String.valueOf(v),String.valueOf(ba));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
//	得到该结算中心得到的授信分配的授信金额币种
	public HashMap getAllCurrencytype (long contractNoid , long officeId ) throws Exception
	{

		HashMap lret = new HashMap();
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT b.nvariety variety , c.currencytype currencytype ");
			strSQL.append(" FROM   loan_bank_creditext_allot b ,");
			strSQL.append(" ( ");
			strSQL.append(" SELECT a.nvariety variety, a.ncurrencytype currencytype ");
			strSQL.append(" FROM loan_bank_creditext_list a  ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" union ");
			strSQL.append(" SELECT 0 variety, a.ncurrencytype currencytype ");
			strSQL.append(" FROM loan_bank_creditext_mix a ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" ) c ");
			strSQL.append(" WHERE b.nisvalid = 1 ");
			strSQL.append(" AND b.nvariety = c.variety");
			strSQL.append(" AND b.nofficeid = ? ");
			strSQL.append(" AND b.nbankcreditextid = ? ");
			strSQL.append(" ORDER by b.nvariety ");			
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ , contractNoid );
			ps.setLong( nIndex++ , contractNoid );
			ps.setLong( nIndex++ , officeId );
			ps.setLong( nIndex++ , contractNoid );
						
			rs = ps.executeQuery();
			while(rs.next())
			{
				long ba ;
				long v;
				v = rs.getLong("variety");
				ba = rs.getLong("currencytype");
				lret.put(String.valueOf(v),String.valueOf(ba));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	//修改银行授信分解记录，判断是否已经分解以及是否超过余额
	public boolean modify(BankCreditExtSplitInfo info) throws Exception 
	{
		boolean lret = false;
		long reLine = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE loan_bank_creditext_split t ");
			strSQL.append(" SET t.mamount = ? ,");//--金额
			strSQL.append(" t.slastmodifier = ? ,");//--修改人
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ,");//--修改日期
			strSQL.append(" t.sremark = ? "); //修改备注
			strSQL.append(" WHERE t.nisvalid = 1 ");//--有效
			strSQL.append(" AND t.nbankcreditextid = ? ");//--授信合同号id
			strSQL.append(" AND t.nvariety = ? ");// --分配品种
			strSQL.append(" AND t.scompanycode = ? ");//结算中心编号			
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setDouble( nIndex++ , info.getAmount());	
			ps.setLong( nIndex++, info.getLastModifier());
			ps.setString( nIndex++ , info.getLastModifyDate());
			ps.setString( nIndex++ , info.getRemark());
			ps.setLong( nIndex++ , info.getId());
			ps.setLong( nIndex++ , info.getVariety());
			ps.setString( nIndex++ , info.getCompanyCode());
			
			reLine = ps.executeUpdate();
			if( reLine>0 )
			{
				lret = true;
			}

			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{			
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	// 判断授信分解信息是否已经存在
//	 若已经分配给该成员单位,返回true,没有分配返回false
	public boolean exist( long contractNoid, long variety, String companyCode ) throws Exception 
	{
		boolean lret = true;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT *");
			strSQL.append(" FROM loan_bank_creditext_split t ");
		    strSQL.append(" WHERE t.nbankcreditextid = ? ");// -- 授信合同号id
		    strSQL.append(" AND t.nvariety =  ? ");//--授信品种id
		    strSQL.append(" AND t.scompanycode = ? ");//--成员单位编号
		    strSQL.append(" AND t.nisvalid = 1");//且该种分配有效
		    
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,contractNoid );
			ps.setLong( nIndex++ , variety );
			ps.setString( nIndex++ , companyCode);
					
			rs = ps.executeQuery();
			if(!rs.next())
			{
				lret = false;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	//计算指定结算中心分解到各成员单位的某品种的银行授信现有余额
	//为分配到指定结算中心的授信额度与已分解给该结算中心各成员单位的授信额度的合计值的差值
	public double getBalance(long contractNoid, long variety, long officeId,String companyCode ) throws Exception 
	{
		double lret = 0;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			if (companyCode == null)
			{
				strSQL.append("  SELECT  nvl( b.mamount-e.amount1,b.mamount )balance ");
				strSQL.append("  FROM loan_bank_creditext_allot b, ");
			}
			else
			{
				strSQL.append(" SELECT  b.mamount-e.amount1+f.mamount balance  ");
				strSQL.append(" FROM loan_bank_creditext_allot b,loan_bank_creditext_split f, ");
			}
			
			strSQL.append(" ( ");
			strSQL.append(" select sum(d.mamount) amount1 ");
			strSQL.append(" from loan_bank_creditext_split d ");
			strSQL.append(" where d.nbankcreditextid = ? ");
			strSQL.append(" and d.nisvalid = 1");
			strSQL.append(" and d.nvariety = ? ");
			strSQL.append(" and d.nofficeid = ? ");//--结算中心id
			strSQL.append(" ) e ");
			if (companyCode == null)
			{
				strSQL.append(" where  b.nbankcreditextid = ? ");//--授信合同号id
				strSQL.append(" and b.nvariety = ? ");//--授信品种
				strSQL.append(" and b.nofficeid = ? ");//--结算中心id
				strSQL.append(" and b.nisvalid = 1 ");								
			}
			else
			{
				strSQL.append(" where  b.nofficeid =  f.nofficeid ");	
				strSQL.append(" and f.nbankcreditextid = b.nbankcreditextid ");
				strSQL.append(" and f.nbankcreditextid = b.nbankcreditextid ");	
				strSQL.append(" and f.nvariety = b.nvariety ");
				strSQL.append(" AND f.nisvalid = 1 ");	
				strSQL.append(" and b.nisvalid = 1 ");
				strSQL.append(" AND f.nbankcreditextid = ? ");	//--授信合同id 传入
				strSQL.append(" and f.nvariety = ? ");//--授信品种id 传入
				strSQL.append(" and f.nofficeid = ? ");	//--结算中心id
				strSQL.append(" and f.scompanycode = ? ");//单位编号	
			}
      
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,contractNoid );
			ps.setLong( nIndex++ ,variety );
			ps.setLong( nIndex++ , officeId );
			if (companyCode == null)
			{
				ps.setLong( nIndex++ , contractNoid );
				ps.setLong( nIndex++ ,variety );
				ps.setLong( nIndex++ , officeId );
			}
			else
			{
				ps.setLong( nIndex++ , contractNoid );
				ps.setLong( nIndex++ , variety );
				ps.setLong( nIndex++ , officeId );	
				ps.setString( nIndex++ ,companyCode );
			}
			rs = ps.executeQuery();
			if(rs.next())
			{
				lret = rs.getDouble("balance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		return lret;
	}
	
	
	  //释放结果集
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("进入关闭RS方法");
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	//释放 ps
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			//Log.print("进入关闭PS方法");
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	//释放连接
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("进入关闭连接方法");
			/**transConn　不为空表明这个数据库连接相关的事务不是容器维护的，因此不在此处关闭
			 * 即　Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false && transConn == null)
			{
				//Log.print("关闭连接--开始");
				con.close();
				con = null;
				//Log.print("关闭连接--结束");
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	
}
