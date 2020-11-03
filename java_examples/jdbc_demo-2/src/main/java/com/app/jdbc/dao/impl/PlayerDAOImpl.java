package com.app.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.jdbc.dao.PlayerDAO;
import com.app.jdbc.dbutil.PostresSqlConnection;
import com.app.jdbc.exception.BusinessException;
import com.app.jdbc.model.Player;

public class PlayerDAOImpl implements PlayerDAO {

	@Override
	public int createPlayer(Player player) throws BusinessException {
		int c = 0;
		try (Connection connection = PostresSqlConnection.getConnection()) {
			String sql = PlayerQueries.INSERTPLAYER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, player.getId());
			preparedStatement.setString(2, player.getName());
			preparedStatement.setInt(3, player.getAge());
			preparedStatement.setString(4, player.getGender());
			preparedStatement.setString(5, player.getTeamName());
			preparedStatement.setLong(6, player.getContact());
			preparedStatement.setDate(7, new java.sql.Date(player.getDob().getTime()));

			c = preparedStatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // take off this line when in production
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
		return c;
	}

	@Override
	public int updatePlayerContact(int id, long newContact) throws BusinessException {
		int c = 0;
		try (Connection connection = PostresSqlConnection.getConnection()) {
			String sql = "update roc_revature.player set contact=? where id=? "; //write update qury here
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//set your ?(placeholders) with the values
			preparedStatement.setLong(1, newContact);
			preparedStatement.setInt(2, id);
			
			
			c = preparedStatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // take off this line when in production
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
		return c;
	}

	@Override
	public void deletePlayer(int id) throws BusinessException {
	//	int c = 0;
		try (Connection connection = PostresSqlConnection.getConnection()) {
			String sql = "DELETE FROM roc_revature.player where id = ?";//write here the delete query
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//set parameter here
			preparedStatement.setInt(1, id);
			 preparedStatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // take off this line when in production
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
	//	return c;

	}

	@Override
	public Player getPlayerById(int id) throws BusinessException {
		Player player = null;
		try (Connection connection = PostresSqlConnection.getConnection()) {
			String sql = PlayerQueries.GETPLAYERBYID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				player = new Player(id, resultSet.getString("name"), resultSet.getString("teamName"),
						resultSet.getInt("age"), resultSet.getLong("contact"), resultSet.getString("gender"));
				player.setDob(resultSet.getDate("dob"));
			}else {
				throw new BusinessException("Invalid ID!!!... No matching records found for the ID = "+id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // take off this line when in production
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
		return player;
	}

}
