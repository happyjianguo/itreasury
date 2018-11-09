/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.dao;

import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitChangeInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitQueryInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import java.util.*;

import com.iss.itreasury.settlement.util.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditLimitDAO
    extends LoanDAO {
  protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
  public CreditLimitDAO() {
    super("loan_creditLimit");
  }

  public CreditLimitDAO(Connection conn) {
    super("loan_creditLimit", conn);
  }

  
  /**
   * ��ѯ������Ϣ
   * @param qInfo
   * @return
   * @throws ITreasuryDAOException
 * @throws SQLException 
   */
  public Collection findByMultiOption(CreditLimitQueryInfo qInfo) throws
      ITreasuryDAOException, SQLException {
	  System.out.println("welcome to Collection findByMultiOption");
	  Collection coll = new ArrayList();
	  Connection conn = null;
      String strSQL = "";
      PreparedStatement ps = null;
      ResultSet rs = null;
      CreditLimitInfo creditLimitInfo = null;

    long queryPurpose = qInfo.getQueryPurpose();

    try {
      /*-----------------init DAO --------------------*/
           conn = Database.getConnection();
      	 
          strSQL = "select lc.* from loan_creditLimit lc,CLIENT_CLIENTINFO cc " +
                	" where cc.id = lc.Clientid  ";
        
          if (queryPurpose == 1) { //for ���Ӳ���
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.SAVE+")";
          }
          else if (queryPurpose == 2) { //����
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.SAVE+")";
          }
          else if(queryPurpose == 3){///ȡ������
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.ACTIVE+")";
          }
          else if(queryPurpose == 4) { //����
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.ACTIVE+")";
          }
          else if(queryPurpose == 5) { //ȡ������
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.FREEZE+")";
          }
          else if(queryPurpose == 6){ //����Ͷ���״̬
        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.ACTIVE+","+LOANConstant.CreditStatus.FREEZE+")";
          }
          if(qInfo.getOfficeId() > 0){ //���´�ID
        	  strSQL += "and cc.OFFICEID = "+qInfo.getOfficeId();
          }
          if (qInfo.getStartClientID() > 0) { //�ͻ�ID��ʼ
            strSQL += " and lc.ClientID>=" + qInfo.getStartClientID();
          }
          if (qInfo.getEndClientID() > 0) { //�ͻ�ID����
            strSQL += " and lc.clientID<=" + qInfo.getEndClientID();
          }
          if (qInfo.getClientID() > 0) {  //�ͻ�ID
            strSQL += " and lc.clientID=" + qInfo.getClientID();
          }
          if (qInfo.getId() > 0) { 
              strSQL += " and lc.id=" + qInfo.getId();
            }
          if (qInfo.getCreditTypeID() > 0) {//����Ʒ��ID
              strSQL += " and lc.creditTypeID=" + qInfo.getCreditTypeID();
            }
          if (qInfo.getCurrentAmount() > 0) {//���Ŷ��
              strSQL += " and lc.amount=" + qInfo.getCurrentAmount();
            }  
          if (qInfo.getStartAmount() > 0) { //���Ŷ�ȿ�ʼ
            strSQL += " and lc.amount>=" + qInfo.getStartAmount();
          }
          if (qInfo.getEndAmount() > 0) { //���Ŷ�Ƚ���
            strSQL += " and lc.amount<=" + qInfo.getEndAmount();
          }
          //��������
          if (qInfo.getStartDate() != null && qInfo.getEndDate() != null ) {
              strSQL +=" and lc.enddate>=to_date('" + DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')" 
                     +" and lc.startdate<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')" ;
          }
          //���ſ�ʼ����
          if (qInfo.getStartDate() != null && qInfo.getEndDate() == null ) {
              strSQL +=" and lc.enddate>=to_date('" + DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')"; 
          }
          //���Ž�������
          if (qInfo.getStartDate() == null && qInfo.getEndDate() != null ) { 
        	  strSQL +=" and lc.startdate<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')" ;
          } 
          //¼�����ڿ�ʼ
          if (qInfo.getStartInputDate() != null &&
              qInfo.getStartInputDate().length() > 0) {
            strSQL += " and lc.INPUTDATE>=to_date('" + qInfo.getStartInputDate().toString().substring(0,10);
            strSQL += "','yyyy-mm-dd')";
          }
          //¼�����ڽ���
          if (qInfo.getEndInputDate() != null &&
              qInfo.getEndInputDate().length() > 0) {
            strSQL += " and lc.INPUTDATE<=to_date('" + qInfo.getEndInputDate().toString().subSequence(0, 10);
            strSQL += "','yyyy-mm-dd')";
          }
          if (qInfo.getStatusID() > 0) {
            strSQL += " and lc.statusID=" + qInfo.getStatusID();
          }
          ////////////////////////////������//////////////////////////////////////////////////////////////////////
          int nIndex = 0;
          String orderParamString = qInfo.getOrderParamString();

          nIndex = orderParamString.indexOf(".");

          if (nIndex > 0) {
            if (orderParamString.substring(0,nIndex).toLowerCase().equals("loan_creditlimit")) {
              strSQL += " order by lc." + orderParamString.substring(nIndex + 1);
            }
          }
          else {
            strSQL += " order by lc.ID";
          }

          if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
            strSQL += " desc";
          }
    	  
        log4j.info("sql= \n" + strSQL);

        ps = conn.prepareStatement(strSQL);
        rs = ps.executeQuery();
        while(rs.next()){
        	creditLimitInfo = new CreditLimitInfo();
        	creditLimitInfo.setId(rs.getLong("ID"));
        	creditLimitInfo.setClientID(rs.getLong("CLIENTID"));
        	creditLimitInfo.setCreditTypeID(rs.getLong("CREDITTYPEID"));
        	creditLimitInfo.setStatusID(rs.getLong("STATUSID"));
        	creditLimitInfo.setStartDate(rs.getTimestamp("STARTDATE"));
        	creditLimitInfo.setEndDate(rs.getTimestamp("ENDDATE"));
        	creditLimitInfo.setInputUserID(rs.getLong("INPUTUSERID"));
        	creditLimitInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
        	creditLimitInfo.setActiveUserID(rs.getLong("ACTIVEUSERID"));
        	creditLimitInfo.setActiveDate(rs.getTimestamp("ACTIVEDATE"));
        	creditLimitInfo.setFreeUserID(rs.getLong("FREEUSERID"));
        	creditLimitInfo.setFreeDate(rs.getTimestamp("FREEDATE"));
        	creditLimitInfo.setRemark(rs.getString("REMARK"));
        	creditLimitInfo.setAmount(rs.getDouble("AMOUNT"));
        	coll.add(creditLimitInfo);
        }

      if(rs!=null){
    	  rs.close();
    	  rs = null;
      }
      if(ps!=null){
    	  ps.close();
    	  ps = null;
      }
      if(conn!=null){
    	  conn.close();
    	  conn = null;
      }
    }catch (Exception e) {
        e.printStackTrace();  
    	throw new ITreasuryDAOException("��ѯ�������ó���", e);
      }
     finally {
      	  if(rs!=null){
        	  rs.close();
        	  rs = null;
          }
          if(ps!=null){
        	  ps.close();
        	  ps = null;
          }
          if(conn!=null){
        	  conn.close();
        	  conn = null;
          }     
     }
         return coll;
  }

  
  /**
   * ��������Ʒ�ּ��㣨��ʹ�ý���ռ�ý����ý�
   * @param info
   * @return
   * @throws Exception
   */
  public CreditLimitInfo findAmountByCreditLimitInfo(CreditLimitInfo info) throws ITreasuryDAOException {
	  CreditLimitInfo tempInfo = new CreditLimitInfo();
      try {
          
    	  double amount = 0; 
    	  if(info.getCurrentAmount()!= 0){
    		  amount = info.getCurrentAmount();
    	  }else{
    		  amount = info.getAmount();
    	  }
    	  //��ѯ��������
    	  tempInfo = findApplyAmountByClient(info);
          info.setZyApplyAmount(tempInfo.getZyApplyAmount());
          info.setSpApplyAmount(tempInfo.getSpApplyAmount());
          //��ѯ��ʹ�ý��
          if(info.isSameInterval()==true){
             tempInfo = findUseAmountByClient(info);
          }else{
        	 tempInfo = findUseAmountByClientForEndate(info); 
          }
          info.setZyUsedAmount(tempInfo.getZyUsedAmount());
          info.setSpUsedAmount(tempInfo.getSpUsedAmount());
          
          //��Ӫ����
          if(info.getCreditTypeID() == LOANConstant.CreditProduct.ZY){
        	 info.setApplyAmount(info.getZyApplyAmount());
        	 info.setUsedAmount(info.getZyUsedAmount());
        	 info.setCanUseAmount(amount-info.getZyApplyAmount()-info.getZyUsedAmount());
          //��Ʊ
          }else if(info.getCreditTypeID() == LOANConstant.CreditProduct.SP){
        	 info.setApplyAmount(info.getSpApplyAmount());
         	 info.setUsedAmount(info.getSpUsedAmount());
         	 info.setCanUseAmount(amount-info.getSpApplyAmount()-info.getSpUsedAmount());
          //�ۺ�����
          }else{
	         info.setApplyAmount(info.getZyApplyAmount()+info.getSpApplyAmount());
	         info.setUsedAmount(info.getZyUsedAmount()+info.getSpUsedAmount());
	         info.setCanUseAmount(amount-info.getZyApplyAmount()-info.getZyUsedAmount()-info.getSpApplyAmount()-info.getSpUsedAmount());
          }
    	   
      } catch (Exception e) {
    	  e.printStackTrace();
    	  throw new ITreasuryDAOException("����ҵ������ת��������", e);
      }
      return info;
  }
 /**
  * ��ѯ�ͻ�ĳһʱ������Ƿ��������Ŷ������
  * @param Info
  * @return
  * @throws ITreasuryDAOException
  */
  public Collection findByDateOption(CreditLimitInfo Info) throws
      ITreasuryDAOException {
    Collection c = null;
    String strSQL = "";
    if (Info.getStartDate() == null && Info.getEndDate() == null) {
    	return c;
    }
    try {
      /*-----------------init DAO --------------------*/
      try {
        initDAO();
      }
      catch (ITreasuryDAOException e) {
         throw new ITreasuryDAOException("��������ʱ�쳣",e);
      }
      /*----------------end of init------------------*/
      try {
        strSQL = "select * from loan_creditLimit ";
        strSQL += " where clientID =" + Info.getClientID();
        strSQL += " and statusID > 0";
        strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
        strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;
        //����ʱ���ж����Ų�Ʒ����
        if(Info.getCreditTypeID() > 0){
        	//���Ų�Ʒ����Ϊ��Ʊ
        	if(Info.getCreditTypeID()==LOANConstant.CreditProduct.SP){
        		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
        	//���Ų�Ʒ����Ϊ��Ӫ
        	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZY){
        		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.ZHSX+" )";
        	//�ۺ�����
        	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZHSX){
        		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
        	}
        }
        if (Info.getId()>0 ){
	    	strSQL+="and   id !="+Info.getId();
	    }
        if(Info.getOfficeID()>0){
        	strSQL+=" and officeid="+Info.getOfficeID();
        }
        if(Info.getCurrencyID()>0){
        	strSQL+=" and currencyid="+Info.getCurrencyID();
        }
        strSQL += " and ((STARTDATE<=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and ENDDATE>=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd'))";

        strSQL += " or ( STARTDATE>=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and STARTDATE<=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd'))";

        strSQL += " or ( ENDDATE>=to_date('" +
            Info.getStartDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')";
        strSQL += " and ENDDATE<=to_date('" +
            Info.getEndDate().toString().substring(0, 10);
        strSQL += "','yyyy-mm-dd')))";
	       
        System.out.println("sql= \n" + strSQL);
        prepareStatement(strSQL);
        executeQuery();
        c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

      }
      catch (Exception e) {
        throw new ITreasuryDAOException("��ѯ�������ó���", e);
      }

      /*----------------finalize Dao-----------------*/
      try {
        finalizeDAO();
      }
      catch (ITreasuryDAOException e) {
        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
      }
      /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      finalizeDAO();
    }
    return (c.size() > 0 ? c : null);
  }
  /**
   * ��ѯ�ͻ�ĳһʱ������Ƿ��������Ŷ������
   * @param Info
   * @return
   * @throws ITreasuryDAOException
   */
   public Collection findByDateOption(CreditLimitChangeInfo Info) throws
       ITreasuryDAOException {
     Collection c = null;
     String strSQL = "";
     if (Info.getStartDate() == null && Info.getEndDate() == null) {
     	return c;
     }
     try {
       /*-----------------init DAO --------------------*/
       try {
         initDAO();
       }
       catch (ITreasuryDAOException e) {
          throw new ITreasuryDAOException("��������ʱ�쳣",e);
       }
       /*----------------end of init------------------*/
       try {
         strSQL = "select * from loan_creditLimitDetail ";
         strSQL += " where clientID =" + Info.getClientID();
         strSQL += " and statusID > 0";
         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
         strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;
         //����ʱ���ж����Ų�Ʒ����
         if(Info.getCreditTypeID() > 0){
         	//���Ų�Ʒ����Ϊ��Ʊ
         	if(Info.getCreditTypeID()==LOANConstant.CreditProduct.SP){
         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
         	//���Ų�Ʒ����Ϊ��Ӫ
         	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZY){
         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.ZHSX+" )";
         	//�ۺ�����
         	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZHSX){
         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
         	}
         }
         if (Info.getId()>0 ){
 	    	strSQL+="and   id !="+Info.getId();
 	    }
         if(Info.getOfficeID()>0){
         	strSQL+=" and officeid="+Info.getOfficeID();
         }
         if(Info.getCurrencyID()>0){
         	strSQL+=" and currencyid="+Info.getCurrencyID();
         }
         strSQL += " and ((STARTDATE<=to_date('" +
             Info.getStartDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd')";
         strSQL += " and ENDDATE>=to_date('" +
             Info.getEndDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd'))";

         strSQL += " or ( STARTDATE>=to_date('" +
             Info.getStartDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd')";
         strSQL += " and STARTDATE<=to_date('" +
             Info.getEndDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd'))";

         strSQL += " or ( ENDDATE>=to_date('" +
             Info.getStartDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd')";
         strSQL += " and ENDDATE<=to_date('" +
             Info.getEndDate().toString().substring(0, 10);
         strSQL += "','yyyy-mm-dd')))";
 	       
         System.out.println("sql= \n" + strSQL);
         prepareStatement(strSQL);
         executeQuery();
         c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

       }
       catch (Exception e) {
         throw new ITreasuryDAOException("��ѯ�������ó���", e);
       }

       /*----------------finalize Dao-----------------*/
       try {
         finalizeDAO();
       }
       catch (ITreasuryDAOException e) {
         throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
       }
       /*----------------end of finalize---------------*/
     }
     catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
       throw new ITreasuryDAOException("��ѯ�쳣",e);
     }
     finally {
       finalizeDAO();
     }
     return (c.size() > 0 ? c : null);
   }
  
   
  /**
   * ��������Ŀ�ʼʱ��/����ʱ�䡢�������ͣ��ͻ�ID �ж��Ƿ��������Ŷ������
   * @param Info
   * @return
   * @throws ITreasuryDAOException
   */
   public CreditLimitInfo findDateByCreditLimit(CreditLimitInfo Info, long queryId) throws
       ITreasuryDAOException {
	   
	 CreditLimitInfo limitInfo = null;
     String strSQL = "";
     
     if(Info.getStartDate()==null){
    	 return null;
     }

     try {
       /*-----------------init DAO --------------------*/
       try {
         initDAO();
       }
       catch (ITreasuryDAOException e) {
         throw new ITreasuryDAOException("��������ʱ�쳣",e);
       }
       /*----------------end of init------------------*/
       try {
         strSQL = "select * from loan_creditLimit ";
         strSQL += " where clientID =" + Info.getClientID();
         strSQL += " and statusID =" + LOANConstant.CreditStatus.ACTIVE;
         if(Info.getOfficeID()>0){
	         strSQL+=" and officeid =" + Info.getOfficeID();
	     }
	     if(Info.getCurrencyID()>0){
	         strSQL+=" and currencyid =" + Info.getCurrencyID();
	     }
         if(Info.getCreditModeID()>0){
	         	strSQL+=" and creditmodeId="+Info.getCreditModeID();
	     }
         //����ʱ���ж����Ų�Ʒ����
         if(Info.getCreditTypeID() > 0){
        	strSQL+=" and credittypeid in("+LOANConstant.queryCreditProduct.getQueryString(queryId, Info.getCreditTypeID())+")";
         }
 	     if(Info.getStartDate() != null){  
	        strSQL += " and STARTDATE<=to_date('" + DataFormat.getDateString(Info.getStartDate()) + "','yyyy-mm-dd')";
	        strSQL += " and ENDDATE>=to_date('" + DataFormat.getDateString(Info.getStartDate()) + "','yyyy-mm-dd')";
 	     }
 	     /*if(Info.getEndDate() != null){
 	    	strSQL += " and STARTDATE<=to_date('" + DataFormat.getDateString(Info.getEndDate()) + "','yyyy-mm-dd'))";
            strSQL += " and ENDDATE>=to_date('" + DataFormat.getDateString(Info.getEndDate()) + "','yyyy-mm-dd'))";
 	     }*/
         System.out.println("��ѯ�ͻ����� sql= \n" + strSQL);
         prepareStatement(strSQL);
         executeQuery();
         limitInfo = (CreditLimitInfo)getDataEntityFromResultSet(CreditLimitInfo.class);

       }
       catch (Exception e) {
           e.printStackTrace();
    	   throw new ITreasuryDAOException("��ѯ�������ó���", e);
       }

       /*----------------finalize Dao-----------------*/
       try {
         finalizeDAO();
       }
       catch (ITreasuryDAOException e) {
          throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
       }
       /*----------------end of finalize---------------*/
     }
     catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
       throw new ITreasuryDAOException(e.getMessage(),e);
     }
     finally {
       finalizeDAO();
     }
     
     if(limitInfo != null && limitInfo.getId() > 0) {
    	 return limitInfo;
     }
     else {
    	 return null;
     }
   }
   
   
   /**
    * ��������Ŀ�ʼʱ��/����ʱ���ж��Ƿ��������Ŷ�ȵĶ���
    * @param Info
    * @return
    * @throws ITreasuryDAOException
    */
   public CreditLimitInfo IsNotCreditfreeze(CreditLimitInfo Info) throws
   ITreasuryDAOException {
	   CreditLimitInfo limitInfo = null;
	   String strSQL = "";
	   if(Info.getStartDate()==null && Info.getEndDate()==null){
		   return limitInfo;
	   }
	   
	   try {
		   /*-----------------init DAO --------------------*/
		   try {
			   initDAO();
		   }
		   catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("��������ʱ�쳣",e);
		   }
		   /*----------------end of init------------------*/
		   try {
			   strSQL = "select * from loan_creditLimit ";
			   strSQL += " where clientID =" + Info.getClientID();
//         strSQL += " and statusID > 0";
//         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
//         strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;
			   strSQL += " and statusID =" + LOANConstant.CreditStatus.FREEZE;
			   //����ʱ���ж����Ų�Ʒ����
			   if(Info.getCreditTypeID() > 0){
				   //���Ų�Ʒ����Ϊ��Ʊ
				   if(Info.getCreditTypeID()==LOANConstant.CreditProduct.SP){
					   strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
					   //���Ų�Ʒ����Ϊ��Ӫ
				   }else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZY){
					   strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.ZHSX+" )";
					   //�ۺ�����
				   }else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZHSX){
					   strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
				   }
			   }
			   if(Info.getStartDate()!=null){  
				   strSQL += " and STARTDATE<=to_date('" +
				   Info.getStartDate().toString().substring(0, 10);
				   strSQL += "','yyyy-mm-dd')";
				   strSQL += " and ENDDATE>=to_date('" +
				   Info.getStartDate().toString().substring(0, 10);
				   strSQL += "','yyyy-mm-dd')";
			   }else if(Info.getEndDate()!=null){
				   strSQL += " and STARTDATE<=to_date('" +
				   Info.getEndDate().toString().substring(0, 10);
				   strSQL += "','yyyy-mm-dd')";
				   strSQL += " and ENDDATE>=to_date('" +
				   Info.getEndDate().toString().substring(0, 10);
				   strSQL += "','yyyy-mm-dd')";
			   }
			   System.out.println("sql= \n" + strSQL);
			   prepareStatement(strSQL);
			   executeQuery();
			   limitInfo = (CreditLimitInfo)getDataEntityFromResultSet(CreditLimitInfo.class);
			   
		   }
		   catch (Exception e) {
			   e.printStackTrace();
			   throw new ITreasuryDAOException("��ѯ�������ó���", e);
		   }
		   
		   /*----------------finalize Dao-----------------*/
		   try {
			   finalizeDAO();
		   }
		   catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
		   }
		   /*----------------end of finalize---------------*/
	   }
	   catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   throw new ITreasuryDAOException(e.getMessage(),e);
	   }
	   finally {
		   finalizeDAO();
	   }
	   return limitInfo;
   }


  
  /**
   * ���ݿͻ���Ϣ��ѯ������
   * @param clientID
   * @return
 * @throws SQLException 
   * @throws Exception
   */
  public CreditLimitInfo findApplyAmountByClient(CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditProductInfo cpInfo = null;
    CreditLimitInfo clInfo = new CreditLimitInfo();
    CreditProductDAO cpDao = new CreditProductDAO();
    double ddRate = 0;
    double ttRate = 0;
    double zyAmount = 0;
    double spAmount = 0;

    try {
    	cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.ZY);
      if (cpInfo != null) {
        ddRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ӫ���Ų�Ʒռ�ö�ȱ�����" + ddRate);

      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.TX);
      if (cpInfo != null) {
        ttRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ʊ���Ų�Ʒռ�ö�ȱ�����" + ttRate);

      conn = Database.getConnection();
      strSQL =
          "select nvl(sum(a.MEXAMINEAMOUNT),0) mloanamount " +
          " from loan_loanForm a "
          + " where 1=1 "
          + " and a.nstatusID in("
          + LOANConstant.LoanStatus.APPROVALING
          + ","+LOANConstant.LoanStatus.SAVE+")"
          + " and a.nBorrowClientID="
          + creditLimitInfo.getClientID()
          + " and a.nTypeID in ("
          + LOANConstant.LoanType.ZY
          + ")";
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      System.out.println("��ѯ��Ӫ������������SQL����"+strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        zyAmount = rs.getDouble(1) * ddRate;
      }
      clInfo.setZyApplyAmount(zyAmount);
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }

      strSQL =
          "select nvl(sum(a.MEXAMINEAMOUNT),0) mloanamount" +
          " from loan_loanForm a "
          + " where 1=1 "
          + " and a.nstatusID in("
          + LOANConstant.LoanStatus.APPROVALING
          + ","+LOANConstant.LoanStatus.SAVE+")"
          + " and a.nBorrowClientID="
          + creditLimitInfo.getClientID()
          + " and a.nTypeID in ("
          + LOANConstant.LoanType.TX
          + ")";
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      System.out.println("��ѯ��Ʊ��������SQL����"+strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        spAmount = rs.getDouble(1) * ttRate;
      }
      clInfo.setSpApplyAmount(spAmount);
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ����쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }

    return clInfo;
  }

 /**
  * ���ݿͻ�ID��ѯʹ�ý��
  * @param clientID
  * @return
  * @throws SQLException 
  * @throws Exception
  */
  public CreditLimitInfo findUseAmountByClient(CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditProductInfo cpInfo = null;
    CreditLimitInfo clInfo = new CreditLimitInfo();
    CreditProductDAO cpDao = new CreditProductDAO();
    double ddRate = 0;
    double ttRate = 0;
    double zyAmount = 0;
    double spAmount = 0;
    double repayAmount = 0;
    double discountRepayAmount = 0;

    try {
      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.ZY);
      if (cpInfo != null) {
        ddRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ӫ����ռ�ö�ȱ�����" + ddRate);

      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.TX);
      if (cpInfo != null) {
        ttRate = cpInfo.getEngrossRate();
      }
      Log.print("����ռ�ö�ȱ�����" + ttRate);

      conn = Database.getConnection();

      repayAmount = this.getRepayAmount(conn, creditLimitInfo);
      discountRepayAmount = this.getDiscountRepayAmount(conn, creditLimitInfo);
      strSQL =
          "select sum(mExamineAmount) mExamineAmount from loan_contractForm"
          + " where 1=1"
          + " and (( nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
          + " and nBorrowClientID="
          + creditLimitInfo.getClientID()
          + " and nTypeID in ("
          + LOANConstant.LoanType.ZY
          + ")";
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      log4j.print(strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        zyAmount = (rs.getDouble(1) - repayAmount) * ddRate;
      }
      clInfo.setZyUsedAmount(zyAmount);
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }

      strSQL =
          "select sum(mExamineAmount) mExamineAmount from loan_contractForm"
          + " where 1=1"
          + " and (( nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
          + " and nBorrowClientID="
          + creditLimitInfo.getClientID()
          + " and nTypeID in ("
          + LOANConstant.LoanType.TX
          + ")";
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      log4j.print(strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        spAmount = (rs.getDouble(1) - discountRepayAmount) * ttRate;
      }
      clInfo.setSpUsedAmount(spAmount);
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯʹ�ý���쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    return clInfo;
  }

  /**
   * ���ݿͻ�ID��ѯʹ�ý��(��������)
   * @param clientID
   * @return
   * @throws SQLException 
   * @throws Exception
   */
   public CreditLimitInfo findUseAmountByClientForEndate(CreditLimitInfo info) throws ITreasuryDAOException, SQLException {
     Connection conn = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     String strSQL = "";
     CreditProductInfo cpInfo = null;
     CreditLimitInfo clInfo = new CreditLimitInfo();
     CreditProductDAO cpDao = new CreditProductDAO();
     double ddRate = 0;
     double ttRate = 0;
     double zyAmount = 0;
     double spAmount = 0;
     double repayAmount = 0;
     double discountRepayAmount = 0;

     try {
       cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.ZY);
       if (cpInfo != null) {
         ddRate = cpInfo.getEngrossRate();
       }
       Log.print("��Ӫ����ռ�ö�ȱ�����" + ddRate);

       cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.TX);
       if (cpInfo != null) {
         ttRate = cpInfo.getEngrossRate();
       }
       Log.print("����ռ�ö�ȱ�����" + ttRate);

       conn = Database.getConnection();

       repayAmount = this.getRepayAmountForEnddate(conn, info);
       discountRepayAmount = this.getDiscountRepayAmountForEnddate(conn,info);
       strSQL =
           "select sum(mExamineAmount) from loan_contractForm"
           + " where 1=1"
           + "  and  ((nStatusID>="
           + LOANConstant.ContractStatus.SAVE
           + " and nStatusID<="
           + LOANConstant.ContractStatus.BADDEBT+") "
           + " or nStatusID="
           + LOANConstant.ContractStatus.APPROVALING+")"
           + " and nBorrowClientID="
           + info.getClientID()
           + " and nTypeID in ("
           + LOANConstant.LoanType.ZY
           + ")"
           + " and dtenddate > to_date('"+DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')";
       log4j.print(strSQL);
       ps = conn.prepareStatement(strSQL);
       rs = ps.executeQuery();
       if (rs.next()) {
         zyAmount = (rs.getDouble(1) - repayAmount) * ddRate;
       }
       clInfo.setZyUsedAmount(zyAmount);
       if (rs != null) {
         rs.close();
         rs = null;
       }
       if (ps != null) {
         ps.close();
         ps = null;
       }

       strSQL =
           "select sum(mExamineAmount) from loan_contractForm"
           + " where 1=1"
           + " and ((nStatusID>="
           + LOANConstant.ContractStatus.SAVE
           + " and nStatusID<="
           + LOANConstant.ContractStatus.BADDEBT+") "
           + " or nStatusID="
           + LOANConstant.ContractStatus.APPROVALING+")"
           + " and nBorrowClientID="
           + info.getClientID()
           + " and nTypeID in ("
           + LOANConstant.LoanType.TX
           + ")"
           + " and dtenddate > to_date('"+DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')";
       log4j.print(strSQL);
       ps = conn.prepareStatement(strSQL);
       rs = ps.executeQuery();
       if (rs.next()) {
         spAmount = (rs.getDouble(1) - discountRepayAmount) * ttRate;
       }
       clInfo.setSpUsedAmount(spAmount);
       if (rs != null) {
         rs.close();
         rs = null;
       }
       if (ps != null) {
         ps.close();
         ps = null;
       }
       if (conn != null) {
         conn.close();
         conn = null;
       }
     }
     catch (SQLException e) {
       e.printStackTrace();
       throw new ITreasuryDAOException("SQL�쳣",e);
     }
     catch (Exception e) {
       e.printStackTrace();
       throw new ITreasuryDAOException("��ѯ���ʱ�쳣",e);
     }
     finally {
       if (rs != null) {
         rs.close();
         rs = null;
       }
       if (ps != null) {
         ps.close();
         ps = null;
       }
       if (conn != null) {
         conn.close();
         conn = null;
       }
     }
     return clInfo;
   }
  
  /**
   * ���ݿͻ�ID��ѯ���Ŷ��������Ϣ���Ѽ���Ѷ��ᣩ
   * @param clientID
   * @return
 * @throws SQLException 
   * @throws Exception
   */
  public CreditLimitInfo findCreditLimitByClientID(long clientID) throws
    ITreasuryDAOException, SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditLimitInfo info = null;

    try {
      conn = Database.getConnection();
      strSQL =
          " select * from Loan_CreditLimit "
          + " where 1 = 1 "
          + " and StatusID in ( "
          + LOANConstant.CreditStatus.ACTIVE
          + " , "
          + LOANConstant.CreditStatus.FREEZE
          + " ) "
          + " and clientID = "
          + clientID;

      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        info = new CreditLimitInfo();
        info.setId(rs.getLong("ID"));
        info.setClientID(rs.getLong("clientID"));
        info.setCreditTypeID(rs.getLong("credittypeid"));  
        //info.setCreditAmount(rs.getDouble("creditAmount"));
        info.setAmount(rs.getDouble("amount"));
        //info.setAssureAmount(rs.getDouble("assureAmount"));
   
        info.setStartDate(rs.getTimestamp("startDate"));
        info.setEndDate(rs.getTimestamp("endDate"));
        info.setStatusID(rs.getLong("statusID"));
        info.setInputDate(rs.getTimestamp("inputDate"));
        info.setInputUserID(rs.getLong("inputUserID"));
        //��������Ʒ�ּ��㲻ͬ�Ķ�Ƚ��
        info = findAmountByCreditLimitInfo(info);
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ������Ϣ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    return info;
  }
  
  
  /**
   * ��ѯ���Ŷ����������������ϸ
   * @param clientID
   * @return
 * @throws SQLException 
   * @throws Exception
   */
  
  public Collection findApplyDetail(CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    Vector v = new Vector();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditProductInfo cpInfo = null;
    CreditProductDAO cpDao = new CreditProductDAO();
    double ddRate = 0;
    double ttRate = 0;

    try {
      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.ZY);
      if (cpInfo != null) {
        ddRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ӫ����ռ�ö�ȱ�����" + ddRate);

      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.TX);
      if (cpInfo != null) {
        ttRate = cpInfo.getEngrossRate();
      }
      Log.print("����ռ�ö�ȱ�����" + ttRate);

      conn = Database.getConnection();
      strSQL =
          "select a.* from loan_loanForm a"
          + " where 1=1"
          + " and a.nstatusID in("
          + LOANConstant.LoanStatus.APPROVALING
          +","+LOANConstant.LoanStatus.SAVE
          + " ) and a.nBorrowClientID="
          + creditLimitInfo.getClientID();
      if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZY){ 
          strSQL+=" and a.ntypeid in("+LOANConstant.LoanType.ZY+")";
      }else if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.SP){ 
          strSQL+=" and a.ntypeid in("+LOANConstant.LoanType.TX+")";
      }else if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZHSX){
    	  strSQL+=" and a.ntypeid in("+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.TX+")";    	  
      }
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      System.out.println("strSQL==="+strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();

      while (rs != null && rs.next()) {
        LoanApplyInfo applyInfo = new LoanApplyInfo();
        applyInfo.setID(rs.getLong("ID"));
        applyInfo.setTypeID(rs.getLong("ntypeid"));
        applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
        applyInfo.setOfficeID(rs.getLong("nofficeid"));
        applyInfo.setApplyCode(rs.getString("sapplycode"));
        applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
        applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
        applyInfo.setInputUserID(rs.getLong("ninputuserid"));
        applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
        applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
        applyInfo.setLoanReason(rs.getString("sloanreason"));
        applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
        applyInfo.setOther(rs.getString("sother"));
        applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
        applyInfo.setChargeRate(rs.getDouble("mchargerate"));
        applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
        applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
        applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
        applyInfo.setClientInfo(rs.getString("sclientinfo"));
        applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
        applyInfo.setInterestRate(rs.getDouble("minterestrate"));
        applyInfo.setIsCircle(rs.getLong("niscircle"));
        applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
        applyInfo.setIsTechnical(rs.getLong("nistechnical"));
        applyInfo.setIsCredit(rs.getLong("niscredit"));
        applyInfo.setIsAssure(rs.getLong("nisassure"));
        applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
        applyInfo.setIsPledge(rs.getLong("nispledge"));
        applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
        applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
        applyInfo.setStatusID(rs.getLong("nStatusID"));
        applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
        applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
        applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
        applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
        applyInfo.setTypeID1(rs.getLong("nTypeID1"));
        applyInfo.setTypeID2(rs.getLong("nTypeID2"));
        applyInfo.setTypeID3(rs.getLong("nTypeID3"));
        applyInfo.setNBankAcceptPO(rs.getLong("nBankAcceptPO"));
        applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
        applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
        applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
        applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
        applyInfo.setAdjustRate(rs.getDouble("mAdjustRate"));
        applyInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
        //if (applyInfo.getTypeID() == LOANConstant.LoanType.TX ||applyInfo.getTypeID() == LOANConstant.LoanType.ZTX) {
        if (applyInfo.getTypeID() == LOANConstant.LoanType.TX){ 
           applyInfo.setUseCreditAmount(applyInfo.getExamineAmount() * ttRate);
        }
        else {
          applyInfo.setUseCreditAmount(applyInfo.getExamineAmount() * ddRate);
        }
        v.add(applyInfo);
      }

      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ����������ϸ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    return v;
  }

  /**
   * ���ݿͻ�ID��ѯ��ʹ�ö����ϸ
   * @param clientID
   * @return
 * @throws SQLException 
   * @throws Exception
   */
  public Collection findUseDetail(CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    Vector v = new Vector();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    CreditProductInfo cpInfo = null;
    CreditProductDAO cpDao = new CreditProductDAO();
    double ddRate = 0;
    double ttRate = 0;

    try {
      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.ZY);
      if (cpInfo != null) {
        ddRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ӫ���Ų�Ʒռ�ö�ȱ�����" + ddRate);

      cpInfo = cpDao.findByLoanTypeID(LOANConstant.LoanType.TX);
      if (cpInfo != null) {
        ttRate = cpInfo.getEngrossRate();
      }
      Log.print("��Ʊ���Ų�Ʒռ�ö�ȱ�����" + ttRate);

      conn = Database.getConnection();
      strSQL =
          "select * from loan_contractForm"
          + " where 1=1"
          + " and (( nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
          + " and nBorrowClientID="
          + creditLimitInfo.getClientID();
      if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZY){ 
          strSQL+=" and ntypeid in("+LOANConstant.LoanType.ZY+")";
      }else if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.SP){ 
          strSQL+=" and ntypeid in("+LOANConstant.LoanType.TX+")";
      }else if(creditLimitInfo.getCreditTypeID()>0 && creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZHSX){
    	  strSQL+=" and ntypeid in("+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.TX+")";    	  
      }
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();

      while (rs != null && rs.next()) {
        ContractInfo info = new ContractInfo();
        info.setContractID(rs.getLong("ID")); //��ͬ��ID
        info.setLoanID(rs.getLong("nLoanID")); //����ID
        info.setLoanTypeID(rs.getLong("nTypeID"));
        info.setContractCode(rs.getString("sContractCode")); //��ͬ���
        info.setLoanAmount(rs.getDouble("mLoanAmount")); //�����
        info.setExamineAmount(rs.getDouble("mExamineAmount")); //��׼���
        info.setCheckAmount(rs.getDouble("mCheckAmount")); //�������ֺ˶����
        info.setDiscountRate(rs.getDouble("mDiscountRate")); //��������
        info.setLoanStart(rs.getTimestamp("dtStartDate")); //������ʼ����
        info.setLoanEnd(rs.getTimestamp("dtEndDate")); //���������
        info.setIntervalNum(rs.getLong("nIntervalNum")); //����
        info.setInputDate(rs.getTimestamp("dtInputDate")); //��ͬ¼������
        info.setStatusID(rs.getLong("nStatusID")); //��ͬ״̬
        // if (info.getLoanTypeID() == LOANConstant.LoanType.TX ||info.getLoanTypeID() == LOANConstant.LoanType.ZTX) {
        if (info.getLoanTypeID() == LOANConstant.LoanType.TX){
           info.setUseCreditAmount( (info.getExamineAmount() -this.getDiscountRepayAmountByContract(conn,
              info.getContractID())) * ttRate);
        }
        else {
          info.setUseCreditAmount( (info.getExamineAmount() -this.getRepayAmountByContract(conn,
              info.getContractID())) * ddRate);
        }
        v.addElement(info);
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ��ʹ�ö����ϸ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    return v;
  }

  
  public long[] findForAutoActive(Timestamp tsToday) throws ITreasuryDAOException, SQLException {
    String strSQL = "";
    long[] id = new long[1000];
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int i = 0;
    try {
      conn = Database.getConnection();
      strSQL = "select ID from loan_creditLimit" + " where 1=1" +
          " and statusID=" + LOANConstant.CreditStatus.CHECK;

      log4j.info("sql= \n" + strSQL);
      ps = conn.prepareStatement(strSQL);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        
        id[i++] = rs.getLong("ID");
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
        e.printStackTrace();
    	throw new ITreasuryDAOException("��ѯ�������ó���", e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    return id;
  }


  public double getRepayAmount(Connection con, CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          + " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and (( b.nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and b.nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or b.nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
          + " and a.nClientID=" + creditLimitInfo.getClientID()
          + " and b.id=a.nLoanContractID"
          //�������ѭ����������㻹����
          + " and b.niscircle=2"
          + " and b.nTypeID in (1,2,6,7,8,10)";
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      strSQL +=" ) ";
      log4j.print("getRepayAmount For:" + creditLimitInfo.getClientID() + strSQL);
      ps = con.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getDouble(1);
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }

    }
    catch (SQLException e) {
       e.printStackTrace();
       throw new ITreasuryDAOException("SQL�쳣",e);
    }catch(Exception e){
    	e.printStackTrace();
    	throw new ITreasuryDAOException("��ѯ�������쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
    }
    return result;

  }
  
  public double getRepayAmountForEnddate(Connection con, CreditLimitInfo info) throws ITreasuryDAOException, SQLException {
	    double result = 0;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String strSQL = "";
	    try {
	      strSQL = "select sum(mAmount)"
	          + " from ( "
	          + " select mAmount "
	          + " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
	          + SETTConstant.TransactionStatus.CHECK
	          + " and (( b.nStatusID>="
	          + LOANConstant.ContractStatus.SAVE
	          + " and b.nStatusID<="
	          + LOANConstant.ContractStatus.BADDEBT+") "
	          + " or b.nStatusID="
	          + LOANConstant.ContractStatus.APPROVALING+")"
	          + " and a.nClientID=" + info.getClientID()
	          + " and b.id=a.nLoanContractID"
	          //�������ѭ����������㻹����
	          + " and b.niscircle=2"
	          + " and b.nTypeID in (1,2,6,7,8,10)"
	          + " and b.dtenddate > to_date('"+DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')"
	    	    + " ) ";

	      log4j.print("getRepayAmount For:" + info.getClientID() + strSQL);
	      ps = con.prepareStatement(strSQL);
	      rs = ps.executeQuery();
	      if (rs.next()) {
	        result = rs.getDouble(1);
	      }
	      if (rs != null) {
	        rs.close();
	        rs = null;
	      }
	      if (ps != null) {
	        ps.close();
	        ps = null;
	      }

	    }
	    catch (SQLException e) {
	       e.printStackTrace();
	       throw new ITreasuryDAOException("SQL�쳣",e);
	    }catch(Exception e){
	    	e.printStackTrace();
	    	throw new ITreasuryDAOException("��ѯ�쳣",e);
	    }
	    finally {
	      if (rs != null) {
	        rs.close();
	        rs = null;
	      }
	      if (ps != null) {
	        ps.close();
	        ps = null;
	      }
	    }
	    return result;

	  }

  public double getRepayAmountByContract(Connection con, long contractID) throws
    ITreasuryDAOException, SQLException {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select nvl(sum(mAmount),0) mAmount"
          + " from ( "
          + " select mAmount "
          +
          " from sett_transRepaymentloan a,loan_ContractForm b where a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and b.id=a.nLoanContractID"
          //�������ѭ����������㻹����
          + " and b.niscircle=2"
          + " and a.nLoanContractID=" + contractID
          + " and b.nTypeID in (1,2,6,7,8,10)"
          + " ) ";

      log4j.print("getRepayAmount For:" + contractID + strSQL);
      ps = con.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getDouble(1);
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }

    }
    catch (SQLException e) {
       e.printStackTrace();
       throw new ITreasuryDAOException("SQL�쳣",e);
    }catch(Exception e){
    	e.printStackTrace();
    	throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
    }
    return result;

  }

 
  public double getDiscountRepayAmount(Connection con, CreditLimitInfo creditLimitInfo) throws ITreasuryDAOException, SQLException {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select a.mAmount "
          + " from Sett_Transrepaymentdiscount a,loan_contractform b "
          + " where a.ndiscountcontractid = b.id " 
          +	" and a.nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and (( b.nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and b.nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or b.nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
          + " and a.nClientID=" + creditLimitInfo.getClientID();
      if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
          strSQL+=" and b.dtenddate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') " 
                +" and b.dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') " ;
      }
      strSQL +=" ) ";
      log4j.print("getRepayAmount For:" + creditLimitInfo.getClientID() + strSQL);
      ps = con.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getDouble(1);
      }
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }

    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }catch(Exception e){
    	e.printStackTrace();
    	throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
    }
    return result;

  }
  
  public double getDiscountRepayAmountForEnddate(Connection con, CreditLimitInfo info) throws ITreasuryDAOException, SQLException {
	double result = 0;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String strSQL = "";
	try {
	  strSQL = "select sum(mAmount)"
	      + " from ( "
	      + " select a.mAmount "
	      + " from Sett_Transrepaymentdiscount a,loan_contractform b "
	      + " where a.nStatusID>="
	      + SETTConstant.TransactionStatus.CHECK
	      + " and (( b.nStatusID>="
          + LOANConstant.ContractStatus.SAVE
          + " and b.nStatusID<="
          + LOANConstant.ContractStatus.BADDEBT+") "
          + " or b.nStatusID="
          + LOANConstant.ContractStatus.APPROVALING+")"
	      + " and a.nClientID=" + info.getClientID()
	      + " and b.dtenddate > to_date('"+DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')"
	      + " ) ";
	
	  log4j.print("getRepayAmount For:" + info.getClientID() + strSQL);
	  ps = con.prepareStatement(strSQL);
	  rs = ps.executeQuery();
	  if (rs.next()) {
	    result = rs.getDouble(1);
	  }
	}
	catch (SQLException e) {
	  e.printStackTrace();
	  throw new ITreasuryDAOException("SQL�쳣",e);
	}catch(Exception e){
		e.printStackTrace();
		throw new ITreasuryDAOException("��ѯ�쳣",e);
	}
	finally {
	  if (rs != null) {
	    rs.close();
	    rs = null;
	  }
	  if (ps != null) {
	    ps.close();
	    ps = null;
	  }
	}
	return result;

  }

  public double getDiscountRepayAmountByContract(Connection con,long contractID) throws
    ITreasuryDAOException, SQLException {
    double result = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String strSQL = "";
    try {
      strSQL = "select sum(mAmount)"
          + " from ( "
          + " select mAmount "
          + " from Sett_Transrepaymentdiscount "
          + " where nStatusID>="
          + SETTConstant.TransactionStatus.CHECK
          + " and NDISCOUNTCONTRACTID=" + contractID
          + " ) ";

      log4j.print("getRepayAmount For:" + contractID + strSQL);
      ps = con.prepareStatement(strSQL);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getDouble(1);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new ITreasuryDAOException("SQL�쳣",e);
    }catch(Exception e){
    	e.printStackTrace();
    	throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (ps != null) {
        ps.close();
        ps = null;
      }
    }
    return result;

  }

 

  /**
   * ���Ѿ����ڵ����ż�¼��Ϊ���� (���ػ�ʱ��ѯ���Ŷ���Ƿ����)
   * @throws SQLException 
   * @throws Exception
   */
  public void updateOverTime() throws ITreasuryDAOException, SQLException {
    String strSQL = "";
    Connection conn = null;
    PreparedStatement ps = null;

    strSQL = " update Loan_CreditLimit set statusid=" +
        LOANConstant.CreditStatus.OVERTIME
        + " where StatusId in (" + LOANConstant.CreditStatus.FREEZE
        + "," + LOANConstant.CreditStatus.ACTIVE
        + "," + LOANConstant.CreditStatus.SAVE+ ")"
        + " and EndDate< TO_DATE('"
        + Env.getSystemDateString() + "','yyyy-mm-dd')";
    //log4j.debug("SQL="+strSQL);
    try {
      conn = Database.getConnection();
      ps = conn.prepareStatement(strSQL);
      int i = ps.executeUpdate();
      log4j.debug(i + " lines effected!");

      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException e) {
        e.printStackTrace();
    	throw new ITreasuryDAOException("SQL�쳣",e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new ITreasuryDAOException("����ʱ�������Ź���״̬�쳣",e);
    }
    finally {
      if (ps != null) {
        ps.close();
        ps = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    }
  }
  
  /**
   * �ж��Ƿ���ж�ȿ���
   * @return
   * @throws Exception
   */
  public long[] findIsControlProduct()throws Exception{
      long[] a=null;
      int i=0;
      
      ResultSet rs = null;
      PreparedStatement ps=null;
      String strSQL = "";
      try {
            try {
                initDAO();
            } catch (ITreasuryDAOException e) {
                throw e;
            }
            strSQL="select count(credittypeid) from loan_creditproduct "
            +" where 1=1 and ISCONTROL=1 and STATUSID=1";
            ps=transConn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if(rs.next())
                a=new long[rs.getInt(1)];
            if (rs != null) {
                rs.close();
                rs = null;
            }
            strSQL="select credittypeid from loan_creditproduct "
                    +" where ISCONTROL=1 and STATUSID=1 order by id";
           this.prepareStatement(strSQL);
           rs=this.executeQuery();
           while(rs.next()){
               a[i]=rs.getLong("CREDITTYPEID");
               i++;
           }
      }catch(SQLException e){
          e.printStackTrace();
      }finally {
          finalizeDAO();
          }
      return a;
  }
  
  /**
   * ���ݿͻ���Ϣ��ѯ����������
   * @param creditLimitInfo
   * @param loanTypeId
   * @return
   * @throws ITreasuryDAOException
   */
  public double getClientLoanApplyAmount(CreditLimitInfo creditLimitInfo)
  	throws ITreasuryDAOException{
    
    double applyAmount = 0;
    String strSQL = "";

    try {
        /*-----------------init DAO --------------------*/
        try {
          initDAO();
        }
        catch (ITreasuryDAOException e) {
           throw new ITreasuryDAOException("��������ʱ�쳣",e);
        }

        try{
        	//** strSQL += "select nvl(sum(a.MEXAMINEAMOUNT),0) mApplyAmount from loan_loanForm a ";
        	strSQL += "select nvl(sum(mApplyAmount),0) mApplyAmount from( ";
        	strSQL += "select nvl(sum(a.MEXAMINEAMOUNT*b.engrossrate),0) mApplyAmount from loan_loanForm a, loan_creditproduct b ";
        	strSQL += " where a.nstatusID = " + LOANConstant.LoanStatus.APPROVALING;
        	if(creditLimitInfo.getCreditModeID() == LOANConstant.CreditMode.SINGLECORP){
        		//�����Ų��ҿͻ��Լ�����Ϣ
        		strSQL += " and a.nBorrowClientID = " + creditLimitInfo.getClientID();
        	}
        	else{
        		//�������Ų��ҿͻ��Լ���������λ���е���Ϣ
   	         	strSQL += " and a.nBorrowClientID in (select b.id from client_clientinfo b where b.levelcode like (select a.levelcode from client_clientinfo a where a.id = "+ creditLimitInfo.getClientID() +") || '%')";
        	}
	        if(creditLimitInfo.getOfficeID()>0){
		        strSQL+=" and a.nofficeid = " + creditLimitInfo.getOfficeID();
		        strSQL+=" and b.officeid = " + creditLimitInfo.getOfficeID();
		    }
		    if(creditLimitInfo.getCurrencyID()>0){
		        strSQL+=" and a.ncurrencyid = " + creditLimitInfo.getCurrencyID();
		        strSQL+=" and b.currencyid = " + creditLimitInfo.getCurrencyID();
		    }
		    
		    strSQL += " and a.nTypeID = b.loantypeid";
        	if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZY){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.ZY;
        	}
        	else if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.SP){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.TX;
        	}
        	else if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.BH){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.DB;
        	}
        	else{
        		strSQL += " and a.nTypeID in (" + LOANConstant.LoanType.ZY + ", " + LOANConstant.LoanType.TX + "," + LOANConstant.LoanType.DB + ")";
        	}
        	
		    if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
		          strSQL += " and a.dtstartdate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') ";
		          strSQL += " and a.dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') ";
		    }
		    //����ѯ��ǰ�����뽻��
		    //strSQL += " and a.id != " + loanId;
		    //�������ͷ���ͳ��
		    strSQL += " group by a.nTypeID)";
      
		    System.out.println("��ѯ����������, sql= \n"+strSQL);

		    prepareStatement(strSQL);
		    executeQuery();
		      

		    if (this.transRS.next()) {
	    	  applyAmount = this.transRS.getDouble("mApplyAmount");
		    }
      
        }
	    catch (Exception e) {
	        throw new ITreasuryDAOException("��ѯ�������������", e);
	    }

	    /*----------------finalize Dao-----------------*/
	    try {
	        finalizeDAO();
	    }
	    catch (ITreasuryDAOException e) {
	        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
	    }
	    /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      finalizeDAO();
    }
    return applyAmount;
  }
  
  /**
   * ���ݿͻ���Ϣ��ѯ�����ͬ���
   * @param creditLimitInfo
   * @param loanTypeId
   * @return
   * @throws ITreasuryDAOException
   */
  public double getClientLoanContractAmount(CreditLimitInfo creditLimitInfo)
  	throws ITreasuryDAOException{
    
    double contractAmount = 0;
    String strSQL = "";

    try {
        /*-----------------init DAO --------------------*/
        try {
          initDAO();
        }
        catch (ITreasuryDAOException e) {
           throw new ITreasuryDAOException("��������ʱ�쳣",e);
        }

        try{
            //** strSQL += "select sum(mExamineAmount) mContractAmount from loan_contractForm a ";
        	strSQL += "select nvl(sum(mContractAmount),0) mContractAmount from( ";
        	strSQL += "select nvl(sum(a.mExamineAmount*b.engrossrate),0) mContractAmount from loan_contractForm a, loan_creditproduct b ";
            strSQL += " where a.nStatusID in (";
            strSQL += LOANConstant.ContractStatus.SAVE + ", ";
            strSQL += LOANConstant.ContractStatus.SUBMIT + ", ";
            strSQL += LOANConstant.ContractStatus.CHECK + ", ";
            strSQL += LOANConstant.ContractStatus.NOTACTIVE + ", ";
            strSQL += LOANConstant.ContractStatus.ACTIVE + ", ";
            strSQL += LOANConstant.ContractStatus.EXTEND + ", ";
            strSQL += LOANConstant.ContractStatus.OVERDUE + ", ";
            strSQL += LOANConstant.ContractStatus.DELAYDEBT + ", ";
            strSQL += LOANConstant.ContractStatus.BADDEBT + ", ";
            strSQL += LOANConstant.ContractStatus.APPROVALING + ")";
        	if(creditLimitInfo.getCreditModeID() == LOANConstant.CreditMode.SINGLECORP){
        		//�����Ų��ҿͻ��Լ�����Ϣ
        		strSQL += " and a.nBorrowClientID = " + creditLimitInfo.getClientID();
        	}
        	else{
        		//�������Ų��ҿͻ��Լ���������λ���е���Ϣ
   	         	strSQL += " and a.nBorrowClientID in (select b.id from client_clientinfo b where b.levelcode like (select a.levelcode from client_clientinfo a where a.id = "+ creditLimitInfo.getClientID() +") || '%')";
        	}
	        if(creditLimitInfo.getOfficeID()>0){
		        strSQL+=" and a.nofficeid = " + creditLimitInfo.getOfficeID();
		        strSQL+=" and b.officeid = " + creditLimitInfo.getOfficeID();
		    }
		    if(creditLimitInfo.getCurrencyID()>0){
		        strSQL+=" and a.ncurrencyid = " + creditLimitInfo.getCurrencyID();
		        strSQL+=" and b.currencyid = " + creditLimitInfo.getCurrencyID();
		    }
		    
		    strSQL += " and a.nTypeID = b.loantypeid";
        	if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZY){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.ZY;
        	}
        	else if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.SP){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.TX;
        	}
        	else if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.BH){
        		strSQL += " and a.nTypeID = " + LOANConstant.LoanType.DB;
        	}
        	else{
        		strSQL += " and a.nTypeID in (" + LOANConstant.LoanType.ZY + ", " + LOANConstant.LoanType.TX + "," + LOANConstant.LoanType.DB + ")";
        	}
		    if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
		          strSQL += " and a.dtstartdate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') ";
		          strSQL += " and a.dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') ";
		    }
		    //�������ͷ���ͳ��
		    strSQL += " group by a.nTypeID)";
      
		    System.out.println("��ѯ�����ͬ���, sql= \n"+strSQL);

		    prepareStatement(strSQL);
		    executeQuery();
		      

		    if (this.transRS.next()) {
		    	contractAmount = this.transRS.getDouble("mContractAmount");
		    }
      
        }
	    catch (Exception e) {
	        throw new ITreasuryDAOException("��ѯ�������������", e);
	    }

	    /*----------------finalize Dao-----------------*/
	    try {
	        finalizeDAO();
	    }
	    catch (ITreasuryDAOException e) {
	        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
	    }
	    /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      finalizeDAO();
    }
    return contractAmount;
  }
  
  /**
   * ���ݿͻ���Ϣ��ѯ��Ӫ�����ѭ������ʱ������
   * ��������������Ĳ�ѯ
   * ��Ӫ�������ҵ���ѯsett_transRepaymentloan��
   * ���ֲ�ѯSett_Transrepaymentdiscount��
   * @param creditLimitInfo
   * @param 
   * @return
   * @throws ITreasuryDAOException
   */
  public double getClientCircleLoanAmount(CreditLimitInfo creditLimitInfo)
  	throws ITreasuryDAOException{
    
    double circleAmount = 0;
    String strSQL = "";

    try {
        /*-----------------init DAO --------------------*/
        try {
          initDAO();
        }
        catch (ITreasuryDAOException e) {
           throw new ITreasuryDAOException("��������ʱ�쳣",e);
        }

        try{
        	
        	//���ݿͻ���Ϣ������Ӫ�������ҵ��Ļ�����
        	if(creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZY || creditLimitInfo.getCreditTypeID() == LOANConstant.CreditProduct.ZHSX)
        	{
        		strSQL = "";
	            strSQL += "select nvl(sum(a.mAmount*c.engrossrate),0) mCircleAmount from sett_transRepaymentloan a,loan_ContractForm b, loan_creditproduct c ";
	            strSQL += " where a.nStatusID = " + SETTConstant.TransactionStatus.CHECK;
	            strSQL += " and b.id = a.nLoanContractID ";
	            strSQL += " and b.nStatusID in (";
	            strSQL += LOANConstant.ContractStatus.SAVE + ", ";
	            strSQL += LOANConstant.ContractStatus.SUBMIT + ", ";
	            strSQL += LOANConstant.ContractStatus.CHECK + ", ";
	            strSQL += LOANConstant.ContractStatus.NOTACTIVE + ", ";
	            strSQL += LOANConstant.ContractStatus.ACTIVE + ", ";
	            strSQL += LOANConstant.ContractStatus.EXTEND + ", ";
	            strSQL += LOANConstant.ContractStatus.OVERDUE + ", ";
	            strSQL += LOANConstant.ContractStatus.DELAYDEBT + ", ";
	            strSQL += LOANConstant.ContractStatus.BADDEBT + ", ";
	            strSQL += LOANConstant.ContractStatus.APPROVALING + ")";
	        	if(creditLimitInfo.getCreditModeID() == LOANConstant.CreditMode.SINGLECORP){
	        		//�����Ų��ҿͻ��Լ�����Ϣ
	        		strSQL += " and b.nBorrowClientID = " + creditLimitInfo.getClientID();
	        	}
	        	else{
	        		//�������Ų��ҿͻ��Լ���������λ���е���Ϣ
	   	         	strSQL += " and b.nBorrowClientID in (select b.id from client_clientinfo b where b.levelcode like (select a.levelcode from client_clientinfo a where a.id = "+ creditLimitInfo.getClientID() +") || '%')";
	        	}
	            //��ѭ������ʱ������
	            strSQL += " and b.niscircle = 2";
	            
		        if(creditLimitInfo.getOfficeID()>0){
			        strSQL+=" and b.nofficeid = " + creditLimitInfo.getOfficeID();
			        strSQL+=" and c.officeid = " + creditLimitInfo.getOfficeID();
			    }
			    if(creditLimitInfo.getCurrencyID()>0){
			        strSQL+=" and b.ncurrencyid = " + creditLimitInfo.getCurrencyID();
			        strSQL+=" and c.currencyid = " + creditLimitInfo.getCurrencyID();
			    }

			    strSQL += " and b.nTypeID = c.loantypeid";
	        	strSQL += " and b.nTypeID = " + LOANConstant.LoanType.ZY;
	            
	            if(creditLimitInfo.getStartDate()!=null && creditLimitInfo.getEndDate()!=null){
			          strSQL += " and b.dtstartdate>=to_date('"+DataFormat.formatDate(creditLimitInfo.getStartDate())+"','yyyy-mm-dd') ";
			          strSQL += " and b.dtstartdate<=to_date('"+DataFormat.formatDate(creditLimitInfo.getEndDate())+"','yyyy-mm-dd') ";
	            }
	      
			    System.out.println("��ѯ������Ӫ�������, sql= \n"+strSQL);
	
			    prepareStatement(strSQL);
			    executeQuery();
	
			    if (this.transRS.next()) {
			    	circleAmount = this.transRS.getDouble("mCircleAmount");
			    }
        	}
        }
	    catch (Exception e) {
	        throw new ITreasuryDAOException("��ѯ����������", e);
	    }

	    /*----------------finalize Dao-----------------*/
	    try {
	        finalizeDAO();
	    }
	    catch (ITreasuryDAOException e) {
	        throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
	    }
	    /*----------------end of finalize---------------*/
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ITreasuryDAOException("��ѯ�쳣",e);
    }
    finally {
      finalizeDAO();
    }
    return circleAmount;
  }
  

  public static void main(String args[]) {
    CreditLimitDAO dao = new CreditLimitDAO();
    try {long[] a=null;
      a=dao.findIsControlProduct();
      for(int i=0;i<a.length;i++){
          System.out.println(a[i]);
      }
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  /**
   * ��ѯ������Ϣ
   * @param qInfo
   * @return
   * @throws ITreasuryDAOException
 * @throws SQLException 
   */
  public Collection findByMultiOption(CreditLimitInfo qInfo) throws
      ITreasuryDAOException, SQLException {
	  Collection c = null;
      String strSQL = "";

    try {
	       /*-----------------init DAO --------------------*/
	       try {
	         initDAO();
	       }
	       catch (ITreasuryDAOException e) {
	          throw new ITreasuryDAOException("��������ʱ�쳣",e);
	       }
	       /*----------------end of init------------------*/
      	 
          try{
              strSQL += "select * from loan_creditLimit";
	          strSQL += " where 1=1";
	          //����Ʒ��
	          if(qInfo.getCreditTypeID()>0){
	        	  strSQL += " and creditTypeID=" + qInfo.getCreditTypeID();
	          }
	          else {
	        	  strSQL += " and creditTypeID in("+ LOANConstant.CreditProduct.ZY +","+ LOANConstant.CreditProduct.BH +","+ LOANConstant.CreditProduct.SP +","+ LOANConstant.CreditProduct.ZHSX +")";
	          }
	          //״̬
	          strSQL += " and STATUSID = " + LOANConstant.CreditStatus.ACTIVE;
	          
	          //���´�ID
	          if(qInfo.getOfficeID()>0){
	        	  strSQL += " and officeid = " + qInfo.getOfficeID();
	          }
	          //����ID
	          if(qInfo.getCurrencyID()>0){
	        	  strSQL += " and currencyid = " + qInfo.getCurrencyID();
	          }
	          
	          //�ͻ�ID
	          if (qInfo.getClientID() > 0) {  
		            strSQL += " and clientID = " + qInfo.getClientID();
		      }
	          //���Ŷ��
	          if (qInfo.getAmount() > 0) {
	              strSQL += " and amount <= " + qInfo.getAmount();
	          }
	          //��ʼ�ա�������
	          if(qInfo.getStartDate() != null && qInfo.getEndDate() == null){
	        	  strSQL += " and STARTDATE >= to_date('"+ DataFormat.getDateString(qInfo.getStartDate()) +"', 'yyyy-mm-dd')";
	          }
	          if(qInfo.getEndDate() != null && qInfo.getStartDate() == null){
	        	  strSQL += " and ENDDATE <= to_date('"+ DataFormat.getDateString(qInfo.getEndDate()) +"', 'yyyy-mm-dd')";
	          }
	          if(qInfo.getStartDate() != null && qInfo.getEndDate() != null){
			      strSQL += " and STARTDATE >= to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd') ";
			      strSQL += " and ENDDATE <= to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd') ";
	          }
	          
	          strSQL += " order by inputdate";

          log4j.info("sql= \n" + strSQL);

	      prepareStatement(strSQL);
	      executeQuery();
	      c = getDataEntitiesFromResultSet(CreditLimitInfo.class);
	     }
	     catch (Exception e) {
		        throw new ITreasuryDAOException("��ѯ�������ó���", e);
		 }
	
	     /*----------------finalize Dao-----------------*/
	     try {
	       finalizeDAO();
	     }
	     catch (ITreasuryDAOException e) {
	       throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
	     }
	     /*----------------end of finalize---------------*/
	  }catch (Exception e) {
	      e.printStackTrace();  
	  	throw new ITreasuryDAOException("��ѯ�������ó���", e);
	    }
	   finally {
	  	 finalizeDAO();  
	   }
	   return (c.size() > 0 ? c : null);
	}  
  
  
}

