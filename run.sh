usage() {
	echo "$0 <testFileName>"
	exit 1
}


[[ -z $1 ]] && usage 


java -cp customBuild:dist/lib/swing-layout-1.0.4.jar interiores/Interiores < $1
