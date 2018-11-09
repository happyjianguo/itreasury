package com.iss.itreasury.loan.discount.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.loan.discount.bizlogic.Discount;
import com.iss.itreasury.loan.discount.bizlogic.DiscountHome;
import com.iss.itreasury.loan.discount.dao.DiscountQueryDao;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.query.dao.QueryNoticeDao;
import com.iss.itreasury.loan.query.dataentity.QueryNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class DiscountQueryBiz {
	
	DiscountQueryDao dao = new DiscountQueryDao();
	
	public PagerInfo findDiscountBillByDiscountID(long loanID,long level) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = dao.findDiscountBillByDiscountID(loanID);
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("loanID", loanID);
			map.put("level", level);
			pagerInfo.setExtensionMothod(DiscountQueryBiz.class,"findDiscountBillResultSetHandle",map);
			pagerInfo.setTotalExtensionMothod(DiscountQueryBiz.class,"findDiscountBillTotalResultSetHandle",map);
			
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	
	public ArrayList findDiscountBillResultSetHandle(ResultSet rs,Map map) throws IException, SQLException {
		long loanID = Long.valueOf(map.get("loanID")+"").longValue();
		long level = Long.valueOf(map.get("level")+"").longValue();
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
			while(rs.next()){
				DiscountBillInfo info = new DiscountBillInfo();

				info.setDiscountApplyID(loanID); // 贴现标示
				info.setID(rs.getLong("ID")); // 票据标示
				info.setSerialNo(rs.getLong("nSerialNo")); // 序列号
				info.setUserName(rs.getString("sUserName")); // 原始出票人
				info.setBank(rs.getString("sBank")); // 承兑银行
				info.setIsLocal(rs.getLong("nIsLocal")); // 承兑银行所在地（是否在本地）
				info.setCreate(rs.getTimestamp("dtCreate")); // 出票日
				info.setEnd(rs.getTimestamp("dtEnd")); // 到期日
				info.setCode(rs.getString("sCode")); // 汇票号码
				info.setAmount(rs.getDouble("mAmount")); // 汇票金额
				info.setAddDays(rs.getLong("nAddDays")); // 节假日增加计息天数
				info.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); // 汇票类型
				info.setFormerOwner(rs.getString("sFormerOwner")); // 贴现单位直接前手
				info.setPayee(rs.getString("payee"));// dsc收款人

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,info.getID());
				if ( level==1 )
				{
					PagerTools.returnCellList(cellList,DataFormat.formatInt((int)info.getSerialNo(),3,true)+","+"frm,"+info.getID());
				}else{
					PagerTools.returnCellList(cellList,DataFormat.formatInt((int)info.getSerialNo(),3,true)+",''"+","+"''");
				}
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getUserName()!=null)?info.getUserName():"");
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getPayee()!=null)?info.getPayee():"");
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getBank()!=null)?info.getBank():"");
				if (info.getIsLocal()==Constant.YesOrNo.NO) 
				{
					PagerTools.returnCellList(cellList,"非本地");
				}else if(info.getIsLocal()==Constant.YesOrNo.YES){
					PagerTools.returnCellList(cellList,"本地");
				}
				PagerTools.returnCellList(cellList,DataFormat.formatString(info.getFormerOwner()));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getCreate()));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getEnd()));
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getCode()!=null)?info.getCode():"");
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getAmount()>0)?DataFormat.formatDisabledAmount(info.getAmount()):"0.00");
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getAddDays()>0)?info.getAddDays():"0");
				PagerTools.returnCellList(cellList,(info!=null)&&(info.getAcceptPOTypeID()>0)?LOANConstant.DraftType.getName(info.getAcceptPOTypeID()):"");
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				
				// 返回数据
				resultList.add(rowInfo);
			}
		return resultList;
	}
	public ArrayList findDiscountBillTotalResultSetHandle(ResultSet rs,Map map) throws Exception{
		long lDiscountApplyID = Long.valueOf(map.get("loanID")+"").longValue();
		Connection con = Database.getConnection();
		String strSQL = "select count(*),sum(mAmount)  from Loan_DiscountFormBill where nStatusID="
				+ Constant.RecordStatus.VALID + " and nLoanID="
				+ lDiscountApplyID;
		PreparedStatement ps = con.prepareStatement(strSQL);
		rs = ps.executeQuery();
		long lRecordCount = 0;
		double dTotalAmount = 0.00;
		if (rs != null && rs.next()) {
			lRecordCount = rs.getLong(1);
			dTotalAmount = rs.getDouble(2);
		}
		rs.close(); 
		rs = null;
		ps.close();
		ps = null;
		con.close();
		con=null;
		ArrayList list = new ArrayList();
		list.add("<input type=\"hidden\" name=totalamount value='"+dTotalAmount+"'>笔数总计{"+lRecordCount+"}");
		list.add("金额总计{"+DataFormat.formatListAmount(dTotalAmount)+"}");
		return list;
	}


	public PagerInfo findDiscountBillAmount(long loanID,String rq,double fDiscountRate,String symbol,double purchaserInterestRate) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			
			DiscountHome home = null;
			Discount      Discount = null;
			home = (DiscountHome)EJBObject.getEJBHome("DiscountHome");
			Discount = home.create();
			Collection cbill = Discount.findDiscountBillByDiscountID(loanID, Constant.PageControl.CODE_PAGELINECOUNT+1000, 1, 0, 1, fDiscountRate,purchaserInterestRate, DataFormat.getDateTime(rq));
			Map map = new HashMap();
			map.put("loanID", loanID);
			map.put("rq", rq);
			map.put("fDiscountRate", fDiscountRate);
			map.put("purchaserInterestRate", purchaserInterestRate);
			map.put("symbol", symbol);
			map.put("cbill", cbill);
			pagerInfo.setExtensionMothod(DiscountQueryBiz.class,"findDiscountBillAmountResultSetHandle",map);
			pagerInfo.setTotalExtensionMothod(DiscountQueryBiz.class,"findDiscountBillAmountTotalResultSetHandle",map);
			  
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList findDiscountBillAmountResultSetHandle(ResultSet rs,Map map) throws IException, SQLException {
	
		Collection cbill = (Collection) map.get("cbill");
		long loanID = Long.valueOf(map.get("loanID")+"").longValue();
		String rq = map.get("rq")+"";
		double fDiscountRate = Double.valueOf(map.get("fDiscountRate")+"").doubleValue();

		double purchaserInterestRate = Double.valueOf(map.get("purchaserInterestRate")+"").doubleValue();
		String symbol = map.get("symbol")+"";
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		Iterator it = cbill.iterator();
			while(it.hasNext()){
				DiscountBillInfo info = ( DiscountBillInfo ) it.next();

				long nDays = info.getDays();
				double dAccrual = info.getAccrual();
				double dRealAmount = info.getRealAmount();
				double dAmount = info.getAmount();
				double dPurchaserAccrual = info.getPurchaserAccrual();	
				double dDiscountAccrual = info.getDiscountAccrual();
				double dDiscountAcount = info.getDiscountAcount();	
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatInt(info.getSerialNo(),3,true));
				PagerTools.returnCellList(cellList,DataFormat.formatString(info.getCode()));
				PagerTools.returnCellList(cellList,info.getAmount()>0?DataFormat.formatDisabledAmount(dAmount):"0.00");
				PagerTools.returnCellList(cellList,rq);
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getEnd()));
				PagerTools.returnCellList(cellList,nDays+"天");
				PagerTools.returnCellList(cellList,DataFormat.formatRate(fDiscountRate)+"%");
				PagerTools.returnCellList(cellList,dPurchaserAccrual>0?symbol+DataFormat.formatDisabledAmount(dPurchaserAccrual):0.00);
				PagerTools.returnCellList(cellList,dDiscountAccrual>0?symbol+DataFormat.formatDisabledAmount(dDiscountAccrual):0.00);
				PagerTools.returnCellList(cellList,dDiscountAcount>0?symbol+DataFormat.formatDisabledAmount(dDiscountAcount):0.00);
				PagerTools.returnCellList(cellList,dAccrual>0?symbol+DataFormat.formatDisabledAmount(dAccrual):0.00);
				PagerTools.returnCellList(cellList,dRealAmount>0?symbol+DataFormat.formatDisabledAmount(dRealAmount):0.00);
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				
				// 返回数据
				resultList.add(rowInfo);
			}
		return resultList;
	}
	
	public ArrayList findDiscountBillAmountTotalResultSetHandle(ResultSet rs,Map map) throws Exception{
		Collection cbill = (Collection) map.get("cbill");
		Iterator it = cbill.iterator();
		ArrayList list = new ArrayList();
		double dPurchaserAccrual=0.0;
		double dDiscountAccrual=0.0;
		double sumPurchaserAccrual=0.0;
		double sumdiscountAccrual=0.0;
		
		double dTotalAccrual=0.0;
		double dTotalRealAmount=0.0;
		
		double totalAmount=0.0;
		while(it.hasNext()){
			DiscountBillInfo info = (DiscountBillInfo) it.next();
			dPurchaserAccrual = info.getPurchaserAccrual();
			dDiscountAccrual = info.getDiscountAccrual();
			sumPurchaserAccrual = sumPurchaserAccrual+dPurchaserAccrual;
			sumdiscountAccrual = sumdiscountAccrual+dDiscountAccrual;
			dTotalAccrual = info.getTotalAccrual();
			dTotalRealAmount = info.getTotalRealAmount();

			totalAmount = info.getTotalAmount();
		}
		list.add("<input type=\"hidden\" name='dTotalRealAmount' value='"+DataFormat.formatDisabledAmount(dTotalRealAmount)+"'>"
				+"<input type=\"hidden\" name='sumPurchaserAccrual' value='"+DataFormat.formatDisabledAmount(sumPurchaserAccrual)+"'>"
				+"<input type=\"hidden\" name='sumdiscountAccrual' value='"+DataFormat.formatDisabledAmount(sumdiscountAccrual)+"'>"
				+"<input type=\"hidden\" name='dTotalAccrual' value='"+DataFormat.formatDisabledAmount(dTotalAccrual)+"'>"
				+"<input type=\"hidden\" name='dTotalAmount' value='"+DataFormat.formatDisabledAmount(totalAmount)+"'>"
				+"汇总贴现总利息{"+DataFormat.formatDisabledAmount(dTotalAccrual)+"}");
		list.add("汇总实付贴现金额{"+DataFormat.formatDisabledAmount(dTotalRealAmount)+"}");
		return list;
	}


	public PagerInfo findBillInterestByID(long lcontractid, long i, long j,
			long k, long lOrderParam, long lDesc,String symbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			
			DiscountHome home = null;
			Discount      Discount = null;
			home = (DiscountHome)EJBObject.getEJBHome("DiscountHome");
			Discount = home.create();
			Collection 		c_billInfo = Discount.findBillInterestByID(lcontractid,-1,1000,k,lOrderParam,lDesc);

			Map map = new HashMap();
			map.put("c_billInfo", c_billInfo);
			map.put("symbol", symbol);
			pagerInfo.setExtensionMothod(DiscountQueryBiz.class,"findBillInterestResultSetHandle",map);
			pagerInfo.setTotalExtensionMothod(DiscountQueryBiz.class,"findBillInterestTotalResultSetHandle",map);
			  
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList findBillInterestResultSetHandle(ResultSet rs,Map map) throws IException, SQLException {
		
		Collection c_billInfo = (Collection) map.get("c_billInfo");
		String symbol = map.get("symbol")+"";
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		Iterator it = c_billInfo.iterator();
			while(it.hasNext()){
				DiscountBillInfo info = ( DiscountBillInfo ) it.next();

				long lCount = info.getCount();
				double dTotalAccrual = info.getTotalAccrual();
				double dTotalRealAmount = info.getTotalRealAmount();
				String nDays = String.valueOf(info.getDays());
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,info.getID());
				PagerTools.returnCellList(cellList,info.getDiscountCredenceID()>0?info.getDiscountCredenceID(): -1);
				PagerTools.returnCellList(cellList,DataFormat.formatInt(info.getSerialNo(),3,true));
				PagerTools.returnCellList(cellList,DataFormat.formatString(info.getCode()));
				PagerTools.returnCellList(cellList,DataFormat.formatString(info.getCode()));
				PagerTools.returnCellList(cellList,(info.getAmount()>0?DataFormat.formatDisabledAmount(info.getAmount()):0.00+""));
				PagerTools.returnCellList(cellList,symbol+((info!=null)&&(info.getAmount()>0)?DataFormat.formatDisabledAmount(info.getAmount()):"0.00"));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getDiscountDate()));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getEnd()));
				PagerTools.returnCellList(cellList,info.getAddDays()+"天");
				PagerTools.returnCellList(cellList,info.getDays()+"天");
				PagerTools.returnCellList(cellList,DataFormat.formatRate(DataFormat.formatRate(info.getDiscountRate()))+"%");
				PagerTools.returnCellList(cellList,(info.getPurchaserAccrual()>0?DataFormat.formatDisabledAmount(info.getPurchaserAccrual()):0.00+""));
				PagerTools.returnCellList(cellList,symbol+(info.getPurchaserAccrual()>0?DataFormat.formatDisabledAmount(info.getPurchaserAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,info.getDiscountAccrual()>0?DataFormat.formatDisabledAmount(info.getDiscountAccrual())+"":0.00+"");
				PagerTools.returnCellList(cellList,symbol+(info.getDiscountAccrual()>0?DataFormat.formatDisabledAmount(info.getDiscountAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getDiscountAcount()>0?DataFormat.formatDisabledAmount(info.getDiscountAcount()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getAccrual()>0?DataFormat.formatDisabledAmount(info.getAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getRealAmount()>0?DataFormat.formatDisabledAmount(info.getRealAmount()):"0.00"));
				
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				
				// 返回数据
				resultList.add(rowInfo);
			}
		return resultList;
	}
	
	public ArrayList findBillInterestTotalResultSetHandle(ResultSet rs,Map map) throws Exception{
		Collection c_billInfo = (Collection) map.get("c_billInfo");
		Iterator it = c_billInfo.iterator();
		String symbol = map.get("symbol")+"";
		ArrayList list = new ArrayList();
		double dTotalAccrual = 0.00;
		double dTotalRealAmount=0.00;
		String nDays = "";
		if(it.hasNext()){
			DiscountBillInfo info = ( DiscountBillInfo ) it.next();
			dTotalAccrual = info.getTotalAccrual();
			dTotalRealAmount = info.getTotalRealAmount();
			nDays= String.valueOf(info.getDays());
		}
		list.add("<input type=\"hidden\" name=\"nDays_temp\" value='"+nDays+"'>汇总贴现总利息{"+symbol+DataFormat.formatDisabledAmount(dTotalAccrual)+"}");
		list.add("汇总实付贴现金额{"+symbol+DataFormat.formatDisabledAmount(dTotalRealAmount)+"}");
		return list;
	}
	
	
	
	public PagerInfo findBillInterest(long lcontractid, long i, long j,
			long k, long lOrderParam, long lDesc,String symbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);
			
			DiscountHome home = null;
			Discount      Discount = null;
			home = (DiscountHome)EJBObject.getEJBHome("DiscountHome");
			Discount = home.create();
			Collection 		c_billInfo = Discount.findBillInterestByID(-1,i,1000,k,lOrderParam,lDesc);

			Map map = new HashMap();
			map.put("c_billInfo", c_billInfo);
			map.put("symbol", symbol);
			pagerInfo.setExtensionMothod(DiscountQueryBiz.class,"findBillInterest1ResultSetHandle",map);
			pagerInfo.setTotalExtensionMothod(DiscountQueryBiz.class,"findBillInterestTotal1ResultSetHandle",map);
			  
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList findBillInterest1ResultSetHandle(ResultSet rs,Map map) throws IException, SQLException {
		
		Collection c_billInfo = (Collection) map.get("c_billInfo");
		String symbol = map.get("symbol")+"";
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		Iterator it = c_billInfo.iterator();
			while(it.hasNext()){
				DiscountBillInfo info = ( DiscountBillInfo ) it.next();

				long lCount = info.getCount();
				double dTotalAccrual = info.getTotalAccrual();
				double dTotalRealAmount = info.getTotalRealAmount();
				String nDays = String.valueOf(info.getDays());
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatInt(info.getSerialNo(),3,true));
				PagerTools.returnCellList(cellList,DataFormat.formatString(info.getCode()));
				PagerTools.returnCellList(cellList,symbol+((info!=null)&&(info.getAmount()>0)?DataFormat.formatDisabledAmount(info.getAmount()):"0.00"));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getDiscountDate()));
				PagerTools.returnCellList(cellList,DataFormat.getDateString(info.getEnd()));
				PagerTools.returnCellList(cellList,info.getDays()+"天");
				PagerTools.returnCellList(cellList,DataFormat.formatRate(DataFormat.formatRate(info.getDiscountRate()))+"%");
				PagerTools.returnCellList(cellList,symbol+(info.getPurchaserAccrual()>0?DataFormat.formatDisabledAmount(info.getPurchaserAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getDiscountAccrual()>0?DataFormat.formatDisabledAmount(info.getDiscountAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getDiscountAcount()>0?DataFormat.formatDisabledAmount(info.getDiscountAcount()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getAccrual()>0?DataFormat.formatDisabledAmount(info.getAccrual()):"0.00"));
				PagerTools.returnCellList(cellList,symbol+(info.getRealAmount()>0?DataFormat.formatDisabledAmount(info.getRealAmount()):"0.00"));
				
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				
				// 返回数据
				resultList.add(rowInfo);
			}
		return resultList;
	}
	
	public ArrayList findBillInterestTotal1ResultSetHandle(ResultSet rs,Map map) throws Exception{
		Collection c_billInfo = (Collection) map.get("c_billInfo");
		Iterator it = c_billInfo.iterator();
		String symbol = map.get("symbol")+"";
		ArrayList list = new ArrayList();
		double dTotalAccrual = 0.00;
		double dTotalRealAmount=0.00;
		String nDays = "";
		if(it.hasNext()){
			DiscountBillInfo info = ( DiscountBillInfo ) it.next();
			dTotalAccrual = info.getTotalAccrual();
			dTotalRealAmount = info.getTotalRealAmount();
			nDays= String.valueOf(info.getDays());
		}
		list.add("汇总贴现总利息{"+symbol+DataFormat.formatDisabledAmount(dTotalAccrual)+"}");
		list.add("汇总实付贴现金额{"+symbol+DataFormat.formatDisabledAmount(dTotalRealAmount)+"}");
		return list;
	}

}
