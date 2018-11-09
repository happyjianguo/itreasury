/*
 * Created on 2006-9-18
 *
 * Title:				iTreasury
 * @author             	yanliu 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2006
 * @version
 * Description:         
 */
package com.iss.itreasury.evoucher.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.base.VoucherDAOException;
import com.iss.itreasury.evoucher.setting.dataentity.PrintRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author yanliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Vou_PrintRelationDao extends VoucherDAO
{
    public Vou_PrintRelationDao()
    {
        super("Vou_PrintRelation");
    }
    
    public Vou_PrintRelationDao(Connection con)
    {
        super("Vou_PrintRelation",con);
    }

    /*
     *  查找办事处和币种下的某交易类型所有打印关联设置
     */
    public PrintRelationInfo findByMultiOption(PrintRelationInfo qInfo)
            throws VoucherDAOException
    {
        String strSQL = "";
        String strSQL1 = "";
        PrintRelationInfo returnInfo = new PrintRelationInfo();
        Vector v_whlRelation = new Vector();
        Vector v_covRelation = new Vector();
        PreparedStatement ps = null;
        
        returnInfo.setCurrencyID(qInfo.getCurrencyID());
        returnInfo.setOfficeID(qInfo.getOfficeID());
        returnInfo.setTransTypeID(qInfo.getTransTypeID());
        
        try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            /**
             * TODO 查询数据库获取关联设置集合(查询出所有模版，用状态标志位statusid标志该模版是否被关联到该交易)
             */ 
            
            // 查询所以全打模版，用状态标志位statusid标志该模版是否被关联到该交易
            strSQL = "select a.*,b.name as name,c.id as printSerialID,c.serialID,c.templatetypeid " +
            		"from vou_PrintRelation a,vou_WholePrintTemplateType b,vou_WholePrintTemplate c " +
            		"where a.printTemplateID(+)=c.id and c.templateTypeID (+)= b.id and a.printTypeID (+)= 1";
            strSQL += " and a.currencyID(+)='";
            strSQL += qInfo.getCurrencyID();
            strSQL += "' and a.officeID(+)='";
            strSQL += qInfo.getOfficeID();
            strSQL += "' and a.transTypeID (+)='";
            strSQL += qInfo.getTransTypeID();
            strSQL += "' order by b.id";
            //log.print("查询全打模版SQL="+strSQL);
            
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs!=null && rs.next()){
            	PrintRelationInfo info = new PrintRelationInfo();
            	info.setPrintTemplateID(rs.getLong("printTemplateID"));
            	info.setPrintTypeID(rs.getLong("printTypeID"));
            	info.setPrintTime(rs.getLong("printTime"));
            	info.setPrintTemplateName(rs.getString("name"));
            	info.setSerialID(rs.getLong("serialID"));
            	info.setTemplateTypeID(rs.getLong("templateTypeID"));
            	info.setPrintSerialID(rs.getLong("printSerialID"));
            	if(rs.getLong("printTemplateID")>0){
            		info.setStatusID(1);
            	}
            	v_whlRelation.add(info);
            }
            if(rs!=null){
            	rs.close();
            	rs = null;
            }
            
            // 查询所以套打模版，用状态标志位statusid标志该模版是否被关联到该交易
            strSQL1 += "select a.*,b.name as name ,b.id as serialID from vou_printrelation a,vou_coverprinttemplate b " +
            		"where a.printtemplateid(+)=b.id and a.printTypeID (+)= 2 and a.currencyid(+)='";
            strSQL1 += qInfo.getCurrencyID();
            strSQL1 += "' and a.officeID(+)='";
            strSQL1 += qInfo.getOfficeID();
            strSQL1 += "' and a.transTypeID (+)='";
            strSQL1 += qInfo.getTransTypeID();
            strSQL1 += "' order by b.id";
            log.print("----------------------------sql===="+strSQL1);
            ps = prepareStatement(strSQL1);
            ResultSet rs1 = ps.executeQuery();
            
            while(rs1!=null && rs1.next()){
            	PrintRelationInfo info = new PrintRelationInfo();
            	info.setPrintTime(rs1.getLong("printTime"));
            	info.setPrintTemplateName(rs1.getString("name"));
            	info.setPrintTemplateID(rs1.getLong("printTemplateID"));
            	info.setPrintTypeID(rs1.getLong("printTypeID"));
            	info.setSerialID(rs1.getLong("serialID"));
            	if(rs1.getLong("printTemplateID")>0){
            		info.setStatusID(1);
            	}
            	v_covRelation.add(info);
            }
            if(rs1!=null){
            	rs1.close();
            	rs1 = null;
            }
            finalizeDAO();
            
            returnInfo.setRelationWholeCollection(v_whlRelation);
            returnInfo.setRelationCoverCollection(v_covRelation);
            
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        return returnInfo;
    }
    
    /*
     *  删除办事处和币种下的某交易类型的所有关联设置
     */
    public long deleteByMultiOption(PrintRelationInfo qInfo)
            throws VoucherDAOException
    {
        String strSQL = "";
        long lReturn = -1;
        long transTypeID = qInfo.getTransTypeID();
        long currencyID = qInfo.getCurrencyID();
        long officeID = qInfo.getOfficeID();
        PreparedStatement ps = null;

        try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            // TODO 删除该办事处、币种下，该交易类型的所有打印关联设置（物理删除）（即sql中限制条件为办事处、币种、交易类型）
            strSQL += "delete from vou_PrintRelation a where a.currencyid = '";
            strSQL += currencyID;
            strSQL += "' and a.officeid = '";
            strSQL += officeID;
            strSQL += "' and a.transtypeid = '";
            strSQL += transTypeID;
            strSQL += "'";
            
            ps = prepareStatement(strSQL);
            ps.executeQuery();
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        return lReturn;
    }

    public long saveNewRelationInfo(PrintRelationInfo qInfo) throws VoucherDAOException
    {
    	long lReturn = -1;
    	PreparedStatement ps = null;
    	String strSQL = "";
    	try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            strSQL += "insert into vou_PrintRelation(currencyID,officeID,transTypeID,printTemplateID,printTypeID,printTime) values(?,?,?,?,?,?)";
            ps = prepareStatement(strSQL);
            ps.setLong(1 , qInfo.getCurrencyID());
            ps.setLong(2 , qInfo.getOfficeID());
            ps.setLong(3 , qInfo.getTransTypeID());
            ps.setLong(4 , qInfo.getPrintTemplateID());
            ps.setLong(5 , qInfo.getPrintTypeID());
            ps.setLong(6 , qInfo.getPrintTime());
            lReturn = ps.executeUpdate();
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return lReturn;
    }
    
    public static void main(String[] args) throws VoucherDAOException
    {
//    	Vou_PrintRelationDao dao = new Vou_PrintRelationDao();
//    	PrintRelationInfo qInfo = new PrintRelationInfo();
//    	qInfo.setCurrencyID(1);
//    	qInfo.setOfficeID(1);
//    	qInfo.setTransTypeID(1);
//    	qInfo.setPrintTemplateID(1);
//    	qInfo.setPrintTypeID(1);
//    	qInfo.setPrintTime(1);
//    	dao.deleteByMultiOption(qInfo);
    }
}
