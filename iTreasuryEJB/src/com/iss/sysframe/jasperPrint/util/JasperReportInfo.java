package com.iss.sysframe.jasperPrint.util;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 * 
 * @author leiyang3
 *
 */
public class JasperReportInfo {
	
	public static class ReportType{
		public final static long SQL_REPORT = 1;
		public final static long JAVABEAN_REPORT = 2;
	}
	
	private long reportType = 1;
	private String reportName = null;
	private String[] subReportName = null;
	private String outFilePath="";	
	private Map parameters = null;
	private JRDataSource dataSource = new JREmptyDataSource();
	
	
	public long getReportType() {
		return reportType;
	}
	public void setReportType(long reportType) {
		this.reportType = reportType;
	}

	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	public JRDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String[] getSubReportName() {
		return subReportName;
	}
	public void setSubReportName(String[] subReportName) {
		this.subReportName = subReportName;
	}

	public String getOutFilePath() {
		return outFilePath;
	}

	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}
}
