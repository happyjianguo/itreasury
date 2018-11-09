package com.iss.itreasury.settlement.setting.dao;

import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_TaxRatePlanSettingDAO  extends SettlementDAO
{
	  public Sett_TaxRatePlanSettingDAO()
    {
        super("Sett_TaxRatePlanSetting",false);
        super.setUseMaxID();
		//strTableName = "Sett_TaxRatePlanSetting";
    }
    
    public void clearDAO() throws ITreasuryDAOException
    {
        super.finalizeDAO();
    }

		/*根据费率ID范围查找费率计划*/
	public Collection findTaxRatePlanSettingBetweenID(long fromId,long toId) throws Exception
	{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        long recordNumber = -1;
		Vector v = new Vector();
        try 
        {
            conn = getConnection();
            
            sb.append(" select ID,OfficeID,Name,Code,InputUserID,InputDate,StatusID from Sett_TaxRatePlanSetting  where 1=1 ");
			if(fromId!=-1)
            sb.append(" and id >= " + fromId);
			if(toId!=-1)
			sb.append(" and  id <= " + toId );

            sb.append(" and statusId = " + Constant.RecordStatus.VALID);
            
			sb.append(" order by id");
            log.info(" SQL : \n"+sb.toString());
            System.out.println("sql========="+sb.toString());
			
            ps = conn.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                TaxRatePlanSettingInfo trpsInfo = new TaxRatePlanSettingInfo();
				trpsInfo.setId(rs.getLong(1));
				trpsInfo.setOfficeID(rs.getLong(2));
				trpsInfo.setName(rs.getString(3));
				trpsInfo.setCode(rs.getString(4));
				trpsInfo.setInputUserID(rs.getLong(5));
				trpsInfo.setInputDate(rs.getTimestamp(6));
				trpsInfo.setStatusID(rs.getLong(7));
				v.add(trpsInfo);
            }
            sb.setLength(0);
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
        finally
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
		return (v.size() > 0 ? v : null);
		
	}
	    /**
     * @description: boolean
     * @param CommissionSettingInfo
     * @return boolean
     * @throws IException
     */
    public boolean isAllow(TaxRatePlanSettingInfo info) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        long recordNumber = -1;
        try 
        {
            conn = getConnection();
            
            // check that if can add or update
            sb.append(" select count(*) recordNumber from Sett_TaxRatePlanSetting ");
			sb.append("where Name = ");
			sb.append(info.getName());
            if( info.getId() > 0 )
            {
                //if update
                sb.append("  and id <>  "+info.getId());
            }
            
            log.info(" SQL : \n"+sb.toString());
            
            ps = conn.prepareStatement(sb.toString());
            
            
            rs = ps.executeQuery();
            
            if( rs != null && rs.next() )
            {
                recordNumber = rs.getLong("recordNumber");
            }
            
            
            if( recordNumber <= 0 )
            {
               flag = true;
            }
            
            
            sb.setLength(0);
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
        finally
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
        return flag;
    }
	public long getMaxId()throws Exception{
	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		String sql = "";
		long maxId = 0;
		try{
			conn = getConnection();
			sql = "select nvl(Max(id),0) maxid from Sett_TaxRatePlanSetting";
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
	/**
	 * 和其他的重 >0
	 * 不重 0*/
	public long isSameName(TaxRatePlanSettingInfo info)throws Exception{
		long result = 0;
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try{
			conn = getConnection();
			//是否原来没变
		sb.append("select count(*) cnt from Sett_TaxRatePlanSetting where id!=");
		sb.append(info.getId());
		sb.append(" and name='");
		sb.append(info.getName());
		sb.append("'\n");
		sb.append(" and statusid=");
		sb.append(Constant.RecordStatus.VALID);
		
		log.info("sql====="+sb.toString());
		log.print("sql====="+sb.toString());
		ps = conn.prepareStatement(sb.toString());
        rs = ps.executeQuery();
		 if( rs != null && rs.next() )
         {
             result = rs.getLong("cnt");
         }
         cleanup(rs);
         cleanup(ps);
         cleanup(conn);
         //
		}finally
        {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }
		log.info("result====="+result);
		log.print("result====="+result);
         return result;
	}
	/*
	   public static void main(String args[]){
   Sett_TaxRatePlanSettingDAO dao = new Sett_TaxRatePlanSettingDAO();
   try{
   Collection c = 
 dao.findTaxRatePlanSettingBetweenID(1,3);
   }catch(Exception e){e.printStackTrace();}
   }*/
}
