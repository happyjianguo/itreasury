/*
 * Created on 2006-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.createdatabasestructuremodule;

import java.util.Collection;

/**
 * @author yinghu
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SqlGetter {
    /**
     * 获取sql语句
     * @return 不会返回null，有可能返回0长度的collection
     */
    public Collection getSql();

}
