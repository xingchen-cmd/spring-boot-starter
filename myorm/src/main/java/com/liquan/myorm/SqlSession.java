package com.liquan.myorm;

import java.util.List;

/**
 * @Description:
 * @Date: 2023/3/30 20:07
 * @author: LiQuan
 **/
public interface SqlSession {

   <T>T selectOne(String statement);

   <T>T selectOne(String statement,Object parameter);

   <T> List<T> selectList(String statement);

   <T> List<T> selectList(String statement, Object parameter);

   void close();
}
