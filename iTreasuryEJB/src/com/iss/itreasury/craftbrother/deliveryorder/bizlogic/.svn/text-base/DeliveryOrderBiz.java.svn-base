package com.iss.itreasury.craftbrother.deliveryorder.bizlogic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.craftbrother.notice.bizlogic.NoticeBiz;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.craftbrother.deliveryorderservice.bizlogic.DeliveryOrderSeviceBiz;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.bizdelegation.NoticeDelegation;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.register.bizlogic.RegisterBiz;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderConditionInfo;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorder.dataentity.MappingDataentity;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceBiz;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

public class DeliveryOrderBiz 
{

	/**
	 *资金划转的提交操作
	*/
	public long submit(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException,Exception
	{
		try{
			//初始化数据库联接
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			
			//保存为交割单
			deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.TRUE);//标记为是通知单关联的交割单
			this.save(deliveryOrderInfo);
			//复核此交割单
			deliveryOrderInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), deliveryOrderInfo.getClass());
			this.check(deliveryOrderInfo);
			//-----------------------
			//新增业务通知单
			//-----------------------
			//NoticeDelegation noticeDelegation = new NoticeDelegation();
			NoticeBiz nbiz = new NoticeBiz();
			NoticeInfo noticeInfo = new NoticeInfo();

			deliveryOrderInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), deliveryOrderInfo.getClass());
			
			//一般资料
			noticeInfo.setDeliveryOrderId(deliveryOrderInfo.getId());
			noticeInfo.setTransactionTypeId(deliveryOrderInfo.getTransactionTypeId());
			noticeInfo.setExecuteDate(deliveryOrderInfo.getTransactionDate());
			//帐户资料
			noticeInfo.setCounterpartBankId(deliveryOrderInfo.getCounterpartBankId());
			noticeInfo.setCounterpartAccountId(deliveryOrderInfo.getCounterpartAccountId());
			noticeInfo.setCompanyBankId(deliveryOrderInfo.getCompanyBankId());
			noticeInfo.setCompanyAccountId(deliveryOrderInfo.getCompanyAccountId());
			//录入人资料
			noticeInfo.setInputUserId(deliveryOrderInfo.getInputUserId());
			noticeInfo.setInputDate(deliveryOrderInfo.getInputDate());
			noticeInfo.setUpdateUserId(deliveryOrderInfo.getUpdateUserId());
			noticeInfo.setUpdateDate(deliveryOrderInfo.getUpdateDate());
			//收付款日期
			noticeInfo.setExecuteDate(deliveryOrderInfo.getDeliveryDate());
			//增加下一审核人id
			noticeInfo.setNextCheckUserId(deliveryOrderInfo.getInputUserId());
			//加入时间戳
			noticeInfo.setDeliveryOrderTimestamp(deliveryOrderInfo.getTimeStamp());
			//改变状态
			noticeInfo.setStatusId(SECConstant.NoticeFormStatus.SUBMITED);
			//增加备注
			noticeInfo.setRemark(deliveryOrderInfo.getRemark());
			
			//新增该通知单	
			long savedNoticeID = nbiz.save(noticeInfo);
			
			noticeInfo = (NoticeInfo)nbiz.findByID(savedNoticeID);
			
			deliveryOrderInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), deliveryOrderInfo.getClass());
//			更新交割单状态为已保存
			deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.SAVED);
			//更新交割单代号为业务通知单代号
			deliveryOrderInfo.setCode(noticeInfo.getCode());
			
			//更新交割单
			deliveryOrderDAO.update(deliveryOrderInfo);
			
			return savedNoticeID;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} 
	
	}
	/**
	 *交割单的保存操作
 	*/
	public void save(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
		//DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
		
		DeliveryOrderSeviceBiz dbiz = new DeliveryOrderSeviceBiz();
		
		DeliveryOrderInfo doInfo = new DeliveryOrderInfo();
		
		long id = -1;

		//---------------
		//  新增交割单
		//---------------
		try {
			if (deliveryOrderInfo.getId() < 0 && deliveryOrderInfo.getStatusId() == -1)

			{
				System.out.println("=============进入交割单--新增=================");

				//检查是否有相同内容的交割单
				deliveryOrderDAO.checkDuplicatedDeliveryOrder(deliveryOrderInfo);
				//生成新的交割单编号
				deliveryOrderInfo.setCode(deliveryOrderDAO.getDeliveryOrderCode(deliveryOrderInfo.getTransactionTypeId()));
				//放入银行帐号id,目前一个公司开户行只对应一个银行帐号，所以银行帐号的id就是公司开户行的id
				deliveryOrderInfo.setCompanyAccountId(deliveryOrderInfo.getCompanyBankId());
				
				//------------------------
				// 修改申请书操作
				//------------------------
			    //改变申请书状态为已使用
			    long applyFormID = (long)deliveryOrderInfo.getApplyFormId();
				ApplyInfo applyInfo = new ApplyInfo();
			
			    applyInfo = (ApplyInfo)applyDAO.findByID(applyFormID, applyInfo.getClass());
			    applyInfo.setStatusId(SECConstant.ApplyFormStatus.USED);		     
			    //更新申请书表
			    applyDAO.update(applyInfo);
				//------------------------
				// 修改交割单操作
				//------------------------
			    //更新交割单状态为已保存
				deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.SAVED);
				//更新时间戳为系统当前时间
				
				deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
				deliveryOrderInfo.setAccountId(deliveryOrderInfo.getAccountId());
				
				//加入与通知单的关系信息(国机)
				if(deliveryOrderInfo.getIsRelatedByNoticeForm() == SECConstant.TRUE){
					System.out.println("=============与业务通知单相关联的交割单(国机)=================");
				}else {
					deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.FALSE);
				}
				if(deliveryOrderInfo.getQuantity() == 0){
					deliveryOrderInfo.setNetPriceAmount(deliveryOrderInfo.getAmount());
				}
			    //加入新的交割单
				id = deliveryOrderDAO.add(deliveryOrderInfo);
				deliveryOrderInfo.setId(id);
			}
			//---------------
			//  更新交割单
			//---------------
			else if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.SAVED || deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.TEMPSAVED ||
			deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.USED)
			{		
				System.out.println("=============进入交割单--修改=================");
				//检查时间戳
				deliveryOrderDAO.checkTimeStamp(deliveryOrderInfo);
				//更新时间戳为系统当前时间
				
				deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
				//放入银行帐号id,目前一个公司开户行只对应一个银行帐号，所以银行帐号的id就是公司开户行的id
				deliveryOrderInfo.setCompanyAccountId(deliveryOrderInfo.getCompanyBankId());
				
				//更新交割单状态为已保存
				deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.SAVED);
				
				doInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), doInfo.getClass());
				doInfo.setIsCheckOverDraft(deliveryOrderInfo.getIsCheckOverDraft());
					
				//虚拟交割单业务无需在保存时通知资金账户(
				String sTemp = Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2);
				if(sTemp.equals("85")||sTemp.equals("71")||sTemp.equals("73")||sTemp.equals("77")||sTemp.equals("81")) {
					//加入与通知单的关系信息(国机)
					deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.TRUE);
					System.out.println("=============资金划拨业务=================");
					
				}else if(deliveryOrderInfo.getIsRelatedByNoticeForm() == SECConstant.TRUE){
					System.out.println("=============与业务通知单相关联的交割单(国机)=================");
					
				}else {
					//加入与通知单的关系信息(国机)
					deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.FALSE);
					
					System.out.println("=============进入交割单--会计帐=================");
					Log.print("=======当前登记簿ID是======:"+deliveryOrderInfo.getRegisterId());
					//修改保存，删除登记簿
					dbiz.deleteDeliveryOrder(doInfo);
				}
				if(deliveryOrderInfo.getQuantity() == 0){
					deliveryOrderInfo.setNetPriceAmount(deliveryOrderInfo.getAmount());
				}
				//更新交割单
				deliveryOrderDAO.update(deliveryOrderInfo);

			}
			//资金划拨业务无需在保存时通知资金账户
			if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85")) {
				System.out.println("==================资金划拨业务====================");
			}else if(deliveryOrderInfo.getIsRelatedByNoticeForm() == SECConstant.TRUE){
				System.out.println("=============与业务通知单相关联的交割单(国机)=================");
			}else{
				//---------------
				//通知资金账户
				//---------------
				long registerID = -1;
				System.out.println("=============进入交割单--会计帐=================");
				Log.print("=======当前交割单的ID是======:"+deliveryOrderInfo.getId());
				
				System.out.println("=============info start=================");
				System.out.println(deliveryOrderInfo.toString());
				System.out.println("=============info end=================");
				
				registerID = dbiz.saveDeliveryOrder(deliveryOrderInfo);
				doInfo.clearUsedFields();
				//置入登记薄id到当前插入或修改的交割单
				doInfo.setId(deliveryOrderInfo.getId());
				doInfo.setRegisterId(registerID);
				
				deliveryOrderDAO.update(doInfo);
			}
			
			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} 

		
	}

	/**
	 *交割单的暂存操作
	*/
	public void tmpSave(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		try {

			//初始化数据库联接
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
			//DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
			DeliveryOrderSeviceBiz dbiz = new DeliveryOrderSeviceBiz();
			DeliveryOrderInfo doInfo = new DeliveryOrderInfo();
			
			long deliveryOrderId = -1;

			//---------------
			//  新增交割单
			//---------------
			if (deliveryOrderInfo.getId() < 0 && deliveryOrderInfo.getStatusId() == -1)
			{
				//检查是否有相同内容的交割单
				deliveryOrderDAO.checkDuplicatedDeliveryOrder(deliveryOrderInfo);
	            //生成交割单编号
				deliveryOrderInfo.setCode(deliveryOrderDAO.getDeliveryOrderCode(deliveryOrderInfo.getTransactionTypeId()));
		        
				//改变申请书状态为已使用
				long applyFormID = (long)deliveryOrderInfo.getApplyFormId();
				ApplyInfo applyInfo = new ApplyInfo();
			
				applyInfo = (ApplyInfo)applyDAO.findByID(applyFormID, applyInfo.getClass());
			    applyInfo.setStatusId(SECConstant.ApplyFormStatus.USED);		     
			    //更新申请书表
				applyDAO.update(applyInfo);
     			//更新交割单状态为已暂存
	     		deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.TEMPSAVED);
				//更新时间戳为系统当前时间
				
				deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
				deliveryOrderInfo.setAccountId(deliveryOrderInfo.getAccountId());
				
				//加入与通知单的关系信息(国机)
				if(deliveryOrderInfo.getIsRelatedByNoticeForm() == SECConstant.TRUE){
					System.out.println("=============与业务通知单相关联的交割单(国机)=================");
				}else {
					deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.FALSE);
				}
				if(deliveryOrderInfo.getQuantity() == 0){
					deliveryOrderInfo.setNetPriceAmount(deliveryOrderInfo.getAmount());
				}
		    	//加入新的交割单
				deliveryOrderId = deliveryOrderDAO.add(deliveryOrderInfo);
				deliveryOrderInfo.setId(deliveryOrderId);
			}
			//---------------
			//  更新交割单
			//---------------
			else if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.TEMPSAVED)
			{
				//检查时间戳
				deliveryOrderDAO.checkTimeStamp(deliveryOrderInfo);
				//更新时间戳为系统当前时间
				
				deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
				
				Log.print("=======当前登记簿ID是======:"+deliveryOrderInfo.getRegisterId());
				
				//修改保存，删除登记簿
				doInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), doInfo.getClass());
				dbiz.deleteDeliveryOrder(doInfo);
				
				//加入与通知单的关系信息(国机)
				deliveryOrderInfo.setIsRelatedByNoticeForm(SECConstant.FALSE);
				if(deliveryOrderInfo.getQuantity() == 0){
					deliveryOrderInfo.setNetPriceAmount(deliveryOrderInfo.getAmount());
				}
				//更新交割单
				deliveryOrderDAO.update(deliveryOrderInfo);
	
			}
	
			//---------------
			//通知资金账户
			//---------------
			long registerID = -1;

			Log.print("=======当前交割单的ID是======:"+deliveryOrderInfo.getId());
			//---------------
			//保存登记簿ID
			//---------------
			registerID = dbiz.saveDeliveryOrder(deliveryOrderInfo);
			doInfo.clearUsedFields();
			//置入登记薄id到当前插入或修改的交割单
			doInfo.setId(deliveryOrderInfo.getId());
			doInfo.setRegisterId(registerID);
			
			deliveryOrderDAO.update(doInfo);
			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} 
		
	}
	
	/**
	 *交割单的删除操作
	 *输入的deliveryOrderInfo只包含id，StatusID,TimeStamp
	 *
	*/
	public void delete(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();		
		if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.SAVED || 
		   deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.TEMPSAVED ||
		   deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED) 
		{
			//检查时间戳
			deliveryOrderDAO.checkTimeStamp(deliveryOrderInfo);

			SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
			
			try {
				//更新时间戳为系统当前时间
				
				deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
				//deliveryOrderDAO.delete(deliveryOrderInfo.getId());
				//更新交割单状态为已删除
				deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.DELETED);
				deliveryOrderDAO.update(deliveryOrderInfo);
		
				//检查是否还有该申请书的交割单
				long applyFormID = deliveryOrderInfo.getApplyFormId();
				
				String strSQL = "ApplyFormID = "+applyFormID + " and "+ " (StatusId = "+
				   				 SECConstant.DeliveryOrderStatus.SAVED + " or StatusId = "+
				   				 SECConstant.DeliveryOrderStatus.TEMPSAVED+")";
				    
				Collection deliveryOrderCollection = deliveryOrderDAO.findByAnyConditions(strSQL);
		
				//如果已经没有与申请书关联的交割单
				if(deliveryOrderCollection.size() == 0){
					ApplyInfo applyInfo = new ApplyInfo();
					applyInfo = (ApplyInfo)applyDAO.findByID(applyFormID, applyInfo.getClass());
					//更新申请书状态从已使用改为已审核
					applyInfo.setStatusId(SECConstant.ApplyFormStatus.CHECKED);
					//更新申请书
					applyDAO.update(applyInfo);
				}
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		//资金划拨业务无需在删除时通知资金账户
		if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85")) {
			System.out.println("@@@@@@@@@@@@@***********@@@@@@@@@@资金划拨业务");
		}else {
			//-----------------
			//通知资金账户
			//-----------------
			DeliveryOrderInfo doInfo = null;
			try {
				doInfo = (DeliveryOrderInfo) deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), DeliveryOrderInfo.class);
				//加入检查过的透支信息
				doInfo.setIsCheckOverDraft(deliveryOrderInfo.getIsCheckOverDraft());
			
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
			//DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
			DeliveryOrderSeviceBiz dbiz = new DeliveryOrderSeviceBiz();
			dbiz.deleteDeliveryOrder(doInfo);
				
		}

	}		
	/**
	 *交割单的复核操作
	*/
	public void check(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		DeliveryOrderInfo doFromDB = null;
		
		if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.SAVED)
		{
			//检查时间戳
			doFromDB = deliveryOrderDAO.checkTimeStamp(deliveryOrderInfo);
			//更新时间戳为系统当前时间
			
			deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
		
			try {
				deliveryOrderInfo.setCode(doFromDB.getCode());
				deliveryOrderInfo.setDeliveryDate(doFromDB.getDeliveryDate());
				
				//更新交割单状态为已复核
				deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.CHECKED);
				//更新交割单
			
				deliveryOrderDAO.update(deliveryOrderInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		//---------------
		//通知资金账户
		//---------------
		//资金划拨业务无需在保存时通知资金账户
		String sTemp = Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2);
		
		if(sTemp.equals("85")||sTemp.equals("71")||sTemp.equals("73")||sTemp.equals("77")||sTemp.equals("81")) {

			System.out.println("=============资金划拨业务复核=================");
		}else {
		
			//DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
			DeliveryOrderSeviceBiz dbiz = new DeliveryOrderSeviceBiz();
			dbiz.checkDeliveryOrder(deliveryOrderInfo);
		}
	}
	/**
	 *交割单的批量复核操作
	*/
	public void massCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException
	{
		try{
			//初始化数据库联接
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			
			Iterator iterator = deliveryOrderInfoCollection.iterator();
			
			DeliveryOrderInfo tempInfo = new DeliveryOrderInfo();
			
			while(iterator.hasNext())
			{
				DeliveryOrderInfo deliveryOrderInfo = (DeliveryOrderInfo)iterator.next();
				tempInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), tempInfo.getClass());
				
				tempInfo.setCheckUserId(deliveryOrderInfo.getCheckUserId());
				tempInfo.setCheckDate(deliveryOrderInfo.getCheckDate());
				
				this.check(tempInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	/**
	 *交割单的取消复核操作
	*/
	public void cancelCheck(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
//		更新交割单状态为已保存
		if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED)
		{
//			初始化数据库联接
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			
			DeliveryOrderInfo doFromDB = null;
			
			//检查时间戳
			doFromDB = deliveryOrderDAO.checkTimeStamp(deliveryOrderInfo);
			//更新时间戳为系统当前时间
			
			deliveryOrderInfo.setTimeStamp(Env.getSystemDateTime());
		
			try {
				deliveryOrderInfo.setCode(doFromDB.getCode());
				deliveryOrderInfo.setDeliveryDate(doFromDB.getDeliveryDate());
				deliveryOrderInfo.setRegisterId(doFromDB.getRegisterId());
				
				//更新交割单状态为已保存
				deliveryOrderInfo.setStatusId(SECConstant.DeliveryOrderStatus.SAVED);
							
//				更新交割单
				deliveryOrderDAO.update(deliveryOrderInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}

		}
		//---------------
		//通知资金账户
		//---------------
		//资金划拨业务无需在保存时通知资金账户
		if(Long.toString(deliveryOrderInfo.getTransactionTypeId()).substring(0,2).equals("85")) {
			System.out.println("=============资金划拨业务取消复核=================");
		}else {
			//DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation(); 
			DeliveryOrderSeviceBiz dbiz = new DeliveryOrderSeviceBiz();
			dbiz.cancelCheckDeliveryOrder(deliveryOrderInfo);
		}
	}	
	/**
	 *交割单的批量取消复核操作
	*/
	public void massCancelCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException
	{
		try{
			//初始化数据库联接
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			DeliveryOrderInfo tempInfo = new DeliveryOrderInfo();
			Iterator iterator = deliveryOrderInfoCollection.iterator();
			
			while(iterator.hasNext())
			{
				DeliveryOrderInfo deliveryOrderInfo = (DeliveryOrderInfo)iterator.next();
				
				tempInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), tempInfo.getClass());
				
				tempInfo.setCheckUserId(deliveryOrderInfo.getCheckUserId());
				tempInfo.setCheckDate(deliveryOrderInfo.getCheckDate());
				
				this.cancelCheck(tempInfo);
			}
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *交割单的单笔查询操作
	*/
	public DeliveryOrderInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
//		初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		DeliveryOrderInfo deliveryOrderInfo = new DeliveryOrderInfo();
		
		/**
		 * 初始化登记簿操作类
		 */
		RegisterBiz operation = new RegisterBiz(); 
		
		try {
			deliveryOrderInfo = (DeliveryOrderInfo)deliveryOrderDAO.findByID(lID,deliveryOrderInfo.getClass());
			
			/**
			 * 如果是有反款业务的交易,判断当前交割单是否已经反款
			 */
			if (deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED &&
				(deliveryOrderInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
				|| deliveryOrderInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)){

				deliveryOrderInfo.setIsRepaid(operation.isDeliveryOrderRepaid(deliveryOrderInfo.getId()));
			}
			
			
			//--------------------------------------
			//添加SEC_DeliveryOrder表中没有的字段
			//--------------------------------------
			//交易类型名称
			String transactionTypeName = NameRef.getTransactionTypeNamebyTransactionTypeID(deliveryOrderInfo.getTransactionTypeId());
			
			if(!transactionTypeName.equals("")){
				deliveryOrderInfo.setTransactionTypeName(transactionTypeName);
			}
			//申请书编号
			String applyFormCode = NameRef.getApplyFormCodeByID(deliveryOrderInfo.getApplyFormId());
			
			if(!applyFormCode.equals("")){
				deliveryOrderInfo.setApplyFormCode(applyFormCode);
			}
			//业务单位名称
			String clientName = NameRef.getClientNameByID(deliveryOrderInfo.getClientId());
			
			if(!clientName.equals("")){
				deliveryOrderInfo.setClientName(clientName);
			}
			
			//交易对手编号和名称
			String counterpartCode = NameRef.getCounterpartCodeByID(deliveryOrderInfo.getCounterpartId());
			String counterpartName = NameRef.getCounterpartNameByID(deliveryOrderInfo.getCounterpartId());
			
			if(!counterpartCode.equals("")){
				deliveryOrderInfo.setCounterpartCode(counterpartCode);
			}
			if(!counterpartName.equals("")){
				deliveryOrderInfo.setCounterpartName(counterpartName);
			}
			//交易对手开户行信息
			String counterpartBankName = NameRef.getCounterpartBankNameByBankID(deliveryOrderInfo.getCounterpartBankId());
			String counterpartAccountCode = NameRef.getCounterpartAccountCodeByBankID(deliveryOrderInfo.getCounterpartBankId());
			String counterpartAccountName = NameRef.getCounterpartAccountNameByBankID(deliveryOrderInfo.getCounterpartBankId());
			
			if(!counterpartBankName.equals("")){
				deliveryOrderInfo.setCounterpartBankName(counterpartBankName);
			}
			if(!counterpartAccountCode.equals("")){
				deliveryOrderInfo.setCounterpartAccountCode(counterpartAccountCode);
			}
			if(!counterpartAccountName.equals("")){
				deliveryOrderInfo.setCounterpartAccountName(counterpartAccountName);
			}
			
			//公司开户行信息
			String clientBankName = NameRef.getClientBankNameByBankID(deliveryOrderInfo.getCompanyBankId());
			String clientAccountCode = NameRef.getClientAccountCodeByBankID(deliveryOrderInfo.getCompanyBankId());
			String clientAccountName = NameRef.getClientAccountNameByBankID(deliveryOrderInfo.getCompanyBankId());
			
			if(!clientBankName.equals("")){
				deliveryOrderInfo.setCompanyBankName(clientBankName);
			}
			if(!clientAccountCode.equals("")){
				deliveryOrderInfo.setCompanyAccountCode(clientAccountCode);
			}
			if(!clientAccountName.equals("")){
				deliveryOrderInfo.setCompanyAccountName(clientAccountName);
			}

			//放入银行帐号id,目前一个公司开户行只对应一个银行帐号，所以银行帐号的id就是公司开户行的id
			deliveryOrderInfo.setCompanyAccountId(deliveryOrderInfo.getCompanyBankId());
			
			
			//股东帐户
			if(deliveryOrderInfo.getAccountId() != -1){
				String stockHolderAccountCode = NameRef.getStockHolderAccountCodeByAccountId(deliveryOrderInfo.getAccountId());
				String stockHolderAccountName = NameRef.getStockHolderAccountNameByAccountId(deliveryOrderInfo.getAccountId());

				if(!stockHolderAccountCode.equals("")){
					deliveryOrderInfo.setStockHolderAccountCode(stockHolderAccountCode);
				}
				if(!stockHolderAccountName.equals("")){
					deliveryOrderInfo.setStockHolderAccountName(stockHolderAccountName);
				}
			}

		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}

		
		System.out.println(deliveryOrderInfo.toString());
		
		
		return deliveryOrderInfo;
	}
	/**
	*交割单的多参数查询操作
	*/
	public Collection findByCondition(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		try {
			return deliveryOrderDAO.findByCondition(deliveryOrderInfo);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 链接查找方法
	 * @param deliveryOrderConditionInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public Collection findByCondition(DeliveryOrderConditionInfo deliveryOrderConditionInfo) throws java.rmi.RemoteException,SecuritiesException 
	{
		/**
		 * 初始化登记簿操作类
		 */
		RegisterBiz operation = new RegisterBiz(); 
		
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		ArrayList aList = new ArrayList();				//要返回的查询结果
		
		Collection colResult = deliveryOrderDAO.findByCondition(deliveryOrderConditionInfo);
		
		if (colResult != null){
			DeliveryOrderInfo info = null;
			Iterator it = colResult.iterator();
			while(it.hasNext()){
				info = (DeliveryOrderInfo)it.next();
				/**
				 * 如果是资金拆入或者资金拆出,那么查询是否已经反款
				 */
				if (info.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED &&
					(info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN
					|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)){
						info.setIsRepaid(operation.isDeliveryOrderRepaid(info.getId()));
				}
				aList.add(info);
			}
		}
		
		
		return aList;
	}
	
	public Collection getAllNotRepayDeliveryOrder(DeliveryOrderConditionInfo conditionInfo) throws java.rmi.RemoteException,SecuritiesException{
		Collection col = null;
		
		DeliveryOrderServiceBiz operation = new DeliveryOrderServiceBiz();
		
		SEC_DeliveryOrderDAO dao = new SEC_DeliveryOrderDAO();
		
		Collection deliveryOrderIds =  operation.getAllNotRepayDeliverOrderID(conditionInfo.getSecTransactionTypeId());
		Log.print("----------成功得到所有未返款交割单ID----------");
		col = dao.getDeliveryOrderInfosByIds(conditionInfo,deliveryOrderIds,1);
		Log.print("----------成功得到所有未返款交割单dataentity----------");
		return col;
	}
	
	/**
	 * 查找某个申请书生成的交割单集
	 * 因为一个申请书可以生成多个交割单。
	 * 
	 */
	public Collection findByApplyFormID(long applyFormID) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		return deliveryOrderDAO.findByApplyFormID(applyFormID);
	}

	/**
	*映射申请书到交割单
	*/
	public DeliveryOrderInfo mappingApplyInfoToDeliveryOrderInfo(long applyFormID) throws java.rmi.RemoteException,SecuritiesException
	{
		//初始化数据库联接

		//SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		SEC_ApplyDAO applyDAO = new SEC_ApplyDAO();
		
		//查找申请书
		ApplyInfo applyInfo = new ApplyInfo();
		
		//double amount = applyInfo.getAmount();//取得申请书的申请金额
		
		try {
			applyInfo = (ApplyInfo)applyDAO.findByID(applyFormID, applyInfo.getClass());
			
			/*
			String strDeliveryOrderSQL = "applyFormID = "+applyFormID+" and relatedDeliveryOrderID = -1";
			Collection c = deliveryOrderDAO.findByAnyConditions(strDeliveryOrderSQL);
			
			Iterator iterator = c.iterator();
		
			while(iterator.hasNext())
			{
				DeliveryOrderInfo info = (DeliveryOrderInfo)iterator.next();
				amount = amount - info.getAmount();//减去交割单已使用的金额
			}
			
			*/
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}

		if(applyInfo != null){
			//得到对应的交割单
			MappingDataentity mappingDataentity = new MappingDataentity();
			DeliveryOrderInfo deliveryOrderInfo = mappingDataentity.map(applyInfo);

			
			return deliveryOrderInfo;
		}		
		return null;
	}
	/**
	 *检查时间戳
	 *
	 *当交割单的时间戳与数据库里的不相同时.检查交割单状态，给前台打出时间戳不同的原因。
	*/
	/*
	public DeliveryOrderInfo checkTimeStamp(DeliveryOrderInfo deliveryOrderInfo) throws SecuritiesException{
		//deliveryOrderInfo只有id,statusID,TimeStamp三个参数。
		//初始化数据库联接
		SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
		
		//提取数据库里的交割单
		DeliveryOrderInfo deliveryOrderInfoFromDB = new DeliveryOrderInfo();
		try {
			deliveryOrderInfoFromDB = (DeliveryOrderInfo)deliveryOrderDAO.findByID(deliveryOrderInfo.getId(), deliveryOrderInfoFromDB.getClass());
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		
		//比较两个交割单里的时间戳是否相同
		// 交割单的时间戳与数据库里的不相同时.说明该条交割单已经被别人修改.
		if(!deliveryOrderInfo.getTimeStamp().equals(deliveryOrderInfoFromDB.getTimeStamp())){
			//检查交割单前后变化的状态，查出时间戳改变的原因。
			String strReason = "";
			
			switch ((int) deliveryOrderInfoFromDB.getStatusId())
			{
				case (int) SECConstant.DeliveryOrderStatus.TEMPSAVED:
					strReason = "暂存";
					break;
				case (int) SECConstant.DeliveryOrderStatus.SAVED:
					strReason = "保存";
					break;
				case (int) SECConstant.DeliveryOrderStatus.DELETED:
					strReason = "删除";
					break;
				case (int) SECConstant.DeliveryOrderStatus.USED:
					strReason = "使用";
					break;
				case (int) SECConstant.DeliveryOrderStatus.POSTED:
					strReason = "过帐";
					break;
				case (int) SECConstant.DeliveryOrderStatus.CHECKED:
					strReason = "复核";
					break;
			}
			if(deliveryOrderInfo.getStatusId() == SECConstant.DeliveryOrderStatus.CHECKED &&
				deliveryOrderInfoFromDB.getStatusId() == SECConstant.DeliveryOrderStatus.SAVED){
				strReason = "取消复核";
			}
			throw new DeliveryOrderModifiedException(strReason);
		}
		return deliveryOrderInfoFromDB;
	}
	*/
	/**
	 *取已到期的交割单
	 *
	*/
	public Collection getMaturityDeliveryOrder(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return null;
	}
	public static void main(String[] args) {

		
           
	}
}
