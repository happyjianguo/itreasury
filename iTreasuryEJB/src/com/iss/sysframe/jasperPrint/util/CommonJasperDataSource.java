package com.iss.sysframe.jasperPrint.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CommonJasperDataSource implements JRDataSource{

	
	private int index = -1;
	
	/**
	 *
	 */
	private Object[] data =null;
	public void setObjectSource(Collection  coll) throws JRException 
	{
		try{
			this.data=new Object[coll.size()];
			Iterator it=coll.iterator();
			int i=0;

			while (it.hasNext()){
				data[i]=it.next();
				i++;
			}
		}
		catch(Exception ex){
			ex.getStackTrace();
		}
	}

	
	public Object getFieldValue(JRField field) throws JRException 
	{
		Object value = null;
		try{
			//String className="com.iss.draftoperation.draftMode.dataentity.DraftFrontPrintMainInfo";
			//Class aa=Class.forName(className);
			//aa.cast(data[index]);
			
//			DraftFrontPrintMainInfo pInfo=new DraftFrontPrintMainInfo();
//			pInfo = (DraftFrontPrintMainInfo)data[index];
	
		    String getMethodName = null;
		    Method curGetMethod = null;
		    
	   	    //String setMethodName = null;
		    //Method curSetMethod = null;

		    String selectFieldName = field.getName();

			getMethodName = "get" + selectFieldName.substring(0, 1).toUpperCase() + selectFieldName.substring(1);
			//setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	
			//System.out.println(data[index].getClass().getName());
			//System.out.println(String[].class.getName());
			if(data[index].getClass().getName().equals(String[].class.getName())){
				String[] temp = (String[]) data[index];
				long colIndex=Long.parseLong(selectFieldName.split("_")[1]);
				value = temp[(int)colIndex];
			}
			else{
				curGetMethod = data[index].getClass().getMethod(getMethodName, new Class[]{});
				//curSetMethod = this.getClass().getMethod(setMethodName, new Class[]{curField.getType()});
			}
			value = curGetMethod.invoke(data[index], new Object[]{});
		}
		catch(Exception ex){
			ex.getStackTrace();
		}
		
		return value;
	}
	public boolean next() throws JRException {
		index++;
		return (index < data.length);

	}

}
