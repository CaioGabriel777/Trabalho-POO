# Trabalho de POO

## Alunos
- [Caio Gabriel Cruz Amorim](https://github.com/CaioGabriel777)
- [Luan Vitor Ribeiro Guimarães](https://github.com/LuanVitorRibeiroGuimaraes)

## Link do vídeo
[Vídeo YouTube](https://youtube.com)

## Tecnologias
- **Java 25**
- **Java FX**
- **Maven**
- **Docker**
- **MariaDB**

## Funcionalidades
- **Nova Locação**: Formulário para alugar veículos com seleção de cliente, veículo disponível, datas e cálculo automático do valor
- **6 Tipos de Veículos**: Economy, Compact, SUV, Luxury, VAN e Electric (cada um como plugin independente)
- **Cálculo Polimórfico**: Cada tipo de veículo pode ter sua própria lógica de precificação via `calculateTotal()`
- **Relatório de Combustível**: Gráfico de pizza mostrando distribuição da frota por tipo de combustível
- **Relatório de Locações**: Tabela com as últimas 100 locações realizadas
- **Plugins Dinâmicos**: Novos tipos de veículos podem ser adicionados sem recompilar o sistema

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
