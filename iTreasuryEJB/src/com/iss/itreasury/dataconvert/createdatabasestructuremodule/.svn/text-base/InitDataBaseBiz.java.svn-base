/*
 * Created on 2006-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.createdatabasestructuremodule;

import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.dataconvert.util.TinyJdbcTemplate;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InitDataBaseBiz {
    
    private SqlGetter sqlGetter=new TxtFileSqlGetter();
    
    public void setSqlGetter(SqlGetter sqlGetter) {
        this.sqlGetter = sqlGetter;
    }
    
    /**
     * 初始化数据库
     *
     */
    public void initDataBase(){
        Collection c=sqlGetter.getSql();
        TinyJdbcTemplate tjt=new TinyJdbcTemplate();
        try{
            tjt.newTransaction();
            for(Iterator i=c.iterator();i.hasNext();){
                String sql=(String)i.next();
                tjt.update(sql);
            }
            tjt.commitTransaction();
        }
        catch(Exception e){
            tjt.rollBackTransaction();
            throw new TRF_Exception("初始化数据库失败",e);
        }
        finally{
            tjt.closeTransaction();
        }
    }

}
