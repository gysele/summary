package org.wxm.core.mybatis;

import java.io.IOException;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <b>标题: </b>通过通配符方式配置typeAliasesPackage <br/>
 * <b>描述: </b> <br/>
 * <b>作者: </b>Cybele 398600198@qq.com <br/>
 * <b>时间: </b>2015-2-23 下午11:56:45 <br/>
 * <b>版本: </b>V1.0
 */
public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass()); // 简单日记门面
    private final String DEFAULT_RESOURCE_PATTERN = "/**/*.class"; // Java编译后文件.class后缀

    /**
     * 
     * <b>标题: </b>使mybatis配置文件支持通配符 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-15 下午6:51:52 <br/>
     * <b>修改记录: </b>
     * 
     * @param typeAliasesPackage
     */
    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        try {
            String allTypeAliasesPackage = this.getPackageWildcards(typeAliasesPackage);
            if (StringUtils.hasLength(allTypeAliasesPackage)) {
                super.setTypeAliasesPackage(allTypeAliasesPackage);
            } else {
                logger.debug("参数typeAliasesPackage:" + typeAliasesPackage + "，未找到任何包");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPlugins(Interceptor[] plugins) {
        super.setPlugins(plugins);
    }

    /**
     * 
     * <b>标题: </b>根据指定的、包含通配符的包，获取所有包 <br/>
     * <b>描述: </b> 包路径以"/"隔开<br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>Cybele 398600198@qq.com <br/>
     * <b>时间: </b>2015-2-24 下午1:27:19 <br/>
     * <b>修改记录: </b>
     * 
     * @param typeAliasesPackage
     * @return
     * @throws IOException
     */
    private String getPackageWildcards(String typeAliasesPackage) throws IOException {
        if (!StringUtils.hasLength(typeAliasesPackage)) {
            logger.debug("映射别名的包路径为空！！！");
            return null;
        }

        StringBuilder resultPackages = new StringBuilder(); // 指定的所有包
        String[] packages = StringUtils.split(typeAliasesPackage, ","); // 分离多个指定包

        if (packages == null || packages.length <= 0) {
            logger.debug("映射别名的包路径不存在或路径错误！！！");
            return null;
        }

        logger.debug("[START]根据指定的、包含通配符的包，扫描所有包");
        logger.debug("源包配置为：{}", typeAliasesPackage);

        try {
            ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
            for (String pkg : packages) {
                logger.debug("[1]转换前包路径为：{}", pkg);
                pkg = String.format("%s%s%s", ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, ClassUtils.convertClassNameToResourcePath(pkg), DEFAULT_RESOURCE_PATTERN);
                logger.debug("[2]转换后包路径为：{}", pkg);
                // 将加载多个模式匹配的Resource
                Resource[] resources = resolver.getResources(pkg);
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            if (resultPackages != null && resultPackages.length() > 0) {
                                // 若resultPackages不为空，则添加“,”
                                resultPackages.append(",");
                            }
                            // 将获取的包路径加入结果变量（指定的所有包）中
                            resultPackages.append(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        // 包名获取结果
        // System.out.println("包名获取结果\n" + resultPackages.toString());
        logger.debug("[END]根据指定的、包含通配符的包，扫描所有包");
        return resultPackages.toString();
    }
}
