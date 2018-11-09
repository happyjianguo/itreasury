/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-20
 */
package com.iss.itreasury.treasuryplan.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer;
import com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yehuang
 *
 * ����ת��Ŀ�����ݱ����ݷ��ʵĳ�����
 * ���������
 * Trea_TPForecastDataDAO
 * Trea_TPActualDataDAO
 */
public abstract class AbstractDestinationDataDAO extends TreasuryPlanDAO {
	
	public AbstractDestinationDataDAO(String strTableName,Connection conn){
		super(strTableName,conn);
	}
	
	public AbstractDestinationDataDAO(String strTableName){
		super(strTableName);
	}
	
	/**
	 * �����Ŀ����н��������и�ֵ
	 * */
	protected String amountFieldName = "";
	
	/**
	 * 	����ͨ���˷�������Ҫ���µ����ݵ��ֶ������벻Ҫ����ʹ�ô˷�����ʹ��ǰ��ȷ��Ҫ���õķ����Ƿ��ɴ��ֶ�
	 * */
	public void setAmountFieldNameAsPlanAmount(){
		amountFieldName = "planamount";
	}
	
	abstract public void resetAmountFieldName();
	/**
	 * ��ѯģ�����ܹ��м��� 
	 */
	public long getLevelCountByCondition(long officeID,long currencyID,Timestamp forecastDate) throws Exception
	{
		StringBuffer sbSQL = null;
		long lTemp = -1;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		try {
			String strForecastDate = transferTimestampTo_DateString(forecastDate);		
			
			sbSQL = new StringBuffer();
			sbSQL.append(" select max(linelevel) \n");
			sbSQL.append(" from "+strTableName+" where officeid = ?");
			sbSQL.append(" and currencyid = ?");
			sbSQL.append(" and TransactionDate = to_date(?,'yyyy-mm-dd')");
			
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			int index =1;
			localPS.setLong(index++,officeID);
			localPS.setLong(index++,currencyID);
			localPS.setString(index++,strForecastDate);
			
			ResultSet localRS = localPS.executeQuery();
			
			if (localRS.next())
			{
				lTemp = localRS.getLong(1);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}

		return lTemp;
	}
	
	/**
	 * �ڲ�ѯͬһ�������Ҷ������Ŀ
	 * @param officeID
	 * @param currencyID
	 * @param forecastDate
	 * @param lineLevel
	 * @return
	 * @throws Exception
	 */
	public Collection getSameLevelLeafDestinationData(long officeID,long currencyID,Timestamp forecastDate,long lineLevel) throws Exception{
		StringBuffer sbSQL = null;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);				
		sbSQL = new StringBuffer();
		//Collection c = getDataEntitiesFromResultSet(null);
		ArrayList list;
		try {
			sbSQL.append(" select distinct lineid from "+strTableName+" where officeid = "+ officeID);
			sbSQL.append(" and currencyid = " + currencyID);
			sbSQL.append(" and TransactionDate = "+strForecastDate);
			sbSQL.append(" and lineLevel = "+lineLevel + " and isleaf = 0");
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			ResultSet localRS = executeQuery();
			list = new ArrayList();
			while(localRS.next()){
				long lineid = localRS.getLong("lineid");
				list.add(new Long(lineid));
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return list;
		
	}
	
	public long getSameLevelParentLineID(long officeID,long currencyID,Timestamp forecastDate,long lineLevel,long lineID) throws Exception{
		StringBuffer sbSQL = null;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		String strForecastDate = transferTimestampTo_DateString(forecastDate);				
		sbSQL = new StringBuffer();
		//Collection c = getDataEntitiesFromResultSet(null);
		long parentlineid = 0;
		try {
			sbSQL.append(" select parentlineid from "+strTableName);
			sbSQL.append(" where officeid = ?");
			sbSQL.append(" and currencyid = ?");
			sbSQL.append(" and TransactionDate = to_date(?,'yyyy-mm-dd')");
			sbSQL.append(" and lineLevel = ? ");
			sbSQL.append(" and lineid = ? ");
			//sbSQL.append(" and isleaf = 0");
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			
			int index = 1;
			localPS.setLong(index++,officeID);
			localPS.setLong(index++,currencyID);
			localPS.setString(index++,strForecastDate);
			localPS.setLong(index++,lineLevel);
			localPS.setLong(index++,lineID);
			
			ResultSet localRS = executeQuery();
	
			while(localRS.next()){
				parentlineid = localRS.getLong("parentlineid");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return parentlineid;
	}

	public boolean isDuplicateData(Timestamp transDate,String lineNo) throws Exception{
		StringBuffer sbSQL = null;
		sbSQL = new StringBuffer();
		boolean res;
		try {
			String strForecastDate = transferTimestampToTo_DateString(transDate);		
			sbSQL.append(" select id,"+amountFieldName+" from "+strTableName+" where transactionDate = "+ strForecastDate);
			sbSQL.append(" and lineNo = '" + lineNo+"'");
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			ResultSet localRS = executeQuery();
			res = false;
			if(localRS.next()){
				res = true;
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return res;
	}	
	
	public TPDestinationDataInfo findByTransactionDateAndLineNo(Timestamp transDate,String lineNo) throws Exception{
		StringBuffer sbSQL = null;
		sbSQL = new StringBuffer();
		ArrayList list;
		try {
			String strForecastDate = transferTimestampToTo_DateString(transDate);		
			sbSQL.append(" select * from "+strTableName+" where transactionDate = "+ strForecastDate);
			sbSQL.append(" and lineNo = '" + lineNo+"'");
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			ResultSet localRS = executeQuery();


			list = (ArrayList) getDataEntitiesFromResultSet(null);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		if(list.size() > 0)
			return (TPDestinationDataInfo) list.get(0);
		else
			return null;
		
	}
	
	/**
	 * �����ͬTransactionDate������Ŀ�����Ѵ��ڣ���update
	 * ��������
	 * */
	public void save(TPDestinationDataInfo dataInfo) throws Exception{
		System.out.println("--------dataInfo.getTransactionDate()"+dataInfo.getTransactionDate());
		System.out.println("--------dataInfo.getLineNo()"+dataInfo.getLineNo());
		
		if(isDuplicateData(dataInfo.getTransactionDate(),dataInfo.getLineNo())){//ͬһ��һ��ֻ������һ������
			updateByPK(dataInfo);
		}else{
			add(dataInfo);
		}
		
	}
	
	/**
	 * ����TREA_TPFORECASTDATA ��TREA_TPACTUALDATA��
	 * һ��������Ƿ���ȷ��
	 * ����Ѿ������ݣ���������TREA_TPTEMPLATE�����һһ��Ӧ���շ��� 1
	 * ��������ݣ���δһһ��Ӧ����ɾ���������ݣ�����0
	 * ��������� ����0
	 * @param date
	 * @return
	 */
	public long checkDataOneDay(Timestamp transDate,
			long officeID, 
            long currencyID)throws Exception{
		String sqlStr = "";
		long rnt = 0;
		long rowNum = -1;
		ResultSet rs = null;
		try {
			String strForecastDate = transferTimestampToTo_DateString(transDate);		
			sqlStr = " select count(*) rnum from "
				      +strTableName+" a ,TREA_TPTEMPLATE b " +
				      " where a.transactionDate = "+ strForecastDate
					  +" and b.id = a.lineid "
					  +" and b.STATUSID >0"
					  + " and a.OFFICEID = "+officeID 
					  + " and a.CURRENCYID = "+currencyID;
			prepareStatement(sqlStr);
			rs = executeQuery();
			
			if(rs.next()){
				rowNum = rs.getLong("rnum");
				if(rowNum>0){
					sqlStr = " select count(*) inum from "
						      +" TREA_TPTEMPLATE " +
						      " where STATUSID >0 "
							  + " and OFFICEID = "+officeID 
							  + " and CURRENCYID = "+currencyID;
					prepareStatement(sqlStr);
					rs = executeQuery();
					if(rs.next()){
						if(rowNum==rs.getLong("inum")){
							rnt = 1;
						}else{
							sqlStr = " delete from "
							      +strTableName+"  " +
							      " where transactionDate = "
								  + strForecastDate
								  + " and OFFICEID = "+officeID 
								   + " and CURRENCYID = "+currencyID;
						    prepareStatement(sqlStr);
						    this.executeUpdate();
						}
					}
				}
				
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		
		return rnt;
	}
	
		/**
	 * ���ݿ�����������������ID����������set����ȷ����ֵ��
	 * @param dataEntity ��Ҫ���������ݿ���Ӧ��Data Entity��ʵ��
	 * @param ��
	 * @return �²�����ID
	 * @throws ITreasuryDAOException
	 */		
	public long add(TPDestinationDataInfo dataInfo) throws Exception{
		StringBuffer bufferSQL = new StringBuffer();
		String strCurrentDate = transferTimestampToTo_DateString(dataInfo.getExecuteDate());
		String strForecastDate = transferTimestampToTo_DateString(dataInfo.getTransactionDate());		
		long id = geSequenceID();

		
		 bufferSQL.append("INSERT INTO "+strTableName+" ("); 
		 
		bufferSQL.append("isNeedSum,officeID,authorizedDepartment,transactionDate,"+amountFieldName+",lineNo,lineName,lineID,id,parentLineID,isLeaf,executeDate,lineLevel,authorizedUser,currencyID ");
		if(amountFieldName.compareToIgnoreCase("FORECASTAMOUNT") == 0 && dataInfo.getIsTransformation() == TPConstant.TRUE)
			bufferSQL.append(" ,planamount ");
		bufferSQL.append(") VALUES (");
		bufferSQL.append( dataInfo.getIsNeedSum());
		bufferSQL.append(" , ");
		bufferSQL.append(dataInfo.getOfficeID());		
		bufferSQL.append(" , '");
		if(dataInfo.getAuthorizedDepartment() == null)
			bufferSQL.append("");
		else
			bufferSQL.append(dataInfo.getAuthorizedDepartment());		
		bufferSQL.append("' , ");
		bufferSQL.append(strForecastDate);
		bufferSQL.append(" , ");
		bufferSQL.append(dataInfo.retrieveAmount());		
		bufferSQL.append(" , '");	
		bufferSQL.append(dataInfo.getLineNo());		
		bufferSQL.append("' , '");
		bufferSQL.append(dataInfo.getLineName());		
		bufferSQL.append(" ', ");
		bufferSQL.append(dataInfo.getLineID());		
		bufferSQL.append(" , ");
		bufferSQL.append(id);		
		bufferSQL.append(" , ");
		bufferSQL.append(dataInfo.getParentLineID());		
		bufferSQL.append(" , ");
		bufferSQL.append(dataInfo.getIsLeaf());		
		bufferSQL.append(" , ");
		bufferSQL.append(strCurrentDate);		
		bufferSQL.append(" , ");
		bufferSQL.append(dataInfo.getLineLevel());		
		bufferSQL.append(" , '");
		if(dataInfo.getAuthorizedUser() == null)
			bufferSQL.append("");
		else
			bufferSQL.append(dataInfo.getAuthorizedUser());		
		bufferSQL.append("' , ");
		bufferSQL.append(dataInfo.getCurrencyID());
		if(amountFieldName.compareToIgnoreCase("FORECASTAMOUNT") == 0 && dataInfo.getIsTransformation() == TPConstant.TRUE){
			bufferSQL.append(" , ");
			bufferSQL.append(dataInfo.retrieveAmount());		
		}
						
		bufferSQL.append(" ) ");
		try {
			prepareStatement(bufferSQL.toString());		
			executeQuery();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return id;
	}

	/**
	private long officeID = -1;//���´�
	private long currencyID = -1;//����
	private Timestamp executeDate = null;//Ԥ��ִ������
	private Timestamp transactionDate = null;//��Ԥ��Ľ�����������
	private long lineID = -1;//����ĿID
	private String lineNo = null;//����Ŀ���
	private String lineName = null;//����Ŀ����
	private long lineLevel = -1;//����Ŀ����
	private long parentLineID = -1;//�ϼ�����ĿID
	private long isLeaf = -1;//�Ƿ�Ҷ��
	private long authorizedDepartmentID = -1;//��������
	private long authorizedUserID = -1;//�����û�
*/
	public void updateByPK(TPDestinationDataInfo info) throws Exception{
		String strCurrentDate = transferTimestampToTo_DateString(info.getExecuteDate());
		String strForecastDate = transferTimestampToTo_DateString(info.getTransactionDate());	
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append("update "); 
		bufferSQL.append(strTableName);
		bufferSQL.append(" set ");
		if(amountFieldName.compareToIgnoreCase("FORECASTAMOUNT") == 0 && info.getIsTransformation() == TPConstant.FALSE){
			bufferSQL.append(" planamount ");
			bufferSQL.append(" = "+info.retrieveAmount());
			String date = transferTimestampToTo_DateString(DataFormat.getTreasuryPlanExecuteDate());
			bufferSQL.append(" , INPUTTIME ");
			bufferSQL.append(" = "+date);			
		}else{
			bufferSQL.append(amountFieldName);
			bufferSQL.append(" = "+info.retrieveAmount());						
		}		

/*		if(amountFieldName.compareToIgnoreCase("FORECASTAMOUNT") == 0 && info.getIsTransformation() == TPConstant.TRUE){
			bufferSQL.append(", planamount ");
			bufferSQL.append(" = "+info.retrieveAmount());			
		}*/
		bufferSQL.append(", executeDate ");
		bufferSQL.append(" = "+strCurrentDate);		
		bufferSQL.append(" where transactionDate = "+strForecastDate);
		bufferSQL.append(" and lineNo = '"+info.getLineNo() + "'");
		bufferSQL.append(" and (" + amountFieldName + " != "+info.retrieveAmount());
		if(info.getAuthorizedDepartment() != null)
			bufferSQL.append(" or AUTHORIZEDDEPARTMENT != '"+info.getAuthorizedDepartment()+"'");
		if(info.getAuthorizedUser() != null)
			bufferSQL.append(" or AUTHORIZEDUSER != '"+info.getAuthorizedUser()+"'");
		if(info.getLineName() != null)
			bufferSQL.append(" or LINENAME != '"+info.getLineName()+"'");		
		bufferSQL.append(")");
		bufferSQL.append(" and officeid = "+ info.getOfficeID());
		bufferSQL.append(" and currencyid = "+ info.getCurrencyID());
		
		try {
			prepareStatement(bufferSQL.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

	}
	
	
	public double getAmountByCondition(long officeID,long currencyID,Timestamp forecastDate,long lineID) throws Exception{
		double amount = 0.0;
		try {
			initDAO();
			//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
			String strForecastDate = transferTimestampToTo_DateString(forecastDate);
			
			String strSQL = "Select *  from "+strTableName+" where Officeid = "+ officeID + " and  Currencyid = " + currencyID
			+ "  and TransactionDate = "+strForecastDate+" and LineID = " + lineID;
			PreparedStatement localPS = prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next()){
				amount = localRS.getDouble(amountFieldName);
				System.out.println("---------linename = "+localRS.getString("linename"));
				System.out.println("---------id = "+localRS.getLong("id"));
				System.out.println("---------amount = "+localRS.getDouble(amountFieldName));
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return amount;
	}
	
	public double sumAmountOfSubItems(long officeID,long currencyID,Timestamp forecastDate,long lineID) throws Exception{
		double amount = -1;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		String strForecastDate = transferTimestampTo_DateString(forecastDate);
        PreparedStatement localPS = null;	
        ResultSet         localRS = null;
		try {
			initDAO();
			String strSQL = "Select sum("+amountFieldName+")as sums from "+strTableName
			+" where Officeid = ? and  Currencyid = ?"  
			+ " and TransactionDate = to_date(?,'yyyy-mm-dd') and ParentLineID   =? " ;
			
			localPS = prepareStatement(strSQL);
			int index=1;
			
			localPS.setLong(index++,officeID);
			localPS.setLong(index++,currencyID);
			localPS.setString(index++,strForecastDate);
			localPS.setLong(index++,lineID);
			
			localRS = localPS.executeQuery();
			if(localRS.next()){
				amount = localRS.getDouble("sums");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
		    if (localRS != null) {
                localRS.close();
                localRS = null;
            }
            if (localPS != null) {
                localPS.close();
                localPS = null;
            }
			finalizeDAO();
		}

		return amount;		
	}
	
	/**
	 * �ڱ�Trea_ActualData ���� Trea_ForcecastData�в�ѯ��ȡ���߼�����"#"������Ŀ������ �ļ��ϡ�
	 * @param officeID
	 * @param currencyID
	 * @param forecastDate
	 * @return
	 * @throws Exception
	 */
	public Collection getDataThatNotSum(long officeID,long currencyID,Timestamp forecastDate) throws Exception{
		Collection c;
		try {
			initDAO();
			//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
			String strForecastDate = transferTimestampToTo_DateString(forecastDate);				
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("select * from "+strTableName+" \n");
			bufferSQL.append(" where officeid= "+officeID+" and currencyid= "+currencyID+" \n");
			bufferSQL.append(" and TransactionDate= "+strForecastDate+" \n");
			bufferSQL.append(" and lineid in (select distinct a.id from Trea_TPTemplate a,Trea_TPTemplateItem  b \n");
			bufferSQL.append(" where b.PARAMETER like '#%' and b.lineid = a.id) order by lineid");
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			executeQuery();
			c = getDataEntitiesFromResultSet(null);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return c;
	}
	
	
	/**
	 * ��������ִ�еĳ�ȡ����������ȡӦ��ʹ�õ�DAO��ʵ��
	 * */
	public static AbstractDestinationDataDAO getAmountDAOInstance(long extractingTypeID,Connection conn){
		AbstractDestinationDataDAO dataDAO = null;
		if(extractingTypeID == AbstractTransformer.Extracting_Type_Actual){
			dataDAO = new Trea_TPActualDataDAO(conn);
		}else if(extractingTypeID == AbstractTransformer.Extracting_Type_Forecast){
			dataDAO = new Trea_TPForecastDataDAO(conn);
		}else{
		}
		return dataDAO;
	} 	


	public void setRStoBaseObject(ResultSet rs,TPDestinationDataInfo info) throws ITreasuryDAOException{
		try{
		info.setId(transRS.getLong("ID"));//
		info.setOfficeID(transRS.getLong("officeID"));//
		info.setCurrencyID(transRS.getLong("currencyID"));//
		info.setAuthorizedDepartment(transRS.getString("AuthorizedDepartment"));//
		info.setAuthorizedUser(transRS.getString("AuthorizedUser"));			//
		info.setExecuteDate(transRS.getTimestamp("ExecuteDate"));//	
		info.setTransactionDate(transRS.getTimestamp("transactionDate"));//
		info.setLineID(transRS.getLong("LineID")); //	
		info.setLineNo(transRS.getString("LineNo"));//
		info.setLineName(transRS.getString("LineName"));//
		info.setLineLevel(transRS.getLong("LineLevel"));		
		info.setIsLeaf(transRS.getLong("IsLeaf"));		//
		info.setParentLineID(transRS.getLong("ParentLineID"));//
		}catch(SQLException e){
			throw new ITreasuryDAOException("",e);
		}
	}
	
	
	 public void updateAmountByTransactionDateAndLineID(long officeID,long currencyID,Timestamp transactionDate,long lineID,double amount,Timestamp inputTime) throws Exception{
		try {
			initDAO();
			String strForecastDate = transferTimestampTo_DateString(transactionDate);
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("update "+strTableName+" \n");
			bufferSQL.append(" set "+amountFieldName+ " = ?");  
			
			String strInputTime = "";
			if(inputTime != null){
				strInputTime = transferTimestampTo_DateString(inputTime);
				bufferSQL.append(" ,INPUTTIME = to_date(?,'yyyy-mm-dd')");
			}		
			bufferSQL.append(" where officeid= ? and currencyid= ?");
			bufferSQL.append(" and TransactionDate = to_date(?,'yyyy-mm-dd')\n");
			bufferSQL.append(" and lineid = ?");

			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			
			int index = 1;
			localPS.setDouble(index++,amount);
			if(inputTime != null){
				localPS.setString(index++,strInputTime);
			}
			localPS.setLong(index++,officeID);
			localPS.setLong(index++,currencyID);
			localPS.setString(index++,strForecastDate);
			localPS.setLong(index++,lineID);

			//localPS.setTimestamp(1,transactionDate);		
			localPS.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
 	
	 }
	 
	 
	 /**
	  * [Ϊ�Ż��¼�δʹ��]�����ṹ�ĸ��ӹ�ϵ����һ������ݻ��ܵ�����
	  * @param officeID
	  * @param currencyID
	  * @param transactionDate
	  * @param levelNum   ����
	  * @param inputTime
	  * @throws Exception
	  */
	 
	 public void updateAmountLevel(long officeID,
	 		                       long currencyID,
								   Timestamp transactionDate,
								   long levelNum,
								   Timestamp inputTime
								   ) throws Exception{
		try {
			initDAO();
			String strForecastDate = transferTimestampTo_DateString(transactionDate);
			//StringBuffer bufferSQL = new StringBuffer();
			//bufferSQL.append("update "+strTableName+" \n");
			//bufferSQL.append(" set "+amountFieldName+ " = "+ amount );
			//if(inputTime != null){
			//	String strInputTime = transferTimestampToTo_DateString(inputTime);
			//	bufferSQL.append(" ,INPUTTIME = "+strInputTime);
			//}		
			//bufferSQL.append(" where officeid= "+officeID+" and currencyid= "+currencyID+" and TransactionDate = \n");
			//bufferSQL.append(strForecastDate);
			//bufferSQL.append(" and lineid = "+lineID);
			String strUpdateSql = "update  "+strTableName +"\n"+
			" set "+amountFieldName +" = " +
			"(" +
				" Select sum(round(a."+amountFieldName +",2)) as sums " +
				" from "+strTableName +" a " +
				" where "+strTableName +".LineID=a.ParentLineID " +
				" and "+strTableName +".TransActionDate = a.TransActionDate " 
				//+" and a.isNeedSum = 1 " 
				+")" ;
			
			String strInputTime = "";
            if(inputTime != null){
			strInputTime = transferTimestampTo_DateString(inputTime);
			strUpdateSql +=" ,INPUTTIME = to_date(?,'yyyy-mm-dd')";
			}
            strUpdateSql +=" where TransactionDate = to_date(?,'yyyy-mm-dd')"
			+" and lineLevel = ? and isleaf = 0 ";
            PreparedStatement localPS = prepareStatement(strUpdateSql);
            
            int index=1;
            
            if(inputTime != null){
			localPS.setString(index++,strInputTime);
            }
			localPS.setString(index++,strForecastDate);
			localPS.setLong(index++,levelNum);
			
            localPS.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
	 }
	 
	 
	 public void updateAfterDailyTransform(Timestamp forecastDate) throws Exception{
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);
		StringBuffer bufferSQL1 = new StringBuffer();
		bufferSQL1.append("update "+strTableName+" \n");
		bufferSQL1.append(" a set a.parentlineid=(select id from TREA_TPFORECASTDATA b where b.transactiondate=a.transactiondate and b.lineno=substr(a.lineno,1,length(a.lineno)-4)) where a.transactiondate= "+strForecastDate);

		try {
			prepareStatement(bufferSQL1.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		
		StringBuffer bufferSQL2 = new StringBuffer();
		bufferSQL2.append("update "+strTableName+" \n");
		bufferSQL2.append(" set parentlineid=-1 where transactiondate = "+strForecastDate+" and parentlineid is null ");

		try {
			prepareStatement(bufferSQL2.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		
	 }
	 
		public double getAmountByLineIDAndTransactionDate(long officeID,long currencyID,long lineID,Timestamp transDate) throws Exception{
			String strDate = this.transferTimestampTo_DateString(transDate);
			String strSQL = " select "+amountFieldName+" from "+strTableName
			+" where transactiondate = to_date(?,'yyyy-mm-dd') and lineid =?"
			+" and OFFICEID = ? and CURRENCYID = ?";
			double amount;
			try {  
				PreparedStatement localPS = this.prepareStatement(strSQL);
				
				localPS.setString(1,strDate);
				localPS.setLong(2,lineID);
				localPS.setLong(3,officeID);
				localPS.setLong(4,currencyID);

				ResultSet localRS = localPS.executeQuery();
				amount = 0.0;
				if(localRS.next())
					amount = localRS.getDouble(amountFieldName);
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw e;
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				finalizeDAO();
			}

			return amount;
		}
		
		public double getUnSumAmountByLineIDAndTransactionDate(long officeID,long currencyID,long lineID,Timestamp transDate) throws Exception{
			String strDate = this.transferTimestampTo_DateString(transDate);
			String strSQL = " select sum("+amountFieldName+") sums from "+strTableName
			+" where transactiondate = to_date(?,'yyyy-mm-dd') and SUBSTRB(lineno,1,3) =LPAD(?,3,'0')"
			+" and OFFICEID = ? and CURRENCYID = ? and isneedsum=0";
			double amount;
			try {  
				PreparedStatement localPS = this.prepareStatement(strSQL);
				
				localPS.setString(1,strDate);
				localPS.setLong(2,lineID);
				localPS.setLong(3,officeID);
				localPS.setLong(4,currencyID);

				ResultSet localRS = localPS.executeQuery();
				amount = 0.0;
				if(localRS.next())
					amount = localRS.getDouble("sums");
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw e;
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				finalizeDAO();
			}

			return amount;
		}
	
}
