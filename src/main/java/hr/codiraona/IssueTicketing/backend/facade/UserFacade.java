/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.IssueTicketing.backend.facade;

import java.util.List;

import javax.ejb.Local;

import hr.codiraona.IssueTicketing.backend.dto.UserDTO;
import hr.codiraona.IssueTicketing.backend.model.Ticket;
import hr.codiraona.IssueTicketing.backend.model.User;

/**
 *
 * @author iva.bilandzic
 */
@Local
public interface UserFacade {
    
    void createUser(User user);
    
    void updateUser(UserDTO user);
    
    void deleteUser(UserDTO user);
    
    List<User> getAllUsers();
    
    UserDTO logIn(String username, String password);
    
    List<Ticket> getTicketsReportedByUser(String username);
    
    List<Ticket> getTicketAssignedToUser(String username);
    
    
    
}
