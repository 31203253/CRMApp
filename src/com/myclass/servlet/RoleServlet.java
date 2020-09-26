package com.myclass.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.connection.JDBCConnection;
import com.myclass.entity.Role;
import com.myclass.util.UrlConstants;

@WebServlet(name = "RoleServlet", urlPatterns = { UrlConstants.URL_ROLE_LIST, 
		UrlConstants.URL_ROLE_ADD, UrlConstants.URL_ROLE_EDIT, UrlConstants.URL_ROLE_DELETE })
public class RoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case UrlConstants.URL_ROLE_LIST:
			getRoleList(req, resp);
			break;
		case UrlConstants.URL_ROLE_ADD:
			getRoleAdd(req, resp);
			break;
		case UrlConstants.URL_ROLE_EDIT:
			getRoleEdit(req, resp);
			break;
		case UrlConstants.URL_ROLE_DELETE:
			deleteRole(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String action = req.getServletPath();

		switch (action) {
		case UrlConstants.URL_ROLE_ADD:
			postRoleAdd(req, resp);
			break;
		case UrlConstants.URL_ROLE_EDIT:
			postRoleEdit(req, resp);
			break;
		default:
			break;
		}
	}

	private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sql="select * from roles";
		List<Role> list=new ArrayList<Role>();
		try(Connection conn=JDBCConnection.getConnection()) {
			PreparedStatement stment=conn.prepareStatement(sql);
			ResultSet rs=stment.executeQuery();
			while(rs.next()) {
				list.add(new Role(rs.getInt(1),rs.getString(2),rs.getString(3)));
				System.out.println("Thành công");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Gui danh sach role ve cho index.jsp
		req.setAttribute("roles", list);
		req.getRequestDispatcher("/WEB-INF/views/role/index.jsp").forward(req, resp);
	}

	private void getRoleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/role/add.jsp").forward(req, resp);
	}

	private void getRoleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lay id client truyen len url
		Role role=null;
		int id = Integer.parseInt(req.getParameter("id"));
		String sql="select * from roles where id = ?";
		try(Connection conn=JDBCConnection.getConnection();) {
			PreparedStatement stment=conn.prepareStatement(sql);
			stment.setInt(1,id);
			ResultSet rs=stment.executeQuery();
			while(rs.next()) {
				id=rs.getInt("id");
				String name=rs.getString("name");
				String des=rs.getString("description");
				role=new Role(id, name, des);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("role", role);
		req.getRequestDispatcher("/WEB-INF/views/role/edit.jsp").forward(req, resp);
	}

	private void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lay id client truyen len url
		int id = Integer.parseInt(req.getParameter("id"));
		String sql="delete from roles where id = ?";
		try(Connection conn=JDBCConnection.getConnection();) {
			PreparedStatement stment=conn.prepareStatement(sql);
			stment.setInt(1, id);
			stment.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + UrlConstants.URL_ROLE_LIST);
	}

	private void postRoleAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Lay du lieu tu form
		String name = req.getParameter("name");
		String description = req.getParameter("description");

		// Tao id tang tu dong
		String sql="insert into roles(name,description) values(?,?)";
		try(Connection conn=JDBCConnection.getConnection();) {
			PreparedStatement stment=conn.prepareStatement(sql);
			stment.setString(1, name);
			stment.setString(2, description);
			stment.execute();
			System.out.println("Add thành công");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + UrlConstants.URL_ROLE_LIST);
	}

	private void postRoleEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Lay du lieu tu form
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String sql="update roles set name = ?,description = ? where id = ? ";
		try(Connection conn=JDBCConnection.getConnection();) {
			PreparedStatement stment=conn.prepareStatement(sql);
			stment.setString(1,name);
			stment.setString(2,description);
			stment.setInt(3,id);
			stment.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Quay ve trang danh sach
		resp.sendRedirect(req.getContextPath() + UrlConstants.URL_ROLE_LIST);
	}
}
