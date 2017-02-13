/*
 *  Copyright Beijing 58 Information Technology Co.,Ltd.
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.DB_LIN.orm;



import com.DB_LIN.orm.entity.ClassInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Service Platform Architecture Team (spat@58.com)
 */
final class MappingAnnotationUtil {

	private static Map<Class<?>, ClassInfo> classInfoCache = new HashMap<Class<?>, ClassInfo>();
	
	public static Map<Class<?>, ClassInfo> getAllClassInfo() {
		return classInfoCache;
	}
	
	private static ClassInfo getClassInfo(Class<?> clazz){
		ClassInfo ci = classInfoCache.get(clazz);
		if(ci == null) {
			try {
				ci = new ClassInfo(clazz);
				classInfoCache.put(clazz, ci);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
		return ci;
	}

	/**
	 * 获得字段对应的set方法
	 */
	public static Method getSetterMethod(Class<?> clazz, Field field) throws Exception {
		ClassInfo ci = getClassInfo(clazz);
		Map<String, Method> mapSetterMethod = ci.getMapSetMethod();
		return mapSetterMethod.get(field.getName());
	}
	
	/**
	 * 获得字段对应的get方法
	 */
	public static Method getGetterMethod(Class<?> clazz, Field field) {
		ClassInfo ci = getClassInfo(clazz);
		Map<String, Method> mapGetterMethod = ci.getMapGetMethod();
		return mapGetterMethod.get(field.getName());
	}
	
	/**
	 * 获得所有字段
	 */
	public static List<Field> getAllFields(Class<?> clazz) {
		ClassInfo ci = getClassInfo(clazz);
		Collection<Field> coll = ci.getMapAllDBField().values();
		List<Field> fields = new ArrayList<Field>();
		for(Field f : coll) {
			fields.add(f);
		}
		return fields;
	}
	
	/**
	 * 获得字段名
	 */
	public static String getDBCloumnName(Class<?> clazz, Field f){
		ClassInfo ci = getClassInfo(clazz);
		return ci.getMapDBColumnName().get(f.getName());
	}
}

