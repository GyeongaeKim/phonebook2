package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
	
	//생성자(여기서는 기본생성자 사용)
	//메소드 gs
	
	//메소드 일반
	//get방식으로 요청시 호출 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//코드작성
		//System.out.println("controller");
				
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) { //리스트("list")를 요청하면 작동한다~
			//데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> phoneList = phoneDao.getPersonList();
			System.out.println(phoneList);
			
			//request에 데이터 추가 --> (이름, 실제주소값)
			request.setAttribute("pList", phoneList);
			
			//데이터 + html --> jsp한테 시킨다
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		}else if("writeForm".equals(action)) { //등록폼("writeForm")을 요청하면 작동한다~
			//System.out.println("등록폼");
			
			//writeForm 포워드하기
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
		}else if("write".equals(action)) { //등록일때
			//System.out.println("등록");
			
			//파라미터에서 값 꺼내기(name, hp, company)
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//vo만들어서 값 초기화
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo);
			
			//phoneDao.personInsert()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personInsert(personVo);
			System.out.println(count);
			
			//리다이렉트 list
			response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else if("delete".equals(action)){ //삭제일때
			//System.out.println("삭제");
			
			//파라미터에서 id값을 꺼낸다
			int id = Integer.parseInt(request.getParameter("id"));
			
			//phoneDao.personDelete()를 통해 삭제하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personDelete(id);
			System.out.println(count);
			
			//리다이렉트 list
			response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else if("updateForm".equals(action)) { //등록폼("writeForm")을 요청하면 작동한다~
			//System.out.println("수정폼");
			
			//writeForm 포워드하기
			RequestDispatcher rd = request.getRequestDispatcher("/updateForm.jsp");
			rd.forward(request, response);
		}else if("update".equals(action)){ //수정일때
			//System.out.println("수정");
			
			//파라미터에서 값 꺼내오기
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//PersonVo 만들기
			PersonVo personVo = new PersonVo(id, name, hp, company);
			System.out.println(personVo);
			
			
			//PhoneDao personUpdate()로 수정하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personUpdate(personVo);
			System.out.println(count);
			
			//리다이렉트 list
			response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else{
			System.out.println("action 파라미터 없음");
		}
	}
	
	
	//post방식으로 요청시 호출 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
