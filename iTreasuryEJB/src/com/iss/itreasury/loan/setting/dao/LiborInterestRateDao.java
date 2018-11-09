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
import com.iss.itreasury.settlement.base.SettlementDAOException;
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
public class LiborInterestRateDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public LiborInterestRateDao()
    {
        super("Loan_LiborInterestRate");
    }

    /*
     *  
     */
    public Collection findByMultiOption(LiborInterestRateInfo qInfo)
            throws LoanDAOException
    {
        String strSelect = "";
        String strSQL = "";
        Vector v = new Vector();

        long liborInterestRateID = qInfo.getId();
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
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            //�����¼����
            if (queryPurpose == 1) //for modify
            {
                strSQL = "";
                strSelect = " select count(*) ";
                strSQL = " from Loan_LiborInterestRate a " + " where 1 = 1 "
                        + " and a.StatusID = "
                        + LOANConstant.RecordStatus.VALID;
                //+ " and a.InputUserID = " + userID;
            }

            //////////////////////��ѯ����////////////////////////////////////////////////////
            if (officeID > 0)
            {
                strSQL += " and a.OfficeID = " + officeID;
            }
            if (currencyID > 0)
            {
                strSQL += " and a.currencyID = " + currencyID;
            }
            if (liborInterestRateID > 0)
            {
                strSQL += " and a.ID = " + liborInterestRateID;
            }

            ////////////////////////////������//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
                if (orderParamString.substring(0, nIndex).equalsIgnoreCase(
                        "Loan_LiborInterestRate"))
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
            log4j.debug(strSelect + strSQL);
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
                  rs=null;
                }
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("������ѯLibor�������ñ�����������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("������ѯLibor�������ñ�����������", e);
            }
            recordCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                recordCount++;
            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //��������Ľ����
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
                    LiborInterestRateInfo liborInterestRateInfo = new LiborInterestRateInfo();
                    liborInterestRateInfo.setId(rs1.getLong("ID")); //id
                    liborInterestRateInfo.setOfficeID(rs1.getLong("OfficeID")); //���´�
                    liborInterestRateInfo.setCurrencyID(rs1
                            .getLong("CurrencyID")); //����
                    liborInterestRateInfo.setCode(rs1.getString("Code")); //Libor���
                    liborInterestRateInfo.setLiborName(rs1
                            .getString("LiborName")); //Libor����
                    liborInterestRateInfo.setIntervalNum(rs1
                            .getLong("IntervalNum")); //����
                    liborInterestRateInfo.setInputUserID(rs1
                            .getLong("InputUserID")); //¼����
                    liborInterestRateInfo.setInputDate(rs1
                            .getTimestamp("InputDate")); //¼��ʱ��
                    liborInterestRateInfo.setStatusID(rs1.getLong("StatusID")); //״̬
                    //����û�е��ֶ�
                    liborInterestRateInfo.setRecordCount(recordCount); //��¼��
                    liborInterestRateInfo.setPageCount(pageCount); //ҳ��
                    v.add(liborInterestRateInfo);
                }
                if(rs1 != null)
                {
                    rs1.close();
                    rs1=null;
                }
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("������ѯLibor�������ò�������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("������ѯLibor�������ò�������", e);
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

    //У��Libor���ʱ���Ƿ��ظ�
    public long checkLiborCode(String strLiborCode, long currencyId)
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
            strSQL = " select count(*) from " + strTableName
                    + " where Code = '" + strLiborCode + " and a.CURRENCYID = "
                    + currencyId + "' and StatusID = "
                    + LOANConstant.RecordStatus.VALID;
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
                    rs=null;
                }
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("���Libor���ʱ�Ų�������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("���Libor���ʱ�Ų�������", e);
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
        log4j.debug(":::::::::: ::::lResult::::::" + lResult);
        return lResult;
    }

    //У��Libor���������Ƿ��ظ�
    public long checkLiborName(String strLiborName, long currencyId)
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
            strSQL = " select count(*) from " + strTableName
                    + " where LiborName = '" + strLiborName
                    + " and a.CURRENCYID = " + currencyId + "' and StatusID = "
                    + LOANConstant.RecordStatus.VALID;
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
                    rs=null;
                }
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("���Libor�������Ʋ�������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("���Libor�������Ʋ�������", e);
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
        log4j.debug(":::::::::: ::::lResult::::::" + lResult);
        return lResult;
    }

    //У��Libor���������Ƿ��ظ�
    public long checkLiborName(LiborInterestRateInfo info)
            throws LoanDAOException
    {
        String strSQL = "";
        long lResult = 1;
        long ID = info.getId();
        long lResultId = -1;
        try
        {
            try
            {
                initDAO();
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            strSQL = " select id from " + strTableName + " where LiborName = '"
                    + info.getLiborName() + " and a.CURRENCYID = "
                    + info.getCurrencyID() + "' and StatusID = "
                    + LOANConstant.RecordStatus.VALID;
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    lResultId = rs.getLong("id");
                    if (lResultId > 0 && lResultId != ID)
                    {
                        lResult = -1;
                    }
                    System.out.println("id=(" + ID + ") select id=("
                            + lResultId + ")");
                }
                if(rs != null)
                {
                    rs.close();
                    rs=null;
                    
                }
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("���Libor�������Ʋ�������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("���Libor�������Ʋ�������", e);
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
        log4j.debug(":::::::::: ::::lResult::::::" + lResult);
        return lResult;
    }

    /**
     * findMaxInterestRateCode ��������������ʱ�� ����InterestRateType���ݱ� ��ѯ��Ӧ��¼
     * 
     * @param lCurrencyID
     *            ���ֱ�ʾ
     * 
     * @return String ����������ʱ��
     * @throws
     */

    public String findMaxInterestRateCode(long lCurrencyID,long lOfficeID)
            throws LoanDAOException
    {
        long ltemp = 1;
        String MaxCode = "";
        StringBuffer sb = new StringBuffer();
        String strSQL = "";
        //ResultSet rs = null;
        try
        {
            try
            {
                this.initDAO();
            } catch (ITreasuryDAOException e)
            {
                // TODO �Զ����� catch ��
                e.printStackTrace();
                throw new LoanDAOException(e);
            }

           /* strSQL = " select id from ( select id from Loan_LiborInterestRate where CURRENCYID="
                    + lCurrencyID
                    + " minus  select to_number(CODE) scode from Loan_LiborInterestRate   where CURRENCYID="
                    + lCurrencyID + ")";
            log4j.debug(strSQL);*/
            try
            {
             /*   prepareStatement(strSQL);
                transRS = executeQuery();

                if (transRS.next())
                { // �����������scode��ͨ�����淽sql��ȡ��������scode�����û��������scode�����ݿ��ѯ�������0��
                    ltemp = transRS.getLong(1);
                } else
                //ȡ���ֵ��һ
                {*/
                    sb.append(" SELECT max(nvl(CODE,'001'))  ");
                    sb.append(" FROM Loan_LiborInterestRate where CURRENCYID="
                            + lCurrencyID+" and OFFICEID="+lOfficeID);
                    prepareStatement(sb.toString());
                    log4j.debug("sql is: " + sb.toString());

                    transRS = executeQuery();
                    if (transRS.next())
                    {
                        MaxCode = transRS.getString(1);
                       
                    } else
                    {
                        MaxCode = "0";
                    }
                    MaxCode = MaxCode != null ? MaxCode : "0";
                   
                    //Common.log(" MaxCode : " + MaxCode );
                    ltemp = Long.parseLong(MaxCode);
                  
                    ltemp = ltemp + 1;
                    log4j.info("ltemp is " + ltemp);
            //    }//end else
            } catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("����Libor���ʱ�Ų�������", e);
            } catch (SQLException e)
            {
                throw new LoanDAOException("����Libor���ʱ�Ų�������", e);
            }

            MaxCode = String.valueOf(ltemp);
           
            for (int j = MaxCode.length(); j < 3; j++)
            {
                MaxCode = "0" + MaxCode;
            }
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                // TODO �Զ����� catch ��
                e.printStackTrace();
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
        log4j.debug(":::::::::: ::::lResult::::::" + MaxCode);

        return MaxCode;
    }

    public static void main(String[] args)
    {
        //
       /*LiborInterestRateDao dao = new LiborInterestRateDao();
        String strCode = "";
        try 
        {
            strCode = dao.findMaxInterestRateCode(2,2);
        } catch (LoanDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("################strCode = "+strCode);
  */  }

}