package com.iss.itreasury.securities.setting.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;

public class SettingException extends SecuritiesException {
	public SettingException(String sCode) {
		super("Sec_E170", sCode, null);
	}
}