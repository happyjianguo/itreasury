package com.iss.itreasury.creditrating.becominginvalid.dao;

import java.sql.PreparedStatement;

import com.iss.itreasury.creditrating.becominginvalid.dataentity.Crert_CreditRatingCancelInfo;
import com.iss.itreasury.creditrating.creditevalution.dao.Crert_CreditRatingRevaluedDDAO;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.IException;


public class Crert_CreditRatingCancelDAO extends ITreasuryDAO{

	
	public Crert_CreditRatingCancelDAO()
	{
		super("CRERT_CREDITRATINGCANCEL");
	}
	
	public long doInsert(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws Exception
	{
		int result = 0;
		long ID = -1;
		StringBuffer sbSQL = new StringBuffer();
		try{

			initDAO();
			ID = this.geSequenceID();
			sbSQL.append("INSERT INTO CRERT_CREDITRATINGCANCEL(ID,RATINGCODE,RATINGPROJECTID,CANCELMARK,OFFICEID,CURRENCYID,");
			sbSQL.append("INPUTUSERID,INPUTDATE,STATE)VALUES(?,?,?,?,?,?,?,SYSDATE,?)");
			prepareStatement(sbSQL.toString());
			transPS.setLong(1,ID);
			transPS.setString(2,crert_CreditRatingCancelInfo.getRatingCode());
			transPS.setLong(3,crert_CreditRatingCancelInfo.getRatingProjectID());
			transPS.setString(4,crert_CreditRatingCancelInfo.getCancelMark());
			transPS.setLong(5,crert_CreditRatingCancelInfo.getOfficeID());
			transPS.setLong(6,crert_CreditRatingCancelInfo.getCurrencyID());
			transPS.setLong(7,crert_CreditRatingCancelInfo.getInputUserID());
			transPS.setLong(8,crert_CreditRatingCancelInfo.getState());
			result = executeUpdate();
			
		}catch(Exception e){
			throw new IException("Gen_E001",e);
		}finally{
            this.finalizeDAO();
		}
		return (result>0?ID:-1);
	}
	
	public long doUpdate(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws Exception
	{
		int result = 0;
		int index = 1;
		StringBuffer sbSQL = new StringBuffer();
		try{
			initDAO();
			sbSQL.append("UPDATE CRERT_CREDITRATINGCANCEL SET RATINGCODE = ?,CANCELMARK = ? ");
			if(crert_CreditRatingCancelInfo.getState() > 0)
			{
			   sbSQL.append(" ,STATE = ? ");
			}
			sbSQL.append(" WHERE ID = ?");
			this.prepareStatement(sbSQL.toString());
			transPS.setString(index++,crert_CreditRatingCancelInfo.getRatingCode());
			transPS.setString(index++,crert_CreditRatingCancelInfo.getCancelMark());
			if(crert_CreditRatingCancelInfo.getState() > 0)
			{
			   transPS.setLong(index++,crert_CreditRatingCancelInfo.getState());
			}
			transPS.setLong(index++,crert_CreditRatingCancelInfo.getID());
			result = this.executeUpdate();
			
		}catch(Exception e){
			throw new IException("Gen_E001",e);
		}finally{
			this.finalizeDAO();
		}
		
		return (result>0?crert_CreditRatingCancelInfo.getID():-1);
	}
	
	public long updateStatus(long status,long ID)throws Exception
	{
		int lReturn = 0;
		try{
			
			initDAO();
			if(CreRtConstant.CreRtStatus.APPROVALED==status)
			{
				this.prepareStatement("UPDATE CRERT_CREDITRATINGCANCEL SET STATE = ?,CANCELDATE = sysdate WHERE ID = ?");
			}
			else
			{
				this.prepareStatement("UPDATE CRERT_CREDITRATINGCANCEL SET STATE = ?,CANCELDATE = null WHERE ID = ?");
			}
			this.transPS.setLong(1,status);
			this.transPS.setLong(2,ID);
			lReturn = this.transPS.executeUpdate();

		}catch(Exception e){
			throw new IException("Gen_E001",e);
	    }finally{
	    	this.finalizeDAO();
	    }
	    return (lReturn>0?ID:-1);
	}
	public long updateCreditRatingStatus(long lStatus,long ID)throws Exception
	{
		int lReturn = 0;
		try{
			
			initDAO();
		    this.prepareStatement("UPDATE CRERT_CREDITRATINGDETAIL SET STATE = ? WHERE ID = ?");
			this.transPS.setLong(1,lStatus);
			this.transPS.setLong(2,ID);
			lReturn = this.transPS.executeUpdate();

		}catch(Exception e){
			throw new IException("Gen_E001",e);
	    }finally{
	    	this.finalizeDAO();
	    }
	    return (lReturn>0?ID:-1);
	}
	
	public boolean checkCancelRatingCode(String ratingCode,long cancelRatingID)throws Exception
	{
		    boolean flag = false;
		    StringBuffer sbSQL = new StringBuffer();
			try{
				initDAO();
				sbSQL.append("SELECT ROWNUM FROM CRERT_CREDITRATINGCANCEL C WHERE C.RATINGCODE LIKE '%"+ratingCode+"%' AND C.STATE != ?");
				if(cancelRatingID > 0)
				{
					sbSQL.append(" AND C.ID != ?");
				}
				prepareStatement(sbSQL.toString());
				transPS.setLong(1,CreRtConstant.CreRtStatus.DELETE);
				if(cancelRatingID > 0)
				{
					transPS.setLong(2,cancelRatingID);
				}
				executeQuery();
				if(transRS != null && transRS.next())
				{
					flag = true;
				}
			}catch(Exception e2){
				throw new IException("Gen_E001",e2);
			}finally{
				this.finalizeDAO();
			}
		   return flag;
	}
	
	public Crert_CreditRatingCancelInfo getCreditRatingCancelByCondition(long ID)throws Exception
	{
		
		Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo = new Crert_CreditRatingCancelInfo();
		
		try{
			initDAO();
			this.prepareStatement(getCreditRatingCancelByConditionSQL());
			this.transPS.setLong(1,ID);
			this.executeQuery();
			while(this.transRS != null && this.transRS.next())
			{
				crert_CreditRatingCancelInfo.setID(transRS.getLong("C1ID"));
				crert_CreditRatingCancelInfo.setRatingCode(transRS.getString("C1RATINGCODE"));
				crert_CreditRatingCancelInfo.setCancelMark(transRS.getString("C1CANCELMARK"));
				crert_CreditRatingCancelInfo.setRatingProjectID(transRS.getLong("C1RATINGPROJECTID"));
				crert_CreditRatingCancelInfo.setInputUserID(transRS.getLong("C1INPUTUSERID"));
				crert_CreditRatingCancelInfo.setInputDate(transRS.getTimestamp("C1INPUTDATE"));
				crert_CreditRatingCancelInfo.setOfficeID(transRS.getLong("C1OFFICEID"));
				crert_CreditRatingCancelInfo.setCurrencyID(transRS.getLong("C1CURRENCYID"));
				crert_CreditRatingCancelInfo.setState(transRS.getLong("C1STATE"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setID(transRS.getLong("C2ID"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setClientID(transRS.getLong("CLIENTID"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setClientCode(transRS.getString("CLIENTCODE"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setClientName(transRS.getString("CLIENTNAME"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setRatingType(transRS.getLong("C2RATINGTYPE"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setRatingProjectName(transRS.getString("C2RATINGPROJECTNAME"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setStateDate(transRS.getTimestamp("C2STATEDATE"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setEndDate(transRS.getTimestamp("C2ENDDATE"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setRatingNumeric(transRS.getDouble("C2RATINGNUMERIC"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setRatingResult(transRS.getString("C2RATINGRESULT"));
				crert_CreditRatingCancelInfo.getCrert_CreditRatingDetailInfo().setReMark(transRS.getString("C2REMARK"));
				
			}
			
		}catch(Exception e){
			throw new IException("Gen_E001",e);
		}finally{
			this.finalizeDAO();
		}
		return crert_CreditRatingCancelInfo;
	}
    public boolean checkSave(long creditRatingID)throws Exception
    {
    	boolean flag = false;
    	try{
    		initDAO();
    		this.prepareStatement("SELECT COUNT(*) AS COUNTS FROM CRERT_CREDITRATINGCANCEL C WHERE C.RATINGPROJECTID = ? AND  C.STATE IN("+CreRtConstant.CreRtStatus.SAVE+","+CreRtConstant.CreRtStatus.APPROVALING+")");
    	    this.transPS.setLong(1,creditRatingID);
    	    this.executeQuery();
    	    if(this.transRS != null && this.transRS.next())
    	    {
    	    	if(this.transRS.getLong("COUNTS") > 0)
    	    	{
    	    		flag = true;
    	    	}
    	    }
    	}catch(Exception e){
    		throw new IException("Gen_E001",e);
    	}finally{
    		this.finalizeDAO();
    	}
    	return flag;
    }
	
	private String getCreditRatingCancelByConditionSQL()
	{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT C1.ID AS C1ID,C1.RATINGCODE AS C1RATINGCODE,C1.CANCELMARK AS C1CANCELMARK,C1.RATINGPROJECTID AS C1RATINGPROJECTID,");
		sbSQL.append("C1.INPUTUSERID AS C1INPUTUSERID,C1.INPUTDATE AS C1INPUTDATE,C1.OFFICEID AS C1OFFICEID,C1.CURRENCYID AS C1CURRENCYID,");
		sbSQL.append("C1.STATE AS C1STATE,");
		sbSQL.append("C2.ID AS C2ID,C2.CLIENTID AS CLIENTID,(SELECT  CL1.SCODE FROM CLIENT CL1 WHERE CL1.ID = C2.CLIENTID) AS CLIENTCODE,C2.RATINGTYPE AS C2RATINGTYPE,");
		sbSQL.append("(SELECT  CL2.SNAME FROM CLIENT CL2 WHERE CL2.ID = C2.CLIENTID) AS CLIENTNAME,C2.RATINGPROJECTNAME AS C2RATINGPROJECTNAME,");
		sbSQL.append("C2.STATEDATE AS C2STATEDATE,C2.ENDDATE AS C2ENDDATE,C2.RATINGNUMERIC AS C2RATINGNUMERIC,C2.RATINGRESULT AS C2RATINGRESULT,");
		sbSQL.append("C2.REMARK AS C2REMARK FROM CRERT_CREDITRATINGCANCEL C1,CRERT_CREDITRATINGDETAIL C2 WHERE C1.RATINGPROJECTID = C2.ID AND C1.ID = ?");
		
		return sbSQL.toString();
	}
	
}