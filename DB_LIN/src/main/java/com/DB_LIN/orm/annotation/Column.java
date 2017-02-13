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
package com.DB_LIN.orm.annotation;

import java.lang.annotation.*;

/**
 * 列注解
 * <p>
 * 标注当前成员变量对应的列名，以及对应的set、get方法
 * </p>
 * @author Service Platform Architecture Team (spat@58.com)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	
	/**
	 * DB table 列名
	 * @return
	 */
	public String name() default "fieldName";
	
	/**
	 * 定义特殊set方法名
	 * @return
	 */
	public String setFuncName() default "setField";
	
	/**
	 * 定义特殊get方法名
	 * @return
	 */
	public String getFuncName() default "getField";
	
}