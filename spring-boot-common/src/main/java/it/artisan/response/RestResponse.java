package it.artisan.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Rest 请求返回值
 *
 * @author zengfan
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
@ApiModel
public class RestResponse<T> implements Serializable {

    /**
     * 执行状态码, 执行成功返回 0, 其他的都是 EXCEPTION
     */
    @ApiModelProperty(value = "执行状态码, 执行成功返回 0, 其他的都是 EXCEPTION")
    private long code;

    /**
     * 消息, OK 的时候, message 一般不填
     */
    @ApiModelProperty(value = "消息, OK 的时候, message 一般不填")
    private String message = null;

    /**
     * 具体的数据, EXCEPTION 的时候, data 一般 不填
     */
    @ApiModelProperty(value = "具体的数据, EXCEPTION 的时候, data 一般 不填")
    private T data = null;

    /**
     * 时间戳, 只在 exception 时有用
     */
    @ApiModelProperty(value = "时间戳, 只在 exception 时有用")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp = null;

    /**
     * 放一些辅助信息 key->value, 一般用来进一步描述异常信息, OK 时暂未用到
     */
    @ApiModelProperty(value = "放一些辅助信息 key->value, 一般用来进一步描述异常信息, OK 时暂未用到")
    private Map<String, Object> extraInfo = null;


    /**
     * 表示调用成功
     * @return 结果
     */
    public static <T> RestResponse<T> ok() {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = CodeDefault.OK.getCode();
        return restResponse;
    }

    /**
     * 表示调用成功
     * @param data 数据
     * @return 结果
     */
    public static <T> RestResponse<T> ok(T data) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = CodeDefault.OK.getCode();
        restResponse.data = data;
        return restResponse;
    }

    /**
     * 调用发生 异常
     * @param errorMessage 代码枚举
     * @return 结果
     */
    public static <T> RestResponse<T> exception(String errorMessage) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = -1;
        restResponse.message = errorMessage;
        restResponse.timestamp = new Date();
        return restResponse;
    }

    /**
     * 调用发生 异常
     * @param codeEnum 代码枚举
     * @return 结果
     */
    public static <T> RestResponse<T> exception(CodeEnum codeEnum) {
        return exception(codeEnum, null);
    }

    /**
     * 调用发生 异常
     * @param codeEnum 代码枚举
     * @param exceptionInfo 详细信息
     * @return 结果
     */
    public static <T> RestResponse<T> exception(CodeEnum codeEnum, Map<String, Object> exceptionInfo) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = codeEnum.getCode();
        restResponse.message = codeEnum.getDefaultMessage();
        // 国际化时, 使用这个
        //        restResponse.message = I18nUtil.getResponseMessage(responseCode);
        restResponse.timestamp = new Date();
        restResponse.extraInfo = exceptionInfo;
        return restResponse;
    }
}
