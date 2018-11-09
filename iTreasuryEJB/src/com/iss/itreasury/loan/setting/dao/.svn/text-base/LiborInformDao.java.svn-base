/*
 * Created on 2004-11-25
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.setting.dao;

import java.util.*;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.base.*;
import com.iss.itreasury.loan.setting.dataentity.*;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LiborInformDao extends LoanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public LiborInformDao()
	{
		super("Loan_LiborInform");
	}
	
	
	/**
	 * 
	 * 在新增/修改Libor利率通知之前，
	 * 检验录入的起息日期和结息日期。
	 * @Create Date: 2005-03-30
	 * @author       Jason Fang
	 * @param        LiborInformInfo 查询条件
	 * @return       long -1，不允许保存；1，允许保存
	 * @exception    LoanDAOException
	 */
	public long checkDate( LiborInformInfo info ) throws LoanDAOException
	{
		StringBuffer sb  = new StringBuffer();
		ResultSet    rs  = null;
		long returnValue = 1;//有效
		
		try
		{
			initDAO();
		}
		catch( ITreasuryDAOException e )
		{
			throw new LoanDAOException( e );
		}
		
		try
		{
			sb.append( " select * from loan_liborinform  where  contractID = " + info.getContractID()+" \n");
			sb.append( " 	and payNoticeID = " + info.getPayNoticeID() +" \n");
			sb.append( " 	and currencyID = " + info.getCurrencyID() +" \n");
			sb.append( " 	and officeID = " + info.getOfficeID() +" \n");
			sb.append( " 	and statusID = " + Constant.RecordStatus.VALID +" \n");
			
			if( info.getId() > 0 )
			{
				//如果是修改保存
				sb.append( " 	and ID <> " + info.getId() +" \n");
			}
			
			log4j.debug( sb.toString() );
			
			prepareStatement( sb.toString() );
			
			rs = executeQuery();
			
			
			//合同下的通知已经做过Libor利率通知，对起息日期和结息日期进行检验
			while(  rs != null && rs.next() )
			{
				System.out.println("Log-11:   ---------- 进入循环！！！");
				if(   ( 
						//检查起息日期  
						(  info.getInterestStart().after(rs.getTimestamp("interestStart"))|| info.getInterestStart().equals(rs.getTimestamp("interestStart")) )     &&     (  info.getInterestStart().before(rs.getTimestamp("interestEnd"))|| info.getInterestStart().equals(rs.getTimestamp("interestEnd")) )   
					  )
				   || (
				   		//检查结息日期  
				   		(  info.getInterestEnd().after(rs.getTimestamp("interestStart"))|| info.getInterestEnd().equals(rs.getTimestamp("interestStart")) )     &&     (  info.getInterestEnd().before(rs.getTimestamp("interestEnd"))|| info.getInterestEnd().equals(rs.getTimestamp("interestEnd")) )   
					  )
				   || (
				        ///检查起息日期 & 结息日期 
				           (  info.getInterestStart().before(rs.getTimestamp("interestStart")) || info.getInterestStart().equals(rs.getTimestamp("interestStart")) )
				        && (  info.getInterestEnd().after(rs.getTimestamp("interestEnd")) || info.getInterestEnd().equals(rs.getTimestamp("interestEnd")) )
				      )
				  )
				{
					//无效
					returnValue = -1;
					System.out.println("Log-2:   ---------- 无效, returnValue = "+returnValue);
					break;
				}
				else
				{
					//有效
					returnValue = 1;
					System.out.println("Log-3:   ---------- 有效, returnValue = "+returnValue);
				}
				
			}
			
			sb.setLength(0);
			
		}
		catch (ITreasuryDAOException e) 
        {
            throw new LoanDAOException("检验录入的起息日期和结息日期产生错误!",e);
        } 
        catch (SQLException e) 
        {
            throw new LoanDAOException("检验录入的起息日期和结息日期产生错误!",e);
        }
            
        finally 
        {   if(rs!=null)
           {
            try {
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            rs=null;
           }
            try {
                finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } 
       
		
		return returnValue;
	}
		
	
	/*
	 * 
	 */
	public Collection findByMultiOption(LiborInformInfo qInfo) throws LoanDAOException
	{
		String strSelect = "";
		String strSQL = "";
		Vector v = new Vector();

		long liborInformID = qInfo.getId();
		long contractID = qInfo.getContractID();
		long payNoticeID = qInfo.getPayNoticeID();
		long queryPurpose = qInfo.getQueryPurpose();
		long currencyID = qInfo.getCurrencyID();
		long officeID = qInfo.getOfficeID();
		long userID = qInfo.getInputUserID();
		
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long recordCount = -1;
		long pageCount = -1;
		long rowNumStart = -1;
		long rowNumEnd = -1;
		
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            //计算记录总数				
            if (queryPurpose == 1) //for modify
            {
            	strSQL = "";
            	strSelect = " select count(*) ";
            	strSQL = " from Loan_LiborInform a "
            			+ " where 1 = 1 "
            			+ " and a.StatusID = "
            			+ LOANConstant.RecordStatus.VALID;
            			//+ " and a.InputUserID = "
            			//+ userID;
            }

            //////////////////////查询条件////////////////////////////////////////////////////
            if (officeID > 0)
            {
            	strSQL += " and a.OfficeID = " + officeID;
            }
            if (currencyID > 0)
            {
            	strSQL += " and a.CurrencyID = " + currencyID;
            }
            if (contractID > 0)
            {
                strSQL += " and a.ContractID = " + contractID;
            }
            if (payNoticeID > 0)
            {
                strSQL += " and a.PayNoticeID = " + payNoticeID;
            }
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
            	if (orderParamString.substring(0, nIndex).equalsIgnoreCase("Loan_LiborInform"))
            	{
            		strSQL += " order by a." + orderParamString.substring(nIndex + 1);
            	}
            }
            else
            {
            	strSQL += " order by a.ID";
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
            	strSQL += " desc";
            }
            log4j.debug(strSelect + strSQL);
            try
            {
            	prepareStatement(strSelect + strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		recordCount = rs.getLong(1);
            	}
            	if(rs!=null)
            	{
            	    rs.close();
            	    rs=null;
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询Libor利率通知笔数产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询Libor利率通知笔数产生错误", e);
            }
            pageCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                pageCount++;
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
            rowNumStart = (pageNo - 1) * pageLineCount + 1;
            rowNumEnd = rowNumStart + pageLineCount - 1;
            strSelect = " select a.* ";
            strSQL = " select * from ( select aa.*,rownum r from ( " + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and " + rowNumEnd;
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs1 = executeQuery();
            	while (rs1 != null && rs1.next())
            	{
            	    LiborInformInfo liborInformInfo = new LiborInformInfo();
            	    liborInformInfo.setId(rs1.getLong("ID")); //id
            	    liborInformInfo.setOfficeID(rs1.getLong("OfficeID")); //办事处
            	    liborInformInfo.setCurrencyID(rs1.getLong("CurrencyID")); //币种
            	    liborInformInfo.setContractID(rs1.getLong("ContractID")); //合同ID
            	    liborInformInfo.setPayNoticeID(rs1.getLong("PAYNOTICEID")); //放款单ID
            	    liborInformInfo.setInterestStart(rs1.getTimestamp("InterestStart")); //起息日
            	    liborInformInfo.setInterestEnd(rs1.getTimestamp("InterestEnd")); //结息日
            	    liborInformInfo.setLiborRate(rs1.getDouble("LiborRate"));	//Libor利率值
            	    liborInformInfo.setAdjustRate(rs1.getDouble("AdjustRate"));	//利率调整比例
            	    liborInformInfo.setStaidAdjustRate(rs1.getDouble("StaidAdjustRate"));	//固定浮动点
            	    liborInformInfo.setIsCountInterest(rs1.getLong("IsCountInterest")); //是否结过息
            	    liborInformInfo.setInputUserID(rs1.getLong("InputUserID")); //录入人
            	    liborInformInfo.setInputDate(rs1.getTimestamp("InputDate")); //录入时间
            	    liborInformInfo.setStatusID(rs1.getLong("StatusID")); //状态
            		//表中没有的字段
            	    liborInformInfo.setRecordCount(recordCount); //记录数
            	    liborInformInfo.setPageCount(pageCount); //页数
            		v.add(liborInformInfo);
            	} 
            	if(rs1!=null)
            	{
            	    rs1.close();
            	    rs1=null;
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询Libor利率通知产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询Libor利率通知产生错误", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		return (v.size() > 0 ? v : null);
	}
	
	//返回放款通知单下最晚的结息日
	public Timestamp getLastInterestEnd(long lPayNoticeID) throws LoanDAOException
	{
		String strSQL = "";
		Timestamp tsLastInterestEnd = null;
		
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            strSQL = " select max(nvl(InterestEnd,sysdate)) from " + strTableName + " where PayNoticeID = " + lPayNoticeID + " and StatusID = " + LOANConstant.RecordStatus.VALID;
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            	    tsLastInterestEnd = rs.getTimestamp(1);
            	}
            	if(rs!=null)
            	{
            	    rs.close();
            	    rs=null;
            	    
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("返回放款通知单下最晚的结息日产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("返回放款通知单下最晚的结息日产生错误", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		log4j.debug(":::::::::: ::::tsLastInterestEnd::::::" + tsLastInterestEnd);
		return tsLastInterestEnd;
	}
	
	public static void main(String[] args)
	{
		//
	}
}
