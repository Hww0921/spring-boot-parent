package it.artisan.response;

import lombok.AllArgsConstructor;

/**
 * 返回的错误代码
 * @author MoSence
 * @modify cxx
 */
@AllArgsConstructor
public enum CodeDefault implements CodeEnum {
	/**
     * 成功
     */
    OK(0, "成功"),
    /**
     * 认证失败
     */
    AUTH_FAIL(403, "认证失败,请求无效"),
    /**
     * 部分成功
     */
    PARTIAL_SUCCESS(100010001, "部分成功"),
    /**
     * 未处理异常
     */
    INTERNAL_SERVER_ERROR(100010002, "未处理异常"),
    /**
     * 客户端输入参数错误
     */
    ILLEGAL_ARGUMENT(100010003, "客户端输入参数错误"),
    /**
     * 主键生成错误
     */
    PRIMARY_ID_ERROR(100010004, "主键生成错误"),
    /**
     * 日期格式错误
     */
    ILLEGAL_DATE_FORMAT(100010005, "日期格式错误"),
    /**
     * 空值异常
     */
    NULL_POINT_ERROR(100010006, "空值异常");

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