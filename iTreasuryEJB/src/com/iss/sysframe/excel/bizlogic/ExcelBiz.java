package com.iss.sysframe.excel.bizlogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.excel.dataentity.ExcelFlexigridInfo;
import com.iss.sysframe.excel.util.ExcelUtil;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;

/**
 * 
 * @author leiyang3
 *
 */
public class ExcelBiz {
	
	public String buildFlexigridToExcel(ResultPagerInfo resultInfo, ExcelFlexigridInfo excelInfo) throws Exception
	{
		String strFileName = "";
		String strFilePath = "";
		
		try {
			strFilePath = Env.UPLOAD_PATH + "temp/excel/";
			strFilePath = strFilePath + DataFormat.getDateString()+"/"; 
			File folder = new File(strFilePath);   
            if(!folder.exists())
            {
            	folder.mkdirs();   
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("生成文件路径产生异常", e);
		}
		
		try {
			
			String tdWidths[] = excelInfo.getTdWidths().split(",");
			float titleWidths[] = null;
			String titles[] = excelInfo.getTitles().split(",");
			String ids[] = excelInfo.getIds().split(",");
			String[] elTypes=excelInfo.getElTypes().split(",");
			
			//TitleWidths
			titleWidths = new float[tdWidths.length];
			
			for(int i=0; i<tdWidths.length; i++)
			{				
				titleWidths[i] = Float.parseFloat(tdWidths[i]);
			}
			
			List titleList = new ArrayList();
			for(int i=0; i<titles.length; i++)
			{
				if(elTypes.length>=i+1&&"checkbox".equalsIgnoreCase(elTypes[i])){
					continue;
				}
				titleList.add(titles[i]);
			}
			
			ExcelUtil.isHaveTitle = true;//如果为true表示excel中有title
			ExcelUtil.cTitle = titleList;//设置title的值
			ExcelUtil.rowWidth = titleWidths;//设置title的列宽
			
			List rows = resultInfo.getRows();
			List rowInfoList = new ArrayList();
			List rowInfo = null;
			
			//Rows
			if(rows != null && rows.size()>0) {
				ResultPagerRowInfo resultPagerRowInfo = null;
				Iterator it = rows.iterator();
				String _tmp = "";
				while(it.hasNext()) {
					
					resultPagerRowInfo = (ResultPagerRowInfo)it.next();
					rowInfo = new ArrayList();
					int rowNum=titleList.size();
					for(int i=0; i <ids.length; i++){							
						if(elTypes.length>=i+1&&"rownum".equalsIgnoreCase(elTypes[i])){
							rowInfo.add(resultPagerRowInfo.getId());
							rowNum--;
							if(i>0&&"checkbox".equalsIgnoreCase(elTypes[i-1])){
								continue;
							}
							if(resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i]))==null || resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])).toString().equals("&nbsp"))
							{
								rowInfo.add("");
							}else{
								rowInfo.add(resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])).toString());
							}
							rowNum--;
							continue;
						}
						if(elTypes.length>=i+1&&"checkbox".equalsIgnoreCase(elTypes[i])){								
							continue;
						}					
						if(rowNum>0){
							if(resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i]))==null || resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])).toString().equals("&nbsp"))
							{
								rowInfo.add("");
							}else{
								
								_tmp = resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])).toString();
								
								if(_tmp.indexOf("&nbsp;") >= 0){
									_tmp = _tmp.replaceAll("&nbsp;", "");
								}
								
								if(_tmp.indexOf("<B>") >= 0 && _tmp.indexOf("</B>") >= 0){
									_tmp = _tmp.replaceAll("<B>", "");
									_tmp = _tmp.replaceAll("</B>", "");
								}
								
								if(_tmp.indexOf("<b>") >= 0 && _tmp.indexOf("</b>") >= 0){
									_tmp = _tmp.replaceAll("<b>", "");
									_tmp = _tmp.replaceAll("</b>", "");
								}
								
								if(_tmp.indexOf("<font") >= 0 && _tmp.indexOf("</font") >= 0){
									_tmp = _tmp.replaceAll("<font color=red>", "");
									_tmp = _tmp.replaceAll("</font>", "");
								}
								
								if(_tmp.indexOf(",") >= 0 && _tmp.indexOf("#") < 0){
									if("link".equalsIgnoreCase(elTypes[i]))
									{
										_tmp = _tmp.substring(0, _tmp.indexOf(","));
									}
									else if(_tmp.substring(0, _tmp.indexOf(",")).equals(""))
									{
										_tmp = "";
									}
								}else if(_tmp.indexOf("#") > 0){
									_tmp = _tmp.substring(0, _tmp.indexOf("#"));
								}
								
								rowInfo.add(_tmp);
							}
							rowNum--;
						}
						
					}					
					rowInfoList.add(rowInfo);
				}
				
			}
			
			//strFileName = System.currentTimeMillis() + ".xls";
			strFileName = UUID.randomUUID().toString() + ".xls";
			
			//添加合计
			ExcelUtil.isHaveTotal=false;
			ExcelUtil.sTotal="";
			if(resultInfo != null && !"".equals(resultInfo.getUserNoHtmlText()))
			{
				ExcelUtil.isHaveTotal=true;
				ExcelUtil.sTotal=resultInfo.getUserNoHtmlText();
			}
			
			ExcelUtil.createExcel(strFilePath, strFileName, "sheet", rowInfoList);	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return strFilePath+strFileName;
	}
	
}
