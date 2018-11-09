/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.importdata.bizlogic;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.iss.itreasury.util.Env;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * @author yinghu
 * 上传文件的模块
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UploadFileBean {
    private String fileDir="transdata/";//管理上传上来文件的路径
    private String fileUrl = "";//管理上传上来文件的文件全称
    private Request request=null;//管理上传上来的request 
    /**
     * @return Returns the fileUrl.
     */
    public String getFileUrl() {
        return fileUrl;
    }
    
    /**
     * 上传文件
     * @throws Exception
     * @throws ServletException
     * @throws IOException
     * @throws SmartUploadException
     *  
     */
    public void uploadBankDataFile(PageContext pagecontext,
            Timestamp currentTime) throws Exception   {
        SmartUpload mySmartUpload = new SmartUpload();
        com.jspsmart.upload.File myFile = null;
        java.io.File file = null;
       
            mySmartUpload.initialize(pagecontext);
            request=mySmartUpload.getRequest();
            mySmartUpload.upload();
            String strUploadPath = Env.UPLOAD_PATH+fileDir;
            file = new java.io.File(strUploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            mySmartUpload.save(strUploadPath);
            myFile = mySmartUpload.getFiles().getFile(0);
            if (myFile == null || myFile.getSize() == 0) {
            }
            fileUrl = strUploadPath+myFile.getFileName();
    }
    
    /**
     * @return Returns the fileDir.
     */
    public String getFileDir() {
        return fileDir;
    }
    /**
     * @param fileDir The fileDir to set.
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
    
    public static void main(String[] args){
        System.out.println("ok");
    }
    /**
     * @return Returns the request.
     */
    public Request getRequest() {
        return request;
    }
}