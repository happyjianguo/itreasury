package com.iss.itreasury.creditrating.set.dao;

import java.sql.Connection;
import java.util.Collection;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectSubInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectSubViewInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectViewInfo;
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
public class RatingProjectSubDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.CREDITRATING, this);
	
	public RatingProjectSubDao() {
		super("CRERT_SUBRATINGPROJECT");
	}

	public RatingProjectSubDao(Connection conn) {
		super("CRERT_SUBRATINGPROJECT", conn);
	}
	
	public void updateRatingProjectSub(RatingProjectSubInfo info)
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
	        	strSQL.append(" set t.FULLMARK = ?,");
	        	strSQL.append(" t.RATINGTYPE = ?,");
	        	strSQL.append(" t.RATINGVALUE = ?");
	        	strSQL.append(" where t.id = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectSubDao.updateRatingProjectSub()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setDouble(nIndex++, info.getFullMark());
	        	transPS.setLong(nIndex++, info.getRatingType());
	        	transPS.setString(nIndex++, info.getRatingValue());
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
	
	public void deleteRatingProjectSub(long ratingProjectId)
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
	        	strSQL.append(" where t.ratingProjectId = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectSubDao.deleteRatingProjectSub()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.INVALID);
	        	transPS.setLong(nIndex++, ratingProjectId);
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
	
	public Collection findByInitializeViewCondition(RatingProjectViewInfo info)
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
	        	strSQL.append(" select t.id targetSetupId, t.name targetSetupName, t.description targetSetupDescription, t.paterid targetSetupPaterId, t.levelcode targetSetupLevelCode, count(t.id) targetSetupColnum");
	        	strSQL.append(" from (select a.*, max(a.levelcode) over(order by rownum) as startCode ");
	        	strSQL.append("       from CRERT_TARGETSETUP a");
	        	strSQL.append("       where a.state = ?");
	        	strSQL.append("       and a.officeid = ?");
	        	strSQL.append("       and a.currencyid = ?");
	        	strSQL.append("       and a.levelcode like ?");
	        	strSQL.append("       start with a.levelcode in");
	        	strSQL.append("       (");
	        	strSQL.append("         select b.levelcode from CRERT_TARGETSETUP b");
	        	strSQL.append("         where b.state = ?");
	        	strSQL.append("         and b.officeid = ?");
	        	strSQL.append("         and b.currencyid = ?");
	        	strSQL.append("         and b.levelcode like ?");
	        	strSQL.append("       )");
	        	strSQL.append("       connect by prior a.paterid = a.id");
	        	strSQL.append("       ) t");
	        	strSQL.append(" group by t.id, t.name, t.description, t.paterid, t.levelcode");
	        	strSQL.append(" order by t.levelcode");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectSubDao.findByInitializeViewCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setString(nIndex++, info.getTargetSetupLevelCode() + "%");
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setString(nIndex++, info.getTargetSetupLevelCode() + "%");
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(RatingProjectSubViewInfo.class);
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
	
	public long getRatingProjectSubRowNum(RatingProjectViewInfo info)
		throws ITreasuryDAOException
	{
		long rownum = -1;
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
	        	strSQL.append(" select count(*) rownums from " + this.strTableName + " t");
	        	strSQL.append(" where t.ratingprojectid = " + info.getId());
	        	strSQL.append(" and t.state = " + Constant.RecordStatus.VALID);
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectSubDao.getRatingProjectSubColumn()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	executeQuery();
	        	if(transRS.next()){
	        		rownum = transRS.getLong("rownums");
	        	}
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
		return rownum;
	}
	
	public Collection findByRatingProjectSubViewCondition(RatingProjectViewInfo info)
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
	        	strSQL.append(" select t2.id, t2.ratingprojectid, t2.fullmark, t2.ratingtype, t2.ratingvalue, t2.officeid,");
	        	strSQL.append(" t1.id targetSetupId, t1.name targetSetupName, t1.description targetSetupDescription, t1.paterid targetSetupPaterId, t1.levelcode targetSetupLevelCode, t1.childnum targetSetupChildNum, t1.row_num targetSetupRow, max(length(t1.levelcode)/5) over() - length(t1.levelcode)/5 + 1 targetSetupColnum from");
	        	strSQL.append(" (");
	        	strSQL.append("   select t.*, (select count(a.id) from CRERT_TARGETSETUP a where a.levelcode like t.levelcode || '%' and a.childnum = 0 and a.state = ?) row_num");
	        	strSQL.append("   from CRERT_TARGETSETUP t");
	        	strSQL.append("   where t.levelcode like ?");
	        	strSQL.append("   and t.officeid = ?");
	        	strSQL.append("   and t.currencyid = ?");
	        	strSQL.append("   and t.state = ?");
	        	strSQL.append(" ) t1 left join  "+ this.strTableName +" t2");
	        	strSQL.append(" on t1.id = t2.Targetsetupid");
	        	strSQL.append(" and t2.ratingprojectid = ? ");
	        	strSQL.append(" and t2.state = ? ");
	        	strSQL.append(" order by t1.levelcode");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("RatingProjectDao.findByRatingProjectCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	transPS.setString(nIndex++, info.getTargetSetupLevelCode() + "%");
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
        		transPS.setLong(nIndex++, info.getId());
        		transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(RatingProjectSubViewInfo.class);
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

}
