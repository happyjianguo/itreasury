/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.ebankPrint.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.ebankPrint.dataentity.PrintEbankApply;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

/**
 * @author liangwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintEbankApplyDao extends ITreasuryDAO
{
	public PrintEbankApplyDao()
	{
		super("ebank_printapply");
	}
	
	public PageLoader findForApplyTransInfo () throws Exception
	{
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer(); 
		StringBuffer m_sbWhere = new StringBuffer();
		
		m_sbSelect.append(" a.id, a.execute, a.transno,a.TransactionTypeID,a.ReceiveAccountNo,a.PayAccountNo,a.Abstract,a.InputUserID ");
		m_sbFrom.append(" sett_vtransaction a");
		m_sbWhere.append("a.id in (select e.nprintcontentid from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1)and a.transno in (select e.nprintcontentno from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1)");
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(
    			new AppContext(),
    			m_sbFrom.toString(),
    			m_sbSelect.toString(),
    			m_sbWhere.toString(),
    			(int) Constant.PageControl.CODE_PAGELINECOUNT,
    			"com.iss.itreasury.settlement.ebankPrint.dataentity.QueryEbankPrint",
    			null);
    	
		return pageLoader;
	}
	
	//需要区分办事处
	public PageLoader findForApplyTransInfo(long officeID, long currencyID) throws Exception
	{
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer(); 
		StringBuffer m_sbWhere = new StringBuffer();
		
		m_sbSelect.append(" a.id, a.execute, a.transno, a.TransactionTypeID, a.ReceiveAccountNo, a.PayAccountNo, a.Abstract, a.InputUserID, a.payamount transAmount ");
		m_sbFrom.append(" sett_vtransaction a ");
		m_sbWhere.append(" a.id in (select e.nprintcontentid from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1) and a.transno in (select e.nprintcontentno from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1) ");
		m_sbWhere.append(" and a.officeid = "+officeID+" and a.currencyid = "+currencyID+" ");
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(
    			new AppContext(),
    			m_sbFrom.toString(),
    			m_sbSelect.toString(),
    			m_sbWhere.toString(),
    			(int) Constant.PageControl.CODE_PAGELINECOUNT,
    			"com.iss.itreasury.settlement.ebankPrint.dataentity.QueryEbankPrint",
    			null);
    	
		return pageLoader;
	}
	
	//查找全部的申请打印记录
	public  Collection findAllprint() throws RemoteException{
		Collection coll = new ArrayList();
		
		try
		{
			StringBuffer strSQL = new StringBuffer();
			
		    strSQL.append("select * from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1");
			
			System.out.println("SQL=================="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeQuery();
			
			coll = getDataEntitiesFromResultSet(PrintEbankApply.class);
			
			finalizeDAO();
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new RemoteException();
		}
		
		return coll;
	}
	
	public Collection findeBankPrint(long transID)throws RemoteException{
		Collection coll = new ArrayList();
		
		try
		{
			StringBuffer strSQL = new StringBuffer();
			
		    strSQL.append("select e.id, e.nofficeid, e.ncurrency, e.nprintcontentid, e.nprintcontentno, e.ndeptid, e.nbillid, e.ntempid, e.nstatusid, e.ischapter, e.nclientid, e.ninputuserid, e.ninputdate, e.ntypeid, e.isebank, e.nprintid,e.stempname,e.moduleid nmoduleid from ebank_printapply e where e.nStatusID = 1 and e.IsEbank  = 1 and e.nprintcontentid = ")
		    .append(transID);
			
			System.out.println("SQL=================="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeQuery();
			  
			coll = getDataEntitiesFromResultSet(PrintEbankApply.class);
			
			finalizeDAO();
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new RemoteException();
		}
		
		return coll;
	}
	
	public Collection findeBankPrintchoose(long transID)throws RemoteException{
		Collection coll = new ArrayList();
		
		try
		{
			StringBuffer strSQL = new StringBuffer();
			
		    strSQL.append("select e.id, e.nofficeid, e.ncurrency, e.nprintcontentid, e.nprintcontentno, e.ndeptid, e.nbillid, e.ntempid, e.nstatusid, e.ischapter, e.nclientid, e.ninputuserid, e.ninputdate, e.ntypeid, e.isebank, e.nprintid,e.stempname,e.moduleid nmoduleid from ebank_printapply e where e.nStatusID = "+VOUConstant.VoucherStatus.REFUSE+" and e.IsEbank  = 1 and e.nprintcontentid = ")
		    .append(transID);
			
			System.out.println("SQL=================="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeQuery();
			  
			coll = getDataEntitiesFromResultSet(PrintEbankApply.class);
			
			finalizeDAO();
		}
		catch(Exception re)
		{
			throw new RemoteException(re.getMessage());
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new RemoteException();
		}
		
		return coll;
	}
	
	//保存网银单据打印申请表
	public void saveEbankPrint(PrintEbankApply printEbankApply,Connection conn)throws RemoteException {
	
			//Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer strSQL = null;

			try
			{
				
			   strSQL = new StringBuffer();
			   strSQL.append("insert into ebank_printapply")
			   		 .append(" (id, nofficeid, ncurrency, nprintcontentid, nprintcontentno, ndeptid, stempname, moduleid, nstatusid, ischapter, nclientid, ninputuserid, ninputdate, ntypeid, isebank) ")
			   		 .append("values (seq_ebank_printapply.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//conn = Database.getConnection();
				
			   System.out.print("--------------------->" + strSQL.toString());
				ps = conn.prepareStatement(strSQL.toString());
				
			    
				int nIndex = 1;
	            //ps.setLong(nIndex++, printEbankApply.getId());
	            ps.setLong(nIndex++, printEbankApply.getNofficeid());
	            ps.setLong(nIndex++, printEbankApply.getNcurrency());
	            ps.setLong(nIndex++, printEbankApply.getNprintcontentid());
	            ps.setString(nIndex++, printEbankApply.getNprintcontentno());
	            ps.setLong(nIndex++, printEbankApply.getNdeptid());
	            ps.setString(nIndex++, printEbankApply.getStempname());
	            ps.setLong(nIndex++, printEbankApply.getNmoduleid());
	            ps.setLong(nIndex++, printEbankApply.getNstatusid());
	            ps.setLong(nIndex++, printEbankApply.getIschapter());
	            ps.setLong(nIndex++, printEbankApply.getNclientid());
	            ps.setLong(nIndex++, printEbankApply.getNinputuserid());
	            ps.setTimestamp(nIndex++, printEbankApply.getNinputdate()); 
	            ps.setLong(nIndex++, printEbankApply.getNtypeid());
	            ps.setLong(nIndex++, printEbankApply.getIsebank());

	            ps.executeUpdate();
			}
	        catch (Exception ex)
	        {
	            throw new RemoteException(ex.getMessage());
	        }
	        finally
	        {
	            try
	            {
	            	if(rs != null){
	            		
	            		rs.close();
	            		rs = null;
	            	}
	                
	                if (ps != null)
	                {
	                    ps.close();
	                    ps = null;
	                }
	                
	            }
	            catch (Exception ex)
	            {

	            	 throw new RemoteException(ex.getMessage());
	            }
	        }
		}
	
	//修改网银单据打印申请表 add by zyyao 2008-8-3
	public void updateEbankPrintstatus(PrintEbankApply printEbankApply,Connection conn)throws RemoteException {
	
			//Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer strSQL = null;

			try
			{
				
				strSQL = new StringBuffer();
			   	strSQL.append("update ebank_printapply ")
			          .append(" set nstatusid = ?, ninputdate = ? ")
			          .append(" where nofficeid = ? and ncurrency = ? and nprintcontentid = ? and nprintcontentno = ? and ndeptid = ? and stempname = ?and moduleid = ? ")
			          .append(" and nstatusid = ? and  ischapter = ? and nclientid = ? and ninputuserid = ? and  ntypeid = ? and isebank = ? ");
				//conn = Database.getConnection();
				
			   	System.out.print("--------------------->" + strSQL.toString());
				ps = conn.prepareStatement(strSQL.toString());
				
				int nIndex = 1;
	            //ps.setLong(nIndex++, printEbankApply.getId());
				ps.setLong(nIndex++, VOUConstant.VoucherStatus.SAVE);
				ps.setTimestamp(nIndex++, printEbankApply.getNinputdate()); 
	            ps.setLong(nIndex++, printEbankApply.getNofficeid());
	            ps.setLong(nIndex++, printEbankApply.getNcurrency());
	            ps.setLong(nIndex++, printEbankApply.getNprintcontentid());
	            ps.setString(nIndex++, printEbankApply.getNprintcontentno());
	            ps.setLong(nIndex++, printEbankApply.getNdeptid());
	            ps.setString(nIndex++, printEbankApply.getStempname());
	            ps.setLong(nIndex++, printEbankApply.getNmoduleid());
	            ps.setLong(nIndex++, printEbankApply.getNstatusid());
	            ps.setLong(nIndex++, printEbankApply.getIschapter());
	            ps.setLong(nIndex++, printEbankApply.getNclientid());
	            ps.setLong(nIndex++, printEbankApply.getNinputuserid());
	            ps.setLong(nIndex++, printEbankApply.getNtypeid());
	            ps.setLong(nIndex++, printEbankApply.getIsebank());

	            ps.executeUpdate();
			}
	        catch (Exception ex)
	        {
	            throw new RemoteException(ex.getMessage());
	        }
	        finally
	        {
	            try
	            {
	            	if(rs != null){
	            		rs.close();
	            		rs = null;
	            	}
	                if (ps != null)
	                {
	                    ps.close();
	                    ps = null;
	                }
	            }
	            catch (Exception ex)
	            {
	            	 throw new RemoteException(ex.getMessage());
	            }
	        }
		}
	
	//更新网银单据打印申请表
	public void updateEbankPrint(PrintEbankApply printEbankApply,Connection conn)throws RemoteException 
	{
		//Connection conn = null;
		PreparedStatement ps = null;  
		ResultSet rs = null;
		StringBuffer strSQL = null;

			try
			{
				//conn = Database.getConnection();
				strSQL = new StringBuffer();
				strSQL.append(" update ebank_printapply ")
				      .append(" set nofficeid = ?, ")
				      .append(" ncurrency = ?, ")
				      .append(" nprintcontentid = ?, ")
				      .append(" nprintcontentno = ?, ")
				       
				      .append(" ndeptid = ?, ")
				      .append(" nbillid = ?, ")
				      .append(" ntempid = ?, ")
				      .append(" nstatusid = ?, ")
				       
				      .append(" ischapter = ?, ")
				      .append(" nclientid = ?, ")
				      .append(" ninputuserid = ?, ")
				      .append(" ninputdate = ?, ")
				       
				      .append(" ntypeid = ?, ")
				      .append(" isebank = ?, ")
				      .append(" nprintid = ?, ")
				      .append(" stempname = ?, ")
				      .append(" moduleid = ? ")
				      .append(" where id = ? ");

				ps = conn.prepareStatement(strSQL.toString());

				int nIndex = 1;

	            ps.setLong(nIndex++, printEbankApply.getNofficeid());
	            ps.setLong(nIndex++, printEbankApply.getNcurrency());
	            ps.setLong(nIndex++, printEbankApply.getNprintcontentid());
	            ps.setString(nIndex++, printEbankApply.getNprintcontentno());
	            
	            ps.setLong(nIndex++, printEbankApply.getNdeptid());
	            ps.setLong(nIndex++, printEbankApply.getNbillid());
	            ps.setLong(nIndex++, printEbankApply.getNtempid());
	            ps.setLong(nIndex++, printEbankApply.getNstatusid());
	            
	            ps.setLong(nIndex++, printEbankApply.getIschapter());
	            ps.setLong(nIndex++, printEbankApply.getNclientid());
	            ps.setLong(nIndex++, printEbankApply.getNinputuserid());
	            ps.setTimestamp(nIndex++, printEbankApply.getNinputdate());
	            
	            ps.setLong(nIndex++, printEbankApply.getNtypeid());
	            ps.setLong(nIndex++, printEbankApply.getIsebank());
	            ps.setLong(nIndex++, printEbankApply.getNprintid());
	            ps.setString(nIndex++, printEbankApply.getStempname());
	            ps.setLong(nIndex++, printEbankApply.getNmoduleid());
	            ps.setLong(nIndex++, printEbankApply.getId());
  
	            ps.executeUpdate();
			}
	        catch (Exception ex)
	        {
	            throw new RemoteException(ex.getMessage());
	        }
	        finally
	        {
	            try
	            {
	            	if(rs != null){
	            		rs.close();
	            		rs = null;
	            	}
	                if (ps != null){
	                    ps.close();
	                    ps = null;
	                }
	            }
	            catch (Exception ex)
	            {

	            	 throw new RemoteException(ex.getMessage());
	            }
	        }
		}
	
	/**
	 * 保存再打印申请信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long savePrintApplayInfo (PrintApplyInfo info,Connection conn) throws Exception
	{
		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//StringBuffer strSQL = null;
		String strSQL = "";
		long lID = -1;
		try 
		{
			strSQL = " select nvl(max(id)+1,1) oID from print_printapply";
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				lID = rs.getLong("oID");
			}
			System.out.println("-----------------新id："+lID);
			
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
			
			//update by dwj 添加接收人(网银提交的补打审批由谁接收的)
			//strSQL = " insert into print_printapply values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			strSQL = " insert into print_printapply values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//end update by dwj 
			ps = conn.prepareStatement(strSQL.toString());
			
			int index = 0;
			ps.setLong(++index , lID);
			ps.setLong(++index , info.getNOfficeID());
			ps.setLong(++index , info.getNCurrencyID());
			ps.setLong(++index , info.getNTransID());
			ps.setString(++index , info.getNTransNo());
			ps.setLong(++index , info.getNDeptID());
			ps.setLong(++index , info.getNBillID());
			ps.setLong(++index , info.getNtempid());
			ps.setLong(++index , VOUConstant.VoucherStatus.APPROVALING);
			ps.setLong(++index , info.getIschapter());
			ps.setLong(++index , info.getNclientid());
			ps.setLong(++index , info.getNInputUserId());
			ps.setTimestamp(++index , info.getNInputDate());
			ps.setLong(++index , info.getLTransTypeID());			
			ps.setString(++index , info.getStrBillName());
			ps.setLong(++index , info.getModuleId());
			//update by dwj 20110930 添加接收人(网银提交的补打审批由谁接收的)
			ps.setLong(++index , info.getNreceiveuserid());
			//end update by dwj 20110930
			ps.executeUpdate();
		}
        catch (Exception ex)
        {
            throw new RemoteException(ex.getMessage());
        }
        finally
        {
            try
            {
            	if(rs != null){
            		rs.close();
            		rs = null;
            	}
                if (ps != null){
                    ps.close();
                    ps = null;
                }
            }
            catch (Exception ex)
            {
            	 throw new RemoteException(ex.getMessage());
            }
        }
        
		return lID;
	}
}