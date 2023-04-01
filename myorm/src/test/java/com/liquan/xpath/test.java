package com.liquan.xpath;

import com.alibaba.fastjson2.JSON;
import com.liquan.myorm.XNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Date: 2023/3/31 13:17
 * @author: LiQuan
 **/
public class test {
    public static void main(String[] args) {
        SAXReader reader=new SAXReader();
        try {
            Document document = reader.read("src/main/resources/config/mysql.xml");
            Element element = document.getRootElement();


            List<Node> nodes = element.selectNodes("//dataSource");
            Element node = (Element) nodes.get(0);

            Iterator<Element> elementIterator = node.elementIterator();
            while(elementIterator.hasNext()){
                Element element11=elementIterator.next();

                System.out.println(element11.attributeValue("name") + element11.attribute("value"));;

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


}
