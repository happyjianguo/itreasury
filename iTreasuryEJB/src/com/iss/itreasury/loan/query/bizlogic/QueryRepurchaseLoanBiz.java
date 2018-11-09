package com.iss.itreasury.loan.query.bizlogic;
import com.iss.itreasury.loan.query.dao.*;
import com.iss.itreasury.loan.query.dataentity.QuerySumInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.query.dataentity.RepurchaseFormWhereInfo;
import com.iss.itreasury.loan.query.dataentity.RepurchaseLoanFormWhereInfo;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.SessionMng;
import com.iss.itreasury.loan.query.dataentity.*;
public class QueryRepurchaseLoanBiz {
	
	/**
     * 查询合同表中已经被资产回购合同所用到的信息
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
	public PageLoader queryRepurchaseLoanApplay(RepurchaseLoanFormWhereInfo wInfo)
    throws Exception 
    {
		
		return new QueryRepurchaseLoanFormDao().QueryRepurchaseForm(wInfo);
	}
	
	 /**
     * 
     * @param wInfo
     * @return
     */
    public String getOrderBySql(RepurchaseFormWhereInfo wInfo) {
        return new QueryRepurchaseFormDao().getOrderBySql(wInfo);   
    }
    public double queryContractSum(RepurchaseLoanFormWhereInfo wInfo) throws Exception
	{
		double result=0;
		QueryRepurchaseLoanFormDao dao = new QueryRepurchaseLoanFormDao();

		
		try
		{
			result = dao.QueryContractSum(wInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QueryRepurchaseLoanBiz test=new QueryRepurchaseLoanBiz();
		RepurchaseLoanFormWhereInfo wInfo=new RepurchaseLoanFormWhereInfo();
		long ClientId=29;
		long id=LOANConstant.RepurchaseStatus.SUBMIT;
		wInfo.setContractCodeFrom("");
		
		wInfo.setBorrowClientId(ClientId);
		wInfo.setCodeOfRepurchaseForm("");
		wInfo.setRepurchaseDateFrom(DataFormat.getDateTime("2005-12-01"));
		wInfo.setRepurchaseDateTo(DataFormat.getDateTime("2006-12-01"));
		wInfo.setBankId(id);
		PageLoader pageLoader=null;
		pageLoader=new QueryRepurchaseLoanFormDao().QueryRepurchaseForm(wInfo);
		String strPageLoaderKey="";
		
		
		SessionMng sessionMng=new SessionMng();
		strPageLoaderKey=sessionMng.setPageLoader(pageLoader);
		System.out.println("strPageLoaderKey:"+strPageLoaderKey);
		Long lDesc=(Long)sessionMng.getQueryCondition(strPageLoaderKey);
		RepurchaseLoanFormInfo[] queryResults = null;
		
		
		
		try
		{
		test.queryContractSum(wInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();	
		}
		System.out.println("adfadfafadfa");

	}

}
