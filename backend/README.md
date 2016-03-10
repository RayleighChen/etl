# ETL脚本执行WEB后端应用

# 编译

	mvn package

# 运行

	mvn tomcat7:run
	
# 测试发送任务(开启服务器之后，运行executor，之后可以测试发送任务，另开命令行界面，运行如下命令)

	mvn test -Dtest=Sent
	
# 运行结果

![最后效果](test.png)