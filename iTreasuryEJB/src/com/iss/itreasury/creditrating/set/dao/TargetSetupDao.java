package com.iss.itreasury.creditrating.set.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.creditrating.set.dataentity.TargetSetupInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-03，信用评级指标体系数据库操作类
 *
 */
public class TargetSetupDao extends ITreasuryDAO {
	
	protected Log4j log4j = new Log4j(Constant.ModuleType.CREDITRATING, this);
	  
	public TargetSetupDao() {
		super("CRERT_TARGETSETUP");
	}

	public TargetSetupDao(Connection conn) {
		super("CRERT_TARGETSETUP", conn);
	}
	
	public long addTargetSetup(TargetSetupInfo info)
		throws ITreasuryDAOException
	{
		long id = -1;
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
	        	id = this.geSequenceID();
	        	info.setId(id);
	        	
	        	long codeId = 10000 + id;
	        	info.setLevelCode(info.getLevelCode() + codeId);
	        	
	        	strSQL = new StringBuffer();
	        	strSQL.append(" INSERT INTO " + this.strTableName + " (");
	        	strSQL.append(" ID, NAME, DESCRIPTION, PATERID, LEVELCODE, CHILDNUM, OFFICEID, CURRENCYID, INPUTUSERID, INPUTDATE, STATE");
	        	strSQL.append(" ) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.addTargetSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, info.getId());
	        	transPS.setString(nIndex++, info.getName());
	        	transPS.setString(nIndex++, info.getDescription());
	        	transPS.setLong(nIndex++, info.getPaterId());
	        	transPS.setString(nIndex++, info.getLevelCode());
	        	transPS.setLong(nIndex++, info.getChildNum());
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setLong(nIndex++, info.getInputUserId());
	        	transPS.setTimestamp(nIndex++, info.getInputDate());
	        	transPS.setLong(nIndex++, info.getState());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行INSERT INTO语句失败", e);
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
		return id;
	}

	public void updateTargetSetup(TargetSetupInfo info)
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
	        	strSQL.append(" t.DESCRIPTION = ?");
	        	strSQL.append(" where t.id = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.updateTargetSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setString(nIndex++, info.getName());
	        	transPS.setString(nIndex++, info.getDescription());
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
	
	public void updateTargetSetupChildNum(long id, String strOperate)
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
	        	if(strOperate.equals("+") || strOperate.equals("-")){
		        	strSQL.append(" update " + this.strTableName + " t");
		        	if(strOperate.equals("+")){
			        	strSQL.append(" set t.CHILDNUM = t.CHILDNUM "+ strOperate +" 1");
			        	strSQL.append(" where t.id = ?");
		        	}
		        	else{
			        	strSQL.append(" set t.CHILDNUM = t.CHILDNUM "+ strOperate +" 1");
			        	strSQL.append(" where t.id = (select a.paterid from "+ this.strTableName +" a where a.id = ?)");
		        	}
	        	}
	        	else {
	        		throw new Exception("执行UPDATE语句失败，操作符错误");
	        	}
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.updateTargetSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
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
	
	public void deleteTargetSetup(TargetSetupInfo info)
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
	        	strSQL.append(" where t.LEVELCODE like ?");
	        	

	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.deleteTargetSetup()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, Constant.RecordStatus.INVALID);
	        	transPS.setString(nIndex++, info.getLevelCode() + "%");

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
	
	public Collection findByRootCondition(TargetSetupInfo info)
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
	        	strSQL.append(" select * from " + this.strTableName + " t");
	        	strSQL.append(" where t.paterid = -1");
	        	if(!info.getName().equals("")){
	        		strSQL.append(" and t.name like ?");
	        	}
	        	strSQL.append(" and t.officeid = ?");
	        	strSQL.append(" and t.currencyid = ?");
	        	strSQL.append(" and t.state = ?");
	        	strSQL.append(" order by t.id");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.findByRootCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	if(!info.getName().equals("")){
	        		transPS.setString(nIndex++, "%" + info.getName() + "%");
	        	}
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(TargetSetupInfo.class);
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
			throw new ITreasuryDAOException("查询异常",e);
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
	
	public Collection findTargetTreeCollection(TargetSetupInfo info)
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
	        	strSQL.append(" select * from " + this.strTableName + " t");
	        	strSQL.append(" where t.LEVELCODE like ?");
	        	strSQL.append(" and t.officeid = ?");
	        	strSQL.append(" and t.currencyid = ?");
	        	strSQL.append(" and t.state = ?");
	        	strSQL.append(" order by t.LEVELCODE");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("TargetSetupDao.getTargetCollection()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setString(nIndex++, info.getLevelCode() + "%");
	        	transPS.setLong(nIndex++, info.getOfficeId());
	        	transPS.setLong(nIndex++, info.getCurrencyId());
	        	transPS.setLong(nIndex++, Constant.RecordStatus.VALID);
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(TargetSetupInfo.class);
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
			throw new ITreasuryDAOException("查询异常",e);
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
	public long validateTargetSetup(long rootID)throws ITreasuryDAOException 
	{
		long lReturn = -1;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_ratingproject t where t.state= ? and t.TARGETSETUPID =? ");
			prepareStatement( buffer.toString());	
			transPS.setLong(1,Constant.RecordStatus.VALID);
			transPS.setLong(2, rootID);
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
