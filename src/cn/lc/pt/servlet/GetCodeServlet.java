package cn.lc.pt.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCodeServlet extends HttpServlet {

    private static final long serialVersionUID = 4009196939110715594L;

    public GetCodeServlet() {
        super();
    }

    private static int WIDTH=64;                                //定义图片的长度
    private static int HEIGHT=25;                               //定义图片的高度
    public static final String  Content_type="text/html;charset=UTF-8";//设定编码格式

    public void destroy() {
        super.destroy(); 
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setContentType(Content_type);
        HttpSession session=request.getSession();
        //设置响应的文档类型为JPG格式
        response.setContentType("image/jpeg");
        ServletOutputStream sos=response.getOutputStream();
        //设置浏览器不要缓存此图片
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        //创建内存图像并获得其图形上下文
        BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics g=image.getGraphics();
        
        //产生随机的验证码
        char [] rands=generateCheckCode();
        //产生图像
        drawBackground(g);
        drawRands(g , rands);
        
        //结束图像的绘制过程，完成图像
        g.dispose();
        
        //将图像输出到客户端
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", bos);
        byte[]buf=bos.toByteArray();
        response.setContentLength(buf.length);

        bos.writeTo(sos);
        sos.write(buf);
        bos.close();
        sos.close();
        //将当前验证码存入到Session中
        session.setAttribute("check_code", new String(rands));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {
    }
    /**
     * //随机产生4位验证码
     */
    private char[] generateCheckCode(){
        String chars="1234567890";
        char [] rands=new char[4];
        for(int i=0;i<4;i++){
            int rand=(int)(Math.random()*10);
            rands[i]=chars.charAt(rand);
        }
        return rands;
    }
    /**
     * 绘制验证码字体的样式
     */
    private void drawRands(Graphics g,char [] rands){
        g.setColor(Color.BLUE);
        g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
        g.drawString(""+rands[0], 6, 20);
        g.drawString(""+rands[1], 21, 20);
        g.drawString(""+rands[2], 30, 24);
        g.drawString(""+rands[3], 41, 18);
    }
    /**
     * 设定验证码的背景格式
     * @param g
     */
    private void drawBackground(Graphics g){
        //设置画笔颜色
        g.setColor(new Color(0xDCDCDC));
        //画出矩形区域
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i=0;i<120;i++){
            int x=(int)(Math.random()*WIDTH);
            int y=(int)(Math.random()*HEIGHT);
            int red=(int)(Math.random()*255);
            int green=(int)(Math.random()*255);
            int blue=(int)(Math.random()*255);
            g.setColor(new Color(red,green,blue));
            g.drawOval(x, y, 1, 0);
        }
        //画边框
        g.setColor(new Color(128, 128, 128));
        g.drawRect(0,0,WIDTH-2,HEIGHT-2);
    }
    

}
