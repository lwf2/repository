#��/bin/sh
#=====================================================
#���ܣ����������ϴ����صĶ����ļ���Ŀ�������
#
#���ߣ�chenpeng
#���ڣ�2017-10-19
#=====================================================
echo " call the ordercheckbackupput.sh start"
#########��ʼ��������#################################
#���ӵ�cdr sftp��������
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/order/backup
lcd /home/d139/d/order/download
mput *PayList*
bye
EOF