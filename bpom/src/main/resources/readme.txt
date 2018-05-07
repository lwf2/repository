#证书
证书导入到本地的jre路径，记住一定是jre路径，而不是jdk,另外注意tomcat要加载jre而不是jdk
keytool -import -keystore  "D:\Program Files (x86)\Java\jre6\lib\security\cacerts"  -file d:\casssov1.crt -alias casssov1
password:changeit

接入参考spring-context-shiro.xml


提取用户名
#com.aspire.birp.modules.sys.authentication.SimpleCasRealm
 AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
             String userId = casPrincipal.getName();  
 可以拿到用户名

#权限加载根据rmi 接口 提取 菜单等信息；---未完成


#测试环境
10.9.20.15 /root/software 下TOMCAT
