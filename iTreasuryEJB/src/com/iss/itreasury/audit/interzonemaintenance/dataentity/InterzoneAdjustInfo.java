package com.iss.itreasury.audit.interzonemaintenance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class InterzoneAdjustInfo implements Serializable{
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
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getINDEXID() {
		return INDEXID;
	}
	public void setINDEXID(long indexid) {
		INDEXID = indexid;
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
	public long getRISKLEVEL() {
		return RISKLEVEL;
	}
	public void setRISKLEVEL(long risklevel) {
		RISKLEVEL = risklevel;
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
	
}
