usage() {
    echo "$0 [-f] <test> [<test2> ...]"
    echo
    echo "By default it will look in testcase/tc<test>.txt."
    echo "<test> doesn't need to be zero padded."
    echo
    echo "-h | --help: Show this info"
    echo "-f | --file: <test> will be interpreted as a filename"                      
    echo
    echo "Examples:"
    echo "   $0 8"
    echo "   $0 08"
    echo "   $0 -f /path/to/my/test.txt"
    exit 1
}

exit_with_error() {
    echo "Error running test $1"
    exit 2
}

run_test() {
    filename=$1 
    java -cp customBuild:dist/lib/swing-layout-1.0.4.jar interiores/Interiores < $filename
    return $?
}

read_filenames() {
    [[ -z $1 ]] && usage
    while (( "$#" )); do
	if run_test $1; then
	    shift
	else
	    exit_with_error $1
	fi
    done
    exit 0
}

read_numbers() {
    [[ -z $1 ]] && usage
    while (( "$#" )); do
	shopt -s extglob
	filename=$(ls testcase/tc?(0)${1}.txt)
	shopt -u extglob
	if run_test $filename; then
	    shift
	else
	    exit_with_error $1
	fi
    done
    exit 0
}

[[ -z $1 ]] && usage
case "$1" in
    -h|--help)
        usage
        ;;
    -f|--file) shift
	read_filenames $@
        ;;
    *)  read_numbers $@
        ;;
esac
