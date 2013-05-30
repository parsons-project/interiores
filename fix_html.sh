sed -i.old '/2013/d' *.html
sed -i.old '/--Navigation Panel/,+13d' *.html
sed -i.old 's/<BODY >/<BODY >\n<DIV CLASS="navigation">\n/' *.html
