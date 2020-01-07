package org.forkjoin.scrat.apikit.tool.info;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.TypeVariable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 类型信息
 *
 * @author zuoge85 on 15/6/12.
 */
public class TypeInfo implements Cloneable {
    private static final Logger log = LoggerFactory.getLogger(TypeInfo.class);

    private Type type;
    private String packageName;
    private String name;
    private boolean isArray;
    private List<TypeInfo> typeArguments = new ArrayList<>();
    /**
     * 是否包内，也就是需要进行包转换
     */
    private boolean isInside = false;
    /**
     * 是否是泛型的变量类型
     */
    private boolean isGeneric = false;
    private boolean isEnum = false;

    private TypeInfo() {
    }

    public TypeInfo(
            Type type,
            String packageName,
            String name,
            boolean isArray,
            List<TypeInfo> typeArguments,
            boolean isInside,
            boolean isGeneric,
            boolean isEnum
    ) {
        this.type = type;
        this.packageName = packageName;
        this.name = name;
        this.isArray = isArray;
        this.typeArguments = typeArguments;
        this.isInside = isInside;
        this.isGeneric = isGeneric;
        this.isEnum = isEnum;
    }

    public void addArguments(TypeInfo typeInfo) {
        typeArguments.add(typeInfo);
    }


    public TypeInfo replaceGeneric(TypeInfo realResultType) {
        if (this.isGeneric) {
            return realResultType;
        } else {
            for (ListIterator<TypeInfo> iterator = typeArguments.listIterator(); iterator.hasNext(); ) {
                iterator.set(iterator.next().replaceGeneric(realResultType));
            }
        }
        return this;
    }

    public static TypeInfo formGeneric(String name, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.OTHER;
        typeInfo.isArray = isArray;
        typeInfo.isInside = false;
        typeInfo.isGeneric = true;
        typeInfo.name = name;
        return typeInfo;
    }

    public static TypeInfo formBaseType(String name, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.form(name);
        typeInfo.isArray = isArray;
        typeInfo.isInside = false;

        if (!typeInfo.type.isBaseType()) {
            throw new AnalyseException("错误的类型,不是base类型:" + name);
        }
        return typeInfo;
    }

    public static TypeInfo formBaseType(Class cls, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.form(cls);
        typeInfo.isArray = isArray;
        typeInfo.isInside = false;

        if (!typeInfo.type.isBaseType()) {
            throw new AnalyseException("错误的类型,不是base类型:" + cls);
        }
        return typeInfo;
    }


    public static TypeInfo form(java.lang.reflect.Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            if (ClassUtils.isPrimitiveOrWrapper(cls)) {
                return TypeInfo.formBaseType(cls.getName(), false);
            } else if (cls.isArray()) {
                TypeInfo typeInfo = form(cls.getComponentType());
                typeInfo.setArray(true);
                return typeInfo;
            } else {
                Type t = Type.form(cls);
                if (!t.isBaseType()) {
                    return new TypeInfo(
                            t, cls.getPackage().getName(), cls.getSimpleName(), false, new ArrayList<>(), false, false, cls.isEnum()
                    );
                } else {
                    return TypeInfo.formBaseType(cls.getName(), false);
                }
            }
        } else if (type instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.ParameterizedType paramType = (java.lang.reflect.ParameterizedType) type;
            Class rawType = (Class) paramType.getRawType();

            TypeInfo typeInfo = form(rawType);
            java.lang.reflect.Type[] arguments = paramType.getActualTypeArguments();
            for (java.lang.reflect.Type typeArgument : arguments) {
                TypeInfo typeArgumentInfo = form(typeArgument);
                typeInfo.addArguments(typeArgumentInfo);
            }
            return typeInfo;
        } else if (type instanceof TypeVariable) {
            TypeVariable typeVar = (TypeVariable) type;
            return TypeInfo.formGeneric(typeVar.getName(), false);
        }
        throw new AnalyseException("暂时不支持的类型，分析失败:" + type);
    }

    public void setArray(boolean array) {
        this.isArray = array;
    }

    public List<TypeInfo> getTypeArguments() {
        return typeArguments;
    }

    public static TypeInfo formImport(Import typeName, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.form(typeName.getFullName());
        typeInfo.isArray = isArray;
        if (!typeInfo.type.isBaseType()) {
            typeInfo.packageName = typeName.getPackageName();
            typeInfo.name = typeName.getName();
            typeInfo.isInside = typeName.isInside();
        }
        return typeInfo;
    }

    public Type getType() {
        return type;
    }

    public String getFullName() {
        if (packageName == null) {
            return name;
        }
        return packageName + "." + name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getTrueName() {
        return type.isBaseType() ? type.getPrimitiveName() : name;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isBytes() {
        return isArray() && type == Type.BYTE;
    }


    public boolean isArrayOrCollection() {
        return (isArray() || isCollection()) && type != Type.BYTE;
    }

    public boolean isCollection() {
        try {
            String fullName = getFullName();
            if (fullName != null) {
                Class<?> cls = Class.forName(fullName);
                return Collection.class.isAssignableFrom(cls);
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean isInside() {
        return isInside;
    }

    public void setInside(boolean inside) {
        isInside = inside;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public boolean isObject() {
        return isOtherType() && getFullName().equals(Object.class.getName());
    }

    public void setGeneric(boolean generic) {
        isGeneric = generic;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeInfo typeInfo = (TypeInfo) o;
        return isArray == typeInfo.isArray &&
                isInside == typeInfo.isInside &&
                isGeneric == typeInfo.isGeneric &&
                type == typeInfo.type &&
                Objects.equals(packageName, typeInfo.packageName) &&
                Objects.equals(name, typeInfo.name) &&
                Objects.equals(typeArguments, typeInfo.typeArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, packageName, name, isArray, typeArguments, isInside, isGeneric);
    }

    @Override
    public TypeInfo clone() {
        try {
            return (TypeInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "TypeInfo{" +
                "type=" + type +
                ", packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", isArray=" + isArray +
                ", typeArguments=" + typeArguments +
                '}';
    }

    public boolean isOtherType() {
        return type == Type.OTHER;
    }

    public boolean isString() {
        return type == Type.STRING;
    }


    public Class<?> toClass() throws ClassNotFoundException {
        if (type.isBaseType()) {
            return type.toClass();
        } else {
            return Class.forName(getFullName());
        }
    }

    public boolean isEnum() {
        return isEnum;
    }

    public TypeInfo findByMyAndTypeArguments(String packageName, String name) {
        if (this.packageName.equals(packageName) && this.name.equals(name)) {
            return this;
        }
        return findByMyAndTypeArguments(packageName, name, this.getTypeArguments());
    }

    public TypeInfo findByMyAndTypeArguments(String packageName, String name, List<TypeInfo> typeArguments) {
        if (CollectionUtils.isNotEmpty(typeArguments)) {
            for (TypeInfo t : typeArguments) {
                if (t.packageName.equals(packageName) && t.name.equals(name)) {
                    return t;
                } else {
                    return findByMyAndTypeArguments(packageName, name, t.getTypeArguments());
                }
            }
        }
        return null;
    }

    /**
     * 0. void *(只在api返回值)*
     * 1. boolean
     * 2. byte *(8位有符号整数)*
     * 3. short *(16位有符号整数)*
     * 4. int *(32位有符号整数)*
     * 5. long *(64位有符号整数)*
     * 6. float *(32位浮点数)*
     * 7. double *(64位浮点数)*
     * 8. String
     * 9. Date
     * 10. enum 枚举类型，只支持简单枚举类型
     * 11. Message类型
     * <p>
     * enum Message 和其他非上面声明类型都不属于basic type
     *
     * @author zuoge85
     */
    public enum Type {
        VOID, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT,
        DOUBLE, STRING, DATE,
        OTHER;


        private static final ImmutableMap<String, Type> typeMap = ImmutableMap.<String, Type>builder()
                .put(void.class.getSimpleName(), VOID)
                .put(boolean.class.getSimpleName(), BOOLEAN)
                .put(byte.class.getSimpleName(), BYTE)
                .put(short.class.getSimpleName(), SHORT)
                .put(int.class.getSimpleName(), INT)
                .put(long.class.getSimpleName(), LONG)
                .put(float.class.getSimpleName(), FLOAT)
                .put(double.class.getSimpleName(), DOUBLE)
                .put(char.class.getSimpleName(), DOUBLE)

                .put(Void.class.getName(), VOID)
                .put(Boolean.class.getName(), BOOLEAN)
                .put(Byte.class.getName(), BYTE)
                .put(Short.class.getName(), SHORT)
                .put(Integer.class.getName(), INT)
                .put(Long.class.getName(), LONG)
                .put(Float.class.getName(), FLOAT)
                .put(Double.class.getName(), DOUBLE)

                .put(String.class.getName(), STRING)
                .put(Character.class.getName(), STRING)
                .put(Date.class.getName(), DATE)
                .put(Instant.class.getName(), DATE)
                .put(LocalDateTime.class.getName(), DATE)
                .put(LocalDate.class.getName(), DATE)
                .put(LocalTime.class.getName(), DATE)
                .build();

        private static final ImmutableMap<Class, Type> typeClassMap = ImmutableMap.<Class, Type>builder()
                .put(void.class, VOID)
                .put(boolean.class, BOOLEAN)
                .put(byte.class, BYTE)
                .put(short.class, SHORT)
                .put(int.class, INT)
                .put(long.class, LONG)
                .put(float.class, FLOAT)
                .put(double.class, DOUBLE)
                .put(char.class, STRING)

                .put(Void.class, VOID)
                .put(Boolean.class, BOOLEAN)
                .put(Byte.class, BYTE)
                .put(Short.class, SHORT)
                .put(Integer.class, INT)
                .put(Long.class, LONG)
                .put(Float.class, FLOAT)
                .put(Double.class, DOUBLE)

                .put(String.class, STRING)
                .put(Character.class, STRING)
                .put(Date.class, DATE)
                .put(Instant.class, DATE)
                .put(LocalDateTime.class, DATE)
                .put(LocalDate.class, DATE)
                .put(LocalTime.class, DATE)
                .build();

        private static final ImmutableMap<Type, Class> classMap = ImmutableMap.<Type, Class>builder()
                .put(VOID, Void.class)
                .put(BOOLEAN, Boolean.class)
                .put(BYTE, Byte.class)
                .put(SHORT, Short.class)
                .put(INT, Integer.class)
                .put(LONG, Long.class)
                .put(FLOAT, Float.class)
                .put(DOUBLE, Double.class)


                .put(STRING, String.class)
                .put(DATE, Date.class)
                .build();

        public static boolean isHasNull(Type type) {
            return type == STRING || type == DATE ||
                    type == OTHER;
        }

        public boolean isHasNull() {
            return isHasNull(this);
        }

        public static boolean isBaseType(Type type) {
            return type != OTHER;
        }

        public boolean isBaseType() {
            return isBaseType(this);
        }

        public Class toClass() {
            return classMap.get(this);
        }

        public String getPrimitiveName() {
            Class aClass = classMap.get(this);
            if (aClass != null) {
                Class<?> primitive = ClassUtils.wrapperToPrimitive(aClass);
                return primitive == null ? aClass.getSimpleName() : primitive.getSimpleName();
            } else {
                return null;
            }
        }

        public static Type form(String name) {
            Type type = typeMap.get(name);
            if (type == null) {
                return OTHER;
            }
            return type;
        }

        public static Type form(Class cls) {
            Type type = typeClassMap.get(cls);
            if (type == null) {
                return OTHER;
            }
            return type;
        }
    }
}
