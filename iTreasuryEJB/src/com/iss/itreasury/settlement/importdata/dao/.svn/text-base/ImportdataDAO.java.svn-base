package com.iss.itreasury.settlement.importdata.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.importdata.dataentity.ImportdataInfo;
import com.iss.itreasury.settlement.util.SETTHTML;
import com.iss.itreasury.util.Database;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author kenny
 * @version Date of Creation 2005-08-16
 */
public class ImportdataDAO extends SettlementDAO {
    public ImportdataDAO() {
        super("SETT_ImportdataTemp");
    }

    public ImportdataDAO(Connection conn) {
        super("SETT_ImportdataTemp", conn);
    }

    //添加一条记录
    //public void add(ImportdataInfo importdataInfo) throws
    // ITreasuryDAOException{super.add(importdataInfo);}
    //删除一条记录
    public long delete(long id) throws Exception {
        return super.delete(id);
    }

    //更新一条几记录
    public void update(ImportdataInfo importdataInfo) throws Exception {
        super.update(importdataInfo);
    }

    //物理删除一条记录
    public void deletePhysically(long id) throws ITreasuryDAOException {
        super.deletePhysically(id);
    }

    //通过条件查询
    public Collection findByCondition(ImportdataInfo importdataInfo)
            throws ITreasuryDAOException {
        String orderByStr="order by to_number(currentNo)";
        return super.findByCondition(importdataInfo,orderByStr);
    }

    //更新一条几记录
    public long updateStatus(long id, long statusId) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("update " + this.strTableName + " set recordstatus="
                + statusId + " where id=" + id);
        transConn = null;
        transConn = Database.getConnection();
        //this.initDAO();
        this.transPS = this.transConn.prepareStatement(sb.toString());
        long lReturn = this.executeUpdate();
        this.finalizeDAO();
        return lReturn;
    }

    public static void main(String[] args) {
      System.out.println("ok");
    }
}