package com.iss.itreasury.ebank.magnifier.dataentity;

public class PagerDepictBaseInfo
{
	private String displayName = null;
	private long displayType = -1;
	private boolean isExtension = false;
	
	private String[] extensionName = null;
	private long[] extensionType = null;
	private String extensionString = null;
	
	private Class fnClazz = null;
	private String fnMethodName = null;
	private Class[] fnArgClass = null;

	public void setExtensionMothod(Class clazz, String methodName, Class[] argClass)
	{
		this.fnClazz = clazz;
		this.fnMethodName = methodName;
		this.fnArgClass = argClass;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getDisplayType() {
		return displayType;
	}

	public void setDisplayType(long displayType) {
		this.displayType = displayType;
	}

	public boolean isExtension() {
		return isExtension;
	}

	public void setExtension(boolean isExtension) {
		this.isExtension = isExtension;
	}

	public String[] getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String[] extensionName) {
		this.extensionName = extensionName;
	}

	public long[] getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(long[] extensionType) {
		this.extensionType = extensionType;
	}

	public String getExtensionString() {
		return extensionString;
	}

	public void setExtensionString(String extensionString) {
		this.extensionString = extensionString;
	}

	public Class getFnClazz() {
		return fnClazz;
	}

	public void setFnClazz(Class fnClazz) {
		this.fnClazz = fnClazz;
	}

	public String getFnMethodName() {
		return fnMethodName;
	}

	public void setFnMethodName(String fnMethodName) {
		this.fnMethodName = fnMethodName;
	}

	public Class[] getFnArgClass() {
		return fnArgClass;
	}

	public void setFnArgClass(Class[] fnArgClass) {
		this.fnArgClass = fnArgClass;
	}
	
}
