package com.iss.itreasury.ebank.obfinanceinstr.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletRequest;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dao.QueryCheckInfoDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBCapSummarizeInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.PayerOrPayeeInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBSignatureConstant;
//import com.iss.itreasury.ebank.util.OBSignatureUtil;
import com.iss.itreasury.safety.facade.SecurityFacadeInterface;
import com.iss.itreasury.safety.facade.factory.SecurityFacadeFactory;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.bizdelegation.PrivilegeDelegation;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QueryCheckInfoBiz {
	
	QueryCheckInfoDao queryCheckInfoDao = new QueryCheckInfoDao();
	
	public PagerInfo queryCheckInfo(Query_FinanceInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = queryCheckInfoDao.queryCheckInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "checkInfoResultSetHandle");
			
			//pagerInfo.setTotalExtensionMothod(QueryCheckInfoBiz.class, "getCheckInfoSumAmount" , paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList checkInfoResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		//QueryAccountResultInfo info = null;
		
		long id = -1;
		double mAmount = 0.0;
		Timestamp dtModify = null;
		long nTranstype = -1;
		String sAccountno = "";
		String sPayeeacctno = "";
		String nbsName = "";
		String sPayeeName = "";
		String sPayeebankName = "";
		long nStatus = -1;
		String sNote = "";
		Timestamp dtExecute = null;
		Timestamp dtConfirm = null;
		String DepositNo =  "";
		long Npayeeacctid = -1;
		long npayeracctid = -1;
		long InterestPayeeAcctID = -1;
		long RemitType = -1;
		long InterestRemitType = -1;
		long confirmUserID = -1;
		long noticeday = -1;
		String depositBillNo =  "";
		long depositBillPeriod = -1;
		Timestamp depositBillStartDate = null;
		long depositInterestDeal = -1;
		long depositInterestToAccountID = -1;
		long fixedDepositTime = -1;
		String signatureValue =  "";
		long timestamp = -1;
		
		FinanceInfo fInfo = null;

		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					id = rs.getLong("id");
					mAmount = rs.getDouble("mAmount");
					dtModify = rs.getTimestamp("DTMODIFY");
					nTranstype = rs.getLong("ntranstype");
					sAccountno = rs.getString("saccountno");
					sPayeeacctno = rs.getString("spayeeacctno");
					nbsName = rs.getString("NBSNAME");
					sPayeeName = rs.getString("spayeename");
					sPayeebankName = rs.getString("spayeebankname");
					nStatus = rs.getLong("NSTATUS");
					sNote = rs.getString("SNOTE");
					dtExecute = rs.getTimestamp("dtexecute");
					dtConfirm = rs.getTimestamp("DTCONFIRM");
					DepositNo = rs.getString("DepositNo");
					Npayeeacctid = rs.getLong("Npayeeacctid");
					npayeracctid = rs.getLong("npayeracctid");
					InterestPayeeAcctID = rs.getLong("InterestPayeeAcctID");
					RemitType = rs.getLong("RemitType");
					InterestRemitType = rs.getLong("InterestRemitType");
					confirmUserID = rs.getLong("confirmUserID");
					noticeday = rs.getLong("noticeday");
					depositBillNo = rs.getString("depositBillNo");
					depositBillPeriod = rs.getLong("depositBillPeriod");
					depositBillStartDate = rs.getTimestamp("depositBillStartDate");
					depositInterestDeal = rs.getLong("depositInterestDeal");
					depositInterestToAccountID = rs.getLong("depositInterestToAccountID");
					fixedDepositTime = rs.getLong("fixedDepositTime");
					signatureValue = rs.getString("signatureValue");
					timestamp = rs.getLong("timestamp");
					
					Query_FinanceInfo info = new Query_FinanceInfo();
					
					info.setID(id);
					info.setDepositNo(DepositNo);
					info.setNpayeeacctid(Npayeeacctid);
					info.setNpayeracctid(npayeracctid);
					info.setInterestPayeeAcctID(InterestPayeeAcctID);
					info.setMAmount(mAmount);
					info.setRemitType(RemitType);
					info.setInterestRemitType(InterestRemitType);
					info.setConfirmUserID(confirmUserID);
					//info.setNtranstype(nTranstype);
					info.setNoticeDay(noticeday);
					info.setDepositBillNo(depositBillNo);
					info.setDepositBillPeriod(depositBillPeriod);
					info.setDepositBillStartDate(depositBillStartDate);
					info.setDepositInterestDeal(depositInterestDeal);
					info.setDepositInterestToAccountID(depositInterestToAccountID);
					info.setFixedDepositTime(fixedDepositTime);
					info.setSignatureValue(signatureValue);
					info.setDtModify(dtModify);
					info.setNtranstype(nTranstype);
					info.setSaccountno(sAccountno);
					info.setSpayeeacctno(sPayeeacctno);
					info.setNBSNAME(nbsName);
					info.setSpayeebankname(sPayeebankName);
					info.setSpayeename(sPayeeName);
					info.setNSTATUS(nStatus);
					info.setSNote(sNote);
					info.setDTEXECUTE(dtExecute);
					info.setDtconfirm(dtConfirm);
					info.setTimestamp(timestamp);
					
					String strNote = "";
					
					//存储列数据
					cellList = new ArrayList();
					
					strNote = info.getNote()== null?"":info.getNote().trim();
					
					//验证签名
					fInfo = OBSignatureConstant.transFinanceInfo(info);
					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();	
					boolean validate = facade.validateSignFromDB(fInfo.getTransType(), fInfo);

					PagerTools.returnCellList(cellList,info.getID()+"####"+info.getMAmount()+"####"+info.getDtModify());
					PagerTools.returnCellList(cellList,info.getID()+","+info.getNtranstype()+","+info.getID()+","+validate);
					PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(info.getNtranstype()));
					PagerTools.returnCellList(cellList,info.getSaccountno());
					PagerTools.returnCellList(cellList,"借");
					PagerTools.returnCellList(cellList,"￥"+info.getFormatMAmount());
					if(info.getNtranstype()==OBConstant.SettInstrType.DRIVEFIXDEPOSIT){ 
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
					}else if(info.getNtranstype()==OBConstant.SettInstrType.OPENFIXDEPOSIT) { 
						PagerTools.returnCellList(cellList,info.getSpayeeacctno());
						PagerTools.returnCellList(cellList,info.getNBSNAME());
						PagerTools.returnCellList(cellList,"&nbsp;");
					}else if(info.getNtranstype()==OBConstant.SettInstrType.OPENNOTIFYACCOUNT){ 
						PagerTools.returnCellList(cellList,info.getSpayeeacctno());
						PagerTools.returnCellList(cellList,info.getSpayeename());
						PagerTools.returnCellList(cellList,"&nbsp;");
					}else if(info.getNtranstype()==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT){ 
						PagerTools.returnCellList(cellList,info.getSpayeeacctno());
						PagerTools.returnCellList(cellList,info.getSpayeename());
						PagerTools.returnCellList(cellList,"&nbsp;");
					}else if(info.getNtranstype()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY){ 
						PagerTools.returnCellList(cellList,info.getSpayeeacctno());
						PagerTools.returnCellList(cellList,info.getSpayeename());
						PagerTools.returnCellList(cellList,info.getSpayeebankname());
					}else if(info.getNtranstype()==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW){
						if(info.getSpayeebankname()==null){ 
							PagerTools.returnCellList(cellList,info.getSpayeeacctno());
							PagerTools.returnCellList(cellList,info.getSpayeename());
							PagerTools.returnCellList(cellList,"&nbsp;");
						}else{ 
							PagerTools.returnCellList(cellList,info.getSpayeeacctno());
							PagerTools.returnCellList(cellList,info.getSpayeename());
							PagerTools.returnCellList(cellList,info.getSpayeebankname());
						} 
					}else if(info.getNtranstype()==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER){
						if(info.getSpayeebankname()==null){ 
							PagerTools.returnCellList(cellList,info.getSpayeeacctno());
							PagerTools.returnCellList(cellList,info.getSpayeename());
							PagerTools.returnCellList(cellList,"&nbsp;");
						}else{ 
							PagerTools.returnCellList(cellList,info.getSpayeeacctno());
							PagerTools.returnCellList(cellList,info.getSpayeename());
							PagerTools.returnCellList(cellList,info.getSpayeebankname());
						} 
					} 
					PagerTools.returnCellList(cellList,OBConstant.SettInstrStatus.getName(info.getNSTATUS()));
					PagerTools.returnCellList(cellList,strNote);
					PagerTools.returnCellList(cellList,info.getFormatDTEXECUTE());
					PagerTools.returnCellList(cellList,info.getFormatDtconfirm());
					
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					rowInfo.setValidate(validate);
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	private String turn2Red(String str){
		return "<font color='red'>"+str+"</font>";
	}
	
	public PagerInfo queryBatchCheckInfo(FinanceInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = queryCheckInfoDao.queryBatchCheckInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", qInfo);
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "batchCheckInfoResultSetHandle", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList batchCheckInfoResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		FinanceInfo info = (FinanceInfo)map.get("info");
		String strStartDate = DataFormat.formatDate(info.getDtDepositBillCheckdate());
		String strEndDate = DataFormat.formatDate(info.getDtDepositBillInputdate());
		String sBatchNo = "";
		String confirmUserName = "";
		String confirmDate = "";
		double amount = 0.0;
		int count = -1;
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					sBatchNo = rs.getString("sbatchno");
					confirmUserName = rs.getString("sname");
					confirmDate = rs.getString("dtconfirm");
					amount = rs.getDouble("mamount");
					count = rs.getInt("ncount");
					
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,((sBatchNo!=null)?sBatchNo:"")+","+strStartDate+","+strEndDate+","+sBatchNo);
					
					PagerTools.returnCellList(cellList,(confirmUserName!=null)?confirmUserName:"");
					
					PagerTools.returnCellList(cellList,(confirmDate!=null)?confirmDate:"");
					
					PagerTools.returnCellList(cellList,DataFormat.formatAmount(amount));
					
					PagerTools.returnCellList(cellList,count);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public PagerInfo queryBatchCheckInfoDetail(FinanceInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			
			sql = queryCheckInfoDao.queryBatchCheckInfoDeatilSQL(qInfo);
			
			paramMap.put("qInfo", qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "batchCheckInfoDetailResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList batchCheckInfoDetailResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		//QueryAccountResultInfo info = null;
		
		long id = -1;
		double mAmount = 0.0;
		Timestamp dtModify = null;
		long nTranstype = -1;
		String sAccountno = "";
		String sPayeeacctno = "";
		String nbsName = "";
		String sPayeeName = "";
		String sPayeebankName = "";
		long nStatus = -1;
		String sNote = "";
		Timestamp dtExecute = null;
		Timestamp dtConfirm = null;
		String DepositNo =  "";
		long Npayeeacctid = -1;
		long npayeracctid = -1;
		long InterestPayeeAcctID = -1;
		long RemitType = -1;
		long InterestRemitType = -1;
		long confirmUserID = -1;
		long noticeday = -1;
		String depositBillNo =  "";
		long depositBillPeriod = -1;
		Timestamp depositBillStartDate = null;
		long depositInterestDeal = -1;
		long depositInterestToAccountID = -1;
		long fixedDepositTime = -1;
		String signatureValue =  "";
		
		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);    
		FinanceInfo fInfo = null;
		boolean blnNotBeFalsified = true;
		int i = 0;
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					
					FinanceInfo info = new FinanceInfo();
					
					info.setID(rs.getLong("id"));
					info.setCurrencyID(rs.getLong("ncurrencyid"));
					info.setPayerAcctNo(rs.getString("payeracctno"));
					info.setPayerName(rs.getString("payername"));
					info.setAmount(rs.getDouble("mamount"));
					info.setPayeeAcctNo(rs.getString("payeeacctno"));
					info.setPayeeName(rs.getString("payeename"));
					info.setNote(rs.getString("snote"));
					info.setSStatus(String.valueOf(rs.getLong("nstatus")));
					info.setRemitType(rs.getLong("nremittype"));
					info.setPayeeProv(rs.getString("spayeeprov"));   //华联银行汇款储存收款方：汇入省
					info.setPayeeCity(rs.getString("spayeecity"));   //华联银行汇款储存收款方：汇入市
					info.setPayeeBankName(rs.getString("spayeebankname"));//华联银行汇款储存收款方：汇入行	
					info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间 
					info.setApplyCode(rs.getString("sapplycode"));
					info.setSPayeeBankCNAPSNO(rs.getString("spayeebankcnapsno"));
					info.setSPayeeBankExchangeNO(rs.getString("spayeebankexchangeno"));
					info.setSPayeeBankOrgNO(rs.getString("spayeebankorgno"));
					info.setTransType(rs.getLong("ntranstype"));
					info.setSignatureValue(rs.getString("signaturevalue"));
					info.setPayerAcctID(rs.getLong("npayeracctid"));
					info.setConfirmUserID(rs.getLong("nconfirmuserid"));
					info.setSBatchNo(rs.getString("sbatchno"));
					info.setTimestamp(rs.getLong("timestamp"));
 					String strNote = "";
					
 					i++;
 					
					//存储列数据
					cellList = new ArrayList();
					
					strNote = info.getNote()== null?"":info.getNote().trim();
					
					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();	
					boolean validate = facade.validateSignFromDB(info.getTransType(), info);				

					PagerTools.returnCellList(cellList,info.getID()+"####"+validate+"####"+info.getDtModify());
					PagerTools.returnCellList(cellList,i+"");
					PagerTools.returnCellList(cellList,Constant.CurrencyType.getName(info.getCurrencyID()));
					PagerTools.returnCellList(cellList,(info.getPayerAcctNo()!=null)?info.getPayerAcctNo():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayerName()!=null)?info.getPayerName():"&nbsp;");
					PagerTools.returnCellList(cellList,OBConstant.SettRemitType.getName(info.getRemitType())!=null||!OBConstant.SettRemitType.getName(info.getRemitType()).equals("")?OBConstant.SettRemitType.getName(info.getRemitType()):"&nbsp;");
					
					PagerTools.returnCellList(cellList,(DataFormat.formatAmount(info.getAmount())!=null)?DataFormat.formatAmount(info.getAmount()):"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeAcctNo()!=null)?info.getPayeeAcctNo():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeName()!=null)?info.getPayeeName():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeProv()!=null)?info.getPayeeProv():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeCity()!=null)?info.getPayeeCity():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeBankName()!=null)?info.getPayeeBankName():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getSPayeeBankCNAPSNO()!=null)?info.getSPayeeBankCNAPSNO():"&nbsp;");
					
					PagerTools.returnCellList(cellList,(info.getSPayeeBankOrgNO()!=null)?info.getSPayeeBankOrgNO():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getSPayeeBankExchangeNO()!=null)?info.getSPayeeBankExchangeNO():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getNote()!=null)?info.getNote():"&nbsp;");
					String SStatus=null;
					if(info.getSStatus().equals("1"))
					{
						SStatus=OBConstant.SettInstrStatus.getName(Long.valueOf(info.getSStatus()).longValue());
					}
					PagerTools.returnCellList(cellList,SStatus);
					
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					rowInfo.setValidate(validate);
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public PagerInfo queryUncheckInfo(QueryCapForm qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			
			sql = queryCheckInfoDao.queryUncheckInfo(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "uncheckInfoResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList uncheckInfoResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		int iCount = 0;//计数器
		String strDataLast = "";//前一个指针
		String strData = "";//当前指针
      	String strNote = "";
		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
		FinanceInfo fInfo = null;
		boolean blnNotBeFalsified = true;
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					iCount++;
					blnNotBeFalsified = true;					
				  	
					FinanceInfo info = new FinanceInfo();
					OBFinanceInstrDao dao = new OBFinanceInstrDao();
					
					info.setID(rs.getLong("ID")); // 指令序号
					info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
					//				下属单位
					if (info.getChildClientID() > 0)
					{
						info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
						info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
					}
					info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
					info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
					// 收款方名称
					info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
					info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME"));
					info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户ID
					info.setContractID(rs.getLong("NCONTRACTID")); // 贷款合同id
					info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); // 贷款合同编号
					info.setLoanNoteID(rs.getLong("NLOANNOTEID")); //放款通知单id
					info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单号
					info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(rs.getLong("NSTATUS")); // 指令状态
					info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(rs.getString("CPF_STRANSNO")); //交易号
					info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
					info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
					info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
					info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
					info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
					info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名
					info.setReject(rs.getString("CPF_SREJECT")); // CPF-拒绝原因
					info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
					info.setSDepositBillNo(rs.getString("sDepositBillNo"));
					info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
					info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
					info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
					info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
					info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
					info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
					info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
					info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间  ---  add   by  zhanglei  2010.06.03
					info.setSignatureValue(rs.getString("signaturevalue"));
					info.setSignatureOriginalValue(rs.getString("signatureoriginalvalue"));
					info.setTimestamp(rs.getLong("timestamp"));
					
					
					//获取收款方和付款方的详细信息
					PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
					payerInfo = dao.getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
					payeeInfo = dao.getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
					interestpPayeeInfo = dao.getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
					info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
					info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
					info.setPayerName(payerInfo.getAccountName()); // 付款方名称
					info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
					info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
					info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
					info.setPayeeProv(payeeInfo.getProv()); // 汇入省
					info.setPayeeCity(payeeInfo.getCity()); // 汇入市
					info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
					info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
					info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
					info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
					info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
					info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
					info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
					info.setSBatchNo(rs.getString("SBATCHNO"));//批量付款批次号 modify by xwhe 2008-11-10
					info.setSDepositBillPeriod(rs.getLong("fixednextperiod"));  //定期存款期限
					info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));  //存单起始日
					info.setSDepositInterestDeal(rs.getLong("fixedinterestdeal"));//利息处理方式
					info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID")); //利息转至活期账户id
					info.setNoticeDay(rs.getLong("NNOTICEDAY"));  //通知存款品种
					
					
					strData = DataFormat.getDateString(info.getConfirmDate());
				  	strNote = info.getNote()== null?"":info.getNote().trim(); //add by zhouxiang
				  	
					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();	
					boolean validate = facade.validateSignFromDB(info.getTransType(), info);			  	
					
					if (iCount == 1)
		            {
						//存储行数据
						strDataLast = strData;
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"0");
						PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
						PagerTools.returnCellList(cellList,"<B>"+((DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"")+"</B>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						//返回数据
						resultList.add(rowInfo);
		            }
					
					if (!strDataLast.equals(strData))
		            {
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"0");
						PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
						PagerTools.returnCellList(cellList,"<B>"+((DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"")+"</B>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						//返回数据
						resultList.add(rowInfo);
						strDataLast = strData;
					}
					
					//存储列数据
					cellList = new ArrayList();
						
					
					PagerTools.returnCellList(cellList,iCount+"####"+info.getID()+"####"+validate+"####"+info.getDtModify());
					PagerTools.returnCellList(cellList,info.getID()+","+validate+","+info.getTransType()+","+info.getID());
					PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(info.getTransType()));
					PagerTools.returnCellList(cellList,NameRef.getNoLineAccountNo(info.getPayerAcctNo()));
					PagerTools.returnCellList(cellList,"借");
					PagerTools.returnCellList(cellList,"￥"+info.getFormatAmount());
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getPayeeName()));
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getNoLineAccountNo(info.getPayeeAcctNo())));
					PagerTools.returnCellList(cellList,info.getFormatExecuteDate());
					PagerTools.returnCellList(cellList,(info.getPayeeProv()!=null)?info.getPayeeProv():"&nbsp;");
					PagerTools.returnCellList(cellList,strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote);
					
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					rowInfo.setValidate(validate);
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public PagerInfo queryUncheckDetailInfo(FinanceInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			sql = "select 1 from userinfo";
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			paramMap.put("qInfo", qInfo);
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "uncheckDetailInfoResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList uncheckDetailInfoResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		try{
			
			FinanceInfo qInfo = (FinanceInfo)paramMap.get("qInfo");
			/* 初始化信息查询类 */
			OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			FinanceInfo info = obFinanceInstrDao.findByID(qInfo.getID(),qInfo.getUserID(),qInfo.getCurrencyID());//如果没有这个lID流水号则info ＝null
			boolean isbill = false;
			if (info.getNDepositBillInputuserId() > 0)
				isbill = true;
			int iTransType = Integer.valueOf(String.valueOf(qInfo.getTransType()));
			
			switch (iTransType)
			{
				case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF:
				case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY:
				case (int)OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER:
				case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://1;资金划拨 
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE)
							|| // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK)
							|| // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认
							(info.getStatus() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中
							(info.getStatus() == OBConstant.SettInstrStatus.FINISH)
							|| //已完成
							(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)
							|| //已拒绝 
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE)
							|| // 已确认,未复核(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
							|| // 已复核(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)
							|| //已完成(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝(换开)  		   
						{
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							if (isbill) {
								PagerTools.returnCellList(cellList,NameRef.getUserNameByID(info.getNDepositBillInputuserId()));
							} else {
								PagerTools.returnCellList(cellList,info.getConfirmUserName());
							}
							PagerTools.returnCellList(cellList,"录入");
							if (isbill) {
								PagerTools.returnCellList(cellList,info.getDtDepositBillInputdate().toString().substring(0, 19));
							} else {
								PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							}
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
						if (((info.getStatus() == OBConstant.SettInstrStatus.CHECK)
							|| // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认
							(info.getStatus() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中
							(info.getStatus() == OBConstant.SettInstrStatus.FINISH) || //已完成
							(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝 
							&& (info.getNDepositBillInputuserId() == 0)) 
						{
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName() == null ? "机核": info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
						if (((info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
							|| // 已复核(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //已完成(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
							&& //已拒绝(换开)
							(info.getStatus() == OBConstant.SettInstrStatus.FINISH)
							&& (info.getDtDepositBillCheckdate() != null)) 
						{
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							if (isbill) {
								PagerTools.returnCellList(cellList,NameRef.getUserNameByID(info.getNDepositBillCheckuserId()));
							} else {
								PagerTools.returnCellList(cellList,"&nbsp;");
							}
							PagerTools.returnCellList(cellList,"复核");
							if (isbill) {
								PagerTools.returnCellList(cellList,info.getDtDepositBillCheckdate().toString().substring(0,19));
							} else {
								PagerTools.returnCellList(cellList,"&nbsp;");
							}
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认
							(info.getStatus() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中
							(info.getStatus() == OBConstant.SettInstrStatus.FINISH) || //已完成
							(info.getStatus() == OBConstant.SettInstrStatus.REFUSE))
							&& //已拒绝
							(info.getSignUserName() != null && !"".equals(info.getSignUserName()))
							&& (info.getNDepositBillInputuserId() == 0)) 
						{
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
						if (((info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
							|| // 已签认(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
							|| //处理中(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //已完成(换开)
							(info.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
							&& //已拒绝(换开)
							(info.getStatus() == OBConstant.SettInstrStatus.FINISH)
							&& (info.getDtDepositBillSignDate() != null)) 
						{
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							if (isbill) {
								PagerTools.returnCellList(cellList,NameRef.getUserNameByID(info.getNDepositBillSignUserID()));
							} else {
								PagerTools.returnCellList(cellList,"&nbsp;");
							}
							PagerTools.returnCellList(cellList,"签认");
							if (isbill) {
								PagerTools.returnCellList(cellList,info.getDtDepositBillSignDate().toString().substring(0, 19));
							} else {
								PagerTools.returnCellList(cellList,"&nbsp;");
							}
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
					}
					break;
				case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER:			
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE))	//已拒绝		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName()==null?"机核":info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
		        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
		        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
		        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
							(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName()==null?"":info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
		        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
		        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
		        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
							(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName()==null?"机核":info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
		        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
		        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
		        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
							(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
								(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
					        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
					        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
					        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
					        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
					        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
							{ 	
								//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"1");
								PagerTools.returnCellList(cellList,info.getConfirmUserName());
								PagerTools.returnCellList(cellList,"录入");
								PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//返回数据
								resultList.add(rowInfo);
							}
					
							if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
									(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
					        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
					        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
					        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
							{ 
								//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"2");
								PagerTools.returnCellList(cellList,info.getCheckUserName());
								PagerTools.returnCellList(cellList,"复核");
								PagerTools.returnCellList(cellList,info.getFormatCheckDate());
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//返回数据
								resultList.add(rowInfo);
					    	}
							
							if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
									(info.getSignUserName() != null))
				         	{ 
								//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"3");
								PagerTools.returnCellList(cellList,info.getSignUserName());
								PagerTools.returnCellList(cellList,"签认");
								PagerTools.returnCellList(cellList,info.getFormatSignDate());
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//返回数据
								resultList.add(rowInfo);
					    	}
						}
					break;
				case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE:
					{
					if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.YTLOANRECEIVE:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.DRIVEFIXDEPOSIT:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				case (int)OBConstant.SettInstrType.CHANGEFIXDEPOSIT:
					{
						if ((info.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
							(info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
							(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        	(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        	(info.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
				        	(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
				        	(info.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
						{ 	
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"1");
							PagerTools.returnCellList(cellList,info.getConfirmUserName());
							PagerTools.returnCellList(cellList,"录入");
							PagerTools.returnCellList(cellList,info.getFormatConfirmDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
						}
				
						if ((info.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
								(info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
				        		(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
				        		(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
				        		(info.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
		        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
		        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
		        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
							(info.getSignUserName() != null)) //已拒绝
						{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"2");
							PagerTools.returnCellList(cellList,info.getCheckUserName());
							PagerTools.returnCellList(cellList,"复核");
							PagerTools.returnCellList(cellList,info.getFormatCheckDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
						
						if (((info.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
			        			(info.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
			        			(info.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
			        			(info.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
								(info.getSignUserName() != null))
			         	{ 
							//存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,"3");
							PagerTools.returnCellList(cellList,info.getSignUserName());
							PagerTools.returnCellList(cellList,"签认");
							PagerTools.returnCellList(cellList,info.getFormatSignDate());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//返回数据
							resultList.add(rowInfo);
				    	}
					}
					break;
				default :
					break;
			}
			
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	public PagerInfo querySignInfo(QueryCapForm qInfo) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			
			sql = queryCheckInfoDao.querySignInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "signInfoResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	
	}
	
	
	public ArrayList signInfoResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		int iCount = 0;//计数器
		String strDataLast = "";//前一个指针
		String strData = "";//当前指针
      	String strNote = "";
		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
		FinanceInfo fInfo = null;
		boolean blnNotBeFalsified = true;
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					iCount++;
					blnNotBeFalsified = true;					
				  	
					FinanceInfo info = new FinanceInfo();
					OBFinanceInstrDao dao = new OBFinanceInstrDao();
					
					info.setID(rs.getLong("ID")); // 指令序号
					info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
					//				下属单位
					if (info.getChildClientID() > 0)
					{
						info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
						info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
					}
					info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
					info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
					// 收款方名称
					info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
					info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME"));
					info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户ID
					info.setContractID(rs.getLong("NCONTRACTID")); // 贷款合同id
					info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); // 贷款合同编号
					info.setLoanNoteID(rs.getLong("NLOANNOTEID")); //放款通知单id
					info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单号
					info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(rs.getLong("NSTATUS")); // 指令状态
					info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(rs.getString("CPF_STRANSNO")); //交易号
					info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
					info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
					info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
					info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
					info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
					info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名
					info.setReject(rs.getString("CPF_SREJECT")); // CPF-拒绝原因
					info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
					info.setSDepositBillNo(rs.getString("sDepositBillNo"));
					info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
					info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
					info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
					info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
					info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
					info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
					info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
					info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间  ---  add   by  zhanglei  2010.06.03
					info.setSignatureValue(rs.getString("signaturevalue"));
					info.setSignatureOriginalValue(rs.getString("signatureoriginalvalue"));
					info.setTimestamp(rs.getLong("timestamp"));
					//获取收款方和付款方的详细信息
					PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
					PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
					payerInfo = dao.getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
					payeeInfo = dao.getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
					interestpPayeeInfo = dao.getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
					info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
					info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
					info.setPayerName(payerInfo.getAccountName()); // 付款方名称
					info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
					info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
					info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
					info.setPayeeProv(payeeInfo.getProv()); // 汇入省
					info.setPayeeCity(payeeInfo.getCity()); // 汇入市
					info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
					info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
					info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
					info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
					info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
					info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
					info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
					info.setSBatchNo(rs.getString("SBATCHNO"));//批量付款批次号 modify by xwhe 2008-11-10
					info.setSDepositBillPeriod(rs.getLong("fixednextperiod"));  //定期存款期限
					info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));  //存单起始日
					info.setSDepositInterestDeal(rs.getLong("fixedinterestdeal"));//利息处理方式
					info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID")); //利息转至活期账户id
					info.setNoticeDay(rs.getLong("NNOTICEDAY"));  //通知存款品种
					
					
					strData = DataFormat.getDateString(info.getConfirmDate());
				  	strNote = info.getNote()== null?"":info.getNote().trim(); //add by zhouxiang

					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();	
					boolean validate = facade.validateSignFromDB(info.getTransType(), info);	
				  	
					if (iCount == 1)
		            {
						//存储行数据
						strDataLast = strData;
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"0");
						PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
						PagerTools.returnCellList(cellList,"<B>"+((DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"")+"</B>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						//返回数据
						resultList.add(rowInfo);
		            }
					
					if (!strDataLast.equalsIgnoreCase(strData))
		            {
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,"0");
						PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
						PagerTools.returnCellList(cellList,"<B>"+((DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"")+"</B>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
						//返回数据
						resultList.add(rowInfo);
						strDataLast = strData;
					}
					
					//存储列数据
					cellList = new ArrayList();

					PagerTools.returnCellList(cellList,iCount+"####"+info.getID()+"####"+"false"+"####"+info.getDtModify());
					PagerTools.returnCellList(cellList,info.getID()+","+validate+","+info.getTransType()+","+info.getID());
					PagerTools.returnCellList(cellList,strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote);
					PagerTools.returnCellList(cellList,NameRef.getNoLineAccountNo(info.getPayerAcctNo()));
					PagerTools.returnCellList(cellList,"借");
					PagerTools.returnCellList(cellList,"￥"+info.getFormatAmount()==""?"0.00":info.getFormatAmount());
					PagerTools.returnCellList(cellList,DataFormat.formatString(info.getPayeeName()));
					PagerTools.returnCellList(cellList,DataFormat.formatString(NameRef.getNoLineAccountNo(info.getPayeeAcctNo())));
					PagerTools.returnCellList(cellList,info.getFormatExecuteDate());
					PagerTools.returnCellList(cellList,(info.getPayeeProv()!=null)?info.getPayeeProv():"&nbsp;");
						
					
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					rowInfo.setValidate(validate);
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	public PagerInfo queryBatchSignInfo(FinanceInfo qInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("qInfo", qInfo);
			
			sql = queryCheckInfoDao.queryBatchSignInfoSQL(qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", qInfo);
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "batchSignInfoResultSetHandle", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
		
	}
	
	
	public ArrayList batchSignInfoResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		FinanceInfo info = (FinanceInfo)map.get("info");
		String strStartDate = DataFormat.formatDate(info.getDtDepositBillCheckdate());
		String strEndDate = DataFormat.formatDate(info.getDtDepositBillInputdate());
		String sBatchNo = "";
		String confirmUserName = "";
		String confirmDate = "";
		double amount = 0.0;
		int count = -1;
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					sBatchNo = rs.getString("sbatchno");
					confirmUserName = rs.getString("sname");
					confirmDate = rs.getString("dtconfirm");
					amount = rs.getDouble("mamount");
					count = rs.getInt("ncount");
					
					
					//存储列数据
					cellList = new ArrayList();
					
					PagerTools.returnCellList(cellList,((sBatchNo!=null)?sBatchNo:"")+","+info.getStatus()+","+strStartDate+","+strEndDate+","+sBatchNo+","+info.getClientID()+","+info.getUserID()+","+info.getCurrencyID());
					
					PagerTools.returnCellList(cellList,(confirmUserName!=null)?confirmUserName:"");
					
					PagerTools.returnCellList(cellList,(confirmDate!=null)?confirmDate:"");
					
					PagerTools.returnCellList(cellList,DataFormat.formatAmount(amount));
					
					PagerTools.returnCellList(cellList,count);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	
	public PagerInfo queryBatchSignInfoDetail(FinanceInfo qInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			
			sql = queryCheckInfoDao.queryBatchSignInfoDeatilSQL(qInfo);
			
			paramMap.put("qInfo", qInfo);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "batchSignInfoDetailResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList batchSignInfoDetailResultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		//QueryAccountResultInfo info = null;
		
		long id = -1;
		double mAmount = 0.0;
		Timestamp dtModify = null;
		long nTranstype = -1;
		String sAccountno = "";
		String sPayeeacctno = "";
		String nbsName = "";
		String sPayeeName = "";
		String sPayeebankName = "";
		long nStatus = -1;
		String sNote = "";
		Timestamp dtExecute = null;
		Timestamp dtConfirm = null;
		String DepositNo =  "";
		long Npayeeacctid = -1;
		long npayeracctid = -1;
		long InterestPayeeAcctID = -1;
		long RemitType = -1;
		long InterestRemitType = -1;
		long confirmUserID = -1;
		long noticeday = -1;
		String depositBillNo =  "";
		long depositBillPeriod = -1;
		Timestamp depositBillStartDate = null;
		long depositInterestDeal = -1;
		long depositInterestToAccountID = -1;
		long fixedDepositTime = -1;
		String signatureValue =  "";
		
		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);    
		FinanceInfo fInfo = null;
		boolean blnNotBeFalsified = true;
		int i = 0;
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					
					FinanceInfo info = new FinanceInfo();
					
					info.setID(rs.getLong("id"));
					info.setCurrencyID(rs.getLong("ncurrencyid"));
					info.setPayerAcctNo(rs.getString("payeracctno"));
					info.setPayerName(rs.getString("payername"));
					info.setAmount(rs.getDouble("mamount"));
					info.setPayeeAcctNo(rs.getString("payeeacctno"));
					info.setPayeeName(rs.getString("payeename"));
					info.setNote(rs.getString("snote"));
					info.setSStatus(String.valueOf(rs.getLong("nstatus")));
					info.setTransType(rs.getLong("nremittype"));
					info.setPayeeProv(rs.getString("spayeeprov"));   //华联银行汇款储存收款方：汇入省
					info.setPayeeCity(rs.getString("spayeecity"));   //华联银行汇款储存收款方：汇入市
					info.setPayeeBankName(rs.getString("spayeebankname"));//华联银行汇款储存收款方：汇入行	
					info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间 
					info.setApplyCode(rs.getString("sapplycode"));
					info.setSPayeeBankCNAPSNO(rs.getString("spayeebankcnapsno"));
					info.setSPayeeBankExchangeNO(rs.getString("spayeebankexchangeno"));
					info.setSPayeeBankOrgNO(rs.getString("spayeebankorgno"));
					info.setTransType(rs.getLong("ntranstype"));
					info.setSignatureValue(rs.getString("signaturevalue"));
					info.setPayerAcctID(rs.getLong("npayeracctid"));
					info.setConfirmUserID(rs.getLong("nconfirmuserid"));
					info.setSBatchNo(rs.getString("sbatchno"));
					info.setTimestamp(rs.getLong("timestamp"));
 					String strNote = "";
					
 					i++;
 					
					//存储列数据
					cellList = new ArrayList();
					
					strNote = info.getNote()== null?"":info.getNote().trim();
					
					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();	
					boolean validate = facade.validateSignFromDB(info.getTransType(), info);			

					PagerTools.returnCellList(cellList,info.getID()+"####"+validate+"####"+info.getDtModify());
					PagerTools.returnCellList(cellList,i+"");
					PagerTools.returnCellList(cellList,Constant.CurrencyType.getName(info.getCurrencyID()));
					PagerTools.returnCellList(cellList,(info.getPayerAcctNo()!=null)?info.getPayerAcctNo():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayerName()!=null)?info.getPayerName():"&nbsp;");
					PagerTools.returnCellList(cellList,OBConstant.SettRemitType.getName(info.getTransType())!=null||!OBConstant.SettRemitType.getName(info.getTransType()).equals("")?OBConstant.SettRemitType.getName(info.getTransType()):"&nbsp;");
					
					PagerTools.returnCellList(cellList,(DataFormat.formatAmount(info.getAmount())!=null)?DataFormat.formatAmount(info.getAmount()):"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeAcctNo()!=null)?info.getPayeeAcctNo():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeName()!=null)?info.getPayeeName():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeProv()!=null)?info.getPayeeProv():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeCity()!=null)?info.getPayeeCity():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getPayeeBankName()!=null)?info.getPayeeBankName():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getSPayeeBankCNAPSNO()!=null)?info.getSPayeeBankCNAPSNO():"&nbsp;");
					
					PagerTools.returnCellList(cellList,(info.getSPayeeBankOrgNO()!=null)?info.getSPayeeBankOrgNO():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getSPayeeBankExchangeNO()!=null)?info.getSPayeeBankExchangeNO():"&nbsp;");
					PagerTools.returnCellList(cellList,(info.getNote()!=null)?info.getNote():"&nbsp;");
					String SStatus=null;
					if(info.getSStatus()!=null && !"".equals(info.getSStatus()))
					{
						SStatus=OBConstant.SettInstrStatus.getName(Long.valueOf(info.getSStatus()).longValue());
					}
					PagerTools.returnCellList(cellList,SStatus);
					
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					rowInfo.setValidate(validate);
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
	/**
	 * 申请指令查询
	 * @param queryCapForm
	 * @return PagerInfo
	 * @throws Exception
	 */
	public PagerInfo queryCapFormInfo(QueryCapForm queryCapForm,String currencySymbol) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			
			sql = queryCheckInfoDao.getQueryCapFormSQL(queryCapForm);
			
			paramMap.put("queryCapForm", queryCapForm);
			paramMap.put("currencySymbol", currencySymbol);
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QueryCheckInfoBiz.class, "queryCapFormInfoResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	/**
	 * 申请指令查询
	 * @param queryCapForm
	 * @return PagerInfo
	 * @throws Exception
	 */
	public ArrayList queryCapFormInfoResultSetHandle(ResultSet rs,Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		String sPreConfirmDate = "";    //前一个确认日期
	    String sConfirmDate = "";       //确认日期
	    String sPrePayerAcctNo = "";    //前一个活期存款
	    String sPayerAcctNo = "";       //活期存款
	    long lTransType = -1;
	    String strNote = "";			//add by zhouxiang
        String strReject = "";
        
	    Timestamp tsConfirmDate = null; //确认时间
	    long lTotalCount = 0;   //共有笔数
	    long lDeleteCount = 0;  //已删除笔数
	    long lUnCheckCount = 0; //未复核笔数
	    long lApprovalingCount = 0; //审批中笔数
		long lApprovaledCount = 0; //审批完成笔数
	    long lCheckCount = 0;   //已复核笔数
	    long lSignCount = 0;    //已签认笔数
	    long lOnGoingCount = 0; //处理中笔数
	    long lFinishedCount = 0;//已完成笔数
	    long lRefusedCount = 0; //已拒绝笔数
	    double dTotalAmount = 0;//总交易金额
	    double dLoanAmount = 0; //其中贷金额
	    double dDebitAmount = 0;//其中借金额
	    boolean blnNotBeFalsified = true;
	    
	    QueryCapForm queryCapForm = (QueryCapForm)paramMap.get("queryCapForm");
	    String currencySymbol = (String)paramMap.get("currencySymbol");
	    /* 初始化EJB */
        OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        /* 调用EJB方法获得查询结果 */
        Collection lstQuery = financeInstrDao.query(queryCapForm);
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
        
		try{
			Vector vctCapSummarize = new Vector(); //存放OBCapSummarizeInfo对象的集合
		    OBCapSummarizeInfo obCSI=null; //存放交易总结信息
		    switch((int) lTransType) {
		        case (int)-1:
		        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
				case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
		        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
		        case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
		        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
		        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
		        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
		        case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT:
		        case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT:
		        	if (listQuery != null) {
		                //根据排序规则，按格式显示所有记录
		                while(listQuery != null && listQuery.hasNext()) {
		                	FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
		                    blnNotBeFalsified = true;
		                    sPreConfirmDate = sConfirmDate;
		                    sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
		                    strNote = info.getNote()== null?"":info.getNote().trim();
		                    strReject = info.getReject() == null?"":info.getReject().trim();
		    				//if(isUseCertification)
		    				//{
		    				//	blnNotBeFalsified = OBSignatureUtil.validateSignature(info,certificationType);
		    				//}
		                    if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
		                    	if (!"".equals(sPreConfirmDate)) {
		                    		obCSI = new OBCapSummarizeInfo();
		                            obCSI.setConfirmDate(tsConfirmDate);    //确认时间
		                            obCSI.setTotalCount(lTotalCount);       //共有笔数
		                            //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
		                            obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
		                            obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
		                            obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
		                            obCSI.setCheckCount(lCheckCount);       //已复核笔数
		                            obCSI.setSignCount(lSignCount);         //已签认笔数
		                            obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
		                            obCSI.setFinishedCount(lFinishedCount); //已完成笔数
		                            obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
		                            obCSI.setTotalAmount(dTotalAmount);     //总交易金额
		                            obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
		                            obCSI.setDebitAmount(dDebitAmount);     //其中借金额
		                            lTotalCount = 0;    //共有笔数
		                            lDeleteCount = 0;   //已删除笔数
		                            lUnCheckCount = 0;  //未复核笔数
		                            lApprovalingCount = 0; //审批中笔数
		    						lApprovaledCount = 0; //审批完成笔数
		                            lCheckCount = 0;    //已复核笔数
		                            lSignCount = 0;     //已签认笔数
		                            lOnGoingCount = 0;  //处理中笔数
		                            lFinishedCount = 0; //已完成笔数
		                            lRefusedCount = 0;  //已拒绝笔数
		                            dTotalAmount = 0;   //总交易金额
		                            dLoanAmount = 0;    //其中贷金额
		                            dDebitAmount = 0;   //其中借金额
		                            vctCapSummarize.addElement(obCSI);
		                        }
		                    	//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
								PagerTools.returnCellList(cellList,"<B>"+sConfirmDate+"</B>");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
								//返回数据
								resultList.add(rowInfo);
		                    }
		                    sPrePayerAcctNo = sPayerAcctNo;
		                    sPayerAcctNo = info.getPayerAcctNo();
		                    if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
		                        (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {    
		                    	//存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"&nbsp;");		
								PagerTools.returnCellList(cellList,"<B>账号：</B>");
								PagerTools.returnCellList(cellList,"<B>"+(sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":NameRef.getNoLineAccountNo(sPayerAcctNo))+"</B>");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
								//返回数据
								resultList.add(rowInfo);
		                    }
		                    //表单内容
		                    //存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,info.getID()+","+info.getID()+","+info.getTransType()+","+blnNotBeFalsified);
				            if (lTransType == -1) {
				            	if(info.getTransType()==2){
				            		if(info.getSBatchNo()!=null && info.getSBatchNo().trim().length() > 0) {
				            			PagerTools.returnCellList(cellList,"批量付款-银行汇款");
				            		}else{       
				            			PagerTools.returnCellList(cellList,"逐笔付款-银行汇款");
				            		}
				            	}else if(info.getTransType()==3){
				            		if(info.getSBatchNo()!=null && info.getSBatchNo().trim().length() > 0) {
				            			PagerTools.returnCellList(cellList,"批量付款-内部转账");
				            		}else{
				            			PagerTools.returnCellList(cellList,"逐笔付款-内部转账");           		           		
				            		}
				            	}else{           		
				            		PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(info.getTransType()));    
				            	}
				            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER){
				            	PagerTools.returnCellList(cellList,info.getChildClientName());
							}else if(lTransType==OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT){
								PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(OBConstant.SettInstrType.BILLOPENFIXDEPOSIT));
				            }else{
				            	if(info.getTransType()==2){           		
				            		if(info.getSBatchNo()!=null && info.getSBatchNo().trim().length() > 0){
				            			PagerTools.returnCellList(cellList,"批量付款-银行汇款");
				            		}else{
				            			PagerTools.returnCellList(cellList,"逐笔付款-银行汇款");
				            		}
				            	}else if(info.getTransType()==3){           		
				            		if(info.getSBatchNo()!=null && info.getSBatchNo().trim().length() > 0){
				            			PagerTools.returnCellList(cellList,"批量付款-内部转账");
				            		}else{
				            			PagerTools.returnCellList(cellList,"逐笔付款-内部转账");
				            		}
				            	}else{           		
				            		PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(info.getTransType()));
				            	}
				            }
				            PagerTools.returnCellList(cellList,"借");
				            PagerTools.returnCellList(cellList,currencySymbol+info.getFormatAmount());
				            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT
				            		||lTransType==OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) {
				            	PagerTools.returnCellList(cellList,info.getFixedDepositTime()>10000?(info.getFixedDepositTime()-10000)+"天":info.getFixedDepositTime()+"个月");
				            } else {
				            	PagerTools.returnCellList(cellList,info.getPayeeName()==null || info.getPayeeName()==""?"&nbsp;":info.getPayeeName());
				            }
				            PagerTools.returnCellList(cellList,info.getPayeeAcctNo()==null || info.getPayeeAcctNo()==""?"&nbsp;":NameRef.getNoLineAccountNo(info.getPayeeAcctNo()));
				            if(lTransType==OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT){ 
				            	PagerTools.returnCellList(cellList,OBConstant.SettInstrStatus.getName(info.getNDepositBillStatusId()));
				            }else{ 
				            	PagerTools.returnCellList(cellList,OBConstant.SettInstrStatus.getName(info.getStatus()));
				            } 
				            PagerTools.returnCellList(cellList,info.getEbankStatus()==-1?"&nbsp;":OBConstant.BankInstructionStatus.getName(info.getEbankStatus()));
				            PagerTools.returnCellList(cellList,info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo());
						   //if(strNote.length()<=6){
								PagerTools.returnCellList(cellList,strNote);
							//}else{
							//	PagerTools.returnCellList(cellList,strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote);
								//<td width="75" height="20" nowrap align="center" id="<%=info.getID()%>"
								// 	onmouseover="showHelper('<%="#"+info.getID()%>', '汇款用途', '<%=strNote %>',50)" onmouseout="$('#_Popup_help').remove()" >
								//	<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
								//</td>
							//}
				            if (lTransType == -1){
				            	if(strReject.length()<=6)
								{
				            		PagerTools.returnCellList(cellList,strReject==null || strReject==""?"&nbsp;":strReject);
								}else{	
									PagerTools.returnCellList(cellList,strReject.length()>6?strReject.substring(0,6)+"<font color='red'>...</font>":strReject);
								}
				            }else{
								if(strReject.length()<=6)
								{
									PagerTools.returnCellList(cellList,strReject==null || strReject==""?"&nbsp;":strReject);
								}else{	
									PagerTools.returnCellList(cellList,strReject.length()>6?strReject.substring(0,6)+"<font color='red'>...</font>":strReject);
								}
				            }	
				            //存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
							//返回数据
							resultList.add(rowInfo);
							
				            switch ((int) info.getStatus()) {
		                    	case (int) OBConstant.SettInstrStatus.DELETE:
		                    		lDeleteCount++;//已删除
		                    	break;
			                    case (int) OBConstant.SettInstrStatus.SAVE:
			                        lUnCheckCount++;//未复核笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.APPROVALING:
			                        lApprovalingCount++;//审批中笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.APPROVALED:
			                        lApprovaledCount++;//审批完成笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.CHECK:
			                        lCheckCount++;//已复核笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.SIGN:
			                        lSignCount++;//已签认笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.DEAL:
			                        lOnGoingCount++;//处理中笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.FINISH:
			                        lFinishedCount++;//已完成笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.REFUSE:
			                        lRefusedCount++;//已拒绝笔数
			                    break;
			                    default :
			                    break;
			                }
			                if (info.getTransType() == -1000) {
			                    dLoanAmount += info.getAmount(); //其中贷金额
			                } else {
			                    dDebitAmount += info.getAmount();//其中借金额
			                }
			                dTotalAmount += info.getAmount();//按日期分类计算出的的总交易金额
			                lTotalCount++;//共有笔数
			                tsConfirmDate = info.getConfirmDate();
			                if (listQuery != null && !listQuery.hasNext()) {
		                        obCSI = new OBCapSummarizeInfo();
		                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
		                        obCSI.setTotalCount(lTotalCount);       //共有笔数
		                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
		                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
		                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
		                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
		                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
		                        obCSI.setSignCount(lSignCount);         //已签认笔数
		                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
		                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
		                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
		                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
		                        obCSI.setLoanAmount(dLoanAmount);       //其中贷金额
		                        obCSI.setDebitAmount(dDebitAmount);     //其中借金额
		                        vctCapSummarize.addElement(obCSI);
			                }
		                }
		        	}
		        break;
		        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
		        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
		        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
				case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
					if (listQuery != null) {
						 //根据排序规则，按格式显示所有记录
			            while(listQuery != null && listQuery.hasNext()) {
			                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
			                sPreConfirmDate = sConfirmDate;
			                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
			                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
			                    if (!"".equals(sPreConfirmDate)) {
			                        obCSI = new OBCapSummarizeInfo();
			                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
			                        obCSI.setTotalCount(lTotalCount);       //共有笔数
			                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
			                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
			                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
			                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
			                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
			                        obCSI.setSignCount(lSignCount);         //已签认笔数
			                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
			                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
			                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
			                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
			                        lTotalCount = 0;    //共有笔数
			                        lDeleteCount = 0;   //已删除笔数
			                        lUnCheckCount = 0;  //未复核笔数
			                        lApprovalingCount = 0; //审批中笔数
									lApprovaledCount = 0; //审批完成笔数
			                        lCheckCount = 0;    //已复核笔数
			                        lSignCount = 0;     //已签认笔数
			                        lOnGoingCount = 0;  //处理中笔数
			                        lFinishedCount = 0; //已完成笔数
			                        lRefusedCount = 0;  //已拒绝笔数
			                        dTotalAmount = 0;   //总交易金额
			                        vctCapSummarize.addElement(obCSI);
			                    }
				                //存储列数据
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,"<B>提交日期：</B>");		
								PagerTools.returnCellList(cellList,"<B>"+sConfirmDate==null || sConfirmDate==""?"&nbsp;":sConfirmDate+"</B>");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								PagerTools.returnCellList(cellList,"&nbsp;");
								//存储行数据
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
								//返回数据
								resultList.add(rowInfo);
			                }
			                //存储列数据
							cellList = new ArrayList();
							PagerTools.returnCellList(cellList,info.getID()+","+info.getID()+","+info.getTransType());		
							PagerTools.returnCellList(cellList,OBConstant.SettInstrType.getName(info.getTransType()));
							PagerTools.returnCellList(cellList,info.getLoanContractNo()==null || info.getLoanContractNo()==""?"&nbsp;":info.getLoanContractNo());
							PagerTools.returnCellList(cellList,info.getPayDate()==null?"&nbsp;":DataFormat.getDateString(info.getPayDate()));
							if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
								double interest = info.getRealInterest() + info.getRealCompoundInterest() + 
									info.getRealOverdueInterest() + info.getRealSuretyFee() + info.getRealCommission();
								PagerTools.returnCellList(cellList,currencySymbol+(interest==0.0?"&nbsp;&nbsp;":DataFormat.formatDisabledAmount(interest)));
							}else{
								PagerTools.returnCellList(cellList,currencySymbol+info.getFormatAmount());
							}
							PagerTools.returnCellList(cellList,OBConstant.SettInstrStatus.getName(info.getStatus()));
							PagerTools.returnCellList(cellList,info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo());
							PagerTools.returnCellList(cellList,info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject());
							//存储行数据
							rowInfo = new ResultPagerRowInfo();
							rowInfo.setCell(cellList);
							//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
							//返回数据
							resultList.add(rowInfo);
							switch ((int) info.getStatus()) {
			                    case (int) OBConstant.SettInstrStatus.DELETE:
			                        lDeleteCount++;//已删除
			                    break;
			                    case (int) OBConstant.SettInstrStatus.SAVE:
			                        lUnCheckCount++;//未复核笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.APPROVALING:
			                        lApprovalingCount++;//审批中笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.APPROVALED:
			                        lApprovaledCount++;//审批完成笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.CHECK:
			                        lCheckCount++;//已复核笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.SIGN:
			                        lSignCount++;//已签认笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.DEAL:
			                        lOnGoingCount++;//处理中笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.FINISH:
			                        lFinishedCount++;//已完成笔数
			                    break;
			                    case (int) OBConstant.SettInstrStatus.REFUSE:
			                        lRefusedCount++;//已拒绝笔数
			                    break;
			                    default :
			                    break;
							}
							dTotalAmount += info.getAmount();//按日期分类计算出的的总交易金额
			                lTotalCount++;//共有笔数
			                tsConfirmDate = info.getConfirmDate();
			                if (listQuery != null && !listQuery.hasNext()) {
		                        obCSI = new OBCapSummarizeInfo();
		                        obCSI.setConfirmDate(tsConfirmDate);    //确认时间
		                        obCSI.setTotalCount(lTotalCount);       //共有笔数
		                        //obCSI.setDeleteCount(lDeleteCount);     //已删除笔数
		                        obCSI.setUnCheckCount(lUnCheckCount);   //未复核笔数
		                        obCSI.setLApprovalingCount(lApprovalingCount); //审批中笔数
		                        obCSI.setLApprovaledCount(lApprovaledCount);  //审批完成笔数
		                        obCSI.setCheckCount(lCheckCount);       //已复核笔数
		                        obCSI.setSignCount(lSignCount);         //已签认笔数
		                        obCSI.setOnGoingCount(lOnGoingCount);   //处理中笔数
		                        obCSI.setFinishedCount(lFinishedCount); //已完成笔数
		                        obCSI.setRefusedCount(lRefusedCount);   //已拒绝笔数
		                        obCSI.setTotalAmount(dTotalAmount);     //总交易金额
		                        vctCapSummarize.addElement(obCSI);
			                }
			            }
					}
				break;
		    }    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
}
