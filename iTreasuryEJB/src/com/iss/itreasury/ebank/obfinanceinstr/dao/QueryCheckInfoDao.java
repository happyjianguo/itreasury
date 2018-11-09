package com.iss.itreasury.ebank.obfinanceinstr.dao;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.OBFSWorkflowManager;

public class QueryCheckInfoDao {
	
	public String queryCheckInfoSQL(Query_FinanceInfo info){
		
		StringBuffer sbSQL= new StringBuffer();


		sbSQL = new StringBuffer();
		sbSQL.append(" select  * \n");
		sbSQL.append(" from ( select B.mAmount,B.id,B.DTMODIFY,B.dtexecute,B.DTCONFIRM,B.ntranstype,B.SNOTE,B.NSTATUS,S.saccountno,S.SNAME NBSNAME," +
			     "  decode(nremittype,102,P.spayeebankname,'') spayeebankname  ," +
			     "  decode(nremittype,102,P.spayeename,103,S1.SNAME,S1.SNAME) spayeename  ," +
			      " decode(nremittype,102,P.spayeeacctno,103,S1.SACCOUNTNO,S1.SACCOUNTNO) spayeeacctno, \n");	
		sbSQL.append(" B.npayeracctid npayeracctid,B.NCONFIRMUSERID confirmUserID,B.SIGNATUREVALUE signatureValue, ");
		sbSQL.append(" B.SDEPOSITNO DepositNo,B.Npayeeacctid ,B.NINTERESTPAYEEACCTID InterestPayeeAcctID, ");
		sbSQL.append(" B.NREMITTYPE RemitType,B.NINTERESTREMITTYPE InterestRemitType,B.NNOTICEDAY noticeday, ");
		sbSQL.append(" B.SDEPOSITBILLNO depositBillNo,B.fixednextperiod depositBillPeriod, ");
		sbSQL.append(" B.FIXEDNEXTSTARTDATE depositBillStartDate,B.fixedinterestdeal depositInterestDeal, ");
		sbSQL.append(" B.FIXEDINTERESTTOACCOUNTID depositInterestToAccountID,B.NFIXEDDEPOSITTIME fixedDepositTime,B.timestamp  ");
		sbSQL.append(" from ob_financeinstr B ,SETT_ACCOUNT S,SETT_ACCOUNT S1,OB_PAYEEINFO P \n");
		sbSQL.append(" where ");
		sbSQL.append(" B.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
		sbSQL.append(" AND B.npayeracctid=S.id(+) \n");
		sbSQL.append(" AND B.nclientid="+info.getClientID()+ "\n");
		sbSQL.append(" AND B. npayeeacctid=P.id(+) \n");
		sbSQL.append(" AND B. npayeeacctid=S1.id(+) \n");
		sbSQL.append(" AND B.nConfirmUserID != " + info.getNUserID() + " \n");
		sbSQL.append(" and B.sbatchno is null" + " \n");
		
		 //�ύ����-��
		if (info.getStartSubmit() != null && info.getStartSubmit().trim().length() > 0 )
		{
			sbSQL.append(" AND B.DTCONFIRM >= to_date('"+info.getStartSubmit()+"','yyyy-mm-dd') \n");
			
		}
		
		// �ύ����-��
		if (info.getEndSubmit() != null && info.getEndSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND B.DTCONFIRM <= to_date('"+info.getEndSubmit()+"','yyyy-mm-dd')+1 \n");
		}
		
		// ִ������-��
		if (info.getStartExe() != null && info.getStartExe().trim().length() > 0)
		{
			sbSQL.append(" AND B.DTEXECUTE >= to_date('"+info.getStartExe()+"','yyyy-mm-dd') \n");
		}
		// ִ������-��
		if (info.getEndExe() != null && info.getEndExe().trim().length() > 0 )
		{
			sbSQL.append(" AND B.DTEXECUTE <= to_date('"+info.getEndExe()+"','yyyy-mm-dd') \n");
		}
		
		// ���׽��-��
		if (info.getMinAmount() > 0.0)
		{
			sbSQL.append(" AND B.mAmount >= " + info.getMinAmount() + " \n");
		}
		// ���׽��-��
		if (info.getMaxAmount() > 0.0)
		{
			sbSQL.append(" AND B.mAmount <= " + info.getMaxAmount() + " \n");
		}
		//ҵ�񸴺˲�ѯ����ѯδ���˵ļ�¼:
		if (info.getNSTATUS() ==-1)
		{
			
			sbSQL.append(" AND B.nstatus in ("+OBConstant.SettInstrStatus.SAVE+","+OBConstant.SettInstrStatus.APPROVALED+") \n");
			sbSQL.append(" and ((B.nstatus = 1 and b.ntranstype not in ( select b.transtypeid from OB_APPROVALRELATIONNEW b where  clientid ="+info.getClientID()+ " and  officeid = "+info.getOfficeID()+" and currencyid ="+info.getCurrencyID()+" and b.approvalid >0 and islowerunit = 2 group by b.transtypeid))or B.nstatus = 20)\n");
		
		}
		if (info.getNSTATUS() ==OBConstant.SettInstrStatus.SAVE)
		{
			
			sbSQL.append(" AND B.nstatus ="+OBConstant.SettInstrStatus.SAVE+" \n");
			sbSQL.append(" and ((B.nstatus = 1 and b.ntranstype not in ( select b.transtypeid from OB_APPROVALRELATIONNEW b where  clientid ="+info.getClientID()+ " and  officeid = "+info.getOfficeID()+" and currencyid ="+info.getCurrencyID()+" and b.approvalid >0 and islowerunit = 2 group by b.transtypeid))or B.nstatus = 20)\n");
		
		}
	
		if (info.getNSTATUS() == OBConstant.SettInstrStatus.APPROVALED)
		{
			
			sbSQL.append(" AND B.nstatus = " + OBConstant.SettInstrStatus.APPROVALED + " \n");
			
		
		}   
	    if (info.getNtranstype() == 0 && "current".equals(info.getSign()))
	    {
	    	sbSQL.append(" AND B.ntranstype in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
	    }
	    if (info.getNtranstype() == 0 && "fixd".equals(info.getSign()))
	    {
	    	sbSQL.append(" AND B.ntranstype not in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
	    }
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_CAPTRANSFER_DOUBLE)		//��ʸ���
		{
			sbSQL.append(" AND B.ntranstype in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
			
		}
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_OPENFIXDEPOSIT)   		//���ڿ���
		{
			sbSQL.append(" AND B.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT+ " \n");
			
		}
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_FIXEDTOCURRENTTRANSFER)        //����֧ȡ
		{
			sbSQL.append(" AND B.ntranstype = " + OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER+ " \n");
			
		}
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_OPENNOTIFYACCOUNT)     //֪ͨ����
		{
			sbSQL.append(" AND B.ntranstype = " + OBConstant.SettInstrType.OPENNOTIFYACCOUNT+ " \n");
			
		}
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_NOTIFYDEPOSITDRAW)            //֪֧ͨȡ
		{
			sbSQL.append(" AND B.ntranstype = " + OBConstant.SettInstrType.NOTIFYDEPOSITDRAW+ " \n");
			
		}
		if (info.getNtranstype() == OBConstant.SettInstrType.BANK_DRIVEFIXDEPOSIT)            //��������
		{
			sbSQL.append(" AND B.ntranstype = " + OBConstant.SettInstrType.DRIVEFIXDEPOSIT+ " \n");
			
		}
		if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
		{
			sbSQL.append(" and B.SIGNATUREVALUE is not null ");
		}
		sbSQL.append(" ) \n");
		sbSQL.append(" where 1=1");
		sbSQL.append(" order by DTCONFIRM desc,ID");
	
		System.out.println("select " +sbSQL.toString() + " from "+ sbSQL.toString() + " where "+sbSQL.toString() + sbSQL.toString());
	
		return sbSQL.toString();
	}
	
	
	public String queryBatchCheckInfoSQL(FinanceInfo info){
		
		StringBuffer sbSQL= new StringBuffer();

		sbSQL = new StringBuffer();
		sbSQL.append(" select f.sbatchno sbatchno, \n ");
		sbSQL.append(" count(1) ncount, \n ");
		sbSQL.append(" to_char(f.dtconfirm ,'yyyy-mm-dd' ) dtconfirm, \n ");//��ͬ�����κ����һ�Σ������޸�By wjyang
		sbSQL.append(" u.sname sname, \n ");
		sbSQL.append(" sum(f.mamount) mamount \n ");
		sbSQL.append("   from ob_financeinstr f \n ");
		sbSQL.append("   join ob_user u on f.nconfirmuserid = u.id \n ");
		sbSQL.append("  where f.dtconfirm > to_date('"+DataFormat.formatDate(info.getDtDepositBillCheckdate())+"', 'yyyy-mm-dd') \n ");
		sbSQL.append("    and f.dtconfirm < to_date('"+DataFormat.formatDate(info.getDtDepositBillInputdate())+"', 'yyyy-mm-dd')+1 \n ");
		sbSQL.append("    and f.nConfirmUserID != " + info.getNUserID() + " \n ");
		sbSQL.append("    and f.sbatchno is not null \n ");
		sbSQL.append("    and f.nstatus = '"+info.getSStatus()+"' \n ");
		if( info.getClientID() > 0 )
		{
			sbSQL.append("   and f.nclientid = " + info.getClientID());
		}
		sbSQL.append("  group by f.sbatchno, to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid,  u.sname \n ");
		sbSQL.append("  order by f.sbatchno desc ");
	
		return sbSQL.toString();
	}
	
	public String queryBatchCheckInfoDeatilSQL(FinanceInfo info){
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append(" select ob.*, \n");
		strSQL.append(" sa.accountno payeeacctno, \n");
		strSQL.append(" sa.name payeename, \n");			
		strSQL.append(" sa.prov spayeeprov, \n");// �������л����ʾ�տ������ʡ
		strSQL.append(" sa.city spayeecity, \n");//�������л����ʾ�տ��������
		strSQL.append(" sa.bankname spayeebankname , \n");//  �������л����ʾ�տ��������   
		strSQL.append(" sa.bankexchangeno spayeebankexchangeno,\n");    
		strSQL.append(" sa.bankcnapsno spayeebankcnapsno, \n");     
		strSQL.append(" sa.bankorgno spayeebankorgno, \n");  
		strSQL.append(" sett.saccountno payeracctno, \n");
		strSQL.append(" sett.sname payername \n");			
		strSQL.append("  from ob_financeinstr   ob,\n");
		strSQL.append("  (  select t1.id, \n");
		strSQL.append("   'bank' payeeType, \n");
		strSQL.append("   t1.SPAYEEACCTNO ACCOUNTNO, \n");
		strSQL.append("   t1.SPAYEENAME NAME, \n");
		strSQL.append("   t1.SPAYEEPROV PROV, \n");
		strSQL.append("   t1.SPAYEECITY CITY, \n");
		strSQL.append("   t1.SPAYEEBANKNAME BANKNAME, \n");
		strSQL.append("   t1.spayeebankexchangeno bankexchangeno, \n");
		strSQL.append("   t1.spayeebankcnapsno bankcnapsno, \n");
		strSQL.append("   t1.spayeebankorgno bankorgno \n");
		strSQL.append("   from OB_PAYEEINFO t1 \n");
		strSQL.append("   union \n");
		strSQL.append("   select t2.id, \n");
		strSQL.append("   'system' payeeType, \n");
		strSQL.append("   t2.SACCOUNTNO ACCOUNTNO, \n");
		strSQL.append("   t2.SNAME NAME, \n");
		strSQL.append("   '' PROV, \n");
		strSQL.append("   '' CITY, \n");
		strSQL.append("   '' BANKNAME, \n");
		strSQL.append("   '' bankexchangeno, \n");
		strSQL.append("   '' bankcnapsno, \n");
		strSQL.append("   '' bankorgno \n");
		strSQL.append("   from SETT_ACCOUNT t2  )    sa, \n");
		strSQL.append("   sett_account sett \n");
		strSQL.append(" where ob.npayeeacctid = sa.id(+) \n");
		strSQL.append(" and ob.npayeracctid = sett.id(+) \n");
		strSQL.append(" and decode(ob.NREMITTYPE, 102, 'bank', 'system') = sa.payeetype \n");
		strSQL.append("  and ob.sbatchno =  " +  "'"+info.getSBatchNo().toString()+"'");
		strSQL.append("  and ob.nstatus = 1");
		
		return strSQL.toString();
	}
	
	public String queryUncheckInfo(QueryCapForm info) throws Exception{
		
		StringBuffer sbSQL = new StringBuffer();
		OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
		
		sbSQL.append(" SELECT fin.*, \n");
		sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
		sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
		sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
		sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
		sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
		sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
		sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
		sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
		sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
		sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
		sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
		sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
		sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");			
		if ( info.getCurrencyID() >0)
			sbSQL.append(" AND fin.nCurrencyID=" + info.getCurrencyID() + " \n");
		sbSQL.append(" AND fin.nClientID=" + info.getClientID() + " \n");
		//����ָ������
		if (info.getTransType() == 0 && info.getSign().equals("current"))
		{
			sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		if (info.getTransType() == 0 && info.getSign().equals("fixd"))
		{
			sbSQL.append(
					" AND fin.ntranstype not in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		if (info.getTransType() > 0)
		{
			if (info.getTransType() == 1)
			{
				sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
			}
			else if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n ");
				sbSQL.append(" AND fin.ndepositbillstatusid is not null \n ");		
			}
			else if(info.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(info.getTransType()) + " \n");
				sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid=");		
				sbSQL.append(OBConstant.SettInstrStatus.SAVE);	
				sbSQL.append(")\n ");
				sbSQL.append(" and fin.settfinid is null");
			}
			else
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(info.getTransType()) + " \n");
				sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
			}
		}
		else
		{
			if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) && (info.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				//ȡ�����˲�ѯ�����ܲ�ѯ���ҽ��������������Զ����˵ļ�¼��ԭ�򣺹ҽ������������Զ����˵ļ�¼ֻ����ȡ�������˵���������
				String tempAutoTransTypes = this.checkTransType(info);
				if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
				{
					sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
				}
			}
		}
		//ҵ�񸴺˲�ѯ����ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
		if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) && (info.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + " \n");
			}
			else
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
			sbSQL.append(" AND fin.nConfirmUserID != " + info.getUserID() + " \n");
		}
		//ҵ�񸴺˲�ѯ����ѯ�Ѹ��˵ļ�¼����¼�˸��˵ı���λ����ָ��
		if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) && (info.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.ndepositbillcheckuserid = " + info.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.nCheckUserID = " + info.getUserID() + " \n");
			}
		}
		//ҵ��ǩ�ϲ�ѯ����ѯδǩ�ϵļ�¼��ָ����¼��ǩ�ϵı���λ����ָ��
		if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) && (info.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{    sbSQL.append(" AND fin.sbatchno is  null\n");
			if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
			}
			else
				sbSQL.append(" AND ((fin.nStatus = " 
						+ OBConstant.SettInstrStatus.CHECK 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="+OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.CHECK 
						+ ")) \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) >= " + oBFinanceInstrDao.getMinSignAmount(info.getUserID(), info.getClientID(), info.getCurrencyID())+ " \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) < " + oBFinanceInstrDao.getMaxSignAmount(info.getUserID(), info.getClientID(), info.getCurrencyID()) + " \n");
			sbSQL.append(" AND fin.nIsCanAccept != 1 \n");
		}
		//ҵ��ǩ�ϲ�ѯ����ѯ��ǩ�ϵļ�¼��ָ����¼��ǩ�ϲ����Ѿ�ǩ�ϵı���λ����ָ��
		if ((info.getStatus() == OBConstant.SettInstrStatus.SIGN) && (info.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{   
			if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SIGN + " \n");
				sbSQL.append(" AND fin.ndepositbillsignuserid = " + info.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND ((fin.nstatus = " 
						+ OBConstant.SettInstrStatus.SIGN 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="
						+ OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.SIGN 
						+ ")) \n");
				sbSQL.append(" AND fin.nSignUserID = " + info.getUserID() + " \n");
			}
			sbSQL.append(" AND fin.nIsCanAccept = 1 \n");
			sbSQL.append(" AND fin.sbatchno is  null\n");
		}
		//���������ѯ����ѯ����״̬
		if (info.getStatus() > 0 && info.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
		{
			if(info.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" and fin.ndepositbillstatusid= " + info.getStatus() + " \n");
			}
			else
			{
				//���������ѯ����ѯ����״̬
				sbSQL.append(" AND fin.nstatus = " + info.getStatus() + " \n");
			}
		}
		// �ύ����-��1
		if (info.getStartSubmit() != null && info.getStartSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM >= to_date('"+info.getStartSubmit().trim()+" 00:00:00', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// �ύ����-��
		if (info.getEndSubmit() != null && info.getEndSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM <= to_date('"+info.getEndSubmit().trim()+" 23:59:59', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// ִ������-��
		if (info.getStartExe() != null && info.getStartExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE >= to_date('"+info.getStartExe().trim()+" 00:00:00', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// ִ������-��
		if (info.getEndExe() != null && info.getEndExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE <= to_date('"+info.getEndExe().trim()+" 23:59:59', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// ���׽��-��
		if (info.getMinAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount >= " + info.getMinAmount() + " \n");
		}
		// ���׽��-��
		if (info.getMaxAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount <= " + info.getMaxAmount() + " \n");
		}
		// ��ͬID
		if (info.getContractID() != -1)
		{
			sbSQL.append(" AND nContractID = " + info.getContractID() + " \n");
		}
		// �ſ�֪ͨ��ID
		if (info.getDepositID() != -1)
		{
			sbSQL.append(" AND fin.NSUBACCOUNTID = " + info.getDepositID() + " \n");
		}
		//������λ
		if (info.getChildClientID() > 0)
		{
			sbSQL.append(" AND nChildClientid=" + info.getChildClientID() + " \n");
		}
		if (info.getChildClientID() == -2)
		{
			sbSQL.append(" AND nChildClientid > 0 \n");
		}
		
		if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
		{
			sbSQL.append(" and fin.signaturevalue is not null \n");
		}			

		if (info.isOrderBy())
		{
			sbSQL.append(" order by  fin.id desc ,TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID \n");
		}
		else
		{
			sbSQL.append(" ORDER BY fin.id desc ,fin.dtconfirm ASC ,fin.nPayerAcctID \n");
		}
		
		return sbSQL.toString();
	}
	
	/**
	 * �õ���Щҵ����������������Զ�����
	 * @param queryCapForm
	 * @return
	 */
	private String checkTransType(QueryCapForm queryCapForm)
	{
		String strReturn = "";
		boolean bisAutoCheck=false;
		try {
			bisAutoCheck = OBFSWorkflowManager.isAutoCheck();
			InutApprovalRelationInfo pInfo = new InutApprovalRelationInfo();
	    	pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	    	pInfo.setCurrencyID(queryCapForm.getCurrencyID());
	    	pInfo.setClientID(queryCapForm.getClientID());
			// �Ƿ��Ƿ����ù����¼���λ
	    	pInfo.setIslowerunit(OBConstant.IsLowerun.ISNO); 
	    	InutApprovalRelationBiz biz = new InutApprovalRelationBiz();
	    	Collection coll = biz.queryByConditions(pInfo);
	    	if(coll!=null && coll.size()>0)
	    	{
	    		Iterator it = coll.iterator();
	    		while(it.hasNext())
	    		{
	    			InutApprovalRelationInfo tempInfo =(InutApprovalRelationInfo)it.next();
	    			if(bisAutoCheck)
	    			{
	    				strReturn = strReturn+tempInfo.getTransTypeID()+",";
	    			}
	    		}
	    	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strReturn.length()>0?strReturn.substring(0, strReturn.length()-1):"";
	}
	
	
	public String querySignInfoSQL(QueryCapForm qcf) throws Exception{
		
		StringBuffer sbSQL = new StringBuffer();
		OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
		
		sbSQL.append(" SELECT fin.*, \n");
		sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
		sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
		sbSQL.append(" office.sname officename,cpfUser.sname DealUserName, \n");
		sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid \n");
		sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
		sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
		sbSQL.append(" OB_User delUser,office ,userinfo cpfUser, \n");
		sbSQL.append(" (select * from BS_BANKINSTRUCTIONINFO bs where bs.n_id in ( \n");
		sbSQL.append("  select max(bs.n_id)\n");
		sbSQL.append("  from BS_BANKINSTRUCTIONINFO bs \n");
		sbSQL.append("  group by bs.s_transactionno) \n");
		sbSQL.append("  ) bs \n");
		sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
		sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
		sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
		sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
		sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
		sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
		sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");			
		sbSQL.append(" and bs.s_transactionno(+)='1'||to_char(fin.CPF_STRANSNO) \n");		
		if ( qcf.getCurrencyID() >0)
			sbSQL.append(" AND fin.nCurrencyID=" + qcf.getCurrencyID() + " \n");
		sbSQL.append(" AND fin.nClientID=" + qcf.getClientID() + " \n");
		//�˻�Ȩ�޿���(ǩ��ʱ��������)
		if (qcf.getOperationTypeID() != OBConstant.QueryOperationType.SIGN)
		{
			if (qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT
				|| qcf.getTransType() == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT
				|| qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT
				|| qcf.getTransType() == OBConstant.QueryInstrType.TRUSTLOANRECEIVE
				|| qcf.getTransType() == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE
				|| qcf.getTransType() == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
			{
				if (qcf.getUserID() != -1)
				{
					//���ڿ�����������գ����Ƹ���˻����տ�˻�
					//��ѯ���ĸ���˺ű����Ǹ��û�ӵ��Ȩ�޵�
					sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
					
					//��ѯ�����տ�˺ű����Ǹ��û�ӵ��Ȩ�޵�
					sbSQL.append(" AND fin.nPayeeAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
				}
			}
			else
			{
				//����ҵ��ֻ���Ƹ���˻�
				//��ѯ���ĸ���˺ű����Ǹ��û�ӵ��Ȩ�޵�
				if (qcf.getUserID() != -1)
				{
					sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
				}
			}
		}
		//����ָ������
		
		//�°»���ҵ�񣬵�ѡȫ��ʱֻ��ѯ������ҵ��
		if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("current"))
		{
			sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		//�°¶���ҵ�񣬵�ѡȫ��ʱֻ��ѯ������ҵ��
		if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("fixd"))
		{
			sbSQL.append(
					" AND fin.ntranstype not in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		if (qcf.getTransType() > 0)
		{
			if (qcf.getTransType() == 1)
			{
				sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
			}
			else if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n ");
				sbSQL.append(" AND fin.ndepositbillstatusid is not null \n ");		
			}
			else if(qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
				sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid=");		
				sbSQL.append(OBConstant.SettInstrStatus.SAVE);	
				sbSQL.append(")\n ");
				sbSQL.append(" and fin.settfinid is null");
			}
			else
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
				sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
			}
		}
		else
		{
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				//ȡ�����˲�ѯ�����ܲ�ѯ���ҽ��������������Զ����˵ļ�¼��ԭ�򣺹ҽ������������Զ����˵ļ�¼ֻ����ȡ�������˵���������
				String tempAutoTransTypes = checkTransType(qcf);
				if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
				{
					sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
				}
			}
		}
		//ҵ�񸴺˲�ѯ����ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + " \n");
			}
			else
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
			sbSQL.append(" AND fin.nConfirmUserID != " + qcf.getUserID() + " \n");
		}
		//ҵ�񸴺˲�ѯ����ѯ�Ѹ��˵ļ�¼����¼�˸��˵ı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.ndepositbillcheckuserid = " + qcf.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.nCheckUserID = " + qcf.getUserID() + " \n");
			}
		}
		//ҵ��ǩ�ϲ�ѯ����ѯδǩ�ϵļ�¼��ָ����¼��ǩ�ϵı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{    sbSQL.append(" AND fin.sbatchno is  null\n");
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
			}
			else
				sbSQL.append(" AND ((fin.nStatus = " 
						+ OBConstant.SettInstrStatus.CHECK 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="+OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.CHECK 
						+ ")) \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) >= " + oBFinanceInstrDao.getMinSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign())+ " \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) < " + oBFinanceInstrDao.getMaxSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign()) + " \n");
			sbSQL.append(" AND fin.nIsCanAccept != 1 \n");
		}
		//ҵ��ǩ�ϲ�ѯ����ѯ��ǩ�ϵļ�¼��ָ����¼��ǩ�ϲ����Ѿ�ǩ�ϵı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{   
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SIGN + " \n");
				sbSQL.append(" AND fin.ndepositbillsignuserid = " + qcf.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND ((fin.nstatus = " 
						+ OBConstant.SettInstrStatus.SIGN 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="
						+ OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.SIGN 
						+ ")) \n");
				sbSQL.append(" AND fin.nSignUserID = " + qcf.getUserID() + " \n");
			}
			sbSQL.append(" AND fin.nIsCanAccept = 1 \n");
			sbSQL.append(" AND fin.sbatchno is  null\n");
		}
		//���������ѯ����ѯ����״̬
		if (qcf.getStatus() > 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" and fin.ndepositbillstatusid= " + qcf.getStatus() + " \n");
			}
			else
			{
				//���������ѯ����ѯ����״̬
				sbSQL.append(" AND fin.nstatus = " + qcf.getStatus() + " \n");
			}
		}
		
		//����ָ��״̬
		//����
		if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.CANCEL);
		}
		//�Ա���δ����
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SAVED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SAVED);
		}
		//֧��������
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTING)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTING);
		}
		//֧���ɹ�
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTED);
		}
		//֧��ʧ��
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.FAILED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.FAILED);
		}
		//֧��δ֪
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.UNKNOWENED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.UNKNOWENED);
		}
		//��״̬
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.NONE)
		{
			sbSQL.append(" and nvl(n_statusid,-1) ="+OBConstant.BankInstructionStatus.UNSEND);
		}
		
		// �ύ����-��1
		if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM >= to_date('"+qcf.getStartSubmit().trim()+" 00:00:00', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// �ύ����-��
		if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM <= to_date('"+qcf.getEndSubmit().trim()+" 23:59:59', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// ִ������-��
		if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE >= to_date('"+qcf.getStartExe().trim()+" 00:00:00', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		// ִ������-��
		if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE <= to_date('"+qcf.getEndExe().trim()+" 23:59:59', 'yyyy-mm-dd hh24-mi-ss') \n");
		}
		
		// ���׽��-��
		if (qcf.getMinAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
		}
		// ���׽��-��
		if (qcf.getMaxAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
		}
		// ��ͬID
		if (qcf.getContractID() != -1)
		{
			sbSQL.append(" AND nContractID = " + qcf.getContractID() + " \n");
		}
		// �ſ�֪ͨ��ID
		if (qcf.getDepositID() != -1)
		{
			sbSQL.append(" AND fin.NSUBACCOUNTID = " + qcf.getDepositID() + " \n");
		}
		//������λ
		if (qcf.getChildClientID() > 0)
		{
			sbSQL.append(" AND nChildClientid=" + qcf.getChildClientID() + " \n");
		}
		if (qcf.getChildClientID() == -2)
		{
			sbSQL.append(" AND nChildClientid > 0 \n");
		}
		
		if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
		{
			sbSQL.append(" and (fin.signaturevalue is not null or fin.lsource >"+SETTConstant.ExtSystemSource.EBANK+") \n");
		}				

		if (qcf.isOrderBy())
		{
			sbSQL.append(" order by  fin.id desc ,TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID \n");
		}
		else
		{
			sbSQL.append(" ORDER BY fin.id desc ,fin.dtconfirm ASC ,fin.nPayerAcctID \n");
		}
		
		return sbSQL.toString();
	}
	
	
	public String queryBatchSignInfoSQL(FinanceInfo finanInfo) throws Exception{
		
		StringBuffer strSQL= new StringBuffer();
		OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
		
		strSQL = new StringBuffer();
		strSQL.append("select f.sbatchno sbatchno,\n");
		strSQL.append("       count(1) ncount,\n");
		strSQL.append("        to_char(f.dtconfirm ,'yyyy-mm-dd' ) dtconfirm,\n");//��ͬ�����κ����һ�Σ������޸�By wjyang
		strSQL.append("       u.sname sname,\n");
		strSQL.append("       sum(f.mamount) mamount\n");
		strSQL.append("  from ob_financeinstr f\n");
		strSQL.append("  join ob_user u on f.nconfirmuserid = u.id\n");
		strSQL.append(" where f.dtconfirm > to_date('"+DataFormat.getDateString(finanInfo.getDtDepositBillCheckdate())+"', 'yyyy-mm-dd')\n");
		strSQL.append("   and f.dtconfirm < to_date('"+DataFormat.getDateString(finanInfo.getDtDepositBillInputdate())+"', 'yyyy-mm-dd')+1\n");			
		strSQL.append(" AND f.nclientid="+finanInfo.getClientID()+ "\n");
		if(finanInfo.getStatus()==OBConstant.SettInstrStatus.CHECK){
			strSQL.append(" AND (NVL(f.mAmount,0)+NVL(f.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealCompoundInterest,0)+NVL(f.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealSuretyFee,0)+NVL(f.mRealCommission,0)) >= " + oBFinanceInstrDao.getMinSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current")+ " \n");
			strSQL.append(" AND (NVL(f.mAmount,0)+NVL(f.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealCompoundInterest,0)+NVL(f.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealSuretyFee,0)+NVL(f.mRealCommission,0)) < " + oBFinanceInstrDao.getMaxSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current") + " \n");
			strSQL.append(" AND f.nIsCanAccept != 1 \n");
		}else if(finanInfo.getStatus()==OBConstant.SettInstrStatus.SIGN){
			strSQL.append(" AND f.nIsCanAccept = 1 \n");
			strSQL.append(" AND f.nSignUserID = " + finanInfo.getUserID() + " \n");
		}			
		strSQL.append("   and f.nClientID = " + finanInfo.getClientID() + " \n");
		strSQL.append("   and f.sbatchno is not null\n");
		strSQL.append("   and f.nstatus = "+Long.parseLong(finanInfo.getSStatus())+"\n");
		strSQL.append(" group by f.sbatchno,  to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid,  u.sname ");
		strSQL.append(" order by f.sbatchno desc ");
	
		return strSQL.toString();
	}
	
	
	public String queryBatchSignInfoDeatilSQL(FinanceInfo finanInfo) throws Exception{
		
		OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append(" select ob.*,\n");
		strSQL.append(" sa.accountno payeeacctno,\n");
		strSQL.append(" sa.name payeename,\n");			
		strSQL.append(" sa.prov spayeeprov,\n");// �������л����ʾ�տ������ʡ
		strSQL.append(" sa.city spayeecity,\n");//�������л����ʾ�տ��������
		strSQL.append(" sa.bankname spayeebankname ,\n");//  �������л����ʾ�տ��������   
		//����ӿ�����
		strSQL.append(" sa.bankexchangeno spayeebankexchangeno,\n");   
		strSQL.append(" sa.bankcnapsno spayeebankcnapsno,\n");  
		strSQL.append(" sa.bankorgno spayeebankorgno,\n"); 
		strSQL.append(" sett.saccountno payeracctno,\n");
		strSQL.append(" sett.sname payername\n");			
		strSQL.append(" from ob_financeinstr ob,\n");
		strSQL.append(" sett_account sett, ");
		strSQL.append(" (  select t1.id, ");
		strSQL.append("  'bank' payeeType, ");
		strSQL.append(" t1.SPAYEEACCTNO ACCOUNTNO, ");
		strSQL.append(" t1.SPAYEENAME NAME, ");
		strSQL.append(" t1.SPAYEEPROV PROV, ");
		strSQL.append(" t1.SPAYEECITY CITY, ");
		strSQL.append(" t1.SPAYEEBANKNAME BANKNAME, ");
		strSQL.append(" t1.spayeebankexchangeno bankexchangeno,  ");
		strSQL.append(" t1.spayeebankcnapsno bankcnapsno, ");
		strSQL.append("  t1.spayeebankorgno bankorgno  ");
		strSQL.append(" from OB_PAYEEINFO t1 ");
		strSQL.append(" union ");
		strSQL.append(" select t2.id, ");
		strSQL.append(" 'system' payeeType, ");
		strSQL.append(" t2.SACCOUNTNO ACCOUNTNO, ");
		strSQL.append(" t2.SNAME NAME, ");
		strSQL.append(" '' PROV, ");
		strSQL.append(" '' CITY, ");
		strSQL.append(" '' BANKNAME, ");
		strSQL.append(" '' bankexchangeno,  ");
		strSQL.append(" '' bankcnapsno, ");
		strSQL.append("  '' bankorgno ");
		strSQL.append(" from SETT_ACCOUNT t2  )  sa ");
		strSQL.append(" where ob.npayeeacctid = sa.id(+) ");
		strSQL.append(" and ob.npayeracctid = sett.id(+) ");
		strSQL.append("  and decode(ob.NREMITTYPE, 102, 'bank', 'system') = sa.payeetype ");
		
		if(finanInfo.getStatus()==OBConstant.SettInstrStatus.CHECK){
			strSQL.append(" AND (NVL(ob.mAmount,0)+NVL(ob.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealCompoundInterest,0)+NVL(ob.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealSuretyFee,0)+NVL(ob.mRealCommission,0)) >= " + oBFinanceInstrDao.getMinSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current")+ " \n");
			strSQL.append(" AND (NVL(ob.mAmount,0)+NVL(ob.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealCompoundInterest,0)+NVL(ob.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealSuretyFee,0)+NVL(ob.mRealCommission,0)) < " + oBFinanceInstrDao.getMaxSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current") + " \n");
			strSQL.append(" AND ob.nIsCanAccept != 1 \n");
		}else if(finanInfo.getStatus()==OBConstant.SettInstrStatus.SIGN){
			strSQL.append(" AND ob.nIsCanAccept = 1 \n");
			strSQL.append(" AND ob.nSignUserID = " + finanInfo.getUserID() + " \n");
		}	
		
		strSQL.append("  and ob.sbatchno =  " +  "'"+finanInfo.getSBatchNo().toString()+"'");
		
		if(finanInfo.getStatus()>0){
			strSQL.append("  and ob.nstatus = "+finanInfo.getStatus());
		}
		
		return strSQL.toString();
		
	}
	
	
	public String getQueryCapFormSQL(QueryCapForm qcf) throws Exception{
		
		OBFinanceInstrDao oBFinanceInstrDao = new OBFinanceInstrDao();
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" SELECT fin.*, \n");
		sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
		sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
		sbSQL.append(" office.sname officename,cpfUser.sname DealUserName, \n");
		sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid \n");
		sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
		sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
		sbSQL.append(" OB_User delUser,office ,userinfo cpfUser, \n");
		sbSQL.append(" (select * from BS_BANKINSTRUCTIONINFO bs where bs.n_id in ( \n");
		sbSQL.append("  select max(bs.n_id)\n");
		sbSQL.append("  from BS_BANKINSTRUCTIONINFO bs \n");
		sbSQL.append("  group by bs.s_transactionno) \n");
		sbSQL.append("  ) bs \n");
		sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
		sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
		sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
		sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
		sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
		sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
		sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");			
		sbSQL.append(" and bs.s_transactionno(+)='1'||to_char(fin.CPF_STRANSNO) \n");		
		if ( qcf.getCurrencyID() >0)
			sbSQL.append(" AND fin.nCurrencyID=" + qcf.getCurrencyID() + " \n");
		sbSQL.append(" AND fin.nClientID=" + qcf.getClientID() + " \n");
		//�˻�Ȩ�޿���(ǩ��ʱ��������)
		if (qcf.getOperationTypeID() != OBConstant.QueryOperationType.SIGN)
		{
			if (qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT
				|| qcf.getTransType() == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT
				|| qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT
				|| qcf.getTransType() == OBConstant.QueryInstrType.TRUSTLOANRECEIVE
				|| qcf.getTransType() == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE
				|| qcf.getTransType() == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
			{
				if (qcf.getUserID() != -1)
				{
					//���ڿ�����������գ����Ƹ���˻����տ�˻�
					//��ѯ���ĸ���˺ű����Ǹ��û�ӵ��Ȩ�޵�
					sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
					
					//��ѯ�����տ�˺ű����Ǹ��û�ӵ��Ȩ�޵�
					sbSQL.append(" AND fin.nPayeeAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
				}
			}
			else
			{
				//����ҵ��ֻ���Ƹ���˻�
				//��ѯ���ĸ���˺ű����Ǹ��û�ӵ��Ȩ�޵�
				if (qcf.getUserID() != -1)
				{
					sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
					sbSQL.append(" select a.naccountid \n");
					sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
					sbSQL.append(" where ai.nStatusID=1  \n");
					sbSQL.append(" and a.saccountno=ai.saccountno  \n");
					sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
					sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
				}
			}
		}
		//����ָ������
		
		//�°»���ҵ�񣬵�ѡȫ��ʱֻ��ѯ������ҵ��
		if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("current"))
		{
			sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		//�°¶���ҵ�񣬵�ѡȫ��ʱֻ��ѯ������ҵ��
		if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("fixd"))
		{
			sbSQL.append(
					" AND fin.ntranstype not in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
		}	
		if (qcf.getTransType() > 0)
		{
			if (qcf.getTransType() == 1)
			{
				sbSQL.append(
					" AND fin.ntranstype in ("
						+ OBConstant.SettInstrType.CAPTRANSFER_SELF
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
						+ ","
						+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
						+ ","
						+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
						+ ") \n");
			}
			else if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n ");
				sbSQL.append(" AND fin.ndepositbillstatusid is not null \n ");		
			}
			else if(qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
				sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid=");		
				sbSQL.append(OBConstant.SettInstrStatus.SAVE);	
				sbSQL.append(")\n ");
				sbSQL.append(" and fin.settfinid is null");
			}
			else
			{
				sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
				sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
			}
		}
		else
		{
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				//ȡ�����˲�ѯ�����ܲ�ѯ���ҽ��������������Զ����˵ļ�¼��ԭ�򣺹ҽ������������Զ����˵ļ�¼ֻ����ȡ�������˵���������
				String tempAutoTransTypes = checkTransType(qcf);
				if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
				{
					sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
				}
			}
		}
		//ҵ�񸴺˲�ѯ����ѯδ���˵ļ�¼���ǵ�¼��¼��ı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + " \n");
			}
			else
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
			sbSQL.append(" AND fin.nConfirmUserID != " + qcf.getUserID() + " \n");
		}
		//ҵ�񸴺˲�ѯ����ѯ�Ѹ��˵ļ�¼����¼�˸��˵ı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.ndepositbillcheckuserid = " + qcf.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
				sbSQL.append(" AND fin.nCheckUserID = " + qcf.getUserID() + " \n");
			}
		}
		//ҵ��ǩ�ϲ�ѯ����ѯδǩ�ϵļ�¼��ָ����¼��ǩ�ϵı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{    sbSQL.append(" AND fin.sbatchno is  null\n");
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
			}
			else
				sbSQL.append(" AND ((fin.nStatus = " 
						+ OBConstant.SettInstrStatus.CHECK 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="+OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.CHECK 
						+ ")) \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) >= " + oBFinanceInstrDao.getMinSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign())+ " \n");
			sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
			sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) < " + oBFinanceInstrDao.getMaxSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign()) + " \n");
			sbSQL.append(" AND fin.nIsCanAccept != 1 \n");
		}
		//ҵ��ǩ�ϲ�ѯ����ѯ��ǩ�ϵļ�¼��ָ����¼��ǩ�ϲ����Ѿ�ǩ�ϵı���λ����ָ��
		if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
		{   
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SIGN + " \n");
				sbSQL.append(" AND fin.ndepositbillsignuserid = " + qcf.getUserID() + " \n");
			}
			else
			{
				sbSQL.append(" AND ((fin.nstatus = " 
						+ OBConstant.SettInstrStatus.SIGN 
						+ ") or (fin.ntranstype="
						+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
						+ " and fin.nstatus="
						+ OBConstant.SettInstrStatus.FINISH 
						+ " and fin.ndepositbillstatusid="
						+ OBConstant.SettInstrStatus.SIGN 
						+ ")) \n");
				sbSQL.append(" AND fin.nSignUserID = " + qcf.getUserID() + " \n");
			}
			sbSQL.append(" AND fin.nIsCanAccept = 1 \n");
			sbSQL.append(" AND fin.sbatchno is  null\n");
		}
		//���������ѯ����ѯ����״̬
		if (qcf.getStatus() > 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
		{
			if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
			{
				sbSQL.append(" and fin.ndepositbillstatusid= " + qcf.getStatus() + " \n");
			}
			else
			{
				//���������ѯ����ѯ����״̬
				sbSQL.append(" AND fin.nstatus = " + qcf.getStatus() + " \n");
			}
		}
		
		//����ָ��״̬
		//����
		if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.CANCEL);
		}
		//�Ա���δ����
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SAVED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SAVED);
		}
		//֧��������
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTING)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTING);
		}
		//֧���ɹ�
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTED);
		}
		//֧��ʧ��
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.FAILED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.FAILED);
		}
		//֧��δ֪
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.UNKNOWENED)
		{
			sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.UNKNOWENED);
		}
		//��״̬
		else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.NONE)
		{
			sbSQL.append(" and nvl(n_statusid,-1) ="+OBConstant.BankInstructionStatus.UNSEND);
		}
		
		// �ύ����-��1
		if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM >= to_date('"+qcf.getStartSubmit().trim() + " 00:00:00','yy-mm-dd hh24:mi:ss') \n");
		}
		// �ύ����-��
		if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTCONFIRM <= to_date('"+qcf.getEndSubmit().trim() + " 23:59:59','yy-mm-dd hh24:mi:ss') \n");
		}
		// ִ������-��
		if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE >= to_date('"+qcf.getStartExe().trim() + " 00:00:00','yy-mm-dd hh24:mi:ss') \n");
		}
		// ִ������-��
		if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
		{
			sbSQL.append(" AND fin.DTEXECUTE <= to_date('"+qcf.getEndExe().trim() + " 23:59:59','yy-mm-dd hh24:mi:ss') \n");
		}
		// ���׽��-��
		if (qcf.getMinAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
		}
		// ���׽��-��
		if (qcf.getMaxAmount() > 0.0)
		{
			sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
		}
		// ��ͬID
		if (qcf.getContractID() != -1)
		{
			sbSQL.append(" AND nContractID = " + qcf.getContractID() + " \n");
		}
		// �ſ�֪ͨ��ID
		if (qcf.getDepositID() != -1)
		{
			sbSQL.append(" AND fin.NSUBACCOUNTID = " + qcf.getDepositID() + " \n");
		}
		//������λ
		if (qcf.getChildClientID() > 0)
		{
			sbSQL.append(" AND nChildClientid=" + qcf.getChildClientID() + " \n");
		}
		if (qcf.getChildClientID() == -2)
		{
			sbSQL.append(" AND nChildClientid > 0 \n");
		}
		
		if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
		{
			sbSQL.append(" and (fin.signaturevalue is not null or fin.lsource >"+SETTConstant.ExtSystemSource.EBANK+") \n");
		}				

		if (qcf.isOrderBy())
		{
			sbSQL.append(" order by  fin.id desc ,TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID \n");
		}
		else
		{
			sbSQL.append(" ORDER BY fin.id desc ,fin.dtconfirm ASC ,fin.nPayerAcctID \n");
		}
		
		return sbSQL.toString();
		
	}
	
	
}
