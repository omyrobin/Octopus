package com.jinying.octopus.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


import android.content.Context;
import android.os.Environment;

import com.jinying.octopus.App;
import com.jinying.octopus.R;

import org.apache.http.util.EncodingUtils;

public class FileUtil {

	private static Context context;
	
	public static void instance(Context context){
		FileUtil.context = context;
	}
	
	/**
	 * 获取该应用的缓存文件根目录(dirt novel photo)
	 * 该方法中有小说.txt文件 不建议使用
	 */
	public static File getDiskCacheDir(){
		String packageName = context.getPackageName();
		File f;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			f = new File(Environment.getExternalStorageDirectory()+File.separator+packageName);
		}else{
			f = new File(context.getCacheDir()+File.separator+packageName);
		}
		if(!f.exists())
			f.mkdirs();
		return f;
	}
	
	/**
	 * 存放目录(dier) 可在缓存中删除
	 * @return
	 */
	public static File getDierPhotoFile(){
		File f= new File(getDiskCacheDir() + File.separator+"dier"+File.separator+"photo");
		if(!f.exists())
			f.mkdirs();
		return f;
	}
	
	/**
	 * 拍照图片(原图)存放目录(photo) 可在缓存中删除
	 * @return
	 */
	public static File getCamoraFile(){
		File f= new File(getDiskCacheDir() + File.separator+"photo");
		if(!f.exists())
			f.mkdirs();
		return f;
	}

	/**
	 * 存放目录(novel) 获取该应用的小说缓存目录 不可在缓存中删除
	 */
	public static File getNovelFile(){
		File f= new File(getDiskCacheDir() + File.separator+"novel");
		if(!f.exists())
			f.mkdirs();
		return f;
	}
	
	/**
	 * 获取该应用的单本小说缓存目录
	 */
	public static File getNovelCacheFile(String novelName){
		File f= new File(getDiskCacheDir() + File.separator+"novel",novelName);
		if(!f.exists()){
			f.mkdirs();
			Logger.i("创建目录"+f.getAbsolutePath());
		}
		
		return f;
	}
	
	/**
	 * 获取该应用的单本小说章节文件
	 */
	public static File getNovelChapterTxt(String novelNameAndId,String chapterId){
		File dir = getNovelCacheFile(novelNameAndId);
		File f= new File(dir,chapterId+".txt");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	/**
	 * 判断该应用的单本小说章节文件是否存在
	 */
	public static boolean isHaveNovelChapterTxt(String novelNameAndId,String chapterId){
		File f= new File(getNovelCacheFile(novelNameAndId),chapterId+".txt");
		if(!f.exists()||f.length()==0||f.length()==54){//54代表本地写入的是error 需要重新下载
			return false;
		}
		return true;
	}
	
	/**
	 * 获取应用在阅读页退出时小说的信息
	 */
	public static File getReadCacheDir(){
		File f= new File(getDiskCacheDir() + File.separator+"novelOut");
		if(!f.exists()){
			f.mkdirs();
		}
		return f;
	}
	
	/**
	 * 获取应用在阅读页退出时小说的信息
	 */
	public static File getReadCacheFile(){
		File f= new File(getReadCacheDir(),"storyInfo.txt");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	/**
	 * 存放书架中小说的章节目录数据的文件夹
	 */
	public static File getNovelChapterListDir(){
		File f= new File(getDiskCacheDir() + File.separator+"clist");
		if(!f.exists())
			f.mkdirs();
		return f;
	}
	
	/**
	 * 存放书架中小说的章节目录数据的文件夹
	 */
	public static File getNovelOutChapterListDir(){
		File f= new File(getDiskCacheDir() + File.separator+"clistOut");
		if(!f.exists())
			f.mkdirs();
		return f;
	}
	
	/**
	 * 存放书架中小说的章节目录数据
	 */
	public static File getNovelChapterList(String id){
		File f= new File(getNovelChapterListDir(),id+".txt");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	/**
	 * 存放应用在阅读页退出时小说目录数据
	 */
	public static File getNovelOutChapterList(String id){
		File f= new File(getNovelOutChapterListDir(),id+".txt");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	/**
	 * 将Json的字符串写入文件
	 * @param file 文件
	 * @param result Json toString
	 */
	public static void writeTextFile(File file,String result){
		if(result==null){
			return;
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			byte b[] = result.getBytes();
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Logger.d("is not found file");
		} catch (IOException e) {
			e.printStackTrace();
			Logger.d("write " + file.getAbsolutePath() + " data failed!");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param b
	 * 		  二进制数组
	 * @param name
	 * 		  文件名
	 * @return
	 * 		  文件
	 */
	public static File writeFileData(byte[] b, String name){
		File file = new File(name);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	// 读在目录下面的文件  
    public static String readFileData(String fileName) {  
        String res = "";  
        try {  
            FileInputStream fin = new FileInputStream(fileName);  
            int length = fin.available();  
            byte[] buffer = new byte[length];  
            fin.read(buffer);  
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        return res;  
  
    }
    
    public static String[] readFileTitle(String fileName){
    	FileReader fr = null;
    	BufferedReader br = null;
    	String temp = null;
    	String s = "";
    	String [] ss = null;
		try {
			fr = new FileReader(fileName);
			 br = new BufferedReader(fr);
	    	while((temp=br.readLine())!=null){
	    	    s+=temp+"\n";
	    	}
	    	ss=s.split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{//别忘了关闭流，不然会对读取造成影响
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
        return ss;  
    }
    
    public static String readFileContent(String fileName){
    	FileReader fr = null;
    	BufferedReader br = null;
    	String temp = null;
    	String s = "";
    	String [] ss = null;
    	StringBuffer buffer = new StringBuffer();
    	String p = App.getInstance().getApplicationContext().getResources().getString(R.string.paragraph);
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
	    	while((temp=br.readLine())!=null){
	    	    s+=temp+"\n";
	    	}
	    	ss = BCConvertUtil.half2Fullchange(s).split("\n");
			for (int i = 0; i < ss.length; i++) {
				if(ss[i].trim().replaceAll("\\s*", "").equals("")){
					continue;
				}
				if(i==0){
					buffer.append(ss[i].trim().replaceAll("\\s*", "")+"\n"+"\n"+"\n");
				}else if(i>1){
					buffer.append(p+ss[i].trim().replaceAll("\\s*", "")+"\n"+"\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{//别忘了关闭流，不然会对读取造成影响
			try {
				fr.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
        return buffer.toString();  
    }
    
    //利用NIO读取文件
    public static String readFileByLine(int bufSize, FileChannel fcin,  
            ByteBuffer rBuffer, ByteBuffer wBuffer) {  
        String enterStr = "\n";  
        StringBuilder strBuf = null; 
        try {  
            byte[] bs = new byte[bufSize];  
            strBuf = new StringBuilder("");  
            String tempString = null;  
            while (fcin.read(rBuffer) != -1) {  
                int rSize = rBuffer.position();  
                rBuffer.rewind();  
                rBuffer.get(bs);  
                rBuffer.clear();  
                tempString = new String(bs, 0, rSize);  
                int fromIndex = 0;  
                int endIndex = 0;  
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {  
                    String line = tempString.substring(fromIndex, endIndex);  
                    line = strBuf.toString() + line;  
//                    writeFileByLine(fcout, wBuffer, line); 
                    strBuf.delete(0, strBuf.length());  
                    fromIndex = endIndex + 1;  
                }  
  
                if (rSize > tempString.length()) {  
                    strBuf.append(tempString.substring(fromIndex,  
                            tempString.length()));  
                } else {  
                    strBuf.append(tempString.substring(fromIndex, rSize));  
                }  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return strBuf.toString();
    }  
    
    //利用NIO写入文件
    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer,  
            String line) {  
        try {  
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 删除文件
     * @param file
     */
	public static void clearFile(File file){
		if(file.isFile()){
			file.delete();
		}else if(file.isDirectory()){
			for(File f : file.listFiles()){
				clearFile(f);
			}
		}
	}
}
