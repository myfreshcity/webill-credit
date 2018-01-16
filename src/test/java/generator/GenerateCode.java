package generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.ConfigGenerator;
import com.webill.framework.annotations.IdType;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class GenerateCode {

    /* 生成代码包名 */
    private static final String PACKAGE_NAME = "com.webill.core";

    public static void main(String[] args) {

        /* 获取 JDBC 配置文件 */
        Properties props = getProperties();

        /* 配置 Mybatis-Plus 代码生成器 */
        ConfigGenerator cg = new ConfigGenerator();

        /* Mysql 数据库相关配置 */
        cg.setDbDriverName("com.mysql.jdbc.Driver");
        cg.setDbUrl(props.getProperty("jdbc_url"));
        cg.setDbUser(props.getProperty("jdbc_username"));
        cg.setDbPassword(props.getProperty("jdbc_password"));

        /* 设置数据库前缀（例如`mp_user`生成实体类，false 为 MpUser.java , true 为 User.java）*/
        cg.setDbPrefix(true);

         /*
         * true 表示数据库设置全局下划线命名规则，默认 false
		 * ------------------------------------------------------------------------------------
		 * 【 开启该设置实体可无 @TableId(value = "test_id") 字段映射，启动配置对应也要开启 true 设置 】
		 */
        cg.setDbColumnUnderline(true);

        /*
         * 表主键 ID 生成类型, 自增该设置无效。
		 * <p>
		 * IdType.AUTO 			数据库ID自增
		 * IdType.INPUT			用户输入ID
		 * IdType.ID_WORKER		全局唯一ID，内容为空自动填充（默认配置）
		 * IdType.UUID			全局唯一ID，内容为空自动填充
		 * </p>
		 */
        cg.setIdType(IdType.AUTO);

        /* 生成文件保存位置 */
        cg.setSaveDir(getRootPath(props.getProperty("project_path")) + "/src/test/java/");
        cg.setTableName(props.getProperty("table_name")); //指定表名称
        cg.setEntityName(props.getProperty("entity_name")); //实体类名称。默认为表名称

        /* 生成代码包路径 */
        cg.setEntityPackage(PACKAGE_NAME + ".model"); //entity 实体包路径
        cg.setMapperPackage(PACKAGE_NAME + ".service.mapper"); //mapper 映射文件路径
        cg.setXmlPackage("mybatis"); //xml层路径

        cg.setServicePackage(PACKAGE_NAME + ".service"); //service 层路径
        //cg.setServiceImplPackage(PACKAGE_NAME + ".service"); //serviceImpl包路径，默认为service/impl
        //cg.setSuperService(PACKAGE_NAME + ".service.BaseDao"); //父类包路径名称
        //cg.setSuperServiceImpl(PACKAGE_NAME + ".service"); //实现父类包路径名称

        //cg.setMapperName("User"); //自定义 mapper 名称
        //cg.setMapperXMLName("User"); //自定义 xml 名称
        //cg.setServiceName("User"); //自定义 service 名称
        //cg.setServiceImplName("User"); //自定义 serviceImp 名称

        //cg.setColumnConstant(true);  //【实体】是否生成字段常量（默认 false）
        //cg.setBuliderModel(true);    //【实体】是否为构建者模型（默认 false）
        cg.setFileOverride(true);     // 是否覆盖当前已有文件

        /* 生成代码 */
        AutoGenerator.run(cg);
    }

    /**
     * 获取配置文件
     *
     * @return 配置Props
     */
    private static Properties getProperties() {
        // 读取配置文件
        Resource resource = new ClassPathResource("/gencode.properties");
        Properties props = new Properties();
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 获取项目根路径
     *
     * @return 项目路径
     */
    private static String getRootPath(String projectPath) {
        File directory = new File(projectPath);// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseFile;
    }
}
