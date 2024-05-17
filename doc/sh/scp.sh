# 打包
mvn -Dmaven.test.skip=true clean package

# 推送jar到服务器
scp miaosha.jar root@43.142.173.83:/root/app/miaosha
