/*
 * Created on 2004-12-09
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
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QCapitalDetailConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCapitalDetailResultInfo;


/**
 * @author gqfang
 * 
 * 本报表的数据来源由1.结算交易,2.台账,3.科目 三部分构成
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QCapitalDetail extends SettlementDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public QCapitalDetail()
    {
        setUseMaxID();
    }
    
    /**1.从交易取数据**/
    //视图字段名
    String[] fields1 = {"drawing1","consignLoan1","trustLoan1","discount1","consignDeposit","consignGathering"}; //资金来源(QCapitalReceive)
    String[] fields2 = {"drawing2","consignLoan2","trustLoan2","discount2","consignPay"};                        //资金用途(QCapitalPayment)
    
    //委托收/付款
    String subjectCode = "21300201";
    String clientName  = " 国家税务总局征收局";
    
    //账户类型
    long[] lAccountTypes1 = {1,9,8,10,4,99};   //资金来源
    long[] lAccountTypes2 = {1,9,8,10,99};     //资金用途
    
    //交易方向
    long[] lDirections1 = {2,2,2,2,2,99};      //资金来源
    long[] lDirections2 = {1,1,1,1,99};        //资金用途
    
    /**2.从台账取数据**/
    String[] fieldsWasteBook1 = {"discountReceive","capitalRePurchase","bankReceive","currency1","securities1","business1"}; //资金来源  business1：业务收入
    String[] fieldsWasteBook2 = {"discountPay","loanRepurchase","bankPay","securities2","currency2","business2"}; //资金用途  business2:业务支出
   
    
    /**3.从科目取数据**/
    String[] fieldsSubject    = {"startBalance","depositPrepare","assureAmount","longInvestBack","businessReceive","longInvest","otherPay"};
    
    //条件子句
    String sClientId = "";
    String sDateFrom = "";
    String sDateTo = "";
    String sAmount  = "";
    String sDateFromPre = "";
    
    /**就全部客户进行查询**/
    public Collection findAllByCondition(QCapitalDetailConditionInfo conditionInfo) throws Exception
	{
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector v = new Vector();
        
        long clientType    = conditionInfo.getClientType();
        Timestamp dateFrom = conditionInfo.getDateFrom();
        Timestamp dateTo   = conditionInfo.getDateTo();
        
        //查询日期起始日的前一天（取期初余额用）
        sDateFromPre  = DataFormat.formatDate( IDate.before(conditionInfo.getDateFrom(),1),1 );
        
        sDateFromPre  = "to_date('"
        	          + sDateFromPre
                      + "','yyyy-mm-dd')";
		
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
        sbSQL.append(" select  executeDate");
       
	    for(int i=0;i<fields1.length;i++)
	    {
	        //从交易取数据 1
	    	sbSQL.append(",sum("+fields1[i]+") "+fields1[i]);
	    }
	    for(int i=0;i<fields2.length;i++)
	    {
	        //从交易取数据 2
	    	sbSQL.append(",sum("+fields2[i]+") "+fields2[i]);
	    }
   
    	 for(int i=0;i<fieldsWasteBook1.length;i++)
         {
             //从台账取数据 1
    	 	sbSQL.append(",sum("+fieldsWasteBook1[i]+") "+fieldsWasteBook1[i]);
         }
         for(int i=0;i<fieldsWasteBook2.length;i++)
         {
             //从台账取数据 2
         	sbSQL.append(",sum("+fieldsWasteBook2[i]+") "+fieldsWasteBook2[i]);
         }
         for(int i=0;i<fieldsSubject.length;i++)
         {
             //从科目取数据
         	sbSQL.append(",sum("+fieldsSubject[i]+") "+fieldsSubject[i]);
         }
       
         sbSQL.append("\n from");
 		 sbSQL.append("\n (");
         
         
         
 	        
         	sbSQL.append(getAllUnionItemTran1(fields1[0],lAccountTypes1[0],lDirections1[0]));
             
         	for(int i=1;i<fields1.length;i++)
 	        {
 	        	 //从交易取数据 1
 	        	sbSQL.append("\n union");
 	        	sbSQL.append(getAllUnionItemTran1(fields1[i],lAccountTypes1[i],lDirections1[i]));
 	        }
 	        
 	        for(int i=0;i<fields2.length;i++)
 	        {
 	        	 //从交易取数据 2
 	        	sbSQL.append("\n union");
 	        	sbSQL.append(getAllUnionItemTran2(fields2[i],lAccountTypes2[i],lDirections2[i]));
 	        }
            
 	        //sbSQL.append("\n union");
         	//sbSQL.append(getAllUnionItemBook1( fieldsWasteBook1[0],conditionInfo.getCounterpartNo()) );
         	
         	for(int j = 0;j<fieldsWasteBook1.length;j++)
 	        {
 	        	//从台账取数据 1
 	        	sbSQL.append("\n union ");
 	        	sbSQL.append(getAllUnionItemBook1( fieldsWasteBook1[j],conditionInfo.getCounterpartNo()) );
 	        }
 	        
 	        for(int j = 0;j<fieldsWasteBook2.length;j++)
 	        {
 	        	//从台账取数据 2
 	        	sbSQL.append("\n union ");
 	        	sbSQL.append(getAllUnionItemBook2( fieldsWasteBook2[j],conditionInfo.getCounterpartNo()) );
 	        }
 	        
 	       for(int j = 0;j<fieldsSubject.length;j++)
	        {
	        	//从科目取数据
	        	sbSQL.append("\n union ");
	        	sbSQL.append(getAllUnionItemGL( fieldsSubject[j] ) );
	        }
        
         
         sbSQL.append("\n )");
         
         sbSQL.append("\n  group by executeDate ");
         
         log4j.debug(sbSQL.toString());
         try
         {
         	 conn = this.getConnection();
         	 pstmt = conn.prepareStatement(sbSQL.toString());
         	 QCapitalDetailResultInfo resultInfo = null;
        
             rs = pstmt.executeQuery();
            
             while( rs != null && rs.next())
             {
             	
             	resultInfo = new QCapitalDetailResultInfo();
             	
             	//resultInfo.setClientId(rs.getLong("clientId"));
             	
             	//resultInfo.setCounterPartNo(rs.getString("scounterPartNo"));
             	
             	resultInfo.setExecuteDate(rs.getTimestamp("executeDate"));
             	/**期初---start**/
             	
             	resultInfo.setStartBalance(rs.getDouble("startBalance"));
             	
             	resultInfo.setDepositPrepare(rs.getDouble("depositPrepare"));
             	resultInfo.setAssureAmount(rs.getDouble("assureAmount"));
             	resultInfo.setStartUseableBalance( resultInfo.getStartBalance() - 
             			                           resultInfo.getDepositPrepare() - 
												   resultInfo.getAssureAmount());
             	
             	/**期初---end**/
             	/**资金来源---start**/
             	
             	resultInfo.setCorpDeposit(rs.getDouble("drawing1"));//企业存款
         		
         		resultInfo.setTrustReceive(rs.getDouble("trustLoan1"));//自营贷款还款
             	
             	resultInfo.setConsignReceive(rs.getDouble("consignLoan1"));//委托贷款收回
             	
             	resultInfo.setDiscountBack(rs.getDouble("discount1"));//贴现收回
             	
             	resultInfo.setConsignDeposit(rs.getDouble("consignDeposit"));//委托存款
             	
             	resultInfo.setConsignGathering(rs.getDouble("consignGathering"));  //委托收款
         	    
            	resultInfo.setDiscountReceive(rs.getDouble("discountReceive"));//转贴现进款
            	
            	resultInfo.setCapitalReceive(rs.getDouble("capitalRePurchase"));//资产回购进款
            	
            	resultInfo.setBankReceive(rs.getDouble("bankReceive"));//同业拆借进款
            	
            	resultInfo.setCurrencyInvestBack(rs.getDouble("currency1"));//货币投资收回
            	
            	resultInfo.setSecuritiesInvestBack(rs.getDouble("securities1"));//证券投资收回
            	
            	resultInfo.setLongInvestBack(rs.getDouble("longInvestBack"));//长期投资收回
            	
            	resultInfo.setBusinessReceive(rs.getDouble("business1"));//业务收入(台账)
            	
            	resultInfo.setBusinessReceiveGL(rs.getDouble("businessReceive"));//业务收入（金融企业往来收入）
            	
            	resultInfo.setBusinessReceive(resultInfo.getBusinessReceive() + resultInfo.getBusinessReceiveGL());//业务收入(台账 + 金融企业往来收入)
            	
            	resultInfo.setTotalAmount1( resultInfo.getCorpDeposit()       //资金来源小计
            			                  + resultInfo.getTrustReceive()
										  + resultInfo.getConsignReceive()
										  + resultInfo.getDiscountBack()
										  + resultInfo.getConsignDeposit()
										  + resultInfo.getConsignGathering()
										  + resultInfo.getDiscountReceive()
										  + resultInfo.getCapitalReceive()
										  + resultInfo.getBankReceive()
										  + resultInfo.getCurrencyInvestBack()
										  + resultInfo.getSecuritiesInvestBack()
										  + resultInfo.getLongInvestBack()
										  + resultInfo.getBusinessReceive());
             	
              
             	/**资金来源---end**/
             	
             	/**资金用途---start**/
             	 
            	resultInfo.setDrawing(rs.getDouble("drawing2"));//企业提款
            	
            	resultInfo.setConsignLoan(rs.getDouble("consignLoan2"));//委托贷款发放
            	
            	resultInfo.setTrustLoan(rs.getDouble("trustLoan2"));//自营贷款发放
            	
            	resultInfo.setDiscount(rs.getDouble("discount2"));//贴现发放
            	
            	resultInfo.setConsignPay(rs.getDouble("consignPay"));  //委托付款
         	 
            	resultInfo.setDiscountPay(rs.getDouble("discountPay"));//转贴现出款
            	
            	resultInfo.setLoanRepurchase(rs.getDouble("loanRepurchase"));//贷款回购出款
            	
            	resultInfo.setBankPay(rs.getDouble("bankPay"));//同业拆借出款
            	
            	resultInfo.setSecuritiesInvest(rs.getDouble("securities2"));//证券投资
            	
            	resultInfo.setCurrencyInvest(rs.getDouble("currency2"));//货币投资
            	
            	resultInfo.setBusinessPay(rs.getDouble("business2"));//业务支出
            	
            	resultInfo.setLongInvest(rs.getDouble("longInvest"));//长期投资
            	
            	resultInfo.setOtherPay(rs.getDouble("otherPay"));//其他出款
            	
            	resultInfo.setTotalAmount2( resultInfo.getDrawing() //资金用途小计
            			                  + resultInfo.getConsignLoan()
										  + resultInfo.getTrustLoan()
										  + resultInfo.getDiscount()
										  + resultInfo.getConsignPay()
										  + resultInfo.getDiscountPay()
										  + resultInfo.getLoanRepurchase()
										  + resultInfo.getBankPay()
										  + resultInfo.getSecuritiesInvest()
										  + resultInfo.getCurrencyInvest()
										  + resultInfo.getBusinessPay()
										  + resultInfo.getLongInvest()
										  + resultInfo.getOtherPay());  
            	
            	
             	 
             	/**资金用途---end**/
            	
            	/**期末---start**/
            	
            	resultInfo.setBackupAmount(0.00);    //必要备付金
            	
            	//期末余额
            	resultInfo.setEndBalance( resultInfo.getStartBalance() 
	                + (resultInfo.getTotalAmount1() - resultInfo.getTrustReceive() 
	                		 - resultInfo.getConsignReceive() - resultInfo.getDiscountBack() 
							 - resultInfo.getConsignDeposit() - resultInfo.getConsignGathering() )
					- (resultInfo.getTotalAmount2() - resultInfo.getConsignPay() - resultInfo.getConsignLoan()
							 - resultInfo.getTrustLoan() - resultInfo.getDiscount()) );          
            	
            	//期末可用余额
            	resultInfo.setEndUseableBalance( resultInfo.getStartUseableBalance()
    	                + (resultInfo.getTotalAmount1() - resultInfo.getTrustReceive() 
   	                		 - resultInfo.getConsignReceive() - resultInfo.getDiscountBack() 
   							 - resultInfo.getConsignDeposit() - resultInfo.getConsignGathering() )
   					- (resultInfo.getTotalAmount2() - resultInfo.getConsignPay() - resultInfo.getConsignLoan()
   							 - resultInfo.getTrustLoan() - resultInfo.getDiscount()) );
            	
            	//资金头寸
            	resultInfo.setCash( resultInfo.getEndUseableBalance() - resultInfo.getBackupAmount() );                
            	/**期末---end**/
             	
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
    
    public Collection findByCondition(QCapitalDetailConditionInfo conditionInfo) throws Exception
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector v = new Vector();

        long clientId = conditionInfo.getClientId();
        Timestamp dateFrom = conditionInfo.getDateFrom();
        Timestamp dateTo = conditionInfo.getDateTo();
        
        if(clientId != -1)
        {
        	sClientId = "\n and client.id = "+clientId;
        }
        
        if(conditionInfo.getClientType() == 1)
        {
        	sAmount ="\n  and amount1 >0 ";
        }
        else if (conditionInfo.getClientType() == 2)
        {
        	sAmount ="\n  where amount1 >0 ";
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
        sbSQL.append(" select clientId,scounterPartNo,executeDate");
       
        if ( conditionInfo.getClientType() != 2 )
        {
		    for(int i=0;i<fields1.length;i++)
		    {
		        //从交易取数据 1
		    	sbSQL.append(",sum("+fields1[i]+") "+fields1[i]);
		    }
		    for(int i=0;i<fields2.length;i++)
		    {
		        //从交易取数据 2
		    	sbSQL.append(",sum("+fields2[i]+") "+fields2[i]);
		    }
        }
        else  if ( conditionInfo.getClientType() != 1 )
        {
	    	 for(int i=0;i<fieldsWasteBook1.length;i++)
	         {
	             //从台账取数据 1
	 			sbSQL.append(",sum("+fieldsWasteBook1[i]+") "+fieldsWasteBook1[i]);
	         }
	         for(int i=0;i<fieldsWasteBook2.length;i++)
	         {
	             //从台账取数据 2
	         	sbSQL.append(",sum("+fieldsWasteBook2[i]+") "+fieldsWasteBook2[i]);
	         }
        }
       
		sbSQL.append("\n from");
		sbSQL.append("\n (");
        
        
        if ( conditionInfo.getClientType() != 2 )
        {
	        
        	sbSQL.append(getUnionItemTran1(fields1[0],lAccountTypes1[0],lDirections1[0]));
            
        	for(int i=1;i<fields1.length;i++)
	        {
	        	 //从交易取数据 1
	        	sbSQL.append("\n union");
	        	sbSQL.append(getUnionItemTran1(fields1[i],lAccountTypes1[i],lDirections1[i]));
	        }
	        
	        for(int i=0;i<fields2.length;i++)
	        {
	        	 //从交易取数据 2
	        	sbSQL.append("\n union");
	        	sbSQL.append(getUnionItemTran2(fields2[i],lAccountTypes2[i],lDirections2[i]));
	        }
        }
        else if ( conditionInfo.getClientType() != 1 )
        {
        	sbSQL.append(getUnionItemBook1( fieldsWasteBook1[0],conditionInfo.getCounterpartNo()) );
        	
        	for(int j = 1;j<fieldsWasteBook1.length;j++)
	        {
	        	//从台账取数据 1
	        	sbSQL.append("\n union ");
	        	sbSQL.append(getUnionItemBook1( fieldsWasteBook1[j],conditionInfo.getCounterpartNo()) );
	        }
	        
	        for(int j = 0;j<fieldsWasteBook2.length;j++)
	        {
	        	//从台账取数据 2
	        	sbSQL.append("\n union ");
	        	sbSQL.append(getUnionItemBook2( fieldsWasteBook2[j],conditionInfo.getCounterpartNo()) );
	        }
        }
        
        
        sbSQL.append("\n )");
        sbSQL.append("\n  group by clientId,scounterPartNo,executeDate ");
        //sbSQL.append("\n group by clientId,scounterPartNo");
        
        log4j.debug(sbSQL.toString());
        try
        {
            conn = this.getConnection();
        	pstmt = conn.prepareStatement(sbSQL.toString());
        	QCapitalDetailResultInfo resultInfo = null;
       
            rs = pstmt.executeQuery();
            while( rs != null && rs.next())
            {
            	resultInfo = new QCapitalDetailResultInfo();
            	
            	resultInfo.setClientId(rs.getLong("clientId"));
            	
            	resultInfo.setCounterPartNo(rs.getString("scounterPartNo"));
            	
            	resultInfo.setExecuteDate(rs.getTimestamp("executeDate"));
            	
            	/**资金来源---start**/
            	if(conditionInfo.getClientType() != 2)
            	{
            		resultInfo.setCorpDeposit(rs.getDouble("drawing1"));//企业存款
            		
            		resultInfo.setTrustReceive(rs.getDouble("trustLoan1"));//自营贷款还款
                	
                	resultInfo.setConsignReceive(rs.getDouble("consignLoan1"));//委托贷款收回
                	
                	resultInfo.setDiscountBack(rs.getDouble("discount1"));//贴现收回
                	
                	resultInfo.setConsignDeposit(rs.getDouble("consignDeposit"));//委托存款
                	
                	resultInfo.setConsignGathering(rs.getDouble("consignGathering"));  //委托收款
            	}
            	else if(conditionInfo.getClientType() != 1)
            	{
	            	resultInfo.setDiscountReceive(rs.getDouble("discountReceive"));//转贴现进款
	            	
	            	resultInfo.setCapitalReceive(rs.getDouble("capitalRePurchase"));//资产回购进款
	            	
	            	resultInfo.setBankReceive(rs.getDouble("bankReceive"));//同业拆借进款
	            	
	            	resultInfo.setCurrencyInvestBack(rs.getDouble("currency1"));//货币投资收回
	            	
	            	resultInfo.setSecuritiesInvestBack(rs.getDouble("securities1"));//证券投资收回
	            	
	            	resultInfo.setBusinessReceive(rs.getDouble("business1"));//业务收入(台账)
	            	//System.out.println("AAAAAAAAAAAAA----------------------");
	            	//resultInfo.setBusinessReceiveGL(rs.getDouble("businessReceive"));//业务收入（金融企业往来收入）
	            	//System.out.println("BBBBBBBBBBBBBBB----------------------");
	            	resultInfo.setBusinessReceive(resultInfo.getBusinessReceive() + resultInfo.getBusinessReceiveGL());//业务收入(台账 + 金融企业往来收入)
	            	//System.out.println("CCCCCCCCCCCCCCCCC----------------------");
            	}
            	
                //小计
            	resultInfo.setTotalAmount1(resultInfo.getCorpDeposit()
            		              	    + resultInfo.getTrustReceive() 
            			                + resultInfo.getConsignReceive() 
            			                + resultInfo.getDiscountBack()
										+ resultInfo.getConsignDeposit()
										+ resultInfo.getDiscountReceive()
										+ resultInfo.getCapitalReceive()
										+ resultInfo.getBankReceive()
										+ resultInfo.getCurrencyInvestBack()
										+ resultInfo.getSecuritiesInvestBack()
										+ resultInfo.getBusinessReceive());
            	
            	/**资金来源---end**/
            	
            	/**资金用途---start**/
            	if(conditionInfo.getClientType()!= 2)
            	{
	            	resultInfo.setDrawing(rs.getDouble("drawing2"));//企业提款
	            	
	            	resultInfo.setConsignLoan(rs.getDouble("consignLoan2"));//委托贷款发放
	            	
	            	resultInfo.setTrustLoan(rs.getDouble("trustLoan2"));//自营贷款发放
	            	
	            	resultInfo.setDiscount(rs.getDouble("discount2"));//贴现发放
	            	
	            	resultInfo.setConsignPay(rs.getDouble("consignPay"));  //委托付款
            	}
            	else if(conditionInfo.getClientType() != 1)
            	{
	            	resultInfo.setDiscountPay(rs.getDouble("discountPay"));//转贴现出款
	            	
	            	resultInfo.setLoanRepurchase(rs.getDouble("loanRepurchase"));//贷款回购出款
	            	
	            	resultInfo.setBankPay(rs.getDouble("bankPay"));//同业拆借出款
	            	
	            	resultInfo.setSecuritiesInvest(rs.getDouble("securities2"));//证券投资
	            	
	            	resultInfo.setCurrencyInvest(rs.getDouble("currency2"));//货币投资
	            	System.out.println("CCCCCCCCCCCCCCCC----------------------");
	            	resultInfo.setBusinessPay(rs.getDouble("business2"));//业务支出
	            	System.out.println("DDDDDDDDDDDDDDDDDDDDDDD----------------------");
            	}
            	
            	//小计
            	resultInfo.setTotalAmount2(resultInfo.getDrawing()
		                + resultInfo.getConsignLoan()
						+ resultInfo.getTrustLoan()
						+ resultInfo.getDiscount()
						+ resultInfo.getDiscountPay()
						+ resultInfo.getLoanRepurchase()
						+ resultInfo.getBankPay()
						+ resultInfo.getSecuritiesInvest()
						+ resultInfo.getCurrencyInvest()
						+ resultInfo.getBusinessPay());
            	
            	/**资金用途---end**/
            	
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

    
    /**
     * 从台账中取数据
     * 资金来源
     **/
    private String getUnionItemBook1(String singleField,String sCounterPartNo)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select -1 clientId,scounterPartNo,dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  if(singleField.equalsIgnoreCase(fieldsWasteBook1[i]))
    	  {
    	     sbResult.append(",amount1 " + fieldsWasteBook1[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	  }
    	}
    	
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select -1 clientId,scounterPartNo,amount1 ,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(singleField.equalsIgnoreCase("discountReceive"))
    	{
    		//转贴现进款
    		sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mDiscountPayAmount amount1,sett_transinvestment.Z_dtDiscountDate dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.BUYBREAK );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
			sbResult.append("\n   and  sett_transinvestment.Z_dtDiscountDate  is  not null " );
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
			 
	    	sbResult.append("\n union ");
			
	    	sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mBillAmount amount1, sett_transinvestment.Z_dtBillEndDate dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
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
			sbResult.append("\n   and  sett_transinvestment.Z_dtBillEndDate  is  not null " );
			
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate <="+sDateTo);
	    	}
			
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mBillAmount amount1, sett_transinvestment.D_dtRepurchaseDate dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.IN );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "        + Constant.RecordStatus.VALID );
			sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
			sbResult.append("\n   and  JQ_bBought = " + Constant.YesOrNo.YES);
			sbResult.append("\n   and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
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
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate dtexecute from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
    		sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n   and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , D_mBuyAmount amount1 , sett_transinvestment.D_dtBuyDate dtexecute from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN);
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
	    	sbResult.append("\n   and  JQ_bBought =  "+ Constant.YesOrNo.YES);
	    	sbResult.append("\n   and  sett_transinvestment.D_dtBuyDate  is  not null " );
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
    		sbResult.append("\n select sCounterpartNo , C_mBorrowAmount amount1,sett_transinvestment.C_dtBorrowDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 ");
		    sbResult.append("\n   and  sett_transinvestment.C_dtBorrowDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
		    sbResult.append("\n select sCounterpartNo , C_mPayAmount amount1 ,  sett_transinvestment.C_dtPayDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n   and  sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
		    sbResult.append("\n   and C_nBorrowTypeId = 2 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and  sett_transinvestment.C_dtPayDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
    		
    		
    	}
    	else if(singleField.equalsIgnoreCase("currency1"))
    	{
    		//货币投资收回
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH
    		                                                                         +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId in ("+LOANConstant.TransDiscountInOrOut.OUT +",1)");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'金融债'"+","+"'央行票据'"+","+"'政策性金融债'"+","+"'货币市场基金'"+","+"'银行间国债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}

    	}
    	else if(singleField.equalsIgnoreCase("securities1"))
    	{
    		//证券投资收回
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where ");
    		sbResult.append("\n  (");
    		sbResult.append("\n    ( sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH+")");
    		sbResult.append("\n      and sett_transinvestment.nTransactoinTypeId  = "+LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n    ) or ( sett_transinvestment.nTransInvestmentTypeId = "+SETTConstant.InvestTransactionType.ZQHG_JYS);
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1 )");
    		sbResult.append("\n  )");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'股票'"+","+"'交易所间国债'"+","+"'开放式基金'"+","+"'封闭式基金'"+","+"'企业债'"+","+"'可转债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and sett_transinvestment.JQ_dtInterestEnd  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd <="+sDateTo);
	    	}

    		
    	}
    	else if(singleField.equalsIgnoreCase("business1"))
    	{
    		//业务收入 
    		/**转贴现台账**/	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1,sett_transinvestment.Z_dtDiscountDate dtexecute from  sett_transinvestment ");
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
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 ,sett_transinvestment.D_dtBuyDate dtexecute from  sett_transinvestment ");
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
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 ,sett_transinvestment.Q_dtDealDate dtexecute from  sett_transinvestment ");
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
    	
    	sbResult.append(sClientId);
    	
    	
    	sbResult.append(sAmount);
		
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by clientId,scounterPartNo");
		 
    	return sbResult.toString();
    }
    
    
    /**
     * 从台账中取数据
     * 资金来源
     * 就全部客户查询
     **/
    private String getAllUnionItemBook1(String singleField,String sCounterPartNo)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  if(singleField.equalsIgnoreCase(fieldsWasteBook1[i]))
    	  {
    	     sbResult.append(", amount1 " + fieldsWasteBook1[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	  }
    	}
    	
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	
    	for(int i=0;i<fieldsSubject.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsSubject[i]);
    	}
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select amount1 ,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(singleField.equalsIgnoreCase("discountReceive"))
    	{
    		//转贴现进款
    		sbResult.append("\n select sett_transinvestment.Z_mDiscountPayAmount amount1,sett_transinvestment.Z_dtDiscountDate dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.BUYBREAK );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n   and  sett_transinvestment.Z_dtDiscountDate  is  not null " );
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
			 
	    	sbResult.append("\n union ");
			
	    	sbResult.append("\n select sett_transinvestment.Z_mBillAmount amount1, sett_transinvestment.Z_dtBillEndDate dtexecute");
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
			sbResult.append("\n   and  sett_transinvestment.Z_dtBillEndDate  is  not null " );
			
			if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n   and sett_transinvestment.Z_dtBillEndDate <="+sDateTo);
	    	}
			
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sett_transinvestment.Z_mBillAmount amount1, sett_transinvestment.D_dtRepurchaseDate dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n   sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = "  + LOANConstant.TransDiscountInOrOut.IN );
			sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType2 = "  + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "        + Constant.RecordStatus.VALID );
			sbResult.append("\n   and  JQ_bBought = " + Constant.YesOrNo.YES);
			sbResult.append("\n   and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
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
    		sbResult.append("\n select  JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate dtexecute from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
    		sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n   and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select  D_mBuyAmount amount1 , sett_transinvestment.D_dtBuyDate dtexecute from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN);
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n   and  JQ_bBought =  "+ Constant.YesOrNo.YES);
	    	sbResult.append("\n   and  sett_transinvestment.D_dtBuyDate  is  not null " );
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
    		sbResult.append("\n select  C_mBorrowAmount amount1,sett_transinvestment.C_dtBorrowDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 ");
		    sbResult.append("\n   and  sett_transinvestment.C_dtBorrowDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
		    sbResult.append("\n select  C_mPayAmount amount1 ,  sett_transinvestment.C_dtPayDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n   and C_nBorrowTypeId = 2 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and  sett_transinvestment.C_dtPayDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
    		
    		
    	}
    	else if(singleField.equalsIgnoreCase("currency1"))
    	{
    		//货币投资收回
    		sbResult.append("\n select Q_mPayAmount amount1,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH
    		                                                                         +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId in ("+LOANConstant.TransDiscountInOrOut.OUT +",1)");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'金融债'"+","+"'央行票据'"+","+"'政策性金融债'"+","+"'货币市场基金'"+","+"'银行间国债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select  JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}

    	}
    	else if(singleField.equalsIgnoreCase("securities1"))
    	{
    		//证券投资收回
    		sbResult.append("\n select  Q_mPayAmount amount1,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where ");
    		sbResult.append("\n  (");
    		sbResult.append("\n    ( sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH+")");
    		sbResult.append("\n      and sett_transinvestment.nTransactoinTypeId = "+LOANConstant.TransDiscountInOrOut.OUT);
    		sbResult.append("\n    ) or ( sett_transinvestment.nTransInvestmentTypeId = "+SETTConstant.InvestTransactionType.ZQHG_JYS);
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1 )");
    		sbResult.append("\n  )");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'股票'"+","+"'交易所间国债'"+","+"'开放式基金'"+","+"'封闭式基金'"+","+"'企业债'"+","+"'可转债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select  JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.JQ_dtInterestEnd  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd <="+sDateTo);
	    	}

    		
    	}
    	else if(singleField.equalsIgnoreCase("business1"))
    	{
    		//业务收入 
    		/**转贴现台账**/	    	
	    	sbResult.append("\n select mInterestAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.ZTX );
	        sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN );
	        sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	        if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**贷款回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select mInterestAmount amount1,sett_transinvestment.D_dtBuyDate  dtexecute from  sett_transinvestment ");
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
	    	
	    	sbResult.append("\n select mInterestAmount amount1 ,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
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
    	
    	//sbResult.append(sClientId);
    	
    	
    	//sbResult.append(sAmount);
		
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by  executeDate");
		 
    	return sbResult.toString();
    }
    
    /**
     * 从台账中取数据
     * 资金用途 
     **/
    private String getUnionItemBook2(String singleField,String sCounterPartNo)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select -1 clientId,scounterPartNo,dtexecute executeDate");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  if(singleField.equalsIgnoreCase(fieldsWasteBook2[i]))
    	  {
    	     sbResult.append(",amount1 " + fieldsWasteBook2[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	  }
    	}
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select -1 clientId,scounterPartNo,amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(singleField.equalsIgnoreCase("discountPay"))
    	{
    		//转贴现出款
    		sbResult.append("\n select sett_transinvestment.sCounterpartNo,sett_transinvestment.Z_mDiscountPayAmount amount1,nvl(sett_transinvestment.D_dtRepurchaseDate,sett_transinvestment.Z_dtDiscountDate) dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n  sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
			sbResult.append("\n    and nvl(sett_transinvestment.D_dtRepurchaseDate,sett_transinvestment.Z_dtDiscountDate)   is  not null " );
			sbResult.append("\n  and (");
			
			
			sbResult.append("\n         (  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n            and sett_transinvestment.Z_nDiscountType2 = " + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n            and sett_transinvestment.Z_nIsRepurchase = " + Constant.YesOrNo.YES );
			if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n          and sett_transinvestment.Z_dtBuyEndDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n        and sett_transinvestment.Z_dtBuyEndDate <="+sDateTo);
	    	}
			sbResult.append("\n         )");
			
			
			sbResult.append("\n      or ( sett_transinvestment.Z_nDiscountType1 = "+LOANConstant.TransDiscountInOrOut.IN );
			if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n         and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n       and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
			sbResult.append("\n         )");
			
			
			sbResult.append("\n       )");
			
			
			
			
    	}
    	else if(singleField.equalsIgnoreCase("loanRepurchase"))
    	{
    		//贷款回购
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
    		sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN);
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , D_mBuyAmount amount1 ,sett_transinvestment.D_dtBuyDate dtexecute  from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
	    	sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT);
	    	sbResult.append("\n   and  JQ_bBought =  "+ Constant.YesOrNo.YES);
	    	sbResult.append("\n    and  sett_transinvestment.D_dtBuyDate  is  not null " );
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtBuyDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtBuyDate <="+sDateTo);
	    	}
	    	
	    
    	}
    	else if(singleField.equalsIgnoreCase("bankPay"))
    	{
    		//同业拆借出款
    		sbResult.append("\n select sCounterpartNo , C_mBorrowAmount amount1,sett_transinvestment.C_dtBorrowDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 2 ");
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n   and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
		    sbResult.append("\n    and  sett_transinvestment.C_dtBorrowDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
		    sbResult.append("\n select sCounterpartNo , C_mPayAmount amount1 ,sett_transinvestment.C_dtPayDate  dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
		    sbResult.append("\n    and  sett_transinvestment.C_dtPayDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
    		
    		
    	}
    	else if(singleField.equalsIgnoreCase("securities2"))
    	{
    		//证券投资
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1 ,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where ");
    		sbResult.append("\n  (");
    		sbResult.append("\n    ( sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH+")");
    		sbResult.append("\n      and sett_transinvestment.nTransactoinTypeId  = "+LOANConstant.TransDiscountInOrOut.IN);
    		sbResult.append("\n    ) or ( sett_transinvestment.nTransInvestmentTypeId = "+SETTConstant.InvestTransactionType.ZQHG_JYS);
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2 )");
    		sbResult.append("\n  )");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'股票'"+","+"'交易所间国债'"+","+"'开放式基金'"+","+"'封闭式基金'"+","+"'企业债'"+","+"'可转债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.JQ_dtInterestEnd  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd <="+sDateTo);
	    	}

    		
    	}
    	else if(singleField.equalsIgnoreCase("currency2"))
    	{
    		//货币投资
    		sbResult.append("\n select sCounterpartNo , Q_mPayAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH
    		                                                                         +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId in ("+LOANConstant.TransDiscountInOrOut.IN +",2)");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'金融债'"+","+"'央行票据'"+","+"'政策性金融债'"+","+"'货币市场基金'"+","+"'银行间国债'"+")");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select sCounterpartNo , JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}

    	}
    	else if(singleField.equalsIgnoreCase("business2"))
    	{
    		//业务支出
    		/**拆借台账**/
    		sbResult.append("\n select sCounterpartNo , mInterestAmount amount1,sett_transinvestment.C_dtPayDate  dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
		    sbResult.append("\n    and  sett_transinvestment.C_dtPayDate  is  not null " );
		    
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**转贴现台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.ZTX );
	        sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT );
	        sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	        sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
	        sbResult.append("\n    and  sett_transinvestment.Z_dtDiscountDate  is  not null " );
	        
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
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1,sett_transinvestment.D_dtBuyDate dtexecute from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
	    	sbResult.append("\n    and  sett_transinvestment.D_dtBuyDate  is  not null " );
	    	
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
	    	
	    	sbResult.append("\n select sCounterpartNo , mInterestAmount amount1,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( "+ SETTConstant.InvestTransactionType.ZQHG_JYS 
    		                                                                        +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.sCounterpartNo  = " +"'" + sCounterPartNo +"'" );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );
    		
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
    	
    	sbResult.append(sClientId); 
		
    	sbResult.append(sAmount);
    	
    	
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by clientId,scounterPartNo");
		 
    	return sbResult.toString();
    }
    
    
    
    /**
     * 从台账中取数据
     * 资金用途 
     * 就所有客户进行查询
     **/
    private String getAllUnionItemBook2(String singleField,String sCounterPartNo)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select dtexecute executeDate");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  if(singleField.equalsIgnoreCase(fieldsWasteBook2[i]))
    	  {
    	     sbResult.append(", amount1 " + fieldsWasteBook2[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	  }
    	}
    	
    	for(int i=0;i<fieldsSubject.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsSubject[i]);
    	}
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(singleField.equalsIgnoreCase("discountPay"))
    	{
    		//转贴现出款
    		sbResult.append("\n select sett_transinvestment.Z_mDiscountPayAmount amount1,nvl(sett_transinvestment.D_dtRepurchaseDate,sett_transinvestment.Z_dtDiscountDate) dtexecute");
		    sbResult.append("\n from sett_transinvestment ");
		    sbResult.append("\n where  ");
			sbResult.append("\n  sett_transinvestment.nTransInvestmentTypeId = " + SETTConstant.InvestTransactionType.ZTX );
			sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
			sbResult.append("\n    and nvl(sett_transinvestment.D_dtRepurchaseDate,sett_transinvestment.Z_dtDiscountDate)   is  not null " );
			sbResult.append("\n  and (");
			
			
			sbResult.append("\n         (  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT );
			sbResult.append("\n            and sett_transinvestment.Z_nDiscountType2 = " + SETTConstant.TransDiscountType.REPURCHASE );
			sbResult.append("\n            and sett_transinvestment.Z_nIsRepurchase = " + Constant.YesOrNo.YES );
			if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n          and sett_transinvestment.Z_dtBuyEndDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n        and sett_transinvestment.Z_dtBuyEndDate <="+sDateTo);
	    	}
			sbResult.append("\n         )");
			
			
			sbResult.append("\n      or ( sett_transinvestment.Z_nDiscountType1 = "+LOANConstant.TransDiscountInOrOut.IN );
			if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n         and sett_transinvestment.Z_dtDiscountDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n       and sett_transinvestment.Z_dtDiscountDate <="+sDateTo);
	    	}
			sbResult.append("\n         )");
			
			
			sbResult.append("\n       )");
			
			
			
			
    	}
    	else if(singleField.equalsIgnoreCase("loanRepurchase"))
    	{
    		//贷款回购
    		sbResult.append("\n select JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
    		sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.IN);
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select  D_mBuyAmount amount1 ,sett_transinvestment.D_dtBuyDate dtexecute  from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT);
	    	sbResult.append("\n   and  JQ_bBought =  "+ Constant.YesOrNo.YES);
	    	sbResult.append("\n    and  sett_transinvestment.D_dtBuyDate  is  not null " );
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtBuyDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtBuyDate <="+sDateTo);
	    	}
	    	
	    
    	}
    	else if(singleField.equalsIgnoreCase("bankPay"))
    	{
    		//同业拆借出款
    		sbResult.append("\n select  C_mBorrowAmount amount1,sett_transinvestment.C_dtBorrowDate dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 2 ");
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n    and  sett_transinvestment.C_dtBorrowDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtBorrowDate <="+sDateTo);
	    	}
	    	
	    	sbResult.append("\n union ");
	    	
		    sbResult.append("\n select  C_mPayAmount amount1 ,sett_transinvestment.C_dtPayDate  dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n    and  sett_transinvestment.C_dtPayDate  is  not null " );
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
    		
    		
    	}
    	else if(singleField.equalsIgnoreCase("securities2"))
    	{
    		//证券投资
    		sbResult.append("\n select Q_mPayAmount amount1 ,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where ");
    		sbResult.append("\n  (");
    		sbResult.append("\n    ( sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH+")");
    		sbResult.append("\n      and sett_transinvestment.nTransactoinTypeId  = "+LOANConstant.TransDiscountInOrOut.IN);
    		sbResult.append("\n    ) or ( sett_transinvestment.nTransInvestmentTypeId = "+SETTConstant.InvestTransactionType.ZQHG_JYS);
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 2 )");
    		sbResult.append("\n  )");
    		sbResult.append("\n    and  sett_transinvestment.Q_SProductType in ("+"'股票'"+","+"'交易所间国债'"+","+"'开放式基金'"+","+"'封闭式基金'"+","+"'企业债'"+","+"'可转债'"+")");
    		sbResult.append("\n    and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select JQ_nBuyAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.JQ_dtInterestEnd  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.JQ_dtInterestEnd <="+sDateTo);
	    	}

    		
    	}
    	else if(singleField.equalsIgnoreCase("currency2"))
    	{
    		//货币投资
    		sbResult.append("\n select  Q_mPayAmount amount1 ,sett_transinvestment.Q_dtDealDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( " + SETTConstant.InvestTransactionType.XQ_JYS
    		                                                                         +","+ SETTConstant.InvestTransactionType.XQ_YH
    		                                                                         +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId in ("+LOANConstant.TransDiscountInOrOut.IN +",2)");
    		sbResult.append("\n    and sett_transinvestment.Q_SProductType in ("+"'金融债'"+","+"'央行票据'"+","+"'政策性金融债'"+","+"'货币市场基金'"+","+"'银行间国债'"+")");
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and sett_transinvestment.Q_dtDealDate  is  not null " );

    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.Q_dtDealDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.Q_dtDealDate <="+sDateTo);
	    	}
    		
    		sbResult.append("\n union ");
    		
    		sbResult.append("\n select JQ_nBuyAmount amount1,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId = "+ SETTConstant.InvestTransactionType.ZQHG_JYS );
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n    and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}

    	}
    	else if(singleField.equalsIgnoreCase("business2"))
    	{
    		//业务支出
    		/**拆借台账**/
    		sbResult.append("\n select mInterestAmount amount1,sett_transinvestment.C_dtPayDate  dtexecute  from  sett_transinvestment ");
		    sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.CJ );
		    sbResult.append("\n   and C_nBorrowTypeId = 1 and C_nIspaid = "+Constant.YesOrNo.YES);
		    sbResult.append("\n   and sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
		    sbResult.append("\n    and  sett_transinvestment.C_dtPayDate  is  not null " );
		    
		    if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.C_dtPayDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.C_dtPayDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**转贴现台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select  mInterestAmount amount1 ,sett_transinvestment.D_dtRepurchaseDate dtexecute  from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.ZTX );
	        sbResult.append("\n   and  sett_transinvestment.Z_nDiscountType1 = " + LOANConstant.TransDiscountInOrOut.OUT );
	        sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	        sbResult.append("\n    and  sett_transinvestment.D_dtRepurchaseDate  is  not null " );
	        
	        if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate >="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_transinvestment.D_dtRepurchaseDate <="+sDateTo);
	    	}
	    	
	    	
	    	/**贷款回购台账**/
	    	sbResult.append("\n union ");
	    	
	    	sbResult.append("\n select  mInterestAmount amount1,sett_transinvestment.D_dtBuyDate dtexecute from  sett_transinvestment ");
	    	sbResult.append("\n where nTransInvestmentTypeid = "+ SETTConstant.InvestTransactionType.DKHG );
	    	sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
	    	sbResult.append("\n    and  sett_transinvestment.D_dtBuyDate  is  not null " );
	    	
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
	    	
	    	sbResult.append("\n select  mInterestAmount amount1,sett_transinvestment.D_dtRepurchaseDate  dtexecute  from  sett_transinvestment ");
    		sbResult.append("\n  where sett_transinvestment.nTransInvestmentTypeId in( "+ SETTConstant.InvestTransactionType.ZQHG_JYS 
    		                                                                        +","+ SETTConstant.InvestTransactionType.ZQHG_YH+")");
    		sbResult.append("\n    and sett_transinvestment.nTransactoinTypeId = 1" );
    		sbResult.append("\n    and sett_transinvestment.JQ_bBought = 1" );
    		sbResult.append("\n   and  sett_transinvestment.nStatusId  = "  + Constant.RecordStatus.VALID );
    		sbResult.append("\n    and  sett_transinvestment.Q_dtDealDate  is  not null " );
    		
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
    	
    	//sbResult.append(sClientId); 
		
    	//sbResult.append(sAmount);
    	
    	
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by executeDate");
		 
    	return sbResult.toString();
    }
    
    /**
     * 从结算交易中取数据
     * 资金来源
     **/
    private String getUnionItemTran1(String sField,long lType,long lDirection)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select clientId,null scounterPartNo,dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(fields1[i]))
    	  {
    	     sbResult.append(",amount1 " + fields1[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fields1[i]);
    	  }
    	}
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	
    	
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select client.id clientId,null scounterPartNo,amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		if(!sField.equalsIgnoreCase("consignGathering"))//如果不是委托收款
		{
			if(sField.equalsIgnoreCase("drawing1"))
			{
				sbResult.append("\n select sett_account.nclientid clientId1,sum(sett_transaccountdetail.mamount) amount1,sett_transaccountdetail.dtexecute ");
			}
			else
			{
				sbResult.append("\n select sett_account.nclientid clientId1,sett_transaccountdetail.mamount amount1,sett_transaccountdetail.dtexecute ");
			}
			sbResult.append("\n from sett_account,sett_transaccountdetail");
			sbResult.append("\n where sett_account.id=sett_transaccountdetail.ntransaccountid (+)");
			if(sField.equalsIgnoreCase("drawing1"))
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
	    	
	    	sbResult.append("\n and sett_transaccountdetail.dtexecute is  not null ");
	    	
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n and sett_transaccountdetail.dtexecute>="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n and sett_transaccountdetail.dtexecute<="+sDateTo);
	    	}
	    	if(sField.equalsIgnoreCase("drawing1"))
	    	{
	    		sbResult.append("\n group by sett_account.nclientid,sett_transaccountdetail.dtexecute ");
	    	}
		}
		else
    	{
			sbResult.append("\n  select client.id clientId1 , sett_glbalance.mCreditAmount amount1 ,sett_glbalance.dtGLDate  dtexecute");
			sbResult.append("\n  from sett_glbalance,client ");
			sbResult.append("\n  where sett_glbalance.sglsubjectcode = "+"'"+subjectCode+"'");
			sbResult.append("\n    and client.sname  = "+"'"+clientName+"'");
			sbResult.append("\n    and sett_glbalance.dtGLDate is  not null ");
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
    	
    	sbResult.append(sClientId); 
		
    	sbResult.append(sAmount);
    	
    	
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by clientId");
		 
    	return sbResult.toString();
    }
    
    /**
     * 从结算交易中取数据
     * 资金来源
     * 就全部成员客户和投融资交易对手进行查询
     **/
    private String getAllUnionItemTran1(String sField,long lType,long lDirection)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select  dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(fields1[i]))
    	  {
    	     sbResult.append(",amount1 " + fields1[i]);
    	  }
    	  else
    	  {
    	  	 sbResult.append(",0 as " + fields1[i]);
    	  }
    	}
    	
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	for(int i=0;i<fieldsSubject.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsSubject[i]);
    	}
    	
    	
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select  amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		if(!sField.equalsIgnoreCase("consignGathering"))//如果不是委托收款
		{
			if(sField.equalsIgnoreCase("drawing1"))
			{
				sbResult.append("\n select sum(sett_transaccountdetail.mamount) amount1,sett_transaccountdetail.dtexecute ");
			}
			else
			{
				sbResult.append("\n select sett_transaccountdetail.mamount amount1,sett_transaccountdetail.dtexecute ");
			}
			sbResult.append("\n from sett_account,sett_transaccountdetail");
			sbResult.append("\n where sett_account.id=sett_transaccountdetail.ntransaccountid (+)");
			if(sField.equalsIgnoreCase("drawing1"))
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
	    	
	    	sbResult.append("\n and sett_transaccountdetail.dtexecute is  not null ");
	    	
	    	if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n and sett_transaccountdetail.dtexecute>="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n and sett_transaccountdetail.dtexecute<="+sDateTo);
	    	}
	    	if(sField.equalsIgnoreCase("drawing1"))
	    	{
	    		sbResult.append("\n group by sett_transaccountdetail.dtexecute");
	    	}
		}
		else
    	{
			sbResult.append("\n  select sett_glbalance.mCreditAmount amount1 ,sett_glbalance.dtGLDate  dtexecute");
			sbResult.append("\n  from sett_glbalance ");
			sbResult.append("\n  where sett_glbalance.sglsubjectcode = "+"'"+subjectCode+"'");
			sbResult.append("\n    and sett_glbalance.dtGLDate is  not null ");
			if(sDateFrom.length()>0)
			{
			  sbResult.append("\n   and sett_glbalance.dtGLDate >= "+sDateFrom);
			}
			if(sDateTo.length()>0)
			{
				sbResult.append("\n and sett_glbalance.dtGLDate <= "+sDateTo);
			}
    	}
    	sbResult.append("\n )");
    	
		//sbResult.append("\n where client.id=clientId1 (+)");
    	
    	//sbResult.append(sClientId); 
		
    	//sbResult.append(sAmount);
    	
    	
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by dtexecute");
		 
    	return sbResult.toString();
    }
    
    /**
     * 从结算交易中取数据
     * 资金用途
     **/
    private String getUnionItemTran2(String sField,long lType,long lDirection)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select clientId,null scounterPartNo,dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	
    	for(int i=0;i<fields2.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(fields2[i]))
    	  {
    	     sbResult.append(",amount1 " + fields2[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fields2[i]);
    	  }
    	}
    	
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select client.id clientId,null scounterPartNo,amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		if(!sField.equalsIgnoreCase("consignPay"))//如果不是委托存款
		{
			if(sField.equalsIgnoreCase("drawing2"))
			{
				sbResult.append("\n select sett_account.nclientid clientId1,sum(sett_transaccountdetail.mamount) amount1,sett_transaccountdetail.dtexecute");
			}
			else
			{
				sbResult.append("\n select sett_account.nclientid clientId1,sett_transaccountdetail.mamount amount1,sett_transaccountdetail.dtexecute");
			}
			sbResult.append("\n from sett_account,sett_transaccountdetail");
			sbResult.append("\n where sett_account.id=sett_transaccountdetail.ntransaccountid (+)");
			if(sField.equalsIgnoreCase("drawing2"))
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
	    	
	    	sbResult.append("\n and sett_transaccountdetail.dtexecute is  not null ");
	    	
	    	if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n and sett_transaccountdetail.dtexecute>="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n and sett_transaccountdetail.dtexecute<="+sDateTo);
	    	}
	    	
	    	if(sField.equalsIgnoreCase("drawing2"))
	    	{
	    		sbResult.append("\n group by sett_account.nclientid,sett_transaccountdetail.dtexecute ");
	    	}
		}
		else//如果是委托存款
		{
			sbResult.append("\n  select client.id clientId1 , sett_glbalance.mDebitamount amount1 ,sett_glbalance.dtGLDate dtexecute ");
			sbResult.append("\n  from sett_glbalance,client ");
			sbResult.append("\n  where sett_glbalance.sglsubjectcode = "+"'"+subjectCode+"'");
			sbResult.append("\n    and client.sname  = "+"'"+clientName+"'");
			sbResult.append("\n    and sett_glbalance.dtGLDate is  not null ");
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
    	
    	sbResult.append(sClientId); 
    	
    	sbResult.append(sAmount);
		
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by clientId,scounterPartNo");
		 
    	return sbResult.toString();
    }
    
    private String getAllUnionItemGL(String sField)
	{
    	StringBuffer sbResult = new StringBuffer();
    	
    	sbResult.append("\n select dtexecute executeDate  ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	
    	for(int i=0;i<fields2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields2[i]);
    	}
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	for(int i=0;i<fieldsSubject.length;i++)
    	{
    		 sbResult.append(", amount1 " + fieldsSubject[i]);
    	}
    	
    	
    	 
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		
		if(sField.equalsIgnoreCase("longInvest"))
    	{
    		sbResult.append("\n select sett_glbalance.mdebitamount amount1 ,sett_glbalance.dtGLDate dtexecute from  sett_glbalance ");
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
		else if(sField.equalsIgnoreCase("depositPrepare"))
		{

    		sbResult.append("\n select  sett_glbalance.mdebitamount amount1 , sett_glbalance.dtGLDate  dtexecute  from  sett_glbalance ");
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
		else if(sField.equalsIgnoreCase("otherPay"))
		{

    		sbResult.append("\n select   sett_glbalance.mdebitamount amount1 ,sett_glbalance.dtGLDate  dtexecute from  sett_glbalance ");
		    sbResult.append("\n   where sett_glbalance.sglsubjectcode = '1020010103'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	} 
    	
		}
		else if(sField.equalsIgnoreCase("longInvestBack"))
    	{
    		sbResult.append("\n select  sett_glbalance.mCreditamount amount1 ,sett_glbalance.dtGLDate dtexecute from  sett_glbalance ");
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
		else if(sField.equalsIgnoreCase("businessReceive"))
		{

    		sbResult.append("\n select   sett_glbalance.mCreditamount amount1,sett_glbalance.dtGLDate  dtexecute  from  sett_glbalance ");
		    sbResult.append("\n   where substr(sett_glbalance.sglsubjectcode,0,3) = '508'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	} 
    	
		}
		else if(sField.equalsIgnoreCase("startBalance"))
		{

    		sbResult.append("\n select   sett_glbalance.mdebitbalance amount1,sett_glbalance.dtGLDate  dtexecute  from  sett_glbalance ");
		    sbResult.append("\n   where substr(sett_glbalance.sglsubjectcode,0,3) = '102'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFromPre);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	} 
    	
		}
		else if(sField.equalsIgnoreCase("assureAmount"))
		{

    		sbResult.append("\n select   sett_glbalance.mdebitbalance amount1,sett_glbalance.dtGLDate  dtexecute  from  sett_glbalance ");
		    sbResult.append("\n   where sett_glbalance.sglsubjectcode = '10200199'" );
    		
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

    		sbResult.append("\n select sett_glbalance.mCreditamount amount1,sett_glbalance.dtGLDate  dtexecute from  sett_glbalance ");
		    sbResult.append("\n   where substr(sett_glbalance.sglsubjectcode,0,3) = '508'" );
    		
    		if(sDateFrom.length()>0)
	    	{
	    	    sbResult.append("\n  and sett_glbalance.dtGLDate >= "+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n  and sett_glbalance.dtGLDate <= "+sDateTo);
	    	}
		}
    	sbResult.append("\n )");
    	sbResult.append("\n )");
		
    	 
    	
    	return sbResult.toString(); 
    	
    }
    
    /**
     * 从结算交易中取数据
     * 资金用途
     * 就全部客户进行查询
     **/
    private String getAllUnionItemTran2(String sField,long lType,long lDirection)
	{
    	StringBuffer sbResult = new StringBuffer();
    	sbResult.append("\n select dtexecute executeDate ");
    	for(int i=0;i<fields1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fields1[i]);
    	}
    	
    	for(int i=0;i<fields2.length;i++)
    	{
    	  if(sField.equalsIgnoreCase(fields2[i]))
    	  {
    	     sbResult.append(",amount1 " + fields2[i]);
    	  }
    	  else
    	  {
    	  	sbResult.append(",0 as " + fields2[i]);
    	  }
    	}
    	
    	for(int i=0;i<fieldsWasteBook1.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook1[i]);
    	}
    	for(int i=0;i<fieldsWasteBook2.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsWasteBook2[i]);
    	}
    	for(int i=0;i<fieldsSubject.length;i++)
    	{
    	  	sbResult.append(",0 as " + fieldsSubject[i]);
    	}
    	
    	
    	sbResult.append("\n from");
    	sbResult.append("\n (");
		sbResult.append("\n select  amount1,dtexecute");
		sbResult.append("\n from");    	
		sbResult.append("\n (");
		if(!sField.equalsIgnoreCase("consignPay"))//如果不是委托存款
		{
			if(sField.equalsIgnoreCase("drawing2"))
			{
				sbResult.append("\n select  sum(sett_transaccountdetail.mamount) amount1,sett_transaccountdetail.dtexecute");
			}
			else
			{
				sbResult.append("\n select  sett_transaccountdetail.mamount amount1,sett_transaccountdetail.dtexecute");
			}
			sbResult.append("\n from sett_account,sett_transaccountdetail");
			sbResult.append("\n where sett_account.id=sett_transaccountdetail.ntransaccountid (+)");
			if(sField.equalsIgnoreCase("drawing2"))
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
	    	
	    	sbResult.append("\n and sett_transaccountdetail.dtexecute is  not null ");
	    	
	    	if(sDateFrom.length()>0)
	    	{
	    	  sbResult.append("\n and sett_transaccountdetail.dtexecute>="+sDateFrom);
	    	}
	    	if(sDateTo.length()>0)
	    	{
	    		sbResult.append("\n and sett_transaccountdetail.dtexecute<="+sDateTo);
	    	}
	    	
	    	if(sField.equalsIgnoreCase("drawing2"))
	    	{
	    		sbResult.append("\n group by sett_transaccountdetail.dtexecute");
	    	}
		}
		else//如果是委托存款
		{
			sbResult.append("\n  select   sett_glbalance.mDebitamount amount1 ,sett_glbalance.dtGLDate dtexecute ");
			sbResult.append("\n  from sett_glbalance  ");
			sbResult.append("\n  where sett_glbalance.sglsubjectcode = "+"'"+subjectCode+"'");
			sbResult.append("\n    and sett_glbalance.dtGLDate is  not null ");
			if(sDateFrom.length()>0)
			{
			  sbResult.append("\n   and sett_glbalance.dtGLDate >= "+sDateFrom);
			}
			if(sDateTo.length()>0)
			{
				sbResult.append("\n and sett_glbalance.dtGLDate <= "+sDateTo);
			}
			
		}
    	sbResult.append("\n ) ");
    	
		//sbResult.append("\n where client.id=clientId1 (+)");
    	
    	//sbResult.append(sClientId); 
    	
    	//sbResult.append(sAmount);
		
    	sbResult.append("\n )");
		
    	//sbResult.append("\n group by  dtexecute");
		 
    	return sbResult.toString();
    }
}