package com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.bizlogic;

import java.sql.Connection;

import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dao.RecincliationDao;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dao.RecincliationDetailsDao;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationDtailsInfo;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationInfo;
import com.iss.itreasury.util.Database;

public class RecincliationBiz {
	/**
	 * 新增调节表回收率信息
	 * @param reconcliationInfo
	 * @param reconcliationDtailsInfos
	 * @throws Exception 
	 */
	public void addRecincliationInfo(ReconcliationInfo reconcliationInfo,ReconcliationDtailsInfo[] reconcliationDtailsInfos) throws Exception{
		Connection conn = Database.getConnection();
		conn.setAutoCommit(false);
		RecincliationDao recincliationDao = new RecincliationDao(conn);
		RecincliationDetailsDao recincliationDetailsDao = new RecincliationDetailsDao(conn);
		try{
			long id =recincliationDao.addRecincliation(reconcliationInfo);
			for(int i =0;i<reconcliationDtailsInfos.length;i++){
				reconcliationDtailsInfos[i].setReconciliationID(id);
			}
			recincliationDetailsDao.addRecincliationDetails(reconcliationDtailsInfos);
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new RuntimeException(e);
		}finally{
			conn.close();
		}
	}
	/**
	 * 修改调节表回收率信息
	 * @param reconcliationInfo
	 * @param reconcliationDtailsInfos
	 * @throws Exception 
	 */
	
	public void updateRecincliationInfo(ReconcliationInfo reconcliationInfo,ReconcliationDtailsInfo[] reconcliationDtailsInfos) throws Exception{
		Connection conn = Database.getConnection();
		conn.setAutoCommit(false);
		RecincliationDao recincliationDao = new RecincliationDao(conn);
		RecincliationDetailsDao recincliationDetailsDao = new RecincliationDetailsDao(conn);
		try{
			recincliationDao.update(reconcliationInfo);
			recincliationDetailsDao.updateRecincliationDetails(reconcliationDtailsInfos);
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new RuntimeException(e);
		}finally{
			conn.close();
		}
	}
	/**
	 * 删除调节表回收率信息
	 * @param reconciliationID
	 * @throws Exception 
	 */
	public void deleteRecincliationInfo(long reconciliationID)throws Exception{
		Connection conn = Database.getConnection();
		conn.setAutoCommit(false);
		RecincliationDao recincliationDao = new RecincliationDao(conn);
		RecincliationDetailsDao recincliationDetailsDao = new RecincliationDetailsDao(conn);
		try{
			recincliationDao.deletePhysically(reconciliationID);
			recincliationDetailsDao.deleteRecincliationDetails(reconciliationID);
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new RuntimeException(e);
		}finally{
			conn.close();
		}
	}
}
