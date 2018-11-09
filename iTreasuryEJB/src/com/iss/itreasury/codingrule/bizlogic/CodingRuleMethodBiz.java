/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         编码规则中获取流水号      
 */
package com.iss.itreasury.codingrule.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.codingrule.dao.Sys_CodingRuleRelationDao;
import com.iss.itreasury.codingrule.dao.Sys_SerialNumberDao;
import com.iss.itreasury.codingrule.dao.Sys_SerialNumberDetailDao;
import com.iss.itreasury.codingrule.dataentity.CodingRuleRelationInfo;
import com.iss.itreasury.codingrule.dataentity.SerialnumberInfo;
import com.iss.itreasury.codingrule.util.CodingRuleFormat;
import com.iss.itreasury.system.approval.dao.InutApprovalRecordDao;
import com.iss.itreasury.system.approval.dao.InutApprovalRelationDao;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CodingRuleMethodBiz implements java.io.Serializable
{
	private boolean isSelfManagedConn = false;
	/**
     * 获取流水号
     */
    public String getSerialNumber(HashMap paraMap) throws Exception
    {
        String strReturn = "";
        Connection conn = null;
        
        try
        {
        	if(paraMap.get("connection") == null)
			{
				conn = Database.getConnection();
			}			
			else
			{
				conn = (Connection)paraMap.get("connection");
				isSelfManagedConn = true;
			}
        	
        	//根据HashMap参数获取编码规则id
        	Sys_SerialNumberDao serialNumberDao = new Sys_SerialNumberDao(conn);
			CodingRuleRelationInfo relationInfo = new CodingRuleRelationInfo();
			SerialnumberInfo serialNumberInfo = new SerialnumberInfo();
			if(paraMap.get("officeID")!=null)
			{
				relationInfo.setOfficeID(CodingRuleFormat.parseLong((String)paraMap.get("officeID")));
			}
			if(paraMap.get("currencyID")!=null)
			{
				relationInfo.setCurrencyID(CodingRuleFormat.parseLong((String)paraMap.get("currencyID")));
			}
			if(paraMap.get("moduleID")!=null)
			{
				relationInfo.setModuleID(CodingRuleFormat.parseLong((String)paraMap.get("moduleID")));
			}
			if(paraMap.get("transTypeID")!=null)
			{
				relationInfo.setTransTypeID(CodingRuleFormat.parseLong((String)paraMap.get("transTypeID")));
			}
			if(paraMap.get("actionID")!=null)
			{
				relationInfo.setActionID(CodingRuleFormat.parseLong((String)paraMap.get("actionID")));
			}
			serialNumberInfo = serialNumberDao.findSerialNumberInfoByRelationInfo(relationInfo);
			
			Timestamp tsToday = null;
			if(relationInfo.getModuleID()==Constant.ModuleType.SETTLEMENT)
			{
				tsToday = Env.getSystemDate(relationInfo.getOfficeID(), relationInfo.getCurrencyID());
			}	
			else
			{
				tsToday = Env.getSystemDate();
			}	
			
			//conn.setAutoCommit(false);
			
			Sys_SerialNumberDetailDao detailDao = new Sys_SerialNumberDetailDao(conn);
			//辅助信息
			serialNumberInfo.setCurrencyID(CodingRuleFormat.parseLong((String)paraMap.get("currencyID")));
			serialNumberInfo.setContractID(CodingRuleFormat.parseLong((String)paraMap.get("contractID")));
			
			strReturn = detailDao.getSerialNumber(serialNumberInfo,tsToday);
						
			//conn.commit();
        } 
        catch (Exception e)
        {
        	//conn.rollback();
        	throw new Exception("获取编码出错：获取流水号出错", e);
        }
        finally
        {       		
        	if (conn != null && conn.isClosed()==false && !isSelfManagedConn )
			{
				//Log.print("关闭连接--开始");
        		conn.close();
        		conn = null;
				//Log.print("关闭连接--结束");
			}
        }       
        return strReturn;
    }
}
