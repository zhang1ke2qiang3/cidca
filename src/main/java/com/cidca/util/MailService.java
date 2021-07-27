package com.cidca.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;

public class MailService {
	
	/**
	 * 描述：发邮件--cidca
	 */
	public static void SendMail(String tomail,String title,String msg) throws Exception {
		
		String qm ="123456789"; //您的邮箱密码
		String tu = "cidca.org.cn";//你邮箱的后缀域名
		String tto=tomail;//接收邮件的邮箱
		String tcontent=msg;
		Properties props=new Properties();
		props.put("mail.smtp.host","mail."+tu);	//发信的主机，这里我填写的是我们公司的主机！可以不用修改！
		props.put("mail.smtp.auth","true"); 
		Session s=Session.getInstance(props);
		s.setDebug(false);
		MimeMessage message=new MimeMessage(s);
		
		//给消息对象设置发件人/收件人/主题/发信时间
		InternetAddress from=new InternetAddress("cidca@"+tu); //发件箱
		message.setFrom(from);
		InternetAddress to=new InternetAddress(tto);
		message.setRecipient(Message.RecipientType.TO,to);
		message.setSubject(title);
		message.setSentDate(new Date());
		
		//给消息对象设置内容
		BodyPart mdp=new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
		mdp.setContent(tcontent,"text/html;charset=gb2312");//给BodyPart对象设置内容和格式/编码方式
		Multipart mm=new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对//象(事实上可以存放多个)
		mm.addBodyPart(mdp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
		message.setContent(mm);//把mm作为消息对象的内容
		message.saveChanges();
		Transport transport=s.getTransport("smtp");
		transport.connect("mail."+tu,"cidca@cidca.org.cn",qm); //这里的115798090也要修改为您的QQ号码 
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
		
	}
	 
	/**
	 * 描述：发邮件--cidca
	 */
	public static void SendMail(String tomail,String ccmail,String title,String msg) throws Exception {
		
		String qm ="faas@cidca"; //您的邮箱密码
		String tu = "cidca.org.cn"; 	//你邮箱的后缀域名
		String tto=tomail; //接收邮件的邮箱
		String tcontent=msg;
		Properties props=new Properties();
		props.put("mail.smtp.host","mail."+tu);	//发信的主机，这里我填写的是我们公司的主机！可以不用修改！
		props.put("mail.smtp.auth","true"); 
		Session s=Session.getInstance(props);
		s.setDebug(false);
		MimeMessage message=new MimeMessage(s);
		
		//给消息对象设置发件人/收件人/主题/发信时间
		InternetAddress from=new InternetAddress("cidca@"+tu); 
		message.setFrom(from);
		InternetAddress to=new InternetAddress(tto);
		message.setRecipient(Message.RecipientType.TO,to);
		message.setSubject(title);
		message.setSentDate(new Date());
		if(StringUtils.isNotEmpty(ccmail)){
			InternetAddress cc=new InternetAddress(ccmail);
			message.setRecipient(Message.RecipientType.CC, cc); //抄送人
		}
		
		//给消息对象设置内容
		BodyPart mdp=new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
		mdp.setContent(tcontent,"text/html;charset=gb2312");//给BodyPart对象设置内容和格式/编码方式
		Multipart mm=new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对//象(事实上可以存放多个)
		mm.addBodyPart(mdp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
		message.setContent(mm);//把mm作为消息对象的内容
		message.saveChanges();
		Transport transport=s.getTransport("smtp");
		transport.connect("mail."+tu,"cidca@cidca.org.cn",qm); //这里的115798090也要修改为您的QQ号码 
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
		
	}
	
	/**
	 * 描述：发邮件
	 * tomails-多个收件人
	 * ccmails-多个抄送人
	 * files-多个附件
	 * String[] tomails,String[] ccmails,两个都是收件人
	 */
	public static void SendMail(String[] tomails,String[] ccmails,String[] files,String title,String msg) throws Exception {
		
		String qm ="cidca@cidca"; //您的邮箱密码
		String tu = "cidca.org.cn"; 	//你邮箱的后缀域名
		Properties props=new Properties();
		props.put("mail.smtp.host","mail."+tu);	//发信的主机，这里我填写的是我们公司的主机！可以不用修改！
		props.put("mail.smtp.auth","true"); 
		Session s=Session.getInstance(props);
		s.setDebug(false);
		MimeMessage message=new MimeMessage(s);
		
		//给消息对象设置发件人/收件人/主题/发信时间
		InternetAddress from=new InternetAddress("cidca@"+tu);
		message.setFrom(from);
		//收件人
		if(tomails!=null){
			InternetAddress[] addrArr = new InternetAddress[tomails.length];
			for(int i=0;i<tomails.length;i++){
				addrArr[i]=new InternetAddress(tomails[i]);
			}
			message.setRecipients(Message.RecipientType.TO,addrArr);
		}
		//抄送人
		if(ccmails!=null && ccmails.length>0){
			InternetAddress[] addrArr = new InternetAddress[ccmails.length];
			for(int i=0;i<ccmails.length;i++){
				if (StringUtils.isNotEmpty(ccmails[i])) {
					addrArr[i]=new InternetAddress(ccmails[i]);
				}
			}
			message.setRecipients(Message.RecipientType.CC, addrArr); 
		}
		//标题
		message.setSubject(title);
		//日期
		message.setSentDate(new Date());
		//内容
		BodyPart mdp=new MimeBodyPart();
		mdp.setContent(msg,"text/html;charset=gb2312");
		Multipart mm=new MimeMultipart();
		mm.addBodyPart(mdp);
		//附件
		if(files!=null){
			for(int i=0;i<files.length;i++){
				BodyPart bp = new MimeBodyPart();
				FileDataSource fields = new FileDataSource(files[i]);
				bp.setDataHandler(new DataHandler(fields));
				bp.setFileName(MimeUtility.encodeWord(fields.getName(),"gb2312", null));
				mm.addBodyPart(bp);
			}
		}
		message.setContent(mm);
		message.saveChanges();
		
		Transport transport=s.getTransport("smtp");
		transport.connect("mail."+tu,"cidca@cidca.org.cn",qm); //这里的115798090也要修改为您的QQ号码 
		transport.sendMessage(message,message.getAllRecipients());//这一步出的错
		transport.close();
		
	}
	
	public static void SendMailOfIndividual(String[] tomails,String[] ccmails,String[] files,String title,String msg,String fjrEmail,String qm,String tu) throws Exception {
		
		Properties props=new Properties();
		props.put("mail.smtp.host","mail."+tu);	//发信的主机，这里我填写的是我们公司的主机！可以不用修改！
		props.put("mail.smtp.auth","true"); 
		Session s=Session.getInstance(props);
		s.setDebug(false);
		MimeMessage message=new MimeMessage(s);
		
		//给消息对象设置发件人/收件人/主题/发信时间
//		InternetAddress from=new InternetAddress("cidca@"+tu);
		InternetAddress from=new InternetAddress(fjrEmail);
		message.setFrom(from);
		//收件人
		if(tomails!=null){
			InternetAddress[] addrArr = new InternetAddress[tomails.length];
			for(int i=0;i<tomails.length;i++){
				addrArr[i]=new InternetAddress(tomails[i]);
			}
			message.setRecipients(Message.RecipientType.TO,addrArr);
		}
		//抄送人
		if(ccmails!=null){
			InternetAddress[] addrArr = new InternetAddress[ccmails.length];
			for(int i=0;i<ccmails.length;i++){
				
				addrArr[i]=new InternetAddress(ccmails[i]);
			}
			message.setRecipients(Message.RecipientType.CC, addrArr); 
		}
		//标题
		message.setSubject(title);
		//日期
		message.setSentDate(new Date());
		//内容
		BodyPart mdp=new MimeBodyPart();
		mdp.setContent(msg,"text/html;charset=gb2312");
		Multipart mm=new MimeMultipart();
		mm.addBodyPart(mdp);
		//附件
		if(files!=null){
			for(int i=0;i<files.length;i++){
				BodyPart bp = new MimeBodyPart();
				FileDataSource fields = new FileDataSource(files[i]);
				bp.setDataHandler(new DataHandler(fields));
				bp.setFileName(MimeUtility.encodeWord(fields.getName(),"gb2312", null));
				mm.addBodyPart(bp);
			}
		}
		message.setContent(mm);
		message.saveChanges();
		
		Transport transport=s.getTransport("smtp");
//		transport.connect("mail."+tu,"cidca@cidca.org.cn",qm); //这里的115798090也要修改为您的QQ号码 
		transport.connect("mail."+tu,fjrEmail,qm); //这里的115798090也要修改为您的QQ号码 
		transport.sendMessage(message,message.getAllRecipients());//这一步出的错
		transport.close();
	}
}
