package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.interest.dao.Sett_AccountInterestCheckDAO;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.UpLoadInterestCheckInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class AccountInterestCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public  AccountInterestCheck()
	{
		// TODO Auto-generated method stub
	}
	
	/**
	 * ����˵������excel�ļ��е��������˻���Ϣ
	 *  Method importInterestInfo.
	 * @param SmartUpload ,long, long
	 * @return Collection
	 */
	public Collection importInterestInfo(com.jspsmart.upload.SmartUpload mySmartUpload,long lCurrencyID,long lOfficeID) throws Exception
	{
		Collection c_Return = null;
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			c_Return = dao.saveUploadFileToDateBase(mySmartUpload,lCurrencyID,lOfficeID);
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c_Return;
	}

	/**
	 * ����˵�������Ҷ��ս����˻���Ϣ�������˻���Ϣ
	 *  Method queryInterestCheckInfo.
	 * @param UpLoadInterestCheckInfo ,InterestSettmentInfo
	 * @return Collection
	 */
	public Collection queryInterestCheckInfo(UpLoadInterestCheckInfo info) throws Exception
	{
		Collection c_Return = null;
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			c_Return = dao.findByConditions(info);
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c_Return;
	}
	
	/**
	 * ����˵���������û�ѡ��������������˻���Ϣ��У��ֵΪ������Ϣ��
	 *  Method correctInterestInfo.
	 * @param long[] ,InterestSettmentInfo
	 * @return Collection
	 */
	public Collection correctInterestInfo(String[] strTransInterestID,InterestSettmentInfo settmentInfo) throws Exception
	{
		Collection c_Return = null;
		Vector v_KeepAccount = new Vector();
		Vector v_NotKeepAccount = new Vector();
		Connection con = Database.getConnection();
		//����ά��
		con.setAutoCommit(false);
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			InterestSettlement interestSettlement= new InterestSettlement();
			//��ȡInterestSettmentInfo��
			Vector v_ISInfo = dao.findInterestSettInfo(con,strTransInterestID);
			//ɾ����Ϣ��¼�����������
			interestSettlement.delInterest(con,v_ISInfo,settmentInfo);
			//���½�����Ϣ
			//�������Vector�������½�����Ϣ����������Ϣ���ּ����벻���ˣ�
			
			for(int i = 0;i<v_ISInfo.size();i++)
			{
				InterestSettmentInfo iSInfo = (InterestSettmentInfo)v_ISInfo.elementAt(i);
				InterestQueryResultInfo iRInfo= dao.convertInterestSettmentInfo(con,iSInfo);
				if(iSInfo.getIsKeepAccount() == Constant.YesOrNo.YES)
				{
					v_KeepAccount.add(iRInfo);
				}
				else
				{
					v_NotKeepAccount.add(iRInfo);
				}
			}
			//������Ϣ
			settmentInfo.setIsKeepAccount(Constant.YesOrNo.YES);
			interestSettlement.balanceInterest(con,v_KeepAccount,settmentInfo);
			
			settmentInfo.setIsKeepAccount(Constant.YesOrNo.NO);
			interestSettlement.balanceInterest(con,v_NotKeepAccount,settmentInfo);
			
			//�����ύ������߼�
			con.commit();
			con.setAutoCommit(true);
			
		}
		catch(IException ie)
		{
			ie.printStackTrace();
			try
			{
				//****���ӻع�******
				con.rollback();
				con.setAutoCommit(true);
			}
			catch (Exception eRollback)
			{
				throw new IException("��������쳣");
			}
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				//****���ӻع�******
				con.rollback();
				con.setAutoCommit(true);
			}
			catch (Exception eRollback)
			{
				throw new IException("��������쳣");
			}
			throw new IException("Gen_E001");
		}
		finally
		{
			//�����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷš�
			try
			{
					//"****�ر�����******
					con.close();
					con = null;
			}
			catch (Exception eClose)
			{
				throw new IException("�ر�����ʧ�ܣ�");
			}
		}
		
		return c_Return;
	}
}
