package model;

import gui.BlockchainMiningGUI;

public class Miner extends Thread {
  private Blockchain blockchain;
  private String minerId;
  private BlockchainMiningGUI gui;
  private boolean successful;

  public static final int DIFFICULTY = 4;

  public Miner(Blockchain blockchain, String minerId) {
    this.blockchain = blockchain;
    this.minerId = minerId;
    this.successful = false;
  }

  public String getMinerId() {
    return minerId;
  }

  public void setGUI(BlockchainMiningGUI gui) {
    this.gui = gui;
  }

  public boolean isSuccessful() {
    return successful;
  }

  private boolean isValidHash(String hash) {
    return hash.startsWith("0".repeat(DIFFICULTY));
  }

  public void mineBlock() {
    try {
      Block lastBlock = blockchain.getLastBlock();
      Block newBlock = new Block(lastBlock.getHash(), "Transaction Data");
      newBlock.setMinerId(minerId);

      // Proceso de Prueba de Trabajo
      while (!isValidHash(newBlock.getHash())) {
        newBlock.incrementNonce();
        newBlock.calculateHash();

        // Actualiza el intento del minero en la GUI cada 100 intentos
        if (newBlock.getNonce() % 100 == 0 && gui != null) {
          String status = "Nonce: " + newBlock.getNonce() + ", Hash: " + newBlock.getHash();
          gui.addMinerAttempt(minerId, status);
        }
      }

      // Intento de añadir el bloque a la blockchain
      if (blockchain.addBlock(newBlock)) {
        successful = true;
        if (gui != null) {
          gui.updateWinner("Miner " + minerId + " ganó con hash: " + newBlock.getHash() + " y nonce: " + newBlock.getNonce());
        }
        System.out.println("Miner " + minerId + " added a block with hash: " + newBlock.getHash() + " and nonce: " + newBlock.getNonce());
      }
    } catch (Exception e) {
      System.out.println("Error en el minero " + minerId + ": " + e.getMessage());
    }
  }
}
