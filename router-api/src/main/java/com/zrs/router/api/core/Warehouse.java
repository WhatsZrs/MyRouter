package com.zrs.router.api.core;

import com.zrs.router.api.templete.IProvider;
import com.zrs.router.api.templete.IRouteGroup;
import com.zrs.router.model.RouteMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 13:43
 * @describe TODO
 */
public class Warehouse {
    // Cache route and metas
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>(); //path对应分组映射 Map<String,set<RouteMeta>>
    static Map<String, RouteMeta> routes = new HashMap<>(); //path对应的详细单个包装类routes
    // Cache provider
    static Map<Class, IProvider> providers = new HashMap<>();       //存储加载后的实例对象
    static Map<String, RouteMeta> providersIndex = new HashMap<>(); //存储映射表信息

    static void clear() {
        providers.clear();
        providersIndex.clear();
    }
}
