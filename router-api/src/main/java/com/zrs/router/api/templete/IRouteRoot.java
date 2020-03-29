package com.zrs.router.api.templete;

import java.util.Map;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 1:21
 * @describe TODO
 */
public interface IRouteRoot {
    public void loadInto(Map<String, Class<? extends IRouteGroup>> routes);
}
