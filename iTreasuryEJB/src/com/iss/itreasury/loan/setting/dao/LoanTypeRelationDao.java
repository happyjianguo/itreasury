/*
 * Created on 2005-5-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeRelationInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeRelationDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public LoanTypeRelationDao()
    {
        super("Loan_LoanTypeRelation");
    }
    
	/**
	 * 保存信息
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long save(LoanTypeRelationInfo info) throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {
            try
            {
                initDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            strSQL = " insert into Loan_LoanTypeRelation(OfficeID,CurrencyID,LoanTypeID,SubLoanTypeID) values(?,?,?,?) ";
            log4j.debug(strSQL);
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			ps.setLong(3, info.getLoanTypeID());
			ps.setLong(4, info.getSubLoanTypeID());
			lResult = ps.executeUpdate();            
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
	
	/**
	 * 删除信息
	 * 操作数据库表
	 * @param       info      	信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long delete(LoanTypeRelationInfo info) throws LoanDAOException
    {
        String strSQL = "";
        long lResult = -1;
        PreparedStatement ps = null;
		ResultSet rs = null;

        try
        {
            try
            {
                initDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            strSQL = " delete Loan_LoanTypeRelation where OfficeID = ? and CurrencyID = ? ";//and LoanTypeID = ? and SubLoanTypeID = ? ";
            log4j.debug(strSQL);
            ps = prepareStatement(strSQL);
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			//ps.setLong(3, info.getLoanTypeID());
			//ps.setLong(4, info.getSubLoanTypeID());
			lResult = ps.executeUpdate();            
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
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return lResult;
	}

    /*
     *  查找办事处和币种下的所有贷款类型及子类
     */
    public Collection findByMultiOption(LoanTypeRelationInfo qInfo)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long officeID = qInfo.getOfficeID();
        long currencyID = qInfo.getCurrencyID();
        //long loanTypeID = qInfo.getId();
        //long loanSubTypeID = qInfo.getId();
        //long queryPurpose = qInfo.getQueryPurpose();
        
        //long pageLineCount = qInfo.getPageLineCount();
        //long pageNo = qInfo.getPageNo();
        //long orderParam = qInfo.getOrderParam();
        //long desc = qInfo.getDesc();
        //String orderParamString = qInfo.getOrderParamString();
        //long recordCount = -1;
        //long pageCount = -1;
        //long rowNumStart = -1;
        //long rowNumEnd = -1;

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
            
//            //计算记录总数
//            if (queryPurpose == 1) //for modify
//            {
//                strSQL = "";
//                strSelect = " select count(*) ";
                strSQL = " from Loan_LoanTypeRelation a " + " where 1 = 1 ";
//                //+ " and a.InputUserID = " + userID;
//            }
//
//            //////////////////////查询条件////////////////////////////////////////////////////
            if (officeID > 0)
            {
                strSQL += " and a.OfficeID = " + officeID;
            }
            if (currencyID > 0)
            {
                strSQL += " and a.CurrencyID = " + currencyID;
            }
//            if (loanTypeID > 0)
//            {
//                strSQL += " and a.LoanTypeID = " + loanTypeID;
//            }
//            if (loanSubTypeID > 0)
//            {
//                strSQL += " and a.SubLoanTypeID = " + loanSubTypeID;
//            }
//
//            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
//            int nIndex = 0;
//            nIndex = orderParamString.indexOf(".");
//            if (nIndex > 0)
//            {
//                if (orderParamString.substring(0, nIndex).equalsIgnoreCase("Loan_LoanTypeRelation"))
//                {
//                    strSQL += " order by a."
//                            + orderParamString.substring(nIndex + 1);
//                }
//            } else
//            {
//                strSQL += " order by a.LoanTypeID";
//            }
//            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
//            {
//                strSQL += " desc";
//            }
            
			strSQL += " order by a.LoanTypeID,a.SubLoanTypeID ";
//			
//            log4j.debug(strSelect + strSQL);
//            try
//            {
//                prepareStatement(strSelect + strSQL);
//                ResultSet rs = executeQuery();
//                if (rs != null && rs.next())
//                {
//                    recordCount = rs.getLong(1);
//                }
//            } catch (ITreasuryDAOException e)
//            {
//                throw new LoanDAOException("批量查询贷款类型关联设置笔数产生错误", e);
//            } catch (SQLException e)
//            {
//                throw new LoanDAOException("批量查询贷款类型关联设置笔数产生错误", e);
//            }
//            recordCount = recordCount / pageLineCount;
//            if ((recordCount % pageLineCount) != 0)
//            {
//                recordCount++;
//            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
//            rowNumStart = (pageNo - 1) * pageLineCount + 1;
//            rowNumEnd = rowNumStart + pageLineCount - 1;
//            strSelect = " select a.* ";
//            strSQL = " select * from ( select aa.*,rownum r from ( "
//                    + strSelect + strSQL;
//            strSQL += " ) aa ) where r between " + rowNumStart + " and "
//                    + rowNumEnd;

			strSelect = " select a.* ";
			strSQL =  strSelect + strSQL;
			
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    LoanTypeRelationInfo info = new LoanTypeRelationInfo();
                    info.setLoanTypeID(rs1.getLong("LoanTypeID")); //贷款类型大类
                    info.setSubLoanTypeID(rs1.getLong("SubLoanTypeID")); //贷款类型子类ID
                    info.setOfficeID(rs1.getLong("OfficeID")); //办事处
                    info.setCurrencyID(rs1.getLong("CurrencyID")); //币种
                    //表中没有的字段
//                    info.setRecordCount(recordCount); //记录数
//                    info.setPageCount(pageCount); //页数
                    v.add(info);
                }
                if(rs1 != null)
    			{
    				rs1.close();
    				rs1 = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
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

    //校验名称是否重复
    public long checkInsert(LoanTypeRelationInfo info) throws LoanDAOException
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
            strSQL = " select count(*) from " + strTableName
                    + " where 1 = 1 " 
                    + " and OfficeID = " + info.getOfficeID()
                    + " and CurrencyID = " + info.getCurrencyID()
                    + " and LoanTypeID = " + info.getLoanTypeID()
                    + " and SubLoanTypeID = " + info.getSubLoanTypeID();
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    if (rs.getLong(1) == 0)
                    {
                        lResult = 1;
                    }
                }
                if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("检查重复产生错误", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("检查重复产生错误", e);
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
    
    /*
     * 取贷款类型关系设置中选择的大类
     */
	public long[] getAllSetLoanTypeID(long officeID,long currencyID)
	{
		long[] allLoanType = null;
		
		int count = 0;
		
		String strSelect = "";
		String strSQL = "";


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
            

				strSQL = " from Loan_LoanTypeRelation a " + " where 1 = 1 ";

//			  //////////////////////查询条件////////////////////////////////////////////////////
			if (officeID > 0)
			{
				strSQL += " and a.OfficeID = " + officeID;
			}
			if (currencyID > 0)
			{
				strSQL += " and a.CurrencyID = " + currencyID;
			}
            //strSQL += " and a.LoanTypeID<>"+LOANConstant.LoanType.YT;
            
			strSQL += " order by a.LoanTypeID ";
			
			//log4j.debug(strSQL);
			try
			{
				strSelect = " select distinct(a.LoanTypeID) ";  
			
				strSQL =  strSelect + strSQL;
				//System.out.println("strSQL="+strSQL);
				transPS = transConn.prepareStatement(" select count(*) from ( "+strSQL + " ) ");
				ResultSet rs = executeQuery(); 
				while (rs != null && rs.next())
				{
					count = rs.getInt(1);
				}
				if(rs != null)
				{ 
					rs.close();
					rs = null;
				}
				/*----2005-08-04----*/
				/*--神龙骑士修改漏洞--*/				
				if (transPS!=null)
				{
					transPS.close();
					transPS=null;
				}
				/*--神龙骑士修改漏洞--*/
				long[] allTmp = new long[count];
				int n = 0;
				
				transPS=transConn.prepareStatement(strSQL);
				rs = executeQuery();
				while (rs != null && rs.next() && n < count)
				{
					allTmp[n] = rs.getLong(1);
					n++;
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				
				allLoanType = allTmp;
				
				
			} catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
			} catch (SQLException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
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
		return allLoanType;
    
	}
	/*
	 * 取贷款类型关系设置中选择的大类
	 */
	public long[] getAllSetSubLoanTypeID(long officeID,long currencyID,long[] loanTypeID)
	{
		long[] allLoanType = null;
		
		int count = 0;
		
		String strSelect = "";
		String strSQL = "";
		String strLoanType = "";
		
		if(loanTypeID != null)
		{
			strLoanType += loanTypeID[0];
			for(int nl=1;nl<loanTypeID.length;nl++)
			{
				strLoanType += ","+loanTypeID[nl];
			}
		}

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
            

				strSQL = " from Loan_LoanTypeRelation a,loan_loantypesetting b " 
							+ " where 1 = 1 and a.subloantypeid=b.id "
							+ " and b.statusid = "+LOANConstant.RecordStatus.VALID;

//			  //////////////////////查询条件////////////////////////////////////////////////////
			if (officeID > 0)
			{
				strSQL += " and a.OfficeID = " + officeID;
			}
			if (currencyID > 0)
			{
				strSQL += " and a.CurrencyID = " + currencyID;
			}
			if(strLoanType != null && strLoanType.length() > 0)
			{
				strSQL += " and a.LoanTypeID in ( " + strLoanType + " ) ";
			}

            
			strSQL += " order by a.SubLoanTypeID ";
			
			log4j.debug(strSQL);
			log4j.info("sql="+strSQL.toString());
			try
			{
				strSelect = " select distinct(a.SubLoanTypeID) ";
			
				strSQL =  strSelect + strSQL;
				
				prepareStatement(" select count(*) from ( "+strSQL + " ) ");
				ResultSet rs = executeQuery();
				while (rs != null && rs.next())
				{
					count = rs.getInt(1);
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				/*----2005-08-04----*/
				/*--神龙骑士修改漏洞--*/				
				if (transPS!=null)
				{
					transPS.close();
					transPS=null;
				}
				/*--神龙骑士修改漏洞--*/
                
				long[] allTmp = new long[count];
				int n = 0;
				prepareStatement(strSQL);
				rs = executeQuery();
				while (rs != null && rs.next() && n < count)
				{
					allTmp[n] = rs.getLong(1);
					n++;
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				
				allLoanType = allTmp;
			} catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
			} catch (SQLException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
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

		return allLoanType;
    
	}
	/*
	 * 参数：子类id
	 * 返回：大类id
	 * 表：loan_loanTypeRelation
	 */
public long getLoanTypeBySubLoan(long id)throws Exception{
    long loanTypeId=-1;
    String strsql="";
    ResultSet rs=null;
    PreparedStatement ps=null;
    Connection conn=null;
    
    try{
        strsql="select loantypeid from loan_loantyperelation where subloantypeid="+id;
        conn=Database.getConnection();
        ps=conn.prepareStatement(strsql);
        rs=ps.executeQuery();
        if(rs!=null&&rs.next())
        {
            loanTypeId=rs.getLong("loantypeid");
        }
        if(rs!=null)
        {
            rs.close();
            rs=null;
        }
        if(ps!=null)
        {
            ps.close();
            ps=null;
        }
        if(conn!=null)
        {
            conn.close();
            conn=null;
            
        }
        
        
    }catch(Exception e)
    {
        e.printStackTrace();
    }finally
    {
        if(rs!=null)
        {
            rs.close();
            rs=null;
        }
        if(ps!=null)
        {
            ps.close();
            ps=null;
        }
        if(conn!=null)
        {
            conn.close();
            conn=null;
            
        }
    }
    return loanTypeId;
}


    public static void main(String[] args)
    {
        //
  /*     LoanTypeRelationDao dao = new LoanTypeRelationDao();
        long strCode = -1;
        try 
        {
            strCode = dao.getLoanTypeBySubLoan(25);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("################strCode = "+strCode);*/
          
    }
    
	//效验操作
	public String validateSubTypeId(LoanTypeRelationInfo info) throws LoanDAOException
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
			ps.setLong(5, 0);

			rs = ps.executeQuery();
			
			while(rs.next())
			{
				lResult = rs.getString("loanSubTypeName");
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
            e.printStackTrace();
        } 
        finally
        {
            try
            {
                if (rs != null) 
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null) 
                {
                    ps.close();
                    ps = null;
                }
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        
        return lResult;
	}
	
}
