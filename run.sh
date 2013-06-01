usage() {
        echo "$0 <testFileName>"
        exit 1
}

java -cp customBuild:dist/lib/swing-layout-1.0.4.jar interiores/Interiores
