# tener instalado brew

brew --version

brew install openjdk@21
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
java -version

brew install maven
mvn -version
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="$JAVA_HOME/bin:$PATH"
export M2_HOME=/opt/homebrew/Cellar/maven/3.x.x/libexec
export PATH="$M2_HOME/bin:$PATH"
source ~/.zshrc


mvn io.quarkus.platform:quarkus-maven-plugin:3.11.2:create \
    -DprojectGroupId=com.example \
    -DprojectArtifactId=quarkus-hello \
    -DclassName="com.example.HelloResource" \
    -Dpath="/hello" \
    -DjavaVersion=21



./mvnw clean package -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.name=hello-quarkus \
    -Dquarkus.container-image.tag=1.0.0

-----
./mvnw clean package -Dquarkus.kubernetes.deploy=true