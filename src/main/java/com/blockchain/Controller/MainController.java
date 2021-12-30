package com.blockchain.Controller;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blockchain.P2P;
import com.blockchain.Rsa;
import com.blockchain.Model.Block;
import com.blockchain.Repository.BlockRepository;


@Controller
@RequestMapping(path="/")
public class MainController {

// ArrayList to store the blocks
 public ArrayList<Block> blockchain = new ArrayList<Block>();
 
 public Rsa certificado;
 
 @Autowired
 public P2P broadcast;
 
 @Autowired
 public BlockRepository repositorioLocal;
	

 public Block CriarBlock(String transacao, String assinatura)
 {	
	 
	String meuLogin = "sebastiaofortes4@gmail.com"; 
       
	Block blocoanterior =  repositorioLocal.ultimoRegistro();
	 
	Block newBlock;
	
	if (blocoanterior != null) {
	newBlock = new Block(transacao, assinatura, meuLogin, blocoanterior.hash);
	System.out.println("Atribuindo dados ao novo bloco");
	}
	else 
	{
	newBlock = new Block(transacao, assinatura,meuLogin, "genesis");
	System.out.println("criando bloco genesis");
	}
	
	newBlock.mineBlock(2);
	
	repositorioLocal.save(newBlock);
	
     return newBlock;
 }

//Java implementation to check
//validity of the blockchain

//Function to check
//validity of the blockchain
 

public Boolean isChainValid()
{
	Block currentBlock;
	Block previousBlock;

	// Iterating through
	// all the blocks
	for (int i = 1;
		i < blockchain.size();
		i++) {

		// Storing the current block
		// and the previous block
		currentBlock = blockchain.get(i);
		previousBlock = blockchain.get(i - 1);

		// Checking if the current hash
		// is equal to the
		// calculated hash or not
		if (!currentBlock.hash
				.equals(
					currentBlock
						.calculateHash())) {
			System.out.println(
				"Hashes are not equal");
			return false;
		}

		// Checking of the previous hash
		// is equal to the calculated
		// previous hash or not
		if (!previousBlock
				.hash
				.equals(
					currentBlock
						.previousHash)) {
			System.out.println(
				"Previous Hashes are not equal");
			return false;
		}
	}

	// If all the hashes are equal
	// to the calculated hashes,
	// then the blockchain is valid
	return true;
}

@RequestMapping("/validation")
public String validation() {
	
	isChainValid();
	
	return "validation";
}

@RequestMapping("/test")
public String Test() {
	
	
	
	return "teste";
}


@Transactional
@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/localpow")
public String LocalPOW(@RequestParam String data) {
JSONObject json = new JSONObject(data);
System.out.println(data);

repositorioLocal.updateHash(json.getString("difficultyHash"), json.getInt("nonce"), json.getString("Hash"));

broadcast.pow(data);

return "User X: sucessfull - difficultyhash adicionado ao se bloco";
}

@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/localblock")
public String LocalBlock(@RequestParam String data) {
System.out.println(data);

Block novoBlock = CriarBlock(data, "assinatura");

broadcast.block(data);

return novoBlock.Tojson();
}

@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/remoteblock")
public String RemoteBlock(@RequestParam String data) {

CriarBlock(data, "assinatura");


	
return "User X: socessfull";
}


@Transactional
@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/remotepow")
public String RemotePOW(@RequestParam String data) {
JSONObject json = new JSONObject(data);
System.out.println(data);

repositorioLocal.updateHash(json.getString("difficultyHash"), json.getInt("nonce"), json.getString("Hash"));

	
return "User X: socessfull";
}


}
