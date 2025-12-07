package org.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * User entity representing both passengers and drivers in the system.
 * The role field determines what actions the user can perform.
 */
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    // Username must be unique across all users
    @Indexed(unique = true)
    private String username;

    // BCrypt encoded password - never store plain text!
    private String password;

    // Either ROLE_USER (passenger) or ROLE_DRIVER
    private String role;
}
