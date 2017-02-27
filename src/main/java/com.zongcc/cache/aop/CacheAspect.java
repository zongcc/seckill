package com.zongcc.cache.aop;

import com.zongcc.cache.annotation.CacheRemove;
import com.zongcc.cache.annotation.CacheStore;
import com.zongcc.cache.memcache.CacheClient;
import com.zongcc.cache.memcache.CacheConstant;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Cache aspect, to deal with cache annotation and cache
 */
@Aspect
@Order(2)
public class CacheAspect {

//    private static Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Resource
    private CacheClient cacheClient;

    /**
     * CacheStore aspect
     *
     * @param joinPoint
     * @param cacheStore
     * @return
     * @throws Throwable
     */
    @Around("@annotation(cacheStore)")
    public Object cacheStoreAround(ProceedingJoinPoint joinPoint, CacheStore cacheStore) throws Throwable {
        String key = getCacheKey(joinPoint,cacheStore.key());
        //get cache value
        Object cachedValue = cacheClient.get(key);
        //if cache key value exist
        if( null == cachedValue ){
            cachedValue = joinPoint.proceed();
            //if return value is null
            if( null != cachedValue ){
                //全部设为一小时缓存
                //get expire date
//                if( cacheStore.expireValue()!=-1 ){
//                    int seconds = 0;
//                    switch (cacheStore.timeUnit()) {
//                    case SECONDS: seconds =  cacheStore.expireValue(); break;
//                    case HOURS : seconds =  cacheStore.expireValue() * 60 *60; break;
//                    case DAYS : seconds =  cacheStore.expireValue()*60*60*24; break;
//                    default : seconds =  cacheStore.expireValue()*60; break;
//                    }
//                    cacheClient.set(key, cachedValue, seconds);
//                } else {
                //如果不设置缓存失效时间，默认为一小时
                int seconds = 0;
                seconds =  1 * 60 * 60;
                cacheClient.set(key, cachedValue,seconds);
//                }
            }
        }
        return cachedValue;
    }

    /**
     * CacheRemove aspect
     *
     * @param joinPoint
     * @param cacheRemove
     * @throws Throwable
     */
    @Around("@annotation(cacheRemove)")
    public Object cacheRemoveAround(ProceedingJoinPoint joinPoint, CacheRemove cacheRemove) throws Throwable {
        Object returnValue = joinPoint.proceed();

        //delete key
        String[] keys = cacheRemove.keys();
        if(null!=keys && keys.length>0){
            for( String keyExpression : keys ){
                if( !org.apache.commons.lang.StringUtils.isBlank(keyExpression) ){
                    cacheClient.delete(getCacheKey(joinPoint,keyExpression));
                }
            }
        }
        return returnValue;
    }

    /**
     * Deal with SpEL Key, if SpEl is nullthen return default key
     *
     * @param joinPoint
     * @param keyExpression
     * @return
     * @throws NotFoundException
     */
    private String getCacheKey(ProceedingJoinPoint joinPoint,String keyExpression) throws NotFoundException{
        String key = "";
        if( org.apache.commons.lang.StringUtils.isBlank(keyExpression)){
            key = getDefaultCacheKey(joinPoint);
        } else {
            //method params name array
            String[] paramNames = getParamNames(joinPoint);

            //deal with Spring SpEL
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keyExpression);
            EvaluationContext context = new StandardEvaluationContext();
            Object[] args = joinPoint.getArgs();
            for( int i=0; i<args.length; i++ ){
                Object obj = args[i];
                String paramName = paramNames[i];
                context.setVariable(paramName, obj);
            }
            key = expression.getValue(context, String.class);
        }
        return key;
    }

    /**
     * Default cache key
     * class full name : method name : method parameter type : parameter value
     *
     * @return
     */
    private String getDefaultCacheKey(ProceedingJoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
        stringBuilder.append(methodName);
        stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
        for( int i=0; i<args.length; i++ ){
            stringBuilder.append(args[i].getClass().getName());
            stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
            stringBuilder.append(args[i].toString());
            if( i!= args.length-1 ){
                stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
            }
        }
//        logger.info("DefaultCacheKey:"+stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * Get method params name key
     * class full name:method name:method parameter type
     *
     * @param joinPoint
     * @return
     */
    private String getParamNamesKey(ProceedingJoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
        stringBuilder.append(methodName);
        stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
        for( int i=0; i<args.length; i++ ){
            stringBuilder.append(args[i].getClass().getName());
            if( i!= args.length-1 ){
                stringBuilder.append(CacheConstant.CACHE_KEY_SEPARATOR);
            }
        }
//        logger.info("ParamNamesKey:"+stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * Get method params name array
     *
     * @param joinPoint
     * @return
     * @throws NotFoundException
     */
    private String[] getParamNames(ProceedingJoinPoint joinPoint) {
        String[] paramNames = null;
        CtClass cc = null;
        CtClass[] parameterTypes = null;
        Set<CtClass> parameterTypeName = null;
        try{
            String key = getParamNamesKey(joinPoint);

            paramNames = CacheConstant.METHOD_ARGS_NAME.get(key);
            if( null != paramNames ){
                return CacheConstant.METHOD_ARGS_NAME.get(key);
            }
            String targetName = joinPoint.getTarget().getClass().getName();
            ClassPool c = ClassPool.getDefault();
            cc =c.get(targetName);
            CtMethod cm = null;
            Object[] args = joinPoint.getArgs();
            if( null!=args && args.length>0 ){
                parameterTypes = new CtClass[args.length];
                parameterTypeName = new HashSet<CtClass>();
                for( int i=0; i<args.length; i++ ){
                    Object obj = args[i];
                    parameterTypes[i] = ClassPool.getDefault().get(obj.getClass().getName());
                    parameterTypeName.add(ClassPool.getDefault().get(obj.getClass().getName()));
                }
            }
            cm = cc.getDeclaredMethod(joinPoint.getSignature().getName(),parameterTypes);

            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

            int begin = 0;
            int count = 0;
            for (int i = 0; i < attr.tableLength(); i++){
                if (attr.variableName(i).equals("this")){
                    begin = i;
                    break;
                }
            }

            paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//            for (int i = 0; i < paramNames.length; i++){
//                paramNames[i] = attr.variableName(i + pos);
//            }
            for (int i = begin+1; i <= begin+paramNames.length; i++){
                paramNames[count] = attr.variableName(i);
                count++;
            }

            CacheConstant.METHOD_ARGS_NAME.putIfAbsent(key, paramNames);
        } catch (Exception e) {

        } finally {
            /*if( null!=parameterTypes && parameterTypes.length>0 ){
                for( int j=0; j<parameterTypes.length; j++ ){
                	if(null!=parameterTypes[j]){
                		parameterTypes[j].detach();
                	}

                }
            }*/

            if( null!=parameterTypeName && parameterTypeName.size()>0 ){
                Iterator<CtClass> it = parameterTypeName.iterator();
                while(it.hasNext()){
                    CtClass cctemp = it.next();
                    if(null!=cctemp){
                        cctemp.detach();
                    }
                }
            }
            if( null!=cc ){
                cc.detach();
            }
        }
        return paramNames;
    }



}
