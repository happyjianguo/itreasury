package com.iss.sysframe.jasperPrint.bizlogic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.jasperPrint.band.JasperColumnFooter;
import com.iss.sysframe.jasperPrint.band.JasperColumnHeader;
import com.iss.sysframe.jasperPrint.band.JasperDetail;
import com.iss.sysframe.jasperPrint.band.JasperEmptyReport;
import com.iss.sysframe.jasperPrint.band.JasperFieldBand;
import com.iss.sysframe.jasperPrint.band.JasperPageFooter;
import com.iss.sysframe.jasperPrint.band.JasperPageHeader;
import com.iss.sysframe.jasperPrint.band.JasperTitle;
import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperFieldInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperFlexigridInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperPageConfigInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperHelperConfigInfo;
import com.iss.sysframe.jasperPrint.util.BoxStyle;
import com.iss.sysframe.jasperPrint.util.CommonJasperDataSource;
import com.iss.sysframe.jasperPrint.util.FontStyle;
import com.iss.sysframe.jasperPrint.element.JasperField;
import com.iss.sysframe.jasperPrint.element.JasperStaticText;
import com.iss.sysframe.jasperPrint.element.JasperTextField;
import com.iss.sysframe.jasperPrint.util.JasperReportInfo;

public class JasperBiz {
	

	public JasperReportInfo buildFlexigridToDynamicJasper(ResultPagerInfo resultInfo, JasperFlexigridInfo japserInfo) throws Exception
	{
		JasperReportInfo jInfo=new JasperReportInfo();
		
		String strFileName = "";
		String strFilePath = "";
		
		JasperHelperConfigInfo jasperConfigInfo = new JasperHelperConfigInfo();
		JasperPageConfigInfo pageInfo=new JasperPageConfigInfo();
		JasperEmptyReport report=new JasperEmptyReport();
		JasperFieldBand fieldBand=new JasperFieldBand();
		JasperPageHeader pageHeader=new JasperPageHeader();
		JasperPageFooter pageFooter=new JasperPageFooter();
		JasperDetail detail=new JasperDetail();
		JasperColumnHeader colHeader=new JasperColumnHeader();
		JasperBandConfigInfo bandConfigInfo=null;
		JasperElementConfigInfo elementInfo=null;
		JasperFieldInfo fieldInfo=null;
		FontStyle fontSeeting=null;
		BoxStyle boxSetting =null;
		ArrayList coll=null;
		ArrayList collField=null;
		JasperStaticText staticText=null;
		JasperTextField fieldText=null;
		JasperField field=null;
		
		CommonJasperDataSource fDataSource=new CommonJasperDataSource();
		try {
			
			strFilePath = Env.UPLOAD_PATH + "temp/jasper/";
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
			
			String tdWidths[] = japserInfo.getTdWidths().split(",");
			long titleWidths[] = null;
			long titleXFlags[] = null;
			String titles[] = japserInfo.getTitles().split(",");
			String ids[] = japserInfo.getIds().split(",");
			String elTypes[]=japserInfo.getElTypes().split(",");
			
			boxSetting=new BoxStyle();
			boxSetting.setPenLineWidth((float)1.0);
			
			ArrayList colHeaderCellInfoList = new ArrayList();
			titleWidths = new long[tdWidths.length];
			titleXFlags = new long[tdWidths.length];
			for(int i=0; i<tdWidths.length; i++)
			{
				titleWidths[i] = Long.parseLong(tdWidths[i]);
			}
			long titleXFlag=0;
			for (int i = 0; i < titles.length; i++) {
				if(elTypes.length>=i+1&&"checkbox".equalsIgnoreCase(elTypes[i])){
					continue;
				}
				//colHeaderCellInfoList.add(new JasperElementConfigInfo(titles[i], titleWidths[i],30,titleXFlag,0,"Center","Middle", new FontStyle(),boxSetting));
				colHeaderCellInfoList.add(new JasperElementConfigInfo(titles[i], titleWidths[i],30,titleXFlag,0,"Center","Middle", new FontStyle(),boxSetting,"RelativeToTallestObject",true));
				titleXFlag=titleXFlag+titleWidths[i];
			}
			colHeader.setTitleArrayList(colHeaderCellInfoList);
			
			bandConfigInfo=new JasperBandConfigInfo();
			bandConfigInfo.setBandHeight(30);
			bandConfigInfo.setSplitType("Stretch");
			colHeader.setBandInfo(bandConfigInfo);
			
			long detailYFlag=0;
			List rows = resultInfo.getRows();
			coll=new ArrayList();
			collField=new ArrayList();
			if(rows != null && rows.size()>0) {
				ResultPagerRowInfo resultPagerRowInfo = null;
				Iterator it = rows.iterator();
				String result[] = null;
				if(it.hasNext()) {
					
					resultPagerRowInfo = (ResultPagerRowInfo)it.next();

					int rowNum=colHeaderCellInfoList.size();
					result = new String[rowNum];
					int idsOffset=0;
					int resOffset=0;
					for(int i=0; i <ids.length; i++){							
						if(elTypes.length>=i+1&&"rownum".equalsIgnoreCase(elTypes[i])){
							//result[i-resOffset] = resultPagerRowInfo.getId();
							rowNum--;
							idsOffset++;
							
							fieldInfo=new JasperFieldInfo();
							fieldInfo.setFieldName("printColMessage_"+(i-resOffset));
							fieldInfo.setFildType("java.lang.String");
							
							field=new JasperField();						
							field.setFieldInfo(fieldInfo);
							collField.add(field);
							
							fieldText=new JasperTextField();
							fieldText.setFieldInfo(fieldInfo);
							coll.add(fieldText);
							
							if(i>0&&"checkbox".equalsIgnoreCase(elTypes[i-1])){
								idsOffset--;
							}
							continue;
						}
						if(elTypes.length>=i+1&&"checkbox".equalsIgnoreCase(elTypes[i])){
							resOffset++;
							continue;
						}					
						if(rowNum>0){							
							//result[i-resOffset] = resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])-idsOffset).toString();								
							rowNum--;
							
							fieldInfo=new JasperFieldInfo();
							fieldInfo.setFieldName("printColMessage_"+(i-resOffset));
							fieldInfo.setFildType("java.lang.String");
							
							field=new JasperField();						
							field.setFieldInfo(fieldInfo);
							collField.add(field);
							
							fieldText=new JasperTextField();
							fieldText.setFieldInfo(fieldInfo);
							coll.add(fieldText);
						}

					}					

				}
				fieldBand.setFieldArrayList(collField);
				detail.setTxtFieldArrayList(coll);
				
			}else{
				coll=new ArrayList();
				elementInfo=new JasperElementConfigInfo();
				fontSeeting=new FontStyle();
				boxSetting=new BoxStyle();
				staticText=new JasperStaticText();
				fontSeeting.setFrontName("宋体");
				fontSeeting.setFrontSize(12);
				//fontSeeting.setIsBold("true");
				boxSetting.setPenLineWidth((float)1.0);
				elementInfo.setFontSetting(fontSeeting);
				elementInfo.setBoxSetting(boxSetting);
				elementInfo.setElementHeight(30);
				elementInfo.setElementWidth(titleXFlag);
				elementInfo.setXFlag(0);
				elementInfo.setYFlag(0);
				elementInfo.setTextAlignment("Center");
				elementInfo.setVerticalAlignment("Middle");
				elementInfo.setElementContext("无      记       录");
				staticText.setElementInfo(elementInfo);
				coll.add(staticText);
				detail.setTxtStaticArrayList(coll);
				
				detailYFlag=detailYFlag+30;
			}

			ArrayList rowInfoList = new ArrayList();
			if(rows != null && rows.size()>0) {
				detailYFlag=0;
				ResultPagerRowInfo resultPagerRowInfo = null;
				Iterator it = rows.iterator();
				String result[] = null;
				String _tmp = null;
				while(it.hasNext()) {
					
					resultPagerRowInfo = (ResultPagerRowInfo)it.next();
					int rowNum=colHeaderCellInfoList.size();
					result = new String[rowNum];
					int idsOffset=0;
					int resOffset=0;
					for(int i=0; i <ids.length && i <= resultPagerRowInfo.getCell().size(); i++){							
						if(elTypes.length>=i+1&&"rownum".equalsIgnoreCase(elTypes[i])){
							result[i-resOffset] = resultPagerRowInfo.getId();
							rowNum--;
							idsOffset++;
							if(i>0&&"checkbox".equalsIgnoreCase(elTypes[i-1])){
								idsOffset--;
							}
							continue;
						}
						if(elTypes.length>=i+1&&"checkbox".equalsIgnoreCase(elTypes[i])){
							resOffset++;
							continue;
						}					
						if(rowNum>0){
							
							if(resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])-idsOffset).toString().equals("&nbsp")){
								result[i-resOffset] ="";
							}else{
								_tmp = resultPagerRowInfo.getCell().get(Integer.parseInt(ids[i])-idsOffset).toString();
								
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
								
								result[i-resOffset] = _tmp;
							}
							rowNum--;
						}
					}					
					
					rowInfoList.add(result);
					detailYFlag=detailYFlag+30;

				}
				//detail.setRowArrayList(rowInfoList);
				fDataSource.setObjectSource(rowInfoList);
				jInfo.setDataSource(fDataSource);
			}else{
				//fDataSource.setObjectSource();
				jInfo.setDataSource(new net.sf.jasperreports.engine.JREmptyDataSource());
			}
			
			JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();
			bandInfo.setBandHeight(30);
			bandInfo.setSplitType("Immediate");
			
			detail.setBandInfo(bandInfo);
			
			bandConfigInfo=new JasperBandConfigInfo();
			coll=new ArrayList();
			
			elementInfo=new JasperElementConfigInfo();
			fontSeeting=new FontStyle();
			staticText=new JasperStaticText();
			fontSeeting.setFrontName("宋体");
			fontSeeting.setFrontSize(20);
			fontSeeting.setIsBold("true");
			elementInfo.setFontSetting(fontSeeting);
			elementInfo.setElementHeight(40);
			elementInfo.setElementWidth(titleXFlag);
			elementInfo.setXFlag(0);
			//elementInfo.setElementWidth(300);
			//elementInfo.setXFlag(((long)(titleXFlag-elementInfo.getElementWidth())/2));
			elementInfo.setYFlag(0);
			elementInfo.setTextAlignment("Center");
			elementInfo.setVerticalAlignment("Middle");
			//elementInfo.setElementContext("查 询 结 果");
			elementInfo.setElementContext((japserInfo.getTableTitle()!=null && japserInfo.getTableTitle().length()>0 )?japserInfo.getTableTitle()+"-查询结果":"查 询 结 果");
			staticText.setElementInfo(elementInfo);
			coll.add(staticText);
			
			elementInfo=new JasperElementConfigInfo();
			fontSeeting=new FontStyle();
			staticText=new JasperStaticText();
			fontSeeting.setFrontName("宋体");
			fontSeeting.setFrontSize(12);
			elementInfo.setFontSetting(fontSeeting);
			elementInfo.setElementHeight(30);
			elementInfo.setElementWidth(150);
			elementInfo.setXFlag(0);
			elementInfo.setYFlag(50);
			elementInfo.setTextAlignment("Left");
			elementInfo.setVerticalAlignment("Middle");
			elementInfo.setElementContext("查询日期："+DataFormat.getDateString());
			staticText.setElementInfo(elementInfo);
			coll.add(staticText);
			
			elementInfo=new JasperElementConfigInfo();
			fontSeeting=new FontStyle();
			staticText=new JasperStaticText();
			fontSeeting.setFrontName("宋体");
			fontSeeting.setFrontSize(12);
			elementInfo.setFontSetting(fontSeeting);
			elementInfo.setElementHeight(30);
			elementInfo.setElementWidth(300);
			elementInfo.setXFlag(titleXFlag-elementInfo.getElementWidth());
			elementInfo.setYFlag(50);
			elementInfo.setTextAlignment("Right");
			elementInfo.setVerticalAlignment("Middle");
			elementInfo.setElementContext("打印人："+ japserInfo.getUsername());			
			staticText.setElementInfo(elementInfo);
			coll.add(staticText);

			
			bandConfigInfo.setBandHeight(80);
			bandConfigInfo.setSplitType("Stretch");
			//title.setBandInfo(bandConfigInfo);
			//title.setStaticTexts(coll);
			pageHeader.setBandInfo(bandConfigInfo);
			pageHeader.setPageHeaderArrayList(coll);
			
//			elementInfo=new JasperElementConfigInfo();
//			fontSeeting=new FontStyle();
//			staticText=new JasperStaticText();
//			coll=new ArrayList();
//			bandConfigInfo=new JasperBandConfigInfo();
//			
//			fontSeeting.setFrontName("宋体");
//			fontSeeting.setFrontSize(12);
//			elementInfo.setFontSetting(fontSeeting);
//			elementInfo.setElementHeight(30);
//			elementInfo.setElementWidth(300);
//			elementInfo.setXFlag(titleXFlag-elementInfo.getElementWidth());
//			elementInfo.setYFlag(0);
//			elementInfo.setTextAlignment("Right");
//			elementInfo.setVerticalAlignment("Middle");
//			elementInfo.setElementContext("打印人："+ japserInfo.getUsername());			
//			staticText.setElementInfo(elementInfo);
//			coll.add(staticText);
			
			coll=new ArrayList();
			bandConfigInfo=new JasperBandConfigInfo();
			
			bandConfigInfo.setBandHeight(30);
			bandConfigInfo.setSplitType("Stretch");
			//colFooter.setBandInfo(bandConfigInfo);
			//colFooter.setColFooterArrayList(coll);
			pageFooter.setBandInfo(bandConfigInfo);
			pageFooter.setPageFooterArrayList(coll);
			
			
			//pageInfo.setReportName("searchResultJasperPrint");
//			pageInfo.setReportName(System.currentTimeMillis()+"");
			pageInfo.setReportName(UUID.randomUUID().toString());
			pageInfo.setReportLeftMargin(japserInfo.getPageLeft());
			pageInfo.setReportRightMargin(japserInfo.getPageRight());
			pageInfo.setReportTopMargin(japserInfo.getPageTop());
			pageInfo.setReportBottomMargin(japserInfo.getPageButtom());
			pageInfo.setReportPageWidth(titleXFlag+japserInfo.getPageLeft()+japserInfo.getPageRight());
			pageInfo.setReportColumnWidth(titleXFlag);
//			pageInfo.setReportPageHeight((detailYFlag>540?540:detailYFlag)
//					//+title.getBandInfo().getBandHeight()
//					+pageHeader.getBandInfo().getBandHeight()
//					//+colFooter.getBandInfo().getBandHeight()
//					+pageFooter.getBandInfo().getBandHeight()
//					+colHeader.getBandInfo().getBandHeight()
//					+japserInfo.getPageButtom()
//					+japserInfo.getPageTop());
			pageInfo.setReportPageHeight(852);
			report.setPageInfo(pageInfo);
			
			jasperConfigInfo.setFieldBand(fieldBand);
			jasperConfigInfo.setReport(report);
			//jasperConfigInfo.setTitle(title);
			jasperConfigInfo.setPageHeader(pageHeader);
			jasperConfigInfo.setColHeader(colHeader);
//			jasperConfigInfo.setColFooter(colFooter);
			jasperConfigInfo.setPageFooter(pageFooter);
			jasperConfigInfo.setDetail(detail);
			jasperConfigInfo.setOutFilePath(strFilePath);

			strFileName = ProduceComonJasperPrintJRXML.produceDynamicJasper(jasperConfigInfo);
			
			Map parameters = new HashMap(); 

			jInfo.setOutFilePath(jasperConfigInfo.getOutFilePath());
			jInfo.setReportName(strFileName);
			jInfo.setParameters(parameters);
			jInfo.setReportType(JasperReportInfo.ReportType.JAVABEAN_REPORT);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return jInfo;
	}
}
