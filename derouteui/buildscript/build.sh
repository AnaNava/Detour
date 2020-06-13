npm install 
ng build --base-href="ngs" --prod --output-hashing=all --aot=false --build-optimizer=false
jar -cvfM ngsui.war -C ./dist .
