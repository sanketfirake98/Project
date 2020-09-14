package com.sanket.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanket.demo.dao.CartRepo;
import com.sanket.demo.dao.ProductsRepo;
import com.sanket.demo.model.Cart;
import com.sanket.demo.model.Products;

@Controller
@RequestMapping("/Cart")
public class AddtoCartController {
	@Autowired
	ProductsRepo pr; 
	@Autowired
	CartRepo car;
	Cart cart=new Cart();
	public int x=0;
	
	@RequestMapping("/addtoCart/{id}")
  public String addtoCart(@PathVariable int id)
  {
	
	
	
	Optional<Products> pro=  pr.findById(id);
	

  
	cart.setId(  pro.map(Products::getId).orElse(0));   
	cart.setName(  pro.map(Products::getName).orElse(null));   
	cart.setPicPath(  pro.map(Products::getPicPath).orElse(null));   
	cart.setPrice(  pro.map(Products::getPrice).orElse(0));   
    car.save(cart);
    
      x=x+(pro.map(Products::getPrice).orElse(0));   
	 
	 //System.out.println(x);
	return "redirect:/product";
	
  }
    
	@RequestMapping("/cartButton")
	 public ModelAndView  addtoCart()
	 {
		//List<Products>lst= (List<Products>) pr.findAll();
    	 //System.out.println("oh bhai maro");
		 List<Cart>lsts=(List<Cart>)car.findAll();
		// System.out.println(x);
		
		ModelAndView mvr= new ModelAndView();
		mvr.addObject("cart",lsts);
		
		mvr.addObject("price", x);
		mvr.setViewName("Cart");
		 return mvr;
	 }
	
	@RequestMapping("/delete/{id}")
	public String Delete(@PathVariable int id)
	
	{
		car.deleteById(id);
		x=x-cart.getPrice();
	
		return "redirect:/Cart/cartButton";
	}
	
	@RequestMapping("/payment")
	public String Pay()
	{
		
		return "payment";
	}

}
