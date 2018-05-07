#！/bin/sh
#=====================================================
#功能：上传结算文件到任我看FTP服务器
#
#作者：chenpeng
#日期：2017-10-19
#=====================================================
echo " call the uploadOrderCountFile.sh start"
#########初始配置数据#################################
#链接到cdr sftp服务器上
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/count/result
lcd /home/d139/d/ordercount/result
mput *rwkTradeList*
bye
EOF