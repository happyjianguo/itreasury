package com.iss.itreasury.fcinterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.fcinterface.bankportal.bankcode.BranchNOIdentify;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.fcinterface.dataentity.ExtSystemLogInfo;
import com.iss.itreasury.fcinterface.util.EPConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * 财企接口数据操作类
 * @author xiangzhou
 * 2011-3-23
 */
public class FcinterfaceDao extends ITreasuryDAO{
	
	private static Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	/**
	 * 增加一条财务交易指令
	 * @param FinanceInfo 账号
	 * @return long 新增加的财务交易指令流水号
	 * @exception Exception
	 */
	public long saveAndCheck(FinanceInfo info,Connection extConn) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lMaxID = -1;
		try
		{
			if(extConn!=null){
				con=extConn;
			}else{
				con = Database.getConnection();
			}
			
			StringBuffer sb = new StringBuffer();
			
			//调用BizCapital方法，得到最大流水号
			lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
			sb.setLength(0);
			log4j.info("财务指令插入语句= \n");
			sb.append(" INSERT INTO OB_FinanceInStr( \n");
			sb.append("ID, nClientID, nCurrencyID,nTransType, ");
			sb.append("nPayerAcctID,nRemitType,nPayeeAcctID, ");
			sb.append(" mAmount,nChildClientid, \n");	
			sb.append("dtExecute, sNote, nFixedDepositTime, nNoticeDay, ");
			sb.append("NCONTRACTID,  NLOANNOTEID, dtPay,");
			sb.append("sDepositNo,NSUBACCOUNTID, dtDepositStart, mDepositRate, mDepositAmount, mDepositBalance, \n");
			sb.append("nStatus, nConfirmUserID, dtConfirm, ");
			sb.append("CPF_nOfficeID, CPF_nDefaultTransType, \n");
			sb.append("DTCLEARINTEREST, nInterestPayeeAcctID, nInterestRemitType, ");
			sb.append("mInterest, mCompoundInterest, mOverdueInterest, mSuretyFee, mCommission, ");
			sb.append("mRealInterest, mRealCompoundInterest, mRealOverdueInterest, mRealSuretyFee, mRealCommission, \n");
			sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX,budgetItemID,ISFIXCONTINUE,FIXEDREMARK,MAMOUNTFORTRANS,sBatchNo,sApplyCode,lSource,isSameBank,isDiffLocal,dtmodify,isautocontinue,autocontinuetype,autocontinueaccountid, \n");
			
			//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
			sb.append("remitArea, remitSpeed, ");
			
			//xiangzhou ADD 2011-3-18
			sb.append("nIsCanAccept,DTCHECK,NCHECKUSERID,DTNOTICEDATE \n");
			sb.append(",FIXEDNEXTSTARTDATE,FIXEDNEXTENDDATE,FIXEDNEXTPERIOD,FIXEDINTERESTDEAL,FIXEDINTERESTTOACCOUNTID,SDEPOSITBILLNO)");
			
			sb.append("VALUES \n");
			sb.append("(?,?,?,?,?,?,?,?,?," );
			if (info.getTransType()== OBConstant.SettInstrType.APPLYCAPITAL)
			{
				sb.append("sysdate,");
			}else
			{
				sb.append("?," );
			}					
			sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,sysdate,?,? ");
			sb.append(",?,?,?,?,?,? )");
			ps = con.prepareStatement(sb.toString());
			int nIndex = 1;
			//////// 第1行 8个字段
			log4j.info("lMaxID=" + lMaxID);
			ps.setLong(nIndex++, lMaxID);
			ps.setLong(nIndex++, info.getClientID()); // 网上银行客户ID
			ps.setLong(nIndex++, info.getCurrencyID()); // 币种
			ps.setLong(nIndex++, info.getTransType()); // 网上银行交易类型
			ps.setLong(nIndex++, info.getPayerAcctID()); // 付款方账户ID
			ps.setLong(nIndex++, info.getRemitType()); // 汇款方式
			ps.setLong(nIndex++, info.getPayeeAcctID()); // 收款方账户ID
			ps.setDouble(nIndex++, info.getAmount()); // 交易金额
			ps.setLong(nIndex++, info.getChildClientID()); // 下属单位
			///////// 第2行  12字段
			if (info.getTransType()!= OBConstant.SettInstrType.APPLYCAPITAL)
			{
				ps.setTimestamp(nIndex++, info.getExecuteDate()); // 执行日
			}
			ps.setString(nIndex++, info.getNote()); // 汇款用途/摘要
			ps.setLong(nIndex++, info.getFixedDepositTime()); // 定期存款期限（月）
			ps.setLong(nIndex++, info.getNoticeDay()); // 通知存款品种（天）
			ps.setLong(nIndex++, info.getContractID()); // 贷款合同ID
			ps.setLong(nIndex++, info.getLoanNoteID()); //方款通知单号
			ps.setTimestamp(nIndex++, info.getPayDate()); // 贷款放款日期
			ps.setString(nIndex++, info.getDepositNo()); //定期（通知）存款单据号
			ps.setLong(nIndex++, info.getSubAccountID()); //子账户ID
			ps.setTimestamp(nIndex++, info.getDepositStart()); //定期（通知）存款起始日
			ps.setDouble(nIndex++, info.getDepositRate()); //定期存单利率
			ps.setDouble(nIndex++, info.getDepositAmount()); //存单金额（开立金额）
			ps.setDouble(nIndex++, info.getDepositBalance()); //存单余额
			/////// 第3行  5字段(日期以有，4)
			ps.setLong(nIndex++, OBConstant.SettInstrStatus.CHECK); // 状态为已复核
			ps.setLong(nIndex++, info.getConfirmUserID()); // 录入人
			ps.setLong(nIndex++, info.getOfficeID()); // CPF-默认办事处	
			ps.setLong(nIndex++, OBConstant.getDefaultTransactionType(info.getTransType())); // CPF-处理业务类型
			/////// 第4行  12字段
			ps.setTimestamp(nIndex++, info.getClearInterest()); //结息日期
			ps.setLong(nIndex++, info.getInterestPayeeAcctID()); //利息收款方账户ID
			ps.setLong(nIndex++, info.getInterestRemitType()); //利息汇款方式
			ps.setDouble(nIndex++, info.getInterest()); //应付贷款利息
			ps.setDouble(nIndex++, info.getCompoundInterest()); //应付复利
			ps.setDouble(nIndex++, info.getOverdueInterest()); // 应付逾期罚息
			ps.setDouble(nIndex++, info.getSuretyFee()); // 应付担保费
			ps.setDouble(nIndex++, info.getCommission()); // 应付手续费
			ps.setDouble(nIndex++, info.getRealInterest()); // 实付贷款利息
			ps.setDouble(nIndex++, info.getRealCompoundInterest()); // 实付复利
			ps.setDouble(nIndex++, info.getRealOverdueInterest()); // 实付逾期罚息
			ps.setDouble(nIndex++, info.getRealSuretyFee()); // 实付担保费
			ps.setDouble(nIndex++, info.getRealCommission()); // 实付手续费
			/////// 第5行  12字段
			ps.setTimestamp(nIndex++, info.getInterestStart()); //利息起息日
			ps.setTimestamp(nIndex++, info.getCompoundStart()); //复利起息日
			ps.setDouble(nIndex++, info.getCompoundRate()); //复利利率
			ps.setTimestamp(nIndex++, info.getOverDueStart()); // 罚息起息日
			ps.setDouble(nIndex++, info.getOverDueRate()); //罚息利率
			ps.setTimestamp(nIndex++, info.getSuretyStart()); // 担保费起息日
			ps.setDouble(nIndex++, info.getSuretyRate()); //担保费率
			ps.setTimestamp(nIndex++, info.getCommissionStart()); // 手续费起息日
			ps.setDouble(nIndex++, info.getCommissionRate()); //手续费率
			ps.setDouble(nIndex++, info.getInterestRate()); //贷款利率
			ps.setDouble(nIndex++, info.getCompoundAmount()); //复利本金
			ps.setDouble(nIndex++, info.getOverDueAmount()); //逾期罚息本金
			/////// 第5行  6字段
			ps.setDouble(nIndex++, info.getInterestReceiveable()); //计提利息
			ps.setDouble(nIndex++, info.getInterestIncome()); //本次利息
			ps.setDouble(nIndex++, info.getRealInterestReceiveable()); //实付计提利息
			ps.setDouble(nIndex++, info.getRealInterestIncome()); //实付本次利息
			ps.setDouble(nIndex++, info.getInterestTax()); //利息税费
			ps.setDouble(nIndex++, info.getRealInterestTax()); //实付利息税费
			
			//modify by lxr for budget 增加 budgetitemID 字段
			ps.setLong(nIndex++, info.getBudgetItemID());  //预算项目id
			//modify by juncai增加 ISFIXCONTINUE 字段
			ps.setLong(nIndex++, info.getIsFixContinue()); //到期是否续存
			//modify by juncai增加 FIXEDREMARK 字段
			ps.setString(nIndex++, info.getFixEdremark()); //备注（定期是否续存）
			//modify by juncai增加 MAMOUNTFORTRANS 字段
			ps.setDouble(nIndex++, info.getMamOuntForTrans()); //定期子帐户留存金额
			ps.setString(nIndex++, info.getSBatchNo());	//批次号
            ps.setString(nIndex++, info.getApplyCode());//业务申请编号
            ps.setLong(nIndex++, info.getSource()); //数据来源
            ps.setLong(nIndex++, info.getIsSameBank()); //是否同行
            ps.setLong(nIndex++, info.getIsDiffLocal()); //是否同城
            ps.setLong(nIndex++, info.getIsAutoContinue());//是否自动续存
            ps.setLong(nIndex++, info.getAutocontinuetype());//自动转续存类型（本金or本息）
            ps.setLong(nIndex++, info.getAutocontinueaccountid());//利息转至活期账户号
            
            ps.setLong(nIndex++, info.getRemitArea());	//汇款区域
            ps.setLong(nIndex++, info.getRemitSpeed());	//汇款速度
            
            ps.setLong(nIndex++, 1);//接收状态
            ps.setLong(nIndex++, info.getConfirmUserID()); //特殊复核用户
            ps.setTimestamp(nIndex++, info.getTsNoticeDate()); //通知日期
            //第15行  6字段 定期续存部分
			ps.setTimestamp(nIndex++, info.getSDepositBillStartDate());
			ps.setTimestamp(nIndex++,info.getSDepositBillEndDate());
			ps.setLong(nIndex++, info.getSDepositBillPeriod());
			ps.setLong(nIndex++,info.getSDepositInterestDeal());
			ps.setLong(nIndex++,info.getSDepositInterestToAccountID());
			ps.setString(nIndex++,info.getSDepositBillNo());
            
			ps.executeUpdate();
			ps.close();
			ps = null;
			if(extConn==null){
				con.close();
				con = null;
			}
			
		}
		catch (IException e)
		{
			log4j.error(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null&&extConn==null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lMaxID;
	}
	
	/**
	 * 根据系统ID获得系统指令是否自动接收状态
	 * @param extSystemID
	 * @return
	 * @throws Exception
	 */
	public boolean isAutoAcceptInstr(long extSystemID) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		boolean bool = false;
		try 
		{
			conn = Database.getConnection();
			strSQL.append(" select ");
			strSQL.append(" nvl((select t.receivemode from EP_EXTSYSRECEIVEMODE t where t.extsystemid = e.id and t.status = "+Constant.RecordStatus.VALID+"),"+SETTConstant.ExtSysReceiveMode.UNAUTO+") receivemode");
			strSQL.append(" from ");
			strSQL.append(" ep_ExtSystem e");
			strSQL.append(" where e.lstatus = "+Constant.RecordStatus.VALID);
			strSQL.append(" and e.id = "+extSystemID);
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getLong("receivemode") == SETTConstant.ExtSysReceiveMode.AUTO){
					bool = true;
				}
			}
			System.out.println("是否自动接收状态-->"+bool);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return bool;
	}
	
	/**
	 * 根据外部系统code查询外部系统对应的ID
	 * @param extSystemCode
	 * @return
	 * @throws Exception
	 */
	public long getExtSystemIDByCode(String extSystemCode) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long ExtSystemID = -1;
		try 
		{
			conn = Database.getConnection();
			strSQL.append(" select");
			strSQL.append(" t.id ExtSystemID from ep_ExtSystem t");
			strSQL.append(" where t.lstatus = "+Constant.RecordStatus.VALID);
			strSQL.append(" and t.scode = '"+extSystemCode+"'");
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				ExtSystemID = rs.getLong("ExtSystemID");
			}
			System.out.println("外部系统-->"+extSystemCode+",对应的ID-->"+ExtSystemID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return ExtSystemID;
	}
	
	/**
	 * 保存日志
	 * @param dataEntity
	 * @return
	 * @throws Exception
	 */
	public void addLog(ExtSystemLogInfo log) throws Exception 
	{
    	Connection con = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sb.append("insert INTO EP_ExtSystemLog( \n");
			sb.append("id,source,applycode,nstatus,remark) \n");
			sb.append("VALUES \n");
			sb.append("((select nvl(max(id)+1,1) from EP_ExtSystemLog),?,?,?,?) \n" );
			ps = con.prepareStatement(sb.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, log.getSource());
			ps.setString(nIndex++, log.getApplycode());
			ps.setLong(nIndex++, log.getNstatus());
			ps.setString(nIndex++, log.getRemark().equals("Sett_E116")?"账户余额不足":log.getRemark());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(con!=null){
				con.close();
				con = null;
			}
		}
	}

	/**
	 * 获得匹配开户行
	 * @param bankName
	 * @return
	 * @throws Exception
	 */
	public long branchMatchForBankName(String bankName) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		
		long lbranch=-1;
		long lbanktype=-1;
		long lreturn=-1;
		long otherBank = -1;
		
		String standerBankName="";
		String strArrayName="";
		
		boolean sign = false;
		try
		{
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			standerBankName = BranchNOIdentify.getStanderBankNameByBankName(bankName);
			System.out.print(standerBankName);
			sbSQL.append(" select s.id,s.banktype,s.bankmatch,s.status from ep_branchmatchsetting s where s.status="+Constant.RecordStatus.VALID);
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lbranch=rs.getLong("bankmatch");
				lbanktype=rs.getLong("banktype");
				if(lbanktype == SETTConstant.BankType.QTBANK ) otherBank = lbranch;
				strArrayName = BranchNOIdentify.getStanderBankNameByBankName(SETTConstant.BankType.getName_EP(lbanktype));
				if(strArrayName!=null&&standerBankName!=null&&standerBankName.equals(strArrayName))	
				{
					sign = true;
					lreturn = lbranch;
					break;
				}
			}
			String out = bankName+",匹配到的开户行ID为：";
			if(sign){
				out += lreturn;
			}else{
				out += otherBank;
			}
			System.out.println(out);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获得匹配开户行异常："+e.getMessage());
		}
		finally
		{
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}
		
		return sign?lreturn:otherBank;
	}
	
	/**
	 * 查询外部系统指令状态
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ExtSystemLogInfo queryInstruction(ExtSystemLogInfo info) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		ExtSystemLogInfo qInfo = null;
		try 
		{
			qInfo = new ExtSystemLogInfo();
			
			//取指令状态
			conn = Database.getConnection();
			strSQL.append(" select log.id id,log.applycode applycode, log.source source, log.nstatus statusid,log.remark remark, log.createtime createtime \n");
			strSQL.append(" from ep_extsystemlog log,ep_extsystem ep \n");
			strSQL.append(" where log.source = ep.id \n");
			strSQL.append(" and log.applycode = ? and ep.scode = ? and ep.LSTATUS = ? \n");
			strSQL.append(" order by log.id desc \n");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			ps.setString(nIndex++, info.getApplycode());
			ps.setString(nIndex++, info.getExtSystemCode());
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if(rs.next()){
				qInfo.setApplycode(rs.getString("applycode"));
				qInfo.setNstatus(rs.getLong("statusid"));
				qInfo.setRemark(rs.getString("remark"));
				qInfo.setCreatetime(rs.getTimestamp("createtime"));
			}
			
			//取银企状态
			strSQL = new StringBuffer();
			strSQL.append(" select fin.sapplycode applycode, bs.n_statusid statusid, bs.dt_modifytime createtime \n");
			strSQL.append(" from OB_FinanceInstr fin,BS_BANKINSTRUCTIONINFO bs,ep_extsystem ep \n");
			strSQL.append(" where bs.s_transactionno ='1'||to_char(fin.CPF_STRANSNO) \n");
			strSQL.append(" and fin.lsource = ep.id \n");
			strSQL.append(" and fin.sapplycode = ? and ep.scode = ? and ep.LSTATUS = ? \n");
			strSQL.append(" order by bs.n_id desc \n");
			ps = conn.prepareStatement(strSQL.toString());
			nIndex = 1;
			ps.setString(nIndex++, info.getApplycode());
			ps.setString(nIndex++, info.getExtSystemCode());
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if(rs.next()){
				qInfo.setBankStatus(rs.getLong("statusid"));
				qInfo.setCreatetime(rs.getTimestamp("createtime"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询外部系统指令状态异常："+e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return qInfo.getApplycode().equals("")?null:qInfo;
	}
	
	/**
	 * 根据客户CODE获得组织机构ID
	 * @param extSystemCode
	 * @return
	 * @throws Exception
	 */
	public long getOBClientIDByCode(String code) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long nclientid = -1;
		try 
		{
			conn = Database.getConnection();
			strSQL.append(" select");
			strSQL.append(" id from client_clientinfo");
			strSQL.append(" where code = '01-"+code+"'");
			strSQL.append(" and statusid = "+Constant.RecordStatus.VALID);
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				nclientid = rs.getLong("id");
			}
			System.out.println("根据客户CODE获得组织机构ID---->"+nclientid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return nclientid;
	}
	
	/**
	 * 获取机制、机核用户（分办事处）
	 * 不分办事处了 modify by bingliu 2011-07-30 
	 * @param username
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	public long getMachineUser(long account,long usertype) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long userid = -1;
		try 
		{
			String username = "";
			if(usertype == 1){
//				username = "机制";
				userid = Constant.MachineUser.InputUser;
			}else if(usertype == 2){
//				username = "机核";
				userid = Constant.MachineUser.CheckUser;
			}
//			int nIndex = -1;
//			conn = Database.getConnection();
//			strSQL.append(" select");
//			strSQL.append(" u.id from userinfo u,SETT_account t");
//			strSQL.append(" where u.nofficeid = t.nofficeid");
//			strSQL.append(" and t.id = ?");
//			strSQL.append(" and u.sname = ?");
//			strSQL.append(" and u.nstatusid = ?");
//			ps = conn.prepareStatement(strSQL.toString());
//			nIndex = 1;
//			ps.setLong(nIndex++, account);
//			ps.setString(nIndex++, username);
//			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				userid = rs.getLong("id");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return userid;
	}
	
	/**
	 * 外部申请指令是否重复
	 * @param source
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean checkApplyCode(FinanceInfo financeInfo) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		boolean isHave = false;
		try 
		{
			int nIndex = -1;
			conn = Database.getConnection();
			strSQL.append(" select");
			strSQL.append(" * from ob_financeinstr o");
			strSQL.append(" where o.sapplycode = ?");
			strSQL.append(" and o.lsource = ?");
			strSQL.append(" and o.nstatus > 0");
			ps = conn.prepareStatement(strSQL.toString());
			nIndex = 1;
			ps.setString(nIndex++, financeInfo.getApplyCode());
			ps.setLong(nIndex++, financeInfo.getSource());
			rs = ps.executeQuery();
			if(rs.next()){
				isHave = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return isHave;
	}
	
	/**
	 * 外部申请指令是否有接收人
	 * @param source
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public long checkReceiveUser(FinanceInfo financeInfo) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		long ReceiveUser = -1;
		try 
		{
			int nIndex = -1;
			conn = Database.getConnection();
			strSQL.append(" select");
			strSQL.append(" ReceiveUser from EP_EXTSYSRECEIVEMODE ");
			strSQL.append(" where 1=1");
			strSQL.append(" and EXTSYSTEMID = ?");
			strSQL.append(" and status > 0");
			ps = conn.prepareStatement(strSQL.toString());
			nIndex = 1;
			ps.setLong(nIndex++, financeInfo.getSource());
			rs = ps.executeQuery();
			if(rs.next()){
				ReceiveUser = rs.getLong("ReceiveUser");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally{
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}
		return ReceiveUser;
	}
	
	/**
	 * 将数字的状态 转换为字符两位
	 * @param lStatus
	 * @return
	 */
	public String getStatus(long lStatus){
		String status = "";
		status = String.valueOf(lStatus);
		if(status.length()==1){
			status = "0"+status;
		}
		return status;
	}
	
}
