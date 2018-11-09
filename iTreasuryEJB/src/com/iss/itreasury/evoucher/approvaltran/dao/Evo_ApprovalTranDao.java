/*
 * Created on 2006-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.approvaltran.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.evoucher.approvaltran.dataentity.Evo_ApprovalApplyInfo;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.printcontrol.dao.PrintApplyDao;
import com.iss.itreasury.evoucher.setting.dao.PrintDAO;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.*;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Evo_ApprovalTranDao extends PrintDAO
{
	public Evo_ApprovalTranDao()
	{
		super("print_printapply");
	}
	
	public Collection findSettConditions(long typeid,long officeid,long currencyid,long userid) throws VoucherException
	{
		Evo_ApprovalApplyInfo approvalapplyinfo = null;
		Collection coll = new ArrayList();
		String sql="";
		ResultSet rs = null;
		
		try{
			initDAO();
			sql =  " (SELECT c.*,-1 moneysegment,-1 approvalid,hhhh.countclient ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply c, ";
			
			/***************************************************/
			sql += " ( select count(nclientid) countclient,nclientid from ( ";
			sql += " (SELECT c.*,-1 moneysegment,-1 approvalid ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply c ";
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a, (select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b ";
			sql += " where (a.NNEXTUSERID="+userid;
			sql += " and a.NMODULEID="+Constant.ModuleType.EVOUCHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			
			sql += " where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+VOUConstant.VoucherStatus.APPROVALING+" ";
		    
			if ( typeid > 0 )
			{
				sql += " and c.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and c.nbillid = ss1.id and c.ntempid = ss2.id and ss1.id = ss2.nbillid ";
			
		    sql+=") union ( ";

			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply aa,loan_approvalrelation rr ";
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " where aa.ntypeid=rr.subloantypeid and aa.ntypeid=rr.actionid and rr.moduleid="+Constant.ModuleType.EVOUCHER+" and aa.nstatusid="+ VOUConstant.VoucherStatus.SAVE;
			
			if ( typeid > 0 )
			{
				sql += " and aa.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and aa.nbillid = ss1.id and aa.ntempid = ss2.id and ss1.id = ss2.nbillid ";
			
			sql += " ) aaa ";
			sql += " where 1=1 ) d,";
			sql += " loan_approvalsetting e,loan_approvalitem f ";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userid;
			sql += " ) ";
			sql += " ) hh where 1=1 group by nclientid ) hhhh ";
			/***************************************************/
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a, (select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b ";
			sql += " where (a.NNEXTUSERID="+userid;
			sql += " and a.NMODULEID="+Constant.ModuleType.EVOUCHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			
			sql += " where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+VOUConstant.VoucherStatus.APPROVALING+" ";
		    
			if ( typeid > 0 )
			{
				sql += " and c.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and c.nbillid = ss1.id and c.ntempid = ss2.id and ss1.id = ss2.nbillid and hhhh.nclientid = c.nclientid ";
			
		    sql+=") union ( ";

			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid,hhhh.countclient ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply aa,loan_approvalrelation rr, ";
			
			/***************************************************/
			sql += " ( select count(nclientid) countclient,nclientid from ( ";
			sql += " (SELECT c.*,-1 moneysegment,-1 approvalid ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply c ";
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a, (select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b ";
			sql += " where (a.NNEXTUSERID="+userid;
			sql += " and a.NMODULEID="+Constant.ModuleType.EVOUCHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			
			sql += " where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+VOUConstant.VoucherStatus.APPROVALING+" ";
		    
			if ( typeid > 0 )
			{
				sql += " and c.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and c.nbillid = ss1.id and c.ntempid = ss2.id and ss1.id = ss2.nbillid ";
			
		    sql+=") union ( ";

			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid ";
			
			//�������ݱ�ģ���
			sql += " ,ss1.sname,ss2.sDescription,ss2.NCOUPLETNO ";
			
			sql += " from print_printapply aa,loan_approvalrelation rr ";
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " where aa.ntypeid=rr.subloantypeid and aa.ntypeid=rr.actionid and rr.moduleid="+Constant.ModuleType.EVOUCHER+" and aa.nstatusid="+ VOUConstant.VoucherStatus.SAVE;
			
			if ( typeid > 0 )
			{
				sql += " and aa.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and aa.nbillid = ss1.id and aa.ntempid = ss2.id and ss1.id = ss2.nbillid ";
			
			sql += " ) aaa ";
			sql += " where 1=1 ) d,";
			sql += " loan_approvalsetting e,loan_approvalitem f ";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userid;
			sql += " ) ";
			sql += " ) hh where 1=1 group by nclientid ) hhhh ";
			/***************************************************/
			
			//�������ݱ�ģ���
			sql += " ,print_billsetting ss1,print_billtemplate ss2 ";
			
			sql += " where aa.ntypeid=rr.subloantypeid and aa.ntypeid=rr.actionid and rr.moduleid="+Constant.ModuleType.EVOUCHER+" and aa.nstatusid="+ VOUConstant.VoucherStatus.SAVE;
			
			if ( typeid > 0 )
			{
				sql += " and aa.ntypeid = "+typeid+" ";
			}
			
			//�������ݱ�ģ���
			sql += " and aa.nbillid = ss1.id and aa.ntempid = ss2.id and ss1.id = ss2.nbillid and hhhh.nclientid = aa.nclientid ";
			
			sql += " ) aaa ";
			sql += " where 1=1 ) d,";
			sql += " loan_approvalsetting e,loan_approvalitem f ";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userid;
			sql += " ) ";
			
			System.out.println("��ѯ���SQL^^^^^^^^^^^"+sql);
			
			prepareStatement(sql);
			rs = executeQuery();
			
			while(rs.next())
			{
				approvalapplyinfo = new Evo_ApprovalApplyInfo();
				
				long contentid = -1;
				String contentno = "";
				contentid = rs.getLong("nprintcontentid");  //����ID
				contentno = rs.getString("nprintcontentno");  //���ױ��
				long inputuserid = rs.getLong("ninputuserid");  //������
				long clientid = rs.getLong("nclientid");  //��Ա��λ��ʶ
				long lID = rs.getLong("id");
				long deptid = rs.getLong("ndeptid");  //��ӡ����ID
				
				approvalapplyinfo.setId(lID);
				approvalapplyinfo.setNofficeid(rs.getLong("nofficeid"));  //�������´�ID
				approvalapplyinfo.setNcurrency(rs.getLong("ncurrency"));  //����ID
				approvalapplyinfo.setNprintcontentid(contentid);
				approvalapplyinfo.setNprintcontentno(contentno);
				approvalapplyinfo.setNdeptid(deptid);  
				approvalapplyinfo.setNbillid(rs.getLong("nbillid"));  //����ID
				approvalapplyinfo.setNtempid(rs.getLong("ntempid"));  //����ģ��ID
				approvalapplyinfo.setNstatusid(rs.getLong("nstatusid"));  //״̬
				approvalapplyinfo.setIschapter(rs.getLong("ischapter"));  //�Ƿ�ǩ��
				approvalapplyinfo.setNclientid(clientid);  
				approvalapplyinfo.setNinputuserid(inputuserid);
				approvalapplyinfo.setNinputdate(rs.getTimestamp("ninputdate"));  //����ʱ��
				approvalapplyinfo.setNtypeid(rs.getLong("ntypeid"));  //ҵ������
				
				//���׽��
				approvalapplyinfo.setReceiveamount( findNprintcontentAmount(contentid,contentno,lID) );
				
				if ( deptid == VOUConstant.PrintSection.FINANCECOMPANY )  //����˾
				{
					approvalapplyinfo.setUsername( com.iss.itreasury.settlement.util.NameRef.getUserNameByID(inputuserid) );
					approvalapplyinfo.setClientname( Env.getClientName() );
				}
				else if ( deptid == VOUConstant.PrintSection.EBANKCUSTOMER )  //���Ͽͻ�
				{
					//���� UserID ȡ���û�����
					approvalapplyinfo.setUsername( com.iss.itreasury.ebank.util.NameRef.getUserNameByID(inputuserid) );
					//ͨ���ͻ�ID��ѯ�ͻ�����
					approvalapplyinfo.setClientname( com.iss.itreasury.ebank.util.NameRef.getClientNameByID(clientid) );
				}
				
				approvalapplyinfo.setBillname(rs.getString("sname"));
				approvalapplyinfo.setTempname(rs.getString("sDescription"));
				approvalapplyinfo.setCoupletno(rs.getLong("ncoupletno"));
				approvalapplyinfo.setCountclient(rs.getLong("countclient"));
				
				coll.add(approvalapplyinfo);
			}
		}catch(Exception ex)
		{
			throw new VoucherException();
		} 
		finally
		{
			try
			{
				if ( rs != null )
				{
					rs = null;
				}
				finalizeDAO();
			}
			catch(Exception es)
			{
				throw new VoucherException();
			}	
		}	
		return coll;
	}
	
	public double findNprintcontentAmount(long printcontentid,String printcontentno,long lID)throws VoucherException
	{
		double receiveamount = 0.0;  //���׽��տ��
		StringBuffer sfSQL = new StringBuffer();
		Connection con = null;
		ResultSet rss = null;
		PreparedStatement ps = null;
		
		try
		{
			sfSQL.append(" select b.receiveamount from print_printapply a,sett_vtransaction b ");
			sfSQL.append(" where a.nprintcontentid=b.id and b.id = "+printcontentid+" and b.transno = "+printcontentno+" ");
			sfSQL.append(" and a.id = "+lID+" ");
			
			con = Database.getConnection();
			
			System.out.println("��ѯ���׽�����SQL^^^^^^^^^^^"+sfSQL.toString());
			
			ps = con.prepareStatement(sfSQL.toString());
			rss = ps.executeQuery();
			
			if ( rss.next() )
			{
				receiveamount = rss.getDouble("receiveamount");
			}
		}
		catch(Exception ex)
		{
			throw new VoucherException();
		} 
		finally
		{
			try
			{
				rss.close();
				rss = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			catch(Exception es)
			{
				throw new VoucherException();
			}	
		}
		
		System.out.println("���׽��=="+receiveamount);
		return receiveamount;
	}
	
	/**
	 * ���ӻص�����������ʹ��  boxu add 2006-12-22
	 * ����ģ��ID��ҵ������ID��������ID������ѯ������������ID
	 * @param moduleid
	 * @param typeId
	 * @param subtypeId
	 * @return
	 */
	public long getApprovalId(long moduleid,long officeID,long currencyID,long actionId,long subtypeId){
		long lReturn=-1;
		try{
			initDAO();
			
			String sql=" select approvalid from loan_approvalrelation ";
			sql+=" where ";
			sql+=" officeID="+officeID+" and currencyid="+ currencyID +" and moduleid="+moduleid+" ";
			sql+=" and actionid="+actionId+" and subloantypeid = "+subtypeId+" ";
			sql+=" and approvalid in (select id from loan_approvalsetting where nstatusid=2 and nofficeid="+officeID+" and ncurrencyid="+ currencyID +" )";
			System.out.println("��ѯ������IDSQL======="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				lReturn=transRS.getLong("approvalid");
			}
				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
	
	/**
	 * ����������ID���û�ID������û�����������
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getLevelId(long approvalID,long userID){
		long lReturn=-1;
		try{
			initDAO();
			String sql="select nlevel from loan_approvalitem";
			sql+="  where napprovalid="+approvalID+" and nuserid="+userID+"";			
			log.print("��ѯ��������IDSQL====="+sql);
			System.out.println("��ѯ��������IDSQL====="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				lReturn=transRS.getLong("nlevel");
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
	
    /**
	 * �����������������֤��ҵ���¼��״̬,ͬʱ����������¼���¼
	 * ����ҵ�����
	 * @param lResultID �������ID
	 * @param tranTypeId ҵ������ID
	 * @param settID ҵ���¼ID
	 * @return
	 */
	public long updateDataStatusID(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//������¼״̬
			//�ȴ���������¼������
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//�����Ǿܾ�\�����޸�\����ͨ��\�����ɶ�����һ����¼
			lReturn = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//��˾ܾ�,�߼�ɾ��������¼�����е���˼�¼
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			/*��������������жϽ�����¼״̬*/
			if(lResultID==Constant.ApprovalDecision.PASS)  //���ͨ��,״̬Ϊ������
			{
				settStatusID=VOUConstant.VoucherStatus.APPROVALING;			
			}
			else if(lResultID==Constant.ApprovalDecision.REFUSE)  //�����ܾ���״̬Ϊɾ��
			{
				settStatusID=VOUConstant.VoucherStatus.REFUSE;		
			}
			else if(lResultID==Constant.ApprovalDecision.RETURN)  //�������أ�״̬Ϊδ����
			{
				settStatusID=VOUConstant.VoucherStatus.REFUSE;		
			}
			else if(lResultID==Constant.ApprovalDecision.FINISH)  //������ɣ�״̬Ϊ����
			{
				settStatusID=VOUConstant.VoucherStatus.APPROVED;
			}
			
			//����ҵ�����ͺ�������������½�����¼״̬��Ϣ
			System.out.println("^^^^^^^^^^^^^^^^"+tranTypeId);
			
			if( tranTypeId > 0 )
			{
				PrintApplyDao dao = new PrintApplyDao();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					//�޸�ԭ��¼��״̬
					lReturn = dao.updateApplyStatus( settID,settStatusID );
					System.out.println(settID+"^^^^^^^��˾ܾ�^^^^^^^"+settStatusID);
				}
				else
				{
					//�޸�ԭ��¼��״̬
					lReturn= dao.updateApplyStatus( settID,settStatusID );
					System.out.println(settID+"^^^^^^^^^^^^^^^^"+settStatusID);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
	
}
