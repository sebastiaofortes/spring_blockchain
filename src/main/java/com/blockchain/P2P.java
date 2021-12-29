package com.blockchain;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.blockchain.Model.Node;
import com.blockchain.Repository.NodeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;



public class P2P {
	
public List<Node> pais = new ArrayList<Node>();
public List<Node> filhos = new ArrayList<Node>();
public Node Usuario;

@Autowired
NodeRepository noderepository;

public P2P() {
	

	pais.add(new Node("João", "123456"));
	pais.add(new Node("Maria", "123456"));

	filhos.add(new Node("Carlos", "123456"));
	filhos.add(new Node("Joanna", "123456"));
	
	Usuario = new Node("Sebastião", "123456789");
}


@Async
public void block(String mensagem){
	
	
	// percorre o array filhos e envia para todos os seus filhos
	// percorre o array filhos e envia para todos os pais;
	
	// os pais ao receber vão enviar para seus pais e seus filhos e assim sucessivamente. 
	
	try{
	Thread.sleep(7000);	
	}
	catch(InterruptedException e) {
		
	}


	ArrayList<Node> nodes = (ArrayList<Node>) noderepository.findAll();
	
	for(Node n : nodes) {
		

try {
	HttpSend(n, mensagem, "/remoteblock");
} catch (ClientProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

		
		
	}
	
	System.out.println("Fim do processo");
	
}	

private void HttpSend(Node n, String mensagem, String action) throws ClientProtocolException, IOException {

	
    CloseableHttpClient httpClient = HttpClients.createDefault();

    try {

        HttpPost request = new HttpPost(n.IP+action);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("data", mensagem));
        request.setEntity(new UrlEncodedFormEntity(params));
        
        
        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse response = httpClient.execute(request);	
        try {

            // Get HttpResponse Status
            System.out.println(response.getProtocolVersion());              // HTTP/1.1
            System.out.println(response.getStatusLine().getStatusCode());   // 200
            System.out.println(response.getStatusLine().getReasonPhrase()); // OK
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        } finally {
            response.close();
        }
    } finally {
        httpClient.close();
    }
	
	
	
}


@Async
public void pow(String mensagem) {
	
	// percorre o array filhos e envia para todos os seus filhos
	// percorre o array filhos e envia para todos os pais;
	
	// os pais ao receber vão enviar para seus pais e seus filhos e assim sucessivamente. 
	
	try{
	Thread.sleep(7000);	
	}
	catch(InterruptedException e) {
		
	}
	
	
	ArrayList<Node> nodes = (ArrayList<Node>) noderepository.findAll();
	
	for(Node n : nodes) {

		
		
		try {
			HttpSend(n, mensagem, "/remotepow");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	
	}
	
	System.out.println("Fim do processo");
}	





}
