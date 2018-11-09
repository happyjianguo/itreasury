/*
 * Created on 2006-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.createdatabasestructuremodule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.LinkedList;

import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TxtFileSqlGetter implements SqlGetter {
    //sql文件的url
    private static final String fileUrl=TRF_Constant.INITDATABASESQLFILEURL;

    public Collection getSql() {
        LinkedList result = new LinkedList();
        BufferedReader in=null;
        try {
            in = new BufferedReader(new FileReader(fileUrl));
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            String[] sqls=sb.toString().split(";");
            if(sqls!=null&&sqls.length>0){
                for(int i=0;i<sqls.length;i++){
                    result.add(sqls[i]);
                }
            }
        } catch (Exception e) {
            throw new TRF_Exception("文件操作失败",e);
        }
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}