/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import java.sql.ResultSet;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditProductDAO extends LoanDAO {
  protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
  public CreditProductDAO() {
    super("loan_creditProduct");
  }

  public CreditProductDAO(Connection conn) {
    super("loan_creditProduct", conn);
  }

  /**
	 * 根据授信类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	/*public CreditProductInfo findProductInfoByCridetType(long creditTypeID)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByCreditTypeID(creditTypeID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}*/
	
  /**
   * 根据授信产品ID查询授信产品信息
   * @param loanTypeID
   * @return
   * @throws Exception
   */
  public CreditProductInfo findByCreditTypeID(CreditProductInfo qInfo) throws ITreasuryDAOException {
		CreditProductInfo info = null;
		String strSQL = null;
		
		try {
	       /*-----------------init DAO --------------------*/
	       try {
	         initDAO();
	       }
	       catch (ITreasuryDAOException e) {
	         throw new ITreasuryDAOException("创建连接时异常",e);
	       }
	       /*----------------end of init------------------*/
	       
	       try {
		      strSQL = "select * from loan_creditProduct t";
		      strSQL += " where t.statusID = " + Constant.RecordStatus.VALID;
		      strSQL += " and t.officeID = " + qInfo.getOfficeID();
		      strSQL += " and t.currencyID = " + qInfo.getCurrencyID();
		      strSQL += " and t.creditTypeID = " + qInfo.getCreditTypeID();
		      
		      prepareStatement(strSQL);
		      executeQuery();
		      info = (CreditProductInfo)this.getDataEntityFromResultSet(CreditProductInfo.class);
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	    	   throw new ITreasuryDAOException("查询授信产品分类设置出错", e);
	       }
	       
	       /*----------------finalize Dao-----------------*/
	       try {
	         finalizeDAO();
	       }
	       catch (ITreasuryDAOException e) {
	          throw new ITreasuryDAOException("关闭连接时异常",e);
	       }
	       /*----------------end of finalize---------------*/
		    
		}catch (Exception e) {
		    e.printStackTrace();
		    throw new ITreasuryDAOException("查询授信产品分类设置出错",e);
		}
		finally {
		  finalizeDAO();
		}
		
	    if(info != null && info.getId() > 0) {
	    	 return info;
	    }
	    else {
	    	 return null;
	    }
  }

  /**
   * 根据授信产品ID查询授信产品信息
   * @param loanTypeID
   * @return
   * @throws Exception
   */
  public CreditProductInfo findByLoanTypeID(long loanTypeID) throws ITreasuryDAOException {
		CreditProductInfo info = null;
		String strSQL = null;
		try {
		    initDAO();
		    strSQL = "select * from loan_creditProduct where statusID=1 and creditTypeID=" + loanTypeID;
		    prepareStatement(strSQL);
		    executeQuery();
		    info = (CreditProductInfo)this.getDataEntityFromResultSet(CreditProductInfo.class);
		}catch (Exception e) {
		    e.printStackTrace();
		    throw new ITreasuryDAOException("查询授信产品信息异常",e);
		}
		finally {
		  finalizeDAO();
		}
		return info;
  }
 
  public long deleteByCreditTypeID(long CreditTypeID) throws ITreasuryDAOException {
    String strSQL = null;
    long lResult = -1;

    try {
      /*-----------------init DAO --------------------*/
      try {
        initDAO();
      }
      catch (ITreasuryDAOException e) {
        throw new ITreasuryDAOException("创建连接时异常",e);
      }
      /*----------------end of init------------------*/

      try {
        strSQL = "update loan_creditProduct set statusID=1 where creditTypeId=" +
            CreditTypeID;
        prepareStatement(strSQL);
        log4j.info("sql: \n" + strSQL);
        lResult = executeUpdate();
      }
      catch (Exception e) {
        throw new ITreasuryDAOException("删除授信产品异常",e);
      }

      /*----------------finalize Dao-----------------*/
      try {
        finalizeDAO();
      }
      catch (ITreasuryDAOException e) {
        throw new ITreasuryDAOException("关闭连接时异常",e);
      }
      /*----------------end of finalize---------------*/
    }
    catch (ITreasuryDAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException(e.getMessage(),e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException(e.getMessage(),e);
    }
    finally {
      finalizeDAO();
    }

    return lResult;
  }

  
  /**
   * 得到在生命周期里的贷款申请和贷款合同的数量
   */
  public long getCountCreditByCreditTypeID(long CreditTypeID) throws ITreasuryDAOException {
    long count = 0;
    String strSQL = null;
    ResultSet rs = null;

    try {
      /*-----------------init DAO --------------------*/
      try {
        initDAO();
      }
      catch (ITreasuryDAOException e) {
        throw new ITreasuryDAOException("创建连接时异常",e);
      }
      /*----------------end of init------------------*/

      try {
        if (CreditTypeID == LOANConstant.CreditProduct.ZY) {
          strSQL = "SELECT COUNT(*) FROM (";
          strSQL += " SELECT id FROM loan_LoanForm l";
          strSQL += " WHERE l.nstatusID IN (" + LOANConstant.LoanStatus.SUBMIT;
          strSQL += "," + LOANConstant.LoanStatus.CHECK + ")";
          strSQL += " AND l.ntypeid IN(" + LOANConstant.LoanType.ZY;
          strSQL += "," + LOANConstant.LoanType.ZGXE;
          strSQL += "," + LOANConstant.LoanType.YT;
          strSQL += ")";

          strSQL += " UNION ALL";

          strSQL += " SELECT id FROM loan_contractForm c";
          strSQL += " WHERE c.nstatusid >= " + LOANConstant.ContractStatus.SAVE;
          strSQL += " AND c.nstatusid <= " +
              LOANConstant.ContractStatus.BADDEBT;
          strSQL += " AND c.ntypeid IN(" + LOANConstant.LoanType.ZY;
          strSQL += "," + LOANConstant.LoanType.ZGXE;
          strSQL += "," + LOANConstant.LoanType.YT;
          strSQL += "))";
        }
        else if (CreditTypeID == LOANConstant.CreditProduct.SP) {
          strSQL = "SELECT COUNT(*) FROM (";
          strSQL += " SELECT id FROM loan_LoanForm l";
          strSQL += " WHERE l.nstatusID IN (" + LOANConstant.LoanStatus.SUBMIT;
          strSQL += "," + LOANConstant.LoanStatus.CHECK + ")";
          strSQL += " AND l.ntypeid IN(" + LOANConstant.LoanType.TX;
          //贷款授信不考虑转贴现业务
          // strSQL += "," + LOANConstant.LoanType.ZTX;
          strSQL += ")";

          strSQL += " UNION ALL";

          strSQL += " SELECT id FROM loan_contractForm c";
          strSQL += " WHERE c.nstatusid >= " + LOANConstant.ContractStatus.SAVE;
          strSQL += " AND c.nstatusid <= " +
              LOANConstant.ContractStatus.BADDEBT;
          strSQL += " AND c.ntypeid IN(" + LOANConstant.LoanType.TX;
          //贷款授信不考虑转贴现业务
          //strSQL += "," + LOANConstant.LoanType.ZTX;
          strSQL += "))";
        }

        if (strSQL != null && strSQL.length() > 0) {
          prepareStatement(strSQL);
          log4j.info("sql: \n" + strSQL);
          rs = executeQuery();
          if (rs.next()) {
            count = rs.getLong(1);
          }
        }
      }
      catch (Exception e) {
        throw new ITreasuryDAOException("查询异常",e);
      }

      /*----------------finalize Dao-----------------*/
      try {
        finalizeDAO();
      }
      catch (ITreasuryDAOException e) {
        throw new ITreasuryDAOException("关闭连接时异常",e);
      }
      /*----------------end of finalize---------------*/
    }
    catch (ITreasuryDAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException(e.getMessage(),e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException(e.getMessage(),e);
    }
    finally {
      finalizeDAO();
    }

    return count;
  }
  public long findIsControlProduct(long loanType)throws ITreasuryDAOException{
      long r=-1;
      ResultSet rs = null;
      PreparedStatement ps=null;
      String strSQL = "";
      try {
            try {
                initDAO();
            } catch (ITreasuryDAOException e) {
                throw new ITreasuryDAOException("创建连接时异常",e);
            }
           strSQL="select iscontrol from loan_creditproduct "
                    +" where STATUSID=1 and LOANTYPEID="+loanType;
           this.prepareStatement(strSQL);
           rs=this.executeQuery();
           if(rs.next()){
             r=rs.getLong("iscontrol");
           }
           
               
      }catch(SQLException e){
          e.printStackTrace();
          throw new ITreasuryDAOException("查询授信产品是否控制时异常",e);
      }finally {
          finalizeDAO();
          }
      return r;
  }
  
  
  
  
  
  
  
  
  
//-----------------------------------------------以下内容为授信信用、担保额度 暂时没有此类业务－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  
  public long typeTrans(long creditProductType) throws Exception {
      long loanType = -1;
      try {
          switch ((int) creditProductType) {
          case (int) LOANConstant.CreditProduct.ZY:
              loanType = LOANConstant.LoanType.ZY;
              break;
          case (int) LOANConstant.CreditProduct.SP:
              loanType = LOANConstant.LoanType.TX;
              break;
          case (int) LOANConstant.CreditProduct.BH:
              loanType = LOANConstant.LoanType.DB;
              break;
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return loanType;
  }
  public long untypeTrans(long loanType) throws Exception {
      long productType = -1;
      try {
          switch ((int) loanType) {
          case (int) LOANConstant.LoanType.ZY:
          
              productType = LOANConstant.CreditProduct.ZY;
              break;
          case (int) LOANConstant.LoanType.TX:
          
              productType = LOANConstant.CreditProduct.SP;
              break;
          case (int) LOANConstant.LoanType.DB:
          
              productType = LOANConstant.CreditProduct.BH;
              break;
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return productType;
  }
}
