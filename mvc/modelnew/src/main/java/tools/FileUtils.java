package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.log4j.Logger;

public class FileUtils {

	private static final Logger LOG = Logger.getLogger(FileUtils.class);
	
	/**
	 * 去除文件名中的非法字符
	 * @param filename
	 * @return String
	 */
	public static String checkFileName(String fileName){
		String regex="\\\\|/|:|\\*|\\?|\\\"|<|>|\\|";
		return fileName=fileName.replaceAll(regex, "");
		
	}
	
	/**
	 * 生成系统存储用文件名
	 * @param fileName
	 * @return String
	 */
	public static String createFileName(String fileName){
		String realName=UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
		return realName;
		
	}
		
	public static void transferFile(String oldPath,String newPath) throws Exception {  
        int byteread = 0;
        File oldFile = new File(oldPath);
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try{
            if(oldFile.exists()){
                fin = new FileInputStream(oldFile);
                fout = new FileOutputStream(newPath);
                byte[] buffer = new byte[fin.available()];
                while( (byteread = fin.read(buffer)) != -1){
                    fout.write(buffer,0,byteread);
                }
                if(fin != null){
                    fin.close();
                    delFile(oldFile);
                }
            }else{
                throw new Exception("需要转移的文件不存在!");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            if(fin != null){
                fin.close();
            }
            fout.close();
        }
    }
	
	private static void delFile(File file) throws Exception {  
        if(!file.exists()) {  
            throw new Exception("文件"+file.getName()+"不存在,请确认!");  
        }  
        if(file.isFile()){  
            if(file.canWrite()){  
                file.delete();  
            }else{  
                throw new Exception("文件"+file.getName()+"只读,无法删除,请手动删除!");  
            }  
        }else{  
            throw new Exception("文件"+file.getName()+"不是一个标准的文件,有可能为目录,请确认!");  
        }  
    }
		
	public static String formatFileLength(long fileLength){
		long kb = 1024;
		long mb = kb*1024;
		long gb = mb*1024;
		long tb = gb*1024;
		if(fileLength<kb){
			return "1KB";
		}
		if(kb<fileLength && fileLength<mb){
			return fileLength/kb+"KB";
		}
		if(mb<fileLength && fileLength<gb){
			return fileLength/mb+"MB";
		}
		if(gb<fileLength && fileLength<tb){
			return fileLength/gb+"GB";
		}
		return fileLength/tb+"TB";
	}
	
	public static boolean checkDir(String dirPath){
		 boolean rs=false;
	     try {	
				File file=new File(dirPath);
				
				if(!file.exists())rs=file.mkdirs();
				else return true;
				if(!rs) throw new Exception("mkdir失败");
			}catch (Exception e) {
				LOG.error("本地文件不存在["+dirPath+"]", e);
			}
			return rs;
	} 
	public static File getDir(String dirPath) throws Exception{
		 boolean rs=true;
	     File file=new File(dirPath);
		 if(!file.exists())rs=file.mkdirs();	 
		 if(!rs) throw new Exception("mkdir失败");
         return file;
	} 
	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 * @throws Exception 
	 */
	public static void copyFile(String oldPath, String newPath) throws Exception {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 文件存在时
			FileInputStream is = null;
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(newPath);
				is = new FileInputStream(oldPath); // 读入原文件
				byte[] buffer = new byte[1024];
				while ((byteread = is.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					os.write(buffer, 0, bytesum);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
					os.flush();
					is.close();
					os.close();
			}
		}
	}
}