/*
 * Created on 2005-5-12
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.system.approval.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalSettingBiz extends Object implements java.io.Serializable
{

	/**
	 * 保存审批设置信息
	 * 新增/修改审批设置信息。
	 * * 信贷、证券模块的审批设置信息是系统上线时初始化加入的，上线后只能修改信息；
	 * * 资金计划模块的审批设置信息是随部门设置信息的添加修改而动态变化的；
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	审批设置信息
	 * @return      long    成功，返回审批设置标示；失败，返回值=-1
	 */
	public long saveApprovalSetting(ApprovalSettingInfo ASInfo) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{			
			con = Database.getConnection();			
			approvaldao = new ApprovalSettingDao(con);
			if(approvaldao.checkByApprovalName(ASInfo.getID(),ASInfo.getName(),ASInfo.getOfficeID())>0)
			{
				lReturn = -2;//存在相同名称的审批流，不能保存
			}
			else
			{
				long lMaxID = approvaldao.findApprovalSettingMaxID();
				if (ASInfo.getID() > 0)
				{
					lReturn = approvaldao.saveApprovalSetting(ASInfo);
				}
				else
				{
					ASInfo.setID(lMaxID);
					// 设置默认值
					/*
					 * ASInfo.setTotalLevel(3); ASInfo.setLowLevel(3);
					 * ASInfo.setIsPass(Constant.YesOrNo.NO);
					 */
					ASInfo.setStatusID(Constant.ApprovalStatus.SAVE);
					lReturn = approvaldao.insertApprovalSetting(ASInfo);
				}

				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
				if (bSucceed)
				{
					// 删除以前的审批用户权限信息(只删除大于新TotalLevel级别的)
					// 审批级别变小了，原来在大于新审批级别已经分配的人员，都要从人员分配表中删除掉
					lReturn = approvaldao.deleteApprovalItemAboveLevel(ASInfo
							.getID(), ASInfo.getTotalLevel());
					if (lReturn > 0)
					{
						bSucceed = true;
					}
					else
					{
						bSucceed = false;
					}
				}
				if (bSucceed)
				{
					// con.commit();
					// lReturn = 1;
					lReturn = lMaxID;// 新增成功，则返回其ID
				}
				else
				{
					// con.rollback();
					lReturn = -1;
				}

				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println(
				"ApprovalBiz.saveApprovalSetting() failed.  Exception is " +
				e.toString());
			//con.rollback();
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	}

	/**
	 * 删除审批设置信息
	 * * 信贷、证券模块的审批设置信息是系统上线时初始化加入的，上线后只能修改信息；
	 * * 资金计划模块的审批设置信息是随部门设置信息的添加修改而动态变化的；
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	审批设置信息
	 * @return      long    成功，返回审批设置标示；失败，返回值=-1
	 */
	public long deleteApprovalSetting(long lApprovalID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalSettingDao(con);
			
			if (lApprovalID > 0)
			{
			    lReturn = approvaldao.deleteApprovalSetting(lApprovalID);
			}
						
			if (lReturn > 0)
			{
				bSucceed = true;
			}
			else
			{
				bSucceed = false;
			}			
			if (bSucceed)
			{
				//con.commit();
				lReturn = 1;
			}
			else
			{
				//con.rollback();
				lReturn = -1;
			}

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println(
				"ApprovalBiz.saveApprovalSetting() failed.  Exception is " +
				e.toString());
			//con.rollback();
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	}
	
	/**
	 * 查询审批设置信息
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   审批设置标示（查询条件）
	 * @return      ApprovalSettingInfo    返回审批设置信息
	 */
	public ApprovalSettingInfo findApprovalSetting(long lApprovalID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//查询审批设置信息
			ASInfo = approvaldao.findApprovalSetting(lApprovalID);

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println("ApprovalBiz.findApprovalSetting() failed.  Exception is " +	e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (ASInfo);
	}
	//add by ygzhao 05-05-09
	public Collection findApprovalSetting(ApprovalQueryInfo info) throws
	Exception
	{
	    Connection con = null;
	    ApprovalSettingDao approvaldao = null;
	    boolean bSucceed = false;
	    long lReturn = -1;
	    Collection c = null;
	    try
	    {			
	        con = Database.getConnection();
	        approvaldao = new ApprovalSettingDao(con);
	        //查询审批设置信息
	        c = approvaldao.findApprovalSetting(info);
	        con.close();
	        con = null;
	    }
	    catch (Exception e)
	    {
	        if (con != null)
	        {
	            con.close();
	            con = null;
	        }
	        System.out.println("ApprovalBiz.findApprovalSetting(ApprovalQueryInfo info) failed.  Exception is " +	e.toString());
	    }
	    finally
	    {
	        try
	        {
	            if (con != null)
	            {
	                con.close();
	                con = null;
	            }
	        }
	        catch (Exception ex)
	        {
	            throw new Exception(ex.getMessage());
	        }
	    }

	    return c;
	}	
	//

	/**
	 * 保存审批用户权限信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo    审批用户权限信息（选择的用户列表等）
	 * @return      long      成功，返回值=1；失败，返回值=-1
	 */
	public long saveApprovalItem(ApprovalSettingInfo ASInfo) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalSettingDao(con);

			//删除旧的审批用户权限信息
			lReturn = approvaldao.deleteApprovalItem(ASInfo.getID(),
				ASInfo.getLevel());
			if (lReturn > 0)
			{
				bSucceed = true;
			}
			else
			{
				bSucceed = false;
			}
			if (bSucceed)
			{
				//保存新的审批用户权限信息
				lReturn = approvaldao.saveApprovalItem(ASInfo);
				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
			}
			if (bSucceed)
			{
				//更新设置装态为设置中
				approvaldao.changeApprovalSetting(ASInfo.getID());
				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
			}

			if (bSucceed)
			{
				//con.commit();
				lReturn = 1;
			}
			else
			{
				//con.rollback();
				lReturn = -1;
			}
			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println(
				"ApprovalBiz.saveApprovalItem() failed.  Exception is " +
				e.toString());
			//con.rollback();
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	}

	/**
	 * 查询审批用户权限
	 * 通用性强，推荐使用
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID         审批设置标示
	 * @param       lLevel              审批级别
	 * @param       lUserID                 排除的用户
	 * @param       lIsReduplicateAssign	是否允许各级别重复分配人员
	 * @return      ApprovalSettingInfo 返回审批用户权限信息（已选择和可供选择的用户列表等）
	 */
	public ApprovalSettingInfo findApprovalItem(long lApprovalID, long lLevel, long lUserID, long lCurrencyID,long lOfficeID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//查询审批用户权限信息
			ASInfo = approvaldao.findApprovalItem(lApprovalID,lLevel,lUserID,lCurrencyID,lOfficeID);

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println(
				"ApprovalBiz.findApprovalItem() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return (ASInfo);
	}
	/**
	 * 检查审批设置是否全部设置完成，如果没有则不允许对此审批设置进行激活
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @return      long                 1：是；2：设置信息不完整；3：分配人员信息错误
	 */
	public long checkApprovalValidity(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//查询审批设置信息
			lReturn = approvaldao.checkApprovalIntegrality(lApprovalID);
			if (lReturn == Constant.YesOrNo.YES)
			{
			    //查询分配人员信息
			    if (approvaldao.checkApprovalItem(lApprovalID) == 2)
			    {
			        lReturn = 3;
			    }			    
			}
			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.checkApprovalIntegrality() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	}

	/**
	 * 激活审批设置
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示
	 * @return      long                 成功，返回值=1；失败，返回值=-1
	 */
	public long activeApprovalSetting(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);


			//查询审批设置信息
			lReturn = approvaldao.activeApprovalSetting(lApprovalID);

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.activeApprovalSetting() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	}


	/**
	 * 审批设置设为设置中
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示
	 * @return      long                 成功，返回值=1；失败，返回值=-1
	 */
	public long changeApprovalSetting(long lApprovalID) throws Exception
	{
	    Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);			
			lReturn = approvaldao.changeApprovalSetting(lApprovalID);

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.changeApprovalSetting() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lReturn;
	}	


	/**
	 *  查询是否有未审批完成的业务
	 *    ApprovalSetting,  ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          ?ó?ú?è??±ê???¨?é????????
	 * @return      long                 1??????2??????
	 */
	public long checkApprovalTracing(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		long lReturn = -1;
		try 
		{
			
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);			
			lReturn = approvaldao.checkApprovalTracing(lApprovalID);
			
			con.close();
			con = null;
		} 
		catch (SQLException e) {		} 
		catch (Exception e) {		}

		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		
		return lReturn;
	}
	/**检查审批流是否被关联
	 * 
	 * @author weihuang
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public long checkApprovalRelation(long lApprovalID,long officeid) throws Exception{
        long lReturn=-1;
        Connection con = null;
		ApprovalSettingDao approvaldao = null;
	    try{
	        con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);
			lReturn=approvaldao.checkApprovalRelation(lApprovalID,officeid);
			con.close();
			con = null;
	    }catch(Exception ex){
	        
	    }finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
	    
	    return lReturn;
	    
	}
	public static void main(String[] args){
	    long l=-1;
	    ApprovalSettingBiz c=new ApprovalSettingBiz();
	    try {
            l=c.checkApprovalRelation(1,1);
            System.out.println("jieguo="+l);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	 }
}
