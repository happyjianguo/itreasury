package com.iss.itreasury.loan.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.query.dao.QueryNoticeDao;
import com.iss.itreasury.loan.query.dataentity.QueryNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QueryNoticeBiz {
	
	QueryNoticeDao noticeDao = new QueryNoticeDao();
	
	/**
	 * 放款通知单查询
	 * @param QueryNoticeInfo nInfo
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-09
	 */
	public PagerInfo queryPayNoticeInfo(QueryNoticeInfo nInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = noticeDao.queryPayNoticeInfo(nInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("Code");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"Code", "ID", "contractID", "DrawNoticeID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.LONG, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("loanTypeName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ContractCode");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("loanClientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ConsignClientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("loanAmount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("amount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(QueryNoticeBiz.class, "getPayRate", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("outDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("inDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("statusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LOANConstant.LoanPayNoticeStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("inputDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InputUserName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setUsertext("放款金额{amount}");
			
			pagerInfo.setDepictList(depictList);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
	public String getPayRate(long id){
		
		String strPayRate = "";
		try {
			PayNoticeOperation operation = new PayNoticeOperation();
			if(id > 0){
				strPayRate = DataFormat.formatRate(operation.getLatelyRate(id).getLateRate())+"%";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strPayRate;
	}
	/**
	 * 还款通知单查询
	 * @param QueryNoticeInfo nInfo
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-11
	 */
	public PagerInfo queryRePayNoticeInfo(QueryNoticeInfo nInfo) throws Exception{
			
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = noticeDao.queryRePayNoticeInfo(nInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setUsertext("放款总金额{LetoutNoticeAmount};还款总金额{amount};归还利息总金额{balanceAmount}");
			
			pagerInfo.setExtensionMothod(QueryNoticeBiz.class,"getRePayNoticeResultSetHandle");
			
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
	public ArrayList getRePayNoticeResultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ID = -1;
		long IsAhead = -1;
		long statusID = -1;
		String code = null;
		String contractCode = null;
		String loanTypeName = null;
		String clientName = null;
		String consignClientName = null;
		String letoutNoticeCode = null;
		String inputUserName = null;
		double letoutNoticeAmount = 0.00;
		double amount = 0.00;
		double balanceAmount = 0.00;
		Timestamp payBackDate = null;
		Timestamp inputDate = null;
		
		try {
			while(rs.next()){
				ID = rs.getLong("ID");
				code = rs.getString("Code") == null ? "":rs.getString("Code");
				contractCode = rs.getString("ContractCode") == null ? "":rs.getString("ContractCode");
				loanTypeName = rs.getString("loanTypeName") == null ? "":rs.getString("loanTypeName");
				clientName = rs.getString("ClientName") == null ? "":rs.getString("ClientName");
				consignClientName = rs.getString("ConsignClientName") == null ? "":rs.getString("ConsignClientName");
				letoutNoticeCode = rs.getString("LetoutNoticeCode") == null ? "":rs.getString("LetoutNoticeCode");
				letoutNoticeAmount = rs.getDouble("LetoutNoticeAmount");
				amount = rs.getDouble("amount");
				payBackDate = rs.getTimestamp("PBackDate");
				balanceAmount = rs.getDouble("balanceAmount");
				IsAhead = rs.getLong("IsAhead");
				statusID = rs.getLong("statusID");
				inputDate = rs.getTimestamp("inputDate");
				inputUserName = rs.getString("InputUserName") == null ? "":rs.getString("InputUserName");
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,code+","+ID);
				PagerTools.returnCellList(cellList,contractCode);
				PagerTools.returnCellList(cellList,loanTypeName);
				PagerTools.returnCellList(cellList,clientName);
				PagerTools.returnCellList(cellList,consignClientName);
				PagerTools.returnCellList(cellList,letoutNoticeCode);
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(letoutNoticeAmount));
				PagerTools.returnCellList(cellList,DataFormat.formatListAmount(amount));
				PagerTools.returnCellList(cellList,payBackDate==null?"":DataFormat.formatListAmount(balanceAmount));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(payBackDate));
				PagerTools.returnCellList(cellList,LOANConstant.YesOrNo.getName(IsAhead));
				PagerTools.returnCellList(cellList,LOANConstant.AheadRepayStatus.getName(statusID));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(inputDate));
				PagerTools.returnCellList(cellList,inputUserName);
				
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				//返回数据
				resultList.add(rowInfo);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

}
