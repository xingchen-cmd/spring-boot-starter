package com.liquan.myorm;

import java.util.Collections;

/**
 * @Description:
 * @Date: 2023/3/30 21:21
 * @author: LiQuan
 **/
public interface SqlSessionFactory {

    SqlSession openSession();
}
