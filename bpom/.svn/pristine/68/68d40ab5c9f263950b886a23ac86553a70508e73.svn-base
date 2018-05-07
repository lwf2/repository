package com.aspire.bpom.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * csv文件的读取和生成
 * 
 * @author liuweifeng
 * 
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	private static final String SET_PREFIX = "set";
	private static final String GET_PREFIX = "get";

	/**
	 * 读取CSV文件 注意，在实体中一定要有 public SignRelationshipReq newEntity(){ return new
	 * SignRelationshipReq(); }类似的方法，不然不能读取数据所有数据，只能重复的读取最后一条数据。
	 * 
	 * @param t
	 *            实体类对象
	 * @return 返回List<实体类>
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public static <T> int readCSVFile(T t, String filePath,
			LinkedBlockingQueue<T> list) {
		int count = 0;
		BufferedReader reader = null;
		Class<? extends Object> clazz = null;
		String csvStr = null;
		String[] csvStrArr = null;
		Field[] field = null;
		String fieldName = null;// 属性值
		String setMethod = null;// 方法名称
		String fileType = null;//属性类型
		try {
			File file = new File(filePath);
			File[] fileArr = file.listFiles();
			if (fileArr != null) {
				for (int j = 0; j < fileArr.length; j++) {
					/*reader = new BufferedReader(new FileReader(fileArr[j]));
				CsvReader csvReader = new CsvReader(reader, ',');*/
					CsvReader csvReader = new CsvReader(new BufferedInputStream(
							new FileInputStream(fileArr[j])), ',', Charset.forName("UTF-8"));
					/*csvReader.readHeaders();
				csvReader.getRawRecord();*/// 读取表头信息
					while (csvReader.readRecord()) {// 读取下一条数据，遍历
						csvStr = csvReader.getRawRecord();// 读取一行数据
						System.out.println("获取的一行数据为：" + csvStr);
						csvStrArr = csvStr.split(",");
						clazz = t.getClass();
						field = clazz.getDeclaredFields();					
						for (int i = 0; i < csvStrArr.length; i++) {
							fieldName = field[i].getName();// 获取属性名称
							fileType = field[i].getGenericType().toString();
							// 如果类型是String  
							if ("class java.lang.String".equals(fileType)) { // 如果type是类类型，则前面包含"class "，后面跟类名  
								setMethod = SET_PREFIX
										+ fieldName.substring(0, 1).toUpperCase()
										+ fieldName.substring(1);// 组装set方法
								Method method = clazz.getMethod(setMethod,
										new Class[] { String.class });// 获取方法
								method.invoke(t, new Object[] { csvStrArr[i] });// 调用实体类set方法
							}
							// 如果类型是Integer  
							if ("class java.lang.Integer".equals(fileType)) {  
								setMethod = SET_PREFIX
										+ fieldName.substring(0, 1).toUpperCase()
										+ fieldName.substring(1);// 组装set方法
								Method method = clazz.getMethod(setMethod,
										new Class[] { Integer.class });// 获取方法
								method.invoke(t, new Object[] { Integer.valueOf(csvStrArr[i]) });// 调用实体类set方法 

							}  

							// 如果类型是Double  
							if ("class java.lang.Double".equals(fileType)) {  
								setMethod = SET_PREFIX
										+ fieldName.substring(0, 1).toUpperCase()
										+ fieldName.substring(1);// 组装set方法
								Method method = clazz.getMethod(setMethod,
										new Class[] { Double.class });// 获取方法
								method.invoke(t, new Object[] { Double.valueOf(csvStrArr[i]) });// 调用实体类set方法
							} 						
						}
						list.put(t);// 数据放满后，自动阻塞
						count++;
						// new一个新的实体类，这个方法是固定的，一定得有。
						t = (T) clazz.getMethod("newEntity", new Class[] {})
								.invoke(t, new Object[] {});
					}
				}

			} else {
				logger.error("当前目录下无文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CSVUtil.readCSVFile().error" + e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				System.gc();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
		}
	}

	/**
	 * 生成CSV文件
	 * 
	 * @param filePath
	 *            部署服务器放置生成csv文件的路径
	 * @param fileName
	 *            csv文件名称
	 * @param headArr
	 *            csv文件表头
	 * @param headArr2
	 *            csv文件尾部表头
	 * @param dataList
	 *            csv文件数据
	 * @param dataCount
	 *            csv文件尾部数据
	 * @return
	 */
	@SuppressWarnings({ "finally", "null" })
	public static <T> boolean generateCSVFile(String filePath, String fileName,
			String[] headArr, String[] headArr2, List<T> dataList,
			String[] dataCount) {
		boolean flag = false;
		CsvWriter csvWriter = null;
		FileOutputStream fos = null;
		try {
			File csvFile = new File(filePath + fileName);
			File parent = csvFile.getParentFile();
			if (parent != null || !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();
			fos = new FileOutputStream(csvFile);
			csvWriter = new CsvWriter(fos, ',', Charset.forName("UTF-8"));
			fos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });// 添加BOM信息，在Excel打开，不会乱码
			csvWriter.writeRecord(headArr);// 输出表头
			for (T t : dataList) {
				Class<? extends Object> clazz = t.getClass();
				Field[] field = clazz.getDeclaredFields();
				String[] strArr = new String[field.length];
				for (int j = 0; j < field.length; j++) {
					String fieldName = field[j].getName();
					String getMethod = GET_PREFIX
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Method method = clazz.getMethod(getMethod, new Class[] {});
					String value = (String) method.invoke(t, new Object[] {});
					if (StringUtils.isNotBlank(value)) {
						strArr[j] = value;
					}
				}
				csvWriter.writeRecord(strArr);// 输出内容
			}
			csvWriter.writeRecord(headArr2);// 输出尾记录表头
			csvWriter.writeRecord(dataCount);// 输出尾记录数据
			csvWriter.flush();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CSVUtil.generateCSVFile().error" + e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (csvWriter != null) {
				csvWriter.close();
			}
			return flag;
		}
	}
	
	/**
	 * 生成不带头部的csv格式的txt文件
	 * @param filePath 部署服务器放置生成csv格式文件的路径
	 * @param fileName 文件名称
	 * @param dataList 主体数据
	 * @param dataCount 尾部数据
	 * @return
	 */
	@SuppressWarnings({ "finally", "null" })
	public static <T> boolean generateCSVFile(String filePath, String fileName, 
			List<T> dataList, String[] dataCount) {
		boolean flag = false;
		CsvWriter csvWriter = null;
		FileOutputStream fos = null;
		try {
			File csvFile = new File(filePath + File.separator + fileName);
			File parent = csvFile.getParentFile();
			if (parent != null || !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();
			fos = new FileOutputStream(csvFile);
			csvWriter = new CsvWriter(fos, ',', Charset.forName("UTF-8"));
			fos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });// 添加BOM信息，在Excel打开，不会乱码
			for (T t : dataList) {
				Class<? extends Object> clazz = t.getClass();
				Field[] field = clazz.getDeclaredFields();
				String[] strArr = new String[field.length];
				for (int j = 0; j < field.length; j++) {
					String fieldName = field[j].getName();
					String getMethod = GET_PREFIX
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Method method = clazz.getMethod(getMethod, new Class[] {});
					String value = (String) method.invoke(t, new Object[] {});
					if (StringUtils.isNotBlank(value)) {
						strArr[j] = value;
					}
				}
				csvWriter.writeRecord(strArr);// 输出内容
			}
			csvWriter.writeRecord(dataCount);// 输出尾记录数据
			csvWriter.flush();
			flag = true;
		} catch (Exception e) {
			logger.error("CSVUtil.generateCSVFile().error" + e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (csvWriter != null) {
				csvWriter.close();
			}
			return flag;
		}
	}

	/**
	 * 获取生成csv文件名称
	 * 
	 * @param baseName
	 *            前缀基本名称
	 * @param servPltfmCode
	 *            业务平台代码
	 * @param num
	 *            第几个文件
	 * @return
	 */
	public static String getFileName(String baseName, String servPltfmCode,
			int num) {
		String fileNameNum = null;
		StringBuffer buffer = new StringBuffer(baseName);
		buffer.append("_");
		buffer.append(DateUtil.getTimeStamp(new Date(), "yyyyMMdd"));
		buffer.append("_");
		buffer.append(servPltfmCode);
		buffer.append("_");
		if (num >= 10) {
			fileNameNum = "0" + num;
		} else if (num >= 100) {
			fileNameNum = String.valueOf(num);
		} else {
			fileNameNum = "00" + num;
		}
		buffer.append(fileNameNum);
		buffer.append(".txt");
		return buffer.toString();
	}
	
	/**
	 * 获取生成csv文件名称
	 * 
	 * @param baseName
	 *            前缀基本名称
	 * @param servPltfmCode
	 *            业务平台代码
	 * @param num
	 *            第几个文件
	 * @return
	 */
	public static String getFileName(String baseName, int num) {
		String fileNameNum = null;
		StringBuffer buffer = new StringBuffer(baseName);
		buffer.append("_");
		buffer.append(DateUtil.getTimeStamp(new Date(), "yyyyMMdd"));
		buffer.append("_");
		if (num >= 10) {
			fileNameNum = "0" + num;
		} else if (num >= 100) {
			fileNameNum = String.valueOf(num);
		} else {
			fileNameNum = "00" + num;
		}
		buffer.append(fileNameNum);
		buffer.append(".txt");
		return buffer.toString();
	}

	/**
	 * 移动文件夹下所有文件到一个新的文件夹中
	 * @param oldPath 要移动文件夹路径
	 * @param newPath 移动目的地文件夹路径
	 */
	public static void moveFile (String oldPath, String newPath) throws Exception {
		logger.info("文件夹下文件移动开始！");
		File[] oldFileArr = new File(oldPath).listFiles();
		if (oldFileArr != null) {
			for (int i = 0; i < oldFileArr.length; i++) {
				if (oldFileArr[i].renameTo(new File(newPath + File.separator + oldFileArr[i].getName()))) {  
					logger.info(oldFileArr[i].getName() + "文件移动成功！");
				} else {  
					logger.error(oldFileArr[i].getName() + "文件移动失败！");
					throw new Exception(oldFileArr[i].getName() + "文件移动失败！");
				}
			}
		} else {
			logger.error("目录下无文件");
		}
		logger.info("文件夹下文件移动结束！");
	}
	
	public static void copy(String src, String des) {  
		logger.info("文件拷贝开始！");
        File file1=new File(src);  
        File[] fs=file1.listFiles();  
        File file2=new File(des);  
        if(!file2.exists()){  
            file2.mkdirs();  
        }  
        for (File f : fs) {  
            if(f.isFile()){  
                fileCopy(f.getPath(),des+f.getName()); //调用文件拷贝的方法  
            }else if(f.isDirectory()){  
                copy(f.getPath(),des+f.getName());  
            }  
        }  
        logger.info("文件拷贝成功！"); 
    }
	
	private static void fileCopy(String src, String des) {  
	      
        BufferedReader br=null;  
        PrintStream ps=null;  
          
        try {  
            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps=new PrintStream(new FileOutputStream(des));  
            String s=null;  
            while((s=br.readLine())!=null){  
                ps.println(s);  
                ps.flush();  
            }  
              
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
              
                try {  
                    if(br!=null)  br.close();  
                    if(ps!=null)  ps.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                  
        }  
          
          
    }
	
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @param dir
	 * @return
	 */
	public static boolean deleteFiles(File dir){
		logger.info("删除文件夹下文件开始!");
		boolean flag = false;
		if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            //循环删除文件夹下文件
            for (int i=0; i<children.length; i++) {
            	flag = children[i].delete();
            	if(!flag){
            		logger.error("删除文件失败！文件名称为" + children[i].getName());
            	}
            }
        }
		logger.info("删除文件夹下文件结束！");
        return flag;
	}
	public static void main(String[] args) throws Exception {
		/*SignRelationshipReq sign = new SignRelationshipReq();
		List<SignRelationshipReq> list = CSVUtil.readCSVFile(sign);
		System.out.println(list.size());
		for (SignRelationshipReq signRelationshipReq : list) {
			System.out.println(signRelationshipReq.toString());
		}*/

		
		 /*String[] headArr = {"业务平台ID", "委托代扣协议id", "协议模板id", "签约协议号", "协议状态", "协议签署时间", "协议到期时间", "协议解约时间", "协议解约方式", "解约备注", "微信用户标识"}; 
		 String[] headArr2 = {"总记录数", "签约用户记录条数", "解约用户记录条数"}; 
		 //数据 
		 List<SignRelationshipReq> dataList = new ArrayList<SignRelationshipReq>(); 
		 SignRelationshipReq sign = null; for (int i = 0; i < 10; i++) { 
		 sign = new SignRelationshipReq(); 
		 sign.setServPltfmCode("caicai" + i);
		 sign.setWxContract_id("财财委托财财" + i); 
		 sign.setWxPlan_id("财财的末班ID" +i); 
		 sign.setWxContract_code("rwk200149");
		 sign.setContract_state("1");
		 sign.setContract_signed_time(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 sign.setContract_expired_time(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 sign.setContract_terminated_time(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 sign.setContract_termination_mode(""+ i);
		 sign.setCancelRemark("xiaoapngnihaoma" + i);
		 sign.setWxOpenid("weixin8888455." + i);
		 dataList.add(sign); 
		 } 
		  
		 String[] dataCount = {"10", "6", "4"};
		 System.out.println(FileUtil.generateCSVFile("D:/xiaopang/liuweifeng/",
		 "xiaopang2.txt", headArr, headArr2, dataList, dataCount));*/
		
		//--------------------------------生成订单测试文件

		/*String[] headArr = {"业务平台ID", "订单号", "交易类型", "外部交易ID", "提交支付时间", "第三方支付机构代码", "支付通道代码", "支付方式代码", "交易金额", "交易完成时间"};		
		String[] headArr2 = {"总记录数", "支付记录条数", "支付金额", "退款记录条数", "退款金额"};
		
		List<PayTransReconReq> dataList = new ArrayList<PayTransReconReq>(); 
		PayTransReconReq payTran = null;
		for (int i = 0; i < 22; i++) {
			payTran = new PayTransReconReq();
			payTran.setServPltfmCode("LWF" + (i + 1));
			payTran.setOrderId("1");
			payTran.setTransType("P");
			payTran.setRequestId("RequestId" + (i + 1));
			payTran.setSubmitTime(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
			payTran.setPayOrganization("WEIXIN" + (i + 1));
			payTran.setPayWap("H5" + (i + 1));
			payTran.setPayType("" + (i + 1));
			payTran.setTotalFee("26000" + (i + 1));
			payTran.setPayTime(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
			dataList.add(payTran);
		}
		String[] dataCount = {"11", "6", "4", "5", "4"};
		System.out.println(FileUtil.generateCSVFile("D:/xiaopang/liuweifeng1/",
		 "PayList_20170915_RWK_002.txt", headArr, headArr2, dataList, dataCount));*/
		
		//FileUtil.moveFile("D:/xiaopang/liuweifeng/", "D:/xiaopang/xiaoxiaopang/");
		//FileUtil.copy("D:/xiaopang/liuweifeng", "D:/xiaopang/xiaoxiaopang/");		
		//FileUtil.deleteFiles(new File("D:/xiaopang/xiaoxiaopang"));
		//System.out.println(DateUtil.getTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		
	}
}
