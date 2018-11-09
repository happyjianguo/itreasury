/**
 * <p>Title:�ػ����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone </p>
 * <p>created by ��yychen</p>
 * <p>modified by :      </p>
 * <p>version 1.0</p>
 * <p>Description: </p>
 */
package com.iss.itreasury.closesystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import java.util.ArrayList;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogBiz;
import com.iss.itreasury.settlement.logger.bizlogic.OpenCloseLogFactory;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;

public class CloseSystemMain
{
	private static long[] m_lOfficeID = null;
	private static String[][][] m_strDealMessage = null; ////��/�ػ�������Ϣ[���´�][����][ģ��]
	private static long m_lExportDataStatusID = -1; ////������Ʒ�¼״̬  
	private static String  m_message = ""; ////������Ʒ�¼״̬  
	public static int getOfficeIndex(long lOfficeID)
	{
		int i = 0;
		for (i = 0; i < m_lOfficeID.length; i++)
		{
			if (m_lOfficeID[i] == lOfficeID) //�ҵ�λ��
			{
				break;
			}
		}
		return i;
	}
	/**
			* ȡ�ÿ�/�ػ������������ʱ�� 
			* return ���ʱ��(����)
			*/
	public static long getDealMaxSecond()
	{
		return 10000;
	}
	/**
				* ȡ�ÿ�/�ػ������е�����Ʒ�· 
				* return ���ʱ��(����)
				*/
	public static long getExportMaxSecond()
	{
		return 6000;
	}
	/**
		* ȡ�ñ�ϵͳ�ı������ֵ�������� 
		* return �������ֵ�������� 
		*/
	public static long getCurrentCount()
	{
		return Constant.CurrencyType.getLength();
	}
	/**
			* ȡ�ñ�ϵͳ��ģ�����ֵ�������� 
			* return ģ�����ֵ�������� 
			*/
	public static long getModelCount()
	{
		//return Constant.ModuleType.getLength();
		//TODO should be checked by yychen
		return 7;
	}
	/**
	 * ���ӿ�/�ػ�������Ϣ
	 * @param lOfficeID ���´���ʶ
	 * @param lCurrencyID ���ֱ�ʶ
	 * @param lModelID ģ���ʶ
	 * @param strMessage �ػ���Ϣ 
	 */
	public static void addDealMessage(long lOfficeID, long lCurrencyID, long lModelID, String strMessage)
	{
		m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1] = DataFormat.formatString(m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1]) + " \n " + DataFormat.formatString(strMessage);
	}
	/**
	* ���ÿ�/�ػ�������Ϣ
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	*/
	public static void resetDealMessage(long lOfficeID, long lCurrencyID, long lModelID)
	{
		m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1] = "";
	}
	/**
	* ȡ�ÿ�/�ػ�������Ϣ
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ������Ϣ
	*/
	public static String getDealMessage(long lOfficeID, long lCurrencyID, long lModelID)
	{
		return DataFormat.formatString(m_strDealMessage[getOfficeIndex(lOfficeID)][(int) lCurrencyID - 1][(int) lModelID - 1]);
	}
	/**
		* ���ÿ�/�ػ�����״̬
		* @param lOfficeID ���´���ʶ
		* @param lCurrencyID ���ֱ�ʶ
		* @param lModelID ģ���ʶ
		* @param strMessage �ػ���Ϣ 
		*/
	public static void setDealStatusID(long lOfficeID, long lCurrencyID, long lModelID, long lStatusID) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, lStatusID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* ȡ�ÿ�/�ػ�����״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public static long getDealStatusID(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID);
	}
	/**
		* ����ϵͳ״̬
		* @param lOfficeID ���´���ʶ
		* @param lCurrencyID ���ֱ�ʶ
		* @param lModelID ģ���ʶ
		* @param strMessage �ػ���Ϣ 
		*/
	public static void setSystemStatusID(long lOfficeID, long lCurrencyID, long lModelID, long lStatusID) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setSystemStatusID(lOfficeID, lCurrencyID, lStatusID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* ȡ��ϵͳ״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public static long getSystemStatusID(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getSystemStatusID(lOfficeID, lCurrencyID);
	}
	/**
			* ����ϵͳʱ��
			* @param lOfficeID ���´���ʶ
			* @param lCurrencyID ���ֱ�ʶ
			* @param lModelID ģ���ʶ
			* @param strMessage �ػ���Ϣ 
			*/
	public static void setSystemDate(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsSystemDate) throws Exception
	{
		try
		{
			BeanFactory.getFunctionBean(lModelID).setSystemDate(lOfficeID, lCurrencyID, tsSystemDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	* ȡ��ϵͳʱ��
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public static Timestamp getSystemDate(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
		return BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID);
	}
	/**
	* ���󿪹ػ�����  
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* @param lActionID ��/�ػ�
	* @param tsDate ��/�ػ�ʱ�� 
	* Description: ҳ����ýӿڣ������е���ShutDownDeal.deal();
	*/
	public static synchronized void requireDeal(long lOfficeID, long lCurrencyID, long lModelID, long lActionID, Timestamp tsDate)
	{
		boolean bIsPassed = true; ////�Ƿ�ͨ��У��
		boolean bIsSuccess = true; ////�Ƿ�ִ�гɹ�
		boolean bBeContinue = true; ///�Ƿ���Ҫ����ִ��
		boolean bIsExecuted = false; ///�Ƿ�ִ�й����ػ�����
		boolean bNeedCheck = true; ////�ػ��Ƿ���Ҫ����ҵ��У���Լ����������]
		OpenCloseLogBiz ocLog =  null;
		try {
			ocLog =  OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
			
			/////�жϱ����´����������Ƿ����ڿ����ػ�
			if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.DOING)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"���ڽ��п��ػ�����,��ȴ���");
				bIsPassed = false;
				
				//Modify by leiyang date 2007/07/05
				try {
					if(lActionID == Constant.ShutDownAction.CLOSE) {
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"���ڽ��йػ�����");
					}
					else {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"���ڽ��п�������");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			///�жϱ����´����������Ƿ���Կ����ػ�
			if (BeanFactory.getFunctionBean(lModelID).getSystemStatusID(lOfficeID, lCurrencyID) == lActionID)
			{
				CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"������" + Constant.ShutDownAction.getName(lActionID) + "״̬�����飡");
				bIsPassed = false;
				
				//Modify by leiyang date 2007/07/05
				try {
					if(lActionID == Constant.ShutDownAction.CLOSE) {
						ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"�����ڹػ�״̬");
					}
					else {
						ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"�����ڿ���״̬");
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			if (bIsPassed)
			{
				long lDealingAction = lActionID; //////���ڽ��еĲ���----�����ػ�
				Timestamp tsDealingDate = null; //////���ڽ��еĲ�������
				Timestamp tsSystem = BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID);
				//// ������е��ǹػ����������������Ϊϵͳʱ�䣻
				//// ������е��ǿ����������ʱ��Ϊϵͳ����һ�죻
				if (lDealingAction == Constant.ShutDownAction.CLOSE)
				{
					tsDealingDate = tsSystem;
				}
				else
				{
					tsDealingDate = DataFormat.getNextDate(tsSystem);
				}
				///ѡ��Ĳ��������Ƿ���Ч
				if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) > 0)
				{
					CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"��ѡ����ȷ��" + Constant.ShutDownAction.getName(lActionID) + "���ڣ�");
					bIsPassed = false;
					
					//Modify by leiyang date 2007/07/05
					try {
						if(lActionID == Constant.ShutDownAction.CLOSE) {
							ocLog.addCloseLog(SETTConstant.CloseSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"û��ѡ����ȷ�Ĺػ�����");
						}
						else {
							ocLog.addOpenLog(SETTConstant.OpenSystemLogType.ERRORINFO,SETTConstant.OpenCloseStatus.UNSUCCESSFUL ,Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"û��ѡ����ȷ�Ŀ�������");
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				//////ѭ�����ƣ��������Ƿ���Ҫ�������йػ������Լ��ػ����Ƿ���Ҫ�������п���������
				//////ԭ��Ϊ�˽��������п��������⣻
				while (bIsPassed && bIsSuccess && bBeContinue && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) <= 0)
				{
					Log.print("======================" + BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID) + "=====" + tsDealingDate + "====" + lDealingAction);
					CloseSystemMain.resetDealMessage(lOfficeID, lCurrencyID, lModelID);
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.DOING);
					bIsExecuted = true;
					DealThread deal = new DealThread(lOfficeID, lCurrencyID, lDealingAction, lModelID, tsDealingDate, bNeedCheck);
					Thread dealthread = new Thread(deal);
					dealthread.start();
					long lCountTime = 0;
					boolean bIsEnd = true;
					/////���ӿ��ػ������Ƿ����
					/////���������ʱ����ǿ���˳����ػ�ʧ�ܣ�
					/////����ػ��ɹ������˳�����ѭ����
					/////����ػ�ʧ�ܣ����˳�����ѭ����
					/////���򣬵ȴ���ѭ�����ӡ�					
					while (bIsEnd)
					{
						if (lCountTime > CloseSystemMain.getDealMaxSecond())
						{
							dealthread.stop();
							dealthread = null;
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.OVERTIME);
							CloseSystemMain.addDealMessage(lOfficeID, lCurrencyID, lModelID, Env.getOfficeName(lOfficeID)+""+ Constant.CurrencyType.getName(lCurrencyID)+"������ʱ�������¿�����");
							bIsEnd = false;
							bIsSuccess = false;
						}
						else if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.SUCCESS)
						{
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
							bIsEnd = false;
						}
						else if (BeanFactory.getFunctionBean(lModelID).getDealStatusID(lOfficeID, lCurrencyID) == Constant.ShutDownStatus.FAIL)
						{
							BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
							bIsEnd = false;
							bIsSuccess = false;
						}
						else
						{
							lCountTime++;
						}
						Thread.currentThread().sleep(1000);
					}
					/////���ݲ����ĳɰܣ��޸��ڴ��еĿ��ػ�����״̬
					if (bIsSuccess)
					{
						BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
					}
					else
					{
						BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
					}
					/////�ж��Ƿ���Ҫ����������һ������
					/////1���ղŽ��е��ǿ�������
					/////����ղŽ��еĲ���ʱ��С����Ҫ���п��ػ�������ʱ�䣬��������е���Ĺػ������������˳�����
					/////2���ղŽ��е��ǹػ�����
					/////����ղŽ��еĲ���ʱ��С����Ҫ���п��ػ�������ʱ�䣬�����������һ��Ŀ��������������˳�����
					if (bIsSuccess)
					{
						if (lDealingAction == Constant.ShutDownAction.OPEN)
						{
							if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) < 0)
							{
								lDealingAction = Constant.ShutDownAction.CLOSE;
								tsDealingDate = tsDealingDate;
								bNeedCheck = false;
								
								//modify by leiyang3 ��Q�_�P�C���}
								ocLog.colseConn();
								OpenCloseLogFactory.bulidInstance(lOfficeID,lCurrencyID,ocLog.lUserId,SETTConstant.OpenCloseType.CLOSE);
								ocLog = OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
							}
							else
							{
								bBeContinue = false;
							}
						}
						else if (lDealingAction == Constant.ShutDownAction.CLOSE)
						{
							bIsExecuted = true;
							if (DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsDate)) < 0)
							{
								lDealingAction = Constant.ShutDownAction.OPEN;
								tsDealingDate = DataFormat.getNextDate(BeanFactory.getFunctionBean(lModelID).getSystemDate(lOfficeID, lCurrencyID));
								
								//modify by leiyang3 ��Q�_�P�C���}
								ocLog.colseConn();
								OpenCloseLogFactory.bulidInstance(lOfficeID,lCurrencyID,ocLog.lUserId,SETTConstant.OpenCloseType.OPEN);
								ocLog = OpenCloseLogFactory.getInstance(lOfficeID,lCurrencyID,true);
							}
							else
							{
								bBeContinue = false;
							}
						}
						else
						{
							bBeContinue = false;
						}
					}
				}
				//////���ִ�й����ҳɹ���ִ�У����޸Ĳ���״̬Ϊ�ɹ��������޸�Ϊʧ�ܣ�
				if (bIsPassed && bIsSuccess && bIsExecuted)
				{
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.SUCCESS);
				}
				else
				{
					BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
				}
			}
			Log.print("����End�� " + CloseSystemMain.getDealMessage(lOfficeID, lCurrencyID, lModelID));
		
		}catch (Exception e){
			try
			{
				BeanFactory.getFunctionBean(lModelID).setDealStatusID(lOfficeID, lCurrencyID, Constant.ShutDownStatus.FAIL);
				e.printStackTrace();
			}
			catch (Exception ss)
			{
				ss.printStackTrace();
			}
		} finally {
			try {
				ocLog.colseConn();
			} catch(IException ie) {
				ie.printStackTrace();
			}
		}
		
	}
	
	/**
	 * �ύ�ػ�����
	 * @param officeID
	 * @param currencyID
	 * @throws Exception
	 */
	public static void CloseApply(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "update sett_officetime set nCloseSystemStatusID="+Constant.ShutDownStatus.REQUEST);
			strSQL.append( " ,dtApplyTime=sysdate ");
			strSQL.append( " where nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("����ػ���"+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
	}

	
	/**
	 * ȡ���ػ�����
	 * @param officeID
	 * @param currencyID
	 * @throws Exception
	 */
	public static void CancelCloseApply(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "update sett_officetime set nCloseSystemStatusID="+Constant.ShutDownStatus.FAIL);
			strSQL.append( " ,dtApplyTime=null ");
			strSQL.append( " where nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("ȡ������ػ���"+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
	}
	
	/**
	 * ��ùػ�����
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public static CloseQueueItemInfo getCloseApplyInfo(long officeID,long currencyID) throws Exception
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		CloseQueueItemInfo item=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "select *  from sett_officetime where nStatusID=" + Constant.RecordStatus.VALID);
			strSQL.append( " and nOfficeID=? ");
			strSQL.append( " and nCurrencyID=? ");
			
			System.out.println("��ȡ���´��ػ�״̬��"+strSQL.toString());

			ps = conn.prepareStatement( strSQL.toString() );
			ps.setLong(1,officeID);
			ps.setLong(2,currencyID);

			rs = ps.executeQuery();
			long id=1;
			while (rs.next())
			{
				item = new CloseQueueItemInfo();
				item.setID( id++ );
				item.setOfficeID( rs.getLong("nOfficeID"));
				item.setCurrencyID( rs.getLong("nCurrencyID"));
				item.setCloseSystemStatusID( rs.getLong("nCloseSystemStatusID"));
				item.setApplyTime(rs.getTimestamp("dtApplyTime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
		return item;
		
	}

	/**
	 * ��ùػ�����
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public static ArrayList getCloseApplyQueue() throws Exception
	{
		ArrayList a = new ArrayList();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn = Database.getConnection();

			StringBuffer strSQL=new StringBuffer();
			strSQL.append( "select *  from sett_officetime where nStatusID=" + Constant.RecordStatus.VALID);
			strSQL.append( " and nCloseSystemStatusID in ("+Constant.ShutDownStatus.DOING +"," +Constant.ShutDownStatus.REQUEST+")");
			strSQL.append( " order by dtApplyTime");

			System.out.println("ȡ�ػ����У�"+strSQL.toString());
			ps = conn.prepareStatement( strSQL.toString() );
			rs = ps.executeQuery();
			long id=1;
			while (rs.next())
			{
				CloseQueueItemInfo item = new CloseQueueItemInfo();
				item.setID( id++ );
				item.setOfficeID( rs.getLong("nOfficeID"));
				item.setOfficeName(NameRef.getOfficeNameByID( item.getOfficeID() ));
				item.setCurrencyID( rs.getLong("nCurrencyID"));
				item.setCurrencyName(Constant.CurrencyType.getName(item.getCurrencyID()));
				item.setCloseSystemStatusID( rs.getLong("nCloseSystemStatusID"));
				item.setCloseSystemStatusName(Constant.ShutDownStatus.getName( item.getCloseSystemStatusID()));
				item.setApplyTime(rs.getTimestamp("dtApplyTime"));
				a.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}

		}
		
		return a;
		
	}
	
	//	��ʼ���ػ���Ϣ
	private static boolean m_bIsInit = false;
	static {
		if (!m_bIsInit)
		{
			PreparedStatement ps=null;
			ResultSet rs=null;
			Connection conn=null;			
			try
			{
				conn = Database.getConnection();
				//���Ұ��´�������
				ps = conn.prepareStatement("select count(*) nCount from office where nStatusID=" + Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				int iOfficeCount = 0; //���´�������
				int iCurrentCount = (int) getCurrentCount();
				int iModelCount = (int) getModelCount();
				
				if (rs.next())
				{
					iOfficeCount = rs.getInt("nCount");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				m_lOfficeID = new long[iOfficeCount];
				m_strDealMessage = new String[iOfficeCount][iCurrentCount][iModelCount];
				ps = conn.prepareStatement("select * from Office where nStatusID=" + Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				int nSize = 0;
				while (rs.next())
				{
					m_lOfficeID[nSize] = rs.getLong("id");
					nSize++;
				}
			}
			catch (Exception exp)
			{
				System.out.println(exp);
			}
			finally{
				try {
					if (rs!=null)
					{
						rs.close();
						rs=null;
					}
					if (ps!=null)
					{
						ps.close();
						ps=null;
					}
					if (conn!=null)
					{
						conn.close();
						conn=null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			m_bIsInit = true;
		}
	}
	
	public static void main(String args[])
	{
		try {
			//CloseSystemMain.CloseApply(1,1);
			//CloseQueueItemInfo info=CloseSystemMain.getCloseApplyInfo(1,1);
			//ArrayList a = CloseSystemMain.getCloseApplyQueue();
			CloseSystemMain.CancelCloseApply(1,1);
			//System.out.println("----"+a.size() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getM_message() {
		return m_message;
	}
	public static void setM_message(String mMessage) {
		m_message = mMessage;
	}
	
}
