package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@MultipartConfig(
		fileSizeThreshold = 1024*1024,
		maxFileSize =1024*1024*50,
		maxRequestSize = 1024*1024*50*5)

//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println("ProductController.java.ProductController() "+this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	//@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")4++++++++++
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
	public String addProduct() throws Exception {

		System.out.println("ProductController.java /Product GET");
		
		return "forward:/product/addProductView.jsp";
	}
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product,
							@RequestParam("file") MultipartFile file)
			throws Exception { //
		System.out.println("ProductController.java에 fileUpload메소드 실행됨");

		String savedFileName = ""; // 빈 파일명 설정하고

		String uploadPath = "C:\\workspace\\11.Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles\\";
//		실제 파일 업로드 경로 db에는 파일명만 올라간다
		String originalFileName = file.getOriginalFilename();
//		form에서 실제 파일명 가져오기
		UUID uuid = UUID.randomUUID();
//		자바 aPI에서 유니크 아이디 생성하는 메소드 사용 해서  uuid 생성
		savedFileName = uuid.toString() + "_" + originalFileName;
//		실제 올라간 파일명 보존하기 위해서 uuid+오리지날 아이디를 _로 구분해서 저장 파일명 생성
// 		4. 파일 생성
		File file1 = new File(uploadPath + savedFileName);
//		File 타입의 file1 = 경로+uuid_오리지날아이디로 생성
// 		5. 서버로 전송
		file.transferTo(file1);
// 		파일로 경로+파일명으로 전송한다

// 		model로 저장
		product.setFileName(savedFileName);
//		db에는 파일명만 올라간다 

//		Business Logic
		productService.addProduct(product);

		return "forward:/product/getProduct.jsp";
	}
	
//	@RequestMapping("/getProduct.do")
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo, 
								@RequestParam("menu") String menu ,
								Model model ) throws Exception {
		
		System.out.println("ProductController.java /getProduct GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		System.out.println("ProductController.java /getProduct.do  product "+product);
		//System.out.println("ProductController.java /getProduct.do  menu "+menu);
		
		if(menu.equals("manage")) {
			return "forward:/product/updateProductView.jsp";	
		}else {
			return "forward:/product/getProduct.jsp";
		}

	}
//	@RequestMapping(value = "getProduct", method = RequestMethod.POST)
//	public String getProduct2( @RequestParam("prodNo") int prodNo, 
//								@RequestParam("menu") String menu ,
//								Model model ) throws Exception {
//		
//		System.out.println("ProductController.java /getProduct GET");
//		//Business Logic
//		Product product = productService.getProduct(prodNo);
//		// Model 과 View 연결
//		model.addAttribute("product", product);
//		System.out.println("ProductController.java /getProduct.do  product "+product);
//		//System.out.println("ProductController.java /getProduct.do  menu "+menu);
//		
//		if(menu.equals("manage")) {
//			return "forward:/product/updateProductView.jsp";	
//		}else {
//			return "forward:/product/getProduct.jsp";
//		}
//
//	}
	
//	@RequestMapping("/updateProductView.do")
	@RequestMapping(value = "updateProduct", method = RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo") int prodNo , 
									Model model ) throws Exception{

		System.out.println("ProductController.java /updateProductView GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
//	@RequestMapping("/updateProduct.do")
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product ,
								 @RequestParam("menu") String menu,
								 @RequestParam("file") MultipartFile file,
									Model model) throws Exception{
		//@RequestParam("file") MultipartFile file,  , HttpSession session

		System.out.println("ProductController.java /updateProduct POST");
		String savedFileName = ""; //빈 파일명 설정하고
		
		String uploadPath = "C:\\workspace\\11.Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles\\";
		//실제 파일 업로드 경로 db에는 파일명만 올라간다
		String originalFileName = file.getOriginalFilename();
		//form에서 실제 파일명 가져오기
		UUID uuid = UUID.randomUUID();
		//자바 aPI에서 유니크 아이디 생성하는 메소드 사용 해서  uuid 생성
	     savedFileName = uuid.toString() + "_" + originalFileName;
	     System.out.println("originalFileName= "+originalFileName);
	     //실제 올라간 파일명 보존하기 위해서 uuid+오리지날 아이디를 _로 구분해서 저장 파일명 생성
	     // 4. 파일 생성
	     File file1 = new File(uploadPath + savedFileName);
	     //File 타입의 file1 = 경로+uuid_오리지날아이디로 생성
	     // 5. 서버로 전송
	     file.transferTo(file1);
	     // 파일로 경로+파일명으로 전송한다
	     
	     // model로 저장
	     product.setFileName(savedFileName);
	     System.out.println("savedFileName= "+savedFileName);
		//Business Logic
		productService.updateProduct(product);
		System.out.println("ProductController.java /updateProduct.do 실행됨");
		System.out.println("menu "+menu);
		productService.getProduct(product.getProdNo());   //post으로 맵핑되서 이거 추가하고 get으로 보냄
//		model.addAttribute("menu", menu);
		
//		String sessionId=((Product)session.getAttribute("product")).getProdNo();
//		if(sessionId.equals(product.getProdNo())){
//			session.setAttribute("product", product);
//		}
//		return "forward:/getProduct.do";
		return "forward:/product/getProduct.jsp";
//		return "forward:/product/getProduct2?menu="+menu;
//		return "forward:/product/getProduct";
//		return "forward:/getProduct.do?menu="+menu;
	}
	
		
//	@RequestMapping("/listProduct.do")
	@RequestMapping(value = "listProduct")
	public String listProduct( @ModelAttribute("search") Search search ,
								Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("ProductController.java에 /listProduct GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, search.getPageSize());
//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
//		model.addAttribute("product",product);
//		model.addAttribute("menu", menu);
//		if(menu.equals("manage")) {
//			return "forward:/product/updateProductView.do";	
//		}else {
//			return "forward:/product/getProduct.jsp";
//		}
		return "forward:/product/listProduct.jsp";
	}
}