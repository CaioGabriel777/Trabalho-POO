# Sistema de Locadora de Veículos - Checklist de Tarefas

## 1. Infraestrutura Base
- [ ] Adicionar driver MariaDB/MySQL ao projeto Maven
- [ ] Criar classe `DatabaseConnection` para gerenciar conexões JDBC
- [ ] Expandir `IUIController` com métodos auxiliares
- [ ] Atualizar versão Java para 25.0.1 conforme requisito

## 2. Modelos de Dados (Entities)
- [ ] Criar classe `Customer` (cliente)
- [ ] Criar classe `Vehicle` (veículo base)
- [ ] Criar classe `VehicleType` (tipo de veículo)
- [ ] Criar classe `Rental` (locação)

## 3. Camada de Serviços (Data Access)
- [ ] Criar `CustomerService` para listar clientes
- [ ] Criar `VehicleService` para listar veículos por tipo/disponibilidade
- [ ] Criar `RentalService` para inserir locações e calcular valores
- [ ] Criar `ReportService` para executar queries dos relatórios

## 4. Plugins de Tipos de Veículos
- [ ] Criar interface `IVehiclePlugin` com método polimórfico de cálculo
- [ ] Criar `EconomicoPlugin` (tipo ECONOMY)
- [ ] Criar `CompactPlugin` (tipo COMPACT)
- [ ] Criar `SUVPlugin` (tipo SUV)
- [ ] Criar `LuxoPlugin` (tipo LUXURY)
- [ ] Criar `VANPlugin` (tipo VAN)
- [ ] Criar `EletricoPlugin` (tipo ELECTRIC)

## 5. Plugins de Relatórios
- [ ] Criar `Relatorio1Plugin` - Gráfico de pizza (distribuição por combustível)
- [ ] Criar `Relatorio2Plugin` - Tabela de locações

## 6. Funcionalidade de Aluguel (UI)
- [ ] Criar formulário reutilizável `RentalForm` com:
  - [ ] ComboBox de clientes (emails)
  - [ ] TableView de veículos disponíveis
  - [ ] DatePicker para datas (início/fim)
  - [ ] TextField para local de retirada
  - [ ] TextField para diária e seguro
  - [ ] Label com valor total calculado
  - [ ] Button de confirmação

## 7. Lógica de Negócio
- [ ] Implementar cálculo polimórfico do valor total:
  - Valor = (diária × dias) + taxas adicionais com sufixo `_fee`
- [ ] Inserir registro na tabela `rentals`
- [ ] Atualizar status do veículo para "RENTED"
- [ ] Tipo de locação sempre "DAILY"

## 8. Verificação e Documentação
- [ ] Testar compilação via Maven (sem IDE)
- [ ] Testar cada plugin individualmente
- [ ] Testar adição/remoção de plugins sem recompilação
- [ ] Atualizar README.md com instruções de compilação e execução
