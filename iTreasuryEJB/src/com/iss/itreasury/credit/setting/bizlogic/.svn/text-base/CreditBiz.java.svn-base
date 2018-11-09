package com.iss.itreasury.credit.setting.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientmanageBiz;
import com.iss.itreasury.credit.check.CreditCheckBiz;
import com.iss.itreasury.credit.setting.dao.CreditDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class CreditBiz {

	CreditDao creditDao = new CreditDao();
/**
 * 授信变更biz层
 * @param amountFormInfo
 * @return
 * @throws Exception
 */	
   public PagerInfo queryCreditInfo(AmountFormInfo amountFormInfo) throws Exception {

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = creditDao.queryCreditSQL(amountFormInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CreditCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"CreditCode","id","CreditCode"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			//baseInfo.setDisplayType(PagerTypeConstant.LONG);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ClientName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CreditModel");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LOANConstant.CreditModel.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("creditAmount");
			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("StartDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("EndDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InputUserName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("InputDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
   
   /**
    * 授信激活biz层
    * @param amountFormInfo
    * @return
    * @throws Exception
    */	
      public PagerInfo queryCreditStatusInfo(AmountSetupInfo amountSetupInfo) throws Exception {
    	  
   		PagerInfo pagerInfo = null;
   		String sql = null;
   		try
   		{
   			pagerInfo = new PagerInfo();
   			sql = creditDao.queryCreditStatusSQL(amountSetupInfo);
   			pagerInfo.setSqlString(sql);
   			
   			ArrayList depictList = new ArrayList();
   			PagerDepictBaseInfo baseInfo = null;
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("id");
   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("CreditCode");
   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("ClientName");
   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("CreditModel");
   			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
   			baseInfo.setExtensionMothod(LOANConstant.CreditModel.class, "getName", new Class[]{long.class});
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("OperationType");
   			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LOANConstant.OperationType.class, "getName", new Class[]{long.class});
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("creditAmount");
   			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("StartDate");
   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("EndDate");
   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("InputUserName");
   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
   			depictList.add(baseInfo);
   			
   			baseInfo = new PagerDepictBaseInfo();
   			baseInfo.setDisplayName("InputDate");
   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
   			depictList.add(baseInfo);
   			
   			pagerInfo.setDepictList(depictList);
   			
   		}
   		catch(Exception e)
   		{
   			e.printStackTrace();
   			throw new Exception("====>查询异常", e);
   		}
   		return pagerInfo;
   	}
   /**
    * 授信可用额度查询 biz层
    * @param amountFormViewInfo
    * @return
    * @throws Exception
    */
   public PagerInfo queryCreditQueryInfo(AmountFormViewInfo amountFormViewInfo) throws Exception{
	   
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = creditDao.queryCreditQuerySQL(amountFormViewInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(CreditBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
		public ArrayList resultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
		CreditCheckBiz creditCheckBiz = new CreditCheckBiz();
		
		long fid = -1;//
		long clientID = -1;//
		long creditModel = -1;//授信方式
		long creditModelID = -1;//
		long OperationType = -1;//授信操作类型
		long state = -1;//状态
		String creditcode = null;//授信编号
		String clientName = null;//客户名称
		String inputUserName = null;//录入人
		double creditAmount = 0.00;//授信额度
		double UsedAmount = 0.00; //已占用额度
		double RemainAmount = 0.00; //可用额度
		Timestamp startDate = null;//授信开始日期
		Timestamp endDate = null;// 授信结束日期
		Timestamp inputDate = null;//录入日期
		
		try {
			if(rs!=null)
			{
			while(rs.next()){
				fid = rs.getLong("FID");
				clientName = rs.getString("CLIENTNAME");
				creditModel = rs.getLong("CREDITMODEL");
				creditAmount = rs.getDouble("CREDITAMOUNT");
				startDate = rs.getTimestamp("STARTDATE");
				endDate = rs.getTimestamp("ENDDATE");
				inputUserName = rs.getString("INPUTUSERNAME");
				inputDate = rs.getTimestamp("INPUTDATE");
				
				
				formViewInfo.setId(fid);
				formViewInfo.setClientId(rs.getLong("CLIENTID"));
				formViewInfo.setStartDate(startDate);
				formViewInfo.setEndDate(endDate);
				formViewInfo.setOfficeId(rs.getLong("OFFICEID"));
				formViewInfo.setCurrencyId(rs.getLong("CURRENCYID"));
				
				if(formViewInfo.getCreditModel() == LOANConstant.CreditModel.GROUP)
				{
					UsedAmount = creditCheckBiz.getGroupUsedCreditAmount(formViewInfo);
				}
				else
				{
					UsedAmount = creditCheckBiz.getUsedCreditAmount(formViewInfo);
				}

				//计算剩余的授信额度
				RemainAmount = UtilOperation.Arith.sub(creditAmount, UsedAmount);
				
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,clientName+","+fid);
					PagerTools.returnCellList(cellList,LOANConstant.CreditModel.getName(creditModel));
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(creditAmount));
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(UsedAmount)+"#"+fid);
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(RemainAmount));
					PagerTools.returnCellList(cellList,DataFormat.formatDate(startDate));
					PagerTools.returnCellList(cellList,DataFormat.formatDate(endDate));
					PagerTools.returnCellList(cellList,inputUserName);
					PagerTools.returnCellList(cellList,DataFormat.formatDate(inputDate));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
				}	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
		/**
		    * 授信激活biz层
		    * @param amountFormInfo
		    * @return
		    * @throws Exception
		    */	
		      public PagerInfo queryCreditRecordQueryInfo(AmountSetupViewInfo amountSetupViewInfo) throws Exception {
		    			    	  
		   		PagerInfo pagerInfo = null;
		   		String sql = null;
		   		try
		   		{
		   			pagerInfo = new PagerInfo();
		   			sql = creditDao.queryCreditRecordSQL(amountSetupViewInfo);
		   			pagerInfo.setSqlString(sql);
		   			
		   			ArrayList depictList = new ArrayList();
		   			PagerDepictBaseInfo baseInfo = null;
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("CreditCode");
		   			
			  		baseInfo.setExtension(true);
			   		baseInfo.setExtensionName(new String[]{"CreditCode","ID","OperationType","CreditModel"});
			   		baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING, PagerTypeConstant.STRING});
			   		baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
		   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("ClientName");
		   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("CreditModel");
		   			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
		   			baseInfo.setExtensionMothod(LOANConstant.CreditModel.class, "getName", new Class[]{long.class});
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("OperationType");
		   			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
					baseInfo.setExtensionMothod(LOANConstant.OperationType.class, "getName", new Class[]{long.class});
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("CreditAmount");
		   			baseInfo.setDisplayType(PagerTypeConstant.AMOUNT_2);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("StartDate");
		   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("EndDate");
		   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("InputUserName");
		   			baseInfo.setDisplayType(PagerTypeConstant.STRING);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("InputDate");
		   			baseInfo.setDisplayType(PagerTypeConstant.DATE);
		   			depictList.add(baseInfo);
		   			
		   			baseInfo = new PagerDepictBaseInfo();
		   			baseInfo.setDisplayName("State");
		   			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
		   			baseInfo.setExtensionMothod(LOANConstant.CreditFlowStatus.class, "getName", new Class[]{long.class});
		   			depictList.add(baseInfo);
		   			
		   			pagerInfo.setDepictList(depictList);
		   			
		   		}
		   		catch(Exception e)
		   		{
		   			e.printStackTrace();
		   			throw new Exception("====>查询异常", e);
		   		}
		   		return pagerInfo;
		   	}
		
}
