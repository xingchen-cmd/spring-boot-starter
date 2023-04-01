package com.liquan.myorm;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2023/3/30 20:12
 * @author: LiQuan
 **/
public class DefaultSqlSession implements SqlSession{

    private Connection connection;
    private Map<String,XNode> mapperElement;

    public DefaultSqlSession(Connection connection, Map<String, XNode> mapperElement) {
        this.connection = connection;
        this.mapperElement = mapperElement;
    }

    @Override
    public <T> T selectOne(String statement) {

        try {
            XNode xNode=mapperElement.get(statement);
            PreparedStatement preparedStatement=connection.prepareStatement(xNode.getSql());
            ResultSet resultSet=preparedStatement.executeQuery();
            List<T> objects=resultSet2Obj(resultSet,Class.forName(xNode.getResultType()));
            return objects.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public <T> T selectOne(String statement, Object parameter) {
        XNode xNode=mapperElement.get(statement);
        Map<Integer,String> parameterMap=xNode.getParameter();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(xNode.getSql());
            buildParamenter(preparedStatement,parameter,parameterMap);
            ResultSet resultSet=preparedStatement.executeQuery();
            List<T> objects= resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return objects.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public <T> List<T> selectList(String statement) {
        XNode xNode=mapperElement.get(statement);

        //Map<Integer,String> parameterMap=xNode.getParameter();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(xNode.getSql());
            ResultSet resultSet= preparedStatement.executeQuery();
            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        XNode xNode=mapperElement.get(statement);
        Map<Integer,String> parameterMap=xNode.getParameter();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(xNode.getSql());
            ResultSet resultSet=preparedStatement.executeQuery();
            buildParamenter(preparedStatement, parameter, parameterMap);
            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if(null==connection) return;

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list=new ArrayList<>();

        try {
            ResultSetMetaData metaData=resultSet.getMetaData();
            int columnCount =metaData.getColumnCount();
            while(resultSet.next()){
                T obj= (T) clazz.getDeclaredConstructor().newInstance();
                for(int i=1;i<=columnCount;i++){
                    Object value=resultSet.getObject(i);
                    String columnName=metaData.getColumnName(i);
                    String setMethod="set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);
                    Method method;

                    if(value instanceof  Timestamp){
                        method=clazz.getMethod(setMethod, Date.class);
                    }else{
                        method=clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private void buildParamenter(PreparedStatement preparedStatement, Object parameter, Map<Integer, String> parameterMap) throws SQLException {
            int size=parameterMap.size();

            if(parameter instanceof Long){
                for(int i=1;i<=size;i++){
                    preparedStatement.setLong(i, Long.parseLong(parameter.toString()));
                }
                return;
            }

            if(parameter instanceof Integer){
                for(int i=1;i<=size;i++){
                    preparedStatement.setInt(i, Integer.parseInt(parameter.toString()));
                }
                return;
            }

            if(parameter instanceof String){
                for(int i=1;i<=size;i++){
                    preparedStatement.setString(i, parameter.toString());
                }
                return;
            }

    }

}
