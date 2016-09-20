package jp.co.hands.hunting.manage.instagram.api.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.Code;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.IGService;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.impl.IGServiceImpl;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "igOAuthCallBackServlet")
@WebServlet("/IGOAuthCallBackServlet")
public class IGOAuthCallBackServlet extends HttpServlet {
	
	@Getter @Setter
	private Code code;
	
	@Getter @Setter
	private AccessToken accessToken;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String codeString = request.getParameter("code");
		if (Optional.ofNullable(codeString).isPresent()) {
			code = Code.builder().code(codeString).build();
			IGService igService = new IGServiceImpl();		
			accessToken = igService.getAccessToken(code.getCode());
			System.out.println("accessToken:   "+ accessToken);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/adminIndex.xhtml?faces-redirect=true");
			dispatcher.forward(request, response);
		} else {		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/adminIndex.xhtml?faces-redirect=true");
		dispatcher.forward(request, response);
		}
	}

}
