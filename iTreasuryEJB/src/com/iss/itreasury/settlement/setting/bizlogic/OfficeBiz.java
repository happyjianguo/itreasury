/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_OfficeDAO;
import com.iss.itreasury.settlement.setting.dataentity.OfficeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.privilege.dataentity.QueryOfficeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OfficeBiz
{
	Sett_OfficeDAO dao = null;
	public OfficeBiz()
	{
		dao = new Sett_OfficeDAO();
	}
	public OfficeInfo findOfficeByID(long lID) throws SettlementException
	{
		return dao.findOfficeByID(lID);
	}
	public OfficeInfo findOfficeByIDOnly(long lID) throws SettlementException
	{
		return dao.findOfficeByIDOnly(lID);
	}
	public OfficeInfo findOfficeByID(long lID, long lCurrencyID) throws SettlementException
	{
		return dao.findOfficeByID(lID,lCurrencyID);
	}
	public Collection findAllOffice(long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, boolean isDesc)  throws SettlementException 
	{
		return dao.findAllOffice(lCurrencyID, lPageLineCount,lPageNo, lOrderParam, isDesc);
	}
	public Collection findAllOffice(long lPageLineCount, long lPageNo, long lOrderParam, boolean isDesc)  throws SettlementException 
	{
		return dao.findAllOffice(lPageLineCount,lPageNo, lOrderParam, isDesc);
	}
	public long deleteOffice(long lID) throws SettlementException ,IException
	{
		//modify by kevin(刘连凯) 2011-07-25
		//原删除功能有事务问题：当出现异常了，事务不能回滚
		//return dao.deleteOffice(lID);
		long lReturn=-1;		
		Connection conn=null;
		try{
					
			conn = Database.getConnection();
			conn.setAutoCommit(false);			
			Sett_OfficeDAO officeDao=new Sett_OfficeDAO(conn);	
			long tempID=officeDao.deleteOffice(lID);			
			if(tempID>0){						
				conn.commit();
				lReturn=tempID;
			}
			else{
				conn.rollback();				
			}
			conn.close();
			conn = null;
		}
		catch(SettlementException se)
		{	
			se.printStackTrace();			
			throw new IException("删除失败");
		}
		catch(Exception e){
			
			e.printStackTrace();			
			throw new IException("删除失败");
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();				
			}
		}
		return lReturn;		
	}
	
	public Collection validateOffice(long lID) throws SettlementException 
	{
		return dao.validateOffice(lID);
	}
	
	public long saveOffice(long lID, String strCode, String strName, long lReservedAccountID, String strSubjectCode, long lCurrencyID) throws SettlementException
	{
		return dao.saveOffice(lID, strCode, strName, lReservedAccountID, strSubjectCode, lCurrencyID);
	}
	
	//保存新方法 2008年1月7日 Boxu Add
	public long saveNewOffice(OfficeInfo officeInfo) throws SettlementException,IException
	{
		//modify by kevin(刘连凯) 2011-11-22
		//原保存功能有事务问题：当出现异常了，事务不能回滚
		//return dao.saveNewOffice(officeInfo);
		long lReturn=-1;		
		Connection conn=null;
		try{
					
			conn = Database.getConnection();
			conn.setAutoCommit(false);			
			Sett_OfficeDAO officeDao=new Sett_OfficeDAO(conn);	
			long tempID=officeDao.saveNewOffice(officeInfo);			
			if(tempID>0){						
				conn.commit();
				lReturn=tempID;
			}
			else{
				conn.rollback();				
			}
			conn.close();
			conn = null;
		}
		catch(SettlementException se)
		{	
			se.printStackTrace();			
			throw new IException(se.getMessage());
		}
		catch(Exception e){
			
			e.printStackTrace();			
			throw new IException(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();				
			}
		}
		return lReturn;		
	}
	//更新办事处方法
	public long updateOffice(OfficeInfo officeInfo) throws Exception
	{
		return dao.updateOffice(officeInfo);
	}
	//保存新方法 2011-07-11 jiangqi
	public long saveNewOfficeWithoutSDC(OfficeInfo officeInfo) throws SettlementException,IException
	{
		long lReturn=-1;
		OfficeInfo returnOINfo=new OfficeInfo();
		Connection conn=null;
		try{
					
					//Sett_OfficeDAO OfficeDao=new Sett_OfficeDAO();
					
					//long OfficeLevel= OfficeDao.getOfficeLevelByID(officeInfo.getM_lOfficeID());
							
					//if(OfficeLevel!=SETTConstant.OrganizationLevel.Headquarters)
					//{
					//	throw new IException("该分支机构没有此设置的权限,请联系总部业务人员。");
					//}

					conn = Database.getConnection();
					conn.setAutoCommit(false);
					
					Sett_OfficeDAO officeDao=new Sett_OfficeDAO(conn);
			
					returnOINfo=officeDao.saveNewOfficeWithoutSDC(officeInfo);
					
					if(returnOINfo.getM_lID()>0){
						lReturn=returnOINfo.getM_lID();
						conn.commit();
						conn.close();
						conn = null;
						
		                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
		                {
		                           EncryptManager.getBeanFactory().changeUserPassword(returnOINfo.getSID1(),officeInfo.getStrPassword1());
		                           EncryptManager.getBeanFactory().changeUserPassword(returnOINfo.getSID2(),officeInfo.getStrPassword2());
		                           EncryptManager.getBeanFactory().changeUserPassword(returnOINfo.getJzID(),officeInfo.getStrPassword3());
		                           EncryptManager.getBeanFactory().changeUserPassword(returnOINfo.getJhID(),officeInfo.getStrPassword4());
		                 }
                        
					}
					else{
						conn.rollback();
						conn.close();
						conn = null;
					}
		}
		catch(SettlementException se)
		{	
			se.printStackTrace();
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("e");
			}
			throw new IException("se");
		}
		catch(Exception e){
			
			e.printStackTrace();
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception ee)
			{
				ee.printStackTrace();
				throw new IException("ee");
			}
			throw new IException("新增分支机构异常。");
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("e");
			}
		}
		return lReturn;
		
		
	}
	public long getMaxOfficeCode() throws SettlementException 
	{
		return dao.getMaxOfficeCode();
	}
	public long getHeadOfficeCode() throws SettlementException 
	{
		return dao.getHeadOfficeCode();
	}
	public long getMaxOfficeID() throws SettlementException 
	{
		return dao.getMaxOfficeID();
	}
	
	/**
	 * 2007.6.13 
	 * @param lID 				//办事处 ID
	 * @param lCurrencyID	    //币种ID 字符串
	 * @return
	 * @throws SettlementException
	 */
	public long updateOffiecCurrency(long lID,String lCurrencyID)throws SettlementException{
		return dao.updateOffiecCurrency(lID, lCurrencyID);
	}
	/**
	 * @param lOfficeID
	 * @return
	 * @throws SettlementException
	 */
	public long[] findCurrencyIDsByOfficeID(long lOfficeID)throws SettlementException{
		return dao.findCurrencyIDsByOfficeID(lOfficeID);
	}
	/**
	 * @param lOfficeID
	 * @return String CurrenyId
	 * @throws SettlementException
	 */
	public String findCurrencyIDByOfficeID(long lOfficeID)throws SettlementException{
		return dao.findCurrencyIDByOfficeID(lOfficeID);
	}
	
	public ArrayList findOfficeInformation()throws Exception
	{
		return dao.findOfficeInformation();
	}
	
	public ArrayList findOfficeByAuthority(Sys_UserAuthorityInfo authorityInfo) throws Exception
	{
		return dao.findOfficeByAuthority(authorityInfo);
	}
	
	public ArrayList findOfficeByModule(QueryOfficeInfo queryOfficeInfo)throws Exception
	{
		return dao.findOfficeByModule(queryOfficeInfo);
	}
	//判断该字符串是否为数字
	public boolean isNumber(String str) throws Exception
	{
		boolean isNumber = false;
		if(!str.equals(""))
		{
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher matcher = pattern.matcher(str);
			isNumber = matcher.matches();
		}
		return isNumber;
	}
	
	public static void main(String[] args)
	{
	
	}
}
