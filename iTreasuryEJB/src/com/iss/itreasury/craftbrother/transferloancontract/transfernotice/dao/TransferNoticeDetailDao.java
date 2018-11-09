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
	 * ����ɾ���տ�֪ͨ����ϸ
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
			throw new IException("ɾ���տ�֪ͨ����ϸ����");
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
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
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
		        throw new ITreasuryDAOException("ִ��select���ʧ��", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		    }
		    /*----------------end of finalize---------------*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("���ݿ�����쳣");
		}
		finally
		{
			finalizeDAO();
		}
		
		return coll;
		
	}

}
