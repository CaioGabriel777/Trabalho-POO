# Plano de Implementação - Sistema de Locadora de Veículos

Sistema Java/JavaFX com arquitetura microkernel para gerenciamento de locadora de veículos, utilizando interfaces, polimorfismo e plugins dinâmicos.

## Requisitos Importantes

> ⚠️ **Java 25.0.1**: O projeto atual está configurado para Java 11. Será necessário atualizar a JDK e o `pom.xml`.

> ⚠️ **MariaDB via Docker**: O banco roda via Docker na **porta 3307**. Execute `docker-compose up -d` na pasta `docker/`.

---

## Estrutura de Componentes

### 1. Configuração Maven

**[MODIFY] pom.xml (raiz)**
- Atualizar Java version de 11 para 25
- Adicionar novos módulos de plugins ao projeto

**[MODIFY] pom.xml (interfaces)**
- Adicionar dependência MariaDB Connector/J

---

### 2. Camada de Dados

**[NEW] DatabaseConnection.java**
- Singleton para gerenciar conexão JDBC com MariaDB
- Host: localhost, Port: 3307, DB: car_rental_system
- User: root, Password: root

**[NEW] Modelos (pasta models/)**
- `Customer.java` - cliente (id, email, nome, etc.)
- `Vehicle.java` - veículo (id, make, model, year, fuel_type, etc.)
- `VehicleType.java` - tipo com `additional_fees` para cálculo polimórfico
- `Rental.java` - locação

---

### 3. Interface de Plugin de Veículo (Polimorfismo)

**[NEW] IVehiclePlugin.java**
```java
public interface IVehiclePlugin extends IPlugin {
    String getTypeName();          // "ECONOMY", "COMPACT", etc.
    double calculateTotal(double baseRate, int days, double insuranceFee, Map<String, Double> additionalFees);
}
```

---

### 4. Plugins de Tipos de Veículos (6 plugins)

Cada plugin implementa `IVehiclePlugin` e registra um item de menu para criar locações:

| Plugin | Tipo BD | Taxas Adicionais |
|--------|---------|------------------|
| `EconomicoPlugin` | ECONOMY | `mileage_limit`, `extra_mileage_fee` |
| `CompactPlugin` | COMPACT | `mileage_limit`, `extra_mileage_fee` |
| `SUVPlugin` | SUV | `mileage_limit`, `extra_mileage_fee`, `offroad_fee` |
| `LuxoPlugin` | LUXURY | `concierge_fee`, `chauffeur_fee` |
| `VANPlugin` | VAN | `extra_passenger_fee`, `driver_fee` |
| `EletricoPlugin` | ELECTRIC | `charging_fee`, `battery_depletion_fee` |

**Estrutura de cada Plugin:**
```
plugins/
├── economico/
│   ├── pom.xml
│   └── src/main/java/br/edu/ifba/inf008/plugins/EconomicoPlugin.java
├── compact/
├── suv/
├── luxo/
├── van/
└── eletrico/
```

Cada plugin:
1. Cria menu item "Locação > [Tipo]"
2. Abre aba com formulário de aluguel
3. Calcula valor total usando método polimórfico

---

### 5. UI de Aluguel (Formulário)

**[NEW] RentalForm.java**

Formulário reutilizável com:
- **ComboBox de Clientes**: Carrega emails da tabela `customers`
- **TableView de Veículos**: Mostra veículos disponíveis do tipo selecionado
  - Colunas: make, model, year, fuel_type, transmission, mileage
- **DatePicker**: Data início (`start_date`) e fim (`scheduled_end_date`)
- **TextField**: Local de retirada (`pickup_location`)
- **TextField**: Diária (`base_rate`) e Seguro (`insurance_fee`)
- **Label**: Valor total calculado
- **Button Confirmar**: Insere em `rentals` e atualiza `vehicles.status = 'RENTED'`

---

### 6. Plugins de Relatórios (2 plugins)

**[NEW] Relatorio1Plugin.java**
- Menu "Relatórios > Distribuição por Combustível"
- Executa query do `report1.sql`
- Exibe **PieChart** (JavaFX) com cores definidas na query

**[NEW] Relatorio2Plugin.java**
- Menu "Relatórios > Dados de Locações"
- Executa query do `report2.sql`
- Exibe **TableView** com colunas: rental_id, customer_name, vehicle, start_date, total_amount, status

---

## Diagrama de Polimorfismo

```
         IPlugin
            │
            ▼
      IVehiclePlugin
       ┌────┼────┐
       │    │    │
       ▼    ▼    ▼
  Economico Compact SUV ...
```

---

## Verificação

### Build Automático
```bash
cd microkernel
mvn clean package -DskipTests
```

### Teste Manual - Fluxo de Locação
1. Iniciar banco: `cd docker && docker-compose up -d`
2. Executar app: `cd microkernel/app && mvn exec:java`
3. Verificar: Menu "Locação" aparece com 6 tipos
4. Clicar em "Locação > Econômico"
5. Preencher formulário e confirmar
6. Verificar: Veículo muda status para RENTED

### Teste de Plugins Dinâmicos
1. Remover um JAR da pasta `plugins/`
2. Reiniciar aplicação
3. Verificar: Menu correspondente não aparece
