package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.ContractdetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetialResultInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class ContractDetailDao extends ITreasuryDAO 
{
	private static Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER);
	
	public ContractDetailDao()
	{
		super("CRA_CONTRACTDETAIL","SEQ_CRA_CONTRACTDETAIL");
	}
	public ContractDetailDao(Connection conn)
	{
		super("CRA_CONTRACTDETAIL","SEQ_CRA_CONTRACTDETAIL",conn);
	}
	
	public ContractdetailInfo[] findContractdetailInfo(ContractdetailInfo info) throws Exception
	{
		
		StringBuffer strSQL = null;
		ContractdetailInfo[] contractdetailinfo=null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select a.* from cra_contractdetail a");
	        	strSQL.append(" where a.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID );
	        	strSQL.append(" and a.transfercontractformid = ?");
	        	
	        	prepareStatement(strSQL.toString());
				
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setString(nIndex++, String.valueOf(info.getTransfercontractformid()));
	        	
	        	executeQuery();
	        	
	        	Collection coll = getDataEntitiesFromResultSet(ContractdetailInfo.class);
	        	contractdetailinfo = new  ContractdetailInfo[coll.size()];
	        	
	        	Iterator it=coll.iterator();
	        	int i=0;
	        	while(it.hasNext())
	        	{
	        		contractdetailinfo[i]=(ContractdetailInfo)it.next();
	        		i++;
	        	}
	        	
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("ִ��select���ʧ��", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("���ݿ�����쳣",e);
			
		}
		finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return contractdetailinfo;
	}
	
	public void deldetailinfo(long id) throws Exception
	{

		long activeResult = -1;
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update cra_contractdetail A set A.Statusid = '"+CRAconstant.TransactionStatus.DELETED+"'" +
					"where 1 = 1 ");
			
			if(id!=-1)
			{
				buffer.append(" and a.transfercontractformid = "+id);
			}
			else
			{
				throw new IException ("����idΪ�գ����ܸ������ݣ�");
			}
			
			String sql = buffer.toString();
			
			log.info(sql);
			prepareStatement(sql);
			executeUpdate();
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			finalizeDAO();
		}
		
	
	}
	
	public double searchSellAmount(long payID) throws Exception
	{
		double returnamount=0.0;
		StringBuffer strSQL=null;
		ResultSet rs=null;
		try
		{
			initDAO();
			strSQL = new StringBuffer();
        	strSQL.append(" select sum(a.transferamount) sellamount  from cra_contractdetail a,CRA_TRANSFERCONTRACTFORM b");
        	strSQL.append(" where a.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID );
        	strSQL.append(" and b.statusid = "+CRAconstant.TransactionStatus.USED );
        	strSQL.append(" and b.transtypeid <> "+CRAconstant.CraTransactionType.BREAK_NOTIFY );//�������ϲ�ͳ��δת��
        	strSQL.append(" and a.transfercontractformid=b.id ");
			if(payID!=-1)
			{
				strSQL.append(" and a.loannoteid = "+payID);
			}
			else
			{
				throw new IException ("����payIDΪ�գ���ѯ����ʧ�ܣ�");
			}
			strSQL.append(" group by a.loannoteid ");
			log.info(strSQL.toString());
			prepareStatement(strSQL.toString());
			rs=executeQuery();
			while(rs.next())
			{
				returnamount=rs.getDouble("sellamount");
			}
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			finalizeDAO();
		}
		
		
		return returnamount;
	}
	
	/**
	 * ��ѯת�ú�ͬ��ϸ���Լ��������Ϣ�����λ���ƣ���Ӫ�����ͬ�ŵ���Ϣ
	 * 
	 * @param contractID
	 * @return
	 * @throws Exception
	 */
	public Collection queryContractDetailInfos(long contractID) throws Exception
	{
		try {
			initDAO();
			
			StringBuffer strSQL = null;
        	strSQL = new StringBuffer();
        	
        	strSQL.append(" select detail.*,\n");
			strSQL.append("        lc.scontractcode loanContractCode,\n");
			strSQL.append("        lp.scode loanNoticeCode,\n");
			strSQL.append("        c.sCode borrowClientCode,\n");
			strSQL.append("        c.id borrowClientId ,\n");
			strSQL.append("        c.sName borrowClientName\n");
			strSQL.append(" from cra_contractdetail detail,\n");
			strSQL.append(" loan_contractform  lc,\n");
			strSQL.append(" loan_payform       lp,\n");
			strSQL.append(" client             c\n");
			strSQL.append(" where 1 = 1\n");
			strSQL.append("       and detail.loancontractid = lc.id(+) \n");
			strSQL.append("       and detail.loannoteid = lp.id(+) \n");
			strSQL.append("       and lc.nborrowclientid = c.id \n");
			strSQL.append("       and detail.transfercontractformid = " + contractID + "\n");
			strSQL.append("       and detail.statusid = " + CRAconstant.TransactionType.counterpartBank.VALID  + "\n");
        	
        	prepareStatement(strSQL.toString());
			
        	executeQuery();
        	
        	return getDataEntitiesFromResultSet(ContractdetailInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("��ѯ�Ŵ��ʲ�ת�ú�ͬ��ϸ�����" + e.getMessage(),e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * ��ѯת�ú�ͬ��ϸ�Ѿ����տ�֪ͨ����ϸռ�õĽ��
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public double getRepayedNoticeContractdetailAmount(ContractAndNoticeDetialResultInfo conditionInfo,Connection con)throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = 0.0;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select sum(t.AMOUNT) sumAmount \n");
		sql.append(" from CRA_NOTICECONTRACTDETAIL t,cra_transfernoticeform t1 where  t1.id = t.NOTICEFORMID and t.CONTRACTDETAILID = ?   and t.officeid = ?  and t.currencyid = ? and t.STATUSID>0 and t1.STATUSID="+CRAconstant.TransactionStatus.APPROVALED+" \n");
		int index = 1;
		try {
			if(con==null)
			{
				con = Database.getConnection();
			}
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, conditionInfo.getContractDetailID());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			rs = ps.executeQuery();
			if(rs.next())
			{
				dReturn = rs.getDouble("sumAmount");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("��ѯװ�ú�ͬ�Ѿ����տ�֪ͨ����ϸռ�õĽ�����");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return dReturn;
	}
	
	/**
	 * 
	 * ��ĳһת�ú�ͬ��ϸ��ĳһ��֪ͨ����ϸ(�տ������յ�)�Ľ����кϼ�
	 * �úϼƿ�����ͳ��ת�ú�ͬ��ϸ�����տ���Ѵ��ս���
	 * 
	 * @param detailInfo
	 * @param noticeType  ֪ͨ������
	 * @param isUrrogatePay  �Ƿ����
	 * @param queryDate  ͳ�ƽ�ֹ����
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double sumNoticeFormAmount(
			ContractdetailInfo detailInfo, 
			long noticeType, 
			long isUrrogatePay,
			Date queryDate) throws ITreasuryDAOException
	{
		//��������������ﲻһ�����ʣ�Ҳ��һ����ͳ�����е�֪ͨ����Ϣ
		double dResult = 0.0;
		
		try{
			this.initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select sum(ndetail.amount) sumamount \n");
			sbSQL.append(" from CRA_NOTICECONTRACTDETAIL ndetail, cra_transfernoticeform nform \n");
			sbSQL.append(" where 1 = 1 \n");
			sbSQL.append("       and ndetail.contractdetailid = " + detailInfo.getId()+ " \n");
			sbSQL.append("       and ndetail.noticeformid = nform.id \n");
			sbSQL.append("       and ndetail.statusid = " + CRAconstant.TransactionStatus.SAVE + " \n");
			sbSQL.append("       and ndetail.officeid = " + detailInfo.getOfficeid() + " \n");
			sbSQL.append("       and ndetail.currencyid = " + detailInfo.getCurrencyid() +" \n");
			sbSQL.append("       and nform.statusid in ( " + CRAconstant.TransactionStatus.APPROVALED +","+CRAconstant.TransactionStatus.USED+ ") \n");			
			if(noticeType>0)
			{
				sbSQL.append("       and nform.noticetypeid = " + noticeType + " \n");
			}
			
			if(isUrrogatePay>0)
			{
				sbSQL.append("       and nform.isurrogatepay = " + isUrrogatePay + " \n");	
			}
			
			if(queryDate!=null)
			{
				sbSQL.append("       and to_char(nform.dtclearinterest,'yyyy-mm-dd')<='" + DataFormat.formatDate(queryDate, 1) + "' \n");
			}
			
			prepareStatement(sbSQL.toString());
			executeQuery();
			if(this.transRS.next())
			{
				dResult = this.transRS.getDouble("sumamount");
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
			throw new ITreasuryDAOException(exp.getMessage(), exp);
		}finally{
			this.finalizeDAO();
		}
		return dResult;
	}
	
	
	/**
	 * ��ѯת���Ӻ�ͬ����ת�ú�ͬ��ϸ�����ϴν�Ϣ��
	 * 
	 * @param contractDetailInfo
	 * @return
	 * @throws ITreasuryDAOException 
	 */
	public Timestamp getLastClearInterestDate(ContractdetailInfo contractDetailInfo) throws ITreasuryDAOException
	{
		Timestamp tsResult = null;
		
		try{
			initDAO();
			
			StringBuffer sql = new StringBuffer(); 
			
			sql.append(" select lastcleardate \n");
			sql.append("   from (select min(intereststart) lastcleardate \n");
			sql.append("           from sett_transferloandetail \n");
			sql.append("           where 1 = 1 \n");
			sql.append("           and contractdetailID = " + contractDetailInfo.getId() + " \n");
			sql.append("           and officeid = "+ contractDetailInfo.getOfficeid() + "\n");
			sql.append("           and currencyid = " + contractDetailInfo.getCurrencyid() + " \n");
			sql.append("           and STATUSID > 0 \n");
			sql.append("           and amount > 0 \n");
			sql.append("        union \n");
			sql.append("        select max(t.dtinterestsettlement) lastcleardate \n");
			sql.append("          from sett_transferintersetrecord t \n");
			sql.append("         where t.NINTERESTTYPE = " + SETTConstant.InterestFeeType.INTEREST + " \n");
			sql.append("           and t.statusid > 0 \n");
			sql.append("           and t.amount > 0 \n");
			sql.append("           and t.cracontractdetailid = " + contractDetailInfo.getId() + " \n");
			sql.append("         group by t.cracontractdetailid) \n");
			sql.append(" order by lastcleardate desc \n");
			
			prepareStatement(sql.toString());
			executeQuery();
			
			if(this.transRS.next())
			{
				tsResult = this.transRS.getTimestamp("lastcleardate");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("��ѯת�ú�ͬ��ϸ���ϴν�Ϣ�ճ���" + e.getMessage(), e);
		}
		finally
		{
			this.finalizeDAO();
		}
		return tsResult;
	}
}
