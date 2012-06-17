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
	 * Method that filter the request and if logged, continue with the request,
	 * if not force the user to login.
	 * 
	 * @param ServletRequest
	 *            , ServletResponse, FilterChain
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
	 * Method that verify if user is logged
	 * 
	 * @param HttpServletRequest
	 * @return if the user is logged or not
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
