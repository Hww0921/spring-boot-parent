package it.artisan.exception;
import it.artisan.response.CodeEnum;
import lombok.Getter;
import lombok.ToString;

/**
 * 全局校验 exception, 如客户端输入参数错误，这些是属于可控范围的 exception； 无需 log
 *
 * @author zengfan
 */
@ToString
public class GlobalVerifyException extends RuntimeException {
    @Getter
    private CodeEnum codeEnum;

    public GlobalVerifyException() {
        super();
    }

    public GlobalVerifyException(String message) {
        super(message);
    }

    /**
     * 使用 codeEnum 的 defaultMessage 异常信息
     *
     * @param codeEnum 代码枚举
     */
    public GlobalVerifyException(CodeEnum codeEnum) {
        super(codeEnum.toString());
        this.codeEnum = codeEnum;
    }

    /**
     * 使用 defaultMessage + message 作为异常信息
     *
     * @param codeEnum 代码枚举
     * @param message 信息
     */
    public GlobalVerifyException(CodeEnum codeEnum, String message) {
        super(String.format("%s\t%s", codeEnum.toString(), message));
        this.codeEnum = codeEnum;
    }

    /**
     * 使用 defaultMessage + message 作为异常信息
     *
     * @param codeEnum 代码枚举
     * @param message 信息
     * @param cause   异常
     */
    public GlobalVerifyException(CodeEnum codeEnum, String message, Throwable cause) {
        super(String.format("%s\t%s", codeEnum.toString(), message), cause);
        this.codeEnum = codeEnum;
    }
}

