package jcrypto.sealed;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import jcrypto.algorithm.Algorithm;
import jcrypto.executor.AbstractExecutor;
import jcrypto.thirdparty.cn.hutool.core.io.IoUtil;


public class Bootstrap {
	private static Map<String, Algorithm> algorithmMap;
	private static StringBuilder canUsedAlgorithm;
	private final static Algorithm defaultAlgorithm = Algorithm.DEFAULT;
    static {
    	 algorithmMap = new HashMap<String, Algorithm>();
    	 canUsedAlgorithm = new StringBuilder();
    	 for(Algorithm algorithm : Algorithm.values()) {
    		 algorithmMap.put(algorithm.getName(), algorithm);
    		 canUsedAlgorithm.append(algorithm.getName()+" ");
    	 }
    }
	public static void main(String[] args) throws Exception {
		 parseArgsAndBootstrap(args);
	}
	
    private static void printUsageAndError(PrintStream printStream,String error) {
    	printStream.println("Error: "+error);
    	printStream.println("======================= Jcrypto Usage =====================");
    	printStream.println("");
    	printStream.println("Encode: java -jar jcrypto.jar enc -al=<algorithm> <file>");
    	printStream.println("Decode: java -jar jcrypto.jar dec -al=<algorithm> <file>");
    	printStream.println("Algorithm: "+canUsedAlgorithm);
    	printStream.println("");
    	printStream.println("===========================================================");
    }
    private static String resolveSaveFilePath(File file,String prefix) {
    	if(file.getParentFile().isDirectory()) {
    		return file.getParentFile().getAbsolutePath()+FileSystems.getDefault().getSeparator()+prefix+"_"+file.getName();
    	}
    	return prefix+"_"+file.getName();
    }
    private static void parseArgsAndBootstrap(String [] args) {
    	try {
    		if(args.length > 3) throw new Exception("bad arguments");
    		else{
    			String algorithmName = defaultAlgorithm.getName();
    			int METHOD = -1;
    			String filePath = "";
    			if(args[1].contains("-al=")) algorithmName = args[1].split("-al=")[1].trim();
    			else filePath = args[1];
    			switch (args[0]) {
				case "enc":
					METHOD = AbstractExecutor.ENCRYPT;
					break;
				case "dec":
					METHOD = AbstractExecutor.DECRYPT;
					break;	
				default:
					throw new Exception("bad arguments");
				}
    			Algorithm algorithm = algorithmMap.get(algorithmName);
    			Class executor = null;
     			if(algorithm==null) executor = Class.forName(algorithmName);	
    			else executor = Class.forName(algorithm.getExecutorClassPath());
     			IoUtil.writeBytes(resolveSaveFilePath(new File(filePath),args[0]),(byte[]) executor.getMethod("execute",byte[].class,int.class).invoke(executor.newInstance(),IoUtil.readBytes(filePath),METHOD));
     			}
    	}catch (Exception e) {e.printStackTrace();printUsageAndError(System.out,e.getMessage());}
    }
}
