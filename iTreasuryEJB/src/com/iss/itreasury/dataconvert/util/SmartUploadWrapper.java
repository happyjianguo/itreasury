/*
 * Created on 2006-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

/**
 * @author yinghu smartupload的一层简单包装，只提供我们需要的功能 TODO To change the template for
 *         this generated type comment go to Window - Preferences - Java - Code
 *         Style - Code Templates
 */
public class SmartUploadWrapper {
    private String uploadPath = TRF_Constant.UPLOADPATH;

    private SmartUpload smartUpload = new SmartUpload();

    private boolean isFileLoaded = false;

    public SmartUploadWrapper(PageContext pc) {
        try {
            smartUpload.initialize(pc);
        } catch (ServletException e) {
            throw new TRF_Exception("fail to initialize smartupload", e);
        }
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * 取得提交上来的表单
     * @return
     */
    public Request getRequest() {
        return smartUpload.getRequest();
    }

    /**
     * 上传文件并保存在指定的目录中
     */
    public void uploadFile() {
        try {
            smartUpload.upload();
            File file = new java.io.File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            smartUpload.save(uploadPath);
        } catch (Exception e) {
            throw new TRF_Exception("fail to upload file", e);
        }
        isFileLoaded = true;
    }
    
    /**
     * 返回文件绝对路径，必须在经过上传或者下载操作以后才可调用此方法
     * @return
     */
    public String getFileUrl(){
        if(!isFileLoaded){
            throw new IllegalStateException("file is not uploaded yet,you should not invoke this method");
        }
        com.jspsmart.upload.File file=smartUpload.getFiles().getFile(0);
        return uploadPath+file.getFileName();
    }
}