package br.univali;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Main {
        public static void main(String[] args){

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            String createProfessionalTable = "CREATE TABLE IF NOT EXISTS profissional (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(255) NOT NULL, " +
                    "cpf VARCHAR(255) NOT NULL UNIQUE, " +
                    "ativo BOOLEAN NOT NULL" +
                    ")";

            try {

                connection = DB.getConnection();
                statement = connection.createStatement();


                resultSet = statement.executeQuery(createProfessionalTable);


            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                DB.closeStatement(statement);
                DB.closeResultset(resultSet);
                DB.closeConnection();
            }
        }
}