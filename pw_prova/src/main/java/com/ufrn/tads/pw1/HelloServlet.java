package com.ufrn.tads.pw1;

import com.ufrn.tads.pw1.Dominio.Pessoa;
import com.ufrn.tads.pw1.Dominio.Produto;
import com.ufrn.tads.pw1.Persistencia.PessoaDAO;
import com.ufrn.tads.pw1.Persistencia.ProdutoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Controller
public class HelloServlet{

    @RequestMapping("/clientes")
    public void listarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");
        if(pessoa == null){
            response.sendRedirect("http://localhost:8080/login.html");
            return;
        }

        if(pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }


        PessoaDAO pessoaDAO = new PessoaDAO();
        ArrayList<Pessoa> pessoas = pessoaDAO.listar(true);

        response.setContentType("text/html");
        response.getWriter().println("<html><head><meta charset=\"UTF-8\"><title>Projeto PW1 </title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h3>---------------     LISTA DE CLIENTES     ---------------</h3>");

        for(Pessoa p: pessoas){
            response.getWriter().println("<b>Nome:      </b>" + p.getNome() + "    |   ");
            response.getWriter().println("<b>Email:     </b>" + p.getEmail()+ "   |   ");
            response.getWriter().println("<b>Senha:     </b>" + p.getSenha() + "<br>");
        }
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    @RequestMapping("/lojistas")
    public void listarLojistas(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");
        if (pessoa == null){
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }
        if(pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }

        PessoaDAO pessoaDAO = new PessoaDAO();
        ArrayList<Pessoa> pessoas = pessoaDAO.listar(false);

        response.setContentType("text/html");
        response.getWriter().println("<html><head><meta charset=\"UTF-8\"><title>Projeto PW1 </title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h3>---------------     LISTA DE LOJISTAS     ---------------</h3>");

        for(Pessoa p: pessoas){
            response.getWriter().println("<b>Nome:      </b>" + p.getNome() + "    |   ");
            response.getWriter().println("<b>Email:     </b>" + p.getEmail()+ "   |   ");
            response.getWriter().println("<b>Senha:     </b>" + p.getSenha() + "<br>");
        }
        response.getWriter().println("</body></html>");
    }

    @RequestMapping (value = "/cadastrarCliente", method= RequestMethod.POST)
    public void cadastrar(HttpServletRequest request, HttpServletResponse response){
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa cliente = new Pessoa();

        cliente.setNome(request.getParameter("nome"));
        cliente.setEmail(request.getParameter("email"));
        cliente.setSenha(request.getParameter("senha"));
        cliente.setCategoria(true);
        pessoaDAO.adicionar(cliente);
    }

    @RequestMapping(value={"/login"}, method= RequestMethod.POST)
    public void verificar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PessoaDAO pessoaDAO = new PessoaDAO();
        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        Pessoa pessoa = pessoaDAO.consultarPorEmail(email);

       if (pessoa == null){
           response.sendRedirect("http://localhost:8080/login.html");
       }else if(!senha.equals(pessoa.getSenha())){
           response.sendRedirect("http://localhost:8080/login.html");

       }else{
           HttpSession session = request.getSession(true);
           session.setAttribute("pessoaLogada", pessoa);
           response.sendRedirect("http://localhost:8080/produtos");
       }
    }

    @RequestMapping("/sair")
    public void sair(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("http://localhost:8080/login.html");
    }

    @RequestMapping("/produtos")
    public void listarProdutos (HttpServletRequest request, HttpServletResponse response) throws  IOException{
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if(pessoa == null){
            response.sendRedirect("http://localhost:8080/login.html");
            return;
        }

        ProdutoDAO pro = new ProdutoDAO();
        ArrayList<Produto> produtos = pro.listar();

        response.setContentType("text/html");
        response.getWriter().println("<html><head><meta charset=\"UTF-8\"><title>Projeto PW1 </title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<p><a href='http://localHost:8080/sair'> SAIR </a></p>");

        response.getWriter().println("<h3>---------------     LISTA DE PRODUTOS     ---------------</h3>");
        if(!pessoa.getCategoria()){
            response.getWriter().println("<p><a href='http://localHost:8080/cadastrarProduto.html'>Cadastrar Produto</a></p>");
            response.getWriter().println("<p><a href='http://localHost:8080/clientes'>Listar Clientes</a></p>");
            response.getWriter().println("<p><a href='http://localHost:8080/lojistas'>Listar Lojistas</a></p>");

        }else{
            response.getWriter().println("<p><a href='http://localHost:8080/visualizarCarrinho'>Visualizar carrinho de compra</a></p>");

        }
        response.getWriter().println("<table>");
        response.getWriter().println("<tr>");
        response.getWriter().println("<th>Nome</th>");
        response.getWriter().println("<th>Descrição</th>");
        response.getWriter().println("<th>Preço</th>");
        response.getWriter().println("<th>Estoque</th>");
        if(!pessoa.getCategoria()) {
            response.getWriter().println("<th></th>");

        }
        response.getWriter().println("</tr>");
        for(Produto produto: produtos){
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>");
            response.getWriter().println(produto.getNome());
            response.getWriter().println("</td>");
            response.getWriter().println("<td>");
            response.getWriter().println(produto.getDescricao());
            response.getWriter().println("</td>");
            response.getWriter().println("<td>");
            response.getWriter().println(produto.getPreco());
            response.getWriter().println("</td>");
            response.getWriter().println("<td>");
            response.getWriter().println(produto.getEstoque());
            response.getWriter().println("</td>");
            if(!pessoa.getCategoria()) {
                response.getWriter().println("<td>");
                response.getWriter().println("<p><a href='http://localHost:8080/excluir?id=" + produto.getId_produto() +"'>Excluir</a></p>");
                response.getWriter().println("</td>");
            }else{
                response.getWriter().println("<td>");
                response.getWriter().println("<p><a href='http://localHost:8080/adicionarCarrinho?id=" + produto.getId_produto() +"'>Adicionar</a></p>");
                response.getWriter().println("</td>");
            }
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</table>");
        response.getWriter().println("</body></html>");
    }

    @RequestMapping("/adicionarCarrinho")
    public void comprar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if (pessoa == null) {
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }
        if(!pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produtoSelecionado = produtoDAO.getById(Integer.parseInt(request.getParameter("id")));
        boolean carrinhoExiste = false;
        Cookie carrinhoCompras = null;

        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (var c : requestCookies) {
                if (c.getName().equals("carrinhoCompras")) {
                    carrinhoExiste = true;
                    carrinhoCompras = c;
                    break;
                }
            }
        }

        if (produtoSelecionado != null) {
            if (carrinhoExiste) {
                String value = carrinhoCompras.getValue();
                carrinhoCompras.setValue(value + produtoSelecionado.getId_produto() + "|");
            } else {
                carrinhoCompras = new Cookie("carrinhoCompras", "");
                carrinhoCompras.setMaxAge(60 * 60 * 24 * 7);
                carrinhoCompras.setValue(produtoSelecionado.getId_produto() + "|");
            }
        } else {
            System.out.println("produtoSelecionado nulo");
            response.sendRedirect("http://localhost:8080/produtos");
            return;
        }
        response.addCookie(carrinhoCompras);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/produtos");
        dispatcher.forward(request, response);
    }

    @RequestMapping("/visualizarCarrinho")
    public void visualizarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if (pessoa == null) {
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }

        if(!pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }
        Cookie carrinhoCompras = null;
        boolean carrinhoExiste = false;

        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (var c : requestCookies) {
                if (c.getName().equals("carrinhoCompras")) {
                    carrinhoExiste = true;
                    carrinhoCompras = c;
                    break;
                }
            }
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = null;
        Double totalCompra = 0.0;
        ArrayList<Produto> produtos = new ArrayList();
        response.getWriter().println("<html>");
        response.getWriter().println("<body>");
        if (carrinhoExiste) {
            StringTokenizer tokenizer = new StringTokenizer(carrinhoCompras.getValue(), "|");
            while (tokenizer.hasMoreTokens()) {
                produto = produtoDAO.getById(Integer.parseInt(tokenizer.nextToken()));
                produtos.add(produto);
            }

            response.getWriter().println("<h2>Carrinho de compras</h2>");
            response.getWriter().println("<br>");
            response.getWriter().println("<table>");
            response.getWriter().println("<tr>");
            response.getWriter().println("<th>Nome</th>");
            response.getWriter().println("<th>Descrição</th>");
            response.getWriter().println("<th>Preço</th>");
            response.getWriter().println("<th>Estoque</th>");
            response.getWriter().println("</tr>");

            for(Produto p: produtos){
                response.getWriter().println("<tr>");
                response.getWriter().println("<td>");
                response.getWriter().println(p.getNome());
                response.getWriter().println("</td>");
                response.getWriter().println("<td>");
                response.getWriter().println(p.getDescricao());
                response.getWriter().println("</td>");
                response.getWriter().println("<td>");
                response.getWriter().println(p.getPreco());
                response.getWriter().println("</td>");
                response.getWriter().println("<td>");
                response.getWriter().println(p.getEstoque());
                response.getWriter().println("</td>");
                response.getWriter().println("</tr>");
                totalCompra += p.getPreco();
            }
            response.getWriter().println("</table>");
            response.getWriter().println("<h4>Total da compra: " + totalCompra + "</h4>");
            response.getWriter().println("<a href='http://localhost:8080/produtos'>Voltar para lista de produtos</a>");
            response.getWriter().println("<br>");
            response.getWriter().println("<a href='http://localhost:8080/finalizarCompra'>Finalizar Comprar</a>");
            response.getWriter().println("<br>");
            response.getWriter().println("<a href='http://localhost:8080/limparCarrinho'>Limpar Carrinho</a>");

        } else {

            response.getWriter().println("<h2>Nenhum produto adicionado ao carrinho</h2>");
            response.getWriter().println("<br>");
            response.getWriter().println("<a href='http://localhost:8080/produtos'>Voltar para lista de produtos</a>");
        }
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    @RequestMapping("/limparCarrinho")
    public void limparCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if (pessoa == null) {
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }

        if (!pessoa.getCategoria()) {
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }

        Cookie carrinhoCompras = null;

        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (var c : requestCookies) {
                if (c.getName().equals("carrinhoCompras")) {
                    carrinhoCompras = c;
                    carrinhoCompras.setMaxAge(-1);
                    break;
                }
            }
        }
        response.addCookie(carrinhoCompras);
        RequestDispatcher encaminhar = request.getRequestDispatcher("/visualizarCarrinho");
        encaminhar.forward(request, response);
    }

    @RequestMapping("/finalizarCompra")
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if (pessoa == null) {
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }

        if (!pessoa.getCategoria()) {
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }

    }

    @RequestMapping("/excluir")
    public void Excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if(pessoa == null){
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }
        if(pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.Excluir(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
        encaminhar.forward(request, response);
    }

    @RequestMapping(value ="/cadastrarProduto", method= RequestMethod.POST)
    public void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Pessoa pessoa = (Pessoa) session.getAttribute("pessoaLogada");

        if(pessoa == null){
            response.sendRedirect("http://localHost:8080/login.html");
            return;
        }

        if(pessoa.getCategoria()){
            RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
            encaminhar.forward(request, response);
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = new Produto();

        produto.setNome(request.getParameter("nome"));
        produto.setDescricao(request.getParameter("descricao"));
        produto.setPreco(Double.parseDouble(request.getParameter("preco").replace(',','.')));
        produto.setEstoque(Integer.parseInt(request.getParameter("estoque")));
        produtoDAO.adicionar(produto);

        RequestDispatcher encaminhar = request.getRequestDispatcher("/produtos");
        encaminhar.forward(request, response);
    }


}

