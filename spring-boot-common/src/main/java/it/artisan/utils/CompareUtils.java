package it.artisan.utils;




import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author dkp
 * @create 2022-12-24 00:15
 */
@Slf4j
public class CompareUtils<T> implements Serializable {

    private String serialField = "serialVersionUID";

    /**
     * 比较新老对象，返回的对象中只有在新对象优质且与老对象有差异的字段，
     * 只适用于同类型对象的比较，且对象的属性均为基本数据类型或者包装类
     * @param oldObj
     * @param newObj
     * @param clazz
     * @param ignores 中的属性保持为新对象的值
     * @param updateItems
     * @return
     */
    public T eliminateEqualAttr(T oldObj, T newObj, Class clazz, Set<String> ignores,
                                Map<String, List<String>> updateItems){
        if (oldObj == null || newObj == null){
            return newObj;
        }
        try {
            T resObj = (T)clazz.newInstance();
            Field[] fields = oldObj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                // 新的对象中为null 表示该项不做修改，序列号字段也不做修改
                if (field.get(newObj)==null||serialField.equals(field.getName())){
                    continue;
                }
                // 新老对象的值不相等 则表示字段需要修改，将新值写入结果对象
                if (ObjectUtils.notEqual(field.get(oldObj),field.get(newObj))){
                    field.set(resObj,field.get(newObj));

                    // 记录修改的字段
                    if (updateItems.containsKey(field.getName())){
                        log.info("xxx operate item has exists :" + field.getName());
                    }else {
                        List<String> updateItem = new ArrayList<>();
                        updateItem.add(org.springframework.util.ObjectUtils.isEmpty(field.get(oldObj))?"":field.get(oldObj).toString());

                        updateItem.add(org.springframework.util.ObjectUtils.isEmpty(field.get(newObj))?"":field.get(newObj).toString());
                        updateItems.put(field.getName(),updateItem);
                    }
                }
                // 如果要忽略的字段新老对象相等 为了保持该字段不被消除 需要写入结果
                if (!ObjectUtils.notEqual(field.get(oldObj),field.get(newObj))&&ignores.contains(field.getName())){
                    field.set(resObj,field.get(newObj));
                }
            }
            return resObj;
        }catch (Exception e){
            System.out.println("" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public T eliminateEqualAttr(T oldObj, T newObj, Class clazz, Set<String> ignores){
        HashMap<String, List<String>> updateItems = new HashMap<>();
        return eliminateEqualAttr(oldObj,newObj,clazz,ignores,updateItems);
    }

    public T eliminateEqualAttr(T oldObj, T newObj, Class clazz){
        Set<String> ignores = new HashSet<>();
        return eliminateEqualAttr(oldObj,newObj,clazz,ignores);
    }

    // 判断一个对象属性全为空，ignores中的属性也认为是空
    public boolean isAllAttributesNull(T obj,Set<String> ignores){
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (ignores.contains(field.getName())||serialField.equals(field.getName())){
                    continue;
                }
                if (field.get(obj)!=null){
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean isAllAttributesNull(T obj) {
        Set<String> ignores = new HashSet<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        return isAllAttributesNull(obj,ignores);
    }
}
