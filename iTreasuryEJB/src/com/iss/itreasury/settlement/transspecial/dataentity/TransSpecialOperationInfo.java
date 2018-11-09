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
 * <p>Title: TransSpecialOperationInfo�� </p>
 * <p>Description:������ҵ����ϸ����,��Ӧdb���ݿ���sett_TransSpecialOperation�� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author Yuqiangliao
 * @version 1.0
 */

public class TransSpecialOperationInfo implements Serializable
{
  /**
   * ��ȡ����״̬���
   *
   * @return long
   */
  public long getNstatusid(){ return nstatusid; }
  /**
   * ���ý���״̬���
   * @param nstatusid������״̬��ţ����� long
   */
  public void setNstatusid(long nstatusid){ this.nstatusid = nstatusid; }

  /**
   * ��ȡȷ��ժҪ
   *
   * @return��String
   */
  public String getSconfirmabstract(){ return sconfirmabstract; }
  /**
   * ����ȷ��ժҪ
   *
   * @param sconfirmabstract��ȷ��ժҪ�����͡�String
   */
  public void setSconfirmabstract(String sconfirmabstract){ this.sconfirmabstract = sconfirmabstract; }

  /**
   *���ȡ������ժҪ
   *
   * @return String
   */
  public String getScanclecheckabstract(){ return scanclecheckabstract; }
  /**
   * ����ȡ������ժҪ
   *
   * @param scanclecheckabstract ȡ������ժҪ,���� String
   */
  public void setScanclecheckabstract(String scanclecheckabstract){ this.scanclecheckabstract = scanclecheckabstract; }
  /**
   * ��ø���ժҪ
   *
   * @return String
   */
  public String getScheckabstract(){ return scheckabstract; }
  /**
   * ���ø���ժҪ
   *
   * @param scheckabstract������ժҪ������ String
   */
  public void setScheckabstract(String scheckabstract){ this.scheckabstract = scheckabstract; }
  /**
   * ���ժҪ
   *
   * @return��String
   */
  public String getSabstract(){ return sabstract; }

  /**
   * ����ժҪ
   *
   * @param sabstract��ժҪ�����͡�String
   */
  public void setSabstract(String sabstract){ this.sabstract = sabstract; }
  /**
   *���ժҪID
   *
   * @return��long
   */
  public long getNabstractid(){ return nabstractid; }
  /**
   * ����ժҪID
   *
   * @param nabstractid��ժҪID�����͡�long
   */
  public void setNabstractid(long nabstractid){ this.nabstractid = nabstractid; }
  /**
   *���ͨ��ͨ�ҶԷ����´����
   *
   * @return��long
   */
  public long getNconfirmofficeid(){ return nconfirmofficeid; }
  /**
   * ����ͨ��ͨ�ҶԷ����´����
   *
   * @param nconfirmofficeid��ͨ��ͨ�ҶԷ����´���ţ����� long
   */
  public void setNconfirmofficeid(long nconfirmofficeid){ this.nconfirmofficeid = nconfirmofficeid; }
  /**
   * ���ȷ���˱��
   *
   * @return long
   */
  public long getNconfirmuserid(){ return nconfirmuserid; }
  /**
   * ����ȷ���˱��
   *
   * @param nconfirmuserid��ȷ���˱�ţ����� long
   */
  public void setNconfirmuserid(long nconfirmuserid){ this.nconfirmuserid = nconfirmuserid; }
  /**
   *���ǩ���˱��
   *
   * @return��long
   */
  public long getNsignuserid(){ return nsignuserid; }
  /**
   * ����ǩ���˱��
   *
   * @param nsignuserid��ǩ���˱�ţ����� long
   */
  public void setNsignuserid(long nsignuserid){ this.nsignuserid = nsignuserid; }
  /**
   * ��ø����˱��
   *
   * @return��long
   */
  public long getNcheckuserid(){ return ncheckuserid; }
  /**
   * ���ø����˱��
   * @param ncheckuseid�������˱�ţ����� long
   */
  public void setNcheckuserid(long ncheckuserid){ this.ncheckuserid = ncheckuserid; }
  /**
   * ���¼��ʱ�䣺������ʱ����
   *
   * @return Timestamp
   */
  public Timestamp getDtinput(){ return dtinput; }
  /**
   * ����¼��ʱ�䣺������ʱ����
   *
   * @param dtinput��¼��ʱ�䣺������ʱ���룬���� Timestamp
   */
  public void setDtinput(Timestamp dtinput){ this.dtinput = dtinput; }
  /**
   *���¼���˱��
   *
   * @return��long
   */
  public long getNinputuserid(){ return ninputuserid; }
  /**
   * ����¼���˱��
   *
   * @param ninputuserid��¼���˱�ţ����͡�long
   */
  public void setNinputuserid(long ninputuserid){ this.ninputuserid = ninputuserid; }
  /**
   * ��ȡ���޸�ʱ�䣺������ʱ����
   *
   * @return String
   */
  public Timestamp getDtmodify(){ return dtmodify; }
  /**
   * ���á��޸�ʱ�䣺������ʱ����
   * ��
   * @param dtmodify���޸�ʱ�䣺������ʱ���룬���� Timestamp
   */
  public void setDtmodify(Timestamp dtmodify){ this.dtmodify = dtmodify; }
  /**
   *��á��޸�ʱ�䣺������ʱ���롡
   *
   * @return��Timestamp
   */
  public Timestamp getDtexecute(){ return dtexecute; }
  /**
   * ����ִ������
   *
   * @param dtexecute ִ������,���� Timestamp
   */
  public void setDtexecute(Timestamp dtexecute){ this.dtexecute = dtexecute; }
  /**
   * ���ִ������
   *
   * @return��Timestamp
   */
  public Timestamp getDtintereststart(){ return dtintereststart; }
  /**
   *���ý�Ϣ��¼��Ϣ���ڡ�
   *
   * @param dtintereststart����Ϣ��¼��Ϣ���ڣ����͡�Timestamp
   */
  public void setDtintereststart(Timestamp dtintereststart){ this.dtintereststart = dtintereststart; }
  /**
   * ��ý�Ϣ��¼��Ϣ����
   *
   * @return��String
   */
  public String getSdeclarationno(){ return sdeclarationno; }
  /**
   *���ñ�����
   *
   * @param sdeclarationno�������ţ����͡�String
   */
  public void setSdeclarationno(String sdeclarationno){ this.sdeclarationno = sdeclarationno; }
  /**
   * ��ñ�����
   *
   * @return String
   */
  public String getSbankchequeno(){ return sbankchequeno; }
  /**
   *��������֧Ʊ��
   *
   * @param sbankchequeno������֧Ʊ�ţ����� String
   */
  public void setSbankchequeno(String sbankchequeno){ this.sbankchequeno = sbankchequeno; }
  /**
   * �������֧Ʊ��
   *
   * @return String
   */
  public String getSremitincity(){ return sremitincity; }
  /**
   *���û�����
   *
   * @param sremitincity�������У����͡�String
   */
  public void setSremitincity(String sremitincity){ this.sremitincity = sremitincity; }
  /**
   * ��û�����
   *
   * @return String
   */
  public String getSremitinprovince(){ return sremitinprovince; }
  /**
   * ���û���ʡ
   *
   * @param sremitinprovince������ʡ�����͡�String
   */
  public void setSremitinprovince(String sremitinprovince){ this.sremitinprovince = sremitinprovince; }
  /**
   *��û���ʡ
   *
   * @return String
   */
  public String getSremitinbank(){ return sremitinbank; }
  /**
   * ���û���������
   *
   * @param sremitinbank ����������,���� String
   */
  public void setSremitinbank(String sremitinbank){ this.sremitinbank = sremitinbank; }
  /**
   * ��û���������
   *
   * @return String
   */
  public String getSextclientname(){ return sextclientname; }
  /**
   *���÷ǲ���˾�˻�����
   *
   * @param sextclientname �ǲ���˾�˻�����,���� String
   */
  public void setSextclientname(String sextclientname){ this.sextclientname = sextclientname; }
  /**
   * ��÷ǲ���˾�˻�����
   *
   * @return String
   */
  public String getSextaccountno(){ return sextaccountno; }
  /**
   *���÷ǲ���˾�˻���
   * @param sextaccountno �ǲ���˾�˻���,���� String
   */
  public void setSextaccountno(String sextaccountno){ this.sextaccountno = sextaccountno; }
  /**
   * ���Ʊ�ݷ�������ID
   *
   * @return long
   */
  public long getNbillbankid(){ return nbillbankid; }
  /**
   * ����Ʊ�ݷ�������ID
   *
   * @param nbillbankid Ʊ�ݷ�������ID,���� long
   */
  public void setNbillbankid(long nbillbankid){ this.nbillbankid = nbillbankid; }
  /**
   *���Ʊ������ID
   *
   * @return long
   */
  public long getNbilltypeid(){ return nbilltypeid; }
  /**
   * ����Ʊ������ID
   *
   * @param nbilltypeid Ʊ������ID,���� long
   */
  public void setNbilltypeid(long nbilltypeid){ this.nbilltypeid = nbilltypeid; }
  /**
   * ���Ʊ�ݺ�
   *
   * @return String
   */
  public String getSbillno(){ return sbillno; }
  /**
   * ����Ʊ�ݺ�
   *
   * @param sbillno Ʊ�ݺ�, ���� String
   */
  public void setSbillno(String sbillno){ this.sbillno = sbillno; }
  /**
   * ���ί�и���ƾ֤����
   *
   * @return String
   */
  public String getSvoucherpwd(){ return svoucherpwd; }
  /**
   * ����ί�и���ƾ֤����
   *
   * @param svoucherpwd ί�и���ƾ֤���룬���� String
   */
  public void setSvoucherpwd(String svoucherpwd){ this.svoucherpwd = svoucherpwd; }
  /**
   *���ί�и���ƾ֤��
   *
   * @return String
   */
  public String getSvoucherno(){ return svoucherno; }
  /**
   * ����ί�и���ƾ֤��
   *
   * @param svoucherno ί�и���ƾ֤��,���� String
   */
  public void setSvoucherno(String svoucherno){ this.svoucherno = svoucherno; }
  /**
   *����տ���
   *
   * @return double
   */
  public double getMreceiveremainbalance(){ return mreceiveremainbalance; }
  /**
   * �����տ���
   *
   * @param mreceiveremainbalance �տ���,���� double
   */
  public void setMreceiveremainbalance(double mreceiveremainbalance){ this.mreceiveremainbalance = mreceiveremainbalance; }
  /**
   *����տ���־
   *
   * @return long
   */
  public long getNreceivedirection(){ return nreceivedirection; }
  /**
   * �����տ���־
   *
   * @param nerceivedirection �տ���־,���� long
   */
  public void setNreceivedirection(long nreceivedirection){ this.nreceivedirection = nreceivedirection; }
  /**
   * ����տ���
   *
   * @return double
   */
  public double getMreceiveamount(){ return mreceiveamount; }
  /**
   * �����տ���
   *
   * @param mreceiveamount �տ���,���� double
   */
  public void setMreceiveamount(double mreceiveamount){ this.mreceiveamount = mreceiveamount; }
  /**
   * ����տ������ID
   *
   * @return long
   */
  public long getNreceivegeneralledgertypeid(){ return nreceivegeneralledgertypeid; }
  /**
   * �����տ������ID
   *
   * @param nreceivegeneralleadgertypeid �տ������ID,���� long
   */
  public void setNreceivegeneralledgertypeid(long nreceivegeneralledgertypeid){ this.nreceivegeneralledgertypeid = nreceivegeneralledgertypeid; }
  /**
   *����տ���е���������ID
   *
   * @return long
   */
  public long getNreceivesinglebankaccounttypei(){ return nreceivesinglebankaccounttypei; }
  /**
   * �����տ���е���������ID
   *
   * @param nreceivesinglebankaccounttypei �տ���е���������ID,���� long
   */
  public void setNreceivesinglebankaccounttypei(long nreceivesinglebankaccounttypei){ this.nreceivesinglebankaccounttypei = nreceivesinglebankaccounttypei; }
  /**
   * ����տ�ֽ�����ID
   *
   * @return long
   */
  public long getNreceivecashflowid(){ return nreceivecashflowid; }
  /**
   * �����տ�ֽ�����ID
   *
   * @param nreceivecashflowid �տ�ֽ�����ID,���͡�long
   */
  public void setNreceivecashflowid(long nreceivecashflowid){ this.nreceivecashflowid = nreceivecashflowid; }
  /**
   *��������б��
   *
   * @return String
   */
  public String getSreceiveextbankno(){ return sreceiveextbankno; }
  /**
   * ���������б��
   *
   * @param serceiveextbankno �����б�������б��,���� String
   */
  public void setSreceiveextbankno(String sreceiveextbankno){ this.sreceiveextbankno = sreceiveextbankno; }
  /**
   * ����տ������ID
   *
   * @return long
   */
  public long getNreceivebankid(){ return nreceivebankid; }
  /**
   * �����տ������ID
   *
   * @param nreceivebankid �տ������ID,���� long
   */
  public void setNreceivebankid(long nreceivebankid){ this.nreceivebankid = nreceivebankid; }
  /**
   * ����տ���˻�ID
   *
   * @return long
   */
  public long getNreceivesubaccountid(){ return nreceivesubaccountid; }
  /**
   * �����տ���˻�ID
   *
   * @param nreceivesubaccountid �տ���˻�ID,���� long
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
  
  
  
  //�������ƾ֤��������
  private String sreckoningbilltypedesc=null;
  //��������
  private long nreckoningtypeid=-1;
  //�ǽ���ϵͳ��ˮ�ţ���������/֤ȯ��
  private String sinstructionno = null;	 
  //����״̬
  private long nstatusid=-1;
  //ȷ��ժҪ
  private String sconfirmabstract=null;
  //ȡ������ժҪ
  private String scanclecheckabstract=null;
  //����ժҪ
  private String scheckabstract=null;
  //ժҪ
  private String sabstract=null;
  //ժҪID
  private long nabstractid=-1;
  //ͨ��ͨ�ҶԷ����´�
  private long nconfirmofficeid=-1;
  //ȷ����
  private long nconfirmuserid=-1;
  //ǩ����
  private long nsignuserid=-1;
  //������
  private long ncheckuserid=-1;
  //¼��ʱ�䣺������ʱ����
  private Timestamp dtinput;
  //¼����
  private long ninputuserid=-1;
  //�޸�ʱ�䣺������ʱ����
  private Timestamp dtmodify=null;
  //ִ����
  private Timestamp dtexecute=null;
  //��Ϣ��
  private Timestamp dtintereststart=null;
  //������
  private String sdeclarationno=null;
  //����֧Ʊ��
  private String sbankchequeno=null;
  //������
  private String sremitincity=null;
  //����ʡ
  private String sremitinprovince=null;
  //����������
  private String sremitinbank=null;
  //�ǲ���˾�˻�����
  private String sextclientname=null;
  //�ǲ���˾�˻���
  private String sextaccountno=null;
  //Ʊ�ݷ�������ID
  private long nbillbankid=-1;
  //Ʊ������ID
  private long nbilltypeid=-1;
  //Ʊ�ݺ�
  private String sbillno=null;
  //ί�и���ƾ֤����
  private String svoucherpwd=null;
  //ί�и���ƾ֤��
  private String svoucherno=null;
  //�տ���
  private double mreceiveremainbalance=0;
  //�տ��
  private long nreceivedirection=-1;
  //�տ���
  private double mreceiveamount=0;
  //�տ������ID
  private long nreceivegeneralledgertypeid=-1;  	 		   
  //�տ���е���������ID
  private long nreceivesinglebankaccounttypei=-1;
  //�տ�ֽ�����ID
  private long nreceivecashflowid=-1;
  //������
  private String sreceiveextbankno=null;
  //�տ������ID
  private long nreceivebankid=-1;
  //�տ���˻�ID
  private long nreceivesubaccountid=-1;
  //�տ�ſ�֪ͨ��ID
  private long nreceiveloannoteid=-1;
  //�տ��ͬID
  private long nreceivecontractid=-1;
  //�տ�浥��
  private String sreceivefixeddepositno=null;
  //�տ�˻�ID
  private long nreceiveaccountid=-1;
  //�տ�ͻ�ID
  private long nreceiveclientid=-1;
  //�����
  private long npaydirection=-1;
  //������
  private double mpayremainbalance=0;
  //������
  private double mpayamount=0;
  //���������ID
  private long npaygeneralledgertypeid=-1;
  //������е���������ID
  private long npaysinglebankaccounttypeid=-1;
  //����ֽ�����ID
  private long npaycashflowid=-1;
  //�����
  private String spayextbankno=null;
  //���������ID
  private long npaybankid=-1;
  //������˻�ID
  private long npaysubaccountid=-1;
  //����ſ�֪ͨ��ID
  private long npayloannoteid=-1;
  //�����ͬID
  private long npaycontractid=-1;
  //����浥��
  private String spayfixeddepositno=null;
  //����˻�ID
  private long npayaccountid=-1;
  //����ͻ�ID
  private long npayclientid=-1;
  //���⽻������
  private long noperationtypeid=-1;
  //��������
  private long ntransactiontypeid=-1;
  //���׺�
  private String stransno=null;
  //����
  private long ncurrencyid=-1;
  //���´�
  private long nofficeid=-1;
  //�ڲ����
  private long id=-1;  
  
  
   //�����ѽ��
   private double mcommissionamount = 0.00;
    
   //����������
   private long ncommissiontype = -1;
    
   //�����ѽ��׺�
   private String scommissiontransno = "";
   
   private String instructionno=""; //����ָ��� 
   private boolean autocreatebankinstruction = true;
  //��������
   private InutParameterInfo inutParameterInfo = null;
   
 //���к�
	private String spayeebankexchangeno="";
	//CNAPS��
	private String spayeebankcnapsno ="";
	//������
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
