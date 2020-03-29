package com.zrs.router.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.zrs.router.annotation.Route;
import com.zrs.router.model.RouteMeta;
import com.zrs.router.model.RouteType;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import utils.Constants;
import utils.LogUtil;
import utils.EmptyUtils;

import static javax.lang.model.element.Modifier.PUBLIC;
import static utils.Constants.IPROVIDER_GROUP;
import static utils.Constants.IROUTE_GROUP;
import static utils.Constants.IROUTE_ROOT;
import static utils.Constants.METHOD_LOAD_INTO;
import static utils.Constants.NAME_OF_GROUP;
import static utils.Constants.NAME_OF_ROOT;
import static utils.Constants.PACKAGE_OF_GENERATE_FILE;
import static utils.Constants.WARNING_TIPS;

/**
 * @author zhang
 * @date 2020/3/26 0028
 * @time 21:34
 * @describe Route注解解析器
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(Constants.ANNOTATION_ROUTER_NAME) //
@SupportedSourceVersion(SourceVersion.RELEASE_8) //
@SupportedOptions(Constants.KEY_MODULE_NAME)//
public class RouterProcessorer extends AbstractProcessor {
    private Filer mFiler;
    private Types types;
    private LogUtil logger;
    private Elements elements;
    private Map<String, Set<RouteMeta>> groupMap = new HashMap<>();  //收集分组
    private Map<String, String> rootMap = new TreeMap<>();
    private String moduleName = "app"; //默认app

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        logger = new LogUtil(processingEnvironment.getMessager());
        elements = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
        Map<String, String> options = processingEnvironment.getOptions();
        if (options != null) {
            moduleName = options.get(Constants.KEY_MODULE_NAME);
        }
        logger.info("路由初始化");
    }

    //核心方法 集所有被注解的类，生成映射关系
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (!set.isEmpty()) {
            Set<? extends Element> elementsSet = roundEnvironment.getElementsAnnotatedWith(Route.class);
            logger.info("寻找Router注解的个数" + set.size() + set.toString());
            try {


                paseRoter(elementsSet);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return true;

        }
        return false;
    }

    //面向对象思想 routeElements
    private void paseRoter(Set<? extends Element> routeElements) throws IOException {
        if (!EmptyUtils.isCollectionEmpty(routeElements)) {
            rootMap.clear();
            //获取activity等类型..=
            TypeMirror type_activity = elements.getTypeElement(Constants.ACTIVITY).asType();

            //interface of route
            TypeElement type_IRouteGroup = elements.getTypeElement(IROUTE_GROUP);
            TypeElement type_IProvider_group = elements.getTypeElement(IPROVIDER_GROUP);

            //classname
            ClassName routeMetaCn = ClassName.get(RouteMeta.class);
            ClassName routeTypeCn = ClassName.get(RouteType.class);

            for (Element element : routeElements) {
                TypeMirror typeMirror = element.asType();

                Route route = element.getAnnotation(Route.class);
                RouteMeta routeMeta;
                if (types.isSubtype(typeMirror, type_activity)) ;
                {
                    logger.info("寻找到被Router注解的activity类型" + typeMirror.toString());
                    routeMeta = new RouteMeta(route, element, RouteType.ACTIVITY, null);
                }

                categories(routeMeta);
            }
            //  logger.info("entry.getKey().." + groupMap.toString());
            //生成java类来存放这些信息：
            //用javapoet生成文件相关
            /**
             * Build input type, format as :
             * ``` Map<String,Class<? extends IRouteGroup>>```
             */
            ParameterizedTypeName inputMapTyppeOfRoot = ParameterizedTypeName.get(
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ParameterizedTypeName.get(ClassName.get(Class.class),
                            WildcardTypeName.subtypeOf(ClassName.get(type_IRouteGroup))));

            /**
             * Map<String,RouteMeta></ 构造参数类型
             */
            ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                    ClassName.get(Map.class)
                    , ClassName.get(String.class)
                    , ClassName.get(RouteMeta.class));


            /**
             * Build input params name. 构造参数名称
             */
            ParameterSpec rootParamSpec = ParameterSpec.builder(inputMapTyppeOfRoot, "routes").build();
            ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup, "atlas").build();
            ParameterSpec providerParamsSpec = ParameterSpec.builder(inputMapTypeOfGroup, "providers").build();

            /*
              Build method : 'loadInto'
             */
            MethodSpec.Builder loadIntoMethodOfRootBuilder = MethodSpec.methodBuilder(METHOD_LOAD_INTO)
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .addParameter(rootParamSpec);
            for (Map.Entry<String, Set<RouteMeta>> entry : groupMap.entrySet()) {
                //  logger.info(entry.toString());
                //  javapoet API 生成Method   @Override public void loadInto(Map<String, RouteMeta> atlas){...}
                String groupName = entry.getKey();
                MethodSpec.Builder loadIntoMethodOfGroupBuilder = MethodSpec.methodBuilder(METHOD_LOAD_INTO)
                        .addModifiers(PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(groupParamSpec);
                Set<RouteMeta> routeMetas = entry.getValue();
                for (RouteMeta routeMeta : routeMetas) {
                    logger.info(routeMeta.toString());
                    ClassName className = ClassName.get((TypeElement) routeMeta.getRawType());
                    StringBuilder mapBodyBuilder = new StringBuilder();
                    Map<String, Integer> paramsType = routeMeta.getParamsType();
                    if (!EmptyUtils.isCollectionEmpty((Collection) paramsType)) {
                        logger.info("..........EmptyUtils");
                        for (Map.Entry<String, Integer> integerEntry : paramsType.entrySet()) {
                            logger.info("mapBodyBuilder，，" + mapBodyBuilder);
                            mapBodyBuilder.append("put(\"").append(integerEntry.getKey()).append("\",").append(integerEntry.getValue()).append("); ");

                        }
                    }

                    String mapBody = mapBodyBuilder.toString();
                    logger.info(mapBody.toString());
                    //添加方法体
                    loadIntoMethodOfGroupBuilder.addStatement(
                            "atlas.put($S," +
                                    "$T.build($T." + routeMeta.getType() + ",$T.class,$S,$S," + (EmptyUtils.isEmpty(mapBody) ? null : ("new java.util.HashMap<String, Integer>(){{" + mapBodyBuilder.toString() + "}}")) + ", " + routeMeta.getPriority() + "," + routeMeta.getExtra() + "))",
                            routeMeta.getPath(),
                            routeMetaCn,
                            routeTypeCn,
                            className,
                            routeMeta.getPath().toLowerCase(),
                            routeMeta.getGroup().toLowerCase());

                }
                //生成 groups
                String groupFileName = NAME_OF_GROUP + groupName;
                JavaFile.builder(PACKAGE_OF_GENERATE_FILE,
                        TypeSpec.classBuilder(groupFileName)
                                .addJavadoc(WARNING_TIPS)
                                .addSuperinterface(ClassName.get(type_IRouteGroup))
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(loadIntoMethodOfGroupBuilder.build())
                                .build()
                ).build().writeTo(mFiler);

                logger.info("生成 group: " + groupName);
                rootMap.put(groupName, groupFileName);
            }

            if (rootMap != null) {
                for (Map.Entry<String, String> entry : rootMap.entrySet()) {
                    loadIntoMethodOfRootBuilder.addStatement("routes.put($S, $T.class)", entry.getKey(), ClassName.get(PACKAGE_OF_GENERATE_FILE, entry.getValue()));
                }
            }
            // Write root meta into disk.
            String rootFileName = NAME_OF_ROOT + moduleName;
            JavaFile.builder(PACKAGE_OF_GENERATE_FILE,
                    TypeSpec.classBuilder(rootFileName)
                            .addJavadoc(WARNING_TIPS)
                            .addSuperinterface(ClassName.get(elements.getTypeElement(IROUTE_ROOT)))
                            .addModifiers(PUBLIC)
                            .addMethod(loadIntoMethodOfRootBuilder.build())
                            .build()
            ).build().writeTo(mFiler);

            logger.info("生成 root, name is " + rootFileName );
        }


    }

    //@descripe 首先根据当前url分组去groupMap中查找，也就是看是否有该分组，如果有取出对应的RouterMeta集合，把本次生成的routeMeta放进去没有就新存一个集合
    private void categories(RouteMeta routeMeta) {
        if (routeVerify(routeMeta)) {
            logger.info("开始分类, group = " + routeMeta.getGroup() + ", path = " + routeMeta.getPath());
            Set<RouteMeta> routeMetas = groupMap.get(routeMeta.getGroup());
            //group是一个全局变量 用来存储分组的routemeta数据
            if (EmptyUtils.isCollectionEmpty(routeMetas)) {
                //当前分组不存在的情况 新建分组
                Set<RouteMeta> routeMetas1 = new TreeSet<>(new Comparator<RouteMeta>() {
                    @Override
                    public int compare(RouteMeta routeMeta, RouteMeta t1) {
                        try {
                            return routeMeta.getPath().compareTo(t1.getPath());
                        } catch (Exception e) {
                            logger.error("发生了错误" + e.getMessage());
                            return 0;
                        }
                    }
                });
                routeMetas1.add(routeMeta);
                groupMap.put(routeMeta.getGroup(), routeMetas1);
            } else {
                routeMetas.add(routeMeta);
            }
        } else {
            logger.warning("Routemeta 验证出现错误, group is " + routeMeta.getGroup());
        }

    }

    /**
     * 验证 routemeta
     *
     * @param meta raw meta
     */
    private boolean routeVerify(RouteMeta meta) {
        String path = meta.getPath();

        if (EmptyUtils.isEmpty(path) || !path.startsWith("/")) {   // 路径要以 '/' 开头 而且不能为空!
            return false;
        }

        if (EmptyUtils.isEmpty(meta.getGroup())) { // Use default group(the first word in path)
            try {
                String defaultGroup = path.substring(1, path.indexOf("/", 1));
                if (EmptyUtils.isEmpty(defaultGroup)) {
                    return false;
                }

                meta.setGroup(defaultGroup);
                return true;
            } catch (Exception e) {
                logger.error("Failed to extract default group! " + e.getMessage());
                return false;
            }
        }

        return true;
    }


//
//    @Override
//    public Set<String> getSupportedOptions() {
//        return Collections.singleton(Constants.KEY_MODULE_NAME);
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.RELEASE_8;
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return Collections.singleton(Constants.ANNOTATION_ROUTER_NAME);
//    }
}
