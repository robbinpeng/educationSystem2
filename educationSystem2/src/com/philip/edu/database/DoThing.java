package com.philip.edu.database;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

public class DoThing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoThing does = new DoThing();
		does.do1();
	}
	
	public void do1(){
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.163.com");
		properties.put("mail.smtp.auth", "true");
		Authentication auth = new Authentication("15901726493","jf829031");
		Session session = Session.getDefaultInstance(properties,auth);

		try {
			MimeMessage message = new MimeMessage(session);
			
			InetAddress ip = getLocalHostLANAddress();
			
			message.setFrom("15901726493@163.com");
			message.addRecipients(Message.RecipientType.TO, "robbinpeng@163.com");
			message.setSubject("education system");
			message.setText("see below: \n" + ip );
			
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
	    try {
	        InetAddress candidateAddress = null;
	        // �������е�����ӿ�
	        for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
	            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
	            // �����еĽӿ����ٱ���IP
	            for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
	                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {// �ų�loopback���͵�ַ
	                    if (inetAddr.isSiteLocalAddress()) {
	                        // �����site-local��ַ����������
	                        return inetAddr;
	                    } else if (candidateAddress == null) {
	                        // site-local���͵ĵ�ַδ�����֣��ȼ�¼��ѡ��ַ
	                        candidateAddress = inetAddr;
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            return candidateAddress;
	        }
	        // ���û�з��� non-loopback��ַ.ֻ�������ѡ�ķ���
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        if (jdkSuppliedAddress == null) {
	            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
	        }
	        return jdkSuppliedAddress;
	    } catch (Exception e) {
	        UnknownHostException unknownHostException = new UnknownHostException(
	                "Failed to determine LAN address: " + e);
	        unknownHostException.initCause(e);
	        throw unknownHostException;
	    }
	}
}
