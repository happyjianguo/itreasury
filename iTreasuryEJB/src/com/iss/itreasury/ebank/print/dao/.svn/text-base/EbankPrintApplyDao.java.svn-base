/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.print.dao;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.print.dataentity.PrintrecordInfo;
import com.iss.itreasury.ebank.print.dataentity.QueryPrintInfo;
import com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.evoucher.setting.dataentity.BillrelationSetInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EbankPrintApplyDao extends ITreasuryDAO
{
	public EbankPrintApplyDao()
	{
		super("print_printapply"); 
	}
	
	public Collection findAllprint(PrintrecordInfo prinfo)throws RemoteException,Exception
	{
		Collection coll = new ArrayList();
		
		try
		{

			StringBuffer strSQL = new StringBuffer();
			
			strSQL.append(" select aa.*,bb.nprintnum printnum from ( ");
			strSQL.append(" select 1 id,a.id setid,a.sname setname,a.nbillsegment billsegment,b.id templateid,b.sdescription templatename,b.ncoupletno templateno, ");
			strSQL.append(" c.id relationid,c.ntransactiontypeid relationtypeid,c.ndeptid relationdeptid,c.nmaxprint relationmax,cc.countnum ");
			strSQL.append(" from print_billsetting a,print_billtemplate b,print_billrelation c, ");
			strSQL.append(" ( select count(setid) countnum,setid from ( ");
			strSQL.append(" select 1 id,a.id setid,a.sname setname,a.nbillsegment billsegment,b.id templateid,b.sdescription templatename,b.ncoupletno templateno,  c.id relationid,c.ntransactiontypeid relationtypeid,c.ndeptid relationdeptid,c.nmaxprint relationmax ");
			strSQL.append(" from print_billsetting a,print_billtemplate b,print_billrelation c ");
			strSQL.append(" where c.ntempid=b.id and b.nbillid=a.id  and c.ndeptid="+ prinfo.getNdeptid() +" and nTransactionTypeId= "+ prinfo.getNtransactiontypeid() +"  and a.nofficeid = 1 and a.ncurrency = 1 and a.nstatusid = 1  and c.nofficeid= 1 and c.ncurrency = 1 ");
			strSQL.append(" ) group by setid ");
			strSQL.append(" ) cc ");
			strSQL.append(" where cc.setid = c.nbillid  and c.ntempid=b.id and b.nbillid=a.id ");
			strSQL.append(" and c.ndeptid="+ prinfo.getNdeptid() +" and nTransactionTypeId= "+ prinfo.getNtransactiontypeid() +" ");
			strSQL.append(" and a.nofficeid = "+ prinfo.getNofficeid() +" and a.ncurrency = "+ prinfo.getNcurrency() +" and a.nstatusid = 1 ");
			strSQL.append(" and c.nofficeid= "+ prinfo.getNofficeid() +" and c.ncurrency = "+ prinfo.getNcurrency() +" ) ");
			strSQL.append(" aa,print_printrecord bb ");
			//strSQL.append(" where 1=1 and aa.relationdeptid = bb.ndeptid(+) and aa.setid = bb.nbillid(+) and aa.templateid = bb.ntempid(+) ");
			//strSQL.append(" and bb.ndeptid(+)="+prinfo.getNdeptid()+" and bb.nTransactionTypeId(+) = "+prinfo.getNtransactiontypeid()+" ");
			//strSQL.append(" and bb.nofficeid(+) = "+prinfo.getNofficeid()+" and bb.ncurrency(+) = "+prinfo.getNcurrency()+" and bb.nPrintContentId(+) = "+prinfo.getNprintcontentid()+" ");
			
			strSQL.append(" where 1=1 and aa.relationdeptid = bb.ndeptid and aa.setid = bb.nbillid and aa.templateid = bb.ntempid ");
			strSQL.append(" and bb.ndeptid="+prinfo.getNdeptid()+" and bb.nTransactionTypeId = "+prinfo.getNtransactiontypeid()+" ");
			strSQL.append(" and bb.nofficeid = "+prinfo.getNofficeid()+" and bb.ncurrency = "+prinfo.getNcurrency()+" and bb.nPrintContentId = "+prinfo.getNprintcontentid()+" ");
			
			strSQL.append(" order by setid,templateid,templateno ");
			
			System.out.println("SQL====="+strSQL.toString());
			
			initDAO();
			
			prepareStatement(strSQL.toString());
			executeQuery();
			
			coll = getDataEntitiesFromResultSet(QueryPrintInfo.class);
			
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
	
	//ADDBY ZYYAO 2007-8-2
	//boxu update 2007-8-15
	public Collection getPrintDetailByTransID(long lTransID, long officeID, long currencyID) throws Exception
	{
		Vector vector = new Vector();
		PrintApplyInfo printApplyInfo = null;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL =  " select a.transno, a.transactiontypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , c.nprintnum , c.stempname , b.sbilltypename ";
			strSQL += " from SETT_VTRANSACTION a, print_billrelation b, print_printrecord c ";
			strSQL += " where a.transactiontypeid = b.ntransactiontypeid and a.id = c.nprintcontentid ";
			strSQL += " and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ";
			strSQL += " and a.id = " + lTransID;
			
			strSQL += " and c.nprintnum >= b.nmaxprint ";
			
			//加入查询条件 打印部门为"网上客户" 和 办事处ID 和币种ID
			strSQL += " and c.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" and b.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" ";
			strSQL += " and b.nofficeid = "+officeID+" and b.ncurrency = "+currencyID+" ";
			strSQL += " and c.nofficeid = "+officeID+" and c.ncurrency = "+currencyID+" ";
			strSQL += " order by b.sbilltypename desc ";
			
			System.out.println("strSQL===="+strSQL);
			
			transPS = transConn.prepareStatement(strSQL); 
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				printApplyInfo = new PrintApplyInfo();
				
				printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				//printApplyInfo.setNBillID(transRS.getLong("nbillid"));
				//printApplyInfo.setNtempid(transRS.getLong("ntempid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				//printApplyInfo.setLCoupletNo(transRS.getLong("ncoupletno"));
				
				vector.add(printApplyInfo);
			}
			
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
		return vector;
	}
	
	//结算打印接收查看申请单据明细
	public Collection getPrintDetailByTransIDTWO(long lTransID, long officeID, long currencyID) throws Exception
	{
		Vector vector = new Vector();
		PrintApplyInfo printApplyInfo = null;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL =  " select distinct a.transno, a.transactiontypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , c.nprintnum , c.stempname , b.sbilltypename ";
			strSQL += " from SETT_VTRANSACTION a, print_billrelation b, print_printrecord c ,ebank_printapply f ";
			strSQL += " where a.transactiontypeid = b.ntransactiontypeid and a.id = c.nprintcontentid ";
			strSQL += " and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ";
			strSQL += " and a.id = " + lTransID;
			strSQL += " and c.ndeptid = f.ndeptid and c.nofficeid = f.nofficeid and c.ncurrency=f.ncurrency and c.stempname = f.stempname and c.nmoduleid = f.moduleid and c.ntransactiontypeid = f.ntypeid ";
			strSQL += " and f.nstatusid = "+VOUConstant.VoucherStatus.SAVE+" ";
			strSQL += " and c.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" and b.ndeptid = "+VOUConstant.PrintSection.EBANKCUSTOMER+" ";
			strSQL += " and b.nofficeid = "+officeID+" and b.ncurrency = "+currencyID+" ";
			strSQL += " and c.nofficeid = "+officeID+" and c.ncurrency = "+currencyID+" ";
			strSQL += " order by b.sbilltypename desc ";
			
			System.out.println("strSQL===="+strSQL);
			
			transPS = transConn.prepareStatement(strSQL); 
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				printApplyInfo = new PrintApplyInfo();
				
				printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				//printApplyInfo.setNBillID(transRS.getLong("nbillid"));
				//printApplyInfo.setNtempid(transRS.getLong("ntempid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				//printApplyInfo.setLCoupletNo(transRS.getLong("ncoupletno"));
				
				vector.add(printApplyInfo);
			}
			
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
		return vector;
	}
	
	public Collection getPrintOptionsByTransID (String TransIDs, long lDeptID, long lCurrencyID, long lOfficeID, long lModuleID) throws Exception 
    {
    	Vector vector = new Vector();
    	String strSQL = ""; 
    	BillrelationSetInfo info = null;
    	
    	try{
			initDAO();
			
			if (lModuleID == Constant.ModuleType.SETTLEMENT)
			{
				strSQL = "select a.* ";
				strSQL += " from print_billrelation a ";
				strSQL += " where a.ntransactiontypeid= "+TransIDs+"";
				strSQL += " and a.nofficeid=" + lOfficeID;
				strSQL += " and a.ncurrency=" + lCurrencyID;
				strSQL += " and a.ndeptid=" + lDeptID;
				strSQL += " order by a.stempname ";
			}
			
			log.print("查询打印选项sql："+strSQL);
			System.out.println("查询打印选项sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new BillrelationSetInfo();
				info.setId(transRS.getLong("id"));
				info.setRelationdeptid(transRS.getLong("ndeptid"));
				info.setRelationmax(transRS.getLong("nmaxprint"));
				//info.setSetid(transRS.getLong("nbillid"));
				info.setSetname(transRS.getString("sbilltypename"));
				//info.setTemplateid(transRS.getLong("ntempid"));
				info.setTemplatename(transRS.getString("stempName"));
				//info.setTemplateno(transRS.getLong("ncoupletno"));				
				info.setModuleid(transRS.getLong("nmoduleid"));
				
				vector.add(info);
			}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	return vector;
    }

	//addby zyyao 批量打印 2007-7-19
    public Collection getPrintTemplateContentmany (String strTransNos,String transIDs , int deptID, long lCurrencyID , long lOfficeID,long lModuleID)throws Exception 
    {
    	Collection resultPrintOption = new ArrayList();
    	String strSQL="";
    	BillrelationSetInfo info= null;
    	int index = -1;
    	//int EIndex = -1;
    	int num = 1;
    	try {
    		// 取得交易id数组大小
    		index = transIDs.indexOf(",");
    		while (index >= 0) {
    			num ++;
	    		index = transIDs.indexOf(",",index+1);
	    	}
    		
    		// 将交易id转换为数组集合
    		long[] lTransIDs = new long[num];
    		String[] TransNos = new String[num];
    		int i = 0;
    		index = transIDs.indexOf(",");
    		while(index >= 0) {
    			lTransIDs[i++] = Long.valueOf(transIDs.substring(0,index)).longValue();
    			transIDs = transIDs.substring(index+1);
    			index = transIDs.indexOf(","); 
    		}
    		lTransIDs[lTransIDs.length-1] = Long.valueOf(transIDs).longValue();
    		
    		index = -1;
    		i = 0;
    		index = strTransNos.indexOf(",");
    		while(index >= 0)
    		{
    			TransNos[i++] = strTransNos.substring(0,index);
    			strTransNos = strTransNos.substring(index+1);
    			index = strTransNos.indexOf(",");
    		}
    		TransNos[TransNos.length-1] = strTransNos;
    		
    		// 将所选交易、模版类型一一对应得到所以打印的内容
    		// 如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		this.initDAO();
    		for(int k=0;k<lTransIDs.length;k++)
    		{   			
    			if (lModuleID == Constant.ModuleType.SETTLEMENT)
    			{
    				strSQL = "select b.* ";
    				strSQL += " from print_billrelation a,SETT_VTRANSACTION d ,print_manybillrelation b ";  
    				strSQL += " where a.ntransactiontypeid=d.transactiontypeid and b.ntransactiontypeid=d.transactiontypeid ";
    				strSQL += " and a.stempname = b.stempname ";
    				strSQL += " and d.id in (" + lTransIDs[k] + ")";
    				strSQL += " and a.nofficeid=" + lOfficeID;
    				strSQL += " and a.ncurrency=" + lCurrencyID;
    				strSQL += " and a.ndeptid=" + deptID;
    				strSQL += " and b.nofficeid=" + lOfficeID;
    				strSQL += " and b.ncurrency=" + lCurrencyID;
    				strSQL += " and b.ndeptid=" + deptID;

    				strSQL += " and d.statusid not in ( "  
    					+SETTConstant.TransactionStatus.DELETED+","   
						+SETTConstant.TransactionStatus.TEMPSAVE + ","
		    			+SETTConstant.TransactionStatus.REFUSE+" ) ";
    				
    				//strSQL += " group by a.ntempid,a.nbillid";
    			}
    			
    			log.print("查询打印选项sql："+strSQL);
    			System.out.println("查询打印选项sql："+strSQL);
    			
    			transPS = transConn.prepareStatement(strSQL);
    			transRS = transPS.executeQuery();
    			
    			while(transRS.next())
    			{
    				info = new BillrelationSetInfo();
    				info.setId(transRS.getLong("id"));
    				info.setRelationdeptid(transRS.getLong("ndeptid"));
    				info.setRelationmax(transRS.getLong("nmaxprint"));
    				//info.setSetid(transRS.getLong("nbillid"));
    				info.setSetname(transRS.getString("sbilltypename"));
    				//info.setTemplateid(transRS.getLong("ntempid"));
    				info.setTemplatename(transRS.getString("stempName"));
    				//info.setTemplateno(transRS.getLong("ncoupletno"));				
    				info.setModuleid(transRS.getLong("nmoduleid"));
    				info.setRelationtypeid(transRS.getLong("ntransactiontypeid"));
    				info.setTransID(lTransIDs[k]);
    				info.setTransNo(TransNos[k]);
    				
    				resultPrintOption.add(info);
    				
    				QueryPrintBiz biz = new QueryPrintBiz();
    				
     				PrintXMLTimeInfo printXMLInfo = new PrintXMLTimeInfo();
     				printXMLInfo.setId(lTransIDs[k]);
     				printXMLInfo.setTransNo(TransNos[k]);
     				printXMLInfo.setBillName(new String[]{info.getTemplatename()});
     				printXMLInfo.setDeptID(deptID);
     				printXMLInfo.setOfficeID(lOfficeID);
     				printXMLInfo.setCurrencyID(lCurrencyID);
     				printXMLInfo.setModule(Constant.ModuleType.SETTLEMENT);
     				printXMLInfo.setOpretionType(info.getRelationtypeid());
     				
     				boolean blPrint = biz.valadatePrintTWO(printXMLInfo);
     				
     				if(!blPrint)
     				{
     					throw new IException("该交易"+TransNos[k]+"对应单据模版已达到最大打印次数");
     				}
    			}
        	}

    	}
    	catch (Exception ie)
    	{
    		ie.printStackTrace();
    		throw new IException(ie.getMessage());
    	}
    	finally
    	{
    		this.finalizeDAO();
    	}
    	
		if(resultPrintOption.isEmpty())
		{
			throw new IException("     批量打印未设置     ");
		}
    	
    	return resultPrintOption;
    }
    
}
