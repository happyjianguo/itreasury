package com.iss.sysframe.jasperPrint.dataentity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
import com.iss.sysframe.jasperPrint.util.FontStyle;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;
import com.iss.sysframe.jasperPrint.util.BoxStyle;

public class JasperElementConfigInfo extends BaseDataEntity{

	private long xFlag=0;
	private long yFlag=0;
	private long elementWidth=0;
	private long elementHeight=0;
	private String textAlignment="";
	private String verticalAlignment="";
	
	private String elementContext="";

	FontStyle fontSetting=null;
	
	BoxStyle boxSetting =null;
	
	private String stretchType = "";
	
	private boolean isStretchWithOverflow = true ;
	
	public JasperElementConfigInfo()
	{
		super();
	}
	public JasperElementConfigInfo(String elementContext)
	{
		this.elementContext = elementContext;
	}
	
	public JasperElementConfigInfo(String elementContext, long elementWidth, FontStyle fontSetting)
	{
		this.elementContext = elementContext;
		this.elementWidth = elementWidth;
		this.fontSetting = fontSetting;
	}
	
	public JasperElementConfigInfo(String elementContext, long elementWidth,long elementHeight,long xFlag,long yFlag, FontStyle fontSetting)
	{
		this.elementContext = elementContext;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.xFlag = xFlag;
		this.yFlag = yFlag;
		this.fontSetting = fontSetting;
	}
	public JasperElementConfigInfo(String elementContext, long elementWidth,long elementHeight,long xFlag,long yFlag, FontStyle fontSetting,BoxStyle boxSetting)
	{
		this.elementContext = elementContext;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.xFlag = xFlag;
		this.yFlag = yFlag;
		this.fontSetting = fontSetting;
		this.boxSetting = boxSetting;
	}
	
	public JasperElementConfigInfo(String elementContext, long elementWidth,long elementHeight,long xFlag,long yFlag,String textAlignment,String verticalAlignment, FontStyle fontSetting,BoxStyle boxSetting)
	{
		this.elementContext = elementContext;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.xFlag = xFlag;
		this.yFlag = yFlag;
		this.textAlignment = textAlignment;
		this.verticalAlignment = verticalAlignment;
		this.fontSetting = fontSetting;
		this.boxSetting = boxSetting;
	}
	
	public JasperElementConfigInfo(String elementContext, long elementWidth,long elementHeight,long xFlag,long yFlag,String textAlignment,String verticalAlignment, FontStyle fontSetting,BoxStyle boxSetting,String stretchType,boolean isStretchWithOverflow)
	{
		this.elementContext = elementContext;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.xFlag = xFlag;
		this.yFlag = yFlag;
		this.textAlignment = textAlignment;
		this.verticalAlignment = verticalAlignment;
		this.fontSetting = fontSetting;
		this.boxSetting = boxSetting;
		this.stretchType = stretchType;
		this.isStretchWithOverflow = isStretchWithOverflow;
	}
	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setId(long arg0) {
		// TODO Auto-generated method stub
		
	}

	public long getXFlag() {
		return xFlag;
	}

	public void setXFlag(long flag) {
		xFlag = flag;
	}

	public long getYFlag() {
		return yFlag;
	}

	public void setYFlag(long flag) {
		yFlag = flag;
	}

	public long getElementWidth() {
		return elementWidth;
	}

	public void setElementWidth(long elementWidth) {
		this.elementWidth = elementWidth;
	}

	public long getElementHeight() {
		return elementHeight;
	}

	public void setElementHeight(long elementHeight) {
		this.elementHeight = elementHeight;
	}

	
	public String getElementContext() {
		return elementContext;
	}

	public void setElementContext(String elementContext) {
		this.elementContext = elementContext;
	}


	public FontStyle getFontSetting() {
		return fontSetting;
	}

	public void setFontSetting(FontStyle fontSetting) {
		this.fontSetting = fontSetting;
	}

	public BoxStyle getBoxSetting() {
		return boxSetting;
	}
	public void setBoxSetting(BoxStyle boxSetting) {
		this.boxSetting = boxSetting;
	}
	
	public String getTextAlignment() {
		return textAlignment;
	}
	public void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}
	public String getVerticalAlignment() {
		return verticalAlignment;
	}
	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	
	public String getStretchType() {
		return stretchType;
	}
	public void setStretchType(String stretchType) {
		this.stretchType = stretchType;
	}
	public boolean getIsStretchWithOverflow() {
		return isStretchWithOverflow;
	}
	public void setIsStretchWithOverflow(boolean isStretchWithOverflow) {
		this.isStretchWithOverflow = isStretchWithOverflow;
	}
	
	public String jasperPrintElementConfigSetting(String fileContext) 
	{
	String newFileContext=fileContext;
	try{
			
			Field[] fields = this.getClass().getDeclaredFields(); 
			Field curField = null;
			String fieldName = null;
			
		    String getMethodName = null;
		    Method curGetMethod = null;
		    Object objValue = null;
			for(int i=0; i<fields.length; i++){
				curField = fields[i];
				fieldName = curField.getName();
				if(!fieldName.equals("fontSeeting")){
					getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					curGetMethod = this.getClass().getMethod(getMethodName, new Class[]{});
					objValue = curGetMethod.invoke(this, new Object[]{});
					
					newFileContext=JasperPrintUtil.replaceSomeOldWithNew("#"+fieldName ,objValue.toString(),newFileContext);
				}
			}
		
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		return newFileContext;
		
	}

}
