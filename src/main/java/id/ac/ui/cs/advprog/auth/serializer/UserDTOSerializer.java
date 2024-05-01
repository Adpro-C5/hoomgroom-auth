package id.ac.ui.cs.advprog.auth.serializer;

import id.ac.ui.cs.advprog.auth.dto.UserDTO;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.text.SimpleDateFormat;

import java.io.IOException;

public class UserDTOSerializer extends JsonSerializer<UserDTO> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void serialize(UserDTO userDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", userDTO.getName());
        jsonGenerator.writeStringField("birthdate", dateFormat.format(userDTO.getBirthdate()));
        jsonGenerator.writeStringField("gender", userDTO.getGender());
        jsonGenerator.writeStringField("username", userDTO.getUsername());
        jsonGenerator.writeStringField("email", userDTO.getEmail());
        jsonGenerator.writeStringField("role", userDTO.getRole());
        jsonGenerator.writeEndObject();
    }
}