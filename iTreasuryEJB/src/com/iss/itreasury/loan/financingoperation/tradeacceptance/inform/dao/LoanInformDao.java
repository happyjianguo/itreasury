package com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dataentity.LoanInformInfo;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.protocol.dataentity.LoanProtocolInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Database;


public class LoanInformDao extends SettlementDAO {


		//根据ID查找用户信息
		public LoanInformInfo findByIDInform(LoanInformInfo informInfo) throws SQLException
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			StringBuffer strSQL = new StringBuffer();
			LoanProtocolInfo info = new LoanProtocolInfo();
			long lID = informInfo.getId();
			
			try {
				conn = Database.getConnection();

				
				/* 开始执行查询 */
				//委托单位临时存储 企业类型
				strSQL.append("select a.id,");
				strSQL.append("a.sapplycode,");//--审请书编号
				strSQL.append("a.discountclientid,");//--出票人
				strSQL.append("a.mloanamount,");//--汇票承兑金额2
				strSQL.append("a.commission,");
				strSQL.append("a.sloanreason,");//--申请原因
				strSQL.append("a.sloanpurpose,");//--申请用途
				strSQL.append("a.sother,");//--承兑来源及措施
				strSQL.append("a.sclientinfo,");//--该单位情况
				
				strSQL.append("d.creditcontroltype,");//授信控制 授信方式（授信向下，非授信向下）
				
				strSQL.append("a.mchargerate,");//--承兑手续费率2
				strSQL.append("a.nintervalnum,");//--期限2
				strSQL.append("a.nborrowclientid,");//--收款单位
				
				strSQL.append("d.gatheringportype gattype,");//--收款单位类型
				
				strSQL.append("a.scontractcode,");//--协议编号
				strSQL.append("a.sloanamountcode,");//--汇票号码
				strSQL.append("a.dtstartdate,");//--起始日期
				strSQL.append("a.dtenddate,");//--结束日期
				strSQL.append("a.ninputuserid,");//--录入人
				
				strSQL.append("a.nnextcheckuserid,");//--下一个审批人
				strSQL.append("a.nnextchecklevel,");//--下一个审批级别
				strSQL.append("a.islowlevel,");//--审批流程
				strSQL.append("a.nstatusid,");//--状态
				strSQL.append("b.sname,");//单位名称1
				strSQL.append("b.scode,");//单位编号1
				strSQL.append("(select lpad(count(*)+1,3,'0') from  LOAN_inform e where e.nContractID = a.id and nNoteTypeID = ?) ywcode, ");//--获得业务通知单编号
				
				if(informInfo.getNnoteTypeId()==1 || informInfo.getNnoteTypeId()==2)
				{
					strSQL.append("(select a.mloanamount*a.mchargerate*a.nintervalnum/36000 from dual) poundage,");//承兑手续费
					
					strSQL.append("(");
					strSQL.append("(select a.mloanamount*a.mchargerate*a.nintervalnum/36000 from dual) -");//--承兑手续费
					strSQL.append(" decode");
					strSQL.append("(");
					strSQL.append("(select sum(namount) from (select * from LOAN_inform where Status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f where f.ncontractid = a.id and f.nNoteTypeID = 1 group by nNoteTypeID),");
					strSQL.append("null,");
					strSQL.append("0,");																																						  //ncontractid 协议ID
					strSQL.append("(select sum(namount) from (select * from LOAN_inform where Status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f where f.ncontractid = a.id and f.nNoteTypeID = 1 group by nNoteTypeID)");
					strSQL.append(")");//获得已手续费（if等于null就等于零否则等于本身）
					strSQL.append(") nAmountsum, ");//计算未收手续费
				}
				
				if(informInfo.getNnoteTypeId()==1 || informInfo.getNnoteTypeId()==3)
				{
					strSQL.append("(select f.saccountno from sett_account f where e.nPayAccountID = f.id) fsaccountno,");//编号
					strSQL.append("(select f.sname from sett_account f where e.nPayAccountID = f.id) fsaccountnoname,");//名称
				}
				
				
				if(informInfo.getNnoteTypeId()==2)
				{
					strSQL.append("(select f.saccountno from sett_account f where e.nPayAccountID = f.id) fsaccountno,");//编号(付款方)
					strSQL.append("(select f.sname from sett_account f where e.nPayAccountID = f.id) fsaccountnoname,");//名称(付款方)
				}
				
				strSQL.append("(select f.saccountno from sett_account f where e.nRecAccountID = f.id) fsaccountno1,");//编号(收款款方)
				strSQL.append("(select f.sname from sett_account f where e.nRecAccountID = f.id) fsaccountnoname1,");//名称(收款款方)
				
				strSQL.append("e.ID eID,");
				strSQL.append("e.nContractID enContractID,");//--票据承兑协议ID
				strSQL.append("e.nNoteTypeID enNoteTypeID,");//--业务通知单类型(1收取手续费 2到期承兑 3垫付本息收回)
				strSQL.append("e.sCode esCode,");//--业务通知单编号(例：001)
				strSQL.append("e.dtExecuteDate edtExecuteDate,");//--执行日期
				strSQL.append("e.dtOutdate edtOutdate,");//--出票日期
				strSQL.append("e.dtTodate edtTodate,");//--到日期
				strSQL.append("e.nAmount enAmount,");//--交易金额(1 手续费 2 承兑金额 3 本息总额)
				strSQL.append("e.nPayAccountID enPayAccountID,");//--付款内部账户ID
				strSQL.append("e.sPayBankAccountNO esPayBankAccountNO,");//--付款银行账号
				strSQL.append("e.sPayBankAccountName esPayBankAccountName,");//--付款银行账户名称
				strSQL.append("e.sPayBankName esPayBankName,");//--付款账户开户行
				strSQL.append("e.nRecAccountID enRecAccountID,");//--收款内部账户ID
				strSQL.append("e.sRecBankAccountNO esRecBankAccountNO,");//--收款银行账号
				strSQL.append("e.sRecBankAccountName esRecBankAccountName,");//--收款银行账户名称
				strSQL.append("e.sRecBankName esRecBankName,");//--收款银行名称
				strSQL.append("e.sRecBankProvince esRecBankProvince,");//--收款开户行所在省
				strSQL.append("e.sRecBankCity esRecBankCity,");//--收款开户行所在市
				strSQL.append("e.ncapitalAmount encapitalAmount,");//--本金
				strSQL.append("e.noverdueDay enoverdueDay,");//--逾期天数
				strSQL.append("e.noverDueRate enoverDueRate,");//--逾期利率
				strSQL.append("e.ninterestAmount eninterestAmount,");//--本次收取利息	
				strSQL.append("e.nextcheckuserid enextcheckuserid,");//--下一个审核人
				strSQL.append("e.nextchecklevel enextchecklevel,");//--下一个审批级别
				strSQL.append("e.islowlevel eislowlevel,");//--审批流程
				strSQL.append("e.officeid eofficeid,");//--办事处
				strSQL.append("e.currencyid ecurrencyid,");//--币种
				strSQL.append("e.status estatus,");//--状态
				strSQL.append("e.inputuserid einputuserid,");//--录入人
				strSQL.append("e.inputdate einputdate");//--录入时间
				
//				add dwj 20081028 informInfo.getNnoteTypeId()==3表示：垫付本息收回
				if(informInfo.getNnoteTypeId()==3)
				{
					
					//计算未收本金
					strSQL.append(" ,(a.mloanamount-(select sum(ncapitalamount) ");// ---以收本金
					strSQL.append(" from (select * ");
					strSQL.append(" from LOAN_inform ");
					strSQL.append(" where Status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f ");
					strSQL.append(" where f.ncontractid = a.id ");
					strSQL.append(" and f.nNoteTypeID = 3 ");
					strSQL.append(" group by nNoteTypeID)) nncapitalamount ");//--未收本金
					
					//说明逾期利息被确定就不能被修改 
					//逾期利率
					strSQL.append(" ,(select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) maxRate ");// --逾期利息
					
					//逾期天数
					strSQL.append(" ,(select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3)  maxday "); //--逾期天数
					
					//add dwj 20081115
					//获得 逾期利息的id   
					strSQL.append(" ,(select max(id) from (select * from LOAN_inform where status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3 and (x.nOverDueRate*x.noverdueday)>0 and x.noverdueday>0)  noverdueid "); //--逾期利息id
					//end dwj 20081115
					
					//逾期利息
					strSQL.append(" ,( ");
					strSQL.append(" ((select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" (select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" a.mloanamount )/36000 ");
					strSQL.append(" ) accrual ");//--逾期利息
					
					
					
					//计算未收回利息	 
					strSQL.append(" ,( ");
					strSQL.append(" ((select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" (select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" a.mloanamount )/36000 ");
					strSQL.append(" -(select sum(nInterestAmount) ");// ---已收逾期利息
					strSQL.append(" from (select * ");
					strSQL.append(" from LOAN_inform ");
					strSQL.append(" where Status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f ");
					strSQL.append(" where f.ncontractid = a.id ");
					strSQL.append(" and f.nNoteTypeID = 3 ");
					strSQL.append(" group by nNoteTypeID) ");
					strSQL.append(" ) naccrual ");//--naccrual未收逾期利息 nninterestamountsum
					
				}
				
				
				strSQL.append(" from LOAN_CONTRACTFORM a,Client b, ");
				strSQL.append(" loan_loanform d, ");
				
				strSQL.append(" loan_inform e ");//通知表
				strSQL.append(" where a.ntypeid = 80");
				strSQL.append(" and b.id = a.discountclientid ");
				
				strSQL.append(" and a.nloanid = d.id");
				
				strSQL.append(" and e.ncontractid = a.id");//--e.ncontractid协议ID
				strSQL.append(" and e.id = ?");//--通知单ID 
				
				ps = conn.prepareStatement(strSQL.toString());
				System.out.println("***dwj*** id : " + lID);
				ps.setLong(1, informInfo.getNnoteTypeId());//通知类型
				ps.setLong(2, lID);
				System.out.println("***dwj*** sql: " + strSQL.toString());
				rs = ps.executeQuery();
				if (rs!=null && rs.next()) {
					
					//info.setID(rs.getLong("id"));
					info.setId(rs.getLong("id"));//ID
					info.setApplicationCode(rs.getString("sapplycode"));//--审请书编号
					info.setPaymentPorId(rs.getLong("discountclientid"));//--出票人
					info.setMOrder(rs.getDouble("mloanamount"));//汇款金额
					info.setCommission(rs.getDouble("commission"));
					
					info.setApplicationReason(rs.getString("sloanreason"));//申请原因
					info.setApplicationPurpose(rs.getString("sloanpurpose"));//申请用途
					info.setApplicationRimea(rs.getString("sother"));//承兑来源及措施
					info.setCorporationcircs(rs.getString("sclientinfo"));//该单位情况简介
					
					info.setCreditcontrolType(rs.getLong("creditcontroltype"));//授信控制 授信方式
					info.setSname(rs.getString("sname"));//单位名称
					info.setScode(rs.getString("scode"));//单位编号
					//info.setCreditlevel(rs.getString("creditlevel"));//等级
					info.setScale(rs.getDouble("mchargerate"));//比例  承兑手续费率
					info.setTerm(rs.getLong("nintervalnum"));//期限
					info.setGatheringPorId(rs.getLong("nborrowclientid"));//--收款单位         通过它们的到单位信息
					info.setGatheringPorType(rs.getLong("gattype"));//--收款单位类型  通过它们的到单位信息
					
					info.setNextCheckuserId(rs.getLong("nnextcheckuserid"));//--下一个审批人
					info.setNextChecklevel(rs.getLong("nnextchecklevel"));//下一个审批级别
					info.setIsLowlevel(rs.getLong("islowlevel"));//--审批流程
					info.setStatus(rs.getLong("nstatusid"));//--状态
					
					//合同特有的字段
					info.setScontractcode(rs.getString("scontractcode"));//合同编号
					info.setSloanamountcode(rs.getString("sloanamountcode"));//汇票号码
					info.setDtstartdate(rs.getTimestamp("dtstartdate"));//起始日期
					info.setDtenddate(rs.getTimestamp("dtenddate"));//结束日期
					
					System.out.println("收款单位类型: " + info.getGatheringPorType());
					info = selName(info);//获得 单位信息（内部或外部）
					informInfo.setScode(rs.getString("ywcode"));//获得业务通知单编号
					if(informInfo.getNnoteTypeId()!=3)
					{
						informInfo.setPoundage(rs.getDouble("poundage"));//承兑手续费
						double npoundage = rs.getDouble("nAmountsum");
						informInfo.setNpoundage(npoundage);//未收手续费
					}
					informInfo.setCurrencyid(rs.getLong("ecurrencyid"));// NUMBER,--币种
					informInfo.setDtExecuteDate(rs.getTimestamp("edtExecuteDate"));//dtExecuteDate Date,--执行日期
					informInfo.setDtOutdate(rs.getTimestamp("edtOutdate"));//dtOutdate Date,--出票日期
					informInfo.setDtTodate(rs.getTimestamp("edtTodate"));//dtTodate Date,--到日期
					informInfo.setId(rs.getLong("eID"));
					informInfo.setInputdate(rs.getTimestamp("eInputdate"));// DATE--录入时间
					informInfo.setInputuserid(rs.getLong("eInputuserid"));// NUMBER,--录入人
					informInfo.setIslowlevel(rs.getLong("eIslowlevel"));//// NUMBER,--审批流程
					informInfo.setNamount(rs.getDouble("eNamount"));//nAmount Number(21,6),--交易金额(1 手续费 2 承兑金额 3 本息总额)
					informInfo.setNcapitalAmount(rs.getDouble("eNcapitalAmount"));// Number(21,6),--本金
					informInfo.setNcontractId(rs.getLong("eNcontractId"));//nContractID Number,--票据承兑协议ID
					informInfo.setNextchecklevel(rs.getLong("eNextchecklevel"));// NUMBER,--下一个审批级别
					informInfo.setNextcheckuserid(rs.getLong("eNextcheckuserid"));// NUMBER,--下一个审核人
					informInfo.setNinterestAmount(rs.getDouble("eNinterestAmount"));// Number(21,6),--本次收取利息
					informInfo.setNnoteTypeId(rs.getLong("eNnoteTypeId"));//nNoteTypeID Number,--业务通知单类型(1收取手续费 2到期承兑 3垫付本息收回)
					
					if(informInfo.getNnoteTypeId()==3)
					{	
						informInfo.setNoverdueDay(rs.getLong("maxday"));// Number,--逾期天数
						informInfo.setNoverDueRate(rs.getDouble("maxRate"));// Number(15,12),--逾期利率
						informInfo.setNncapitalamount(rs.getDouble("nncapitalamount"));//未收本金
						informInfo.setNninterestamountsum(rs.getDouble("naccrual"));//未收回利息
						informInfo.setNoverdueratesum(rs.getDouble("accrual"));//逾期利息
						
						informInfo.setNoverdueid(rs.getLong("noverdueid"));//逾期利息id
						
					}
					informInfo.setNpayAccountID(rs.getLong("enpayAccountID"));//nPayAccountID number,--付款内部账户ID
					informInfo.setNrecAccountID(rs.getLong("enrecAccountID"));//nRecAccountID number,--收款内部账户ID
					informInfo.setOfficeid(rs.getLong("eOfficeid"));//NUMBER,--办事处
					informInfo.setScode(rs.getString("escode"));//sCode Varchar2(10),--业务通知单编号(例：001)
					informInfo.setSpayBankAccountName(rs.getString("espayBankAccountName"));//sPayBankAccountName Varchar2(100),--付款银行账户名称
					informInfo.setSpayBankAccountNO(rs.getString("espayBankAccountNO"));//sPayBankAccountNO Varchar2(30),--付款银行账号
					informInfo.setSpayBankName(rs.getString("espayBankName"));//sPayBankName Varchar2(100),--付款账户开户行
					informInfo.setSrecBankAccountName(rs.getString("esrecBankAccountName"));//sRecBankAccountName Varchar2(100),--收款银行账户名称
					informInfo.setSrecBankAccountNO(rs.getString("esrecBankAccountNO"));//sRecBankAccountNO Varchar2(30),--收款银行账号
					informInfo.setSrecBankCity(rs.getString("esrecBankCity"));//sRecBankCity Varchar2(50),--收款开户行所在市
					informInfo.setSrecBankName(rs.getString("esrecBankName"));//sRecBankName Varchar2(100),--收款银行名称
					informInfo.setSrecBankProvince(rs.getString("esrecBankProvince"));//sRecBankProvince Varchar2(50),--收款开户行所在省
					informInfo.setStatus(rs.getLong("estatus"));// NUMBER,--状态
					informInfo.setFsaccountno(rs.getString("fsaccountno"));//内部帐号编号(付款方)
					informInfo.setFsaccountnoname(rs.getString("fsaccountnoname"));//内部帐号名称(付款方)
					informInfo.setFsaccountno1(rs.getString("fsaccountno1"));//内部帐号编号(收款款方)
					informInfo.setFsaccountnoname1(rs.getString("fsaccountnoname1"));//内部帐号编号(收款款方)
				}
				
				informInfo.setLoanProtocolInfo(info);
				informInfo.setInputuserid(rs.getLong("ninputuserid"));//录入人
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			return informInfo;
		}

//		查找 收款单位信息
		private LoanProtocolInfo selName(LoanProtocolInfo info)throws SQLException
		{
			long type = -1;
			long gatheringPorId = info.getGatheringPorId();
			type = info.getGatheringPorType();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			StringBuffer strSQL = new StringBuffer();
			try {
				conn = Database.getConnection();

				
				/* 开始执行查询 */
				if(type==1)//1是内部单位select scode from loan_loanform a, Client b where a.ntypeid = 80 and b.id=a.nborrowclientid
				{
					strSQL.append(" select sname,scode ");
					strSQL.append(" from loan_loanform a, Client b where a.ntypeid = 80 and b.id=?");
				}
				
				if(type==2)//2是外部单位 select slegalpersoncodecert from loan_loanform a, loan_exteriorunits c where a.ntypeid = 80 and c.id=a.nborrowclientid
				{
					strSQL.append(" select sname,slegalpersoncodecert ");
					strSQL.append(" from loan_loanform a, loan_exteriorunits c where a.ntypeid = 80 and c.id=?");
				}
				
				System.out.println("***dwj*** sql: " + strSQL.toString());
				
				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, gatheringPorId);
				rs = ps.executeQuery();
				if (rs.next()) {
					if(type==1)
					{
						info.setSname1(rs.getString("sname"));//单位名称（内部）
						info.setScode1(rs.getString("scode"));//单位编号（内部）
					}
					if(type==2)
					{
						info.setSname2(rs.getString("sname"));//单位名称（外部）
						info.setSlegalpersoncodecert2(rs.getString("slegalpersoncodecert"));//单位法人（外部）
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			return info;
		}
}
