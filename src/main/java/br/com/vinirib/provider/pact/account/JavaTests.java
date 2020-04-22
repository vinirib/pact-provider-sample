package br.com.vinirib.provider.pact.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

public class JavaTests {

    public static void main(String[] args) {
        Produto p1 = new Produto();
        p1.preco = 10;

        Produto p2 = new Produto();
        p2.preco = 30;

        List<Produto> produtos = Arrays.asList(p1, p2);

        Integer somaPrecoProdutos = produtos.stream()
                .mapToInt(i -> i.preco)
                .sum(); // ?????
        System.out.println(somaPrecoProdutos);

    }

    static class Produto {
        Integer preco;
    }


}
