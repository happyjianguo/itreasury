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
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obloanapply.dataentity.*;
import java.util.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanAssureDao extends OBBaseDao
{

	private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

	/**
	 * 新增一笔保证信息纪录
	 * @param aInfo  保证信息
	 * @return long
	 * @throws Exception
	 */
	public long insert(OBAssureInfo aInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		long aID=-1;
		long loanID=aInfo.getLoanID();
		long assureTypeID=aInfo.getAssureTypeID();
		long fillQuestionary=aInfo.getFillQuestionary();
		long clientID=aInfo.getClientID();
		double amount=aInfo.getAmount();
		String impawName=aInfo.getImpawName();
		String impawQuality=aInfo.getImpawQuality();
		String impawStatus=aInfo.getImpawStatus();
		double pledgeAmount=aInfo.getPledgeAmount();
		double pledgeRate=aInfo.getPledgeRate();
		String assureCode=aInfo.getAssureCode();
        
		try
		{
			conn=Database.getConnection();
			/*首先获得ID*/
			strSQL="select nvl(max(ID)+1,1) nID from OB_Assure";
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				aID=rs.getLong("nID");
			}
			cleanup(rs);
			cleanup(ps);

			/*插入操作*/
			strSQL="insert into OB_Assure("
				+"ID, nLoanID, nAssureTypeID, nFillQuestionary, nClientID, "
				+"mAmount, sImpawName, sImpawQuality, sImpawStatus, mPledGeAmount, "
				+"mPledGeRate, nStatusID, sAssureCode) "
				+"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setLong(n++,aID);
			ps.setLong(n++,loanID);
			ps.setLong(n++,assureTypeID);
			ps.setLong(n++,fillQuestionary);
			ps.setLong(n++,clientID);
			ps.setDouble(n++,amount);
			ps.setString(n++,impawName);
			ps.setString(n++,impawQuality);
			ps.setString(n++,impawStatus);
			ps.setDouble(n++,pledgeAmount);
			ps.setDouble(n++,pledgeRate);
			ps.setLong(n++,Constant.RecordStatus.VALID);
			ps.setString(n++,assureCode);
            
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			return aID;
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
	 * 更新一笔保证信息
	 * @param aInfo  保证信息
	 * @return long
	 * @throws Exception
	 */	
	public long update(OBAssureInfo aInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		long aID=aInfo.getID();
		long loanID=aInfo.getLoanID();
		long assureTypeID=aInfo.getAssureTypeID();
		long fillQuestionary=aInfo.getFillQuestionary();
		long clientID=aInfo.getClientID();
		double amount=aInfo.getAmount();
		String impawName=aInfo.getImpawName();
		String impawQuality=aInfo.getImpawQuality();
		String impawStatus=aInfo.getImpawStatus();
		double pledgeAmount=aInfo.getPledgeAmount();
		double pledgeRate=aInfo.getPledgeRate();
		long statusID=aInfo.getStatusID();
		String assureCode=aInfo.getAssureCode();
        
		try
		{
			conn=Database.getConnection(); 
			strSQL="update OB_Assure set "
				+"nLoanID=?, nAssureTypeID=?, nFillQuestionary=?, "
				+"nClientID=?, mAmount=?, sImpawName=?, sImpawQuality=?, "
				+"sImpawStatus=?, mPledgeAmount=?, mPledgeRate=?, "
				+"nStatusID=?, sAssureCode=? where ID=?";
                
                
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setLong(n++,loanID);
			ps.setLong(n++,assureTypeID);
			ps.setLong(n++,fillQuestionary);
			ps.setLong(n++,clientID);
			ps.setDouble(n++,amount);
			ps.setString(n++,impawName);
			ps.setString(n++,impawQuality);
			ps.setString(n++,impawStatus);
			ps.setDouble(n++,pledgeAmount);
			ps.setDouble(n++,pledgeRate);
			ps.setLong(n++,statusID);
			ps.setString(n++,assureCode);
			ps.setLong(n++,aID);
            
           
			lResult=ps.executeUpdate();
            
			cleanup(ps);
			cleanup(conn);
			return aID;
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
	 * 删除一笔保证信息纪录
	 * @param aID  保证信息ID
	 * @return long
	 * @throws Exception
	 */	
	public long delete(long aID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		try 
		{
			conn = Database.getConnection ();
			strSQL="delete from OB_Assure where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,aID);
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			return 1;

		}catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace ();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
		}
	}
	/**
	 * 查找一笔保证信息纪录
	 * @param aID  保证信息ID
	 * @return OBAssureInfo
	 * @throws Exception
	 */
	public OBAssureInfo findByID(long aID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		OBAssureInfo aInfo=null;
        
		try 
		{
			conn = Database.getConnection ();
			strSQL="select * from OB_Assure where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,aID);
			rs=ps.executeQuery();
            
			if ( rs.next() )
			{
				aInfo=new OBAssureInfo();
				aInfo.setID(rs.getLong("ID"));
				aInfo.setLoanID(rs.getLong("nLoanID"));
				aInfo.setAssureTypeID(rs.getLong("nAssureTypeID"));
				aInfo.setFillQuestionary(rs.getLong("nFillQuestionary"));
				aInfo.setClientID(rs.getLong("nClientID"));
				aInfo.setAmount(rs.getDouble("mAmount"));
				aInfo.setImpawName(rs.getString("sImpawName"));
				aInfo.setImpawQuality(rs.getString("sImpawQuality"));
				aInfo.setImpawStatus(rs.getString("sImpawStatus"));
				aInfo.setPledgeAmount(rs.getDouble("mPledGeAmount"));
				aInfo.setPledgeRate(rs.getDouble("mPledGeRate"));
				aInfo.setStatusID(rs.getLong("nStatusID"));
				aInfo.setAssureCode(rs.getString("sAssureCode"));
			}
            
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			return aInfo;      
                  
		}catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace ();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            
		}
	}    
	/**
	 * 查询一笔贷款申请的所有保证信息
	 * @param lLoanID   贷款申请ID
	 * @param pInfo   翻页
	 * @return long
	 * @throws Exception
	 */    
	public Collection findByLoanID(long lLoanID,OBPageInfo pInfo) throws Exception
	{
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection conn = null;
	String strSQL = null;
	long  lResult=-1;
	long lOrderParam=pInfo.getOrderParam();
	long lDesc=pInfo.getDesc();
        
	Vector v=null;
	long    planID=-1;
        
	try
	{
		conn=Database.getConnection();
            
		/*组织查询条件*/
		strSQL="select a.*,b.sCode,b.sName,b.sContacter,b.sPhone,b.SPROVINCE,b.SCITY,b.SADDRESS,b.SBANK1,b.SEXTENDACCOUNT1 from "
			+"OB_assure a,client b where b.id=a.nclientid and a.nLoanID="+lLoanID;
		switch ((int) lOrderParam)
		{
			case 1 : //按客户编码
				strSQL += " order by b.sCode ";
				break;
			case 2 : //按客户名称
				strSQL += " order by b.sName ";
				break;
			case 3 : //按保证方式
				strSQL += " order by a.nAssureTypeID ";
				break;  
			case 4 : //联系人
				strSQL += " order by b.sContacter ";
				break;
			case 5 : //电话
				strSQL += " order by b.sPhone ";    
				break;
			case 6 : //担保金额
				strSQL += " order by a.mAmount ";
				break;                
			default :
				strSQL += " order by a.ID ";
		}
		//判断是升序还是降序，升序是系统默认的，降序是desc
		if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			strSQL += " desc";
		}
        
		System.out.println(strSQL);
		ps = conn.prepareStatement(strSQL);
		rs = ps.executeQuery();
        
		v=new Vector();
		while (rs.next())
		{
			OBAssureInfo aInfo=new OBAssureInfo();
			aInfo.setID(rs.getLong("ID"));
			aInfo.setLoanID(rs.getLong("nLoanID"));
			aInfo.setAssureTypeID(rs.getLong("nAssureTypeID"));
			aInfo.setFillQuestionary(rs.getLong("nFillQuestionary"));
			aInfo.setClientID(rs.getLong("nClientID"));
			aInfo.setAmount(rs.getDouble("mAmount"));
			aInfo.setImpawName(rs.getString("sImpawName"));
			aInfo.setImpawQuality(rs.getString("sImpawQuality"));
			aInfo.setImpawStatus(rs.getString("sImpawStatus"));
			aInfo.setPledgeAmount(rs.getDouble("mPledGeAmount"));
			aInfo.setPledgeRate(rs.getDouble("mPledGeRate"));
			aInfo.setStatusID(rs.getLong("nStatusID"));
			aInfo.setAssureCode(rs.getString("sAssureCode"));
			aInfo.setClientCode(rs.getString("sCode"));
			aInfo.setClientName(rs.getString("sName"));
			aInfo.setClientContacter(rs.getString("sContacter"));
			aInfo.setClientPhone(rs.getString("sPhone"));
			aInfo.setClientProvince( rs.getString("sProvince"));
			aInfo.setClientCity( rs.getString("sCity"));
			aInfo.setClientAddress(rs.getString("sAddress"));
			aInfo.setClientBank1(rs.getString("sBank1"));
			aInfo.setClientBankAccount1( rs.getString("SEXTENDACCOUNT1"));
			v.add(aInfo);
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
            
		return v;
            
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
}
