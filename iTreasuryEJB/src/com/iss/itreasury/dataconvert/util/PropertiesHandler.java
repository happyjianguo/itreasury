/*
 * Created on 2006-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PropertiesHandler {

    /**
     * 读取属性文件
     * @return
     */
    public Map read(String fileUrl) {
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileUrl);
            prop.load(fis);
        } catch (Exception e) {
            throw new TRF_Exception("fail to load property files", e);
        } finally {
            try {
                if(fis!=null){
                  fis.close();
                }
            } catch (Exception e1) {
                System.out.println("fail to close file");
            }
        }
        return DataFormat.transferCodeForProperties(prop);
    }
}