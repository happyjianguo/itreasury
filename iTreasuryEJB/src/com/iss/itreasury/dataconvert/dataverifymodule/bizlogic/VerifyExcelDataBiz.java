/*
 * Created on 2006-5-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.bizlogic;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.iss.itreasury.dataconvert.dataverifymodule.dao.VerifyExcelDataDao;
import com.iss.itreasury.dataconvert.dataverifymodule.dataentity.ErrorInfo;
import com.iss.itreasury.dataconvert.dataverifymodule.rulescollecter.ICollecter;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.IRule;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.PropertiesHandler;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VerifyExcelDataBiz {
    //校验器的映射文件路径
    private static final String VERIFY_MAPPINGFILEURL = "verifycollectermapper.properties";

    //excel表中实际数据内容的起始行数
    private static final long STARTROWNUMBER = 4;

    private Log4j log = new Log4j(1, this);

    
    public void verifyExcel(String fileUrl) {
        FileInputStream fis = null;
        log.info("loading excel file " + fileUrl);
        try {
            LinkedList errListForAll = new LinkedList();
            PropertiesHandler propHandler = new PropertiesHandler();
            Map verifyMap = propHandler.read(VERIFY_MAPPINGFILEURL);
            fis = new FileInputStream(fileUrl);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            //循环取excel的每张sheet
            for (int index = 0, n = workbook.getNumberOfSheets(); index < n; index++) {
                HSSFSheet sheet = workbook.getSheetAt(index);
                String sheetName = workbook.getSheetName(index);
                log.info("handling sheet " + sheetName);
                String className = (String) verifyMap.get(sheetName.substring(
                        0, 3));
                if (className == null) {
                    log.info("can't find mapper for " + sheetName);
                    continue;
                }
                //通过反射动态组装实现类
                ICollecter iCollecter = (ICollecter) Class.forName(className)
                        .newInstance();
                log.info(className + " is assembled");
                LinkedList errListPerTable = new LinkedList();
                //循环每行,进行校验
                for (Iterator it = sheet.rowIterator(); it.hasNext();) {
                    HSSFRow row = (HSSFRow) it.next();
                    //跳过标题行
                    if (row.getRowNum() < STARTROWNUMBER - 1) {
                        continue;
                    }
                    List ruleListPerRow = iCollecter.collect(row);
                    List errListPerRow = verifyAndAddRowNumPerRow(
                            ruleListPerRow, row.getRowNum());
                    errListPerTable.addAll(errListPerRow);
                }
                addExcelNameToList(errListPerTable, sheetName);
                errListForAll.addAll(errListPerTable);
            }
            saveErrorListToDataBase(errListForAll);
        } catch (Exception e) {
            throw new TRF_Exception("fail to verify data", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e1) {
                log.info("fail to close file");
            }
        }
    }
    
    public Collection queryVerifyErrors(){
        return new VerifyExcelDataDao().queryErrors(false);
    }
    
    public Collection queryAllErrors(){
        return new VerifyExcelDataDao().queryErrors(true);
    }

    private List verifyAndAddRowNumPerRow(List ruleList, long rowNum) {
        List result = new LinkedList();
        for (Iterator i = ruleList.iterator(); i.hasNext();) {
            IRule each = (IRule) i.next();
            if (!each.verify()) {
                each.getErrorInfo().setExcelRow(rowNum);
                result.add(each.getErrorInfo());
            }
        }
        return result;
    }

    private void addExcelNameToList(List list, String excelName) {
        for (Iterator i = list.iterator(); i.hasNext();) {
            ErrorInfo each = (ErrorInfo) i.next();
            each.setExcelName(excelName);
        }
    }

    private void saveErrorListToDataBase(List list) {
        DataTransplantBaseDao dao = new DataTransplantBaseDao();
        dao.setStrTableName("dc_ErrorInfo");
        for (Iterator i = list.iterator(); i.hasNext();) {
            ErrorInfo each = (ErrorInfo) i.next();
            dao.add(each, null, false);
        }
    }

}