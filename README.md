# Spring Boot FastDFS Starter

Usage:

Add dependency **fastdfs-spring-boot-starter** to **pom.xml** 

```xml
		<dependency>
			<groupId>com.luhuiguo</groupId>
			<artifactId>fastdfs-spring-boot-starter</artifactId>
			<version>0.1.0</version>
		</dependency>
```

Config **application.yml**

```yml
fdfs:
  connect-timeout: 2000
  so-timeout: 3000
  tracker-list:
    - 10.1.5.85:22122
    - 10.1.5.86:22122
    
```

Inject **FastFileStorageClient**

```java
  @Autowired
  private FastFileStorageClient storageClient;
```
