/*
 * Created on 2004-7-5
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.securities.securitiescontract.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerOperation;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.securities.notice.dao.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.*;
import java.sql.*;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractInterestDao extends SecuritiesDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public SecuritiesContractInterestDao(){
		super("Sec_ApplyContract");
	}
	
	//合同利息计提
	public long countSecuritiesContractInterest(SecuritiesContractInterestInfo info) throws Exception
	{
	    StringBuffer strSQL = new StringBuffer();
		long lCount = 0;
		long lResult = -1;
		long lContractID = -1;
		double dContractInterest = 0;
		double dContractRate = 0;
		double dContractBalance = 0;
		Timestamp tsContractInterestDate = null;
		long lDays = 0;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		SecuritiesContractInfo cInfo = new SecuritiesContractInfo();
		SecuritiesContractInfo cNewInfo = new SecuritiesContractInfo();
		ContractBalanceInfo bInfo = new ContractBalanceInfo();
		GenerateGLEntryParam generateGLEntryParam = new GenerateGLEntryParam();
		
		/*----------------- init DAO --------------------*/
		try 
		{
			initDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*---------------- end of init ------------------*/		
				
		try 
		{
		    SecuritiesGeneralLedgerOperation securitiesGeneralLedgerOperation = new SecuritiesGeneralLedgerOperation(this.transConn);
			
		    lCount = info.getContractID().length;
		    for (int i = 0; i < lCount; i++)
		    {
		        lContractID = info.getContractID()[i];
		        if (lContractID > 0)
		        {
		            log4j.info("取得合同当前信息");
		            cInfo = (SecuritiesContractInfo)dao.findByID(lContractID,cInfo.getClass());
		            log4j.info("合同编号：" + cInfo.getCode());
		            //上次计提的利息
		            if (cInfo.getContractInterest() > 0)
		            {
		                dContractInterest = cInfo.getContractInterest();
		            }
		            else
		            {
		                dContractInterest = 0;
		            }
		            //上次计提的日期
		            if (cInfo.getContractInterestDate() != null)
		            {
		                tsContractInterestDate = cInfo.getContractInterestDate();
		            } 
		            else if (cInfo.getInterestBeginDate() != null)
		            {
		                tsContractInterestDate = cInfo.getInterestBeginDate();
		            }
		            else
		            {
		                tsContractInterestDate = cInfo.getTransactionStartDate();
		            }
		            //上次计提时的合同余额
		            bInfo = dao.getContractBalance(lContractID,tsContractInterestDate);
		            dContractBalance = bInfo.getBalance();
		            
		            strSQL.setLength(0);
		            strSQL.append(" select nvl(t1.ExecuteRate,t3.Rate) Rate ");
		            strSQL.append(" from Sec_NoticeForm t1,Sett_TransSecurities t2,Sec_ApplyContract t3 ");
		            strSQL.append(" where t1.ID = t2.nSecuritiesNoticeID ");
		            strSQL.append(" and t1.ContractID = t3.ID ");
		            strSQL.append(" and t2.nStatusID = " + SECConstant.TransactionStatus.CHECK);
		            strSQL.append(" and t1.TransactionTypeID in ( ");
					strSQL.append(SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY);
					strSQL.append(" , ");
					strSQL.append(SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY);
					strSQL.append(" ) ");
					strSQL.append(" and to_char(t2.dtSettlementDate,'YYYY-MM-DD') <= '" + DataFormat.getDateString(tsContractInterestDate) + "' ");
					strSQL.append(" and t1.ContractID = " + lContractID);
					strSQL.append(" order by t2.dtSettlementDate desc ");
		            					
		            log4j.debug(strSQL.toString());
					prepareStatement(strSQL.toString());
					ResultSet rs1 = executeQuery();
					if (rs1 != null && rs1.next())
					{
						//上次计提时的合同利率
						if (rs1.getDouble("Rate") > 0)
						{
						    dContractRate = rs1.getDouble("Rate");
						}
						else
						{
						    dContractRate = cInfo.getRate();
						}
					}
							            
					log4j.info("取得合同的通知单信息");
		            strSQL.setLength(0);
		            strSQL.append(" select t1.TransactionTypeID,t1.ContractID,nvl(t1.ExecuteRate,t3.Rate) Rate, ");
		            strSQL.append(" t2.mAmount,t2.dtSettlementDate ");
		            strSQL.append(" from Sec_NoticeForm t1,Sett_TransSecurities t2,Sec_ApplyContract t3 ");
		            strSQL.append(" where t1.ID = t2.nSecuritiesNoticeID ");
		            strSQL.append(" and t1.ContractID = t3.ID ");
		            strSQL.append(" and t2.nStatusID = " + SECConstant.TransactionStatus.CHECK);
		            strSQL.append(" and t1.TransactionTypeID in ( ");
					strSQL.append(SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY);
					strSQL.append(" , ");
					strSQL.append(SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY);
					strSQL.append(" , ");
					strSQL.append(SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT);
					strSQL.append(" ) ");
					strSQL.append(" and to_char(t2.dtSettlementDate,'YYYY-MM-DD') > '" + DataFormat.getDateString(tsContractInterestDate) + "' ");
					strSQL.append(" and to_char(t2.dtSettlementDate,'YYYY-MM-DD') <= '" + DataFormat.getDateString(info.getInterestDate()) + "' ");
					strSQL.append(" and t1.ContractID = " + lContractID);
					strSQL.append(" order by t2.dtSettlementDate ");
					
		            log4j.debug(strSQL.toString());
		            prepareStatement(strSQL.toString());
					ResultSet rs = executeQuery();
					while (rs != null && rs.next())
					{
						//资产回购业务回购通知单
					    if (rs.getLong("TransactionTypeID") == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY)
						{
					        log4j.info("资产回购业务回购通知单");
					        if (rs.getTimestamp("dtSettlementDate") != null && tsContractInterestDate != null && dContractRate > 0)
						    {
						        //分段计息
					            lDays = DataFormat.getTime(
									    new Date(tsContractInterestDate.getYear(),
									             tsContractInterestDate.getMonth(),
									             tsContractInterestDate.getDate()),
							            new Date(rs.getTimestamp("dtSettlementDate").getYear(),
									             rs.getTimestamp("dtSettlementDate").getMonth(),
									             rs.getTimestamp("dtSettlementDate").getDate())
									    );
					            log4j.info("dContractBalance = " + dContractBalance);
					            log4j.info("dContractRate = " + dContractRate);
					            log4j.info("Day1 = " + DataFormat.getDateString(tsContractInterestDate));
					            log4j.info("Day2 = " + DataFormat.getDateString(rs.getTimestamp("dtSettlementDate")));
					            log4j.info("lDays = " + lDays);
					            log4j.info("dContractInterest = " + dContractInterest);
					            dContractInterest = dContractInterest + 
							    dContractBalance * ( dContractRate / 100 / 360 ) * lDays;					            
					            log4j.info("dContractInterest = " + dContractInterest);
							    dContractBalance = dContractBalance + rs.getDouble("mAmount");
							    dContractRate = rs.getDouble("Rate");
							    tsContractInterestDate = rs.getTimestamp("dtSettlementDate");
						    }					        
						}
					  	//资产回购业务购回通知单
					    else if (rs.getLong("TransactionTypeID") == SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY)
						{
					        log4j.info("资产回购业务购回通知单");
					        if (rs.getTimestamp("dtSettlementDate") != null && tsContractInterestDate != null && dContractRate > 0)
						    {
						        //分段计息
					            lDays = DataFormat.getTime(
					                    new Date(tsContractInterestDate.getYear(),
									             tsContractInterestDate.getMonth(),
									             tsContractInterestDate.getDate()),
							            new Date(rs.getTimestamp("dtSettlementDate").getYear(),
									             rs.getTimestamp("dtSettlementDate").getMonth(),
									             rs.getTimestamp("dtSettlementDate").getDate())
									    );
					            log4j.info("dContractBalance = " + dContractBalance);
					            log4j.info("dContractRate = " + dContractRate);
					            log4j.info("Day1 = " + DataFormat.getDateString(tsContractInterestDate));
					            log4j.info("Day2 = " + DataFormat.getDateString(rs.getTimestamp("dtSettlementDate")));
					            log4j.info("lDays = " + lDays);
					            log4j.info("dContractInterest = " + dContractInterest);
							    dContractInterest = dContractInterest + 
							    dContractBalance * ( dContractRate / 100 / 360 ) * lDays;
							    log4j.info("dContractInterest = " + dContractInterest);
							    dContractBalance = dContractBalance - rs.getDouble("mAmount");
							    dContractRate = rs.getDouble("Rate");
							    tsContractInterestDate = rs.getTimestamp("dtSettlementDate");
						    }
						}
					    //利息支付通知单
					    else if (rs.getLong("TransactionTypeID") == SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PAYMENT)
					    {
					        log4j.info("利息支付通知单");
					        if (rs.getTimestamp("dtSettlementDate") != null && tsContractInterestDate != null && dContractRate > 0)
						    {
						        lDays = DataFormat.getTime(
						                new Date(tsContractInterestDate.getYear(),
									             tsContractInterestDate.getMonth(),
									             tsContractInterestDate.getDate()),
							            new Date(rs.getTimestamp("dtSettlementDate").getYear(),
									             rs.getTimestamp("dtSettlementDate").getMonth(),
									             rs.getTimestamp("dtSettlementDate").getDate())
									    );
						        log4j.info("dContractBalance = " + dContractBalance);
					            log4j.info("dContractRate = " + dContractRate);
					            log4j.info("Day1 = " + DataFormat.getDateString(tsContractInterestDate));
					            log4j.info("Day2 = " + DataFormat.getDateString(rs.getTimestamp("dtSettlementDate")));
					            log4j.info("lDays = " + lDays);
					            log4j.info("dContractInterest = " + dContractInterest);
						        dContractInterest = dContractInterest + 
							    dContractBalance * ( dContractRate / 100 / 360 ) * lDays - rs.getDouble("mAmount");
						        log4j.info("dContractInterest = " + dContractInterest);
						        //合同余额和利率不发生改变
						        tsContractInterestDate = rs.getTimestamp("dtSettlementDate");
						    }
					    }
					}
					//最后一段计息
					log4j.info("最后一段计息");
				    if (tsContractInterestDate != null && info.getInterestDate() != null && dContractRate > 0)
				    {
				        //分段计息
				        lDays = DataFormat.getTime(
				                new Date(tsContractInterestDate.getYear(),
							             tsContractInterestDate.getMonth(),
							             tsContractInterestDate.getDate()),
							    new Date(info.getInterestDate().getYear(),
							             info.getInterestDate().getMonth(),
							             info.getInterestDate().getDate())
							    );
				        log4j.info("dContractBalance = " + dContractBalance);
			            log4j.info("dContractRate = " + dContractRate);
			            log4j.info("Day1 = " + DataFormat.getDateString(tsContractInterestDate));
			            log4j.info("Day2 = " + DataFormat.getDateString(info.getInterestDate()));
			            log4j.info("Day1 = " + new Date(tsContractInterestDate.getYear(),
					             tsContractInterestDate.getMonth(),
					             tsContractInterestDate.getDate()));
			            log4j.info("Day2 = " + new Date(info.getInterestDate().getYear(),
					             info.getInterestDate().getMonth(),
					             info.getInterestDate().getDate()));
			            log4j.info("lDays = " + lDays);
			            log4j.info("dContractInterest = " + dContractInterest);
				        dContractInterest = dContractInterest + 
					    dContractBalance * ( dContractRate / 100 / 360 ) * lDays;
				        log4j.info("dContractInterest = " + dContractInterest);
					    tsContractInterestDate = info.getInterestDate();
				    }
		        
			        //更新合同计提利息和利息计提日期
					log4j.info("更新合同计提利息和利息计提日期");
					log4j.info("合同计提利息：" + dContractInterest);
					log4j.info("利息计提日期：" + info.getInterestDate());
			        
					cNewInfo.setId(lContractID);
					cNewInfo.setContractInterest(dContractInterest);
					cNewInfo.setContractInterestDate(info.getInterestDate());
					dao.update(cNewInfo);
					//this.transConn
					
					//生成季度结息会计分录
					log4j.info("生成季度结息会计分录");
					generateGLEntryParam = new GenerateGLEntryParam();
					generateGLEntryParam.setOfficeID(info.getOfficeID());
					generateGLEntryParam.setCurrencyID(info.getCurrencyID());
					generateGLEntryParam.setInputUserID(info.getInputUserID());
					generateGLEntryParam.setCheckUserID(info.getInputUserID());
					generateGLEntryParam.setTransactionType(SECConstant.BusinessType.CAPITAL_REPURCHASE.INTEREST_PLAN);
					generateGLEntryParam.setAccountType(SECConstant.EntryAccountType.AccountType_11);
					generateGLEntryParam.setDeliverOrderCode(cInfo.getCode());
					generateGLEntryParam.setSuspenseInterest(dContractInterest);
					generateGLEntryParam.setExecuteDate(info.getInputDate());
					generateGLEntryParam.setDeliveryDate(tsContractInterestDate);
					generateGLEntryParam.setTransactionDate(tsContractInterestDate);
					
					securitiesGeneralLedgerOperation.generateGLEntry(generateGLEntryParam);
					/*
					//借
					eInfo = new GLEntryInfo();
					eInfo.setCurrencyID(info.getCurrencyID());
					eInfo.setOfficeID(info.getOfficeID());
					eInfo.setInputUserID(info.getInputUserID());
					eInfo.setCheckUserID(info.getInputUserID());
					eInfo.setTransactionTypeID(SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE);
					eInfo.setDeliveryOrderCode(cInfo.getCode());
					eInfo.setAmount(dContractInterest);
					eInfo.setExecuteDate(info.getInputDate());
					eInfo.setInterestStartDate(tsContractInterestDate);
					eInfo.setTransDirection(SECConstant.DebitOrCredit.DEBIT);
					eInfo.setSubjectCode("001.000.5260040000.000.000000.0000.0000");
					eInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
					eDao.add(eInfo);
					//贷
					eInfo = new GLEntryInfo();
					eInfo.setCurrencyID(info.getCurrencyID());
					eInfo.setOfficeID(info.getOfficeID());
					eInfo.setInputUserID(info.getInputUserID());
					eInfo.setCheckUserID(info.getInputUserID());
					eInfo.setTransactionTypeID(SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE);
					eInfo.setDeliveryOrderCode(cInfo.getCode());
					eInfo.setAmount(dContractInterest);
					eInfo.setExecuteDate(info.getInputDate());
					eInfo.setInterestStartDate(tsContractInterestDate);
					eInfo.setTransDirection(SECConstant.DebitOrCredit.CREDIT);
					eInfo.setSubjectCode("001.000.2010030000.000.000000.0000.0000");
					eInfo.setStatusID(SECConstant.TransactionStatus.CHECK);
					eDao.add(eInfo);
					*/
		        }
		    }
		    lResult = 1;
		}
		catch(Exception e)
		{
			throw new SecuritiesDAOException("合同利息计提产生错误",e);
		}

		/*----------------finalize Dao-----------------*/
		try 
		{
			finalizeDAO();
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new SecuritiesDAOException(e);
		}
		/*----------------end of finalize---------------*/
		
		return lResult;		
	}
	
	public static void main(String[] args)
	{
		try
		{
		    Log.print(DataFormat.getTime(new Date(104,6,15),new Date(104,8,21)));
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
	}
}
