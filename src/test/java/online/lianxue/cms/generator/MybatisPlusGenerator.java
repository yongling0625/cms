package online.lianxue.cms.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MybatisPlusGenerator {

    String dbUrl = "jdbc:mysql://192.168.10.213:3306/cms";
    String username = "root";
    String password = "gaodeyang";
    String drivername = "com.mysql.jdbc.Driver";
    String[] tablePrefix = new String[]{"sys_"};
    String packageName = "online.lianxue.cms";
    String moduleName = "system";
    String superControllerClass = "online.lianxue.cms.common.controller.BaseController";
    String superEntityClass = "online.lianxue.cms.common.entity.CreateUpdateEntity";
    String[] superEntityColumns = new String[]{"id", "create_by", "create_time", "last_update_by", "last_update_time"};

    @Test
    public void generateCode() {
//        generateByTables(packageName, moduleName, "t_sys_log");
        generateByTables(packageName, moduleName, "sys_menu");
    }

    public void generateByTables(String packageName, String moduleName, String... tableNames) {

        // 全局配置
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        config.setAuthor("lianxue")
                .setServiceName("%sService")
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(false)
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setOpen(false);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(drivername);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName)
                .setParent(packageName);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass(superEntityClass)
                .setSuperEntityColumns(superEntityColumns)
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setSuperControllerClass(superControllerClass)
                .setInclude(tableNames)
                .setControllerMappingHyphenStyle(true)
//                .setTablePrefix(tablePrefix)
        ;


        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setCfg(cfg)
                .setStrategy(strategyConfig)
                .setPackageInfo(pc)
                .setTemplate(new TemplateConfig().setXml(null))
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
