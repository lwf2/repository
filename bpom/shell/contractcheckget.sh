#��/bin/sh
#=====================================================
#���ܣ�ǩԼ��������ǩԼ�ļ������ط�����
#
#���ߣ�chenpeng
#���ڣ�2017-10-18
#=====================================================
echo " call the contractcheckget.sh start"
#########��ʼ��������#################################
#���ӵ�cdr sftp��������
lftp -u wabp,wabp@123 sftp://10.12.12.175 <<EOF
cd /home/d139/test/contract/file
lcd /home/d139/d/contract/download
mget *wxEntrustPay*
mrm *wxEntrustPay*
bye
EOF