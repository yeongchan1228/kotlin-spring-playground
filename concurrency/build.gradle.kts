dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("com.mysql:mysql-connector-j")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.redisson:redisson-spring-boot-starter:3.23.4") // redisson 라이브러리
}
