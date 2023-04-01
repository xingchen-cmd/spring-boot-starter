package com.liquan.myorm;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;


import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Date: 2023/3/30 21:28
 * @author: LiQuan
 **/
public class SqlSessionFactoryBuilder {

    public SqlSessionFactoryImpl build(Reader reader){
        SAXReader saxReader=new SAXReader();

        try {
            Document document= saxReader.read(reader);
            Configration configration=parseConfiguration(document.getRootElement());
            return new SqlSessionFactoryImpl(configration);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Configration parseConfiguration(Element rootElement) {
        Configration configration=new Configration();
        configration.setDataSo(dataScource((rootElement.selectNodes("//dataSource"))));
        configration.setConnection(connection(configration.dataSo));
        configration.setMapperElement(mapperElemrnt(rootElement.selectNodes("mappers")));
        return  configration;
    }

    private Map<String, XNode> mapperElemrnt(List<Node> list) {
        Map<String, XNode> map = new HashMap<>();

        Element element = (Element) list.get(0);
        Iterator<Element> elementIterator = element.elementIterator();
        while(elementIterator.hasNext()){
            Element e=elementIterator.next();
            String resource= e.attributeValue("resource");

            try {
                Reader reader=Resources.getResourceAsReader(resource);

                SAXReader saxReader=new SAXReader();
                Document document=saxReader.read(new InputSource(reader));
                Element root=document.getRootElement();
                //获取命名空间
                String namespace=root.attributeValue("namespace");

                List<Node> nodes = root.selectNodes("//select");
                for(Node node:nodes){
                    String id=((Element) node).attributeValue("id");
                    String parameterType=((Element) node).attributeValue("parameterType");
                    String resultType= ((Element) node).attributeValue("resultType");
                    String sql=node.getText();


                    //匹配
                    Map<Integer,String> parameter=new HashMap<>();
                    Pattern pattern=Pattern.compile("(#\\{(.*?)})");
                    Matcher matcher=pattern.matcher(sql);

                    for(int i=1;matcher.find();i++){
                        String g1=matcher.group(1);
                        String g2= matcher.group(2);
                        parameter.put(i, g2);
                        sql=sql.replace(g1, "?");
                    }

                    XNode xNode=new XNode();
                    xNode.setNamespace(namespace);
                    xNode.setId(id);
                    xNode.setParameterType(parameterType);
                    xNode.setSql(sql);
                    xNode.setResultType(resultType);
                    xNode.setParameter(parameter);

                    map.put(namespace+"."+id,xNode);
                }



            } catch (IOException | DocumentException ioException) {
                ioException.printStackTrace();
            }

        }
        return map;
    }

    private Map<String, String> dataScource(List<Node> selectNodes) {
        Map<String,String> dataSource=new HashMap<>(4);
        Element element=(Element) selectNodes.get(0);
        Iterator<Element> elementIterator = element.elementIterator();
        while(elementIterator.hasNext()){
            Element e=elementIterator.next();
            String name=e.attributeValue("name");
            String value=e.attributeValue("value");
            dataSource.put(name,value);
        }
        return dataSource;
    }


    private Connection connection(Map<String, String> dataSo) {

        try {
            Class.forName(dataSo.get("driver"));
            return DriverManager.getConnection(dataSo.get("url"),dataSo.get("username"),dataSo.get("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
}
