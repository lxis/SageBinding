package com.sage.binding.mvvm.facade;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.sage.binding.GlobalUncaughtExceptionHandler;
import com.sage.binding.R;
import com.sage.binding.mvvm.BindingCore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class BindingAdapter<T extends BaseViewModel> extends BaseAdapter
{
	protected ArrayList<T> data;
	protected Context context;
	protected int layoutId;
	protected Class modelClass;
	protected BindingCore binding;

	public BindingAdapter(ArrayList<T> data, Context context, int layoutId, Class modelClass)
	{
		this.data = data;
		this.context = context;
		this.layoutId = layoutId;
		this.modelClass = modelClass;
		for (T item : data)
			item.setContext(context);
		this.binding = new BindingCore();
	}

	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		try
		{
			return getViewInternal(position, convertView);
		}
		catch (IllegalArgumentException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (IllegalAccessException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (InvocationTargetException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (NoSuchFieldException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		catch (NoSuchMethodException e)
		{
			GlobalUncaughtExceptionHandler.AddUnhandledException(e);
		}
		return null;
	}

	private View getViewInternal(int position, View convertView) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException
	{
		if (data.get(position) == null) return null;
		if (convertView == null)
		{
			convertView = createView(layoutId);
			setDataInternal(position, convertView, layoutId);
		}
		else
		{
			int convertViewPosition = Integer.parseInt(convertView.getTag(R.id.common_adapter_position).toString());// 浠巘ag涓幏鍙栬
			if (convertViewPosition == position)
				return convertView;
			else
				setDataInternal(position, convertView, layoutId);
		}
		return convertView;
	}

	private void setDataInternal(int position, View convertView, int itemLayoutId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		if (layoutId == 0) convertView.setTag(R.id.common_adapter_layout_id, itemLayoutId);
		convertView.setTag(R.id.common_adapter_position, position);
		T item = data.get(position);
		setViewData(convertView, item);
		binding.bindData(convertView, item);
	}

	public void setViewData(View convertView, T t)
	{
	
	}

	private View createView(int itemLayout) throws NoSuchFieldException, NoSuchMethodException
	{
		View control = LayoutInflater.from(context).inflate(itemLayout, null);
		binding.bindTag(control,modelClass);
		return control;
	}
	
	public void bindAdapter(int id)
	{	
		((ListView)((Activity)context).findViewById(id)).setAdapter(this);			
	}

}
