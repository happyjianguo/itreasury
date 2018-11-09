package com.iss.itreasury.creditrating.set.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectViewInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案数据库操作类
 *
 */
public class RatingProjectDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.CREDITRATING, this);
	
	public RatingProjectDao() {
		super("CRERT_RATINGPROJECT");
	}

	public RatingProjectDao(Connection conn) {
		super("CRERT_RATINGPROJECT", conn);
	}
	
	public void updateRatingProject(RatingProjectInfo info)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.name = ?,");
	        	strSQL.append(" t.summation = ?,");
	        	strSQL.append(" t.decimals = ?,");
	        	strSQL.append(" t.remark = ?");
	        	strSQL.append(" where t.id = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectDao.updateRatingProject()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setString(nIndex++, info.getName());
	        	transPS.setDouble(nIndex++, info.getSummation());
	        	transPS.setLong(nIndex++, info.getDecimals());
	        	transPS.setString(nIndex++, info.getRemark());
	        	transPS.setLong(nIndex++, info.getId());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行UPDATE语句失败", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	public void deleteRatingProject(long id)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.STATE = ?");
	        	strSQL.append(" where t.id = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectDao.deleteRatingProject()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.INVALID);
	        	transPS.setLong(nIndex++, id);
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行UPDATE语句失败", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	public RatingProjectViewInfo getRatingProjectViewInfo(long id)
		throws ITreasuryDAOException
	{
		RatingProjectViewInfo rpvInfo = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t.*, a.name STANDARDRATINGNAME, b.name TARGETSETUPNAME, b.levelCode targetSetupLevelCode");
	        	strSQL.append(" from " + this.strTableName + " t, CRERT_STANDARDRATING a, CRERT_TARGETSETUP b");
	        	strSQL.append(" where t.state = ?");
	        	strSQL.append(" and a.state = ?");
	        	strSQL.append(" and b.state = ?");
	        	strSQL.append(" and t.id = ?");
	        	strSQL.append(" and t.standardratingid = a.id(+)");
	        	strSQL.append(" and t.targetsetupid = b.id(+)");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectDao.getRatingProjectViewInfo()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, id);
	        	executeQuery();
	        	rpvInfo = (RatingProjectViewInfo)getDataEntityFromResultSet(RatingProjectViewInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行SELECT语句失败", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		if(rpvInfo == null){
			return null;
		}
		else {
			return rpvInfo;
		}
	}
	
	public Collection findByRatingProjectViewCondition(RatingProjectInfo info)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t.*, a.name STANDARDRATINGNAME, b.name TARGETSETUPNAME, b.levelCode targetSetupLevelCode");
	        	strSQL.append(" from " + this.strTableName + " t, CRERT_STANDARDRATING a, CRERT_TARGETSETUP b");
	        	strSQL.append(" where t.officeid = ?");
	        	strSQL.append(" and t.currencyid = ?");
	        	strSQL.append(" and t.state = ?");
	        	strSQL.append(" and a.state = ?");
	        	strSQL.append(" and b.state = ?");
	        	if(!info.getName().equals("")){
	        		strSQL.append(" and t.name like ?");
	        	}
	        	strSQL.append(" and t.standardratingid = a.id(+)");
	        	strSQL.append(" and t.targetsetupid = b.id(+)");
	        	strSQL.append(" order by t.inputdate");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectDao.findByRatingProjectCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	if(!info.getName().equals("")){
	        		transPS.setString(nIndex++, "%" + info.getName() + "%");
	        	}
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(RatingProjectViewInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行SELECT语句失败", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		if(coll == null || coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}

	/**
	 * 根据某一指标体系的根节点的levelcode,返回该指标体系树的最大级数
	 * 
	 * @param levelCode 某一体系根节点的levelcode
	 * @return
	 * @throws Exception
	 */
	public long findMaxLevelOfRatingProject(String levelCode) throws Exception {
		
		long maxLevel = -1;
		try{
			String strSQL = "select max(length(levelcode)/5) maxlevel from crert_targetsetup setup where setup.levelcode like ''|| " + levelCode + "||'%'";
			
			initDAO();
			prepareStatement(strSQL);
			executeQuery();
			if(this.transRS.next()){
				maxLevel = this.transRS.getLong("maxlevel");
			}
		}finally{
			finalizeDAO();
		}
		
		return maxLevel;
	}
	

	/**
	 * @param ID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long validateProject(long ID)throws ITreasuryDAOException 
	{
		long lReturn = -1;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_creditrating t where t.state!= ? and t.RATINGPROJECTID=? ");
			prepareStatement( buffer.toString());	
			transPS.setLong(1,CreRtConstant.CreRtStatus.DELETE);
			transPS.setLong(2, ID);
			executeQuery();
			if(transRS.next())
			{
				lReturn =1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ITreasuryDAOException("校验SQL出错",e);
			
		}finally{
			finalizeDAO();
		}
		
		return lReturn;
	}

	public long validateRepeat(RatingProjectInfo info) throws ITreasuryDAOException {
		
		long lReturn = -1;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			
			if(info.getId() > 0){
				buffer.append("select * from CRERT_RATINGPROJECT t where t.state = ? and t.id <> ? and t.name = ? ");
				prepareStatement( buffer.toString());
				transPS.setLong(1,CreRtConstant.CreRtStatus.SAVE);
				transPS.setLong(2,info.getId());
				transPS.setString(3,info.getName());
			}else {
				buffer.append("select * from CRERT_RATINGPROJECT t where t.state = ? and t.name = ? ");
				prepareStatement( buffer.toString());
				transPS.setLong(1,CreRtConstant.CreRtStatus.SAVE);
				transPS.setString(2,info.getName());
			}
			executeQuery();
			if(transRS.next())
			{
				lReturn =1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ITreasuryDAOException("校验SQL出错",e);
			
		}finally{
			finalizeDAO();
		}
		
		return lReturn;
	}
	
}
