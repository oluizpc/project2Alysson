import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CadastroDeNomes {
    static ArrayList<String> nomes = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String arquivo = "nomes.json";

    public static void main(String[] args) {
        carregarNomes();

        int opcao;
        do {
            System.out.println("\nMENU:");
            System.out.println("1 - Adicionar nome");
            System.out.println("2 - Listar nomes");
            System.out.println("3 - Remover nome");
            System.out.println("4 - Buscar nome");
            System.out.println("5 - Salvar nomes em arquivo JSON");
            System.out.println("6 - Carregar nomes de arquivo JSON");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> adicionarNome();
                case 2 -> listarNomes();
                case 3 -> removerNome();
                case 4 -> buscarNome();
                case 5 -> salvarNomes();
                case 6 -> carregarNomes();
                case 0 -> System.out.println("Encerrando programa...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    static void adicionarNome() {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome vazio não é permitido.");
        } else if (nomes.contains(nome)) {
            System.out.println("Nome já existe na lista.");
        } else {
            nomes.add(nome);
            System.out.println("Nome adicionado.");
        }
    }

    static void listarNomes() {
        Collections.sort(nomes);
        System.out.println("\nLista de Nomes:");
        for (int i = 0; i < nomes.size(); i++) {
            System.out.println((i + 1) + " - " + nomes.get(i));
        }
    }

    static void removerNome() {
        System.out.print("Digite o nome a ser removido: ");
        String nome = scanner.nextLine();
        if (nomes.remove(nome)) {
            System.out.println("Nome removido com sucesso.");
        } else {
            System.out.println("Nome não encontrado.");
        }
    }

    static void buscarNome() {
        System.out.print("Digite o nome a buscar: ");
        String nome = scanner.nextLine();
        if (nomes.contains(nome)) {
            System.out.println("Nome encontrado na lista.");
        } else {
            System.out.println("Nome não está na lista.");
        }
    }

    static void salvarNomes() {
        try (Writer writer = new FileWriter(arquivo)) {
            Gson gson = new Gson();
            gson.toJson(nomes, writer);
            System.out.println("Nomes salvos no arquivo " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    static void carregarNomes() {
        try (Reader reader = new FileReader(arquivo)) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<ArrayList<String>>() {}.getType();
            nomes = gson.fromJson(reader, tipoLista);
            if (nomes == null) nomes = new ArrayList<>();
            System.out.println("Nomes carregados do arquivo " + arquivo);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, iniciando lista vazia.");
            nomes = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
