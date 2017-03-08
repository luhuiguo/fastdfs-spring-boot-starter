package com.luhuiguo.fastdfs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.ConnectionPoolConfig;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.PooledConnectionFactory;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.service.AppendFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultAppendFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;

@Configuration
@EnableConfigurationProperties(FdfsProperties.class)
public class FdfsAutoConfiguration {

  private final FdfsProperties properties;

  public FdfsAutoConfiguration(FdfsProperties properties) {
    super();
    this.properties = properties;
  }

  @Bean
  public PooledConnectionFactory pooledConnectionFactory() {
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    pooledConnectionFactory.setSoTimeout(properties.getSoTimeout());
    pooledConnectionFactory.setConnectTimeout(properties.getConnectTimeout());
    return pooledConnectionFactory;
  }


  @Bean
  public ConnectionPoolConfig connectionPoolConfig() {
    ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
    //connectionPoolConfig.setJmxEnabled(false);
    return connectionPoolConfig;
  }

  @Bean
  public FdfsConnectionPool fdfsConnectionPool(PooledConnectionFactory pooledConnectionFactory,
      ConnectionPoolConfig connectionPoolConfig) {
    FdfsConnectionPool pool =  new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
    return pool;
  }

  @Bean
  public TrackerConnectionManager trackerConnectionManager(FdfsConnectionPool fdfsConnectionPool) {
    return new TrackerConnectionManager(fdfsConnectionPool, properties.getTrackerList());
  }

  @Bean
  public TrackerClient trackerClient(TrackerConnectionManager trackerConnectionManager) {
    return new DefaultTrackerClient(trackerConnectionManager);
  }

  @Bean
  public ConnectionManager connectionManager(FdfsConnectionPool fdfsConnectionPool) {
    return new ConnectionManager(fdfsConnectionPool);
  }

  @Bean
  public FastFileStorageClient fastFileStorageClient(TrackerClient trackerClient,
      ConnectionManager connectionManager) {
    return new DefaultFastFileStorageClient(trackerClient, connectionManager);
  }
  
  @Bean
  public AppendFileStorageClient appendFileStorageClient(TrackerClient trackerClient,
      ConnectionManager connectionManager) {
    return new DefaultAppendFileStorageClient(trackerClient, connectionManager);
  }

}
