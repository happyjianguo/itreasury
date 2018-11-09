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
 * 数据转换目标数据表数据访问的抽象类
 * 子类包括：
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
	 * 具体的目标表中金额，在子类中付值
	 * */
	protected String amountFieldName = "";
	
	/**
	 * 	可以通过此方法更改要更新的数据的字段名，请不要轻易使用此方法，使用前请确认要调用的方法是否由此字段
	 * */
	public void setAmountFieldNameAsPlanAmount(){
		amountFieldName = "planamount";
	}
	
	abstract public void resetAmountFieldName();
	/**
	 * 查询模板树总共有几级 
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
	 * 在查询同一个级别的叶子行项目
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
	 * 如果相同TransactionDate的行项目数据已存在，则update
	 * 否则新增
	 * */
	public void save(TPDestinationDataInfo dataInfo) throws Exception{
		System.out.println("--------dataInfo.getTransactionDate()"+dataInfo.getTransactionDate());
		System.out.println("--------dataInfo.getLineNo()"+dataInfo.getLineNo());
		
		if(isDuplicateData(dataInfo.getTransactionDate(),dataInfo.getLineNo())){//同一天一行只可能有一条数据
			updateByPK(dataInfo);
		}else{
			add(dataInfo);
		}
		
	}
	
	/**
	 * 检测表TREA_TPFORECASTDATA 或TREA_TPACTUALDATA中
	 * 一天的数据是否正确。
	 * 如果已经有数据，并可以与TREA_TPTEMPLATE表可以一一对应，刚返回 1
	 * 如果有数据，但未一一对应，则删除错误数据，返回0
	 * 如果无数据 返回0
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
	 * 数据库新增操作，新增的ID必须在子类set入正确的数值　
	 * @param dataEntity 需要被插入数据库表对应的Data Entity的实例
	 * @param 　
	 * @return 新产生的ID
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
	private long officeID = -1;//办事处
	private long currencyID = -1;//币种
	private Timestamp executeDate = null;//预测执行日期
	private Timestamp transactionDate = null;//被预测的交易数据日期
	private long lineID = -1;//行项目ID
	private String lineNo = null;//行项目编号
	private String lineName = null;//行项目名称
	private long lineLevel = -1;//行项目级次
	private long parentLineID = -1;//上级行项目ID
	private long isLeaf = -1;//是否叶子
	private long authorizedDepartmentID = -1;//所属部门
	private long authorizedUserID = -1;//所属用户
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
	 * 在表Trea_ActualData 或者 Trea_ForcecastData中查询，取数逻辑包含"#"的行项目的数据 的集合。
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
	 * 根据正在执行的抽取操作的类别获取应该使用的DAO的实例
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
	  * [为优化新加未使用]按树结构的父子关系把下一层的数据汇总到本层
	  * @param officeID
	  * @param currencyID
	  * @param transactionDate
	  * @param levelNum   层数
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
