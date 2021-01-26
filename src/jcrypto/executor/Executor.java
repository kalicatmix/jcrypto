package jcrypto.executor;

public interface Executor {
 public byte[] encrypt(byte[] target);
 public byte[] decrypt(byte[] target);
}
