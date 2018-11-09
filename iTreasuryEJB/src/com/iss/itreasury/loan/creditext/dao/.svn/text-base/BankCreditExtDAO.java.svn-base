package com.iss.itreasury.loan.creditext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.creditext.dataentity.*;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;


public class BankCreditExtDAO  extends LoanDAO
{

	public BankCreditExtDAO() 
	{
		super("LOAN_BANK_CREDITEXT");	
	}
	public BankCreditExtDAO(Connection conn) 
	{
		super("LOAN_BANK_CREDITEXT",conn);
	}
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	/**
	 * 数据库资源
	 */
	private Connection conn = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	/**
	 * 获得SQL语句
	 * @param info
	 * @return
	 */
	private String[] getSQL(BankCreditExtQueryInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strTemp = new StringBuffer();

		//select
		sql[0] = " * ";
			
		//from
		sql[1] = " v_loan_bankcreditextresult ";

		//where	
		/**********处理查询条件*************/		
		strTemp.append(" isAvlid>0 ");
		if(!info.getYear().equals(""))
		{
			strTemp.append(" and year LIKE '%" + info.getYear() + "%'");
		}
		if(info.getId() != -1)
		{
			strTemp.append(" and no= " + info.getId());
		}
		if(!info.getCompany().equals(""))
		{
			strTemp.append(" and company LIKE '%" + info.getCompany() + "%'");			
		}	
		if(!info.getBankName().equals(""))
		{
			strTemp.append(" and bankName LIKE '%" + info.getBankName() + "%'");			
		}	
		if(!info.getStartDate1().equals(""))
		{
			strTemp.append(" and startDate>=(TO_DATE('" + info.getStartDate1() + "','YYYY-MM-DD')) ");
		}
		if(!info.getStartDate2().equals(""))
		{
			strTemp.append(" and startDate<=(TO_DATE('" + info.getStartDate2() + "','YYYY-MM-DD')) ");
		}
		if(!info.getEndDate1().equals(""))
		{
			strTemp.append(" and endDate>=(TO_DATE('" + info.getEndDate1() + "','YYYY-MM-DD')) ");
		}
		if(!info.getEndDate2().equals(""))
		{
			strTemp.append(" and endDate<=(TO_DATE('" + info.getEndDate2() + "','YYYY-MM-DD')) ");
		}
		if(!info.getOperationDate1().equals(""))
		{
			strTemp.append(" and operationDate>=(TO_DATE('" + info.getOperationDate1() + "','YYYY-MM-DD')) ");
		}
		if(!info.getOperationDate2().equals(""))
		{
			strTemp.append(" and operationDate<=(TO_DATE('" + info.getOperationDate2() + "','YYYY-MM-DD')) ");
		}
		if(info.getStatus() != -1)
		{
			strTemp.append(" and status=" + info.getStatus());
		}
		
		//added by mzh_fu 2007/03/26 区分办事处
		if(info.getOfficeId() != -1)
		{
			strTemp.append(" and nofficeid =" + info.getOfficeId());
		}
		
		sql[2] = strTemp.toString();
		
		//order
		sql[3] = " ORDER BY no DESC,year DESC,variety ";
			
		log4j.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		return sql;
	}
	
	/**
	 * 分页查询，根据条件查询银行授信信息
	 * @param info
	 * @return PageLoader对象
	 * @throws Exception
	 */
	public PageLoader getBankCreditExt(BankCreditExtQueryInfo  queryInfo) throws Exception
	{
		String[] sql = this.getSQL(queryInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.creditext.dataentity.BankCreditExtResultInfo", null);	
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	
	/**
	 * 根据银行授信ID查询银行授信信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BankCreditExtInfo  getBankCreditExtInfo(long id) throws Exception
	{
		BankCreditExtInfo info = null;
		String strSQL = "SELECT * FROM loan_bank_creditext WHERE id=?";
		log4j.print(strSQL);
		try
		{	
			conn = Database.getConnection();				
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1, id); 
            rs = pstm.executeQuery();
            info = this.getDataEntityFromResultSet(new BankCreditExtInfo());
						
			finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return info;
	}
	
	/**
	 * 根据银行授信ID查询银行混合额度授信信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BankCreditExtMixInfo  getBankCreditExtMixInfo(long id) throws Exception
	{
		BankCreditExtMixInfo mixInfo = null;
		String strSQL = "SELECT * FROM loan_bank_creditext_mix WHERE nBankCreditExtId=?";
		log4j.print(strSQL);
		try
		{	
			conn = Database.getConnection();				
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1, id); 
            rs = pstm.executeQuery();
            mixInfo = this.getDataEntityFromResultSet(new BankCreditExtMixInfo());						
			
			finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return mixInfo;
	}

	/**
	 * 根据银行授信ID和银行授信种类查询银行综合授信信息
	 * @param code
	 * @param variety
	 * @return
	 * @throws Exception
	 */
	public BankCreditExtListInfo  getBankCreditExtListInfo(long code,long variety) throws Exception
	{
		BankCreditExtListInfo listInfo = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM loan_bank_creditext_list WHERE nBankCreditExtId=? AND nVariety=?");
		log4j.print(strSQL.toString());			
		try
        {          
			conn = Database.getConnection();				
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1, code); 
            pstm.setLong(2, variety); 
            rs = pstm.executeQuery();  
            listInfo = this.getDataEntityFromResultSet(new BankCreditExtListInfo());
            finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return listInfo;
	}
	

	/**
	 * 修改银行授信及混合额度信息
	 * @param info
	 * @param mixInfo
	 * @return
	 * @throws Exception
	 */
	public boolean modify(BankCreditExtInfo info, BankCreditExtMixInfo mixInfo) throws Exception  
	{
		boolean modifyOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_bank_creditext SET ");
		strSQL.append("sContractNo=?, sYear=?, sBankName=?, sCompany=?, nReuse=?, nTerm=?, dStartDate=(TO_DATE(?,'YYYY-MM-DD')), ");
		strSQL.append("dEndDate=(TO_DATE(?,'YYYY-MM-DD')), nStatus=?, sLastModifier=?, dLastModifyDate=sysdate, ");
		strSQL.append("sUseLimit=?, sRemark=?, sGuarangy=?, sAccessory=? ");		
		strSQL.append("WHERE id=?" );			
		log4j.print( strSQL.toString());	
		try
        {          
		
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			int index = 1;
            pstm = conn.prepareStatement(strSQL.toString());
             
            pstm.setString(index++, info.getContractNo());            
            pstm.setString(index++, info.getYear());           
            pstm.setString(index++, info.getBankName());          
            pstm.setString(index++, info.getCompany());   
            pstm.setLong(index++, info.getReuse());
            pstm.setLong(index++, info.getTerm());         
            pstm.setString(index++, info.getStartDate());
            pstm.setString(index++, info.getEndDate());
            pstm.setLong(index++, info.getStatus());  
            pstm.setLong(index++, info.getLastModifier());            
            pstm.setString(index++, info.getUseLimit());         
            pstm.setString(index++, info.getRemark());                          
            pstm.setString(index++, info.getGuarangy());         
            pstm.setString(index++, info.getAccessory());
            pstm.setLong(index++, info.getId());
            
            pstm.executeUpdate();
            //
            this.modify(mixInfo);
            //end DAO
            conn.commit();
            modifyOK = true;
            finalize();
        } 
        catch (Exception e) 
        {       	
        	e.printStackTrace();  
        	conn.rollback();
        	throw e;
        }
        finally
        {
           finalize();
        }
		return modifyOK;
	}
	
	/**
	 * 修改混合额度授信信息
	 * @param mixInfo
	 * @return
	 * @throws Exception
	 */
	public boolean modify(BankCreditExtMixInfo mixInfo) throws Exception  
	{
		boolean modifyOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_bank_creditext_mix SET ");
		strSQL.append("nCurrencyType=?, mAmount=?, nExchangeRate=?, nConvertRMB=?, nRiskCoefficient1=?, nRiskCoefficient2=?, ");
		strSQL.append("nRiskCoefficient3=?, nRiskCoefficient4=?, nRiskCoefficient5=?, nRiskCoefficient6=? ");
		strSQL.append("WHERE nBankCreditExtId=? ");		
		log4j.print( strSQL.toString());	
		try
        {          
				
            pstm = conn.prepareStatement(strSQL.toString());
            int index = 1;
          
            pstm.setLong(index++, mixInfo.getCurrencyType());            
            pstm.setDouble(index++, mixInfo.getAmount());           
            pstm.setDouble(index++, mixInfo.getExchangeRate());    
            pstm.setDouble(index++, mixInfo.getConvertRMB()); 
            pstm.setLong(index++, mixInfo.getRiskCoefficient1());   
            pstm.setLong(index++, mixInfo.getRiskCoefficient2());
            pstm.setLong(index++, mixInfo.getRiskCoefficient3());         
            pstm.setLong(index++, mixInfo.getRiskCoefficient4());
            pstm.setLong(index++, mixInfo.getRiskCoefficient5());
            pstm.setLong(index++, mixInfo.getRiskCoefficient6());                         
            pstm.setLong(index++, mixInfo.getBankCreditExtId()); 
            
            pstm.executeUpdate();
            modifyOK = true;
            
 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
            
          
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
        	if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
        }
		return modifyOK;
	}
	
	/**
	 * 修改综合授信信息
	 * @param listInfo
	 * @return
	 * @throws Exception
	 */
	public boolean modify(BankCreditExtListInfo listInfo) throws Exception
	{
		boolean modifyOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_bank_creditext_list SET ");
		strSQL.append("nCurrencyType=?, mAmount=?, nExchangeRate=?, nConvertRMB=?, nRiskCoefficient=? ");
		strSQL.append("WHERE nBankCreditExtId=? AND nVariety=?");		
		log4j.print( strSQL.toString());	
		try
        {          
		
			conn = Database.getConnection();
			int index = 1;
            pstm = conn.prepareStatement(strSQL.toString());                     
            pstm.setLong(index++, listInfo.getCurrencyType());           
            pstm.setDouble(index++, listInfo.getAmount());   
            pstm.setDouble(index++, listInfo.getExchangeRate());  
            pstm.setDouble(index++, listInfo.getConvertRMB());   
            pstm.setLong(index++, listInfo.getRiskCoefficient());
            pstm.setLong(index++, listInfo.getBankCreditExtId()); 
            pstm.setLong(index++, listInfo.getVariety()); 
         
            pstm.executeUpdate();
          
            modifyOK = true;
            //end DAO
            finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return modifyOK;
	}
	
	/**
	 * 判断银行授信的某个种类有没有被分配
	 * @param code
	 * @param variety
	 * @return
	 * @throws Exception
	 */
	public boolean isAlloted(long code, long variety) throws Exception
	{
		boolean yes = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT COUNT(*) FROM loan_bank_creditext_allot WHERE nBankCreditExtId=? ");
		if(variety != -1)
		{
			strSQL.append(" AND nVariety=?");
		}
		log4j.print(strSQL.toString());			
		try
        {          
			conn = Database.getConnection();
				
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1, code); 
            if(variety != -1)
    		{
            	pstm.setLong(2, variety); 
    		}            
            rs = pstm.executeQuery();  
            rs.next();
            if((int)rs.getInt(1) == 0)
            {
            	yes = false;
//            	log4j.print("The variety is not alloted!");
            }
            else
            {
            	yes = true;
//            	log4j.print("The variety is  alloted!");
            }  
            finalize();
        } 
        catch (Exception e) 
        {       	
        	e.printStackTrace();   
        	throw e;
        }
        finally
        {
           finalize();
        }
		return yes;
	
	}
	
	/**
	 * 逻辑删除银行授信
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean delete(long code) throws Exception
	{
		boolean deleteOK = false;
		String strSQL = "UPDATE loan_bank_creditext SET nIsValid=0 WHERE id=? ";			
		log4j.print(strSQL);

		try
        {          
			conn = Database.getConnection();
			
            pstm = conn.prepareStatement(strSQL);
            pstm.setLong(1, code);                                                   
            pstm.executeUpdate();   
        
            deleteOK = true;
            finalize();
        } 
        catch (Exception e) 
        {       	
        	e.printStackTrace();   
        	throw e;
        }
        finally
        {
           finalize();
        }
		return deleteOK;
	}
	
	/**
	 * 物理删除银行综合授信信息
	 * @param code
	 * @param varieties
	 * @return
	 * @throws Exception
	 */
	public boolean delete(long code, List varieties) throws Exception
	{
		
		boolean deleteOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("DELETE FROM loan_bank_creditext_list WHERE nBankCreditExtId=? AND nVariety IN (");
		Iterator it = varieties.iterator();
		for(int i=0;i<varieties.size();i++)
		{
			if( i== varieties.size()-1)
			{
				strSQL.append("?)");
			}
			else
			{
				strSQL.append("?,");
			}			
		}		
		log4j.print(strSQL.toString());			
		try
        {          
			conn = Database.getConnection();
			conn.setAutoCommit(false);	
            pstm = conn.prepareStatement(strSQL.toString());
            int index = 1;
            pstm.setLong(index++, code); 
            while(it.hasNext())
            {           	
            	pstm.setLong(index++, Long.valueOf((String)it.next()).longValue());
            	log4j.print("set  OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }                                             
            pstm.executeUpdate();            
            conn.commit();
            
            deleteOK = true;
            finalize();
        } 
        catch (Exception e) 
        {       	
        	e.printStackTrace();   
        	conn.rollback();
        	throw e;
        }
        finally
        {
           finalize();
        }
		return deleteOK;
	}

	/**
	 * 获得银行授信的最大ID值
	 * @return
	 * @throws Exception
	 */
	public long getMaxId() throws Exception
	{
		long id = -1;
		String strSQL = "SELECT nvl(MAX(id)+1,1) ID FROM loan_bank_creditext";
		try
        {          		
            pstm = conn.prepareStatement(strSQL);  
            rs = pstm.executeQuery();
            if(rs.next())
            {
            	id = rs.getLong(1);
            } 
            if (rs != null) 
 			{
 				rs.close();
 				rs = null;
 			}

 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
        }
		catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        } 
		finally
        {
			if (rs != null) 
 			{
 				rs.close();
 				rs = null;
 			}

 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
        }
		return id;
	}
	
	
	/**
	 *  新增银行授信
	 * @param info
	 * @param mixInfo
	 * @return
	 * @throws Exception
	 */
	public long insert(BankCreditExtInfo info,BankCreditExtMixInfo mixInfo) throws Exception
	{
		long id = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO loan_bank_creditext ");
		strSQL.append("(id, sContractNo, sYear, sBankName, sCompany, nReuse, nTerm, dStartDate, ");
		strSQL.append("dEndDate, dOperationDate, nStatus, nIsValid, sLastModifier, dLastModifyDate, ");
		strSQL.append("sUseLimit, sRemark, sGuarangy, sAccessory ,nOfficeId) ");		
		strSQL.append(" VALUES(?,?,?,?,?,?,?,(TO_DATE(?,'YYYY-MM-DD')),");
		strSQL.append("(TO_DATE(?,'YYYY-MM-DD')),sysdate,?,1,?,sysdate,?,?,?,?,? )" );			
		log4j.print( strSQL.toString());	
		try
        {          
		
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			//得到最大ID号
			id = this.getMaxId();
			int index = 1;
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(index++, id); 
            pstm.setString(index++, info.getContractNo());            
            pstm.setString(index++, info.getYear());           
            pstm.setString(index++, info.getBankName());          
            pstm.setString(index++, info.getCompany());   
            pstm.setLong(index++, info.getReuse());
            pstm.setLong(index++, info.getTerm());         
            pstm.setString(index++, info.getStartDate());
            pstm.setString(index++, info.getEndDate());
            pstm.setLong(index++, info.getStatus());  
            pstm.setLong(index++, info.getLastModifier());            
            pstm.setString(index++, info.getUseLimit());         
            pstm.setString(index++, info.getRemark());                          
            pstm.setString(index++, info.getGuarangy());         
            pstm.setString(index++, info.getAccessory());   
            
            //added by mzh_fu 2007/03/26增加办事处
            pstm.setLong(index++, info.getNOfficeId());  
            pstm.executeUpdate();
            //
            this.insert(mixInfo,id);
            //end DAO
            conn.commit();
            finalize();
        } 
        catch (Exception e) 
        {       	
        	e.printStackTrace();  
        	conn.rollback();
        	throw e;
        }
        finally
        {
           finalize();
        }
		return id;
	}
	
	/**
	 * 新增银行混用额度信息
	 * @param mixInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean insert(BankCreditExtMixInfo mixInfo, long id) throws Exception
	{
		boolean insertOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO loan_bank_creditext_mix ");
		strSQL.append("(nBankCreditExtId, nCurrencyType, mAmount, nExchangeRate, nConvertRMB, nRiskCoefficient1, nRiskCoefficient2, ");
		strSQL.append("nRiskCoefficient3, nRiskCoefficient4, nRiskCoefficient5, nRiskCoefficient6) ");
		strSQL.append(" VALUES(?,?,?,?,?,?,?,?,?,?,? )");		
		log4j.print( strSQL.toString());	
		try
        {          
				
            pstm = conn.prepareStatement(strSQL.toString());
            int index = 1;
            pstm.setLong(index++, id); 
            pstm.setLong(index++, mixInfo.getCurrencyType());            
            pstm.setDouble(index++, mixInfo.getAmount());           
            pstm.setDouble(index++, mixInfo.getExchangeRate());    
            pstm.setDouble(index++, mixInfo.getConvertRMB()); 
            pstm.setLong(index++, mixInfo.getRiskCoefficient1());   
            pstm.setLong(index++, mixInfo.getRiskCoefficient2());
            pstm.setLong(index++, mixInfo.getRiskCoefficient3());         
            pstm.setLong(index++, mixInfo.getRiskCoefficient4());
            pstm.setLong(index++, mixInfo.getRiskCoefficient5());
            pstm.setLong(index++, mixInfo.getRiskCoefficient6());                         
            
            pstm.executeUpdate();
            insertOK = true;
          
            if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {		
 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}
        }
		return insertOK;
	}
	
	/**
	 * 新增银行综合授信信息
	 * @param listInfo
	 * @return
	 * @throws Exception
	 */
	public boolean insert(BankCreditExtListInfo  listInfo) throws Exception
	{
		boolean insertOK = false;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO loan_bank_creditext_list ");
		strSQL.append("(nBankCreditExtId, nVariety, nCurrencyType, mAmount, nExchangeRate, nConvertRMB, nRiskCoefficient )");
		strSQL.append(" VALUES(?,?,?,?,?,?,?)");		
		log4j.print( strSQL.toString());	
		try
        {          
		
			conn = Database.getConnection();
			int index = 1;
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(index++, listInfo.getBankCreditExtId()); 
            pstm.setLong(index++, listInfo.getVariety());            
            pstm.setLong(index++, listInfo.getCurrencyType());           
            pstm.setDouble(index++, listInfo.getAmount());   
            pstm.setDouble(index++, listInfo.getExchangeRate());  
            pstm.setDouble(index++, listInfo.getConvertRMB());   
            pstm.setLong(index++, listInfo.getRiskCoefficient());
         
            pstm.executeUpdate();
          
            insertOK = true;
            //end DAO
            finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return insertOK;
	}
	
	/**
	 * 查看某银行授信包含的所有银行综合授信信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Collection allList(long id) throws Exception
	{
		Collection listResults = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("SELECT * FROM loan_bank_creditext_list WHERE nBankCreditExtId=?");
		log4j.print( strSQL.toString());	
		try
		{
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1,id);
            rs = pstm.executeQuery();
            listResults = getDataEntitiesFromResultSet();
            finalize();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();   	
        	throw e;
        }
        finally
        {
           finalize();
        }
		return listResults;
	}

//	计算所有品种的授信现有余额，为新增时各个授信品种的选择提供信息
	public double getAllotedAmount ( long bankcreditextid , long variety ) throws Exception
	{
		double  allotedAmount = 0.0 ;		
		StringBuffer strSQL = new StringBuffer();
				
		strSQL.append(" SELECT sum(a.mamount * b.nexchangerate) amount ");
		strSQL.append(" FROM loan_bank_creditext_allot a,");
		if( variety == 0 )
		{
			strSQL.append(" loan_bank_creditext_mix  b ");
		}
		else
		{
			strSQL.append(" loan_bank_creditext_list  b ");
		}
		strSQL.append(" WHERE a.nbankcreditextid=b.nbankcreditextid AND a.nisvalid=1");
		strSQL.append(" and a.nbankcreditextid=? ");
		strSQL.append(" and a.nvariety=? ");
		if( variety > 0)
		{
			strSQL.append(" and b.nvariety=? ");
		}
		strSQL.append(" GROUP by a.nvariety ");	
		log4j.print( strSQL.toString() );
		try
		{
			conn = Database.getConnection();					
			pstm = conn.prepareStatement(strSQL.toString());				
			pstm.setLong( 1, bankcreditextid );
			pstm.setLong( 2, variety );	
			if( variety > 0)
			{
				pstm.setLong( 3, variety );	
			}			
			rs = pstm.executeQuery();
			if(rs.next())
			{
				allotedAmount = rs.getDouble("amount");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
	    {
	        finalize();
	    }
		log4j.print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=======" + allotedAmount);
		return allotedAmount;
	}
	
	/**
	 * 将ResultSet中的结果集转换成AssureInfo
	 * @return
	 * @throws SQLException
	 */
	public BankCreditExtInfo getDataEntityFromResultSet(BankCreditExtInfo info) throws SQLException
	{
		try
		{
			if(rs.next())
			{
				info.setId(rs.getLong("id"));
				info.setContractNo(rs.getString("sContractNo"));
				info.setYear(rs.getString("sYear"));
				info.setBankName(rs.getString("sBankName"));
				info.setCompany(rs.getString("sCompany"));
				info.setReuse(rs.getLong("nReuse"));
				info.setTerm(rs.getLong("nTerm"));
				info.setStartDate(rs.getString("dStartDate"));
				info.setEndDate(rs.getString("dEndDate"));
				info.setOperationDate(rs.getString("dOperationDate").substring(0,10));
				info.setStatus(rs.getLong("nStatus"));
				info.setIsAvlid(rs.getLong("nIsValid"));
				info.setLastModifier(rs.getLong("sLastModifier"));
				info.setLastModifyDate(rs.getString("dLastModifyDate").substring(0,10));
				info.setUseLimit(rs.getString("sUseLimit"));
				info.setRemark(rs.getString("sRemark"));
				info.setGuarangy(rs.getString("sGuarangy"));
				info.setAccessory(rs.getString("sAccessory"));
				info.formatString();
				
			}
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }			
		return info;    
	}
	
	/**
	 * 将ResultSet中的结果集转换成AssureInfo
	 * @return
	 * @throws SQLException
	 */
	public BankCreditExtMixInfo getDataEntityFromResultSet(BankCreditExtMixInfo mixInfo) throws SQLException
	{
		try
		{
			if(rs.next())
			{
				mixInfo.setBankCreditExtId(rs.getLong("nBankCreditExtId"));				
				mixInfo.setCurrencyType(rs.getLong("nCurrencyType"));
				mixInfo.setAmount(rs.getDouble("mAmount"));
				mixInfo.setExchangeRate(rs.getDouble("nExchangeRate"));
				mixInfo.setConvertRMB(rs.getDouble("nConvertRMB"));
				mixInfo.setRiskCoefficient1(rs.getLong("nRiskCoefficient1"));
				mixInfo.setRiskCoefficient2(rs.getLong("nRiskCoefficient2"));
				mixInfo.setRiskCoefficient3(rs.getLong("nRiskCoefficient3"));
				mixInfo.setRiskCoefficient4(rs.getLong("nRiskCoefficient4"));
				mixInfo.setRiskCoefficient5(rs.getLong("nRiskCoefficient5"));
				mixInfo.setRiskCoefficient6(rs.getLong("nRiskCoefficient5"));
			}
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }			
		return mixInfo;    
	}
	
	/**
	 * 将ResultSet中的结果集转换成AssureInfo
	 * @return
	 * @throws SQLException
	 */
	public BankCreditExtListInfo getDataEntityFromResultSet(BankCreditExtListInfo listInfo) throws SQLException
	{
		try
		{
			if(rs.next())
			{
				listInfo.setBankCreditExtId(rs.getLong("nBankCreditExtId"));
				listInfo.setVariety(rs.getLong("nVariety"));
				listInfo.setCurrencyType(rs.getLong("nCurrencyType"));
				listInfo.setAmount(rs.getDouble("mAmount"));
				listInfo.setExchangeRate(rs.getDouble("nExchangeRate"));
				listInfo.setConvertRMB(rs.getDouble("nConvertRMB"));
				listInfo.setRiskCoefficient(rs.getLong("nRiskCoefficient"));
			}
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }			
		return listInfo;    
	}
	
	/**
	 * 将ResultSet中的结果集转换成由BankCreditExtListInfo组成的集合Collection
	 * @return
	 * @throws SQLException
	 */
	public Collection getDataEntitiesFromResultSet() throws SQLException
	{
		Collection result = new ArrayList();
		BankCreditExtListInfo infoTemp = null;
		try
		{
			while(rs.next())
			{
				infoTemp = new BankCreditExtListInfo();
				infoTemp.setBankCreditExtId(rs.getLong("nBankCreditExtId"));
				infoTemp.setVariety(rs.getLong("nVariety"));
				infoTemp.setCurrencyType(rs.getLong("nCurrencyType"));
				infoTemp.setAmount(rs.getDouble("mAmount"));
				infoTemp.setExchangeRate(rs.getDouble("nExchangeRate"));
				infoTemp.setConvertRMB(rs.getDouble("nConvertRMB"));
				infoTemp.setRiskCoefficient(rs.getLong("nRiskCoefficient"));			
				result.add(infoTemp);
				infoTemp = null;
			} 
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }	
		return result;
	}
	
	
	/**
	 * 关闭数据库资源
	 */
	public void finalize() throws ITreasuryDAOException
	{		
		try 
         {
 			if (rs != null) 
 			{
 				rs.close();
 				rs = null;
 			}

 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}

 			if (conn != null) 
 			{
 				conn.close();
 				conn = null;
 			}
 		} catch (SQLException e) 
 		{
 			throw new ITreasuryDAOException("数据库关闭异常发生",e);			
 	    }
	}


	

}
