package com.blockchain.Repository;

import org.springframework.data.repository.CrudRepository;
import com.blockchain.Model.Node;

public interface NodeRepository extends CrudRepository<Node, String>{

}
