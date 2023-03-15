/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author ricks
 */
public class ProjectController {

    public void save(Project project) {
        String sql = "INSERT INTO projects (name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o Banco de Dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + e.getMessage(), e);
        }
    }

    public void removeById(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a tarefa" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de projetos que será devolvida quando a chamada do método ocorrer
        List<Project> projects = new ArrayList<Project>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos no resultSet
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar projetos " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        //Lista de tarefas criada e carregada do banco de dados
        return projects;
    }
}
