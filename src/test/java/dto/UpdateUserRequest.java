package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UpdateUserRequest {


           private String firstName;
        private String lastName;

        private String email;

        public String title;


        public String gender;


        public String phone;

    public String registerDate;
    public String updatedDate;


    }

