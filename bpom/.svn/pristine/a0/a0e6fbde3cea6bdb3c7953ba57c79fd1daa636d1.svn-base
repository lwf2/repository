package com.aspire.bpom.xml.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.aspire.bpom.constants.SysConstant;
import com.aspire.bpom.xml.bean.response.PaySignResp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ParseXMLHelper {

    private static final Log log = LogFactory.getLog(ParseXMLHelper.class);

    private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + System.lineSeparator();

    private static final Map<String, XStream> xstreamMap = new HashMap<String, XStream>();

    static {
        XStream appVerifyResult = new XStream(new XppDriver());
        appVerifyResult.processAnnotations(new Class[] { PaySignResp.class });
        xstreamMap.put("AppVerifyResult", appVerifyResult);

        for (XStream xstream : xstreamMap.values()) {
            xstream.ignoreUnknownElements();
        }
    }

    private static XStream getXStream(Class<?> clazz) {
        String clazzName = clazz.getSimpleName();
        XStream xStream = null;
        if (xstreamMap.containsKey(clazzName)) {
            xStream = xstreamMap.get(clazzName);
        } else {
            xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("$", "_")));
            xStream.ignoreUnknownElements();
            xStream.processAnnotations(clazz);
            xstreamMap.put(clazzName, xStream);
        }
        return xStream;
    }

    public static Object parseXML2Obj(InputStream xml) {
        XStream xStream = new XStream(new XppDriver());
        return xStream.fromXML(xml);
    }

    public static <T> T parseXMLToObject(String xml, Class<T> clazz) {
        XStream xStream = getXStream(clazz);
        return (T) xStream.fromXML(xml);
    }

    public static String object2XML(Class<?> clazz, Object obj) {
        XStream xStream = getXStream(clazz);
        return xmlHead + xStream.toXML(obj);
    }

    /**
     * 获取解析好的文档对象
     * 
     * @param reader
     *            xml字符流
     * @return 文档对象
     */
    public static Document getDocument(String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return document;
    }

    /**
     * 获取根元素
     * 
     * @param reader
     * @return
     */
    public static Element getRoot(String xml) {
        Document document = getDocument(xml);
        return document.getRootElement();
    }

    /**
     * 获取指定元素的Element对象
     * 
     * @param reader
     * @param nodeName
     * @return
     */
    public static Element getElement(String xml, String nodeName) {
        Element root = getRoot(xml);
        return root.element(nodeName);
    }

    /**
     * 获取xml的指定元素值
     * 
     * @param xml
     * @param nodeName
     * @return
     */
    public static String getElementVal(String xml, String nodeName) {
        Element element = getElement(xml, nodeName);
        return element.getTextTrim();
    }

    /**
     * xml转javabean的方法
     * 
     * @param xml
     * @param clazz要转换成的bean的Class
     * @return
     */
    // @SuppressWarnings("unchecked")
    // public static <T> T parseXMLToObject(String xml, Class<T> clazz) {
    // T unmarshal = null;
    // try {
    // JAXBContext context = JAXBContext.newInstance(clazz);
    // Unmarshaller um = context.createUnmarshaller();
    // StringReader sr = new StringReader(xml);
    // unmarshal = (T) um.unmarshal(sr);
    // } catch (JAXBException e) {
    // log.error(e.getMessage(), e);
    // throw new RuntimeException(e);
    // }
    // return unmarshal;
    // }

    /**
     * xml转javabean的方法
     * 
     * @param xml的流
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseXMLToObject(InputStream xml, Class<T> clazz) {
        T unmarshal = null;
        InputStreamReader inputStreamReader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller um = context.createUnmarshaller();
            inputStreamReader = new InputStreamReader(xml, SysConstant.CHARSET_UTF8);
            unmarshal = (T) um.unmarshal(inputStreamReader);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }
        return unmarshal;
    }

    /**
     * javabean转xml的方法
     * 
     * @param clazz
     *            要转换对象的字节码
     * @param obj
     *            要转换的对象
     * @return
     */
    // public static String object2XML(Class<?> clazz, Object obj) {
    // return object2XML(clazz, obj, true, true);
    // }

    public static String object2XML(Class<?> clazz, Object obj, boolean includeHead, boolean formatted) {

        StringWriter sw = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, !includeHead);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            sw = new StringWriter();
            m.marshal(obj, sw);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return sw.toString();

    }
}
