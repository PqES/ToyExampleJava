package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.intercomm.InterfaceAutenticacao;
import com.intercomm.InterfaceCliente;
import com.intercomm.InterfaceProduto;

@Controller
public class GerenciadorVenda {

	@Autowired
	private InterfaceProduto produto;
	

	@Autowired
	private RestTemplate restTemplate;

	 @Autowired
	private InterfaceAutenticacao autenticacao;

	 
	public GerenciadorVenda() {
	}
	
	@RequestMapping("/login")
	String login() throws Exception {
		return "index";
	}
	@RequestMapping("/sale")
	String service() throws Exception {
		return "sale";
	}

	
//	@RequestMapping(value = "/venda/autenticacao/{nome}/{senha}", method = RequestMethod.GET)
//	@ResponseBody
//	private String autenticarUsuario(@PathVariable("nome") String nome,@PathVariable("senha") String senha) {
//		String mensagem = "";
//		boolean confirmacao = autenticacao.autenticarFuncionario(nome, senha);
//		if(confirmacao) {
//			mensagem = "Usuário autenticado com sucesso!";
//		} else {
//			mensagem = "Usuário ou senha inválidos!";
//		}
//		return mensagem;
//	}
//	
	@RequestMapping(value = "/venda/autenticacao/{nome}/{senha}", method = RequestMethod.GET)
	@ResponseBody
	private Boolean autenticarUsuario(@PathVariable("nome") String nome,@PathVariable("senha") String senha) {
		
		return autenticacao.autenticarFuncionario(nome, senha);
		
	}
	
	
	@RequestMapping(value = "venda/getProdutos", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList getProducts() {
		String url = "http://MsProduto/produto/obterProdutos";
		ArrayList response = restTemplate.getForObject(url, ArrayList.class);
		return response;
	}

	@RequestMapping(value = "venda/getProdutosFeign", method = RequestMethod.GET)
	@ResponseBody
	public List getProdutosFeign() {
		return produto.obterTodosProdutos();
	}

	
	@RequestMapping(value="venda/atualizaEstoque/{id}/{quantidade}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean updateStock(@PathVariable("id") String id, @PathVariable("quantidade") int quantidade) {
		
		 return produto.atualizaEstoque(id, quantidade);
				
	}
	
	
	
	@RequestMapping(value="venda/getCustomerCpf/{cpf}", method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String getClienteCPF(@PathVariable("cpf") String cpf) {
		String url = "http://MsCliente/cliente/getClienteCpfPOST/";
		LinkedMultiValueMap<String, String> parametros = new LinkedMultiValueMap<String,String>();
		parametros.add("cpf", cpf);
		String response = restTemplate.postForObject(url, parametros, String.class);
		return response;
	}
	
	
}
