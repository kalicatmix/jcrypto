package jcrypto.executor;

import java.util.Objects;

public abstract class AbstractExecutor implements Executor{
    public static final int ENCRYPT=0xff;
    public static final int DECRYPT=0x00;
    public  byte[] execute(byte[] target,int WAY) throws Exception {
    	Objects.requireNonNull(target, "bad filePath");
    	switch (WAY) {
		case ENCRYPT:
			return encrypt(target);
        case DECRYPT:
        	return decrypt(target);
		default:
			throw new Exception("can't execute");
		}
    }
}
