/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.plugins;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * <p>
 * SQL 执行分析拦截器【 目前只支持 MYSQL-5.6.3 以上版本 】
 * </p>
 * 
 * @author hubin
 * @Date 2016-08-16
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SqlExplainInterceptor implements Interceptor {
	protected final Logger logger = Logger.getLogger("SqlExplainInterceptor");
	
	/**
	 * 发现执行全表 delete update 语句是否停止执行
	 */
	private boolean stopProceed = false;
	
	public Object intercept(Invocation invocation) throws Throwable {
		/**
		 * 处理 DELETE UPDATE 语句
		 */
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		if (ms.getSqlCommandType() == SqlCommandType.DELETE || ms.getSqlCommandType() == SqlCommandType.UPDATE) {
			Configuration configuration = ms.getConfiguration();
			Object parameter = invocation.getArgs()[1];
			BoundSql boundSql = ms.getBoundSql(parameter);
			Executor exe = (Executor) invocation.getTarget();
			Connection connection = exe.getTransaction().getConnection();

			/**
			 * 执行 SQL 分析
			 */
			sqlExplain(configuration, ms, boundSql, connection, parameter);
		}
		return invocation.proceed();
	}

	/**
	 * <p>
	 * 判断是否执行 SQL
	 * </p>
	 * @param configuration
	 * @param mappedStatement
	 * @param boundSql
	 * @param connection
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	protected void sqlExplain( Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql,
			Connection connection, Object parameter ) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder explain = new StringBuilder("EXPLAIN ");
			explain.append(boundSql.getSql());
			String sqlExplain = explain.toString();
			StaticSqlSource sqlsource = new StaticSqlSource(configuration, sqlExplain, boundSql.getParameterMappings());
			MappedStatement.Builder builder = new MappedStatement.Builder(configuration, "explain_sql", sqlsource, SqlCommandType.SELECT);
			builder.resultMaps(mappedStatement.getResultMaps()).resultSetType(mappedStatement.getResultSetType()).statementType(mappedStatement.getStatementType());
			MappedStatement query_statement = builder.build();
			DefaultParameterHandler handler = new DefaultParameterHandler(query_statement, parameter, boundSql);
			stmt = connection.prepareStatement(sqlExplain);
			handler.setParameters(stmt);
			rs = stmt.executeQuery();
			while ( rs.next() ) {
				if (!"Using where".equals(rs.getString("Extra"))) {
					String tip = " Full table operation is prohibited. SQL: " + boundSql.getSql();
					if (this.isStopProceed()) {
						throw new MybatisPlusException(tip);
					}
					logger.severe(tip);
					break;
				}
			}

		} catch ( Exception e ) {
			throw new MybatisPlusException(e); 
		} finally {
			if ( rs != null ) {
				rs.close();
				rs = null;
			}
			if ( stmt != null ) {
				stmt.close();
				stmt = null;
			}
		}
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		// TODO
	}

	public boolean isStopProceed() {
		return stopProceed;
	}

	public void setStopProceed(boolean stopProceed) {
		this.stopProceed = stopProceed;
	}

}