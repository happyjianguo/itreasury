/*
 * Created on 2003-9-26
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dao;

import com.iss.itreasury.settlement.transspecial.dataentity.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.*;
import java.sql.*;

/**
 * 特殊交易设置的数据操作类
 * 
 * @author wlming
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_SpecialOperationDAO extends SettlementDAO
{
    /**
     * 根据标识查询特殊业务设置信息的方法： 逻辑说明： 通过特殊业务设置类型的ID查找特殊业务设置信息
     * 
     * @param lID
     *            long , 特殊业务设置类型的ID
     * @return SpecialOperationInfo, 特殊业务设置信息实体类
     *  
     */
    public SpecialOperationInfo findByID(long lID) throws Exception
    {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        SpecialOperationInfo soi = null;
        try
        {
            con = this.getConnection();
            pstm = con.prepareStatement("select * from sett_specialOperation where id=?");
            pstm.setLong(1, lID);
            rs = pstm.executeQuery();
            while (rs.next())
            {
                soi = new SpecialOperationInfo();
                soi.setId(rs.getLong("ID"));
                soi.setNofficeid(rs.getLong("NOFFICEID"));
                soi.setNCurrencyID(rs.getLong("NCURRENCYID"));
                soi.setNstatusid(rs.getLong("NSTATUSID"));
                soi.setScontent(rs.getString("SCONTENT"));
                soi.setSname(rs.getString("SNAME"));
                soi.setPayRelation(rs.getLong("PAYRELATION"));
                soi.setGatheringRelation(rs.getLong("GATHERINGRELATION"));  //全哨修改 2010-5-26 增加了2个字段
                //jiangqi 2011-07-01
                soi.setIspayaccount(rs.getLong("IspayAccount"));
                soi.setIspaybank(rs.getLong("IspayBank"));
                soi.setIspayGlEntry(rs.getLong("IspayGlEntry"));
                soi.setIsRecAccount(rs.getLong("IsRecAccount"));
                soi.setIsRecBank(rs.getLong("IsRecBank"));
                soi.setIsRecGlEntry(rs.getLong("IsRecGlEntry"));
                
            }
            this.cleanup(rs);
            this.cleanup(pstm);
            this.cleanup(con);
        }
        catch (Exception e)
        {
            this.cleanup(rs);
            this.cleanup(pstm);
            this.cleanup(con);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            this.cleanup(rs);
            this.cleanup(pstm);
            this.cleanup(con);
        }
        return soi;
    }
    
    
    /**
     * 根据业务类型标识查询显示信息的方法： 
     * 
     * @param lTransActionTypeID
     *            long , 业务类型的ID
     * @return SpecialOperationInfo, 特殊业务设置信息实体类
     *  
     */
    public SpecialOperationInfo findByTranActionTypeID(long lTransActionTypeID) throws Exception
    {
        SpecialOperationInfo soi = null;
        
        try
        {
                soi = new SpecialOperationInfo();
                
                if(lTransActionTypeID == SETTConstant.TransactionType.JGBAKUPSAVE
                	|| lTransActionTypeID == SETTConstant.TransactionType.JGRESERVEUPSAVE)
                {//如果是机构备付金上存或者机构准备金上存
                	 soi.setScontent("付款方详细资料,收款方详细资料,银行资料");
                     soi.setIspayaccount(-1);
                     soi.setIspaybank(-1);
                     soi.setIspayGlEntry(1);
                     soi.setIsRecAccount(-1);
                     soi.setIsRecBank(1);
                     soi.setIsRecGlEntry(1);
                }
                else if(lTransActionTypeID == SETTConstant.TransactionType.JGBAKDRAW
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGRESERVEDRAW)
                {//如果是机构备付金提取或者机构准备金提取
                     soi.setScontent("付款方详细资料,收款方详细资料");
                     soi.setIspayaccount(-1);
                     soi.setIspaybank(1);
                     soi.setIspayGlEntry(1);
                     soi.setIsRecAccount(-1);
                     soi.setIsRecBank(-1);
                     soi.setIsRecGlEntry(1);
                 }
                else if(lTransActionTypeID == SETTConstant.TransactionType.JGBAKINTEREST
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGRESERVEINTERES
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGLENDING
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGLENDINGRETURN
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGLENDINGINTEREST)
                {//如果是 利息收入,支出，及拆借，拆借返款 
                     soi.setScontent("付款方详细资料,收款方详细资料");
                     soi.setIspayaccount(-1);
                     soi.setIspaybank(-1);
                     soi.setIspayGlEntry(1);
                     soi.setIsRecAccount(-1);
                     soi.setIsRecBank(-1);
                     soi.setIsRecGlEntry(1);
                 }
                
                soi.setPayRelation(SETTConstant.DebitOrCredit.DEBIT);
                soi.setGatheringRelation(SETTConstant.DebitOrCredit.CREDIT);
                soi.setId(lTransActionTypeID);
                soi.setSname(SETTConstant.TransactionType.getName(lTransActionTypeID));
                
                
                
               
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return soi;
    }
}