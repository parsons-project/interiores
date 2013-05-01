usage() {
	echo "$0 <testFileName>"
	exit 1
}


[[ -z $1 ]] && usage 


java -cp customBuild:swing-layout.jar interiores/Interiores < $1
