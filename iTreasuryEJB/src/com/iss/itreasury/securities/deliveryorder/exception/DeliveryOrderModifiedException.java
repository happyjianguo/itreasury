package com.iss.itreasury.securities.deliveryorder.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DeliveryOrderModifiedException extends SecuritiesException {

	public DeliveryOrderModifiedException(String deliveryOrderCode){
		super("Sec_E131",deliveryOrderCode,null);
	}
}