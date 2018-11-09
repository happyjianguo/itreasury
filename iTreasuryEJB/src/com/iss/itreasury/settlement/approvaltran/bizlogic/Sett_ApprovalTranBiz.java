package com.iss.itreasury.settlement.approvaltran.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.settlement.approvaltran.dao.Sett_ApprovalTranDao;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author yangliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_ApprovalTranBiz
{
	Sett_ApprovalTranDao dao = null;
    public Sett_ApprovalTranBiz(){
        dao = new Sett_ApprovalTranDao();
    }
    /**
	 *  ����������ѯ��Ҫ��¼�������ļ�¼(���и���ڲ�ת�ʡ�ί�д��)--add by ����
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
   public Collection findByAppBankConditions(TransCurrentDepositInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppBankConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
   }
   /**
	 *  ����������ѯ��Ҫ��¼�������ļ�¼(���и���ڲ�ת�ʡ�ί�д��)--add by ����
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
  public Collection findByAppGenerallConditions(TransGeneralLedgerInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppGenerallConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
  }
  /**
	 *  ����������ѯ��Ҫ��¼�������ļ�¼(���⽻��)--add by ����
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
public Collection findByAppCorpsConditions(TransSpecialOperationInfo info,long typeID,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppCorpsConditions(info,typeID,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
}
   /**
	 * ����ģ��ID��ҵ������ID������ѯ������������ID--add by ����
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
   public long getApprovalId(long moduleid,long typeId,long currencyID,long officeID,double amount){
	   long lretu=-1;
	   try{
		   lretu=dao.getApprovalId(moduleid,typeId,currencyID,officeID,amount);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
   }
   /**
	 * ����������ID���û�ID������û�����������--add by ����
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
   public long getLevelId(long approvalID,long userID){
	   long lretu=-1;
	   try{
		   lretu=dao.getLevelId(approvalID,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
   }
   /**
	 * ����������������½���ҵ���¼��״̬,ͬʱ����������¼����¼--add by ����
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
  public long updateDataStatusID(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
	   long lretu=-1;
	   try{
		   lretu=dao.updateDataStatusID(info,lResultID,tranTypeId,settID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
  }
  /**
   *  ����ģ��ID,ҵ������ID����������id�������Ϣ����������Ϣ
   * @author chuanliu
   *
   * TODO To change the template for this generated type comment go to
   * Window - Preferences - Java - Code Style - Code Templates
   */
  public Collection findByCondition(ApprovalTracingInfo info) throws Exception
  {
      Collection Condition = null;
      try
      {
          Condition = dao.findByCondition(info);
      }
      catch(Exception e){e.printStackTrace();}
   return Condition;
  }
  /**
   * ����������ѯ��Ҫ��¼�������ļ�¼(���ִ���)
   * @author chuanliu
   *TransGrantDiscountInfo
   * TODO To change the template for this generated type comment go to
   * Window - Preferences - Java - Code Style - Code Templates
   */
  public Collection findByDISCOUNTGRANTConditions(TransGrantDiscountInfo info,long userID) throws Exception
  {
      Collection Condition = null;
      try
      {
          Condition = dao.findByDISCOUNTGRANTConditions(info,userID);
      }
      catch(Exception e){e.printStackTrace();}
   return Condition;
  }
  /**
	 *  ����������ѯ��Ҫ��¼�������ļ�¼(��Ӫ���ί�д���)--add by ����
	 * @param TransGrantLoanInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
  public Collection findByTrustConditions(TransGrantLoanInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByTrustConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
  }
	/**
	 * ��ʾ��һ��������б�
	 * ֧���ظ�������Ա��Խ������Լ���ȿ���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     ���
	 * @param       strFieldName            �������
	 * @param       strNextFieldName        ��һ���������
	 * @param       lModuleID               ģ���ʾ
	 * @param       lLoanTypeID             ҵ�����ͱ�ʶ
	 * @param       amount               	���
	 * @param       lUserID                 ����û���ʾ
	 * @param       dataStatusID            ��ǰ״̬
	 * @return      long                    ���һ����˷���1������������˷���0�����󷵻�-1
	 */
	public long showApprovalUserList(
	        JspWriter out, 
	        String strFieldName,
	        String strNextFieldName, 
	        long lModuleID,
			long lLoanTypeID, 
			double amount,
			long lOfficeID, 
			long lCurrencyID,
			long lUserID,
			long dataStatusID
		) throws Exception
	{

		Connection con = null;
		ApprovalDao approvaldao = null;
		Sett_ApprovalTranDao settdao = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		ApprovalTracingInfo ATInfo = null;
		ApprovalSettingInfo ASInfo = null;
		long lApprovalID = -1;
		long lTotalLevel = -1;
		long lLevel = -1;
		long lIsPass = -1;		
		long lStatusID = -1;
		Vector vNextUser = new Vector(); //��һ������˼���

		try 
		{
			//connection ����Ƕ��
			con = Database.getConnection();
			settdao = new Sett_ApprovalTranDao();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);
			//��ѯ���������Ϣ
			lApprovalID = settdao.getApprovalId(lModuleID,lLoanTypeID,lOfficeID,lCurrencyID,amount);
			lLevel = settdao.getLevelId(lApprovalID,lUserID);
			ASInfo = approvalsettingdao.findApprovalSetting(lApprovalID);			
		
			if (ASInfo != null)
			{
				lApprovalID = ASInfo.getID();
				lTotalLevel = ASInfo.getTotalLevel();
				lIsPass = ASInfo.getIsPass();
				lStatusID = ASInfo.getStatusID();
			}
			//��������õ�״̬�����Ѽ���򷵻�-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if(dataStatusID==SETTConstant.TransactionStatus.APPROVALING && lLevel!=1)
				{
					 //��ǰ����˲������������У�����-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,5);
                    lReturn = -2;
				}else if (lLevel <= 0)
                {
                    //��ǰ����˲������������У�����-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{
					//���һ�����
					showUserList(out, strFieldName, strNextFieldName, null, 3);
					lReturn = 1;
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
				    //��ǰ����˲����������õ�һ���У�����-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//��ѯ�����Ա��Ϣ
					ASInfo = null;
					if (lIsPass == 1) //����Խ�����
					{					    
					        ASInfo = approvaldao.findApprovalItemAboveLevel(
									lApprovalID,
									lLevel,
									lUserID,									
									lTotalLevel,lCurrencyID,lOfficeID);					    
					}
					else
					{					     
					        ASInfo = approvaldao.findApprovalItem(lApprovalID,
									lLevel+1,
									lUserID,
									lCurrencyID,lOfficeID);					    
					}
					if (ASInfo != null)
					{ 
						vNextUser = ASInfo.getNextUser();
					}
					if (vNextUser == null || vNextUser.size() == 0)
					{
						showWrongUserList(out,strFieldName,strNextFieldName,4);
						//û��������һ�������Ա
						lReturn = -1;
					}
					else
					{
						//�����Ա�����б���ʾ�ؼ�
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				showWrongUserList(out,strFieldName,strNextFieldName,1);
				lReturn = -1;
			}
			con.close();
			con = null;
		}
        catch (IException ie)
        {
            if (con != null)
			{
				con.close();
				con = null;
			}
            throw ie;
        }
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.showApprovalUserList() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	
	}
	/**
	 * �����Ա�����б���ʾ�ؼ�(��������δ��ɡ���
	 * @param out                ���
	 * @param strFieldName       �������
	 * @param strFieldName       ��һ���������
	 * @param nErrorNo       	 ������Ϣ���
	 *
	 */
	private static void showWrongUserList(JspWriter out, String strFieldName,String strNextFieldName,int nErrorNo) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();
		String strError = "";

		try
		{
			switch (nErrorNo)
			{
				case 1:
				    strError = "��������δ��ɻ�û�м�����飡";
				    break;
				case 2:
				    strError = "��ǰ����˲��ڸ����������У����飡";
				    break;
				case 3:
				    strError = "��ǰ����˲��ڸ��������õ�һ���У����飡";
				    break;
				case 4:
				    strError = "��һ�������Ϊ�գ����飡";
				    break;
				case 5:
				    strError = "����Ϊ��һ�������ˣ����飡";
				    break;
				default :
				    strError = "�������ò���ȷ�����飡";
			}
		    out.println("<script language='javascript'>");
			out.println("alert('" + strError + "');");
			out.println("</script>");
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}
	/**
	 * �����Ա�����б���ʾ�ؼ�
	 * @param out                ���
	 * @param strFieldName       �������
	 * @param strFieldName       ��һ���������
	 * @param NextUser           �û��б�
	 * @param lDisplayFinish     �Ƿ���ʾ�������ɡ���1 �ǣ�2 ��3 ֻ��ʾ�������ɡ�
	 *
	 */
	private static void showUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, Vector vNextUser,
									 long lDisplayFinish) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();

		try
		{
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			if (lDisplayFinish != 3)
			{
				if (vNextUser != null && vNextUser.size() > 0)
				{
					while (i < vNextUser.size())
					{
						AUInfo = (ApprovalUserInfo) vNextUser.get(i);
						out.println("<option value=\"" + AUInfo.getUserID() +
									"\">" + AUInfo.getUserName() + "</option>");
						i++;
					}
					if (lDisplayFinish == 1)
					{
						out.println("<option value=\"-2\">������</option>");
					}
				}
			}
			else
			{
				out.println("<option value=\"-2\">������</option>");
			}
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}
}