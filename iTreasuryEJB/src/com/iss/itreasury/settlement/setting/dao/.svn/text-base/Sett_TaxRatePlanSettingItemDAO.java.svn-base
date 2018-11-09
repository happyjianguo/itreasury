package com.iss.itreasury.settlement.setting.dao;

import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingItemInfo;

import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_TaxRatePlanSettingItemDAO  extends SettlementDAO
{
	  public Sett_TaxRatePlanSettingItemDAO()
    {
		// strTableName = "Sett_TaxRatePlanSettingItem";
		  super("Sett_TaxRatePlanSettingItem",false);
          super.setUseMaxID();
    }
    
    public void clearDAO() throws ITreasuryDAOException
    {
        super.finalizeDAO();
    }
	//取得最大的明细编号
    public long getMaxId()throws Exception{
    	Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
    		String sql = "";
    		long maxId = 0;
    		try{
    			conn = getConnection();
    			sql = "select nvl(Max(id),0) maxid from Sett_TaxRatePlanSettingItem";
    			log.info("sql="+sql);
    			ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                if( rs != null && rs.next() )
                {
                    maxId = rs.getLong("maxid");
                }
                cleanup(rs);
                cleanup(ps);
                cleanup(conn);
    		}finally
            {
                cleanup(rs);
                cleanup(ps);
                cleanup(conn);
            }
    		return maxId;
    	}
		//在删除计划时删除相应明细
	  public void deleteByPlan(TaxRatePlanSettingInfo info) throws Exception
    {
			Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
    		String sql = "";
			StringBuffer sb = new StringBuffer();
        try 
        {
            conn = getConnection();
			sb.append("update sett_taxrateplansettingitem set statusid=");
			sb.append(Constant.RecordStatus.INVALID);
			sb.append(",MODIFYUSERID=");
			sb.append(info.getModifyUserID());
			sb.append(",MODIFYDATE=");
			sb.append(info.getModifyDate());
			//sb.append();
			sb.append(" where taxrateplanid = ");
			sb.append(info.getId());
			sb.append("\n");
			log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.executeUpdate();
			
                cleanup(rs);
                cleanup(ps);
                cleanup(conn);
        }
        catch ( Exception e ) 
        {
            e.printStackTrace();
        }
        finally 
        {
            
                cleanup(rs);
                cleanup(ps);
                cleanup(conn);
        }

    }
	  
	  /**
	 * @param interestTaxPlanId
	 * @return
	 * @throws Exception
	 * 得到利息税费率计划对应的主利率
	 */
	public double getMainRate( long interestTaxPlanId ) throws Exception
   {
	  
	    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
  		String sql = "";
  		double mainRate = 0.00;
  		
  		try
  		{
  			conn = getConnection();
  			sql  = " select nvl(taxRate,0.00) mainRate from Sett_TaxRatePlanSettingItem ";
  			sql += " 	where taxrateplanid = " + interestTaxPlanId;
  			sql += "    	and serialno in (select min(serialno) serialno from Sett_TaxRatePlanSettingItem where taxrateplanid = "+interestTaxPlanId +" and statusid <> 0 )";
  			sql += "		and statusid <> 0 ";
  			
  			log.info("得到利息税费率计划对应的主利率 sql=  \n"+sql);
  			
  			ps = conn.prepareStatement(sql);
  			
            rs = ps.executeQuery();
              
            if( rs != null && rs.next() )
            {
            	mainRate = rs.getDouble("mainRate");
            }
  		}
  		finally
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
  		
		return mainRate;
		
	  }
	
	/**
	 * @param interestTaxPlanId
	 * @return
	 * @throws Exception
	 * 得到一条利息税费计划的非主费率结果集
	 */
	public Collection getOtherRates( long interestTaxPlanId ) throws Exception
	{
		Vector vector = new Vector();
		
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
  		String sql = "";
  		
  		try
  		{
  			conn = getConnection();
  			
  			sql  = " select *  from Sett_TaxRatePlanSettingItem  ";
  			sql += " 	where taxrateplanid = " + interestTaxPlanId;
  			sql += "    	and serialno not in (select min(serialno) serialno from Sett_TaxRatePlanSettingItem where taxrateplanid = "+interestTaxPlanId +" and statusid <> 0 )";
  			sql += "		and statusid <> 0 ";
  			
  			log.info("得到一条利息税费计划的非主费率结果集 sql=  \n"+sql);
  			
  			ps = conn.prepareStatement(sql);
  			
            rs = ps.executeQuery();
            
            TaxRatePlanSettingItemInfo info = null;
            
            while( rs != null && rs.next() )
            {
            	info = new TaxRatePlanSettingItemInfo();
            	
            	info.setId(rs.getLong("ID"));
            	info.setTaxRatePlanID(rs.getLong("taxRatePlanID"));
            	info.setTaxRate(rs.getDouble("taxRate"));
            	
            	vector.add(info);
            }
  		}
  		finally
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
		
		return vector;
	}
}
