package it.artisan.utils;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author dkp
 * @create 2022-11-07 19:25
 */
public class ListConvertAdapter<T, V> {
    private static final Logger logger = Logger.getLogger(ListConvertAdapter.class);

    /**
     * 生成类属性对象List集合
     */
    private final List<V> objects;
    /**
     * 需要生成List的类属性名称
     */
    private final String propertyName;

    private char split = ',';

    /**
     * 适配器,在创建时必须传入List参数和生成属性名称
     *
     * @param objects      list集合
     * @param propertyName 需要生成List参数和生成属性名称
     */
    public ListConvertAdapter(List<V> objects, String propertyName) {
        this.objects = objects;
        this.propertyName = propertyName;

        if (!validConvertParams()) {
            logger.warn("传入参数为空,objects=" + objects + ",propertyName=" + propertyName);
        }
    }

    /**
     * list 集合对象的某一属性转换为Object[] 集合
     * @return 获取属性list 去重复 返回数组
     */
    public Object[] getUnRepeatElementsArray() {
        Set<T> objectPropertyElements = getUnRepeatElements();
        if (CollectionUtils.isEmpty(objectPropertyElements)) {
            return null;
        }
        return objectPropertyElements.toArray();
    }

    /**
     * list集合对象的某一个属性转换为set 集合
     * @return 获取属性list 去重复 返回HashSet
     */
    public Set<T> getUnRepeatElements() {
        List<T> objectPropertyElements = getElements();
        if (CollectionUtils.isEmpty(objectPropertyElements)) {
            return new HashSet<>(0);
        }
        Set<T> objectPropertyElementSet = new HashSet<>();
        for (T objectPropertyElement : objectPropertyElements) {
            objectPropertyElementSet.add(objectPropertyElement);
        }
        return objectPropertyElementSet;
    }

    /**
     * list集合对象的某一个属性转换为object[]集合
     * @return Object[]
     */
    public Object[] getElementsArray() {
        List<T> objectPropertyElements = getElements();
        if (CollectionUtils.isEmpty(objectPropertyElements)) {
            return null;
        }
        return objectPropertyElements.toArray();
    }

    /**
     *  list 集合对象的某一属性转换为string 用逗号分隔
     * @return String
     */
    public String getElementsString() {
        List<T> objectPropertyElements = getElements();
        if (objectPropertyElements == null) {
            return null;
        }
        return StringUtils.join(objectPropertyElements.toArray(), split);
    }

    /**
     * 将list转换成map key 为传入的属性
     * @return map
     */
    public Map<String, V> toMap() {
        if (CollectionUtils.isEmpty(objects)) {
            return new HashMap<>(0);
        }
        Map<String, V> m = new HashMap<>();
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Object o = null;
        try {
            for (V t : objects) {
                pd = new PropertyDescriptor(propertyName, t.getClass());
                getMethod = pd.getReadMethod();
                o = getMethod.invoke(t);
                m.put((String) o, t);
            }
            return m;
        } catch (Exception e) {
            logger.error("exception:" + e.getMessage());
        }
        return null;
    }

    public Map<String, V> toMapWithMultiProperty(String... propertyNames) {
        if (CollectionUtils.isEmpty(objects)) {
            return new HashMap<>();
        }
        HashMap<String, V> m = new HashMap<>();
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Object o = null;
        StringBuilder sb = null;
        try {
            for (V t : objects) {
                sb = new StringBuilder();
                pd = new PropertyDescriptor(propertyName, t.getClass());
                getMethod = pd.getReadMethod();
                o = getMethod.invoke(t);
                sb.append(o.toString());
                for (String str : propertyNames) {
                    pd = new PropertyDescriptor(str, t.getClass());
                    getMethod = pd.getReadMethod();
                    o = getMethod.invoke(t);
                    sb.append(null == o ? "" : o.toString());
                }
                m.put(sb.toString(), t);
            }
            return m;
        } catch (Exception e) {
            logger.error("exception:" + e.getMessage());
        }
        return null;
    }


    /**
     * 将list转换为map key 为传入的属性
     * @param propertyNames
     * @return
     */
    public Map<String, List<V>> toMapWithMultiValue(String... propertyNames) {
        if (CollectionUtils.isEmpty(objects)) {
            return new HashMap<>();
        }
        Map<String, List<V>> m = new HashMap<>();
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Object o = null;
        StringBuilder sb = null;
        try {
            for (V t : objects) {
                sb = new StringBuilder();
                pd = new PropertyDescriptor(propertyName, t.getClass());
                getMethod = pd.getReadMethod();
                o = getMethod.invoke(t);
                sb.append(o.toString());
                if (null != propertyNames) {
                    for (String str : propertyNames) {
                        pd = new PropertyDescriptor(str, t.getClass());
                        getMethod = pd.getReadMethod();
                        o = getMethod.invoke(t);
                        sb.append(null == o ? "" : o.toString());
                    }
                }
                if (null == m.get(sb.toString())) {
                    ArrayList<V> list = new ArrayList<>();
                    list.add(t);
                    m.put(sb.toString(), list);
                } else {
                    m.get(sb.toString()).add(t);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * list集合对象的某一个属性转换成list集合
     * @return list
     */
    public List<T> getElements() {
        if (objects == null) {
            return null;
        }
        List<T> objectPropertyElements = new ArrayList<>();
        Class<?> clazz = null;
        Field field = null;
        StringBuffer nameBuffer = null;
        Method getPropertyNameMethod = null;
        Method[] methods = null;
        try {
            for (V v : objects) {
                clazz = v.getClass();
                // 查询属性在类中存不存在
                //private 方法查询
                field = clazz.getDeclaredField(propertyName);
                //查询不到找public方法

                if (field == null) {
                    if (clazz.getField(propertyName) == null) {
                        return getEmptyValues();
                    }
                    field = clazz.getField(propertyName);
                }
                // 获取方法名称
                nameBuffer = new StringBuffer();
                nameBuffer.append(ElementsMethod.GET.getMethodHeadCode()).append(propertyName);
                // 找出对应的方法
                methods = clazz.getMethods();

                if (ArrayUtils.isEmpty(methods)) {
                    return getEmptyValues();
                }

                for (Method method : methods) {
                    if (method.getName().toUpperCase().equals(nameBuffer.toString().toUpperCase())) {
                        getPropertyNameMethod = method;
                        break;
                    }
                }
                // 找不到对应属性的get 方法
                if (getPropertyNameMethod == null) {
                    return getEmptyValues();
                }

                objectPropertyElements.add((T) getPropertyNameMethod.invoke(v));
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            return getEmptyValues();
        }

        return objectPropertyElements;
    }


    /**
     * 校验转换参数是否符合转换逻辑
     *
     * @return
     */
    protected boolean validConvertParams() {
        if (StringUtils.isBlank(propertyName)) {
            return false;
        }
        return true;
    }

    /**
     * 不符合检验逻辑 返回空list
     *
     * @return
     */
    private List<T> getEmptyValues() {
        return new ArrayList<>();
    }

    /**
     * 获取数组常量 参数取值可以扩展
     */
    protected interface CommonPropertyAware {
        String id = "id";
        String cid = "cid";
        String SPU_ID = "spuId";
    }

    /**
     * 类属性方法head 枚举
     */
    protected enum ElementsMethod {

        /**
         * get方法
         */
        GET("get"),
        /**
         * boolean 方法
         */
        IS("is"),
        /**
         * set方法
         */
        SET("set");
        /**
         * 方法头参数
         */
        private String methodHeadCode;

        /**
         * 构造方法
         *
         * @param methodHeadCode
         */
        private ElementsMethod(String methodHeadCode) {
            this.methodHeadCode = methodHeadCode;
        }

        private String getMethodHeadCode() {
            return methodHeadCode;
        }
    }


    public char getSplit() {
        return split;
    }

    public void setSplit(char split) {
        this.split = split;
    }
}
