import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa {
	//Classe Funcionário que estenda a classe Pessoa, com os atributos: 
	//salário (BigDecimal) e função (String).
    private BigDecimal salario;
    private String funcao;

    // Construtor
    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    // Métodos de acesso (getters e setters) para os atributos específicos de Funcionario
    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
