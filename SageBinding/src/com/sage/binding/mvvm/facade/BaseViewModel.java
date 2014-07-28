package com.sage.binding.mvvm.facade;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.sage.binding.GlobalUncaughtExceptionHandler;
import com.sage.binding.mvvm.model.PropertyChangedHandler;


import android.content.Context;

public class BaseViewModel
{
	public Context context;

	public void setContext(Context context)
	{
		this.context = context;
	}

	public HashMap<String, ArrayList<PropertyChangedHandler>> Handlers = new HashMap<String, ArrayList<PropertyChangedHandler>>();
	
	public void AddHandler(String PropertyName,Method method,Field Field,android.view.View View)
	{
		PropertyChangedHandler handler = new PropertyChangedHandler();
		handler.Method = method;
		handler.Field = Field;		
		handler.View = View;
		handler.PropertyName = PropertyName;					
		if(Handlers.containsKey(PropertyName))
		{
			Handlers.get(PropertyName).add(handler);
		}
		else
		{
			ArrayList<PropertyChangedHandler> handlers = new ArrayList<PropertyChangedHandler>();
			handlers.add(handler);
			Handlers.put(PropertyName, handlers);
		}
			
	}

	public void NotifyPropertyChanged(String property)
	{
		ArrayList<PropertyChangedHandler> handlers = Handlers.get(property);
		if(handlers == null)
			return;
		for (PropertyChangedHandler handler : handlers)
			try
			{
				handler.Handle(this);
			}
			catch (IllegalAccessException e)
			{
				GlobalUncaughtExceptionHandler.AddUnhandledException(e);
			}
			catch (IllegalArgumentException e)
			{
				GlobalUncaughtExceptionHandler.AddUnhandledException(e);
			}
			catch (InvocationTargetException e)
			{
				GlobalUncaughtExceptionHandler.AddUnhandledException(e);
			}

	}
}
