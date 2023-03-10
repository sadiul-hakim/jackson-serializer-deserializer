package org.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class CustomeSerializer extends StdSerializer<Person> {

    CustomeSerializer(){
        this(null);
    }

    protected CustomeSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField(VarNames.ID,person.getId());
        jsonGenerator.writeStringField(VarNames.NAME,person.getName());
        jsonGenerator.writeStringField(VarNames.EMAIL,person.getEmail());
        jsonGenerator.writeFieldName(VarNames.ADDRESS_LIST);
        //serialize list
        serializeList(person.getAddressList(),jsonGenerator);

        jsonGenerator.writeEndObject();

    }

    private void serializeList(List<Address> addressList,JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray();

        for(Address address:addressList){
            jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField(VarNames.ADDRESS_ID,address.getId());
            jsonGenerator.writeStringField(VarNames.ADDRESS_CITY,address.getCity());
            jsonGenerator.writeStringField(VarNames.ADDRESS_STATE,address.getState());
            jsonGenerator.writeStringField(VarNames.ADDRESS_COUNTRY,address.getCountry());

            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}
