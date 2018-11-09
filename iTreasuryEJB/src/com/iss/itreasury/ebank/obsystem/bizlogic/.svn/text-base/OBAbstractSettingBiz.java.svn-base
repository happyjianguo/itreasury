package com.iss.itreasury.ebank.obsystem.bizlogic;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.dao.*;
import com.iss.itreasury.ebank.obsystem.dataentity.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.bulletin.bizlogic.BulletinBiz;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obsystem.dao.*;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class OBAbstractSettingBiz {
	
	
	OBAbstractSettingDao dao  = null;
	public OBAbstractSettingBiz()
	{
		super();
		dao = new OBAbstractSettingDao();
		// TODO Auto-generated constructor stub
	}
	
	public Collection findAllStandardAbstract(OBAbstractSettingInfo info) throws Exception
	{
		return dao.findAllStandardAbstract(info);
	}
	public long saveStandardAbstract(OBAbstractSettingInfo info) throws Exception
	{
		info.setNstatusid(Constant.RecordStatus.VALID);
		return dao.saveStandardAbstract(info);
	}
	public long getMaxCode(long lOfficeID,long lClientID) throws Exception {
		return dao.getMaxCode(lOfficeID,lClientID);
	}
	public long getMaxId() throws Exception{
		return dao.getMaxId();
	}
	public long deleteStandardAbstract(String lID) throws Exception
	{
		try
		{
			return dao.updateStatus(lID,Constant.RecordStatus.INVALID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	}
	public OBAbstractSettingInfo findStandardAbstractByID(long lID) throws Exception
	{
		try
		{
			return (OBAbstractSettingInfo)dao.findAllStandardAbstractByID(lID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	/**
	 * 标准摘要定义设置
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public PagerInfo query(OBAbstractSettingInfo queryInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("queryInfo", queryInfo);
			
			sql = "select 1 from userinfo";
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(OBAbstractSettingBiz.class, "queryResultSetHandle", paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	/**
	 * 标准摘要定义设置
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryResultSetHandle(ResultSet rs, Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz(); 
		OBAbstractSettingInfo queryInfo = (OBAbstractSettingInfo)paramMap.get("queryInfo");
		Collection OBAbstract = OBAbstractSetting.findAllStandardAbstract(queryInfo);
		
		try{
			int iCount = 0;
	        if( OBAbstract != null )
	        {
	        	
	        	Iterator it = OBAbstract.iterator();
	        	while (it.hasNext() )
	        	{
	        		iCount++;
	        		OBAbstractSettingInfo info = ( OBAbstractSettingInfo ) it.next();
	        		//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,info.getId());		
					PagerTools.returnCellList(cellList,info.getScode()+","+info.getId());
					PagerTools.returnCellList(cellList,info.getSdesc());	
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
	            }
	            
	        }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	
	/**
	 * 往来账户维护
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryClientCapInfo(long lClientID, long lCurrencyID, long lISCPFAccount) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			paramMap.put("lCurrencyID", lCurrencyID);
			paramMap.put("lISCPFAccount", lISCPFAccount);
			//得到查询SQL
			sql = this.getQueryClientCapSQL(lClientID,lCurrencyID,lISCPFAccount);
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(OBAbstractSettingBiz.class, "queryClientCapInfoResultSetHandle", paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	/**
	 * 往来账户维护
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryClientCapInfoResultSetHandle(ResultSet rs, Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long lClientID = Long.valueOf(paramMap.get("lClientID").toString());
		long lCurrencyID = Long.valueOf(paramMap.get("lCurrencyID").toString());
		long lISCPFAccount = Long.valueOf(paramMap.get("lISCPFAccount").toString());
		
		OBSystemDao obSystemDao = new OBSystemDao();
		Collection collection = null;
		collection = obSystemDao.queryPayee(lClientID,lCurrencyID,lISCPFAccount);
		Iterator iterator = null;
		if (collection != null)
		{
            iterator = collection.iterator();
		}
		
		try{
			NameRef nameref = new NameRef();
			while ((iterator != null) && iterator.hasNext())
		 	{
				ClientCapInfo  rf =(ClientCapInfo)iterator.next();
				
        		//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,rf.getID());		
				PagerTools.returnCellList(cellList,rf.getPayeeName()!=null?rf.getPayeeName():"&nbsp;");
				PagerTools.returnCellList(cellList,(rf.getPayeeAccoutNO()!=null?nameref.getNoLineAccountNo(rf.getPayeeAccoutNO()):"&nbsp;")+","+rf.getID());	
				PagerTools.returnCellList(cellList,rf.getPayeeBankName()!=null?rf.getPayeeBankName():"&nbsp;");
				PagerTools.returnCellList(cellList,rf.getPayeeProv()!=null?rf.getPayeeProv():"&nbsp;");
				PagerTools.returnCellList(cellList,rf.getCity()!=null?rf.getCity():"&nbsp;");
				PagerTools.returnCellList(cellList,rf.getSPayeeBankExchangeNO()!=null?rf.getSPayeeBankExchangeNO() :"&nbsp;");
				PagerTools.returnCellList(cellList,rf.getSPayeeBankOrgNO()!=null?rf.getSPayeeBankOrgNO():"&nbsp;");
				PagerTools.returnCellList(cellList,rf.getSPayeeBankCNAPSNO()!=null?rf.getSPayeeBankCNAPSNO():"&nbsp;");
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
            }
	            
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public String getQueryClientCapSQL(long lClientID, long lCurrencyID, long lISCPFAccount){
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" SELECT * FROM OB_PayeeInfo");
		sbSQL.append(" WHERE nclientid =" + lClientID);
		sbSQL.append(" AND ncurrencyid =" + lCurrencyID);
		sbSQL.append(" AND niscpfacct =" + lISCPFAccount);
		sbSQL.append(" AND NSTATUSID =" + OBConstant.RecordStatus.VALID);
		sbSQL.append(" ORDER BY spayeeacctno asc " );
		return sbSQL.toString();
	}
}
