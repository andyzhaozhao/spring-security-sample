# 生成一个证书（certificate authority (CA)）
keytool -genkey -alias ca -ext BC=ca:true \
    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass $(PASSWORD) \
    -validity 3650 -dname $(DNAME_CA) \
    -keystore $(KEYSTORE) -storepass $(PASSWORD)
其中参数：
PASSWORD=123456
KEYSTORE=keystore.jks
HOSTNAME=localhost
DNAME_CA='CN=a,OU=a,O=a,L=a,ST=a,C=CC'

实际输入：
keytool -genkey -alias ca -ext BC=ca:true \
    -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass 123456 \
    -validity 3650 -dname 'CN=a,OU=a,O=a,L=a,ST=a,C=CC' \
    -keystore keystore.jks -storepass 123456

浏览证书库里面的证书信息
keytool -list -keystore keystore.jks

#