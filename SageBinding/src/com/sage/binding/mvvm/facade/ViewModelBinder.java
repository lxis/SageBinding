package com.sage.binding.mvvm.facade;

import java.lang.reflect.InvocationTargetException;

import com.sage.binding.GlobalUncaughtExceptionHandler;
import com.sage.binding.mvvm.BindingCore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

public class ViewModelBinder
{
	static BindingCore bind = new BindingCore();
	public static void Bind(View view,BaseViewModel viewModel) 
	{
		try
		{
			bind.bindTag(view, viewModel.getClass());
		}
		catch (NoSuchFieldException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (NoSuchMethodException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		try
		{
			bind.bindData(view, viewModel);
		}
		catch (IllegalAccessException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (InvocationTargetException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
	}
	public static void Bind(BaseViewModel viewModel, int layoutId, Activity bindingActivity)
	{				
		View rootView = LayoutInflater.from(bindingActivity).inflate(layoutId, null);
		ViewModelBinder.Bind(rootView , viewModel);
		bindingActivity.setContentView(rootView);				
		
	}
}
