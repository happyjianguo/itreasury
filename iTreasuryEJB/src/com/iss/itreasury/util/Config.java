/*
 * Created on 2004-7-23
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.iss.itreasury.configtool.configmanage.bizlogic.ConfigManager;
/**
 * @author qqgd
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Config implements ConfigConstant
{
    private static Config       m_instance                      = new Config ( ) ;
	// 对应于属性文件的文件对象变量
	private static File         m_file                          = null ;
	// 属性文件的最后修改日期
	private static long         m_lastModifiedTime              = 0 ;
	// 属性文件所对应的属性对象变量
	private static Properties   m_props                         = null ;
	
	private static HashMap      configItems                     = null ;
	
    private Config()
    {
    }
    private static void loadFromFile()
    {
        try
        {
        	ConfigManager cm = ConfigManager.getInstance();
        	
        	configItems = cm.getAllItem();

        } catch (Exception e)
        {
            e.printStackTrace ( ) ;
        }
    }
    
    public static Config getInstance ( )
    {
        return m_instance ;
    }    
    
    
    public static String getConfigItem ( String name,String defVal )
    {
    	String val = null ;
        try
        {
        	ConfigManager cm = ConfigManager.getInstance();
        	configItems = cm.getAllItem();
        	
            val = (String)configItems.get(name);
            if(val==null)
            {
            	val = defVal;
            }
        } catch (Exception e)
        {
        	e.printStackTrace();
            System.out.println ( "读取 Config item '" + name + "' 错误." ) ;
        }
        return val ;
    }  
    /**
     * Retrieve a system string property.
     * Returns a null if the property is not defined.
     *
     * @param name The name of the property to retrieve.
     * @return The string value of the named property.
     */

    public static String getProperty( String name )
    {
        String result =getConfigItem( name, null );
        return result;
    }

    /**
     * Retrieve a system string property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default string value.
     * @return The string value of the named property.
     */

    public static String getProperty( String name, String defval )
    {
        String result =  getConfigItem( name, defval );
        return result;
    }

    /**
     * Retrieve a system integer property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default integer value.
     * @return The integer value of the named property.
     */

    public static int getInteger( String name, int defval )
    {
        int result = defval;

        String val = getConfigItem( name, null );

        if ( val != null )
        {
            try { result = Integer.parseInt( val ); }
                catch ( NumberFormatException ex )
                    { result = defval; }
        }

        return result;
     }

    /**
     * Retrieve a system long property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default integer value.
     * @return The integer value of the named property.
     */

    public static long getLong( String name, long defval )
    {
        long result = defval;

        String val = getConfigItem( name, null );

        if ( val != null )
        {
            try { result = Long.parseLong( val ); }
                catch ( NumberFormatException ex )
                    { result = defval; }
        }

        return result;
    }

    /**
     * Retrieve a system float property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default float value.
     * @return The float value of the named property.
     */

    public static float getFloat( String name, float defval )
    {
        float result = defval;

        String val = getConfigItem( name, null );

        if ( val != null )
        {
            try { result = Float.valueOf( val ).floatValue(); }
                catch ( NumberFormatException ex )
                    { result = defval; }
        }

        return result;
     }

    /**
     * Retrieve a system double property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default double value.
     * @return The double value of the named property.
     */

    public static double getDouble( String name, double defval )
    {
        double result = defval;

        String val =getConfigItem( name, null );

        if ( val != null )
        {
            try { result = Double.valueOf( val ).doubleValue(); }
                catch ( NumberFormatException ex )
                    { result = defval; }
        }
        return result;
     }

    /**
     * Retrieve a system boolean property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default boolean value.
     * @return The boolean value of the named property.
     */

    public static boolean getBoolean( String name, boolean defval )
    {
        boolean result = defval;

        String val = getConfigItem( name, null );

        if ( val != null )
        {
            if ( val.equalsIgnoreCase( "T" )
                    || val.equalsIgnoreCase( "TRUE" )
                    || val.equalsIgnoreCase( "Y" )
                    || val.equalsIgnoreCase( "YES" )
                    || val.equalsIgnoreCase( "1" ) )
                result = true;
            else if ( val.equalsIgnoreCase( "F" )
                    || val.equalsIgnoreCase( "FALSE" )
                    || val.equalsIgnoreCase( "N" )
                    || val.equalsIgnoreCase( "NO" )
                    || val.equalsIgnoreCase( "0" ) )
                result = false;
        }

        return result;
    }    
    /**
     * Retrieve a system boolean property.
     * Returns a provided default value if the property
     * is not defined.
     *
     * @param name The name of the property to retrieve.
     * @param defval A default boolean value.
     * @return The boolean value of the named property.
     */

    public static ArrayList getArray( String name,ArrayList defval )
    {
    	ArrayList result = defval;

        String val = getConfigItem( name, null );
    	//String val=name;
        if ( val != null )
        {
        	result=new ArrayList();
        	try {
				StringTokenizer st=new StringTokenizer(val,"|");
				while (st.hasMoreTokens()) {
					result.add(st.nextToken() );
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				result=defval;
				e.printStackTrace();
			}        	
        }

        return result;
    }        
	public static void main(String[] args)
	{
		int rate = Config.getInteger(ConfigConstant.GLOBAL_ACCOUNTNO_FIELD,1);
		String tag = Config.getProperty(ConfigConstant.GLOBAL_ACCOUNTNO_TAG,"");
		System.out.println(rate);
		System.out.println(tag);
		String strAccountNo = "01-01-0001-1";
		String[] accountNo = strAccountNo.split(tag);
		String[] strTempCtl = new String[accountNo.length];
		for (int i=0; i<accountNo.length; i++) {
			strTempCtl[i]="=====" + "Ctrl"+(i+1);
		}
		System.out.println(strTempCtl[0]);
		System.out.println(strTempCtl[1]);
		System.out.println(strTempCtl[2]);
		System.out.println(strTempCtl[3]);
	}
 
}
