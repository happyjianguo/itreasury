/*
 * Created on 2005-6-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.lang.reflect.Field;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultConstantManager extends AbstractConstantManager{
  
	public long[] getFieldsByClassName(String name,long officeID,long currencyID)
	{
		return getAllFields(name);
	}
}
