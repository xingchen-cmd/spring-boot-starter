package com.liquan.myorm;

/**
 * @Description:
 * @Date: 2023/3/30 21:24
 * @author: LiQuan
 **/
public class SqlSessionFactoryImpl implements SqlSessionFactory{

    private final Configration configration;

    public SqlSessionFactoryImpl(Configration configration) {
        this.configration = configration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configration.connection, configration.mapperElement);
    }
}
