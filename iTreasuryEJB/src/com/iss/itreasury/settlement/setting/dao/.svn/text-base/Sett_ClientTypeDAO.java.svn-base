/*
 * Created on 2003-12-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.setting.dataentity.ClientTypeSetInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.util.Constant;
import java.util.Collection;
import com.iss.itreasury.util.Log;
import java.util.ArrayList;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_ClientTypeDAO extends SettlementDAO
{
    public Sett_ClientTypeDAO()
    {
        super.strTableName = "Sett_ClientType";
    }

    public static void main(String[] args)
    {

    }

    //新增一条记录
    public long add(ClientTypeSetInfo info) throws Exception
    {
        System.out.println("调用add方法");
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = this.getConnection();

            //先判断是否重复
            String sql = " select * from sett_ClientType where sname = '"+info.getName()+"'   and nstatusid = 1 ";
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
                lRtn = -2;
                rs.close();rs = null;
                ps.close();ps = null;
            }
            else
            {
                StringBuffer buffer = new StringBuffer();
                buffer.append("insert into " + this.strTableName + " ");
                buffer.append(" ( id, Sname, Nstatusid, scode ,NOFFICEID) values ");
                buffer.append("(");
                buffer.append("?,?,?,?,?");
                buffer.append(")");
                sql = buffer.toString();

                System.out.println(sql);

                ps = conn.prepareStatement(sql);
                int index = 1;
                info.setID(this.createNewID());
                ps.setLong(index++, info.getID());
                ps.setString(index++, info.getName());
                ps.setLong(index++, 1);
                ps.setLong(index++, info.getID());
                ps.setLong(index++,info.getOfficeID());

                int nRs = ps.executeUpdate();
                if (nRs > 0)
                {
                    lRtn = info.getID();
                }
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

    //修改一条记录
    public long update(ClientTypeSetInfo info) throws Exception
    {
        System.out.println("调用update方法");
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = this.getConnection();

            //先判断是否重复
            String sql = " select * from sett_ClientType where sname = '"+info.getName()+"'  and nstatusid = 1 and NOFFICEID="+ info.getOfficeID() +" and id != "+info.getID();
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
                lRtn = -2;
                rs.close();rs = null;
                ps.close();ps = null;
            }
            else
            {
                sql = " update sett_ClientType set " + " sName = ?, nStatusID = ? " + " where id = " + info.getID() + "\n";

                System.out.println(sql);

                ps = conn.prepareStatement(sql);

                int index = 1;
                ps.setString(index++, info.getName());
                ps.setLong(index++, 1);
                int n = ps.executeUpdate();
                if (n > 0)
                {
                    lRtn = info.getID();
                }
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

    //删除一条记录
    public long delete(long nID) throws Exception
    {
        System.out.println(" 调用delete方法 ,客户类型   \n");
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            //如果客户类型已经被使用（新增客户时选择了该客户类型），则客户类型不允许被删除
            boolean isUsedByClient = false;
            isUsedByClient = CheckClientType(nID);
            
            if(!isUsedByClient)
            {
                lRtn = this.updateStatusID(nID, Constant.RecordStatus.INVALID);
            }
            else
            {
                System.out.println(" 该客户类型已经被使用，不允许删除！  \n");
                
                lRtn = -100;
            }
        }
        finally
        {
            this.cleanup(ps);
            this.cleanup(conn);
        }

        return lRtn;
    }

    //查询所有有效记录
    public Collection find(boolean isDesc,long lOfficeID) throws Exception
    {
        System.out.println("调用find方法");
        Collection cln = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = this.getConnection();
            StringBuffer buffer = new StringBuffer("");
            //拼凑查询语句
            buffer.append(" select * ");

            buffer.append(" from " + this.strTableName + " \n");
            buffer.append(" where nStatusID != " + Constant.RecordStatus.INVALID + " \n");
            buffer.append(" and NOFFICEID=" + lOfficeID);
            buffer.append(" order by id ");

            //处理排序
            if (isDesc)
                buffer.append(" asc ");
            else
                buffer.append(" desc ");

            String sql = buffer.toString();

            System.out.println(sql);

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            cln = this.transferResultsetIntoCollection(rs);
        }
        finally
        {
            this.cleanup(rs);
            this.cleanup(ps);
            this.cleanup(conn);
        }

        return cln;
    }

    //通过主键查找记录
    public ClientTypeSetInfo findByID(long nID) throws Exception
    {
        ClientTypeSetInfo info = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = this.getConnection();
            String sql = " select * from sett_ClientType where id = ? ";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, nID);
            rs = ps.executeQuery();

            while (rs.next())
            {
                info = new ClientTypeSetInfo();
                info.setID(rs.getLong("ID"));
                info.setName(rs.getString("sName"));
            }

        }
        finally
        {
            this.cleanup(rs);
            this.cleanup(ps);
            this.cleanup(conn);
        }

        return info;
    }

    //产生新的ID(数据库最大值+1)
    private long createNewID() throws Exception
    {
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = this.getConnection();
            String sql = " select max(id) maxid from sett_ClientType ";
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

    //设置记录状态
    private long updateStatusID(long id, long nNewStatus) throws Exception
    {
        long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            conn = this.getConnection();
            String sql = " update " + this.strTableName + " set nStatusID=? where id=? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, nNewStatus);
            ps.setLong(2, id);

            int n = ps.executeUpdate();
            if (n > 0)
                lRtn = id;
        }
        finally
        {
            this.cleanup(ps);
            this.cleanup(conn);
        }

        return lRtn;
    }

    //将查询结果转为Collection
    private Collection transferResultsetIntoCollection(ResultSet rs) throws Exception
    {
        ArrayList list = new ArrayList();
        ClientTypeSetInfo info = null;
        while (rs.next())
        {
            info = new ClientTypeSetInfo();
            info.setID(rs.getLong("ID"));
            info.setName(rs.getString("sName"));
            info.setStatusID(rs.getLong("nStatusID"));

            list.add(info);
            Log.print("\n\n id=" + info.getID() + "\n\n");
        }

        return list;
    }
    
    /**
     *@description: 检查客户类型是否已经被使用
     *@param clientId
     *@return 已经被使用，返回true;没被使用，返回flase
     */
    private boolean CheckClientType(long clientTypeId) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = new StringBuffer();

        boolean flag = false;
        
        sbSQL.append(" select a.* from  client a,sett_clienttype b   \n");
        sbSQL.append("     where a.nsettclienttypeid = b.id   \n");
        sbSQL.append("         and a.nstatusid = "+Constant.RecordStatus.VALID + "  \n");
        sbSQL.append("         and b.nstatusid = "+Constant.RecordStatus.VALID + "  \n");
        sbSQL.append("         and b.id = "+clientTypeId + "  \n");
        
        Log.print(" 检查客户类型是否已经被使用 : \n"+sbSQL.toString());
        
        try 
        {
            conn = this.getConnection();
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            if( rs != null && rs.next() )
            {
                flag = true;
            }
        }
        finally
        {
            this.cleanup(rs);
            this.cleanup(ps);
            this.cleanup(conn);
            sbSQL.setLength(0);
        }
        
        return flag;
    }
}