package jcrypto.thirdparty.cn.hutool.core.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO工具类<br>
 * IO工具类只是辅助流的读写，并不负责关闭流。原因是流可能被多次读写，读写关闭后容易造成问题。
 * 
 * @author xiaoleilu
 *
 */
public class IoUtil {
    public static byte[] readBytes(String filePath) throws Exception{
    	return readBytes(new FileInputStream(filePath),1024*1024*512);
    }
    //读取全部内容，无优化策略
	public static byte[] readBytes(InputStream in,int buffLen) throws Exception {
		if (null == in) {
			return null;
		}
		byte[] b = {};
		byte[] buffer = new byte[buffLen];
		int read = -1;
		int len = 0;
		while((read = in.read(buffer))!=-1) {
		//动态扩容
		if(b.length<(len+read)) {
			byte [] bReaded = new byte[len];
			System.arraycopy(b, 0, bReaded, 0, len);
			try {
			b = new byte[((len+len>>1)+buffLen)];
			}catch (Exception e) {b = new byte[Integer.MAX_VALUE-128];}
			System.arraycopy(bReaded, 0,b, 0, len);
		}
	    System.arraycopy(buffer,0,b,len,read);
	    len += read;
	    }
		//过滤扩容的空白项
		byte[] readFull = new byte[len];
		System.arraycopy(b, 0, readFull, 0, len);
		in.close();
	    return readFull;
	}
	public static void writeBytes(String filePath,byte[] bytes) throws Exception{
		writeBytes(new FileOutputStream(filePath), bytes);
	}
	//无优化《优化策略:边读边写》
	public static void writeBytes(OutputStream out,byte[] bytes) throws Exception{
		out.write(bytes);
		out.flush();
		out.close();
	}
}
