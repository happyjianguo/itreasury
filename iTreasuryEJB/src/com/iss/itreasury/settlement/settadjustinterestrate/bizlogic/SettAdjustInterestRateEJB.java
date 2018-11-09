/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settadjustinterestrate.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settadjustinterestrate.dao.Sett_RateAdjustPayConditionDAO;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestConditionInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettAdjustInterestQueryInfo;
import com.iss.itreasury.settlement.settadjustinterestrate.dataentity.SettRateAdjustPayConditionInfo;
import com.iss.itreasury.settlement.settpaynotice.dao.SettPayNoticeDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;

/**
 * @author jinchen
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettAdjustInterestRateEJB implements SessionBean
{
    private javax.ejb.SessionContext mySessionCtx = null;

    final static long serialVersionUID = 3212393123460846163L;

    /**
     * ejbActivate method comment
     * 
     * @exception java.rmi.RemoteException
     *                �쳣˵����
     */
    public void ejbActivate() throws java.rmi.RemoteException
    {
    }

    /**
     * ejbCreate method comment
     * 
     * @exception javax.ejb.CreateException
     *                �쳣˵����
     * @exception java.rmi.RemoteException
     *                �쳣˵����
     */
    public void ejbCreate() throws javax.ejb.CreateException,
            java.rmi.RemoteException
    {
    }

    /**
     * ejbPassivate method comment
     * 
     * @exception java.rmi.RemoteException
     *                �쳣˵����
     */
    public void ejbPassivate() throws java.rmi.RemoteException
    {
    }

    /**
     * ejbRemove method comment
     * 
     * @exception java.rmi.RemoteException
     *                �쳣˵����
     */
    public void ejbRemove() throws java.rmi.RemoteException
    {
    }

    /**
     * getSessionContext method comment
     * 
     * @return javax.ejb.SessionContext
     */
    public javax.ejb.SessionContext getSessionContext()
    {
        return mySessionCtx;
    }

    /**
     * setSessionContext method comment
     * 
     * @param ctx
     *            javax.ejb.SessionContext
     * @exception java.rmi.RemoteException
     *                �쳣˵����
     */
    public void setSessionContext(javax.ejb.SessionContext ctx)
            throws java.rmi.RemoteException
    {
        mySessionCtx = ctx;
    }

    /**
     * updateInterestRate ��ͨ�������������޸� �ύ���������޸����� ���� ContractRateSetting ���ݱ�
     * �ύ���������޸����� �����޸ĵĺ�ͬ�Ĳ�ѯ����д����� lID = 0 ,�����������޸ļ�¼
     * 
     * @param lID
     *            long �����޸�������ʾ
     * @param lLoanInterestRateID
     *            long �������ʱ�ʾ
     * @param lContractID
     *            long ��ͬ��ʾ
     * @param lLetoutRequisitionID
     *            long �ſ�֪ͨ����ʾ
     * @param dRate
     *            double ����ֵ
     * @param tsValidate
     *            Timestamp ��Ч��
     * @param strReason
     *            String ����ԭ��
     * @param lCurrencyID
     *            long ����
     * @param lOfficeID
     *            long ���´�ID
     * @param lInputUserID
     *            long ¼����ID
     * @param tsInputDate
     *            Timestamp ¼������
     * @param ���ϲ���ȫ����װ����
     *            SettAdjustInterestSaveInfo
     * @return long �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     * @throws SettlementException
     */
    public long saveAdjustInterestRate(SettRateAdjustPayConditionInfo info)
            throws SettlementException, RemoteException
    {
        /*
         * long lID = -1; long lLoanInterestRateID = -1; long lContractID = -1;
         * long lLetoutRequisitionID = -1; double dRate = 0.0; double
         * dAdjustRate = 0.0; double dStaidAdjustRate = 0.0; Timestamp
         * tsValidate = null; String strReason = null; long lCurrencyID = -1;
         * long lOfficeID = -1; long lInputUserID = -1; Timestamp tsInputDate =
         * null;
         */

        String strErrorMessage = null;
        long lID = -1;
        long lStatusID = -1;
        long lSaveId = -1;
        lID = info.getId();

        strErrorMessage = this.checkAddValidity(info);
        //      �׳��Զ����쳣

        try
        {
            if (strErrorMessage != null && strErrorMessage.length() > 0)
            {
                throw new SettlementException("Sett_E180", strErrorMessage,
                        null);
            }
            Log.print("�������ʵ�������ͨ����֤...................................");

            if (lID <= 0)
            {
                lStatusID = SETTConstant.SettAdjustInterestStatus.SUBMIT;
                info.setStatusId(lStatusID);
                Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();

                lSaveId = dao.add(info);

            } else
            {
                lStatusID = SETTConstant.SettAdjustInterestStatus.SUBMIT;
                info.setStatusId(lStatusID);
                info.setNextCheckLevel(1);
                info.setNextCheckUserId(info.getInputUserId());
                lSaveId = info.getId();
                Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
                dao.update(info);

            }

        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lSaveId;
    }

    /**
     * findUpdateInterestRateByID ���������޸�������Ϣ �����������������޸�������Ϣ ����
     * ContractRateSetting ���ݱ� ��ѯ��Ӧ��¼
     * 
     * @param long
     *            lID �����޸�������ʾ
     * @return AdjustInterestConditionInfo
     * @throws RemoteException
     * @throws SettlementDAOException
     * @throws SettlementDAOException
     */
    public SettAdjustInterestConditionInfo findAdjustInterestRateByID(long id)
            throws java.rmi.RemoteException
    //SettlementException
    {

        SettAdjustInterestConditionInfo settAdjustInterestConditionInfo = null;

        Sett_RateAdjustPayConditionDAO sett_RateAdjustPayConditionDAO = new Sett_RateAdjustPayConditionDAO();
        try
        {
            settAdjustInterestConditionInfo = sett_RateAdjustPayConditionDAO
                    .findAdjustInterestRateByID(id);
        } catch (SettlementDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (settAdjustInterestConditionInfo != null)
        {
            Timestamp tsTemp = DataFormat
                    .getPreviousDate(settAdjustInterestConditionInfo
                            .getValidate());

            if (settAdjustInterestConditionInfo.getLoanPayNoticeID() > 0)
            {

                SettPayNoticeDAO settPayNoticeDao = new SettPayNoticeDAO();
                try
                {
                    settAdjustInterestConditionInfo
                            .setFInterestRate(settPayNoticeDao.getLatelyRate(
                                    settAdjustInterestConditionInfo
                                            .getLoanPayNoticeID(), tsTemp));
                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
            } else
            {
                //SettContractDAO settContractDAO = new
                // SettContractDAO("LOAN_CONTRACTFORM");

                // rateInfo =
                // sett_RateAdjustPayConditionDAO.getLatelyRate((long)-1,settAdjustInterestConditionInfo.getContractID(),tsTemp);

                try
                {
                    settAdjustInterestConditionInfo
                            .setFInterestRate(UtilOperation
                                    .getLoanInterestRate(null, (long) -1,
                                            settAdjustInterestConditionInfo
                                                    .getContractID(), tsTemp));
                } catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return settAdjustInterestConditionInfo;
    }

    /**
     * findUpdateInterestByMultiOption ����Ӧ���˵������޸� ������������Ӧ�ø��˵������޸� ���� Contract
     * RateSetting ���ݱ� ��ѯ��Ӧ��¼
     * 
     * @param long
     *            lActionID ������ʾ1���޸Ĳ�ѯ��2�����˲�ѯ
     * @param Timestamp
     *            tsStartDate �����޸Ŀ�ʼ��
     * @param Timestamp
     *            tsEndDate �����޸Ľ�����
     * @param long
     *            lStatusID �����޸�״̬
     * @param long
     *            lCurrencyID ����
     * @param long
     *            lOfficeID ���´�ID
     * @param long
     *            lUserID ������ID
     * @param long
     *            lPageLineCount ÿҳҳ��������
     * @param long
     *            lPageNo �ڼ�ҳ����
     * @param long
     *            lOrderParam �������������ݴ˲��������������������
     * @param long
     *            lDesc �������
     * @return Collection �����
     * @throws RemoteException
     */
    public Collection findAdjustInterestByMultiOption(
            SettAdjustInterestQueryInfo info) throws java.rmi.RemoteException
    {

        Collection c = null;
        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        try
        {
            c = dao.findAdjustInterestByMultiOption(info);
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }

        return c;
    }

    /**
     * �����������
     * <p>
     * <b>&nbsp; </b>
     * <ol>
     * <b>����������� </b>
     * <ul>
     * <li>�������ݿ��loan_approvalTracing,loan_rateadjustpaycondition
     * <li>������������Ǿܾ����޸Ĵ�����������״̬
     * </ul>
     * </ol>
     * 
     * @Copyright (c) Jan. 2003, by iSoftStone Inc. All Rights Reserved
     * @param long
     *            nReviewTypeID ��������
     * @param long
     *            nReviewContentID ������������
     * @param String
     *            sOpinion �������
     * @param long
     *            nUserID �����˱�ʾ
     * @param long
     *            nNextUserID ��һ�������˱�ʾ
     * @param long
     *            action �������ܾ����޸ģ��������
     * @return long �ɹ�������ֵ == 1��ʧ�ܣ�����ֵ == -1
     * @exception Exception
     */
    public long checkAdjustInterestRate(long lApprovalContentID,
            String sOpinion, long lUserID, long lNextUserID, long lAction)
            throws RemoteException
    {
        return -1;
    }

    /**
     * cancelUpdateInterestRate ȡ����ͨ�������������޸� ȡ����ͨ�������������޸� ���� AdjustedContract
     * ���ݱ� ������Ӧ��¼ Ӧ���ȼ�����״̬
     * 
     * @param lAdjustConditionID :
     *            ContractRateSetting.ID
     * @return long �ɹ�����ID��ʶ��ʧ�ܷ���0
     * @throws RemoteException
     */
    public long cancelAdjustInterestRate(long lAdjustConditionID)
            throws java.rmi.RemoteException
    {
        //      Connection con = null;
        //		PreparedStatement ps = null;
        //		ResultSet rs = null;
        String strSQL = null;
        long lResult = -1;

        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        //		//������Ӧ��������
        //		//����
        //		long lModuleID = Constant.ModuleType.LOAN;
        //		//ģ������
        //		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
        //		long lActionID = Constant.ApprovalAction.INTEREST_ADJUST;
        //
        //
        //		long lApprovalID = -1;
        //		long lContractID = -1;
        //		long lLoanPayNoticeID = -1;
        //		ApprovalBiz appbiz = new ApprovalBiz();
        /*
         * try { //���ApprovalID lApprovalID =
         * getApprovalID(lModuleID,lLoanTypeID,lActionID); //�������ݿ� con =
         * Database.getConnection(); //��ɾ����ǰ����˼�¼������ɾ����
         */
        SettRateAdjustPayConditionInfo info = new SettRateAdjustPayConditionInfo();
        info.setId(lAdjustConditionID);
        //		��loan_rateadjustpaycondition��װ̬��Ϊ��Ч
        info.setStatusId(Constant.RecordStatus.INVALID);

        try
        {
            dao.update(info);
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            dao.cancelAdjustInterestRate(lAdjustConditionID);
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lResult;
    }

    /**
     * ����������ʵ�����Ϣ���ݵ���Ч�ԡ������Ч������Ч
     * 
     * @param info
     * @return �����Ч������Чԭ��. ��Ч����null
     * @throws SettlementException
     */
    private String checkAddValidity(SettRateAdjustPayConditionInfo info)
            throws RemoteException
    {
        String strErrorMessage = null;
        Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
        try
        {
            if (dao.findRateAdjustPayConditionByCondition(info))
            {
                strErrorMessage = "������ͬ�����ʵ�����¼�����������룡";
            }
        } catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }
        return strErrorMessage;
    }

    /**
     * �������ʵ�������
     * 
     * @param lID
     * @param userID
     * @return @throws
     *         java.rmi.RemoteException
     */
    public long checkAdjustInterestRate(long lID, long userID)
            throws java.rmi.RemoteException
    {
        try
        {
            Sett_RateAdjustPayConditionDAO dao = new Sett_RateAdjustPayConditionDAO();
            SettRateAdjustPayConditionInfo info = new SettRateAdjustPayConditionInfo();
            info.setId(lID);
            info.setNextCheckUserId(userID);
            info.setStatusId(SETTConstant.SettAdjustInterestStatus.CHECK);

            dao.update(info);

        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            throw new java.rmi.RemoteException();
        }
        return lID;
    }

}