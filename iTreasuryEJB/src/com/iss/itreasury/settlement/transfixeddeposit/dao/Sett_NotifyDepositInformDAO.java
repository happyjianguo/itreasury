/*
 * Created on 2006-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.settlement.util.*;

/**
 * @author caryzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sett_NotifyDepositInformDAO extends SettlementDAO
{
	public Sett_NotifyDepositInformDAO()
	{
		super();
	}
	
	/**
	 * Constructor for Sett_AccountBankDAO.
	 * @param conn
	 */
	public Sett_NotifyDepositInformDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_notifyDepositInform";
	}
	//�õ�ָ֪ͨ���״̬
	public long getStatus (long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			conn = Database.getConnection();
			strSQL = new StringBuffer();
		    strSQL.append(" select t.statusid status from sett_notifyDepositInform t where t.id = ?");
		    ps = conn.prepareStatement(strSQL.toString());	
			int nIndex = 1;
			ps.setLong( nIndex++ ,id);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				lret = rs.getLong("status");
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				throw exp;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);		
			}			
			//System.out.println("**********hydao****************************id="+id);
			//System.out.println("**********hydao****************************lret="+lret);
		return lret;
	}

	//	����֪ͨ��Ϣ����info�����Ϣinsert����sett_notifyDepositInform��
	public long add(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if(info!=null)
			{
				conn = getConnection();
				strSQL = new StringBuffer();
				strSQL.append("INSERT INTO sett_notifyDepositInform(ID,MODULEID,OFFICEID,DEPOSITNO,NOTIFYDATE,AMOUNT,STATUSID,USERID,STRANSNO) VALUES((SELECT nvl(MAX(id)+1,1) ID FROM sett_notifyDepositInform ),?,?,?,?,?,?,?,?) ");
				ps = conn.prepareStatement(strSQL.toString());
				int nIndex = 1;
				ps.setLong( nIndex++ ,info.getModuleID());
				ps.setLong(nIndex++ , info.getOfficeID());
				ps.setString(nIndex++ , info.getDepositNo());
				ps.setDate(nIndex++ , DataFormat.getDate(info.getNotifyDate()));
				ps.setDouble(nIndex++ , info.getAmount());
				ps.setLong(nIndex++ , info.getStatusID());
				ps.setLong(nIndex++ , info.getUserID());
				ps.setString(nIndex++,info.getStransno());
				
				lret = ps.executeUpdate();
	
				cleanup(ps);
				cleanup(conn);
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	/**
	 * ���뽻��id
	 */
	public long insertTransID (long transId ,long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = Database.getConnection();
			
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE sett_notifyDepositInform ");
			strSQL.append(" set transId = ? ");
			strSQL.append(" where id = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			
			ps.setLong(nIndex++ , transId);
			ps.setLong(nIndex++ , id);
			
			lret = ps.executeUpdate();				
			cleanup(ps);
			cleanup(conn);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}	
	
	//�޸�ָ֪ͨ�� ���׺ţ�
	public long insertTransNo (String stransno,long id) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = Database.getConnection();
			
			strSQL = new StringBuffer();
			strSQL.append(" UPDATE sett_notifyDepositInform ");
			strSQL.append(" set stransno = ? ");
			strSQL.append(" where id = ? ");
			
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			
			ps.setString(nIndex++ , stransno);
			ps.setLong(nIndex++ , id);
			
			lret = ps.executeUpdate();				
			cleanup(ps);
			cleanup(conn);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	/**
	 * ͨ�����ף����Ҽ�¼��Added by zwsun, 2007/7/17
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public NotifyDepositInformInfo getInfoByTransId(long transId) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT ");
			strSQL.append(" t.id,");//id
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//֪ͨ����
			strSQL.append(" to_char(t.notifyDate+(u.nnoticeday-10000),'yyyy-mm-dd') strTakeDate "); //ȡ������
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u  ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND t.transId = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,transId );
			System.out.print("sql="+strSQL);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				info.setID(rs.getLong("id"));
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setStrTakeDate(rs.getString("strTakeDate"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info == null ? new NotifyDepositInformInfo(): info;
	}	
	
	/**
	 * ͨ�����׺Ų��Ҽ�¼��Added by zwsun, 2007/7/17
	 * @param stransno
	 * @return
	 * @throws Exception
	 */
	public NotifyDepositInformInfo getInfoByTransNo (String stransno) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT ");
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//֪ͨ����
			strSQL.append(" to_char(t.notifyDate+(u.nnoticeday-10000),'yyyy-mm-dd') strTakeDate "); //ȡ������
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u  ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND t.stransno = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setString( nIndex++ ,stransno );
			System.out.print("sql="+strSQL);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setStrTakeDate(rs.getString("strTakeDate"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info == null ? new NotifyDepositInformInfo(): info;
	}
	
	//�޸�֪ͨ��Ϣ����info�����Ϣupdate����sett_notifyDepositInform��
	public long modify(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if(info!=null)
			{
				
				conn = Database.getConnection();
			
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE sett_notifyDepositInform ");
				
				if (info.getStatusID() == 0)//������״̬
				{
					strSQL.append(" SET statusid = ? ");
					strSQL.append(" WHERE id = "+info.getID());
					
					ps = conn.prepareStatement(strSQL.toString());
					int nIndex = 1;
					
					ps.setLong(nIndex++ , info.getStatusID());
				}
				if (info.getStatusID() == 1)//�޸�֪ͨ���ں�ȡ����
				{
					if(info.getIsDele() ==1)//�����֪֧ͨȡʱ��ɾ��,��ʱ��ָ֪ͨ�����ʹ�ø�Ϊ����
					{
						strSQL.append(" SET statusid = ? ");
						strSQL.append(" WHERE depositno = ?");
						strSQL.append(" AND stransno = ?");
						
						ps = conn.prepareStatement(strSQL.toString());
						int nIndex = 1;
						
						ps.setLong(nIndex++ , SETTConstant.NotifyInformStatus.CANCEL);
						ps.setString(nIndex++ , info.getDepositNo());
						ps.setString(nIndex++ , info.getStransno());
					}
					else
					{
						strSQL.append(" SET notifydate = to_date( ? ,'yyyy-mm-dd') ,");
						strSQL.append(" amount = ? ");
						strSQL.append(" WHERE id = ?");
						strSQL.append(" AND depositno = ?");
						
						ps = conn.prepareStatement(strSQL.toString());
						int nIndex = 1;
						
						ps.setString( nIndex++ , info.getNotifyDate());
						ps.setDouble( nIndex++ , info.getAmount());
						ps.setLong( nIndex++ , info.getID());
						ps.setString( nIndex++ , info.getDepositNo());	
					}
 				}	
				if (info.getStatusID() == 2 )//֪֧ͨȡ����״̬дΪ��ʹ��
				{
					strSQL.append(" SET statusid = ? ");
					strSQL.append(" WHERE depositno = ?");
					strSQL.append(" AND id = ?");
					
					ps = conn.prepareStatement(strSQL.toString());
					int nIndex = 1;
					
					ps.setLong(nIndex++ , SETTConstant.NotifyInformStatus.USED);
					ps.setString(nIndex++ , info.getDepositNo());
					ps.setLong(nIndex++ , info.getID());
				}
					
				
				lret = ps.executeUpdate();				
				cleanup(ps);
				cleanup(conn);
				
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	
	//��ѯ֪ͨ��Ϣ����info�����Ϣ��ѯ��sett_notifyDepositInform
	//info��ֻ�贫��ģ���ʶ�Ͱ��´���ʶ
	//������NotifyDepositInformInfo��ɵļ���
	public Vector findByCondition(NotifyDepositInformInfo info) throws Exception
	{
		Vector vret = null;
		
		return vret;
	}
	
	//	��ѯ֪ͨ��Ϣ����id��ѯ��sett_notifyDepositInform
	//����NotifyDepositInformInfo
	public NotifyDepositInformInfo findByID(long id) throws Exception
	{
		NotifyDepositInformInfo info = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT  c.scode clientCode ,");	//�ͻ����		
			strSQL.append(" c.sname clientName, " );//�ͻ�����	
			strSQL.append(" a.saccountno  saccountno,");//�˻����
			strSQL.append(" u.mamount mAmount ,");//����
			strSQL.append(" (b.mBalance-b.mUncheckPaymentAmount)  balance,");//���
			strSQL.append(" to_char(b.AF_dtStart,'yyyy-mm-dd') startdate,");//��ʼ��
			strSQL.append(" t.depositno DepositNo,");//���ݺ�
			strSQL.append(" t.amount amount, ");//֧ȡ���
			strSQL.append(" to_char(t.notifydate , 'yyyy-mm-dd' ) notifyDate , ");//֪ͨ����
			strSQL.append(" t.id id,"); //idֵ
			//Added by zwsun, 2007/7/4
			strSQL.append(" t.moduleid moduleId");//ģ��id
			
			strSQL.append(" FROM sett_notifyDepositInform t ,");
			strSQL.append(" sett_transopenfixeddeposit u , ");
			strSQL.append(" client c , ");
			strSQL.append(" sett_account a ,");
			strSQL.append(" sett_SubAccount b ");
			
			strSQL.append(" WHERE u.sdepositno = t.depositno ");
			strSQL.append(" AND c.id = u.nclientid ");
			strSQL.append(" AND a.id = u.naccountid ");
			strSQL.append(" AND b.nAccountID = a.ID ");
			strSQL.append(" AND t.depositno = b.af_sdepositno ");
			strSQL.append(" AND b.nstatusid>0 ");
			strSQL.append(" AND t.id = ? ");						
			
			ps = con.prepareStatement(strSQL.toString());
			
			int nIndex = 1;
			ps.setLong( nIndex++ ,id );
			
			System.out.print("id="+id);
			System.out.print("sql="+strSQL);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				info = new NotifyDepositInformInfo();
				
				info.setClientCode(rs.getString("clientCode"));
				info.setClientName(rs.getString("clientName"));
				info.setSaccountno(rs.getString("saccountno"));
				info.setmAmount(rs.getDouble("mAmount"));
				info.setBalance(rs.getDouble("balance"));
				info.setStartdate(rs.getString("startdate"));
				info.setDepositNo(rs.getString("DepositNo"));
				info.setAmount(rs.getDouble("amount"));
				info.setNotifyDate(rs.getString("notifyDate"));
				info.setID(rs.getLong("id"));
				//Added by zwsun, 2007/7/4
				info.setModuleID(rs.getLong("moduleId"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return info;
	}
	
	//  ��ѯ���÷�ҳ�ķ�ʽ
	public PageLoader findWithPage(NotifyDepositInformInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//��ҳ�������õĵõ�sql���ĺ���
	private String[] getSQL (NotifyDepositInformInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		long  moduleID = info.getModuleID();
		
		//select
		sql[0] =" a.saccountno  saccountno,  c.sname clientName ,  t.depositno DepositNo ,u.mamount mAmount ,t.amount amount ,t.moduleid moduleID, t.notifydate notifyDate1 , t.id ID , t.statusid statusID , t.userid userID";
		//from
		sql[1] =" sett_notifyDepositInform t ,sett_transopenfixeddeposit u, sett_account a , client c ";
	   if (moduleID== Constant.ModuleType.EBANK)
        {
        	sql[1] = sql[1]+ " , ob_user us" ;
        }
		
		//where
		sql[2] =" t.statusid >0 ";
		strSQL.append(" and t.depositno  = u.sdepositno ");
        strSQL.append(" and a.id = u.naccountid ");
        strSQL.append(" and  c.id = u.nclientid");
    
        if (moduleID== Constant.ModuleType.EBANK)
        {
        	strSQL.append(" and t.moduleid = " + Constant.ModuleType.EBANK);
        	strSQL.append(" and us.id = t.userid");
        	strSQL.append(" and t.userid = "+info.getUserID());
        }
		if (moduleID == Constant.ModuleType.SETTLEMENT )
		{
//modified by zwsun, 2007/6/27, 			
//			strSQL.append(" and t.moduleid = " + Constant.ModuleType.SETTLEMENT);
			strSQL.append(" and u.nofficeid = " + info.getOfficeID());//���ְ��´�
			strSQL.append(" and u.ncurrencyid = " + info.getCurrencyID());//���ֱ���
				
		}
		sql[2] = sql[2] + strSQL.toString();
		//	order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" \n order by t.notifydate" + strDesc);
		sql[3] = oBuf.toString();
		
		return sql;
			
	}
}
