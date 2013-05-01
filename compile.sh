[[ -d customBuild ]] || mkdir customBuild

find src -name *.java > classes

javac -cp .:swing-layout.jar @classes -d customBuild

rm classes
