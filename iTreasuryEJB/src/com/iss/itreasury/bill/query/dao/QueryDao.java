package com.iss.itreasury.bill.query.dao;

import com.iss.itreasury.bill.query.dataentity.QueryBillInfo;
import com.iss.itreasury.bill.query.dataentity.QueryBillTypeInfo;
import com.iss.itreasury.bill.query.dataentity.QueryBlackListInfo;
import com.iss.itreasury.bill.query.dataentity.QueryBlankTransactionInfo;
import com.iss.itreasury.bill.query.dataentity.QueryBlankVoucherInfo;
import com.iss.itreasury.bill.query.dataentity.QueryDraftTransactionInfo;
import com.iss.itreasury.bill.query.dataentity.QueryTracingInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class QueryDao extends BillDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.BILL, this);

	public QueryDao(String table) 
	{
		super(table);
	}

	public static void main(String args[])
	{
		QueryDao dao = new QueryDao("123");
//		QueryBillTypeInfo qInfo = new QueryBillTypeInfo();
//		QueryBlankVoucherInfo qInfo = new QueryBlankVoucherInfo();
		QueryBillInfo qInfo = new QueryBillInfo();
//		QueryBlankListInfo qInfo = new QueryBlankListInfo();
		
		long lReturn = -1;
		try
		{
//			BillTypeInfo[] queryResults = null;
//			queryResults = (BillTypeInfo[])
//			dao.queryBillType(qInfo);
//			dao.queryBlankVoucher(qInfo);
			dao.queryBill(qInfo);
//			dao.queryBlackList(qInfo);
			

		}
		catch (Exception e)
		{
			Log.print("erro");
		}
	}
	
	public String getOrderSQL(String orderParamString, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();

		if ((orderParamString != null) && (orderParamString.length() > 0))
			oBuf.append(" \n order by "+ orderParamString + strDesc);
				
		return (oBuf.toString());
	}

	private String[] getBillTypeSQL(QueryBillTypeInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " ID as subjectID,code,name,bank,abstractTypeID,subjectCode,inputUserID,inputDate,statusID,officeID,currencyID ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_BillType ";

		//where
		sql[2] = " statusID=1 ";

		/**********处理查询条件*************/

		//编号开始
		if ((qInfo.getStartCode() != null) && (qInfo.getStartCode().length() > 0))
			sqlBuf.append(" and code >= '" + qInfo.getStartCode() + "'");

		//编号结束
		if ((qInfo.getEndCode() != null) && (qInfo.getEndCode().length() > 0))
			sqlBuf.append(" and code <='" + qInfo.getEndCode() + "'");

		//AbstractTypeID
		if (qInfo.getAbstractTypeID() > 0)
			sqlBuf.append(" and AbstractTypeID = " + qInfo.getAbstractTypeID());

		//bank
		if ((qInfo.getBank() != null) && (qInfo.getBank().length() > 0))
			sqlBuf.append(" and bank = '" + qInfo.getBank() + "'");

		//日期开始
		if (qInfo.getStartDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartDate()) 
				+ "','yyyy-mm-dd') ");

		//日期结束
		if (qInfo.getEndDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndDate()) 
				+ "','yyyy-mm-dd') ");

		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}

	public PageLoader queryBillType(QueryBillTypeInfo qInfo) throws Exception
	{
		String[] sql = this.getBillTypeSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.billtype.dataentity.BillTypeInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getBlankVoucherSQL(QueryBlankVoucherInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " Id,code,statusID,inputUserID,inputDate,billTypeID,inPrice,outPrice,useClientID,useAccountID,Password,useUser,officeID,currencyID,inDate,outDate ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_BlankVoucher ";

		//where
		sql[2] = " 1=1 ";

		/**********处理查询条件*************/

		//BillTypeID
		if (qInfo.getBillTypeID() > 0)
			sqlBuf.append(" and billTypeID = " + qInfo.getBillTypeID());

		//编号开始
		if ((qInfo.getStartBillCode() != null) && (qInfo.getStartBillCode().length() > 0))
			sqlBuf.append(" and code >= '" + qInfo.getStartBillCode() + "'");

		//编号结束
		if ((qInfo.getEndBillCode() != null) && (qInfo.getEndBillCode().length() > 0))
			sqlBuf.append(" and code <= '" + qInfo.getEndBillCode() + "'");

		//BillTypeID
		if (qInfo.getStatusID() > 0)
			sqlBuf.append(" and statusID = " + qInfo.getStatusID());

		//日期开始
		if (qInfo.getStartDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartDate()) 
				+ "','yyyy-mm-dd') ");

		//日期结束
		if (qInfo.getEndDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndDate()) 
				+ "','yyyy-mm-dd') ");

		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}
	//*******************************************************************************
	private String[] getDraftTransactionSQL(QueryDraftTransactionInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " * ";

		//from
		sql[1] = " v_bill_transaction ";

		//where
		sql[2] = " 1=1 and officeId="+qInfo.getOfficeId()+" and currencyId="+qInfo.getCurrencyId();

		/**********处理查询条件*************/
		if (qInfo.getDraftTypeID() > 0){
			sqlBuf.append(" and NDraftTypeID = " + qInfo.getDraftTypeID());
		}
		if(qInfo.getTransTypeID() > 0){
			sqlBuf.append(" and TransTypeID = " + qInfo.getTransTypeID());
		}
		//编号开始
		if ((qInfo.getStartBillCode() != null) && (qInfo.getStartBillCode().length() > 0))
			sqlBuf.append(" and billcode >= '" + qInfo.getStartBillCode() + "'");

		//编号结束
		if ((qInfo.getEndBillCode() != null) && (qInfo.getEndBillCode().length() > 0))
			sqlBuf.append(" and billcode <= '" + qInfo.getEndBillCode() + "'");

		
		if (qInfo.getStatusID() > 0)
			sqlBuf.append(" and statusID = " + qInfo.getStatusID());

		//日期开始
		if (qInfo.getStartInputDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartInputDate()) 
				+ "','yyyy-mm-dd') ");

		//日期结束
		if (qInfo.getEndInputDate() != null)
			sqlBuf.append(" and TRUNC(inputDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndInputDate()) 
				+ "','yyyy-mm-dd') ");

		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");


		return sql;
		
	}
	public PageLoader queryDraftTransaction(QueryDraftTransactionInfo qInfo) throws Exception
	{
		String[] sql = this.getDraftTransactionSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.draft.dataentity.TransDraftInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	
	//*******************************************************************************
	
	public PageLoader queryBlankVoucher(QueryBlankVoucherInfo qInfo) throws Exception
	{
		String[] sql = this.getBlankVoucherSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.blankvoucher.dataentity.BlankVoucherInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	private String[] getBillSQL(QueryBillInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " Id,sCode,nDraftTypeID,dtEnd,nvl(strAcceptorName,sBank) strAcceptorName,mAmount" +
			",dtCreate,nStatusID,nModuleSourceID,strAcceptorBank,strAcceptorAccount" +
			",nStorageStatusID,nStorageTransID,nQueryStatusID,nConsignStatusID" +
			",nConsignTime,nInputUserID,dtInputDate,nModifyUserID,dtModifyDate,nbillsourcetypeid,nbillstatusid ";

//		sql[0] = " * ";

		//from
		sql[1] = " Loan_DiscountContractBill ";

		//where
		sql[2] = " 1=1 and nStatusID != 0";

		/**********处理查询条件*************/

		//nDraftTypeID
		if (qInfo.getDraftTypeID() > 0)
			sqlBuf.append(" and nDraftTypeID = " + qInfo.getDraftTypeID());

		//编号开始
		if ((qInfo.getStartBillCode() != null) && (qInfo.getStartBillCode().length() > 0))
			sqlBuf.append(" and sCode >= '" + qInfo.getStartBillCode() + "'");

		//编号结束
		if ((qInfo.getEndBillCode() != null) && (qInfo.getEndBillCode().length() > 0))
			sqlBuf.append(" and sCode <= '" + qInfo.getEndBillCode() + "'");

		//end日期开始
		if (qInfo.getStartEndDate() != null)
			sqlBuf.append(" and TRUNC(dtEnd) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartEndDate()) 
				+ "','yyyy-mm-dd') ");

		//end日期结束
		if (qInfo.getEndEndDate() != null)
			sqlBuf.append(" and TRUNC(dtEnd) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndEndDate()) 
				+ "','yyyy-mm-dd') ");

		//mAmount开始
		if (qInfo.getStartAmount() > 0)
			sqlBuf.append(" and mAmount >= " 
				+ qInfo.getStartAmount() );

		//mAmount结束
		if (qInfo.getEndAmount() > 0)
			sqlBuf.append(" and mAmount <= " 
				+ qInfo.getEndAmount() );
		


		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}





	public PageLoader queryBill(QueryBillInfo qInfo) throws Exception
	{
		String[] sql = this.getBillSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	public PageLoader queryBill1(QueryBillInfo qInfo) throws Exception
	{
		String[] sql = this.getBillSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.query.dataentity.QueryDiscountContractBillInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	public double queryBill1_SumAmount(QueryBillInfo qInfo)throws ITreasuryDAOException{
		double amount = 0.00;
		
		try{
			initDAO();
			String[] sql = this.getBillSQL(qInfo);
			String strSQL = "select" + " sum(mAmount) sumAmount" + " from " + sql[1] + " where " + sql[2] + sql[3];
			this.prepareStatement(strSQL);
			this.executeQuery();
			if(transRS != null && transRS.next()){
				amount = transRS.getDouble("sumAmount");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_001", e);
		}finally{
			finalizeDAO();
		}
		return amount;
	}

	private String[] getBlackListSQL(QueryBlackListInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " Id,billCode,statusID,inputUserID,inputDate,createDate,maturityDate,billTypeID,acceptor,billAmount ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_BlackList ";

		//where
		sql[2] = " 1=1 ";

		/**********处理查询条件*************/

		//BillTypeID
		if (qInfo.getBillTypeID() > 0)
			sqlBuf.append(" and billTypeID = " + qInfo.getBillTypeID());

		//编号开始
		if ((qInfo.getStartBillCode() != null) && (qInfo.getStartBillCode().length() > 0))
			sqlBuf.append(" and billCode >= '" + qInfo.getStartBillCode() + "'");

		//编号结束
		if ((qInfo.getEndBillCode() != null) && (qInfo.getEndBillCode().length() > 0))
			sqlBuf.append(" and billCode <= '" + qInfo.getEndBillCode() + "'");
         
		//StatusID
		if(qInfo.getStatusID() == -1)
		    sqlBuf.append(" and statusID!=0 " ); 
		if (qInfo.getStatusID() > 0)
			sqlBuf.append(" and statusID = " + qInfo.getStatusID());

		//Create日期开始
		if (qInfo.getStartCreateDate() != null)
			sqlBuf.append(" and TRUNC(createDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartCreateDate()) 
				+ "','yyyy-mm-dd') ");

		//Create日期结束
		if (qInfo.getEndCreateDate() != null)
			sqlBuf.append(" and TRUNC(createDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndCreateDate()) 
				+ "','yyyy-mm-dd') ");

		//Maturity日期开始
		if (qInfo.getStartMaturityDate() != null)
			sqlBuf.append(" and TRUNC(MaturityDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartMaturityDate()) 
				+ "','yyyy-mm-dd') ");

		//Maturity日期结束
		if (qInfo.getEndMaturityDate() != null)
			sqlBuf.append(" and TRUNC(MaturityDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndMaturityDate()) 
				+ "','yyyy-mm-dd') ");

		//
		if ((qInfo.getAcceptor() != null) && (qInfo.getAcceptor().length() > 0))
			sqlBuf.append(" and Acceptor = '" + qInfo.getAcceptor() + "'");

		//BillAmount开始
		if (qInfo.getStartAmount() > 0)
			sqlBuf.append(" and BillAmount >= " 
				+ qInfo.getStartAmount() );

		//BillAmount结束
		if (qInfo.getEndAmount() > 0)
			sqlBuf.append(" and BillAmount <= " 
				+ qInfo.getEndAmount() );


		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}



	public PageLoader queryBlackList(QueryBlackListInfo qInfo) throws Exception
	{
		String[] sql = this.getBlackListSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.query.dataentity.BlackListInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	private String[] getBlankTransactionSQL(QueryBlankTransactionInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " Id,transTypeID,transCode,statusID,inputUserID,inputDate,checkUserID,checkDate,transDetailID,summary,currencyID,officeID,executeDate,interestStartDate ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_BlankTransaction ";

		//where
		sql[2] = " 1=1 ";

		/**********处理查询条件*************/

		//BillTypeID
		if (qInfo.getTransTypeID() > 0)
			sqlBuf.append(" and transTypeID = " + qInfo.getTransTypeID());

		//编号开始
		if ((qInfo.getStartTransCode() != null) && (qInfo.getStartTransCode().length() > 0))
			sqlBuf.append(" and transCode >= '" + qInfo.getStartTransCode() + "'");

		//编号结束
		if ((qInfo.getEndTransCode() != null) && (qInfo.getEndTransCode().length() > 0))
			sqlBuf.append(" and transCode <= '" + qInfo.getEndTransCode() + "'");

		//StatusID
		if (qInfo.getStatusID() > 0)
			sqlBuf.append(" and statusID = " + qInfo.getStatusID());

		//Input日期开始
		if (qInfo.getStartInputDate() != null)
			sqlBuf.append(" and TRUNC(InputDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartInputDate()) 
				+ "','yyyy-mm-dd') ");

		//Input日期结束
		if (qInfo.getEndInputDate() != null)
			sqlBuf.append(" and TRUNC(InputDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndInputDate()) 
				+ "','yyyy-mm-dd') ");


		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}




	public PageLoader queryBlankTransaction(QueryBlankTransactionInfo qInfo) throws Exception
	{
		String[] sql = this.getBlankTransactionSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.blankvoucher.dataentity.BlankTransactionInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	private String[] getBlankVoucherTracingSQL(QueryTracingInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " a.Id,b.code as billCode,a.actionID,a.OperatorID,a.OperateDate,a.DetailID,b.inputUserID as operatorUserID ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_Tracing a,Bill_BlankVoucher b ";

		//where
		sql[2]  = " 1=1 ";
		sql[2] += " and a.DetailID = b.Id ";
		sql[2] += " and a.BillModuleID = " + BILLConstant.TraceModule.BLANKVOUCHER;

		/**********处理查询条件*************/

		//编号
		if ((qInfo.getBillCode() != null) && (qInfo.getBillCode().length() > 0))
			sqlBuf.append(" and b.code = '" + qInfo.getBillCode() + "'");

		//ActionID
		if (qInfo.getActionID() > 0)
			sqlBuf.append(" and a.ActionID = " + qInfo.getActionID());

		//operatorID
		if (qInfo.getOperatorID() > 0)
			sqlBuf.append(" and a.operatorID = " + qInfo.getOperatorID());

		//OperateDate日期开始
		if (qInfo.getStartOperateDate() != null)
			sqlBuf.append(" and TRUNC(a.OperateDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartOperateDate()) 
				+ "','yyyy-mm-dd') ");

		//OperateDate日期结束
		if (qInfo.getEndOperateDate() != null)
			sqlBuf.append(" and TRUNC(a.OperateDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndOperateDate()) 
				+ "','yyyy-mm-dd') ");


		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}





	public PageLoader queryBlankVoucherTracing(QueryTracingInfo qInfo) throws Exception
	{
		String[] sql = this.getBlankVoucherTracingSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.query.dataentity.TracingInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getBillTracingSQL(QueryTracingInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " a.Id,b.scode as billCode,a.actionID,a.OperatorID,a.OperateDate,a.DetailID,b.nInputUserID as operatorUserID ";

//		sql[0] = " * ";

		//from
		sql[1] = " Bill_Tracing a,Loan_DiscountContractBill b ";

		//where
		sql[2]  = " 1=1 ";
		sql[2] += " and a.DetailID = b.Id ";
		sql[2] += " and a.BillModuleID = " + BILLConstant.TraceModule.DRAFT;

		/**********处理查询条件*************/

		//编号
		if ((qInfo.getBillCode() != null) && (qInfo.getBillCode().length() > 0))
			sqlBuf.append(" and b.scode = '" + qInfo.getBillCode() + "'");

		//ActionID
		if (qInfo.getActionID() > 0)
			sqlBuf.append(" and a.ActionID = " + qInfo.getActionID());

		//operatorID
		if (qInfo.getOperatorID() > 0)
			sqlBuf.append(" and a.operatorID = " + qInfo.getOperatorID());

		//OperateDate日期开始
		if (qInfo.getStartOperateDate() != null)
			sqlBuf.append(" and TRUNC(a.OperateDate) >= To_Date('" 
				+ DataFormat.getDateString(qInfo.getStartOperateDate()) 
				+ "','yyyy-mm-dd') ");

		//OperateDate日期结束
		if (qInfo.getEndOperateDate() != null)
			sqlBuf.append(" and TRUNC(a.OperateDate) <= To_Date('" 
				+ DataFormat.getDateString(qInfo.getEndOperateDate()) 
				+ "','yyyy-mm-dd') ");


		sql[2] = sql[2] + sqlBuf.toString();

		//order
		sql[3] = this.getOrderSQL(qInfo.getOrderParamString(),qInfo.getDesc());
		
		log.print("get QuerySQL:\n select " + sql[0] + "\n from " + sql[1] + "\n where " + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}

	public PageLoader queryBillTracing(QueryTracingInfo qInfo) throws Exception
	{
		String[] sql = this.getBillTracingSQL(qInfo);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), 
								  sql[1], 
								  sql[0], 
								  sql[2], 
								  (int) Constant.PageControl.CODE_PAGELINECOUNT, 
								  "com.iss.itreasury.bill.query.dataentity.TracingInfo", 
								  null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}


}