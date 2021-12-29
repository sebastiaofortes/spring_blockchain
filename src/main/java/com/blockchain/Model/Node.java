package com.blockchain.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Node {

@Id	
public String nome;


public String IP;

public Node(String nome, String iP) {
	this.nome = nome;
	this.IP = iP;
}

public Node() {

}




}
