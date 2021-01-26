package jcrypto.executor.impl;

import jcrypto.executor.AbstractExecutor;

/**
xor 111
    101
   -010
    101
    111
byte[flag,1,2,3,4....] 
*/
public class DefaultExecutor extends AbstractExecutor {
    
	@Override
	public byte[] encrypt(byte[] target) {
		byte[] encrypt = new byte[target.length+5];
		encrypt[0] = 'm';
		encrypt[1] = 'a';
		encrypt[2] = 'g';
		encrypt[3] = 'i';
		encrypt[4] = 'c';
		
		for(int i=0;i<target.length;i++) {
			byte xor = (byte) ((target[i])^0b1111);
			encrypt[i+5] = xor;
		}
		return encrypt;
	}

	@Override
	public byte[] decrypt(byte[] target) {
		byte [] decrypt = new byte[target.length-5];
		for(int i=0;i<decrypt.length;i++) {
			byte xor = (byte) ((target[i+5])^0b1111);
			decrypt[i] = xor;
		}
		return decrypt;
	}

}
