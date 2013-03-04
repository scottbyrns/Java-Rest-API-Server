package com.geoapi.utils;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for working with generic types.
 */
public abstract class GenericsUtil {
    /**
     * Returns the class defined for the type variable
     * of the given name.
     * @param clazz the class
     * @param genericClazz the generic class or interface to check the type for
     * @param name the name of the type variable
     * @param recursive whether or not to recurse up the
     * object's inheritance hierarchy.
     * @return the class
     */
    public static Class<?> getTypeVariableClassByName(
        Class<?> clazz, Type genericClazz, String name, Boolean recursive) {

        // we hit the end of the line here :)
        if (clazz==null || clazz.equals(Object.class)) {
            return null;
        }

        // loop through all of the types implemented
        for (ParameterizedType pType : getGenericTypes(clazz)) {

            // do all of them, or one of them
            if (genericClazz==null || genericClazz.equals(pType.getRawType())) {

                // get super class type variables
                TypeVariable<?>[] typeVars = getGenericTypeParameters(clazz, pType.getRawType());
                for (int i=0; i<typeVars.length; i++) {
                    if ((genericClazz==null || genericClazz.equals(typeVars[i].getGenericDeclaration()))
                        && typeVars[i].getName().equals(name)) {

                        // get the type
                        Type type = pType.getActualTypeArguments()[i];

                        if (Class.class.isAssignableFrom(type.getClass())) {
                            return (Class<?>)type;
                        } else if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                            return (Class<?>)((ParameterizedType) type).getRawType();
                        }
                    }
                }

            }

        }

        // none found
        return (recursive)
            ? getTypeVariableClassByName(clazz.getSuperclass(), genericClazz, name, recursive) : null;
    }

    /**
     * Returns all of the {@link ParameterizedType}s implemented
     * by the given class.  If none are implemented then an array
     * of zero length is returned.
     * @param clazz the class
     * @return an array of ParameterizedType
     */
    public static ParameterizedType[] getGenericTypes(Class<?> clazz) {
        List<ParameterizedType> types = new ArrayList<ParameterizedType>();

        // add superclass
        if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
            types.add((ParameterizedType)clazz.getGenericSuperclass());
        }

        // add interfaces
        for (Type type : clazz.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                types.add((ParameterizedType)type);
            }
        }

        // return list
        return types.toArray(new ParameterizedType[0]);
    }

    /**
     * Returns all of the {@link TypeVariables}s implemented
     * by the given class.  If none are implemented then an array
     * of zero length is returned.
     * @param clazz the class
     * @return an array of TypeVariable
     */
    public static TypeVariable<?>[] getGenericTypeParameters(Class<?> clazz) {
        return getGenericTypeParameters(clazz);
    }

    /**
     * Returns all of the {@link TypeVariables}s implemented
     * by the given class.  If none are implemented then an array
     * of zero length is returned.
     * @param clazz the class
     * @param genericClazz the generic class or interface to return
     * the TypeVariables from
     * @return an array of TypeVariable
     */
    public static TypeVariable<?>[] getGenericTypeParameters(Class<?> clazz, Type genericClazz) {
        List<TypeVariable<?>> vars = new ArrayList<TypeVariable<?>>();

        // add superclass
        for (TypeVariable<?> var : clazz.getSuperclass().getTypeParameters()) {
            if (genericClazz==null || genericClazz.equals(var.getGenericDeclaration())) {
                vars.add(var);
            }
        }

        // add interfaces
        for (Class<?> iface : clazz.getInterfaces()) {
            for (TypeVariable<?> var : iface.getTypeParameters()) {
                if (genericClazz==null || genericClazz.equals(var.getGenericDeclaration())) {
                    vars.add(var);
                }
            }
        }

        // return list
        return vars.toArray(new TypeVariable<?>[0]);
    }

}

