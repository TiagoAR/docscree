package br.ueg.unucet.docscree.utilitarios;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ueg.unucet.quid.dominios.Usuario;

/**
 * Filtro para o login, tentativa de acesso a aplicação passa por essa servlet
 * 
 * @author Diego
 * 
 */
public class FiltroLogin implements Filter {

	@Override
	public void destroy() {
	}

	/**
	 * Método que filtra a requisição, se estiver logado não faz nada,
	 * caso contrário redireciona para a página de login.
	 * 
	 * @param resp response da visão
	 * @param req request da visão
	 * @param chain filterChain da visão
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (!this.isLogged(request)
				&& !request.getRequestURL().toString().contains("login.zul")) {
			response.sendRedirect(request.getContextPath() + "/login.zul");
		} else {
			try {
				chain.doFilter(req, resp);
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/login.zul");
			}
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Método que verifica se está logado
	 * 
	 * @param request 
	 * @return boolean se está logado ou não
	 */
	private boolean isLogged(HttpServletRequest request) {
		boolean retorno = false;
		HttpSession session = request.getSession(true);
		if (session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if (usuario != null) {
				retorno = true;
			}
		}
		return retorno;
	}

}
