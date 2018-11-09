/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.obloanapply.dataentity.*;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obdao.*;
import java.util.*;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanPlanDao extends OBBaseDao
{
	private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

	/**
	 * ����һ��ִ�мƻ��汾
	 * @param pInfo  ִ�мƻ��汾��¼
	 * @return long
	 * @throws Exception
	 */	
	public long insert(OBLoanPlanInfo pInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		long    planID=-1;
		long    loanID=pInfo.getLoanID();
		long    planVersion=pInfo.getPlanVersion();
		long    inputUserID=pInfo.getInputUserID();
		//long    isUsed=pInfo.getIsUsed();
		long 	isUsed=2;
		long    userType=pInfo.getUserType();
        
		try
		{
			conn=Database.getConnection();
			/* ���Ȼ�øð汾��ID */
			strSQL="select nvl(max(ID)+1,1) nID from OB_Plan";
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				planID=rs.getLong("nID");
			}
			cleanup(rs);
			cleanup(ps);

			/* ִ�в������*/
			strSQL="Insert into ob_Plan("
				+"ID, nLoanID, nPlanVersion, nInputUserID, dtInput, "
				+"nStatusID, nIsUsed, nUserType "
				+") values(?, ?, 1, ?, SYSDATE, ?, ?, ? )";
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setLong(n++,planID);
			ps.setLong(n++,loanID);
			ps.setLong(n++,inputUserID);
			ps.setLong(n++,Constant.RecordStatus.VALID);
			ps.setLong(n++,isUsed);
			ps.setLong(n++,userType);
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			return planID;
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
	}
	/**
	 * ɾ���üƻ��汾����ϸ�ƻ���Ϣ
	 * @param vID   ���������ID
	 * @return  �ɹ�����1�����󷵻�-1
	 */
	public long delete(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long  vID=-1;
        
		try 
		{
			conn = Database.getConnection ();
			strSQL="select ID from ob_Plan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lID);
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				vID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			
			strSQL="delete from ob_PlanDetail where nPlanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,vID);
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);

		}catch(Exception e){
			e.printStackTrace ();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
		}
		return lResult;
	}
	/**
	 * ��ѯһ�ʴ��������ִ�мƻ��汾ID
	 * @param loanID ���������ʾ
	 * @return long
	 * @throws Exception
	 */	
	public long findVersionByLoanID(long lLoanID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;

		long planID=-1;

		try
		{
			conn=Database.getConnection();
			/*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
			strSQL="select ID from ob_Plan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return 0; 
			}
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return planID;
	}
	/**
	 * ����һ��ִ�мƻ��汾���Ҵ�������ID
	 * @param planID  ִ�мƻ��汾��¼ID
	 * @return long
	 * @throws Exception
	 */	
	public long findLoanIDByPlanVersion(long planID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;

		long loanID=-1;

		try
		{
			conn=Database.getConnection();
			/*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
			strSQL="select nLoanID from ob_Plan where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				loanID=rs.getLong("nLoanID");
			}
			cleanup(rs);
			cleanup(ps);
			if( loanID<0 )
			{
				cleanup(conn);
				log.info("can not find loanID for planVersion:"+planID);
				return 0; 
			}
		
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return loanID;
	}
	/**
	 * ��ѯ�ܷſ�����ܻ�����
	 * @param loanID  ��������ID
	 * @param payRepay �Ż�������
	 * @return double
	 * @throws Exception
	 */	
	public double findSumPlanAmount(long loanID,long payRepay) throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long planID=-1;
		double payAmount=0;
			
		try
		{
			conn=Database.getConnection();
			/*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
			strSQL="select ID from ob_Plan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,loanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+loanID);
				return 0; 
			}
			strSQL="select sum(mAmount) from ob_PlanDetail where nPlanID=? and nPayTypeID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			ps.setLong(2,payRepay);
			
			rs=ps.executeQuery();
			if ( rs.next())
			{
				payAmount=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		}
		return payAmount;
		
	}
	/**
	 * ��ѯ�ſ�ͻ��������Ŀ��
	 * @param loanID  ��������ID
	 * @return long
	 * @throws Exception
	 */		
	public long findCountByLoanID(long lLoanID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long planID=-1;

		try
		{
			conn=Database.getConnection();
			/*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
			strSQL="select ID from ob_Plan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return 0; 
			}
			/*�����ϸ�ƻ��ĸ���*/
			strSQL="select count(*) from ob_PlanDetail where nPlanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				lResult=rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return lResult;
	}
	/**
	 * ��ѯִ�мƻ���ϸ
	 * @param loanID  ��������ID
	 * @param pInfo ��ҳ��Ϣ
	 * @return Collection
	 * @throws Exception
	 */		
	public Collection findByLoanID(long lLoanID,OBPageInfo pInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long lPageNo=pInfo.getPageNo();
		long lPageLine=pInfo.getPageLineCount();
		long lOrderParam=pInfo.getOrderParam() ;
		long lDesc=pInfo.getDesc() ;
        
		Vector v=null;
		long    planID=-1;

        
		try
		{
			conn=Database.getConnection();
			/*���Ȼ�ø�loanID��Ӧ�ļƻ��汾*/
			strSQL="select ID from ob_Plan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return null; 
			}
            
			/*��֯��ѯ����*/
			strSQL="select * from ob_PlanDetail where nPlanID="+planID;
			switch ((int) lOrderParam)
			{
				case 1 : //���ƻ���������
					strSQL += " order by dtPlanDate ";
					break;
				case 2 : //��������
					strSQL += " order by nPayTypeID ";
					break;    
				case 3 : //�����
					strSQL += " order by mAmount ";
					break;    
				case 6 : //���޸���������
					strSQL += " order by dtModifyDate ";
					break;    
				default :
					strSQL += " order by nPayTypeID,dtPlanDate";
			}
			System.out.println((int)lOrderParam);
			//�ж��������ǽ���������ϵͳĬ�ϵģ�������desc
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
            
			String strSQLRecordCount = "";
			strSQLRecordCount = "select count(*) nCount from (  " + strSQL + ") aa";
			System.out.println(strSQLRecordCount);
			ps = conn.prepareStatement(strSQLRecordCount);
			rs = ps.executeQuery();
			long lRecordCount = 0; //��¼��Ŀ
			if (rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
            
			//������ҳ��
			long lPageCount = lRecordCount / lPageLine;
			if ((lRecordCount % lPageLine) != 0)
			{
				lPageCount++;
			}
			//��ѯ��¼
			String strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + strSQL;
			strSQLRecord = strSQLRecord + " ) aa ) where r between ? and ? ";
			long lRowNumStart = (lPageNo - 1) * lPageLine + 1;
			long lRowNumEnd = lRowNumStart + lPageLine - 1;
			Log.print(strSQLRecord);
			ps = conn.prepareStatement(strSQLRecord);
			ps.setLong(1, lRowNumStart);
			ps.setLong(2, lRowNumEnd);
			rs = ps.executeQuery();
			v=new Vector();
            
			while (rs.next())
			{
				OBLoanPlanDetailInfo dInfo=new OBLoanPlanDetailInfo();
				dInfo.setID(rs.getLong("ID"));
				dInfo.setPlanID(rs.getLong("nPlanID"));
				dInfo.setPlanDate(rs.getTimestamp("dtPlanDate"));
				dInfo.setPayTypeID(rs.getLong("nPayTypeID"));
				dInfo.setAmount(rs.getDouble("mAmount"));
				dInfo.setType(rs.getString("sType"));
				dInfo.setModifyDate(rs.getTimestamp("dtModifyDate"));
				dInfo.setLastExtendID(rs.getLong("nLastExtendID"));
				dInfo.setLastOverdueID(rs.getLong("nLastOverdueID"));
				dInfo.setLastVersionPlanID(rs.getLong("nLastVersionPlanID"));
				v.add(dInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
       
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v;
	}
	/**
	 * �Զ����ŷŻ���ƻ�
	 * @param apInfo �Զ�������Ϣ
	 * @return long
	 * @throws Exception
	 */	    
	public long  autoSavePlanDetail(OBAutoPlanInfo apInfo) throws Exception
	 {
		   //ȡ�ô������ʼʱ��
		   int nNum = 0;  
		   double dTotal = 0;
		   double dLastTime = 0;
		   long dEach = 0;
		   long lResult = 0;
		   long lVersionID = 0;

		   long lLoanID=apInfo.getLLoanID();
		   long nPayType=apInfo.getNPayType();
		   Timestamp tsPayStart=apInfo.getTsPayStart() ;
		   Timestamp tsPayEnd=apInfo.getTsPayEnd() ;
		   long nRepayType=apInfo.getNRepayType() ;
		   Timestamp tsRepayStart=apInfo.getTsRepayStart(); 
		   Timestamp tsRepayEnd=apInfo.getTsRepayEnd();
		   long nSourceType=apInfo.getNSourceType() ;

		   Connection con = null;
		   PreparedStatement ps = null;
		   ResultSet rs = null;
           
		   OBLoanPlanDetailDao pdDao=null;
		   OBLoanPlanDetailInfo pdInfo=null;
		   try
		   {
			   con = Database.getConnection();
			   StringBuffer sb = new StringBuffer();
               
			   //ȡ��������
			   sb.append("SELECT mLoanAmount FROM ob_loan WHERE ID = ?");
			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1,lLoanID);
			   rs = ps.executeQuery();
			   if (rs.next())
			   {
				   dTotal = rs.getDouble("mLoanAmount");
				   System.out.println("dTotal is " + dTotal);
			   }
			   cleanup(rs);
			   cleanup(ps);
			   sb.setLength(0);

			   sb.append("select ID from ob_Plan where nLoanID=?"); 
			   ps = con.prepareStatement(sb.toString());
			   ps.setLong(1,lLoanID);
			   rs = ps.executeQuery();
			   if (rs.next())
			   {
				   lVersionID = rs.getLong("ID");
				   System.out.println("lVersionID is " + lVersionID);
			   }
			   System.out.println("Get version ID"+lVersionID);
			   cleanup(rs);
			   cleanup(ps);
			   cleanup(con);
			   sb.setLength(0);
			   if ( lVersionID<=0 )
			   {
					log.error("Can not find plan version for apply"+lLoanID);
					return -1;
			   }
 
			   int nAdd = 0;
			   pdDao=new OBLoanPlanDetailDao();
			   if (nPayType != LOANConstant.PayType.NOTUSE ) 
			   {
				   if ( nPayType == LOANConstant.PayType.ONETIME) //һ���Էſ�
				   {
						pdInfo=new OBLoanPlanDetailInfo();
						pdInfo.setID(-1);
						pdInfo.setPlanID(lVersionID);
						pdInfo.setPayTypeID(LOANConstant.PlanType.PAY);
						pdInfo.setPlanDate(tsPayStart);
						pdInfo.setType("����");
						pdInfo.setAmount(dTotal);
						pdInfo.setLastExtendID(-1);
						pdInfo.setLastOverdueID(-1);
						pdInfo.setLastVersionPlanID(-1);
						
						lResult=pdDao.insert(pdInfo);
						pdInfo=null;
					 								
				   }
				   else
				   {
						ArrayList payDateList=new ArrayList();
						while (!tsPayStart.after(tsPayEnd)|| tsPayStart.toString().substring(1,10).equals(tsPayEnd.toString().substring(1,10)))
						{
							payDateList.add(tsPayStart); 
							nNum++;
							switch ( (int)nPayType )
							{
								case  (int)LOANConstant.PayType.YEAR  :     //��
									tsPayStart = DataFormat.getNextMonth(tsPayStart,12);
									break;
								case  (int)LOANConstant.PayType.HALFYEAR  :     //����
									tsPayStart = DataFormat.getNextMonth(tsPayStart,6);
									break;
								case (int)LOANConstant.PayType.QUARTOR  :     //��
									tsPayStart = DataFormat.getNextMonth(tsPayStart,3);
									break;
								case  (int)LOANConstant.PayType.MONTH :    //��
									tsPayStart = DataFormat.getNextMonth(tsPayStart,1);
									break;
								case  (int)LOANConstant.PayType.WEEK :    //��
									tsPayStart = DataFormat.getNextDate(tsPayStart,7);
									break;
								case  (int)LOANConstant.PayType.DAY  :   //��
									tsPayStart = DataFormat.getNextDate(tsPayStart,1);
									break;
							}
						}
						dEach = (long)dTotal/nNum;
						dLastTime = dTotal - dEach*(nNum-1);
						int n=payDateList.size();
						for ( int i=0;i<n;i++ )
						{
							pdInfo=new OBLoanPlanDetailInfo();
							pdInfo.setPlanID(lVersionID);
							pdInfo.setPayTypeID(LOANConstant.PlanType.PAY );
							pdInfo.setPlanDate((Timestamp)payDateList.get(i));
							pdInfo.setType("����");
							pdInfo.setLastExtendID(-1);
							pdInfo.setLastOverdueID(-1);
							pdInfo.setLastVersionPlanID(-1);

							if ( i==( n-1 ) )
							{
								pdInfo.setAmount(dLastTime);
							}
							else
							{
								pdInfo.setAmount(dEach);
							}
							lResult=pdDao.insert(pdInfo);
							pdInfo=null;
						}
				   }
			   }
			   // �ſʼ 
			   nNum = 0;
			   dEach = 0;
			   dLastTime = 0;
			   if (nRepayType != LOANConstant.RepayType.NOTUSE) 
			   {
				   if (nRepayType == LOANConstant.RepayType.ONETIME) //һ���Էſ�
				   {
					   pdInfo=new OBLoanPlanDetailInfo();
					   pdInfo.setPlanID(lVersionID);
					   pdInfo.setPayTypeID(LOANConstant.PlanType.REPAY );
					   pdInfo.setPlanDate(tsRepayStart);
					   pdInfo.setType("����");
					   pdInfo.setAmount(dTotal);
						pdInfo.setLastExtendID(-1);
						pdInfo.setLastOverdueID(-1);
						pdInfo.setLastVersionPlanID(-1);

					   lResult=pdDao.insert(pdInfo);
					   pdInfo=null;
				   }
				   else
				   {
						ArrayList repayDateList=new ArrayList();
						while (!tsRepayStart.after(tsRepayEnd) || tsRepayStart.toString().substring(1,10).equals(tsRepayEnd.toString().substring(1,10)))
						{
							repayDateList.add(tsRepayStart);
							nNum++;
							switch ((int)nRepayType)
							{
								case (int)LOANConstant.RepayType.YEAR  :     //��
									tsRepayStart = DataFormat.getNextMonth(tsRepayStart,12);
									break;
								case (int)LOANConstant.RepayType.HALFYEAR  :     //����
									tsRepayStart = DataFormat.getNextMonth(tsRepayStart,6);
									break;
								case (int)LOANConstant.RepayType.QUARTOR  :     //��
									tsRepayStart = DataFormat.getNextMonth(tsRepayStart,3);
									break;
								case  (int)LOANConstant.RepayType.MONTH:    //��
									tsRepayStart = DataFormat.getNextMonth(tsRepayStart,1);
									break;
								case  (int)LOANConstant.RepayType.WEEK :    //��
									tsRepayStart = DataFormat.getNextDate(tsRepayStart,7);
									break;
								case  (int)LOANConstant.RepayType.DAY  :   //��
									tsRepayStart = DataFormat.getNextDate(tsRepayStart,1);
									break;
							}
						}
					   dEach = (long)dTotal/nNum;
					   dLastTime = dTotal - dEach*(nNum-1);
					   int n=repayDateList.size();
					   for ( int i=0;i<n;i++ )
					   {
						    pdInfo=new OBLoanPlanDetailInfo();
						    pdInfo.setPlanID(lVersionID);
						    pdInfo.setPayTypeID(LOANConstant.PlanType.REPAY  );
						    pdInfo.setPlanDate((Timestamp)repayDateList.get(i));
						    pdInfo.setType("����");
							pdInfo.setLastExtendID(-1);
							pdInfo.setLastOverdueID(-1);
							pdInfo.setLastVersionPlanID(-1);

						   if ( i==( n-1 ) )
						   {
							   pdInfo.setAmount(dLastTime);
						   }
						   else
						   {
							   pdInfo.setAmount(dEach);
						   }
						   lResult=pdDao.insert(pdInfo);
						   pdInfo=null;
					   }
				   }
			   }
		   }
		   catch(Exception e)
		   {
				e.printStackTrace();
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
				 throw e;
		   }finally{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
		   }
		   return lResult;
        
	}

}
                                                                                                                                                                                                                                                                                                 