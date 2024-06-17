package dao.impl;

import entities.Profissional;

import java.util.List;

public interface ProfissionalDao {

    void insert(Profissional obj);

    void update(Profissional obj);

    void deleteById(Integer id);

    void deleteByAtivo(Profissional obj);

    Profissional findById(Integer id);

    List<Profissional> finall();
}
