import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // 3.1 - Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria" ,  LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João"  ,  LocalDate.of(1990,  5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio"  ,  LocalDate.of(1961,  5,  2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel",  LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice" ,  LocalDate.of(1995,  1,  5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor",  LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur",  LocalDate.of(1993,  3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura" ,  LocalDate.of(1994,  7,  8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003,  5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena",  LocalDate.of(1996,  9,  2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário "João" da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aplicar aumento de 10% no salário
        aumentarSalarios(funcionarios);

        // 3.5 e 3.6 - Agrupar por função e imprimir
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 - Imprimir funcionários com aniversário nos meses 10 e 12
        imprimirAniversariantes(funcionarios, 10, 12);

        // 3.9 - Imprimir funcionário com maior idade
        imprimirFuncionarioMaiorIdade(funcionarios);

        // 3.10 - Imprimir lista de funcionários por ordem alfabética
        imprimirFuncionariosOrdenados(funcionarios);

        // 3.11 - Imprimir total dos salários
        imprimirTotalSalarios(funcionarios);

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        imprimirSalariosMinimos(funcionarios);
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("Todos os Funcionários:");
        funcionarios.forEach(Principal::imprimirInfoFormatada);
    }

    private static void imprimirInfoFormatada(Funcionario funcionario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataNascimentoFormatada = funcionario.getDataNascimento().format(formatter);

        String salarioFormatado = String.format("%.2f", funcionario.getSalario());

        System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: %s, Função: %s%n",
                funcionario.getNome(), dataNascimentoFormatada, salarioFormatado, funcionario.getFuncao());
    }

    private static void aumentarSalarios(List<Funcionario> funcionarios) {
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.valueOf(1.1));
            funcionario.setSalario(novoSalario);
        });
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(Principal::imprimirInfoFormatada);
        });
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        System.out.println("\nAniversariantes nos Meses 10 e 12:");
        Arrays.stream(meses).forEach(mes -> {
            funcionarios.stream()
                    .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == mes)
                    .forEach(Principal::imprimirInfoFormatada);
        });
    }

    private static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios) {
        System.out.println("\nFuncionário com Maior Idade:");
        Funcionario funcionarioMaiorIdade = funcionarios.stream()
                .max(Comparator.comparingInt(funcionario -> LocalDate.now().getYear() - funcionario.getDataNascimento().getYear()))
                .orElse(null);

        if (funcionarioMaiorIdade != null) {
            int idade = LocalDate.now().getYear() - funcionarioMaiorIdade.getDataNascimento().getYear();
            System.out.printf("Nome: %s, Idade: %d%n", funcionarioMaiorIdade.getNome(), idade);
        }
    }

    private static void imprimirFuncionariosOrdenados(List<Funcionario> funcionarios) {
        System.out.println("\nFuncionários Ordenados por Nome:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(Principal::imprimirInfoFormatada);
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        System.out.println("\nTotal dos Salários:");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("Total: %.2f%n", totalSalarios);
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        System.out.println("\nSalários em Mínimos:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.printf("%s: %.2f salários mínimos%n", funcionario.getNome(), salariosMinimos);
        });
    }
}