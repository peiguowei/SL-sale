<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">会员管理</a> <span class="divider">/</span></li>
		<li><a href="/member/registmember.html">会员注册</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>会员注册</h2>
					</div>
				<div class="box-content">
				<form action="/member/saveregistmember.html" enctype="multipart/form-data" method="post" onsubmit="return registMemberFunc();">
					<div class="member-modal-body">
						<ul id="add_formtip"></ul>
						<ul class="topul">
							<li>
							  <label>用户名：</label>
							  <input type="text" id="a_loginCode" name="loginCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>姓名：</label>
							  <input type="text" id="a_userName" name="userName"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li> 
							<li>
							  <label>性别：</label>
 							  <select id="a_sex" name="sex" style="width:100px;">
								<option value="" selected="selected">--请选择--</option>
								　　 <option value="男">男</option>
									<option value="女">女</option>
						 	  </select>
							</li> 
							<li>
					 		  <label>证件类型：</label>
							  <input id="a_cardTypeName" type="hidden" name="cardTypeName" value=""/>
							  <select id="a_cardType" name="cardType" style="width:100px;">
					 			<option value="" selected="selected">--请选择--</option>
					 			<c:if test="${cardTypeList != null}">
					 				<c:forEach items="${cardTypeList}" var="cardType">
					 					<option value="${cardType.valueId}">${cardType.valueName}</option>
					 				</c:forEach>
					 			</c:if>
					 		  </select><span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>证件号码：</label>
							  <input type="text" id="a_idCard" name="idCard" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>生日：</label>
							  <input class="Wdate" id="a_birthday" size="15" name="birthday" readonly="readonly"  type="text" onClick="WdatePicker();" />
							  <!-- <input type="text" class="input-xlarge datepicker" id="a_birthday" name="birthday" readonly="readonly"/> -->
							</li>
							<li>
							  <label>收货国家：</label>
							  <input type="text" id="a_country" name="country" value="中国"/>
							</li>
							<li>
							  <label>联系电话：</label>
							  <input type="text" id="a_mobile" name="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>Email：</label>
							  <input type="text" id="a_email" name="email"/>
							</li>
							<li>
							  <label>邮政编码：</label>
							  <input type="text" id="a_postCode" name="postCode" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							</li>
							<li>
							  <label>开户行：</label>
							  <input type="text" id="a_bankName" name="bankName"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>开户卡号：</label>
							  <input type="text" id="a_bankAccount" name="bankAccount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>开户人：</label>
							  <input type="text" id="a_accountHolder" name="accountHolder"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>是否启用：</label>
				 			  <select name="isStart" style="width:100px;">
				 				<option value="1" selected="selected">启用</option>
				 				<option value="2">不启用</option>
				 			  </select> <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li class="lastli">
							  <label>收货地址：</label><textarea id="a_useraddress" name="userAddress"></textarea>
							</li>
						</ul>
						<div class="clear"></div>
						<ul class="downul">
					<li>
					<label>上传身份证图片：</label>
						<input type="hidden" id="a_fileInputIDPath" name="idCardPicPath" value=""/>
						<input id="a_fileInputID" name="a_fileInputID" type="file">
						<input type="button" id="a_uploadbtnID" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="a_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>上传银行卡图片：</label>
						<input type="hidden" id="a_fileInputBankPath" name="bankPicPath" value=""/>
						<input id="a_fileInputBank" name="a_fileInputBank" type="file">
						<input type="button" id="a_uploadbtnBank" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="a_bankPic"></div>
					 </li>
				</ul>
					</div>
					<div class="clear"></div>
					<div class="modifymemberinfo">
						<!-- <input type="button"  class="btn btn-primary backbtn" value="上一步"/> -->
						<input type="checkbox" class="agreerule" checked="checked"/><a href="#" style="color:#317EAC;" class="rule">同意《SL会员商城会员注册条款》</a>
						<input type="submit"  class="btn btn-primary nextbtn" value="立即注册" />
					</div>
			</form>	
		</div>
	</div><!--/span-->
</div><!--/row-->
			
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/registmember.js"></script> 