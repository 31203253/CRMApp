<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<div class="row bg-title">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<h4 class="page-title">Cập nhật thành viên</h4>
		</div>
	</div>
	<!-- /.row -->
	<!-- .row -->
	<div class="row">
		<div class="col-md-2 col-12"></div>
		<div class="col-md-8 col-xs-12">
			<div class="white-box">
				<form action="<c:url value="/user/edit" />" method="post" class="form-horizontal form-material">
					
					<input type="hidden" name="id" value="${ user.id }"/>
								
					<div class="form-group">
						<label class="col-md-12">Full Name</label>
						<div class="col-md-12">
							<input type="text" placeholder="Fullname" name="fullname" value="${ user.fullname }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-12">Email</label>
						<div class="col-md-12">
							<input type="email" placeholder="Email" name="email" value="${ user.email }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-12">Password</label>
						<div class="col-md-12">
							<input type="password" placeholder="Password" name="password" value="${ user.password }"
								class="form-control form-control-line" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-12">Avatar</label>
						<div class="col-md-12">
							<input type="text" placeholder="Avatar" name="avatar" value="${ user.avatar }"
								class="form-control form-control-line">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-12">Role</label>
						<div class="col-sm-12">
							<select name="roleId"
								class="form-control form-control-line">
								<option value="1" ${ user.roleId == 1 ? 'selected' : '' }>Admin</option>
								<option value="2" ${ user.roleId == 2 ? 'selected' : '' }>Manager</option>
								<option value="3" ${ user.roleId == 3 ? 'selected' : '' }>User</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<button type="submit" class="btn btn-success">Edit User</button>
							<a href="<c:url value="/user" />" class="btn btn-primary">Quay lại</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="col-md-2 col-12"></div>
	</div>
	<!-- /.row -->
</div>