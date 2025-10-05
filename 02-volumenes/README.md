# Eliminar el deployment y el service, si existen
kubectl delete deployment nginx-deployment-persist --ignore-not-found
kubectl delete svc nginx-service-persist --ignore-not-found

# Forzar la eliminación del PVC y PV
kubectl delete pvc nginx-pvc-persist --grace-period=0 --force --ignore-not-found
kubectl delete pv nginx-pv-persist --grace-period=0 --force --ignore-not-found


kubectl get pv,pvc


kubectl describe pvc nginx-pvc-persist


kubectl apply -f pv.yaml
kubectl apply -f pvc.yaml
kubectl apply -f nginx-deployment.yaml
kubectl apply -f nginx-service.yaml


kubectl get pods -l app=nginx-persist

kubectl exec -it nginx-deployment-persist-57bc97b776-x4kbv -- bash
ls -l /usr/share/nginx/html

echo "<h1>Hello Persistent NGINX!</h1>" > /usr/share/nginx/html/index.html
