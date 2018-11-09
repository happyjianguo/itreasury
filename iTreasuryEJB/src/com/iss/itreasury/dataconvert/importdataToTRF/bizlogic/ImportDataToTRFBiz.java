/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.bizlogic;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.usermodel.*;
import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToTRF.bizlogic.datatransfer.IExcelBeanDataTranser;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.PropertiesHandler;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportDataToTRFBiz {
    //excel�����ݿ������ӳ���ļ�·��
    private static final String EXCELDATABASE_MAPPINGFILEURL="exceldatabasenamemapper.properties";
    //excel����ʵ���������ݵ���ʼ����
    private static final long STARTROWNUMBER=4;    
    //���ݿ����������ת��ʵ������ӳ���ļ���·��
    private static final String DATABASEDATATRANSFERCLASSNAME_MAPPINGFILEURL="databasedatatranserclassmapper.properties";
    
    private Log4j log = new Log4j(1, this);
    
    /**
     * ��excel�ļ�������ʱ��
     * 
     * @throws TRF_Exception
     */
    public void importDataToTRF(String fileUrl) {
        Connection con = null;
        FileInputStream fis = null;
        try {
            con = DataBaseUtil.getConnection();
            con.setAutoCommit(false);
            //����FieldGenerator����������һ��������,��Ҫ����һ��֧�������FieldGenerator
            MaxFieldGenerator fieldGenerator=new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            DataTransplantBaseDao dao = new DataTransplantBaseDao();
            dao.setConnection(con);
            dao.setFieldGenerator(fieldGenerator);
            log.info("loading excel file " + fileUrl);
            PropertiesHandler propHandler=new PropertiesHandler();
            Map excelDataBaseMap=propHandler.read(EXCELDATABASE_MAPPINGFILEURL);
            Map dataBaseDataTransferMap=propHandler.read(DATABASEDATATRANSFERCLASSNAME_MAPPINGFILEURL);
            
            fis = new FileInputStream(fileUrl);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            //ѭ��ȡexcel��ÿ��sheet
            for (int index = 0,n=workbook.getNumberOfSheets(); index < n; index++) {
                HSSFSheet sheet = workbook.getSheetAt(index);
                String sheetName = workbook.getSheetName(index);
                log.info("handling sheet " + sheetName);
                String dataBaseTableName = (String) excelDataBaseMap.get(sheetName.substring(0,3));
                if (dataBaseTableName == null) {
                    log.info("can't find mapper for " + sheetName);
                    continue;
                }
                dao.setStrTableName(dataBaseTableName);
                String className = (String)dataBaseDataTransferMap.get(dataBaseTableName);
                if (className == null) {
                    log.info("can't find mapper for " + dataBaseTableName);
                    continue;
                }
                //ͨ�����䶯̬��װʵ����
                IExcelBeanDataTranser iExcelBeanDataTranser = (IExcelBeanDataTranser) Class
                        .forName(className).newInstance();
                log.info(className + " is assembled");
                //ѭ��ÿ��,����bean,�������ݿ�
                for (Iterator it = sheet.rowIterator(); it.hasNext();) {
                    HSSFRow row = (HSSFRow) it.next();
                    //����������
                    if (row.getRowNum() < STARTROWNUMBER - 1) {
                        continue;
                    }
                    Object o = iExcelBeanDataTranser.transExcelRowToBean(row);
                    //������Ч����
                    if(o==null){
                        continue;
                    }
                    dao.add(o,null,false);
                }
            }
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            throw new TRF_Exception("fail to import data", e);
        } finally {
            try {
                if(fis!=null){
                   fis.close();
                }
            } catch (Exception e1) {
                log.info("fail to close file");
            }
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }
}