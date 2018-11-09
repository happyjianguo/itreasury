/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 *
 * <p>Title: TransSpecialOperationInfo类 </p>
 * <p>Description:　特殊业务交易细节类,对应db数据库中sett_TransSpecialOperation表 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author Yuqiangliao
 * @version 1.0
 */

public class TransSpecialOperationInfo implements Serializable
{
  /**
   * 获取交易状态编号
   *
   * @return long
   */
  public long getNstatusid(){ return nstatusid; }
  /**
   * 设置交易状态编号
   * @param nstatusid　交易状态编号，类型 long
   */
  public void setNstatusid(long nstatusid){ this.nstatusid = nstatusid; }

  /**
   * 获取确认摘要
   *
   * @return　String
   */
  public String getSconfirmabstract(){ return sconfirmabstract; }
  /**
   * 设置确认摘要
   *
   * @param sconfirmabstract　确认摘要，类型　String
   */
  public void setSconfirmabstract(String sconfirmabstract){ this.sconfirmabstract = sconfirmabstract; }

  /**
   *获得取消复核摘要
   *
   * @return String
   */
  public String getScanclecheckabstract(){ return scanclecheckabstract; }
  /**
   * 设置取消复核摘要
   *
   * @param scanclecheckabstract 取消复核摘要,类型 String
   */
  public void setScanclecheckabstract(String scanclecheckabstract){ this.scanclecheckabstract = scanclecheckabstract; }
  /**
   * 获得复核摘要
   *
   * @return String
   */
  public String getScheckabstract(){ return scheckabstract; }
  /**
   * 设置复核摘要
   *
   * @param scheckabstract　复核摘要，类型 String
   */
  public void setScheckabstract(String scheckabstract){ this.scheckabstract = scheckabstract; }
  /**
   * 获得摘要
   *
   * @return　String
   */
  public String getSabstract(){ return sabstract; }

  /**
   * 设置摘要
   *
   * @param sabstract　摘要，类型　String
   */
  public void setSabstract(String sabstract){ this.sabstract = sabstract; }
  /**
   *获得摘要ID
   *
   * @return　long
   */
  public long getNabstractid(){ return nabstractid; }
  /**
   * 设置摘要ID
   *
   * @param nabstractid　摘要ID，类型　long
   */
  public void setNabstractid(long nabstractid){ this.nabstractid = nabstractid; }
  /**
   *获得通存通兑对方办事处编号
   *
   * @return　long
   */
  public long getNconfirmofficeid(){ return nconfirmofficeid; }
  /**
   * 设置通存通兑对方办事处编号
   *
   * @param nconfirmofficeid　通存通兑对方办事处编号，类型 long
   */
  public void setNconfirmofficeid(long nconfirmofficeid){ this.nconfirmofficeid = nconfirmofficeid; }
  /**
   * 获得确认人编号
   *
   * @return long
   */
  public long getNconfirmuserid(){ return nconfirmuserid; }
  /**
   * 设置确认人编号
   *
   * @param nconfirmuserid　确认人编号，类型 long
   */
  public void setNconfirmuserid(long nconfirmuserid){ this.nconfirmuserid = nconfirmuserid; }
  /**
   *获得签认人编号
   *
   * @return　long
   */
  public long getNsignuserid(){ return nsignuserid; }
  /**
   * 设置签认人编号
   *
   * @param nsignuserid　签认人编号，类型 long
   */
  public void setNsignuserid(long nsignuserid){ this.nsignuserid = nsignuserid; }
  /**
   * 获得复核人编号
   *
   * @return　long
   */
  public long getNcheckuserid(){ return ncheckuserid; }
  /**
   * 设置复核人编号
   * @param ncheckuseid　复核人编号，类型 long
   */
  public void setNcheckuserid(long ncheckuserid){ this.ncheckuserid = ncheckuserid; }
  /**
   * 获得录入时间：年月日时分秒
   *
   * @return Timestamp
   */
  public Timestamp getDtinput(){ return dtinput; }
  /**
   * 设置录入时间：年月日时分秒
   *
   * @param dtinput　录入时间：年月日时分秒，类型 Timestamp
   */
  public void setDtinput(Timestamp dtinput){ this.dtinput = dtinput; }
  /**
   *获得录入人编号
   *
   * @return　long
   */
  public long getNinputuserid(){ return ninputuserid; }
  /**
   * 设置录入人编号
   *
   * @param ninputuserid　录入人编号，类型　long
   */
  public void setNinputuserid(long ninputuserid){ this.ninputuserid = ninputuserid; }
  /**
   * 获取　修改时间：年月日时分秒
   *
   * @return String
   */
  public Timestamp getDtmodify(){ return dtmodify; }
  /**
   * 设置　修改时间：年月日时分秒
   * 　
   * @param dtmodify　修改时间：年月日时分秒，类型 Timestamp
   */
  public void setDtmodify(Timestamp dtmodify){ this.dtmodify = dtmodify; }
  /**
   *获得　修改时间：年月日时分秒　
   *
   * @return　Timestamp
   */
  public Timestamp getDtexecute(){ return dtexecute; }
  /**
   * 设置执行日期
   *
   * @param dtexecute 执行日期,类型 Timestamp
   */
  public void setDtexecute(Timestamp dtexecute){ this.dtexecute = dtexecute; }
  /**
   * 获得执行日期
   *
   * @return　Timestamp
   */
  public Timestamp getDtintereststart(){ return dtintereststart; }
  /**
   *设置结息记录起息日期　
   *
   * @param dtintereststart　结息记录起息日期，类型　Timestamp
   */
  public void setDtintereststart(Timestamp dtintereststart){ this.dtintereststart = dtintereststart; }
  /**
   * 获得结息记录起息日期
   *
   * @return　String
   */
  public String getSdeclarationno(){ return sdeclarationno; }
  /**
   *设置报单号
   *
   * @param sdeclarationno　报单号，类型　String
   */
  public void setSdeclarationno(String sdeclarationno){ this.sdeclarationno = sdeclarationno; }
  /**
   * 获得报单号
   *
   * @return String
   */
  public String getSbankchequeno(){ return sbankchequeno; }
  /**
   *设置银行支票号
   *
   * @param sbankchequeno　银行支票号，类型 String
   */
  public void setSbankchequeno(String sbankchequeno){ this.sbankchequeno = sbankchequeno; }
  /**
   * 获得银行支票号
   *
   * @return String
   */
  public String getSremitincity(){ return sremitincity; }
  /**
   *设置汇入市
   *
   * @param sremitincity　汇入市，类型　String
   */
  public void setSremitincity(String sremitincity){ this.sremitincity = sremitincity; }
  /**
   * 获得汇入市
   *
   * @return String
   */
  public String getSremitinprovince(){ return sremitinprovince; }
  /**
   * 设置汇入省
   *
   * @param sremitinprovince　汇入省，类型　String
   */
  public void setSremitinprovince(String sremitinprovince){ this.sremitinprovince = sremitinprovince; }
  /**
   *获得汇入省
   *
   * @return String
   */
  public String getSremitinbank(){ return sremitinbank; }
  /**
   * 设置汇入行名称
   *
   * @param sremitinbank 汇入行名称,类型 String
   */
  public void setSremitinbank(String sremitinbank){ this.sremitinbank = sremitinbank; }
  /**
   * 获得汇入行名称
   *
   * @return String
   */
  public String getSextclientname(){ return sextclientname; }
  /**
   *设置非财务公司账户名称
   *
   * @param sextclientname 非财务公司账户名称,类型 String
   */
  public void setSextclientname(String sextclientname){ this.sextclientname = sextclientname; }
  /**
   * 获得非财务公司账户名称
   *
   * @return String
   */
  public String getSextaccountno(){ return sextaccountno; }
  /**
   *设置非财务公司账户号
   * @param sextaccountno 非财务公司账户号,类型 String
   */
  public void setSextaccountno(String sextaccountno){ this.sextaccountno = sextaccountno; }
  /**
   * 获得票据发放银行ID
   *
   * @return long
   */
  public long getNbillbankid(){ return nbillbankid; }
  /**
   * 设置票据发放银行ID
   *
   * @param nbillbankid 票据发放银行ID,类型 long
   */
  public void setNbillbankid(long nbillbankid){ this.nbillbankid = nbillbankid; }
  /**
   *获得票据类型ID
   *
   * @return long
   */
  public long getNbilltypeid(){ return nbilltypeid; }
  /**
   * 设置票据类型ID
   *
   * @param nbilltypeid 票据类型ID,类型 long
   */
  public void setNbilltypeid(long nbilltypeid){ this.nbilltypeid = nbilltypeid; }
  /**
   * 获得票据号
   *
   * @return String
   */
  public String getSbillno(){ return sbillno; }
  /**
   * 设置票据号
   *
   * @param sbillno 票据号, 类型 String
   */
  public void setSbillno(String sbillno){ this.sbillno = sbillno; }
  /**
   * 获得委托付款凭证密码
   *
   * @return String
   */
  public String getSvoucherpwd(){ return svoucherpwd; }
  /**
   * 设置委托付款凭证密码
   *
   * @param svoucherpwd 委托付款凭证密码，类型 String
   */
  public void setSvoucherpwd(String svoucherpwd){ this.svoucherpwd = svoucherpwd; }
  /**
   *获得委托付款凭证号
   *
   * @return String
   */
  public String getSvoucherno(){ return svoucherno; }
  /**
   * 设置委托付款凭证号
   *
   * @param svoucherno 委托付款凭证号,类型 String
   */
  public void setSvoucherno(String svoucherno){ this.svoucherno = svoucherno; }
  /**
   *获得收款方余额
   *
   * @return double
   */
  public double getMreceiveremainbalance(){ return mreceiveremainbalance; }
  /**
   * 设置收款方余额
   *
   * @param mreceiveremainbalance 收款方余额,类型 double
   */
  public void setMreceiveremainbalance(double mreceiveremainbalance){ this.mreceiveremainbalance = mreceiveremainbalance; }
  /**
   *获得收款方向标志
   *
   * @return long
   */
  public long getNreceivedirection(){ return nreceivedirection; }
  /**
   * 设置收款方向标志
   *
   * @param nerceivedirection 收款方向标志,类型 long
   */
  public void setNreceivedirection(long nreceivedirection){ this.nreceivedirection = nreceivedirection; }
  /**
   * 获得收款金额
   *
   * @return double
   */
  public double getMreceiveamount(){ return mreceiveamount; }
  /**
   * 设置收款金额
   *
   * @param mreceiveamount 收款金额,类型 double
   */
  public void setMreceiveamount(double mreceiveamount){ this.mreceiveamount = mreceiveamount; }
  /**
   * 获得收款方总账类ID
   *
   * @return long
   */
  public long getNreceivegeneralledgertypeid(){ return nreceivegeneralledgertypeid; }
  /**
   * 设置收款方总账类ID
   *
   * @param nreceivegeneralleadgertypeid 收款方总账类ID,类型 long
   */
  public void setNreceivegeneralledgertypeid(long nreceivegeneralledgertypeid){ this.nreceivegeneralledgertypeid = nreceivegeneralledgertypeid; }
  /**
   *获得收款方银行单边账类型ID
   *
   * @return long
   */
  public long getNreceivesinglebankaccounttypei(){ return nreceivesinglebankaccounttypei; }
  /**
   * 设置收款方银行单边账类型ID
   *
   * @param nreceivesinglebankaccounttypei 收款方银行单边账类型ID,类型 long
   */
  public void setNreceivesinglebankaccounttypei(long nreceivesinglebankaccounttypei){ this.nreceivesinglebankaccounttypei = nreceivesinglebankaccounttypei; }
  /**
   * 获得收款方现金流向ID
   *
   * @return long
   */
  public long getNreceivecashflowid(){ return nreceivecashflowid; }
  /**
   * 设置收款方现金流向ID
   *
   * @param nreceivecashflowid 收款方现金流向ID,类型　long
   */
  public void setNreceivecashflowid(long nreceivecashflowid){ this.nreceivecashflowid = nreceivecashflowid; }
  /**
   *获得提入行编号
   *
   * @return String
   */
  public String getSreceiveextbankno(){ return sreceiveextbankno; }
  /**
   * 设置提入行编号
   *
   * @param serceiveextbankno 提入行编号提入行编号,类型 String
   */
  public void setSreceiveextbankno(String sreceiveextbankno){ this.sreceiveextbankno = sreceiveextbankno; }
  /**
   * 获得收款方开户行ID
   *
   * @return long
   */
  public long getNreceivebankid(){ return nreceivebankid; }
  /**
   * 设置收款方开户行ID
   *
   * @param nreceivebankid 收款方开户行ID,类型 long
   */
  public void setNreceivebankid(long nreceivebankid){ this.nreceivebankid = nreceivebankid; }
  /**
   * 获得收款方子账户ID
   *
   * @return long
   */
  public long getNreceivesubaccountid(){ return nreceivesubaccountid; }
  /**
   * 设置收款方子账户ID
   *
   * @param nreceivesubaccountid 收款方子账户ID,类型 long
   */
  public void setNreceivesubaccountid(long nreceivesubaccountid){ this.nreceivesubaccountid = nreceivesubaccountid; }

  public long getNreceiveloannoteid(){ return nreceiveloannoteid; }

  public void setNreceiveloannoteid(long nreceiveloannoteid){ this.nreceiveloannoteid = nreceiveloannoteid; }

  public long getNreceivecontractid(){ return nreceivecontractid; }

  public void setNreceivecontractid(long nreceivecontractid){ this.nreceivecontractid = nreceivecontractid; }

  public String getSreceivefixeddepositno(){ return sreceivefixeddepositno; }

  public void setSreceivefixeddepositno(String sreceivefixeddepositno){ this.sreceivefixeddepositno = sreceivefixeddepositno; }

  public long getNreceiveaccountid(){ return nreceiveaccountid; }

  public void setNreceiveaccountid(long nreceiveaccountid){ this.nreceiveaccountid = nreceiveaccountid; }

  public long getNreceiveclientid(){ return nreceiveclientid; }

  public void setNreceiveclientid(long nreceiveclientid){ this.nreceiveclientid = nreceiveclientid; }

  public long getNpaydirection(){ return npaydirection; }

  public void setNpaydirection(long npaydirection){ this.npaydirection = npaydirection; }

  public double getMpayremainbalance(){ return mpayremainbalance; }

  public void setMpayremainbalance(double mpayremainbalance){ this.mpayremainbalance = mpayremainbalance; }

  public double getMpayamount(){ return mpayamount; }

  public void setMpayamount(double mpayamount){ this.mpayamount = mpayamount; }

  public long getNpaygeneralledgertypeid(){ return npaygeneralledgertypeid; }

  public void setNpaygeneralledgertypeid(long npaygeneralledgertypeid){ this.npaygeneralledgertypeid = npaygeneralledgertypeid; }

  public long getNpaysinglebankaccounttypeid(){ return npaysinglebankaccounttypeid; }

  public void setNpaysinglebankaccounttypeid(long npaysinglebankaccounttypeid){ this.npaysinglebankaccounttypeid = npaysinglebankaccounttypeid; }

  public long getNpaycashflowid(){ return npaycashflowid; }

  public void setNpaycashflowid(long npaycashflowid){ this.npaycashflowid = npaycashflowid; }

  public String getSpayextbankno(){ return spayextbankno; }

  public void setSpayextbankno(String spayextbankno){ this.spayextbankno = spayextbankno; }

  public long getNpaybankid(){ return npaybankid; }

  public void setNpaybankid(long npaybankid){ this.npaybankid = npaybankid; }

  public long getNpaysubaccountid(){ return npaysubaccountid; }

  public void setNpaysubaccountid(long npaysubaccountid){ this.npaysubaccountid = npaysubaccountid; }

  public long getNpayloannoteid(){ return npayloannoteid; }

  public void setNpayloannoteid(long npayloannoteid){ this.npayloannoteid = npayloannoteid; }

  public long getNpaycontractid(){ return npaycontractid; }

  public void setNpaycontractid(long npaycontractid){ this.npaycontractid = npaycontractid; }

  public String getSpayfixeddepositno(){ return spayfixeddepositno; }

  public void setSpayfixeddepositno(String spayfixeddepositno){ this.spayfixeddepositno = spayfixeddepositno; }

  public long getNpayaccountid(){ return npayaccountid; }

  public void setNpayaccountid(long npayaccountid){ this.npayaccountid = npayaccountid; }

  public long getNpayclientid(){ return npayclientid; }

  public void setNpayclientid(long npayclientid){ this.npayclientid = npayclientid; }

  public long getNoperationtypeid(){ return noperationtypeid; }

  public void setNoperationtypeid(long noperationtypeid){ this.noperationtypeid = noperationtypeid; }

  public long getNtransactiontypeid(){ return ntransactiontypeid; }

  public void setNtransactiontypeid(long ntransactiontypeid){ this.ntransactiontypeid = ntransactiontypeid; }

  public String getStransno(){ return stransno; }

  public void setStransno(String stransno){ this.stransno = stransno; }

  public long getNcurrencyid(){ return ncurrencyid; }

  public void setNcurrencyid(long ncurrencyid){ this.ncurrencyid = ncurrencyid; }

  public long getNofficeid(){ return nofficeid; }

  public void setNofficeid(long nofficeid){ this.nofficeid = nofficeid; }

  public long getId(){ return id; }

  public void setId(long id){ this.id = id; }
  
  
  public String getSinstructionno() {  return sinstructionno;  }
  
  public void setSinstructionno(String sinstructionNo) {	  this.sinstructionno = sinstructionNo;  }
  
  
  
  //清算表单的凭证种类描述
  private String sreckoningbilltypedesc=null;
  //清算类型
  private long nreckoningtypeid=-1;
  //非结算系统流水号（网上银行/证券）
  private String sinstructionno = null;	 
  //交易状态
  private long nstatusid=-1;
  //确认摘要
  private String sconfirmabstract=null;
  //取消复核摘要
  private String scanclecheckabstract=null;
  //复核摘要
  private String scheckabstract=null;
  //摘要
  private String sabstract=null;
  //摘要ID
  private long nabstractid=-1;
  //通存通兑对方办事处
  private long nconfirmofficeid=-1;
  //确认人
  private long nconfirmuserid=-1;
  //签认人
  private long nsignuserid=-1;
  //复核人
  private long ncheckuserid=-1;
  //录入时间：年月日时分秒
  private Timestamp dtinput;
  //录入人
  private long ninputuserid=-1;
  //修改时间：年月日时分秒
  private Timestamp dtmodify=null;
  //执行日
  private Timestamp dtexecute=null;
  //起息日
  private Timestamp dtintereststart=null;
  //报单号
  private String sdeclarationno=null;
  //银行支票号
  private String sbankchequeno=null;
  //汇入市
  private String sremitincity=null;
  //汇入省
  private String sremitinprovince=null;
  //汇入行名称
  private String sremitinbank=null;
  //非财务公司账户名称
  private String sextclientname=null;
  //非财务公司账户号
  private String sextaccountno=null;
  //票据发放银行ID
  private long nbillbankid=-1;
  //票据类型ID
  private long nbilltypeid=-1;
  //票据号
  private String sbillno=null;
  //委托付款凭证密码
  private String svoucherpwd=null;
  //委托付款凭证号
  private String svoucherno=null;
  //收款方余额
  private double mreceiveremainbalance=0;
  //收款方向
  private long nreceivedirection=-1;
  //收款金额
  private double mreceiveamount=0;
  //收款方总账类ID
  private long nreceivegeneralledgertypeid=-1;  	 		   
  //收款方银行单边账类型ID
  private long nreceivesinglebankaccounttypei=-1;
  //收款方现金流向ID
  private long nreceivecashflowid=-1;
  //提入行
  private String sreceiveextbankno=null;
  //收款方开户行ID
  private long nreceivebankid=-1;
  //收款方子账户ID
  private long nreceivesubaccountid=-1;
  //收款方放款通知单ID
  private long nreceiveloannoteid=-1;
  //收款方合同ID
  private long nreceivecontractid=-1;
  //收款方存单号
  private String sreceivefixeddepositno=null;
  //收款方账户ID
  private long nreceiveaccountid=-1;
  //收款方客户ID
  private long nreceiveclientid=-1;
  //付款方向
  private long npaydirection=-1;
  //付款方余额
  private double mpayremainbalance=0;
  //付款金额
  private double mpayamount=0;
  //付款方总账类ID
  private long npaygeneralledgertypeid=-1;
  //付款方银行单边账类型ID
  private long npaysinglebankaccounttypeid=-1;
  //付款方现金流向ID
  private long npaycashflowid=-1;
  //提出行
  private String spayextbankno=null;
  //付款方开户行ID
  private long npaybankid=-1;
  //付款方子账户ID
  private long npaysubaccountid=-1;
  //付款方放款通知单ID
  private long npayloannoteid=-1;
  //付款方合同ID
  private long npaycontractid=-1;
  //付款方存单号
  private String spayfixeddepositno=null;
  //付款方账户ID
  private long npayaccountid=-1;
  //付款方客户ID
  private long npayclientid=-1;
  //特殊交易类型
  private long noperationtypeid=-1;
  //交易类型
  private long ntransactiontypeid=-1;
  //交易号
  private String stransno=null;
  //币种
  private long ncurrencyid=-1;
  //办事处
  private long nofficeid=-1;
  //内部编号
  private long id=-1;  
  
  
   //手续费金额
   private double mcommissionamount = 0.00;
    
   //手续费类型
   private long ncommissiontype = -1;
    
   //手续费交易号
   private String scommissiontransno = "";
   
   private String instructionno=""; //银行指令号 
   private boolean autocreatebankinstruction = true;
  //审批参数
   private InutParameterInfo inutParameterInfo = null;
   
 //联行号
	private String spayeebankexchangeno="";
	//CNAPS号
	private String spayeebankcnapsno ="";
	//机构号
	private String spayeebankorgno ="";

/**
 * @return Returns the nreckoningtypeid.
 */
public long getNreckoningtypeid()
{
	return nreckoningtypeid;
}
/**
 * @param nreckoningtypeid The nreckoningtypeid to set.
 */
public void setNreckoningtypeid(long nreckoningtypeid)
{
	this.nreckoningtypeid = nreckoningtypeid;
}
/**
 * @return Returns the sreckoningbilltypedesc.
 */
public String getSreckoningbilltypedesc()
{
	return sreckoningbilltypedesc;
}
/**
 * @param sreckoningbilltypedesc The sreckoningbilltypedesc to set.
 */
public void setSreckoningbilltypedesc(String sreckoningbilltypedesc)
{
	this.sreckoningbilltypedesc = sreckoningbilltypedesc;
}
/**
 * @return Returns the mcommissionamount.
 */
public double getMcommissionamount()
{
    return mcommissionamount;
}
/**
 * @param mcommissionamount The mcommissionamount to set.
 */
public void setMcommissionamount(double mcommissionamount)
{
    this.mcommissionamount = mcommissionamount;
}
/**
 * @return Returns the ncommissiontype.
 */
public long getNcommissiontype()
{
    return ncommissiontype;
}
/**
 * @param ncommissiontype The ncommissiontype to set.
 */
public void setNcommissiontype(long ncommissiontype)
{
    this.ncommissiontype = ncommissiontype;
}
/**
 * @return Returns the scommissiontransno.
 */
public String getScommissiontransno()
{
    return scommissiontransno;
}
/**
 * @param scommissiontransno The scommissiontransno to set.
 */
public void setScommissiontransno(String scommissiontransno)
{
    this.scommissiontransno = scommissiontransno;
}

public String getInstructionno() {
	return instructionno;
}
public void setInstructionno(String instructionno) {
	this.instructionno = instructionno;
}

public boolean isAutocreatebankinstruction() {
	return autocreatebankinstruction;
}
public void setAutocreatebankinstruction(boolean autocreatebankinstruction) {
	this.autocreatebankinstruction = autocreatebankinstruction;
}
public InutParameterInfo getInutParameterInfo() {
	return inutParameterInfo;
}
public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
	this.inutParameterInfo = inutParameterInfo;
}
public String getSpayeebankexchangeno() {
	return spayeebankexchangeno;
}
public void setSpayeebankexchangeno(String spayeebankexchangeno) {
	this.spayeebankexchangeno = spayeebankexchangeno;
}
public String getSpayeebankcnapsno() {
	return spayeebankcnapsno;
}
public void setSpayeebankcnapsno(String spayeebankcnapsno) {
	this.spayeebankcnapsno = spayeebankcnapsno;
}
public String getSpayeebankorgno() {
	return spayeebankorgno;
}
public void setSpayeebankorgno(String spayeebankorgno) {
	this.spayeebankorgno = spayeebankorgno;
}


}
