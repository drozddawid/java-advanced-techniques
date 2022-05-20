cd filecrypto;
mvn clean install;
if [ "$?" == "1" ]; then
	echo "filecrypto build error";
	exit 1;
fi;
cd ..;
cd filecryptoapp;
mvn clean package;
if [ "$?" == "1" ]; then
	echo "filecryptoapp build error";
	exit 1;
fi;
cd ..;
rm -rf build;
mkdir build;
cp -r filecryptoapp/target/lib build/lib;
cp filecryptoapp/target/filecryptoapp-1.0.jar build/filecryptoapp-1.0.jar;
cp policy.policy build/policy.policy;
cp keystore_for_testing.pfx build/keystore_for_testing.pfx;
cp trusted.pass build/trusted.pass;
cp trusted.pfx build/trusted.pfx
mkdir build/lib/signed;
cp build/lib/filecrypto-1.0.jar build/lib/signed/filecrypto-1.0.jar;
jarsigner -keystore drozd.pfx -storepass password -storetype PKCS12 build/lib/signed/filecrypto-1.0.jar drozd;
echo "java -Djava.security.manager -Djava.security.policy=\"policy.policy\" --module-path \"lib;lib/signed\" --add-modules ALL-MODULE-PATH -jar filecryptoapp-1.0.jar" > build/run.sh