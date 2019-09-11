package br.com.clinica.service;

import br.com.clinica.dao.EnderecoDAO;
import br.com.clinica.entity.Endereco;

public class EnderecoService {

    public Endereco save(Integer id, String logradouro, Integer numero, String bairro, String complemento, String cidade,
                         String estado, Integer cep) {

        Endereco endereco = create(id, logradouro, numero, bairro, complemento, cidade, estado, cep);
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        if (endereco.getId() == null) {

            endereco = enderecoDAO.find(enderecoDAO.insert(endereco));

        } else {

            enderecoDAO.update(endereco);

            if (!isPersisted(endereco)) {

                System.err.println("\n" + "Erro ao alterar endereço" + "\n");
            }
        }

        return endereco;
    }

    private Boolean isPersisted(Endereco endereco) {

        EnderecoDAO enderecoDAO = new EnderecoDAO();

        Endereco enderecoPersisted = enderecoDAO.find(endereco.getId());

        if (endereco.equals(enderecoPersisted)) {

            return true;

        } else {

            return false;
        }
    }

    private Endereco create(Integer id, String logradouro, Integer numero, String bairro, String complemento,
                            String cidade, String estado, Integer cep) {

        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        if (id != null) {

            endereco = enderecoDAO.find(id);
        }

        if (!logradouro.equals("0")) {

            endereco.setLogradouro(logradouro);
        }

        if (numero != 0) {

            endereco.setNumero(numero);
        }

        if (!bairro.equals("0")) {

            endereco.setBairro(bairro);
        }

        if (!complemento.equals("0")) {

            endereco.setComplemento(complemento);
        }

        if (!cidade.equals("0")) {

            endereco.setCidade(cidade);
        }

        if (!estado.equals("0")) {

            endereco.setEstado(estado);
        }

        if (cep != 0) {

            endereco.setCep(cep);
        }

        return endereco;
    }
}
