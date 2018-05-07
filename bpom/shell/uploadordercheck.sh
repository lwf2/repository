#！/bin/sh
#=====================================================
#功能：上传生成的订单文件到任我看FTP服务器
#
#作者：chenpeng
#日期：2017-10-19
#=====================================================
echo " call the uploadOrdercheck.sh start"
#########初始配置数据#################################
#链接到cdr sftp服务器上
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/order/rwkresult
lcd /home/d139/d/order/result
mput *PayList*
bye
EOF