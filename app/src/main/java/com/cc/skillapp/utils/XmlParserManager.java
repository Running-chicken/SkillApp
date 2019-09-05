package com.cc.skillapp.utils;

import android.util.Xml;

import com.cc.skillapp.entity.Query;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class XmlParserManager {

    /**
     * 获取根节点对象及一个集合
     * 通过getBean()可以获得根节点对象，getList()获得集合
     * @param <T1>
     * @param xml
     *            输入流
     * @param clazz
     *            集合中的实体clazz
     * @param listRoot
     *            clazz根节点
     * @param bean
     *            根节点实体bean类
     * @param beanRoot
     *            bean的根节点 获取Query数据
     */
    public static <T, T1> Query<T> getQuery(String xml, Class<T> clazz, String listRoot,
											Class<T1> bean, String beanRoot) {
        Query<T> query = null;
        ArrayList<T> list = null;
        T t = null;
        T1 object = null;
        int nodecount = 0;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 开始元素事件
                        query = new Query<T>();
                        list = new ArrayList<T>();
                        query.setList(list);
                        break;
                    case XmlPullParser.START_TAG:// 开始结点
                        String tagName = parser.getName();
                        if (t != null) {
                            try {
                                Field field = clazz.getField(tagName);
                                if (field != null) {
                                    nodecount++;
                                    field.setAccessible(true);
                                    field.set(t, parser.nextText());
                                }
                            } catch (Exception e) {
                            }
                        } else if (object != null) {
                            try {
                                Field field1 = bean.getField(tagName);
                                if (field1 != null) {
                                    nodecount++;
                                    field1.set(object, parser.nextText());
                                }
                            } catch (Exception e) {
                                // e.printStackTrace();
                            }
                        }
                        if (tagName.equals(listRoot)) {
                            t = clazz.newInstance();
                        }
                        if (tagName.equals(beanRoot)) {
                            object = bean.newInstance();
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束结点
                        if (listRoot.equalsIgnoreCase(parser.getName())) {
                            if (t != null) {
                                list.add(t);
                                t = null;
                            }
                        } else if (beanRoot.equalsIgnoreCase(parser.getName())) {
                            query.setBean(object);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nodecount == 0)
            query = null;
        return query;

    }
}
