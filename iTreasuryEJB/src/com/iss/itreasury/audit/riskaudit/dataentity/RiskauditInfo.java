package com.iss.itreasury.audit.riskaudit.dataentity;

import java.sql.Timestamp;

public class RiskauditInfo {
	private long ID = -1;
	private long INDEXID = -1;
	private long RISKLEVEL = -1;
	private Timestamp THEDATE  = null;
	private long NORMALCOND = -1;
	private long NORMALVALUE = -1;
	private long LEVEL1LOW = -1;
	private long LEVEL1UP = -1;
	private long LEVEL2LOW = -1;
	private long LEVEL2UP = -1;
	private long LEVEL3LOW = -1;
	private long LEVEL3UP = -1;
	private long STATUS = -1;
	private String indexName = "";
    private String procedure = "";
    private String scode = "";
    private String sdefine ="";
    private String sfrequency = "";
    private String attribute4 = "";
    private String sort  = "";
    private long sortnum1 = -1;
    private long sortnum2 = -1;
    private double RiskStandard = -1;
    private long Flag = -1;
	
	public double getRiskStandard() {
		return RiskStandard;
	}
	public void setRiskStandard(double riskStandard) {
		RiskStandard = riskStandard;
	}
	public long getSortnum1() {
		return sortnum1;
	}
	public void setSortnum1(long sortnum1) {
		this.sortnum1 = sortnum1;
	}
	public long getSortnum2() {
		return sortnum2;
	}
	public void setSortnum2(long sortnum2) {
		this.sortnum2 = sortnum2;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public long getINDEXID() {
		return INDEXID;
	}
	public void setINDEXID(long indexid) {
		INDEXID = indexid;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public long getLEVEL1LOW() {
		return LEVEL1LOW;
	}
	public void setLEVEL1LOW(long level1low) {
		LEVEL1LOW = level1low;
	}
	public long getLEVEL1UP() {
		return LEVEL1UP;
	}
	public void setLEVEL1UP(long level1up) {
		LEVEL1UP = level1up;
	}
	public long getLEVEL2LOW() {
		return LEVEL2LOW;
	}
	public void setLEVEL2LOW(long level2low) {
		LEVEL2LOW = level2low;
	}
	public long getLEVEL2UP() {
		return LEVEL2UP;
	}
	public void setLEVEL2UP(long level2up) {
		LEVEL2UP = level2up;
	}
	public long getLEVEL3LOW() {
		return LEVEL3LOW;
	}
	public void setLEVEL3LOW(long level3low) {
		LEVEL3LOW = level3low;
	}
	public long getLEVEL3UP() {
		return LEVEL3UP;
	}
	public void setLEVEL3UP(long level3up) {
		LEVEL3UP = level3up;
	}
	public long getNORMALCOND() {
		return NORMALCOND;
	}
	public void setNORMALCOND(long normalcond) {
		NORMALCOND = normalcond;
	}
	public long getNORMALVALUE() {
		return NORMALVALUE;
	}
	public void setNORMALVALUE(long normalvalue) {
		NORMALVALUE = normalvalue;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public long getRISKLEVEL() {
		return RISKLEVEL;
	}
	public void setRISKLEVEL(long risklevel) {
		RISKLEVEL = risklevel;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSdefine() {
		return sdefine;
	}
	public void setSdefine(String sdefine) {
		this.sdefine = sdefine;
	}
	public String getSfrequency() {
		return sfrequency;
	}
	public void setSfrequency(String sfrequency) {
		this.sfrequency = sfrequency;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public long getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(long status) {
		STATUS = status;
	}
	public Timestamp getTHEDATE() {
		return THEDATE;
	}
	public void setTHEDATE(Timestamp thedate) {
		THEDATE = thedate;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getFlag() {
		return Flag;
	}
	public void setFlag(long flag) {
		Flag = flag;
	}

}
