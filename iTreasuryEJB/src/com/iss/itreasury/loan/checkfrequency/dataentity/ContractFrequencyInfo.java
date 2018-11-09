package com.iss.itreasury.loan.checkfrequency.dataentity;
import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.util.*;
public class ContractFrequencyInfo extends ITreasuryBaseDataEntity implements java.io.Serializable
{
	
	
	private long id = -1;
    private long nloanid = -1;
    private String scontractcode ="";
    private long isextend = -1;
    private Timestamp dtactive = null;
    private long ntypeid = -1;
    private String subTypeName = "";
    //private long nriskLevel = -1;//五级分类又名风险状态
    private long ncurrencyid = -1;
    private long nofficeid = -1;
    private String sapplycode ="";
    private long nconsignclientid = -1;
    private long nborrowclientid = -1;
    private double mloanamount = 0;
    private String sloanreason ="";
    private String sloanpurpose ="";
    private String sother ="";
    private long niscircle = -1;
    private long nissalebuy = -1;
    private long nistechnical = -1;
    private long ninputuserid = -1;
    private Timestamp dtinputdate = null;
    private long niscredit = -1;
    private long nisassure = -1;
    private long nisimpawn = -1;
    private long nispledge = -1;
    private long ninteresttypeid = -1;
    private double mexamineamount = 0;
    private long nintervalnum = -1;
    private long nbankinterestid = -1;
    private long nstatusid = -1;
    private long nnextcheckuserid = -1;
    private double mchargerate = 0;
    private Timestamp dtstartdate = null;
    private Timestamp dtenddate = null;
    private long iscanmodify = -1;
    private long nchargeratetypeid = -1;
    private String sclientinfo ="";
    private double mselfamount = 0;
    private long nrisklevel = -1;
    private long ntypeid1 = -1;
    private long ntypeid2 = -1;
    private long ntypeid3 = -1;
    private long nbankacceptpo = -1;
    private long nbizacceptpo = -1;
    private double mcheckamount = 0;
    private double mdiscountrate = 0;
    private Timestamp dtdiscountdate = null;
    private double minterestrate = 0;
    private double madjustrate = 0;
    private double lastattornmentamount = 0;
    private long nnextchecklevel = -1;
    private double mstaidadjustrate = 0;
    private long ninorout = -1;
    private long ndiscounttypeid = -1;
    private long nrepurchasetypeid = -1;
    private double mexamineselfamount = 0;
    private long discountclientid = -1;
    private double purchaserinterestrate = 0;
    private long islowlevel = -1;
    private String discountclientname ="";
    private long ispurchaserinterest = -1;
    private double assurechargerate = 0;
    private long assurechargetypeid = -1;
    private String beneficiary ="";
    private long assuretypeid1 = -1;
    private long assuretypeid2 = -1;
    private long isrecognizance = -1;
    private String projectinfo ="";
    private long isrepurchase = -1;
    private long sellclientid = -1;
    private double selfrate = 0;
    private double sellcontractamount = 0;
    private long ntypeid4 = -1;
    private long nliborrateid = -1;
    private long nsubtypeid = -1;
    private long ncreditcheckreportid = -1;//贷款检查报告ID
    private long checkfrequency = -1;//贷后检查频率
    private String sname="";
    
    
    //add by llliu 2008-7-22
    private String clientcode = "";
    public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
  
    
    
    private String checkreportcode = "";
    private long checkreportid =-1;
    private long advice = -1;


	public double getAssurechargerate() {
		return assurechargerate;
	}

	public void setAssurechargerate(double assurechargerate) {
		this.assurechargerate = assurechargerate;
	}

	public long getAssurechargetypeid() {
		return assurechargetypeid;
	}

	public void setAssurechargetypeid(long assurechargetypeid) {
		this.assurechargetypeid = assurechargetypeid;
	}

	public long getAssuretypeid1() {
		return assuretypeid1;
	}

	public void setAssuretypeid1(long assuretypeid1) {
		this.assuretypeid1 = assuretypeid1;
	}

	public long getAssuretypeid2() {
		return assuretypeid2;
	}

	public void setAssuretypeid2(long assuretypeid2) {
		this.assuretypeid2 = assuretypeid2;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public long getCheckfrequency() {
		return checkfrequency;
	}

	public void setCheckfrequency(long checkfrequency) {
		this.checkfrequency = checkfrequency;
	}

	public long getDiscountclientid() {
		return discountclientid;
	}

	public void setDiscountclientid(long discountclientid) {
		this.discountclientid = discountclientid;
	}

	public String getDiscountclientname() {
		return discountclientname;
	}

	public void setDiscountclientname(String discountclientname) {
		this.discountclientname = discountclientname;
	}

	public Timestamp getDtactive() {
		return dtactive;
	}

	public void setDtactive(Timestamp dtactive) {
		this.dtactive = dtactive;
	}

	public Timestamp getDtdiscountdate() {
		return dtdiscountdate;
	}

	public void setDtdiscountdate(Timestamp dtdiscountdate) {
		this.dtdiscountdate = dtdiscountdate;
	}

	public Timestamp getDtenddate() {
		return dtenddate;
	}

	public void setDtenddate(Timestamp dtenddate) {
		this.dtenddate = dtenddate;
	}

	public Timestamp getDtinputdate() {
		return dtinputdate;
	}

	public void setDtinputdate(Timestamp dtinputdate) {
		this.dtinputdate = dtinputdate;
	}

	public Timestamp getDtstartdate() {
		return dtstartdate;
	}

	public void setDtstartdate(Timestamp dtstartdate) {
		this.dtstartdate = dtstartdate;
	}

	public long getIscanmodify() {
		return iscanmodify;
	}

	public void setIscanmodify(long iscanmodify) {
		this.iscanmodify = iscanmodify;
	}

	public long getIsextend() {
		return isextend;
	}

	public void setIsextend(long isextend) {
		this.isextend = isextend;
	}

	public long getIslowlevel() {
		return islowlevel;
	}

	public void setIslowlevel(long islowlevel) {
		this.islowlevel = islowlevel;
	}

	public long getIspurchaserinterest() {
		return ispurchaserinterest;
	}

	public void setIspurchaserinterest(long ispurchaserinterest) {
		this.ispurchaserinterest = ispurchaserinterest;
	}

	public long getIsrecognizance() {
		return isrecognizance;
	}

	public void setIsrecognizance(long isrecognizance) {
		this.isrecognizance = isrecognizance;
	}

	public long getIsrepurchase() {
		return isrepurchase;
	}

	public void setIsrepurchase(long isrepurchase) {
		this.isrepurchase = isrepurchase;
	}

	public double getLastattornmentamount() {
		return lastattornmentamount;
	}

	public void setLastattornmentamount(double lastattornmentamount) {
		this.lastattornmentamount = lastattornmentamount;
	}

	public double getMadjustrate() {
		return madjustrate;
	}

	public void setMadjustrate(double madjustrate) {
		this.madjustrate = madjustrate;
	}

	public double getMchargerate() {
		return mchargerate;
	}

	public void setMchargerate(double mchargerate) {
		this.mchargerate = mchargerate;
	}

	public double getMcheckamount() {
		return mcheckamount;
	}

	public void setMcheckamount(double mcheckamount) {
		this.mcheckamount = mcheckamount;
	}

	public double getMdiscountrate() {
		return mdiscountrate;
	}

	public void setMdiscountrate(double mdiscountrate) {
		this.mdiscountrate = mdiscountrate;
	}

	public double getMexamineamount() {
		return mexamineamount;
	}

	public void setMexamineamount(double mexamineamount) {
		this.mexamineamount = mexamineamount;
	}

	public double getMexamineselfamount() {
		return mexamineselfamount;
	}

	public void setMexamineselfamount(double mexamineselfamount) {
		this.mexamineselfamount = mexamineselfamount;
	}

	public double getMinterestrate() {
		return minterestrate;
	}

	public void setMinterestrate(double minterestrate) {
		this.minterestrate = minterestrate;
	}

	public double getMloanamount() {
		return mloanamount;
	}

	public void setMloanamount(double mloanamount) {
		this.mloanamount = mloanamount;
	}

	public double getMselfamount() {
		return mselfamount;
	}

	public void setMselfamount(double mselfamount) {
		this.mselfamount = mselfamount;
	}

	public double getMstaidadjustrate() {
		return mstaidadjustrate;
	}

	public void setMstaidadjustrate(double mstaidadjustrate) {
		this.mstaidadjustrate = mstaidadjustrate;
	}

	public long getNbankacceptpo() {
		return nbankacceptpo;
	}

	public void setNbankacceptpo(long nbankacceptpo) {
		this.nbankacceptpo = nbankacceptpo;
	}

	public long getNbankinterestid() {
		return nbankinterestid;
	}

	public void setNbankinterestid(long nbankinterestid) {
		this.nbankinterestid = nbankinterestid;
	}

	public long getNbizacceptpo() {
		return nbizacceptpo;
	}

	public void setNbizacceptpo(long nbizacceptpo) {
		this.nbizacceptpo = nbizacceptpo;
	}

	public long getNborrowclientid() {
		return nborrowclientid;
	}

	public void setNborrowclientid(long nborrowclientid) {
		this.nborrowclientid = nborrowclientid;
	}

	public long getNchargeratetypeid() {
		return nchargeratetypeid;
	}

	public void setNchargeratetypeid(long nchargeratetypeid) {
		this.nchargeratetypeid = nchargeratetypeid;
	}

	public long getNconsignclientid() {
		return nconsignclientid;
	}

	public void setNconsignclientid(long nconsignclientid) {
		this.nconsignclientid = nconsignclientid;
	}

	public long getNcreditcheckreportid() {
		return ncreditcheckreportid;
	}

	public void setNcreditcheckreportid(long ncreditcheckreportid) {
		this.ncreditcheckreportid = ncreditcheckreportid;
	}

	public long getNcurrencyid() {
		return ncurrencyid;
	}

	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
	}

	public long getNdiscounttypeid() {
		return ndiscounttypeid;
	}

	public void setNdiscounttypeid(long ndiscounttypeid) {
		this.ndiscounttypeid = ndiscounttypeid;
	}

	public long getNinorout() {
		return ninorout;
	}

	public void setNinorout(long ninorout) {
		this.ninorout = ninorout;
	}

	public long getNinputuserid() {
		return ninputuserid;
	}

	public void setNinputuserid(long ninputuserid) {
		this.ninputuserid = ninputuserid;
	}

	public long getNinteresttypeid() {
		return ninteresttypeid;
	}

	public void setNinteresttypeid(long ninteresttypeid) {
		this.ninteresttypeid = ninteresttypeid;
	}

	public long getNintervalnum() {
		return nintervalnum;
	}

	public void setNintervalnum(long nintervalnum) {
		this.nintervalnum = nintervalnum;
	}

	public long getNisassure() {
		return nisassure;
	}

	public void setNisassure(long nisassure) {
		this.nisassure = nisassure;
	}

	public long getNiscircle() {
		return niscircle;
	}

	public void setNiscircle(long niscircle) {
		this.niscircle = niscircle;
	}

	public long getNiscredit() {
		return niscredit;
	}

	public void setNiscredit(long niscredit) {
		this.niscredit = niscredit;
	}

	public long getNisimpawn() {
		return nisimpawn;
	}

	public void setNisimpawn(long nisimpawn) {
		this.nisimpawn = nisimpawn;
	}

	public long getNispledge() {
		return nispledge;
	}

	public void setNispledge(long nispledge) {
		this.nispledge = nispledge;
	}

	public long getNissalebuy() {
		return nissalebuy;
	}

	public void setNissalebuy(long nissalebuy) {
		this.nissalebuy = nissalebuy;
	}

	public long getNistechnical() {
		return nistechnical;
	}

	public void setNistechnical(long nistechnical) {
		this.nistechnical = nistechnical;
	}

	public long getNliborrateid() {
		return nliborrateid;
	}

	public void setNliborrateid(long nliborrateid) {
		this.nliborrateid = nliborrateid;
	}

	public long getNloanid() {
		return nloanid;
	}

	public void setNloanid(long nloanid) {
		this.nloanid = nloanid;
	}

	public long getNnextchecklevel() {
		return nnextchecklevel;
	}

	public void setNnextchecklevel(long nnextchecklevel) {
		this.nnextchecklevel = nnextchecklevel;
	}

	public long getNnextcheckuserid() {
		return nnextcheckuserid;
	}

	public void setNnextcheckuserid(long nnextcheckuserid) {
		this.nnextcheckuserid = nnextcheckuserid;
	}

	public long getNofficeid() {
		return nofficeid;
	}

	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}

	public long getNrepurchasetypeid() {
		return nrepurchasetypeid;
	}

	public void setNrepurchasetypeid(long nrepurchasetypeid) {
		this.nrepurchasetypeid = nrepurchasetypeid;
	}

	public long getNrisklevel() {
		return nrisklevel;
	}

	public void setNrisklevel(long nrisklevel) {
		this.nrisklevel = nrisklevel;
	}

	public long getNstatusid() {
		return nstatusid;
	}

	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}

	public long getNsubtypeid() {
		return nsubtypeid;
	}

	public void setNsubtypeid(long nsubtypeid) {
		this.nsubtypeid = nsubtypeid;
	}

	public long getNtypeid() {
		return ntypeid;
	}

	public void setNtypeid(long ntypeid) {
		this.ntypeid = ntypeid;
	}

	public long getNtypeid1() {
		return ntypeid1;
	}

	public void setNtypeid1(long ntypeid1) {
		this.ntypeid1 = ntypeid1;
	}

	public long getNtypeid2() {
		return ntypeid2;
	}

	public void setNtypeid2(long ntypeid2) {
		this.ntypeid2 = ntypeid2;
	}

	public long getNtypeid3() {
		return ntypeid3;
	}

	public void setNtypeid3(long ntypeid3) {
		this.ntypeid3 = ntypeid3;
	}

	public long getNtypeid4() {
		return ntypeid4;
	}

	public void setNtypeid4(long ntypeid4) {
		this.ntypeid4 = ntypeid4;
	}

	public String getProjectinfo() {
		return projectinfo;
	}

	public void setProjectinfo(String projectinfo) {
		this.projectinfo = projectinfo;
	}

	public double getPurchaserinterestrate() {
		return purchaserinterestrate;
	}

	public void setPurchaserinterestrate(double purchaserinterestrate) {
		this.purchaserinterestrate = purchaserinterestrate;
	}

	public String getSapplycode() {
		return sapplycode;
	}

	public void setSapplycode(String sapplycode) {
		this.sapplycode = sapplycode;
	}

	public String getSclientinfo() {
		return sclientinfo;
	}

	public void setSclientinfo(String sclientinfo) {
		this.sclientinfo = sclientinfo;
	}

	public String getScontractcode() {
		return scontractcode;
	}

	public void setScontractcode(String scontractcode) {
		this.scontractcode = scontractcode;
	}

	public double getSelfrate() {
		return selfrate;
	}

	public void setSelfrate(double selfrate) {
		this.selfrate = selfrate;
	}

	public long getSellclientid() {
		return sellclientid;
	}

	public void setSellclientid(long sellclientid) {
		this.sellclientid = sellclientid;
	}

	public double getSellcontractamount() {
		return sellcontractamount;
	}

	public void setSellcontractamount(double sellcontractamount) {
		this.sellcontractamount = sellcontractamount;
	}

	public String getSloanpurpose() {
		return sloanpurpose;
	}

	public void setSloanpurpose(String sloanpurpose) {
		this.sloanpurpose = sloanpurpose;
	}

	public String getSloanreason() {
		return sloanreason;
	}

	public void setSloanreason(String sloanreason) {
		this.sloanreason = sloanreason;
	}

	public String getSother() {
		return sother;
	}

	public void setSother(String sother) {
		this.sother = sother;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCheckreportcode() {
		return checkreportcode;
	}

	public void setCheckreportcode(String checkreportcode) {
		this.checkreportcode = checkreportcode;
	}

	public long getAdvice() {
		return advice;
	}

	public void setAdvice(long advice) {
		this.advice = advice;
	}

	public long getCheckreportid() {
		return checkreportid;
	}

	public void setCheckreportid(long checkreportid) {
		this.checkreportid = checkreportid;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	
	
	
	
	
	
	
	
}
