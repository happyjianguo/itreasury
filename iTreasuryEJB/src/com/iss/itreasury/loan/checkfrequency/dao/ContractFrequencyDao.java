package com.iss.itreasury.loan.checkfrequency.dao;
import java.util.*;
import java.sql.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.checkfrequency.dataentity.ContractFrequencyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.*;
public class ContractFrequencyDao extends SettlementDAO{
	
	private static Log4j log4j = null;
	
	public ContractFrequencyDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	
	public List queryInfoList(ContractFrequencyInfo info)throws Exception{
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();
			
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT c.sname as csname,type.name as subTypeName,c.sCode as sCode,ct.id as lid,ct.Nloanid as nloanid,ct.Scontractcode as scontractcode,ct.nTypeID as ntypeid ,ct.nStatusID as contractStatus,ct.nintervalnum as nintervalnum,  ");
			sbSQL.append(" ct.Mloanamount as mloanamount,ct.Nrisklevel as nrisklevel,ct.Dtenddate as dtenddate,");
			sbSQL.append(" ct.Dtstartdate as dtstartdate,ct.Checkfrequency as checkfrequency ");
			sbSQL.append(" FROM loan_contractform  ct ,client c,LOAN_LOANTYPESETTING type   "); 
			//sbSQL.append(" WHERE  (ct.nstatusid=3　or ct.nstatusid=4　or ct.nstatusid=5　or ct.nstatusid=6　or ct.nstatusid=7) ");
			//sbSQL.append(" and  (ct.ntypeid = 1 or ct.ntypeid = 2) ");
			//LOANConstant.ContractStatus.NOTACTIVE4   BADDEBT9
			sbSQL.append(" WHERE ct.nStatusID >= " + LOANConstant.ContractStatus.ACTIVE);
			sbSQL.append("and ct.nStatusID <=  " + LOANConstant.ContractStatus.BADDEBT);
			sbSQL.append(" and ct.nTypeID in (1,2,3,4,8) ");
			sbSQL.append(" and ct.Nborrowclientid=c.ID and ct.nsubtypeid=type.id ");
			sbSQL.append(" and ct.nCurrencyID = ? ");
			sbSQL.append(" and ct.nOfficeID = ?");
			sbSQL.append(" order by c.sCode,ct.sContractCode"); 
			System.out.println("贷后检查频率查询sql******"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int n=1;
            ps.setLong(n++, info.getNcurrencyid());
            ps.setLong(n++,info.getNofficeid());
			rs = ps.executeQuery();

			while (rs.next())
			{
				ContractFrequencyInfo contractfrequencyinfo = new ContractFrequencyInfo();
				contractfrequencyinfo.setId(rs.getLong("lid"));
				contractfrequencyinfo.setNloanid(rs.getLong("nloanid"));
				contractfrequencyinfo.setSname(rs.getString("csname"));
				contractfrequencyinfo.setClientcode(rs.getString("sCode"));
				contractfrequencyinfo.setScontractcode(rs.getString("scontractcode"));
				contractfrequencyinfo.setMloanamount(rs.getDouble("mloanamount"));
				contractfrequencyinfo.setNrisklevel(rs.getLong("nrisklevel"));
				contractfrequencyinfo.setDtenddate(rs.getTimestamp("dtenddate"));
				contractfrequencyinfo.setNtypeid(rs.getLong("ntypeid"));
				contractfrequencyinfo.setNstatusid(rs.getLong("contractStatus"));
				contractfrequencyinfo.setNintervalnum(rs.getLong("nintervalnum"));
				contractfrequencyinfo.setDtstartdate(rs.getTimestamp("dtstartdate"));
				contractfrequencyinfo.setCheckfrequency(rs.getLong("checkfrequency"));
				contractfrequencyinfo.setSubTypeName(rs.getString("subTypeName"));
				list.add(contractfrequencyinfo);
			}

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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			//try {
				//throw new IException("Gen_E001");
			//} catch (IException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			//}
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				//throw new IException("Gen_E001");
			}
		}
		
		return list;	
	
	}
	
	
	
	public long update(ContractFrequencyInfo info)
	{
		long flat = -1;
		
		PreparedStatement ps = null;
        Connection conn = null;
        String strSQL = null;
        
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        		
    			strSQL="update loan_contractform "
                    +"set checkfrequency=? "
                    +" where ID=?";
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++, info.getCheckfrequency());
                ps.setLong(n++,info.getId());
                flat=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( flat<0 )
                {
                    log.info("update loan plan detail fail:"+flat);
                    return -1;          
                }
                else
                {
                    return flat; //info.getID();
                }
    		
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}       	
        }	
	}

}
