#!/bin/bash
rm -rf build;
mkdir build;
for proj in billboard client manager; do
	cd "$proj";
	mvn clean javafx:jlink;
	if [ "$?" = "1" ]; then
		echo "Error while executing mvn clean javafx:jlink for $proj module";
		break;
	fi;
	cd ..;
	cp -r "$proj/target/app" "build/$proj"
	cp "$proj/drozdkeys.jks" "build/$proj/drozdkeys.jks"
	cp "$proj/policy.policy" "build/$proj/policy.policy"
done

