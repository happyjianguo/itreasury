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
import java.sql.PreparedStatement;
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
public class LoanTypeSettingDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public LoanTypeSettingDao()
    {
        super("Loan_LoanTypeSetting");
    }

    /*
     *  
     */
    public Collection findByMultiOption(LoanTypeSettingInfo qInfo)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long loanSubTypeID = qInfo.getId();
        long loanTypeID = qInfo.getLoanTypeID();
        long queryPurpose = qInfo.getQueryPurpose();
        String subName=qInfo.getName();
        String subCode=qInfo.getCode();
        long pageLineCount = qInfo.getPageLineCount();
        long pageNo = qInfo.getPageNo();
        long orderParam = qInfo.getOrderParam();
        long desc = qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();
        long recordCount = -1;
        long pageCount = -1;
        long rowNumStart = -1;
        long rowNumEnd = -1;
        
        long lOfficeID=qInfo.getOfficeID();
        long lCurrencyID=qInfo.getCurrencyID();

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
                strSQL = " from Loan_LoanTypeSetting a " + " where 1 = 1 "
                        + " and a.StatusID = "
                        + Constant.RecordStatus.VALID;
                //+ " and a.InputUserID = " + userID;
            }

            //////////////////////查询条件////////////////////////////////////////////////////
            if (loanSubTypeID > 0)
            {
                strSQL += " and a.ID = " + loanSubTypeID;
            }
            if (loanTypeID > 0)
            {
                strSQL += " and a.LoanTypeID = " + loanTypeID;
            }
            if (!subName.equals(""))
            {
            	//strSQL+=" and a.Name = '"+subName+"'";
            	
            	//改成模糊查询 mzh_fu 2007-03-09
                strSQL+=" and a.Name like '%"+subName+"%'";
            }
            if (!subCode.equals(""))
            {
                strSQL+=" and a.Code = '"+subCode+"'";
            }
            if (lOfficeID>0)
            {
                strSQL+=" and a.OfficeID = '"+lOfficeID+"'";
            }
            if (lCurrencyID>0)
            {
                strSQL+=" and a.CurrencyID = '"+lCurrencyID+"'";
            }
            
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
                if (orderParamString.substring(0, nIndex).equalsIgnoreCase("Loan_LoanTypeSetting"))
                {
                    strSQL += " order by a."
                            + orderParamString.substring(nIndex + 1);
                }
            } else
            {
                strSQL += " order by a.ID";
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                strSQL += " desc";
            }
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
				{
					rs.close();
					rs = null;
				}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型分类设置笔数产生错误", e);
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
            strSQL = " select * from ( select aa.*,rownum r from ( "
                    + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and "
                    + rowNumEnd;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    info.setPageCount(pageCount); //页数
                    v.add(info);
                }
                if(rs1 != null)
				{
					rs1.close();
					rs1 = null;
				}
            } catch (ITreasuryDAOException e)
            {   
                throw new LoanDAOException("批量查询贷款类型分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
    
    /*
     *  
     */
    public Collection findByLoanTypeID(long lLoanTypeID,long lOfficeID,long lCurrencyID)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL += " from Loan_LoanTypeSetting a where 1 = 1 ";
            strSQL += " and a.StatusID = " + Constant.RecordStatus.VALID;
            strSQL += " and a.LoanTypeID = " + lLoanTypeID;
            strSQL += " and a.OFFICEID = " + lOfficeID;
            strSQL += " and a.CURRENCYID = " + lCurrencyID;
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
				{
					rs.close();
					rs = null;
				}
            } catch (ITreasuryDAOException e)
            {   
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /*
           finally{
               try {
                finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
           }
           */
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
				{
					rs1.close();
					rs1 = null;
				}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {  
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
    
	public Collection findByLoanTypeID(long lLoanTypeID)
	    throws LoanDAOException
	{
	String strSelect = "";
	String strSQL = "";
	Vector v = new Vector();
	
	long recordCount = -1;
	
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
	    
	    strSQL = "";
	    strSelect = " select count(*) ";
	    strSQL = " from Loan_LoanTypeSetting a " 
	        	+ " where 1 = 1 "
	            + " and a.StatusID = "
	            + Constant.RecordStatus.VALID;
	    if (lLoanTypeID >0)
	        strSQL += " and a.LoanTypeID = " + lLoanTypeID;
	    		
	
	    //////////////////////查询条件////////////////////////////////////////////////////
	                
	    ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
	    
	    log4j.info(strSelect + strSQL);
	
	    try
	    {
	        prepareStatement(strSelect + strSQL);
	        ResultSet rs = executeQuery();
	        if (rs != null && rs.next())
	        {
	            recordCount = rs.getLong(1);
	        }
	        if(rs != null)
			{
				rs.close();
				rs = null;
			}
	    } catch (ITreasuryDAOException e)
	    {   
	        throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	    } catch (SQLException e)
	    {
	        throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	    }
	    
	    /*
	   finally{
	       try {
	        finalizeDAO();
	    } catch (ITreasuryDAOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	   }
	   */
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //返回需求的结果集
	
	    strSelect = " select a.* ";
	    strSQL = strSelect + strSQL;
	    log4j.debug(strSQL);
	    try
	    {
	        prepareStatement(strSQL);
	        ResultSet rs1 = executeQuery();
	        while (rs1 != null && rs1.next())
	        {
	            LoanTypeSettingInfo info = new LoanTypeSettingInfo();
	            info.setId(rs1.getLong("ID")); //id
	            info.setCode(rs1.getString("Code")); //编号
	            info.setName(rs1.getString("Name")); //名称                    
	            info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
	            info.setStatusID(rs1.getLong("StatusID")); //状态
	            //表中没有的字段
	            info.setRecordCount(recordCount); //记录数
	            v.add(info);
	        }
	        if(rs1 != null)
			{
				rs1.close();
				rs1 = null;
			}
	    } catch (ITreasuryDAOException e)
	    {
	        throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	    } catch (SQLException e)
	    {
	        throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	    }
	    try
	    {  
	        finalizeDAO();
	    } catch (ITreasuryDAOException e)
	    {
	        throw new LoanDAOException(e);
	    }
	} catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally
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
    /**
     * 根据多个贷款大类型查询小类型
     * @param lLoanTypeID
     * @return
     * @throws LoanDAOException
     */
    public Collection findByLoanTypeID(long[] lLoanTypeID)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL = " from Loan_LoanTypeSetting a " 
                	+ " where 1 = 1 "
                    + " and a.StatusID = "
                    + Constant.RecordStatus.VALID;
            if (lLoanTypeID != null)

                strSQL += " and a.LoanTypeID in (" + DataFormat.getStringWithTagByLongArray(lLoanTypeID) +")";
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
    
    
    /**
     * 根据多个贷款大类型查询小类型
     * @param lLoanTypeID
     * @return
     * @throws LoanDAOException
     */
    public Collection findSubLoanCodeByLoanTypeID(long[] lLoanTypeID,long officeid,long currencyid) throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL = " from Loan_LoanTypeSetting a " 
                	+ " where 1 = 1 "
                    + " and a.StatusID = "
                    + Constant.RecordStatus.VALID 
            		+ " and a.currencyid = " +  currencyid
            		+ " and a.officeid = " + officeid ;
            if (lLoanTypeID != null)

                strSQL += " and a.LoanTypeID in (" + DataFormat.getStringWithTagByLongArray(lLoanTypeID) +")";
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
    

//	2007.6.12
    /**
     * 根据多个贷款大类型查询小类型,区分办事处，币种
     * @param lLoanTypeID
     * @return
     * @throws LoanDAOException
     */
    public Collection findByLoanTypeID(long[] lLoanTypeID,long lOfficeID,long lCurrencyID)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL = " from Loan_LoanTypeSetting a " 
                	+ " where 1 = 1 "
                    + " and a.StatusID = "
                    + Constant.RecordStatus.VALID;
            if (lLoanTypeID != null)

                strSQL += " and a.LoanTypeID in (" + DataFormat.getStringWithTagByLongArray(lLoanTypeID) +") and a.OFFICEID = "+lOfficeID+" and a.CURRENCYID = "+lCurrencyID;
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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


    //校验编号是否重复
    public long checkCode(LoanTypeSettingInfo info) throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;

        try
        {
            try
            {
                initDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            strSQL = " select id from " + strTableName
                    + " where Code = '" + info.getCode() + "' and StatusID = "
                    + LOANConstant.RecordStatus.VALID
            		+ " and CurrencyID = "+ info.getCurrencyID() +" and OfficeID = " + info.getOfficeID()
            		//modify by xwhe 同一个业务类型不能有重复的编号
            		+ " and loantypeid = " + info.getLoanTypeID();
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    if (rs.getLong(1) == info.getId())
                    {
                        lResult = 1;
                    }
                    else
                    {
                        lResult = -1;
                    }
                }
                else
                {
                    lResult = 1;
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("检查编号产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("检查编号产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
    }

    //校验名称是否重复
    public long checkName(LoanTypeSettingInfo info)
            throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;

        try
        {
            try
            {
                initDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            
            strSQL = " select id from " + strTableName
                    + " where Name = '" + info.getName()
                    + "' and StatusID = "
                    + LOANConstant.RecordStatus.VALID
            		+ " and CurrencyID = "+ info.getCurrencyID() +" and OfficeID = " + info.getOfficeID()
            		//modify by xwhe 同一个业务类型不能有重复的编号
            		+ " and loantypeid = " + info.getLoanTypeID();
            log4j.info(strSQL);
            
           
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                if (rs1 != null && rs1.next())
                {
                    if (rs1.getLong(1) == info.getId())
                    {
                        lResult = 1;
                    }
                    else 
                    {
                        lResult = -1;
                    }
                }
                else
                {
                    lResult = 1;
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("检查名称产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("检查名称产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
        return lResult;
    }

    public static void main(String[] args)
    {
        //
      /* LoanTypeSettingDao dao = new LoanTypeSettingDao();
        String strCode = "";
        LoanTypeSettingInfo info = new LoanTypeSettingInfo();
        try 
        {
            info.setId(2);
            info.setName("ibmsun1");
            System.out.println("dao.checkName()="+dao.checkName(info));
        } catch (LoanDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //System.out.println("################strCode = "+strCode);
    }

	/**
	 * @param loanTypeID
	 * @return
	 */
	public Collection findByLoanTypeIDTrust(long[] lLoanTypeID) throws LoanDAOException {
		String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL = " from Loan_LoanTypeSetting a " 
                	+ " where 1 = 1 "
                    + " and a.StatusID = "
                    + Constant.RecordStatus.VALID;
            if (lLoanTypeID != null)
            	 strSQL += "and a.id in (select b.subloantypeid from loan_loantyperelation b where b.loantypeid in (" + DataFormat.getStringWithTagByLongArray(lLoanTypeID) +"))";
               
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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
	//2007.6.11 qhzhou
	public Collection findByLoanTypeIDTrust(long[] lLoanTypeID,long lOfficeID,long lCurrencyID) throws LoanDAOException {
		String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

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
            
            strSQL = "";
            strSelect = " select count(*) ";
            strSQL = " from Loan_LoanTypeSetting a " 
                	+ " where 1 = 1 "
                    + " and a.StatusID = "
                    + Constant.RecordStatus.VALID;
            if (lLoanTypeID != null)
            	 strSQL += "and a.id in (select b.subloantypeid from loan_loantyperelation b where b.loantypeid in (" + DataFormat.getStringWithTagByLongArray(lLoanTypeID) +") and b.OFFICEID="+lOfficeID+" and b.CURRENCYID="+lCurrencyID+")";
               
            		

            //////////////////////查询条件////////////////////////////////////////////////////
                        
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            
            log4j.info(strSelect + strSQL);
        
            try
            {
                prepareStatement(strSelect + strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    recordCount = rs.getLong(1);
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集

            strSelect = " select a.* ";
            strSQL = strSelect + strSQL;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
                    info.setId(rs1.getLong("ID")); //id
                    info.setCode(rs1.getString("Code")); //编号
                    info.setName(rs1.getString("Name")); //名称                    
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
                    info.setStatusID(rs1.getLong("StatusID")); //状态
                    //表中没有的字段
                    info.setRecordCount(recordCount); //记录数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
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


	/**
	 * @param loanTypeID
	 * @return
	 */
	public Collection findByLoanTypeID1(long lLoanTypeID) throws LoanDAOException{
		 String strSelect = "";
	        String strSQL = "";
	        Vector v = new Vector();

	        long recordCount = -1;

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
	            
	            strSQL = "";
	            strSelect = " select count(*) ";
	            strSQL = " from Loan_LoanTypeSetting a " 
	                	+ " where 1 = 1 "
	                    + " and a.StatusID = "
	                    + Constant.RecordStatus.VALID;
	            if (lLoanTypeID >0)
	                strSQL += " and a.id in (select b.subloantypeid from loan_loantyperelation b where b.loantypeid = " + lLoanTypeID + ")";
	            		

	            //////////////////////查询条件////////////////////////////////////////////////////
	                        
	            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
	            
	            log4j.info(strSelect + strSQL);
	        
	            try
	            {
	                prepareStatement(strSelect + strSQL);
	                ResultSet rs = executeQuery();
	                if (rs != null && rs.next())
	                {
	                    recordCount = rs.getLong(1);
	                }
	                if(rs != null)
					{
						rs.close();
						rs = null;
					}
	            } catch (ITreasuryDAOException e)
	            {   
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	            } catch (SQLException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	            }
	            
	            /*
	           finally{
	               try {
	                finalizeDAO();
	            } catch (ITreasuryDAOException e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	           }
	           */
	            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	            //返回需求的结果集

	            strSelect = " select a.* ";
	            strSQL = strSelect + strSQL;
	            log4j.debug(strSQL);
	            try
	            {
	                prepareStatement(strSQL);
	                ResultSet rs1 = executeQuery();
	                while (rs1 != null && rs1.next())
	                {
	                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
	                    info.setId(rs1.getLong("ID")); //id
	                    info.setCode(rs1.getString("Code")); //编号
	                    info.setName(rs1.getString("Name")); //名称                    
	                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
	                    info.setStatusID(rs1.getLong("StatusID")); //状态
	                    //表中没有的字段
	                    info.setRecordCount(recordCount); //记录数
	                    v.add(info);
	                }
	                if(rs1 != null)
					{
						rs1.close();
						rs1 = null;
					}
	            } catch (ITreasuryDAOException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	            } catch (SQLException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	            }
	            try
	            {  
	                finalizeDAO();
	            } catch (ITreasuryDAOException e)
	            {
	                throw new LoanDAOException(e);
	            }
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } finally
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
	//2007.6.11 qhzhou
	public Collection findByLoanTypeID1(long lLoanTypeID,long lOfficeID,long lCurrencyID) throws LoanDAOException{
		 String strSelect = "";
	        String strSQL = "";
	        Vector v = new Vector();

	        long recordCount = -1;

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
	            
	            strSQL = "";
	            strSelect = " select count(*) ";
	            strSQL = " from Loan_LoanTypeSetting a " 
	                	+ " where 1 = 1 "
	                    + " and a.StatusID = "
	                    + Constant.RecordStatus.VALID;
	            if (lLoanTypeID >0)
	                strSQL += " and a.id in (select b.subloantypeid from loan_loantyperelation b where b.loantypeid = " + lLoanTypeID + " and OFFICEID="+lOfficeID+" and CURRENCYID="+lCurrencyID+")";
	            		

	            //////////////////////查询条件////////////////////////////////////////////////////
	                        
	            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
	            
	            log4j.info(strSelect + strSQL);
	        
	            try
	            {
	                prepareStatement(strSelect + strSQL);
	                ResultSet rs = executeQuery();
	                if (rs != null && rs.next())
	                {
	                    recordCount = rs.getLong(1);
	                }
	                if(rs != null)
					{
						rs.close();
						rs = null;
					}
	            } catch (ITreasuryDAOException e)
	            {   
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	            } catch (SQLException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置笔数产生错误", e);
	            }
	            
	            /*
	           finally{
	               try {
	                finalizeDAO();
	            } catch (ITreasuryDAOException e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	           }
	           */
	            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	            //返回需求的结果集

	            strSelect = " select a.* ";
	            strSQL = strSelect + strSQL;
	            log4j.debug(strSQL);
	            try
	            {
	                prepareStatement(strSQL);
	                ResultSet rs1 = executeQuery();
	                while (rs1 != null && rs1.next())
	                {
	                    LoanTypeSettingInfo info = new LoanTypeSettingInfo();
	                    info.setId(rs1.getLong("ID")); //id
	                    info.setCode(rs1.getString("Code")); //编号
	                    info.setName(rs1.getString("Name")); //名称                    
	                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类ID
	                    info.setStatusID(rs1.getLong("StatusID")); //状态
	                    //表中没有的字段
	                    info.setRecordCount(recordCount); //记录数
	                    v.add(info);
	                }
	                if(rs1 != null)
					{
						rs1.close();
						rs1 = null;
					}
	            } catch (ITreasuryDAOException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	            } catch (SQLException e)
	            {
	                throw new LoanDAOException("批量查询贷款类型大类下的分类设置产生错误", e);
	            }
	            try
	            {  
	                finalizeDAO();
	            } catch (ITreasuryDAOException e)
	            {
	                throw new LoanDAOException(e);
	            }
	        } catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } finally
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

	//效验能不能删除
    public String validateSubloanTypeId(LoanTypeSettingInfo info) throws LoanDAOException
    {
        String strSQL = "";
        String lResult = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
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
            
            strSQL = " select t.loanSubTypeName from v_loan_transaction t where t.loanTypeId = ? and t.loanSubTypeId = ? and t.Officeid = ? and t.Currencyid = ? and t.Statusid != ? ";
            
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getLoanTypeID());
			ps.setLong(2, info.getSubLoanTypeID());
			ps.setLong(3, info.getOfficeID());
			ps.setLong(4, info.getCurrencyID());
			ps.setLong(5, info.getStatusID());
			
            try
            {
                rs = executeQuery();
                
                if (rs != null && rs.next())
                {
                	lResult = rs.getString("loanSubTypeName");
                }

                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("删除产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("删除产生错误", e);
            }
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        
        return lResult;
    }
    
}