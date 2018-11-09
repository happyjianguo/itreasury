/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-15
 */
package com.iss.itreasury.closesystem.securities;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.closesystem.basebean.GLWithinBaseBean;
import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.securities.securitiesgeneralledger.dao.SEC_GLEntryDAO;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SecGLWithBean extends GLWithinBaseBean {
	/*
	 * @author yychen ? FindGLVoucherBaseBean.findGLVoucherByCondition
	 * ():查询会计凭证 
	 * 参数：long lOfficeID:办事处； long lCurrencyID:币种； Timestamp
	 * tsStartDate:执行日； Timestamp tsEndDate:执行日； 
	 * 返回值：Collection
	 * collVoucher:凭证集合； 
	 * 功能描述：根据条件从业务系统中查询所需的会计凭证； 
	 * 流程描述： l 建立连接，开始事务； l 根据条件查询出所需的交易编号； l 分别根据交易编号查询出该交易的会计分录； l 关闭连接，提交事务；
	 */
	public Collection findGLVoucherByCondition(long lOfficeID,
			long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd)
			throws Exception {
		long lReturn = -1;
		ArrayList list = new ArrayList();
		try {
			SEC_GLEntryDAO dao = new SEC_GLEntryDAO();
			boolean hasData = false;
			System.out.println("-------------导账开始时间是:"+tsStart);
			System.out.println("-------------导账结束时间是:"+tsEnd);
			for (Timestamp tsTempDate = tsStart; DataFormat.getDateString(
					tsTempDate).compareTo(DataFormat.getDateString(tsEnd)) <= 0; tsTempDate = DataFormat
					.getNextDate(tsTempDate)) {
				System.out.println("-------------now data is "+tsTempDate);
				com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo secGlEntryInfo = new com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo();
				tsTempDate.setNanos(0);
				secGlEntryInfo.setExecuteDate(tsTempDate);
				
				secGlEntryInfo.setOfficeID(lOfficeID);
				secGlEntryInfo.setCurrencyID(lCurrencyID); 
				secGlEntryInfo.setStatusID(3);
				Collection coll = dao.findByCondition(secGlEntryInfo);
				System.out.println("----------查询到记录数："+coll.size());
				if (coll != null) {
					Iterator it = coll.iterator();
					GLVoucherInfo voucher = new GLVoucherInfo();
					voucher.setModelID(Constant.ModuleType.SECURITIES);
					while(it.hasNext()){
						com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo tmpSECGlEntryInfo = new com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo();
						GLEntryInfo info = new GLEntryInfo();
						tmpSECGlEntryInfo = (com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GLEntryInfo) it
								.next();
						//////
						System.out.println("--------证券的会计分录信息为------------");
						System.out.println(UtilOperation.dataentityToString(tmpSECGlEntryInfo));
					
						GlSettingInfo glSettingInfo=new GlSettingInfo();
						glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
						if(glSettingInfo.getGlName().equals("GENERSOFT"))
						//if(Constant.GLType.getID()==Constant.GLType.GENERSOFT)   //浪潮接口
	                	{
							if (tmpSECGlEntryInfo.getPostStatusID() != Constant.GLPostStatus.SUCCESS && tmpSECGlEntryInfo.getPostStatusID() != Constant.GLPostStatus.DOING)
							{
								info.setID(tmpSECGlEntryInfo.getId());
								info.setAbstract(tmpSECGlEntryInfo.getRemark());
								info.setAmount(tmpSECGlEntryInfo.getAmount());
								info.setCurrencyID(tmpSECGlEntryInfo.getCurrencyID());
								info.setOfficeID(tmpSECGlEntryInfo.getOfficeID());
								info.setDirectionID(tmpSECGlEntryInfo.getTransDirection());
								info.setExecute(tmpSECGlEntryInfo.getExecuteDate());
								info.setInterestStart(tmpSECGlEntryInfo.getInterestStartDate());
								info.setSubject(tmpSECGlEntryInfo.getSubjectCode());
								info.setTransNo(tmpSECGlEntryInfo.getDeliveryOrderCode());
								info.setBankChequeNo(getBankChequeNoByTransNo(tmpSECGlEntryInfo.getDeliveryOrderCode()));
								System.out.println("--------输入账套的信息为------------");
								System.out.println(UtilOperation.dataentityToString(info));
											//////
								voucher.addEntryInfo(info);
								voucher.setPostDate(Env.getSecuritiesSystemDate(lOfficeID, lCurrencyID));
								hasData = true;
							}
							
						}
						else
						{
							if (tmpSECGlEntryInfo.getPostStatusID() != Constant.GLPostStatus.SUCCESS )
							{
								info.setID(tmpSECGlEntryInfo.getId());
								info.setAbstract(tmpSECGlEntryInfo.getRemark());
								info.setAmount(tmpSECGlEntryInfo.getAmount());
								info.setCurrencyID(tmpSECGlEntryInfo.getCurrencyID());
								info.setOfficeID(tmpSECGlEntryInfo.getOfficeID());
								info.setDirectionID(tmpSECGlEntryInfo.getTransDirection());
								info.setExecute(tmpSECGlEntryInfo.getExecuteDate());
								info.setInterestStart(tmpSECGlEntryInfo.getInterestStartDate());
								info.setSubject(tmpSECGlEntryInfo.getSubjectCode());
								info.setTransNo(tmpSECGlEntryInfo.getDeliveryOrderCode());
								info.setBankChequeNo(getBankChequeNoByTransNo(tmpSECGlEntryInfo.getDeliveryOrderCode()));
								System.out.println("--------输入账套的信息为------------");
								System.out.println(UtilOperation.dataentityToString(info));
											//////
				
								voucher.addEntryInfo(info);
								voucher.setPostDate(Env.getSecuritiesSystemDate(lOfficeID, lCurrencyID));
								hasData = true;
							}
						}
					}	//end while
					if(hasData)
					{
						list.add(voucher);
						hasData = false;
					}
				}
			}
		} finally {
		}

		System.out.println("--------凭证个数为:"+list.size());
		return (list != null && list.size() > 0 ? list : null);
	}
	private static String getBankChequeNoByTransNo(String sTransNo)
			throws Exception {

		return "";
	}
	/*
	 * @author yychen 参数：Collection collVoucher:解析从EAI返回信息所得到的信息集合； 返回值：boolean
	 * bIsSuccess:是否成功； 功能描述：修改业务系统中会计分录的状态； 流程描述： l 建立连接，开始修改会计分录状态事务； l
	 * 根据参数，修改每笔会计分录的状态（数据库会计分录表Sett_GLEntry中nPostGLStatusID字段）； l
	 * 提交修改会计分录状态事务，返回是否成功；
	 */
	public boolean updatePostStatus(Collection collVoucher) throws Exception {
		boolean bIsPassed = true;
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
				
		ResultSet rs = null;
		StringBuffer sbRecord1 = new StringBuffer();
		StringBuffer sbRecord2 = new StringBuffer();
		StringBuffer sbRecord3 = new StringBuffer();
		try {
			System.out.println("********collVoucher**********="+collVoucher);
			if (collVoucher != null) {
				System.out.println("********进入证券修改状态**********");
				conn = Database.getConnection();
				sbRecord1
						.append("update sec_glentry set poststatusid = ? where id = ? ");
				
				sbRecord2
				.append("update sec_deliveryorder set statusid = ? where code = ? ");
				
				sbRecord3
				.append("update sec_noticeform set statusid = ? where sec_noticeform.deliveryorderid in  (select Id from sec_deliveryorder   where code = ?)");				
				
				ps1 = conn.prepareStatement(sbRecord1.toString());
				ps2 = conn.prepareStatement(sbRecord2.toString());
				ps3 = conn.prepareStatement(sbRecord3.toString());
				Iterator it = collVoucher.iterator();
				while (it.hasNext()) {
					System.out.println("********循环**********");
					GLVoucherInfo voucher = (GLVoucherInfo) it.next();
					///修改会计分录状态
					for(int i=0;i<voucher.getList().size();i++)
					{
						GLEntryInfo glentryinfo = (GLEntryInfo)voucher.getList().get(i);
						System.out.println("---voucher.getPostStatusID()="+voucher.getPostStatusID()+";glentryinfo.getID()="+glentryinfo.getID());
						ps1.setLong(1, voucher.getPostStatusID());
						ps1.setLong(2, glentryinfo.getID());
						ps1.addBatch();
						
						ps2.setLong(1, SECConstant.DeliveryOrderStatus.POSTED);
						ps2.setString(2, glentryinfo.getTransNo());
						ps2.addBatch();
						
						ps3.setLong(1, SECConstant.NoticeFormStatus.POSTED);
						ps3.setString(2, glentryinfo.getTransNo());
						ps3.addBatch();												
						
						if (voucher.getPostStatusID() == Constant.GLPostStatus.FAILED) {
							bIsPassed = false;
						}
					}
				}
				ps1.executeBatch();
				ps1.close();
				ps1 = null;
				
				ps2.executeBatch();
				ps2.close();
				ps2 = null;
				
				ps3.executeBatch();
				ps3.close();
				ps3 = null;			

				System.out.println("********退出证券修改状态**********");
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bIsPassed = false;
			if (conn != null) {
				conn.close();
				conn = null;
			}
			throw new RemoteException(e.getMessage());
		}
		
		return bIsPassed;
	}
	
	/*
	 * @author lixr 浪潮财务软件专用
	 * 参数：Collection collVoucher:解析从EAI返回信息所得到的信息集合； 
	 * 返回值：boolean
	 * bIsSuccess:是否成功； 
	 * 功能描述：修改业务系统中会计分录的状态； 
	 * 流程描述： l 建立连接，开始修改会计分录状态事务； l
	 * 根据参数，修改每笔会计分录的状态（数据库会计分录表Sett_GLEntry中nPostGLStatusID字段）； l
	 * 提交修改会计分录状态事务，返回是否成功；
	 */
	public boolean updatePostStatus(long lOfficeID, long lCurrencyID) throws Exception {
		boolean bIsPassed = true;
		Connection conn = null;
		PreparedStatement ps = null;
		
				
		ResultSet rs = null;
		StringBuffer sbRecord1 = new StringBuffer();
		
		try {
			
			
				conn = Database.getConnection();
				sbRecord1.append("update sec_glentry set poststatusid = ? where poststatusid = ? ");
				sbRecord1.append("and nOfficeID=? and nCurrencyID= ? ");
				
				ps = conn.prepareStatement(sbRecord1.toString());
				
				ps.setLong(1, Constant.GLPostStatus.SUCCESS);		
				ps.setLong(2, Constant.GLPostStatus.DOING);	
				ps.setLong(3, lOfficeID);
	            ps.setLong(4, lCurrencyID);
				ps.executeUpdate();
			
				ps.close();
				ps = null;						

				System.out.println("********退出证券修改状态**********");
				conn.close();
				conn = null;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			bIsPassed = false;			
			
			if(ps!=null)
			{
				ps.close();
				ps=null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			if(ps!=null)
			{
				ps.close();
				ps=null;
			}
			if(conn!=null)
			{
				conn.close();
				conn=null;
			}
		}
		
		return bIsPassed;
	}
	 /*
     * 导入科目余额，调用结算的方法
     */
    public boolean addSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collBalance) throws Exception
    {
    	SettGLWithinDao dao=new SettGLWithinDao();
    	return dao.addSubjectBalance(lOfficeID,lCurrencyID,tsDate,collBalance);
    }
    
//	private void updateDeliveryOrderAndNoticeForm(String deliveryOrderCode){
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		StringBuffer sbRecord = new StringBuffer();		
//		//sbRecord.append(" update sec_deliveryorder set ")
//		try {
//		} catch (Exception e) {
//			if (conn != null) {
//				conn.close();
//				conn = null;
//			}
//			throw new RemoteException(e.getMessage());
//		}			
//		
//	}
}
