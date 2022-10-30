package it.artisan.exception;

import com.google.common.collect.Maps;
import it.artisan.response.CodeEnum;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/**
 * 全局 exception, 本系统中的所有自定义 exception 都应该由此派生, 以便 GlobalExceptionHandler 进行捕获
 *
 * @author zengfan
 */
@ToString
public class GlobalException extends RuntimeException {
    @Getter
    private CodeEnum codeEnum;
    @Getter
    private Map<String, Object> extraInfo = Maps.newHashMap();
    /**
     * 使用 codeEnum 的 defaultMessage 异常信息
     * defaultMessage 用来输出国际化信息
     * 一定要带上 cause，才能 log 出最原始的堆栈
     *
     * @param codeEnum 代码枚举
     */
    public GlobalException(CodeEnum codeEnum, Throwable cause) {
        super(codeEnum.toString(), cause);
        this.codeEnum = codeEnum;
    }

    /**
     * 使用 defaultMessage + extraInfo 作为异常信息
     * defaultMessage 用来输出国际化信息
     * 一定要带上 cause，才能 log 出最原始的堆栈
     * @param codeEnum 代码枚举
     * @param extraInfo 异常信息
     */
    public GlobalException(CodeEnum codeEnum, Map<String, Object> extraInfo, Throwable cause) {
        super(String.format("%s\t%s", codeEnum.toString(), Objects.toString(extraInfo,"")), cause);
        this.codeEnum = codeEnum;
        if (null != extraInfo) {
            this.extraInfo.putAll(extraInfo);
        }
    }
}

