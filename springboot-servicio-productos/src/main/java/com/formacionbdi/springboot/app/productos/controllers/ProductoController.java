package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@Value("${server.port}")
	private Integer port;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(p -> {
			p.setPort(port);
			return p;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException{
		if(id.equals(10L)) {
			throw new IllegalStateException("Poducto no encontrado");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Producto producto = productoService.findById(id);
		producto.setPort(port);
		
		return producto;
	}

}
