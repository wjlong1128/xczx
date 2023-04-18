package com.wjl.xczx.common.exception;

import java.util.function.Supplier;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */

public class Assert {

    public static void change(int i, Supplier<CommonException> s) {
        if (i <= 0) {
            throw s.get();
        }
    }

    public static void isTrue(boolean i, Supplier<CommonException> s) {
        if (!i) {
            throw s.get();
        }
    }

    public static void notNull(Object i, Supplier<CommonException> s) {
        if (i == null) {
            throw s.get();
        }
    }

    public static void notBlank(String i, Supplier<CommonException> s) {
        if (i == null || "".equals(i)) {
            throw s.get();
        }
    }

}
