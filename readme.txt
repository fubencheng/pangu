1.在pangu-web工程目录下运行命令npm run build，对应web前端开发进行打包
2.将打包后dist目录下的文件夹和文件拷贝到pangu工程的static目录下
3.使用maven对springboot工程进行打包，运行命令 mvn clean install -Dmaven.test.skip=true
4.将target目录下的pangu-0.0.1.jar包拷贝或者上传到需要部署的应用主机上
5.启动服务，后台运行启动命令 java -jar pangu-0.0.1.jar --spring.profiles.active=prod >pangu-start.log &