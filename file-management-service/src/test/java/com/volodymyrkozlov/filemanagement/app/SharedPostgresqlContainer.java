package com.volodymyrkozlov.filemanagement.app;

import org.testcontainers.containers.PostgreSQLContainer;

public class SharedPostgresqlContainer extends PostgreSQLContainer<SharedPostgresqlContainer> {
    private static SharedPostgresqlContainer sharedPostgresqlContainer;
    private static final String IMAGE_VERSION = "postgres:14.1";

    private SharedPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    private SharedPostgresqlContainer(final String imageVersion) {
        super(imageVersion);
    }

    public static SharedPostgresqlContainer getInstance(final String imageVersion) {
        if (sharedPostgresqlContainer == null) {
            sharedPostgresqlContainer = new SharedPostgresqlContainer(imageVersion);
        }

        return sharedPostgresqlContainer;
    }

    public static SharedPostgresqlContainer getInstance() {
        if (sharedPostgresqlContainer == null) {
            sharedPostgresqlContainer = new SharedPostgresqlContainer();
        }

        return sharedPostgresqlContainer;
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
