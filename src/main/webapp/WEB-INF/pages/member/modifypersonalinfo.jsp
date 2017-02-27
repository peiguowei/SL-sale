<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">会员管理</a> <span class="divider">/</span></li>
		<li><a href="/member/modifypersonalinfo.html">修改本人资料</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>修改本人资料</h2>
					</div>
				<div class="box-content">
				<form action="/member/savepersonalinfo.html" enctype="multipart/form-data" method="post" onsubmit="return modifyPersonalInfoFunc();">
					<div class="member-modal-body">
						<ul id="modify_formtip"></ul>
						<input id="m_id" type="hidden" name="id" value="${currentUser.id}"/>
						<ul class="topul">
							<li>
							  <label>角色：</label>
							  <input id="m_rolename" type="text" name="roleName" value="${currentUser.roleName}" readonly="readonly"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>会员类型：</label>
							  <input id="m_usertypename" type="text" name="userTypeName" value="${currentUser.userTypeName}" readonly="readonly"/>
							</li>
							<li>
							  <label>用户名：</label><input type="text" id="m_logincode" name="loginCode" value="${currentUser.loginCode}" readonly="readonly"/>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>姓名：</label><input type="text" id="m_username" name="userName" value="${currentUser.userName}" />
							  <span style="color:red;font-weight: bold;">*</span>
							</li> 
							<li>
							  <label>性别：</label>
 							  <select id="m_sex" name="sex" style="width:100px;">
								<option value="" selected="selected">--请选择--</option>
								　　 <c:if test="${currentUser.sex == '男'}">  
									　　<option value="男" selected="selected">男</option>
										<option value="女">女</option>
								　　 </c:if>  
								　　 <c:if test="${currentUser.sex == '女'}">  
									　    <option value="女" selected="selected">女</option>
										<option value="男">男</option>
								 	 </c:if>
								　　  <c:if test="${currentUser.sex == null || currentUser.sex == ''}">  
									　    <option value="男">男</option>
										<option value="女">女</option>
								 	</c:if>
						 	  </select>
							</li> 
							<li>
							  <label>证件类型：</label>
							  <input id="m_cardtypename" type="hidden" name="cardTypeName" value="${currentUser.cardTypeName}"/>
							   <select id="m_cardtype" name="cardType" style="width:100px;">
					 			<option value="" selected="selected">--请选择--</option>
					 			<c:if test="${cardTypeList != null}">
					 				<c:forEach items="${cardTypeList}" var="cardtype">
					 					<option <c:if test="${currentUser.cardType == cardtype.valueId}">selected = "selected"</c:if>
					 					value="${cardtype.valueId}">${cardtype.valueName}</option>
					 				</c:forEach>
					 			</c:if>
					 		   </select>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							    <label>证件号码：</label><input type="text" id="m_idcard" name="idCard" value="${currentUser.idCard}" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
								<span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>生日：</label>
							   <input class="Wdate" id="m_birthday" size="15" name="birthday" readonly="readonly"  type="text" onClick="WdatePicker();" value="<fmt:formatDate value="${currentUser.birthday}" pattern="yyyy-MM-dd"/>"/>
							   <!-- <input type="text" class="input-xlarge datepicker" id="m_birthday" name="birthday" value="<fmt:formatDate value="${currentUser.birthday}" pattern="MM/dd/yyyy"/>" readonly="readonly"/> -->
							</li>
							<li>
							  <label>收货国家：</label><input type="text" id="m_country" name="country" value="${currentUser.country}" />
							</li>
							<li>
							    <label>联系电话：</label><input type="text" id="m_mobile" name="mobile" value="${currentUser.mobile}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>Email：</label><input type="text" id="m_email" name="email" value="${currentUser.email}"/>
							</li>
							<li>
							  <label>邮政编码：</label><input type="text" id="m_postcode" name="postCode" value="${currentUser.postCode}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							</li>
							<li>
							    <label>开户行：</label><input type="text" id="m_bankname" name="bankName" value="${currentUser.bankName}"/>
								<span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							    <label>开户卡号：</label><input type="text" id="m_bankaccount" name="bankAccount" value="${currentUser.bankAccount}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							    <label>开户人：</label><input type="text" id="m_accountholder" name="accountHolder" value="${currentUser.accountHolder}"/>
								<span style="color:red;font-weight: bold;">*</span>
							</li>
							<li>
							  <label>推荐人：</label><input type="text" id="m_refercode" name="referCode" value="${currentUser.referCode}" readonly="readonly"/>
							</li>
							<li>
							  <label>注册时间：</label>
							  <input class="Wdate" id="m_createtime" size="15" name="createTime" disabled="disabled"  type="text" onClick="WdatePicker();" value="<fmt:formatDate value="${currentUser.createTime}" pattern="yyyy-MM-dd"/>"/>
							 <!--<input type="text" class="input-xlarge datepicker" id="m_createtime" name="createTime" value="<fmt:formatDate value="${currentUser.createTime}" pattern="MM/dd/yyyy"/>" readonly="readonly"/>-->
							</li>
							<li>
							  <label>是否启用：</label>
							  <select id="m_isstart" name="isStart" style="width:100px;">
								　　 <c:if test="${currentUser.isStart == 1}">  
									　　<option value="1" selected="selected">启用</option>
										<option value="2">未启用</option>
								　　 </c:if>  
								　　 <c:if test="${currentUser.isStart == '2'}">  
									　    <option value="2" selected="selected">未启用</option>
										<option value="1">启用</option>
								 	 </c:if>
						 	  </select>
							  <span style="color:red;font-weight: bold;">*</span>
							</li>
							<li class="lastli">
							  <label>收货地址：</label><textarea id="m_useraddress" name="userAddress">${currentUser.userAddress}</textarea>
							</li>
						</ul>
						<div class="clear"></div>
						<ul class="downul">
							<li>
							<label>上传身份证图片：</label>
								<input type="hidden" id="m_fileInputIDPath" name="idCardPicPath" value="${currentUser.idCardPicPath}"/>
								<input id="m_fileInputID" name="m_fileInputID" type="file"/>
								<c:if test="${currentUser.idCardPicPath == null || currentUser.idCardPicPath == ''}">
								     <input type="button" id="m_uploadbtnID" value="上传"/>
									 <p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
									 <div id="m_idPic"></div>
								</c:if>
								<c:if test="${currentUser.idCardPicPath != null && currentUser.idCardPicPath != ''}">
								     <input type="button" id="m_uploadbtnID_h" value="上传" style="display:none;"/>
									 <p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
									 <div id="m_idPic">
									 <p><span onclick="delpic('${currentUser.id}','m_idPic','m_uploadbtnID_h',this,'${currentUser.idCardPicPath}','m_fileInputIDPath','m_fileInputID');">x</span>
									 <img src="${currentUser.idCardPicPath}"?m="+Math.random()+"" />
									 </p>
									 </div>
								</c:if>
							 </li>
						</ul>
						<ul class="downul">
							<li>
							<label>上传银行卡图片：</label>
								<input type="hidden" id="m_fileInputBankPath" name="bankPicPath" value="${currentUser.bankPicPath}"/>
								<input id="m_fileInputBank" name="m_fileInputBank" type="file">
								<c:if test="${currentUser.bankPicPath == null || currentUser.bankPicPath == ''}">
									 <input type="button" id="m_uploadbtnBank" value="上传"/>
									 <p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
									 <div id="m_bankPic"></div>
								</c:if>
								<c:if test="${currentUser.bankPicPath != null && currentUser.bankPicPath != ''}">
								     <input type="button" id="m_uploadbtnBank_h" value="上传" style="display:none;"/>
									 <p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
									 <div id="m_bankPic">
									 <p><span onclick="delpic('${currentUser.id}','m_bankPic','m_uploadbtnBank_h',this,'${currentUser.bankPicPath}','m_fileInputBankPath','m_fileInputBank');">x</span>
									 <img src="${currentUser.bankPicPath}"?m="+Math.random()+"" />
									 </p>
									 </div>
								</c:if>
							 </li>
						</ul>
					</div>
					<div class="clear"></div>
					<div class="modifymemberinfo">
						<input type="submit"  class="btn btn-primary" value="保存" />
					</div>
				</form>	
				</div>
			</div><!--/span-->
		</div><!--/row-->
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/modifypersonalinfo.js"></script> 
