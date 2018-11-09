/*
 * Created on 2006-3-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractImportBiz implements IImportBiz{

    protected Log4j log = new Log4j(1, this);

    /**
     * 供子类使用的从临时表读取数据方法
     * @param tableName
     * @param conditionInfoClass
     * @return 不会返回null
     */
    protected Collection readDataFromTRF(String tableName,Class conditionInfoClass) {
        Object o=null;
        try {
            o = conditionInfoClass.newInstance();
        } catch (Exception e) {
            throw new TRF_Exception("fail to create new instance of "+conditionInfoClass.getName());
        } 
        DataTransplantBaseDao dao = new DataTransplantBaseDao();
        dao.setStrTableName(tableName);
        Collection result = dao.findByConditionOrderById(o,null);
        //避免返回null,可以省去调用它的方法很多工作
        if(result==null){
            result=new ArrayList();
        }
        return result;
    }

}
