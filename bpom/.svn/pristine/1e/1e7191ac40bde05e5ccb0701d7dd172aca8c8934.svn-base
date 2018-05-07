#！/bin/sh
#=====================================================
#功能：订单对账下载支付网关订单文件到本地服务器
#
#作者：chenpeng
#日期：2017-10-19
#=====================================================
echo " call the ordercheckget.sh start"
#########初始配置数据#################################
#链接到cdr sftp服务器上
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/order/file
lcd /home/d139/d/order/download
mget *PayList*
mrm *PayList*
bye
EOF