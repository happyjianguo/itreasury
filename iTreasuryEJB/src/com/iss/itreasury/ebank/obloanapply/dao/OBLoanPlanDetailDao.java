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
import com.iss.itreasury.ebank.obdao.*;
import com.iss.itreasury.ebank.obloanapply.dataentity.*;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanPlanDetailDao extends OBBaseDao
{
	private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

	/**
	 * 查找一笔执行计划明细
	 * @param pdInfo  执行计划明细纪录
	 * @return long
	 * @throws Exception
	 */	
	public long insert(OBLoanPlanDetailInfo pdInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		long ID=-1;
		long planID=pdInfo.getPlanID();
		Timestamp planDate=pdInfo.getPlanDate() ;
		long payTypeID=pdInfo.getPayTypeID();
		double amount=pdInfo.getAmount();
		String type=pdInfo.getType();
		long lastExtendID=pdInfo.getLastExtendID();
		long lastOverdueID=pdInfo.getLastOverdueID();
		long lastVersionPlanID=pdInfo.getLastVersionPlanID();
        
        
		try
		{
			conn=Database.getConnection();
			/*首先要获得新的ID*/
			strSQL="select nvl(max(ID)+1,1) nID from ob_PlanDetail";
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				ID=rs.getLong("nID");
			}
			cleanup(rs);
			cleanup(ps);

			/* 开始执行插入*/            
			strSQL="insert into ob_PlanDetail ("
				+"ID, nPlanID, dtPlanDate, nPayTypeID, mAmount, sType, "
				+"dtModifyDate, nLastExtendID, nLastOverdueID, nLastVersionPlanID"
				+") values (?, ?, ?, ?, ?, ?, SYSDATE , ?, ?, ?)";
			//System.out.println(strSQL);
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setLong(n++,ID);
			ps.setLong(n++,planID);
			ps.setTimestamp(n++,planDate);
			ps.setLong(n++,payTypeID);
			ps.setDouble(n++,amount);
			ps.setString(n++,type);
			ps.setLong(n++,lastExtendID);
			ps.setLong(n++,lastOverdueID);
			ps.setLong(n++,lastVersionPlanID);
            
			lResult=ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            
			return ID;
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			//e.printStackTrace();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 删除一笔执行计划明细
	 * @param pdID  执行计划明细纪录ID
	 * @return long
	 * @throws Exception
	 */	
	public long delete(long pdID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		try
		{
			conn=Database.getConnection();
			strSQL="delete ob_PlanDetail where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,pdID);
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			return 1;
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
	 * 更改一笔执行计划明细
	 * @param pdInfo  执行计划明细纪录
	 * @return long
	 * @throws Exception
	 */	
	public long update(OBLoanPlanDetailInfo pdInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		long pdID=pdInfo.getID();
		long planID=pdInfo.getPlanID();
		long payTypeID=pdInfo.getPayTypeID();
		double amount=pdInfo.getAmount();
		String type=pdInfo.getType();
		long lastExtendID=pdInfo.getLastExtendID();
		long lastOverdueID=pdInfo.getLastOverdueID();
		long lastVersionPlanID=pdInfo.getLastVersionPlanID();
        
		try
		{
			conn=Database.getConnection();
			strSQL="update ob_PlanDetail "
				+"set dtModifyDate=SYSDATE, nPlanID=?, "
				+"nPayTypeID=?, mAmount=?, sType=?, nLastExtendID=?, "
				+"nLastOverdueID=?, nLastVersionPlanID=?,dtPlanDate = ? "
				+"where ID=?";
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setLong(n++,planID);
			ps.setLong(n++,payTypeID);
			ps.setDouble(n++,amount);
			ps.setString(n++,type);
			ps.setLong(n++,lastExtendID);
			ps.setLong(n++,lastOverdueID);
			ps.setLong(n++,lastVersionPlanID);
			ps.setTimestamp(n++,pdInfo.getPlanDate());
			ps.setLong(n++,pdID);
			
			lResult=ps.executeUpdate();
            
			cleanup(ps);
			cleanup(conn);
            
			return pdID;
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
	 * 查找一笔执行计划明细
	 * @param pdID  查询执行计划明细纪录
	 * @return OBLoanPlanDetailInfo
	 * @throws Exception
	 */	
	public OBLoanPlanDetailInfo findByID(long pdID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
        
		OBLoanPlanDetailInfo pdInfo=null;
		try
		{
			conn=Database.getConnection();
			strSQL="select * from ob_PlanDetail where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,pdID);
			rs=ps.executeQuery();
			if (rs.next())
			{
				pdInfo=new OBLoanPlanDetailInfo();
				pdInfo.setID(rs.getLong("ID"));
				pdInfo.setPlanID(rs.getLong("nPlanID"));
				pdInfo.setPlanDate(rs.getTimestamp("dtPlanDate"));
				pdInfo.setPayTypeID(rs.getLong("nPayTypeID"));
				pdInfo.setAmount(rs.getDouble("mAmount"));
				pdInfo.setType(rs.getString("sType"));
				pdInfo.setModifyDate(rs.getTimestamp("dtModifyDate"));
				pdInfo.setLastExtendID(rs.getLong("nLastExtendID"));
				pdInfo.setLastOverdueID(rs.getLong("nLastOverdueID"));
				pdInfo.setLastVersionPlanID(rs.getLong("nLastVersionPlanID"));
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
		return pdInfo;
	}	
	public static void main(String[] args)
	{
	}
}
