# Parrot Api 

Api de uma rede social desenvolvida em Java com Spring Boot e MongoDB.

## Instalação

### Via docker-hub
[https://hub.docker.com/r/luizgustavo0/parrot-app](https://hub.docker.com/r/luizgustavo0/parrot-app)
```shell
docker pull luizgustavo0/parrot-app
```
após baixar a imagem siga as intruções de configuração do localstack <a href='#importante'>aqui</a>.

### Local
entre na pasta do projeto
```shell
cd backend 
```
execute os comandos a seguir
```shell
mvn install 

docker-compose build

docker-compose up -d
```

A aplicação responde na porta 8082

<h2 id='importante'>Importante</h2>
Após a instalação é necessário configurar o localstack para fazer a persistência de arquivos de imagem.

No terminal na pasta do projeto excute:
```bash
docker exec -it parrot-localstack bash
```
 em seguida
```bash
aws configure --profile default
```
adicione as seguites configurações
```bash
key: mykey
secretKey: mykey
output: json
```
em seguida configure o bucket e o endpoint url
```bash
aws s3 mb s3://parrot-bucket --endpoint-url http://localhost:4566
```
