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
package com.iss.itreasury.evoucher.setting.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.Vou_PrintRelationDao;
import com.iss.itreasury.evoucher.setting.dataentity.PrintRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author yanliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PrintRelationBiz extends VoucherBaseBean implements java.io.Serializable
{
    /**
     * No argument constructor required by container.
     */
    public PrintRelationBiz()
    {
    }

    /**
     * 保存操作
     */
    public long save(PrintRelationInfo info) throws VoucherException
    {
        long lReturn = info.getTransTypeID();
        Connection con = null;

        try
        {
        	con = Database.getConnection();
            Vou_PrintRelationDao dao = new Vou_PrintRelationDao(con);
            
            /* 删除该办事处、币种下该交易类型的所有关联设置 */
            dao.deleteByMultiOption(info);
            
            /* 插入全打类型 */
        	if(info!= null && info.getRelationWholeCollection() != null)
        	{        		       		
        		Iterator it = info.getRelationWholeCollection().iterator();
        		while(it!=null && it.hasNext())
				{					
        			PrintRelationInfo detailInfo = (PrintRelationInfo)it.next();																
		        	/* 重新该办事处、币种下该交易类型的关联设置 */
		        	dao.saveNewRelationInfo(detailInfo);		        			
				}					
			}
        	/* 插入套打类型 */
        	if(info!= null && info.getRelationCoverCollection() != null)
        	{        		       		
        		Iterator it = info.getRelationCoverCollection().iterator();
        		while(it!=null && it.hasNext())
				{					
        			PrintRelationInfo detailInfo = (PrintRelationInfo)it.next();																
		        	/* 重新该办事处、币种下该交易类型的关联设置 */
		        	dao.saveNewRelationInfo(detailInfo);		        			
				}					
			}
        } 
        catch (Exception e)
        {
            throw new VoucherException("Gen_E001", e);
        }
        finally
        {
        	this.finalizeConnection(con);
        }
        return lReturn;
    }

    /**
     * 删除操作
     */
    public long delete(PrintRelationInfo info) throws VoucherException
    {
        long lReturn = -1;
        Vou_PrintRelationDao dao = new Vou_PrintRelationDao();

        try
        {
            /* 删除设置表 */
            lReturn = dao.deleteByMultiOption(info);
        } catch (Exception e)
        {
            throw new VoucherException("Gen_E001", e);
        }
        return lReturn;
    }

    /**
     * 查询某交易类型打印关联设置操作
     */
    public PrintRelationInfo findByMultiOption(PrintRelationInfo qInfo)
            throws VoucherException
    {
        PrintRelationInfo returnInfo = new PrintRelationInfo();

        try
        {
            Vou_PrintRelationDao dao = new Vou_PrintRelationDao();
        	
        	returnInfo = dao.findByMultiOption(qInfo);
        } 
        catch (Exception e)
        {
            throw new VoucherException("Gen_E001", e);
        }

        return returnInfo;
    }
    
}
