package com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.sql.Timestamp;
public   class    CheckInAccountInfo    extends  SECBaseDataEntity
{
	private long id = -1;
	private long NOFFICEID=-1; //������ID
	private long NCURRENCYID=-1;//���ֺ�
	private String SSUBJECTCODE;//��Ŀ����
	private String STRANSNO;//���ױ��
	private long NTRANSACCOUNTID=-1;//�����ʻ�ID
	private long NSUBACCOUNTID=-1;//�������ʻ�ID
	private long NTRANSACTIONTYPEID=-1;//��������
	private long NTRANSDIRECTION=-1;//���׷���
	private double MAMOUNT=0.00;//���׷�����
	private long NOPPACCOUNTID=-1;//�Է��ʻ�ID
	private long NOPPSUBACCOUNTID=-1;//�Է����ʻ�ID
	private Timestamp DTEXECUTE=null;//ִ����
	private Timestamp DTINTERESTSTART=null;//��Ϣ��
	private String SABSTRACT="";//ժҪ
	private long NSTATUSID=-1;//״̬
	private String SBILLNO="";//Ʊ�ݺ�
	private String SBANKCHEQUENO="";//����֧Ʊ��
	private long NBILLTYPEID=-1;//Ʊ������
	private long NGROUP=-1;		//�����
	private long NINTERESTBACKFLAG=-1;//��Ϣ�����־
	private long NSERIALNO=-1;//һ���������к�
	private long NDISCOUNTBILLID=-1;//
	private long BUDGETITEMID=-1;//Ԥ����ĿID  
	private long BUDGETSTATUSID=-1;//�Ƿ���Ԥ���־ 
	private String OPPACCOUNTNO="";//�Է��˻���
	private String OPPACCOUNTNAME="";//�Է��˻�����
	private long AMOUNTTYPE=-1;//�������
	private String dtinputdates="";//ִ������
	private String dtinputdatee="";//ִ������
	private String lbranchid="";//������
	private String AccountKind="";//�˻���
	private String SACCOUNTNO="";//�˻���
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountKind() {
		return AccountKind;
	}

	public void setAccountKind(String accountKind) {
		AccountKind = accountKind;
	}

	public long getAMOUNTTYPE() {
		return AMOUNTTYPE;
	}

	public void setAMOUNTTYPE(long amounttype) {
		AMOUNTTYPE = amounttype;
	}

	public long getBUDGETITEMID() {
		return BUDGETITEMID;
	}

	public void setBUDGETITEMID(long budgetitemid) {
		BUDGETITEMID = budgetitemid;
	}

	public long getBUDGETSTATUSID() {
		return BUDGETSTATUSID;
	}

	public void setBUDGETSTATUSID(long budgetstatusid) {
		BUDGETSTATUSID = budgetstatusid;
	}

	public Timestamp getDTEXECUTE() {
		return DTEXECUTE;
	}

	public void setDTEXECUTE(Timestamp dtexecute) {
		DTEXECUTE = dtexecute;
	}

	public String getDtinputdatee() {
		return dtinputdatee;
	}

	public void setDtinputdatee(String dtinputdatee) {
		this.dtinputdatee = dtinputdatee;
	}

	public String getDtinputdates() {
		return dtinputdates;
	}

	public void setDtinputdates(String dtinputdates) {
		this.dtinputdates = dtinputdates;
	}

	public Timestamp getDTINTERESTSTART() {
		return DTINTERESTSTART;
	}

	public void setDTINTERESTSTART(Timestamp dtintereststart) {
		DTINTERESTSTART = dtintereststart;
	}

	public String getLbranchid() {
		return lbranchid;
	}

	public void setLbranchid(String lbranchid) {
		this.lbranchid = lbranchid;
	}

	public double getMAMOUNT() {
		return MAMOUNT;
	}

	public void setMAMOUNT(double mamount) {
		MAMOUNT = mamount;
	}

	public long getNBILLTYPEID() {
		return NBILLTYPEID;
	}

	public void setNBILLTYPEID(long nbilltypeid) {
		NBILLTYPEID = nbilltypeid;
	}

	public long getNCURRENCYID() {
		return NCURRENCYID;
	}

	public void setNCURRENCYID(long ncurrencyid) {
		NCURRENCYID = ncurrencyid;
	}

	public long getNDISCOUNTBILLID() {
		return NDISCOUNTBILLID;
	}

	public void setNDISCOUNTBILLID(long ndiscountbillid) {
		NDISCOUNTBILLID = ndiscountbillid;
	}

	public long getNGROUP() {
		return NGROUP;
	}

	public void setNGROUP(long ngroup) {
		NGROUP = ngroup;
	}

	public long getNINTERESTBACKFLAG() {
		return NINTERESTBACKFLAG;
	}

	public void setNINTERESTBACKFLAG(long ninterestbackflag) {
		NINTERESTBACKFLAG = ninterestbackflag;
	}

	public long getNOFFICEID() {
		return NOFFICEID;
	}

	public void setNOFFICEID(long nofficeid) {
		NOFFICEID = nofficeid;
	}

	public long getNOPPACCOUNTID() {
		return NOPPACCOUNTID;
	}

	public void setNOPPACCOUNTID(long noppaccountid) {
		NOPPACCOUNTID = noppaccountid;
	}

	public long getNOPPSUBACCOUNTID() {
		return NOPPSUBACCOUNTID;
	}

	public void setNOPPSUBACCOUNTID(long noppsubaccountid) {
		NOPPSUBACCOUNTID = noppsubaccountid;
	}

	public long getNSERIALNO() {
		return NSERIALNO;
	}

	public void setNSERIALNO(long nserialno) {
		NSERIALNO = nserialno;
	}

	public long getNSTATUSID() {
		return NSTATUSID;
	}

	public void setNSTATUSID(long nstatusid) {
		NSTATUSID = nstatusid;
	}

	public long getNSUBACCOUNTID() {
		return NSUBACCOUNTID;
	}

	public void setNSUBACCOUNTID(long nsubaccountid) {
		NSUBACCOUNTID = nsubaccountid;
	}

	public long getNTRANSACCOUNTID() {
		return NTRANSACCOUNTID;
	}

	public void setNTRANSACCOUNTID(long ntransaccountid) {
		NTRANSACCOUNTID = ntransaccountid;
	}

	public long getNTRANSACTIONTYPEID() {
		return NTRANSACTIONTYPEID;
	}

	public void setNTRANSACTIONTYPEID(long ntransactiontypeid) {
		NTRANSACTIONTYPEID = ntransactiontypeid;
	}

	public long getNTRANSDIRECTION() {
		return NTRANSDIRECTION;
	}

	public void setNTRANSDIRECTION(long ntransdirection) {
		NTRANSDIRECTION = ntransdirection;
	}

	public String getOPPACCOUNTNAME() {
		return OPPACCOUNTNAME;
	}

	public void setOPPACCOUNTNAME(String oppaccountname) {
		OPPACCOUNTNAME = oppaccountname;
	}

	public String getOPPACCOUNTNO() {
		return OPPACCOUNTNO;
	}

	public void setOPPACCOUNTNO(String oppaccountno) {
		OPPACCOUNTNO = oppaccountno;
	}

	public String getSABSTRACT() {
		return SABSTRACT;
	}

	public void setSABSTRACT(String sabstract) {
		SABSTRACT = sabstract;
	}

	public String getSBANKCHEQUENO() {
		return SBANKCHEQUENO;
	}

	public void setSBANKCHEQUENO(String sbankchequeno) {
		SBANKCHEQUENO = sbankchequeno;
	}

	public String getSBILLNO() {
		return SBILLNO;
	}

	public void setSBILLNO(String sbillno) {
		SBILLNO = sbillno;
	}

	public String getSSUBJECTCODE() {
		return SSUBJECTCODE;
	}

	public void setSSUBJECTCODE(String ssubjectcode) {
		SSUBJECTCODE = ssubjectcode;
	}

	public String getSTRANSNO() {
		return STRANSNO;
	}

	public void setSTRANSNO(String stransno) {
		STRANSNO = stransno;
	}

	public String getSACCOUNTNO() {
		return SACCOUNTNO;
	}

	public void setSACCOUNTNO(String saccountno) {
		SACCOUNTNO = saccountno;
	}

	
}
