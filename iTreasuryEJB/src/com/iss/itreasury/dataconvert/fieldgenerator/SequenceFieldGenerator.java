/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.fieldgenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SequenceFieldGenerator implements FieldGenerator{
    /**
     * 取sequence的方式产生字段的值
     */
    public Object generateValue(String tableName,String fieldName) {
        Object result = null;
        String strSeqName = "SEQ_" + tableName;
        String sql = "SELECT " + strSeqName + ".nextval from dual";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DataBaseUtil.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
               result = rs.getObject(1);
            }
        } catch (Exception e) {
            throw new TRF_Exception("发生数据库异常");
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, ps, rs);
        }
        return result;
    }

}
