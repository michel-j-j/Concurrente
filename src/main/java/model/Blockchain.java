package model;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
  private List<Block> chain = new ArrayList<>();

  public Blockchain() {
    // Crear el bloque génesis
    Block genesisBlock = new Block("0", "Genesis Block");
    chain.add(genesisBlock);
  }

  public synchronized boolean addBlock(Block block) {
    if (isValidBlock(block, getLastBlock())) {
      chain.add(block);
      return true;
    }
    return false;
  }

  public Block getLastBlock() {
    return chain.get(chain.size() - 1);
  }

  // Método para verificar la validez de un bloque en base al anterior
  public boolean isValidBlock(Block newBlock, Block previousBlock) {
    // Verificar que el bloque anterior coincida
    if (!newBlock.getPreviousHash().equals(previousBlock.getHash())) {
      return false;
    }
    // Verificar que el hash actual cumpla con la dificultad (e.g., que tenga ceros al inicio)
    String hashPrefix = "0".repeat(Miner.DIFFICULTY);
    return newBlock.getHash().startsWith(hashPrefix);
  }
}
