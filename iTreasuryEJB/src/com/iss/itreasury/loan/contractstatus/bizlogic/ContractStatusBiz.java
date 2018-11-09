package com.iss.itreasury.loan.contractstatus.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientmanageBiz;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contractstatus.dao.ContractStatusDao_New;
import com.iss.itreasury.loan.contractstatus.dataentity.ContractStatusInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;


public class ContractStatusBiz {

	ContractStatusDao_New contractStatusDao_New =new ContractStatusDao_New();
	/**
	 * �����ͬ״̬���biz��
	 * @author issuser
	 *
	 */
	public PagerInfo queryContractStatus(
			 long lCurrencyID,
			 long lOfficeID,
			 long lUserID,
			 long lActionID,
			 Timestamp tsDateFrom ,
	         Timestamp tsDateTo ,
			 long lStatusID) throws Exception{
		   
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = contractStatusDao_New.queryContractStatusSQL(lCurrencyID,lOfficeID,lUserID,lActionID,tsDateFrom,tsDateTo,lStatusID);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ContractStatusBiz.class, "resultSetHandle2");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
		public ArrayList resultSetHandle2(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		ContractInfo cInfo = new ContractInfo();
		ContractDao contractDao = new ContractDao() ;
		ContractStatusInfo info = new ContractStatusInfo() ;
		 
		long lID = -1;
		long lContractID = -1;
		long m_lStatusID = -1;//��ͬ״̬
		String ContractCode = null;//��ͬ���
		String BorrowClientName = null;//��λ
		String ClientName = null;//ί�е�λ
		long m_sInputUserName = -1;//ҵ������
		String Status = "";//���״̬
		double ExamineAmount = 0.00;//������ 
		double BalanceAmount = 0.00;//�������
		Timestamp m_dtInputDate =null;//�������
		
		
		try {
			if(rs!=null)
			{
			while(rs.next())
			{
					lID = rs.getLong( "ID" ) ;
	                lContractID = rs.getLong( "nContractID" ) ;
	                cInfo = contractDao.findByID( lContractID ) ;
	                
	                ContractCode = cInfo.getContractCode();
	                BorrowClientName =cInfo.getBorrowClientName() ;
	                ClientName = cInfo.getClientName();
	                ExamineAmount = cInfo.getExamineAmount();
	                BalanceAmount = cInfo.getAInfo().getBalanceAmount();
	                Status = cInfo.getStatus();
	                
	                m_dtInputDate = rs.getTimestamp( "dtInputDate" ) ;
	                m_sInputUserName = rs.getLong("NINPUTUSERID" );
	                m_lStatusID = rs.getLong( "nStatusID" ) ;
						
					//�洢������
					cellList = new ArrayList();
				    PagerTools.returnCellList(cellList,ContractCode+","+lContractID+","+ContractCode+","+m_lStatusID);
					PagerTools.returnCellList(cellList,BorrowClientName);
					PagerTools.returnCellList(cellList,ClientName);
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(ExamineAmount));
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(BalanceAmount));
					PagerTools.returnCellList(cellList,Status);
					PagerTools.returnCellList(cellList,DataFormat.formatDate(m_dtInputDate));
					PagerTools.returnCellList(cellList,getInputUserName(m_sInputUserName));
					PagerTools.returnCellList(cellList,LOANConstant.RiskModifyStatus.getName(m_lStatusID));
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
					resultList.add(rowInfo);
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
		private static String getInputUserName( long lID ) throws RemoteException
	    {
	        String sResult = "" ;
	        Connection conn = null ;
	        PreparedStatement ps = null ;
	        ResultSet rs = null ;
	        StringBuffer sb = new StringBuffer() ;

	        try
	        {
	            conn = Database.getConnection() ;
	            sb.append( "select * from USERINFO where ID = " + lID ) ;
	            System.out.println( sb.toString() ) ;
	            ps = conn.prepareStatement( sb.toString() ) ;
	            rs = ps.executeQuery() ;
	            //log4j.info("came here =--------------");
	            if ( rs.next() )
	            {
	                sResult = rs.getString( "SNAME" ) ;
	            }
	            ps.close() ;
	            ps = null ;
	            sb.setLength( 0 ) ;
	            //log4j.info("came here =--------------");
	        }
	        catch ( Exception ex )
	        {
	            ex.printStackTrace() ;
	            throw new RemoteException( ex.getMessage() ) ;
	        }
	        finally
	        {
	            try
	            {
	                if ( rs != null )
	                {
	                    rs.close() ;
	                    rs = null ;
	                }
	                if ( ps != null )
	                {
	                    ps.close() ;
	                    ps = null ;
	                }
	                if ( conn != null )
	                {
	                    conn.close() ;
	                    conn = null ;
	                }

	            }
	            catch ( Exception ex )
	            {
	                throw new RemoteException( ex.getMessage() ) ;
	            }
	        }
	        return sResult ;
	    }
}
