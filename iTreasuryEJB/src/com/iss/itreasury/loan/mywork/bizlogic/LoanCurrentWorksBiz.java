package com.iss.itreasury.loan.mywork.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanCurrentWorksBiz {

	public PagerInfo queryLoanTransActionWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		// TODO Auto-generated method stub
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"resultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}

	public ArrayList resultSetHandle(ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanTransActionWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = (LoanMyWorkInfo) it.next();
				String strFormatedAmount = DataFormat
						.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount == null
						|| strFormatedAmount.equals("") ? "0.00"
						: strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode()
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getStepName());
				PagerTools.returnCellList(cellList, workInfo.getLoanTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName() == null ? "" : workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo.getActionName());
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName() == null ? "" : workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList,
						workInfo.getContractCode() == null ? "" : workInfo
								.getContractCode());
				PagerTools.returnCellList(cellList,
						workInfo.getCode() == null ? "" : workInfo.getCode());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, NameRef
						.getUserNameByID(Long.parseLong(workInfo
								.getInutWorkInfo().getOwner())));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractStatusChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanContractStatusChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}

	public ArrayList queryLoanContractStatusChangeWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanContractStatusChangeWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = (LoanMyWorkInfo) it.next();
				String strFormatedAmount = DataFormat
						.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount == null
						|| strFormatedAmount.equals("") ? "0.00"
						: strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getLoanTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName() == null ? "" : workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName() == null ? "" : workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList,
						workInfo.getContractCode() == null ? "" : workInfo
								.getContractCode());
				PagerTools.returnCellList(cellList,
						workInfo.getCode() == null ? "" : workInfo.getCode());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, LOANConstant.ContractStatus.getName(workInfo.getOldStatus()));
				PagerTools.returnCellList(cellList, LOANConstant.ContractStatus.getName(workInfo.getChangeStatus()));
				 
				PagerTools.returnCellList(cellList, NameRef
						.getUserNameByID(Long.parseLong(workInfo
								.getInutWorkInfo().getOwner())));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRisklevelChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanContractRisklevelChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractRisklevelChangeWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanContractRisklevelChangeWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = ( LoanMyWorkInfo ) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getLoanTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName() == null ? "" : workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName() == null ? "" : workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList,
						workInfo.getContractCode() == null ? "" : workInfo
								.getContractCode());
				PagerTools.returnCellList(cellList,
						workInfo.getCode() == null ? "" : workInfo.getCode());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel.getName(workInfo.getOldStatus()));
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel.getName(workInfo.getChangeStatus()));
				 
				PagerTools.returnCellList(cellList, NameRef
						.getUserNameByID(Long.parseLong(workInfo
								.getInutWorkInfo().getOwner())));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractPlanChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanContractPlanChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanContractPlanChangeWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanContractPlanChangeWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = ( LoanMyWorkInfo ) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getLoanTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName() == null ? "" : workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName() == null ? "" : workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList,
						workInfo.getContractCode() == null ? "" : workInfo
								.getContractCode());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, workInfo.getContractChangeReason()==null?"":workInfo.getContractChangeReason());
				PagerTools.returnCellList(cellList, workInfo.getContractPlanVersion());
				 
				PagerTools.returnCellList(cellList, NameRef
						.getUserNameByID(Long.parseLong(workInfo
								.getInutWorkInfo().getOwner())));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRateChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanContractRateChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractRateChangeWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanContractRateChangeWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = ( LoanMyWorkInfo ) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getLoanTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName() == null ? "" : workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName() == null ? "" : workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList,
						workInfo.getContractCode() == null ? "" : workInfo
								.getContractCode());
				PagerTools.returnCellList(cellList,workInfo.getReason());
				PagerTools.returnCellList(cellList, DataFormat.formatRate(workInfo.getRate()));
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getDtValiDate()));
				PagerTools.returnCellList(cellList, NameRef
						.getUserNameByID(Long.parseLong(workInfo
								.getInutWorkInfo().getOwner())));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRateChangeWorkBatch(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanContractRateChangeWorkBatchResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractRateChangeWorkBatchResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanContractRateChangeWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = ( LoanMyWorkInfo ) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(workInfo.getAmount());
				strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getBatchid() );
				PagerTools.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getInputDate()));
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}


	public PagerInfo queryLoanCredit(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryLoanCreditResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanCreditResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector) workBiz
				.queryLoanCredit(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				LoanMyWorkInfo workInfo = ( LoanMyWorkInfo )it.next();
				double creditAmount = workInfo.getAmount();
				
				String strOperationType = LOANConstant.OperationType.getName(workInfo.getOperationType());
				
				if(workInfo.getOperationType() == LOANConstant.OperationType.CHANGE){
					strOperationType = strOperationType + "("+ LOANConstant.OperationSign.getName(workInfo.getOperationSign()) +")";
				}

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getCode()==null||"".equals(workInfo.getCode())?"":workInfo.getCode() );
				PagerTools.returnCellList(cellList, workInfo.getBorrowClientName());
				PagerTools.returnCellList(cellList, LOANConstant.CreditModel.getName(workInfo.getCreditModel()));
				PagerTools.returnCellList(cellList, strOperationType);
				PagerTools.returnCellList(cellList, DataFormat.formatListAmount(creditAmount));
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getStartDate()));
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getEndDate()));
				PagerTools.returnCellList(cellList, NameRef.getUserNameByID(Long.parseLong(workInfo.getInutWorkInfo().getOwner())));
				PagerTools.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getInputDate()));
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryAfterCreditWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCurrentWorksBiz.class,
					"queryAfterCreditWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryAfterCreditWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_CurrentWork = (Vector)workBiz.queryAfterCreditWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		if (c_CurrentWork != null && c_CurrentWork.size() > 0) {
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext()) {
				AfterCreditWorkInfo workInfo = ( AfterCreditWorkInfo ) it.next();

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo()
						.getWfDefineName() == null
						|| "".equals(workInfo.getInutWorkInfo()
								.getWfDefineName()) ? "&nbsp;" : workInfo
						.getInutWorkInfo().getWfDefineName()
						+ ","
						+ workInfo.getInutWorkInfo().getTaskID()
						+ ","
						+ workInfo.getInutWorkInfo().getEntryID()
						+ ","
						+ workInfo.getInutWorkInfo().getActionCode() 
						+ ","
						+ workInfo.getInutWorkInfo().getStepCode()
						+ ","
						+ workInfo.getContractID()
						+ ","
						+ workInfo.getClientid());
				PagerTools.returnCellList(cellList, workInfo.getInutWorkInfo().getStepName() );
				PagerTools.returnCellList(cellList, workInfo.getCheckReportCode() );
				PagerTools.returnCellList(cellList, workInfo.getClientName());
				PagerTools.returnCellList(cellList, workInfo.getContractCode());
				PagerTools.returnCellList(cellList, LOANConstant.AfterCheckType.getName(workInfo.getCheckType()));
				PagerTools.returnCellList(cellList, workInfo.getCheckYear());
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel.getName(workInfo.getAdvice()));
				PagerTools.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getInputDate()));
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}
}
