/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-14
 */
package com.iss.itreasury.treasuryplan.etl.transform.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Database;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_TPTemplateDAO extends TreasuryPlanDAO {
	public Trea_TPTemplateDAO(Connection tpConn){
		super("Trea_TPTemplate",tpConn);
	}
	
	public Trea_TPTemplateDAO(){
		super("Trea_TPTemplate");
	}	
	
	public Collection findTPTemplateByOfficeIDAndCurrencyID(long officeID,long currencyID) throws Exception{
		String strSQL = "select * from "+strTableName+" where officeid = "+officeID+" and currencyid= "+currencyID+" and statusid=1 order by lineno";
		Collection c;
		try {
			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(TPTemplateInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return c;
	}

	
	/**
	 * 从ResultSet中获取查询结果　
	 * @param className 需要查找的数据库表对应的Data Entity的类名
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 * ID
OFFICEID
CURRENCYID
LINENO
LINENAME
LINELEVEL
PARENTLINEID
ISLEAF
AUTHORIZEDDEPARTMENTID
AUTHORIZEDUSERID
MAINTENANCEFLAG
STATUSID
INPUTUSERID
INPUTDATE
UPDATEUSERID
UPDATEDATE
ISREADONLY

	 */	
	public Collection getDataEntitiesFromResultSet(Class dataEntityClass) throws ITreasuryDAOException{
		ArrayList res = new ArrayList();
		try{
		while(transRS.next()){
			TPTemplateInfo info = new TPTemplateInfo();
			info.setId(transRS.getLong("ID"));
			info.setOfficeID(transRS.getLong("OFFICEID"));
			info.setCurrencyID(transRS.getLong("CURRENCYID"));
			info.setLineLevel(transRS.getLong("LINELEVEL"));
			info.setParentLineID(transRS.getLong("PARENTLINEID"));
			info.setIsLeaf(transRS.getLong("ISLEAF"));
			info.setAuthorizedDepartment(transRS.getString("AUTHORIZEDDEPARTMENT"));
			info.setAuthorizedUser(transRS.getString("AUTHORIZEDUSER"));
			info.setMaintenanceFlag(transRS.getLong("MAINTENANCEFLAG"));
			info.setStatusID(transRS.getLong("STATUSID"));
			info.setUpdateUserID(transRS.getLong("INPUTUSERID"));			
			info.setUpdateUserID(transRS.getLong("UPDATEUSERID"));
			info.setIsReadOnly(transRS.getLong("ISREADONLY"));
			info.setLineNo(transRS.getString("LINENO"));
			info.setLineName(transRS.getString("LINENAME"));
			info.setInputDate(transRS.getTimestamp("INPUTDATE"));
			info.setUpdateDate(transRS.getTimestamp("UPDATEDATE"));
			info.setIsNeedSum(transRS.getLong("IsNeedSum"));
			res.add(info);
		}
		}catch(SQLException e){
			throw new ITreasuryDAOException("",e);
		}
		return res;
	}
	
	public Collection getTemplatesDependingOtherLine(long officeID,long currencyID)throws Exception{
		
		String strSQL = "select * from " + strTableName + " where id in (3660) and " +
				"officeid = " + officeID + " and currencyid = "+ currencyID
				+" order by id";
		Collection c;
		try {
			prepareStatement(strSQL);
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
	
//	public void contructLineNoByLineLevel() throws Exception{
//		this.initDAO();
//		
//		String strSQL = "select * from "+ strTableName + " where linelevel >=2 order by id";
//		this.prepareStatement(strSQL);
//		this.executeQuery();
//		Collection c = this.getDataEntitiesFromResultSet(null);
//		long initLevel = 2;
//		long maxLevel = 5;
//		long unitLineLevel = 1;
//	
//		getLineNo(c, initLevel, unitLineLevel);
//
//		this.finalizeDAO();
//	}
//
//	/**
//	 * @param c
//	 * @param initLevel
//	 * @param unitLineLevel
//	 */
//	private void getLineNo(Collection c, long initLevel, long unitLineLevel) {
//		String lineNo = "";
//		if(initLevel == 2)
//			lineNo = "00"+initLevel;
//		else if(initLevel <= 5)
//			lineNo = "_00"+initLevel;
//
//			
//		
//		Iterator it = c.iterator();
//		while(it.hasNext()){
//			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
//			if(templateInfo.getLineLevel() > initLevel){
//				lineNo = getUnitLineNo(unitLineLevel, lineNo);		
//					unitLineLevel++;
//
//			}else{
//				if(initLevel != 2)//针对第二级以下处理，例如 002_001
//					initLevel+=1;
//				else{//针对第二级处理，例如 002
//					uppdateTempalte(templateInfo, lineNo);					
//					continue;
//				}
//				if(initLevel >= 1 && initLevel <=9){
//					lineNo = lineNo.substring(0,lineNo.length()-1);
//					lineNo += initLevel; 
//				}else if(initLevel >= 10 && initLevel <= 99){
//					lineNo = lineNo.substring(0,lineNo.length()-2);
//					lineNo += initLevel; 
//				}else if(initLevel >= 100 && initLevel <= 999){
//					lineNo = lineNo.substring(0,lineNo.length()-3);
//					lineNo += initLevel; 
//				}
//			}
//			
//		}
//
//	}
	
	private void uppdateTempalte(TPTemplateInfo templateInfo, String lineNo)throws Exception{
		System.out.println("========Line lineNo:"+lineNo);
		System.out.println("========ParentLineID:"+templateInfo.getParentLineID());
		TPTemplateInfo updatedTemplateInfo = new TPTemplateInfo();
		updatedTemplateInfo.setId(templateInfo.getId());
		//updatedTemplateInfo.setParentLineID(templateInfo.getParentLineID());
		//updatedTemplateInfo.setLineNo(lineNo);
		updatedTemplateInfo.setIsLeaf(templateInfo.getIsLeaf());
		update(updatedTemplateInfo);
		finalizeDAO();
	}

	/**
	 * @param unitLineLevel
	 * @param lineNo
	 * @return
	 */
	private String getUnitLineNo(long subLineNum) {
		String res = "";
		if(subLineNum >= 1 && subLineNum <=9){
			res += "_00"+subLineNum;
		}else if(subLineNum >= 10 && subLineNum <= 99){
			res += "_0"+subLineNum;
		}else if(subLineNum >= 100 && subLineNum <= 999){
			res += "_"+subLineNum;
		}
		return res;
	}
	
	
	static public void main(String[] args){
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO();
		try {
			templateDAO.getLineNo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getLineNo() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);
		String strSQL = "select * from "+ strTableName + " order by id";
		this.prepareStatement(strSQL);
		this.executeQuery();
		String currentLevel1LineNo = "";
		String currentLevel2LineNo = "";
		String currentLevel3LineNo = "";
		String currentLevel4LineNo = "";		
		String currentLevel5LineNo = "";
		
		
		long currentLevel1LineID = 0;
		long currentLevel2LineID = 0;
		long currentLevel3LineID = 0;
		long currentLevel4LineID = 0;
		long currentLevel5LineID = 0;		
		
		long currentLevel1LineCount = 1;
		long currentLevel2LineCount = 1;
		long currentLevel3LineCount = 1;
		long currentLevel4LineCount = 1;
		long currentLevel5LineCount = 1;
		ArrayList list = (ArrayList) this.getDataEntitiesFromResultSet(null);
		
		for(int i=0;i<list.size();i++){
			TPTemplateInfo templateInfo = (TPTemplateInfo) list.get(i);
			TPTemplateInfo nextTemplateInfo = null;
			checkIsLeaf(list, i, templateInfo);
			
			//System.out.println("---------templateInfo:"+templateInfo);
			System.out.println("---------LineName:"+templateInfo.getLineName());
			//System.out.println("---------LineLevel:"+templateInfo.getLineLevel());
			if(templateInfo.getLineLevel() == 1){
				currentLevel1LineNo = templateInfo.getLineNo();
				currentLevel1LineID = templateInfo.getId();
				currentLevel2LineNo = "";
				currentLevel3LineNo = "";
				currentLevel4LineNo = "";		
				currentLevel5LineNo = "";				
				currentLevel2LineCount = 1;
				currentLevel3LineCount = 1;
				currentLevel4LineCount = 1;
				currentLevel5LineCount = 1;		
			}
			else if(templateInfo.getLineLevel() == 2){
				String level2SubNo = getUnitLineNo(currentLevel2LineCount);
				currentLevel2LineNo = currentLevel1LineNo+level2SubNo;
				currentLevel2LineID = templateInfo.getId();				
				templateInfo.setParentLineID(currentLevel1LineID);
				uppdateTempalte(templateInfo, currentLevel2LineNo);
				currentLevel2LineCount++;
				currentLevel3LineNo = "";
				currentLevel4LineNo = "";		
				currentLevel5LineNo = "";								
				currentLevel3LineCount = 1;
				currentLevel4LineCount = 1;
				currentLevel5LineCount = 1;		

			}else if(templateInfo.getLineLevel() == 3){
				String level3SubNo = getUnitLineNo(currentLevel3LineCount);
				currentLevel3LineNo = currentLevel2LineNo + level3SubNo;
				currentLevel3LineID = templateInfo.getId();				
				templateInfo.setParentLineID(currentLevel2LineID);				
				uppdateTempalte(templateInfo, currentLevel3LineNo);
				currentLevel4LineNo = "";		
				currentLevel5LineNo = "";								
				currentLevel4LineCount = 1;
				currentLevel5LineCount = 1;										
				currentLevel3LineCount++;
			}else if(templateInfo.getLineLevel() == 4){	
				currentLevel5LineNo = "";								
				String level4SubNo = getUnitLineNo(currentLevel4LineCount);
				currentLevel4LineNo = currentLevel3LineNo + level4SubNo; 
				currentLevel4LineID = templateInfo.getId();				
				templateInfo.setParentLineID(currentLevel3LineID);								
				uppdateTempalte(templateInfo, currentLevel4LineNo);
				currentLevel4LineCount++;
				currentLevel5LineCount = 1;				
			}else if(templateInfo.getLineLevel() == 5){
				String level5SubNo = getUnitLineNo(currentLevel5LineCount);
				currentLevel5LineNo = currentLevel4LineNo + level5SubNo; 
				currentLevel5LineID = templateInfo.getId();				
				templateInfo.setParentLineID(currentLevel4LineID);												
				uppdateTempalte(templateInfo, currentLevel5LineNo);
				currentLevel5LineCount++;				
			}
		}
		if(transConn != null)
			transConn.close();
		return null;
	}

	/**
	 * @param list
	 * @param i
	 * @param templateInfo
	 */
	private void checkIsLeaf(ArrayList list, int i, TPTemplateInfo templateInfo) {
		TPTemplateInfo nextTemplateInfo;
		if(i+1<list.size()){
			nextTemplateInfo = (TPTemplateInfo) list.get(i+1);

			System.out.println("---------LineLevel:"+templateInfo.getLineLevel());				
			System.out.println("---------NextLineLevel:"+nextTemplateInfo.getLineLevel());								

			if(templateInfo.getLineLevel() == 5){
				templateInfo.setIsLeaf(1);
			}else if(templateInfo.getLineLevel() >= nextTemplateInfo.getLineLevel()){
				templateInfo.setIsLeaf(1);
			}else{
				templateInfo.setIsLeaf(0);				
			}				
		}else{
			templateInfo.setIsLeaf(1);	
		}
	}
	
	
	public Collection getLevelNTemplateLines(long lineLevel) throws Exception{
		String strSQL = "select * from Trea_TPTemplate where linelevel = "+lineLevel+" and statusid > 0 and isleaf = 1 and officeid =  1 and currencyid = 1 order by id";
		this.prepareStatement(strSQL);
		this.executeQuery();				
		Collection c = getDataEntitiesFromResultSet(TPTemplateInfo.class);
		finalizeDAO();
		return c;
	}
	
	/**
	 * 
	 * @param lineNoCondition
	 * @return
	 * @throws Exception
	 */
	public String getMaxLineNo(String lineNoCondition) throws Exception{
		String strSQL = "select max(substr(lineno,length('"+lineNoCondition+"')+2,3))+1 maxlinecode from TREA_TPTEMPLATE where lineno like '"+lineNoCondition+"____'" ;

		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		String maxLineNo = "";
		if(localRS.next())
			maxLineNo = localRS.getString("maxlinecode");
		finalizeDAO();
		return maxLineNo;				
	}
	
	public void updateStatusIDByAccountType(String clientCode,long accountTypeID) throws Exception{
		String accTypeName = ""; 
		if(accountTypeID == SETTConstant.AccountGroupType.CURRENT)
			accTypeName = "活期";
		else if(accountTypeID == SETTConstant.AccountGroupType.FIXED)
			accTypeName = "定期";
		else if(accountTypeID == SETTConstant.AccountGroupType.NOTIFY)
			accTypeName = "通知";
		else if(accountTypeID == SETTConstant.AccountGroupType.TRUST)
			accTypeName = "自营";
		else if(accountTypeID == SETTConstant.AccountGroupType.CONSIGN)
			accTypeName = "委贷";
		else if(accountTypeID == SETTConstant.AccountGroupType.DISCOUNT)
			accTypeName = "贴现";			
		else if(accountTypeID == SETTConstant.AccountGroupType.CURRENT)
			accTypeName = "委存";
		
		String strSQL = "select a.id from trea_tptemplate a ,trea_tptemplateitem b where a.linename='"+accTypeName+"' and b.lineid=a.id and b.clientcode='"+clientCode+"'";
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		if(localRS.next()){
			finalizeDAO();		
			strSQL = "update trea_tptemplate set statusid=1 where id in (select a.id from trea_tptemplate a ,trea_tptemplateitem b where a.linename='"+accTypeName+"' and b.lineid=a.id and b.clientcode='"+clientCode+"')";
			prepareStatement(strSQL);
			executeUpdate();
			finalizeDAO();
			//上级行项目
			strSQL = "update trea_tptemplate set statusid=1 where id in ( select parentlineid from trea_tptemplate where id in (select a.id from trea_tptemplate a ,trea_tptemplateitem b where a.linename= '"+accTypeName+"' and b.lineid=a.id and b.clientcode='"+clientCode+"'))";
			prepareStatement(strSQL);
			executeUpdate();
			finalizeDAO();						
		}else{
		}

	}
	
	/**
	 * 
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public String getLineNoByPrefix(String prefix) throws Exception{
		String strSQL = "select max(substr(lineno,9,3))+1 maxlinecode from TREA_TPTEMPLATE where lineno like '"+prefix+ "%'";

		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		String maxLineNo = "";
		if(localRS.next())
			maxLineNo = localRS.getString("maxlinecode");
		finalizeDAO();
		return maxLineNo;				
	}
	
	public long getIDByLineNo(String lineNo) throws Exception{
		String strSQL = "select id from " +strTableName+ " where lineno = '"+ lineNo+"'";

		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		long id = -1;
		if(localRS.next())
			id = localRS.getLong("id");
		finalizeDAO();
		return id;						
	}
	
	/**
	 * 获取下一个行号的后缀
	 * */
	public String getNextAccountTypeLineNoSuffix(String lineNo) throws Exception{
		String strSQL = "select count(*)+1 suffix from trea_tptemplate where parentlineid = (select id from trea_tptemplate where lineno = '"+lineNo+"')";

		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		String suffix = "";
		if(localRS.next())
			suffix = localRS.getString("suffix");
		finalizeDAO();
		return suffix;				
	}
}



	

