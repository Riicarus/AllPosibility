package com.skyline.anyposibility.runner.exception;

/**
 * [FEATURE INFO]<br/>
 * 执行程序失败异常
 *
 * @author Skyline
 * @create 2022-10-28 21:05
 * @since 1.0.0
 */
public class RunApplicationFailed extends RuntimeException {

    public RunApplicationFailed(String message, Throwable cause) {
        super(message, cause);
    }

}
