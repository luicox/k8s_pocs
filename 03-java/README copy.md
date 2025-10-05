# 🧠 Proyecto: Quarkus Greeting Service

Ejemplo completo de cómo crear, construir, desplegar y actualizar un servicio REST con **Quarkus**, que devuelve un JSON, usando **Docker Desktop con Kubernetes habilitado**.

---

## 🧩 Prerrequisitos

Asegúrate de tener instalado:

- ☕ **Java 21**
- 🐘 **Maven 3+**
- 🐳 **Docker Desktop** (con Kubernetes habilitado)
- 💻 macOS / Windows / Linux

Verifica Kubernetes:
```bash
kubectl get nodes
Debe aparecer un nodo Ready.

🏗️ 1️⃣ Crear el proyecto Quarkus
Ejecuta:

bash

mvn io.quarkus.platform:quarkus-maven-plugin:3.11.2:create \
    -DprojectGroupId=com.example \
    -DprojectArtifactId=quarkus-greeting \
    -DclassName="com.example.GreetingResource" \
    -Dpath="/greeting" \
    -DjavaVersion=21

Esto genera el proyecto en la carpeta quarkus-greeting/.

🧭 2️⃣ Entrar al proyecto
bash

cd quarkus-greeting
🧩 3️⃣ Agregar extensiones necesarias
Agrega soporte JSON y para construir imágenes Docker:

bash
./mvnw quarkus:add-extension -Dextensions="kubernetes,rest-jackson,container-image-docker"

Edita el archivo src/main/java/com/example/GreetingResource.java y reemplaza su contenido con:

java

package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

@Path("/greeting")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> greeting() {
        return Map.of("message", "Hello from Quarkus Greeting Service!");
    }
}
🧪 5️⃣ Probar localmente
Ejecuta el modo dev:

bash

./mvnw quarkus:dev -Dquarkus.http.port=9000
Abre:
http://localhost:9000/greeting

Deberías ver:

json

{"message":"Hello from Quarkus Greeting Service!"}
Detén con Ctrl + C.

🐳 6️⃣ Construir la imagen Docker
Ejecuta:

bash

docker build -f src/main/docker/Dockerfile.jvm -t quarkus-greeting:1.0.0 .
Verifica:

bash

docker images | grep quarkus-greeting
☸️ 7️⃣ Crear manifiesto de Kubernetes
Crea la carpeta k8s y dentro un archivo quarkus-greeting.yaml con:

yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: quarkus-greeting
  labels:
    app: quarkus-greeting
spec:
  replicas: 1
  selector:
    matchLabels:
      app: quarkus-greeting
  template:
    metadata:
      labels:
        app: quarkus-greeting
    spec:
      containers:
        - name: quarkus-greeting
          image: quarkus-greeting:1.0.0
          imagePullPolicy: Never
          ports:
            - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: quarkus-greeting
spec:
  selector:
    app: quarkus-greeting
  ports:
    - name: http
      port: 8081
      targetPort: 8080
  type: LoadBalancer
🚀 8️⃣ Desplegar en Kubernetes
Aplica el manifiesto:

bash

kubectl apply -f k8s/quarkus-greeting.yaml
Verifica los pods:

bash

kubectl get pods
Cuando el pod esté Running, prueba:

bash

curl http://localhost:8080/greeting
Deberías ver:

json

{"message":"Hello from Quarkus Greeting Service!"}
🔁 9️⃣ Modificar el código y reflejar cambios
Si editas tu clase Java (por ejemplo cambias el mensaje), sigue estos pasos:

bash

./mvnw clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-greeting:1.0.0 .
kubectl rollout restart deployment/quarkus-greeting
Kubernetes recreará el contenedor con la nueva versión (mismo tag).

Verifica:

bash

kubectl get pods
Y prueba de nuevo:

bash

curl http://localhost:8080/greeting
⚡ 10️⃣ (Opcional) Script para redeploy rápido
Crea un archivo redeploy.sh en la raíz del proyecto:

bash

#!/bin/bash
echo "🔄 Building and redeploying Quarkus Greeting..."
./mvnw clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-greeting:1.0.0 .
kubectl rollout restart deployment/quarkus-greeting
echo "✅ Done! New version deployed."
Dale permisos de ejecución:

bash

chmod +x redeploy.sh
Y cada vez que modifiques el código, solo ejecuta:

bash

./redeploy.sh
🧹 11️⃣ Eliminar el despliegue
Cuando termines:

bash

kubectl delete -f k8s/quarkus-greeting.yaml
✅ Resultado final
✔️ Proyecto Quarkus REST funcionando
✔️ Devuelve un JSON
✔️ Empaquetado en Docker
✔️ Desplegado y actualizable en Kubernetes local (Docker Desktop)

bash

curl http://localhost:8080/greeting
👉 Devuelve:

json

{"message":"Hello from Quarkus Greeting Service!"}