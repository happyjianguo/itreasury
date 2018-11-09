package com.iss.itreasury.creditrating.creditevalution.dao;

import java.util.ArrayList;
import java.util.List;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingRevaluedInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.util.IException;

public class Crert_CreditRatingRevaluedDDAO extends ITreasuryDAO{

	
	public Crert_CreditRatingRevaluedDDAO()
	{
		super("CRERT_CREDITRATINGREVALUED");
	}
	
	public long doInsert(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws Exception 
	{
		int result = 0;
		long ID = -1;
		StringBuffer sbSQL = new StringBuffer();
        
		try{
			initDAO();
			ID = this.geSequenceID();
     		sbSQL.append("INSERT INTO CRERT_CREDITRATINGREVALUED(ID,RATINGCODE,CREDITRATINGID,REVALUEDRESULT,REVALUEDMARK,");
			sbSQL.append("REMARK,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATE,RATINGOLDRESULT)VALUES(?,?,?,?,?,?,?,?,?,sysdate,?,?)");
			prepareStatement(sbSQL.toString());
			transPS.setLong(1,ID);
			transPS.setString(2,crert_CreditRatingRevaluedInfo.getRatingCode());
			transPS.setLong(3,crert_CreditRatingRevaluedInfo.getCreditRatingID());
			transPS.setString(4,crert_CreditRatingRevaluedInfo.getRevalueDresult());
			transPS.setString(5,crert_CreditRatingRevaluedInfo.getRevaluedMark());
			transPS.setString(6,crert_CreditRatingRevaluedInfo.getReMark());
			transPS.setLong(7,crert_CreditRatingRevaluedInfo.getOfficeID());
			transPS.setLong(8,crert_CreditRatingRevaluedInfo.getCurrencyID());
			transPS.setLong(9,crert_CreditRatingRevaluedInfo.getInputUserID());
			transPS.setLong(10,crert_CreditRatingRevaluedInfo.getState());
			transPS.setString(11,crert_CreditRatingRevaluedInfo.getRatingOldResult());
			result = executeUpdate();
			
		}catch(Exception e2){
			
			throw new IException("Gen_E001",e2);
			
		}finally{
			this.finalizeDAO();
		}
		
		return (result>0?ID:-1);
	}
	public long doUpdate(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws Exception
	{
		int result = 0;
		int index = 1;
		StringBuffer stringBufferSQL = new StringBuffer();
		try{
		  
			initDAO();
			stringBufferSQL.append("UPDATE CRERT_CREDITRATINGREVALUED SET RATINGCODE = ?,REVALUEDRESULT = ?,REVALUEDMARK = ?,REMARK = ?");
			if(crert_CreditRatingRevaluedInfo.getState() > 0)
			{
				stringBufferSQL.append(" ,STATE = ?");
			}
			stringBufferSQL.append(" WHERE ID = ?");
			this.prepareStatement(stringBufferSQL.toString());
			this.transPS.setString(index++,crert_CreditRatingRevaluedInfo.getRatingCode());
			this.transPS.setString(index++,crert_CreditRatingRevaluedInfo.getRevalueDresult());
			this.transPS.setString(index++,crert_CreditRatingRevaluedInfo.getRevaluedMark());
			this.transPS.setString(index++,crert_CreditRatingRevaluedInfo.getReMark());
			if(crert_CreditRatingRevaluedInfo.getState() > 0)
			{
				this.transPS.setLong(index++,crert_CreditRatingRevaluedInfo.getState());
			}
			this.transPS.setLong(index++,crert_CreditRatingRevaluedInfo.getID());
			result = this.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new IException("Gen_E001",e);
			
		}finally{
			this.finalizeDAO();
		}
		return (result>0?crert_CreditRatingRevaluedInfo.getID():-1);
	}
	
   //根据信用评级的ID查找出重估记录
   public List getCreditRatingRevaluedList(long creditRatingID)throws Exception
   {
	   List creditRatingRevaluedList = new ArrayList();
	   StringBuffer sbSQL = new StringBuffer();
	   Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo = null;
	   if(creditRatingID <= 0)
	   {
		   return null;
	   }
	   else
	   {
		   try{
			   initDAO();
			   sbSQL.append("SELECT RATING2.REVALUEDDATE AS REVALUEDDATE,RATING2.REVALUEDRESULT AS REVALUEDRESULT,RATING2.REVALUEDMARK AS REVALUEDMARK,");
			   sbSQL.append("RATING2.ID AS ID FROM CRERT_CREDITRATINGDETAIL RATING1,CRERT_CREDITRATINGREVALUED RATING2 WHERE RATING1.ID = RATING2.CREDITRATINGID ");
			   sbSQL.append(" AND RATING1.STATE = RATING2.STATE AND RATING1.State<>"+CreRtConstant.CreRtStatus.DELETE+" AND RATING1.CREDITRATINGID = ? order by RATING2.REVALUEDDATE");
			   prepareStatement(sbSQL.toString());
			   transPS.setLong(1,creditRatingID);
			   executeQuery();
			   while(transRS != null && transRS.next())
			   {
				   crert_CreditRatingRevaluedInfo = new Crert_CreditRatingRevaluedInfo();
				   crert_CreditRatingRevaluedInfo.setID(transRS.getLong("ID"));
				   crert_CreditRatingRevaluedInfo.setRevaluedDate(transRS.getTimestamp("REVALUEDDATE"));
				   crert_CreditRatingRevaluedInfo.setRevaluedMark(transRS.getString("REVALUEDMARK"));
				   crert_CreditRatingRevaluedInfo.setRevalueDresult(transRS.getString("REVALUEDRESULT"));
				   creditRatingRevaluedList.add(crert_CreditRatingRevaluedInfo);
			   }
			   
		   }catch(Exception e){
			   
			   throw new IException("Gen_E001",e);
			   
		   }finally{
			   
			   this.finalizeDAO();
			   
		   }
	   }
	   return creditRatingRevaluedList;
   }
   //根据信用评级结果的ID查询出所有已重估的记录
   public List getCreditEvalutionByCondition (long creditRatingDetailID)throws Exception
   {
	   List creditEvalutionList = new ArrayList();
	   StringBuffer sbSQL = new StringBuffer();
	   Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo = null;
	   if(creditRatingDetailID <= 0)
	   {
		   return null;
	   }
	   else
	   {
		   try{
			   initDAO();
			   sbSQL.append(" SELECT ID,RATINGCODE,CREDITRATINGID,REVALUEDDATE,REVALUEDMARK,REVALUEDRESULT ");
			   sbSQL.append(" FROM CRERT_CREDITRATINGREVALUED WHERE CREDITRATINGID = ? AND STATE = ?");
			   prepareStatement(sbSQL.toString());
			   transPS.setLong(1,creditRatingDetailID);
			   transPS.setLong(2,CreRtConstant.CreRtStatus.APPROVALED);
			   executeQuery();
			   while(transRS != null && transRS.next())
			   {
				   crert_CreditRatingRevaluedInfo = new Crert_CreditRatingRevaluedInfo();
				   crert_CreditRatingRevaluedInfo.setID(transRS.getLong("ID"));
				   crert_CreditRatingRevaluedInfo.setRatingCode(transRS.getString("RATINGCODE"));
				   crert_CreditRatingRevaluedInfo.setCreditRatingID(transRS.getLong("CREDITRATINGID"));
				   crert_CreditRatingRevaluedInfo.setRevaluedDate(transRS.getTimestamp("REVALUEDDATE"));
				   crert_CreditRatingRevaluedInfo.setRevaluedMark(transRS.getString("REVALUEDMARK"));
				   crert_CreditRatingRevaluedInfo.setRevalueDresult(transRS.getString("REVALUEDRESULT"));
				   creditEvalutionList.add(crert_CreditRatingRevaluedInfo);
			   }
			   
		   }catch(Exception e){
			   
			   throw new IException("Gen_E001",e);
			   
		   }finally{
			   
			   this.finalizeDAO();
			   
		   }
	   }
	   return creditEvalutionList;
   }
	
   public boolean checkRatingCode(String ratingCode,long creditRatingRevalued_ID)throws Exception
   {
	   boolean flag = false;
	   StringBuffer sbSQL = new StringBuffer();
	   
	   try{
		    sbSQL.append("SELECT ROWNUM FROM CRERT_CREDITRATINGREVALUED C WHERE C.RATINGCODE LIKE '%"+ratingCode+"%' AND C.STATE != ? ");
		    if(creditRatingRevalued_ID > 0)
		    {
		    	sbSQL.append(" AND C.ID != ? ");
		    }
		    initDAO();
			prepareStatement(sbSQL.toString());
			transPS.setLong(1,CreRtConstant.CreRtStatus.DELETE);
			if(creditRatingRevalued_ID > 0)
			{
				transPS.setLong(2,creditRatingRevalued_ID);
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
   
   public Crert_CreditRatingRevaluedInfo findByID(long ID)throws Exception
   {
	   StringBuffer sbSQL = new StringBuffer();
	   Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo = new Crert_CreditRatingRevaluedInfo();
	   try{
		   if(ID <= 0)
		   {
			   return null;
		   }
		   else
		   {
			   sbSQL.append("SELECT C1.ID AS C1ID,C1.RATINGCODE AS C1RATINGCODE,C1.CREDITRATINGID AS C1CREDITRATINGID,");
			   sbSQL.append("C1.REVALUEDRESULT AS C1REVALUEDRESULT,C1.REVALUEDDATE AS C1REVALUEDDATE,C1.REVALUEDMARK AS C1REVALUEDMARK,");
			   sbSQL.append("C1.REMARK AS C1REMARK,C1.OFFICEID AS C1OFFICEID,C1.CURRENCYID AS C1CURRENCYID,C1.INPUTUSERID AS C1INPUTUSERID,");
			   sbSQL.append("C1.INPUTDATE AS C1INPUTDATE,C1.STATE AS C1STATE,C1.RATINGOLDRESULT AS C1RATINGOLDRESULT,");
			   sbSQL.append("(SELECT CL1.SCODE FROM CLIENT CL1 WHERE CL1.ID = C2.CLIENTID) AS CLIENTCODE,");
			   sbSQL.append("(SELECT CL2.SNAME FROM CLIENT CL2 WHERE CL2.ID = C2.CLIENTID) AS CLIENTNAME,");
			   sbSQL.append("C2.RATINGPROJECTNAME AS C2RATINGPROJECTNAME,C2.RATINGTYPE AS C2RATINGTYPE,");
			   sbSQL.append("C2.STATEDATE AS C2STATEDATE,C2.ENDDATE AS C2ENDDATE,C2.RATINGNUMERIC AS C2RATINGNUMERIC,");
			   sbSQL.append("C2.RATINGRESULT AS C2RATINGRESULT,C2.ID AS C2ID, C2.CLIENTID AS C2CLIENTID,C2.RATINGPROJECTID AS C2RATINGPROJECTID ");
			   sbSQL.append("FROM CRERT_CREDITRATINGREVALUED C1,CRERT_CREDITRATINGDETAIL C2 WHERE C1.CREDITRATINGID = C2.ID AND C1.ID = ?");
			   initDAO();
			   prepareStatement(sbSQL.toString());
			   transPS.setLong(1,ID);
			   executeQuery();
			   while(this.transRS != null && this.transRS.next())
			   {
				   crert_CreditRatingRevaluedInfo.setID(transRS.getLong("C1ID"));
				   crert_CreditRatingRevaluedInfo.setRatingCode(transRS.getString("C1RATINGCODE"));
				   crert_CreditRatingRevaluedInfo.setCreditRatingID(transRS.getLong("C1CREDITRATINGID"));
				   crert_CreditRatingRevaluedInfo.setRevalueDresult(transRS.getString("C1REVALUEDRESULT"));
				   crert_CreditRatingRevaluedInfo.setRevaluedDate(transRS.getTimestamp("C1REVALUEDDATE"));
				   crert_CreditRatingRevaluedInfo.setRevaluedMark(transRS.getString("C1REVALUEDMARK"));
				   crert_CreditRatingRevaluedInfo.setReMark(transRS.getString("C1REMARK"));
				   crert_CreditRatingRevaluedInfo.setOfficeID(transRS.getLong("C1OFFICEID"));
				   crert_CreditRatingRevaluedInfo.setCurrencyID(transRS.getLong("C1CURRENCYID"));
				   crert_CreditRatingRevaluedInfo.setInputUserID(transRS.getLong("C1INPUTUSERID"));
				   crert_CreditRatingRevaluedInfo.setInputDate(transRS.getTimestamp("C1INPUTDATE"));
				   crert_CreditRatingRevaluedInfo.setState(transRS.getLong("C1STATE"));
				   crert_CreditRatingRevaluedInfo.setRatingOldResult(transRS.getString("C1RATINGOLDRESULT"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setID(transRS.getLong("C2ID"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setClientID(transRS.getLong("C2CLIENTID"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setClientCode(transRS.getString("CLIENTCODE"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setClientName(transRS.getString("CLIENTNAME"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setRatingProjectID(transRS.getLong("C2RATINGPROJECTID"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setRatingProjectName(transRS.getString("C2RATINGPROJECTNAME"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setRatingType(transRS.getLong("C2RATINGTYPE"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setStateDate(transRS.getTimestamp("C2STATEDATE"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setEndDate(transRS.getTimestamp("C2ENDDATE"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setRatingNumeric(transRS.getDouble("C2RATINGNUMERIC"));
				   crert_CreditRatingRevaluedInfo.getCrert_CreditRatingDetailInfo().setRatingResult(transRS.getString("C2RATINGRESULT"));
			   }
		   }
		   
	   }catch(Exception e){
		   
		   throw new IException("Gen_E001",e);
		   
	   }finally{
		   
		   this.finalizeDAO();
		   
	   }
	   return crert_CreditRatingRevaluedInfo;
   }
   public Crert_CreditRatingDetailInfo findByIDCreditRatingDetail(long creditID)throws Exception
   {
		StringBuffer sbSQL = new StringBuffer();
		Crert_CreditRatingDetailInfo  crert_CreditRatingDetailInfo = null;
		if(creditID <= 0)
		{
			return null;
		}
		else
		{
			try{
				sbSQL.append(" SELECT ID,CREDITRATINGID,RATINGCODE,CLIENTID,RATINGTYPE,RATINGPROJECTID,RATINGPROJECTNAME,STATEDATE,");
				sbSQL.append(" ENDDATE,RATINGNUMERIC,RATINGRESULT,REMARK,RATINGDATE,OFFICEID,CURRENCYID,INPUTUSERID,INPUTDATE,STATE,");
				sbSQL.append("(SELECT  C1.SCODE FROM CLIENT C1 WHERE C1.ID = CLIENTID) AS CLIENTCODE,");
				sbSQL.append("(SELECT  C2.SNAME FROM CLIENT C2 WHERE C2.ID = CLIENTID) AS CLIENTNAME ");
				sbSQL.append(" FROM CRERT_CREDITRATINGDETAIL CRERT WHERE ID = ?"); 
                initDAO();
				prepareStatement(sbSQL.toString());
				transPS.setLong(1,creditID);
				executeQuery();
				if(transRS != null && transRS.next())
				{
					crert_CreditRatingDetailInfo = new Crert_CreditRatingDetailInfo();
					crert_CreditRatingDetailInfo.setID(transRS.getLong("ID"));
					crert_CreditRatingDetailInfo.setCreditRatingID(transRS.getLong("CREDITRATINGID"));
					crert_CreditRatingDetailInfo.setRatingCode(transRS.getString("RATINGCODE"));
					crert_CreditRatingDetailInfo.setClientID(transRS.getLong("CLIENTID"));
					crert_CreditRatingDetailInfo.setClientCode(transRS.getString("CLIENTCODE"));
					crert_CreditRatingDetailInfo.setClientName(transRS.getString("CLIENTNAME"));
					crert_CreditRatingDetailInfo.setRatingType(transRS.getLong("RATINGTYPE"));
					crert_CreditRatingDetailInfo.setRatingProjectID(transRS.getLong("RATINGPROJECTID"));
					crert_CreditRatingDetailInfo.setRatingProjectName(transRS.getString("RATINGPROJECTNAME"));
					crert_CreditRatingDetailInfo.setStateDate(transRS.getTimestamp("STATEDATE"));
					crert_CreditRatingDetailInfo.setEndDate(transRS.getTimestamp("ENDDATE"));
					crert_CreditRatingDetailInfo.setRatingNumeric(transRS.getDouble("RATINGNUMERIC"));
					crert_CreditRatingDetailInfo.setRatingResult(transRS.getString("RATINGRESULT"));
					crert_CreditRatingDetailInfo.setReMark(transRS.getString("REMARK"));
					crert_CreditRatingDetailInfo.setRatingDate(transRS.getTimestamp("RATINGDATE"));
					crert_CreditRatingDetailInfo.setOfficeID(transRS.getLong("OFFICEID"));
					crert_CreditRatingDetailInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
					crert_CreditRatingDetailInfo.setInputUserID(transRS.getLong("INPUTUSERID"));
					crert_CreditRatingDetailInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
					crert_CreditRatingDetailInfo.setState(transRS.getLong("STATE"));
				}
				
			}catch(Exception e2){
				
				throw new IException("Gen_E001",e2.getMessage());
				
			}finally{
				this.finalizeDAO();
			}
		}
		return crert_CreditRatingDetailInfo;
	}
    //更新信用评级重估的状态和生效日期（审批通过才生成生效日期）
    public long updateStatus(long status,long ID)throws Exception
    {
    	int result = 0;
    	try{
    		
    		initDAO();
    		if(CreRtConstant.CreRtStatus.APPROVALED==status)
    		{
    			this.prepareStatement("UPDATE CRERT_CREDITRATINGREVALUED SET STATE = ?,REVALUEDDATE=sysdate WHERE ID = ?");
    		}
    		else
    		{
    			this.prepareStatement("UPDATE CRERT_CREDITRATINGREVALUED SET STATE = ?,REVALUEDDATE=null WHERE ID = ?");
    		}
    		this.transPS.setLong(1,status);
    		this.transPS.setLong(2,ID);
    		result = this.executeUpdate();
    		
    	}catch(Exception e){
    		throw new IException("Gen_E001",e);
    	}finally{
    		this.finalizeDAO();
    	}
    	return (result>0?ID:-1);
    }
   
    //审批通过后更新信用评级-重估后的评级结果
    public long updateRatingResult(long resultID,String ratingRsult)throws Exception
    {
    	int result = 0;
    	try{
    		
    		initDAO();
    		this.prepareStatement("UPDATE CRERT_CREDITRATINGDETAIL SET RATINGRESULT = ? WHERE ID = ?");
    		this.transPS.setString(1,ratingRsult);
    		this.transPS.setLong(2,resultID);
    		result = this.executeUpdate();
    		
    	}catch(Exception e){
    		throw new IException("Gen_E001",e);
    	}finally{
    		this.finalizeDAO();
    	}
    	return (result>0?resultID:-1);
    }
    //取消重估审批时进行校验
    public boolean checkCancelApproval(long creditRatingValuedID)throws Exception
    {
    	boolean flag = false;
    	StringBuffer sbSQL = new StringBuffer();
    	
    	try{
    		sbSQL.append("SELECT COUNT(*) AS COUNTS FROM CRERT_CREDITRATINGREVALUED CRERT1,");
    		sbSQL.append("(SELECT CRERT2.CREDITRATINGID AS CREDITRATINGID,CRERT2.REVALUEDDATE AS REVALUEDDATE FROM CRERT_CREDITRATINGREVALUED CRERT2 WHERE CRERT2.ID = ?)CRERT3 ");
    		sbSQL.append(" WHERE CRERT1.CREDITRATINGID = CRERT3.CREDITRATINGID AND CRERT1.REVALUEDDATE > CRERT3.REVALUEDDATE AND CRERT1.STATE !="+CreRtConstant.CreRtStatus.DELETE);
    		initDAO();
    		this.prepareStatement(sbSQL.toString());
    		this.transPS.setLong(1,creditRatingValuedID);
    		this.executeQuery();
    		while(this.transRS != null && this.transRS.next())
    		{
    			if(this.transRS.getLong("COUNTS") > 0)
    			{
    				flag = true;
    			}
    		}
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		throw new IException("Gen_E001",e);
    		
    	}finally{
    		
    		this.finalizeDAO();
   
    	}
    	return flag;
    }
    //校验保存重估信息所对应的评级信息是否是否有其他重估操作正在进行中
    public boolean checkSave(long creditRatingID)throws Exception
    {
    	boolean flag = false;
    	try{
    		initDAO();
    		this.prepareStatement("SELECT COUNT(*) AS COUNTS FROM CRERT_CREDITRATINGREVALUED C2 WHERE C2.CREDITRATINGID = ? AND  C2.STATE IN("+CreRtConstant.CreRtStatus.SAVE+","+CreRtConstant.CreRtStatus.APPROVALING+")");
    		this.transPS.setLong(1,creditRatingID);
    		this.executeQuery();
    		while(this.transRS != null && this.transRS.next())
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
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
