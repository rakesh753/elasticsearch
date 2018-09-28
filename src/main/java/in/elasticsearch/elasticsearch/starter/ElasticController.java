package in.elasticsearch.elasticsearch.starter;

import java.io.IOException;
import java.net.InetAddress;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.Transport;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@RestController
@RequestMapping("/rest/users")
public class ElasticController {

	TransportClient client;
	
	@SuppressWarnings("resource")
	public ElasticController() {
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"),9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}			
	}
	

	@RequestMapping("/insert/{indexName}/{docType}/{id}")
	public String insert(@PathVariable String indexName, @PathVariable String docType, @PathVariable String id) throws Exception {
		
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		request.settings(Settings.builder()
				.put("index.number_of_shards",3)
				.put("index.number_of_replicas",2));
		
		IndexResponse indexResponse = client.prepareIndex(indexName,docType,id)
				.setSource(jsonBuilder().startObject()
						.field("name","aa")
						.field("salary","11")
						.field("team","bb")
						.endObject()).get();
						
		return indexResponse.getResult().toString();
				
	}
	
	@RequestMapping("/view/{indexName}/{type}/{id}")
	public Map<String, Object> view (@PathVariable String indexName, @PathVariable String type, @PathVariable String id){
		
		GetResponse getResponse = client.prepareGet(indexName, type, id).get();
		System.out.println(getResponse.getSourceAsString());
		return getResponse.getSource();
		
	}
	
	
}
