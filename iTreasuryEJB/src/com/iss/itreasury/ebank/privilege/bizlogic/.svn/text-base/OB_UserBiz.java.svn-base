package com.iss.itreasury.ebank.privilege.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.system.bizlogic.EBankbean;
import com.iss.itreasury.system.dataentity.QueryClientSAInfo;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class OB_UserBiz {
	
	OB_UserDAO oB_UserDAO = new OB_UserDAO();
	
	public PagerInfo queryUser(QueryClientSAInfo qInfo, long OfficeID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = oB_UserDAO.queryUserSQL(qInfo);
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("OfficeID", OfficeID);
			pagerInfo.setExtensionMothod(OB_UserBiz.class, "resultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList resultSetHandle(ResultSet rs, Map map) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long nSaid = -1;
		long nOfficeID = -1;
		long isAdmin = -1;
		long Id = -1;
		long isBelongToClient = -1;
		String sLoginNo = "";
		String sClientCode = "";
		String sClientName = "";
		String sOfficeName = "";
		Timestamp dtChangePassword = null;
		long OfficeID = Long.parseLong(map.get("OfficeID").toString());
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					
					Id = rs.getLong("userid");
					nSaid = rs.getLong("nsaid");
					nOfficeID = rs.getLong("officeid");
					isAdmin = rs.getLong("isadmin");
					isBelongToClient = rs.getLong("isbelongtoclient");
					sLoginNo = rs.getString("loginno");
					sClientCode = rs.getString("clientcode");
					sClientName = rs.getString("clientname");
					sOfficeName = rs.getString("officename");
					dtChangePassword = rs.getTimestamp("dtchangepassword");
					
					//存储列数据
					cellList = new ArrayList();
					
					if(nSaid==-1){
						PagerTools.returnCellList(cellList,sClientCode+","+Id+","+sLoginNo+","+"shouDetail"); 
					}else if(nOfficeID == OfficeID){
						PagerTools.returnCellList(cellList,sClientCode+","+Id+","+sLoginNo+","+"modifyCode"); 
					}else{
						PagerTools.returnCellList(cellList,sClientCode); 
					}
					
					PagerTools.returnCellList(cellList,sClientName); 
					
					PagerTools.returnCellList(cellList,sOfficeName);
					
					PagerTools.returnCellList(cellList,sLoginNo);
					
					PagerTools.returnCellList(cellList,SYSConstant.isAdmin.getName(isAdmin));
					
					PagerTools.returnCellList(cellList,DataFormat.formatDate(dtChangePassword));
					
					PagerTools.returnCellList(cellList,Id+","+sLoginNo);
					
					if(isBelongToClient!=2&&nOfficeID==OfficeID){  
						PagerTools.returnCellList(cellList,"1");
					}else{
						PagerTools.returnCellList(cellList,"0");
					} 
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public PagerInfo queryUser4Check(QueryClientSAInfo qInfo) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = oB_UserDAO.queryUser4CheckSQL(qInfo);
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(OB_UserBiz.class, "checkResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList checkResultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long nSaid = -1;
		long nOfficeID = -1;
		long isAdmin = -1;
		long Id = -1;
		long isBelongToClient = -1;
		long nClientId = -1;
		String sLoginNo = "";
		String sClientCode = "";
		String sClientName = "";
		String sOfficeName = "";
		Timestamp dtChangePassword = null;
		String spassword = "";
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					
					nClientId = rs.getLong("nclientid");
					sLoginNo = rs.getString("sloginno");
					sClientCode = rs.getString("clientcode");
					sClientName = rs.getString("clientname");
					sOfficeName = rs.getString("officename");
					spassword = rs.getString("spassword");
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,sClientCode+","+"form_1"+","+sClientCode+","+nClientId+","+sClientName); 
					
					PagerTools.returnCellList(cellList,sClientName);
					
					PagerTools.returnCellList(cellList,sOfficeName);
					
					PagerTools.returnCellList(cellList,sLoginNo);
					
					PagerTools.returnCellList(cellList,"******");
					
					PagerTools.returnCellList(cellList,spassword);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public PagerInfo queryUser4Mng(long lClientID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = oB_UserDAO.getQueryUser4MngSQL(lClientID);
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			pagerInfo.setExtensionMothod(OB_UserBiz.class, "queryUser4MngResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryUser4MngResultSetHandle(ResultSet rs, Map paramMap) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long lClientID = -1;
		lClientID = Long.valueOf(paramMap.get("lClientID").toString());
		try {
			EBankbean userAdmin = new EBankbean();
			Collection c = userAdmin.findUserByClientID(lClientID);
			if( c != null )
			{
				Iterator it = c.iterator();
	
				while( it.hasNext() )
				{
					OB_UserInfo pi = (OB_UserInfo) it.next();
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,pi.getSName()+","+pi.getId()); 
					PagerTools.returnCellList(cellList,pi.getSLoginNo());
					PagerTools.returnCellList(cellList,pi.getNCurrencyId() > 0 ? Constant.CurrencyType.getName(pi.getNCurrencyId()) : "全部");
					PagerTools.returnCellList(cellList,pi.getInputUserName()==null?"上级系统管理员":pi.getInputUserName());
					PagerTools.returnCellList(cellList,DataFormat.getDateString(pi.getInput()));
					PagerTools.returnCellList(cellList,pi.getId());
					
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
	
	public PagerInfo queryUser4Authorized(long lClientID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = oB_UserDAO.getQueryUser4AuthorizedSQL(lClientID);
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			pagerInfo.setExtensionMothod(OB_UserBiz.class, "queryUser4AuthorizedResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryUser4AuthorizedResultSetHandle(ResultSet rs, Map paramMap) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		OB_UserInfo info = null;
		long lClientID = -1;
		lClientID = Long.valueOf(paramMap.get("lClientID").toString());
		try {
			EBankbean bean = new EBankbean();
			ArrayList list = new ArrayList();
			list = bean.findAuthorizedUser(lClientID);
			
			if(list!=null)
			{
				Iterator it = list.iterator();
				while(it.hasNext())
				{
					info = (OB_UserInfo)it.next();
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,info.getSName()==null?"":info.getSName()); 
					PagerTools.returnCellList(cellList,info.getSLoginNo()==null?"":info.getSLoginNo());
					PagerTools.returnCellList(cellList,info.getNCurrencyId()>0?Constant.CurrencyType.getName(info.getNCurrencyId()):"");
					PagerTools.returnCellList(cellList,info.getClientName()==null?"":info.getClientName());
					PagerTools.returnCellList(cellList,info.getId());
					
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
}
