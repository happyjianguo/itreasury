package com.iss.itreasury.loan.query.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.query.dao.QueryDiscountDao;
import com.iss.itreasury.loan.query.dataentity.QueryDiscountInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QueryDiscountBiz {
	
	QueryDiscountDao discountDao = new QueryDiscountDao();
	
	/**
	 * 贴现合同查询
	 * @param QueryDiscountInfo discountInfo
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-15
	 */
	public PagerInfo queryDiscountContractInfo(QueryDiscountInfo discountInfo) throws Exception{
			
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = discountDao.queryDiscountContractInfo(discountInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryDiscountBiz.class,"getDiscountContractResultSetHandle");
			pagerInfo.setTotalExtensionMothod(QueryDiscountBiz.class, "getSumAmountResult", 3);
			
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
	public ArrayList getDiscountContractResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ID = -1;
		long tsDiscountTypeID = -1;
		long statusID = -1;
		String contractCode = null;
		String borrowClientName = null;
		String applyCode = null;
		double examineAmount = 0.00;
		double checkAmount = 0.00;
		double mDiscountAccrual = 0.00;
		double discountPurchaserInterest = 0.00;
		double discountRate = 0.00;
		Timestamp loanStart = null;
		Timestamp loanEnd = null;
		Timestamp inputDate = null;
		String inputUserName = null;
		
		ContractAmountInfo amountInfo = null;
		ContractOperation operation = new ContractOperation();
		
		try {
			while(rs.next()){
				ID = rs.getLong("contractID");
				contractCode = rs.getString("contractCode");
				borrowClientName = rs.getString("borrowClientName");
				applyCode = rs.getString("applyCode");
				tsDiscountTypeID = rs.getLong("tsDiscountTypeID");
				examineAmount = rs.getDouble("examineAmount");
				checkAmount = rs.getDouble("CheckAmount");
				mDiscountAccrual = rs.getDouble("mDiscountAccrual");
				discountPurchaserInterest = rs.getDouble("discountPurchaserInterest");
				discountRate = rs.getDouble("discountRate");
				loanStart = rs.getTimestamp("LoanStart");
				loanEnd = rs.getTimestamp("LoanEnd");
				inputDate = rs.getTimestamp("InputDate");
				statusID = rs.getLong("statusID");
				inputUserName = rs.getString("inputUserName");
				
				amountInfo = operation.getLateAmount(ID);
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatString(contractCode)+","+ID);
				PagerTools.returnCellList(cellList,DataFormat.formatString(borrowClientName));
				PagerTools.returnCellList(cellList,DataFormat.formatString(applyCode));
				PagerTools.returnCellList(cellList,LOANConstant.TsDiscountType.getName(tsDiscountTypeID));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(examineAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(checkAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mDiscountAccrual+discountPurchaserInterest));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(discountPurchaserInterest));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mDiscountAccrual));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(amountInfo.getBalanceAmount()));
				PagerTools.returnCellList(cellList,DataFormat.formatRate(discountRate)+"%");
				PagerTools.returnCellList(cellList,DataFormat.getDateString(loanStart));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(loanEnd));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(inputDate));
				PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(statusID));
				PagerTools.returnCellList(cellList,DataFormat.formatString(inputUserName));
				
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
	public ArrayList getSumAmountResult(ResultSet rs){
		
		ArrayList<String> list = new ArrayList<String>();
		double examineAmountSum = 0.00;
		double checkAmountSum = 0.00;
		double discountInterestSum = 0.00;
		double discountPurchaserInterestSum = 0.00;
		double totalInterestSum = 0.00;
		double balanceSum = 0.00;
		
		try {
			while(rs.next()){
				//获取数据
				examineAmountSum = examineAmountSum + rs.getDouble("examineAmount");
				checkAmountSum = checkAmountSum + rs.getDouble("CheckAmount");
				discountInterestSum = discountInterestSum + rs.getDouble("mDiscountAccrual");
				discountPurchaserInterestSum = discountPurchaserInterestSum + rs.getDouble("discountPurchaserInterest");
				balanceSum = balanceSum + rs.getDouble("balance");
			}
			
			totalInterestSum = discountInterestSum + discountPurchaserInterestSum;
			
			list.add("贴现批准金额{"+DataFormat.formatListAmount(examineAmountSum)+"}");
			list.add("贴现实付金额{"+DataFormat.formatListAmount(checkAmountSum)+"}");
			list.add("贴现余额{"+DataFormat.formatListAmount(balanceSum)+"}");
			list.add("贴现总利息{"+DataFormat.formatListAmount(totalInterestSum)+"}");
			list.add("贴现人付息{"+DataFormat.formatListAmount(discountInterestSum)+"}");
			list.add("贴现买方付息{"+DataFormat.formatListAmount(discountPurchaserInterestSum)+"}");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * 贴现票据明细表查询
	 * @author zk 2012-01-15
	 *
	 */
	public PagerInfo queryDiscountBillDetailInfo(long lContractID,long lDiscountID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			if(lContractID > 0){
				sql = discountDao.queryDiscountBillByContractID(lContractID);
			}else if(lDiscountID > 0){
				sql = discountDao.queryDiscountBillByDiscountID(lDiscountID);
			}
			
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NSERIALNO");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(QueryDiscountBiz.class, "getformatSerialNO", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SUSERNAME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SBANK");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NISLOCAL");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(QueryDiscountBiz.class, "getIsLocal", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SFORMEROWNER");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("DTCREATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("DTEND");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SCODE");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MAMOUNT");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NADDDAYS");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NACCEPTPOTYPEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LOANConstant.DraftType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
			pagerInfo.setUsertext("金额总计{mAmount}");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public String getformatSerialNO(long nSerialNO){
			
		return DataFormat.formatInt(nSerialNO,3,true);
	}
	public String getIsLocal(long nIsLocal){
		String temp = "";
		if(nIsLocal == Constant.YesOrNo.NO){
			temp = "非本地";
		}else if (nIsLocal == Constant.YesOrNo.YES){
			temp = "本地";
		}
		return temp;
	}
	/**
	 * 贴现票据计息明细表查询
	 * @author zk 2012-01-16
	 *
	 */
	public PagerInfo queryDiscountBillInterestInfo(long lContractID,long lDiscountID,long lCredenceID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			if(lContractID > 0){
				sql = discountDao.queryDiscountBillByContractID(lContractID);
			}else if(lDiscountID > 0){
				sql = discountDao.queryDiscountBillByDiscountID(lDiscountID);
			}else if(lCredenceID > 0){
				sql = discountDao.queryDiscountBillByCredenceID(lCredenceID);
			}
			
			pagerInfo.setSqlString(sql);
			
			Map<String, Long> params = new HashMap<String, Long>();
			params.put("lContractID", lContractID);
			params.put("lDiscountID", lDiscountID);
			params.put("lCredenceID", lCredenceID);
			
			pagerInfo.setExtensionMothod(QueryDiscountBiz.class,"getDiscountBillInterestResultSetHandle",params);
			pagerInfo.setTotalExtensionMothod(QueryDiscountBiz.class, "getSumBillInterestAmountResult",params);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getDiscountBillInterestResultSetHandle(ResultSet rs, Map<String, Long> params) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		QueryDiscountInfo rInfo = new QueryDiscountInfo();
		
		long lContractID = params.get("lContractID");
		long lDiscountID = params.get("lDiscountID");
		long lCredenceID = params.get("lCredenceID");
		long nSerialNO = -1;
		long nAddDays = -1;
		long nDays = 0;
		String sCode = null;
		String strEndDate = null;
		double amount = 0.00;
		double dAccrual = 0.00;
		double dDiscountRate = 0.00;
		double dPurchaserRate = 0.00;
		double dPurchaserAmount = 0.00;
		double checkAmount = 0.00;
		Timestamp tsDiscountDate = null;
		Timestamp dtEnd = null;
		
		try {
			while(rs.next()){
				nSerialNO = rs.getLong("NSERIALNO");
				sCode = rs.getString("SCODE");
				amount = rs.getDouble("MAMOUNT");
				dtEnd = rs.getTimestamp("DTEND");
				nAddDays = rs.getLong("NADDDAYS");
				checkAmount = rs.getDouble("MCHECKAMOUNT");
				
				rInfo = this.getBillInterestRelatedInfo(lContractID,lDiscountID,lCredenceID);
				
				tsDiscountDate = rInfo.getTsDiscountDate();
				dAccrual = rInfo.getDAccrual();
				dDiscountRate = rInfo.getDDiscountRate();
				dPurchaserRate = rInfo.getDPurchaserRate();
				
				
				if(dtEnd != null && tsDiscountDate != null){
					strEndDate = dtEnd.toString();
					dtEnd = new java.sql.Timestamp(new Integer(strEndDate.substring(0, 4)).intValue() - 1900, 
							new Integer(strEndDate.substring(5, 7)).intValue() - 1,
							new Integer(strEndDate.substring(8, 10)).intValue(), 0,
							0, 0, 0);
					nDays = (int) java.lang.Math.ceil((dtEnd.getTime() - tsDiscountDate.getTime()) / 86400000)
							+ rs.getInt("NADDDAYS");	//加上节假日增加计息天数
				}
				if(nDays >= 0){
					if (rs.getLong("NISLOCAL") == LOANConstant.YesOrNo.NO) {
						nDays = nDays + 3;
					}
					dAccrual = amount * (dDiscountRate / 360) * 0.01 * nDays;
					dAccrual = DataFormat.formatDouble(dAccrual, 2);
					dPurchaserAmount = dAccrual * dPurchaserRate * 0.01;
					dPurchaserAmount = DataFormat.formatDouble(dPurchaserAmount, 2);
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatInt(nSerialNO,3,true));
				PagerTools.returnCellList(cellList,DataFormat.formatString(sCode));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(amount));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(tsDiscountDate));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(dtEnd));
				PagerTools.returnCellList(cellList,nAddDays);
				PagerTools.returnCellList(cellList,nDays);
				PagerTools.returnCellList(cellList,DataFormat.formatRate(dDiscountRate));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(dPurchaserAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(dAccrual - dPurchaserAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(dAccrual));
				PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(checkAmount));
				
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
	//获得表格其他字段的信息
	public QueryDiscountInfo getBillInterestRelatedInfo(long lContractID,long lDiscountID,long lCredenceID){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		QueryDiscountInfo rInfo = new QueryDiscountInfo();
		String sql = "";
		
		try {
			con = Database.getConnection();
			if(lContractID > 0){
				
				sql = " select a.* from Loan_ContractForm a where a.ID = " + lContractID;
			}else if(lDiscountID > 0){
				
				sql = " select a.* from Loan_LoanForm a where a.ID = " + lDiscountID;
			}else if(lCredenceID > 0){
				
				sql = " select a.* from Loan_ContractForm a, Loan_DiscountCredence b where a.ID = b.nContractID and b.ID = " + lCredenceID;
			}
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				rInfo.setDExamineAmount(rs.getDouble("mExamineAmount"));							//批准金额
				rInfo.setDRealAmount(rs.getDouble("mCheckAmount"));									//核定金额		
				rInfo.setDAccrual(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount"));	//贴现利息		
				rInfo.setDDiscountRate(rs.getDouble("mDiscountRate"));								//贴现利率				
				rInfo.setDTotalDiscountAccrual(rs.getDouble("mDiscountAccrual")); 					//贴现人付息				
				rInfo.setDTotalPurchaserAmount(rs.getDouble("mPurchaserAmount"));					//买方付息
				rInfo.setDPurchaserRate(rs.getDouble("purchaserInterestRate"));						//买方利率 	
				rInfo.setTsDiscountDate(rs.getTimestamp("dtDiscountDate")); 						//贴现计息日
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rInfo;
	}
	public ArrayList getSumBillInterestAmountResult(ResultSet rs,Map<String, Long> params){
		
		ArrayList<String> list = new ArrayList<String>();
		QueryDiscountInfo rInfo = new QueryDiscountInfo();
		
		long lContractID = params.get("lContractID");
		long lDiscountID = params.get("lDiscountID");
		long lCredenceID = params.get("lCredenceID");
		double totalActualAmountSum = 0.00;
		double totalActualPayAmountSum = 0.00;
		double totalInterestSum = 0.00;
		
		try {
			while(rs.next()){
				//获取数据
				totalActualAmountSum = totalActualAmountSum +  + rs.getDouble("MAMOUNT");
				totalActualPayAmountSum = totalActualPayAmountSum + rs.getDouble("MCHECKAMOUNT");
			}
			
			rInfo = this.getBillInterestRelatedInfo(lContractID,lDiscountID,lCredenceID);
			totalInterestSum = totalActualAmountSum - totalActualPayAmountSum + rInfo.getDTotalPurchaserAmount();
			
			list.add("汇总贴现总利息{"+DataFormat.formatDisabledAmount(totalInterestSum)+"}");
			list.add("汇总实付贴现金额{"+DataFormat.formatDisabledAmount(totalActualPayAmountSum)+"}");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * 贴现凭证信息查询
	 * @author zk 2012-01-16
	 *
	 */
	public PagerInfo queryDiscountVoucherInfo(QueryDiscountInfo discountVoucherInfo) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = discountDao.queryDiscountVoucherInfo(discountVoucherInfo);
			
			pagerInfo.setSqlString(sql);
			
			Map<String, Long> params = new HashMap<String, Long>();
			
			pagerInfo.setExtensionMothod(QueryDiscountBiz.class,"getDiscountVoucherResultSetHandle");
			pagerInfo.setTotalExtensionMothod(QueryDiscountBiz.class, "getSumVoucherAmountResult");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList getDiscountVoucherResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ID = -1;
		long statusID = -1;
		String discountContractCode = null;
		String applyClientName = null;
		String discountCredenceCode = null; 
		String inputUserName = null;
		double billAmount = 0.00;
		double billInterest = 0.00;
		double purchaserInterest = 0.00;
		double discountRate = 0.00;
		Timestamp discountDate = null;
		Timestamp inputDate = null;
		
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				discountContractCode = rs.getString("DiscountContractCode");
				applyClientName = rs.getString("applyClientName");
				discountCredenceCode = rs.getString("code");
				billAmount = rs.getDouble("BillAmount");
				billInterest = rs.getDouble("BillInterest");
				purchaserInterest = rs.getDouble("PurchaserInterest");
				discountRate = rs.getDouble("DiscountRate");
				discountDate = rs.getTimestamp("DiscountDate");
				statusID = rs.getLong("statusID");
				inputUserName = rs.getString("InputUserName");
				inputDate = rs.getTimestamp("InputDate");
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatString(discountContractCode)+","+ID);
				PagerTools.returnCellList(cellList,DataFormat.formatString(applyClientName));
				PagerTools.returnCellList(cellList,DataFormat.formatString(discountCredenceCode));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(billAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(billAmount - billInterest));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(billInterest));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(purchaserInterest));
				PagerTools.returnCellList(cellList,DataFormat.formatRate(discountRate));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(discountDate));
				PagerTools.returnCellList(cellList,LOANConstant.DiscountCredenceStatus.getName(statusID));
				PagerTools.returnCellList(cellList,inputUserName);
				PagerTools.returnCellList(cellList,DataFormat.formatDate(inputDate));
				
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
	public ArrayList getSumVoucherAmountResult(ResultSet rs){
		
		ArrayList<String> list = new ArrayList<String>();
		double totalDiscountAmountSum = 0.00;
		double totalDiscountInterestSum = 0.00;
		double totalDiscountActualPayAmountSum = 0.00;
		double discountPurchaserInterest = 0.00;
		
		
		try {
			while(rs.next()){
				//获取数据
				totalDiscountAmountSum = totalDiscountAmountSum +  + rs.getDouble("BillAmount");
				totalDiscountInterestSum = totalDiscountInterestSum + rs.getDouble("BillInterest");
				discountPurchaserInterest = discountPurchaserInterest + rs.getDouble("PurchaserInterest");
			}
			
			totalDiscountActualPayAmountSum = totalDiscountAmountSum - totalDiscountInterestSum;
			
			list.add("贴现批准金额{"+DataFormat.formatListAmount(totalDiscountAmountSum)+"}");
			list.add("贴现实付金额{"+DataFormat.formatListAmount(totalDiscountActualPayAmountSum)+"}");
			list.add("贴现人付息{"+DataFormat.formatListAmount(totalDiscountInterestSum)+"}");
			list.add("贴现买方付利息{"+DataFormat.formatListAmount(discountPurchaserInterest)+"}");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
