
# Example project JPA

* GraalVM/Native Image
* ORM
* Eclipse Link

https://www.graalvm.org/22.2/reference-manual/native-image/guides/use-native-image-maven-plugin/

Compile to standard jvm.

```
mvn clean package

java -jar target/jpaEntityManager.jar

java -jar target/jpaEntityManager-jar-with-dependencies.jar
```

## Collect info

```
mvn clean package

mvn -Pnative -Dagent exec:exec@java-agent
```

### Generate files in folder 

> target/native/agent-output/main/

## Compile using graalvm/native-image

```
mvn -Pnative -Dagent package
```

## Run the native project executable (Assuming you are in root folder of the project)

target/jpaEntityManager

## Reference images

Vs Code Enviroment 01
![vs_code_01](others/images/vs_code_01.png)

Vs Code Enviroment 02
![vs_code_02](others/images/vs_code_02.png)

Vs Code Enviroment 03
![vs_code_03](others/images/vs_code_03.png)

Vs Code Enviroment 04
![vs_code_04](others/images/vs_code_04.png)
