package in.elasticsearch.elasticsearch.starter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Map<String, Object> viewById (@PathVariable String indexName, @PathVariable String type, @PathVariable String id){
		
		GetResponse getResponse = client.prepareGet(indexName, type, id).get();
		System.out.println(getResponse.getSourceAsString());
		return getResponse.getSource();
		
	}
	
	@RequestMapping("/view/{indexName}")
	public String viewAll(@PathVariable String indexName){
		
		return null;
	}
	
	@RequestMapping("/update/{indexName}/{type}/{id}")
	public String Update(@PathVariable String indexName, @PathVariable String type, @PathVariable String id){
		
		UpdateRequest updateRequest = new UpdateRequest();
		try {
			updateRequest.index(indexName)
			.type(type)
			.id(id)
			.doc(jsonBuilder()
					.startObject()
					.field("gender","male")
					.endObject());
		}
		
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try{
			UpdateResponse updateResponse = client.update(updateRequest).get();
			System.out.println(updateResponse.toString());
		}
		
		catch (InterruptedException | ExecutionException e){
			//e.printStackTrace();
			//return updateRequest.toString();
			System.out.println("UPDATION_FAILED");
			return "UPDATION_FAILED";
		}
		
		//return null;
		return updateRequest.toString();
	}
	
	
	@RequestMapping("/delete/{indexName}/{type}/{id}")
	public String deleteById (@PathVariable String indexName, @PathVariable String type, @PathVariable String id){
		
		DeleteResponse deleteResponse = client.prepareDelete(indexName, type, id).get();
		System.out.println(deleteResponse.getResult().toString());
		return deleteResponse.getResult().toString();
	}
	
	@RequestMapping("/delete/{indexName}")
	public RestStatus deletAll(@PathVariable String indexName){

		DeleteIndexResponse deleteIndexResponse = null;
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		try{
			deleteIndexResponse = client.admin().indices().delete(request).actionGet();
			
		}
		
		catch(ElasticsearchException e){
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("INDEX_NOT_FOUND");
				return RestStatus.NO_CONTENT;
		    }
			else
				e.printStackTrace();
		}
		
		System.out.println(deleteIndexResponse.toString());
		return RestStatus.OK;
		
	}
	
}
