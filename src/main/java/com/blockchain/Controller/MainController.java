package com.blockchain.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blockchain.P2P;
import com.blockchain.Rsa;
import com.blockchain.Model.Block;
import com.blockchain.Model.Node;
import com.blockchain.Repository.BlockRepository;
import com.blockchain.Repository.NodeRepository;

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
	
 @Autowired
 public NodeRepository noderepository;
	
 public Block CriarBlock(String transacao, String assinatura, String timeStamp)
 {	
	 
	String meuLogin = "sebastiaofortes4@gmail.com"; 
       
	Block blocoanterior =  repositorioLocal.ultimoRegistro();
	 
	Block newBlock;
	
	if (blocoanterior != null) {
	newBlock = new Block(transacao, assinatura, meuLogin, blocoanterior.hash, timeStamp);
	System.out.println("Atribuindo dados ao novo bloco");
	}
	else 
	{
	newBlock = new Block(transacao, assinatura,meuLogin, "genesis",timeStamp);
	System.out.println("criando bloco genesis");
	}
	
	
	
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

<<<<<<< HEAD
@GetMapping("/mine_block")
public String MineBlock(@RequestParam String id, ModelMap model) {
	
	model.addAttribute("idv", id);
	
	return "mineblock";
}

=======
@RequestMapping("/test2")
public String Test2() {
	
	
	
	return "teste2";
}


@RequestMapping("/mine_block")
public String MineBlock() {
	
	
	
	return "mine_block";
}


>>>>>>> d96a3ba9a4af0e541c7bb75c0006a8ee069f5e5b



@RequestMapping("/translist")public String listandoT (ModelMap model) { 

List<Block> lista = (List<Block>) repositorioLocal.findAll();
int numres = (lista.size());
String textres = Integer.toString(numres);
model.addAttribute("numeroresultados", textres);
model.addAttribute("blocks", lista);
return "translist";
}





	
	
@Transactional
@GetMapping(path="/dell_node") 
	  public @ResponseBody String dellNode(@RequestParam String nome) {

	noderepository.deleteById(nome);
	System.out.println("Relacionamentos excluídos ");
	  
	 return "Dados deletados com sucesso!";	  
	  }	
	
@Transactional
@GetMapping(path="/dell_block") 
	  public @ResponseBody String dellBlock(@RequestParam String nome) {

       
        repositorioLocal.deleteB(Integer.valueOf(nome));

	System.out.println("Relacionamentos excluídos ");
	  
	 return "Dados deletados com sucesso!";	  
	  }	
	
	
@RequestMapping("/nodelist")
public String listando(ModelMap model) {

	List<Node> lista = (List<Node>) noderepository.findAll();
	int numres = (lista.size());
	String textres = Integer.toString(numres);
	model.addAttribute("numeroresultados", textres);
	model.addAttribute("nodes", lista);
	return "nodelist";
}

@RequestMapping("/addnode")
public String AddNode() {
	
	
	
	return "addnode";
}

@RequestMapping("/createnode")
public String CreateNode(@RequestParam String nome, @RequestParam String ip) {
	Node n =new Node(nome, ip);
	
	noderepository.save(n);
	
	return "createnode";
}	
	
@Transactional
@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/localpow")
public String LocalPOW(@RequestParam String data,@RequestParam String timeStamp) {
JSONObject json = new JSONObject(data);
System.out.println(data);

repositorioLocal.updateHash(json.getString("difficultyHash"), json.getInt("nonce"), json.getString("Id"));

broadcast.pow(data,timeStamp);

return json.getString("difficultyHash") + json.getString("Hash") + String.valueOf(json.getInt("nonce"));
}

@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/localblock")
public String LocalBlock(@RequestParam String data, @RequestParam String timeStamp) {
System.out.println(data);

Block novoBlock = CriarBlock(data, "assinatura",timeStamp);

broadcast.block(data, timeStamp);

return novoBlock.Tojson();
}



@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/get_local_block")
public String GetLocalBlock(@RequestParam String id) {
System.out.println(id);

Block b = repositorioLocal.GetBlock(Integer.valueOf(id));


return b.Tojson();
}




@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/remoteblock")
public String RemoteBlock(@RequestParam String data, @RequestParam String timeStamp) {

CriarBlock(data, "assinatura", timeStamp);


	
return "User X: socessfull";
}


@Transactional
@ResponseBody
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/remotepow")
public String RemotePOW(@RequestParam String data, @RequestParam String timeStamp) {
JSONObject json = new JSONObject(data);
System.out.println(data);

repositorioLocal.updateHash(json.getString("difficultyHash"), json.getInt("nonce"), json.getString("Id"));

	
return "User X: socessfull";
}


}
