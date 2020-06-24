package com.twitchbot.commandadapter.config;

import com.twitchbot.commandadapter.database.CommandDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class JdbiConfig {

    @Bean
    public Jdbi jdbi(DataSource ds) {
        Jdbi jdbi = Jdbi.create(new TransactionAwareDataSourceProxy(ds));
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public CommandDao commandDao(Jdbi jdbi) {
        return jdbi.onDemand(CommandDao.class);
    }

}