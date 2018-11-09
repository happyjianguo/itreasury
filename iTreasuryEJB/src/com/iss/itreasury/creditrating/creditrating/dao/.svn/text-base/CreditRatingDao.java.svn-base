package com.iss.itreasury.creditrating.creditrating.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.creditrating.creditrating.dataentity.CreditRatingInfo;
import com.iss.itreasury.creditrating.creditrating.dataentity.SubCreditRatingInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class CreditRatingDao extends ITreasuryDAO {
	public CreditRatingDao()
	{
	
	}
	public CreditRatingDao(String tableName)
	{
		super(tableName);
	}
	/**
	 * 第一个下一步得到编号
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public String nextOneStep(CreditRatingInfo info) throws Exception 
	{
		String strReturn ="";
		try {		
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(info.getOfficeID()));
				map.put("currencyID",String.valueOf(info.getCurrencyID()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.CREDITRATING));
				map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRERT_CREDITRATING));
			    map.put("clientID",String.valueOf(info.getClientID()));
			    strReturn=CreateCodeManager.createCode(map);	
				
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return strReturn;
	}
	/**
	 * 通过ID,返回信用评级信息（主）
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public ITreasuryBaseDataEntity getCreditRatingByCondition(long ID,Class className) throws Exception 
	{
		ITreasuryBaseDataEntity resultInfo = null;
		try {		
				
			resultInfo = findByID(ID,className);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return resultInfo;
	}
	/**
	 * 第二个下一步保存
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public long nextTwoStepSave(CreditRatingInfo info) throws Exception 
	{
		long lReturn = -1;
		try {	
			 lReturn = add(info);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return lReturn;
	}
	/**
	 * 第二个下一步保存(子表)
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public long nextTwoStepSave(SubCreditRatingInfo info) throws Exception 
	{
		long lReturn = -1;
		try {	
			 lReturn = add(info);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return lReturn;
	}
	/**
	 * 第二个保存
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public long save(CreditRatingInfo info) throws Exception 
	{
		long lReturn = -1;
		try {	
			   update(info);
			   lReturn = info.getID();	
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return lReturn;
	}
	/**
	 * 第二个保存(子表)
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public long save(SubCreditRatingInfo info) throws Exception 
	{
		long lReturn = -1;
		try {	
			   update(info);
			   lReturn = info.getId();	
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		return lReturn;
	}
	/**
	 * 更新评级子表状态
	 * @param info
	 * @return
	 * @throws Exception 
	 * @throws IException 
	 */
	public void cancelFinalRatingInfo(long ratingID,long status) throws Exception 
	{
		long lReturn = -1;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update crert_creditratingdetail t set t.STATE=? where t.CREDITRATINGID=?");
			prepareStatement( buffer.toString());	
			transPS.setLong(1, status);
			transPS.setLong(2, ratingID);
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 校验是否存在重复的编号
	 * @param info
	 * @return
	 * @throws  
	 */
	public boolean validateRatingCode(CreditRatingInfo info)throws ITreasuryDAOException 
	{
		boolean isTrue = false;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_creditrating t where t.state!= ? and t.OFFICEID=? and t.CURRENCYID=? and t.RATINGCODE=? ");
			if(info.getID()>0)
			{
				buffer.append("and t.id!=?");
			}
			prepareStatement( buffer.toString());	
			transPS.setLong(1,CreRtConstant.CreRtStatus.DELETE);
			transPS.setLong(2, info.getOfficeID());
			transPS.setLong(3, info.getCurrencyID());
			transPS.setString(4, info.getRatingCode());
			if(info.getID()>0)
			{
				transPS.setLong(5,info.getID());
			}
			executeQuery();
			if(transRS.next())
			{
				isTrue =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ITreasuryDAOException("校验编号SQL出错",e);
			
		}finally{
			finalizeDAO();
		}
		
		return isTrue;
	}
	public boolean validateSameDate(CreditRatingInfo info)throws ITreasuryDAOException 
	{
		boolean isTrue = false;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_creditrating t   where least(?,t.enddate)>=greatest(?,t.statedate) and t.STATE<>? and t.OFFICEID=? and t.CURRENCYID=? and t.CLIENTID=? ");
			if(info.getRatingprojectID()>0)
			{
				buffer.append(" and t.RATINGPROJECTID=? and t.RATINGPROJECTNAME=? and t.RATINGTYPE=?");
			}
			else
			{
				buffer.append(" and t.RATINGPROJECTNAME=? and t.RATINGTYPE=?");
			}
			if(info.getID()>0)
			{
				buffer.append(" and t.id!=?");
			}
			
			int index = 1;
			prepareStatement( buffer.toString());	
			transPS.setTimestamp(index++, info.getEndDate());
			transPS.setTimestamp(index++, info.getStateDate());
			transPS.setLong(index++,CreRtConstant.CreRtStatus.DELETE);
			transPS.setLong(index++, info.getOfficeID());
			transPS.setLong(index++, info.getCurrencyID());
			transPS.setLong(index++, info.getClientID());
			
			if(info.getRatingprojectID()>0)
			{
				transPS.setLong(index++,info.getRatingprojectID());
				transPS.setString(index++, info.getRatingprojectName());
				transPS.setLong(index++, CreRtConstant.CreRtType.INCRERT);
			}
			else
			{
				transPS.setString(index++, info.getRatingprojectName());
				transPS.setLong(index++, CreRtConstant.CreRtType.OUTCRERT);
			}

			if(info.getID()>0)
			{
				transPS.setLong(index++,info.getID());
			}
			executeQuery();
			if(transRS.next())
			{
				isTrue =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ITreasuryDAOException("校验评级时间重复区间SQL出错",e);
			
		}finally{
			finalizeDAO();
		}
		
		return isTrue;
	}
	public boolean validateCancelCreditRating(long rationgID)throws ITreasuryDAOException 
	{
		boolean isTrue = false;
		try {	
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select t2.id ID \n");
			buffer.append(" from crert_creditratingdetail t1, crert_creditratingrevalued t2 \n");
			buffer.append("where t2.creditratingid = t1.id \n");
			buffer.append("and t2.state !="+CreRtConstant.CreRtStatus.DELETE+" \n");
			buffer.append("and t1.creditratingid = "+rationgID+" \n");
			buffer.append("and t1.state!="+CreRtConstant.CreRtStatus.DELETE+" \n");
			buffer.append("union all \n");
			buffer.append("select t2.id ID \n");
			buffer.append("from crert_creditratingdetail t1, crert_creditratingcancel t2 \n");
			buffer.append("where t2.RATINGPROJECTID = t1.id \n");
			buffer.append("and t2.state !="+CreRtConstant.CreRtStatus.DELETE+" \n");
			buffer.append("and t1.creditratingid = "+rationgID+" \n");
			buffer.append("and t1.state!="+CreRtConstant.CreRtStatus.DELETE+" \n");
			
			prepareStatement( buffer.toString());	
			executeQuery();
			if(transRS.next())
			{
				isTrue =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ITreasuryDAOException("校验编号SQL出错",e);
			
		}finally{
			finalizeDAO();
		}
		
		return isTrue;
	}
	/**
	 * 通过评级ID,得到评级子表信息
	 * @param ratingID
	 * @return
	 */
	public Collection findSubCreditRatingByRatingID(long ratingID)throws ITreasuryDAOException
	{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_subcreditrating t where t.state= ? and t.CREDITRATINGID=? ");
			log.print("信用评级子表明细SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			transPS.setLong(1,Constant.RecordStatus.VALID);
			transPS.setLong(2, ratingID);
			executeQuery();
			Collection coll = new ArrayList();
			while(transRS.next()){
				SubCreditRatingInfo resultInfo = new SubCreditRatingInfo();
				resultInfo.setId(transRS.getLong("ID"));
				resultInfo.setCreditratingID(transRS.getLong("CREDITRATINGID"));
				resultInfo.setRatingprojectID(transRS.getLong("RATINGPROJECTID"));
				resultInfo.setSubratingprojectID(transRS.getLong("SUBRATINGPROJECTID"));
				resultInfo.setRatingValue(transRS.getString("RATINGVALUE"));
				resultInfo.setOfficeID(transRS.getLong("OFFICEID"));
				resultInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
				resultInfo.setInputuserID(transRS.getLong("INPUTUSERID"));
				resultInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
				resultInfo.setState(transRS.getLong("STATE"));
				coll.add(resultInfo);
			}
				return coll;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询评级方案子表明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
	/**
	 * 通过评级ID,得到评级子表信息（Map）
	 * @param ratingID
	 * @return
	 */
	public Map findSubCreditRatingValueByRatingID(long ratingID)throws ITreasuryDAOException
	{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from crert_subcreditrating t where t.state= ? and t.CREDITRATINGID=? ");
			log.print("信用评级方案子表明细SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			transPS.setLong(1,Constant.RecordStatus.VALID);
			transPS.setLong(2, ratingID);
			executeQuery();
			Map map = new HashMap();
				while(transRS.next()){
					map.put(String.valueOf(transRS.getLong("SUBRATINGPROJECTID")), transRS.getLong("ID")+"::"+transRS.getString("RATINGVALUE"));
				}
			return map;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询评级方案子表明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
	/**
	 * 通过评级方案ID，评级分数的到评级结果（例：AAA）
	 * @param ratingProjectID
	 * @param ratingnumeric
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public String findSubtandardratingNameByRatingID(long ratingProjectID,double ratingnumeric)throws ITreasuryDAOException
	{
		String strReturn = "";
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from (select a.name standardName, \n");
			buffer.append(" decode(a.mixvalue, -99999, -999999999,a.mixvalue) minvalue, \n");
			buffer.append("decode(a.maxvalue, -99999, 999999999999,a.maxvalue) maxvalue \n");
			buffer.append("from crert_substandardrating a, \n");
			buffer.append("(select distinct t1.id \n");
			buffer.append(" from crert_standardrating t1, \n");
			buffer.append("crert_ratingproject  t2, \n");
			buffer.append("crert_creditrating   t3 \n");
			buffer.append("where t1.id = t2.standardratingid \n");
			buffer.append(" and t2.id = t3.ratingprojectid(+) \n");
			buffer.append("and t2.id ="+ratingProjectID+" ) b \n");
			buffer.append("where a.standardratingid = b.id \n");
			buffer.append("and a.state = "+Constant.RecordStatus.VALID+" ) \n");
			buffer.append("where "+ratingnumeric+">=minvalue and "+ratingnumeric+"<maxvalue \n");
			log.print("查询评级结果SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			executeQuery();
			
			while(transRS.next()){
				strReturn = transRS.getString("standardName");
			}
				return strReturn;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询评级方案子表明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
	
	/**
	 * 通过评级方案ID,得到评级标准名称（例：AAA;;AA;;A）
	 * @param ratingProjectID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findSubtandardratingNamesByProjectID(long ratingProjectID)throws ITreasuryDAOException
	{
		Collection coll = null;
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from (select a.name standardName, \n");
			buffer.append(" decode(a.mixvalue, -99999, -999999999,a.mixvalue) minvalue, \n");
			buffer.append("decode(a.maxvalue, -99999, 999999999999,a.maxvalue) maxvalue \n");
			buffer.append("from crert_substandardrating a, \n");
			buffer.append("(select distinct t1.id \n");
			buffer.append(" from crert_standardrating t1, \n");
			buffer.append("crert_ratingproject  t2, \n");
			buffer.append("crert_creditrating   t3 \n");
			buffer.append("where t1.id = t2.standardratingid \n");
			buffer.append(" and t2.id = t3.ratingprojectid \n");
			buffer.append("and t2.id ="+ratingProjectID+" ) b \n");
			buffer.append("where a.standardratingid = b.id \n");
			buffer.append("and a.state = "+Constant.RecordStatus.VALID+" ) \n");
			buffer.append("order by minvalue desc \n");
			log.print("查询评级结果SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			executeQuery();
			coll = new ArrayList();
			
			while(transRS.next()){
				coll.add( transRS.getString("standardName"));
			}
			return coll;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询评级方案子表明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
	
}
