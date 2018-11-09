package com.iss.itreasury.loan.acceptbill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.iss.system.dao.PageLoader;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.acceptbill.dataentity.AcceptBillInfo;
import com.iss.itreasury.loan.base.LoanDAO;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author fxzhang
 *
 */
public class AcceptBillDao extends LoanDAO
{
	
	public AcceptBillDao() 
	{
		super("LOAN_ACCEPTBILL");
	}
	public AcceptBillDao(Connection conn) 
	{
		super("LOAN_ACCEPTBILL",conn);
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
	private String[] getAcceptBillSQL(AcceptBillInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strTemp = new StringBuffer();

		//select
		sql[0] = " code,billClient,b.sName billClientName,billID,conferContractNo,c.sContractNo conferContractCode,certificateBank,mCurrencyID,money,startDate,endDate,statusID ";
			
		//from
		sql[1] = " loan_acceptbill a, client b, loan_bank_creditext c";

		//where	
		/**********处理查询条件*************/
		strTemp.append(" a.billClient=b.sCode and a.conferContractNo=c.id ");
		strTemp.append(" and a.statusID>0 and a.officeId=" + info.getOfficeId());
		if(!info.getContractNo().equals(""))
		{
			strTemp.append(" and a.contractNo LIKE '%" + info.getContractNo() + "%'");
		}
		if(info.getConferContractNo() != -1)
		{
			strTemp.append(" and a.conferContractNo= " + info.getConferContractNo());
		}
		if(!info.getCertificateBank().equals(""))
		{
			strTemp.append(" and a.certificateBank LIKE '%" + info.getCertificateBank() + "%'");			
		}
		if(info.getStatusID() != -1)           
		{
			strTemp.append(" and a.statusID=" + info.getStatusID());
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
	 * 分页查询，根据条件查询承兑汇票
	 * @param info
	 * @return PageLoader对象
	 * @throws Exception
	 */
	public PageLoader findByCondition(AcceptBillInfo info) throws Exception
	{
		String[] sql = this.getAcceptBillSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.acceptbill.dataentity.AcceptBillInfo", null);	
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	
	/**
	 * 根据内部流水号查询承兑汇票
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public AcceptBillInfo findByCode(AcceptBillInfo info) throws Exception
	{
		AcceptBillInfo acceptBillInfo = null;
		StringBuffer strSQL = new StringBuffer();		
		strSQL.append("SELECT a.id, code, billClient, b.sName billClientName, billID, contractNo, projectName, outTicketDate, ");
		strSQL.append("money, mCurrencyID, exchangeRate, convertRMB, conferContractNo, c.sContractNo conferContractCode, certificateBank, ");
		strSQL.append("payee, payeeAccountID, startDate, endDate, statusID, remark, officeId ");
		strSQL.append("FROM loan_acceptbill a, client b, loan_bank_creditext c ");
		strSQL.append("WHERE a.billClient=b.sCode and a.conferContractNo=c.id and a.code=? and a.statusID>0");
		log4j.print(strSQL.toString());
		try
        {          
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setString(1,info.getCode());
            rs = pstm.executeQuery();
            acceptBillInfo = this.getDataEntityFromResultSet();
            finalize();
        } 
        catch (ITreasuryDAOException e) 
        {
        	e.printStackTrace();
        	throw new ITreasuryDAOException("根据内部流水号查询承兑汇票出错",e);
        }
        finally
        {
           finalize();
        }
        return acceptBillInfo;
		
	}
	/**
	 * 新增承兑汇票
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws Exception  
	 */		
	public long add(AcceptBillInfo info) throws Exception
	{		
		long lret = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO loan_acceptbill ");
		strSQL.append("(id, code, billClient, billID, contractNo, projectName, outTicketDate, ");
		strSQL.append("money, mCurrencyID, exchangeRate, convertRMB, conferContractNo, certificateBank, ");
		strSQL.append("payee, payeeAccountID, startDate, endDate, statusID, remark, officeId) ");		
		strSQL.append(" VALUES((SELECT nvl(MAX(id)+1,1) ID FROM loan_acceptbill),?,?,?,?,?,(TO_DATE(?,'YYYY-MM-DD')),");
		strSQL.append("?,?,?,?,?,?,?,?,(TO_DATE(?,'YYYY-MM-DD')),(TO_DATE(?,'YYYY-MM-DD')),?,?,?)" );			
		log4j.print( strSQL.toString());	
		try
        {          
			
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());          
            pstm.setString(1,info.getCode());            
            pstm.setString(2,info.getBillClient());           
            pstm.setString(3,info.getBillID());          
            pstm.setString(4,info.getContractNo());           
            pstm.setString(5,info.getProjectName());         
            pstm.setString(6,info.getOutTicketDate());         
            pstm.setDouble(7,info.getMoney());      
            pstm.setLong(8,info.getMCurrencyID());         
            pstm.setDouble(9,info.getExchangeRate());         
            pstm.setDouble(10,info.getConvertRMB());          
            pstm.setLong(11,info.getConferContractNo());          
            pstm.setString(12,info.getCertificateBank());           
            pstm.setString(13,info.getPayee());        
            pstm.setString(14,info.getPayeeAccountID());            
            pstm.setString(15,info.getStartDate());         
            pstm.setString(16,info.getEndDate());          
            pstm.setLong(17,info.getStatusID());           
            pstm.setString(18,info.getRemark());
            pstm.setLong(19,info.getOfficeId());
            
            pstm.executeUpdate();
						
			lret = 1;
            //end DAO
            finalize();
        } 
        catch (ITreasuryDAOException e) 
        {
        	e.printStackTrace();   	
        	throw new ITreasuryDAOException("新增承兑汇票出错",e);
        }
        finally
        {
           finalize();
        }
		return lret;
	}
	
	/**
	 * 修改承兑汇票信息
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws Exception 
	 */
	public long modify(AcceptBillInfo info) throws Exception
	{
		long lret = -1;	
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_acceptbill SET ");
		strSQL.append("code=?, billClient=?, billID=?, contractNo=?, projectName=?, outTicketDate=(TO_DATE(?,'YYYY-MM-DD')), ");
		strSQL.append("money=?, mCurrencyID=?, exchangeRate=?, convertRMB=?, conferContractNo=?, certificateBank=?, payee=?, ");
		strSQL.append("payeeAccountID=?, startDate=(TO_DATE(?,'YYYY-MM-DD')), endDate=(TO_DATE(?,'YYYY-MM-DD')), statusID=?, remark=?, officeId=? ");		
		strSQL.append("WHERE id=?");		
		log4j.print( strSQL.toString());	
		try
        {          
			
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());          
            pstm.setString(1,info.getCode());            
            pstm.setString(2,info.getBillClient());           
            pstm.setString(3,info.getBillID());          
            pstm.setString(4,info.getContractNo());           
            pstm.setString(5,info.getProjectName());         
            pstm.setString(6,info.getOutTicketDate());         
            pstm.setDouble(7,info.getMoney());      
            pstm.setLong(8,info.getMCurrencyID());         
            pstm.setDouble(9,info.getExchangeRate());         
            pstm.setDouble(10,info.getConvertRMB());          
            pstm.setLong(11,info.getConferContractNo());          
            pstm.setString(12,info.getCertificateBank());           
            pstm.setString(13,info.getPayee());        
            pstm.setString(14,info.getPayeeAccountID());            
            pstm.setString(15,info.getStartDate());         
            pstm.setString(16,info.getEndDate());          
            pstm.setLong(17,info.getStatusID());           
            pstm.setString(18,info.getRemark());
            pstm.setLong(19,info.getOfficeId());
            pstm.setLong(20,info.getId());
            pstm.executeUpdate();
            
            lret = 1;
            //end DAO
            finalize();
        } 
        catch (ITreasuryDAOException e) 
        {
        	e.printStackTrace();
        	throw new ITreasuryDAOException("修改承兑汇票出错",e);
        }
        finally
        {
           finalize();
        }
		return lret;
	}
	
	/**
	 * 删除承兑汇票
	 * @param info
	 * @return  返回成功与否，成功 1  失败  -1
	 * @throws Exception 
	 */
	public long delete(AcceptBillInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE loan_acceptbill SET statusID=0 WHERE id=?");
		log4j.print( strSQL.toString());
		log4j.print("info.id ="+info.getId());
		try
        {
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());          
            pstm.setLong(1,info.getId());  
            pstm.executeUpdate();
            lret = 1;
            //end DAO
            finalize();
        } 
        catch (ITreasuryDAOException e) 
        {
        	e.printStackTrace();
        	throw new ITreasuryDAOException("删除承兑汇票出错",e);
        }
        finally
        {
           finalize();
        }
		return lret;
	}
	
	/**
	 * 生成内部流水号。
	 * 内部流水号为14位：办事处简码 + CD + 年 + 月 + 流水号
	 * @return
	 * @throws Exception
	 */
	public String getNextCode() throws Exception 
	{
		StringBuffer strSQL = new StringBuffer();           //执行的SQL语句
		strSQL.append(" SELECT code FROM loan_acceptbill ORDER BY code DESC");     
		String newCode = "";                //要返回的内部流水号
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(Env.getSystemDateTime());      //取得当前时间
		StringBuffer strTemp = new StringBuffer();               
		strTemp.append("BJCD");  
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

	/**
	 * 将ResultSet中的结果集转换成由AcceptBillInfo组成的集合Collection
	 * @return
	 * @throws SQLException
	 */
	public Collection getDataEntitiesFromResultSet() throws SQLException
	{
		Collection result = new ArrayList();
		AcceptBillInfo infoTemp = null;
		try
		{
			while(rs.next())
			{
				infoTemp = new AcceptBillInfo();
				infoTemp.setCode(rs.getString("code"));
				infoTemp.setBillClient(rs.getString("billClient"));
				infoTemp.setBillClientName(rs.getString("billClientName"));
				infoTemp.setBillID(rs.getString("billID"));
				infoTemp.setConferContractNo(rs.getLong("conferContractNo"));
				infoTemp.setConferContractCode(rs.getString("conferContractCode"));
				infoTemp.setCertificateBank(rs.getString("certificateBank"));
				infoTemp.setMCurrencyID(rs.getLong("mCurrencyID"));
				infoTemp.setMoney(rs.getDouble("money"));
				infoTemp.setStartDate((rs.getString("startDate")).substring(0,10));
				infoTemp.setEndDate((rs.getString("endDate")).substring(0,10));
				infoTemp.setStatusID(rs.getLong("statusID"));
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
	 * 将ResultSet中的结果集转换成AcceptBillInfo
	 * @return
	 * @throws SQLException
	 */
	public AcceptBillInfo getDataEntityFromResultSet() throws SQLException
	{
		AcceptBillInfo acceptBillInfo = null;
		try
		{
			if(rs.next())
			{
	        	acceptBillInfo = new AcceptBillInfo();
	        	acceptBillInfo.setId(rs.getLong("id"));
	        	acceptBillInfo.setCode(rs.getString("code"));
	    		acceptBillInfo.setBillClient(rs.getString("billClient"));
	    		acceptBillInfo.setBillClientName(rs.getString("billClientName"));
	    		acceptBillInfo.setBillID(rs.getString("billID"));
	        	acceptBillInfo.setContractNo(rs.getString("contractNo"));
	    		acceptBillInfo.setProjectName(rs.getString("projectName"));
	    		acceptBillInfo.setOutTicketDate((rs.getString("outTicketDate")).substring(0,10));
	    		acceptBillInfo.setMoney(rs.getDouble("money"));
	    		acceptBillInfo.setMCurrencyID(rs.getLong("mCurrencyID"));
	    		acceptBillInfo.setExchangeRate(rs.getDouble("exchangeRate"));
	    		acceptBillInfo.setConvertRMB(rs.getDouble("convertRMB"));
	    		acceptBillInfo.setConvertRMB0(rs.getDouble("convertRMB"));
	    		acceptBillInfo.setConferContractNo(rs.getLong("conferContractNo"));
	    		acceptBillInfo.setConferContractCode(rs.getString("conferContractCode"));
	        	acceptBillInfo.setCertificateBank(rs.getString("certificateBank"));
	    		acceptBillInfo.setPayee(rs.getString("payee"));
	    		acceptBillInfo.setPayeeAccountID(rs.getString("payeeAccountID"));           	
	        	acceptBillInfo.setStartDate((rs.getString("startDate")).substring(0,10));
	        	acceptBillInfo.setEndDate((rs.getString("endDate")).substring(0,10));
	        	acceptBillInfo.setStatusID(rs.getLong("statusID"));
	        	acceptBillInfo.setRemark(rs.getString("remark"));	
	        	
	        	acceptBillInfo.formatString();
	        	
			}
		}catch (SQLException e) 
 		{
 			throw e;			
 	    }			
		return acceptBillInfo;    
	}
}
