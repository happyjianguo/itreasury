package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeDetailInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.IException;

/**
 * @author zcwang
 * @version 1.1
 */
public class TransferNoticeDetailDao extends ITreasuryDAO
{
	public TransferNoticeDetailDao(Connection con)
	{
		super("CRA_NOTICECONTRACTDETAIL",con);
	}
	public TransferNoticeDetailDao()
	{
		super("CRA_NOTICECONTRACTDETAIL");
	}
	
	public StringBuffer m_sbSelect   = null;
	public StringBuffer m_sbFrom     = null;
	public StringBuffer m_sbWhere    = null;
	public StringBuffer m_sbOrderBy  = null;
	
	/**
	 * 物理删除收款通知单明细
	 * @param transferNoticeID
	 * @throws IException
	 */
	public void  deleteTransferNoticeDetailByNoticeID(long transferNoticeID) throws IException
	{
		StringBuffer sql = new StringBuffer(); 
		sql.append("delete from cra_noticecontractdetail t where t.NOTICEFORMID=?");
		try {
			initDAO();
			prepareStatement(sql.toString());
			transPS.setLong(1, transferNoticeID);
			executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("删除收款通知单明细出错");
		}
		finally
		{
			finalizeDAO();
		}
		
	}
	public Collection  searchTransferNoticeDetailByNoticeID(long transferNoticeID) throws IException
	{
		StringBuffer sql = new StringBuffer(); 
		Collection coll =null;
		sql.append("select * from cra_noticecontractdetail t where t.NOTICEFORMID=?");
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
	        	
	        	prepareStatement(sql.toString());
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, transferNoticeID);
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(TransferNoticeDetailInfo.class);
	        	
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("执行select语句失败", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("数据库操作异常");
		}
		finally
		{
			finalizeDAO();
		}
		
		return coll;
		
	}

}
