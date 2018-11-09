/*
 * Created on 2006-3-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.util.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;

import java.sql.*;

/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransChangeFixedDepositDAO extends SettlementDAO 
{
	//��Ҫ��Ϊ�˲�ѯ������������
	public final static int ORDERBY_TRANSACTIONNOID = 0;  //���׺�
	public final static int ORDERBY_ACCOUNTNO = 1;     //�����˻���
	public final static int ORDERBY_CLIENTNAME = 2;    //���ڿͻ�����
	public final static int ORDERBY_DEPOSITNO = 3;    //���ڴ��ݺ�
	public final static int ORDERBY_CURRENTACCOUNTNO = 4;    //�����˻���	
	public final static int ORDERBY_BILLNO = 5;    //Ʊ�ݺ�	
	public final static int ORDERBY_CONSIGNVOUCHERNO = 6;  //ί�и���ƾ֤��
	public final static int ORDERBY_BANKNAME = 7;    //������	
	public final static int ORDERBY_AMOUNT = 8;            //���
	public final static int ORDERBY_STARTDATE = 9;   //��ʼ��
	public final static int ORDERBY_ENDDATE = 10;     //������
	public final static int ORDERBY_STATUSID = 11;    //״̬	
	public final static int ORDERBY_ABSTRACT = 12;     //ժҪ
	
	
	public final static int ORDERBY_DEPOSITBILLNO = 13;    //�������ڴ浥��
	public final static int ORDERBY_DEPOSITBILLSTATUS = 14;    //�������ڴ浥��״̬ID
	//���￼���Ƿ�Ҫ���
	
	/**
	 * ��־���
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	/**
	 * �޸Ķ��ڣ�֪ͨ���������ڴ浥���׵ķ�����
	 * �߼�˵����
	 * 
	 * @param info, FixedOpenInfo, ���ڣ�֪ͨ����������ʵ����
	 * @return long, ���޸ļ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long update(TransFixedChangeInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			String strSQL="";
			
			buffer.append("update sett_TransOpenFixedDeposit set \n");
			
			//���Ӷ��ڴ浥��ķ���
			if(info.getDepositBillNO()!=null && !info.getDepositBillNO().equals(""))
				buffer.append(" sDepositBillNO='"+info.getDepositBillNO()+"',");
			
			if(info.getDepositBillStatusID()!=-1)
				buffer.append(" nDepositBillStatusID="+info.getDepositBillStatusID()+",");
			
			if(info.getDepositBillInputUserID()!=-1)
				buffer.append(" nDepositBillInputUserID="+info.getDepositBillInputUserID()+",");
			
			if(info.getDepositBillCheckUserID()!=-1)
				buffer.append(" nDepositBillCheckUserID="+info.getDepositBillCheckUserID()+",");
			
			
			if(info.getDepositBillInputDate()!=null){
				buffer.append(" dtDepositBillInputDate= to_date('"+DataFormat.getDateString(info.getDepositBillInputDate())+"','yyyy-mm-dd'),");
				System.out.println("�õ��Ļ������ڴ浥��������Ϊ:"+DataFormat.getDateString(info.getDepositBillInputDate()));
			}
			
			if(info.getDepositBillCheckDate()!=null){
				System.out.println("�õ��Ļ������ڴ浥��������Ϊ:"+DataFormat.getDateString(info.getDepositBillCheckDate()));
				buffer.append(" dtDepositBillCheckDate= to_date('"+DataFormat.getDateString(info.getDepositBillCheckDate())+"','yyyy-mm-dd'),");
			}
			
			if(info.getDepositBillABSTRACT()!=null && !info.getDepositBillABSTRACT().equals(""))
				buffer.append(" SDepositBillABSTRACT='"+info.getDepositBillABSTRACT()+"',");
			
			if(info.getDepositBillCHECKABSTRACT()!=null && !info.getDepositBillCHECKABSTRACT().equals(""))
				buffer.append(" SDepositBillCHECKABSTRACT='"+info.getDepositBillCHECKABSTRACT()+"',");
			
			buffer.append(" dtModify=sysdate,");
			
			log.info("==δ��ȡ�ַ�ǰ��SQL:���:"+buffer.toString()+".");
			strSQL=buffer.substring(0,buffer.length()-1);
			log.info("==��ȡ�����ַ����SQL:���:"+strSQL+".");

			strSQL=strSQL+" where ID=? ";

			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			//ID
			ps.setLong(1, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ��������ϸ�ķ�����
	 * �߼�˵����
	 * 
	 * @param lID long , ���׵�ID
	 * @return FixedOpenInfo, ���ڣ�֪ͨ������ʵ����
	 * @throws Exception
	 */
	public TransFixedChangeInfo findByID(long lID) throws Exception {
		TransFixedChangeInfo info = new TransFixedChangeInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
	 * ���ݽ��׺Ų�ѯ���ڣ�֪ͨ��������ϸ�ķ�����
	 * �߼�˵����
	 * 
	 * @param  strTransNo String , ���׺�
	 * @return FixedOpenInfo, ���ڣ�֪ͨ������ʵ����
	 * @throws Exception
	 */
	public TransFixedChangeInfo findByTransNo(String strTransNo) throws Exception {
		TransFixedChangeInfo info = new TransFixedChangeInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where sTransNo=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1,strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
		 * ����������ѯ���ݺ��Ƿ��ظ��ķ�����
		 * �߼�˵����
		 * 
		 * @param FixedOpenInfo, ���ڣ�֪ͨ������ʵ����
		 * @return boolean false �ظ�
		 * @throws Exception
		 */
		//��黻�����ڴ浥
		public boolean checkDepositNo(TransFixedChangeInfo info) throws Exception 
		{			
			Connection con = getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			boolean rtnFlg = true;
			try 
			{	
				//���ʱҪ�жϴ� �������ڴ浥��  �����ݿ����Ƿ��Ѿ�����
				
				StringBuffer buffer = new StringBuffer();
				/*
				buffer.append("select sDepositNo from sett_TransOpenFixedDeposit where \n");
				buffer.append("sDepositNo=? and nAccountID=? and ID<>? and \n");
				buffer.append("nStatusID <> ? \n");
				buffer.append("union select sDepositNo from sett_TransFixedContinue where \n");
				buffer.append("sNewDepositNo=? and nAccountID=? and \n");
				buffer.append("nStatusID <> ?");
				*/
				//��ͬ���˻��Ļ������ڴ浥�ſ�����ͬ
				buffer.append(" select sDepositBillNO from sett_TransOpenFixedDeposit where \n ");
				buffer.append(" sDepositBillNO=? and nAccountID=? and ID<>? \n");
				buffer.append(" and nStatusID <> ? \n");
				buffer.append(" and nDepositBillStatusID <> ? \n");
				buffer.append(" and NOFFICEID = ? \n");
				buffer.append(" and NCURRENCYID = ? \n");
				
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index=1;
				ps.setString(index++, info.getDepositBillNO());	//�������ڴ浥��
				ps.setLong(index++, info.getAccountID());		//���ڿ������˻�ID
				ps.setLong(index++, info.getID());				//���ڿ�����ID
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//ɾ��
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//ɾ��
				ps.setLong(index++, info.getOfficeID());	//���´�
				ps.setLong(index++, info.getCurrencyID());	//����
				
				rs = ps.executeQuery();
				if (rs.next()) 
				{
					rtnFlg=false;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			} 
			finally 
			{
				
				try 
				{
					cleanup(rs);
					cleanup(ps);
					cleanup(con);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					throw e;
				}
			}
			return rtnFlg;
		}
	
	/**
	 * �޸Ļ������ڴ浥����״̬�ķ�����
	 * �߼�˵����
	 * 
	 * @param lID, long, �����ױ�ʶ
	 * @param nDepositBillStatusID, long, ״̬��ʶ
	 * @return long, ���޸ļ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("ɾ���������ڴ浥----(��ʼDAO)");
			StringBuffer buffer = new StringBuffer();
			buffer.append(
				"update sett_TransOpenFixedDeposit" +
				" set nDepositBillStatusID=? ," +
				" NDEPOSITBILLINPUTUSERID = -1," +
				" NDEPOSITBILLCHECKUSERID = -1," +
				" DTDEPOSITBILLINPUTDATE = ''," +
				" DTDEPOSITBILLCHECKDATE = ''," +
				" SDEPOSITBILLABSTRACT = ''," +
				" SDEPOSITBILLCHECKABSTRACT = ''," +
				" SDEPOSITBILLNO=''" +
				"where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("ɾ���������ڴ浥----(����DAO)");
		return lReturn;
	}
	/**
	 * ���ö��ڽ��׽������ 
	 * �߼�˵����
	 * @throws Exception
	 */
	private TransFixedChangeInfo getOpenDeposit(
		TransFixedChangeInfo info,
		ResultSet rs)
		throws Exception {
		info = new TransFixedChangeInfo();
		try {
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setClientID(rs.getLong("nClientID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setDepositNo(rs.getString("sDepositNo"));
			info.setCertificationBankID(rs.getLong("nCertificationBankID"));
			info.setRate(rs.getDouble("mRate"));
			info.setStartDate(rs.getTimestamp("dtStart"));
			info.setEndDate(rs.getTimestamp("dtEnd"));
			info.setDepositTerm(rs.getLong("nDepositTerm"));
			info.setInterestPlanID(rs.getLong("nInterestPlanID"));
			info.setNoticeDay(rs.getLong("nNoticeDay"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setConsignVoucherNo(rs.getString("sConsignVoucherNo"));
			info.setConsignPassword(rs.getString("sConsignPassword"));
			info.setBillNo(rs.getString("sBillNo"));
			info.setBillTypeID(rs.getLong("nBillTypeID"));
			info.setBillBankID(rs.getLong("nBillBankID"));
			info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtBankNo(rs.getString("sExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			
			//���Ӷ��ڴ浥��ķ���
			info.setDepositBillNO(rs.getString("sDepositBillNO"));
			info.setDepositBillStatusID(rs.getLong("nDepositBillStatusID"));
			info.setDepositBillInputUserID(rs.getLong("nDepositBillInputUserID"));
			info.setDepositBillCheckUserID(rs.getLong("nDepositBillCheckUserID"));
			info.setDepositBillInputDate(rs.getTimestamp("dtDepositBillInputDate"));
			info.setDepositBillCheckDate(rs.getTimestamp("dtDepositBillCheckDate"));
			info.setDepositBillABSTRACT(rs.getString("SDepositBillABSTRACT"));
			info.setDepositBillCHECKABSTRACT(rs.getString("SDepositBillCHECKABSTRACT"));
			info.setIsAutoContinue(rs.getLong("IsAutoContinue"));
			info.setAutocontinuetype(rs.getLong("Autocontinuetype"));
			info.setAutocontinueaccountid(rs.getLong("Autocontinueaccountid"));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	
	/**
	 * ����״̬��ѯ�ķ�����
	 * �߼�˵����
	 * 
	 * @param QueryByStatusConditionInfo , ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����FixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * @throws Exception
	 */
	
	//--------------�˷�����Ҫ�о�һ��
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
	{
		System.out.println("�������ڴ浥(findByStatus):----------(��ʼDAO)");
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//״̬��ѯ����
			String query ="";
			if(info.getStatus()!=null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			
			//��������
			String order = getOrderString(info);			
			//ҵ����
			if (info.getTypeID() == 0) 
			{
				//����ʱ�Ĳ��Ҳ��ü����ڣ���Ϊ�йػ������ݴ���δ���˲�ȫ���Ϳ����ˣ�			
								
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nDepositBillInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedChangeInfo resultInfo = new TransFixedChangeInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //ҵ�񸴺�
				{
				//����ʱ�Ĳ��Ҳ��ü����ڣ���Ϊ�йػ������ݴ���δ���˲�ȫ���Ϳ����ˣ�				
				
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nDepositBillCheckUserID=? and DTDEPOSITBILLCHECKDATE=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedChangeInfo resultInfo = new TransFixedChangeInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("�������ڴ浥(findByStatus):----------(����DAO)");
		return arrResult;

	}
	
	//�ж�"���ڿ���"ҵ���Ƿ���"�������ڴ浥"���� add boxu 2007-9-3
	public boolean findByDepositBill(long lID, String transNO) throws Exception
	{
		boolean IsDepositBill = false;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer buffer = new StringBuffer();
		
			buffer.append(" select * \n");
			buffer.append(" from sett_TransOpenFixedDeposit \n");
			buffer.append(" where \n");
			buffer.append(" id=? \n");
			buffer.append(" and stransno=? and nTransActionTypeID="+SETTConstant.TransactionType.OPENFIXEDDEPOSIT+" \n");
			buffer.append(" and NDEPOSITBILLSTATUSID in ("+SETTConstant.TransactionStatus.SAVE+", "+SETTConstant.TransactionStatus.CHECK+") and sdepositbillno is not null \n");

			ps = con.prepareStatement(buffer.toString());
			
			int index = 1;
			ps.setLong(index++, lID);
			ps.setString(index++, transNO);		
			
			rs = ps.executeQuery();
			
			if(rs.next()) 
			{
				IsDepositBill = true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}

		return IsDepositBill;
	}
	
	//�õ���ѯ״̬��ϵ�����SQL
	private String getQueryString(QueryByStatusConditionInfo info) 
	{
		String query;
		query =" nDepositBillStatusID=";
		for(int i=0;i<info.getStatus().length;i++)
		{									
			if(i<info.getStatus().length -1)
			{	
								
				query= query+ info.getStatus()[i] + " or nDepositBillStatusID=";
			}
			else
			{
				query= query+ info.getStatus()[i];
			}
		}	
		return query;
	}
	
	//���������������SQL
	private String getOrderString(QueryByStatusConditionInfo info) 
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSACTIONNOID :
			{
				order=" ORDER BY sTransNo ";					
			}	
				break;
			case ORDERBY_ACCOUNTNO :
			{
				order=" ORDER BY nAccountID ";					
			}
				break;
			case ORDERBY_CLIENTNAME :
			{
				order=" ORDER BY nClientID ";					
			}
				break;				
			case ORDERBY_DEPOSITNO :
			{
				order=" ORDER BY sDepositNo ";					
			}
				break;
			case ORDERBY_CURRENTACCOUNTNO :
			{
				order=" ORDER BY nCurrentAccountID ";					
			}
				break;				
			case ORDERBY_CONSIGNVOUCHERNO :
			{
				order=" ORDER BY sConsignVoucherNo ";					
			}
				break;
			case ORDERBY_BILLNO :
			{
				order=" ORDER BY sBillNo ";					
			}
				break;	
			case ORDERBY_BANKNAME :
			{
				order=" ORDER BY nBankID ";					
			}
				break;			
			case ORDERBY_AMOUNT :
			{
				order=" ORDER BY mAmount ";					
			}
				break;
			case ORDERBY_STARTDATE :
			{
				order=" ORDER BY dtStart ";					
			}
				break;
			case ORDERBY_ENDDATE :
			{
				order=" ORDER BY dtEnd ";					
			}
				break;
			case ORDERBY_ABSTRACT :
			{
				order=" ORDER BY sAbstract ";					
			}
				break;
			case ORDERBY_STATUSID :
			{
				order=" ORDER BY nStatusID ";					
			}
				break;
			case ORDERBY_DEPOSITBILLNO :
			{
				order=" ORDER BY SDEPOSITBILLNO ";					
			}
				break;
			case ORDERBY_DEPOSITBILLSTATUS :
			{
				order=" ORDER BY NDEPOSITBILLSTATUSID ";					
			}
				break;
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if(isNeedOrderBy)
		{
			if (info.isDesc())
				order= order +" DESC \n";
			else
				order= order +" ASC \n";		
		}
		else
		{
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}
	/**
	 * ����ƥ��ķ�����
	 * �߼�˵����
	 * 
	 * @param FixedOpenInfo,���ڣ�֪ͨ�����׸���ƥ���ѯ����ʵ����
	 * @return Collection ,����FixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * @throws Exception
	 */
	//�������Ҳ��Ҳ��С�䶯
	public Collection match(TransFixedChangeInfo info) throws Exception {
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("-----�������ڴ浥����ƥ��--(��ʼDAO)");
			StringBuffer buffer = null;
				
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT) 
			{
				buffer = new StringBuffer();
				buffer.append("select * from sett_TransOpenFixedDeposit \n");
					//buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					//buffer.append("and nInputUserID <>? and nAccountID=? and \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and NDEPOSITBILLSTATUSID=? \n");
				buffer.append("and nDepositBillInputUserID <>? and nAccountID=? and \n");
				buffer.append("dtStart=? and nDepositTerm=? and mRate=? and \n");
				buffer.append("nCurrentAccountID=? \n");	
					//buffer.append("nCurrentAccountID=? and sConsignVoucherNo=?  \n");
				buffer.append("and nBankID=? and nCashFlowID=? and mAmount=?\n");
				buffer.append("and SDEPOSITBILLNO=? \n");
				buffer.append("order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				log.info("info.getOfficeID():"+info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				log.info("info.getCurrencyID():"+info.getCurrencyID());
					//ps.setLong(index++, info.getStatusID());
					//log.info("info.getStatusID():"+info.getStatusID());
					//ps.setLong(index++, info.getInputUserID());
					//log.info("info.getInputUserID():"+info.getInputUserID());
				ps.setLong(index++, info.getDepositBillStatusID());
				log.info("info.getDepositBillStatusID():"+info.getDepositBillStatusID());
				ps.setLong(index++, info.getDepositBillInputUserID());
				log.info("info.getDepositBillInputUserID():"+info.getDepositBillInputUserID());
				ps.setLong(index++, info.getAccountID());
				log.info("info.getAccountID():"+info.getAccountID());		//�����˺�ID
					//ps.setLong(index++, info.getClientID());
				ps.setTimestamp(index++, info.getStartDate());
				log.info("info.getStartDate():"+info.getStartDate());
				ps.setLong(index++, info.getDepositTerm());
				log.info("info.getDepositTerm():"+info.getDepositTerm());		//���ڴ������
				ps.setDouble(index++, info.getRate());
				log.info("info.getRate():"+info.getRate());						//���ڴ������
				ps.setLong(index++, info.getCurrentAccountID());
				log.info("info.getCurrentAccountID():"+info.getCurrentAccountID());		//�������˻�ID
					//ps.setString(index++, info.getConsignVoucherNo());
				ps.setLong(index++, info.getBankID());
				log.info("info.getBankID():"+info.getBankID());
				ps.setLong(index++, info.getCashFlowID());
				log.info("info.getCashFlowID():"+info.getCashFlowID());
				ps.setDouble(index++, info.getAmount());
				log.info("info.getAmount():"+info.getAmount());

				ps.setString(index++, info.getDepositBillNO());					//�������ڴ浥�ĺ�
				log.info("info.getDepositBillNO():"+info.getDepositBillNO());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransFixedChangeInfo depositInfo =
						new TransFixedChangeInfo();
					depositInfo = getOpenDeposit(depositInfo, rs);
					arrResult.add(depositInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {

				cleanup(rs);
				cleanup(ps);
				cleanup(con);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		System.out.println("-----�������ڴ浥����ƥ��--(����DAO)");
		return arrResult;
	}
	
	
	//�ҳ����ڿ��������˲��һ������ڴ浥��״̬Ϊ�ջ���Ϊ�����˵Ķ��ڿ������ݣ��û������ڴ浥����з�װ����
	public Collection findForOpenCheck(QueryByStatusConditionInfo qInfo) throws Exception {
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			//��������
			String order = getOrderString(qInfo);
			
			//nDepositBillStatusID�����ڶ��ڿ������ӵ�ʱ��Ӧ�þ�Ĭ��Ϊ-1  �����ݿ����default������Ϊ-1 ������û������,����ߺ�־ǿ2006.3.26
			
			
			//��ʵ��SQL��ѯ���
			//boxu 2007-11-15 update �������˻�,�����ѽ���浥����
			String strSQL =
				"select * from (select distinct a.* from sett_TransOpenFixedDeposit a, sett_subaccount b "
				+" where a.nStatusID=? and (a.nDepositBillStatusID is null  or a.nDepositBillStatusID= ? or a.nDepositBillStatusID= -1 )"
				+" and a.NTRANSACTIONTYPEID = ? "
				+" and a.NOFFICEID = ? "
				+" and a.NCURRENCYID = ? "
				+" and a.sdepositno = b.af_sdepositno "
				+" and b.nstatusid = "+SETTConstant.SubAccountStatus.NORMAL+" )"  //δ����
				+ ""+ order + "";
			
			ps = con.prepareStatement(strSQL);
			int index = 1;
			ps.setLong(index++, SETTConstant.TransactionStatus.CHECK);		//����
			ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//ɾ��
			ps.setLong(index++, qInfo.getTransactionTypeID());		//��������
			ps.setLong(index++, qInfo.getOfficeID());		//���´�
			ps.setLong(index++, qInfo.getCurrencyID());		//����
			
			rs = ps.executeQuery();
			Log.print("   --Log: "+strSQL);
			
			while (rs.next()) {
				TransFixedChangeInfo depositInfo =
					new TransFixedChangeInfo();
				depositInfo = getOpenDeposit(depositInfo, rs);
				arrResult.add(depositInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;
	}
	
	/**
	 * �޸Ļ������ڴ浥����״̬�ķ�����
	 * �߼�˵����
	 * 
	 * @param lID, long, �����ױ�ʶ
	 * @param nDepositBillStatusID, long, ״̬��ʶ
	 * @return long, ���޸ļ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long updateForCheck(TransFixedChangeInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("���˺�ȡ�����˻������ڴ浥  sett_subAccount----(��ʼDAO)");
			StringBuffer buffer = new StringBuffer();
			buffer.append(
				"update sett_subAccount" +
				" set AF_SDEPOSITBILLNO=? " +
				" where nAccountID=?" +
				" and AF_SDEPOSITNO=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setString(index++, info.getDepositBillNO());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			System.out.println("   ==========  SQL:"+buffer.toString());
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("���˺�ȡ�����˻������ڴ浥  sett_subAccount----(����DAO)");
		return lReturn;
	}
}
