package com.oracle.mybatis.typehandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleConnection;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.oracle.mybatis.domain.EmployeeDO;

public class EmployeeDaoTypeHandler implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = ps.getConnection();
		if (parameter instanceof Collection) {
			Collection objects = (Collection) parameter;
			Collection<Struct> oracleStructs = new ArrayList<>(objects.size());

			for (Object object : objects) {
				if(object instanceof EmployeeDO) {
					oracleStructs.add(convertToDbStruct(connection, (EmployeeDO)object));
				}
			}
			
			ps.setObject(i, ((OracleConnection) connection).createARRAY("EMPLOYEE_ARRAYTYPE", oracleStructs.toArray()));
		} else if (parameter instanceof EmployeeDO) {
			
			ps.setObject(i, convertToDbStruct(connection, (EmployeeDO) parameter), Types.STRUCT);
			
		} else {
			throw new SQLException("Unsupport parameter type: " + parameter.getClass().getCanonicalName()
					+ " for handler " + this.getClass().getCanonicalName());

		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		//return null;
		return convertSqlResult(cs.getObject(columnIndex));
	}
	
	 private Object convertSqlResult(Object sqlResult) throws SQLException {
	        if (sqlResult instanceof Array) {
	            // Converts JDBC Array into collection of target POJOs
	            Object[] structs = (Object[]) ((Array) sqlResult).getArray();
	            Collection<EmployeeDO> result = new ArrayList<>(structs.length);
	            for (Object struct : structs) {
	                // Convert JDBC Struct into target POJO
	                result.add(convertToEmployeeDao((Struct) struct));
	            }
	            return result;
	        } else if (sqlResult instanceof Struct) {
	            // Convert JDBC Struct into target POJO
	            return convertToEmployeeDao((Struct) sqlResult);
	        } else {
	            throw new SQLException("Unsupported parameter type: " + sqlResult.getClass().getCanonicalName()
	                    + " for handler " + this.getClass().getCanonicalName());
	        }
	    }
	
	
	
	private Struct convertToDbStruct(Connection connection, EmployeeDO object) throws SQLException{
		Object[] structFields = new Object[] {object.getEmployeeId(), object.getFirstName(), object.getLastName()};
		return connection.createStruct("EMPLOYEE_STRUCTTYPE", structFields);
	}
	
	private EmployeeDO convertToEmployeeDao(Struct struct) throws SQLException{
		EmployeeDO result = new EmployeeDO();
		Object[] attributes = struct.getAttributes();
		result.setEmployeeId((Integer) attributes[0]);
		result.setFirstName((String) attributes[1]);
		result.setLastName((String)attributes[2]);
		
		return result;
	}

}
