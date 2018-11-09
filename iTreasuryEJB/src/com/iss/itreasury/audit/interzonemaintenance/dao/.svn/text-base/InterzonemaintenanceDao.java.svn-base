package com.iss.itreasury.audit.interzonemaintenance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditCondition;
import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditResultInfo;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzoneAdjustInfo;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzonemaintenanceResultInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dataentity.NotifySettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class InterzonemaintenanceDao extends ITreasuryDAO{
	 // SQL语法结构
    private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;
    public InterzonemaintenanceDao() {
        super();
        logger = new Log4j(Constant.ModuleType.AUDIT, this);
    }
    public Collection queryInterzoneByIndexid(InterzonemaintenanceResultInfo info)throws IException
	{
    	Vector v = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select m.* from RPT_AUDIT_INDEX m \n");
		if(info.getIndexID()>0)
		{
		sbSQL.append("where m.indexid = "+info.getIndexID());
		}
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			while(transRS.next()){
				InterzonemaintenanceResultInfo resultInfo = new InterzonemaintenanceResultInfo();
				resultInfo.setIndexID(transRS.getLong("INDEXID"));
				resultInfo.setIndexName(transRS.getString("INDEXNAME"));
			//	resultInfo.setProcedure(transRS.getString("PROCEDURE"));
			//	resultInfo.setScode(transRS.getString("SCODE"));
			//	resultInfo.setSdefine(transRS.getString("SDEFINE"));
			//	resultInfo.setSfrequency(transRS.getString("SFREQUENCY"));
			//	resultInfo.setAttribute4(transRS.getString("ATTRIBUTE4"));
			//	resultInfo.setSort(transRS.getString("SORT"));			
				v.add(resultInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException("查询监测指标出现错误");
		}
		finally{
			finalizeDAO();
		}
	return v;
	} 
    public InterzonemaintenanceResultInfo queryInterIndexid(InterzonemaintenanceResultInfo info)throws IException
	{
    	InterzonemaintenanceResultInfo resultInfo = new InterzonemaintenanceResultInfo();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select m.* from RPT_AUDIT_INDEX m \n");
		if(info.getIndexID()>0)
		{
		sbSQL.append("where m.indexid = "+info.getIndexID());
		}
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			if(transRS.next()){
				resultInfo.setIndexID(transRS.getLong("INDEXID"));
				resultInfo.setIndexName(transRS.getString("INDEXNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException("查询监测指标出现错误");
		}
		finally{
			finalizeDAO();
		}
	return resultInfo;
	} 
    
    public Collection queryInterzoneInterestAudit(InterzoneAdjustInfo info)throws IException
	{
    	Vector v = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select m.* from RPT_AUDIT_RISK m \n");
		if(info.getINDEXID()>0)
		{
		sbSQL.append(" where m.indexid = "+info.getINDEXID());
		sbSQL.append(" and m.STATUS= "+Constant.RecordStatus.VALID);
		sbSQL.append(" order by m.thedate ");
		}
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			while(transRS.next()){
				InterzoneAdjustInfo resultInfo = new InterzoneAdjustInfo();
				resultInfo.setID(transRS.getLong("ID"));
				resultInfo.setINDEXID(transRS.getLong("INDEXID"));
				resultInfo.setRISKLEVEL(transRS.getLong("RISKLEVEL"));
				resultInfo.setTHEDATE(transRS.getTimestamp("THEDATE"));
				resultInfo.setNORMALCOND(transRS.getLong("NORMALCOND"));
				resultInfo.setNORMALVALUE(transRS.getLong("NORMALVALUE"));
				resultInfo.setLEVEL1LOW(transRS.getLong("LEVEL1LOW"));
				resultInfo.setLEVEL1UP(transRS.getLong("LEVEL1UP"));
				resultInfo.setLEVEL2LOW(transRS.getLong("LEVEL2LOW"));
				resultInfo.setLEVEL2UP(transRS.getLong("LEVEL2UP"));
				resultInfo.setLEVEL3LOW(transRS.getLong("LEVEL3LOW"));
				resultInfo.setLEVEL3UP(transRS.getLong("LEVEL3UP"));		
				v.add(resultInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException("查询监测指标出现错误");
		}
		finally{
			finalizeDAO();
		}
	return v;
	}
    
    /**
	 * 新增一条记录
	 * @param info 录入信息
	 * @return -1或该记录id
	 */
    public long addInfo(InterzoneAdjustInfo aInfo) throws Exception
    {
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
        	conn = this.getConnection();

            StringBuffer buffer = new StringBuffer();
            buffer.append("insert into RPT_AUDIT_RISK ");
            buffer.append(" ( ID, INDEXID, RISKLEVEL, THEDATE , NORMALCOND, NORMALVALUE, LEVEL1LOW, LEVEL1UP,LEVEL2LOW,LEVEL2UP,LEVEL3LOW,LEVEL3UP,STATUS) values ");
            buffer.append("(");
            buffer.append("?,?,?,?,?,?,?,?,?,?,?,?,?");
            buffer.append(")");
            String sql = buffer.toString();

            System.out.println(sql);

            ps = conn.prepareStatement(sql);
            int index = 1;
            aInfo.setID(this.createNewID());
            ps.setLong(index++, aInfo.getID());
            ps.setLong(index++, aInfo.getINDEXID());
            ps.setLong(index++, aInfo.getRISKLEVEL());
            ps.setTimestamp(index++, aInfo.getTHEDATE());
            ps.setLong(index++, aInfo.getNORMALCOND());
            ps.setLong(index++, aInfo.getNORMALVALUE());
            ps.setLong(index++, aInfo.getLEVEL1LOW());
            ps.setLong(index++, aInfo.getLEVEL1UP());
            ps.setLong(index++, aInfo.getLEVEL2LOW());
            ps.setLong(index++, aInfo.getLEVEL2UP());
            ps.setLong(index++, aInfo.getLEVEL3LOW());
            ps.setLong(index++, aInfo.getLEVEL3UP());
            ps.setLong(index++, aInfo.getSTATUS());

            int nRs = ps.executeUpdate();
            if (nRs > 0)
            {
                lRtn = aInfo.getID();
            }

        }
        finally
        {
        	this.cleanup(rs);
            this.cleanup(ps);
            this.cleanup(conn);
        }

        return lRtn;
    }
    /**
	 * 数据库更新操作操作
	 * @param id
	 * @param statusID
	 *            需要更新到的状态
	 * @return id
	 * @throws ITreasuryDAOException
	 */
	public long delInfo(InterzoneAdjustInfo info) throws ITreasuryDAOException {
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update RPT_AUDIT_RISK t set t.status= \n"+Constant.RecordStatus.INVALID);
			
			buffer.append("\n  WHERE ID = "+info.getID());

			String strSQL = buffer.toString();

			prepareStatement(strSQL);
			executeQuery();
			finalizeDAO();
			
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常", e);
		}
       return info.getID();
	}
	/**
	 * 数据库查询操作操作
	 * @param id
	 * @return id
	 * @throws ITreasuryDAOException
	 */
	public InterzoneAdjustInfo selInfo(InterzoneAdjustInfo info) throws ITreasuryDAOException {
		InterzoneAdjustInfo aInfo = new InterzoneAdjustInfo();
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from  RPT_AUDIT_RISK t  \n");
			
			buffer.append("\n  WHERE ID = "+info.getID());

			String strSQL = buffer.toString();

			prepareStatement(strSQL);
			executeQuery();
			if (transRS != null && transRS.next())
			{
				aInfo.setID(transRS.getLong("ID"));
				aInfo.setINDEXID(transRS.getLong("INDEXID"));
				aInfo.setRISKLEVEL(transRS.getLong("RISKLEVEL"));
				aInfo.setTHEDATE(transRS.getTimestamp("THEDATE"));
				aInfo.setNORMALCOND(transRS.getLong("NORMALCOND"));				
				aInfo.setNORMALVALUE(transRS.getLong("NORMALVALUE"));
				aInfo.setLEVEL1LOW(transRS.getLong("LEVEL1LOW"));	
				aInfo.setLEVEL1UP(transRS.getLong("LEVEL1UP"));
				aInfo.setLEVEL2LOW(transRS.getLong("LEVEL2LOW"));	
				aInfo.setLEVEL2UP(transRS.getLong("LEVEL2UP"));
				aInfo.setLEVEL3LOW(transRS.getLong("LEVEL3LOW"));
				aInfo.setLEVEL3UP(transRS.getLong("LEVEL3UP"));							
			}
			
			finalizeDAO();
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("状态更新异常", e);
		}
       return aInfo;
	}
	 /**
	 * 数据库更新操作操作
	 * @param id
	 *            需要更新到的状态
	 * @return id
	 * @throws ITreasuryDAOException
	 */
	public long updateInfo(InterzoneAdjustInfo info) throws IException {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE RPT_AUDIT_RISK ");
			sbSQL.append(" SET THEDATE = ? "); //生效日期
			sbSQL.append(" ,NORMALCOND = ?");
			sbSQL.append(" ,NORMALVALUE = ?");
			sbSQL.append(" ,LEVEL1LOW = ?");
			sbSQL.append(" ,LEVEL1UP = ?");

			if (info.getLEVEL2LOW() > 0)
			{
				sbSQL.append(" ,LEVEL2LOW = ?");
			}

			if (info.getLEVEL2UP() > 0 )
			{
				sbSQL.append(" ,LEVEL2UP = ?");
			}
			if (info.getLEVEL3LOW() > 0)
			{
				sbSQL.append(" ,LEVEL3LOW = ?");
			}
			if (info.getLEVEL3UP() > 0 )
			{
				sbSQL.append(" ,LEVEL3UP = ?");
			}
		    if(info.getRISKLEVEL()>0)
		    {
		    	sbSQL.append(" ,RISKLEVEL = ?");
		    }
			sbSQL.append(" WHERE ID = ? "); 

			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setTimestamp(index++, info.getTHEDATE());
			ps.setLong(index++, info.getNORMALCOND());
			ps.setLong(index++, info.getNORMALVALUE());
			ps.setLong(index++, info.getLEVEL1LOW());
			ps.setLong(index++, info.getLEVEL1UP());
			if (info.getLEVEL2LOW() > 0)
			{
				ps.setLong(index++, info.getLEVEL2LOW());
			}

			if (info.getLEVEL2UP() > 0)
			{
				ps.setLong(index++, info.getLEVEL2UP());
			}
			if (info.getLEVEL3LOW() > 0)
			{
				ps.setLong(index++, info.getLEVEL3LOW());
			}

			if (info.getLEVEL3UP() > 0)
			{
				ps.setLong(index++, info.getLEVEL3UP());
			}
			if (info.getRISKLEVEL() > 0)
			{
				ps.setLong(index++, info.getRISKLEVEL());
			}
			ps.setLong(index++, info.getID());

			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			throw new IException("状态更新异常");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new IException("状态更新异常");
			}
		}
       return info.getID();
	}
	 public long checkRiskDate(InterzoneAdjustInfo info) throws IException
	    {
	        StringBuffer strSQL = new StringBuffer();
	        long lResult = -1;
	            try
	            {
	            initDAO();       
	            strSQL.append( " select count(INDEXID)num from RPT_AUDIT_RISK \n");
	            strSQL.append( " where INDEXID = '" + info.getINDEXID() + "' and STATUS = "+Constant.RecordStatus.VALID);
	            strSQL.append( " and THEDATE = to_date('" +DataFormat.getDateString(info.getTHEDATE())+ "','yyyy-mm-dd')");	          
	            if(info.getID()>0)
	            {
	            strSQL.append(" and id !="+info.getID());	
	            }
	            prepareStatement(strSQL.toString());
	            executeQuery();
	            if (transRS != null && transRS.next())
	                {
	                    if (transRS.getLong("num") >0)
	                    {
	                        lResult = 1;
	                    }
	                    else
	                    {
	                        lResult = -1;
	                    }
	                }
	            else{
	            	lResult = -1;
	            }
	           finalizeDAO();
	                
	            }  catch (Exception e)
	            {
	                throw new IException("查询指标生效日产生错误", e);	           
	             }
	        return lResult;
	    }
    protected Connection getConnection()
	{
		Connection con = null;
		try
		{
			if(transConn == null)
			{
				con = Database.getConnection();
			}
			else
			{
				con = transConn;
			}
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
		}
		return con;		
	}
    /**
     * 产生新的ID(数据库最大值+1)
     */
	public long createNewID() throws Exception{
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
        	conn = this.getConnection();
            String sql = " select max(id) maxid from RPT_AUDIT_RISK " ;
            ps = conn.prepareStatement(sql);;
            rs = ps.executeQuery();
            while (rs.next())
            {
                lRtn = rs.getLong("maxid") + 1;
            }
        }
        finally
        {
        	cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
        return lRtn;
    }
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("进入关闭RS方法");
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null && con.isClosed()==false && transConn == null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
 }

