package edu.sjsu.cmpe.cache.client;

public class CRDTClient {
	
	CacheServiceInterface cache1 = new DistributedCacheService(
            "http://localhost:3000");
    CacheServiceInterface cache2 = new DistributedCacheService(
            "http://localhost:3001");
    CacheServiceInterface cache3 = new DistributedCacheService(
            "http://localhost:3002");
    
    public void putCache(long key,String value)
    {
    	if(cache1.put(key,value)+cache2.put(key,value)+cache3.put(key,value)>=2)
    	{
    		System.out.println("Successfully put values");
    	}
    	else
    	{
    		System.out.println("Rollback");
    		cache1.delete(key);
    		cache2.delete(key);
    		cache3.delete(key);
    	}
    }
    
    public void getCache(long key)
    {
    	String cValue1=cache1.get(key);
    	String cValue2=cache2.get(key);
    	String cValue3=cache3.get(key);
    	if(cValue1.equals(cValue2) && cValue2.equals(cValue3) && cValue3.equals(cValue1))
    	{
    		System.out.println("All gets successful, Consistent State");
    	}
    	else
    	{
    		if(cValue1.equals(cValue2))
    		{
    			cache3.put(key,cValue1);
    		}
    		else if(cValue2.equals(cValue3))
    		{
    			cache1.put(key,cValue2);
    		}
    		else if(cValue3.equals(cValue1))
    		{
    			cache2.put(key,cValue1);
    		}
    	}
    }

}
