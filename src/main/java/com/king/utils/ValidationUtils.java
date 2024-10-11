package com.king.utils;

import java.util.List;
import java.util.Objects;

/**
 * 公共校验类
 */
public class ValidationUtils {
    /**
     * 校验List不为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isListNonEmpty(List<T> list) {
        return Objects.nonNull(list) && !list.isEmpty();
    }

    /**
     * 校验List为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isListEmpty(List<T> list) {
        return Objects.isNull(list) || list.isEmpty();
    }
}
