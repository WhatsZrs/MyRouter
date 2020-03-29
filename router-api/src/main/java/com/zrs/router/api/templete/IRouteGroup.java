package com.zrs.router.api.templete;

import com.zrs.router.model.RouteMeta;

import java.util.Map;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 1:21
 * @describe TODO
 */
public interface IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas);
}
