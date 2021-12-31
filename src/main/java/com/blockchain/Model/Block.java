package com.blockchain.Model;

	
	// Java implementation for creating
	// a block in a Blockchain
	   
import java.util.Date;




import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.json.JSONObject;

import com.blockchain.Hash;




@Table(name = "TRANS")
@Entity
public class Block {
	// Java implementation for creating
	// a block in a Blockchain

		// Every block contains
		// a hash, previous hash and
		// data of the transaction made
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public int id;
		public String hash;
		public String previousHash;
		private String data;
		private String timeStamp;
		private String assinatura;
		private int nonce;
		private String autor;
		private String difficultyHash;
		


		public Block() {
		
		}

		// Constructor for the block
		public Block(String data, String assinatura, String autor,
					String previousHash, String timeStamp)
		{	
			this.nonce = 1;
			this.assinatura = assinatura;
			this.data = data;
			this.autor = autor;
			this.previousHash
				= previousHash;
			this.timeStamp
				= timeStamp;
			this.hash
				= calculateHash();
		}

		// Function to calculate the hash
		public String calculateHash()
		{
			// Calling the "crypt" class
			// to calculate the hash
			// by using the previous hash,
			// timestamp and the data
			String calculatedhash
				= Hash.sha256(
					nonce 
					+ previousHash
					+ Long.toString(timeStamp)
					+ data);

			return calculatedhash;
		}
		
		public String mineBlock(int prefix) {
		    String prefixString = new String(new char[prefix]).replace('\0', '0');
		    while (!hash.substring(0, prefix).equals(prefixString)) {
		        nonce++;
		        hash = calculateHash();
		    }
		    System.out.println("Hash encontrado: " + hash);
		    return hash;
		}
		
		public String Tojson() {
			JSONObject jo = new JSONObject();
		
			
			jo.put("id", id);
			jo.put("hash", hash);
			jo.put("previousHash", previousHash);
			jo.put("data", data);
			jo.put("timeStamp", timeStamp);
			jo.put("assinatura", assinatura);
			jo.put("nonce", nonce);
			jo.put("autor", autor);
		
			jo.put("difficulty", 3);
			
			return jo.toString();
			
			
		}
		
		
		
		

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

		public String getPreviousHash() {
			return previousHash;
		}

		public void setPreviousHash(String previousHash) {
			this.previousHash = previousHash;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getTimeStamp() {
			return timeStamp;
		}

		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}



		public String getAssinatura() {
			return assinatura;
		}

		public void setAssinatura(String assinatura) {
			this.assinatura = assinatura;
		}

		public int getNonce() {
			return nonce;
		}

		public void setNonce(int nonce) {
			this.nonce = nonce;
		}

		public String getAutor() {
			return autor;
		}

		public void setAutor(String autor) {
			this.autor = autor;
		}

		public String getDifficultyHash() {
			return difficultyHash;
		}

		public void setDifficultyHash(String difficultyHash) {
			this.difficultyHash = difficultyHash;
		}
		
		
		
		
		
		
		
		
		
		
		
	}


	
	

