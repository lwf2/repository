#！/bin/sh
#=====================================================
#功能：签约对账上传下载的签约文件到目标服务器
#
#作者：chenpeng
#日期：2017-10-19
#=====================================================
echo " call the contractcheckbackupput.sh start"
#########初始配置数据#################################
#链接到cdr sftp服务器上
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/contract/backup
lcd /home/d139/d/contract/download
mput *wxEntrustPay*
bye
EOF