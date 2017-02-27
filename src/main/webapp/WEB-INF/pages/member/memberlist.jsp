<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">会员管理</a> <span class="divider">/</span></li>
		<li><a href="/member/memberlist.html">新会员管理</a></li>
	</ul>
</div>

			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>会员列表</h2>
					</div>
					
					<div class="box-content">
						<form action="/member/memberlist.html" method="post">
							<div class="searcharea">
							会员账号:
							<input type="text" name="s_loginCode" value="${s_loginCode}" />
							真实姓名:
							<input type="text" name="s_userName" value="${s_userName}" />
							
							<button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i> 查询 </button>
						</div>
						</form>
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>用户名</th>
								  <th>真实姓名</th>
								  <th>联系电话</th>
								  <th>推荐人</th>
								  <th>注册时间</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  
						  <c:if test="${page.items != null}">
						  <c:forEach items="${page.items}" var="user">
							<tr>
								<td class="center">${user.loginCode}</td>
								<td class="center">${user.userName}</td>
								<td class="center">${user.mobile}</td>
								<td class="center">${user.referCode}</td>
								<td class="center">
								<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/>
								</td>
								<td class="center">
									<a class="btn btn-success viewmember" href="#" id="${user.id}">
										<i class="icon-zoom-in icon-white"></i>  
										查看                                           
									</a>
									<a class="btn btn-info modifymember" href="#" id="${user.id}">
										<i class="icon-edit icon-white"></i>  
										修改                                            
									</a>
									<a class="btn btn-danger delmember" href="#" logincode="${user.loginCode}" id="${user.id}" idcardpicpath="${user.idCardPicPath}" bankpicpath="${user.bankPicPath}">
										<i class="icon-trash icon-white"></i> 
										删除
									</a>
								</td>
							</tr>
						  </c:forEach>
						 </c:if>
						  </tbody>
					  </table>   
					<div class="pagination pagination-centered">
					  <ul>
					   <c:choose>
					  	<c:when test="${page.page == 1}">
					  	<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
					  	</c:when>
					  	<c:otherwise>
					  	<li><a href="/member/memberlist.html?currentpage=1&s_loginCode=${s_loginCode}&s_userName=${s_userName}" title="首页">首页</a></li>
					  	</c:otherwise>
					  </c:choose>
						
						<c:if test="${page.prevPages!=null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="/member/memberlist.html?currentpage=${num}&s_loginCode=${s_loginCode}&s_userName=${s_userName}"
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>
						<li class="active">
						  <a href="#" title="${page.page}">${page.page}</a>
						</li>
						<c:if test="${page.nextPages!=null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href="/member/memberlist.html?currentpage=${num}&s_loginCode=${s_loginCode}&s_userName=${s_userName}" title="${num}">
								${num} </a></li>
							</c:forEach>
						</c:if>
						<c:if test="${page.pageCount !=null}">
							<c:choose>
						  	<c:when test="${page.page == page.pageCount}">
						  	<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
						  	</c:when>
						  	<c:otherwise>
						  	<li><a href="/member/memberlist.html?currentpage=${page.pageCount}&s_loginCode=${s_loginCode}&s_userName=${s_userName}" title="尾页">尾页</a></li>
						  	</c:otherwise>
						    </c:choose>
					    </c:if>
						<c:if test="${page.pageCount == null}">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					  	</c:if>
					  </ul>
				  </div>
				</div>
			</div><!--/span-->
		</div><!--/row-->
		<div class="modal hide fade" id="viewMemberDiv">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>查看会员信息</h3>
			</div>
			<div class="modal-body">
				<input id="v_id" type="hidden" value=""/>
				<ul class="topul">
					<li>
					  <label>角色：</label>
					  <input id="v_rolename" type="text" value=""/>
					</li>
					<li>
					  <label>会员类型：</label>
					  <input id="v_usertypename" type="text" value=""/>
					</li>
					<li>
					  <label>用户名：</label><input type="text" id="v_logincode" value="" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>姓名：</label><input type="text" id="v_username" value="" />
					  <span style="color:red;font-weight: bold;">*</span>
					</li> 
					<li>
					  <label>性别：</label>
					  <input type="text" id="v_sex" value="" />
					</li> 
					<li>
					  <label>证件类型：</label>
					  <input id="v_cardtypename" type="text" value=""/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>证件号码：</label><input type="text" id="v_idcard" value="" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>生日：</label>
					   <input type="text" id="v_birthday" value="" readonly="readonly"/>
					</li>
					<li>
					  <label>收货国家：</label><input type="text" id="v_country" value=""/>
					</li>
					<li>
					  <label>联系电话：</label><input type="text" id="v_mobile" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>Email：</label><input type="text" id="v_email" value=""/>
					</li>
					<li>
					  <label>邮政编码：</label><input type="text" id="v_postcode" value="" />
					</li>
					<li>
					  <label>开户行：</label><input type="text" id="v_bankname" value=""/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>开户卡号：</label><input type="text" id="v_bankaccount" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>开户人：</label><input type="text" id="v_accountholder" value=""/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>推荐人：</label><input type="text" id="v_refercode" value=""/>
					</li>
					<li>
					  <label>注册时间：</label>
					  <input type="text" id="v_createtime" value=""/>
					</li>
					<li>
					  <label>是否启用：</label>
					  <select id="v_isstart" style="width:100px;" disabled="disabled">
					  </select>
					</li>
					<li class="lastli">
					  <label>收货地址：</label>
					  <textarea id="v_useraddress" name="userAddress"></textarea>
					</li>
					
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
					<label>上传身份证图片
					(正反面)：</label>
						<input type="hidden" id="v_fileInputIDPath" value=""/>
						<div id="v_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>上传银行卡图片：</label>
						<input type="hidden" id="v_fileInputBankPath" value=""/>
						<div id="v_bankPic"></div>
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn viewmembercancel" data-dismiss="modal">关闭</a>
			</div>
	   </div>
	   <div class="modal hide fade" id="modifyMemberDiv">
		<form action="/member/modifymember.html" enctype="multipart/form-data" method="post" onsubmit="return modifyMemberFunction();">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>修改会员信息</h3>
			</div>
			<div class="modal-body">
				<ul id="modify_formtip"></ul>
				<input id="m_id" type="hidden" name="id"/>
				<ul class="topul">
					<li>
					  <label>角色：</label>
					  <input id="m_rolename" type="text" name="roleName" readonly="readonly"/>
					</li>
					<li>
					  <label>会员类型：</label>
					  <input id="m_selectusertypename" type="hidden" name="userTypeName" value=""/>
					  <select id="m_selectusertype" name="userType" style="width:100px;">
			 		  </select>
					</li>
					<li>
					  <label>用户名：</label><input type="text" id="m_logincode" name="loginCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>姓名：</label><input type="text" id="m_username" name="userName" />
					  <span style="color:red;font-weight: bold;">*</span>
					</li> 
					<li>
					  <label>性别：</label>
		 			  <select id="m_sex" name="sex" style="width:100px;">
					  </select>
					</li> 
					<li>
					  <label>证件类型：</label>
					  <input id="m_cardtypename" type="hidden" name="cardTypeName" value=""/>
					  <select id="m_cardtype" name="cardType" style="width:100px;">
					  </select>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>证件号码：</label><input type="text" id="m_idcard" name="idCard" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>生日：</label>
					  <input class="Wdate" id="m_birthday" size="15" name="birthday" readonly="readonly" type="text" onClick="WdatePicker();"/>
					  <!-- <input type="text" class="input-xlarge datepicker" id="m_birthday" name="birthday" readonly="readonly"/> -->
					</li>
					<li>
					  <label>收货国家：</label><input type="text" id="m_country" name="country"/>
					</li>
					<li>
					  <label>联系电话：</label><input type="text" id="m_mobile" name="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>Email：</label><input type="text" id="m_email" name="email"/>
					</li>
					<li>
					  <label>邮政编码：</label><input type="text" id="m_postcode" name="postCode" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					</li>
					<li>
					  <label>开户行：</label><input type="text" id="m_bankname" name="bankName"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>开户卡号：</label><input type="text" id="m_bankaccount" name="bankAccount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>开户人：</label><input type="text" id="m_accountholder" name="accountHolder"/>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li>
					  <label>推荐人：</label><input type="text" id="m_refercode" readonly="readonly"/>
					</li>
					<li>
					  <label>注册时间：</label>
					  <input type="text" id="m_createtime" name="createTime" readonly="readonly"/>
					</li>
					<li>
					  <label>是否启用：</label>
					  <select id="m_isstart" name="isStart" style="width:100px;">
					  </select>
					  <span style="color:red;font-weight: bold;">*</span>
					</li>
					<li class="lastli">
					  <label>收货地址：</label><textarea id="m_useraddress" name="userAddress"></textarea>
					</li>
					
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
					<label>上传身份证图片：</label>
						<input type="hidden" id="m_fileInputIDPath" name="idCardPicPath" value=""/>
						<input id="m_fileInputID" name="m_fileInputID" type="file"/>
						<input type="button" id="m_uploadbtnID" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="m_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>上传银行卡图片：</label>
						<input type="hidden" id="m_fileInputBankPath" name="bankPicPath" value=""/>
						<input id="m_fileInputBank" name="m_fileInputBank" type="file"/>
						<input type="button" id="m_uploadbtnBank" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="m_bankPic"></div>
					 </li>
				</ul>
			</div>
			
			<div class="modal-footer">
				<a href="#" class="btn modifymembercancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript">
    var cartTypeListJson =	[<c:forEach  items="${cardTypeList}" var="cardType"> 
							{"valueId":"${cardType.valueId}","valueName":"${cardType.valueName}"},
							</c:forEach>{"valueId":"over","valueName":"over"}];
    var userTypeListJson =	[<c:forEach  items="${userTypeList}" var="userType"> 
							{"valueId":"${userType.valueId}","valueName":"${userType.valueName}"},
							</c:forEach>{"valueId":"over","valueName":"over"}];
</script>
<script type="text/javascript" src="/statics/localjs/memberlist.js"></script> 
