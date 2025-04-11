# Projeto Hype Advice

Siga as orientações abaixo para configurar o projeto:

* Faça o download e instalação do postgres versão **9.6.24** no link https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
* Faça o download e instalação do pgAdmin no link https://www.pgadmin.org/download/
* Após instalado e configurado crie um super user com nome **hypeadvice** e senha **hypeadvice** no pgAdmin
![img.png](img.png)
![img_3.png](img_3.png)

* Crie um data base com nome **hypeadvice** e defina o owner **hypeadvice**
![img_1.png](img_1.png)
![img_2.png](img_2.png)

**IMPORTANTE:** garanta que o data base fique em "jdbc:postgresql://localhost:5432/hypeadvice", caso contrario atualize o parametro spring.datasource.url do arquivo application.properties

* O projeto deve rodar na JDK versão **8**
* Baixe as dependências via **Maven**

* Indicamos que seja utilizado como IDE o Intellij link: https://www.jetbrains.com/idea/download/

Após iniciar a aplicação, acesse http://localhost:8080/advice-list.xhtml