# Sistema de Locadora de Veículos

Sistema Java/JavaFX com arquitetura **Microkernel** para gerenciamento de locadora de veículos.

## Pré-requisitos

- **JDK 25** (conforme especificação do trabalho)
- **Maven 3.6+**
- **Docker** (para o banco MariaDB)

## Configuração do Banco de Dados

1. Navegue até a pasta `docker/`:
   ```bash
   cd docker
   ```

2. Inicie o container MariaDB:
   ```bash
   docker-compose up -d
   ```

O banco estará disponível em `localhost:3307` com:
- **Database**: car_rental_system
- **User**: root
- **Password**: root

## Compilação

Na pasta `microkernel/`:

```bash
mvn clean package -DskipTests
```

Os JARs dos plugins serão gerados em `microkernel/plugins/`.

## Execução

Na pasta `microkernel/app/`:

```bash
mvn exec:java
```

## Funcionalidades

### Menu Locação
- **Econômico** - Veículos tipo ECONOMY
- **Compact** - Veículos tipo COMPACT  
- **SUV** - Veículos tipo SUV
- **Luxo** - Veículos tipo LUXURY
- **VAN** - Veículos tipo VAN
- **Elétrico** - Veículos tipo ELECTRIC

### Menu Relatórios
- **Distribuição por Combustível** - Gráfico de pizza (PieChart)
- **Dados de Locações** - Tabela com até 100 locações

## Arquitetura

```
microkernel/
├── interfaces/     # Contratos e modelos compartilhados
├── app/            # Núcleo da aplicação (Core, Controllers)
└── plugins/        # Plugins carregados dinamicamente
    ├── economico/
    ├── compact/
    ├── suv/
    ├── luxo/
    ├── van/
    ├── eletrico/
    ├── relatorio1/
    └── relatorio2/
```

### Polimorfismo

Cada plugin de veículo implementa `IVehiclePlugin`, que define o método polimórfico:

```java
double calculateTotal(double baseRate, int days, double insuranceFee, Map<String, Double> additionalFees);
```

O cálculo considera as taxas adicionais específicas de cada tipo (campos com sufixo `_fee`).

## Adicionando/Removendo Plugins

Para adicionar ou remover funcionalidades, basta:
1. Adicionar/remover o arquivo `.jar` da pasta `plugins/`
2. Reiniciar a aplicação

**Não é necessário recompilar o sistema principal.**
