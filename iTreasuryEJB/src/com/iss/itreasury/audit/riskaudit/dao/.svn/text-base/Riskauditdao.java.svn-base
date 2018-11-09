package com.iss.itreasury.audit.riskaudit.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzoneAdjustInfo;
import com.iss.itreasury.audit.interzonemaintenance.dataentity.InterzonemaintenanceResultInfo;
import com.iss.itreasury.audit.riskaudit.dataentity.RiskauditInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dataconvert.util.DataFormat;

public class Riskauditdao extends ITreasuryDAO{
	private Log4j logger = null;
    public Riskauditdao() {
        super();
        logger = new Log4j(Constant.ModuleType.AUDIT, this);
    }
    public Collection queryAllzoneByIndexDate(InterzoneAdjustInfo info)throws IException
	{
    	Vector v = new Vector();
    	Connection conn = null;
    	Connection conn1104 = null;
    	CallableStatement cstmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select r.* from (select m.*,n.indexid indexidN,n.indexname, n.procedure,n.scode,n.sdefine,n.sfrequency,n.attribute4,n.sort from rpt_audit_risk m ,rpt_audit_index n \n");
		sbSQL.append(" where m.indexid = n.indexid \n");
		sbSQL.append(" and m.status= "+Constant.RecordStatus.VALID);
		sbSQL.append(" )r, ");
		sbSQL.append(" ( select t.indexid indexidS, max(t.thedate) thedateS \n");
		sbSQL.append(" from rpt_audit_risk t \n");
		sbSQL.append(" where t.status = "+Constant.RecordStatus.VALID);
		sbSQL.append(" and t.thedate <= to_date('" +DataFormat.getDateString(info.getTHEDATE())+ "', 'yyyy-mm-dd') \n");
		sbSQL.append(" group by t.indexid ) s \n");
		sbSQL.append(" where r.indexidN = s.indexidS  \n");
		sbSQL.append(" and r.thedate = s.thedateS \n");
		sbSQL.append(" order by r.scode ");
		try {
			conn = this.getConnection();
			try {
				conn1104 = this.get_1104jdbc_connection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			int tempOnePropertyCount = 0;
			String tempsort = "";
			while(rs.next()){
				RiskauditInfo resultInfo = new RiskauditInfo();
				if(rs.getString("sort").equals("合规风险"))
				{
					tempOnePropertyCount++;
					if(!rs.getString("sort").equals(""))
					{
						resultInfo.setSortnum1(tempOnePropertyCount);
					}
				}
				else if(rs.getString("sort").equals("业务风险"))
				{
					tempOnePropertyCount++;
					if(!rs.getString("sort").equals(""))
					{
						resultInfo.setSortnum2(tempOnePropertyCount);
					}
					
				}		
				String procedure="";
				procedure = rs.getString("PROCEDURE");				
				cstmt = conn1104.prepareCall("{call "+procedure+"(?,?,?)}");
			    cstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
			    cstmt.registerOutParameter(2, java.sql.Types.NUMERIC);
				cstmt.setTimestamp(3,info.getTHEDATE());
				cstmt.executeQuery();
				double X1 = cstmt.getDouble(1);
				long X2 = cstmt.getLong(2);				
				resultInfo.setRiskStandard(X1);
				resultInfo.setFlag(X2);
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setINDEXID(rs.getLong("INDEXID"));
				resultInfo.setTHEDATE(rs.getTimestamp("THEDATE"));
				resultInfo.setNORMALCOND(rs.getLong("NORMALCOND"));
				resultInfo.setNORMALVALUE(rs.getLong("NORMALVALUE"));
				
				resultInfo.setLEVEL1LOW(rs.getLong("LEVEL1LOW"));
				resultInfo.setLEVEL1UP(rs.getLong("LEVEL1UP"));
				resultInfo.setLEVEL2LOW(rs.getLong("LEVEL2LOW"));
				resultInfo.setLEVEL2UP(rs.getLong("LEVEL2UP"));
				resultInfo.setLEVEL3LOW(rs.getLong("LEVEL3LOW"));
				resultInfo.setLEVEL3UP(rs.getLong("LEVEL3UP"));
				resultInfo.setSTATUS(rs.getLong("STATUS"));
				resultInfo.setRISKLEVEL(rs.getLong("RISKLEVEL"));
				resultInfo.setIndexName(rs.getString("INDEXNAME"));
				resultInfo.setProcedure(rs.getString("PROCEDURE"));
				resultInfo.setSort(rs.getString("SORT"));
				tempsort = resultInfo.getSort();
				resultInfo.setScode(rs.getString("SCODE"));
				resultInfo.setSdefine(rs.getString("SDEFINE"));
				resultInfo.setSfrequency(rs.getString("SFREQUENCY"));
				resultInfo.setAttribute4(rs.getString("ATTRIBUTE4"));
		
				v.add(resultInfo);
			}
			try {
			    if(cstmt!=null)
			    	cstmt.close();
			    if(conn!=null)
			    	conn.close();
			    if(conn1104!=null)
			    	conn1104.close();
			   } catch (SQLException e) {			    
			    e.printStackTrace();
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
    public Collection queryAllIndex(InterzoneAdjustInfo info)throws IException
	{
    	Vector v = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select m.* from RPT_AUDIT_INDEX m \n");
		if(info.getINDEXID()>0)
		{
		sbSQL.append("where m.indexid = "+info.getINDEXID());
		}
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			while(transRS.next()){
				InterzonemaintenanceResultInfo resultInfo = new InterzonemaintenanceResultInfo();
				resultInfo.setIndexID(transRS.getLong("INDEXID"));
				resultInfo.setIndexName(transRS.getString("INDEXNAME"));
				resultInfo.setProcedure(transRS.getString("PROCEDURE"));
				resultInfo.setScode(transRS.getString("SCODE"));
				resultInfo.setSdefine(transRS.getString("SDEFINE"));
				resultInfo.setSfrequency(transRS.getString("SFREQUENCY"));
				resultInfo.setAttribute4(transRS.getString("ATTRIBUTE4"));
				resultInfo.setSort(transRS.getString("SORT"));			
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
	 * 数据库查询操作操作
	 * @param id
	 * @return id
	 * @throws ITreasuryDAOException
	 */
	public RiskauditInfo selInfo(InterzoneAdjustInfo info) throws ITreasuryDAOException {
		RiskauditInfo resultInfo = new RiskauditInfo();
		
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select  n.sort,count(n.sort)num from rpt_audit_risk m ,rpt_audit_index n \n");
			buffer.append(" where m.indexid = n.indexid ");
			buffer.append(" and m.status= "+Constant.RecordStatus.VALID);
			if(info.getTHEDATE()!=null)
			{
				buffer.append(" and m.thedate =to_date('" +DataFormat.getDateString(info.getTHEDATE())+ "','yyyy-mm-dd')");
			}
			buffer.append(" group by n.sort ");
			String strSQL = buffer.toString();
			prepareStatement(strSQL);
			executeQuery();
			while(transRS.next())
			{
				if(transRS.getString("sort").equals("合规风险"))
				{
					if(!transRS.getString("sort").equals(""))
					{
						resultInfo.setSortnum1(transRS.getLong("num"));
					}
				}
				else if(transRS.getString("sort").equals("业务风险"))
				{
					if(!transRS.getString("sort").equals(""))
					{
						resultInfo.setSortnum2(transRS.getLong("num"));
					}
					
				}						
			}
			
			finalizeDAO();
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("状态更新异常", e);
		}
       return resultInfo;
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
	 public static Connection get_1104jdbc_connection() throws Exception
	    {
	        Connection conn = null;
	        try
	        {
	            String DB_IP = Config.getProperty( Config.GLOBAL_1104DB_IP ,"");
	            String DB_SID = Config.getProperty( Config.GLOBAL_1104DB_SID ,"");
	            String DB_USERNAME = Config.getProperty( Config.GLOBAL_1104DB_USERNAME ,"");
	            String DB_PASSWORD = Config.getProperty( Config.GLOBAL_1104DB_PASSWORD ,"");
	            String DB_PORT = Config.getProperty( Config.GLOBAL_1104DB_PORT ,"");

	            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

	            Log.print("dbURL = " + dbURL);
	            Log.print("DB_USERNAME = " + DB_USERNAME);
	            Log.print("DB_PASSWORD = " + DB_PASSWORD);

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
	        }
	        catch (SQLException sqe)
	        {
	            Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
	            throw sqe;
	        }
	        return conn;
	    }
}
