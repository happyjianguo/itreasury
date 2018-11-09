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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_TPTemplateItemDAO extends TreasuryPlanDAO {
	public Trea_TPTemplateItemDAO(Connection tpConn){
		super("Trea_TPTemplateItem",tpConn);
	}
	
	public Trea_TPTemplateItemDAO(){
		super("Trea_TPTemplateItem");
	}	
	
	public Collection findAllByLineID(long lineID) throws Exception
    {
		PreparedStatement ps = null;
        ResultSet         rs = null;
        
		String strSQL = "select * from " + strTableName + " where lineid = " + lineID;
         System.out.println("strSQL     \n "+strSQL);
		Collection c = null;
		try 
        {
			//Init
            
            initDAO();
            
            if( transConn != null && transConn.isClosed() )
            {
                transConn = Database.getConnection();
            }
            
            ps = transConn.prepareStatement(strSQL);
           
            
            if( ps != null )
            {
                rs = ps.executeQuery();
               
            }
            
            if( rs != null )
            {
                 transRS = rs;
                 c = getDataEntitiesFromResultSet(null);
            }
            
		} 
        catch (ITreasuryDAOException e)
        {
			e.printStackTrace();
			throw e;
		} 
        finally 
        {
			try 
            {
                
                if (rs != null) 
                {
                    rs.close();
                    rs = null;
                }
        
                if (ps != null) 
                {
                    ps.close();
                    ps = null;
                }
        
                if (transConn != null) 
                {
                    transConn.close();
                    transConn = null;
                }
            } 
            catch (SQLException e)
            {
                throw new ITreasuryDAOException("数据库关闭异常发生",e);         
            }
            
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
	 */	
	public Collection getDataEntitiesFromResultSet(Class dataEntityClass) throws ITreasuryDAOException{
		ArrayList list = new ArrayList();		
		try
        {
    		System.out.println(" getDataEntitiesFromResultSet      transRS =  "+transRS);
            while(transRS.next())
            {
    			TPTemplateItemInfo info = new TPTemplateItemInfo();
    			info.setId(transRS.getLong("ID"));
    			info.setLineID(transRS.getLong("LINEID"));
    			info.setModuleID(transRS.getLong("MODULEID"));
    			info.setAccountTypeId(transRS.getLong("ACCOUNTTYPEID"));
    			info.setAmountDirection(transRS.getLong("AMOUNTDIRECTION"));
    			info.setAmountFlag(transRS.getLong("AMOUNTFLAG"));
    			info.setCalculateFlag(transRS.getString("CALCULATEFLAG"));
    			info.setClientCode(transRS.getString("CLIENTCODE"));
    			info.setAccountCode(transRS.getString("ACCOUNTCODE"));
    			info.setContractCode(transRS.getString("CONTRACTCODE"));
    			info.setPayFormCode(transRS.getString("PAYFORMCODE"));
    			info.setCounterpartName(transRS.getString("COUNTERPARTNAME"));
    			info.setSecuritiesName(transRS.getString("SECURITIESNAME"));
    			info.setGlSubjectCode(transRS.getString("GLSUBJECTCODE"));
    			info.setParameter(transRS.getString("PARAMETER"));
    			list.add(info);
    		}
		}
        catch(SQLException e)
        {
			throw new ITreasuryDAOException("",e);
		}
		return list;
	}
	
	private String getSubjectCodeByClientName(long accountTpeID,String clientName){
		return "";
	}
	
	
	private ArrayList getAllBranch() throws Exception{
		ArrayList list = new ArrayList();
		String strSQL = "select SSUBJECTCODE from sett_branch where NSTATUSID > 0 and NOFFICEID = 1 and  NCURRENCYID = 1";
		this.prepareStatement(strSQL);
		ResultSet localRS = this.executeQuery();
		while(localRS.next()){
			String subjectCode = localRS.getString("SSUBJECTCODE");
			list.add(subjectCode);
		}
		finalizeDAO();
		return list;
		
	}
	
	public void consturctLevel1TemplateItem() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);		
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(transConn);		
		Collection c = templateDAO.getLevelNTemplateLines(1);
		Iterator it = c.iterator();		
		while(it.hasNext()){
			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
			//TPTemplateItemInfo updatedTemplateItemInfo = new TPTemplateItemInfo();
			String lineLevel = templateInfo.getLineNo();
			String lineName = templateInfo.getLineName();
			long amountDirection = -1;			
			ArrayList list = new ArrayList();
			String subjectCode = "";
			if(lineLevel.indexOf("001") >=0){//L1期初余额
				ArrayList subjectCodeOfBranchs = this.getAllBranch();
				for(int i=0;i<subjectCodeOfBranchs.size();i++){
					TPTemplateItemInfo intTemplateItemInfo = new TPTemplateItemInfo();
					String branchSubjectCode = (String)subjectCodeOfBranchs.get(i);
					intTemplateItemInfo.setGlSubjectCode(branchSubjectCode);
					intTemplateItemInfo.setModuleID(Constant.ModuleType.GENERALLEDGER);
					intTemplateItemInfo.setLineID(templateInfo.getId());
					intTemplateItemInfo.setAmountFlag(TPConstant.AmountFlag.Balance);
					list.add(intTemplateItemInfo);
				}
			}else if(lineLevel.indexOf("002") >=0){//L1存款准备金
				//工行翠微路科目用户查询的日期范围内每日的贷方发生额
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.GENERALLEDGER);
				// 读取财务系统银行存款--存放中央银行款项下的存款准备金科目借方余额 
				updatedTemplateItemInfo1.setGlSubjectCode("存放中央银行款项下的存款准备金科目借方余额");
				list.add(updatedTemplateItemInfo1);
				
			}else if(lineLevel.indexOf("003") >=0){//L1保证金
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.GENERALLEDGER);				
				// 读取此日财务系统中银行存款下活期存款下的保证金科目借方余额和通知存款下保证金科目借方余额
				updatedTemplateItemInfo1.setGlSubjectCode("银行存款下活期存款下的保证金科目借方余额");
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.GENERALLEDGER);				
				// 读取此日财务系统中银行存款下活期存款下的保证金科目借方余额和通知存款下保证金科目借方余额
				updatedTemplateItemInfo2.setGlSubjectCode("通知存款下保证金科目借方余额");
				list.add(updatedTemplateItemInfo1);				
			}else if(lineLevel.indexOf("004") >=0){//L1期初可用余额=期初余额-存款准备金-保证金
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#2");
				updatedTemplateItemInfo2.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo3.setParameter("#3");
				updatedTemplateItemInfo3.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo3);
			}else if(lineLevel.indexOf("007") >=0){//L1必要备付金=企业存款*必要备付金率
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(1);
				updatedTemplateItemInfo1.setAmountFlag(2);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setAccountTypeId(2);
				updatedTemplateItemInfo2.setAmountFlag(2);
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo3.setAccountTypeId(3);
				updatedTemplateItemInfo3.setAmountFlag(2);
				list.add(updatedTemplateItemInfo3);
				
				TPTemplateItemInfo updatedTemplateItemInfo4 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo4.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo4.setParameter("@paymentrate");
				updatedTemplateItemInfo4.setCalculateFlag("*");
				list.add(updatedTemplateItemInfo4);				
			}else if(lineLevel.indexOf("008") >=0){//L1资金头寸=期末可用余额-必要备付金
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1288");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#1283");
				updatedTemplateItemInfo2.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo2);
			}else if(lineLevel.indexOf("010") >=0){//L1调节后资金头寸
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1284");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#1285");
				updatedTemplateItemInfo2.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo2);
			}else if(lineLevel.indexOf("011") >=0){//L1期末余额
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#5");
				updatedTemplateItemInfo2.setCalculateFlag("+");
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo3.setParameter("#678");
				updatedTemplateItemInfo3.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo3);				
			}else if(lineLevel.indexOf("012") >=0){//L1期末可用余额=期初可用余额+资金来源-资金运用
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#4");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#5");
				updatedTemplateItemInfo2.setCalculateFlag("+");
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo3.setParameter("#678");
				updatedTemplateItemInfo3.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo3);				
			}
			
			for(int j=0;j<list.size();j++){

				TPTemplateItemInfo tmpTemplateItemInfo = (TPTemplateItemInfo) list.get(j);
				this.setUseMaxID();
				this.add(tmpTemplateItemInfo);								
			}
		}
		transConn.close();

	}	
	
	
	public void consturctLevel2TemplateItem() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);		
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(transConn);		
		Collection c = templateDAO.getLevelNTemplateLines(2);
		ArrayList list = new ArrayList();
		Iterator it = c.iterator();		
		while(it.hasNext()){
			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
			//TPTemplateItemInfo updatedTemplateItemInfo = new TPTemplateItemInfo();
			String lineLevel = templateInfo.getLineNo();
			String lineName = templateInfo.getLineName();
			long amountDirection = -1;			
			
			String subjectCode = "";
			if(lineLevel.indexOf("005_009") >=0){//L2资产回购进款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGJK);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo1.setModuleID(1);
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
			}else if(lineLevel.indexOf("006_008") >=0){//L2资产回购出款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGCK);
				updatedTemplateItemInfo1.setModuleID(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
			}else if(lineLevel.indexOf("006_012") >=0){//L2其它出款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setModuleID(9);
				//工行翠微路科目用户查询的日期范围内每日的贷方发生额
				updatedTemplateItemInfo1.setGlSubjectCode("1020010103");
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setModuleID(9);
				//及应缴税金科目贷方发生额
				updatedTemplateItemInfo2.setGlSubjectCode("应缴税金科目贷方发生额");
				System.out.println("++++++++++"+updatedTemplateItemInfo2);
				list.add(updatedTemplateItemInfo2);
			
			}
		}
		for(int j=0;j<list.size();j++){

			TPTemplateItemInfo tmpTemplateItemInfo = (TPTemplateItemInfo) list.get(j);
			this.setUseMaxID();
			System.out.println("XXXXXXXXXXXXXX"+tmpTemplateItemInfo);
			this.add(tmpTemplateItemInfo);								
		}		
		transConn.close();
	}
	
	
	
	public void consturctLevel3TemplateItem() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);		
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(transConn);		
		Collection c = templateDAO.getLevelNTemplateLines(3);
		Iterator it = c.iterator();		

		while(it.hasNext()){
			ArrayList list = new ArrayList();			
			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
//			TPTemplateInfo parentTemplateInfo = (TPTemplateInfo) templateDAO.findByID(templateInfo.getParentLineID(),TPTemplateInfo.class);
			TPTemplateItemInfo updatedTemplateItemInfo = new TPTemplateItemInfo();
			String lineLevel = templateInfo.getLineNo();
			String lineName = templateInfo.getLineName();
			long amountDirection = -1;			
			String subjectCode = "";
			if(lineLevel.indexOf("005_006_001") >=0){//L3同业拆入
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.TYCR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_006_002") >=0){//L3拆出还款
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.CCHK);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_001") >=0){//L3股票
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.GPMC);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_007_002") >=0){//L3交易所国债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JYSGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_003") >=0){//L3可转债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KZZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_004") >=0){//L3企业债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.QYZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_005") >=0){//L3封闭式基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.FBSJJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_006") >=0){//L3开放式基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KFSJJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_007") >=0){//L3委托理财
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				updatedTemplateItemInfo.setGlSubjectCode("委托理财收方科目该日的发生额");
				amountDirection = TPConstant.AmountDirection.PLUS;			
				updatedTemplateItemInfo.setModuleID(9);				
			}else if(lineLevel.indexOf("005_008_001") >=0){//L3央行票据
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHPJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_002") >=0){//L3银行间国债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_002") >=0){//L3银行间国债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_003") >=0){//L3金融债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JRZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_004") >=0){//L3政策性金融债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZCXJRZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;
			}else if(lineLevel.indexOf("005_008_005") >=0){//L3货币市场基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HBSCZJMC);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_010_001") >=0){//L3融入资金进款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMCMD);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMCHG);
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo3.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMCDQSH);
				updatedTemplateItemInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo3);								
			}else if(lineLevel.indexOf("005_010_002") >=0){//L3买入票据回款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMRDQSHMD);
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMRGHHG);
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo2);
				
			}else if(lineLevel.indexOf("005_011_001") >=0){//L3股权投资
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// 取在用户查询的范围内每日的财务长期投资—债券投资科目：“负”发生额，即贷方发生额。（参与公式计算）
//				subjectCode = ?
				updatedTemplateItemInfo.setGlSubjectCode("财务长期投资—股权投资科目：贷方发生额");
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);
				
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3债券承销
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// @TBD  取在用户查询的范围内每日的财务长期投资—债券投资科目：“负”发生额，即贷方发生额。（参与公式计算）
//				subjectCode = ?		
				updatedTemplateItemInfo.setGlSubjectCode("财务长期投资—债券投资科目：贷方发生额");
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);											
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3贷款利息收入（税后）
				//updatedTemplateItemInfo.setLineID(templateInfo.getId());				
/**
 * ?	在结算模块，读取“利息费用支付”业务中该客户所有活期账户的利息费用及复利、罚息中的交易金额
?	在结算模块，读取“贷款收回”（包括自营贷款收回、委托贷款收回）业务中该客户所有活期账户的利息费用及复利、罚息中的交易金额
?	在结算模块，读取“季度结息” 业务中该客户所有活期账户的利息费用及复利、罚息中的交易金额

 * */
				//amountDirection = TPConstant.AmountDirection.PLUS;			
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("利息费用支付1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("利息费用支付2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);	
				
				
				
			}else if(lineLevel.indexOf("005_012_002") >=0){//L3贷款手续费收入
				//updatedTemplateItemInfo.setLineID(templateInfo.getId());				
/**
 * ?	在结算模块，读取“利息费用支付”业务中该客户所有活期账户的手续费、担保费的交易金额
?	在结算模块，读取“贷款收回”（包括自营贷款收回、委托贷款收回）业务中该客户所有活期账户的手续费、担保费的交易金额
?	在结算模块，读取“季度结息” 业务中该客户所有活期账户的手续费、担保费的交易金额

 * */
				//amountDirection = TPConstant.AmountDirection.PLUS;			
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("手续费、担保费1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("手续费、担保费2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);					
			}else if(lineLevel.indexOf("005_012_003") >=0){//L3同业收入
/**
 * ?	读取财务系统金融企业往来收入科目：“贷”方发生额
?	转贴现台账：用户查询的日期范围内每日的，买入/卖出类型为买入，转贴利息的合计
?	债券回购台账：用户查询的日期范围内每日的，交易类型为逆回购，已经购回的，利息的合计
?	贷款回购台账：用户查询的日期范围内每日的，交易类型为买入，已经购回的，利息的合计
 * */				
				
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXLX);
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo1.setAmountFlag(1);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo2.setAccountTypeId(SETTConstant.SubInvestMentType.HGDQLX);
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo2);				
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo3.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGLX);
				updatedTemplateItemInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo3.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo3.setAmountFlag(1);
				list.add(updatedTemplateItemInfo3);								


			}else if(lineLevel.indexOf("005_012_004") >=0){//L3营业外收入
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("营业外收入科目的发生额");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_012_005") >=0){//L3其它
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// 读取财务系统该日其他营业收入科目的发生额
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("其他营业收入科目的发生额");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
			}
			
			
			
			
			
			
////////////////////////////////////
			if(lineLevel.indexOf("006_005_001") >=0){//L3同业拆出
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.TYCC);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_005_002") >=0){//L3拆入还款
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.CRHK);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_001") >=0){//L3股票
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.GPMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_002") >=0){//L3交易所国债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JYSGZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_003") >=0){//L3可转债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KZZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_004") >=0){//L3企业债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.QYZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_005") >=0){//L3封闭式基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.FBSJJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_006") >=0){//L3开放式基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KFSJJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_007") >=0){//L3委托理财
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				updatedTemplateItemInfo.setGlSubjectCode("委托理财付方科目该日的发生额");		
				updatedTemplateItemInfo.setModuleID(9);	
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				
			}else if(lineLevel.indexOf("006_007_001") >=0){//L3央行票据
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHPJMR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_007_002") >=0){//L3银行间国债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_007_003") >=0){//L3金融债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JRZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_007_004") >=0){//L3政策性金融债
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZCXJRZMC);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lineLevel.indexOf("006_007_005") >=0){//L3货币市场基金
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HBSCZJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_009_001") >=0){//L3L3融入资金还款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMCGHHG);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);			
			}else if(lineLevel.indexOf("006_009_002") >=0){//L3买入票据付款
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMRMD);
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMRHG);
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo2);
				
			}else if(lineLevel.indexOf("006_010_001") >=0){//L3股权投资
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				//取在用户查询的范围内每日的财务长期投资—股权投资科目：“负”发生额，即贷方发生额。
				updatedTemplateItemInfo.setGlSubjectCode("财务长期投资—股权投资科目：“负”发生额，即贷方发生额");
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);
				
			}else if(lineLevel.indexOf("006_010_002") >=0){//L3债券承销  
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				//内每日的财务长期投资—债券投资科目：“负”发生额，即贷方发生额
				updatedTemplateItemInfo.setGlSubjectCode("财务长期投资—股权投资科目：“负”发生额，即贷方发生额");
				updatedTemplateItemInfo.setModuleID(9);				
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3存款利息支出
			
/**
?	在结算模块，读取“自营贷款收回”、 “委托贷款收回” 、“利息费用支付” 、“季度结息”业务中该客户活期账户的交易记录中的交易金额（包括利息、复利、罚息栏位的金额； 
?	在结算模块，读取“贴现贷款收回”业务中退票处理时，该客户活期账户的交易金额（包括罚息）
?	在结算模块，读取“自营贷款收回”、 “委托贷款收回” 、“利息费用支付” 、“季度结息”业务中该客户活期账户的交易记录中的交易金额（包括手续费、担保费栏位的金额； 
 * */
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("存款利息支出1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("存款利息支出2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());
				updatedTemplateItemInfo3.setGlSubjectCode("存款利息支出3");
				updatedTemplateItemInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo3.setAmountFlag(1);
				updatedTemplateItemInfo3.setModuleID(1);
				list.add(updatedTemplateItemInfo2);								
				
			}else if(lineLevel.indexOf("006_011_002") >=0){//L3手续费支出
/**
?	在结算模块，读取“自营贷款收回”、 “委托贷款收回” 、“利息费用支付” 、“季度结息”业务中该客户活期账户的交易记录中的交易金额（包括手续费、担保费栏位的金额； 
 * */
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("手续费支出1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("手续费支出2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);	
				
				
				
			}else if(lineLevel.indexOf("005_012_003") >=0){//L3同业收入
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("财务系统金融企业往来收入科目：“贷”方发生额");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lineLevel.indexOf("006_011_003") >=0){//L3营业费用
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// 读取财务系统该日营业费用科目的发生额
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("营业费用科目的发生额");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);	
			}
			else if(lineLevel.indexOf("006_011_004") >=0){//L3营业外支出		
				//读取财务系统该日营业外支出科目的发生额
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("营业外支出科目的发生额");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);	
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_011_005") >=0){//L3其它
/**
?	拆借台账：用户查询的日期范围内每日的，拆借类型为拆入，已经返款的，利息额的合计
?	转贴现台账：用户查询的日期范围内每日的，买入/卖出类型为卖出，转贴利息的合计
?	贷款回购台账：用户查询的日期范围内每日的，已经购回的，到期应付利息的合计
?	债券回购台账：用户查询的日期范围内每日的，交易类型为正回购，已经购回的，利息的合计
 * 
 * */
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXLX);
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo2.setAccountTypeId(SETTConstant.SubInvestMentType.HGDQLX);
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo2);				
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());				
				//updatedTemplateItemInfo3.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGLX);
				updatedTemplateItemInfo3.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo3.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo3);
				
				TPTemplateItemInfo updatedTemplateItemInfo4 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo4.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo4.setAccountTypeId(SETTConstant.SubInvestMentType.CRLX);
				updatedTemplateItemInfo4.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
				updatedTemplateItemInfo4.setModuleID(Constant.ModuleType.SECURITIES);
				list.add(updatedTemplateItemInfo4);														
			}		
////////////////////////
			
			
			
			
			
			
			
			
			if(updatedTemplateItemInfo.getLineID() >= 0 ){			
				updatedTemplateItemInfo.setAmountDirection(amountDirection);
				updatedTemplateItemInfo.setAmountFlag(1);
				this.setUseMaxID();
	
				this.add(updatedTemplateItemInfo);			
				
				for(int j=0;j<list.size();j++){
	
					TPTemplateItemInfo tmpTemplateItemInfo = (TPTemplateItemInfo) list.get(j);
					this.setUseMaxID();
					System.out.println("XXXXXXXXXXXXXX"+tmpTemplateItemInfo);
					this.add(tmpTemplateItemInfo);								
				}					
			}
		}
		
		
		transConn.close();		
	}
	
	
	public void consturctLevel4TemplateItem() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);		
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(transConn);		
		Collection c = templateDAO.getLevelNTemplateLines(4);
		Iterator it = c.iterator();		


		while(it.hasNext()){
			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
			//TPTemplateInfo parentTemplateInfo = (TPTemplateInfo) templateDAO.findByID(templateInfo.getParentLineID(),TPTemplateInfo.class);
			TPTemplateItemInfo updatedTemplateItemInfo = new TPTemplateItemInfo();

			String lineLevel = templateInfo.getLineNo();
			String lineName = templateInfo.getLineName();
			if(templateInfo.getId() == 654){
				System.out.println("-----------clientName:");				
			}
			
			String clientName = templateInfo.getLineName().substring(2);
			String clientCode = getClientCodeByClientName(clientName);
			System.out.println("-----------clientName:"+clientName);
			System.out.println("-----------clientCode:"+clientCode);			
			
			long amountDirection = -1;			
			if(lineLevel.indexOf("005_002") >=0){//L2委托存款 
/**
 * 在用户输入的查询范围内每日的：该客户“委托存款”账户的“正”发生额（即该账户所增加的余额）
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);				
			}else if(lineLevel.indexOf("005_003") >=0 ){//L2委托收款 
/**
 * 取用户输入的查询范围内每日的，财务系统委托存款—代理业务科目-客户1的“正”发生额（即该科目所增加的余额）
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("委托存款—代理业务科目-客户1的“正”发生额");
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_005") >= 0 || lineLevel.indexOf("006_004") >= 0){//L2贴现收回||L2贴现放款
/**
 * 此栏位的取值原则为：在用户查询的日期范围内每日的，该客户该账户的“正”发生额（即该账户所增加的余额），
 * 引起账户余额增加的情况如下：（不参与公式计算）
  在结算模块，读取“贴现贷款收回”交易中该客户贴现贷款账户的发生额（即票据的票面金额）
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.DISCOUNT);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_007_008_001") >= 0){//L4融资回购
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RZHG);
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.PLUS;
			}else if(lineLevel.indexOf("005_008_006_001") >= 0){//L4正回购
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HG);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setModuleID(5);
			}else if(lineLevel.indexOf("005_008_006_002") >= 0){//L4逆回购到期
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.NHGDQ);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setModuleID(5);
			}else if(lineLevel.indexOf("006_002") >= 0){//L2委托付款
/**
 * 取用户输入的查询范围内每日的，财务系统委托存款—代理业务科目“负”发生额（即该科目所减少的余额）
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("财务系统委托存款—代理业务科目“负”发生额");
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setAmountFlag(1);
				//@TBD				
			}else if(lineLevel.indexOf("006_006_008_001") >= 0){//L4融资购回
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RZGH);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_006_008_002") >= 0){//L4融资购回
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RJHG);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_007_006_001") >= 0){//L4正回购
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZHGDQ);
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_007_006_002") >= 0){//L4逆回购到期
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.NHG);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else
				continue;
			

 
			if(updatedTemplateItemInfo.getLineID() > 0){
				if(amountDirection > 0)
					updatedTemplateItemInfo.setAmountDirection(amountDirection);
				updatedTemplateItemInfo.setAmountFlag(1);
				this.setUseMaxID();
				this.add(updatedTemplateItemInfo);
			}
		}		
		
		transConn.close();		
	}
	
	public void consturctLevel5TemplateItem() throws Exception{
		this.transConn = Database.getConnection();
		this.setSelfManagedConn(true);
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(transConn);
		Collection c = templateDAO.getLevelNTemplateLines(5);
		Iterator it = c.iterator();
		while(it.hasNext()){
			TPTemplateInfo templateInfo = (TPTemplateInfo) it.next();
			TPTemplateInfo parentTemplateInfo = (TPTemplateInfo) templateDAO.findByID(templateInfo.getParentLineID(),TPTemplateInfo.class);
			TPTemplateItemInfo updatedTemplateItemInfo = new TPTemplateItemInfo();
			updatedTemplateItemInfo.setLineID(templateInfo.getId());

			String lineLevel = templateInfo.getLineNo();
			String lineName = templateInfo.getLineName();
			boolean isAssetAccount = true;
			if(lineName.indexOf("L5活期") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);				
			}else if(lineName.indexOf("L5定期") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.FIXED);
			}else if(lineName.indexOf("L5通知") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.NOTIFY);
			}else if(lineName.indexOf("L5自营") >= 0){
				isAssetAccount = true;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.TRUST);
			}else if(lineName.indexOf("L5委贷") >= 0){
				isAssetAccount = true;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.CONSIGN);
			}
			
			String clientName = parentTemplateInfo.getLineName().substring(2);
			String clientCode = getClientCodeByClientName(clientName);
			System.out.println("-----------clientName:"+clientName);
			System.out.println("-----------clientCode:"+clientCode);
			
			
			if(clientCode == null || clientCode.trim().length() == 0)
				clientCode = "No such Client";
			updatedTemplateItemInfo.setClientCode(clientCode);
			
			long amountDirection = -1;
 
			System.out.println("-----------lineLevel:"+lineLevel);
			String sourceOrUse = lineLevel.substring(2,3);
			long lSourceOrUse = Long.parseLong(sourceOrUse); 			
			System.out.println("-----------sourceOrUse:"+sourceOrUse);
			if(lSourceOrUse == 5){//来源
				if(!isAssetAccount)
					amountDirection = TPConstant.AmountDirection.PLUS;
				else
					amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lSourceOrUse == 6){//运用
				if(!isAssetAccount)
					amountDirection = TPConstant.AmountDirection.SUBTRACT;
				else
					amountDirection = TPConstant.AmountDirection.PLUS;
			} 
			updatedTemplateItemInfo.setAmountDirection(amountDirection);
			updatedTemplateItemInfo.setAmountFlag(1);
			this.setUseMaxID();
			this.add(updatedTemplateItemInfo);
		}
		transConn.close();
	}
	
 
	public String getClientCodeByClientName(String clientName) throws Exception{
		String strSQL = "select scode from client where NSTATUSID > 0 and sname = '"+ clientName+"'";
		this.prepareStatement(strSQL);
		ResultSet localRS = this.executeQuery();
		String clientcode = "";
		if(localRS.next())
			clientcode = localRS.getString("scode");
		finalizeDAO();
		return clientcode;
		
	}
	
	static public void main(String args[]){
		Trea_TPTemplateItemDAO templateItemDAO = new Trea_TPTemplateItemDAO();
		try {
			templateItemDAO.consturctLevel1TemplateItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查是否有新增的开户行,并返回新增的开户行科目
	 * */
	public Collection getNewBranchSubject()throws Exception{
		ArrayList list = new ArrayList();
		String strSQL = "select ssubjectcode From SETT_BRANCH where ncurrencyid=1 and nstatusid=1 and ssubjectcode not in(select glsubjectcode From TREA_TPTEMPLATEITEM where lineid=1 and glsubjectcode is not null)";
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		while(localRS.next()){
			String subject = localRS.getString("ssubjectcode");
			list.add(subject);
		}
		finalizeDAO();
		return list;		
	} 
}
