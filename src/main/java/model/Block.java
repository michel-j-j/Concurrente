package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  private String previousHash;
  private String data;
  private String hash;
  private String minerId;
  private int nonce = 0; // Iniciar el nonce en 0

  public Block(String previousHash, String data) {
    this.previousHash = previousHash;
    this.data = data;
    this.hash = calculateHash();
  }

  public String calculateHash() {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      String text = previousHash + data + nonce; // Incluir nonce en el cálculo del hash
      byte[] hashBytes = digest.digest(text.getBytes());
      StringBuilder buffer = new StringBuilder();
      for (byte b : hashBytes) {
        buffer.append(String.format("%02x", b));
      }
      return buffer.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public void incrementNonce() {
    nonce++; // Incrementar el nonce en cada intento
    hash = calculateHash(); // Recalcular el hash después de cambiar el nonce
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public String getHash() {
    return hash;
  }

  public void setMinerId(String minerId) {
    this.minerId = minerId;
  }

  public String getMinerId() {
    return minerId;
  }

  public int getNonce() {
    return nonce;
  }
}
