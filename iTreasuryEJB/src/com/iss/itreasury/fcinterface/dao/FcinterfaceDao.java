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
 * ����ӿ����ݲ�����
 * @author xiangzhou
 * 2011-3-23
 */
public class FcinterfaceDao extends ITreasuryDAO{
	
	private static Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT);
	
	/**
	 * ����һ��������ָ��
	 * @param FinanceInfo �˺�
	 * @return long �����ӵĲ�����ָ����ˮ��
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
			
			//����BizCapital�������õ������ˮ��
			lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
			sb.setLength(0);
			log4j.info("����ָ��������= \n");
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
			
			//Boxu Add 2010-12-01 ����"����/���"��"�Ƿ�Ӽ�"
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
			//////// ��1�� 8���ֶ�
			log4j.info("lMaxID=" + lMaxID);
			ps.setLong(nIndex++, lMaxID);
			ps.setLong(nIndex++, info.getClientID()); // �������пͻ�ID
			ps.setLong(nIndex++, info.getCurrencyID()); // ����
			ps.setLong(nIndex++, info.getTransType()); // �������н�������
			ps.setLong(nIndex++, info.getPayerAcctID()); // ����˻�ID
			ps.setLong(nIndex++, info.getRemitType()); // ��ʽ
			ps.setLong(nIndex++, info.getPayeeAcctID()); // �տ�˻�ID
			ps.setDouble(nIndex++, info.getAmount()); // ���׽��
			ps.setLong(nIndex++, info.getChildClientID()); // ������λ
			///////// ��2��  12�ֶ�
			if (info.getTransType()!= OBConstant.SettInstrType.APPLYCAPITAL)
			{
				ps.setTimestamp(nIndex++, info.getExecuteDate()); // ִ����
			}
			ps.setString(nIndex++, info.getNote()); // �����;/ժҪ
			ps.setLong(nIndex++, info.getFixedDepositTime()); // ���ڴ�����ޣ��£�
			ps.setLong(nIndex++, info.getNoticeDay()); // ֪ͨ���Ʒ�֣��죩
			ps.setLong(nIndex++, info.getContractID()); // �����ͬID
			ps.setLong(nIndex++, info.getLoanNoteID()); //����֪ͨ����
			ps.setTimestamp(nIndex++, info.getPayDate()); // ����ſ�����
			ps.setString(nIndex++, info.getDepositNo()); //���ڣ�֪ͨ�����ݺ�
			ps.setLong(nIndex++, info.getSubAccountID()); //���˻�ID
			ps.setTimestamp(nIndex++, info.getDepositStart()); //���ڣ�֪ͨ�������ʼ��
			ps.setDouble(nIndex++, info.getDepositRate()); //���ڴ浥����
			ps.setDouble(nIndex++, info.getDepositAmount()); //�浥��������
			ps.setDouble(nIndex++, info.getDepositBalance()); //�浥���
			/////// ��3��  5�ֶ�(�������У�4)
			ps.setLong(nIndex++, OBConstant.SettInstrStatus.CHECK); // ״̬Ϊ�Ѹ���
			ps.setLong(nIndex++, info.getConfirmUserID()); // ¼����
			ps.setLong(nIndex++, info.getOfficeID()); // CPF-Ĭ�ϰ��´�	
			ps.setLong(nIndex++, OBConstant.getDefaultTransactionType(info.getTransType())); // CPF-����ҵ������
			/////// ��4��  12�ֶ�
			ps.setTimestamp(nIndex++, info.getClearInterest()); //��Ϣ����
			ps.setLong(nIndex++, info.getInterestPayeeAcctID()); //��Ϣ�տ�˻�ID
			ps.setLong(nIndex++, info.getInterestRemitType()); //��Ϣ��ʽ
			ps.setDouble(nIndex++, info.getInterest()); //Ӧ��������Ϣ
			ps.setDouble(nIndex++, info.getCompoundInterest()); //Ӧ������
			ps.setDouble(nIndex++, info.getOverdueInterest()); // Ӧ�����ڷ�Ϣ
			ps.setDouble(nIndex++, info.getSuretyFee()); // Ӧ��������
			ps.setDouble(nIndex++, info.getCommission()); // Ӧ��������
			ps.setDouble(nIndex++, info.getRealInterest()); // ʵ��������Ϣ
			ps.setDouble(nIndex++, info.getRealCompoundInterest()); // ʵ������
			ps.setDouble(nIndex++, info.getRealOverdueInterest()); // ʵ�����ڷ�Ϣ
			ps.setDouble(nIndex++, info.getRealSuretyFee()); // ʵ��������
			ps.setDouble(nIndex++, info.getRealCommission()); // ʵ��������
			/////// ��5��  12�ֶ�
			ps.setTimestamp(nIndex++, info.getInterestStart()); //��Ϣ��Ϣ��
			ps.setTimestamp(nIndex++, info.getCompoundStart()); //������Ϣ��
			ps.setDouble(nIndex++, info.getCompoundRate()); //��������
			ps.setTimestamp(nIndex++, info.getOverDueStart()); // ��Ϣ��Ϣ��
			ps.setDouble(nIndex++, info.getOverDueRate()); //��Ϣ����
			ps.setTimestamp(nIndex++, info.getSuretyStart()); // ��������Ϣ��
			ps.setDouble(nIndex++, info.getSuretyRate()); //��������
			ps.setTimestamp(nIndex++, info.getCommissionStart()); // ��������Ϣ��
			ps.setDouble(nIndex++, info.getCommissionRate()); //��������
			ps.setDouble(nIndex++, info.getInterestRate()); //��������
			ps.setDouble(nIndex++, info.getCompoundAmount()); //��������
			ps.setDouble(nIndex++, info.getOverDueAmount()); //���ڷ�Ϣ����
			/////// ��5��  6�ֶ�
			ps.setDouble(nIndex++, info.getInterestReceiveable()); //������Ϣ
			ps.setDouble(nIndex++, info.getInterestIncome()); //������Ϣ
			ps.setDouble(nIndex++, info.getRealInterestReceiveable()); //ʵ��������Ϣ
			ps.setDouble(nIndex++, info.getRealInterestIncome()); //ʵ��������Ϣ
			ps.setDouble(nIndex++, info.getInterestTax()); //��Ϣ˰��
			ps.setDouble(nIndex++, info.getRealInterestTax()); //ʵ����Ϣ˰��
			
			//modify by lxr for budget ���� budgetitemID �ֶ�
			ps.setLong(nIndex++, info.getBudgetItemID());  //Ԥ����Ŀid
			//modify by juncai���� ISFIXCONTINUE �ֶ�
			ps.setLong(nIndex++, info.getIsFixContinue()); //�����Ƿ�����
			//modify by juncai���� FIXEDREMARK �ֶ�
			ps.setString(nIndex++, info.getFixEdremark()); //��ע�������Ƿ����棩
			//modify by juncai���� MAMOUNTFORTRANS �ֶ�
			ps.setDouble(nIndex++, info.getMamOuntForTrans()); //�������ʻ�������
			ps.setString(nIndex++, info.getSBatchNo());	//���κ�
            ps.setString(nIndex++, info.getApplyCode());//ҵ��������
            ps.setLong(nIndex++, info.getSource()); //������Դ
            ps.setLong(nIndex++, info.getIsSameBank()); //�Ƿ�ͬ��
            ps.setLong(nIndex++, info.getIsDiffLocal()); //�Ƿ�ͬ��
            ps.setLong(nIndex++, info.getIsAutoContinue());//�Ƿ��Զ�����
            ps.setLong(nIndex++, info.getAutocontinuetype());//�Զ�ת�������ͣ�����or��Ϣ��
            ps.setLong(nIndex++, info.getAutocontinueaccountid());//��Ϣת�������˻���
            
            ps.setLong(nIndex++, info.getRemitArea());	//�������
            ps.setLong(nIndex++, info.getRemitSpeed());	//����ٶ�
            
            ps.setLong(nIndex++, 1);//����״̬
            ps.setLong(nIndex++, info.getConfirmUserID()); //���⸴���û�
            ps.setTimestamp(nIndex++, info.getTsNoticeDate()); //֪ͨ����
            //��15��  6�ֶ� �������沿��
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
	 * ����ϵͳID���ϵͳָ���Ƿ��Զ�����״̬
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
			System.out.println("�Ƿ��Զ�����״̬-->"+bool);
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
	 * �����ⲿϵͳcode��ѯ�ⲿϵͳ��Ӧ��ID
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
			System.out.println("�ⲿϵͳ-->"+extSystemCode+",��Ӧ��ID-->"+ExtSystemID);
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
	 * ������־
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
			ps.setString(nIndex++, log.getRemark().equals("Sett_E116")?"�˻�����":log.getRemark());
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
	 * ���ƥ�俪����
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
			String out = bankName+",ƥ�䵽�Ŀ�����IDΪ��";
			if(sign){
				out += lreturn;
			}else{
				out += otherBank;
			}
			System.out.println(out);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("���ƥ�俪�����쳣��"+e.getMessage());
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
	 * ��ѯ�ⲿϵͳָ��״̬
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
			
			//ȡָ��״̬
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
			
			//ȡ����״̬
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
			throw new Exception("��ѯ�ⲿϵͳָ��״̬�쳣��"+e.getMessage());
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
	 * ���ݿͻ�CODE�����֯����ID
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
			System.out.println("���ݿͻ�CODE�����֯����ID---->"+nclientid);
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
	 * ��ȡ���ơ������û����ְ��´���
	 * ���ְ��´��� modify by bingliu 2011-07-30 
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
//				username = "����";
				userid = Constant.MachineUser.InputUser;
			}else if(usertype == 2){
//				username = "����";
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
	 * �ⲿ����ָ���Ƿ��ظ�
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
	 * �ⲿ����ָ���Ƿ��н�����
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
	 * �����ֵ�״̬ ת��Ϊ�ַ���λ
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
