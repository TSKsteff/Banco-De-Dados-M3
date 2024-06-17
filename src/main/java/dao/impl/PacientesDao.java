package dao.impl;

import entities.Pacientes;

import java.util.List;

public interface PacientesDao {

    void insert(Pacientes obj);

    void update(Pacientes obj);

    void deleteById(Integer id);

    void deleteByAtivo(Pacientes obj);

    Pacientes findById(Integer id);

    List<Pacientes> finall();
}
