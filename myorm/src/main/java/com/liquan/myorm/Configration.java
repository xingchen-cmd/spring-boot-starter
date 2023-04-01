package com.liquan.myorm;

import java.sql.Connection;
import java.util.Map;

/**
 * @Description:
 * @Date: 2023/3/30 18:14
 * @author: LiQuan
 **/
public class Configration {

    protected Connection connection;

    protected Map<String,String> dataSo;

    protected Map<String,XNode> mapperElement;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setDataSo(Map<String, String> dataSo) {
        this.dataSo = dataSo;
    }

    public void setMapperElement(Map<String, XNode> mapperElement) {
        this.mapperElement = mapperElement;
    }
}
