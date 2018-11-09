package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.query.Dao.QQueryAccountDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.query.queryobj.QAccount;
import com.iss.itreasury.settlement.query.queryobj.QAccountAmountInfoDao;
import com.iss.itreasury.settlement.query.queryobj.QAccountBalance;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionNewInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QQueryAccountBiz {
	
	QQueryAccountDao qQueryAccountDao = new QQueryAccountDao();
	
	public PagerInfo queryQueryAccount(QueryAccountWhereInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			if(qInfo.getQueryDate()==null)
				sql = qQueryAccountDao.queryAccountInfoSQL(qInfo);
			else{
				sql = qQueryAccountDao.queryAccountBalanceSQL(qInfo);
			}
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "accountResultSetHandle");
			
			pagerInfo.setTotalExtensionMothod(QQueryAccountBiz.class, "getAccountSumAmount" , paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList accountResultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		QueryAccountResultInfo info = null;
		
		long officeID = -1;
		long currencyID = -1;
		String accountNo = "";
		long accountID = -1;
		String clientCode = "";
		String accountName = "";
		long accountTypeID = -1;
		long mainAccountStatusID = -1;
		Timestamp openDate = null;
		double balance = 0.0;
		Timestamp clearDate = null;
		long interestPlanID = -1;
		long firstLimitTypeID = -1;
		double dFirstLimitAmount = 0.0;
		long secondLimitTypeID = -1;
 		double dSecondLimitAmount = 0.0;
		long ThirdLimitTypeID = -1;
		double dThirdLimitAmount = 0.0;
		double dCapitalLimitAmount = 0.0;
		double dCanUseBalance = 0.0;
		double uncheckPaymentAmount = 0.0;
		double dAvailableBalance = 0.0;
		double interest = 0.0;		
		long lUnit = 1; 
		String strUnit = "";
	    long isNegotiate = -1;
		String sabstract = "";
	    
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					officeID = rs.getLong("OfficeID");
					currencyID = rs.getLong("CurrencyID");
					accountNo = rs.getString("AccountNo");
					accountID = rs.getLong("AccountID");
					clientCode = rs.getString("ClientCode");
					accountName = rs.getString("AccountName");
					accountTypeID = rs.getLong("AccountTypeID");
					mainAccountStatusID = rs.getLong("MainAccountStatusID");
					openDate = rs.getTimestamp("OpenDate");
					balance = rs.getDouble("balance");
					//firstLimitTypeID = rs.getLong("firstLimitTypeId");
					dFirstLimitAmount = rs.getDouble("firstLimitAmount");
					secondLimitTypeID = rs.getLong("secondLimitTypeId");
					dSecondLimitAmount = rs.getDouble("secondLimitAmount");
					ThirdLimitTypeID = rs.getLong("thirdLimitTypeId");
					dThirdLimitAmount = rs.getDouble("thirdLimitAmount");
					dCapitalLimitAmount = rs.getDouble("capitalLimitAmount");
					//uncheckPaymentAmount = rs.getDouble("uncheckPaymentAmount");
					//if(!(uncheckPaymentAmount>0)){
					//	uncheckPaymentAmount = 0.0;
					//}
					dCanUseBalance = rs.getDouble("availableBalance");
					dAvailableBalance = balance - uncheckPaymentAmount;
					interest = rs.getDouble("interest");
					interestPlanID = rs.getLong("interestPlanID");
					clearDate = rs.getTimestamp("ClearDate");
					//strUnit = rs.getString("amount");
					//if (strUnit!=null && strUnit.trim().length()>0){
				   	// 	lUnit = Long.parseLong(strUnit);
				    //}
					Timestamp tsToday = Env.getSystemDate(officeID,currencyID);
					isNegotiate = rs.getLong("IsNegotiate");
					sabstract = rs.getString("sabstract");
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,accountNo+","+accountID);
					PagerTools.returnCellList(cellList,clientCode);
					PagerTools.returnCellList(cellList,accountName);
					PagerTools.returnCellList(cellList,SETTConstant.AccountType.getName(accountTypeID));
					PagerTools.returnCellList(cellList,SETTConstant.AccountStatus.getName(mainAccountStatusID));
					PagerTools.returnCellList(cellList,openDate==null ? "":DataFormat.formatDate(openDate) );
					PagerTools.returnCellList(cellList,"￥"+(balance != 0.0?DataFormat.formatDisabledAmount(balance/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,"￥"+(dCanUseBalance !=0.0? DataFormat.formatDisabledAmount(dCanUseBalance/lUnit):"0.00"));
					String interestRate = "";
					info = new QueryAccountResultInfo();
					info.setBalance(balance);
					info.setInterestPlanID(interestPlanID);
					info.setOpenDate(openDate);
					info.setAccountTypeID(accountTypeID);
					if (SETTConstant.AccountType.isCurrentAccountType(accountTypeID)||SETTConstant.AccountType.isOtherDepositAccountType(accountTypeID))
					{
						//利率小数点后显示6位
						interestRate = DataFormat.formatAmountUseZero(info.getInterestRate(tsToday),6);
					}
					PagerTools.returnCellList(cellList,interestRate);
					PagerTools.returnCellList(cellList,"￥"+(interest!=0.0?DataFormat.formatDisabledAmount(interest/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,clearDate==null ? "":DataFormat.formatDate(clearDate));
					PagerTools.returnCellList(cellList,isNegotiate>0?"是":"否");
					PagerTools.returnCellList(cellList,sabstract);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	
	public ArrayList getAccountSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			QueryAccountWhereInfo qInfo = (QueryAccountWhereInfo)map.get("qInfo");
			QueryAccountAmountWhereInfo newInfo = new QueryAccountAmountWhereInfo();
			newInfo.copy(qInfo);
			long lUnit = 1;
			if(rs!=null)
			{
				
				QAccountAmountInfoDao qaid = new QAccountAmountInfoDao();
				// 查询的总计结果集
	            QueryAccountSumInfo qasi = qaid.getDepositLoanSum(newInfo);
		
	            long AccountTypeID = -1;
	            double depositCanUseBalanceSum = 0.0;
	        	double loanCanUseBalanceSum = 0.0;
	        	double depositBalanceSum = 0.0;
	        	double loanBalanceSum = 0.0;
	        	String strDepositAccountType = "";
	        	String strLoanAccountType = "";
	        	String[] sDepositAccountType = null;
	        	String[] sLoanAccountType = null;
	        	long[] lDepositAccountType = null;
	        	long[] lLoanAccountType = null;
	        	int i=0;
	        	
	        	QAccount qobj = new QAccount();
	        	strDepositAccountType = qobj.getDepositAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
	        	strLoanAccountType = qobj.getLoanAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
	        	sDepositAccountType = strDepositAccountType.split(",");
	        	lDepositAccountType = new long[sDepositAccountType.length];
	        	
	        	if(sDepositAccountType.length>1) {
		        	for(i=0;i<sDepositAccountType.length;i++)
		        	{
		        		lDepositAccountType[i] = Long.parseLong(sDepositAccountType[i]);
		        	}
	        	}
	        	sLoanAccountType = strLoanAccountType.split(",");

	        	lLoanAccountType = new long[sLoanAccountType.length];
	        	if(sLoanAccountType.length>1){
		        	for(i=0;i<sLoanAccountType.length;i++)
		        	{
		        		lLoanAccountType[i] = Long.parseLong(sLoanAccountType[i]);
		        	}
	        	}
	        	
        		while(rs.next())
				{
	    		
	    			long FirstLimitTypeID = -1;
	    			double dFirstLimitAmount = 0.0;
	    			long SecondLimitTypeID = -1;
	    	 		double dSecondLimitAmount = 0.0;
	    			long ThirdLimitTypeID = -1;
	    			double dThirdLimitAmount = 0.0;
	    			double dCapitalLimitAmount = 0.0;
	    			double balance = 0.0;
	    			double uncheckPaymentAmount = 0.0;
	    			double availableBalance = 0.0;
	    			AccountTypeID = rs.getLong("AccountTypeID");
	    			if(rs.getDouble("UncheckPaymentAmount") > 0){
	    				uncheckPaymentAmount = rs.getDouble("UncheckPaymentAmount");
	    			}
	    			if(rs.getDouble("availableBalance") > 0){
	    				availableBalance = rs.getDouble("availableBalance");
	    			}

	    			double dAvailableBalance = balance - uncheckPaymentAmount;
	    			double dCanUseBalance = availableBalance;

	    			FirstLimitTypeID = rs.getLong("firstLimitTypeId");
	    			SecondLimitTypeID = rs.getLong("secondLimitTypeId");
	    			ThirdLimitTypeID = rs.getLong("thirdLimitTypeId");
	    			balance = rs.getDouble("balance");
	    			
	    			
	    			if(FirstLimitTypeID > 0)
	    			{
	    				dFirstLimitAmount = rs.getDouble("firstLimitAmount");
	    			}
	    			if(SecondLimitTypeID > 0)
	    			{
	    				dSecondLimitAmount = rs.getDouble("secondLimitAmount");
	    			}
	    			if(ThirdLimitTypeID > 0)
	    			{
	    				dThirdLimitAmount = rs.getDouble("thirdLimitAmount");
	    			}
	    			if ( rs.getDouble("capitalLimitAmount") > 0 )
	    			{
	    				dCapitalLimitAmount = rs.getDouble("capitalLimitAmount");;
	    			}			
	    			
	    			if(dFirstLimitAmount>0)
	    			{
	    				dCanUseBalance = dCanUseBalance + dFirstLimitAmount - dCapitalLimitAmount;
	    			}
	    			else if(dSecondLimitAmount>0)
	    			{
	    				dCanUseBalance = dCanUseBalance + dSecondLimitAmount - dCapitalLimitAmount;
	    			}
	    			else if(dThirdLimitAmount>0)
	    			{
	    				dCanUseBalance = dCanUseBalance + dThirdLimitAmount - dCapitalLimitAmount;
	    			}
	    			else
	    			{
	    				dCanUseBalance = dCanUseBalance - dCapitalLimitAmount;
	    			}

	    		//计算合计金额
	    		   int g=0;
	    		   for(g=0;g<lDepositAccountType.length;g++)
	    		   {
	    		   	    if(AccountTypeID==lDepositAccountType[g])
	    		   	    {
	    		   	    	depositCanUseBalanceSum = depositCanUseBalanceSum + dCanUseBalance;
	    		   	    	break;
	    		   	    }
	    		   }
	    		   for(g=0;g<lLoanAccountType.length;g++)
	    		   {
	    		   		if(AccountTypeID==lLoanAccountType[g])
	    		   		{
	    		   			loanCanUseBalanceSum = loanCanUseBalanceSum + dCanUseBalance;
	    		   			break;
	    		   		}
	    		   }
	    		   for(g=0;g<lDepositAccountType.length;g++)
	    		   {
	    		   	    if(AccountTypeID==lDepositAccountType[g])
	    		   	    {
	    		   	    	depositBalanceSum = depositBalanceSum + balance;
	    		   	    	break;
	    		   	    }
	    		   }
	    		   for(g=0;g<lLoanAccountType.length;g++)
	    		   {
	    		   		if(AccountTypeID==lLoanAccountType[g])
	    		   		{
	    		   			loanBalanceSum = loanBalanceSum + balance;
	    		   			break;
	    		   		}
	    		   }
	    		}
	            
	            list.add("存款余额合计{"+(depositBalanceSum!=0.0?DataFormat.formatDisabledAmount(depositBalanceSum/lUnit):"0.00")+"}");
		        list.add("贷款余额合计{"+(loanBalanceSum!=0.0?DataFormat.formatDisabledAmount(loanBalanceSum/lUnit):"0.00")+"}");
		        list.add("存款可用余额合计{"+(depositCanUseBalanceSum!=0.0?DataFormat.formatDisabledAmount(depositCanUseBalanceSum/lUnit):"0.00")+"}");
		        list.add("贷款可用余额合计{"+(loanCanUseBalanceSum!=0.0?DataFormat.formatDisabledAmount(loanCanUseBalanceSum/lUnit):"0.00")+"}");
	        	
			}
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	public PagerInfo queryQueryTransaction(QueryTransactionConditionInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			sql = qQueryAccountDao.queryTransactionSQL(qInfo);
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "transactionResultSetHandle" , paramMap);
			pagerInfo.setTotalExtensionMothod(QQueryAccountBiz.class, "getTransactionSumAmount" , paramMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList transactionResultSetHandle(ResultSet rs , Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		Sett_AccountDAO aDao = new Sett_AccountDAO();
		Timestamp execute = null;
		long ID = -1;
		String serialNo = "";
		long transactionTypeID = -1;
		String transNo = "";
		double receiveAmount = 0.0;
		String sAccountNo = "";
		long lAccountTypeID = -1;
		String receiveBakAccountNo = "";
		String payBakAccountNo = "";
		String payAccountNo = "";
		String receiveAccountNo = "";
		long statusID = -1;
		String sAbstract = "";
		long inputUserID = -1;
		long checkUserID = -1;
		long transDirection = -1;
		long lUnit = 1;
//	    if (strUnit!=null && strUnit.trim().length()>0){
//	    	lUnit = Long.parseLong(strUnit);
//	    }
		QueryTransactionConditionInfo qInfo = (QueryTransactionConditionInfo)map.get("qInfo");
		lUnit = qInfo.getUnit();
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					transactionTypeID = rs.getLong("TransactionTypeID");
					
					if(transactionTypeID==SETTConstant.TransactionType.REPORTLOSS
			            	||transactionTypeID==SETTConstant.TransactionType.REPORTFIND
			            	||transactionTypeID==SETTConstant.TransactionType.CHANGECERTIFICATE
			            	||transactionTypeID==SETTConstant.TransactionType.FREEZE
			            	||transactionTypeID==SETTConstant.TransactionType.DEFREEZE
			            	)
			             continue;
					
					execute = rs.getTimestamp("Execute");
					
					transNo = rs.getString("TransNo")==null?"":rs.getString("TransNo");
					receiveAmount = rs.getDouble("ReceiveAmount");
					sAccountNo = rs.getString("strAccountNo");
					receiveBakAccountNo = rs.getString("ReceiveBakAccountNo");
					payBakAccountNo = rs.getString("PayBakAccountNo");
					receiveAccountNo = rs.getString("ReceiveAccountNo");
					payAccountNo = rs.getString("PayAccountNo");
					ID = rs.getLong("ID");
					serialNo = rs.getString("SerialNo");
					inputUserID = rs.getLong("InputuserID");
					checkUserID = rs.getLong("CheckuserID");
					statusID = rs.getLong("StatusID");
					sAbstract = rs.getString("Abstract");
					
					AccountInfo ainfo = aDao.findByAccountNO(sAccountNo);
					lAccountTypeID = ainfo.getAccountTypeID();
					
					if(SETTConstant.AccountType.isBakAccountType(lAccountTypeID))
		            {//如果是查询备付金账户
		            	if (sAccountNo.equals(receiveBakAccountNo)) 
		    			{
		                    transDirection = 8;
		    				//dReceiveSum = dReceiveSum + resultInfo.getReceiveAmount();
		                } 
		    			else if (sAccountNo.equals(payBakAccountNo)) 
		    			{
		    				transDirection = 9;
		    				//dPaySum = dPaySum + resultInfo.getReceiveAmount();
		                }
		    			else if (sAccountNo.equals(receiveAccountNo)) 
		    			{
		    				transDirection = 8;
		    				//dReceiveSum = dReceiveSum + resultInfo.getReceiveAmount();
		                } 
		    			else if (sAccountNo.equals(payAccountNo)) 
		    			{
		    				transDirection = 9;
		    				//dPaySum = dPaySum + resultInfo.getReceiveAmount();
		                }
		            	
		            	//如果备付金账户不为空，说明是通存通兑交易。收付方账户号分别取收付方备付金账号
		            	if(receiveBakAccountNo!=null && receiveBakAccountNo.trim().length()>0)
		            	{
		            		receiveAccountNo = receiveBakAccountNo;
		            	}
		            	if(payBakAccountNo!=null && payBakAccountNo.trim().length()>0)
		            	{
		            		payAccountNo = payBakAccountNo;
		            	}
		            }
		            else
		            {//如果不是查询备付金账户
		            	if (sAccountNo.equals(receiveAccountNo)) 
		    			{
		            		transDirection = 8;
		    				//dReceiveSum = dReceiveSum + resultInfo.getReceiveAmount();
		                } 
		    			else if (sAccountNo.equals(payAccountNo)) 
		    			{
		    				transDirection = 9;
		    				//dPaySum = dPaySum + resultInfo.getReceiveAmount();
		                }
		            }
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,DataFormat.getDateString(execute));
					if (SETTConstant.TransactionType.isInterestTransaction(transactionTypeID))
					{
						PagerTools.returnCellList(cellList,((transNo == null || transNo.equals("")) ? "" : transNo)+","+" "+","+" "+","+" "+","+" ");
					}
					else
					{
						PagerTools.returnCellList(cellList,((transNo == null || transNo.equals("")) ? "" : transNo)+","+ID+","+transactionTypeID+","+transNo+","+serialNo);
					}
					PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(transactionTypeID));
					PagerTools.returnCellList(cellList,sAccountNo);
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(receiveAmount/lUnit));
					PagerTools.returnCellList(cellList,SETTConstant.ReceiveOrPay.getName(transDirection));
					PagerTools.returnCellList(cellList,transDirection==8?(payAccountNo == null ? "" : payAccountNo):(receiveAccountNo == null ? "" : receiveAccountNo));
					PagerTools.returnCellList(cellList,SETTConstant.TransactionStatus.getName(statusID));
					PagerTools.returnCellList(cellList,sAbstract == null ? "" : sAbstract);
					PagerTools.returnCellList(cellList,(inputUserID>0 ? NameRef.getUserNameByID(inputUserID) : ""));
					PagerTools.returnCellList(cellList,(checkUserID>0||checkUserID == -101)?NameRef.getUserNameByID(checkUserID):"");

			          
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public ArrayList getTransactionSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			QTransaction qobj = new QTransaction();
			QueryTransactionConditionInfo qInfo = (QueryTransactionConditionInfo)map.get("qInfo");
			long lUnit = 1;
			lUnit = qInfo.getUnit();
			
			QueryTransactionNewInfo qnpayInfo = new QueryTransactionNewInfo();
			QueryTransactionNewInfo qnreceiveInfo = new QueryTransactionNewInfo();
			qnpayInfo = qobj.getPayAmountSumForQueryNew(qInfo);
			qnreceiveInfo = qobj.getReceiveAmountSumForQueryNew(qInfo);
			
			double dPaySum = qnpayInfo.getPaySum();
			double dReceiveSum = qnreceiveInfo.getReceiveSum();
			long lCountPay = qnpayInfo.getCountPay();
			long lCountReceive = qnreceiveInfo.getCountReceive();
			long lCount = lCountPay+lCountReceive;
	            
            list.add("收方金额合计{"+DataFormat.formatDisabledAmount(dReceiveSum/lUnit)+"}");
	        list.add("付方金额合计{"+DataFormat.formatDisabledAmount(dPaySum/lUnit)+"}");
	        list.add("记录合计{"+DataFormat.formatNumber(lCount)+"}");
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	
		
	}
	
	public PagerInfo queryQueryAccountAmount(QueryAccountAmountWhereInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = qQueryAccountDao.queryAccountAmountInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "accountAmountResultSetHandle");
			
			Map map = new HashMap();
			map.put("params", qInfo);
			pagerInfo.setTotalExtensionMothod(QQueryAccountBiz.class, "getAccountAmountSumAmount" , map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList accountAmountResultSetHandle(ResultSet rs) throws Exception{

		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long accountID = -1;
 		String sAccountNo = "";
 		String name = "";
		long lAccountTypeID = -1;
		double startBalance = 0.0;
		double endBalance = 0.0;
		double payAmount = 0.0;
		double recAmount = 0.0;
		long lUnit = 1;
//	    if (strUnit!=null && strUnit.trim().length()>0){
//	    	lUnit = Long.parseLong(strUnit);
//	    }
	    
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					accountID = rs.getLong("AccountID");
					sAccountNo = rs.getString("AccountNo");
					name = rs.getString("Name");
					lAccountTypeID = rs.getLong("AccountTypeID");
					startBalance = rs.getDouble("StartBalance");
					payAmount = rs.getDouble("PayAmount");
					recAmount = rs.getDouble("RecAmount");
					endBalance = rs.getDouble("EndBalance");
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,sAccountNo+","+accountID+","+sAccountNo);
					PagerTools.returnCellList(cellList,name);
					PagerTools.returnCellList(cellList,SETTConstant.AccountType.getName(lAccountTypeID));
					PagerTools.returnCellList(cellList,"￥"+(startBalance!=0.0?DataFormat.formatDisabledAmount(startBalance/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,"￥"+(payAmount!=0.0?DataFormat.formatDisabledAmount(payAmount/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,"￥"+(recAmount!=0.0?DataFormat.formatDisabledAmount(recAmount/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,"￥"+(endBalance!=0.0?DataFormat.formatDisabledAmount(endBalance/lUnit):"0.00"));
			          
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public ArrayList getAccountAmountSumAmount(ResultSet rs , Map map) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			QueryAccountAmountWhereInfo qInfo = (QueryAccountAmountWhereInfo)map.get("params");
			long lUnit = 1;
			if(rs!=null)
			{
				
				QAccountAmountInfoDao qaid = new QAccountAmountInfoDao();
				// 查询的总计结果集
	            QueryAccountSumInfo qasi = qaid.getDepositLoanSum(qInfo);
		
		        list.add("存款合计{"+DataFormat.formatDisabledAmount(qasi.getDepositBalanceSum()/lUnit)+"}");
		        list.add("贷款合计{"+(qasi.getLoanBalanceSum()!=0?DataFormat.formatDisabledAmount(qasi.getLoanBalanceSum()/lUnit):"0.00")+"}");
	        	
			}
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	public PagerInfo queryQueryAccountDetail(QueryAccountAmountWhereInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = qQueryAccountDao.queryAccountDetailInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "accountDetailResultSetHandle");
			
			pagerInfo.setTotalExtensionMothod(QQueryAccountBiz.class, "getAccountDetailSumAmount");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList accountDetailResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		Timestamp executeDate = null;
		long transactionTypeID = -1;
		long trnasID = -1;
		String transNo = "";
		long transAccountId = -1;
		double amount = 0.0;
		long amountType = -1;
		long transDirection = -1;
		String oppAccountNo = "";
		long oppAccountId = -1;
		long statusID = -1;
		String remark = "";
		long inputUserID = -1;
		long checkUserID = -1;
		String strCheckUser = "";
	    long ID = -1;
	    String serialNo = "";
		String accountTypeId = "";
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					executeDate = rs.getTimestamp("executeDate");
					transactionTypeID = rs.getLong("transactionTypeId");
					trnasID = rs.getLong("trnasID");
					transNo = rs.getString("transNo");
					transAccountId = rs.getLong("transAccountId");
					amount = rs.getDouble("amount");
					amountType = rs.getLong("amounttype");
					transDirection =  rs.getLong("transDirection");
					oppAccountNo = rs.getString("oppAccountNo");
					oppAccountId = rs.getLong("oppAccountId");
					statusID = rs.getLong("statusId");
					remark = rs.getString("remark");
					inputUserID = rs.getLong("InputUserID");
					ID = rs.getLong("ID");
					serialNo = rs.getString("serialNo");
					accountTypeId = rs.getString("accountTypeId");
					
					checkUserID = rs.getLong("CheckUserID");
					if(checkUserID>0){
						strCheckUser = NameRef.getUserNameByID(checkUserID);
					}
					else if(checkUserID == -1) {
						strCheckUser = "机核";
					}
					else if(checkUserID == -100 || checkUserID == -101) {
						strCheckUser = NameRef.getUserNameByID(checkUserID);
					}
					
					//存储列数据
					cellList = new ArrayList();
					if (SETTConstant.AccountType.isDepositAccountType(Long.valueOf(accountTypeId).longValue())){
						if (transDirection == SETTConstant.DebitOrCredit.DEBIT) {
			                transDirection = SETTConstant.ReceiveOrPay.PAY;
						} else if (transDirection == SETTConstant.DebitOrCredit.CREDIT) {
			                transDirection = SETTConstant.ReceiveOrPay.RECEIVE;
						}
					}
					else if (SETTConstant.AccountType.isLoanAccountType(Long.valueOf(accountTypeId).longValue())){
						if (transDirection == SETTConstant.DebitOrCredit.DEBIT) {
			                transDirection = SETTConstant.ReceiveOrPay.RECEIVE;
						} else if (transDirection == SETTConstant.DebitOrCredit.CREDIT) {
			                transDirection = SETTConstant.ReceiveOrPay.PAY;
						}
					}
					PagerTools.returnCellList(cellList,DataFormat.getDateString(executeDate));
					if (SETTConstant.TransactionType.isInterestTransaction(transactionTypeID) || (transactionTypeID!=SETTConstant.TransactionType.ONETOMULTI && trnasID<0))
					{
						PagerTools.returnCellList(cellList,((transNo == null || transNo.equals("")) ? "&nbsp;" : transNo)+","+" "+","+" "+","+" "+","+" "+","+" ");
					}
					else
					{
						PagerTools.returnCellList(cellList,(((transNo == null || transNo.equals("")) ? "&nbsp;" : transNo))+","+ID+","+transactionTypeID+","+trnasID+","+transNo+","+serialNo);
					}
					PagerTools.returnCellList(cellList,SETTConstant.TransactionType.getName(transactionTypeID));
					PagerTools.returnCellList(cellList,transAccountId>-1?NameRef.getAccountNoByID(transAccountId):"&nbsp;");
					PagerTools.returnCellList(cellList,"￥"+DataFormat.formatListAmount(amount));
					PagerTools.returnCellList(cellList,SETTConstant.AmountType.getName(amountType));
					PagerTools.returnCellList(cellList,SETTConstant.ReceiveOrPay.getName(transDirection));
					if(transactionTypeID == SETTConstant.TransactionType.OPENNOTIFYACCOUNT){
						PagerTools.returnCellList(cellList,oppAccountNo);
					}else{
						PagerTools.returnCellList(cellList,oppAccountId>-1?NameRef.getAccountNoByID(oppAccountId):"&nbsp;");
					}
					PagerTools.returnCellList(cellList,statusID>-1?SETTConstant.TransactionStatus.getName(statusID):"&nbsp;");
					PagerTools.returnCellList(cellList,remark == null?"&nbsp;":remark);
					PagerTools.returnCellList(cellList,inputUserID>0?NameRef.getUserNameByID(inputUserID):"&nbsp;");
					PagerTools.returnCellList(cellList,strCheckUser);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public ArrayList getAccountDetailSumAmount(ResultSet rs) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			
			if(rs!=null)
			{
				double dPaySum = 0.0;
				double dReceiveSum = 0.0;
				long accountTypeId = -1;
				double amount = 0.0;
				long transDirection = -1;
				int resultCount = 0;
				
				while(rs.next())
				{
					resultCount++;
					accountTypeId = rs.getLong("accountTypeId");
					amount = rs.getDouble("AMOUNT");
					transDirection = rs.getLong("transDirection");
					
					if (SETTConstant.AccountType.isDepositAccountType(accountTypeId)){
						if (transDirection == SETTConstant.DebitOrCredit.DEBIT) {
			                transDirection = SETTConstant.ReceiveOrPay.PAY;
							dPaySum = dPaySum + amount;
						} else if (transDirection == SETTConstant.DebitOrCredit.CREDIT) {
			                transDirection = SETTConstant.ReceiveOrPay.RECEIVE;
							dReceiveSum = dReceiveSum + amount;
						}
					}
					else if (SETTConstant.AccountType.isLoanAccountType(accountTypeId)){
						if (transDirection == SETTConstant.DebitOrCredit.DEBIT) {
			                transDirection = SETTConstant.ReceiveOrPay.RECEIVE;
							dReceiveSum = dReceiveSum + amount;
						} else if (transDirection == SETTConstant.DebitOrCredit.CREDIT) {
			                transDirection = SETTConstant.ReceiveOrPay.PAY;
							dPaySum = dPaySum + amount;
						}
					}
					
		        }
		
		        list.add("付方金额合计{"+DataFormat.formatDisabledAmount(dPaySum)+"}");
		        list.add("收方金额合计{"+DataFormat.formatDisabledAmount(dReceiveSum)+"}");
		        list.add("记录合计{"+resultCount+"}");
	        	
			}
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	} 
	
	public PagerInfo queryQueryAccountBalance(QueryAccountWhereInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			boolean isToday = isToday(qInfo.getOfficeID(), qInfo.getCurrencyID(), qInfo.getQueryDate());
			if (isToday)
				sql = qQueryAccountDao.queryTodayAccountBalanceInfoSQL(qInfo);
			else
				sql = qQueryAccountDao.queryHistoryAccountBalanceInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			paramMap.put("isToday", isToday?"1":"0");
			paramMap.put("qawi", qInfo);
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "accountBalanceResultSetHandle" , paramMap);
			
			pagerInfo.setTotalExtensionMothod(QQueryAccountBiz.class, "getAccountBalanceSumAmount" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public boolean isToday(long lOfficeID, long lCurrencyID, Timestamp tsQueryDate)
	{
		boolean b = true;
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && tsQueryDate != null)
		{
			if (tsOpenDate.toString().substring(0, 10).equals(tsQueryDate.toString().substring(0, 10)))
				b = true;
			else
				b = false;
		}
		return b;
	}
	
	public ArrayList accountBalanceResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		String accountNo = "";
		String clientCode = "";
		String clientName = "";
		long accountTypeID = -1;
		String depositNo = "";
		String contractCode = "";
		String loanPayCode = "";
		double balance = 0.0;
		double minterestbalance = 0.0;
		double negotiateBalance = 0.0;
		long negotiateUnit = -1;
		double negotiateAmount = 0.0;
		double dNegotiateBalance = 0.0;
		double interestRate = 0.0;
		double contractInterestRate = 0.0;
		long loanPayID = -1;
		Timestamp balanceDate = null;
		long subAccountID = -1;
		long isNegotiate = -1;
		double negotiateRate = 0.0;
		double interest = 0.0;
		double negotiateInterest = 0.0;
		long isToday = -1;
		double fixPreDrawInterest = 0.0;
		Timestamp accountOpenDate = null;
		long interestPlanID = -1;
		double fixInterestRate = 0.00;
		
		String iIsToday = paramMap.get("isToday").toString();
		boolean bIsToday = false;
		if(iIsToday.equals("1")){
			bIsToday = true;
		}
		QueryAccountWhereInfo qawi = (QueryAccountWhereInfo)paramMap.get("qawi");
		long lUnit = qawi.getUnit();
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					accountNo = rs.getString("AccountNo");
					clientCode = rs.getString("ClientCode");
					clientName = rs.getString("ClientName");
					accountTypeID = rs.getLong("AccountTypeID");
					depositNo = rs.getString("DepositNo");
					contractCode = rs.getString("ContractCode");
					loanPayCode = rs.getString("LoanpayCode");
					balance =  rs.getDouble("Balance");
					minterestbalance = rs.getDouble("Minterestbalance");
					negotiateBalance = rs.getDouble("NegotiateBalance");
					negotiateUnit = rs.getLong("NegotiateUnit");
					negotiateAmount = rs.getDouble("NegotiateAmount");
					interestRate = rs.getDouble("InterestRate");
					contractInterestRate = rs.getLong("ContractInterestRate");
					loanPayID = rs.getLong("LoanPayID");
					balanceDate = rs.getTimestamp("BalanceDate");
					subAccountID = rs.getLong("SubAccountID");
					isNegotiate = rs.getLong("IsNegotiate");
					negotiateRate = rs.getDouble("NegotiateRate");
					interest = rs.getDouble("Interest");
					negotiateInterest = rs.getDouble("NegotiateInterest");
					isToday = rs.getLong("isToday");
					fixPreDrawInterest = rs.getDouble("FixPreDrawInterest");
					accountOpenDate = rs.getTimestamp("AccountOpenDate");
					interestPlanID = rs.getLong("InterestPlanID");
					fixInterestRate = rs.getDouble("FixInterestRate");
					
					String strNegotiateInterest = "￥0.00";
					// 历史协定存款余额
					if( isToday == 0 && isNegotiate > 0 )
					{
						dNegotiateBalance =  java.lang.Math.floor((balance - negotiateAmount ) / negotiateUnit) * negotiateUnit;
					}
					// 当天协定存款余额
					if( isToday == 1 && isNegotiate > 0 )
					{
						if( negotiateUnit > 0 )
						{
				    		dNegotiateBalance =java.lang.Math.floor((balance - negotiateAmount ) / negotiateUnit) * negotiateUnit ;
						}
					}
					strNegotiateInterest = "￥"+(negotiateInterest>0?DataFormat.formatDisabledAmount(negotiateInterest/lUnit) : "0.00");

					double dInterestRate = 0.00 ;
					if (isToday == 0){
			            // 如果查询日期不是当天，利率取自sett_DailyAccountBalance
						dInterestRate = interestRate;
					}else{
			            // 如果查询日期是当日,利率取法如下
						if (SETTConstant.AccountType.isCurrentAccountType(accountTypeID)
						        ||SETTConstant.AccountType.isOtherDepositAccountType(accountTypeID)){
							
							dInterestRate = BaseQueryObject.getCurrentInterestRate(accountOpenDate, balanceDate, interestPlanID, balance);
						}else if (SETTConstant.AccountType.isFixAccountType(accountTypeID)
							        ||SETTConstant.AccountType.isNotifyAccountType(accountTypeID)){
							
							dInterestRate = fixInterestRate;
						} else {
							
							dInterestRate = contractInterestRate;
						}
					}
					
					UtilOperation utilOperation = new UtilOperation();
					// 取利率调整后的贷款利率（不包括贴现）
					if( SETTConstant.AccountType.isLoanAccountType(accountTypeID)) {
						if (SETTConstant.AccountType.isDiscountAccountType(accountTypeID)) {
							dInterestRate = contractInterestRate ;
						} else {
							dInterestRate = utilOperation.getLatelyRate(loanPayID,balanceDate);
						}
					}
					else if( SETTConstant.AccountType.isMarginAccountType(accountTypeID))
					{
						dInterestRate = interestRate;
					}
					
					long marginSubaccountId = -1;
					long loanNoteID         = -1;
					String loanNoteNo       ="";
					String contractNo       ="";
					if(SETTConstant.AccountType.isMarginAccountType(accountTypeID))
					{
						marginSubaccountId = subAccountID;
						loanNoteID= NameRef.getLoanNoteIdBySubaccountID(marginSubaccountId);
						contractNo=NameRef.getContractIDByLoanID(loanNoteID);//合同号
						loanNoteNo=NameRef.getAssureCodeByID(loanNoteID);//放款通知单号
						depositNo=NameRef.getDepositNoBySubaccountID(marginSubaccountId);//定期存单号
						dInterestRate=NameRef.getMarginRateBySubaccountID(marginSubaccountId);//利率
						depositNo = DataFormat.formatString(depositNo);
						contractCode = DataFormat.formatString(contractNo);
						loanPayCode = DataFormat.formatString(loanNoteNo);
						interestRate = dInterestRate;
					}
					
					if (SETTConstant.AccountType.isFixAccountType(accountTypeID) ||
						SETTConstant.AccountType.isNotifyAccountType(accountTypeID)) {
						interest = fixPreDrawInterest;
					}
					
		            String showMinterestBalance = "￥0.00";     //显示计息余额
		            String showNegotiateBalance = "￥0.00";     //显示协定存款计息余额
		            // 历史
					if(!bIsToday){
		            	showMinterestBalance = "￥"+(minterestbalance>0.0?DataFormat.formatDisabledAmount(minterestbalance/lUnit):"0.00") ;
		            	showNegotiateBalance = "￥"+(negotiateBalance>0.0?DataFormat.formatDisabledAmount(negotiateBalance/lUnit):"0.00") ;
		            }
		            
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,accountNo);
					PagerTools.returnCellList(cellList,clientCode);
					PagerTools.returnCellList(cellList,clientName);
					PagerTools.returnCellList(cellList,SETTConstant.AccountType.getName(accountTypeID));
					PagerTools.returnCellList(cellList,(SETTConstant.AccountType.isFixAccountType(accountTypeID)|| SETTConstant.AccountType.isMarginAccountType(accountTypeID) || SETTConstant.AccountType.isNotifyAccountType(accountTypeID))? depositNo:"" );
					PagerTools.returnCellList(cellList,contractCode);
					PagerTools.returnCellList(cellList,loanPayCode);
					PagerTools.returnCellList(cellList,"￥"+(balance>0.0?DataFormat.formatDisabledAmount(balance/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,showMinterestBalance);
					PagerTools.returnCellList(cellList,"￥"+(dNegotiateBalance>0.0?DataFormat.formatDisabledAmount(dNegotiateBalance/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,showNegotiateBalance);
					PagerTools.returnCellList(cellList,DataFormat.formatRate(dInterestRate));
					PagerTools.returnCellList(cellList,isNegotiate>0?String.valueOf(negotiateRate):"");
					PagerTools.returnCellList(cellList,"￥"+(interest!=0.0?DataFormat.formatDisabledAmount(interest/lUnit):"0.00"));
					PagerTools.returnCellList(cellList,strNegotiateInterest);

					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public ArrayList getAccountBalanceSumAmount(ResultSet rs , Map paramMap) throws Exception{
		
		try{
			
			ArrayList list = new ArrayList();
			
			if(rs!=null)
			{
				String showMinterestBalanceSum = "";  //显示计息余额合计
			    String showNegotiateBalanceSum = "";  //显示协定计息余额合计
			    double interest = 0.0;
				long accountTypeID = -1;
				
				String iIsToday = paramMap.get("isToday").toString();
				boolean bIsToday = false;
				if(iIsToday.equals("1")){
					bIsToday = true;
				}
				
				QAccountBalance qobj = new QAccountBalance();
				QueryAccountWhereInfo qawi = (QueryAccountWhereInfo)paramMap.get("qawi");
				long lUnit = qawi.getUnit();
				
				QueryAccountSumInfo qasi = qobj.queryAccountBalanceSum(qawi);
				
				while(rs.next()){
					interest = rs.getDouble("interest");
					accountTypeID = rs.getLong("accountTypeID");
					if (SETTConstant.AccountType.isFixAccountType(accountTypeID) || SETTConstant.AccountType.isNotifyAccountType(accountTypeID)) 
						qasi.setInterestSum(qasi.getInterestSum()+interest);
				}
				
				if(!bIsToday){
				    showMinterestBalanceSum   = qasi.getMinterestbalanceSum()>0?DataFormat.formatDisabledAmount(qasi.getMinterestbalanceSum()/lUnit):"0.00";
			        showNegotiateBalanceSum   = qasi.getNegotiateBalanceSum()>0?DataFormat.formatDisabledAmount(qasi.getNegotiateBalanceSum()/lUnit):"0.00";
				}
				 
		        list.add("余额合计{"+(qasi.getBalanceSum()>0?DataFormat.formatDisabledAmount(qasi.getBalanceSum()/lUnit):"0.00")+"}");
		        list.add("利息合计{"+(qasi.getNegotiateInterestSum()+qasi.getInterestSum()>0?DataFormat.formatDisabledAmount((qasi.getNegotiateInterestSum()+qasi.getInterestSum())/lUnit):"0.00")+"}");
		        list.add("协定存款余额合计{"+(qasi.getNegotiateBalanceSum()>0?DataFormat.formatDisabledAmount(qasi.getNegotiateBalanceSum()/lUnit):"0.00")+"}");
		        list.add("应计利息合计{"+(qasi.getInterestSum()>0?DataFormat.formatDisabledAmount(qasi.getInterestSum()/lUnit):"0.00")+"}");
		        list.add("计息余额合计{"+showMinterestBalanceSum+"}");
		        list.add("协定利息合计{"+(qasi.getNegotiateInterestSum()>0?DataFormat.formatDisabledAmount(qasi.getNegotiateInterestSum()/lUnit):"0.00")+"}");
		        list.add("协定计息余额合计{"+showNegotiateBalanceSum+"}");
	        	
			}
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	public PagerInfo queryQueryTransAccountDetail(QueryTransAccountDetailWhereInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			sql = qQueryAccountDao.queryQueryTransAccountDetail(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			paramMap.put("qInfo", qInfo);
			
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "transAccountDetailResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList transAccountDetailResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		
		Timestamp startDate = null;
		Timestamp endDate = null;
		long accountID = -1;
		String accountNo = "";
		String clientCode = "";
		String clientName = "";
		double historyBalance = 0.0;
		long lUnit = 1;
		long isFilterNull = -1;
		long isIntrDate = -1;
		String accountStatusIDs = "";
		
		QueryTransAccountDetailWhereInfo qawi = (QueryTransAccountDetailWhereInfo)paramMap.get("qInfo");
		lUnit = qawi.getUnit();
		startDate = qawi.getStartDate();
		endDate = qawi.getEndDate();
		isFilterNull = qawi.getIsFilterNull();
		isIntrDate = qawi.getIsIntrDate();
		accountStatusIDs = qawi.getAccountStatusIDs();
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					
					accountID = rs.getLong("AccountID");
					accountNo = rs.getString("AccountNo");
					clientCode = rs.getString("ClientCode");
					clientName = rs.getString("ClientName");
					historyBalance = rs.getDouble("HistoryBalance");
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,accountID+"");
					PagerTools.returnCellList(cellList,accountNo+","+accountID+","+startDate+","+endDate+","+isFilterNull+","+isIntrDate+","+accountStatusIDs);
					PagerTools.returnCellList(cellList,clientCode);
					PagerTools.returnCellList(cellList,clientName);
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(historyBalance/lUnit));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}

	public PagerInfo queryTransAccountDetail(QueryTransAccountDetailWhereInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			sql = "select 1 from sys_user";//qQueryAccountDao.queryTransAccountDetail(qInfo);
			double dBalance = 0.0;
			double dCanUseBalance = 0.0;
			QTransAccountDetail qobj = new QTransAccountDetail();
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			if(1 == qInfo.getIsIntrDate() ){
				dBalance = qobj.queryTransAccountBalanceForIntr(qInfo);//账户的期初计息余额
			}
			else{
				dBalance = qobj.queryTransAccountBalance(qInfo);//账户的期初余额
			}
			dCanUseBalance = qobj.queryAccountCanUseBalance(qInfo);//账户的可用余额
			paramMap.put("qInfo", qInfo);
			paramMap.put("dBalance",dBalance+"");
			paramMap.put("dCanUseBalance",dCanUseBalance+"");
			
			pagerInfo.setExtensionMothod(QQueryAccountBiz.class, "transDetailResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList transDetailResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long isIntrDate = -1;
		Timestamp interestStartDate = null;
		Timestamp executeDate = null;
		String sAbstract = "";
		long transTypeId = -1;
		String transNo = "";
		long groupId = -1;
		String billNo = "";
		String oppAccountNo = "";
		String oppAccountName = "";
		double payAmount = 0.0;
		double receiveAmount = 0.0;
		double balance = 0.0;
		Timestamp tsTempDate = null;
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String executeMonth = "";
		String executeYear = "";
		double dDayPayBalance = 0.0;
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dDayReceiveBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		
		
		QueryTransAccountDetailWhereInfo qInfo = (QueryTransAccountDetailWhereInfo)paramMap.get("qInfo");
		isIntrDate = qInfo.getIsIntrDate();
		QTransAccountDetail qobj = new QTransAccountDetail();
		Collection coll = qobj.queryTransAccountDetail(qInfo);
		
		try{
			
			tsStartDate = qInfo.getStartDate();
			tsEndDate = qInfo.getEndDate();
			
			double dBalance = Double.valueOf(paramMap.get("dBalance").toString()).doubleValue();
			double dCanUseBalance = Double.valueOf(paramMap.get("dCanUseBalance").toString()).doubleValue();
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,DataFormat.formatDate(tsStartDate));
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>期初余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance))+"</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
			
			for(tsTempDate = tsStartDate ; (tsTempDate.before(tsEndDate)||tsTempDate.equals(tsEndDate)) ; tsTempDate = DataFormat.getNextMonth(tsTempDate,12) )
			{
			
				dYearPayBalance = 0.0;
				dYearReceiveBalance = 0.0;
				
				for(tsTempDate = tsStartDate ; (DataFormat.getMonthString(tsTempDate).equals(DataFormat.getMonthString(tsEndDate))||tsTempDate.before(tsEndDate)||DataFormat.formatDate(tsTempDate).equals(DataFormat.formatDate(tsEndDate))) ; tsTempDate = getNextMonth1(tsTempDate,1) )
				{
					
					dMonthPayBalance = 0.0;
					dMonthReceiveBalance = 0.0;
					
					Collection tempColl = coll;
					Iterator it = null;
					if(tempColl != null)
					{
						it = tempColl.iterator();
					}
					if(it != null && it.hasNext())
					{
						while(it.hasNext())
						{
							QueryTransAccountDetailResultInfo qtri = (QueryTransAccountDetailResultInfo)it.next();
						
							if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue()+1 != qtri.getExecuteMonth())
							{//如果不是本月的则不显示在本月范围之内
								continue;
							}
							if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != qtri.getExecuteYear())
							{//如果不是本年的则不显示在本年范围之内
								continue;
							}
							if(qtri.getTransTypeID() > -1000)//如果不是日合计的 金额
							{
								dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
								dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
								dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
								dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
							}
							//存储列数据
							cellList = new ArrayList();
							if(isIntrDate == 1){
								PagerTools.returnCellList(cellList,qtri.getInterestStartDate() != null ? DataFormat.formatDate(qtri.getInterestStartDate()) : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;");
							}else{
								PagerTools.returnCellList(cellList,qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getInterestStartDate() != null ? DataFormat.formatDate(qtri.getInterestStartDate()) : "&nbsp;");
							}
							PagerTools.returnCellList(cellList,qtri.getAbstract());
							if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
						    {
								PagerTools.returnCellList(cellList,"&nbsp;");
						    }else{
						    	if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID())){
						    		PagerTools.returnCellList(cellList,qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;");
						    	}else{
						    		PagerTools.returnCellList(cellList,(qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;")+","+qtri.getTransTypeID()+","+qtri.getTransNo()+","+qtri.getGroupID());
						    	}
						    }
							PagerTools.returnCellList(cellList,qtri.getBillNo());
							PagerTools.returnCellList(cellList,qtri.getOppAccountNo() != null ? qtri.getOppAccountNo() : "&nbsp;");
							PagerTools.returnCellList(cellList,qtri.getOppAccountName() != null ? qtri.getOppAccountName() : "&nbsp;");
							if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
						    {
								PagerTools.returnCellList(cellList,"<B>"+(qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00") + "</B>");
								PagerTools.returnCellList(cellList,"<B>"+(qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00") + "</B>");
								PagerTools.returnCellList(cellList,"<B>"+(qtri.getBalance() != 0 ? DataFormat.formatDisabledAmount(qtri.getBalance()) : "0.00") + "</B>");
						    }else{
						    	PagerTools.returnCellList(cellList,qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getBalance() != 0 ? DataFormat.formatDisabledAmount(qtri.getBalance()) : "0.00");
						    }
							
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
							
							dBalance = qtri.getBalance();
						}
					}
				
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.getLastDateString(tsTempDate));
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"<B>本月合计</B>");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"<B>"+(dMonthPayBalance != 0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dMonthReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
					
				}
				
				//存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1)));
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>本年合计</B>");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>"+(dYearPayBalance != 0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00") + "</B>");
				PagerTools.returnCellList(cellList,"<B>"+(dYearReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00") + "</B>");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
				//存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//返回数据
				resultList.add(rowInfo);
				
			}
			//存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>可用余额</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+(dCanUseBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dCanUseBalance)) + "</B>");
			//存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//返回数据
			resultList.add(rowInfo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
	public java.sql.Timestamp getNextMonth1(java.sql.Timestamp tsDate, int nMonth)
	{
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		java.sql.Timestamp returnTsDate = DataFormat.getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 + nMonth, calendar.get(Calendar.DATE), 0, 0, 0);
		if( returnTsDate.getMonth()-tsDate.getMonth()>nMonth)
		{	while(returnTsDate.getMonth()-tsDate.getMonth()>nMonth)
				returnTsDate=DataFormat.getPreviousDate(returnTsDate);
		}	
		return returnTsDate;
	}
}
