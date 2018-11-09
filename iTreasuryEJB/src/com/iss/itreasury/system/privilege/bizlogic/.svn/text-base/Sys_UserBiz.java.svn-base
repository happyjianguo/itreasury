package com.iss.itreasury.system.privilege.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.system.bizdelegation.PrivilegeDelegation;
import com.iss.itreasury.system.privilege.dao.Sys_GroupDAO;
import com.iss.itreasury.system.privilege.dao.Sys_UserDAO;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.system.util.NameRef;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sys_UserBiz {
	
	Sys_UserDAO sys_UserDAO = new Sys_UserDAO();
	
	public PagerInfo queryUser(Sys_UserInfo userInfoCondition, Sys_GroupInfo groupInfoCondition, long lOrderCondition, long lasc, long m_lIsAdminUser ,long m_lUserID) throws Exception
	// TODO Auto-generated method stub
	{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			
            Sys_GroupDAO groupDao = new Sys_GroupDAO();
			
			if (groupInfoCondition != null)
            {
                System.out.println("组条件不为空");
                String strGroupName = groupInfoCondition.getName();
                Collection c = null;
                c = groupDao.findGroupByName(strGroupName);
                if (c.size() == 0){
                	sql = " select * from userinfo where 1 = 2 ";
                }else{
                	long groupId = ((Sys_GroupInfo) c.iterator().next()).getId();
                	sql = sys_UserDAO.queryUserSQL(userInfoCondition, groupId, lOrderCondition, lasc);
                }
            }/*
              * 查询条件不含用户组信息
              */
            else
            {
                sql = sys_UserDAO.queryUserSQL(userInfoCondition, lOrderCondition, lasc);
            }
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			paramMap.put("userInfoCondition", userInfoCondition);
			paramMap.put("groupInfoCondition", groupInfoCondition);
			paramMap.put("lOrderCondition", lOrderCondition);
			paramMap.put("lasc", lasc);
			paramMap.put("m_lIsAdminUser", m_lIsAdminUser);
			paramMap.put("m_lUserID", m_lUserID);

			pagerInfo.setExtensionMothod(Sys_UserBiz.class, "userInfoResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList userInfoResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		try{
			
			Sys_UserInfo userInfoCondition = (Sys_UserInfo)paramMap.get("userInfoCondition");
			Sys_GroupInfo groupInfoCondition = (Sys_GroupInfo)paramMap.get("groupInfoCondition");
			long lOrderCondition = Long.parseLong(paramMap.get("lOrderCondition").toString());
			long lasc = Long.parseLong(paramMap.get("lasc").toString());
			long m_lIsAdminUser = Long.parseLong(paramMap.get("m_lIsAdminUser").toString());
			long m_lUserID = Long.parseLong(paramMap.get("m_lUserID").toString());
			
			if(rs!=null){
				while(rs.next())
				{
					//存储列数据
					cellList = new ArrayList();
					
					if(rs.getTimestamp(10)!=null && rs.getTimestamp(10).toString().equals("0")){
						PagerTools.returnCellList(cellList,rs.getString(2));
				 	}else{
				 		PagerTools.returnCellList(cellList,rs.getString(2)+","+rs.getLong(1));
				 	}
					
					PagerTools.returnCellList(cellList,rs.getString(3));
					
					PagerTools.returnCellList(cellList,PrivilegeConstant.getOfficeNameByID(rs.getLong(4)));
					
					PagerTools.returnCellList(cellList,rs.getString(6)==null?"":rs.getString(6));
					
					PagerTools.returnCellList(cellList,DataFormat.formatDate(rs.getTimestamp(7),DataFormat.FMT_DATE_YYYYMMDD));
					
					PagerTools.returnCellList(cellList,rs.getLong(1));
					
					if((m_lIsAdminUser == 1 && userInfoCondition.getStatusID() == SYSConstant.SysCheckStatus.CHECK) || 
							(m_lUserID==rs.getInt(13) && userInfoCondition.getStatusID() == SYSConstant.SysCheckStatus.UNCHECK)){  //jzw 2010-05-17 修改自身创建的系统管理员不能修改的问题。
						PagerTools.returnCellList(cellList,"1");
					}else{
						PagerTools.returnCellList(cellList,"0");
					} 
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
				}
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	public PagerInfo queryUser4Check(Sys_UserInfo userInfoCondition, Sys_GroupInfo groupInfoCondition, long lOrderCondition, long lasc, long m_lIsAdminUser ,long m_lUserID) throws Exception
	// TODO Auto-generated method stub
	{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			
			Sys_GroupDAO groupDao = new Sys_GroupDAO();
			
			if (groupInfoCondition != null)
            {
                System.out.println("组条件不为空");
                String strGroupName = groupInfoCondition.getName();
                Collection c = null;
                c = groupDao.findGroupByName(strGroupName);
                if (c.size() == 0){
                	sql = " select * from userinfo where 1 = 2 ";
                }else{
                	long groupId = ((Sys_GroupInfo) c.iterator().next()).getId();
                	sql = sys_UserDAO.queryUserSQL(userInfoCondition, groupId, lOrderCondition, lasc);
                	paramMap.put("flag", 1);
                }
            }/*
              * 查询条件不含用户组信息
              */
            else
            {
                sql = sys_UserDAO.queryUserSQL(userInfoCondition, lOrderCondition, lasc);
                paramMap.put("flag", 2);
            }
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			paramMap.put("userInfoCondition", userInfoCondition);
			paramMap.put("groupInfoCondition", groupInfoCondition);
			paramMap.put("lOrderCondition", lOrderCondition);
			paramMap.put("lasc", lasc);
			paramMap.put("m_lIsAdminUser", m_lIsAdminUser);
			paramMap.put("m_lUserID", m_lUserID);

			pagerInfo.setExtensionMothod(Sys_UserBiz.class, "userInfoResult4CheckSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList userInfoResult4CheckSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		int flag = Integer.valueOf(paramMap.get("flag").toString());
		
		try{
			
			Sys_UserInfo userInfoCondition = (Sys_UserInfo)paramMap.get("userInfoCondition");
			Sys_GroupInfo groupInfoCondition = (Sys_GroupInfo)paramMap.get("groupInfoCondition");
			long lOrderCondition = Long.parseLong(paramMap.get("lOrderCondition").toString());
			long lasc = Long.parseLong(paramMap.get("lasc").toString());
			long m_lIsAdminUser = Long.parseLong(paramMap.get("m_lIsAdminUser").toString());
			long m_lUserID = Long.parseLong(paramMap.get("m_lUserID").toString());
			 
			if(rs!=null){
				while(rs.next())
				{
					
					//存储列数据
					cellList = new ArrayList();
					
					
				 	PagerTools.returnCellList(cellList,rs.getString(2)+","+rs.getLong(1));
					
					PagerTools.returnCellList(cellList,rs.getString(3));
					
					PagerTools.returnCellList(cellList,PrivilegeConstant.getOfficeNameByID(rs.getLong(4)));
					
					PagerTools.returnCellList(cellList,rs.getString(6)==null?"":rs.getString(6));
					
					PagerTools.returnCellList(cellList,DataFormat.formatDate(rs.getTimestamp(7),DataFormat.FMT_DATE_YYYYMMDD));
					
					PagerTools.returnCellList(cellList,rs.getLong(9)>0?NameRef.findUserInfoByID(rs.getLong(9)):"");
					
					PagerTools.returnCellList(cellList,rs.getString(10)==null?"":DataFormat.formatDate(DataFormat.getDateTime(rs.getString(10)),DataFormat.FMT_DATE_YYYYMMDD));
					
					PagerTools.returnCellList(cellList,rs.getLong(11)>0?SYSConstant.SysCheckStatus.getName(rs.getLong(11)):"");
					
					PagerTools.returnCellList(cellList,rs.getLong(1));
					
					if((m_lIsAdminUser == 1 && userInfoCondition.getStatusID() == SYSConstant.SysCheckStatus.CHECK) || 
							(m_lUserID==rs.getInt(13) && userInfoCondition.getStatusID() == SYSConstant.SysCheckStatus.UNCHECK)){  //jzw 2010-05-17 修改自身创建的系统管理员不能修改的问题。
						PagerTools.returnCellList(cellList,"1");
					}else{
						PagerTools.returnCellList(cellList,"0");
					} 
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
				}
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
}
