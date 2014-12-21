package edu.sjsu.cmpe.cache.client;

import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
    private final String cacheServerUrl;
    int count=0;
    String value="";
    
    
    public DistributedCacheService(String serverUrl) {
        this.cacheServerUrl = serverUrl;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
     */
    @Override
    public String get(long key) {
        Future<HttpResponse<JsonNode>> response = null;
            response = Unirest.get(this.cacheServerUrl + "/cache/{key}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key)).asJsonAsync(new Callback<JsonNode>() {
                    	
    		            public void completed(HttpResponse<JsonNode> response) {
    		            	value=response.getBody().getObject().getString("value");
    		            }

    					@Override
    					public void cancelled() {
    						// TODO Auto-generated method stub
    						
    					}

    					@Override
    					public void failed(UnirestException arg0) {
    						// TODO Auto-generated method stub
    						
    					}

    		        });

        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // String value = response.getBody().getObject().getString("value");

        return value;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
     *      java.lang.String)
     */
    @Override
    public int put(long key, String value) {
    	Future<HttpResponse<JsonNode>> response = null;
            response = Unirest
                    .put(this.cacheServerUrl + "/cache/{key}/{value}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .routeParam("value", value).asJsonAsync(new Callback<JsonNode>() {
                    	
    		            public void completed(HttpResponse<JsonNode> response) {
   		                 	count=1;
    		            }

    					@Override
    					public void cancelled() {
    						// TODO Auto-generated method stub
    						
    					}

    					@Override
    					public void failed(UnirestException arg0) {
    						// TODO Auto-generated method stub
    						
    					}

    		        });
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return count;
        
    }
    @Override
    public void delete(long key) {
        Future<HttpResponse<JsonNode>> response = null;
        response = Unirest
		        .delete(this.cacheServerUrl + "/cache/{key}")
		        .header("accept", "application/json")
		        .routeParam("key", Long.toString(key)).asJsonAsync(new Callback<JsonNode>() {
		        	
		            public void completed(HttpResponse<JsonNode> response) {
		                 //System.out.println("in delete");
		            }

					@Override
					public void cancelled() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void failed(UnirestException arg0) {
						// TODO Auto-generated method stub
						
					}

		        });
    }
}
