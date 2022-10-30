/*
 */
package it.artisan.response;

import lombok.AllArgsConstructor;

/**
 * 返回的错误代码
 * @author MoSence
 * @modify cxx
 */
@AllArgsConstructor
public enum CodeMetadata implements CodeEnum {
   
    
    /**
     * 数据不存在
     */
    DATA_NOEXIT(200010005, "数据不存在"),
    /**
     * 数据已存在
     */
    DATA_EXIT(200010006, "数据已存在,不可重复添加"),
    
    /**
     * 无权限操作该数据
     */
    NO_AUTH(200010007, "无权限操作该数据"),
    
    /**
     * 无权限操作该只读数据
     */
    DATA_IS_READONLY(200010008, "无权限操作该只读数据"),
    
    /**
     * 无权限操作内置数据
     */
    DATA_IS_BUIDIN(200010009, "无权限操作内置数据"),
	/**
     * 已被收藏
     */

    DATA_IS_COLLECTION(200010010, "任务或数据已被收藏"),
    
    /**
     * 非管理员不可变更改数据
     */
    IS_NOT_ADMIN(200010011, "非管理员不可变更改数据"),
    
    /**
     * 不能提交空的脱敏
     */
    IS_NULL_EMASKING(200010012, "不能提交空的脱敏申请"), 
    
    
    
    FULLTEXT_SEARCH_EXCEPTION(300010000, "全文检索异常"),
    
    LINEAGE_SEARCH_EXCEPTION(400010000, "血缘查询异常");
    /**
     * 返回客户端的编码
     */
    private final int code;

    /**
     * 默认消息
     */
    private final String defaultMessage;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getDefaultMessage() {
        return this.defaultMessage;
    }
}