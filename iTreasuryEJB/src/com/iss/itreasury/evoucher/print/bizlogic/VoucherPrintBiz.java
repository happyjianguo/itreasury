package com.iss.itreasury.evoucher.print.bizlogic;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.evoucher.print.dao.QueryPrintDao;
import com.iss.itreasury.evoucher.print.dao.VoucherPrintDao;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo_New;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class VoucherPrintBiz {
	VoucherPrintDao printDao = new VoucherPrintDao();
	
	/**
	 * 电子单据柜 单据打印
	 * @author zk 2013-01-22
	 *
	 */
	public PagerInfo queryVouchersInfo(QueryPrintConditionInfo_New info) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = printDao.queryVouchersInfo(info);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(VoucherPrintBiz.class, "getVouchersResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getVouchersResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ID = -1;
		long TransactionTypeID = -1;
		long InputUserID = -1;
		long OperationTypeID = -1;
		long temp_TransactionTypeID = -1;
		long currencyID = -1;
		long officeID = -1;
		double transAmount = 0.0;
		String TransNo = "";
		String ReceiveAccountNo = "";
		String PayAccountNo = "";
		String Abstract = "";
		Timestamp dtTrans = null;
		PrintXMLTimeInfo pInfo = null;
		QueryPrintDao qDao = new QueryPrintDao();
		
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				dtTrans = rs.getTimestamp("dtTrans");
				TransNo = rs.getString("TransNo");
				TransactionTypeID = rs.getLong("TransactionTypeID");
				InputUserID = rs.getLong("InputUserID");
				OperationTypeID = rs.getLong("OperationTypeID");
				ReceiveAccountNo = rs.getString("RECEIVEACCOUNTNO");
				PayAccountNo = rs.getString("PAYACCOUNTNO");
				transAmount = rs.getDouble("transAmount");
				Abstract = rs.getString("Abstract");
				currencyID = rs.getLong("CurrencyID");
				officeID = rs.getLong("OfficeID");
				
				if(TransactionTypeID < 0){
					TransactionTypeID = OperationTypeID;
				}
				
//				pInfo = new PrintXMLTimeInfo();
//				pInfo.setCurrencyID(currencyID);
//				pInfo.setOfficeID(officeID);
//				pInfo.setModule(Constant.ModuleType.SETTLEMENT);
//				pInfo.setDeptID(VOUConstant.PrintSection.FINANCECOMPANY);
//				pInfo.setId(ID);
//				pInfo.setTransTypeID(temp_TransactionTypeID);
//				long printNUM = qDao.getPrintXMLTime(pInfo);
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,ID);
				PagerTools.returnCellList(cellList,DataFormat.getDateString(dtTrans));
				PagerTools.returnCellList(cellList,TransNo+","+ID+","+TransactionTypeID+","+InputUserID+","+OperationTypeID+","+TransNo);
				PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(TransactionTypeID));
				PagerTools.returnCellList(cellList,ReceiveAccountNo);
				PagerTools.returnCellList(cellList,PayAccountNo);
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(transAmount));
				PagerTools.returnCellList(cellList,Abstract);
				PagerTools.returnCellList(cellList,NameRef.getUserNameByID(InputUserID));
				//PagerTools.returnCellList(cellList,printNUM>0?printNUM:0);
				PagerTools.returnCellList(cellList,TransNo);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

}
