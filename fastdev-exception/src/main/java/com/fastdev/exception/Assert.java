package com.fastdev.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 断言类 来源于spring Assert
 *
 * @author zhouxi
 */
public abstract class Assert {


    /**
     * 判断布尔表达式是否为真,同isTrue
     *
     * @param expression
     * @param message
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BaseException(message);
        }
    }

    /**
     * 判断布尔表达式是否为真,同isTrue
     *
     * @param expression
     */
    public static <T extends RuntimeException> void state(boolean expression, Supplier<T> supplier) {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言true
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        state(expression, message);
    }

    /**
     * 断言true 返回自定义异常
     *
     * @param expression
     * @param supplier
     * @param <T>
     */
    public static <T extends RuntimeException> void isTrue(boolean expression, Supplier<T> supplier) {
        state(expression, supplier);
    }

    /**
     * 为null断言
     *
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        state(Objects.isNull(object), message);
    }

    /**
     * 为null断言
     *
     * @param object
     * @param supplier
     */
    public static <T extends RuntimeException> void isNull(Object object, Supplier<T> supplier) {
        state(Objects.isNull(object), supplier);
    }

    /**
     * 不为null 断言
     *
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        state(Objects.nonNull(object), message);
    }

    /**
     * 不为null 断言
     *
     * @param object
     * @param supplier
     */
    public static <T extends RuntimeException> void notNull(Object object, Supplier<T> supplier) {
        state(Objects.nonNull(object), supplier);
    }

    /**
     * 断言length
     *
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        state(null == text || text.length() == 0, message);
    }

    /**
     * 断言length
     *
     * @param text
     * @param supplier
     */
    public static <T extends RuntimeException> void hasLength(String text, Supplier<T> supplier) {
        state(null == text || text.length() == 0, supplier);
    }

    /**
     * 断言字符串最大长度
     *
     * @param text
     * @param maxLength
     * @param message
     */
    public static void lengthMax(String text, Integer maxLength, String message) {
        if (text != null && text.length() > maxLength) {
            throw new BaseException(message);
        }
    }

    /**
     * 断言字符串最大长度
     *
     * @param text
     * @param maxLength
     * @param supplier
     */
    public static <T extends RuntimeException> void lengthMax(String text, Integer maxLength, Supplier<T> supplier) {
        if (text != null && text.length() > maxLength) {
            throw supplier.get();
        }
    }

    /**
     * 断言hasText
     *
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        state(StringUtils.hasText(text), message);
    }

    /**
     * 断言hasText
     *
     * @param text
     * @param supplier
     */
    public static <T extends RuntimeException> void hasText(String text, Supplier<T> supplier) {
        state(StringUtils.hasText(text), supplier);
    }


    /**
     * 断言不包含子串
     *
     * @param textToSearch
     * @param substring
     * @param message
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BaseException(message);
        }
    }

    /**
     * 断言不包含子串
     *
     * @param textToSearch
     * @param substring
     * @param supplier
     */
    public static <T extends RuntimeException> void doesNotContain(@Nullable String textToSearch, String substring, Supplier<T> supplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws BaseException if the object array is {@code null} or contains no elements
     */
    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BaseException(message);
        }
    }

    public static <T extends RuntimeException> void notEmpty(@Nullable Object[] array, Supplier<T> supplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw supplier.get();
        }
    }

    /**
     * 断言集合不为空
     *
     * @param collection
     * @param message
     */
    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseException(message);
        }
    }

    /**
     * 断言集合不为空
     *
     * @param collection
     * @param supplier
     */
    public static <T extends RuntimeException> void notEmptyCollection(@Nullable Collection<?> collection, Supplier<T> supplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">
     * Assert.notEmpty(collection, () -&gt; "The " + collectionType + " collection must contain elements");
     * </pre>
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws BaseException if the collection is {@code null} or
     *                       contains no elements
     * @since 5.0
     */
    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseException(nullSafeGet(messageSupplier));
        }
    }


    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>Note: Does not complain if the collection is empty!
     * <pre class="code">Assert.noNullElements(collection, "Collection must contain non-null elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws BaseException if the collection contains a {@code null} element
     * @since 5.2
     */
    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BaseException(message);
                }
            }
        }
    }

    /**
     * 判断没有null元素
     *
     * @param collection
     * @param supplier
     * @param <T>
     */
    public static <T extends RuntimeException> void noNullElementsSupplier(@Nullable Collection<?> collection, Supplier<T> supplier) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw supplier.get();
                }
            }
        }
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>Note: Does not complain if the collection is empty!
     * <pre class="code">
     * Assert.noNullElements(collection, () -&gt; "Collection " + collectionName + " must contain non-null elements");
     * </pre>
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws BaseException if the collection contains a {@code null} element
     * @since 5.2
     */
    public static void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BaseException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws BaseException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BaseException(message);
        }
    }

    /**
     * 不是空map
     *
     * @param map
     * @param supplier
     * @param <T>
     */
    public static <T extends RuntimeException> void notEmptyMap(@Nullable Map<?, ?> map, Supplier<T> supplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">
     * Assert.notEmpty(map, () -&gt; "The " + mapType + " map must contain entries");
     * </pre>
     *
     * @param map             the map to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws BaseException if the map is {@code null} or contains no entries
     * @since 5.0
     */
    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BaseException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
     *
     * @param type    the type to check against
     * @param obj     the object to check
     * @param message a message which will be prepended to provide further context.
     *                If it is empty or ends in ":" or ";" or "," or ".", a full exception message
     *                will be appended. If it ends in a space, the name of the offending object's
     *                type will be appended. In any other case, a ":" with a space and the name
     *                of the offending object's type will be appended.
     * @throws BaseException if the object is not an instance of type
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    public static <T extends RuntimeException> void isInstanceOfObj(Class<?> type, @Nullable Object obj, Supplier<T> supplier) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
     * </pre>
     *
     * @param type            the type to check against
     * @param obj             the object to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails. See {@link #isInstanceOf(Class, Object, String)} for details.
     * @throws BaseException if the object is not an instance of type
     * @since 5.0
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     *
     * @param type the type to check against
     * @param obj  the object to check
     * @throws BaseException if the object is not an instance of type
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
     *
     * @param superType the super type to check against
     * @param subType   the sub type to check
     * @param message   a message which will be prepended to provide further context.
     *                  If it is empty or ends in ":" or ";" or "," or ".", a full exception message
     *                  will be appended. If it ends in a space, the name of the offending sub type
     *                  will be appended. In any other case, a ":" with a space and the name of the
     *                  offending sub type will be appended.
     * @throws BaseException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
     * </pre>
     *
     * @param superType       the super type to check against
     * @param subType         the sub type to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails. See {@link #isAssignable(Class, Class, String)} for details.
     * @throws BaseException if the classes are not assignable
     * @since 5.0
     */
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言类型
     *
     * @param superType
     * @param subType
     * @param supplier
     * @param <T>
     */
    public static <T extends RuntimeException> void isAssignableClazz(Class<?> superType, @Nullable Class<?> subType, Supplier<T> supplier) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     *
     * @param superType the super type to check
     * @param subType   the sub type to check
     * @throws BaseException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }


    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new BaseException(result);
    }


    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new BaseException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

}

