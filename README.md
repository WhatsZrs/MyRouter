# MyRouter
组件化 路由设计
目前只支持activity跳转 获取fragment等功最近会更新

                   实现思路
									 
						 
利用编译注解（apt）的特性，新增一个注解，给每个需要通过url打开的activity加上此注解。在注解处理器中获取所有被注解的类，动态生成映射关系表，然后在app启动时把所生成的映射关系load到内存（其实就是读到一个map中）合着相当维护了两个map 一个是分组的path对应的Map<string,set<RouteMeta>groups> 一个是Map<String,RouteMeta> path对应详细的class信息，这样设计主要是为了load进内存的时候，实现了一个懒加载的功能！具体文档 最近更新篇文章。
  


             
