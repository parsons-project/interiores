[[ -d customBuild ]] || mkdir customBuild

find src -name *.java > classes

javac -cp .:dist/lib/swing-layout-1.0.4.jar @classes -d customBuild

rm classes
