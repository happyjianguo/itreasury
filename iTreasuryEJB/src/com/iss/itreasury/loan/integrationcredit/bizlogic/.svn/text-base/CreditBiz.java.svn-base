/*
 * Created on 2004-8-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.integrationcredit.bizlogic;

import com.iss.itreasury.clientmanage.client.dao.ClientDAO;
import com.iss.itreasury.clientmanage.client.dao.CorporationDAO;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.discount.dataentity.DiscountLoanInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitChangeInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitQueryInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitChangeDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitDetailDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitDetailSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditProductDAO;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.system.dao.PageLoader;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

/**
 * @author gdzhao
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditBiz extends AbstractCreditBiz {

	/**
	 * 根据贷款类型查询授信类型
	 * 
	 * @param loanTypeID
	 * @return
	 */
	public long getCreditTypeByLoanType(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}

	/**
	 * 根据授信品种类型查询对应的贷款类型
	 * 
	 * @param creditProductType
	 * @return
	 * @throws Exception
	 */
	public long typeTrans(long creditProductType) throws IException {
		long loanType = -1;
		try {
			switch ((int) creditProductType) {
			case (int) LOANConstant.CreditProduct.ZY:
				loanType = LOANConstant.LoanType.ZY;
				break;
			case (int) LOANConstant.CreditProduct.SP:
				loanType = LOANConstant.LoanType.TX;
				break;
			case (int) LOANConstant.CreditProduct.BH:
				loanType = LOANConstant.LoanType.DB;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("转换错误",e);
		}
		return loanType;
	}

	/**
	 * 保存授信额度信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long saveLimitInfo(CreditLimitInfo info) throws IException {

		long ret = -1;
		try {
			CreditLimitDAO dao = new CreditLimitDAO();
			// 判断是否做过授信额度设置
			Collection c = dao.findByDateOption(info);
			if (c != null) {
				throw new IException("Loan_E121");
			}

			// 新增操作
			ret = dao.add(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度信息错误",e);
		}
		return ret;
	}

	/**
	 * 保存授信额度信息到creditlimitdetail
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	/*public long saveCreditLimitDetailInfo(CreditLimitInfo info) throws IException {

		long ret = -1;
		try {
			CreditLimitDAO dao = new CreditLimitDAO();
			// 判断是否做过授信额度设置
			Collection c = dao.findByDateOption(info);
			if (c != null) {
				throw new IException("Loan_E121");
			}

			// 新增操作
			ret = dao.add(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度信息错误",e);
		}
		return ret;
	}*/
	
	/**
	 * 修改授信额度设置信息
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void updateLimitInfo(CreditLimitInfo info) throws IException {
	
		try {

			CreditLimitDAO dao = new CreditLimitDAO();
			info.setStatusID(LOANConstant.CreditStatus.SAVE);
			// 判断是否做过授信额度设置
			Collection c = dao.findByDateOption(info);
			if (c != null) {
				throw new IException("该授信有效区间内已有记录，请修改后重新提交！！");
			}
			if (info.getId() > 0) {
				dao.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("更新授信额度信息错误",e);
		}
	}

	/**
	 * 删除授信额度设置信息
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void deleteLimitInfo(long id) throws IException {

		try {

			CreditLimitDAO dao = new CreditLimitDAO();
			CreditLimitInfo info = new CreditLimitInfo();

			info.setId(id);
			info.setStatusID(LOANConstant.CreditStatus.DELETE);

			dao.update(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("删除授信额度错误",e);
		}
	}
    
	/**
	 * 根据授信类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	/*public CreditProductInfo findProductInfoByCreditType(long creditTypeID)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByCreditTypeID(creditTypeID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}*/
	
	/**
	 * 查询授信变更信息(激活/取消激活)
	 * 
	 */
	/*public Collection findCreditChange(CreditLimitQueryInfo Info)
			throws IException {
		Collection c = new ArrayList();
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();

		try {
			c = dao.findCreditChange(Info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信额度变更信息错误",e);
		}
		return c;
	}*/
	
	/**
	 * 查询授信变更历史信息
	 * 
	 */
	/*public Collection findHistoryChange(long historyClientID,long ID)
			throws IException {
		Collection c = new ArrayList();
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();

		try {
			c = dao.findHistoryChange(historyClientID,ID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信额度变更历史信息错误",e);
		}
		return c;
	}*/
	
	public Collection findCreditChangeMaxId(CreditLimitQueryInfo Info)
	    throws IException {
		Collection c = new ArrayList();
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();
		
		try {
			c = dao.findCreditChangeMaxId(Info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return c;
    }

	/**
	 * 查询授信信息（产品分类设置和额度设置）
	 * 
	 * @param clientID
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public CreditInfo findCreditInfo(long clientID, long loanTypeID)
			throws IException {
		CreditInfo creditInfo = new CreditInfo();
		CreditLimitDAO dao = new CreditLimitDAO();
		try{
			// 查询授信产品信息
			creditInfo.setProductInfo(findProductInfoByLoanType(loanTypeID));
			// 查询授信额度设置信息
			creditInfo.setLimitInfo(dao.findCreditLimitByClientID(clientID));
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return creditInfo;
	}

	/**
	 * 根据贷款类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public CreditProductInfo findProductInfoByLoanType(long loanTypeID)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByLoanTypeID(loanTypeID);
			//info = dao.findByCreditTypeID(loanTypeID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}

	/**
	 * 根据授信额度ID查询额度设置信息（包括额度的使用情况）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Collection findLimitInfoByID(long id) throws IException {
		
		Collection col = new ArrayList();
		CreditLimitDAO dao = new CreditLimitDAO();
		CreditLimitInfo info = null;
		CreditLimitChangeInfo Cinfo = new CreditLimitChangeInfo();
		double currentAmount = 0;
		try{
			
			col = (Collection) dao.findByID(id, CreditLimitInfo.class);
			Cinfo.setAmount(info.getAmount());
			Cinfo.setCreditLimitID(info.getId());
			Cinfo.setId(info.getCID());
			Cinfo.setOperate(2);
			currentAmount = calculate(Cinfo);
			info.setCurrentAmount(currentAmount);
			// 根据授信品种计算（已使用金额、已占用金额、可用金额）
			info = dao.findAmountByCreditLimitInfo(info);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("查询额度设置信息错误",e);
		}
		return col;
	}

	public CreditLimitInfo findLimitInfoByInfo(CreditLimitInfo info)
			throws IException {
		CreditLimitDAO dao = new CreditLimitDAO();
		CreditLimitInfo Linfo = new CreditLimitInfo();
		try{
		// 根据授信品种计算（已使用金额、已占用金额、可用金额）
		Linfo = dao.findAmountByCreditLimitInfo(info);
		System.out.println("Linfo.getCurrentAmoun==**=="
				+ Linfo.getCurrentAmount());
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("查询授信金额错误",e);
		}
		return Linfo;
	}

	/**
	 * 激活授信额度设置
	 */
	public long active(long id, long userID) throws IException {
		long signID = -1;
		CreditLimitDAO dao = null;
		dao = new CreditLimitDAO();

		try {
				if (id > 0) {
					// 根据ID查询授信额度信息
					CreditLimitInfo info = (CreditLimitInfo) dao.findByID(id, CreditLimitInfo.class);
					if (info != null) {
						// 判断授信信息是否过期 如果结束日期早于系统日期 将状态变为“已过期” 否则变为“已激活”
						if (info.getEndDate().before(Env.getSystemDate())) {
							CreditLimitInfo info2 = new CreditLimitInfo();
							info2.setId(id);
							info2.setStatusID(LOANConstant.CreditStatus.OVERTIME);
							dao.update(info2);
							throw new IException ("该授信已过期");
						} else {
							CreditLimitInfo info2 = new CreditLimitInfo();
							info2.setId(id);
							info2.setStatusID(LOANConstant.CreditStatus.ACTIVE);
							info2.setActiveUserID(userID);
							info2.setActiveDate(Env.getSystemDate());
							dao.update(info2);
							signID=1;
						}
					} 
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}finally{
		}
		return signID;
	}
	
	/**
	 * 激活授信额度设置
	 */
	public long activeCredit(CreditLimitInfo info, CreditLimitDetailInfo dinfo) throws IException {
		long signID = -1;
		CreditLimitDAO dao = new CreditLimitDAO();
		CreditLimitDetailDAO ddao = new CreditLimitDetailDAO();

		try {
			info.setActiveDate(Env.getSystemDate());
			info.setStatusID(LOANConstant.CreditStatus.ACTIVE);
			dao.add(info);
			dinfo.setActiveDate(Env.getSystemDate());
			dinfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);
			dinfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
			ddao.updateCreditLimitDetailInfo(dinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}finally{
		}
		return signID;
	}

	/**
	 * 取消激活(授信额度设置)
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public long cancelActive(long id, long userID) throws IException {

		long signID = 2;
		CreditLimitDAO dao = new CreditLimitDAO();;
		Collection coll = null;
 
		try {

				if (id > 0) {
					CreditLimitInfo info = (CreditLimitInfo) dao.findByID(
							id, CreditLimitInfo.class);

					if (null != info) {
						if (info.getId() > 0) {
							CreditLimitChangeInfo cinfo = new CreditLimitChangeInfo();
							CreditLimitChangeDAO cdao = new CreditLimitChangeDAO();
							cinfo.setCreditLimitID(info.getId());
							// 取消激活
							//cinfo.setOperate(2);
							// 查询有无授信额度变更信息
							coll = cdao.findCreditChangeAmount(cinfo);
							if (null != coll && coll.size() > 0) {
								signID = -1;
								throw new IException("此额度已被变更无法取消激活");
							}
							// 查询授信额度是否被占用 （已申请＋已使用）
							info = dao.findAmountByCreditLimitInfo(info);
							if ((info.getApplyAmount() + info.getUsedAmount()) > 0) {
								signID = -1;
								throw new IException("此额度已被占用");
							}
							CreditLimitInfo tempInfo = new CreditLimitInfo();
							tempInfo.setId(id);
							tempInfo.setStatusID(LOANConstant.CreditStatus.SAVE);
							dao.update(tempInfo);

						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		} 
		return signID;
	}

	/**
	 * 激活/取消授信额度变更信息
	 * 
	 * @param info
	 * @param operate
	 *            (1为激活操作 2为取消激活操作)
	 * @throws IException
	 * @throws Exception
	 */
	public long activeORcancleCreditChange(CreditLimitChangeInfo creditLimitChangeInfo)
			throws IException {
		CreditLimitDAO dao =new CreditLimitDAO();
		double currentAmount = 0;
		long sign = -1;

		try {

			if (creditLimitChangeInfo !=null && creditLimitChangeInfo.getCreditLimitID() > 0) {
					CreditLimitInfo info = (CreditLimitInfo) dao.findByID(creditLimitChangeInfo.getCreditLimitID(), CreditLimitInfo.class);
					if (null != info) {
						CreditLimitChangeDAO cdao = new CreditLimitChangeDAO();
						if (!info.getEndDate().before(Env.getSystemDate())) {
							CreditLimitChangeInfo cinfo = new CreditLimitChangeInfo();
							cinfo.setId(creditLimitChangeInfo.getId());
							cinfo.setCreditLimitID(creditLimitChangeInfo.getCreditLimitID());
							cinfo.setAmount(info.getAmount());
							cinfo.setOperate(creditLimitChangeInfo.getOperate());
							
							// 计算变更后金额
							currentAmount = calculate(cinfo);
							info.setCurrentAmount(currentAmount);
							// 查询金额
							info = dao.findAmountByCreditLimitInfo(info);

							if (info.getCanUseAmount() > 0) {
								IsactiveOrCancel(cinfo);
								if(creditLimitChangeInfo.getOperate()==1){
									sign=3;
								}
								else{
									sign=4;
								}
							} else {
								sign = 5; //表示可用额度小于零
							}
						} else {
							cdao.updateStatus(creditLimitChangeInfo.getId(),LOANConstant.CreditStatus.OVERTIME);
						}
					} 
				}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		} finally {
		}

		return sign;
	}

	/**
	 * 判断激活和取消激活  add by zwxiao
	 */
	public boolean IsactiveOrCancel(CreditLimitChangeInfo creditLimitChangeInfo) throws IException{
		boolean result = false;
		
		CreditLimitChangeDAO cdao = new CreditLimitChangeDAO();
		try{
			//激活操作
			if (creditLimitChangeInfo.getOperate() == 1) {
				creditLimitChangeInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditLimitChangeInfo.setCheckDate(Env.getSystemDate());
				cdao.updateAciveDate(creditLimitChangeInfo);
				result = true;//表示激活
			}
			// 取消激活操作
			if (creditLimitChangeInfo.getOperate() == 2) {
				boolean flag = cdao.findIsExist(creditLimitChangeInfo);
				if(flag == false){
					cdao.updateStatus(creditLimitChangeInfo.getId(),LOANConstant.CreditStatus.SAVE);
					result = false;//表示取消复核
				}else{
					throw new IException("此变更操作存在保存业务无法进行此操作");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 计算变更后金额 用此笔授信额度的最初金额 加(+)/减(-) 变更的金额 = 实际的授信额度
	 * 
	 * @param cinfo
	 * @return
	 * @throws Exception
	 */
	public double calculate(CreditLimitChangeInfo cinfo) throws IException {
		Collection coll = null;
		double amount = 0;
		CreditLimitChangeDAO cdao = new CreditLimitChangeDAO();
        try{
			// 查询授信额度变更信息
			coll = cdao.findCreditChangeAmount(cinfo);
			if (null != coll  && coll.size()>0) {
				amount = Double.parseDouble(DataFormat.formatAmount(cinfo
						.getAmount()));
				Iterator it = coll.iterator();
				while (it.hasNext()) {
					CreditLimitChangeInfo info = (CreditLimitChangeInfo) it.next();
					if (info.getChangeTypeID() == 1) {
						amount += info.getChangeAmount();
					} else if (info.getChangeTypeID() == 2) {
						amount -= info.getChangeAmount();
					}
				}
			}
        }catch(Exception e){
        	e.printStackTrace();
        	throw new IException(e.getMessage(),e);
        }

		return amount;
	}

	/**
	 * 冻结授信额度
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void freeze(long[] id) throws IException {
		Connection conn = null;
		CreditLimitDAO dao = null;

		try {
			conn = Database.getConnection();
			dao = new CreditLimitDAO(conn);
			conn.setAutoCommit(false);

			for (int i = 0; i < id.length; i++) {
				if (id[i] > 0) {
					CreditLimitInfo info = (CreditLimitInfo) dao.findByID(
							id[i], CreditLimitInfo.class);

					if (info.getStatusID() == LOANConstant.CreditStatus.ACTIVE) {
						CreditLimitInfo info2 = new CreditLimitInfo();
						info2.setId(id[i]);
						info2.setStatusID(LOANConstant.CreditStatus.FREEZE);
						dao.update(info2);
					}
				}
			}

			conn.commit();
			conn.setAutoCommit(true);

			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			    conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			throw new IException(e.getMessage(),e);
		}
	}

	/**
	 * 取消冻结
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void cancelFreeze(long[] id) throws IException {
		Connection conn = null;
		CreditLimitDAO dao = null;

		try {
			conn = Database.getConnection();
			dao = new CreditLimitDAO(conn);
			conn.setAutoCommit(false);

			for (int i = 0; i < id.length; i++) {
				if (id[i] > 0) {
					CreditLimitInfo info = (CreditLimitInfo) dao.findByID(
							id[i], CreditLimitInfo.class);

					if (info.getStatusID() == LOANConstant.CreditStatus.FREEZE) {
						CreditLimitInfo info2 = new CreditLimitInfo();
						info2.setId(id[i]);
						info2.setStatusID(LOANConstant.CreditStatus.ACTIVE);
						dao.update(info2);
					}
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			    conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new IException(e.getMessage(),e);
			}
			throw new IException(e.getMessage(),e);
		}
	}

	/**
	 * 查询已申请授信额度明细
	 * 
	 * @param CreditLimitInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findApplyDetail(CreditLimitInfo creditLimitInfo) throws IException {
		CreditLimitDAO dao = new CreditLimitDAO();

		try{
		  return dao.findApplyDetail(creditLimitInfo);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}

	/**
	 * 查询已使用授信额度明细
	 * 
	 * @param clientID
	 * @return
	 * @throws Exception
	 */
	public Collection findUseDetail(CreditLimitInfo creditLimitInfo) throws IException {
		CreditLimitDAO dao = new CreditLimitDAO();
        
		try{
		  return dao.findUseDetail(creditLimitInfo);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}

	/**
	 * 根据贷款类型ID查询贷款业务是否进行额度控制
	 */
	public long findIsControlProduct(long loantype) throws IException {
		long r = -1;
	     
		try{
			CreditProductDAO dao = new CreditProductDAO();
			r = dao.findIsControlProduct(loantype);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return r;
	}

	/**
	 * 根据授信额度ID查询额度变更明细信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Collection findLimitChangeInfoByID(long id) throws IException {
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();
		Collection col = new ArrayList();
		try {
			col = (Collection) dao.findLimitChangeInfoByID(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return col;
	}

	/**
	 * 保存授信额度变更信息
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void saveLimitChangeInfo(CreditLimitChangeInfo info)
			throws IException {
		System.out.println("welcome to saveLimitChangeInfo");
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();
        long lID = -1;
		try {
			// 判断是否做过授信额度设置
			//Collection c = dao.findByDateOption(info);
			//if (c != null) {
			//	throw new IException("Loan_E121");
			//}
			lID = dao.saveCreditLimitDetail(info);
			
			if(info.getInutParameterInfo()!=null){
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(lID));

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
				info.setId(lID);
				dao.updateStatusID(info);
			}
			System.out.println("保存完了");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}

	/**
	 * 修改授信额度变更信息
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void updateLimitChangeInfo(CreditLimitChangeInfo info)
			throws IException {
		System.out.println("welcome to updateLimitChangeInfo");
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();

		try {
			dao.updateLimitChange(info);
			System.out.println("修改完了");
		}catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException("查询错误",e);
		}catch(Exception e1){
			e1.printStackTrace();
			throw new IException(e1.getMessage(),e1);
		}
	}

	/**
	 * 删除授信额度变更信息
	 * 
	 * @param id
	 * @param lInputUserID
	 * @throws Exception
	 */
	public void deleteLimitChangeInfo(CreditLimitChangeInfo info)
			throws IException {
		System.out.println("welcome to deleteLimitChangeInfo");
		CreditLimitChangeDAO dao = new CreditLimitChangeDAO();

		try {
			
			dao.deleteCreditChange(info);
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException("查询错误",e);
		}catch(Exception e1){
			e1.printStackTrace();
			throw new IException(e1.getMessage(),e1);
		}
	}
	
	/**
	 * 授信控制(用于自营贷款和贴现授信控制)
	 * 
	 */
	/*public boolean controlCredit(LoanApplyInfo applyInfo) throws IException {
		try {
			// 读取配置文件判断是否有贷款授信业务
			boolean integrationcredit = Config.getBoolean(
					ConfigConstant.LOAN_CREDIT_INTEGRATIONCREDIT, false);
			// true为有授信控制业务，false为没有
			if (integrationcredit) {
				// 然后把贷款申请的开始日期放入到CreditLimitInfo对象中
				CreditLimitInfo limitInfo = new CreditLimitInfo();
				limitInfo.setStartDate(applyInfo.getStartDate());
				limitInfo.setCreditTypeID(getCreditTypeByLoanType(applyInfo.getTypeID()));
				limitInfo.setClientID(applyInfo.getBorrowClientID());
				// 是否进行产品的分类设置
				// 先找对应的贷款类型
				CreditProductInfo creditProductInfo = findProductInfoByLoanType(applyInfo.getTypeID());
				if (creditProductInfo != null && creditProductInfo.getId() > 0) {
					if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.YES) {
						return isCreditLimitDate(limitInfo, creditProductInfo,applyInfo);
					}
				} else {
					throw new IException("   请设置产品分类   ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return false;
	}*/
	

	/**
	 * 判断贷款申请金额是否超过授信额度(用于自营贷款和贴现)
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 *             方法作用： 1、查询授信额度变更信息 CreditLimitChangeDAO 里 findCreditChange
	 *             2、如果有变更信息则计算授信变更后额度 calculate 3、查询合同已使用金额
	 *             findAmountByCreditLimitInfo
	 */
	/*private boolean isExceedCreditLimit(CreditLimitInfo limitInfo,
			LoanApplyInfo applyInfo) throws IException {
		boolean isExceed = false;
		CreditLimitChangeDAO creditLimitChangeDAO = new CreditLimitChangeDAO();
		CreditLimitQueryInfo limitQueryInfo = new CreditLimitQueryInfo();
		CreditLimitDAO creditLimitDAO = new CreditLimitDAO();
		try{
			limitQueryInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);// 查询已经激活
			limitQueryInfo.setClientID(limitInfo.getClientID());// 客户id
			limitQueryInfo.setCreditTypeID(limitInfo.getCreditTypeID());// 授信品种
			// 通过状态.授信品种.客户id来查询是否发生额度变更
			Collection creditChangeColl = creditLimitChangeDAO.findCreditChange(limitQueryInfo);
			
			if (creditChangeColl != null && creditChangeColl.size() > 0) {
				// 当有额度变更的时候则需要计算授信变更后的额度
				CreditLimitChangeInfo limitChangeInfo = new CreditLimitChangeInfo();
				limitChangeInfo.setCreditLimitID(limitInfo.getId());
				limitChangeInfo.setAmount(limitInfo.getAmount());
				limitChangeInfo.setOperate(1);
				// 变更后的额度
				double changeCreditAmount = calculate(limitChangeInfo);
				limitInfo.setCurrentAmount(changeCreditAmount);
			}
			
			CreditLimitInfo findUserAmountlimitInfo = creditLimitDAO.findAmountByCreditLimitInfo(limitInfo);
			if (applyInfo.getExamineAmount() <= DataFormat.formatDouble(findUserAmountlimitInfo.getCanUseAmount())) {
				isExceed = true;
			} else {
				isExceed = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isExceed;
	}*/

	/**
	 * 判断贷款申请的日期是否在授信的期限之内（用于自营贷款和贴现）
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	/*private boolean isCreditLimitDate(CreditLimitInfo limitInfo,
			CreditProductInfo creditProductInfo, LoanApplyInfo applyInfo)
			throws IException {
		boolean isStartExceed = false;
		boolean isEndExceed = false;
		CreditLimitDAO creditLimitDAO = new CreditLimitDAO();
		try{
			// 判断贷款申请的开始日期是否在授信的期限之内
			CreditLimitInfo startCreditLimitInfo = creditLimitDAO
					.findDateByCreditLimit(limitInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			if (startCreditLimitInfo != null && startCreditLimitInfo.getId() > 0) {
				//申请的批准金额乘以占用额度比率再与授信额度比较
				applyInfo.setExamineAmount(applyInfo.getExamineAmount()*creditProductInfo.getEngrossRate());
				// 判断贷款申请金额是否超过授信额度,调用isExceedCreditLimit方法
				// true表示没有超过额度，反之则超过
				if (isExceedCreditLimit(startCreditLimitInfo, applyInfo) == false) {
					// 如果是刚性控制,返回一个标识位为true,如果是柔性控制,返回一个标识位为false
					if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
						throw new IException("您申请的金额超过授信额度");
					} else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
						isStartExceed = true;
					}
				}
				
				// 如果贷款申请的结束日期在授信的期限之内
				boolean isapplyenddate = Config.getBoolean(
						ConfigConstant.LOAN_CREDIT_ISAPPLYENDDATE, false);
				if (isapplyenddate) {
					limitInfo.setStartDate(null);
					limitInfo.setEndDate(applyInfo.getEndDate());
					limitInfo.setCreditTypeID(getCreditTypeByLoanType(applyInfo
							.getTypeID()));
					limitInfo.setClientID(applyInfo.getBorrowClientID());
					CreditLimitInfo endCreditLimitInfo = creditLimitDAO
							.findDateByCreditLimit(limitInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
					if (endCreditLimitInfo != null && endCreditLimitInfo.getId() > 0) {
						// 判断开始日期和结束日期是否在同一区间内
						if (endCreditLimitInfo.getId() != startCreditLimitInfo.getId()) {
							endCreditLimitInfo.setSameInterval(false);
							// true表示没有超过额度，反之则超过
							if (isExceedCreditLimit(endCreditLimitInfo, applyInfo) == false) {
								// 如果是刚性控制
								if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
									throw new IException("您申请的金额超过授信额度");
								} else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
									isEndExceed = true;
								}
							}
						}
					} else {
						CreditLimitInfo endCreditLimitfreezeInfo = creditLimitDAO.IsNotCreditfreeze(limitInfo);//判断结束日期所在的授信额度区间内是否被冻结
						if(endCreditLimitfreezeInfo!=null && endCreditLimitfreezeInfo.getId() > 0){
							throw new IException("申请结束日期所在授信额度区间内已经被冻结");
						}else{
							throw new IException("申请结束日期不在授信额度区间内");
						}
					}
				}
			} else {
				CreditLimitInfo startCreditLimitfreezeInfo = creditLimitDAO.IsNotCreditfreeze(limitInfo);//判断结束日期所在的授信额度区间内是否被冻结
				if(startCreditLimitfreezeInfo!=null && startCreditLimitfreezeInfo.getId() > 0){
					throw new IException("申请开始日期所在授信额度区间内已经被冻结");
				}else{
					throw new IException("申请开始日期不在授信额度区间内");
				}	
			}
			if (isStartExceed == true || isEndExceed == true) {
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return false;
	}*/
	
	public static void main(String args[]) {
		CreditBiz bean1 = new CreditBiz();
		try {
			long B = 2;
			// System.out.println(bean1.getApplyCreditAmount(5));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	/***************************************************************************/
	
	
	/**
	 * 根据授信类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public CreditProductInfo findCreditProductInfo(CreditProductInfo qInfo)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByCreditTypeID(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}
	

	/**
	 * 保存授信产品分类设置信息
	 * 
	 * @param CreditProductInfo
	 * @return
	 * @throws Exception
	 */
	public long saveProductInfo(CreditProductInfo info) throws IException {
		long ret = -1;
		CreditProductDAO dao = new CreditProductDAO();
		CreditProductInfo qInfo = null;

		try {
			qInfo = dao.findByCreditTypeID(info);
			
			if(qInfo == null){
				info.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				ret = dao.add(info);
			}
			else {
				qInfo.setOfficeID(info.getOfficeID());    
				qInfo.setCurrencyID(info.getCurrencyID());
				qInfo.setUpdateTime(info.getUpdateTime());
				qInfo.setUpdateUserID(info.getUpdateUserID());
				qInfo.setCreditTypeID(info.getCreditTypeID());
				qInfo.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				qInfo.setIsControl(info.getIsControl());
				qInfo.setControlTypeID(info.getControlTypeID());
				qInfo.setEngrossRate(info.getEngrossRate()); 
				qInfo.setStatusID(info.getStatusID()); 
				dao.update(qInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信产品分类设置错误",e);
		}
		return ret;
	}
	
	
	/***************************************************************************/
	
	/**
	 * 
	 * 检查客户的所有上级客户是否已作过集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isParentCredit(CorporationInfo corporationInfo, CreditLimitDetailInfo detailInfo) throws IException
	{    
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
			 CreditLimitDetailInfo  queryinfo = new CreditLimitDetailInfo();
			 queryinfo.setCreditLimitID(detailInfo.getCreditLimitID());
			 queryinfo.setCreditTypeID(detailInfo.getCreditTypeID());
			 queryinfo.setOfficeID(detailInfo.getOfficeID());
			 queryinfo.setCurrencyID(detailInfo.getCurrencyID());
			 queryinfo.setStartDate(detailInfo.getStartDate());
			 queryinfo.setEndDate(detailInfo.getEndDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 Collection c = dao.findByDateOption(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, true);
			 
			 if(corporationInfo.getParentCorpID1() == -1){
				 if(c == null){
					 isCredit = true;
				 }
				 else {
					 isCredit = false;
				 }
			 }
			 else {
				if(c == null){
					CorporationDAO corporationDAO = new CorporationDAO();
					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
					isCredit = isParentCredit(parentCInfo, detailInfo);
				}
				 else {
					isCredit = false;
				 }
			 }
		 }
		 catch (Exception e) {
			 throw new IException("查询上级客户授信出错");
		 }
		 return isCredit;
    }
	
	/**
	 * 
	 * 检查客户在做集团授信时客户的所有下级客户是否已作过授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isChildCredit(CreditLimitDetailInfo detailInfo) throws IException{
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
			 
			 Collection c = dao.findByDateOptionAndChildClient(detailInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
			 if(c == null){
				 isCredit = true;
			 }
			 else{
				 isCredit = false;
			 }
		 }
		 catch (Exception e) {
			 throw new IException("查询下级客户授信出错");
		 }
		 return isCredit;
	}
	
	/***************************************************************************/
	
	/**
	 * 新增授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long saveCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();

		try {
			// 判断是否做过授信额度设置
			Collection c = dao.findByDateOption(info, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, false);
			if (c != null) {
				throw new IException("Loan_E121");
			}
			
			//检查客户的所有上级客户是否已作过集团授信
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("客户上级已设置过集团授信");
			}
			
			//检查客户在做集团授信时客户的所有下级客户是否已作过授信
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("客户下级已设置过授信");
				}
			}

			lID = dao.add(info);
			
			if(info.getInutParameterInfo()!=null){
				
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(lID));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
				info.setId(lID);
				dao.update(info);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度设置失败 ：" + e.getMessage(),e);
		}
		return lID;
	}
	
	/**
	 * 修改授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long updateCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();

		try {
			//检查客户的所有上级客户是否已作过集团授信
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("客户上级已设置过集团授信");
			}
			
			//检查客户在做集团授信时客户的所有下级客户是否已作过授信
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("客户下级已设置过授信");
				}
			}
			
			if(info.getInutParameterInfo() != null){
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(info.getId()));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
			}
			dao.update(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度信息错误",e);
		}
		return lID;
	}
	
	/**
	 * 删除授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long deleteCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();

		try {
			dao.update(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("删除授信额度信息错误",e);
		}
		return lID;
	}
	
	/**
	 * 根据授信额度ID查询额度设置信息（包括额度的使用情况）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitInfo findCreditLimitInfoByID(long id) throws IException {
		
		CreditLimitDAO dao = new CreditLimitDAO();
		CreditLimitInfo info = new CreditLimitInfo();
		try{
			
			info = (CreditLimitInfo) dao.findByID(id, CreditLimitInfo.class);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("查询额度设置信息错误",e);
		}
		return info;
	}
	
	/**
	 * 通过ID获得授信信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitDetailInfo findCreditLimitDetailInfoByID(long id) throws IException {
		
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
		CreditLimitDetailInfo cinfo = new CreditLimitDetailInfo();

		try{
			cinfo = (CreditLimitDetailInfo) dao.findByID(id, CreditLimitDetailInfo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("获得授信信息错误",e);
		}
		return cinfo;
	}
	
	/**
	 * 授信设置审批
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void doApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo returnInfo = new InutParameterInfo();
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
		
		try {
			returnInfo = FSWorkflowManager.doApproval(info.getInutParameterInfo());

			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.CHECK);
				dao.updateStatusID(info);
				
			}
			
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.SAVE);
				dao.updateStatusID(info);
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException("授信设置审批失败",ex);
		}
	}
	/**
	 * 授信设置取消审批
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void cancelApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
		
		try {
			if(info.getActiveStatusID() == LOANConstant.CreditStatus.ACTIVE){
				throw new IException("授信记录已被激活不能取消审批");
			}
			
			info.setStatusID(LOANConstant.LoanStatus.SAVE);
			dao.updateStatusID(info);
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			else {
				throw new Exception();
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException(ex.getMessage(),ex);
		}
	}
	
	
	
	/*******************************************************************/
	/*************        贷款申请检查所用       *****************************/
	
	//转换一个贷款ID为一个授信ID
	public long switchLoanTypeId(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}
	
	//转换一个授信ID为一个贷款ID
	public long switchCreditTypeId(long creditTypeID) {

		long ret = -1;
		switch ((int) creditTypeID) {
		case (int) LOANConstant.CreditProduct.ZY:
			ret = LOANConstant.LoanType.ZY;
			break;
		case (int) LOANConstant.CreditProduct.SP:
			ret = LOANConstant.LoanType.TX;
			break;
		case (int) LOANConstant.CreditProduct.BH:
			ret = LOANConstant.LoanType.DB;
			break;
		}
		return ret;
	}
	
	/**
	 * 综合授信时，检查授信产品的分类设置
	 */
	public boolean checkCreditProduct(long officeId, long currencyId, long creditTypeId) throws IException {
		boolean isCheck = false;
		CreditProductInfo creditProductInfo = null; 
		try {
			if(creditTypeId == LOANConstant.CreditProduct.ZHSX){
				long lArrayID[] = new long[3];
				lArrayID[0] = LOANConstant.CreditProduct.ZY;
				lArrayID[1] = LOANConstant.CreditProduct.SP;
				lArrayID[2] = LOANConstant.CreditProduct.BH;
				
				for(int i=0; i<lArrayID.length; i++){
					CreditProductInfo qInfo = new CreditProductInfo();
					qInfo.setOfficeID(officeId);
					qInfo.setCurrencyID(currencyId);
					qInfo.setCreditTypeID(lArrayID[i]);
					
					creditProductInfo = this.findCreditProductInfo(qInfo);
					
					if (creditProductInfo != null && creditProductInfo.getId() > 0) {
						if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.NO) {
							isCheck = false;
							throw new IException("所有授信产品分类必须进行额度控制");
						}
					} else {
						isCheck = false;
						throw new IException("请设置所有授信产品分类");
					}
				}
				
				isCheck = true;
			}
			else {
				isCheck = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	
	/**
	 * 授信金额及日期控制主方法
	 * 
	 */
	public boolean checkCreditLimit(LoanApplyInfo applyInfo) throws IException {
		boolean isCheck = false; 
		try {
			// 读取配置文件判断是否有贷款授信业务
			boolean integrationcredit = Config.getBoolean(ConfigConstant.LOAN_CREDIT_INTEGRATIONCREDIT, false);
			
			if(integrationcredit){
				long creditTypeId = this.switchLoanTypeId(applyInfo.getTypeID());
				
				//然后把贷款申请的开始日期放入到CreditLimitInfo对象中
				CreditLimitInfo queryCreditInfo = new CreditLimitInfo();
				queryCreditInfo.setOfficeID(applyInfo.getOfficeID());
				queryCreditInfo.setCurrencyID(applyInfo.getCurrencyID());
				queryCreditInfo.setStartDate(applyInfo.getStartDate());
				queryCreditInfo.setCreditTypeID(creditTypeId);
				queryCreditInfo.setClientID(applyInfo.getBorrowClientID());
				
				// 是否进行过产品的分类设置
				// 先找对应的贷款类型
				CreditProductInfo qInfo = new CreditProductInfo();
				qInfo.setOfficeID(applyInfo.getOfficeID());
				qInfo.setCurrencyID(applyInfo.getCurrencyID());
				qInfo.setCreditTypeID(creditTypeId);
				
				CreditProductInfo creditProductInfo = this.findCreditProductInfo(qInfo);
				
				if (creditProductInfo != null && creditProductInfo.getId() > 0) {
					if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.YES) {
						return this.checkCreditAmountAndDate(queryCreditInfo, creditProductInfo,applyInfo);
					}
					else {
						isCheck = true;
					}
				} else {
					isCheck = false;
					throw new IException("请设置授信产品分类");
				}
			}

			isCheck = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * 判断贷款申请的日期是否在授信的期限之内
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkCreditAmountAndDate(CreditLimitInfo queryCreditInfo, CreditProductInfo creditProductInfo, LoanApplyInfo applyInfo)
			throws IException {
		boolean isCheck = false;
		CreditLimitDAO creditLimitDAO = null;
		CreditLimitInfo creditInfo = null;
		
		//申请批准金额
		double examineAmount = 0;
		//授信金额
	  	double creditAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
		
		try{
			creditLimitDAO = new CreditLimitDAO();
			
			// 判断贷款申请的开始日期是否在授信的期限之内
			creditInfo = creditLimitDAO.findDateByCreditLimit(queryCreditInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			
			if (creditInfo != null) {
				  //检查出客户自己的授信记录
				  //申请批准金额
				  examineAmount = applyInfo.getExamineAmount();
				  //授信金额
		    	  creditAmount = creditInfo.getAmount();
		    	  //客户将占用的总金额
		    	  countAmount = this.getClientCountAmount(creditInfo);
		    	  
		    	  countAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
		    	  
		    	  System.out.println("查询类型：" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
		    	  System.out.println("申请批准金额 examineAmount：" + examineAmount);
		    	  System.out.println("占用额度比率 engrossRate：" + creditProductInfo.getEngrossRate());
		    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
		    	  System.out.println("授信金额 creditAmount：" + creditAmount);
	
		    	  if(Double.compare(countAmount, creditAmount) > 0){
		    		  // 如果是刚性控制,返回一个标识位为true,如果是柔性控制,返回一个标识位为false
		    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
		    			  isCheck = false;
		    			  throw new IException("您申请的金额超过授信余额");
		    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
		    			  isCheck = false;
		    		  }
		    	  }
		    	  else {
		    		  isCheck = true;
		    	  }
			}
			else {
				CorporationDAO corporationDAO = new CorporationDAO();
				CorporationInfo corporationInfo = corporationDAO.findByclietID(queryCreditInfo.getClientID());
				creditInfo = this.getParentCreditLimitInfo(corporationInfo, queryCreditInfo);

				if(creditInfo != null){
					  //检查出客户自己的授信记录
					  //申请批准金额
					  examineAmount = applyInfo.getExamineAmount();
					  //授信金额
			    	  creditAmount = creditInfo.getAmount();
			    	  //客户将占用的总金额
			    	  countAmount = this.getClientCountAmount(creditInfo);
			    	  
			    	  countAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
			    	  
			    	  System.out.println("查询类型：" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
			    	  System.out.println("申请批准金额 examineAmount：" + examineAmount);
			    	  System.out.println("占用额度比率 engrossRate：" + creditProductInfo.getEngrossRate());
			    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
			    	  System.out.println("授信金额 creditAmount：" + creditAmount);
		
			    	  if(Double.compare(countAmount, creditAmount) > 0){
			    		  // 如果是刚性控制,返回一个标识位为true,如果是柔性控制,返回一个标识位为false
			    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
			    			  isCheck = false;
			    			  throw new IException("您申请的金额超过授信余额");
			    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
			    			  isCheck = false;
			    		  }
			    	  }
			    	  else {
			    		  isCheck = true;
			    	  }
				}
				else {
					isCheck = false;
					throw new IException("申请开始日期与结束日期内没有授信记录");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * 取出客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//客户已占用的申请金额
	  	double applyAmount = 0;
	  	//客户已占用的合同金额
	  	double contractAmount = 0;
	  	//非循环贷款时还款单金额
	  	double circleAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
	  	
	  	CreditLimitDAO creditLimitDAO = null;
		
		try{
			creditLimitDAO = new CreditLimitDAO();
			
			if (creditInfo != null) {
				  // 判断是否是综合授信，并检查所有类型是否进行过产品的分类设置
				  if(this.checkCreditProduct(creditInfo.getOfficeID(), creditInfo.getCurrencyID(), creditInfo.getCreditTypeID()) == false){
	    			  throw new IException("授信产品分类设置不完整");
				  }
	
		    	  //根据客户信息查询贷款申请金额
		    	  applyAmount = creditLimitDAO.getClientLoanApplyAmount(creditInfo);
	
		    	  //根据客户信息查询贷款合同金额
		    	  contractAmount = creditLimitDAO.getClientLoanContractAmount(creditInfo);
		    	  
		    	  //自营贷款非循环贷款时还款单金额
		    	  circleAmount = creditLimitDAO.getClientCircleLoanAmount(creditInfo);
		    	  
		    	  //总计金额
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("客户已占用的申请金额 applyAmount：" + applyAmount);
		    	  System.out.println("客户已占用的合同金额 contractAmount：" + contractAmount);
		    	  System.out.println("非循环贷款时还款单金额 circleAmount：" + circleAmount);
		    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
			}
			else {
				throw new IException("授信记录有误");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * 取出历史客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getHistoryClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//客户历史已占用的申请金额
	  	double applyAmount = 0;
	  	//客户历史已占用的合同金额
	  	double contractAmount = 0;
	  	//历史非循环贷款时还款单金额
	  	double circleAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
	  	
	  	CreditLimitDAO creditLimitDAO = null;
		
		try{
			creditLimitDAO = new CreditLimitDAO();
			
			if (creditInfo != null) {
	
		    	  //根据客户信息查询历史贷款申请金额
		    	  applyAmount = creditLimitDAO.getClientLoanApplyAmount(creditInfo);
	
		    	  //根据客户信息查询历史贷款合同金额
		    	  contractAmount = creditLimitDAO.getClientLoanContractAmount(creditInfo);
		    	  
		    	  //历史自营贷款非循环贷款时还款单金额
		    	  circleAmount = creditLimitDAO.getClientCircleLoanAmount(creditInfo);
		    	  
		    	  //总计金额
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("客户历史已占用的申请金额 applyAmount：" + applyAmount);
		    	  System.out.println("客户历史已占用的合同金额 contractAmount：" + contractAmount);
		    	  System.out.println("历史非循环贷款时还款单金额 circleAmount：" + circleAmount);
		    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
			}
			else {
				throw new IException("授信记录有误");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * 
	 * 查找客户的所有上级客户是否已作过集团授信，并取得上级客户的集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public CreditLimitInfo getParentCreditLimitInfo(CorporationInfo corporationInfo, CreditLimitInfo limitInfo) throws IException
	{    
		CreditLimitInfo returnCInfo = null;
		 try {
			 CreditLimitDAO creditLimitDAO = new CreditLimitDAO();
			 CreditLimitInfo  queryinfo = new CreditLimitInfo();
			 queryinfo.setOfficeID(limitInfo.getOfficeID());
			 queryinfo.setCurrencyID(limitInfo.getCurrencyID());
			 queryinfo.setCreditTypeID(limitInfo.getCreditTypeID());
			 queryinfo.setStartDate(limitInfo.getStartDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 returnCInfo = creditLimitDAO.findDateByCreditLimit(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
			 if(corporationInfo.getParentCorpID1() == -1){
				 return returnCInfo;
			 }
			 else {
				if(returnCInfo == null){
					CorporationDAO corporationDAO = new CorporationDAO();
					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
					returnCInfo = getParentCreditLimitInfo(parentCInfo, limitInfo);
				}
				 else {
					return returnCInfo;
				 }
			 }
		 }
		 catch (Exception e) {
			 throw new IException("查找上级客户授信出错");
		 }
		 return returnCInfo;
    }
	
	
	/**
	 * 查询授信额度信息(激活/取消激活)
	 * 
	 */
	public Collection findLimitDetailInfoByCondition(CreditLimitDetailInfo qInfo)
			throws IException {
		Collection c = null;
		CreditLimitDetailDAO dao = new CreditLimitDetailDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信信息错误",e);
		}
		return c;
	}
	

	/**
	 * 查询授信额度信息
	 * 
	 */
	public Collection findLimitInfoByCondition(CreditLimitInfo qInfo)
			throws Exception {
		Collection c = null;
		CreditLimitDAO dao = new CreditLimitDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信额度信息错误",e);
		}
		return c;
	}
	
	/**
	 * 激活/取消授信额度变更信息
	 * 
	 * @param info
	 * @throws IException
	 * @throws Exception
	 */
	public void activeCreditLimitDetail(long creditId, long activeUserId)
		throws IException {
		
		CreditLimitDAO creditDao = null;
		CreditLimitDetailDAO creditDetailDao = null;
		CreditLimitInfo creditInfo = null;
		CreditLimitDetailInfo creditDetailInfo = null;
		Timestamp tsTime = Env.getSystemDateTime();
		long lReturn = -1;
		
		try {
			creditDao = new CreditLimitDAO();
			creditDetailDao = new CreditLimitDetailDAO();

			creditDetailInfo = (CreditLimitDetailInfo)creditDetailDao.findByID(creditId, CreditLimitDetailInfo.class);
			if(creditDetailInfo == null) {
				throw new IException("不能找到授信详细信息，激活失败");
			}
			
			if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.XINZENG){
				//添加主表授信信息
				creditInfo = new CreditLimitInfo();
				creditInfo.setOfficeID(creditDetailInfo.getOfficeID());
				creditInfo.setCurrencyID(creditDetailInfo.getCurrencyID());
				creditInfo.setClientID(creditDetailInfo.getClientID());
				creditInfo.setAmount(creditDetailInfo.getChangeAmount());
				creditInfo.setCreditTypeID(creditDetailInfo.getCreditTypeID());
				creditInfo.setCreditModeID(creditDetailInfo.getCreditModeID());
				creditInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditInfo.setStartDate(creditDetailInfo.getStartDate());
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setInputUserID(creditDetailInfo.getInputUserID());
				creditInfo.setInputDate(tsTime);
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				lReturn = creditDao.add(creditInfo);
				
				//修改授信详细信息
				creditDetailInfo.setCreditLimitID(lReturn);
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}
			else {
				creditInfo = (CreditLimitInfo)creditDao.findByID(creditDetailInfo.getCreditLimitID(), CreditLimitInfo.class);
				if(creditInfo == null) {
					throw new IException("不能找到授信信息，激活失败");
				}
				
				double initAmount = creditInfo.getAmount();
				
				if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.JIA){
					initAmount = initAmount + creditDetailInfo.getChangeAmount();
				}
				else {
					initAmount = initAmount - creditDetailInfo.getChangeAmount();
				}
				
				//修改主表授信信息
				creditInfo.setAmount(initAmount);
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				creditDao.update(creditInfo);
				
				//修改授信详细信息
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}
	
	/**
	 * 获得授信设置子类型
	 * 
	 * @param loanTypeId
     * @param lOfficeID
     * @param lCurrencyID
	 * @throws IException
	 * @throws Exception
	 */
	public long getCreditSubLoanTypeId(long loanTypeId,long lOfficeID,long lCurrencyID)
		throws IException {
		long subLoanTypeId = -1;
		try {
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			Collection coll = biz.findByLoanTypeID(loanTypeId,lOfficeID,lCurrencyID);
			if(coll != null){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					LoanTypeSettingInfo info = (LoanTypeSettingInfo)it.next();
					subLoanTypeId = info.getId();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return subLoanTypeId;
	}

	public PageLoader findLimitDetailHistoryInfoByCondition(CreditLimitDetailInfo qInfo) throws IException {
		// TODO Auto-generated method stub
		return null;
	}
//	授信记录信息排序
	public String getCreditLimitDetailOrderSQL(long lOrder, long lDesc)
	{
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		return dao.getCreditLimitDetailOrderSQL(lOrder, lDesc);
	}
}
