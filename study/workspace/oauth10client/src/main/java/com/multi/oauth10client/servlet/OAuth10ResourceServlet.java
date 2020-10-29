package com.multi.oauth10client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HttpServletBean;
import org.thinker.oauth.ResourceTokenVO;
import org.thinker.oauth.TokenSender;

import com.multi.oauth10client.Setup;

public class OAuth10ResourceServlet extends HttpServletBean {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 이곳에 코드를 작성합니다.
		HttpSession session = req.getSession();
		String AT = (String) session.getAttribute("AT");
		String ATS = (String) session.getAttribute("ATS");

//		System.out.println(AT);
//		System.out.println(ATS);

		try {
			ResourceTokenVO vo = new ResourceTokenVO();
			vo.setMethod("GET");
			vo.setRequestURL(Setup.RESOURCE_URL);
			vo.setConsumerKey(Setup.CONSUMER_KEY);
			vo.setConsumerSecretKey(Setup.CONSUMER_SECRET);
			vo.setRequestOauthToken(AT);
			vo.setRequestOauthTokenSecret(ATS);
			vo.sign();

			TokenSender sender = new TokenSender();
			sender.sendHttpClient(vo);
			String result = vo.getResult();
			resp.setContentType("application/json");
			resp.getWriter().print(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
