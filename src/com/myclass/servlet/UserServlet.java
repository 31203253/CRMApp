package com.myclass.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myclass.entity.User;

@WebServlet(name = "UserServlet", urlPatterns = { 
		"/user", "/user/add", "/user/edit", "/user/delete", "/user/details" })
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private List<User> list;

	@Override
	public void init() throws ServletException {
		list = new ArrayList<User>();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/user":
			getUserList(req, resp);
			break;
		case "/user/add":
			getUserAdd(req, resp);
			break;
		case "/user/edit":
			getUserEdit(req, resp);
			break;
		case "/user/details":
			getUserDetails(req, resp);
			break;
		case "/user/delete":
			deleteUser(req, resp);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/user/add":
			postUserAdd(req, resp);
			break;
		case "/user/edit":
			postUserEdit(req, resp);
			break;
		default:
			break;
		}
	}

	private void getUserList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("users", list);
		req.getRequestDispatcher("/WEB-INF/views/user/index.jsp").forward(req, resp);
	}

	private void getUserAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/user/add.jsp").forward(req, resp);
	}

	private void getUserEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		for (User user : list) {
			if (user.getId() == id) {
				req.setAttribute("user", user);
				break;
			}
		}
		req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
	}

	private void getUserDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/user/details.jsp").forward(req, resp);
	}

	private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			if (user.getId() == id) {
				list.remove(user);
				break;
			}
		}
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + "/user");
	}

	private void postUserAdd(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		// Lay du lieu tu form
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.parseInt(req.getParameter("roleId"));

		// Tao id tang tu dong
		int id = list.size() + 1;

		// Tao doi tuong Role
		User user = new User(id, email, password, fullname, avatar, roleId);
		// Luu vao list
		list.add(user);
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + "/user");
	}

	private void postUserEdit(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		// Lay du lieu tu form
		int id = Integer.parseInt(req.getParameter("id"));
		// Lay du lieu tu form
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.parseInt(req.getParameter("roleId"));

		// Tao doi tuong Role
		for (User user : list) {
			if (user.getId() == id) {
				user.setEmail(email);
				user.setPassword(password);
				user.setFullname(fullname);
				user.setAvatar(avatar);
				user.setRoleId(roleId);
				break;
			}
		}
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + "/user");
	}
}
