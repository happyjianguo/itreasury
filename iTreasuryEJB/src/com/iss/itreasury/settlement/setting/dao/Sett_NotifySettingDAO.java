package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.NotifySettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class Sett_NotifySettingDAO extends SettlementDAO{
	public Sett_NotifySettingDAO() {
		super.strTableName = "Sett_NotifySetting";
	}
    
	/**
	 * 判断是否存在某条记录
	 * @param officeId 办事处id
	 * @param currencyId 币种id
	 * @return 若存在返回该记录的id，否则返回-1
	 */
	public long isExist(long officeId, long currencyId) throws Exception{
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		try{
			conn = this.getConnection();

	        String sql = " select id from " + this.strTableName + " where officeid = '" + officeId + "'   and currencyid = '" + currencyId +"'";
	        System.out.println(sql);
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();	
	        if(rs.next()){
	        	lRtn = rs.getLong("id");
	        }
		}
		finally{
            this.cleanup(rs);
            this.cleanup(ps);
            this.cleanup(conn);			
		}
		
		return lRtn;
	}
	
	/**
	 * 新增一条记录, info中 modifyUserId, modifyDate不输入
	 * @param info 录入信息
	 * @return -1或该记录id
	 */
    public long addInfo(NotifySettingInfo info) throws Exception
    {
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = this.getConnection();

            StringBuffer buffer = new StringBuffer();
            buffer.append("insert into " + this.strTableName + " ");
            buffer.append(" ( id, officeid, currencyid, isinputsign , isview, inputuserid, inputdate, status) values ");
            buffer.append("(");
            buffer.append("?,?,?,?,?,?,?,?");
            buffer.append(")");
            String sql = buffer.toString();

            System.out.println(sql);

            ps = conn.prepareStatement(sql);
            int index = 1;
            info.setId(this.createNewID());
            ps.setLong(index++, info.getId());
            ps.setLong(index++, info.getOfficeId());
            ps.setLong(index++,info.getCurrencyId());
            ps.setLong(index++, info.getIsInputSign());
            ps.setLong(index++, info.getIsView());
            ps.setLong(index++, info.getInputUserId());
            ps.setDate(index++, info.getInputDate());
            ps.setLong(index++, info.getStatusId());

            int nRs = ps.executeUpdate();
            if (nRs > 0)
            {
                lRtn = info.getId();
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
     * 更新修改的信息
     * @param id 记录id
     * @param modifyUserId 修改人id
     * @param modifyDate 修改日期
     * @param statusId 状态信息
     * @return -1或者记录id
     */
    public long updateModifyUser(NotifySettingInfo info) throws Exception{
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = this.getConnection();
            
            String updateSql = "update " + this.strTableName + " " +
            		"set  modifyuserid = ?"+ 
            		" , modifydate = ?" + 
            		" , status = ?"+
            		" , isinputsign =?"+
            		" , isview=?"+
            		" where id = " + info.getId();                 
            System.out.println(updateSql);
            ps = conn.prepareStatement(updateSql);
            ps.setLong(1, info.getModifyUserId());
            ps.setDate(2, info.getModifyDate());
            ps.setLong(3, info.getStatusId());
            ps.setLong(4, info.getIsInputSign());
            ps.setLong(5, info.getIsView());
            int nRs = ps.executeUpdate();
            if (nRs > 0)
            {
                lRtn = info.getId();
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
     * 更新状态
     * @param id 记录id
     * @param statusId 状态id
     * @return -1或者记录id
     */
    public long updateStatus(long id, long statusId) throws Exception{
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = this.getConnection();
            
            String updateSql = "update " + this.strTableName + " " +
            		"set  status = " + statusId + 
            		" where id = " + id ;                 
            System.out.println(updateSql);
            ps = conn.prepareStatement(updateSql);
            int nRs = ps.executeUpdate();
            if (nRs > 0)
            {
                lRtn = id;
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
	 * 查询记录
	 * @param officeId 办事处id
	 * @param currencyId 币种id
	 * @return 若找不到返回null
	 */
	public NotifySettingInfo findRecord(long officeId, long currencyId) throws Exception {
		NotifySettingInfo info = null;
		
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 通过账户ID查询账户监管设置记录
			strSQLQuery.append("SELECT ");
			strSQLQuery.append(" * ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append(this.strTableName);
			strSQLQuery.append(" WHERE ");
			strSQLQuery.append("	OFFICEID = " + officeId + " AND ");
			strSQLQuery.append("	CURRENCYID = " + currencyId + " AND ");
			strSQLQuery.append("	STATUS = " + Constant.RecordStatus.VALID);

			Log.print("查询SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				info = new NotifySettingInfo();
				// 转换数据
				info.setId(rs.getLong("ID"));
				info.setCurrencyId(rs.getLong("CURRENCYID"));
				info.setOfficeId(rs.getLong("OFFICEID"));
				info.setIsInputSign(rs.getLong("ISINPUTSIGN"));
				info.setIsView(rs.getLong("ISVIEW"));
				info.setInputUserId(rs.getLong("INPUTUSERID"));
				info.setInputDate(rs.getDate("INPUTDATE"));
				info.setModifyUserId(rs.getLong("MODIFYUSERID"));
				info.setModifyDate(rs.getDate("MODIFYDATE"));
				info.setStatusId(rs.getLong("STATUS"));
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		return info;

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
            String sql = " select max(id) maxid from " +this.strTableName;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
            {
                lRtn = rs.getLong("maxid") + 1;
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
}
