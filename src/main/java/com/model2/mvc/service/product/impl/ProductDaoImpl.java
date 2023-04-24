package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public ProductDaoImpl() {
		System.out.println("ProductDaoImpl.java ProductDaoImpl()"+this.getClass());
	}

	///Method
	public Product findProduct(int prodNo) throws Exception{
		System.out.println("ProductDaoImpl.java findProduct ½ÇÇàµÊ");
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}
	
	public String getProdName(String prodName) throws Exception{
		System.out.println("ProductDaoImpl.java getProdName ½ÇÇàµÊ");
		return sqlSession.selectOne("ProductMapper.getProdName", prodName);
	}
	
	public void insertProduct(Product product)throws Exception{
		System.out.println("ProductDaoImpl.java insertProduct ½ÇÇàµÊ");
		sqlSession.insert("ProductMapper.addProduct", product);
		System.out.println("ProductDaoImpl.java addProduct product: "+product);
	}
	
	public List<Product> getProductList(Search search) throws Exception {
		System.out.println("ProductDaoImpl.java getProductList ½ÇÇàµÊ");
		System.out.println("ProductDaoImpl.java   getProductList= "+search);
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}
	
	public void updateProduct(Product product) throws Exception {
		System.out.println("ProductDaoImpl.java updateProduct ½ÇÇàµÊ");
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	
	public int getTotalCount(Search search) throws Exception {
		System.out.println("ProductDaoImpl.java getTotalCount ½ÇÇàµÊ");
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}
	
	 
//	public String makeCurrentPageSql(String sql , Search search) {
//		return sqlSession.selectOne("ProductMapper.getTotalCount", sql);
//		
//	}


}