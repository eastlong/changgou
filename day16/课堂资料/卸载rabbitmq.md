/sbin/service rabbitmq-server stop
yum list | grep rabbitmq
yum -y remove rabbitmq-server.noarch

yum list | grep erlang
yum -y remove erlang-*
yum -y remove erlang.x86_64 
rm -rf /usr/lib64/erlang
rm -rf /var/lib/rabbitmq






systemctl stop firewalld.service 



systemctl disable firewalld.service



1.先卸载



2.再安装



3.再从A中 copy cookie 到B中



4.再全部重启,加入节点即可



5.将b加入到A中

