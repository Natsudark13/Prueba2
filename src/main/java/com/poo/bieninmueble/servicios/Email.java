package com.poo.bieninmueble.servicios;

import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import java.io.IOException;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que permite la gestion de los metodos asociados al correo electronico
 */
public class Email {

  private String emisor = "bieninmueble121@gmail.com";
  private String contrasena = "PrograPOO2020";
  private String host = "smtp.gmail.com";
  private static Email correo;
  private Store storeObj;

  /**
   * Constructor de la clase Email, la cual inicia la ejecucion del hilo para comprobar nuevos
   * correos
   */
  public Email() {
    pullUpNewMail(this);
  }

  /**
   * Metodo para obtener la instancia existente, que contiene la ejecucion del hilo
   *
   * @return Email, la instancia creada inicialmente
   */
  public static Email getInstance() {
    if (correo == null) {
      correo = new Email();
    }
    return correo;
  }

  /**
   * Metodo para comprobar la existencia de nuevos correos cada 3 minutos
   *
   * @param correo Email, de la instancia creada
   */
  public void pullUpNewMail(Email correo) {
    ScheduledExecutorService executor
      = Executors.newSingleThreadScheduledExecutor();

    Runnable periodicTask = new Runnable() {
      public void run() {
        // Invoke method(s) to do the work
        System.out.println("Nuevo ciclo");
        ArrayList<String[]> clientes;
        try {
          clientes = correo.checkMail();

          System.out.println("Revise email");
          for (String[] persona : clientes) {
            if (persona == null) {
              continue;
            }
            System.out.println(persona[0] + persona[1] + persona[2] + persona[3] + persona[4]);
            Cliente cliente = new Cliente(persona[1], persona[2],
              persona[4], persona[3], persona[0]);
            System.out.println("Creo cliente");
            try {
              cliente.insertarCliente(cliente);
              System.out.println("Inserto cliente");

            } catch (SQLException ex) {
              try {
                correo.enviar(persona[3], "Problema en registro de cliente", "<h1>Hubo "
                  + "un problema registrando el cliente, por favor, verifique los datos</<h1>",
                   new ArrayList<byte[]>(), false);
              } catch (IOException ex1) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex1);
              }
              Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }

          }
        } catch (MessagingException ex) {
          Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    };

    executor.scheduleAtFixedRate(periodicTask, 0, 3, TimeUnit.MINUTES);
  }

  /**
   * Método el cual permite crear la conexion con el servidor imap, para comprobar nuevos correos
   * existentes
   *
   * @throws NoSuchProviderException
   * @throws MessagingException
   */
  private void connection() throws NoSuchProviderException, MessagingException {
    String hostval = "imap.googlemail.com";
    String mailStrProt = "993";
    //Set property values
    Properties propvals = new Properties();
    propvals.setProperty("mail.imaps.startttls.enable", "true");
    propvals.setProperty("mail.imaps.usecocketchannels", "true");
    propvals.put("mail.imap.ssl.trust", host);
    propvals.setProperty("mail.imaps.port", mailStrProt);
    propvals.setProperty("mail.debug", "true");
    Session emailSessionObj = Session.getDefaultInstance(propvals);
    //Create POP3 store object and connect with the server
    storeObj = emailSessionObj.getStore("imaps");
    storeObj.connect(hostval, emisor, contrasena);
  }

  /**
   * Método para enviar el correo, con el contenido y datos adjuntos
   *
   * @param pDestinatario A quien va dirigido el correo
   * @param pAsunto Asunto del correo
   * @param pContenido Texto contenido en el correo
   * @param pAdjuntos Conjunto de bytes para ser convertidos a imagenes y posteriormente cargadas en
   * el correo
   */
  public void enviar(String pDestinatario, String pAsunto, String pContenido, ArrayList<byte[]> pAdjuntos,
    boolean agente) throws IOException {

    // Get system properties
    Properties properties = System.getProperties();
      // Setup mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(emisor, contrasena);
      }
    });

    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);
      Multipart multipart = new MimeMultipart();
      MimeBodyPart messageBodyPart = new MimeBodyPart();

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(emisor));
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(pDestinatario));
      // Set Subject: header field
      message.setSubject(pAsunto);
      // Now set the actual message
      String archivo;
      for (int i = 0; i < pAdjuntos.size() + 1; i++) {
        BodyPart attachmentBodyPart = new MimeBodyPart();
        if (agente && pAdjuntos.size() == i ) {
          archivo = "datosAgente.png";
        } else if (!agente && pAdjuntos.size() == i ) {
          continue;
        }else {
          archivo = "Imagen" + i + ".jpg";
          guardarImagen(pAdjuntos.get(i), archivo);
        }
        DataSource source = new FileDataSource("./" + archivo);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(archivo);
        multipart.addBodyPart(attachmentBodyPart);
      }

      messageBodyPart.setContent(pContenido, "text/html");
      multipart.addBodyPart(messageBodyPart);
      // Set the Multipart's to be the email's content
      message.setContent(multipart);
      // Send message
        System.out.println(pDestinatario);
        System.out.println(pAsunto);
        System.out.println(pContenido);
        System.out.println(this.getTextFromMessage(message));
        System.out.println(this.getTextFromMimeMultipart((MimeMultipart)multipart));
      Transport.send(message);
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }

  /**
   * Este método se encarga de obtener los correos no leídos y retorna la informacion de nuevos
   * clientes por registrar en caso de existir
   *
   * @return Una lista con los nuevos clientes
   * @throws MessagingException
   */
  public ArrayList<String[]> checkMail() throws MessagingException {
    ArrayList<String[]> clientes = new ArrayList<>();
    connection();
    try {
      //Create folder object and open it in read-only mode
      Folder inbox = storeObj.getFolder("INBOX");
      inbox.open(Folder.READ_WRITE);
      //Fetch messages from the folder and print in a loop
      Message[] correos = inbox.search(
        new FlagTerm(new Flags(Flags.Flag.SEEN), false));
      System.out.println("Cantidad correos sin leer: " + correos.length);
      for (int i = 0, n = correos.length; i < n; i++) {
        Message correo = correos[i];
        clientes.add(parseCorreo(correo));
        correo.setFlag(Flags.Flag.SEEN, true);
      }
      //parseCorreo(correosLista);
      //Now close all the objects
      inbox.close(false);
      storeObj.close();
      storeObj = null;
    } catch (NoSuchProviderException exp) {
      exp.printStackTrace();
    } catch (MessagingException exp) {
      exp.printStackTrace();
    } catch (Exception exp) {
      exp.printStackTrace();
    }
    return clientes;
  }

  /**
   * Este método, obtiene la informacion del objeto message, procedente de los correos
   *
   * @param message es el objeto del correo
   * @return El texto contenido en el correo
   * @throws MessagingException
   * @throws IOException
   */
  private String getTextFromMessage(Message message) throws MessagingException, IOException {
    String result = "";
    if (message.isMimeType("text/plain")) {
      result = message.getContent().toString();
    } else if (message.isMimeType("multipart/*")) {
      MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
      result = getTextFromMimeMultipart(mimeMultipart);
    }
    return result;
  }

  /**
   * Este metodo se encarga de obtener la informacion del objeto Mime del correo
   *
   * @param mimeMultipart
   * @return La ifnormacion contenida en el correo
   * @throws MessagingException
   * @throws IOException
   */
  private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
    String result = "";
    int count = mimeMultipart.getCount();
    for (int i = 0; i < count; i++) {
      BodyPart bodyPart = mimeMultipart.getBodyPart(i);
      if (bodyPart.isMimeType("text/plain")) {
        result = result + "\n" + bodyPart.getContent();
        break; // without break same text appears twice in my tests
      } /*else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } */ else if (bodyPart.getContent() instanceof MimeMultipart) {
        result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
      }
    }
    return result;
  }

  /**
   * Este metodo se encarga de obtener la informacion del cliente contenida en el mensaje de texto
   *
   * @param pCorreo
   * @return una lista con los valores atomicos del cliente
   * @throws MessagingException
   * @throws IOException
   */
  public String[] parseCorreo(Message pCorreo) throws MessagingException, IOException {
    String nombre, apellido, identificacion, email, numero, asunto, contenido;
    asunto = pCorreo.getSubject().replace("\n", "");
    if (!"Nuevo Cliente".equals(asunto)) {
      return null;
    }
    email = pCorreo.getFrom()[0].toString().replace("\n", "").replace("\t", "");
    if (email.contains("<")) {
      email = email.substring(email.indexOf("<") + 1, email.length() - 1);
    }
    contenido = getTextFromMessage(pCorreo).replace("\n", "").replace("\t", "");
    String partes[] = contenido.split(",");
    identificacion = partes[1].replace("\n", "").replace("\t", "").trim();
    numero = partes[2].replace("\n", "").replace("\t", "").trim();
    nombre = partes[0].split(" ")[0].replace("\n", "").replace("\t", "").trim();
    apellido = partes[0].split(" ")[1].replace("\n", "").replace("\t", "").trim();
    return new String[]{identificacion, nombre, apellido, email, numero};
  }

  /**
   * Este metodo se encarga de crear la imagen y guardarla en la carpeta del proyecto
   *
   * @param info Es la informacion de la imagen en bytes
   * @param pImagen Nombre con el cual se desea guardar la imagen
   */
  private void guardarImagen(byte[] info, String pImagen) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(info);
    BufferedImage imagen = ImageIO.read(bis);
    ImageIO.write(imagen, "jpg", new File(pImagen));
  }
}
