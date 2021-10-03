package com.poo.bieninmueble.servicios;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase encargada de la generacion de un codigo QR dependiendo de la cadena de caracteres
 * necesarios
 */
public class GeneradorQR {

  /**
   * Constructor de la clase GeneradorQr
   */
  public GeneradorQR() {
  }

  /**
   * Crea una imagen de un codigo QR con la informacion necesaria
   * 
   * @param texto Texto que se encripta en el QR
   * @throws WriterException En caso de que se genere un error con la escritura del archivo
   * @throws IOException En caso de un error con la creacion de la imagen
   */
  public void createQRImage(String texto) throws WriterException, IOException {
    String filePath = "datosAgente.png";
    int size = 125;
    String fileType = "png";
    File qrFile = new File(filePath);
    Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, 
      ErrorCorrectionLevel>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix byteMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, size, size, hintMap);
    int matrixWidth = byteMatrix.getWidth();
    BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
    image.createGraphics();
    Graphics2D graphics = (Graphics2D) image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, matrixWidth, matrixWidth);
    graphics.setColor(Color.BLACK);
    for (int i = 0; i < matrixWidth; i++) {
      for (int j = 0; j < matrixWidth; j++) {
        if (byteMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    ImageIO.write(image, fileType, qrFile);
  }
}
