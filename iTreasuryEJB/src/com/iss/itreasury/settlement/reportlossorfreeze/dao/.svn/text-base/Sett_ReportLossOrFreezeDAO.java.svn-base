package com.iss.itreasury.settlement.reportlossorfreeze.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreezeBean;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author jinchen
 * @version Date of Creation 2004-10-24
 */
public class Sett_ReportLossOrFreezeDAO extends SettlementDAO
{
    private Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public Sett_ReportLossOrFreezeDAO()
    {
        super("Sett_ReportLossOrFreeze");
    }
    
    public Sett_ReportLossOrFreezeDAO(Connection conn)
    {
        super("Sett_ReportLossOrFreeze",conn);
    }
    
    /**
     * @param tableName 
     * @param conn
     */
    public Sett_ReportLossOrFreezeDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
        // TODO Auto-generated constructor stub
    }

    /**
     * 方法说明：根据状态查找
     * 
     * @param StatusID
     *            0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
     * @return Collection
     * @throws IException
     */
    public Collection findByStatus(long statusID) throws Exception
    {
        return null;
    }
    
    /**
     * added by mzh_fu 2007/05/09
     * @param id
     * @return
     * @throws ITreasuryDAOException
     */
    public ReportLossOrFreezeInfo findById(long id)
			throws Exception {
		ReportLossOrFreezeInfo reportLossOrFreezeInfo = new ReportLossOrFreezeInfo();
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM Sett_ReportLossOrFreeze ");
			buffer.append("\n WHERE id = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			
			if(transRS.next()){
				reportLossOrFreezeInfo.setId(transRS.getLong("ID"));
				reportLossOrFreezeInfo.setTransNo(transRS.getString("TRANSNO"));
				reportLossOrFreezeInfo.setTransActionType(transRS.getLong("TRANSACTIONTYPE"));
				reportLossOrFreezeInfo.setClientId(transRS.getLong("CLIENTID"));
				reportLossOrFreezeInfo.setAccountId(transRS.getLong("ACCOUNTID"));
				reportLossOrFreezeInfo.setOldDepositNo(transRS.getString("OLDDEPOSITNO"));
				reportLossOrFreezeInfo.setNewDepositNo(transRS.getString("NEWDEPOSITNO"));
				reportLossOrFreezeInfo.setEffectiveDate(transRS.getTimestamp("EFFECTIVEDATE"));
				reportLossOrFreezeInfo.setFreezeTerm(transRS.getLong("FREEZETERM"));
				reportLossOrFreezeInfo.setFreezeEndDate(transRS.getTimestamp("FREEZEENDDATE"));
				reportLossOrFreezeInfo.setFreezeAmount(transRS.getDouble("FREEZEAMOUNT"));
				reportLossOrFreezeInfo.setSubAccountOldStatus(transRS.getLong("SUBACCOUNTOLDSTATUS"));
				reportLossOrFreezeInfo.setSubAccountNewStatus(transRS.getLong("SUBACCOUNTNEWSTATUS"));
				reportLossOrFreezeInfo.setExecuteGovernment(transRS.getString("EXECUTEGOVERNMENT"));
				reportLossOrFreezeInfo.setApplyCompany(transRS.getString("APPLYCOMPANY"));
				reportLossOrFreezeInfo.setLawWritNo(transRS.getString("LAWWRITNO"));
				reportLossOrFreezeInfo.setAbstract(transRS.getString("ABSTRACT"));
				reportLossOrFreezeInfo.setExecuteDate(transRS.getTimestamp("EXECUTEDATE"));
				reportLossOrFreezeInfo.setInputUserId(transRS.getLong("INPUTUSERID"));
				reportLossOrFreezeInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
				reportLossOrFreezeInfo.setCheckUserId(transRS.getLong("CHECKUSERID"));
				reportLossOrFreezeInfo.setCheckDate(transRS.getTimestamp("CHECKDATE"));
				reportLossOrFreezeInfo.setModifyDate(transRS.getTimestamp("MODIFYDATE"));
				reportLossOrFreezeInfo.setStatus(transRS.getLong("STATUS"));
				reportLossOrFreezeInfo.setOfficeId(transRS.getLong("OFFICEID"));
				reportLossOrFreezeInfo.setCurrencyId(transRS.getLong("CURRENCYID"));
				reportLossOrFreezeInfo.setFreezeType(transRS.getLong("FREEZETYPE"));
				
			}
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			finalizeDAO();
		}

		return reportLossOrFreezeInfo;
	}
    
    /**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transCurrentDepositID
	 * @return Timestamp
	 * @throws SettlementDAOException
	 * @throws IException
	 */
    public Timestamp findTouchDate(long transCurrentDepositID)
            throws SettlementDAOException
    {

        Timestamp res = null;
        try
        {
            this.initDAO();
            StringBuffer strSQLBuffer = new StringBuffer();
            strSQLBuffer.append("SELECT MODIFYDATE FROM \n");
            strSQLBuffer.append(strTableName + " \n");
            strSQLBuffer.append("WHERE ID = ? \n");
            String strSQL = strSQLBuffer.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            transPS.setLong(1, transCurrentDepositID);
            transRS = transPS.executeQuery();
            if (transRS.next())
            {
                res = transRS.getTimestamp(1);
            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return res;
    }

    /**
     * 方法说明：根据查询条件组合，查询出符合的记录 Method findByConditions.
     * 
     * @param sett_TransCurrentDepositInfo
     * @param orderBySequence
     * @return Collection
     * @throws SettlementDAOException
     */
    public Collection findByConditions(ReportLossOrFreezeQueryInfo qInfo)
            throws SettlementDAOException
    {

        String strCheckDate = "";
        String strInputDate = "";
        
        long lTransactionType = -1;
        long lInputUserId = -1;
        long lCheckUserId = -1;
        long lStatus = -1;
        lCheckUserId = qInfo.getCheckUserId();
        lInputUserId = qInfo.getInputUserId();
        lTransactionType = qInfo.getTransActionType();
        lStatus = qInfo.getStatus();
        Vector v = new Vector();

        try
        {
            strCheckDate = DataFormat.getDateString(qInfo.getCheckDate());
            strInputDate = DataFormat.getDateString(qInfo.getInputDate());
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append("SELECT re.*,c.SCODE clientcode,u.sname checkUserName, sa.SACCOUNTNO accountNo FROM \n");
            sbSQL
                    .append(" sett_reportlossorfreeze re ,sett_account sa, client c, userinfo u"
                            + " \n");
            sbSQL.append("WHERE re.TransactionType=" + lTransactionType);
            /* if(lTransactionType == SETTConstant.TransactionType.REPORTLOSS || lTransactionType == SETTConstant.TransactionType.REPORTLOSS
             || lTransactionType == SETTConstant.TransactionType.CHANGECERTIFICATE)*/
            if (lCheckUserId > 0)
            {
                sbSQL.append(" and re.CHECKUSERID=" + lCheckUserId);
            }
            /* else if(lTransactionType == SETTConstant.TransactionType.FREEZE || 
             lTransactionType == SETTConstant.TransactionType.DEFREEZE)
             {
             if(lStatus == SETTConstant.TransactionStatus.SAVE)
             */
            else if (lInputUserId > 0)
            {
                sbSQL.append(" and re.INPUTUSERID=" + lInputUserId);
            }
            /*  else if(lStatus == SETTConstant.TransactionStatus.CHECK)
             {
             sbSQL.append(" and re.CHECKUSERID=" + lCheckUserId);
             }
             */
            sbSQL.append(" and re.status=" + lStatus);
            if (lCheckUserId > 0)
            {
                
            
            sbSQL.append(" and re.CHECKDATE=to_date('" + strCheckDate
                    + "','yyyy-mm-dd') ");
            }
            else if (lInputUserId > 0)
            {
                sbSQL.append(" and re.INPUTDATE=to_date('" + strInputDate
                        + "','yyyy-mm-dd') ");
            }
            sbSQL
                    .append(" and re.clientid=c.id(+) and re.checkuserid=u.id(+) and re.accountid=sa.id(+)");
           
            String strDesc = qInfo.getDesc() == 1 ? " desc " : "";

            switch ((int) qInfo.getOrderField())
            {
            case ReportLossOrFreezeBean.OrderBy_TransNo:
                sbSQL.append(" \n order by TRANSNO" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_ClientCode:
                sbSQL.append(" \n order by clientcode" + strDesc);
                break;

            case ReportLossOrFreezeBean.OrderBy_AccountNo:
                sbSQL.append(" \n order by accountNo" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_FreezeAmount:
                sbSQL.append(" \n order by FREEZEAMOUNT" + strDesc);
            	break;
            case ReportLossOrFreezeBean.OrderBy_OldDepositNo:
                sbSQL.append(" \n order by OLDDEPOSITNO" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_EffectiveDate:
                sbSQL.append(" \n order by EFFECTIVEDATE" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_ExecuteDate:
                sbSQL.append(" \n order by EXECUTEDATE" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_CheckUserName:
                sbSQL.append(" \n order by checkUserName" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_ExcuteGovernment:
                sbSQL.append(" \n order by EXECUTEGOVERNMENT" + strDesc);
                break;
            case ReportLossOrFreezeBean.OrderBy_FreezeType:
                sbSQL.append(" \n order by freezetype" + strDesc);
                break;
            default:
                sbSQL.append(" \n order by TRANSNO" + strDesc);
                break;
            }

            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
                info.setId(transRS.getLong("ID"));
                info.setTransNo(transRS.getString("TRANSNO"));
                info.setAbstract(transRS.getString("ABSTRACT"));
                info.setAccountId(transRS.getLong("ACCOUNTID"));
                info.setApplyCompany(transRS.getString("APPLYCOMPANY"));
                info.setCheckDate(transRS.getTimestamp("CHECKDATE"));
                info.setCheckUserId(transRS.getLong("CHECKUSERID"));
                info.setClientId(transRS.getLong("CLIENTID"));
                info.setCurrencyId(transRS.getLong("CLIENTID"));
                info.setEffectiveDate(transRS.getTimestamp("EFFECTIVEDATE"));
                info.setExecuteDate(transRS.getTimestamp("EXECUTEDATE"));
                info.setExecuteGovernment(transRS
                        .getString("EXECUTEGOVERNMENT"));
                info.setFreezeAmount(transRS.getDouble("FREEZEAMOUNT"));
                info.setFreezeEndDate(transRS.getTimestamp("FREEZEENDDATE"));
                info.setFreezeTerm(transRS.getLong("FREEZETERM"));
                info.setInputDate(transRS.getTimestamp("INPUTDATE"));
                info.setInputUserId(transRS.getLong("INPUTUSERID"));
                info.setLawWritNo(transRS.getString("LAWWRITNO"));
                info.setModifyDate(transRS.getTimestamp("MODIFYDATE"));
                info.setNewDepositNo(transRS.getString("NEWDEPOSITNO"));
                info.setOfficeId(transRS.getLong("OFFICEID"));
                info.setOldDepositNo(transRS.getString("OLDDEPOSITNO"));
                info.setStatus(transRS.getLong("STATUS"));
                info.setSubAccountNewStatus(transRS
                        .getLong("SUBACCOUNTNEWSTATUS"));
                info.setSubAccountOldStatus(transRS
                        .getLong("SUBACCOUNTOLDSTATUS"));
                info.setTransActionType(transRS.getLong("TRANSACTIONTYPE"));
                info.setCheckUserName(transRS.getString("checkUserName"));
                info.setClientCode(transRS.getString("clientcode"));
                info.setAccountNo(transRS.getString("accountNo"));
                info.setFreezeType(transRS.getLong("FREEZETYPE"));
                v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return v.size() > 0 ? v : null;

    }

    /**
     * 方法说明：根据查询条件匹配 Method match.
     * 
     * @param Sett_TransCurrentDepositInfo
     *            info
     * @return Sett_TransCurrentDepositInfo
     */
    public ReportLossOrFreezeInfo match(long transactionType,
            ReportLossOrFreezeInfo qInfo) throws SettlementDAOException
    {
        Vector v = new Vector();
        ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(strTableName + " \n");
            sbSQL.append("WHERE TransactionType="+qInfo.getTransActionType());
            sbSQL.append(" and INPUTUSERID <>" + qInfo.getCheckUserId());
            sbSQL.append(" and status=" + qInfo.getStatus());
            sbSQL.append(" and EFFECTIVEDATE=to_date('" + DataFormat.getDateString(qInfo.getEffectiveDate())
                    + "','yyyy-mm-dd') ");
            if(transactionType == SETTConstant.TransactionType.FREEZE)
            {
                sbSQL.append(" and FREEZEENDDATE=to_date('" + DataFormat.getDateString(qInfo.getFreezeEndDate())
                    + "','yyyy-mm-dd') ");
            }
            sbSQL.append(" and ACCOUNTID="+qInfo.getAccountId());
            sbSQL.append(" and CLIENTID=" + qInfo.getClientId());
            if(qInfo.getOldDepositNo()!=null && qInfo.getOldDepositNo().length()>0)
            {
                sbSQL.append(" and OLDDEPOSITNO='"+qInfo.getOldDepositNo()+"'");
            }
            else 
            {
                sbSQL.append(" and OLDDEPOSITNO is null ");
            }
            if(qInfo.getFreezeTerm()>0)
            {
                sbSQL.append(" and FREEZETERM="+qInfo.getFreezeTerm());
            }
            else
            {
                sbSQL.append(" and (FREEZETERM = 0 or  FREEZETERM is null) ");
            }
            sbSQL.append(" and FREEZETYPE="+qInfo.getFreezeType());
            sbSQL.append(" and FREEZEAMOUNT="+qInfo.getFreezeAmount());
            
            sbSQL.append(" order by transno desc ");
            //sbSQL
            String strSQL = sbSQL.toString();
            System.out.println("################sql===="+strSQL);
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            { 
                info.setId(transRS.getLong("ID"));
                info.setTransNo(transRS.getString("TRANSNO"));
                info.setAbstract(transRS.getString("ABSTRACT"));
                info.setAccountId(transRS.getLong("ACCOUNTID"));
                info.setApplyCompany(transRS.getString("APPLYCOMPANY"));
                info.setCheckDate(transRS.getTimestamp("CHECKDATE"));
                info.setCheckUserId(transRS.getLong("CHECKUSERID"));
                info.setClientId(transRS.getLong("CLIENTID"));
                info.setCurrencyId(transRS.getLong("CLIENTID"));
                info.setEffectiveDate(transRS.getTimestamp("EFFECTIVEDATE"));
                info.setExecuteDate(transRS.getTimestamp("EXECUTEDATE"));
                info.setExecuteGovernment(transRS
                        .getString("EXECUTEGOVERNMENT"));
                info.setFreezeAmount(transRS.getDouble("FREEZEAMOUNT"));
                info.setFreezeEndDate(transRS.getTimestamp("FREEZEENDDATE"));
                info.setFreezeTerm(transRS.getLong("FREEZETERM"));
                info.setInputDate(transRS.getTimestamp("INPUTDATE"));
                info.setInputUserId(transRS.getLong("INPUTUSERID"));
                info.setLawWritNo(transRS.getString("LAWWRITNO"));
                info.setModifyDate(transRS.getTimestamp("MODIFYDATE"));
                info.setNewDepositNo(transRS.getString("NEWDEPOSITNO"));
                info.setOfficeId(transRS.getLong("OFFICEID"));
                info.setOldDepositNo(transRS.getString("OLDDEPOSITNO"));
                info.setStatus(transRS.getLong("STATUS"));
                info.setSubAccountNewStatus(transRS.getLong("SUBACCOUNTNEWSTATUS"));
                info.setSubAccountOldStatus(transRS.getLong("SUBACCOUNTOLDSTATUS"));
                info.setTransActionType(transRS.getLong("TRANSACTIONTYPE")); 
                info.setFreezeType(transRS.getLong("FREEZETYPE"));
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return info;
    }
   
    public boolean isValidAccount(long lSubAccountId, long lTransActionType,
            long lTransactionOperate) throws SettlementDAOException
    {
        boolean bReturn = false;
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select NSTATUSID from sett_subaccount ");
            sbSQL.append(" where id=" + lSubAccountId);

            log4j.print("SQL=:\t" + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());

            transRS = transPS.executeQuery();

            String strTemp = null;
            //SETTConstant t = null;
            if (transRS != null && transRS.next())
            {
                lTemp = transRS.getLong("NSTATUSID");
                System.out.println("----------------------------lTemp=:"+lTemp);
                if (lTransActionType == SETTConstant.TransactionType.REPORTLOSS)
                {

                    if (lTransactionOperate == SETTConstant.TransactionOperate.SAVE
                    		|| lTransactionOperate == SETTConstant.TransactionOperate.CHECK
                    		|| lTransactionOperate == SETTConstant.TransactionOperate.DELETE)
                    {
                        if (lTemp > 0
                                && lTemp == SETTConstant.SubAccountStatus.NORMAL)
                        {
                            bReturn = true;
                        }
                    } 
                    else
                    {
                        if (lTemp > 0
                                && (lTemp == SETTConstant.SubAccountStatus.REPORTLOSS))
                        {
                            bReturn = true;
                        }
                    }
                } else if (lTransActionType == SETTConstant.TransactionType.REPORTFIND)
                {
                    if (lTransactionOperate == SETTConstant.TransactionOperate.SAVE)
                    {
                        if (lTemp > 0
                                && (lTemp == SETTConstant.SubAccountStatus.REPORTLOSS))
                        {
                            bReturn = true;
                        }
                    } else if (lTransactionOperate == SETTConstant.TransactionOperate.CHECK)
                    {
                        if (lTemp > 0
                                && lTemp == SETTConstant.SubAccountStatus.REPORTLOSS)
                        {
                            bReturn = true;
                        }

                    } else if (lTransactionOperate == SETTConstant.TransactionOperate.DELETE)
                    {
                        if (lTemp > 0
                                && lTemp == SETTConstant.SubAccountStatus.REPORTLOSS)
                        {
                            bReturn = true;
                        }
                    } else
                    {
                        if (lTemp > 0
                                && lTemp == SETTConstant.SubAccountStatus.NORMAL)
                        {
                            bReturn = true;
                        }
                    }
                } 
                else if (lTransActionType == SETTConstant.TransactionType.FREEZE)
                {
                    if (lTransactionOperate == SETTConstant.TransactionOperate.DELETE 
                    		|| lTransactionOperate == SETTConstant.TransactionOperate.CHECK)
                    {
                    	
                        if (lTemp > 0 && lTemp == SETTConstant.SubAccountStatus.NORMAL)
                        {
                            bReturn = true;
                        }
                    }
                    else
                    {
                        if (lTemp > 0
                                && (lTemp == SETTConstant.SubAccountStatus.PARTFREEZE
                                        || lTemp == SETTConstant.SubAccountStatus.ONLYPAYFREEZE || lTemp == SETTConstant.SubAccountStatus.ALLFREEZE))
                        {
                            bReturn = true;
                        }
                    }
                } else if (lTransActionType == SETTConstant.TransactionType.DEFREEZE)
                {
                	if (lTransactionOperate == SETTConstant.TransactionOperate.DELETE 
                    		|| lTransactionOperate == SETTConstant.TransactionOperate.CHECK
                    		)
                    {
                        if (lTemp > 0 && (lTemp == SETTConstant.SubAccountStatus.PARTFREEZE
                        		          || lTemp == SETTConstant.SubAccountStatus.ONLYPAYFREEZE || lTemp == SETTConstant.SubAccountStatus.ALLFREEZE))
                        {
                            bReturn = true;
                        }
                    } 
                    else
                    {
                    	if (lTemp > 0 && lTemp == SETTConstant.SubAccountStatus.NORMAL)
                        {
                            bReturn = true;
                        }
                    }
                } else if (lTransActionType == SETTConstant.TransactionType.CHANGECERTIFICATE)
                {
                    if (lTransactionOperate == SETTConstant.TransactionOperate.SAVE)
                    {
                        if (lTemp > 0
                                && (lTemp == SETTConstant.SubAccountStatus.REPORTLOSS))
                        {
                            bReturn = true;
                        }
                    } else
                    {
                        if (lTemp > 0
                                && lTemp == SETTConstant.SubAccountStatus.NORMAL)
                        {
                            bReturn = true;
                        }
                    }
                }

            }

            this.finalizeDAO();

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new SettlementDAOException("发生其他异常", e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SettlementDAOException(e);
            }
        }

        return bReturn;
    }

    /**
     * 数据库更新操作操作
     * 
     * @param id
     * @param statusID
     *            需要更新到的状态
     * @return
     * @throws ITreasuryDAOException
     */
    public void updateStatus(long id, long statusID, Timestamp dtUpdate)
            throws ITreasuryDAOException
    {
        try
        {
        	System.out.println("-----------------------调用updateStatus方法-------------------------------");
        	System.out.println("-----------------------id====:-------------------------------"+id);
        	System.out.println("-----------------------statusID====:-------------------------------"+statusID);
        	System.out.println("-----------------------dtUpdate====:-------------------------------"+dtUpdate);
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE \n");
            buffer.append(strTableName);
            buffer.append(" SET STATUS = " + statusID);
            buffer.append(" , MODIFYDATE =?");
            //TBD: maybe need add update execute date
            buffer.append("\n  WHERE ID = " + id);
          
            String strSQL = buffer.toString();
            System.out.println("strSQL====:"+strSQL);
            log.debug(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            transPS.setTimestamp(1, dtUpdate);
            transPS.executeUpdate();
            
            finalizeDAO();
            System.out.println("-----------------------调用updateStatus方法成功-------------------------------");
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("状态更新异常", e);
        } catch (SQLException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            throw new ITreasuryDAOException("数据库操作异常", e);
        } finally
        {
            finalizeDAO();
        }
    }
    
    /**
     * 数据库更新操作操作
     * 
     * @param id
     * @param statusID
     *            需要更新到的状态
     * @return
     * @throws ITreasuryDAOException
     */
    public void updateCheckDate(long id)
            throws ITreasuryDAOException
    {
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE \n");
            buffer.append(strTableName);
            buffer.append(" SET checkdate=null ");
           
            //TBD: maybe need add update execute date
            buffer.append("\n  WHERE ID = " + id);
            String strSQL = buffer.toString();
            log.debug(strSQL);
            transPS = transConn.prepareStatement(strSQL);
           
            transPS.executeUpdate();
            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("状态更新异常", e);
        } catch (SQLException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            throw new ITreasuryDAOException("数据库操作异常", e);
        } finally
        {
            finalizeDAO();
        }
    }

    /**
     * 方法说明：根据ID查找修改时间
     * 
     * @param transCurrentDepositID
     * @return Timestamp
     * @throws SettlementDAOException
     * @throws IException
     */
    public Collection findByConditions(long lUserId, String strDate,
            long lStatus, long lTransactionType) throws SettlementDAOException
    {

        //		    String strDate = "";
        //		    long lCheckId = -1;
        //		    long lTransactionType = -1;
        Vector v = new Vector();

        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(strTableName + " \n");
            sbSQL.append("WHERE TransactionType=" + lTransactionType);
            sbSQL.append(" and CHECKUSERID=" + lUserId);
            sbSQL.append(" and status=" + lStatus);
            sbSQL.append(" and CHECKDATE=to_date('" + strDate
                    + "','yyyy-mm-dd') ");
            sbSQL.append(" order by transno desc ");
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
                info.setId(transRS.getLong("ID"));
                info.setTransNo(transRS.getString("TRANSNO"));
                info.setAbstract(transRS.getString("ABSTRACT"));
                info.setAccountId(transRS.getLong("ACCOUNTID"));
                info.setApplyCompany(transRS.getString("APPLYCOMPANY"));
                info.setCheckDate(transRS.getTimestamp("CHECKDATE"));
                info.setCheckUserId(transRS.getLong("CHECKUSERID"));
                info.setClientId(transRS.getLong("CLIENTID"));
                info.setCurrencyId(transRS.getLong("CLIENTID"));
                info.setEffectiveDate(transRS.getTimestamp("EFFECTIVEDATE"));
                info.setExecuteDate(transRS.getTimestamp("EXECUTEDATE"));
                info.setExecuteGovernment(transRS
                        .getString("EXECUTEGOVERNMENT"));
                info.setFreezeAmount(transRS.getDouble("FREEZEAMOUNT"));
                info.setFreezeEndDate(transRS.getTimestamp("FREEZEENDDATE"));
                info.setFreezeTerm(transRS.getLong("FREEZETERM"));
                info.setInputDate(transRS.getTimestamp("INPUTDATE"));
                info.setInputUserId(transRS.getLong("INPUTUSERID"));
                info.setLawWritNo(transRS.getString("LAWWRITNO"));
                info.setModifyDate(transRS.getTimestamp("MODIFYDATE"));
                info.setNewDepositNo(transRS.getString("NEWDEPOSITNO"));
                info.setOfficeId(transRS.getLong("OFFICEID"));
                info.setOldDepositNo(transRS.getString("OLDDEPOSITNO"));
                info.setStatus(transRS.getLong("STATUS"));
                info.setSubAccountNewStatus(transRS
                        .getLong("SUBACCOUNTNEWSTATUS"));
                info.setSubAccountOldStatus(transRS
                        .getLong("SUBACCOUNTOLDSTATUS"));
                info.setTransActionType(transRS.getLong("TRANSACTIONTYPE")); 
                info.setFreezeType(transRS.getLong("FREEZETYPE"));
                v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return v.size() > 0 ? v : null;
    }
	/**
	 * 设置定期交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private TransFixedOpenInfo getOpenDeposit(
		TransFixedOpenInfo info,
		ResultSet rs)
		throws Exception {
		info = new TransFixedOpenInfo();
		try {
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setClientID(rs.getLong("nClientID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setDepositNo(rs.getString("sDepositNo"));
			info.setCertificationBankID(rs.getLong("nCertificationBankID"));
			info.setRate(rs.getDouble("mRate"));
			info.setStartDate(rs.getTimestamp("dtStart"));
			info.setEndDate(rs.getTimestamp("dtEnd"));
			info.setDepositTerm(rs.getLong("nDepositTerm"));
			info.setInterestPlanID(rs.getLong("nInterestPlanID"));
			info.setNoticeDay(rs.getLong("nNoticeDay"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setConsignVoucherNo(rs.getString("sConsignVoucherNo"));
			info.setConsignPassword(rs.getString("sConsignPassword"));
			info.setBillNo(rs.getString("sBillNo"));
			info.setBillTypeID(rs.getLong("nBillTypeID"));
			info.setBillBankID(rs.getLong("nBillBankID"));
			info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtBankNo(rs.getString("sExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	public TransFixedOpenInfo findDepositByNo(String depositno) throws SettlementDAOException
    {
	    TransFixedOpenInfo info = new TransFixedOpenInfo();
        try
        { 
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_TransOpenFixedDeposit " + " \n");
            sbSQL.append(" WHERE SDEPOSITNO=? ");
            
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            transPS.setString(1, depositno);
            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                info = getOpenDeposit(info, transRS);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return info;
    }
	public TransFixedOpenInfo findDepositByCondition(ReportLossOrFreezeInfo qInfo) throws SettlementDAOException
    {
	    TransFixedOpenInfo info = new TransFixedOpenInfo();
        try
        { 
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_subaccount " + " \n");
            sbSQL.append(" WHERE NACCOUNTID="+qInfo.getAccountId());
            if(qInfo.getNewDepositNo()!=null && qInfo.getNewDepositNo().length()>0 )
            {
                sbSQL.append(" and AF_SDEPOSITNO='"+qInfo.getNewDepositNo()+"'");
            }
            else
            {
                sbSQL.append(" and AF_SDEPOSITNO is null ");
            }
            
            
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            
            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            { 
                
                info.setTransNo(qInfo.getTransNo());
                info.setClientID(qInfo.getClientId());
                info.setDepositNo(qInfo.getNewDepositNo());
                info.setInputUserID(qInfo.getInputUserId());
                info.setCheckUserID(qInfo.getCheckUserId());
                info.setAccountID(transRS.getLong("NACCOUNTID"));
                info.setInterestStartDate(transRS.getTimestamp("AF_DTSTART")); 
                info.setAmount(transRS.getDouble("MOPENAMOUNT"));
                info.setDepositTerm(transRS.getLong("AF_NDEPOSITTERM"));
                info.setRate(transRS.getDouble("AF_MRATE"));
                info.setAbstract(qInfo.getAbstract());
                info.setEndDate(transRS.getTimestamp("AF_DTEND"));
                
            } 

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return info;
    }
	

    /**
     * @param subAccountID
     * @return
     */
    public double findCapitalLimitAmountById(long subAccountID)
    {
        double capitalLimitAmount = 0;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_subaccount " + " \n");
            sbSQL.append("WHERE id="+subAccountID);
          
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                capitalLimitAmount = transRS.getDouble("AC_MCAPITALLIMITAMOUNT");
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           //throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            //throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            //throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                // TODO 自动生成 catch 块
                e3.printStackTrace();
                //throw new SettlementDAOException(e3);
            }
        }
        return capitalLimitAmount;
    
    }
    /**
     * @param subAccountID
     * @return
     * @throws SettlementDAOException
     */
    public long findSubAccountStatus(long lAccountId, String strDepositNo) throws SettlementDAOException
    {
        long lStatus = -1;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_subaccount " + " \n");
            sbSQL.append(" WHERE NACCOUNTID="+lAccountId);
            if(strDepositNo!=null && strDepositNo.length()>0 )
            {
                sbSQL.append(" and AF_SDEPOSITNO='"+strDepositNo+"'");
            }
            else
            {
                sbSQL.append(" and AF_SDEPOSITNO is null ");
            }
            sbSQL.append(" order by id desc ");
          
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                lStatus = transRS.getLong("NSTATUSID");
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return lStatus;
    
    }

    /**
     * @param accountId
     * @param newDepositNo
     * @return
     * @throws SettlementDAOException
     */
    public long findSubAccountId(long lAccountId, String strDepositNo) throws SettlementDAOException
    {
        long lSubStatusId = -1;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_subaccount " + " \n");
            sbSQL.append(" WHERE NACCOUNTID="+lAccountId);
            if(strDepositNo!=null && strDepositNo.length()>0 )
            {
                sbSQL.append(" and AF_SDEPOSITNO='"+strDepositNo+"'");
            }
            else
            {
                sbSQL.append(" and AF_SDEPOSITNO is null ");
            }
            sbSQL.append(" order by id desc ");
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                lSubStatusId = transRS.getLong("id");
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return lSubStatusId;
    }

    /**
     * @param transType
     * @param accountId
     * @param strDepositNo
     * @return
     * @throws SettlementDAOException
     */
    public ReportLossOrFreezeInfo findRecordLossOrFreeze(long transType, long accountId, String strDepositNo) throws SettlementDAOException
    {
        ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_reportlossorfreeze " + " \n");
            sbSQL.append(" WHERE ACCOUNTID="+accountId);
            if(strDepositNo!=null && strDepositNo.length()>0 )
            {
                sbSQL.append(" and OLDDEPOSITNO='"+strDepositNo+"'");
            }
            else
            {
                sbSQL.append(" and OLDDEPOSITNO is null ");
            }
            if(transType == SETTConstant.TransactionType.CHANGECERTIFICATE)
            {
                sbSQL.append(" and TRANSACTIONTYPE="+SETTConstant.TransactionType.REPORTLOSS);
            }
            else if(transType == SETTConstant.TransactionType.DEFREEZE)
            {
                sbSQL.append(" and TRANSACTIONTYPE="+SETTConstant.TransactionType.FREEZE);
            } 
            
            
            sbSQL.append(" and STATUS="+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append(" order by id desc ");
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                info.setId(transRS.getLong("id"));
                info.setEffectiveDate(transRS.getTimestamp("EFFECTIVEDATE"));
                info.setFreezeAmount(transRS.getDouble("FREEZEAMOUNT"));
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return info;
    }

    /**
     * @param info
     * @return
     * @throws SettlementDAOException
     */
    public boolean checkTransValid(ReportLossOrFreezeInfo info) throws SettlementDAOException
    {
        boolean bReturn = false;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM \n");
            sbSQL.append(" sett_vtransaction " + " \n");
            sbSQL.append(" WHERE PAYaccountid="+info.getAccountId());
          
            sbSQL.append(" and DepositNo='"+info.getNewDepositNo()+"'");
            sbSQL.append(" and STATUSID<>"+SETTConstant.TransactionStatus.DELETED);
            sbSQL.append(" and Execute >= to_date('"
                    + DataFormat.getDateString(info.getExecuteDate())
                    + "','yyyy-mm-dd')   \n");
            sbSQL.append(" order by id desc ");
            //sbSQL
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
                
                bReturn = true; 
                //v.add(info);

            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return bReturn;
    }
    
    
    
    public boolean isReportLossOrFreeze(ReportLossOrFreezeInfo info) throws SettlementDAOException
    {
        ReportLossOrFreezeInfo rinfo = new ReportLossOrFreezeInfo();
        boolean flag = false;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT re.ID FROM \n");
            sbSQL.append(" sett_reportlossorfreeze re" + " \n");
            sbSQL.append(" WHERE re.ACCOUNTID="+info.getAccountId());
            sbSQL.append(" and re.officeid="+info.getOfficeId());
            sbSQL.append(" and re.currencyid="+info.getCurrencyId());
            sbSQL.append(" and re.status in("+SETTConstant.TransactionStatus.SAVE+","+SETTConstant.TransactionStatus.APPROVALING+")");
            sbSQL.append(" and re.TRANSACTIONTYPE="+info.getTransActionType());
            sbSQL.append(" order by id desc ");
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);

            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
            	rinfo.setId(transRS.getLong("ID"));
            }
            System.out.println("-----------------ID-->>"+rinfo.getId());
            if(rinfo.getId() > 0)
            {
            	flag = true;
            }
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return flag;
    }
    
    
    public long findMaxId(ReportLossOrFreezeInfo info) throws SettlementDAOException
    {
        long lReturn = -1;
        try
        {
            this.initDAO();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT max(re.ID) maxid FROM \n");
            sbSQL.append(" sett_reportlossorfreeze re" + " \n");
            sbSQL.append(" WHERE re.ACCOUNTID="+info.getAccountId());
            sbSQL.append(" and re.officeid="+info.getOfficeId());
            sbSQL.append(" and re.currencyid="+info.getCurrencyId());
            sbSQL.append(" and re.status = "+SETTConstant.TransactionStatus.CHECK);
            sbSQL.append(" and re.TRANSACTIONTYPE="+info.getTransActionType());
            sbSQL.append(" order by id desc ");
            String strSQL = sbSQL.toString();
            log.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            transRS = transPS.executeQuery();
            if (transRS!=null && transRS.next())
            {
            	lReturn  = transRS.getLong("maxid");
            }
            System.out.println("-----------------ID-->>"+lReturn);
            
           
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           throw new SettlementDAOException(e1);
        } catch (SQLException e2)
        {
             //TODO Auto-generated catch block
            e2.printStackTrace();
            throw new SettlementDAOException("发生数据库操作异常", e2);
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new SettlementDAOException("发生其他异常", e);
        }

        finally
        {
            try
            {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e3)
            {
                 //TODO 自动生成 catch 块
                e3.printStackTrace();
                throw new SettlementDAOException(e3);
            }
        }
        return lReturn;
    }

}