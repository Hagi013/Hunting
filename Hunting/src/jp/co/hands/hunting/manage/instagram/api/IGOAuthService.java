package jp.co.hands.hunting.manage.instagram.api;

import javax.ejb.Stateless;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Stateless
@WebServlet("/IGOAuthService")
public class IGOAuthService extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	
		String code = req.getParameter("code");
		System.out.println(code);
		/*IGService igService = new IGServiceImpl();*/
		
	}
}
