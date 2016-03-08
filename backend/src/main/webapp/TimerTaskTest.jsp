<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<title>Try</title>
<script type="text/javascript" src="./js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#send1').click(
				function() {
					var params = $('input').serialize(); //将input元素序列化，返回JSON数据格式 
					var id = $("#Id1").val();
					$.ajax({
						type : 'GET', //请求方式为get方式
						url : './timertask/' + id, //请求地址
						dataType : 'json', //服务器返回类型为JSON类型
						data : params, //发送到服务器的数据
						success : function(data) { //请求成功后的回调函数
							$('#show').html(
									"get TimerTask: " + data.Name + " "
											+ data.Script + " " + data.ExeTime
											+ " " + data.ExeInterval+ " " + data.Description+ " " + data.Status);

						},
						error : function(data) {
							$('#show').html("查询失败！")
						}
					});

				});
		
		$('#send5').click(
				function() {
					var params = $('#update_Form').serialize(); //将input元素序列化，返回JSON数据格式 
					//var id = $("#Id5").val();
					$.ajax({
						type : 'POST', //请求方式为post方式
						url : './timertask/' + "update", //请求地址
						dataType : 'json', //服务器返回类型为JSON类型
						data :params, //发送到服务器的数据
						success : function(data) { //请求成功后的回调函数
							$('#show').html("更新成功 ！")
						},
						error : function(data) {
							$('#show').html("更新失败！")
						}
					});

				});
		
			$('#send3').click(
					function() {
						var params = $('input').serialize(); //将input元素序列化，返回JSON数据格式 
						var id = $("#Id3").val();
						$.ajax({
							type : 'delete', //
							url : './timertask/' + id, //请求地址
							dataType : 'json', //服务器返回类型为JSON类型
							data : params, //发送到服务器的数据
							success : function(data) { //请求成功后的回调函数
							if(data==true)
								$('#show').html("删除成功！");
							else
								$('#show').html("删除失败！");

							},
							error : function(data) {
								$('#show').html(" 删除失败！")
							}
						});

					});
			
			$('#send4').click(
					function() {
						var params = $('#add_Form').serialize(); //将input元素序列化，返回JSON数据格式 
						var id = $("#Id4").val();
						$.ajax({
							type : 'POST', //请求方式为post方式
							url : './timertask/' + "add", //请求地址
							dataType : 'json', //服务器返回类型为JSON类型
							data : params, //发送到服务器的数据
							success : function(data) { //请求成功后的回调函数
								$('#show').html("添加成功！");
							console.log(data)
							
							},
							error : function(data) {
								$('#show').html("添加失败！")
							}
						});

					});
			$('#all').click(
				function() {
					
					$.ajax({
						type : 'get', //请求方式为get方式
						url : './timertask/',//请求地址
						dataType : 'json', //服务器返回类型为JSON类型
						success : function(data) { //请求成功后的回调函数
							$.each(data, function(i, item) {
								$('#TaskList').append(item.Name + " "
										+ item.Script + " " + item.ExeTime
										+ " " + item.ExeInterval+ " " + item.Description+ " " + item.Status + '<br/>')
							});
						},
						error : function(data) {
							$('#TaskList').html("get error")
						}
					});
				}	
			
			);
			
			
							
	})
	
</script>
</head>

<body>
	<center>
		<h2>查询页面</h2>
		<form id="logForm" method="post">
			<table>
				<tr>
					<td><label>get Id：</label></td>
					<td><input type="text" id="Id1" name="Id1"></td>
					<td><input type="button" id="send1" value="提交"></td>
				</tr>
				
				
				<tr>
					<td><label>delete Id：</label></td>
					<td><input type="text" id="Id3" name="Id3"></td>
					<td><input type="button" id="send3" value="提交"></td>
				</tr>			
			</table>
		</form>
		
		<form id="add_Form" method="post">
		<table>
		<tr>
		
					<td><label>add：</label></td>
		</tr>
		<tr>
					
					<td><label>name：</label></td>
					<td><input type="text" id="Name" name="Name"></td>
					<td><label>Script：</label></td>
					<td><input type="text" id="Script" name="Script"></td>
					<td><label>Status：</label></td>
					<td><input type="text" id="Status" name="Status"></td>
					<td><label>Description：</label></td>
					<td><input type="text" id="Description" name="Description"></td><br>
					<td><input type="button" id="send4" value="提交"></td>
		</tr>
		</table>
		</form>
		
		<form  id="update_Form" method="post">
		<table>
		<tr>
					<td><label>update：</label></td>
					</tr>
					<tr>
					<td><label>id：</label></td>
					<td><input type="text" id="Id" name="Id"></td>
					<td><label>name：</label></td>
					<td><input type="text" id="Name" name="Name"></td>
					<td><label>Script：</label></td>
					<td><input type="text" id="Script" name="Script"></td>
					<td><label>Status：</label></td>
					<td><input type="text" id="Status" name="Status"></td>
					<td><label>Description：</label></td>
					<td><input type="text" id="Description" name="Description"></td>
					<td><input type="button" id="send5" value="提交"></td>
		</tr>
		</table>
		</form>														
		<div id="show"></div>
			<center>
		<button id="all">查看任务列表</button>
		<div id="TaskList"></div>
	</center>		
	</center>
</body>
</html>