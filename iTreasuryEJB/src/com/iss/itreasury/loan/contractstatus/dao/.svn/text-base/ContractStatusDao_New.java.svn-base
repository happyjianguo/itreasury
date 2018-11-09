package com.iss.itreasury.loan.contractstatus.dao;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.client.dataentity.QueryCorporationInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.loan.contractstatus.dataentity.ContractStatusInfo;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;


public class ContractStatusDao_New {

	/**
	 * 贷款合同状态变更dao层
	 * @author issuser
	 * @throws Exception 
	 *
	 */
	 public String queryContractStatusSQL(
			 long lCurrencyID,
			 long lOfficeID,
			 long lUserID,
			 long lActionID,
			 Timestamp tsDateFrom ,
	         Timestamp tsDateTo ,
			 long lStatusID) throws Exception{
			
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append(" select *from LOAN_CONTRACTSTATUS \n");
			
			sbSQL.append(" where 1 = 1 \n");
			
			if ( tsDateFrom != null )
            {
				sbSQL.append(" and to_char(dtInputDate,'yyyy-mm-dd') >= '"
                      + DataFormat.getDateString( tsDateFrom )
                      + "'" );
            }
            if ( tsDateTo != null )
            {
            	sbSQL.append(" and to_char(dtInputDate,'yyyy-mm-dd') <= '"
                      + DataFormat.getDateString( tsDateTo )
                      + "'" );
            }
            if ( lStatusID > 0 )
            {
            	sbSQL.append( " and nStatusID = " + lStatusID );
            }
            else
            {
            	sbSQL.append( " and nStatusID in ( "
                      + LOANConstant.RiskModifyStatus.CHECK
                      + ","
                      + LOANConstant.RiskModifyStatus.SUBMIT
                      + ")" );
            }
            if ( lCurrencyID > 0 )
            { sbSQL.append( " and NCURRENCYID = "
                + lCurrencyID);
                }
            if ( lOfficeID > 0 )
            { sbSQL.append(" and NOFFICEID = "
                + lOfficeID);
                }
			 if ( lActionID == 1 )
	            {
	                if ( lUserID != -1
	                      && lStatusID == LOANConstant.RiskModifyStatus.SUBMIT )
	                {
	                    sbSQL.append(" and nInputUserID = "+ " and nInputUserID = "
	                    		+ lUserID + " and nNextCheckLevel = 1 "
	                    		+ " and nStatusID = " + LOANConstant.RiskModifyStatus.SUBMIT );
	                          
	                }
	                if ( lUserID != -1
	                      && lStatusID == LOANConstant.RiskModifyStatus.CHECK )
	                {
	                	sbSQL.append( " and nInputUserID = " + lUserID+ " and nStatusID = " + LOANConstant.RiskModifyStatus.CHECK );
	                }
	                if ( lUserID != -1 && lStatusID == -1 )
	                {
	                	sbSQL.append( " and (( nInputUserID = " + lUserID + " and nNextCheckLevel = 1 "
	                		  + " and nStatusID = " + LOANConstant.RiskModifyStatus.SUBMIT
	                          + " ) or ( nInputUserID = "
	                          + lUserID
	                          + " and nStatusID = "
	                          + LOANConstant.RiskModifyStatus.CHECK
	                          + " )) " );
	                }
	            }
			 //审核查询
	            else if ( lActionID == 2 )
	            {
	                ApprovalDelegation appBiz = new ApprovalDelegation() ;

					LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
					long[] loanTypeId = { LOANConstant.LoanType.ZY,
							LOANConstant.LoanType.WT, LOANConstant.LoanType.ZGXE,
							LOANConstant.LoanType.MFXD, LOANConstant.LoanType.OTHER };
					String strUser = null;
					long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
							lOfficeID, lCurrencyID, loanTypeId);
					if (a_SubLoanType != null && a_SubLoanType.length > 0) {
						sbSQL.append( " and ( ");
						for (int i = 0; i < a_SubLoanType.length; i++) {
							strUser = appBiz.findTheVeryUser(
									Constant.ModuleType.LOAN, a_SubLoanType[i],
									Constant.ApprovalAction.CONTRACT_RISKLEVEL,
									lOfficeID, lCurrencyID, lUserID);
							
			                if ( strUser != null && strUser.length() > 0
			                      && lStatusID == LOANConstant.RiskModifyStatus.SUBMIT )
			                {
			                	sbSQL.append( " ( NSTATUSID = "
			                          + LOANConstant.RiskModifyStatus.SUBMIT
			                          + " and NNEXTCHECKUSERID in " + strUser +" ) ");
			                }
			                if ( strUser != null && strUser.length() > 0
			                      && lStatusID == LOANConstant.RiskModifyStatus.CHECK )
			                {
			                	sbSQL.append( " NSTATUSID = "
			                          + LOANConstant.RiskModifyStatus.CHECK +" ) ");
			                    break;
			                }
			                if ( strUser != null && strUser.length() > 0 && lStatusID <= 0 )
			                {
			                	sbSQL.append( " (((NSTATUSID = "
			                          + LOANConstant.RiskModifyStatus.SUBMIT
			                          + " and NNEXTCHECKUSERID in " + strUser
			                          + ") or (NSTATUSID = "
			                          + LOANConstant.RiskModifyStatus.CHECK
			                          + ") ) )" );
			                }
							if (i < a_SubLoanType.length - 1)
								sbSQL.append( " or ");
							else
								sbSQL.append( " ) ");		                
						}
					}else{
						return null;
					}
	                
	                

	            }
			 
			return sbSQL.toString();
			
		}
}
