package cn.lc.pt.servlet;

import java.io.File;
import java.io.IOException;
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
		//   图片上传路径
		String uploadPath =request.getSession().getServletContext().getRealPath("/")+"upload/images/";
		//   图片网络相对路径
		String imagePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		//   文件夹不存在就自动创建：
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
	    		   request.setAttribute("commodity_img_url", imagePath+"upload/images/"+destinationfileName);
	    		   request.setAttribute("commodity_real_url", "upload/images/"+destinationfileName);
	    		   request.setAttribute("successMsg", "");
	    	   }else{
	    		   request.setAttribute("errMsg", "上传文件出错，只能上传 *.jpg , *.png格式图片文件");
	    	   }
	    	}
	    	//   跳转到上传成功提示页面
	    }catch(Exception e) {
	    	//   可以跳转出错页面
	    }
	}

}
