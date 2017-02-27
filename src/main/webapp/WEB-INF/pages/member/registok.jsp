<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">会员管理</a> <span class="divider">/</span></li>
		<li><a href="/member/registok.html">会员注册成功</a></li>
	</ul>
</div>
			<div class="row-fluid">
				<div class="box span12">
					<div class="box-header well">
						<h2><i class="icon-info-sign"></i>会员注册成功</h2>
					</div>
					<div class="box-content">
						<h1>注册成功！恭喜您成为注册会员！</h1>
						<br/>
						<p><b>注意：用户名、编号是您登录网站时使用的，您的初始密码是证件号码的后6位，注册成功后要立即更改。您的用户信息如下：</b></p>
						<ul>
						<li><lable>用户名：</lable>${registUser.loginCode}</li>
						<li><lable>姓名：</lable>${registUser.userName}</li>
						<li><lable>证件号码：</lable>${registUser.idCard}</li>
						<li><lable>移动电话：</lable>${registUser.mobile}</li>
						<li><lable>开户行：</lable>${registUser.bankName}</li>
						<li><lable>银行卡号：</lable>${registUser.bankAccount}</li>
						<li><lable>开户人：</lable>${registUser.accountHolder}</li>
						</ul>
						<p class="right">
							<button class="btn btn-large btn-primary nextbtn" disabled=""><i class="icon-chevron-left icon-white"></i>报单购货</button>
						</p>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/registok.js"></script> 
