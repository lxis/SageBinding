package com.sage.core;

import java.lang.Thread.UncaughtExceptionHandler;

public class GlobalUncaughtExceptionHandler {
	public static void Catch() {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				String stackTraceText = GetStackTraceText(ex);
				int i = 0;
				int ii = i;
			}
		});
	}

	public static String GetStackTraceText(Throwable e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		String stackTraceText = "";
		for (StackTraceElement stack : stackTrace) {
			stackTraceText += stack.getClassName() + ":"
					+ stack.getMethodName() + "(" + stack.getLineNumber() + ")"
					+ "\r\n";
		}
		return stackTraceText;
	}

	public static void Uncatch() {
		Thread.setDefaultUncaughtExceptionHandler(null);
	}

	public static void AddUnhandledException(Exception e) {
		e.printStackTrace();

	}
}
