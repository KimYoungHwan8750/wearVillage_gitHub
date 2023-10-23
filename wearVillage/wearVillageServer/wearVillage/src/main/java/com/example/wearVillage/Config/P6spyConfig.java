package com.example.wearVillage.Config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
    }
}

// JPA 사용시 애노테이션 추가

//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@DataJpaTest(showSql = false)
//@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(P6spyConfig.class)
//public @interface CustomDataJpaTest {
//}

