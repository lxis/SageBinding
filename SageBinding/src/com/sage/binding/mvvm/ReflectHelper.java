package com.sage.binding.mvvm;

import java.lang.reflect.Method;

public class ReflectHelper
{
	//浠嶡baseClass閲屾壘鏂规硶鍚嶆槸@method锛屼笖绫诲�?�涓篅childClass鐨勫�?绫绘垨鑰呮帴鍙ｇ殑鏂规硶
	public static Method getMethodByClassAndType(Class<?> baseClass, Class<?> childClass,String method) throws NoSuchMethodException 
	{		
	        for (Class<?> c = childClass; c != null; c = c.getSuperclass()) 
	        {	        	
	        	try
	        	{
			return baseClass.getMethod(method, childClass);
	        	}
	        	catch(NoSuchMethodException e)
	        	{}
	        }

	            for (Class<?> c = childClass; c != null; c = c.getSuperclass()) 
	            {
	                for (Class<?> ifc : c.getInterfaces()) 
	                {
	                	 Method method1 = getMethodByClassAndType(baseClass,ifc,method);
	                	 if(method1!=null)
	                		 return method1;
	                		 
	                }
	            }					
	            return null;
	}
}
