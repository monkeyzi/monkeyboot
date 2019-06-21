package com.monkeyzi.mboot.common.core.holder;
/**
 * @author 高yg
 * @date 2019/6/21 10:17
 * @description  基于ThreadLocal存储用户的上下文信息--holder模式线程安全的单例
 **/
public class UserContextHolder<T> {

    private ThreadLocal<T>  threadLocal;

    private UserContextHolder(){
        this.threadLocal=new ThreadLocal<T>();
    }


    public static UserContextHolder getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static UserContextHolder instance=new UserContextHolder();
    }

    /**
     * 添加用户信息
     * @param obj
     */
    public void  setContext(T obj){
         this.threadLocal.set(obj);
    }

    /**
     * 获取用户信息
     * @return
     */
    public Object getContext(){
         return   this.threadLocal.get();
    }

    /**
     * 移除用户信息
     */
    public void  remove(){
        this.threadLocal.remove();
    }



}
