package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
		System.out.println("ProductRestController.java "+this.getClass());
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET )
//	@RequestMapping( value="json/getProduct/{prodNo}",params = "type=manage", method=RequestMethod.GET )
	public Product getProduct( @PathVariable int prodNo ) throws Exception{
		
		System.out.println("/product/json/getProduct : GET");
		
		//Business Logic
		return productService.getProduct(prodNo);
	}

	@RequestMapping( value="json/getProduct", method=RequestMethod.POST )
	public Product getProduct(	@RequestBody Product product) throws Exception{
	
		System.out.println("/product/json/getProduct : POST");
		//Business Logic
		System.out.println("::"+product);
		Product dbProduct = productService.getProduct(product.getProdNo());
		
//		if( user.getPassword().equals(dbUser.getPassword())){
//			session.setAttribute("user", dbUser);
//		}
		
		return dbProduct;
	}
	
	/*
	 * @RequestMapping( value="json/addProduct", method=RequestMethod.POST ) public
	 * Product addProduct(@RequestBody Product product) throws Exception{
	 * 
	 * Product dbProduct=productService.addProduct(product);
	 * 
	 * }
	 */
//	@RequestMapping(value="json/listProduct", method = RequestMethod.POST)
//	public Map<String, Object> listProduct(@RequestBody Search search) throws Exception{
//	
//		Product dbProduct = productService.getProductList(search);
//		return dbProduct ;
//	}
}