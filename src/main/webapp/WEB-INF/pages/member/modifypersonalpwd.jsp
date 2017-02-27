<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">会员管理</a> <span class="divider">/</span></li>
		<li><a href="/member/modifypersonalpassword.html">修改本人密码</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">	
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>修改密码</h2>
					</div>
				<div class="box-content">
					<div class="modal-body">
						<ul class="topul">
							<div class="modal-header">
							<h3>修改商城登录密码</h3>
					</div>
					<div class="member-modal-body">
						<p>
								<label>请输入原密码：</label>
								  <input id="oldloginpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*</span>
								<label>请输入新密码：</label>
								  <input id="newloginpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*新密码必须6位以上</span>
								<label>再次输入新密码：</label>
								  <input id="againloginpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*</span>
						</p>
						<p id="modifyloginpwdtip">
						</p>
					</div>
				<a href="#" id="saveloginpwd" class="btn btn-primary">保存</a>
				</div>
				
				<div class="box-content">
					<div class="modal-body">
						<ul class="topul">
							<div class="modal-header">
						<h3>修改商城二级密码</h3>
					</div>
					<div class="member-modal-body">
						<p>
								<label>请输入原密码：</label>
								  <input id="oldsecondpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*</span>
								<label>请输入新密码：</label>
								  <input id="newsecondpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*新密码必须6位以上</span>
								<label>再次输入新密码：</label>
								  <input id="againsecondpwd"  type="password"/>
								  <span style="color:red;font-weight: bold;">*</span>
						</p>
						<p id="modifysecondpwdtip">
						</p>
					</div>
					<a href="#" id="savesecondpwd" class="btn btn-primary" id=>保存</a>
				</div>
				
			</div><!--/span-->
			
			
		</div><!--/row-->
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/modifypersonalpwd.js"></script> 
