# My Favorite Movie 

## Sobre o projeto

My Favorite Movie é uma REST API feita em Spring Boot. 

A aplicação consiste em uma API que fornece endpoints para a criação de uma lista de filmes, separadas por categoria.

A criação é feita com base na API do IMDb - Internet Movie Database.

# Como funciona a API

## Requisito

- Fazer registro no site http://www.omdbapi.com/apikey.aspx para conseguir ima key.

- Substituir a key no application.properties

![application.properties](https://github.com/leoljunior/assets/blob/master/application%20properties.png)

- Subir um container Docker com o MySQL, com o seguinte comando:

docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven

# Autor

Leonardo de Lima Junior

https://www.linkedin.com/in/leonardo-de-lima-junior-11618b200/
