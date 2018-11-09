/*
 * Created on 2005-02-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.bill.query.bizlogic;

import com.iss.itreasury.bill.query.dao.QueryDao;
import com.iss.itreasury.bill.query.dataentity.*;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;



/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryOperation
{
	private static Log4j log4j = null;

	public String getOrderSQL(String orderParamString, long desc)
	{
		QueryDao dao = new QueryDao("Bill_BillType");
		String orderSQL = dao.getOrderSQL(orderParamString, desc);
		return orderSQL;
	}
	
	public PageLoader queryBillType(QueryBillTypeInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Bill_BillType");

		try
		{
			c = dao.queryBillType(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}	
	
	public PageLoader queryBlankVoucher(QueryBlankVoucherInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Bill_BlankVoucher");

		try
		{
			c = dao.queryBlankVoucher(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}	

	public PageLoader queryBill(QueryBillInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Loan_DiscountContractBill");

		try
		{
			c = dao.queryBill(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public PageLoader queryBill1(QueryBillInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Loan_DiscountContractBill");

		try
		{
			c = dao.queryBill1(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public double queryBill1_SumAmount(QueryBillInfo qInfo)throws Exception{
		return new QueryDao("Loan_DiscountContractBill").queryBill1_SumAmount(qInfo);
	}
	public PageLoader queryBlackList(QueryBlackListInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Bill_BlackList");

		try
		{
			c = dao.queryBlackList(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	

	public PageLoader queryBlankTransaction(QueryBlankTransactionInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Bill_BlankTransaction");

		try
		{
			c = dao.queryBlankTransaction(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public PageLoader queryDraftTransaction(QueryDraftTransactionInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("v_bill_transaction");

		try
		{
			c = dao.queryDraftTransaction(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	public PageLoader queryBlankVoucherTracing(QueryTracingInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Bill_BlankVoucher");

		try
		{
			c = dao.queryBlankVoucherTracing(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public PageLoader queryBillTracing(QueryTracingInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao("Loan_DiscountContractBill");

		try
		{
			c = dao.queryBillTracing(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

}