package com.bit2016.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.dao.GuestbookDao;
import com.bit2016.guestbook.vo.GuestbookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// action name 가져오기
		String actionName = request.getParameter("a");
		
		if ("delete".equals(actionName)) {
			// delete 구현
			String no = request.getParameter("no");
			String pass = request.getParameter("pass");
			
			GuestbookVo vo = new GuestbookVo(); 
			vo.setNo(Long.parseLong( no )); 
			vo.setPassword(pass); 
			 
			GuestbookDao dao = new GuestbookDao(); 
			dao.delete(vo); 
			 
			response.sendRedirect( "/guestbook2/gb" ); 

			
		} else if ("add".equals(actionName)) {
			// add 구현
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(pass);
			vo.setContent(content);
			
			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);
			
			response.sendRedirect("/guestbook2/gb");
			
		} else if ("deleteform".equals(actionName)) {
			// deleteform 구현
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			
			rd.forward(request, response);
			
		} else {
			// default action 처리(리스트 처리)
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();

			// request 범위에 모델 데이터 저장
			request.setAttribute("list", list); // list를 list라는 이름으로 request객체에
												// 넣는 작업, view에서 list를 사용할 수 있게
												// 하기 위함

			// forwarding(request 연장, request dispatch), 어떤 view로 request를 연장하는지
			// 여기서 설정
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");

			rd.forward(request, response); // 여기서 request 객체를 view로 옮기는 것
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
