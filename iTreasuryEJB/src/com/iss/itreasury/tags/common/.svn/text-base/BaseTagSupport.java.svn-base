/*
 * Created on 2005-8-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.tags.common;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BaseTagSupport extends TagSupport{

    protected String valueScope = "";
    protected long[] value_scope=null;
    
    public void setValueScope(String valueScope) {
      	this.valueScope = valueScope;
      	
      	if (valueScope!=null && valueScope.length()>0)
      	{
      		StringTokenizer st=new StringTokenizer(valueScope," \t\n\r\f|");
      		this.value_scope =new long[st.countTokens()];
      		int i=0;
      		while (st.hasMoreTokens() )
      		{
      			try {
      				this.value_scope[i++]=Long.parseLong( st.nextToken());
      			} catch (NumberFormatException e) {
      				e.printStackTrace();
      			}
      		}
      	}
        }
        
        private boolean findValue(long val)
        {
        	boolean ret=false;
        	if (value_scope==null) ret=true;
        	else{
        		int i=value_scope.length ;
        		for (int j=0;j<i;j++)
        		{
        			if (value_scope[j]==val) ret=true;
        		}
        	}
        	return ret;
        }
        
        public long[] shadowArray(long[] arr,long[] value_scope)
        {
        	long [] ret=null;
        	if (value_scope ==null) ret=arr;
        	else
        	{
        		ArrayList a = new ArrayList();
        		for ( int i=0;i<value_scope.length;i++)
        		{
        			for ( int j=0;j<arr.length ;j++)
        			{
        				if (value_scope[i]==arr[j])
        				{
        					a.add(new Long(value_scope[i]));
        				}
        				continue;
        			}
        		}
        		
        		ret=new long[a.size()];
        		for (int i=0;i<ret.length;i++)
        		{
        			ret[i]=((Long)a.get(i)).longValue() ;
        		}
        	}
        	return ret;
        }

}
