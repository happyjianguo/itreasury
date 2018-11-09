package com.iss.itreasury.loan.assureloan.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assureloan.dataentity.AssureInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtBalanceDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe,fxzhang
 * Created on 2006-10-30
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class AssureDao extends LoanDAO
{
	public AssureDao() 
	{
		super("LOAN_ASSURE");
	}
	public AssureDao(Connection conn) 
	{
		super("LOAN_ASSURE",conn);
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
	private String[] getAssureSQL(AssureInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strTemp = new StringBuffer();

		//select
		sql[0] = "code, assureContractNo, b.sContractNo conferContractCode, assureMode, cautionerName, " +
				"warranteeName, currencyID, amount,startDate, endDate " ;
		
		//from
		sql[1] = " loan_assure a, loan_bank_creditext b";

		//where	
		/**********处理查询条件*************/		
		strTemp.append(" a.conferContractNo=b.id ");
		if(!info.getAssureContractNo().equals(""))
		{
			strTemp.append(" and a.assureContractNo LIKE '%" + info.getAssureContractNo() + "%'");
		}
		if(info.getAssureKind() != -1)
		{
			strTemp.append(" and a.assureKind=" + info.getAssureKind());
		}
		if(info.getAssureMode() != -1)
		{
			strTemp.append(" and a.assureMode=" + info.getAssureMode());			
		}		
		if(!info.getFromStartDate().equals(""))
		{
			strTemp.append(" and a.startDate>=(TO_DATE('" + info.getFromStartDate() + "','YYYY-MM-DD'))");
		}
		if(!info.getEndStartDate().equals(""))
		{
			strTemp.append(" and a.startDate<=(TO_DATE('" + info.getEndStartDate() + "','YYYY-MM-DD'))");
		}
		if(!info.getFromEndDate().equals(""))
		{
			strTemp.append(" and a.endDate>=(TO_DATE('" + info.getFromEndDate() + "','YYYY-MM-DD'))");
		}
		if(!info.getEndEndDate().equals(""))
		{
			strTemp.append(" and a.endDate<=(TO_DATE('" + info.getEndEndDate() + "','YYYY-MM-DD'))");
		}
		sql[2] = strTemp.toString();
		
		//order
		sql[3] = " ORDER BY CODE DESC";
			
		log4j.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		return sql;
	}
	
	/**
	 * 分页查询，根据条件查询担保信息
	 * @param info
	 * @return PageLoader对象
	 * @throws Exception
	 */
	public PageLoader findByCondition(AssureInfo info) throws Exception
	{
		String[] sql = this.getAssureSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.assureloan.dataentity.AssureInfo", null);	
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	/**
	 * 根据内部流水号查询担保信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public AssureInfo findByCode(AssureInfo info) throws Exception
	{
		AssureInfo assureInfo = null;
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("SELECT a.id, code, assureContractNo, assureKind, assureMode,assureType, conferContractNo, b.sContractNo conferContractCode, beneficiaryName, ");
		strSQL.append("cautionerName, warranteeName, assureWorth, amount, currencyID, exchangeRate, ");
		strSQL.append("convertRMB, startDate, endDate, remark  ");		
		strSQL.append("FROM  loan_assure a, loan_bank_creditext b ");
		strSQL.append("WHERE  a.conferContractNo=b.id and a.code=? ");
		log4j.print(strSQL.toString());
		try
        {          
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setString(1,info.getCode());
            rs = pstm.executeQuery();
            assureInfo = this.getDataEntityFromResultSet();
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
        return assureInfo;
		
	}
	
	
	/**
	 * 根据用户编号查询用户名称
	 * @param codes 
	 * @return
	 * @throws IException
	 */
	public List getClient(String[] codes) throws Exception
	{
		List clientInfos = new ArrayList();
		Map clientInfo = null;
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("SELECT sCode,sName FROM client where sCode in(");
		for(int i=0;i<codes.length;i++)
		{
			if(i == codes.length-1)
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
            pstm = conn.prepareStatement(strSQL.toString());
            for(int i=0;i<codes.length;i++)
            {
            	log4j.print("id["+i+"] = "+ codes[i]);
            	pstm.setString(i+1,codes[i]);
            }
            rs = pstm.executeQuery();
            while(rs.next())
            {
            	clientInfo = new HashMap();
            	clientInfo.put("code",rs.getString("sCode"));
            	clientInfo.put("name",rs.getString("sName"));
            	clientInfos.add(clientInfo);
            	clientInfo = null;
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
		return clientInfos;
	}
	
	/**
	 * 新增，将info里的信息insert到表loan_Assure里
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long add(AssureInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO loan_assure ");
		strSQL.append("(id, code, assureContractNo, assureKind, assureMode,assureType, conferContractNo, beneficiaryName, ");
		strSQL.append("cautionerName, warranteeName, assureWorth, amount, currencyID, exchangeRate, ");
		strSQL.append("convertRMB, startDate, endDate, remark  ) ");		
		strSQL.append(" VALUES((SELECT nvl(MAX(id)+1,1) ID FROM loan_assure),?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
		strSQL.append("(TO_DATE(?,'YYYY-MM-DD')),(TO_DATE(?,'YYYY-MM-DD')),?)" );			
		log4j.print( strSQL.toString());	
		try
        {          
			
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());          
            pstm.setString(1,info.getCode());            
            pstm.setString(2,info.getAssureContractNo());           
            pstm.setLong(3,info.getAssureKind());          
            pstm.setLong(4,info.getAssureMode());   
            pstm.setString(5,info.getAssureType());
            pstm.setLong(6,info.getConferContractNo());         
            pstm.setString(7,info.getBeneficiaryName());
            pstm.setString(8,info.getCautionerName());
            pstm.setString(9,info.getWarranteeName()); 
            pstm.setDouble(10,info.getAssureWorth());  
            pstm.setDouble(11,info.getAmount());      
            pstm.setLong(12,info.getCurrencyID());         
            pstm.setDouble(13,info.getExchangeRate());         
            pstm.setDouble(14,info.getConvertRMB());                          
            pstm.setString(15,info.getStartDate());         
            pstm.setString(16,info.getEndDate());                  
            pstm.setString(17,info.getRemark());
            
            pstm.executeUpdate();
						
			lret = 1;
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
		return lret;
	}
	
	/**
	 * 修改，将info里的信息update到表loan_Assure里
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long modify(AssureInfo info) throws Exception
	{
		long lret = -1;	
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_assure SET ");
		strSQL.append("code=?, assureContractNo=?, assureKind=?, assureMode=?, assureType=?,conferContractNo=?, beneficiaryName=?, ");
		strSQL.append("cautionerName=?, warranteeName=?, assureWorth=?, amount=?, currencyID=?, exchangeRate=?, ");
		strSQL.append("convertRMB=?, startDate=(TO_DATE(?,'YYYY-MM-DD')), endDate=(TO_DATE(?,'YYYY-MM-DD')), remark=?  ");		
		strSQL.append("WHERE id=?");		
		log4j.print( strSQL.toString());	
		try
        {          
			
			
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString()); 
            pstm.setString(1,info.getCode());          
            pstm.setString(2,info.getAssureContractNo());               
            pstm.setLong(3,info.getAssureKind());                  
            pstm.setLong(4,info.getAssureMode());               
            pstm.setString(5,info.getAssureType());
            pstm.setLong(6,info.getConferContractNo());         
            pstm.setString(7,info.getBeneficiaryName());
            pstm.setString(8,info.getCautionerName());
            pstm.setString(9,info.getWarranteeName()); 
            pstm.setDouble(10,info.getAssureWorth());  
            pstm.setDouble(11,info.getAmount());      
            pstm.setLong(12,info.getCurrencyID());         
            pstm.setDouble(13,info.getExchangeRate());         
            pstm.setDouble(14,info.getConvertRMB());                          
            pstm.setString(15,info.getStartDate());         
            pstm.setString(16,info.getEndDate());                  
            pstm.setString(17,info.getRemark());         
            pstm.setLong(18,info.getId());
            
            pstm.executeUpdate();
            
            lret = 1;
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
		return lret;
	}
	
	/**
	 * 判断该担保有没有被别的地方引用 2006-12-31
	 * @param info
	 * @return   
	 * @throws Exception 
	 */
	public boolean isUsed(AssureInfo info) throws Exception 
	{
		boolean  yes = false;
		String  strSQL0 = null;
		String  strSQL1 = "select count(*) from loan_bankloan where loantype=1 and statusid<>0   and confercontractno=? and instr(?, loanclient,1,1)>0";
		String  strSQL2 = "select count(*) from loan_bankloan where loantype=2 and statusid<>0   and confercontractno=? and instr(?, loanclient,1,1)>0";
		String  strSQL3 = "select count(*) from loan_letter_credit  where nstatus<>0 and nbankcreditextid=? and instr(?, sapplycompanycode,1,1)>0 ";
		String  strSQL4 = "select count(*) from loan_letter_guarantee where  nstatus<>0 and nbankcreditextid=?  and instr(?, sapplycompanycode,1,1)>0 ";
		String  strSQL5 = "select count(*) from loan_creditprove where  statusid<>0 and confercontractno=? and instr(?, applyclient,1,1)>0 ";
		String  strSQL6 = "select count(*) from loan_acceptbill where statusid<>0 and confercontractno=? and instr(?, billclient,1,1)>0 ";
		try
        {	
            //获得数据库连接，初使化PreparedStatement
            conn = Database.getConnection();
            String[] assureTypes = null;
            if(info.getAssureType0() != null)
            {
            	assureTypes = ( info.getAssureType0()).split(",");
            }                
            for(int i = 0; i < assureTypes.length; i++ )
            {
            	System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  " + assureTypes[i]);
            	if(!assureTypes[i].equals(""))
	            {
	            	switch ((Integer.valueOf(assureTypes[i])).intValue())
	    			{   			
	    					//短期贷款	
	    				case (int)LOANConstant.BankCreditVariety.SHORTTERMLOAN :               
	    					strSQL0 = strSQL1 ;   					
	    					break;
	    					//中长期贷款
	    				case (int)LOANConstant.BankCreditVariety.LONGTERMLOAN :
	    					strSQL0 = strSQL2 ;  
	    					break;
	    					//信用证
	    				case (int)LOANConstant.BankCreditVariety.LETTEROFCREDIT :
	    					strSQL0 = strSQL3 ; 
	    					break;
	    					//保函
	    				case (int)LOANConstant.BankCreditVariety.LETTEROFIGUARANTEE :
	    					strSQL0 = strSQL4 ; 
	    					break;
	    					//信贷证明
	    				case (int)LOANConstant.BankCreditVariety.PROVEOFCREDIT :
	    					strSQL0 = strSQL5 ; 
	    					break;
	    					//承兑汇票
	    				case (int)LOANConstant.BankCreditVariety.ACCEPTBILL :
	    					strSQL0 = strSQL6 ; 
	    					break;
	    			}
	            	System.out.println(strSQL0);
	            	pstm = null;
	            	rs = null;
	            	pstm = conn.prepareStatement(strSQL0);
	            	pstm.setLong(1, info.getConferContractNo0());
	            	pstm.setString(2,info.getWarranteeName0());
	                rs = pstm.executeQuery();         
	                rs.next();
	                System.out.println("************************************************        " + rs.getInt(1));
	                if(rs.getInt(1) > 0)
	                {
	                	yes = true;
	                	break;
	                }
	            }
            }
            
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
           finalize();
        }      
		return yes;
	}
	
	/**
	 * 删除，将info里的信息update到表loan_Assure里
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long delete(AssureInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("DELETE FROM loan_assure WHERE id=?");
		log4j.print( strSQL.toString());
		log4j.print("info.id ="+info.getId());
		try
        {
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());          
            pstm.setLong(1,info.getId());  
            pstm.executeUpdate();
            lret = 1;
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
		return lret;
	}
	
	/**
	 * 生成内部流水号。
	 * 内部流水号为14位：办事处简码 + AS + 年 + 月 + 流水号
	 * @return
	 * @throws Exception
	 */
	public String getNextCode() throws Exception 
	{
		StringBuffer strSQL = new StringBuffer();           //执行的SQL语句
		strSQL.append(" SELECT code FROM loan_assure ORDER BY code DESC");     
		String newCode = "";                //要返回的内部流水号
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(Env.getSystemDateTime());      //取得当前时间
		StringBuffer strTemp = new StringBuffer();               
		strTemp.append("BJAS");  
		strTemp.append(String.valueOf(calendar.get(Calendar.YEAR)));
		strTemp.append(String.valueOf(calendar.get(Calendar.MONTH)+1));		

		try
		{
			conn = Database.getConnection();
			pstm = conn.prepareStatement(strSQL.toString());
			rs = pstm.executeQuery();		
			if(rs.next())
			{
				String scode = rs.getString("code");	
				scode = scode.substring(strTemp.length(),scode.length());			
				if(scode != null && !scode.equals(""))
				{
					int iTemp = Integer.valueOf(scode).intValue();
					newCode = String.valueOf(iTemp + 1);	
					while(newCode.length() < 4)
					{
						newCode = "0" + newCode;
					}
				}
			}
			else
			{
				newCode = "0001";
			}
			strTemp.append(newCode);
			newCode = strTemp.toString();	
			log4j.print("newCode = " + newCode);
			finalize();
	
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalize();
		}
		return newCode;
	}

	
	/**
	 * 将ResultSet中的结果集转换成AssureInfo
	 * @return
	 * @throws SQLException
	 */
	public AssureInfo getDataEntityFromResultSet() throws SQLException
	{
		AssureInfo assureInfo = null;
		try
		{
			if(rs.next())
			{
				assureInfo = new AssureInfo();
				assureInfo.setId(rs.getLong("id"));
				assureInfo.setCode(rs.getString("code"));
				assureInfo.setAssureContractNo(rs.getString("assureContractNo"));
				assureInfo.setAssureKind(rs.getLong("assureKind"));
				assureInfo.setAssureMode(rs.getLong("assureMode"));
				assureInfo.setAssureType(rs.getString("assureType"));
				assureInfo.setConferContractNo(rs.getLong("conferContractNo"));
				assureInfo.setConferContractCode(rs.getString("conferContractCode"));
				assureInfo.setBeneficiaryName(rs.getString("beneficiaryName"));
				assureInfo.setCautionerName(rs.getString("cautionerName"));
				assureInfo.setWarranteeName(rs.getString("warranteeName"));				
				assureInfo.setAssureWorth(rs.getDouble("assureWorth"));
				assureInfo.setAmount(rs.getDouble("amount"));
				assureInfo.setCurrencyID(rs.getLong("currencyID"));
				assureInfo.setExchangeRate(rs.getDouble("exchangeRate"));
				assureInfo.setConvertRMB(rs.getDouble("convertRMB"));				          	
				assureInfo.setStartDate(rs.getString("startDate"));
				assureInfo.setEndDate(rs.getString("endDate"));
				assureInfo.setRemark(rs.getString("remark"));		
				assureInfo.formatString();
			}
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }			
		return assureInfo;    
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
