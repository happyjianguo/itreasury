/*
 * Created on 2006-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.creditprove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreditProveDao extends LoanDAO {
	
	public CreditProveDao()
	{
		super("loan_creditprove");
	}	
	

	//�����Ŵ�֤������info�����Ϣinsert����loan_CreditProve��
	public long add(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{
				con = Database.getConnection();
				strSQL = new StringBuffer();
				strSQL.append("INSERT INTO loan_creditprove VALUES((SELECT nvl(MAX(id)+1,1) ID FROM loan_creditProve ),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1) ");
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				
				ps.setString( nIndex++ , info.getCode());
				ps.setLong( nIndex++ , info.getConferContractNo());
				ps.setString( nIndex++ , info.getApplyClient());
				ps.setString( nIndex++ , info.getCertificateBank());
				ps.setString( nIndex++ , info.getCreditProveID());
				ps.setDouble( nIndex++ , info.getBalance());
				ps.setLong( nIndex++ , info.getBCurrencyID());
				ps.setString( nIndex++ , info.getBeneficiaryName());
				ps.setString( nIndex++ , info.getProjectName());
				ps.setDouble( nIndex++ , info.getMoney());
				ps.setLong( nIndex++ , info.getMCurrencyID());
				ps.setDouble( nIndex++ , info.getExchangeRate());
				ps.setDouble( nIndex++ , info.getConvertRMB());
				ps.setLong( nIndex++ , info.getApplyMonth());
				ps.setDate( nIndex++ , DataFormat.getDate(info.getStartDate()));
				ps.setDate( nIndex++ , DataFormat.getDate(info.getEndDate()));
				ps.setDouble( nIndex++ , info.getCharge());
				ps.setLong( nIndex++ , info.getStatusID());
				ps.setString( nIndex++ , info.getApplyPurpose());
				ps.setString( nIndex++ , info.getRemark());
				ps.setLong( nIndex++ , info.getOfficeId());
								
				lret = ps.executeUpdate();
				cleanup(ps);
				cleanup(con);
				
			}
			
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
	
	//	�޸��Ŵ�֤������info�����Ϣupdate����loan_CreditProve��
	public long modify(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{				
				con = Database.getConnection();
			
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE loan_creditprove ");
				strSQL.append(" SET ConferContractNo = ? , ");//���ź�ͬ��
				strSQL.append(" ApplyClient = ? ,");//���뵥λ
				strSQL.append(" CertificateBank = ? ,");//��֤����
				strSQL.append(" CreditProveID = ? ,");//�Ŵ�֤�����
				strSQL.append(" Balance = ? ,");//���
				strSQL.append(" BCurrencyID = ? ,");//������
				strSQL.append(" BeneficiaryName = ? ,");//����������
				strSQL.append(" ProjectName = ? ,");//��Ŀ����
				strSQL.append(" Money = ? ,");//���
				strSQL.append(" MCurrencyID = ? ,");//������
				strSQL.append(" ExchangeRate = ? ,");//������һ���
				strSQL.append(" ConvertRMB = ? ,");//�ۺ������
				strSQL.append(" ApplyMonth = ? ,");//��������
				strSQL.append(" StartDate = to_date(?,'yyyy-mm-dd') ,");//��ʼ����
				strSQL.append(" EndDate = to_date(?,'yyyy-mm-dd') ,");//��������
				strSQL.append(" Charge = ? ,");//������
				strSQL.append(" StatusID = ? ,");//״̬
				strSQL.append(" ApplyPurpose = ? ,");//������;
				strSQL.append(" Remark = ? ");//��ע
				strSQL.append(" where CODE = ? ");
				
				
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				
				ps.setLong( nIndex++ , info.getConferContractNo());
				ps.setString( nIndex++ , info.getApplyClient());
				ps.setString( nIndex++ , info.getCertificateBank());
				ps.setString( nIndex++ , info.getCreditProveID());
				ps.setDouble( nIndex++ , info.getBalance());
				ps.setLong( nIndex++ , info.getBCurrencyID());
				ps.setString( nIndex++ , info.getBeneficiaryName());
				ps.setString( nIndex++ , info.getProjectName());
				ps.setDouble( nIndex++ , info.getMoney());
				ps.setLong( nIndex++ , info.getMCurrencyID());
				ps.setDouble( nIndex++ , info.getExchangeRate());
				ps.setDouble( nIndex++ , info.getConvertRMB());
				ps.setLong( nIndex++ , info.getApplyMonth());
				ps.setString( nIndex++ , info.getStartDate());
				ps.setString( nIndex++ , info.getEndDate());
				ps.setDouble( nIndex++ , info.getCharge());
				ps.setLong( nIndex++ , info.getStatusID());
				ps.setString( nIndex++ , info.getApplyPurpose());
				ps.setString( nIndex++ , info.getRemark());
				ps.setString( nIndex , info.getCode());
				
				lret = ps.executeUpdate();				
				cleanup(ps);
				cleanup(con);
			}
			
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
	
	//	ɾ���Ŵ�֤������info�����Ϣupdate����loan_CreditProve�� �߼�ɾ��
	public long delete(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{
				con = Database.getConnection();
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE loan_creditprove SET statusId = 0");
				strSQL.append(" WHERE code = ?");
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				ps.setString( nIndex++ , info.getCode());				
				lret = ps.executeUpdate();			
				cleanup(ps);
				cleanup(con);			
			}
			
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
	
	//	��ѯ�Ŵ�֤������info�����Ϣ��ѯ��loan_CreditProve
	//������CreditProveInfo��ɵļ���
	public Vector findByCreditProve(CreditProveInfo info) throws Exception
	{
		Vector vret = new Vector();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT t.*,c.sname ClientName ,a.scontractno Contractno ");	
			strSQL.append(" FROM loan_creditprove t ,client c , loan_bank_creditext a  WHERE t.StatusID >0 " );
			strSQL.append(" AND t.officeid = " + info.getOfficeId());
			strSQL.append(" AND c.scode = t.applyclient AND a.id = t.confercontractno ");
	
			if (info != null && !info.getCode().equals(""))
			{
				strSQL.append(" AND t.code = '"+info.getCode()+"'");
			}
			
			if (info != null && info.getCode().equals(""))
			{
				if ( info.getApplyClient()!= null && !info.getApplyClient().equals(""))
				{
					strSQL.append(" AND t.ApplyClient = '"+info.getApplyClient()+"'");
				}
				if ( info.getConferContractNo()>0)
				{
					strSQL.append(" AND t.ConferContractNo = '"+info.getConferContractNo()+"'");
				}
				if ( info.getCertificateBank()!=null && !info.getCertificateBank().equals(""))
				{
					strSQL.append(" AND t.CertificateBank = '"+info.getCertificateBank()+"'");
				}
				if ( info.getApplyMonth()>0 )
				{
					strSQL.append(" AND t.ApplyMonth = "+info.getApplyMonth());
				}
				if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
				{
					strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");
					strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && !(info.getEndStartDate()!=null && !info.getEndStartDate().equals("")))
				{
					strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");				
				}
				if ( !(info.getFromStartDate()!=null && !info.getFromStartDate().equals("") )&& info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
				{				
					strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
				{
					strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");
					strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && !(info.getEndEndDate()!=null && !info.getEndEndDate().equals("")))
				{
					strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");				
				}
				if ( !(info.getFromEndDate()!=null && !info.getFromEndDate().equals("") )&& info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
				{				
					strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
				}									
				if ( info.getStatusID()>0 )
				{
					strSQL.append(" AND t.StatusID = "+info.getStatusID());
				}
			}
			
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				CreditProveInfo cpInfo = new CreditProveInfo();
				
				cpInfo.setCode( rs.getString("Code")); //�ڲ���ˮ��
				cpInfo.setApplyClient( rs.getString("ApplyClient")); //���뵥λ���
				cpInfo.setCertificateBank( rs.getString("CertificateBank")); //��֤����
				cpInfo.setCreditProveID( rs.getString("CreditProveID")); //�Ŵ�֤�����
				cpInfo.setMCurrencyID( rs.getLong("MCurrencyID"));//������
				cpInfo.setMoney( rs.getDouble("Money")); //���
				cpInfo.setStartDate( rs.getDate("StartDate").toString()); //��ʼ����
				cpInfo.setEndDate( rs.getDate("EndDate").toString()); //��������
				cpInfo.setApplyMonth( rs.getLong("ApplyMonth")); //����
				cpInfo.setStatusID( rs.getLong("StatusID")); //״̬
				
				cpInfo.setConferContractNo( rs.getLong("conferContractNo"));//���ź�ͬ��id
				cpInfo.setBalance( rs.getDouble("balance"));//���
				cpInfo.setBCurrencyID( rs.getLong("BCurrencyID"));//������ 
				cpInfo.setBeneficiaryName( rs.getString("beneficiaryName"));//������
				cpInfo.setProjectName( rs.getString("projectName"));//��Ŀ����
				cpInfo.setExchangeRate( rs.getDouble("exchangeRate"));//������һ���
				cpInfo.setConvertRMB( rs.getDouble("convertRMB"));//�ۺ������
				cpInfo.setCharge( rs.getDouble("charge"));//������
				cpInfo.setApplyPurpose( rs.getString("applyPurpose"));//������;
				cpInfo.setRemark( rs.getString("remark"));//��ע
				cpInfo.setClientName( rs.getString("ClientName"));//���뵥λ����
				cpInfo.setContractno( rs.getString("Contractno"));//���ź�ͬ��
				
				
				vret.add( cpInfo );
				
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
		
		return vret.size() >0 ? vret : null;
	}

	//  ��ѯ���÷�ҳ�ķ�ʽ
	public PageLoader findWithPage(CreditProveInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//��ҳ�������õĵõ�sql���ĺ���
	private String[] getSQL (CreditProveInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		
		//select
		sql[0] =" t.*,c.sname ClientName ,a.scontractno Contractno ";
		//from
		sql[1] =" loan_creditprove t ,client c , loan_bank_creditext a";
		//where
		sql[2] =" t.StatusID >0  AND t.officeid = " + info.getOfficeId() +" AND c.scode = t.applyclient AND a.id = t.confercontractno ";
		
		
		/**********�����ѯ����*************/
		if (info != null && !info.getCode().equals(""))
		{
			strSQL.append(" AND t.code = '"+info.getCode()+"'");
		}
		
		if (info != null && info.getCode().equals(""))
		{
			if ( info.getApplyClient()!= null && !info.getApplyClient().equals(""))
			{
				strSQL.append(" AND t.ApplyClient = '"+info.getApplyClient()+"'");
			}
			if ( info.getConferContractNo() > 0 )
			{
				strSQL.append(" AND t.ConferContractNo = '"+info.getConferContractNo()+"'");
			}
			if ( info.getCertificateBank()!=null && !info.getCertificateBank().equals(""))
			{
				strSQL.append(" AND t.CertificateBank = '"+info.getCertificateBank()+"'");
			}
			if ( info.getApplyMonth()>0 )
			{
				strSQL.append(" AND t.ApplyMonth = "+info.getApplyMonth());
			}
			if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
			{
				strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");
				strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && !(info.getEndStartDate()!=null && !info.getEndStartDate().equals("")))
			{
				strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");				
			}
			if ( !(info.getFromStartDate()!=null && !info.getFromStartDate().equals("") )&& info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
			{				
				strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
			{
				strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");
				strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && !(info.getEndEndDate()!=null && !info.getEndEndDate().equals("")))
			{
				strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");				
			}
			if ( !(info.getFromEndDate()!=null && !info.getFromEndDate().equals("") )&& info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
			{				
				strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
			}									
			if ( info.getStatusID()>0 )
			{
				strSQL.append(" AND t.StatusID = "+info.getStatusID());
			}
		}
		
		sql[2] = sql[2] + strSQL.toString();
		//	order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" \n order by t.code" + strDesc);
		sql[3] = oBuf.toString();
		
		return sql;
			
	}
	//������ˮ��
	
	public String getNextCode() throws Exception 
	{
		String strSQL = " SELECT code from loan_creditProve WHERE code LIKE ?";
		String strTemp = "BJBC";
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,strTemp + "%");
			rs = pstmt.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("code");
				scode = scode.substring(strTemp.length(),scode.length());
				if(scode != null && !scode.equals(""))
				{
					int iTemp = Integer.parseInt(scode);
					if(iCode < iTemp)
					{
						iCode = iTemp;
					}
				}
			}
			iCode += 1;
			newCode = "" +iCode;
			while(newCode.length() < 4)
			{
				newCode = "0" + newCode;
			}
			newCode = strTemp + newCode;
			
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		return newCode;
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
