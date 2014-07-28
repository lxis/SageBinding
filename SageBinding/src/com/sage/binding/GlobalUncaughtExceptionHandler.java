package com.sage.binding;

import java.lang.Thread.UncaughtExceptionHandler;

//这个类是加上了全�?异常处理，其他都不说，至少在调试上很省时间�?�可以在调试时加，完成后去掉�?
public class GlobalUncaughtExceptionHandler {
	public static void Catch()
	{
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {							
				String stackTraceText = GetStackTraceText(ex);
				int i = 0;			
				int ii = i;
			}
		});
	}
	
	public static String GetStackTraceText(Throwable e)
	{
		StackTraceElement[] stackTrace = e.getStackTrace();
		String stackTraceText = "";
		for(StackTraceElement stack : stackTrace)
		{
			stackTraceText += stack.getClassName()+":"+ stack.getMethodName() +"("+ stack.getLineNumber()+")"  +"\r\n" ;				
		}
		return stackTraceText;
	}
	
	public static void Uncatch()
	{
		Thread.setDefaultUncaughtExceptionHandler(null);
	}	
	
	public static void AddUnhandledException(Exception e)
	{
		e.printStackTrace();
		
	}
}
