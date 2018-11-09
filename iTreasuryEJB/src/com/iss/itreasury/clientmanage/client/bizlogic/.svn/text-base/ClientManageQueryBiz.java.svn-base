package com.iss.itreasury.clientmanage.client.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.clientmanage.client.dataentity.ClientInvestInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ClientUploadReportInfo;
import com.iss.itreasury.clientmanage.client.dataentity.ManagerInfo;
import com.iss.itreasury.clientmanage.client.dataentity.StockPartnerInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.clientmanage.enterprisememo.bizlogic.EnterpriseMemoCmd;
import com.iss.itreasury.clientmanage.enterprisememo.dataentity.EnterpriseMemoInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ClientManageQueryBiz {
	private Clientmanage biz = new Clientmanage();

	public PagerInfo findStockPartnerInformation(long clientID, long officeID)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("clientID", clientID);
			map.put("officeID", officeID);
			pagerInfo.setExtensionMothod(ClientManageQueryBiz.class,
					"findStockPartnerInformationHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList findStockPartnerInformationHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		long clientID = Long.valueOf(map.get("clientID") + "").longValue();
		long officeID = Long.valueOf(map.get("officeID") + "").longValue();

		ArrayList list4 = biz.findStockPartnerInformationByID(clientID,
				officeID);
		Iterator it = list4.iterator();
		String stockholderNameCHN4 = ""; // 股东中文名称
		String stockholderNameEN4 = ""; // 股东英文名称
		String stockCharacter4 = ""; // 股东性质
		String stockWay4 = ""; // 入股方式
		String contributiveCurrency4 = ""; // 币种
		String contributiveAmount4 = ""; // 出资金额
		double stockRate4 = 0.00; // 持股比例
		String strStockRate4 = "";
		while (it.hasNext()) {

			StockPartnerInfo stockPartnerInfo4 = (StockPartnerInfo) it.next();

			stockholderNameCHN4 = stockPartnerInfo4.getStockholderNameCHN();
			stockholderNameEN4 = stockPartnerInfo4.getStockholderNameEN();
			stockCharacter4 = stockPartnerInfo4.getStockCharacter();
			stockWay4 = stockPartnerInfo4.getStockWay();
			contributiveCurrency4 = stockPartnerInfo4.getContributiveCurrency();
			contributiveAmount4 = stockPartnerInfo4.getContributiveAmount();
			stockRate4 = stockPartnerInfo4.getStockRate();
			strStockRate4 = String.valueOf(stockRate4);
			// 存储列数据
			cellList = new ArrayList();

			PagerTools.returnCellList(cellList,
					stockholderNameCHN4 == null ? "" : stockholderNameCHN4);
			PagerTools.returnCellList(cellList, stockholderNameEN4 == null ? ""
					: stockholderNameEN4);
			PagerTools.returnCellList(cellList, stockCharacter4 == null ? ""
					: stockCharacter4);
			PagerTools.returnCellList(cellList, stockWay4 == null ? ""
					: stockWay4);
			PagerTools.returnCellList(cellList,
					contributiveCurrency4 == null ? "" : contributiveCurrency4);
			PagerTools.returnCellList(cellList,
					contributiveAmount4 == null ? "" : contributiveAmount4);
			PagerTools.returnCellList(cellList,
					strStockRate4.equals("0.0") ? "" : strStockRate4 + "%");

			// 存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);

			// 返回数据
			resultList.add(rowInfo);

		}

		return resultList;
	}

	public PagerInfo findByClientID(long clientID) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("clientID", clientID);
			pagerInfo.setExtensionMothod(ClientManageQueryBiz.class,
					"findByClientIDHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList findByClientIDHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		long clientID = Long.valueOf(map.get("clientID") + "").longValue();

		ClientInvestInfo clientInvestInfo = new ClientInvestInfo();
		Iterator it = biz.findByClientID(clientID).iterator();
		try {
			while (it.hasNext()) {

				clientInvestInfo = (ClientInvestInfo) it.next();

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, new Clientmanage()
						.findbyid(clientInvestInfo.getPartnerid()).getName());
				PagerTools.returnCellList(cellList, clientInvestInfo
						.getStockCharacter());
				PagerTools.returnCellList(cellList, clientInvestInfo
						.getStockWay());
				PagerTools.returnCellList(cellList, clientInvestInfo
						.getContributiveCurrency());
				PagerTools.returnCellList(cellList, clientInvestInfo
						.getContributiveAmount());
				PagerTools.returnCellList(cellList, clientInvestInfo
						.getStockRate());

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);

				// 返回数据
				resultList.add(rowInfo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}

		return resultList;
	}

	public PagerInfo findClientReportByID(long clientID, long officeID)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("clientID", clientID);
			map.put("officeID", officeID);
			pagerInfo.setExtensionMothod(ClientManageQueryBiz.class,
					"findClientReportHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList findClientReportHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		long clientID = Long.valueOf(map.get("clientID") + "").longValue();
		long officeID = Long.valueOf(map.get("officeID") + "").longValue();

		ArrayList list11 = biz.findClientReportByID(clientID, officeID);
		if (list11 != null) {
			Iterator it = list11.iterator();
			while (it.hasNext()) {

				ClientUploadReportInfo clientUploadReportInfo11 = (ClientUploadReportInfo) it
						.next();
				long reportID11 = clientUploadReportInfo11.getId(); // 报表id
				String reportName11 = clientUploadReportInfo11.getReportName(); // 报表名称
				long reportTypeID11 = clientUploadReportInfo11.getReportType(); // 报表类型id
				String reportType11 = CMConstant.ClientReportType
						.getClientReportType(reportTypeID11);// 报表类型
				Timestamp reportDate11 = clientUploadReportInfo11
						.getReportDate(); // 报表日期
				String sDocPath11 = clientUploadReportInfo11.getSDocPath(); // 文件路径
				String strReportDate11 = String.valueOf(reportDate11);
				strReportDate11 = strReportDate11.equals("null") ? ""
						: strReportDate11.substring(0, 10);
				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, reportName11 == null ? ""
						: reportName11);
				PagerTools.returnCellList(cellList, reportType11);
				PagerTools.returnCellList(cellList, strReportDate11);
				PagerTools.returnCellList(cellList, "详细," + reportID11 + ","
						+ clientID);
				PagerTools.returnCellList(cellList, "下载," + sDocPath11);

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryManagerInfo(ClientInfo clientInfo9) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("ClientInfo", clientInfo9);
			pagerInfo.setExtensionMothod(ClientManageQueryBiz.class,
					"queryManagerInfoHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryManagerInfoHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		ClientInfo clientInfo9 = (ClientInfo) map.get("ClientInfo");
		Collection collection9 = biz.query_ManagerInfo(clientInfo9);
		int count9 = 0;
		if (collection9 != null && collection9.size() > 0) {
			Iterator it = collection9.iterator();
			while (it.hasNext()) {

				ManagerInfo managerinfo = (ManagerInfo) it.next();
				count9 += 1;
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, managerinfo
						.getManagername() == null ? "" : count9);
				PagerTools.returnCellList(cellList,
						managerinfo.getPosition() == null ? "" : managerinfo
								.getPosition());
				PagerTools.returnCellList(cellList, managerinfo
						.getManagername() == null ? "" : managerinfo
						.getManagername());
				PagerTools.returnCellList(cellList,
						managerinfo.getMail() == null ? "" : managerinfo
								.getMail());
				PagerTools.returnCellList(cellList,
						managerinfo.getTel() == null ? "" : managerinfo
								.getTel());

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryEnterpriseMemoInfo(ClientInfo clientInfo9)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("ClientInfo", clientInfo9);
			pagerInfo.setExtensionMothod(ClientManageQueryBiz.class,
					"queryEnterpriseMemoInfoHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryEnterpriseMemoInfoHandle(ResultSet rs, Map map)
			throws Exception {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		ClientInfo clientInfo9 = (ClientInfo) map.get("ClientInfo");
		EnterpriseMemoCmd  enterprisememobiz = new EnterpriseMemoCmd();
		Collection collection9 = enterprisememobiz.query_enterpriseMemoInfo(clientInfo9);
		int count9 = 0;
		if (collection9 != null && collection9.size() > 0) {
			Iterator it = collection9.iterator();
			while (it.hasNext()) {

				EnterpriseMemoInfo enterprisemeno=(EnterpriseMemoInfo)it.next();
				count9 += 1;
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, enterprisemeno.getMemoDescribe()==null ? "" : count9);
				PagerTools.returnCellList(cellList, enterprisemeno.getFormatEventDate());
				PagerTools.returnCellList(cellList, enterprisemeno.getMemoDescribe()==null?"":enterprisemeno.getMemoDescribe());
				PagerTools.returnCellList(cellList, enterprisemeno.getDescribedetail()==null?"":enterprisemeno.getDescribedetail());

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}
}
