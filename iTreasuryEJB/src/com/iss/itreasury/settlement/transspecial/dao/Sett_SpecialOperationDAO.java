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
 * ���⽻�����õ����ݲ�����
 * 
 * @author wlming
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_SpecialOperationDAO extends SettlementDAO
{
    /**
     * ���ݱ�ʶ��ѯ����ҵ��������Ϣ�ķ����� �߼�˵���� ͨ������ҵ���������͵�ID��������ҵ��������Ϣ
     * 
     * @param lID
     *            long , ����ҵ���������͵�ID
     * @return SpecialOperationInfo, ����ҵ��������Ϣʵ����
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
                soi.setGatheringRelation(rs.getLong("GATHERINGRELATION"));  //ȫ���޸� 2010-5-26 ������2���ֶ�
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
     * ����ҵ�����ͱ�ʶ��ѯ��ʾ��Ϣ�ķ����� 
     * 
     * @param lTransActionTypeID
     *            long , ҵ�����͵�ID
     * @return SpecialOperationInfo, ����ҵ��������Ϣʵ����
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
                {//����ǻ����������ϴ���߻���׼�����ϴ�
                	 soi.setScontent("�����ϸ����,�տ��ϸ����,��������");
                     soi.setIspayaccount(-1);
                     soi.setIspaybank(-1);
                     soi.setIspayGlEntry(1);
                     soi.setIsRecAccount(-1);
                     soi.setIsRecBank(1);
                     soi.setIsRecGlEntry(1);
                }
                else if(lTransActionTypeID == SETTConstant.TransactionType.JGBAKDRAW
                    	|| lTransActionTypeID == SETTConstant.TransactionType.JGRESERVEDRAW)
                {//����ǻ�����������ȡ���߻���׼������ȡ
                     soi.setScontent("�����ϸ����,�տ��ϸ����");
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
                {//����� ��Ϣ����,֧��������裬��践�� 
                     soi.setScontent("�����ϸ����,�տ��ϸ����");
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