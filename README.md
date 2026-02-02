# Trabalho de POO

## Alunos
- [Caio Gabriel Cruz Amorim](https://github.com/CaioGabriel777)
- [Luan Vitor Ribeiro Guimarães](https://github.com/LuanVitorRibeiroGuimaraes)

## Introdução
Falar sobre o projeto

## Tecnologias
- **Java 25**
- **Java FX**
- **Maven**
- **Docker**
- **MariaDB**

## Funcionalidades
Falar sobre as funcionalidades

## Instruções de Execução
- **Subir o Banco de Dados**: 
```bash
cd docker 
docker-compose up -d
```
- **Compilar**:
```bash
cd microkernel
mvn clean package -DskipTests
```
- **Executar**:
```bash
cd microkernel
mvn javafx:run -pl app
```

- **Comandos Completos:**
##### ⚠️ Esteja na raiz do projeto
```bash
cd docker 
docker-compose up -d
cd ../microkernel
mvn clean package -DskipTests
mvn javafx:run -pl app
```
