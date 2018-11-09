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
//�Ƿ���logj
//import org.apache.log4j.Logger;
/**
 * <p>Title:COMMAND��װ��</p>
 * <p>Description: ����һ�ַ�װ���������COMMOND�������ɼ���COMMAND����</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: iSoftstone</p>
 * @version 1.0
 * @author pclliu
 */

public  class Command extends AbstractCommand {
  //static Log logger = Log.getLogger(WrapperCMD.class.getName());
  protected Class clazz = this.getClass();
  protected HashMap methods = new HashMap(); //��ŵ��ù��ķ���
  protected String operationName=null;//���õķ�������
  protected String[] argstype=null;
  protected Object[] args=null;
  protected Class[] types = null;//�����������
  private Object result=null;//ִ�з��صĽ��

  public Command() {
  	
  }
  /**
   * ����ͨ��ִ����ִ��ʱ�����Կ����ô˹��캯������������ 
   * @param operationName   ���÷���������
   * @param argstypes          ������Ӧ�Ĳ�������
   * @param argsvalues        ��Ӧ������ֵ����
   * */
  public Command(final String operationName, final String[] argstypes, Object[] argsvalues){
	 this.operationName=operationName;
     this.argstype=argstypes;
     this.args=argsvalues;
  }

  /**
   * �õ����������
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
    String ERROR="�ڵ���";
    String ERROR1=":found error .";
    try
    {
      preExe();//��Ч�Լ��
      Log.print("After ��Ч�Լ��");
      Method method = null;
      method = getMethod(operationName);
      Log.print (" ����ǰ");
       result=method.invoke(this, args);
       Log.print (" ���ú�");
    }
    catch (NoSuchMethodException e)
    {
      throw new IException("û�ҵ����÷���"+operationName+"��",e);
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
  //ִ��ǰ��׼��
  protected void preExe() throws IException
  {
    if(operationName==null)
          throw new IException("���õķ������Ʋ���Ϊ��");
    if((argstype!=null && args==null) ||
       (argstype==null && args!=null) ||
       (argstype!=null && args!=null && argstype.length != args.length ))
      throw new IException("�����ĸ���������͵ĸ���һ��");
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
        throw new IException("��"+argstype[i]+"û�ҵ�",e);
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
   * �õ�ִ�з����ķ��ؽ��
   * @return  Object ִ�з����ķ��ؽ��
   */
  public Object getResult() {
    return result;
  }

}
