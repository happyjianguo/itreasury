package com.iss.itreasury.integratedCredit.dao;
/**
 * @author 王屹嵘 2009-11-25
 * 主要投资人DAO
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.integratedCredit.dataentity.ClientMainInvestorInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class ClientMainInvestorDao {
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	public Collection findbyClientId(long clientid)throws Exception
	{
		String strSQL = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList resList = new ArrayList();
		try
		{
			con = Database.getConnection();
			
			strSQL ="select * from ClientMainInvestor where clientid= ?";
			
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, clientid);
			rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ClientMainInvestorInfo cinvest = new ClientMainInvestorInfo();
				cinvest.setId(rs.getLong("ID"));
				cinvest.setClientid(rs.getLong("CLIENTID"));
				cinvest.setInvertId(rs.getLong("INVERTID"));
				cinvest.setNactualInvestment(rs.getLong("NACTUALINVESTMENT"));
				cinvest.setNpaidCapitalRate(rs.getDouble("NPAIDCAPITALRATE"));
				cinvest.setSmainInvestorName(rs.getString("SMAININVESTORNAME"));
				resList.add(cinvest);
			}
			try{
				if(rs !=null)
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
			}catch(Exception e)
			{
				
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			if(rs !=null)
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
		}finally
        {
            try
            {
            	if(rs !=null)
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
                throw new IException("Gen_E001");
            }
        }
		return resList;
	}
	
	public long SaveClientMainInvest(ClientMainInvestorInfo cinvest)throws IException,Exception
	{
		long lResult=-1;
        String strSQL = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
        	con = Database.getConnection();
        	
        	strSQL="select max(id) as id from ClientMainInvestor";
        	log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				cinvest.setId(rs.getLong("id")+1);
			}else
				cinvest.setId(1);
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }
			
			strSQL="select max(invertId) as invertId from ClientMainInvestor where clientid=?";
        	log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, cinvest.getClientid());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				cinvest.setInvertId(rs.getLong("invertId")+1);
			}else
				cinvest.setInvertId(1);
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }
			
        	strSQL=" insert into ClientMainInvestor(id,clientid,invertId,smainInvestorName,nactualInvestment," +
        			"npaidCapitalRate,sabstract,nstatusid,nofficeid) "
        		   +"values(?,?,?,?,?,?,?,?,?)";
        	log4j.info(" 其它股东" + strSQL);
            ps = con.prepareStatement(strSQL);
            ps.setLong(1, cinvest.getId());
            ps.setLong(2, cinvest.getClientid());
            ps.setLong(3, cinvest.getInvertId());
            ps.setString(4, cinvest.getSmainInvestorName());
            ps.setLong(5, cinvest.getNactualInvestment());
            ps.setDouble(6, cinvest.getNpaidCapitalRate());
            ps.setString(7, cinvest.getSabstract());
            ps.setLong(8, 1);
            ps.setLong(9, cinvest.getNofficeid());
            
            lResult = ps.executeUpdate();
            
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (lResult < 0)
            {
                lResult = -1;
                return lResult;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        	
        }catch(Exception e)
        {
        	 e.printStackTrace();
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
             throw new IException("Gen_E001");
        }finally
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
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
		
		return lResult;
	}
	public long UpdateClientMainInvest(ClientMainInvestorInfo cinvest)throws IException,Exception
	{
		long lResult=-1;
        String strSQL = null;
        Connection con = null;
        PreparedStatement ps = null;
        
        try{
        	con = Database.getConnection();
        	strSQL="update ClientMainInvestor set smainInvestorName=?,nactualInvestment=?, npaidCapitalRate=?" +
        			" where clientid=? and invertId=?";
        	ps = con.prepareStatement(strSQL);
        	ps.setString(1, cinvest.getSmainInvestorName());
        	ps.setLong(2, cinvest.getNactualInvestment());
        	ps.setDouble(3, cinvest.getNpaidCapitalRate());
        	ps.setLong(4, cinvest.getClientid());        	
        	ps.setLong(5, cinvest.getInvertId());        	
        	lResult = ps.executeUpdate();
        	if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (lResult < 0)
            {
                lResult = -1;
                return lResult;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
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
        }finally
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
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
		return lResult;
	}
	
	public long DeleteClientMainInvest(long clientid)throws IException,Exception
	{
		long Result = -1;
		String strSQL = null;
		Connection con = null;
		PreparedStatement ps = null;
		
		try
		{
			con = Database.getConnection();
			
			strSQL ="delete from ClientMainInvestor where clientid=?";
			
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, clientid);
			Result = ps.executeUpdate();
        	if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (Result < 0)
            {
            	Result = -1;
                return Result;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return Result;
	}
}
