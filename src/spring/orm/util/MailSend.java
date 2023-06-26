package spring.orm.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import spring.orm.model.output.MailAppOutputModel;

public class MailSend {

	public void sendEmail(String recipient, String subject, String message) {
		// Configure mail server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Set username and password for authentication
		final String username = "madhukar2067@gmail.com";
		final String password = "ygjsartjepgzbmj";

		// Create session with mail server authentication
		javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create message
			Message emailMessage = new MimeMessage(session);
			emailMessage.setFrom(new InternetAddress(username));
			emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			emailMessage.setSubject(subject);
			emailMessage.setText(message);

			// Send message
			Transport.send(emailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendOTPMail(String recipient, String otp) {
		System.out.println("mail1");

		Properties p = new Properties();
		System.out.println("mail2");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.port", "587");
		System.out.println("mail3");
		Session s = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("yathindranikshipth@gmail.com", "nslq npgk ripj lglk");
			}
		});
		try {
			MimeMessage mm = new MimeMessage(s);
			System.out.println("mail4");
			mm.setFrom(new InternetAddress("yathindranikshipth@gmail.com"));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mm.setSubject("Forget Password");
			mm.setText("OTP TO RESELT PASSWORD" + "your otp for password reset is " + otp
					+ " please use it quickly as IT IS ACTIVE FOR 3MIN ONLY");
			System.out.println("mail5");
			Transport.send(mm);
			System.out.println("mail6");
			System.out.println(
					"your otp for password reset is " + otp + " please use it quickly as IT IS ACTIVE FOR 3MIN ONLY");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String generateOTP() {
		int otpLength = 6; // Length of the OTP

		// Generate the OTP
		StringBuilder otp = new StringBuilder();
		while (otp.length() < otpLength) {
			UUID uuid = UUID.randomUUID();
			otp.append(uuid.toString().replaceAll("-", "").substring(0, otpLength - otp.length()));
		}

		return otp.toString();
	}

	public void sendrefundMail(String email, String amount) {
		System.out.println("mail1");

		Properties p = new Properties();
		System.out.println("mail2");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.port", "587");
		System.out.println("mail3");
		Session s = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("yathindranikshipth@gmail.com", "nslq npgk ripj lglk");
			}
		});
		try {
			MimeMessage mm = new MimeMessage(s);
			System.out.println("mail4");
			mm.setFrom(new InternetAddress("yathindranikshipth@gmail.com"));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mm.setSubject("Forget Password");
			mm.setText("Your Appointment has been suspended and amount " + amount + " has been refunded");
			System.out.println("mail5");
			Transport.send(mm);
			System.out.println("mail6");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	public static void sendEmail(HttpServletRequest request, HttpServletResponse response, MailAppOutputModel app,
			String UserMail) throws Exception {
		// Set up model attributes with the variables for JSP replacements

		// Resolve JSP view
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		String viewName = "temp";
		request.setAttribute("appointment", app);
		// Render JSP
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
		StringWriter stringWriter = new StringWriter();
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return new PrintWriter(stringWriter);
			}
		};
		requestDispatcher.include(request, responseWrapper);
		String renderedHtml = stringWriter.toString();
		System.out.println(renderedHtml);

		Properties p = new Properties();
		System.out.println("mail2");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.port", "587");
		System.out.println("mail3");
		Session s = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("yathindranikshipth@gmail.com", "nslq npgk ripj lglk");
			}
		});
		try {
			MimeMessage mm = new MimeMessage(s);
			System.out.println("mail4");
			mm.setFrom(new InternetAddress("yathindranikshipth@gmail.com"));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(UserMail));
			mm.setSubject("Doctor Appointment Booking Details");
			mm.setContent(renderedHtml, "text/html");
			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the email with the rendered HTML

	}

	public static void sendEmail1(HttpServletRequest request, HttpServletResponse response, String mail, String data)
			throws ServletException, IOException {
		// Resolve JSP view
		System.out.println("in mail");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		String viewName = "temp1";
		request.setAttribute("booktest", data);
		// Render JSP
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
		StringWriter stringWriter = new StringWriter();
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return new PrintWriter(stringWriter);
			}
		};
		requestDispatcher.include(request, responseWrapper);
		String renderedHtml = stringWriter.toString();
		System.out.println(renderedHtml);

		Properties p = new Properties();
		System.out.println("mail2");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.port", "587");
		System.out.println("mail3");
		Session s = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("yathindranikshipth@gmail.com", "nslq npgk ripj lglk");
			}
		});
		try {
			MimeMessage mm = new MimeMessage(s);
			System.out.println("mail4");
			mm.setFrom(new InternetAddress("yathindranikshipth@gmail.com"));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
			mm.setSubject("Doctor Appointment Booking Details");
			mm.setContent(renderedHtml, "text/html");
			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the email with the rendered HTML

	}

}