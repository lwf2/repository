#��/bin/sh
#=====================================================
#���ܣ��ϴ������ļ������ҿ�FTP������
#
#���ߣ�chenpeng
#���ڣ�2017-10-19
#=====================================================
echo " call the uploadOrderCountFile.sh start"
#########��ʼ��������#################################
#���ӵ�cdr sftp��������
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/count/result
lcd /home/d139/d/ordercount/result
mput *rwkTradeList*
bye
EOF