package it.artisan.response;

/**
 * 代码枚举
 * @author MoSence
 */
public interface CodeEnum {
    /**
     * 获取枚举的 Name
     * @return 枚举的 Name
     */
    String getName();

    /**
     * 获取 code
     * @return code
     */
    long getCode();

    /**
     * 获取默认消息
     * @return 默认消息
     */
    String getDefaultMessage();
}
