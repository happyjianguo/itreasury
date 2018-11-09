package com.iss.itreasury.loan.integratedCredit.customerfeedback.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.util.DataFormat; 
//import com.iss.itreasury.loan.assertgrant.ransom.dataentity.RansomQueryInfo;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dao.LoanCreditgradeInfoQueryDao;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgradeInfo;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class LoanCreditgradeInfoQueryBean {
private LoanCreditgradeInfoQueryDao detailDao = null;

	
	private long m_luserID = -1;

	public LoanCreditgradeInfoQueryBean() {

		detailDao = new LoanCreditgradeInfoQueryDao();
	}
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	
	
	
	
	/**
	 * ��ҳ��Ķ�������������в�ѯ
	 * @param info
	 * @return
	 * @author songjiahan
	 * @throws SettlementException
	 */
	public Collection findUploadfiles(LoanCreditgradeInfo info,long ctype,long userID)throws SettlementException
	{
		Collection res = null;
		ResultSet rs = null;
		Timestamp inputdatefrom = info.getInputdatefrom();
		Timestamp inputdateto = info.getInputdateto();
		LoanCreditgradeInfo info1 = null;
		System.out.println("Info: " + info);
		StringBuffer sql = new StringBuffer();
	
		
		try {
			System.out.println(" --------enter  selectbean--------");
			if (info != null ) {
				if(ctype==1)
				{
					sql.append(" select * from Loan_Creditgrade t, client c where t.id not in(select creditGrandID from loan_creditCheckAndRatify where status in(1,2,3,4,6)) and  c.id= t.clientid  and t.status !=3 ");
				}else
				{
					sql.append(" select * from Loan_Creditgrade t, client c  where c.id= t.clientid ");
				}
				sql.append( "and  t.INPUTUSERID="+userID );
			   if (info.getScode() != null && !info.getScode().equals("")) {
				   System.out.println("----------- getScode------" + info.getScode());
					sql.append("and c.Scode >= '" + info.getScode() + "' ");
				}
			   if (info.getScode1() != null && !info.getScode1().equals("")) {
				   System.out.println("----------- getScode1------" + info.getScode1());
					sql.append("and c.Scode <= '" + info.getScode1() + "' ");
				}
			   
			   
				System.out.println("------@ ----------" + sql);
				if(info.getCreditlevel()!="" && !"-1".equals(info.getCreditlevel()))
				{
					sql.append("and t.CREDITLEVEL='" + info.getCreditlevel() + "'");
               }
				if (info.getCreditcode() !=null && info.getCreditcode()!="") {
					sql.append("and t.CREDITCODE='" + info.getCreditcode() + "'");
				}
			   
				if(info.getStatus() >0){
					sql.append(" and t.status = '" + info.getStatus()+ "'");
				}
				
					if (info.getInputdatefrom() != null) {
						sql.append(" and t.inputdate >=to_date('"+DataFormat.getDateString(info.getInputdateto())+ "','yyyy-mm-dd') ");
					}
				
					if (info.getInputdateto() != null) {
						sql.append(" and t.inputdate <=to_date('"+DataFormat.getDateString(info.getInputdateto())+ "','yyyy-mm-dd') ");
					}
			}	
			else {
				if(ctype==1)
				{
				sql.append(" select * from loan_creditGrade where id not in(select creditGrandID from loan_creditCheckAndRatify where status in(1,2,3,4,6)) and status !=3  ");
				}else
				{
					sql.append("select * from Loan_Creditgrade t where status in(1,2,3)   ");	
				}
			}
			log.debug("��ӡsql --------------------"+sql);
			//res = detailDao.findByPrint(info, sql.toString());
			if(res!=null)
			{
				Iterator iterator = res.iterator();
				if(iterator.hasNext())
				{
					info = (LoanCreditgradeInfo) iterator.next();
				
					info.setInputdatefrom(inputdatefrom);
					info.setInputdateto(inputdateto);
				}
				
			}
			log.debug("����findByPrint����֮��--------"+res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		
		return res;

	}
	
	
	
	
	/**
	 *  �������û��ɣġ���ѯ��λ����
	 * @param userid
	 * @return songjiahan
	 * @throws SettlementException
	 * @throws SQLException 
	 */
	public LoanCreditgradeInfo queryclient(LoanCreditgradeInfo info) throws SettlementException, SQLException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		LoanCreditgradeInfo resinfo1 = new LoanCreditgradeInfo();
		String sql = "";
		try {
        	
			sql = "select sname, scode from client where  id = ? ";
			
		    
		
			System.out.println("=-------  print sql" + sql);
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, info.getClientid());
			rs = ps.executeQuery();

			while (rs.next()) {

				resinfo1.setSname(rs.getString("sname"));
                resinfo1.setScode(rs.getString("scode"));
                System.out.println("------6.30---------"  +  resinfo1.getScode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return resinfo1;
	
	}	
public long updateLoanStatus(long lID) throws SettlementException, SQLException {	
		PreparedStatement ps = null;
		Connection conn = null;
		long lResult=-1;
		String sql = "";
		try {
        	
			sql = " UPDATE LOAN_CREDITGRADE SET STATUS=4 WHERE ID="+lID+" ";
			System.out.println("=-------  print sql" + sql);
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			lResult=ps.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return lResult;
	} 



	/**
	 * ����pageloader����sql���� created by yuzhang @ 2008-11-27
	 */
	public String[] getQuerySQL(LoanCreditgradeInfo info,String laction){
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();
		//Ҫ��ѯ���ֶΡ�SELECT (sql[0]) FROM 
		//ע��INFO������ֱ������ֶ���ȫ��Ӧ
		sql[0] = /*SELECT*/
			"G.ID AS CREDITGRADEID,G.CREDITCODE,C.SCODE AS CLIENTCODE,C.SNAME AS CLIENTNAME," +
			"NVL(G.CREDITLEVEL,'') AS CREDITLEVEL,G.GRADEDATE,U.SNAME AS INPUTUSER,G.STATUS ";
		
		
		//Ҫ��ѯ�ı�SELECT ... FROM (sql[1])
		sql[1] = /*FROM*/
			"LOAN_CREDITGRADE G,CLIENT C,USERINFO U";
		
		
		//SELECT ... FROM ... WHERE (sql[2])
		if(laction.equals("modify"))
		{
			sql[2] = /*WHERE*/
				"C.ID = G.CLIENTID AND U.ID=G.INPUTUSERID AND G.STATUS IN (1,2) AND U.ID= " + info.getInputuserid() +
				" AND G.ID NOT IN (SELECT DISTINCT R.CREDITGRANDID FROM LOAN_CREDITCHECKANDRATIFY R WHERE R.STATUS NOT IN (0,5))";
		}
		if(laction.equals("search"))
		{
			sql[2] = /*WHERE*/
				"C.ID = G.CLIENTID AND U.ID=G.INPUTUSERID AND G.STATUS IN (1,2,3)";
		}
		//�޶�����
		if(info.getScode()!=null&&!"".equals(info.getScode()))
		{
			sqlBuf.append(" AND C.SCODE >='" + info.getScode() + "'");
		}
		if(info.getScode1()!=null&&!"".equals(info.getScode1()))
		{
			sqlBuf.append(" AND C.SCODE <='" + info.getScode1() + "'");
		}
		if(info.getCreditcode()!=null&&!"".equals(info.getCreditcode()))
		{
			sqlBuf.append(" AND G.CREDITCODE >='" + info.getCreditcode() + "'");
		}
		if(info.getCreditcode1()!=null&&!"".equals(info.getCreditcode1()))
		{
			sqlBuf.append(" AND G.CREDITCODE <='" + info.getCreditcode1() + "'");
		}
		if(info.getCreditlevel()!=null&&!"".equals(info.getCreditlevel())&&!"-1".equals(info.getCreditlevel()))
		{
			sqlBuf.append(" AND G.CREDITLEVEL ='" + info.getCreditlevel() + "'");
		}
		if(info.getInputdatefrom()!=null)
		{
			sqlBuf.append(" AND G.GRADEDATE >=to_date('" + info.getInputdatefrom().toString().substring(0,10) + "','YYYY-MM-DD')");
		}
		if(info.getInputdateto()!=null)
		{
			sqlBuf.append(" AND G.GRADEDATE <=to_date('" + info.getInputdateto().toString().substring(0,10) + "','YYYY-MM-DD')");
		}
		if(info.getStatus()>0)
		{
			sqlBuf.append(" AND G.STATUS =" + info.getStatus());
		}
		
	    sql[2] = sql[2] + sqlBuf.toString();
		//����������ORDER BY, GROUP BY����SELECT ... FROM ... WHERE ... (sql[3]) 
	    sql[3] = " ORDER BY G.CREDITCODE";  
	    log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
	
		return sql;
		
	}
	/**
	 * ����pageloader����ʼ�� created by yuzhang @ 2008-11-27
	 */
	public PageLoader getCreditGradeInfoList(LoanCreditgradeInfo info,String laction)throws Exception{
		String [] sql = getQuerySQL(info,laction);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			sql[1], sql[0], sql[2],
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.CreditGradeQueryInfo",
			null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
		
	}


}
