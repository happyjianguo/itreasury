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
* �������ŷ��������DAO
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotDAO extends LoanDAO
{
	public BankCreditExtAllotDAO()
	{
		super("loan_bank_creditext_allot");
	}	
	//ͨ��������Ⱥ����ź�ͬ�ţ��������ź�ͬ��id���Դ���Vector getBankCreditAllot(long id)����������м�����ѯ�Ķ���
	
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
			strSQL.append(" where a.scontractno = ? ");//���ź�ͬ��
			strSQL.append(" and a.syear = ? ");// �������
			strSQL.append(" and a.nisvalid = 1 ");//��Ч
					
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
	//ͨ��������Ⱥ����ź�ͬ�Ų�ѯ�������Ѿ���������������ĵ����
	// v001.jsp �Ĳ�ѯ
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
				info.setId(rs.getLong("id"));//id��
				info.setContractNo(rs.getString("contractno"));//���ź�ͬ��
				
				info.setOfficeid( rs.getLong("officeid"));//��������id
				info.setOfficeCode(rs.getString("officecode"));//�������ı��
				info.setOfficeName(rs.getString("officename"));//������������
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
	
	//ͨ�����ź�ͬid������Ʒ�ֺͽ������ı�Ų�ѯ���ŷ�����Ϣ��
	//���в���������������û��ʵ�����壬ֻ�������ɷ��ؽ��ʱ�����ڹ������ݱ��ѯ����ֱ��ʹ�ø����Ƽ���
	//v002.jsp ��������еĲ�ѯ�������V003.jsp��ʾ
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
				info.setContractNo(rs.getString("contractNo"));//���ź�ͬ��
				info.setBankName(rs.getString("bankname"));//��������
				info.setYear(rs.getString("year"));//�������
				if(rs.getDate("startdate")!=null)
				{
					info.setStartDate(rs.getDate("startdate").toString());//��ʼ����
				}
				if (rs.getDate("enddate")!=null)
				{
					info.setEndDate(rs.getDate("enddate").toString());//��������
				}
				info.setOfficeid(rs.getLong("officeid"));//�������Ĵ���
				info.setOfficeName(officeName);//������������
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
	
	// ɾ�����ŷ����¼ id�����ź�ͬ��  viriety������Ʒ�� officeCode�������ı�� lastModifier ����޸�id  lastModifyDate ����޸�����
	// v003.jsp ɾ�����ŷ�����Ϣ
	//����true ɾ���ɹ�  false ɾ��ʧ��
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
			strSQL.append(" SET t.nisvalid = 0 ,");//--����״̬λΪ��Ч״̬
			strSQL.append(" t.slastmodifier = ? ,");//--����޸���id
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ");//--����޸�����
			strSQL.append(" WHERE t.nbankcreditextid = ? ");//--���ź�ͬ��
			strSQL.append(" AND t.nvariety = ? ");//--����Ʒ��
			strSQL.append(" AND t.nofficeid = ?");//-- �������ı��
			  
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
	
	//�жϸ������Ƿ��Ѿ����ֽ⵽�������ĵĳ�Ա��λ�� id�����ź�ͬ��  viriety������Ʒ�� officeCode�������ı��
	//����true���Ѿ��ֽ� falseû�зֽ�
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
	
	
	// �����������ŷ����������¼
	// ��ӳɹ�����true,ʧ�ܷ���false
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
	
	//	ͨ�����ź�ͬ��id�õ�������Ϣ
	// v002,������������
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

	//�޸��������ŷ����������¼
	//����true�޸ĳɹ�������false�޸�ʧ��
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
			strSQL.append(" SET t.mamount = ? ,");//--���
			strSQL.append(" t.slastmodifier = ? ,");//--�޸���
			strSQL.append(" t.dlastmodifydate = to_date( ? ,'yyyy-mm-dd') ,");//--�޸�����
			strSQL.append(" t.sremark = ? "); //�޸ı�ע
			strSQL.append(" WHERE t.nisvalid = 1 ");//--��Ч
			strSQL.append(" AND t.nbankcreditextid = ? ");//--���ź�ͬ��id
			strSQL.append(" AND t.nvariety = ? ");// --����Ʒ��
			strSQL.append(" AND t.nofficeid = ? ");//�������ı��
			
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
	
	// �жϸ�����Ʒ���Ƿ��Ѿ������ָ���Ľ�������
	// ���Ѿ�������ý�������,����true,û�з��䷵��false
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
		    strSQL.append(" WHERE t.nofficeid = ? ");// --�������ı��
		    strSQL.append(" AND t.nvariety = ? ");//--����Ʒ��id
		    strSQL.append(" AND t.nbankcreditextid = ? ");//--���ź�ͬ��id
		    strSQL.append(" AND t.nisvalid = 1");//�Ҹ��ַ�����Ч
				
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
	
	//����ĳƷ�ֵ���������������Ϊ�����еõ��Ķ�����ѷ�������������Ķ�ȵĺϼ�ֵ�Ĳ�ֵ�Ӹý������ĵķ�����
	//��officeCode��ֵΪnull����Ϊ���еõ��Ķ�����ѷ�������������Ķ�ȵĺϼ�ֵ�Ĳ�ֵ
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
			strSQL.append(" AND d.nisvalid = 1 ");//��Ч״̬
			strSQL.append(" AND d.nvariety = ? ");//����Ʒ��id
			strSQL.append(" ) e");
			if(officeId==-1)
			{
				strSQL.append(" WHERE c.variety = ?");
			}
			else
			{
				strSQL.append(" WHERE b.nisvalid=1");// --�������Ч
				strSQL.append(" AND c.variety=b.nvariety");//--c���b�������Ʒ����ͬ
				strSQL.append(" AND b.nbankcreditextid = ? ");//--���ź�ͬ��id
				strSQL.append(" AND b.nofficeid = ? ");// �������ı��
				strSQL.append(" AND b.nvariety = ? ");//����Ʒ��
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
	
	//��������Ʒ�ֵ�����������Ϊ����ʱ��������Ʒ�ֵ�ѡ���ṩ��Ϣ
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
	//�õ���������Ʒ�ֵ����Ž�����
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
	//����ĳ��������ĳƷ�ֵ����������Ѿ��ֽ����ܺ�
    //���ź�ͬid������Ʒ�֣��������ı���
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
			strSQL.append(" WHERE t.nofficeid = ? "); // --��������id
			strSQL.append(" AND t.nvariety = ? "); //--����Ʒ��
			strSQL.append(" AND t.nbankcreditextid = ? ");// --���ź�ͬid
			strSQL.append(" AND t.nisvalid = 1 "); //--��Ч			 
			
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
