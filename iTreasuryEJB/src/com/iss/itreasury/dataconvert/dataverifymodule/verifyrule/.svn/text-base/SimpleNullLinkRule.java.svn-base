/*
 * Created on 2006-5-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.verifyrule;

import java.sql.ResultSet;
import java.util.List;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.RowCallbackHandler;
import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TinyJdbcTemplate;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SimpleNullLinkRule extends AbstractRule {
    private String tableName = "";

    private String fieldName = "";

    public SimpleNullLinkRule(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public boolean verify() {
        try{
        if (cell == null) {
            return true;
        }
        String cellValue = DataFormat.getCellValue(cell);
        String strSql = "select id from " + tableName + " where " + fieldName
                + "='" + cellValue.trim() + "' \n";
        List queryResult=new TinyJdbcTemplate().query(strSql,new RowCallbackHandler(){
            public Object processRow(ResultSet rs)throws Exception{
                return new Long(rs.getLong(1));
            }
        });
        if(queryResult.size()>0){
            return true;
        }
        else{
            getErrorInfo().setErrorTypeId(TRF_Constant.DataConvertErrorType.NULLLINKERR);
            getErrorInfo().setExeclValue(DataFormat.getCellValue(cell));
            getErrorInfo().setExcelColumn(cell.getCellNum());
            return false;
        }
        }catch(Exception e){
            getErrorInfo().setErrorTypeId(TRF_Constant.DataConvertErrorType.NULLLINKERR);
            getErrorInfo().setExeclValue("发生异常，请先通过其它校验再进行本校验");
            getErrorInfo().setExcelColumn(cell.getCellNum());
            return false;
        }
    }
}