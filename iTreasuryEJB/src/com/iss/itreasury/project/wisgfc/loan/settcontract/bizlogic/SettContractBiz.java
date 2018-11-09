package com.iss.itreasury.project.wisgfc.loan.settcontract.bizlogic;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.iss.itreasury.archivesmanagement.util.SETTConstant;
import com.iss.itreasury.dataentity.UpLoanReturnInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.project.wisgfc.loan.settcontract.dao.SettContractDao;
import com.iss.itreasury.project.wisgfc.loan.settcontract.dataentity.UploadContract;
import com.iss.itreasury.project.wisgfc.loan.settcontract.util.NameRef;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.SessionMng;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;

public class SettContractBiz {
	public Map importExcel(SmartUpload mySmartUpload,
								String uploadPath,
								SessionMng sessionMng) throws Exception{
		
		Vector vector = new Vector();
		Map map = new HashMap();
		Vector returnVector = new Vector();
		String strAdd ="";//ÿ�δ��ϴ��ļ��ж�����һ����Ԫ��
		short index = 0;
		boolean bIsValid = false;
		File myfile = mySmartUpload.getFiles().getFile(0);
		System.out.println(uploadPath);
		System.out.println(uploadPath+myfile.getFileName());
		myfile.saveAs(uploadPath+myfile.getFileName());
		Connection conn = null;
		FileInputStream fis = new  FileInputStream(myfile.getFilePathName().replaceAll("\\\\", "\\\\\\\\"));
		try{
		//Workbook[] wbs = new Workbook[] { new HSSFWorkbook(fis)};
		//for(int i =0; i<wbs.length;i++){
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			if(workbook!=null){
				sheet = workbook.getSheetAt(0);
			}
			if (sheet==null){
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
				returnVector.addElement (upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(true);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("���ܵ���յ�Excel�ļ���");
			}
			if(sheet!=null){
				row = sheet.getRow(2);
			}
			for (int j=2;row!=null;j++,row =sheet.getRow(j)){
				bIsValid=true;
				UploadContract uploadContract= new UploadContract();
				//1.��ͬ��� 2.ҵ�����ͱ��� 3.��λ���  5.ί�е�λ���
				//7.��ʼ���� 8���������� 9.������� 10.�Ƿ��򷽸�Ϣ 11.�򷽸�Ϣ����
				long subTypeId = -1;
				for  (index = 1; index <= 13; index++){
					strAdd = "";
					cell = row.getCell((short)(index-1));
					if (cell!=null)
					{
						//��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							strAdd=cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							strAdd=String.valueOf(cell.getNumericCellValue());
						}
						System.out.println(strAdd);
					}
					if (strAdd==null)
					{
						strAdd="";
					}
					if(index ==1){
						if(strAdd.matches("^\\s*\\w+\\s*$")){
							strAdd=strAdd.replaceAll("\\s*", "");
							uploadContract.setSContractCode(strAdd);
						}else{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("��ͬ��Ų��ܹ�Ϊ��");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index ==2){
						if(strAdd.matches("^\\s*\\w+(\\.[0]+)?\\s*$")){
							subTypeId = NameRef.getLoanSubTypeIdByCode(strAdd.replaceAll("\\.\\d+", ""), sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
							if(subTypeId>0){
								uploadContract.setNTypeID(subTypeId);
							}else{
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setPositionRow(j);
								upLoanReturnInfo.setReason("ҵ�����ͱ������ݿ��в�����");
								returnVector.addElement(upLoanReturnInfo);
							}
						}else{
						UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
						upLoanReturnInfo.setIsOk(true);
						upLoanReturnInfo.setPositionCol(index);
						upLoanReturnInfo.setPositionRow(j);
						upLoanReturnInfo.setReason("ҵ�����ͱ����ʽ���ܹ�Ϊ��");
						returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index == 3){
						if(strAdd.matches("^\\s*\\w(\\w|[-])*(\\.[0]+)?\\s*$")){
							long clientId = NameRef.getClientIdByClientCode(strAdd.replaceAll("\\.\\d+", ""), sessionMng.m_lOfficeID);
							if(clientId>0){
								uploadContract.setNBorrowClientID(clientId);
							}else{
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setPositionRow(j);
								upLoanReturnInfo.setReason("��λ������ݿ��в�����");
								returnVector.addElement(upLoanReturnInfo);
							}
						}else{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("��λ��Ų��ܹ�Ϊ��");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index == 5){
						if(LOANConstant.LoanType.WT == SETTConstant.SettLoanType.getBigType(subTypeId)){
							if(strAdd.matches("^\\s*\\w(\\w|[-])*(\\.[0]+)?\\s*$")){
								long clientId = NameRef.getClientIdByClientCode(strAdd.replaceAll("\\.\\d+", ""), sessionMng.m_lOfficeID);
								if(clientId>0){
									uploadContract.setConsignClientID(clientId);
								}else{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setIsOk(true);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setPositionRow(j);
									upLoanReturnInfo.setReason("ί�е�λ������ݿ��в�����");
									returnVector.addElement(upLoanReturnInfo);
								}
							}else{
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setPositionRow(j);
								upLoanReturnInfo.setReason("ί�е�λ��Ų��ܹ�Ϊ��");
								returnVector.addElement(upLoanReturnInfo);
							}
						}
					}
					if(index == 7){
						if(strAdd.matches("^\\s*((((19|20)\\d{2})-(0?[13-9]|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))\\s*$")){
							uploadContract.setDtStartDate(strAdd);
						}else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("��ʼ���ڸ�ʽ����ȷ");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index == 8){
						if(strAdd.matches("^\\s*((((19|20)\\d{2})-(0?[13-9]|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))\\s*$")){
							uploadContract.setDtEndDate(strAdd);
						}else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("���������ڸ�ʽ����ȷ");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index ==9){
						if(strAdd.matches("^\\s*\\d+(\\.[0]+)?\\s*$")){
							uploadContract.setIntervalNum(Long.valueOf(strAdd.replaceAll("\\.\\d+", "")).longValue());
						}else{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("��ͬ���޸�ʽӦΪ����");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index ==10){
						if(strAdd.matches("^\\s*([1-9]\\d*|0)(\\.(\\d){1,2})?\\s*$")){
							uploadContract.setMLoanAmount(Double.valueOf(strAdd.replaceAll(",","")).longValue());
						}else{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("����ʽ����ȷ");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index == 11){
						if(strAdd.matches("^\\s*([1-9]([0-9]){0,2}|0)(,(\\d){3})*(\\.(\\d){1,2})?\\s*$")){
							uploadContract.setMDiscountRate(Double.valueOf(strAdd.replaceAll(",","")).doubleValue());
						}else{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("���ʸ�ʽ����ȷ");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if(index ==12){
						if(strAdd.matches("^\\s*\\w+(\\.[0]+)?\\s*$")){
							if(LOANConstant.LoanType.TX ==SETTConstant.SettLoanType.getBigType(subTypeId)){
								uploadContract.setIsPurchaserInterest(Long.valueOf(strAdd.replaceAll("\\.\\d+", "")).longValue());
							}
						}
					}
					if(index == 13){
						if(uploadContract.getIsPurchaserInterest()>0){
							if(strAdd.matches("^\\s*\\d{1,2}(\\.\\d+)?\\s*$")){
								uploadContract.setPurchaserInterestRate(Double.valueOf(strAdd.replaceAll(",","")).doubleValue());
							}else{
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setPositionRow(j);
								upLoanReturnInfo.setReason("�򷽸�Ϣ������ʽ����ȷ");
								returnVector.addElement(upLoanReturnInfo);
							}
						}
					}
					
				}
				String batchEntity ="";
				String prefix = DataFormat.formatDate(new Date(), DataFormat.FMT_DATE_YYYYMMDD);
				String num = NameRef.getMaxNumForexportTrustCollection(prefix);
				vector.add(uploadContract);
			//}
		}
		//�жϸ�ʽ�Ƿ���ȷ �����ȷ ��������ݿ����
		//���ݿ����
			
		StringBuffer sb = new StringBuffer();
		if( returnVector.size()==0){
			sb.append("(");
			int length = vector.size();
			SettContractInfo[] infos = new SettContractInfo[length];
			if(length>0){
				for(int i =0;i<length;i++){
					UploadContract uploadContract = (UploadContract) vector.get(i);
					SettContractInfo info = new SettContractInfo();
					info.setBorrowClientID(uploadContract.getNBorrowClientID());
					info.setContractCode(uploadContract.getSContractCode());
					info.setTypeID(SETTConstant.SettLoanType.getBigType(uploadContract.getNTypeID()));
					info.setStartDate(uploadContract.getDtStartDate());
					info.setInterestRate(uploadContract.getMDiscountRate());
					info.setExamineAmount(uploadContract.getMLoanAmount());
					info.setEndDate(uploadContract.getDtEndDate());
					info.setIntervalNum(uploadContract.getIntervalNum());
					info.setSubTypeID(uploadContract.getNTypeID());
					if(uploadContract.getNTypeID() == LOANConstant.LoanType.WT){
						info.setConsignClientID(uploadContract.getConsignClientID());
					}
					if(uploadContract.getNTypeID() == LOANConstant.LoanType.TX){
						info.setIsPurchaserInterest(uploadContract.getIsPurchaserInterest());
						info.setPurchaserInterestRate(uploadContract.getPurchaserInterestRate());
					}
					info.setCurrencyID(sessionMng.m_lCurrencyID);
					info.setOfficeID(sessionMng.m_lOfficeID);
					info.setInputUserID(sessionMng.m_lUserID);
					info.setInputDate(sessionMng.settlemetSystemDate);
					info.setLoanID(uploadContract.getLLoanID());
					info.setStatusID(SETTConstant.SettContractStatus.NOTACTIVE);
					infos[i] = info;
					if(i <length-1){
						sb.append("'");
						sb.append(uploadContract.getSContractCode()+"',");
					}else{
						sb.append("'");
						sb.append(uploadContract.getSContractCode()+"')");
					}
				}
				//�������
				
			}
			//ɾ���Ѿ�����Ĳ���
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			SettContractDao SettContractDao = new SettContractDao(conn);
			SettContractDao.importContractFromExcel(sb.toString(), infos);
			map.put("ContractNos", sb.toString());
		}else{
			map.put("returnVector", returnVector);
		}
		}finally{
			fis.close();
			if(conn!=null){
				conn.close();
			}
		}
		return map;
	}
	
	public Collection findFromExcel(String contractNos,long lofficeId,long inputUserId,long lcurrenyID) throws Exception{
		SettContractDao contractDao = new SettContractDao();
		return contractDao.contractDao(contractNos,lofficeId,inputUserId,lcurrenyID);
	}
}
