package com.iss.itreasury.integratedCredit.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.iss.itreasury.integratedCredit.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.jspsmart.upload.Request;

/**
 * 解析Excel文档，生成FinanceItemDetail的列表，
 * 将发生的错误记录在lstErrorRecord中。
 * 对象需要调用close()函数关闭
 * @author lcliu
 */
public class ImportExcelFileAction
{
	public static final long OTHER_FILE = -1;		// 其他文件
	public static final long DEBT_SHEET = 1; 		// 资产负债表
	public static final long PROFIT_SHEET = 2;		// 利润表
	public static final long CASH_SHEET = 3;		// 现金流量表
	
	private List lstFinanceItemDetail = new ArrayList();
	private UploadFileAction uploadFileAction = null;
	private Request request = null;
	private String strFilePath = "/AfterCredit/excel/";		// 上传文件放在系统临时文件夹下，系统开机自动删除
	private File excelFile = null;
	private Workbook workbook = null;
	private Log4j log = new Log4j(Constant.ModuleType.LOAN);
	
	private boolean errorOccured = false;	// 错误发生标志
	private boolean fatalErrorOccured = false;		// 严重错误发生标志
	private List lstErrorRecord = new ArrayList();		// 错误记录列表
	
	private long financeId = -1;
	private long cycleYear = -1;
	private long cycleMonth = -1;
	private long reportNameId = -1;
	public ImportExcelFileAction(){
		super();
	}
	/**
	 * 以jsp的PageContext初始化ImportExcelFileAction对象
	 * 在初始化中自动上传文件到默认文件夹/temp/excel/
	 * 使用此初始化函数可以调用getRequest()获得com.jspsmart.upload.Request类型的页面request对象
	 * @param context 包含上传文件的页面PageContext对象
	 * @param financeIdName 财务分析主表ID控件名
	 * @param cycleYearName 财务报表年份控件名
	 * @param cycleMonthName 财务报表月份控件名
	 * @param reportNameIdName 财务报表类型控件名
	 * @throws ServletException
	 */
	public ImportExcelFileAction(
			PageContext context,
			String financeIdName,
			String cycleYearName,
			String cycleMonthName,
			String reportNameIdName) throws ServletException
	{
		uploadFileAction = new UploadFileAction(context, Constant.ModuleType.LOAN);
		try
		{
			uploadFileAction.setStrFileExt("xls");		// 限制上传文件为xls文件
			uploadFileAction.uploadTo(strFilePath);		// 上传到临时文件夹
			excelFile = new File(uploadFileAction.getStrFileURL());		// 获得上传的excel文件
			
			/*
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("ISO-8859-1");
			workbook = Workbook.getWorkbook(excelFile);
			*/
			
			try
			{
				workbook = Workbook.getWorkbook(excelFile);		// 获得工作簿
			}
			catch (BiffException e)
			{
				recordUnknownError();
				e.printStackTrace();
			}
			
			request = uploadFileAction.getRequest();
			if (financeIdName != "")
			{
				financeId = Long.parseLong(request.getParameter(financeIdName));
			}
			cycleYear = Long.parseLong(request.getParameter(cycleYearName));
			cycleMonth = Long.parseLong(request.getParameter(cycleMonthName));
			reportNameId = Long.parseLong(request.getParameter(reportNameIdName));
			
		}
		catch (SecurityException e)
		{
			recordFileExtendError();
		}
		catch (Exception e)
		{
			recordUnknownError();
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传贷后调查报告报表
	 * @param context
	 * @param financeIdName
	 * @param cycleYearName
	 * @param cycleMonthName
	 * @param reportNameIdName
	 * @param clientID
	 * @param reportID
	 * @throws ServletException
	 */
	public long ImportExcelFileAction4AfterCredit(
			PageContext context,
			String financeIdName,
			String cycleYearName,
			String cycleMonthName,
			String reportNameIdName,long reportID,long userID) throws ServletException
	{
		long iResult = -1;
		uploadFileAction = new UploadFileAction(context, Constant.ModuleType.LOAN);
		try
		{
			uploadFileAction.setStrFileExt("xls,xlsx");		// 限制上传文件为excel文件
			iResult = uploadFileAction.upload4AfterCredit(strFilePath,reportID,userID);		// 上传到临时文件夹
			excelFile = new File(uploadFileAction.getStrFileURL());		// 获得上传的excel文件
			request = uploadFileAction.getRequest();
			
		}
		catch (SecurityException e)
		{
			recordFileExtendError();
		}
		catch (Exception e)
		{
			recordUnknownError();
			e.printStackTrace();
		}
		return iResult;
	}
	
	
	/**
	 * 以外部Excel文档初始化ImportExcelFileAction对象
	 * 使用此初始化函数不可以调用getRequest()获得com.jspsmart.upload.Request类型的页面request对象
	 * @param excelFile 需要解析的excel文档
	 * @param financeId 财务分析主表ID，若只要验证财务报表的正确性，可为任意值
	 * @param cycleYear 财务分析年份，若只要验证财务报表的正确性，可为任意值
	 * @param cycleMonth 财务分析月份，若只要验证财务报表的正确性，可为任意值
	 * @param reportNameId 财务报表类型：1--资产负债表，2--利润表，3--现金流量表
	 */
	public ImportExcelFileAction(
			File excelFile,
			long financeId,
			long cycleYear,
			long cycleMonth,
			long reportNameId)
	{
		this.excelFile = excelFile;
		this.financeId = financeId;
		this.cycleYear = cycleYear;
		this.cycleMonth = cycleMonth;
		this.reportNameId = reportNameId;

		try
		{
			workbook = Workbook.getWorkbook(excelFile);		// 获得工作簿
		}
		catch (Exception e)
		{
			recordUnknownError();
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 读取第sheetNo页，第rowNo行，从columnNo列开始的三个Excel的单元格，
	 * 如果第一个单元格的内容为strItemName，则将第二和第三个单元格的内容写入到lstFinanceItemDetail中的itemId位置,
	 * 并设置其reprotNameType
	 * @author lcliu
	 * @param sheetNo
	 * @param rowNo
	 * @param columnNo
	 * @param strItemName
	 * @param itemId
	 */
	private void importExcelCell(
			int sheetNo,
			int rowNo,
			int columnNo,
			String strItemName,
			int itemId)
	{
		Sheet sheet = null;
		Cell[] cells = null;
		String itemName = "";
		boolean errorInLineOccured = false;
		
		try
		{
			sheet = workbook.getSheet(sheetNo);	// 获取sheet
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			recordError(ErrorRecord.UNEXISTED_SHEET, sheetNo, rowNo, columnNo);
			return;
		}
		
		try
		{
			cells = sheet.getRow(rowNo);		// 获取cells
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			recordError(ErrorRecord.UNEXISTED_CELL, sheetNo, rowNo, columnNo);
			return;
		}
		
		try
		{
			itemName = cells[columnNo].getContents();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			recordError(ErrorRecord.UNEXISTED_CELL, sheetNo, rowNo, columnNo);
			return;
		}
		
		if (!strItemName.equals(itemName.replaceAll("　", " ").trim()))	// 若第一个单元格与项目名不符
		{
			log.print("===aeolus: ===" + strItemName);
			log.print("===aeolus: ===" + cells[columnNo].getContents().replaceAll("\\s", " ").trim());
			recordError(ErrorRecord.ITEM_NAME_ERROR, sheetNo, rowNo, columnNo);
		}
		else
		{
			LoanFinanceitemdetail info = new LoanFinanceitemdetail();
			info.setItemid(itemId);
			info.setFinanceid(financeId);
			info.setCycleyear(String.valueOf(cycleYear));
			info.setCyclemonth(String.valueOf(cycleMonth));
			info.setReportnametype(reportNameId);
			info.setExplain("");
			info.setBalancestate(1);
			
			try
			{
				String strAmount = cells[columnNo+2].getContents().trim();
				if (strAmount == "")
				{
					strAmount = "0";
				}
				// 解析本期余额
				info.setAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strAmount)));
			}
			catch (NumberFormatException e)
			{
				recordError(ErrorRecord.NUMBER_TRANSLATE_ERROR, sheetNo, rowNo, columnNo + 2);
				errorInLineOccured = true;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				recordError(ErrorRecord.UNEXISTED_CELL, sheetNo, rowNo, columnNo + 2);
				errorInLineOccured = true;
			}
			catch (Exception e)
			{
				recordError(ErrorRecord.UNKNOWN_ERROR, sheetNo, rowNo, columnNo + 2);
				errorInLineOccured = true;
			}
			
			try
			{
				String strScale = cells[columnNo+3].getContents().trim();
				if (strScale == "")
				{
					strScale = "0";
				}
				// 解析上期余额
				info.setScale(Double.parseDouble(DataFormat.reverseFormatAmount(strScale)));
			}
			catch (NumberFormatException e)
			{
				recordError(ErrorRecord.NUMBER_TRANSLATE_ERROR, sheetNo, rowNo, columnNo + 3);
				errorInLineOccured = true;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				recordError(ErrorRecord.UNEXISTED_CELL, sheetNo, rowNo, columnNo + 3);
				errorInLineOccured = true;
			}
			catch (Exception e)
			{
				recordError(ErrorRecord.UNKNOWN_ERROR, sheetNo, rowNo, columnNo + 3);
				errorInLineOccured = true;
			}
			
			if (!errorInLineOccured)
			{
				lstFinanceItemDetail.add(info);
			}
		}
	}
	
	/**
	 * 以固定参数调用importExcelCell()，
	 * 为importExcelSheet1()和importExcelSheet2()简化输入
	 */
	private void _importExcelCell(int rowNo, String strItemName, int itemId)
	{
		importExcelCell(0, rowNo, 0, strItemName, itemId);
	}
	
	/**
	 * 使用报表中的某些数据计算报表中没有的数据，为新数据生成新的LoanFinanceitemdetail节点并插入到lstFinanceItemDetail中
	 * @param targetItemID 需要计算出的数据的ItemID
	 * @param sourceItemIDs 用来计算的元数据的ItemID集合。
	 * @param sourceOperaters 用来计算的元数据的符号集合，true为+号，false为-号。
	 */
	private void compute(int targetItemID, int[] sourceItemIDs, boolean[] sourceOperaters)
	{
		double amount = 0;	// 目标数据的金额
		
		// 计算目标数据金额
		for (int i=0; i<sourceItemIDs.length; i++)	// 遍历所有的元数据ItemID
		{
			Iterator it = lstFinanceItemDetail.iterator();
			while (it.hasNext())		// 遍历已有的所有数据
			{
				LoanFinanceitemdetail info = (LoanFinanceitemdetail)it.next();
				if (info.getItemid() == sourceItemIDs[i])	// 如果该数据是所需要的元数据
				{
					if (sourceOperaters[i])		// 累计元数据的金额到目标数据金额中
					{
						amount += info.getAmount();
					}
					else
					{
						amount -= info.getAmount();
					}
					break;
				}
			}
		}
		
		// 生成新节点，插入到lstFinanceItemDetail中
		LoanFinanceitemdetail info = new LoanFinanceitemdetail();
		info.setItemid(targetItemID);
		info.setFinanceid(financeId);
		info.setCycleyear(String.valueOf(cycleYear));
		info.setCyclemonth(String.valueOf(cycleMonth));
		info.setReportnametype(reportNameId);
		info.setExplain("");
		info.setBalancestate(1);
		info.setAmount(amount);
		lstFinanceItemDetail.add(info);
	}
	
	private void importExcelSheet0()	// 导入资产负债表
	{
		importExcelCell(0,  5, 0, "货币资金", 1);
		importExcelCell(0,  6, 0, "交易性金融资产", 3);
		importExcelCell(0,  7, 0, "短期投资", 150);
		importExcelCell(0,  8, 0, "应收票据", 4);
		importExcelCell(0,  9, 0, "应收账款", 5);
		importExcelCell(0,  10, 0, "预付款项", 6);
		importExcelCell(0, 11, 0, "应收股利", 8);
		importExcelCell(0, 12, 0, "应收利息", 7);
		
		importExcelCell(0, 13, 0, "其他应收款", 9);
		importExcelCell(0, 14, 0, "存货", 11);
		importExcelCell(0, 15, 0, "其中：原材料", 151);
		importExcelCell(0, 16, 0, "库存商品（产成品）", 152);
		importExcelCell(0, 17, 0, "一年内到期的非流动资产", 15);
		importExcelCell(0, 18, 0, "其他流动资产", 16);
		
		importExcelCell(0, 19, 0, "流动资产合计", 17);
		importExcelCell(0, 21, 0, "可供出售金融资产", 18);
		importExcelCell(0, 22, 0, "持有至到期投资", 19);
		importExcelCell(0, 23, 0, "长期债权投资", 153);
		importExcelCell(0, 24, 0, "长期应收款", 20);
		importExcelCell(0, 25, 0, "长期股权投资", 21);
		importExcelCell(0, 26, 0, "股权分置流通权", 154);
		importExcelCell(0, 27, 0, "投资性房地产", 22);
		
		importExcelCell(0, 28, 0, "固定资产原价", 155);
		importExcelCell(0, 29, 0, "减：累计折旧", 156);
		importExcelCell(0, 30, 0, "固定资产净值", 157);
		importExcelCell(0, 31, 0, "减：固定资产减值准备", 158);
		importExcelCell(0, 32, 0, "固定资产净额", 159);
		
		//importExcelCell(0, 23, 0, "固定资产", 23);
		
		importExcelCell(0, 33, 0, "在建工程", 27);
		importExcelCell(0, 34, 0, "工程物资", 28);
		importExcelCell(0, 35, 0, "固定资产清理", 29);
		importExcelCell(0, 36, 0, "生产性生物资产", 30);
		importExcelCell(0, 37, 0, "油气资产", 31);
		importExcelCell(0, 38, 0, "无形资产", 32);
		importExcelCell(0, 39, 0, "其中：土地使用权", 160);
		importExcelCell(0, 40, 0, "开发支出", 33);
		importExcelCell(0, 41, 0, "商誉", 34);
		importExcelCell(0, 42, 0, "合并价差", 161);
		importExcelCell(0, 43, 0, "长期待摊费用（递延资产）", 35);
		importExcelCell(0, 44, 0, "递延所得税资产", 36);
		importExcelCell(0, 45, 0, "递延税款借项", 162);
		importExcelCell(0, 46, 0, "其他非流动资产（其他长期资产）", 37);
		importExcelCell(0, 47, 0, "其中：特准储备物资", 163);
		importExcelCell(0, 48, 0, "非流动资产合计", 38);
		importExcelCell(0, 56, 0, "资  产  总  计", 39);
		
		importExcelCell(0,  5, 4, "短期借款", 40);
		importExcelCell(0,  6, 4, "交易性金融负债", 41);
		importExcelCell(0,  7, 4, "应付权证", 164);
		importExcelCell(0,  8, 4, "应付票据", 42);
		importExcelCell(0,  9, 4, "应付账款", 43);
		importExcelCell(0,  10, 4, "预收款项", 44);
		importExcelCell(0, 11, 4, "应付职工薪酬", 45);
		
		importExcelCell(0, 12, 4, "其中：应付工资", 165);
		importExcelCell(0, 13, 4, "应付福利费", 166);
		importExcelCell(0, 14, 4, "应交税费", 46);
		importExcelCell(0, 15, 4, "其中：应交税金", 167);
		importExcelCell(0, 16, 4, "应付利息", 47);
		importExcelCell(0, 17, 4, "应付股利（应付利润）", 48);
		importExcelCell(0, 18, 4, "其他应付款", 49);
		importExcelCell(0, 19, 4, "一年内到期的非流动负债", 50);
		importExcelCell(0, 20, 4, "其他流动负债", 51);
		importExcelCell(0, 21, 4, "流动负债合计", 52);
		importExcelCell(0, 23, 4, "长期借款", 53);
		importExcelCell(0, 24, 4, "应付债券", 54);
		importExcelCell(0, 25, 4, "长期应付款", 55);
		importExcelCell(0, 26, 4, "专项应付款", 56);
		importExcelCell(0, 27, 4, "预计负债", 57);
		importExcelCell(0, 28, 4, "递延所得税负债", 58);
		importExcelCell(0, 29, 4, "递延税款贷项", 168);
		importExcelCell(0, 30, 4, "其他非流动负债", 59);
		
		/*
		 * 重复项，非流动负债合计 即 长期负债
		 * 在loan_financeitem表中为第60项（非流动负债合计）和第138项（长期负债）
		 * 第60项已经废弃
		 */
		//importExcelCell(0, 26, 3, "非流动负债合计", 60);
		importExcelCell(0, 31, 4, "其中：特准储备基金", 169);
		importExcelCell(0, 32, 4, "非流动负债合计", 138);
		importExcelCell(0, 33, 4, "负 债 合 计", 61);
		//importExcelCell(0, 28, 3, "所有者权益（或股东权益）：", 62);
		importExcelCell(0, 35, 4, "实收资本（股本）", 63);
		importExcelCell(0, 36, 4, "国家资本", 170);
		importExcelCell(0, 37, 4, "集体资本", 171);
		importExcelCell(0, 38, 4, "法人资本", 172);
		importExcelCell(0, 39, 4, "其中：国有法人资本", 173);
		importExcelCell(0, 40, 4, "集体法人资本", 174);
		importExcelCell(0, 41, 4, "个人资本", 175);
		importExcelCell(0, 42, 4, "外商资本", 176);
		importExcelCell(0, 43, 4, "资本公积", 65);
		importExcelCell(0, 44, 4, "减：库存股", 66);
		importExcelCell(0, 45, 4, "盈余公积", 67);
		importExcelCell(0, 46, 4, "一般风险准备", 177);
		importExcelCell(0, 47, 4, "未确认的投资损失（以“-”号填列）", 178);
		
		importExcelCell(0, 48, 4, "未分配利润", 68);
		importExcelCell(0, 49, 4, "其中：现金股利", 179);
		importExcelCell(0, 50, 4, "外币报表折算差额", 180);
		importExcelCell(0, 51, 4, "归属于母公司所有者权益合计", 181);
		importExcelCell(0, 52, 4, "少数股东权益", 182);
		
		importExcelCell(0, 53, 4, "所有者权益合计", 69);
		importExcelCell(0, 54, 4, "减：未处理资产损失", 183);
		importExcelCell(0, 55, 4, "所有者权益合计（剔除未处理资产损失后的金额）", 184);
		importExcelCell(0, 56, 4, "负债和所有者权益总计", 70);
		
		// 计算短期投资
		// 短期投资(2) = 交易性金融资产(3) + 可供出售金融资产(18)
		//compute(2, new int[]{3, 18}, new boolean[]{true, true});
	}
	
	private void importExcelSheet1()	// 导入利润表
	{
		
		importExcelCell(0,4,0, "一、营业收入", 71);
		importExcelCell(0,5,0, "其中：主营业务收入", 185);
		importExcelCell(0,6,0, "其他业务收入", 186);
		importExcelCell(0,7,0, "减：营业成本", 72);
		importExcelCell(0,8,0, "其中：主营业务成本", 187);
		importExcelCell(0,9,0, "其他业务成本", 188);
		importExcelCell(0,10,0, "营业税金及附加", 73);
		importExcelCell(0,11,0, "销售费用", 74);
		importExcelCell(0,12,0, "管理费用", 75);
		importExcelCell(0,13,0, "其中：业务招待费", 189);
		importExcelCell(0,14,0, "研究与开发费", 190);
		importExcelCell(0,15,0, "财务费用", 76);
		importExcelCell(0,16,0, "其中：利息支出", 191);
		importExcelCell(0,17,0, "利息收入", 192);
		importExcelCell(0,18,0, "汇兑净损失（汇兑净收益以“－”号填列）", 193);
		importExcelCell(0,19,0, "资产减值损失", 77);
		importExcelCell(0,20,0, "其他", 194);
		importExcelCell(0,21,0, "加：公允价值变动收益（损失以“－”号填列）", 78);
		importExcelCell(0,22,0, "投资收益（损失以“－”号填列）", 79);
		importExcelCell(0,23,0, "其中：对联营企业和合营企业的投资收益", 80);
		importExcelCell(0,4,4, "二、营业利润（亏损以“－”号填列）", 81);
		importExcelCell(0,5,4, "加：营业外收入", 83);
		importExcelCell(0,6,4, "其中：非流动资产处置利得", 195);
		importExcelCell(0,7,4, "非货币性资产交换利得（非货币性交易收益）", 196);
		importExcelCell(0,8,4, "政府补助（补贴收入）", 197);
		importExcelCell(0,9,4, "债务重组利得", 198);
		importExcelCell(0,10,4, "减：营业外支出", 84);
		importExcelCell(0,11,4, "其中：非流动资产处置损失", 85);

		importExcelCell(0,12,4, "非货币性资产交换损失（非货币性交易损失）", 199);
		importExcelCell(0,13,4, "债务重组损失", 200);
		importExcelCell(0,14,4, "三、利润总额（亏损总额以“－”号填列）", 87);
		importExcelCell(0,15,4, "减：所得税费用", 88);
		importExcelCell(0,16,4, "加：＃* 未确认的投资损失", 201);
		importExcelCell(0,17,4, "四、净利润（净亏损以“－”号填列）", 89);
		importExcelCell(0,18,4, "减：* 少数股东损益", 203);
		//importExcelCell(0,20,4, "六、每股收益：", 90);
		importExcelCell(0,21,4, "基本每股收益", 91);
		importExcelCell(0,22,4, "稀释每股收益", 92);
		

		// 计算其他业务利润
		// 其他业务利润(86) = 其他业务收入(186) - 其他业务成本(188)
		compute(86, new int[]{186, 188}, new boolean[]{true, false});
		// 主营业务利润(202) = 主营业务收入(185) - 主营业务成本(187)
		compute(202, new int[]{185, 187}, new boolean[]{true, false});
	}
	
	private void importExcelSheet2()	// 导入现金流量表
	{
	    importExcelCell(0,5, 0,"销售商品、提供劳务收到的现金", 93);
		importExcelCell(0,6, 0,"收到的税费返还", 94);
		importExcelCell(0,7, 0,"收到其他与经营活动有关的现金", 95);
		importExcelCell(0,8, 0,"经营活动现金流入小计", 96);
		importExcelCell(0,9, 0,"购买商品、接受劳务支付的现金", 97);
		importExcelCell(0,10,0, "支付给职工以及为职工支付的现金", 98);
		importExcelCell(0,11,0, "支付的各项税费", 99);
		importExcelCell(0,12,0, "支付其他与经营活动有关的现金", 100);
		importExcelCell(0,13,0, "经营活动现金流出小计", 101);
		importExcelCell(0,14,0, "经营活动产生的现金流量净额", 102);
		importExcelCell(0,16,0, "收回投资收到的现金", 103);
		importExcelCell(0,17,0, "取得投资收益收到的现金", 104);
		importExcelCell(0,18,0, "处置固定资产、无形资产和其他长期资产收回的现金净额", 105);
		importExcelCell(0,19,0, "处置子公司及其他营业单位收到的现金净额", 106);
		importExcelCell(0,20,0, "收到其他与投资活动有关的现金", 107);
		importExcelCell(0,21,0, "投资活动现金流入小计", 108);
		importExcelCell(0,22,0, "购建固定资产、无形资产和其他长期资产支付的现金", 109);
		importExcelCell(0,23,0, "投资支付的现金", 110);
		
	    importExcelCell(0,4,4, "取得子公司及其他营业单位支付的现金净额", 111);
		importExcelCell(0,5,4, "支付其他与投资活动有关的现金", 112);
		importExcelCell(0,6,4, "投资活动现金流出小计", 113);
		importExcelCell(0,7,4, "投资活动产生的现金流量净额", 114);
		importExcelCell(0,9,4, "吸收投资收到的现金", 115);
		importExcelCell(0,10,4, "其中：子公司吸收少数股东投资收到的现金", 204);
		importExcelCell(0,11,4, "取得借款收到的现金", 116);
		importExcelCell(0,12,4, "收到其他与筹资活动有关的现金", 117);
		importExcelCell(0,13,4, "筹资活动现金流入小计", 118);
		importExcelCell(0,14,4, "偿还债务支付的现金", 119);
		importExcelCell(0,15,4, "分配股利、利润或偿付利息支付的现金", 120);
		importExcelCell(0,16,4, "其中：子公司支付给少数股东的股利、利润", 205);
		importExcelCell(0,17,4, "支付其他与筹资活动有关的现金", 121);
		importExcelCell(0,18,4, "筹资活动现金流出小计", 122);
		importExcelCell(0,19,4, "筹资活动产生的现金流量净额", 123);
		importExcelCell(0,20,4, "四、汇率变动对现金及现金等价物的影响", 124);
		importExcelCell(0,21,4, "五、现金及现金等价物净增加额", 206);
		importExcelCell(0,22,4, "加：期初现金及现金等价物余额", 207);
		importExcelCell(0,23,4, "六、期末现金及现金等价物余额", 208);

		// 计算净现金流量
		// 净现金流量(125) = 经营净现金流量(102) + 投资净现金流量(114) + 筹资净现金流量(123)
		compute(125, new int[]{102, 114, 123}, new boolean[]{true, true, true});
	}
	
	public List importExcelFile()
	{
		if (!fatalErrorOccured)
		{
			switch ((int)reportNameId)
			{
			default:
				break;
			case 1:
				importExcelSheet0();		// 导入资产负债表
				break;
			case 2:
				importExcelSheet1();		// 导入利润表
				break;
			case 3:
				importExcelSheet2();		// 导入现金流量表
				break;
			}
		}
		
		return lstFinanceItemDetail;
	}
	
	public void close()
	{
		if (workbook != null)
		{
			workbook.close();
		}
	}
	
	private void recordUnknownError()
	{
		errorOccured = true;	// 置错误发生标志为真
		fatalErrorOccured =true;	// 置严重错误发生标志为真
		ErrorRecord errorRecord = new ErrorRecord(ErrorRecord.UNKNOWN_ERROR);
		log.print(errorRecord.toString());
		lstErrorRecord.add(errorRecord);	// 记录错误点
	}
	
	private void recordFileExtendError()	//记录文件扩展名错误
	{
		errorOccured = true;	// 置错误发生标志为真
		fatalErrorOccured =true;	// 置严重错误发生标志为真
		ErrorRecord errorRecord = new ErrorRecord(ErrorRecord.FILE_EXTEND_ERROR);
		log.print(errorRecord.toString());
		lstErrorRecord.add(errorRecord);	// 记录错误点
	}
	
	private void recordError(int errCode, int sheetNo, int rowNo, int columnNo)	// 记录其他错误
	{
		errorOccured = true;	// 置错误发生标志为真
		ErrorRecord errorRecord = new ErrorRecord(errCode, sheetNo + 1, rowNo + 1, columnNo + 1);
		log.print(errorRecord.toString());
		lstErrorRecord.add(errorRecord);	// 记录错误点
	}

	public boolean isFatalErrorOccured() {
		return fatalErrorOccured;
	}
	
	public boolean isErrorOccured() {
		return errorOccured;
	}

	public List getLstErrorRecord() {
		return lstErrorRecord;
	}

	public Request getRequest() {
		return request;
	}
	
	/**
	 * 错误记录类
	 * @author lcliu
	 *
	 */
	public class ErrorRecord
	{
		public static final int NO_ERROR = 0;
		public static final int FILE_EXTEND_ERROR = 1;
		public static final int UNEXISTED_SHEET = 2;
		public static final int UNEXISTED_CELL = 3;
		public static final int ITEM_NAME_ERROR = 4;
		public static final int NUMBER_TRANSLATE_ERROR = 5;
		public static final int UNKNOWN_ERROR = 6;
		private static final String STR_NO_ERROR = "无错误";
		private static final String STR_FILE_EXTEND_ERROR = "文件类型错误";
		private static final String STR_UNEXISTED_SHEET = "不存在的工作表";
		private static final String STR_UNEXISTED_CELL = "不存在的单元格";
		private static final String STR_ITEM_NAME_ERROR = "项目名错误";
		private static final String STR_NUMBER_TRANSLATE_ERROR = "数值转换错误";
		private static final String STR_UNKNOWN_ERROR = "未知的错误";
		
		private int errCode = -1;
		private int errSheetNo = -1;
		private int errRowNo = -1;
		private int errColumnNo = -1;
		
		public ErrorRecord(int errCode)
		{
			this.errCode = errCode;
		}
		
		public ErrorRecord(int errCode, int errSheetNo, int errRowNo, int errColumnNo)
		{
			this.errCode = errCode;
			this.errSheetNo = errSheetNo;
			this.errRowNo = errRowNo;
			this.errColumnNo = errColumnNo;
		}
		
		public String getErrorCodeName(int errorCode)
		{
			switch (errorCode)
			{
			default:
				return "未知的错误代码";
			case NO_ERROR:
				return STR_NO_ERROR;
			case FILE_EXTEND_ERROR:
				return STR_FILE_EXTEND_ERROR;
			case UNEXISTED_SHEET:
				return STR_UNEXISTED_SHEET;
			case UNEXISTED_CELL:
				return STR_UNEXISTED_CELL;
			case ITEM_NAME_ERROR:
				return STR_ITEM_NAME_ERROR;
			case NUMBER_TRANSLATE_ERROR:
				return STR_NUMBER_TRANSLATE_ERROR;
			case UNKNOWN_ERROR:
				return STR_UNKNOWN_ERROR;
			}
		}
		
		public String toString()
		{
			if (errCode == NO_ERROR)
			{
				return "无错误！";
			}
			else if (errCode == UNKNOWN_ERROR)
			{
				return "发生未知的错误！";
			}
			else if (errCode == FILE_EXTEND_ERROR)
			{
				return "发生文件类型错误！请检查上传文件！";
			}
			else
			{
				return "在文件的第" + errSheetNo + "工作表第"
							+ errRowNo + "行第"
							+ errColumnNo + "列发生错误："
							+ getErrorCodeName(errCode) + ".";
			}
		}
		
		public int getErrColumnNo() {
			return errColumnNo;
		}
		
		public int getErrRowNo() {
			return errRowNo;
		}
		
		public int getErrSheetNo() {
			return errSheetNo;
		}

		public int getErrCode() {
			return errCode;
		}
	}
}
