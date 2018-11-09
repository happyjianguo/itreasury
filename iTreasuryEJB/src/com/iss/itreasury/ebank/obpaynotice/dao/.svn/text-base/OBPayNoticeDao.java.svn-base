	package com.iss.itreasury.ebank.obpaynotice.dao;
	
	import com.iss.itreasury.ebank.obpaynotice.dataentity.*;
	import java.rmi.RemoteException;
	import java.sql.*;
	import com.iss.itreasury.util.*;
	import javax.servlet.jsp.JspWriter;
	import com.iss.itreasury.ebank.util.*;
	import com.iss.itreasury.loan.util.*;
	/**
	 * @author yanhuang
	 *
	 * To change this generated comment edit the template variable "typecomment":
	 * Window>Preferences>Java>Templates.
	 * To enable and disable the creation of type comments go to
	 * Window>Preferences>Java>Code Generation.
	 */
	public class OBPayNoticeDao {
		private static Log4j log4j = null;
	
		public OBPayNoticeDao() {
			log4j = new Log4j(Constant.ModuleType.LOAN, this);
		}
	
		public PayNoticeInfo findPayNoticeByID(long lpayID) throws Exception {
	
			PayNoticeInfo info = new PayNoticeInfo();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			//LoanPayNoticeDao dao = new LoanPayNoticeDao() ;
	
			try {
				con = Database.getConnection();
				StringBuffer sb = new StringBuffer();
				sb.append(" select a.*,b.sname as sInputName,c.sname as accname,c.sCode as acccodec,d.sname as sGrantName ,d.saccountno as sGrantCurrentAccount");
				sb.append(" from OB_DUEBILL a,ob_user b,sett_Branch c,sett_account d");
	
				sb.append("  where a.ninputuserid = b.id(+) ");
				sb.append(" and a.nAccountBankID = c.id(+) ");
				sb.append("  and a.Ngrantcurrentaccountid = d.id(+) ");
				sb.append("  and a.id = ? ");
	
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lpayID);
				System.out.println(sb.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next()) {
					info.setID(rs.getLong("ID"));
					info.setCode(rs.getString("SINSTRUCTIONNO"));
					info.setContractID(rs.getLong("NCONTRACTID"));
					info.setOutDate(rs.getTimestamp("DTOUTDATE"));
					info.setAmount(rs.getDouble("MAMOUNT"));
					info.setConsignAccount(rs.getString("SCONSIGNACCOUNT"));
					info.setBankInterestID(rs.getLong("NBANKINTERESTID"));
					info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
					info.setSuretyFeeRate(rs.getDouble("MSURETYFEERATE"));
					info.setStart(rs.getTimestamp("DTSTART"));
					info.setEnd(rs.getTimestamp("DTEND"));
					info.setReceiveClientName(rs.getString("SRECEIVECLIENTNAME"));
					info.setReceiveAccount(rs.getString("SRECEIVEACCOUNT"));
					info.setRemitBank(rs.getString("SREMITBANK"));
					info.setCompanyLeader(rs.getString("SCOMPANYLEADER"));
					info.setHandlingPerson(rs.getString("SHANDLINGPERSON"));
					info.setDepartmentLeader(rs.getString("SDEPARTMENTLEADER"));
					info.setStatusID(rs.getLong("NSTATUSID"));
					info.setInputUserID(rs.getLong("NINPUTUSERID"));
					info.setInputUserName(rs.getString("sInputName"));
					info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
					info.setGrantCurrentAccountID(
						rs.getLong("NGRANTCURRENTACCOUNTID"));
					info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
					info.setGrantCurrentName(rs.getString("sGrantName"));
					
					info.setGrantTypeID(rs.getLong("NGRANTTYPEID"));
					info.setRemitinProvince(rs.getString("SREMITINPROVINCE"));
					info.setRemitinCity(rs.getString("SREMITINCITY"));
					info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));

//					info.setInterestRate(rs.getDouble("MINTERESTRATE"));//////执行利率！
					
					info.setWTInterestRate( rs.getDouble( "MINTERESTRATE" ) ) ;//基准利率
					
					info.setLoanAccount(rs.getString("SLOANACCOUNT"));
					info.setAccountBankID(rs.getLong("NACCOUNTBANKID"));
					info.setAccountBankCode(rs.getString("acccodec"));
					info.setAccountBankName(rs.getString("accname"));
					
					/*
					
					info.setContractCode( rs.getString( "sContractCode" ) ) ;
					info.setLoanClientName( rs.getString( "sBorrowClientName" ) ) ;
					info.setLoanAmount( rs.getDouble( "mLoanAmount" ) ) ;
					info.setIntervalNum( rs.getLong( "NINTERVALNUM" ) ) ;
					//已发放金额info.set
					info.setContractRate( rs.getDouble( "mContractRate" ) ) ;
					
					info.setLoanPurpose( rs.getString( "sLoanPurpose" ) ) ;
					info.setLoanZipCode( rs.getString( "sZipCode" ) ) ;
					info.setLoanPhone( rs.getString( "sPhone" ) ) ;
					info.setLoanAddress( rs.getString( "sAddress" ) ) ;
					info.setLoanTypeID( rs.getLong( "nTypeID" ) ) ;
					
					info.setGrantCurrentName( rs.getString( "sGrantName" ) ) ;
					info.setConsignClientName( rs.getString( "sConsignClientName" ) ) ;
					info.setInterest( rs.getDouble( "subInterest" ) ) ;
					info.setBalance( rs.getDouble( "subBalance" ) ) ;
					info.setAccountBankCode( rs.getString( "acccode" ) ) ;
					info.setAccountBankName( rs.getString( "accname" ) ) ;
					info.setWTInterestRate( rs.getDouble( "minterestrate" ) ) ;
					
					 */
				}
	
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);
	
				con.close();
				con = null;
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new Exception("remote exception : " + e.toString());
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (Exception e) {
					throw new Exception("remote exception : " + e.toString());
				}
			}
			return info;
		}
	
		public double getLatelyRate(long lLoanPayNoticeID, Timestamp tsDate)
			throws Exception {
			double dResult = 100;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
	
			try {
				con = Database.getConnection();
	
				if (tsDate == null || tsDate.equals("")) {
					tsDate = DataFormat.getDateTime(con);
				}
	
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(
					" SELECT b.mrate FROM LOAN_RATEADJUSTPAYDETAIL a ,loan_interestRate b  ");
				sbSQL.append(" WHERE a.NLOANPAYNOTICEID = ?  ");
				sbSQL.append(
					" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
				sbSQL.append(" and a.nBankInterestID = b.id(+) ");
				sbSQL.append(" ORDER BY  a.dtStartDate DESC ");
	
				System.out.println("sbSQl=" + sbSQL.toString());
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				ps.setTimestamp(2, tsDate);
				rs = ps.executeQuery();
	
				if (rs.next()) {
					dResult = rs.getDouble("mRate");
				} else if (lLoanPayNoticeID > 0) {
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					sbSQL.setLength(0);
					sbSQL.append(
						" SELECT decode( b.mRate,null,a.mInterestRate,0,a.mInterestRate ,b.mRate) mRate ");
					sbSQL.append(" FROM loan_payform a,loan_interestRate b ");
					sbSQL.append(" WHERE a.nBankInterestID = b.id(+) ");
					sbSQL.append(" AND a.id = ?");
	
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lLoanPayNoticeID);
					rs = ps.executeQuery();
					if (rs.next()) {
						dResult = rs.getDouble("mRate");
					}
				}
	
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
	
				sbSQL.setLength(0);
				sbSQL.append(" SELECT b.mAdjustRate ");
				sbSQL.append(" FROM loan_payform a,loan_contractform b ");
				sbSQL.append(" WHERE a.nContractID = b.ID ");
				sbSQL.append(" AND a.id = ?");
	
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanPayNoticeID);
				rs = ps.executeQuery();
				if (rs.next()) {
					dResult = dResult * (1 + rs.getDouble("mAdjustRate") / 100);
				}
	
				rs.close();
				rs = null;
				ps.close();
				ps = null;
	
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (Exception e) {
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return dResult;
	
		}
	
		public long savePayNotice(PayNoticeInfo lpninfo) throws Exception {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			String strSQL = null;
			long lMaxID = 0;
			long lResult = 0;
			try {
				con = Database.getConnection();
				//如果是新增
				if (lpninfo.getID() < 0) {
					//获得最大ID＋1
					strSQL =
						//" select Seq_Loan_PayFrm_DiscountCred.Nextval from dual ";
						"select nvl(max(ID)+1,1) ID from OB_DUEBILL ";
					ps = con.prepareStatement(strSQL);
					//log4j.info("sql="+strSQL);
					rs = ps.executeQuery();
					if (rs.next()) {
						lMaxID = rs.getLong(1);
						lResult = lMaxID;
						rs.close();
						rs = null;
						ps.close();
						ps = null;
					} else {
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						con.close();
						con = null;
						return -1;
					}
					//获得放款通知单编号
						
					lpninfo.setCode(OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN));
					
					//进行插入操作
					strSQL =
						"insert into OB_DUEBILL(id,SINSTRUCTIONNO,nContractID,dtOutDate,"
							+ "mamount,sConsignAccount,nBankInterestID,mCommissionRate,"
							+ "mSuretyFeeRate,dtStart,dtEnd,sReceiveClientName,"
							+ "sReceiveAccount,sRemitBank,sCompanyLeader,sHandlingPerson,"
							+ "sDepartmentLeader,nStatusID,nInputUserID,dtInputDate,"
							+ "nSourceTypeID,nGrantCurrentAccountID,"
							+ "nGrantTypeID,"
							+ "sRemitinProvince,sRemitincity,"
							+ "nDrawNoticeID,sLoanAccount,naccountbankid,minterestrate) values"
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					System.out.println("sql=" + strSQL);
					
					ps = con.prepareStatement(strSQL);
	
					ps.setLong(1, lMaxID);
					ps.setString(2, lpninfo.getCode());
					ps.setLong(3, lpninfo.getContractID());
					ps.setTimestamp(4, lpninfo.getOutDate());
					ps.setDouble(5, lpninfo.getAmount());
					ps.setString(6, lpninfo.getConsignAccount());
					ps.setLong(7, lpninfo.getBankInterestID());
					ps.setDouble(8, lpninfo.getCommissionRate());
					ps.setDouble(9, lpninfo.getSuretyFeeRate());
					ps.setTimestamp(10, lpninfo.getStart());
					ps.setTimestamp(11, lpninfo.getEnd());
					ps.setString(12, lpninfo.getReceiveClientName());
					ps.setString(13, lpninfo.getReceiveAccount());
					ps.setString(14, lpninfo.getRemitBank());
					ps.setString(15, lpninfo.getCompanyLeader());
					ps.setString(16, lpninfo.getHandlingPerson());
					ps.setString(17, lpninfo.getDepartmentLeader());
					ps.setLong(18, OBConstant.LoanInstrStatus.SAVE);
					//装态已提交
					ps.setLong(19, lpninfo.getInputUserID());
					ps.setTimestamp(20, lpninfo.getInputDate());
				//	ps.setLong(21, lpninfo.getNextCheckUserID());
					ps.setLong(21, LOANConstant.LoanPayNoticeModifySourceType.XD);
					//信贷
					ps.setLong(22, lpninfo.getGrantCurrentAccountID());
					ps.setLong(23, lpninfo.getGrantTypeID());
					//ps.setLong(25,lpninfo.getRemitoutBankID());
					//ps.setString(26,lpninfo.getRemitinAccountNo());
					ps.setString(24, lpninfo.getRemitinProvince());
					ps.setString(25, lpninfo.getRemitinCity());
					//ps.setLong(29,lpninfo.getRemitinBankID());
					//ps.setLong(30,lpninfo.getCashFlowID());
					ps.setLong(26, lpninfo.getDrawNoticeID());
					ps.setString(27, lpninfo.getLoanAccount());
					//ps.setString(29, lpninfo.getCheckPerson());
					ps.setLong(28, lpninfo.getAccountBankID());
					ps.setDouble(29, lpninfo.getWTInterestRate());
					
					System.out.println("prepared execute insert SQL");
					
					ps.executeUpdate();

					System.out.println("execute insert SQL sucess");
					ps.close();
					ps = null;
				}
				if ( con != null )
				  {
					  con.close() ;
					  con = null ;
				  }
	
				  //saveExternalAccount( lpninfo ) ;
			  }
			  catch ( Exception e )
			  {
				  log4j.error( "catch a error" ) ;
				  throw new RemoteException( e.getMessage() ) ;
			  }
			  finally
			  {
				  try
				  {
					  if ( rs != null )
					  {
						  rs.close() ;
						  rs = null ;
					  }
					  if ( ps != null )
					  {
						  ps.close() ;
						  ps = null ;
					  }
					  if ( con != null )
					  {
						  con.close() ;
						  con = null ;
					  }
				  }
				  catch ( Exception ex )
				  {
					  throw new RemoteException( ex.getMessage() ) ;
				  }
			  }
			  return lResult ;
			}
			
			
			
			
			public long updatePayNotice(PayNoticeInfo lpninfo) throws Exception {
					PreparedStatement ps = null;
					ResultSet rs = null;
					Connection con = null;
					String strSQL = null;
					long lMaxID = 0;
					long lResult = 0;
					try {
						con = Database.getConnection();
						strSQL =
							  "UPDATE  OB_DUEBILL  set "
							  + "dtOutDate = ? ,mamount = ? ,sConsignAccount = ? ,nBankInterestID = ? ,mCommissionRate = ? ,"
							  + "mSuretyFeeRate = ? ,dtStart = ? ,dtEnd = ? ,sReceiveClientName = ? ,sReceiveAccount = ? ,"
							  + "sRemitBank = ? ,sCompanyLeader = ? ,sHandlingPerson = ? ,sDepartmentLeader = ? ,nStatusID = ? ,"
							  + "nInputUserID = ? ,dtInputDate = ? ,nSourceTypeID = ? ,nGrantCurrentAccountID = ? ,"
							  + "nGrantTypeID = ? ,sRemitinProvince = ? ,sRemitincity = ? ,nDrawNoticeID = ? ,sLoanAccount = ? ,"
						  +
						  "  naccountbankid = ? ,minterestrate = ?  where id = ?" ;
					
						ps = con.prepareStatement( strSQL ) ;
						ps.setTimestamp( 1 , lpninfo.getOutDate() ) ;
						ps.setDouble( 2 , lpninfo.getAmount() ) ;
						ps.setString( 3 , lpninfo.getConsignAccount() ) ;
						ps.setLong( 4 , lpninfo.getBankInterestID() ) ;
						ps.setDouble( 5 , lpninfo.getCommissionRate() ) ;
						ps.setDouble( 6 , lpninfo.getSuretyFeeRate() ) ;
						ps.setTimestamp( 7 , lpninfo.getStart() ) ;
						ps.setTimestamp( 8 , lpninfo.getEnd() ) ;
						ps.setString( 9 , lpninfo.getReceiveClientName() ) ;
						ps.setString( 10 , lpninfo.getReceiveAccount() ) ;
						ps.setString( 11 , lpninfo.getRemitBank() ) ;
						ps.setString( 12 , lpninfo.getCompanyLeader() ) ;
						ps.setString( 13 , lpninfo.getHandlingPerson() ) ;
						ps.setString( 14 , lpninfo.getDepartmentLeader() ) ;
						ps.setLong( 15 , OBConstant.LoanInstrStatus.SAVE ) ; //装态已提交
						ps.setLong( 16 , lpninfo.getInputUserID() ) ;
						ps.setTimestamp( 17 , lpninfo.getInputDate() ) ;
						//ps.setLong( 18 , lpninfo.getNextCheckUserID() ) ;
						ps.setLong( 18 , LOANConstant.LoanPayNoticeModifySourceType.XD ) ;
						ps.setLong( 19 , lpninfo.getGrantCurrentAccountID() ) ;
						ps.setLong( 20 , lpninfo.getGrantTypeID() ) ;
						ps.setString( 21 , lpninfo.getRemitinProvince() ) ;
						ps.setString( 22 , lpninfo.getRemitinCity() ) ;
						ps.setLong( 23 , lpninfo.getDrawNoticeID() ) ;
						ps.setString( 24 , lpninfo.getLoanAccount() ) ;
						ps.setLong( 25 , lpninfo.getAccountBankID() ) ;
						ps.setDouble( 26 , lpninfo.getWTInterestRate() ) ;
						ps.setLong( 27 , lpninfo.getID() ) ;
						ps.executeUpdate() ;
						lResult = lpninfo.getID() ;
						ps.close() ;
						ps = null ;
	
					if ( con != null )
							  {
								  con.close() ;
								  con = null ;
							  }
							  //saveExternalAccount( lpninfo ) ;
						  }
						  catch ( Exception e )
						  {
							  log4j.error( "catch a error" ) ;
							  throw new RemoteException( e.getMessage() ) ;
						  }
						  finally
						  {
							  try
							  {
								  if ( rs != null )
								  {
									  rs.close() ;
									  rs = null ;
								  }
								  if ( ps != null )
								  {
									  ps.close() ;
									  ps = null ;
								  }
								  if ( con != null )
								  {
									  con.close() ;
									  con = null ;
								  }
							  }
							  catch ( Exception ex )
							  {
								  throw new RemoteException( ex.getMessage() ) ;
							  }
						  }
						  return lResult ;
			}
			
			
			
			public long updateStatus(long ipayID, long nstatusID)
				throws Exception {
					Connection con = null;
			PreparedStatement ps = null;
			long lResult = -1;
			int nIndex = 1;
			long lUpdateStatus = -1;
			lUpdateStatus = nstatusID;
		
			try {
				con = Database.getConnection();
				StringBuffer sb = new StringBuffer();
				sb.append("UPDATE OB_DUEBILL SET NSTATUSID = ? ");
				sb.append(" where ID = ?");
				//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
				//用途：获取开关机状态和开机时间
		
				log4j.info(sb.toString());
				ps = con.prepareStatement(sb.toString());
				nIndex = 1;
				ps.setLong(nIndex++, lUpdateStatus);
				ps.setLong(nIndex++, ipayID);
				//执行update
				lResult = ps.executeUpdate();
				// 关闭数据库资源
		
				ps.close();
				ps = null;
				con.close();
				con = null;
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			} finally {
				try {
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (Exception e) {
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
		}
		return lResult;
			}
			public static void noShowPrintHeadAndFooter(
				JspWriter out,
				long lTypeID,
				long lOfficeID) {
				try {
				IPrint.noShowPrintHeadAndFooter(out,lTypeID,lOfficeID);
				} catch (Exception e) {
				}
			}
			public static void showGrantLoan(
				JspWriter out,
				ShowGrantLoanInfo info,
				int i)
				throws Exception {
                    try
                    {
                        boolean bIsYT = false;
                        if (info.getLoanType().indexOf("银团") >= 0)
                        {
                            bIsYT = true;
                        }
                        String strBuffer = "";
                        switch (i)
                        {
                            case (1) :
                                strBuffer = "           <TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>附<BR>件</FONT> ";
                                break;
                            case (2) :
                                strBuffer = "           <TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>业<BR>务<BR>留<BR>存</FONT> ";
                                break;
                            case (3) :
                                strBuffer = "           <TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> ";
                                break;
                            case (4) :
                                strBuffer = "           <TD height=\"230\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>留<BR>存<BR>卡<BR>片</FONT> ";
                                break;
                        }
                        out.println(
                            "<Script Language=\"JavaScript\"> document.write(' "
                                + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                                + " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
                                + " <style type=\"text/css\"> "
                                + " <!-- "
                                + " .In1-table1 {  border: 2px #000000 solid} "
                                + " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
                                + " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
                                + " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
                                + " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                                + " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
                                + " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
                                + " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                                + " --> "
                                + " </style> "
                                + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                                + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                                + "     <TR> "
                                + "         <TD width=\"70\" height=\"46\" nowrap>&nbsp;     "
                                + "         </TD> "
                                + "          "
                                + "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"470\" nowrap><strong><font size =\"+1.5\">"
                                + Env.getClientName()
                                + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
                                + "      贷款发放凭证（一式四联）</font></font></strong></strong></TD> "
                                + "         <TD width=\"90\" nowrap>&nbsp;   "
                                + "         </TD> "
                                + "     </TR> "
                                + "     <TR> "
                                + "         <TD width=\"90\">&nbsp;  "
                                + "         </TD> "
                                + " </TABLE> "
                                + "  "
                                + "<TABLE width=\"630\"> "
                                + "  <tr>  "
                                + "    <TD align=\"left\" width=\"244\"><B>货款种类：</B><u><b>"
                                + info.getLoanType()
                                + " </b></u></TD> "
                                + "    <TD width=\"386\" align=\"right\"><B>"
                                + info.getYear()
                                + " 年 "
                                + info.getMonth()
                                + " 月 "
                                + info.getDay()
                                + " 日 "
                                + "</B> </TD> "
                                + "  </TR> "
                                + "</TABLE> "
                                + "  "
                                + "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\" align=\"left\"> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
                                + "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
                                + info.getLoanUnit()
                                + "</TD> "
                                + "  </TR> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
                                + "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
                                + info.getOpenBankName()
                                + "</TD> "
                                + "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
                                + "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
                                + info.getAccountNo()
                                + "</TD> "
                                + "  </TR> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" height=\"24\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
                                + "    <td height=\"24\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
                                + info.getContractCode()
                                + "</td> "
                                + "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>委托单位</B></td> "
                                + "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
                                + info.getConsignUnit()
                                + "</td> "
                                + "  </TR> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
                                + "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
                                + info.getLoanInterval()
                                + "</td> "
                                + "  </TR> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
                                + info.getCurrencyName()
                                + "<br> "
                                + "      （大写）</B> </TD> "
                                + "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
                                + info.getChineseAmount()
                                + "</b></td> "
                                + "    <td height=\"24\" align=\"right\" nowrap class=\"td-bottom\">"
                                + info.getAmount()
                                + "</td> "
                                + "  </TR> "
                                + "  <TR>  "
                                + "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
                                + "    <td align=\"left\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
                                + info.getContractRate()
                                + (info.getContractRate().length() == 0?"0":"%")
                                + "</td> "
                                + "    <td align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>"
                                + (bIsYT ? "代理" : "手续")
                                + "费率</b>"
                                + "</td> "
                                + "    <td align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
                                + info.getChargeRate()
                                + ""
                                + (info.getChargeRate().length() == 0?"0":(bIsYT ? "‰" : "%"))
                                + "</td> "
                                + "  </TR> "
                                + "  <TR rowspan=\"3\">  "
                                + "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> ：</TD> "
                                + "    <td height=\"72\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
                                + info.getAbstract()
                                + "</td> "
                                + "  </TR> "
                                + "</TABLE> "
                                + " <TABLE width=\"30\" border=\"0\"> "
                                + "     <TR> "
                                + strBuffer
                                + "         </TD> "
                                + "     </TR> "
                                + " </TABLE> "
                                + "<br>"
                                + "<Table width=\"600  \" border=\"0\">"
                                + "  <TR> "
                                //+ "  <TD height=\"22\" nowrap>&nbsp; </TD>"
                                + "    <TD width=\"100\" height=\"22\" nowrap align=\"right\">[主管总经理] &nbsp;</TD>"
                                + "    <TD width=\"80\" height=\"22\" nowrap align=\"left\">"
                                + info.getManagerLeaderName()
                                + "</TD>"
                                + "    <TD width=\"100\" height=\"22\" nowrap align=\"right\">[部门经理]&nbsp; </TD>"
                                + "    <TD width=\"80\" height=\"22\" nowrap align=\"left\">"
                                + info.getManagerName()
                                + "</TD>"
                                + "    <TD width=\"70\" height=\"22\" nowrap align=\"right\">[复核]&nbsp; </TD>"
                                + "    <TD width=\"80\" height=\"22\" nowrap align=\"left\">"
                                + info.getCheckUserName()
                                + "</TD>"
                                + "    <TD width=\"70\" height=\"22\" nowrap align=\"right\">[经办]&nbsp; </TD>"
                                + "    <TD width=\"80\" height=\"22\" nowrap align=\"left\">"
                                + info.getInputUserName()
                                + "</TD>"
                                + "  </TR>"
                                + "  </Table>"
                                + "</BODY> "
                                + " "
                                + " '); </SCRIPT>  ");
                    }
                    catch (Exception e)
                    {
                    }
    }
		}
