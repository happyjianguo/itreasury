/*
 * Created on 2006-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB;

import java.util.Map;
import com.iss.itreasury.dataconvert.importdataToDB.bizlogic.IImportBiz;
import com.iss.itreasury.dataconvert.util.PropertiesHandler;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportBizInvoker {
    
    private Log4j log = new Log4j(1, this);
    private static final String WORKINGFORMIMPORTBIZ_MAPPINGFILEURL="workformimportbizmapper.properties";
    
    /**
     * 动态调用传入参数表所指向的导入流程
     * @param tablesToImport
     */
    public void invoke(String[] tablesToImport){
        PropertiesHandler propHandler=new PropertiesHandler();
        Map map=propHandler.read(WORKINGFORMIMPORTBIZ_MAPPINGFILEURL);
        for(int i=0;i<tablesToImport.length;i++){
            try{
            String className=(String)map.get(tablesToImport[i]);
            if(className==null){
                log.info("can not find mapper for "+tablesToImport[i]);
                continue;
            }
            log.info("importing "+tablesToImport[i]);
            IImportBiz iBiz=(IImportBiz)Class.forName(className).newInstance();
            iBiz.importData();
            }
            catch(Exception e){
                throw new TRF_Exception("error occurs when handling "+tablesToImport[i],e);
            }
        }
    }
}
