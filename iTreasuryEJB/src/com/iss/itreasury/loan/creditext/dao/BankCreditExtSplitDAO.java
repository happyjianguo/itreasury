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
* �������ŷֽ������DAO
* @author mayongming
* @version 1.0
*/
public class BankCreditExtSplitDAO extends LoanDAO
{
	public BankCreditExtSplitDAO()
	{
		super("loan_bank_creditext_split");
	}	
	//ͨ��������ȣ����ź�ͬ�Ų��ҷ����������ź�ͬ��id��Ϊ��һ����ѯ�ṩ����
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
			strSQL.append(" and b.nofficeid = ? ");//--��������id
			strSQL.append(" and a.syear = ? ");//--�������
			strSQL.append(" and a.scontractno = ? ");//--���ź�ͬ��
			
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
	//	 ͨ�����ź�ͬ��id�ͽ������Ĵ����ѯ�������Ѿ��ֽ⵽���������ĵ����
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
				info.setId(rs.getLong("id"));//���ź�ͬid��
				info.setContractNo(rs.getString("contractno"));//���ź�ͬ��
								
				info.setCompanyCode(rs.getString("companycode"));//��λ���
				info.setCompanyName(rs.getString("Companyname"));//��λ����
				info.setBankName(rs.getString("bankname"));//��������
				
				info.setCurrencyType1(rs.getLong("currencytype1"));//�������ű���
				info.setAmout1(rs.getDouble("amount1"));//�������Ŷ��
				info.setExchangeRate1(rs.getDouble("exchangerate1"));//�������Ż���
				
				info.setCurrencyType2(rs.getLong("currencytype2"));//���ڴ������
				info.setAmout2(rs.getDouble("amount2"));//���ڴ�����
				info.setExchangeRate2(rs.getDouble("exchangerate2"));//���ڴ������
				
				info.setCurrencyType3(rs.getLong("currencytype3"));//�г��ڴ������
				info.setAmout3(rs.getDouble("amount3"));//�г��ڴ�����
				info.setExchangeRate3(rs.getDouble("exchangerate3"));//�г��ڴ������
				
				info.setCurrencyType4(rs.getLong("currencytype4"));//����֤����
				info.setAmout4(rs.getDouble("amount4"));//����֤���
				info.setExchangeRate4(rs.getDouble("exchangerate4"));//����֤����
				
				info.setCurrencyType5(rs.getLong("currencytype5"));//��������
				info.setAmout5(rs.getDouble("amount5"));//�������
				info.setExchangeRate5(rs.getDouble("exchangerate5"));//��������
				
				info.setCurrencyType6(rs.getLong("currencytype6"));//�Ŵ�֤������
				info.setAmout6(rs.getDouble("amount6"));//�Ŵ�֤�����
				info.setExchangeRate6(rs.getDouble("exchangerate6"));//�Ŵ�֤������
				
				info.setCurrencyType7(rs.getLong("currencytype7"));//�жһ�Ʊ����
				info.setAmout7(rs.getDouble("amount7"));//�жһ�Ʊ���
				info.setExchangeRate7(rs.getDouble("exchangerate7"));//�жһ�Ʊ����
				
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
	
	//	 ͨ����������ID������Ʒ�ֺͳ�Ա��λ�����ѯ���ŷ�����Ϣ
	//  �����������ϸ��Ϣ
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
			strSQL.append(" WHERE  f.scompanycode = ? ");// --��λ��� 
			strSQL.append(" AND a.id = f.nbankcreditextid ");
			strSQL.append(" AND f.nbankcreditextid = b.nbankcreditextid ");
			strSQL.append(" AND f.nbankcreditextid = ? ");// --���ź�ͬid 
			strSQL.append(" AND f.nofficeid = b.nofficeid ");
			strSQL.append(" AND c.variety = f.nvariety ");
			strSQL.append(" AND f.nvariety = ? ");//  --����Ʒ��id 
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
				info.setContractNo(rs.getString("contractNo"));//���ź�ͬ��
				info.setBankName(rs.getString("bankname"));//��������
				info.setYear(rs.getString("year"));//�������
				if (rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());//��ʼ����
				}
				if (rs.getDate("enddate")!=null)
				{
					info.setEndDate(rs.getDate("enddate").toString());//��������
				}
				info.setCompanyCode(companyCode);//��λ���
				info.setCompanyName(companyName);//��λ����
				info.setVariety(rs.getLong("variety"));//����Ʒ��
				info.setBalance(rs.getDouble("balance"));//�������
				info.setCurrencyType(rs.getLong("currencytype"));//����
				info.setAmount(rs.getDouble("amount"));//	���
				info.setRemark(rs.getString("remark"));//��ע
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
	
	//	ɾ�����ŷֽ��¼
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
			strSQL.append(" SET t.nisvalid = 0 ,");//--����״̬λΪ��Ч״̬
			strSQL.append(" t.slastmodifier = ? ,");//--����޸���id
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ");//--����޸�����
			strSQL.append(" WHERE t.nbankcreditextid = ? ");//--���ź�ͬ��
			strSQL.append(" AND t.nvariety = ? ");//--����Ʒ��
			strSQL.append(" AND t.scompanycode = ? ");//-- ��λ���
			
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
	
	
	
	//�õ�ĳ����Ʒ�ֵķ���ϵ��
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
			strSQL.append(" WHERE  t.nbankcreditextid = ? ");// --���ź�ͬid
			strSQL.append(" AND t.nvariety = ? ");//--����Ʒ��id
		
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
	
	
	//�����������ŷֽ��¼���ж��Ƿ��Ѿ��ֽ��Լ��Ƿ񳬹����
   //	 ��ӳɹ�����true,ʧ�ܷ���false
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
			ps.setLong( nIndex++ , info.getId());//���ź�ͬ��id
			ps.setLong( nIndex++ , info.getOfficeId());//��������id
			ps.setString( nIndex++ , info.getCompanyCode());//��Ա��λ���
			ps.setLong( nIndex++ , info.getVariety()); //����Ʒ��
			ps.setDouble( nIndex++ , info.getAmount());//���
			ps.setLong( nIndex++ ,info.getLastModifier());//����޸���
			ps.setString ( nIndex++ , info.getLastModifyDate());//����޸�ʱ��
			ps.setString( nIndex++ ,info.getRemark());//��ע
						
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
	
	//ͨ�����ź�ͬ�ŵõ�������Ϣ
	//v002�������������������ҳ�棬��ʾ��ʾ��Ϣ�Ĳ�ѯ
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
			
			strSQL.append(" SELECT a.scontractno contractno , ");// -- ���ź�ͬ��
			strSQL.append(" a.sbankname bankname ,");//  --��������
			strSQL.append(" a.syear year,");//--�������
			strSQL.append(" a.dstartdate startdate ,");//--��ʼ����
			strSQL.append(" a.denddate enddate");//-- ��������
			strSQL.append(" FROM loan_bank_creditext a");
			strSQL.append(" WHERE a.id = ? ");
			strSQL.append(" AND a.nisvalid = 1 ");//��Ч״̬

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
	
//	��������Ʒ�ֵ�����������Ϊ����ʱ��������Ʒ�ֵ�ѡ���ṩ��Ϣ
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
//	�õ��ý������ĵõ������ŷ�������Ž�����
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
	
	//�޸��������ŷֽ��¼���ж��Ƿ��Ѿ��ֽ��Լ��Ƿ񳬹����
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
			strSQL.append(" SET t.mamount = ? ,");//--���
			strSQL.append(" t.slastmodifier = ? ,");//--�޸���
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ,");//--�޸�����
			strSQL.append(" t.sremark = ? "); //�޸ı�ע
			strSQL.append(" WHERE t.nisvalid = 1 ");//--��Ч
			strSQL.append(" AND t.nbankcreditextid = ? ");//--���ź�ͬ��id
			strSQL.append(" AND t.nvariety = ? ");// --����Ʒ��
			strSQL.append(" AND t.scompanycode = ? ");//�������ı��			
			
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
	
	// �ж����ŷֽ���Ϣ�Ƿ��Ѿ�����
//	 ���Ѿ�������ó�Ա��λ,����true,û�з��䷵��false
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
		    strSQL.append(" WHERE t.nbankcreditextid = ? ");// -- ���ź�ͬ��id
		    strSQL.append(" AND t.nvariety =  ? ");//--����Ʒ��id
		    strSQL.append(" AND t.scompanycode = ? ");//--��Ա��λ���
		    strSQL.append(" AND t.nisvalid = 1");//�Ҹ��ַ�����Ч
		    
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
	
	//����ָ���������ķֽ⵽����Ա��λ��ĳƷ�ֵ����������������
	//Ϊ���䵽ָ���������ĵ����Ŷ�����ѷֽ���ý������ĸ���Ա��λ�����Ŷ�ȵĺϼ�ֵ�Ĳ�ֵ
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
			strSQL.append(" and d.nofficeid = ? ");//--��������id
			strSQL.append(" ) e ");
			if (companyCode == null)
			{
				strSQL.append(" where  b.nbankcreditextid = ? ");//--���ź�ͬ��id
				strSQL.append(" and b.nvariety = ? ");//--����Ʒ��
				strSQL.append(" and b.nofficeid = ? ");//--��������id
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
				strSQL.append(" AND f.nbankcreditextid = ? ");	//--���ź�ͬid ����
				strSQL.append(" and f.nvariety = ? ");//--����Ʒ��id ����
				strSQL.append(" and f.nofficeid = ? ");	//--��������id
				strSQL.append(" and f.scompanycode = ? ");//��λ���	
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
	
	
	  //�ͷŽ����
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("����ر�RS����");
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
	//�ͷ� ps
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			//Log.print("����ر�PS����");
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
	//�ͷ�����
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("����ر����ӷ���");
			/**transConn����Ϊ�ձ���������ݿ�������ص�����������ά���ģ���˲��ڴ˴��ر�
			 * ����Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false && transConn == null)
			{
				//Log.print("�ر�����--��ʼ");
				con.close();
				con = null;
				//Log.print("�ر�����--����");
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	
}
