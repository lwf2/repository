#��/bin/sh
#=====================================================
#���ܣ�������������֧�����ض����ļ������ط�����
#
#���ߣ�chenpeng
#���ڣ�2017-10-19
#=====================================================
echo " call the ordercheckget.sh start"
#########��ʼ��������#################################
#���ӵ�cdr sftp��������
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/order/file
lcd /home/d139/d/order/download
mget *PayList*
mrm *PayList*
bye
EOF