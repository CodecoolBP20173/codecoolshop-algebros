package com.codecool.shop.processes;

import com.codecool.shop.model.Order;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class MailProcess {
    static void send(Order order) {
        String name = order.getCheckoutProcess().getBuyerInfo().get("name");
        String zipcode = order.getCheckoutProcess().getBuyerInfo().get("zip");
        String city = order.getCheckoutProcess().getBuyerInfo().get("city");
        String address = order.getCheckoutProcess().getBuyerInfo().get("address");
        String orderId = Integer.toString(order.getId());
        String to = order.getCheckoutProcess().getBuyerInfo().get("email");

        String user = System.getenv("serverEmailName");
        String pass = System.getenv("serverEmailPassword");
        //create an instance of Properties Class
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        String msg = "";
        msg += "Dear " + name + ",\n";
        msg += "\n";
        msg += "Order number : " + orderId + "\n";
        msg += "Your order have been sent to : " + "\n";
        msg += "    " + zipcode + "    " + city + "    " + address + "\n";
        msg += "\n";
        msg += "Sincerely,  Codecool Shop" + "\n";
        msg += "Designed by Algebros";

     
     /* Pass Properties object(props) and Authenticator object   
           for authentication to Session instance 
        */

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
 
 	/* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
 	   */

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Order confirmation");
            message.setText(msg);

            /* Transport class is used to deliver the message to the recipients */

            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}