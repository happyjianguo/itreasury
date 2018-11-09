/*
 * Created on 2006-6-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUtil {
    /**
     * 取得一个目录中所有的文件名,不包括目录
     * @param dirUrl
     * @return
     */
    public static List getFileNamesFromDir(String dirUrl){
        File dir=new File(dirUrl);
        File[] files=dir.listFiles();
        List result=new LinkedList();
        if(files==null){
            return result;
        }
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                result.add(files[i].getName());
            }
        }
        return result;
    }

}
