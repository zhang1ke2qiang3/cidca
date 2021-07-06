package com.cidca.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cidca.util.DateTools;

public class Constants {
	public final static String SESSION_KEY="User";
	public final static String DEVELOPMENT_URL="http://localhost:8080/fas/";
	public final static String AMPURL="http://www.cidca.gov.cn/cidca/";
	public final static String SEVERURL="http://www.cidca.gov.cn/cidca/upload/headphoto/";

	public static final String ATTACHFILE_PATH="/upload/attachfile/";
	public static final String EMAILFILES_PATH="/upload/emailFiles/";
	public static final String HEADPHOTO_PATH="/upload/headphoto/";
	public static final String NOTICE_PATH="/upload/notice/";
	public static final String PDF_PATH="/upload/pdf/";
	public static final String TOOLBOX_PATH="/upload/toolbox/";
	public static final String SXAP_PATH="/upload/sxap/";
	public static final String MNPS_PATH="/upload/mnps/";
	public static final String JZBG_PATH="/upload/jzbg/";
	public static final String AJSC_PATH="/upload/ajsc/";
	public static final String ZGCZ_PATH="/upload/zgcz/";
	public static final String CULTIVATE_PATH="/upload/pxtz/";
	public static final String TJBG_PATH="/upload/tjbg/";
	public static final String JJZJ_PATH="/upload/jjzj/";
	public static final String LEAVE_PATH="/upload/leave/";

	public static final String ZERO="0";
	public static final String ONE="1";
	public static final String TWO="2";
	public static final String THREE="3";
	public static final String FOUR="4";
	public static final String FIVE="5";

	//	综合业务部门：
	public final static int ZHYWB_RKJD=8;//	认可监督部	
	public final static int ZHYWB_RKPD=9;//	认可评定部
	public final static int ZHYWB_PSYB=10;//	评审员管理部
	public final static int ZHYWB_JSYJ=11;//	技术研究部
	public final static int ZHYWB_ZLGL=12;//	质量管理部
	
	/**学历-本科**/
	public final static int EDU_181=181;
	/**学历-硕士研究生**/
	public final static int EDU_182=182;
	/**学历-博士研究生**/
	public final static int EDU_183=183;
	/**学历-专科**/
	public final static int EDU_180=180;
	/**学历-其他**/
	public final static int EDU_185=185;
	
	/**证件类型-身份证**/
	public final static int ATTACHTYPE_IDCARD=227;
	/**证件类型-学历学位证**/
	public final static int ATTACHTYPE_EDUCATION=228;
	/**证件类型-职称证明**/
	public final static int ATTACHTYPE_PRO=229;
	/**证件类型-业务成就**/
	public final static int ATTACHTYPE_ACHIEVEMENT=230;
	/**证件类型-fas培训**/
	public final static int ATTACHTYPE_TRAIN_fas=233;
	/**证件类型-其他类型**/
	public final static int ATTCHTYPE_OTHER=232;

	/**
	 * sessionFilterContent
	 * session登录验证/ session验证/ 检索词别动
	 * checkPhoneNum：登录时
	 * checkMobile：注册时
	 */
	public final static String sfc[]={"login.do","loginning.do","sessionFilter.do","inputRegisterCheckIdcard.do","checkIdcardByRegister.do",
			"inputRegisterPwdReset.do","saveRegisterPwdReset.do","saveMuser.do","getMuserBaseData.do","saveOrUpdateMuserEducational.do",
			"saveOrUpdateMuserWork.do","inputRegisterPwd.do","saveOrUpdateMuserTraining.do","saveOrUpdateMuserBusiness.do",
			"saveOrUpdateMuserQualifications.do","saveRegisterPwd.do","getMuserEducationalList.do","deleteMuserEducational.do","getMuserWorkList.do",
			"inputRegister.do","deleteMuserWork.do","getMuserTrainingList.do","deleteMuserTraining.do","getMuserBusinessList.do",
			"deleteMuserBusiness.do","getMuserQualificationsList.do","deleteMuserQualifications.do","UploadPdfFile.do",
			"selectRoleOfGuide.do","guideList.do","getGuideList.do","welcomeGuide.do","getGuideBySequenceNumber.do","config.do",
			"checkPhoneNum.do","checkMobile.do","ssoLogin.do","getVerificationCode.do","error405.do","error404.do","error400.do",
			"error500.do","errorNoChrome.do","operationsFrequently.do","testFunction.jsp","testFunction2.jsp","testFunction3.jsp"};

	//文件类型/文件后缀名/附件后缀 检索词别动
	public final static String IMG_SUFFIX[]={"BMP","GIF","JPG","JPEG","JPEG2000","PNG","TIFF"};
	public final static String OFFICE_SUFFIX[]={"DOC","DOCX","PDF","PPT","PPTX","RTF","TXT","XLS","XLSX"};
	public final static String DINGEROUS_SUFFIX[]={"EXE","BAT","MSI","COM","DLL","SYS"};
	public final static String PDFFILE="PDF";
	public final static String WORDFILE2010="DOC";
	public final static String WORDFILE2013="DOCX";
	public final static String EXCELFILE2010="XLS";
	public final static String EXCELFILE2013="XLSX";
	
	/**时间交叉验证:参数a不在参数b和c之间-2018-07-04**/
	public static boolean isInDate(String date, String strDateBegin,String strDateEnd) throws Exception{
		if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(strDateBegin) && StringUtils.isNotEmpty(strDateEnd)) {
			try {
				String strDate =date.replace("-", "");
				int newkssj = Integer.parseInt(strDate=strDate.replace("-", ""));
				int kssj = Integer.parseInt(strDateBegin=strDateBegin.replace("-", ""));
				int jssj = Integer.parseInt(strDateEnd=strDateEnd.replace("-", ""));
				if (newkssj >=kssj && newkssj <= jssj) {
					return true;//在范围内
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	//判断"数值类型"变量的位数，从Integer源码中抄过来的，因为Integer源码中该方法是私有的，还得反射太麻烦 
	final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
			99999999, 999999999, Integer.MAX_VALUE };
	public static int stringSize(int x) {
		for (int i=0; ; i++)
			if (x <= sizeTable[i])
				return i+1;
	}

	//获取文件路径 获取文件目录 附件目录 附件路径 get filepath get fileurl 检索词别动 20190929
	public static final HashMap<String, String> FILEPATH=new HashMap<String, String>();
	public static String getFilePath(String para){
		if(FILEPATH.size()<1){
			FILEPATH.put("types1","/upload/attachfile/nbpx/");
			FILEPATH.put("types2","/upload/attachfile/qtlx/");
			FILEPATH.put("types3","/upload/attachfile/sfz/");
		}
		String name=(String)FILEPATH.get(para);
		return name!=null?name:"";
	}
	

	//缓存提交状态-防重复提交
	@SuppressWarnings("rawtypes")
	public static Map cachesubmitmap =new HashMap();
	
	//
	@SuppressWarnings("rawtypes")
	public static Map errorMap =new HashMap();
	
	//防SQL注入 过滤器
	public static final HashMap<Integer,String> SqlInjectMap= new HashMap<Integer,String>();
	static{
		SqlInjectMap.put(0,"exec");
		SqlInjectMap.put(1,"insert");
		SqlInjectMap.put(2,"delete");
		SqlInjectMap.put(3,"update");
		SqlInjectMap.put(4,"alter");
		SqlInjectMap.put(5,"truncate");
		SqlInjectMap.put(6,"drop");
		SqlInjectMap.put(7,"create");
	}
	
	//防止请求.do或者频繁刷新
	@SuppressWarnings("rawtypes")
	public static Map checkCountMap =new HashMap();
	@SuppressWarnings("unchecked")
	public static boolean getFrequency(String clientIpAddress ,Date date) {
		boolean result=false;
		try {
			Date isSubmit=(Date)Constants.checkCountMap.get(clientIpAddress);
			if (null!=isSubmit) {
				long showTimeInterval= DateTools.ShowTimeInterval(isSubmit, new Date());
				if (showTimeInterval>3) {
					result=true;
					Constants.checkCountMap.put(clientIpAddress,date);//放入本次访问时间
				}
			}else {
				//第一次进入方法
				result=true;
				Constants.checkCountMap.put(clientIpAddress,date);//放入本次访问时间
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//跨站白名单
	public static final HashMap<Integer,String> IpWhiteMap= new HashMap<Integer,String>();
	static{
		IpWhiteMap.put(0,"https://www.fas.org.cn");
		IpWhiteMap.put(1,"https://las.fas.org.cn");
		IpWhiteMap.put(2,"http://localhost");
		IpWhiteMap.put(3,"http://127.0.0.1");//本地测试用
		IpWhiteMap.put(4,"https://fas.fas.org.cn");
		IpWhiteMap.put(5,"https://mail.126.com");
		IpWhiteMap.put(6,"https://mail.163.com");
		IpWhiteMap.put(7,"https://mail.qq.com");
		IpWhiteMap.put(8,"https://appmail.mail.10086.cn");
		IpWhiteMap.put(9,"https://mail.sina.com.cn");
		IpWhiteMap.put(10,"https://mail.sohu.com");
	}
			
}
