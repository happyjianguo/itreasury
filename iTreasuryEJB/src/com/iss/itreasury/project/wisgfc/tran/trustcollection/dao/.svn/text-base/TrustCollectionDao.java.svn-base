package com.iss.itreasury.project.wisgfc.tran.trustcollection.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.tran.trustcollection.dataentity.TrustCollectionentity;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

public class TrustCollectionDao extends ITreasuryDAO{
	private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
	
	public TrustCollectionDao(){
		super("sett_transtrustcollectiontemp");
	}
	public TrustCollectionDao(Connection conn) {
		super("sett_transtrustcollectiontemp",conn);
	}
	
	 public PageLoader queryTrustCollection(long batchEntity)
	    throws Exception {
	    // 获取SQL字句
	    	getTrustCollectionSQL(batchEntity);
	    // 获取PageLoader对象
	    PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
	        getBaseObject("com.iss.system.dao.PageLoader");

	    // 初始化PageLoader对象、实现查询和分页
	    pageLoader.initPageLoader(
	        new AppContext(),
	        m_sbFrom.toString(),
	        m_sbSelect.toString(),
	        m_sbWhere.toString(),
	        (int) Constant.PageControl.CODE_PAGELINECOUNT,
	        "com.iss.itreasury.project.wisgfc.tran.trustcollection.dataentity.TrustCollectionentity",
	        null);
	    pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
	    return pageLoader;
	}
	 public PageLoader queryTrustCollection()
	 throws Exception {
		 // 获取SQL字句
		 getTrustCollectionSQL();
		 // 获取PageLoader对象
		 PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
		 getBaseObject("com.iss.system.dao.PageLoader");
		 
		 // 初始化PageLoader对象、实现查询和分页
		 pageLoader.initPageLoader(
				 new AppContext(),
				 m_sbFrom.toString(),
				 m_sbSelect.toString(),
				 m_sbWhere.toString(),
				 (int) Constant.PageControl.CODE_PAGELINECOUNT,
				 "com.iss.itreasury.project.wisgfc.tran.trustcollection.dataentity.TrustCollectionentity",
				 null);
		 pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		 return pageLoader;
	 }
	
	private void getTrustCollectionSQL() {
		 m_sbSelect = new StringBuffer();
	        m_sbSelect.append("\n ID id,CONTRACTNO contractNO, \n");
	        m_sbSelect.append("\n ACCOUNTNO accountNO,ACCOUNTINGDATE accountingDate, \n");
	        m_sbSelect.append("\n ABSTRACT sabstract,COLLECTIONAMOUNT collectionAmount, \n");
	        m_sbSelect.append("\n STATUS status,REMARKS remarks,bid isvalidate,BATCHENTITY batchEntity \n");
	        m_sbFrom = new StringBuffer();
	        
	    	m_sbFrom.append("(select a.*,b.id bid from sett_transtrustcollectiontemp a left join sett_account b on a.accountno=b.saccountno) c left join (select scode,npayaccountid from sett_specialContract where sett_specialContract.Nstatusid >0) d on c.contractno=d.scode and c.bid=d.npayaccountid");
	        m_sbWhere = new StringBuffer();
	        m_sbWhere.append(" 1=1 ");
	       
	        if(m_sbOrderBy ==null){
	        	m_sbOrderBy = new StringBuffer();
	        	m_sbOrderBy.append("order by contractNO");
	        }
	}
	private void getTrustCollectionSQL(long batchEntity) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("\n ID id,CONTRACTNO contractNO, \n");
		m_sbSelect.append("\n ACCOUNTNO accountNO,ACCOUNTINGDATE accountingDate, \n");
		m_sbSelect.append("\n ABSTRACT sabstract,COLLECTIONAMOUNT collectionAmount, \n");
		m_sbSelect.append("\n STATUS status,REMARKS remarks,bid isvalidate,BATCHENTITY batchEntity \n");
		m_sbFrom = new StringBuffer();
		
		m_sbFrom.append("(select a.*,b.id bid from sett_transtrustcollectiontemp a left join sett_account b on a.accountno=b.saccountno) c left join (select scode,npayaccountid from sett_specialContract where sett_specialContract.Nstatusid >0) d on c.contractno=d.scode and c.bid=d.npayaccountid");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 ");
		if(batchEntity>0){
			m_sbWhere.append(" and BATCHENTITY ='"+batchEntity+"'");
		}
		if(m_sbOrderBy ==null){
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append("order by contractNO");
		}
	}
	public void importCollectionFromExcel(TrustCollectionentity[] trustCollectionentitys) throws SQLException, ITreasuryDAOException{
		try {
			initDAO();
			transConn.setAutoCommit(false);
			setUseMaxID();
			for(int i = 0; i<trustCollectionentitys.length;i++){
				TrustCollectionentity dataEntity = trustCollectionentitys[i];
				dataEntity.setId(-1);
				StringBuffer buffer = new StringBuffer();
				buffer.append("INSERT INTO " + strTableName + " (\n");
				String[] buffers = getAllFieldNameBuffer(dataEntity,
						DAO_OPERATION_ADD);
				buffer.append(buffers[0]);
				buffer.append("\n) " + "VALUES (\n");
				buffer.append(buffers[1] + ") \n");
	
				String strSQL = buffer.toString();
				log.debug(strSQL);
				prepareStatement(strSQL);
	
				setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_ADD,
						buffers[0].toString());
				executeUpdate();
			}
			transConn.commit();
		} catch (ITreasuryDAOException ide) {
			transConn.rollback();
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
	}
	public long deleteAll() throws ITreasuryDAOException {
		long lRtn = -1;
		try{
		initDAO();
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from " + strTableName + " \n");
		prepareStatement(buffer.toString());
		lRtn = executeUpdate();
		return lRtn;
		}catch(ITreasuryDAOException ide) {
			ide.printStackTrace();
			throw ide;
		}finally{
			finalizeDAO();
		}
	}
	
	public Collection getAllBatchEntity() throws Exception{
		  String strSQL = "";
	        Vector v = new Vector();
	        long recordCount = -1;
	        try
	        {  
	            initDAO();
	            strSQL = " Select distinct BATCHENTITY from sett_transtrustcollectiontemp";
	            prepareStatement(strSQL);
	            ResultSet rs = executeQuery();
	            while (rs != null && rs.next())
	            {
	            	String batchEntity = rs.getString("BATCHENTITY");
	                v.add(batchEntity);
	            }
	            if(rs != null)
	    		{
	    			rs.close();
	    			rs = null;
	    		}
	            
	            finalizeDAO();
	     
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	            throw e ;
	        } finally
	        {
	            try
	            {
	                finalizeDAO();
	            } catch (ITreasuryDAOException e1)
	            {
	                e1.printStackTrace();
	                throw e1;
	            }
	        }
	        return (v.size() > 0 ? v : null);
		}
	public long deleteTrustCollection(long batchEntity) throws Exception {
		String strSQL = "";
        long lReturn = -1;
        try
        {  
            initDAO();
           // transConn.setAutoCommit(false);
            strSQL = "  delete from sett_transtrustcollectiontemp where 1 =1 ";
            if(batchEntity>0){
            	strSQL+=" and BATCHENTITY='"+batchEntity+"'";
            }
            prepareStatement(strSQL);
            System.out.print(strSQL);
            lReturn = executeUpdate();
          // transConn.commit(); 
            finalizeDAO();
     
        } catch (Exception e)
        {
        	//transConn.rollback();
            e.printStackTrace();
            throw e ;
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
                throw e1;
            }
        }
        return lReturn;
	}
	
	public Map getCountInfo(long batchEntity) throws Exception{
		String strSQL = "";
        Map result = new HashMap();
        try
        {  
            initDAO();
            transConn.setAutoCommit(false);
            strSQL = "  select count(*) record, sum(COLLECTIONAMOUNT) amountNum from sett_transtrustcollectiontemp where 1 =1 ";
            if(batchEntity>0){
            	strSQL+=" and BATCHENTITY='"+batchEntity+"'";
            }
            prepareStatement(strSQL);
            executeQuery();
            if(transRS.next()){
            	result.put("record", String.valueOf(transRS.getLong("record")));
            	result.put("amountNum", String.valueOf(transRS.getDouble("amountNum")));
            }else{
            	result.put("record", "0");
            	result.put("amountNum", "0.00");
            }
           strSQL="select count(*) matchRecord from (SELECT  bid isvalidate,BATCHENTITY batchEntity FROM (select a.*, b.id bid from sett_transtrustcollectiontemp a "+
           			" left join sett_account b on a.accountno =b.saccountno) c left join (select scode, npayaccountid from sett_specialContract "+
           			"where sett_specialContract.Nstatusid > 0) d on c.contractno =d.scode and c.bid =d.npayaccountid  )cc where isvalidate is not null ";
           if(batchEntity>0){
           	strSQL+=" and BATCHENTITY='"+batchEntity+"'";
           }
           prepareStatement(strSQL);
           executeQuery();
           if(transRS.next()){
           	result.put("matchRecord", String.valueOf(transRS.getLong("matchRecord")));
           }else{
           	result.put("record", "0");
           }
           transConn.commit(); 
        } catch (Exception e)
        {
        	transConn.rollback();
            e.printStackTrace();
            throw e ;
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
                throw e1;
            }
        }
		return result;
	}
}
