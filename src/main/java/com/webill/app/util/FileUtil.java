package com.webill.app.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.toolkit.StringUtils;


public class FileUtil {
    private static Log logger = LogFactory.getLog(FileUtil.class);
    private  static Properties props = null;

    

	private FileUtil() {

	}

	private static final String[][] MIME_MapTable = {
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".pdf", "application/pdf" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".txt", "text/plain" }, { ".wps", "application/vnd.ms-works" },
			{ ".bmp", "image/bmp" }, { ".gif", "image/gif" },
			{ ".png", "image/png" }, { ".jpg", "image/jpeg" },
			{ ".jpeg", "image/jpeg" }, { ".ico", "image/x-icon" } };

	/**
	 * 获取文件MIME类型
	 * 
	 * @param str
	 *            文件后缀名
	 * @return 文件MIME类型
	 */
	public static String getMIMEType(String str) {
		String type = "";
		if (str != null) {
			for (int i = 0; i < MIME_MapTable.length; i++) {
				if (str.equals(MIME_MapTable[i][0])) {
					type = MIME_MapTable[i][1];
				}
			}
		}
		return type;
	}

	/**
	 * 根据传入的路径生成对应目录
	 * 
	 * @param path
	 */
	public static void createFilePath(String path) {
		try {
			if (path.indexOf(".") > 0) {
				path = path.substring(0, path.lastIndexOf("/"));
			}
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			logger.error("生成文件目录出错！", e);
		}
	}

	/**
	 * 生成word文件路径
	 * 
	 * @return
	 */
	public static String getNewFileName() {
		try {
			SecureRandom sr = new SecureRandom();
			Date now = new java.sql.Date(System.currentTimeMillis());
			String dateMonth = new SimpleDateFormat("yyyyMM").format(now);
			String fileName = new SimpleDateFormat("yyyyMMddHHmmsss")
					.format(now);
			for (int j = 0; j < 4; j++) {
				fileName += sr.nextInt(10);
			}
			fileName += ".doc";
			return dateMonth + "/" + fileName;
		} catch (Exception e) {
			logger.error("获取文件路径出错！", e);
		}
		return "";
	}

	/**
	 * 文件转字节数组
	 * 
	 * @param filePath
	 *            文件路径或文件名
	 * @return
	 */
	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			logger.error("文件未找到！", e);
		} catch (IOException e) {
			logger.error("读写文件出错！", e);
		}
		return buffer;
	}

	/**
	 * 文件转字节数组
	 * 
	 * @param filePath
	 *            文件路径或文件名
	 * @return
	 */
	public static byte[] File2byteByUrl(String filePath) {
		byte[] buffer = null;
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			url = new URL(filePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream(); // 得到网络返回的输入流
			byte[] b = new byte[1024];
			int n;
			while ((n = is.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			is.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (MalformedURLException e) {
			logger.error("url错误！", e);
		} catch (IOException e) {
			logger.error("文件读写错误！", e);
		}
		return buffer;
	}

	/**
	 * 根据传入的地址获取文件大小
	 * 
	 * @param filePath
	 *            网络文件路径
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long filesize = 0;
		URL url = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			url = new URL(filePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
			filesize = conn.getContentLengthLong();
			if (filesize > 0) {
				filesize /= 1024;
			}
			bos.close();
			return filesize;
		} catch (MalformedURLException e) {
			logger.error("url错误！", e);
		} catch (IOException e) {
			logger.error("文件读写错误！", e);
		}
		return 0;
	}

	/**
	 * 字节数组转文件
	 * 
	 * @param buf
	 *            字节数组
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 */
	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			logger.error("字节数组转文件出错！", e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("关闭输出流出错！", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("关闭文件输出流出错！", e);
				}
			}
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param filepath
	 *            文件路径或文件名
	 * @return
	 */
	public static String getFileSuffix(String filepath) {
		String fileSuffix = "";
		try {
			if (StringUtils.isNotEmpty(filepath)) {
				fileSuffix = filepath.substring(filepath.lastIndexOf("."),
						filepath.length());
				fileSuffix = fileSuffix.toLowerCase();
			}
		} catch (Exception e) {
			logger.error("获取文件后缀名出错！", e);
		}
		return fileSuffix;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileName(String filepath) {
		String filename = "";
		try {
			File file = new File(filepath);
			filename = file.getName();
		} catch (Exception e) {
			logger.error("获取文件名出错！", e);
		}
		return filename;
	}
    
    
    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传图片保存到服务器
     * @param file
     * @param catPath
     * @return
     */
    public static String uploadFile(MultipartFile file, String catPath){
        String fileName = null;
        String basePath = props.getProperty("FILE_SAVE_PATH");
        if(basePath==null){
            logger.error("Error: config.properties {FILE_SAVE_PATH} is null!!");
            return null;
        }

        File fileDir = new File(basePath + catPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        int index = 0;
        File newFile = null;
        String datePath = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD);
        do {
            index ++;
            fileName = datePath + "_" + index + "." + getFileType(file);
            newFile = new File(fileDir, fileName);
        } while (newFile.exists());

        try {
            file.transferTo(newFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 删除文件
     * @param session
     * @param filePath
     */
    public static void deleteFile(HttpSession session, String filePath){
        String basePath = session.getServletContext().getRealPath("/");
        File file = new File(basePath + filePath);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
    }

    public static String getFileType(MultipartFile file){
        String fileName = file.getOriginalFilename();
        return fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
    }
}

