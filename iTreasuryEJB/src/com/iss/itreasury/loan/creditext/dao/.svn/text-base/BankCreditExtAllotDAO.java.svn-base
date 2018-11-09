package com.iss.itreasury.loan.creditext.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotResultInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotInfo;
import com.iss.itreasury.util.Database;

/**
* 银行授信分配与调整DAO
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotDAO extends LoanDAO
{
	public BankCreditExtAllotDAO()
	{
		super("loan_bank_creditext_allot");
	}	
	//通过授信年度和授信合同号，查找授信合同号id，以传给Vector getBankCreditAllot(long id)这个函数进行继续查询的动作
	
	public long getContractId (String contractno, String year) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		long lret = -1;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" select a.id id from loan_bank_creditext a ");
			strSQL.append(" where a.scontractno = ? ");//授信合同号
			strSQL.append(" and a.syear = ? ");// 授信年度
			strSQL.append(" and a.nisvalid = 1 ");//有效
					
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setString( nIndex++ ,contractno );
			ps.setString( nIndex++ , year);	
			
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
	//通过授信年度和授信合同号查询该授信已经分配给各结算中心的情况
	// v001.jsp 的查询
	public Vector getBankCreditAllot(long id) throws Exception
	{
		Vector vret = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		BankCreditExtAllotResultInfo info = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			strSQL.append("select d.id id ,contractno,bankname, officeid  , e.sname officename, e.scode officecode,");
			strSQL.append(" sum(amount1) amount1, sum(currencytype1) currencytype1,sum(exchangerate1) exchangerate1,");
			strSQL.append(" sum(amount2) amount2,sum(currencytype2) currencytype2,sum(exchangerate2) exchangerate2,");
			strSQL.append(" sum(amount3) amount3,sum(currencytype3) currencytype3,sum(exchangerate3) exchangerate3,");
			strSQL.append(" sum(amount4) amount4,sum(currencytype4) currencytype4,sum(exchangerate4) exchangerate4,");
			strSQL.append(" sum(amount5) amount5,sum(currencytype5) currencytype5,sum(exchangerate5) exchangerate5,");
			strSQL.append(" sum(amount6) amount6,sum(currencytype6) currencytype6,sum(exchangerate6) exchangerate6,");
			strSQL.append(" sum(amount7) amount7,sum(currencytype7) currencytype7,sum(exchangerate7) exchangerate7 ");
			strSQL.append(" from (");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" b.mamount amount1, c.ncurrencytype currencytype1, c.nexchangerate exchangerate1, ");
			strSQL.append(" 0 amount2,0 currencytype2, 0 exchangerate2, 0 amount3,0 currencytype3, 0 exchangerate3,");
			strSQL.append(" 0 amount4,0 currencytype4, 0 exchangerate4, 0 amount5,0 currencytype5, 0 exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_mix c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=0");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, b.mamount amount2, c.ncurrencytype currencytype2, c.nexchangerate exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0 currencytype6, 0 exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=1 and c.nvariety=1 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, 0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" b.mamount amount3, c.ncurrencytype currencytype3, c.nexchangerate exchangerate3,");
			strSQL.append(" 0 amount4,0 currencytype4, 0 exchangerate4, 0 amount5,0 currencytype5, 0 exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=2 and c.nvariety=2 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3, b.mamount amount4, c.ncurrencytype currencytype4, c.nexchangerate exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0 currencytype6, 0 exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7 ");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=3 and c.nvariety=3 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1, 0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3, 0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" b.mamount amount5, c.ncurrencytype currencytype5, c.nexchangerate exchangerate5,");
			strSQL.append(" 0 amount6,0 currencytype6, 0 exchangerate6, 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=4 and c.nvariety=4 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,b.mamount amount6, c.ncurrencytype currencytype6, c.nexchangerate exchangerate6,");
			strSQL.append(" 0 amount7,0 currencytype7, 0 exchangerate7");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=5 and c.nvariety=5 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" union");
			strSQL.append(" select a.id id, a.scontractno contractno, a.syear year, a.sbankname bankname, b.nofficeid officeid, ");
			strSQL.append(" 0 amount1,0 currencytype1, 0 exchangerate1,0 amount2,0 currencytype2, 0 exchangerate2,");
			strSQL.append(" 0 amount3,0 currencytype3, 0 exchangerate3,0 amount4,0 currencytype4, 0 exchangerate4,");
			strSQL.append(" 0 amount5,0 currencytype5, 0 exchangerate5,0 amount6,0  currencytype6, 0 exchangerate6,");
			strSQL.append(" b.mamount amount7,c.ncurrencytype currencytype7, c.nexchangerate exchangerate7 ");
			strSQL.append(" from loan_bank_creditext a,loan_bank_creditext_allot b,loan_bank_creditext_list c");
			strSQL.append(" where a.id=b.nbankcreditextid and a.id=c.nbankcreditextid");
			strSQL.append(" and b.nvariety=c.nvariety and b.nbankcreditextid=c.nbankcreditextid and b.nvariety=6 and c.nvariety=6 ");
			strSQL.append(" and a.id =? and b.nisvalid= 1 and a.nisvalid = 1");
			strSQL.append(" ) d,office e");
			strSQL.append(" where e.id = d.officeid");
			strSQL.append(" group by d.id ,contractno,bankname,e.sname,d.officeid,e.scode");
			strSQL.append(" order by e.scode ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , id );			
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );
			ps.setLong( nIndex++ , id );	
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				info = new BankCreditExtAllotResultInfo();
				info.setId(rs.getLong("id"));//id号
				info.setContractNo(rs.getString("contractno"));//授信合同号
				
				info.setOfficeid( rs.getLong("officeid"));//结算中心id
				info.setOfficeCode(rs.getString("officecode"));//结算中心编号
				info.setOfficeName(rs.getString("officename"));//结算中心名称
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
	
	//通过授信合同id、授信品种和结算中心编号查询授信分配信息，
	//其中参数结算中心名称没有实际意义，只是在生成返回结果时不用在关联数据表查询，而直接使用该名称即可
	//v002.jsp 点击金额进行的查询，结果在V003.jsp显示
	public BankCreditExtAllotInfo getBankCreditAllotInfo(long  id, long variety, long officeId, String officeName) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		BankCreditExtAllotInfo info = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append("select a.id id, a.scontractno contractNo,a.sbankname bankname,a.syear year, a.dstartdate startdate,");
			strSQL.append(" a.denddate enddate,b.nofficeid officeid,b.nvariety variety,c.currencytype currencytype,");
			strSQL.append(" c.amount-e.amount1+b.mamount balance,b.mamount amount, b.sremark remark");
			strSQL.append(" from loan_bank_creditext a, loan_bank_creditext_allot b,");
			strSQL.append(" (");
			strSQL.append(" select a.nvariety variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" from loan_bank_creditext_list a ");
			strSQL.append(" where a.nbankcreditextid = ?");
			strSQL.append(" union ");
			strSQL.append(" select 0 variety, a.ncurrencytype currencytype,a.mamount amount");
			strSQL.append(" from loan_bank_creditext_mix a ");
			strSQL.append(" where a.nbankcreditextid=?");
			strSQL.append(" ) c,");
			strSQL.append(" (");
			strSQL.append(" select sum(d.mamount) amount1 ");
			strSQL.append(" from loan_bank_creditext_allot d ");
			strSQL.append(" where d.nbankcreditextid=?");
			strSQL.append(" and d.nisvalid=1 ");
			strSQL.append(" and d.nvariety=?");
			strSQL.append(" ) e");
			strSQL.append(" where a.id=b.nbankcreditextid ");
			strSQL.append(" and a.nisvalid=1 and b.nisvalid=1");
			strSQL.append(" and c.variety=b.nvariety");			
			strSQL.append(" and b.nofficeid=?");
			strSQL.append(" and b.nvariety=?");
			strSQL.append(" and a.id =?");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,variety );
			ps.setLong( nIndex++ ,officeId );
			ps.setLong( nIndex++ ,variety );
			ps.setLong( nIndex++ ,id );
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = new BankCreditExtAllotInfo();
				
				info.setId(rs.getLong("id"));//id
				info.setContractNo(rs.getString("contractNo"));//授信合同号
				info.setBankName(rs.getString("bankname"));//授信银行
				info.setYear(rs.getString("year"));//授信年度
				if(rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());//起始日期
				}
				if (rs.getDate("enddate")!=null)
				{
					info.setEndDate(rs.getDate("enddate").toString());//结束日期
				}
				info.setOfficeid(rs.getLong("officeid"));//结算中心代码
				info.setOfficeName(officeName);//结算中心名称
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
	
	// 删除授信分配记录 id：授信合同号  viriety：授信品种 officeCode结算中心编号 lastModifier 最后修改id  lastModifyDate 最后修改日期
	// v003.jsp 删除授信分配信息
	//返回true 删除成功  false 删除失败
	public boolean delete(long id, long viriety, long officeId,long lastModifier, String lastModifyDate) throws Exception 
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
			strSQL.append(" UPDATE loan_bank_creditext_allot t ");
			strSQL.append(" SET t.nisvalid = 0 ,");//--设置状态位为无效状态
			strSQL.append(" t.slastmodifier = ? ,");//--最后修改人id
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ");//--最后修改日期
			strSQL.append(" WHERE t.nbankcreditextid = ? ");//--授信合同号
			strSQL.append(" AND t.nvariety = ? ");//--授信品种
			strSQL.append(" AND t.nofficeid = ?");//-- 结算中心编号
			  
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , lastModifier);	
			ps.setString( nIndex++ , lastModifyDate );
			ps.setLong( nIndex++ , id);
			ps.setLong( nIndex++ , viriety);
			ps.setLong( nIndex++ , officeId );
			
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
	
	//判断该授信是否已经被分解到结算中心的成员单位了 id：授信合同号  viriety：授信品种 officeCode结算中心编号
	//返回true是已经分解 false没有分解
	public boolean isSplited(long id, long viriety, long officeId) throws Exception 
	{
		boolean lret = false;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
			
			strSQL.append(" SELECT t.nisvalid isvalid ");
			strSQL.append(" FROM loan_bank_creditext_split t");
			strSQL.append(" WHERE t.nbankcreditextid = ? ");
			strSQL.append(" AND t.nofficeid = ?");
			strSQL.append(" AND t.nvariety = ?");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,officeId );
			ps.setLong( nIndex++ ,viriety );
			
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				long isvalid = -1;
				isvalid = rs.getLong( "isvalid" );
				if(isvalid==1)
				{
					lret = true;
					break;					
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
		return lret;
	}
	
	
	// 新增银行授信分配与调整记录
	// 添加成功返回true,失败返回false
	public boolean insert(BankCreditExtAllotInfo info) throws Exception 
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
			strSQL.append(" INSERT INTO loan_bank_creditext_allot t VALUES(?,?,?,?,1,?,to_date(?,'yyyy-mm-dd'),? ) ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , info.getId());
			ps.setLong( nIndex++ , info.getOfficeid());
			ps.setLong( nIndex++ , info.getVariety());
			ps.setDouble( nIndex++ , info.getAmount());
			ps.setLong( nIndex++ , info.getLastModifier());
			ps.setString( nIndex++ , info.getLastModifyDate());
			ps.setString( nIndex++ , info.getRemark());
			
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
	
	//	通过授信合同号id得到授信信息
	// v002,点击新增后调用
	public BankCreditExtAllotInfo getBankCreditInfo(long id) throws Exception 
	{
		BankCreditExtAllotInfo info = null ;
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
			ps.setLong( nIndex++ ,id );
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = new BankCreditExtAllotInfo();
				
				info.setId(id);
				info.setContractNo(rs.getString("contractno"));
				info.setBankName(rs.getString("bankname"));
				info.setYear(rs.getString("year"));
				if (rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());
				}
				if(rs.getString("enddate")!=null)
				{
					info.setEndDate(rs.getString("enddate").toString());
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

	//修改银行授信分配与调整记录
	//返回true修改成功，返回false修改失败
	public boolean modify( BankCreditExtAllotInfo info) throws Exception 
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
			strSQL.append(" UPDATE loan_bank_creditext_allot t ");
			strSQL.append(" SET t.mamount = ? ,");//--金额
			strSQL.append(" t.slastmodifier = ? ,");//--修改人
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ,");//--修改日期
			strSQL.append(" t.sremark = ? "); //修改备注
			strSQL.append(" WHERE t.nisvalid = 1 ");//--有效
			strSQL.append(" AND t.nbankcreditextid = ? ");//--授信合同号id
			strSQL.append(" AND t.nvariety = ? ");// --分配品种
			strSQL.append(" AND t.nofficeid = ? ");//结算中心编号
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setDouble( nIndex++ , info.getAmount());	
			ps.setLong( nIndex++, info.getLastModifier());
			ps.setString( nIndex++ , info.getLastModifyDate());
			ps.setString( nIndex++ , info.getRemark());
			ps.setLong( nIndex++ , info.getId());
			ps.setLong( nIndex++ , info.getVariety());
			ps.setLong( nIndex++ , info.getOfficeid());
			
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
	
	// 判断该授信品种是否已经分配给指定的结算中心
	// 若已经分配给该结算中心,返回true,没有分配返回false
	public boolean exist(long id, long variety, long officeId) throws Exception 
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
			strSQL.append(" FROM loan_bank_creditext_allot t ");
		    strSQL.append(" WHERE t.nofficeid = ? ");// --结算中心编号
		    strSQL.append(" AND t.nvariety = ? ");//--授信品种id
		    strSQL.append(" AND t.nbankcreditextid = ? ");//--授信合同号id
		    strSQL.append(" AND t.nisvalid = 1");//且该种分配有效
				
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,officeId );
			ps.setLong( nIndex++ , variety );
			ps.setLong( nIndex++ , id);
					
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
	
	//计算某品种的银行授信现有余额，为从银行得到的额度与已分配给各结算中心额度的合计值的差值加该结算中心的分配金额
	//若officeCode传值为null，则为银行得到的额度与已分配给各结算中心额度的合计值的差值
	public double getBalance(long id, long variety , long officeId) throws Exception 
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
			if(officeId==-1)
			{
				strSQL.append(" SELECT nvl( c.amount-e.amount1, c.amount ) balance ");
				strSQL.append(" FROM");
			}
			else
			{
			strSQL.append(" SELECT c.amount-e.amount1+b.mamount balance ");
			strSQL.append(" FROM loan_bank_creditext_allot b,");
			}
			strSQL.append(" (");
			strSQL.append(" SELECT a.nvariety variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_list a ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" union ");
			strSQL.append(" SELECT 0 variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_mix a ");
			strSQL.append(" WHERE a.nbankcreditextid = ? ");
			strSQL.append(" ) c,");
			strSQL.append(" (");
			strSQL.append(" SELECT sum(d.mamount) amount1 ");
			strSQL.append(" FROM loan_bank_creditext_allot d ");
			strSQL.append(" WHERE d.nbankcreditextid = ?");
			strSQL.append(" AND d.nisvalid = 1 ");//有效状态
			strSQL.append(" AND d.nvariety = ? ");//授信品种id
			strSQL.append(" ) e");
			if(officeId==-1)
			{
				strSQL.append(" WHERE c.variety = ?");
			}
			else
			{
				strSQL.append(" WHERE b.nisvalid=1");// --分配表有效
				strSQL.append(" AND c.variety=b.nvariety");//--c表和b表的授信品种相同
				strSQL.append(" AND b.nbankcreditextid = ? ");//--授信合同号id
				strSQL.append(" AND b.nofficeid = ? ");// 结算中心编号
				strSQL.append(" AND b.nvariety = ? ");//授信品种
			}
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,variety );
			if(officeId ==-1)
			{
				ps.setLong(nIndex++ ,variety);
			}
			else
			{
				ps.setLong( nIndex++ ,id );
				ps.setLong( nIndex++ , officeId);
				ps.setLong( nIndex++ , variety );
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
	
	//计算所有品种的授信现有余额，为新增时各个授信品种的选择提供信息
	public HashMap getAllBalance (long id ) throws Exception
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
			
			strSQL.append(" SELECT c.variety variety , nvl( c.amount-e.amount1, c.amount ) balance ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT a.nvariety variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_list a ");
			strSQL.append(" WHERE a.nbankcreditextid=? ");
			strSQL.append(" union ");
			strSQL.append(" SELECT 0 variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_mix a ");
			strSQL.append(" WHERE a.nbankcreditextid=?");
			strSQL.append(" ) c,");
			strSQL.append(" ( ");
			strSQL.append(" SELECT sum(d.mamount) amount1 ,d.nvariety nvariety ");
			strSQL.append(" FROM loan_bank_creditext_allot d ");
			strSQL.append(" WHERE d.nbankcreditextid=?");
			strSQL.append(" AND d.nisvalid=1 ");
			strSQL.append(" GROUP by d.nvariety ");
			strSQL.append(" ) e ");
			strSQL.append(" WHERE e.nvariety (+) = c.variety");
			strSQL.append(" ORDER by c.variety ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );			
			ps.setLong( nIndex++ ,id );
				
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
	//得到所有授信品种的授信金额币种
	public HashMap getAllCurrencytype (long id ) throws Exception
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
			
			strSQL.append(" SELECT c.variety variety , c.currencytype currencytype ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT a.nvariety variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_list a ");
			strSQL.append(" WHERE a.nbankcreditextid=? ");
			strSQL.append(" union ");
			strSQL.append(" SELECT 0 variety, a.ncurrencytype currencytype,a.mamount amount ");
			strSQL.append(" FROM loan_bank_creditext_mix a ");
			strSQL.append(" WHERE a.nbankcreditextid=?");
			strSQL.append(" ) c,");
			strSQL.append(" ( ");
			strSQL.append(" SELECT sum(d.mamount) amount1 ");
			strSQL.append(" FROM loan_bank_creditext_allot d ");
			strSQL.append(" WHERE d.nbankcreditextid=?");
			strSQL.append(" AND d.nisvalid=1 ");
			strSQL.append(" ) e ");
			strSQL.append(" ORDER by c.variety ");
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
		
			ps.setLong( nIndex++ ,id );
			ps.setLong( nIndex++ ,id );			
			ps.setLong( nIndex++ ,id );
				
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
	//计算某结算中心某品种的银行授信已经分解金额总和
    //授信合同id，授信品种，结算中心编码
	public double getHasSplit ( long id, long variety ,long officeId) throws Exception
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
			
			strSQL.append(" SELECT sum(t.mamount) amount ");
			strSQL.append(" FROM loan_bank_creditext_split t");
			strSQL.append(" WHERE t.nofficeid = ? "); // --结算中心id
			strSQL.append(" AND t.nvariety = ? "); //--授信品种
			strSQL.append(" AND t.nbankcreditextid = ? ");// --授信合同id
			strSQL.append(" AND t.nisvalid = 1 "); //--有效			 
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ , officeId);
			ps.setLong( nIndex++ ,variety );
			ps.setLong( nIndex++ ,id );			
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				lret = rs.getDouble("amount");
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
