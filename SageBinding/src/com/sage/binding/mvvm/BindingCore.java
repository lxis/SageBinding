package com.sage.binding.mvvm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sage.binding.mvvm.facade.BaseViewModel;
import com.sage.binding.mvvm.model.TagBinding;
import com.sage.binding.mvvm.model.TagBindingAnalysisResult;
import com.sage.binding.mvvm.model.TagBindingItem;

import android.view.View;

public class BindingCore {
	public void bindTag(View control, Class modelClass)
			throws NoSuchFieldException, NoSuchMethodException {
		ArrayList<View> controls = VisualHelper.getAllChildrenControls(control);
		for (View controlItem : controls) {
			Object tag = controlItem.getTag();
			if (!checkOriginalTag(tag))
				continue;
			bindTagSingleControl(controlItem, tag.toString(), modelClass);
		}
	}

	private boolean checkOriginalTag(Object tag)// 鏄惁鏈夋晥鐨勬湭瑙ｆ�?�杩囩殑Tag
	{
		return tag != null && !(tag instanceof TagBinding);
	}

	public void bindTagSingleControl(View controlItem, Class modelClass)
			throws NoSuchFieldException, NoSuchMethodException {
		bindTagSingleControl(controlItem, controlItem.getTag().toString(),
				modelClass);
	}

	private void bindTagSingleControl(View controlItem, String tag,
			Class modelClass) throws NoSuchFieldException,
			NoSuchMethodException {
		TagBinding tagBinding = new TagBinding();
		for (TagBindingAnalysisResult tagInfo : new BindingAnalyst()
				.loadBindingParamText(tag))
			tagBinding.Items.add(loadTagBindingItem(controlItem, tagInfo,
					modelClass));
		controlItem.setTag(tagBinding);
	}

	private TagBindingItem loadTagBindingItem(View controlItem,
			TagBindingAnalysisResult tagInfo, Class modelClass)
			throws NoSuchFieldException, NoSuchMethodException {
		TagBindingItem bindingItem = new TagBindingItem();
		bindingItem.PropertyName = tagInfo.FieldName;

		Class currentClass = modelClass;
		for (String fieldNameString : tagInfo.FieldNameStrings) {
			Field field = currentClass.getField(fieldNameString);
			bindingItem.Fields.add(field);
			currentClass = field.getType();
		}
		bindingItem.Method = ReflectHelper.getMethodByClassAndType(
				controlItem.getClass(), bindingItem.getLastField().getType(),
				tagInfo.MethodNameString);
		bindingItem.View = controlItem;
		return bindingItem;
	}

	// //////////////

	public void bindData(View convertView, BaseViewModel item)
			throws IllegalAccessException, InvocationTargetException {
		ArrayList<View> controls = VisualHelper
				.getAllChildrenControls(convertView);
		for (View controlItem : controls) {
			Object tag = controlItem.getTag();
			if (!checkBindedTag(tag))
				continue;
			bindDataSingleControl(controlItem, item);
		}
	}

	public void bindDataSingleControl(View convertView, BaseViewModel item)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		ArrayList<TagBindingItem> items = ((TagBinding) convertView.getTag()).Items;
		for (TagBindingItem bindingItem : items) {
			BaseViewModel bindViewModel = item;
			for (int i = 0; i < bindingItem.Fields.size() - 1; i++)
				bindViewModel = (BaseViewModel) bindingItem.Fields.get(i).get(
						bindViewModel);
			String propertyKey = bindingItem.PropertyName;
			bindViewModel
					.AddHandler(bindingItem.PropertyName, bindingItem.Method,
							bindingItem.getLastField(), convertView);
			bindViewModel.NotifyPropertyChanged(bindingItem.PropertyName);
		}
	}

	private boolean checkBindedTag(Object tag) {
		return tag != null && tag instanceof TagBinding;
	}

}
