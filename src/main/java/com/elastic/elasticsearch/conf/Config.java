package com.elastic.elasticsearch.conf;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages="com.elastic.elasticsearch.repository")
public class Config {

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws IOException {
		
		File tempDir = File.createTempFile("temp-elastic", Long.toString(System.nanoTime()));
		System.out.println("Temp directory: "+ tempDir.getAbsolutePath());
		
		@SuppressWarnings("unused")
		Settings.Builder settings = Settings.builder()
										
				// cluster settings
				//.put("cluster.name", "elasticsearch")
		
				//http settings
				.put("http.enable", "true")
				.put("http.cor.enable", "true")
				.put("http.cors.allow-origin", "https?:?/?/localhost(:[0-9]+)?/")
				.put("http.port", "9200")
			
				//transport settings
				.put("transport.tcp.port", "9300")
				.put("network.host", "localhost")
			
				//node settings
				.put("node.data", "true")
				.put("node.master", "true")
			
				//configuring index
				.put("index.number_of_shards", "1")
				.put("index.number_of_replicas", "2")
				.put("index.refresh_interval", "10s")
				.put("index.max_results_window", "100")
	
				//configuring paths
				.put("path.logs", new File (tempDir, "logs").getAbsolutePath())
				.put("path.data", new File (tempDir, "data").getAbsolutePath());
				//.put("path.home", tempDir);
				//.build();
		
		@SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
		
		//return new ElasticsearchTemplate(nodeBuilder()
		//		.local(true)
		//		.settings(settings.build())
		//		.node()
		//		.client());
		return new ElasticsearchTemplate(client);
	}

}