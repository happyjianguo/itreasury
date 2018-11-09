/*
 * Created on 2006-5-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.sql.ResultSet;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RowCallbackHandler {
    
    Object processRow(ResultSet rs) throws Exception;

}
