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
                throw new ITreasuryDAOException("���ݿ�ر��쳣����",e);         
            }
            
            finalizeDAO();
            
		}
        
		return c;
	}
	

	/**
	 * ��ResultSet�л�ȡ��ѯ�����
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @param ��
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
			if(lineLevel.indexOf("001") >=0){//L1�ڳ����
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
			}else if(lineLevel.indexOf("002") >=0){//L1���׼����
				//���д�΢·��Ŀ�û���ѯ�����ڷ�Χ��ÿ�յĴ���������
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.GENERALLEDGER);
				// ��ȡ����ϵͳ���д��--����������п����µĴ��׼�����Ŀ�跽��� 
				updatedTemplateItemInfo1.setGlSubjectCode("����������п����µĴ��׼�����Ŀ�跽���");
				list.add(updatedTemplateItemInfo1);
				
			}else if(lineLevel.indexOf("003") >=0){//L1��֤��
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setModuleID(Constant.ModuleType.GENERALLEDGER);				
				// ��ȡ���ղ���ϵͳ�����д���»��ڴ���µı�֤���Ŀ�跽����֪ͨ����±�֤���Ŀ�跽���
				updatedTemplateItemInfo1.setGlSubjectCode("���д���»��ڴ���µı�֤���Ŀ�跽���");
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setModuleID(Constant.ModuleType.GENERALLEDGER);				
				// ��ȡ���ղ���ϵͳ�����д���»��ڴ���µı�֤���Ŀ�跽����֪ͨ����±�֤���Ŀ�跽���
				updatedTemplateItemInfo2.setGlSubjectCode("֪ͨ����±�֤���Ŀ�跽���");
				list.add(updatedTemplateItemInfo1);				
			}else if(lineLevel.indexOf("004") >=0){//L1�ڳ��������=�ڳ����-���׼����-��֤��
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
			}else if(lineLevel.indexOf("007") >=0){//L1��Ҫ������=��ҵ���*��Ҫ��������
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
			}else if(lineLevel.indexOf("008") >=0){//L1�ʽ�ͷ��=��ĩ�������-��Ҫ������
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1288");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#1283");
				updatedTemplateItemInfo2.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo2);
			}else if(lineLevel.indexOf("010") >=0){//L1���ں��ʽ�ͷ��
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setParameter("#1284");	
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo2.setParameter("#1285");
				updatedTemplateItemInfo2.setCalculateFlag("-");
				list.add(updatedTemplateItemInfo2);
			}else if(lineLevel.indexOf("011") >=0){//L1��ĩ���
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
			}else if(lineLevel.indexOf("012") >=0){//L1��ĩ�������=�ڳ��������+�ʽ���Դ-�ʽ�����
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
			if(lineLevel.indexOf("005_009") >=0){//L2�ʲ��ع�����
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGJK);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo1.setModuleID(1);
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
			}else if(lineLevel.indexOf("006_008") >=0){//L2�ʲ��ع�����
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo1.setAccountTypeId(SETTConstant.SubInvestMentType.ZCHGCK);
				updatedTemplateItemInfo1.setModuleID(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
			}else if(lineLevel.indexOf("006_012") >=0){//L2��������
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setModuleID(9);
				//���д�΢·��Ŀ�û���ѯ�����ڷ�Χ��ÿ�յĴ���������
				updatedTemplateItemInfo1.setGlSubjectCode("1020010103");
				System.out.println("++++++++++"+updatedTemplateItemInfo1);
				list.add(updatedTemplateItemInfo1);
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setModuleID(9);
				//��Ӧ��˰���Ŀ����������
				updatedTemplateItemInfo2.setGlSubjectCode("Ӧ��˰���Ŀ����������");
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
			if(lineLevel.indexOf("005_006_001") >=0){//L3ͬҵ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.TYCR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_006_002") >=0){//L3�������
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.CCHK);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_001") >=0){//L3��Ʊ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.GPMC);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_007_002") >=0){//L3��������ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JYSGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_003") >=0){//L3��תծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KZZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_004") >=0){//L3��ҵծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.QYZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_005") >=0){//L3���ʽ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.FBSJJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_006") >=0){//L3����ʽ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KFSJJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_007_007") >=0){//L3ί�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				updatedTemplateItemInfo.setGlSubjectCode("ί������շ���Ŀ���յķ�����");
				amountDirection = TPConstant.AmountDirection.PLUS;			
				updatedTemplateItemInfo.setModuleID(9);				
			}else if(lineLevel.indexOf("005_008_001") >=0){//L3����Ʊ��
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHPJMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_002") >=0){//L3���м��ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_002") >=0){//L3���м��ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_003") >=0){//L3����ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JRZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("005_008_004") >=0){//L3�����Խ���ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZCXJRZMC);
				amountDirection = TPConstant.AmountDirection.PLUS;
			}else if(lineLevel.indexOf("005_008_005") >=0){//L3�����г�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HBSCZJMC);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.PLUS;				
			}else if(lineLevel.indexOf("005_010_001") >=0){//L3�����ʽ����
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
			}else if(lineLevel.indexOf("005_010_002") >=0){//L3����Ʊ�ݻؿ�
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
				
			}else if(lineLevel.indexOf("005_011_001") >=0){//L3��ȨͶ��
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// ȡ���û���ѯ�ķ�Χ��ÿ�յĲ�����Ͷ�ʡ�ծȯͶ�ʿ�Ŀ�����������������������������빫ʽ���㣩
//				subjectCode = ?
				updatedTemplateItemInfo.setGlSubjectCode("������Ͷ�ʡ���ȨͶ�ʿ�Ŀ������������");
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);
				
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3ծȯ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// @TBD  ȡ���û���ѯ�ķ�Χ��ÿ�յĲ�����Ͷ�ʡ�ծȯͶ�ʿ�Ŀ�����������������������������빫ʽ���㣩
//				subjectCode = ?		
				updatedTemplateItemInfo.setGlSubjectCode("������Ͷ�ʡ�ծȯͶ�ʿ�Ŀ������������");
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);											
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3������Ϣ���루˰��
				//updatedTemplateItemInfo.setLineID(templateInfo.getId());				
/**
 * ?	�ڽ���ģ�飬��ȡ����Ϣ����֧����ҵ���иÿͻ����л����˻�����Ϣ���ü���������Ϣ�еĽ��׽��
?	�ڽ���ģ�飬��ȡ�������ջء���������Ӫ�����ջء�ί�д����ջأ�ҵ���иÿͻ����л����˻�����Ϣ���ü���������Ϣ�еĽ��׽��
?	�ڽ���ģ�飬��ȡ�����Ƚ�Ϣ�� ҵ���иÿͻ����л����˻�����Ϣ���ü���������Ϣ�еĽ��׽��

 * */
				//amountDirection = TPConstant.AmountDirection.PLUS;			
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("��Ϣ����֧��1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("��Ϣ����֧��2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);	
				
				
				
			}else if(lineLevel.indexOf("005_012_002") >=0){//L3��������������
				//updatedTemplateItemInfo.setLineID(templateInfo.getId());				
/**
 * ?	�ڽ���ģ�飬��ȡ����Ϣ����֧����ҵ���иÿͻ����л����˻��������ѡ������ѵĽ��׽��
?	�ڽ���ģ�飬��ȡ�������ջء���������Ӫ�����ջء�ί�д����ջأ�ҵ���иÿͻ����л����˻��������ѡ������ѵĽ��׽��
?	�ڽ���ģ�飬��ȡ�����Ƚ�Ϣ�� ҵ���иÿͻ����л����˻��������ѡ������ѵĽ��׽��

 * */
				//amountDirection = TPConstant.AmountDirection.PLUS;			
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("�����ѡ�������1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("�����ѡ�������2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);					
			}else if(lineLevel.indexOf("005_012_003") >=0){//L3ͬҵ����
/**
 * ?	��ȡ����ϵͳ������ҵ���������Ŀ����������������
?	ת����̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ�����/��������Ϊ���룬ת����Ϣ�ĺϼ�
?	ծȯ�ع�̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ���������Ϊ��ع����Ѿ����صģ���Ϣ�ĺϼ�
?	����ع�̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ���������Ϊ���룬�Ѿ����صģ���Ϣ�ĺϼ�
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


			}else if(lineLevel.indexOf("005_012_004") >=0){//L3Ӫҵ������
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("Ӫҵ�������Ŀ�ķ�����");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_012_005") >=0){//L3����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// ��ȡ����ϵͳ��������Ӫҵ�����Ŀ�ķ�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("����Ӫҵ�����Ŀ�ķ�����");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
			}
			
			
			
			
			
			
////////////////////////////////////
			if(lineLevel.indexOf("006_005_001") >=0){//L3ͬҵ���
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.TYCC);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_005_002") >=0){//L3���뻹��
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.CRHK);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_001") >=0){//L3��Ʊ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.GPMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_002") >=0){//L3��������ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JYSGZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_006_003") >=0){//L3��תծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KZZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_004") >=0){//L3��ҵծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.QYZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_005") >=0){//L3���ʽ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.FBSJJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_006") >=0){//L3����ʽ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.KFSJJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_006_007") >=0){//L3ί�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				updatedTemplateItemInfo.setGlSubjectCode("ί����Ƹ�����Ŀ���յķ�����");		
				updatedTemplateItemInfo.setModuleID(9);	
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				
			}else if(lineLevel.indexOf("006_007_001") >=0){//L3����Ʊ��
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHPJMR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_007_002") >=0){//L3���м��ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.YHJGZMR);
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
			}else if(lineLevel.indexOf("006_007_003") >=0){//L3����ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.JRZMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_007_004") >=0){//L3�����Խ���ծ
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZCXJRZMC);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lineLevel.indexOf("006_007_005") >=0){//L3�����г�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HBSCZJMR);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;				
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);
			}else if(lineLevel.indexOf("006_009_001") >=0){//L3L3�����ʽ𻹿�
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZTXMCGHHG);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(Constant.ModuleType.SECURITIES);			
			}else if(lineLevel.indexOf("006_009_002") >=0){//L3����Ʊ�ݸ���
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
				
			}else if(lineLevel.indexOf("006_010_001") >=0){//L3��ȨͶ��
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				//ȡ���û���ѯ�ķ�Χ��ÿ�յĲ�����Ͷ�ʡ���ȨͶ�ʿ�Ŀ������������������������
				updatedTemplateItemInfo.setGlSubjectCode("������Ͷ�ʡ���ȨͶ�ʿ�Ŀ�������������������������");
				amountDirection = TPConstant.AmountDirection.SUBTRACT;			
				updatedTemplateItemInfo.setModuleID(9);
				
			}else if(lineLevel.indexOf("006_010_002") >=0){//L3ծȯ����  
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				//��ÿ�յĲ�����Ͷ�ʡ�ծȯͶ�ʿ�Ŀ�������������������������
				updatedTemplateItemInfo.setGlSubjectCode("������Ͷ�ʡ���ȨͶ�ʿ�Ŀ�������������������������");
				updatedTemplateItemInfo.setModuleID(9);				
			}else if(lineLevel.indexOf("005_011_002") >=0){//L3�����Ϣ֧��
			
/**
?	�ڽ���ģ�飬��ȡ����Ӫ�����ջء��� ��ί�д����ջء� ������Ϣ����֧���� �������Ƚ�Ϣ��ҵ���иÿͻ������˻��Ľ��׼�¼�еĽ��׽�������Ϣ����������Ϣ��λ�Ľ� 
?	�ڽ���ģ�飬��ȡ�����ִ����ջء�ҵ������Ʊ����ʱ���ÿͻ������˻��Ľ��׽�������Ϣ��
?	�ڽ���ģ�飬��ȡ����Ӫ�����ջء��� ��ί�д����ջء� ������Ϣ����֧���� �������Ƚ�Ϣ��ҵ���иÿͻ������˻��Ľ��׼�¼�еĽ��׽����������ѡ���������λ�Ľ� 
 * */
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("�����Ϣ֧��1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("�����Ϣ֧��2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);
				
				TPTemplateItemInfo updatedTemplateItemInfo3 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo3.setLineID(templateInfo.getId());
				updatedTemplateItemInfo3.setGlSubjectCode("�����Ϣ֧��3");
				updatedTemplateItemInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo3.setAmountFlag(1);
				updatedTemplateItemInfo3.setModuleID(1);
				list.add(updatedTemplateItemInfo2);								
				
			}else if(lineLevel.indexOf("006_011_002") >=0){//L3������֧��
/**
?	�ڽ���ģ�飬��ȡ����Ӫ�����ջء��� ��ί�д����ջء� ������Ϣ����֧���� �������Ƚ�Ϣ��ҵ���иÿͻ������˻��Ľ��׼�¼�еĽ��׽����������ѡ���������λ�Ľ� 
 * */
				TPTemplateItemInfo updatedTemplateItemInfo1 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo1.setLineID(templateInfo.getId());
				updatedTemplateItemInfo1.setGlSubjectCode("������֧��1");
				updatedTemplateItemInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo1.setAmountFlag(1);
				updatedTemplateItemInfo1.setModuleID(1);
				list.add(updatedTemplateItemInfo1);	
				
				TPTemplateItemInfo updatedTemplateItemInfo2 = new TPTemplateItemInfo();				
				updatedTemplateItemInfo2.setLineID(templateInfo.getId());
				updatedTemplateItemInfo2.setGlSubjectCode("������֧��2");
				updatedTemplateItemInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
				updatedTemplateItemInfo2.setAmountFlag(1);
				updatedTemplateItemInfo2.setModuleID(1);
				list.add(updatedTemplateItemInfo2);	
				
				
				
			}else if(lineLevel.indexOf("005_012_003") >=0){//L3ͬҵ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("����ϵͳ������ҵ���������Ŀ����������������");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lineLevel.indexOf("006_011_003") >=0){//L3Ӫҵ����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				// ��ȡ����ϵͳ����Ӫҵ���ÿ�Ŀ�ķ�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("Ӫҵ���ÿ�Ŀ�ķ�����");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);	
			}
			else if(lineLevel.indexOf("006_011_004") >=0){//L3Ӫҵ��֧��		
				//��ȡ����ϵͳ����Ӫҵ��֧����Ŀ�ķ�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("Ӫҵ��֧����Ŀ�ķ�����");
				updatedTemplateItemInfo.setModuleID(9);
				updatedTemplateItemInfo.setAmountFlag(1);	
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_011_005") >=0){//L3����
/**
?	���̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ��������Ϊ���룬�Ѿ�����ģ���Ϣ��ĺϼ�
?	ת����̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ�����/��������Ϊ������ת����Ϣ�ĺϼ�
?	����ع�̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ��Ѿ����صģ�����Ӧ����Ϣ�ĺϼ�
?	ծȯ�ع�̨�ˣ��û���ѯ�����ڷ�Χ��ÿ�յģ���������Ϊ���ع����Ѿ����صģ���Ϣ�ĺϼ�
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
			if(lineLevel.indexOf("005_002") >=0){//L2ί�д�� 
/**
 * ���û�����Ĳ�ѯ��Χ��ÿ�յģ��ÿͻ���ί�д��˻��ġ���������������˻������ӵ���
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);				
			}else if(lineLevel.indexOf("005_003") >=0 ){//L2ί���տ� 
/**
 * ȡ�û�����Ĳ�ѯ��Χ��ÿ�յģ�����ϵͳί�д�����ҵ���Ŀ-�ͻ�1�ġ�������������ÿ�Ŀ�����ӵ���
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("ί�д�����ҵ���Ŀ-�ͻ�1�ġ�����������");
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_005") >= 0 || lineLevel.indexOf("006_004") >= 0){//L2�����ջ�||L2���ַſ�
/**
 * ����λ��ȡֵԭ��Ϊ�����û���ѯ�����ڷ�Χ��ÿ�յģ��ÿͻ����˻��ġ���������������˻������ӵ�����
 * �����˻�������ӵ�������£��������빫ʽ���㣩
  �ڽ���ģ�飬��ȡ�����ִ����ջء������иÿͻ����ִ����˻��ķ������Ʊ�ݵ�Ʊ���
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.DISCOUNT);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("005_007_008_001") >= 0){//L4���ʻع�
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RZHG);
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
				amountDirection = TPConstant.AmountDirection.PLUS;
			}else if(lineLevel.indexOf("005_008_006_001") >= 0){//L4���ع�
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.HG);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setModuleID(5);
			}else if(lineLevel.indexOf("005_008_006_002") >= 0){//L4��ع�����
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.NHGDQ);
				amountDirection = TPConstant.AmountDirection.PLUS;
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setModuleID(5);
			}else if(lineLevel.indexOf("006_002") >= 0){//L2ί�и���
/**
 * ȡ�û�����Ĳ�ѯ��Χ��ÿ�յģ�����ϵͳί�д�����ҵ���Ŀ��������������ÿ�Ŀ�����ٵ���
 * */				
				updatedTemplateItemInfo.setLineID(templateInfo.getId());			
				updatedTemplateItemInfo.setGlSubjectCode("����ϵͳί�д�����ҵ���Ŀ������������");
				updatedTemplateItemInfo.setModuleID(1);
				updatedTemplateItemInfo.setAmountFlag(1);
				updatedTemplateItemInfo.setClientCode(clientCode);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setAmountFlag(1);
				//@TBD				
			}else if(lineLevel.indexOf("006_006_008_001") >= 0){//L4���ʹ���
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RZGH);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_006_008_002") >= 0){//L4���ʹ���
				updatedTemplateItemInfo.setLineID(templateInfo.getId());				
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.RJHG);
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_007_006_001") >= 0){//L4���ع�
				updatedTemplateItemInfo.setLineID(templateInfo.getId());
				amountDirection = TPConstant.AmountDirection.SUBTRACT;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.SubInvestMentType.ZHGDQ);
				updatedTemplateItemInfo.setModuleID(5);
				updatedTemplateItemInfo.setAmountFlag(1);
			}else if(lineLevel.indexOf("006_007_006_002") >= 0){//L4��ع�����
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
			if(lineName.indexOf("L5����") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);				
			}else if(lineName.indexOf("L5����") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.FIXED);
			}else if(lineName.indexOf("L5֪ͨ") >= 0){
				isAssetAccount = false;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.NOTIFY);
			}else if(lineName.indexOf("L5��Ӫ") >= 0){
				isAssetAccount = true;
				updatedTemplateItemInfo.setAccountTypeId(SETTConstant.AccountGroupType.TRUST);
			}else if(lineName.indexOf("L5ί��") >= 0){
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
			if(lSourceOrUse == 5){//��Դ
				if(!isAssetAccount)
					amountDirection = TPConstant.AmountDirection.PLUS;
				else
					amountDirection = TPConstant.AmountDirection.SUBTRACT;
			}else if(lSourceOrUse == 6){//����
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
	 * ����Ƿ��������Ŀ�����,�����������Ŀ����п�Ŀ
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
