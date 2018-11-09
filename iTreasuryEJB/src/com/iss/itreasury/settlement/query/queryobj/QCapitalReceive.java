/*
 * Created on 2004-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QCapitalReceiveConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCapitalReceiveResultInfo;

/**
 * @author gqfang
 * 
 * 本报表的数据来源由1.结算交易,2.台账,3.科目 三部分构成
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QCapitalReceive  extends BaseQueryObject
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public QCapitalReceive()
    {
        setUseMaxID();
    }
    
    /**1.从交易取数据**/
    //视图字段名
    /**
     *  consignGathering,委托收款栏位(数据来源于科目)：
     * 系统将根据科目名称与客户资料中描述的名称的完全对应来进行取数！
     * 所以将"委托收款"也统计到fields数组里面
     * **/
    String[] fields = {"drawing","consignLoan","trustLoan","discount","consignDeposit","consignGathering"};
    //对应的账户类型
    long[] lAccountTypes = {1,9,8,10,4,99};
    //对应的交易方向
    long[] lDirections = {2,2,2,2,2,99};
    
    //委托收款
    String subjectCode = "21300201";
    String clientName  = " 国家税务总局征收局";
    
    /**2.从台账取数据**/
    String[] fieldsWasteBook = {"discountReceive","capitalRePurchase","bankReceive","currency","securities","business"};
    long[] lDirectionsWasteBook = {1,1,1,1,1,1};
    
    
    /**3.从科目取数据**/
    String[] generalLedger = {"longInvestBack","depositPrepareBack","businessReceive"};
    
    //条件子句
    String sClientIdFrom = "";
    String sClientIdTo = "";
    String sDateFrom = "";
    String sDateTo = "";
    
    /**
     * 取得总账的发生额
     * @param conditionInfo
     * @return
     * @throws Exception
     */
    public Collection findGLByCondition(QCapitalReceiveConditionInfo conditionInfo) throws Exception
	{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector v = new Vector();

        Timestamp dateFrom = conditionInfo.getDateFrom();
        Timestamp dateTo = conditionInfo.getDateTo();
        
        
       
        StringBuffer sbSQL = new StringBuffer();
        
        sbSQL.append(" select -1 clientId ");
        for(int i=0;i<generalLedger.length;i++)
        {
        	sbSQL.append(",sum("+generalLedger[i]+") "+generalLedger[i]);
        }
        
        sbSQL.append("\n from");
		sbSQL.append("\n (");
        
        sbSQL.append( getUnionItemGL(generalLedger[0]) );
        
        for(int i=1;i<generalLedger.length;i++)
        {
        	sbSQL.append("\n union");
        	sbSQL.append( getUnionItemGL(generalLedger[i]) );
        }
        
        sbSQL.append("\n )");
        sbSQL.append("\n group by clientId ");
        log4j.debug(sbSQL.toString());
        
        try
        {
            conn = this.getConnection();
        	pstmt = conn.prepareStatement(sbSQL.toString());
        	QCapitalReceiveResultInfo resultInfo = null;
       
            rs = pstmt.executeQuery();
            
            while( rs != null && rs.next())
            {
            	resultInfo = new QCapitalReceiveResultInfo();
            	
            	resultInfo.setClientId(rs.getLong("clientId"));
            	
            	resultInfo.setLongInvestBack(rs.getDouble("longInvestBack"));
            	
            	resultInfo.setDepositPrepareBack(rs.getDouble("depositPrepareBack"));
            	
            	resultInfo.setBusinessReceiveGL(rs.getDouble("businessReceive"));
            	
            	v.add(resultInfo);
            }
        } 
        finally
        {
        	this.cleanup(rs);
            this.cleanup(pstmt);
            this.cleanup(conn);

        }
        return v.size() > 0 ? v : null;	
    }
    public Collection findByCondition(QCapitalReceiveConditionInfo conditionInfo) throws Exception
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector v = new Vector();

        long clientIdFrom = conditionInfo.getClientIdFrom();
        long clientIdTo = conditionInfo.getClientIdTo();
        Timestamp dateFrom = conditionInfo.getDateFrom();
        Timestamp dateTo = conditionInfo.getDateTo();
        
        if(clientIdFrom != -1)
        {
        	sClientIdFrom = "\n and client.id >= "+clientIdFrom;
        }
        if(clientIdTo != -1)
        {
        	sClientIdTo = "\n and client.id <= "+clientIdTo;
        }
        
        if(dateFrom != null)
        {
        	sDateFrom = "to_date('"
        	+ DataFormat.getDateString(dateFrom)
            + "','yyyy-mm-dd')";
        }
        if(dateTo != null)
        {
        	sDateTo = "to_date('"
            	+ DataFormat.getDateString(dateTo)
                + "','yyyy-mm-dd')";
        }
        
        
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select clientId,scounterPartNo");
        for(int i=0;i<fields.length;i++)
        {
          sbSQL.append(",sum("+fields[i]+") "+fields[i]);
        }
        for(int i=0;i<fieldsWasteBook.length;i++)
        {
          sbSQL.append(",sum("+fieldsWasteBook[i]+") "+fieldsWasteBook[i]);
        }
		sbSQL.append("\n from");
		sbSQL.append("\n (");
        
        sbSQL.append(getUnionItem(fields[0],lAccountTypes[0],lDirections[0]));
        
        for(int i=1;i<fields.length;i++)
        {
        	sbSQL.append("\n union");
        	sbSQL.append(getUnionItem(fields[i],lAccountTypes[i],lDirections[i]));
        }
        
        for(int j = 0;j<fieldsWasteBook.length;j++)
        {
        	sbSQL.append("\n union ");
        	sbSQL.append(getUnionItem( fieldsWasteBook[j]) );
        }
        
        sbSQL.append("\n )");
        sbSQL.append("\n group by clientId,scounterPartNo");
        
        log4j.debug(sbSQL.toString());
        try
        {
            conn = this.getConnection();
        	pstmt = conn.prepareStatement(sbSQL.toString());
        	QCapitalReceiveResultInfo resultInfo = null;
       
            rs = pstmt.executeQuery();
            while( rs != null && rs.next())
            {
            	resultInfo = new QCapitalReceiveResultInfo();
            	
            	
            	resultInfo.setClientId(rs.getLong("clientId"));
            	
            	resultInfo.setCounterPartNo(rs.getString("scounterPartNo"));
            	
            	resultInfo.setCorpDeposit(rs.getDouble("drawing"));//企业存款
            	
            	resultInfo.setTrustReceive(rs.getDouble("trustLoan"));//自营贷款还款
            	
            	resultInfo.setConsignReceive(rs.getDouble("consignLoan"));//委托贷款收回
            	
            	resultInfo.setDiscountBack(rs.getDouble("discount"));//贴现收回
            	
            	resultInfo.setConsignDeposit(rs.getDouble("consignDeposit"));//委托存款
            	
            	resultInfo.setDiscountReceive(rs.getDouble("discountReceive"));//转贴现进款
            	
            	resultInfo.setCapitalReceive(rs.getDouble("capitalRePurchase"));//资产回购进款
            	
            	resultInfo.setBankReceive(rs.getDouble("bankReceive"));//同业拆借进款
            	
            	resultInfo.setCurrencyInvestBack(rs.getDouble("currency"));//货币投资收回
            	
            	resultInfo.setSecuritiesInvestBack(rs.getDouble("securities"));//证券投资收回
            	
            	resultInfo.setConsignGathering(rs.getDouble("consignGathering"));//委托收款
            	
            	resultInfo.setBusinessReceive(rs.getDouble("business"));//业务收入(台账)
            	
            	//resultInfo.setBusinessReceiveGL(rs.getDouble("businessReceive"));//业务收入（金融企业往来收入）
            	
            	//resultInfo.setBusinessReceive(resultInfo.getBusinessReceive() + resultInfo.getBusinessReceiveGL());//业务收入(台账 + 金融企业往来收入)
            
            	
            	//合计
            	resultInfo.setTotalAmount(resultInfo.getCorpDeposit()
            		              	    + resultInfo.getTrustReceive() 
            			                + resultInfo.getConsignReceive() 
            			                + resultInfo.getDiscountBack()
										+ resultInfo.getConsignDeposit()
										+ resultInfo.getDiscountReceive()
										+ resultInfo.getCapitalReceive()
										+ resultInfo.getBankReceive()
										+ resultInfo.getCurrencyInvestBack()
										+ resultInfo.getSecuritiesInvestBack()
										+ resultInfo.getConsignGathering()
										+ resultInfo.getBusinessReceive());
            	v.add(resultInfo);
            }
        } 
        finally
        {
        	this.cleanup(rs);
            this.cleanup(pstmt);
            this.cleanup(conn);

        }
        return v.size() > 0 ? v : null;	
    }
    
    /**从台账中取数据**/
    private String getUnionItem(String singleField)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select -1 clientId,scounterPartNo");
    	for(int i=0;i<fields.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields[i]);
    	}
    	for(int i=0;i<fieldsWasteBook.length;i++)
    	{
    	  if(singleField.equalsIgnoreCase(fieldsWasteBook[i]))
    	  {
    	     sbResult.append(",sum(amount1) " + fieldsWasteBook[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fieldsWasteBook[i]);
    	  }
    	}
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select -1 clientId,scounterPartNo,amount1");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(singleField.equalsIgnoreCase("discountReceive"))
    	{
    		//转贴现进款
    		sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mDiscountPayAmount amount1");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.BUYBREAK );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
			 
	    	sbResult.append("\n union ");
			
	    	sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mBillAmount amount1");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n   and (");
			
			sbResult.append("\n           (");
			sbResult.append("\n               sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n               and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n           )");
			sbResult.append("\n        or ( ");
			sbResult.append("\n               sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.IN );
			sbResult.append("\n               and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.BUYBREAK );
			sbResult.append("\n            )");
			
			sbResult.append("\n       )");
			sbResult.append("\n   and Z_nIsRepayment = " + Constant.YesOrNo.YES);
			
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate <="+sDateTo);
	    	}
			
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mBillAmount amount1");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.IN );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n   and  JQ_bBought = " + Constant.YesOrNo.YES);
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
    	}
    	else if(singleField.equalsIgnoreCase("capitalRePurchase"))
    	{
    		//资产回购进款
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
    		sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , D_mBuyAmount amount1 from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN);
	    	sbResult.append("\n   and  JQ_bBought =  "+ Constant.YesOrNo.YES);
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtBuyDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtBuyDate <="+sDateTo);
	    	}
	    	
	    	
	    
    	}
    	else if(singleField.equalsIgnoreCase("bankReceive"))
    	{
    		//同业拆借进款
    		sbResult.append("\n select sCounterpartNo , C_mBorrowAmount amount1 from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 ");
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
		    sbResult.append("\n select sCounterpartNo , C_mPayAmount amount1 from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 2 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
    		
    		
    	}
    	else if(singleField.equalsIgnoreCase("currency"))
    	{
    		//货币投资收回
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH
    		                                                                         +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId in ("+LOANConstant.TransDiscountInOrOut.OUT +",1)");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'金融债'"+","+"'央行票据'"+","+"'政策性金融债'"+","+"'货币市场基金'"+","+"'银行间国债'"+")");
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}

    	}
    	else if(singleField.equalsIgnoreCase("securities"))
    	{
    		//证券投资收回
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where ");
    		sbResult.append("\n  (");
    		sbResult.append("\n    ( sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH+")");
    		sbResult.append("\n      and sett_transinvestment.nTransactoinTypeId  = "+LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n    ) or ( sett_transinvestment.nTransInvestmentTypeId = "+SETTConstant.InvestTransactionType.ZQHG_JYS);
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1 )");
    		sbResult.append("\n  )");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'股票'"+","+"'交易所间国债'"+","+"'开放式基金'"+","+"'封闭式基金'"+","+"'企业债'"+","+"'可转债'"+")");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd <="+sDateTo);
	    	}

    		
    	}
    	else if(singleField.equalsIgnoreCase("business"))
    	{
    		//业务收入 
    		
    		/**转贴现台账**/	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.ZTX );
	        sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN );
	        sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	        if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**贷款回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n    and  sett_transinvestment.JQ_bBought = 1" );
	    	sbResult.append("\n    and  sett_transinvestment.nTransactoinTypeId = 1" );
	    	sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtBuyDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtBuyDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**债券回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( "+ SETTConstant.InvestTransactionType.ZQHG_JYS 
    		                                                                        +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    	}
		
    	
    	sbResult.append("\n )");
    	
		//sbResult.append("\n where client.id=clientId1 (+)");
    	
    	sbResult.append(sClientIdFrom);
    	sbResult.append(sClientIdTo);
		
    	sbResult.append("\n )");
		
    	sbResult.append("\n group by clientId,scounterPartNo");
		 
    	return sbResult.toString();
    }   
    private String getUnionItemGL(String sField)
	{
    	StringBuffer sbResult = new StringBuffer();
    	
    	sbResult.append("\n select -1 clientId ");
    	for(int i=0;i<generalLedger.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(generalLedger[i]))
    	  {
    	      sbResult.append(",sum(amount1) " + generalLedger[i]);
    	  }
    	  else
    	  {
    	  	  sbResult.append(",0 as " + generalLedger[i]);
    	  }
    	}
    	 
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select -1 clientId, amount1");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		if(sField.equalsIgnoreCase("longInvestBack"))
    	{
    		sbResult.append("\n select -1 clientId, sett_glbalance.mCreditamount amount1 from  sett_glbalance ");
		    sbResult.append("\n   where substr(sett_glbalance.sglsubjectcode,0,3) = '151'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	} 
    	}
		else if(sField.equalsIgnoreCase("depositPrepareBack"))
		{

    		sbResult.append("\n select -1 clientId, sett_glbalance.mCreditamount amount1 from  sett_glbalance ");
		    sbResult.append("\n   where sett_glbalance.sglsubjectcode = '109001'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	} 
    	
		}
		else if(sField.equalsIgnoreCase("businessReceive"))
		{

    		sbResult.append("\n select -1 clientId, sett_glbalance.mCreditamount amount1 from  sett_glbalance ");
		    sbResult.append("\n   where substr(sett_glbalance.sglsubjectcode,0,3) = '508'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	/**转贴现台账**/	    	
	    	sbResult.append("\n select -1 clientId, mInterestAmount amount1 from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.ZTX );
	        sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN );
	        sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	        if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**贷款回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select -1 clientId, mInterestAmount amount1 from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n    and  sett_transinvestment.JQ_bBought = 1" );
	    	sbResult.append("\n    and  sett_transinvestment.nTransactoinTypeId = 1" );
	    	sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtBuyDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtBuyDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**债券回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select -1 clientId, mInterestAmount amount1 from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( "+ SETTConstant.InvestTransactionType.ZQHG_JYS 
    		                                                                        +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
	    	
    	
		}
    	sbResult.append("\n )");
    	sbResult.append("\n )");
		
    	sbResult.append("\n group by clientId ");
    	
    	return sbResult.toString();
    	
    }
    private String getUnionItem(String sField,long lType,long lDirection)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select clientId,null scounterPartNo");
    	for(int i=0;i<fields.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(fields[i]))
    	  {
    	     sbResult.append(",sum(amount1) " + fields[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fields[i]);
    	  }
    	}
    	for(int i=0;i<fieldsWasteBook.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook[i]);
    	}
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select client.id clientId,null scounterPartNo,amount1");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
    	if(!sField.equalsIgnoreCase("consignGathering"))//如果不是委托收款
    	{

			sbResult.append("\n select sett_account.nclientid clientId1,sett_transaccountdetail.mamount amount1");
			sbResult.append("\n from sett_account,sett_transaccountdetail");
			sbResult.append("\n where sett_account.id=sett_transaccountdetail.ntransaccountid (+)");
			if(sField.equalsIgnoreCase("drawing"))
	    	{
	    		//活期，定期，通知存款
	    		sbResult.append("\n and sett_account.naccounttypeid in (1,2,3)");  
	    	}
	    	else
	    	{
	    		sbResult.append("\n and sett_account.naccounttypeid="+lType);
	    	}
			
	    	sbResult.append("\n and sett_transaccountdetail.ntransdirection="+lDirection);
			
	    	sbResult.append("\n and sett_transaccountdetail.nstatusid>=3");
	    	
	    	if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n and sett_transaccountdetail.dtexecute>="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n and sett_transaccountdetail.dtexecute<="+sDateTo);
	    	}
    	}
    	else
    	{
			sbResult.append("\n  select client.id clientId1 , sett_glbalance.mCreditAmount amount1  ");
			sbResult.append("\n  from sett_glbalance,client ");
			sbResult.append("\n  where sett_glbalance.sglsubjectcode = "+"'"+subjectCode+"'");
			sbResult.append("\n    and client.sname  = "+"'"+clientName+"'");
			if(sDateFrom.length()>0)
			{
			  sbResult.append("\n   and sett_glbalance.dtGLDate >= "+sDateFrom);
			}
			if(sDateTo.length()>0)
			{
				sbResult.append("\n and sett_glbalance.dtGLDate <= "+sDateTo);
			}
    	}
    	sbResult.append("\n ),client");
    	
		sbResult.append("\n where client.id=clientId1 (+)");
    	
    	sbResult.append(sClientIdFrom);
    	sbResult.append(sClientIdTo);
		
    	sbResult.append("\n )");
		
    	sbResult.append("\n group by clientId");
		 
    	return sbResult.toString();
    }
}
