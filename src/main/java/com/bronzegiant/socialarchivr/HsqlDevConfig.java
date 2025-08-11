package com.bronzegiant.socialarchivr;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class HsqlDevConfig {

	// Start TCP listener on 9002 so DB tools like DBeaver can view the embedded DB...
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server hsqlServer() throws Exception {
        HsqlProperties props = new HsqlProperties();
        // Same DB as Spring Boot uses (in-memory)
        props.setProperty("server.database.0", "mem:socialarchivr");
        props.setProperty("server.dbname.0", "socialarchivr");
        props.setProperty("server.port", "9002");
        props.setProperty("server.remote_open", "true"); // allow remote connections

        Server server = new Server();
        server.setProperties(props);
        return server;
    }
}

