/*
 * Created on 2005-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.command;

import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;
import java.lang.ClassNotFoundException;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
//是否用logj
//import org.apache.log4j.Logger;
/**
 * <p>Title:COMMAND封装器</p>
 * <p>Description: 它是一种封装多个方法的COMMOND。用它可减少COMMAND个数</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: iSoftstone</p>
 * @version 1.0
 * @author pclliu
 */

public  class Command extends AbstractCommand {
  //static Log logger = Log.getLogger(WrapperCMD.class.getName());
  protected Class clazz = this.getClass();
  protected HashMap methods = new HashMap(); //存放调用过的方法
  protected String operationName=null;//调用的方法名称
  protected String[] argstype=null;
  protected Object[] args=null;
  protected Class[] types = null;//输入参数的类
  private Object result=null;//执行返回的结果

  public Command() {
  	
  }
  /**
   * 当想通过执行器执行时，可以考虑用此构造函数来创建对象 
   * @param operationName   调用方法的名称
   * @param argstypes          方法对应的参数类型
   * @param argsvalues        对应参数的值对象
   * */
  public Command(final String operationName, final String[] argstypes, Object[] argsvalues){
	 this.operationName=operationName;
     this.argstype=argstypes;
     this.args=argsvalues;
  }

  /**
   * 得到参数类矩阵
   */
  protected void getTypes() throws IException
  {
    try
    {
      int i =0;
      for (; i < argstype.length; i++) {
        types[i] = Class.forName(argstype[i]);
      }
    }catch(ClassNotFoundException e)
    {
      throw new IException (e.getMessage());
    }
  }
  public void execute() throws IException{
    /**@todo Implement this AbstractCommand abstract method*/
    String ERROR="在调用";
    String ERROR1=":found error .";
    try
    {
      preExe();//有效性检查
      Log.print("After 有效性检查");
      Method method = null;
      method = getMethod(operationName);
      Log.print (" 调用前");
       result=method.invoke(this, args);
       Log.print (" 调用后");
    }
    catch (NoSuchMethodException e)
    {
      throw new IException("没找到调用方法"+operationName+"。",e);
    }
    catch (ClassCastException e)
    {
         throw  new IException(ERROR+operationName+ERROR1 ,e);
     }
     catch (IllegalAccessException e)
     {
         throw  new IException(ERROR+operationName+ERROR1  ,e);
     }
     catch (InvocationTargetException e)
     {
         // Rethrow the target exception if possible so that the
         // exception handling machinery can deal with it
         e.printStackTrace();
         Throwable t = e.getTargetException();
         if (t instanceof Exception) {
         	if(t instanceof IException)
         		throw (IException)t;
         	else
             throw  new IException(ERROR+operationName+ERROR1, (Exception) t);
         }
         else
         {
         	e.printStackTrace();
            throw  new IException(ERROR+operationName+ERROR1);
         }
       }
    finally
    {
      types=null;
    }
  }
  //执行前的准备
  protected void preExe() throws IException
  {
    if(operationName==null)
          throw new IException("调用的方法名称不能为空");
    if((argstype!=null && args==null) ||
       (argstype==null && args!=null) ||
       (argstype!=null && args!=null && argstype.length != args.length ))
      throw new IException("参数的个与参数类型的个数一致");
    if(argstype!=null)
    {
      types = new Class[argstype.length];
      int i = 0;
      try
      {
      	
        for (; i < argstype.length; i++)
        {
          if(argstype[i].equalsIgnoreCase("boolean"))
          	types[i]=java.lang.Boolean.TYPE;
          else if(argstype[i].equalsIgnoreCase("char"))
          	types[i]=java.lang.Character.TYPE;
          else if(argstype[i].equalsIgnoreCase("byte"))
          	types[i]=java.lang.Byte.TYPE;
          else if(argstype[i].equalsIgnoreCase("short"))
          	types[i]=java.lang.Short.TYPE;
          else if(argstype[i].equalsIgnoreCase("int"))
          	types[i]=java.lang.Integer.TYPE;
          else if(argstype[i].equalsIgnoreCase("long"))
          	types[i]=java.lang.Long.TYPE;
          else if(argstype[i].equalsIgnoreCase("float"))
          	types[i]=java.lang.Float.TYPE;
          else if(argstype[i].equalsIgnoreCase("double"))
          	types[i]=java.lang.Double.TYPE;
          else if(argstype[i].equalsIgnoreCase("void"))
          	types[i]=java.lang.Void.TYPE;
          else
          	types[i] = Class.forName(argstype[i]);
        }
      }
      catch(ClassNotFoundException e)
      {
        throw new IException("类"+argstype[i]+"没找到",e);
      }
    }
  }

  protected Method getMethod(String name)
       throws NoSuchMethodException {
      System.out.println("Into getMethod");
       synchronized (methods) {
           Method method = (Method) methods.get(name);
           if (method == null) {
               method = clazz.getMethod(name, types);
               methods.put(name, method);
           }
           return (method);
       }

   }

  public Object[] getArgs() {
    return args;
  }
  public String[] getArgstype() {
    return argstype;
  }
  public String getOperationName() {
    return operationName;
  }
  public void setArgs(Object[] args) {
    this.args = args;
  }
  public void setArgstype(String[] argstype) {
    this.argstype = argstype;
  }
  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }
  /**
   * 得到执行方法的返回结果
   * @return  Object 执行方法的返回结果
   */
  public Object getResult() {
    return result;
  }

}
