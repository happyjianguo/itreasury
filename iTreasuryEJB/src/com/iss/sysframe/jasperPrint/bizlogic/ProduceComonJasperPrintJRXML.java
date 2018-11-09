package com.iss.sysframe.jasperPrint.bizlogic;


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperHelperConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperPageConfigInfo;
import com.iss.sysframe.jasperPrint.element.JasperField;
import com.iss.sysframe.jasperPrint.element.JasperStaticText;
import com.iss.sysframe.jasperPrint.element.JasperTextField;

public class ProduceComonJasperPrintJRXML {

//	public static String produceJasper(JasperHelperConfigInfo jasperConfigInfo)
//			throws Exception {
//
//		String filePath = "";
//		String fileName = "";
//
//		try {
//			
//			filePath = jasperConfigInfo.getOutFilePath();
//			fileName=jasperConfigInfo.getReport().getPageInfo().getReportName()+".jrxml";
//
//			ArrayList titleArrayList = jasperConfigInfo.getTitle().getStaticTexts();			
//			Iterator titles=titleArrayList.iterator();
//			ArrayList title=new ArrayList();
//			while(titles.hasNext()){
//				JasperStaticText staticText= (JasperStaticText) titles.next();
//				JasperElementConfigInfo titleCellInfo = staticText.getElementInfo();
//				staticText.createStaticText(titleCellInfo);
//				title.add(staticText.getStaticText());
//
//			}
//			jasperConfigInfo.getTitle().createTitleBand(jasperConfigInfo.getTitle().getBandInfo());
//			jasperConfigInfo.getTitle().addElementToTitleBand(title);
//			
//			
//			ArrayList footerArrayList = jasperConfigInfo.getColFooter().getStaticTexts();			
//			Iterator footerIt=footerArrayList.iterator();
//			ArrayList footer=new ArrayList();
//			while(footerIt.hasNext()){
//				JasperStaticText staticText= (JasperStaticText) footerIt.next();
//				JasperElementConfigInfo footerCellInfo = staticText.getElementInfo();
//				staticText.createStaticText(footerCellInfo);
//				footer.add(staticText.getStaticText());
//
//			}
//			jasperConfigInfo.getColFooter().createColFooterBand(jasperConfigInfo.getColFooter().getBandInfo());
//			jasperConfigInfo.getColFooter().addElementToColFooterBand(footer);
//			
//			ArrayList colTitleArrayList = jasperConfigInfo.getColHeader().getTitleArrayList();
//			Iterator colTitles=colTitleArrayList.iterator();
//			ArrayList collTitle=new ArrayList();
//
//			while(colTitles.hasNext()){
//				JasperStaticText staticText=new JasperStaticText();
//				JasperElementConfigInfo colCellinfo = (JasperElementConfigInfo) colTitles.next();
//				staticText.createStaticText(colCellinfo);
//				collTitle.add(staticText.getStaticText());
//			}
//			jasperConfigInfo.getColHeader().createColHeaderBand(jasperConfigInfo.getColHeader().getBandInfo());
//			jasperConfigInfo.getColHeader().addElementToColHeaderBand(collTitle);
//			
//			
//			ArrayList rowArrayList = jasperConfigInfo.getDetail().getRowArrayList();
//			Iterator rowIterator = rowArrayList.iterator();
//			ArrayList collRows=new ArrayList();
//			long yFlag=0;
//			while (rowIterator.hasNext()) {
//				String[] colArray = (String[]) rowIterator.next();
//				for (int i = 0; i < colArray.length; i++) {
//					JasperElementConfigInfo edtailCellInfo =(JasperElementConfigInfo)colTitleArrayList.get(i);
//					edtailCellInfo.setElementContext(colArray[i]);
//					edtailCellInfo.setYFlag(yFlag);
//					JasperStaticText staticText=new JasperStaticText();
//					staticText.createStaticText(edtailCellInfo);
//					collRows.add(staticText.getStaticText());
//				}	
//				yFlag=yFlag+30;
//			}
//			jasperConfigInfo.getDetail().createDetailBand(jasperConfigInfo.getDetail().getBandInfo());
//			jasperConfigInfo.getDetail().addElementToDetailBand(collRows);
//
//			
//			StringBuffer txtBuffer=new StringBuffer();
//
//			txtBuffer.append(jasperConfigInfo.getTitle().getTxtTitleBand());
//			txtBuffer.append(jasperConfigInfo.getColHeader().getTxtColHeaderBand());
//			txtBuffer.append(jasperConfigInfo.getDetail().getTxtDetailBand());
//			txtBuffer.append(jasperConfigInfo.getColFooter().getTxtColFooterBand());
//
//			jasperConfigInfo.getReport().createEmptyJasperReport(jasperConfigInfo.getReport().getPageInfo());
//			jasperConfigInfo.getReport().addContextToJasperRepoet(txtBuffer.toString());
//			
//			File jrxmlFile = new File(filePath+fileName);
//        	BufferedWriter writer = new BufferedWriter(new FileWriter(jrxmlFile));
//     	   
//			writer.write(jasperConfigInfo.getReport().getTxtReport());
//			
//			writer.flush();
//			writer.close();
//			writer = null;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			new Exception(e.getMessage(), e);
//		}
//
//		return fileName;
//	}

	
	public static String produceDynamicJasper(JasperHelperConfigInfo jasperConfigInfo)
		throws Exception {
	
	String filePath = "";
	String fileName = "";
	
	ByteArrayInputStream _inStream = null;
	OutputStream _outStream = null;
	byte[] _buffer = null;
	
	try {
		
		filePath = jasperConfigInfo.getOutFilePath();
		fileName=jasperConfigInfo.getReport().getPageInfo().getReportName();
	
		ArrayList fieldBandArrayList = jasperConfigInfo.getFieldBand().getFieldArrayList();		
		if(fieldBandArrayList!=null && fieldBandArrayList.size()>0)
		{
			Iterator fieldIt=fieldBandArrayList.iterator();
			ArrayList fields=new ArrayList();
			while(fieldIt.hasNext()){
				JasperField field= (JasperField) fieldIt.next();
				field.createField(field.getFieldInfo());
				fields.add(field.getFieldText());

			}
			jasperConfigInfo.getFieldBand().addFieldToFieldBand(fields);
		}
		
//		ArrayList titleArrayList = jasperConfigInfo.getTitle().getStaticTexts();			
//		Iterator titles=titleArrayList.iterator();
//		ArrayList title=new ArrayList();
//		while(titles.hasNext()){
//			JasperStaticText staticText= (JasperStaticText) titles.next();
//			JasperElementConfigInfo titleCellInfo = staticText.getElementInfo();
//			staticText.createStaticText(titleCellInfo);
//			title.add(staticText.getStaticText());
//	
//		}
//		jasperConfigInfo.getTitle().createTitleBand(jasperConfigInfo.getTitle().getBandInfo());
//		jasperConfigInfo.getTitle().addElementToTitleBand(title);
		
		ArrayList pageHeaderArrayList = jasperConfigInfo.getPageHeader().getPageHeaderArrayList();			
		Iterator pageHeaderIt=pageHeaderArrayList.iterator();
		ArrayList pageHeader=new ArrayList();
		while(pageHeaderIt.hasNext()){
			JasperStaticText staticText= (JasperStaticText) pageHeaderIt.next();
			JasperElementConfigInfo pageHeaderCellInfo = staticText.getElementInfo();
			staticText.createStaticText(pageHeaderCellInfo);
			pageHeader.add(staticText.getStaticText());
	
		}
		jasperConfigInfo.getPageHeader().createPageHeaderBand(jasperConfigInfo.getPageHeader().getBandInfo());
		jasperConfigInfo.getPageHeader().addElementToPageHeaderBand(pageHeader);
		
		//±íÎ²
//		ArrayList footerArrayList = jasperConfigInfo.getColFooter().getColFooterArrayList();			
//		Iterator footerIt=footerArrayList.iterator();
//		ArrayList footer=new ArrayList();
//		while(footerIt.hasNext()){
//			JasperStaticText staticText= (JasperStaticText) footerIt.next();
//			JasperElementConfigInfo footerCellInfo = staticText.getElementInfo();
//			staticText.createStaticText(footerCellInfo);
//			footer.add(staticText.getStaticText());
//	
//		}
//		jasperConfigInfo.getColFooter().createColFooterBand(jasperConfigInfo.getColFooter().getBandInfo());
//		jasperConfigInfo.getColFooter().addElementToColFooterBand(footer);
		
		ArrayList colTitleArrayList = jasperConfigInfo.getColHeader().getTitleArrayList();
		Iterator colTitles=colTitleArrayList.iterator();
		ArrayList collTitle=new ArrayList();
	
		while(colTitles.hasNext()){
			JasperStaticText staticText=new JasperStaticText();
			JasperElementConfigInfo colCellinfo = (JasperElementConfigInfo) colTitles.next();
			staticText.createStaticText(colCellinfo);
			collTitle.add(staticText.getStaticText());
		}
		jasperConfigInfo.getColHeader().createColHeaderBand(jasperConfigInfo.getColHeader().getBandInfo());
		jasperConfigInfo.getColHeader().addElementToColHeaderBand(collTitle);
		
		//Ò³Î²
		ArrayList pageFooterArrayList = jasperConfigInfo.getPageFooter().getPageFooterArrayList();			
		Iterator pageFooterIt=pageFooterArrayList.iterator();
		ArrayList pageFooter=new ArrayList();
		while(pageFooterIt.hasNext()){
			JasperStaticText staticText= (JasperStaticText) pageFooterIt.next();
			JasperElementConfigInfo pageFooterCellInfo = staticText.getElementInfo();
			staticText.createStaticText(pageFooterCellInfo);
			pageFooter.add(staticText.getStaticText());
	
		}
		jasperConfigInfo.getPageFooter().createPageFooterBand(jasperConfigInfo.getPageFooter().getBandInfo());
		jasperConfigInfo.getPageFooter().addElementToPageFooterBand(pageFooter);
		
//		ArrayList rowArrayList = jasperConfigInfo.getDetail().getRowArrayList();
//		Iterator rowIterator = rowArrayList.iterator();
//		ArrayList collRows=new ArrayList();
//		long yFlag=0;
//		while (rowIterator.hasNext()) {
//			String[] colArray = (String[]) rowIterator.next();
//			for (int i = 0; i < colArray.length; i++) {
//				JasperElementConfigInfo edtailCellInfo =(JasperElementConfigInfo)colTitleArrayList.get(i);
//				edtailCellInfo.setElementContext(colArray[i]);
//				edtailCellInfo.setYFlag(yFlag);
//				JasperStaticText staticText=new JasperStaticText();
//				staticText.createStaticText(edtailCellInfo);
//				collRows.add(staticText.getStaticText());
//			}	
//			yFlag=yFlag+30;
//		}
//		jasperConfigInfo.getDetail().createDetailBand(jasperConfigInfo.getDetail().getBandInfo());
//		jasperConfigInfo.getDetail().addElementToDetailBand(collRows);
	
		ArrayList txtFieldList = jasperConfigInfo.getDetail().getTxtFieldArrayList();
		if(txtFieldList!=null && txtFieldList.size()>0){
			Iterator txtFieldIterator = txtFieldList.iterator();
			ArrayList textFieldList=new ArrayList();
			int i=0;
			while (txtFieldIterator.hasNext()) {
				JasperTextField txtField = (JasperTextField) txtFieldIterator.next();
				JasperElementConfigInfo colsCellInfo =(JasperElementConfigInfo)colTitleArrayList.get(i++);
				colsCellInfo.setElementContext("");
				txtField.createTextField(colsCellInfo, txtField.getFieldInfo());
				textFieldList.add(txtField.getTxtField());
			}
			jasperConfigInfo.getDetail().createDetailBand(jasperConfigInfo.getDetail().getBandInfo());
			jasperConfigInfo.getDetail().addElementToDetailBand(textFieldList);
		}else{
			
			ArrayList txtStaticList = jasperConfigInfo.getDetail().getTxtStaticArrayList();
			Iterator txtStaticIterator = txtStaticList.iterator();
			ArrayList textStaticList=new ArrayList();

			while (txtStaticIterator.hasNext()) {
				JasperStaticText txtStatic = (JasperStaticText) txtStaticIterator.next();
				txtStatic.createStaticText(txtStatic.getElementInfo());
				textStaticList.add(txtStatic.getStaticText());
			}
			jasperConfigInfo.getDetail().createDetailBand(jasperConfigInfo.getDetail().getBandInfo());
			jasperConfigInfo.getDetail().addElementToDetailBand(textStaticList);
		}

		
		
		StringBuffer txtBuffer=new StringBuffer();
		
		txtBuffer.append(jasperConfigInfo.getFieldBand().getTxtFieldBand());
		//txtBuffer.append(jasperConfigInfo.getTitle().getTxtTitleBand());
		txtBuffer.append(jasperConfigInfo.getPageHeader().getTxtPageHeaderBand());
		txtBuffer.append(jasperConfigInfo.getColHeader().getTxtColHeaderBand());
		txtBuffer.append(jasperConfigInfo.getDetail().getTxtDetailBand());
//		txtBuffer.append(jasperConfigInfo.getColFooter().getTxtColFooterBand());
		txtBuffer.append(jasperConfigInfo.getPageFooter().getTxtPageFooterBand());
		
		jasperConfigInfo.getReport().createEmptyJasperReport(jasperConfigInfo.getReport().getPageInfo());
		jasperConfigInfo.getReport().addContextToJasperRepoet(txtBuffer.toString());
		
//		File jrxmlFile = new File(filePath+fileName+".jrxml");
//		BufferedWriter writer = new BufferedWriter(new FileWriter(jrxmlFile));
//		   
//		writer.write(jasperConfigInfo.getReport().getTxtReport());
//		
//		writer.flush();
//		writer.close();
//		writer = null;

		
		_inStream = new ByteArrayInputStream(jasperConfigInfo.getReport().getTxtReport().getBytes("UTF-8"));
		
		_outStream = new FileOutputStream(filePath+fileName + ".jrxml");
		_buffer = new byte[4096];
		
		int ii = 0;
		double size = 0.0;
		while((ii=_inStream.read(_buffer)) != -1){
			_outStream.write(_buffer, 0, ii);
			size = size + ii;
		}
		
		_outStream.flush();
		_outStream.close();
		_outStream = null;
		
	} catch (Exception e) {
		e.printStackTrace();
		new Exception(e.getMessage(), e);
	}
	finally {
		
		try {
			if(_outStream != null)
			{
				_outStream.flush();
				_outStream.close();
				_outStream = null;
			}
			
			if(_inStream != null)
			{
				_inStream.close();
				_inStream = null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	return fileName;
	}
}
