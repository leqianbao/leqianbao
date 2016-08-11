package cn.lc.pt.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.lc.dao.CommodityDao;

/**
 * Servlet implementation class DoFileUpload
 */
@WebServlet("/doFileUpload")
public class DoFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoFileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		CommodityDao dao = new CommodityDao();
        PrintWriter out = response.getWriter();
	    //商品id
	    String commodity_id = request.getParameter("commodity_id");
		//   图片上传路径
		String uploadPath =request.getSession().getServletContext().getRealPath("/")+"upload/images/"+commodity_id+"/";
		//   图片网络相对路径
		String imagePath=request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/";
		//   文件夹不存在就自动创建：
		deleteDir(new File(uploadPath));
	    if(!new File(uploadPath).isDirectory()){
	    	new File(uploadPath).mkdirs();
	    }
	    try {
	    	DiskFileItemFactory factory = new DiskFileItemFactory(); 
	    	ServletFileUpload fu = new ServletFileUpload(factory); 
	    	//   设置最大文件尺寸，这里是4MB
	    	fu.setSizeMax(4194304);
	    	//   得到所有的文件:
			@SuppressWarnings("unchecked")
			List<FileItem> items = fu.parseRequest(request);
			Iterator<FileItem> i = items.iterator();
	    	//   依次处理每一个文件：
	    	while(i.hasNext()) {
	    	   FileItem file = (FileItem)i.next();
	    	   //   获得文件名，这个文件名是用户上传时用户的绝对路径：
	    	   String sourcefileName = file.getName();

	 		   StringBuffer json = new StringBuffer();
	           json.append('{');
	    	   if(sourcefileName!=null
	    			   &&(sourcefileName.endsWith(".jpg")
	    			   			||sourcefileName.endsWith(".png")
	    			   				||sourcefileName.endsWith(".jpeg"))) {
	    		   //   在这里可以记录用户和文件信息,生成上传后的文件名
	    		   String destinationfileName=null;
	    		   Calendar time = Calendar.getInstance();
	    		   if(sourcefileName.endsWith(".jpg")){
	    			   destinationfileName=String.valueOf(time.get(Calendar.YEAR))
	    					   	+ String.valueOf(time.get(Calendar.MONTH))
	    					   	+ String.valueOf(time.get(Calendar.DAY_OF_MONTH))
	    					   	+ String.valueOf(time.get(Calendar.HOUR_OF_DAY))
	    					   	+ String.valueOf(time.get(Calendar.MINUTE))
	    					   	+ String.valueOf(time.get(Calendar.SECOND))
	    					   	+ String.valueOf(time.get(Calendar.MILLISECOND)) + ".jpg";
	    		   }else if(sourcefileName.endsWith(".png")){
	    			   destinationfileName=String.valueOf(time.get(Calendar.YEAR))
	    				   + String.valueOf(time.get(Calendar.MONTH))
	    				   + String.valueOf(time.get(Calendar.DAY_OF_MONTH))
	    				   + String.valueOf(time.get(Calendar.HOUR_OF_DAY))
	    				   + String.valueOf(time.get(Calendar.MINUTE))
	    				   + String.valueOf(time.get(Calendar.SECOND))
	    				   + String.valueOf(time.get(Calendar.MILLISECOND)) + ".png";
	    		   }else if(sourcefileName.endsWith(".jpeg")){
	    			   destinationfileName=String.valueOf(time.get(Calendar.YEAR))
		    				   + String.valueOf(time.get(Calendar.MONTH))
		    				   + String.valueOf(time.get(Calendar.DAY_OF_MONTH))
		    				   + String.valueOf(time.get(Calendar.HOUR_OF_DAY))
		    				   + String.valueOf(time.get(Calendar.MINUTE))
		    				   + String.valueOf(time.get(Calendar.SECOND))
		    				   + String.valueOf(time.get(Calendar.MILLISECOND)) + ".jpeg";
	    		   }
	    		   File f1=new File(uploadPath+ destinationfileName);
	    		   file.write(f1);
	    		   boolean setImg = dao.setCommodityImg(commodity_id, "upload/images/"+commodity_id+"/"+destinationfileName);
	    		   String pathEnd = imagePath.split("http:")[1]+"upload/images/"+commodity_id+"/"+destinationfileName;
	    		   if(setImg){
		               json.append("uploadMsg:").append("图片上传成功！").append(",");
	    		   }else{
	    			   json.append("uploadMsg:").append("图片上传失败！").append(",");
	    		   }
	               json.append("commodity_img_url:").append(pathEnd);
	    	   }else{
	               json.append("uploadMsg:").append("上传文件出错，只能上传 *.jpg , *.png, *.jpeg格式图片文件！");
	    	   }
               json.append("}");
               out.print(json.toString());
               out.close();
	    	}
	    	//   跳转到上传成功提示页面
	    }catch(Exception e) {
	    	//   可以跳转出错页面
	    }
	}
	
	/**
	 * 上传文件前删除之前目录下的图片文件以及目录
	 * */
	private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
