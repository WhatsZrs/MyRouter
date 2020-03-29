package com.zrs.router.model;

/**
 * @author zhang
 * @date 2020/3/27 0027
 * @time 17:36
 * @describe TODO
 */
public enum RouteType {
    //可以扩展
    ACTIVITY(0, "android.app.Activity"),
    SERVICE(1, "android.app.Service"),
    PROVIDER(2, "com.hzq.routerapi.facade.template.IProvider"),
    CONTENT_PROVIDER(-1, "android.app.ContentProvider"),
    FRAGMENT(-1, "android.app.Fragment"),
    UNKNOWN(-1, "Unknown route type");

    int id;
    String className;

    public int getId() {
        return id;
    }

    public RouteType setId(int id) {
        this.id = id;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public RouteType setClassName(String className) {
        this.className = className;
        return this;
    }

    RouteType(int id, String className) {
        this.id = id;
        this.className = className;
    }

    public static RouteType parse(String name) {
        for (RouteType routeType : RouteType.values()) {
            if (routeType.getClassName().equals(name)) {
                return routeType;
            }
        }

        return UNKNOWN;
    }
}
