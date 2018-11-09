/*
 * Created on 2006-6-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.dao;

import java.util.Collection;

import com.iss.itreasury.dataconvert.dataverifymodule.dataentity.ErrorInfo;
import com.iss.itreasury.dataconvert.util.TinyJdbcTemplate;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VerifyExcelDataDao {
    
    public Collection queryErrors(boolean isForAll){
        String strSqlForVerify="select * from dc_ErrorInfo where errorTypeId in (1,2,3,4,5) order by id";
        String strSqlForAll="select * from dc_ErrorInfo order by id";
        TinyJdbcTemplate tjt=new TinyJdbcTemplate();
        if(isForAll){
            return tjt.queryAndGetResultByDataTranserUtil(strSqlForAll,ErrorInfo.class);
        }
        else{
            return tjt.queryAndGetResultByDataTranserUtil(strSqlForVerify,ErrorInfo.class);
        }
    }

}
