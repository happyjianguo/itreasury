package com.iss.itreasury.bill.mywork.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BILLConstant.BillConsign;
import com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.bill.draft.dataentity.TransDraftOutInfo;

public class BillDraftOutActionDao  extends BillMyWorkDao {
	//������ҵ��
	protected Collection queryCurrentWork(BillMyWorkInfo billMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select ar.*, vt.*");
		sbSQL.append(" from sys_approvalrecord ar,");
		sbSQL.append(" (" );
		sbSQL.append(" select");
		sbSQL.append(" lb.id,");		//
		sbSQL.append(" lb.susername,");			//
		sbSQL.append(" lb.sbank,");				//
		sbSQL.append(" lb.nislocal,");			//
		sbSQL.append(" lb.dtcreate,");			// ��Ʊ��
		sbSQL.append(" lb.dtend,");				// ������
		sbSQL.append(" lb.scode,");				// Ʊ�ݺ�
		sbSQL.append(" lb.mamount,");			// Ʊ����
		sbSQL.append(" lb.nmodulesourceid,");	// ģ��id
		sbSQL.append(" lb.ndrafttypeid,");		// ��Ʊ����
		sbSQL.append(" lb.stracceptorname,");   // �ж���
		sbSQL.append(" lb.stracceptorbank,");	// �ж���
		sbSQL.append(" lb.stracceptoraccount,");// �ж��ʺ�
		sbSQL.append(" lb.nstoragestatusid,");	// Ʊ�ݵ�ǰ�����ĳ������״̬
		sbSQL.append(" lb.nstoragetransid,");	// Ʊ�ݵ�ǰ�����������״̬��Ӧ������⽻��id
		sbSQL.append(" lb.nquerystatusid,");	// Ʊ�ݲ�ѯ�鸴״̬
		sbSQL.append(" lb.nconsignstatusid,");	// Ʊ������״̬
		sbSQL.append(" lb.nformerconstatusid,");// ���ڼ�¼�޸�����״̬֮ǰ������״̬ 
		sbSQL.append(" lb.nconsigntime,");		// Ʊ�����մ���
		sbSQL.append(" lb.ninputuserid,");		// Ʊ����Ϣ¼����
		sbSQL.append(" lb.dtinputdate,");		// Ʊ����Ϣ¼������
		sbSQL.append(" lb.nmodifyuserid,");		// Ʊ����Ϣ�޸���
		sbSQL.append(" lb.dtmodifydate,");		// Ʊ����Ϣ�޸�����
		sbSQL.append(" lb.ncheckstatus,");		// ����״̬
		sbSQL.append(" lb.scheckcode,");		// ������
		sbSQL.append(" lb.dtcheckdate,");		// �������
		sbSQL.append(" lb.dtcanceldate,");		// ��������
		sbSQL.append(" lb.nofficeid,");			// 
		sbSQL.append(" lb.ncurrencyid,");		//
		sbSQL.append(" bo.id transid,");				// 
		sbSQL.append(" bo.transcode,");			// ���ױ��
		sbSQL.append(" bo.transtypeid,");		// ��������
		sbSQL.append(" bo.billdestinationid,");	// Ʊ��ȥ��
		sbSQL.append(" bo.billid,");			// ���Ʊ��id
		sbSQL.append(" bo.outdate,");			// ��������
		sbSQL.append(" bo.nextholder,");		// ���� 
		sbSQL.append(" bo.outcontractcode,");	// ����ʱ�ĺ�ͬ��� 
		sbSQL.append(" bo.outpayformcode,");	// �����ſ�֪ͨ��
		sbSQL.append(" bo.outrate,");			// ������������ 
		sbSQL.append(" bo.outinterest,");		// ����������Ϣ 
		sbSQL.append(" bo.repurchaseenddate,");	// �ع���������
		sbSQL.append(" bo.sumrepurchaseinterest,");// �ۼƻع���Ϣ
		sbSQL.append(" bo.inputuserid,");// ¼����
		sbSQL.append(" bo.inputdate,"); //	¼������
		sbSQL.append(" bo.modifyuserid,");// �޸��� 
		sbSQL.append(" bo.modifydate,");// �޸�����
		sbSQL.append(" bo.statusid");// ����״̬
		sbSQL.append(" from LOAN_DISCOUNTCONTRACTBILL lb, Bill_TransDraftOut bo");
		sbSQL.append(" where lb.id = bo.billid");
		sbSQL.append(" and lb.nstatusid = " + Constant.RecordStatus.VALID);
		sbSQL.append(" and bo.statusid = " + BILLConstant.TransctionStatus.APPROVALING);
		sbSQL.append(" ) vt");
		
		sbSQL.append(" where ar.moduleid = " + billMyWorkInfo.getModuleID());
		sbSQL.append(" and ar.transid = vt.transid");
		sbSQL.append(" and ar.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		sbSQL.append(" and ar.actionid = " + BILLConstant.DraftOperationType.DraftOut);
		sbSQL.append(" and ar.approvalentryid in (" + billMyWorkInfo.getApprovalEntryIDs() + ")");
		
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				BillMyWorkInfo workInfo = new BillMyWorkInfo();

				//����������Ϣ
				workInfo.setId(transRS.getLong("id"));
				workInfo.setOfficeID(transRS.getLong("nOfficeid"));
				workInfo.setCurrencyID(transRS.getLong("nCurrencyid"));
				//workInfo.setActionID(transRS.getLong("actionId"));
				//workInfo.setActionName(transRS.getString("transactionTypeName"));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setUserID(billMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("statusId"));
				
				//Ʊ��ҵ����Ϣ
				workInfo.setDTransID(transRS.getLong("transid"));
				workInfo.setBillId(transRS.getLong("transid"));
				workInfo.setBillCode(transRS.getString("scode"));
				workInfo.setTranscode(transRS.getString("transcode"));
				//workInfo.setClientName(transRS.getString("clientName"));
				workInfo.setBusinessTypeId(transRS.getLong("transtypeid"));
				workInfo.setBusinessTypeName(BILLConstant.TraceModule.getName(transRS.getLong("transtypeid")));
				workInfo.setTransactionTypeId(transRS.getLong("transtypeid"));
				workInfo.setTranscode(transRS.getString("transcode"));
				workInfo.setActionName(BILLConstant.DraftOperationType.getName(transRS.getLong("actionid")));
				workInfo.setStracceptoraccount(transRS.getString("stracceptoraccount"));
				workInfo.setInputUserID(transRS.getLong("inputUserID"));
				//workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));

				//��Ӧ������������Ĵ�����Ϣ
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)billMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	//�ܾ�ҵ��
	protected void getRefuseWorkSql(BillMyWorkInfo billMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		//��������
		m_sbSelect.append(" distinct sappr.officeid ,sappr.currencyid ,sappr.moduleid ,sappr.transtypeid ,sappr.actionid ,sappr.transid ,sappr.linkurl ,sappr.statusid ,sappr.nextlevel ,");
		//���ױ�
		m_sbSelect.append(" btran.id DTransID");
		//m_sbSelect.append(" ,his.stepname stepName,his.modelname wfDefineName");
		//FROM
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SYS_APPROVALRECORD sappr , bill_transdraftout btran");
		//m_sbFrom.append(" , v_os_histroystep his");
		m_sbWhere = new StringBuffer();
		//����
		m_sbWhere.append(" sappr.transid = btran.id");
		//m_sbWhere.append(" and his.entry_id = sappr.approvalentryid");
		//������¼����Ϣ
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID());
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DraftOut);
		//m_sbWhere.append(" and sappr.lastappuserid = " + billMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.INVALID);		
		//���ױ���Ϣ
		//m_sbWhere.append(" and btran.inputuserid = " + billMyWorkInfo.getInputUserID());
		m_sbWhere.append(" and btran.statusid = " + BILLConstant.TransctionStatus.SUBMIT);
		//��ʷ���ױ�
		//m_sbWhere.append(" and his.stepname='�������'");
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = billMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = billMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				default:
					m_sbOrderBy.append(" \n order by DTransID desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by DTransID desc" );
		}
		
	}
	//���ύҵ��
	protected Collection queryWaitDealWithWork(BillMyWorkInfo info) throws IException {
		// TODO Auto-generated method stub
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select draftout.*");
		sbSQL.append(" from BILL_TRANSDRAFTOUT draftout,");
		sbSQL.append(" LOAN_DISCOUNTCONTRACTBILL bill," );
		sbSQL.append(" BILL_TRANSDRAFTIN draftin");
		sbSQL.append(" where bill.ID = draftout.BILLID");		
		sbSQL.append(" and draftin.ID = draftout.TRANSDRAFTINID");			
		sbSQL.append(" and draftout.INPUTUSERID = " + info.getUserID());
		sbSQL.append(" and draftout.OFFICEID = " + info.getOfficeID());
		sbSQL.append(" and draftout.CURRENCYID = " + info.getCurrencyID());
		sbSQL.append(" and bill.NSTATUSID = " + Constant.RecordStatus.VALID);
		sbSQL.append(" and draftout.STATUSID = " + BILLConstant.TransctionStatus.SUBMIT);		
		sbSQL.append(" and draftin.STATUSID = " + Constant.RecordStatus.VALID);
		sbSQL.append(" order by bill.SCODE ASC");
		
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			
			while (transRS.next()) {
				TransDraftOutInfo oInfo= new TransDraftOutInfo();
				oInfo.setId(transRS.getLong("ID"));
				oInfo.setOfficeID(transRS.getLong("OFFICEID"));
				oInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
				oInfo.setTransCode(transRS.getString("TRANSCODE"));
				oInfo.setTransTypeID(transRS.getLong("TRANSTYPEID"));
				oInfo.setBillDestinationID(transRS.getLong("BILLDESTINATIONID"));
				oInfo.setBillID(transRS.getLong("BILLID"));
				oInfo.setOutDate(transRS.getTimestamp("OUTDATE"));
				oInfo.setNextHolder(transRS.getString("NEXTHOLDER"));
				oInfo.setOutContractCode(transRS.getString("OUTCONTRACTCODE"));
				oInfo.setOutPayFormCode(transRS.getString("OUTPAYFORMCODE"));
				oInfo.setOutOnWayDays(transRS.getLong("OUTONWAYDAYS"));
				oInfo.setOutRate(transRS.getDouble("OUTRATE"));
				oInfo.setOutInterest(transRS.getDouble("OUTINTEREST"));
				oInfo.setRepurchaseEndDate(transRS.getTimestamp("REPURCHASEENDDATE"));
				oInfo.setSumRepurchaseInterest(transRS.getDouble("SUMREPURCHASEINTEREST"));
				oInfo.setAbstract(transRS.getString("ABSTRACT"));
				oInfo.setTransDraftInID(transRS.getLong("TRANSDRAFTINID"));
				oInfo.setInputUserID(transRS.getLong("INPUTUSERID"));
				oInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
				oInfo.setModifyUserID(transRS.getLong("MODIFYUSERID"));
				oInfo.setModifyDate(transRS.getTimestamp("MODIFYDATE"));
				oInfo.setStatusID(transRS.getLong("STATUSID"));
				
				v_Return.add(oInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	//�Ѱ�ҵ��
	protected void getFinishedWorkSql(BillMyWorkInfo billMyWorkInfo) {
		// TODO Auto-generated method stub
		m_sbSelect = new StringBuffer();
	
		m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,his.owner,his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,sappr.*, draftOut.id dTransID \n ");
		// from
		m_sbFrom = new StringBuffer();
		//m_sbFrom.append("  v_os_histroystep his,(select * from BILL_TRANSDRAFTOUT o where o.STATUSID in ( "+ BILLConstant.TransctionStatus.APPROVALED+","+BILLConstant.TransctionStatus.SUBMIT+",0)) draftOut,SYS_APPROVALRECORD sappr \n");
		m_sbFrom.append("  v_os_histroystep his,BILL_TRANSDRAFTOUT draftOut,SYS_APPROVALRECORD sappr \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = sappr.approvalentryid \n");
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID() +"\n");
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID()+"\n");
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and sappr.transid = draftOut.id" + " \n");
		m_sbWhere.append(" and his.owner = '"+billMyWorkInfo.getUserID()+"' \n");
		
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT + " \n");
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DraftOut+" \n");
		//m_sbWhere.append(" and sappr.approvalentryid in (" + billMyWorkInfo.getApprovalEntryIDs() + ")");


		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by sappr.id desc" );
	}
	//ȡ������
	protected void getCancelApprovalSql(BillMyWorkInfo billMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		//��������
		m_sbSelect.append(" sappr.*,");
		//���ױ�
		//m_sbSelect.append(" btran.*");
		m_sbSelect.append(" btran.id dTransID");
		//��ʷ��
		//m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SYS_APPROVALRECORD sappr,Bill_TransDraftOut btran");
		
		m_sbWhere = new StringBuffer();
		//����
		m_sbWhere.append(" sappr.transid = btran.id");
		//m_sbWhere.append(" and his.entry_id = sappr.approvalentryid");
		//������¼����Ϣ
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID());
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DraftOut);
		m_sbWhere.append(" and sappr.lastappuserid = " + billMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.VALID);		
		//���ױ���Ϣ
		m_sbWhere.append(" and btran.STATUSID = " + BILLConstant.TransctionStatus.APPROVALED);
		//��ʷ���ױ�
		//m_sbWhere.append(" and his.stepname='�������'");
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = billMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = billMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				default:
					m_sbOrderBy.append(" \n order by sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by sappr.approvalentryid desc" );
		}
	}
	/**�Ѱ�ҵ��
	protected Collection queryHistoryWork(BillMyWorkInfo billMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select ar.*, vt.*");
		sbSQL.append(" from sys_approvalrecord ar,");
		sbSQL.append(" (" );
		sbSQL.append(" select");
		sbSQL.append(" lb.id transid,");		//
		sbSQL.append(" lb.susername,");			//
		sbSQL.append(" lb.sbank,");				//
		sbSQL.append(" lb.nislocal,");			//
		sbSQL.append(" lb.dtcreate,");			// ��Ʊ��
		sbSQL.append(" lb.dtend,");				// ������
		sbSQL.append(" lb.scode,");				// Ʊ�ݺ�
		sbSQL.append(" lb.mamount,");			// Ʊ����
		sbSQL.append(" lb.nmodulesourceid,");	// ģ��id
		sbSQL.append(" lb.ndrafttypeid,");		// ��Ʊ����
		sbSQL.append(" lb.stracceptorname,");   // �ж���
		sbSQL.append(" lb.stracceptorbank,");	// �ж���
		sbSQL.append(" lb.stracceptoraccount,");// �ж��ʺ�
		sbSQL.append(" lb.nstoragestatusid,");	// Ʊ�ݵ�ǰ�����ĳ������״̬
		sbSQL.append(" lb.nstoragetransid,");	// Ʊ�ݵ�ǰ�����������״̬��Ӧ������⽻��id
		sbSQL.append(" lb.nquerystatusid,");	// Ʊ�ݲ�ѯ�鸴״̬
		sbSQL.append(" lb.nconsignstatusid,");	// Ʊ������״̬
		sbSQL.append(" lb.nformerconstatusid,");// ���ڼ�¼�޸�����״̬֮ǰ������״̬ 
		sbSQL.append(" lb.nconsigntime,");		// Ʊ�����մ���
		sbSQL.append(" lb.ninputuserid,");		// Ʊ����Ϣ¼����
		sbSQL.append(" lb.dtinputdate,");		// Ʊ����Ϣ¼������
		sbSQL.append(" lb.nmodifyuserid,");		// Ʊ����Ϣ�޸���
		sbSQL.append(" lb.dtmodifydate,");		// Ʊ����Ϣ�޸�����
		sbSQL.append(" lb.ncheckstatus,");		// ����״̬
		sbSQL.append(" lb.scheckcode,");		// ������
		sbSQL.append(" lb.dtcheckdate,");		// �������
		sbSQL.append(" lb.dtcanceldate,");		// ��������
		sbSQL.append(" lb.nofficeid,");			// 
		sbSQL.append(" lb.ncurrencyid,");		//
		sbSQL.append(" bo.id,");				// 
		sbSQL.append(" bo.transcode,");			// ���ױ��
		sbSQL.append(" bo.transtypeid,");		// ��������
		sbSQL.append(" bo.billdestinationid,");	// Ʊ��ȥ��
		sbSQL.append(" bo.billid,");			// ���Ʊ��id
		sbSQL.append(" bo.outdate,");			// ��������
		sbSQL.append(" bo.nextholder,");		// ���� 
		sbSQL.append(" bo.outcontractcode,");	// ����ʱ�ĺ�ͬ��� 
		sbSQL.append(" bo.outpayformcode,");	// �����ſ�֪ͨ��
		sbSQL.append(" bo.outrate,");			// ������������ 
		sbSQL.append(" bo.outinterest,");		// ����������Ϣ 
		sbSQL.append(" bo.repurchaseenddate,");	// �ع���������
		sbSQL.append(" bo.sumrepurchaseinterest,");// �ۼƻع���Ϣ
		sbSQL.append(" bo.inputuserid,");// ¼����
		sbSQL.append(" bo.inputdate,"); //	¼������
		sbSQL.append(" bo.modifyuserid,");// �޸��� 
		sbSQL.append(" bo.modifydate,");// �޸�����
		sbSQL.append(" bo.statusid");// ����״̬
		sbSQL.append(" from LOAN_DISCOUNTCONTRACTBILL lb, Bill_TransDraftOut bo");
		sbSQL.append(" where lb.id = bo.billid");
		sbSQL.append(" and lb.nstatusid = 1");
		sbSQL.append(" and bo.statusid = " + BILLConstant.TransctionStatus.APPROVALED);
		sbSQL.append(" ) vt");
		
		sbSQL.append(" where ar.moduleid = " + billMyWorkInfo.getModuleID());
		sbSQL.append(" and ar.transid = vt.id");
		sbSQL.append(" and ar.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		sbSQL.append(" and ar.actionid = " + BILLConstant.DraftOperationType.DraftOut);
		sbSQL.append(" and ar.approvalentryid in (" + billMyWorkInfo.getApprovalEntryIDs() + ")");
		
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				BillMyWorkInfo workInfo = new BillMyWorkInfo();

				//����������Ϣ
				workInfo.setId(transRS.getLong("id"));
				workInfo.setOfficeID(transRS.getLong("nOfficeid"));
				workInfo.setCurrencyID(transRS.getLong("nCurrencyid"));
				//workInfo.setActionID(transRS.getLong("actionId"));
				//workInfo.setActionName(transRS.getString("transactionTypeName"));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setUserID(billMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("statusId"));
				
				//Ʊ��ҵ����Ϣ
				workInfo.setBillId(transRS.getLong("transid"));
				workInfo.setBillCode(transRS.getString("scode"));
				workInfo.setTranscode(transRS.getString("transcode"));
				//workInfo.setClientName(transRS.getString("clientName"));
				workInfo.setBusinessTypeId(transRS.getLong("transtypeid"));
				workInfo.setBusinessTypeName(BILLConstant.TraceModule.getName(transRS.getLong("transtypeid")));
				workInfo.setTransactionTypeId(transRS.getLong("transtypeid"));
				workInfo.setTranscode(transRS.getString("transcode"));
				workInfo.setActionName(BILLConstant.DraftOperationType.getName(transRS.getLong("actionid")));
				workInfo.setStracceptoraccount(transRS.getString("stracceptoraccount"));
				workInfo.setInputUserID(transRS.getLong("inputUserID"));
				//workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));

				//��Ӧ������������Ĵ�����Ϣ
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)billMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	*/
}
