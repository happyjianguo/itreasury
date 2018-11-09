package com.iss.itreasury.creditrating.set.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.creditrating.set.dao.RatingProjectDao;
import com.iss.itreasury.creditrating.set.dao.RatingProjectSubDao;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectSubInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectSubViewInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectViewInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案代理类
 *
 */
public class RatingProjectBiz {

	public long saveRatingProject(RatingProjectInfo info)
		throws IException
	{
		long id = -1;
		try {
			RatingProjectDao rpTao = new RatingProjectDao();
			id = rpTao.add(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		return id;
	}
	
	public void updateRatingProject(RatingProjectInfo info)
		throws IException
	{
		try {
			RatingProjectDao rpTao = new RatingProjectDao();
			rpTao.updateRatingProject(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
	}
	
	public void saveBatchRatingProjectSub(Collection coll)
		throws IException
	{
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			RatingProjectSubDao rpsTao = new RatingProjectSubDao(conn);
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				RatingProjectSubInfo rpsInfo = (RatingProjectSubInfo)it.next();
				rpsTao.add(rpsInfo);
			}
			conn.commit();

			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public void updateBatchRatingProjectSub(Collection coll)
		throws IException
	{
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			RatingProjectSubDao rpsTao = new RatingProjectSubDao(conn);
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				RatingProjectSubInfo rpsInfo = (RatingProjectSubInfo)it.next();
				rpsTao.updateRatingProjectSub(rpsInfo);
			}
			conn.commit();
			
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public void deleteBatchRatingProjectSub(long ratingId)
		throws IException
	{
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			//删除子数据
			RatingProjectSubDao rpsTao = new RatingProjectSubDao(conn);
			RatingProjectDao rpTao = new RatingProjectDao(conn);
			rpsTao.deleteRatingProjectSub(ratingId);
			//删除父数据
			rpTao.deleteRatingProject(ratingId);
			
			conn.commit();
			
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public RatingProjectViewInfo getRatingProjectViewInfo(long id)
		throws IException
	{
		RatingProjectViewInfo rpvInfo = null;
		try {
			RatingProjectDao rpTao = new RatingProjectDao();
			rpvInfo = rpTao.getRatingProjectViewInfo(id);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库查询异常" ,e);
		}
		return rpvInfo;
	}
	
	public Collection findByRatingProjectViewCondition(RatingProjectInfo info)
		throws IException
	{
		Collection coll = null;
		try {
			RatingProjectDao rpTao = new RatingProjectDao();
			coll = rpTao.findByRatingProjectViewCondition(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库查询异常" ,e);
		}
		return coll;
	}
	
	public long getRatingProjectSubRowNum(RatingProjectViewInfo info)
		throws IException
	{
		long rownum = 0;
		try {
			RatingProjectSubDao rpsTao = new RatingProjectSubDao();
			rownum = rpsTao.getRatingProjectSubRowNum(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库查询异常" ,e);
		}
		return rownum;
	}
	
	public Collection findByInitializeViewCondition(RatingProjectViewInfo info)
		throws IException
	{
		Collection coll = null;
		try {
			RatingProjectSubDao rpsTao = new RatingProjectSubDao();
			coll = rpsTao.findByInitializeViewCondition(info);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new IException("数据库查询异常" ,e);
		}
		return coll;
	}
	
	/**
	 * 查询某一指标体系的最大级数
	 * 
	 * @return
	 */
	public long findMaxLevelOfRatingProject(RatingProjectViewInfo projectInfo) throws IException
	{
		long maxLevel = -1;
		try{
			if(projectInfo == null 
					|| projectInfo.getTargetSetupLevelCode() == null
					|| "".equals(projectInfo.getTargetSetupLevelCode()))
			{
				return 0;
			}
			
			RatingProjectDao ratingProjectDAO = new RatingProjectDao();
			maxLevel = ratingProjectDAO.findMaxLevelOfRatingProject(projectInfo.getTargetSetupLevelCode());
		}catch(Exception exp) {
			exp.printStackTrace();
			throw new IException("查询该指标体系的最大级数时出现异常，原因：" + exp.getMessage(), exp);
		}
		
		return maxLevel;
	}
	
	public Collection findByRatingProjectSubViewCondition(RatingProjectViewInfo info)
		throws IException
	{
		Collection coll = null;
		try {
			RatingProjectSubDao rpsTao = new RatingProjectSubDao();
			coll = rpsTao.findByRatingProjectSubViewCondition(info);

		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库查询异常" ,e);
		}
		return coll;
	}
	public long validateProject(long ID) throws IException
	{
		RatingProjectDao dao = new RatingProjectDao();
		try {
			return dao.validateProject(ID);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("校验出错",e);
		}
	}
	
	public long validateRepeat(RatingProjectInfo info)	throws IException
	{
		long lReturn = -1;
		try {
			RatingProjectDao rpTao = new RatingProjectDao();
			lReturn = rpTao.validateRepeat(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		return lReturn;
	}
	
}
