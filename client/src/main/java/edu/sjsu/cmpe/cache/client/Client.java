package edu.sjsu.cmpe.cache.client;

public class Client {
	
	
	public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        CRDTClient crdt=new CRDTClient();
        crdt.putCache(1,"a");
        System.out.println("Stop server A");
        Thread.sleep(30000);
        crdt.putCache(1,"b");
        System.out.println("Start Server A again");
        Thread.sleep(30000);
        crdt.getCache(1);
        System.out.println("Existing Cache Client...");
    }

}
