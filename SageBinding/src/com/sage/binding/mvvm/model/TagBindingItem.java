package com.sage.binding.mvvm.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TagBindingItem
{
	public Method Method;
	
	public ArrayList<Field> Fields = new ArrayList<Field>();
	

	public Field getLastField()
	{
		return Fields.get(Fields.size()-1);
	}
	
	public String PropertyName;		
	
	public android.view.View View;
}
