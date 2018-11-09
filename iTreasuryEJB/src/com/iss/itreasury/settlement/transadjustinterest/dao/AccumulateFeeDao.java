/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transadjustinterest.dao;

import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfoQuery;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfo;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.DataFormat;

import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author feiye
 * 
 * �˻�������ϢDAO
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccumulateFeeDao extends SettlementDAO{
	    
	 /**
     * ���ҵ�ǰ��Ϣ
     * @param lOfficeID ���´���ʶ
     * @param lCurrencyID ���ֱ�ʶ
     * @param strAccountNoStart ��ʼ�˻���
     * @param strAccountNoEnd �����˻���
     * @param strContractNoStart ��ʼ��ͬ��
     * @param strContractNoEnd ������ͬ��
     * @param strDueBillCodeStart ��ʼ�ſ�֪ͨ����
     * @param strDueBillCodeEnd �����ſ�֪ͨ����
     * @param strFixedDepositNoStart ��ʼ���ڴ浥��
     * @param strFixedDepositNoEnd �������ڴ浥��
     * @param tsDateStart ��ʼ����
     * @param tsDateEnd ��������
     * @param lPageLineCount,
     * @param lPageNo,
     * @param lOrderParam,
     * @param lDesc 
     */
   
	/**
     * ����ģ��--����(�ۼƷ��ü���Ϣ����)������Ϣ(���ұ�Sett_SubAccount ,�����ж�����)
     * @param AccumulateFeeInfoQuery 
     */
	//��Ҫ��һ�£��Ƿ�Ҫ���ϴ������ID��ֵ
    public Collection findForm(AccumulateFeeInfoQuery queryCondition) throws SettlementDAOException,ITreasuryDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        Vector v = new Vector();
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        int nIndex = 1;
        try
        {
        	//�õ�����
            conn = this.getConnection();
            
            //����ȡ����
            strSQL=" select b.id subAccountID,a.Id nAccountID,a.sAccountNO sAccountNo,c.id nDueBillNo, "
            	+"  c.sCode sLetoutRequisitionNo,e.id nContractID,e.sContractCode sContractNo "
            	+" ,d.sName sName ,b.MINTEREST mInterest,b.AL_mCommission fCommissionFee, "
            	+" b.AL_mSuretyFee fSuretyFee,b.AL_mInterestTax fInterestTaxFee , a.nAccountTypeID accountTypeID" 
            	+" from sett_Account a,sett_subAccount b,loan_payForm c,sett_interestRatePlan d, loan_contractform e , sett_accountType f"
            	+" where 1=1 "
            	+" and b.nAccountID=a.id "
            	+" and b.AF_nInterestPlanID=d.id(+) "
            	+" and b.AL_nLoanNoteID=c.id (+) "
            	+" and c.ncontractid = e.id(+) "
				+" and a.nAccountTypeID=f.id "		//�˻��������
            	+" and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
            	//�˻���Ϊ���ںͶ�����������
            	+"  and a.nStatusID>0 "
            	+"  and b.nStatusID>0 "
            
            	+" and a.NOFFICEID = " + queryCondition.getLOfficeID()	//���´�ID
				+" and a.NCURRENCYID = "+ queryCondition.getLCurrencyID() ;	//����ID
            
            	System.out.println("�õ��İ��´�IDΪ:"+queryCondition.getLOfficeID());
            	System.out.println("�õ��ı���IDΪ:"+queryCondition.getLOfficeID());
            	
            //�˻���
            if (queryCondition.getSAccountNoStartCtrl() != null && queryCondition.getSAccountNoStartCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo >= '" + queryCondition.getSAccountNoStartCtrl() + "'";
            }
            if (queryCondition.getSAccountNoEndCtrl() != null && queryCondition.getSAccountNoEndCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo <= '" + queryCondition.getSAccountNoEndCtrl() + "'";
            }
            //��ͬ��(e)		(���ںͻ����޺�ͬ������)
            if (queryCondition.getSContractNoStartCtrl() != null && queryCondition.getSContractNoStartCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode >= '" + queryCondition.getSContractNoStartCtrl() + "'";
            }
            if (queryCondition.getSContractNoEndCtrl() != null && queryCondition.getSContractNoEndCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode <= '" + queryCondition.getSContractNoEndCtrl() + "'";
            }
            //�ſ�֪ͨ����(c)		(���ںͻ����޺�ͬ������)
            if (!queryCondition.getSDueBillCodeStartCtrl().equals(""))	
            {
                //strSQL = strSQL + " and c.sCode>='" + queryCondition.getSDueBillCodeStartCtrl() + "'";
            }
            if (!queryCondition.getSDueBillCodeEndCtrl().equals(""))
            {
                //strSQL = strSQL + " and c.sCode<='" + queryCondition.getSDueBillCodeEndCtrl() + "'";
            }
          
            ps = conn.prepareStatement(strSQL);
            
    		Log.print("findForm SQL : " + strSQL);
            rs = ps.executeQuery();
            Log.print("!!!!!!!!!!!!!!!!!! after execute query!!!!!!!!!!!!!!!!!!!!!");
            while (rs.next())		//����ѯ�õ��Ľ����װ��AccumulateFeeInfo��Ϣ���У��Խ�ҳ����ʾ��
            {
                AccumulateFeeInfo accumulateFeeInfo = new AccumulateFeeInfo();

                accumulateFeeInfo.setLID(rs.getLong("subAccountID"));			//���˻�ID
                Log.print("bbb");
                accumulateFeeInfo.setLAccountID(rs.getLong("nAccountID"));		//�˻�ID	
                accumulateFeeInfo.setLContractID(rs.getLong("nContractID"));	//��ͬID
                accumulateFeeInfo.setLDueBillID(rs.getLong("nDueBillNo"));		//�ſ�֪ͨ��ID
                Log.print("eee");
                //accumulateFeeInfo.m_lTypeID = rs.getLong("nTypeID");	//��֪���費��ҪҪ
                Log.print("fff");
                accumulateFeeInfo.setSAccountNo(rs.getString("sAccountNo"));		//�˻����
                accumulateFeeInfo.setSContractNo(rs.getString("sContractNo"));	//��ͬ��
    		    accumulateFeeInfo.setSLetoutRequisitionNo(rs.getString("sLetoutRequisitionNo"));	//�ſ�֪ͨ����	
                //accumulateFeeInfo.m_tsOpen = rs.getTimestamp("dtInfo");
    		    			//�õ����ڵ���һ��
    			//accumulateFeeInfo.m_tsOpen = Function.getPreviousDate(tsDateStart);		//��������
    		    accumulateFeeInfo.setTsOpen(DataFormat.getPreviousDate(queryCondition.getTsDateStart()));  //��������
    		    //accumulateFeeInfo.setTsOpen(rs.getTimestamp("clearDate"));		//��������

    		    accumulateFeeInfo.setDInterest(rs.getDouble("mInterest"));				//�ۼ���Ϣ
                accumulateFeeInfo.setSInterestRatePlanName(rs.getString("sName"));		//��Ϣ�ƻ�
                accumulateFeeInfo.setDCommission(rs.getDouble("fCommissionFee"));	//�ۼ�������
                accumulateFeeInfo.setDSuretyFee(rs.getDouble("fSuretyFee"));			//�ۼƵ�����
                accumulateFeeInfo.setDInterestTax(rs.getDouble("fInterestTaxFee"));	//�ۼ���Ϣ˰��
                
                //�õ��������״̬
                accumulateFeeInfo.setLDepositTypeID(rs.getLong("accountTypeID"));		//��sett_account����ȡ�ֶ�nAccountTypeID��ֵ
                System.out.println("===�õ��˻�����Ϊ:"+accumulateFeeInfo.getLDepositTypeID());
                //��ӵ�������
                v.add(accumulateFeeInfo);
            }
        }
        catch(SQLException sqle){
        	Log.print("������SQL�쳣");
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return (v.size() > 0 ? v : null);
    }
    
    /**
     * ����ģ��--����(�ۼƷ��ü���Ϣ����)��ʷ��Ϣ(���ұ�Sett_DailyAccount��Ҫ�ж�ʱ��)
     * @param AccumulateFeeInfoQuery 
     */
    public Collection findFormHistory(AccumulateFeeInfoQuery queryCondition) throws SettlementDAOException,ITreasuryDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        Vector v = new Vector();
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        int nIndex = 1;
        try
        {
        	//�õ�����
            conn = this.getConnection();
            //Ҫȡ����   ���õ����˻�ID�Թ��պ�ֻ��Ҫ���ݴ����޸����˻����˻�ÿ�����ĵ���ֵ��¼��
            strSQL=" select b.nSubAccountID subAccountID,a.Id nAccountID,a.sAccountNO sAccountNo"
            +",-1 nDueBillNo, '' sLetoutRequisitionNo,-1 nContractID,'' sContractNo"
            +",d.sName sName, a.nAccountTypeID accountTypeID ,b.dtDate dtDate"
            +", b.MINTEREST mInterest"
            +",0 fCommissionFee"
            +",0 fSuretyFee"
            +",0 fInterestTaxFee"
            +" from   sett_Account a,SETT_DAILYACCOUNTBALANCE b,sett_subAccount c,sett_interestRatePlan d ,sett_accountType f"
            +" where 1=1 "
            +" and b.nAccountID=a.id "
            +" and b.nSubAccountID=c.id "
            +" and c.AF_nInterestPlanID=d.id(+) "
            +" and a.nAccountTypeID=f.id " 		//�˻��������						//���ںͻ���
            +" and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
            +" and b.nAccountStatusID >0 "
            +" and b.nSubAccountStatusID >0 "
			
            +" and a.NOFFICEID = " + queryCondition.getLOfficeID()			//���´�ID
            +" and a.NCURRENCYID = "+ queryCondition.getLCurrencyID() ;		//����ID
            
            System.out.println("�õ��İ��´�IDΪ:"+queryCondition.getLOfficeID());
            System.out.println("�õ��ı���IDΪ:"+queryCondition.getLOfficeID());
            
            //�˻���
            if (queryCondition.getSAccountNoStartCtrl() != null && queryCondition.getSAccountNoStartCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo >= '" + queryCondition.getSAccountNoStartCtrl() + "'";
            }
            if (queryCondition.getSAccountNoEndCtrl() != null && queryCondition.getSAccountNoEndCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo <= '" + queryCondition.getSAccountNoEndCtrl() + "'";
            }
            
            //��ͬ��(��û���� e)	���ںͻ��ڲ�������
            if (queryCondition.getSContractNoStartCtrl() != null && queryCondition.getSContractNoStartCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode >= '" + queryCondition.getSContractNoStartCtrl() + "'";
            }
            if (queryCondition.getSContractNoEndCtrl() != null && queryCondition.getSContractNoEndCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode <= '" + queryCondition.getSContractNoEndCtrl() + "'";
            }
            //�ſ�֪ͨ����(c)			���ںͻ��ڲ�������
            if (!queryCondition.getSDueBillCodeStartCtrl().equals(""))	
            {
                //strSQL = strSQL + " and c.sCode>='" + queryCondition.getSDueBillCodeStartCtrl() + "'";
            }
            if (!queryCondition.getSDueBillCodeEndCtrl().equals(""))
            {
                //strSQL = strSQL + " and c.sCode<='" + queryCondition.getSDueBillCodeEndCtrl() + "'";
            }
            
            //��Ϣ����
            if (queryCondition.getTsDateStart()!=null)	
            {
                strSQL = strSQL + " and b.dtDate >=  to_date('" + DataFormat.getDateString( queryCondition.getTsDateStart() ) + "','yyyy-mm-dd')";
            }
            if (queryCondition.getTsDateEnd()!=null)	
            {
                strSQL = strSQL + " and b.dtDate <= to_date('" + DataFormat.getDateString( queryCondition.getTsDateEnd() ) + "','yyyy-mm-dd')";
            }
            
            //to_date('2005-09-08','yyyy-mm-dd');
          
            ps = conn.prepareStatement(strSQL);
            
    		Log.print("findForm SQL : " + strSQL);
            rs = ps.executeQuery();
            Log.print("!!!!!!!!!!!!!!!!!! after execute query!!!!!!!!!!!!!!!!!!!!!");
            while (rs.next())		//����ѯ�õ��Ľ����װ��AccumulateFeeInfo��Ϣ���У��Խ�ҳ����ʾ��
            {
                AccumulateFeeInfo accumulateFeeInfo = new AccumulateFeeInfo();

                accumulateFeeInfo.setLID(rs.getLong("subAccountID"));			//���˻�ID
                Log.print("bbb");
                accumulateFeeInfo.setLAccountID(rs.getLong("nAccountID"));		//�˻�ID	
                accumulateFeeInfo.setLContractID(rs.getLong("nContractID"));	//��ͬID
                accumulateFeeInfo.setLDueBillID(rs.getLong("nDueBillNo"));		//�ſ�֪ͨ��ID
                Log.print("eee");
                //accumulateFeeInfo.m_lTypeID = rs.getLong("nTypeID");	//��֪���費��ҪҪ
                Log.print("fff");
                accumulateFeeInfo.setSAccountNo(rs.getString("sAccountNo"));		//�˻����
                accumulateFeeInfo.setSContractNo(rs.getString("sContractNo"));	//��ͬ��
    		    accumulateFeeInfo.setSLetoutRequisitionNo(rs.getString("sLetoutRequisitionNo"));	//�ſ�֪ͨ����	
                //accumulateFeeInfo.m_tsOpen = rs.getTimestamp("dtInfo");
    		    			//�õ����ڵ���һ��
    			//accumulateFeeInfo.m_tsOpen = Function.getPreviousDate(tsDateStart);		//��������
    		    //accumulateFeeInfo.setTsOpen(DataFormat.getNextDate(queryCondition.getTsDateStart()));		//��������
    		    accumulateFeeInfo.setTsOpen(rs.getTimestamp("dtDate"));		//��������

    		    accumulateFeeInfo.setDInterest(rs.getDouble("mInterest"));				//�ۼ���Ϣ
                accumulateFeeInfo.setSInterestRatePlanName(rs.getString("sName"));		//��Ϣ�ƻ�
                accumulateFeeInfo.setDCommission(rs.getDouble("fCommissionFee"));	//�ۼ�������
                accumulateFeeInfo.setDSuretyFee(rs.getDouble("fSuretyFee"));			//�ۼƵ�����
                accumulateFeeInfo.setDInterestTax(rs.getDouble("fInterestTaxFee"));	//�ۼ���Ϣ˰��
                
                //�õ��������״̬
                accumulateFeeInfo.setLDepositTypeID(rs.getLong("accountTypeID"));		//��sett_account����ȡ�ֶ�nAccountTypeID��ֵ
                System.out.println("===�õ��˻�����Ϊ:"+accumulateFeeInfo.getLDepositTypeID());
                //��ӵ�������
                v.add(accumulateFeeInfo);
            }
        }
        catch(SQLException sqle){
        	Log.print("������SQL�쳣");
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return (v.size() > 0 ? v : null);
    }
    
    //�����˻�ÿ����������)
    public long updateDailyAccountBlance(AdjustInterestInfo adjustInfo) throws SettlementDAOException,ITreasuryDAOException{
    	long ret = 0;	//-1:ʧ��  0:����
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //��ѯ��
		ResultSet rs = null;
		
		double interestValue=0;	//��Ϣ����ֵ
		if(adjustInfo.getLAddOrReduce()==1)	//��ֵ
			interestValue=adjustInfo.getDInterest();
		else
			interestValue=adjustInfo.getDInterest()*(-1);
		
		try
		{
			System.out.println("*****************�����û��Ҫ����µ���Ϣ��ϸ��¼�ɹ�ʹ�ã�������)******************");
			//����SQL���
			buffer.append(" select * from SETT_DAILYACCOUNTBALANCE ");
			
			//����IDֵ
			buffer.append(" where  NSUBACCOUNTID = ? ");			//����IDֵ
			buffer.append(" and  nAccountStatusID > 0 ");		//�˻�״̬>0
			buffer.append(" and  nSubAccountStatusID > 0 ");	//���˻�״̬>0
			buffer.append(" and DTDATE >= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");

	        //��ʼ�����ݿ���Դ
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			//
			System.out.println("��Ҫ���µ����˻���ID��Ϊ:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(1, adjustInfo.getLSubAccountID());	//����IDֵ
			//��ӡSQL���
			System.out.println("select ->strSQL:"+buffer.toString());
			rs=pstmt.executeQuery();
			if(!rs.next()){
				//���û�в鵽��¼�����˳��˷���
				System.out.println("===========:û�в�����Ҫ�ĸ��µļ�¼!");
				ret = -100;
				return ret;
			}
			
			System.out.println("*****************��ʼ�����˻�ÿ����������)******************");
			//����SQL���
			buffer.setLength(0);
			buffer.append(" update SETT_DAILYACCOUNTBALANCE set \n");
			buffer.append(" MINTEREST = MINTEREST + ? "); 			//���µ��յ��ۼ������Ϣ
			
			//����IDֵ
			buffer.append(" where  NSUBACCOUNTID = ? ");			//����IDֵ
			buffer.append(" and  nAccountStatusID > 0 ");		//�˻�״̬>0
			buffer.append(" and  nSubAccountStatusID > 0 ");	//���˻�״̬>0
			buffer.append(" and DTDATE >= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");
			
	        //��ʼ�����ݿ���Դ
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//��ʼ����ֵ��SQL����
			int nIndex = 1; 
			//
			pstmt.setDouble(nIndex++, interestValue);	//��Ϣ����ֵ
			//
			System.out.println("��Ҫ���µ����˻���ID��Ϊ:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(nIndex++, adjustInfo.getLSubAccountID());	//����IDֵ
			//��ӡSQL���
			System.out.println("update->strSQL:"+buffer.toString());
			pstmt.executeUpdate();
			
			System.out.println("*****************���������˻�ÿ����������)*****************");
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		System.out.println("*****************��ʼ������õ�JDBC��Դ!*****************");
	    		this.cleanup(rs);
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
	}
    
    //�������˻�(������)
    //���������һ�����⣬���û������ʱ��Ĭ�ϵ��ǿ������ڣ���������������ں͵�������ڲ�һ��ʱ���û����뿪�������Ժ���������ǰ�����ھͲ�
    //Ӧ������������Ϣ����ֵ
    public long updateSubAccount(AdjustInterestInfo adjustInfo) throws SettlementDAOException,ITreasuryDAOException{
    	long ret = 0;	//-1:ʧ��  0:����
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //��ѯ��
		
		double interestValue=0;	//��Ϣ����ֵ
		if(adjustInfo.getLAddOrReduce()==1)	//��ֵ
			interestValue=adjustInfo.getDInterest();
		else
			interestValue=adjustInfo.getDInterest()*(-1);
		
		try
		{
			System.out.println("*****************��ʼ�������˻���Ϣ��������)******************");
			//����SQL���
			buffer.append(" update sett_subAccount set \n");
			buffer.append(" MINTEREST = MINTEREST + ? "); 			//���µ��յ��ۼ������Ϣ
			
			//����IDֵ
			buffer.append(" where  id = ? ");			//����IDֵ
			buffer.append(" and  NSTATUSID != 0 ");		//���˻�״̬>0
			
			//buffer.append(" and DTDATE <= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");
			
	        //��ʼ�����ݿ���Դ
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//��ʼ����ֵ��SQL����
			int nIndex = 1; 
			//
			pstmt.setDouble(nIndex++, interestValue);	//��Ϣ����ֵ
			//
			System.out.println("��Ҫ���µ����˻���ID��Ϊ:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(nIndex++, adjustInfo.getLSubAccountID());	//����IDֵ
			//��ӡSQL���
			System.out.println("update->strSQL:"+buffer.toString());
			pstmt.executeUpdate();
			
			System.out.println("*****************�������¸������˻���Ϣ��������)*****************");
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
    }
   
}