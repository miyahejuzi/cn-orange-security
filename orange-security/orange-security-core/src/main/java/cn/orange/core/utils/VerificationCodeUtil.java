package cn.orange.core.utils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * 验证码图片生成器
 *
 * @author yr fan
 */
public class VerificationCodeUtil {

    /**
     * width 验证码图片的宽度。 height 验证码图片的高度。 length 验证码图片的个数。
     */
    private int width = 70;
    private int height = 30;
    private int length = 4;
    private String verificationCode;

    public VerificationCodeUtil(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    /**
     * 验证码的数量。
     */
    private Random random = new Random();

    /**
     * 生成随机颜色
     *
     * @param fc 前景色
     * @param bc 背景色
     * @return Color对象，此Color对象是RGB形式的。
     */
    private Color getRandomColor(int fc, int bc) {
        if (fc > 255) {
            fc = 200;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制干扰线
     *
     * @param g    Graphics2D对象，用来绘制图像
     * @param nums 干扰线的条数
     */
    private void drawRandomLines(Graphics2D g, int nums) {
        g.setColor(this.getRandomColor(160, 200));
        for (int i = 0; i < nums; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(12);
            int y2 = random.nextInt(12);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 获取随机字符串， 此函数可以产生由大写字母，数字组成的字符串
     *
     * @param length 随机字符串的长度
     * @return 返回随机的字符串全部由大写字母，和数字构成
     */
    private String drawRandomString(int length, Graphics2D g) {
        StringBuffer strbuf = new StringBuffer();
        String temp = "";
        int itmp = 0;
        for (int i = 0; i < length; i++) {
            switch (random.nextInt(2)) {
//			case 1: // 生成A～Z的字母
//				itmp = random.nextInt(26) + 65;
//				temp = String.valueOf((char) itmp);
//				break;
//			default:
//				itmp = random.nextInt(26) + 97;
//				temp = String.valueOf((char) itmp);
//				break;
                //只生成数字
                default:
                    itmp = random.nextInt(10) + 48;
                    temp = String.valueOf((char) itmp);
                    break;
            }
            Color color = new Color(20 + random.nextInt(20),
                    20 + random.nextInt(20), 20 + random.nextInt(20));
            g.setColor(color);
            // 让文字旋转一定的角度
            AffineTransform trans = new AffineTransform();
            trans.rotate(random.nextInt(30) * 3.14 / 180, 15 * i, 7);
            // 缩放文字
//			float scaleSize = 1.1f;
//			trans.scale(scaleSize, scaleSize);
//			g.setTransform(trans);
            g.drawString(temp, 15 * i + 8, 20);
            strbuf.append(temp);
        }
        g.dispose();
        return strbuf.toString();
    }

    /**
     * @return 返回图片验证码的字符串
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * @return 返回一个图片的输入流
     */
    public InputStream createPictureCheckCode() {
        BufferedImage image = bufferedImageOutputStream();
        InputStream inputStream = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
            ImageIO.write(image, "JPEG", ios);
            inputStream = new ByteArrayInputStream(bos.toByteArray());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * @return 返回一个图片的输入流
     */
    public ByteArrayOutputStream createPictureCode() {
        BufferedImage image = bufferedImageOutputStream();
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
            ImageIO.write(image, "JPEG", ios);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos;
    }

    /**
     * @return 返回一个图片的输入流
     */
    public BufferedImage bufferedImageOutputStream() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        // 定义字体样式
        Font myFont = new Font("黑体", Font.BOLD, 16);
        // 设置字体
        g.setFont(myFont);

        g.setColor(getRandomColor(200, 250));
        // 绘制背景
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getRandomColor(180, 200));
        drawRandomLines(g, 160);

        String picCodeString = drawRandomString(length, g);
        setPictureCheckCodeString(picCodeString);
        g.dispose();
        return image;
    }

    private int getWidth() {
        return width;
    }

    private int getHeight() {
        return height;
    }

    private void setPictureCheckCodeString(String pictureCheckCodeString) {
        this.verificationCode = pictureCheckCodeString;
    }

}
