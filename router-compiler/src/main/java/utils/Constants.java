package utils;

/**
 * @author zhang
 * @date 2020/3/26 0026
 * @time 16:07
 * @describe TODO
 */
public class Constants {


    // Options of processor
    public static final String KEY_MODULE_NAME = "MODULE_NAME";

    public static final String ANNOTATION_ROUTER_NAME = "com.zrs.router.annotation.Route";
    private static final String API_PACKAGE_NAME = "com.zrs.router.api";
    public static final String TEMPLITE_PACKAGE = ".templete";
    public static final String IPROVIDER = API_PACKAGE_NAME + TEMPLITE_PACKAGE + ".IProvider";
    public static final String IPROVIDER_GROUP = API_PACKAGE_NAME + TEMPLITE_PACKAGE + ".IProviderGroup";
    public static final String IROUTE_GROUP = API_PACKAGE_NAME + TEMPLITE_PACKAGE + ".IRouteGroup";
    public static final String IROUTE_ROOT = API_PACKAGE_NAME + TEMPLITE_PACKAGE + ".IRouteRoot";


    // System interface
    public static final String ACTIVITY = "android.app.Activity";
    public static final String FRAGMENT = "android.app.Fragment";
    public static final String FRAGMENT_V4 = "android.support.v4.app.Fragment";
    public static final String SERVICE = "android.app.Service";
    public static final String PARCELABLE = "android.os.Parcelable";
    //path name相关
    public static final String PROJECT = "MyRouter";
    public static final String PREFIX_OF_LOGGER = PROJECT + "::Compiler";
    public static final String SEPARATOR = "$$";
    public static final String METHOD_LOAD_INTO = "loadInto";
    public static final String NAME_OF_GROUP = PROJECT + SEPARATOR + "Group" + SEPARATOR;
    public static final String NAME_OF_PROVIDER = PROJECT + SEPARATOR + "Providers" + SEPARATOR;
    public static final String NAME_OF_ROOT = PROJECT + SEPARATOR + "Root" + SEPARATOR;
    public static final String PACKAGE_OF_GENERATE_FILE = "com.zrs.android.router.routes"; //生成class的包名
    public static final String WARNING_TIPS = "DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY ROUTERMANAGER.";

}