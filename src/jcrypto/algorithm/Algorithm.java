package jcrypto.algorithm;

public enum Algorithm {

DEFAULT("default","jcrypto.executor.impl.DefaultExecutor");
	
String name;
String executorClassPath;

Algorithm(String name,String executorClassPath) {
	this.name=name;
	this.executorClassPath=executorClassPath;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @return the executorClassPath
 */
public String getExecutorClassPath() {
	return executorClassPath;
}
/**
 * @param executorClassPath the executorClassPath to set
 */
public void setExecutorClassPath(String executorClassPath) {
	this.executorClassPath = executorClassPath;
}

}
