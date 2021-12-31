package com.blockchain.Repository;



import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blockchain.Model.Block;

@Repository
public interface BlockRepository extends CrudRepository<Block, String>{

@Query(value = "SELECT * FROM TRANS WHERE ID =(SELECT MAX (ID) FROM TRANS)", nativeQuery=true)
Block ultimoRegistro();

// UPDATE TRANS SET DIFFICULTY_HASH = 's', NONCE = 11 WHERE HASH = 'c7aafc2b4a5a5ef32b16a7464796227f28fd2f1805f0e670c0a0e31739d75c30'

@Modifying
@Query(value = "UPDATE TRANS SET DIFFICULTY_HASH = ?1, NONCE = ?2 WHERE HASH = ?3", nativeQuery=true)
int updateHash(String ph, int nonce, String hash);



}
