/*
 * Created on 2004-2-17
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.bizlogic;

import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.ebank.obquery.dao.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.loan.extendapply.dataentity.* ;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo ;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBExtendQuery {
	
	public Collection queryExtend(OBQueryTermInfo qInfo) throws Exception
	{
		Collection c=null;
		OBQueryDao dao=new OBQueryDao();
		try
		{
			c=dao.queryExtend( qInfo );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e; 
		}
		return c;
	}
	public QuerySumInfo QueryExtendSum(OBQueryTermInfo qInfo) throws Exception
	{
		QuerySumInfo c=null;
		OBQueryDao dao=new OBQueryDao();
		try
		{
			c=dao.queryExtendSum( qInfo );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e; 
		}
		return c;		
	}
	public ExtendApplyInfo findExtendByID( long lID ) throws Exception
	{
		ExtendApplyInfo e_info = new ExtendApplyInfo() ;
		RepayPlanInfo r_info = new RepayPlanInfo() ;
		ExtendContractInfo ec_info = new ExtendContractInfo() ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		StringBuffer sb = new StringBuffer() ;
		String sql = "" ;
		ArrayList alist = new ArrayList() ;
		ArrayList alist2 = new ArrayList() ;
		double dTemp = 0 ;

		try
		{
			conn = Database.getConnection() ;
			sb.append(
				  "select aa.*,bb.SCONTRACTCODE,bb.nTypeID,bb.NLOANID"
				  + " from loan_ExtendForm aa,loan_ContractForm bb"
				  + " where aa.ID = ? "
				  + " and aa.NCONTRACTID = bb.ID " ) ;
			System.out.println( sb.toString() ) ;
			ps = conn.prepareStatement( sb.toString() ) ;
			ps.setLong( 1 , lID ) ;
			rs = ps.executeQuery() ;

			if ( rs.next() )
			{
				e_info.m_lContractID = rs.getLong( "NCONTRACTID" ) ; //�����ͬ��ʾ
				e_info.m_lPlanVersionID = rs.getLong( "NPLANVERSIONID" ) ; //����ID
				e_info.loanID = rs.getLong( "NLOANID" ) ; //����ID
				e_info.m_lSerialNo = rs.getLong( "NSERIALNO" ) ; //չ�����
				e_info.lLoanTypeID = rs.getLong( "NTYPEID" ) ;
				e_info.m_strExtendReason = rs.getString( "SEXTENDREASON" ) ; //չ��ԭ��
				e_info.m_strExtendReason = e_info.m_strExtendReason == null
					  ? "" : e_info.m_strExtendReason ;
				e_info.m_strReturnPostPend = rs.getString( "SRETURNPOSTPEND" ) ; //�黹���ڻ��Ϣ�ʽ�
				e_info.m_strReturnPostPend = e_info.m_strReturnPostPend == null
					  ? "" : e_info.m_strReturnPostPend ;
				e_info.m_strOtherContent = ( rs.getString( "SOTHERCONTENT" ) == null )
					  ? "" : rs.getString( "SOTHERCONTENT" ) ; //��������
					  //e_info.m_dInterestRate = Double.parseDouble( getTheRate( rs.
					  //getLong( "NBANKINTERESTID" ) , rs.getLong( "NCONTRACTID" ) ) ) ; //����
				e_info.m_dInterestRate = rs.getDouble( "MINTERESTADJUST" ) ; //ִ������
				e_info.m_lStatusID = rs.getLong( "NSTATUSID" ) ; //״̬
				e_info.m_lInputUserID = rs.getLong( "NINPUTUSERID" ) ; // չ��¼���˱�ʶ
				e_info.m_sInputUserName = getInputUserName( e_info.
					  m_lInputUserID ) ;
				e_info.m_tsInputDate = rs.getTimestamp( "DTINPUTDATE" ) ; // ¼������
				e_info.m_strContractCode = rs.getString( "SCONTRACTCODE" ) ;
				e_info.lBankRateTypeID = rs.getLong( "NBANKINTERESTID" ) ; //����ID
				e_info.m_dBasicInterestRate = rs.getDouble( "MINTERESTRATE" ) ; //��׼����
			}
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;
			sb.setLength( 0 ) ;

			// ��չ����ϸ -> RapayPlanInfo
			sql =
				  "select id,nPlanID,mPlanBalance,mExtendAmount,"
				  + " dtExtendBeginDate,dtExtendEndDate,NEXTENDINTERVALNUM,"
				  + " DTEXTENDBEGINDATE dtPlanEndDate from loan_ExtendDetail "
				  + " where NEXTENDFORMID = " + lID ;
			System.out.println( sql ) ;
			ps = conn.prepareStatement( sql ) ;
			rs = ps.executeQuery() ;
			while ( rs.next() )
			{
				r_info = new RepayPlanInfo() ;
				r_info.lExtendListID = rs.getLong( "ID" ) ;
				r_info.lID = rs.getLong( "NPLANID" ) ;
				r_info.dPlanBalance = rs.getDouble( "MPLANBALANCE" ) ;
				r_info.dAmount = rs.getDouble( "MEXTENDAMOUNT" ) ;
				r_info.tsExtendStartDate = rs.getTimestamp( "DTEXTENDBEGINDATE" ) ;
				r_info.tsExtendEndDate = rs.getTimestamp( "DTEXTENDENDDATE" ) ;
				r_info.lExtendPeriod = rs.getLong( "NEXTENDINTERVALNUM" ) ;
				r_info.tsPlanDate = rs.getTimestamp( "dtPlanEndDate" ) ;
				alist.add( r_info ) ;
			}
			e_info.cExtendList = alist ;
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;

			// ��չ�ں�ͬ -> ExtendContractInfo
			sql =
				  "select a.*,b.ID as ContractContentID,b.SDOCNAME "
				  + " from loan_extendcontract a,loan_contractcontent b"
				  + " where a.nextendid = " + lID
				  + " and b.nparentid(+) = a.nextendid "
				  + " and b.ncontractid(+) = " + e_info.m_lContractID
				  + " and b.ncontracttypeid(+) = "
				  + LOANConstant.ContractType.EXTEND
				  + " and a.nstatusid > " + Constant.RecordStatus.INVALID ;
			System.out.println( sql ) ;
			ps = conn.prepareStatement( sql ) ;
			rs = ps.executeQuery() ;
			if ( rs.next() )
			{
				ec_info = new ExtendContractInfo() ;
				ec_info.m_lExtendID = rs.getLong( "ID" ) ; //չ�ں�ͬID
				ec_info.m_lClientID = rs.getLong( "ContractContentID" ) ; //չ�ں�ͬ�ı�ID
				ec_info.m_lExtendApplyID = lID ; //չ�������ID
				ec_info.m_strExtendCode = rs.getString( "SCODE" ) ; //չ�ں�ͬ���
				e_info.m_sExCode = ec_info.m_strExtendCode ;
				ec_info.m_lStatusID = rs.getLong( "NSTATUSID" ) ; //չ�ں�ͬ״̬
				ec_info.m_lInputUserID = rs.getLong( "NINPUTUSERID" ) ; //¼��������
				ec_info.m_sInputUserName = getInputUserName( ec_info.
					  m_lInputUserID ) ; //¼��������
				ec_info.m_lCheckUserID = rs.getLong( "NNEXTCHECKUSERID" ) ; ////����������
				ec_info.m_sCheckUserName = getInputUserName( ec_info.
					  m_lCheckUserID ) ; //����������
				ec_info.m_tsExtendStart = rs.getTimestamp( "DTINPUTDATE" ) ; //¼��ʱ��
				ec_info.m_sDocName = rs.getString( "SDOCNAME" ) ;
				alist2.add( ec_info ) ;
			}
			e_info.cExtendContractList = alist2 ;
			rs.close() ;
			rs = null ;
			ps.close() ;
			ps = null ;

			conn.close() ;
			conn = null ;

		}
		catch ( Exception ex )
		{
			ex.printStackTrace() ;
			throw ex ;
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
				throw ex;
			}
		}
		return e_info ;

	}
	private static String getInputUserName( long lID ) throws Exception
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
			throw ex;
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
				throw ex ;
			}
		}
		return sResult ;
	}
	
}
